/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IPathfindable;
/*     */ import net.minecraft.server.management.LowerStringMap;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public class PathfinderIM
/*     */ {
/*  25 */   private static PathfinderIM pathfinder = new PathfinderIM();
/*     */   private EnumGameType worldMap;
/*     */   private NodeContainer path;
/*     */   private LowerStringMap pointMap;
/*     */   private PathNode[] pathOptions;
/*     */   private PathNode finalTarget;
/*     */   private float targetRadius;
/*     */   private int pathsIndex;
/*     */   private float searchRange;
/*     */   private int nodeLimit;
/*     */   private int nodesOpened;
/*     */ 
/*     */   public static synchronized Path createPath(IPathfindable entity, int x, int y, int z, int x2, int y2, int z2, float targetRadius, float maxSearchRange, EnumGameType iblockaccess, int searchDepth, int quickFailDepth)
/*     */   {
/*  31 */     return pathfinder.createEntityPathTo(entity, x, y, z, x2, y2, z2, targetRadius, maxSearchRange, iblockaccess, searchDepth, quickFailDepth);
/*     */   }
/*     */ 
/*     */   public PathfinderIM()
/*     */   {
/*  36 */     this.path = new NodeContainer();
/*  37 */     this.pointMap = new LowerStringMap();
/*  38 */     this.pathOptions = new PathNode[32];
/*     */   }
/*     */ 
/*     */   public Path createEntityPathTo(IPathfindable entity, int x, int y, int z, int x2, int y2, int z2, float targetRadius, float maxSearchRange, EnumGameType iblockaccess, int searchDepth, int quickFailDepth)
/*     */   {
/*  45 */     this.worldMap = iblockaccess;
/*  46 */     this.nodeLimit = searchDepth;
/*  47 */     this.nodesOpened = 1;
/*  48 */     this.searchRange = maxSearchRange;
/*  49 */     this.path.clearPath();
/*  50 */     this.pointMap.c();
/*  51 */     PathNode start = openPoint(x, y, z);
/*  52 */     PathNode target = openPoint(x2, y2, z2);
/*  53 */     this.finalTarget = target;
/*  54 */     this.targetRadius = targetRadius;
/*  55 */     Path pathentity = addToPath(entity, start, target);
/*     */ 
/*  57 */     return pathentity;
/*     */   }
/*     */ 
/*     */   private Path addToPath(IPathfindable entity, PathNode start, PathNode target)
/*     */   {
/*  62 */     start.totalPathDistance = 0.0F;
/*  63 */     start.distanceToNext = start.distanceTo(target);
/*  64 */     start.distanceToTarget = start.distanceToNext;
/*  65 */     this.path.clearPath();
/*  66 */     this.path.addPoint(start);
/*  67 */     PathNode previousPoint = start;
/*     */ 
/*  71 */     int loops = 0;
/*     */ 
/*  74 */     long elapsed = 0L;
/*  75 */     while (!this.path.isPathEmpty())
/*     */     {
/*  86 */       if (this.nodesOpened > this.nodeLimit)
/*     */       {
/*  89 */         return createEntityPath(start, previousPoint);
/*     */       }
/*  91 */       PathNode examiningPoint = this.path.dequeue();
/*  92 */       float distanceToTarget = examiningPoint.distanceTo(target);
/*  93 */       if (distanceToTarget < this.targetRadius + 0.1F)
/*     */       {
/*  96 */         return createEntityPath(start, examiningPoint);
/*     */       }
/*  98 */       if (distanceToTarget < previousPoint.distanceTo(target))
/*     */       {
/* 100 */         previousPoint = examiningPoint;
/*     */       }
/* 102 */       examiningPoint.isFirst = true;
/*     */ 
/* 104 */       int i = findPathOptions(entity, examiningPoint, target);
/*     */ 
/* 108 */       int j = 0;
/* 109 */       while (j < i)
/*     */       {
/* 111 */         PathNode newPoint = this.pathOptions[j];
/*     */ 
/* 114 */         float actualCost = examiningPoint.totalPathDistance + entity.getBlockPathCost(examiningPoint, newPoint, this.worldMap);
/*     */ 
/* 116 */         if ((!newPoint.isAssigned()) || (actualCost < newPoint.totalPathDistance))
/*     */         {
/* 118 */           newPoint.previous = examiningPoint;
/* 119 */           newPoint.totalPathDistance = actualCost;
/* 120 */           newPoint.distanceToNext = estimateDistance(newPoint, target);
/*     */ 
/* 136 */           if (newPoint.isAssigned())
/*     */           {
/* 138 */             this.path.changeDistance(newPoint, newPoint.totalPathDistance + newPoint.distanceToNext);
/*     */           }
/*     */           else
/*     */           {
/* 142 */             newPoint.distanceToTarget = (newPoint.totalPathDistance + newPoint.distanceToNext);
/* 143 */             this.path.addPoint(newPoint);
/*     */           }
/*     */         }
/* 146 */         j++;
/*     */       }
/*     */     }
/*     */ 
/* 150 */     if (previousPoint == start) {
/* 151 */       return null;
/*     */     }
/* 153 */     return createEntityPath(start, previousPoint);
/*     */   }
/*     */ 
/*     */   public void addNode(int x, int y, int z, PathAction action)
/*     */   {
/* 158 */     PathNode node = openPoint(x, y, z, action);
/* 159 */     if ((node != null) && (!node.isFirst) && (node.distanceTo(this.finalTarget) < this.searchRange))
/* 160 */       this.pathOptions[(this.pathsIndex++)] = node;
/*     */   }
/*     */ 
/*     */   private float estimateDistance(PathNode start, PathNode target)
/*     */   {
/* 165 */     return Math.abs(target.xCoord - start.xCoord) + Math.abs(target.yCoord - start.yCoord) + Math.abs(target.zCoord - start.zCoord) * 1.01F;
/*     */   }
/*     */ 
/*     */   protected PathNode openPoint(int x, int y, int z)
/*     */   {
/* 170 */     return openPoint(x, y, z, PathAction.NONE);
/*     */   }
/*     */ 
/*     */   protected PathNode openPoint(int x, int y, int z, PathAction action)
/*     */   {
/* 175 */     int hash = PathNode.makeHash(x, y, z, action);
/* 176 */     PathNode pathpoint = (PathNode)this.pointMap.a(hash);
/* 177 */     if (pathpoint == null)
/*     */     {
/* 179 */       pathpoint = new PathNode(x, y, z, action);
/* 180 */       this.pointMap.a(hash, pathpoint);
/* 181 */       this.nodesOpened += 1;
/*     */     }
/*     */ 
/* 188 */     return pathpoint;
/*     */   }
/*     */ 
/*     */   private int findPathOptions(IPathfindable entity, PathNode pathpoint, PathNode target)
/*     */   {
/* 193 */     this.pathsIndex = 0;
/* 194 */     entity.getPathOptionsFromNode(this.worldMap, pathpoint, this);
/* 195 */     return this.pathsIndex;
/*     */   }
/*     */ 
/*     */   private Path createEntityPath(PathNode pathpoint, PathNode pathpoint1)
/*     */   {
/* 268 */     int i = 1;
/* 269 */     for (PathNode pathpoint2 = pathpoint1; pathpoint2.previous != null; pathpoint2 = pathpoint2.previous)
/*     */     {
/* 271 */       i++;
/*     */     }
/*     */ 
/* 274 */     PathNode[] apathpoint = new PathNode[i];
/* 275 */     PathNode pathpoint3 = pathpoint1;
/* 276 */     for (apathpoint[(--i)] = pathpoint3; pathpoint3.previous != null; apathpoint[(--i)] = pathpoint3)
/*     */     {
/* 278 */       pathpoint3 = pathpoint3.previous;
/*     */     }
/*     */ 
/* 281 */     return new Path(apathpoint, this.finalTarget);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.PathfinderIM
 * JD-Core Version:    0.6.2
 */