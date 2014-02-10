/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.nexus.INexusAccess;
/*    */ import invmod.common.util.CoordsInt;
/*    */ import invmod.common.util.Distance;
/*    */ import invmod.common.util.IPosition;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntityAIGoToNexus extends EntityAIFollowParent
/*    */ {
/*    */   private EntityIMMob theEntity;
/*    */   private IPosition lastPathRequestPos;
/*    */   private int pathRequestTimer;
/*    */   private int pathFailedCount;
/*    */ 
/*    */   public EntityAIGoToNexus(EntityIMMob entity)
/*    */   {
/* 18 */     this.theEntity = entity;
/* 19 */     this.lastPathRequestPos = new CoordsInt(0, -128, 0);
/* 20 */     this.pathRequestTimer = 0;
/* 21 */     this.pathFailedCount = 0;
/* 22 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 28 */     if (this.theEntity.getAIGoal() == Goal.BREAK_NEXUS) {
/* 29 */       return true;
/*    */     }
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 37 */     setPathToNexus();
/*    */   }
/*    */ 
/*    */   public void updateTask()
/*    */   {
/* 43 */     if (this.pathFailedCount > 1) {
/* 44 */       wanderToNexus();
/*    */     }
/* 46 */     if ((this.theEntity.getNavigatorNew().noPath()) || (this.theEntity.getNavigatorNew().getStuckTime() > 40))
/* 47 */       setPathToNexus();
/*    */   }
/*    */ 
/*    */   private void setPathToNexus()
/*    */   {
/* 52 */     INexusAccess nexus = this.theEntity.getNexus();
/* 53 */     if ((nexus != null) && (this.pathRequestTimer-- <= 0))
/*    */     {
/* 55 */       boolean pathSet = false;
/* 56 */       double distance = this.theEntity.findDistanceToNexus();
/* 57 */       if (distance > 2000.0D)
/*    */       {
/* 59 */         pathSet = this.theEntity.getNavigatorNew().tryMoveTowardsXZ(nexus.getXCoord(), nexus.getZCoord(), 1, 6, 4, this.theEntity.getMoveSpeedStat());
/*    */       }
/* 61 */       else if (distance > 1.5D)
/*    */       {
/* 63 */         pathSet = this.theEntity.getNavigatorNew().tryMoveToXYZ(nexus.getXCoord(), nexus.getYCoord(), nexus.getZCoord(), 1.0F, this.theEntity.getMoveSpeedStat());
/*    */       }
/*    */ 
/* 66 */       if ((!pathSet) || ((this.theEntity.getNavigatorNew().getLastPathDistanceToTarget() > 3.0F) && (Distance.distanceBetween(this.lastPathRequestPos, this.theEntity) < 3.5D)))
/*    */       {
/* 68 */         this.pathFailedCount += 1;
/* 69 */         this.pathRequestTimer = (40 * this.pathFailedCount + this.theEntity.q.s.nextInt(10));
/*    */       }
/*    */       else
/*    */       {
/* 73 */         this.pathFailedCount = 0;
/* 74 */         this.pathRequestTimer = 20;
/*    */       }
/*    */ 
/* 77 */       this.lastPathRequestPos = new CoordsInt(this.theEntity.getXCoord(), this.theEntity.getYCoord(), this.theEntity.getZCoord());
/*    */     }
/*    */   }
/*    */ 
/*    */   private boolean pathTooShort()
/*    */   {
/* 83 */     Path path = this.theEntity.getNavigatorNew().getPath();
/* 84 */     if (path != null)
/*    */     {
/* 86 */       IPosition pos = path.getFinalPathPoint();
/* 87 */       return this.theEntity.getDistanceSq(pos.getXCoord(), pos.getYCoord(), pos.getZCoord()) < 4.0D;
/*    */     }
/* 89 */     return true;
/*    */   }
/*    */ 
/*    */   protected void wanderToNexus()
/*    */   {
/* 94 */     INexusAccess nexus = this.theEntity.getNexus();
/* 95 */     this.theEntity.getMoveHelper().a(nexus.getXCoord() + 0.5D, nexus.getYCoord(), nexus.getZCoord() + 0.5D, this.theEntity.getMoveSpeedStat());
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIGoToNexus
 * JD-Core Version:    0.6.2
 */