/*    */ package invmod.common.util;
/*    */ 
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.util.AABBPool;
/*    */ 
/*    */ public class Distance
/*    */ {
/*    */   public static double distanceBetween(IPosition pos1, IPosition pos2)
/*    */   {
/* 11 */     double dX = pos2.getXCoord() - pos1.getXCoord();
/* 12 */     double dY = pos2.getYCoord() - pos1.getYCoord();
/* 13 */     double dZ = pos2.getZCoord() - pos1.getZCoord();
/* 14 */     return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*    */   }
/*    */ 
/*    */   public static double distanceBetween(IPosition pos1, AABBPool pos2)
/*    */   {
/* 19 */     double dX = pos2.listAABB - pos1.getXCoord();
/* 20 */     double dY = pos2.nextPoolIndex - pos1.getYCoord();
/* 21 */     double dZ = pos2.maxPoolIndex - pos1.getZCoord();
/* 22 */     return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*    */   }
/*    */ 
/*    */   public static double distanceBetween(IPosition pos1, double x2, double y2, double z2)
/*    */   {
/* 27 */     double dX = x2 - pos1.getXCoord();
/* 28 */     double dY = y2 - pos1.getYCoord();
/* 29 */     double dZ = z2 - pos1.getZCoord();
/* 30 */     return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*    */   }
/*    */ 
/*    */   public static double distanceBetween(double x1, double y1, double z1, double x2, double y2, double z2)
/*    */   {
/* 35 */     double dX = x2 - x1;
/* 36 */     double dY = y2 - y1;
/* 37 */     double dZ = z2 - z1;
/* 38 */     return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*    */   }
/*    */ 
/*    */   public static double distanceBetween(nm entity, AABBPool pos2)
/*    */   {
/* 43 */     double dX = pos2.listAABB - entity.u;
/* 44 */     double dY = pos2.nextPoolIndex - entity.v;
/* 45 */     double dZ = pos2.maxPoolIndex - entity.w;
/* 46 */     return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.Distance
 * JD-Core Version:    0.6.2
 */