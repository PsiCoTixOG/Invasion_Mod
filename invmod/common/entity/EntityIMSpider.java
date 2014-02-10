/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.EntityConstruct;
/*     */ import invmod.common.nexus.IMEntityType;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.nexus.MobBuilder;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.StepSoundStone;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EntityLivingData;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMSpider extends EntityIMMob
/*     */   implements ISpawnsOffspring
/*     */ {
/*     */   private IMMoveHelper i;
/*     */   private byte metaChanged;
/*     */   private int tier;
/*     */   private int flavour;
/*     */   private int pounceTime;
/*     */   private int pounceAbility;
/*     */   private int airborneTime;
/*     */   private static final int META_CHANGED = 29;
/*     */   private static final int META_TIER = 30;
/*     */   private static final int META_TEXTURE = 31;
/*     */   private static final int META_FLAVOUR = 28;
/*     */ 
/*     */   public EntityIMSpider(ColorizerGrass world)
/*     */   {
/*  42 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMSpider(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  47 */     super(world, nexus);
/*  48 */     setSize(1.4F, 0.9F);
/*  49 */     setCanClimb(true);
/*  50 */     this.airborneTime = 0;
/*  51 */     if (world.I)
/*  52 */       this.metaChanged = 1;
/*     */     else
/*  54 */       this.metaChanged = 0;
/*  55 */     this.tier = 1;
/*  56 */     this.flavour = 0;
/*  57 */     setAttributes(this.tier, this.flavour);
/*  58 */     setAI();
/*  59 */     this.i = new IMMoveHelperSpider(this);
/*     */ 
/*  61 */     EntityCreature dataWatcher = u();
/*  62 */     dataWatcher.a(29, Byte.valueOf(this.metaChanged));
/*  63 */     dataWatcher.a(30, Integer.valueOf(this.tier));
/*  64 */     dataWatcher.a(31, Integer.valueOf(0));
/*  65 */     dataWatcher.a(28, Integer.valueOf(this.flavour));
/*     */   }
/*     */ 
/*     */   protected void setAI()
/*     */   {
/*  70 */     this.c = new EntityAIBase(this.q.C);
/*  71 */     this.c.a(0, new EntityAIKillEntity(this, CallableItemName.class, 40));
/*  72 */     this.c.a(1, new EntityAIAttackNexus(this));
/*  73 */     this.c.a(2, new EntityAIWaitForEngy(this, 5.0F, false));
/*  74 */     this.c.a(3, new EntityAIKillEntity(this, EntityLivingBase.class, 40));
/*  75 */     this.c.a(4, new EntityAIGoToNexus(this));
/*  76 */     this.c.a(6, new EntityAIWanderIM(this));
/*  77 */     this.c.a(7, new EntityAILeapAtTarget(this, CallableItemName.class, 8.0F));
/*     */ 
/*  79 */     this.c.a(8, new EntityAIPlay(this));
/*     */ 
/*  81 */     this.d = new EntityAIBase(this.q.C);
/*  82 */     this.d.a(0, new EntityAITargetRetaliate(this, EntityLivingBase.class, 12.0F));
/*  83 */     this.d.a(2, new EntityAISimpleTarget(this, CallableItemName.class, 14.0F, true));
/*  84 */     this.d.a(3, new EntityAITargetOnNoNexusPath(this, EntityIMPigEngy.class, 3.5F));
/*  85 */     this.d.a(4, new EntityAIDefendVillage(this, false));
/*     */ 
/*  88 */     this.c.a(0, new EntityAIRallyBehindEntity(this, EntityIMCreeper.class, 4.0F));
/*  89 */     this.c.a(8, new EntityAILeapAtTarget(this, EntityIMCreeper.class, 12.0F));
/*     */ 
/*  94 */     if (this.tier == 2)
/*     */     {
/*  96 */       if (this.flavour == 0)
/*     */       {
/*  98 */         this.c.a(2, new EntityAIPounce(this, 0.2F, 1.55F, 18));
/*     */       }
/* 100 */       else if (this.flavour == 1)
/*     */       {
/* 102 */         this.c.a(0, new EntityAILayEgg(this, 1));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 110 */     if (this.q.I) {
/* 111 */       this.q.I = this.q.I;
/*     */     }
/* 113 */     super.onUpdate();
/* 114 */     if ((this.q.I) && (this.metaChanged != u().a(29)))
/*     */     {
/* 116 */       EntityCreature data = u();
/* 117 */       this.metaChanged = data.a(29);
/* 118 */       setTexture(data.c(31));
/*     */ 
/* 120 */       if (this.tier != data.c(30))
/* 121 */         setTier(data.c(30));
/* 122 */       if (this.flavour != data.c(28))
/* 123 */         setFlavour(data.c(28));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void moveEntityWithHeading(float x, float z)
/*     */   {
/* 134 */     if (isWet())
/*     */     {
/* 136 */       double y = this.posY;
/* 137 */       moveFlying(x, z, 0.02F);
/* 138 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 139 */       this.motionX *= 0.8D;
/* 140 */       this.motionY *= 0.8D;
/* 141 */       this.motionZ *= 0.8D;
/* 142 */       this.motionY -= 0.02D;
/* 143 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/* 144 */         this.motionY = 0.3D;
/*     */     }
/* 146 */     else if (handleWaterMovement())
/*     */     {
/* 148 */       double y = this.posY;
/* 149 */       moveFlying(x, z, 0.02F);
/* 150 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 151 */       this.motionX *= 0.5D;
/* 152 */       this.motionY *= 0.5D;
/* 153 */       this.motionZ *= 0.5D;
/* 154 */       this.motionY -= 0.02D;
/* 155 */       if ((this.isCollidedHorizontally) && (isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6D - this.posY + y, this.motionZ)))
/* 156 */         this.motionY = 0.3D;
/*     */     }
/*     */     else
/*     */     {
/* 160 */       float groundFriction = 0.91F;
/*     */ 
/* 162 */       if (this.airborneTime == 0)
/*     */       {
/*     */         float landMoveSpeed;
/* 164 */         if (this.onGround)
/*     */         {
/* 166 */           groundFriction = 0.546F;
/* 167 */           int i = this.q.a(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.E.b) - 1, LongHashMapEntry.c(this.posZ));
/* 168 */           if (i > 0) {
/* 169 */             groundFriction = BlockEndPortal.s[i].slipperiness * 0.91F;
/*     */           }
/* 171 */           float landMoveSpeed = bf();
/* 172 */           landMoveSpeed *= 0.162771F / (groundFriction * groundFriction * groundFriction);
/*     */         }
/*     */         else
/*     */         {
/* 176 */           landMoveSpeed = this.jumpMovementFactor;
/*     */         }
/*     */ 
/* 179 */         moveFlying(x, z, landMoveSpeed);
/*     */       }
/*     */       else
/*     */       {
/* 183 */         groundFriction = 1.0F;
/*     */       }
/*     */ 
/* 186 */       if (isOnLadder())
/*     */       {
/* 188 */         float maxLadderXZSpeed = 0.15F;
/* 189 */         if (this.motionX < -maxLadderXZSpeed)
/* 190 */           this.motionX = (-maxLadderXZSpeed);
/* 191 */         if (this.motionX > maxLadderXZSpeed)
/* 192 */           this.motionX = maxLadderXZSpeed;
/* 193 */         if (this.motionZ < -maxLadderXZSpeed)
/* 194 */           this.motionZ = (-maxLadderXZSpeed);
/* 195 */         if (this.motionZ > maxLadderXZSpeed) {
/* 196 */           this.motionZ = maxLadderXZSpeed;
/*     */         }
/* 198 */         this.fallDistance = 0.0F;
/* 199 */         if (this.motionY < -0.15D) {
/* 200 */           this.motionY = -0.15D;
/*     */         }
/* 202 */         if ((isRiding()) && (this.motionY < 0.0D)) {
/* 203 */           this.motionY = 0.0D;
/*     */         }
/*     */       }
/* 206 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/* 207 */       if (((this.isCollidedHorizontally) || (this.isJumping)) && (isOnLadder())) {
/* 208 */         this.motionY = 0.2D;
/*     */       }
/* 210 */       float airResistance = 1.0F;
/* 211 */       this.motionY -= getGravity();
/* 212 */       this.motionY *= airResistance;
/* 213 */       this.motionX *= groundFriction * airResistance;
/* 214 */       this.motionZ *= groundFriction * airResistance;
/*     */     }
/*     */ 
/* 217 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 218 */     double dX = this.posX - this.prevPosX;
/* 219 */     double dZ = this.posZ - this.prevPosZ;
/* 220 */     float limbEnergy = LongHashMapEntry.a(dX * dX + dZ * dZ) * 4.0F;
/*     */ 
/* 222 */     if (limbEnergy > 1.0F)
/*     */     {
/* 224 */       limbEnergy = 1.0F;
/*     */     }
/*     */ 
/* 227 */     this.limbSwingAmount += (limbEnergy - this.limbSwingAmount) * 0.4F;
/* 228 */     this.limbSwing += this.limbSwingAmount;
/*     */   }
/*     */ 
/*     */   public IMMoveHelper getMoveHelper()
/*     */   {
/* 234 */     return this.i;
/*     */   }
/*     */ 
/*     */   protected void bd()
/*     */   {
/* 240 */     this.motionY = 0.41D;
/* 241 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */   public void setTier(int tier)
/*     */   {
/* 252 */     this.tier = tier;
/* 253 */     u().b(30, Integer.valueOf(tier));
/* 254 */     setAttributes(tier, this.flavour);
/* 255 */     setAI();
/*     */ 
/* 257 */     if (u().c(31) == 0)
/*     */     {
/* 259 */       if (tier == 1)
/*     */       {
/* 261 */         setTexture(0);
/*     */       }
/* 263 */       else if (tier == 2)
/*     */       {
/* 265 */         if (this.flavour == 0)
/* 266 */           setTexture(1);
/*     */         else
/* 268 */           setTexture(2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTexture(int textureId)
/*     */   {
/* 275 */     u().b(31, Integer.valueOf(textureId));
/*     */   }
/*     */ 
/*     */   public void setFlavour(int flavour)
/*     */   {
/* 280 */     this.flavour = flavour;
/* 281 */     u().b(28, Integer.valueOf(flavour));
/* 282 */     setAttributes(this.tier, flavour);
/*     */   }
/*     */ 
/*     */   public int getTextureId()
/*     */   {
/* 287 */     return u().c(31);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 293 */     return "EntityIMSpider#" + this.tier + "-" + u().c(31) + "-" + this.flavour;
/*     */   }
/*     */ 
/*     */   public double getYOffset()
/*     */   {
/* 299 */     return this.height * 0.75D - 0.5D;
/*     */   }
/*     */ 
/*     */   public void b(NBTTagByte nbttagcompound)
/*     */   {
/* 305 */     nbttagcompound.a("tier", this.tier);
/* 306 */     nbttagcompound.a("flavour", this.flavour);
/* 307 */     nbttagcompound.a("textureId", this.ah.c(31));
/* 308 */     super.b(nbttagcompound);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagByte nbttagcompound)
/*     */   {
/* 314 */     setTexture(nbttagcompound.e("textureId"));
/* 315 */     this.flavour = nbttagcompound.e("flavour");
/* 316 */     this.tier = nbttagcompound.e("tier");
/* 317 */     if (this.tier == 0)
/* 318 */       this.tier = 1;
/* 319 */     setTier(this.tier);
/* 320 */     super.a(nbttagcompound);
/*     */   }
/*     */ 
/*     */   public boolean avoidsBlock(int id)
/*     */   {
/* 326 */     if ((id == 51) || (id == 7))
/*     */     {
/* 328 */       return true;
/*     */     }
/*     */ 
/* 332 */     return false;
/*     */   }
/*     */ 
/*     */   public float spiderScaleAmount()
/*     */   {
/* 338 */     if ((this.tier == 1) && (this.flavour == 1))
/* 339 */       return 0.35F;
/* 340 */     if ((this.tier == 2) && (this.flavour == 1)) {
/* 341 */       return 1.3F;
/*     */     }
/* 343 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   public nm[] getOffspring(nm partner)
/*     */   {
/* 349 */     if ((this.tier == 2) && (this.flavour == 1))
/*     */     {
/* 351 */       EntityConstruct template = new EntityConstruct(IMEntityType.SPIDER, 1, 0, 1, 1.0F, 0, 0);
/* 352 */       nm[] offSpring = new nm[6];
/* 353 */       for (int i = 0; i < offSpring.length; i++)
/*     */       {
/* 355 */         offSpring[i] = mod_Invasion.getMobBuilder().createMobFromConstruct(template, this.q, getNexus());
/*     */       }
/* 357 */       return offSpring;
/*     */     }
/*     */ 
/* 361 */     return null;
/*     */   }
/*     */ 
/*     */   public int getAirborneTime()
/*     */   {
/* 367 */     return this.airborneTime;
/*     */   }
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 373 */     return !isOnLadder();
/*     */   }
/*     */ 
/*     */   public EntityLivingData aX()
/*     */   {
/* 379 */     return EntityLivingData.c;
/*     */   }
/*     */ 
/*     */   public boolean checkForAdjacentClimbBlock()
/*     */   {
/* 385 */     return this.isCollidedHorizontally;
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 394 */     return "Spider";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/* 400 */     return 2;
/*     */   }
/*     */ 
/*     */   protected void setAirborneTime(int time)
/*     */   {
/* 407 */     this.airborneTime = time;
/*     */   }
/*     */ 
/*     */   protected boolean canTriggerWalking()
/*     */   {
/* 413 */     return false;
/*     */   }
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 419 */     return "mob.spider.say";
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/* 425 */     return "mob.spider.say";
/*     */   }
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 431 */     return "mob.spider.death";
/*     */   }
/*     */ 
/*     */   protected void fall(float f)
/*     */   {
/* 437 */     int i = (int)Math.ceil(f - 3.0F);
/* 438 */     if (i > 0)
/*     */     {
/* 440 */       int j = this.q.a(LongHashMapEntry.c(this.posX), LongHashMapEntry.c(this.posY - 0.2D - this.yOffset), LongHashMapEntry.c(this.posZ));
/* 441 */       if (j > 0)
/*     */       {
/* 443 */         StepSoundStone stepsound = BlockEndPortal.s[j].cS;
/* 444 */         this.q.a(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected int getDropItemId()
/*     */   {
/* 452 */     return ItemHoe.M.itemID;
/*     */   }
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int bonus)
/*     */   {
/* 458 */     if ((this.tier == 1) && (this.flavour == 1)) {
/* 459 */       return;
/*     */     }
/* 461 */     super.dropFewItems(flag, bonus);
/* 462 */     if (this.rand.nextFloat() < 0.35F)
/*     */     {
/* 464 */       b(ItemHoe.M.itemID, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setAttributes(int tier, int flavour)
/*     */   {
/* 470 */     setGravity(0.08F);
/* 471 */     setSize(1.4F, 0.9F);
/* 472 */     setGender(this.rand.nextInt(2) + 1);
/* 473 */     if (tier == 1)
/*     */     {
/* 475 */       if (flavour == 0)
/*     */       {
/* 477 */         setName("Spider");
/* 478 */         setBaseMoveSpeedStat(0.29F);
/* 479 */         this.attackStrength = 3;
/* 480 */         setMaxHealthAndHealth(18.0F);
/* 481 */         this.pounceTime = 0;
/* 482 */         this.pounceAbility = 0;
/* 483 */         this.maxDestructiveness = 0;
/* 484 */         setDestructiveness(0);
/* 485 */         setAggroRange(10);
/*     */       }
/* 487 */       else if (flavour == 1)
/*     */       {
/* 489 */         setName("Baby Spider");
/* 490 */         setSize(0.42F, 0.3F);
/* 491 */         setBaseMoveSpeedStat(0.17F);
/* 492 */         this.attackStrength = 1;
/* 493 */         setMaxHealthAndHealth(3.0F);
/* 494 */         this.pounceTime = 0;
/* 495 */         this.pounceAbility = 0;
/* 496 */         this.maxDestructiveness = 0;
/* 497 */         setDestructiveness(0);
/* 498 */         setAggroRange(10);
/*     */       }
/*     */     }
/* 501 */     else if (tier == 2)
/*     */     {
/* 503 */       if (flavour == 0)
/*     */       {
/* 505 */         setName("Jumping Spider");
/* 506 */         setBaseMoveSpeedStat(0.3F);
/* 507 */         this.attackStrength = 5;
/* 508 */         setMaxHealthAndHealth(18.0F);
/* 509 */         this.pounceTime = 0;
/* 510 */         this.pounceAbility = 1;
/* 511 */         this.maxDestructiveness = 0;
/* 512 */         setDestructiveness(0);
/* 513 */         setAggroRange(18);
/* 514 */         setGravity(0.043F);
/*     */       }
/* 516 */       else if (flavour == 1)
/*     */       {
/* 518 */         setName("Mother Spider");
/* 519 */         setGender(2);
/* 520 */         setBaseMoveSpeedStat(0.22F);
/* 521 */         this.attackStrength = 4;
/* 522 */         setMaxHealthAndHealth(23.0F);
/* 523 */         this.pounceTime = 0;
/* 524 */         this.pounceAbility = 0;
/* 525 */         this.maxDestructiveness = 0;
/* 526 */         setDestructiveness(0);
/* 527 */         setAggroRange(18);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMSpider
 * JD-Core Version:    0.6.2
 */