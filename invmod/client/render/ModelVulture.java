/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.client.render.animation.AnimationAction;
/*     */ import invmod.client.render.animation.AnimationState;
/*     */ import invmod.client.render.animation.BonesBirdLegs;
/*     */ import invmod.client.render.animation.BonesMouth;
/*     */ import invmod.client.render.animation.BonesWings;
/*     */ import invmod.client.render.animation.ModelAnimator;
/*     */ import invmod.common.util.MathUtil;
/*     */ import java.util.EnumMap;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class ModelVulture extends ModelMagmaCube
/*     */ {
/*     */   private ModelAnimator animationFlap;
/*     */   private ModelAnimator animationRun;
/*     */   private ModelAnimator animationBeak;
/*     */   bcr body;
/*     */   bcr rightThigh;
/*     */   bcr rightLeg;
/*     */   bcr rightAnkle;
/*     */   bcr rightToeB;
/*     */   bcr rightClawB;
/*     */   bcr rightToeL;
/*     */   bcr rightClawL;
/*     */   bcr rightToeM;
/*     */   bcr rightClawM;
/*     */   bcr rightToeR;
/*     */   bcr rightClawR;
/*     */   bcr leftThigh;
/*     */   bcr leftLeg;
/*     */   bcr leftAnkle;
/*     */   bcr leftToeB;
/*     */   bcr leftClawB;
/*     */   bcr leftToeL;
/*     */   bcr leftClawL;
/*     */   bcr leftToeM;
/*     */   bcr leftClawM;
/*     */   bcr leftToeR;
/*     */   bcr leftClawR;
/*     */   bcr neck1;
/*     */   bcr neck2;
/*     */   bcr neck3;
/*     */   bcr head;
/*     */   bcr upperBeak;
/*     */   bcr upperBeakTip;
/*     */   bcr lowerBeak;
/*     */   bcr lowerBeakTip;
/*     */   bcr leftWing1;
/*     */   bcr leftWing2;
/*     */   bcr leftWing3;
/*     */   bcr tail;
/*     */   bcr rightWing1;
/*     */   bcr rightWing2;
/*     */   bcr rightWing3;
/*     */ 
/*     */   public ModelVulture()
/*     */   {
/*  65 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   public ModelVulture(float par1)
/*     */   {
/*  70 */     this.body = new bcr(this, 0, 0);
/*  71 */     this.body.b(128, 128);
/*  72 */     this.body.a(-10.0F, -10.0F, -10.0F, 20, 30, 20);
/*  73 */     this.body.a(0.0F, -19.0F, 0.0F);
/*  74 */     this.rightThigh = new bcr(this, 84, 82);
/*  75 */     this.rightThigh.b(128, 128);
/*  76 */     this.rightThigh.a(-4.5F, -3.5F, -4.5F, 9, 15, 9);
/*  77 */     this.rightThigh.a(-5.0F, 20.0F, -2.0F);
/*  78 */     this.rightLeg = new bcr(this, 56, 50);
/*  79 */     this.rightLeg.b(128, 128);
/*  80 */     this.rightLeg.a(-2.0F, -3.0F, -2.0F, 4, 16, 4);
/*  81 */     this.rightLeg.a(0.0F, 11.0F, 0.0F);
/*  82 */     this.rightAnkle = new bcr(this, 16, 16);
/*  83 */     this.rightAnkle.b(128, 128);
/*  84 */     this.rightAnkle.a(0.0F, 0.0F, 0.0F, 0, 0, 0);
/*  85 */     this.rightAnkle.a(0.0F, 12.0F, 0.0F);
/*  86 */     this.rightToeB = new bcr(this, 60, 0);
/*  87 */     this.rightToeB.b(128, 128);
/*  88 */     this.rightToeB.a(-1.0F, -1.0F, -1.0F, 2, 8, 2);
/*  89 */     this.rightToeB.a(0.0F, 0.0F, 2.0F);
/*  90 */     this.rightClawB = new bcr(this, 0, 11);
/*  91 */     this.rightClawB.b(128, 128);
/*  92 */     this.rightClawB.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/*  93 */     this.rightClawB.a(0.0F, 6.0F, 0.0F);
/*  94 */     this.rightToeL = new bcr(this, 0, 0);
/*  95 */     this.rightToeL.b(128, 128);
/*  96 */     this.rightToeL.a(-1.0F, 0.5F, -1.0F, 2, 9, 2);
/*  97 */     this.rightToeL.a(-0.5F, 0.0F, 1.0F);
/*  98 */     this.rightClawL = new bcr(this, 0, 11);
/*  99 */     this.rightClawL.b(128, 128);
/* 100 */     this.rightClawL.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 101 */     this.rightClawL.a(0.0F, 9.0F, 0.0F);
/* 102 */     this.rightToeM = new bcr(this, 8, 0);
/* 103 */     this.rightToeM.b(128, 128);
/* 104 */     this.rightToeM.a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
/* 105 */     this.rightToeM.a(0.0F, 0.0F, 0.0F);
/* 106 */     this.rightClawM = new bcr(this, 0, 11);
/* 107 */     this.rightClawM.b(128, 128);
/* 108 */     this.rightClawM.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 109 */     this.rightClawM.a(0.0F, 9.0F, 0.0F);
/* 110 */     this.rightToeR = new bcr(this, 0, 0);
/* 111 */     this.rightToeR.b(128, 128);
/* 112 */     this.rightToeR.a(-1.0F, -0.5F, -1.0F, 2, 9, 2);
/* 113 */     this.rightToeR.a(1.0F, 0.0F, 1.0F);
/* 114 */     this.rightClawR = new bcr(this, 0, 11);
/* 115 */     this.rightClawR.b(128, 128);
/* 116 */     this.rightClawR.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 117 */     this.rightClawR.a(0.0F, 8.0F, 0.0F);
/* 118 */     this.leftThigh = new bcr(this, 84, 82);
/* 119 */     this.leftThigh.b(128, 128);
/* 120 */     this.leftThigh.a(-4.5F, -3.5F, -4.5F, 9, 15, 9);
/* 121 */     this.leftThigh.a(5.0F, 20.0F, -2.0F);
/* 122 */     this.leftThigh.i = true;
/* 123 */     this.leftLeg = new bcr(this, 56, 50);
/* 124 */     this.leftLeg.b(128, 128);
/* 125 */     this.leftLeg.a(-2.0F, -3.0F, -2.0F, 4, 16, 4);
/* 126 */     this.leftLeg.a(0.0F, 11.0F, 0.0F);
/* 127 */     this.leftLeg.i = true;
/* 128 */     this.leftAnkle = new bcr(this, 16, 16);
/* 129 */     this.leftAnkle.b(128, 128);
/* 130 */     this.leftAnkle.a(0.0F, 0.0F, 0.0F, 0, 0, 0);
/* 131 */     this.leftAnkle.a(0.0F, 12.0F, 0.0F);
/* 132 */     this.leftToeB = new bcr(this, 60, 0);
/* 133 */     this.leftToeB.b(128, 128);
/* 134 */     this.leftToeB.a(-1.0F, -1.0F, -1.0F, 2, 8, 2);
/* 135 */     this.leftToeB.a(0.0F, 0.0F, 2.0F);
/* 136 */     this.leftClawB = new bcr(this, 0, 11);
/* 137 */     this.leftClawB.b(128, 128);
/* 138 */     this.leftClawB.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 139 */     this.leftClawB.a(0.0F, 6.0F, 0.0F);
/* 140 */     this.leftToeL = new bcr(this, 0, 0);
/* 141 */     this.leftToeL.b(128, 128);
/* 142 */     this.leftToeL.a(-1.0F, 0.5F, -1.0F, 2, 9, 2);
/* 143 */     this.leftToeL.a(0.5F, 0.0F, 1.0F);
/* 144 */     this.leftClawL = new bcr(this, 0, 11);
/* 145 */     this.leftClawL.b(128, 128);
/* 146 */     this.leftClawL.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 147 */     this.leftClawL.a(0.0F, 9.0F, 0.0F);
/* 148 */     this.leftToeM = new bcr(this, 8, 0);
/* 149 */     this.leftToeM.b(128, 128);
/* 150 */     this.leftToeM.a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
/* 151 */     this.leftToeM.a(0.0F, 0.0F, 0.0F);
/* 152 */     this.leftClawM = new bcr(this, 0, 11);
/* 153 */     this.leftClawM.b(128, 128);
/* 154 */     this.leftClawM.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 155 */     this.leftClawM.a(0.0F, 9.0F, 0.0F);
/* 156 */     this.leftToeR = new bcr(this, 0, 0);
/* 157 */     this.leftToeR.b(128, 128);
/* 158 */     this.leftToeR.a(-1.0F, -0.5F, -1.0F, 2, 9, 2);
/* 159 */     this.leftToeR.a(-1.0F, 0.0F, 1.0F);
/* 160 */     this.leftClawR = new bcr(this, 0, 11);
/* 161 */     this.leftClawR.b(128, 128);
/* 162 */     this.leftClawR.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 163 */     this.leftClawR.a(0.0F, 8.0F, 0.0F);
/* 164 */     this.neck1 = new bcr(this, 43, 95);
/* 165 */     this.neck1.b(128, 128);
/* 166 */     this.neck1.a(-7.0F, -7.0F, -6.5F, 14, 10, 13);
/* 167 */     this.neck1.a(0.0F, -10.0F, 1.0F);
/* 168 */     this.neck2 = new bcr(this, 50, 73);
/* 169 */     this.neck2.b(128, 128);
/* 170 */     this.neck2.a(-5.0F, -4.0F, -5.0F, 10, 8, 10);
/* 171 */     this.neck2.a(0.0F, -8.0F, 0.0F);
/* 172 */     this.neck3 = new bcr(this, 80, 65);
/* 173 */     this.neck3.b(128, 128);
/* 174 */     this.neck3.a(-4.0F, -5.5F, -5.0F, 8, 5, 10);
/* 175 */     this.neck3.a(0.0F, -2.0F, 0.0F);
/* 176 */     this.head = new bcr(this, 14, 108);
/* 177 */     this.head.b(128, 128);
/* 178 */     this.head.a(-4.5F, -5.0F, -9.5F, 9, 8, 11);
/* 179 */     this.head.a(0.0F, -4.0F, 0.0F);
/* 180 */     this.upperBeak = new bcr(this, 54, 118);
/* 181 */     this.upperBeak.b(128, 128);
/* 182 */     this.upperBeak.a(-2.5F, -1.0F, -5.0F, 5, 2, 8);
/* 183 */     this.upperBeak.a(0.0F, -0.8F, -10.0F);
/* 184 */     this.upperBeakTip = new bcr(this, 72, 118);
/* 185 */     this.upperBeakTip.b(128, 128);
/* 186 */     this.upperBeakTip.a(-1.0F, -1.0F, -1.0F, 2, 2, 2);
/* 187 */     this.upperBeakTip.a(0.0F, 0.0F, -6.0F);
/* 188 */     this.lowerBeak = new bcr(this, 80, 118);
/* 189 */     this.lowerBeak.b(128, 128);
/* 190 */     this.lowerBeak.a(-2.5F, -1.0F, -5.0F, 5, 2, 8);
/* 191 */     this.lowerBeak.a(0.0F, 1.5F, -10.0F);
/* 192 */     this.lowerBeakTip = new bcr(this, 78, 121);
/* 193 */     this.lowerBeakTip.b(128, 128);
/* 194 */     this.lowerBeakTip.a(-1.0F, -0.5F, -1.0F, 2, 1, 2);
/* 195 */     this.lowerBeakTip.a(0.0F, -0.5F, -6.0F);
/* 196 */     this.leftWing1 = new bcr(this, 0, 50);
/* 197 */     this.leftWing1.i = true;
/* 198 */     this.leftWing1.b(128, 128);
/* 199 */     this.leftWing1.a(-0.5F, -4.5F, -1.5F, 25, 29, 3);
/* 200 */     this.leftWing1.a(7.0F, -8.0F, 6.0F);
/* 201 */     this.leftWing2 = new bcr(this, 0, 82);
/* 202 */     this.leftWing2.i = true;
/* 203 */     this.leftWing2.b(128, 128);
/* 204 */     this.leftWing2.a(-2.5F, -5.0F, -1.0F, 23, 24, 2);
/* 205 */     this.leftWing2.a(23.0F, 1.0F, 0.0F);
/* 206 */     this.leftWing3 = new bcr(this, 80, 0);
/* 207 */     this.leftWing3.i = true;
/* 208 */     this.leftWing3.b(128, 128);
/* 209 */     this.leftWing3.a(-2.5F, -5.0F, -0.5F, 23, 22, 1);
/* 210 */     this.leftWing3.a(21.0F, 0.2F, 0.3F);
/* 211 */     this.tail = new bcr(this, 80, 23);
/* 212 */     this.tail.b(128, 128);
/* 213 */     this.tail.a(-8.5F, -5.0F, -1.0F, 17, 40, 2);
/* 214 */     this.tail.a(0.0F, 19.0F, 8.0F);
/* 215 */     this.rightWing1 = new bcr(this, 0, 50);
/* 216 */     this.rightWing1.b(128, 128);
/* 217 */     this.rightWing1.a(-24.5F, -4.5F, -1.5F, 25, 29, 3);
/* 218 */     this.rightWing1.a(-7.0F, -8.0F, 6.0F);
/* 219 */     this.rightWing2 = new bcr(this, 0, 82);
/* 220 */     this.rightWing2.b(128, 128);
/* 221 */     this.rightWing2.a(-20.5F, -5.0F, -1.0F, 23, 24, 2);
/* 222 */     this.rightWing2.a(-23.0F, 1.0F, 0.0F);
/* 223 */     this.rightWing3 = new bcr(this, 80, 0);
/* 224 */     this.rightWing3.b(128, 128);
/* 225 */     this.rightWing3.a(-20.5F, -5.0F, -0.5F, 23, 22, 1);
/* 226 */     this.rightWing3.a(-21.0F, 0.2F, 0.3F);
/*     */ 
/* 228 */     this.rightToeB.a(this.rightClawB);
/* 229 */     this.rightToeR.a(this.rightClawR);
/* 230 */     this.rightToeM.a(this.rightClawM);
/* 231 */     this.rightToeL.a(this.rightClawL);
/* 232 */     this.leftToeB.a(this.leftClawB);
/* 233 */     this.leftToeR.a(this.leftClawR);
/* 234 */     this.leftToeM.a(this.leftClawM);
/* 235 */     this.leftToeL.a(this.leftClawL);
/* 236 */     this.rightAnkle.a(this.rightToeB);
/* 237 */     this.rightAnkle.a(this.rightToeR);
/* 238 */     this.rightAnkle.a(this.rightToeM);
/* 239 */     this.rightAnkle.a(this.rightToeL);
/* 240 */     this.leftAnkle.a(this.leftToeB);
/* 241 */     this.leftAnkle.a(this.leftToeR);
/* 242 */     this.leftAnkle.a(this.leftToeM);
/* 243 */     this.leftAnkle.a(this.leftToeL);
/* 244 */     this.rightLeg.a(this.rightAnkle);
/* 245 */     this.leftLeg.a(this.leftAnkle);
/* 246 */     this.rightThigh.a(this.rightLeg);
/* 247 */     this.leftThigh.a(this.leftLeg);
/* 248 */     this.upperBeak.a(this.upperBeakTip);
/* 249 */     this.lowerBeak.a(this.lowerBeakTip);
/* 250 */     this.head.a(this.upperBeak);
/* 251 */     this.head.a(this.lowerBeak);
/* 252 */     this.neck3.a(this.head);
/* 253 */     this.neck2.a(this.neck3);
/* 254 */     this.neck1.a(this.neck2);
/* 255 */     this.leftWing2.a(this.leftWing3);
/* 256 */     this.leftWing1.a(this.leftWing2);
/* 257 */     this.rightWing2.a(this.rightWing3);
/* 258 */     this.rightWing1.a(this.rightWing2);
/* 259 */     this.body.a(this.rightThigh);
/* 260 */     this.body.a(this.leftThigh);
/* 261 */     this.body.a(this.neck1);
/* 262 */     this.body.a(this.tail);
/* 263 */     this.body.a(this.leftWing1);
/* 264 */     this.body.a(this.rightWing1);
/*     */ 
/* 266 */     EnumMap legMap = new EnumMap(BonesBirdLegs.class);
/* 267 */     legMap.put(BonesBirdLegs.LEFT_KNEE, this.leftThigh);
/* 268 */     legMap.put(BonesBirdLegs.RIGHT_KNEE, this.rightThigh);
/* 269 */     legMap.put(BonesBirdLegs.LEFT_ANKLE, this.leftLeg);
/* 270 */     legMap.put(BonesBirdLegs.RIGHT_ANKLE, this.rightLeg);
/* 271 */     legMap.put(BonesBirdLegs.LEFT_METATARSOPHALANGEAL_ARTICULATIONS, this.leftAnkle);
/* 272 */     legMap.put(BonesBirdLegs.RIGHT_METATARSOPHALANGEAL_ARTICULATIONS, this.rightAnkle);
/* 273 */     legMap.put(BonesBirdLegs.LEFT_BACK_CLAW, this.leftToeB);
/* 274 */     legMap.put(BonesBirdLegs.RIGHT_BACK_CLAW, this.rightToeB);
/* 275 */     this.animationRun = new ModelAnimator(legMap, AnimationRegistry.instance().getAnimation("bird_run"));
/*     */ 
/* 277 */     EnumMap wingMap = new EnumMap(BonesWings.class);
/* 278 */     wingMap.put(BonesWings.LEFT_SHOULDER, this.leftWing1);
/* 279 */     wingMap.put(BonesWings.RIGHT_SHOULDER, this.rightWing1);
/* 280 */     wingMap.put(BonesWings.LEFT_ELBOW, this.leftWing2);
/* 281 */     wingMap.put(BonesWings.RIGHT_ELBOW, this.rightWing2);
/* 282 */     this.animationFlap = new ModelAnimator(wingMap, AnimationRegistry.instance().getAnimation("wing_flap_2_piece"));
/*     */ 
/* 284 */     EnumMap beakMap = new EnumMap(BonesMouth.class);
/* 285 */     beakMap.put(BonesMouth.UPPER_MOUTH, this.upperBeak);
/* 286 */     beakMap.put(BonesMouth.LOWER_MOUTH, this.lowerBeak);
/* 287 */     this.animationBeak = new ModelAnimator(beakMap, AnimationRegistry.instance().getAnimation("bird_beak"));
/*     */ 
/* 289 */     setRotation(this.body, 0.7F, 0.0F, 0.0F);
/* 290 */     setRotation(this.rightThigh, -0.39F, 0.0F, 0.09F);
/* 291 */     setRotation(this.leftThigh, -0.39F, 0.0F, -0.09F);
/* 292 */     setRotation(this.rightLeg, -0.72F, 0.0F, 0.0F);
/* 293 */     setRotation(this.leftLeg, -0.72F, 0.0F, 0.0F);
/* 294 */     setRotation(this.rightAnkle, 0.1F, 0.2F, 0.0F);
/* 295 */     setRotation(this.leftAnkle, 0.1F, -0.2F, 0.0F);
/* 296 */     setRotation(this.rightToeB, 1.34F, 0.0F, 0.0F);
/* 297 */     setRotation(this.rightToeR, -0.8F, -0.28F, -0.28F);
/* 298 */     setRotation(this.rightToeM, -0.8F, 0.0F, 0.0F);
/* 299 */     setRotation(this.rightToeL, -0.8F, 0.28F, 0.28F);
/* 300 */     setRotation(this.leftToeB, 1.34F, 0.0F, 0.0F);
/* 301 */     setRotation(this.leftToeR, -0.8F, 0.28F, 0.28F);
/* 302 */     setRotation(this.leftToeM, -0.8F, 0.0F, 0.0F);
/* 303 */     setRotation(this.leftToeL, -0.8F, -0.28F, -0.28F);
/* 304 */     setRotation(this.rightClawB, -36.0F, 0.0F, 0.0F);
/*     */ 
/* 306 */     setRotation(this.neck1, -0.18F, 0.0F, 0.0F);
/* 307 */     setRotation(this.neck2, 0.52F, 0.0F, 0.0F);
/* 308 */     setRotation(this.neck3, 0.26F, 0.0F, 0.0F);
/* 309 */     setRotation(this.head, -0.97F, 0.0F, 0.0F);
/*     */ 
/* 311 */     setRotation(this.tail, 0.3F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public void a(nm par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*     */   {
/* 330 */     this.body.a(par7);
/*     */   }
/*     */ 
/*     */   public void setFlyingAnimations(AnimationState wingState, AnimationState legState, AnimationState beakState, float roll, float headYaw, float headPitch, float parTick)
/*     */   {
/* 335 */     AnimationAction legAction = legState.getCurrentAction();
/* 336 */     AnimationAction wingAction = wingState.getCurrentAction();
/* 337 */     float flapProgress = wingState.getCurrentAnimationTimeInterp(parTick);
/* 338 */     float legProgress = legState.getCurrentAnimationTimeInterp(parTick);
/* 339 */     float beakProgress = beakState.getCurrentAnimationTimeInterp(parTick);
/* 340 */     this.animationFlap.updateAnimation(flapProgress);
/* 341 */     this.animationRun.updateAnimation(legProgress);
/* 342 */     this.animationBeak.updateAnimation(beakProgress);
/* 343 */     if (legAction == AnimationAction.RUN)
/*     */     {
/* 346 */       if ((legProgress >= 0.109195F) && (legProgress < 0.5373563F))
/*     */       {
/* 348 */         legProgress += 0.03735632F;
/* 349 */         if (legProgress >= 0.5373563F) {
/* 350 */           legProgress -= 0.4281609F;
/*     */         }
/* 352 */         float t = 25.132742F * legProgress / 0.8908046F;
/* 353 */         this.body.f += (float)(-Math.cos(t) * 0.04D);
/* 354 */         this.neck1.f += (float)(Math.cos(t) * 0.08D);
/* 355 */         this.body.d += -(float)(Math.cos(t) * 1.9D);
/*     */       }
/*     */     }
/*     */ 
/* 359 */     if (wingAction == AnimationAction.WINGFLAP)
/*     */     {
/* 361 */       float flapCycle = flapProgress / 0.2714932F;
/*     */ 
/* 363 */       this.body.d += LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 1.4F;
/*     */       bcr tmp244_241 = this.rightThigh; tmp244_241.f = ((float)(tmp244_241.f + LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 0.08726646324990228D));
/*     */       bcr tmp274_271 = this.leftThigh; tmp274_271.f = ((float)(tmp274_271.f + LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 0.08726646324990228D));
/*     */       bcr tmp304_301 = this.tail; tmp304_301.f = ((float)(tmp304_301.f + LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 0.03490658588512815D));
/*     */     }
/*     */ 
/* 369 */     this.body.h = (-roll / 180.0F * 3.141593F);
/*     */ 
/* 372 */     headPitch = (float)MathUtil.boundAngle180Deg(headPitch);
/* 373 */     if (headPitch > 37.16F)
/* 374 */       headPitch = 37.16F;
/* 375 */     else if (headPitch < -56.650002F) {
/* 376 */       headPitch = -56.650002F;
/*     */     }
/* 378 */     float pitchFactor = (headPitch + 56.650002F) / 93.800003F;
/* 379 */     this.head.f += -0.96F + pitchFactor * -0.1400001F;
/* 380 */     this.neck3.f += 0.378F + pitchFactor * -0.528F;
/* 381 */     this.neck2.f += 0.4F + pitchFactor * -0.4F;
/* 382 */     this.neck1.f += 0.513F + pitchFactor * -0.613F;
/*     */ 
/* 384 */     headYaw = (float)MathUtil.boundAngle180Deg(headYaw);
/* 385 */     if (headYaw > 30.5F)
/* 386 */       headYaw = 30.5F;
/* 387 */     else if (headYaw < -30.5F) {
/* 388 */       headYaw = -30.5F;
/*     */     }
/* 390 */     float yawFactor = (headYaw + 30.5F) / 61.0F;
/* 391 */     this.head.h += 0.8F + yawFactor * 2.0F * -0.8F;
/* 392 */     this.neck3.h += 0.38F + yawFactor * 2.0F * -0.38F;
/* 393 */     this.neck2.h += 0.14F + yawFactor * 2.0F * -0.14F;
/* 394 */     this.head.g += -0.7F + yawFactor * 2.0F * 0.7F;
/* 395 */     this.neck3.g += -0.12F + yawFactor * 2.0F * 0.12F;
/*     */   }
/*     */ 
/*     */   public void a(float limbPeriod, float limbMaxMovement, float ticksExisted, float headYaw, float entityPitch, float unitScale, nm entity)
/*     */   {
/* 400 */     super.a(limbPeriod, limbMaxMovement, ticksExisted, headYaw, entityPitch, unitScale, entity);
/*     */     bcr tmp19_16 = this.body; tmp19_16.f = ((float)(tmp19_16.f + (0.8707963705062867D - entityPitch / 180.0F * 3.141593F)));
/* 402 */     float pitchFactor = entityPitch / 50.0F;
/* 403 */     if (pitchFactor > 1.0F)
/* 404 */       pitchFactor = 1.0F;
/* 405 */     else if (pitchFactor < 0.0F)
/* 406 */       pitchFactor = 0.0F;
/*     */   }
/*     */ 
/*     */   public void resetSkeleton()
/*     */   {
/* 413 */     setRotation(this.body, 0.7F, 0.0F, 0.0F);
/* 414 */     setRotation(this.rightThigh, -0.39F, 0.0F, 0.09F);
/* 415 */     setRotation(this.leftThigh, -0.39F, 0.0F, -0.09F);
/* 416 */     setRotation(this.rightLeg, -0.72F, 0.0F, 0.0F);
/* 417 */     setRotation(this.leftLeg, -0.72F, 0.0F, 0.0F);
/* 418 */     setRotation(this.rightAnkle, 0.1F, 0.2F, 0.0F);
/* 419 */     setRotation(this.leftAnkle, 0.1F, -0.2F, 0.0F);
/* 420 */     setRotation(this.rightToeB, 1.34F, 0.0F, 0.0F);
/* 421 */     setRotation(this.rightToeR, -0.8F, -0.28F, -0.28F);
/* 422 */     setRotation(this.rightToeM, -0.8F, 0.0F, 0.0F);
/* 423 */     setRotation(this.rightToeL, -0.8F, 0.28F, 0.28F);
/* 424 */     setRotation(this.leftToeB, 1.34F, 0.0F, 0.0F);
/* 425 */     setRotation(this.leftToeR, -0.8F, 0.28F, 0.28F);
/* 426 */     setRotation(this.leftToeM, -0.8F, 0.0F, 0.0F);
/* 427 */     setRotation(this.leftToeL, -0.8F, -0.28F, -0.28F);
/*     */ 
/* 429 */     setRotation(this.neck1, 0.0F, 0.0F, 0.0F);
/* 430 */     setRotation(this.neck2, 0.0F, 0.0F, 0.0F);
/* 431 */     setRotation(this.neck3, 0.0F, 0.0F, 0.0F);
/* 432 */     setRotation(this.head, 0.0F, 0.0F, 0.0F);
/* 433 */     setRotation(this.tail, 0.3F, 0.0F, 0.0F);
/* 434 */     setRotation(this.upperBeak, 0.0F, 0.0F, 0.0F);
/* 435 */     setRotation(this.lowerBeak, 0.0F, 0.0F, 0.0F);
/*     */ 
/* 437 */     this.body.a(0.0F, -19.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   private void setRotation(bcr model, float x, float y, float z)
/*     */   {
/* 442 */     model.f = x;
/* 443 */     model.g = y;
/* 444 */     model.h = z;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelVulture
 * JD-Core Version:    0.6.2
 */