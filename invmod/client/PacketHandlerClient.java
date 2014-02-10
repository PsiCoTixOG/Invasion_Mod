/*    */ package invmod.client;
/*    */ 
/*    */ import cpw.mods.fml.common.network.Player;
/*    */ import invmod.common.PacketHandlerCommon;
/*    */ import invmod.common.mod_Invasion;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.network.packet.Packet103SetSlot;
/*    */ 
/*    */ public class PacketHandlerClient extends PacketHandlerCommon
/*    */ {
/*    */   public void onPacketData(NBTBase manager, Packet103SetSlot packet, Player player)
/*    */   {
/* 20 */     DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.myItemStack));
/*    */     try
/*    */     {
/* 23 */       byte type = dataStream.readByte();
/* 24 */       if (type == 0)
/*    */       {
/* 26 */         byte id = dataStream.readByte();
/* 27 */         mod_Invasion.playSingleSFX(id);
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 32 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.PacketHandlerClient
 * JD-Core Version:    0.6.2
 */