/*     */ package invmod.client.render;
/*     */ 
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ 
/*     */ public class ModelEgg extends ModelMagmaCube
/*     */ {
/*     */   bcr base;
/*     */   bcr l3s4;
/*     */   bcr l3s2;
/*     */   bcr l3s3;
/*     */   bcr l3s1;
/*     */   bcr top;
/*     */   bcr l4s1;
/*     */   bcr l4s4;
/*     */   bcr l2s3;
/*     */   bcr l2s4;
/*     */   bcr l2s1;
/*     */   bcr l4s2;
/*     */   bcr l4s3;
/*     */   bcr l2s2;
/*     */   bcr l1s4;
/*     */   bcr l1s1;
/*     */   bcr l1s2;
/*     */   bcr l1s3;
/*     */ 
/*     */   public ModelEgg()
/*     */   {
/*  11 */     this.textureWidth = 64;
/*  12 */     this.textureHeight = 32;
/*     */ 
/*  14 */     this.base = new bcr(this, 0, 0);
/*  15 */     this.base.a(0.0F, 0.0F, 0.0F, 7, 1, 7);
/*  16 */     this.base.a(1.0F, 0.0F, 1.0F);
/*  17 */     this.base.b(64, 32);
/*  18 */     this.base.i = true;
/*  19 */     setRotation(this.base, 0.0F, 0.0F, 0.0F);
/*  20 */     this.l3s4 = new bcr(this, 10, 22);
/*  21 */     this.l3s4.a(0.0F, 0.0F, 0.0F, 1, 2, 8);
/*  22 */     this.l3s4.a(0.0F, -7.0F, 1.0F);
/*  23 */     this.l3s4.b(64, 32);
/*  24 */     this.l3s4.i = true;
/*  25 */     setRotation(this.l3s4, 0.0F, 0.0F, 0.0F);
/*  26 */     this.l3s2 = new bcr(this, 10, 22);
/*  27 */     this.l3s2.a(0.0F, 0.0F, 0.0F, 1, 2, 8);
/*  28 */     this.l3s2.a(8.0F, -7.0F, 0.0F);
/*  29 */     this.l3s2.b(64, 32);
/*  30 */     this.l3s2.i = true;
/*  31 */     setRotation(this.l3s2, 0.0F, 0.0F, 0.0F);
/*  32 */     this.l3s3 = new bcr(this, 0, 21);
/*  33 */     this.l3s3.a(0.0F, 0.0F, 0.0F, 8, 2, 1);
/*  34 */     this.l3s3.a(0.0F, -7.0F, 0.0F);
/*  35 */     this.l3s3.b(64, 32);
/*  36 */     this.l3s3.i = true;
/*  37 */     setRotation(this.l3s3, 0.0F, 0.0F, 0.0F);
/*  38 */     this.l3s1 = new bcr(this, 0, 21);
/*  39 */     this.l3s1.a(0.0F, 0.0F, 0.0F, 8, 2, 1);
/*  40 */     this.l3s1.a(1.0F, -7.0F, 8.0F);
/*  41 */     this.l3s1.b(64, 32);
/*  42 */     this.l3s1.i = true;
/*  43 */     setRotation(this.l3s1, 0.0F, 0.0F, 0.0F);
/*  44 */     this.top = new bcr(this, 0, 8);
/*  45 */     this.top.a(0.0F, 0.0F, 0.0F, 5, 1, 5);
/*  46 */     this.top.a(2.0F, -11.0F, 2.0F);
/*  47 */     this.top.b(64, 32);
/*  48 */     this.top.i = true;
/*  49 */     setRotation(this.top, 0.0F, 0.0F, 0.0F);
/*  50 */     this.l4s1 = new bcr(this, 0, 24);
/*  51 */     this.l4s1.a(0.0F, 0.0F, 0.0F, 6, 3, 1);
/*  52 */     this.l4s1.a(2.0F, -10.0F, 7.0F);
/*  53 */     this.l4s1.b(64, 32);
/*  54 */     this.l4s1.i = true;
/*  55 */     setRotation(this.l4s1, 0.0F, 0.0F, 0.0F);
/*  56 */     this.l4s4 = new bcr(this, 28, 23);
/*  57 */     this.l4s4.a(0.0F, 0.0F, 0.0F, 1, 3, 6);
/*  58 */     this.l4s4.a(1.0F, -10.0F, 2.0F);
/*  59 */     this.l4s4.b(64, 32);
/*  60 */     this.l4s4.i = true;
/*  61 */     setRotation(this.l4s4, 0.0F, 0.0F, 0.0F);
/*  62 */     this.l2s3 = new bcr(this, 0, 16);
/*  63 */     this.l2s3.a(1.0F, 0.0F, 0.0F, 9, 4, 1);
/*  64 */     this.l2s3.a(-1.0F, -5.0F, -1.0F);
/*  65 */     this.l2s3.b(64, 32);
/*  66 */     this.l2s3.i = true;
/*  67 */     setRotation(this.l2s3, 0.0F, 0.0F, 0.0F);
/*  68 */     this.l2s4 = new bcr(this, 20, 10);
/*  69 */     this.l2s4.a(0.0F, 0.0F, 0.0F, 1, 4, 9);
/*  70 */     this.l2s4.a(-1.0F, -5.0F, 0.0F);
/*  71 */     this.l2s4.b(64, 32);
/*  72 */     this.l2s4.i = true;
/*  73 */     setRotation(this.l2s4, 0.0F, 0.0F, 0.0F);
/*  74 */     this.l2s1 = new bcr(this, 0, 16);
/*  75 */     this.l2s1.a(0.0F, 0.0F, 0.0F, 9, 4, 1);
/*  76 */     this.l2s1.a(0.0F, -5.0F, 9.0F);
/*  77 */     this.l2s1.b(64, 32);
/*  78 */     this.l2s1.i = true;
/*  79 */     setRotation(this.l2s1, 0.0F, 0.0F, 0.0F);
/*  80 */     this.l4s2 = new bcr(this, 28, 23);
/*  81 */     this.l4s2.a(0.0F, 0.0F, 0.0F, 1, 3, 6);
/*  82 */     this.l4s2.a(7.0F, -10.0F, 1.0F);
/*  83 */     this.l4s2.b(64, 32);
/*  84 */     this.l4s2.i = true;
/*  85 */     setRotation(this.l4s2, 0.0F, 0.0F, 0.0F);
/*  86 */     this.l4s3 = new bcr(this, 0, 24);
/*  87 */     this.l4s3.a(0.0F, 0.0F, 0.0F, 6, 3, 1);
/*  88 */     this.l4s3.a(1.0F, -10.0F, 1.0F);
/*  89 */     this.l4s3.b(64, 32);
/*  90 */     this.l4s3.i = true;
/*  91 */     setRotation(this.l4s3, 0.0F, 0.0F, 0.0F);
/*  92 */     this.l2s2 = new bcr(this, 20, 10);
/*  93 */     this.l2s2.a(0.0F, 0.0F, 0.0F, 1, 4, 9);
/*  94 */     this.l2s2.a(9.0F, -5.0F, 0.0F);
/*  95 */     this.l2s2.b(64, 32);
/*  96 */     this.l2s2.i = true;
/*  97 */     setRotation(this.l2s2, 0.0F, 0.0F, 0.0F);
/*  98 */     this.l1s4 = new bcr(this, 28, 0);
/*  99 */     this.l1s4.a(0.0F, 0.0F, 0.0F, 1, 1, 8);
/* 100 */     this.l1s4.a(0.0F, -1.0F, 1.0F);
/* 101 */     this.l1s4.b(64, 32);
/* 102 */     this.l1s4.i = true;
/* 103 */     setRotation(this.l1s4, 0.0F, 0.0F, 0.0F);
/* 104 */     this.l1s1 = new bcr(this, 0, 14);
/* 105 */     this.l1s1.a(0.0F, 0.0F, 0.0F, 8, 1, 1);
/* 106 */     this.l1s1.a(1.0F, -1.0F, 8.0F);
/* 107 */     this.l1s1.b(64, 32);
/* 108 */     this.l1s1.i = true;
/* 109 */     setRotation(this.l1s1, 0.0F, 0.0F, 0.0F);
/* 110 */     this.l1s2 = new bcr(this, 28, 0);
/* 111 */     this.l1s2.a(0.0F, 0.0F, 0.0F, 1, 1, 8);
/* 112 */     this.l1s2.a(8.0F, -1.0F, 0.0F);
/* 113 */     this.l1s2.b(64, 32);
/* 114 */     this.l1s2.i = true;
/* 115 */     setRotation(this.l1s2, 0.0F, 0.0F, 0.0F);
/* 116 */     this.l1s3 = new bcr(this, 0, 14);
/* 117 */     this.l1s3.a(0.0F, 0.0F, 0.0F, 8, 1, 1);
/* 118 */     this.l1s3.a(0.0F, -1.0F, 0.0F);
/* 119 */     this.l1s3.b(64, 32);
/* 120 */     this.l1s3.i = true;
/* 121 */     setRotation(this.l1s3, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 127 */     super.a(entity, f, f1, f2, f3, f4, f5);
/* 128 */     a(f, f1, f2, f3, f4, f5, entity);
/* 129 */     this.base.a(f5);
/* 130 */     this.l3s4.a(f5);
/* 131 */     this.l3s2.a(f5);
/* 132 */     this.l3s3.a(f5);
/* 133 */     this.l3s1.a(f5);
/* 134 */     this.top.a(f5);
/* 135 */     this.l4s1.a(f5);
/* 136 */     this.l4s4.a(f5);
/* 137 */     this.l2s3.a(f5);
/* 138 */     this.l2s4.a(f5);
/* 139 */     this.l2s1.a(f5);
/* 140 */     this.l4s2.a(f5);
/* 141 */     this.l4s3.a(f5);
/* 142 */     this.l2s2.a(f5);
/* 143 */     this.l1s4.a(f5);
/* 144 */     this.l1s1.a(f5);
/* 145 */     this.l1s2.a(f5);
/* 146 */     this.l1s3.a(f5);
/*     */   }
/*     */ 
/*     */   private void setRotation(bcr model, float x, float y, float z)
/*     */   {
/* 151 */     model.f = x;
/* 152 */     model.g = y;
/* 153 */     model.h = z;
/*     */   }
/*     */ 
/*     */   public void a(float f, float f1, float f2, float f3, float f4, float f5, nm entity)
/*     */   {
/* 159 */     super.a(f, f1, f2, f3, f4, f5, entity);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelEgg
 * JD-Core Version:    0.6.2
 */