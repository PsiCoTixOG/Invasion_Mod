/*     */ package invmod.common.entity;
/*     */ 
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import cpw.mods.fml.relauncher.SideOnly;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMGiantBird extends EntityIMBird
/*     */ {
/*     */   private static final float PICKUP_OFFSET_X = 0.0F;
/*     */   private static final float PICKUP_OFFSET_Y = 0.2F;
/*     */   private static final float PICKUP_OFFSET_Z = -0.92F;
/*     */   private static final float MODEL_ROTATION_OFFSET_Y = 1.9F;
/*     */   private static final byte TRIGGER_SQUAWK = 10;
/*     */   private static final byte TRIGGER_SCREECH = 10;
/*     */   private static final byte TRIGGER_DEATHSOUND = 10;
/*     */ 
/*     */   public EntityIMGiantBird(ColorizerGrass world)
/*     */   {
/*  22 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMGiantBird(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  27 */     super(world, nexus);
/*  28 */     setName("Bird");
/*  29 */     setGender(2);
/*  30 */     this.attackStrength = 5;
/*  31 */     setMaxHealthAndHealth(58.0F);
/*  32 */     setSize(1.9F, 2.8F);
/*  33 */     setGravity(0.03F);
/*  34 */     setThrust(0.028F);
/*  35 */     setMaxPoweredFlightSpeed(0.9F);
/*  36 */     setLiftFactor(0.35F);
/*  37 */     setThrustComponentRatioMin(0.0F);
/*  38 */     setThrustComponentRatioMax(0.5F);
/*  39 */     setMaxTurnForce(getGravity() * 8.0F);
/*  40 */     setBaseMoveSpeedStat(0.4F);
/*  41 */     setAI();
/*  42 */     setDebugMode(1);
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  47 */     super.onUpdate();
/*  48 */     if ((getDebugMode() == 1) && (!this.q.I))
/*     */     {
/*  50 */       setRenderLabel(getAIGoal() + "\n" + getNavString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canDespawn()
/*     */   {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   public void updateRidden()
/*     */   {
/*  61 */     if (this.n != null)
/*     */     {
/*  64 */       double x = 0.0D;
/*  65 */       double y = getYOffset() - 1.899999976158142D;
/*  66 */       double z = -0.9200000166893005D;
/*     */ 
/*  70 */       double dAngle = this.rotationPitch / 180.0F * 3.141592653589793D;
/*  71 */       double sinF = Math.sin(dAngle);
/*  72 */       double cosF = Math.cos(dAngle);
/*  73 */       double tmp = z * cosF - y * sinF;
/*  74 */       y = y * cosF + z * sinF;
/*  75 */       z = tmp;
/*     */ 
/*  78 */       dAngle = this.rotationYaw / 180.0F * 3.141592653589793D;
/*  79 */       sinF = Math.sin(dAngle);
/*  80 */       cosF = Math.cos(dAngle);
/*  81 */       tmp = x * cosF - z * sinF;
/*  82 */       z = z * cosF + x * sinF;
/*  83 */       x = tmp;
/*     */ 
/*  85 */       y += 1.899999976158142D + this.n.W();
/*     */ 
/*  89 */       this.n.U = (this.lastTickPosX + x);
/*  90 */       this.n.V = (this.lastTickPosY + y);
/*  91 */       this.n.W = (this.lastTickPosZ + z);
/*  92 */       this.n.b(this.posX + x, this.posY + y, this.posZ + z);
/*  93 */       this.n.A = (getCarriedEntityYawOffset() + this.rotationYaw);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean shouldRiderSit()
/*     */   {
/*  99 */     return false;
/*     */   }
/*     */ 
/*     */   public double getYOffset()
/*     */   {
/* 107 */     return -0.2000000029802322D;
/*     */   }
/*     */ 
/*     */   protected void doScreech()
/*     */   {
/* 113 */     if (!this.q.I)
/*     */     {
/* 115 */       this.q.a(this, "invmod:v_screech", 6.0F, 1.0F + (this.rand.nextFloat() * 0.2F - 0.1F));
/* 116 */       this.q.a(this, (byte)10);
/*     */     }
/*     */     else
/*     */     {
/* 120 */       setBeakState(35);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void doMeleeSound()
/*     */   {
/* 127 */     doSquawk();
/*     */   }
/*     */ 
/*     */   protected void doHurtSound()
/*     */   {
/* 133 */     doSquawk();
/*     */   }
/*     */ 
/*     */   protected void doDeathSound()
/*     */   {
/* 139 */     if (!this.q.I)
/*     */     {
/* 141 */       this.q.a(this, "invmod:v_death", 1.9F, 1.0F + (this.rand.nextFloat() * 0.2F - 0.1F));
/* 142 */       this.q.a(this, (byte)10);
/*     */     }
/*     */     else
/*     */     {
/* 146 */       setBeakState(25);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void onDebugChange()
/*     */   {
/* 152 */     if (getDebugMode() == 1)
/*     */     {
/* 154 */       setShouldRenderLabel(true);
/*     */     }
/*     */     else
/*     */     {
/* 158 */       setShouldRenderLabel(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   @SideOnly(Side.CLIENT)
/*     */   public void handleHealthUpdate(byte b)
/*     */   {
/* 166 */     super.handleHealthUpdate(b);
/* 167 */     if (b == 10)
/*     */     {
/* 169 */       doSquawk();
/*     */     }
/* 171 */     else if (b == 10)
/*     */     {
/* 173 */       doScreech();
/*     */     }
/* 175 */     else if (b == 10)
/*     */     {
/* 177 */       doDeathSound();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doSquawk()
/*     */   {
/* 183 */     if (!this.q.I)
/*     */     {
/* 185 */       this.q.a(this, "invmod:v_squawk", 1.9F, 1.0F + (this.rand.nextFloat() * 0.2F - 0.1F));
/* 186 */       this.q.a(this, (byte)10);
/*     */     }
/*     */     else
/*     */     {
/* 190 */       setBeakState(10);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getNavString()
/*     */   {
/* 196 */     return getNavigatorNew().getStatus();
/*     */   }
/*     */ 
/*     */   private void setAI()
/*     */   {
/* 201 */     this.c = new EntityAIBase(this.q.C);
/*     */ 
/* 204 */     this.c.a(0, new EntityAISwoop(this));
/*     */ 
/* 206 */     this.c.a(3, new EntityAIBoP(this));
/* 207 */     this.c.a(4, new EntityAIFlyingStrike(this));
/* 208 */     this.c.a(4, new EntityAIFlyingTackle(this));
/* 209 */     this.c.a(4, new EntityAIPickUpEntity(this, 0.0F, 0.2F, 0.0F, 1.5F, 1.5F, 20, 45.0F, 45.0F));
/* 210 */     this.c.a(4, new EntityAIStabiliseFlying(this, 35));
/* 211 */     this.c.a(4, new EntityAICircleTarget(this, 300, 16.0F, 45.0F));
/* 212 */     this.c.a(4, new EntityAIBirdFight(this, EntityWitch.class, 25, 0.4F));
/* 213 */     this.c.a(4, new EntityAIWatchTarget(this));
/*     */ 
/* 215 */     this.d = new EntityAIBase(this.q.C);
/*     */ 
/* 217 */     this.d.a(2, new EntityAISimpleTarget(this, EntityWitch.class, 58.0F, true));
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMGiantBird
 * JD-Core Version:    0.6.2
 */