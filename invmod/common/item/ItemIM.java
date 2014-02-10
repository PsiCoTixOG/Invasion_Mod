/*    */ package invmod.common.item;
/*    */ 
/*    */ import cpw.mods.fml.relauncher.Side;
/*    */ import cpw.mods.fml.relauncher.SideOnly;
/*    */ import net.minecraft.item.ItemHoe;
/*    */ import net.minecraft.util.Icon;
/*    */ 
/*    */ public class ItemIM extends ItemHoe
/*    */ {
/*    */   public ItemIM(int id)
/*    */   {
/* 12 */     super(id);
/*    */   }
/*    */ 
/*    */   @SideOnly(Side.CLIENT)
/*    */   public void a(Icon par1IconRegister)
/*    */   {
/* 19 */     this.cz = par1IconRegister.a("invmod:" + getUnlocalizedName().substring(5));
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemIM
 * JD-Core Version:    0.6.2
 */