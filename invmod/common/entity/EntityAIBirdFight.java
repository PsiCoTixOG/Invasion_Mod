/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ import oe;
/*    */ 
/*    */ public class EntityAIBirdFight<T extends oe> extends EntityAIMeleeFight<T>
/*    */ {
/*    */   private EntityIMBird theEntity;
/*    */   private boolean wantsToRetreat;
/*    */   private boolean buffetedTarget;
/*    */ 
/*    */   public EntityAIBirdFight(EntityIMBird entity, Class<? extends T> targetClass, int attackDelay, float retreatHealthLossPercent)
/*    */   {
/* 16 */     super(entity, targetClass, attackDelay, retreatHealthLossPercent);
/* 17 */     this.theEntity = entity;
/* 18 */     this.wantsToRetreat = false;
/* 19 */     this.buffetedTarget = false;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 25 */     if (getAttackTime() == 0)
/*    */     {
/* 27 */       this.theEntity.setAttackingWithWings(isInStartMeleeRange());
/*    */     }
/* 29 */     super.updateTask();
/*    */   }
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 35 */     this.theEntity.setAttackingWithWings(false);
/* 36 */     super.resetTask();
/*    */   }
/*    */ 
/*    */   public void updatePath()
/*    */   {
/* 42 */     INavigationFlying nav = this.theEntity.getNavigatorNew();
/* 43 */     nm target = this.theEntity.m();
/* 44 */     if (target != nav.getTargetEntity())
/*    */     {
/* 46 */       nav.clearPath();
/* 47 */       nav.setMovementType(INavigationFlying.MoveType.PREFER_WALKING);
/* 48 */       Path path = nav.getPathToEntity(target, 0.0F);
/* 49 */       if ((path != null) && (path.getCurrentPathLength() > 1.6D * this.theEntity.d(target)))
/*    */       {
/* 51 */         nav.setMovementType(INavigationFlying.MoveType.MIXED);
/*    */       }
/* 53 */       nav.autoPathToEntity(target);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void updateDisengage()
/*    */   {
/* 60 */     if (!this.wantsToRetreat)
/*    */     {
/* 62 */       if (shouldLeaveMelee())
/* 63 */         this.wantsToRetreat = true;
/*    */     }
/* 65 */     else if ((this.buffetedTarget) && (this.theEntity.getAIGoal() == Goal.MELEE_TARGET))
/*    */     {
/* 67 */       this.theEntity.transitionAIGoal(Goal.LEAVE_MELEE);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void attackEntity(EntityLeashKnot target)
/*    */   {
/* 74 */     this.theEntity.doMeleeSound();
/* 75 */     super.attackEntity(target);
/* 76 */     if (this.wantsToRetreat)
/*    */     {
/* 78 */       doWingBuffetAttack(target);
/* 79 */       this.buffetedTarget = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   protected boolean isInStartMeleeRange()
/*    */   {
/* 85 */     EntityLeashKnot target = this.theEntity.m();
/* 86 */     if (target == null) {
/* 87 */       return false;
/*    */     }
/* 89 */     double d = this.theEntity.width + this.theEntity.getAttackRange() + 3.0D;
/* 90 */     return this.theEntity.getDistanceSq(target.posX, target.E.b, target.posZ) < d * d;
/*    */   }
/*    */ 
/*    */   protected void doWingBuffetAttack(EntityLeashKnot target)
/*    */   {
/* 95 */     int knockback = 2;
/* 96 */     target.addVelocity(-LongHashMapEntry.a(this.theEntity.rotationYaw * 3.141593F / 180.0F) * knockback * 0.5F, 0.4D, LongHashMapEntry.b(this.theEntity.rotationYaw * 3.141593F / 180.0F) * knockback * 0.5F);
/* 97 */     target.q.a(target, "damage.fallbig", 1.0F, 1.0F);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIBirdFight
 * JD-Core Version:    0.6.2
 */