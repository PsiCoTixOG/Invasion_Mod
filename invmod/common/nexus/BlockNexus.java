/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import invmod.common.mod_Invasion;
/*     */ import java.util.Random;
		  import net.minecraft.block.Block;
		  import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.client.renderer.IconFlipped;
		  import net.minecraft.client.renderer.texture.IconRegister;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
		  import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.Icon;
		  import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockNexus extends Block
/*     */ {
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon sideOn;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon sideOff;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon topOn;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon topOff;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon botTexture;
/*     */ 
/*     */   public BlockNexus(int id)
/*     */   {
/*  35 */     super(id, Material.iron);
/*     */   }
/*     */ 
/*     */   public void registerIcons(IconRegister iconRegister)
/*     */   {
/*  40 */     this.sideOn = iconRegister.registerIcon("invmod:nexusSideOn");
/*  41 */     this.sideOff = iconRegister.registerIcon("invmod:nexusSideOff");
/*  42 */     this.topOn = iconRegister.registerIcon("invmod:nexusTopOn");
/*  43 */     this.topOff = iconRegister.registerIcon("invmod:nexusTopOff");
/*  44 */     this.botTexture = iconRegister.registerIcon("obsidian");
/*     */   }
/*     */ 
/*     */   public Icon getIcon(int side, int meta)
/*     */   {
/*  50 */     if ((meta & 0x4) == 0)
/*     */     {
/*  52 */       if (side == 1)
/*     */       {
/*  54 */         return this.topOff;
/*     */       }
/*  56 */       return side != 0 ? this.sideOff : this.botTexture;
/*     */     }
/*     */ 
/*  60 */     if (side == 1)
/*     */     {
/*  62 */       return this.topOn;
/*     */     }
/*  64 */     return side != 0 ? this.sideOn : this.botTexture;
/*     */   }
/*     */ 
/*     */   public boolean a(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
/*     */   {
/*  85 */     ItemStack item = entityPlayer.getHeldItem();
/*  86 */     int itemId = item != null ? item.EMERALD : 0;
/*  87 */     if (world.isRemote) //PsiCoTix: should this be world.isRemote
/*     */     {
/*  89 */       return true;
/*     */     }
/*  91 */     if ((itemId != mod_Invasion.itemProbe.itemID) && ((!mod_Invasion.isDebug()) || (itemId != mod_Invasion.itemDebugWand.itemID)))
/*     */     {
/*  93 */       TileEntityNexus tileEntityNexus = (TileEntityNexus)world.r(x, y, z);
/*  94 */       if (tileEntityNexus != null)
/*     */       {
/*  96 */         mod_Invasion.setNexusClicked(tileEntityNexus);
/*  97 */         entityPlayer.openGui(mod_Invasion.getLoadedInstance(), mod_Invasion.getGuiIdNexus(), world, x, y, z);
/*     */       }
/*  99 */       return true;
/*     */     }
/*     */ 
/* 103 */     return false;
/*     */   }
/*     */ 
/*     */   public TileEntitySign b(World world)
/*     */   {
/* 110 */     return new TileEntityNexus(world);
/*     */   }
/*     */ 
/*     */   public void b(World world, int x, int y, int z, Random random)
/*     */   {
/* 119 */     int meta = world.h(x, y, z);
/*     */     int numberOfParticles;
/* 121 */     if ((meta & 0x4) == 0)
/* 122 */       numberOfParticles = 0;
/*     */     else {
/* 124 */       numberOfParticles = 6;
/*     */     }
/*     */ 
/* 128 */     for (int i = 0; i < numberOfParticles; i++)
/*     */     {
/* 132 */       double y1 = y + random.nextFloat();
/* 133 */       double y2 = (random.nextFloat() - 0.5D) * 0.5D;
/*     */ 
/* 136 */       int direction = random.nextInt(2) * 2 - 1;
/*     */       double x2;
/*     */       double x1;
/*     */       double z1;
/*     */       double z2;
/* 141 */       if (random.nextInt(2) == 0)
/*     */       {
/* 145 */         z1 = z + 0.5D + 0.25D * direction;
/* 146 */         z2 = random.nextFloat() * 2.0F * direction;
/*     */ 
/* 150 */         x1 = x + random.nextFloat();
/* 151 */         x2 = (random.nextFloat() - 0.5D) * 0.5D;
/*     */       }
/*     */       else
/*     */       {
/* 156 */         x1 = x + 0.5D + 0.25D * direction;
/* 157 */         x2 = random.nextFloat() * 2.0F * direction;
/* 158 */         z1 = z + random.nextFloat();
/* 159 */         z2 = (random.nextFloat() - 0.5D) * 0.5D;
/*     */       }
/*     */ 
/* 163 */       world.a("portal", x1, y1, z1, x2, y2, z2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.BlockNexus
 * JD-Core Version:    0.6.2
 */