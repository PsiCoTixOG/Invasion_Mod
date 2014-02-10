/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.TileEntityNexus;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMBoulder extends nm
/*     */ {
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private int inTile;
/*     */   private int inData;
/*     */   private boolean inGround;
/*     */   private int life;
/*     */   public boolean doesArrowBelongToPlayer;
/*     */   public int arrowShake;
/*     */   public EntityLeashKnot shootingEntity;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   public boolean arrowCritical;
/*     */ 
/*     */   public EntityIMBoulder(ColorizerGrass world)
/*     */   {
/*  42 */     super(world);
/*  43 */     this.xTile = -1;
/*  44 */     this.yTile = -1;
/*  45 */     this.zTile = -1;
/*  46 */     this.inTile = 0;
/*  47 */     this.inData = 0;
/*  48 */     this.life = 60;
/*  49 */     this.inGround = false;
/*  50 */     this.doesArrowBelongToPlayer = false;
/*  51 */     this.arrowShake = 0;
/*  52 */     this.ticksInAir = 0;
/*  53 */     this.arrowCritical = false;
/*  54 */     a(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   public EntityIMBoulder(ColorizerGrass world, double d, double d1, double d2)
/*     */   {
/*  59 */     super(world);
/*  60 */     this.xTile = -1;
/*  61 */     this.yTile = -1;
/*  62 */     this.zTile = -1;
/*  63 */     this.inTile = 0;
/*  64 */     this.inData = 0;
/*  65 */     this.life = 60;
/*  66 */     this.inGround = false;
/*  67 */     this.doesArrowBelongToPlayer = false;
/*  68 */     this.arrowShake = 0;
/*  69 */     this.ticksInAir = 0;
/*  70 */     this.arrowCritical = false;
/*  71 */     a(0.5F, 0.5F);
/*  72 */     b(d, d1, d2);
/*  73 */     this.N = 0.0F;
/*     */   }
/*     */ 
/*     */   public EntityIMBoulder(ColorizerGrass world, EntityLeashKnot entityliving, float f)
/*     */   {
/*  78 */     super(world);
/*  79 */     this.xTile = -1;
/*  80 */     this.yTile = -1;
/*  81 */     this.zTile = -1;
/*  82 */     this.inTile = 0;
/*  83 */     this.inData = 0;
/*  84 */     this.life = 60;
/*  85 */     this.inGround = false;
/*  86 */     this.doesArrowBelongToPlayer = false;
/*  87 */     this.arrowShake = 0;
/*  88 */     this.ticksInAir = 0;
/*  89 */     this.arrowCritical = false;
/*  90 */     this.shootingEntity = entityliving;
/*  91 */     this.doesArrowBelongToPlayer = (entityliving instanceof CallableItemName);
/*  92 */     a(0.5F, 0.5F);
/*  93 */     b(entityliving.posX, entityliving.posY + entityliving.getEyeHeight(), entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
/*  94 */     this.u -= LongHashMapEntry.b(this.A / 180.0F * 3.141593F) * 0.16F;
/*  95 */     this.v -= 0.1D;
/*  96 */     this.w -= LongHashMapEntry.a(this.A / 180.0F * 3.141593F) * 0.16F;
/*  97 */     b(this.u, this.v, this.w);
/*  98 */     this.N = 0.0F;
/*  99 */     this.x = (-LongHashMapEntry.a(this.A / 180.0F * 3.141593F) * LongHashMapEntry.b(this.B / 180.0F * 3.141593F));
/* 100 */     this.z = (LongHashMapEntry.b(this.A / 180.0F * 3.141593F) * LongHashMapEntry.b(this.B / 180.0F * 3.141593F));
/* 101 */     this.y = (-LongHashMapEntry.a(this.B / 180.0F * 3.141593F));
/* 102 */     setBoulderHeading(this.x, this.y, this.z, f, 1.0F);
/*     */   }
/*     */ 
/*     */   protected void a()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setBoulderHeading(double x, double y, double z, float speed, float variance)
/*     */   {
/* 113 */     float distance = LongHashMapEntry.a(x * x + y * y + z * z);
/* 114 */     x /= distance;
/* 115 */     y /= distance;
/* 116 */     z /= distance;
/*     */ 
/* 118 */     x += this.ab.nextGaussian() * variance;
/* 119 */     y += this.ab.nextGaussian() * variance;
/* 120 */     z += this.ab.nextGaussian() * variance;
/* 121 */     x *= speed;
/* 122 */     y *= speed;
/* 123 */     z *= speed;
/* 124 */     this.x = x;
/* 125 */     this.y = y;
/* 126 */     this.z = z;
/* 127 */     float xzDistance = LongHashMapEntry.a(x * x + z * z);
/* 128 */     this.C = (this.A = (float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D));
/* 129 */     this.D = (this.B = (float)(Math.atan2(y, xzDistance) * 180.0D / 3.141592653589793D));
/* 130 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */   public void h(double d, double d1, double d2)
/*     */   {
/* 136 */     this.x = d;
/* 137 */     this.y = d1;
/* 138 */     this.z = d2;
/* 139 */     if ((this.D == 0.0F) && (this.C == 0.0F))
/*     */     {
/* 141 */       float f = LongHashMapEntry.a(d * d + d2 * d2);
/* 142 */       this.C = (this.A = (float)(Math.atan2(d, d2) * 180.0D / 3.141592741012573D));
/* 143 */       this.D = (this.B = (float)(Math.atan2(d1, f) * 180.0D / 3.141592741012573D));
/* 144 */       this.D = this.B;
/* 145 */       this.C = this.A;
/* 146 */       b(this.u, this.v, this.w, this.A, this.B);
/* 147 */       this.ticksInGround = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void l_()
/*     */   {
/* 154 */     super.l_();
/* 155 */     if ((this.D == 0.0F) && (this.C == 0.0F))
/*     */     {
/* 157 */       float f = LongHashMapEntry.a(this.x * this.x + this.z * this.z);
/* 158 */       this.C = (this.A = (float)(Math.atan2(this.x, this.z) * 180.0D / 3.141592653589793D));
/* 159 */       this.D = (this.B = (float)(Math.atan2(this.y, f) * 180.0D / 3.141592653589793D));
/*     */     }
/*     */ 
/* 163 */     int i = this.q.a(this.xTile, this.yTile, this.zTile);
/* 164 */     if (i > 0)
/*     */     {
/* 166 */       BlockEndPortal.s[i].a(this.q, this.xTile, this.yTile, this.zTile);
/* 167 */       BlockPistonExtension axisalignedbb = BlockEndPortal.s[i].b(this.q, this.xTile, this.yTile, this.zTile);
/* 168 */       if ((axisalignedbb != null) && (axisalignedbb.a(AABBPool.a(this.u, this.v, this.w))))
/*     */       {
/* 170 */         this.inGround = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 175 */     if ((this.inGround) || (this.life-- <= 0))
/*     */     {
/* 177 */       w();
/* 178 */       return;
/*     */     }
/*     */ 
/* 181 */     this.ticksInAir += 1;
/*     */ 
/* 184 */     AABBPool vec3d = AABBPool.a(this.u, this.v, this.w);
/* 185 */     AABBPool vec3d1 = AABBPool.a(this.u + this.x, this.v + this.y, this.w + this.z);
/* 186 */     AxisAlignedBB movingobjectposition = this.q.a(vec3d, vec3d1, false, true);
/* 187 */     vec3d = AABBPool.a(this.u, this.v, this.w);
/* 188 */     vec3d1 = AABBPool.a(this.u + this.x, this.v + this.y, this.w + this.z);
/* 189 */     if (movingobjectposition != null)
/*     */     {
/* 191 */       vec3d1 = AABBPool.a(movingobjectposition.maxZ.listAABB, movingobjectposition.maxZ.nextPoolIndex, movingobjectposition.maxZ.maxPoolIndex);
/*     */     }
/*     */ 
/* 195 */     nm entity = null;
/* 196 */     List list = this.q.b(this, this.E.a(this.x, this.y, this.z).b(1.0D, 1.0D, 1.0D));
/* 197 */     double d = 0.0D;
/* 198 */     for (int l = 0; l < list.size(); l++)
/*     */     {
/* 200 */       nm entity1 = (nm)list.get(l);
/* 201 */       if ((entity1.K()) && ((entity1 != this.shootingEntity) || (this.ticksInAir >= 5)))
/*     */       {
/* 205 */         float f5 = 0.3F;
/* 206 */         BlockPistonExtension axisalignedbb1 = entity1.E.b(f5, f5, f5);
/* 207 */         AxisAlignedBB movingobjectposition1 = axisalignedbb1.a(vec3d, vec3d1);
/* 208 */         if (movingobjectposition1 != null)
/*     */         {
/* 212 */           double d1 = vec3d.d(movingobjectposition1.maxZ);
/* 213 */           if ((d1 < d) || (d == 0.0D))
/*     */           {
/* 215 */             entity = entity1;
/* 216 */             d = d1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 221 */     if (entity != null)
/*     */     {
/* 223 */       movingobjectposition = new AxisAlignedBB(entity);
/*     */     }
/* 225 */     if (movingobjectposition != null)
/*     */     {
/* 228 */       if (movingobjectposition.theAABBLocalPool != null)
/*     */       {
/* 231 */         int damage = (int)(Math.max(this.ticksInAir / 20.0F, 1.0F) * 6.0F);
/* 232 */         if (damage > 14) damage = 14;
/* 233 */         if (movingobjectposition.theAABBLocalPool.a(CombatTracker.a(this.shootingEntity), damage))
/*     */         {
/* 235 */           if ((movingobjectposition.theAABBLocalPool instanceof EntityLivingBase))
/*     */           {
/* 237 */             if (!this.q.I)
/*     */             {
/* 239 */               EntityLivingBase entityLiving = (EntityLivingBase)movingobjectposition.theAABBLocalPool;
/* 240 */               entityLiving.setArrowCountInEntity(entityLiving.aT() + 1);
/*     */             }
/*     */           }
/* 243 */           this.q.a(this, "random.explode", 1.0F, 0.9F / (this.ab.nextFloat() * 0.2F + 0.9F));
/* 244 */           w();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 249 */         this.xTile = movingobjectposition.minY;
/* 250 */         this.yTile = movingobjectposition.minZ;
/* 251 */         this.zTile = movingobjectposition.maxX;
/* 252 */         this.inTile = this.q.a(this.xTile, this.yTile, this.zTile);
/* 253 */         this.inData = this.q.h(this.xTile, this.yTile, this.zTile);
/* 254 */         this.x = ((float)(movingobjectposition.maxZ.listAABB - this.u));
/* 255 */         this.y = ((float)(movingobjectposition.maxZ.nextPoolIndex - this.v));
/* 256 */         this.z = ((float)(movingobjectposition.maxZ.maxPoolIndex - this.w));
/* 257 */         float f2 = LongHashMapEntry.a(this.x * this.x + this.y * this.y + this.z * this.z);
/* 258 */         this.u -= this.x / f2 * 0.05D;
/* 259 */         this.v -= this.y / f2 * 0.05D;
/* 260 */         this.w -= this.z / f2 * 0.05D;
/* 261 */         this.q.a(this, "random.explode", 1.0F, 0.9F / (this.ab.nextFloat() * 0.2F + 0.9F));
/* 262 */         this.inGround = true;
/* 263 */         this.arrowCritical = false;
/*     */ 
/* 266 */         int id = this.q.a(this.xTile, this.yTile, this.zTile);
/* 267 */         BlockEndPortal block = BlockEndPortal.s[id];
/* 268 */         if (id == mod_Invasion.blockNexus.cF)
/*     */         {
/* 270 */           TileEntityNexus tileEntityNexus = (TileEntityNexus)this.q.r(this.xTile, this.yTile, this.zTile);
/* 271 */           if (tileEntityNexus != null)
/*     */           {
/* 273 */             tileEntityNexus.attackNexus(2);
/*     */           }
/*     */         }
/* 276 */         else if (id != BlockEndPortal.E.blockID)
/*     */         {
/* 278 */           if ((block != null) && (id != mod_Invasion.blockNexus.cF) && (id != 54))
/*     */           {
/* 280 */             if ((EntityIMLiving.getBlockSpecial(id) == BlockSpecial.DEFLECTION_1) && (this.ab.nextInt(2) == 0))
/*     */             {
/* 282 */               w();
/* 283 */               return;
/*     */             }
/* 285 */             int meta = this.q.h(this.xTile, this.yTile, this.zTile);
/* 286 */             this.q.c(this.xTile, this.yTile, this.zTile, 0);
/* 287 */             block.g(this.q, this.xTile, this.yTile, this.zTile, meta);
/* 288 */             block.c(this.q, this.xTile, this.yTile, this.zTile, meta, 0);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 295 */     if (this.arrowCritical)
/*     */     {
/* 297 */       for (int i1 = 0; i1 < 4; i1++)
/*     */       {
/* 299 */         this.q.a("crit", this.u + this.x * i1 / 4.0D, this.v + this.y * i1 / 4.0D, this.w + this.z * i1 / 4.0D, -this.x, -this.y + 0.2D, -this.z);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 305 */     this.u += this.x;
/* 306 */     this.v += this.y;
/* 307 */     this.w += this.z;
/*     */ 
/* 310 */     float xyVelocity = LongHashMapEntry.a(this.x * this.x + this.z * this.z);
/* 311 */     this.A = ((float)(Math.atan2(this.x, this.z) * 180.0D / 3.141592653589793D));
/* 312 */     for (this.B = ((float)(Math.atan2(this.y, xyVelocity) * 180.0D / 3.141592653589793D)); this.B - this.D < -180.0F; this.D -= 360.0F);
/* 313 */     while (this.B - this.D >= 180.0F) this.D += 360.0F;
/* 314 */     while (this.A - this.C < -180.0F) this.C -= 360.0F;
/* 315 */     while (this.A - this.C >= 180.0F) this.C += 360.0F;
/* 316 */     this.B = (this.D + (this.B - this.D) * 0.2F);
/* 317 */     this.A = (this.C + (this.A - this.C) * 0.2F);
/* 318 */     float airResistance = 1.0F;
/* 319 */     float gravityAcel = 0.025F;
/* 320 */     if (G())
/*     */     {
/* 322 */       for (int k1 = 0; k1 < 4; k1++)
/*     */       {
/* 324 */         float f7 = 0.25F;
/* 325 */         this.q.a("bubble", this.u - this.x * f7, this.v - this.y * f7, this.w - this.z * f7, this.x, this.y, this.z);
/*     */       }
/*     */ 
/* 328 */       airResistance = 0.8F;
/*     */     }
/* 330 */     this.x *= airResistance;
/* 331 */     this.y *= airResistance;
/* 332 */     this.z *= airResistance;
/* 333 */     this.y -= gravityAcel;
/* 334 */     b(this.u, this.v, this.w);
/*     */   }
/*     */ 
/*     */   public void b(NBTTagByte nbttagcompound)
/*     */   {
/* 340 */     nbttagcompound.a("xTile", (short)this.xTile);
/* 341 */     nbttagcompound.a("yTile", (short)this.yTile);
/* 342 */     nbttagcompound.a("zTile", (short)this.zTile);
/* 343 */     nbttagcompound.a("inTile", (byte)this.inTile);
/* 344 */     nbttagcompound.a("inData", (byte)this.inData);
/* 345 */     nbttagcompound.a("shake", (byte)this.arrowShake);
/* 346 */     nbttagcompound.a("inGround", (byte)(this.inGround ? 1 : 0));
/* 347 */     nbttagcompound.a("player", this.doesArrowBelongToPlayer);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagByte nbttagcompound)
/*     */   {
/* 353 */     this.xTile = nbttagcompound.d("xTile");
/* 354 */     this.yTile = nbttagcompound.d("yTile");
/* 355 */     this.zTile = nbttagcompound.d("zTile");
/* 356 */     this.inTile = (nbttagcompound.c("inTile") & 0xFF);
/* 357 */     this.inData = (nbttagcompound.c("inData") & 0xFF);
/* 358 */     this.arrowShake = (nbttagcompound.c("shake") & 0xFF);
/* 359 */     this.inGround = (nbttagcompound.c("inGround") == 1);
/* 360 */     this.doesArrowBelongToPlayer = nbttagcompound.n("player");
/*     */   }
/*     */ 
/*     */   public void b_(CallableItemName entityplayer)
/*     */   {
/* 366 */     if (this.q.I);
/*     */   }
/*     */ 
/*     */   public float R()
/*     */   {
/* 375 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public int getFlightTime()
/*     */   {
/* 380 */     return this.ticksInAir;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMBoulder
 * JD-Core Version:    0.6.2
 */