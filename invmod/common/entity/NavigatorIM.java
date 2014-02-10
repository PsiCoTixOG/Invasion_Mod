/*      */ package invmod.common.entity;
/*      */ 
/*      */ import invmod.common.INotifyTask;
/*      */ import invmod.common.nexus.INexusAccess;
/*      */ import invmod.common.util.CoordsInt;
/*      */ import invmod.common.util.Distance;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockEndPortal;
/*      */ import net.minecraft.block.BlockPistonExtension;
/*      */ import net.minecraft.block.material.MaterialLogic;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.src.nm;
/*      */ import net.minecraft.util.AABBPool;
/*      */ import net.minecraft.util.LongHashMapEntry;
/*      */ import net.minecraft.world.ColorizerGrass;
/*      */ import net.minecraft.world.EnumGameType;
/*      */ import net.minecraft.world.PortalPosition;
/*      */ 
/*      */ public class NavigatorIM
/*      */   implements INotifyTask, INavigation
/*      */ {
/*      */   protected static final int XZPATH_HORIZONTAL_SEARCH = 1;
/*      */   protected static final double ENTITY_TRACKING_TOLERANCE = 0.1D;
/*      */   protected static final double MINIMUM_PROGRESS = 0.01D;
/*      */   protected final EntityIMLiving theEntity;
/*      */   protected IPathSource pathSource;
/*      */   protected Path path;
/*      */   protected PathNode activeNode;
/*      */   protected AABBPool entityCentre;
/*      */   protected nm pathEndEntity;
/*      */   protected AABBPool pathEndEntityLastPos;
/*      */   protected float moveSpeed;
/*      */   protected float pathSearchLimit;
/*      */   protected boolean noSunPathfind;
/*      */   protected int totalTicks;
/*      */   protected AABBPool lastPos;
/*      */   private AABBPool holdingPos;
/*      */   protected boolean nodeActionFinished;
/*      */   private boolean canSwim;
/*      */   protected boolean waitingForNotify;
/*      */   protected boolean actionCleared;
/*      */   protected double lastDistance;
/*      */   protected int ticksStuck;
/*      */   private boolean maintainPosOnWait;
/*      */   private int lastActionResult;
/*      */   private boolean haltMovement;
/*      */   private boolean autoPathToEntity;
/*      */ 
/*      */   public NavigatorIM(EntityIMLiving entity, IPathSource pathSource)
/*      */   {
/*   48 */     this.theEntity = entity;
/*   49 */     this.pathSource = pathSource;
/*   50 */     this.noSunPathfind = false;
/*   51 */     this.lastPos = AABBPool.a(0.0D, 0.0D, 0.0D);
/*   52 */     this.pathEndEntityLastPos = AABBPool.a(0.0D, 0.0D, 0.0D);
/*   53 */     this.lastDistance = 0.0D;
/*   54 */     this.ticksStuck = 0;
/*   55 */     this.canSwim = false;
/*   56 */     this.waitingForNotify = false;
/*   57 */     this.actionCleared = true;
/*   58 */     this.nodeActionFinished = true;
/*   59 */     this.maintainPosOnWait = false;
/*   60 */     this.haltMovement = false;
/*   61 */     this.lastActionResult = 0;
/*   62 */     this.autoPathToEntity = false;
/*      */   }
/*      */ 
/*      */   public PathAction getCurrentWorkingAction()
/*      */   {
/*   68 */     if ((!this.nodeActionFinished) && (!noPath())) {
/*   69 */       return this.activeNode.action;
/*      */     }
/*   71 */     return PathAction.NONE;
/*      */   }
/*      */ 
/*      */   protected boolean isMaintainingPos()
/*      */   {
/*   76 */     return this.maintainPosOnWait;
/*      */   }
/*      */ 
/*      */   protected void setNoMaintainPos()
/*      */   {
/*   81 */     this.maintainPosOnWait = false;
/*      */   }
/*      */ 
/*      */   protected void setMaintainPosOnWait(AABBPool pos)
/*      */   {
/*   86 */     this.holdingPos = pos;
/*   87 */     this.maintainPosOnWait = true;
/*      */   }
/*      */ 
/*      */   public void setSpeed(float par1)
/*      */   {
/*   93 */     this.moveSpeed = par1;
/*      */   }
/*      */ 
/*      */   public boolean isAutoPathingToEntity()
/*      */   {
/*   98 */     return this.autoPathToEntity;
/*      */   }
/*      */ 
/*      */   public nm getTargetEntity()
/*      */   {
/*  104 */     return this.pathEndEntity;
/*      */   }
/*      */ 
/*      */   public Path getPathToXYZ(double x, double y, double z, float targetRadius)
/*      */   {
/*  110 */     if (!canNavigate()) {
/*  111 */       return null;
/*      */     }
/*  113 */     return createPath(this.theEntity, LongHashMapEntry.c(x), (int)y, LongHashMapEntry.c(z), targetRadius);
/*      */   }
/*      */ 
/*      */   public boolean tryMoveToXYZ(double x, double y, double z, float targetRadius, float speed)
/*      */   {
/*  119 */     this.ticksStuck = 0;
/*  120 */     Path newPath = getPathToXYZ(LongHashMapEntry.c(x), (int)y, LongHashMapEntry.c(z), targetRadius);
/*  121 */     if (newPath != null) {
/*  122 */       return setPath(newPath, speed);
/*      */     }
/*  124 */     return false;
/*      */   }
/*      */ 
/*      */   public Path getPathTowardsXZ(double x, double z, int min, int max, int verticalRange)
/*      */   {
/*  134 */     if (canNavigate())
/*      */     {
/*  136 */       AABBPool target = findValidPointNear(x, z, min, max, verticalRange);
/*  137 */       if (target != null)
/*      */       {
/*  139 */         Path entityPath = getPathToXYZ(target.listAABB, target.nextPoolIndex, target.maxPoolIndex, 0.0F);
/*  140 */         if (entityPath != null)
/*  141 */           return entityPath;
/*      */       }
/*      */     }
/*  144 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean tryMoveTowardsXZ(double x, double z, int min, int max, int verticalRange, float speed)
/*      */   {
/*  150 */     this.ticksStuck = 0;
/*  151 */     Path newPath = getPathTowardsXZ(LongHashMapEntry.c(x), LongHashMapEntry.c(z), min, max, verticalRange);
/*  152 */     if (newPath != null) {
/*  153 */       return setPath(newPath, speed);
/*      */     }
/*  155 */     return false;
/*      */   }
/*      */ 
/*      */   public Path getPathToEntity(nm targetEntity, float targetRadius)
/*      */   {
/*  161 */     if (!canNavigate()) {
/*  162 */       return null;
/*      */     }
/*  164 */     return createPath(this.theEntity, LongHashMapEntry.c(targetEntity.u), LongHashMapEntry.c(targetEntity.E.b), LongHashMapEntry.c(targetEntity.w), targetRadius);
/*      */   }
/*      */ 
/*      */   public boolean tryMoveToEntity(nm targetEntity, float targetRadius, float speed)
/*      */   {
/*  170 */     Path newPath = getPathToEntity(targetEntity, targetRadius);
/*  171 */     if (newPath != null)
/*      */     {
/*  173 */       if (setPath(newPath, speed))
/*      */       {
/*  175 */         this.pathEndEntity = targetEntity;
/*  176 */         return true;
/*      */       }
/*      */ 
/*  180 */       this.pathEndEntity = null;
/*  181 */       return false;
/*      */     }
/*      */ 
/*  186 */     return false;
/*      */   }
/*      */ 
/*      */   public void autoPathToEntity(nm target)
/*      */   {
/*  208 */     this.autoPathToEntity = true;
/*  209 */     this.pathEndEntity = target;
/*      */   }
/*      */ 
/*      */   public boolean setPath(Path newPath, float speed)
/*      */   {
/*  218 */     if (newPath == null)
/*      */     {
/*  220 */       this.path = null;
/*  221 */       this.theEntity.onPathSet();
/*  222 */       return false;
/*      */     }
/*      */ 
/*  225 */     this.moveSpeed = speed;
/*  226 */     this.lastDistance = getDistanceToActiveNode();
/*  227 */     this.ticksStuck = 0;
/*  228 */     resetStatus();
/*      */ 
/*  230 */     CoordsInt size = this.theEntity.getCollideSize();
/*  231 */     this.entityCentre = AABBPool.a(size.getXCoord() * 0.5D, 0.0D, size.getZCoord() * 0.5D);
/*      */ 
/*  233 */     this.path = newPath;
/*  234 */     this.activeNode = this.path.getPathPointFromIndex(this.path.getCurrentPathIndex());
/*      */ 
/*  236 */     if (this.activeNode.action != PathAction.NONE)
/*      */     {
/*  238 */       this.nodeActionFinished = false;
/*      */     }
/*  242 */     else if ((size.getXCoord() <= 1) && (size.getZCoord() <= 1))
/*      */     {
/*  244 */       this.path.incrementPathIndex();
/*  245 */       if (!this.path.isFinished())
/*      */       {
/*  247 */         this.activeNode = this.path.getPathPointFromIndex(this.path.getCurrentPathIndex());
/*  248 */         if (this.activeNode.action != PathAction.NONE)
/*      */         {
/*  250 */           this.nodeActionFinished = false;
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  256 */       while (this.theEntity.getDistanceSq(this.activeNode.xCoord + this.entityCentre.listAABB, this.activeNode.yCoord + this.entityCentre.nextPoolIndex, this.activeNode.zCoord + this.entityCentre.maxPoolIndex) < this.theEntity.width)
/*      */       {
/*  258 */         this.path.incrementPathIndex();
/*  259 */         if (!this.path.isFinished())
/*      */         {
/*  262 */           this.activeNode = this.path.getPathPointFromIndex(this.path.getCurrentPathIndex());
/*  263 */           if (this.activeNode.action != PathAction.NONE)
/*      */           {
/*  265 */             this.nodeActionFinished = false;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  272 */     if (this.noSunPathfind)
/*      */     {
/*  274 */       removeSunnyPath();
/*      */     }
/*      */ 
/*  277 */     this.theEntity.onPathSet();
/*  278 */     return true;
/*      */   }
/*      */ 
/*      */   public Path getPath()
/*      */   {
/*  287 */     return this.path;
/*      */   }
/*      */ 
/*      */   public boolean isWaitingForTask()
/*      */   {
/*  293 */     return this.waitingForNotify;
/*      */   }
/*      */ 
/*      */   public void onUpdateNavigation()
/*      */   {
/*  299 */     this.totalTicks += 1;
/*  300 */     if (this.autoPathToEntity)
/*      */     {
/*  302 */       updateAutoPathToEntity();
/*      */     }
/*      */ 
/*  305 */     if (noPath())
/*      */     {
/*  307 */       noPathFollow();
/*  308 */       return;
/*      */     }
/*      */ 
/*  312 */     if (this.waitingForNotify)
/*      */     {
/*  314 */       if (isMaintainingPos()) {
/*  315 */         this.theEntity.getMoveHelper().a(this.holdingPos.listAABB, this.holdingPos.nextPoolIndex, this.holdingPos.maxPoolIndex, this.moveSpeed);
/*      */       }
/*  317 */       return;
/*      */     }
/*      */ 
/*  321 */     if ((canNavigate()) && (this.nodeActionFinished))
/*      */     {
/*  323 */       double distance = getDistanceToActiveNode();
/*  324 */       if (this.lastDistance - distance > 0.01D)
/*      */       {
/*  326 */         this.lastDistance = distance;
/*  327 */         this.ticksStuck -= 1;
/*      */       }
/*      */       else
/*      */       {
/*  331 */         this.ticksStuck += 1;
/*      */       }
/*      */ 
/*  334 */       int pathIndex = this.path.getCurrentPathIndex();
/*  335 */       pathFollow();
/*  336 */       if (noPath()) {
/*  337 */         return;
/*      */       }
/*  339 */       if (this.path.getCurrentPathIndex() != pathIndex)
/*      */       {
/*  341 */         this.lastDistance = getDistanceToActiveNode();
/*  342 */         this.ticksStuck = 0;
/*  343 */         this.activeNode = this.path.getPathPointFromIndex(this.path.getCurrentPathIndex());
/*  344 */         if (this.activeNode.action != PathAction.NONE) {
/*  345 */           this.nodeActionFinished = false;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  350 */     if (this.nodeActionFinished)
/*      */     {
/*  352 */       if (!isPositionClearFrom(this.theEntity.getXCoord(), this.theEntity.getYCoord(), this.theEntity.getZCoord(), this.activeNode.xCoord, this.activeNode.yCoord, this.activeNode.zCoord, this.theEntity))
/*      */       {
/*  354 */         if (this.theEntity.onPathBlocked(this.path, this))
/*      */         {
/*  356 */           setDoingTaskAndHoldOnPoint();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  364 */       if (!this.haltMovement)
/*      */       {
/*  367 */         if ((this.pathEndEntity != null) && (this.pathEndEntity.v - this.theEntity.posY <= 0.0D) && (this.theEntity.getDistanceSq(this.pathEndEntity.u, this.pathEndEntity.E.b, this.pathEndEntity.w) < 4.5D))
/*  368 */           this.theEntity.getMoveHelper().a(this.pathEndEntity.u, this.pathEndEntity.E.b, this.pathEndEntity.w, this.moveSpeed);
/*      */         else {
/*  370 */           this.theEntity.getMoveHelper().a(this.activeNode.xCoord + this.entityCentre.listAABB, this.activeNode.yCoord + this.entityCentre.nextPoolIndex, this.activeNode.zCoord + this.entityCentre.maxPoolIndex, this.moveSpeed);
/*      */         }
/*      */       }
/*      */       else {
/*  374 */         this.haltMovement = false;
/*      */       }
/*      */ 
/*      */     }
/*  379 */     else if (!handlePathAction()) {
/*  380 */       clearPath();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void notifyTask(int result)
/*      */   {
/*  387 */     this.waitingForNotify = false;
/*  388 */     this.lastActionResult = result;
/*      */   }
/*      */ 
/*      */   public int getLastActionResult()
/*      */   {
/*  394 */     return this.lastActionResult;
/*      */   }
/*      */ 
/*      */   public boolean noPath()
/*      */   {
/*  403 */     return (this.path == null) || (this.path.isFinished());
/*      */   }
/*      */ 
/*      */   public int getStuckTime()
/*      */   {
/*  413 */     return this.ticksStuck;
/*      */   }
/*      */ 
/*      */   public float getLastPathDistanceToTarget()
/*      */   {
/*  422 */     if (noPath())
/*      */     {
/*  424 */       if ((this.path != null) && (this.path.getIntendedTarget() != null))
/*      */       {
/*  426 */         PathNode node = this.path.getIntendedTarget();
/*  427 */         return (float)this.theEntity.getDistance(node.xCoord, node.yCoord, node.zCoord);
/*      */       }
/*  429 */       return 0.0F;
/*      */     }
/*      */ 
/*  433 */     return this.path.getFinalPathPoint().distanceTo(this.path.getIntendedTarget());
/*      */   }
/*      */ 
/*      */   public void clearPath()
/*      */   {
/*  443 */     this.path = null;
/*  444 */     this.autoPathToEntity = false;
/*  445 */     resetStatus();
/*      */   }
/*      */ 
/*      */   public void haltForTick()
/*      */   {
/*  451 */     this.haltMovement = true;
/*      */   }
/*      */ 
/*      */   public String getStatus()
/*      */   {
/*  457 */     String s = "";
/*  458 */     if (this.autoPathToEntity)
/*      */     {
/*  460 */       s = s + "Auto:";
/*      */     }
/*  462 */     if (noPath())
/*      */     {
/*  464 */       s = s + "NoPath:";
/*  465 */       return s;
/*      */     }
/*  467 */     s = s + "Pathing:";
/*  468 */     s = s + "Node[" + this.path.getCurrentPathIndex() + "/" + this.path.getCurrentPathLength() + "]:";
/*  469 */     if ((!this.nodeActionFinished) && (this.activeNode != null))
/*      */     {
/*  471 */       s = s + "Action[" + this.activeNode.action + "]:";
/*      */     }
/*  473 */     return s;
/*      */   }
/*      */ 
/*      */   protected Path createPath(EntityIMLiving entity, nm target, float targetRadius)
/*      */   {
/*  478 */     return createPath(entity, LongHashMapEntry.c(target.u), (int)target.v, LongHashMapEntry.c(target.w), targetRadius);
/*      */   }
/*      */ 
/*      */   protected Path createPath(EntityIMLiving entity, int x, int y, int z, float targetRadius)
/*      */   {
/*  483 */     this.theEntity.setCurrentTargetPos(new CoordsInt(x, y, z));
/*  484 */     EnumGameType terrainCache = getChunkCache(entity.getXCoord(), entity.getYCoord(), entity.getZCoord(), x, y, z, 16.0F);
/*  485 */     INexusAccess nexus = entity.getNexus();
/*  486 */     if (nexus != null)
/*      */     {
/*  488 */       terrainCache = nexus.getAttackerAI().wrapEntityData(terrainCache);
/*      */     }
/*  490 */     float maxSearchRange = 12.0F + (float)Distance.distanceBetween(entity, x, y, z);
/*  491 */     if (this.pathSource.canPathfindNice(IPathSource.PathPriority.HIGH, maxSearchRange, this.pathSource.getSearchDepth(), this.pathSource.getQuickFailDepth())) {
/*  492 */       return this.pathSource.createPath(entity, x, y, z, targetRadius, maxSearchRange, terrainCache);
/*      */     }
/*  494 */     return null;
/*      */   }
/*      */ 
/*      */   protected void pathFollow()
/*      */   {
/*  514 */     AABBPool vec3d = getEntityPosition();
/*  515 */     int maxNextLegIndex = this.path.getCurrentPathIndex() - 1;
/*      */ 
/*  518 */     PathNode nextPoint = this.path.getPathPointFromIndex(this.path.getCurrentPathIndex());
/*  519 */     if ((nextPoint.yCoord == (int)vec3d.nextPoolIndex) && (maxNextLegIndex < this.path.getCurrentPathLength() - 1))
/*      */     {
/*  521 */       maxNextLegIndex++;
/*      */ 
/*  523 */       boolean canConsolidate = true;
/*  524 */       int prevIndex = maxNextLegIndex - 2;
/*  525 */       if ((prevIndex >= 0) && (this.path.getPathPointFromIndex(prevIndex).action != PathAction.NONE)) {
/*  526 */         canConsolidate = false;
/*      */       }
/*  528 */       if ((canConsolidate) && (this.theEntity.canStandAt(this.theEntity.q, LongHashMapEntry.c(this.theEntity.posX), LongHashMapEntry.c(this.theEntity.posY), LongHashMapEntry.c(this.theEntity.posZ))))
/*      */       {
/*  531 */         while ((maxNextLegIndex < this.path.getCurrentPathLength() - 1) && (this.path.getPathPointFromIndex(maxNextLegIndex).yCoord == (int)vec3d.nextPoolIndex) && (this.path.getPathPointFromIndex(maxNextLegIndex).action == PathAction.NONE))
/*      */         {
/*  533 */           maxNextLegIndex++;
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  539 */     float fa = this.theEntity.width * 0.5F;
/*  540 */     fa *= fa;
/*  541 */     for (int j = this.path.getCurrentPathIndex(); j <= maxNextLegIndex; j++)
/*      */     {
/*  543 */       if (vec3d.e(this.path.getPositionAtIndex(this.theEntity, j)) < fa)
/*      */       {
/*  545 */         this.path.setCurrentPathIndex(j + 1);
/*      */       }
/*      */     }
/*      */ 
/*  549 */     int xSize = (int)Math.ceil(this.theEntity.width);
/*  550 */     int ySize = (int)this.theEntity.height + 1;
/*  551 */     int zSize = xSize;
/*  552 */     int index = maxNextLegIndex;
/*      */ 
/*  557 */     while (index > this.path.getCurrentPathIndex())
/*      */     {
/*  560 */       if (isDirectPathBetweenPoints(vec3d, this.path.getPositionAtIndex(this.theEntity, index), xSize, ySize, zSize)) {
/*      */         break;
/*      */       }
/*  563 */       index--;
/*      */     }
/*      */ 
/*  568 */     for (int i = this.path.getCurrentPathIndex() + 1; i < index; i++)
/*      */     {
/*  570 */       if (this.path.getPathPointFromIndex(i).action != PathAction.NONE)
/*      */       {
/*  572 */         index = i;
/*  573 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  578 */     if (this.path.getCurrentPathIndex() < index)
/*  579 */       this.path.setCurrentPathIndex(index);
/*      */   }
/*      */ 
/*      */   protected void noPathFollow()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void updateAutoPathToEntity()
/*      */   {
/*  588 */     if (this.pathEndEntity == null)
/*      */       return;
/*      */     boolean wantsUpdate;
/*      */     boolean wantsUpdate;
/*  592 */     if (noPath())
/*      */     {
/*  594 */       wantsUpdate = true;
/*      */     }
/*      */     else
/*      */     {
/*  600 */       double d1 = Distance.distanceBetween(this.pathEndEntity, this.pathEndEntityLastPos);
/*  601 */       double d2 = 6.0D + Distance.distanceBetween(this.theEntity, this.pathEndEntityLastPos);
/*      */       boolean wantsUpdate;
/*  602 */       if (d1 / d2 > 0.1D)
/*  603 */         wantsUpdate = true;
/*      */       else {
/*  605 */         wantsUpdate = false;
/*      */       }
/*      */     }
/*  608 */     if (wantsUpdate)
/*      */     {
/*  610 */       Path newPath = getPathToEntity(this.pathEndEntity, 0.0F);
/*  611 */       if (newPath != null)
/*      */       {
/*  613 */         if (setPath(newPath, this.moveSpeed))
/*      */         {
/*  615 */           this.pathEndEntityLastPos = AABBPool.a(this.pathEndEntity.u, this.pathEndEntity.v, this.pathEndEntity.w);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected double getDistanceToActiveNode()
/*      */   {
/*  623 */     if (this.activeNode != null)
/*      */     {
/*  625 */       double dX = this.activeNode.xCoord + 0.5D - this.theEntity.posX;
/*  626 */       double dY = this.activeNode.yCoord - this.theEntity.posY;
/*  627 */       double dZ = this.activeNode.zCoord + 0.5D - this.theEntity.posZ;
/*  628 */       return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*      */     }
/*  630 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   protected boolean handlePathAction()
/*      */   {
/*  639 */     this.nodeActionFinished = true;
/*  640 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean setDoingTask()
/*      */   {
/*  645 */     this.waitingForNotify = true;
/*  646 */     this.actionCleared = false;
/*  647 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean setDoingTaskAndHold()
/*      */   {
/*  652 */     this.waitingForNotify = true;
/*  653 */     this.actionCleared = false;
/*  654 */     setMaintainPosOnWait(AABBPool.a(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ));
/*  655 */     this.theEntity.setIsHoldingIntoLadder(true);
/*  656 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean setDoingTaskAndHoldOnPoint()
/*      */   {
/*  661 */     this.waitingForNotify = true;
/*  662 */     this.actionCleared = false;
/*  663 */     setMaintainPosOnWait(AABBPool.a(this.activeNode.getXCoord() + 0.5D, this.activeNode.getYCoord(), this.activeNode.getZCoord() + 0.5D));
/*  664 */     this.theEntity.setIsHoldingIntoLadder(true);
/*  665 */     return true;
/*      */   }
/*      */ 
/*      */   protected void resetStatus()
/*      */   {
/*  670 */     setNoMaintainPos();
/*  671 */     this.theEntity.setIsHoldingIntoLadder(false);
/*  672 */     this.nodeActionFinished = true;
/*  673 */     this.actionCleared = true;
/*  674 */     this.waitingForNotify = false;
/*      */   }
/*      */ 
/*      */   protected AABBPool getEntityPosition()
/*      */   {
/*  679 */     return AABBPool.a(this.theEntity.posX, getPathableYPos(), this.theEntity.posZ);
/*      */   }
/*      */ 
/*      */   protected EntityIMLiving getEntity()
/*      */   {
/*  684 */     return this.theEntity;
/*      */   }
/*      */ 
/*      */   private int getPathableYPos()
/*      */   {
/*  692 */     if ((!this.theEntity.isWet()) || (!this.canSwim))
/*      */     {
/*  694 */       return (int)(this.theEntity.E.b + 0.5D);
/*      */     }
/*      */ 
/*  697 */     int i = (int)this.theEntity.E.b;
/*  698 */     int j = this.theEntity.q.a(LongHashMapEntry.c(this.theEntity.posX), i, LongHashMapEntry.c(this.theEntity.posZ));
/*  699 */     int k = 0;
/*      */ 
/*  701 */     while ((j == BlockEndPortal.F.blockID) || (j == BlockEndPortal.G.blockID))
/*      */     {
/*  703 */       i++;
/*  704 */       j = this.theEntity.q.a(LongHashMapEntry.c(this.theEntity.posX), i, LongHashMapEntry.c(this.theEntity.posZ));
/*      */ 
/*  706 */       k++; if (k > 16)
/*      */       {
/*  708 */         return (int)this.theEntity.E.b;
/*      */       }
/*      */     }
/*      */ 
/*  712 */     return i;
/*      */   }
/*      */ 
/*      */   protected boolean canNavigate()
/*      */   {
/*  720 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean isInLiquid()
/*      */   {
/*  725 */     return (this.theEntity.isWet()) || (this.theEntity.handleWaterMovement());
/*      */   }
/*      */ 
/*      */   protected AABBPool findValidPointNear(double x, double z, int min, int max, int verticalRange)
/*      */   {
/*  743 */     double xOffset = x - this.theEntity.posX;
/*  744 */     double zOffset = z - this.theEntity.posZ;
/*  745 */     double h = Math.sqrt(xOffset * xOffset + zOffset * zOffset);
/*      */ 
/*  747 */     if (h < 0.5D) {
/*  748 */       return null;
/*      */     }
/*      */ 
/*  751 */     double distance = min + this.theEntity.aC().nextInt(max - min);
/*  752 */     int xi = LongHashMapEntry.c(xOffset * (distance / h) + this.theEntity.posX);
/*  753 */     int zi = LongHashMapEntry.c(zOffset * (distance / h) + this.theEntity.posZ);
/*  754 */     int y = LongHashMapEntry.c(this.theEntity.posY);
/*      */ 
/*  757 */     Path entityPath = null;
/*  758 */     for (int vertical = 0; vertical < verticalRange; vertical = vertical > 0 ? vertical * -1 : vertical * -1 + 1)
/*      */     {
/*  760 */       for (int i = -1; i <= 1; i++)
/*      */       {
/*  762 */         for (int j = -1; j <= 1; j++)
/*      */         {
/*  764 */           if (this.theEntity.canStandAtAndIsValid(this.theEntity.q, xi + i, y + vertical, zi + j))
/*      */           {
/*  766 */             return AABBPool.a(xi + i, y + vertical, zi + j);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  772 */     return null;
/*      */   }
/*      */ 
/*      */   protected void removeSunnyPath()
/*      */   {
/*  780 */     if (this.theEntity.q.l(LongHashMapEntry.c(this.theEntity.posX), (int)(this.theEntity.E.b + 0.5D), LongHashMapEntry.c(this.theEntity.posZ)))
/*      */     {
/*  782 */       return;
/*      */     }
/*      */ 
/*  785 */     for (int i = 0; i < this.path.getCurrentPathLength(); i++)
/*      */     {
/*  787 */       PathNode pathpoint = this.path.getPathPointFromIndex(i);
/*      */ 
/*  789 */       if (this.theEntity.q.l(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord))
/*      */       {
/*  791 */         this.path.setCurrentPathLength(i - 1);
/*  792 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isDirectPathBetweenPoints(AABBPool pos1, AABBPool pos2, int xSize, int ySize, int zSize)
/*      */   {
/*  799 */     int x = LongHashMapEntry.c(pos1.listAABB);
/*  800 */     int z = LongHashMapEntry.c(pos1.maxPoolIndex);
/*  801 */     double dX = pos2.listAABB - pos1.listAABB;
/*  802 */     double dZ = pos2.maxPoolIndex - pos1.maxPoolIndex;
/*  803 */     double dXZsq = dX * dX + dZ * dZ;
/*      */ 
/*  805 */     if (dXZsq < 1.0E-008D)
/*      */     {
/*  807 */       return false;
/*      */     }
/*      */ 
/*  810 */     double scale = 1.0D / Math.sqrt(dXZsq);
/*  811 */     dX *= scale;
/*  812 */     dZ *= scale;
/*  813 */     xSize += 2;
/*  814 */     zSize += 2;
/*      */ 
/*  816 */     if (!isSafeToStandAt(x, (int)pos1.nextPoolIndex, z, xSize, ySize, zSize, pos1, dX, dZ))
/*      */     {
/*  818 */       return false;
/*      */     }
/*      */ 
/*  821 */     xSize -= 2;
/*  822 */     zSize -= 2;
/*  823 */     double xIncrement = 1.0D / Math.abs(dX);
/*  824 */     double zIncrement = 1.0D / Math.abs(dZ);
/*  825 */     double xOffset = x * 1 - pos1.listAABB;
/*  826 */     double zOffset = z * 1 - pos1.maxPoolIndex;
/*      */ 
/*  828 */     if (dX >= 0.0D)
/*      */     {
/*  830 */       xOffset += 1.0D;
/*      */     }
/*      */ 
/*  833 */     if (dZ >= 0.0D)
/*      */     {
/*  835 */       zOffset += 1.0D;
/*      */     }
/*      */ 
/*  838 */     xOffset /= dX;
/*  839 */     zOffset /= dZ;
/*  840 */     byte xDirection = (byte)(dX >= 0.0D ? 1 : -1);
/*  841 */     byte zDirection = (byte)(dZ >= 0.0D ? 1 : -1);
/*  842 */     int x2 = LongHashMapEntry.c(pos2.listAABB);
/*  843 */     int z2 = LongHashMapEntry.c(pos2.maxPoolIndex);
/*  844 */     int xDiff = x2 - x;
/*      */ 
/*  846 */     for (int i = z2 - z; (xDiff * xDirection > 0) || (i * zDirection > 0); )
/*      */     {
/*  848 */       if (xOffset < zOffset)
/*      */       {
/*  850 */         xOffset += xIncrement;
/*  851 */         x += xDirection;
/*  852 */         xDiff = x2 - x;
/*      */       }
/*      */       else
/*      */       {
/*  856 */         zOffset += zIncrement;
/*  857 */         z += zDirection;
/*  858 */         i = z2 - z;
/*      */       }
/*      */ 
/*  861 */       if (!isSafeToStandAt(x, (int)pos1.nextPoolIndex, z, xSize, ySize, zSize, pos1, dX, dZ))
/*      */       {
/*  863 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  867 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean isSafeToStandAt(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, AABBPool entityPostion, double par8, double par10)
/*      */   {
/*  872 */     int i = xOffset - xSize / 2;
/*  873 */     int j = zOffset - zSize / 2;
/*      */ 
/*  875 */     if (!isPositionClear(i, yOffset, j, xSize, ySize, zSize, entityPostion, par8, par10))
/*      */     {
/*  877 */       return false;
/*      */     }
/*      */ 
/*  881 */     for (int k = i; k < i + xSize; k++)
/*      */     {
/*  883 */       for (int l = j; l < j + zSize; l++)
/*      */       {
/*  885 */         double d = k + 0.5D - entityPostion.listAABB;
/*  886 */         double d1 = l + 0.5D - entityPostion.maxPoolIndex;
/*      */ 
/*  888 */         if (d * par8 + d1 * par10 >= 0.0D)
/*      */         {
/*  893 */           int i1 = this.theEntity.q.a(k, yOffset - 1, l);
/*      */ 
/*  895 */           if (i1 <= 0)
/*      */           {
/*  897 */             return false;
/*      */           }
/*      */ 
/*  900 */           MaterialLogic material = BlockEndPortal.s[i1].cU;
/*      */ 
/*  902 */           if ((material == MaterialLogic.h) && (!this.theEntity.isWet()))
/*      */           {
/*  904 */             return false;
/*      */           }
/*      */ 
/*  907 */           if (material == MaterialLogic.i)
/*      */           {
/*  909 */             return false;
/*      */           }
/*      */ 
/*  912 */           if (!material.isSolid())
/*      */           {
/*  914 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  919 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean isPositionClear(int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, AABBPool entityPostion, double vecX, double vecZ)
/*      */   {
/*  924 */     for (int i = xOffset; i < xOffset + xSize; i++)
/*      */     {
/*  926 */       for (int j = yOffset; j < yOffset + ySize; j++)
/*      */       {
/*  928 */         for (int k = zOffset; k < zOffset + zSize; k++)
/*      */         {
/*  930 */           double d = i + 0.5D - entityPostion.listAABB;
/*  931 */           double d1 = k + 0.5D - entityPostion.maxPoolIndex;
/*      */ 
/*  933 */           if (d * vecX + d1 * vecZ >= 0.0D)
/*      */           {
/*  938 */             int l = this.theEntity.q.a(i, j, k);
/*      */ 
/*  940 */             if ((l > 0) && (!BlockEndPortal.s[l].b(this.theEntity.q, i, j, k)))
/*      */             {
/*  942 */               return false;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  948 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean isPositionClearFrom(int x1, int y1, int z1, int x2, int y2, int z2, EntityIMLiving entity)
/*      */   {
/*  953 */     if (y2 > y1)
/*      */     {
/*  955 */       int id = this.theEntity.q.a(x1, y1 + entity.getCollideSize().getYCoord(), z1);
/*  956 */       if ((id > 0) && (!BlockEndPortal.s[id].b(this.theEntity.q, x1, y1 + entity.getCollideSize().getYCoord(), z1)))
/*      */       {
/*  958 */         return false;
/*      */       }
/*      */     }
/*      */ 
/*  962 */     return isPositionClear(x2, y2, z2, entity);
/*      */   }
/*      */ 
/*      */   protected boolean isPositionClear(int x, int y, int z, EntityIMLiving entity)
/*      */   {
/*  967 */     CoordsInt size = entity.getCollideSize();
/*  968 */     return isPositionClear(x, y, z, size.getXCoord(), size.getYCoord(), size.getZCoord());
/*      */   }
/*      */ 
/*      */   protected boolean isPositionClear(int x, int y, int z, int xSize, int ySize, int zSize)
/*      */   {
/*  973 */     for (int i = x; i < x + xSize; i++)
/*      */     {
/*  975 */       for (int j = y; j < y + ySize; j++)
/*      */       {
/*  977 */         for (int k = z; k < z + zSize; k++)
/*      */         {
/*  979 */           int id = this.theEntity.q.a(i, j, k);
/*      */ 
/*  981 */           if ((id > 0) && (!BlockEndPortal.s[id].b(this.theEntity.q, i, j, k)))
/*      */           {
/*  983 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  989 */     return true;
/*      */   }
/*      */ 
/*      */   protected PortalPosition getChunkCache(int x1, int y1, int z1, int x2, int y2, int z2, float axisExpand)
/*      */   {
/*  994 */     int d = (int)axisExpand;
/*      */     int cX2;
/*      */     int cX2;
/*      */     int cX1;
/* 1001 */     if (x1 < x2)
/*      */     {
/* 1003 */       int cX1 = x1 - d;
/* 1004 */       cX2 = x2 + d;
/*      */     }
/*      */     else
/*      */     {
/* 1008 */       cX2 = x1 + d;
/* 1009 */       cX1 = x2 - d;
/*      */     }
/*      */     int cY2;
/*      */     int cY2;
/*      */     int cY1;
/* 1011 */     if (y1 < y2)
/*      */     {
/* 1013 */       int cY1 = y1 - d;
/* 1014 */       cY2 = y2 + d;
/*      */     }
/*      */     else
/*      */     {
/* 1018 */       cY2 = y1 + d;
/* 1019 */       cY1 = y2 - d;
/*      */     }
/*      */     int cZ2;
/*      */     int cZ2;
/*      */     int cZ1;
/* 1021 */     if (z1 < z2)
/*      */     {
/* 1023 */       int cZ1 = z1 - d;
/* 1024 */       cZ2 = z2 + d;
/*      */     }
/*      */     else
/*      */     {
/* 1028 */       cZ2 = z1 + d;
/* 1029 */       cZ1 = z2 - d;
/*      */     }
/* 1031 */     return new PortalPosition(this.theEntity.q, cX1, cY1, cZ1, cX2, cY2, cZ2, 0);
/*      */   }
/*      */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.NavigatorIM
 * JD-Core Version:    0.6.2
 */