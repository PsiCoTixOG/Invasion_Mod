/*     */ package invmod.common.item;
/*     */ 
/*     */ import invmod.common.entity.EntityIMBird;
/*     */ import invmod.common.entity.EntityIMCreeper;
/*     */ import invmod.common.entity.EntityIMGiantBird;
/*     */ import invmod.common.entity.EntityIMPigEngy;
/*     */ import invmod.common.entity.EntityIMSkeleton;
/*     */ import invmod.common.entity.EntityIMSpider;
/*     */ import invmod.common.entity.EntityIMThrower;
/*     */ import invmod.common.entity.EntityIMZombie;
/*     */ import invmod.common.mod_Invasion;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.TileEntityNexus;

/*     */ import java.util.ArrayList;

/*     */ import net.minecraft.entity.EntityLeashKnot;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.passive.EntityWaterMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
/*     */ 
/*     */ public class ItemDebugWand extends ItemIM
/*     */ {
/*     */   private TileEntityNexus nexus;
/*     */ 
/*     */   public ItemDebugWand(int itemId)
/*     */   {
/*  31 */     super(itemId);
/*  32 */     this.maxStackSize = 1;
/*  33 */     this.setHasSubtypes(false);
/*     */   }
/*     */ 
/*     */   public boolean onItemUseFirst(EnumToolMaterial itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
/*     */   {
/*  39 */     if (world.isRemote) 
			  {
/*  40 */       return false;
/*     */     }
/*  42 */     int id = world.getBlockId(x, y, z);
/*  43 */     if (id == mod_Invasion.blockNexus.blockID)
/*     */     {
/*  45 */       this.nexus = ((TileEntityNexus)world.getBlockTileEntity(x, y, z));
/*  46 */       return true;
/*     */     }
/*     */ 
/*  50 */     mod_Invasion.playGlobalSFX("invmod:rumble");
/*     */ 
/*  53 */     EntityIMBird bird = new EntityIMGiantBird(world);
/*  54 */     bird.setPosition(x, y + 1, z);
/*     */ 
/*  57 */     EntityWitch zombie2 = new EntityWitch(world);
/*  58 */     zombie2.setPosition(x, y + 1, z);
/*     */ 
/*  68 */     EntityWaterMob wolf = new EntityWaterMob(world);
/*  69 */     wolf.setPosition(x, y + 1, z);
/*  70 */     world.d(wolf);
/*     */ 
/*  73 */     nm entity1 = new EntityIMPigEngy(world);
/*  74 */     entity1.b(x, y + 1, z);
/*     */ 
/*  77 */     EntityIMZombie zombie = new EntityIMZombie(world, this.nexus);
/*  78 */     zombie.setTexture(0);
/*  79 */     zombie.setFlavour(0);
/*  80 */     zombie.setTier(1);
/*     */ 
/*  82 */     zombie.setPosition(x, y + 1, z);
/*     */ 
/*  85 */     if (this.nexus != null)
/*     */     {
/*  87 */       nm entity = new EntityIMPigEngy(world, this.nexus);
/*  88 */       entity.b(x, y + 1, z);
/*     */ 
/*  91 */       zombie = new EntityIMZombie(world, this.nexus);
/*  92 */       zombie.setTexture(0);
/*  93 */       zombie.setFlavour(0);
/*  94 */       zombie.setTier(2);
/*  95 */       zombie.setPosition(x, y + 1, z);
/*     */ 
/*  98 */       nm thrower = new EntityIMThrower(world, this.nexus);
/*  99 */       thrower.b(x, y + 1, z);
/*     */ 
/* 102 */       EntityIMCreeper creep = new EntityIMCreeper(world, this.nexus);
/* 103 */       creep.setPosition(x, y + 1, z);
/*     */ 
/* 106 */       EntityIMSpider spider = new EntityIMSpider(world, this.nexus);
/*     */ 
/* 109 */       spider.setTexture(0);
/* 110 */       spider.setFlavour(0);
/* 111 */       spider.setTier(2);
/*     */ 
/* 113 */       spider.setPosition(x, y + 1, z);
/*     */ 
/* 116 */       EntityIMSkeleton skeleton = new EntityIMSkeleton(world, this.nexus);
/* 117 */       skeleton.setPosition(x, y + 1, z);
/*     */     }
/*     */ 
/* 120 */     EntityIMSpider entity = new EntityIMSpider(world, this.nexus);
/*     */ 
/* 123 */     entity.setTexture(0);
/* 124 */     entity.setFlavour(1);
/* 125 */     entity.setTier(2);
/*     */ 
/* 127 */     entity.setPosition(x, y + 1, z);
/*     */ 
/* 130 */     EntityIMCreeper creep = new EntityIMCreeper(world);
/* 131 */     creep.setPosition(150.5D, 64.0D, 271.5D);
/*     */ 
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean onItemUse(ItemStack itemstack, EntityPlayer player, EntityLeashKnot targetEntity)
/*     */   {
/* 156 */     if ((targetEntity instanceof EntityWaterMob))
/*     */     {
/* 159 */       EntityWaterMob wolf = (EntityWaterMob)targetEntity;
/*     */ 
/* 161 */       if (player != null) 
				{
/* 162 */         wolf.b(player.bu);
/*     */       }
/* 164 */       return true;
/*     */     }
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   public void addCreativeItems(ArrayList itemList)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.item.ItemDebugWand
 * JD-Core Version:    0.6.2
 */