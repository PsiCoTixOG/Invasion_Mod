/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.nexus.SpawnPoint;
/*     */ import invmod.common.nexus.SpawnType;
/*     */ import invmod.common.nexus.TileEntityNexus;
/*     */ import invmod.common.util.ComparatorDistanceFrom;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EntitySelectorArmoredMob;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.passive.EntityWaterMob;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMWolf extends EntityWaterMob
/*     */ {
/*     */   private static final int META_BOUND = 30;
/*     */   private INexusAccess nexus;
/*     */   private int nexusX;
/*     */   private int nexusY;
/*     */   private int nexusZ;
/*     */   private int updateTimer;
/*     */   private boolean loadedFromNBT;
/*     */   private float maxHealth;
/*     */ 
/*     */   public EntityIMWolf(ColorizerGrass world)
/*     */   {
/*  42 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMWolf(EntityWaterMob wolf, INexusAccess nexus)
/*     */   {
/*  47 */     this(wolf.q, nexus);
/*  48 */     this.loadedFromNBT = false;
/*  49 */     setPositionAndRotation(wolf.posX, wolf.posY, wolf.posZ, wolf.rotationYaw, wolf.rotationPitch);
/*  50 */     this.ah.b(16, Byte.valueOf(wolf.u().a(16)));
/*  51 */     this.ah.b(17, wolf.u().e(17));
/*  52 */     this.ah.b(18, Float.valueOf(wolf.u().d(18)));
/*  53 */     this.bp.a(bU());
/*     */   }
/*     */ 
/*     */   public EntityIMWolf(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  58 */     super(world);
/*  59 */     this.d.a(5, new EntityAIHurtByTarget(this, EntityEnderman.class, 0, true));
/*  60 */     setHealth(getMaxHealth());
/*  61 */     this.ah.a(30, Byte.valueOf((byte)0));
/*  62 */     this.nexus = nexus;
/*  63 */     if (nexus != null)
/*     */     {
/*  65 */       this.nexusX = nexus.getXCoord();
/*  66 */       this.nexusY = nexus.getYCoord();
/*  67 */       this.nexusZ = nexus.getZCoord();
/*  68 */       this.ah.b(30, Byte.valueOf((byte)1));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setDead()
/*     */   {
/*  75 */     super.setDead();
/*  76 */     if (this.loadedFromNBT)
/*     */     {
/*  78 */       this.loadedFromNBT = false;
/*  79 */       checkNexus();
/*     */     }
/*     */ 
/*  82 */     if ((!this.q.I) && (this.updateTimer++ > 40))
/*  83 */       checkNexus();
/*     */   }
/*     */ 
/*     */   public boolean m(nm par1Entity)
/*     */   {
/*  89 */     int damage = bT() ? 4 : 2;
/*  90 */     if ((par1Entity instanceof EntityEnderman))
/*  91 */       damage *= 2;
/*  92 */     boolean success = par1Entity.a(CombatTracker.a(this), damage);
/*  93 */     if (success) {
/*  94 */       heal(4.0F);
/*     */     }
/*  96 */     return success;
/*     */   }
/*     */ 
/*     */   public float getMaxHealth()
/*     */   {
/* 101 */     return !bT() ? 8.0F : 25.0F;
/*     */   }
/*     */ 
/*     */   public int cd()
/*     */   {
/* 107 */     return this.ah.a(30) == 1 ? 10 : 1;
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/* 113 */     if ((m() instanceof EntityEnderman)) {
/* 114 */       return "mob.wolf.growl";
/*     */     }
/* 116 */     return "mob.wolf.hurt";
/*     */   }
/*     */ 
/*     */   protected void aA()
/*     */   {
/* 122 */     this.deathTime += 1;
/* 123 */     if (this.deathTime == 120)
/*     */     {
/*     */       int i;
/* 125 */       if ((!this.q.I) && ((this.recentlyHit > 0) || (aB())) && (!isChild()))
/*     */       {
/* 127 */         for (i = e(this.aS); i > 0; )
/*     */         {
/* 129 */           int k = EntitySelectorArmoredMob.a(i);
/* 130 */           i -= k;
/* 131 */           this.q.d(new EntitySelectorArmoredMob(this.q, this.posX, this.posY, this.posZ, k));
/*     */         }
/*     */       }
/*     */ 
/* 135 */       preparePlayerToSpawn();
/* 136 */       for (int j = 0; j < 20; j++)
/*     */       {
/* 138 */         double d = this.rand.nextGaussian() * 0.02D;
/* 139 */         double d1 = this.rand.nextGaussian() * 0.02D;
/* 140 */         double d2 = this.rand.nextGaussian() * 0.02D;
/* 141 */         this.q.a("explode", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d, d1, d2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void preparePlayerToSpawn()
/*     */   {
/* 150 */     respawnAtNexus();
/* 151 */     this.isDead = true;
/*     */   }
/*     */ 
/*     */   public void setHealth(float par1)
/*     */   {
/* 158 */     this.ah.b(6, Float.valueOf(LongHashMapEntry.a(par1, 0.0F, getMaxHealth())));
/*     */   }
/*     */ 
/*     */   public boolean respawnAtNexus()
/*     */   {
/* 163 */     if ((!this.q.I) && (this.ah.a(30) == 1) && (this.nexus != null))
/*     */     {
/* 165 */       EntityIMWolf wolfRecreation = new EntityIMWolf(this, this.nexus);
/*     */ 
/* 167 */       int x = this.nexus.getXCoord();
/* 168 */       int y = this.nexus.getYCoord();
/* 169 */       int z = this.nexus.getZCoord();
/* 170 */       List spawnPoints = new ArrayList();
/* 171 */       setRotation(0.0F, 0.0F);
/* 172 */       for (int vertical = 0; vertical < 3; vertical = vertical > 0 ? vertical * -1 : vertical * -1 + 1)
/*     */       {
/* 174 */         for (int i = -4; i < 5; i++)
/*     */         {
/* 176 */           for (int j = -4; j < 5; j++)
/*     */           {
/* 178 */             wolfRecreation.setPosition(x + i + 0.5F, y + vertical, z + j + 0.5F);
/* 179 */             if (wolfRecreation.getCanSpawnHere())
/* 180 */               spawnPoints.add(new SpawnPoint(x + i, y + vertical, z + i, 0, SpawnType.WOLF));
/*     */           }
/*     */         }
/*     */       }
/* 184 */       Collections.sort(spawnPoints, new ComparatorDistanceFrom(x, y, z));
/*     */ 
/* 187 */       if (spawnPoints.size() > 0)
/*     */       {
/* 189 */         SpawnPoint point = (SpawnPoint)spawnPoints.get(spawnPoints.size() / 2);
/* 190 */         wolfRecreation.setPosition(point.getXCoord() + 0.5D, point.getYCoord(), point.getZCoord() + 0.5D);
/* 191 */         wolfRecreation.heal(60.0F);
/* 192 */         this.q.d(wolfRecreation);
/* 193 */         return true;
/*     */       }
/*     */     }
/* 196 */     mod_Invasion.log("No respawn spot for wolf");
/* 197 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean getCanSpawnHere()
/*     */   {
/* 203 */     return (this.q.b(this.E)) && (this.q.a(this, this.E).size() == 0) && (!this.q.d(this.E));
/*     */   }
/*     */ 
/*     */   public boolean a(CallableItemName player)
/*     */   {
/* 209 */     EnumToolMaterial itemstack = player.bn.h();
/* 210 */     if (itemstack != null)
/*     */     {
/* 212 */       if ((itemstack.EMERALD == ItemHoe.aZ.itemID) && (player.bu.equalsIgnoreCase(h_())) && (this.ah.a(30) == 1))
/*     */       {
/* 214 */         this.ah.b(30, Byte.valueOf((byte)0));
/* 215 */         this.nexus = null;
/*     */ 
/* 217 */         itemstack.STONE -= 1;
/* 218 */         if (itemstack.STONE <= 0)
/* 219 */           player.bn.a(player.bn.allowFlying, null);
/* 220 */         return false;
/*     */       }
/* 222 */       if ((itemstack.EMERALD == mod_Invasion.itemStrangeBone.itemID) && (player.bu.equalsIgnoreCase(h_())))
/*     */       {
/* 224 */         INexusAccess newNexus = findNexus();
/* 225 */         if ((newNexus != null) && (newNexus != this.nexus))
/*     */         {
/* 227 */           this.nexus = newNexus;
/* 228 */           this.ah.b(30, Byte.valueOf((byte)1));
/* 229 */           this.nexusX = this.nexus.getXCoord();
/* 230 */           this.nexusY = this.nexus.getYCoord();
/* 231 */           this.nexusZ = this.nexus.getZCoord();
/*     */ 
/* 233 */           itemstack.STONE -= 1;
/* 234 */           if (itemstack.STONE <= 0) {
/* 235 */             player.bn.a(player.bn.allowFlying, null);
/*     */           }
/* 237 */           this.maxHealth = 25.0F;
/* 238 */           setHealth(25.0F);
/*     */         }
/* 240 */         return true;
/*     */       }
/*     */     }
/* 243 */     return super.a(player);
/*     */   }
/*     */ 
/*     */   public void b(NBTTagByte nbttagcompound)
/*     */   {
/* 249 */     super.b(nbttagcompound);
/* 250 */     if (this.nexus != null)
/*     */     {
/* 252 */       nbttagcompound.a("nexusX", this.nexus.getXCoord());
/* 253 */       nbttagcompound.a("nexusY", this.nexus.getYCoord());
/* 254 */       nbttagcompound.a("nexusZ", this.nexus.getZCoord());
/*     */     }
/* 256 */     nbttagcompound.a("nexusBound", this.ah.a(30));
/*     */   }
/*     */ 
/*     */   public void a(NBTTagByte nbttagcompound)
/*     */   {
/* 262 */     super.a(nbttagcompound);
/* 263 */     this.nexusX = nbttagcompound.e("nexusX");
/* 264 */     this.nexusY = nbttagcompound.e("nexusY");
/* 265 */     this.nexusZ = nbttagcompound.e("nexusZ");
/* 266 */     this.ah.b(30, Byte.valueOf(nbttagcompound.c("nexusBound")));
/* 267 */     this.loadedFromNBT = true;
/*     */   }
/*     */ 
/*     */   public void l(boolean par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void checkNexus()
/*     */   {
/* 277 */     if ((this.q != null) && (this.ah.a(30) == 1))
/*     */     {
/* 279 */       if (this.q.a(this.nexusX, this.nexusY, this.nexusZ) == mod_Invasion.blockNexus.cF) {
/* 280 */         this.nexus = ((TileEntityNexus)this.q.r(this.nexusX, this.nexusY, this.nexusZ));
/*     */       }
/* 282 */       if (this.nexus == null)
/* 283 */         this.ah.b(30, Byte.valueOf((byte)0));
/*     */     }
/*     */   }
/*     */ 
/*     */   private INexusAccess findNexus()
/*     */   {
/* 289 */     TileEntityNexus nexus = null;
/* 290 */     int x = LongHashMapEntry.c(this.posX);
/* 291 */     int y = LongHashMapEntry.c(this.posY);
/* 292 */     int z = LongHashMapEntry.c(this.posZ);
/* 293 */     for (int i = -7; i < 8; i++)
/*     */     {
/* 295 */       for (int j = -4; j < 5; j++)
/*     */       {
/* 297 */         for (int k = -7; k < 8; k++)
/*     */         {
/* 299 */           if (this.q.a(x + i, y + j, z + k) == mod_Invasion.blockNexus.cF)
/*     */           {
/* 301 */             nexus = (TileEntityNexus)this.q.r(x + i, y + j, z + k);
/* 302 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 308 */     return nexus;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMWolf
 * JD-Core Version:    0.6.2
 */