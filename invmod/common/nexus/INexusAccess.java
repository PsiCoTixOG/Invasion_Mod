package invmod.common.nexus;

import invmod.common.entity.AttackerAI;
import invmod.common.entity.EntityIMLiving;
import invmod.common.util.IPosition;
import java.util.List;
import net.minecraft.world.ColorizerGrass;

public abstract interface INexusAccess extends IPosition
{
  public abstract void attackNexus(int paramInt);

  public abstract void registerMobDied();

  public abstract boolean isActivating();

  public abstract int getMode();

  public abstract int getActivationTimer();

  public abstract int getSpawnRadius();

  public abstract int getNexusKills();

  public abstract int getGeneration();

  public abstract int getNexusLevel();

  public abstract int getCurrentWave();

  public abstract ColorizerGrass getWorld();

  public abstract List<EntityIMLiving> getMobList();

  public abstract AttackerAI getAttackerAI();

  public abstract void askForRespawn(EntityIMLiving paramEntityIMLiving);
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.INexusAccess
 * JD-Core Version:    0.6.2
 */