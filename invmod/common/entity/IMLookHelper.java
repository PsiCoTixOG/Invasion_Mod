/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.MathUtil;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityJumpHelper;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ 
/*     */ public class IMLookHelper extends EntityJumpHelper
/*     */ {
/*     */   private final EntityIMLiving a;
/*     */   private float b;
/*     */   private float c;
/*  17 */   private boolean d = false;
/*     */   private double e;
/*     */   private double f;
/*     */   private double g;
/*     */ 
/*     */   public IMLookHelper(EntityIMLiving entity)
/*     */   {
/*  24 */     super(entity);
/*  25 */     this.a = entity;
/*     */   }
/*     */ 
/*     */   public void a(nm par1Entity, float par2, float par3)
/*     */   {
/*  34 */     this.e = par1Entity.u;
/*     */ 
/*  36 */     if ((par1Entity instanceof EntityLivingBase))
/*     */     {
/*  38 */       this.f = (par1Entity.v + par1Entity.f());
/*     */     }
/*     */     else
/*     */     {
/*  42 */       this.f = ((par1Entity.E.b + par1Entity.E.e) / 2.0D);
/*     */     }
/*     */ 
/*  45 */     this.g = par1Entity.w;
/*  46 */     this.b = par2;
/*  47 */     this.c = par3;
/*  48 */     this.d = true;
/*     */   }
/*     */ 
/*     */   public void a(double par1, double par3, double par5, float par7, float par8)
/*     */   {
/*  56 */     this.e = par1;
/*  57 */     this.f = par3;
/*  58 */     this.g = par5;
/*  59 */     this.b = par7;
/*  60 */     this.c = par8;
/*  61 */     this.d = true;
/*     */   }
/*     */ 
/*     */   public void setJumping()
/*     */   {
/*  69 */     if (this.d)
/*     */     {
/*  71 */       this.d = false;
/*  72 */       double d0 = this.e - this.a.posX;
/*  73 */       double d1 = this.f - (this.a.posY + this.a.getEyeHeight());
/*  74 */       double d2 = this.g - this.a.posZ;
/*  75 */       double d3 = LongHashMapEntry.a(d0 * d0 + d2 * d2);
/*  76 */       float yaw = (float)MathUtil.boundAngle180Deg(this.a.rotationYaw);
/*  77 */       float pitch = (float)MathUtil.boundAngle180Deg(this.a.rotationPitch);
/*  78 */       float yawHeadOffset = (float)(Math.atan2(d2, d0) * 180.0D / 3.141592653589793D) - 90.0F - yaw;
/*  79 */       float pitchHeadOffset = (float)(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D + 40.0D - pitch);
/*  80 */       float f2 = (float)MathUtil.boundAngle180Deg(yawHeadOffset);
/*     */       float yawFinal;
/*     */       float yawFinal;
/*  82 */       if ((f2 > 100.0F) || (f2 < -100.0F))
/*  83 */         yawFinal = 0.0F;
/*     */       else {
/*  85 */         yawFinal = f2 / 6.0F;
/*     */       }
/*     */ 
/*  89 */       this.a.setRotationPitchHead(a(this.a.getRotationPitchHead(), pitchHeadOffset, this.c));
/*  90 */       this.a.setRotationYawHeadIM(a(this.a.getRotationYawHeadIM(), yawFinal, this.b));
/*     */     }
/*     */   }
/*     */ 
/*     */   private float a(float par1, float par2, float par3)
/*     */   {
/* 119 */     float f3 = LongHashMapEntry.g(par2 - par1);
/*     */ 
/* 121 */     if (f3 > par3)
/*     */     {
/* 123 */       f3 = par3;
/*     */     }
/*     */ 
/* 126 */     if (f3 < -par3)
/*     */     {
/* 128 */       f3 = -par3;
/*     */     }
/*     */ 
/* 131 */     return par1 + f3;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.IMLookHelper
 * JD-Core Version:    0.6.2
 */