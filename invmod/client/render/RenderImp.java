/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMImp;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.src.nm;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderImp extends RendererLivingEntity
/*    */ {
/* 16 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("textures/entity/arrow.png");
/*    */ 
/*    */   public RenderImp(ModelMagmaCube modelbase, float f)
/*    */   {
/* 20 */     super(modelbase, f);
/*    */   }
/*    */ 
/*    */   protected void preRenderScale(EntityIMImp entity, float f)
/*    */   {
/* 25 */     GL11.glScalef(0.7F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   protected void a(EntityLeashKnot entityliving, float f)
/*    */   {
/* 31 */     preRenderScale((EntityIMImp)entityliving, f);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 37 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderImp
 * JD-Core Version:    0.6.2
 */