package invmod.common.entity;

import invmod.common.INotifyTask;
import invmod.common.mod_Invasion;
import invmod.common.nexus.INexusAccess;
import invmod.common.util.CoordsInt;
import invmod.common.util.IPosition;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityIMThrower extends EntityIMMob 
{
	private int throwTime;
	private int ammo;
	private int punchTimer;
	private boolean clearingPoint;
	private IPosition pointToClear;
	private INotifyTask clearPointNotifee;

	public EntityIMThrower(World world) 
	{
		this(world, null);
	}

	public EntityIMThrower(World world, INexusAccess nexus) 
	{
		super(world, nexus);
		setBaseMoveSpeedStat(0.13F);
		this.attackStrength = 10;
		setMaxHealthAndHealth(50.0F);
		this.selfDamage = 0;
		this.maxSelfDamage = 0;
		this.ammo = 5;
		this.experienceValue = 20;
		this.clearingPoint = false;
		setBurnsInDay(true);
		setName("");
		setDestructiveness(2);
		setSize(1.8F, 1.95F);
		setAI();
	}

	protected void setAI() {
		this.tasks = new EntityAITasks(this.worldObj.theProfiler);
		this.tasks.addTask(0, new EntityAIThrowerKillEntity(this, EntityPlayer.class, 55, 40.0F, 1.0F, 5));
		this.tasks.addTask(1, new EntityAIAttackNexus(this));
		this.tasks.addTask(2, new EntityAIRandomBoulder(this, 3));
		this.tasks.addTask(3, new EntityAIGoToNexus(this));
		this.tasks.addTask(6, new EntityAIWanderIM(this));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityIMCreeper.class, 12.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 16.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));

		this.targetTasks = new EntityAITasks(this.worldObj.theProfiler);
		this.targetTasks.addTask(2, new EntityAISimpleTarget(this, EntityPlayer.class, 16.0F, true));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, false));
	}

	public void updateAITick() {
		super.updateAITick();
		this.throwTime -= 1;
		if (this.clearingPoint) {
			if (clearPoint()) {
				this.clearingPoint = false;
				if (this.clearPointNotifee != null)
					this.clearPointNotifee.notifyTask(0);
			}
		}
	}

	public boolean isAIEnabled() {
		return true;
	}

	public boolean canThrow() {
		return this.throwTime <= 0;
	}

	public boolean onPathBlocked(Path path, INotifyTask notifee) {
		if (!path.isFinished()) {
			PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
			this.clearingPoint = true;
			this.clearPointNotifee = notifee;
			this.pointToClear = new CoordsInt(node.xCoord, node.yCoord, node.zCoord);
			return true;
		}
		return false;
	}

	public boolean isBlockDestructible(int id) {
		return false;
	}

	public String getName() {
		return getName();
	}

	public String getSpecies() {
		return "";
	}

	public int getTier() {
		return 3;
	}

	public int getGender() {
		return 1;
	}

	protected String getLivingSound() {
		return "mob.zombie.say";
	}

	protected String getHurtSound() {
		return "mob.zombie.hurt";
	}

	protected String getDeathSound() {
		return "mob.zombie.death";
	}

	protected boolean clearPoint() {
		if (--this.punchTimer <= 0) {
			int x = this.pointToClear.getXCoord();
			int y = this.pointToClear.getYCoord();
			int z = this.pointToClear.getZCoord();
			int mobX = MathHelper.floor_double(this.posX);
			int mobZ = MathHelper.floor_double(this.posZ);
			int xOffsetR = 0;
			int zOffsetR = 0;
			int axisX = 0;
			int axisZ = 0;

			float facing = this.rotationYaw % 360.0F;
			if (facing < 0.0F) {
				facing += 360.0F;
			}
			if ((facing >= 45.0F) && (facing < 135.0F)) {
				zOffsetR = -1;
				axisX = -1;
			} else if ((facing >= 135.0F) && (facing < 225.0F)) {
				xOffsetR = -1;
				axisZ = -1;
			} else if ((facing >= 225.0F) && (facing < 315.0F)) {
				zOffsetR = -1;
				axisX = 1;
			} else {
				xOffsetR = -1;
				axisZ = 1;
			}

			if (((Block.blocksList[this.worldObj.getBlockId(x, y, z)] != null) && (Block.blocksList[this.worldObj.getBlockId(x, y, z)].blockMaterial.isSolid())) || ((Block.blocksList[this.worldObj.getBlockId(x, y + 1, z)] != null) && (Block.blocksList[this.worldObj.getBlockId(x, y + 1, z)].blockMaterial.isSolid()))
					|| ((Block.blocksList[this.worldObj.getBlockId(x + xOffsetR, y, z + zOffsetR)] != null) && (Block.blocksList[this.worldObj.getBlockId(x + xOffsetR, y, z + zOffsetR)].blockMaterial.isSolid())) || ((Block.blocksList[this.worldObj.getBlockId(x + xOffsetR, y + 1, z + zOffsetR)] != null) && (Block.blocksList[this.worldObj.getBlockId(x + xOffsetR, y + 1, z + zOffsetR)].blockMaterial.isSolid()))) {
				tryDestroyBlock(x, y, z);
				tryDestroyBlock(x, y + 1, z);
				tryDestroyBlock(x + xOffsetR, y, z + zOffsetR);
				tryDestroyBlock(x + xOffsetR, y + 1, z + zOffsetR);
				this.punchTimer = 160;
			} else if (((Block.blocksList[this.worldObj.getBlockId(x - axisX, y + 1, z - axisZ)] != null) && (Block.blocksList[this.worldObj.getBlockId(x - axisX, y + 1, z - axisZ)].blockMaterial.isSolid())) || ((Block.blocksList[this.worldObj.getBlockId(x - axisX + xOffsetR, y + 1, z - axisZ + zOffsetR)] != null) && (Block.blocksList[this.worldObj.getBlockId(x - axisX + xOffsetR, y + 1, z - axisZ + zOffsetR)].blockMaterial.isSolid()))) {
				tryDestroyBlock(x - axisX, y + 1, z - axisZ);
				tryDestroyBlock(x - axisX + xOffsetR, y + 1, z - axisZ + zOffsetR);
				this.punchTimer = 160;
			} else if (((Block.blocksList[this.worldObj.getBlockId(x - 2 * axisX, y + 1, z - 2 * axisZ)] != null) && (Block.blocksList[this.worldObj.getBlockId(x - 2 * axisX, y + 1, z - 2 * axisZ)].blockMaterial.isSolid())) || ((Block.blocksList[this.worldObj.getBlockId(x - 2 * axisX + xOffsetR, y + 1, z - 2 * axisZ + zOffsetR)] != null) && (Block.blocksList[this.worldObj.getBlockId(x - 2 * axisX + xOffsetR, y + 1, z - 2 * axisZ + zOffsetR)].blockMaterial.isSolid()))) {
				tryDestroyBlock(x - 2 * axisX, y + 1, z - 2 * axisZ);
				tryDestroyBlock(x - 2 * axisX + xOffsetR, y + 1, z - 2 * axisZ + zOffsetR);
				this.punchTimer = 160;
			} else {
				return true;
			}
		}
		return false;
	}

	protected void tryDestroyBlock(int x, int y, int z) {
		int id = this.worldObj.getBlockId(x, y, z);
		Block block = Block.blocksList[id];
		if ((block != null) && ((isNexusBound()) || (this.j != null))) {
			if ((id == mod_Invasion.blockNexus.blockID) && (this.attackTime == 0) && (x == this.targetNexus.getXCoord()) && (y == this.targetNexus.getYCoord()) && (z == this.targetNexus.getZCoord())) {
				this.targetNexus.attackNexus(5);
				this.attackTime = 60;
			} else if (id != mod_Invasion.blockNexus.blockID) {
				int meta = this.worldObj.getBlockMetadata(x, y, z);
				this.worldObj.setBlock(x, y, z, 0);
				block.onBlockDestroyedByPlayer(this.worldObj, x, y, z, meta);
				block.dropBlockAsItem(this.worldObj, x, y, z, meta, 0);

				if (this.throttled == 0) {
					this.worldObj.playSoundAtEntity(this, "random.explode", 1.0F, 0.4F);

					this.throttled = 5;
				}
			}
		}
	}

	protected void attackEntity(Entity entity, float f) {
		if ((this.throwTime <= 0) && (this.ammo > 0) && (f > 4.0F)) {
			this.throwTime = 120;
			if (f < 50.0F) {
				throwBoulder(entity.posX, entity.posY + entity.getEyeHeight() - 0.7D, entity.posZ, false);
			}
		} else {
			super.attackEntity(entity, f);
		}
	}

	protected void throwBoulder(double entityX, double entityY, double entityZ, boolean forced) {
		float launchSpeed = 1.0F;
		double dX = entityX - this.posX;
		double dZ = entityZ - this.posZ;
		double dXY = MathHelper.sqrt_double(dX * dX + dZ * dZ);

		if ((0.025D * dXY / (launchSpeed * launchSpeed) <= 1.0D) && (this.attackTime == 0)) {
			EntityIMBoulder entityBoulder = new EntityIMBoulder(this.worldObj, this, launchSpeed);
			double dY = entityY - entityBoulder.posY;
			double angle = 0.5D * Math.asin(0.025D * dXY / (launchSpeed * launchSpeed));
			dY += dXY * Math.tan(angle);
			entityBoulder.setBoulderHeading(dX, dY, dZ, launchSpeed, 0.05F);
			this.worldObj.spawnEntityInWorld(entityBoulder);
		} else if (forced) {
			EntityIMBoulder entityBoulder = new EntityIMBoulder(this.worldObj, this, launchSpeed);
			double dY = entityY - entityBoulder.posY;
			dY += dXY * Math.tan(0.7853981633974483D);
			entityBoulder.setBoulderHeading(dX, dY, dZ, launchSpeed, 0.05F);
			this.worldObj.spawnEntityInWorld(entityBoulder);
		}
	}

	protected void throwBoulder(double entityX, double entityY, double entityZ) {
		this.throwTime = 40;
		float launchSpeed = 1.0F;
		double dX = entityX - this.posX;
		double dZ = entityZ - this.posZ;
		double dXY = MathHelper.sqrt_double(dX * dX + dZ * dZ);
		double p = 0.025D * dXY / (launchSpeed * launchSpeed);
		double angle;
		if (p <= 1.0D)
			angle = 0.5D * p;
		else {
			angle = 0.7853981633974483D;
		}
		EntityIMBoulder entityBoulder = new EntityIMBoulder(this.worldObj, this, launchSpeed);
		double dY = entityY - entityBoulder.posY;
		dY += dXY * Math.tan(angle);
		entityBoulder.setBoulderHeading(dX, dY, dZ, launchSpeed, 0.05F);
		this.worldObj.spawnEntityInWorld(entityBoulder);
	}

	protected void dropFewItems(boolean flag, int bonus) {
		super.dropFewItems(flag, bonus);
		entityDropItem(new ItemStack(mod_Invasion.itemRemnants, 1, 1), 0.0F);
	}
}