/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.nexus.INexusAccess;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public abstract class EntityIMMob extends EntityIMLiving
/*    */ {
/*    */   public EntityIMMob(ColorizerGrass world)
/*    */   {
/* 10 */     super(world, null);
/*    */   }
/*    */ 
/*    */   public EntityIMMob(ColorizerGrass world, INexusAccess nexus)
/*    */   {
/* 15 */     super(world, nexus);
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMMob
 * JD-Core Version:    0.6.2
 */