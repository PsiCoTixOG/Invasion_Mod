/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.nexus.INexusAccess;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntityIMImp extends EntityIMMob
/*    */ {
/*    */   public EntityIMImp(ColorizerGrass world, INexusAccess nexus)
/*    */   {
/* 12 */     super(world, nexus);
/*    */ 
/* 14 */     setBaseMoveSpeedStat(0.075F);
/* 15 */     this.attackStrength = 3;
/* 16 */     setMaxHealthAndHealth(11.0F);
/* 17 */     setName("Imp");
/* 18 */     setGender(1);
/* 19 */     setJumpHeight(1);
/* 20 */     setCanClimb(true);
/*    */   }
/*    */ 
/*    */   public EntityIMImp(ColorizerGrass world)
/*    */   {
/* 25 */     this(world, null);
/*    */   }
/*    */ 
/*    */   public String getSpecies()
/*    */   {
/* 35 */     return "Imp";
/*    */   }
/*    */ 
/*    */   public int getTier()
/*    */   {
/* 41 */     return 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMImp
 * JD-Core Version:    0.6.2
 */