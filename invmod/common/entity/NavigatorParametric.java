/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.PosRotate3D;
/*     */ 
/*     */ public abstract class NavigatorParametric extends NavigatorIM
/*     */ {
/*     */   protected double minMoveToleranceSq;
/*     */   protected int timeParam;
/*     */ 
/*     */   public NavigatorParametric(EntityIMLiving entity, IPathSource pathSource)
/*     */   {
/*  19 */     super(entity, pathSource);
/*  20 */     this.minMoveToleranceSq = 21.0D;
/*  21 */     this.timeParam = 0;
/*     */   }
/*     */ 
/*     */   public void onUpdateNavigation(int paramElapsed)
/*     */   {
/*  32 */     this.totalTicks += 1;
/*  33 */     if ((noPath()) || (this.waitingForNotify)) {
/*  34 */       return;
/*     */     }
/*  36 */     if ((canNavigate()) && (this.nodeActionFinished))
/*     */     {
/*  38 */       int pathIndex = this.path.getCurrentPathIndex();
/*  39 */       pathFollow(this.timeParam + paramElapsed);
/*  40 */       doMovementTo(this.timeParam);
/*     */ 
/*  42 */       if (this.path.getCurrentPathIndex() != pathIndex)
/*     */       {
/*  44 */         this.ticksStuck = 0;
/*  45 */         if (this.activeNode.action != PathAction.NONE) {
/*  46 */           this.nodeActionFinished = false;
/*     */         }
/*     */       }
/*     */     }
/*  50 */     if (this.nodeActionFinished)
/*     */     {
/*  52 */       if (!isPositionClear(this.activeNode.xCoord, this.activeNode.yCoord, this.activeNode.zCoord, this.theEntity))
/*     */       {
/*  54 */         if (this.theEntity.onPathBlocked(this.path, this))
/*     */         {
/*  56 */           setDoingTaskAndHold();
/*     */         }
/*     */         else
/*     */         {
/*  60 */           clearPath();
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  66 */       handlePathAction();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onUpdateNavigation()
/*     */   {
/*  76 */     onUpdateNavigation(1);
/*     */   }
/*     */ 
/*     */   protected void doMovementTo(int param)
/*     */   {
/*  84 */     PosRotate3D movePos = entityPositionAtParam(param);
/*  85 */     this.theEntity.moveEntity(movePos.getPosX(), movePos.getPosY(), movePos.getPosZ());
/*     */ 
/*  88 */     if (Math.abs(this.theEntity.getDistanceSq(movePos.getPosX(), movePos.getPosY(), movePos.getPosZ())) < this.minMoveToleranceSq)
/*     */     {
/*  90 */       this.timeParam = param;
/*  91 */       this.ticksStuck -= 1;
/*     */     }
/*     */     else
/*     */     {
/*  95 */       this.ticksStuck += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract PosRotate3D entityPositionAtParam(int paramInt);
/*     */ 
/*     */   protected abstract boolean isReadyForNextNode(int paramInt);
/*     */ 
/*     */   protected void pathFollow(int param)
/*     */   {
/* 115 */     int nextIndex = this.path.getCurrentPathIndex() + 1;
/* 116 */     if (isReadyForNextNode(param))
/*     */     {
/* 118 */       if (nextIndex < this.path.getCurrentPathLength())
/*     */       {
/* 120 */         this.timeParam = 0;
/* 121 */         this.path.setCurrentPathIndex(nextIndex);
/* 122 */         this.activeNode = this.path.getPathPointFromIndex(this.path.getCurrentPathIndex());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 127 */       this.timeParam = param;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void pathFollow()
/*     */   {
/* 137 */     pathFollow(0);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.NavigatorParametric
 * JD-Core Version:    0.6.2
 */