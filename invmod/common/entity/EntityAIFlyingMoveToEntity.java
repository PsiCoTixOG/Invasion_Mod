/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.src.nm;
/*    */ 
/*    */ public class EntityAIFlyingMoveToEntity extends EntityAIFollowParent
/*    */ {
/*    */   private EntityIMFlying theEntity;
/*    */ 
/*    */   public EntityAIFlyingMoveToEntity(EntityIMFlying entity)
/*    */   {
/* 13 */     this.theEntity = entity;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 19 */     return (this.theEntity.getAIGoal() == Goal.GOTO_ENTITY) && (this.theEntity.m() != null);
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 25 */     INavigationFlying nav = this.theEntity.getNavigatorNew();
/* 26 */     nm target = this.theEntity.m();
/* 27 */     if (target != nav.getTargetEntity())
/*    */     {
/* 29 */       nav.clearPath();
/* 30 */       nav.setMovementType(INavigationFlying.MoveType.PREFER_WALKING);
/* 31 */       Path path = nav.getPathToEntity(target, 0.0F);
/* 32 */       if (path.getCurrentPathLength() > 2.0D * this.theEntity.d(target))
/*    */       {
/* 34 */         nav.setMovementType(INavigationFlying.MoveType.MIXED);
/*    */       }
/* 36 */       nav.autoPathToEntity(target);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIFlyingMoveToEntity
 * JD-Core Version:    0.6.2
 */