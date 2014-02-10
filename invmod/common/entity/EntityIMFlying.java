/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IBlockAccessExtended;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.MathUtil;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public abstract class EntityIMFlying extends EntityIMLiving
/*     */ {
/*     */   private static final int META_TARGET_X = 29;
/*     */   private static final int META_TARGET_Y = 30;
/*     */   private static final int META_TARGET_Z = 31;
/*     */   private static final int META_THRUST_DATA = 28;
/*     */   private static final int META_FLYSTATE = 27;
/*     */   private final NavigatorFlying navigatorFlying;
/*     */   private final IMMoveHelperFlying i;
/*     */   private final IMLookHelper h;
/*     */   private final IMBodyHelper bn;
/*     */   private FlyState flyState;
/*     */   private float liftFactor;
/*     */   private float maxPoweredFlightSpeed;
/*     */   private float thrust;
/*     */   private float thrustComponentRatioMin;
/*     */   private float thrustComponentRatioMax;
/*     */   private float maxTurnForce;
/*     */   private float optimalPitch;
/*     */   private float landingSpeedThreshold;
/*     */   private float maxRunSpeed;
/*     */   private float flightAccelX;
/*     */   private float flightAccelY;
/*     */   private float flightAccelZ;
/*     */   private boolean thrustOn;
/*     */   private float thrustEffort;
/*     */   private boolean flyPathfind;
/*     */   private boolean debugFlying;
/*     */ 
/*     */   public EntityIMFlying(ColorizerGrass world)
/*     */   {
/*  45 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMFlying(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  50 */     super(world, nexus);
/*  51 */     this.debugFlying = true;
/*  52 */     this.flyState = FlyState.GROUNDED;
/*  53 */     this.maxPoweredFlightSpeed = 0.28F;
/*  54 */     this.liftFactor = 0.4F;
/*  55 */     this.thrust = 0.08F;
/*  56 */     this.thrustComponentRatioMin = 0.0F;
/*  57 */     this.thrustComponentRatioMax = 0.1F;
/*  58 */     this.maxTurnForce = (getGravity() * 3.0F);
/*  59 */     this.optimalPitch = 52.0F;
/*  60 */     this.landingSpeedThreshold = (getMoveSpeedStat() * 1.2F);
/*  61 */     this.maxRunSpeed = 0.45F;
/*  62 */     this.thrustOn = false;
/*  63 */     this.thrustEffort = 1.0F;
/*  64 */     this.flyPathfind = true;
/*     */ 
/*  66 */     this.i = new IMMoveHelperFlying(this);
/*  67 */     this.h = new IMLookHelper(this);
/*  68 */     this.bn = new IMBodyHelper(this);
/*  69 */     IPathSource pathSource = getPathSource();
/*  70 */     pathSource.setSearchDepth(800);
/*  71 */     pathSource.setQuickFailDepth(200);
/*  72 */     this.navigatorFlying = new NavigatorFlying(this, pathSource);
/*     */ 
/*  74 */     this.ah.a(29, Integer.valueOf(0));
/*  75 */     this.ah.a(30, Integer.valueOf(0));
/*  76 */     this.ah.a(31, Integer.valueOf(0));
/*  77 */     this.ah.a(28, Byte.valueOf((byte)0));
/*  78 */     this.ah.a(27, Integer.valueOf(this.flyState.ordinal()));
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  85 */     super.onUpdate();
/*  86 */     if (!this.q.I)
/*     */     {
/*  88 */       if (this.debugFlying)
/*     */       {
/*  90 */         AABBPool target = this.navigatorFlying.getTarget();
/*  91 */         float oldTargetX = MathUtil.unpackFloat(this.ah.c(29));
/*  92 */         float oldTargetY = MathUtil.unpackFloat(this.ah.c(30));
/*  93 */         float oldTargetZ = MathUtil.unpackFloat(this.ah.c(31));
/*     */ 
/*  96 */         if ((!MathUtil.floatEquals(oldTargetX, (float)target.listAABB, 0.1F)) || (!MathUtil.floatEquals(oldTargetY, (float)target.nextPoolIndex, 0.1F)) || (!MathUtil.floatEquals(oldTargetZ, (float)target.maxPoolIndex, 0.1F)))
/*     */         {
/* 100 */           this.ah.b(29, Integer.valueOf(MathUtil.packFloat((float)target.listAABB)));
/* 101 */           this.ah.b(30, Integer.valueOf(MathUtil.packFloat((float)target.nextPoolIndex)));
/* 102 */           this.ah.b(31, Integer.valueOf(MathUtil.packFloat((float)target.maxPoolIndex)));
/*     */         }
/*     */       }
/*     */ 
/* 106 */       byte thrustData = this.ah.a(28);
/* 107 */       int oldThrustOn = thrustData & 0x1;
/* 108 */       int oldThrustEffortEncoded = thrustData >> 1 & 0xF;
/* 109 */       int thrustEffortEncoded = (int)(this.thrustEffort * 15.0F);
/* 110 */       if (this.thrustOn == oldThrustOn > 0) { if (thrustEffortEncoded == oldThrustEffortEncoded);
/*     */       } else {
/* 112 */         this.ah.b(28, Byte.valueOf((byte)(thrustEffortEncoded << 1 | oldThrustOn)));
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 119 */       if (this.debugFlying)
/*     */       {
/* 121 */         float x = MathUtil.unpackFloat(this.ah.c(29));
/* 122 */         float y = MathUtil.unpackFloat(this.ah.c(30));
/* 123 */         float z = MathUtil.unpackFloat(this.ah.c(31));
/* 124 */         this.navigatorFlying.setTarget(x, y, z);
/*     */       }
/*     */ 
/* 127 */       this.flyState = FlyState.values()[this.ah.c(27)];
/*     */ 
/* 129 */       byte thrustData = this.ah.a(28);
/* 130 */       this.thrustOn = ((thrustData & 0x1) > 0);
/* 131 */       this.thrustEffort = ((thrustData >> 1 & 0xF) / 15.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   public FlyState getFlyState()
/*     */   {
/* 138 */     return this.flyState;
/*     */   }
/*     */ 
/*     */   public boolean isThrustOn()
/*     */   {
/* 143 */     return this.ah.a(28) != 0;
/*     */   }
/*     */ 
/*     */   public float getThrustEffort()
/*     */   {
/* 148 */     return this.thrustEffort;
/*     */   }
/*     */ 
/*     */   public AABBPool getFlyTarget()
/*     */   {
/* 153 */     return this.navigatorFlying.getTarget();
/*     */   }
/*     */ 
/*     */   public INavigationFlying getNavigatorNew()
/*     */   {
/* 161 */     return this.navigatorFlying;
/*     */   }
/*     */ 
/*     */   public IMMoveHelperFlying getMoveHelper()
/*     */   {
/* 166 */     return this.i;
/*     */   }
/*     */ 
/*     */   public IMLookHelper getLookHelper()
/*     */   {
/* 171 */     return this.h;
/*     */   }
/*     */ 
/*     */   public IMBodyHelper getBodyHelper()
/*     */   {
/* 176 */     return this.bn;
/*     */   }
/*     */ 
/*     */   public void moveEntityWithHeading(float x, float z)
/*     */   {
/* 184 */     if (isWet())
/*     */     {
/* 186 */       double y = this.posY;
/* 187 */       moveFlying(x, z, be() ? 0.04F : 0.02F);
/* 188 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 189 */       this.motionX *= 0.8D;
/* 190 */       this.motionY *= 0.8D;
/* 191 */       this.motionZ *= 0.8D;
/* 192 */       this.motionY -= 0.02D;
/* 193 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/* 194 */         this.motionY = 0.3D;
/*     */     }
/* 196 */     else if (handleWaterMovement())
/*     */     {
/* 198 */       double y = this.posY;
/* 199 */       moveFlying(x, z, be() ? 0.04F : 0.02F);
/* 200 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 201 */       this.motionX *= 0.5D;
/* 202 */       this.motionY *= 0.5D;
/* 203 */       this.motionZ *= 0.5D;
/* 204 */       this.motionY -= 0.02D;
/* 205 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/* 206 */         this.motionY = 0.3D;
/*     */     }
/*     */     else
/*     */     {
/* 210 */       float groundFriction = 0.9995F;
/*     */ 
/* 212 */       if (this.onGround)
/*     */       {
/* 214 */         groundFriction = getGroundFriction();
/*     */ 
/* 219 */         float maxRunSpeed = getMaxRunSpeed();
/* 220 */         if (this.motionX * this.motionX + this.motionZ * this.motionZ < maxRunSpeed * maxRunSpeed)
/*     */         {
/* 222 */           float landMoveSpeed = bf();
/* 223 */           landMoveSpeed *= 0.162771F / (groundFriction * groundFriction * groundFriction);
/* 224 */           moveFlying(x, z, landMoveSpeed);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 229 */         moveFlying(x, z, 0.01F);
/*     */       }
/*     */ 
/* 232 */       this.motionX += this.flightAccelX;
/* 233 */       this.motionY += this.flightAccelY;
/* 234 */       this.motionZ += this.flightAccelZ;
/*     */ 
/* 236 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 237 */       this.motionY -= getGravity();
/* 238 */       this.motionY *= getAirResistance();
/* 239 */       this.motionX *= groundFriction * getAirResistance();
/* 240 */       this.motionZ *= groundFriction * getAirResistance();
/*     */     }
/*     */ 
/* 243 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 244 */     double dX = this.posX - this.prevPosX;
/* 245 */     double dZ = this.posZ - this.prevPosZ;
/* 246 */     float limbEnergy = LongHashMapEntry.a(dX * dX + dZ * dZ) * 4.0F;
/*     */ 
/* 248 */     if (limbEnergy > 1.0F)
/*     */     {
/* 250 */       limbEnergy = 1.0F;
/*     */     }
/*     */ 
/* 253 */     this.limbSwingAmount += (limbEnergy - this.limbSwingAmount) * 0.4F;
/* 254 */     this.limbSwing += this.limbSwingAmount;
/*     */   }
/*     */ 
/*     */   public boolean isOnLadder()
/*     */   {
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean hasFlyingDebug()
/*     */   {
/* 267 */     return this.debugFlying;
/*     */   }
/*     */ 
/*     */   protected void setPathfindFlying(boolean flag)
/*     */   {
/* 272 */     this.flyPathfind = flag;
/*     */   }
/*     */ 
/*     */   protected void setFlyState(FlyState flyState)
/*     */   {
/* 277 */     this.flyState = flyState;
/* 278 */     if (!this.q.I)
/* 279 */       this.ah.b(27, Integer.valueOf(flyState.ordinal()));
/*     */   }
/*     */ 
/*     */   protected float getMaxPoweredFlightSpeed()
/*     */   {
/* 284 */     return this.maxPoweredFlightSpeed;
/*     */   }
/*     */ 
/*     */   protected float getLiftFactor()
/*     */   {
/* 289 */     return this.liftFactor;
/*     */   }
/*     */ 
/*     */   protected float getThrust()
/*     */   {
/* 294 */     return this.thrust;
/*     */   }
/*     */ 
/*     */   protected float getThrustComponentRatioMin()
/*     */   {
/* 299 */     return this.thrustComponentRatioMin;
/*     */   }
/*     */ 
/*     */   protected float getThrustComponentRatioMax()
/*     */   {
/* 304 */     return this.thrustComponentRatioMax;
/*     */   }
/*     */ 
/*     */   protected float getMaxTurnForce()
/*     */   {
/* 309 */     return this.maxTurnForce;
/*     */   }
/*     */ 
/*     */   protected float getMaxPitch()
/*     */   {
/* 314 */     return this.optimalPitch;
/*     */   }
/*     */ 
/*     */   protected float getLandingSpeedThreshold()
/*     */   {
/* 319 */     return this.landingSpeedThreshold;
/*     */   }
/*     */ 
/*     */   protected float getMaxRunSpeed()
/*     */   {
/* 324 */     return this.maxRunSpeed;
/*     */   }
/*     */ 
/*     */   protected void setFlightAccelerationVector(float xAccel, float yAccel, float zAccel)
/*     */   {
/* 329 */     this.flightAccelX = xAccel;
/* 330 */     this.flightAccelY = yAccel;
/* 331 */     this.flightAccelZ = zAccel;
/*     */   }
/*     */ 
/*     */   protected void setThrustOn(boolean flag)
/*     */   {
/* 336 */     this.thrustOn = flag;
/*     */   }
/*     */ 
/*     */   protected void setThrustEffort(float effortFactor)
/*     */   {
/* 341 */     this.thrustEffort = effortFactor;
/*     */   }
/*     */ 
/*     */   protected void setMaxPoweredFlightSpeed(float speed)
/*     */   {
/* 346 */     this.maxPoweredFlightSpeed = speed;
/* 347 */     getNavigatorNew().setFlySpeed(speed);
/*     */   }
/*     */ 
/*     */   protected void setThrust(float thrust)
/*     */   {
/* 352 */     this.thrust = thrust;
/*     */   }
/*     */ 
/*     */   protected void setLiftFactor(float liftFactor)
/*     */   {
/* 357 */     this.liftFactor = liftFactor;
/*     */   }
/*     */ 
/*     */   protected void setThrustComponentRatioMin(float ratio)
/*     */   {
/* 362 */     this.thrustComponentRatioMin = ratio;
/*     */   }
/*     */ 
/*     */   protected void setThrustComponentRatioMax(float ratio)
/*     */   {
/* 367 */     this.thrustComponentRatioMax = ratio;
/*     */   }
/*     */ 
/*     */   protected void setMaxTurnForce(float maxTurnForce)
/*     */   {
/* 372 */     this.maxTurnForce = maxTurnForce;
/*     */   }
/*     */ 
/*     */   protected void setOptimalPitch(float pitch)
/*     */   {
/* 377 */     this.optimalPitch = pitch;
/*     */   }
/*     */ 
/*     */   protected void setLandingSpeedThreshold(float speed)
/*     */   {
/* 382 */     this.landingSpeedThreshold = speed;
/*     */   }
/*     */ 
/*     */   protected void setMaxRunSpeed(float speed)
/*     */   {
/* 387 */     this.maxRunSpeed = speed;
/*     */   }
/*     */ 
/*     */   protected void fall(float par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void updateFallState(double par1, boolean par3)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void calcPathOptions(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 404 */     if (!this.flyPathfind)
/* 405 */       super.calcPathOptions(terrainMap, currentNode, pathFinder);
/*     */     else
/* 407 */       calcPathOptionsFlying(terrainMap, currentNode, pathFinder);
/*     */   }
/*     */ 
/*     */   protected void calcPathOptionsFlying(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 412 */     if ((currentNode.yCoord <= 0) || (currentNode.yCoord > 255)) {
/* 413 */       return;
/*     */     }
/*     */ 
/* 416 */     if (getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord) > 0) {
/* 417 */       pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.NONE);
/*     */     }
/*     */ 
/* 420 */     if (getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord - 1, currentNode.zCoord) > 0) {
/* 421 */       pathFinder.addNode(currentNode.xCoord, currentNode.yCoord - 1, currentNode.zCoord, PathAction.NONE);
/*     */     }
/*     */ 
/* 424 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 426 */       if (getCollide(terrainMap, currentNode.xCoord + invmod.common.util.CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + invmod.common.util.CoordsInt.offsetAdjZ[i]) > 0) {
/* 427 */         pathFinder.addNode(currentNode.xCoord + invmod.common.util.CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + invmod.common.util.CoordsInt.offsetAdjZ[i], PathAction.NONE);
/*     */       }
/*     */     }
/* 430 */     if (canSwimHorizontal())
/*     */     {
/* 432 */       for (int i = 0; i < 4; i++)
/*     */       {
/* 434 */         if (getCollide(terrainMap, currentNode.xCoord + invmod.common.util.CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + invmod.common.util.CoordsInt.offsetAdjZ[i]) == -1)
/* 435 */           pathFinder.addNode(currentNode.xCoord + invmod.common.util.CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + invmod.common.util.CoordsInt.offsetAdjZ[i], PathAction.SWIM);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected float calcBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*     */   {
/* 442 */     float multiplier = 1.0F;
/* 443 */     if ((terrainMap instanceof IBlockAccessExtended))
/*     */     {
/* 445 */       int mobDensity = ((IBlockAccessExtended)terrainMap).getLayeredData(node.xCoord, node.yCoord, node.zCoord) & 0x7;
/* 446 */       multiplier += mobDensity * 3;
/*     */     }
/*     */ 
/* 450 */     for (int i = -1; i > -6; i--)
/*     */     {
/* 452 */       int id = terrainMap.a(node.xCoord, node.yCoord + i, node.zCoord);
/*     */       int i;
/*     */       int id;
/* 453 */       if (id != 0)
/*     */       {
/* 455 */         int blockType = getBlockType(id);
/* 456 */         if (blockType != 1)
/*     */         {
/* 460 */           multiplier += 1.0F - -i * 0.2F;
/* 461 */           if ((blockType != 2) || (i < -2))
/*     */             break;
/* 463 */           multiplier = (float)(multiplier + (6.0D - -i * 2.0D)); break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 470 */     for (i = 0; i < 4; i++)
/*     */     {
/* 472 */       for (int j = 1; j <= 2; j++)
/*     */       {
/* 474 */         int id = terrainMap.a(node.xCoord + invmod.common.util.CoordsInt.offsetAdjX[i] * j, node.yCoord, node.zCoord + invmod.common.util.CoordsInt.offsetAdjZ[i] * j);
/* 475 */         int blockType = getBlockType(id);
/* 476 */         if (blockType != 1)
/*     */         {
/* 480 */           multiplier += 1.5F - j * 0.5F;
/* 481 */           if ((blockType != 2) || (i < -2))
/*     */             break;
/* 483 */           multiplier += 6.0F - j * 2.0F; break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 501 */     if (node.action == PathAction.SWIM)
/*     */     {
/* 504 */       multiplier *= ((node.yCoord <= prevNode.yCoord) && (terrainMap.a(node.xCoord, node.yCoord + 1, node.zCoord) != 0) ? 3.0F : 1.0F);
/* 505 */       return prevNode.distanceTo(node) * 1.3F * multiplier;
/*     */     }
/*     */ 
/* 508 */     id = terrainMap.a(node.xCoord, node.yCoord, node.zCoord);
/* 509 */     if (blockCosts.containsKey(Integer.valueOf(id)))
/*     */     {
/* 511 */       return prevNode.distanceTo(node) * ((Float)blockCosts.get(Integer.valueOf(id))).floatValue() * multiplier;
/*     */     }
/* 513 */     if (BlockEndPortal.s[id].isCollidable())
/*     */     {
/* 515 */       return prevNode.distanceTo(node) * 3.2F * multiplier;
/*     */     }
/*     */ 
/* 519 */     return prevNode.distanceTo(node) * 1.0F * multiplier;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMFlying
 * JD-Core Version:    0.6.2
 */