package invmod.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntityAIFollowEntity<T extends EntityLiving> extends EntityAIMoveToEntity<T>
{
  private float followDistanceSq;

  public EntityAIFollowEntity(EntityIMLiving entity, float followDistance)
  {
    this(entity, EntityLiving.class, followDistance);
  }

  public EntityAIFollowEntity(EntityIMLiving entity, Class<? extends T> target, float followDistance)
  {
    super(entity, target);
    this.followDistanceSq = (followDistance * followDistance);
  }

  public void startExecuting()
  {
    getEntity().onFollowingEntity(getTarget());
    super.startExecuting();
  }

  public void resetTask()
  {
    getEntity().onFollowingEntity(null);
    super.resetTask();
  }

  public void updateTask()
  {
    super.updateTask();
    Entity entity = getTarget();
    if (getEntity().getDistanceSq(entity.posX, entity.boundingBox.minY, entity.posZ) < this.followDistanceSq)
      getEntity().getNavigatorNew().haltForTick();
  }
}