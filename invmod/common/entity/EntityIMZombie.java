/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IBlockAccessExtended;
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.IPosition;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public class EntityIMZombie extends EntityIMMob implements ICanDig
/*     */ {
/*     */   private static final int META_CHANGED = 29;
/*     */   private static final int META_TIER = 30;
/*     */   private static final int META_TEXTURE = 31;
/*     */   private static final int META_FLAVOUR = 28;
/*     */   private static final int META_SWINGING = 27;
/*     */   private TerrainModifier terrainModifier;
/*     */   private TerrainDigger terrainDigger;
/*     */   private byte metaChanged;
/*     */   private int tier;
/*     */   private int flavour;
/*     */   private EnumToolMaterial defaultHeldItem;
/*     */   private ItemHoe itemDrop;
/*     */   private float dropChance;
/*     */   private int swingTimer;
/*     */ 
/*     */   public EntityIMZombie(ColorizerGrass world)
/*     */   {
/*  49 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMZombie(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  54 */     super(world, nexus);
/*  55 */     this.terrainModifier = new TerrainModifier(this, 2.0F);
/*  56 */     this.terrainDigger = new TerrainDigger(this, this.terrainModifier, 1.0F);
/*  57 */     this.dropChance = 0.0F;
/*  58 */     if (world.I)
/*  59 */       this.metaChanged = 1;
/*     */     else
/*  61 */       this.metaChanged = 0;
/*  62 */     this.tier = 1;
/*  63 */     this.flavour = 0;
/*     */ 
/*  65 */     EntityCreature dataWatcher = u();
/*  66 */     dataWatcher.a(29, Byte.valueOf(this.metaChanged));
/*  67 */     dataWatcher.a(30, Integer.valueOf(this.tier));
/*  68 */     dataWatcher.a(31, Integer.valueOf(0));
/*  69 */     dataWatcher.a(28, Integer.valueOf(this.flavour));
/*  70 */     dataWatcher.a(27, Byte.valueOf((byte)0));
/*     */ 
/*  72 */     setAttributes(this.tier, this.flavour);
/*  73 */     setAI();
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  79 */     super.onUpdate();
/*  80 */     if (this.metaChanged != u().a(29))
/*     */     {
/*  82 */       EntityCreature data = u();
/*  83 */       this.metaChanged = data.a(29);
/*  84 */       setTexture(data.c(31));
/*     */ 
/*  86 */       if (this.tier != data.c(30))
/*  87 */         setTier(data.c(30));
/*  88 */       if (this.flavour != data.c(28)) {
/*  89 */         setFlavour(data.c(28));
/*     */       }
/*     */     }
/*  92 */     if ((!this.q.I) && (this.flammability >= 20) && (ae()))
/*  93 */       doFireball();
/*     */   }
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/*  99 */     super.onLivingUpdate();
/* 100 */     updateAnimation();
/* 101 */     updateSound();
/*     */   }
/*     */ 
/*     */   public void onPathSet()
/*     */   {
/* 107 */     this.terrainModifier.cancelTask();
/*     */   }
/*     */ 
/*     */   protected void setAI()
/*     */   {
/* 112 */     this.c = new EntityAIBase(this.q.C);
/* 113 */     this.c.a(0, new EntityAIKillEntity(this, CallableItemName.class, 40));
/* 114 */     this.c.a(1, new EntityAIAttackNexus(this));
/* 115 */     this.c.a(2, new EntityAIWaitForEngy(this, 4.0F, true));
/* 116 */     this.c.a(3, new EntityAIKillEntity(this, EntityLivingBase.class, 40));
/* 117 */     this.c.a(4, new EntityAIGoToNexus(this));
/* 118 */     this.c.a(6, new EntityAIWanderIM(this));
/* 119 */     this.c.a(7, new EntityAILeapAtTarget(this, CallableItemName.class, 8.0F));
/* 120 */     this.c.a(8, new EntityAILeapAtTarget(this, EntityIMCreeper.class, 12.0F));
/* 121 */     this.c.a(8, new EntityAIPlay(this));
/*     */ 
/* 123 */     this.d = new EntityAIBase(this.q.C);
/* 124 */     this.d.a(0, new EntityAITargetRetaliate(this, EntityLivingBase.class, 12.0F));
/* 125 */     this.d.a(2, new EntityAISimpleTarget(this, CallableItemName.class, 12.0F, true));
/* 126 */     this.d.a(5, new EntityAIDefendVillage(this, false));
/*     */ 
/* 128 */     if (this.tier == 3)
/*     */     {
/* 130 */       this.c.a(3, new EntityAIStoop(this));
/* 131 */       this.c.a(2, new EntityAISprint(this));
/*     */     }
/*     */     else
/*     */     {
/* 135 */       this.c.a(0, new EntityAIRallyBehindEntity(this, EntityIMCreeper.class, 4.0F));
/* 136 */       this.d.a(1, new EntityAISimpleTarget(this, CallableItemName.class, 6.0F, true));
/* 137 */       this.d.a(3, new EntityAILeaderTarget(this, EntityIMCreeper.class, 10.0F, true));
/* 138 */       this.d.a(4, new EntityAITargetOnNoNexusPath(this, EntityIMPigEngy.class, 3.5F));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTier(int tier)
/*     */   {
/* 150 */     this.tier = tier;
/* 151 */     u().b(30, Integer.valueOf(tier));
/* 152 */     setAttributes(tier, this.flavour);
/* 153 */     setAI();
/*     */ 
/* 156 */     if (u().c(31) == 0)
/*     */     {
/* 158 */       if (tier == 1)
/*     */       {
/* 160 */         int r = this.rand.nextInt(2);
/* 161 */         if (r == 0)
/* 162 */           setTexture(0);
/* 163 */         else if (r == 1)
/* 164 */           setTexture(1);
/*     */       }
/* 166 */       else if (tier == 2)
/*     */       {
/* 168 */         if (this.flavour == 2)
/*     */         {
/* 170 */           setTexture(5);
/*     */         }
/* 172 */         else if (this.flavour == 3)
/*     */         {
/* 174 */           setTexture(3);
/*     */         }
/*     */         else
/*     */         {
/* 178 */           int r = this.rand.nextInt(2);
/* 179 */           if (r == 0)
/* 180 */             setTexture(2);
/* 181 */           else if (r == 1)
/* 182 */             setTexture(4);
/*     */         }
/*     */       }
/* 185 */       else if (tier == 3)
/*     */       {
/* 187 */         setTexture(6);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTexture(int textureId)
/*     */   {
/* 194 */     u().b(31, Integer.valueOf(textureId));
/*     */   }
/*     */ 
/*     */   public void setFlavour(int flavour)
/*     */   {
/* 199 */     u().b(28, Integer.valueOf(flavour));
/* 200 */     this.flavour = flavour;
/* 201 */     setAttributes(this.tier, flavour);
/*     */   }
/*     */ 
/*     */   public int getTextureId()
/*     */   {
/* 206 */     return u().c(31);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 212 */     return "EntityIMZombie#" + this.tier + "-" + u().c(31) + "-" + this.flavour;
/*     */   }
/*     */ 
/*     */   public EnumGameType getTerrain()
/*     */   {
/* 218 */     return this.q;
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial aY()
/*     */   {
/* 224 */     return this.defaultHeldItem;
/*     */   }
/*     */ 
/*     */   public boolean avoidsBlock(int id)
/*     */   {
/* 230 */     if ((this.isImmuneToFire) && ((id == 51) || (id == 10) || (id == 11))) {
/* 231 */       return false;
/*     */     }
/* 233 */     return super.avoidsBlock(id);
/*     */   }
/*     */ 
/*     */   public float getBlockRemovalCost(int x, int y, int z)
/*     */   {
/* 239 */     return getBlockStrength(x, y, z) * 20.0F;
/*     */   }
/*     */ 
/*     */   public boolean canClearBlock(int x, int y, int z)
/*     */   {
/* 245 */     int id = this.q.a(x, y, z);
/* 246 */     return (id == 0) || (isBlockDestructible(this.q, x, y, z, id));
/*     */   }
/*     */ 
/*     */   protected boolean onPathBlocked(Path path, INotifyTask notifee)
/*     */   {
/* 252 */     if ((!path.isFinished()) && ((isNexusBound()) || (m() != null)))
/*     */     {
/* 255 */       if ((path.getFinalPathPoint().distanceTo(path.getIntendedTarget()) > 2.2D) && (path.getCurrentPathIndex() + 2 >= path.getCurrentPathLength() / 2)) {
/* 256 */         return false;
/*     */       }
/* 258 */       PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
/* 259 */       if (this.terrainDigger.askClearPosition(node.xCoord, node.yCoord, node.zCoord, notifee, 1.0F))
/* 260 */         return true;
/*     */     }
/* 262 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isBigRenderTempHack()
/*     */   {
/* 267 */     return this.tier == 3;
/*     */   }
/*     */ 
/*     */   public boolean m(nm entity)
/*     */   {
/* 273 */     return (this.tier == 3) && (isSneaking()) ? chargeAttack(entity) : super.m(entity);
/*     */   }
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 279 */     return this.tier != 3;
/*     */   }
/*     */ 
/*     */   public void a(nm par1Entity, float par2, double par3, double par5)
/*     */   {
/* 285 */     if (this.tier == 3) {
/* 286 */       return;
/*     */     }
/* 288 */     this.isAirBorne = true;
/* 289 */     float f = LongHashMapEntry.a(par3 * par3 + par5 * par5);
/* 290 */     float f1 = 0.4F;
/* 291 */     this.motionX /= 2.0D;
/* 292 */     this.motionY /= 2.0D;
/* 293 */     this.motionZ /= 2.0D;
/* 294 */     this.motionX -= par3 / f * f1;
/* 295 */     this.motionY += f1;
/* 296 */     this.motionZ -= par5 / f * f1;
/*     */ 
/* 298 */     if (this.motionY > 0.4000000059604645D)
/*     */     {
/* 300 */       this.motionY = 0.4000000059604645D;
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*     */   {
/* 311 */     if ((this.tier == 2) && (this.flavour == 2) && (node.action == PathAction.SWIM))
/*     */     {
/* 313 */       float multiplier = 1.0F;
/* 314 */       if ((terrainMap instanceof IBlockAccessExtended))
/*     */       {
/* 316 */         int mobDensity = ((IBlockAccessExtended)terrainMap).getLayeredData(node.xCoord, node.yCoord, node.zCoord) & 0x7;
/* 317 */         multiplier += mobDensity * 3;
/*     */       }
/*     */ 
/* 320 */       if ((node.yCoord > prevNode.yCoord) && (getCollide(terrainMap, node.xCoord, node.yCoord, node.zCoord) == 2))
/*     */       {
/* 322 */         multiplier += 2.0F;
/*     */       }
/*     */ 
/* 325 */       return prevNode.distanceTo(node) * 1.2F * multiplier;
/*     */     }
/*     */ 
/* 329 */     return super.getBlockPathCost(prevNode, node, terrainMap);
/*     */   }
/*     */ 
/*     */   public boolean az()
/*     */   {
/* 336 */     return (this.tier == 2) && (this.flavour == 2);
/*     */   }
/*     */ 
/*     */   public boolean isBlockDestructible(EnumGameType terrainMap, int x, int y, int z, int id)
/*     */   {
/* 342 */     if (getDestructiveness() == 0) {
/* 343 */       return false;
/*     */     }
/*     */ 
/* 346 */     IPosition pos = getCurrentTargetPos();
/* 347 */     int dY = pos.getYCoord() - y;
/* 348 */     boolean isTooSteep = false;
/* 349 */     if (dY > 0)
/*     */     {
/* 351 */       dY += 8;
/* 352 */       int dX = pos.getXCoord() - x;
/* 353 */       int dZ = pos.getZCoord() - z;
/* 354 */       double dXZ = Math.sqrt(dX * dX + dZ * dZ) + 1.E-005D;
/* 355 */       isTooSteep = dY / dXZ > 2.144D;
/*     */     }
/*     */ 
/* 358 */     return (!isTooSteep) && (isBlockTypeDestructible(id));
/*     */   }
/*     */ 
/*     */   public boolean isBlockTypeDestructible(int id)
/*     */   {
/* 364 */     if ((id == 0) || (id == BlockEndPortal.E.blockID) || (id == BlockEndPortal.aK.blockID))
/*     */     {
/* 366 */       return false;
/*     */     }
/* 368 */     if ((id == BlockEndPortal.aQ.blockID) || (id == BlockEndPortal.aJ.blockID) || (id == BlockEndPortal.bp.blockID))
/*     */     {
/* 370 */       return true;
/*     */     }
/* 372 */     if (BlockEndPortal.s[id].cU.isSolid())
/*     */     {
/* 374 */       return true;
/*     */     }
/*     */ 
/* 378 */     return false;
/*     */   }
/*     */ 
/*     */   public void onFollowingEntity(nm entity)
/*     */   {
/* 385 */     if (entity == null)
/*     */     {
/* 387 */       setDestructiveness(1);
/*     */     }
/* 389 */     else if (((entity instanceof EntityIMPigEngy)) || ((entity instanceof EntityIMCreeper)))
/*     */     {
/* 391 */       setDestructiveness(0);
/*     */     }
/*     */     else
/*     */     {
/* 395 */       setDestructiveness(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float scaleAmount()
/*     */   {
/* 401 */     if (this.tier == 2)
/* 402 */       return 1.12F;
/* 403 */     if (this.tier == 3) {
/* 404 */       return 1.21F;
/*     */     }
/* 406 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 415 */     return "Zombie";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/* 421 */     return this.tier < 3 ? 2 : 3;
/*     */   }
/*     */ 
/*     */   public void b(NBTTagByte nbttagcompound)
/*     */   {
/* 429 */     nbttagcompound.a("tier", this.tier);
/* 430 */     nbttagcompound.a("flavour", this.flavour);
/* 431 */     nbttagcompound.a("textureId", this.ah.c(31));
/* 432 */     super.b(nbttagcompound);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagByte nbttagcompound)
/*     */   {
/* 438 */     setTexture(nbttagcompound.e("textureId"));
/* 439 */     this.flavour = nbttagcompound.e("flavour");
/* 440 */     this.tier = nbttagcompound.e("tier");
/* 441 */     if (this.tier == 0) {
/* 442 */       this.tier = 1;
/*     */     }
/* 444 */     setFlavour(this.flavour);
/* 445 */     setTier(this.tier);
/* 446 */     super.a(nbttagcompound);
/*     */   }
/*     */ 
/*     */   protected void sunlightDamageTick()
/*     */   {
/* 452 */     if ((this.tier == 2) && (this.flavour == 2))
/* 453 */       d(CombatTracker.j, 3.0F);
/*     */     else
/* 455 */       setFire(8);
/*     */   }
/*     */ 
/*     */   protected void updateAnimation()
/*     */   {
/* 460 */     if ((!this.q.I) && (this.terrainModifier.isBusy())) {
/* 461 */       setSwinging(true);
/*     */     }
/* 463 */     int swingSpeed = getSwingSpeed();
/* 464 */     if (isSwinging())
/*     */     {
/* 466 */       this.swingTimer += 1;
/* 467 */       if (this.swingTimer >= swingSpeed)
/*     */       {
/* 469 */         this.swingTimer = 0;
/* 470 */         setSwinging(false);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 475 */       this.swingTimer = 0;
/*     */     }
/*     */ 
/* 478 */     this.swingProgress = (this.swingTimer / swingSpeed);
/*     */   }
/*     */ 
/*     */   protected boolean isSwinging()
/*     */   {
/* 483 */     return u().a(27) != 0;
/*     */   }
/*     */ 
/*     */   protected void setSwinging(boolean flag)
/*     */   {
/* 488 */     u().b(27, Byte.valueOf((byte)(flag == true ? 1 : 0)));
/*     */   }
/*     */ 
/*     */   protected void updateSound()
/*     */   {
/* 493 */     if (this.terrainModifier.isBusy())
/*     */     {
/* 495 */       if (--this.throttled2 <= 0)
/*     */       {
/* 498 */         this.q.a(this, "invmod:scrape", 0.85F, 1.0F / (this.rand.nextFloat() * 0.5F + 1.0F));
/* 499 */         this.throttled2 = (45 + this.rand.nextInt(20));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getSwingSpeed()
/*     */   {
/* 506 */     return 10;
/*     */   }
/*     */ 
/*     */   protected boolean chargeAttack(nm entity)
/*     */   {
/* 511 */     int knockback = 4;
/* 512 */     entity.a(CombatTracker.a(this), this.attackStrength + 3);
/* 513 */     entity.g(-LongHashMapEntry.a(this.rotationYaw * 3.141593F / 180.0F) * knockback * 0.5F, 0.4D, LongHashMapEntry.b(this.rotationYaw * 3.141593F / 180.0F) * knockback * 0.5F);
/* 514 */     setSprinting(false);
/* 515 */     this.q.a(entity, "damage.fallbig", 1.0F, 1.0F);
/* 516 */     return true;
/*     */   }
/*     */ 
/*     */   protected void bh()
/*     */   {
/* 522 */     super.bh();
/* 523 */     this.terrainModifier.onUpdate();
/*     */   }
/*     */ 
/*     */   protected ITerrainDig getTerrainDig()
/*     */   {
/* 528 */     return this.terrainDigger;
/*     */   }
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 534 */     if (this.tier == 3)
/*     */     {
/* 536 */       return this.rand.nextInt(3) == 0 ? "invmod:bigzombie" : null;
/*     */     }
/*     */ 
/* 540 */     return "mob.zombie.say";
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/* 547 */     return "mob.zombie.hurt";
/*     */   }
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 553 */     return "mob.zombie.death";
/*     */   }
/*     */ 
/*     */   protected int getDropItemId()
/*     */   {
/* 559 */     return ItemHoe.bo.itemID;
/*     */   }
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int bonus)
/*     */   {
/* 565 */     super.dropFewItems(flag, bonus);
/* 566 */     if (this.rand.nextFloat() < 0.35F)
/*     */     {
/* 568 */       b(ItemHoe.bo.itemID, 1);
/*     */     }
/*     */ 
/* 571 */     if ((this.itemDrop != null) && (this.rand.nextFloat() < this.dropChance))
/*     */     {
/* 573 */       a(new EnumToolMaterial(this.itemDrop, 1, 0), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setAttributes(int tier, int flavour)
/*     */   {
/* 579 */     if (tier == 1)
/*     */     {
/* 581 */       if (flavour == 0)
/*     */       {
/* 583 */         setName("Zombie");
/* 584 */         setGender(1);
/* 585 */         setBaseMoveSpeedStat(0.19F);
/* 586 */         this.attackStrength = 4;
/* 587 */         setMaxHealthAndHealth(18.0F);
/* 588 */         this.selfDamage = 3;
/* 589 */         this.maxSelfDamage = 6;
/* 590 */         this.maxDestructiveness = 2;
/* 591 */         this.flammability = 3;
/*     */ 
/* 593 */         setDestructiveness(2);
/*     */       }
/* 595 */       else if (flavour == 1)
/*     */       {
/* 597 */         setName("Zombie");
/* 598 */         setGender(1);
/* 599 */         setBaseMoveSpeedStat(0.19F);
/* 600 */         this.attackStrength = 6;
/* 601 */         setMaxHealthAndHealth(18.0F);
/* 602 */         this.selfDamage = 3;
/* 603 */         this.maxSelfDamage = 6;
/* 604 */         this.maxDestructiveness = 0;
/* 605 */         this.flammability = 3;
/* 606 */         this.defaultHeldItem = new EnumToolMaterial(ItemHoe.t, 1);
/* 607 */         this.itemDrop = ItemHoe.t;
/* 608 */         this.dropChance = 0.2F;
/*     */ 
/* 610 */         setDestructiveness(0);
/*     */       }
/*     */     }
/* 613 */     else if (tier == 2)
/*     */     {
/* 615 */       if (flavour == 0)
/*     */       {
/* 617 */         setName("Zombie");
/* 618 */         setGender(1);
/* 619 */         setBaseMoveSpeedStat(0.19F);
/* 620 */         this.attackStrength = 7;
/* 621 */         setMaxHealthAndHealth(35.0F);
/* 622 */         this.selfDamage = 4;
/* 623 */         this.maxSelfDamage = 12;
/* 624 */         this.maxDestructiveness = 2;
/* 625 */         this.flammability = 4;
/* 626 */         this.itemDrop = ItemHoe.ag;
/* 627 */         this.dropChance = 0.25F;
/*     */ 
/* 629 */         setDestructiveness(2);
/*     */       }
/* 631 */       else if (flavour == 1)
/*     */       {
/* 633 */         setName("Zombie");
/* 634 */         setGender(1);
/* 635 */         setBaseMoveSpeedStat(0.19F);
/* 636 */         this.attackStrength = 10;
/* 637 */         setMaxHealthAndHealth(40.0F);
/* 638 */         this.selfDamage = 3;
/* 639 */         this.maxSelfDamage = 9;
/* 640 */         this.maxDestructiveness = 0;
/* 641 */         this.itemDrop = ItemHoe.s;
/* 642 */         this.dropChance = 0.25F;
/* 643 */         this.defaultHeldItem = new EnumToolMaterial(ItemHoe.s, 1);
/*     */ 
/* 645 */         setDestructiveness(0);
/*     */       }
/* 647 */       else if (flavour == 2)
/*     */       {
/* 649 */         setName("Tar Zombie");
/* 650 */         setGender(1);
/* 651 */         setBaseMoveSpeedStat(0.19F);
/* 652 */         this.attackStrength = 5;
/* 653 */         setMaxHealthAndHealth(30.0F);
/* 654 */         this.selfDamage = 3;
/* 655 */         this.maxSelfDamage = 9;
/* 656 */         this.maxDestructiveness = 2;
/* 657 */         this.flammability = 30;
/* 658 */         this.floatsInWater = false;
/*     */ 
/* 660 */         setDestructiveness(2);
/*     */       }
/* 662 */       else if (flavour == 3)
/*     */       {
/* 664 */         setName("Zombie Pigman");
/* 665 */         setGender(1);
/* 666 */         setBaseMoveSpeedStat(0.25F);
/* 667 */         this.attackStrength = 8;
/* 668 */         setMaxHealthAndHealth(30.0F);
/* 669 */         this.maxDestructiveness = 2;
/* 670 */         this.isImmuneToFire = true;
/* 671 */         this.defaultHeldItem = new EnumToolMaterial(ItemHoe.I, 1);
/* 672 */         setDestructiveness(2);
/*     */       }
/*     */     }
/* 675 */     else if (tier == 3)
/*     */     {
/* 677 */       if (flavour == 0)
/*     */       {
/* 679 */         setName("Zombie Brute");
/* 680 */         setGender(1);
/* 681 */         setBaseMoveSpeedStat(0.17F);
/* 682 */         this.attackStrength = 18;
/* 683 */         setMaxHealthAndHealth(65.0F);
/* 684 */         this.selfDamage = 4;
/* 685 */         this.maxSelfDamage = 20;
/* 686 */         this.maxDestructiveness = 2;
/* 687 */         this.flammability = 4;
/*     */ 
/* 689 */         this.dropChance = 0.0F;
/*     */ 
/* 691 */         setDestructiveness(2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doFireball()
/*     */   {
/* 698 */     int x = LongHashMapEntry.c(this.posX);
/* 699 */     int y = LongHashMapEntry.c(this.posY);
/* 700 */     int z = LongHashMapEntry.c(this.posZ);
/* 701 */     for (int i = -1; i < 2; i++)
/*     */     {
/* 703 */       for (int j = -1; j < 2; j++)
/*     */       {
/* 705 */         for (int k = -1; k < 2; k++)
/*     */         {
/* 707 */           if ((this.q.a(x + i, y + j, z + k) == 0) || (this.q.g(x + i, y + j, z + k).getCanBurn())) {
/* 708 */             this.q.c(x + i, y + j, z + k, BlockEndPortal.aw.blockID);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 714 */     List entities = this.q.b(this, this.E.b(1.5D, 1.5D, 1.5D));
/* 715 */     for (nm entity : entities) {
/* 716 */       entity.d(8);
/*     */     }
/* 718 */     a(CombatTracker.field_94556_a, 500.0F);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMZombie
 * JD-Core Version:    0.6.2
 */