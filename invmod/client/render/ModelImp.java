/*     */ package invmod.client.render;
/*     */ 
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class ModelImp extends ModelMagmaCube
/*     */ {
/*     */   bcr head;
/*     */   bcr body;
/*     */   bcr rightarm;
/*     */   bcr leftarm;
/*     */   bcr rightleg;
/*     */   bcr leftleg;
/*     */   bcr rshin;
/*     */   bcr rfoot;
/*     */   bcr lshin;
/*     */   bcr lfoot;
/*     */   bcr rhorn;
/*     */   bcr lhorn;
/*     */   bcr bodymid;
/*     */   bcr neck;
/*     */   bcr bodychest;
/*     */   bcr tail;
/*     */   bcr tail2;
/*     */ 
/*     */   public ModelImp()
/*     */   {
/*  14 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   public ModelImp(float f)
/*     */   {
/*  19 */     this(f, 0.0F);
/*     */   }
/*     */ 
/*     */   public ModelImp(float f, float f1)
/*     */   {
/*  24 */     this.head = new bcr(this, 44, 0);
/*  25 */     this.head.a(-2.733333F, -3.0F, -2.0F, 5, 3, 4);
/*  26 */     this.head.a(-0.4F, 9.8F, -3.3F);
/*  27 */     this.head.f = 0.15807F;
/*  28 */     this.head.g = 0.0F;
/*  29 */     this.head.h = 0.0F;
/*  30 */     this.head.i = false;
/*  31 */     this.body = new bcr(this, 23, 1);
/*  32 */     this.body.a(-4.0F, 0.0F, -4.0F, 7, 4, 3);
/*  33 */     this.body.a(0.0F, 9.1F, -0.8666667F);
/*  34 */     this.body.f = 0.64346F;
/*  35 */     this.body.g = 0.0F;
/*  36 */     this.body.h = 0.0F;
/*  37 */     this.body.i = false;
/*  38 */     this.rightarm = new bcr(this, 26, 9);
/*  39 */     this.rightarm.a(-2.0F, -0.7333333F, -1.133333F, 2, 7, 2);
/*  40 */     this.rightarm.a(-4.0F, 10.8F, -2.066667F);
/*  41 */     this.rightarm.f = 0.0F;
/*  42 */     this.rightarm.g = 0.0F;
/*  43 */     this.rightarm.h = 0.0F;
/*  44 */     this.rightarm.i = false;
/*  45 */     this.leftarm = new bcr(this, 18, 9);
/*  46 */     this.leftarm.a(0.0F, -0.8666667F, -1.0F, 2, 7, 2);
/*  47 */     this.leftarm.a(3.0F, 10.8F, -2.1F);
/*  48 */     this.leftarm.f = 0.0F;
/*  49 */     this.leftarm.g = 0.0F;
/*  50 */     this.leftarm.h = 0.0F;
/*  51 */     this.leftarm.i = false;
/*  52 */     this.rightleg = new bcr(this, 0, 17);
/*  53 */     this.rightleg.a(-1.0F, 0.0F, -2.0F, 2, 4, 3);
/*  54 */     this.rightleg.a(-2.0F, 16.9F, -1.0F);
/*  55 */     this.rightleg.f = -0.15807F;
/*  56 */     this.rightleg.g = 0.0F;
/*  57 */     this.rightleg.h = 0.0F;
/*  58 */     this.rightleg.i = false;
/*  59 */     this.leftleg = new bcr(this, 0, 24);
/*  60 */     this.leftleg.a(-1.0F, 0.0F, -2.0F, 2, 4, 3);
/*  61 */     this.leftleg.a(1.0F, 17.0F, -1.0F);
/*  62 */     this.leftleg.f = -0.15919F;
/*  63 */     this.leftleg.g = 0.0F;
/*  64 */     this.leftleg.h = 0.0F;
/*  65 */     this.leftleg.i = false;
/*  66 */     this.rshin = new bcr(this, 10, 17);
/*  67 */     this.rshin.a(-2.0F, 0.6F, -4.4F, 2, 3, 2);
/*  68 */     this.rshin.a(-1.0F, 16.9F, -1.0F);
/*  69 */     this.rshin.f = 0.82623F;
/*  70 */     this.rshin.g = 0.0F;
/*  71 */     this.rshin.h = 0.0F;
/*  72 */     this.rshin.i = false;
/*  73 */     this.rfoot = new bcr(this, 18, 18);
/*  74 */     this.rfoot.a(-2.0F, 4.2F, -1.0F, 2, 3, 2);
/*  75 */     this.rfoot.a(-1.0F, 16.9F, -1.0F);
/*  76 */     this.rfoot.f = -0.01403F;
/*  77 */     this.rfoot.g = 0.0F;
/*  78 */     this.rfoot.h = 0.0F;
/*  79 */     this.rfoot.i = false;
/*  80 */     this.lshin = new bcr(this, 10, 22);
/*  81 */     this.lshin.a(-1.0F, 0.6F, -4.433333F, 2, 3, 2);
/*  82 */     this.lshin.a(1.0F, 17.0F, -1.0F);
/*  83 */     this.lshin.f = 0.82461F;
/*  84 */     this.lshin.g = 0.0F;
/*  85 */     this.lshin.h = 0.0F;
/*  86 */     this.lshin.i = false;
/*  87 */     this.lfoot = new bcr(this, 10, 27);
/*  88 */     this.lfoot.a(-1.0F, 4.2F, -1.0F, 2, 3, 2);
/*  89 */     this.lfoot.a(1.0F, 17.0F, -1.0F);
/*  90 */     this.lfoot.f = -0.01214F;
/*  91 */     this.lfoot.g = 0.0F;
/*  92 */     this.lfoot.h = 0.0F;
/*  93 */     this.lfoot.i = false;
/*  94 */     this.rhorn = new bcr(this, 0, 0);
/*  95 */     this.rhorn.a(0.0F, 0.0F, 0.0F, 1, 1, 1);
/*  96 */     this.rhorn.a(-2.5F, 6.0F, -5.0F);
/*  97 */     this.rhorn.f = 0.0F;
/*  98 */     this.rhorn.g = 0.0F;
/*  99 */     this.rhorn.h = 0.0F;
/* 100 */     this.rhorn.i = false;
/* 101 */     this.lhorn = new bcr(this, 0, 2);
/* 102 */     this.lhorn.a(0.0F, 0.0F, 0.0F, 1, 1, 1);
/* 103 */     this.lhorn.a(0.5F, 6.0F, -5.0F);
/* 104 */     this.lhorn.f = 0.0F;
/* 105 */     this.lhorn.g = 0.0F;
/* 106 */     this.lhorn.h = 0.0F;
/* 107 */     this.lhorn.i = false;
/* 108 */     this.bodymid = new bcr(this, 1, 1);
/* 109 */     this.bodymid.a(0.0F, 0.0F, 0.0F, 7, 5, 3);
/* 110 */     this.bodymid.a(-4.0F, 12.46667F, -2.266667F);
/* 111 */     this.bodymid.f = -0.15807F;
/* 112 */     this.bodymid.g = 0.0F;
/* 113 */     this.bodymid.h = 0.0F;
/* 114 */     this.bodymid.i = false;
/* 115 */     this.neck = new bcr(this, 44, 7);
/* 116 */     this.neck.a(0.0F, 0.0F, 0.0F, 3, 2, 2);
/* 117 */     this.neck.a(-2.0F, 9.6F, -4.033333F);
/* 118 */     this.neck.f = 0.27662F;
/* 119 */     this.neck.g = 0.0F;
/* 120 */     this.neck.h = 0.0F;
/* 121 */     this.neck.i = false;
/* 122 */     this.bodychest = new bcr(this, 0, 9);
/* 123 */     this.bodychest.a(0.0F, -1.0F, 0.0F, 7, 6, 2);
/* 124 */     this.bodychest.a(-4.0F, 12.36667F, -3.8F);
/* 125 */     this.bodychest.f = 0.31614F;
/* 126 */     this.bodychest.g = 0.0F;
/* 127 */     this.bodychest.h = 0.0F;
/* 128 */     this.bodychest.i = false;
/* 129 */     this.tail = new bcr(this, 18, 23);
/* 130 */     this.tail.a(0.0F, 0.0F, 0.0F, 1, 8, 1);
/* 131 */     this.tail.a(-1.0F, 15.0F, -0.6666667F);
/* 132 */     this.tail.f = 0.47304F;
/* 133 */     this.tail.g = 0.0F;
/* 134 */     this.tail.h = 0.0F;
/* 135 */     this.tail.i = false;
/* 136 */     this.tail2 = new bcr(this, 22, 23);
/* 137 */     this.tail2.a(0.0F, 0.0F, 0.0F, 1, 4, 1);
/* 138 */     this.tail2.a(-1.0F, 22.1F, 2.9F);
/* 139 */     this.tail2.f = 1.38309F;
/* 140 */     this.tail2.g = 0.0F;
/* 141 */     this.tail2.h = 0.0F;
/* 142 */     this.tail2.i = false;
/*     */   }
/*     */ 
/*     */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 149 */     super.a(entity, f, f1, f2, f3, f4, f5);
/* 150 */     a(f, f1, f2, f3, f4, f5, entity);
/* 151 */     this.head.a(f5);
/* 152 */     this.body.a(f5);
/* 153 */     this.rightarm.a(f5);
/* 154 */     this.leftarm.a(f5);
/* 155 */     this.rightleg.a(f5);
/* 156 */     this.leftleg.a(f5);
/* 157 */     this.rshin.a(f5);
/* 158 */     this.rfoot.a(f5);
/* 159 */     this.lshin.a(f5);
/* 160 */     this.lfoot.a(f5);
/* 161 */     this.rhorn.a(f5);
/* 162 */     this.lhorn.a(f5);
/* 163 */     this.bodymid.a(f5);
/* 164 */     this.neck.a(f5);
/* 165 */     this.bodychest.a(f5);
/* 166 */     this.tail.a(f5);
/* 167 */     this.tail2.a(f5);
/*     */   }
/*     */ 
/*     */   public void a(float f, float f1, float f2, float f3, float f4, float f5, nm entity)
/*     */   {
/* 173 */     this.head.g = (f3 / 57.29578F);
/* 174 */     this.head.f = (f4 / 57.29578F);
/* 175 */     this.rightarm.f = (LongHashMapEntry.b(f * 0.6662F + 3.141593F) * 2.0F * f1 * 0.5F);
/* 176 */     this.leftarm.f = (LongHashMapEntry.b(f * 0.6662F) * 2.0F * f1 * 0.5F);
/* 177 */     this.rightarm.h = 0.0F;
/* 178 */     this.leftarm.h = 0.0F;
/*     */ 
/* 180 */     this.rightleg.f = (LongHashMapEntry.b(f * 0.6662F) * 1.4F * f1 - 0.158F);
/* 181 */     this.rshin.f = (LongHashMapEntry.b(f * 0.6662F) * 1.4F * f1 + 0.82623F);
/* 182 */     this.rfoot.f = (LongHashMapEntry.b(f * 0.6662F) * 1.4F * f1 - 0.01403F);
/*     */ 
/* 184 */     this.leftleg.f = (LongHashMapEntry.b(f * 0.6662F + 3.141593F) * 1.4F * f1 - 0.15919F);
/* 185 */     this.lshin.f = (LongHashMapEntry.b(f * 0.6662F + 3.141593F) * 1.4F * f1 + 0.82461F);
/* 186 */     this.lfoot.f = (LongHashMapEntry.b(f * 0.6662F + 3.141593F) * 1.4F * f1 - 0.01214F);
/*     */ 
/* 188 */     this.rightleg.g = 0.0F;
/* 189 */     this.rshin.g = 0.0F;
/* 190 */     this.rfoot.g = 0.0F;
/*     */ 
/* 192 */     this.leftleg.g = 0.0F;
/* 193 */     this.lshin.g = 0.0F;
/* 194 */     this.lfoot.g = 0.0F;
/*     */ 
/* 205 */     this.rightarm.g = 0.0F;
/* 206 */     this.leftarm.g = 0.0F;
/*     */ 
/* 208 */     this.rightarm.h += LongHashMapEntry.b(f2 * 0.09F) * 0.05F + 0.05F;
/* 209 */     this.leftarm.h -= LongHashMapEntry.b(f2 * 0.09F) * 0.05F + 0.05F;
/* 210 */     this.rightarm.f += LongHashMapEntry.a(f2 * 0.067F) * 0.05F;
/* 211 */     this.leftarm.f -= LongHashMapEntry.a(f2 * 0.067F) * 0.05F;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelImp
 * JD-Core Version:    0.6.2
 */