/*     */ package invmod.common.entity;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import oe;
/*     */ import pr;
/*     */ 
/*     */ public class EntityAIMoveToEntity<T extends oe> extends pr
/*     */ {
/*     */   private EntityIMLiving theEntity;
/*     */   private T targetEntity;
/*     */   private Class<? extends T> targetClass;
/*     */   private boolean targetMoves;
/*     */   private double lastX;
/*     */   private double lastY;
/*     */   private double lastZ;
/*     */   private int pathRequestTimer;
/*     */   private int pathFailedCount;
/*     */ 
/*     */   public EntityAIMoveToEntity(EntityIMLiving entity)
/*     */   {
/*  20 */     this(entity, EntityLeashKnot.class);
/*     */   }
/*     */ 
/*     */   public EntityAIMoveToEntity(EntityIMLiving entity, Class<? extends T> target)
/*     */   {
/*  25 */     this.targetClass = target;
/*  26 */     this.theEntity = entity;
/*  27 */     this.targetMoves = false;
/*  28 */     this.pathRequestTimer = 0;
/*  29 */     this.pathFailedCount = 0;
/*  30 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  36 */     if (--this.pathRequestTimer <= 0)
/*     */     {
/*  38 */       EntityLeashKnot target = this.theEntity.m();
/*  39 */       if ((target != null) && (this.targetClass.isAssignableFrom(this.theEntity.m().getClass())))
/*     */       {
/*  41 */         this.targetEntity = ((EntityLeashKnot)this.targetClass.cast(target));
/*  42 */         return true;
/*     */       }
/*     */     }
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  51 */     EntityLeashKnot target = this.theEntity.m();
/*  52 */     if ((target != null) && (target == this.targetEntity)) {
/*  53 */       return true;
/*     */     }
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  61 */     this.targetMoves = true;
/*  62 */     setPath();
/*     */   }
/*     */ 
/*     */   public void resetTask()
/*     */   {
/*  68 */     this.targetMoves = false;
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  74 */     if ((--this.pathRequestTimer <= 0) && (!this.theEntity.getNavigatorNew().isWaitingForTask()) && (this.targetMoves) && (this.targetEntity.getDistanceSq(this.lastX, this.lastY, this.lastZ) > 1.8D))
/*     */     {
/*  76 */       setPath();
/*     */     }
/*  78 */     if (this.pathFailedCount > 3)
/*     */     {
/*  80 */       this.theEntity.getMoveHelper().a(this.targetEntity.posX, this.targetEntity.posY, this.targetEntity.posZ, this.theEntity.getMoveSpeedStat());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setTargetMoves(boolean flag)
/*     */   {
/*  86 */     this.targetMoves = flag;
/*     */   }
/*     */ 
/*     */   protected EntityIMLiving getEntity()
/*     */   {
/*  91 */     return this.theEntity;
/*     */   }
/*     */ 
/*     */   protected T getTarget()
/*     */   {
/*  96 */     return this.targetEntity;
/*     */   }
/*     */ 
/*     */   protected void setPath()
/*     */   {
/* 101 */     if (this.theEntity.getNavigatorNew().tryMoveToEntity(this.targetEntity, 0.0F, this.theEntity.getMoveSpeedStat()))
/*     */     {
/* 103 */       if (this.theEntity.getNavigatorNew().getLastPathDistanceToTarget() > 3.0F)
/*     */       {
/* 105 */         this.pathRequestTimer = (30 + this.theEntity.q.s.nextInt(10));
/* 106 */         if (this.theEntity.getNavigatorNew().getPath().getCurrentPathLength() > 2)
/* 107 */           this.pathFailedCount = 0;
/*     */         else
/* 109 */           this.pathFailedCount += 1;
/*     */       }
/*     */       else
/*     */       {
/* 113 */         this.pathRequestTimer = (10 + this.theEntity.q.s.nextInt(10));
/* 114 */         this.pathFailedCount = 0;
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 119 */       this.pathFailedCount += 1;
/* 120 */       this.pathRequestTimer = (40 * this.pathFailedCount + this.theEntity.q.s.nextInt(10));
/*     */     }
/*     */ 
/* 123 */     this.lastX = this.targetEntity.posX;
/* 124 */     this.lastY = this.targetEntity.posY;
/* 125 */     this.lastZ = this.targetEntity.posZ;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIMoveToEntity
 * JD-Core Version:    0.6.2
 */