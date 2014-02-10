/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ 
/*    */ public class EntityAIFlyingTackle extends EntityAIFollowParent
/*    */ {
/*    */   private EntityIMFlying theEntity;
/*    */   private int time;
/*    */ 
/*    */   public EntityAIFlyingTackle(EntityIMFlying entity)
/*    */   {
/* 14 */     this.theEntity = entity;
/* 15 */     this.time = 0;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 21 */     return this.theEntity.getAIGoal() == Goal.TACKLE_TARGET;
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 27 */     EntityLeashKnot target = this.theEntity.m();
/* 28 */     if ((target == null) || (target.isDead))
/*    */     {
/* 30 */       this.theEntity.transitionAIGoal(Goal.NONE);
/* 31 */       return false;
/*    */     }
/*    */ 
/* 34 */     if (this.theEntity.getAIGoal() != Goal.TACKLE_TARGET) {
/* 35 */       return false;
/*    */     }
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 43 */     this.time = 0;
/* 44 */     EntityLeashKnot target = this.theEntity.m();
/* 45 */     if (target != null)
/*    */     {
/* 47 */       this.theEntity.getNavigatorNew().setMovementType(INavigationFlying.MoveType.PREFER_WALKING);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 54 */     if (this.theEntity.getMoveState() != MoveState.FLYING)
/*    */     {
/* 56 */       this.theEntity.transitionAIGoal(Goal.MELEE_TARGET);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIFlyingTackle
 * JD-Core Version:    0.6.2
 */