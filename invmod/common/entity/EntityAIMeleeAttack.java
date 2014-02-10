/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import oe;
/*    */ import pr;
/*    */ 
/*    */ public class EntityAIMeleeAttack<T extends oe> extends pr
/*    */ {
/*    */   private EntityIMLiving theEntity;
/*    */   private Class<? extends T> targetClass;
/*    */   private float attackRange;
/*    */   private int attackDelay;
/*    */   private int nextAttack;
/*    */ 
/*    */   public EntityAIMeleeAttack(EntityIMLiving entity, Class<? extends T> targetClass, int attackDelay)
/*    */   {
/* 16 */     this.theEntity = entity;
/* 17 */     this.targetClass = targetClass;
/* 18 */     this.attackDelay = attackDelay;
/* 19 */     this.attackRange = 0.6F;
/* 20 */     this.nextAttack = 0;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 26 */     EntityLeashKnot target = this.theEntity.m();
/* 27 */     return (target != null) && (this.theEntity.getAIGoal() == Goal.MELEE_TARGET) && (this.theEntity.d(target) < (this.attackRange + this.theEntity.width + target.width) * 4.0F) && (target.getClass().isAssignableFrom(this.targetClass));
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 33 */     EntityLeashKnot target = this.theEntity.m();
/* 34 */     if (canAttackEntity(target))
/*    */     {
/* 36 */       attackEntity(target);
/*    */     }
/* 38 */     setAttackTime(getAttackTime() - 1);
/*    */   }
/*    */ 
/*    */   public Class<? extends T> getTargetClass()
/*    */   {
/* 43 */     return this.targetClass;
/*    */   }
/*    */ 
/*    */   protected void attackEntity(EntityLeashKnot target)
/*    */   {
/* 48 */     this.theEntity.m(target);
/* 49 */     setAttackTime(getAttackDelay());
/*    */   }
/*    */ 
/*    */   protected boolean canAttackEntity(EntityLeashKnot target)
/*    */   {
/* 54 */     if (getAttackTime() <= 0)
/*    */     {
/* 56 */       double d = this.theEntity.width + this.attackRange;
/* 57 */       return this.theEntity.getDistanceSq(target.posX, target.E.b, target.posZ) < d * d;
/*    */     }
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   protected int getAttackTime()
/*    */   {
/* 64 */     return this.nextAttack;
/*    */   }
/*    */ 
/*    */   protected void setAttackTime(int time)
/*    */   {
/* 69 */     this.nextAttack = time;
/*    */   }
/*    */ 
/*    */   protected int getAttackDelay()
/*    */   {
/* 74 */     return this.attackDelay;
/*    */   }
/*    */ 
/*    */   protected void setAttackDelay(int time)
/*    */   {
/* 79 */     this.attackDelay = time;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIMeleeAttack
 * JD-Core Version:    0.6.2
 */