/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.MathUtil;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityAISwoop extends EntityAIFollowParent
/*     */ {
/*     */   private static final int INITIAL_LINEUP_TIME = 25;
/*     */   private EntityIMBird theEntity;
/*     */   private float minDiveClearanceY;
/*     */   private EntityLeashKnot swoopTarget;
/*     */   private float diveAngle;
/*     */   private float diveHeight;
/*     */   private float strikeDistance;
/*     */   private float minHeight;
/*     */   private float minXZDistance;
/*     */   private float maxSteepness;
/*     */   private float finalRunLength;
/*     */   private float finalRunArcLimit;
/*     */   private int time;
/*     */   private boolean isCommittedToFinalRun;
/*     */   private boolean endSwoop;
/*     */   private boolean usingClaws;
/*     */ 
/*     */   public EntityAISwoop(EntityIMBird entity)
/*     */   {
/*  45 */     this.theEntity = entity;
/*  46 */     this.minDiveClearanceY = 0.0F;
/*  47 */     this.swoopTarget = null;
/*  48 */     this.diveAngle = 0.0F;
/*  49 */     this.diveHeight = 0.0F;
/*  50 */     this.maxSteepness = 40.0F;
/*  51 */     this.strikeDistance = (entity.width + 1.5F);
/*  52 */     this.minHeight = 6.0F;
/*  53 */     this.minXZDistance = 10.0F;
/*  54 */     this.finalRunLength = 4.0F;
/*  55 */     this.finalRunArcLimit = 15.0F;
/*  56 */     this.time = 0;
/*  57 */     this.isCommittedToFinalRun = false;
/*  58 */     this.endSwoop = false;
/*  59 */     this.usingClaws = false;
/*  60 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  66 */     if ((this.theEntity.getAIGoal() == Goal.FIND_ATTACK_OPPORTUNITY) && (this.theEntity.m() != null))
/*     */     {
/*  68 */       this.swoopTarget = this.theEntity.m();
/*  69 */       double dX = this.swoopTarget.posX - this.theEntity.posX;
/*  70 */       double dY = this.swoopTarget.posY - this.theEntity.posY;
/*  71 */       double dZ = this.swoopTarget.posZ - this.theEntity.posZ;
/*  72 */       double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/*  73 */       if ((-dY < this.minHeight) || (dXZ < this.minXZDistance)) {
/*  74 */         return false;
/*     */       }
/*  76 */       double pitchToTarget = Math.atan(dY / dXZ) * 180.0D / 3.141592653589793D;
/*  77 */       if (pitchToTarget > this.maxSteepness) {
/*  78 */         return false;
/*     */       }
/*  80 */       this.finalRunLength = ((float)(dXZ * 0.42D));
/*  81 */       if (this.finalRunLength > 18.0F)
/*  82 */         this.finalRunLength = 18.0F;
/*  83 */       else if (this.finalRunLength < 4.0F) {
/*  84 */         this.finalRunLength = 4.0F;
/*     */       }
/*  86 */       this.diveAngle = ((float)(Math.atan((dXZ - this.finalRunLength) / dY) * 180.0D / 3.141592653589793D));
/*  87 */       if ((this.swoopTarget != null) && (isSwoopPathClear(this.swoopTarget, this.diveAngle)))
/*     */       {
/*  89 */         this.diveHeight = ((float)-dY);
/*  90 */         return true;
/*     */       }
/*     */     }
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  99 */     return (this.theEntity.m() == this.swoopTarget) && (!this.endSwoop) && (this.theEntity.getMoveState() == MoveState.FLYING);
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/* 105 */     this.time = 0;
/* 106 */     this.theEntity.transitionAIGoal(Goal.SWOOP);
/* 107 */     this.theEntity.getNavigatorNew().setMovementType(INavigationFlying.MoveType.PREFER_FLYING);
/* 108 */     this.theEntity.getNavigatorNew().tryMoveToEntity(this.swoopTarget, 0.0F, this.theEntity.getMaxPoweredFlightSpeed());
/*     */ 
/* 110 */     this.theEntity.doScreech();
/*     */   }
/*     */ 
/*     */   public void resetTask()
/*     */   {
/* 116 */     this.endSwoop = false;
/* 117 */     this.isCommittedToFinalRun = false;
/* 118 */     this.theEntity.getNavigatorNew().enableDirectTarget(false);
/* 119 */     if (this.theEntity.getAIGoal() == Goal.SWOOP)
/*     */     {
/* 121 */       this.theEntity.transitionAIGoal(Goal.NONE);
/* 122 */       this.theEntity.setClawsForward(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/* 129 */     this.time += 1;
/* 130 */     if (!this.isCommittedToFinalRun)
/*     */     {
/* 134 */       if (this.theEntity.d(this.swoopTarget) < this.finalRunLength)
/*     */       {
/* 136 */         this.theEntity.getNavigatorNew().setPitchBias(0.0F, 1.0F);
/* 137 */         if (isFinalRunLinedUp())
/*     */         {
/* 140 */           this.usingClaws = (this.theEntity.q.s.nextFloat() > 0.6F);
/*     */ 
/* 143 */           this.theEntity.setClawsForward(true);
/*     */ 
/* 149 */           this.theEntity.getNavigatorNew().enableDirectTarget(true);
/* 150 */           this.isCommittedToFinalRun = true;
/*     */         }
/*     */         else
/*     */         {
/* 155 */           this.theEntity.transitionAIGoal(Goal.NONE);
/* 156 */           this.endSwoop = true;
/*     */         }
/*     */       }
/* 159 */       else if (this.time > 25)
/*     */       {
/* 163 */         double dYp = -(this.swoopTarget.posY - this.theEntity.posY);
/* 164 */         if (dYp < 2.9D)
/*     */         {
/* 166 */           dYp = 0.0D;
/*     */         }
/* 168 */         this.theEntity.getNavigatorNew().setPitchBias(this.diveAngle * (float)(dYp / this.diveHeight), (float)(0.6D * (dYp / this.diveHeight)));
/*     */       }
/*     */ 
/*     */     }
/* 175 */     else if (this.theEntity.d(this.swoopTarget) < this.strikeDistance)
/*     */     {
/* 177 */       this.theEntity.transitionAIGoal(Goal.FLYING_STRIKE);
/*     */ 
/* 180 */       this.theEntity.getNavigatorNew().enableDirectTarget(false);
/* 181 */       this.endSwoop = true;
/*     */     }
/*     */     else
/*     */     {
/* 187 */       double dX = this.swoopTarget.posX - this.theEntity.posX;
/* 188 */       double dZ = this.swoopTarget.posZ - this.theEntity.posZ;
/* 189 */       double yawToTarget = Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D;
/* 190 */       if (Math.abs(MathUtil.boundAngle180Deg(yawToTarget - this.theEntity.rotationYaw)) > 90.0D)
/*     */       {
/* 192 */         this.theEntity.transitionAIGoal(Goal.NONE);
/* 193 */         this.theEntity.getNavigatorNew().enableDirectTarget(false);
/* 194 */         this.theEntity.setClawsForward(false);
/* 195 */         this.endSwoop = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isSwoopPathClear(EntityLeashKnot target, float diveAngle)
/*     */   {
/* 234 */     double dX = target.posX - this.theEntity.posX;
/* 235 */     double dY = target.posY - this.theEntity.posY;
/* 236 */     double dZ = target.posZ - this.theEntity.posZ;
/* 237 */     double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/* 238 */     double dRayY = 2.0D;
/* 239 */     int hitCount = 0;
/* 240 */     double lowestCollide = this.theEntity.posY;
/* 241 */     for (double y = this.theEntity.posY - dRayY; y > target.posY; y -= dRayY)
/*     */     {
/* 243 */       double dist = Math.tan(90.0F + diveAngle) * (this.theEntity.posY - y);
/* 244 */       double x = -Math.sin(this.theEntity.rotationYaw / 180.0F * 3.141592653589793D) * dist;
/* 245 */       double z = Math.cos(this.theEntity.rotationYaw / 180.0F * 3.141592653589793D) * dist;
/* 246 */       AABBPool source = this.theEntity.q.V().a(x, y, z);
/* 247 */       AxisAlignedBB collide = this.theEntity.q.a(source, target.l(1.0F));
/* 248 */       if (collide != null)
/*     */       {
/* 250 */         if (hitCount == 0) {
/* 251 */           lowestCollide = y;
/*     */         }
/* 253 */         hitCount++;
/*     */       }
/*     */     }
/*     */ 
/* 257 */     if (isAcceptableDiveSpace(this.theEntity.posY, lowestCollide, hitCount))
/*     */     {
/* 259 */       return true;
/*     */     }
/*     */ 
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isFinalRunLinedUp()
/*     */   {
/* 267 */     double dX = this.swoopTarget.posX - this.theEntity.posX;
/* 268 */     double dY = this.swoopTarget.posY - this.theEntity.posY;
/* 269 */     double dZ = this.swoopTarget.posZ - this.theEntity.posZ;
/* 270 */     double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/* 271 */     double yawToTarget = Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D;
/* 272 */     double dYaw = MathUtil.boundAngle180Deg(yawToTarget - this.theEntity.rotationYaw);
/* 273 */     if ((dYaw < -this.finalRunArcLimit) || (dYaw > this.finalRunArcLimit)) {
/* 274 */       return false;
/*     */     }
/* 276 */     double dPitch = Math.atan(dY / dXZ) * 180.0D / 3.141592653589793D - this.theEntity.rotationPitch;
/* 277 */     if ((dPitch < -this.finalRunArcLimit) || (dPitch > this.finalRunArcLimit)) {
/* 278 */       return false;
/*     */     }
/* 280 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean isAcceptableDiveSpace(double entityPosY, double lowestCollideY, int hitCount)
/*     */   {
/* 285 */     double clearanceY = entityPosY - lowestCollideY;
/* 286 */     if (clearanceY < this.minDiveClearanceY) {
/* 287 */       return false;
/*     */     }
/* 289 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAISwoop
 * JD-Core Version:    0.6.2
 */