/*    */ package invmod.common.entity;
/*    */ 
/*    */ import of;
/*    */ 
/*    */ public class EntityAILeaderTarget extends EntityAISimpleTarget
/*    */ {
/*    */   private final EntityIMLiving theEntity;
/*    */ 
/*    */   public EntityAILeaderTarget(EntityIMLiving entity, Class<? extends of> targetType, float distance)
/*    */   {
/* 11 */     this(entity, targetType, distance, true);
/*    */   }
/*    */ 
/*    */   public EntityAILeaderTarget(EntityIMLiving entity, Class<? extends of> targetType, float distance, boolean needsLos)
/*    */   {
/* 16 */     super(entity, targetType, distance, needsLos);
/* 17 */     this.theEntity = entity;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 23 */     if (!this.theEntity.readyToRally()) {
/* 24 */       return false;
/*    */     }
/* 26 */     return super.shouldExecute();
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAILeaderTarget
 * JD-Core Version:    0.6.2
 */