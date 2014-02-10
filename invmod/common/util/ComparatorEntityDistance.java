/*    */ package invmod.common.util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class ComparatorEntityDistance
/*    */   implements Comparator<nm>
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */ 
/*    */   public ComparatorEntityDistance(double x, double y, double z)
/*    */   {
/* 14 */     this.x = x;
/* 15 */     this.y = y;
/* 16 */     this.z = z;
/*    */   }
/*    */ 
/*    */   public int compare(net.minecraft.src.nm entity1, net.minecraft.src.nm entity2)
/*    */   {
/* 22 */     double d1 = (this.x - entity1.u) * (this.x - entity1.u) + (this.y - entity1.v) * (this.y - entity1.v) + (this.z - entity1.w) * (this.z - entity1.w);
/* 23 */     double d2 = (this.x - entity2.u) * (this.x - entity2.u) + (this.y - entity2.v) * (this.y - entity2.v) + (this.z - entity2.w) * (this.z - entity2.w);
/* 24 */     if (d1 > d2)
/* 25 */       return -1;
/* 26 */     if (d1 < d2) {
/* 27 */       return 1;
/*    */     }
/* 29 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.ComparatorEntityDistance
 * JD-Core Version:    0.6.2
 */