/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IBlockAccessExtended;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.Distance;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public class NavigatorEngy extends NavigatorIM
/*     */ {
/*     */   private final EntityIMPigEngy pigEntity;
/*     */ 
/*     */   public NavigatorEngy(EntityIMPigEngy entity, IPathSource pathSource)
/*     */   {
/*  13 */     super(entity, pathSource);
/*  14 */     this.pigEntity = entity;
/*  15 */     setNoMaintainPos();
/*     */   }
/*     */ 
/*     */   protected Path createPath(EntityIMLiving entity, int x, int y, int z, float targetRadius)
/*     */   {
/*  21 */     EnumGameType terrainCache = getChunkCache(entity.getXCoord(), entity.getYCoord(), entity.getZCoord(), x, y, z, 16.0F);
/*  22 */     INexusAccess nexus = this.pigEntity.getNexus();
/*  23 */     if (nexus != null)
/*     */     {
/*  26 */       IBlockAccessExtended terrainCacheExt = nexus.getAttackerAI().wrapEntityData(terrainCache);
/*     */ 
/*  29 */       nexus.getAttackerAI().addScaffoldDataTo(terrainCacheExt);
/*  30 */       terrainCache = terrainCacheExt;
/*     */     }
/*  32 */     float maxSearchRange = 12.0F + (float)Distance.distanceBetween(entity, x, y, z);
/*  33 */     if (this.pathSource.canPathfindNice(IPathSource.PathPriority.HIGH, maxSearchRange, this.pathSource.getSearchDepth(), this.pathSource.getQuickFailDepth())) {
/*  34 */       return this.pathSource.createPath(entity, x, y, z, targetRadius, maxSearchRange, terrainCache);
/*     */     }
/*  36 */     return null;
/*     */   }
/*     */ 
/*     */   protected boolean handlePathAction()
/*     */   {
/*  55 */     if (!this.actionCleared)
/*     */     {
/*  57 */       resetStatus();
/*  58 */       if (getLastActionResult() != 0) {
/*  59 */         return false;
/*     */       }
/*  61 */       return true;
/*     */     }
/*     */ 
/*  64 */     if ((this.activeNode.action == PathAction.LADDER_UP_PX) || (this.activeNode.action == PathAction.LADDER_UP_NX) || (this.activeNode.action == PathAction.LADDER_UP_PZ) || (this.activeNode.action == PathAction.LADDER_UP_NZ))
/*     */     {
/*  68 */       if (this.pigEntity.getTerrainBuildEngy().askBuildLadder(this.activeNode, this))
/*  69 */         return setDoingTaskAndHold();
/*     */     }
/*  71 */     else if (this.activeNode.action == PathAction.BRIDGE)
/*     */     {
/*  73 */       if (this.pigEntity.getTerrainBuildEngy().askBuildBridge(this.activeNode, this))
/*  74 */         return setDoingTaskAndHold();
/*     */     }
/*  76 */     else if (this.activeNode.action == PathAction.SCAFFOLD_UP)
/*     */     {
/*  78 */       if (this.pigEntity.getTerrainBuildEngy().askBuildScaffoldLayer(this.activeNode, this))
/*  79 */         return setDoingTaskAndHoldOnPoint();
/*     */     }
/*  81 */     else if (this.activeNode.action == PathAction.LADDER_TOWER_UP_PX)
/*     */     {
/*  83 */       if (this.pigEntity.getTerrainBuildEngy().askBuildLadderTower(this.activeNode, 0, 1, this))
/*  84 */         return setDoingTaskAndHold();
/*     */     }
/*  86 */     else if (this.activeNode.action == PathAction.LADDER_TOWER_UP_NX)
/*     */     {
/*  88 */       if (this.pigEntity.getTerrainBuildEngy().askBuildLadderTower(this.activeNode, 1, 1, this))
/*  89 */         return setDoingTaskAndHold();
/*     */     }
/*  91 */     else if (this.activeNode.action == PathAction.LADDER_TOWER_UP_PZ)
/*     */     {
/*  93 */       if (this.pigEntity.getTerrainBuildEngy().askBuildLadderTower(this.activeNode, 2, 1, this))
/*  94 */         return setDoingTaskAndHold();
/*     */     }
/*  96 */     else if (this.activeNode.action == PathAction.LADDER_TOWER_UP_NZ)
/*     */     {
/*  98 */       if (this.pigEntity.getTerrainBuildEngy().askBuildLadderTower(this.activeNode, 3, 1, this)) {
/*  99 */         return setDoingTaskAndHold();
/*     */       }
/*     */     }
/* 102 */     this.nodeActionFinished = true;
/* 103 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.NavigatorEngy
 * JD-Core Version:    0.6.2
 */