/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IBlockAccessExtended;
/*     */ import invmod.common.IPathfindable;
/*     */ import invmod.common.TerrainDataLayer;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.Distance;
/*     */ import invmod.common.util.IPosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.nbt.NBTTagInt;
/*     */ import net.minecraft.server.management.LowerStringMap;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ import net.minecraft.world.PortalPosition;
/*     */ 
/*     */ public class AttackerAI
/*     */ {
/*     */   private INexusAccess nexus;
/*     */   private IPathSource pathSource;
/*     */   private LowerStringMap entityDensityData;
/*     */   private List<Scaffold> scaffolds;
/*     */   private int scaffoldLimit;
/*     */   private int minDistanceBetweenScaffolds;
/*     */   private int nextScaffoldCalcTimer;
/*     */   private int updateScaffoldTimer;
/*     */   private int nextEntityDensityUpdate;
/*     */ 
/*     */   public AttackerAI(INexusAccess nexus)
/*     */   {
/*  42 */     this.nexus = nexus;
/*  43 */     this.pathSource = new PathCreator();
/*  44 */     this.pathSource.setSearchDepth(8500);
/*  45 */     this.pathSource.setQuickFailDepth(8500);
/*  46 */     this.entityDensityData = new LowerStringMap();
/*  47 */     this.scaffolds = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void update()
/*     */   {
/*  52 */     this.nextScaffoldCalcTimer -= 1;
/*  53 */     if (--this.updateScaffoldTimer <= 0)
/*     */     {
/*  55 */       this.updateScaffoldTimer = 40;
/*  56 */       updateScaffolds();
/*     */ 
/*  58 */       this.scaffoldLimit = (2 + this.nexus.getCurrentWave() / 2);
/*  59 */       this.minDistanceBetweenScaffolds = (90 / (this.nexus.getCurrentWave() + 10));
/*     */     }
/*     */ 
/*  62 */     if (--this.nextEntityDensityUpdate <= 0)
/*     */     {
/*  64 */       this.nextEntityDensityUpdate = 20;
/*  65 */       updateDensityData();
/*     */     }
/*     */   }
/*     */ 
/*     */   public IBlockAccessExtended wrapEntityData(EnumGameType terrainMap)
/*     */   {
/*  71 */     TerrainDataLayer newTerrain = new TerrainDataLayer(terrainMap);
/*  72 */     newTerrain.setAllData(this.entityDensityData);
/*  73 */     return newTerrain;
/*     */   }
/*     */ 
/*     */   public int getMinDistanceBetweenScaffolds()
/*     */   {
/*  82 */     return this.minDistanceBetweenScaffolds;
/*     */   }
/*     */ 
/*     */   public List<Scaffold> getScaffolds()
/*     */   {
/*  90 */     return this.scaffolds;
/*     */   }
/*     */ 
/*     */   public boolean askGenerateScaffolds(EntityIMLiving entity)
/*     */   {
/* 105 */     if ((this.nextScaffoldCalcTimer > 0) || (this.scaffolds.size() > this.scaffoldLimit)) {
/* 106 */       return false;
/*     */     }
/* 108 */     this.nextScaffoldCalcTimer = 200;
/* 109 */     List newScaffolds = findMinScaffolds(entity, LongHashMapEntry.c(entity.posX), LongHashMapEntry.c(entity.posY), LongHashMapEntry.c(entity.posZ));
/* 110 */     if ((newScaffolds != null) && (newScaffolds.size() > 0))
/*     */     {
/* 112 */       addNewScaffolds(newScaffolds);
/* 113 */       return true;
/*     */     }
/*     */ 
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   public List<Scaffold> findMinScaffolds(IPathfindable pather, int x, int y, int z)
/*     */   {
/* 129 */     Scaffold scaffold = new Scaffold(this.nexus);
/* 130 */     scaffold.setPathfindBase(pather);
/* 131 */     Path basePath = createPath(scaffold, x, y, z, this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord(), 12.0F);
/* 132 */     if (basePath == null) {
/* 133 */       return new ArrayList();
/*     */     }
/* 135 */     List scaffoldPositions = extractScaffolds(basePath);
/* 136 */     if (scaffoldPositions.size() > 1)
/*     */     {
/* 141 */       float lowestCost = (1.0F / 1.0F);
/* 142 */       int lowestCostIndex = -1;
/* 143 */       for (int i = 0; i < scaffoldPositions.size(); i++)
/*     */       {
/* 145 */         TerrainDataLayer terrainMap = new TerrainDataLayer(getChunkCache(x, y, z, this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord(), 12.0F));
/* 146 */         Scaffold s = (Scaffold)scaffoldPositions.get(i);
/* 147 */         terrainMap.setData(s.getXCoord(), s.getYCoord(), s.getZCoord(), Integer.valueOf(200000));
/* 148 */         Path path = createPath(pather, x, y, z, this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord(), terrainMap);
/* 149 */         if ((path.getTotalPathCost() < lowestCost) && (path.getFinalPathPoint().equals(this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord()))) {
/* 150 */           lowestCostIndex = i;
/*     */         }
/*     */       }
/*     */ 
/* 154 */       if (lowestCostIndex >= 0)
/*     */       {
/* 156 */         List s = new ArrayList();
/* 157 */         s.add(scaffoldPositions.get(lowestCostIndex));
/* 158 */         return s;
/*     */       }
/*     */ 
/* 164 */       List costDif = new ArrayList(scaffoldPositions.size());
/* 165 */       for (int i = 0; i < scaffoldPositions.size(); i++)
/*     */       {
/* 167 */         TerrainDataLayer terrainMap = new TerrainDataLayer(getChunkCache(x, y, z, this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord(), 12.0F));
/* 168 */         Scaffold s = (Scaffold)scaffoldPositions.get(i);
/* 169 */         for (int j = 0; j < scaffoldPositions.size(); j++)
/*     */         {
/* 171 */           if (j != i)
/*     */           {
/* 174 */             terrainMap.setData(s.getXCoord(), s.getYCoord(), s.getZCoord(), Integer.valueOf(200000));
/*     */           }
/*     */         }
/* 176 */         Path path = createPath(pather, x, y, z, this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord(), terrainMap);
/*     */ 
/* 178 */         if (!path.getFinalPathPoint().equals(this.nexus.getXCoord(), this.nexus.getYCoord(), this.nexus.getZCoord()))
/*     */         {
/* 180 */           costDif.add(s);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 185 */       return costDif;
/*     */     }
/*     */ 
/* 188 */     if (scaffoldPositions.size() == 1)
/*     */     {
/* 190 */       return scaffoldPositions;
/*     */     }
/*     */ 
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   public void addScaffoldDataTo(IBlockAccessExtended terrainMap)
/*     */   {
/* 206 */     for (Scaffold scaffold : this.scaffolds)
/*     */     {
/* 208 */       for (int i = 0; i < scaffold.getTargetHeight(); i++)
/*     */       {
/* 210 */         int data = terrainMap.getLayeredData(scaffold.getXCoord(), scaffold.getYCoord() + i, scaffold.getZCoord());
/* 211 */         terrainMap.setData(scaffold.getXCoord(), scaffold.getYCoord() + i, scaffold.getZCoord(), Integer.valueOf(data | 0x4000));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Scaffold getScaffoldAt(IPosition pos)
/*     */   {
/* 221 */     return getScaffoldAt(pos.getXCoord(), pos.getYCoord(), pos.getZCoord());
/*     */   }
/*     */ 
/*     */   public Scaffold getScaffoldAt(int x, int y, int z)
/*     */   {
/* 229 */     for (Scaffold scaffold : this.scaffolds)
/*     */     {
/* 231 */       if ((scaffold.getXCoord() == x) && (scaffold.getZCoord() == z))
/*     */       {
/* 233 */         if ((scaffold.getYCoord() <= y) && (scaffold.getYCoord() + scaffold.getTargetHeight() >= y))
/* 234 */           return scaffold;
/*     */       }
/*     */     }
/* 237 */     return null;
/*     */   }
/*     */ 
/*     */   public void onResume()
/*     */   {
/* 242 */     for (Scaffold scaffold : this.scaffolds)
/*     */     {
/* 244 */       scaffold.forceStatusUpdate();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagByte nbttagcompound)
/*     */   {
/* 250 */     NBTTagInt nbtScaffoldList = nbttagcompound.m("scaffolds");
/* 251 */     for (int i = 0; i < nbtScaffoldList.c(); i++)
/*     */     {
/* 253 */       Scaffold scaffold = new Scaffold(this.nexus);
/* 254 */       scaffold.readFromNBT((NBTTagByte)nbtScaffoldList.b(i));
/* 255 */       this.scaffolds.add(scaffold);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagByte nbttagcompound)
/*     */   {
/* 261 */     NBTTagInt nbttaglist = new NBTTagInt();
/* 262 */     for (Scaffold scaffold : this.scaffolds)
/*     */     {
/* 264 */       NBTTagByte nbtscaffold = new NBTTagByte();
/* 265 */       scaffold.writeToNBT(nbtscaffold);
/* 266 */       nbttaglist.a(nbtscaffold);
/*     */     }
/* 268 */     nbttagcompound.a("scaffolds", nbttaglist);
/*     */   }
/*     */ 
/*     */   private Path createPath(IPathfindable pather, int x1, int y1, int z1, int x2, int y2, int z2, EnumGameType terrainMap)
/*     */   {
/* 273 */     return this.pathSource.createPath(pather, x1, y1, z1, x2, y2, z2, 1.1F, 12.0F + (float)Distance.distanceBetween(x1, y1, z1, x2, y2, z2), terrainMap);
/*     */   }
/*     */ 
/*     */   private Path createPath(IPathfindable pather, int x, int y, int z, int x2, int y2, int z2, float axisExpand)
/*     */   {
/* 282 */     TerrainDataLayer terrainMap = new TerrainDataLayer(getChunkCache(x, y, z, x2, y2, z2, axisExpand));
/* 283 */     addScaffoldDataTo(terrainMap);
/* 284 */     return createPath(pather, x, y, z, x2, y2, z2, terrainMap);
/*     */   }
/*     */ 
/*     */   private PortalPosition getChunkCache(int x1, int y1, int z1, int x2, int y2, int z2, float axisExpand)
/*     */   {
/* 292 */     int d = (int)axisExpand;
/*     */     int cX2;
/*     */     int cX2;
/*     */     int cX1;
/* 299 */     if (x1 < x2)
/*     */     {
/* 301 */       int cX1 = x1 - d;
/* 302 */       cX2 = x2 + d;
/*     */     }
/*     */     else
/*     */     {
/* 306 */       cX2 = x1 + d;
/* 307 */       cX1 = x2 - d;
/*     */     }
/*     */     int cY2;
/*     */     int cY2;
/*     */     int cY1;
/* 309 */     if (y1 < y2)
/*     */     {
/* 311 */       int cY1 = y1 - d;
/* 312 */       cY2 = y2 + d;
/*     */     }
/*     */     else
/*     */     {
/* 316 */       cY2 = y1 + d;
/* 317 */       cY1 = y2 - d;
/*     */     }
/*     */     int cZ2;
/*     */     int cZ2;
/*     */     int cZ1;
/* 319 */     if (z1 < z2)
/*     */     {
/* 321 */       int cZ1 = z1 - d;
/* 322 */       cZ2 = z2 + d;
/*     */     }
/*     */     else
/*     */     {
/* 326 */       cZ2 = z1 + d;
/* 327 */       cZ1 = z2 - d;
/*     */     }
/* 329 */     return new PortalPosition(this.nexus.getWorld(), cX1, cY1, cZ1, cX2, cY2, cZ2, 0);
/*     */   }
/*     */ 
/*     */   private List<Scaffold> extractScaffolds(Path path)
/*     */   {
/* 334 */     List scaffoldPositions = new ArrayList();
/* 335 */     boolean flag = false;
/* 336 */     int startHeight = 0;
/* 337 */     for (int i = 0; i < path.getCurrentPathLength(); i++)
/*     */     {
/* 339 */       PathNode node = path.getPathPointFromIndex(i);
/* 340 */       if (!flag)
/*     */       {
/* 342 */         if (node.action == PathAction.SCAFFOLD_UP)
/*     */         {
/* 344 */           flag = true;
/* 345 */           startHeight = node.getYCoord() - 1;
/*     */         }
/*     */ 
/*     */       }
/* 350 */       else if (node.action != PathAction.SCAFFOLD_UP)
/*     */       {
/* 352 */         Scaffold scaffold = new Scaffold(node.previous.getXCoord(), startHeight, node.previous.getZCoord(), node.getYCoord() - startHeight, this.nexus);
/* 353 */         orientScaffold(scaffold, this.nexus.getWorld());
/* 354 */         scaffold.setInitialIntegrity();
/* 355 */         scaffoldPositions.add(scaffold);
/* 356 */         flag = false;
/*     */       }
/*     */     }
/*     */ 
/* 360 */     return scaffoldPositions;
/*     */   }
/*     */ 
/*     */   private void orientScaffold(Scaffold scaffold, EnumGameType terrainMap)
/*     */   {
/* 368 */     int mostBlocks = 0;
/* 369 */     int highestDirectionIndex = 0;
/* 370 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 372 */       int blockCount = 0;
/* 373 */       for (int height = 0; height < scaffold.getYCoord(); height++)
/*     */       {
/* 375 */         if (terrainMap.u(scaffold.getXCoord() + invmod.common.util.CoordsInt.offsetAdjX[i], scaffold.getYCoord() + height, scaffold.getZCoord() + invmod.common.util.CoordsInt.offsetAdjZ[i])) {
/* 376 */           blockCount++;
/*     */         }
/* 378 */         if (terrainMap.u(scaffold.getXCoord() + invmod.common.util.CoordsInt.offsetAdjX[i] * 2, scaffold.getYCoord() + height, scaffold.getZCoord() + invmod.common.util.CoordsInt.offsetAdjZ[i] * 2)) {
/* 379 */           blockCount++;
/*     */         }
/*     */       }
/* 382 */       if (blockCount > mostBlocks) {
/* 383 */         highestDirectionIndex = i;
/*     */       }
/*     */     }
/* 386 */     scaffold.setOrientation(highestDirectionIndex);
/*     */   }
/*     */ 
/*     */   private void addNewScaffolds(List<Scaffold> newScaffolds)
/*     */   {
/* 392 */     for (Scaffold newScaffold : newScaffolds)
/*     */     {
/* 394 */       for (Scaffold existingScaffold : this.scaffolds)
/*     */       {
/* 397 */         if ((existingScaffold.getXCoord() == newScaffold.getXCoord()) && (existingScaffold.getZCoord() == newScaffold.getZCoord()))
/*     */         {
/* 400 */           if (newScaffold.getYCoord() > existingScaffold.getYCoord())
/*     */           {
/* 402 */             if (newScaffold.getYCoord() < existingScaffold.getYCoord() + existingScaffold.getTargetHeight())
/*     */             {
/* 404 */               existingScaffold.setHeight(newScaffold.getYCoord() + newScaffold.getTargetHeight() - existingScaffold.getYCoord());
/* 405 */               break;
/*     */             }
/*     */ 
/*     */           }
/* 410 */           else if (newScaffold.getYCoord() + newScaffold.getTargetHeight() > existingScaffold.getYCoord())
/*     */           {
/* 412 */             existingScaffold.setPosition(newScaffold.getXCoord(), newScaffold.getYCoord(), newScaffold.getZCoord());
/* 413 */             existingScaffold.setHeight(existingScaffold.getYCoord() + existingScaffold.getTargetHeight() - newScaffold.getYCoord());
/* 414 */             break;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 420 */       this.scaffolds.add(newScaffold);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateScaffolds()
/*     */   {
/* 426 */     for (int i = 0; i < this.scaffolds.size(); i++)
/*     */     {
/* 428 */       Scaffold lol = (Scaffold)this.scaffolds.get(i);
/* 429 */       this.nexus.getWorld().a("heart", lol.getXCoord() + 0.2D, lol.getYCoord() + 0.2D, lol.getZCoord() + 0.2D, lol.getXCoord() + 0.5D, lol.getYCoord() + 0.5D, lol.getZCoord() + 0.5D);
/*     */ 
/* 431 */       ((Scaffold)this.scaffolds.get(i)).forceStatusUpdate();
/* 432 */       if (((Scaffold)this.scaffolds.get(i)).getPercentIntactCached() + 0.05F < 0.4F * ((Scaffold)this.scaffolds.get(i)).getPercentCompletedCached())
/* 433 */         this.scaffolds.remove(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateDensityData()
/*     */   {
/* 439 */     this.entityDensityData.c();
/* 440 */     List mobs = this.nexus.getMobList();
/* 441 */     for (EntityIMLiving mob : mobs)
/*     */     {
/* 443 */       int coordHash = PathNode.makeHash(mob.getXCoord(), mob.getYCoord(), mob.getZCoord(), PathAction.NONE);
/* 444 */       if (this.entityDensityData.b(coordHash))
/*     */       {
/* 446 */         Integer value = (Integer)this.entityDensityData.a(coordHash);
/* 447 */         if (value.intValue() < 7)
/*     */         {
/* 449 */           this.entityDensityData.a(coordHash, Integer.valueOf(value.intValue() + 1));
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 454 */         this.entityDensityData.a(coordHash, Integer.valueOf(1));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.AttackerAI
 * JD-Core Version:    0.6.2
 */