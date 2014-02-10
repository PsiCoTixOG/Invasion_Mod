/*   */ package invmod.common.entity;
/*   */ 
/*   */ import net.minecraft.src.nm;
/*   */ 
/*   */ public abstract interface INavigationFlying extends INavigation
/*   */ {
/*   */   public abstract void setMovementType(MoveType paramMoveType);
/*   */ 
/*   */   public abstract void setLandingPath();
/*   */ 
/*   */   public abstract void setCirclingPath(nm paramnm, float paramFloat1, float paramFloat2);
/*   */ 
/*   */   public abstract void setCirclingPath(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat1, float paramFloat2);
/*   */ 
/*   */   public abstract float getDistanceToCirclingRadius();
/*   */ 
/*   */   public abstract boolean isCircling();
/*   */ 
/*   */   public abstract void setFlySpeed(float paramFloat);
/*   */ 
/*   */   public abstract void setPitchBias(float paramFloat1, float paramFloat2);
/*   */ 
/*   */   public abstract void enableDirectTarget(boolean paramBoolean);
/*   */ 
/*   */   public static enum MoveType
/*   */   {
/* 9 */     PREFER_WALKING, MIXED, PREFER_FLYING;
/*   */   }
/*   */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.INavigationFlying
 * JD-Core Version:    0.6.2
 */