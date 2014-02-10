package invmod.common;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet103SetSlot;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketHandlerCommon implements IPacketHandler
{
  public void onPacketData(INetworkManager manager, Packet103SetSlot packet, Player player)
  {
  
  }

@Override
public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
{
	// TODO Auto-generated method stub
	
}
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.PacketHandlerCommon
 * JD-Core Version:    0.6.2
 */