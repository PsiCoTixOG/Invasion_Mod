package invmod.common.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityIMArrowOld extends Entity
  implements IProjectile
{
  private int xTile;
  private int yTile;
  private int zTile;
  private int inTile;
  private int inData;
  private boolean inGround;
  public boolean doesArrowBelongToPlayer;
  public int arrowShake;
  public Entity shootingEntity;
  private int ticksInGround;
  private int ticksInAir;
  private int targetsHit;
  public boolean arrowCritical;
  private List<Integer> entitiesHit;

  public EntityIMArrowOld(World world)
  {
    super(world);
    this.renderDistanceWeight = 10.0D;
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inData = 0;
    this.targetsHit = 0;
    this.entitiesHit = new ArrayList();
    this.inGround = false;
    this.doesArrowBelongToPlayer = false;
    this.arrowShake = 0;
    this.ticksInAir = 0;
    this.arrowCritical = false;
    setSize(0.5F, 0.5F);
    setFire(5);
  }

  public EntityIMArrowOld(World world, double d, double d1, double d2)
  {
    super(world);
    this.renderDistanceWeight = 10.0D;
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inData = 0;
    this.targetsHit = 0;
    this.entitiesHit = new ArrayList();
    this.inGround = false;
    this.doesArrowBelongToPlayer = false;
    this.arrowShake = 0;
    this.ticksInAir = 0;
    this.arrowCritical = false;
    setSize(0.5F, 0.5F);
    setPosition(d, d1, d2);
    setFire(5);
    this.yOffset = 0.0F;
  }

  public EntityIMArrowOld(World world, EntityLivingBase entityliving, float f)
  {
    super(world);
    this.renderDistanceWeight = 10.0D;
    this.xTile = -1;
    this.yTile = -1;
    this.zTile = -1;
    this.inTile = 0;
    this.inData = 0;
    this.inGround = false;
    this.arrowShake = 0;
    this.ticksInAir = 0;
    this.arrowCritical = false;
    this.targetsHit = 0;
    this.entitiesHit = new ArrayList();
    this.shootingEntity = entityliving;
    this.doesArrowBelongToPlayer = (entityliving instanceof EntityPlayer);
    setSize(0.5F, 0.5F);
    setFire(5);
    setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
    this.posX -= MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
    this.posY -= 0.1000000014901161D;
    this.posZ -= MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
    setPosition(this.posX, this.posY, this.posZ);
    this.yOffset = 0.0F;
    this.motionX = (-MathHelper.sin(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F));
    this.motionZ = (MathHelper.cos(this.rotationYaw / 180.0F * 3.141593F) * MathHelper.cos(this.rotationPitch / 180.0F * 3.141593F));
    this.motionY = (-MathHelper.sin(this.rotationPitch / 180.0F * 3.141593F));
    setThrowableHeading(this.motionX, this.motionY, this.motionZ, f * 1.5F, 1.0F);
  }

  public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
  {
    setPosition(par1, par3, par5);
    setRotation(par7, par8);
  }

  public void setThrowableHeading(double d, double d1, double d2, float f, float f1)
  {
    float f2 = MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
    d /= f2;
    d1 /= f2;
    d2 /= f2;
    d += this.rand.nextGaussian() * 0.007499999832361937D * f1;
    d1 += this.rand.nextGaussian() * 0.007499999832361937D * f1;
    d2 += this.rand.nextGaussian() * 0.007499999832361937D * f1;
    d *= f;
    d1 *= f;
    d2 *= f;
    this.motionX = d;
    this.motionY = d1;
    this.motionZ = d2;
    float f3 = MathHelper.sqrt_double(d * d + d2 * d2);
    this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D));
    this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.141592741012573D));
    this.ticksInGround = 0;
  }

  public void setVelocity(double d, double d1, double d2)
  {
    this.motionX = d;
    this.motionY = d1;
    this.motionZ = d2;
    if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
    {
      float f = MathHelper.sqrt_double(d * d + d2 * d2);
      this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D));
      this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(d1, f) * 180.0D / 3.141592741012573D));
      this.prevRotationPitch = this.rotationPitch;
      this.prevRotationYaw = this.rotationYaw;
      setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      this.ticksInGround = 0;
    }
  }

  public void onUpdate()
  {
    super.onUpdate();
    if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
    {
      float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592741012573D));
      this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / 3.141592741012573D));
    }
    int i = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
    if (i > 0)
    {
      Block.blocksList[i].setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
      AxisAlignedBB axisalignedbb = Block.blocksList[i].getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
      if ((axisalignedbb != null) && (axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))))
      {
        this.inGround = true;
      }
    }
    if (this.arrowShake > 0)
    {
      this.arrowShake -= 1;
    }

    if (this.inGround)
    {
      int j = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
      int k = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
      if ((j == this.inTile) && (k == this.inData))
      {
        this.ticksInGround += 1;
        if (this.ticksInGround == 1200)
        {
          setDead();
        }
      }
      else
      {
        this.inGround = false;
        this.motionX *= this.rand.nextFloat() * 0.2F;
        this.motionY *= this.rand.nextFloat() * 0.2F;
        this.motionZ *= this.rand.nextFloat() * 0.2F;
        this.ticksInGround = 0;
        this.ticksInAir = 0;
      }
    }
    else
    {
      this.ticksInAir += 1;
      Vec3 vec3d = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
      Vec3 vec3d1 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks_do_do(vec3d, vec3d1, false, true);
      vec3d = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
      vec3d1 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
      if (movingobjectposition != null)
      {
        vec3d1 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
      }
      Entity entity = null;
      List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
      double d = 0.0D;
      for (int l = 0; l < list.size(); l++)
      {
        Entity entity1 = (Entity)list.get(l);
        if ((entity1.canBeCollidedWith()) && ((entity1 != this.shootingEntity) || (this.ticksInAir >= 5)))
        {
          float f5 = 0.3F;
          AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f5, f5, f5);
          MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3d, vec3d1);
          if (movingobjectposition1 != null)
          {
            double d1 = vec3d.distanceTo(movingobjectposition1.hitVec);
            if ((d1 < d) || (d == 0.0D))
            {
              entity = entity1;
              d = d1;
            }
          }
        }
      }
      if (entity != null)
      {
        movingobjectposition = new MovingObjectPosition(entity);
      }
      if (movingobjectposition != null)
      {
        if (movingobjectposition.entityHit != null)
        {
          float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
          int j1 = (int)Math.ceil(f1 * 2.0D);
          if (this.arrowCritical)
          {
            j1 = j1 * 3 / 2 + 1;
          }
          if ((movingobjectposition.entityHit instanceof EntityLiving))
          {
            boolean alreadyHit = false;
            for (Integer id : this.entitiesHit)
            {
              if (movingobjectposition.entityHit.entityId == id.intValue())
              {
                alreadyHit = true;
              }
            }

            if (!alreadyHit)
            {
              this.entitiesHit.add(Integer.valueOf(movingobjectposition.entityHit.entityId));
              if (!this.worldObj.isRemote)
              {
                EntityLiving entityLiving = (EntityLiving)movingobjectposition.entityHit;
                entityLiving.setArrowCountInEntity(entityLiving.getArrowCountInEntity() + 1);
              }
              if (this.targetsHit == 0)
              {
                this.targetsHit += 1;
                movingobjectposition.entityHit.attackEntityFrom(DamageSource.generic, j1);
                movingobjectposition.entityHit.setFire(8);
              }
              else if (this.targetsHit < 8)
              {
                this.targetsHit += 1;
                movingobjectposition.entityHit.setFire(8);
              }
              else
              {
                movingobjectposition.entityHit.setFire(8);
                this.worldObj.playSoundAtEntity(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                setDead();
              }
            }
          }
          else {
            this.motionX *= -0.1000000014901161D;
            this.motionY *= -0.1000000014901161D;
            this.motionZ *= -0.1000000014901161D;
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            this.ticksInAir = 0;
          }
        }
        else {
          this.xTile = movingobjectposition.blockX;
          this.yTile = movingobjectposition.blockY;
          this.zTile = movingobjectposition.blockZ;
          this.inTile = this.worldObj.getBlockId(this.xTile, this.yTile, this.zTile);
          this.inData = this.worldObj.getBlockMetadata(this.xTile, this.yTile, this.zTile);
          this.motionX = ((float)(movingobjectposition.hitVec.xCoord - this.posX));
          this.motionY = ((float)(movingobjectposition.hitVec.yCoord - this.posY));
          this.motionZ = ((float)(movingobjectposition.hitVec.zCoord - this.posZ));
          float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
          this.posX -= this.motionX / f2 * 0.0500000007450581D;
          this.posY -= this.motionY / f2 * 0.0500000007450581D;
          this.posZ -= this.motionZ / f2 * 0.0500000007450581D;
          this.worldObj.playSoundAtEntity(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
          this.inGround = true;
          this.arrowShake = 7;
          this.arrowCritical = false;
          if (this.inTile != 0)
          {
            Block.blocksList[this.inTile].onEntityCollidedWithBlock(this.worldObj, this.xTile, this.yTile, this.zTile, this);
          }
        }

      }

      for (int i1 = 0; i1 < 4; i1++)
      {
        this.worldObj.spawnParticle("lava", this.posX + this.motionX * i1 / 4.0D, this.posY + this.motionY * i1 / 4.0D, this.posZ + this.motionZ * i1 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
      }

      this.posX += this.motionX;
      this.posY += this.motionY;
      this.posZ += this.motionZ;
      float f3 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
      this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592741012573D));
      for (this.rotationPitch = ((float)(Math.atan2(this.motionY, f3) * 180.0D / 3.141592741012573D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
      while (this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
      while (this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
      while (this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
      this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
      this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
      float f4 = 0.99F;
      float f6 = 0.05F;
      if (isInWater())
      {
        for (int k1 = 0; k1 < 4; k1++)
        {
          float f7 = 0.25F;
          this.worldObj.spawnParticle("bubble", this.posX - this.motionX * f7, this.posY - this.motionY * f7, this.posZ - this.motionZ * f7, this.motionX, this.motionY, this.motionZ);
        }

        f4 = 0.8F;
      }
      this.motionX *= f4;
      this.motionY *= f4;
      this.motionZ *= f4;
      this.motionY -= f6;
      setPosition(this.posX, this.posY, this.posZ);
      doBlockCollisions();
    }
  }

  public void writeEntityToNBT(NBTTagCompound nbttagcompound)
  {
    nbttagcompound.setShort("xTile", (short)this.xTile);
    nbttagcompound.setShort("yTile", (short)this.yTile);
    nbttagcompound.setShort("zTile", (short)this.zTile);
    nbttagcompound.setByte("inTile", (byte)this.inTile);
    nbttagcompound.setByte("inData", (byte)this.inData);
    nbttagcompound.setByte("shake", (byte)this.arrowShake);
    nbttagcompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    nbttagcompound.setBoolean("player", this.doesArrowBelongToPlayer);
  }

  public void readEntityFromNBT(NBTTagCompound nbttagcompound)
  {
    this.xTile = nbttagcompound.getShort("xTile");
    this.yTile = nbttagcompound.getShort("yTile");
    this.zTile = nbttagcompound.getShort("zTile");
    this.inTile = (nbttagcompound.getByte("inTile") & 0xFF);
    this.inData = (nbttagcompound.getByte("inData") & 0xFF);
    this.arrowShake = (nbttagcompound.getByte("shake") & 0xFF);
    this.inGround = (nbttagcompound.getByte("inGround") == 1);
    this.doesArrowBelongToPlayer = nbttagcompound.getBoolean("player");
  }

  public void onCollideWithPlayer(EntityPlayer entityplayer)
  {
    if (this.worldObj.isRemote)
    {
      return;
    }
    if ((this.inGround) && (this.doesArrowBelongToPlayer) && (this.arrowShake <= 0) && (entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.arrow, 1))))
    {
      this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
      entityplayer.onItemPickup(this, 1);
      setDead();
    }
  }

  protected boolean canTriggerWalking()
  {
    return false;
  }

  @SideOnly(Side.CLIENT)
  public float getShadowSize()
  {
    return 0.0F;
  }

  public boolean canAttackWithItem()
  {
    return false;
  }

  protected void entityInit()
  {
  }
}