/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.client.render.animation.AnimationAction;
/*     */ import invmod.client.render.animation.AnimationState;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class WingController
/*     */ {
/*     */   private EntityIMBird theEntity;
/*     */   private AnimationState animationFlap;
/*     */   private int timeAttacking;
/*     */   private float flapEffort;
/*     */   private float[] flapEffortSamples;
/*     */   private int sampleIndex;
/*     */ 
/*     */   public WingController(EntityIMBird entity, AnimationState stateObject)
/*     */   {
/*  17 */     this.theEntity = entity;
/*  18 */     this.animationFlap = stateObject;
/*  19 */     this.timeAttacking = 0;
/*  20 */     this.flapEffort = 1.0F;
/*  21 */     this.flapEffortSamples = new float[] { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F };
/*  22 */     this.sampleIndex = 0;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  27 */     AnimationAction currAnimation = this.animationFlap.getCurrentAction();
/*  28 */     AnimationAction nextAnimation = this.animationFlap.getNextSetAction();
/*  29 */     boolean wingAttack = this.theEntity.isAttackingWithWings();
/*  30 */     if (!wingAttack)
/*  31 */       this.timeAttacking = 0;
/*     */     else {
/*  33 */       this.timeAttacking += 1;
/*     */     }
/*  35 */     if (this.theEntity.ticksExisted % 5 == 0)
/*     */     {
/*  37 */       if (++this.sampleIndex >= this.flapEffortSamples.length) {
/*  38 */         this.sampleIndex = 0;
/*     */       }
/*  40 */       float sample = this.theEntity.getThrustEffort();
/*  41 */       this.flapEffort -= this.flapEffortSamples[this.sampleIndex] / this.flapEffortSamples.length;
/*  42 */       this.flapEffort += sample / this.flapEffortSamples.length;
/*  43 */       this.flapEffortSamples[this.sampleIndex] = sample;
/*     */     }
/*     */ 
/*  46 */     if (this.theEntity.getFlyState() != FlyState.GROUNDED)
/*     */     {
/*  48 */       if (currAnimation == AnimationAction.WINGTUCK)
/*     */       {
/*  50 */         ensureAnimation(this.animationFlap, AnimationAction.WINGSPREAD, 2.2F, true);
/*     */       }
/*  54 */       else if (this.theEntity.isThrustOn())
/*     */       {
/*  56 */         ensureAnimation(this.animationFlap, AnimationAction.WINGFLAP, 2.0F * this.flapEffort, false);
/*     */       }
/*     */       else
/*     */       {
/*  60 */         ensureAnimation(this.animationFlap, AnimationAction.WINGGLIDE, 0.7F, false);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*  66 */       boolean wingsActive = false;
/*  67 */       if (this.theEntity.getMoveState() == MoveState.RUNNING)
/*     */       {
/*  69 */         if (currAnimation == AnimationAction.WINGTUCK)
/*     */         {
/*  71 */           ensureAnimation(this.animationFlap, AnimationAction.WINGSPREAD, 2.2F, true);
/*     */         }
/*     */         else
/*     */         {
/*  75 */           ensureAnimation(this.animationFlap, AnimationAction.WINGFLAP, 1.0F, false);
/*  76 */           if ((!wingAttack) && (currAnimation == AnimationAction.WINGSPREAD) && (this.animationFlap.getCurrentAnimationPercent() >= 0.65F))
/*     */           {
/*  78 */             this.animationFlap.setPaused(true);
/*     */           }
/*     */         }
/*  81 */         wingsActive = true;
/*     */       }
/*     */ 
/*  84 */       if (wingAttack)
/*     */       {
/*  86 */         float speed = (float)(1.0D / Math.min(this.timeAttacking / 40 * 0.6D + 0.4D, 1.0D));
/*  87 */         ensureAnimation(this.animationFlap, AnimationAction.WINGFLAP, speed, false);
/*  88 */         wingsActive = true;
/*     */       }
/*     */ 
/*  91 */       if (!wingsActive)
/*     */       {
/*  93 */         ensureAnimation(this.animationFlap, AnimationAction.WINGTUCK, 1.8F, true);
/*     */       }
/*     */     }
/*  96 */     this.animationFlap.update();
/*     */   }
/*     */ 
/*     */   private void ensureAnimation(AnimationState state, AnimationAction action, float animationSpeed, boolean pauseAfterAction)
/*     */   {
/* 101 */     if (state.getNextSetAction() != action)
/*     */     {
/* 103 */       state.setNewAction(action, animationSpeed, pauseAfterAction);
/*     */     }
/*     */     else
/*     */     {
/* 107 */       state.setAnimationSpeed(animationSpeed);
/* 108 */       state.setPauseAfterSetAction(pauseAfterAction);
/* 109 */       state.setPaused(false);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.WingController
 * JD-Core Version:    0.6.2
 */