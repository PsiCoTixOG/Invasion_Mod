/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.inventory.SlotBrewingStandPotion;
/*     */ import net.minecraft.inventory.SlotCrafting;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.util.FoodStats;
/*     */ 
/*     */ public class ContainerNexus extends FoodStats
/*     */ {
/*     */   private TileEntityNexus nexus;
/*     */   private int activationTimer;
/*     */   private int currentWave;
/*     */   private int nexusLevel;
/*     */   private int nexusKills;
/*     */   private int spawnRadius;
/*     */   private int generation;
/*     */   private int powerLevel;
/*     */   private int cookTime;
/*     */   private int mode;
/*     */ 
/*     */   public ContainerNexus(PlayerCapabilities inventoryplayer, TileEntityNexus tileEntityNexus)
/*     */   {
/*  17 */     this.mode = 0;
/*  18 */     this.activationTimer = 0;
/*  19 */     this.currentWave = 0;
/*  20 */     this.nexusLevel = 0;
/*  21 */     this.nexusKills = 0;
/*  22 */     this.spawnRadius = 0;
/*  23 */     this.generation = 0;
/*  24 */     this.powerLevel = 0;
/*  25 */     this.cookTime = 0;
/*  26 */     this.nexus = tileEntityNexus;
/*  27 */     a(new SlotCrafting(tileEntityNexus, 0, 32, 33));
/*  28 */     a(new SlotOutput(tileEntityNexus, 1, 102, 33));
/*  29 */     for (int i = 0; i < 3; i++)
/*     */     {
/*  31 */       for (int k = 0; k < 9; k++)
/*     */       {
/*  33 */         a(new SlotCrafting(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  38 */     for (int j = 0; j < 9; j++)
/*     */     {
/*  40 */       a(new SlotCrafting(inventoryplayer, j, 8 + j * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void b()
/*     */   {
/*  48 */     super.b();
/*  49 */     for (int i = 0; i < this.e.size(); i++)
/*     */     {
/*  51 */       SlotBrewingStandPotion icrafting = (SlotBrewingStandPotion)this.e.get(i);
/*  52 */       if (this.activationTimer != this.nexus.getActivationTimer())
/*     */       {
/*  54 */         icrafting.a(this, 0, this.nexus.getActivationTimer());
/*     */       }
/*  56 */       if (this.mode != this.nexus.getMode())
/*     */       {
/*  58 */         icrafting.a(this, 1, this.nexus.getMode());
/*     */       }
/*  60 */       if (this.currentWave != this.nexus.getCurrentWave())
/*     */       {
/*  62 */         icrafting.a(this, 2, this.nexus.getCurrentWave());
/*     */       }
/*  64 */       if (this.nexusLevel != this.nexus.getNexusLevel())
/*     */       {
/*  66 */         icrafting.a(this, 3, this.nexus.getNexusLevel());
/*     */       }
/*  68 */       if (this.nexusKills != this.nexus.getNexusKills())
/*     */       {
/*  70 */         icrafting.a(this, 4, this.nexus.getNexusKills());
/*     */       }
/*  72 */       if (this.spawnRadius != this.nexus.getSpawnRadius())
/*     */       {
/*  74 */         icrafting.a(this, 5, this.nexus.getSpawnRadius());
/*     */       }
/*  76 */       if (this.generation != this.nexus.getGeneration())
/*     */       {
/*  78 */         icrafting.a(this, 6, this.nexus.getGeneration());
/*     */       }
/*  80 */       if (this.generation != this.nexus.getNexusPowerLevel())
/*     */       {
/*  82 */         icrafting.a(this, 7, this.nexus.getNexusPowerLevel());
/*     */       }
/*  84 */       if (this.generation != this.nexus.getCookTime())
/*     */       {
/*  86 */         icrafting.a(this, 9, this.nexus.getCookTime());
/*     */       }
/*     */     }
/*     */ 
/*  90 */     this.activationTimer = this.nexus.getActivationTimer();
/*  91 */     this.mode = this.nexus.getMode();
/*  92 */     this.currentWave = this.nexus.getCurrentWave();
/*  93 */     this.nexusLevel = this.nexus.getNexusLevel();
/*  94 */     this.nexusKills = this.nexus.getNexusKills();
/*  95 */     this.spawnRadius = this.nexus.getSpawnRadius();
/*  96 */     this.generation = this.nexus.getGeneration();
/*  97 */     this.powerLevel = this.nexus.getNexusPowerLevel();
/*  98 */     this.cookTime = this.nexus.getCookTime();
/*     */   }
/*     */ 
/*     */   public void b(int i, int j)
/*     */   {
/* 104 */     if (i == 0)
/*     */     {
/* 106 */       this.nexus.setActivationTimer(j);
/*     */     }
/* 108 */     else if (i == 1)
/*     */     {
/* 110 */       this.nexus.setMode(j);
/*     */     }
/* 112 */     else if (i == 2)
/*     */     {
/* 114 */       this.nexus.setWave(j);
/*     */     }
/* 116 */     else if (i == 3)
/*     */     {
/* 118 */       this.nexus.setNexusLevel(j);
/*     */     }
/* 120 */     else if (i == 4)
/*     */     {
/* 122 */       this.nexus.setNexusKills(j);
/*     */     }
/* 124 */     else if (i == 5)
/*     */     {
/* 126 */       this.nexus.setSpawnRadius(j);
/*     */     }
/* 128 */     else if (i == 6)
/*     */     {
/* 130 */       this.nexus.setGeneration(j);
/*     */     }
/* 132 */     else if (i == 7)
/*     */     {
/* 134 */       this.nexus.setNexusPowerLevel(j);
/*     */     }
/* 136 */     else if (i == 8)
/*     */     {
/* 138 */       this.nexus.setCookTime(j);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean a(CallableItemName entityplayer)
/*     */   {
/* 145 */     return this.nexus.a(entityplayer);
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial b(CallableItemName player, int i)
/*     */   {
/* 151 */     EnumToolMaterial itemstack = null;
/* 152 */     SlotCrafting slot = (SlotCrafting)this.c.get(i);
/* 153 */     if ((slot != null) && (slot.getHasStack()))
/*     */     {
/* 155 */       EnumToolMaterial itemstack1 = slot.d();
/* 156 */       itemstack = itemstack1.m();
/* 157 */       if (i == 1)
/*     */       {
/* 159 */         if (!a(itemstack1, 2, 38, true))
/*     */         {
/* 161 */           return null;
/*     */         }
/*     */       }
/* 164 */       else if ((i >= 2) && (i < 29))
/*     */       {
/* 166 */         if (!a(itemstack1, 29, 38, false))
/*     */         {
/* 168 */           return null;
/*     */         }
/*     */       }
/* 171 */       else if ((i >= 29) && (i < 38))
/*     */       {
/* 173 */         if (!a(itemstack1, 2, 29, false))
/*     */         {
/* 175 */           return null;
/*     */         }
/*     */       }
/* 178 */       else if (!a(itemstack1, 2, 38, false))
/*     */       {
/* 180 */         return null;
/*     */       }
/* 182 */       if (itemstack1.STONE == 0)
/*     */       {
/* 184 */         slot.c(null);
/*     */       }
/*     */       else {
/* 187 */         slot.onSlotChanged();
/*     */       }
/* 189 */       if (itemstack1.STONE != itemstack.STONE)
/*     */       {
/* 191 */         slot.a(player, itemstack1);
/*     */       }
/*     */       else {
/* 194 */         return null;
/*     */       }
/*     */     }
/* 197 */     return itemstack;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.ContainerNexus
 * JD-Core Version:    0.6.2
 */