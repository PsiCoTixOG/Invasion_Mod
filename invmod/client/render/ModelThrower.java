/*     */ package invmod.client.render;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class ModelThrower extends ModelMagmaCube
/*     */ {
/*     */   public bcr bipedHead;
/*     */   public bcr bipedBody;
/*     */   public bcr bipedBody2;
/*     */   public bcr bipedRightArm;
/*     */   public bcr bipedLeftArm;
/*     */   public bcr bipedRightLeg;
/*     */   public bcr bipedLeftLeg;
/*     */   public boolean heldItemLeft;
/*     */   public boolean heldItemRight;
/*     */   public boolean isSneak;
/*     */ 
/*     */   public ModelThrower()
/*     */   {
/*  14 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   public ModelThrower(float f)
/*     */   {
/*  19 */     this(f, 0.0F);
/*     */   }
/*     */ 
/*     */   public ModelThrower(float f, float f1)
/*     */   {
/*  24 */     this.heldItemLeft = false;
/*  25 */     this.heldItemRight = false;
/*  26 */     this.isSneak = false;
/*     */ 
/*  28 */     this.bipedHead = new bcr(this, 16, 14);
/*  29 */     this.bipedHead.a(-2.0F, -2.0F, -2.0F, 4, 2, 4, 0.0F);
/*  30 */     this.bipedHead.a(0.0F, 16.0F, 4.0F);
/*  31 */     this.bipedHead.f = 0.0F;
/*  32 */     this.bipedHead.g = 0.0F;
/*  33 */     this.bipedHead.h = 0.0F;
/*  34 */     this.bipedHead.i = false;
/*  35 */     this.bipedBody = new bcr(this, 0, 1);
/*  36 */     this.bipedBody.a(-7.0F, 2.0F, -4.0F, 12, 4, 9, 0.0F);
/*  37 */     this.bipedBody.a(-0.4F, 16.0F, 3.0F);
/*  38 */     this.bipedBody.f = 0.0F;
/*  39 */     this.bipedBody.g = 0.0F;
/*  40 */     this.bipedBody.h = 0.0F;
/*  41 */     this.bipedBody.i = false;
/*  42 */     this.bipedRightArm = new bcr(this, 39, 22);
/*  43 */     this.bipedRightArm.a(-3.0F, 0.0F, -1.466667F, 3, 7, 3, 0.0F);
/*  44 */     this.bipedRightArm.a(-6.566667F, 16.0F, 5.0F);
/*  45 */     this.bipedRightArm.f = 0.0F;
/*  46 */     this.bipedRightArm.g = 0.0F;
/*  47 */     this.bipedRightArm.h = 0.0F;
/*  48 */     this.bipedRightArm.i = false;
/*  49 */     this.bipedLeftArm = new bcr(this, 40, 16);
/*  50 */     this.bipedLeftArm.a(0.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
/*  51 */     this.bipedLeftArm.a(5.0F, 16.0F, 5.0F);
/*  52 */     this.bipedLeftArm.f = 0.0F;
/*  53 */     this.bipedLeftArm.g = 0.0F;
/*  54 */     this.bipedLeftArm.h = 0.0F;
/*  55 */     this.bipedLeftArm.i = false;
/*  56 */     this.bipedRightLeg = new bcr(this, 0, 14);
/*  57 */     this.bipedRightLeg.a(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
/*  58 */     this.bipedRightLeg.a(-4.066667F, 22.0F, 4.0F);
/*  59 */     this.bipedRightLeg.f = 0.0F;
/*  60 */     this.bipedRightLeg.g = 0.0F;
/*  61 */     this.bipedRightLeg.h = 0.0F;
/*  62 */     this.bipedRightLeg.i = false;
/*  63 */     this.bipedLeftLeg = new bcr(this, 0, 14);
/*  64 */     this.bipedLeftLeg.a(-2.0F, 0.0F, -2.0F, 4, 2, 4, 0.0F);
/*  65 */     this.bipedLeftLeg.a(3.0F, 22.0F, 4.0F);
/*  66 */     this.bipedLeftLeg.f = 0.0F;
/*  67 */     this.bipedLeftLeg.g = 0.0F;
/*  68 */     this.bipedLeftLeg.h = 0.0F;
/*  69 */     this.bipedLeftLeg.i = false;
/*  70 */     this.bipedBody2 = new bcr(this, 0, 23);
/*  71 */     this.bipedBody2.a(-3.666667F, 0.0F, 0.0F, 12, 2, 7, 0.0F);
/*  72 */     this.bipedBody2.a(-3.0F, 16.0F, 0.0F);
/*  73 */     this.bipedBody2.f = 0.0F;
/*  74 */     this.bipedBody2.g = 0.0F;
/*  75 */     this.bipedBody2.h = 0.0F;
/*  76 */     this.bipedBody2.i = false;
/*     */   }
/*     */ 
/*     */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  82 */     a(f, f1, f2, f3, f4, f5, entity);
/*  83 */     this.bipedHead.a(f5);
/*  84 */     this.bipedBody.a(f5);
/*  85 */     this.bipedBody2.a(f5);
/*  86 */     this.bipedRightArm.a(f5);
/*  87 */     this.bipedLeftArm.a(f5);
/*  88 */     this.bipedRightLeg.a(f5);
/*  89 */     this.bipedLeftLeg.a(f5);
/*     */   }
/*     */ 
/*     */   public void a(float f, float f1, float f2, float f3, float f4, float f5, nm entity)
/*     */   {
/*  95 */     this.bipedHead.g = (f3 / 57.29578F);
/*  96 */     this.bipedHead.f = (f4 / 57.29578F);
/*  97 */     this.bipedRightArm.f = (LongHashMapEntry.b(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F);
/*  98 */     this.bipedLeftArm.f = (LongHashMapEntry.b(f * 0.6662F) * 2.0F * f1 * 0.5F);
/*  99 */     this.bipedRightArm.h = 0.0F;
/* 100 */     this.bipedLeftArm.h = 0.0F;
/* 101 */     this.bipedRightLeg.f = (LongHashMapEntry.b(f * 0.6662F) * 1.4F * f1);
/* 102 */     this.bipedLeftLeg.f = (LongHashMapEntry.b(f * 0.6662F + 3.141593F) * 1.4F * f1);
/* 103 */     this.bipedRightLeg.g = 0.0F;
/* 104 */     this.bipedLeftLeg.g = 0.0F;
/* 105 */     if (this.isRiding)
/*     */     {
/* 107 */       this.bipedRightArm.f += -0.6283185F;
/* 108 */       this.bipedLeftArm.f += -0.6283185F;
/* 109 */       this.bipedRightLeg.f = -1.256637F;
/* 110 */       this.bipedLeftLeg.f = -1.256637F;
/* 111 */       this.bipedRightLeg.g = 0.314159F;
/* 112 */       this.bipedLeftLeg.g = -0.314159F;
/*     */     }
/* 114 */     if (this.heldItemLeft)
/*     */     {
/* 116 */       this.bipedLeftArm.f = (this.bipedLeftArm.f * 0.5F - 0.314159F);
/*     */     }
/* 118 */     if (this.heldItemRight)
/*     */     {
/* 120 */       this.bipedRightArm.f = (this.bipedRightArm.f * 0.5F - 0.314159F);
/*     */     }
/* 122 */     this.bipedRightArm.g = 0.0F;
/* 123 */     this.bipedLeftArm.g = 0.0F;
/*     */ 
/* 166 */     this.bipedRightArm.h += LongHashMapEntry.b(f2 * 0.09F) * 0.05F + 0.05F;
/* 167 */     this.bipedLeftArm.h -= LongHashMapEntry.b(f2 * 0.09F) * 0.05F + 0.05F;
/* 168 */     this.bipedRightArm.f += LongHashMapEntry.a(f2 * 0.067F) * 0.05F;
/* 169 */     this.bipedLeftArm.f -= LongHashMapEntry.a(f2 * 0.067F) * 0.05F;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelThrower
 * JD-Core Version:    0.6.2
 */