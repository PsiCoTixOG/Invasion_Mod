/*    */ package invmod.common;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SoundHandlerCommon
/*    */ {
/*    */   protected Map<Byte, String> sfxMapToString;
/*    */   protected Map<String, Byte> sfxMapToByte;
/*    */   protected boolean soundsInstalled;
/*    */   protected boolean soundEnabled;
/*    */ 
/*    */   public SoundHandlerCommon()
/*    */   {
/* 18 */     this.sfxMapToString = new HashMap();
/* 19 */     this.sfxMapToByte = new HashMap();
/* 20 */     this.soundsInstalled = false;
/* 21 */     this.soundEnabled = true;
/* 22 */     addNetworkSoundMapping("random.explode", (byte)0);
/* 23 */     addNetworkSoundMapping("invmod:scrape", (byte)1);
/* 24 */     addNetworkSoundMapping("invmod:chime", (byte)2);
/* 25 */     addNetworkSoundMapping("invmod:rumble", (byte)3);
/* 26 */     addNetworkSoundMapping("invmod:zap", (byte)4);
/* 27 */     addNetworkSoundMapping("invmod:fireball", (byte)5);
/* 28 */     addNetworkSoundMapping("invmod:bigzombie", (byte)6);
/* 29 */     addNetworkSoundMapping("invmod:egghatch", (byte)7);
/* 30 */     addNetworkSoundMapping("invmod:v_squawk", (byte)8);
/* 31 */     addNetworkSoundMapping("invmod:v_hiss", (byte)9);
/* 32 */     addNetworkSoundMapping("invmod:v_screech", (byte)10);
/* 33 */     addNetworkSoundMapping("invmod:v_longscreech", (byte)11);
/* 34 */     addNetworkSoundMapping("invmod:v_death", (byte)12);
/*    */   }
/*    */ 
/*    */   public void addNetworkSoundMapping(String soundName, byte id)
/*    */   {
/* 48 */     this.sfxMapToByte.put(soundName, Byte.valueOf(id));
/* 49 */     this.sfxMapToString.put(Byte.valueOf(id), soundName);
/*    */   }
/*    */ 
/*    */   public void playGlobalSFX(String s)
/*    */   {
/* 58 */     if (this.sfxMapToByte.containsKey(s))
/*    */     {
/* 60 */       mod_Invasion.sendInvasionPacketToAll(new byte[] { 0, ((Byte)this.sfxMapToByte.get(s)).byteValue() });
/*    */     }
/*    */   }
/*    */ 
/*    */   public void playSingleSFX(String s)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void playSingleSFX(byte id)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setSoundEnabled(boolean enabled)
/*    */   {
/* 80 */     this.soundEnabled = enabled;
/*    */   }
/*    */ 
/*    */   public boolean soundsInstalled()
/*    */   {
/* 85 */     return this.soundsInstalled;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.SoundHandlerCommon
 * JD-Core Version:    0.6.2
 */