/*    */ package invmod.common;
/*    */ 
/*    */ import bgj;
/*    */ import cpw.mods.fml.common.FMLCommonHandler;
/*    */ import java.io.File;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.management.PlayerPositionComparator;
/*    */ import net.minecraft.src.cu;
/*    */ import nm;
/*    */ 
/*    */ public class ProxyCommon
/*    */ {
/*    */   public void preloadTexture(String texture)
/*    */   {
/*    */   }
/*    */ 
/*    */   public int addTextureOverride(String fileToOverride, String fileToAdd)
/*    */   {
/* 18 */     return 0;
/*    */   }
/*    */ 
/*    */   public void registerEntityRenderingHandler(Class<? extends nm> entityClass, bgj renderer)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void broadcastToAll(String message)
/*    */   {
/* 27 */     FMLCommonHandler.instance().getMinecraftServerInstance().af().a(cu.e(message));
/*    */   }
/*    */ 
/*    */   public void printGuiMessage(String message)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void registerEntityRenderers()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void loadAnimations()
/*    */   {
/*    */   }
/*    */ 
/*    */   public File getFile(String fileName)
/*    */   {
/* 44 */     return FMLCommonHandler.instance().getMinecraftServerInstance().getFile(fileName);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.ProxyCommon
 * JD-Core Version:    0.6.2
 */