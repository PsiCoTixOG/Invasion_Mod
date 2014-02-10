/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.util.Distance;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class TerrainModifier
/*     */   implements ITerrainModify
/*     */ {
/*     */   private static final float DEFAULT_REACH = 2.0F;
/*     */   private EntityLivingBase theEntity;
/*     */   private INotifyTask taskSetter;
/*     */   private INotifyTask blockNotify;
/*     */   private List<ModifyBlockEntry> modList;
/*     */   private ModifyBlockEntry nextEntry;
/*     */   private ModifyBlockEntry lastEntry;
/*     */   private int entryIndex;
/*     */   private int timer;
/*     */   private float reach;
/*     */   private boolean outOfRangeFlag;
/*     */   private boolean terrainFailFlag;
/*     */ 
/*     */   public TerrainModifier(EntityLivingBase entity, float defaultReach)
/*     */   {
/*  38 */     this.theEntity = entity;
/*  39 */     this.modList = new ArrayList();
/*  40 */     this.entryIndex = 0;
/*  41 */     this.timer = 0;
/*  42 */     this.reach = defaultReach;
/*     */   }
/*     */ 
/*     */   public void onUpdate()
/*     */   {
/*  50 */     taskUpdate();
/*     */   }
/*     */ 
/*     */   public boolean isReadyForTask(INotifyTask asker)
/*     */   {
/*  59 */     return (this.modList.size() == 0) || (this.taskSetter == asker);
/*     */   }
/*     */ 
/*     */   public void cancelTask()
/*     */   {
/*  64 */     endTask();
/*     */   }
/*     */ 
/*     */   public boolean isBusy()
/*     */   {
/*  69 */     return this.timer > 0;
/*     */   }
/*     */ 
/*     */   public boolean requestTask(ModifyBlockEntry[] entries, INotifyTask onFinished, INotifyTask onBlockChange)
/*     */   {
/*  85 */     if (isReadyForTask(onFinished))
/*     */     {
/*  87 */       for (ModifyBlockEntry entry : entries) {
/*  88 */         this.modList.add(entry);
/*     */       }
/*  90 */       this.taskSetter = onFinished;
/*  91 */       this.blockNotify = onBlockChange;
/*  92 */       return true;
/*     */     }
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   public ModifyBlockEntry getLastBlockModified()
/*     */   {
/* 104 */     return this.lastEntry;
/*     */   }
/*     */ 
/*     */   private void taskUpdate()
/*     */   {
/* 113 */     if (this.timer > 1)
/*     */     {
/* 115 */       this.timer -= 1;
/* 116 */       return;
/*     */     }
/* 118 */     if (this.timer == 1)
/*     */     {
/* 120 */       this.entryIndex += 1;
/* 121 */       this.timer = 0;
/* 122 */       int result = changeBlock(this.nextEntry) ? 0 : 1;
/* 123 */       this.lastEntry = this.nextEntry;
/* 124 */       if (this.blockNotify != null) {
/* 125 */         this.blockNotify.notifyTask(result);
/*     */       }
/*     */     }
/*     */ 
/* 129 */     if (this.entryIndex < this.modList.size())
/*     */     {
/* 131 */       this.nextEntry = ((ModifyBlockEntry)this.modList.get(this.entryIndex));
/* 132 */       while (isTerrainIdentical(this.nextEntry))
/*     */       {
/* 134 */         this.entryIndex += 1;
/* 135 */         if (this.entryIndex < this.modList.size())
/*     */         {
/* 137 */           this.nextEntry = ((ModifyBlockEntry)this.modList.get(this.entryIndex));
/*     */         }
/*     */         else
/*     */         {
/* 141 */           endTask();
/* 142 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 146 */       this.timer = this.nextEntry.getCost();
/* 147 */       if (this.timer == 0)
/* 148 */         this.timer = 1;
/*     */     }
/* 150 */     else if (this.modList.size() > 0)
/*     */     {
/* 152 */       endTask();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void endTask()
/*     */   {
/* 161 */     this.entryIndex = 0;
/* 162 */     this.timer = 0;
/* 163 */     this.modList.clear();
/* 164 */     if (this.taskSetter != null)
/* 165 */       this.taskSetter.notifyTask(this.outOfRangeFlag ? 1 : this.terrainFailFlag ? 2 : 0);
/*     */   }
/*     */ 
/*     */   private boolean changeBlock(ModifyBlockEntry entry)
/*     */   {
/* 174 */     if (Distance.distanceBetween(this.theEntity.posX, this.theEntity.posY + this.theEntity.height / 2.0F, this.theEntity.posZ, entry.getXCoord() + 0.5D, entry.getYCoord() + 0.5D, entry.getZCoord() + 0.5D) > this.reach)
/*     */     {
/* 176 */       this.outOfRangeFlag = true;
/* 177 */       return false;
/*     */     }
/*     */ 
/* 180 */     int newId = entry.getNewBlockId();
/* 181 */     int oldId = this.theEntity.q.a(entry.getXCoord(), entry.getYCoord(), entry.getZCoord());
/* 182 */     int oldMeta = this.theEntity.q.h(entry.getXCoord(), entry.getYCoord(), entry.getZCoord());
/* 183 */     entry.setOldBlockId(oldId);
/* 184 */     if (oldId == mod_Invasion.blockNexus.cF)
/*     */     {
/* 186 */       this.terrainFailFlag = true;
/* 187 */       return false;
/*     */     }
/*     */ 
/* 190 */     boolean succeeded = this.theEntity.q.f(entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), entry.getNewBlockId(), entry.getNewBlockMeta(), 3);
/* 191 */     if (succeeded)
/*     */     {
/* 193 */       if (newId == 0)
/*     */       {
/* 195 */         BlockEndPortal block = BlockEndPortal.s[oldId];
/* 196 */         block.g(this.theEntity.q, entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), oldMeta);
/* 197 */         block.c(this.theEntity.q, entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), oldMeta, 0);
/*     */       }
/* 199 */       if (newId == BlockEndPortal.aK.blockID)
/*     */       {
/* 201 */         int meta = BlockEndPortal.s[newId].a(this.theEntity.q, entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), 0, 0.0F, 0.0F, 0.0F, oldMeta);
/* 202 */         this.theEntity.q.b(entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), meta, 3);
/*     */ 
/* 204 */         BlockEndPortal.s[BlockEndPortal.aK.blockID].k(this.theEntity.q, entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), meta);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 209 */       this.terrainFailFlag = true;
/*     */     }
/* 211 */     return succeeded;
/*     */   }
/*     */ 
/*     */   private boolean isTerrainIdentical(ModifyBlockEntry entry)
/*     */   {
/* 220 */     if ((this.theEntity.q.a(entry.getXCoord(), entry.getYCoord(), entry.getZCoord()) == entry.getNewBlockId()) && (this.theEntity.q.h(entry.getXCoord(), entry.getYCoord(), entry.getZCoord()) == entry.getNewBlockMeta()))
/*     */     {
/* 223 */       return true;
/*     */     }
/* 225 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.TerrainModifier
 * JD-Core Version:    0.6.2
 */