/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.common.entity.EntityIMZombie;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.client.model.ModelCreeper;
/*     */ import net.minecraft.client.renderer.CallableMouseLocation;
/*     */ import net.minecraft.client.renderer.RenderList;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*     */ import net.minecraft.client.resources.GrassColorReloadListener;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class RenderIMZombie extends RendererLivingEntity
/*     */ {
/*  19 */   private static final GrassColorReloadListener t_old = new GrassColorReloadListener("invmod:textures/zombie_old.png");
/*  20 */   private static final GrassColorReloadListener t_T1a = new GrassColorReloadListener("invmod:textures/zombieT1a.png");
/*  21 */   private static final GrassColorReloadListener t_pig = new GrassColorReloadListener("invmod:textures/pigzombie64x32.png");
/*  22 */   private static final GrassColorReloadListener t_T2 = new GrassColorReloadListener("invmod:textures/zombieT2.png");
/*  23 */   private static final GrassColorReloadListener t_T2a = new GrassColorReloadListener("invmod:textures/zombieT2a.png");
/*  24 */   private static final GrassColorReloadListener t_T3 = new GrassColorReloadListener("invmod:textures/zombieT3.png");
/*  25 */   private static final GrassColorReloadListener t_tar = new GrassColorReloadListener("invmod:textures/zombietar.png");
/*     */   protected ModelCreeper modelBiped;
/*     */   protected ModelBigBiped modelBigBiped;
/*     */ 
/*     */   public RenderIMZombie(ModelCreeper model, float par2)
/*     */   {
/*  32 */     this(model, par2, 1.0F);
/*     */   }
/*     */ 
/*     */   public RenderIMZombie(ModelCreeper model, float par2, float par3)
/*     */   {
/*  37 */     super(model, par2);
/*  38 */     this.modelBiped = model;
/*  39 */     this.modelBigBiped = new ModelBigBiped();
/*     */   }
/*     */ 
/*     */   public void doRenderLiving(EntityLivingBase entity, double par2, double par4, double par6, float par8, float par9)
/*     */   {
/*  45 */     if ((entity instanceof EntityIMZombie))
/*     */     {
/*  47 */       if (((EntityIMZombie)entity).isBigRenderTempHack())
/*     */       {
/*  49 */         this.i = this.modelBigBiped;
/*  50 */         this.modelBigBiped.setSneaking(entity.isRiding());
/*     */       }
/*     */       else
/*     */       {
/*  54 */         this.i = this.modelBiped;
/*     */       }
/*  56 */       super.doRenderLiving(entity, par2, par4, par6, par8, par9);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void a(EntityLeashKnot par1EntityLiving, float par2)
/*     */   {
/*  63 */     float f = ((EntityIMZombie)par1EntityLiving).scaleAmount();
/*  64 */     GL11.glScalef(f, (2.0F + f) / 3.0F, f);
/*     */   }
/*     */ 
/*     */   protected void c(EntityLeashKnot entity, float par2)
/*     */   {
/*  70 */     super.c(entity, par2);
/*  71 */     EnumToolMaterial itemstack = entity.aY();
/*     */ 
/*  73 */     if (itemstack != null)
/*     */     {
/*  75 */       GL11.glPushMatrix();
/*  76 */       if (((EntityIMZombie)entity).isBigRenderTempHack())
/*  77 */         this.modelBigBiped.itemArmPostRender(0.0625F);
/*     */       else {
/*  79 */         this.modelBiped.leg3.c(0.0625F);
/*     */       }
/*  81 */       GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
/*     */ 
/*  83 */       if ((itemstack.EMERALD < 256) && (RenderList.a(BlockEndPortal.s[itemstack.EMERALD].getRenderType())))
/*     */       {
/*  85 */         float f = 0.5F;
/*  86 */         GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
/*  87 */         f *= 0.75F;
/*  88 */         GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
/*  89 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*  90 */         GL11.glScalef(f, -f, f);
/*     */       }
/*  92 */       else if (itemstack.EMERALD == ItemHoe.m.itemID)
/*     */       {
/*  94 */         float f1 = 0.625F;
/*  95 */         GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
/*  96 */         GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
/*  97 */         GL11.glScalef(f1, -f1, f1);
/*  98 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/*  99 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*     */       }
/* 101 */       else if (ItemHoe.g[itemstack.EMERALD].isFull3D())
/*     */       {
/* 103 */         float f2 = 0.625F;
/* 104 */         GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
/* 105 */         GL11.glScalef(f2, -f2, f2);
/* 106 */         GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
/* 107 */         GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       else
/*     */       {
/* 111 */         float f3 = 0.375F;
/* 112 */         GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
/* 113 */         GL11.glScalef(f3, f3, f3);
/* 114 */         GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
/* 115 */         GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 116 */         GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */ 
/* 119 */       this.b.field_76995_b.a(entity, itemstack, 0);
/*     */ 
/* 121 */       if (itemstack.b().requiresMultipleRenderPasses())
/*     */       {
/* 123 */         for (int x = 1; x < itemstack.b().getRenderPasses(itemstack.k()); x++)
/*     */         {
/* 125 */           this.b.field_76995_b.a(entity, itemstack, x);
/*     */         }
/*     */       }
/*     */ 
/* 129 */       GL11.glPopMatrix();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected GrassColorReloadListener getTexture(EntityIMZombie entity)
/*     */   {
/* 135 */     switch (entity.getTextureId())
/*     */     {
/*     */     case 0:
/* 138 */       return t_old;
/*     */     case 1:
/* 140 */       return t_T1a;
/*     */     case 2:
/* 142 */       return t_T2;
/*     */     case 3:
/* 144 */       return t_pig;
/*     */     case 4:
/* 146 */       return t_T2a;
/*     */     case 5:
/* 148 */       return t_tar;
/*     */     case 6:
/* 150 */       return t_T3;
/*     */     }
/* 152 */     return t_old;
/*     */   }
/*     */ 
/*     */   protected GrassColorReloadListener a(nm entity)
/*     */   {
/* 159 */     return getTexture((EntityIMZombie)entity);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderIMZombie
 * JD-Core Version:    0.6.2
 */