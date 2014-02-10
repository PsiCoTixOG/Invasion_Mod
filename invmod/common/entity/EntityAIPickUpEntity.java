/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.MathUtil;
/*     */ import java.io.PrintStream;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.src.nm;
/*     */ 
/*     */ public class EntityAIPickUpEntity extends EntityAIFollowParent
/*     */ {
/*     */   private EntityIMBird theEntity;
/*     */   private int time;
/*     */   private int holdTime;
/*     */   private int abortTime;
/*     */   private float pickupPointY;
/*     */   private float pickupRangeY;
/*     */   private float pickupPointX;
/*     */   private float pickupPointZ;
/*     */   private float pickupRangeXZ;
/*     */   private float abortAngleYaw;
/*     */   private float abortAnglePitch;
/*     */   private boolean isHoldingEntity;
/*     */ 
/*     */   public EntityAIPickUpEntity(EntityIMBird entity, float pickupPointX, float pickupPointY, float pickupPointZ, float pickupRangeY, float pickupRangeXZ, int abortTime, float abortAngleYaw, float abortAnglePitch)
/*     */   {
/*  26 */     this.theEntity = entity;
/*  27 */     this.time = 0;
/*  28 */     this.holdTime = 70;
/*  29 */     this.pickupPointX = pickupPointX;
/*  30 */     this.pickupPointY = pickupPointY;
/*  31 */     this.pickupPointZ = pickupPointZ;
/*  32 */     this.pickupRangeY = pickupRangeY;
/*  33 */     this.pickupRangeXZ = pickupRangeXZ;
/*  34 */     this.abortTime = abortTime;
/*  35 */     this.abortAngleYaw = abortAngleYaw;
/*  36 */     this.abortAnglePitch = abortAnglePitch;
/*  37 */     this.isHoldingEntity = false;
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  43 */     return (this.theEntity.getAIGoal() == Goal.PICK_UP_TARGET) || (this.theEntity.n != null);
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  49 */     this.isHoldingEntity = (this.theEntity.n != null);
/*  50 */     this.time = 0;
/*     */   }
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  56 */     EntityLeashKnot target = this.theEntity.m();
/*  57 */     if ((target != null) && (!target.isDead))
/*     */     {
/*  59 */       if (!this.isHoldingEntity)
/*     */       {
/*  63 */         if ((this.time > this.abortTime) && (isLinedUp(target)))
/*  64 */           return true;
/*     */       }
/*  66 */       else if (this.theEntity.n == target)
/*     */       {
/*  68 */         return true;
/*     */       }
/*     */     }
/*  71 */     this.theEntity.transitionAIGoal(Goal.NONE);
/*  72 */     this.theEntity.setClawsForward(false);
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  79 */     this.time += 1;
/*  80 */     if (!this.isHoldingEntity)
/*     */     {
/*  83 */       EntityLeashKnot target = this.theEntity.m();
/*  84 */       double dY = target.prevPosY - this.theEntity.prevPosY;
/*  85 */       System.out.println(dY);
/*  86 */       if (Math.abs(dY - this.pickupPointY) < this.pickupRangeY)
/*     */       {
/*  89 */         double dAngle = this.theEntity.prevRotationYaw / 180.0F * 3.141592653589793D;
/*  90 */         double sinF = Math.sin(dAngle);
/*  91 */         double cosF = Math.cos(dAngle);
/*  92 */         double x = this.pickupPointX * cosF - this.pickupPointZ * sinF;
/*  93 */         double z = this.pickupPointZ * cosF + this.pickupPointX * sinF;
/*     */ 
/*  96 */         double dX = target.prevPosX - (x + this.theEntity.prevPosX);
/*  97 */         double dZ = target.prevPosZ - (z + this.theEntity.prevPosZ);
/*  98 */         double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/*  99 */         System.out.println(dXZ);
/* 100 */         if (dXZ < this.pickupRangeXZ)
/*     */         {
/* 102 */           target.a(this.theEntity);
/* 103 */           this.isHoldingEntity = true;
/* 104 */           this.time = 0;
/* 105 */           this.theEntity.getNavigatorNew().clearPath();
/* 106 */           this.theEntity.getNavigatorNew().setPitchBias(20.0F, 1.5F);
/*     */         }
/*     */       }
/*     */     }
/* 110 */     else if (this.time == 45)
/*     */     {
/* 112 */       this.theEntity.getNavigatorNew().setPitchBias(0.0F, 0.0F);
/*     */     }
/* 114 */     else if (this.time > this.holdTime)
/*     */     {
/* 116 */       this.theEntity.m().a(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isLinedUp(nm target)
/*     */   {
/* 122 */     double dX = target.u - this.theEntity.posX;
/* 123 */     double dY = target.v - this.theEntity.posY;
/* 124 */     double dZ = target.w - this.theEntity.posZ;
/* 125 */     double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/* 126 */     double yawToTarget = Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D;
/* 127 */     double dYaw = MathUtil.boundAngle180Deg(yawToTarget - this.theEntity.rotationYaw);
/* 128 */     if ((dYaw < -this.abortAngleYaw) || (dYaw > this.abortAngleYaw)) {
/* 129 */       return false;
/*     */     }
/* 131 */     double dPitch = Math.atan(dY / dXZ) * 180.0D / 3.141592653589793D - this.theEntity.rotationPitch;
/* 132 */     if ((dPitch < -this.abortAnglePitch) || (dPitch > this.abortAnglePitch)) {
/* 133 */       return false;
/*     */     }
/* 135 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIPickUpEntity
 * JD-Core Version:    0.6.2
 */