/*     */ package invmod.common.entity;
/*     */ 
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityAISprint extends EntityAIFollowParent
/*     */ {
/*     */   private EntityIMLiving theEntity;
/*     */   private int updateTimer;
/*     */   private int timer;
/*     */   private boolean isExecuting;
/*     */   private boolean isSprinting;
/*     */   private boolean isInWindup;
/*     */   private int missingTarget;
/*     */   private double lastX;
/*     */   private double lastY;
/*     */   private double lastZ;
/*     */ 
/*     */   public EntityAISprint(EntityIMLiving entity)
/*     */   {
/*  22 */     this.theEntity = entity;
/*  23 */     this.updateTimer = 0;
/*  24 */     this.timer = 0;
/*  25 */     this.isExecuting = true;
/*  26 */     this.isSprinting = false;
/*  27 */     this.isInWindup = false;
/*  28 */     this.missingTarget = 0;
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  34 */     if (--this.updateTimer <= 0)
/*     */     {
/*  36 */       this.updateTimer = 20;
/*  37 */       if (((this.theEntity.m() != null) && (this.theEntity.o(this.theEntity.m()))) || (this.isSprinting))
/*     */       {
/*  39 */         return true;
/*     */       }
/*     */ 
/*  43 */       this.isExecuting = false;
/*  44 */       return false;
/*     */     }
/*     */ 
/*  48 */     return this.isExecuting;
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  54 */     this.isExecuting = true;
/*  55 */     this.timer = 60;
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  61 */     if (this.isSprinting)
/*     */     {
/*  63 */       nm target = this.theEntity.m();
/*  64 */       if ((!this.theEntity.isSneaking()) || (target == null) || ((this.missingTarget > 0) && (++this.missingTarget > 20)))
/*     */       {
/*  66 */         endSprint();
/*  67 */         return;
/*     */       }
/*     */ 
/*  70 */       double dX = target.u - this.theEntity.posX;
/*  71 */       double dZ = target.w - this.theEntity.posZ;
/*  72 */       double dAngle = (Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D - this.theEntity.rotationYaw) % 360.0D;
/*  73 */       if (dAngle > 60.0D)
/*     */       {
/*  75 */         this.theEntity.setTurnRate(2.0F);
/*  76 */         this.missingTarget = 1;
/*     */       }
/*     */ 
/*  79 */       if (this.theEntity.getDistanceSq(this.lastX, this.lastY, this.lastZ) < 0.0009D)
/*     */       {
/*  81 */         crash();
/*  82 */         return;
/*     */       }
/*     */ 
/*  85 */       this.lastX = this.theEntity.posX;
/*  86 */       this.lastY = this.theEntity.posY;
/*  87 */       this.lastZ = this.theEntity.posZ;
/*     */     }
/*     */ 
/*  91 */     if (--this.timer <= 0)
/*     */     {
/*  93 */       if (!this.isInWindup)
/*     */       {
/*  95 */         if (!this.isSprinting)
/*     */         {
/*  97 */           startSprint();
/*     */         }
/*     */         else
/*     */         {
/* 101 */           endSprint();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 106 */         sprint();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void startSprint()
/*     */   {
/* 113 */     nm target = this.theEntity.m();
/* 114 */     if ((target == null) || (target.E.b - this.theEntity.posY >= 1.0D)) {
/* 115 */       return;
/*     */     }
/* 117 */     double dX = target.u - this.theEntity.posX;
/* 118 */     double dZ = target.w - this.theEntity.posZ;
/* 119 */     double dAngle = (Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D - 90.0D - this.theEntity.rotationYaw) % 360.0D;
/* 120 */     if (dAngle < 10.0D)
/*     */     {
/* 122 */       this.isInWindup = true;
/* 123 */       this.timer = 20;
/*     */ 
/* 125 */       this.theEntity.setMoveSpeedStat(0.0F);
/*     */     }
/*     */     else
/*     */     {
/* 129 */       this.timer = 10;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void sprint()
/*     */   {
/* 135 */     this.isInWindup = false;
/* 136 */     this.isSprinting = true;
/* 137 */     this.missingTarget = 0;
/* 138 */     this.timer = 35;
/*     */ 
/* 140 */     this.theEntity.resetMoveSpeed();
/* 141 */     this.theEntity.setMoveSpeedStat(this.theEntity.getMoveSpeedStat() * 2.3F);
/* 142 */     this.theEntity.setSprinting(true);
/* 143 */     this.theEntity.setTurnRate(4.9F);
/* 144 */     this.theEntity.attackTime = 0;
/*     */   }
/*     */ 
/*     */   protected void endSprint()
/*     */   {
/* 149 */     this.isSprinting = false;
/* 150 */     this.timer = 180;
/* 151 */     this.theEntity.resetMoveSpeed();
/* 152 */     this.theEntity.setTurnRate(30.0F);
/* 153 */     this.theEntity.setSprinting(false);
/*     */   }
/*     */ 
/*     */   protected void crash()
/*     */   {
/* 158 */     this.theEntity.stunEntity(40);
/* 159 */     this.theEntity.a(CombatTracker.j, 5.0F);
/* 160 */     this.theEntity.q.a(this.theEntity, "random.explode", 1.0F, 0.6F);
/* 161 */     endSprint();
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAISprint
 * JD-Core Version:    0.6.2
 */