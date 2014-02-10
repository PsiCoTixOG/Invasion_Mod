/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ 
/*    */ public class EntityAIStabiliseFlying extends EntityAIFollowParent
/*    */ {
/*  8 */   private static int INITIAL_STABILISE_TIME = 50;
/*    */   private EntityIMFlying theEntity;
/*    */   private int time;
/*    */   private int stabiliseTime;
/*    */ 
/*    */   public EntityAIStabiliseFlying(EntityIMFlying entity, int stabiliseTime)
/*    */   {
/* 15 */     this.theEntity = entity;
/* 16 */     this.time = 0;
/* 17 */     this.stabiliseTime = stabiliseTime;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 23 */     return this.theEntity.getAIGoal() == Goal.STABILISE;
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 29 */     if (this.time >= this.stabiliseTime)
/*    */     {
/* 31 */       this.theEntity.transitionAIGoal(Goal.NONE);
/* 32 */       this.theEntity.getNavigatorNew().setPitchBias(0.0F, 0.0F);
/* 33 */       return false;
/*    */     }
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 41 */     this.time = 0;
/* 42 */     INavigationFlying nav = this.theEntity.getNavigatorNew();
/* 43 */     nav.clearPath();
/* 44 */     nav.setMovementType(INavigationFlying.MoveType.PREFER_FLYING);
/* 45 */     nav.setPitchBias(20.0F, 0.5F);
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 51 */     this.time += 1;
/* 52 */     if (this.time == INITIAL_STABILISE_TIME)
/*    */     {
/* 54 */       this.theEntity.getNavigatorNew().setPitchBias(0.0F, 0.0F);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIStabiliseFlying
 * JD-Core Version:    0.6.2
 */