/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.IPathfindable;
/*     */ import invmod.common.util.CoordsInt;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ 
/*     */ public class PathCreator
/*     */   implements IPathSource
/*     */ {
/*     */   private int searchDepth;
/*     */   private int quickFailDepth;
/*     */   private int[] nanosUsed;
/*     */   private int index;
/*     */ 
/*     */   public PathCreator()
/*     */   {
/*  18 */     this(200, 50);
/*     */   }
/*     */ 
/*     */   public PathCreator(int searchDepth, int quickFailDepth)
/*     */   {
/*  23 */     this.searchDepth = searchDepth;
/*  24 */     this.quickFailDepth = quickFailDepth;
/*  25 */     this.nanosUsed = new int[6];
/*  26 */     this.index = 0;
/*     */   }
/*     */ 
/*     */   public int getSearchDepth()
/*     */   {
/*  32 */     return this.searchDepth;
/*     */   }
/*     */ 
/*     */   public int getQuickFailDepth()
/*     */   {
/*  38 */     return this.quickFailDepth;
/*     */   }
/*     */ 
/*     */   public void setSearchDepth(int depth)
/*     */   {
/*  44 */     this.searchDepth = depth;
/*     */   }
/*     */ 
/*     */   public void setQuickFailDepth(int depth)
/*     */   {
/*  50 */     this.quickFailDepth = depth;
/*     */   }
/*     */ 
/*     */   public Path createPath(IPathfindable entity, int x, int y, int z, int x2, int y2, int z2, float targetRadius, float maxSearchRange, EnumGameType terrainMap)
/*     */   {
/*  56 */     long time = System.nanoTime();
/*  57 */     Path path = PathfinderIM.createPath(entity, x, y, z, x2, y2, z2, targetRadius, maxSearchRange, terrainMap, this.searchDepth, this.quickFailDepth);
/*  58 */     int elapsed = (int)(System.nanoTime() - time);
/*  59 */     this.nanosUsed[this.index] = elapsed;
/*  60 */     if (++this.index >= this.nanosUsed.length) {
/*  61 */       this.index = 0;
/*     */     }
/*  63 */     return path;
/*     */   }
/*     */ 
/*     */   public Path createPath(EntityIMLiving entity, nm target, float targetRadius, float maxSearchRange, EnumGameType terrainMap)
/*     */   {
/*  69 */     return createPath(entity, LongHashMapEntry.c(target.u + 0.5D - entity.width / 2.0F), LongHashMapEntry.c(target.v), LongHashMapEntry.c(target.w + 0.5D - entity.width / 2.0F), targetRadius, maxSearchRange, terrainMap);
/*     */   }
/*     */ 
/*     */   public Path createPath(EntityIMLiving entity, int x, int y, int z, float targetRadius, float maxSearchRange, EnumGameType terrainMap)
/*     */   {
/*  76 */     CoordsInt size = entity.getCollideSize();
/*     */     int startZ;
/*     */     int startX;
/*     */     int startY;
/*     */     int startZ;
/*  78 */     if ((size.getXCoord() <= 1) && (size.getZCoord() <= 1))
/*     */     {
/*  80 */       int startX = entity.getXCoord();
/*  81 */       int startY = LongHashMapEntry.c(entity.E.b);
/*  82 */       startZ = entity.getZCoord();
/*     */     }
/*     */     else
/*     */     {
/*  86 */       startX = LongHashMapEntry.c(entity.E.headTexture);
/*  87 */       startY = LongHashMapEntry.c(entity.E.b);
/*  88 */       startZ = LongHashMapEntry.c(entity.E.c);
/*     */     }
/*  90 */     return createPath(entity, startX, startY, startZ, LongHashMapEntry.c(x + 0.5F - entity.width / 2.0F), y, LongHashMapEntry.c(z + 0.5F - entity.width / 2.0F), targetRadius, maxSearchRange, terrainMap);
/*     */   }
/*     */ 
/*     */   public void createPath(IPathResult observer, IPathfindable entity, int x, int y, int z, int x2, int y2, int z2, float maxSearchRange, EnumGameType terrainMap)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void createPath(IPathResult observer, EntityIMLiving entity, nm target, float maxSearchRange, EnumGameType terrainMap)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void createPath(IPathResult observer, EntityIMLiving entity, int x, int y, int z, float maxSearchRange, EnumGameType terrainMap)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean canPathfindNice(IPathSource.PathPriority priority, float maxSearchRange, int searchDepth, int quickFailDepth)
/*     */   {
/* 114 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.PathCreator
 * JD-Core Version:    0.6.2
 */