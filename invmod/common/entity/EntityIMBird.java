/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.client.render.AnimationRegistry;
/*     */ import invmod.client.render.animation.AnimationAction;
/*     */ import invmod.client.render.animation.AnimationState;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityWaterMob;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.potion.PotionHealth;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraftforge.common.ForgeHooks;
/*     */ 
/*     */ public class EntityIMBird extends EntityIMFlying
/*     */ {
/*     */   private static final int META_ANIMATION_FLAGS = 26;
/*     */   private AnimationState animationRun;
/*     */   private AnimationState animationFlap;
/*     */   private AnimationState animationBeak;
/*     */   private WingController wingController;
/*     */   private LegController legController;
/*     */   private MouthController beakController;
/*     */   private int animationFlags;
/*     */   private float carriedEntityYawOffset;
/*     */ 
/*     */   public EntityIMBird(ColorizerGrass world)
/*     */   {
/*  30 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMBird(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  35 */     super(world, nexus);
/*  36 */     this.animationRun = new AnimationState(AnimationRegistry.instance().getAnimation("bird_run"));
/*  37 */     this.animationFlap = new AnimationState(AnimationRegistry.instance().getAnimation("wing_flap_2_piece"));
/*  38 */     this.animationBeak = new AnimationState(AnimationRegistry.instance().getAnimation("bird_beak"));
/*  39 */     this.animationRun.setNewAction(AnimationAction.STAND);
/*  40 */     this.animationFlap.setNewAction(AnimationAction.WINGTUCK);
/*  41 */     this.animationBeak.setNewAction(AnimationAction.MOUTH_CLOSE);
/*  42 */     this.wingController = new WingController(this, this.animationFlap);
/*  43 */     this.legController = new LegController(this, this.animationRun);
/*  44 */     this.beakController = new MouthController(this, this.animationBeak);
/*  45 */     setName("Bird");
/*  46 */     setGender(2);
/*  47 */     setBaseMoveSpeedStat(1.0F);
/*  48 */     this.attackStrength = 1;
/*  49 */     setMaxHealthAndHealth(18.0F);
/*  50 */     this.animationFlags = 0;
/*  51 */     this.carriedEntityYawOffset = 0.0F;
/*  52 */     setGravity(0.025F);
/*  53 */     setThrust(0.1F);
/*  54 */     setMaxPoweredFlightSpeed(0.5F);
/*  55 */     setLiftFactor(0.35F);
/*  56 */     setThrustComponentRatioMin(0.0F);
/*  57 */     setThrustComponentRatioMax(0.5F);
/*  58 */     setMaxTurnForce(getGravity() * 8.0F);
/*  59 */     setMoveState(MoveState.STANDING);
/*  60 */     setFlyState(FlyState.GROUNDED);
/*     */ 
/*  62 */     this.ah.a(26, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   protected void doScreech()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void doMeleeSound()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void doHurtSound()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void doDeathSound()
/*     */   {
/*     */   }
/*     */ 
/*     */   public AnimationState getWingAnimationState()
/*     */   {
/*  83 */     return this.animationFlap;
/*     */   }
/*     */ 
/*     */   public float getLegSweepProgress()
/*     */   {
/*  89 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   public AnimationState getLegAnimationState()
/*     */   {
/*  94 */     return this.animationRun;
/*     */   }
/*     */ 
/*     */   public AnimationState getBeakAnimationState()
/*     */   {
/*  99 */     return this.animationBeak;
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/* 106 */     super.onUpdate();
/* 107 */     if (this.q.I)
/*     */     {
/* 109 */       updateFlapAnimation();
/* 110 */       updateLegAnimation();
/* 111 */       updateBeakAnimation();
/* 112 */       this.animationFlags = this.ah.c(26);
/*     */     }
/*     */     else
/*     */     {
/* 116 */       this.ah.b(26, Integer.valueOf(this.animationFlags));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/* 124 */     return "Bird";
/*     */   }
/*     */ 
/*     */   public boolean getClawsForward()
/*     */   {
/* 129 */     return (this.animationFlags & 0x1) > 0;
/*     */   }
/*     */ 
/*     */   public boolean isAttackingWithWings()
/*     */   {
/* 134 */     return (this.animationFlags & 0x2) > 0;
/*     */   }
/*     */ 
/*     */   public boolean isBeakOpen()
/*     */   {
/* 139 */     return (this.animationFlags & 0x4) > 0;
/*     */   }
/*     */ 
/*     */   public float getCarriedEntityYawOffset()
/*     */   {
/* 144 */     return this.carriedEntityYawOffset;
/*     */   }
/*     */ 
/*     */   public boolean a(CombatTracker par1DamageSource, float par2)
/*     */   {
/* 150 */     if (ForgeHooks.onLivingAttack(this, par1DamageSource, par2)) return false;
/* 151 */     if (canAttackWithItem())
/*     */     {
/* 153 */       return false;
/*     */     }
/* 155 */     if (this.q.I)
/*     */     {
/* 157 */       return false;
/*     */     }
/*     */ 
/* 161 */     this.entityAge = 0;
/*     */ 
/* 163 */     if (aM() <= 0.0F)
/*     */     {
/* 165 */       return false;
/*     */     }
/* 167 */     if ((par1DamageSource.m()) && (a(PotionHealth.n)))
/*     */     {
/* 169 */       return false;
/*     */     }
/*     */ 
/* 173 */     if (((par1DamageSource == CombatTracker.m) || (par1DamageSource == CombatTracker.n)) && (n(4) != null))
/*     */     {
/* 175 */       n(4).a((int)(par2 * 4.0F + this.rand.nextFloat() * par2 * 2.0F), this);
/* 176 */       par2 *= 0.75F;
/*     */     }
/*     */ 
/* 179 */     this.limbSwingAmount = 1.5F;
/* 180 */     boolean flag = true;
/*     */ 
/* 182 */     if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F)
/*     */     {
/* 184 */       if (par2 <= this.lastDamage)
/*     */       {
/* 186 */         return false;
/*     */       }
/*     */ 
/* 189 */       d(par1DamageSource, par2 - this.lastDamage);
/* 190 */       this.lastDamage = par2;
/* 191 */       flag = false;
/*     */     }
/*     */     else
/*     */     {
/* 195 */       this.lastDamage = par2;
/* 196 */       this.prevHealth = aM();
/* 197 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 198 */       d(par1DamageSource, par2);
/* 199 */       this.hurtTime = (this.maxHurtTime = 10);
/*     */     }
/*     */ 
/* 202 */     this.attackedAtYaw = 0.0F;
/* 203 */     nm entity = par1DamageSource.i();
/*     */ 
/* 205 */     if (entity != null)
/*     */     {
/* 207 */       if ((entity instanceof EntityLeashKnot))
/*     */       {
/* 209 */         b((EntityLeashKnot)entity);
/*     */       }
/*     */ 
/* 212 */       if ((entity instanceof CallableItemName))
/*     */       {
/* 214 */         this.recentlyHit = 100;
/* 215 */         this.aS = ((CallableItemName)entity);
/*     */       }
/* 217 */       else if ((entity instanceof EntityWaterMob))
/*     */       {
/* 219 */         EntityWaterMob entitywolf = (EntityWaterMob)entity;
/*     */ 
/* 221 */         if (entitywolf.bT())
/*     */         {
/* 223 */           this.recentlyHit = 100;
/* 224 */           this.aS = null;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 229 */     if (flag)
/*     */     {
/* 231 */       this.q.a(this, (byte)2);
/*     */ 
/* 233 */       if (par1DamageSource != CombatTracker.field_94553_e)
/*     */       {
/* 235 */         J();
/*     */       }
/*     */ 
/* 238 */       if (entity != null)
/*     */       {
/* 240 */         double d0 = entity.u - this.posX;
/*     */ 
/* 243 */         for (double d1 = entity.w - this.posZ; d0 * d0 + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
/*     */         {
/* 245 */           d0 = (Math.random() - Math.random()) * 0.01D;
/*     */         }
/*     */ 
/* 248 */         this.attackedAtYaw = ((float)(Math.atan2(d1, d0) * 180.0D / 3.141592653589793D) - this.rotationYaw);
/* 249 */         a(entity, par2, d0, d1);
/*     */       }
/*     */       else
/*     */       {
/* 253 */         this.attackedAtYaw = ((int)(Math.random() * 2.0D) * 180);
/*     */       }
/*     */     }
/*     */ 
/* 257 */     if (aM() <= 0.0F)
/*     */     {
/* 259 */       if (flag)
/*     */       {
/* 261 */         doDeathSound();
/*     */       }
/*     */ 
/* 264 */       a(par1DamageSource);
/*     */     }
/* 266 */     else if (flag)
/*     */     {
/* 268 */       doHurtSound();
/*     */     }
/*     */ 
/* 271 */     return true;
/*     */   }
/*     */ 
/*     */   protected void setBeakState(int timeOpen)
/*     */   {
/* 283 */     this.beakController.setMouthState(timeOpen);
/*     */   }
/*     */ 
/*     */   protected void onPickedUpEntity(nm entity)
/*     */   {
/* 288 */     this.carriedEntityYawOffset = (entity.A - entity.A);
/*     */   }
/*     */ 
/*     */   protected void setClawsForward(boolean flag)
/*     */   {
/* 293 */     if ((flag ? 1 : 0) != (this.animationFlags & 0x1))
/* 294 */       this.animationFlags ^= 1;
/*     */   }
/*     */ 
/*     */   protected void setAttackingWithWings(boolean flag)
/*     */   {
/* 299 */     if ((flag ? 1 : 0) != (this.animationFlags & 0x2))
/* 300 */       this.animationFlags ^= 2;
/*     */   }
/*     */ 
/*     */   protected void setBeakOpen(boolean flag)
/*     */   {
/* 305 */     if ((flag ? 1 : 0) != (this.animationFlags & 0x4))
/* 306 */       this.animationFlags ^= 4;
/*     */   }
/*     */ 
/*     */   protected void collideWithNearbyEntities()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void updateFlapAnimation()
/*     */   {
/* 318 */     this.wingController.update();
/*     */   }
/*     */ 
/*     */   protected void updateLegAnimation()
/*     */   {
/* 323 */     this.legController.update();
/*     */   }
/*     */ 
/*     */   protected void updateBeakAnimation()
/*     */   {
/* 328 */     this.beakController.update();
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMBird
 * JD-Core Version:    0.6.2
 */