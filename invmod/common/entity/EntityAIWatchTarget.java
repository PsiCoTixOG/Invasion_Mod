/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.entity.ai.EntityJumpHelper;
/*    */ 
/*    */ public class EntityAIWatchTarget extends EntityAIFollowParent
/*    */ {
/*    */   private EntityLivingBase theEntity;
/*    */ 
/*    */   public EntityAIWatchTarget(EntityLivingBase entity)
/*    */   {
/* 12 */     this.theEntity = entity;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 18 */     return this.theEntity.m() != null;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 24 */     this.theEntity.h().a(this.theEntity.m(), 2.0F, 2.0F);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIWatchTarget
 * JD-Core Version:    0.6.2
 */