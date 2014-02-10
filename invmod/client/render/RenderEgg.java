/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMEgg;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.src.nm;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderEgg extends RenderDragon
/*    */ {
/* 12 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/spideregg.png");
/*    */   private ModelEgg modelEgg;
/*    */ 
/*    */   public RenderEgg()
/*    */   {
/* 18 */     this.modelEgg = new ModelEgg();
/*    */   }
/*    */ 
/*    */   public void renderEgg(EntityIMEgg entityEgg, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 23 */     GL11.glPushMatrix();
/* 24 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 25 */     GL11.glEnable(32826);
/* 26 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/*    */ 
/* 28 */     b(entityEgg);
/* 29 */     this.modelEgg.a(entityEgg, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 30 */     GL11.glDisable(32826);
/* 31 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   public void a(nm entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 37 */     renderEgg((EntityIMEgg)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 43 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderEgg
 * JD-Core Version:    0.6.2
 */