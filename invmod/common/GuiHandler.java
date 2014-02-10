/*    */ package invmod.common;
/*    */ 
/*    */ import cpw.mods.fml.common.network.IGuiHandler;
/*    */ import invmod.common.nexus.ContainerNexus;
/*    */ import invmod.common.nexus.GuiNexus;
/*    */ import invmod.common.nexus.TileEntityNexus;
/*    */ import net.minecraft.entity.player.CallableItemName;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class GuiHandler
/*    */   implements IGuiHandler
/*    */ {
/*    */   public Object getClientGuiElement(int id, CallableItemName player, ColorizerGrass world, int x, int y, int z)
/*    */   {
/* 16 */     if (id == mod_Invasion.getGuiIdNexus())
/*    */     {
/* 18 */       TileEntityNexus nexus = (TileEntityNexus)world.r(x, y, z);
/* 19 */       if (nexus != null) {
/* 20 */         return new GuiNexus(player.bn, nexus);
/*    */       }
/*    */     }
/* 23 */     return null;
/*    */   }
/*    */ 
/*    */   public Object getServerGuiElement(int id, CallableItemName player, ColorizerGrass world, int x, int y, int z)
/*    */   {
/* 30 */     if (id == mod_Invasion.getGuiIdNexus())
/*    */     {
/* 32 */       TileEntityNexus nexus = (TileEntityNexus)world.r(x, y, z);
/* 33 */       if (nexus != null) {
/* 34 */         return new ContainerNexus(player.bn, nexus);
/*    */       }
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.GuiHandler
 * JD-Core Version:    0.6.2
 */