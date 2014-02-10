/*     */ package invmod.client;
/*     */ 
/*     */ import bgj;
/*     */ import cpw.mods.fml.client.FMLClientHandler;
/*     */ import cpw.mods.fml.client.registry.RenderingRegistry;
/*     */ import invmod.client.render.AnimationRegistry;
/*     */ import invmod.client.render.ModelIMSkeleton;
/*     */ import invmod.client.render.ModelImp;
/*     */ import invmod.client.render.ModelThrower;
/*     */ import invmod.client.render.ModelTrap;
/*     */ import invmod.client.render.RenderB;
/*     */ import invmod.client.render.RenderBolt;
/*     */ import invmod.client.render.RenderBoulder;
/*     */ import invmod.client.render.RenderBurrower;
/*     */ import invmod.client.render.RenderEgg;
/*     */ import invmod.client.render.RenderGiantBird;
/*     */ import invmod.client.render.RenderIMCreeper;
/*     */ import invmod.client.render.RenderIMSkeleton;
/*     */ import invmod.client.render.RenderIMZombie;
/*     */ import invmod.client.render.RenderImp;
/*     */ import invmod.client.render.RenderInvis;
/*     */ import invmod.client.render.RenderPenArrow;
/*     */ import invmod.client.render.RenderPigEngy;
/*     */ import invmod.client.render.RenderSpiderIM;
/*     */ import invmod.client.render.RenderThrower;
/*     */ import invmod.client.render.RenderTrap;
/*     */ import invmod.client.render.animation.Animation;
/*     */ import invmod.client.render.animation.AnimationAction;
/*     */ import invmod.client.render.animation.AnimationPhaseInfo;
/*     */ import invmod.client.render.animation.BonesBirdLegs;
/*     */ import invmod.client.render.animation.BonesMouth;
/*     */ import invmod.client.render.animation.BonesWings;
/*     */ import invmod.client.render.animation.InterpType;
/*     */ import invmod.client.render.animation.KeyFrame;
/*     */ import invmod.client.render.animation.Transition;
/*     */ import invmod.common.ProxyCommon;
/*     */ import invmod.common.entity.EntityIMArrowOld;
/*     */ import invmod.common.entity.EntityIMBird;
/*     */ import invmod.common.entity.EntityIMBolt;
/*     */ import invmod.common.entity.EntityIMBoulder;
/*     */ import invmod.common.entity.EntityIMBurrower;
/*     */ import invmod.common.entity.EntityIMCreeper;
/*     */ import invmod.common.entity.EntityIMEgg;
/*     */ import invmod.common.entity.EntityIMGiantBird;
/*     */ import invmod.common.entity.EntityIMImp;
/*     */ import invmod.common.entity.EntityIMPigEngy;
/*     */ import invmod.common.entity.EntityIMSkeleton;
/*     */ import invmod.common.entity.EntityIMSpawnProxy;
/*     */ import invmod.common.entity.EntityIMSpider;
/*     */ import invmod.common.entity.EntityIMThrower;
/*     */ import invmod.common.entity.EntityIMTrap;
/*     */ import invmod.common.entity.EntityIMZombie;
/*     */ import invmod.common.entity.EntitySFX;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.gui.GuiScreenAddServer;
/*     */ import net.minecraft.client.model.ModelCreeper;
/*     */ import net.minecraft.client.model.ModelWitch;
/*     */ import net.minecraft.client.renderer.entity.RenderDragon;
/*     */ import net.minecraft.client.settings.KeyBinding;
/*     */ import net.minecraft.util.Timer;
/*     */ import nm;
/*     */ 
/*     */ public class ProxyClient extends ProxyCommon
/*     */ {
/*     */   public void preloadTexture(String texture)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int addTextureOverride(String fileToOverride, String fileToAdd)
/*     */   {
/*  61 */     return RenderingRegistry.addTextureOverride(fileToOverride, fileToAdd);
/*     */   }
/*     */ 
/*     */   public void registerEntityRenderingHandler(Class<? extends nm> entityClass, bgj renderer)
/*     */   {
/*  67 */     RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
/*     */   }
/*     */ 
/*     */   public void printGuiMessage(String message)
/*     */   {
/*  73 */     FMLClientHandler.instance().getClient().r.b().a(message);
/*     */   }
/*     */ 
/*     */   public void registerEntityRenderers()
/*     */   {
/*  79 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMZombie.class, new RenderIMZombie(new ModelWitch(0.0F, true), 0.5F));
/*  80 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMSkeleton.class, new RenderIMSkeleton(new ModelIMSkeleton(), 0.5F));
/*  81 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMSpider.class, new RenderSpiderIM());
/*  82 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMPigEngy.class, new RenderPigEngy(new ModelCreeper(), 0.5F));
/*  83 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMImp.class, new RenderImp(new ModelImp(), 0.3F));
/*  84 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMThrower.class, new RenderThrower(new ModelThrower(), 1.5F));
/*  85 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMBurrower.class, new RenderBurrower());
/*  86 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMBoulder.class, new RenderBoulder());
/*  87 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMTrap.class, new RenderTrap(new ModelTrap()));
/*  88 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMArrowOld.class, new RenderPenArrow());
/*  89 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMBolt.class, new RenderBolt());
/*  90 */     RenderingRegistry.registerEntityRenderingHandler(EntitySFX.class, new RenderInvis());
/*  91 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMSpawnProxy.class, new RenderInvis());
/*  92 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMEgg.class, new RenderEgg());
/*     */ 
/*  94 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMCreeper.class, new RenderIMCreeper());
/*  95 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMBird.class, new RenderB());
/*  96 */     RenderingRegistry.registerEntityRenderingHandler(EntityIMGiantBird.class, new RenderGiantBird());
/*     */   }
/*     */ 
/*     */   public void loadAnimations()
/*     */   {
/* 102 */     EnumMap allKeyFrames = new EnumMap(BonesBirdLegs.class);
/* 103 */     List animationPhases = new ArrayList(2);
/* 104 */     int x = 17;
/* 105 */     float totalFrames = 331 + x;
/*     */ 
/* 108 */     Map transitions = new HashMap(1);
/* 109 */     Transition defaultTransition = new Transition(AnimationAction.STAND, 1.0F / totalFrames, 0.0F);
/* 110 */     transitions.put(AnimationAction.STAND, defaultTransition);
/* 111 */     transitions.put(AnimationAction.STAND_TO_RUN, new Transition(AnimationAction.STAND_TO_RUN, 1.0F / totalFrames, 1.0F / totalFrames));
/* 112 */     transitions.put(AnimationAction.LEGS_RETRACT, new Transition(AnimationAction.LEGS_RETRACT, 1.0F / totalFrames, (211.0F + x) / totalFrames));
/* 113 */     transitions.put(AnimationAction.LEGS_CLAW_ATTACK_P1, new Transition(AnimationAction.LEGS_CLAW_ATTACK_P1, 1.0F / totalFrames, (171.0F + x) / totalFrames));
/* 114 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.STAND, 0.0F, 1.0F / totalFrames, defaultTransition, transitions));
/*     */ 
/* 118 */     transitions = new HashMap(1);
/* 119 */     defaultTransition = new Transition(AnimationAction.RUN, 38.0F / totalFrames, 38.0F / totalFrames);
/* 120 */     transitions.put(AnimationAction.RUN, defaultTransition);
/* 121 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.STAND_TO_RUN, 1.0F / totalFrames, 38.0F / totalFrames, defaultTransition, transitions));
/*     */ 
/* 124 */     transitions = new HashMap(1);
/* 125 */     defaultTransition = new Transition(AnimationAction.RUN, (170.0F + x) / totalFrames, 38.0F / totalFrames);
/* 126 */     transitions.put(AnimationAction.RUN, defaultTransition);
/* 127 */     transitions.put(AnimationAction.STAND, new Transition(AnimationAction.STAND, (170.0F + x) / totalFrames, 0.0F));
/* 128 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.RUN, 38.0F / totalFrames, (170.0F + x) / totalFrames, defaultTransition, transitions));
/*     */ 
/* 131 */     transitions = new HashMap(1);
/* 132 */     defaultTransition = new Transition(AnimationAction.LEGS_UNRETRACT, (251.0F + x) / totalFrames, (251.0F + x) / totalFrames);
/* 133 */     transitions.put(AnimationAction.LEGS_UNRETRACT, defaultTransition);
/* 134 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.LEGS_RETRACT, (211.0F + x) / totalFrames, (251.0F + x) / totalFrames, defaultTransition, transitions));
/*     */ 
/* 137 */     transitions = new HashMap(1);
/* 138 */     defaultTransition = new Transition(AnimationAction.STAND, (291.0F + x) / totalFrames, 0.0F);
/* 139 */     transitions.put(AnimationAction.STAND, defaultTransition);
/* 140 */     transitions.put(AnimationAction.LEGS_RETRACT, new Transition(AnimationAction.LEGS_RETRACT, (291.0F + x) / totalFrames, (211.0F + x) / totalFrames));
/* 141 */     transitions.put(AnimationAction.LEGS_CLAW_ATTACK_P1, new Transition(AnimationAction.LEGS_CLAW_ATTACK_P1, (291.0F + x) / totalFrames, (291.0F + x) / totalFrames));
/* 142 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.LEGS_UNRETRACT, (251.0F + x) / totalFrames, (291.0F + x) / totalFrames, defaultTransition, transitions));
/*     */ 
/* 145 */     transitions = new HashMap(1);
/* 146 */     defaultTransition = new Transition(AnimationAction.LEGS_CLAW_ATTACK_P2, (331.0F + x) / totalFrames, (171.0F + x) / totalFrames);
/* 147 */     transitions.put(AnimationAction.LEGS_CLAW_ATTACK_P2, defaultTransition);
/* 148 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.LEGS_CLAW_ATTACK_P1, (291.0F + x) / totalFrames, (331.0F + x) / totalFrames, defaultTransition, transitions));
/*     */ 
/* 151 */     transitions = new HashMap(1);
/* 152 */     defaultTransition = new Transition(AnimationAction.STAND, (211.0F + x) / totalFrames, 0.0F);
/* 153 */     transitions.put(AnimationAction.STAND, defaultTransition);
/* 154 */     transitions.put(AnimationAction.LEGS_RETRACT, new Transition(AnimationAction.LEGS_RETRACT, (211.0F + x) / totalFrames, (211.0F + x) / totalFrames));
/* 155 */     transitions.put(AnimationAction.LEGS_CLAW_ATTACK_P1, new Transition(AnimationAction.LEGS_CLAW_ATTACK_P1, (211.0F + x) / totalFrames, (291.0F + x) / totalFrames));
/* 156 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.LEGS_CLAW_ATTACK_P2, (171.0F + x) / totalFrames, (211.0F + x) / totalFrames, defaultTransition, transitions));
/*     */ 
/* 158 */     float frameUnit = 1.0F / totalFrames;
/* 159 */     float runBegin = 38.0F * frameUnit;
/* 160 */     float runEnd = (170 + x) * frameUnit;
/*     */ 
/* 164 */     List leftThighFrames = new ArrayList(13);
/* 165 */     leftThighFrames.add(new KeyFrame(0.0F, -15.0F, 0.0F, -5.0F, InterpType.LINEAR));
/* 166 */     leftThighFrames.add(new KeyFrame(1.0F * frameUnit, -15.0F, 0.0F, -5.0F, InterpType.LINEAR));
/* 167 */     leftThighFrames.add(new KeyFrame(5.0F * frameUnit, -12.6F, 0.2F, 5.0F, InterpType.LINEAR));
/* 168 */     leftThighFrames.add(new KeyFrame(10.0F * frameUnit, 21.200001F, -0.6F, 5.2F, InterpType.LINEAR));
/* 169 */     leftThighFrames.add(new KeyFrame(15.0F * frameUnit, -32.0F, -1.7F, 5.7F, InterpType.LINEAR));
/* 170 */     leftThighFrames.add(new KeyFrame(25.0F * frameUnit, -57.0F, -6.4F, 9.0F, InterpType.LINEAR));
/* 171 */     leftThighFrames.add(new KeyFrame(35.0F * frameUnit, -76.5F, -19.299999F, 21.200001F, InterpType.LINEAR));
/* 172 */     KeyFrame.toRadians(leftThighFrames);
/* 173 */     List leftThighRunCycle = new ArrayList(7);
/* 174 */     leftThighRunCycle.add(new KeyFrame(38.0F * frameUnit, -74.099998F, 0.0F, -6.5F, InterpType.LINEAR));
/* 175 */     leftThighRunCycle.add(new KeyFrame(44.0F * frameUnit, -63.700001F, 0.0F, -6.5F, InterpType.LINEAR));
/* 176 */     leftThighRunCycle.add(new KeyFrame((80 + x) * frameUnit, 13.1F, 0.0F, -6.5F, InterpType.LINEAR));
/* 177 */     leftThighRunCycle.add(new KeyFrame((101 + x) * frameUnit, 35.700001F, 0.0F, -6.5F, InterpType.LINEAR));
/* 178 */     leftThighRunCycle.add(new KeyFrame((110 + x) * frameUnit, 20.0F, 0.0F, -6.5F, InterpType.LINEAR));
/* 179 */     leftThighRunCycle.add(new KeyFrame((140 + x) * frameUnit, -33.0F, 0.0F, -6.5F, InterpType.LINEAR));
/* 180 */     leftThighRunCycle.add(new KeyFrame((170 + x) * frameUnit, -74.099998F, 0.0F, -6.5F, InterpType.LINEAR));
/* 181 */     leftThighRunCycle.add(new KeyFrame((171 + x) * frameUnit, -76.0F, 0.0F, -5.6F, InterpType.LINEAR));
/* 182 */     leftThighRunCycle.add(new KeyFrame((211 + x) * frameUnit, -15.0F, 0.0F, -5.0F, InterpType.LINEAR));
/* 183 */     leftThighRunCycle.add(new KeyFrame((251 + x) * frameUnit, 9.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 184 */     leftThighRunCycle.add(new KeyFrame((291 + x) * frameUnit, -15.0F, 0.0F, -5.0F, InterpType.LINEAR));
/* 185 */     leftThighRunCycle.add(new KeyFrame((331 + x) * frameUnit, -76.0F, 0.0F, -5.6F, InterpType.LINEAR));
/* 186 */     KeyFrame.toRadians(leftThighRunCycle);
/*     */ 
/* 189 */     List rightThighFrames = new ArrayList(13);
/* 190 */     rightThighFrames.add(new KeyFrame(0.0F, -15.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 191 */     rightThighFrames.add(new KeyFrame(1.0F * frameUnit, -15.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 192 */     rightThighFrames.add(new KeyFrame(37.0F * frameUnit, -15.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 193 */     KeyFrame.toRadians(rightThighFrames);
/* 194 */     List rightThighRunCycle = KeyFrame.cloneFrames(leftThighRunCycle);
/* 195 */     KeyFrame.mirrorFramesX(rightThighRunCycle);
/* 196 */     KeyFrame.offsetFramesCircular(rightThighRunCycle, runBegin, runEnd, (runEnd - runBegin) / 2.0F);
/*     */ 
/* 199 */     leftThighFrames.addAll(leftThighRunCycle);
/* 200 */     rightThighFrames.addAll(rightThighRunCycle);
/* 201 */     allKeyFrames.put(BonesBirdLegs.LEFT_KNEE, leftThighFrames);
/* 202 */     allKeyFrames.put(BonesBirdLegs.RIGHT_KNEE, rightThighFrames);
/*     */ 
/* 205 */     List leftLegFrames = new ArrayList(19);
/* 206 */     leftLegFrames.add(new KeyFrame(0.0F, -41.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 207 */     leftLegFrames.add(new KeyFrame(1.0F * frameUnit, -41.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 208 */     leftLegFrames.add(new KeyFrame(10.0F * frameUnit, -80.300003F, 0.0F, 0.0F, InterpType.LINEAR));
/* 209 */     leftLegFrames.add(new KeyFrame(25.0F * frameUnit, -44.200001F, 0.0F, 0.0F, InterpType.LINEAR));
/* 210 */     leftLegFrames.add(new KeyFrame(35.0F * frameUnit, -5.6F, 0.0F, 0.0F, InterpType.LINEAR));
/* 211 */     KeyFrame.toRadians(leftLegFrames);
/* 212 */     List leftLegRunCycle = new ArrayList(16);
/* 213 */     leftLegRunCycle.add(new KeyFrame(38.0F * frameUnit, 6.6F, 0.0F, 0.0F, InterpType.LINEAR));
/* 214 */     leftLegRunCycle.add(new KeyFrame(44.0F * frameUnit, 6.5F, 0.0F, 0.0F, InterpType.LINEAR));
/* 215 */     leftLegRunCycle.add(new KeyFrame(47.0F * frameUnit, -11.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 216 */     leftLegRunCycle.add(new KeyFrame(50.0F * frameUnit, -24.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 217 */     leftLegRunCycle.add(new KeyFrame(53.0F * frameUnit, -32.900002F, 0.0F, 0.0F, InterpType.LINEAR));
/* 218 */     leftLegRunCycle.add(new KeyFrame(56.0F * frameUnit, -40.799999F, 0.0F, 0.0F, InterpType.LINEAR));
/* 219 */     leftLegRunCycle.add(new KeyFrame(59.0F * frameUnit, -46.700001F, 0.0F, 0.0F, InterpType.LINEAR));
/* 220 */     leftLegRunCycle.add(new KeyFrame(62.0F * frameUnit, -45.799999F, 0.0F, 0.0F, InterpType.LINEAR));
/* 221 */     leftLegRunCycle.add(new KeyFrame(82.0F * frameUnit, -45.599998F, 0.0F, 0.0F, InterpType.LINEAR));
/* 222 */     leftLegRunCycle.add(new KeyFrame(97.0F * frameUnit, -17.1F, 0.0F, 0.0F, InterpType.LINEAR));
/* 223 */     leftLegRunCycle.add(new KeyFrame((85 + x) * frameUnit, 0.75F, 0.0F, 0.0F, InterpType.LINEAR));
/* 224 */     leftLegRunCycle.add(new KeyFrame((90 + x) * frameUnit, -0.4F, 0.0F, 0.0F, InterpType.LINEAR));
/* 225 */     leftLegRunCycle.add(new KeyFrame((101 + x) * frameUnit, -43.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 226 */     leftLegRunCycle.add(new KeyFrame((115 + x) * frameUnit, -60.099998F, 0.0F, 0.0F, InterpType.LINEAR));
/* 227 */     leftLegRunCycle.add(new KeyFrame((154 + x) * frameUnit, -50.5F, 0.0F, 0.0F, InterpType.LINEAR));
/* 228 */     leftLegRunCycle.add(new KeyFrame((170 + x) * frameUnit, 6.6F, 0.0F, 0.0F, InterpType.LINEAR));
/* 229 */     leftLegRunCycle.add(new KeyFrame((171 + x) * frameUnit, -37.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 230 */     leftLegRunCycle.add(new KeyFrame((211 + x) * frameUnit, -41.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 231 */     leftLegRunCycle.add(new KeyFrame((251 + x) * frameUnit, 15.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 232 */     leftLegRunCycle.add(new KeyFrame((291 + x) * frameUnit, -41.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 233 */     leftLegRunCycle.add(new KeyFrame((331 + x) * frameUnit, -37.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 234 */     KeyFrame.toRadians(leftLegRunCycle);
/*     */ 
/* 237 */     List rightLegFrames = new ArrayList(19);
/* 238 */     rightLegFrames.add(new KeyFrame(0.0F, -41.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 239 */     rightLegFrames.add(new KeyFrame(37.0F * frameUnit, -41.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 240 */     KeyFrame.toRadians(rightLegFrames);
/*     */ 
/* 242 */     List rightLegRunCycle = KeyFrame.cloneFrames(leftLegRunCycle);
/* 243 */     KeyFrame.mirrorFramesX(rightLegRunCycle);
/* 244 */     KeyFrame.offsetFramesCircular(rightLegRunCycle, runBegin, runEnd, (runEnd - runBegin) / 2.0F);
/*     */ 
/* 247 */     leftLegFrames.addAll(leftLegRunCycle);
/* 248 */     rightLegFrames.addAll(rightLegRunCycle);
/* 249 */     allKeyFrames.put(BonesBirdLegs.LEFT_ANKLE, leftLegFrames);
/* 250 */     allKeyFrames.put(BonesBirdLegs.RIGHT_ANKLE, rightLegFrames);
/*     */ 
/* 254 */     List leftAnkleFrames = new ArrayList(27);
/* 255 */     leftAnkleFrames.add(new KeyFrame(0.0F, -0.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 256 */     leftAnkleFrames.add(new KeyFrame(1.0F * frameUnit, -0.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 257 */     leftAnkleFrames.add(new KeyFrame(5.0F * frameUnit, 31.700001F, -5.0F, 0.0F, InterpType.LINEAR));
/* 258 */     leftAnkleFrames.add(new KeyFrame(10.0F * frameUnit, 45.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 259 */     leftAnkleFrames.add(new KeyFrame(20.0F * frameUnit, 52.799999F, -5.0F, 0.0F, InterpType.LINEAR));
/* 260 */     leftAnkleFrames.add(new KeyFrame(25.0F * frameUnit, 51.599998F, -5.0F, 0.0F, InterpType.LINEAR));
/* 261 */     leftAnkleFrames.add(new KeyFrame(30.0F * frameUnit, 42.299999F, -5.0F, 0.0F, InterpType.LINEAR));
/* 262 */     KeyFrame.toRadians(leftAnkleFrames);
/* 263 */     List leftAnkleRunCycle = new ArrayList(21);
/* 264 */     leftAnkleRunCycle.add(new KeyFrame(38.0F * frameUnit, 28.799999F, -5.0F, 0.0F, InterpType.LINEAR));
/* 265 */     leftAnkleRunCycle.add(new KeyFrame(44.0F * frameUnit, -0.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 266 */     leftAnkleRunCycle.add(new KeyFrame(47.0F * frameUnit, 7.6F, -5.0F, 0.0F, InterpType.LINEAR));
/* 267 */     leftAnkleRunCycle.add(new KeyFrame(50.0F * frameUnit, 12.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 268 */     leftAnkleRunCycle.add(new KeyFrame(53.0F * frameUnit, 12.6F, -5.0F, 0.0F, InterpType.LINEAR));
/* 269 */     leftAnkleRunCycle.add(new KeyFrame(56.0F * frameUnit, 11.8F, -5.0F, 0.0F, InterpType.LINEAR));
/* 270 */     leftAnkleRunCycle.add(new KeyFrame(59.0F * frameUnit, 8.5F, -5.0F, 0.0F, InterpType.LINEAR));
/* 271 */     leftAnkleRunCycle.add(new KeyFrame(62.0F * frameUnit, 1.6F, -5.0F, 0.0F, InterpType.LINEAR));
/* 272 */     leftAnkleRunCycle.add(new KeyFrame(82.0F * frameUnit, -1.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 273 */     leftAnkleRunCycle.add(new KeyFrame(87.0F * frameUnit, -5.5F, -5.0F, 0.0F, InterpType.LINEAR));
/* 274 */     leftAnkleRunCycle.add(new KeyFrame(90.0F * frameUnit, -0.7F, -5.0F, 0.0F, InterpType.LINEAR));
/* 275 */     leftAnkleRunCycle.add(new KeyFrame(93.0F * frameUnit, 6.8F, -5.0F, 0.0F, InterpType.LINEAR));
/* 276 */     leftAnkleRunCycle.add(new KeyFrame(97.0F * frameUnit, -4.6F, -5.0F, 0.0F, InterpType.LINEAR));
/* 277 */     leftAnkleRunCycle.add(new KeyFrame((85 + x) * frameUnit, 20.700001F, -5.0F, 0.0F, InterpType.LINEAR));
/* 278 */     leftAnkleRunCycle.add(new KeyFrame((95 + x) * frameUnit, 34.200001F, -5.0F, 0.0F, InterpType.LINEAR));
/* 279 */     leftAnkleRunCycle.add(new KeyFrame((100 + x) * frameUnit, 45.599998F, -5.0F, 0.0F, InterpType.LINEAR));
/* 280 */     leftAnkleRunCycle.add(new KeyFrame((110 + x) * frameUnit, 36.599998F, -5.0F, 0.0F, InterpType.LINEAR));
/* 281 */     leftAnkleRunCycle.add(new KeyFrame((115 + x) * frameUnit, 38.400002F, -5.0F, 0.0F, InterpType.LINEAR));
/* 282 */     leftAnkleRunCycle.add(new KeyFrame((124 + x) * frameUnit, 50.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 283 */     leftAnkleRunCycle.add(new KeyFrame((140 + x) * frameUnit, 45.299999F, -5.0F, 0.0F, InterpType.LINEAR));
/* 284 */     leftAnkleRunCycle.add(new KeyFrame((154 + x) * frameUnit, 52.900002F, -5.0F, 0.0F, InterpType.LINEAR));
/* 285 */     leftAnkleRunCycle.add(new KeyFrame((170 + x) * frameUnit, 25.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 286 */     leftAnkleRunCycle.add(new KeyFrame((171 + x) * frameUnit, -38.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 287 */     leftAnkleRunCycle.add(new KeyFrame((211 + x) * frameUnit, 0.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 288 */     leftAnkleRunCycle.add(new KeyFrame((251 + x) * frameUnit, 22.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 289 */     leftAnkleRunCycle.add(new KeyFrame((291 + x) * frameUnit, 0.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 290 */     leftAnkleRunCycle.add(new KeyFrame((331 + x) * frameUnit, -38.0F, -5.0F, 0.0F, InterpType.LINEAR));
/* 291 */     KeyFrame.toRadians(leftAnkleRunCycle);
/*     */ 
/* 294 */     List rightAnkleFrames = new ArrayList(27);
/* 295 */     rightAnkleFrames.add(new KeyFrame(0.0F, -0.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 296 */     rightAnkleFrames.add(new KeyFrame(1.0F * frameUnit, -0.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 297 */     rightAnkleFrames.add(new KeyFrame(37.0F * frameUnit, -0.4F, -5.0F, 0.0F, InterpType.LINEAR));
/* 298 */     KeyFrame.toRadians(rightAnkleFrames);
/* 299 */     List rightAnkleRunCycle = KeyFrame.cloneFrames(leftAnkleRunCycle);
/* 300 */     KeyFrame.mirrorFramesX(rightAnkleRunCycle);
/* 301 */     KeyFrame.offsetFramesCircular(rightAnkleRunCycle, runBegin, runEnd, (runEnd - runBegin) / 2.0F);
/*     */ 
/* 304 */     leftAnkleFrames.addAll(leftAnkleRunCycle);
/* 305 */     rightAnkleFrames.addAll(rightAnkleRunCycle);
/* 306 */     allKeyFrames.put(BonesBirdLegs.LEFT_METATARSOPHALANGEAL_ARTICULATIONS, leftAnkleFrames);
/* 307 */     allKeyFrames.put(BonesBirdLegs.RIGHT_METATARSOPHALANGEAL_ARTICULATIONS, rightAnkleFrames);
/*     */ 
/* 310 */     List leftBackClawFrames = new ArrayList(21);
/* 311 */     leftBackClawFrames.add(new KeyFrame(0.0F, 77.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 312 */     leftBackClawFrames.add(new KeyFrame((170 + x) * frameUnit, 77.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 313 */     leftBackClawFrames.add(new KeyFrame((171 + x) * frameUnit, 84.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 314 */     leftBackClawFrames.add(new KeyFrame((211 + x) * frameUnit, 77.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 315 */     leftBackClawFrames.add(new KeyFrame((251 + x) * frameUnit, -7.5F, 0.0F, 0.0F, InterpType.LINEAR));
/* 316 */     leftBackClawFrames.add(new KeyFrame((291 + x) * frameUnit, 77.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 317 */     leftBackClawFrames.add(new KeyFrame((331 + x) * frameUnit, 84.0F, 0.0F, 0.0F, InterpType.LINEAR));
/*     */ 
/* 320 */     KeyFrame.toRadians(leftBackClawFrames);
/* 321 */     List rightBackClawFrames = KeyFrame.cloneFrames(leftBackClawFrames);
/* 322 */     KeyFrame.mirrorFramesX(rightBackClawFrames);
/*     */ 
/* 324 */     allKeyFrames.put(BonesBirdLegs.LEFT_BACK_CLAW, leftBackClawFrames);
/* 325 */     allKeyFrames.put(BonesBirdLegs.RIGHT_BACK_CLAW, rightBackClawFrames);
/*     */ 
/* 327 */     Animation birdRun = new Animation(BonesBirdLegs.class, 1.0F, 0.04651163F, allKeyFrames, animationPhases);
/* 328 */     AnimationRegistry.instance().registerAnimation("bird_run", birdRun);
/*     */ 
/* 331 */     EnumMap allKeyFramesWings = new EnumMap(BonesWings.class);
/* 332 */     animationPhases = new ArrayList(3);
/*     */ 
/* 335 */     transitions = new HashMap(1);
/* 336 */     defaultTransition = new Transition(AnimationAction.WINGFLAP, 0.2714932F, 0.0F);
/* 337 */     transitions.put(AnimationAction.WINGFLAP, defaultTransition);
/* 338 */     transitions.put(AnimationAction.WINGTUCK, new Transition(AnimationAction.WINGTUCK, 0.06787331F, 0.2760181F));
/* 339 */     transitions.put(AnimationAction.WINGGLIDE, new Transition(AnimationAction.WINGGLIDE, 0.06787331F, 0.8190045F));
/* 340 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.WINGFLAP, 0.0F, 0.2714932F, defaultTransition, transitions));
/*     */ 
/* 343 */     transitions = new HashMap(1);
/* 344 */     defaultTransition = new Transition(AnimationAction.WINGSPREAD, 0.5429865F, 0.5475113F);
/* 345 */     transitions.put(AnimationAction.WINGSPREAD, defaultTransition);
/* 346 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.WINGTUCK, 0.2760181F, 0.5429865F, defaultTransition, transitions));
/*     */ 
/* 349 */     transitions = new HashMap(1);
/* 350 */     defaultTransition = new Transition(AnimationAction.WINGTUCK, 0.8190045F, 0.2760181F);
/* 351 */     transitions.put(AnimationAction.WINGTUCK, defaultTransition);
/* 352 */     transitions.put(AnimationAction.WINGFLAP, new Transition(AnimationAction.WINGFLAP, 0.8190045F, 0.06787331F));
/* 353 */     transitions.put(AnimationAction.WINGGLIDE, new Transition(AnimationAction.WINGGLIDE, 0.8190045F, 0.8190045F));
/* 354 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.WINGSPREAD, 0.5475113F, 0.8190045F, defaultTransition, transitions));
/*     */ 
/* 357 */     transitions = new HashMap(1);
/* 358 */     defaultTransition = new Transition(AnimationAction.WINGGLIDE, 1.0F, 0.8190045F);
/* 359 */     transitions.put(AnimationAction.WINGGLIDE, defaultTransition);
/* 360 */     transitions.put(AnimationAction.WINGFLAP, new Transition(AnimationAction.WINGFLAP, 1.0F, 0.06787331F));
/* 361 */     transitions.put(AnimationAction.WINGTUCK, new Transition(AnimationAction.WINGTUCK, 1.0F, 0.2760181F));
/* 362 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.WINGGLIDE, 0.8190045F, 1.0F, defaultTransition, transitions));
/*     */ 
/* 364 */     frameUnit = 0.004524887F;
/* 365 */     List rightInnerWingFrames = new ArrayList(12);
/* 366 */     rightInnerWingFrames.add(new KeyFrame(0.0F, 2.0F, -48.0F, 0.0F, 7.0F, -8.0F, 6.0F, InterpType.LINEAR));
/* 367 */     rightInnerWingFrames.add(new KeyFrame(5.0F * frameUnit, 4.0F, -38.0F, 0.0F, InterpType.LINEAR));
/* 368 */     rightInnerWingFrames.add(new KeyFrame(10.0F * frameUnit, 5.5F, -27.5F, 0.0F, InterpType.LINEAR));
/* 369 */     rightInnerWingFrames.add(new KeyFrame(15.0F * frameUnit, 5.5F, -7.0F, 0.0F, InterpType.LINEAR));
/* 370 */     rightInnerWingFrames.add(new KeyFrame(20.0F * frameUnit, 5.5F, 15.0F, 0.0F, InterpType.LINEAR));
/* 371 */     rightInnerWingFrames.add(new KeyFrame(25.0F * frameUnit, 4.5F, 30.0F, 0.0F, InterpType.LINEAR));
/* 372 */     rightInnerWingFrames.add(new KeyFrame(30.0F * frameUnit, 2.0F, 38.0F, 9.0F, InterpType.LINEAR));
/* 373 */     rightInnerWingFrames.add(new KeyFrame(35.0F * frameUnit, 1.0F, 20.0F, 0.0F, InterpType.LINEAR));
/* 374 */     rightInnerWingFrames.add(new KeyFrame(40.0F * frameUnit, 1.0F, 3.5F, 0.0F, InterpType.LINEAR));
/* 375 */     rightInnerWingFrames.add(new KeyFrame(45.0F * frameUnit, 1.0F, -19.0F, 0.0F, InterpType.LINEAR));
/* 376 */     rightInnerWingFrames.add(new KeyFrame(50.0F * frameUnit, -3.0F, -38.0F, 0.0F, InterpType.LINEAR));
/* 377 */     rightInnerWingFrames.add(new KeyFrame(55.0F * frameUnit, -1.0F, -48.0F, 0.0F, InterpType.LINEAR));
/* 378 */     rightInnerWingFrames.add(new KeyFrame(60.0F * frameUnit, 2.0F, -48.0F, 0.0F, InterpType.LINEAR));
/* 379 */     rightInnerWingFrames.add(new KeyFrame(61.0F * frameUnit, 5.5F, -7.0F, 0.0F, 7.0F, -8.0F, 6.0F, InterpType.LINEAR));
/* 380 */     rightInnerWingFrames.add(new KeyFrame(121.0F * frameUnit, 0.71F, 88.599998F, 0.0F, 11.0F, -8.0F, 9.0F, InterpType.LINEAR));
/* 381 */     rightInnerWingFrames.add(new KeyFrame(181.0F * frameUnit, 5.5F, -7.0F, 0.0F, 7.0F, -8.0F, 6.0F, InterpType.LINEAR));
/* 382 */     rightInnerWingFrames.add(new KeyFrame(209.0F * frameUnit, 5.5F, -5.0F, 0.0F, InterpType.LINEAR));
/* 383 */     rightInnerWingFrames.add(new KeyFrame(221.0F * frameUnit, 5.5F, -7.0F, 0.0F, InterpType.LINEAR));
/*     */ 
/* 385 */     KeyFrame.toRadians(rightInnerWingFrames);
/* 386 */     List leftInnerWingFrames = KeyFrame.cloneFrames(rightInnerWingFrames);
/* 387 */     KeyFrame.mirrorFramesX(leftInnerWingFrames);
/* 388 */     allKeyFramesWings.put(BonesWings.LEFT_SHOULDER, rightInnerWingFrames);
/* 389 */     allKeyFramesWings.put(BonesWings.RIGHT_SHOULDER, leftInnerWingFrames);
/*     */ 
/* 391 */     List rightOuterWingFrames = new ArrayList(13);
/* 392 */     rightOuterWingFrames.add(new KeyFrame(0.0F, 2.0F, 34.5F, 0.0F, 23.0F, 1.0F, 0.0F, InterpType.LINEAR));
/* 393 */     rightOuterWingFrames.add(new KeyFrame(5.0F * frameUnit, 5.0F, 13.0F, -7.0F, InterpType.LINEAR));
/* 394 */     rightOuterWingFrames.add(new KeyFrame(10.0F * frameUnit, 7.0F, 8.5F, -10.0F, InterpType.LINEAR));
/* 395 */     rightOuterWingFrames.add(new KeyFrame(15.0F * frameUnit, 7.5F, -2.5F, -10.0F, InterpType.LINEAR));
/* 396 */     rightOuterWingFrames.add(new KeyFrame(25.0F * frameUnit, 5.0F, 7.0F, -10.0F, InterpType.LINEAR));
/* 397 */     rightOuterWingFrames.add(new KeyFrame(30.0F * frameUnit, 2.0F, 15.0F, 0.0F, InterpType.LINEAR));
/* 398 */     rightOuterWingFrames.add(new KeyFrame(35.0F * frameUnit, -3.0F, 37.0F, 12.0F, InterpType.LINEAR));
/* 399 */     rightOuterWingFrames.add(new KeyFrame(40.0F * frameUnit, -9.0F, 56.0F, 27.0F, InterpType.LINEAR));
/* 400 */     rightOuterWingFrames.add(new KeyFrame(45.0F * frameUnit, -13.0F, 68.0F, 28.0F, InterpType.LINEAR));
/* 401 */     rightOuterWingFrames.add(new KeyFrame(50.0F * frameUnit, -13.5F, 70.0F, 31.5F, InterpType.LINEAR));
/* 402 */     rightOuterWingFrames.add(new KeyFrame(53.0F * frameUnit, -9.0F, 71.0F, 31.0F, InterpType.LINEAR));
/* 403 */     rightOuterWingFrames.add(new KeyFrame(55.0F * frameUnit, -3.5F, 65.5F, 22.0F, InterpType.LINEAR));
/* 404 */     rightOuterWingFrames.add(new KeyFrame(58.0F * frameUnit, 0.0F, 52.0F, 8.0F, InterpType.LINEAR));
/* 405 */     rightOuterWingFrames.add(new KeyFrame(60.0F * frameUnit, 2.0F, 34.5F, 0.0F, InterpType.LINEAR));
/* 406 */     rightOuterWingFrames.add(new KeyFrame(61.0F * frameUnit, -5.0F, -2.5F, -10.0F, 23.0F, 1.0F, 0.0F, InterpType.LINEAR));
/* 407 */     rightOuterWingFrames.add(new KeyFrame(76.0F * frameUnit, 0.0F, 0.0F, 15.0F, 22.0F, 1.0F, 0.0F, InterpType.LINEAR));
/* 408 */     rightOuterWingFrames.add(new KeyFrame(101.0F * frameUnit, 0.0F, 0.0F, 83.0F, 20.33F, 1.0F, 0.0F, InterpType.LINEAR));
/* 409 */     rightOuterWingFrames.add(new KeyFrame(121.0F * frameUnit, 0.0F, 0.0F, 90.0F, 19.0F, 1.0F, 0.0F, InterpType.LINEAR));
/* 410 */     rightOuterWingFrames.add(new KeyFrame(141.0F * frameUnit, 0.0F, 0.0F, 83.0F, 20.33F, 1.0F, 0.0F, InterpType.LINEAR));
/* 411 */     rightOuterWingFrames.add(new KeyFrame(166.0F * frameUnit, 0.0F, 0.0F, 15.0F, 22.0F, 1.0F, 0.0F, InterpType.LINEAR));
/* 412 */     rightOuterWingFrames.add(new KeyFrame(181.0F * frameUnit, -5.0F, -2.5F, -10.0F, 23.0F, 1.0F, 0.0F, InterpType.LINEAR));
/* 413 */     rightOuterWingFrames.add(new KeyFrame(209.0F * frameUnit, -5.0F, -1.3F, -10.0F, InterpType.LINEAR));
/* 414 */     rightOuterWingFrames.add(new KeyFrame(221.0F * frameUnit, -5.0F, -2.5F, -10.0F, InterpType.LINEAR));
/* 415 */     KeyFrame.toRadians(rightOuterWingFrames);
/* 416 */     List leftOuterWingFrames = KeyFrame.cloneFrames(rightOuterWingFrames);
/* 417 */     KeyFrame.mirrorFramesX(leftOuterWingFrames);
/* 418 */     allKeyFramesWings.put(BonesWings.LEFT_ELBOW, rightOuterWingFrames);
/* 419 */     allKeyFramesWings.put(BonesWings.RIGHT_ELBOW, leftOuterWingFrames);
/*     */ 
/* 421 */     Animation wingFlap = new Animation(BonesWings.class, 1.0F, 0.01666667F, allKeyFramesWings, animationPhases);
/* 422 */     AnimationRegistry.instance().registerAnimation("wing_flap_2_piece", wingFlap);
/*     */ 
/* 427 */     EnumMap allKeyFramesBeak = new EnumMap(BonesMouth.class);
/* 428 */     animationPhases = new ArrayList(3);
/*     */ 
/* 431 */     transitions = new HashMap(1);
/* 432 */     defaultTransition = new Transition(AnimationAction.MOUTH_CLOSE, 0.5F, 0.5083333F);
/* 433 */     transitions.put(AnimationAction.MOUTH_CLOSE, defaultTransition);
/* 434 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.MOUTH_OPEN, 0.0F, 0.5F, defaultTransition, transitions));
/*     */ 
/* 437 */     transitions = new HashMap(1);
/* 438 */     defaultTransition = new Transition(AnimationAction.MOUTH_OPEN, 1.0F, 0.0F);
/* 439 */     transitions.put(AnimationAction.MOUTH_OPEN, defaultTransition);
/* 440 */     animationPhases.add(new AnimationPhaseInfo(AnimationAction.MOUTH_CLOSE, 0.5F, 1.0F, defaultTransition, transitions));
/*     */ 
/* 442 */     frameUnit = 0.008333334F;
/* 443 */     List upperBeakFrames = new ArrayList(3);
/* 444 */     upperBeakFrames.add(new KeyFrame(0.0F * frameUnit, 0.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 445 */     upperBeakFrames.add(new KeyFrame(60.0F * frameUnit, -8.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 446 */     upperBeakFrames.add(new KeyFrame(120.0F * frameUnit, 0.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 447 */     KeyFrame.toRadians(upperBeakFrames);
/* 448 */     allKeyFramesBeak.put(BonesMouth.UPPER_MOUTH, upperBeakFrames);
/*     */ 
/* 450 */     List lowerBeakFrames = new ArrayList(3);
/* 451 */     lowerBeakFrames.add(new KeyFrame(0.0F * frameUnit, 0.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 452 */     lowerBeakFrames.add(new KeyFrame(60.0F * frameUnit, 20.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 453 */     lowerBeakFrames.add(new KeyFrame(120.0F * frameUnit, 0.0F, 0.0F, 0.0F, InterpType.LINEAR));
/* 454 */     KeyFrame.toRadians(lowerBeakFrames);
/* 455 */     allKeyFramesBeak.put(BonesMouth.LOWER_MOUTH, lowerBeakFrames);
/*     */ 
/* 457 */     Animation beak = new Animation(BonesMouth.class, 1.0F, 0.1F, allKeyFramesBeak, animationPhases);
/* 458 */     AnimationRegistry.instance().registerAnimation("bird_beak", beak);
/*     */   }
/*     */ 
/*     */   public File getFile(String fileName)
/*     */   {
/* 464 */     return new File(FMLClientHandler.instance().getClient().x.getPath() + fileName);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.ProxyClient
 * JD-Core Version:    0.6.2
 */