package invmod.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityAIThrowerKillEntity<T extends EntityLivingBase> extends EntityAIKillEntity<T> {
	private boolean melee;
	private int ammo;
	private float attackRangeSq;
	private float launchSpeed;
	private final EntityIMThrower theEntity;

	public EntityAIThrowerKillEntity(EntityIMThrower entity, Class<? extends T> targetClass, int attackDelay, float throwRange, float launchSpeed, int ammo) {
		super(entity, targetClass, attackDelay);
		this.attackRangeSq = (throwRange * throwRange);
		this.launchSpeed = launchSpeed;
		this.ammo = ammo;
		this.theEntity = entity;
	}

	protected void attackEntity(Entity target) {
		if (this.melee) {
			setAttackTime(getAttackDelay());
			super.attackEntity(target);
		} else {
			this.ammo -= 1;
			setAttackTime(getAttackDelay() * 2);
			this.theEntity.throwBoulder(target.posX, target.posY, target.posZ);
		}
	}

	protected boolean canAttackEntity(Entity target) {
		this.melee = super.canAttackEntity(target);
		if (this.melee) {
			return true;
		}
		if ((!this.theEntity.canThrow()) || (this.ammo == 0)) {
			return false;
		}

		double dX = this.theEntity.posX - target.posX;
		double dZ = this.theEntity.posZ - target.posZ;
		double dXY = MathHelper.sqrt_double(dX * dX + dZ * dZ);
		return (getAttackTime() <= 0) && (this.theEntity.getEntitySenses().canSee(target)) && (0.025D * dXY / (this.launchSpeed * this.launchSpeed) <= 1.0D);
	}
}