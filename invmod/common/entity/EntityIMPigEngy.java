package invmod.common.entity;

import invmod.common.IBlockAccessExtended;
import invmod.common.INotifyTask;
import invmod.common.mod_Invasion;
import invmod.common.nexus.INexusAccess;
import invmod.common.util.CoordsInt;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityIMPigEngy extends EntityIMMob implements ICanDig {
	private static final ItemStack itemLadder;
	private static final ItemStack itemPick;
	private static final ItemStack itemHammer;
	private static final int MAX_LADDER_TOWER_HEIGHT = 4;
	private static final int META_ITEM_ID_HELD = 29;
	private static final int META_SWINGING = 30;
	private static final Map<Integer, ItemStack> itemMap = new HashMap();
	private final NavigatorEngy bo;
	private final PathNavigateAdapter oldNavAdapter;
	private int swingTimer;
	private int planks;
	private int askForScaffoldTimer;
	private float supportThisTick;
	private TerrainModifier terrainModifier;
	private TerrainDigger terrainDigger;
	private TerrainBuilder terrainBuilder;
	private ItemStack currentItem;

	public EntityIMPigEngy(World world, INexusAccess nexus) {
		super(world, nexus);

		IPathSource pathSource = getPathSource();
		pathSource.setSearchDepth(1500);
		pathSource.setQuickFailDepth(1500);
		this.bo = new NavigatorEngy(this, pathSource);
		this.oldNavAdapter = new PathNavigateAdapter(this.bo);
		pathSource.setSearchDepth(1200);

		this.terrainModifier = new TerrainModifier(this, 2.8F);
		this.terrainDigger = new TerrainDigger(this, this.terrainModifier, 1.0F);
		this.terrainBuilder = new TerrainBuilder(this, this.terrainModifier, 1.0F);

		setBaseMoveSpeedStat(0.23F);
		this.attackStrength = 2;
		setMaxHealthAndHealth(11.0F);
		this.selfDamage = 0;
		this.maxSelfDamage = 0;
		this.planks = 15;
		this.maxDestructiveness = 2;
		this.askForScaffoldTimer = 0;

		this.dataWatcher.addObject(29, Short.valueOf((short) 0));
		this.dataWatcher.addObject(30, Byte.valueOf((byte) 0));

		setName("Pig Engineer");
		setGender(1);
		setDestructiveness(2);
		setJumpHeight(1);
		setCanClimb(false);
		setAI();

		int r = this.rand.nextInt(3);
		if (r == 0)
			setCurrentItem(itemHammer);
		else if (r == 1)
			setCurrentItem(itemPick);
		else
			setCurrentItem(itemLadder);
	}

	public EntityIMPigEngy(World world) {
		this(world, null);
	}

	protected void setAI() {
		this.c = new EntityAITasks(this.worldObj.theProfiler);
		this.c.addTask(0, new EntityAIKillEntity(this, EntityPlayer.class, 60));
		this.c.addTask(1, new EntityAIAttackNexus(this));
		this.c.addTask(2, new EntityAIGoToNexus(this));
		this.c.addTask(6, new EntityAIWanderIM(this));
		this.c.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 7.0F));
		this.c.addTask(8, new EntityAIWatchClosest(this, EntityIMCreeper.class, 12.0F));
		this.c.addTask(8, new EntityAILookIdle(this));

		this.d = new EntityAITasks(this.worldObj.theProfiler);
		this.d.addTask(2, new EntityAISimpleTarget(this, EntityPlayer.class, 3.0F, true));
		this.d.addTask(3, new EntityAIHurtByTarget(this, false));
	}

	public void updateAITasks() {
		super.updateAITasks();
		this.terrainModifier.onUpdate();
	}

	public void updateAITick() {
		super.updateAITick();
		this.terrainBuilder.setBuildRate(1.0F + this.supportThisTick * 0.33F);

		this.supportThisTick = 0.0F;

		this.askForScaffoldTimer -= 1;
		if (this.targetNexus != null) {
			int weight = Math.max(6000 / this.targetNexus.getYCoord() - getYCoord(), 1);
			if ((this.currentGoal == Goal.BREAK_NEXUS) && (((getNavigatorNew().getLastPathDistanceToTarget() > 2.0F) && (this.askForScaffoldTimer <= 0)) || (this.rand.nextInt(weight) == 0))) {
				if (this.targetNexus.getAttackerAI().askGenerateScaffolds(this)) {
					getNavigatorNew().clearPath();
					this.askForScaffoldTimer = 60;
				} else {
					this.askForScaffoldTimer = 140;
				}
			}
		}
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		updateAnimation();
	}

	public void onPathSet() {
		this.terrainModifier.cancelTask();
	}

	public PathNavigateAdapter getNavigator() {
		return this.oldNavAdapter;
	}

	public INavigation getNavigatorNew() {
		return this.bo;
	}

	public IBlockAccess getTerrain() {
		return this.worldObj;
	}

	protected boolean onPathBlocked(Path path, INotifyTask notifee) {
		if (!path.isFinished()) {
			PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
			return this.terrainDigger.askClearPosition(node.xCoord, node.yCoord, node.zCoord, notifee, 1.0F);
		}
		return false;
	}

	protected ITerrainBuild getTerrainBuildEngy() {
		return this.terrainBuilder;
	}

	protected ITerrainDig getTerrainDig() {
		return this.terrainDigger;
	}

	protected String getLivingSound() {
		return "mob.zombiepig.zpig";
	}

	protected String getHurtSound() {
		return "mob.zombiepig.zpighurt";
	}

	protected String getDeathSound() {
		return "mob.pig.death";
	}

	public String getSpecies() {
		return "Pigman";
	}

	public int getTier() {
		return 2;
	}

	public float getBlockRemovalCost(int x, int y, int z) {
		return getBlockStrength(x, y, z) * 20.0F;
	}

	public boolean canClearBlock(int x, int y, int z) {
		int id = this.worldObj.getBlockId(x, y, z);
		return (id == 0) || (isBlockDestructible(this.worldObj, x, y, z, id));
	}

	public boolean avoidsBlock(int id) {
		if ((id == 51) || (id == 7) || (id == 64) || (id == 8) || (id == 9) || (id == 10) || (id == 11)) {
			return true;
		}

		return false;
	}

	public boolean isBlockDestructible(IBlockAccess terrainMap, int x, int y, int z, int id) {
		return isBlockTypeDestructible(id);
	}

	public boolean isBlockTypeDestructible(int id) {
		if ((id == 0) || (id == Block.bedrock.blockID) || (id == Block.ladder.blockID)) {
			return false;
		}
		if ((id == Block.doorIron.blockID) || (id == Block.doorWood.blockID) || (id == Block.trapdoor.blockID)) {
			return true;
		}
		if (Block.blocksList[id].blockMaterial.isSolid()) {
			return true;
		}

		return false;
	}

	public void supportForTick(EntityIMLiving entity, float amount) {
		this.supportThisTick += amount;
	}

	public boolean canBePushed() {
		return false;
	}

	public float getBlockPathCost(PathNode prevNode, PathNode node, IBlockAccess terrainMap) {
		if ((node.xCoord == -21) && (node.zCoord == 180)) {
			this.planks = 10;
		}
		int id = terrainMap.getBlockId(node.xCoord, node.yCoord, node.zCoord);
		float materialMultiplier = (id != 0) && (isBlockDestructible(terrainMap, node.xCoord, node.yCoord, node.zCoord, id)) ? 3.2F : 1.0F;

		if (node.action == PathAction.BRIDGE)
			return prevNode.distanceTo(node) * 1.7F * materialMultiplier;
		if (node.action == PathAction.SCAFFOLD_UP)
			return prevNode.distanceTo(node) * 0.5F;
		if ((node.action == PathAction.LADDER_UP_NX) || (node.action == PathAction.LADDER_UP_NZ) || (node.action == PathAction.LADDER_UP_PX) || (node.action == PathAction.LADDER_UP_PZ))
			return prevNode.distanceTo(node) * 1.3F * materialMultiplier;
		if ((node.action == PathAction.LADDER_TOWER_UP_PX) || (node.action == PathAction.LADDER_TOWER_UP_NX) || (node.action == PathAction.LADDER_TOWER_UP_PZ) || (node.action == PathAction.LADDER_TOWER_UP_NZ)) {
			return prevNode.distanceTo(node) * 1.4F;
		}

		float multiplier = 1.0F;
		if ((terrainMap instanceof IBlockAccessExtended)) {
			int mobDensity = ((IBlockAccessExtended) terrainMap).getLayeredData(node.xCoord, node.yCoord, node.zCoord) & 0x7;
			multiplier += mobDensity;
		}
		if (id == 0) {
			return prevNode.distanceTo(node) * 1.0F * multiplier;
		}
		if (id == Block.snow.blockID) {
			return prevNode.distanceTo(node) * 1.0F * multiplier;
		}
		if (id == Block.ladder.blockID) {
			return prevNode.distanceTo(node) * 1.0F * 0.7F * multiplier;
		}
		if ((!Block.blocksList[id].getBlocksMovement(terrainMap, node.xCoord, node.yCoord, node.zCoord)) && (id != mod_Invasion.blockNexus.blockID)) {
			return prevNode.distanceTo(node) * 3.2F;
		}

		return super.getBlockPathCost(prevNode, node, terrainMap);
	}

	public void getPathOptionsFromNode(IBlockAccess terrainMap, PathNode currentNode, PathfinderIM pathFinder) {
		super.getPathOptionsFromNode(terrainMap, currentNode, pathFinder);
		if (this.planks <= 0) {
			return;
		}

		for (int i = 0; i < 4; i++) {
			if (getCollide(terrainMap, currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + CoordsInt.offsetAdjZ[i]) > 0) {
				for (int yOffset = 0; yOffset > -4; yOffset--) {
					int id = terrainMap.getBlockId(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord - 1 + yOffset, currentNode.zCoord + CoordsInt.offsetAdjZ[i]);
					if (id != 0)
						break;
					pathFinder.addNode(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord + yOffset, currentNode.zCoord + CoordsInt.offsetAdjZ[i], PathAction.BRIDGE);
				}
			}
		}
	}

	protected void calcPathOptionsVertical(IBlockAccess terrainMap, PathNode currentNode, PathfinderIM pathFinder) {
		if ((currentNode.xCoord == -11) && (currentNode.zCoord == 177)) {
			this.planks = 10;
		}
		super.calcPathOptionsVertical(terrainMap, currentNode, pathFinder);
		if (this.planks <= 0) {
			return;
		}

		if (getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord) > 0) {
			if (terrainMap.getBlockId(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord) == 0) {
				if (currentNode.action == PathAction.NONE) {
					addAnyLadderPoint(terrainMap, currentNode, pathFinder);
				} else if (!continueLadder(terrainMap, currentNode, pathFinder)) {
					addAnyLadderPoint(terrainMap, currentNode, pathFinder);
				}

			}

			if ((currentNode.action == PathAction.NONE) || (currentNode.action == PathAction.BRIDGE)) {
				int maxHeight = 4;
				for (int i = getCollideSize().getYCoord(); i < 4; i++) {
					int id = terrainMap.getBlockId(currentNode.xCoord, currentNode.yCoord + i, currentNode.zCoord);
					if ((id > 0) && (!Block.blocksList[id].getBlocksMovement(terrainMap, currentNode.xCoord, currentNode.yCoord + i, currentNode.zCoord))) {
						maxHeight = i - getCollideSize().getYCoord();
						break;
					}

				}

				for (int i = 0; i < 4; i++) {
					int id = terrainMap.getBlockId(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord - 1, currentNode.zCoord + CoordsInt.offsetAdjZ[i]);
					if (Block.isNormalCube(id)) {
						for (int height = 0; height < maxHeight; height++) {
							id = terrainMap.getBlockId(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord + height, currentNode.zCoord + CoordsInt.offsetAdjZ[i]);
							if (id != 0) {
								if (!Block.isNormalCube(id))
									break;
								pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.ladderTowerIndexOrient[i]);
								break;
							}
						}
					}
				}
			}

		}

		if ((terrainMap instanceof IBlockAccessExtended)) {
			int data = ((IBlockAccessExtended) terrainMap).getLayeredData(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord);
			if (data == 16384) {
				pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.SCAFFOLD_UP);
			}
		}
	}

	protected void addAnyLadderPoint(IBlockAccess terrainMap, PathNode currentNode, PathfinderIM pathFinder) {
		for (int i = 0; i < 4; i++) {
			if (terrainMap.isBlockNormalCube(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord + 1, currentNode.zCoord + CoordsInt.offsetAdjZ[i]))
				pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.ladderIndexOrient[i]);
		}
	}

	protected boolean continueLadder(IBlockAccess terrainMap, PathNode currentNode, PathfinderIM pathFinder)
  {
    switch (1.$SwitchMap$invmod$common$entity$PathAction[currentNode.action.ordinal()])
    {
    case 1:
      if (terrainMap.isBlockNormalCube(currentNode.xCoord + 1, currentNode.yCoord + 1, currentNode.zCoord))
      {
        pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_PX);
      }
      return true;
    case 2:
      if (terrainMap.isBlockNormalCube(currentNode.xCoord - 1, currentNode.yCoord + 1, currentNode.zCoord))
      {
        pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_NX);
      }
      return true;
    case 3:
      if (terrainMap.isBlockNormalCube(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord + 1))
      {
        pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_PZ);
      }
      return true;
    case 4:
      if (terrainMap.isBlockNormalCube(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord - 1))
      {
        pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_NZ);
      }
      return true;
    }

    return false;
  }

	public ItemStack getHeldItem() {
		return getCurrentItem();
	}

	protected void dropFewItems(boolean flag, int bonus) {
		super.dropFewItems(flag, bonus);
		if (this.rand.nextInt(2) == 0) {
			entityDropItem(new ItemStack(Item.leather, 1, 0), 0.0F);
		} else if (isBurning())
			entityDropItem(new ItemStack(Item.porkCooked, 1, 0), 0.0F);
		else
			entityDropItem(new ItemStack(Item.porkRaw, 1, 0), 0.0F);
	}

	protected void updateAnimation() {
		if ((!this.worldObj.isRemote) && (this.terrainModifier.isBusy())) {
			setSwinging(true);
			PathAction currentAction = getNavigatorNew().getCurrentWorkingAction();
			if (currentAction == PathAction.NONE)
				setCurrentItem(itemPick);
			else {
				setCurrentItem(itemHammer);
			}
		}
		int swingSpeed = getSwingSpeed();
		if (isSwinging()) {
			this.swingTimer += 1;
			if (this.swingTimer >= swingSpeed) {
				this.swingTimer = 0;
				setSwinging(false);
			}
		} else {
			this.swingTimer = 0;
		}

		this.swingProgress = (this.swingTimer / swingSpeed);
	}

	protected boolean isSwinging() {
		return getDataWatcher().getWatchableObjectByte(30) != 0;
	}

	protected void setSwinging(boolean flag) {
		getDataWatcher().updateObject(30, Byte.valueOf((byte) (flag == true ? 1 : 0)));
	}

	protected int getSwingSpeed() {
		return 10;
	}

	protected ItemStack getCurrentItem() {
		if (this.worldObj.isRemote) {
			int id = getDataWatcher().getWatchableObjectShort(29);
			if (id != this.currentItem.itemID)
				this.currentItem = ((ItemStack) itemMap.get(Integer.valueOf(id)));
		}
		return this.currentItem;
	}

	protected void setCurrentItem(ItemStack item) {
		this.currentItem = item;
		getDataWatcher().updateObject(29, Short.valueOf((short) item.itemID));
	}

	public static boolean canPlaceLadderAt(IBlockAccess map, int x, int y, int z) {
		if ((map.isBlockNormalCube(x + 1, y, z)) || (map.isBlockNormalCube(x - 1, y, z)) || (map.isBlockNormalCube(x, y, z + 1)) || (map.isBlockNormalCube(x, y, z - 1))) {
			return true;
		}
		return false;
	}

	static {
		itemLadder = new ItemStack(Block.ladder, 1);
		itemPick = new ItemStack(Item.pickaxeIron, 1);
		itemHammer = mod_Invasion.getRenderHammerItem();
		itemMap.put(Integer.valueOf(itemLadder.itemID), itemLadder);
		itemMap.put(Integer.valueOf(itemPick.itemID), itemPick);
		itemMap.put(Integer.valueOf(itemHammer.itemID), itemHammer);
	}
}