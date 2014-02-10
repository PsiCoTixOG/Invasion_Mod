/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.util.PosRotate3D;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.src.bcr;
/*    */ import net.minecraft.src.nm;
/*    */ 
/*    */ public class ModelBurrower2 extends ModelMagmaCube
/*    */ {
/*    */   bcr head;
/*    */   bcr[] segments;
/*    */ 
/*    */   public ModelBurrower2(int numberOfSegments)
/*    */   {
/* 12 */     this.textureWidth = 64;
/* 13 */     this.textureHeight = 32;
/*    */ 
/* 15 */     this.head = new bcr(this, 0, 0);
/* 16 */     this.head.a(-1.0F, -3.0F, -3.0F, 2, 6, 6);
/* 17 */     this.head.a(0.0F, 0.0F, 0.0F);
/* 18 */     this.head.b(64, 32);
/* 19 */     this.head.i = true;
/* 20 */     setRotation(this.head, 0.0F, 0.0F, 0.0F);
/* 21 */     this.segments = new bcr[numberOfSegments];
/* 22 */     for (int i = 0; i < numberOfSegments; i++)
/*    */     {
/* 24 */       this.segments[i] = new bcr(this, 0, 0);
/*    */ 
/* 26 */       if (i % 2 == 0)
/* 27 */         this.segments[i].a(-0.5F, -3.5F, -3.5F, 2, 7, 7);
/*    */       else {
/* 29 */         this.segments[i].a(-0.5F, -2.5F, -2.5F, 2, 5, 5);
/*    */       }
/* 31 */       this.segments[i].a(-4.0F, 0.0F, 0.0F);
/* 32 */       this.segments[i].b(64, 32);
/* 33 */       this.segments[i].i = true;
/* 34 */       setRotation(this.segments[i], 0.0F, 0.0F, 0.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void render(nm entity, float partialTick, PosRotate3D[] pos, float modelScale)
/*    */   {
/* 40 */     super.a(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, modelScale);
/*    */ 
/* 46 */     this.head.a((float)pos[0].getPosX(), (float)pos[0].getPosY(), (float)pos[0].getPosZ());
/* 47 */     setRotation(this.head, pos[0].getRotX(), pos[0].getRotY(), pos[0].getRotZ());
/* 48 */     for (int i = 0; i < this.segments.length; i++)
/*    */     {
/* 50 */       this.segments[i].a((float)pos[(i + 1)].getPosX(), (float)pos[(i + 1)].getPosY(), (float)pos[(i + 1)].getPosZ());
/* 51 */       setRotation(this.segments[i], pos[(i + 1)].getRotX(), pos[(i + 1)].getRotY(), pos[(i + 1)].getRotZ());
/* 52 */       this.segments[i].a(modelScale);
/*    */     }
/* 54 */     this.head.a(modelScale);
/*    */   }
/*    */ 
/*    */   private void setRotation(bcr model, float x, float y, float z)
/*    */   {
/* 62 */     model.f = x;
/* 63 */     model.g = y;
/* 64 */     model.h = z;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelBurrower2
 * JD-Core Version:    0.6.2
 */