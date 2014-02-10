/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.util.IPosition;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ 
/*    */ public class EntityAIWanderIM extends EntityAIFollowParent
/*    */ {
/*    */   private static final int MIN_HORIZONTAL_PATH = 1;
/*    */   private static final int MAX_HORIZONTAL_PATH = 6;
/*    */   private static final int MAX_VERTICAL_PATH = 4;
/*    */   private EntityIMLiving theEntity;
/*    */   private IPosition movePosition;
/*    */ 
/*    */   public EntityAIWanderIM(EntityIMLiving entity)
/*    */   {
/* 17 */     this.theEntity = entity;
/* 18 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 32 */     if (this.theEntity.aC().nextInt(120) == 0)
/*    */     {
/* 34 */       int x = this.theEntity.getXCoord() + this.theEntity.aC().nextInt(13) - 6;
/* 35 */       int z = this.theEntity.getZCoord() + this.theEntity.aC().nextInt(13) - 6;
/* 36 */       Path path = this.theEntity.getNavigatorNew().getPathTowardsXZ(x, z, 1, 6, 4);
/* 37 */       if (path != null)
/*    */       {
/* 39 */         this.theEntity.getNavigatorNew().setPath(path, this.theEntity.getMoveSpeedStat());
/* 40 */         return true;
/*    */       }
/*    */     }
/*    */ 
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 53 */     return (!this.theEntity.getNavigatorNew().noPath()) && (this.theEntity.getNavigatorNew().getStuckTime() < 40);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIWanderIM
 * JD-Core Version:    0.6.2
 */