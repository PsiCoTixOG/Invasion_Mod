/*    */ package invmod.common.nexus;
/*    */ 
/*    */ import invmod.common.util.IPolarAngle;
/*    */ import invmod.common.util.IPosition;
/*    */ 
/*    */ public class SpawnPoint
/*    */   implements IPosition, IPolarAngle, Comparable<IPolarAngle>
/*    */ {
/*    */   private int xCoord;
/*    */   private int yCoord;
/*    */   private int zCoord;
/*    */   private int spawnAngle;
/*    */   private SpawnType spawnType;
/*    */ 
/*    */   public SpawnPoint(int x, int y, int z, int angle, SpawnType type)
/*    */   {
/* 19 */     this.xCoord = x;
/* 20 */     this.yCoord = y;
/* 21 */     this.zCoord = z;
/* 22 */     this.spawnAngle = angle;
/* 23 */     this.spawnType = type;
/*    */   }
/*    */ 
/*    */   public int getXCoord()
/*    */   {
/* 29 */     return this.xCoord;
/*    */   }
/*    */ 
/*    */   public int getYCoord()
/*    */   {
/* 35 */     return this.yCoord;
/*    */   }
/*    */ 
/*    */   public int getZCoord()
/*    */   {
/* 41 */     return this.zCoord;
/*    */   }
/*    */ 
/*    */   public int getAngle()
/*    */   {
/* 47 */     return this.spawnAngle;
/*    */   }
/*    */ 
/*    */   public SpawnType getType()
/*    */   {
/* 52 */     return this.spawnType;
/*    */   }
/*    */ 
/*    */   public int compareTo(IPolarAngle polarAngle)
/*    */   {
/* 58 */     if (this.spawnAngle < polarAngle.getAngle())
/*    */     {
/* 60 */       return -1;
/*    */     }
/* 62 */     if (this.spawnAngle > polarAngle.getAngle())
/*    */     {
/* 64 */       return 1;
/*    */     }
/*    */ 
/* 68 */     return 0;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 75 */     return "Spawn#" + this.spawnType + "#" + this.xCoord + "," + this.yCoord + "," + this.zCoord + "#" + this.spawnAngle;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.SpawnPoint
 * JD-Core Version:    0.6.2
 */