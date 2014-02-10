/*    */ package invmod.common.item;
/*    */ 
/*    */ import invmod.common.entity.EntityIMWolf;
/*    */ import invmod.common.mod_Invasion;
/*    */ import invmod.common.nexus.BlockNexus;
/*    */ import invmod.common.nexus.TileEntityNexus;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLeashKnot;
/*    */ import net.minecraft.entity.passive.EntityWaterMob;
/*    */ import net.minecraft.entity.player.CallableItemName;
/*    */ import net.minecraft.item.EnumToolMaterial;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ import net.minecraft.world.ColorizerGrass;
/*    */ 
/*    */ public class ItemStrangeBone extends ItemIM
/*    */ {
/*    */   public ItemStrangeBone(int i)
/*    */   {
/* 16 */     super(i);
/* 17 */     this.maxStackSize = 1;
/*    */   }
/*    */ 
/*    */   public int getDamage(EnumToolMaterial stack)
/*    */   {
/* 23 */     return 0;
/*    */   }
/*    */ 
/*    */   public boolean a(EnumToolMaterial itemstack, CallableItemName player, EntityLeashKnot targetEntity)
/*    */   {
/* 29 */     if ((!targetEntity.q.I) && ((targetEntity instanceof EntityWaterMob)) && (!(targetEntity instanceof EntityIMWolf)))
/*    */     {
/* 31 */       EntityWaterMob wolf = (EntityWaterMob)targetEntity;
/* 32 */       if (wolf.bT())
/*    */       {
/* 34 */         TileEntityNexus nexus = null;
/* 35 */         int x = LongHashMapEntry.c(wolf.posX);
/* 36 */         int y = LongHashMapEntry.c(wolf.posY);
/* 37 */         int z = LongHashMapEntry.c(wolf.posZ);
/* 38 */         for (int i = -7; i < 8; i++)
/*    */         {
/* 40 */           for (int j = -4; j < 5; j++)
/*    */           {
/* 42 */             for (int k = -7; k < 8; k++)
/*    */             {
/* 44 */               if (wolf.q.a(x + i, y + j, z + k) == mod_Invasion.blockNexus.cF)
/*    */               {
/* 46 */                 nexus = (TileEntityNexus)wolf.q.r(x + i, y + j, z + k);
/* 47 */                 break;
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */ 
/* 53 */         if (nexus != null)
/*    */         {
/* 56 */           EntityIMWolf newWolf = new EntityIMWolf(wolf, nexus);
/* 57 */           wolf.q.d(newWolf);
/* 58 */           wolf.preparePlayerToSpawn();
/* 59 */           itemstack.STONE -= 1;
/*    */         }
/*    */       }
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean a(EnumToolMaterial itemstack, EntityLeashKnot entityLiving, EntityLeashKnot entityLiving1)
/*    */   {
/* 70 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemStrangeBone
 * JD-Core Version:    0.6.2
 */