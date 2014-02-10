/*     */ package invmod.common.entity;
/*     */ 
/*     */ import invmod.common.nexus.INexusAccess;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPlay;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMSkeleton extends EntityIMMob
/*     */ {
/* 136 */   private static final EnumToolMaterial defaultHeldItem = new EnumToolMaterial(ItemHoe.m, 1);
/*     */ 
/*     */   public EntityIMSkeleton(ColorizerGrass world)
/*     */   {
/*  22 */     this(world, null);
/*     */   }
/*     */ 
/*     */   public EntityIMSkeleton(ColorizerGrass world, INexusAccess nexus)
/*     */   {
/*  27 */     super(world, nexus);
/*  28 */     setBurnsInDay(true);
/*  29 */     setMaxHealthAndHealth(12.0F);
/*  30 */     setBurnsInDay(true);
/*  31 */     setName("Skeleton");
/*  32 */     setGender(0);
/*  33 */     setBaseMoveSpeedStat(0.21F);
/*     */ 
/*  35 */     this.c.a(0, new EntityAIKillWithArrow(this, CallableItemName.class, 65, 16.0F));
/*  36 */     this.c.a(1, new EntityAIKillWithArrow(this, EntityLivingBase.class, 65, 16.0F));
/*  37 */     this.c.a(2, new EntityAIAttackNexus(this));
/*  38 */     this.c.a(3, new EntityAIGoToNexus(this));
/*  39 */     this.c.a(4, new EntityAIWanderIM(this));
/*  40 */     this.c.a(5, new EntityAILeapAtTarget(this, CallableItemName.class, 8.0F));
/*  41 */     this.c.a(5, new EntityAIPlay(this));
/*  42 */     this.d.a(1, new EntityAIDefendVillage(this, false));
/*  43 */     this.d.a(2, new EntityAISimpleTarget(this, CallableItemName.class, 16.0F, true));
/*     */ 
/*  46 */     this.c.a(0, new EntityAIRallyBehindEntity(this, EntityIMCreeper.class, 4.0F));
/*  47 */     this.c.a(8, new EntityAILeapAtTarget(this, EntityIMCreeper.class, 12.0F));
/*  48 */     this.d.a(0, new EntityAISimpleTarget(this, CallableItemName.class, 6.0F, true));
/*  49 */     this.d.a(1, new EntityAILeaderTarget(this, EntityIMCreeper.class, 10.0F, true));
/*     */   }
/*     */ 
/*     */   protected String getLivingSound()
/*     */   {
/*  55 */     return "mob.skeleton";
/*     */   }
/*     */ 
/*     */   protected String aN()
/*     */   {
/*  61 */     return "mob.skeletonhurt";
/*     */   }
/*     */ 
/*     */   protected String getHurtSound()
/*     */   {
/*  67 */     return "mob.skeletonhurt";
/*     */   }
/*     */ 
/*     */   public void b(NBTTagByte nbttagcompound)
/*     */   {
/*  73 */     super.b(nbttagcompound);
/*     */   }
/*     */ 
/*     */   public void a(NBTTagByte nbttagcompound)
/*     */   {
/*  79 */     super.a(nbttagcompound);
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/*  88 */     return "Skeleton";
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/*  94 */     return 2;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 102 */     return "EntityIMSkeleton#";
/*     */   }
/*     */ 
/*     */   protected int getDropItemId()
/*     */   {
/* 108 */     return ItemHoe.n.itemID;
/*     */   }
/*     */ 
/*     */   protected void dropFewItems(boolean flag, int bonus)
/*     */   {
/* 114 */     super.dropFewItems(flag, bonus);
/* 115 */     int i = this.rand.nextInt(3);
/* 116 */     for (int j = 0; j < i; j++)
/*     */     {
/* 118 */       b(ItemHoe.n.itemID, 1);
/*     */     }
/*     */ 
/* 121 */     i = this.rand.nextInt(3);
/* 122 */     for (int k = 0; k < i; k++)
/*     */     {
/* 124 */       b(ItemHoe.aZ.itemID, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public EnumToolMaterial aY()
/*     */   {
/* 131 */     return defaultHeldItem;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMSkeleton
 * JD-Core Version:    0.6.2
 */