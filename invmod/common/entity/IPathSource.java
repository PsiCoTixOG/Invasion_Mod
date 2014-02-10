/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.IPathfindable;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.world.EnumGameType;
/*    */ 
/*    */ public abstract interface IPathSource
/*    */ {
/*    */   public abstract Path createPath(IPathfindable paramIPathfindable, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2, EnumGameType paramEnumGameType);
/*    */ 
/*    */   public abstract Path createPath(EntityIMLiving paramEntityIMLiving, nm paramnm, float paramFloat1, float paramFloat2, EnumGameType paramEnumGameType);
/*    */ 
/*    */   public abstract Path createPath(EntityIMLiving paramEntityIMLiving, int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, EnumGameType paramEnumGameType);
/*    */ 
/*    */   public abstract void createPath(IPathResult paramIPathResult, IPathfindable paramIPathfindable, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat, EnumGameType paramEnumGameType);
/*    */ 
/*    */   public abstract void createPath(IPathResult paramIPathResult, EntityIMLiving paramEntityIMLiving, nm paramnm, float paramFloat, EnumGameType paramEnumGameType);
/*    */ 
/*    */   public abstract void createPath(IPathResult paramIPathResult, EntityIMLiving paramEntityIMLiving, int paramInt1, int paramInt2, int paramInt3, float paramFloat, EnumGameType paramEnumGameType);
/*    */ 
/*    */   public abstract int getSearchDepth();
/*    */ 
/*    */   public abstract int getQuickFailDepth();
/*    */ 
/*    */   public abstract void setSearchDepth(int paramInt);
/*    */ 
/*    */   public abstract void setQuickFailDepth(int paramInt);
/*    */ 
/*    */   public abstract boolean canPathfindNice(PathPriority paramPathPriority, float paramFloat, int paramInt1, int paramInt2);
/*    */ 
/*    */   public static enum PathPriority
/*    */   {
/* 11 */     LOW, MEDIUM, HIGH;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.IPathSource
 * JD-Core Version:    0.6.2
 */