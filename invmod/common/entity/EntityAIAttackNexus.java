/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.nexus.TileEntityNexus;
/*     */ import invmod.common.util.CoordsInt;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityAIAttackNexus extends EntityAIFollowParent
/*     */ {
/*     */   private EntityIMLiving theEntity;
/*     */   private boolean attacked;
/*     */ 
/*     */   public EntityAIAttackNexus(EntityIMLiving par1EntityLiving)
/*     */   {
/*  16 */     this.theEntity = par1EntityLiving;
/*  17 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */   public boolean shouldExecute()
/*     */   {
/*  24 */     if ((this.theEntity.attackTime == 0) && (this.theEntity.getAIGoal() == Goal.BREAK_NEXUS) && (this.theEntity.findDistanceToNexus() > 4.0D))
/*     */     {
/*  26 */       this.theEntity.attackTime = 5;
/*  27 */       return false;
/*     */     }
/*     */ 
/*  30 */     return isNexusInRange();
/*     */   }
/*     */ 
/*     */   public void startExecuting()
/*     */   {
/*  36 */     this.theEntity.attackTime = 40;
/*     */   }
/*     */ 
/*     */   public boolean continueExecuting()
/*     */   {
/*  42 */     return !this.attacked;
/*     */   }
/*     */ 
/*     */   public void updateTask()
/*     */   {
/*  48 */     if (this.theEntity.attackTime == 0)
/*     */     {
/*  50 */       if (isNexusInRange())
/*     */       {
/*  52 */         this.theEntity.getNexus().attackNexus(2);
/*     */       }
/*  54 */       this.attacked = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void resetTask()
/*     */   {
/*  61 */     this.attacked = false;
/*     */   }
/*     */ 
/*     */   private boolean isNexusInRange()
/*     */   {
/*  67 */     CoordsInt size = this.theEntity.getCollideSize();
/*  68 */     int x = this.theEntity.getXCoord();
/*  69 */     int y = this.theEntity.getYCoord();
/*  70 */     int z = this.theEntity.getZCoord();
/*  71 */     for (int i = 0; i < size.getYCoord(); i++)
/*     */     {
/*  74 */       for (int j = 0; j < size.getXCoord(); j++)
/*     */       {
/*  76 */         if (this.theEntity.q.a(x + j, y, z - 1) == mod_Invasion.blockNexus.cF)
/*     */         {
/*  78 */           if (isCorrectNexus(x + j, y, z - 1)) {
/*  79 */             return true;
/*     */           }
/*     */         }
/*  82 */         if (this.theEntity.q.a(x + j, y, z + 1 + size.getZCoord()) == mod_Invasion.blockNexus.cF)
/*     */         {
/*  84 */           if (isCorrectNexus(x + j, y, z + 1 + size.getZCoord())) {
/*  85 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*  90 */       for (int j = 0; j < size.getZCoord(); j++)
/*     */       {
/*  92 */         if (this.theEntity.q.a(x - 1, y, z + j) == mod_Invasion.blockNexus.cF)
/*     */         {
/*  94 */           if (isCorrectNexus(x - 1, y, z + j)) {
/*  95 */             return true;
/*     */           }
/*     */         }
/*  98 */         if (this.theEntity.q.a(x + 1 + size.getXCoord(), y, z + j) == mod_Invasion.blockNexus.cF)
/*     */         {
/* 100 */           if (isCorrectNexus(x + 1 + size.getXCoord(), y, z + j)) {
/* 101 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 107 */     for (int i = 0; i < size.getXCoord(); i++)
/*     */     {
/* 109 */       for (int j = 0; j < size.getZCoord(); j++)
/*     */       {
/* 111 */         if (this.theEntity.q.a(x + i, y + 1 + size.getYCoord(), z + j) == mod_Invasion.blockNexus.cF)
/*     */         {
/* 113 */           if (isCorrectNexus(x + i, y + 1 + size.getYCoord(), z + j)) {
/* 114 */             return true;
/*     */           }
/*     */         }
/* 117 */         if (this.theEntity.q.a(x + i, y - 1, z + j) == mod_Invasion.blockNexus.cF)
/*     */         {
/* 119 */           if (isCorrectNexus(x + i, y - 1, z + j)) {
/* 120 */             return true;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isCorrectNexus(int x, int y, int z)
/*     */   {
/* 130 */     INexusAccess nexus = (TileEntityNexus)this.theEntity.q.r(x, y, z);
/* 131 */     if ((nexus != null) && (nexus == this.theEntity.getNexus())) {
/* 132 */       return true;
/*     */     }
/* 134 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIAttackNexus
 * JD-Core Version:    0.6.2
 */