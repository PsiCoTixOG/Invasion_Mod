/*     */ package invmod.client.render;
/*     */ 
/*     */ import invmod.client.render.animation.AnimationAction;
/*     */ import invmod.client.render.animation.AnimationState;
/*     */ import invmod.client.render.animation.BonesBirdLegs;
/*     */ import invmod.client.render.animation.BonesWings;
/*     */ import invmod.client.render.animation.ModelAnimator;
/*     */ import invmod.common.util.MathUtil;
/*     */ import java.util.EnumMap;
/*     */ import net.minecraft.client.model.ModelMagmaCube;
/*     */ import net.minecraft.src.bcr;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class ModelGiantBird extends ModelMagmaCube
/*     */ {
/*     */   private ModelAnimator animationFlap;
/*     */   private ModelAnimator animationRun;
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
/*     */   bcr headFeathers;
/*     */   bcr backNeckFeathers;
/*     */   bcr leftNeckFeathers;
/*     */   bcr rightNeckFeathers;
/*     */   bcr leftWing1;
/*     */   bcr leftWing2;
/*     */   bcr leftWing3;
/*     */   bcr tail;
/*     */   bcr rightWing1;
/*     */   bcr rightWing2;
/*     */   bcr rightWing3;
/*     */ 
/*     */   public ModelGiantBird()
/*     */   {
/*  67 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   public ModelGiantBird(float par1)
/*     */   {
/*  72 */     this.body = new bcr(this, 0, 0);
/*  73 */     this.body.b(128, 128);
/*  74 */     this.body.a(-10.0F, -10.0F, -10.0F, 20, 30, 20);
/*  75 */     this.body.a(0.0F, -19.0F, 0.0F);
/*  76 */     this.rightThigh = new bcr(this, 84, 82);
/*  77 */     this.rightThigh.b(128, 128);
/*  78 */     this.rightThigh.a(-4.5F, -3.5F, -4.5F, 9, 15, 9);
/*  79 */     this.rightThigh.a(-5.0F, 20.0F, -2.0F);
/*  80 */     this.rightLeg = new bcr(this, 56, 50);
/*  81 */     this.rightLeg.b(128, 128);
/*  82 */     this.rightLeg.a(-2.0F, -3.0F, -2.0F, 4, 16, 4);
/*  83 */     this.rightLeg.a(0.0F, 11.0F, 0.0F);
/*  84 */     this.rightAnkle = new bcr(this, 16, 16);
/*  85 */     this.rightAnkle.b(128, 128);
/*  86 */     this.rightAnkle.a(0.0F, 0.0F, 0.0F, 0, 0, 0);
/*  87 */     this.rightAnkle.a(0.0F, 12.0F, 0.0F);
/*  88 */     this.rightToeB = new bcr(this, 60, 0);
/*  89 */     this.rightToeB.b(128, 128);
/*  90 */     this.rightToeB.a(-1.0F, -1.0F, -1.0F, 2, 8, 2);
/*  91 */     this.rightToeB.a(0.0F, 0.0F, 2.0F);
/*  92 */     this.rightClawB = new bcr(this, 0, 11);
/*  93 */     this.rightClawB.b(128, 128);
/*  94 */     this.rightClawB.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/*  95 */     this.rightClawB.a(0.0F, 6.0F, 0.0F);
/*  96 */     this.rightToeL = new bcr(this, 0, 0);
/*  97 */     this.rightToeL.b(128, 128);
/*  98 */     this.rightToeL.a(-1.0F, 0.5F, -1.0F, 2, 9, 2);
/*  99 */     this.rightToeL.a(-0.5F, 0.0F, 1.0F);
/* 100 */     this.rightClawL = new bcr(this, 0, 11);
/* 101 */     this.rightClawL.b(128, 128);
/* 102 */     this.rightClawL.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 103 */     this.rightClawL.a(0.0F, 9.0F, 0.0F);
/* 104 */     this.rightToeM = new bcr(this, 8, 0);
/* 105 */     this.rightToeM.b(128, 128);
/* 106 */     this.rightToeM.a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
/* 107 */     this.rightToeM.a(0.0F, 0.0F, 0.0F);
/* 108 */     this.rightClawM = new bcr(this, 0, 11);
/* 109 */     this.rightClawM.b(128, 128);
/* 110 */     this.rightClawM.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 111 */     this.rightClawM.a(0.0F, 9.0F, 0.0F);
/* 112 */     this.rightToeR = new bcr(this, 0, 0);
/* 113 */     this.rightToeR.b(128, 128);
/* 114 */     this.rightToeR.a(-1.0F, -0.5F, -1.0F, 2, 9, 2);
/* 115 */     this.rightToeR.a(1.0F, 0.0F, 1.0F);
/* 116 */     this.rightClawR = new bcr(this, 0, 11);
/* 117 */     this.rightClawR.b(128, 128);
/* 118 */     this.rightClawR.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 119 */     this.rightClawR.a(0.0F, 8.0F, 0.0F);
/* 120 */     this.leftThigh = new bcr(this, 84, 82);
/* 121 */     this.leftThigh.b(128, 128);
/* 122 */     this.leftThigh.a(-4.5F, -3.5F, -4.5F, 9, 15, 9);
/* 123 */     this.leftThigh.a(5.0F, 20.0F, -2.0F);
/* 124 */     this.leftThigh.i = true;
/* 125 */     this.leftLeg = new bcr(this, 56, 50);
/* 126 */     this.leftLeg.b(128, 128);
/* 127 */     this.leftLeg.a(-2.0F, -3.0F, -2.0F, 4, 16, 4);
/* 128 */     this.leftLeg.a(0.0F, 11.0F, 0.0F);
/* 129 */     this.leftLeg.i = true;
/* 130 */     this.leftAnkle = new bcr(this, 16, 16);
/* 131 */     this.leftAnkle.b(128, 128);
/* 132 */     this.leftAnkle.a(0.0F, 0.0F, 0.0F, 0, 0, 0);
/* 133 */     this.leftAnkle.a(0.0F, 12.0F, 0.0F);
/* 134 */     this.leftToeB = new bcr(this, 60, 0);
/* 135 */     this.leftToeB.b(128, 128);
/* 136 */     this.leftToeB.a(-1.0F, -1.0F, -1.0F, 2, 8, 2);
/* 137 */     this.leftToeB.a(0.0F, 0.0F, 2.0F);
/* 138 */     this.leftClawB = new bcr(this, 0, 11);
/* 139 */     this.leftClawB.b(128, 128);
/* 140 */     this.leftClawB.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 141 */     this.leftClawB.a(0.0F, 6.0F, 0.0F);
/* 142 */     this.leftToeL = new bcr(this, 0, 0);
/* 143 */     this.leftToeL.b(128, 128);
/* 144 */     this.leftToeL.a(-1.0F, 0.5F, -1.0F, 2, 9, 2);
/* 145 */     this.leftToeL.a(0.5F, 0.0F, 1.0F);
/* 146 */     this.leftClawL = new bcr(this, 0, 11);
/* 147 */     this.leftClawL.b(128, 128);
/* 148 */     this.leftClawL.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 149 */     this.leftClawL.a(0.0F, 9.0F, 0.0F);
/* 150 */     this.leftToeM = new bcr(this, 8, 0);
/* 151 */     this.leftToeM.b(128, 128);
/* 152 */     this.leftToeM.a(-1.0F, 0.0F, -1.0F, 2, 10, 2);
/* 153 */     this.leftToeM.a(0.0F, 0.0F, 0.0F);
/* 154 */     this.leftClawM = new bcr(this, 0, 11);
/* 155 */     this.leftClawM.b(128, 128);
/* 156 */     this.leftClawM.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 157 */     this.leftClawM.a(0.0F, 9.0F, 0.0F);
/* 158 */     this.leftToeR = new bcr(this, 0, 0);
/* 159 */     this.leftToeR.b(128, 128);
/* 160 */     this.leftToeR.a(-1.0F, -0.5F, -1.0F, 2, 9, 2);
/* 161 */     this.leftToeR.a(-1.0F, 0.0F, 1.0F);
/* 162 */     this.leftClawR = new bcr(this, 0, 11);
/* 163 */     this.leftClawR.b(128, 128);
/* 164 */     this.leftClawR.a(-0.5F, 0.0F, -1.0F, 1, 4, 2);
/* 165 */     this.leftClawR.a(0.0F, 8.0F, 0.0F);
/* 166 */     this.neck1 = new bcr(this, 43, 95);
/* 167 */     this.neck1.b(128, 128);
/* 168 */     this.neck1.a(-7.0F, -7.0F, -6.5F, 14, 10, 13);
/* 169 */     this.neck1.a(0.0F, -10.0F, 1.0F);
/* 170 */     this.neck2 = new bcr(this, 50, 73);
/* 171 */     this.neck2.b(128, 128);
/* 172 */     this.neck2.a(-5.0F, -4.0F, -5.0F, 10, 8, 10);
/* 173 */     this.neck2.a(0.0F, -8.0F, 0.0F);
/* 174 */     this.neck3 = new bcr(this, 80, 65);
/* 175 */     this.neck3.b(128, 128);
/* 176 */     this.neck3.a(-4.0F, -5.5F, -5.0F, 8, 5, 10);
/* 177 */     this.neck3.a(0.0F, -2.0F, 0.0F);
/* 178 */     this.head = new bcr(this, 14, 108);
/* 179 */     this.head.b(128, 128);
/* 180 */     this.head.a(-4.5F, -5.0F, -9.5F, 9, 8, 11);
/* 181 */     this.head.a(0.0F, -4.0F, 0.0F);
/* 182 */     this.upperBeak = new bcr(this, 54, 118);
/* 183 */     this.upperBeak.b(128, 128);
/* 184 */     this.upperBeak.a(-2.5F, -1.0F, -3.0F, 5, 2, 6);
/* 185 */     this.upperBeak.a(0.0F, -0.8F, -10.0F);
/* 186 */     this.upperBeakTip = new bcr(this, 70, 118);
/* 187 */     this.upperBeakTip.b(128, 128);
/* 188 */     this.upperBeakTip.a(-1.0F, -1.0F, -1.0F, 2, 2, 2);
/* 189 */     this.upperBeakTip.a(0.0F, 0.0F, -4.0F);
/* 190 */     this.lowerBeak = new bcr(this, 78, 118);
/* 191 */     this.lowerBeak.b(128, 128);
/* 192 */     this.lowerBeak.a(-2.5F, -1.0F, -3.0F, 5, 2, 6);
/* 193 */     this.lowerBeak.a(0.0F, 1.5F, -10.0F);
/* 194 */     this.lowerBeakTip = new bcr(this, 76, 121);
/* 195 */     this.lowerBeakTip.b(128, 128);
/* 196 */     this.lowerBeakTip.a(-1.0F, -0.5F, -1.0F, 2, 1, 2);
/* 197 */     this.lowerBeakTip.a(0.0F, -0.5F, -4.0F);
/* 198 */     this.headFeathers = new bcr(this, -5, 121);
/* 199 */     this.headFeathers.b(128, 128);
/* 200 */     this.headFeathers.a(-3.5F, 0.0F, -0.5F, 7, 0, 5);
/* 201 */     this.headFeathers.a(0.0F, -5.0F, 0.0F);
/* 202 */     this.backNeckFeathers = new bcr(this, -7, 108);
/* 203 */     this.backNeckFeathers.b(128, 128);
/* 204 */     this.backNeckFeathers.a(-4.0F, 0.0F, -1.5F, 8, 0, 7);
/* 205 */     this.backNeckFeathers.a(0.0F, -3.0F, 5.0F);
/* 206 */     this.leftNeckFeathers = new bcr(this, -6, 115);
/* 207 */     this.leftNeckFeathers.b(128, 128);
/* 208 */     this.leftNeckFeathers.a(-3.0F, 0.0F, -1.0F, 6, 0, 6);
/* 209 */     this.leftNeckFeathers.a(4.0F, -3.0F, 2.0F);
/* 210 */     this.rightNeckFeathers = new bcr(this, -6, 115);
/* 211 */     this.rightNeckFeathers.b(128, 128);
/* 212 */     this.rightNeckFeathers.a(-3.0F, 0.0F, -1.0F, 6, 0, 6);
/* 213 */     this.rightNeckFeathers.a(-4.0F, -3.0F, 2.0F);
/* 214 */     this.leftWing1 = new bcr(this, 0, 50);
/* 215 */     this.leftWing1.i = true;
/* 216 */     this.leftWing1.b(128, 128);
/* 217 */     this.leftWing1.a(-0.5F, -4.5F, -1.5F, 25, 29, 3);
/* 218 */     this.leftWing1.a(7.0F, -8.0F, 6.0F);
/* 219 */     this.leftWing2 = new bcr(this, 0, 82);
/* 220 */     this.leftWing2.i = true;
/* 221 */     this.leftWing2.b(128, 128);
/* 222 */     this.leftWing2.a(-2.5F, -5.0F, -1.0F, 23, 24, 2);
/* 223 */     this.leftWing2.a(23.0F, 1.0F, 0.0F);
/* 224 */     this.leftWing3 = new bcr(this, 80, 0);
/* 225 */     this.leftWing3.i = true;
/* 226 */     this.leftWing3.b(128, 128);
/* 227 */     this.leftWing3.a(-2.5F, -5.0F, -0.5F, 23, 22, 1);
/* 228 */     this.leftWing3.a(21.0F, 0.2F, 0.3F);
/* 229 */     this.tail = new bcr(this, 80, 23);
/* 230 */     this.tail.b(128, 128);
/* 231 */     this.tail.a(-8.5F, -5.0F, -1.0F, 17, 40, 2);
/* 232 */     this.tail.a(0.0F, 19.0F, 8.0F);
/* 233 */     this.rightWing1 = new bcr(this, 0, 50);
/* 234 */     this.rightWing1.b(128, 128);
/* 235 */     this.rightWing1.a(-24.5F, -4.5F, -1.5F, 25, 29, 3);
/* 236 */     this.rightWing1.a(-7.0F, -8.0F, 6.0F);
/* 237 */     this.rightWing2 = new bcr(this, 0, 82);
/* 238 */     this.rightWing2.b(128, 128);
/* 239 */     this.rightWing2.a(-20.5F, -5.0F, -1.0F, 23, 24, 2);
/* 240 */     this.rightWing2.a(-23.0F, 1.0F, 0.0F);
/* 241 */     this.rightWing3 = new bcr(this, 80, 0);
/* 242 */     this.rightWing3.b(128, 128);
/* 243 */     this.rightWing3.a(-20.5F, -5.0F, -0.5F, 23, 22, 1);
/* 244 */     this.rightWing3.a(-21.0F, 0.2F, 0.3F);
/*     */ 
/* 246 */     this.rightToeB.a(this.rightClawB);
/* 247 */     this.rightToeR.a(this.rightClawR);
/* 248 */     this.rightToeM.a(this.rightClawM);
/* 249 */     this.rightToeL.a(this.rightClawL);
/* 250 */     this.leftToeB.a(this.leftClawB);
/* 251 */     this.leftToeR.a(this.leftClawR);
/* 252 */     this.leftToeM.a(this.leftClawM);
/* 253 */     this.leftToeL.a(this.leftClawL);
/* 254 */     this.rightAnkle.a(this.rightToeB);
/* 255 */     this.rightAnkle.a(this.rightToeR);
/* 256 */     this.rightAnkle.a(this.rightToeM);
/* 257 */     this.rightAnkle.a(this.rightToeL);
/* 258 */     this.leftAnkle.a(this.leftToeB);
/* 259 */     this.leftAnkle.a(this.leftToeR);
/* 260 */     this.leftAnkle.a(this.leftToeM);
/* 261 */     this.leftAnkle.a(this.leftToeL);
/* 262 */     this.rightLeg.a(this.rightAnkle);
/* 263 */     this.leftLeg.a(this.leftAnkle);
/* 264 */     this.rightThigh.a(this.rightLeg);
/* 265 */     this.leftThigh.a(this.leftLeg);
/* 266 */     this.upperBeak.a(this.upperBeakTip);
/* 267 */     this.lowerBeak.a(this.lowerBeakTip);
/* 268 */     this.head.a(this.upperBeak);
/* 269 */     this.head.a(this.lowerBeak);
/* 270 */     this.head.a(this.headFeathers);
/* 271 */     this.neck3.a(this.head);
/* 272 */     this.neck3.a(this.leftNeckFeathers);
/* 273 */     this.neck3.a(this.rightNeckFeathers);
/* 274 */     this.neck3.a(this.backNeckFeathers);
/* 275 */     this.neck2.a(this.neck3);
/* 276 */     this.neck1.a(this.neck2);
/* 277 */     this.leftWing2.a(this.leftWing3);
/* 278 */     this.leftWing1.a(this.leftWing2);
/* 279 */     this.rightWing2.a(this.rightWing3);
/* 280 */     this.rightWing1.a(this.rightWing2);
/* 281 */     this.body.a(this.rightThigh);
/* 282 */     this.body.a(this.leftThigh);
/* 283 */     this.body.a(this.neck1);
/* 284 */     this.body.a(this.tail);
/* 285 */     this.body.a(this.leftWing1);
/* 286 */     this.body.a(this.rightWing1);
/*     */ 
/* 288 */     EnumMap legMap = new EnumMap(BonesBirdLegs.class);
/* 289 */     legMap.put(BonesBirdLegs.LEFT_KNEE, this.leftThigh);
/* 290 */     legMap.put(BonesBirdLegs.RIGHT_KNEE, this.rightThigh);
/* 291 */     legMap.put(BonesBirdLegs.LEFT_ANKLE, this.leftLeg);
/* 292 */     legMap.put(BonesBirdLegs.RIGHT_ANKLE, this.rightLeg);
/* 293 */     legMap.put(BonesBirdLegs.LEFT_METATARSOPHALANGEAL_ARTICULATIONS, this.leftAnkle);
/* 294 */     legMap.put(BonesBirdLegs.RIGHT_METATARSOPHALANGEAL_ARTICULATIONS, this.rightAnkle);
/* 295 */     legMap.put(BonesBirdLegs.LEFT_BACK_CLAW, this.leftToeB);
/* 296 */     legMap.put(BonesBirdLegs.RIGHT_BACK_CLAW, this.rightToeB);
/* 297 */     this.animationRun = new ModelAnimator(legMap, AnimationRegistry.instance().getAnimation("bird_run"));
/*     */ 
/* 299 */     EnumMap wingMap = new EnumMap(BonesWings.class);
/* 300 */     wingMap.put(BonesWings.LEFT_SHOULDER, this.leftWing1);
/* 301 */     wingMap.put(BonesWings.RIGHT_SHOULDER, this.rightWing1);
/* 302 */     wingMap.put(BonesWings.LEFT_ELBOW, this.leftWing2);
/* 303 */     wingMap.put(BonesWings.RIGHT_ELBOW, this.rightWing2);
/* 304 */     this.animationFlap = new ModelAnimator(wingMap, AnimationRegistry.instance().getAnimation("wing_flap_2_piece"));
/*     */ 
/* 306 */     setRotation(this.body, 0.7F, 0.0F, 0.0F);
/* 307 */     setRotation(this.rightThigh, -0.39F, 0.0F, 0.09F);
/* 308 */     setRotation(this.leftThigh, -0.39F, 0.0F, -0.09F);
/* 309 */     setRotation(this.rightLeg, -0.72F, 0.0F, 0.0F);
/* 310 */     setRotation(this.leftLeg, -0.72F, 0.0F, 0.0F);
/* 311 */     setRotation(this.rightAnkle, 0.1F, 0.2F, 0.0F);
/* 312 */     setRotation(this.leftAnkle, 0.1F, -0.2F, 0.0F);
/* 313 */     setRotation(this.rightToeB, 1.34F, 0.0F, 0.0F);
/* 314 */     setRotation(this.rightToeR, -0.8F, -0.28F, -0.28F);
/* 315 */     setRotation(this.rightToeM, -0.8F, 0.0F, 0.0F);
/* 316 */     setRotation(this.rightToeL, -0.8F, 0.28F, 0.28F);
/* 317 */     setRotation(this.leftToeB, 1.34F, 0.0F, 0.0F);
/* 318 */     setRotation(this.leftToeR, -0.8F, 0.28F, 0.28F);
/* 319 */     setRotation(this.leftToeM, -0.8F, 0.0F, 0.0F);
/* 320 */     setRotation(this.leftToeL, -0.8F, -0.28F, -0.28F);
/* 321 */     setRotation(this.rightClawB, -36.0F, 0.0F, 0.0F);
/*     */ 
/* 323 */     setRotation(this.neck1, -0.18F, 0.0F, 0.0F);
/* 324 */     setRotation(this.neck2, 0.52F, 0.0F, 0.0F);
/* 325 */     setRotation(this.neck3, 0.26F, 0.0F, 0.0F);
/* 326 */     setRotation(this.head, -0.97F, 0.0F, 0.0F);
/* 327 */     setRotation(this.headFeathers, 0.38F, 0.0F, 0.0F);
/* 328 */     setRotation(this.backNeckFeathers, -1.11F, 0.0F, 0.0F);
/* 329 */     setRotation(this.leftNeckFeathers, -0.85F, -1.87F, 0.39F);
/* 330 */     setRotation(this.rightNeckFeathers, -0.85F, 1.87F, -0.39F);
/*     */ 
/* 332 */     setRotation(this.tail, 0.3F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public void a(nm par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
/*     */   {
/* 351 */     this.body.a(par7);
/*     */   }
/*     */ 
/*     */   public void setFlyingAnimations(AnimationState wingState, AnimationState legState, float roll, float headYaw, float headPitch, float parTick)
/*     */   {
/* 356 */     AnimationAction legAction = legState.getCurrentAction();
/* 357 */     AnimationAction wingAction = wingState.getCurrentAction();
/* 358 */     float flapProgress = wingState.getCurrentAnimationTimeInterp(parTick);
/* 359 */     float legProgress = legState.getCurrentAnimationTimeInterp(parTick);
/* 360 */     this.animationFlap.updateAnimation(flapProgress);
/* 361 */     this.animationRun.updateAnimation(legProgress);
/* 362 */     if (legAction == AnimationAction.RUN)
/*     */     {
/* 365 */       if ((legProgress >= 0.109195F) && (legProgress < 0.5373563F))
/*     */       {
/* 367 */         legProgress = (float)(legProgress + 0.0D);
/*     */ 
/* 371 */         float t = 25.132742F * legProgress / 0.7967914F;
/* 372 */         this.body.f += (float)(-Math.cos(t) * 0.1D);
/* 373 */         this.neck1.f += (float)(Math.cos(t) * 0.08D);
/* 374 */         this.body.c += -(float)(Math.cos(t) * 1.0D);
/*     */       }
/*     */     }
/*     */ 
/* 378 */     if (wingAction == AnimationAction.WINGFLAP)
/*     */     {
/* 380 */       float flapCycle = flapProgress / 0.2714932F;
/*     */ 
/* 382 */       this.body.d += LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 1.4F;
/*     */       bcr tmp208_205 = this.rightThigh; tmp208_205.f = ((float)(tmp208_205.f + LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 0.08726646324990228D));
/*     */       bcr tmp238_235 = this.leftThigh; tmp238_235.f = ((float)(tmp238_235.f + LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 0.08726646324990228D));
/*     */       bcr tmp268_265 = this.tail; tmp268_265.f = ((float)(tmp268_265.f + LongHashMapEntry.b(flapCycle * 3.141593F * 2.0F) * 0.03490658588512815D));
/*     */     }
/*     */ 
/* 388 */     this.body.h = (-roll / 180.0F * 3.141593F);
/*     */ 
/* 391 */     headPitch = (float)MathUtil.boundAngle180Deg(headPitch);
/* 392 */     if (headPitch > 37.16F)
/* 393 */       headPitch = 37.16F;
/* 394 */     else if (headPitch < -56.650002F) {
/* 395 */       headPitch = -56.650002F;
/*     */     }
/* 397 */     float pitchFactor = (headPitch + 56.650002F) / 93.800003F;
/* 398 */     this.head.f += -0.96F + pitchFactor * -0.1400001F;
/* 399 */     this.neck3.f += 0.378F + pitchFactor * -0.528F;
/* 400 */     this.neck2.f += 0.4F + pitchFactor * -0.4F;
/* 401 */     this.neck1.f += 0.513F + pitchFactor * -0.613F;
/*     */ 
/* 403 */     headYaw = (float)MathUtil.boundAngle180Deg(headYaw);
/* 404 */     if (headYaw > 30.5F)
/* 405 */       headYaw = 30.5F;
/* 406 */     else if (headYaw < -30.5F) {
/* 407 */       headYaw = -30.5F;
/*     */     }
/* 409 */     float yawFactor = (headYaw + 30.5F) / 61.0F;
/* 410 */     this.head.h += 0.8F + yawFactor * 2.0F * -0.8F;
/* 411 */     this.neck3.h += 0.38F + yawFactor * 2.0F * -0.38F;
/* 412 */     this.neck2.h += 0.14F + yawFactor * 2.0F * -0.14F;
/* 413 */     this.head.g += -0.7F + yawFactor * 2.0F * 0.7F;
/* 414 */     this.neck3.g += -0.12F + yawFactor * 2.0F * 0.12F;
/*     */   }
/*     */ 
/*     */   public void a(float limbPeriod, float limbMaxMovement, float ticksExisted, float headYaw, float entityPitch, float unitScale, nm entity)
/*     */   {
/* 419 */     super.a(limbPeriod, limbMaxMovement, ticksExisted, headYaw, entityPitch, unitScale, entity);
/*     */     bcr tmp19_16 = this.body; tmp19_16.f = ((float)(tmp19_16.f + (0.8707963705062867D - entityPitch / 180.0F * 3.141593F)));
/*     */   }
/*     */ 
/*     */   public void resetSkeleton()
/*     */   {
/* 425 */     setRotation(this.body, 0.7F, 0.0F, 0.0F);
/* 426 */     setRotation(this.rightThigh, -0.39F, 0.0F, 0.09F);
/* 427 */     setRotation(this.leftThigh, -0.39F, 0.0F, -0.09F);
/* 428 */     setRotation(this.rightLeg, -0.72F, 0.0F, 0.0F);
/* 429 */     setRotation(this.leftLeg, -0.72F, 0.0F, 0.0F);
/* 430 */     setRotation(this.rightAnkle, 0.1F, 0.2F, 0.0F);
/* 431 */     setRotation(this.leftAnkle, 0.1F, -0.2F, 0.0F);
/* 432 */     setRotation(this.rightToeB, 1.34F, 0.0F, 0.0F);
/* 433 */     setRotation(this.rightToeR, -0.8F, -0.28F, -0.28F);
/* 434 */     setRotation(this.rightToeM, -0.8F, 0.0F, 0.0F);
/* 435 */     setRotation(this.rightToeL, -0.8F, 0.28F, 0.28F);
/* 436 */     setRotation(this.leftToeB, 1.34F, 0.0F, 0.0F);
/* 437 */     setRotation(this.leftToeR, -0.8F, 0.28F, 0.28F);
/* 438 */     setRotation(this.leftToeM, -0.8F, 0.0F, 0.0F);
/* 439 */     setRotation(this.leftToeL, -0.8F, -0.28F, -0.28F);
/*     */ 
/* 441 */     setRotation(this.neck1, 0.0F, 0.0F, 0.0F);
/* 442 */     setRotation(this.neck2, 0.0F, 0.0F, 0.0F);
/* 443 */     setRotation(this.neck3, 0.0F, 0.0F, 0.0F);
/* 444 */     setRotation(this.head, 0.0F, 0.0F, 0.0F);
/* 445 */     setRotation(this.headFeathers, 0.38F, 0.0F, 0.0F);
/* 446 */     setRotation(this.backNeckFeathers, -1.11F, 0.0F, 0.0F);
/* 447 */     setRotation(this.leftNeckFeathers, -0.85F, -1.87F, 0.39F);
/* 448 */     setRotation(this.rightNeckFeathers, -0.85F, 1.87F, -0.39F);
/* 449 */     setRotation(this.tail, 0.3F, 0.0F, 0.0F);
/*     */ 
/* 451 */     this.body.a(0.0F, -27.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   private void setRotation(bcr model, float x, float y, float z)
/*     */   {
/* 456 */     model.f = x;
/* 457 */     model.g = y;
/* 458 */     model.h = z;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.ModelGiantBird
 * JD-Core Version:    0.6.2
 */