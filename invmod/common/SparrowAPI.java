package invmod.common;

import net.minecraft.src.nm;

public abstract interface SparrowAPI
{
  public abstract boolean isStupidToAttack();

  public abstract boolean doNotVaporize();

  public abstract boolean isPredator();

  public abstract boolean isHostile();

  public abstract boolean isPeaceful();

  public abstract boolean isPrey();

  public abstract boolean isNeutral();

  public abstract boolean isUnkillable();

  public abstract boolean isThreatTo(nm paramnm);

  public abstract boolean isFriendOf(nm paramnm);

  public abstract boolean isNPC();

  public abstract int isPet();

  public abstract nm getPetOwner();

  public abstract String getName();

  public abstract nm getAttackingTarget();

  public abstract float getSize();

  public abstract String getSpecies();

  public abstract int getTier();

  public abstract int getGender();

  public abstract String customStringAndResponse(String paramString);

  public abstract String getSimplyID();
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.SparrowAPI
 * JD-Core Version:    0.6.2
 */