/*     */ package invmod.common.item;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import invmod.common.entity.EntityIMLiving;
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.TileEntityNexus;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.IconFlipped;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.ItemMapBase;
/*     */ import net.minecraft.util.Icon;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class ItemProbe extends ItemIM
/*     */ {
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IconFlipped iconAdjuster;
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   private IconFlipped iconProbe;
/* 114 */   public static final String[] probeNames = { "Nexus Adjuster", "Material Probe" };
/*     */ 
/*     */   public ItemProbe(int itemId)
/*     */   {
/*  28 */     super(itemId);
/*  29 */     this.maxStackSize = 1;
/*  30 */     a(true);
/*  31 */     e(0);
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void a(Icon par1IconRegister)
/*     */   {
/*  38 */     this.iconAdjuster = par1IconRegister.a("invmod:adjuster");
/*  39 */     this.iconProbe = par1IconRegister.a("invmod:probe");
/*     */   }
/*     */ 
/*     */   public boolean isFull3D()
/*     */   {
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial a(EnumToolMaterial itemstack, ColorizerGrass world, CallableItemName entityplayer)
/*     */   {
/*  51 */     return itemstack;
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(EnumToolMaterial itemstack, CallableItemName entityplayer, ColorizerGrass world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  57 */     if (world.I) {
/*  58 */       return false;
/*     */     }
/*  60 */     int id = world.a(x, y, z);
/*  61 */     if (id == mod_Invasion.blockNexus.cF)
/*     */     {
/*  63 */       TileEntityNexus nexus = (TileEntityNexus)world.r(x, y, z);
/*  64 */       int newRange = nexus.getSpawnRadius() + 8;
/*  65 */       if (newRange > 128)
/*  66 */         newRange = 32;
/*  67 */       nexus.setSpawnRadius(newRange);
/*     */ 
/*  69 */       mod_Invasion.sendMessageToPlayer(entityplayer.bu, "Nexus range changed to: " + nexus.getSpawnRadius());
/*  70 */       return true;
/*     */     }
/*  72 */     if (itemstack.k() == 1)
/*     */     {
/*  74 */       float blockStrength = EntityIMLiving.getBlockStrength(x, y, z, id, world);
/*     */ 
/*  76 */       mod_Invasion.sendMessageToPlayer(entityplayer.bu, "Block strength: " + (int)((blockStrength + 0.005D) * 100.0D) / 100.0D);
/*  77 */       return true;
/*     */     }
/*  79 */     return false;
/*     */   }
/*     */ 
/*     */   public String d(EnumToolMaterial itemstack)
/*     */   {
/*  85 */     if (itemstack.k() < probeNames.length) {
/*  86 */       return probeNames[itemstack.k()];
/*     */     }
/*  88 */     return "";
/*     */   }
/*     */ 
/*     */   public IconFlipped b_(int i)
/*     */   {
/*  94 */     if (i == 1) {
/*  95 */       return this.iconProbe;
/*     */     }
/*  97 */     return this.iconAdjuster;
/*     */   }
/*     */ 
/*     */   public int getItemEnchantability()
/*     */   {
/* 103 */     return 14;
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void a(int id, ItemMapBase tab, List dest)
/*     */   {
/* 110 */     dest.add(new EnumToolMaterial(id, 1, 0));
/* 111 */     dest.add(new EnumToolMaterial(id, 1, 1));
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemProbe
 * JD-Core Version:    0.6.2
 */