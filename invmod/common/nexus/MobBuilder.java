/*    */ package invmod.common.nexus;
/*    */ 
/*    */ import invmod.common.entity.EntityIMBurrower;
/*    */ import invmod.common.entity.EntityIMCreeper;
/*    */ import invmod.common.entity.EntityIMLiving;
/*    */ import invmod.common.entity.EntityIMPigEngy;
/*    */ import invmod.common.entity.EntityIMSkeleton;
/*    */ import invmod.common.entity.EntityIMSpider;
/*    */ import invmod.common.entity.EntityIMThrower;
/*    */ import invmod.common.entity.EntityIMZombie;
/*    */ import invmod.common.mod_Invasion;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class MobBuilder
/*    */ {
/*    */   public EntityIMLiving createMobFromConstruct(EntityConstruct mobConstruct, ColorizerGrass world, INexusAccess nexus)
/*    */   {
/* 26 */     EntityIMLiving mob = null;
/* 27 */     switch (1.$SwitchMap$invmod$common$nexus$IMEntityType[mobConstruct.getMobType().ordinal()])
/*    */     {
/*    */     case 1:
/* 30 */       EntityIMZombie zombie = new EntityIMZombie(world, nexus);
/* 31 */       zombie.setTexture(mobConstruct.getTexture());
/* 32 */       zombie.setFlavour(mobConstruct.getFlavour());
/* 33 */       zombie.setTier(mobConstruct.getTier());
/* 34 */       mob = zombie;
/* 35 */       break;
/*    */     case 2:
/* 37 */       EntityIMSpider spider = new EntityIMSpider(world, nexus);
/* 38 */       spider.setTexture(mobConstruct.getTexture());
/* 39 */       spider.setFlavour(mobConstruct.getFlavour());
/* 40 */       spider.setTier(mobConstruct.getTier());
/* 41 */       mob = spider;
/* 42 */       break;
/*    */     case 3:
/* 44 */       EntityIMSkeleton skeleton = new EntityIMSkeleton(world, nexus);
/* 45 */       mob = skeleton;
/* 46 */       break;
/*    */     case 4:
/* 48 */       EntityIMPigEngy pigEngy = new EntityIMPigEngy(world, nexus);
/* 49 */       mob = pigEngy;
/* 50 */       break;
/*    */     case 5:
/* 52 */       EntityIMThrower thrower = new EntityIMThrower(world, nexus);
/* 53 */       mob = thrower;
/* 54 */       break;
/*    */     case 6:
/* 56 */       EntityIMBurrower burrower = new EntityIMBurrower(world, nexus);
/* 57 */       mob = burrower;
/* 58 */       break;
/*    */     case 7:
/* 60 */       EntityIMCreeper creeper = new EntityIMCreeper(world, nexus);
/* 61 */       mob = creeper;
/* 62 */       break;
/*    */     default:
/* 64 */       mod_Invasion.log("Missing mob type in MobBuilder: " + mobConstruct.getMobType());
/* 65 */       mob = null;
/*    */     }
/*    */ 
/* 71 */     return mob;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.MobBuilder
 * JD-Core Version:    0.6.2
 */