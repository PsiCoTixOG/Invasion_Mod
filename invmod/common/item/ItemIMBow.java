/*     */ package invmod.common.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import invmod.client.BowHackHandler;
/*     */ import invmod.common.entity.EntityIMArrowOld;
/*     */ import invmod.common.mod_Invasion;

/*     */ import java.util.Random;

/*     */ import net.minecraft.client.renderer.IconFlipped;
		  import net.minecraft.client.renderer.texture.IconRegister;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentDigging;
/*     */ import net.minecraft.enchantment.EnumEnchantmentType;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.entity.player.EnumStatus;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemGlassBottle;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemReed;
/*     */ import net.minecraft.util.Icon;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.EventBus;
/*     */ import net.minecraftforge.event.entity.player.ArrowLooseEvent;
/*     */ import net.minecraftforge.event.entity.player.ArrowNockEvent;
/*     */ 
/*     */ public class ItemIMBow extends ItemGlassBottle
/*     */ {
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon iconCharge1;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon iconCharge2;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private Icon iconCharge3;
/*     */ 
/*     */   public ItemIMBow(int i)
/*     */   {
/*  35 */     super(i);
/*  36 */     this.maxStackSize = 1;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void registerIcons(IconRegister par1IconRegister)
/*     */   {
/*  43 */     this.itemIcon = par1IconRegister.registerIcon("invmod:" + getUnlocalizedName().substring(5));
/*  44 */     this.iconCharge1 = par1IconRegister.registerIcon("invmod:sbowc1");
/*  45 */     this.iconCharge2 = par1IconRegister.registerIcon("invmod:sbowc2");
/*  46 */     this.iconCharge3 = par1IconRegister.registerIcon("invmod:sbowc3");
/*     */   }
/*     */ 
/*     */   public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
/*     */   {
/*  52 */     mod_Invasion.getBowHackHandler().setBowReleased();
/*  53 */     int var6 = d_(par1ItemStack) - par4;
/*     */ 
/*  55 */     ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, var6);
/*  56 */     MinecraftForge.EVENT_BUS.post(event);
/*  57 */     if (event.isCanceled())
/*     */     {
/*  59 */       return;
/*     */     }
/*  61 */     var6 = event.charge;
/*     */ 
/*  63 */     boolean var5 = (par3EntityPlayer.bG.d) || (EnumEnchantmentType.a(EnchantmentDigging.y.effectId, par1ItemStack) > 0);
/*     */ 
/*  65 */     if ((var5) || (par3EntityPlayer.bn.e(ItemHoe.n.itemID)))
/*     */     {
/*  68 */       float f = var6 / 20.0F;
/*  69 */       f = (f * f + f * 2.0F) / 3.0F;
/*  70 */       boolean special = false;
/*     */ 
/*  72 */       if (f < 0.1D)
/*     */       {
/*  74 */         return;
/*     */       }
/*  76 */       if (f >= 3.8F)
/*     */       {
/*  78 */         special = true;
/*  79 */         f = 1.0F;
/*     */       }
/*  81 */       else if (f > 1.0F)
/*     */       {
/*  83 */         f = 1.0F;
/*     */       }
/*     */ 
/*  87 */       if (!special)
/*     */       {
/*  89 */         EnumStatus var8 = new EnumStatus(par2World, par3EntityPlayer, f * 2.0F);
/*  90 */         if (f == 1.0F)
/*     */         {
/*  92 */           var8.a(true);
/*     */         }
/*     */ 
/*  95 */         int var9 = EnumEnchantmentType.a(EnchantmentDigging.v.effectId, par1ItemStack);
/*     */ 
/*  97 */         if (var9 > 0)
/*     */         {
/*  99 */           var8.b(var8.c() + var9 * 0.5D + 0.5D);
/*     */         }
/*     */ 
/* 102 */         int var10 = EnumEnchantmentType.a(EnchantmentDigging.w.effectId, par1ItemStack);
/*     */ 
/* 104 */         if (var10 > 0)
/*     */         {
/* 106 */           var8.a(var10);
/*     */         }
/*     */ 
/* 109 */         if (EnumEnchantmentType.a(EnchantmentDigging.x.effectId, par1ItemStack) > 0)
/*     */         {
/* 111 */           var8.d(100);
/*     */         }
/*     */ 
/* 114 */         if (var5)
/*     */         {
/* 116 */           var8.OK = 2;
/*     */         }
/*     */         else
/*     */         {
/* 120 */           par3EntityPlayer.bn.d(ItemHoe.n.itemID);
/*     */         }
/*     */ 
/* 123 */         if (!par2World.I)
/*     */         {
/* 125 */           par2World.d(var8);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 130 */         EntityIMArrowOld var8 = new EntityIMArrowOld(par2World, par3EntityPlayer, f * 2.0F);
/* 131 */         if (f == 1.0F)
/*     */         {
/* 133 */           var8.arrowCritical = true;
/*     */         }
/* 135 */         if (!par2World.I)
/*     */         {
/* 137 */           par2World.d(var8);
/*     */         }
/*     */       }
/*     */ 
/* 141 */       par1ItemStack.a(1, par3EntityPlayer);
/* 142 */       par2World.a(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (Item.itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IconFlipped b_(int i)
/*     */   {
/* 151 */     if (mod_Invasion.getBowHackHandler().getDrawTimeLeft() <= 0)
/*     */     {
/* 153 */       return this.cz;
/*     */     }
/* 155 */     if (mod_Invasion.getBowHackHandler().getDrawTimeLeft() <= 500)
/*     */     {
/* 157 */       return this.iconCharge1;
/*     */     }
/* 159 */     if (mod_Invasion.getBowHackHandler().getDrawTimeLeft() <= 2400)
/*     */     {
/* 161 */       return this.iconCharge2;
/*     */     }
/*     */ 
/* 165 */     return this.iconCharge3;
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial b(EnumToolMaterial itemstack, ColorizerGrass world, CallableItemName entityplayer)
/*     */   {
/* 177 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public int d_(EnumToolMaterial itemstack)
/*     */   {
/* 183 */     return 72000;
/*     */   }
/*     */ 
/*     */   public ItemReed c_(EnumToolMaterial itemstack)
/*     */   {
/* 189 */     return ItemReed.e;
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial a(EnumToolMaterial itemStack, ColorizerGrass world, CallableItemName entityPlayer)
/*     */   {
/* 195 */     ArrowNockEvent event = new ArrowNockEvent(entityPlayer, itemStack);
/* 196 */     MinecraftForge.EVENT_BUS.post(event);
/* 197 */     if (event.isCanceled())
/*     */     {
/* 199 */       return event.result;
/*     */     }
/*     */ 
/* 202 */     if ((entityPlayer.bG.d) || (entityPlayer.bn.e(ItemHoe.n.itemID)))
/*     */     {
/* 204 */       entityPlayer.a(itemStack, d_(itemStack));
/* 205 */       mod_Invasion.getBowHackHandler().setBowDrawing(true);
/*     */     }
/*     */ 
/* 208 */     return itemStack;
/*     */   }
/*     */ 
/*     */   public int getItemEnchantability()
/*     */   {
/* 214 */     return 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemIMBow
 * JD-Core Version:    0.6.2
 */