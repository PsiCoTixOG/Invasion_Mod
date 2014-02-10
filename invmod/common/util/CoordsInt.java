/*    */ package invmod.common.util;
/*    */ 
/*    */ public class CoordsInt
/*    */   implements IPosition
/*    */ {
/* 34 */   public static final int[] offsetAdjX = { 1, -1, 0, 0 };
/* 35 */   public static final int[] offsetAdjZ = { 0, 0, 1, -1 };
/*    */ 
/* 41 */   public static final int[] offsetAdj2X = { 2, 2, -1, -1, 1, 0, 0, 1 };
/* 42 */   public static final int[] offsetAdj2Z = { 0, 1, 1, 0, 2, 2, -1, -1 };
/*    */ 
/* 47 */   public static final int[] offsetRing1X = { 1, 0, -1, -1, -1, 0, 1, 1 };
/* 48 */   public static final int[] offsetRing1Z = { 1, 1, 1, 0, -1, -1, -1, 0 };
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */ 
/*    */   public CoordsInt(int x, int y, int z)
/*    */   {
/*  7 */     this.x = x;
/*  8 */     this.y = y;
/*  9 */     this.z = z;
/*    */   }
/*    */ 
/*    */   public int getXCoord()
/*    */   {
/* 15 */     return this.x;
/*    */   }
/*    */ 
/*    */   public int getYCoord()
/*    */   {
/* 21 */     return this.y;
/*    */   }
/*    */ 
/*    */   public int getZCoord()
/*    */   {
/* 27 */     return this.z;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.CoordsInt
 * JD-Core Version:    0.6.2
 */