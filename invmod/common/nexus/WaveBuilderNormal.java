/*    */ package invmod.common.nexus;
/*    */ 
/*    */ public class WaveBuilderNormal
/*    */   implements IWaveSource
/*    */ {
/*    */   public Wave getWave()
/*    */   {
/* 10 */     int difficulty = 0;
/* 11 */     int lengthSeconds = 0;
/*    */ 
/* 13 */     float basicMobsPerSecond = 0.12F * difficulty;
/* 14 */     int numberOfGroups = 7;
/* 15 */     int numberOfBigGroups = 1;
/* 16 */     float proportionInGroups = 0.5F;
/* 17 */     int mobsPerGroup = Math.round(proportionInGroups * basicMobsPerSecond * lengthSeconds / (numberOfGroups + numberOfBigGroups * 2));
/* 18 */     int mobsPerBigGroup = mobsPerGroup * 2;
/* 19 */     int remainingMobs = (int)(basicMobsPerSecond * lengthSeconds) - mobsPerGroup * numberOfGroups - mobsPerBigGroup * numberOfBigGroups;
/* 20 */     int mobsPerSteady = Math.round(0.7F * remainingMobs / numberOfGroups);
/* 21 */     int extraMobsForFinale = Math.round(0.3F * remainingMobs);
/* 22 */     int extraMobsForCleanup = (int)(basicMobsPerSecond * lengthSeconds * 0.2F);
/* 23 */     float timeForGroups = 0.5F;
/* 24 */     int groupTimeInterval = (int)(lengthSeconds * 1000 * timeForGroups / (numberOfGroups + numberOfBigGroups * 3));
/* 25 */     int steadyTimeInterval = (int)(lengthSeconds * 1000 * (1.0F - timeForGroups) / numberOfGroups);
/*    */ 
/* 29 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.WaveBuilderNormal
 * JD-Core Version:    0.6.2
 */