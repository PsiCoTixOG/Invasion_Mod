package invmod.common.entity;

public class EntityAIWaitForEngy extends EntityAIFollowEntity<EntityIMPigEngy>
{
  private final float PATH_DISTANCE_TRIGGER = 4.0F;
  private boolean canHelp;

  public EntityAIWaitForEngy(EntityIMLiving entity, float followDistance, boolean canHelp)
  {
    super(entity, EntityIMPigEngy.class, followDistance);
    this.canHelp = canHelp;
  }

  public void updateTask()
  {
    super.updateTask();
    if (this.canHelp)
    {
      ((EntityIMPigEngy)getTarget()).supportForTick(getEntity(), 1.0F);
    }
  }
}