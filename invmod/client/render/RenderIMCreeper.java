/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.common.entity.EntityIMCreeper;
/*     */ import net.minecraft.client.model.ModelChest;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.resources.GrassColorReloadListener;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderIMCreeper extends RendererLivingEntity
/*     */ {
/*  16 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("textures/entity/creeper/creeper.png");
/*     */   private ModelMagmaCube field_27008_a;
/*     */ 
/*     */   public RenderIMCreeper()
/*     */   {
/*  22 */     super(new ModelChest(), 0.5F);
/*  23 */     this.field_27008_a = new ModelChest(2.0F);
/*     */   }
/*     */ 
/*     */   protected void updateCreeperScale(EntityIMCreeper par1EntityCreeper, float par2)
/*     */   {
/*  31 */     EntityIMCreeper entitycreeper = par1EntityCreeper;
/*  32 */     float f = entitycreeper.setCreeperFlashTime(par2);
/*  33 */     float f1 = 1.0F + LongHashMapEntry.a(f * 100.0F) * f * 0.01F;
/*     */ 
/*  35 */     if (f < 0.0F)
/*     */     {
/*  37 */       f = 0.0F;
/*     */     }
/*     */ 
/*  40 */     if (f > 1.0F)
/*     */     {
/*  42 */       f = 1.0F;
/*     */     }
/*     */ 
/*  45 */     f *= f;
/*  46 */     f *= f;
/*  47 */     float f2 = (1.0F + f * 0.4F) * f1;
/*  48 */     float f3 = (1.0F + f * 0.1F) / f1;
/*  49 */     GL11.glScalef(f2, f3, f2);
/*     */   }
/*     */ 
/*     */   protected int updateCreeperColorMultiplier(EntityIMCreeper par1EntityCreeper, float par2, float par3)
/*     */   {
/*  57 */     EntityIMCreeper entitycreeper = par1EntityCreeper;
/*  58 */     float f = entitycreeper.setCreeperFlashTime(par3);
/*     */ 
/*  60 */     if ((int)(f * 10.0F) % 2 == 0)
/*     */     {
/*  62 */       return 0;
/*     */     }
/*     */ 
/*  65 */     int i = (int)(f * 0.2F * 255.0F);
/*     */ 
/*  67 */     if (i < 0)
/*     */     {
/*  69 */       i = 0;
/*     */     }
/*     */ 
/*  72 */     if (i > 255)
/*     */     {
/*  74 */       i = 255;
/*     */     }
/*     */ 
/*  77 */     char c = 'ÿ';
/*  78 */     char c1 = 'ÿ';
/*  79 */     char c2 = 'ÿ';
/*  80 */     return i << 24 | c << '\020' | c1 << '\b' | c2;
/*     */   }
/*     */ 
/*     */   protected int func_27007_b(EntityIMCreeper par1EntityCreeper, int par2, float par3)
/*     */   {
/*  85 */     return -1;
/*     */   }
/*     */ 
/*     */   protected void a(EntityLeashKnot par1EntityLiving, float par2)
/*     */   {
/*  95 */     updateCreeperScale((EntityIMCreeper)par1EntityLiving, par2);
/*     */   }
/*     */ 
/*     */   protected int a(EntityLeashKnot par1EntityLiving, float par2, float par3)
/*     */   {
/* 104 */     return updateCreeperColorMultiplier((EntityIMCreeper)par1EntityLiving, par2, par3);
/*     */   }
/*     */ 
/*     */   protected int b(EntityLeashKnot par1EntityLiving, int par2, float par3)
/*     */   {
/* 110 */     return func_27007_b((EntityIMCreeper)par1EntityLiving, par2, par3);
/*     */   }
/*     */ 
/*     */   protected GrassColorReloadListener a(nm entity)
/*     */   {
/* 116 */     return texture;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderIMCreeper
 * JD-Core Version:    0.6.2
 */