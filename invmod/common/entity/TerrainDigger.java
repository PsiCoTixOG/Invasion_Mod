/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.INotifyTask;
/*    */ import invmod.common.util.IPosition;
/*    */ import net.minecraft.block.BlockEndPortal;
/*    */ import net.minecraft.world.EnumGameType;
/*    */ 
/*    */ public class TerrainDigger
/*    */   implements ITerrainDig, INotifyTask
/*    */ {
/*    */   private ICanDig digger;
/*    */   private ITerrainModify modifier;
/*    */   private float digRate;
/*    */ 
/*    */   public TerrainDigger(ICanDig digger, ITerrainModify modifier, float digRate)
/*    */   {
/* 11 */     this.digger = digger;
/* 12 */     this.modifier = modifier;
/* 13 */     this.digRate = digRate;
/*    */   }
/*    */ 
/*    */   public void setDigRate(float digRate)
/*    */   {
/* 18 */     this.digRate = digRate;
/*    */   }
/*    */ 
/*    */   public float getDigRate()
/*    */   {
/* 23 */     return this.digRate;
/*    */   }
/*    */ 
/*    */   public boolean askClearPosition(int x, int y, int z, INotifyTask onFinished, float costMultiplier)
/*    */   {
/* 29 */     IPosition[] removals = this.digger.getBlockRemovalOrder(x, y, z);
/* 30 */     ModifyBlockEntry[] removalEntries = new ModifyBlockEntry[removals.length];
/* 31 */     int entries = 0;
/* 32 */     for (int i = 0; i < removals.length; i++)
/*    */     {
/* 34 */       int id = this.digger.getTerrain().a(removals[i].getXCoord(), removals[i].getYCoord(), removals[i].getZCoord());
/* 35 */       if ((id != 0) && (!BlockEndPortal.s[id].b(this.digger.getTerrain(), removals[i].getXCoord(), removals[i].getYCoord(), removals[i].getZCoord())))
/*    */       {
/* 39 */         if (!this.digger.canClearBlock(removals[i].getXCoord(), removals[i].getYCoord(), removals[i].getZCoord()))
/*    */         {
/* 41 */           return false;
/*    */         }
/*    */ 
/* 44 */         removalEntries[(entries++)] = new ModifyBlockEntry(removals[i].getXCoord(), removals[i].getYCoord(), removals[i].getZCoord(), 0, (int)(costMultiplier * this.digger.getBlockRemovalCost(removals[i].getXCoord(), removals[i].getYCoord(), removals[i].getZCoord()) / this.digRate));
/*    */       }
/*    */     }
/* 46 */     ModifyBlockEntry[] finalEntries = new ModifyBlockEntry[entries];
/* 47 */     System.arraycopy(removalEntries, 0, finalEntries, 0, entries);
/* 48 */     return this.modifier.requestTask(finalEntries, onFinished, this);
/*    */   }
/*    */ 
/*    */   public boolean askRemoveBlock(int x, int y, int z, INotifyTask onFinished, float costMultiplier)
/*    */   {
/* 54 */     if (!this.digger.canClearBlock(x, y, z)) {
/* 55 */       return false;
/*    */     }
/* 57 */     ModifyBlockEntry[] removalEntries = { new ModifyBlockEntry(x, y, z, 0, (int)(costMultiplier * this.digger.getBlockRemovalCost(x, y, z) / this.digRate)) };
/* 58 */     return this.modifier.requestTask(removalEntries, onFinished, this);
/*    */   }
/*    */ 
/*    */   public void notifyTask(int result)
/*    */   {
/* 64 */     if (result == 0)
/*    */     {
/* 66 */       ModifyBlockEntry entry = this.modifier.getLastBlockModified();
/* 67 */       this.digger.onBlockRemoved(entry.getXCoord(), entry.getYCoord(), entry.getZCoord(), entry.getOldBlockId());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.TerrainDigger
 * JD-Core Version:    0.6.2
 */