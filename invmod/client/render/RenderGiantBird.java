/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMBird;
/*    */ import invmod.common.util.MathUtil;
/*    */ import net.minecraft.client.renderer.ImageBufferDownload;
/*    */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.util.AABBPool;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderGiantBird extends RenderIMLiving
/*    */ {
/* 16 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/burd.png");
/*    */   private ModelVulture modelBird;
/*    */ 
/*    */   public RenderGiantBird()
/*    */   {
/* 22 */     super(new ModelVulture(), 0.4F);
/* 23 */     this.modelBird = ((ModelVulture)this.i);
/*    */   }
/*    */ 
/*    */   public void renderGiantBird(EntityIMBird entityBird, double renderX, double renderY, double renderZ, float interpYaw, float partialTick)
/*    */   {
/* 28 */     if (entityBird.hasFlyingDebug())
/*    */     {
/* 30 */       renderNavigationVector(entityBird, renderX, renderY, renderZ);
/*    */     }
/*    */ 
/* 33 */     float roll = MathUtil.interpRotationDeg(entityBird.getPrevRotationRoll(), entityBird.getRotationRoll(), partialTick);
/* 34 */     float headYaw = MathUtil.interpRotationDeg(entityBird.getPrevRotationYawHeadIM(), entityBird.getRotationYawHeadIM(), partialTick);
/* 35 */     float headPitch = MathUtil.interpRotationDeg(entityBird.getPrevRotationPitchHead(), entityBird.getRotationPitchHead(), partialTick);
/*    */ 
/* 38 */     this.modelBird.resetSkeleton();
/* 39 */     this.modelBird.setFlyingAnimations(entityBird.getWingAnimationState(), entityBird.getLegAnimationState(), entityBird.getBeakAnimationState(), roll, headYaw, headPitch, partialTick);
/*    */ 
/* 41 */     super.doRenderLiving(entityBird, renderX, renderY, renderZ, interpYaw, partialTick);
/*    */   }
/*    */ 
/*    */   public void a(nm entity, double d, double d1, double d2, float f, float f1)
/*    */   {
/* 47 */     renderGiantBird((EntityIMBird)entity, d, d1, d2, f, f1);
/*    */   }
/*    */ 
/*    */   protected void renderModel(EntityLivingBase par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
/*    */   {
/* 52 */     this.modelBird.a(par2, par3, par4, par5, par6, par7, par1EntityLiving);
/* 53 */     super.a(par1EntityLiving, par2, par3, par4, par5, par6, par7);
/*    */   }
/*    */ 
/*    */   private void renderNavigationVector(EntityIMBird entityBird, double entityRenderOffsetX, double entityRenderOffsetY, double entityRenderOffsetZ)
/*    */   {
/* 58 */     ImageBufferDownload tessellator = ImageBufferDownload.imageData;
/* 59 */     GL11.glPushMatrix();
/*    */ 
/* 61 */     GL11.glDisable(3553);
/* 62 */     GL11.glDisable(2896);
/* 63 */     GL11.glEnable(3042);
/* 64 */     GL11.glBlendFunc(770, 1);
/*    */ 
/* 66 */     AABBPool target = entityBird.getFlyTarget();
/* 67 */     double drawWidth = 0.1D;
/*    */ 
/* 69 */     tessellator.b(5);
/* 70 */     tessellator.a(1.0F, 0.0F, 0.0F, 1.0F);
/* 71 */     for (int j = 0; j < 5; j++)
/*    */     {
/* 73 */       double xOffset = drawWidth;
/* 74 */       double zOffset = drawWidth;
/* 75 */       if ((j == 1) || (j == 2))
/*    */       {
/* 77 */         xOffset += drawWidth * 2.0D;
/*    */       }
/* 79 */       if ((j == 2) || (j == 3))
/*    */       {
/* 81 */         zOffset += drawWidth * 2.0D;
/*    */       }
/* 83 */       tessellator.a(entityRenderOffsetX - entityBird.width / 2.0F + xOffset, entityRenderOffsetY + entityBird.height / 2.0F, entityRenderOffsetZ - entityBird.width / 2.0F + zOffset);
/* 84 */       tessellator.a(target.listAABB + xOffset - RenderEnderCrystal.b, target.nextPoolIndex - RenderEnderCrystal.c, target.maxPoolIndex + zOffset - RenderEnderCrystal.d);
/*    */     }
/* 86 */     tessellator.a();
/*    */ 
/* 88 */     GL11.glDisable(3042);
/* 89 */     GL11.glEnable(2896);
/* 90 */     GL11.glEnable(3553);
/*    */ 
/* 92 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(nm entity)
/*    */   {
/* 98 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderGiantBird
 * JD-Core Version:    0.6.2
 */