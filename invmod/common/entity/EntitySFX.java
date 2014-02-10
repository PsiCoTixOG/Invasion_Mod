/*    */ package invmod.common.entity;
/*    */ 
/*    */ import net.minecraft.nbt.NBTTagByte;
/*    */ import net.minecraft.src.nm;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class EntitySFX extends nm
/*    */ {
/*    */   private int lifespan;
/*    */ 
/*    */   public EntitySFX(ColorizerGrass world)
/*    */   {
/* 13 */     super(world);
/* 14 */     this.lifespan = 200;
/*    */   }
/*    */ 
/*    */   public EntitySFX(ColorizerGrass world, double x, double y, double z)
/*    */   {
/* 19 */     super(world);
/* 20 */     this.lifespan = 200;
/* 21 */     this.u = x;
/* 22 */     this.v = y;
/* 23 */     this.w = z;
/*    */   }
/*    */ 
/*    */   public void l_()
/*    */   {
/* 29 */     super.l_();
/* 30 */     if (this.lifespan-- <= 0)
/*    */     {
/* 32 */       w();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void a(byte byte0)
/*    */   {
/* 39 */     if (byte0 != 0)
/*    */     {
/* 43 */       if (byte0 != 1)
/*    */       {
/* 46 */         if (byte0 != 2);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void a()
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void a(NBTTagByte nbttagcompound)
/*    */   {
/*    */   }
/*    */ 
/*    */   protected void b(NBTTagByte nbttagcompound)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntitySFX
 * JD-Core Version:    0.6.2
 */