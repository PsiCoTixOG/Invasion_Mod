/*    */ package invmod.client.render;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import invmod.common.entity.EntityIMArrowOld;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ @SideOnly(Side.CLIENT)
/*    */ public class RenderPenArrow extends RenderDragon
/*    */ {
/* 19 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("textures/entity/arrow.png");
/*    */ 
/*    */   public void renderPenArrow(EntityIMArrowOld entityarrow, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 23 */     b(entityarrow);
/* 24 */     GL11.glPushMatrix();
/* 25 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 26 */     GL11.glRotatef(entityarrow.prevRotationYaw + (entityarrow.rotationYaw - entityarrow.prevRotationYaw) * f1 - 90.0F, 0.0F, 1.0F, 0.0F);
/* 27 */     GL11.glRotatef(entityarrow.prevRotationPitch + (entityarrow.rotationPitch - entityarrow.prevRotationPitch) * f1, 0.0F, 0.0F, 1.0F);
/* 28 */     ImageBufferDownload tessellator = ImageBufferDownload.imageData;
/* 29 */     int i = 0;
/* 30 */     float f2 = 0.0F;
/* 31 */     float f3 = 0.5F;
/* 32 */     float f4 = (0 + i * 10) / 32.0F;
/* 33 */     float f5 = (5 + i * 10) / 32.0F;
/* 34 */     float f6 = 0.0F;
/* 35 */     float f7 = 0.15625F;
/* 36 */     float f8 = (5 + i * 10) / 32.0F;
/* 37 */     float f9 = (10 + i * 10) / 32.0F;
/* 38 */     float f10 = 0.05625F;
/* 39 */     GL11.glEnable(32826);
/* 40 */     float f11 = entityarrow.arrowShake - f1;
/* 41 */     if (f11 > 0.0F)
/*    */     {
/* 43 */       float f12 = -LongHashMapEntry.a(f11 * 3.0F) * f11;
/* 44 */       GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
/*    */     }
/* 46 */     GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
/* 47 */     GL11.glScalef(f10, f10, f10);
/* 48 */     GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
/* 49 */     GL11.glNormal3f(f10, 0.0F, 0.0F);
/* 50 */     tessellator.b();
/* 51 */     tessellator.a(-7.0D, -2.0D, -2.0D, f6, f8);
/* 52 */     tessellator.a(-7.0D, -2.0D, 2.0D, f7, f8);
/* 53 */     tessellator.a(-7.0D, 2.0D, 2.0D, f7, f9);
/* 54 */     tessellator.a(-7.0D, 2.0D, -2.0D, f6, f9);
/* 55 */     tessellator.a();
/* 56 */     GL11.glNormal3f(-f10, 0.0F, 0.0F);
/* 57 */     tessellator.b();
/* 58 */     tessellator.a(-7.0D, 2.0D, -2.0D, f6, f8);
/* 59 */     tessellator.a(-7.0D, 2.0D, 2.0D, f7, f8);
/* 60 */     tessellator.a(-7.0D, -2.0D, 2.0D, f7, f9);
/* 61 */     tessellator.a(-7.0D, -2.0D, -2.0D, f6, f9);
/* 62 */     tessellator.a();
/* 63 */     for (int j = 0; j < 4; j++)
/*    */     {
/* 65 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 66 */       GL11.glNormal3f(0.0F, 0.0F, f10);
/* 67 */       tessellator.b();
/* 68 */       tessellator.a(-8.0D, -2.0D, 0.0D, f2, f4);
/* 69 */       tessellator.a(8.0D, -2.0D, 0.0D, f3, f4);
/* 70 */       tessellator.a(8.0D, 2.0D, 0.0D, f3, f5);
/* 71 */       tessellator.a(-8.0D, 2.0D, 0.0D, f2, f5);
/* 72 */       tessellator.a();
/*    */     }
/*    */ 
/* 75 */     GL11.glDisable(32826);
/* 76 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   public void a(nm entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 82 */     renderPenArrow((EntityIMArrowOld)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 88 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderPenArrow
 * JD-Core Version:    0.6.2
 */