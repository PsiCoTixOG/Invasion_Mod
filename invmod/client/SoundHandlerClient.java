/*    */ package invmod.client;
/*    */ 
/*    */ import cpw.mods.fml.client.FMLClientHandler;
/*    */ import invmod.common.SoundHandlerCommon;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.src.blk;
/*    */ import net.minecraftforge.client.event.sound.SoundLoadEvent;
/*    */ import net.minecraftforge.event.ForgeSubscribe;
/*    */ 
/*    */ public class SoundHandlerClient extends SoundHandlerCommon
/*    */ {
/*    */   public void playSingleSFX(String s)
/*    */   {
/* 14 */     FMLClientHandler.instance().getClient().v.a(s, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   public void playSingleSFX(byte id)
/*    */   {
/* 20 */     if ((this.soundsInstalled) && (this.soundEnabled) && (this.sfxMapToString.containsKey(Byte.valueOf(id))))
/*    */     {
/* 22 */       FMLClientHandler.instance().getClient().v.a((String)this.sfxMapToString.get(Byte.valueOf(id)), 1.0F, 1.0F);
/*    */     }
/*    */   }
/*    */ 
/*    */   @ForgeSubscribe
/*    */   public void onSoundLoad(SoundLoadEvent event)
/*    */   {
/* 29 */     String resourcePath = "invmod:";
/* 30 */     String[] soundNames = { "scrape1.ogg", "scrape2.ogg", "scrape3.ogg", "chime1.ogg", "rumble1.ogg", "zap1.ogg", "zap2.ogg", "zap3.ogg", "fireball1.ogg", "bigzombie1.ogg", "egghatch1.ogg", "egghatch2.ogg", "v_squawk1.ogg", "v_squawk2.ogg", "v_squawk3.ogg", "v_squawk4.ogg", "v_hiss1.ogg", "v_screech1.ogg", "v_screech2.ogg", "v_screech3.ogg", "v_longscreech1.ogg", "v_death1.ogg" };
/*    */ 
/* 35 */     for (String name : soundNames)
/*    */     {
/* 37 */       event.manager.a(resourcePath + name);
/*    */     }
/* 39 */     this.soundsInstalled = true;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.SoundHandlerClient
 * JD-Core Version:    0.6.2
 */