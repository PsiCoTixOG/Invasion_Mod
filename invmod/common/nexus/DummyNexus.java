/*     */ package invmod.common.nexus;
/*     */ 
/*     */ import invmod.common.entity.AttackerAI;
/*     */ import invmod.common.entity.EntityIMLiving;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ 
/*     */ public class DummyNexus
/*     */   implements INexusAccess
/*     */ {
/*     */   private ColorizerGrass world;
/*     */ 
/*     */   public void setWorld(ColorizerGrass world)
/*     */   {
/*  17 */     this.world = world;
/*     */   }
/*     */ 
/*     */   public void attackNexus(int damage)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void registerMobDied()
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean isActivating() {
/*  29 */     return false;
/*     */   }
/*     */ 
/*     */   public int getMode()
/*     */   {
/*  35 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getActivationTimer()
/*     */   {
/*  41 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getSpawnRadius()
/*     */   {
/*  47 */     return 45;
/*     */   }
/*     */ 
/*     */   public int getNexusKills()
/*     */   {
/*  53 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getGeneration()
/*     */   {
/*  59 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getNexusLevel()
/*     */   {
/*  65 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getCurrentWave()
/*     */   {
/*  71 */     return 1;
/*     */   }
/*     */ 
/*     */   public int getXCoord()
/*     */   {
/*  77 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getYCoord()
/*     */   {
/*  83 */     return 0;
/*     */   }
/*     */ 
/*     */   public int getZCoord()
/*     */   {
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   public ColorizerGrass getWorld()
/*     */   {
/*  95 */     return this.world;
/*     */   }
/*     */ 
/*     */   public List<EntityIMLiving> getMobList()
/*     */   {
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   public void askForRespawn(EntityIMLiving entity)
/*     */   {
/*     */   }
/*     */ 
/*     */   public AttackerAI getAttackerAI()
/*     */   {
/* 110 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.DummyNexus
 * JD-Core Version:    0.6.2
 */