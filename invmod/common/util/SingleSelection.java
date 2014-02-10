/*    */ package invmod.common.util;
/*    */ 
/*    */ public class SingleSelection<T>
/*    */   implements ISelect<T>
/*    */ {
/*    */   private T object;
/*    */ 
/*    */   public SingleSelection(T object)
/*    */   {
/*  7 */     this.object = object;
/*    */   }
/*    */ 
/*    */   public T selectNext()
/*    */   {
/* 13 */     return this.object;
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 22 */     return this.object.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.SingleSelection
 * JD-Core Version:    0.6.2
 */