/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.util.FiniteSelectionPool;
/*     */ import invmod.common.util.ISelect;
/*     */ import invmod.common.util.RandomSelectionPool;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class IMWaveBuilder
/*     */ {
/*     */   public static final int WAVES_DEFINED = 11;
/*     */   private static final float ZOMBIE_T1_WEIGHT = 1.0F;
/*     */   private static final float ZOMBIE_T2_WEIGHT = 2.0F;
/*     */   private static final float ZOMBIE_T2_PIGMAN_WEIGHT = 1.0F;
/*     */   private static final float SPIDER_T1_WEIGHT = 1.0F;
/*     */   private static final float SPIDER_T2_WEIGHT = 2.0F;
/*  30 */   private static Map<String, IEntityIMPattern> commonPatterns = new HashMap();
/*     */   private Random rand;
/*     */ 
/*     */   public IMWaveBuilder()
/*     */   {
/*  36 */     this.rand = new Random();
/*     */   }
/*     */ 
/*     */   public Wave generateWave(float difficulty, float tierLevel, int lengthSeconds)
/*     */   {
/*  44 */     float basicMobsPerSecond = 0.12F * difficulty;
/*  45 */     int numberOfGroups = 7;
/*  46 */     int numberOfBigGroups = 1;
/*  47 */     float proportionInGroups = 0.5F;
/*  48 */     int mobsPerGroup = Math.round(proportionInGroups * basicMobsPerSecond * lengthSeconds / (numberOfGroups + numberOfBigGroups * 2));
/*  49 */     int mobsPerBigGroup = mobsPerGroup * 2;
/*  50 */     int remainingMobs = (int)(basicMobsPerSecond * lengthSeconds) - mobsPerGroup * numberOfGroups - mobsPerBigGroup * numberOfBigGroups;
/*  51 */     int mobsPerSteady = Math.round(0.7F * remainingMobs / numberOfGroups);
/*  52 */     int extraMobsForFinale = Math.round(0.3F * remainingMobs);
/*  53 */     int extraMobsForCleanup = (int)(basicMobsPerSecond * lengthSeconds * 0.2F);
/*  54 */     float timeForGroups = 0.5F;
/*  55 */     int groupTimeInterval = (int)(lengthSeconds * 1000 * timeForGroups / (numberOfGroups + numberOfBigGroups * 3));
/*  56 */     int steadyTimeInterval = (int)(lengthSeconds * 1000 * (1.0F - timeForGroups) / numberOfGroups);
/*     */ 
/*  58 */     int time = 0;
/*  59 */     ArrayList entryList = new ArrayList();
/*  60 */     for (int i = 0; i < numberOfGroups; i++)
/*     */     {
/*  62 */       if (this.rand.nextInt(2) == 0)
/*     */       {
/*  64 */         entryList.add(new WaveEntry(time, time + 3500, mobsPerGroup, 500, generateGroupPool(tierLevel), 25, 3));
/*  65 */         entryList.add(new WaveEntry(time += groupTimeInterval, time += steadyTimeInterval, mobsPerSteady, 2000, generateSteadyPool(tierLevel), 160, 5));
/*     */       }
/*     */       else
/*     */       {
/*  69 */         entryList.add(new WaveEntry(time, time += steadyTimeInterval, mobsPerSteady, 2000, generateSteadyPool(tierLevel), 160, 5));
/*  70 */         entryList.add(new WaveEntry(time, time + 5000, mobsPerGroup, 500, generateGroupPool(tierLevel), 25, 3));
/*  71 */         time += groupTimeInterval;
/*     */       }
/*     */     }
/*     */ 
/*  75 */     time = (int)(time + groupTimeInterval * 0.75D);
/*  76 */     FiniteSelectionPool finaleGroup = new FiniteSelectionPool();
/*  77 */     finaleGroup.addEntry(getPattern("thrower"), mobsPerBigGroup / 5);
/*  78 */     generateGroupPool(tierLevel + 0.5F, finaleGroup, mobsPerBigGroup);
/*  79 */     WaveEntry finale = new WaveEntry(time, time + 8000, mobsPerBigGroup + mobsPerBigGroup / 7, 500, finaleGroup, 45, 3);
/*  80 */     finale.addAlert("A large number of mobs are slipping through the nexus rift!", 0);
/*     */ 
/*  82 */     entryList.add(finale);
/*  83 */     entryList.add(new WaveEntry(time + 5000, (int)(time + groupTimeInterval * 2.25F), extraMobsForFinale / 2, 500, generateSteadyPool(tierLevel), 160, 5));
/*  84 */     entryList.add(new WaveEntry(time + 5000, (int)(time + groupTimeInterval * 2.25F), extraMobsForFinale / 2, 500, generateSteadyPool(tierLevel), 160, 5));
/*  85 */     entryList.add(new WaveEntry(time + 5000, (int)(time + groupTimeInterval * 2.25F), extraMobsForFinale / 2, 500, generateSteadyPool(tierLevel), 160, 5));
/*  86 */     entryList.add(new WaveEntry(time + 15000, (int)(time + 10000 + groupTimeInterval * 2.25F), extraMobsForCleanup, 500, generateSteadyPool(tierLevel)));
/*  87 */     time = (int)(time + groupTimeInterval * 2.25D);
/*     */ 
/*  89 */     return new Wave(time + 16000, groupTimeInterval * 3, entryList);
/*     */   }
/*     */ 
/*     */   private ISelect<IEntityIMPattern> generateGroupPool(float tierLevel)
/*     */   {
/* 100 */     RandomSelectionPool newPool = new RandomSelectionPool();
/* 101 */     generateGroupPool(tierLevel, newPool, 6.0F);
/* 102 */     return newPool;
/*     */   }
/*     */ 
/*     */   private void generateGroupPool(float tierLevel, FiniteSelectionPool<IEntityIMPattern> startPool, int amount)
/*     */   {
/* 112 */     RandomSelectionPool newPool = new RandomSelectionPool();
/* 113 */     generateGroupPool(tierLevel, newPool, 6.0F);
/* 114 */     startPool.addEntry(newPool, amount);
/*     */   }
/*     */ 
/*     */   private void generateGroupPool(float tierLevel, RandomSelectionPool<IEntityIMPattern> startPool, float weight)
/*     */   {
/* 123 */     float[] weights = new float[6];
/* 124 */     for (int i = 0; i < 6; i++)
/*     */     {
/* 126 */       if (tierLevel - i * 0.5F > 0.0F) {
/* 127 */         weights[i] = (tierLevel - i <= 1.0F ? tierLevel - i * 0.5F : 1.0F);
/*     */       }
/*     */     }
/* 130 */     RandomSelectionPool zombiePool = new RandomSelectionPool();
/* 131 */     zombiePool.addEntry(getPattern("zombie_t1_any"), 1.0F * weights[0]);
/* 132 */     zombiePool.addEntry(getPattern("zombie_t2_any_basic"), 2.0F * weights[2]);
/* 133 */     zombiePool.addEntry(getPattern("zombie_t2_pigman"), 1.0F * weights[3]);
/*     */ 
/* 135 */     RandomSelectionPool spiderPool = new RandomSelectionPool();
/* 136 */     spiderPool.addEntry(getPattern("spider_t1_any"), 1.0F * weights[0]);
/* 137 */     spiderPool.addEntry(getPattern("spider_t2_any"), 2.0F * weights[2]);
/*     */ 
/* 139 */     RandomSelectionPool basicPool = new RandomSelectionPool();
/* 140 */     basicPool.addEntry(zombiePool, 3.1F);
/* 141 */     basicPool.addEntry(spiderPool, 0.7F);
/* 142 */     basicPool.addEntry(getPattern("skeleton_t1_any"), 0.8F);
/*     */ 
/* 144 */     RandomSelectionPool specialPool = new RandomSelectionPool();
/* 145 */     specialPool.addEntry(getPattern("pigengy_t1_any"), 4.0F);
/* 146 */     specialPool.addEntry(getPattern("thrower"), 1.1F * weights[4]);
/* 147 */     specialPool.addEntry(getPattern("zombie_t3_any"), 1.1F * weights[5]);
/* 148 */     specialPool.addEntry(getPattern("creeper_t1_basic"), 0.7F * weights[3]);
/*     */ 
/* 150 */     startPool.addEntry(basicPool, weight * 0.8333333F);
/* 151 */     startPool.addEntry(specialPool, weight * 0.1666667F);
/*     */   }
/*     */ 
/*     */   private ISelect<IEntityIMPattern> generateSteadyPool(float tierLevel)
/*     */   {
/* 156 */     float[] weights = new float[6];
/* 157 */     for (int i = 0; i < 6; i++)
/*     */     {
/* 159 */       if (tierLevel - i * 0.5F > 0.0F) {
/* 160 */         weights[i] = (tierLevel - i <= 1.0F ? tierLevel - i * 0.5F : 1.0F);
/*     */       }
/*     */     }
/* 163 */     RandomSelectionPool zombiePool = new RandomSelectionPool();
/* 164 */     zombiePool.addEntry(getPattern("zombie_t1_any"), 1.0F * weights[0]);
/* 165 */     zombiePool.addEntry(getPattern("zombie_t2_any_basic"), 2.0F * weights[2]);
/* 166 */     zombiePool.addEntry(getPattern("zombie_t2_pigman"), 1.0F * weights[3]);
/*     */ 
/* 168 */     RandomSelectionPool spiderPool = new RandomSelectionPool();
/* 169 */     spiderPool.addEntry(getPattern("spider_t1_any"), 1.0F * weights[0]);
/* 170 */     spiderPool.addEntry(getPattern("spider_t2_any"), 2.0F * weights[2]);
/*     */ 
/* 172 */     RandomSelectionPool basicPool = new RandomSelectionPool();
/* 173 */     basicPool.addEntry(zombiePool, 3.1F);
/* 174 */     basicPool.addEntry(spiderPool, 0.7F);
/* 175 */     basicPool.addEntry(getPattern("skeleton_t1_any"), 0.8F);
/*     */ 
/* 177 */     RandomSelectionPool specialPool = new RandomSelectionPool();
/* 178 */     specialPool.addEntry(getPattern("pigengy_t1_any"), 3.0F);
/* 179 */     specialPool.addEntry(getPattern("zombie_t3_any"), 1.1F * weights[5]);
/* 180 */     specialPool.addEntry(getPattern("creeper_t1_basic"), 0.8F * weights[3]);
/*     */ 
/* 182 */     RandomSelectionPool pool = new RandomSelectionPool();
/* 183 */     pool.addEntry(basicPool, 9.0F);
/* 184 */     pool.addEntry(specialPool, 1.0F);
/* 185 */     return pool;
/*     */   }
/*     */ 
/*     */   public static IEntityIMPattern getPattern(String s)
/*     */   {
/* 194 */     if (commonPatterns.containsKey(s))
/*     */     {
/* 196 */       return (IEntityIMPattern)commonPatterns.get(s);
/*     */     }
/*     */ 
/* 200 */     mod_Invasion.log("Non-existing pattern name in wave definition: " + s);
/* 201 */     return (IEntityIMPattern)commonPatterns.get("zombie_t1_any");
/*     */   }
/*     */ 
/*     */   public static boolean isPatternNameValid(String s)
/*     */   {
/* 210 */     return commonPatterns.containsKey(s);
/*     */   }
/*     */ 
/*     */   public Wave generateWave(int waveNumber, int difficulty)
/*     */   {
/* 215 */     return null;
/*     */   }
/*     */ 
/*     */   public static Wave generateMainInvasionWave(int waveNumber)
/*     */   {
/* 224 */     if (waveNumber > 11) {
/* 225 */       return generateExtendedWave(waveNumber);
/*     */     }
/* 227 */     ArrayList entryList = new ArrayList();
/* 228 */     switch (waveNumber)
/*     */     {
/*     */     case 1:
/* 231 */       RandomSelectionPool wave1BasePool = new RandomSelectionPool();
/* 232 */       wave1BasePool.addEntry(getPattern("zombie_t1_any"), 3.0F);
/* 233 */       wave1BasePool.addEntry(getPattern("spider_t1_any"), 1.0F);
/* 234 */       WaveEntry wave1Base = new WaveEntry(0, 90000, 8, 2000, wave1BasePool);
/* 235 */       entryList.add(wave1Base);
/*     */ 
/* 237 */       FiniteSelectionPool wave1BurstPool = new FiniteSelectionPool();
/* 238 */       wave1BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 239 */       wave1BurstPool.addEntry(getPattern("zombie_t1_any"), 2);
/* 240 */       WaveEntry wave1Engy = new WaveEntry(70000, 73000, 3, 500, wave1BurstPool, 25, 3);
/* 241 */       entryList.add(wave1Engy);
/* 242 */       return new Wave(110000, 15000, entryList);
/*     */     case 2:
/* 244 */       RandomSelectionPool wave2BasePool = new RandomSelectionPool();
/* 245 */       wave2BasePool.addEntry(getPattern("zombie_t1_any"), 3.0F);
/* 246 */       wave2BasePool.addEntry(getPattern("spider_t1_any"), 1.0F);
/* 247 */       wave2BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 248 */       wave2BasePool.addEntry(getPattern("creeper_t1_basic"), 0.038F);
/* 249 */       WaveEntry wave2Base = new WaveEntry(0, 50000, 5, 2000, wave2BasePool, 110, 5);
/* 250 */       entryList.add(wave2Base);
/*     */ 
/* 252 */       WaveEntry wave2Base2 = new WaveEntry(50000, 100000, 5, 2000, wave2BasePool.clone(), 110, 5);
/* 253 */       entryList.add(wave2Base2);
/*     */ 
/* 255 */       RandomSelectionPool wave2SpecialPool = new RandomSelectionPool();
/* 256 */       wave2SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1.0F);
/* 257 */       WaveEntry wave2Special = new WaveEntry(20000, 23000, 1, 500, wave2SpecialPool);
/* 258 */       entryList.add(wave2Special);
/*     */ 
/* 260 */       FiniteSelectionPool wave2BurstPool = new FiniteSelectionPool();
/* 261 */       wave2BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 262 */       wave2BurstPool.addEntry(getPattern("zombie_t1_any"), 2);
/* 263 */       WaveEntry wave2Burst = new WaveEntry(65000, 68000, 3, 500, wave2BurstPool, 25, 2);
/* 264 */       entryList.add(wave2Burst);
/* 265 */       return new Wave(120000, 15000, entryList);
/*     */     case 3:
/* 267 */       RandomSelectionPool wave3BasePool = new RandomSelectionPool();
/* 268 */       wave3BasePool.addEntry(getPattern("zombie_t1_any"), 3.0F);
/* 269 */       wave3BasePool.addEntry(getPattern("spider_t1_any"), 1.0F);
/* 270 */       wave3BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 271 */       wave3BasePool.addEntry(getPattern("creeper_t1_basic"), 0.04F);
/* 272 */       WaveEntry wave3Base1 = new WaveEntry(0, 30000, 6, 2000, wave3BasePool, 45, 3);
/* 273 */       entryList.add(wave3Base1);
/*     */ 
/* 275 */       WaveEntry wave3Base2 = new WaveEntry(80000, 100000, 5, 2000, wave3BasePool.clone(), 45, 3);
/* 276 */       entryList.add(wave3Base2);
/*     */ 
/* 278 */       RandomSelectionPool wave3SpecialPool = new RandomSelectionPool();
/* 279 */       wave3SpecialPool.addEntry(getPattern("spider_t2_any"), 1.0F);
/* 280 */       WaveEntry wave3Special = new WaveEntry(10000, 12000, 1, 500, wave3SpecialPool);
/* 281 */       entryList.add(wave3Special);
/*     */ 
/* 283 */       FiniteSelectionPool wave3BurstPool = new FiniteSelectionPool();
/* 284 */       wave3BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 285 */       wave3BurstPool.addEntry(getPattern("zombie_t1_any"), 1);
/* 286 */       wave3BurstPool.addEntry(getPattern("zombie_t2_plain"), 1);
/* 287 */       wave3BurstPool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 288 */       wave3BurstPool.addEntry(getPattern("spider_t1_any"), 1);
/* 289 */       wave3BurstPool.addEntry(getPattern("creeper_t1_basic"), 1);
/* 290 */       WaveEntry wave3Burst = new WaveEntry(50000, 55000, 5, 500, wave3BurstPool, 25, 6);
/* 291 */       wave3Burst.addAlert("A small group of mobs have gathered...", 0);
/* 292 */       entryList.add(wave3Burst);
/* 293 */       return new Wave(120000, 18000, entryList);
/*     */     case 4:
/* 295 */       RandomSelectionPool wave4BasePool = new RandomSelectionPool();
/* 296 */       wave4BasePool.addEntry(getPattern("zombie_t1_any"), 3.0F);
/* 297 */       wave4BasePool.addEntry(getPattern("spider_t1_any"), 1.0F);
/* 298 */       wave4BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 299 */       wave4BasePool.addEntry(getPattern("creeper_t1_basic"), 0.058F);
/* 300 */       WaveEntry wave4Base1 = new WaveEntry(0, 50000, 6, 2000, wave4BasePool, 110, 5);
/* 301 */       entryList.add(wave4Base1);
/*     */ 
/* 303 */       WaveEntry wave4Base2 = new WaveEntry(50000, 100000, 6, 2000, wave4BasePool.clone(), 110, 5);
/* 304 */       entryList.add(wave4Base2);
/*     */ 
/* 306 */       FiniteSelectionPool wave4SpecialPool = new FiniteSelectionPool();
/* 307 */       wave4SpecialPool.addEntry(getPattern("spider_t2_any"), 1);
/* 308 */       wave4SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 309 */       WaveEntry wave4Special = new WaveEntry(0, 90000, 3, 500, wave4SpecialPool);
/* 310 */       entryList.add(wave4Special);
/*     */ 
/* 312 */       FiniteSelectionPool wave4BurstPool = new FiniteSelectionPool();
/* 313 */       wave4BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 314 */       wave4BurstPool.addEntry(getPattern("zombie_t2_any_basic"), 1);
/* 315 */       WaveEntry wave4Burst = new WaveEntry(70000, 75000, 2, 500, wave4BurstPool, 25, 2);
/* 316 */       entryList.add(wave4Burst);
/* 317 */       return new Wave(120000, 18000, entryList);
/*     */     case 5:
/* 319 */       RandomSelectionPool wave5BasePool = new RandomSelectionPool();
/* 320 */       wave5BasePool.addEntry(getPattern("zombie_t1_any"), 3.0F);
/* 321 */       wave5BasePool.addEntry(getPattern("spider_t1_any"), 1.0F);
/* 322 */       wave5BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 323 */       wave5BasePool.addEntry(getPattern("creeper_t1_basic"), 0.054F);
/* 324 */       WaveEntry wave5Base1 = new WaveEntry(0, 40000, 6, 2000, wave5BasePool, 110, 5);
/* 325 */       entryList.add(wave5Base1);
/*     */ 
/* 327 */       WaveEntry wave5Base2 = new WaveEntry(40000, 80000, 6, 2000, wave5BasePool.clone(), 110, 5);
/* 328 */       entryList.add(wave5Base2);
/*     */ 
/* 330 */       RandomSelectionPool wave5SpecialPool = new RandomSelectionPool();
/* 331 */       wave5SpecialPool.addEntry(getPattern("spider_t2_any"), 1.0F);
/* 332 */       wave5SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1.0F);
/* 333 */       WaveEntry wave5Special = new WaveEntry(0, 80000, 3, 500, wave5SpecialPool);
/* 334 */       entryList.add(wave5Special);
/*     */ 
/* 336 */       FiniteSelectionPool wave5BurstPool = new FiniteSelectionPool();
/* 337 */       wave5BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 338 */       wave5BurstPool.addEntry(getPattern("zombie_t1_any"), 3);
/* 339 */       wave5BurstPool.addEntry(getPattern("zombie_t2_any_basic"), 1);
/* 340 */       wave5BurstPool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 341 */       wave5BurstPool.addEntry(getPattern("spider_t2_any"), 1);
/* 342 */       wave5BurstPool.addEntry(getPattern("thrower"), 1);
/* 343 */       WaveEntry wave5Burst = new WaveEntry(115000, 118000, 8, 500, wave5BurstPool, 35, 5);
/* 344 */       wave5Burst.addAlert("A large number of mobs are slipping through the nexus rift!", 0);
/* 345 */       entryList.add(wave5Burst);
/*     */ 
/* 347 */       FiniteSelectionPool wave5FinalePool = new FiniteSelectionPool();
/* 348 */       wave5FinalePool.addEntry(getPattern("zombie_t1_any"), 3);
/* 349 */       wave5FinalePool.addEntry(getPattern("zombie_t2_any_basic"), 1);
/* 350 */       wave5FinalePool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 351 */       wave5FinalePool.addEntry(getPattern("spider_t2_any"), 1);
/* 352 */       wave5FinalePool.addEntry(getPattern("spider_t1_any"), 1);
/* 353 */       WaveEntry wave5Finale = new WaveEntry(135000, 165000, 7, 500, wave5FinalePool);
/* 354 */       entryList.add(wave5Finale);
/* 355 */       return new Wave(130000, 80000, entryList);
/*     */     case 6:
/* 357 */       RandomSelectionPool wave6BasePool = new RandomSelectionPool();
/* 358 */       wave6BasePool.addEntry(getPattern("zombie_t1_any"), 2.0F);
/* 359 */       wave6BasePool.addEntry(getPattern("zombie_t2_any_basic"), 1.0F);
/* 360 */       wave6BasePool.addEntry(getPattern("spider_t1_any"), 0.7F);
/* 361 */       wave6BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 362 */       wave6BasePool.addEntry(getPattern("creeper_t1_basic"), 0.064F);
/* 363 */       WaveEntry wave6Base1 = new WaveEntry(0, 50000, 7, 2000, wave6BasePool, 110, 5);
/* 364 */       entryList.add(wave6Base1);
/*     */ 
/* 366 */       WaveEntry wave6Base2 = new WaveEntry(50000, 100000, 6, 2000, wave6BasePool.clone(), 110, 5);
/* 367 */       entryList.add(wave6Base2);
/*     */ 
/* 369 */       FiniteSelectionPool wave6SpecialPool = new FiniteSelectionPool();
/* 370 */       wave6SpecialPool.addEntry(getPattern("spider_t2_any"), 1);
/* 371 */       wave6SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 372 */       WaveEntry wave6Special = new WaveEntry(0, 90000, 2, 500, wave6SpecialPool);
/* 373 */       entryList.add(wave6Special);
/*     */ 
/* 375 */       FiniteSelectionPool wave6BurstPool = new FiniteSelectionPool();
/* 376 */       wave6BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 377 */       wave6BurstPool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 378 */       wave6BurstPool.addEntry(getPattern("zombie_t1_any"), 1);
/* 379 */       WaveEntry wave6Burst = new WaveEntry(70000, 75000, 4, 500, wave6BurstPool, 25, 2);
/* 380 */       entryList.add(wave6Burst);
/* 381 */       return new Wave(110000, 25000, entryList);
/*     */     case 7:
/* 383 */       RandomSelectionPool wave7BasePool = new RandomSelectionPool();
/* 384 */       wave7BasePool.addEntry(getPattern("zombie_t1_any"), 2.0F);
/* 385 */       wave7BasePool.addEntry(getPattern("zombie_t2_any_basic"), 1.0F);
/* 386 */       wave7BasePool.addEntry(getPattern("spider_t1_any"), 0.7F);
/* 387 */       wave7BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 388 */       wave7BasePool.addEntry(getPattern("creeper_t1_basic"), 0.064F);
/* 389 */       WaveEntry wave7Base1 = new WaveEntry(0, 30000, 7, 2000, wave7BasePool, 45, 5);
/* 390 */       entryList.add(wave7Base1);
/*     */ 
/* 392 */       FiniteSelectionPool wave7SpecialPool = new FiniteSelectionPool();
/* 393 */       wave7SpecialPool.addEntry(getPattern("spider_t2_any"), 1);
/* 394 */       wave7SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 395 */       wave7SpecialPool.addEntry(getPattern("thrower"), 1);
/* 396 */       WaveEntry wave7Special = new WaveEntry(0, 60000, 3, 500, wave7SpecialPool);
/* 397 */       entryList.add(wave7Special);
/*     */ 
/* 399 */       FiniteSelectionPool wave7BurstPool = new FiniteSelectionPool();
/* 400 */       wave7BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 401 */       wave7BurstPool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 402 */       wave7BurstPool.addEntry(getPattern("zombie_t1_any"), 1);
/* 403 */       wave7BurstPool.addEntry(getPattern("spider_t2_any"), 1);
/* 404 */       WaveEntry wave7Burst = new WaveEntry(65000, 67000, 5, 500, wave7BurstPool, 45, 2);
/* 405 */       entryList.add(wave7Burst);
/*     */ 
/* 407 */       FiniteSelectionPool wave7Burst2Pool = new FiniteSelectionPool();
/* 408 */       wave7Burst2Pool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 409 */       wave7Burst2Pool.addEntry(getPattern("zombie_t2_pigman"), 3);
/* 410 */       WaveEntry wave7Burst2 = new WaveEntry(95000, 97000, 4, 500, wave7Burst2Pool, 45, 2);
/* 411 */       entryList.add(wave7Burst2);
/*     */ 
/* 413 */       return new Wave(120000, 36000, entryList);
/*     */     case 8:
/* 415 */       RandomSelectionPool wave8BasePool = new RandomSelectionPool();
/* 416 */       wave8BasePool.addEntry(getPattern("zombie_t1_any"), 2.0F);
/* 417 */       wave8BasePool.addEntry(getPattern("zombie_t2_any_basic"), 1.5F);
/* 418 */       wave8BasePool.addEntry(getPattern("spider_t1_any"), 0.7F);
/* 419 */       wave8BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 420 */       wave8BasePool.addEntry(getPattern("creeper_t1_basic"), 0.064F);
/* 421 */       WaveEntry wave8Base1 = new WaveEntry(0, 35000, 7, 2000, wave8BasePool, 110, 5);
/* 422 */       entryList.add(wave8Base1);
/*     */ 
/* 424 */       WaveEntry wave8Base2 = new WaveEntry(80000, 110000, 4, 2000, wave8BasePool.clone(), 110, 5);
/* 425 */       entryList.add(wave8Base2);
/*     */ 
/* 427 */       FiniteSelectionPool wave8SpecialPool = new FiniteSelectionPool();
/* 428 */       wave8SpecialPool.addEntry(getPattern("spider_t2_any"), 1);
/* 429 */       wave8SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 430 */       WaveEntry wave8Special = new WaveEntry(0, 90000, 2, 500, wave8SpecialPool);
/* 431 */       entryList.add(wave8Special);
/*     */ 
/* 433 */       FiniteSelectionPool wave8BurstPool = new FiniteSelectionPool();
/* 434 */       wave8BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 435 */       wave8BurstPool.addEntry(getPattern("zombie_t2_any_basic"), 3);
/* 436 */       wave8BurstPool.addEntry(getPattern("zombie_t1_any"), 2);
/* 437 */       wave8BurstPool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 438 */       wave8BurstPool.addEntry(getPattern("creeper_t1_basic"), 1);
/* 439 */       WaveEntry wave8Burst = new WaveEntry(60000, 63000, 8, 500, wave8BurstPool, 25, 2);
/* 440 */       wave8Burst.addAlert("A group of mobs have gathered...", 0);
/* 441 */       entryList.add(wave8Burst);
/* 442 */       return new Wave(110000, 30000, entryList);
/*     */     case 9:
/* 444 */       RandomSelectionPool wave9BasePool = new RandomSelectionPool();
/* 445 */       wave9BasePool.addEntry(getPattern("zombie_t1_any"), 2.0F);
/* 446 */       wave9BasePool.addEntry(getPattern("zombie_t2_any_basic"), 2.0F);
/* 447 */       wave9BasePool.addEntry(getPattern("spider_t1_any"), 0.7F);
/* 448 */       wave9BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 449 */       wave9BasePool.addEntry(getPattern("creeper_t1_basic"), 0.074F);
/* 450 */       WaveEntry wave9Base1 = new WaveEntry(0, 30000, 7, 2000, wave9BasePool, 45, 5);
/* 451 */       entryList.add(wave9Base1);
/*     */ 
/* 453 */       FiniteSelectionPool wave9SpecialPool = new FiniteSelectionPool();
/* 454 */       wave9SpecialPool.addEntry(getPattern("spider_t2_any"), 1);
/* 455 */       wave9SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 456 */       WaveEntry wave9Special = new WaveEntry(0, 90000, 3, 500, wave9SpecialPool);
/* 457 */       entryList.add(wave9Special);
/*     */ 
/* 459 */       FiniteSelectionPool wave9BurstPool = new FiniteSelectionPool();
/* 460 */       wave9BurstPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 461 */       wave9BurstPool.addEntry(getPattern("zombie_t2_pigman"), 3);
/* 462 */       wave9BurstPool.addEntry(getPattern("zombie_t1_any"), 1);
/* 463 */       wave9BurstPool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 464 */       wave9BurstPool.addEntry(getPattern("zombie_t3_any"), 1);
/* 465 */       WaveEntry wave9Burst = new WaveEntry(65000, 67000, 6, 500, wave9BurstPool, 25, 3);
/* 466 */       entryList.add(wave9Burst);
/*     */ 
/* 468 */       FiniteSelectionPool wave9Burst2Pool = new FiniteSelectionPool();
/* 469 */       wave9Burst2Pool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 470 */       wave9Burst2Pool.addEntry(getPattern("zombie_t1_any"), 3);
/* 471 */       wave9Burst2Pool.addEntry(getPattern("spider_t2_any"), 1);
/* 472 */       WaveEntry wave9Burst2 = new WaveEntry(95000, 97000, 6, 500, wave9Burst2Pool, 45, 2);
/* 473 */       entryList.add(wave9Burst2);
/*     */ 
/* 475 */       return new Wave(120000, 35000, entryList);
/*     */     case 10:
/* 477 */       RandomSelectionPool wave10BasePool = new RandomSelectionPool();
/* 478 */       wave10BasePool.addEntry(getPattern("zombie_t1_any"), 1.5F);
/* 479 */       wave10BasePool.addEntry(getPattern("zombie_t2_any_basic"), 2.2F);
/* 480 */       wave10BasePool.addEntry(getPattern("spider_t1_any"), 0.7F);
/* 481 */       wave10BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 482 */       wave10BasePool.addEntry(getPattern("creeper_t1_basic"), 0.084F);
/* 483 */       WaveEntry wave10Base1 = new WaveEntry(0, 40000, 9, 2000, wave10BasePool, 110, 5);
/* 484 */       entryList.add(wave10Base1);
/*     */ 
/* 486 */       WaveEntry wave10Base2 = new WaveEntry(40000, 80000, 7, 2000, wave10BasePool.clone(), 110, 5);
/* 487 */       entryList.add(wave10Base2);
/*     */ 
/* 489 */       RandomSelectionPool wave10SpecialPool = new RandomSelectionPool();
/* 490 */       wave10SpecialPool.addEntry(getPattern("spider_t2_any"), 1.0F);
/* 491 */       wave10SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1.0F);
/* 492 */       WaveEntry wave10Special = new WaveEntry(0, 80000, 3, 500, wave10SpecialPool);
/* 493 */       entryList.add(wave10Special);
/*     */ 
/* 495 */       FiniteSelectionPool wave10BurstPool = new FiniteSelectionPool();
/* 496 */       wave10BurstPool.addEntry(getPattern("pigengy_t1_any"), 2);
/* 497 */       wave10BurstPool.addEntry(getPattern("zombie_t1_any"), 2);
/* 498 */       wave10BurstPool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 499 */       wave10BurstPool.addEntry(getPattern("zombie_t2_pigman"), 3);
/* 500 */       wave10BurstPool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 501 */       wave10BurstPool.addEntry(getPattern("spider_t2_any"), 1);
/* 502 */       wave10BurstPool.addEntry(getPattern("thrower"), 1);
/* 503 */       WaveEntry wave10Burst = new WaveEntry(125000, 128000, 12, 500, wave10BurstPool, 35, 5);
/* 504 */       wave10Burst.addAlert("A large number of mobs are slipping through the nexus rift!", 0);
/* 505 */       entryList.add(wave10Burst);
/*     */ 
/* 507 */       FiniteSelectionPool wave10FinalePool = new FiniteSelectionPool();
/* 508 */       wave10FinalePool.addEntry(getPattern("zombie_t1_any"), 2);
/* 509 */       wave10FinalePool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 510 */       wave10FinalePool.addEntry(getPattern("skeleton_t1_any"), 1);
/* 511 */       wave10FinalePool.addEntry(getPattern("spider_t2_any"), 1);
/* 512 */       wave10FinalePool.addEntry(getPattern("spider_t1_any"), 2);
/* 513 */       WaveEntry wave10Finale = new WaveEntry(152000, 170000, 7, 500, wave10FinalePool);
/* 514 */       entryList.add(wave10Finale);
/* 515 */       return new Wave(172000, 60000, entryList);
/*     */     case 11:
/* 517 */       RandomSelectionPool wave11BasePool = new RandomSelectionPool();
/* 518 */       wave11BasePool.addEntry(getPattern("zombie_t1_any"), 1.5F);
/* 519 */       wave11BasePool.addEntry(getPattern("zombie_t2_any_basic"), 2.2F);
/* 520 */       wave11BasePool.addEntry(getPattern("zombie_t3_any"), 0.185F);
/* 521 */       wave11BasePool.addEntry(getPattern("zombie_t2_pigman"), 0.8F);
/* 522 */       wave11BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 523 */       wave11BasePool.addEntry(getPattern("thrower"), 0.1F);
/* 524 */       wave11BasePool.addEntry(getPattern("creeper_t1_basic"), 0.064F);
/* 525 */       WaveEntry wave11Base1 = new WaveEntry(0, 30000, 7, 2000, wave11BasePool, 45, 5);
/* 526 */       entryList.add(wave11Base1);
/*     */ 
/* 528 */       FiniteSelectionPool wave11SpecialPool = new FiniteSelectionPool();
/* 529 */       wave11SpecialPool.addEntry(getPattern("spider_t2_any"), 1);
/* 530 */       wave11SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 531 */       WaveEntry wave11Special = new WaveEntry(0, 90000, 3, 500, wave11SpecialPool);
/* 532 */       entryList.add(wave11Special);
/*     */ 
/* 534 */       RandomSelectionPool wave11BurstPool = new RandomSelectionPool();
/* 535 */       wave11BurstPool.addEntry(getPattern("pigengy_t1_any"), 1.0F);
/* 536 */       wave11BurstPool.addEntry(getPattern("zombie_t2_pigman"), 2.0F);
/* 537 */       wave11BurstPool.addEntry(getPattern("zombie_t2_any"), 3.0F);
/* 538 */       wave11BurstPool.addEntry(getPattern("zombie_t1_any"), 1.0F);
/* 539 */       wave11BurstPool.addEntry(getPattern("skeleton_t1_any"), 1.0F);
/* 540 */       wave11BurstPool.addEntry(getPattern("thrower"), 0.8F);
/* 541 */       wave11BurstPool.addEntry(getPattern("creeper_t1_basic"), 0.8F);
/* 542 */       WaveEntry wave11Burst = new WaveEntry(65000, 67000, 7, 500, wave11BurstPool, 25, 3);
/* 543 */       entryList.add(wave11Burst);
/*     */ 
/* 545 */       FiniteSelectionPool wave11Burst2Pool = new FiniteSelectionPool();
/* 546 */       wave11Burst2Pool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 547 */       wave11Burst2Pool.addEntry(getPattern("zombie_t1_any"), 3);
/* 548 */       wave11Burst2Pool.addEntry(getPattern("spider_t2_any"), 1);
/* 549 */       WaveEntry wave11Burst2 = new WaveEntry(95000, 97000, 6, 500, wave11Burst2Pool, 45, 2);
/* 550 */       entryList.add(wave11Burst2);
/*     */ 
/* 552 */       return new Wave(120000, 35000, entryList);
/*     */     }
/* 554 */     return null;
/*     */   }
/*     */ 
/*     */   private static Wave generateExtendedWave(int waveNumber)
/*     */   {
/* 560 */     float mobScale = (float)Math.pow(1.090000033378601D, waveNumber - 11);
/* 561 */     float timeScale = 1.0F + (waveNumber - 11) * 0.04F;
/* 562 */     ArrayList entryList = new ArrayList();
/* 563 */     RandomSelectionPool wave11BasePool = new RandomSelectionPool();
/* 564 */     wave11BasePool.addEntry(getPattern("zombie_t1_any"), 1.5F);
/* 565 */     wave11BasePool.addEntry(getPattern("zombie_t2_any_basic"), 2.2F);
/* 566 */     wave11BasePool.addEntry(getPattern("zombie_t2_pigman"), 0.8F);
/* 567 */     wave11BasePool.addEntry(getPattern("zombie_t3_any"), 0.26F);
/* 568 */     wave11BasePool.addEntry(getPattern("skeleton_t1_any"), 0.7F);
/* 569 */     wave11BasePool.addEntry(getPattern("thrower"), 0.18F);
/* 570 */     wave11BasePool.addEntry(getPattern("creeper_t1_basic"), 0.054F);
/* 571 */     WaveEntry wave11Base1 = new WaveEntry(0, (int)(timeScale * 30000.0F), (int)(mobScale * 7.0F), 2000, wave11BasePool, 45, 5);
/* 572 */     entryList.add(wave11Base1);
/*     */ 
/* 574 */     FiniteSelectionPool wave11SpecialPool = new FiniteSelectionPool();
/* 575 */     wave11SpecialPool.addEntry(getPattern("spider_t2_any"), 2);
/* 576 */     wave11SpecialPool.addEntry(getPattern("pigengy_t1_any"), 1);
/* 577 */     WaveEntry wave11Special = new WaveEntry(0, (int)(timeScale * 90000.0F), (int)(mobScale * 3.0F), 500, wave11SpecialPool);
/* 578 */     entryList.add(wave11Special);
/*     */ 
/* 580 */     RandomSelectionPool wave11BurstPool = new RandomSelectionPool();
/* 581 */     wave11BurstPool.addEntry(getPattern("zombie_t2_pigman"), 1.5F);
/* 582 */     wave11BurstPool.addEntry(getPattern("zombie_t2_any"), 1.5F);
/* 583 */     wave11BurstPool.addEntry(getPattern("spider_t2_any"), 1.0F);
/* 584 */     wave11BurstPool.addEntry(getPattern("zombie_t1_any"), 1.0F);
/* 585 */     wave11BurstPool.addEntry(getPattern("skeleton_t1_any"), 1.0F);
/* 586 */     wave11BurstPool.addEntry(getPattern("thrower"), 0.5F);
/* 587 */     wave11BurstPool.addEntry(getPattern("zombie_t3_any"), 0.5F);
/* 588 */     wave11BurstPool.addEntry(getPattern("creeper_t1_basic"), 0.42F);
/* 589 */     WaveEntry wave11Burst = new WaveEntry((int)(timeScale * 65000.0F), (int)(timeScale * 67000.0F), (int)(mobScale * 7.0F), 500, wave11BurstPool, 25, 3);
/* 590 */     entryList.add(wave11Burst);
/*     */ 
/* 592 */     FiniteSelectionPool wave11Burst2Pool = new FiniteSelectionPool();
/* 593 */     wave11Burst2Pool.addEntry(getPattern("zombie_t2_any_basic"), 2);
/* 594 */     wave11Burst2Pool.addEntry(getPattern("zombie_t1_any"), 3);
/* 595 */     wave11Burst2Pool.addEntry(getPattern("spider_t2_any"), 1);
/* 596 */     WaveEntry wave11Burst2 = new WaveEntry((int)(timeScale * 95000.0F), (int)(timeScale * 97000.0F), (int)(mobScale * 6.0F), 500, wave11Burst2Pool, 45, 2);
/* 597 */     entryList.add(wave11Burst2);
/*     */ 
/* 599 */     return new Wave((int)(timeScale * 120000.0F), (int)(timeScale * 35000.0F), entryList);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 604 */     EntityPattern zombieT1Any = new EntityPattern(IMEntityType.ZOMBIE);
/* 605 */     zombieT1Any.addTier(1, 1.0F);
/* 606 */     zombieT1Any.addFlavour(0, 3.0F);
/* 607 */     zombieT1Any.addFlavour(1, 1.0F);
/* 608 */     EntityPattern zombieT2Basic = new EntityPattern(IMEntityType.ZOMBIE);
/* 609 */     zombieT2Basic.addTier(2, 1.0F);
/* 610 */     zombieT2Basic.addFlavour(0, 2.0F);
/* 611 */     zombieT2Basic.addFlavour(1, 1.0F);
/* 612 */     zombieT2Basic.addFlavour(2, 0.4F);
/* 613 */     EntityPattern zombieT2Plain = new EntityPattern(IMEntityType.ZOMBIE);
/* 614 */     zombieT2Plain.addTier(2, 1.0F);
/* 615 */     zombieT2Plain.addFlavour(0, 1.0F);
/* 616 */     EntityPattern zombieT2Tar = new EntityPattern(IMEntityType.ZOMBIE);
/* 617 */     zombieT2Tar.addTier(2, 1.0F);
/* 618 */     zombieT2Tar.addFlavour(2, 1.0F);
/* 619 */     zombieT2Tar.addTexture(5, 1.0F);
/* 620 */     EntityPattern zombieT2Pigman = new EntityPattern(IMEntityType.ZOMBIE);
/* 621 */     zombieT2Pigman.addTier(2, 1.0F);
/* 622 */     zombieT2Pigman.addFlavour(3, 1.0F);
/* 623 */     zombieT2Pigman.addTexture(3, 1.0F);
/* 624 */     EntityPattern zombieT3Any = new EntityPattern(IMEntityType.ZOMBIE);
/* 625 */     zombieT3Any.addTier(3, 1.0F);
/* 626 */     zombieT3Any.addFlavour(0, 1.0F);
/* 627 */     EntityPattern spiderT1Any = new EntityPattern(IMEntityType.SPIDER);
/* 628 */     spiderT1Any.addTier(1, 1.0F);
/* 629 */     EntityPattern spiderT2Any = new EntityPattern(IMEntityType.SPIDER);
/* 630 */     spiderT2Any.addTier(2, 1.0F);
/* 631 */     spiderT2Any.addFlavour(0, 1.0F);
/* 632 */     spiderT2Any.addFlavour(1, 1.0F);
/* 633 */     EntityPattern pigEngyT1Any = new EntityPattern(IMEntityType.PIG_ENGINEER);
/* 634 */     pigEngyT1Any.addTier(1, 1.0F);
/* 635 */     EntityPattern skeletonT1Any = new EntityPattern(IMEntityType.SKELETON);
/* 636 */     skeletonT1Any.addTier(1, 1.0F);
/* 637 */     EntityPattern thrower = new EntityPattern(IMEntityType.THROWER);
/* 638 */     thrower.addTier(1, 1.0F);
/* 639 */     EntityPattern burrower = new EntityPattern(IMEntityType.BURROWER);
/* 640 */     burrower.addTier(1, 1.0F);
/* 641 */     EntityPattern creeper = new EntityPattern(IMEntityType.CREEPER);
/* 642 */     creeper.addTier(1, 1.0F);
/* 643 */     commonPatterns.put("zombie_t1_any", zombieT1Any);
/* 644 */     commonPatterns.put("zombie_t2_any_basic", zombieT2Basic);
/* 645 */     commonPatterns.put("zombie_t2_plain", zombieT2Plain);
/* 646 */     commonPatterns.put("zombie_t2_tar", zombieT2Tar);
/* 647 */     commonPatterns.put("zombie_t2_pigman", zombieT2Pigman);
/* 648 */     commonPatterns.put("zombie_t3_any", zombieT3Any);
/* 649 */     commonPatterns.put("spider_t1_any", spiderT1Any);
/* 650 */     commonPatterns.put("spider_t2_any", spiderT2Any);
/* 651 */     commonPatterns.put("pigengy_t1_any", pigEngyT1Any);
/* 652 */     commonPatterns.put("skeleton_t1_any", skeletonT1Any);
/* 653 */     commonPatterns.put("thrower", thrower);
/* 654 */     commonPatterns.put("burrower", burrower);
/* 655 */     commonPatterns.put("creeper_t1_basic", creeper);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.IMWaveBuilder
 * JD-Core Version:    0.6.2
 */