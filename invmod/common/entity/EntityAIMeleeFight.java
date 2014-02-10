/*     */ package invmod.common.entity;
/*     */ 
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.src.nm;
/*     */ import oe;
/*     */ 
/*     */ public class EntityAIMeleeFight<T extends oe> extends EntityAIMeleeAttack<T>
/*     */ {
/*     */   private EntityIMLiving theEntity;
/*     */   private int time;
/*     */   private float startingHealth;
/*     */   private int damageDealt;
/*     */   private int invulnCount;
/*     */   private float retreatHealthLossPercent;
/*     */ 
/*     */   public EntityAIMeleeFight(EntityIMLiving entity, Class<? extends T> targetClass, int attackDelay, float retreatHealthLossPercent)
/*     */   {
/*  17 */     super(entity, targetClass, attackDelay);
/*  18 */     this.theEntity = entity;
/*  19 */     this.time = 0;
/*  20 */     this.startingHealth = 0.0F;
/*  21 */     this.damageDealt = 0;
/*  22 */     this.invulnCount = 0;
/*  23 */     this.retreatHealthLossPercent = retreatHealthLossPercent;
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  29 */     nm target = this.theEntity.m();
/*  30 */     return (this.theEntity.getAIGoal() == Goal.MELEE_TARGET) && (target != null) && (target.getClass().isAssignableFrom(getTargetClass()));
/*     */   }
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  36 */     return ((this.theEntity.getAIGoal() == Goal.MELEE_TARGET) || (isWaitingForTransition())) && (this.theEntity.m() != null);
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  42 */     this.time = 0;
/*  43 */     this.startingHealth = this.theEntity.getHealth();
/*  44 */     this.damageDealt = 0;
/*  45 */     this.invulnCount = 0;
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  51 */     updateDisengage();
/*  52 */     updatePath();
/*  53 */     super.updateTask();
/*  54 */     if ((this.damageDealt > 0) || (this.startingHealth - this.theEntity.getHealth() > 0.0F))
/*  55 */       this.time += 1;
/*     */   }
/*     */ 
/*     */   public void updatePath()
/*     */   {
/*  60 */     INavigation nav = this.theEntity.getNavigatorNew();
/*  61 */     if (this.theEntity.m() != nav.getTargetEntity())
/*     */     {
/*  63 */       nav.clearPath();
/*  64 */       nav.autoPathToEntity(this.theEntity.m());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateDisengage()
/*     */   {
/*  70 */     if ((this.theEntity.getAIGoal() == Goal.MELEE_TARGET) && (shouldLeaveMelee()))
/*     */     {
/*  72 */       this.theEntity.transitionAIGoal(Goal.LEAVE_MELEE);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean isWaitingForTransition()
/*     */   {
/*  78 */     return (this.theEntity.getAIGoal() == Goal.LEAVE_MELEE) && (this.theEntity.getPrevAIGoal() == Goal.MELEE_TARGET);
/*     */   }
/*     */ 
/*     */   protected void attackEntity(EntityLeashKnot target)
/*     */   {
/*  84 */     float h = target.aM();
/*  85 */     super.attackEntity(target);
/*  86 */     h -= target.aM();
/*  87 */     if (h <= 0.0F)
/*     */     {
/*  89 */       this.invulnCount += 1;
/*     */     }
/*  91 */     this.damageDealt = ((int)(this.damageDealt + h));
/*     */   }
/*     */ 
/*     */   protected boolean shouldLeaveMelee()
/*     */   {
/*  96 */     float damageReceived = this.startingHealth - this.theEntity.getHealth();
/*  97 */     if ((this.time > 40) && (damageReceived > this.theEntity.getMaxHealth() * this.retreatHealthLossPercent)) {
/*  98 */       return true;
/*     */     }
/* 100 */     if ((this.time > 100) && (damageReceived - this.damageDealt > this.theEntity.getMaxHealth() * 0.66F * this.retreatHealthLossPercent)) {
/* 101 */       return true;
/*     */     }
/* 103 */     if (this.invulnCount >= 2) {
/* 104 */       return true;
/*     */     }
/* 106 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIMeleeFight
 * JD-Core Version:    0.6.2
 */