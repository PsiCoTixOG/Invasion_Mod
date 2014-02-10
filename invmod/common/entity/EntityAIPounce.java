/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ 
/*    */ public class EntityAIPounce extends EntityAIFollowParent
/*    */ {
/*    */   private EntityIMSpider theEntity;
/*    */   private boolean isPouncing;
/*    */   private int pounceTimer;
/*    */   private int cooldown;
/*    */   private float minPower;
/*    */   private float maxPower;
/*    */ 
/*    */   public EntityAIPounce(EntityIMSpider entity, float minPower, float maxPower, int cooldown)
/*    */   {
/* 18 */     this.theEntity = entity;
/* 19 */     this.isPouncing = false;
/* 20 */     this.minPower = minPower;
/* 21 */     this.maxPower = maxPower;
/* 22 */     this.cooldown = cooldown;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 28 */     EntityLeashKnot target = this.theEntity.m();
/* 29 */     if ((--this.pounceTimer <= 0) && (target != null) && (this.theEntity.o(target)) && (this.theEntity.onGround))
/*    */     {
/* 31 */       return true;
/*    */     }
/*    */ 
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 40 */     return this.isPouncing;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 46 */     EntityLeashKnot target = this.theEntity.m();
/* 47 */     if (pounce(target.posX, target.posY, target.posZ))
/*    */     {
/* 49 */       this.theEntity.setAirborneTime(0);
/* 50 */       this.isPouncing = true;
/* 51 */       this.theEntity.getNavigatorNew().haltForTick();
/*    */     }
/*    */     else
/*    */     {
/* 55 */       this.isPouncing = false;
/*    */     }
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 62 */     this.theEntity.getNavigatorNew().haltForTick();
/* 63 */     int airborneTime = this.theEntity.getAirborneTime();
/* 64 */     if ((airborneTime > 20) && (this.theEntity.onGround))
/*    */     {
/* 66 */       this.isPouncing = false;
/* 67 */       this.pounceTimer = this.cooldown;
/* 68 */       this.theEntity.setAirborneTime(0);
/* 69 */       this.theEntity.getNavigatorNew().clearPath();
/*    */     }
/*    */     else
/*    */     {
/* 73 */       this.theEntity.setAirborneTime(airborneTime + 1);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected boolean pounce(double x, double y, double z)
/*    */   {
/* 79 */     double dX = x - this.theEntity.posX;
/* 80 */     double dY = y - this.theEntity.posY;
/* 81 */     double dZ = z - this.theEntity.posZ;
/* 82 */     double dXZ = LongHashMapEntry.a(dX * dX + dZ * dZ);
/* 83 */     double a = Math.atan(dY / dXZ);
/* 84 */     if ((a > -0.7853981633974483D) && (a < 0.7853981633974483D))
/*    */     {
/* 86 */       double rratio = (1.0D - Math.tan(a)) * (1.0D / Math.cos(a));
/* 87 */       double r = dXZ / rratio;
/* 88 */       double v = 1.0D / Math.sqrt(1.0F / this.theEntity.getGravity() / r);
/* 89 */       if ((v > this.minPower) && (v < this.maxPower))
/*    */       {
/* 91 */         double distance = LongHashMapEntry.a(2.0D * (dXZ * dXZ));
/* 92 */         this.theEntity.motionX = (v * dX / distance);
/* 93 */         this.theEntity.motionY = (v * dXZ / distance);
/* 94 */         this.theEntity.motionZ = (v * dZ / distance);
/* 95 */         return true;
/*    */       }
/*    */     }
/* 98 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIPounce
 * JD-Core Version:    0.6.2
 */