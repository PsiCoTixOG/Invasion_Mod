/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.client.render.animation.AnimationAction;
/*    */ import invmod.client.render.animation.AnimationState;
/*    */ 
/*    */ public class MouthController
/*    */ {
/*    */   private EntityIMLiving theEntity;
/*    */   private AnimationState mouthState;
/*    */   private int mouthOpenTime;
/*    */ 
/*    */   public MouthController(EntityIMBird entity, AnimationState stateObject)
/*    */   {
/* 14 */     this.theEntity = entity;
/* 15 */     this.mouthState = stateObject;
/* 16 */     this.mouthOpenTime = 0;
/*    */   }
/*    */ 
/*    */   public void update()
/*    */   {
/* 21 */     if (this.mouthOpenTime > 0)
/*    */     {
/* 23 */       this.mouthOpenTime -= 1;
/* 24 */       ensureAnimation(this.mouthState, AnimationAction.MOUTH_OPEN, 1.0F, true);
/*    */     }
/*    */     else
/*    */     {
/* 28 */       ensureAnimation(this.mouthState, AnimationAction.MOUTH_CLOSE, 1.0F, true);
/*    */     }
/* 30 */     this.mouthState.update();
/*    */   }
/*    */ 
/*    */   public void setMouthState(int timeOpen)
/*    */   {
/* 40 */     this.mouthOpenTime = timeOpen;
/*    */   }
/*    */ 
/*    */   private void ensureAnimation(AnimationState state, AnimationAction action, float animationSpeed, boolean pauseAfterAction)
/*    */   {
/* 45 */     if (state.getNextSetAction() != action)
/*    */     {
/* 47 */       state.setNewAction(action, animationSpeed, pauseAfterAction);
/*    */     }
/*    */     else
/*    */     {
/* 51 */       state.setAnimationSpeed(animationSpeed);
/* 52 */       state.setPauseAfterSetAction(pauseAfterAction);
/* 53 */       state.setPaused(false);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.MouthController
 * JD-Core Version:    0.6.2
 */