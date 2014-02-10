/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.util.ComparatorEntityDistanceFrom;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import oe;
/*     */ 
/*     */ public class EntityAISimpleTarget extends EntityAIFollowParent
/*     */ {
/*     */   private final EntityIMLiving theEntity;
/*     */   private EntityLeashKnot targetEntity;
/*     */   private Class<? extends oe> targetClass;
/*     */   private int outOfLosTimer;
/*     */   private float distance;
/*     */   private boolean needsLos;
/*     */ 
/*     */   public EntityAISimpleTarget(EntityIMLiving entity, Class<? extends oe> targetType, float distance)
/*     */   {
/*  26 */     this(entity, targetType, distance, true);
/*     */   }
/*     */ 
/*     */   public EntityAISimpleTarget(EntityIMLiving entity, Class<? extends oe> targetType, float distance, boolean needsLoS)
/*     */   {
/*  31 */     this.theEntity = entity;
/*  32 */     this.targetClass = targetType;
/*  33 */     this.outOfLosTimer = 0;
/*  34 */     this.distance = distance;
/*  35 */     this.needsLos = needsLoS;
/*  36 */     setMutexBits(1);
/*     */   }
/*     */ 
/*     */   public EntityIMLiving getEntity()
/*     */   {
/*  41 */     return this.theEntity;
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  50 */     if (this.targetClass == CallableItemName.class)
/*     */     {
/*  52 */       CallableItemName entityplayer = this.theEntity.q.b(this.theEntity, this.distance);
/*  53 */       if (isValidTarget(entityplayer))
/*     */       {
/*  55 */         this.targetEntity = entityplayer;
/*  56 */         return true;
/*     */       }
/*     */     }
/*     */ 
/*  60 */     List list = this.theEntity.q.a(this.targetClass, this.theEntity.E.b(this.distance, this.distance / 2.0F, this.distance));
/*  61 */     Comparator comp = new ComparatorEntityDistanceFrom(this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ);
/*  62 */     Collections.sort(list, comp);
/*     */ 
/*  65 */     boolean foundEntity = false;
/*  66 */     while (list.size() > 0)
/*     */     {
/*  68 */       EntityLeashKnot entity = (EntityLeashKnot)list.remove(list.size() - 1);
/*  69 */       if (isValidTarget(entity))
/*     */       {
/*  71 */         this.targetEntity = entity;
/*  72 */         return true;
/*     */       }
/*     */     }
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  84 */     EntityLeashKnot entityliving = this.theEntity.m();
/*  85 */     if (entityliving == null) {
/*  86 */       return false;
/*     */     }
/*  88 */     if (!entityliving.S()) {
/*  89 */       return false;
/*     */     }
/*  91 */     if (this.theEntity.e(entityliving) > this.distance * this.distance) {
/*  92 */       return false;
/*     */     }
/*  94 */     if (this.needsLos)
/*     */     {
/*  96 */       if (!this.theEntity.l().a(entityliving))
/*     */       {
/*  98 */         if (++this.outOfLosTimer > 60) {
/*  99 */           return false;
/*     */         }
/*     */       }
/*     */       else {
/* 103 */         this.outOfLosTimer = 0;
/*     */       }
/*     */     }
/*     */ 
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/* 116 */     this.theEntity.d(this.targetEntity);
/* 117 */     this.outOfLosTimer = 0;
/*     */   }
/*     */ 
/*     */   public void resetTask()
/*     */   {
/* 126 */     this.theEntity.d(null);
/*     */   }
/*     */ 
/*     */   public Class<? extends oe> getTargetType()
/*     */   {
/* 131 */     return this.targetClass;
/*     */   }
/*     */ 
/*     */   public float getAggroRange()
/*     */   {
/* 136 */     return this.distance;
/*     */   }
/*     */ 
/*     */   protected void setTarget(EntityLeashKnot entity)
/*     */   {
/* 141 */     this.targetEntity = entity;
/*     */   }
/*     */ 
/*     */   protected boolean isValidTarget(EntityLeashKnot entity)
/*     */   {
/* 146 */     if (entity == null) {
/* 147 */       return false;
/*     */     }
/* 149 */     if (entity == this.theEntity) {
/* 150 */       return false;
/*     */     }
/* 152 */     if (!entity.S()) {
/* 153 */       return false;
/*     */     }
/*     */ 
/* 167 */     if ((this.needsLos) && (!this.theEntity.l().a(entity))) {
/* 168 */       return false;
/*     */     }
/* 170 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAISimpleTarget
 * JD-Core Version:    0.6.2
 */