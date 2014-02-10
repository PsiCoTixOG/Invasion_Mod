/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class IMMoveHelperSpider extends IMMoveHelper
/*    */ {
/*    */   public IMMoveHelperSpider(EntityIMLiving par1EntityLiving)
/*    */   {
/* 10 */     super(par1EntityLiving);
/*    */   }
/*    */ 
/*    */   protected int getClimbFace(double x, double y, double z)
/*    */   {
/* 17 */     int mobX = LongHashMapEntry.c(x - this.a.width / 2.0F);
/* 18 */     int mobY = LongHashMapEntry.c(y);
/* 19 */     int mobZ = LongHashMapEntry.c(z - this.a.width / 2.0F);
/*    */ 
/* 23 */     int index = 0;
/* 24 */     Path path = this.a.getNavigatorNew().getPath();
/* 25 */     if ((path != null) && (!path.isFinished()))
/*    */     {
/* 27 */       PathNode currentPoint = path.getPathPointFromIndex(path.getCurrentPathIndex());
/* 28 */       int pathLength = path.getCurrentPathLength();
/* 29 */       for (int i = path.getCurrentPathIndex(); i < pathLength; i++)
/*    */       {
/* 31 */         PathNode point = path.getPathPointFromIndex(i);
/* 32 */         if (point.xCoord > currentPoint.xCoord)
/*    */         {
/*    */           break;
/*    */         }
/* 36 */         if (point.xCoord < currentPoint.xCoord)
/*    */         {
/* 38 */           index = 2;
/* 39 */           break;
/*    */         }
/* 41 */         if (point.zCoord > currentPoint.zCoord)
/*    */         {
/* 43 */           index = 4;
/* 44 */           break;
/*    */         }
/* 46 */         if (point.zCoord < currentPoint.zCoord)
/*    */         {
/* 48 */           index = 6;
/* 49 */           break;
/*    */         }
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 55 */     for (int count = 0; count < 8; count++)
/*    */     {
/* 57 */       if (this.a.q.u(mobX + invmod.common.util.CoordsInt.offsetAdj2X[index], mobY, mobZ + invmod.common.util.CoordsInt.offsetAdj2Z[index])) {
/* 58 */         return index / 2;
/*    */       }
/* 60 */       index++; if (index > 7) {
/* 61 */         index = 0;
/*    */       }
/*    */     }
/* 64 */     return -1;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.IMMoveHelperSpider
 * JD-Core Version:    0.6.2
 */