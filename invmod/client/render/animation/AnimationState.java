/*     */ package invmod.client.render.animation;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class AnimationState<T extends Enum<T>>
/*     */ {
/*     */   private Animation<T> animation;
/*     */   private float currentTime;
/*     */   private float animationSpeed;
/*     */   private boolean pauseAtTransition;
/*     */   private boolean pauseAfterSetAction;
/*     */   private boolean isPaused;
/*     */   private AnimationPhaseInfo currentPhase;
/*     */   private Transition nextTransition;
/*     */   private AnimationAction setAction;
/*     */ 
/*     */   public AnimationState(Animation<T> animation)
/*     */   {
/*  20 */     this(animation, 0.0F);
/*     */   }
/*     */ 
/*     */   public AnimationState(Animation<T> animation, float startTime)
/*     */   {
/*  25 */     this.animation = animation;
/*  26 */     this.pauseAtTransition = false;
/*  27 */     this.pauseAfterSetAction = false;
/*  28 */     this.isPaused = false;
/*  29 */     this.currentTime = startTime;
/*  30 */     this.animationSpeed = animation.getBaseSpeed();
/*  31 */     updatePhase(this.currentTime);
/*  32 */     this.nextTransition = this.currentPhase.getDefaultTransition();
/*  33 */     this.setAction = this.nextTransition.getNewAction();
/*     */   }
/*     */ 
/*     */   public void setNewAction(AnimationAction action)
/*     */   {
/*  38 */     this.setAction = action;
/*  39 */     updateTransition(action);
/*  40 */     this.pauseAtTransition = false;
/*  41 */     this.pauseAfterSetAction = false;
/*  42 */     this.isPaused = false;
/*     */   }
/*     */ 
/*     */   public void setNewAction(AnimationAction action, float animationSpeedFactor, boolean pauseAfterAction)
/*     */   {
/*  47 */     setNewAction(action);
/*  48 */     setAnimationSpeed(animationSpeedFactor);
/*  49 */     setPauseAfterSetAction(pauseAfterAction);
/*     */   }
/*     */ 
/*     */   public void setPauseAfterCurrentAction(boolean shouldPause)
/*     */   {
/*  54 */     this.pauseAtTransition = shouldPause;
/*     */   }
/*     */ 
/*     */   public void setPauseAfterSetAction(boolean shouldPause)
/*     */   {
/*  59 */     this.pauseAfterSetAction = shouldPause;
/*     */   }
/*     */ 
/*     */   public void setPaused(boolean isPaused)
/*     */   {
/*  64 */     this.isPaused = isPaused;
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  69 */     if (this.isPaused) {
/*  70 */       return;
/*     */     }
/*  72 */     this.currentTime += this.animationSpeed;
/*  73 */     if (this.currentTime >= this.nextTransition.getSourceTime())
/*     */     {
/*  75 */       if ((this.setAction == this.currentPhase.getAction()) && (this.pauseAfterSetAction))
/*     */       {
/*  77 */         this.pauseAfterSetAction = false;
/*  78 */         this.pauseAtTransition = true;
/*     */       }
/*     */ 
/*  81 */       if (!this.pauseAtTransition)
/*     */       {
/*  83 */         float overflow = this.currentTime - this.nextTransition.getSourceTime();
/*  84 */         this.currentTime = this.nextTransition.getDestTime();
/*  85 */         updatePhase(this.currentTime);
/*  86 */         float phaseLength = this.currentPhase.getTimeEnd() - this.currentPhase.getTimeBegin();
/*  87 */         if (overflow > phaseLength) {
/*  88 */           overflow = phaseLength;
/*     */         }
/*  90 */         updateTransition(this.setAction);
/*  91 */         this.currentTime += overflow;
/*  92 */         this.isPaused = false;
/*     */       }
/*     */       else
/*     */       {
/*  96 */         this.currentTime = this.nextTransition.getSourceTime();
/*  97 */         this.isPaused = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public AnimationAction getNextSetAction()
/*     */   {
/* 104 */     return this.setAction;
/*     */   }
/*     */ 
/*     */   public AnimationAction getCurrentAction()
/*     */   {
/* 109 */     return this.currentPhase.getAction();
/*     */   }
/*     */ 
/*     */   public float getCurrentAnimationTime()
/*     */   {
/* 114 */     return this.currentTime;
/*     */   }
/*     */ 
/*     */   public float getCurrentAnimationTimeInterp(float parTick)
/*     */   {
/* 119 */     if (this.isPaused) {
/* 120 */       parTick = 0.0F;
/*     */     }
/* 122 */     float frameTime = this.currentTime + parTick * this.animationSpeed;
/* 123 */     if (frameTime < this.nextTransition.getSourceTime())
/*     */     {
/* 125 */       return frameTime;
/*     */     }
/*     */ 
/* 129 */     float overFlow = frameTime - this.nextTransition.getSourceTime();
/* 130 */     float phaseLength = this.currentPhase.getTimeEnd() - this.currentPhase.getTimeBegin();
/* 131 */     if (overFlow > phaseLength) {
/* 132 */       overFlow = phaseLength;
/*     */     }
/* 134 */     return this.nextTransition.getDestTime() + overFlow;
/*     */   }
/*     */ 
/*     */   public float getCurrentAnimationPercent()
/*     */   {
/* 140 */     return (this.currentTime - this.currentPhase.getTimeBegin()) / (this.currentPhase.getTimeEnd() - this.currentPhase.getTimeBegin());
/*     */   }
/*     */ 
/*     */   public float getAnimationSpeed()
/*     */   {
/* 145 */     return this.animationSpeed;
/*     */   }
/*     */ 
/*     */   public Transition getNextTransition()
/*     */   {
/* 150 */     return this.nextTransition;
/*     */   }
/*     */ 
/*     */   public float getAnimationPeriod()
/*     */   {
/* 155 */     return this.animation.getAnimationPeriod();
/*     */   }
/*     */ 
/*     */   public float getBaseAnimationTime()
/*     */   {
/* 160 */     return this.animation.getBaseSpeed();
/*     */   }
/*     */ 
/*     */   public List<AnimationPhaseInfo> getAnimationPhases()
/*     */   {
/* 165 */     return this.animation.getAnimationPhases();
/*     */   }
/*     */ 
/*     */   public void setAnimationSpeed(float speedFactor)
/*     */   {
/* 170 */     this.animationSpeed = (this.animation.getBaseSpeed() * speedFactor);
/*     */   }
/*     */ 
/*     */   private boolean updateTransition(AnimationAction action)
/*     */   {
/* 175 */     if (this.currentPhase.hasTransition(action))
/*     */     {
/* 177 */       this.nextTransition = this.currentPhase.getTransition(action);
/* 178 */       if (this.currentTime > this.nextTransition.getSourceTime())
/*     */       {
/* 180 */         this.nextTransition = this.currentPhase.getDefaultTransition();
/* 181 */         return false;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 186 */       this.nextTransition = this.currentPhase.getDefaultTransition();
/*     */     }
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */   private void updatePhase(float time)
/*     */   {
/* 193 */     this.currentPhase = findPhase(time);
/* 194 */     if (this.currentPhase == null)
/*     */     {
/* 196 */       this.currentTime = 0.0F;
/* 197 */       this.currentPhase = ((AnimationPhaseInfo)this.animation.getAnimationPhases().get(0));
/*     */     }
/*     */   }
/*     */ 
/*     */   private AnimationPhaseInfo findPhase(float time)
/*     */   {
/* 203 */     for (AnimationPhaseInfo phase : this.animation.getAnimationPhases())
/*     */     {
/* 205 */       if ((phase.getTimeBegin() <= time) && (phase.getTimeEnd() > time))
/* 206 */         return phase;
/*     */     }
/* 208 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.animation.AnimationState
 * JD-Core Version:    0.6.2
 */