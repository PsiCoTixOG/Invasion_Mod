/*    */ package invmod.common.nexus;
/*    */ 
/*    */ public class InvMobConstruct
/*    */ {
/*    */   private int texture;
/*    */   private int tier;
/*    */   private int flavour;
/*    */   private float scaling;
/*    */ 
/*    */   public InvMobConstruct(int texture, int tier, int flavour, float scaling)
/*    */   {
/*  9 */     this.texture = texture;
/* 10 */     this.tier = tier;
/* 11 */     this.flavour = flavour;
/* 12 */     this.scaling = scaling;
/*    */   }
/*    */ 
/*    */   public int getTexture()
/*    */   {
/* 38 */     return this.texture;
/*    */   }
/*    */ 
/*    */   public int getTier()
/*    */   {
/* 43 */     return this.tier;
/*    */   }
/*    */ 
/*    */   public int getFlavour()
/*    */   {
/* 48 */     return this.flavour;
/*    */   }
/*    */ 
/*    */   public float getScaling()
/*    */   {
/* 53 */     return this.scaling;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.nexus.InvMobConstruct
 * JD-Core Version:    0.6.2
 */