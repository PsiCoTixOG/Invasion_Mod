/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.util.IPosition;
/*    */ 
/*    */ public class ModifyBlockEntry
/*    */   implements IPosition
/*    */ {
/*    */   private int xCoord;
/*    */   private int yCoord;
/*    */   private int zCoord;
/*    */   private int oldBlockId;
/*    */   private int newBlockId;
/*    */   private int newBlockMeta;
/*    */   private int cost;
/*    */ 
/*    */   public ModifyBlockEntry(int x, int y, int z, int newBlockId)
/*    */   {
/*  9 */     this(x, y, z, newBlockId, 0, 0, -1);
/*    */   }
/*    */ 
/*    */   public ModifyBlockEntry(int x, int y, int z, int newBlockId, int cost)
/*    */   {
/* 14 */     this(x, y, z, newBlockId, cost, 0, -1);
/*    */   }
/*    */ 
/*    */   public ModifyBlockEntry(int x, int y, int z, int newBlockId, int cost, int newBlockMeta, int oldBlockId)
/*    */   {
/* 19 */     this.xCoord = x;
/* 20 */     this.yCoord = y;
/* 21 */     this.zCoord = z;
/* 22 */     this.newBlockId = newBlockId;
/* 23 */     this.cost = cost;
/* 24 */     this.newBlockMeta = newBlockMeta;
/* 25 */     this.oldBlockId = oldBlockId;
/*    */   }
/*    */ 
/*    */   public int getXCoord()
/*    */   {
/* 31 */     return this.xCoord;
/*    */   }
/*    */ 
/*    */   public int getYCoord()
/*    */   {
/* 37 */     return this.yCoord;
/*    */   }
/*    */ 
/*    */   public int getZCoord()
/*    */   {
/* 43 */     return this.zCoord;
/*    */   }
/*    */ 
/*    */   public int getNewBlockId()
/*    */   {
/* 48 */     return this.newBlockId;
/*    */   }
/*    */ 
/*    */   public int getNewBlockMeta()
/*    */   {
/* 53 */     return this.newBlockMeta;
/*    */   }
/*    */ 
/*    */   public int getCost()
/*    */   {
/* 58 */     return this.cost;
/*    */   }
/*    */ 
/*    */   public int getOldBlockId()
/*    */   {
/* 66 */     return this.oldBlockId;
/*    */   }
/*    */ 
/*    */   public void setOldBlockId(int id)
/*    */   {
/* 71 */     this.oldBlockId = id;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.ModifyBlockEntry
 * JD-Core Version:    0.6.2
 */