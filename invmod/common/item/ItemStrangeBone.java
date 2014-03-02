package invmod.common.item;

import invmod.common.entity.EntityIMWolf;
import invmod.common.mod_Invasion;
import invmod.common.nexus.TileEntityNexus;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemStrangeBone extends ItemIM
{
  public ItemStrangeBone(int i)
  {
    super(i);
    this.maxStackSize = 1;
  }

  public int getDamage(ItemStack stack)
  {
    return 0;
  }

  public boolean func_111207_a(ItemStack itemstack, EntityPlayer player, EntityLivingBase targetEntity)
  {
    if ((!targetEntity.worldObj.isRemote) && ((targetEntity instanceof EntityWolf)) && (!(targetEntity instanceof EntityIMWolf)))
    {
      EntityWolf wolf = (EntityWolf)targetEntity;
      if (wolf.isTamed())
      {
        TileEntityNexus nexus = null;
        int x = MathHelper.floor_double(wolf.posX);
        int y = MathHelper.floor_double(wolf.posY);
        int z = MathHelper.floor_double(wolf.posZ);
        for (int i = -7; i < 8; i++)
        {
          for (int j = -4; j < 5; j++)
          {
            for (int k = -7; k < 8; k++)
            {
              if (wolf.worldObj.getBlockId(x + i, y + j, z + k) == mod_Invasion.blockNexus.blockID)
              {
                nexus = (TileEntityNexus)wolf.worldObj.getBlockTileEntity(x + i, y + j, z + k);
                break;
              }
            }
          }
        }

        if (nexus != null)
        {
          EntityIMWolf newWolf = new EntityIMWolf(wolf, nexus);
          wolf.worldObj.spawnEntityInWorld(newWolf);
          wolf.setDead();
          itemstack.stackSize -= 1;
        }
      }
      return true;
    }
    return false;
  }

  public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityLiving, EntityLivingBase entityLiving1)
  {
    return false;
  }
}