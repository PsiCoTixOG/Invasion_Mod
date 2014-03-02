package invmod.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import invmod.client.BowHackHandler;
import invmod.common.entity.EntityIMArrowOld;
import invmod.common.mod_Invasion;
import java.util.Random;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemIMBow extends ItemBow
{

  @SideOnly(Side.CLIENT)
  private Icon iconCharge1;

  @SideOnly(Side.CLIENT)
  private Icon iconCharge2;

  @SideOnly(Side.CLIENT)
  private Icon iconCharge3;

  public ItemIMBow(int i)
  {
    super(i);
    this.maxStackSize = 1;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister par1IconRegister)
  {
    this.itemIcon = par1IconRegister.registerIcon("invmod:" + getUnlocalizedName().substring(5));
    this.iconCharge1 = par1IconRegister.registerIcon("invmod:sbowc1");
    this.iconCharge2 = par1IconRegister.registerIcon("invmod:sbowc2");
    this.iconCharge3 = par1IconRegister.registerIcon("invmod:sbowc3");
  }

  public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
  {
    mod_Invasion.getBowHackHandler().setBowReleased();
    int var6 = getMaxItemUseDuration(par1ItemStack) - par4;

    ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, var6);
    MinecraftForge.EVENT_BUS.post(event);
    if (event.isCanceled())
    {
      return;
    }
    var6 = event.charge;

    boolean var5 = (par3EntityPlayer.capabilities.isCreativeMode) || (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0);

    if ((var5) || (par3EntityPlayer.inventory.hasItem(Item.arrow.itemID)))
    {
      float f = var6 / 20.0F;
      f = (f * f + f * 2.0F) / 3.0F;
      boolean special = false;

      if (f < 0.1D)
      {
        return;
      }
      if (f >= 3.8F)
      {
        special = true;
        f = 1.0F;
      }
      else if (f > 1.0F)
      {
        f = 1.0F;
      }

      if (!special)
      {
        EntityArrow var8 = new EntityArrow(par2World, par3EntityPlayer, f * 2.0F);
        if (f == 1.0F)
        {
          var8.setIsCritical(true);
        }

        int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

        if (var9 > 0)
        {
          var8.setDamage(var8.getDamage() + var9 * 0.5D + 0.5D);
        }

        int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

        if (var10 > 0)
        {
          var8.setKnockbackStrength(var10);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
        {
          var8.setFire(100);
        }

        if (var5)
        {
          var8.canBePickedUp = 2;
        }
        else
        {
          par3EntityPlayer.inventory.consumeInventoryItem(Item.arrow.itemID);
        }

        if (!par2World.isRemote)
        {
          par2World.spawnEntityInWorld(var8);
        }
      }
      else
      {
        EntityIMArrowOld var8 = new EntityIMArrowOld(par2World, par3EntityPlayer, f * 2.0F);
        if (f == 1.0F)
        {
          var8.arrowCritical = true;
        }
        if (!par2World.isRemote)
        {
          par2World.spawnEntityInWorld(var8);
        }
      }

      par1ItemStack.damageItem(1, par3EntityPlayer);
      par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (Item.itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
    }
  }

  public Icon getIconFromDamage(int i)
  {
    if (mod_Invasion.getBowHackHandler().getDrawTimeLeft() <= 0)
    {
      return this.itemIcon;
    }
    if (mod_Invasion.getBowHackHandler().getDrawTimeLeft() <= 500)
    {
      return this.iconCharge1;
    }
    if (mod_Invasion.getBowHackHandler().getDrawTimeLeft() <= 2400)
    {
      return this.iconCharge2;
    }

    return this.iconCharge3;
  }

  public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
  {
    return itemstack;
  }

  public int getMaxItemUseDuration(ItemStack itemstack)
  {
    return 72000;
  }

  public EnumAction getItemUseAction(ItemStack itemstack)
  {
    return EnumAction.bow;
  }

  public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer)
  {
    ArrowNockEvent event = new ArrowNockEvent(entityPlayer, itemStack);
    MinecraftForge.EVENT_BUS.post(event);
    if (event.isCanceled())
    {
      return event.result;
    }

    if ((entityPlayer.capabilities.isCreativeMode) || (entityPlayer.inventory.hasItem(Item.arrow.itemID)))
    {
      entityPlayer.setItemInUse(itemStack, getMaxItemUseDuration(itemStack));
      mod_Invasion.getBowHackHandler().setBowDrawing(true);
    }

    return itemStack;
  }

  public int getItemEnchantability()
  {
    return 1;
  }
}