package invmod.common.entity;

import invmod.common.nexus.INexusAccess;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityIMSkeleton extends EntityIMMob
{
  private static final ItemStack defaultHeldItem = new ItemStack(Item.bow, 1);

  public EntityIMSkeleton(World world)
  {
    this(world, null);
  }

  public EntityIMSkeleton(World world, INexusAccess nexus)
  {
    super(world, nexus);
    setBurnsInDay(true);
    setMaxHealthAndHealth(12.0F);
    setBurnsInDay(true);
    setName("Skeleton");
    setGender(0);
    setBaseMoveSpeedStat(0.21F);

    this.c.addTask(0, new EntityAIKillWithArrow(this, EntityPlayer.class, 65, 16.0F));
    this.c.addTask(1, new EntityAIKillWithArrow(this, EntityLiving.class, 65, 16.0F));
    this.c.addTask(2, new EntityAIAttackNexus(this));
    this.c.addTask(3, new EntityAIGoToNexus(this));
    this.c.addTask(4, new EntityAIWanderIM(this));
    this.c.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.c.addTask(5, new EntityAILookIdle(this));
    this.d.addTask(1, new EntityAIHurtByTarget(this, false));
    this.d.addTask(2, new EntityAISimpleTarget(this, EntityPlayer.class, 16.0F, true));

    this.c.addTask(0, new EntityAIRallyBehindEntity(this, EntityIMCreeper.class, 4.0F));
    this.c.addTask(8, new EntityAIWatchClosest(this, EntityIMCreeper.class, 12.0F));
    this.d.addTask(0, new EntityAISimpleTarget(this, EntityPlayer.class, 6.0F, true));
    this.d.addTask(1, new EntityAILeaderTarget(this, EntityIMCreeper.class, 10.0F, true));
  }

  protected String getLivingSound()
  {
    return "mob.skeleton";
  }

  protected String getHurtSound()
  {
    return "mob.skeletonhurt";
  }

  protected String getDeathSound()
  {
    return "mob.skeletonhurt";
  }

  public void writeEntityToNBT(NBTTagCompound nbttagcompound)
  {
    super.writeEntityToNBT(nbttagcompound);
  }

  public void readEntityFromNBT(NBTTagCompound nbttagcompound)
  {
    super.readEntityFromNBT(nbttagcompound);
  }

  public String getSpecies()
  {
    return "Skeleton";
  }

  public int getTier()
  {
    return 2;
  }

  public String toString()
  {
    return "EntityIMSkeleton#";
  }

  protected int getDropItemId()
  {
    return Item.arrow.itemID;
  }

  protected void dropFewItems(boolean flag, int bonus)
  {
    super.dropFewItems(flag, bonus);
    int i = this.rand.nextInt(3);
    for (int j = 0; j < i; j++)
    {
      dropItem(Item.arrow.itemID, 1);
    }

    i = this.rand.nextInt(3);
    for (int k = 0; k < i; k++)
    {
      dropItem(Item.bone.itemID, 1);
    }
  }

  public ItemStack getHeldItem()
  {
    return defaultHeldItem;
  }
}