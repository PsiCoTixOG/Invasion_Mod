/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.pathfinding.PathNavigate;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntityAILayEgg extends EntityAIFollowParent
/*    */ {
/*    */   private static final int EGG_LAY_TIME = 45;
/*    */   private static final int INITIAL_EGG_DELAY = 25;
/*    */   private static final int NEXT_EGG_DELAY = 230;
/*    */   private static final int EGG_HATCH_TIME = 125;
/*    */   private static final int EGG_HP = 12;
/*    */   private EntityIMLiving theEntity;
/*    */   private int time;
/*    */   private boolean isLaying;
/*    */   private int eggCount;
/*    */ 
/*    */   public EntityAILayEgg(EntityIMLiving entity, int eggs)
/*    */   {
/* 10 */     this.theEntity = entity;
/* 11 */     this.eggCount = eggs;
/* 12 */     this.isLaying = false;
/*    */   }
/*    */ 
/*    */   public void addEggs(int eggs)
/*    */   {
/* 17 */     this.eggCount += eggs;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 23 */     if ((this.theEntity.getAIGoal() == Goal.TARGET_ENTITY) && (this.eggCount > 0) && (this.theEntity.l().a(this.theEntity.m()))) {
/* 24 */       return true;
/*    */     }
/* 26 */     return false;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 32 */     this.time = 25;
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 38 */     this.time -= 1;
/* 39 */     if (this.time <= 0)
/*    */     {
/* 41 */       if (!this.isLaying)
/*    */       {
/* 43 */         this.isLaying = true;
/* 44 */         this.time = 45;
/* 45 */         setMutexBits(1);
/*    */       }
/*    */       else
/*    */       {
/* 49 */         this.isLaying = false;
/* 50 */         this.eggCount -= 1;
/* 51 */         this.time = 230;
/* 52 */         setMutexBits(0);
/* 53 */         layEgg();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private void layEgg()
/*    */   {
/*    */     nm[] contents;
/*    */     nm[] contents;
/* 61 */     if ((this.theEntity instanceof ISpawnsOffspring))
/* 62 */       contents = ((ISpawnsOffspring)this.theEntity).getOffspring(null);
/*    */     else {
/* 64 */       contents = null;
/*    */     }
/* 66 */     this.theEntity.q.d(new EntityIMEgg(this.theEntity, contents, 125, 12));
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAILayEgg
 * JD-Core Version:    0.6.2
 */