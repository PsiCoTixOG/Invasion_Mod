/*     */ package invmod.common.entity;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMArrowOld extends nm
/*     */   implements EntityLargeFireball
/*     */ {
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int inData;
/*     */   private boolean inGround;
/*     */   public boolean doesArrowBelongToPlayer;
/*     */   public int arrowShake;
/*     */   public nm shootingEntity;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   private int targetsHit;
/*     */   public boolean arrowCritical;
/*     */   private List<Integer> entitiesHit;
/*     */ 
/*     */   public EntityIMArrowOld(ColorizerGrass world)
/*     */   {
/*  47 */     super(world);
/*  48 */     this.renderDistanceWeight = 10.0D;
/*  49 */     this.xTile = -1;
/*  50 */     this.yTile = -1;
/*  51 */     this.zTile = -1;
/*  52 */     this.inTile = 0;
/*  53 */     this.inData = 0;
/*  54 */     this.targetsHit = 0;
/*  55 */     this.entitiesHit = new ArrayList();
/*  56 */     this.inGround = false;
/*  57 */     this.doesArrowBelongToPlayer = false;
/*  58 */     this.arrowShake = 0;
/*  59 */     this.ticksInAir = 0;
/*  60 */     this.arrowCritical = false;
/*  61 */     setSize(0.5F, 0.5F);
/*  62 */     setFire(5);
/*     */   }
/*     */ 
/*     */   public EntityIMArrowOld(ColorizerGrass world, double d, double d1, double d2)
/*     */   {
/*  67 */     super(world);
/*  68 */     this.renderDistanceWeight = 10.0D;
/*  69 */     this.xTile = -1;
/*  70 */     this.yTile = -1;
/*  71 */     this.zTile = -1;
/*  72 */     this.inTile = 0;
/*  73 */     this.inData = 0;
/*  74 */     this.targetsHit = 0;
/*  75 */     this.entitiesHit = new ArrayList();
/*  76 */     this.inGround = false;
/*  77 */     this.doesArrowBelongToPlayer = false;
/*  78 */     this.arrowShake = 0;
/*  79 */     this.ticksInAir = 0;
/*  80 */     this.arrowCritical = false;
/*  81 */     setSize(0.5F, 0.5F);
/*  82 */     setPosition(d, d1, d2);
/*  83 */     setFire(5);
/*  84 */     this.yOffset = 0.0F;
/*     */   }
/*     */ 
/*     */   public EntityIMArrowOld(ColorizerGrass world, EntityLeashKnot entityliving, float f)
/*     */   {
/*  89 */     super(world);
/*  90 */     this.renderDistanceWeight = 10.0D;
/*  91 */     this.xTile = -1;
/*  92 */     this.yTile = -1;
/*  93 */     this.zTile = -1;
/*  94 */     this.inTile = 0;
/*  95 */     this.inData = 0;
/*  96 */     this.inGround = false;
/*  97 */     this.arrowShake = 0;
/*  98 */     this.ticksInAir = 0;
/*  99 */     this.arrowCritical = false;
/* 100 */     this.targetsHit = 0;
/* 101 */     this.entitiesHit = new ArrayList();
/* 102 */     this.shootingEntity = entityliving;
/* 103 */     this.doesArrowBelongToPlayer = (entityliving instanceof CallableItemName);
/* 104 */     setSize(0.5F, 0.5F);
/* 105 */     setFire(5);
/* 106 */     setLocationAndAngles(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
/* 107 */     this.posX -= LongHashMapEntry.b(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/* 108 */     this.posY -= 0.1000000014901161D;
/* 109 */     this.posZ -= LongHashMapEntry.a(this.rotationYaw / 180.0F * 3.141593F) * 0.16F;
/* 110 */     setPosition(this.posX, this.posY, this.posZ);
/* 111 */     this.yOffset = 0.0F;
/* 112 */     this.motionX = (-LongHashMapEntry.a(this.rotationYaw / 180.0F * 3.141593F) * LongHashMapEntry.b(this.rotationPitch / 180.0F * 3.141593F));
/* 113 */     this.motionZ = (LongHashMapEntry.b(this.rotationYaw / 180.0F * 3.141593F) * LongHashMapEntry.b(this.rotationPitch / 180.0F * 3.141593F));
/* 114 */     this.motionY = (-LongHashMapEntry.a(this.rotationPitch / 180.0F * 3.141593F));
/* 115 */     c(this.motionX, this.motionY, this.motionZ, f * 1.5F, 1.0F);
/*     */   }
/*     */ 
/*     */   public void setPositionAndRotation2(double par1, double par3, double par5, float par7, float par8, int par9)
/*     */   {
/* 121 */     setPosition(par1, par3, par5);
/* 122 */     setRotation(par7, par8);
/*     */   }
/*     */ 
/*     */   public void c(double d, double d1, double d2, float f, float f1)
/*     */   {
/* 127 */     float f2 = LongHashMapEntry.a(d * d + d1 * d1 + d2 * d2);
/* 128 */     d /= f2;
/* 129 */     d1 /= f2;
/* 130 */     d2 /= f2;
/* 131 */     d += this.rand.nextGaussian() * 0.007499999832361937D * f1;
/* 132 */     d1 += this.rand.nextGaussian() * 0.007499999832361937D * f1;
/* 133 */     d2 += this.rand.nextGaussian() * 0.007499999832361937D * f1;
/* 134 */     d *= f;
/* 135 */     d1 *= f;
/* 136 */     d2 *= f;
/* 137 */     this.motionX = d;
/* 138 */     this.motionY = d1;
/* 139 */     this.motionZ = d2;
/* 140 */     float f3 = LongHashMapEntry.a(d * d + d2 * d2);
/* 141 */     this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D));
/* 142 */     this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(d1, f3) * 180.0D / 3.141592741012573D));
/* 143 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   public void setVelocity(double d, double d1, double d2)
/*     */   {
/* 149 */     this.motionX = d;
/* 150 */     this.motionY = d1;
/* 151 */     this.motionZ = d2;
/* 152 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/*     */     {
/* 154 */       float f = LongHashMapEntry.a(d * d + d2 * d2);
/* 155 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D));
/* 156 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(d1, f) * 180.0D / 3.141592741012573D));
/* 157 */       this.prevRotationPitch = this.rotationPitch;
/* 158 */       this.prevRotationYaw = this.rotationYaw;
/* 159 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 160 */       this.ticksInGround = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 167 */     super.l_();
/* 168 */     if ((this.prevRotationPitch == 0.0F) && (this.prevRotationYaw == 0.0F))
/*     */     {
/* 170 */       float f = LongHashMapEntry.a(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 171 */       this.prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592741012573D));
/* 172 */       this.prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(this.motionY, f) * 180.0D / 3.141592741012573D));
/*     */     }
/* 174 */     int i = this.q.a(this.xTile, this.yTile, this.zTile);
/* 175 */     if (i > 0)
/*     */     {
/* 177 */       BlockEndPortal.s[i].a(this.q, this.xTile, this.yTile, this.zTile);
/* 178 */       BlockPistonExtension axisalignedbb = BlockEndPortal.s[i].b(this.q, this.xTile, this.yTile, this.zTile);
/* 179 */       if ((axisalignedbb != null) && (axisalignedbb.a(AABBPool.a(this.posX, this.posY, this.posZ))))
/*     */       {
/* 181 */         this.inGround = true;
/*     */       }
/*     */     }
/* 184 */     if (this.arrowShake > 0)
/*     */     {
/* 186 */       this.arrowShake -= 1;
/*     */     }
/*     */ 
/* 189 */     if (this.inGround)
/*     */     {
/* 191 */       int j = this.q.a(this.xTile, this.yTile, this.zTile);
/* 192 */       int k = this.q.h(this.xTile, this.yTile, this.zTile);
/* 193 */       if ((j == this.inTile) && (k == this.inData))
/*     */       {
/* 195 */         this.ticksInGround += 1;
/* 196 */         if (this.ticksInGround == 1200)
/*     */         {
/* 198 */           preparePlayerToSpawn();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 203 */         this.inGround = false;
/* 204 */         this.motionX *= this.rand.nextFloat() * 0.2F;
/* 205 */         this.motionY *= this.rand.nextFloat() * 0.2F;
/* 206 */         this.motionZ *= this.rand.nextFloat() * 0.2F;
/* 207 */         this.ticksInGround = 0;
/* 208 */         this.ticksInAir = 0;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 213 */       this.ticksInAir += 1;
/* 214 */       AABBPool vec3d = AABBPool.a(this.posX, this.posY, this.posZ);
/* 215 */       AABBPool vec3d1 = AABBPool.a(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 216 */       AxisAlignedBB movingobjectposition = this.q.a(vec3d, vec3d1, false, true);
/* 217 */       vec3d = AABBPool.a(this.posX, this.posY, this.posZ);
/* 218 */       vec3d1 = AABBPool.a(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 219 */       if (movingobjectposition != null)
/*     */       {
/* 221 */         vec3d1 = AABBPool.a(movingobjectposition.maxZ.listAABB, movingobjectposition.maxZ.nextPoolIndex, movingobjectposition.maxZ.maxPoolIndex);
/*     */       }
/* 223 */       nm entity = null;
/* 224 */       List list = this.q.b(this, this.E.a(this.motionX, this.motionY, this.motionZ).b(1.0D, 1.0D, 1.0D));
/* 225 */       double d = 0.0D;
/* 226 */       for (int l = 0; l < list.size(); l++)
/*     */       {
/* 228 */         nm entity1 = (nm)list.get(l);
/* 229 */         if ((entity1.K()) && ((entity1 != this.shootingEntity) || (this.ticksInAir >= 5)))
/*     */         {
/* 233 */           float f5 = 0.3F;
/* 234 */           BlockPistonExtension axisalignedbb1 = entity1.E.b(f5, f5, f5);
/* 235 */           AxisAlignedBB movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
/* 236 */           if (movingobjectposition1 != null)
/*     */           {
/* 240 */             double d1 = vec3d.d(movingobjectposition1.maxZ);
/* 241 */             if ((d1 < d) || (d == 0.0D))
/*     */             {
/* 243 */               entity = entity1;
/* 244 */               d = d1;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 248 */       if (entity != null)
/*     */       {
/* 250 */         movingobjectposition = new AxisAlignedBB(entity);
/*     */       }
/* 252 */       if (movingobjectposition != null)
/*     */       {
/* 254 */         if (movingobjectposition.theAABBLocalPool != null)
/*     */         {
/* 256 */           float f1 = LongHashMapEntry.a(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 257 */           int j1 = (int)Math.ceil(f1 * 2.0D);
/* 258 */           if (this.arrowCritical)
/*     */           {
/* 260 */             j1 = j1 * 3 / 2 + 1;
/*     */           }
/* 262 */           if ((movingobjectposition.theAABBLocalPool instanceof EntityLivingBase))
/*     */           {
/* 264 */             boolean alreadyHit = false;
/* 265 */             for (Integer id : this.entitiesHit)
/*     */             {
/* 267 */               if (movingobjectposition.theAABBLocalPool.k == id.intValue())
/*     */               {
/* 269 */                 alreadyHit = true;
/*     */               }
/*     */             }
/*     */ 
/* 273 */             if (!alreadyHit)
/*     */             {
/* 275 */               this.entitiesHit.add(Integer.valueOf(movingobjectposition.theAABBLocalPool.k));
/* 276 */               if (!this.q.I)
/*     */               {
/* 278 */                 EntityLivingBase entityLiving = (EntityLivingBase)movingobjectposition.theAABBLocalPool;
/* 279 */                 entityLiving.setArrowCountInEntity(entityLiving.aT() + 1);
/*     */               }
/* 281 */               if (this.targetsHit == 0)
/*     */               {
/* 283 */                 this.targetsHit += 1;
/* 284 */                 movingobjectposition.theAABBLocalPool.a(CombatTracker.j, j1);
/* 285 */                 movingobjectposition.theAABBLocalPool.d(8);
/*     */               }
/* 287 */               else if (this.targetsHit < 8)
/*     */               {
/* 289 */                 this.targetsHit += 1;
/* 290 */                 movingobjectposition.theAABBLocalPool.d(8);
/*     */               }
/*     */               else
/*     */               {
/* 294 */                 movingobjectposition.theAABBLocalPool.d(8);
/* 295 */                 this.q.a(this, "random.drr", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 296 */                 preparePlayerToSpawn();
/*     */               }
/*     */             }
/*     */           }
/*     */           else {
/* 301 */             this.motionX *= -0.1000000014901161D;
/* 302 */             this.motionY *= -0.1000000014901161D;
/* 303 */             this.motionZ *= -0.1000000014901161D;
/* 304 */             this.rotationYaw += 180.0F;
/* 305 */             this.prevRotationYaw += 180.0F;
/* 306 */             this.ticksInAir = 0;
/*     */           }
/*     */         }
/*     */         else {
/* 310 */           this.xTile = movingobjectposition.minY;
/* 311 */           this.yTile = movingobjectposition.minZ;
/* 312 */           this.zTile = movingobjectposition.maxX;
/* 313 */           this.inTile = this.q.a(this.xTile, this.yTile, this.zTile);
/* 314 */           this.inData = this.q.h(this.xTile, this.yTile, this.zTile);
/* 315 */           this.motionX = ((float)(movingobjectposition.maxZ.listAABB - this.posX));
/* 316 */           this.motionY = ((float)(movingobjectposition.maxZ.nextPoolIndex - this.posY));
/* 317 */           this.motionZ = ((float)(movingobjectposition.maxZ.maxPoolIndex - this.posZ));
/* 318 */           float f2 = LongHashMapEntry.a(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 319 */           this.posX -= this.motionX / f2 * 0.0500000007450581D;
/* 320 */           this.posY -= this.motionY / f2 * 0.0500000007450581D;
/* 321 */           this.posZ -= this.motionZ / f2 * 0.0500000007450581D;
/* 322 */           this.q.a(this, "random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 323 */           this.inGround = true;
/* 324 */           this.arrowShake = 7;
/* 325 */           this.arrowCritical = false;
/* 326 */           if (this.inTile != 0)
/*     */           {
/* 328 */             BlockEndPortal.s[this.inTile].a(this.q, this.xTile, this.yTile, this.zTile, this);
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 334 */       for (int i1 = 0; i1 < 4; i1++)
/*     */       {
/* 336 */         this.q.a("lava", this.posX + this.motionX * i1 / 4.0D, this.posY + this.motionY * i1 / 4.0D, this.posZ + this.motionZ * i1 / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
/*     */       }
/*     */ 
/* 340 */       this.posX += this.motionX;
/* 341 */       this.posY += this.motionY;
/* 342 */       this.posZ += this.motionZ;
/* 343 */       float f3 = LongHashMapEntry.a(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 344 */       this.rotationYaw = ((float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / 3.141592741012573D));
/* 345 */       for (this.rotationPitch = ((float)(Math.atan2(this.motionY, f3) * 180.0D / 3.141592741012573D)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/* 346 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F) this.prevRotationPitch += 360.0F;
/* 347 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F) this.prevRotationYaw -= 360.0F;
/* 348 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F) this.prevRotationYaw += 360.0F;
/* 349 */       this.rotationPitch = (this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F);
/* 350 */       this.rotationYaw = (this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F);
/* 351 */       float f4 = 0.99F;
/* 352 */       float f6 = 0.05F;
/* 353 */       if (isWet())
/*     */       {
/* 355 */         for (int k1 = 0; k1 < 4; k1++)
/*     */         {
/* 357 */           float f7 = 0.25F;
/* 358 */           this.q.a("bubble", this.posX - this.motionX * f7, this.posY - this.motionY * f7, this.posZ - this.motionZ * f7, this.motionX, this.motionY, this.motionZ);
/*     */         }
/*     */ 
/* 361 */         f4 = 0.8F;
/*     */       }
/* 363 */       this.motionX *= f4;
/* 364 */       this.motionY *= f4;
/* 365 */       this.motionZ *= f4;
/* 366 */       this.motionY -= f6;
/* 367 */       setPosition(this.posX, this.posY, this.posZ);
/* 368 */       kill();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void b(NBTTagByte nbttagcompound)
/*     */   {
/* 375 */     nbttagcompound.a("xTile", (short)this.xTile);
/* 376 */     nbttagcompound.a("yTile", (short)this.yTile);
/* 377 */     nbttagcompound.a("zTile", (short)this.zTile);
/* 378 */     nbttagcompound.a("inTile", (byte)this.inTile);
/* 379 */     nbttagcompound.a("inData", (byte)this.inData);
/* 380 */     nbttagcompound.a("shake", (byte)this.arrowShake);
/* 381 */     nbttagcompound.a("inGround", (byte)(this.inGround ? 1 : 0));
/* 382 */     nbttagcompound.a("player", this.doesArrowBelongToPlayer);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagByte nbttagcompound)
/*     */   {
/* 388 */     this.xTile = nbttagcompound.d("xTile");
/* 389 */     this.yTile = nbttagcompound.d("yTile");
/* 390 */     this.zTile = nbttagcompound.d("zTile");
/* 391 */     this.inTile = (nbttagcompound.c("inTile") & 0xFF);
/* 392 */     this.inData = (nbttagcompound.c("inData") & 0xFF);
/* 393 */     this.arrowShake = (nbttagcompound.c("shake") & 0xFF);
/* 394 */     this.inGround = (nbttagcompound.c("inGround") == 1);
/* 395 */     this.doesArrowBelongToPlayer = nbttagcompound.n("player");
/*     */   }
/*     */ 
/*     */   public void b_(CallableItemName entityplayer)
/*     */   {
/* 401 */     if (this.q.I)
/*     */     {
/* 403 */       return;
/*     */     }
/* 405 */     if ((this.inGround) && (this.doesArrowBelongToPlayer) && (this.arrowShake <= 0) && (entityplayer.bn.a(new EnumToolMaterial(ItemHoe.n, 1))))
/*     */     {
/* 407 */       this.q.a(this, "random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 408 */       entityplayer.a(this, 1);
/* 409 */       preparePlayerToSpawn();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 416 */     return false;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public float R()
/*     */   {
/* 423 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public boolean ap()
/*     */   {
/* 429 */     return false;
/*     */   }
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMArrowOld
 * JD-Core Version:    0.6.2
 */