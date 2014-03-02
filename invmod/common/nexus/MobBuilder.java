package invmod.common.nexus;

import invmod.common.mod_Invasion;
import invmod.common.entity.EntityIMBurrower;
import invmod.common.entity.EntityIMCreeper;
import invmod.common.entity.EntityIMLiving;
import invmod.common.entity.EntityIMPigEngy;
import invmod.common.entity.EntityIMSkeleton;
import invmod.common.entity.EntityIMSpider;
import invmod.common.entity.EntityIMThrower;
import invmod.common.entity.EntityIMZombie;
import net.minecraft.world.World;

public class MobBuilder {
	public EntityIMLiving createMobFromConstruct(EntityConstruct mobConstruct, World world, INexusAccess nexus)
  {
    EntityIMLiving mob = null;
    switch (1.$SwitchMap$invmod$common$nexus$IMEntityType[mobConstruct.getMobType().ordinal()])
    {
    case 1:
      EntityIMZombie zombie = new EntityIMZombie(world, nexus);
      zombie.setTexture(mobConstruct.getTexture());
      zombie.setFlavour(mobConstruct.getFlavour());
      zombie.setTier(mobConstruct.getTier());
      mob = zombie;
      break;
    case 2:
      EntityIMSpider spider = new EntityIMSpider(world, nexus);
      spider.setTexture(mobConstruct.getTexture());
      spider.setFlavour(mobConstruct.getFlavour());
      spider.setTier(mobConstruct.getTier());
      mob = spider;
      break;
    case 3:
      EntityIMSkeleton skeleton = new EntityIMSkeleton(world, nexus);
      mob = skeleton;
      break;
    case 4:
      EntityIMPigEngy pigEngy = new EntityIMPigEngy(world, nexus);
      mob = pigEngy;
      break;
    case 5:
      EntityIMThrower thrower = new EntityIMThrower(world, nexus);
      mob = thrower;
      break;
    case 6:
      EntityIMBurrower burrower = new EntityIMBurrower(world, nexus);
      mob = burrower;
      break;
    case 7:
      EntityIMCreeper creeper = new EntityIMCreeper(world, nexus);
      mob = creeper;
      break;
    default:
      mod_Invasion.log("Missing mob type in MobBuilder: " + mobConstruct.getMobType());
      mob = null;
    }

    return mob;
  }
}