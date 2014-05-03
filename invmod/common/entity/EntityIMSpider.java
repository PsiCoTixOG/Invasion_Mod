package invmod.common.entity;

import invmod.common.mod_Invasion;
import invmod.common.nexus.EntityConstruct;
import invmod.common.nexus.IMEntityType;
import invmod.common.nexus.INexusAccess;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityIMSpider extends EntityIMMob implements ISpawnsOffspring {
	private IMMoveHelper i;
	private byte metaChanged;
	private int tier;
	private int flavour;
	private int pounceTime;
	private int pounceAbility;
	private int airborneTime;
	private static final int META_CHANGED = 29;
	private static final int META_TIER = 30;
	private static final int META_TEXTURE = 31;
	private static final int META_FLAVOUR = 28;

	public EntityIMSpider(World world) {
		this(world, null);
	}

	public EntityIMSpider(World world, INexusAccess nexus) {
		super(world, nexus);
		setSize(1.4F, 0.9F);
		setCanClimb(true);
		this.airborneTime = 0;
		if (world.isRemote)
			this.metaChanged = 1;
		else
			this.metaChanged = 0;
		this.tier = 1;
		this.flavour = 0;
		setAttributes(this.tier, this.flavour);
		setAI();
		this.i = new IMMoveHelperSpider(this);

		DataWatcher dataWatcher = getDataWatcher();
		dataWatcher.addObject(29, Byte.valueOf(this.metaChanged));
		dataWatcher.addObject(30, Integer.valueOf(this.tier));
		dataWatcher.addObject(31, Integer.valueOf(0));
		dataWatcher.addObject(28, Integer.valueOf(this.flavour));
	}

	protected void setAI() {
		this.tasks = new EntityAITasks(this.worldObj.theProfiler);
		this.tasks.addTask(0, new EntityAIKillEntity(this, EntityPlayer.class, 40));
		this.tasks.addTask(1, new EntityAIAttackNexus(this));
		this.tasks.addTask(2, new EntityAIWaitForEngy(this, 5.0F, false));
		this.tasks.addTask(3, new EntityAIKillEntity(this, EntityLiving.class, 40));
		this.tasks.addTask(4, new EntityAIGoToNexus(this));
		this.tasks.addTask(6, new EntityAIWanderIM(this));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));

		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.targetTasks = new EntityAITasks(this.worldObj.theProfiler);
		this.targetTasks.addTask(0, new EntityAITargetRetaliate(this, EntityLiving.class, 12.0F));
		this.targetTasks.addTask(2, new EntityAISimpleTarget(this, EntityPlayer.class, 14.0F, true));
		this.targetTasks.addTask(3, new EntityAITargetOnNoNexusPath(this, EntityIMPigEngy.class, 3.5F));
		this.targetTasks.addTask(4, new EntityAIHurtByTarget(this, false));

		this.tasks.addTask(0, new EntityAIRallyBehindEntity(this, EntityIMCreeper.class, 4.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityIMCreeper.class, 12.0F));

		if (this.tier == 2) {
			if (this.flavour == 0) {
				this.tasks.addTask(2, new EntityAIPounce(this, 0.2F, 1.55F, 18));
			} else if (this.flavour == 1) {
				this.tasks.addTask(0, new EntityAILayEgg(this, 1));
			}
		}
	}

	public void onUpdate() {
		if (this.worldObj.isRemote) {
			this.worldObj.isRemote = this.worldObj.isRemote;
		}
		super.onUpdate();
		if ((this.worldObj.isRemote) && (this.metaChanged != getDataWatcher().getWatchableObjectByte(29))) {
			DataWatcher data = getDataWatcher();
			this.metaChanged = data.getWatchableObjectByte(29);
			setTexture(data.getWatchableObjectInt(31));

			if (this.tier != data.getWatchableObjectInt(30))
				setTier(data.getWatchableObjectInt(30));
			if (this.flavour != data.getWatchableObjectInt(28))
				setFlavour(data.getWatchableObjectInt(28));
		}
	}

	public void moveEntityWithHeading(float x, float z) {
		if (isInWater()) {
			double y = this.posY;
			moveFlying(x, z, 0.02F);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.8D;
			this.motionY *= 0.8D;
			this.motionZ *= 0.8D;
			this.motionY -= 0.02D;
			if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
				this.motionY = 0.3D;
		} else if (handleLavaMovement()) {
			double y = this.posY;
			moveFlying(x, z, 0.02F);
			moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionY *= 0.5D;
			this.motionZ *= 0.5D;
			this.motionY -= 0.02D;
			if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
				this.motionY = 0.3D;
		} else {
			float groundFriction = 0.91F;

			if (this.airborneTime == 0) {
				float landMoveSpeed;
				if (this.onGround) {
					groundFriction = 0.546F;
					int i = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY) - 1, MathHelper.floor_double(this.posZ));
					if (i > 0) {
						groundFriction = Block.blocksList[i].slipperiness * 0.91F;
					}
					landMoveSpeed = getAIMoveSpeed();
					landMoveSpeed *= 0.162771F / (groundFriction * groundFriction * groundFriction);
				} else {
					landMoveSpeed = this.jumpMovementFactor;
				}

				moveFlying(x, z, landMoveSpeed);
			} else {
				groundFriction = 1.0F;
			}

			if (isOnLadder()) {
				float maxLadderXZSpeed = 0.15F;
				if (this.motionX < -maxLadderXZSpeed)
					this.motionX = (-maxLadderXZSpeed);
				if (this.motionX > maxLadderXZSpeed)
					this.motionX = maxLadderXZSpeed;
				if (this.motionZ < -maxLadderXZSpeed)
					this.motionZ = (-maxLadderXZSpeed);
				if (this.motionZ > maxLadderXZSpeed) {
					this.motionZ = maxLadderXZSpeed;
				}
				this.fallDistance = 0.0F;
				if (this.motionY < -0.15D) {
					this.motionY = -0.15D;
				}
				if ((isSneaking()) && (this.motionY < 0.0D)) {
					this.motionY = 0.0D;
				}
			}
			moveEntity(this.motionX, this.motionY, this.motionZ);
			if (((this.isCollidedHorizontally) || (this.isJumping)) && (isOnLadder())) {
				this.motionY = 0.2D;
			}
			float airResistance = 1.0F;
			this.motionY -= getGravity();
			this.motionY *= airResistance;
			this.motionX *= groundFriction * airResistance;
			this.motionZ *= groundFriction * airResistance;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double dX = this.posX - this.prevPosX;
		double dZ = this.posZ - this.prevPosZ;
		float limbEnergy = MathHelper.sqrt_double(dX * dX + dZ * dZ) * 4.0F;

		if (limbEnergy > 1.0F) {
			limbEnergy = 1.0F;
		}

		this.limbSwingAmount += (limbEnergy - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;
	}

	public IMMoveHelper getMoveHelper() {
		return this.i;
	}

	protected void jump() {
		this.motionY = 0.41D;
		this.isAirBorne = true;
	}

	public void setTier(int tier) {
		this.tier = tier;
		getDataWatcher().updateObject(30, Integer.valueOf(tier));
		setAttributes(tier, this.flavour);
		setAI();

		if (getDataWatcher().getWatchableObjectInt(31) == 0) {
			if (tier == 1) {
				setTexture(0);
			} else if (tier == 2) {
				if (this.flavour == 0)
					setTexture(1);
				else
					setTexture(2);
			}
		}
	}

	public void setTexture(int textureId) {
		getDataWatcher().updateObject(31, Integer.valueOf(textureId));
	}

	public void setFlavour(int flavour) {
		this.flavour = flavour;
		getDataWatcher().updateObject(28, Integer.valueOf(flavour));
		setAttributes(this.tier, flavour);
	}

	public int getTextureId() {
		return getDataWatcher().getWatchableObjectInt(31);
	}

	public String toString() {
		return "EntityIMSpider#" + this.tier + "-" + getDataWatcher().getWatchableObjectInt(31) + "-" + this.flavour;
	}

	public double getMountedYOffset() {
		return this.height * 0.75D - 0.5D;
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
		if (this.tier == 0)
			this.tier = 1;
		setTier(this.tier);
		super.readEntityFromNBT(nbttagcompound);
	}

	public boolean avoidsBlock(int id) {
		if ((id == 51) || (id == 7)) {
			return true;
		}

		return false;
	}

	public float spiderScaleAmount() {
		if ((this.tier == 1) && (this.flavour == 1))
			return 0.35F;
		if ((this.tier == 2) && (this.flavour == 1)) {
			return 1.3F;
		}
		return 1.0F;
	}

	public Entity[] getOffspring(Entity partner) {
		if ((this.tier == 2) && (this.flavour == 1)) {
			EntityConstruct template = new EntityConstruct(IMEntityType.SPIDER, 1, 0, 1, 1.0F, 0, 0);
			Entity[] offSpring = new Entity[6];
			for (int i = 0; i < offSpring.length; i++) {
				offSpring[i] = mod_Invasion.getMobBuilder().createMobFromConstruct(template, this.worldObj, getNexus());
			}
			return offSpring;
		}

		return null;
	}

	public int getAirborneTime() {
		return this.airborneTime;
	}

	public boolean canBePushed() {
		return !isOnLadder();
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	public boolean checkForAdjacentClimbBlock() {
		return this.isCollidedHorizontally;
	}

	public String getSpecies() {
		return "Spider";
	}

	public int getTier() {
		return 2;
	}

	protected void setAirborneTime(int time) {
		this.airborneTime = time;
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected String getLivingSound() {
		return "mob.spider.say";
	}

	protected String getHurtSound() {
		return "mob.spider.say";
	}

	protected String getDeathSound() {
		return "mob.spider.death";
	}

	protected void fall(float f) {
		int i = (int) Math.ceil(f - 3.0F);
		if (i > 0) {
			int j = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2D - this.yOffset), MathHelper.floor_double(this.posZ));
			if (j > 0) {
				StepSound stepsound = Block.blocksList[j].stepSound;
				this.worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
			}
		}
	}

	protected int getDropItemId() {
		return Item.silk.itemID;
	}

	protected void dropFewItems(boolean flag, int bonus) {
		if ((this.tier == 1) && (this.flavour == 1)) {
			return;
		}
		super.dropFewItems(flag, bonus);
		if (this.rand.nextFloat() < 0.35F) {
			dropItem(Item.silk.itemID, 1);
		}
	}

	private void setAttributes(int tier, int flavour) {
		setGravity(0.08F);
		setSize(1.4F, 0.9F);
		setGender(this.rand.nextInt(2) + 1);
		if (tier == 1) {
			if (flavour == 0) {
				setName("Spider");
				setBaseMoveSpeedStat(0.29F);
				this.attackStrength = 3;
				setMaxHealthAndHealth(18.0F);
				this.pounceTime = 0;
				this.pounceAbility = 0;
				this.maxDestructiveness = 0;
				setDestructiveness(0);
				setAggroRange(10);
			} else if (flavour == 1) {
				setName("Baby Spider");
				setSize(0.42F, 0.3F);
				setBaseMoveSpeedStat(0.17F);
				this.attackStrength = 1;
				setMaxHealthAndHealth(3.0F);
				this.pounceTime = 0;
				this.pounceAbility = 0;
				this.maxDestructiveness = 0;
				setDestructiveness(0);
				setAggroRange(10);
			}
		} else if (tier == 2) {
			if (flavour == 0) {
				setName("Jumping Spider");
				setBaseMoveSpeedStat(0.3F);
				this.attackStrength = 5;
				setMaxHealthAndHealth(18.0F);
				this.pounceTime = 0;
				this.pounceAbility = 1;
				this.maxDestructiveness = 0;
				setDestructiveness(0);
				setAggroRange(18);
				setGravity(0.043F);
			} else if (flavour == 1) {
				setName("Mother Spider");
				setGender(2);
				setBaseMoveSpeedStat(0.22F);
				this.attackStrength = 4;
				setMaxHealthAndHealth(23.0F);
				this.pounceTime = 0;
				this.pounceAbility = 0;
				this.maxDestructiveness = 0;
				setDestructiveness(0);
				setAggroRange(18);
			}
		}
	}
}