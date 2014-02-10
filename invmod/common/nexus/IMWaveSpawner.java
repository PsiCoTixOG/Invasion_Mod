/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import invmod.common.entity.EntityIMLiving;
/*     */ import invmod.common.entity.EntityIMZombie;
/*     */ import invmod.common.mod_Invasion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.src.ns;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class IMWaveSpawner
/*     */   implements ISpawnerAccess
/*     */ {
/*  22 */   private final int MAX_SPAWN_TRIES = 20;
/*  23 */   private final int NORMAL_SPAWN_HEIGHT = 30;
/*  24 */   private final int MIN_SPAWN_POINTS_TO_KEEP = 15;
/*  25 */   private final int MIN_SPAWN_POINTS_TO_KEEP_BELOW_HEIGHT_CUTOFF = 20;
/*  26 */   private final int HEIGHT_CUTOFF = 35;
/*  27 */   private final float SPAWN_POINT_CULL_RATE = 0.3F;
/*     */   private SpawnPointContainer spawnPointContainer;
/*     */   private INexusAccess nexus;
/*     */   private MobBuilder mobBuilder;
/*     */   private Random rand;
/*     */   private Wave currentWave;
/*     */   private boolean active;
/*     */   private boolean waveComplete;
/*     */   private boolean spawnMode;
/*     */   private boolean debugMode;
/*     */   private int spawnRadius;
/*     */   private int currentWaveNumber;
/*     */   private int successfulSpawns;
/*     */   private long elapsed;
/*     */ 
/*     */   public IMWaveSpawner(INexusAccess tileEntityNexus, int radius)
/*     */   {
/*  45 */     this.nexus = tileEntityNexus;
/*  46 */     this.active = false;
/*  47 */     this.waveComplete = false;
/*  48 */     this.spawnMode = true;
/*  49 */     this.debugMode = false;
/*  50 */     this.spawnRadius = radius;
/*  51 */     this.currentWaveNumber = 1;
/*  52 */     this.elapsed = 0L;
/*  53 */     this.successfulSpawns = 0;
/*  54 */     this.rand = new Random();
/*  55 */     this.spawnPointContainer = new SpawnPointContainer();
/*  56 */     this.mobBuilder = new MobBuilder();
/*     */   }
/*     */ 
/*     */   public long getElapsedTime()
/*     */   {
/*  61 */     return this.elapsed;
/*     */   }
/*     */ 
/*     */   public void setRadius(int radius)
/*     */   {
/*  66 */     if (radius > 8)
/*     */     {
/*  68 */       this.spawnRadius = radius;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void beginNextWave(int waveNumber)
/*     */     throws WaveSpawnerException
/*     */   {
/*  77 */     beginNextWave(IMWaveBuilder.generateMainInvasionWave(waveNumber));
/*     */   }
/*     */ 
/*     */   public void beginNextWave(Wave wave) throws WaveSpawnerException
/*     */   {
/*  82 */     if (!this.active)
/*     */     {
/*  84 */       generateSpawnPoints();
/*     */     }
/*  90 */     else if (this.debugMode) {
/*  91 */       mod_Invasion.log("Successful spawns last wave: " + this.successfulSpawns);
/*     */     }
/*     */ 
/*  95 */     wave.resetWave();
/*  96 */     this.waveComplete = false;
/*  97 */     this.active = true;
/*  98 */     this.currentWave = wave;
/*  99 */     this.elapsed = 0L;
/* 100 */     this.successfulSpawns = 0;
/*     */ 
/* 102 */     if (this.debugMode)
/* 103 */       mod_Invasion.log("Defined mobs this wave: " + getTotalDefinedMobsThisWave());
/*     */   }
/*     */ 
/*     */   public void spawn(int elapsedMillis)
/*     */     throws WaveSpawnerException
/*     */   {
/* 111 */     this.elapsed += elapsedMillis;
/* 112 */     if ((this.waveComplete) || (!this.active)) {
/* 113 */       return;
/*     */     }
/*     */ 
/* 116 */     if (this.spawnPointContainer.getNumberOfSpawnPoints(SpawnType.HUMANOID) < 10)
/*     */     {
/* 118 */       generateSpawnPoints();
/* 119 */       if (this.spawnPointContainer.getNumberOfSpawnPoints(SpawnType.HUMANOID) < 10) {
/* 120 */         throw new WaveSpawnerException("Not enough spawn points for type " + SpawnType.HUMANOID);
/*     */       }
/*     */     }
/* 123 */     this.currentWave.doNextSpawns(elapsedMillis, this);
/*     */ 
/* 125 */     if (this.currentWave.isComplete())
/* 126 */       this.waveComplete = true;
/*     */   }
/*     */ 
/*     */   public int resumeFromState(Wave wave, long elapsedTime, int radius)
/*     */     throws WaveSpawnerException
/*     */   {
/* 134 */     this.spawnRadius = radius;
/* 135 */     stop();
/* 136 */     beginNextWave(wave);
/*     */ 
/* 138 */     setSpawnMode(false);
/* 139 */     int numberOfSpawns = 0;
/* 140 */     for (; this.elapsed < elapsedTime; this.elapsed += 100L)
/*     */     {
/* 142 */       numberOfSpawns += this.currentWave.doNextSpawns(100, this);
/*     */     }
/* 144 */     setSpawnMode(true);
/* 145 */     return numberOfSpawns;
/*     */   }
/*     */ 
/*     */   public void resumeFromState(int waveNumber, long elapsedTime, int radius)
/*     */     throws WaveSpawnerException
/*     */   {
/* 153 */     this.spawnRadius = radius;
/* 154 */     stop();
/* 155 */     beginNextWave(waveNumber);
/*     */ 
/* 157 */     setSpawnMode(false);
/* 158 */     for (; this.elapsed < elapsedTime; this.elapsed += 100L)
/*     */     {
/* 160 */       this.currentWave.doNextSpawns(100, this);
/*     */     }
/* 162 */     setSpawnMode(true);
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 170 */     this.active = false;
/*     */   }
/*     */ 
/*     */   public boolean isActive()
/*     */   {
/* 178 */     return this.active;
/*     */   }
/*     */ 
/*     */   public boolean isReady()
/*     */   {
/* 183 */     if ((!this.active) && (this.nexus != null) && (this.nexus.getWorld() != null))
/*     */     {
/* 185 */       return true;
/*     */     }
/*     */ 
/* 189 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isWaveComplete()
/*     */   {
/* 198 */     return this.waveComplete;
/*     */   }
/*     */ 
/*     */   public int getWaveDuration()
/*     */   {
/* 206 */     return this.currentWave.getWaveTotalTime();
/*     */   }
/*     */ 
/*     */   public int getWaveRestTime()
/*     */   {
/* 214 */     return this.currentWave.getWaveBreakTime();
/*     */   }
/*     */ 
/*     */   public int getSuccessfulSpawnsThisWave()
/*     */   {
/* 222 */     return this.successfulSpawns;
/*     */   }
/*     */ 
/*     */   public int getTotalDefinedMobsThisWave()
/*     */   {
/* 230 */     return this.currentWave.getTotalMobAmount();
/*     */   }
/*     */ 
/*     */   public void askForRespawn(EntityIMLiving entity)
/*     */   {
/* 238 */     if (this.spawnPointContainer.getNumberOfSpawnPoints(SpawnType.HUMANOID) > 10)
/*     */     {
/* 240 */       SpawnPoint spawnPoint = this.spawnPointContainer.getRandomSpawnPoint(SpawnType.HUMANOID);
/* 241 */       entity.setLocationAndAngles(spawnPoint.getXCoord(), spawnPoint.getYCoord(), spawnPoint.getZCoord(), 0.0F, 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void sendSpawnAlert(String message)
/*     */   {
/* 248 */     if (this.debugMode) {
/* 249 */       mod_Invasion.log(message);
/*     */     }
/* 251 */     mod_Invasion.broadcastToAll(message);
/*     */   }
/*     */ 
/*     */   public void noSpawnPointNotice()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void debugMode(boolean isOn)
/*     */   {
/* 265 */     this.debugMode = isOn;
/*     */   }
/*     */ 
/*     */   public int getNumberOfPointsInRange(int minAngle, int maxAngle, SpawnType type)
/*     */   {
/* 274 */     return this.spawnPointContainer.getNumberOfSpawnPoints(type, minAngle, maxAngle);
/*     */   }
/*     */ 
/*     */   public void setSpawnMode(boolean flag)
/*     */   {
/* 283 */     this.spawnMode = flag;
/*     */   }
/*     */ 
/*     */   public void giveSpawnPoints(SpawnPointContainer spawnPointContainer)
/*     */   {
/* 291 */     this.spawnPointContainer = spawnPointContainer;
/*     */   }
/*     */ 
/*     */   public boolean attemptSpawn(EntityConstruct mobConstruct, int minAngle, int maxAngle)
/*     */   {
/* 301 */     if (this.nexus.getWorld() == null)
/*     */     {
/* 303 */       if (this.spawnMode) {
/* 304 */         return false;
/*     */       }
/*     */     }
/* 307 */     EntityIMLiving mob = this.mobBuilder.createMobFromConstruct(mobConstruct, this.nexus.getWorld(), this.nexus);
/* 308 */     if (mob == null)
/*     */     {
/* 310 */       mod_Invasion.log("Invalid entity construct");
/* 311 */       return false;
/*     */     }
/*     */ 
/* 314 */     int spawnTries = getNumberOfPointsInRange(minAngle, maxAngle, SpawnType.HUMANOID);
/* 315 */     if (spawnTries > 20) {
/* 316 */       spawnTries = 20;
/*     */     }
/* 318 */     for (int j = 0; j < spawnTries; j++)
/*     */     {
/*     */       SpawnPoint spawnPoint;
/*     */       SpawnPoint spawnPoint;
/* 321 */       if (maxAngle - minAngle >= 360)
/* 322 */         spawnPoint = this.spawnPointContainer.getRandomSpawnPoint(SpawnType.HUMANOID);
/*     */       else {
/* 324 */         spawnPoint = this.spawnPointContainer.getRandomSpawnPoint(SpawnType.HUMANOID, minAngle, maxAngle);
/*     */       }
/* 326 */       if (spawnPoint == null) {
/* 327 */         return false;
/*     */       }
/* 329 */       if (!this.spawnMode)
/*     */       {
/* 331 */         this.successfulSpawns += 1;
/* 332 */         if (this.debugMode)
/*     */         {
/* 334 */           mod_Invasion.log("[Spawn] Time: " + this.currentWave.getTimeInWave() / 1000 + "  Type: " + mob.toString() + "  Coords: " + spawnPoint.getXCoord() + ", " + spawnPoint.getYCoord() + ", " + spawnPoint.getZCoord() + "  θ" + spawnPoint.getAngle() + "  Specified: " + minAngle + "," + maxAngle);
/*     */         }
/*     */ 
/* 338 */         return true;
/*     */       }
/*     */ 
/* 341 */       mob.setLocationAndAngles(spawnPoint.getXCoord(), spawnPoint.getYCoord(), spawnPoint.getZCoord(), 0.0F, 0.0F);
/* 342 */       if (mob.getCanSpawnHere())
/*     */       {
/* 344 */         this.successfulSpawns += 1;
/* 345 */         this.nexus.getWorld().d(mob);
/* 346 */         if (this.debugMode)
/*     */         {
/* 348 */           mod_Invasion.log("[Spawn] Time: " + this.currentWave.getTimeInWave() / 1000 + "  Type: " + mob.toString() + "  Coords: " + mob.posX + ", " + mob.posY + ", " + mob.posZ + "  θ" + spawnPoint.getAngle() + "  Specified: " + minAngle + "," + maxAngle);
/*     */         }
/*     */ 
/* 352 */         return true;
/*     */       }
/*     */     }
/* 355 */     mod_Invasion.log("Could not find valid spawn for '" + ns.b(mob) + "' after " + spawnTries + " tries");
/* 356 */     return false;
/*     */   }
/*     */ 
/*     */   private void generateSpawnPoints()
/*     */   {
/* 361 */     if (this.nexus.getWorld() == null) {
/* 362 */       return;
/*     */     }
/* 364 */     EntityIMZombie zombie = new EntityIMZombie(this.nexus.getWorld(), this.nexus);
/* 365 */     List spawnPoints = new ArrayList();
/* 366 */     int x = this.nexus.getXCoord();
/* 367 */     int y = this.nexus.getYCoord();
/* 368 */     int z = this.nexus.getZCoord();
/* 369 */     for (int vertical = 0; vertical < 128; vertical = vertical > 0 ? vertical * -1 : vertical * -1 + 1)
/*     */     {
/* 371 */       if (y + vertical <= 252)
/*     */       {
/* 374 */         for (int i = 0; i <= this.spawnRadius * 0.7D + 1.0D; i++)
/*     */         {
/* 377 */           int j = (int)Math.round(this.spawnRadius * Math.cos(Math.asin(i / this.spawnRadius)));
/*     */ 
/* 380 */           addValidSpawn(zombie, spawnPoints, x + i, y + vertical, z + j);
/* 381 */           addValidSpawn(zombie, spawnPoints, x + j, y + vertical, z + i);
/*     */ 
/* 384 */           addValidSpawn(zombie, spawnPoints, x + i, y + vertical, z - j);
/* 385 */           addValidSpawn(zombie, spawnPoints, x + j, y + vertical, z - i);
/*     */ 
/* 388 */           addValidSpawn(zombie, spawnPoints, x - i, y + vertical, z + j);
/* 389 */           addValidSpawn(zombie, spawnPoints, x - j, y + vertical, z + i);
/*     */ 
/* 392 */           addValidSpawn(zombie, spawnPoints, x - i, y + vertical, z - j);
/* 393 */           addValidSpawn(zombie, spawnPoints, x - j, y + vertical, z - i);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 402 */     if (spawnPoints.size() > 15)
/*     */     {
/* 405 */       int amountToRemove = (int)((spawnPoints.size() - 15) * 0.3F);
/* 406 */       for (int i = spawnPoints.size() - 1; 
/* 407 */         i >= spawnPoints.size() - amountToRemove; i--)
/*     */       {
/* 409 */         if (Math.abs(((SpawnPoint)spawnPoints.get(i)).getYCoord() - y) < 30)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/* 414 */       for (; i >= 20; i--)
/*     */       {
/* 416 */         SpawnPoint spawnPoint = (SpawnPoint)spawnPoints.get(i);
/* 417 */         if (spawnPoint.getYCoord() - y <= 35)
/*     */         {
/* 419 */           this.spawnPointContainer.addSpawnPointXZ(spawnPoint);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 424 */       for (; i >= 0; i--)
/*     */       {
/* 426 */         this.spawnPointContainer.addSpawnPointXZ((SpawnPoint)spawnPoints.get(i));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 435 */     mod_Invasion.log("Num. Spawn Points: " + Integer.toString(this.spawnPointContainer.getNumberOfSpawnPoints(SpawnType.HUMANOID)));
/*     */   }
/*     */ 
/*     */   private void addValidSpawn(EntityIMLiving entity, List<SpawnPoint> spawnPoints, int x, int y, int z)
/*     */   {
/* 443 */     entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
/* 444 */     if (entity.getCanSpawnHere())
/*     */     {
/* 447 */       int angle = (int)(Math.atan2(this.nexus.getZCoord() - z, this.nexus.getXCoord() - x) * 180.0D / 3.141592653589793D);
/* 448 */       spawnPoints.add(new SpawnPoint(x, y, z, angle, SpawnType.HUMANOID));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkAddSpawn(EntityIMLiving entity, int x, int y, int z)
/*     */   {
/* 454 */     entity.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
/* 455 */     if (entity.getCanSpawnHere())
/*     */     {
/* 458 */       int angle = (int)(Math.atan2(this.nexus.getZCoord() - z, this.nexus.getXCoord() - x) * 180.0D / 3.141592653589793D);
/* 459 */       this.spawnPointContainer.addSpawnPointXZ(new SpawnPoint(x, y, z, angle, SpawnType.HUMANOID));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.IMWaveSpawner
 * JD-Core Version:    0.6.2
 */