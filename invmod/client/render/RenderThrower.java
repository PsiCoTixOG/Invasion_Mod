package invmod.client.render;

import invmod.common.entity.EntityIMThrower;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class RenderThrower extends RenderLiving {
	private static final ResourceLocation texture = new ResourceLocation("invmod:textures/throwerT1.png");

	public RenderThrower(ModelBase modelbase, float f) {
		super(modelbase, f);
	}

	protected void preRenderScale(EntityIMThrower entity, float f) {
		GL11.glScalef(2.4F, 2.8F, 2.4F);
	}

	protected void preRenderCallback(EntityLivingBase entityliving, float f) {
		preRenderScale((EntityIMThrower) entityliving, f);
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}
}