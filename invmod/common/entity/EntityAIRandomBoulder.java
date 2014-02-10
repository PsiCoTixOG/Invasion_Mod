/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.nexus.INexusAccess;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*    */ 
/*    */ public class EntityAIRandomBoulder extends EntityAIFollowParent
/*    */ {
/*    */   private final EntityIMThrower theEntity;
/*    */   private int randomAmmo;
/*    */   private int timer;
/*    */ 
/*    */   public EntityAIRandomBoulder(EntityIMThrower entity, int ammo)
/*    */   {
/* 14 */     this.theEntity = entity;
/* 15 */     this.randomAmmo = ammo;
/* 16 */     this.timer = 180;
/*    */   }
/*    */ 
/*    */   public boolean shouldExecute()
/*    */   {
/* 22 */     if ((this.theEntity.getNexus() != null) && (this.randomAmmo > 0) && (this.theEntity.canThrow()))
/*    */     {
/* 24 */       if (--this.timer <= 0)
/* 25 */         return true;
/*    */     }
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean isInterruptible()
/*    */   {
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   public void startExecuting()
/*    */   {
/* 39 */     this.randomAmmo -= 1;
/* 40 */     this.timer = 240;
/* 41 */     INexusAccess nexus = this.theEntity.getNexus();
/* 42 */     int d = (int)(this.theEntity.findDistanceToNexus() * 0.37D);
/* 43 */     this.theEntity.throwBoulder(nexus.getXCoord() - d + this.theEntity.aC().nextInt(2 * d), nexus.getYCoord() - 5 + this.theEntity.aC().nextInt(10), nexus.getZCoord() - d + this.theEntity.aC().nextInt(2 * d));
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityAIRandomBoulder
 * JD-Core Version:    0.6.2
 */