/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.client.render.animation.AnimationAction;
/*     */ import invmod.client.render.animation.AnimationState;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class LegController
/*     */ {
/*     */   private EntityIMBird theEntity;
/*     */   private AnimationState animationRun;
/*     */   private int timeAttacking;
/*     */   private float flapEffort;
/*     */   private float[] flapEffortSamples;
/*     */   private int sampleIndex;
/*     */ 
/*     */   public LegController(EntityIMBird entity, AnimationState stateObject)
/*     */   {
/*  17 */     this.theEntity = entity;
/*  18 */     this.animationRun = stateObject;
/*  19 */     this.timeAttacking = 0;
/*  20 */     this.flapEffort = 1.0F;
/*  21 */     this.flapEffortSamples = new float[] { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F };
/*  22 */     this.sampleIndex = 0;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  27 */     AnimationAction currAnimation = this.animationRun.getCurrentAction();
/*  28 */     if (this.theEntity.getMoveState() == MoveState.RUNNING)
/*     */     {
/*  30 */       double dX = this.theEntity.posX - this.theEntity.lastTickPosX;
/*  31 */       double dZ = this.theEntity.posZ - this.theEntity.lastTickPosZ;
/*  32 */       double dist = Math.sqrt(dX * dX + dZ * dZ);
/*  33 */       float speed = 0.2F + (float)dist * 1.3F;
/*     */ 
/*  35 */       if (this.animationRun.getNextSetAction() != AnimationAction.RUN)
/*     */       {
/*  37 */         if (dist >= 1.E-005D)
/*     */         {
/*  39 */           if (currAnimation == AnimationAction.STAND)
/*     */           {
/*  41 */             ensureAnimation(this.animationRun, AnimationAction.STAND_TO_RUN, speed, false);
/*     */           }
/*  43 */           else if (currAnimation == AnimationAction.STAND_TO_RUN)
/*     */           {
/*  45 */             ensureAnimation(this.animationRun, AnimationAction.RUN, speed, false);
/*     */           }
/*     */           else
/*     */           {
/*  50 */             ensureAnimation(this.animationRun, AnimationAction.STAND, 1.0F, true);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  56 */         this.animationRun.setAnimationSpeed(speed);
/*  57 */         if (dist < 1.E-005D)
/*     */         {
/*  59 */           ensureAnimation(this.animationRun, AnimationAction.STAND, 0.2F, true);
/*     */         }
/*     */       }
/*     */     }
/*  63 */     else if (this.theEntity.getMoveState() == MoveState.STANDING)
/*     */     {
/*  65 */       ensureAnimation(this.animationRun, AnimationAction.STAND, 1.0F, true);
/*     */     }
/*  67 */     else if (this.theEntity.getMoveState() == MoveState.FLYING)
/*     */     {
/*  69 */       if (this.theEntity.getClawsForward())
/*     */       {
/*  71 */         if (currAnimation == AnimationAction.STAND)
/*     */         {
/*  73 */           ensureAnimation(this.animationRun, AnimationAction.LEGS_CLAW_ATTACK_P1, 1.5F, true);
/*     */         }
/*  75 */         else if (this.animationRun.getNextSetAction() != AnimationAction.LEGS_CLAW_ATTACK_P1)
/*     */         {
/*  77 */           ensureAnimation(this.animationRun, AnimationAction.STAND, 1.5F, true);
/*     */         }
/*     */       }
/*  80 */       else if (((this.theEntity.getFlyState() == FlyState.FLYING) || (this.theEntity.getFlyState() == FlyState.LANDING)) && (currAnimation != AnimationAction.LEGS_RETRACT))
/*     */       {
/*  82 */         if (currAnimation == AnimationAction.STAND)
/*     */         {
/*  84 */           ensureAnimation(this.animationRun, AnimationAction.LEGS_RETRACT, 1.0F, true);
/*     */         }
/*  86 */         else if (currAnimation == AnimationAction.LEGS_CLAW_ATTACK_P1)
/*     */         {
/*  88 */           ensureAnimation(this.animationRun, AnimationAction.LEGS_CLAW_ATTACK_P2, 1.0F, true);
/*     */         }
/*     */         else
/*     */         {
/*  92 */           ensureAnimation(this.animationRun, AnimationAction.STAND, 1.0F, true);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  97 */     this.animationRun.update();
/*     */   }
/*     */ 
/*     */   private void ensureAnimation(AnimationState state, AnimationAction action, float animationSpeed, boolean pauseAfterAction)
/*     */   {
/* 102 */     if (state.getNextSetAction() != action)
/*     */     {
/* 104 */       state.setNewAction(action, animationSpeed, pauseAfterAction);
/*     */     }
/*     */     else
/*     */     {
/* 108 */       state.setAnimationSpeed(animationSpeed);
/* 109 */       state.setPauseAfterSetAction(pauseAfterAction);
/* 110 */       state.setPaused(false);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.LegController
 * JD-Core Version:    0.6.2
 */