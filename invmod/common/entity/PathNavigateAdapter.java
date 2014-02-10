/*     */ package invmod.common.entity;
/*     */ 
/*     */ import net.minecraft.entity.ai.EntityAITarget;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.world.gen.layer.GenLayerZoom;
/*     */ 
/*     */ public class PathNavigateAdapter extends EntityAITarget
/*     */ {
/*     */   private NavigatorIM navigator;
/*     */ 
/*     */   public PathNavigateAdapter(NavigatorIM navigator)
/*     */   {
/*  23 */     super(navigator.getEntity(), navigator.getEntity().q);
/*  24 */     this.navigator = navigator;
/*     */   }
/*     */ 
/*     */   public void f()
/*     */   {
/*  30 */     this.navigator.onUpdateNavigation();
/*     */   }
/*     */ 
/*     */   public boolean g()
/*     */   {
/*  36 */     return this.navigator.noPath();
/*     */   }
/*     */ 
/*     */   public void h()
/*     */   {
/*  42 */     this.navigator.clearPath();
/*     */   }
/*     */ 
/*     */   public void a(double speed)
/*     */   {
/*  51 */     this.navigator.setSpeed((float)speed);
/*     */   }
/*     */ 
/*     */   public boolean a(double x, double y, double z, double movespeed)
/*     */   {
/*  60 */     return this.navigator.tryMoveToXYZ(x, y, z, 0.0F, (float)movespeed);
/*     */   }
/*     */ 
/*     */   public boolean a(nm entity, double movespeed)
/*     */   {
/*  69 */     return this.navigator.tryMoveToEntity(entity, 0.0F, (float)movespeed);
/*     */   }
/*     */ 
/*     */   public boolean setPath(Path entity, float movespeed)
/*     */   {
/*  77 */     return this.navigator.setPath(entity, movespeed);
/*     */   }
/*     */ 
/*     */   public boolean a(GenLayerZoom entity, double movespeed)
/*     */   {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   public GenLayerZoom a(double x, double y, double z)
/*     */   {
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   public void a(boolean par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/* 114 */     return false;
/*     */   }
/*     */ 
/*     */   public void b(boolean par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void c(boolean par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean c()
/*     */   {
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   public void d(boolean par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void e(boolean par1)
/*     */   {
/*     */   }
/*     */ 
/*     */   public GenLayerZoom a(nm par1EntityLiving)
/*     */   {
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   public GenLayerZoom e()
/*     */   {
/* 173 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.PathNavigateAdapter
 * JD-Core Version:    0.6.2
 */