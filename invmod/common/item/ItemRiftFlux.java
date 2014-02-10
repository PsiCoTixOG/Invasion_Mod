/*    */ package invmod.common.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.player.CallableItemName;
/*    */ import net.minecraft.item.EnumToolMaterial;
/*    */ import net.minecraft.item.ItemMapBase;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class ItemRiftFlux extends ItemIM
/*    */ {
/* 55 */   public static final String[] fluxNames = { "Inert Flux", "Rift Flux", "ff Flux", "yy Flux", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid", "invalid" };
/*    */ 
/*    */   public ItemRiftFlux(int i)
/*    */   {
/* 18 */     super(i);
/* 19 */     a(true);
/* 20 */     e(0);
/*    */   }
/*    */ 
/*    */   public String d(EnumToolMaterial itemstack)
/*    */   {
/* 32 */     return fluxNames[itemstack.k()].toString();
/*    */   }
/*    */ 
/*    */   public boolean onItemUseFirst(EnumToolMaterial itemstack, CallableItemName entityplayer, ColorizerGrass world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*    */   {
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void a(int id, ItemMapBase tab, List dest)
/*    */   {
/* 52 */     dest.add(new EnumToolMaterial(id, 1, 1));
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemRiftFlux
 * JD-Core Version:    0.6.2
 */