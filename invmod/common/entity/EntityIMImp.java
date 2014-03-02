package invmod.common.entity;

import invmod.common.nexus.INexusAccess;
import net.minecraft.world.World;

public class EntityIMImp extends EntityIMMob
{
  public EntityIMImp(World world, INexusAccess nexus)
  {
    super(world, nexus);

    setBaseMoveSpeedStat(0.075F);
    this.attackStrength = 3;
    setMaxHealthAndHealth(11.0F);
    setName("Imp");
    setGender(1);
    setJumpHeight(1);
    setCanClimb(true);
  }

  public EntityIMImp(World world)
  {
    this(world, null);
  }

  public String getSpecies()
  {
    return "Imp";
  }

  public int getTier()
  {
    return 1;
  }
}