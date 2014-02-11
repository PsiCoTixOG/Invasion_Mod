/*    */ package invmod.common.entity;
/*    */ 
/*    */ import invmod.common.mod_Invasion;

/*    */ import java.util.Random;

/*    */ import net.minecraft.block.BlockPistonExtension;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.nbt.NBTTagByte;
/*    */ import net.minecraft.entity.player.EntityPlayer;
		 import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.LongHashMapEntry;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.WorldType;
/*    */ 
/*    */ public class EntityIMSpawnProxy extends EntityLivingBase
/*    */ {
/*    */   public EntityIMSpawnProxy(World world)
/*    */   {
/* 21 */     super(world);
/*    */   }
/*    */ 
/*    */   public void setDead()
/*    */   {
/* 27 */     if (this.q != null)
/*    */     {
/* 30 */       World[] entities = mod_Invasion.getNightMobSpawns1(this.q);
/* 31 */       for (World entity : entities)
/*    */       {
/* 33 */         entity.b(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 34 */         this.q.d(entity);
/*    */       }
/*    */     }
/* 37 */     preparePlayerToSpawn();
/*    */   }
/*    */ 
/*    */   public void b(NBTTagByte nbttagcompound)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void a(NBTTagByte nbttagcompound)
/*    */   {
/*    */   }
/*    */ 
/*    */   public float getBlockPathWeight(int i, int j, int k)
/*    */   {
/* 52 */     return 0.5F - this.q.q(i, j, k);
/*    */   }
/*    */ 
/*    */   protected boolean darkEnoughToSpawn()
/*    */   {
/* 57 */     int i = LongHashMapEntry.c(this.posX);
/* 58 */     int j = LongHashMapEntry.c(this.E.b);
/* 59 */     int k = LongHashMapEntry.c(this.posZ);
/* 60 */     if (this.q.b(WorldType.worldTypes, i, j, k) > this.rand.nextInt(32))
/*    */     {
/* 62 */       return false;
/*    */     }
/* 64 */     int l = this.q.n(i, j, k);
/* 65 */     if (this.q.P())
/*    */     {
/* 67 */       int i1 = this.q.j;
/* 68 */       this.q.j = 10;
/* 69 */       l = this.q.n(i, j, k);
/* 70 */       this.q.j = i1;
/*    */     }
/* 72 */     return l <= this.rand.nextInt(8);
/*    */   }
/*    */ 
/*    */   public boolean bs()
/*    */   {
/* 78 */     int i = LongHashMapEntry.c(this.posX);
/* 79 */     int j = LongHashMapEntry.c(this.E.b);
/* 80 */     int k = LongHashMapEntry.c(this.posZ);
/* 81 */     return (darkEnoughToSpawn()) && (super.bs()) && (getBlockPathWeight(i, j, k) >= 0.0F);
/*    */   }
/*    */
		  @Override
		  public ItemStack getHeldItem() 
		  {
			  // TODO Auto-generated method stub
			  return null;
		  }
		  
		  @Override
		  public ItemStack getCurrentItemOrArmor(int i) 
		  {
			  // TODO Auto-generated method stub
			  return null;
		  }
		  
		  @Override
		  public void setCurrentItemOrArmor(int i, ItemStack itemstack) 
		  {
			  // TODO Auto-generated method stub
		  }

		  @Override
		  public ItemStack[] getLastActiveItems() 
		  {
			  // TODO Auto-generated method stub
			  return null;
		  } 
	}

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMSpawnProxy
 * JD-Core Version:    0.6.2
 */