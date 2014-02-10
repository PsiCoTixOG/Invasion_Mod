/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMTrap;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.src.nm;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderTrap extends RenderDragon
/*    */ {
/* 14 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/trap.png");
/*    */   private ModelTrap modelTrap;
/*    */ 
/*    */   public RenderTrap(ModelTrap model)
/*    */   {
/* 18 */     this.modelTrap = model;
/*    */   }
/*    */ 
/*    */   public void renderTrap(EntityIMTrap entityTrap, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 23 */     GL11.glPushMatrix();
/* 24 */     GL11.glTranslatef((float)d, (float)d1, (float)d2);
/*    */ 
/* 27 */     GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
/* 28 */     GL11.glEnable(32826);
/* 29 */     GL11.glScalef(1.3F, 1.3F, 1.3F);
/* 30 */     b(entityTrap);
/* 31 */     this.modelTrap.render(entityTrap, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, entityTrap.isEmpty(), entityTrap.getTrapType());
/* 32 */     GL11.glDisable(32826);
/* 33 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   public void a(nm entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 39 */     renderTrap((EntityIMTrap)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 47 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderTrap
 * JD-Core Version:    0.6.2
 */