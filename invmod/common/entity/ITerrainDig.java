package invmod.common.entity;

import invmod.common.INotifyTask;

public abstract interface ITerrainDig
{
  public abstract boolean askRemoveBlock(int paramInt1, int paramInt2, int paramInt3, INotifyTask paramINotifyTask, float paramFloat);

  public abstract boolean askClearPosition(int paramInt1, int paramInt2, int paramInt3, INotifyTask paramINotifyTask, float paramFloat);
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.ITerrainDig
 * JD-Core Version:    0.6.2
 */