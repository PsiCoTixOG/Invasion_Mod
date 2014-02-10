/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.INotifyTask;
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import invmod.common.util.IPosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class TerrainBuilder
/*     */   implements ITerrainBuild
/*     */ {
/*     */   private static final float LADDER_COST = 25.0F;
/*     */   private static final float PLANKS_COST = 45.0F;
/*     */   private static final float COBBLE_COST = 65.0F;
/*     */   private EntityIMLiving theEntity;
/*     */   private ITerrainModify modifier;
/*     */   private float buildRate;
/*     */ 
/*     */   public TerrainBuilder(EntityIMLiving entity, ITerrainModify modifier, float buildRate)
/*     */   {
/*  17 */     this.theEntity = entity;
/*  18 */     this.modifier = modifier;
/*  19 */     this.buildRate = buildRate;
/*     */   }
/*     */ 
/*     */   public void setBuildRate(float buildRate)
/*     */   {
/*  24 */     this.buildRate = buildRate;
/*     */   }
/*     */ 
/*     */   public float getBuildRate()
/*     */   {
/*  29 */     return this.buildRate;
/*     */   }
/*     */ 
/*     */   public boolean askBuildScaffoldLayer(IPosition pos, INotifyTask asker)
/*     */   {
/*  35 */     if (this.modifier.isReadyForTask(asker))
/*     */     {
/*  37 */       Scaffold scaffold = this.theEntity.getNexus().getAttackerAI().getScaffoldAt(pos);
/*  38 */       if (scaffold != null)
/*     */       {
/*  40 */         int height = pos.getYCoord() - scaffold.getYCoord();
/*  41 */         int xOffset = invmod.common.util.CoordsInt.offsetAdjX[scaffold.getOrientation()];
/*  42 */         int zOffset = invmod.common.util.CoordsInt.offsetAdjZ[scaffold.getOrientation()];
/*  43 */         int id = this.theEntity.q.a(pos.getXCoord() + xOffset, pos.getYCoord() - 1, pos.getZCoord() + zOffset);
/*  44 */         List modList = new ArrayList();
/*     */ 
/*  46 */         if (height == 1)
/*     */         {
/*  48 */           if (!BlockEndPortal.isNormalCube(id)) {
/*  49 */             modList.add(new ModifyBlockEntry(pos.getXCoord() + xOffset, pos.getYCoord() - 1, pos.getZCoord() + zOffset, BlockEndPortal.C.blockID, (int)(45.0F / this.buildRate)));
/*     */           }
/*  51 */           id = this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord());
/*  52 */           if (id == 0) {
/*  53 */             modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord(), BlockEndPortal.aK.blockID, (int)(25.0F / this.buildRate)));
/*     */           }
/*     */         }
/*  56 */         id = this.theEntity.q.a(pos.getXCoord() + xOffset, pos.getYCoord(), pos.getZCoord() + zOffset);
/*  57 */         if (!BlockEndPortal.isNormalCube(id)) {
/*  58 */           modList.add(new ModifyBlockEntry(pos.getXCoord() + xOffset, pos.getYCoord(), pos.getZCoord() + zOffset, BlockEndPortal.C.blockID, (int)(45.0F / this.buildRate)));
/*     */         }
/*  60 */         id = this.theEntity.q.a(pos.getXCoord(), pos.getYCoord(), pos.getZCoord());
/*  61 */         if (id != BlockEndPortal.aK.blockID) {
/*  62 */           modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord(), pos.getZCoord(), BlockEndPortal.aK.blockID, (int)(25.0F / this.buildRate)));
/*     */         }
/*     */ 
/*  65 */         if (scaffold.isLayerPlatform(height))
/*     */         {
/*  67 */           for (int i = 0; i < 8; i++)
/*     */           {
/*  69 */             if ((invmod.common.util.CoordsInt.offsetRing1X[i] != xOffset) || (invmod.common.util.CoordsInt.offsetRing1Z[i] != zOffset))
/*     */             {
/*  72 */               id = this.theEntity.q.a(pos.getXCoord() + invmod.common.util.CoordsInt.offsetRing1X[i], pos.getYCoord(), pos.getZCoord() + invmod.common.util.CoordsInt.offsetRing1Z[i]);
/*  73 */               if (!BlockEndPortal.isNormalCube(id))
/*  74 */                 modList.add(new ModifyBlockEntry(pos.getXCoord() + invmod.common.util.CoordsInt.offsetRing1X[i], pos.getYCoord(), pos.getZCoord() + invmod.common.util.CoordsInt.offsetRing1Z[i], BlockEndPortal.C.blockID, (int)(45.0F / this.buildRate)));
/*     */             }
/*     */           }
/*     */         }
/*  78 */         if (modList.size() > 0)
/*  79 */           return this.modifier.requestTask((ModifyBlockEntry[])modList.toArray(new ModifyBlockEntry[modList.size()]), asker, null);
/*     */       }
/*     */     }
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean askBuildLadderTower(IPosition pos, int orientation, int layersToBuild, INotifyTask asker)
/*     */   {
/*  88 */     if (this.modifier.isReadyForTask(asker))
/*     */     {
/*  90 */       int xOffset = orientation == 1 ? -1 : orientation == 0 ? 1 : 0;
/*  91 */       int zOffset = orientation == 3 ? -1 : orientation == 2 ? 1 : 0;
/*  92 */       List modList = new ArrayList();
/*     */ 
/*  94 */       int id = this.theEntity.q.a(pos.getXCoord() + xOffset, pos.getYCoord() - 1, pos.getZCoord() + zOffset);
/*  95 */       if (!BlockEndPortal.isNormalCube(id)) {
/*  96 */         modList.add(new ModifyBlockEntry(pos.getXCoord() + xOffset, pos.getYCoord() - 1, pos.getZCoord() + zOffset, BlockEndPortal.C.blockID, (int)(45.0F / this.buildRate)));
/*     */       }
/*  98 */       id = this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord());
/*  99 */       if (id == 0) {
/* 100 */         modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord(), BlockEndPortal.aK.blockID, (int)(25.0F / this.buildRate)));
/*     */       }
/* 102 */       for (int i = 0; i < layersToBuild; i++)
/*     */       {
/* 104 */         id = this.theEntity.q.a(pos.getXCoord() + xOffset, pos.getYCoord() + i, pos.getZCoord() + zOffset);
/* 105 */         if (!BlockEndPortal.isNormalCube(id)) {
/* 106 */           modList.add(new ModifyBlockEntry(pos.getXCoord() + xOffset, pos.getYCoord() + i, pos.getZCoord() + zOffset, BlockEndPortal.C.blockID, (int)(45.0F / this.buildRate)));
/*     */         }
/* 108 */         id = this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() + i, pos.getZCoord());
/* 109 */         if (id != BlockEndPortal.aK.blockID) {
/* 110 */           modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord() + i, pos.getZCoord(), BlockEndPortal.aK.blockID, (int)(25.0F / this.buildRate)));
/*     */         }
/*     */       }
/* 113 */       if (modList.size() > 0)
/* 114 */         return this.modifier.requestTask((ModifyBlockEntry[])modList.toArray(new ModifyBlockEntry[modList.size()]), asker, null);
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean askBuildLadder(IPosition pos, INotifyTask asker)
/*     */   {
/* 122 */     if (this.modifier.isReadyForTask(asker))
/*     */     {
/* 124 */       List modList = new ArrayList();
/* 125 */       int id = this.theEntity.q.a(pos.getXCoord(), pos.getYCoord(), pos.getZCoord());
/* 126 */       if (id != BlockEndPortal.aK.blockID)
/*     */       {
/* 128 */         if (EntityIMPigEngy.canPlaceLadderAt(this.theEntity.q, pos.getXCoord(), pos.getYCoord(), pos.getZCoord()))
/* 129 */           modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord(), pos.getZCoord(), BlockEndPortal.aK.blockID, (int)(25.0F / this.buildRate)));
/*     */         else {
/* 131 */           return false;
/*     */         }
/*     */       }
/*     */ 
/* 135 */       id = this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() - 2, pos.getZCoord());
/* 136 */       if ((id > 0) && (BlockEndPortal.s[id].cU.isSolid()))
/*     */       {
/* 138 */         if (EntityIMPigEngy.canPlaceLadderAt(this.theEntity.q, pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord())) {
/* 139 */           modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord(), BlockEndPortal.aK.blockID, (int)(25.0F / this.buildRate)));
/*     */         }
/*     */       }
/* 142 */       if (modList.size() > 0)
/* 143 */         return this.modifier.requestTask((ModifyBlockEntry[])modList.toArray(new ModifyBlockEntry[modList.size()]), asker, null);
/*     */     }
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean askBuildBridge(IPosition pos, INotifyTask asker)
/*     */   {
/* 151 */     if (this.modifier.isReadyForTask(asker))
/*     */     {
/* 153 */       List modList = new ArrayList();
/* 154 */       if (this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord()) == 0)
/*     */       {
/* 156 */         if ((this.theEntity.avoidsBlock(this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() - 2, pos.getZCoord()))) || (this.theEntity.avoidsBlock(this.theEntity.q.a(pos.getXCoord(), pos.getYCoord() - 3, pos.getZCoord()))))
/*     */         {
/* 159 */           modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord(), BlockEndPortal.B.blockID, (int)(65.0F / this.buildRate)));
/*     */         }
/*     */         else
/*     */         {
/* 163 */           modList.add(new ModifyBlockEntry(pos.getXCoord(), pos.getYCoord() - 1, pos.getZCoord(), BlockEndPortal.C.blockID, (int)(45.0F / this.buildRate)));
/*     */         }
/*     */ 
/* 166 */         if (modList.size() > 0)
/* 167 */           return this.modifier.requestTask((ModifyBlockEntry[])modList.toArray(new ModifyBlockEntry[modList.size()]), asker, null);
/*     */       }
/*     */     }
/* 170 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.TerrainBuilder
 * JD-Core Version:    0.6.2
 */