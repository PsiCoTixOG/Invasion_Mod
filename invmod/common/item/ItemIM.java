/*    */ package invmod.common.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
		 import net.minecraft.client.renderer.texture.IconRegister;
/*    */ import net.minecraft.item.Item;
/*    */ 
/*    */ public class ItemIM extends Item
/*    */ {
/*    */   public ItemIM(int id)
/*    */   {
/* 12 */     super(id);
/*    */   }
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void registerIcons(IconRegister par1IconRegister)
/*    */   {
/* 19 */     this.itemIcon = par1IconRegister.registerIcon("invmod:" + (getUnlocalizedName().substring(5)));
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemIM
 * JD-Core Version:    0.6.2
 */