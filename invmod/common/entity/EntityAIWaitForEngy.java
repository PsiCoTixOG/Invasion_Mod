/*    */ package invmod.common.entity;
/*    */ 
/*    */ public class EntityAIWaitForEngy extends EntityAIFollowEntity<EntityIMPigEngy>
/*    */ {
/*  5 */   private final float PATH_DISTANCE_TRIGGER = 4.0F;
/*    */   private boolean canHelp;
/*    */ 
/*    */   public EntityAIWaitForEngy(EntityIMLiving entity, float followDistance, boolean canHelp)
/*    */   {
/* 11 */     super(entity, EntityIMPigEngy.class, followDistance);
/* 12 */     this.canHelp = canHelp;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 18 */     super.updateTask();
/* 19 */     if (this.canHelp)
/*    */     {
/* 21 */       ((EntityIMPigEngy)getTarget()).supportForTick(getEntity(), 1.0F);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIWaitForEngy
 * JD-Core Version:    0.6.2
 */