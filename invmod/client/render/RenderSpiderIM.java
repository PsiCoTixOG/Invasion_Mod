/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.common.entity.EntityIMSpider;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.resources.GrassColorReloadListener;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.src.bca;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.stats.ThreadStatSyncherReceive;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderSpiderIM extends RendererLivingEntity
/*     */ {
/*  16 */   private static final GrassColorReloadListener t_eyes = new GrassColorReloadListener("textures/entity/spider_eyes.png");
/*  17 */   private static final GrassColorReloadListener t_spider = new GrassColorReloadListener("textures/entity/spider/spider.png");
/*  18 */   private static final GrassColorReloadListener t_jumping = new GrassColorReloadListener("invmod:textures/spiderT2.png");
/*  19 */   private static final GrassColorReloadListener t_mother = new GrassColorReloadListener("invmod:textures/spiderT2b.png");
/*     */ 
/*     */   public RenderSpiderIM()
/*     */   {
/*  23 */     super(new bca(), 1.0F);
/*  24 */     a(new bca());
/*     */   }
/*     */ 
/*     */   protected float setSpiderDeathMaxRotation(EntityIMSpider entityspider)
/*     */   {
/*  29 */     return 180.0F;
/*     */   }
/*     */ 
/*     */   protected int setSpiderEyeBrightness(EntityIMSpider entityspider, int i, float f)
/*     */   {
/*  34 */     if (i != 0)
/*     */     {
/*  36 */       return -1;
/*     */     }
/*     */ 
/*  40 */     a(t_eyes);
/*  41 */     float f1 = 1.0F;
/*  42 */     GL11.glEnable(3042);
/*  43 */     GL11.glDisable(3008);
/*  44 */     GL11.glBlendFunc(1, 1);
/*     */ 
/*  46 */     if (entityspider.isSprinting())
/*     */     {
/*  48 */       GL11.glDepthMask(false);
/*     */     }
/*     */     else
/*     */     {
/*  52 */       GL11.glDepthMask(true);
/*     */     }
/*     */ 
/*  55 */     char c0 = 61680;
/*  56 */     int j = c0 % 65536;
/*  57 */     int k = c0 / 65536;
/*  58 */     ThreadStatSyncherReceive.a(ThreadStatSyncherReceive.b, j / 1.0F, k / 1.0F);
/*  59 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  60 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
/*  61 */     return 1;
/*     */   }
/*     */ 
/*     */   protected void scaleSpider(EntityIMSpider entityspider, float f)
/*     */   {
/*  67 */     float f1 = entityspider.spiderScaleAmount();
/*  68 */     this.shadowSize = f1;
/*  69 */     GL11.glScalef(f1, f1, f1);
/*     */   }
/*     */ 
/*     */   protected void a(EntityLeashKnot entityliving, float f)
/*     */   {
/*  75 */     scaleSpider((EntityIMSpider)entityliving, f);
/*     */   }
/*     */ 
/*     */   protected float a(EntityLeashKnot entityliving)
/*     */   {
/*  81 */     return setSpiderDeathMaxRotation((EntityIMSpider)entityliving);
/*     */   }
/*     */ 
/*     */   protected int a(EntityLeashKnot entityliving, int i, float f)
/*     */   {
/*  87 */     return setSpiderEyeBrightness((EntityIMSpider)entityliving, i, f);
/*     */   }
/*     */ 
/*     */   protected GrassColorReloadListener getTexture(EntityIMSpider entity)
/*     */   {
/*  92 */     switch (entity.getTextureId())
/*     */     {
/*     */     case 0:
/*  95 */       return t_spider;
/*     */     case 1:
/*  97 */       return t_jumping;
/*     */     case 2:
/*  99 */       return t_mother;
/*     */     }
/* 101 */     return t_spider;
/*     */   }
/*     */ 
/*     */   protected GrassColorReloadListener a(nm entity)
/*     */   {
/* 108 */     return getTexture((EntityIMSpider)entity);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderSpiderIM
 * JD-Core Version:    0.6.2
 */