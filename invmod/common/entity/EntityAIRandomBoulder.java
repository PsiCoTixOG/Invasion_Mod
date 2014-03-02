package invmod.common.entity;

import invmod.common.nexus.INexusAccess;
import java.util.Random;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIRandomBoulder extends EntityAIBase
{
  private final EntityIMThrower theEntity;
  private int randomAmmo;
  private int timer;

  public EntityAIRandomBoulder(EntityIMThrower entity, int ammo)
  {
    this.theEntity = entity;
    this.randomAmmo = ammo;
    this.timer = 180;
  }

  public boolean shouldExecute()
  {
    if ((this.theEntity.getNexus() != null) && (this.randomAmmo > 0) && (this.theEntity.canThrow()))
    {
      if (--this.timer <= 0)
        return true;
    }
    return false;
  }

  public boolean isInterruptible()
  {
    return false;
  }

  public void startExecuting()
  {
    this.randomAmmo -= 1;
    this.timer = 240;
    INexusAccess nexus = this.theEntity.getNexus();
    int d = (int)(this.theEntity.findDistanceToNexus() * 0.37D);
    this.theEntity.throwBoulder(nexus.getXCoord() - d + this.theEntity.getRNG().nextInt(2 * d), nexus.getYCoord() - 5 + this.theEntity.getRNG().nextInt(10), nexus.getZCoord() - d + this.theEntity.getRNG().nextInt(2 * d));
  }
}