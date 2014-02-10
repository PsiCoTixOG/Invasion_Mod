/*     */ package invmod.common.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class FiniteSelectionPool<T>
/*     */   implements ISelect<T>
/*     */ {
/*     */   private List<Pair<ISelect<T>, Integer>> currentPool;
/*     */   private List<Integer> originalPool;
/*     */   private int totalAmount;
/*     */   private int originalAmount;
/*     */   private Random rand;
/*     */ 
/*     */   public FiniteSelectionPool()
/*     */   {
/*  16 */     this.currentPool = new ArrayList();
/*  17 */     this.originalPool = new ArrayList();
/*  18 */     this.totalAmount = 0;
/*  19 */     this.rand = new Random();
/*     */   }
/*     */ 
/*     */   public void addEntry(T entry, int amount)
/*     */   {
/*  27 */     SingleSelection selection = new SingleSelection(entry);
/*  28 */     addEntry(selection, amount);
/*     */   }
/*     */ 
/*     */   public void addEntry(ISelect<T> entry, int amount)
/*     */   {
/*  37 */     this.currentPool.add(new Pair(entry, Integer.valueOf(amount)));
/*  38 */     this.originalPool.add(Integer.valueOf(amount));
/*  39 */     this.originalAmount = (this.totalAmount += amount);
/*     */   }
/*     */ 
/*     */   public T selectNext()
/*     */   {
/*  45 */     if (this.totalAmount < 1) {
/*  46 */       regeneratePool();
/*     */     }
/*  48 */     float r = this.rand.nextInt(this.totalAmount);
/*  49 */     for (Pair entry : this.currentPool)
/*     */     {
/*  51 */       int amountLeft = ((Integer)entry.getVal2()).intValue();
/*  52 */       if (r < amountLeft)
/*     */       {
/*  54 */         entry.setVal2(Integer.valueOf(amountLeft - 1));
/*  55 */         this.totalAmount -= 1;
/*  56 */         return ((ISelect)entry.getVal1()).selectNext();
/*     */       }
/*     */ 
/*  60 */       r -= amountLeft;
/*     */     }
/*     */ 
/*  64 */     return null;
/*     */   }
/*     */ 
/*     */   public FiniteSelectionPool<T> clone()
/*     */   {
/*  70 */     FiniteSelectionPool clone = new FiniteSelectionPool();
/*  71 */     for (int i = 0; i < this.currentPool.size(); i++)
/*     */     {
/*  73 */       clone.addEntry((ISelect)((Pair)this.currentPool.get(i)).getVal1(), ((Integer)this.originalPool.get(i)).intValue());
/*     */     }
/*     */ 
/*  76 */     return clone;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  82 */     regeneratePool();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  88 */     String s = "FiniteSelectionPool@" + Integer.toHexString(hashCode()) + "#Size=" + this.currentPool.size();
/*  89 */     for (int i = 0; i < this.currentPool.size(); i++)
/*     */     {
/*  91 */       s = s + "\n\tEntry " + i + "   Amount: " + this.originalPool.get(i);
/*  92 */       s = s + "\n\t" + ((ISelect)((Pair)this.currentPool.get(i)).getVal1()).toString();
/*     */     }
/*  94 */     return s;
/*     */   }
/*     */ 
/*     */   private void regeneratePool()
/*     */   {
/* 102 */     this.totalAmount = this.originalAmount;
/* 103 */     for (int i = 0; i < this.currentPool.size(); i++)
/*     */     {
/* 105 */       ((Pair)this.currentPool.get(i)).setVal2(this.originalPool.get(i));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.FiniteSelectionPool
 * JD-Core Version:    0.6.2
 */