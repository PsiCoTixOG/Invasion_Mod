/*     */ package invmod.client.render.animation;
/*     */ 
/*     */ import invmod.common.util.Triplet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class ModelAnimator<T extends Enum<T>>
/*     */ {
/*     */   private List<Triplet<bcr, Integer, List<KeyFrame>>> parts;
/*     */   private float animationPeriod;
/*     */ 
/*     */   public ModelAnimator()
/*     */   {
/*  25 */     this(1.0F);
/*     */   }
/*     */ 
/*     */   public ModelAnimator(float animationPeriod)
/*     */   {
/*  30 */     this.animationPeriod = animationPeriod;
/*  31 */     this.parts = new ArrayList(1);
/*     */   }
/*     */ 
/*     */   public ModelAnimator(Map<T, bcr> modelParts, Animation<T> animation)
/*     */   {
/*  36 */     this.animationPeriod = animation.getAnimationPeriod();
/*  37 */     this.parts = new ArrayList(((Enum[])animation.getSkeletonType().getEnumConstants()).length);
/*  38 */     for (Map.Entry entry : modelParts.entrySet())
/*     */     {
/*  40 */       List keyFrames = animation.getKeyFramesFor((Enum)entry.getKey());
/*  41 */       if (keyFrames != null)
/*     */       {
/*  45 */         this.parts.add(new Triplet(entry.getValue(), Integer.valueOf(0), keyFrames));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addPart(bcr part, List<KeyFrame> keyFrames) {
/*  51 */     if (validate(keyFrames))
/*     */     {
/*  53 */       this.parts.add(new Triplet(part, Integer.valueOf(0), keyFrames));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clearParts()
/*     */   {
/*  59 */     this.parts.clear();
/*     */   }
/*     */ 
/*     */   public void updateAnimation(float newTime)
/*     */   {
/*  64 */     for (Triplet entry : this.parts)
/*     */     {
/*  68 */       int prevIndex = ((Integer)entry.getVal2()).intValue();
/*  69 */       List keyFrames = (List)entry.getVal3();
/*  70 */       KeyFrame prevFrame = (KeyFrame)keyFrames.get(prevIndex++);
/*  71 */       KeyFrame nextFrame = null;
/*     */ 
/*  73 */       if (prevFrame.getTime() <= newTime)
/*     */       {
/*  75 */         for (; prevIndex < keyFrames.size(); prevIndex++)
/*     */         {
/*  77 */           KeyFrame keyFrame = (KeyFrame)keyFrames.get(prevIndex);
/*  78 */           if (newTime < keyFrame.getTime())
/*     */           {
/*  80 */             nextFrame = keyFrame;
/*  81 */             prevIndex--;
/*  82 */             break;
/*     */           }
/*     */ 
/*  86 */           prevFrame = keyFrame;
/*     */         }
/*     */ 
/*  89 */         if (prevIndex >= keyFrames.size())
/*     */         {
/*  91 */           prevIndex = keyFrames.size() - 1;
/*  92 */           nextFrame = (KeyFrame)keyFrames.get(0);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  97 */         for (prevIndex = 0; 
/*  98 */           prevIndex < keyFrames.size(); prevIndex++)
/*     */         {
/* 100 */           KeyFrame keyFrame = (KeyFrame)keyFrames.get(prevIndex);
/* 101 */           if (newTime < keyFrame.getTime())
/*     */           {
/* 103 */             nextFrame = keyFrame;
/* 104 */             prevIndex--;
/* 105 */             prevFrame = (KeyFrame)keyFrames.get(prevIndex);
/* 106 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 110 */       entry.setVal2(Integer.valueOf(prevIndex));
/* 111 */       interpolate(prevFrame, nextFrame, newTime, (net.minecraft.src.bcr)entry.getVal1());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void interpolate(KeyFrame prevFrame, KeyFrame nextFrame, float time, net.minecraft.src.bcr part)
/*     */   {
/* 117 */     if (prevFrame.getInterpType() == InterpType.LINEAR)
/*     */     {
/* 120 */       float dtPrev = time - prevFrame.getTime();
/* 121 */       float dtFrame = nextFrame.getTime() - prevFrame.getTime();
/* 122 */       if (dtFrame < 0.0F)
/*     */       {
/* 124 */         dtFrame += this.animationPeriod;
/*     */       }
/*     */ 
/* 127 */       float r = dtPrev / dtFrame;
/* 128 */       part.f = (prevFrame.getRotX() + r * (nextFrame.getRotX() - prevFrame.getRotX()));
/* 129 */       part.g = (prevFrame.getRotY() + r * (nextFrame.getRotY() - prevFrame.getRotY()));
/* 130 */       part.h = (prevFrame.getRotZ() + r * (nextFrame.getRotZ() - prevFrame.getRotZ()));
/*     */ 
/* 132 */       if (prevFrame.hasPos())
/*     */       {
/* 134 */         if (nextFrame.hasPos())
/*     */         {
/* 136 */           part.c = (prevFrame.getPosX() + r * (nextFrame.getPosX() - prevFrame.getPosX()));
/* 137 */           part.d = (prevFrame.getPosY() + r * (nextFrame.getPosY() - prevFrame.getPosY()));
/* 138 */           part.e = (prevFrame.getPosZ() + r * (nextFrame.getPosZ() - prevFrame.getPosZ()));
/*     */         }
/*     */         else
/*     */         {
/* 142 */           part.c = prevFrame.getPosX();
/* 143 */           part.d = prevFrame.getPosY();
/* 144 */           part.e = prevFrame.getPosZ();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean validate(List<KeyFrame> keyFrames)
/*     */   {
/* 161 */     if (keyFrames.size() < 2) {
/* 162 */       return false;
/*     */     }
/* 164 */     if (((KeyFrame)keyFrames.get(0)).getTime() != 0.0F) {
/* 165 */       return false;
/*     */     }
/* 167 */     int prevTime = 0;
/* 168 */     for (int i = 1; i < keyFrames.size(); i++)
/*     */     {
/* 170 */       if (((KeyFrame)keyFrames.get(i)).getTime() <= prevTime) {
/* 171 */         return false;
/*     */       }
/*     */     }
/* 174 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.animation.ModelAnimator
 * JD-Core Version:    0.6.2
 */