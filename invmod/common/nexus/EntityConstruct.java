/*    */ package invmod.common.nexus;
/*    */ 
/*    */ public class EntityConstruct
/*    */ {
/*    */   private IMEntityType entityType;
/*    */   private int texture;
/*    */   private int tier;
/*    */   private int flavour;
/*    */   private int minAngle;
/*    */   private int maxAngle;
/*    */   private float scaling;
/*    */ 
/*    */   public EntityConstruct(IMEntityType mobType, int tier, int texture, int flavour, float scaling, int minAngle, int maxAngle)
/*    */   {
/* 12 */     this.entityType = mobType;
/* 13 */     this.texture = texture;
/* 14 */     this.tier = tier;
/* 15 */     this.flavour = flavour;
/* 16 */     this.scaling = scaling;
/* 17 */     this.minAngle = minAngle;
/* 18 */     this.maxAngle = maxAngle;
/*    */   }
/*    */ 
/*    */   public IMEntityType getMobType()
/*    */   {
/* 44 */     return this.entityType;
/*    */   }
/*    */ 
/*    */   public int getTexture()
/*    */   {
/* 49 */     return this.texture;
/*    */   }
/*    */ 
/*    */   public int getTier()
/*    */   {
/* 54 */     return this.tier;
/*    */   }
/*    */ 
/*    */   public int getFlavour()
/*    */   {
/* 59 */     return this.flavour;
/*    */   }
/*    */ 
/*    */   public float getScaling()
/*    */   {
/* 64 */     return this.scaling;
/*    */   }
/*    */ 
/*    */   public int getMinAngle()
/*    */   {
/* 69 */     return this.minAngle;
/*    */   }
/*    */ 
/*    */   public int getMaxAngle()
/*    */   {
/* 74 */     return this.maxAngle;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.EntityConstruct
 * JD-Core Version:    0.6.2
 */