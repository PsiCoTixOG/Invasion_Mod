package invmod.common.nexus;

import invmod.common.mod_Invasion;
import invmod.common.entity.AttackerAI;
import invmod.common.entity.EntityIMBolt;
import invmod.common.entity.EntityIMLiving;
import invmod.common.util.ComparatorEntityDistance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class TileEntityNexus extends TileEntity implements INexusAccess, IInventory {
	private static final long BIND_EXPIRE_TIME = 300000L;
	private IMWaveSpawner waveSpawner;
	private IMWaveBuilder waveBuilder;
	private ItemStack[] nexusItemStacks;
	private AxisAlignedBB boundingBoxToRadius;
	private HashMap<String, Long> boundPlayers;
	private List<EntityIMLiving> mobList;
	private AttackerAI attackerAI;
	private int activationTimer;
	private int currentWave;
	private int spawnRadius;
	private int nexusLevel;
	private int nexusKills;
	private int generation;
	private int cookTime;
	private int maxHp;
	private int hp;
	private int lastHp;
	private int mode;
	private int powerLevel;
	private int lastPowerLevel;
	private int powerLevelTimer;
	private int mobsLeftInWave;
	private int lastMobsLeftInWave;
	private int mobsToKillInWave;
	private int nextAttackTime;
	private int daysToAttack;
	private long lastWorldTime;
	private int zapTimer;
	private int errorState;
	private int tickCount;
	private int cleanupTimer;
	private long spawnerElapsedRestore;
	private long timer;
	private long waveDelayTimer;
	private long waveDelay;
	private boolean continuousAttack;
	private boolean mobsSorted;
	private boolean resumedFromNBT;

	public TileEntityNexus() {
		this(null);
	}

	public TileEntityNexus(World world) {
		this.worldObj = world;
		this.spawnRadius = 52;
		this.waveSpawner = new IMWaveSpawner(this, this.spawnRadius);
		this.waveBuilder = new IMWaveBuilder();
		this.nexusItemStacks = new ItemStack[2];
		this.boundingBoxToRadius = AxisAlignedBB.getBoundingBox(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
		this.boundingBoxToRadius.setBounds(this.xCoord - (this.spawnRadius + 10), this.yCoord - (this.spawnRadius + 40), this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), this.yCoord + (this.spawnRadius + 40), this.zCoord + (this.spawnRadius + 10));
		this.boundPlayers = new HashMap();
		this.mobList = new ArrayList();
		this.attackerAI = new AttackerAI(this);
		this.activationTimer = 0;
		this.cookTime = 0;
		this.currentWave = 0;
		this.nexusLevel = 1;
		this.nexusKills = 0;
		this.generation = 0;
		this.maxHp = (this.hp = this.lastHp = 100);
		this.mode = 0;
		this.powerLevelTimer = 0;
		this.powerLevel = 0;
		this.lastPowerLevel = 0;
		this.mobsLeftInWave = 0;
		this.nextAttackTime = 0;
		this.daysToAttack = 0;
		this.lastWorldTime = 0L;
		this.errorState = 0;
		this.tickCount = 0;
		this.timer = 0L;
		this.zapTimer = 0;
		this.cleanupTimer = 0;
		this.waveDelayTimer = -1L;
		this.waveDelay = 0L;
		this.continuousAttack = false;
		this.mobsSorted = false;
		this.resumedFromNBT = false;
	}

	public void updateEntity() {
		if (this.worldObj.isRemote) {
			return;
		}

		updateStatus();

		updateAI();

		if ((this.mode == 1) || (this.mode == 2) || (this.mode == 3)) {
			if (this.resumedFromNBT) {
				this.boundingBoxToRadius.setBounds(this.xCoord - (this.spawnRadius + 10), 0.0D, this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), 127.0D, this.zCoord + (this.spawnRadius + 10));
				if ((this.mode == 2) && (this.continuousAttack)) {
					if (resumeSpawnerContinuous()) {
						this.mobsLeftInWave = (this.lastMobsLeftInWave += acquireEntities());
						mod_Invasion.log("mobsLeftInWave: " + this.mobsLeftInWave);
						mod_Invasion.log("mobsToKillInWave: " + this.mobsToKillInWave);
					}
				} else {
					resumeSpawnerInvasion();
					acquireEntities();
				}

				this.attackerAI.onResume();

				this.resumedFromNBT = false;
			}

			try {
				this.tickCount += 1;
				if (this.tickCount == 60) {
					this.tickCount -= 60;
					bindPlayers();
					updateMobList();
				}

				if ((this.mode == 1) || (this.mode == 3))
					doInvasion(50);
				else if (this.mode == 2)
					doContinuous(50);
			} catch (WaveSpawnerException e) {
				mod_Invasion.log(e.getMessage());
				e.printStackTrace();
				stop();
			}
		}

		if (this.cleanupTimer++ > 40) {
			this.cleanupTimer = 0;
			if (this.worldObj.getBlockId(this.xCoord, this.yCoord, this.zCoord) != mod_Invasion.blockNexus.blockID) {
				mod_Invasion.setInvasionEnded(this);
				stop();
				invalidate();
				mod_Invasion.log("Stranded nexus entity trying to delete itself...");
			}
		}
	}

	public void emergencyStop() {
		mod_Invasion.log("Nexus manually stopped by command");
		stop();
		killAllMobs();
	}

	public void debugStatus() {
		mod_Invasion.broadcastToAll("Current Time: " + this.worldObj.getWorldTime());
		mod_Invasion.broadcastToAll("Time to next: " + this.nextAttackTime);
		mod_Invasion.broadcastToAll("Days to attack: " + this.daysToAttack);
		mod_Invasion.broadcastToAll("Mobs left: " + this.mobsLeftInWave);
		mod_Invasion.broadcastToAll("Mode: " + this.mode);
	}

	public void debugStartInvaion(int startWave) {
		mod_Invasion.tryGetInvasionPermission(this);
		startInvasion(startWave);
	}

	public void createBolt(int x, int y, int z, int t) {
		EntityIMBolt bolt = new EntityIMBolt(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, x + 0.5D, y + 0.5D, z + 0.5D, t, 1);
		this.worldObj.spawnEntityInWorld(bolt);
	}

	public boolean setSpawnRadius(int radius) {
		if ((!this.waveSpawner.isActive()) && (radius > 8)) {
			this.spawnRadius = radius;
			this.waveSpawner.setRadius(radius);
			this.boundingBoxToRadius.setBounds(this.xCoord - (this.spawnRadius + 10), 0.0D, this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), 127.0D, this.zCoord + (this.spawnRadius + 10));
			return true;
		}

		return false;
	}

	public void attackNexus(int damage) {
		this.hp -= damage;
		if (this.hp <= 0) {
			this.hp = 0;
			if (this.mode == 1) {
				theEnd();
			}
		}
		while (this.hp + 5 <= this.lastHp) {
			mod_Invasion.broadcastToAll("Nexus at " + (this.lastHp - 5) + " hp");
			this.lastHp -= 5;
		}
	}

	public void registerMobDied() {
		this.nexusKills += 1;
		this.mobsLeftInWave -= 1;
		if (this.mobsLeftInWave <= 0) {
			if (this.lastMobsLeftInWave > 0) {
				mod_Invasion.broadcastToAll("Nexus rift stable again!");
				mod_Invasion.broadcastToAll("Unleashing tapped energy...");
				this.lastMobsLeftInWave = this.mobsLeftInWave;
			}
			return;
		}
		while (this.mobsLeftInWave + this.mobsToKillInWave * 0.1F <= this.lastMobsLeftInWave) {
			mod_Invasion.broadcastToAll("Nexus rift stabilised to " + (100 - (int) (100 * this.mobsLeftInWave / this.mobsToKillInWave)) + "%");
			this.lastMobsLeftInWave = ((int) (this.lastMobsLeftInWave - this.mobsToKillInWave * 0.1F));
		}
	}

	public void registerMobClose() {
	}

	public boolean isActivating() {
		return (this.activationTimer > 0) && (this.activationTimer < 400);
	}

	public int getMode() {
		return this.mode;
	}

	public int getActivationTimer() {
		return this.activationTimer;
	}

	public int getSpawnRadius() {
		return this.spawnRadius;
	}

	public int getNexusKills() {
		return this.nexusKills;
	}

	public int getGeneration() {
		return this.generation;
	}

	public int getNexusLevel() {
		return this.nexusLevel;
	}

	public int getPowerLevel() {
		return this.powerLevel;
	}

	public int getCookTime() {
		return this.cookTime;
	}

	public int getNexusID() {
		return -1;
	}

	public int getXCoord() {
		return this.xCoord;
	}

	public int getYCoord() {
		return this.yCoord;
	}

	public int getZCoord() {
		return this.zCoord;
	}

	public World getWorld() {
		return this.worldObj;
	}

	public List<EntityIMLiving> getMobList() {
		return this.mobList;
	}

	public int getActivationProgressScaled(int i) {
		return this.activationTimer * i / 400;
	}

	public int getGenerationProgressScaled(int i) {
		return this.generation * i / 3000;
	}

	public int getCookProgressScaled(int i) {
		return this.cookTime * i / 1200;
	}

	public int getNexusPowerLevel() {
		return this.powerLevel;
	}

	public int getCurrentWave() {
		return this.currentWave;
	}

	public int getSizeInventory() {
		return this.nexusItemStacks.length;
	}

	public String getInvName() {
		return "Nexus";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.nexusItemStacks[i] = itemstack;
		if ((itemstack != null) && (itemstack.stackSize > getInventoryStackLimit())) {
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public ItemStack getStackInSlot(int i) {
		return this.nexusItemStacks[i];
	}

	public ItemStack decrStackSize(int i, int j) {
		if (this.nexusItemStacks[i] != null) {
			if (this.nexusItemStacks[i].stackSize <= j) {
				ItemStack itemstack = this.nexusItemStacks[i];
				this.nexusItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = this.nexusItemStacks[i].splitStack(j);
			if (this.nexusItemStacks[i].stackSize == 0) {
				this.nexusItemStacks[i] = null;
			}
			return itemstack1;
		}

		return null;
	}

	public void openChest() {
	}

	public void closeChest() {
	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	public void readFromNBT(NBTTagCompound nbttagcompound) {
		mod_Invasion.log("Restoring TileEntityNexus from NBT");
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		this.nexusItemStacks = new ItemStack[getSizeInventory()];
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if ((byte0 >= 0) && (byte0 < this.nexusItemStacks.length)) {
				this.nexusItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}

		}

		nbttaglist = nbttagcompound.getTagList("boundPlayers");
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			this.boundPlayers.put(((NBTTagCompound) nbttaglist.tagAt(i)).getString("name"), Long.valueOf(System.currentTimeMillis()));
			mod_Invasion.log("Added bound player: " + ((NBTTagCompound) nbttaglist.tagAt(i)).getString("name"));
		}

		this.activationTimer = nbttagcompound.getShort("activationTimer");
		this.mode = nbttagcompound.getInteger("mode");
		this.currentWave = nbttagcompound.getShort("currentWave");
		this.spawnRadius = nbttagcompound.getShort("spawnRadius");
		this.nexusLevel = nbttagcompound.getShort("nexusLevel");
		this.hp = nbttagcompound.getShort("hp");
		this.nexusKills = nbttagcompound.getInteger("nexusKills");
		this.generation = nbttagcompound.getShort("generation");
		this.powerLevel = nbttagcompound.getInteger("powerLevel");
		this.lastPowerLevel = nbttagcompound.getInteger("lastPowerLevel");
		this.nextAttackTime = nbttagcompound.getInteger("nextAttackTime");
		this.daysToAttack = nbttagcompound.getInteger("daysToAttack");
		this.continuousAttack = nbttagcompound.getBoolean("continuousAttack");

		this.boundingBoxToRadius.setBounds(this.xCoord - (this.spawnRadius + 10), this.yCoord - (this.spawnRadius + 40), this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), this.yCoord + (this.spawnRadius + 40), this.zCoord + (this.spawnRadius + 10));

		mod_Invasion.log("activationTimer = " + this.activationTimer);
		mod_Invasion.log("mode = " + this.mode);
		mod_Invasion.log("currentWave = " + this.currentWave);
		mod_Invasion.log("spawnRadius = " + this.spawnRadius);
		mod_Invasion.log("nexusLevel = " + this.nexusLevel);
		mod_Invasion.log("hp = " + this.hp);
		mod_Invasion.log("nexusKills = " + this.nexusKills);
		mod_Invasion.log("powerLevel = " + this.powerLevel);
		mod_Invasion.log("lastPowerLevel = " + this.lastPowerLevel);
		mod_Invasion.log("nextAttackTime = " + this.nextAttackTime);

		this.waveSpawner.setRadius(this.spawnRadius);
		if ((this.mode == 1) || (this.mode == 3) || ((this.mode == 2) && (this.continuousAttack))) {
			mod_Invasion.log("Nexus is active; flagging for restore");
			this.resumedFromNBT = true;
			this.spawnerElapsedRestore = nbttagcompound.getLong("spawnerElapsed");
			mod_Invasion.log("spawnerElapsed = " + this.spawnerElapsedRestore);
		}

		this.attackerAI.readFromNBT(nbttagcompound);
	}

	public void writeToNBT(NBTTagCompound nbttagcompound) {
		if (this.mode != 0) {
			mod_Invasion.setNexusUnloaded(this);
		}
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setShort("activationTimer", (short) this.activationTimer);
		nbttagcompound.setShort("currentWave", (short) this.currentWave);
		nbttagcompound.setShort("spawnRadius", (short) this.spawnRadius);
		nbttagcompound.setShort("nexusLevel", (short) this.nexusLevel);
		nbttagcompound.setShort("hp", (short) this.hp);
		nbttagcompound.setInteger("nexusKills", this.nexusKills);
		nbttagcompound.setShort("generation", (short) this.generation);
		nbttagcompound.setLong("spawnerElapsed", this.waveSpawner.getElapsedTime());
		nbttagcompound.setInteger("mode", this.mode);
		nbttagcompound.setInteger("powerLevel", this.powerLevel);
		nbttagcompound.setInteger("lastPowerLevel", this.lastPowerLevel);
		nbttagcompound.setInteger("nextAttackTime", this.nextAttackTime);
		nbttagcompound.setInteger("daysToAttack", this.daysToAttack);
		nbttagcompound.setBoolean("continuousAttack", this.continuousAttack);

		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < this.nexusItemStacks.length; i++) {
			if (this.nexusItemStacks[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.nexusItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);

		NBTTagList nbttaglist2 = new NBTTagList();
		for (Map.Entry entry : this.boundPlayers.entrySet()) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("name", (String) entry.getKey());
			nbttaglist2.appendTag(nbttagcompound1);
		}
		nbttagcompound.setTag("boundPlayers", nbttaglist2);

		this.attackerAI.writeToNBT(nbttagcompound);
	}

	public void askForRespawn(EntityIMLiving entity) {
		mod_Invasion.log("Stuck entity asking for respawn: " + entity.toString() + "  " + entity.posX + ", " + entity.posY + ", " + entity.posZ);
		this.waveSpawner.askForRespawn(entity);
	}

	public AttackerAI getAttackerAI() {
		return this.attackerAI;
	}

	protected void setActivationTimer(int i) {
		this.activationTimer = i;
	}

	protected void setNexusLevel(int i) {
		this.nexusLevel = i;
	}

	protected void setNexusKills(int i) {
		this.nexusKills = i;
	}

	protected void setGeneration(int i) {
		this.generation = i;
	}

	protected void setNexusPowerLevel(int i) {
		this.powerLevel = i;
	}

	protected void setCookTime(int i) {
		this.cookTime = i;
	}

	protected void setWave(int wave) {
		this.currentWave = wave;
	}

	private void startInvasion(int startWave) {
		this.boundingBoxToRadius.setBounds(this.xCoord - (this.spawnRadius + 10), this.yCoord - (this.spawnRadius + 40), this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), this.yCoord + (this.spawnRadius + 40), this.zCoord + (this.spawnRadius + 10));
		if ((this.mode == 2) && (this.continuousAttack)) {
			mod_Invasion.broadcastToAll("Can't activate nexus when already under attack!");
			return;
		}

		if ((this.mode == 0) || (this.mode == 2)) {
			if (this.waveSpawner.isReady()) {
				try {
					this.currentWave = startWave;
					this.waveSpawner.beginNextWave(this.currentWave);
					if (this.mode == 0)
						setMode(1);
					else {
						setMode(3);
					}
					bindPlayers();
					this.hp = this.maxHp;
					this.lastHp = this.maxHp;
					this.waveDelayTimer = -1L;
					this.timer = System.currentTimeMillis();
					mod_Invasion.broadcastToAll("The first wave is coming soon!");
					mod_Invasion.playGlobalSFX("invmod:rumble");
				} catch (WaveSpawnerException e) {
					stop();
					mod_Invasion.log(e.getMessage());
					mod_Invasion.broadcastToAll(e.getMessage());
				}
			} else {
				mod_Invasion.log("Wave spawner not in ready state");
			}
		} else {
			mod_Invasion.log("Tried to activate nexus while already active");
		}
	}

	private void startContinuousPlay() {
		this.boundingBoxToRadius.setBounds(this.xCoord - (this.spawnRadius + 10), 0.0D, this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), 127.0D, this.zCoord + (this.spawnRadius + 10));
		if ((this.mode == 4) && (this.waveSpawner.isReady()) && (mod_Invasion.tryGetInvasionPermission(this))) {
			setMode(2);
			this.hp = this.maxHp;
			this.lastHp = this.maxHp;
			this.lastPowerLevel = this.powerLevel;
			this.lastWorldTime = this.worldObj.getWorldTime();
			this.nextAttackTime = ((int) (this.lastWorldTime / 24000L * 24000L) + 14000);
			if ((this.lastWorldTime % 24000L > 12000L) && (this.lastWorldTime % 24000L < 16000L)) {
				mod_Invasion.broadcastToAll("The night looms around the nexus...");
			} else {
				mod_Invasion.broadcastToAll("Nexus activated and stable");
			}
		} else {
			mod_Invasion.broadcastToAll("Couldn't activate nexus");
		}
	}

	private void doInvasion(int elapsed) throws WaveSpawnerException {
		if (this.waveSpawner.isActive()) {
			if (this.hp <= 0) {
				theEnd();
			} else {
				generateFlux(1);
				if (this.waveSpawner.isWaveComplete()) {
					if (this.waveDelayTimer == -1L) {
						mod_Invasion.broadcastToAll("Wave " + this.currentWave + " almost complete!");
						mod_Invasion.playGlobalSFX("invmod:chime");
						this.waveDelayTimer = 0L;
						this.waveDelay = this.waveSpawner.getWaveRestTime();
					} else {
						this.waveDelayTimer += elapsed;
						if (this.waveDelayTimer > this.waveDelay) {
							this.currentWave += 1;
							mod_Invasion.broadcastToAll("Wave " + this.currentWave + " about to begin");
							this.waveSpawner.beginNextWave(this.currentWave);
							this.waveDelayTimer = -1L;
							mod_Invasion.playGlobalSFX("invmod:rumble");
							if (this.currentWave > this.nexusLevel) {
								this.nexusLevel = this.currentWave;
							}
						}
					}
				} else {
					this.waveSpawner.spawn(elapsed);
				}
			}
		}
	}

	private void doContinuous(int elapsed) {
		this.powerLevelTimer += elapsed;
		if (this.powerLevelTimer > 2200) {
			this.powerLevelTimer -= 2200;
			generateFlux(5 + (int) (5 * this.powerLevel / 1550.0F));
			if ((this.nexusItemStacks[0] == null) || (this.nexusItemStacks[0].getItem().itemID != mod_Invasion.itemDampingAgent.itemID)) {
				this.powerLevel += 1;
			}
		}

		if ((this.nexusItemStacks[0] != null) && (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemStrongDampingAgent.itemID)) {
			if ((this.powerLevel >= 0) && (!this.continuousAttack)) {
				this.powerLevel -= 1;
				if (this.powerLevel < 0) {
					stop();
				}
			}
		}
		if (!this.continuousAttack) {
			long currentTime = this.worldObj.getWorldTime();
			int timeOfDay = (int) (this.lastWorldTime % 24000L);
			if ((timeOfDay < 12000) && (currentTime % 24000L >= 12000L) && (currentTime + 12000L > this.nextAttackTime)) {
				mod_Invasion.broadcastToAll("The night looms around the nexus...");
			}
			if (this.lastWorldTime > currentTime) {
				this.nextAttackTime = ((int) (this.nextAttackTime - (this.lastWorldTime - currentTime)));
			}
			this.lastWorldTime = currentTime;

			if (this.lastWorldTime >= this.nextAttackTime) {
				float difficulty = 1.0F + this.powerLevel / 4500;
				float tierLevel = 1.0F + this.powerLevel / 4500;
				int timeSeconds = 240;
				try {
					Wave wave = this.waveBuilder.generateWave(difficulty, tierLevel, timeSeconds);
					this.mobsLeftInWave = (this.lastMobsLeftInWave = this.mobsToKillInWave = (int) (wave.getTotalMobAmount() * 0.8F));
					this.waveSpawner.beginNextWave(wave);
					this.continuousAttack = true;
					int days = mod_Invasion.getMinContinuousModeDays() + this.worldObj.rand.nextInt(1 + mod_Invasion.getMaxContinuousModeDays() - mod_Invasion.getMinContinuousModeDays());
					this.nextAttackTime = ((int) (currentTime / 24000L * 24000L) + 14000 + days * 24000);
					this.hp = (this.lastHp = 100);
					this.zapTimer = 0;
					this.waveDelayTimer = -1L;
					mod_Invasion.broadcastToAll("Forces are destabilising the nexus!");
					mod_Invasion.playGlobalSFX("invmod:rumble");
				} catch (WaveSpawnerException e) {
					mod_Invasion.log(e.getMessage());
					e.printStackTrace();
					stop();
				}

			}

		} else if (this.hp <= 0) {
			this.continuousAttack = false;
			continuousNexusHurt();
		} else if (this.waveSpawner.isWaveComplete()) {
			if (this.mobsLeftInWave > 0) {
				return;
			}

			if (this.waveDelayTimer == -1L) {
				this.waveDelayTimer = 0L;
				this.waveDelay = this.waveSpawner.getWaveRestTime();
			} else {
				this.waveDelayTimer += elapsed;
				if ((this.waveDelayTimer > this.waveDelay) && (this.zapTimer < -200)) {
					this.waveDelayTimer = -1L;
					this.continuousAttack = false;
					this.waveSpawner.stop();
					this.hp = 100;
					this.lastHp = 100;
					this.lastPowerLevel = this.powerLevel;
				}
			}

			this.zapTimer -= 1;
			if (this.mobsLeftInWave <= 0) {
				if ((this.zapTimer <= 0) && (zapEnemy(1))) {
					zapEnemy(0);
					this.zapTimer = 23;
				}
			}
		} else {
			try {
				this.waveSpawner.spawn(elapsed);
			} catch (WaveSpawnerException e) {
				mod_Invasion.log(e.getMessage());
				e.printStackTrace();
				stop();
			}
		}
	}

	private void updateStatus() {
		if (this.nexusItemStacks[0] != null) {
			if ((this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemIMTrap.itemID) && (this.nexusItemStacks[0].getItemDamage() == 0)) {
				if (this.cookTime < 1200) {
					if (this.mode == 0)
						this.cookTime += 1;
					else {
						this.cookTime += 9;
					}
				}
				if (this.cookTime >= 1200) {
					if (this.nexusItemStacks[1] == null) {
						this.nexusItemStacks[1] = new ItemStack(mod_Invasion.itemIMTrap, 1, 1);
						if (--this.nexusItemStacks[0].stackSize <= 0)
							this.nexusItemStacks[0] = null;
						this.cookTime = 0;
					} else if ((this.nexusItemStacks[1].getItem().itemID == mod_Invasion.itemIMTrap.itemID) && (this.nexusItemStacks[1].getItemDamage() == 1)) {
						this.nexusItemStacks[1].stackSize += 1;
						if (--this.nexusItemStacks[0].stackSize <= 0)
							this.nexusItemStacks[0] = null;
						this.cookTime = 0;
					}
				}
			} else if ((this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemRiftFlux.itemID) && (this.nexusItemStacks[0].getItemDamage() == 1)) {
				if ((this.cookTime < 1200) && (this.nexusLevel >= 10)) {
					this.cookTime += 5;
				}

				if (this.cookTime >= 1200) {
					if (this.nexusItemStacks[1] == null) {
						this.nexusItemStacks[1] = new ItemStack(mod_Invasion.itemStrongCatalyst, 1);
						if (--this.nexusItemStacks[0].stackSize <= 0)
							this.nexusItemStacks[0] = null;
						this.cookTime = 0;
					}
				}
			}
		} else {
			this.cookTime = 0;
		}

		if (this.activationTimer >= 400) {
			this.activationTimer = 0;
			if ((mod_Invasion.tryGetInvasionPermission(this)) && (this.nexusItemStacks[0] != null)) {
				if (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemNexusCatalyst.itemID) {
					this.nexusItemStacks[0].stackSize -= 1;
					if (this.nexusItemStacks[0].stackSize == 0)
						this.nexusItemStacks[0] = null;
					startInvasion(1);
				} else if (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemStrongCatalyst.itemID) {
					this.nexusItemStacks[0].stackSize -= 1;
					if (this.nexusItemStacks[0].stackSize == 0)
						this.nexusItemStacks[0] = null;
					startInvasion(10);
				} else if (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemStableNexusCatalyst.itemID) {
					this.nexusItemStacks[0].stackSize -= 1;
					if (this.nexusItemStacks[0].stackSize == 0)
						this.nexusItemStacks[0] = null;
					startContinuousPlay();
				}
			}

		} else if ((this.mode == 0) || (this.mode == 4)) {
			if (this.nexusItemStacks[0] != null) {
				if ((this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemNexusCatalyst.itemID) || (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemStrongCatalyst.itemID)) {
					this.activationTimer += 1;
					this.mode = 0;
				} else if (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemStableNexusCatalyst.itemID) {
					this.activationTimer += 1;
					this.mode = 4;
				}
			} else {
				this.activationTimer = 0;
			}
		} else if (this.mode == 2) {
			if (this.nexusItemStacks[0] != null) {
				if ((this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemNexusCatalyst.itemID) || (this.nexusItemStacks[0].getItem().itemID == mod_Invasion.itemStrongCatalyst.itemID)) {
					this.activationTimer += 1;
				}
			} else
				this.activationTimer = 0;
		}
	}

	private void generateFlux(int increment) {
		this.generation += increment;
		if (this.generation >= 3000) {
			if (this.nexusItemStacks[1] == null) {
				this.nexusItemStacks[1] = new ItemStack(mod_Invasion.itemRiftFlux, 1, 1);
				this.generation -= 3000;
			} else if (this.nexusItemStacks[1].getItem().itemID == mod_Invasion.itemRiftFlux.itemID) {
				this.nexusItemStacks[1].stackSize += 1;
				this.generation -= 3000;
			}
		}
	}

	private void stop() {
		if (this.mode == 3) {
			setMode(2);
			int days = mod_Invasion.getMinContinuousModeDays() + this.worldObj.rand.nextInt(1 + mod_Invasion.getMaxContinuousModeDays() - mod_Invasion.getMinContinuousModeDays());
			this.nextAttackTime = ((int) (this.worldObj.getWorldTime() / 24000L * 24000L) + 14000 + days * 24000);
		} else {
			setMode(0);
		}

		this.waveSpawner.stop();
		mod_Invasion.setInvasionEnded(this);
		this.activationTimer = 0;
		this.currentWave = 0;
		this.errorState = 0;
	}

	private void bindPlayers() {
		List players = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBoxToRadius);
		for (EntityPlayer entityPlayer : players) 
		{
			long time = System.currentTimeMillis();
			if (!this.boundPlayers.containsKey(entityPlayer.username)) 
			{
				mod_Invasion.broadcastToAll(entityPlayer.username + (entityPlayer.username.toLowerCase().endsWith("s") ? "'" : "'s") + " life is now bound to the nexus");
			} 
			else if (time - ((Long) this.boundPlayers.get(entityPlayer.username)).longValue() > 300000L) 
			{
				mod_Invasion.broadcastToAll(entityPlayer.username + (entityPlayer.username.toLowerCase().endsWith("s") ? "'" : "'s") + " life is now bound to the nexus");
			}
			this.boundPlayers.put(entityPlayer.username, Long.valueOf(time));
		}
	}

	private void updateMobList() {
		this.mobList = this.worldObj.getEntitiesWithinAABB(EntityIMLiving.class, this.boundingBoxToRadius);
		this.mobsSorted = false;
	}

	protected void setMode(int i) {
		this.mode = i;
		if (this.mode == 0)
			setActive(false);
		else
			setActive(true);
	}

	private void setActive(boolean flag) {
		if (this.worldObj != null) {
			int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
			if (flag) {
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, (meta & 0x4) == 0 ? meta + 4 : meta, 3);
			} else {
				this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, (meta & 0x4) == 4 ? meta - 4 : meta, 3);
			}
		}
	}

	private int acquireEntities() {
		AxisAlignedBB bb = this.boundingBoxToRadius.expand(10.0D, 128.0D, 10.0D);

		List entities = this.worldObj.getEntitiesWithinAABB(EntityIMLiving.class, bb);
		for (EntityIMLiving entity : entities) {
			entity.acquiredByNexus(this);
		}
		mod_Invasion.log("Acquired " + entities.size() + " entities after state restore");
		return entities.size();
	}

	private void theEnd() {
		if (!this.worldObj.isRemote) {
			stop();
			long time = System.currentTimeMillis();
			for (Map.Entry entry : this.boundPlayers.entrySet()) {
				if (time - ((Long) entry.getValue()).longValue() < 300000L) {
					EntityPlayer player = this.worldObj.getPlayerEntityByName((String) entry.getKey());
					if (player != null) {
						player.attackEntityFrom(DamageSource.magic, 500.0F);
						mod_Invasion.playGlobalSFX("random.explode");
					}

				}

			}

			this.boundPlayers.clear();
			killAllMobs();
		}
	}

	private void continuousNexusHurt() {
		mod_Invasion.broadcastToAll("Nexus severely damaged!");
		mod_Invasion.playGlobalSFX("random.explode");
		killAllMobs();
		this.waveSpawner.stop();
		this.powerLevel = ((int) ((this.powerLevel - (this.powerLevel - this.lastPowerLevel)) * 0.7F));
		this.lastPowerLevel = this.powerLevel;
		if (this.powerLevel < 0) {
			this.powerLevel = 0;
			stop();
		}
	}

	private void killAllMobs() {
		List mobs = this.worldObj.getEntitiesWithinAABB(EntityIMLiving.class, this.boundingBoxToRadius);
		for (EntityIMLiving mob : mobs) {
			mob.attackEntityFrom(DamageSource.magic, 500.0F);
		}
	}

	private boolean zapEnemy(int sfx) {
		if (this.mobList.size() > 0) {
			if (!this.mobsSorted) {
				Collections.sort(this.mobList, new ComparatorEntityDistance(this.xCoord, this.yCoord, this.zCoord));
			}
			EntityIMLiving mob = (EntityIMLiving) this.mobList.remove(this.mobList.size() - 1);
			mob.attackEntityFrom(DamageSource.magic, 500.0F);
			EntityIMBolt bolt = new EntityIMBolt(this.worldObj, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, mob.posX, mob.posY, mob.posZ, 15, sfx);
			this.worldObj.spawnEntityInWorld(bolt);
			return true;
		}

		return false;
	}

	private boolean resumeSpawnerContinuous() {
		try {
			mod_Invasion.tryGetInvasionPermission(this);
			float difficulty = 1.0F + this.powerLevel / 4500;
			float tierLevel = 1.0F + this.powerLevel / 4500;
			int timeSeconds = 240;
			Wave wave = this.waveBuilder.generateWave(difficulty, tierLevel, timeSeconds);
			this.mobsToKillInWave = ((int) (wave.getTotalMobAmount() * 0.8F));
			mod_Invasion.log("Original mobs to kill: " + this.mobsToKillInWave);
			this.mobsLeftInWave = (this.lastMobsLeftInWave = this.mobsToKillInWave - this.waveSpawner.resumeFromState(wave, this.spawnerElapsedRestore, this.spawnRadius));
			return true;
		} catch (WaveSpawnerException e) {
			float tierLevel;
			mod_Invasion.log("Error resuming spawner:" + e.getMessage());
			this.waveSpawner.stop();
			return false;
		} finally {
			mod_Invasion.setInvasionEnded(this);
		}
	}

	private boolean resumeSpawnerInvasion() {
		try {
			mod_Invasion.tryGetInvasionPermission(this);
			this.waveSpawner.resumeFromState(this.currentWave, this.spawnerElapsedRestore, this.spawnRadius);
			return true;
		} catch (WaveSpawnerException e) {
			mod_Invasion.log("Error resuming spawner:" + e.getMessage());
			this.waveSpawner.stop();
			return false;
		} finally {
			mod_Invasion.setInvasionEnded(this);
		}
	}

	private void playSoundTo() {
	}

	private void updateAI() {
		this.attackerAI.update();
	}
}