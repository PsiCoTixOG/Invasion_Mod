package invmod.common;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class PacketHandlerCommon implements IPacketHandler
{
  public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
  {
  }
}