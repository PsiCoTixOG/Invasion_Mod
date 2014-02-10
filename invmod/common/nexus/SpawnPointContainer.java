/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import invmod.common.util.PolarAngle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Random;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class SpawnPointContainer
/*     */ {
/*     */   private EnumMap<SpawnType, ArrayList<SpawnPoint>> spawnPoints;
/*     */   private boolean sorted;
/*     */   private Random random;
/*     */   private PolarAngle angleDesired;
/*     */ 
/*     */   public SpawnPointContainer()
/*     */   {
/*  27 */     this.sorted = false;
/*  28 */     this.random = new Random();
/*  29 */     this.angleDesired = new PolarAngle(0);
/*  30 */     this.spawnPoints = new EnumMap(SpawnType.class);
/*  31 */     for (SpawnType type : SpawnType.values())
/*     */     {
/*  33 */       this.spawnPoints.put(type, new ArrayList());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addSpawnPointXZ(SpawnPoint spawnPoint)
/*     */   {
/*  43 */     boolean flag = false;
/*  44 */     ArrayList spawnList = (ArrayList)this.spawnPoints.get(spawnPoint.getType());
/*  45 */     for (int i = 0; i < spawnList.size(); i++)
/*     */     {
/*  47 */       SpawnPoint oldPoint = (SpawnPoint)spawnList.get(i);
/*  48 */       if ((oldPoint.getXCoord() == spawnPoint.getXCoord()) && (oldPoint.getZCoord() == spawnPoint.getZCoord()))
/*     */       {
/*  50 */         if (oldPoint.getYCoord() > spawnPoint.getYCoord())
/*     */         {
/*  52 */           spawnList.set(i, spawnPoint);
/*     */         }
/*  54 */         flag = true;
/*  55 */         break;
/*     */       }
/*     */     }
/*     */ 
/*  59 */     if (!flag)
/*     */     {
/*  61 */       spawnList.add(spawnPoint);
/*     */     }
/*  63 */     this.sorted = false;
/*     */   }
/*     */ 
/*     */   public SpawnPoint getRandomSpawnPoint(SpawnType spawnType)
/*     */   {
/*  71 */     ArrayList spawnList = (ArrayList)this.spawnPoints.get(spawnType);
/*  72 */     if (spawnList.size() == 0)
/*     */     {
/*  74 */       return null;
/*     */     }
/*  76 */     return (SpawnPoint)spawnList.get(this.random.nextInt(spawnList.size()));
/*     */   }
/*     */ 
/*     */   public SpawnPoint getRandomSpawnPoint(SpawnType spawnType, int minAngle, int maxAngle)
/*     */   {
/*  85 */     ArrayList spawnList = (ArrayList)this.spawnPoints.get(spawnType);
/*  86 */     if (spawnList.size() == 0)
/*     */     {
/*  88 */       return null;
/*     */     }
/*     */ 
/*  91 */     if (!this.sorted)
/*     */     {
/*  93 */       Collections.sort(spawnList);
/*  94 */       this.sorted = true;
/*     */     }
/*     */ 
/*  97 */     this.angleDesired.setAngle(minAngle);
/*  98 */     int start = Collections.binarySearch(spawnList, this.angleDesired);
/*  99 */     if (start < 0) {
/* 100 */       start = -start - 1;
/*     */     }
/* 102 */     this.angleDesired.setAngle(maxAngle);
/* 103 */     int end = Collections.binarySearch(spawnList, this.angleDesired);
/* 104 */     if (end < 0) {
/* 105 */       end = -end - 1;
/*     */     }
/* 107 */     if (end > start) {
/* 108 */       return (SpawnPoint)spawnList.get(start + this.random.nextInt(end - start));
/*     */     }
/* 110 */     if ((start > end) && (end > 0))
/*     */     {
/* 112 */       int r = start + this.random.nextInt(spawnList.size() + end - start);
/* 113 */       if (r >= spawnList.size()) {
/* 114 */         r -= spawnList.size();
/*     */       }
/* 116 */       return (SpawnPoint)spawnList.get(r);
/*     */     }
/* 118 */     return null;
/*     */   }
/*     */ 
/*     */   public int getNumberOfSpawnPoints(SpawnType type)
/*     */   {
/* 126 */     return ((ArrayList)this.spawnPoints.get(SpawnType.HUMANOID)).size();
/*     */   }
/*     */ 
/*     */   public int getNumberOfSpawnPoints(SpawnType spawnType, int minAngle, int maxAngle)
/*     */   {
/* 135 */     ArrayList spawnList = (ArrayList)this.spawnPoints.get(spawnType);
/* 136 */     if ((spawnList.size() == 0) || (maxAngle - minAngle >= 360))
/*     */     {
/* 138 */       return spawnList.size();
/*     */     }
/*     */ 
/* 141 */     if (!this.sorted)
/*     */     {
/* 143 */       Collections.sort(spawnList);
/* 144 */       this.sorted = true;
/*     */     }
/*     */ 
/* 147 */     this.angleDesired.setAngle(minAngle);
/* 148 */     int start = Collections.binarySearch(spawnList, this.angleDesired);
/* 149 */     if (start < 0) {
/* 150 */       start = -start - 1;
/*     */     }
/* 152 */     this.angleDesired.setAngle(maxAngle);
/* 153 */     int end = Collections.binarySearch(spawnList, this.angleDesired);
/* 154 */     if (end < 0) {
/* 155 */       end = -end - 1;
/*     */     }
/* 157 */     if (end > start) {
/* 158 */       return end - start;
/*     */     }
/* 160 */     if ((start > end) && (end > 0)) {
/* 161 */       return end + spawnList.size() - start;
/*     */     }
/* 163 */     return 0;
/*     */   }
/*     */ 
/*     */   public void pointDisplayTest(int blockID, ColorizerGrass world)
/*     */   {
/* 172 */     ArrayList points = (ArrayList)this.spawnPoints.get(SpawnType.HUMANOID);
/* 173 */     SpawnPoint point = null;
/* 174 */     for (int i = 0; i < points.size(); i++)
/*     */     {
/* 176 */       point = (SpawnPoint)points.get(i);
/* 177 */       world.c(point.getXCoord(), point.getYCoord(), point.getZCoord(), blockID);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.SpawnPointContainer
 * JD-Core Version:    0.6.2
 */