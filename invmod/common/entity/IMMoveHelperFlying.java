/*     */ package invmod.common.entity;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.pc;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class IMMoveHelperFlying extends IMMoveHelper
/*     */ {
/*     */   private EntityIMFlying a;
/*     */   private double targetFlySpeed;
/*     */   private boolean wantsToBeFlying;
/*     */ 
/*     */   public IMMoveHelperFlying(EntityIMFlying entity)
/*     */   {
/*  14 */     super(entity);
/*  15 */     this.a = entity;
/*  16 */     this.wantsToBeFlying = false;
/*     */   }
/*     */ 
/*     */   public void setHeading(float yaw, float pitch, float idealSpeed, int time)
/*     */   {
/*  21 */     double x = this.a.posX + Math.sin(yaw / 180.0F * 3.141592653589793D) * idealSpeed * time;
/*  22 */     double y = this.a.posY + Math.sin(pitch / 180.0F * 3.141592653589793D) * idealSpeed * time;
/*  23 */     double z = this.a.posZ + Math.cos(yaw / 180.0F * 3.141592653589793D) * idealSpeed * time;
/*  24 */     a(x, y, z, idealSpeed);
/*     */   }
/*     */ 
/*     */   public void setWantsToBeFlying(boolean flag)
/*     */   {
/*  29 */     this.wantsToBeFlying = flag;
/*     */   }
/*     */ 
/*     */   public void c()
/*     */   {
/*  36 */     this.a.setMoveForward(0.0F);
/*  37 */     this.a.setFlightAccelerationVector(0.0F, 0.0F, 0.0F);
/*  38 */     if ((!this.needsUpdate) && (this.a.getMoveState() != MoveState.FLYING))
/*     */     {
/*  40 */       this.a.setMoveState(MoveState.STANDING);
/*  41 */       this.a.setFlyState(FlyState.GROUNDED);
/*  42 */       this.a.rotationPitch = correctRotation(this.a.rotationPitch, 50.0F, 4.0F);
/*  43 */       return;
/*     */     }
/*  45 */     this.needsUpdate = false;
/*     */ 
/*  47 */     if (this.wantsToBeFlying)
/*     */     {
/*  49 */       if (this.a.getFlyState() == FlyState.GROUNDED)
/*     */       {
/*  51 */         this.a.setMoveState(MoveState.RUNNING);
/*  52 */         this.a.setFlyState(FlyState.TAKEOFF);
/*     */       }
/*  54 */       else if (this.a.getFlyState() == FlyState.FLYING)
/*     */       {
/*  56 */         this.a.setMoveState(MoveState.FLYING);
/*     */       }
/*     */ 
/*     */     }
/*  61 */     else if (this.a.getFlyState() == FlyState.FLYING)
/*     */     {
/*  63 */       this.a.setFlyState(FlyState.LANDING);
/*     */     }
/*     */ 
/*  67 */     if (this.a.getFlyState() == FlyState.FLYING)
/*     */     {
/*  69 */       FlyState result = doFlying();
/*  70 */       if (result == FlyState.GROUNDED)
/*  71 */         this.a.setMoveState(MoveState.STANDING);
/*  72 */       else if (result == FlyState.FLYING) {
/*  73 */         this.a.setMoveState(MoveState.FLYING);
/*     */       }
/*  75 */       this.a.setFlyState(result);
/*     */     }
/*  77 */     else if (this.a.getFlyState() == FlyState.TAKEOFF)
/*     */     {
/*  79 */       FlyState result = doTakeOff();
/*  80 */       if (result == FlyState.GROUNDED)
/*  81 */         this.a.setMoveState(MoveState.STANDING);
/*  82 */       else if (result == FlyState.TAKEOFF)
/*  83 */         this.a.setMoveState(MoveState.RUNNING);
/*  84 */       else if (result == FlyState.FLYING) {
/*  85 */         this.a.setMoveState(MoveState.FLYING);
/*     */       }
/*  87 */       this.a.setFlyState(result);
/*     */     }
/*  89 */     else if ((this.a.getFlyState() == FlyState.LANDING) || (this.a.getFlyState() == FlyState.TOUCHDOWN))
/*     */     {
/*  91 */       FlyState result = doLanding();
/*  92 */       if ((result == FlyState.GROUNDED) || (result == FlyState.TOUCHDOWN)) {
/*  93 */         this.a.setMoveState(MoveState.RUNNING);
/*     */       }
/*  95 */       this.a.setFlyState(result);
/*     */     }
/*     */     else
/*     */     {
/*  99 */       MoveState result = doGroundMovement();
/* 100 */       this.a.setMoveState(result);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected MoveState doGroundMovement()
/*     */   {
/* 106 */     this.a.setGroundFriction(0.6F);
/* 107 */     this.a.setRotationRoll(correctRotation(this.a.getRotationRoll(), 0.0F, 6.0F));
/* 108 */     this.targetSpeed = this.a.getMoveSpeedStat();
/* 109 */     this.a.rotationPitch = correctRotation(this.a.rotationPitch, 50.0F, 4.0F);
/* 110 */     return super.doGroundMovement();
/*     */   }
/*     */ 
/*     */   protected FlyState doFlying()
/*     */   {
/* 115 */     this.targetFlySpeed = this.setSpeed;
/* 116 */     return fly();
/*     */   }
/*     */ 
/*     */   protected FlyState fly()
/*     */   {
/* 121 */     this.a.setGroundFriction(1.0F);
/* 122 */     boolean isInLiquid = (this.a.isWet()) || (this.a.handleWaterMovement());
/* 123 */     double dX = this.b - this.a.posX;
/* 124 */     double dZ = this.d - this.a.posZ;
/* 125 */     double dY = this.c - this.a.posY;
/*     */ 
/* 128 */     double dXZSq = dX * dX + dZ * dZ;
/* 129 */     double dXZ = Math.sqrt(dXZSq);
/* 130 */     double distanceSquared = dXZSq + dY * dY;
/*     */ 
/* 134 */     if (distanceSquared > 0.04D)
/*     */     {
/* 136 */       int timeToTurn = 10;
/* 137 */       float gravity = this.a.getGravity();
/* 138 */       float liftConstant = gravity;
/* 139 */       double xAccel = 0.0D;
/* 140 */       double yAccel = 0.0D;
/* 141 */       double zAccel = 0.0D;
/* 142 */       double velX = this.a.motionX;
/* 143 */       double velY = this.a.motionY;
/* 144 */       double velZ = this.a.motionZ;
/* 145 */       double hSpeedSq = velX * velX + velZ * velZ;
/* 146 */       if (hSpeedSq == 0.0D)
/* 147 */         hSpeedSq = 1.0E-008D;
/* 148 */       double horizontalSpeed = Math.sqrt(hSpeedSq);
/* 149 */       double flySpeed = Math.sqrt(hSpeedSq + velY * velY);
/*     */ 
/* 151 */       double desiredYVelocity = dY / timeToTurn;
/* 152 */       double dVelY = desiredYVelocity - (velY - gravity);
/*     */ 
/* 155 */       float minFlightSpeed = 0.05F;
/* 156 */       if (flySpeed < minFlightSpeed)
/*     */       {
/* 158 */         float newYaw = (float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D);
/* 159 */         newYaw = correctRotation(this.a.rotationYaw, newYaw, this.a.getTurnRate());
/* 160 */         this.a.rotationYaw = newYaw;
/* 161 */         if (this.a.onGround) {
/* 162 */           return FlyState.GROUNDED;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 167 */         double liftForce = flySpeed / (this.a.getMaxPoweredFlightSpeed() * this.a.getLiftFactor()) * liftConstant;
/* 168 */         double climbForce = liftForce * horizontalSpeed / (Math.abs(velY) + horizontalSpeed);
/* 169 */         double forwardForce = liftForce * Math.abs(velY) / (Math.abs(velY) + horizontalSpeed);
/* 170 */         double turnForce = liftForce;
/*     */         double climbAccel;
/*     */         double climbAccel;
/* 172 */         if (dVelY < 0.0D)
/*     */         {
/* 174 */           double maxDiveForce = this.a.getMaxTurnForce() - gravity;
/* 175 */           climbAccel = -Math.min(Math.min(climbForce, maxDiveForce), -dVelY);
/*     */         }
/*     */         else
/*     */         {
/* 179 */           double maxClimbForce = this.a.getMaxTurnForce() + gravity;
/* 180 */           climbAccel = Math.min(Math.min(climbForce, maxClimbForce), dVelY);
/*     */         }
/*     */ 
/* 184 */         float minBankForce = 0.01F;
/* 185 */         if (turnForce < minBankForce) {
/* 186 */           turnForce = minBankForce;
/*     */         }
/*     */ 
/* 189 */         double desiredXZHeading = Math.atan2(dZ, dX) - 1.570796326794897D;
/* 190 */         double currXZHeading = Math.atan2(velZ, velX) - 1.570796326794897D;
/* 191 */         double dXZHeading = desiredXZHeading - currXZHeading;
/* 192 */         while (dXZHeading >= 3.141592653589793D) dXZHeading -= 6.283185307179586D;
/* 193 */         while (dXZHeading < -3.141592653589793D) dXZHeading += 6.283185307179586D;
/* 194 */         double bankForce = horizontalSpeed * dXZHeading / timeToTurn;
/* 195 */         double maxBankForce = Math.min(turnForce, this.a.getMaxTurnForce());
/* 196 */         if (bankForce > maxBankForce)
/* 197 */           bankForce = maxBankForce;
/* 198 */         else if (bankForce < -maxBankForce) {
/* 199 */           bankForce = -maxBankForce;
/*     */         }
/*     */ 
/* 202 */         double bankXAccel = bankForce * -velZ / horizontalSpeed;
/* 203 */         double bankZAccel = bankForce * velX / horizontalSpeed;
/*     */ 
/* 206 */         double totalForce = xAccel + yAccel + zAccel;
/*     */ 
/* 209 */         double r = liftForce / totalForce;
/* 210 */         xAccel += bankXAccel;
/* 211 */         yAccel += climbAccel;
/* 212 */         zAccel += bankZAccel;
/* 213 */         velX += bankXAccel;
/* 214 */         velY += climbAccel;
/* 215 */         velZ += bankZAccel;
/*     */ 
/* 219 */         double dYAccelGravity = yAccel - gravity;
/* 220 */         double middlePitch = 15.0D;
/*     */         double newPitch;
/* 222 */         if (velY - gravity < 0.0D)
/*     */         {
/* 224 */           double climbForceRatio = yAccel / climbForce;
/* 225 */           if (climbForceRatio > 1.0D)
/* 226 */             climbForceRatio = 1.0D;
/* 227 */           else if (climbForceRatio < -1.0D) {
/* 228 */             climbForceRatio = -1.0D;
/*     */           }
/* 230 */           double xzSpeed = Math.sqrt(velX * velX + velZ * velZ);
/*     */           double velPitch;
/*     */           double velPitch;
/* 232 */           if (xzSpeed > 0.0D)
/* 233 */             velPitch = Math.atan(velY / xzSpeed) / 3.141592653589793D * 180.0D;
/*     */           else {
/* 235 */             velPitch = -180.0D;
/*     */           }
/* 237 */           double pitchInfluence = (this.a.getMaxPoweredFlightSpeed() - Math.abs(velY)) / this.a.getMaxPoweredFlightSpeed();
/* 238 */           if (pitchInfluence < 0.0D) {
/* 239 */             pitchInfluence = 0.0D;
/*     */           }
/* 241 */           newPitch = velPitch + 15.0D * climbForceRatio * pitchInfluence;
/*     */         }
/*     */         else
/*     */         {
/* 245 */           double pitchLimit = this.a.getMaxPitch();
/* 246 */           double climbForceRatio = Math.min(yAccel / climbForce, 1.0D);
/* 247 */           newPitch = middlePitch + (pitchLimit - middlePitch) * climbForceRatio;
/*     */         }
/* 249 */         double newPitch = correctRotation(this.a.rotationPitch, (float)newPitch, 1.5F);
/* 250 */         double newYaw = Math.atan2(velZ, velX) * 180.0D / 3.141592653589793D - 90.0D;
/* 251 */         newYaw = correctRotation(this.a.rotationYaw, (float)newYaw, this.a.getTurnRate());
/* 252 */         this.a.setPositionAndRotation(this.a.posX, this.a.posY, this.a.posZ, (float)newYaw, (float)newPitch);
/* 253 */         double newRoll = 60.0D * bankForce / turnForce;
/* 254 */         this.a.setRotationRoll(correctRotation(this.a.getRotationRoll(), (float)newRoll, 6.0F));
/*     */         double horizontalForce;
/*     */         double horizontalForce;
/* 260 */         if (velY > 0.0D)
/*     */         {
/* 262 */           horizontalForce = -climbAccel;
/*     */         }
/*     */         else
/*     */         {
/* 266 */           horizontalForce = forwardForce;
/*     */         }
/* 268 */         int xDirection = velX > 0.0D ? 1 : -1;
/* 269 */         int zDirection = velZ > 0.0D ? 1 : -1;
/* 270 */         double hComponentX = xDirection * velX / (xDirection * velX + zDirection * velZ);
/*     */ 
/* 272 */         double xLiftAccel = xDirection * horizontalForce * hComponentX;
/* 273 */         double zLiftAccel = zDirection * horizontalForce * (1.0D - hComponentX);
/*     */ 
/* 276 */         double loss = 0.4D;
/* 277 */         xLiftAccel += xDirection * -Math.abs(bankForce * loss) * hComponentX;
/* 278 */         zLiftAccel += zDirection * -Math.abs(bankForce * loss) * (1.0D - hComponentX);
/*     */ 
/* 280 */         xAccel += xLiftAccel;
/* 281 */         zAccel += zLiftAccel;
/*     */       }
/*     */ 
/* 286 */       if (flySpeed < this.targetFlySpeed)
/*     */       {
/* 288 */         this.a.setThrustEffort(0.6F);
/* 289 */         if (!this.a.isThrustOn())
/*     */         {
/* 291 */           this.a.setThrustOn(true);
/*     */         }
/* 293 */         double desiredVThrustRatio = (dVelY - yAccel) / this.a.getThrust();
/* 294 */         AABBPool thrust = calcThrust(desiredVThrustRatio);
/* 295 */         xAccel += thrust.listAABB;
/* 296 */         yAccel += thrust.nextPoolIndex;
/* 297 */         zAccel += thrust.maxPoolIndex;
/*     */       }
/* 299 */       else if (flySpeed > this.targetFlySpeed * 1.8D)
/*     */       {
/* 301 */         this.a.setThrustEffort(1.0F);
/* 302 */         if (!this.a.isThrustOn())
/*     */         {
/* 304 */           this.a.setThrustOn(true);
/*     */         }
/* 306 */         double desiredVThrustRatio = (dVelY - yAccel) / (this.a.getThrust() * 10.0F);
/* 307 */         AABBPool thrust = calcThrust(desiredVThrustRatio);
/* 308 */         xAccel += -thrust.listAABB;
/* 309 */         yAccel += thrust.nextPoolIndex;
/* 310 */         zAccel += -thrust.maxPoolIndex;
/*     */       }
/* 312 */       else if (this.a.isThrustOn())
/*     */       {
/* 314 */         this.a.setThrustOn(false);
/*     */       }
/*     */ 
/* 319 */       this.a.setFlightAccelerationVector((float)xAccel, (float)yAccel, (float)zAccel);
/*     */     }
/* 321 */     return FlyState.FLYING;
/*     */   }
/*     */ 
/*     */   protected FlyState doTakeOff()
/*     */   {
/* 326 */     this.a.setGroundFriction(0.98F);
/* 327 */     this.a.setThrustOn(true);
/* 328 */     this.a.setThrustEffort(1.0F);
/* 329 */     this.targetSpeed = this.a.getMoveSpeedStat();
/*     */ 
/* 331 */     MoveState result = doGroundMovement();
/* 332 */     if (result == MoveState.STANDING) {
/* 333 */       return FlyState.GROUNDED;
/*     */     }
/* 335 */     if (this.a.isCollidedHorizontally) {
/* 336 */       this.a.j().a();
/*     */     }
/* 338 */     AABBPool thrust = calcThrust(0.0D);
/* 339 */     this.a.setFlightAccelerationVector((float)thrust.listAABB, (float)thrust.nextPoolIndex, (float)thrust.maxPoolIndex);
/* 340 */     double speed = Math.sqrt(this.a.motionX * this.a.motionX + this.a.motionY * this.a.motionY + this.a.motionZ * this.a.motionZ);
/*     */ 
/* 342 */     this.a.rotationPitch = correctRotation(this.a.rotationPitch, 40.0F, 4.0F);
/*     */ 
/* 344 */     float gravity = this.a.getGravity();
/* 345 */     float liftConstant = gravity;
/* 346 */     double liftForce = speed / (this.a.getMaxPoweredFlightSpeed() * this.a.getLiftFactor()) * liftConstant;
/*     */ 
/* 348 */     if (liftForce > gravity) {
/* 349 */       return FlyState.FLYING;
/*     */     }
/* 351 */     return FlyState.TAKEOFF;
/*     */   }
/*     */ 
/*     */   protected FlyState doLanding()
/*     */   {
/* 356 */     this.a.setGroundFriction(0.3F);
/* 357 */     int x = LongHashMapEntry.c(this.a.posX);
/* 358 */     int y = LongHashMapEntry.c(this.a.posY);
/* 359 */     int z = LongHashMapEntry.c(this.a.posZ);
/*     */ 
/* 361 */     for (int i = 1; i < 5; i++)
/*     */     {
/* 363 */       if (this.a.q.a(x, y - i, z) != 0)
/*     */         break;
/*     */     }
/* 366 */     this.targetFlySpeed = (this.setSpeed * (0.66F - (0.4F - (i - 1) * 0.133F)));
/* 367 */     FlyState result = fly();
/* 368 */     this.a.setThrustOn(true);
/* 369 */     if (result == FlyState.FLYING)
/*     */     {
/* 371 */       double speed = Math.sqrt(this.a.motionX * this.a.motionX + this.a.motionY * this.a.motionY + this.a.motionZ * this.a.motionZ);
/* 372 */       if (this.a.onGround)
/*     */       {
/* 374 */         if (speed < this.a.getLandingSpeedThreshold())
/*     */         {
/* 376 */           return FlyState.GROUNDED;
/*     */         }
/*     */ 
/* 380 */         this.a.setRotationRoll(correctRotation(this.a.getRotationRoll(), 40.0F, 6.0F));
/* 381 */         return FlyState.TOUCHDOWN;
/*     */       }
/*     */     }
/*     */ 
/* 385 */     return FlyState.LANDING;
/*     */   }
/*     */ 
/*     */   protected AABBPool calcThrust(double desiredVThrustRatio)
/*     */   {
/* 390 */     float thrust = this.a.getThrust();
/* 391 */     float rMin = this.a.getThrustComponentRatioMin();
/* 392 */     float rMax = this.a.getThrustComponentRatioMax();
/* 393 */     double vThrustRatio = desiredVThrustRatio;
/* 394 */     if (vThrustRatio > rMax)
/* 395 */       vThrustRatio = rMax;
/* 396 */     else if (vThrustRatio < rMin) {
/* 397 */       vThrustRatio = rMin;
/*     */     }
/* 399 */     double hThrust = (1.0D - vThrustRatio) * thrust;
/* 400 */     double vThrust = vThrustRatio * thrust;
/* 401 */     double xAccel = hThrust * -Math.sin(this.a.rotationYaw / 180.0F * 3.141592653589793D);
/* 402 */     double yAccel = vThrust;
/* 403 */     double zAccel = hThrust * Math.cos(this.a.rotationYaw / 180.0F * 3.141592653589793D);
/* 404 */     return this.a.q.V().a(xAccel, yAccel, zAccel);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.IMMoveHelperFlying
 * JD-Core Version:    0.6.2
 */