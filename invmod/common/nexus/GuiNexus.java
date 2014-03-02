package invmod.common.nexus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiNexus extends GuiContainer
{
  private static final ResourceLocation background = new ResourceLocation("invmod:textures/nexusgui.png");
  private TileEntityNexus tileEntityNexus;

  public GuiNexus(InventoryPlayer inventoryplayer, TileEntityNexus tileentityNexus)
  {
    super(new ContainerNexus(inventoryplayer, tileentityNexus));
    this.tileEntityNexus = tileentityNexus;
  }

  protected void drawGuiContainerForegroundLayer(int x, int y)
  {
    this.fontRenderer.drawString("Nexus - Level " + this.tileEntityNexus.getNexusLevel(), 46, 6, 4210752);
    this.fontRenderer.drawString(this.tileEntityNexus.getNexusKills() + " mobs killed", 96, 60, 4210752);
    this.fontRenderer.drawString("R: " + this.tileEntityNexus.getSpawnRadius(), 142, 72, 4210752);

    if ((this.tileEntityNexus.getMode() == 1) || (this.tileEntityNexus.getMode() == 3))
    {
      this.fontRenderer.drawString("Activated!", 13, 62, 4210752);
      this.fontRenderer.drawString("Wave " + this.tileEntityNexus.getCurrentWave(), 55, 37, 4210752);
    }
    else if (this.tileEntityNexus.getMode() == 2)
    {
      this.fontRenderer.drawString("Power:", 56, 31, 4210752);
      this.fontRenderer.drawString("" + this.tileEntityNexus.getNexusPowerLevel(), 61, 44, 4210752);
    }

    if ((this.tileEntityNexus.isActivating()) && (this.tileEntityNexus.getMode() == 0))
    {
      this.fontRenderer.drawString("Activating...", 13, 62, 4210752);
      if (this.tileEntityNexus.getMode() != 4)
        this.fontRenderer.drawString("Are you sure?", 8, 72, 4210752);
    }
  }

  protected void drawGuiContainerBackgroundLayer(float f, int un1, int un2)
  {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(background);
    int j = (this.width - this.xSize) / 2;
    int k = (this.height - this.ySize) / 2;
    drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);

    int l = this.tileEntityNexus.getGenerationProgressScaled(26);
    drawTexturedModalRect(j + 126, k + 28 + 26 - l, 185, 26 - l, 9, l);
    l = this.tileEntityNexus.getCookProgressScaled(18);
    drawTexturedModalRect(j + 31, k + 51, 204, 0, l, 2);

    if ((this.tileEntityNexus.getMode() == 1) || (this.tileEntityNexus.getMode() == 3))
    {
      drawTexturedModalRect(j + 19, k + 29, 176, 0, 9, 31);
      drawTexturedModalRect(j + 19, k + 19, 194, 0, 9, 9);
    }
    else if (this.tileEntityNexus.getMode() == 2)
    {
      drawTexturedModalRect(j + 19, k + 29, 176, 31, 9, 31);
    }

    if (((this.tileEntityNexus.getMode() == 0) || (this.tileEntityNexus.getMode() == 2)) && (this.tileEntityNexus.isActivating()))
    {
      l = this.tileEntityNexus.getActivationProgressScaled(31);
      drawTexturedModalRect(j + 19, k + 29 + 31 - l, 176, 31 - l, 9, l);
    }
    else if ((this.tileEntityNexus.getMode() == 4) && (this.tileEntityNexus.isActivating()))
    {
      l = this.tileEntityNexus.getActivationProgressScaled(31);
      drawTexturedModalRect(j + 19, k + 29 + 31 - l, 176, 62 - l, 9, l);
    }
  }
}