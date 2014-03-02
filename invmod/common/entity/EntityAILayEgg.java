package invmod.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAILayEgg extends EntityAIBase {
	private static final int EGG_LAY_TIME = 45;
	private static final int INITIAL_EGG_DELAY = 25;
	private static final int NEXT_EGG_DELAY = 230;
	private static final int EGG_HATCH_TIME = 125;
	private static final int EGG_HP = 12;
	private EntityIMLiving theEntity;
	private int time;
	private boolean isLaying;
	private int eggCount;

	public EntityAILayEgg(EntityIMLiving entity, int eggs) {
		this.theEntity = entity;
		this.eggCount = eggs;
		this.isLaying = false;
	}

	public void addEggs(int eggs) {
		this.eggCount += eggs;
	}

	public boolean shouldExecute() {
		if ((this.theEntity.getAIGoal() == Goal.TARGET_ENTITY) && (this.eggCount > 0) && (this.theEntity.getEntitySenses().canSee(this.theEntity.getAttackTarget()))) {
			return true;
		}
		return false;
	}

	public void startExecuting() {
		this.time = 25;
	}

	public void updateTask() {
		this.time -= 1;
		if (this.time <= 0) {
			if (!this.isLaying) {
				this.isLaying = true;
				this.time = 45;
				setMutexBits(1);
			} else {
				this.isLaying = false;
				this.eggCount -= 1;
				this.time = 230;
				setMutexBits(0);
				layEgg();
			}
		}
	}

	private void layEgg() {
		Entity[] contents;
		if ((this.theEntity instanceof ISpawnsOffspring))
			contents = ((ISpawnsOffspring) this.theEntity).getOffspring(null);
		else {
			contents = null;
		}
		this.theEntity.worldObj.spawnEntityInWorld(new EntityIMEgg(this.theEntity, contents, 125, 12));
	}
}