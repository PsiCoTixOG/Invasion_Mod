/*    */ package invmod.common.util;
/*    */ 
/*    */ import invmod.common.mod_Invasion;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class RandomSelectionPool<T>
/*    */   implements ISelect<T>
/*    */ {
/*    */   private List<Pair<ISelect<T>, Float>> pool;
/*    */   private float totalWeight;
/*    */   private Random rand;
/*    */ 
/*    */   public RandomSelectionPool()
/*    */   {
/* 20 */     this.pool = new ArrayList();
/* 21 */     this.totalWeight = 0.0F;
/* 22 */     this.rand = new Random();
/*    */   }
/*    */ 
/*    */   public void addEntry(T entry, float weight)
/*    */   {
/* 31 */     SingleSelection selection = new SingleSelection(entry);
/* 32 */     addEntry(selection, weight);
/*    */   }
/*    */ 
/*    */   public void addEntry(ISelect<T> entry, float weight)
/*    */   {
/* 40 */     this.pool.add(new Pair(entry, Float.valueOf(weight)));
/* 41 */     this.totalWeight += weight;
/*    */   }
/*    */ 
/*    */   public T selectNext()
/*    */   {
/* 47 */     float r = this.rand.nextFloat() * this.totalWeight;
/* 48 */     for (Pair entry : this.pool)
/*    */     {
/* 50 */       if (r < ((Float)entry.getVal2()).floatValue())
/*    */       {
/* 52 */         return ((ISelect)entry.getVal1()).selectNext();
/*    */       }
/*    */ 
/* 56 */       r -= ((Float)entry.getVal2()).floatValue();
/*    */     }
/*    */ 
/* 61 */     if (this.pool.size() > 0)
/*    */     {
/* 63 */       mod_Invasion.log("RandomSelectionPool invalid setup or rounding error. Failing safe.");
/* 64 */       return ((ISelect)((Pair)this.pool.get(0)).getVal1()).selectNext();
/*    */     }
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */   public RandomSelectionPool<T> clone()
/*    */   {
/* 72 */     RandomSelectionPool clone = new RandomSelectionPool();
/* 73 */     for (Pair entry : this.pool)
/*    */     {
/* 75 */       clone.addEntry((ISelect)entry.getVal1(), ((Float)entry.getVal2()).floatValue());
/*    */     }
/*    */ 
/* 78 */     return clone;
/*    */   }
/*    */ 
/*    */   public void reset()
/*    */   {
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 87 */     String s = "RandomSelectionPool@" + Integer.toHexString(hashCode()) + "#Size=" + this.pool.size();
/* 88 */     for (int i = 0; i < this.pool.size(); i++)
/*    */     {
/* 90 */       s = s + "\n\tEntry " + i + "   Weight: " + ((Pair)this.pool.get(i)).getVal2();
/* 91 */       s = s + "\n\t" + ((ISelect)((Pair)this.pool.get(i)).getVal1()).toString();
/*    */     }
/* 93 */     return s;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.RandomSelectionPool
 * JD-Core Version:    0.6.2
 */