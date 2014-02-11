/*    */ package invmod.client.render;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
		 import net.minecraft.client.model.ModelRenderer; //PsiCoTix: this was import net.minecraft.src.ModelRenderer;
		 import net.minecraft.entity.Entity; //PsiCoTix: this was import net.minecraft.src.nm;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class ModelB extends ModelMagmaCube
/*    */ {
/*    */   private ModelRenderer batHead;
/*    */   private ModelRenderer batBody;
/*    */   private ModelRenderer batRightWing;
/*    */   private ModelRenderer batLeftWing;
/*    */   private ModelRenderer batOuterRightWing;
/*    */   private ModelRenderer batOuterLeftWing;
/*    */ 
/*    */   public ModelB()
/*    */   {
/* 32 */     this.textureWidth = 64;
/* 33 */     this.textureHeight = 64;
/* 34 */     this.batHead = new ModelRenderer(this, 0, 0);
/* 35 */     this.batHead.a(-3.0F, -3.0F, -3.0F, 6, 6, 6);
/* 36 */     ModelRenderer modelrenderer = new ModelRenderer(this, 24, 0);
/* 37 */     modelrenderer.a(-4.0F, -6.0F, -2.0F, 3, 4, 1);
/* 38 */     this.batHead.a(modelrenderer);
/* 39 */     ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
/* 40 */     modelrenderer1.i = true;
/* 41 */     modelrenderer1.a(1.0F, -6.0F, -2.0F, 3, 4, 1);
/* 42 */     this.batHead.a(modelrenderer1);
/* 43 */     this.batBody = new ModelRenderer(this, 0, 16);
/* 44 */     this.batBody.a(-3.0F, 4.0F, -3.0F, 6, 12, 6);
/* 45 */     this.batBody.a(0, 34).a(-5.0F, 16.0F, 0.0F, 10, 6, 1);
/* 46 */     this.batRightWing = new ModelRenderer(this, 42, 0);
/* 47 */     this.batRightWing.a(-12.0F, 1.0F, 1.5F, 10, 16, 1);
/* 48 */     this.batOuterRightWing = new ModelRenderer(this, 24, 16);
/* 49 */     this.batOuterRightWing.a(-12.0F, 1.0F, 1.5F);
/* 50 */     this.batOuterRightWing.a(-8.0F, 1.0F, 0.0F, 8, 12, 1);
/* 51 */     this.batLeftWing = new ModelRenderer(this, 42, 0);
/* 52 */     this.batLeftWing.i = true;
/* 53 */     this.batLeftWing.a(2.0F, 1.0F, 1.5F, 10, 16, 1);
/* 54 */     this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
/* 55 */     this.batOuterLeftWing.i = true;
/* 56 */     this.batOuterLeftWing.a(12.0F, 1.0F, 1.5F);
/* 57 */     this.batOuterLeftWing.a(0.0F, 1.0F, 0.0F, 8, 12, 1);
/* 58 */     this.batBody.a(this.batRightWing);
/* 59 */     this.batBody.a(this.batLeftWing);
/* 60 */     this.batRightWing.a(this.batOuterRightWing);
/* 61 */     this.batLeftWing.a(this.batOuterLeftWing);
/*    */   }
/*    */ 
/*    */   public int getBatSize()
/*    */   {
/* 70 */     return 36;
/*    */   }
/*    */ 
/*    */   public void a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*    */   {
/* 79 */     this.batHead.f = (par6 / 57.295776F);
/* 80 */     this.batHead.g = (par5 / 57.295776F);
/* 81 */     this.batHead.h = 0.0F;
/* 82 */     this.batHead.a(0.0F, 0.0F, 0.0F);
/* 83 */     this.batRightWing.a(0.0F, 0.0F, 0.0F);
/* 84 */     this.batLeftWing.a(0.0F, 0.0F, 0.0F);
/* 85 */     this.batBody.f = (0.7853982F + LongHashMapEntry.b(par4 * 0.1F) * 0.15F);
/* 86 */     this.batBody.g = 0.0F;
/* 87 */     this.batRightWing.g = (LongHashMapEntry.b(par4 * 1.3F) * 3.141593F * 0.25F);
/* 88 */     this.batLeftWing.g = (-this.batRightWing.g);
/* 89 */     this.batOuterRightWing.g = (this.batRightWing.g * 0.5F);
/* 90 */     this.batOuterLeftWing.g = (-this.batRightWing.g * 0.5F);
/*    */ 
/* 92 */     this.batHead.a(par7);
/* 93 */     this.batBody.a(par7);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelB
 * JD-Core Version:    0.6.2
 */