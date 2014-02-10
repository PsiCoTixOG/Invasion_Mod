/*      */ package invmod.common.nexus;
/*      */ 
/*      */ import invmod.common.entity.AttackerAI;
/*      */ import invmod.common.entity.EntityIMBolt;
/*      */ import invmod.common.entity.EntityIMLiving;
/*      */ import invmod.common.mod_Invasion;
/*      */ import invmod.common.util.ComparatorEntityDistance;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.BlockPistonExtension;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.CallableItemName;
/*      */ import net.minecraft.inventory.InventoryLargeChest;
/*      */ import net.minecraft.item.EnumToolMaterial;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.nbt.NBTTagByte;
/*      */ import net.minecraft.nbt.NBTTagInt;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.CombatTracker;
/*      */ import net.minecraft.world.ColorizerGrass;
/*      */ 
/*      */ public class TileEntityNexus extends TileEntitySign
/*      */   implements INexusAccess, InventoryLargeChest
/*      */ {
/*      */   private static final long BIND_EXPIRE_TIME = 300000L;
/*      */   private IMWaveSpawner waveSpawner;
/*      */   private IMWaveBuilder waveBuilder;
/*      */   private EnumToolMaterial[] nexusItemStacks;
/*      */   private BlockPistonExtension boundingBoxToRadius;
/*      */   private HashMap<String, Long> boundPlayers;
/*      */   private List<EntityIMLiving> mobList;
/*      */   private AttackerAI attackerAI;
/*      */   private int activationTimer;
/*      */   private int currentWave;
/*      */   private int spawnRadius;
/*      */   private int nexusLevel;
/*      */   private int nexusKills;
/*      */   private int generation;
/*      */   private int cookTime;
/*      */   private int maxHp;
/*      */   private int hp;
/*      */   private int lastHp;
/*      */   private int mode;
/*      */   private int powerLevel;
/*      */   private int lastPowerLevel;
/*      */   private int powerLevelTimer;
/*      */   private int mobsLeftInWave;
/*      */   private int lastMobsLeftInWave;
/*      */   private int mobsToKillInWave;
/*      */   private int nextAttackTime;
/*      */   private int daysToAttack;
/*      */   private long lastWorldTime;
/*      */   private int zapTimer;
/*      */   private int errorState;
/*      */   private int tickCount;
/*      */   private int cleanupTimer;
/*      */   private long spawnerElapsedRestore;
/*      */   private long timer;
/*      */   private long waveDelayTimer;
/*      */   private long waveDelay;
/*      */   private boolean continuousAttack;
/*      */   private boolean mobsSorted;
/*      */   private boolean resumedFromNBT;
/*      */ 
/*      */   public TileEntityNexus()
/*      */   {
/*   72 */     this(null);
/*      */   }
/*      */ 
/*      */   public TileEntityNexus(ColorizerGrass world)
/*      */   {
/*   77 */     this.k = world;
/*   78 */     this.spawnRadius = 52;
/*   79 */     this.waveSpawner = new IMWaveSpawner(this, this.spawnRadius);
/*   80 */     this.waveBuilder = new IMWaveBuilder();
/*   81 */     this.nexusItemStacks = new EnumToolMaterial[2];
/*   82 */     this.boundingBoxToRadius = BlockPistonExtension.a(this.xCoord, this.yCoord, this.zCoord, this.xCoord, this.yCoord, this.zCoord);
/*   83 */     this.boundingBoxToRadius.b(this.xCoord - (this.spawnRadius + 10), this.yCoord - (this.spawnRadius + 40), this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), this.yCoord + (this.spawnRadius + 40), this.zCoord + (this.spawnRadius + 10));
/*   84 */     this.boundPlayers = new HashMap();
/*   85 */     this.mobList = new ArrayList();
/*   86 */     this.attackerAI = new AttackerAI(this);
/*   87 */     this.activationTimer = 0;
/*   88 */     this.cookTime = 0;
/*   89 */     this.currentWave = 0;
/*   90 */     this.nexusLevel = 1;
/*   91 */     this.nexusKills = 0;
/*   92 */     this.generation = 0;
/*   93 */     this.maxHp = (this.hp = this.lastHp = 100);
/*   94 */     this.mode = 0;
/*   95 */     this.powerLevelTimer = 0;
/*   96 */     this.powerLevel = 0;
/*   97 */     this.lastPowerLevel = 0;
/*   98 */     this.mobsLeftInWave = 0;
/*   99 */     this.nextAttackTime = 0;
/*  100 */     this.daysToAttack = 0;
/*  101 */     this.lastWorldTime = 0L;
/*  102 */     this.errorState = 0;
/*  103 */     this.tickCount = 0;
/*  104 */     this.timer = 0L;
/*  105 */     this.zapTimer = 0;
/*  106 */     this.cleanupTimer = 0;
/*  107 */     this.waveDelayTimer = -1L;
/*  108 */     this.waveDelay = 0L;
/*  109 */     this.continuousAttack = false;
/*  110 */     this.mobsSorted = false;
/*  111 */     this.resumedFromNBT = false;
/*      */   }
/*      */ 
/*      */   public void updateEntity()
/*      */   {
/*  133 */     if (this.k.I)
/*      */     {
/*  135 */       return;
/*      */     }
/*      */ 
/*  140 */     updateStatus();
/*      */ 
/*  142 */     updateAI();
/*      */ 
/*  145 */     if ((this.mode == 1) || (this.mode == 2) || (this.mode == 3))
/*      */     {
/*  147 */       if (this.resumedFromNBT)
/*      */       {
/*  149 */         this.boundingBoxToRadius.b(this.xCoord - (this.spawnRadius + 10), 0.0D, this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), 127.0D, this.zCoord + (this.spawnRadius + 10));
/*  150 */         if ((this.mode == 2) && (this.continuousAttack))
/*      */         {
/*  152 */           if (resumeSpawnerContinuous())
/*      */           {
/*  154 */             this.mobsLeftInWave = (this.lastMobsLeftInWave += acquireEntities());
/*  155 */             mod_Invasion.log("mobsLeftInWave: " + this.mobsLeftInWave);
/*  156 */             mod_Invasion.log("mobsToKillInWave: " + this.mobsToKillInWave);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  161 */           resumeSpawnerInvasion();
/*  162 */           acquireEntities();
/*      */         }
/*      */ 
/*  165 */         this.attackerAI.onResume();
/*      */ 
/*  167 */         this.resumedFromNBT = false;
/*      */       }
/*      */ 
/*      */       try
/*      */       {
/*  172 */         this.tickCount += 1;
/*  173 */         if (this.tickCount == 60)
/*      */         {
/*  175 */           this.tickCount -= 60;
/*  176 */           bindPlayers();
/*  177 */           updateMobList();
/*      */         }
/*      */ 
/*  180 */         if ((this.mode == 1) || (this.mode == 3))
/*  181 */           doInvasion(50);
/*  182 */         else if (this.mode == 2)
/*  183 */           doContinuous(50);
/*      */       }
/*      */       catch (WaveSpawnerException e)
/*      */       {
/*  187 */         mod_Invasion.log(e.getMessage());
/*  188 */         e.printStackTrace();
/*  189 */         stop();
/*      */       }
/*      */     }
/*      */ 
/*  193 */     if (this.cleanupTimer++ > 40)
/*      */     {
/*  195 */       this.cleanupTimer = 0;
/*  196 */       if (this.k.a(this.xCoord, this.yCoord, this.zCoord) != mod_Invasion.blockNexus.cF)
/*      */       {
/*  198 */         mod_Invasion.setInvasionEnded(this);
/*  199 */         stop();
/*  200 */         invalidate();
/*  201 */         mod_Invasion.log("Stranded nexus entity trying to delete itself...");
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void emergencyStop()
/*      */   {
/*  208 */     mod_Invasion.log("Nexus manually stopped by command");
/*  209 */     stop();
/*  210 */     killAllMobs();
/*      */   }
/*      */ 
/*      */   public void debugStatus()
/*      */   {
/*  215 */     mod_Invasion.broadcastToAll("Current Time: " + this.k.J());
/*  216 */     mod_Invasion.broadcastToAll("Time to next: " + this.nextAttackTime);
/*  217 */     mod_Invasion.broadcastToAll("Days to attack: " + this.daysToAttack);
/*  218 */     mod_Invasion.broadcastToAll("Mobs left: " + this.mobsLeftInWave);
/*  219 */     mod_Invasion.broadcastToAll("Mode: " + this.mode);
/*      */   }
/*      */ 
/*      */   public void debugStartInvaion(int startWave)
/*      */   {
/*  224 */     mod_Invasion.tryGetInvasionPermission(this);
/*  225 */     startInvasion(startWave);
/*      */   }
/*      */ 
/*      */   public void createBolt(int x, int y, int z, int t)
/*      */   {
/*  230 */     EntityIMBolt bolt = new EntityIMBolt(this.k, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, x + 0.5D, y + 0.5D, z + 0.5D, t, 1);
/*  231 */     this.k.d(bolt);
/*      */   }
/*      */ 
/*      */   public boolean setSpawnRadius(int radius)
/*      */   {
/*  236 */     if ((!this.waveSpawner.isActive()) && (radius > 8))
/*      */     {
/*  238 */       this.spawnRadius = radius;
/*  239 */       this.waveSpawner.setRadius(radius);
/*  240 */       this.boundingBoxToRadius.b(this.xCoord - (this.spawnRadius + 10), 0.0D, this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), 127.0D, this.zCoord + (this.spawnRadius + 10));
/*  241 */       return true;
/*      */     }
/*      */ 
/*  245 */     return false;
/*      */   }
/*      */ 
/*      */   public void attackNexus(int damage)
/*      */   {
/*  252 */     this.hp -= damage;
/*  253 */     if (this.hp <= 0)
/*      */     {
/*  255 */       this.hp = 0;
/*  256 */       if (this.mode == 1) {
/*  257 */         theEnd();
/*      */       }
/*      */     }
/*  260 */     while (this.hp + 5 <= this.lastHp)
/*      */     {
/*  262 */       mod_Invasion.broadcastToAll("Nexus at " + (this.lastHp - 5) + " hp");
/*  263 */       this.lastHp -= 5;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void registerMobDied()
/*      */   {
/*  270 */     this.nexusKills += 1;
/*  271 */     this.mobsLeftInWave -= 1;
/*  272 */     if (this.mobsLeftInWave <= 0)
/*      */     {
/*  274 */       if (this.lastMobsLeftInWave > 0)
/*      */       {
/*  276 */         mod_Invasion.broadcastToAll("Nexus rift stable again!");
/*  277 */         mod_Invasion.broadcastToAll("Unleashing tapped energy...");
/*  278 */         this.lastMobsLeftInWave = this.mobsLeftInWave;
/*      */       }
/*  280 */       return;
/*      */     }
/*  282 */     while (this.mobsLeftInWave + this.mobsToKillInWave * 0.1F <= this.lastMobsLeftInWave)
/*      */     {
/*  284 */       mod_Invasion.broadcastToAll("Nexus rift stabilised to " + (100 - (int)(100 * this.mobsLeftInWave / this.mobsToKillInWave)) + "%");
/*  285 */       this.lastMobsLeftInWave = ((int)(this.lastMobsLeftInWave - this.mobsToKillInWave * 0.1F));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void registerMobClose()
/*      */   {
/*      */   }
/*      */ 
/*      */   public boolean isActivating()
/*      */   {
/*  297 */     return (this.activationTimer > 0) && (this.activationTimer < 400);
/*      */   }
/*      */ 
/*      */   public int getMode()
/*      */   {
/*  302 */     return this.mode;
/*      */   }
/*      */ 
/*      */   public int getActivationTimer()
/*      */   {
/*  308 */     return this.activationTimer;
/*      */   }
/*      */ 
/*      */   public int getSpawnRadius()
/*      */   {
/*  314 */     return this.spawnRadius;
/*      */   }
/*      */ 
/*      */   public int getNexusKills()
/*      */   {
/*  320 */     return this.nexusKills;
/*      */   }
/*      */ 
/*      */   public int getGeneration()
/*      */   {
/*  326 */     return this.generation;
/*      */   }
/*      */ 
/*      */   public int getNexusLevel()
/*      */   {
/*  332 */     return this.nexusLevel;
/*      */   }
/*      */ 
/*      */   public int getPowerLevel()
/*      */   {
/*  337 */     return this.powerLevel;
/*      */   }
/*      */ 
/*      */   public int getCookTime()
/*      */   {
/*  342 */     return this.cookTime;
/*      */   }
/*      */ 
/*      */   public int getNexusID()
/*      */   {
/*  347 */     return -1;
/*      */   }
/*      */ 
/*      */   public int getXCoord()
/*      */   {
/*  353 */     return this.xCoord;
/*      */   }
/*      */ 
/*      */   public int getYCoord()
/*      */   {
/*  359 */     return this.yCoord;
/*      */   }
/*      */ 
/*      */   public int getZCoord()
/*      */   {
/*  365 */     return this.zCoord;
/*      */   }
/*      */ 
/*      */   public ColorizerGrass getWorld()
/*      */   {
/*  371 */     return this.k;
/*      */   }
/*      */ 
/*      */   public List<EntityIMLiving> getMobList()
/*      */   {
/*  377 */     return this.mobList;
/*      */   }
/*      */ 
/*      */   public int getActivationProgressScaled(int i)
/*      */   {
/*  382 */     return this.activationTimer * i / 400;
/*      */   }
/*      */ 
/*      */   public int getGenerationProgressScaled(int i)
/*      */   {
/*  387 */     return this.generation * i / 3000;
/*      */   }
/*      */ 
/*      */   public int getCookProgressScaled(int i)
/*      */   {
/*  392 */     return this.cookTime * i / 1200;
/*      */   }
/*      */ 
/*      */   public int getNexusPowerLevel()
/*      */   {
/*  397 */     return this.powerLevel;
/*      */   }
/*      */ 
/*      */   public int getCurrentWave()
/*      */   {
/*  403 */     return this.currentWave;
/*      */   }
/*      */ 
/*      */   public int getSizeInventory()
/*      */   {
/*  409 */     return this.nexusItemStacks.length;
/*      */   }
/*      */ 
/*      */   public String getInvName()
/*      */   {
/*  415 */     return "Nexus";
/*      */   }
/*      */ 
/*      */   public int getInventoryStackLimit()
/*      */   {
/*  421 */     return 64;
/*      */   }
/*      */ 
/*      */   public boolean isInvNameLocalized()
/*      */   {
/*  427 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean b(int i, EnumToolMaterial itemstack)
/*      */   {
/*  433 */     return true;
/*      */   }
/*      */ 
/*      */   public void a(int i, EnumToolMaterial itemstack)
/*      */   {
/*  439 */     this.nexusItemStacks[i] = itemstack;
/*  440 */     if ((itemstack != null) && (itemstack.STONE > getInventoryStackLimit()))
/*      */     {
/*  442 */       itemstack.STONE = getInventoryStackLimit();
/*      */     }
/*      */   }
/*      */ 
/*      */   public EnumToolMaterial a(int i)
/*      */   {
/*  449 */     return this.nexusItemStacks[i];
/*      */   }
/*      */ 
/*      */   public EnumToolMaterial a(int i, int j)
/*      */   {
/*  455 */     if (this.nexusItemStacks[i] != null)
/*      */     {
/*  457 */       if (this.nexusItemStacks[i].STONE <= j)
/*      */       {
/*  459 */         EnumToolMaterial itemstack = this.nexusItemStacks[i];
/*  460 */         this.nexusItemStacks[i] = null;
/*  461 */         return itemstack;
/*      */       }
/*  463 */       EnumToolMaterial itemstack1 = this.nexusItemStacks[i].a(j);
/*  464 */       if (this.nexusItemStacks[i].STONE == 0)
/*      */       {
/*  466 */         this.nexusItemStacks[i] = null;
/*      */       }
/*  468 */       return itemstack1;
/*      */     }
/*      */ 
/*  471 */     return null;
/*      */   }
/*      */ 
/*      */   public void openChest()
/*      */   {
/*      */   }
/*      */ 
/*      */   public void closeChest()
/*      */   {
/*      */   }
/*      */ 
/*      */   public boolean a(CallableItemName entityplayer)
/*      */   {
/*  488 */     return true;
/*      */   }
/*      */ 
/*      */   public EnumToolMaterial a_(int i)
/*      */   {
/*  494 */     return null;
/*      */   }
/*      */ 
/*      */   public void a(NBTTagByte nbttagcompound)
/*      */   {
/*  500 */     mod_Invasion.log("Restoring TileEntityNexus from NBT");
/*  501 */     super.a(nbttagcompound);
/*  502 */     NBTTagInt nbttaglist = nbttagcompound.m("Items");
/*  503 */     this.nexusItemStacks = new EnumToolMaterial[getSizeInventory()];
/*  504 */     for (int i = 0; i < nbttaglist.c(); i++)
/*      */     {
/*  506 */       NBTTagByte nbttagcompound1 = (NBTTagByte)nbttaglist.b(i);
/*  507 */       byte byte0 = nbttagcompound1.c("Slot");
/*  508 */       if ((byte0 >= 0) && (byte0 < this.nexusItemStacks.length))
/*      */       {
/*  510 */         this.nexusItemStacks[byte0] = EnumToolMaterial.a(nbttagcompound1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  515 */     nbttaglist = nbttagcompound.m("boundPlayers");
/*  516 */     for (int i = 0; i < nbttaglist.c(); i++)
/*      */     {
/*  518 */       this.boundPlayers.put(((NBTTagByte)nbttaglist.b(i)).i("name"), Long.valueOf(System.currentTimeMillis()));
/*  519 */       mod_Invasion.log("Added bound player: " + ((NBTTagByte)nbttaglist.b(i)).i("name"));
/*      */     }
/*      */ 
/*  522 */     this.activationTimer = nbttagcompound.d("activationTimer");
/*  523 */     this.mode = nbttagcompound.e("mode");
/*  524 */     this.currentWave = nbttagcompound.d("currentWave");
/*  525 */     this.spawnRadius = nbttagcompound.d("spawnRadius");
/*  526 */     this.nexusLevel = nbttagcompound.d("nexusLevel");
/*  527 */     this.hp = nbttagcompound.d("hp");
/*  528 */     this.nexusKills = nbttagcompound.e("nexusKills");
/*  529 */     this.generation = nbttagcompound.d("generation");
/*  530 */     this.powerLevel = nbttagcompound.e("powerLevel");
/*  531 */     this.lastPowerLevel = nbttagcompound.e("lastPowerLevel");
/*  532 */     this.nextAttackTime = nbttagcompound.e("nextAttackTime");
/*  533 */     this.daysToAttack = nbttagcompound.e("daysToAttack");
/*  534 */     this.continuousAttack = nbttagcompound.n("continuousAttack");
/*      */ 
/*  536 */     this.boundingBoxToRadius.b(this.xCoord - (this.spawnRadius + 10), this.yCoord - (this.spawnRadius + 40), this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), this.yCoord + (this.spawnRadius + 40), this.zCoord + (this.spawnRadius + 10));
/*      */ 
/*  538 */     mod_Invasion.log("activationTimer = " + this.activationTimer);
/*  539 */     mod_Invasion.log("mode = " + this.mode);
/*  540 */     mod_Invasion.log("currentWave = " + this.currentWave);
/*  541 */     mod_Invasion.log("spawnRadius = " + this.spawnRadius);
/*  542 */     mod_Invasion.log("nexusLevel = " + this.nexusLevel);
/*  543 */     mod_Invasion.log("hp = " + this.hp);
/*  544 */     mod_Invasion.log("nexusKills = " + this.nexusKills);
/*  545 */     mod_Invasion.log("powerLevel = " + this.powerLevel);
/*  546 */     mod_Invasion.log("lastPowerLevel = " + this.lastPowerLevel);
/*  547 */     mod_Invasion.log("nextAttackTime = " + this.nextAttackTime);
/*      */ 
/*  551 */     this.waveSpawner.setRadius(this.spawnRadius);
/*  552 */     if ((this.mode == 1) || (this.mode == 3) || ((this.mode == 2) && (this.continuousAttack)))
/*      */     {
/*  554 */       mod_Invasion.log("Nexus is active; flagging for restore");
/*  555 */       this.resumedFromNBT = true;
/*  556 */       this.spawnerElapsedRestore = nbttagcompound.f("spawnerElapsed");
/*  557 */       mod_Invasion.log("spawnerElapsed = " + this.spawnerElapsedRestore);
/*      */     }
/*      */ 
/*  560 */     this.attackerAI.readFromNBT(nbttagcompound);
/*      */   }
/*      */ 
/*      */   public void b(NBTTagByte nbttagcompound)
/*      */   {
/*  566 */     if (this.mode != 0) {
/*  567 */       mod_Invasion.setNexusUnloaded(this);
/*      */     }
/*  569 */     super.b(nbttagcompound);
/*  570 */     nbttagcompound.a("activationTimer", (short)this.activationTimer);
/*  571 */     nbttagcompound.a("currentWave", (short)this.currentWave);
/*  572 */     nbttagcompound.a("spawnRadius", (short)this.spawnRadius);
/*  573 */     nbttagcompound.a("nexusLevel", (short)this.nexusLevel);
/*  574 */     nbttagcompound.a("hp", (short)this.hp);
/*  575 */     nbttagcompound.a("nexusKills", this.nexusKills);
/*  576 */     nbttagcompound.a("generation", (short)this.generation);
/*  577 */     nbttagcompound.a("spawnerElapsed", this.waveSpawner.getElapsedTime());
/*  578 */     nbttagcompound.a("mode", this.mode);
/*  579 */     nbttagcompound.a("powerLevel", this.powerLevel);
/*  580 */     nbttagcompound.a("lastPowerLevel", this.lastPowerLevel);
/*  581 */     nbttagcompound.a("nextAttackTime", this.nextAttackTime);
/*  582 */     nbttagcompound.a("daysToAttack", this.daysToAttack);
/*  583 */     nbttagcompound.a("continuousAttack", this.continuousAttack);
/*      */ 
/*  585 */     NBTTagInt nbttaglist = new NBTTagInt();
/*  586 */     for (int i = 0; i < this.nexusItemStacks.length; i++)
/*      */     {
/*  588 */       if (this.nexusItemStacks[i] != null)
/*      */       {
/*  590 */         NBTTagByte nbttagcompound1 = new NBTTagByte();
/*  591 */         nbttagcompound1.a("Slot", (byte)i);
/*  592 */         this.nexusItemStacks[i].b(nbttagcompound1);
/*  593 */         nbttaglist.a(nbttagcompound1);
/*      */       }
/*      */     }
/*  596 */     nbttagcompound.a("Items", nbttaglist);
/*      */ 
/*  598 */     NBTTagInt nbttaglist2 = new NBTTagInt();
/*  599 */     for (Map.Entry entry : this.boundPlayers.entrySet())
/*      */     {
/*  601 */       NBTTagByte nbttagcompound1 = new NBTTagByte();
/*  602 */       nbttagcompound1.a("name", (String)entry.getKey());
/*  603 */       nbttaglist2.a(nbttagcompound1);
/*      */     }
/*  605 */     nbttagcompound.a("boundPlayers", nbttaglist2);
/*      */ 
/*  607 */     this.attackerAI.writeToNBT(nbttagcompound);
/*      */   }
/*      */ 
/*      */   public void askForRespawn(EntityIMLiving entity)
/*      */   {
/*  613 */     mod_Invasion.log("Stuck entity asking for respawn: " + entity.toString() + "  " + entity.posX + ", " + entity.posY + ", " + entity.posZ);
/*  614 */     this.waveSpawner.askForRespawn(entity);
/*      */   }
/*      */ 
/*      */   public AttackerAI getAttackerAI()
/*      */   {
/*  620 */     return this.attackerAI;
/*      */   }
/*      */ 
/*      */   protected void setActivationTimer(int i)
/*      */   {
/*  625 */     this.activationTimer = i;
/*      */   }
/*      */ 
/*      */   protected void setNexusLevel(int i)
/*      */   {
/*  630 */     this.nexusLevel = i;
/*      */   }
/*      */ 
/*      */   protected void setNexusKills(int i)
/*      */   {
/*  635 */     this.nexusKills = i;
/*      */   }
/*      */ 
/*      */   protected void setGeneration(int i)
/*      */   {
/*  640 */     this.generation = i;
/*      */   }
/*      */ 
/*      */   protected void setNexusPowerLevel(int i)
/*      */   {
/*  645 */     this.powerLevel = i;
/*      */   }
/*      */ 
/*      */   protected void setCookTime(int i)
/*      */   {
/*  650 */     this.cookTime = i;
/*      */   }
/*      */ 
/*      */   protected void setWave(int wave)
/*      */   {
/*  655 */     this.currentWave = wave;
/*      */   }
/*      */ 
/*      */   private void startInvasion(int startWave)
/*      */   {
/*  660 */     this.boundingBoxToRadius.b(this.xCoord - (this.spawnRadius + 10), this.yCoord - (this.spawnRadius + 40), this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), this.yCoord + (this.spawnRadius + 40), this.zCoord + (this.spawnRadius + 10));
/*  661 */     if ((this.mode == 2) && (this.continuousAttack))
/*      */     {
/*  663 */       mod_Invasion.broadcastToAll("Can't activate nexus when already under attack!");
/*  664 */       return;
/*      */     }
/*      */ 
/*  667 */     if ((this.mode == 0) || (this.mode == 2))
/*      */     {
/*  669 */       if (this.waveSpawner.isReady())
/*      */       {
/*      */         try
/*      */         {
/*  673 */           this.currentWave = startWave;
/*  674 */           this.waveSpawner.beginNextWave(this.currentWave);
/*  675 */           if (this.mode == 0)
/*  676 */             setMode(1);
/*      */           else {
/*  678 */             setMode(3);
/*      */           }
/*  680 */           bindPlayers();
/*  681 */           this.hp = this.maxHp;
/*  682 */           this.lastHp = this.maxHp;
/*  683 */           this.waveDelayTimer = -1L;
/*  684 */           this.timer = System.currentTimeMillis();
/*  685 */           mod_Invasion.broadcastToAll("The first wave is coming soon!");
/*  686 */           mod_Invasion.playGlobalSFX("invmod:rumble");
/*      */         }
/*      */         catch (WaveSpawnerException e)
/*      */         {
/*  690 */           stop();
/*  691 */           mod_Invasion.log(e.getMessage());
/*  692 */           mod_Invasion.broadcastToAll(e.getMessage());
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  697 */         mod_Invasion.log("Wave spawner not in ready state");
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  702 */       mod_Invasion.log("Tried to activate nexus while already active");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void startContinuousPlay()
/*      */   {
/*  708 */     this.boundingBoxToRadius.b(this.xCoord - (this.spawnRadius + 10), 0.0D, this.zCoord - (this.spawnRadius + 10), this.xCoord + (this.spawnRadius + 10), 127.0D, this.zCoord + (this.spawnRadius + 10));
/*  709 */     if ((this.mode == 4) && (this.waveSpawner.isReady()) && (mod_Invasion.tryGetInvasionPermission(this)))
/*      */     {
/*  711 */       setMode(2);
/*  712 */       this.hp = this.maxHp;
/*  713 */       this.lastHp = this.maxHp;
/*  714 */       this.lastPowerLevel = this.powerLevel;
/*  715 */       this.lastWorldTime = this.k.J();
/*  716 */       this.nextAttackTime = ((int)(this.lastWorldTime / 24000L * 24000L) + 14000);
/*  717 */       if ((this.lastWorldTime % 24000L > 12000L) && (this.lastWorldTime % 24000L < 16000L))
/*      */       {
/*  719 */         mod_Invasion.broadcastToAll("The night looms around the nexus...");
/*      */       }
/*      */       else
/*      */       {
/*  723 */         mod_Invasion.broadcastToAll("Nexus activated and stable");
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  728 */       mod_Invasion.broadcastToAll("Couldn't activate nexus");
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doInvasion(int elapsed)
/*      */     throws WaveSpawnerException
/*      */   {
/*  735 */     if (this.waveSpawner.isActive())
/*      */     {
/*  737 */       if (this.hp <= 0)
/*      */       {
/*  739 */         theEnd();
/*      */       }
/*      */       else
/*      */       {
/*  743 */         generateFlux(1);
/*  744 */         if (this.waveSpawner.isWaveComplete())
/*      */         {
/*  747 */           if (this.waveDelayTimer == -1L)
/*      */           {
/*  749 */             mod_Invasion.broadcastToAll("Wave " + this.currentWave + " almost complete!");
/*  750 */             mod_Invasion.playGlobalSFX("invmod:chime");
/*  751 */             this.waveDelayTimer = 0L;
/*  752 */             this.waveDelay = this.waveSpawner.getWaveRestTime();
/*      */           }
/*      */           else
/*      */           {
/*  756 */             this.waveDelayTimer += elapsed;
/*  757 */             if (this.waveDelayTimer > this.waveDelay)
/*      */             {
/*  759 */               this.currentWave += 1;
/*  760 */               mod_Invasion.broadcastToAll("Wave " + this.currentWave + " about to begin");
/*  761 */               this.waveSpawner.beginNextWave(this.currentWave);
/*  762 */               this.waveDelayTimer = -1L;
/*  763 */               mod_Invasion.playGlobalSFX("invmod:rumble");
/*  764 */               if (this.currentWave > this.nexusLevel) {
/*  765 */                 this.nexusLevel = this.currentWave;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  772 */           this.waveSpawner.spawn(elapsed);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doContinuous(int elapsed)
/*      */   {
/*  780 */     this.powerLevelTimer += elapsed;
/*  781 */     if (this.powerLevelTimer > 2200)
/*      */     {
/*  783 */       this.powerLevelTimer -= 2200;
/*  784 */       generateFlux(5 + (int)(5 * this.powerLevel / 1550.0F));
/*  785 */       if ((this.nexusItemStacks[0] == null) || (this.nexusItemStacks[0].b().itemID != mod_Invasion.itemDampingAgent.itemID)) {
/*  786 */         this.powerLevel += 1;
/*      */       }
/*      */     }
/*      */ 
/*  790 */     if ((this.nexusItemStacks[0] != null) && (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemStrongDampingAgent.itemID))
/*      */     {
/*  792 */       if ((this.powerLevel >= 0) && (!this.continuousAttack))
/*      */       {
/*  794 */         this.powerLevel -= 1;
/*  795 */         if (this.powerLevel < 0) {
/*  796 */           stop();
/*      */         }
/*      */       }
/*      */     }
/*  800 */     if (!this.continuousAttack)
/*      */     {
/*  802 */       long currentTime = this.k.J();
/*  803 */       int timeOfDay = (int)(this.lastWorldTime % 24000L);
/*  804 */       if ((timeOfDay < 12000) && (currentTime % 24000L >= 12000L) && (currentTime + 12000L > this.nextAttackTime)) {
/*  805 */         mod_Invasion.broadcastToAll("The night looms around the nexus...");
/*      */       }
/*  807 */       if (this.lastWorldTime > currentTime) {
/*  808 */         this.nextAttackTime = ((int)(this.nextAttackTime - (this.lastWorldTime - currentTime)));
/*      */       }
/*  810 */       this.lastWorldTime = currentTime;
/*      */ 
/*  812 */       if (this.lastWorldTime >= this.nextAttackTime)
/*      */       {
/*  814 */         float difficulty = 1.0F + this.powerLevel / 4500;
/*  815 */         float tierLevel = 1.0F + this.powerLevel / 4500;
/*  816 */         int timeSeconds = 240;
/*      */         try
/*      */         {
/*  819 */           Wave wave = this.waveBuilder.generateWave(difficulty, tierLevel, timeSeconds);
/*  820 */           this.mobsLeftInWave = (this.lastMobsLeftInWave = this.mobsToKillInWave = (int)(wave.getTotalMobAmount() * 0.8F));
/*  821 */           this.waveSpawner.beginNextWave(wave);
/*  822 */           this.continuousAttack = true;
/*  823 */           int days = mod_Invasion.getMinContinuousModeDays() + this.k.s.nextInt(1 + mod_Invasion.getMaxContinuousModeDays() - mod_Invasion.getMinContinuousModeDays());
/*  824 */           this.nextAttackTime = ((int)(currentTime / 24000L * 24000L) + 14000 + days * 24000);
/*  825 */           this.hp = (this.lastHp = 100);
/*  826 */           this.zapTimer = 0;
/*  827 */           this.waveDelayTimer = -1L;
/*  828 */           mod_Invasion.broadcastToAll("Forces are destabilising the nexus!");
/*  829 */           mod_Invasion.playGlobalSFX("invmod:rumble");
/*      */         }
/*      */         catch (WaveSpawnerException e)
/*      */         {
/*  833 */           mod_Invasion.log(e.getMessage());
/*  834 */           e.printStackTrace();
/*  835 */           stop();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*  841 */     else if (this.hp <= 0)
/*      */     {
/*  843 */       this.continuousAttack = false;
/*  844 */       continuousNexusHurt();
/*      */     }
/*  846 */     else if (this.waveSpawner.isWaveComplete())
/*      */     {
/*  848 */       if (this.mobsLeftInWave > 0) {
/*  849 */         return;
/*      */       }
/*      */ 
/*  852 */       if (this.waveDelayTimer == -1L)
/*      */       {
/*  854 */         this.waveDelayTimer = 0L;
/*  855 */         this.waveDelay = this.waveSpawner.getWaveRestTime();
/*      */       }
/*      */       else
/*      */       {
/*  859 */         this.waveDelayTimer += elapsed;
/*  860 */         if ((this.waveDelayTimer > this.waveDelay) && (this.zapTimer < -200))
/*      */         {
/*  862 */           this.waveDelayTimer = -1L;
/*  863 */           this.continuousAttack = false;
/*  864 */           this.waveSpawner.stop();
/*  865 */           this.hp = 100;
/*  866 */           this.lastHp = 100;
/*  867 */           this.lastPowerLevel = this.powerLevel;
/*      */         }
/*      */       }
/*      */ 
/*  871 */       this.zapTimer -= 1;
/*  872 */       if (this.mobsLeftInWave <= 0)
/*      */       {
/*  874 */         if ((this.zapTimer <= 0) && (zapEnemy(1)))
/*      */         {
/*  876 */           zapEnemy(0);
/*  877 */           this.zapTimer = 23;
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       try
/*      */       {
/*  885 */         this.waveSpawner.spawn(elapsed);
/*      */       }
/*      */       catch (WaveSpawnerException e)
/*      */       {
/*  889 */         mod_Invasion.log(e.getMessage());
/*  890 */         e.printStackTrace();
/*  891 */         stop();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateStatus()
/*      */   {
/*  899 */     if (this.nexusItemStacks[0] != null)
/*      */     {
/*  901 */       if ((this.nexusItemStacks[0].b().itemID == mod_Invasion.itemIMTrap.itemID) && (this.nexusItemStacks[0].k() == 0))
/*      */       {
/*  903 */         if (this.cookTime < 1200)
/*      */         {
/*  905 */           if (this.mode == 0)
/*  906 */             this.cookTime += 1;
/*      */           else {
/*  908 */             this.cookTime += 9;
/*      */           }
/*      */         }
/*  911 */         if (this.cookTime >= 1200)
/*      */         {
/*  914 */           if (this.nexusItemStacks[1] == null)
/*      */           {
/*  916 */             this.nexusItemStacks[1] = new EnumToolMaterial(mod_Invasion.itemIMTrap, 1, 1);
/*  917 */             if (--this.nexusItemStacks[0].STONE <= 0)
/*  918 */               this.nexusItemStacks[0] = null;
/*  919 */             this.cookTime = 0;
/*      */           }
/*  921 */           else if ((this.nexusItemStacks[1].b().itemID == mod_Invasion.itemIMTrap.itemID) && (this.nexusItemStacks[1].k() == 1))
/*      */           {
/*  923 */             this.nexusItemStacks[1].STONE += 1;
/*  924 */             if (--this.nexusItemStacks[0].STONE <= 0)
/*  925 */               this.nexusItemStacks[0] = null;
/*  926 */             this.cookTime = 0;
/*      */           }
/*      */         }
/*      */       }
/*  930 */       else if ((this.nexusItemStacks[0].b().itemID == mod_Invasion.itemRiftFlux.itemID) && (this.nexusItemStacks[0].k() == 1))
/*      */       {
/*  932 */         if ((this.cookTime < 1200) && (this.nexusLevel >= 10))
/*      */         {
/*  934 */           this.cookTime += 5;
/*      */         }
/*      */ 
/*  937 */         if (this.cookTime >= 1200)
/*      */         {
/*  940 */           if (this.nexusItemStacks[1] == null)
/*      */           {
/*  942 */             this.nexusItemStacks[1] = new EnumToolMaterial(mod_Invasion.itemStrongCatalyst, 1);
/*  943 */             if (--this.nexusItemStacks[0].STONE <= 0)
/*  944 */               this.nexusItemStacks[0] = null;
/*  945 */             this.cookTime = 0;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  952 */       this.cookTime = 0;
/*      */     }
/*      */ 
/*  955 */     if (this.activationTimer >= 400)
/*      */     {
/*  957 */       this.activationTimer = 0;
/*  958 */       if ((mod_Invasion.tryGetInvasionPermission(this)) && (this.nexusItemStacks[0] != null))
/*      */       {
/*  960 */         if (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemNexusCatalyst.itemID)
/*      */         {
/*  962 */           this.nexusItemStacks[0].STONE -= 1;
/*  963 */           if (this.nexusItemStacks[0].STONE == 0)
/*  964 */             this.nexusItemStacks[0] = null;
/*  965 */           startInvasion(1);
/*      */         }
/*  967 */         else if (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemStrongCatalyst.itemID)
/*      */         {
/*  969 */           this.nexusItemStacks[0].STONE -= 1;
/*  970 */           if (this.nexusItemStacks[0].STONE == 0)
/*  971 */             this.nexusItemStacks[0] = null;
/*  972 */           startInvasion(10);
/*      */         }
/*  974 */         else if (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemStableNexusCatalyst.itemID)
/*      */         {
/*  976 */           this.nexusItemStacks[0].STONE -= 1;
/*  977 */           if (this.nexusItemStacks[0].STONE == 0)
/*  978 */             this.nexusItemStacks[0] = null;
/*  979 */           startContinuousPlay();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*  984 */     else if ((this.mode == 0) || (this.mode == 4))
/*      */     {
/*  986 */       if (this.nexusItemStacks[0] != null)
/*      */       {
/*  988 */         if ((this.nexusItemStacks[0].b().itemID == mod_Invasion.itemNexusCatalyst.itemID) || (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemStrongCatalyst.itemID))
/*      */         {
/*  991 */           this.activationTimer += 1;
/*  992 */           this.mode = 0;
/*      */         }
/*  994 */         else if (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemStableNexusCatalyst.itemID)
/*      */         {
/*  996 */           this.activationTimer += 1;
/*  997 */           this.mode = 4;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1002 */         this.activationTimer = 0;
/*      */       }
/*      */     }
/* 1005 */     else if (this.mode == 2)
/*      */     {
/* 1007 */       if (this.nexusItemStacks[0] != null)
/*      */       {
/* 1009 */         if ((this.nexusItemStacks[0].b().itemID == mod_Invasion.itemNexusCatalyst.itemID) || (this.nexusItemStacks[0].b().itemID == mod_Invasion.itemStrongCatalyst.itemID))
/*      */         {
/* 1011 */           this.activationTimer += 1;
/*      */         }
/*      */       }
/*      */       else
/* 1015 */         this.activationTimer = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void generateFlux(int increment)
/*      */   {
/* 1022 */     this.generation += increment;
/* 1023 */     if (this.generation >= 3000)
/*      */     {
/* 1025 */       if (this.nexusItemStacks[1] == null)
/*      */       {
/* 1027 */         this.nexusItemStacks[1] = new EnumToolMaterial(mod_Invasion.itemRiftFlux, 1, 1);
/* 1028 */         this.generation -= 3000;
/*      */       }
/* 1030 */       else if (this.nexusItemStacks[1].b().itemID == mod_Invasion.itemRiftFlux.itemID)
/*      */       {
/* 1032 */         this.nexusItemStacks[1].STONE += 1;
/* 1033 */         this.generation -= 3000;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void stop()
/*      */   {
/* 1040 */     if (this.mode == 3)
/*      */     {
/* 1042 */       setMode(2);
/* 1043 */       int days = mod_Invasion.getMinContinuousModeDays() + this.k.s.nextInt(1 + mod_Invasion.getMaxContinuousModeDays() - mod_Invasion.getMinContinuousModeDays());
/* 1044 */       this.nextAttackTime = ((int)(this.k.J() / 24000L * 24000L) + 14000 + days * 24000);
/*      */     }
/*      */     else
/*      */     {
/* 1048 */       setMode(0);
/*      */     }
/*      */ 
/* 1051 */     this.waveSpawner.stop();
/* 1052 */     mod_Invasion.setInvasionEnded(this);
/* 1053 */     this.activationTimer = 0;
/* 1054 */     this.currentWave = 0;
/* 1055 */     this.errorState = 0;
/*      */   }
/*      */ 
/*      */   private void bindPlayers()
/*      */   {
/* 1062 */     List players = this.k.a(CallableItemName.class, this.boundingBoxToRadius);
/* 1063 */     for (CallableItemName entityPlayer : players)
/*      */     {
/* 1065 */       long time = System.currentTimeMillis();
/* 1066 */       if (!this.boundPlayers.containsKey(entityPlayer.bu))
/*      */       {
/* 1068 */         mod_Invasion.broadcastToAll(entityPlayer.bu + (entityPlayer.bu.toLowerCase().endsWith("s") ? "'" : "'s") + " life is now bound to the nexus");
/*      */       }
/* 1070 */       else if (time - ((Long)this.boundPlayers.get(entityPlayer.bu)).longValue() > 300000L)
/*      */       {
/* 1072 */         mod_Invasion.broadcastToAll(entityPlayer.bu + (entityPlayer.bu.toLowerCase().endsWith("s") ? "'" : "'s") + " life is now bound to the nexus");
/*      */       }
/* 1074 */       this.boundPlayers.put(entityPlayer.bu, Long.valueOf(time));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateMobList()
/*      */   {
/* 1081 */     this.mobList = this.k.a(EntityIMLiving.class, this.boundingBoxToRadius);
/* 1082 */     this.mobsSorted = false;
/*      */   }
/*      */ 
/*      */   protected void setMode(int i)
/*      */   {
/* 1088 */     this.mode = i;
/* 1089 */     if (this.mode == 0)
/* 1090 */       setActive(false);
/*      */     else
/* 1092 */       setActive(true);
/*      */   }
/*      */ 
/*      */   private void setActive(boolean flag)
/*      */   {
/* 1097 */     if (this.k != null)
/*      */     {
/* 1099 */       int meta = this.k.h(this.xCoord, this.yCoord, this.zCoord);
/* 1100 */       if (flag)
/*      */       {
/* 1102 */         this.k.b(this.xCoord, this.yCoord, this.zCoord, (meta & 0x4) == 0 ? meta + 4 : meta, 3);
/*      */       }
/*      */       else
/*      */       {
/* 1106 */         this.k.b(this.xCoord, this.yCoord, this.zCoord, (meta & 0x4) == 4 ? meta - 4 : meta, 3);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private int acquireEntities()
/*      */   {
/* 1116 */     BlockPistonExtension bb = this.boundingBoxToRadius.b(10.0D, 128.0D, 10.0D);
/*      */ 
/* 1118 */     List entities = this.k.a(EntityIMLiving.class, bb);
/* 1119 */     for (EntityIMLiving entity : entities)
/*      */     {
/* 1121 */       entity.acquiredByNexus(this);
/*      */     }
/* 1123 */     mod_Invasion.log("Acquired " + entities.size() + " entities after state restore");
/* 1124 */     return entities.size();
/*      */   }
/*      */ 
/*      */   private void theEnd()
/*      */   {
/* 1130 */     if (!this.k.I)
/*      */     {
/* 1132 */       stop();
/* 1133 */       long time = System.currentTimeMillis();
/* 1134 */       for (Map.Entry entry : this.boundPlayers.entrySet())
/*      */       {
/* 1136 */         if (time - ((Long)entry.getValue()).longValue() < 300000L)
/*      */         {
/* 1138 */           CallableItemName player = this.k.a((String)entry.getKey());
/* 1139 */           if (player != null)
/*      */           {
/* 1141 */             player.a(CombatTracker.k, 500.0F);
/* 1142 */             mod_Invasion.playGlobalSFX("random.explode");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1151 */       this.boundPlayers.clear();
/* 1152 */       killAllMobs();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void continuousNexusHurt()
/*      */   {
/* 1158 */     mod_Invasion.broadcastToAll("Nexus severely damaged!");
/* 1159 */     mod_Invasion.playGlobalSFX("random.explode");
/* 1160 */     killAllMobs();
/* 1161 */     this.waveSpawner.stop();
/* 1162 */     this.powerLevel = ((int)((this.powerLevel - (this.powerLevel - this.lastPowerLevel)) * 0.7F));
/* 1163 */     this.lastPowerLevel = this.powerLevel;
/* 1164 */     if (this.powerLevel < 0)
/*      */     {
/* 1166 */       this.powerLevel = 0;
/* 1167 */       stop();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void killAllMobs()
/*      */   {
/* 1177 */     List mobs = this.k.a(EntityIMLiving.class, this.boundingBoxToRadius);
/* 1178 */     for (EntityIMLiving mob : mobs)
/*      */     {
/* 1180 */       mob.a(CombatTracker.k, 500.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean zapEnemy(int sfx)
/*      */   {
/* 1186 */     if (this.mobList.size() > 0)
/*      */     {
/* 1188 */       if (!this.mobsSorted) {
/* 1189 */         Collections.sort(this.mobList, new ComparatorEntityDistance(this.xCoord, this.yCoord, this.zCoord));
/*      */       }
/* 1191 */       EntityIMLiving mob = (EntityIMLiving)this.mobList.remove(this.mobList.size() - 1);
/* 1192 */       mob.a(CombatTracker.k, 500.0F);
/* 1193 */       EntityIMBolt bolt = new EntityIMBolt(this.k, this.xCoord + 0.5D, this.yCoord + 0.5D, this.zCoord + 0.5D, mob.posX, mob.posY, mob.posZ, 15, sfx);
/* 1194 */       this.k.d(bolt);
/* 1195 */       return true;
/*      */     }
/*      */ 
/* 1199 */     return false;
/*      */   }
/*      */ 
/*      */   private boolean resumeSpawnerContinuous()
/*      */   {
/*      */     try
/*      */     {
/* 1207 */       mod_Invasion.tryGetInvasionPermission(this);
/* 1208 */       float difficulty = 1.0F + this.powerLevel / 4500;
/* 1209 */       tierLevel = 1.0F + this.powerLevel / 4500;
/* 1210 */       int timeSeconds = 240;
/* 1211 */       Wave wave = this.waveBuilder.generateWave(difficulty, tierLevel, timeSeconds);
/* 1212 */       this.mobsToKillInWave = ((int)(wave.getTotalMobAmount() * 0.8F));
/* 1213 */       mod_Invasion.log("Original mobs to kill: " + this.mobsToKillInWave);
/* 1214 */       this.mobsLeftInWave = (this.lastMobsLeftInWave = this.mobsToKillInWave - this.waveSpawner.resumeFromState(wave, this.spawnerElapsedRestore, this.spawnRadius));
/* 1215 */       return true;
/*      */     }
/*      */     catch (WaveSpawnerException e)
/*      */     {
/*      */       float tierLevel;
/* 1219 */       mod_Invasion.log("Error resuming spawner:" + e.getMessage());
/* 1220 */       this.waveSpawner.stop();
/* 1221 */       return 0;
/*      */     }
/*      */     finally
/*      */     {
/* 1225 */       mod_Invasion.setInvasionEnded(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean resumeSpawnerInvasion()
/*      */   {
/*      */     try
/*      */     {
/* 1233 */       mod_Invasion.tryGetInvasionPermission(this);
/* 1234 */       this.waveSpawner.resumeFromState(this.currentWave, this.spawnerElapsedRestore, this.spawnRadius);
/* 1235 */       return true;
/*      */     }
/*      */     catch (WaveSpawnerException e)
/*      */     {
/* 1239 */       mod_Invasion.log("Error resuming spawner:" + e.getMessage());
/* 1240 */       this.waveSpawner.stop();
/* 1241 */       return false;
/*      */     }
/*      */     finally
/*      */     {
/* 1245 */       mod_Invasion.setInvasionEnded(this);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void playSoundTo()
/*      */   {
/*      */   }
/*      */ 
/*      */   private void updateAI()
/*      */   {
/* 1257 */     this.attackerAI.update();
/*      */   }
/*      */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.TileEntityNexus
 * JD-Core Version:    0.6.2
 */