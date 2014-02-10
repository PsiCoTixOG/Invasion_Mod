/*    */ package invmod.common.util;
/*    */ 
/*    */ public class Pair<T, U>
/*    */ {
/*    */   private T val1;
/*    */   private U val2;
/*    */ 
/*    */   public Pair(T val1, U val2)
/*    */   {
/*  7 */     this.val1 = val1;
/*  8 */     this.val2 = val2;
/*    */   }
/*    */ 
/*    */   public T getVal1()
/*    */   {
/* 13 */     return this.val1;
/*    */   }
/*    */ 
/*    */   public U getVal2()
/*    */   {
/* 18 */     return this.val2;
/*    */   }
/*    */ 
/*    */   public void setVal1(T entry)
/*    */   {
/* 23 */     this.val1 = entry;
/*    */   }
/*    */ 
/*    */   public void setVal2(U value)
/*    */   {
/* 28 */     this.val2 = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.Pair
 * JD-Core Version:    0.6.2
 */