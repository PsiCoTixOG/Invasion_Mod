/*    */ package invmod.common.nexus;
/*    */ 
/*    */ import net.minecraft.inventory.InventoryLargeChest;
/*    */ import net.minecraft.inventory.SlotCrafting;
/*    */ import net.minecraft.item.EnumToolMaterial;
/*    */ 
/*    */ public class SlotOutput extends SlotCrafting
/*    */ {
/*    */   public SlotOutput(InventoryLargeChest iinventory, int i, int j, int k)
/*    */   {
/* 13 */     super(iinventory, i, j, k);
/*    */   }
/*    */ 
/*    */   public boolean a(EnumToolMaterial itemstack)
/*    */   {
/* 19 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.SlotOutput
 * JD-Core Version:    0.6.2
 */