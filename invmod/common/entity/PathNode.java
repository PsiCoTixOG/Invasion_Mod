/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.IPosition;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class PathNode
/*     */   implements IPosition
/*     */ {
/*     */   public final int xCoord;
/*     */   public final int yCoord;
/*     */   public final int zCoord;
/*     */   public final PathAction action;
/*     */   private final int hash;
/*     */   int index;
/*     */   float totalPathDistance;
/*     */   float distanceToNext;
/*     */   float distanceToTarget;
/*     */   PathNode previous;
/*     */   public boolean isFirst;
/*     */ 
/*     */   public PathNode(int i, int j, int k)
/*     */   {
/*  22 */     this(i, j, k, PathAction.NONE);
/*     */   }
/*     */ 
/*     */   public PathNode(int i, int j, int k, PathAction pathAction)
/*     */   {
/*  27 */     this.index = -1;
/*  28 */     this.isFirst = false;
/*  29 */     this.xCoord = i;
/*  30 */     this.yCoord = j;
/*  31 */     this.zCoord = k;
/*  32 */     this.action = pathAction;
/*  33 */     this.hash = makeHash(i, j, k, this.action);
/*     */   }
/*     */ 
/*     */   public static int makeHash(int x, int y, int z, PathAction action)
/*     */   {
/*  41 */     return y & 0xFF | (x & 0xFF) << 8 | (z & 0xFF) << 16 | (action.ordinal() & 0xFF) << 24;
/*     */   }
/*     */ 
/*     */   public float distanceTo(PathNode pathpoint)
/*     */   {
/*  46 */     float f = pathpoint.xCoord - this.xCoord;
/*  47 */     float f1 = pathpoint.yCoord - this.yCoord;
/*  48 */     float f2 = pathpoint.zCoord - this.zCoord;
/*  49 */     return LongHashMapEntry.c(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */   public float distanceTo(float x, float y, float z)
/*     */   {
/*  54 */     float f = x - this.xCoord;
/*  55 */     float f1 = y - this.yCoord;
/*  56 */     float f2 = z - this.zCoord;
/*  57 */     return LongHashMapEntry.c(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/*  63 */     if ((obj instanceof PathNode))
/*     */     {
/*  65 */       PathNode node = (PathNode)obj;
/*  66 */       return (this.hash == node.hash) && (this.xCoord == node.xCoord) && (this.yCoord == node.yCoord) && (this.zCoord == node.zCoord) && (node.action == this.action);
/*     */     }
/*     */ 
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equals(int x, int y, int z)
/*     */   {
/*  76 */     return (this.xCoord == x) && (this.yCoord == y) && (this.zCoord == z);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  82 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public boolean isAssigned()
/*     */   {
/*  87 */     return this.index >= 0;
/*     */   }
/*     */ 
/*     */   public int getXCoord()
/*     */   {
/*  93 */     return this.xCoord;
/*     */   }
/*     */ 
/*     */   public int getYCoord()
/*     */   {
/*  99 */     return this.yCoord;
/*     */   }
/*     */ 
/*     */   public int getZCoord()
/*     */   {
/* 105 */     return this.zCoord;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 111 */     return this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ", " + this.action;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.PathNode
 * JD-Core Version:    0.6.2
 */