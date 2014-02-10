/*    */ package invmod.client;
/*    */ 
/*    */ public class BowHackHandler
/*    */ {
/*    */   private int bowDrawTime;
/*    */   private boolean bowDrawing;
/*    */ 
/*    */   public void onUpdate()
/*    */   {
/* 10 */     if (this.bowDrawing)
/*    */     {
/* 12 */       this.bowDrawTime += 50;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setBowDrawing(boolean flag)
/*    */   {
/* 18 */     this.bowDrawing = flag;
/*    */   }
/*    */ 
/*    */   public void setBowReleased()
/*    */   {
/* 23 */     this.bowDrawing = false;
/* 24 */     this.bowDrawTime = 0;
/*    */   }
/*    */ 
/*    */   public int getDrawTimeLeft()
/*    */   {
/* 29 */     return this.bowDrawTime;
/*    */   }
/*    */ 
/*    */   public boolean isBowDrawing()
/*    */   {
/* 34 */     return this.bowDrawing;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.BowHackHandler
 * JD-Core Version:    0.6.2
 */