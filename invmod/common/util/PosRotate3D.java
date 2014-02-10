/*    */ package invmod.common.util;
/*    */ 
/*    */ import net.minecraft.util.AABBPool;
/*    */ 
/*    */ public class PosRotate3D
/*    */ {
/*    */   private double posX;
/*    */   private double posY;
/*    */   private double posZ;
/*    */   private float rotX;
/*    */   private float rotY;
/*    */   private float rotZ;
/*    */ 
/*    */   public PosRotate3D()
/*    */   {
/*  9 */     this(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   public PosRotate3D(double posX, double posY, double posZ, float rotX, float rotY, float rotZ)
/*    */   {
/* 14 */     this.posX = posX;
/* 15 */     this.posY = posY;
/* 16 */     this.posZ = posZ;
/* 17 */     this.rotX = rotX;
/* 18 */     this.rotY = rotY;
/* 19 */     this.rotZ = rotZ;
/*    */   }
/*    */ 
/*    */   public AABBPool getPos()
/*    */   {
/* 24 */     return AABBPool.a(this.posX, this.posY, this.posZ);
/*    */   }
/*    */ 
/*    */   public double getPosX()
/*    */   {
/* 29 */     return this.posX;
/*    */   }
/*    */ 
/*    */   public double getPosY()
/*    */   {
/* 34 */     return this.posY;
/*    */   }
/*    */ 
/*    */   public double getPosZ()
/*    */   {
/* 39 */     return this.posZ;
/*    */   }
/*    */ 
/*    */   public float getRotX()
/*    */   {
/* 44 */     return this.rotX;
/*    */   }
/*    */ 
/*    */   public float getRotY()
/*    */   {
/* 49 */     return this.rotY;
/*    */   }
/*    */ 
/*    */   public float getRotZ()
/*    */   {
/* 54 */     return this.rotZ;
/*    */   }
/*    */ 
/*    */   public void setPosX(double pos)
/*    */   {
/* 59 */     this.posX = pos;
/*    */   }
/*    */ 
/*    */   public void setPosY(double pos)
/*    */   {
/* 64 */     this.posY = pos;
/*    */   }
/*    */ 
/*    */   public void setPosZ(double pos)
/*    */   {
/* 69 */     this.posZ = pos;
/*    */   }
/*    */ 
/*    */   public void setRotX(float rot)
/*    */   {
/* 74 */     this.rotX = rot;
/*    */   }
/*    */ 
/*    */   public void setRotY(float rot)
/*    */   {
/* 79 */     this.rotY = rot;
/*    */   }
/*    */ 
/*    */   public void setRotZ(float rot)
/*    */   {
/* 84 */     this.rotZ = rot;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.PosRotate3D
 * JD-Core Version:    0.6.2
 */