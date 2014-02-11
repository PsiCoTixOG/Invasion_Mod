/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.common.entity.EntityIMBurrower;
/*    */ import invmod.common.util.PosRotate3D;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.client.renderer.tileentity.RenderEnderCrystal;
/*    */ import net.minecraft.client.resources.GrassColorReloadListener;
/*    */ import net.minecraft.entity.Entity;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class RenderBurrower extends RenderDragon
/*    */ {
/* 16 */   private static final GrassColorReloadListener texture = new GrassColorReloadListener("invmod:textures/burrower.png");
/*    */   private ModelBurrower2 modelBurrower;
/*    */ 
/*    */   public RenderBurrower()
/*    */   {
/* 22 */     this.modelBurrower = new ModelBurrower2(16);
/*    */   }
/*    */ 
/*    */   public void renderBurrower(EntityIMBurrower entityBurrower, double x, double y, double z, float yaw, float partialTick)
/*    */   {
/* 32 */     PosRotate3D[] pos = entityBurrower.getSegments3D();
/* 33 */     PosRotate3D[] lastPos = entityBurrower.getSegments3DLastTick();
/* 34 */     PosRotate3D[] renderPos = new PosRotate3D[17];
/* 35 */     renderPos[0] = new PosRotate3D();
/* 36 */     renderPos[0].setPosX(x * -7.269999980926514D);
/* 37 */     renderPos[0].setPosY(y * -7.269999980926514D);
/* 38 */     renderPos[0].setPosZ(z * 7.269999980926514D);
/* 39 */     renderPos[0].setRotX(entityBurrower.getPrevRotX() + partialTick * (entityBurrower.getRotX() - entityBurrower.getPrevRotX()));
/* 40 */     renderPos[0].setRotY(entityBurrower.getPrevRotY() + partialTick * (entityBurrower.getRotY() - entityBurrower.getPrevRotY()));
/* 41 */     renderPos[0].setRotZ(entityBurrower.getPrevRotZ() + partialTick * (entityBurrower.getRotZ() - entityBurrower.getPrevRotZ()));
/*    */ 
/* 43 */     for (int i = 0; i < 16; i++)
/*    */     {
/* 45 */       renderPos[(i + 1)] = new PosRotate3D();
/* 46 */       renderPos[(i + 1)].setPosX((lastPos[i].getPosX() + partialTick * (pos[i].getPosX() - lastPos[i].getPosX()) - RenderEnderCrystal.b) * -7.269999980926514D);
/* 47 */       renderPos[(i + 1)].setPosY((lastPos[i].getPosY() + partialTick * (pos[i].getPosY() - lastPos[i].getPosY()) - RenderEnderCrystal.c) * -7.269999980926514D);
/* 48 */       renderPos[(i + 1)].setPosZ((lastPos[i].getPosZ() + partialTick * (pos[i].getPosZ() - lastPos[i].getPosZ()) - RenderEnderCrystal.d) * 7.269999980926514D);
/* 49 */       renderPos[(i + 1)].setRotX(lastPos[i].getRotX() + partialTick * (pos[i].getRotX() - lastPos[i].getRotX()));
/* 50 */       renderPos[(i + 1)].setRotY(lastPos[i].getRotY() + partialTick * (pos[i].getRotY() - lastPos[i].getRotY()));
/* 51 */       renderPos[(i + 1)].setRotZ(lastPos[i].getRotZ() + partialTick * (pos[i].getRotZ() - lastPos[i].getRotZ()));
/*    */     }
/*    */ 
/* 54 */     GL11.glPushMatrix();
/* 55 */     GL11.glEnable(32826);
/* 56 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/* 57 */     GL11.glScalef(2.2F, 2.2F, 2.2F);
/* 58 */     b(entityBurrower);
/* 59 */     this.modelBurrower.render(entityBurrower, partialTick, renderPos, 0.0625F);
/* 60 */     GL11.glDisable(32826);
/* 61 */     GL11.glPopMatrix();
/*    */   }
/*    */ 
/*    */   public void a(nm entity, double d, double d1, double d2, float yaw, float partialTick)
/*    */   {
/* 67 */     renderBurrower((EntityIMBurrower)entity, d, d1, d2, yaw, partialTick);
/*    */   }
/*    */ 
/*    */   protected GrassColorReloadListener a(Entity entity)
/*    */   {
/* 73 */     return texture;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.RenderBurrower
 * JD-Core Version:    0.6.2
 */