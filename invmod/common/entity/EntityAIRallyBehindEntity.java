package invmod.common.entity;

import net.minecraft.entity.EntityLiving;

public class EntityAIRallyBehindEntity<T extends EntityLiving, ILeader> extends EntityAIFollowEntity<T> {
	private static final float DEFAULT_FOLLOW_DISTANCE = 5.0F;

	public EntityAIRallyBehindEntity(EntityIMLiving entity, Class<T> leader) {
		this(entity, leader, 5.0F);
	}

	public EntityAIRallyBehindEntity(EntityIMLiving entity, Class<T> leader, float followDistance) {
		super(entity, leader, followDistance);
	}

	public boolean shouldExecute() {
		return (getEntity().readyToRally()) && (super.shouldExecute());
	}

	public boolean continueExecuting() {
		return (getEntity().readyToRally()) && (super.continueExecuting());
	}

	public void updateTask() {
		super.updateTask();
		if (getEntity().readyToRally()) {
			EntityLiving leader = (EntityLiving) getTarget();
			if (((ILeader) leader).isMartyr())
				rally(leader);
		}
	}

	protected void rally(T leader) {
		getEntity().rally(leader);
	}
}