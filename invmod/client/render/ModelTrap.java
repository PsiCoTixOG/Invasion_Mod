/*     */ package invmod.client.render;
/*     */ 
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ 
/*     */ public class ModelTrap extends ModelMagmaCube
/*     */ {
/*     */   bcr Core;
/*     */   bcr CoreFire;
/*     */   bcr Clasp1a;
/*     */   bcr Clasp1b;
/*     */   bcr Clasp2b;
/*     */   bcr Clasp2a;
/*     */   bcr Clasp3a;
/*     */   bcr Clasp3b;
/*     */   bcr Clasp4a;
/*     */   bcr Clasp4b;
/*     */   bcr Base;
/*     */   bcr BaseS1;
/*     */   bcr BaseS2;
/*     */ 
/*     */   public ModelTrap()
/*     */   {
/*  27 */     this.textureWidth = 32;
/*  28 */     this.textureHeight = 32;
/*  29 */     this.Core = new bcr(this, 0, 13);
/*  30 */     this.Core.a(0.0F, 0.0F, 0.0F, 4, 2, 4);
/*  31 */     this.Core.a(-2.0F, -2.0F, -2.0F);
/*  32 */     this.Core.b(32, 32);
/*  33 */     this.Core.i = true;
/*  34 */     this.CoreFire = new bcr(this, 5, 7);
/*  35 */     this.CoreFire.a(0.0F, 0.0F, 0.0F, 4, 2, 4);
/*  36 */     this.CoreFire.a(-2.0F, -2.0F, -2.0F);
/*  37 */     this.CoreFire.b(32, 32);
/*  38 */     this.CoreFire.i = true;
/*  39 */     setRotation(this.Core, 0.0F, 0.0F, 0.0F);
/*  40 */     this.Clasp1a = new bcr(this, 0, 0);
/*  41 */     this.Clasp1a.a(0.0F, 0.0F, 0.0F, 2, 2, 1);
/*  42 */     this.Clasp1a.a(-1.0F, -2.0F, 2.0F);
/*  43 */     this.Clasp1a.b(32, 32);
/*  44 */     this.Clasp1a.i = true;
/*  45 */     setRotation(this.Clasp1a, 0.0F, 0.0F, 0.0F);
/*  46 */     this.Clasp1b = new bcr(this, 0, 7);
/*  47 */     this.Clasp1b.a(0.0F, 0.0F, 0.0F, 2, 1, 2);
/*  48 */     this.Clasp1b.a(-1.0F, -1.0F, 3.0F);
/*  49 */     this.Clasp1b.b(32, 32);
/*  50 */     this.Clasp1b.i = true;
/*  51 */     setRotation(this.Clasp1b, 0.0F, 0.0F, 0.0F);
/*  52 */     this.Clasp2b = new bcr(this, 0, 19);
/*  53 */     this.Clasp2b.a(0.0F, 0.0F, 0.0F, 2, 1, 2);
/*  54 */     this.Clasp2b.a(3.0F, -1.0F, -1.0F);
/*  55 */     this.Clasp2b.b(32, 32);
/*  56 */     this.Clasp2b.i = true;
/*  57 */     setRotation(this.Clasp2b, 0.0F, 0.0F, 0.0F);
/*  58 */     this.Clasp2a = new bcr(this, 0, 3);
/*  59 */     this.Clasp2a.a(0.0F, 0.0F, 0.0F, 1, 2, 2);
/*  60 */     this.Clasp2a.a(2.0F, -2.0F, -1.0F);
/*  61 */     this.Clasp2a.b(32, 32);
/*  62 */     this.Clasp2a.i = true;
/*  63 */     setRotation(this.Clasp2a, 0.0F, 0.0F, 0.0F);
/*  64 */     this.Clasp3a = new bcr(this, 0, 0);
/*  65 */     this.Clasp3a.a(0.0F, 0.0F, 0.0F, 2, 2, 1);
/*  66 */     this.Clasp3a.a(-1.0F, -2.0F, -3.0F);
/*  67 */     this.Clasp3a.b(32, 32);
/*  68 */     this.Clasp3a.i = true;
/*  69 */     setRotation(this.Clasp3a, 0.0F, 0.0F, 0.0F);
/*  70 */     this.Clasp3b = new bcr(this, 0, 7);
/*  71 */     this.Clasp3b.a(0.0F, 0.0F, 0.0F, 2, 1, 2);
/*  72 */     this.Clasp3b.a(-1.0F, -1.0F, -5.0F);
/*  73 */     this.Clasp3b.b(32, 32);
/*  74 */     this.Clasp3b.i = true;
/*  75 */     setRotation(this.Clasp3b, 0.0F, 0.0F, 0.0F);
/*  76 */     this.Clasp4a = new bcr(this, 0, 3);
/*  77 */     this.Clasp4a.a(0.0F, 0.0F, 0.0F, 1, 2, 2);
/*  78 */     this.Clasp4a.a(-3.0F, -2.0F, -1.0F);
/*  79 */     this.Clasp4a.b(32, 32);
/*  80 */     this.Clasp4a.i = true;
/*  81 */     setRotation(this.Clasp4a, 0.0F, 0.0F, 0.0F);
/*  82 */     this.Clasp4b = new bcr(this, 0, 19);
/*  83 */     this.Clasp4b.a(0.0F, 0.0F, 0.0F, 2, 1, 2);
/*  84 */     this.Clasp4b.a(-5.0F, -1.0F, -1.0F);
/*  85 */     this.Clasp4b.b(32, 32);
/*  86 */     this.Clasp4b.i = true;
/*  87 */     setRotation(this.Clasp4b, 0.0F, 0.0F, 0.0F);
/*  88 */     this.Base = new bcr(this, 0, 23);
/*  89 */     this.Base.a(0.0F, 0.0F, 0.0F, 4, 1, 2);
/*  90 */     this.Base.a(-2.0F, -1.0F, -1.0F);
/*  91 */     this.Base.b(32, 32);
/*  92 */     this.Base.i = true;
/*  93 */     setRotation(this.Base, 0.0F, 0.0F, 0.0F);
/*  94 */     this.BaseS1 = new bcr(this, 0, 27);
/*  95 */     this.BaseS1.a(0.0F, 0.0F, 0.0F, 2, 1, 1);
/*  96 */     this.BaseS1.a(-1.0F, -1.0F, 1.0F);
/*  97 */     this.BaseS1.b(32, 32);
/*  98 */     this.BaseS1.i = true;
/*  99 */     setRotation(this.BaseS1, 0.0F, 0.0F, 0.0F);
/* 100 */     this.BaseS2 = new bcr(this, 0, 27);
/* 101 */     this.BaseS2.a(0.0F, 0.0F, 0.0F, 2, 1, 1);
/* 102 */     this.BaseS2.a(-1.0F, -1.0F, -2.0F);
/* 103 */     this.BaseS2.b(32, 32);
/* 104 */     this.BaseS2.i = true;
/* 105 */     setRotation(this.BaseS2, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public void render(nm entity, float f, float f1, float f2, float f3, float f4, float f5, boolean isEmpty, int type)
/*     */   {
/* 110 */     super.a(entity, f, f1, f2, f3, f4, f5);
/* 111 */     a(f, f1, f2, f3, f4, f5, entity);
/*     */ 
/* 113 */     if (!isEmpty)
/*     */     {
/* 115 */       if (type == 1)
/* 116 */         this.Core.a(f5);
/* 117 */       else if (type == 2) {
/* 118 */         this.CoreFire.a(f5);
/*     */       }
/*     */     }
/* 121 */     this.Clasp1a.a(f5);
/* 122 */     this.Clasp1b.a(f5);
/* 123 */     this.Clasp2b.a(f5);
/* 124 */     this.Clasp2a.a(f5);
/* 125 */     this.Clasp3a.a(f5);
/* 126 */     this.Clasp3b.a(f5);
/* 127 */     this.Clasp4a.a(f5);
/* 128 */     this.Clasp4b.a(f5);
/* 129 */     this.Base.a(f5);
/* 130 */     this.BaseS1.a(f5);
/* 131 */     this.BaseS2.a(f5);
/*     */   }
/*     */ 
/*     */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 137 */     render(entity, f, f1, f2, f3, f4, f5, false, 0);
/*     */   }
/*     */ 
/*     */   private void setRotation(bcr model, float x, float y, float z)
/*     */   {
/* 142 */     model.f = x;
/* 143 */     model.g = y;
/* 144 */     model.h = z;
/*     */   }
/*     */ 
/*     */   public void a(float f, float f1, float f2, float f3, float f4, float f5, nm entity)
/*     */   {
/* 150 */     super.a(f, f1, f2, f3, f4, f5, entity);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelTrap
 * JD-Core Version:    0.6.2
 */