/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.util.ISelect;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class WaveEntry
/*     */ {
/*     */   private int timeBegin;
/*     */   private int timeEnd;
/*     */   private int amount;
/*     */   private int granularity;
/*     */   private int amountQueued;
/*     */   private int elapsed;
/*     */   private int toNextSpawn;
/*     */   private int minAngle;
/*     */   private int maxAngle;
/*     */   private int minPointsInRange;
/*     */   private int nextAlert;
/*     */   private ISelect<IEntityIMPattern> mobPool;
/*     */   private List<EntityConstruct> spawnList;
/*     */   private Map<Integer, String> alerts;
/*     */ 
/*     */   public WaveEntry(int timeBegin, int timeEnd, int amount, int granularity, ISelect<IEntityIMPattern> mobPool)
/*     */   {
/*  25 */     this(timeBegin, timeEnd, amount, granularity, mobPool, -180, 180, 1);
/*     */   }
/*     */ 
/*     */   public WaveEntry(int timeBegin, int timeEnd, int amount, int granularity, ISelect<IEntityIMPattern> mobPool, int angleRange, int minPointsInRange)
/*     */   {
/*  30 */     this(timeBegin, timeEnd, amount, granularity, mobPool, 0, 0, minPointsInRange);
/*  31 */     this.minAngle = (new Random().nextInt(360) - 180);
/*  32 */     this.maxAngle = (this.minAngle + angleRange);
/*  33 */     while (this.maxAngle > 180)
/*  34 */       this.maxAngle -= 360;
/*     */   }
/*     */ 
/*     */   public WaveEntry(int timeBegin, int timeEnd, int amount, int granularity, ISelect<IEntityIMPattern> mobPool, int minAngle, int maxAngle, int minPointsInRange)
/*     */   {
/*  39 */     this.spawnList = new ArrayList();
/*  40 */     this.alerts = new HashMap();
/*  41 */     this.timeBegin = timeBegin;
/*  42 */     this.timeEnd = timeEnd;
/*  43 */     this.amount = amount;
/*  44 */     this.granularity = granularity;
/*  45 */     this.mobPool = mobPool;
/*  46 */     this.minAngle = minAngle;
/*  47 */     this.maxAngle = maxAngle;
/*  48 */     this.minPointsInRange = minPointsInRange;
/*  49 */     this.amountQueued = 0;
/*  50 */     this.elapsed = 0;
/*  51 */     this.toNextSpawn = 0;
/*  52 */     this.nextAlert = 2147483647;
/*     */   }
/*     */ 
/*     */   public int doNextSpawns(int elapsedMillis, ISpawnerAccess spawner)
/*     */   {
/*  62 */     this.toNextSpawn -= elapsedMillis;
/*  63 */     if (this.nextAlert <= this.elapsed - this.toNextSpawn)
/*     */     {
/*  65 */       sendNextAlert(spawner);
/*     */     }
/*     */ 
/*  68 */     if (this.toNextSpawn <= 0)
/*     */     {
/*  70 */       this.elapsed += this.granularity;
/*  71 */       this.toNextSpawn += this.granularity;
/*  72 */       if (this.toNextSpawn < 0)
/*     */       {
/*  74 */         this.elapsed -= this.toNextSpawn;
/*  75 */         this.toNextSpawn = 0;
/*     */       }
/*     */ 
/*  78 */       int amountToSpawn = Math.round(this.amount * this.elapsed / (this.timeEnd - this.timeBegin)) - this.amountQueued;
/*  79 */       if (amountToSpawn > 0)
/*     */       {
/*  81 */         if (amountToSpawn + this.amountQueued > this.amount) {
/*  82 */           amountToSpawn = this.amount - this.amountQueued;
/*     */         }
/*  84 */         while (amountToSpawn > 0)
/*     */         {
/*  86 */           IEntityIMPattern pattern = (IEntityIMPattern)this.mobPool.selectNext();
/*  87 */           if (pattern != null)
/*     */           {
/*  89 */             EntityConstruct mobConstruct = pattern.generateEntityConstruct(this.minAngle, this.maxAngle);
/*  90 */             if (mobConstruct != null)
/*     */             {
/*  92 */               amountToSpawn--;
/*  93 */               this.amountQueued += 1;
/*  94 */               this.spawnList.add(mobConstruct);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/*  99 */             mod_Invasion.log("A selection pool in wave entry " + toString() + " returned empty");
/* 100 */             mod_Invasion.log("Pool: " + this.mobPool.toString());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 106 */     if (this.spawnList.size() > 0)
/*     */     {
/* 108 */       int numberOfSpawns = 0;
/* 109 */       if (spawner.getNumberOfPointsInRange(this.minAngle, this.maxAngle, SpawnType.HUMANOID) >= this.minPointsInRange)
/*     */       {
/* 111 */         for (int i = this.spawnList.size() - 1; i >= 0; i--)
/*     */         {
/* 113 */           if (spawner.attemptSpawn((EntityConstruct)this.spawnList.get(i), this.minAngle, this.maxAngle))
/*     */           {
/* 115 */             numberOfSpawns++;
/* 116 */             this.spawnList.remove(i);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 122 */         reviseSpawnAngles(spawner);
/*     */       }
/* 124 */       return numberOfSpawns;
/*     */     }
/* 126 */     return 0;
/*     */   }
/*     */ 
/*     */   public void resetToBeginning()
/*     */   {
/* 134 */     this.elapsed = 0;
/* 135 */     this.amountQueued = 0;
/* 136 */     this.mobPool.reset();
/*     */   }
/*     */ 
/*     */   public void setToTime(int millis)
/*     */   {
/* 145 */     this.elapsed = millis;
/*     */   }
/*     */ 
/*     */   public int getTimeBegin()
/*     */   {
/* 153 */     return this.timeBegin;
/*     */   }
/*     */ 
/*     */   public int getTimeEnd()
/*     */   {
/* 161 */     return this.timeEnd;
/*     */   }
/*     */ 
/*     */   public int getAmount()
/*     */   {
/* 169 */     return this.amount;
/*     */   }
/*     */ 
/*     */   public int getGranularity()
/*     */   {
/* 177 */     return this.granularity;
/*     */   }
/*     */ 
/*     */   public void addAlert(String message, int timeElapsed)
/*     */   {
/* 182 */     this.alerts.put(Integer.valueOf(timeElapsed), message);
/* 183 */     if (timeElapsed < this.nextAlert)
/* 184 */       this.nextAlert = timeElapsed;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 190 */     return "WaveEntry@" + Integer.toHexString(hashCode()) + "#time=" + this.timeBegin + "-" + this.timeEnd + "#amount=" + this.amount;
/*     */   }
/*     */ 
/*     */   private void sendNextAlert(ISpawnerAccess spawner)
/*     */   {
/* 195 */     String message = (String)this.alerts.remove(Integer.valueOf(this.nextAlert));
/* 196 */     if (message != null) {
/* 197 */       spawner.sendSpawnAlert(message);
/*     */     }
/* 199 */     this.nextAlert = 2147483647;
/* 200 */     if (this.alerts.size() > 0)
/*     */     {
/* 202 */       for (Integer key : this.alerts.keySet())
/*     */       {
/* 204 */         if (key.intValue() < this.nextAlert)
/* 205 */           this.nextAlert = key.intValue();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void reviseSpawnAngles(ISpawnerAccess spawner)
/*     */   {
/* 212 */     int angleRange = this.maxAngle - this.minAngle;
/* 213 */     while (angleRange < 0)
/* 214 */       angleRange += 360;
/* 215 */     if (angleRange == 0) {
/* 216 */       angleRange = 360;
/*     */     }
/* 218 */     List validAngles = new ArrayList();
/*     */ 
/* 220 */     for (int angle = -180; angle < 180; angle += angleRange)
/*     */     {
/* 222 */       int nextAngle = angle + angleRange;
/* 223 */       if (nextAngle >= 180)
/* 224 */         nextAngle -= 360;
/* 225 */       if (spawner.getNumberOfPointsInRange(angle, nextAngle, SpawnType.HUMANOID) >= this.minPointsInRange) {
/* 226 */         validAngles.add(Integer.valueOf(angle));
/*     */       }
/*     */     }
/* 229 */     if (validAngles.size() > 0)
/*     */     {
/* 231 */       this.minAngle = ((Integer)validAngles.get(new Random().nextInt(validAngles.size()))).intValue();
/* 232 */       this.maxAngle = (this.minAngle + angleRange);
/* 233 */       while (this.maxAngle >= 180) {
/* 234 */         this.maxAngle -= 360;
/*     */       }
/*     */     }
/*     */ 
/* 238 */     if (this.minPointsInRange > 1)
/*     */     {
/* 240 */       mod_Invasion.log("Can't find a direction with enough spawn points: " + this.minPointsInRange + ". Lowering requirement.");
/*     */ 
/* 242 */       this.minPointsInRange = 1;
/*     */     }
/* 244 */     else if (this.maxAngle - this.minAngle < 360)
/*     */     {
/* 246 */       mod_Invasion.log("Can't find a direction with enough spawn points: " + this.minPointsInRange + ". Switching to 360 degree mode for this entry");
/*     */ 
/* 249 */       this.minAngle = -180;
/* 250 */       this.maxAngle = 180;
/*     */     }
/*     */     else
/*     */     {
/* 254 */       mod_Invasion.log("Wave entry cannot find a single spawn point");
/* 255 */       spawner.noSpawnPointNotice();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.WaveEntry
 * JD-Core Version:    0.6.2
 */