/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ import oe;
/*    */ 
/*    */ public class EntityAIThrowerKillEntity<T extends oe> extends EntityAIKillEntity<T>
/*    */ {
/*    */   private boolean melee;
/*    */   private int ammo;
/*    */   private float attackRangeSq;
/*    */   private float launchSpeed;
/*    */   private final EntityIMThrower theEntity;
/*    */ 
/*    */   public EntityAIThrowerKillEntity(EntityIMThrower entity, Class<? extends T> targetClass, int attackDelay, float throwRange, float launchSpeed, int ammo)
/*    */   {
/* 17 */     super(entity, targetClass, attackDelay);
/* 18 */     this.attackRangeSq = (throwRange * throwRange);
/* 19 */     this.launchSpeed = launchSpeed;
/* 20 */     this.ammo = ammo;
/* 21 */     this.theEntity = entity;
/*    */   }
/*    */ 
/*    */   protected void attackEntity(nm target)
/*    */   {
/* 27 */     if (this.melee)
/*    */     {
/* 29 */       setAttackTime(getAttackDelay());
/* 30 */       super.attackEntity(target);
/*    */     }
/*    */     else
/*    */     {
/* 34 */       this.ammo -= 1;
/* 35 */       setAttackTime(getAttackDelay() * 2);
/* 36 */       this.theEntity.throwBoulder(target.u, target.v, target.w);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected boolean canAttackEntity(nm target)
/*    */   {
/* 43 */     this.melee = super.canAttackEntity(target);
/* 44 */     if (this.melee) {
/* 45 */       return true;
/*    */     }
/* 47 */     if ((!this.theEntity.canThrow()) || (this.ammo == 0)) {
/* 48 */       return false;
/*    */     }
/*    */ 
/* 51 */     double dX = this.theEntity.posX - target.u;
/* 52 */     double dZ = this.theEntity.posZ - target.w;
/* 53 */     double dXY = LongHashMapEntry.a(dX * dX + dZ * dZ);
/* 54 */     return (getAttackTime() <= 0) && (this.theEntity.l().a(target)) && (0.025D * dXY / (this.launchSpeed * this.launchSpeed) <= 1.0D);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIThrowerKillEntity
 * JD-Core Version:    0.6.2
 */