/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import oe;
/*    */ 
/*    */ public class EntityAIWingAttack extends EntityAIMeleeAttack
/*    */ {
/*    */   private EntityIMBird theEntity;
/*    */ 
/*    */   public EntityAIWingAttack(EntityIMBird entity, Class<? extends oe> targetClass, int attackDelay)
/*    */   {
/* 11 */     super(entity, targetClass, attackDelay);
/* 12 */     this.theEntity = entity;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 18 */     if (getAttackTime() == 0)
/*    */     {
/* 20 */       this.theEntity.setAttackingWithWings(isInStartMeleeRange());
/*    */     }
/* 22 */     super.updateTask();
/*    */   }
/*    */ 
/*    */   public void resetTask()
/*    */   {
/* 28 */     this.theEntity.setAttackingWithWings(false);
/*    */   }
/*    */ 
/*    */   protected boolean isInStartMeleeRange()
/*    */   {
/* 33 */     EntityLeashKnot target = this.theEntity.m();
/* 34 */     if (target == null) {
/* 35 */       return false;
/*    */     }
/* 37 */     double d = this.theEntity.width + this.theEntity.getAttackRange() + 3.0D;
/* 38 */     return this.theEntity.getDistanceSq(target.posX, target.E.b, target.posZ) < d * d;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIWingAttack
 * JD-Core Version:    0.6.2
 */