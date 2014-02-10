/*    */ package invmod.common.nexus;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class Wave
/*    */ {
/*    */   private List<WaveEntry> entries;
/*    */   private int elapsed;
/*    */   private int waveTotalTime;
/*    */   private int waveBreakTime;
/*    */ 
/*    */   public Wave(int waveTotalTime, int waveBreakTime, List<WaveEntry> entries)
/*    */   {
/* 10 */     this.entries = entries;
/* 11 */     this.waveTotalTime = waveTotalTime;
/* 12 */     this.waveBreakTime = waveBreakTime;
/* 13 */     this.elapsed = 0;
/*    */   }
/*    */ 
/*    */   public void addEntry(WaveEntry entry)
/*    */   {
/* 18 */     this.entries.add(entry);
/*    */   }
/*    */ 
/*    */   public int doNextSpawns(int elapsedMillis, ISpawnerAccess spawner)
/*    */   {
/* 23 */     int numberOfSpawns = 0;
/* 24 */     this.elapsed += elapsedMillis;
/* 25 */     for (WaveEntry entry : this.entries)
/*    */     {
/* 27 */       if ((this.elapsed >= entry.getTimeBegin()) && (this.elapsed < entry.getTimeEnd()))
/*    */       {
/* 29 */         numberOfSpawns += entry.doNextSpawns(elapsedMillis, spawner);
/*    */       }
/*    */     }
/* 32 */     return numberOfSpawns;
/*    */   }
/*    */ 
/*    */   public int getTimeInWave()
/*    */   {
/* 37 */     return this.elapsed;
/*    */   }
/*    */ 
/*    */   public int getWaveTotalTime()
/*    */   {
/* 42 */     return this.waveTotalTime;
/*    */   }
/*    */ 
/*    */   public int getWaveBreakTime()
/*    */   {
/* 47 */     return this.waveBreakTime;
/*    */   }
/*    */ 
/*    */   public boolean isComplete()
/*    */   {
/* 52 */     return this.elapsed > this.waveTotalTime;
/*    */   }
/*    */ 
/*    */   public void resetWave()
/*    */   {
/* 57 */     this.elapsed = 0;
/* 58 */     for (WaveEntry entry : this.entries)
/*    */     {
/* 60 */       entry.resetToBeginning();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void setWaveToTime(int millis)
/*    */   {
/* 66 */     this.elapsed = millis;
/*    */   }
/*    */ 
/*    */   public int getTotalMobAmount()
/*    */   {
/* 71 */     int total = 0;
/* 72 */     for (WaveEntry entry : this.entries)
/*    */     {
/* 74 */       total += entry.getAmount();
/*    */     }
/* 76 */     return total;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.Wave
 * JD-Core Version:    0.6.2
 */