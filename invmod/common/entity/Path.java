/*     */ package invmod.common.entity;
/*     */ 
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.AABBPool;
/*     */ 
/*     */ public class Path
/*     */ {
/*     */   protected final PathNode[] points;
/*     */   private PathNode intendedTarget;
/*     */   private int pathLength;
/*     */   private int pathIndex;
/*     */   private float totalCost;
/*     */ 
/*     */   public Path(PathNode[] apathpoint)
/*     */   {
/*  16 */     this.points = apathpoint;
/*  17 */     this.pathLength = apathpoint.length;
/*  18 */     if (apathpoint.length > 0)
/*     */     {
/*  20 */       this.intendedTarget = apathpoint[(apathpoint.length - 1)];
/*     */     }
/*     */   }
/*     */ 
/*     */   public Path(PathNode[] apathpoint, PathNode intendedTarget)
/*     */   {
/*  26 */     this.points = apathpoint;
/*  27 */     this.pathLength = apathpoint.length;
/*  28 */     this.intendedTarget = intendedTarget;
/*     */   }
/*     */ 
/*     */   public float getTotalPathCost()
/*     */   {
/*  33 */     return this.points[(this.pathLength - 1)].totalPathDistance;
/*     */   }
/*     */ 
/*     */   public void incrementPathIndex()
/*     */   {
/*  38 */     this.pathIndex += 1;
/*     */   }
/*     */ 
/*     */   public boolean isFinished()
/*     */   {
/*  43 */     return this.pathIndex >= this.points.length;
/*     */   }
/*     */ 
/*     */   public PathNode getFinalPathPoint()
/*     */   {
/*  48 */     if (this.pathLength > 0)
/*     */     {
/*  50 */       return this.points[(this.pathLength - 1)];
/*     */     }
/*     */ 
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   public PathNode getPathPointFromIndex(int par1)
/*     */   {
/*  60 */     return this.points[par1];
/*     */   }
/*     */ 
/*     */   public int getCurrentPathLength()
/*     */   {
/*  65 */     return this.pathLength;
/*     */   }
/*     */ 
/*     */   public void setCurrentPathLength(int par1)
/*     */   {
/*  70 */     this.pathLength = par1;
/*     */   }
/*     */ 
/*     */   public int getCurrentPathIndex()
/*     */   {
/*  75 */     return this.pathIndex;
/*     */   }
/*     */ 
/*     */   public void setCurrentPathIndex(int par1)
/*     */   {
/*  80 */     this.pathIndex = par1;
/*     */   }
/*     */ 
/*     */   public PathNode getIntendedTarget()
/*     */   {
/*  85 */     return this.intendedTarget;
/*     */   }
/*     */ 
/*     */   public AABBPool getPositionAtIndex(nm entity, int index)
/*     */   {
/*  90 */     double d = this.points[index].xCoord + (int)(entity.O + 1.0F) * 0.5D;
/*  91 */     double d1 = this.points[index].yCoord;
/*  92 */     double d2 = this.points[index].zCoord + (int)(entity.O + 1.0F) * 0.5D;
/*  93 */     return AABBPool.a(d, d1, d2);
/*     */   }
/*     */ 
/*     */   public AABBPool getCurrentNodeVec3d(nm entity)
/*     */   {
/*  98 */     return getPositionAtIndex(entity, this.pathIndex);
/*     */   }
/*     */ 
/*     */   public AABBPool destination()
/*     */   {
/* 103 */     return AABBPool.a(this.points[(this.points.length - 1)].xCoord, this.points[(this.points.length - 1)].yCoord, this.points[(this.points.length - 1)].zCoord);
/*     */   }
/*     */ 
/*     */   public boolean equalsPath(Path par1PathEntity)
/*     */   {
/* 108 */     if (par1PathEntity == null)
/*     */     {
/* 110 */       return false;
/*     */     }
/*     */ 
/* 113 */     if (par1PathEntity.points.length != this.points.length)
/*     */     {
/* 115 */       return false;
/*     */     }
/*     */ 
/* 118 */     for (int i = 0; i < this.points.length; i++)
/*     */     {
/* 120 */       if ((this.points[i].xCoord != par1PathEntity.points[i].xCoord) || (this.points[i].yCoord != par1PathEntity.points[i].yCoord) || (this.points[i].zCoord != par1PathEntity.points[i].zCoord))
/*     */       {
/* 122 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 126 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isDestinationSame(AABBPool par1Vec3D)
/*     */   {
/* 131 */     PathNode pathpoint = getFinalPathPoint();
/*     */ 
/* 133 */     if (pathpoint == null)
/*     */     {
/* 135 */       return false;
/*     */     }
/*     */ 
/* 139 */     return (pathpoint.xCoord == (int)par1Vec3D.listAABB) && (pathpoint.zCoord == (int)par1Vec3D.maxPoolIndex);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.Path
 * JD-Core Version:    0.6.2
 */