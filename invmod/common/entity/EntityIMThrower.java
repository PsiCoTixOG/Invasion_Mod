/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.CoordsInt;
/*     */ import invmod.common.util.IPosition;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMThrower extends EntityIMMob
/*     */ {
/*     */   private int throwTime;
/*     */   private int ammo;
/*     */   private int punchTimer;
/*     */   private boolean clearingPoint;
/*     */   private IPosition pointToClear;
/*     */   private INotifyTask clearPointNotifee;
/*     */ 
/*     */   public EntityIMThrower(ColorizerGrass world)
/*     */   {
/*  31 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMThrower(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  36 */     super(world, nexus);
/*  37 */     setBaseMoveSpeedStat(0.13F);
/*  38 */     this.attackStrength = 10;
/*  39 */     setMaxHealthAndHealth(50.0F);
/*  40 */     this.selfDamage = 0;
/*  41 */     this.maxSelfDamage = 0;
/*  42 */     this.ammo = 5;
/*  43 */     this.experienceValue = 20;
/*  44 */     this.clearingPoint = false;
/*  45 */     setBurnsInDay(true);
/*  46 */     setName("");
/*  47 */     setDestructiveness(2);
/*  48 */     setSize(1.8F, 1.95F);
/*  49 */     setAI();
/*     */   }
/*     */ 
/*     */   protected void setAI()
/*     */   {
/*  54 */     this.c = new EntityAIBase(this.q.C);
/*  55 */     this.c.a(0, new EntityAIThrowerKillEntity(this, CallableItemName.class, 55, 40.0F, 1.0F, 5));
/*  56 */     this.c.a(1, new EntityAIAttackNexus(this));
/*  57 */     this.c.a(2, new EntityAIRandomBoulder(this, 3));
/*  58 */     this.c.a(3, new EntityAIGoToNexus(this));
/*  59 */     this.c.a(6, new EntityAIWanderIM(this));
/*  60 */     this.c.a(7, new EntityAILeapAtTarget(this, CallableItemName.class, 8.0F));
/*  61 */     this.c.a(8, new EntityAILeapAtTarget(this, EntityIMCreeper.class, 12.0F));
/*  62 */     this.c.a(9, new EntityAILeapAtTarget(this, CallableItemName.class, 16.0F));
/*  63 */     this.c.a(9, new EntityAIPlay(this));
/*     */ 
/*  65 */     this.d = new EntityAIBase(this.q.C);
/*  66 */     this.d.a(2, new EntityAISimpleTarget(this, CallableItemName.class, 16.0F, true));
/*  67 */     this.d.a(3, new EntityAIDefendVillage(this, false));
/*     */   }
/*     */ 
/*     */   public void collideWithNearbyEntities()
/*     */   {
/*  73 */     super.collideWithNearbyEntities();
/*  74 */     this.throwTime -= 1;
/*  75 */     if (this.clearingPoint)
/*     */     {
/*  77 */       if (clearPoint())
/*     */       {
/*  79 */         this.clearingPoint = false;
/*  80 */         if (this.clearPointNotifee != null)
/*  81 */           this.clearPointNotifee.notifyTask(0);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean be()
/*     */   {
/*  89 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean canThrow()
/*     */   {
/*  94 */     return this.throwTime <= 0;
/*     */   }
/*     */ 
/*     */   public boolean onPathBlocked(Path path, INotifyTask notifee)
/*     */   {
/* 100 */     if (!path.isFinished())
/*     */     {
/* 102 */       PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
/* 103 */       this.clearingPoint = true;
/* 104 */       this.clearPointNotifee = notifee;
/* 105 */       this.pointToClear = new CoordsInt(node.xCoord, node.yCoord, node.zCoord);
/* 106 */       return true;
/*     */     }
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isBlockDestructible(int id)
/*     */   {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 139 */     return getName();
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 147 */     return "";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/* 153 */     return 3;
/*     */   }
/*     */ 
/*     */   public int getGender()
/*     */   {
/* 161 */     return 1;
/*     */   }
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 169 */     return "mob.zombie.say";
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/* 175 */     return "mob.zombie.hurt";
/*     */   }
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 181 */     return "mob.zombie.death";
/*     */   }
/*     */ 
/*     */   protected boolean clearPoint()
/*     */   {
/* 187 */     if (--this.punchTimer <= 0)
/*     */     {
/* 189 */       int x = this.pointToClear.getXCoord();
/* 190 */       int y = this.pointToClear.getYCoord();
/* 191 */       int z = this.pointToClear.getZCoord();
/* 192 */       int mobX = LongHashMapEntry.c(this.posX);
/* 193 */       int mobZ = LongHashMapEntry.c(this.posZ);
/* 194 */       int xOffsetR = 0;
/* 195 */       int zOffsetR = 0;
/* 196 */       int axisX = 0;
/* 197 */       int axisZ = 0;
/*     */ 
/* 199 */       float facing = this.rotationYaw % 360.0F;
/* 200 */       if (facing < 0.0F)
/*     */       {
/* 202 */         facing += 360.0F;
/*     */       }
/* 204 */       if ((facing >= 45.0F) && (facing < 135.0F))
/*     */       {
/* 206 */         zOffsetR = -1;
/* 207 */         axisX = -1;
/*     */       }
/* 209 */       else if ((facing >= 135.0F) && (facing < 225.0F))
/*     */       {
/* 211 */         xOffsetR = -1;
/* 212 */         axisZ = -1;
/*     */       }
/* 214 */       else if ((facing >= 225.0F) && (facing < 315.0F))
/*     */       {
/* 216 */         zOffsetR = -1;
/* 217 */         axisX = 1;
/*     */       }
/*     */       else
/*     */       {
/* 221 */         xOffsetR = -1;
/* 222 */         axisZ = 1;
/*     */       }
/*     */ 
/* 226 */       if (((BlockEndPortal.s[this.q.a(x, y, z)] != null) && (BlockEndPortal.s[this.q.a(x, y, z)].cU.isSolid())) || ((BlockEndPortal.s[this.q.a(x, y + 1, z)] != null) && (BlockEndPortal.s[this.q.a(x, y + 1, z)].cU.isSolid())) || ((BlockEndPortal.s[this.q.a(x + xOffsetR, y, z + zOffsetR)] != null) && (BlockEndPortal.s[this.q.a(x + xOffsetR, y, z + zOffsetR)].cU.isSolid())) || ((BlockEndPortal.s[this.q.a(x + xOffsetR, y + 1, z + zOffsetR)] != null) && (BlockEndPortal.s[this.q.a(x + xOffsetR, y + 1, z + zOffsetR)].cU.isSolid())))
/*     */       {
/* 231 */         tryDestroyBlock(x, y, z);
/* 232 */         tryDestroyBlock(x, y + 1, z);
/* 233 */         tryDestroyBlock(x + xOffsetR, y, z + zOffsetR);
/* 234 */         tryDestroyBlock(x + xOffsetR, y + 1, z + zOffsetR);
/* 235 */         this.punchTimer = 160;
/*     */       }
/* 238 */       else if (((BlockEndPortal.s[this.q.a(x - axisX, y + 1, z - axisZ)] != null) && (BlockEndPortal.s[this.q.a(x - axisX, y + 1, z - axisZ)].cU.isSolid())) || ((BlockEndPortal.s[this.q.a(x - axisX + xOffsetR, y + 1, z - axisZ + zOffsetR)] != null) && (BlockEndPortal.s[this.q.a(x - axisX + xOffsetR, y + 1, z - axisZ + zOffsetR)].cU.isSolid())))
/*     */       {
/* 241 */         tryDestroyBlock(x - axisX, y + 1, z - axisZ);
/* 242 */         tryDestroyBlock(x - axisX + xOffsetR, y + 1, z - axisZ + zOffsetR);
/* 243 */         this.punchTimer = 160;
/*     */       }
/* 245 */       else if (((BlockEndPortal.s[this.q.a(x - 2 * axisX, y + 1, z - 2 * axisZ)] != null) && (BlockEndPortal.s[this.q.a(x - 2 * axisX, y + 1, z - 2 * axisZ)].cU.isSolid())) || ((BlockEndPortal.s[this.q.a(x - 2 * axisX + xOffsetR, y + 1, z - 2 * axisZ + zOffsetR)] != null) && (BlockEndPortal.s[this.q.a(x - 2 * axisX + xOffsetR, y + 1, z - 2 * axisZ + zOffsetR)].cU.isSolid())))
/*     */       {
/* 248 */         tryDestroyBlock(x - 2 * axisX, y + 1, z - 2 * axisZ);
/* 249 */         tryDestroyBlock(x - 2 * axisX + xOffsetR, y + 1, z - 2 * axisZ + zOffsetR);
/* 250 */         this.punchTimer = 160;
/*     */       }
/*     */       else
/*     */       {
/* 255 */         return true;
/*     */       }
/*     */     }
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */   protected void tryDestroyBlock(int x, int y, int z)
/*     */   {
/* 264 */     int id = this.q.a(x, y, z);
/* 265 */     BlockEndPortal block = BlockEndPortal.s[id];
/* 266 */     if ((block != null) && ((isNexusBound()) || (this.j != null)))
/*     */     {
/* 268 */       if ((id == mod_Invasion.blockNexus.cF) && (this.attackTime == 0) && (x == this.targetNexus.getXCoord()) && (y == this.targetNexus.getYCoord()) && (z == this.targetNexus.getZCoord()))
/*     */       {
/* 270 */         this.targetNexus.attackNexus(5);
/* 271 */         this.attackTime = 60;
/*     */       }
/* 273 */       else if (id != mod_Invasion.blockNexus.cF)
/*     */       {
/* 276 */         int meta = this.q.h(x, y, z);
/* 277 */         this.q.c(x, y, z, 0);
/* 278 */         block.g(this.q, x, y, z, meta);
/* 279 */         block.c(this.q, x, y, z, meta, 0);
/*     */ 
/* 282 */         if (this.throttled == 0)
/*     */         {
/* 284 */           this.q.a(this, "random.explode", 1.0F, 0.4F);
/*     */ 
/* 286 */           this.throttled = 5;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void a(nm entity, float f)
/*     */   {
/* 295 */     if ((this.throwTime <= 0) && (this.ammo > 0) && (f > 4.0F))
/*     */     {
/* 297 */       this.throwTime = 120;
/* 298 */       if (f < 50.0F)
/*     */       {
/* 300 */         throwBoulder(entity.u, entity.v + entity.f() - 0.7D, entity.w, false);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 305 */       super.a(entity, f);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void throwBoulder(double entityX, double entityY, double entityZ, boolean forced)
/*     */   {
/* 311 */     float launchSpeed = 1.0F;
/* 312 */     double dX = entityX - this.posX;
/* 313 */     double dZ = entityZ - this.posZ;
/* 314 */     double dXY = LongHashMapEntry.a(dX * dX + dZ * dZ);
/*     */ 
/* 317 */     if ((0.025D * dXY / (launchSpeed * launchSpeed) <= 1.0D) && (this.attackTime == 0))
/*     */     {
/* 319 */       EntityIMBoulder entityBoulder = new EntityIMBoulder(this.q, this, launchSpeed);
/* 320 */       double dY = entityY - entityBoulder.v;
/* 321 */       double angle = 0.5D * Math.asin(0.025D * dXY / (launchSpeed * launchSpeed));
/* 322 */       dY += dXY * Math.tan(angle);
/* 323 */       entityBoulder.setBoulderHeading(dX, dY, dZ, launchSpeed, 0.05F);
/* 324 */       this.q.d(entityBoulder);
/*     */     }
/* 326 */     else if (forced)
/*     */     {
/* 328 */       EntityIMBoulder entityBoulder = new EntityIMBoulder(this.q, this, launchSpeed);
/* 329 */       double dY = entityY - entityBoulder.v;
/* 330 */       dY += dXY * Math.tan(0.7853981633974483D);
/* 331 */       entityBoulder.setBoulderHeading(dX, dY, dZ, launchSpeed, 0.05F);
/* 332 */       this.q.d(entityBoulder);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void throwBoulder(double entityX, double entityY, double entityZ)
/*     */   {
/* 338 */     this.throwTime = 40;
/* 339 */     float launchSpeed = 1.0F;
/* 340 */     double dX = entityX - this.posX;
/* 341 */     double dZ = entityZ - this.posZ;
/* 342 */     double dXY = LongHashMapEntry.a(dX * dX + dZ * dZ);
/* 343 */     double p = 0.025D * dXY / (launchSpeed * launchSpeed);
/*     */     double angle;
/*     */     double angle;
/* 346 */     if (p <= 1.0D)
/* 347 */       angle = 0.5D * p;
/*     */     else {
/* 349 */       angle = 0.7853981633974483D;
/*     */     }
/* 351 */     EntityIMBoulder entityBoulder = new EntityIMBoulder(this.q, this, launchSpeed);
/* 352 */     double dY = entityY - entityBoulder.v;
/* 353 */     dY += dXY * Math.tan(angle);
/* 354 */     entityBoulder.setBoulderHeading(dX, dY, dZ, launchSpeed, 0.05F);
/* 355 */     this.q.d(entityBoulder);
/*     */   }
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int bonus)
/*     */   {
/* 361 */     super.dropFewItems(flag, bonus);
/* 362 */     a(new EnumToolMaterial(mod_Invasion.itemRemnants, 1, 1), 0.0F);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMThrower
 * JD-Core Version:    0.6.2
 */