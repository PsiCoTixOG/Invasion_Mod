package invmod.common.entity;

import invmod.common.nexus.INexusAccess;

public abstract interface IHasNexus
{
  public abstract INexusAccess getNexus();

  public abstract void acquiredByNexus(INexusAccess paramINexusAccess);
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.IHasNexus
 * JD-Core Version:    0.6.2
 */