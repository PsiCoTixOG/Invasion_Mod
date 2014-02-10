/*    */ package invmod.common.entity;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntityAIFlyingStrike extends EntityAIFollowParent
/*    */ {
/*    */   private EntityIMBird theEntity;
/*    */ 
/*    */   public EntityAIFlyingStrike(EntityIMBird entity)
/*    */   {
/* 12 */     this.theEntity = entity;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 21 */     return (this.theEntity.getAIGoal() == Goal.FLYING_STRIKE) || (this.theEntity.getAIGoal() == Goal.SWOOP);
/*    */   }
/*    */ 
/*    */   public boolean continueExecuting()
/*    */   {
/* 27 */     return shouldExecute();
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 33 */     if (this.theEntity.getAIGoal() == Goal.FLYING_STRIKE)
/* 34 */       doStrike();
/*    */   }
/*    */ 
/*    */   private void doStrike()
/*    */   {
/* 39 */     EntityLeashKnot target = this.theEntity.m();
/* 40 */     if (target == null)
/*    */     {
/* 42 */       this.theEntity.transitionAIGoal(Goal.NONE);
/* 43 */       return;
/*    */     }
/*    */ 
/* 47 */     float flyByChance = 1.0F;
/* 48 */     float tackleChance = 0.0F;
/* 49 */     float pickUpChance = 0.0F;
/* 50 */     if (this.theEntity.getClawsForward())
/*    */     {
/* 52 */       flyByChance = 0.5F;
/* 53 */       tackleChance = 100.0F;
/* 54 */       pickUpChance = 1.0F;
/*    */     }
/*    */ 
/* 57 */     float pE = flyByChance + tackleChance + pickUpChance;
/* 58 */     float r = this.theEntity.q.s.nextFloat();
/* 59 */     if (r <= flyByChance / pE)
/*    */     {
/* 61 */       doFlyByAttack(target);
/* 62 */       this.theEntity.transitionAIGoal(Goal.STABILISE);
/* 63 */       this.theEntity.setClawsForward(false);
/*    */     }
/* 65 */     else if (r <= (flyByChance + tackleChance) / pE)
/*    */     {
/* 67 */       this.theEntity.transitionAIGoal(Goal.TACKLE_TARGET);
/* 68 */       this.theEntity.setClawsForward(false);
/*    */     }
/*    */     else
/*    */     {
/* 72 */       this.theEntity.transitionAIGoal(Goal.PICK_UP_TARGET);
/*    */     }
/*    */   }
/*    */ 
/*    */   private void doFlyByAttack(EntityLeashKnot entity)
/*    */   {
/* 78 */     this.theEntity.attackEntityAsMob(entity, 5);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIFlyingStrike
 * JD-Core Version:    0.6.2
 */