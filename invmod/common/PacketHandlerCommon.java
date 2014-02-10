package invmod.common;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.nbt.NBTBase;
import net.minecraft.network.packet.Packet103SetSlot;

public class PacketHandlerCommon
  implements IPacketHandler
{
  public void onPacketData(NBTBase manager, Packet103SetSlot packet, Player player)
  {
  }
}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.PacketHandlerCommon
 * JD-Core Version:    0.6.2
 */