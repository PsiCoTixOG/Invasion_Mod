/*    */ package invmod.client.render;
/*    */ 
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.renderer.entity.RenderGhast;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.src.nm;
/*    */ 
/*    */ public class RenderPigEngy extends RenderGhast
/*    */ {
/* 10 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/pigengT1.png");
/*    */ 
/*    */   public RenderPigEngy(ModelCreeper model, float shadowSize)
/*    */   {
/* 14 */     super(model, shadowSize);
/*    */   }
/*    */ 
/*    */   public RenderPigEngy(ModelCreeper model, float shadowSize, float par3)
/*    */   {
/* 19 */     super(model, shadowSize, par3);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 25 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderPigEngy
 * JD-Core Version:    0.6.2
 */