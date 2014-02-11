/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMBolt;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderBolt extends RenderDragon
/*    */ {
/*    */   public void render(EntityIMBolt entityBolt, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 18 */     ImageBufferDownload tessellator = ImageBufferDownload.imageData;
/* 19 */     GL11.glPushMatrix();
/* 20 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 21 */     GL11.glRotatef(entityBolt.getYaw(), 0.0F, 1.0F, 0.0F);
/* 22 */     GL11.glRotatef(entityBolt.getPitch(), 0.0F, 0.0F, 1.0F);
/* 23 */     float scale = 0.0625F;
/* 24 */     GL11.glScalef(scale, scale, scale);
/* 25 */     renderFromVertices(entityBolt, tessellator);
/* 26 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   public void renderFromVertices(EntityIMBolt entityBolt, ImageBufferDownload tessellator)
/*    */   {
/* 31 */     double[][] vertices = entityBolt.getVertices();
/* 32 */     if (vertices == null) {
/* 33 */       return;
/*    */     }
/* 35 */     GL11.glDisable(3553);
/* 36 */     GL11.glDisable(2896);
/* 37 */     GL11.glEnable(3042);
/* 38 */     GL11.glBlendFunc(770, 1);
/*    */ 
/* 40 */     double[] xCoords = vertices[0];
/* 41 */     double[] yCoords = vertices[1];
/* 42 */     double[] zCoords = vertices[2];
/*    */ 
/* 44 */     double drawWidth = -0.1D;
/* 45 */     for (int pass = 0; pass < 4; pass++)
/*    */     {
/* 47 */       drawWidth += 0.32D;
/* 48 */       for (int i = 1; i < yCoords.length; i++)
/*    */       {
/* 50 */         tessellator.b(5);
/* 51 */         tessellator.a(0.5F, 0.5F, 0.65F, 0.6F);
/* 52 */         for (int j = 0; j < 5; j++)
/*    */         {
/* 54 */           double xOffset = 0.5D - drawWidth;
/* 55 */           double zOffset = 0.5D - drawWidth;
/* 56 */           if ((j == 1) || (j == 2))
/*    */           {
/* 58 */             xOffset += drawWidth * 2.0D;
/*    */           }
/* 60 */           if ((j == 2) || (j == 3))
/*    */           {
/* 62 */             zOffset += drawWidth * 2.0D;
/*    */           }
/* 64 */           tessellator.a(xCoords[(i - 1)] + xOffset, yCoords[(i - 1)] * 16.0D, zCoords[(i - 1)] + zOffset);
/* 65 */           tessellator.a(xCoords[i] + xOffset, yCoords[i] * 16.0D, zCoords[i] + zOffset);
/*    */         }
/* 67 */         tessellator.a();
/*    */       }
/*    */     }
/* 70 */     GL11.glDisable(3042);
/* 71 */     GL11.glEnable(2896);
/* 72 */     GL11.glEnable(3553);
/*    */   }
/*    */ 
/*    */   public void a(Entity entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 78 */     render((EntityIMBolt)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(Entity entity)
/*    */   {
/* 84 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderBolt
 * JD-Core Version:    0.6.2
 */