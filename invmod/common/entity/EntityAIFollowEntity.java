/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.src.nm;
/*    */ import of;
/*    */ 
/*    */ public class EntityAIFollowEntity<T extends of> extends EntityAIMoveToEntity<T>
/*    */ {
/*    */   private float followDistanceSq;
/*    */ 
/*    */   public EntityAIFollowEntity(EntityIMLiving entity, float followDistance)
/*    */   {
/* 12 */     this(entity, EntityLivingBase.class, followDistance);
/*    */   }
/*    */ 
/*    */   public EntityAIFollowEntity(EntityIMLiving entity, Class<? extends T> target, float followDistance)
/*    */   {
/* 17 */     super(entity, target);
/* 18 */     this.followDistanceSq = (followDistance * followDistance);
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 24 */     getEntity().onFollowingEntity(getTarget());
/* 25 */     super.startExecuting();
/*    */   }
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 31 */     getEntity().onFollowingEntity(null);
/* 32 */     super.resetTask();
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 38 */     super.updateTask();
/* 39 */     nm entity = getTarget();
/* 40 */     if (getEntity().getDistanceSq(entity.u, entity.E.b, entity.w) < this.followDistanceSq)
/* 41 */       getEntity().getNavigatorNew().haltForTick();
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIFollowEntity
 * JD-Core Version:    0.6.2
 */