/*     */ package invmod.common.entity;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.player.CallableItemName;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class EntityIMEgg extends EntityIMLiving
/*     */ {
/*   9 */   private static int META_HATCHED = 30;
/*     */   private int hatchTime;
/*     */   private int ticks;
/*     */   private boolean hatched;
/*     */   private nm parent;
/*     */   private nm[] contents;
/*     */ 
/*     */   public EntityIMEgg(ColorizerGrass world)
/*     */   {
/*  19 */     super(world);
/*  20 */     u().a(META_HATCHED, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   public EntityIMEgg(nm parent, nm[] contents, int hatchTime, int hp)
/*     */   {
/*  25 */     super(parent.q);
/*  26 */     this.parent = parent;
/*  27 */     this.contents = contents;
/*  28 */     this.hatchTime = hatchTime;
/*  29 */     setMaxHealthAndHealth(hp);
/*  30 */     this.hatched = false;
/*  31 */     this.ticks = 0;
/*  32 */     setBaseMoveSpeedStat(0.01F);
/*     */ 
/*  34 */     u().a(META_HATCHED, Byte.valueOf((byte)0));
/*     */ 
/*  36 */     setName("Spider Egg");
/*  37 */     setGender(0);
/*  38 */     setPosition(parent.u, parent.v, parent.w);
/*  39 */     setSize(0.5F, 0.8F);
/*     */   }
/*     */ 
/*     */   public String getSpecies()
/*     */   {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */   public int getTier()
/*     */   {
/*  54 */     return 0;
/*     */   }
/*     */ 
/*     */   public boolean isHostile()
/*     */   {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNeutral()
/*     */   {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isThreatTo(nm entity)
/*     */   {
/*  75 */     if ((entity instanceof CallableItemName)) {
/*  76 */       return true;
/*     */     }
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */   public nm getAttackingTarget()
/*     */   {
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDead()
/*     */   {
/* 108 */     super.setDead();
/* 109 */     if (!this.q.I)
/*     */     {
/* 111 */       this.ticks += 1;
/* 112 */       if (this.hatched)
/*     */       {
/* 114 */         if (this.ticks > this.hatchTime + 40)
/* 115 */           preparePlayerToSpawn();
/*     */       }
/* 117 */       else if (this.ticks > this.hatchTime)
/*     */       {
/* 119 */         hatch();
/*     */       }
/*     */     }
/* 122 */     else if ((!this.hatched) && (u().a(META_HATCHED) == 1))
/*     */     {
/* 124 */       this.q.a(this, "invmod:egghatch", 1.0F, 1.0F);
/* 125 */       this.hatched = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void hatch()
/*     */   {
/* 131 */     this.q.a(this, "invmod:egghatch", 1.0F, 1.0F);
/* 132 */     this.hatched = true;
/* 133 */     if (!this.q.I)
/*     */     {
/* 135 */       u().b(META_HATCHED, Byte.valueOf((byte)1));
/* 136 */       if (this.contents != null)
/*     */       {
/* 138 */         for (nm entity : this.contents)
/*     */         {
/* 140 */           entity.b(this.posX, this.posY, this.posZ);
/* 141 */           this.q.d(entity);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMEgg
 * JD-Core Version:    0.6.2
 */