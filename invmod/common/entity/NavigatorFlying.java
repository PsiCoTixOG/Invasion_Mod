/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.Distance;
/*     */ import invmod.common.util.MathUtil;
/*     */ import invmod.common.util.Pair;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.AABBLocalPool;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class NavigatorFlying extends NavigatorIM
/*     */   implements INavigationFlying
/*     */ {
/*     */   private static final int VISION_RESOLUTION_H = 30;
/*     */   private static final int VISION_RESOLUTION_V = 20;
/*     */   private static final float FOV_H = 300.0F;
/*     */   private static final float FOV_V = 220.0F;
/*     */   private final EntityIMFlying theEntity;
/*     */   private INavigationFlying.MoveType moveType;
/*     */   private boolean wantsToBeFlying;
/*     */   private float targetYaw;
/*     */   private float targetPitch;
/*     */   private float targetSpeed;
/*     */   private float visionDistance;
/*     */   private int visionUpdateRate;
/*     */   private int timeSinceVision;
/*     */   private float[][] retina;
/*     */   private float[][] headingAppeal;
/*     */   private AABBPool intermediateTarget;
/*     */   private AABBPool finalTarget;
/*     */   private boolean isCircling;
/*     */   private float circlingHeight;
/*     */   private float circlingRadius;
/*     */   private float pitchBias;
/*     */   private float pitchBiasAmount;
/*     */   private int timeLookingForEntity;
/*     */   private boolean precisionTarget;
/*     */   private float closestDistToTarget;
/*     */   private int timeSinceGotCloser;
/*     */ 
/*     */   public NavigatorFlying(EntityIMFlying entityFlying, IPathSource pathSource)
/*     */   {
/*  43 */     super(entityFlying, pathSource);
/*  44 */     this.theEntity = entityFlying;
/*  45 */     this.moveType = INavigationFlying.MoveType.MIXED;
/*  46 */     this.visionDistance = 14.0F;
/*  47 */     this.visionUpdateRate = (this.timeSinceVision = 3);
/*  48 */     this.targetYaw = entityFlying.rotationYaw;
/*  49 */     this.targetPitch = 0.0F;
/*  50 */     this.targetSpeed = entityFlying.getMaxPoweredFlightSpeed();
/*  51 */     this.retina = new float[30][20];
/*  52 */     this.headingAppeal = new float[28][18];
/*  53 */     this.intermediateTarget = AABBPool.a(0.0D, 0.0D, 0.0D);
/*  54 */     this.isCircling = false;
/*  55 */     this.pitchBias = 0.0F;
/*  56 */     this.pitchBiasAmount = 0.0F;
/*  57 */     this.timeLookingForEntity = 0;
/*  58 */     this.precisionTarget = false;
/*  59 */     this.closestDistToTarget = 0.0F;
/*  60 */     this.timeSinceGotCloser = 0;
/*     */   }
/*     */ 
/*     */   public void setMovementType(INavigationFlying.MoveType moveType)
/*     */   {
/*  66 */     this.moveType = moveType;
/*     */   }
/*     */ 
/*     */   public void enableDirectTarget(boolean enabled)
/*     */   {
/*  76 */     this.precisionTarget = enabled;
/*     */   }
/*     */ 
/*     */   public void setLandingPath()
/*     */   {
/*  82 */     clearPath();
/*  83 */     this.moveType = INavigationFlying.MoveType.PREFER_WALKING;
/*  84 */     setWantsToBeFlying(false);
/*     */   }
/*     */ 
/*     */   public void setCirclingPath(nm target, float preferredHeight, float preferredRadius)
/*     */   {
/*  90 */     setCirclingPath(target.u, target.v, target.w, preferredHeight, preferredRadius);
/*     */   }
/*     */ 
/*     */   public void setCirclingPath(double x, double y, double z, float preferredHeight, float preferredRadius)
/*     */   {
/*  96 */     clearPath();
/*  97 */     this.finalTarget = AABBPool.a(x, y, z);
/*  98 */     this.circlingHeight = preferredHeight;
/*  99 */     this.circlingRadius = preferredRadius;
/* 100 */     this.isCircling = true;
/*     */   }
/*     */ 
/*     */   public float getDistanceToCirclingRadius()
/*     */   {
/* 106 */     double dX = this.finalTarget.listAABB - this.theEntity.posX;
/* 107 */     double dY = this.finalTarget.nextPoolIndex - this.theEntity.posY;
/* 108 */     double dZ = this.finalTarget.maxPoolIndex - this.theEntity.posZ;
/* 109 */     return (float)(Math.sqrt(dX * dX + dZ * dZ) - this.circlingRadius);
/*     */   }
/*     */ 
/*     */   public void setFlySpeed(float speed)
/*     */   {
/* 115 */     this.targetSpeed = speed;
/*     */   }
/*     */ 
/*     */   public void setPitchBias(float pitch, float biasAmount)
/*     */   {
/* 121 */     this.pitchBias = pitch;
/* 122 */     this.pitchBiasAmount = biasAmount;
/*     */   }
/*     */ 
/*     */   protected void updateAutoPathToEntity()
/*     */   {
/* 129 */     double dist = this.theEntity.d(this.pathEndEntity);
/* 130 */     if (dist < this.closestDistToTarget - 1.0F)
/*     */     {
/* 132 */       this.closestDistToTarget = ((float)dist);
/* 133 */       this.timeSinceGotCloser = 0;
/*     */     }
/*     */     else
/*     */     {
/* 137 */       this.timeSinceGotCloser += 1;
/*     */     }
/*     */ 
/* 142 */     boolean pathUpdate = false;
/* 143 */     boolean needsPathfinder = false;
/* 144 */     if (this.path != null)
/*     */     {
/* 146 */       double dSq = this.theEntity.e(this.pathEndEntity);
/* 147 */       if (((this.moveType == INavigationFlying.MoveType.PREFER_FLYING) || ((this.moveType == INavigationFlying.MoveType.MIXED) && (dSq > 100.0D))) && (this.theEntity.o(this.pathEndEntity)))
/*     */       {
/* 151 */         this.timeLookingForEntity = 0;
/* 152 */         pathUpdate = true;
/*     */       }
/*     */       else
/*     */       {
/* 158 */         double d1 = Distance.distanceBetween(this.pathEndEntity, this.pathEndEntityLastPos);
/* 159 */         double d2 = Distance.distanceBetween(this.theEntity, this.pathEndEntityLastPos);
/* 160 */         if (d1 / d2 > 0.1D) {
/* 161 */           pathUpdate = true;
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/* 166 */     else if ((this.moveType == INavigationFlying.MoveType.PREFER_WALKING) || (this.timeSinceGotCloser > 160) || (this.timeLookingForEntity > 600))
/*     */     {
/* 169 */       pathUpdate = true;
/* 170 */       needsPathfinder = true;
/* 171 */       this.timeSinceGotCloser = 0;
/* 172 */       this.timeLookingForEntity = 500;
/*     */     }
/* 174 */     else if (this.moveType == INavigationFlying.MoveType.MIXED)
/*     */     {
/* 176 */       double dSq = this.theEntity.e(this.pathEndEntity);
/* 177 */       if (dSq < 100.0D)
/*     */       {
/* 179 */         pathUpdate = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 184 */     if (pathUpdate)
/*     */     {
/* 186 */       if (this.moveType == INavigationFlying.MoveType.PREFER_FLYING)
/*     */       {
/* 188 */         if (needsPathfinder)
/*     */         {
/* 190 */           this.theEntity.setPathfindFlying(true);
/* 191 */           this.path = createPath(this.theEntity, this.pathEndEntity, 0.0F);
/* 192 */           if (this.path != null)
/*     */           {
/* 194 */             setWantsToBeFlying(true);
/* 195 */             setPath(this.path, this.moveSpeed);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 201 */           setWantsToBeFlying(true);
/* 202 */           resetStatus();
/*     */         }
/*     */       }
/* 205 */       else if (this.moveType == INavigationFlying.MoveType.MIXED)
/*     */       {
/* 208 */         this.theEntity.setPathfindFlying(false);
/* 209 */         Path path = createPath(this.theEntity, this.pathEndEntity, 0.0F);
/* 210 */         if ((path != null) && (path.getCurrentPathLength() < dist * 1.8D))
/*     */         {
/* 212 */           setWantsToBeFlying(false);
/* 213 */           setPath(path, this.moveSpeed);
/*     */         }
/* 215 */         else if (needsPathfinder)
/*     */         {
/* 217 */           this.theEntity.setPathfindFlying(true);
/* 218 */           path = createPath(this.theEntity, this.pathEndEntity, 0.0F);
/* 219 */           setWantsToBeFlying(true);
/* 220 */           if (path != null)
/* 221 */             setPath(path, this.moveSpeed);
/*     */           else {
/* 223 */             resetStatus();
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 228 */           setWantsToBeFlying(true);
/* 229 */           resetStatus();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 234 */         setWantsToBeFlying(false);
/* 235 */         this.theEntity.setPathfindFlying(false);
/* 236 */         Path path = createPath(this.theEntity, this.pathEndEntity, 0.0F);
/* 237 */         if (path != null)
/*     */         {
/* 239 */           setPath(path, this.moveSpeed);
/*     */         }
/*     */       }
/* 242 */       this.pathEndEntityLastPos = AABBPool.a(this.pathEndEntity.u, this.pathEndEntity.v, this.pathEndEntity.w);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void autoPathToEntity(nm target)
/*     */   {
/* 249 */     super.autoPathToEntity(target);
/* 250 */     this.isCircling = false;
/*     */   }
/*     */ 
/*     */   public boolean tryMoveToEntity(nm targetEntity, float targetRadius, float speed)
/*     */   {
/* 256 */     if (this.moveType != INavigationFlying.MoveType.PREFER_WALKING)
/*     */     {
/* 258 */       clearPath();
/* 259 */       this.pathEndEntity = targetEntity;
/* 260 */       this.finalTarget = AABBPool.a(this.pathEndEntity.u, this.pathEndEntity.v, this.pathEndEntity.w);
/* 261 */       this.isCircling = false;
/* 262 */       return true;
/*     */     }
/*     */ 
/* 266 */     this.theEntity.setPathfindFlying(false);
/* 267 */     return super.tryMoveToEntity(targetEntity, targetRadius, speed);
/*     */   }
/*     */ 
/*     */   public boolean tryMoveToXYZ(double x, double y, double z, float targetRadius, float speed)
/*     */   {
/* 274 */     AABBPool target = this.theEntity.q.V().a(x, y, z);
/* 275 */     if (this.moveType != INavigationFlying.MoveType.PREFER_WALKING)
/*     */     {
/* 277 */       clearPath();
/* 278 */       this.finalTarget = AABBPool.a(x, y, z);
/* 279 */       this.isCircling = false;
/* 280 */       return true;
/*     */     }
/*     */ 
/* 284 */     this.theEntity.setPathfindFlying(false);
/* 285 */     return super.tryMoveToXYZ(x, y, z, targetRadius, speed);
/*     */   }
/*     */ 
/*     */   public boolean tryMoveTowardsXZ(double x, double z, int min, int max, int verticalRange, float speed)
/*     */   {
/* 292 */     AABBPool target = findValidPointNear(x, z, min, max, verticalRange);
/* 293 */     if (target != null)
/*     */     {
/* 295 */       return tryMoveToXYZ(target.listAABB, target.nextPoolIndex, target.maxPoolIndex, 0.0F, speed);
/*     */     }
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */   public void clearPath()
/*     */   {
/* 303 */     super.clearPath();
/* 304 */     this.pathEndEntity = null;
/* 305 */     this.isCircling = false;
/*     */   }
/*     */ 
/*     */   public boolean isCircling()
/*     */   {
/* 311 */     return this.isCircling;
/*     */   }
/*     */ 
/*     */   public String getStatus()
/*     */   {
/* 317 */     if (!noPath())
/*     */     {
/* 319 */       return super.getStatus();
/*     */     }
/* 321 */     String s = "";
/* 322 */     if (isAutoPathingToEntity())
/*     */     {
/* 324 */       s = s + "Auto:";
/*     */     }
/*     */ 
/* 327 */     s = s + "Flyer:";
/* 328 */     if (this.isCircling)
/*     */     {
/* 330 */       s = s + "Circling:";
/*     */     }
/* 332 */     else if (this.wantsToBeFlying)
/*     */     {
/* 334 */       if (this.theEntity.getFlyState() == FlyState.TAKEOFF)
/* 335 */         s = s + "TakeOff:";
/*     */       else {
/* 337 */         s = s + "Flying:";
/*     */       }
/*     */ 
/*     */     }
/* 341 */     else if ((this.theEntity.getFlyState() == FlyState.LANDING) || (this.theEntity.getFlyState() == FlyState.TOUCHDOWN))
/* 342 */       s = s + "Landing:";
/*     */     else {
/* 344 */       s = s + "Ground";
/*     */     }
/* 346 */     return s;
/*     */   }
/*     */ 
/*     */   protected void pathFollow()
/*     */   {
/* 352 */     AABBPool vec3d = getEntityPosition();
/* 353 */     int maxNextLeg = this.path.getCurrentPathLength();
/*     */ 
/* 356 */     float fa = this.theEntity.width * 0.5F;
/* 357 */     for (int j = this.path.getCurrentPathIndex(); j < maxNextLeg; j++)
/*     */     {
/* 359 */       if (vec3d.e(this.path.getPositionAtIndex(this.theEntity, j)) < fa * fa)
/* 360 */         this.path.setCurrentPathIndex(j + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void noPathFollow()
/*     */   {
/* 366 */     if ((this.theEntity.getMoveState() != MoveState.FLYING) && (this.theEntity.getAIGoal() == Goal.CHILL))
/*     */     {
/* 368 */       setWantsToBeFlying(false);
/* 369 */       return;
/*     */     }
/*     */ 
/* 372 */     if (this.moveType == INavigationFlying.MoveType.PREFER_FLYING)
/* 373 */       setWantsToBeFlying(true);
/* 374 */     else if (this.moveType == INavigationFlying.MoveType.PREFER_WALKING) {
/* 375 */       setWantsToBeFlying(false);
/*     */     }
/* 377 */     if (++this.timeSinceVision >= this.visionUpdateRate)
/*     */     {
/* 379 */       this.timeSinceVision = 0;
/* 380 */       if ((!this.precisionTarget) || (this.pathEndEntity == null))
/* 381 */         updateHeading();
/*     */       else {
/* 383 */         updateHeadingDirectTarget(this.pathEndEntity);
/*     */       }
/* 385 */       this.intermediateTarget = convertToVector(this.targetYaw, this.targetPitch, this.targetSpeed);
/*     */     }
/* 387 */     this.theEntity.getMoveHelper().a(this.intermediateTarget.listAABB, this.intermediateTarget.nextPoolIndex, this.intermediateTarget.maxPoolIndex, this.targetSpeed);
/*     */   }
/*     */ 
/*     */   protected AABBPool convertToVector(float yaw, float pitch, float idealSpeed)
/*     */   {
/* 394 */     int time = this.visionUpdateRate + 20;
/* 395 */     double x = this.theEntity.posX + -Math.sin(yaw / 180.0F * 3.141592653589793D) * idealSpeed * time;
/* 396 */     double y = this.theEntity.posY + Math.sin(pitch / 180.0F * 3.141592653589793D) * idealSpeed * time;
/* 397 */     double z = this.theEntity.posZ + Math.cos(yaw / 180.0F * 3.141592653589793D) * idealSpeed * time;
/* 398 */     return AABBPool.a(x, y, z);
/*     */   }
/*     */ 
/*     */   protected void updateHeading()
/*     */   {
/* 405 */     float pixelDegreeH = 10.0F;
/* 406 */     float pixelDegreeV = 11.0F;
/* 407 */     for (int i = 0; i < 30; i++)
/*     */     {
/* 409 */       double nextAngleH = i * pixelDegreeH + 0.5D * pixelDegreeH - 150.0D + this.theEntity.rotationYaw;
/* 410 */       for (int j = 0; j < 20; j++)
/*     */       {
/* 412 */         double nextAngleV = j * pixelDegreeV + 0.5D * pixelDegreeV - 110.0D;
/* 413 */         double y = this.theEntity.posY + Math.sin(nextAngleV / 180.0D * 3.141592653589793D) * this.visionDistance;
/* 414 */         double distanceXZ = Math.cos(nextAngleV / 180.0D * 3.141592653589793D) * this.visionDistance;
/* 415 */         double x = this.theEntity.posX + -Math.sin(nextAngleH / 180.0D * 3.141592653589793D) * distanceXZ;
/* 416 */         double z = this.theEntity.posZ + Math.cos(nextAngleH / 180.0D * 3.141592653589793D) * distanceXZ;
/* 417 */         AABBPool target = this.theEntity.q.V().a(x, y, z);
/* 418 */         AABBPool origin = this.theEntity.l(1.0F);
/* 419 */         origin.nextPoolIndex += 1.0D;
/*     */ 
/* 423 */         AxisAlignedBB object = this.theEntity.q.a(origin, target);
/* 424 */         if ((object != null) && (object.minX == AABBLocalPool.a))
/*     */         {
/* 426 */           double dX = this.theEntity.posX - object.minY;
/* 427 */           double dZ = this.theEntity.posY - object.minZ;
/* 428 */           double dY = this.theEntity.posZ - object.maxX;
/* 429 */           this.retina[i][j] = ((float)Math.sqrt(dX * dX + dY * dY + dZ * dZ));
/*     */         }
/*     */         else
/*     */         {
/* 434 */           this.retina[i][j] = (this.visionDistance + 1.0F);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 442 */     for (int i = 1; i < 29; i++)
/*     */     {
/* 444 */       for (int j = 1; j < 19; j++)
/*     */       {
/* 446 */         float appeal = this.retina[i][j];
/* 447 */         appeal += this.retina[(i - 1)][(j - 1)];
/* 448 */         appeal += this.retina[(i - 1)][j];
/* 449 */         appeal += this.retina[(i - 1)][(j + 1)];
/* 450 */         appeal += this.retina[i][(j - 1)];
/* 451 */         appeal += this.retina[i][(j + 1)];
/* 452 */         appeal += this.retina[(i + 1)][(j - 1)];
/* 453 */         appeal += this.retina[(i + 1)][j];
/* 454 */         appeal += this.retina[(i + 1)][(j + 1)];
/* 455 */         appeal /= 9.0F;
/* 456 */         this.headingAppeal[(i - 1)][(j - 1)] = appeal;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 461 */     if (this.isCircling)
/*     */     {
/* 463 */       double dX = this.finalTarget.listAABB - this.theEntity.posX;
/* 464 */       double dY = this.finalTarget.nextPoolIndex - this.theEntity.posY;
/* 465 */       double dZ = this.finalTarget.maxPoolIndex - this.theEntity.posZ;
/* 466 */       double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/*     */ 
/* 472 */       if ((dXZ > 0.0D) && (dXZ > this.circlingRadius * 0.6D))
/*     */       {
/* 474 */         double intersectRadius = Math.abs((this.circlingRadius - dXZ) * 2.0D) + 8.0D;
/* 475 */         if (intersectRadius > this.circlingRadius * 1.8D) {
/* 476 */           intersectRadius = dXZ + 5.0D;
/*     */         }
/*     */ 
/* 479 */         float preferredYaw1 = (float)(Math.acos((dXZ * dXZ - this.circlingRadius * this.circlingRadius + intersectRadius * intersectRadius) / (2.0D * dXZ) / intersectRadius) * 180.0D / 3.141592653589793D);
/* 480 */         float preferredYaw2 = -preferredYaw1;
/*     */ 
/* 483 */         double dYaw = Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D;
/* 484 */         preferredYaw1 = (float)(preferredYaw1 + dYaw);
/* 485 */         preferredYaw2 = (float)(preferredYaw2 + dYaw);
/*     */ 
/* 488 */         float preferredPitch = (float)(Math.atan((dY + this.circlingHeight) / intersectRadius) * 180.0D / 3.141592653589793D);
/*     */ 
/* 492 */         float yawBias = (float)(1.5D * Math.abs(dXZ - this.circlingRadius) / this.circlingRadius);
/* 493 */         float pitchBias = (float)(1.9D * Math.abs((dY + this.circlingHeight) / this.circlingHeight));
/*     */ 
/* 496 */         doHeadingBiasPass(this.headingAppeal, preferredYaw1, preferredYaw2, preferredPitch, yawBias, pitchBias);
/*     */       }
/*     */       else
/*     */       {
/* 500 */         float yawToTarget = (float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D);
/* 501 */         yawToTarget += 180.0F;
/* 502 */         float preferredPitch = (float)(Math.atan((dY + this.circlingHeight) / Math.abs(this.circlingRadius - dXZ)) * 180.0D / 3.141592653589793D);
/* 503 */         float yawBias = (float)(0.5D * Math.abs(dXZ - this.circlingRadius) / this.circlingRadius);
/* 504 */         float pitchBias = (float)(0.9D * Math.abs((dY + this.circlingHeight) / this.circlingHeight));
/* 505 */         doHeadingBiasPass(this.headingAppeal, yawToTarget, yawToTarget, preferredPitch, yawBias, pitchBias);
/*     */       }
/*     */     }
/* 508 */     else if (this.pathEndEntity != null)
/*     */     {
/* 510 */       double dX = this.pathEndEntity.u - this.theEntity.posX;
/* 511 */       double dY = this.pathEndEntity.v - this.theEntity.posY;
/* 512 */       double dZ = this.pathEndEntity.w - this.theEntity.posZ;
/* 513 */       double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/* 514 */       float yawToTarget = (float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D);
/* 515 */       float pitchToTarget = (float)(Math.atan(dY / dXZ) * 180.0D / 3.141592653589793D);
/* 516 */       doHeadingBiasPass(this.headingAppeal, yawToTarget, yawToTarget, pitchToTarget, 20.6F, 20.6F);
/*     */     }
/*     */ 
/* 520 */     if (this.pathEndEntity == null)
/*     */     {
/* 523 */       float dOldYaw = this.targetYaw - this.theEntity.rotationYaw;
/* 524 */       MathUtil.boundAngle180Deg(dOldYaw);
/* 525 */       float dOldPitch = this.targetPitch;
/* 526 */       float approxLastTargetX = dOldYaw / pixelDegreeH + 14.0F;
/* 527 */       float approxLastTargetY = dOldPitch / pixelDegreeV + 9.0F;
/* 528 */       if (approxLastTargetX > 28.0F)
/* 529 */         approxLastTargetX = 28.0F;
/* 530 */       else if (approxLastTargetX < 0.0F) {
/* 531 */         approxLastTargetX = 0.0F;
/*     */       }
/* 533 */       if (approxLastTargetY > 18.0F)
/* 534 */         approxLastTargetY = 18.0F;
/* 535 */       else if (approxLastTargetY < 0.0F) {
/* 536 */         approxLastTargetY = 0.0F;
/*     */       }
/* 538 */       float statusQuoBias = 0.4F;
/* 539 */       float falloffDist = 30.0F;
/* 540 */       for (int i = 0; i < 28; i++)
/*     */       {
/* 542 */         float dXSq = (approxLastTargetX - i) * (approxLastTargetX - i);
/* 543 */         for (int j = 0; j < 18; j++)
/*     */         {
/* 545 */           float dY = approxLastTargetY - j;
/*     */           int tmp1306_1304 = j;
/*     */           float[] tmp1306_1303 = this.headingAppeal[i]; tmp1306_1303[tmp1306_1304] = ((float)(tmp1306_1303[tmp1306_1304] * (1.0F + statusQuoBias - statusQuoBias * Math.sqrt(dXSq + dY * dY) / falloffDist)));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 551 */     if (this.pitchBias != 0.0F)
/*     */     {
/* 553 */       doHeadingBiasPass(this.headingAppeal, 0.0F, 0.0F, this.pitchBias, 0.0F, this.pitchBiasAmount);
/*     */     }
/*     */ 
/* 557 */     if (!this.wantsToBeFlying)
/*     */     {
/* 559 */       Pair landingInfo = appraiseLanding();
/* 560 */       if (((Float)landingInfo.getVal2()).floatValue() < 4.0F)
/*     */       {
/* 562 */         if (((Float)landingInfo.getVal1()).floatValue() >= 0.9F)
/* 563 */           doHeadingBiasPass(this.headingAppeal, 0.0F, 0.0F, -45.0F, 0.0F, 3.5F);
/* 564 */         else if (((Float)landingInfo.getVal1()).floatValue() >= 0.65F) {
/* 565 */           doHeadingBiasPass(this.headingAppeal, 0.0F, 0.0F, -15.0F, 0.0F, 0.4F);
/*     */         }
/*     */ 
/*     */       }
/* 569 */       else if (((Float)landingInfo.getVal1()).floatValue() >= 0.52F) {
/* 570 */         doHeadingBiasPass(this.headingAppeal, 0.0F, 0.0F, -15.0F, 0.0F, 0.8F);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 575 */     Pair bestPixel = chooseCoordinate();
/* 576 */     this.targetYaw = (this.theEntity.rotationYaw - 150.0F + (((Integer)bestPixel.getVal1()).intValue() + 1) * pixelDegreeH + 0.5F * pixelDegreeH);
/* 577 */     this.targetPitch = (-110.0F + (((Integer)bestPixel.getVal2()).intValue() + 1) * pixelDegreeV + 0.5F * pixelDegreeV);
/*     */   }
/*     */ 
/*     */   protected void updateHeadingDirectTarget(nm target)
/*     */   {
/* 582 */     double dX = target.u - this.theEntity.posX;
/* 583 */     double dY = target.v - this.theEntity.posY;
/* 584 */     double dZ = target.w - this.theEntity.posZ;
/* 585 */     double dXZ = Math.sqrt(dX * dX + dZ * dZ);
/* 586 */     this.targetYaw = ((float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D));
/* 587 */     this.targetPitch = ((float)(Math.atan(dY / dXZ) * 180.0D / 3.141592653589793D));
/*     */   }
/*     */ 
/*     */   protected Pair<Integer, Integer> chooseCoordinate()
/*     */   {
/* 592 */     int bestPixelX = 0;
/* 593 */     int bestPixelY = 0;
/* 594 */     for (int i = 0; i < 28; i++)
/*     */     {
/* 596 */       for (int j = 0; j < 18; j++)
/*     */       {
/* 598 */         if (this.headingAppeal[bestPixelX][bestPixelY] < this.headingAppeal[i][j])
/*     */         {
/* 600 */           bestPixelX = i;
/* 601 */           bestPixelY = j;
/*     */         }
/*     */       }
/*     */     }
/* 605 */     return new Pair(Integer.valueOf(bestPixelX), Integer.valueOf(bestPixelY));
/*     */   }
/*     */ 
/*     */   protected void setTarget(double x, double y, double z)
/*     */   {
/* 610 */     this.intermediateTarget = AABBPool.a(x, y, z);
/*     */   }
/*     */ 
/*     */   protected AABBPool getTarget()
/*     */   {
/* 615 */     return this.intermediateTarget;
/*     */   }
/*     */ 
/*     */   protected void doHeadingBiasPass(float[][] array, float preferredYaw1, float preferredYaw2, float preferredPitch, float yawBias, float pitchBias)
/*     */   {
/* 620 */     float pixelDegreeH = 10.0F;
/* 621 */     float pixelDegreeV = 11.0F;
/* 622 */     for (int i = 0; i < array.length; i++)
/*     */     {
/* 624 */       double nextAngleH = (i + 1) * pixelDegreeH + 0.5D * pixelDegreeH - 150.0D + this.theEntity.rotationYaw;
/* 625 */       double dYaw1 = MathUtil.boundAngle180Deg(preferredYaw1 - nextAngleH);
/* 626 */       double dYaw2 = MathUtil.boundAngle180Deg(preferredYaw2 - nextAngleH);
/* 627 */       double yawBiasAmount = 1.0D + Math.min(Math.abs(dYaw1), Math.abs(dYaw2)) * yawBias / 180.0D;
/* 628 */       for (int j = 0; j < array[0].length; tmp162_159++)
/*     */       {
/* 630 */         double nextAngleV = (j + 1) * pixelDegreeV + 0.5D * pixelDegreeV - 110.0D;
/* 631 */         double pitchBiasAmount = 1.0D + Math.abs(MathUtil.boundAngle180Deg(preferredPitch - nextAngleV)) * pitchBias / 180.0D;
/*     */         int tmp162_160 = j;
/*     */         float[] tmp162_159 = array[i]; tmp162_159[tmp162_160] = ((float)(tmp162_159[tmp162_160] / (yawBiasAmount * pitchBiasAmount)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setWantsToBeFlying(boolean flag)
/*     */   {
/* 639 */     this.wantsToBeFlying = flag;
/* 640 */     this.theEntity.getMoveHelper().setWantsToBeFlying(flag);
/*     */   }
/*     */ 
/*     */   private Pair<Float, Float> appraiseLanding()
/*     */   {
/* 645 */     float safety = 0.0F;
/* 646 */     float distance = 0.0F;
/* 647 */     int landingResolution = 3;
/* 648 */     double nextAngleH = this.theEntity.rotationYaw;
/* 649 */     for (int i = 0; i < landingResolution; i++)
/*     */     {
/* 651 */       double nextAngleV = -90 + i * 30 / landingResolution;
/* 652 */       double y = this.theEntity.posY + Math.sin(nextAngleV / 180.0D * 3.141592653589793D) * 64.0D;
/* 653 */       double distanceXZ = Math.cos(nextAngleV / 180.0D * 3.141592653589793D) * 64.0D;
/* 654 */       double x = this.theEntity.posX + -Math.sin(nextAngleH / 180.0D * 3.141592653589793D) * distanceXZ;
/* 655 */       double z = this.theEntity.posZ + Math.cos(nextAngleH / 180.0D * 3.141592653589793D) * distanceXZ;
/* 656 */       AABBPool target = this.theEntity.q.V().a(x, y, z);
/* 657 */       AABBPool origin = this.theEntity.l(1.0F);
/* 658 */       AxisAlignedBB object = this.theEntity.q.a(origin, target);
/* 659 */       if (object != null)
/*     */       {
/* 661 */         int id = this.theEntity.q.a(object.minY, object.minZ, object.maxX);
/* 662 */         if (!this.theEntity.avoidsBlock(id)) {
/* 663 */           safety += 0.7F;
/*     */         }
/* 665 */         if (object.maxY == 1) {
/* 666 */           safety += 0.3F;
/*     */         }
/* 668 */         double dX = object.minY - this.theEntity.posX;
/* 669 */         double dY = object.minZ - this.theEntity.posY;
/* 670 */         double dZ = object.maxX - this.theEntity.posZ;
/* 671 */         distance = (float)(distance + Math.sqrt(dX * dX + dY * dY + dZ * dZ));
/*     */       }
/*     */       else
/*     */       {
/* 675 */         distance += 64.0F;
/*     */       }
/*     */     }
/* 678 */     distance /= landingResolution;
/* 679 */     safety /= landingResolution;
/* 680 */     return new Pair(Float.valueOf(safety), Float.valueOf(distance));
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.NavigatorFlying
 * JD-Core Version:    0.6.2
 */