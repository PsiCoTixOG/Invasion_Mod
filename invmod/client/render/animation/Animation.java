/*    */ package invmod.client.render.animation;
/*    */ 
/*    */ import java.util.Collections;
/*    */ import java.util.EnumMap;
/*    */ import java.util.List;
/*    */ 
/*    */ public class Animation<T extends Enum<T>>
/*    */ {
/*    */   private float animationPeriod;
/*    */   private float baseSpeed;
/*    */   private Class<T> skeletonType;
/*    */   private EnumMap<T, List<KeyFrame>> allKeyFrames;
/*    */   private List<AnimationPhaseInfo> animationPhases;
/*    */ 
/*    */   public Animation(Class<T> skeletonType, float animationPeriod, float baseTime, EnumMap<T, List<KeyFrame>> allKeyFrames, List<AnimationPhaseInfo> animationPhases)
/*    */   {
/* 19 */     this.animationPeriod = animationPeriod;
/* 20 */     this.baseSpeed = baseTime;
/* 21 */     this.skeletonType = skeletonType;
/* 22 */     this.allKeyFrames = allKeyFrames;
/* 23 */     this.animationPhases = animationPhases;
/*    */   }
/*    */ 
/*    */   public float getAnimationPeriod()
/*    */   {
/* 28 */     return this.animationPeriod;
/*    */   }
/*    */ 
/*    */   public float getBaseSpeed()
/*    */   {
/* 33 */     return this.baseSpeed;
/*    */   }
/*    */ 
/*    */   public List<AnimationPhaseInfo> getAnimationPhases()
/*    */   {
/* 38 */     return Collections.unmodifiableList(this.animationPhases);
/*    */   }
/*    */ 
/*    */   public Class<T> getSkeletonType()
/*    */   {
/* 43 */     return this.skeletonType;
/*    */   }
/*    */ 
/*    */   public List<KeyFrame> getKeyFramesFor(T skeletonPart)
/*    */   {
/* 48 */     if (this.allKeyFrames.containsKey(skeletonPart))
/*    */     {
/* 50 */       return Collections.unmodifiableList((List)this.allKeyFrames.get(skeletonPart));
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.animation.Animation
 * JD-Core Version:    0.6.2
 */