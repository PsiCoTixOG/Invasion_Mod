/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.block.material.MaterialLogic;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntityAIStoop extends EntityAIFollowParent
/*    */ {
/*    */   private EntityIMLiving theEntity;
/*    */   private int updateTimer;
/*    */   private boolean stopStoop;
/*    */ 
/*    */   public EntityAIStoop(EntityIMLiving entity)
/*    */   {
/* 13 */     this.theEntity = entity;
/* 14 */     this.stopStoop = true;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 20 */     if (--this.updateTimer <= 0)
/*    */     {
/* 22 */       this.updateTimer = 10;
/* 23 */       if (this.theEntity.q.g(this.theEntity.getXCoord(), this.theEntity.getYCoord() + 2, this.theEntity.getZCoord()).blocksMovement()) {
/* 24 */         return true;
/*    */       }
/*    */     }
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 33 */     return !this.stopStoop;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 39 */     this.theEntity.setSneaking(true);
/* 40 */     this.stopStoop = false;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 46 */     if (--this.updateTimer <= 0)
/*    */     {
/* 48 */       this.updateTimer = 10;
/* 49 */       if (!this.theEntity.q.g(this.theEntity.getXCoord(), this.theEntity.getYCoord() + 2, this.theEntity.getZCoord()).blocksMovement())
/*    */       {
/* 51 */         this.theEntity.setSneaking(false);
/* 52 */         this.stopStoop = true;
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIStoop
 * JD-Core Version:    0.6.2
 */