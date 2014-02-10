/*    */ package invmod.common.util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class ComparatorDistanceFrom
/*    */   implements Comparator<IPosition>
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */ 
/*    */   public ComparatorDistanceFrom(double x, double y, double z)
/*    */   {
/* 12 */     this.x = x;
/* 13 */     this.y = y;
/* 14 */     this.z = z;
/*    */   }
/*    */ 
/*    */   public int compare(IPosition pos1, IPosition pos2)
/*    */   {
/* 20 */     double d1 = (this.x - pos1.getXCoord()) * (this.x - pos1.getXCoord()) + (this.y - pos1.getYCoord()) * (this.y - pos1.getYCoord()) + (this.z - pos1.getZCoord()) * (this.z - pos1.getZCoord());
/* 21 */     double d2 = (this.x - pos2.getXCoord()) * (this.x - pos2.getXCoord()) + (this.y - pos2.getYCoord()) * (this.y - pos2.getYCoord()) + (this.z - pos2.getZCoord()) * (this.z - pos2.getZCoord());
/* 22 */     if (d1 > d2)
/* 23 */       return -1;
/* 24 */     if (d1 < d2) {
/* 25 */       return 1;
/*    */     }
/* 27 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.ComparatorDistanceFrom
 * JD-Core Version:    0.6.2
 */