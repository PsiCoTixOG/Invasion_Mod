/*    */ package invmod.client.render;
/*    */ 
/*    */ import invmod.client.render.animation.Animation;
/*    */ import invmod.client.render.animation.AnimationAction;
/*    */ import invmod.client.render.animation.AnimationPhaseInfo;
/*    */ import invmod.client.render.animation.BonesWings;
/*    */ import invmod.client.render.animation.Transition;
/*    */ import invmod.common.mod_Invasion;
/*    */ import java.util.ArrayList;
/*    */ import java.util.EnumMap;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AnimationRegistry
/*    */ {
/* 63 */   private static final AnimationRegistry instance = new AnimationRegistry();
/*    */   private Map<String, Animation> animationMap;
/*    */   private Animation emptyAnim;
/*    */ 
/*    */   private AnimationRegistry()
/*    */   {
/* 26 */     this.animationMap = new HashMap(4);
/* 27 */     EnumMap allKeyFramesWings = new EnumMap(BonesWings.class);
/* 28 */     List animationPhases = new ArrayList(1);
/* 29 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.STAND, 0.0F, 1.0F, new Transition(AnimationAction.STAND, 1.0F, 0.0F)));
/* 30 */     this.emptyAnim = new Animation(BonesWings.class, 1.0F, 1.0F, allKeyFramesWings, animationPhases);
/*    */   }
/*    */ 
/*    */   public void registerAnimation(String name, Animation animation)
/*    */   {
/* 35 */     if (!this.animationMap.containsKey(name))
/*    */     {
/* 37 */       this.animationMap.put(name, animation);
/* 38 */       return;
/*    */     }
/* 40 */     mod_Invasion.log("Register animation: Name \"" + name + "\" already assigned");
/*    */   }
/*    */ 
/*    */   public Animation getAnimation(String name)
/*    */   {
/* 45 */     if (this.animationMap.containsKey(name))
/*    */     {
/* 47 */       return (Animation)this.animationMap.get(name);
/*    */     }
/*    */ 
/* 51 */     mod_Invasion.log("Tried to use animation \"" + name + "\" but it doesn't exist");
/* 52 */     return this.emptyAnim;
/*    */   }
/*    */ 
/*    */   public static AnimationRegistry instance()
/*    */   {
/* 58 */     return instance;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.AnimationRegistry
 * JD-Core Version:    0.6.2
 */