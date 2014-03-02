package invmod.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelEgg extends ModelBase
{
  ModelRenderer base;
  ModelRenderer l3s4;
  ModelRenderer l3s2;
  ModelRenderer l3s3;
  ModelRenderer l3s1;
  ModelRenderer top;
  ModelRenderer l4s1;
  ModelRenderer l4s4;
  ModelRenderer l2s3;
  ModelRenderer l2s4;
  ModelRenderer l2s1;
  ModelRenderer l4s2;
  ModelRenderer l4s3;
  ModelRenderer l2s2;
  ModelRenderer l1s4;
  ModelRenderer l1s1;
  ModelRenderer l1s2;
  ModelRenderer l1s3;

  public ModelEgg()
  {
    this.textureWidth = 64;
    this.textureHeight = 32;

    this.base = new ModelRenderer(this, 0, 0);
    this.base.addBox(0.0F, 0.0F, 0.0F, 7, 1, 7);
    this.base.setRotationPoint(1.0F, 0.0F, 1.0F);
    this.base.setTextureSize(64, 32);
    this.base.mirror = true;
    setRotation(this.base, 0.0F, 0.0F, 0.0F);
    this.l3s4 = new ModelRenderer(this, 10, 22);
    this.l3s4.addBox(0.0F, 0.0F, 0.0F, 1, 2, 8);
    this.l3s4.setRotationPoint(0.0F, -7.0F, 1.0F);
    this.l3s4.setTextureSize(64, 32);
    this.l3s4.mirror = true;
    setRotation(this.l3s4, 0.0F, 0.0F, 0.0F);
    this.l3s2 = new ModelRenderer(this, 10, 22);
    this.l3s2.addBox(0.0F, 0.0F, 0.0F, 1, 2, 8);
    this.l3s2.setRotationPoint(8.0F, -7.0F, 0.0F);
    this.l3s2.setTextureSize(64, 32);
    this.l3s2.mirror = true;
    setRotation(this.l3s2, 0.0F, 0.0F, 0.0F);
    this.l3s3 = new ModelRenderer(this, 0, 21);
    this.l3s3.addBox(0.0F, 0.0F, 0.0F, 8, 2, 1);
    this.l3s3.setRotationPoint(0.0F, -7.0F, 0.0F);
    this.l3s3.setTextureSize(64, 32);
    this.l3s3.mirror = true;
    setRotation(this.l3s3, 0.0F, 0.0F, 0.0F);
    this.l3s1 = new ModelRenderer(this, 0, 21);
    this.l3s1.addBox(0.0F, 0.0F, 0.0F, 8, 2, 1);
    this.l3s1.setRotationPoint(1.0F, -7.0F, 8.0F);
    this.l3s1.setTextureSize(64, 32);
    this.l3s1.mirror = true;
    setRotation(this.l3s1, 0.0F, 0.0F, 0.0F);
    this.top = new ModelRenderer(this, 0, 8);
    this.top.addBox(0.0F, 0.0F, 0.0F, 5, 1, 5);
    this.top.setRotationPoint(2.0F, -11.0F, 2.0F);
    this.top.setTextureSize(64, 32);
    this.top.mirror = true;
    setRotation(this.top, 0.0F, 0.0F, 0.0F);
    this.l4s1 = new ModelRenderer(this, 0, 24);
    this.l4s1.addBox(0.0F, 0.0F, 0.0F, 6, 3, 1);
    this.l4s1.setRotationPoint(2.0F, -10.0F, 7.0F);
    this.l4s1.setTextureSize(64, 32);
    this.l4s1.mirror = true;
    setRotation(this.l4s1, 0.0F, 0.0F, 0.0F);
    this.l4s4 = new ModelRenderer(this, 28, 23);
    this.l4s4.addBox(0.0F, 0.0F, 0.0F, 1, 3, 6);
    this.l4s4.setRotationPoint(1.0F, -10.0F, 2.0F);
    this.l4s4.setTextureSize(64, 32);
    this.l4s4.mirror = true;
    setRotation(this.l4s4, 0.0F, 0.0F, 0.0F);
    this.l2s3 = new ModelRenderer(this, 0, 16);
    this.l2s3.addBox(1.0F, 0.0F, 0.0F, 9, 4, 1);
    this.l2s3.setRotationPoint(-1.0F, -5.0F, -1.0F);
    this.l2s3.setTextureSize(64, 32);
    this.l2s3.mirror = true;
    setRotation(this.l2s3, 0.0F, 0.0F, 0.0F);
    this.l2s4 = new ModelRenderer(this, 20, 10);
    this.l2s4.addBox(0.0F, 0.0F, 0.0F, 1, 4, 9);
    this.l2s4.setRotationPoint(-1.0F, -5.0F, 0.0F);
    this.l2s4.setTextureSize(64, 32);
    this.l2s4.mirror = true;
    setRotation(this.l2s4, 0.0F, 0.0F, 0.0F);
    this.l2s1 = new ModelRenderer(this, 0, 16);
    this.l2s1.addBox(0.0F, 0.0F, 0.0F, 9, 4, 1);
    this.l2s1.setRotationPoint(0.0F, -5.0F, 9.0F);
    this.l2s1.setTextureSize(64, 32);
    this.l2s1.mirror = true;
    setRotation(this.l2s1, 0.0F, 0.0F, 0.0F);
    this.l4s2 = new ModelRenderer(this, 28, 23);
    this.l4s2.addBox(0.0F, 0.0F, 0.0F, 1, 3, 6);
    this.l4s2.setRotationPoint(7.0F, -10.0F, 1.0F);
    this.l4s2.setTextureSize(64, 32);
    this.l4s2.mirror = true;
    setRotation(this.l4s2, 0.0F, 0.0F, 0.0F);
    this.l4s3 = new ModelRenderer(this, 0, 24);
    this.l4s3.addBox(0.0F, 0.0F, 0.0F, 6, 3, 1);
    this.l4s3.setRotationPoint(1.0F, -10.0F, 1.0F);
    this.l4s3.setTextureSize(64, 32);
    this.l4s3.mirror = true;
    setRotation(this.l4s3, 0.0F, 0.0F, 0.0F);
    this.l2s2 = new ModelRenderer(this, 20, 10);
    this.l2s2.addBox(0.0F, 0.0F, 0.0F, 1, 4, 9);
    this.l2s2.setRotationPoint(9.0F, -5.0F, 0.0F);
    this.l2s2.setTextureSize(64, 32);
    this.l2s2.mirror = true;
    setRotation(this.l2s2, 0.0F, 0.0F, 0.0F);
    this.l1s4 = new ModelRenderer(this, 28, 0);
    this.l1s4.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8);
    this.l1s4.setRotationPoint(0.0F, -1.0F, 1.0F);
    this.l1s4.setTextureSize(64, 32);
    this.l1s4.mirror = true;
    setRotation(this.l1s4, 0.0F, 0.0F, 0.0F);
    this.l1s1 = new ModelRenderer(this, 0, 14);
    this.l1s1.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1);
    this.l1s1.setRotationPoint(1.0F, -1.0F, 8.0F);
    this.l1s1.setTextureSize(64, 32);
    this.l1s1.mirror = true;
    setRotation(this.l1s1, 0.0F, 0.0F, 0.0F);
    this.l1s2 = new ModelRenderer(this, 28, 0);
    this.l1s2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8);
    this.l1s2.setRotationPoint(8.0F, -1.0F, 0.0F);
    this.l1s2.setTextureSize(64, 32);
    this.l1s2.mirror = true;
    setRotation(this.l1s2, 0.0F, 0.0F, 0.0F);
    this.l1s3 = new ModelRenderer(this, 0, 14);
    this.l1s3.addBox(0.0F, 0.0F, 0.0F, 8, 1, 1);
    this.l1s3.setRotationPoint(0.0F, -1.0F, 0.0F);
    this.l1s3.setTextureSize(64, 32);
    this.l1s3.mirror = true;
    setRotation(this.l1s3, 0.0F, 0.0F, 0.0F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    this.base.render(f5);
    this.l3s4.render(f5);
    this.l3s2.render(f5);
    this.l3s3.render(f5);
    this.l3s1.render(f5);
    this.top.render(f5);
    this.l4s1.render(f5);
    this.l4s4.render(f5);
    this.l2s3.render(f5);
    this.l2s4.render(f5);
    this.l2s1.render(f5);
    this.l4s2.render(f5);
    this.l4s3.render(f5);
    this.l2s2.render(f5);
    this.l1s4.render(f5);
    this.l1s1.render(f5);
    this.l1s2.render(f5);
    this.l1s3.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
  }
}