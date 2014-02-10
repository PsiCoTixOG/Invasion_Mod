/*    */ package invmod.common.nexus;
/*    */ 
/*    */ import invmod.common.util.RandomSelectionPool;
/*    */ 
/*    */ public class EntityPattern
/*    */   implements IEntityIMPattern
/*    */ {
/*    */   private IMEntityType entityType;
/*    */   private RandomSelectionPool<Integer> tierPool;
/*    */   private RandomSelectionPool<Integer> texturePool;
/*    */   private RandomSelectionPool<Integer> flavourPool;
/*    */   private static final int DEFAULT_TIER = 1;
/*    */   private static final int DEFAULT_FLAVOUR = 0;
/*    */   private static final int OPEN_TEXTURE = 0;
/*    */   private static final int OPEN_SCALING = 0;
/*    */ 
/*    */   public EntityPattern(IMEntityType entityType)
/*    */   {
/* 14 */     this.entityType = entityType;
/* 15 */     this.tierPool = new RandomSelectionPool();
/* 16 */     this.texturePool = new RandomSelectionPool();
/* 17 */     this.flavourPool = new RandomSelectionPool();
/*    */   }
/*    */ 
/*    */   public EntityConstruct generateEntityConstruct()
/*    */   {
/* 26 */     return generateEntityConstruct(-180, 180);
/*    */   }
/*    */ 
/*    */   public EntityConstruct generateEntityConstruct(int minAngle, int maxAngle)
/*    */   {
/* 36 */     Integer tier = (Integer)this.tierPool.selectNext();
/* 37 */     if (tier == null) {
/* 38 */       tier = Integer.valueOf(1);
/*    */     }
/* 40 */     Integer texture = (Integer)this.texturePool.selectNext();
/* 41 */     if (texture == null) {
/* 42 */       texture = Integer.valueOf(0);
/*    */     }
/* 44 */     Integer flavour = (Integer)this.flavourPool.selectNext();
/* 45 */     if (flavour == null) {
/* 46 */       flavour = Integer.valueOf(0);
/*    */     }
/* 48 */     return new EntityConstruct(this.entityType, tier.intValue(), texture.intValue(), flavour.intValue(), 0.0F, minAngle, maxAngle);
/*    */   }
/*    */ 
/*    */   public void addTier(int tier, float weight)
/*    */   {
/* 53 */     this.tierPool.addEntry(Integer.valueOf(tier), weight);
/*    */   }
/*    */ 
/*    */   public void addTexture(int texture, float weight)
/*    */   {
/* 58 */     this.texturePool.addEntry(Integer.valueOf(texture), weight);
/*    */   }
/*    */ 
/*    */   public void addFlavour(int flavour, float weight)
/*    */   {
/* 63 */     this.flavourPool.addEntry(Integer.valueOf(flavour), weight);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 69 */     return "EntityIMPattern@" + Integer.toHexString(hashCode()) + "#" + this.entityType;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.EntityPattern
 * JD-Core Version:    0.6.2
 */