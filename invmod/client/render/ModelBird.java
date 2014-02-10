/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.client.render.animation.InterpType;
/*     */ import invmod.client.render.animation.KeyFrame;
/*     */ import invmod.client.render.animation.ModelAnimator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class ModelBird extends ModelMagmaCube
/*     */ {
/*     */   private ModelAnimator animationWingFlap;
/*     */   private bcr body;
/*     */   private bcr rightwing1;
/*     */   private bcr leftwing1;
/*     */   private bcr head;
/*     */   private bcr beak;
/*     */   private bcr leftwing2;
/*     */   private bcr rightwing2;
/*     */   private bcr tail;
/*     */   private bcr legR;
/*     */   private bcr ltoeR;
/*     */   private bcr btoeR;
/*     */   private bcr rtoeR;
/*     */   private bcr thighR;
/*     */   private bcr legL;
/*     */   private bcr ltoeL;
/*     */   private bcr btoeL;
/*     */   private bcr rtoeL;
/*     */   private bcr thighL;
/*     */ 
/*     */   public ModelBird()
/*     */   {
/*  39 */     this.textureWidth = 64;
/*  40 */     this.textureHeight = 32;
/*     */ 
/*  42 */     this.body = new bcr(this, 24, 0);
/*  43 */     this.body.a(-3.5F, 0.0F, -3.5F, 7, 12, 7);
/*  44 */     this.body.a(3.5F, 7.0F, 3.5F);
/*  45 */     this.body.b(64, 32);
/*  46 */     this.body.i = true;
/*  47 */     setRotation(this.body, 0.0F, 0.0F, 0.0F);
/*  48 */     this.rightwing1 = new bcr(this, 0, 22);
/*  49 */     this.rightwing1.a(-7.0F, -1.0F, -1.0F, 7, 9, 1);
/*  50 */     this.rightwing1.a(-3.5F, 2.0F, 3.5F);
/*  51 */     this.rightwing1.b(64, 32);
/*  52 */     this.rightwing1.i = false;
/*  53 */     setRotation(this.rightwing1, 0.0F, 0.0F, 0.0F);
/*  54 */     this.rightwing2 = new bcr(this, 16, 24);
/*  55 */     this.rightwing2.a(-14.0F, -1.0F, -0.5F, 14, 7, 1);
/*  56 */     this.rightwing2.a(-7.0F, 0.0F, -0.5F);
/*  57 */     this.rightwing2.b(64, 32);
/*  58 */     this.rightwing2.i = false;
/*  59 */     setRotation(this.rightwing2, 0.0F, 0.0F, 0.0F);
/*  60 */     this.rightwing1.a(this.rightwing2);
/*  61 */     this.leftwing1 = new bcr(this, 0, 22);
/*  62 */     this.leftwing1.a(0.0F, -1.0F, -1.0F, 7, 9, 1);
/*  63 */     this.leftwing1.a(3.5F, 2.0F, 3.5F);
/*  64 */     this.leftwing1.b(64, 32);
/*  65 */     this.leftwing1.i = true;
/*  66 */     setRotation(this.leftwing1, 0.0F, 0.0F, 0.0F);
/*  67 */     this.leftwing2 = new bcr(this, 16, 24);
/*  68 */     this.leftwing2.a(0.0F, -1.0F, -0.5F, 14, 7, 1);
/*  69 */     this.leftwing2.a(7.0F, 0.0F, -0.5F);
/*  70 */     this.leftwing2.b(64, 32);
/*  71 */     this.leftwing2.i = true;
/*  72 */     setRotation(this.leftwing2, 0.0F, 0.0F, 0.0F);
/*  73 */     this.leftwing1.a(this.leftwing2);
/*  74 */     this.head = new bcr(this, 2, 0);
/*  75 */     this.head.a(-2.5F, -5.0F, -4.0F, 5, 6, 6);
/*  76 */     this.head.a(0.0F, 0.5F, 1.5F);
/*  77 */     this.head.b(64, 32);
/*  78 */     this.head.i = true;
/*  79 */     setRotation(this.head, 0.0F, 0.0F, 0.0F);
/*  80 */     this.beak = new bcr(this, 19, 0);
/*  81 */     this.beak.a(-0.5F, 0.0F, -2.0F, 1, 2, 2);
/*  82 */     this.beak.a(0.0F, -3.0F, -4.0F);
/*  83 */     this.beak.b(64, 32);
/*  84 */     this.beak.i = true;
/*  85 */     setRotation(this.beak, 0.0F, 0.0F, 0.0F);
/*  86 */     this.head.a(this.beak);
/*  87 */     this.tail = new bcr(this, 0, 12);
/*  88 */     this.tail.a(-3.0F, 0.0F, 0.0F, 5, 9, 1);
/*  89 */     this.tail.a(0.5F, 12.0F, 2.5F);
/*  90 */     this.tail.b(64, 32);
/*  91 */     this.tail.i = true;
/*  92 */     setRotation(this.tail, 0.446143F, 0.0F, 0.0F);
/*  93 */     this.legR = new bcr(this, 13, 12);
/*  94 */     this.legR.a(-0.5F, 0.0F, -0.5F, 1, 5, 1);
/*  95 */     this.legR.a(0.0F, 0.0F, 0.0F);
/*  96 */     this.legR.b(64, 32);
/*  97 */     this.legR.i = false;
/*  98 */     setRotation(this.legR, 0.0F, 0.0F, 0.0F);
/*  99 */     this.ltoeR = new bcr(this, 0, 0);
/* 100 */     this.ltoeR.a(0.0F, 0.0F, -2.0F, 1, 1, 2);
/* 101 */     this.ltoeR.a(0.2F, 4.0F, 0.0F);
/* 102 */     this.ltoeR.b(64, 32);
/* 103 */     this.ltoeR.i = false;
/* 104 */     setRotation(this.ltoeR, 0.0F, -0.1396263F, 0.0F);
/* 105 */     this.legR.a(this.ltoeR);
/* 106 */     this.btoeR = new bcr(this, 0, 0);
/* 107 */     this.btoeR.a(-0.5F, 0.0F, 0.0F, 1, 1, 2);
/* 108 */     this.btoeR.a(0.0F, 4.0F, 0.0F);
/* 109 */     this.btoeR.b(64, 32);
/* 110 */     this.btoeR.i = false;
/* 111 */     setRotation(this.btoeR, -0.349066F, 0.0F, 0.0F);
/* 112 */     this.legR.a(this.btoeR);
/* 113 */     this.rtoeR = new bcr(this, 0, 0);
/* 114 */     this.rtoeR.a(-1.0F, 0.0F, -2.0F, 1, 1, 2);
/* 115 */     this.rtoeR.a(-0.2F, 4.0F, 0.0F);
/* 116 */     this.rtoeR.b(64, 32);
/* 117 */     this.rtoeR.i = false;
/* 118 */     setRotation(this.rtoeR, 0.0F, 0.1396263F, 0.0F);
/* 119 */     this.legR.a(this.rtoeR);
/* 120 */     this.thighR = new bcr(this, 13, 18);
/* 121 */     this.thighR.a(-1.0F, 0.0F, -1.0F, 2, 2, 2);
/* 122 */     this.thighR.a(-1.5F, 12.0F, -1.0F);
/* 123 */     this.thighR.b(64, 32);
/* 124 */     this.thighR.i = false;
/* 125 */     setRotation(this.thighR, 0.0F, 0.0F, 0.0F);
/* 126 */     this.thighR.a(this.legR);
/* 127 */     this.legL = new bcr(this, 13, 12);
/* 128 */     this.legL.a(-0.5F, 0.0F, -0.5F, 1, 5, 1);
/* 129 */     this.legL.a(0.0F, 0.0F, 0.0F);
/* 130 */     this.legL.b(64, 32);
/* 131 */     this.legL.i = true;
/* 132 */     setRotation(this.legL, 0.0F, 0.0F, 0.0F);
/* 133 */     this.ltoeL = new bcr(this, 0, 0);
/* 134 */     this.ltoeL.a(0.0F, 0.0F, -2.0F, 1, 1, 2);
/* 135 */     this.ltoeL.a(0.2F, 4.0F, 0.0F);
/* 136 */     this.ltoeL.b(64, 32);
/* 137 */     this.ltoeL.i = true;
/* 138 */     setRotation(this.ltoeL, 0.0F, -0.1396263F, 0.0F);
/* 139 */     this.legL.a(this.ltoeL);
/* 140 */     this.btoeL = new bcr(this, 0, 0);
/* 141 */     this.btoeL.a(-0.5F, 0.0F, 0.0F, 1, 1, 2);
/* 142 */     this.btoeL.a(0.0F, 4.0F, 0.0F);
/* 143 */     this.btoeL.b(64, 32);
/* 144 */     this.btoeL.i = true;
/* 145 */     setRotation(this.btoeL, -0.349066F, 0.0F, 0.0F);
/* 146 */     this.legL.a(this.btoeL);
/* 147 */     this.rtoeL = new bcr(this, 0, 0);
/* 148 */     this.rtoeL.a(-1.0F, 0.0F, -2.0F, 1, 1, 2);
/* 149 */     this.rtoeL.a(-0.2F, 4.0F, 0.0F);
/* 150 */     this.rtoeL.b(64, 32);
/* 151 */     this.rtoeL.i = true;
/* 152 */     setRotation(this.rtoeL, 0.0F, 0.1396263F, 0.0F);
/* 153 */     this.legL.a(this.rtoeL);
/* 154 */     this.thighL = new bcr(this, 13, 18);
/* 155 */     this.thighL.a(-1.0F, 0.0F, -1.0F, 2, 2, 2);
/* 156 */     this.thighL.a(1.5F, 12.0F, -1.0F);
/* 157 */     this.thighL.b(64, 32);
/* 158 */     this.thighL.i = true;
/* 159 */     setRotation(this.thighL, 0.0F, 0.0F, 0.0F);
/* 160 */     this.thighL.a(this.legL);
/* 161 */     this.body.a(this.thighL);
/* 162 */     this.body.a(this.thighR);
/* 163 */     this.body.a(this.head);
/* 164 */     this.body.a(this.tail);
/* 165 */     this.body.a(this.rightwing1);
/* 166 */     this.body.a(this.leftwing1);
/*     */ 
/* 168 */     this.animationWingFlap = new ModelAnimator();
/*     */ 
/* 170 */     float frameUnit = 0.01666667F;
/* 171 */     List innerWingFrames = new ArrayList(12);
/* 172 */     innerWingFrames.add(new KeyFrame(0.0F, 2.0F, -43.5F, 0.0F, InterpType.LINEAR));
/* 173 */     innerWingFrames.add(new KeyFrame(5.0F * frameUnit, 4.0F, -38.0F, 0.0F, InterpType.LINEAR));
/* 174 */     innerWingFrames.add(new KeyFrame(10.0F * frameUnit, 5.5F, -27.5F, 0.0F, InterpType.LINEAR));
/* 175 */     innerWingFrames.add(new KeyFrame(15.0F * frameUnit, 5.5F, -7.0F, 0.0F, InterpType.LINEAR));
/* 176 */     innerWingFrames.add(new KeyFrame(20.0F * frameUnit, 5.5F, 15.0F, 0.0F, InterpType.LINEAR));
/* 177 */     innerWingFrames.add(new KeyFrame(25.0F * frameUnit, 4.5F, 30.0F, 0.0F, InterpType.LINEAR));
/* 178 */     innerWingFrames.add(new KeyFrame(30.0F * frameUnit, 2.0F, 38.0F, 9.0F, InterpType.LINEAR));
/* 179 */     innerWingFrames.add(new KeyFrame(35.0F * frameUnit, 1.0F, 20.0F, 0.0F, InterpType.LINEAR));
/* 180 */     innerWingFrames.add(new KeyFrame(40.0F * frameUnit, 1.0F, 3.5F, 0.0F, InterpType.LINEAR));
/* 181 */     innerWingFrames.add(new KeyFrame(45.0F * frameUnit, 1.0F, -19.0F, 0.0F, InterpType.LINEAR));
/* 182 */     innerWingFrames.add(new KeyFrame(50.0F * frameUnit, -3.0F, -38.0F, 0.0F, InterpType.LINEAR));
/* 183 */     innerWingFrames.add(new KeyFrame(55.0F * frameUnit, -1.0F, -48.0F, 0.0F, InterpType.LINEAR));
/* 184 */     KeyFrame.toRadians(innerWingFrames);
/* 185 */     this.animationWingFlap.addPart(this.rightwing1, innerWingFrames);
/* 186 */     List copy = KeyFrame.cloneFrames(innerWingFrames);
/* 187 */     KeyFrame.mirrorFramesX(copy);
/* 188 */     this.animationWingFlap.addPart(this.leftwing1, copy);
/*     */ 
/* 190 */     List outerWingFrames = new ArrayList(13);
/* 191 */     outerWingFrames.add(new KeyFrame(0.0F, 2.0F, 34.5F, 0.0F, InterpType.LINEAR));
/* 192 */     outerWingFrames.add(new KeyFrame(5.0F * frameUnit, 5.0F, 13.0F, -7.0F, InterpType.LINEAR));
/* 193 */     outerWingFrames.add(new KeyFrame(10.0F * frameUnit, 7.0F, 8.5F, -10.0F, InterpType.LINEAR));
/* 194 */     outerWingFrames.add(new KeyFrame(15.0F * frameUnit, 7.5F, -2.5F, -10.0F, InterpType.LINEAR));
/* 195 */     outerWingFrames.add(new KeyFrame(25.0F * frameUnit, 5.0F, 7.0F, -10.0F, InterpType.LINEAR));
/* 196 */     outerWingFrames.add(new KeyFrame(30.0F * frameUnit, 2.0F, 15.0F, 0.0F, InterpType.LINEAR));
/* 197 */     outerWingFrames.add(new KeyFrame(35.0F * frameUnit, -3.0F, 37.0F, 12.0F, InterpType.LINEAR));
/* 198 */     outerWingFrames.add(new KeyFrame(40.0F * frameUnit, -9.0F, 56.0F, 27.0F, InterpType.LINEAR));
/* 199 */     outerWingFrames.add(new KeyFrame(45.0F * frameUnit, -13.0F, 68.0F, 28.0F, InterpType.LINEAR));
/* 200 */     outerWingFrames.add(new KeyFrame(50.0F * frameUnit, -13.5F, 70.0F, 31.5F, InterpType.LINEAR));
/* 201 */     outerWingFrames.add(new KeyFrame(53.0F * frameUnit, -9.0F, 71.0F, 31.0F, InterpType.LINEAR));
/* 202 */     outerWingFrames.add(new KeyFrame(55.0F * frameUnit, -3.5F, 65.5F, 22.0F, InterpType.LINEAR));
/* 203 */     outerWingFrames.add(new KeyFrame(58.0F * frameUnit, 0.0F, 52.0F, 8.0F, InterpType.LINEAR));
/* 204 */     KeyFrame.toRadians(outerWingFrames);
/* 205 */     this.animationWingFlap.addPart(this.rightwing2, outerWingFrames);
/* 206 */     List copy2 = KeyFrame.cloneFrames(outerWingFrames);
/* 207 */     KeyFrame.mirrorFramesX(copy2);
/* 208 */     this.animationWingFlap.addPart(this.leftwing2, copy2);
/*     */   }
/*     */ 
/*     */   public void a(nm entity, float f, float f1, float f2, float f3, float f4, float f5)
/*     */   {
/* 214 */     super.a(entity, f, f1, f2, f3, f4, f5);
/* 215 */     a(f, f1, f2, f3, f4, f5, entity);
/* 216 */     this.body.a(f5);
/*     */   }
/*     */ 
/*     */   public void setFlyingAnimations(float flapProgress, float legSweepProgress, float roll)
/*     */   {
/* 222 */     this.animationWingFlap.updateAnimation(flapProgress);
/*     */ 
/* 225 */     this.body.g = (this.body.f = 0.0F);
/* 226 */     this.body.h = (-roll / 180.0F * 3.141593F);
/*     */ 
/* 229 */     this.thighR.f = (0.08726647F * legSweepProgress);
/* 230 */     this.thighL.f = (0.08726647F * legSweepProgress);
/* 231 */     this.legR.f = (0.08726647F * legSweepProgress);
/* 232 */     this.legL.f = (0.08726647F * legSweepProgress);
/* 233 */     this.ltoeR.f = (0.8726647F * legSweepProgress);
/* 234 */     this.rtoeR.f = (0.8726647F * legSweepProgress);
/* 235 */     this.btoeR.f = (-0.3490659F + 0.0F * legSweepProgress);
/* 236 */     this.ltoeL.f = (0.8726647F * legSweepProgress);
/* 237 */     this.rtoeL.f = (0.8726647F * legSweepProgress);
/* 238 */     this.btoeL.f = (-0.3490659F + 0.0F * legSweepProgress);
/*     */ 
/* 241 */     this.body.d = (7.0F + LongHashMapEntry.b(flapProgress * 3.141593F * 2.0F) * 1.4F);
/*     */     bcr tmp182_179 = this.thighR; tmp182_179.f = ((float)(tmp182_179.f + LongHashMapEntry.b(flapProgress * 3.141593F * 2.0F) * 0.08726646324990228D));
/*     */     bcr tmp210_207 = this.thighL; tmp210_207.f = ((float)(tmp210_207.f + LongHashMapEntry.b(flapProgress * 3.141593F * 2.0F) * 0.08726646324990228D));
/* 244 */     this.tail.f = ((float)(0.2617993956013792D + LongHashMapEntry.b(flapProgress * 3.141593F * 2.0F) * 0.03490658588512815D));
/* 245 */     this.head.f = ((float)(-0.3141592700403172D - LongHashMapEntry.b(flapProgress * 3.141593F * 2.0F) * 0.03490658588512815D));
/*     */   }
/*     */ 
/*     */   private void setRotation(bcr model, float x, float y, float z)
/*     */   {
/* 250 */     model.f = x;
/* 251 */     model.g = y;
/* 252 */     model.h = z;
/*     */   }
/*     */ 
/*     */   public void a(float limbPeriod, float limbMaxMovement, float ticksExisted, float headYaw, float entityPitch, float unitScale, nm entity)
/*     */   {
/* 257 */     super.a(limbPeriod, limbMaxMovement, ticksExisted, headYaw, entityPitch, unitScale, entity);
/* 258 */     this.body.f = (1.570796F - entityPitch / 180.0F * 3.141593F);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelBird
 * JD-Core Version:    0.6.2
 */