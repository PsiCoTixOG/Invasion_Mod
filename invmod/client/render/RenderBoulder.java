/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMBoulder;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderBoulder extends RenderDragon
/*    */ {
/* 14 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/boulder.png");
/*    */   private ModelBoulder modelBoulder;
/*    */ 
/*    */   public RenderBoulder()
/*    */   {
/* 18 */     this.modelBoulder = new ModelBoulder();
/*    */   }
/*    */ 
/*    */   public void renderBoulder(EntityIMBoulder entityBoulder, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 23 */     GL11.glPushMatrix();
/* 24 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/* 25 */     GL11.glEnable(32826);
/* 26 */     GL11.glScalef(2.2F, 2.2F, 2.2F);
/* 27 */     b(entityBoulder);
/* 28 */     float spin = entityBoulder.getFlightTime() % 20 / 20.0F;
/* 29 */     this.modelBoulder.a(entityBoulder, spin, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
/* 30 */     GL11.glDisable(32826);
/* 31 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   public void a(nm entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 37 */     renderBoulder((EntityIMBoulder)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(Entity entity)
/*    */   {
/* 45 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderBoulder
 * JD-Core Version:    0.6.2
 */