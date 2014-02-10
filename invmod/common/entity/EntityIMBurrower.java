/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.PosRotate3D;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
/*     */ 
/*     */ public class EntityIMBurrower extends EntityIMMob
/*     */   implements ICanDig
/*     */ {
/*     */   public static final int NUMBER_OF_SEGMENTS = 16;
/*     */   private final NavigatorBurrower bo;
/*     */   private final PathNavigateAdapter oldNavAdapter;
/*     */   private TerrainModifier terrainModifier;
/*     */   private TerrainDigger terrainDigger;
/*     */   private EntityAIBase goals;
/*     */   private PosRotate3D[] segments3D;
/*     */   private PosRotate3D[] segments3DLastTick;
/*     */   private float rotX;
/*     */   private float rotY;
/*     */   private float rotZ;
/*     */   protected float prevRotX;
/*     */   protected float prevRotY;
/*     */   protected float prevRotZ;
/*     */ 
/*     */   public EntityIMBurrower(ColorizerGrass world)
/*     */   {
/*  43 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMBurrower(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  48 */     super(world, nexus);
/*     */ 
/*  51 */     IPathSource pathSource = getPathSource();
/*  52 */     pathSource.setSearchDepth(800);
/*  53 */     pathSource.setQuickFailDepth(400);
/*  54 */     this.bo = new NavigatorBurrower(this, pathSource, 16, -4);
/*  55 */     this.oldNavAdapter = new PathNavigateAdapter(this.bo);
/*     */ 
/*  58 */     this.terrainModifier = new TerrainModifier(this, 2.0F);
/*  59 */     this.terrainDigger = new TerrainDigger(this, this.terrainModifier, 1.0F);
/*     */ 
/*  62 */     setName("Burrower");
/*  63 */     setGender(0);
/*  64 */     setSize(0.5F, 0.5F);
/*  65 */     setJumpHeight(0);
/*  66 */     setCanClimb(true);
/*  67 */     setDestructiveness(2);
/*  68 */     this.maxDestructiveness = 2;
/*  69 */     this.blockRemoveSpeed = 0.5F;
/*     */ 
/*  78 */     this.segments3D = new PosRotate3D[16];
/*  79 */     this.segments3DLastTick = new PosRotate3D[16];
/*     */ 
/*  81 */     PosRotate3D zero = new PosRotate3D();
/*  82 */     for (int i = 0; i < 16; i++)
/*     */     {
/*  84 */       this.segments3D[i] = zero;
/*  85 */       this.segments3DLastTick[i] = zero;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  97 */     return "EntityIMBurrower#u-u-u";
/*     */   }
/*     */ 
/*     */   public EnumGameType getTerrain()
/*     */   {
/* 103 */     return this.q;
/*     */   }
/*     */ 
/*     */   public float getBlockPathCost(GenLayerVoronoiZoom prevNode, GenLayerVoronoiZoom node, EnumGameType worldMap)
/*     */   {
/* 108 */     int id = worldMap.a(node.a, node.b, node.c);
/*     */ 
/* 111 */     float penalty = 0.0F;
/* 112 */     int enclosedLevelSide = 0;
/* 113 */     if (!this.q.t(node.a, node.b - 1, node.c)) penalty += 0.3F;
/* 114 */     if (!this.q.t(node.a, node.b + 1, node.c)) penalty += 2.0F;
/* 115 */     if (!this.q.t(node.a + 1, node.b, node.c)) enclosedLevelSide++;
/* 116 */     if (!this.q.t(node.a - 1, node.b, node.c)) enclosedLevelSide++;
/* 117 */     if (!this.q.t(node.a, node.b, node.c + 1)) enclosedLevelSide++;
/* 118 */     if (!this.q.t(node.a, node.b, node.c - 1)) enclosedLevelSide++;
/*     */ 
/* 120 */     if (enclosedLevelSide > 2) {
/* 121 */       enclosedLevelSide = 2;
/*     */     }
/* 123 */     penalty += enclosedLevelSide * 0.5F;
/*     */ 
/* 126 */     if (id == 0)
/*     */     {
/* 128 */       return prevNode.a(node) * 1.0F * penalty;
/*     */     }
/* 130 */     if (blockCosts.containsKey(Integer.valueOf(id)))
/*     */     {
/* 132 */       return prevNode.a(node) * 1.0F * 1.3F * penalty;
/*     */     }
/* 134 */     if (BlockEndPortal.s[id].isCollidable())
/*     */     {
/* 136 */       return prevNode.a(node) * 1.0F * 1.3F * penalty;
/*     */     }
/*     */ 
/* 140 */     return prevNode.a(node) * 1.0F * penalty;
/*     */   }
/*     */ 
/*     */   public float getBlockRemovalCost(int x, int y, int z)
/*     */   {
/* 147 */     return getBlockStrength(x, y, z) * 20.0F;
/*     */   }
/*     */ 
/*     */   public boolean canClearBlock(int x, int y, int z)
/*     */   {
/* 153 */     int id = this.q.a(x, y, z);
/* 154 */     return (id == 0) || (isBlockDestructible(this.q, x, y, z, id));
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 163 */     return "";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/* 169 */     return 3;
/*     */   }
/*     */ 
/*     */   public PathNavigateAdapter getNavigator()
/*     */   {
/* 187 */     return this.oldNavAdapter;
/*     */   }
/*     */ 
/*     */   public INavigation getNavigatorNew()
/*     */   {
/* 196 */     return this.bo;
/*     */   }
/*     */ 
/*     */   protected boolean onPathBlocked(int x, int y, int z, INotifyTask notifee)
/*     */   {
/* 201 */     if (this.terrainDigger.askClearPosition(x, y, z, notifee, 1.0F)) {
/* 202 */       return true;
/*     */     }
/* 204 */     return false;
/*     */   }
/*     */ 
/*     */   public float getRotX()
/*     */   {
/* 209 */     return this.rotX;
/*     */   }
/*     */ 
/*     */   public float getRotY()
/*     */   {
/* 214 */     return this.rotY;
/*     */   }
/*     */ 
/*     */   public float getRotZ()
/*     */   {
/* 219 */     return this.rotZ;
/*     */   }
/*     */ 
/*     */   public float getPrevRotX()
/*     */   {
/* 224 */     return this.prevRotX;
/*     */   }
/*     */ 
/*     */   public float getPrevRotY()
/*     */   {
/* 229 */     return this.prevRotY;
/*     */   }
/*     */ 
/*     */   public float getPrevRotZ()
/*     */   {
/* 234 */     return this.prevRotZ;
/*     */   }
/*     */ 
/*     */   public PosRotate3D[] getSegments3D()
/*     */   {
/* 239 */     return this.segments3D;
/*     */   }
/*     */ 
/*     */   public PosRotate3D[] getSegments3DLastTick()
/*     */   {
/* 244 */     return this.segments3DLastTick;
/*     */   }
/*     */ 
/*     */   public void setSegment(int index, PosRotate3D pos)
/*     */   {
/* 249 */     if (index < this.segments3D.length)
/*     */     {
/* 251 */       this.segments3DLastTick[index] = this.segments3D[index];
/* 252 */       this.segments3D[index] = pos;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setHeadRotation(PosRotate3D pos)
/*     */   {
/* 258 */     this.prevRotX = this.rotX;
/* 259 */     this.prevRotY = this.rotY;
/* 260 */     this.prevRotZ = this.rotZ;
/* 261 */     this.rotX = pos.getRotX();
/* 262 */     this.rotY = pos.getRotY();
/* 263 */     this.rotZ = pos.getRotZ();
/*     */   }
/*     */ 
/*     */   public void moveEntityWithHeading(float x, float z)
/*     */   {
/* 269 */     if (isWet())
/*     */     {
/* 271 */       double y = this.posY;
/* 272 */       moveFlying(x, z, 0.02F);
/* 273 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 274 */       this.motionX *= 0.8D;
/* 275 */       this.motionY *= 0.8D;
/* 276 */       this.motionZ *= 0.8D;
/* 277 */       this.motionY -= 0.02D;
/* 278 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/* 279 */         this.motionY = 0.3D;
/*     */     }
/* 281 */     else if (handleWaterMovement())
/*     */     {
/* 283 */       double y = this.posY;
/* 284 */       moveFlying(x, z, 0.02F);
/* 285 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 286 */       this.motionX *= 0.5D;
/* 287 */       this.motionY *= 0.5D;
/* 288 */       this.motionZ *= 0.5D;
/* 289 */       this.motionY -= 0.02D;
/* 290 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/* 291 */         this.motionY = 0.3D;
/*     */     }
/*     */     else
/*     */     {
/* 295 */       float groundFriction = 1.0F;
/* 296 */       if (this.onGround)
/*     */       {
/* 298 */         groundFriction = 0.546F;
/* 299 */         int i = this.q.a(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.E.b) - 1, LongHashMapEntry.c(this.posZ));
/* 300 */         if (i > 0) {
/* 301 */           groundFriction = BlockEndPortal.s[i].slipperiness * 0.91F;
/*     */         }
/*     */       }
/* 304 */       if (isOnLadder())
/*     */       {
/* 306 */         float maxLadderXZSpeed = 0.15F;
/* 307 */         if (this.motionX < -maxLadderXZSpeed)
/* 308 */           this.motionX = (-maxLadderXZSpeed);
/* 309 */         if (this.motionX > maxLadderXZSpeed)
/* 310 */           this.motionX = maxLadderXZSpeed;
/* 311 */         if (this.motionZ < -maxLadderXZSpeed)
/* 312 */           this.motionZ = (-maxLadderXZSpeed);
/* 313 */         if (this.motionZ > maxLadderXZSpeed) {
/* 314 */           this.motionZ = maxLadderXZSpeed;
/*     */         }
/* 316 */         this.fallDistance = 0.0F;
/* 317 */         if (this.motionY < -0.15D) {
/* 318 */           this.motionY = -0.15D;
/*     */         }
/* 320 */         if ((isRiding()) && (this.motionY < 0.0D)) {
/* 321 */           this.motionY = 0.0D;
/*     */         }
/*     */       }
/* 324 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 325 */       if ((this.isCollidedHorizontally) && (isOnLadder())) {
/* 326 */         this.motionY = 0.2D;
/*     */       }
/* 328 */       float airResistance = 0.98F;
/* 329 */       float gravityAcel = 0.0F;
/* 330 */       this.motionY -= gravityAcel;
/* 331 */       this.motionY *= airResistance;
/* 332 */       this.motionX *= airResistance;
/* 333 */       this.motionZ *= airResistance;
/*     */     }
/*     */ 
/* 336 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 337 */     double dX = this.posX - this.prevPosX;
/* 338 */     double dZ = this.posZ - this.prevPosZ;
/* 339 */     float f4 = LongHashMapEntry.a(dX * dX + dZ * dZ) * 4.0F;
/*     */ 
/* 341 */     if (f4 > 1.0F)
/*     */     {
/* 343 */       f4 = 1.0F;
/*     */     }
/*     */ 
/* 346 */     this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
/* 347 */     this.limbSwing += this.limbSwingAmount;
/*     */   }
/*     */ 
/*     */   protected void bh()
/*     */   {
/* 353 */     super.bh();
/* 354 */     this.terrainModifier.onUpdate();
/*     */   }
/*     */ 
/*     */   public void collideWithNearbyEntities()
/*     */   {
/* 360 */     super.collideWithNearbyEntities();
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMBurrower
 * JD-Core Version:    0.6.2
 */