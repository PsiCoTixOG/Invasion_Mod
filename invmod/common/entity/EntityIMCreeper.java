package invmod.common.entity;

import invmod.common.INotifyTask;
import invmod.common.mod_Invasion;
import invmod.common.nexus.INexusAccess;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//NOOB HAUS  this.c.addTask(0, new EntityAISwimming(this));   c = this.tasks...
//Noob HAUS  this.d.addTask(2, new EntityAIHurtByTarget(this, false));   d = this.targetTasks

public class EntityIMCreeper extends EntityIMMob implements ILeader
{
  private int timeSinceIgnited;
  private int lastActiveTime;
  private boolean explosionDeath;
  private boolean commitToExplode;
  private int explodeDirection;

  public EntityIMCreeper(World world)
  {
    this(world, null);
  }

  public EntityIMCreeper(World world, INexusAccess nexus)
  {
    super(world, nexus);
    this.setName("Creeper");
    this.setGender(0);
    setBaseMoveSpeedStat(0.21F);
    setMaxHealthAndHealth(20.0F);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAICreeperIMSwell(this));
    this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 0.25D, 0.300000011920929D));
    this.tasks.addTask(3, new EntityAIKillEntity(this, EntityLiving.class, 40));
    this.tasks.addTask(4, new EntityAIGoToNexus(this));
    this.tasks.addTask(5, new EntityAIWanderIM(this));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 4.8F));
    this.tasks.addTask(6, new EntityAILookIdle(this));
    this.targetTasks.addTask(0, new EntityAITargetRetaliate(this, EntityLiving.class, 12.0F));
    this.targetTasks.addTask(1, new EntityAISimpleTarget(this, EntityPlayer.class, 4.8F, true));
    this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
  }

  public void updateAITick()
  {
    super.updateAITick();
  }

  public boolean isAIEnabled()
  {
    return true;
  }

  public boolean onPathBlocked(Path path, INotifyTask notifee)
  {
    if (!path.isFinished())
    {
      PathNode node = path.getPathPointFromIndex(path.getCurrentPathIndex());
      double dX = node.xCoord + 0.5D - this.posX;
      double dZ = node.zCoord + 0.5D - this.posZ;
      float facing = (float)(Math.atan2(dZ, dX) * 180.0D / 3.141592653589793D) - 90.0F;
      if (facing < 0.0F)
      {
        facing += 360.0F;
      }
      facing %= 360.0F;

      if ((facing >= 45.0F) && (facing < 135.0F))
        this.explodeDirection = 1;
      else if ((facing >= 135.0F) && (facing < 225.0F))
        this.explodeDirection = 3;
      else if ((facing >= 225.0F) && (facing < 315.0F))
        this.explodeDirection = 0;
      else {
        this.explodeDirection = 2;
      }
      setCreeperState(1);
      this.commitToExplode = true;
    }
    return false;
  }

  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)-1));
    this.dataWatcher.addObject(17, Byte.valueOf((byte)0));
  }

  public void onUpdate()
  {
    if (this.explosionDeath)
    {
      doExplosion();
      setDead();
    }
    else if (isEntityAlive())
    {
      this.lastActiveTime = this.timeSinceIgnited;
      int state = getCreeperState();

      if (state > 0)
      {
        if (this.commitToExplode) {
          getMoveHelper().setMoveTo(this.posX + invmod.common.util.CoordsInt.offsetAdjX[this.explodeDirection], this.posY, this.posZ + invmod.common.util.CoordsInt.offsetAdjZ[this.explodeDirection], 0.0D);
        }
        if (this.timeSinceIgnited == 0) {
          this.worldObj.playSoundAtEntity(this, "random.fuse", 1.0F, 0.5F);
        }
      }
      this.timeSinceIgnited += state;
      if (this.timeSinceIgnited < 0) {
        this.timeSinceIgnited = 0;
      }
      if (this.timeSinceIgnited >= 30)
      {
        this.timeSinceIgnited = 30;
        if (!this.worldObj.isRemote)
        {
          this.explosionDeath = true;
        }
      }
    }

    super.onUpdate();
  }

  public boolean isMartyr()
  {
    return this.explosionDeath;
  }

  protected String getHurtSound()
  {
    return "mob.creeper.say";
  }

  protected String getDeathSound()
  {
    return "mob.creeper.death";
  }

  public String getSpecies()
  {
    return "Creeper";
  }

  public int getTier()
  {
    return 2;
  }

  public void onDeath(DamageSource par1DamageSource)
  {
    super.onDeath(par1DamageSource);

    if ((par1DamageSource.getEntity() instanceof EntitySkeleton))
    {
      dropItem(Item.record13.itemID + this.rand.nextInt(10), 1);
    }
  }

  public boolean attackEntityAsMob(Entity par1Entity)
  {
    return true;
  }

  public float setCreeperFlashTime(float par1)
  {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * par1) / 28.0F;
  }

  public float getBlockPathCost(PathNode prevNode, PathNode node, IBlockAccess terrainMap)
  {
    int id = terrainMap.getBlockId(node.xCoord, node.yCoord, node.zCoord);
    if ((id > 0) && (!Block.blocksList[id].getBlocksMovement(terrainMap, node.xCoord, node.yCoord, node.zCoord)) && (id != mod_Invasion.blockNexus.blockID))
    {
      return prevNode.distanceTo(node) * 12.0F;
    }

    return super.getBlockPathCost(prevNode, node, terrainMap);
  }

  public boolean isBlockTypeDestructible(int id)
  {
    if ((id == 0) || (id == Block.bedrock.blockID) || (id == Block.ladder.blockID))
    {
      return false;
    }
    if ((id == Block.doorIron.blockID) || (id == Block.doorWood.blockID) || (id == Block.trapdoor.blockID))
    {
      return true;
    }
    if (Block.blocksList[id].blockMaterial.isSolid())
    {
      return true;
    }

    return false;
  }

  public String toString()
  {
    return "EntityIMCreeper#";
  }

  protected int getDropItemId()
  {
    return Item.gunpowder.itemID;
  }

  protected void doExplosion()
  {
    Explosion explosion = this.worldObj.createExplosion(this, this.posX, this.posY + 1.0D, this.posZ, 1.3F, true);
    int x = getXCoord();
    int y = getYCoord() + 1;
    int z = getZCoord();
    int direction = 0;
    float facing = this.rotationYaw % 360.0F;
    if (facing < 0.0F) {
      facing += 360.0F;
    }
    if ((facing >= 45.0F) && (facing < 135.0F))
      direction = 1;
    else if ((facing >= 135.0F) && (facing < 225.0F))
      direction = 3;
    else if ((facing >= 225.0F) && (facing < 315.0F))
      direction = 0;
    else {
      direction = 2;
    }
    for (int i = -1; i < 2; i++)
    {
      for (int j = -1; j < 2; j++)
      {
        float explosionStrength = 50.0F;
        for (int depth = 0; depth <= 3; depth++)
        {
          if ((depth == 3) && (((j != -1) && (j != 0)) || (i != 0))) {
            break;
          }
          int xOff = i;
          int zOff = i;
          if (direction == 0)
            xOff = depth;
          else if (direction == 1)
            xOff = -depth;
          else if (direction == 2)
            zOff = depth;
          else {
            zOff = -depth;
          }
          int id = this.worldObj.getBlockId(x + xOff, y + j, z + zOff);
          if ((id > 0) && (id != mod_Invasion.blockNexus.blockID))
          {
            explosionStrength -= Block.blocksList[id].getExplosionResistance(this);
            if (explosionStrength < 0.0F) {
              break;
            }
            Block.blocksList[id].dropBlockAsItemWithChance(this.worldObj, x + xOff, y + j, z + zOff, this.worldObj.getBlockMetadata(x + xOff, y + j, z + zOff), 0.5F, 0);
            this.worldObj.setBlock(x + xOff, y + j, z + zOff, 0);
            Block.blocksList[id].onBlockDestroyedByExplosion(this.worldObj, x + xOff, y + j, z + zOff, explosion);
          }
        }
      }
    }
  }

  public int getCreeperState()
  {
    return this.dataWatcher.getWatchableObjectByte(16);
  }

  public void setCreeperState(int state)
  {
    if ((this.commitToExplode) && (state != 1)) {
      return;
    }
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)state));
  }
}