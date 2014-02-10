/*    */ package invmod.client.render.animation;
/*    */ 
/*    */ public class Transition
/*    */ {
/*    */   private AnimationAction newAction;
/*    */   private float sourceTime;
/*    */   private float destTime;
/*    */ 
/*    */   public Transition(AnimationAction newAction, float sourceTime, float destTime)
/*    */   {
/* 11 */     this.newAction = newAction;
/* 12 */     this.sourceTime = sourceTime;
/* 13 */     this.destTime = destTime;
/*    */   }
/*    */ 
/*    */   public AnimationAction getNewAction()
/*    */   {
/* 18 */     return this.newAction;
/*    */   }
/*    */ 
/*    */   public float getSourceTime()
/*    */   {
/* 23 */     return this.sourceTime;
/*    */   }
/*    */ 
/*    */   public float getDestTime()
/*    */   {
/* 28 */     return this.destTime;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.animation.Transition
 * JD-Core Version:    0.6.2
 */