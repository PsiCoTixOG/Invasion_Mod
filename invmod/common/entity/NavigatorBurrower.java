/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.PosRotate3D;
/*     */ import net.minecraft.entity.Entity;
/*     */ 
/*     */ public class NavigatorBurrower extends NavigatorParametric
/*     */ {
/*     */   protected PathNode nextNode;
/*     */   protected PathNode prevNode;
/*     */   protected PathNode[] prevSegmentNodes;
/*     */   protected PathNode[] activeSegmentNodes;
/*     */   protected PathNode[] nextSegmentNodes;
/*     */   protected int[] segmentPathIndices;
/*     */   protected int[] segmentTime;
/*     */   protected int[] segmentOffsets;
/*     */   protected float timePerTick;
/*     */   protected Path lastPath;
/*     */   protected boolean nodeChanged;
/*     */ 
/*     */   public NavigatorBurrower(EntityIMBurrower entity, IPathSource pathSource, int segments, int offset)
/*     */   {
/*  14 */     super(entity, pathSource);
/*  15 */     this.timePerTick = 0.05F;
/*  16 */     this.prevSegmentNodes = new PathNode[segments];
/*  17 */     this.activeSegmentNodes = new PathNode[segments];
/*  18 */     this.nextSegmentNodes = new PathNode[segments];
/*  19 */     this.segmentPathIndices = new int[segments];
/*  20 */     this.segmentTime = new int[segments];
/*  21 */     this.segmentOffsets = new int[segments];
/*  22 */     this.nodeChanged = false;
/*     */ 
/*  24 */     for (int i = 0; i < this.segmentOffsets.length; i++)
/*  25 */       this.segmentOffsets[i] = ((i + 1) * offset);
/*     */   }
/*     */ 
/*     */   protected PosRotate3D entityPositionAtParam(int param)
/*     */   {
/*  35 */     return calcAbsolutePositionAndRotation(param * this.timePerTick, this.prevNode, this.activeNode, this.nextNode);
/*     */   }
/*     */ 
/*     */   protected PosRotate3D positionAtTime(int tick, PathNode start, PathNode middle, PathNode end)
/*     */   {
/*  41 */     PosRotate3D pos = calcPositionAndRotation(tick * this.timePerTick, start, middle, end);
/*  42 */     pos.setPosX(pos.getPosX() + middle.xCoord);
/*  43 */     pos.setPosY(pos.getPosY() + middle.yCoord);
/*  44 */     pos.setPosZ(pos.getPosZ() + middle.zCoord);
/*  45 */     return pos;
/*     */   }
/*     */ 
/*     */   protected boolean isReadyForNextNode(int ticks)
/*     */   {
/*  51 */     return ticks * this.timePerTick >= 1.0D;
/*     */   }
/*     */ 
/*     */   protected void pathFollow(int time)
/*     */   {
/*  58 */     int nextFrontIndex = this.path.getCurrentPathIndex() + 2;
/*  59 */     if (isReadyForNextNode(time))
/*     */     {
/*  61 */       if (nextFrontIndex < this.path.getCurrentPathLength())
/*     */       {
/*  63 */         this.timeParam = 0;
/*  64 */         this.path.setCurrentPathIndex(nextFrontIndex - 1);
/*  65 */         this.prevNode = this.activeNode;
/*  66 */         this.activeNode = this.nextNode;
/*  67 */         this.nextNode = this.path.getPathPointFromIndex(nextFrontIndex);
/*  68 */         this.nodeChanged = true;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  73 */       this.timeParam = time;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doSegmentFollowTo(int ticks, int segmentIndex)
/*     */   {
/*  80 */     ticks += this.segmentOffsets[segmentIndex];
/*  81 */     while (ticks <= 0) ticks += 20;
/*     */ 
/*  85 */     int nextFrontIndex = this.segmentPathIndices[segmentIndex] + 2;
/*  86 */     if (isReadyForNextNode(ticks))
/*     */     {
/*  88 */       if (nextFrontIndex < this.path.getCurrentPathLength())
/*     */       {
/*  90 */         this.segmentPathIndices[segmentIndex] = (nextFrontIndex - 1);
/*  91 */         this.prevSegmentNodes[segmentIndex] = this.activeSegmentNodes[segmentIndex];
/*  92 */         this.activeSegmentNodes[segmentIndex] = this.nextSegmentNodes[segmentIndex];
/*  93 */         if (this.segmentPathIndices[segmentIndex] >= 0)
/*  94 */           this.nextSegmentNodes[segmentIndex] = this.path.getPathPointFromIndex(nextFrontIndex);
/*     */         else {
/*  96 */           this.nextSegmentNodes[segmentIndex] = this.path.getPathPointFromIndex(0);
/*     */         }
/*  98 */         this.segmentTime[segmentIndex] = 0;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 103 */       this.segmentTime[segmentIndex] = ticks;
/*     */     }
/*     */ 
/* 107 */     if (this.segmentPathIndices[segmentIndex] >= 0)
/*     */     {
/* 109 */       PosRotate3D pos = positionAtTime(this.segmentTime[segmentIndex], this.prevSegmentNodes[segmentIndex], this.activeSegmentNodes[segmentIndex], this.nextSegmentNodes[segmentIndex]);
/* 110 */       ((EntityIMBurrower)this.theEntity).setSegment(segmentIndex, pos);
/* 111 */       if (this.segmentTime[segmentIndex] == 0)
/* 112 */         ((EntityIMBurrower)this.theEntity).setSegment(segmentIndex, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doMovementTo(int time)
/*     */   {
/* 122 */     PosRotate3D movePos = entityPositionAtParam(time);
/* 123 */     this.theEntity.moveEntity(movePos.getPosX() - this.theEntity.posX, movePos.getPosY() - this.theEntity.posY, movePos.getPosZ() - this.theEntity.posZ);
/* 124 */     ((EntityIMBurrower)this.theEntity).setHeadRotation(movePos);
/*     */ 
/* 126 */     if (this.nodeChanged)
/*     */     {
/* 128 */       ((EntityIMBurrower)this.theEntity).setHeadRotation(movePos);
/* 129 */       this.nodeChanged = false;
/*     */     }
/*     */ 
/* 133 */     if (Math.abs(this.theEntity.getDistanceSq(movePos.getPosX(), movePos.getPosY(), movePos.getPosZ())) < this.minMoveToleranceSq)
/*     */     {
/* 136 */       for (int segmentIndex = 0; segmentIndex < this.segmentPathIndices.length; segmentIndex++) {
/* 137 */         doSegmentFollowTo(time, segmentIndex);
/*     */       }
/* 139 */       this.timeParam = time;
/* 140 */       this.ticksStuck -= 1;
/*     */     }
/*     */     else
/*     */     {
/* 144 */       this.ticksStuck += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean noPath()
/*     */   {
/* 151 */     return (this.path == null) || (this.path.getCurrentPathIndex() >= this.path.getCurrentPathLength() - 2);
/*     */   }
/*     */ 
/*     */   public boolean setPath(Path newPath, float speed)
/*     */   {
/* 157 */     if ((newPath == null) || (newPath.getCurrentPathLength() < 2))
/*     */     {
/* 159 */       this.path = null;
/* 160 */       return false;
/*     */     }
/*     */ 
/* 163 */     if (this.path == null)
/*     */     {
/* 165 */       this.path = newPath;
/* 166 */       this.activeNode = this.path.getPathPointFromIndex(0);
/* 167 */       this.prevNode = this.activeNode;
/* 168 */       this.nextNode = this.path.getPathPointFromIndex(1);
/* 169 */       if (this.activeNode.action != PathAction.NONE) {
/* 170 */         this.nodeActionFinished = false;
/*     */       }
/* 172 */       for (int i = 0; i < this.segmentPathIndices.length; i++)
/*     */       {
/* 174 */         if (this.activeSegmentNodes[i] == null)
/*     */         {
/* 176 */           this.activeSegmentNodes[i] = this.activeNode;
/* 177 */           this.nextSegmentNodes[i] = this.activeNode;
/* 178 */           this.segmentPathIndices[i] = 0;
/* 179 */           this.segmentTime[i] = this.segmentOffsets[i];
/* 180 */           while (this.segmentTime[i] < 0)
/*     */           {
/* 182 */             this.segmentTime[i] += 20;
/* 183 */             this.segmentPathIndices[i] -= 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 189 */     int mainIndex = this.path.getCurrentPathIndex();
/* 190 */     if (newPath.getPathPointFromIndex(0).equals(this.activeNode))
/*     */     {
/* 192 */       if (this.segmentPathIndices.length > 0)
/*     */       {
/* 194 */         int lowestIndex = this.segmentPathIndices[(this.segmentPathIndices.length - 1)];
/* 195 */         if (lowestIndex < 0)
/* 196 */           lowestIndex = 0;
/* 197 */         this.path = extendPath(this.path, newPath, lowestIndex, mainIndex);
/* 198 */         mainIndex -= lowestIndex;
/* 199 */         this.path.setCurrentPathIndex(mainIndex);
/* 200 */         this.nextNode = this.path.getPathPointFromIndex(mainIndex + 1);
/* 201 */         for (int i = 0; i < this.segmentPathIndices.length; i++)
/*     */         {
/* 203 */           this.segmentPathIndices[i] -= lowestIndex;
/* 204 */           if (this.segmentPathIndices[i] == mainIndex)
/* 205 */             this.nextSegmentNodes[i] = this.nextNode;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 210 */         this.path = newPath;
/* 211 */         this.path.setCurrentPathIndex(0);
/* 212 */         this.nextNode = this.path.getPathPointFromIndex(1);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 217 */       this.path = newPath;
/* 218 */       this.activeNode = this.path.getPathPointFromIndex(0);
/* 219 */       this.prevNode = this.activeNode;
/* 220 */       this.nextNode = this.path.getPathPointFromIndex(1);
/* 221 */       if (this.activeNode.action != PathAction.NONE) {
/* 222 */         this.nodeActionFinished = false;
/*     */       }
/* 224 */       for (int i = 0; i < this.segmentPathIndices.length; i++)
/*     */       {
/* 226 */         if (this.activeSegmentNodes[i] == null)
/*     */         {
/* 228 */           this.activeSegmentNodes[i] = this.activeNode;
/* 229 */           this.nextSegmentNodes[i] = this.activeNode;
/* 230 */           this.segmentPathIndices[i] = 0;
/* 231 */           this.segmentTime[i] = this.segmentOffsets[i];
/* 232 */           while (this.segmentTime[i] < 0)
/*     */           {
/* 234 */             this.segmentTime[i] += 20;
/* 235 */             this.segmentPathIndices[i] -= 1;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 241 */     this.ticksStuck = 0;
/*     */ 
/* 243 */     if (this.noSunPathfind)
/*     */     {
/* 245 */       removeSunnyPath();
/*     */     }
/*     */ 
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */   private PosRotate3D calcAbsolutePositionAndRotation(float time, PathNode start, PathNode middle, PathNode end)
/*     */   {
/* 256 */     PosRotate3D pos = calcPositionAndRotation(time, start, middle, end);
/* 257 */     pos.setPosX(pos.getPosX() + middle.xCoord);
/* 258 */     pos.setPosY(pos.getPosY() + middle.yCoord);
/* 259 */     pos.setPosZ(pos.getPosZ() + middle.zCoord);
/* 260 */     return pos;
/*     */   }
/*     */ 
/*     */   private PosRotate3D calcPositionAndRotation(float time, PathNode start, PathNode middle, PathNode end)
/*     */   {
/* 270 */     int vX = end.xCoord - start.xCoord;
/* 271 */     int vY = end.yCoord - start.yCoord;
/* 272 */     int vZ = end.zCoord - start.zCoord;
/* 273 */     int hX = middle.xCoord != start.xCoord ? 1 : -1;
/* 274 */     int hY = middle.yCoord != start.yCoord ? 1 : -1;
/* 275 */     int hZ = middle.zCoord != start.zCoord ? 1 : -1;
/* 276 */     int gX = middle.xCoord != end.xCoord ? 1 : -1;
/* 277 */     int gY = middle.yCoord != end.yCoord ? 1 : -1;
/* 278 */     int gZ = middle.zCoord != end.zCoord ? 1 : -1;
/* 279 */     double xOffset = vX * -0.5D * hX;
/* 280 */     double yOffset = vY * -0.5D * hY;
/* 281 */     double zOffset = vZ * -0.5D * hZ;
/*     */ 
/* 283 */     double posX = 0.0D; double posY = 0.0D; double posZ = 0.0D;
/* 284 */     float rotX = 0.0F; float rotY = 0.0F; float rotZ = 0.0F;
/*     */ 
/* 286 */     if ((hX == 1) && (gX == 1))
/*     */     {
/* 288 */       posX = time * vX * 0.5D + (vX > 0 ? 0 : 1);
/* 289 */       posY = 0.5D;
/* 290 */       posZ = 0.5D;
/* 291 */       rotY = vX >= 1 ? 0.0F : 3.141593F;
/* 292 */       return new PosRotate3D(posX, posY, posZ, rotX, rotY, rotZ);
/*     */     }
/* 294 */     if ((hY == 1) && (gY == 1))
/*     */     {
/* 296 */       posY = time * vY * 0.5D + (vY > 0 ? 0 : 1);
/* 297 */       posX = 0.5D;
/* 298 */       posZ = 0.5D;
/* 299 */       return new PosRotate3D(posX, posY, posZ, rotX, rotY, rotZ);
/*     */     }
/* 301 */     if ((hZ == 1) && (gZ == 1))
/*     */     {
/* 303 */       posZ = time * vZ * 0.5D + (vZ > 0 ? 0 : 1);
/* 304 */       posY = 0.5D;
/* 305 */       posX = 0.5D;
/* 306 */       rotY = vZ * 3.141593F / 4.0F;
/* 307 */       return new PosRotate3D(posX, posY, posZ, rotX, rotY, rotZ);
/*     */     }
/*     */ 
/* 314 */     if (hX == 1)
/* 315 */       posX = vX * hX * Math.sin(time * 0.5D * 3.141592653589793D) * 0.5D + xOffset;
/*     */     else {
/* 317 */       posX = vX * hX * Math.cos(time * 0.5D * 3.141592653589793D) * 0.5D + xOffset;
/*     */     }
/* 319 */     if (hY == 1)
/* 320 */       posY = vY * hY * Math.sin(time * 0.5D * 3.141592653589793D) * 0.5D + yOffset;
/*     */     else {
/* 322 */       posY = vY * hY * Math.cos(time * 0.5D * 3.141592653589793D) * 0.5D + yOffset;
/*     */     }
/* 324 */     if (hZ == 1)
/* 325 */       posZ = vZ * hZ * Math.sin(time * 0.5D * 3.141592653589793D) * 0.5D + zOffset;
/*     */     else {
/* 327 */       posZ = vZ * hZ * Math.cos(time * 0.5D * 3.141592653589793D) * 0.5D + zOffset;
/*     */     }
/* 329 */     if (hX == 1)
/*     */     {
/* 331 */       rotY = vX == 1 ? 0.0F : 180.0F;
/* 332 */       if (gZ == 1)
/* 333 */         rotY += time * vZ * vX * 90.0F;
/* 334 */       else if (gY == 1)
/* 335 */         rotZ = time * vY * 90.0F;
/*     */     }
/* 337 */     else if (hY == 1)
/*     */     {
/* 339 */       if (gX == 1)
/*     */       {
/* 341 */         rotX = vX == 1 ? 0.0F : 180.0F;
/* 342 */         rotZ = 90 * vY + time * vX * -90.0F;
/*     */       }
/* 344 */       else if (gZ == 1)
/*     */       {
/* 346 */         rotX = 90.0F;
/* 347 */         rotY = vZ * (time * vY * -90.0F);
/* 348 */         rotZ = -90.0F;
/*     */       }
/*     */     }
/* 351 */     else if (hZ == 1)
/*     */     {
/* 353 */       if (gX == 1)
/*     */       {
/* 355 */         rotY = vZ * (90.0F + time * vX * -90.0F);
/*     */       }
/* 357 */       else if (gY == 1)
/*     */       {
/* 359 */         rotX = 90.0F;
/* 360 */         rotY = -vZ * (-90.0F + time * vY * -90.0F);
/* 361 */         rotZ = -90.0F;
/*     */       }
/*     */     }
/*     */ 
/* 365 */     posX += 0.5D;
/* 366 */     posY += 0.5D;
/* 367 */     posZ += 0.5D;
/*     */ 
/* 369 */     rotX /= 57.295799F;
/* 370 */     rotY /= 57.295799F;
/* 371 */     rotZ /= 57.295799F;
/* 372 */     return new PosRotate3D(posX, posY, posZ, rotX, rotY, rotZ);
/*     */   }
/*     */ 
/*     */   private PosRotate3D calcStraight(float time, PathNode start, PathNode end)
/*     */   {
/* 377 */     PosRotate3D segment = new PosRotate3D();
/* 378 */     segment.setPosX(start.xCoord + 0.5D + time * (end.xCoord - start.xCoord) * 0.5D);
/* 379 */     segment.setPosY(start.yCoord + time * (end.yCoord - start.yCoord) * 0.5D);
/* 380 */     segment.setPosZ(start.zCoord + 0.5D + time * (end.zCoord - start.zCoord * 0.5D));
/* 381 */     return segment;
/*     */   }
/*     */ 
/*     */   private Path extendPath(Path path1, Path path2, int lowerBoundP1, int upperBoundP1)
/*     */   {
/* 389 */     int k = upperBoundP1 - lowerBoundP1;
/* 390 */     PathNode[] newPoints = new PathNode[k + path2.getCurrentPathLength()];
/* 391 */     System.arraycopy(path1.points, lowerBoundP1, newPoints, 0, k);
/* 392 */     System.arraycopy(path2.points, 0, newPoints, k, path2.getCurrentPathLength());
/* 393 */     return new Path(newPoints, path2.getIntendedTarget());
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.NavigatorBurrower
 * JD-Core Version:    0.6.2
 */