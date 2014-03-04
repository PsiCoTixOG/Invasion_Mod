package invmod.common.entity;

import invmod.common.IBlockAccessExtended;
import invmod.common.INotifyTask;
import invmod.common.nexus.INexusAccess;
import invmod.common.util.IPosition;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EntityIMZombie extends EntityIMMob implements ICanDig {
	private static final int META_CHANGED = 29;
	private static final int META_TIER = 30;
	private static final int META_TEXTURE = 31;
	private static final int META_FLAVOUR = 28;
	private static final int META_SWINGING = 27;
	private TerrainModifier terrainModifier;
	private TerrainDigger terrainDigger;
	private byte metaChanged;
	private int tier;
	private int flavour;
	private ItemStack defaultHeldItem;
	private Item itemDrop;
	private float dropChance;
	private int swingTimer;

	public EntityIMZombie(World world) {
		this(world, null);
	}

	public EntityIMZombie(World world, INexusAccess nexus) {
		super(world, nexus);
		this.terrainModifier = new TerrainModifier(this, 2.0F);
		this.terrainDigger = new TerrainDigger(this, this.terrainModifier, 1.0F);
		this.dropChance = 0.0F;
		if (world.isRemote)
			this.metaChanged = 1;
		else
			this.metaChanged = 0;
		this.tier = 1;
		this.flavour = 0;

		DataWatcher dataWatcher = getDataWatcher();
		dataWatcher.addObject(29, Byte.valueOf(this.metaChanged));
		dataWatcher.addObject(30, Integer.valueOf(this.tier));
		dataWatcher.addObject(31, Integer.valueOf(0));
		dataWatcher.addObject(28, Integer.valueOf(this.flavour));
		dataWatcher.addObject(27, Byte.valueOf((byte) 0));

		setAttributes(this.tier, this.flavour);
		setAI();
	}

	public void onUpdate() {
		super.onUpdate();
		if (this.metaChanged != getDataWatcher().getWatchableObjectByte(29)) {
			DataWatcher data = getDataWatcher();
			this.metaChanged = data.getWatchableObjectByte(29);
			setTexture(data.getWatchableObjectInt(31));

			if (this.tier != data.getWatchableObjectInt(30))
				setTier(data.getWatchableObjectInt(30));
			if (this.flavour != data.getWatchableObjectInt(28)) {
				setFlavour(data.getWatchableObjectInt(28));
			}
		}
		if ((!this.worldObj.isRemote) && (this.flammability >= 20) && (isBurning()))
			doFireball();
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		updateAnimation();
		updateSound();
	}

	public void onPathSet() {
		this.terrainModifier.cancelTask();
	}

	protected void setAI() {
		this.c = new EntityAITasks(this.worldObj.theProfiler);
		this.c.addTask(0, new EntityAIKillEntity(this, EntityPlayer.class, 40));
		this.c.addTask(1, new EntityAIAttackNexus(this));
		this.c.addTask(2, new EntityAIWaitForEngy(this, 4.0F, true));
		this.c.addTask(3, new EntityAIKillEntity(this, EntityLiving.class, 40));
		this.c.addTask(4, new EntityAIGoToNexus(this));
		this.c.addTask(6, new EntityAIWanderIM(this));
		this.c.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.c.addTask(8, new EntityAIWatchClosest(this, EntityIMCreeper.class, 12.0F));
		this.c.addTask(8, new EntityAILookIdle(this));

		this.d = new EntityAITasks(this.worldObj.theProfiler);
		this.d.addTask(0, new EntityAITargetRetaliate(this, EntityLiving.class, 12.0F));
		this.d.addTask(2, new EntityAISimpleTarget(this, EntityPlayer.class, 12.0F, true));
		this.d.addTask(5, new EntityAIHurtByTarget(this, false));

		if (this.tier == 3) {
			this.c.addTask(3, new EntityAIStoop(this));
			this.c.addTask(2, new EntityAISprint(this));
		} else {
			this.c.addTask(0, new EntityAIRallyBehindEntity(this, EntityIMCreeper.class, 4.0F));
			this.d.addTask(1, new EntityAISimpleTarget(this, EntityPlayer.class, 6.0F, true));
			this.d.addTask(3, new EntityAILeaderTarget(this, EntityIMCreeper.class, 10.0F, true));
			this.d.addTask(4, new EntityAITargetOnNoNexusPath(this, EntityIMPigEngy.class, 3.5F));
		}
	}

	public void setTier(int tier) {
		this.tier = tier;
		getDataWatcher().updateObject(30, Integer.valueOf(tier));
		setAttributes(tier, this.flavour);
		setAI();

		if (getDataWatcher().getWatchableObjectInt(31) == 0) {
			if (tier == 1) {
				int r = this.rand.nextInt(2);
				if (r == 0)
					setTexture(0);
				else if (r == 1)
					setTexture(1);
			} else if (tier == 2) {
				if (this.flavour == 2) {
					setTexture(5);
				} else if (this.flavour == 3) {
					setTexture(3);
				} else {
					int r = this.rand.nextInt(2);
					if (r == 0)
						setTexture(2);
					else if (r == 1)
						setTexture(4);
				}
			} else if (tier == 3) {
				setTexture(6);
			}
		}
	}

	public void setTexture(int textureId) {
		getDataWatcher().updateObject(31, Integer.valueOf(textureId));
	}

	public void setFlavour(int flavour) {
		getDataWatcher().updateObject(28, Integer.valueOf(flavour));
		this.flavour = flavour;
		setAttributes(this.tier, flavour);
	}

	public int getTextureId() {
		return getDataWatcher().getWatchableObjectInt(31);
	}

	public String toString() {
		return "EntityIMZombie#" + this.tier + "-" + getDataWatcher().getWatchableObjectInt(31) + "-" + this.flavour;
	}

	public IBlockAccess getTerrain() {
		return this.worldObj;
	}

	public ItemStack getHeldItem() {
		return this.defaultHeldItem;
	}

	public boolean avoidsBlock(int id) {
		if ((this.isImmuneToFire) && ((id == 51) || (id == 10) || (id == 11))) {
			return false;
		}
		return super.avoidsBlock(id);
	}

	public float getBlockRemovalCost(int x, int y, int z) {
		return getBlockStrength(x, y, z) * 20.0F;
	}

	public boolean canClearBlock(int x, int y, int z) {
		int id = this.worldObj.getBlockId(x, y, z);
		return (id == 0) || (isBlockDestructible(this.worldObj, x, y, z, id));
	}

	protected boolean onPathBlocked(Path path, INotifyTask notifee) {
		if ((!path.isFinished()) && ((isNexusBound()) || (getAttackTarget() != null))) {
			if ((path.getFinalPathPoint().distanceTo(path.getIntendedTarget()) > 2.2D) && (path.getCurrentPathIndex() + 2 >= path.getCurrentPathLength() / 2)) {
				return false;
			}
			PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
			if (this.terrainDigger.askClearPosition(node.xCoord, node.yCoord, node.zCoord, notifee, 1.0F))
				return true;
		}
		return false;
	}

	public boolean isBigRenderTempHack() {
		return this.tier == 3;
	}

	public boolean attackEntityAsMob(Entity entity) {
		return (this.tier == 3) && (isSprinting()) ? chargeAttack(entity) : super.attackEntityAsMob(entity);
	}

	public boolean canBePushed() {
		return this.tier != 3;
	}

	public void knockBack(Entity par1Entity, float par2, double par3, double par5) {
		if (this.tier == 3) {
			return;
		}
		this.isAirBorne = true;
		float f = MathHelper.sqrt_double(par3 * par3 + par5 * par5);
		float f1 = 0.4F;
		this.motionX /= 2.0D;
		this.motionY /= 2.0D;
		this.motionZ /= 2.0D;
		this.motionX -= par3 / f * f1;
		this.motionY += f1;
		this.motionZ -= par5 / f * f1;

		if (this.motionY > 0.4000000059604645D) {
			this.motionY = 0.4000000059604645D;
		}
	}

	public float getBlockPathCost(PathNode prevNode, PathNode node, IBlockAccess terrainMap) {
		if ((this.tier == 2) && (this.flavour == 2) && (node.action == PathAction.SWIM)) {
			float multiplier = 1.0F;
			if ((terrainMap instanceof IBlockAccessExtended)) {
				int mobDensity = ((IBlockAccessExtended) terrainMap).getLayeredData(node.xCoord, node.yCoord, node.zCoord) & 0x7;
				multiplier += mobDensity * 3;
			}

			if ((node.yCoord > prevNode.yCoord) && (getCollide(terrainMap, node.xCoord, node.yCoord, node.zCoord) == 2)) {
				multiplier += 2.0F;
			}

			return prevNode.distanceTo(node) * 1.2F * multiplier;
		}

		return super.getBlockPathCost(prevNode, node, terrainMap);
	}

	public boolean canBreatheUnderwater() {
		return (this.tier == 2) && (this.flavour == 2);
	}

	public boolean isBlockDestructible(IBlockAccess terrainMap, int x, int y, int z, int id) {
		if (getDestructiveness() == 0) {
			return false;
		}

		IPosition pos = getCurrentTargetPos();
		int dY = pos.getYCoord() - y;
		boolean isTooSteep = false;
		if (dY > 0) {
			dY += 8;
			int dX = pos.getXCoord() - x;
			int dZ = pos.getZCoord() - z;
			double dXZ = Math.sqrt(dX * dX + dZ * dZ) + 1.E-005D;
			isTooSteep = dY / dXZ > 2.144D;
		}

		return (!isTooSteep) && (isBlockTypeDestructible(id));
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

	public void onFollowingEntity(Entity entity) {
		if (entity == null) {
			setDestructiveness(1);
		} else if (((entity instanceof EntityIMPigEngy)) || ((entity instanceof EntityIMCreeper))) {
			setDestructiveness(0);
		} else {
			setDestructiveness(1);
		}
	}

	public float scaleAmount() {
		if (this.tier == 2)
			return 1.12F;
		if (this.tier == 3) {
			return 1.21F;
		}
		return 1.0F;
	}

	public String getSpecies() {
		return "Zombie";
	}

	public int getTier() {
		return this.tier < 3 ? 2 : 3;
	}

	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setInteger("tier", this.tier);
		nbttagcompound.setInteger("flavour", this.flavour);
		nbttagcompound.setInteger("textureId", this.dataWatcher.getWatchableObjectInt(31));
		super.writeEntityToNBT(nbttagcompound);
	}

	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		setTexture(nbttagcompound.getInteger("textureId"));
		this.flavour = nbttagcompound.getInteger("flavour");
		this.tier = nbttagcompound.getInteger("tier");
		if (this.tier == 0) {
			this.tier = 1;
		}
		setFlavour(this.flavour);
		setTier(this.tier);
		super.readEntityFromNBT(nbttagcompound);
	}

	protected void sunlightDamageTick() {
		if ((this.tier == 2) && (this.flavour == 2))
			damageEntity(DamageSource.generic, 3.0F);
		else
			setFire(8);
	}

	protected void updateAnimation() {
		if ((!this.worldObj.isRemote) && (this.terrainModifier.isBusy())) {
			setSwinging(true);
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
		return getDataWatcher().getWatchableObjectByte(27) != 0;
	}

	protected void setSwinging(boolean flag) {
		getDataWatcher().updateObject(27, Byte.valueOf((byte) (flag == true ? 1 : 0)));
	}

	protected void updateSound() {
		if (this.terrainModifier.isBusy()) {
			if (--this.throttled2 <= 0) {
				this.worldObj.playSoundAtEntity(this, "invmod:scrape", 0.85F, 1.0F / (this.rand.nextFloat() * 0.5F + 1.0F));
				this.throttled2 = (45 + this.rand.nextInt(20));
			}
		}
	}

	protected int getSwingSpeed() {
		return 10;
	}

	protected boolean chargeAttack(Entity entity) {
		int knockback = 4;
		entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.attackStrength + 3);
		entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.141593F / 180.0F) * knockback * 0.5F, 0.4D, MathHelper.cos(this.rotationYaw * 3.141593F / 180.0F) * knockback * 0.5F);
		setSprinting(false);
		this.worldObj.playSoundAtEntity(entity, "damage.fallbig", 1.0F, 1.0F);
		return true;
	}

	protected void updateAITasks() {
		super.updateAITasks();
		this.terrainModifier.onUpdate();
	}

	protected ITerrainDig getTerrainDig() {
		return this.terrainDigger;
	}

	protected String getLivingSound() {
		if (this.tier == 3) {
			return this.rand.nextInt(3) == 0 ? "invmod:bigzombie" : null;
		}

		return "mob.zombie.say";
	}

	protected String getHurtSound() {
		return "mob.zombie.hurt";
	}

	protected String getDeathSound() {
		return "mob.zombie.death";
	}

	protected int getDropItemId() {
		return Item.rottenFlesh.itemID;
	}

	protected void dropFewItems(boolean flag, int bonus) {
		super.dropFewItems(flag, bonus);
		if (this.rand.nextFloat() < 0.35F) {
			dropItem(Item.rottenFlesh.itemID, 1);
		}

		if ((this.itemDrop != null) && (this.rand.nextFloat() < this.dropChance)) {
			entityDropItem(new ItemStack(this.itemDrop, 1, 0), 0.0F);
		}
	}

	private void setAttributes(int tier, int flavour) {
		if (tier == 1) {
			if (flavour == 0) {
				setName("Zombie");
				setGender(1);
				setBaseMoveSpeedStat(0.19F);
				this.attackStrength = 4;
				setMaxHealthAndHealth(18.0F);
				this.selfDamage = 3;
				this.maxSelfDamage = 6;
				this.maxDestructiveness = 2;
				this.flammability = 3;

				setDestructiveness(2);
			} else if (flavour == 1) {
				setName("Zombie");
				setGender(1);
				setBaseMoveSpeedStat(0.19F);
				this.attackStrength = 6;
				setMaxHealthAndHealth(18.0F);
				this.selfDamage = 3;
				this.maxSelfDamage = 6;
				this.maxDestructiveness = 0;
				this.flammability = 3;
				this.defaultHeldItem = new ItemStack(Item.swordWood, 1);
				this.itemDrop = Item.swordWood;
				this.dropChance = 0.2F;

				setDestructiveness(0);
			}
		} else if (tier == 2) {
			if (flavour == 0) {
				setName("Zombie");
				setGender(1);
				setBaseMoveSpeedStat(0.19F);
				this.attackStrength = 7;
				setMaxHealthAndHealth(35.0F);
				this.selfDamage = 4;
				this.maxSelfDamage = 12;
				this.maxDestructiveness = 2;
				this.flammability = 4;
				this.itemDrop = Item.plateIron;
				this.dropChance = 0.25F;

				setDestructiveness(2);
			} else if (flavour == 1) {
				setName("Zombie");
				setGender(1);
				setBaseMoveSpeedStat(0.19F);
				this.attackStrength = 10;
				setMaxHealthAndHealth(40.0F);
				this.selfDamage = 3;
				this.maxSelfDamage = 9;
				this.maxDestructiveness = 0;
				this.itemDrop = Item.swordIron;
				this.dropChance = 0.25F;
				this.defaultHeldItem = new ItemStack(Item.swordIron, 1);

				setDestructiveness(0);
			} else if (flavour == 2) {
				setName("Tar Zombie");
				setGender(1);
				setBaseMoveSpeedStat(0.19F);
				this.attackStrength = 5;
				setMaxHealthAndHealth(30.0F);
				this.selfDamage = 3;
				this.maxSelfDamage = 9;
				this.maxDestructiveness = 2;
				this.flammability = 30;
				this.floatsInWater = false;

				setDestructiveness(2);
			} else if (flavour == 3) {
				setName("Zombie Pigman");
				setGender(1);
				setBaseMoveSpeedStat(0.25F);
				this.attackStrength = 8;
				setMaxHealthAndHealth(30.0F);
				this.maxDestructiveness = 2;
				this.isImmuneToFire = true;
				this.defaultHeldItem = new ItemStack(Item.swordGold, 1);
				setDestructiveness(2);
			}
		} else if (tier == 3) {
			if (flavour == 0) {
				setName("Zombie Brute");
				setGender(1);
				setBaseMoveSpeedStat(0.17F);
				this.attackStrength = 18;
				setMaxHealthAndHealth(65.0F);
				this.selfDamage = 4;
				this.maxSelfDamage = 20;
				this.maxDestructiveness = 2;
				this.flammability = 4;

				this.dropChance = 0.0F;

				setDestructiveness(2);
			}
		}
	}

	private void doFireball() {
		int x = MathHelper.floor_double(this.posX);
		int y = MathHelper.floor_double(this.posY);
		int z = MathHelper.floor_double(this.posZ);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int k = -1; k < 2; k++) {
					if ((this.worldObj.getBlockId(x + i, y + j, z + k) == 0) || (this.worldObj.getBlockMaterial(x + i, y + j, z + k).getCanBurn())) {
						this.worldObj.setBlock(x + i, y + j, z + k, Block.fire.blockID);
					}
				}
			}
		}

		List entities = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.5D, 1.5D, 1.5D));
		for (Entity entity : entities) 
		{
			entity.setFire(8);
		}
		attackEntityFrom(DamageSource.inFire, 500.0F);
	}
}