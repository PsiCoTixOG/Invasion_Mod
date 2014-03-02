package invmod.common.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import invmod.common.nexus.INexusAccess;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

public class EntityIMGiantBird extends EntityIMBird
{
  private static final float PICKUP_OFFSET_X = 0.0F;
  private static final float PICKUP_OFFSET_Y = 0.2F;
  private static final float PICKUP_OFFSET_Z = -0.92F;
  private static final float MODEL_ROTATION_OFFSET_Y = 1.9F;
  private static final byte TRIGGER_SQUAWK = 10;
  private static final byte TRIGGER_SCREECH = 10;
  private static final byte TRIGGER_DEATHSOUND = 10;

  public EntityIMGiantBird(World world)
  {
    this(world, null);
  }

  public EntityIMGiantBird(World world, INexusAccess nexus)
  {
    super(world, nexus);
    setName("Bird");
    setGender(2);
    this.attackStrength = 5;
    setMaxHealthAndHealth(58.0F);
    setSize(1.9F, 2.8F);
    setGravity(0.03F);
    setThrust(0.028F);
    setMaxPoweredFlightSpeed(0.9F);
    setLiftFactor(0.35F);
    setThrustComponentRatioMin(0.0F);
    setThrustComponentRatioMax(0.5F);
    setMaxTurnForce(getGravity() * 8.0F);
    setBaseMoveSpeedStat(0.4F);
    setAI();
    setDebugMode(1);
  }

  public void onUpdate()
  {
    super.onUpdate();
    if ((getDebugMode() == 1) && (!this.worldObj.isRemote))
    {
      setRenderLabel(getAIGoal() + "\n" + getNavString());
    }
  }

  public boolean canDespawn()
  {
    return false;
  }

  public void updateRiderPosition()
  {
    if (this.riddenByEntity != null)
    {
      double x = 0.0D;
      double y = getMountedYOffset() - 1.899999976158142D;
      double z = -0.9200000166893005D;

      double dAngle = this.rotationPitch / 180.0F * 3.141592653589793D;
      double sinF = Math.sin(dAngle);
      double cosF = Math.cos(dAngle);
      double tmp = z * cosF - y * sinF;
      y = y * cosF + z * sinF;
      z = tmp;

      dAngle = this.rotationYaw / 180.0F * 3.141592653589793D;
      sinF = Math.sin(dAngle);
      cosF = Math.cos(dAngle);
      tmp = x * cosF - z * sinF;
      z = z * cosF + x * sinF;
      x = tmp;

      y += 1.899999976158142D + this.riddenByEntity.getYOffset();

      this.riddenByEntity.lastTickPosX = (this.lastTickPosX + x);
      this.riddenByEntity.lastTickPosY = (this.lastTickPosY + y);
      this.riddenByEntity.lastTickPosZ = (this.lastTickPosZ + z);
      this.riddenByEntity.setPosition(this.posX + x, this.posY + y, this.posZ + z);
      this.riddenByEntity.rotationYaw = (getCarriedEntityYawOffset() + this.rotationYaw);
    }
  }

  public boolean shouldRiderSit()
  {
    return false;
  }

  public double getMountedYOffset()
  {
    return -0.2000000029802322D;
  }

  protected void doScreech()
  {
    if (!this.worldObj.isRemote)
    {
      this.worldObj.playSoundAtEntity(this, "invmod:v_screech", 6.0F, 1.0F + (this.rand.nextFloat() * 0.2F - 0.1F));
      this.worldObj.setEntityState(this, (byte)10);
    }
    else
    {
      setBeakState(35);
    }
  }

  protected void doMeleeSound()
  {
    doSquawk();
  }

  protected void doHurtSound()
  {
    doSquawk();
  }

  protected void doDeathSound()
  {
    if (!this.worldObj.isRemote)
    {
      this.worldObj.playSoundAtEntity(this, "invmod:v_death", 1.9F, 1.0F + (this.rand.nextFloat() * 0.2F - 0.1F));
      this.worldObj.setEntityState(this, (byte)10);
    }
    else
    {
      setBeakState(25);
    }
  }

  protected void onDebugChange()
  {
    if (getDebugMode() == 1)
    {
      setShouldRenderLabel(true);
    }
    else
    {
      setShouldRenderLabel(false);
    }
  }

  @SideOnly(Side.CLIENT)
  public void handleHealthUpdate(byte b)
  {
    super.handleHealthUpdate(b);
    if (b == 10)
    {
      doSquawk();
    }
    else if (b == 10)
    {
      doScreech();
    }
    else if (b == 10)
    {
      doDeathSound();
    }
  }

  private void doSquawk()
  {
    if (!this.worldObj.isRemote)
    {
      this.worldObj.playSoundAtEntity(this, "invmod:v_squawk", 1.9F, 1.0F + (this.rand.nextFloat() * 0.2F - 0.1F));
      this.worldObj.setEntityState(this, (byte)10);
    }
    else
    {
      setBeakState(10);
    }
  }

  private String getNavString()
  {
    return getNavigatorNew().getStatus();
  }

  private void setAI()
  {
    this.c = new EntityAITasks(this.worldObj.theProfiler);

    this.c.addTask(0, new EntityAISwoop(this));

    this.c.addTask(3, new EntityAIBoP(this));
    this.c.addTask(4, new EntityAIFlyingStrike(this));
    this.c.addTask(4, new EntityAIFlyingTackle(this));
    this.c.addTask(4, new EntityAIPickUpEntity(this, 0.0F, 0.2F, 0.0F, 1.5F, 1.5F, 20, 45.0F, 45.0F));
    this.c.addTask(4, new EntityAIStabiliseFlying(this, 35));
    this.c.addTask(4, new EntityAICircleTarget(this, 300, 16.0F, 45.0F));
    this.c.addTask(4, new EntityAIBirdFight(this, EntityZombie.class, 25, 0.4F));
    this.c.addTask(4, new EntityAIWatchTarget(this));

    this.d = new EntityAITasks(this.worldObj.theProfiler);

    this.d.addTask(2, new EntityAISimpleTarget(this, EntityZombie.class, 58.0F, true));
  }
}