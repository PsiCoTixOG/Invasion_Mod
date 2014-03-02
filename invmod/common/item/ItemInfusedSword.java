package invmod.common.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

public class ItemInfusedSword extends ItemSword
{
  private int a;

  public ItemInfusedSword(int i)
  {
    super(i, EnumToolMaterial.EMERALD);
    this.maxStackSize = 1;
    setMaxDamage(21);
    this.a = 7;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IconRegister par1IconRegister)
  {
    this.itemIcon = par1IconRegister.registerIcon("invmod:" + getUnlocalizedName().substring(5));
  }

  public boolean isDamageable()
  {
    return false;
  }

  public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1)
  {
    if (itemstack.getItemDamage() > 0)
    {
      itemstack.setItemDamage(itemstack.getItemDamage() - 1);
    }
    return true;
  }

  public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer entityPlayer)
  {
    return true;
  }

  public int getDamage(ItemStack stack)
  {
    return this.a;
  }

  public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
  {
    if (par2Block.blockID == Block.web.blockID)
    {
      return 15.0F;
    }

    Material material = par2Block.blockMaterial;
    return (material != Material.plants) && (material != Material.vine) && (material != Material.coral) && (material != Material.leaves) && (material != Material.pumpkin) ? 1.0F : 1.5F;
  }

  public EnumAction getItemUseAction(ItemStack par1ItemStack)
  {
    return EnumAction.none;
  }

  public int getMaxItemUseDuration(ItemStack par1ItemStack)
  {
    return 0;
  }

  public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
  {
    if (itemstack.getItemDamage() == 0)
    {
      entityplayer.heal(6.0F);
      itemstack.setItemDamage(20);
      world.spawnParticle("heart", entityplayer.posX + 1.5D, entityplayer.posY, entityplayer.posZ, 0.0D, 0.0D, 0.0D);
      world.spawnParticle("heart", entityplayer.posX - 1.5D, entityplayer.posY, entityplayer.posZ, 0.0D, 0.0D, 0.0D);
      world.spawnParticle("heart", entityplayer.posX, entityplayer.posY, entityplayer.posZ + 1.5D, 0.0D, 0.0D, 0.0D);
      world.spawnParticle("heart", entityplayer.posX, entityplayer.posY, entityplayer.posZ - 1.5D, 0.0D, 0.0D, 0.0D);
    }

    return itemstack;
  }

  public boolean canHarvestBlock(Block block)
  {
    return block.blockID == Block.web.blockID;
  }

  public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase)
  {
    return true;
  }
}