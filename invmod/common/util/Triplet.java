/*    */ package invmod.common.util;
/*    */ 
/*    */ public class Triplet<T, U, V>
/*    */ {
/*    */   private T val1;
/*    */   private U val2;
/*    */   private V val3;
/*    */ 
/*    */   public Triplet(T val1, U val2, V val3)
/*    */   {
/*  7 */     this.val1 = val1;
/*  8 */     this.val2 = val2;
/*  9 */     this.val3 = val3;
/*    */   }
/*    */ 
/*    */   public T getVal1()
/*    */   {
/* 14 */     return this.val1;
/*    */   }
/*    */ 
/*    */   public U getVal2()
/*    */   {
/* 19 */     return this.val2;
/*    */   }
/*    */ 
/*    */   public V getVal3()
/*    */   {
/* 24 */     return this.val3;
/*    */   }
/*    */ 
/*    */   public void setVal1(T entry)
/*    */   {
/* 29 */     this.val1 = entry;
/*    */   }
/*    */ 
/*    */   public void setVal2(U value)
/*    */   {
/* 34 */     this.val2 = value;
/*    */   }
/*    */ 
/*    */   public void setVal3(V value)
/*    */   {
/* 39 */     this.val3 = value;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.Triplet
 * JD-Core Version:    0.6.2
 */