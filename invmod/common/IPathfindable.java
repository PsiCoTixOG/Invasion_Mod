package invmod.common;

import invmod.common.entity.PathNode;
import invmod.common.entity.PathfinderIM;
import net.minecraft.world.EnumGameType;

public abstract interface IPathfindable
{
  public abstract float getBlockPathCost(PathNode paramPathNode1, PathNode paramPathNode2, EnumGameType paramEnumGameType);

  public abstract void getPathOptionsFromNode(EnumGameType paramEnumGameType, PathNode paramPathNode, PathfinderIM paramPathfinderIM);
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.IPathfindable
 * JD-Core Version:    0.6.2
 */