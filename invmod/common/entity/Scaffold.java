/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IPathfindable;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.Distance;
/*     */ import invmod.common.util.IPosition;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public class Scaffold
/*     */   implements IPathfindable, IPosition
/*     */ {
/*     */   private static final int MIN_SCAFFOLD_HEIGHT = 4;
/*     */   private int xCoord;
/*     */   private int yCoord;
/*     */   private int zCoord;
/*     */   private int targetHeight;
/*     */   private int orientation;
/*     */   private int[] platforms;
/*     */   private IPathfindable pathfindBase;
/*     */   private INexusAccess nexus;
/*     */   private float latestPercentCompleted;
/*     */   private float latestPercentIntact;
/*     */   private float initialCompletion;
/*     */ 
/*     */   public Scaffold(INexusAccess nexus)
/*     */   {
/*  35 */     this.nexus = nexus;
/*  36 */     this.initialCompletion = 0.01F;
/*  37 */     calcPlatforms();
/*     */   }
/*     */ 
/*     */   public Scaffold(int x, int y, int z, int height, INexusAccess nexus)
/*     */   {
/*  42 */     this.xCoord = x;
/*  43 */     this.yCoord = y;
/*  44 */     this.zCoord = z;
/*  45 */     this.targetHeight = height;
/*  46 */     this.latestPercentCompleted = 0.0F;
/*  47 */     this.latestPercentIntact = 0.0F;
/*  48 */     this.initialCompletion = 0.01F;
/*  49 */     this.nexus = nexus;
/*  50 */     calcPlatforms();
/*     */   }
/*     */ 
/*     */   public void setPosition(int x, int y, int z)
/*     */   {
/*  55 */     this.xCoord = x;
/*  56 */     this.yCoord = y;
/*  57 */     this.zCoord = z;
/*     */   }
/*     */ 
/*     */   public void setInitialIntegrity()
/*     */   {
/*  62 */     this.initialCompletion = evaluateIntegrity();
/*  63 */     if (this.initialCompletion == 0.0F)
/*  64 */       this.initialCompletion = 0.01F;
/*     */   }
/*     */ 
/*     */   public void setOrientation(int i)
/*     */   {
/*  69 */     this.orientation = i;
/*     */   }
/*     */ 
/*     */   public int getOrientation()
/*     */   {
/*  74 */     return this.orientation;
/*     */   }
/*     */ 
/*     */   public void setHeight(int height)
/*     */   {
/*  79 */     this.targetHeight = height;
/*  80 */     calcPlatforms();
/*     */   }
/*     */ 
/*     */   public int getTargetHeight()
/*     */   {
/*  88 */     return this.targetHeight;
/*     */   }
/*     */ 
/*     */   public void forceStatusUpdate()
/*     */   {
/*  96 */     this.latestPercentIntact = ((evaluateIntegrity() - this.initialCompletion) / (1.0F - this.initialCompletion));
/*  97 */     if (this.latestPercentIntact > this.latestPercentCompleted)
/*  98 */       this.latestPercentCompleted = this.latestPercentIntact;
/*     */   }
/*     */ 
/*     */   public float getPercentIntactCached()
/*     */   {
/* 106 */     return this.latestPercentIntact;
/*     */   }
/*     */ 
/*     */   public float getPercentCompletedCached()
/*     */   {
/* 114 */     return this.latestPercentCompleted;
/*     */   }
/*     */ 
/*     */   public int getXCoord()
/*     */   {
/* 120 */     return this.xCoord;
/*     */   }
/*     */ 
/*     */   public int getYCoord()
/*     */   {
/* 126 */     return this.yCoord;
/*     */   }
/*     */ 
/*     */   public int getZCoord()
/*     */   {
/* 132 */     return this.zCoord;
/*     */   }
/*     */ 
/*     */   public INexusAccess getNexus()
/*     */   {
/* 137 */     return this.nexus;
/*     */   }
/*     */ 
/*     */   public void setPathfindBase(IPathfindable base)
/*     */   {
/* 142 */     this.pathfindBase = base;
/*     */   }
/*     */ 
/*     */   public boolean isLayerPlatform(int height)
/*     */   {
/* 147 */     if (height == this.targetHeight - 1) {
/* 148 */       return true;
/*     */     }
/* 150 */     if (this.platforms != null)
/*     */     {
/* 153 */       for (int i : this.platforms)
/*     */       {
/* 155 */         if (i == height)
/* 156 */           return true;
/*     */       }
/*     */     }
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   public void readFromNBT(NBTTagByte nbttagcompound)
/*     */   {
/* 164 */     this.xCoord = nbttagcompound.e("xCoord");
/* 165 */     this.yCoord = nbttagcompound.e("yCoord");
/* 166 */     this.zCoord = nbttagcompound.e("zCoord");
/* 167 */     this.targetHeight = nbttagcompound.e("targetHeight");
/* 168 */     this.orientation = nbttagcompound.e("orientation");
/* 169 */     this.initialCompletion = nbttagcompound.g("initialCompletion");
/* 170 */     this.latestPercentCompleted = nbttagcompound.g("latestPercentCompleted");
/* 171 */     calcPlatforms();
/*     */   }
/*     */ 
/*     */   public void writeToNBT(NBTTagByte nbttagcompound)
/*     */   {
/* 176 */     nbttagcompound.a("xCoord", this.xCoord);
/* 177 */     nbttagcompound.a("yCoord", this.yCoord);
/* 178 */     nbttagcompound.a("zCoord", this.zCoord);
/* 179 */     nbttagcompound.a("targetHeight", this.targetHeight);
/* 180 */     nbttagcompound.a("orientation", this.orientation);
/* 181 */     nbttagcompound.a("initialCompletion", this.initialCompletion);
/* 182 */     nbttagcompound.a("latestPercentCompleted", this.latestPercentCompleted);
/*     */   }
/*     */ 
/*     */   private void calcPlatforms()
/*     */   {
/* 193 */     int spanningPlatforms = this.targetHeight < 16 ? this.targetHeight / 4 - 1 : this.targetHeight / 5 - 1;
/* 194 */     if (spanningPlatforms > 0)
/*     */     {
/* 197 */       int avgSpace = this.targetHeight / (spanningPlatforms + 1);
/* 198 */       int remainder = this.targetHeight % (spanningPlatforms + 1) - 1;
/* 199 */       this.platforms = new int[spanningPlatforms];
/* 200 */       for (int i = 0; i < spanningPlatforms; i++) {
/* 201 */         this.platforms[i] = (avgSpace * (i + 1) - 1);
/*     */       }
/*     */ 
/* 204 */       int i = spanningPlatforms - 1;
/* 205 */       while (remainder > 0)
/*     */       {
/* 207 */         this.platforms[i] += 1;
/* 208 */         i--; if (i < 0)
/*     */         {
/* 210 */           i = spanningPlatforms - 1;
/* 211 */           remainder--;
/*     */         }
/* 213 */         remainder--;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 218 */       this.platforms = new int[0];
/*     */     }
/*     */   }
/*     */ 
/*     */   private float evaluateIntegrity()
/*     */   {
/* 227 */     if (this.nexus != null)
/*     */     {
/* 229 */       int existingMainSectionBlocks = 0;
/* 230 */       int existingMainLadderBlocks = 0;
/* 231 */       int existingPlatformBlocks = 0;
/* 232 */       ColorizerGrass world = this.nexus.getWorld();
/* 233 */       for (int i = 0; i < this.targetHeight; i++)
/*     */       {
/* 235 */         if (world.u(this.xCoord + invmod.common.util.CoordsInt.offsetAdjX[this.orientation], this.yCoord + i, this.zCoord + invmod.common.util.CoordsInt.offsetAdjZ[this.orientation])) {
/* 236 */           existingMainSectionBlocks++;
/*     */         }
/* 238 */         if (world.a(this.xCoord, this.yCoord + i, this.zCoord) == BlockEndPortal.aK.blockID) {
/* 239 */           existingMainLadderBlocks++;
/*     */         }
/* 241 */         if (isLayerPlatform(i))
/*     */         {
/* 243 */           for (int j = 0; j < 8; j++)
/*     */           {
/* 245 */             if (world.u(this.xCoord + invmod.common.util.CoordsInt.offsetRing1X[j], this.yCoord + i, this.zCoord + invmod.common.util.CoordsInt.offsetRing1Z[j])) {
/* 246 */               existingPlatformBlocks++;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 252 */       float mainSectionPercent = this.targetHeight > 0 ? existingMainSectionBlocks / this.targetHeight : 0.0F;
/* 253 */       float ladderPercent = this.targetHeight > 0 ? existingMainLadderBlocks / this.targetHeight : 0.0F;
/*     */ 
/* 256 */       return 0.7F * (0.7F * mainSectionPercent + 0.3F * ladderPercent) + 0.3F * (existingPlatformBlocks / ((this.platforms.length + 1) * 8));
/*     */     }
/* 258 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float getBlockPathCost(PathNode prevNode, PathNode node, EnumGameType terrainMap)
/*     */   {
/* 264 */     float materialMultiplier = terrainMap.g(node.xCoord, node.yCoord, node.zCoord).isSolid() ? 2.2F : 1.0F;
/* 265 */     if (node.action == PathAction.SCAFFOLD_UP)
/*     */     {
/* 267 */       if (prevNode.action != PathAction.SCAFFOLD_UP) {
/* 268 */         materialMultiplier *= 3.4F;
/*     */       }
/* 270 */       return prevNode.distanceTo(node) * 0.85F * materialMultiplier;
/*     */     }
/* 272 */     if (node.action == PathAction.BRIDGE)
/*     */     {
/* 274 */       if (prevNode.action == PathAction.SCAFFOLD_UP) {
/* 275 */         materialMultiplier = 0.0F;
/*     */       }
/* 277 */       return prevNode.distanceTo(node) * 1.1F * materialMultiplier;
/*     */     }
/* 279 */     if ((node.action == PathAction.LADDER_UP_NX) || (node.action == PathAction.LADDER_UP_NZ) || (node.action == PathAction.LADDER_UP_PX) || (node.action == PathAction.LADDER_UP_PZ))
/*     */     {
/* 281 */       return prevNode.distanceTo(node) * 1.5F * materialMultiplier;
/*     */     }
/*     */ 
/* 284 */     if (this.pathfindBase != null) {
/* 285 */       return this.pathfindBase.getBlockPathCost(prevNode, node, terrainMap);
/*     */     }
/* 287 */     return prevNode.distanceTo(node);
/*     */   }
/*     */ 
/*     */   public void getPathOptionsFromNode(EnumGameType terrainMap, PathNode currentNode, PathfinderIM pathFinder)
/*     */   {
/* 293 */     if (this.pathfindBase != null) {
/* 294 */       this.pathfindBase.getPathOptionsFromNode(terrainMap, currentNode, pathFinder);
/*     */     }
/* 296 */     int id = terrainMap.a(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord);
/* 297 */     if ((currentNode.previous != null) && (currentNode.previous.action == PathAction.SCAFFOLD_UP) && (!avoidsBlock(id)))
/*     */     {
/* 299 */       pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.SCAFFOLD_UP);
/*     */       return;
/*     */     }
/*     */     int minDistance;
/* 304 */     if (this.nexus != null)
/*     */     {
/* 306 */       List scaffolds = this.nexus.getAttackerAI().getScaffolds();
/* 307 */       minDistance = this.nexus.getAttackerAI().getMinDistanceBetweenScaffolds();
/* 308 */       for (Scaffold scaffold : scaffolds)
/*     */       {
/* 310 */         if (Distance.distanceBetween(scaffold, currentNode.xCoord, currentNode.yCoord, currentNode.zCoord) < minDistance) {
/* 311 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 316 */     if ((id == 0) && (terrainMap.g(currentNode.xCoord, currentNode.yCoord - 2, currentNode.zCoord).isSolid()))
/*     */     {
/* 318 */       boolean flag = false;
/* 319 */       for (int i = 1; i < 4; i++)
/*     */       {
/* 321 */         if (terrainMap.a(currentNode.xCoord, currentNode.yCoord + i, currentNode.zCoord) != 0)
/*     */         {
/* 323 */           flag = true;
/* 324 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 328 */       if (!flag)
/* 329 */         pathFinder.addNode(currentNode.xCoord, currentNode.yCoord + 1, currentNode.zCoord, PathAction.SCAFFOLD_UP);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean avoidsBlock(int id)
/*     */   {
/* 335 */     if ((id == 51) || (id == 7) || (id == 64) || (id == 8) || (id == 9) || (id == 10) || (id == 11))
/*     */     {
/* 337 */       return true;
/*     */     }
/*     */ 
/* 341 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.Scaffold
 * JD-Core Version:    0.6.2
 */