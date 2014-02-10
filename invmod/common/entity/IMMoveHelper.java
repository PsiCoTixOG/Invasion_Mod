/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.IPosition;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.src.pc;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class IMMoveHelper extends EntityLookHelper
/*     */ {
/*     */   protected EntityIMLiving a;
/*     */   protected double b;
/*     */   protected double c;
/*     */   protected double d;
/*     */   protected double setSpeed;
/*     */   protected double targetSpeed;
/*     */   protected boolean needsUpdate;
/*     */   protected boolean isRunning;
/*     */ 
/*     */   public IMMoveHelper(EntityIMLiving par1EntityLiving)
/*     */   {
/*  29 */     super(par1EntityLiving);
/*  30 */     this.needsUpdate = false;
/*  31 */     this.a = par1EntityLiving;
/*  32 */     this.b = par1EntityLiving.posX;
/*  33 */     this.c = par1EntityLiving.posY;
/*  34 */     this.d = par1EntityLiving.posZ;
/*  35 */     this.setSpeed = (this.targetSpeed = 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean a()
/*     */   {
/*  47 */     return this.needsUpdate;
/*     */   }
/*     */ 
/*     */   public double b()
/*     */   {
/*  56 */     return this.setSpeed;
/*     */   }
/*     */ 
/*     */   public void setMoveSpeed(float speed)
/*     */   {
/*  61 */     this.setSpeed = speed;
/*     */   }
/*     */ 
/*     */   public void setMoveTo(IPosition pos, float speed)
/*     */   {
/*  66 */     a(pos.getXCoord(), pos.getYCoord(), pos.getZCoord(), speed);
/*     */   }
/*     */ 
/*     */   public void a(double x, double y, double z, double speed)
/*     */   {
/*  72 */     this.b = x;
/*  73 */     this.c = y;
/*  74 */     this.d = z;
/*  75 */     this.setSpeed = speed;
/*  76 */     this.needsUpdate = true;
/*     */   }
/*     */ 
/*     */   public void c()
/*     */   {
/*  82 */     if (!this.needsUpdate)
/*     */     {
/*  84 */       this.a.setMoveForward(0.0F);
/*  85 */       this.a.setMoveState(MoveState.STANDING);
/*  86 */       return;
/*     */     }
/*     */ 
/*  89 */     MoveState result = doGroundMovement();
/*  90 */     this.a.setMoveState(result);
/*     */   }
/*     */ 
/*     */   protected MoveState doGroundMovement()
/*     */   {
/*  95 */     this.needsUpdate = false;
/*  96 */     this.targetSpeed = this.setSpeed;
/*  97 */     boolean isInLiquid = (this.a.isWet()) || (this.a.handleWaterMovement());
/*  98 */     double dX = this.b - this.a.posX;
/*  99 */     double dZ = this.d - this.a.posZ;
/* 100 */     double dY = this.c - (!isInLiquid ? LongHashMapEntry.c(this.a.E.b + 0.5D) : this.a.posY);
/*     */ 
/* 103 */     float newYaw = (float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D) - 90.0F;
/* 104 */     int ladderPos = -1;
/* 105 */     if ((Math.abs(dX) < 0.8D) && (Math.abs(dZ) < 0.8D) && ((dY > 0.0D) || (this.a.isHoldingOntoLadder())))
/*     */     {
/* 108 */       ladderPos = getClimbFace(this.a.posX, this.a.posY, this.a.posZ);
/* 109 */       if (ladderPos == -1) {
/* 110 */         ladderPos = getClimbFace(this.a.posX, this.a.posY + 1.0D, this.a.posZ);
/*     */       }
/*     */ 
/* 113 */       switch (ladderPos) {
/*     */       case 0:
/* 115 */         newYaw = (float)(Math.atan2(dZ, dX + 1.0D) * 180.0D / 3.141592653589793D) - 90.0F; break;
/*     */       case 1:
/* 116 */         newYaw = (float)(Math.atan2(dZ, dX - 1.0D) * 180.0D / 3.141592653589793D) - 90.0F; break;
/*     */       case 2:
/* 117 */         newYaw = (float)(Math.atan2(dZ + 1.0D, dX) * 180.0D / 3.141592653589793D) - 90.0F; break;
/*     */       case 3:
/* 118 */         newYaw = (float)(Math.atan2(dZ - 1.0D, dX) * 180.0D / 3.141592653589793D) - 90.0F;
/*     */       }
/*     */     }
/*     */ 
/* 122 */     double dXZSq = dX * dX + dZ * dZ;
/* 123 */     double distanceSquared = dXZSq + dY * dY;
/* 124 */     if ((distanceSquared < 0.01D) && (ladderPos == -1)) {
/* 125 */       return MoveState.STANDING;
/*     */     }
/*     */ 
/* 129 */     if ((dXZSq > 0.04D) || (ladderPos != -1))
/*     */     {
/* 131 */       this.a.rotationYaw = correctRotation(this.a.rotationYaw, newYaw, this.a.getTurnRate());
/*     */       double moveSpeed;
/*     */       double moveSpeed;
/* 133 */       if ((distanceSquared >= 0.064D) || (this.a.isSneaking()))
/* 134 */         moveSpeed = this.targetSpeed;
/*     */       else {
/* 136 */         moveSpeed = this.targetSpeed * 0.5D;
/*     */       }
/* 138 */       if ((this.a.isWet()) && (moveSpeed < 0.6D)) {
/* 139 */         moveSpeed = 0.6000000238418579D;
/*     */       }
/* 141 */       this.a.setAIMoveSpeed((float)moveSpeed);
/*     */     }
/*     */ 
/* 146 */     double w = Math.max(this.a.width * 0.5F + 1.0F, 1.0D);
/* 147 */     w = this.a.width * 0.5F + 1.0F;
/* 148 */     if ((dY > 0.0D) && ((dX * dX + dZ * dZ <= w * w) || (isInLiquid)))
/*     */     {
/* 150 */       this.a.j().a();
/* 151 */       if (ladderPos != -1)
/* 152 */         return MoveState.CLIMBING;
/*     */     }
/* 154 */     return MoveState.RUNNING;
/*     */   }
/*     */ 
/*     */   protected float correctRotation(float currentYaw, float newYaw, float turnSpeed)
/*     */   {
/* 164 */     float dYaw = newYaw - currentYaw;
/* 165 */     while (dYaw < -180.0F) dYaw += 360.0F;
/* 166 */     while (dYaw >= 180.0F) dYaw -= 360.0F;
/* 167 */     if (dYaw > turnSpeed)
/* 168 */       dYaw = turnSpeed;
/* 169 */     if (dYaw < -turnSpeed) {
/* 170 */       dYaw = -turnSpeed;
/*     */     }
/* 172 */     return currentYaw + dYaw;
/*     */   }
/*     */ 
/*     */   protected int getClimbFace(double x, double y, double z)
/*     */   {
/* 181 */     int mobX = LongHashMapEntry.c(x);
/* 182 */     int mobY = LongHashMapEntry.c(y);
/* 183 */     int mobZ = LongHashMapEntry.c(z);
/*     */ 
/* 185 */     int id = this.a.q.a(mobX, mobY, mobZ);
/* 186 */     if (id == BlockEndPortal.aK.blockID)
/*     */     {
/* 188 */       int meta = this.a.q.h(mobX, mobY, mobZ);
/* 189 */       if (meta == 2)
/* 190 */         return 2;
/* 191 */       if (meta == 3)
/* 192 */         return 3;
/* 193 */       if (meta == 4)
/* 194 */         return 0;
/* 195 */       if (meta == 5)
/* 196 */         return 1;
/*     */     }
/* 198 */     else if (id == BlockEndPortal.bz.blockID)
/*     */     {
/* 200 */       int meta = this.a.q.h(mobX, mobY, mobZ);
/* 201 */       if (meta == 1)
/* 202 */         return 2;
/* 203 */       if (meta == 4)
/* 204 */         return 3;
/* 205 */       if (meta == 2)
/* 206 */         return 1;
/* 207 */       if (meta == 8)
/* 208 */         return 0;
/*     */     }
/* 210 */     return -1;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.IMMoveHelper
 * JD-Core Version:    0.6.2
 */