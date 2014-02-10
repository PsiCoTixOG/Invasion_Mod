/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAIFleeSun;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.passive.EntityMooshroom;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.src.abq;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public class EntityIMCreeper extends EntityIMMob
/*     */   implements ILeader
/*     */ {
/*     */   private int timeSinceIgnited;
/*     */   private int lastActiveTime;
/*     */   private boolean explosionDeath;
/*     */   private boolean commitToExplode;
/*     */   private int explodeDirection;
/*     */ 
/*     */   public EntityIMCreeper(ColorizerGrass world)
/*     */   {
/*  34 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMCreeper(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  39 */     super(world, nexus);
/*  40 */     setName("Creeper");
/*  41 */     setGender(0);
/*  42 */     setBaseMoveSpeedStat(0.21F);
/*  43 */     setMaxHealthAndHealth(20.0F);
/*  44 */     this.c.a(0, new EntityAIFleeSun(this));
/*  45 */     this.c.a(1, new EntityAICreeperIMSwell(this));
/*  46 */     this.c.a(2, new EntityMoveHelper(this, EntityMooshroom.class, 6.0F, 0.25D, 0.300000011920929D));
/*  47 */     this.c.a(3, new EntityAIKillEntity(this, EntityLivingBase.class, 40));
/*  48 */     this.c.a(4, new EntityAIGoToNexus(this));
/*  49 */     this.c.a(5, new EntityAIWanderIM(this));
/*  50 */     this.c.a(6, new EntityAILeapAtTarget(this, CallableItemName.class, 4.8F));
/*  51 */     this.c.a(6, new EntityAIPlay(this));
/*  52 */     this.d.a(0, new EntityAITargetRetaliate(this, EntityLivingBase.class, 12.0F));
/*  53 */     this.d.a(1, new EntityAISimpleTarget(this, CallableItemName.class, 4.8F, true));
/*  54 */     this.d.a(2, new EntityAIDefendVillage(this, false));
/*     */   }
/*     */ 
/*     */   public void collideWithNearbyEntities()
/*     */   {
/*  60 */     super.collideWithNearbyEntities();
/*     */   }
/*     */ 
/*     */   public boolean be()
/*     */   {
/*  66 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onPathBlocked(Path path, INotifyTask notifee)
/*     */   {
/*  72 */     if (!path.isFinished())
/*     */     {
/*  74 */       PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
/*  75 */       double dX = node.xCoord + 0.5D - this.posX;
/*  76 */       double dZ = node.zCoord + 0.5D - this.posZ;
/*  77 */       float facing = (float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D) - 90.0F;
/*  78 */       if (facing < 0.0F)
/*     */       {
/*  80 */         facing += 360.0F;
/*     */       }
/*  82 */       facing %= 360.0F;
/*     */ 
/*  84 */       if ((facing >= 45.0F) && (facing < 135.0F))
/*  85 */         this.explodeDirection = 1;
/*  86 */       else if ((facing >= 135.0F) && (facing < 225.0F))
/*  87 */         this.explodeDirection = 3;
/*  88 */       else if ((facing >= 225.0F) && (facing < 315.0F))
/*  89 */         this.explodeDirection = 0;
/*     */       else {
/*  91 */         this.explodeDirection = 2;
/*     */       }
/*  93 */       setCreeperState(1);
/*  94 */       this.commitToExplode = true;
/*     */     }
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   protected void entityInit()
/*     */   {
/* 102 */     super.entityInit();
/* 103 */     this.ah.a(16, Byte.valueOf((byte)-1));
/* 104 */     this.ah.a(17, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 110 */     if (this.explosionDeath)
/*     */     {
/* 112 */       doExplosion();
/* 113 */       preparePlayerToSpawn();
/*     */     }
/* 115 */     else if (S())
/*     */     {
/* 117 */       this.lastActiveTime = this.timeSinceIgnited;
/* 118 */       int state = getCreeperState();
/*     */ 
/* 120 */       if (state > 0)
/*     */       {
/* 122 */         if (this.commitToExplode) {
/* 123 */           getMoveHelper().a(this.posX + invmod.common.util.CoordsInt.offsetAdjX[this.explodeDirection], this.posY, this.posZ + invmod.common.util.CoordsInt.offsetAdjZ[this.explodeDirection], 0.0D);
/*     */         }
/* 125 */         if (this.timeSinceIgnited == 0) {
/* 126 */           this.q.a(this, "random.fuse", 1.0F, 0.5F);
/*     */         }
/*     */       }
/* 129 */       this.timeSinceIgnited += state;
/* 130 */       if (this.timeSinceIgnited < 0) {
/* 131 */         this.timeSinceIgnited = 0;
/*     */       }
/* 133 */       if (this.timeSinceIgnited >= 30)
/*     */       {
/* 135 */         this.timeSinceIgnited = 30;
/* 136 */         if (!this.q.I)
/*     */         {
/* 138 */           this.explosionDeath = true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 143 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   public boolean isMartyr()
/*     */   {
/* 149 */     return this.explosionDeath;
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/* 155 */     return "mob.creeper.say";
/*     */   }
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 161 */     return "mob.creeper.death";
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 170 */     return "Creeper";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/* 176 */     return 2;
/*     */   }
/*     */ 
/*     */   public void a(CombatTracker par1DamageSource)
/*     */   {
/* 184 */     super.a(par1DamageSource);
/*     */ 
/* 186 */     if ((par1DamageSource.i() instanceof EntitySilverfish))
/*     */     {
/* 188 */       b(ItemHoe.cj.itemID + this.rand.nextInt(10), 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean m(nm par1Entity)
/*     */   {
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */   public float setCreeperFlashTime(float par1)
/*     */   {
/* 203 */     return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / 28.0F;
/*     */   }
/*     */ 
/*     */   public float getBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*     */   {
/* 209 */     int id = terrainMap.a(node.xCoord, node.yCoord, node.zCoord);
/* 210 */     if ((id > 0) && (!BlockEndPortal.s[id].b(terrainMap, node.xCoord, node.yCoord, node.zCoord)) && (id != mod_Invasion.blockNexus.cF))
/*     */     {
/* 212 */       return prevNode.distanceTo(node) * 12.0F;
/*     */     }
/*     */ 
/* 215 */     return super.getBlockPathCost(prevNode, node, terrainMap);
/*     */   }
/*     */ 
/*     */   public boolean isBlockTypeDestructible(int id)
/*     */   {
/* 221 */     if ((id == 0) || (id == BlockEndPortal.E.blockID) || (id == BlockEndPortal.aK.blockID))
/*     */     {
/* 223 */       return false;
/*     */     }
/* 225 */     if ((id == BlockEndPortal.aQ.blockID) || (id == BlockEndPortal.aJ.blockID) || (id == BlockEndPortal.bp.blockID))
/*     */     {
/* 227 */       return true;
/*     */     }
/* 229 */     if (BlockEndPortal.s[id].cU.isSolid())
/*     */     {
/* 231 */       return true;
/*     */     }
/*     */ 
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 242 */     return "EntityIMCreeper#";
/*     */   }
/*     */ 
/*     */   protected int getDropItemId()
/*     */   {
/* 248 */     return ItemHoe.O.itemID;
/*     */   }
/*     */ 
/*     */   protected void doExplosion()
/*     */   {
/* 253 */     abq explosion = this.q.a(this, this.posX, this.posY + 1.0D, this.posZ, 1.3F, true);
/* 254 */     int x = getXCoord();
/* 255 */     int y = getYCoord() + 1;
/* 256 */     int z = getZCoord();
/* 257 */     int direction = 0;
/* 258 */     float facing = this.rotationYaw % 360.0F;
/* 259 */     if (facing < 0.0F) {
/* 260 */       facing += 360.0F;
/*     */     }
/* 262 */     if ((facing >= 45.0F) && (facing < 135.0F))
/* 263 */       direction = 1;
/* 264 */     else if ((facing >= 135.0F) && (facing < 225.0F))
/* 265 */       direction = 3;
/* 266 */     else if ((facing >= 225.0F) && (facing < 315.0F))
/* 267 */       direction = 0;
/*     */     else {
/* 269 */       direction = 2;
/*     */     }
/* 271 */     for (int i = -1; i < 2; i++)
/*     */     {
/* 273 */       for (int j = -1; j < 2; j++)
/*     */       {
/* 275 */         float explosionStrength = 50.0F;
/* 276 */         for (int depth = 0; depth <= 3; depth++)
/*     */         {
/* 279 */           if ((depth == 3) && (((j != -1) && (j != 0)) || (i != 0))) {
/*     */             break;
/*     */           }
/* 282 */           int xOff = i;
/* 283 */           int zOff = i;
/* 284 */           if (direction == 0)
/* 285 */             xOff = depth;
/* 286 */           else if (direction == 1)
/* 287 */             xOff = -depth;
/* 288 */           else if (direction == 2)
/* 289 */             zOff = depth;
/*     */           else {
/* 291 */             zOff = -depth;
/*     */           }
/* 293 */           int id = this.q.a(x + xOff, y + j, z + zOff);
/* 294 */           if ((id > 0) && (id != mod_Invasion.blockNexus.cF))
/*     */           {
/* 296 */             explosionStrength -= BlockEndPortal.s[id].a(this);
/* 297 */             if (explosionStrength < 0.0F) {
/*     */               break;
/*     */             }
/* 300 */             BlockEndPortal.s[id].a(this.q, x + xOff, y + j, z + zOff, this.q.h(x + xOff, y + j, z + zOff), 0.5F, 0);
/* 301 */             this.q.c(x + xOff, y + j, z + zOff, 0);
/* 302 */             BlockEndPortal.s[id].a(this.q, x + xOff, y + j, z + zOff, explosion);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getCreeperState()
/*     */   {
/* 314 */     return this.ah.a(16);
/*     */   }
/*     */ 
/*     */   public void setCreeperState(int state)
/*     */   {
/* 322 */     if ((this.commitToExplode) && (state != 1)) {
/* 323 */       return;
/*     */     }
/* 325 */     this.ah.b(16, Byte.valueOf((byte)state));
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMCreeper
 * JD-Core Version:    0.6.2
 */