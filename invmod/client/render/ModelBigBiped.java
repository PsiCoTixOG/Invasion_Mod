/*     */ package invmod.client.render;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class ModelBigBiped extends ModelMagmaCube
/*     */ {
/*     */   private bcr head;
/*     */   private bcr body;
/*     */   private bcr rightArm;
/*     */   private bcr leftArm;
/*     */   private bcr rightLeg;
/*     */   private bcr leftLeg;
/*     */   private bcr headwear;
/*     */   private int heldItemLeft;
/*     */   private int heldItemRight;
/*     */   private boolean isSneaking;
/*     */   private boolean aimedBow;
/*     */ 
/*     */   public ModelBigBiped()
/*     */   {
/*  24 */     this.isSneaking = false;
/*  25 */     this.textureWidth = 64;
/*  26 */     this.textureHeight = 32;
/*     */ 
/*  28 */     this.head = new bcr(this, 0, 0);
/*  29 */     this.head.a(-3.533333F, -7.0F, -3.5F, 7, 7, 7);
/*  30 */     this.head.a(0.0F, 0.0F, 0.0F);
/*  31 */     this.head.b(64, 32);
/*  32 */     this.head.i = true;
/*  33 */     setRotation(this.head, 0.0F, 0.0F, 0.0F);
/*  34 */     this.body = new bcr(this, 16, 15);
/*  35 */     this.body.a(-5.0F, 0.0F, -3.0F, 10, 12, 5);
/*  36 */     this.body.a(0.0F, 0.0F, 0.0F);
/*  37 */     this.body.b(64, 32);
/*  38 */     this.body.i = true;
/*  39 */     setRotation(this.body, 0.0F, 0.0F, 0.0F);
/*  40 */     this.rightArm = new bcr(this, 46, 15);
/*  41 */     this.rightArm.a(-3.0F, -2.0F, -2.0F, 4, 13, 4);
/*  42 */     this.rightArm.a(-6.0F, 2.0F, 0.0F);
/*  43 */     this.rightArm.b(64, 32);
/*  44 */     this.rightArm.i = true;
/*  45 */     setRotation(this.rightArm, 0.0F, 0.0F, 0.0F);
/*  46 */     this.leftArm = new bcr(this, 46, 15);
/*  47 */     this.leftArm.a(-1.0F, -2.0F, -2.0F, 4, 13, 4);
/*  48 */     this.leftArm.a(6.0F, 2.0F, 0.0F);
/*  49 */     this.leftArm.b(64, 32);
/*  50 */     this.leftArm.i = true;
/*  51 */     setRotation(this.leftArm, 0.0F, 0.0F, 0.0F);
/*  52 */     this.rightLeg = new bcr(this, 0, 16);
/*  53 */     this.rightLeg.a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/*  54 */     this.rightLeg.a(-2.0F, 12.0F, 0.0F);
/*  55 */     this.rightLeg.b(64, 32);
/*  56 */     this.rightLeg.i = true;
/*  57 */     setRotation(this.rightLeg, 0.0F, 0.0F, 0.0F);
/*  58 */     this.leftLeg = new bcr(this, 0, 16);
/*  59 */     this.leftLeg.a(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/*  60 */     this.leftLeg.a(2.0F, 12.0F, 0.0F);
/*  61 */     this.leftLeg.b(64, 32);
/*  62 */     this.leftLeg.i = true;
/*  63 */     setRotation(this.leftLeg, 0.0F, 0.0F, 0.0F);
/*     */ 
/*  65 */     this.headwear = new bcr(this, 32, 0);
/*  66 */     this.headwear.a(-3.533333F, -7.0F, -3.5F, 7, 7, 7, 0.5F);
/*  67 */     this.headwear.a(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/*  73 */     super.a(entity, f, f1, f2, f3, f4, f5);
/*  74 */     a(f, f1, f2, f3, f4, f5, entity);
/*  75 */     this.head.a(f5);
/*  76 */     this.body.a(f5);
/*  77 */     this.rightArm.a(f5);
/*  78 */     this.leftArm.a(f5);
/*  79 */     this.rightLeg.a(f5);
/*  80 */     this.leftLeg.a(f5);
/*     */   }
/*     */ 
/*     */   public void setSneaking(boolean flag)
/*     */   {
/*  85 */     this.isSneaking = flag;
/*     */   }
/*     */ 
/*     */   private void setRotation(bcr model, float x, float y, float z)
/*     */   {
/*  90 */     model.f = x;
/*  91 */     model.g = y;
/*  92 */     model.h = z;
/*     */   }
/*     */ 
/*     */   public void a(float par1, float par2, float par3, float par4, float par5, float par6, nm entity)
/*     */   {
/*  98 */     this.head.g = (par4 / 57.295776F);
/*  99 */     this.head.f = (par5 / 57.295776F);
/* 100 */     this.headwear.g = this.head.g;
/* 101 */     this.headwear.f = this.head.f;
/* 102 */     this.rightArm.f = (LongHashMapEntry.b(par1 * 0.6662F + 3.141593F) * 2.0F * par2 * 0.5F);
/* 103 */     this.leftArm.f = (LongHashMapEntry.b(par1 * 0.6662F) * 2.0F * par2 * 0.5F);
/* 104 */     this.rightArm.h = 0.0F;
/* 105 */     this.leftArm.h = 0.0F;
/* 106 */     this.rightLeg.f = (LongHashMapEntry.b(par1 * 0.6662F) * 1.4F * par2);
/* 107 */     this.leftLeg.f = (LongHashMapEntry.b(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
/* 108 */     this.rightLeg.g = 0.0F;
/* 109 */     this.leftLeg.g = 0.0F;
/*     */ 
/* 111 */     if (this.isRiding)
/*     */     {
/* 113 */       this.rightArm.f += -0.6283186F;
/* 114 */       this.leftArm.f += -0.6283186F;
/* 115 */       this.rightLeg.f = -1.256637F;
/* 116 */       this.leftLeg.f = -1.256637F;
/* 117 */       this.rightLeg.g = 0.3141593F;
/* 118 */       this.leftLeg.g = -0.3141593F;
/*     */     }
/*     */ 
/* 121 */     if (this.heldItemLeft != 0)
/*     */     {
/* 123 */       this.leftArm.f = (this.leftArm.f * 0.5F - 0.3141593F * this.heldItemLeft);
/*     */     }
/*     */ 
/* 126 */     if (this.heldItemRight != 0)
/*     */     {
/* 128 */       this.rightArm.f = (this.rightArm.f * 0.5F - 0.3141593F * this.heldItemRight);
/*     */     }
/*     */ 
/* 131 */     this.rightArm.g = 0.0F;
/* 132 */     this.leftArm.g = 0.0F;
/*     */ 
/* 134 */     if (this.onGround > -9990.0F)
/*     */     {
/* 136 */       float f = this.onGround;
/* 137 */       this.body.g = (LongHashMapEntry.a(LongHashMapEntry.c(f) * 3.141593F * 2.0F) * 0.2F);
/* 138 */       this.rightArm.e = (LongHashMapEntry.a(this.body.g) * 5.0F);
/* 139 */       this.rightArm.c = (-LongHashMapEntry.b(this.body.g) * 5.0F);
/* 140 */       this.leftArm.e = (-LongHashMapEntry.a(this.body.g) * 5.0F);
/* 141 */       this.leftArm.c = (LongHashMapEntry.b(this.body.g) * 5.0F);
/* 142 */       this.rightArm.g += this.body.g;
/* 143 */       this.leftArm.g += this.body.g;
/* 144 */       this.leftArm.f += this.body.g;
/* 145 */       f = 1.0F - this.onGround;
/* 146 */       f *= f;
/* 147 */       f *= f;
/* 148 */       f = 1.0F - f;
/* 149 */       float f2 = LongHashMapEntry.a(f * 3.141593F);
/* 150 */       float f4 = LongHashMapEntry.a(this.onGround * 3.141593F) * -(this.head.f - 0.7F) * 0.75F;
/*     */       bcr tmp570_567 = this.rightArm; tmp570_567.f = ((float)(tmp570_567.f - (f2 * 1.2D + f4)));
/* 152 */       this.rightArm.g += this.body.g * 2.0F;
/* 153 */       this.rightArm.h = (LongHashMapEntry.a(this.onGround * 3.141593F) * -0.4F);
/*     */     }
/*     */ 
/* 157 */     if (this.isSneaking)
/*     */     {
/* 159 */       this.body.f = 0.7F;
/* 160 */       this.body.d = 1.5F;
/* 161 */       this.rightLeg.f -= 0.0F;
/* 162 */       this.leftLeg.f -= 0.0F;
/* 163 */       this.rightArm.f += 0.4F;
/* 164 */       this.leftArm.f += 0.4F;
/* 165 */       this.rightLeg.e = 7.0F;
/* 166 */       this.leftLeg.e = 7.0F;
/* 167 */       this.rightLeg.d = 12.0F;
/* 168 */       this.leftLeg.d = 12.0F;
/* 169 */       this.rightArm.d = 3.5F;
/* 170 */       this.leftArm.d = 3.5F;
/* 171 */       this.head.d = 3.0F;
/*     */     }
/*     */     else
/*     */     {
/* 175 */       this.body.f = 0.0F;
/* 176 */       this.body.d = 0.0F;
/* 177 */       this.rightLeg.e = 0.0F;
/* 178 */       this.leftLeg.e = 0.0F;
/* 179 */       this.rightLeg.d = 12.0F;
/* 180 */       this.leftLeg.d = 12.0F;
/* 181 */       this.rightArm.d = 2.0F;
/* 182 */       this.leftArm.d = 2.0F;
/* 183 */       this.head.d = 0.0F;
/* 184 */       this.rightArm.c = -6.0F;
/* 185 */       this.leftArm.c = 6.0F;
/*     */     }
/*     */ 
/* 188 */     this.rightArm.h += LongHashMapEntry.b(par3 * 0.09F) * 0.05F + 0.05F;
/* 189 */     this.leftArm.h -= LongHashMapEntry.b(par3 * 0.09F) * 0.05F + 0.05F;
/* 190 */     this.rightArm.f += LongHashMapEntry.a(par3 * 0.067F) * 0.05F;
/* 191 */     this.leftArm.f -= LongHashMapEntry.a(par3 * 0.067F) * 0.05F;
/*     */ 
/* 193 */     if (this.aimedBow)
/*     */     {
/* 195 */       float f1 = 0.0F;
/* 196 */       float f3 = 0.0F;
/* 197 */       this.rightArm.h = 0.0F;
/* 198 */       this.leftArm.h = 0.0F;
/* 199 */       this.rightArm.g = (-(0.1F - f1 * 0.6F) + this.head.g);
/* 200 */       this.leftArm.g = (0.1F - f1 * 0.6F + this.head.g + 0.4F);
/* 201 */       this.rightArm.f = (-1.570796F + this.head.f);
/* 202 */       this.leftArm.f = (-1.570796F + this.head.f);
/* 203 */       this.rightArm.f -= f1 * 1.2F - f3 * 0.4F;
/* 204 */       this.leftArm.f -= f1 * 1.2F - f3 * 0.4F;
/* 205 */       this.rightArm.h += LongHashMapEntry.b(par3 * 0.09F) * 0.05F + 0.05F;
/* 206 */       this.leftArm.h -= LongHashMapEntry.b(par3 * 0.09F) * 0.05F + 0.05F;
/* 207 */       this.rightArm.f += LongHashMapEntry.a(par3 * 0.067F) * 0.05F;
/* 208 */       this.leftArm.f -= LongHashMapEntry.a(par3 * 0.067F) * 0.05F;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void itemArmPostRender(float scale)
/*     */   {
/* 214 */     this.rightArm.c(scale);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelBigBiped
 * JD-Core Version:    0.6.2
 */