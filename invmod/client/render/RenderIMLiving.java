/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMLiving;
/*    */ import net.minecraft.client.model.ModelMagmaCube;
/*    */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*    */ 
/*    */ public abstract class RenderIMLiving extends RendererLivingEntity
/*    */ {
/*    */   public RenderIMLiving(ModelMagmaCube model, float shadowWidth)
/*    */   {
/* 11 */     super(model, shadowWidth);
/*    */   }
/*    */ 
/*    */   public void doRenderLiving(EntityIMLiving entity, double renderX, double renderY, double renderZ, float interpYaw, float parTick)
/*    */   {
/* 16 */     super.doRenderLiving(entity, renderX, renderY, renderZ, interpYaw, parTick);
/* 17 */     if (entity.shouldRenderLabel())
/*    */     {
/* 19 */       String s = entity.getRenderLabel();
/* 20 */       String[] labels = s.split("\n");
/* 21 */       for (int i = 0; i < labels.length; i++)
/*    */       {
/* 23 */         a(entity, labels[i], renderX, renderY + (labels.length - 1 - i) * 0.22D, renderZ, 32);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderIMLiving
 * JD-Core Version:    0.6.2
 */