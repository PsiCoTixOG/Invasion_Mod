/*    */ package invmod.common.nexus;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiSlot;
/*    */ import net.minecraft.client.gui.GuiTextField;
/*    */ import net.minecraft.client.gui.achievement.GuiSlotStatsItem;
/*    */ import net.minecraft.client.renderer.StitcherException;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.entity.player.PlayerCapabilities;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class GuiNexus extends GuiSlotStatsItem
/*    */ {
/* 19 */   private static final GrassColorReloadListener background = new GrassColorReloadListener("invmod:textures/nexusgui.png");
/*    */   private TileEntityNexus tileEntityNexus;
/*    */ 
/*    */   public GuiNexus(PlayerCapabilities inventoryplayer, TileEntityNexus tileentityNexus)
/*    */   {
/* 23 */     super(new ContainerNexus(inventoryplayer, tileentityNexus));
/* 24 */     this.tileEntityNexus = tileentityNexus;
/*    */   }
/*    */ 
/*    */   protected void func_77215_b(int x, int y)
/*    */   {
/* 30 */     this.o.b("Nexus - Level " + this.tileEntityNexus.getNexusLevel(), 46, 6, 4210752);
/* 31 */     this.o.b(this.tileEntityNexus.getNexusKills() + " mobs killed", 96, 60, 4210752);
/* 32 */     this.o.b("R: " + this.tileEntityNexus.getSpawnRadius(), 142, 72, 4210752);
/*    */ 
/* 35 */     if ((this.tileEntityNexus.getMode() == 1) || (this.tileEntityNexus.getMode() == 3))
/*    */     {
/* 37 */       this.o.b("Activated!", 13, 62, 4210752);
/* 38 */       this.o.b("Wave " + this.tileEntityNexus.getCurrentWave(), 55, 37, 4210752);
/*    */     }
/* 40 */     else if (this.tileEntityNexus.getMode() == 2)
/*    */     {
/* 42 */       this.o.b("Power:", 56, 31, 4210752);
/* 43 */       this.o.b("" + this.tileEntityNexus.getNexusPowerLevel(), 61, 44, 4210752);
/*    */     }
/*    */ 
/* 46 */     if ((this.tileEntityNexus.isActivating()) && (this.tileEntityNexus.getMode() == 0))
/*    */     {
/* 48 */       this.o.b("Activating...", 13, 62, 4210752);
/* 49 */       if (this.tileEntityNexus.getMode() != 4)
/* 50 */         this.o.b("Are you sure?", 8, 72, 4210752);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void a(float f, int un1, int un2)
/*    */   {
/* 57 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 58 */     this.f.J().a(background);
/* 59 */     int j = (this.mouseY - this.top) / 2;
/* 60 */     int k = (this.height - this.bottom) / 2;
/* 61 */     b(j, k, 0, 0, this.top, this.bottom);
/*    */ 
/* 63 */     int l = this.tileEntityNexus.getGenerationProgressScaled(26);
/* 64 */     b(j + 126, k + 28 + 26 - l, 185, 26 - l, 9, l);
/* 65 */     l = this.tileEntityNexus.getCookProgressScaled(18);
/* 66 */     b(j + 31, k + 51, 204, 0, l, 2);
/*    */ 
/* 68 */     if ((this.tileEntityNexus.getMode() == 1) || (this.tileEntityNexus.getMode() == 3))
/*    */     {
/* 70 */       b(j + 19, k + 29, 176, 0, 9, 31);
/* 71 */       b(j + 19, k + 19, 194, 0, 9, 9);
/*    */     }
/* 73 */     else if (this.tileEntityNexus.getMode() == 2)
/*    */     {
/* 75 */       b(j + 19, k + 29, 176, 31, 9, 31);
/*    */     }
/*    */ 
/* 78 */     if (((this.tileEntityNexus.getMode() == 0) || (this.tileEntityNexus.getMode() == 2)) && (this.tileEntityNexus.isActivating()))
/*    */     {
/* 80 */       l = this.tileEntityNexus.getActivationProgressScaled(31);
/* 81 */       b(j + 19, k + 29 + 31 - l, 176, 31 - l, 9, l);
/*    */     }
/* 83 */     else if ((this.tileEntityNexus.getMode() == 4) && (this.tileEntityNexus.isActivating()))
/*    */     {
/* 85 */       l = this.tileEntityNexus.getActivationProgressScaled(31);
/* 86 */       b(j + 19, k + 29 + 31 - l, 176, 62 - l, 9, l);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.GuiNexus
 * JD-Core Version:    0.6.2
 */