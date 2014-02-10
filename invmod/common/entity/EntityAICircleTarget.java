/*    */ package invmod.common.entity;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntityAICircleTarget extends EntityAIFollowParent
/*    */ {
/*    */   private static final int ATTACK_SEARCH_TIME = 400;
/*    */   private EntityIMFlying theEntity;
/*    */   private int time;
/*    */   private int patienceTime;
/*    */   private int patience;
/*    */   private float preferredHeight;
/*    */   private float preferredRadius;
/*    */ 
/*    */   public EntityAICircleTarget(EntityIMFlying entity, int patience, float preferredHeight, float preferredRadius)
/*    */   {
/* 18 */     this.theEntity = entity;
/* 19 */     this.time = 0;
/* 20 */     this.patienceTime = 0;
/* 21 */     this.patience = patience;
/* 22 */     this.preferredHeight = preferredHeight;
/* 23 */     this.preferredRadius = preferredRadius;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 29 */     return (this.theEntity.getAIGoal() == Goal.STAY_AT_RANGE) && (this.theEntity.m() != null);
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 35 */     return ((this.theEntity.getAIGoal() == Goal.STAY_AT_RANGE) || (isWaitingForTransition())) && (this.theEntity.m() != null);
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 41 */     INavigationFlying nav = this.theEntity.getNavigatorNew();
/* 42 */     nav.setMovementType(INavigationFlying.MoveType.PREFER_FLYING);
/* 43 */     nav.setCirclingPath(this.theEntity.m(), this.preferredHeight, this.preferredRadius);
/* 44 */     this.time = 0;
/* 45 */     int extraTime = (int)(4.0F * nav.getDistanceToCirclingRadius());
/* 46 */     if (extraTime < 0) {
/* 47 */       extraTime = 0;
/*    */     }
/* 49 */     this.patienceTime = (extraTime + this.theEntity.q.s.nextInt(this.patience) + this.patience / 3);
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 55 */     this.time += 1;
/* 56 */     if (this.theEntity.getAIGoal() == Goal.STAY_AT_RANGE)
/*    */     {
/* 58 */       this.patienceTime -= 1;
/* 59 */       if (this.patienceTime <= 0)
/*    */       {
/* 61 */         this.theEntity.transitionAIGoal(Goal.FIND_ATTACK_OPPORTUNITY);
/* 62 */         this.patienceTime = 400;
/*    */       }
/*    */     }
/* 65 */     else if (isWaitingForTransition())
/*    */     {
/* 67 */       this.patienceTime -= 1;
/* 68 */       if (this.patienceTime > 0);
/*    */     }
/*    */   }
/*    */ 
/*    */   protected boolean isWaitingForTransition()
/*    */   {
/* 77 */     return (this.theEntity.getPrevAIGoal() == Goal.STAY_AT_RANGE) && (this.theEntity.getAIGoal() == Goal.FIND_ATTACK_OPPORTUNITY);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAICircleTarget
 * JD-Core Version:    0.6.2
 */