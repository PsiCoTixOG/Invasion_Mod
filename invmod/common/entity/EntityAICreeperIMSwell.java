/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ 
/*    */ public class EntityAICreeperIMSwell extends EntityAIFollowParent
/*    */ {
/*    */   EntityIMCreeper theEntity;
/*    */   EntityLeashKnot targetEntity;
/*    */ 
/*    */   public EntityAICreeperIMSwell(EntityIMCreeper par1EntityCreeper)
/*    */   {
/* 13 */     this.theEntity = par1EntityCreeper;
/* 14 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 23 */     EntityLeashKnot entityliving = this.theEntity.m();
/* 24 */     return (this.theEntity.getCreeperState() > 0) || ((entityliving != null) && (this.theEntity.e(entityliving) < 9.0D));
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 33 */     this.theEntity.getNavigatorNew().clearPath();
/* 34 */     this.targetEntity = this.theEntity.m();
/*    */   }
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 43 */     this.targetEntity = null;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 52 */     if (this.targetEntity == null)
/*    */     {
/* 54 */       this.theEntity.setCreeperState(-1);
/* 55 */       return;
/*    */     }
/*    */ 
/* 58 */     if (this.theEntity.e(this.targetEntity) > 49.0D)
/*    */     {
/* 60 */       this.theEntity.setCreeperState(-1);
/* 61 */       return;
/*    */     }
/*    */ 
/* 64 */     if (!this.theEntity.l().a(this.targetEntity))
/*    */     {
/* 66 */       this.theEntity.setCreeperState(-1);
/* 67 */       return;
/*    */     }
/*    */ 
/* 71 */     this.theEntity.setCreeperState(1);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAICreeperIMSwell
 * JD-Core Version:    0.6.2
 */