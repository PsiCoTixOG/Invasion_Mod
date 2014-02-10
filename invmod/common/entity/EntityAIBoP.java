/*     */ package invmod.common.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityAIBoP extends EntityAIFollowParent
/*     */ {
/*     */   private static final int PATIENCE = 500;
/*     */   private EntityIMFlying theEntity;
/*     */   private int timeWithGoal;
/*     */   private int timeWithTarget;
/*     */   private int patienceTime;
/*     */   private float lastHealth;
/*     */   private Goal lastGoal;
/*     */   private EntityLeashKnot lastTarget;
/*     */ 
/*     */   public EntityAIBoP(EntityIMFlying entity)
/*     */   {
/*  20 */     this.theEntity = entity;
/*  21 */     this.timeWithGoal = 0;
/*  22 */     this.patienceTime = 0;
/*  23 */     this.lastHealth = entity.getHealth();
/*  24 */     this.lastGoal = entity.getAIGoal();
/*  25 */     this.lastTarget = entity.m();
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  31 */     return true;
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  37 */     this.timeWithGoal = 0;
/*  38 */     this.patienceTime = 0;
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  45 */     this.timeWithGoal += 1;
/*  46 */     if (this.theEntity.getAIGoal() != this.lastGoal)
/*     */     {
/*  48 */       this.lastGoal = this.theEntity.getAIGoal();
/*  49 */       this.timeWithGoal = 0;
/*     */     }
/*     */ 
/*  52 */     this.timeWithTarget += 1;
/*  53 */     if (this.theEntity.m() != this.lastTarget)
/*     */     {
/*  55 */       this.lastTarget = this.theEntity.m();
/*  56 */       this.timeWithTarget = 0;
/*     */     }
/*     */ 
/*  60 */     if (this.theEntity.m() == null)
/*     */     {
/*  62 */       if (this.theEntity.getNexus() != null)
/*     */       {
/*  64 */         if (this.theEntity.getAIGoal() != Goal.BREAK_NEXUS) {
/*  65 */           this.theEntity.transitionAIGoal(Goal.BREAK_NEXUS);
/*     */         }
/*     */ 
/*     */       }
/*  69 */       else if (this.theEntity.getAIGoal() != Goal.CHILL)
/*     */       {
/*  71 */         this.theEntity.transitionAIGoal(Goal.CHILL);
/*  72 */         this.theEntity.getNavigatorNew().clearPath();
/*  73 */         this.theEntity.getNavigatorNew().setMovementType(INavigationFlying.MoveType.PREFER_WALKING);
/*  74 */         this.theEntity.getNavigatorNew().setLandingPath();
/*     */       }
/*     */ 
/*     */     }
/*  78 */     else if ((this.theEntity.getAIGoal() == Goal.CHILL) || (this.theEntity.getAIGoal() == Goal.NONE))
/*     */     {
/*  80 */       chooseTargetAction(this.theEntity.m());
/*     */     }
/*     */ 
/*  84 */     if (this.theEntity.getAIGoal() != Goal.STAY_AT_RANGE)
/*     */     {
/*  88 */       if (this.theEntity.getAIGoal() == Goal.MELEE_TARGET)
/*     */       {
/*  90 */         if (this.timeWithGoal > 600)
/*     */         {
/*  92 */           this.theEntity.transitionAIGoal(Goal.STAY_AT_RANGE);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void chooseTargetAction(EntityLeashKnot target) {
/*  99 */     if (this.theEntity.getMoveState() != MoveState.FLYING)
/*     */     {
/* 101 */       if ((this.theEntity.d(target) < 10.0F) && (this.theEntity.q.s.nextFloat() > 0.3F))
/*     */       {
/* 103 */         this.theEntity.transitionAIGoal(Goal.MELEE_TARGET);
/* 104 */         return;
/*     */       }
/*     */     }
/* 107 */     this.theEntity.transitionAIGoal(Goal.STAY_AT_RANGE);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIBoP
 * JD-Core Version:    0.6.2
 */