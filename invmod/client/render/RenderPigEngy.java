package invmod.client.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderPigEngy extends RenderBiped
{
  private static final ResourceLocation texture = new ResourceLocation("invmod:textures/pigengT1.png");

  public RenderPigEngy(ModelBiped model, float shadowSize)
  {
    super(model, shadowSize);
  }

  public RenderPigEngy(ModelBiped model, float shadowSize, float par3)
  {
    super(model, shadowSize, par3);
  }

  protected ResourceLocation func_110775_a(Entity entity)
  {
    return texture;
  }
}