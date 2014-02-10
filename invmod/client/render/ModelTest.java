/*    */ package invmod.client.render;
/*    */ 
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.src.bcr;
/*    */ import net.minecraft.src.nm;
/*    */ 
/*    */ public class ModelTest extends ModelMagmaCube
/*    */ {
/*    */   bcr Shape1;
/*    */   bcr Shape2;
/*    */   bcr Shape3;
/*    */ 
/*    */   public ModelTest()
/*    */   {
/* 13 */     this.textureWidth = 64;
/* 14 */     this.textureHeight = 32;
/*    */ 
/* 16 */     this.Shape1 = new bcr(this, 0, 0);
/* 17 */     this.Shape1.a(-3.0F, -1.5F, -1.5F, 7, 3, 3);
/* 18 */     this.Shape1.a(0.0F, 0.0F, 0.0F);
/* 19 */     this.Shape1.b(64, 32);
/* 20 */     setRotation(this.Shape1, 0.0F, 0.0F, 0.0F);
/* 21 */     this.Shape2 = new bcr(this, 0, 8);
/* 22 */     this.Shape2.a(4.0F, -0.5F, -0.5F, 1, 1, 1);
/* 23 */     this.Shape2.a(0.0F, 0.0F, 0.0F);
/* 24 */     this.Shape2.b(64, 32);
/* 25 */     setRotation(this.Shape2, 0.0F, 0.0F, 0.0F);
/* 26 */     this.Shape3 = new bcr(this, 0, 6);
/* 27 */     this.Shape3.a(2.0F, 1.5F, -0.5F, 1, 1, 1);
/* 28 */     this.Shape3.a(0.0F, 0.0F, 0.0F);
/* 29 */     this.Shape3.b(64, 32);
/* 30 */     setRotation(this.Shape3, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*    */   {
/* 36 */     super.a(entity, f, f1, f2, f3, f4, f5);
/* 37 */     a(f, f1, f2, f3, f4, f5, entity);
/* 38 */     this.Shape1.a(f5);
/* 39 */     this.Shape2.a(f5);
/* 40 */     this.Shape3.a(f5);
/*    */   }
/*    */ 
/*    */   private void setRotation(bcr model, float x, float y, float z)
/*    */   {
/* 45 */     model.f = x;
/* 46 */     model.g = y;
/* 47 */     model.h = z;
/*    */   }
/*    */ 
/*    */   public void a(float f, float f1, float f2, float f3, float f4, float f5, nm entity)
/*    */   {
/* 53 */     setRotation(this.Shape1, f, f1, f2);
/* 54 */     setRotation(this.Shape2, f, f1, f2);
/* 55 */     setRotation(this.Shape3, f, f1, f2);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelTest
 * JD-Core Version:    0.6.2
 */