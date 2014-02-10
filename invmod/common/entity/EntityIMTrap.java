/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.mod_Invasion;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.block.BlockPistonExtension;
/*     */ import net.minecraft.block.material.MaterialLogic;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.item.EntityFallingSand;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMTrap extends nm
/*     */ {
/*     */   public static final int TRAP_DEFAULT = 0;
/*     */   public static final int TRAP_RIFT = 1;
/*     */   public static final int TRAP_FIRE = 2;
/*     */   private static final int ARM_TIME = 60;
/*     */   private static final int META_CHANGED = 29;
/*     */   private static final int META_TYPE = 30;
/*     */   private static final int META_EMPTY = 31;
/*     */   private int trapType;
/*     */   private int ticks;
/*     */   private boolean isEmpty;
/*     */   private byte metaChanged;
/*     */   private boolean fromLoaded;
/*     */ 
/*     */   public EntityIMTrap(ColorizerGrass world)
/*     */   {
/*  38 */     super(world);
/*  39 */     a(0.5F, 0.28F);
/*  40 */     this.N = 0.0F;
/*  41 */     this.ticks = 0;
/*  42 */     this.isEmpty = false;
/*  43 */     this.trapType = 0;
/*  44 */     if (world.I)
/*  45 */       this.metaChanged = 1;
/*     */     else {
/*  47 */       this.metaChanged = 0;
/*     */     }
/*  49 */     this.ah.a(29, Byte.valueOf(this.metaChanged));
/*  50 */     this.ah.a(30, Integer.valueOf(this.trapType));
/*  51 */     this.ah.a(31, Byte.valueOf((byte)(this.isEmpty ? 0 : 1)));
/*     */   }
/*     */ 
/*     */   public EntityIMTrap(ColorizerGrass world, double x, double y, double z)
/*     */   {
/*  56 */     this(world, x, y, z, 0);
/*     */   }
/*     */ 
/*     */   public EntityIMTrap(ColorizerGrass world, double x, double y, double z, int trapType)
/*     */   {
/*  61 */     this(world);
/*  62 */     this.trapType = trapType;
/*  63 */     this.ah.b(30, Integer.valueOf(trapType));
/*  64 */     b(x, y, z, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public void l_()
/*     */   {
/*  70 */     super.l_();
/*  71 */     this.ticks += 1;
/*  72 */     if (this.q.I)
/*     */     {
/*  74 */       if ((this.metaChanged != this.ah.a(29)) || (this.ticks % 20 == 0))
/*     */       {
/*  76 */         this.metaChanged = this.ah.a(29);
/*  77 */         this.trapType = this.ah.c(30);
/*  78 */         boolean wasEmpty = this.isEmpty;
/*  79 */         this.isEmpty = (this.ah.a(31) == 0);
/*  80 */         if ((this.isEmpty) && (!wasEmpty) && (this.trapType == 1))
/*  81 */           doRiftParticles();
/*     */       }
/*  83 */       return;
/*     */     }
/*     */ 
/*  86 */     if (!isValidPlacement())
/*     */     {
/*  88 */       EntityFallingSand entityitem = new EntityFallingSand(this.q, this.u, this.v, this.w, new EnumToolMaterial(mod_Invasion.itemIMTrap.itemID, 1, 0));
/*  89 */       entityitem.metadata = 10;
/*  90 */       this.q.d(entityitem);
/*  91 */       w();
/*     */     }
/*     */ 
/*  94 */     if ((this.q.I) || ((!this.isEmpty) && (this.ticks < 60))) {
/*  95 */       return;
/*     */     }
/*     */ 
/*  99 */     List entities = this.q.a(EntityLeashKnot.class, this.E);
/* 100 */     if ((entities.size() > 0) && (!this.isEmpty))
/*     */     {
/* 102 */       for (EntityLeashKnot entity : entities)
/*     */       {
/* 104 */         if (trapEffect(entity))
/*     */         {
/* 106 */           setEmpty();
/* 107 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean trapEffect(EntityLeashKnot triggerEntity)
/*     */   {
/* 115 */     if (this.trapType == 0)
/*     */     {
/* 117 */       triggerEntity.a(CombatTracker.j, 4.0F);
/*     */     }
/* 119 */     else if (this.trapType == 1)
/*     */     {
/* 121 */       triggerEntity.a(CombatTracker.k, (triggerEntity instanceof CallableItemName) ? 12.0F : 38.0F);
/*     */ 
/* 123 */       List entities = this.q.b(this, this.E.b(1.899999976158142D, 1.0D, 1.899999976158142D));
/* 124 */       for (nm entity : entities)
/*     */       {
/* 126 */         entity.a(CombatTracker.k, 8.0F);
/* 127 */         if ((entity instanceof EntityIMLiving)) {
/* 128 */           ((EntityIMLiving)entity).stunEntity(60);
/*     */         }
/*     */       }
/* 131 */       this.q.a(this, "random.break", 1.5F, 1.0F * (this.ab.nextFloat() * 0.25F + 0.55F));
/*     */     }
/* 137 */     else if (this.trapType == 2)
/*     */     {
/* 139 */       this.q.a(this, "invmod:fireball", 1.5F, 1.15F / (this.ab.nextFloat() * 0.3F + 1.0F));
/* 140 */       doFireball(1.1F, 8);
/*     */     }
/*     */ 
/* 143 */     return true;
/*     */   }
/*     */ 
/*     */   public void b_(CallableItemName entityPlayer)
/*     */   {
/* 149 */     if ((!this.q.I) && (this.ticks > 30) && (this.isEmpty))
/*     */     {
/* 151 */       if (entityPlayer.bn.a(new EnumToolMaterial(mod_Invasion.itemIMTrap, 1, 0)))
/*     */       {
/* 153 */         this.q.a(this, "random.pop", 0.2F, ((this.ab.nextFloat() - this.ab.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 154 */         entityPlayer.a(this, 1);
/* 155 */         w();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean c(CallableItemName entityPlayer)
/*     */   {
/* 163 */     if ((this.q.I) || (this.isEmpty)) {
/* 164 */       return false;
/*     */     }
/* 166 */     EnumToolMaterial itemStack = entityPlayer.bn.h();
/* 167 */     if ((itemStack != null) && (itemStack.EMERALD == mod_Invasion.itemProbe.itemID) && (itemStack.k() >= 1))
/*     */     {
/* 169 */       EntityFallingSand entityitem = new EntityFallingSand(this.q, this.u, this.v, this.w, new EnumToolMaterial(mod_Invasion.itemIMTrap.itemID, 1, this.trapType));
/* 170 */       entityitem.metadata = 5;
/* 171 */       this.q.d(entityitem);
/* 172 */       w();
/* 173 */       return true;
/*     */     }
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 180 */     return this.isEmpty;
/*     */   }
/*     */ 
/*     */   public int getTrapType()
/*     */   {
/* 185 */     return this.trapType;
/*     */   }
/*     */ 
/*     */   public boolean isValidPlacement()
/*     */   {
/* 190 */     return (this.q.u(LongHashMapEntry.c(this.u), LongHashMapEntry.c(this.v) - 1, LongHashMapEntry.c(this.w))) && (this.q.a(EntityIMTrap.class, this.E).size() < 2);
/*     */   }
/*     */ 
/*     */   public boolean K()
/*     */   {
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*     */   }
/*     */ 
/*     */   public float R()
/*     */   {
/* 207 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   protected void a(NBTTagByte nbttagcompound)
/*     */   {
/* 213 */     this.isEmpty = nbttagcompound.n("isEmpty");
/* 214 */     this.trapType = nbttagcompound.e("type");
/* 215 */     this.ah.b(31, Byte.valueOf((byte)(this.isEmpty ? 0 : 1)));
/* 216 */     this.ah.b(30, Integer.valueOf(this.trapType));
/*     */   }
/*     */ 
/*     */   protected void b(NBTTagByte nbttagcompound)
/*     */   {
/* 222 */     nbttagcompound.a("isEmpty", this.isEmpty);
/* 223 */     nbttagcompound.a("type", this.trapType);
/*     */   }
/*     */ 
/*     */   private void setEmpty()
/*     */   {
/* 228 */     this.isEmpty = true;
/* 229 */     this.ticks = 0;
/* 230 */     this.ah.b(31, Byte.valueOf((byte)(this.isEmpty ? 0 : 1)));
/* 231 */     this.ah.b(29, Byte.valueOf((byte)(this.ah.a(29) == 0 ? 1 : 0)));
/*     */   }
/*     */ 
/*     */   private void doFireball(float size, int initialDamage)
/*     */   {
/* 236 */     int x = LongHashMapEntry.c(this.u);
/* 237 */     int y = LongHashMapEntry.c(this.v);
/* 238 */     int z = LongHashMapEntry.c(this.w);
/* 239 */     int min = 0 - (int)size;
/* 240 */     int max = 0 + (int)size;
/* 241 */     for (int i = min; i <= max; i++)
/*     */     {
/* 243 */       for (int j = min; j <= max; j++)
/*     */       {
/* 245 */         for (int k = min; k <= max; k++)
/*     */         {
/* 247 */           if ((this.q.a(x + i, y + j, z + k) == 0) || (this.q.g(x + i, y + j, z + k).getCanBurn())) {
/* 248 */             this.q.c(x + i, y + j, z + k, BlockEndPortal.aw.blockID);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 254 */     List entities = this.q.b(this, this.E.b(size, size, size));
/* 255 */     for (nm entity : entities)
/*     */     {
/* 257 */       entity.d(8);
/* 258 */       entity.a(CombatTracker.fighter, initialDamage);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doRiftParticles()
/*     */   {
/* 264 */     for (int i = 0; i < 300; i++)
/*     */     {
/* 266 */       float x = this.ab.nextFloat() * 6.0F - 3.0F;
/* 267 */       float z = this.ab.nextFloat() * 6.0F - 3.0F;
/* 268 */       this.q.a("portal", this.u + x, this.v + 2.0D, this.w + z, -x / 3.0F, -2.0D, -z / 3.0F);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMTrap
 * JD-Core Version:    0.6.2
 */