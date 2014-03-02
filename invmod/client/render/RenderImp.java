package invmod.client.render;

import invmod.common.entity.EntityIMImp;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderImp extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("textures/entity/arrow.png");

	public RenderImp(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	protected void preRenderScale(EntityIMImp entity, float f) {
		GL11.glScalef(0.7F, 1.0F, 1.0F);
	}

	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		preRenderScale((EntityIMImp) entityliving, f);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}