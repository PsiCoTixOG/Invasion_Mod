/*    */ package invmod.common.entity;
/*    */ 
/*    */ import of;
/*    */ 
/*    */ public class EntityAITargetOnNoNexusPath extends EntityAISimpleTarget
/*    */ {
/*  7 */   private final float PATH_DISTANCE_TRIGGER = 4.0F;
/*    */ 
/*    */   public EntityAITargetOnNoNexusPath(EntityIMLiving entity, Class<? extends of> targetType, float distance)
/*    */   {
/* 11 */     super(entity, targetType, distance);
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 17 */     if ((getEntity().getAIGoal() == Goal.BREAK_NEXUS) && (getEntity().getNavigatorNew().getLastPathDistanceToTarget() > 4.0F)) {
/* 18 */       return super.shouldExecute();
/*    */     }
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 26 */     if ((getEntity().getAIGoal() == Goal.BREAK_NEXUS) && (getEntity().getNavigatorNew().getLastPathDistanceToTarget() > 4.0F)) {
/* 27 */       return super.continueExecuting();
/*    */     }
/* 29 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAITargetOnNoNexusPath
 * JD-Core Version:    0.6.2
 */