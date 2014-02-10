/*    */ package invmod.client.render.animation;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AnimationPhaseInfo
/*    */ {
/*    */   private AnimationAction action;
/*    */   private float timeBegin;
/*    */   private float timeEnd;
/*    */   private Map<AnimationAction, Transition> transitions;
/*    */   private Transition defaultTransition;
/*    */ 
/*    */   public AnimationPhaseInfo(AnimationAction action, float timeBegin, float timeEnd, Transition defaultTransition)
/*    */   {
/* 16 */     this(action, timeBegin, timeEnd, defaultTransition, new HashMap(1));
/* 17 */     this.transitions.put(defaultTransition.getNewAction(), defaultTransition);
/*    */   }
/*    */ 
/*    */   public AnimationPhaseInfo(AnimationAction action, float timeBegin, float timeEnd, Transition defaultTransition, Map<AnimationAction, Transition> transitions)
/*    */   {
/* 22 */     this.action = action;
/* 23 */     this.timeBegin = timeBegin;
/* 24 */     this.timeEnd = timeEnd;
/* 25 */     this.defaultTransition = defaultTransition;
/* 26 */     this.transitions = transitions;
/*    */   }
/*    */ 
/*    */   public AnimationAction getAction()
/*    */   {
/* 31 */     return this.action;
/*    */   }
/*    */ 
/*    */   public float getTimeBegin()
/*    */   {
/* 36 */     return this.timeBegin;
/*    */   }
/*    */ 
/*    */   public float getTimeEnd()
/*    */   {
/* 41 */     return this.timeEnd;
/*    */   }
/*    */ 
/*    */   public boolean hasTransition(AnimationAction newAction)
/*    */   {
/* 46 */     return this.transitions.containsKey(newAction);
/*    */   }
/*    */ 
/*    */   public Transition getTransition(AnimationAction newAction)
/*    */   {
/* 51 */     return (Transition)this.transitions.get(newAction);
/*    */   }
/*    */ 
/*    */   public Transition getDefaultTransition()
/*    */   {
/* 56 */     return this.defaultTransition;
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.animation.AnimationPhaseInfo
 * JD-Core Version:    0.6.2
 */