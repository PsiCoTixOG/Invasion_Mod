/*     */ package invmod.common.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemLilyPad;
/*     */ import net.minecraft.item.ItemReed;
/*     */ import net.minecraft.util.Icon;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class ItemInfusedSword extends ItemLilyPad
/*     */ {
/*     */   private int a;
/*     */ 
/*     */   public ItemInfusedSword(int i)
/*     */   {
/*  23 */     super(i, Item.potionEffect);
/*  24 */     this.maxStackSize = 1;
/*  25 */     e(21);
/*  26 */     this.blockID = 7;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void a(Icon par1IconRegister)
/*     */   {
/*  33 */     this.cz = par1IconRegister.a("invmod:" + getUnlocalizedName().substring(5));
/*     */   }
/*     */ 
/*     */   public boolean isDamageable()
/*     */   {
/*  44 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean a(EnumToolMaterial itemstack, EntityLeashKnot entityliving, EntityLeashKnot entityliving1)
/*     */   {
/*  50 */     if (itemstack.k() > 0)
/*     */     {
/*  52 */       itemstack.b(itemstack.k() - 1);
/*     */     }
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onBlockStartBreak(EnumToolMaterial itemstack, int i, int j, int k, CallableItemName entityPlayer)
/*     */   {
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   public int getDamage(EnumToolMaterial stack)
/*     */   {
/*  66 */     return this.blockID;
/*     */   }
/*     */ 
/*     */   public float a(EnumToolMaterial par1ItemStack, BlockEndPortal par2Block)
/*     */   {
/*  71 */     if (par2Block.blockID == BlockEndPortal.ab.blockID)
/*     */     {
/*  73 */       return 15.0F;
/*     */     }
/*     */ 
/*  77 */     MaterialLogic material = par2Block.cU;
/*  78 */     return (material != MaterialLogic.k) && (material != MaterialLogic.l) && (material != MaterialLogic.v) && (material != MaterialLogic.j) && (material != MaterialLogic.B) ? 1.0F : 1.5F;
/*     */   }
/*     */ 
/*     */   public ItemReed c_(EnumToolMaterial par1ItemStack)
/*     */   {
/*  95 */     return ItemReed.spawnID;
/*     */   }
/*     */ 
/*     */   public int d_(EnumToolMaterial par1ItemStack)
/*     */   {
/* 101 */     return 0;
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial a(EnumToolMaterial itemstack, ColorizerGrass world, CallableItemName entityplayer)
/*     */   {
/* 107 */     if (itemstack.k() == 0)
/*     */     {
/* 109 */       entityplayer.f(6.0F);
/* 110 */       itemstack.b(20);
/* 111 */       world.a("heart", entityplayer.u + 1.5D, entityplayer.v, entityplayer.w, 0.0D, 0.0D, 0.0D);
/* 112 */       world.a("heart", entityplayer.u - 1.5D, entityplayer.v, entityplayer.w, 0.0D, 0.0D, 0.0D);
/* 113 */       world.a("heart", entityplayer.u, entityplayer.v, entityplayer.w + 1.5D, 0.0D, 0.0D, 0.0D);
/* 114 */       world.a("heart", entityplayer.u, entityplayer.v, entityplayer.w - 1.5D, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 117 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public boolean a(BlockEndPortal block)
/*     */   {
/* 123 */     return block.blockID == BlockEndPortal.ab.blockID;
/*     */   }
/*     */ 
/*     */   public boolean a(EnumToolMaterial par1ItemStack, ColorizerGrass par2World, int par3, int par4, int par5, int par6, EntityLeashKnot par7EntityLivingBase)
/*     */   {
/* 129 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemInfusedSword
 * JD-Core Version:    0.6.2
 */