package invmod.common.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class EntityAIStoop extends EntityAIBase
{
  private EntityIMLiving theEntity;
  private int updateTimer;
  private boolean stopStoop;

  public EntityAIStoop(EntityIMLiving entity)
  {
    this.theEntity = entity;
    this.stopStoop = true;
  }

  public boolean shouldExecute()
  {
    if (--this.updateTimer <= 0)
    {
      this.updateTimer = 10;
      if (this.theEntity.worldObj.getBlockMaterial(this.theEntity.getXCoord(), this.theEntity.getYCoord() + 2, this.theEntity.getZCoord()).blocksMovement()) {
        return true;
      }
    }
    return false;
  }

  public boolean continueExecuting()
  {
    return !this.stopStoop;
  }

  public void startExecuting()
  {
    this.theEntity.setSneaking(true);
    this.stopStoop = false;
  }

  public void updateTask()
  {
    if (--this.updateTimer <= 0)
    {
      this.updateTimer = 10;
      if (!this.theEntity.worldObj.getBlockMaterial(this.theEntity.getXCoord(), this.theEntity.getYCoord() + 2, this.theEntity.getZCoord()).blocksMovement())
      {
        this.theEntity.setSneaking(false);
        this.stopStoop = true;
      }
    }
  }
}