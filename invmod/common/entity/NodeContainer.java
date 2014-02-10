/*     */ package invmod.common.entity;
/*     */ 
/*     */ public class NodeContainer
/*     */ {
/*     */   private PathNode[] pathPoints;
/*     */   private int count;
/*     */ 
/*     */   public NodeContainer()
/*     */   {
/*  11 */     this.pathPoints = new PathNode[1024];
/*  12 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   public PathNode addPoint(PathNode pathpoint)
/*     */   {
/*  17 */     if (pathpoint.index >= 0)
/*     */     {
/*  19 */       throw new IllegalStateException("OW KNOWS!");
/*     */     }
/*  21 */     if (this.count == this.pathPoints.length)
/*     */     {
/*  23 */       PathNode[] apathpoint = new PathNode[this.count << 1];
/*  24 */       System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
/*  25 */       this.pathPoints = apathpoint;
/*     */     }
/*  27 */     this.pathPoints[this.count] = pathpoint;
/*  28 */     pathpoint.index = this.count;
/*  29 */     sortBack(this.count++);
/*  30 */     return pathpoint;
/*     */   }
/*     */ 
/*     */   public void clearPath()
/*     */   {
/*  35 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   public PathNode dequeue()
/*     */   {
/*  40 */     PathNode pathpoint = this.pathPoints[0];
/*  41 */     this.pathPoints[0] = this.pathPoints[(--this.count)];
/*  42 */     this.pathPoints[this.count] = null;
/*  43 */     if (this.count > 0)
/*     */     {
/*  45 */       sortForward(0);
/*     */     }
/*  47 */     pathpoint.index = -1;
/*  48 */     return pathpoint;
/*     */   }
/*     */ 
/*     */   public void changeDistance(PathNode pathpoint, float f)
/*     */   {
/*  53 */     float f1 = pathpoint.distanceToTarget;
/*  54 */     pathpoint.distanceToTarget = f;
/*  55 */     if (f < f1)
/*     */     {
/*  57 */       sortBack(pathpoint.index);
/*     */     }
/*     */     else
/*     */     {
/*  61 */       sortForward(pathpoint.index);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sortBack(int i)
/*     */   {
/*  67 */     PathNode pathpoint = this.pathPoints[i];
/*  68 */     float f = pathpoint.distanceToTarget;
/*     */ 
/*  71 */     while (i > 0)
/*     */     {
/*  75 */       int j = i - 1 >> 1;
/*  76 */       PathNode pathpoint1 = this.pathPoints[j];
/*  77 */       if (f >= pathpoint1.distanceToTarget)
/*     */       {
/*     */         break;
/*     */       }
/*  81 */       this.pathPoints[i] = pathpoint1;
/*  82 */       pathpoint1.index = i;
/*  83 */       i = j;
/*     */     }
/*     */ 
/*  86 */     this.pathPoints[i] = pathpoint;
/*  87 */     pathpoint.index = i;
/*     */   }
/*     */ 
/*     */   private void sortForward(int i)
/*     */   {
/*  92 */     PathNode pathpoint = this.pathPoints[i];
/*  93 */     float f = pathpoint.distanceToTarget;
/*     */     while (true)
/*     */     {
/*  96 */       int j = 1 + (i << 1);
/*  97 */       int k = j + 1;
/*  98 */       if (j >= this.count)
/*     */       {
/*     */         break;
/*     */       }
/* 102 */       PathNode pathpoint1 = this.pathPoints[j];
/* 103 */       float f1 = pathpoint1.distanceToTarget;
/*     */       float f2;
/*     */       PathNode pathpoint2;
/*     */       float f2;
/* 106 */       if (k >= this.count)
/*     */       {
/* 108 */         PathNode pathpoint2 = null;
/* 109 */         f2 = (1.0F / 1.0F);
/*     */       }
/*     */       else
/*     */       {
/* 113 */         pathpoint2 = this.pathPoints[k];
/* 114 */         f2 = pathpoint2.distanceToTarget;
/*     */       }
/* 116 */       if (f1 < f2)
/*     */       {
/* 118 */         if (f1 >= f)
/*     */         {
/*     */           break;
/*     */         }
/* 122 */         this.pathPoints[i] = pathpoint1;
/* 123 */         pathpoint1.index = i;
/* 124 */         i = j;
/*     */       }
/*     */       else {
/* 127 */         if (f2 >= f)
/*     */         {
/*     */           break;
/*     */         }
/* 131 */         this.pathPoints[i] = pathpoint2;
/* 132 */         pathpoint2.index = i;
/* 133 */         i = k;
/*     */       }
/*     */     }
/* 136 */     this.pathPoints[i] = pathpoint;
/* 137 */     pathpoint.index = i;
/*     */   }
/*     */ 
/*     */   public boolean isPathEmpty()
/*     */   {
/* 142 */     return this.count == 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.NodeContainer
 * JD-Core Version:    0.6.2
 */