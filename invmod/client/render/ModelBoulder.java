/*    */ package invmod.client.render;
/*    */ 
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.src.bcr;
/*    */ import net.minecraft.src.nm;
/*    */ 
/*    */ public class ModelBoulder extends ModelMagmaCube
/*    */ {
/*    */   bcr boulder;
/*    */ 
/*    */   public ModelBoulder()
/*    */   {
/* 13 */     this.boulder = new bcr(this, 0, 0);
/* 14 */     this.boulder.a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
/* 15 */     this.boulder.a(0.0F, 0.0F, 0.0F);
/* 16 */     this.boulder.f = 0.0F;
/* 17 */     this.boulder.g = 0.0F;
/* 18 */     this.boulder.h = 0.0F;
/* 19 */     this.boulder.i = false;
/*    */   }
/*    */ 
/*    */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*    */   {
/* 25 */     super.a(entity, f, f1, f2, f3, f4, f5);
/* 26 */     a(f, f1, f2, f3, f4, f5, entity);
/* 27 */     this.boulder.a(f5);
/*    */   }
/*    */ 
/*    */   public void a(float f, float f1, float f2, float f3, float f4, float f5, nm entity)
/*    */   {
/* 33 */     this.boulder.f = f;
/* 34 */     this.boulder.g = f1;
/* 35 */     this.boulder.h = f2;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelBoulder
 * JD-Core Version:    0.6.2
 */