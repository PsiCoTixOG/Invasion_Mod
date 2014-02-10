package invmod.common;

import net.minecraft.world.EnumGameType;

public abstract interface IBlockAccessExtended extends EnumGameType
{
  public abstract int getLayeredData(int paramInt1, int paramInt2, int paramInt3);

  public abstract void setData(int paramInt1, int paramInt2, int paramInt3, Integer paramInteger);
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.IBlockAccessExtended
 * JD-Core Version:    0.6.2
 */