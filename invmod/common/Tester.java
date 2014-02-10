/*     */ package invmod.common;
/*     */ 
/*     */ import invmod.common.nexus.DummyNexus;
/*     */ import invmod.common.nexus.IMWaveBuilder;
/*     */ import invmod.common.nexus.IMWaveSpawner;
/*     */ import invmod.common.nexus.SpawnPoint;
/*     */ import invmod.common.nexus.SpawnPointContainer;
/*     */ import invmod.common.nexus.SpawnType;
/*     */ import invmod.common.nexus.Wave;
/*     */ import invmod.common.nexus.WaveSpawnerException;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class Tester
/*     */ {
/*     */   Random rand;
/*     */ 
/*     */   public Tester()
/*     */   {
/*  21 */     this.rand = new Random();
/*     */   }
/*     */ 
/*     */   public void doWaveBuilderTest(float difficulty, float tierLevel, int lengthSeconds)
/*     */   {
/*  26 */     mod_Invasion.log("Doing wave builder test. Difficulty: " + difficulty + ", tier: " + tierLevel + ", length: " + lengthSeconds + " seconds");
/*  27 */     mod_Invasion.log("Generating dummy nexus and fake spawn points...");
/*  28 */     DummyNexus nexus = new DummyNexus();
/*  29 */     SpawnPointContainer spawnPoints = new SpawnPointContainer();
/*  30 */     for (int i = -170; i < -100; i += 3) {
/*  31 */       spawnPoints.addSpawnPointXZ(new SpawnPoint(i, 0, 0, i, SpawnType.HUMANOID));
/*     */     }
/*  33 */     for (int i = 90; i < 180; i += 3) {
/*  34 */       spawnPoints.addSpawnPointXZ(new SpawnPoint(i, 0, 0, i, SpawnType.HUMANOID));
/*     */     }
/*  36 */     mod_Invasion.log("Setting radius to 45");
/*  37 */     IMWaveSpawner spawner = new IMWaveSpawner(nexus, 45);
/*  38 */     spawner.giveSpawnPoints(spawnPoints);
/*  39 */     spawner.debugMode(true);
/*  40 */     spawner.setSpawnMode(false);
/*     */ 
/*  42 */     IMWaveBuilder waveBuilder = new IMWaveBuilder();
/*  43 */     Wave wave = waveBuilder.generateWave(difficulty, tierLevel, lengthSeconds);
/*     */ 
/*  45 */     int successfulSpawns = 0;
/*  46 */     int definedSpawns = 0;
/*     */     try
/*     */     {
/*  49 */       spawner.beginNextWave(wave);
/*  50 */       mod_Invasion.log("Starting wave.Wave duration: " + spawner.getWaveDuration());
/*  51 */       while (!spawner.isWaveComplete())
/*     */       {
/*  53 */         spawner.spawn(100);
/*     */       }
/*  55 */       mod_Invasion.log("Wave finished spawning. Wave rest time: " + spawner.getWaveRestTime());
/*  56 */       successfulSpawns += spawner.getSuccessfulSpawnsThisWave();
/*  57 */       definedSpawns += spawner.getTotalDefinedMobsThisWave();
/*     */     }
/*     */     catch (WaveSpawnerException e)
/*     */     {
/*  61 */       mod_Invasion.log(e.getMessage());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  65 */       mod_Invasion.log(e.getMessage());
/*  66 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  69 */     mod_Invasion.log("Successful spawns for wave: " + spawner.getSuccessfulSpawnsThisWave());
/*  70 */     mod_Invasion.log("Test finished. Total successful spawns: " + successfulSpawns + "  Total defined spawns: " + definedSpawns);
/*     */   }
/*     */ 
/*     */   public void doWaveSpawnerTest(int startWave, int endWave)
/*     */   {
/*  75 */     mod_Invasion.log("Doing wave spawner test. Start wave: " + startWave + "  End wave: " + endWave);
/*  76 */     mod_Invasion.log("Generating dummy nexus and fake spawn points...");
/*  77 */     DummyNexus nexus = new DummyNexus();
/*  78 */     SpawnPointContainer spawnPoints = new SpawnPointContainer();
/*  79 */     for (int i = -170; i < -100; i += 3) {
/*  80 */       spawnPoints.addSpawnPointXZ(new SpawnPoint(i, 0, 0, i, SpawnType.HUMANOID));
/*     */     }
/*  82 */     for (int i = 90; i < 180; i += 3) {
/*  83 */       spawnPoints.addSpawnPointXZ(new SpawnPoint(i, 0, 0, i, SpawnType.HUMANOID));
/*     */     }
/*  85 */     mod_Invasion.log("Setting radius to 45");
/*  86 */     IMWaveSpawner spawner = new IMWaveSpawner(nexus, 45);
/*  87 */     spawner.giveSpawnPoints(spawnPoints);
/*  88 */     spawner.debugMode(true);
/*  89 */     spawner.setSpawnMode(false);
/*     */ 
/*  91 */     int successfulSpawns = 0;
/*  92 */     int definedSpawns = 0;
/*  93 */     for (; startWave <= endWave; startWave++)
/*     */     {
/*     */       try
/*     */       {
/*  97 */         spawner.beginNextWave(startWave);
/*  98 */         mod_Invasion.log("Starting wave " + startWave + ". Wave duration: " + spawner.getWaveDuration());
/*  99 */         while (!spawner.isWaveComplete())
/*     */         {
/* 101 */           spawner.spawn(100);
/*     */         }
/* 103 */         mod_Invasion.log("Wave finished spawning. Wave rest time: " + spawner.getWaveRestTime());
/* 104 */         successfulSpawns += spawner.getSuccessfulSpawnsThisWave();
/* 105 */         definedSpawns += spawner.getTotalDefinedMobsThisWave();
/*     */       }
/*     */       catch (WaveSpawnerException e)
/*     */       {
/* 109 */         mod_Invasion.log(e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 113 */     mod_Invasion.log("Successful spawns last wave: " + spawner.getSuccessfulSpawnsThisWave());
/* 114 */     mod_Invasion.log("Test finished. Total successful spawns: " + successfulSpawns + "  Total defined spawns: " + definedSpawns);
/*     */   }
/*     */ 
/*     */   public void doSpawnPointSelectionTest()
/*     */   {
/* 119 */     mod_Invasion.log("Doing SpawnPointContainer test");
/* 120 */     mod_Invasion.log("Filling with spawn points...");
/* 121 */     SpawnPointContainer spawnPoints = new SpawnPointContainer();
/* 122 */     for (int i = -180; i < 180; i += this.rand.nextInt(3)) {
/* 123 */       spawnPoints.addSpawnPointXZ(new SpawnPoint(i, 0, 0, i, SpawnType.HUMANOID));
/*     */     }
/* 125 */     mod_Invasion.log(spawnPoints.getNumberOfSpawnPoints(SpawnType.HUMANOID) + " random points in container");
/*     */ 
/* 127 */     mod_Invasion.log("Cycling through ranges... format: min <= x < max");
/* 128 */     for (int i = -180; i < 180; i += 25)
/*     */     {
/* 130 */       int i2 = i + 40;
/* 131 */       if (i2 >= 180)
/* 132 */         i2 -= 360;
/* 133 */       mod_Invasion.log(i + " to " + i2);
/* 134 */       for (int j = 0; j < 4; j++)
/*     */       {
/* 136 */         SpawnPoint point = spawnPoints.getRandomSpawnPoint(SpawnType.HUMANOID, i, i2);
/* 137 */         if (point != null) {
/* 138 */           mod_Invasion.log(point.toString());
/*     */         }
/*     */       }
/*     */     }
/* 142 */     mod_Invasion.log("Beginning random stress test");
/*     */ 
/* 145 */     int count = 0;
/* 146 */     int count2 = 0;
/* 147 */     for (int i = 0; i < 1105000; i++)
/*     */     {
/* 149 */       int r = this.rand.nextInt(361) - 180;
/* 150 */       int r2 = this.rand.nextInt(361) - 180;
/* 151 */       for (int j = 0; j < 17; j++)
/*     */       {
/* 153 */         count++;
/* 154 */         SpawnPoint point = spawnPoints.getRandomSpawnPoint(SpawnType.HUMANOID, r, r2);
/* 155 */         if (point != null)
/*     */         {
/* 157 */           if (r < r2)
/*     */           {
/* 159 */             if ((point.getAngle() < r) || (point.getAngle() >= r2))
/*     */             {
/* 163 */               count2++;
/* 164 */               mod_Invasion.log(point.toString() + " with specified: " + r + ", " + r2);
/*     */             }
/*     */ 
/*     */           }
/* 169 */           else if ((point.getAngle() >= r) && (point.getAngle() < r2))
/*     */           {
/* 173 */             count2++;
/* 174 */             mod_Invasion.log(point.toString() + " with specified: " + r + ", " + r2);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 181 */     mod_Invasion.log("Tested " + count + " random spawn point retrievals. " + count2 + " results out of bounds.");
/*     */ 
/* 183 */     mod_Invasion.log("Finished test.");
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.Tester
 * JD-Core Version:    0.6.2
 */