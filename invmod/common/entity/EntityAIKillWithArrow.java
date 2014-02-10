/*    */ package invmod.common.entity;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.player.EnumStatus;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ import oe;
/*    */ 
/*    */ public class EntityAIKillWithArrow<T extends oe> extends EntityAIKillEntity<T>
/*    */ {
/*    */   private float attackRangeSq;
/*    */ 
/*    */   public EntityAIKillWithArrow(EntityIMLiving entity, Class<? extends T> targetClass, int attackDelay, float attackRange)
/*    */   {
/* 13 */     super(entity, targetClass, attackDelay);
/* 14 */     this.attackRangeSq = (attackRange * attackRange);
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 20 */     super.updateTask();
/* 21 */     EntityLeashKnot target = getTarget();
/* 22 */     if ((getEntity().getDistanceSq(target.posX, target.E.b, target.posZ) < 36.0D) && (getEntity().l().a(target)))
/* 23 */       getEntity().getNavigatorNew().haltForTick();
/*    */   }
/*    */ 
/*    */   protected void attackEntity(nm target)
/*    */   {
/* 29 */     setAttackTime(getAttackDelay());
/* 30 */     EntityLeashKnot entity = getEntity();
/* 31 */     EnumStatus entityarrow = new EnumStatus(entity.q, entity, getTarget(), 1.1F, 12.0F);
/* 32 */     entity.q.a(entity, "random.bow", 1.0F, 1.0F / (entity.aC().nextFloat() * 0.4F + 0.8F));
/* 33 */     entity.q.d(entityarrow);
/*    */   }
/*    */ 
/*    */   protected boolean canAttackEntity(nm target)
/*    */   {
/* 39 */     return (getAttackTime() <= 0) && (getEntity().getDistanceSq(target.u, target.E.b, target.w) < this.attackRangeSq) && (getEntity().l().a(target));
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIKillWithArrow
 * JD-Core Version:    0.6.2
 */