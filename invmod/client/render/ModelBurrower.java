/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.util.PosRotate3D;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.src.bcr;
/*    */ import net.minecraft.src.nm;
/*    */ 
/*    */ public class ModelBurrower extends ModelMagmaCube
/*    */ {
/*    */   bcr head;
/*    */   bcr seg1;
/*    */   bcr seg2;
/*    */   bcr seg3;
/*    */ 
/*    */   public ModelBurrower()
/*    */   {
/* 13 */     this.textureWidth = 64;
/* 14 */     this.textureHeight = 32;
/*    */ 
/* 16 */     this.head = new bcr(this, 0, 0);
/* 17 */     this.head.a(-2.0F, -2.5F, -2.5F, 4, 5, 5);
/* 18 */     this.head.a(0.0F, 0.0F, 0.0F);
/* 19 */     this.head.b(64, 32);
/* 20 */     this.head.i = true;
/* 21 */     setRotation(this.head, 0.0F, 0.0F, 0.0F);
/* 22 */     this.seg1 = new bcr(this, 0, 0);
/* 23 */     this.seg1.a(-2.0F, -2.5F, -2.5F, 4, 5, 5);
/* 24 */     this.seg1.a(-4.0F, 0.0F, 0.0F);
/* 25 */     this.seg1.b(64, 32);
/* 26 */     this.seg1.i = true;
/* 27 */     setRotation(this.seg1, 0.0F, 0.0F, 0.0F);
/* 28 */     this.seg2 = new bcr(this, 0, 0);
/* 29 */     this.seg2.a(-2.0F, -2.5F, -2.5F, 4, 5, 5);
/* 30 */     this.seg2.a(-8.0F, 0.0F, 0.0F);
/* 31 */     this.seg2.b(64, 32);
/* 32 */     this.seg2.i = true;
/* 33 */     setRotation(this.seg2, 0.0F, 0.0F, 0.0F);
/* 34 */     this.seg3 = new bcr(this, 0, 0);
/* 35 */     this.seg3.a(-2.0F, -2.5F, -2.5F, 4, 5, 5);
/* 36 */     this.seg3.a(-12.0F, 0.0F, 0.0F);
/* 37 */     this.seg3.b(64, 32);
/* 38 */     this.seg3.i = true;
/* 39 */     setRotation(this.seg3, 0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   public void render(nm entity, float partialTick, PosRotate3D[] pos, float modelScale)
/*    */   {
/* 44 */     super.a(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, modelScale);
/*    */ 
/* 50 */     if (pos.length >= 16)
/*    */     {
/* 52 */       this.head.a((float)pos[0].getPosX(), (float)pos[0].getPosY(), (float)pos[0].getPosZ());
/* 53 */       setRotation(this.head, pos[0].getRotX(), pos[0].getRotY(), pos[0].getRotZ());
/* 54 */       this.seg1.a((float)pos[1].getPosX(), (float)pos[1].getPosY(), (float)pos[1].getPosZ());
/* 55 */       setRotation(this.seg1, pos[1].getRotX(), pos[1].getRotY(), pos[1].getRotZ());
/* 56 */       this.seg2.a((float)pos[2].getPosX(), (float)pos[2].getPosY(), (float)pos[2].getPosZ());
/* 57 */       setRotation(this.seg2, pos[2].getRotX(), pos[2].getRotY(), pos[2].getRotZ());
/* 58 */       this.seg3.a((float)pos[3].getPosX(), (float)pos[3].getPosY(), (float)pos[3].getPosZ());
/* 59 */       setRotation(this.seg3, pos[3].getRotX(), pos[3].getRotY(), pos[3].getRotZ());
/* 60 */       this.head.a(modelScale);
/* 61 */       this.seg1.a(modelScale);
/* 62 */       this.seg2.a(modelScale);
/* 63 */       this.seg3.a(modelScale);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void setRotation(bcr model, float x, float y, float z)
/*    */   {
/* 72 */     model.f = x;
/* 73 */     model.g = y;
/* 74 */     model.h = z;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelBurrower
 * JD-Core Version:    0.6.2
 */