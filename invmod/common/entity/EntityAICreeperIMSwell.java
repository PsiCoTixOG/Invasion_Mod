package invmod.common.entity;

//NOOB HAUS: Done 

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAICreeperIMSwell extends EntityAIBase
{
  EntityIMCreeper theEntity;
  EntityLivingBase targetEntity;

  public EntityAICreeperIMSwell(EntityIMCreeper par1EntityCreeper)
  {
    this.theEntity = par1EntityCreeper;
    setMutexBits(1);
  }

  public boolean shouldExecute()
  {
    EntityLivingBase entityliving = this.theEntity.getAttackTarget();
    return (this.theEntity.getCreeperState() > 0) || ((entityliving != null) && (this.theEntity.getDistanceSqToEntity(entityliving) < 9.0D));
  }

  public void startExecuting()
  {
    this.theEntity.getNavigatorNew().clearPath();
    this.targetEntity = this.theEntity.getAttackTarget();
  }

  public void resetTask()
  {
    this.targetEntity = null;
  }

  public void updateTask()
  {
    if (this.targetEntity == null)
    {
      this.theEntity.setCreeperState(-1);
      return;
    }

    if (this.theEntity.getDistanceSqToEntity(this.targetEntity) > 49.0D)
    {
      this.theEntity.setCreeperState(-1);
      return;
    }

    if (!this.theEntity.getEntitySenses().canSee(this.targetEntity))
    {
      this.theEntity.setCreeperState(-1);
      return;
    }

    this.theEntity.setCreeperState(1);
  }
}