/*      */ package invmod.common.entity;
/*      */ 
/*      */ import invmod.common.IBlockAccessExtended;
/*      */ import invmod.common.INotifyTask;
/*      */ import invmod.common.IPathfindable;
/*      */ import invmod.common.SparrowAPI;
/*      */ import invmod.common.mod_Invasion;
/*      */ import invmod.common.nexus.INexusAccess;
/*      */ import invmod.common.util.CoordsInt;
/*      */ import invmod.common.util.Distance;
/*      */ import invmod.common.util.IPosition;
/*      */ import invmod.common.util.MathUtil;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockEndPortal;
/*      */ import net.minecraft.block.BlockPistonExtension;
/*      */ import net.minecraft.block.material.MaterialLogic;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityCreature;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.EntityJumpHelper;
/*      */ import net.minecraft.entity.monster.EntityEnderman;
/*      */ import net.minecraft.entity.player.CallableItemName;
/*      */ import net.minecraft.item.EnumToolMaterial;
/*      */ import net.minecraft.nbt.NBTTagByte;
/*      */ import net.minecraft.pathfinding.PathNavigate;
/*      */ import net.minecraft.src.anq;
/*      */ import net.minecraft.src.lu;
/*      */ import net.minecraft.src.nm;
/*      */ import net.minecraft.src.pc;
/*      */ import net.minecraft.util.CombatTracker;
/*      */ import net.minecraft.util.EnumArt;
/*      */ import net.minecraft.util.LongHashMapEntry;
/*      */ import net.minecraft.world.ColorizerGrass;
/*      */ import net.minecraft.world.EnumGameType;
/*      */ import net.minecraft.world.WorldType;
/*      */ 
/*      */ public abstract class EntityIMLiving extends EnumArt
/*      */   implements EntityEnderman, IPathfindable, IPosition, IHasNexus, SparrowAPI
/*      */ {
/*      */   private final NavigatorIM bo;
/*      */   private final PathNavigateAdapter oldNavAdapter;
/*      */   private PathCreator pathSource;
/*      */   protected Goal currentGoal;
/*      */   protected Goal prevGoal;
/*      */   protected EntityAIBase c;
/*      */   protected EntityAIBase d;
/*      */   private IMMoveHelper i;
/*      */   private MoveState moveState;
/*      */   private float rotationRoll;
/*      */   private float rotationYawHeadIM;
/*      */   private float rotationPitchHead;
/*      */   private float prevRotationRoll;
/*      */   private float prevRotationYawHeadIM;
/*      */   private float prevRotationPitchHead;
/*      */   private int debugMode;
/*      */   private float airResistance;
/*      */   private float groundFriction;
/*      */   private float gravityAcel;
/*      */   private float moveSpeed;
/*      */   private float moveSpeedBase;
/*      */   private float turnRate;
/*      */   private float pitchRate;
/*      */   private int rallyCooldown;
/*      */   private IPosition currentTargetPos;
/*      */   private IPosition lastBreathExtendPos;
/*      */   private String simplyID;
/*      */   private String name;
/*      */   private String renderLabel;
/*      */   private boolean shouldRenderLabel;
/*      */   private int gender;
/*      */   private boolean isHostile;
/*      */   private boolean creatureRetaliates;
/*      */   protected INexusAccess targetNexus;
/*      */   protected int attackStrength;
/*      */   protected float attackRange;
/*      */   private float maxHealth;
/*      */   protected int selfDamage;
/*      */   protected int maxSelfDamage;
/*      */   protected int maxDestructiveness;
/*      */   protected float blockRemoveSpeed;
/*      */   protected boolean floatsInWater;
/*      */   private CoordsInt collideSize;
/*      */   private boolean canClimb;
/*      */   private boolean canDig;
/*      */   private boolean nexusBound;
/*      */   private boolean alwaysIndependent;
/*      */   private boolean burnsInDay;
/*      */   private int jumpHeight;
/*      */   private int aggroRange;
/*      */   private int senseRange;
/*      */   private int stunTimer;
/*      */   protected int throttled;
/*      */   protected int throttled2;
/*      */   protected int pathThrottle;
/*      */   protected int destructionTimer;
/*      */   protected int flammability;
/*      */   protected int destructiveness;
/*      */   protected nm j;
/*      */   protected static final int META_CLIMB_STATE = 20;
/*      */   protected static final byte META_CLIMBABLE_BLOCK = 21;
/*      */   protected static final byte META_JUMPING = 22;
/*      */   protected static final byte META_MOVESTATE = 23;
/*      */   protected static final byte META_ROTATION = 24;
/*      */   protected static final byte META_RENDERLABEL = 25;
/*      */   protected static final float DEFAULT_SOFT_STRENGTH = 2.5F;
/*      */   protected static final float DEFAULT_HARD_STRENGTH = 5.5F;
/*      */   protected static final float DEFAULT_SOFT_COST = 2.0F;
/*      */   protected static final float DEFAULT_HARD_COST = 3.2F;
/*      */   protected static final float AIR_BASE_COST = 1.0F;
/*  130 */   protected static final Map<Integer, Float> blockCosts = new HashMap();
/*  131 */   private static final Map<Integer, Float> blockStrength = new HashMap();
/*  132 */   private static final Map<Integer, BlockSpecial> blockSpecials = new HashMap();
/*  133 */   private static final Map<Integer, Integer> blockType = new HashMap();
/*      */ 
/*      */   public EntityIMLiving(ColorizerGrass world)
/*      */   {
/*  137 */     this(world, null);
/*      */   }
/*      */ 
/*      */   public EntityIMLiving(ColorizerGrass world, INexusAccess nexus)
/*      */   {
/*  142 */     super(world);
/*  143 */     this.targetNexus = nexus;
/*  144 */     this.currentGoal = Goal.NONE;
/*  145 */     this.prevGoal = Goal.NONE;
/*  146 */     this.moveState = MoveState.STANDING;
/*  147 */     this.c = new EntityAIBase(world.C);
/*  148 */     this.d = new EntityAIBase(world.C);
/*  149 */     this.pathSource = new PathCreator(700, 50);
/*  150 */     this.bo = new NavigatorIM(this, this.pathSource);
/*  151 */     this.oldNavAdapter = new PathNavigateAdapter(this.bo);
/*  152 */     this.i = new IMMoveHelper(this);
/*  153 */     this.collideSize = new CoordsInt(LongHashMapEntry.c(this.width + 1.0F), LongHashMapEntry.c(this.height + 1.0F), LongHashMapEntry.c(this.width + 1.0F));
/*  154 */     this.moveSpeedBase = 0.26F;
/*  155 */     this.moveSpeed = this.moveSpeedBase;
/*  156 */     this.turnRate = 30.0F;
/*  157 */     this.pitchRate = 2.0F;
/*  158 */     CoordsInt initCoords = new CoordsInt(0, 0, 0);
/*  159 */     this.currentTargetPos = initCoords;
/*  160 */     this.lastBreathExtendPos = initCoords;
/*  161 */     this.simplyID = "needID";
/*  162 */     this.renderLabel = "";
/*  163 */     this.shouldRenderLabel = false;
/*  164 */     this.gender = 0;
/*  165 */     this.isHostile = true;
/*  166 */     this.creatureRetaliates = true;
/*  167 */     this.debugMode = 0;
/*      */ 
/*  171 */     this.airResistance = 0.9995F;
/*  172 */     this.groundFriction = 0.546F;
/*  173 */     this.gravityAcel = 0.08F;
/*      */ 
/*  177 */     this.attackStrength = 2;
/*  178 */     this.attackRange = 0.0F;
/*  179 */     setMaxHealth(20.0F);
/*  180 */     setHealth(20.0F);
/*  181 */     this.selfDamage = 2;
/*  182 */     this.maxSelfDamage = 6;
/*  183 */     this.flammability = 2;
/*  184 */     this.isImmuneToFire = false;
/*  185 */     this.canClimb = false;
/*  186 */     this.canDig = true;
/*  187 */     this.floatsInWater = true;
/*  188 */     this.alwaysIndependent = false;
/*  189 */     this.jumpHeight = 1;
/*  190 */     this.experienceValue = 5;
/*  191 */     this.maxDestructiveness = 0;
/*  192 */     this.blockRemoveSpeed = 1.0F;
/*  193 */     setBurnsInDay(false);
/*  194 */     setAggroRange(10);
/*  195 */     setSenseRange(3);
/*  196 */     if (nexus != null)
/*  197 */       this.nexusBound = true;
/*      */     else {
/*  199 */       this.nexusBound = false;
/*      */     }
/*      */ 
/*  202 */     this.hasAttacked = false;
/*  203 */     this.destructionTimer = 0;
/*  204 */     this.destructiveness = 0;
/*  205 */     this.throttled = 0;
/*  206 */     this.throttled2 = 0;
/*  207 */     this.pathThrottle = 0;
/*      */ 
/*  209 */     this.ah.a(20, Byte.valueOf((byte)0));
/*  210 */     this.ah.a(21, Byte.valueOf((byte)0));
/*  211 */     this.ah.a(22, Byte.valueOf((byte)0));
/*  212 */     this.ah.a(23, Integer.valueOf(this.moveState.ordinal()));
/*  213 */     this.ah.a(24, Integer.valueOf(MathUtil.packAnglesDeg(this.rotationRoll, this.rotationYawHeadIM, this.rotationPitchHead, 0.0F)));
/*  214 */     this.ah.a(25, "");
/*      */   }
/*      */ 
/*      */   public void onUpdate()
/*      */   {
/*  220 */     super.l_();
/*  221 */     this.prevRotationRoll = this.rotationRoll;
/*  222 */     this.prevRotationYawHeadIM = this.rotationYawHeadIM;
/*  223 */     this.prevRotationPitchHead = this.rotationPitchHead;
/*  224 */     if (this.q.I)
/*      */     {
/*  226 */       this.moveState = MoveState.values()[this.ah.c(23)];
/*  227 */       int packedAngles = this.ah.c(24);
/*  228 */       this.rotationRoll = MathUtil.unpackAnglesDeg_1(packedAngles);
/*  229 */       this.rotationYawHeadIM = MathUtil.unpackAnglesDeg_2(packedAngles);
/*  230 */       this.rotationPitchHead = MathUtil.unpackAnglesDeg_3(packedAngles);
/*  231 */       this.renderLabel = this.ah.e(25);
/*      */     }
/*      */     else
/*      */     {
/*  237 */       int packedAngles = MathUtil.packAnglesDeg(this.rotationRoll, this.rotationYawHeadIM, this.rotationPitchHead, 0.0F);
/*  238 */       if (packedAngles != this.ah.c(24)) {
/*  239 */         this.ah.b(24, Integer.valueOf(packedAngles));
/*      */       }
/*  241 */       if (!this.renderLabel.equals(this.ah.e(25)))
/*  242 */         this.ah.b(25, this.renderLabel);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setDead()
/*      */   {
/*  249 */     super.x();
/*      */ 
/*  251 */     if (this.q.I)
/*      */     {
/*  258 */       if (this.ah.a(22) == 1)
/*  259 */         this.isJumping = true;
/*      */       else {
/*  261 */         this.isJumping = false;
/*      */       }
/*      */     }
/*      */     else {
/*  265 */       setAdjacentClimbBlock(checkForAdjacentClimbBlock());
/*      */     }
/*      */ 
/*  269 */     if (ak() == 190)
/*      */     {
/*  271 */       this.lastBreathExtendPos = new CoordsInt(getXCoord(), getYCoord(), getZCoord());
/*      */     }
/*  273 */     else if (ak() == 0)
/*      */     {
/*  275 */       IPosition pos = new CoordsInt(getXCoord(), getYCoord(), getZCoord());
/*  276 */       if (Distance.distanceBetween(this.lastBreathExtendPos, pos) > 4.0D)
/*      */       {
/*  278 */         this.lastBreathExtendPos = pos;
/*  279 */         setAir(180);
/*      */       }
/*      */     }
/*      */ 
/*  283 */     if (this.simplyID == "needID");
/*      */   }
/*      */ 
/*      */   public void onLivingUpdate()
/*      */   {
/*  292 */     if (!this.nexusBound)
/*      */     {
/*  294 */       float brightness = getBrightness(1.0F);
/*  295 */       if ((brightness > 0.5F) || (this.posY < 55.0D)) {
/*  296 */         this.entityAge += 2;
/*      */       }
/*  298 */       if ((getBurnsInDay()) && (this.q.v()) && (!this.q.I))
/*      */       {
/*  300 */         if ((brightness > 0.5F) && (this.q.l(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.posY), LongHashMapEntry.c(this.posZ))) && (this.rand.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F))
/*      */         {
/*  302 */           sunlightDamageTick();
/*      */         }
/*      */       }
/*      */     }
/*  306 */     super.c();
/*      */   }
/*      */ 
/*      */   public boolean a(CombatTracker damagesource, float damage)
/*      */   {
/*  312 */     if (super.a(damagesource, damage))
/*      */     {
/*  314 */       nm entity = damagesource.i();
/*  315 */       if ((this.n == entity) || (this.o == entity)) {
/*  316 */         return true;
/*      */       }
/*  318 */       if (entity != this) {
/*  319 */         this.j = entity;
/*      */       }
/*  321 */       return true;
/*      */     }
/*      */ 
/*  325 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean stunEntity(int ticks)
/*      */   {
/*  331 */     if (this.stunTimer < ticks) {
/*  332 */       this.stunTimer = ticks;
/*      */     }
/*  334 */     this.motionX = 0.0D;
/*  335 */     this.motionZ = 0.0D;
/*  336 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean m(nm entity)
/*      */   {
/*  342 */     return entity.a(CombatTracker.a(this), this.attackStrength);
/*      */   }
/*      */ 
/*      */   public boolean attackEntityAsMob(nm entity, int damageOverride)
/*      */   {
/*  347 */     return entity.a(CombatTracker.a(this), damageOverride);
/*      */   }
/*      */ 
/*      */   public void moveEntityWithHeading(float x, float z)
/*      */   {
/*  353 */     if (isWet())
/*      */     {
/*  355 */       double y = this.posY;
/*  356 */       moveFlying(x, z, be() ? 0.04F : 0.02F);
/*  357 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*  358 */       this.motionX *= 0.8D;
/*  359 */       this.motionY *= 0.8D;
/*  360 */       this.motionZ *= 0.8D;
/*  361 */       this.motionY -= 0.02D;
/*  362 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/*  363 */         this.motionY = 0.3D;
/*      */     }
/*  365 */     else if (handleWaterMovement())
/*      */     {
/*  367 */       double y = this.posY;
/*  368 */       moveFlying(x, z, be() ? 0.04F : 0.02F);
/*  369 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*  370 */       this.motionX *= 0.5D;
/*  371 */       this.motionY *= 0.5D;
/*  372 */       this.motionZ *= 0.5D;
/*  373 */       this.motionY -= 0.02D;
/*  374 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/*  375 */         this.motionY = 0.3D;
/*      */     }
/*      */     else
/*      */     {
/*  379 */       float groundFriction = 0.91F;
/*      */       float landMoveSpeed;
/*  381 */       if (this.onGround)
/*      */       {
/*  383 */         groundFriction = getGroundFriction();
/*  384 */         int i = this.q.a(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.E.b) - 1, LongHashMapEntry.c(this.posZ));
/*  385 */         if (i > 0) {
/*  386 */           groundFriction = BlockEndPortal.s[i].slipperiness * 0.91F;
/*      */         }
/*  388 */         float landMoveSpeed = bf();
/*      */ 
/*  390 */         landMoveSpeed *= 0.162771F / (groundFriction * groundFriction * groundFriction);
/*      */       }
/*      */       else
/*      */       {
/*  394 */         landMoveSpeed = this.jumpMovementFactor;
/*      */       }
/*      */ 
/*  397 */       moveFlying(x, z, landMoveSpeed);
/*      */ 
/*  400 */       if (isOnLadder())
/*      */       {
/*  402 */         float maxLadderXZSpeed = 0.15F;
/*  403 */         if (this.motionX < -maxLadderXZSpeed)
/*  404 */           this.motionX = (-maxLadderXZSpeed);
/*  405 */         if (this.motionX > maxLadderXZSpeed)
/*  406 */           this.motionX = maxLadderXZSpeed;
/*  407 */         if (this.motionZ < -maxLadderXZSpeed)
/*  408 */           this.motionZ = (-maxLadderXZSpeed);
/*  409 */         if (this.motionZ > maxLadderXZSpeed) {
/*  410 */           this.motionZ = maxLadderXZSpeed;
/*      */         }
/*  412 */         this.fallDistance = 0.0F;
/*  413 */         if (this.motionY < -0.15D) {
/*  414 */           this.motionY = -0.15D;
/*      */         }
/*  416 */         if ((isHoldingOntoLadder()) || ((isRiding()) && (this.motionY < 0.0D)))
/*  417 */           this.motionY = 0.0D;
/*  418 */         else if ((this.q.I) && (this.isJumping)) {
/*  419 */           this.motionY += 0.04D;
/*      */         }
/*      */       }
/*  422 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*      */ 
/*  424 */       if ((this.isCollidedHorizontally) && (isOnLadder())) {
/*  425 */         this.motionY = 0.2D;
/*      */       }
/*  427 */       this.motionY -= getGravity();
/*  428 */       this.motionY *= this.airResistance;
/*  429 */       this.motionX *= groundFriction * this.airResistance;
/*  430 */       this.motionZ *= groundFriction * this.airResistance;
/*      */     }
/*      */ 
/*  433 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/*  434 */     double dX = this.posX - this.prevPosX;
/*  435 */     double dZ = this.posZ - this.prevPosZ;
/*  436 */     float limbEnergy = LongHashMapEntry.a(dX * dX + dZ * dZ) * 4.0F;
/*      */ 
/*  438 */     if (limbEnergy > 1.0F)
/*      */     {
/*  440 */       limbEnergy = 1.0F;
/*      */     }
/*      */ 
/*  443 */     this.limbSwingAmount += (limbEnergy - this.limbSwingAmount) * 0.4F;
/*  444 */     this.limbSwing += this.limbSwingAmount;
/*      */   }
/*      */ 
/*      */   public void moveFlying(float strafeAmount, float forwardAmount, float movementFactor)
/*      */   {
/*  454 */     float unit = LongHashMapEntry.c(strafeAmount * strafeAmount + forwardAmount * forwardAmount);
/*      */ 
/*  456 */     if (unit < 0.01F)
/*      */     {
/*  458 */       return;
/*      */     }
/*      */ 
/*  461 */     if (unit < 20.0F)
/*      */     {
/*  463 */       unit = 1.0F;
/*      */     }
/*      */ 
/*  466 */     unit = movementFactor / unit;
/*  467 */     strafeAmount *= unit;
/*  468 */     forwardAmount *= unit;
/*      */ 
/*  471 */     float com1 = LongHashMapEntry.a(this.rotationYaw * 3.141593F / 180.0F);
/*  472 */     float com2 = LongHashMapEntry.b(this.rotationYaw * 3.141593F / 180.0F);
/*  473 */     this.motionX += strafeAmount * com2 - forwardAmount * com1;
/*  474 */     this.motionZ += forwardAmount * com2 + strafeAmount * com1;
/*      */   }
/*      */ 
/*      */   public boolean isInWater()
/*      */   {
/*  480 */     if (this.floatsInWater)
/*      */     {
/*  482 */       return this.q.a(this.E.b(0.0D, -0.4D, 0.0D).e(0.001D, 0.001D, 0.001D), MaterialLogic.h, this);
/*      */     }
/*      */ 
/*  487 */     double vX = this.motionX;
/*  488 */     double vY = this.motionY;
/*  489 */     double vZ = this.motionZ;
/*  490 */     boolean isInWater = this.q.a(this.E.b(0.0D, -0.4D, 0.0D).e(0.001D, 0.001D, 0.001D), MaterialLogic.h, this);
/*  491 */     this.motionX = vX;
/*  492 */     this.motionY = vY;
/*  493 */     this.motionZ = vZ;
/*  494 */     return isInWater;
/*      */   }
/*      */ 
/*      */   public void rally(nm leader)
/*      */   {
/*  500 */     this.rallyCooldown = 300;
/*      */   }
/*      */ 
/*      */   public void onFollowingEntity(nm entity)
/*      */   {
/*      */   }
/*      */ 
/*      */   public void onPathSet()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void onBlockRemoved(int x, int y, int z, int id)
/*      */   {
/*  517 */     if (getHealth() > this.maxHealth - this.maxSelfDamage)
/*      */     {
/*  519 */       a(CombatTracker.j, this.selfDamage);
/*      */     }
/*      */ 
/*  523 */     if ((this.throttled == 0) && ((id == 3) || (id == 2) || (id == 12) || (id == 13)))
/*      */     {
/*  525 */       this.q.a(this, "step.gravel", 1.4F, 1.0F / (this.rand.nextFloat() * 0.6F + 1.0F));
/*      */ 
/*  527 */       this.throttled = 5;
/*      */     }
/*      */     else
/*      */     {
/*  531 */       this.q.a(this, "step.stone", 1.4F, 1.0F / (this.rand.nextFloat() * 0.6F + 1.0F));
/*      */ 
/*  533 */       this.throttled = 5;
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean avoidsBlock(int id)
/*      */   {
/*  542 */     if ((id == BlockEndPortal.aw.blockID) || (id == BlockEndPortal.E.blockID) || (id == BlockEndPortal.I.blockID) || (id == BlockEndPortal.H.blockID))
/*      */     {
/*  544 */       return true;
/*      */     }
/*  546 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean ignoresBlock(int id)
/*      */   {
/*  551 */     if ((id == BlockEndPortal.ac.blockID) || (id == BlockEndPortal.ad.cF) || (id == BlockEndPortal.aj.blockID) || (id == BlockEndPortal.ai.blockID) || (id == BlockEndPortal.ak.blockID) || (id == BlockEndPortal.al.blockID) || (id == BlockEndPortal.aR.blockID) || (id == BlockEndPortal.cp.blockID) || (id == BlockEndPortal.aP.blockID))
/*      */     {
/*  555 */       return true;
/*      */     }
/*  557 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isBlockDestructible(EnumGameType terrainMap, int x, int y, int z, int id)
/*      */   {
/*  565 */     return isBlockTypeDestructible(id);
/*      */   }
/*      */ 
/*      */   public boolean isBlockTypeDestructible(int id)
/*      */   {
/*  570 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean canEntityBeDetected(nm entity)
/*      */   {
/*  575 */     float distance = d(entity);
/*  576 */     return (distance <= getSenseRange()) || ((o(entity)) && (distance <= getAggroRange()));
/*      */   }
/*      */ 
/*      */   public double findDistanceToNexus()
/*      */   {
/*  581 */     if (this.targetNexus == null) {
/*  582 */       return 1.7976931348623157E+308D;
/*      */     }
/*  584 */     double x = this.targetNexus.getXCoord() + 0.5D - this.posX;
/*  585 */     double y = this.targetNexus.getYCoord() - this.posY + this.height * 0.5D;
/*  586 */     double z = this.targetNexus.getZCoord() + 0.5D - this.posZ;
/*  587 */     return Math.sqrt(x * x + y * y + z * z);
/*      */   }
/*      */ 
/*      */   public nm bL()
/*      */   {
/*  593 */     CallableItemName entityPlayer = this.q.a(this, getSenseRange());
/*  594 */     if (entityPlayer != null) {
/*  595 */       return entityPlayer;
/*      */     }
/*  597 */     entityPlayer = this.q.a(this, getAggroRange());
/*  598 */     if ((entityPlayer != null) && (o(entityPlayer))) {
/*  599 */       return entityPlayer;
/*      */     }
/*  601 */     return null;
/*      */   }
/*      */ 
/*      */   public void b(NBTTagByte nbttagcompound)
/*      */   {
/*  607 */     nbttagcompound.a("alwaysIndependent", this.alwaysIndependent);
/*  608 */     super.b(nbttagcompound);
/*      */   }
/*      */ 
/*      */   public void a(NBTTagByte nbttagcompound)
/*      */   {
/*  614 */     this.alwaysIndependent = nbttagcompound.n("alwaysIndependent");
/*  615 */     if (this.alwaysIndependent)
/*      */     {
/*  617 */       setAggroRange(mod_Invasion.getNightMobSightRange());
/*  618 */       setSenseRange(mod_Invasion.getNightMobSenseRange());
/*  619 */       setBurnsInDay(mod_Invasion.getNightMobsBurnInDay());
/*      */     }
/*  621 */     super.a(nbttagcompound);
/*      */   }
/*      */ 
/*      */   public float getPrevRotationRoll()
/*      */   {
/*  626 */     return this.prevRotationRoll;
/*      */   }
/*      */ 
/*      */   public float getRotationRoll()
/*      */   {
/*  631 */     return this.rotationRoll;
/*      */   }
/*      */ 
/*      */   public float getPrevRotationYawHeadIM()
/*      */   {
/*  636 */     return this.prevRotationYawHeadIM;
/*      */   }
/*      */ 
/*      */   public float getRotationYawHeadIM()
/*      */   {
/*  641 */     return this.rotationYawHeadIM;
/*      */   }
/*      */ 
/*      */   public float getPrevRotationPitchHead()
/*      */   {
/*  646 */     return this.prevRotationPitchHead;
/*      */   }
/*      */ 
/*      */   public float getRotationPitchHead()
/*      */   {
/*  651 */     return this.rotationPitchHead;
/*      */   }
/*      */ 
/*      */   public int getXCoord()
/*      */   {
/*  657 */     return LongHashMapEntry.c(this.posX);
/*      */   }
/*      */ 
/*      */   public int getYCoord()
/*      */   {
/*  663 */     return LongHashMapEntry.c(this.posY);
/*      */   }
/*      */ 
/*      */   public int getZCoord()
/*      */   {
/*  669 */     return LongHashMapEntry.c(this.posZ);
/*      */   }
/*      */ 
/*      */   public float getAttackRange()
/*      */   {
/*  674 */     return this.attackRange;
/*      */   }
/*      */ 
/*      */   public float getMaxHealth()
/*      */   {
/*  680 */     return aS();
/*      */   }
/*      */ 
/*      */   public float getHealth()
/*      */   {
/*  685 */     return aM();
/*      */   }
/*      */ 
/*      */   public void setMaxHealth(float health)
/*      */   {
/*  690 */     this.maxHealth = health;
/*      */   }
/*      */ 
/*      */   public void setMaxHealthAndHealth(float health)
/*      */   {
/*  695 */     this.maxHealth = health;
/*  696 */     setHealth(health);
/*      */   }
/*      */ 
/*      */   public boolean getCanSpawnHere()
/*      */   {
/*  702 */     boolean lightFlag = false;
/*  703 */     if ((this.nexusBound) || (getLightLevelBelow8())) {
/*  704 */       lightFlag = true;
/*      */     }
/*  706 */     return (super.bs()) && (lightFlag) && (this.q.u(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.E.b + 0.5D) - 1, LongHashMapEntry.c(this.posZ)));
/*      */   }
/*      */ 
/*      */   public MoveState getMoveState()
/*      */   {
/*  711 */     return this.moveState;
/*      */   }
/*      */ 
/*      */   public float getMoveSpeedStat()
/*      */   {
/*  716 */     return this.moveSpeed;
/*      */   }
/*      */ 
/*      */   public float getBaseMoveSpeedStat()
/*      */   {
/*  721 */     return this.moveSpeedBase;
/*      */   }
/*      */ 
/*      */   public int getJumpHeight()
/*      */   {
/*  726 */     return this.jumpHeight;
/*      */   }
/*      */ 
/*      */   public float getBlockStrength(int x, int y, int z)
/*      */   {
/*  732 */     return getBlockStrength(x, y, z, this.q.a(x, y, z));
/*      */   }
/*      */ 
/*      */   public float getBlockStrength(int x, int y, int z, int id)
/*      */   {
/*  737 */     return getBlockStrength(x, y, z, id, this.q);
/*      */   }
/*      */ 
/*      */   public boolean getCanClimb()
/*      */   {
/*  742 */     return this.canClimb;
/*      */   }
/*      */ 
/*      */   public boolean getCanDigDown()
/*      */   {
/*  747 */     return this.canDig;
/*      */   }
/*      */ 
/*      */   public int getAggroRange()
/*      */   {
/*  752 */     return this.aggroRange;
/*      */   }
/*      */ 
/*      */   public int getSenseRange()
/*      */   {
/*  757 */     return this.senseRange;
/*      */   }
/*      */ 
/*      */   public float getBlockPathWeight(int i, int j, int k)
/*      */   {
/*  763 */     if (this.nexusBound) {
/*  764 */       return 0.0F;
/*      */     }
/*  766 */     return 0.5F - this.q.q(i, j, k);
/*      */   }
/*      */ 
/*      */   public boolean getBurnsInDay()
/*      */   {
/*  771 */     return this.burnsInDay;
/*      */   }
/*      */ 
/*      */   public int getDestructiveness()
/*      */   {
/*  776 */     return this.destructiveness;
/*      */   }
/*      */ 
/*      */   public float getTurnRate()
/*      */   {
/*  781 */     return this.turnRate;
/*      */   }
/*      */ 
/*      */   public float getPitchRate()
/*      */   {
/*  786 */     return this.pitchRate;
/*      */   }
/*      */ 
/*      */   public float getGravity()
/*      */   {
/*  791 */     return this.gravityAcel;
/*      */   }
/*      */ 
/*      */   public float getAirResistance()
/*      */   {
/*  796 */     return this.airResistance;
/*      */   }
/*      */ 
/*      */   public float getGroundFriction()
/*      */   {
/*  801 */     return this.groundFriction;
/*      */   }
/*      */ 
/*      */   public CoordsInt getCollideSize()
/*      */   {
/*  806 */     return this.collideSize;
/*      */   }
/*      */ 
/*      */   public static BlockSpecial getBlockSpecial(int id)
/*      */   {
/*  811 */     if (blockSpecials.containsKey(Integer.valueOf(id))) {
/*  812 */       return (BlockSpecial)blockSpecials.get(Integer.valueOf(id));
/*      */     }
/*  814 */     return BlockSpecial.NONE;
/*      */   }
/*      */ 
/*      */   public Goal getAIGoal()
/*      */   {
/*  819 */     return this.currentGoal;
/*      */   }
/*      */ 
/*      */   public Goal getPrevAIGoal()
/*      */   {
/*  824 */     return this.prevGoal;
/*      */   }
/*      */ 
/*      */   public PathNavigateAdapter getNavigator()
/*      */   {
/*  840 */     return this.oldNavAdapter;
/*      */   }
/*      */ 
/*      */   public INavigation getNavigatorNew()
/*      */   {
/*  848 */     return this.bo;
/*      */   }
/*      */ 
/*      */   public IPathSource getPathSource()
/*      */   {
/*  853 */     return this.pathSource;
/*      */   }
/*      */ 
/*      */   public float getBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*      */   {
/*  862 */     return calcBlockPathCost(prevNode, node, terrainMap);
/*      */   }
/*      */ 
/*      */   public void getPathOptionsFromNode(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*      */   {
/*  868 */     calcPathOptions(terrainMap, currentNode, pathFinder);
/*      */   }
/*      */ 
/*      */   public IPosition getCurrentTargetPos()
/*      */   {
/*  873 */     return this.currentTargetPos;
/*      */   }
/*      */ 
/*      */   public IPosition[] getBlockRemovalOrder(int x, int y, int z)
/*      */   {
/*  878 */     if (LongHashMapEntry.c(this.posY) >= y)
/*      */     {
/*  880 */       IPosition[] blocks = new IPosition[2];
/*  881 */       blocks[1] = new CoordsInt(x, y + 1, z);
/*  882 */       blocks[0] = new CoordsInt(x, y, z);
/*  883 */       return blocks;
/*      */     }
/*      */ 
/*  887 */     IPosition[] blocks = new IPosition[3];
/*  888 */     blocks[2] = new CoordsInt(x, y, z);
/*  889 */     blocks[1] = new CoordsInt(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.posY) + this.collideSize.getYCoord(), LongHashMapEntry.c(this.posZ));
/*  890 */     blocks[0] = new CoordsInt(x, y + 1, z);
/*  891 */     return blocks;
/*      */   }
/*      */ 
/*      */   public IMMoveHelper getMoveHelper()
/*      */   {
/*  898 */     return this.i;
/*      */   }
/*      */ 
/*      */   public INexusAccess getNexus()
/*      */   {
/*  904 */     return this.targetNexus;
/*      */   }
/*      */ 
/*      */   public String getRenderLabel()
/*      */   {
/*  909 */     return this.renderLabel;
/*      */   }
/*      */ 
/*      */   public int getDebugMode()
/*      */   {
/*  914 */     return this.debugMode;
/*      */   }
/*      */ 
/*      */   public boolean isHostile()
/*      */   {
/*  923 */     return this.isHostile;
/*      */   }
/*      */ 
/*      */   public boolean isNeutral()
/*      */   {
/*  930 */     return this.creatureRetaliates;
/*      */   }
/*      */ 
/*      */   public boolean isThreatTo(nm entity)
/*      */   {
/*  937 */     if ((this.isHostile) && ((entity instanceof CallableItemName))) {
/*  938 */       return true;
/*      */     }
/*  940 */     return false;
/*      */   }
/*      */ 
/*      */   public nm getAttackingTarget()
/*      */   {
/*  947 */     return m();
/*      */   }
/*      */ 
/*      */   public boolean isStupidToAttack()
/*      */   {
/*  954 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean doNotVaporize()
/*      */   {
/*  961 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isPredator()
/*      */   {
/*  968 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isPeaceful()
/*      */   {
/*  975 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isPrey()
/*      */   {
/*  982 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isUnkillable()
/*      */   {
/*  989 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isFriendOf(nm par1entity)
/*      */   {
/*  996 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isNPC()
/*      */   {
/* 1003 */     return false;
/*      */   }
/*      */ 
/*      */   public int isPet()
/*      */   {
/* 1010 */     return 0;
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/* 1017 */     return this.name;
/*      */   }
/*      */ 
/*      */   public int getGender()
/*      */   {
/* 1024 */     return this.gender;
/*      */   }
/*      */ 
/*      */   public nm getPetOwner()
/*      */   {
/* 1031 */     return null;
/*      */   }
/*      */ 
/*      */   public float getSize()
/*      */   {
/* 1038 */     return this.height * this.width;
/*      */   }
/*      */ 
/*      */   public String customStringAndResponse(String s)
/*      */   {
/* 1045 */     return null;
/*      */   }
/*      */ 
/*      */   public int getTier()
/*      */   {
/* 1052 */     return 0;
/*      */   }
/*      */ 
/*      */   public String getSimplyID()
/*      */   {
/* 1059 */     return this.simplyID;
/*      */   }
/*      */ 
/*      */   public boolean isNexusBound()
/*      */   {
/* 1066 */     return this.nexusBound;
/*      */   }
/*      */ 
/*      */   public boolean isHoldingOntoLadder()
/*      */   {
/* 1071 */     return this.ah.a(20) == 1;
/*      */   }
/*      */ 
/*      */   public boolean isOnLadder()
/*      */   {
/* 1078 */     return isAdjacentClimbBlock();
/*      */   }
/*      */ 
/*      */   public boolean isAdjacentClimbBlock()
/*      */   {
/* 1083 */     return this.ah.a(21) == 1;
/*      */   }
/*      */ 
/*      */   public boolean checkForAdjacentClimbBlock()
/*      */   {
/* 1088 */     int var1 = LongHashMapEntry.c(this.posX);
/* 1089 */     int var2 = LongHashMapEntry.c(this.E.b);
/* 1090 */     int var3 = LongHashMapEntry.c(this.posZ);
/* 1091 */     int var4 = this.q.a(var1, var2, var3);
/* 1092 */     return (BlockEndPortal.s[var4] != null) && (BlockEndPortal.s[var4].isLadder(this.q, var1, var2, var3, this));
/*      */   }
/*      */ 
/*      */   public boolean readyToRally()
/*      */   {
/* 1097 */     return this.rallyCooldown == 0;
/*      */   }
/*      */ 
/*      */   public boolean canSwimHorizontal()
/*      */   {
/* 1102 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean canSwimVertical()
/*      */   {
/* 1107 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean shouldRenderLabel()
/*      */   {
/* 1112 */     return this.shouldRenderLabel;
/*      */   }
/*      */ 
/*      */   public void acquiredByNexus(INexusAccess nexus)
/*      */   {
/* 1118 */     if ((this.targetNexus == null) && (!this.alwaysIndependent))
/*      */     {
/* 1120 */       this.targetNexus = nexus;
/* 1121 */       this.nexusBound = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void preparePlayerToSpawn()
/*      */   {
/* 1128 */     super.w();
/* 1129 */     if ((getHealth() <= 0.0F) && (this.targetNexus != null))
/* 1130 */       this.targetNexus.registerMobDied();
/*      */   }
/*      */ 
/*      */   public void setEntityIndependent()
/*      */   {
/* 1135 */     this.targetNexus = null;
/* 1136 */     this.nexusBound = false;
/* 1137 */     this.alwaysIndependent = true;
/*      */   }
/*      */ 
/*      */   public void setSize(float width, float height)
/*      */   {
/* 1143 */     super.a(width, height);
/* 1144 */     this.collideSize = new CoordsInt(LongHashMapEntry.c(width + 1.0F), LongHashMapEntry.c(height + 1.0F), LongHashMapEntry.c(width + 1.0F));
/*      */   }
/*      */ 
/*      */   public void setBurnsInDay(boolean flag)
/*      */   {
/* 1149 */     this.burnsInDay = flag;
/*      */   }
/*      */ 
/*      */   public void setAggroRange(int range)
/*      */   {
/* 1154 */     this.aggroRange = range;
/*      */   }
/*      */ 
/*      */   public void setSenseRange(int range)
/*      */   {
/* 1159 */     this.senseRange = range;
/*      */   }
/*      */ 
/*      */   public void setIsHoldingIntoLadder(boolean flag)
/*      */   {
/* 1165 */     if (!this.q.I)
/* 1166 */       this.ah.b(20, Byte.valueOf((byte)(flag ? 1 : 0)));
/*      */   }
/*      */ 
/*      */   public void setJumping(boolean flag)
/*      */   {
/* 1172 */     super.f(flag);
/* 1173 */     if (!this.q.I)
/* 1174 */       this.ah.b(22, Byte.valueOf((byte)(flag ? 1 : 0)));
/*      */   }
/*      */ 
/*      */   public void setAdjacentClimbBlock(boolean flag)
/*      */   {
/* 1179 */     if (!this.q.I)
/* 1180 */       this.ah.b(21, Byte.valueOf((byte)(flag ? 1 : 0)));
/*      */   }
/*      */ 
/*      */   public void setRenderLabel(String label)
/*      */   {
/* 1185 */     this.renderLabel = label;
/*      */   }
/*      */ 
/*      */   public void setShouldRenderLabel(boolean flag)
/*      */   {
/* 1190 */     this.shouldRenderLabel = flag;
/*      */   }
/*      */ 
/*      */   public void setDebugMode(int mode)
/*      */   {
/* 1195 */     this.debugMode = mode;
/* 1196 */     onDebugChange();
/*      */   }
/*      */ 
/*      */   protected void bh()
/*      */   {
/* 1202 */     this.q.C.a("Entity IM");
/* 1203 */     this.entityAge += 1;
/* 1204 */     bo();
/* 1205 */     l().a();
/* 1206 */     this.d.a();
/* 1207 */     collideWithNearbyEntities();
/* 1208 */     this.c.a();
/* 1209 */     getNavigatorNew().onUpdateNavigation();
/* 1210 */     h().setJumping();
/* 1211 */     getMoveHelper().c();
/* 1212 */     j().b();
/* 1213 */     this.q.C.b();
/*      */   }
/*      */ 
/*      */   protected void collideWithNearbyEntities()
/*      */   {
/* 1219 */     if (this.rallyCooldown > 0) {
/* 1220 */       this.rallyCooldown -= 1;
/*      */     }
/* 1222 */     if (m() != null)
/* 1223 */       this.currentGoal = Goal.TARGET_ENTITY;
/* 1224 */     else if (this.targetNexus != null)
/* 1225 */       this.currentGoal = Goal.BREAK_NEXUS;
/*      */     else
/* 1227 */       this.currentGoal = Goal.CHILL;
/*      */   }
/*      */ 
/*      */   protected boolean be()
/*      */   {
/* 1233 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean canDespawn()
/*      */   {
/* 1239 */     return !this.nexusBound;
/*      */   }
/*      */ 
/*      */   protected void setRotationRoll(float roll)
/*      */   {
/* 1244 */     this.rotationRoll = roll;
/*      */   }
/*      */ 
/*      */   public void setRotationYawHeadIM(float yaw)
/*      */   {
/* 1249 */     this.rotationYawHeadIM = yaw;
/*      */   }
/*      */ 
/*      */   protected void setRotationPitchHead(float pitch)
/*      */   {
/* 1254 */     this.rotationPitchHead = pitch;
/*      */   }
/*      */ 
/*      */   protected void setAttackRange(float range)
/*      */   {
/* 1259 */     this.attackRange = range;
/*      */   }
/*      */ 
/*      */   protected void setCurrentTargetPos(IPosition pos)
/*      */   {
/* 1264 */     this.currentTargetPos = pos;
/*      */   }
/*      */ 
/*      */   protected void a(nm entity, float f)
/*      */   {
/* 1270 */     if ((this.attackTime <= 0) && (f < 2.0F) && (entity.E.e > this.E.b) && (entity.E.b < this.E.e))
/*      */     {
/* 1272 */       this.attackTime = 38;
/* 1273 */       m(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void sunlightDamageTick()
/*      */   {
/* 1279 */     setFire(8);
/*      */   }
/*      */ 
/*      */   protected boolean onPathBlocked(Path path, INotifyTask asker)
/*      */   {
/* 1294 */     return false;
/*      */   }
/*      */ 
/*      */   protected void dealFireDamage(int i)
/*      */   {
/* 1300 */     super.e(i * this.flammability);
/*      */   }
/*      */ 
/*      */   protected void dropFewItems(boolean flag, int amount)
/*      */   {
/* 1306 */     if (this.rand.nextInt(4) == 0)
/*      */     {
/* 1308 */       a(new EnumToolMaterial(mod_Invasion.itemRemnants, 1, 1), 0.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected float calcBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*      */   {
/* 1314 */     float multiplier = 1.0F;
/* 1315 */     if ((terrainMap instanceof IBlockAccessExtended))
/*      */     {
/* 1317 */       int mobDensity = ((IBlockAccessExtended)terrainMap).getLayeredData(node.xCoord, node.yCoord, node.zCoord) & 0x7;
/* 1318 */       multiplier += mobDensity * 3;
/*      */     }
/*      */ 
/* 1326 */     if ((node.yCoord > prevNode.yCoord) && (getCollide(terrainMap, node.xCoord, node.yCoord, node.zCoord) == 2))
/*      */     {
/* 1328 */       multiplier += 2.0F;
/*      */     }
/*      */ 
/* 1332 */     if (blockHasLadder(terrainMap, node.xCoord, node.yCoord, node.zCoord))
/*      */     {
/* 1334 */       multiplier += 5.0F;
/*      */     }
/*      */ 
/* 1337 */     if (node.action == PathAction.SWIM)
/*      */     {
/* 1340 */       multiplier *= ((node.yCoord <= prevNode.yCoord) && (terrainMap.a(node.xCoord, node.yCoord + 1, node.zCoord) != 0) ? 3.0F : 1.0F);
/* 1341 */       return prevNode.distanceTo(node) * 1.3F * multiplier;
/*      */     }
/*      */ 
/* 1344 */     int id = terrainMap.a(node.xCoord, node.yCoord, node.zCoord);
/* 1345 */     if (blockCosts.containsKey(Integer.valueOf(id)))
/*      */     {
/* 1347 */       return prevNode.distanceTo(node) * ((Float)blockCosts.get(Integer.valueOf(id))).floatValue() * multiplier;
/*      */     }
/* 1349 */     if (BlockEndPortal.s[id].isCollidable())
/*      */     {
/* 1351 */       return prevNode.distanceTo(node) * 3.2F * multiplier;
/*      */     }
/*      */ 
/* 1355 */     return prevNode.distanceTo(node) * 1.0F * multiplier;
/*      */   }
/*      */ 
/*      */   protected void calcPathOptions(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*      */   {
/* 1361 */     if ((currentNode.yCoord <= 0) || (currentNode.yCoord > 255)) {
/* 1362 */       return;
/*      */     }
/*      */ 
/* 1365 */     calcPathOptionsVertical(terrainMap, currentNode, pathFinder);
/*      */ 
/* 1367 */     if ((currentNode.action == PathAction.DIG) && (!canStandAt(terrainMap, currentNode.xCoord, currentNode.yCoord, currentNode.zCoord))) {
/* 1368 */       return;
/*      */     }
/*      */ 
/* 1375 */     int height = getJumpHeight();
/* 1376 */     for (int i = 1; i <= height; i++)
/*      */     {
/* 1378 */       if (getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord + i, currentNode.zCoord) == 0) {
/* 1379 */         height = i - 1;
/*      */       }
/*      */     }
/*      */ 
/* 1383 */     int maxFall = 8;
/* 1384 */     for (int i = 0; i < 4; i++)
/*      */     {
/* 1387 */       if (currentNode.action != PathAction.NONE)
/*      */       {
/* 1389 */         if ((i == 0) && (currentNode.action == PathAction.LADDER_UP_NX)) {
/* 1390 */           height = 0;
/*      */         }
/* 1392 */         if ((i == 1) && (currentNode.action == PathAction.LADDER_UP_PX)) {
/* 1393 */           height = 0;
/*      */         }
/* 1395 */         if ((i == 2) && (currentNode.action == PathAction.LADDER_UP_NZ)) {
/* 1396 */           height = 0;
/*      */         }
/* 1398 */         if ((i == 3) && (currentNode.action == PathAction.LADDER_UP_PZ)) {
/* 1399 */           height = 0;
/*      */         }
/*      */       }
/* 1402 */       int yOffset = 0;
/* 1403 */       int currentY = currentNode.yCoord + height;
/* 1404 */       boolean passedLevel = false;
/*      */       do
/*      */       {
/* 1407 */         yOffset = getNextLowestSafeYOffset(terrainMap, currentNode.xCoord + CoordsInt.offsetAdjX[i], currentY, currentNode.zCoord + CoordsInt.offsetAdjZ[i], maxFall + currentY - currentNode.yCoord);
/* 1408 */         if (yOffset > 0)
/*      */           break;
/* 1410 */         if (yOffset > -maxFall)
/*      */         {
/* 1412 */           pathFinder.addNode(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentY + yOffset, currentNode.zCoord + CoordsInt.offsetAdjZ[i], PathAction.NONE);
/*      */         }
/*      */ 
/* 1415 */         currentY += yOffset - 1;
/*      */ 
/* 1417 */         if ((!passedLevel) && (currentY <= currentNode.yCoord))
/*      */         {
/* 1419 */           passedLevel = true;
/* 1420 */           if (currentY != currentNode.yCoord)
/*      */           {
/* 1422 */             addAdjacent(terrainMap, currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + CoordsInt.offsetAdjZ[i], currentNode, pathFinder);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1431 */       while (currentY >= currentNode.yCoord);
/*      */     }
/*      */ 
/* 1434 */     if (canSwimHorizontal())
/*      */     {
/* 1436 */       for (int i = 0; i < 4; i++)
/*      */       {
/* 1438 */         if (getCollide(terrainMap, currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + CoordsInt.offsetAdjZ[i]) == -1)
/* 1439 */           pathFinder.addNode(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + CoordsInt.offsetAdjZ[i], PathAction.SWIM);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void calcPathOptionsVertical(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*      */   {
/* 1446 */     int collideUp = getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord);
/* 1447 */     if (collideUp > 0)
/*      */     {
/* 1450 */       if (terrainMap.a(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord) == BlockEndPortal.aK.blockID)
/*      */       {
/* 1454 */         int meta = terrainMap.h(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord);
/* 1455 */         PathAction action = PathAction.NONE;
/* 1456 */         if (meta == 4)
/* 1457 */           action = PathAction.LADDER_UP_PX;
/* 1458 */         else if (meta == 5)
/* 1459 */           action = PathAction.LADDER_UP_NX;
/* 1460 */         else if (meta == 2)
/* 1461 */           action = PathAction.LADDER_UP_PZ;
/* 1462 */         else if (meta == 3) {
/* 1463 */           action = PathAction.LADDER_UP_NZ;
/*      */         }
/*      */ 
/* 1466 */         if (currentNode.action == PathAction.NONE)
/*      */         {
/* 1468 */           pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, action);
/*      */         }
/* 1470 */         else if ((currentNode.action == PathAction.LADDER_UP_PX) || (currentNode.action == PathAction.LADDER_UP_NX) || (currentNode.action == PathAction.LADDER_UP_PZ) || (currentNode.action == PathAction.LADDER_UP_NZ))
/*      */         {
/* 1473 */           if (action == currentNode.action) {
/* 1474 */             pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, action);
/*      */           }
/*      */         }
/*      */         else {
/* 1478 */           pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, action);
/*      */         }
/*      */       }
/* 1481 */       else if (getCanClimb())
/*      */       {
/* 1483 */         if (isAdjacentSolidBlock(terrainMap, currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord)) {
/* 1484 */           pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.NONE);
/*      */         }
/*      */       }
/*      */     }
/* 1488 */     int below = getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord - 1, currentNode.zCoord);
/* 1489 */     int above = getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord);
/* 1490 */     if (getCanDigDown())
/*      */     {
/* 1492 */       if (below == 2)
/*      */       {
/* 1494 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord - 1, currentNode.zCoord, PathAction.DIG);
/*      */       }
/* 1496 */       else if (below == 1)
/*      */       {
/* 1498 */         int maxFall = 5;
/* 1499 */         int yOffset = getNextLowestSafeYOffset(terrainMap, currentNode.xCoord, currentNode.yCoord - 1, currentNode.zCoord, maxFall);
/* 1500 */         if (yOffset <= 0)
/*      */         {
/* 1502 */           pathFinder.addNode(currentNode.xCoord, currentNode.yCoord - 1 + yOffset, currentNode.zCoord, PathAction.NONE);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1507 */     if (canSwimVertical())
/*      */     {
/* 1509 */       if (below == -1) {
/* 1510 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord - 1, currentNode.zCoord, PathAction.SWIM);
/*      */       }
/* 1512 */       if (above == -1)
/* 1513 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.SWIM);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addAdjacent(EnumGameType terrainMap, int x, int y, int z, PathNode currentNode, PathfinderIM pathFinder)
/*      */   {
/* 1519 */     if (getCollide(terrainMap, x, y, z) <= 0) {
/* 1520 */       return;
/*      */     }
/* 1522 */     if (getCanClimb())
/*      */     {
/* 1524 */       if (isAdjacentSolidBlock(terrainMap, x, y, z))
/* 1525 */         pathFinder.addNode(x, y, z, PathAction.NONE);
/*      */     }
/* 1527 */     else if (terrainMap.a(x, y, z) == BlockEndPortal.aK.blockID)
/*      */     {
/* 1529 */       pathFinder.addNode(x, y, z, PathAction.NONE);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isAdjacentSolidBlock(EnumGameType terrainMap, int x, int y, int z)
/*      */   {
/* 1535 */     if ((this.collideSize.getXCoord() == 1) && (this.collideSize.getZCoord() == 1))
/*      */     {
/* 1537 */       for (int i = 0; i < 4; i++)
/*      */       {
/* 1539 */         int id = terrainMap.a(x + CoordsInt.offsetAdjX[i], y, z + CoordsInt.offsetAdjZ[i]);
/* 1540 */         if ((id > 0) && (BlockEndPortal.s[id].cU.isSolid()))
/* 1541 */           return true;
/*      */       }
/*      */     }
/* 1544 */     else if ((this.collideSize.getXCoord() == 2) && (this.collideSize.getZCoord() == 2))
/*      */     {
/* 1546 */       for (int i = 0; i < 8; i++)
/*      */       {
/* 1548 */         int id = terrainMap.a(x + CoordsInt.offsetAdj2X[i], y, z + CoordsInt.offsetAdj2Z[i]);
/* 1549 */         if ((id > 0) && (BlockEndPortal.s[id].cU.isSolid()))
/* 1550 */           return true;
/*      */       }
/*      */     }
/* 1553 */     return false;
/*      */   }
/*      */ 
/*      */   protected int getNextLowestSafeYOffset(EnumGameType terrainMap, int x, int y, int z, int maxOffsetMagnitude)
/*      */   {
/* 1563 */     for (int i = 0; (i + y > 0) && (i < maxOffsetMagnitude); i--)
/*      */     {
/* 1565 */       if ((canStandAtAndIsValid(terrainMap, x, y + i, z)) || ((canSwimHorizontal()) && (getCollide(terrainMap, x, y + i, z) == -1))) {
/* 1566 */         return i;
/*      */       }
/*      */     }
/* 1569 */     return 1;
/*      */   }
/*      */ 
/*      */   protected boolean canStandAt(EnumGameType terrainMap, int x, int y, int z)
/*      */   {
/* 1578 */     boolean isSolidBlock = false;
/* 1579 */     for (int xOffset = x; xOffset < x + this.collideSize.getXCoord(); xOffset++)
/*      */     {
/* 1581 */       for (int zOffset = z; zOffset < z + this.collideSize.getZCoord(); zOffset++)
/*      */       {
/* 1583 */         int id = terrainMap.a(xOffset, y - 1, zOffset);
/* 1584 */         if (id != 0)
/*      */         {
/* 1587 */           if (!BlockEndPortal.s[id].b(terrainMap, xOffset, y - 1, zOffset)) {
/* 1588 */             isSolidBlock = true;
/*      */           }
/* 1590 */           else if (avoidsBlock(id))
/* 1591 */             return false; 
/*      */         }
/*      */       }
/*      */     }
/* 1594 */     return isSolidBlock;
/*      */   }
/*      */ 
/*      */   protected boolean canStandAtAndIsValid(EnumGameType terrainMap, int x, int y, int z)
/*      */   {
/* 1603 */     if ((getCollide(terrainMap, x, y, z) > 0) && (canStandAt(terrainMap, x, y, z))) {
/* 1604 */       return true;
/*      */     }
/* 1606 */     return false;
/*      */   }
/*      */ 
/*      */   protected boolean canStandOnBlock(EnumGameType terrainMap, int x, int y, int z)
/*      */   {
/* 1615 */     int id = terrainMap.a(x, y, z);
/* 1616 */     if ((id != 0) && (!BlockEndPortal.s[id].b(terrainMap, x, y, z)) && (!avoidsBlock(id))) {
/* 1617 */       return true;
/*      */     }
/* 1619 */     return false;
/*      */   }
/*      */ 
/*      */   protected boolean blockHasLadder(EnumGameType terrainMap, int x, int y, int z)
/*      */   {
/* 1624 */     for (int i = 0; i < 4; i++)
/*      */     {
/* 1626 */       if (terrainMap.a(x + CoordsInt.offsetAdjX[i], y, z + CoordsInt.offsetAdjZ[i]) == BlockEndPortal.aK.blockID)
/* 1627 */         return true;
/*      */     }
/* 1629 */     return false;
/*      */   }
/*      */ 
/*      */   protected int getCollide(EnumGameType terrainMap, int x, int y, int z)
/*      */   {
/* 1638 */     boolean destructibleFlag = false;
/* 1639 */     boolean liquidFlag = false;
/* 1640 */     for (int xOffset = x; xOffset < x + this.collideSize.getXCoord(); xOffset++)
/*      */     {
/* 1642 */       for (int yOffset = y; yOffset < y + this.collideSize.getYCoord(); yOffset++)
/*      */       {
/* 1644 */         for (int zOffset = z; zOffset < z + this.collideSize.getZCoord(); zOffset++)
/*      */         {
/* 1646 */           int id = terrainMap.a(xOffset, yOffset, zOffset);
/* 1647 */           if (id > 0)
/*      */           {
/* 1650 */             if ((id == BlockEndPortal.G.blockID) || (id == BlockEndPortal.F.blockID) || (id == BlockEndPortal.I.blockID) || (id == BlockEndPortal.H.blockID))
/*      */             {
/* 1653 */               liquidFlag = true;
/*      */             }
/* 1655 */             else if (!BlockEndPortal.s[id].b(terrainMap, xOffset, yOffset, zOffset))
/*      */             {
/* 1657 */               if (isBlockDestructible(terrainMap, x, y, z, id))
/* 1658 */                 destructibleFlag = true;
/*      */               else
/* 1660 */                 return 0;
/*      */             }
/* 1662 */             else if (terrainMap.a(xOffset, yOffset - 1, zOffset) == BlockEndPortal.be.blockID)
/*      */             {
/* 1664 */               if (isBlockDestructible(terrainMap, x, y, z, BlockEndPortal.be.blockID)) {
/* 1665 */                 return 3;
/*      */               }
/* 1667 */               return 0;
/*      */             }
/*      */ 
/* 1670 */             if (avoidsBlock(id))
/* 1671 */               return -2;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1676 */     if (destructibleFlag)
/* 1677 */       return 2;
/* 1678 */     if (liquidFlag) {
/* 1679 */       return -1;
/*      */     }
/* 1681 */     return 1;
/*      */   }
/*      */ 
/*      */   protected boolean getLightLevelBelow8()
/*      */   {
/* 1686 */     int i = LongHashMapEntry.c(this.posX);
/* 1687 */     int j = LongHashMapEntry.c(this.E.b);
/* 1688 */     int k = LongHashMapEntry.c(this.posZ);
/* 1689 */     if (this.q.b(WorldType.worldTypes, i, j, k) > this.rand.nextInt(32))
/*      */     {
/* 1691 */       return false;
/*      */     }
/* 1693 */     int l = this.q.n(i, j, k);
/* 1694 */     if (this.q.P())
/*      */     {
/* 1696 */       int i1 = this.q.j;
/* 1697 */       this.q.j = 10;
/* 1698 */       l = this.q.n(i, j, k);
/* 1699 */       this.q.j = i1;
/*      */     }
/* 1701 */     return l <= this.rand.nextInt(8);
/*      */   }
/*      */ 
/*      */   protected void setAIGoal(Goal goal)
/*      */   {
/* 1706 */     this.currentGoal = goal;
/*      */   }
/*      */ 
/*      */   protected void setPrevAIGoal(Goal goal)
/*      */   {
/* 1711 */     this.prevGoal = goal;
/*      */   }
/*      */ 
/*      */   protected void transitionAIGoal(Goal newGoal)
/*      */   {
/* 1716 */     this.prevGoal = this.currentGoal;
/* 1717 */     this.currentGoal = newGoal;
/*      */   }
/*      */ 
/*      */   protected void setMoveState(MoveState moveState)
/*      */   {
/* 1722 */     this.moveState = moveState;
/* 1723 */     if (!this.q.I)
/* 1724 */       this.ah.b(23, Integer.valueOf(moveState.ordinal()));
/*      */   }
/*      */ 
/*      */   protected void setDestructiveness(int x)
/*      */   {
/* 1729 */     this.destructiveness = x;
/*      */   }
/*      */ 
/*      */   protected void setGravity(float acceleration)
/*      */   {
/* 1734 */     this.gravityAcel = acceleration;
/*      */   }
/*      */ 
/*      */   protected void setGroundFriction(float frictionCoefficient)
/*      */   {
/* 1739 */     this.groundFriction = frictionCoefficient;
/*      */   }
/*      */ 
/*      */   protected void setCanClimb(boolean flag)
/*      */   {
/* 1744 */     this.canClimb = flag;
/*      */   }
/*      */ 
/*      */   protected void setJumpHeight(int height)
/*      */   {
/* 1749 */     this.jumpHeight = height;
/*      */   }
/*      */ 
/*      */   protected void setBaseMoveSpeedStat(float speed)
/*      */   {
/* 1754 */     this.moveSpeedBase = speed;
/* 1755 */     this.moveSpeed = speed;
/*      */   }
/*      */ 
/*      */   protected void setMoveSpeedStat(float speed)
/*      */   {
/* 1760 */     this.moveSpeed = speed;
/* 1761 */     getNavigatorNew().setSpeed(speed);
/* 1762 */     getMoveHelper().setMoveSpeed(speed);
/*      */   }
/*      */ 
/*      */   protected void resetMoveSpeed()
/*      */   {
/* 1767 */     setMoveSpeedStat(this.moveSpeedBase);
/* 1768 */     getNavigatorNew().setSpeed(this.moveSpeedBase);
/*      */   }
/*      */ 
/*      */   protected void setTurnRate(float rate)
/*      */   {
/* 1773 */     this.turnRate = rate;
/*      */   }
/*      */ 
/*      */   protected void setName(String name)
/*      */   {
/* 1778 */     this.name = name;
/*      */   }
/*      */ 
/*      */   protected void setGender(int gender)
/*      */   {
/* 1783 */     this.gender = gender;
/*      */   }
/*      */ 
/*      */   protected void onDebugChange()
/*      */   {
/*      */   }
/*      */ 
/*      */   public static int getBlockType(int id)
/*      */   {
/* 1793 */     if (blockType.containsKey(Integer.valueOf(id))) {
/* 1794 */       return ((Integer)blockType.get(Integer.valueOf(id))).intValue();
/*      */     }
/* 1796 */     return 0;
/*      */   }
/*      */ 
/*      */   public static float getBlockStrength(int x, int y, int z, int id, ColorizerGrass world)
/*      */   {
/* 1801 */     if (blockSpecials.containsKey(Integer.valueOf(id)))
/*      */     {
/* 1803 */       BlockSpecial special = (BlockSpecial)blockSpecials.get(Integer.valueOf(id));
/* 1804 */       if (special == BlockSpecial.CONSTRUCTION_1)
/*      */       {
/* 1807 */         int bonus = 0;
/* 1808 */         if (world.a(x, y - 1, z) == id) bonus++;
/* 1809 */         if (world.a(x, y + 1, z) == id) bonus++;
/* 1810 */         if (world.a(x + 1, y, z) == id) bonus++;
/* 1811 */         if (world.a(x - 1, y, z) == id) bonus++;
/* 1812 */         if (world.a(x, y, z + 1) == id) bonus++;
/* 1813 */         if (world.a(x, y, z - 1) == id) bonus++;
/*      */ 
/* 1815 */         return ((Float)blockStrength.get(Integer.valueOf(id))).floatValue() * (1.0F + bonus * 0.1F);
/*      */       }
/* 1817 */       if (special == BlockSpecial.CONSTRUCTION_STONE)
/*      */       {
/* 1820 */         int bonus = 0;
/* 1821 */         int adjId = world.a(x, y - 1, z);
/* 1822 */         if ((adjId == 1) || (adjId == 4) || (adjId == 48) || (adjId == 98)) bonus++;
/* 1823 */         adjId = world.a(x, y + 1, z);
/* 1824 */         if ((adjId == 1) || (adjId == 4) || (adjId == 48) || (adjId == 98)) bonus++;
/* 1825 */         adjId = world.a(x - 1, y, z);
/* 1826 */         if ((adjId == 1) || (adjId == 4) || (adjId == 48) || (adjId == 98)) bonus++;
/* 1827 */         adjId = world.a(x + 1, y, z);
/* 1828 */         if ((adjId == 1) || (adjId == 4) || (adjId == 48) || (adjId == 98)) bonus++;
/* 1829 */         adjId = world.a(x, y, z - 1);
/* 1830 */         if ((adjId == 1) || (adjId == 4) || (adjId == 48) || (adjId == 98)) bonus++;
/* 1831 */         adjId = world.a(x, y, z + 1);
/* 1832 */         if ((adjId == 1) || (adjId == 4) || (adjId == 48) || (adjId == 98)) bonus++;
/*      */ 
/* 1834 */         return ((Float)blockStrength.get(Integer.valueOf(id))).floatValue() * (1.0F + bonus * 0.1F);
/*      */       }
/*      */     }
/*      */ 
/* 1838 */     if (blockStrength.containsKey(Integer.valueOf(id)))
/*      */     {
/* 1840 */       return ((Float)blockStrength.get(Integer.valueOf(id))).floatValue();
/*      */     }
/* 1842 */     return 2.5F;
/*      */   }
/*      */ 
/*      */   public static void putBlockStrength(int id, float strength)
/*      */   {
/* 1847 */     blockStrength.put(Integer.valueOf(id), Float.valueOf(strength));
/*      */   }
/*      */ 
/*      */   public static void putBlockCost(int id, float cost)
/*      */   {
/* 1852 */     blockCosts.put(Integer.valueOf(id), Float.valueOf(cost));
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1858 */     blockCosts.put(Integer.valueOf(0), Float.valueOf(1.0F));
/* 1859 */     blockCosts.put(Integer.valueOf(BlockEndPortal.aK.blockID), Float.valueOf(1.0F));
/* 1860 */     blockCosts.put(Integer.valueOf(BlockEndPortal.y.blockID), Float.valueOf(3.2F));
/* 1861 */     blockCosts.put(Integer.valueOf(BlockEndPortal.br.blockID), Float.valueOf(3.2F));
/* 1862 */     blockCosts.put(Integer.valueOf(BlockEndPortal.B.blockID), Float.valueOf(3.2F));
/* 1863 */     blockCosts.put(Integer.valueOf(BlockEndPortal.at.blockID), Float.valueOf(3.2F));
/* 1864 */     blockCosts.put(Integer.valueOf(BlockEndPortal.aq.blockID), Float.valueOf(3.2F));
/* 1865 */     blockCosts.put(Integer.valueOf(BlockEndPortal.au.blockID), Float.valueOf(3.2F));
/* 1866 */     blockCosts.put(Integer.valueOf(BlockEndPortal.an.blockID), Float.valueOf(3.2F));
/* 1867 */     blockCosts.put(Integer.valueOf(BlockEndPortal.A.blockID), Float.valueOf(2.0F));
/* 1868 */     blockCosts.put(Integer.valueOf(BlockEndPortal.J.blockID), Float.valueOf(2.0F));
/* 1869 */     blockCosts.put(Integer.valueOf(BlockEndPortal.K.blockID), Float.valueOf(2.0F));
/* 1870 */     blockCosts.put(Integer.valueOf(BlockEndPortal.R.blockID), Float.valueOf(2.0F));
/* 1871 */     blockCosts.put(Integer.valueOf(BlockEndPortal.P.blockID), Float.valueOf(2.0F));
/* 1872 */     blockCosts.put(Integer.valueOf(BlockEndPortal.aQ.blockID), Float.valueOf(2.24F));
/* 1873 */     blockCosts.put(Integer.valueOf(BlockEndPortal.aJ.blockID), Float.valueOf(1.4F));
/* 1874 */     blockCosts.put(Integer.valueOf(BlockEndPortal.bp.blockID), Float.valueOf(1.4F));
/* 1875 */     blockCosts.put(Integer.valueOf(BlockEndPortal.V.blockID), Float.valueOf(3.2F));
/* 1876 */     blockCosts.put(Integer.valueOf(BlockEndPortal.O.blockID), Float.valueOf(3.2F));
/* 1877 */     blockCosts.put(Integer.valueOf(BlockEndPortal.C.blockID), Float.valueOf(3.2F));
/* 1878 */     blockCosts.put(Integer.valueOf(BlockEndPortal.am.blockID), Float.valueOf(3.2F));
/* 1879 */     blockCosts.put(Integer.valueOf(BlockEndPortal.aC.blockID), Float.valueOf(3.2F));
/* 1880 */     blockCosts.put(Integer.valueOf(BlockEndPortal.be.blockID), Float.valueOf(3.2F));
/* 1881 */     blockCosts.put(Integer.valueOf(BlockEndPortal.bg.blockID), Float.valueOf(3.2F));
/* 1882 */     blockCosts.put(Integer.valueOf(BlockEndPortal.bF.blockID), Float.valueOf(3.2F));
/* 1883 */     blockCosts.put(Integer.valueOf(BlockEndPortal.bh.blockID), Float.valueOf(2.0F));
/* 1884 */     blockCosts.put(Integer.valueOf(BlockEndPortal.bi.blockID), Float.valueOf(2.0F));
/* 1885 */     blockCosts.put(Integer.valueOf(BlockEndPortal.ac.blockID), Float.valueOf(1.0F));
/*      */ 
/* 1889 */     blockStrength.put(Integer.valueOf(0), Float.valueOf(0.01F));
/* 1890 */     blockStrength.put(Integer.valueOf(BlockEndPortal.y.blockID), Float.valueOf(5.5F));
/* 1891 */     blockStrength.put(Integer.valueOf(BlockEndPortal.br.blockID), Float.valueOf(5.5F));
/* 1892 */     blockStrength.put(Integer.valueOf(BlockEndPortal.B.blockID), Float.valueOf(5.5F));
/* 1893 */     blockStrength.put(Integer.valueOf(BlockEndPortal.at.blockID), Float.valueOf(5.5F));
/* 1894 */     blockStrength.put(Integer.valueOf(BlockEndPortal.aq.blockID), Float.valueOf(5.5F));
/* 1895 */     blockStrength.put(Integer.valueOf(BlockEndPortal.au.blockID), Float.valueOf(7.7F));
/* 1896 */     blockStrength.put(Integer.valueOf(BlockEndPortal.an.blockID), Float.valueOf(7.7F));
/* 1897 */     blockStrength.put(Integer.valueOf(BlockEndPortal.A.blockID), Float.valueOf(3.125F));
/* 1898 */     blockStrength.put(Integer.valueOf(BlockEndPortal.z.blockID), Float.valueOf(3.125F));
/* 1899 */     blockStrength.put(Integer.valueOf(BlockEndPortal.J.blockID), Float.valueOf(2.5F));
/* 1900 */     blockStrength.put(Integer.valueOf(BlockEndPortal.K.blockID), Float.valueOf(2.5F));
/* 1901 */     blockStrength.put(Integer.valueOf(BlockEndPortal.R.blockID), Float.valueOf(2.5F));
/* 1902 */     blockStrength.put(Integer.valueOf(BlockEndPortal.P.blockID), Float.valueOf(1.25F));
/* 1903 */     blockStrength.put(Integer.valueOf(BlockEndPortal.bz.blockID), Float.valueOf(1.25F));
/* 1904 */     blockStrength.put(Integer.valueOf(BlockEndPortal.aQ.blockID), Float.valueOf(15.4F));
/* 1905 */     blockStrength.put(Integer.valueOf(BlockEndPortal.aJ.blockID), Float.valueOf(9.9F));
/* 1906 */     blockStrength.put(Integer.valueOf(BlockEndPortal.V.blockID), Float.valueOf(5.5F));
/* 1907 */     blockStrength.put(Integer.valueOf(BlockEndPortal.O.blockID), Float.valueOf(5.5F));
/* 1908 */     blockStrength.put(Integer.valueOf(BlockEndPortal.C.blockID), Float.valueOf(5.5F));
/* 1909 */     blockStrength.put(Integer.valueOf(BlockEndPortal.am.blockID), Float.valueOf(5.5F));
/* 1910 */     blockStrength.put(Integer.valueOf(BlockEndPortal.aC.blockID), Float.valueOf(5.5F));
/* 1911 */     blockStrength.put(Integer.valueOf(BlockEndPortal.be.blockID), Float.valueOf(5.5F));
/* 1912 */     blockStrength.put(Integer.valueOf(BlockEndPortal.bg.blockID), Float.valueOf(3.85F));
/* 1913 */     blockStrength.put(Integer.valueOf(BlockEndPortal.bF.blockID), Float.valueOf(5.5F));
/* 1914 */     blockStrength.put(Integer.valueOf(BlockEndPortal.bh.blockID), Float.valueOf(2.5F));
/* 1915 */     blockStrength.put(Integer.valueOf(BlockEndPortal.bi.blockID), Float.valueOf(2.5F));
/* 1916 */     blockStrength.put(Integer.valueOf(BlockEndPortal.ac.blockID), Float.valueOf(0.3F));
/* 1917 */     blockStrength.put(Integer.valueOf(BlockEndPortal.bP.blockID), Float.valueOf(15.0F));
/*      */ 
/* 1920 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.y.blockID), BlockSpecial.CONSTRUCTION_STONE);
/* 1921 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.br.blockID), BlockSpecial.CONSTRUCTION_STONE);
/* 1922 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.B.blockID), BlockSpecial.CONSTRUCTION_STONE);
/* 1923 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.at.blockID), BlockSpecial.CONSTRUCTION_STONE);
/* 1924 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.aq.blockID), BlockSpecial.CONSTRUCTION_1);
/* 1925 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.V.blockID), BlockSpecial.CONSTRUCTION_1);
/* 1926 */     blockSpecials.put(Integer.valueOf(112), BlockSpecial.CONSTRUCTION_1);
/* 1927 */     blockSpecials.put(Integer.valueOf(BlockEndPortal.au.blockID), BlockSpecial.DEFLECTION_1);
/*      */ 
/* 1929 */     blockType.put(Integer.valueOf(0), Integer.valueOf(1));
/* 1930 */     blockType.put(Integer.valueOf(BlockEndPortal.ac.blockID), Integer.valueOf(1));
/* 1931 */     blockType.put(Integer.valueOf(BlockEndPortal.ad.cF), Integer.valueOf(1));
/* 1932 */     blockType.put(Integer.valueOf(BlockEndPortal.aj.blockID), Integer.valueOf(1));
/* 1933 */     blockType.put(Integer.valueOf(BlockEndPortal.ai.blockID), Integer.valueOf(1));
/* 1934 */     blockType.put(Integer.valueOf(BlockEndPortal.aP.blockID), Integer.valueOf(1));
/* 1935 */     blockType.put(Integer.valueOf(BlockEndPortal.cp.blockID), Integer.valueOf(1));
/* 1936 */     blockType.put(Integer.valueOf(BlockEndPortal.aR.blockID), Integer.valueOf(1));
/* 1937 */     blockType.put(Integer.valueOf(BlockEndPortal.co.blockID), Integer.valueOf(1));
/* 1938 */     blockType.put(Integer.valueOf(BlockEndPortal.aW.blockID), Integer.valueOf(1));
/* 1939 */     blockType.put(Integer.valueOf(BlockEndPortal.ck.blockID), Integer.valueOf(1));
/* 1940 */     blockType.put(Integer.valueOf(BlockEndPortal.aU.blockID), Integer.valueOf(1));
/* 1941 */     blockType.put(Integer.valueOf(BlockEndPortal.aV.blockID), Integer.valueOf(1));
/* 1942 */     blockType.put(Integer.valueOf(BlockEndPortal.av.blockID), Integer.valueOf(1));
/* 1943 */     blockType.put(Integer.valueOf(BlockEndPortal.aO.blockID), Integer.valueOf(1));
/* 1944 */     blockType.put(Integer.valueOf(BlockEndPortal.bc.blockID), Integer.valueOf(1));
/* 1945 */     blockType.put(Integer.valueOf(BlockEndPortal.aE.blockID), Integer.valueOf(1));
/* 1946 */     blockType.put(Integer.valueOf(BlockEndPortal.ci.blockID), Integer.valueOf(1));
/* 1947 */     blockType.put(Integer.valueOf(BlockEndPortal.cj.blockID), Integer.valueOf(1));
/* 1948 */     blockType.put(Integer.valueOf(BlockEndPortal.aw.blockID), Integer.valueOf(2));
/* 1949 */     blockType.put(Integer.valueOf(BlockEndPortal.E.blockID), Integer.valueOf(2));
/* 1950 */     blockType.put(Integer.valueOf(BlockEndPortal.I.blockID), Integer.valueOf(2));
/* 1951 */     blockType.put(Integer.valueOf(BlockEndPortal.H.blockID), Integer.valueOf(2));
/*      */   }
/*      */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMLiving
 * JD-Core Version:    0.6.2
 */