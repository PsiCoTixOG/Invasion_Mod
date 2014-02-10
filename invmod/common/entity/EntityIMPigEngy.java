/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IBlockAccessExtended;
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.CoordsInt;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ import yd;
/*     */ 
/*     */ public class EntityIMPigEngy extends EntityIMMob
/*     */   implements ICanDig
/*     */ {
/*     */   private static final EnumToolMaterial itemLadder;
/*     */   private static final EnumToolMaterial itemPick;
/*     */   private static final EnumToolMaterial itemHammer;
/*     */   private static final int MAX_LADDER_TOWER_HEIGHT = 4;
/*     */   private static final int META_ITEM_ID_HELD = 29;
/*     */   private static final int META_SWINGING = 30;
/*  33 */   private static final Map<Integer, yd> itemMap = new HashMap();
/*     */   private final NavigatorEngy bo;
/*     */   private final PathNavigateAdapter oldNavAdapter;
/*     */   private int swingTimer;
/*     */   private int planks;
/*     */   private int askForScaffoldTimer;
/*     */   private float supportThisTick;
/*     */   private TerrainModifier terrainModifier;
/*     */   private TerrainDigger terrainDigger;
/*     */   private TerrainBuilder terrainBuilder;
/*     */   private EnumToolMaterial currentItem;
/*     */ 
/*     */   public EntityIMPigEngy(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  59 */     super(world, nexus);
/*     */ 
/*  62 */     IPathSource pathSource = getPathSource();
/*  63 */     pathSource.setSearchDepth(1500);
/*  64 */     pathSource.setQuickFailDepth(1500);
/*  65 */     this.bo = new NavigatorEngy(this, pathSource);
/*  66 */     this.oldNavAdapter = new PathNavigateAdapter(this.bo);
/*  67 */     pathSource.setSearchDepth(1200);
/*     */ 
/*  70 */     this.terrainModifier = new TerrainModifier(this, 2.8F);
/*  71 */     this.terrainDigger = new TerrainDigger(this, this.terrainModifier, 1.0F);
/*  72 */     this.terrainBuilder = new TerrainBuilder(this, this.terrainModifier, 1.0F);
/*     */ 
/*  74 */     setBaseMoveSpeedStat(0.23F);
/*  75 */     this.attackStrength = 2;
/*  76 */     setMaxHealthAndHealth(11.0F);
/*  77 */     this.selfDamage = 0;
/*  78 */     this.maxSelfDamage = 0;
/*  79 */     this.planks = 15;
/*  80 */     this.maxDestructiveness = 2;
/*  81 */     this.askForScaffoldTimer = 0;
/*     */ 
/*  83 */     this.ah.a(29, Short.valueOf((short)0));
/*  84 */     this.ah.a(30, Byte.valueOf((byte)0));
/*     */ 
/*  86 */     setName("Pig Engineer");
/*  87 */     setGender(1);
/*  88 */     setDestructiveness(2);
/*  89 */     setJumpHeight(1);
/*  90 */     setCanClimb(false);
/*  91 */     setAI();
/*     */ 
/*  93 */     int r = this.rand.nextInt(3);
/*  94 */     if (r == 0)
/*  95 */       setCurrentItem(itemHammer);
/*  96 */     else if (r == 1)
/*  97 */       setCurrentItem(itemPick);
/*     */     else
/*  99 */       setCurrentItem(itemLadder);
/*     */   }
/*     */ 
/*     */   public EntityIMPigEngy(ColorizerGrass world)
/*     */   {
/* 104 */     this(world, null);
/*     */   }
/*     */ 
/*     */   protected void setAI()
/*     */   {
/* 109 */     this.c = new EntityAIBase(this.q.C);
/* 110 */     this.c.a(0, new EntityAIKillEntity(this, CallableItemName.class, 60));
/* 111 */     this.c.a(1, new EntityAIAttackNexus(this));
/* 112 */     this.c.a(2, new EntityAIGoToNexus(this));
/* 113 */     this.c.a(6, new EntityAIWanderIM(this));
/* 114 */     this.c.a(7, new EntityAILeapAtTarget(this, CallableItemName.class, 7.0F));
/* 115 */     this.c.a(8, new EntityAILeapAtTarget(this, EntityIMCreeper.class, 12.0F));
/* 116 */     this.c.a(8, new EntityAIPlay(this));
/*     */ 
/* 118 */     this.d = new EntityAIBase(this.q.C);
/* 119 */     this.d.a(2, new EntityAISimpleTarget(this, CallableItemName.class, 3.0F, true));
/* 120 */     this.d.a(3, new EntityAIDefendVillage(this, false));
/*     */   }
/*     */ 
/*     */   public void bh()
/*     */   {
/* 126 */     super.bh();
/* 127 */     this.terrainModifier.onUpdate();
/*     */   }
/*     */ 
/*     */   public void collideWithNearbyEntities()
/*     */   {
/* 133 */     super.collideWithNearbyEntities();
/* 134 */     this.terrainBuilder.setBuildRate(1.0F + this.supportThisTick * 0.33F);
/*     */ 
/* 136 */     this.supportThisTick = 0.0F;
/*     */ 
/* 138 */     this.askForScaffoldTimer -= 1;
/* 139 */     if (this.targetNexus != null)
/*     */     {
/* 141 */       int weight = Math.max(6000 / this.targetNexus.getYCoord() - getYCoord(), 1);
/* 142 */       if ((this.currentGoal == Goal.BREAK_NEXUS) && (((getNavigatorNew().getLastPathDistanceToTarget() > 2.0F) && (this.askForScaffoldTimer <= 0)) || (this.rand.nextInt(weight) == 0)))
/*     */       {
/* 144 */         if (this.targetNexus.getAttackerAI().askGenerateScaffolds(this))
/*     */         {
/* 146 */           getNavigatorNew().clearPath();
/* 147 */           this.askForScaffoldTimer = 60;
/*     */         }
/*     */         else
/*     */         {
/* 151 */           this.askForScaffoldTimer = 140;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onLivingUpdate()
/*     */   {
/* 160 */     super.onLivingUpdate();
/* 161 */     updateAnimation();
/*     */   }
/*     */ 
/*     */   public void onPathSet()
/*     */   {
/* 167 */     this.terrainModifier.cancelTask();
/*     */   }
/*     */ 
/*     */   public PathNavigateAdapter getNavigator()
/*     */   {
/* 183 */     return this.oldNavAdapter;
/*     */   }
/*     */ 
/*     */   public INavigation getNavigatorNew()
/*     */   {
/* 192 */     return this.bo;
/*     */   }
/*     */ 
/*     */   public EnumGameType getTerrain()
/*     */   {
/* 198 */     return this.q;
/*     */   }
/*     */ 
/*     */   protected boolean onPathBlocked(Path path, INotifyTask notifee)
/*     */   {
/* 204 */     if (!path.isFinished())
/*     */     {
/* 206 */       PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
/* 207 */       return this.terrainDigger.askClearPosition(node.xCoord, node.yCoord, node.zCoord, notifee, 1.0F);
/*     */     }
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   protected ITerrainBuild getTerrainBuildEngy()
/*     */   {
/* 214 */     return this.terrainBuilder;
/*     */   }
/*     */ 
/*     */   protected ITerrainDig getTerrainDig()
/*     */   {
/* 219 */     return this.terrainDigger;
/*     */   }
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/* 225 */     return "mob.zombiepig.zpig";
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/* 231 */     return "mob.zombiepig.zpighurt";
/*     */   }
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/* 237 */     return "mob.pig.death";
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 246 */     return "Pigman";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/* 252 */     return 2;
/*     */   }
/*     */ 
/*     */   public float getBlockRemovalCost(int x, int y, int z)
/*     */   {
/* 260 */     return getBlockStrength(x, y, z) * 20.0F;
/*     */   }
/*     */ 
/*     */   public boolean canClearBlock(int x, int y, int z)
/*     */   {
/* 266 */     int id = this.q.a(x, y, z);
/* 267 */     return (id == 0) || (isBlockDestructible(this.q, x, y, z, id));
/*     */   }
/*     */ 
/*     */   public boolean avoidsBlock(int id)
/*     */   {
/* 273 */     if ((id == 51) || (id == 7) || (id == 64) || (id == 8) || (id == 9) || (id == 10) || (id == 11))
/*     */     {
/* 275 */       return true;
/*     */     }
/*     */ 
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isBlockDestructible(EnumGameType terrainMap, int x, int y, int z, int id)
/*     */   {
/* 286 */     return isBlockTypeDestructible(id);
/*     */   }
/*     */ 
/*     */   public boolean isBlockTypeDestructible(int id)
/*     */   {
/* 292 */     if ((id == 0) || (id == BlockEndPortal.E.blockID) || (id == BlockEndPortal.aK.blockID))
/*     */     {
/* 294 */       return false;
/*     */     }
/* 296 */     if ((id == BlockEndPortal.aQ.blockID) || (id == BlockEndPortal.aJ.blockID) || (id == BlockEndPortal.bp.blockID))
/*     */     {
/* 298 */       return true;
/*     */     }
/* 300 */     if (BlockEndPortal.s[id].cU.isSolid())
/*     */     {
/* 302 */       return true;
/*     */     }
/*     */ 
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */   public void supportForTick(EntityIMLiving entity, float amount)
/*     */   {
/* 312 */     this.supportThisTick += amount;
/*     */   }
/*     */ 
/*     */   public boolean canBeCollidedWith()
/*     */   {
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */   public float getBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*     */   {
/* 326 */     if ((node.xCoord == -21) && (node.zCoord == 180)) {
/* 327 */       this.planks = 10;
/*     */     }
/* 329 */     int id = terrainMap.a(node.xCoord, node.yCoord, node.zCoord);
/* 330 */     float materialMultiplier = (id != 0) && (isBlockDestructible(terrainMap, node.xCoord, node.yCoord, node.zCoord, id)) ? 3.2F : 1.0F;
/*     */ 
/* 332 */     if (node.action == PathAction.BRIDGE)
/* 333 */       return prevNode.distanceTo(node) * 1.7F * materialMultiplier;
/* 334 */     if (node.action == PathAction.SCAFFOLD_UP)
/* 335 */       return prevNode.distanceTo(node) * 0.5F;
/* 336 */     if ((node.action == PathAction.LADDER_UP_NX) || (node.action == PathAction.LADDER_UP_NZ) || (node.action == PathAction.LADDER_UP_PX) || (node.action == PathAction.LADDER_UP_PZ))
/* 337 */       return prevNode.distanceTo(node) * 1.3F * materialMultiplier;
/* 338 */     if ((node.action == PathAction.LADDER_TOWER_UP_PX) || (node.action == PathAction.LADDER_TOWER_UP_NX) || (node.action == PathAction.LADDER_TOWER_UP_PZ) || (node.action == PathAction.LADDER_TOWER_UP_NZ)) {
/* 339 */       return prevNode.distanceTo(node) * 1.4F;
/*     */     }
/*     */ 
/* 343 */     float multiplier = 1.0F;
/* 344 */     if ((terrainMap instanceof IBlockAccessExtended))
/*     */     {
/* 346 */       int mobDensity = ((IBlockAccessExtended)terrainMap).getLayeredData(node.xCoord, node.yCoord, node.zCoord) & 0x7;
/* 347 */       multiplier += mobDensity;
/*     */     }
/* 349 */     if (id == 0)
/*     */     {
/* 351 */       return prevNode.distanceTo(node) * 1.0F * multiplier;
/*     */     }
/* 353 */     if (id == BlockEndPortal.aX.blockID)
/*     */     {
/* 355 */       return prevNode.distanceTo(node) * 1.0F * multiplier;
/*     */     }
/* 357 */     if (id == BlockEndPortal.aK.blockID)
/*     */     {
/* 359 */       return prevNode.distanceTo(node) * 1.0F * 0.7F * multiplier;
/*     */     }
/* 361 */     if ((!BlockEndPortal.s[id].b(terrainMap, node.xCoord, node.yCoord, node.zCoord)) && (id != mod_Invasion.blockNexus.cF))
/*     */     {
/* 363 */       return prevNode.distanceTo(node) * 3.2F;
/*     */     }
/*     */ 
/* 367 */     return super.getBlockPathCost(prevNode, node, terrainMap);
/*     */   }
/*     */ 
/*     */   public void getPathOptionsFromNode(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 374 */     super.getPathOptionsFromNode(terrainMap, currentNode, pathFinder);
/* 375 */     if (this.planks <= 0) {
/* 376 */       return;
/*     */     }
/*     */ 
/* 379 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 382 */       if (getCollide(terrainMap, currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord, currentNode.zCoord + CoordsInt.offsetAdjZ[i]) > 0)
/*     */       {
/* 386 */         for (int yOffset = 0; yOffset > -4; yOffset--)
/*     */         {
/* 388 */           int id = terrainMap.a(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord - 1 + yOffset, currentNode.zCoord + CoordsInt.offsetAdjZ[i]);
/* 389 */           if (id != 0) break;
/* 390 */           pathFinder.addNode(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord + yOffset, currentNode.zCoord + CoordsInt.offsetAdjZ[i], PathAction.BRIDGE);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void calcPathOptionsVertical(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 400 */     if ((currentNode.xCoord == -11) && (currentNode.zCoord == 177)) {
/* 401 */       this.planks = 10;
/*     */     }
/* 403 */     super.calcPathOptionsVertical(terrainMap, currentNode, pathFinder);
/* 404 */     if (this.planks <= 0) {
/* 405 */       return;
/*     */     }
/*     */ 
/* 408 */     if (getCollide(terrainMap, currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord) > 0)
/*     */     {
/* 410 */       if (terrainMap.a(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord) == 0)
/*     */       {
/* 412 */         if (currentNode.action == PathAction.NONE)
/*     */         {
/* 414 */           addAnyLadderPoint(terrainMap, currentNode, pathFinder);
/*     */         }
/* 418 */         else if (!continueLadder(terrainMap, currentNode, pathFinder))
/*     */         {
/* 420 */           addAnyLadderPoint(terrainMap, currentNode, pathFinder);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 425 */       if ((currentNode.action == PathAction.NONE) || (currentNode.action == PathAction.BRIDGE))
/*     */       {
/* 429 */         int maxHeight = 4;
/* 430 */         for (int i = getCollideSize().getYCoord(); i < 4; i++)
/*     */         {
/* 432 */           int id = terrainMap.a(currentNode.xCoord, currentNode.yCoord + i, currentNode.zCoord);
/* 433 */           if ((id > 0) && (!BlockEndPortal.s[id].b(terrainMap, currentNode.xCoord, currentNode.yCoord + i, currentNode.zCoord)))
/*     */           {
/* 435 */             maxHeight = i - getCollideSize().getYCoord();
/* 436 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 443 */         for (int i = 0; i < 4; i++)
/*     */         {
/* 446 */           int id = terrainMap.a(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord - 1, currentNode.zCoord + CoordsInt.offsetAdjZ[i]);
/* 447 */           if (BlockEndPortal.isNormalCube(id))
/*     */           {
/* 450 */             for (int height = 0; height < maxHeight; height++)
/*     */             {
/* 452 */               id = terrainMap.a(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord + height, currentNode.zCoord + CoordsInt.offsetAdjZ[i]);
/* 453 */               if (id != 0)
/*     */               {
/* 459 */                 if (!BlockEndPortal.isNormalCube(id)) break;
/* 460 */                 pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.ladderTowerIndexOrient[i]); break;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 469 */     if ((terrainMap instanceof IBlockAccessExtended))
/*     */     {
/* 471 */       int data = ((IBlockAccessExtended)terrainMap).getLayeredData(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord);
/* 472 */       if (data == 16384)
/*     */       {
/* 474 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.SCAFFOLD_UP);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addAnyLadderPoint(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 481 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 483 */       if (terrainMap.u(currentNode.xCoord + CoordsInt.offsetAdjX[i], currentNode.yCoord + 1, currentNode.zCoord + CoordsInt.offsetAdjZ[i]))
/* 484 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.ladderIndexOrient[i]);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean continueLadder(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 493 */     switch (1.$SwitchMap$invmod$common$entity$PathAction[currentNode.action.ordinal()])
/*     */     {
/*     */     case 1:
/* 496 */       if (terrainMap.u(currentNode.xCoord + 1, currentNode.yCoord + 1, currentNode.zCoord))
/*     */       {
/* 498 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_PX);
/*     */       }
/* 500 */       return true;
/*     */     case 2:
/* 502 */       if (terrainMap.u(currentNode.xCoord - 1, currentNode.yCoord + 1, currentNode.zCoord))
/*     */       {
/* 504 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_NX);
/*     */       }
/* 506 */       return true;
/*     */     case 3:
/* 508 */       if (terrainMap.u(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord + 1))
/*     */       {
/* 510 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_PZ);
/*     */       }
/* 512 */       return true;
/*     */     case 4:
/* 514 */       if (terrainMap.u(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord - 1))
/*     */       {
/* 516 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.LADDER_UP_NZ);
/*     */       }
/* 518 */       return true;
/*     */     }
/*     */ 
/* 521 */     return false;
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial aY()
/*     */   {
/* 527 */     return getCurrentItem();
/*     */   }
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int bonus)
/*     */   {
/* 533 */     super.dropFewItems(flag, bonus);
/* 534 */     if (this.rand.nextInt(2) == 0)
/*     */     {
/* 536 */       a(new EnumToolMaterial(ItemHoe.aH, 1, 0), 0.0F);
/*     */     }
/* 540 */     else if (ae())
/* 541 */       a(new EnumToolMaterial(ItemHoe.at, 1, 0), 0.0F);
/*     */     else
/* 543 */       a(new EnumToolMaterial(ItemHoe.as, 1, 0), 0.0F);
/*     */   }
/*     */ 
/*     */   protected void updateAnimation()
/*     */   {
/* 549 */     if ((!this.q.I) && (this.terrainModifier.isBusy()))
/*     */     {
/* 551 */       setSwinging(true);
/* 552 */       PathAction currentAction = getNavigatorNew().getCurrentWorkingAction();
/* 553 */       if (currentAction == PathAction.NONE)
/* 554 */         setCurrentItem(itemPick);
/*     */       else {
/* 556 */         setCurrentItem(itemHammer);
/*     */       }
/*     */     }
/* 559 */     int swingSpeed = getSwingSpeed();
/* 560 */     if (isSwinging())
/*     */     {
/* 562 */       this.swingTimer += 1;
/* 563 */       if (this.swingTimer >= swingSpeed)
/*     */       {
/* 565 */         this.swingTimer = 0;
/* 566 */         setSwinging(false);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 571 */       this.swingTimer = 0;
/*     */     }
/*     */ 
/* 574 */     this.swingProgress = (this.swingTimer / swingSpeed);
/*     */   }
/*     */ 
/*     */   protected boolean isSwinging()
/*     */   {
/* 579 */     return u().a(30) != 0;
/*     */   }
/*     */ 
/*     */   protected void setSwinging(boolean flag)
/*     */   {
/* 584 */     u().b(30, Byte.valueOf((byte)(flag == true ? 1 : 0)));
/*     */   }
/*     */ 
/*     */   protected int getSwingSpeed()
/*     */   {
/* 589 */     return 10;
/*     */   }
/*     */ 
/*     */   protected EnumToolMaterial getCurrentItem()
/*     */   {
/* 594 */     if (this.q.I)
/*     */     {
/* 596 */       int id = u().b(29);
/* 597 */       if (id != this.currentItem.EMERALD)
/* 598 */         this.currentItem = ((EnumToolMaterial)itemMap.get(Integer.valueOf(id)));
/*     */     }
/* 600 */     return this.currentItem;
/*     */   }
/*     */ 
/*     */   protected void setCurrentItem(EnumToolMaterial item)
/*     */   {
/* 605 */     this.currentItem = item;
/* 606 */     u().b(29, Short.valueOf((short)item.EMERALD));
/*     */   }
/*     */ 
/*     */   public static boolean canPlaceLadderAt(EnumGameType map, int x, int y, int z)
/*     */   {
/* 611 */     if ((map.u(x + 1, y, z)) || (map.u(x - 1, y, z)) || (map.u(x, y, z + 1)) || (map.u(x, y, z - 1)))
/*     */     {
/* 613 */       return true;
/*     */     }
/* 615 */     return false;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 620 */     itemLadder = new EnumToolMaterial(BlockEndPortal.aK, 1);
/* 621 */     itemPick = new EnumToolMaterial(ItemHoe.i, 1);
/* 622 */     itemHammer = mod_Invasion.getRenderHammerItem();
/* 623 */     itemMap.put(Integer.valueOf(itemLadder.EMERALD), itemLadder);
/* 624 */     itemMap.put(Integer.valueOf(itemPick.EMERALD), itemPick);
/* 625 */     itemMap.put(Integer.valueOf(itemHammer.EMERALD), itemHammer);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMPigEngy
 * JD-Core Version:    0.6.2
 */