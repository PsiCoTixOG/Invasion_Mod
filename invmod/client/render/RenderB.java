/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.client.render.animation.AnimationState;
/*     */ import invmod.common.entity.EntityIMBird;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*     */ import net.minecraft.client.resources.GrassColorReloadListener;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.AABBPool;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderB extends RendererLivingEntity
/*     */ {
/*     */   private ModelBird modelBird;
/*  16 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/bird_tx1.png");
/*     */ 
/*     */   public RenderB()
/*     */   {
/*  20 */     super(new ModelBird(), 0.4F);
/*  21 */     this.modelBird = ((ModelBird)this.i);
/*     */   }
/*     */ 
/*     */   public void renderBz(EntityIMBird entityBird, double renderX, double renderY, double renderZ, float interpYaw, float partialTick)
/*     */   {
/*  26 */     if (entityBird.hasFlyingDebug())
/*     */     {
/*  28 */       renderNavigationVector(entityBird, renderX, renderY, renderZ);
/*     */     }
/*     */ 
/*  31 */     float flapProgress = entityBird.getWingAnimationState().getCurrentAnimationTimeInterp(partialTick);
/*  32 */     this.modelBird.setFlyingAnimations(flapProgress, entityBird.getLegSweepProgress(), entityBird.getRotationRoll());
/*  33 */     super.doRenderLiving(entityBird, renderX, renderY, renderZ, interpYaw, partialTick);
/*     */   }
/*     */ 
/*     */   public void a(nm entity, double d, double d1, double d2, float f, float f1)
/*     */   {
/*  56 */     renderBz((EntityIMBird)entity, d, d1, d2, f, f1);
/*     */   }
/*     */ 
/*     */   private void renderNavigationVector(EntityIMBird entityBird, double entityRenderOffsetX, double entityRenderOffsetY, double entityRenderOffsetZ)
/*     */   {
/*  61 */     ImageBufferDownload tessellator = ImageBufferDownload.imageData;
/*  62 */     GL11.glPushMatrix();
/*     */ 
/*  64 */     GL11.glDisable(3553);
/*  65 */     GL11.glDisable(2896);
/*  66 */     GL11.glEnable(3042);
/*  67 */     GL11.glBlendFunc(770, 1);
/*     */ 
/*  69 */     AABBPool target = entityBird.getFlyTarget();
/*  70 */     double drawWidth = 0.1D;
/*     */ 
/*  72 */     tessellator.b(5);
/*  73 */     tessellator.a(1.0F, 0.0F, 0.0F, 1.0F);
/*  74 */     for (int j = 0; j < 5; j++)
/*     */     {
/*  76 */       double xOffset = drawWidth;
/*  77 */       double zOffset = drawWidth;
/*  78 */       if ((j == 1) || (j == 2))
/*     */       {
/*  80 */         xOffset += drawWidth * 2.0D;
/*     */       }
/*  82 */       if ((j == 2) || (j == 3))
/*     */       {
/*  84 */         zOffset += drawWidth * 2.0D;
/*     */       }
/*  86 */       tessellator.a(entityRenderOffsetX - entityBird.width / 2.0F + xOffset, entityRenderOffsetY + entityBird.height / 2.0F, entityRenderOffsetZ - entityBird.width / 2.0F + zOffset);
/*  87 */       tessellator.a(target.listAABB + xOffset - RenderEnderCrystal.b, target.nextPoolIndex - RenderEnderCrystal.c, target.maxPoolIndex + zOffset - RenderEnderCrystal.d);
/*     */     }
/*  89 */     tessellator.a();
/*     */ 
/*  91 */     GL11.glDisable(3042);
/*  92 */     GL11.glEnable(2896);
/*  93 */     GL11.glEnable(3553);
/*     */ 
/*  95 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   protected GrassColorReloadListener a(nm entity)
/*     */   {
/* 101 */     return texture;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderB
 * JD-Core Version:    0.6.2
 */