/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMThrower;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.src.nm;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderThrower extends RendererLivingEntity
/*    */ {
/* 16 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/throwerT1.png");
/*    */ 
/*    */   public RenderThrower(ModelMagmaCube modelbase, float f)
/*    */   {
/* 20 */     super(modelbase, f);
/*    */   }
/*    */ 
/*    */   protected void preRenderScale(EntityIMThrower entity, float f)
/*    */   {
/* 25 */     GL11.glScalef(2.4F, 2.8F, 2.4F);
/*    */   }
/*    */ 
/*    */   protected void a(EntityLeashKnot entityliving, float f)
/*    */   {
/* 31 */     preRenderScale((EntityIMThrower)entityliving, f);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 37 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderThrower
 * JD-Core Version:    0.6.2
 */