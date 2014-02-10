/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import of;
/*    */ 
/*    */ public class EntityAIRallyBehindEntity<T extends of,  extends ILeader> extends EntityAIFollowEntity<T>
/*    */ {
/*    */   private static final float DEFAULT_FOLLOW_DISTANCE = 5.0F;
/*    */ 
/*    */   public EntityAIRallyBehindEntity(EntityIMLiving entity, Class<T> leader)
/*    */   {
/* 11 */     this(entity, leader, 5.0F);
/*    */   }
/*    */ 
/*    */   public EntityAIRallyBehindEntity(EntityIMLiving entity, Class<T> leader, float followDistance)
/*    */   {
/* 16 */     super(entity, leader, followDistance);
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 22 */     return (getEntity().readyToRally()) && (super.shouldExecute());
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 28 */     return (getEntity().readyToRally()) && (super.continueExecuting());
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 34 */     super.updateTask();
/* 35 */     if (getEntity().readyToRally())
/*    */     {
/* 37 */       EntityLivingBase leader = (EntityLivingBase)getTarget();
/* 38 */       if (((ILeader)leader).isMartyr())
/* 39 */         rally(leader);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void rally(T leader)
/*    */   {
/* 45 */     getEntity().rally(leader);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIRallyBehindEntity
 * JD-Core Version:    0.6.2
 */