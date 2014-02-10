/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import oe;
/*    */ 
/*    */ public class EntityAITargetRetaliate extends EntityAISimpleTarget
/*    */ {
/*    */   public EntityAITargetRetaliate(EntityIMLiving entity, Class<? extends oe> targetType, float distance)
/*    */   {
/*  9 */     super(entity, targetType, distance);
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 15 */     EntityLeashKnot attacker = getEntity().aD();
/* 16 */     if (attacker != null)
/*    */     {
/* 18 */       if ((getEntity().d(attacker) <= getAggroRange()) && (getTargetType().isAssignableFrom(attacker.getClass())))
/*    */       {
/* 20 */         setTarget(attacker);
/* 21 */         return true;
/*    */       }
/*    */     }
/* 24 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAITargetRetaliate
 * JD-Core Version:    0.6.2
 */