/*     */ package invmod.common.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import invmod.common.entity.EntityIMTrap;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.IconFlipped;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.ItemMapBase;
/*     */ import net.minecraft.util.Icon;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class ItemIMTrap extends ItemIM
/*     */ {
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IconFlipped emptyIcon;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IconFlipped riftIcon;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IconFlipped flameIcon;
/* 108 */   public static final String[] trapNames = { "Empty Trap", "Rift Trap (folded)", "Flame Trap (folded)", "XYZ Trap" };
/*     */ 
/*     */   public ItemIMTrap(int itemId)
/*     */   {
/*  29 */     super(itemId);
/*  30 */     this.maxStackSize = 64;
/*  31 */     a(true);
/*  32 */     e(0);
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void a(Icon par1IconRegister)
/*     */   {
/*  39 */     this.emptyIcon = par1IconRegister.a("invmod:trapEmpty");
/*  40 */     this.riftIcon = par1IconRegister.a("invmod:trapPurple");
/*  41 */     this.flameIcon = par1IconRegister.a("invmod:trapRed");
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial a(EnumToolMaterial itemstack, ColorizerGrass world, CallableItemName entityplayer)
/*     */   {
/*  47 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(EnumToolMaterial itemstack, CallableItemName entityplayer, ColorizerGrass world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  53 */     if (world.I) {
/*  54 */       return false;
/*     */     }
/*  56 */     if (side == 1)
/*     */     {
/*     */       EntityIMTrap trap;
/*  59 */       if (itemstack.k() == 1) {
/*  60 */         trap = new EntityIMTrap(world, x + 0.5D, y + 1.0D, z + 0.5D, 1);
/*     */       }
/*     */       else
/*     */       {
/*     */         EntityIMTrap trap;
/*  61 */         if (itemstack.k() == 2)
/*  62 */           trap = new EntityIMTrap(world, x + 0.5D, y + 1.0D, z + 0.5D, 2);
/*     */         else
/*  64 */           return false;
/*     */       }
/*     */       EntityIMTrap trap;
/*  66 */       if ((trap.isValidPlacement()) && (world.a(EntityIMTrap.class, trap.E).size() == 0))
/*     */       {
/*  68 */         world.d(trap);
/*  69 */         itemstack.STONE -= 1;
/*     */       }
/*  71 */       return true;
/*     */     }
/*     */ 
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   public String d(EnumToolMaterial itemstack)
/*     */   {
/*  82 */     if (itemstack.k() < trapNames.length) {
/*  83 */       return trapNames[itemstack.k()];
/*     */     }
/*  85 */     return "";
/*     */   }
/*     */ 
/*     */   public IconFlipped b_(int i)
/*     */   {
/*  91 */     if (i == 1)
/*  92 */       return this.riftIcon;
/*  93 */     if (i == 2) {
/*  94 */       return this.flameIcon;
/*     */     }
/*  96 */     return this.emptyIcon;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void a(int id, ItemMapBase tab, List dest)
/*     */   {
/* 103 */     dest.add(new EnumToolMaterial(id, 1, 0));
/* 104 */     dest.add(new EnumToolMaterial(id, 1, 1));
/* 105 */     dest.add(new EnumToolMaterial(id, 1, 2));
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemIMTrap
 * JD-Core Version:    0.6.2
 */