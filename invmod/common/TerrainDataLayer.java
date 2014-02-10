/*     */ package invmod.common;
/*     */ 
/*     */ import invmod.common.entity.PathAction;
/*     */ import invmod.common.entity.PathNode;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.server.management.LowerStringMap;
/*     */ import net.minecraft.tileentity.TileEntitySign;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.world.EnumGameType;
/*     */ import net.minecraft.world.biome.BiomeGenBeach;
/*     */ import net.minecraftforge.common.ForgeDirection;
/*     */ 
/*     */ public class TerrainDataLayer
/*     */   implements IBlockAccessExtended
/*     */ {
/*     */   public static final int EXT_DATA_SCAFFOLD_METAPOSITION = 16384;
/*     */   private EnumGameType world;
/*     */   private LowerStringMap dataLayer;
/*     */ 
/*     */   public TerrainDataLayer(EnumGameType world)
/*     */   {
/*  28 */     this.world = world;
/*  29 */     this.dataLayer = new LowerStringMap();
/*     */   }
/*     */ 
/*     */   public void setData(int x, int y, int z, Integer data)
/*     */   {
/*  35 */     this.dataLayer.a(PathNode.makeHash(x, y, z, PathAction.NONE), data);
/*     */   }
/*     */ 
/*     */   public int getLayeredData(int x, int y, int z)
/*     */   {
/*  41 */     int key = PathNode.makeHash(x, y, z, PathAction.NONE);
/*  42 */     if (this.dataLayer.b(key)) {
/*  43 */       return ((Integer)this.dataLayer.a(key)).intValue();
/*     */     }
/*  45 */     return 0;
/*     */   }
/*     */ 
/*     */   public void setAllData(LowerStringMap data)
/*     */   {
/*  50 */     this.dataLayer = data;
/*     */   }
/*     */ 
/*     */   public int a(int x, int y, int z)
/*     */   {
/*  56 */     return this.world.a(x, y, z);
/*     */   }
/*     */ 
/*     */   public TileEntitySign r(int x, int y, int z)
/*     */   {
/*  62 */     return this.world.r(x, y, z);
/*     */   }
/*     */ 
/*     */   public int h(int x, int y, int z, int meta)
/*     */   {
/*  68 */     return this.world.h(x, y, z, meta);
/*     */   }
/*     */ 
/*     */   public float i(int x, int y, int z, int meta)
/*     */   {
/*  74 */     return this.world.i(x, y, z, meta);
/*     */   }
/*     */ 
/*     */   public float q(int x, int y, int z)
/*     */   {
/*  80 */     return this.world.q(x, y, z);
/*     */   }
/*     */ 
/*     */   public int h(int x, int y, int z)
/*     */   {
/*  86 */     return this.world.h(x, y, z);
/*     */   }
/*     */ 
/*     */   public MaterialLogic g(int x, int y, int z)
/*     */   {
/*  92 */     return this.world.g(x, y, z);
/*     */   }
/*     */ 
/*     */   public boolean t(int x, int y, int z)
/*     */   {
/*  98 */     return this.world.t(x, y, z);
/*     */   }
/*     */ 
/*     */   public boolean u(int x, int y, int z)
/*     */   {
/* 104 */     return this.world.u(x, y, z);
/*     */   }
/*     */ 
/*     */   public boolean c(int x, int y, int z)
/*     */   {
/* 110 */     return this.world.c(x, y, z);
/*     */   }
/*     */ 
/*     */   public BiomeGenBeach a(int i, int j)
/*     */   {
/* 116 */     return this.world.a(i, j);
/*     */   }
/*     */ 
/*     */   public int R()
/*     */   {
/* 122 */     return this.world.R();
/*     */   }
/*     */ 
/*     */   public boolean T()
/*     */   {
/* 128 */     return this.world.T();
/*     */   }
/*     */ 
/*     */   public boolean w(int x, int y, int z)
/*     */   {
/* 134 */     return this.world.w(x, y, z);
/*     */   }
/*     */ 
/*     */   public MovingObjectPosition V()
/*     */   {
/* 140 */     return this.world.V();
/*     */   }
/*     */ 
/*     */   public int j(int var1, int var2, int var3, int var4)
/*     */   {
/* 146 */     return this.world.j(var1, var2, var3, var4);
/*     */   }
/*     */ 
/*     */   public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side, boolean _default)
/*     */   {
/* 152 */     return this.world.isBlockSolidOnSide(x, y, z, side, _default);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.TerrainDataLayer
 * JD-Core Version:    0.6.2
 */