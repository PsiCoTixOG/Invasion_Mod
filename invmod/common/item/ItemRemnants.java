package invmod.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRemnants extends ItemIM
{
  public static final String[] remnantNames = { "Inert Remnants", "Small Remnants", "ff Remanants", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid" };

  public ItemRemnants(int i)
  {
    super(i);
    setHasSubtypes(true);
    setMaxDamage(0);
  }

  public String getUnlocalizedName(ItemStack itemstack)
  {
    return remnantNames[itemstack.getItemDamage()].toString();
  }

  public boolean onItemUseFirst(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
  {
    return false;
  }

  @SideOnly(Side.CLIENT)
  public void getSubItems(int id, CreativeTabs tab, List dest)
  {
    dest.add(new ItemStack(id, 1, 1));
  }
}