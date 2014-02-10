/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.src.nm;
/*    */ import oe;
/*    */ 
/*    */ public class EntityAIKillEntity<T extends oe> extends EntityAIMoveToEntity<T>
/*    */ {
/*    */   private static final float ATTACK_RANGE = 1.0F;
/*    */   private int attackDelay;
/*    */   private int nextAttack;
/*    */ 
/*    */   public EntityAIKillEntity(EntityIMLiving entity, Class<? extends T> targetClass, int attackDelay)
/*    */   {
/* 15 */     super(entity, targetClass);
/* 16 */     this.attackDelay = attackDelay;
/* 17 */     this.nextAttack = 0;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 23 */     super.updateTask();
/* 24 */     setAttackTime(getAttackTime() - 1);
/* 25 */     nm target = getTarget();
/* 26 */     if (canAttackEntity(target))
/*    */     {
/* 28 */       attackEntity(target);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void attackEntity(nm target)
/*    */   {
/* 34 */     getEntity().m(getTarget());
/* 35 */     setAttackTime(getAttackDelay());
/*    */   }
/*    */ 
/*    */   protected boolean canAttackEntity(nm target)
/*    */   {
/* 40 */     if (getAttackTime() <= 0)
/*    */     {
/* 42 */       nm entity = getEntity();
/* 43 */       double d = (entity.O + 1.0F) * (entity.O + 1.0F);
/*    */ 
/* 45 */       return entity.e(target.u, target.E.b, target.w) < d;
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   protected int getAttackTime()
/*    */   {
/* 52 */     return this.nextAttack;
/*    */   }
/*    */ 
/*    */   protected void setAttackTime(int time)
/*    */   {
/* 57 */     this.nextAttack = time;
/*    */   }
/*    */ 
/*    */   protected int getAttackDelay()
/*    */   {
/* 62 */     return this.attackDelay;
/*    */   }
/*    */ 
/*    */   protected void setAttackDelay(int time)
/*    */   {
/* 67 */     this.attackDelay = time;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIKillEntity
 * JD-Core Version:    0.6.2
 */