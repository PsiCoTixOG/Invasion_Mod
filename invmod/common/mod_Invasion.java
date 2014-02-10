/*     */ package invmod.common;
/*     */ 
/*     */ import cpw.mods.fml.common.FMLCommonHandler;
/*     */ import cpw.mods.fml.common.Mod;
/*     */ import cpw.mods.fml.common.Mod.EventHandler;
/*     */ import cpw.mods.fml.common.SidedProxy;
/*     */ import cpw.mods.fml.common.event.FMLInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLPreInitializationEvent;
/*     */ import cpw.mods.fml.common.event.FMLServerStartingEvent;
/*     */ import cpw.mods.fml.common.network.NetworkMod;
/*     */ import cpw.mods.fml.common.network.NetworkRegistry;
/*     */ import cpw.mods.fml.common.registry.EntityRegistry;
/*     */ import cpw.mods.fml.common.registry.GameRegistry;
/*     */ import cpw.mods.fml.common.registry.LanguageRegistry;
/*     */ import cpw.mods.fml.common.registry.TickRegistry;
/*     */ import cpw.mods.fml.relauncher.Side;
/*     */ import invmod.client.BowHackHandler;
/*     */ import invmod.client.TickHandlerClient;
/*     */ import invmod.common.entity.EntityIMArrowOld;
/*     */ import invmod.common.entity.EntityIMBird;
/*     */ import invmod.common.entity.EntityIMBolt;
/*     */ import invmod.common.entity.EntityIMBoulder;
/*     */ import invmod.common.entity.EntityIMCreeper;
/*     */ import invmod.common.entity.EntityIMEgg;
/*     */ import invmod.common.entity.EntityIMGiantBird;
/*     */ import invmod.common.entity.EntityIMLiving;
/*     */ import invmod.common.entity.EntityIMPigEngy;
/*     */ import invmod.common.entity.EntityIMSkeleton;
/*     */ import invmod.common.entity.EntityIMSpawnProxy;
/*     */ import invmod.common.entity.EntityIMSpider;
/*     */ import invmod.common.entity.EntityIMThrower;
/*     */ import invmod.common.entity.EntityIMTrap;
/*     */ import invmod.common.entity.EntityIMWolf;
/*     */ import invmod.common.entity.EntityIMZombie;
/*     */ import invmod.common.item.ItemDebugWand;
/*     */ import invmod.common.item.ItemIM;
/*     */ import invmod.common.item.ItemIMBow;
/*     */ import invmod.common.item.ItemIMTrap;
/*     */ import invmod.common.item.ItemInfusedSword;
/*     */ import invmod.common.item.ItemProbe;
/*     */ import invmod.common.item.ItemRemnants;
/*     */ import invmod.common.item.ItemRiftFlux;
/*     */ import invmod.common.item.ItemStrangeBone;
/*     */ import invmod.common.nexus.BlockNexus;
/*     */ import invmod.common.nexus.IEntityIMPattern;
/*     */ import invmod.common.nexus.IMWaveBuilder;
/*     */ import invmod.common.nexus.MobBuilder;
/*     */ import invmod.common.nexus.TileEntityNexus;
/*     */ import invmod.common.util.ISelect;
/*     */ import invmod.common.util.RandomSelectionPool;

/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Random;

/*     */ import net.minecraft.block.BlockEndPortal;
/*     */ import net.minecraft.command.CommandHandler;
/*     */ import net.minecraft.command.ICommandManager;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.player.CallableItemName; //PSICOTIX NOTE: No Idea what this is ?
/*     */ import net.minecraft.item.EnumToolMaterial;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemMapBase;
/*     */ import net.minecraft.network.packet.Packet103SetSlot;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerPositionComparator;
/*     */ import net.minecraft.src.cu; //PSICOTIX NOTE: World Gen? 
/*     */ import net.minecraft.src.nm; //PSICOTIX NOTE: Derp?
/*     */ import net.minecraft.util.CombatTracker;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.ServerBlockEventList;
/*     */ import net.minecraft.world.biome.BiomeGenBeach;
/*     */ import net.minecraftforge.common.DimensionManager;
/*     */ import net.minecraftforge.common.MinecraftForge;
/*     */ import net.minecraftforge.event.EventBus;
/*     */ 
/*     */ @Mod(modid="mod_Invasion", name="Invasion", version="0.12.0")
/*     */ @NetworkMod(clientSideRequired=true, serverSideRequired=false)
/*     */ public class mod_Invasion
/*     */ {
/*     */ 
/*     */   @SidedProxy(clientSide="invmod.client.PacketHandlerClient", serverSide="invmod.common.PacketHandlerCommon")
/*     */   public static PacketHandlerCommon packetHandler;
/*     */ 
/*     */   @SidedProxy(clientSide="invmod.client.SoundHandlerClient", serverSide="invmod.common.SoundHandlerCommon")
/* 100 */   public static SoundHandlerCommon soundHandler = new SoundHandlerCommon();
/*     */ 
/*     */   @SidedProxy(clientSide="invmod.client.ProxyClient", serverSide="invmod.common.ProxyCommon")
/*     */   public static ProxyCommon proxy;
/*     */   public static ResourceLoader resourceLoader;
/*     */   public static GuiHandler guiHandler;
/*     */   public static ConfigInvasion configInvasion;
/*     */   private static File configFile;
/*     */   private static boolean runFlag;
/*     */   private static long timer;
/*     */   private static long clientElapsed;
/*     */   private static long serverElapsed;
/*     */   private static boolean serverRunFlag;
/*     */   private static int killTimer;
/*     */   private static boolean loginFlag;
/* 117 */   private static HashMap<String, Long> deathList = new HashMap();
/* 118 */   private static MobBuilder defaultMobBuilder = new MobBuilder();
/*     */   private static BufferedWriter logOut;
/*     */   private static ISelect<IEntityIMPattern> nightSpawnPool1;
/*     */   private static TileEntityNexus focusNexus;
/*     */   private static TileEntityNexus activeNexus;
/* 123 */   private static boolean isInvasionActive = false;
/* 124 */   private static boolean soundInstalled = false;
/*     */   public static final byte PACKET_SFX = 0;
/*     */   public static final byte PACKET_INV_MOB_SPAWN = 2;
/*     */   private static final int DEFAULT_NEXUS_BLOCK_ID = 216;
/*     */   private static final int DEFAULT_GUI_ID_NEXUS = 76;
/*     */   private static final int DEFAULT_ITEM_ID_DEBUGWAND = 24399;
/*     */   private static final int DEFAULT_ITEM_ID_PHASECRYSTAL = 24400;
/*     */   private static final int DEFAULT_ITEM_ID_RIFTFLUX = 24401;
/*     */   private static final int DEFAULT_ITEM_ID_REMNANTS = 24402;
/*     */   private static final int DEFAULT_ITEM_ID_NEXUSCATALYST = 24403;
/*     */   private static final int DEFAULT_ITEM_ID_INFUSEDSWORD = 24404;
/*     */   private static final int DEFAULT_ITEM_ID_IMTRAP = 24405;
/*     */   private static final int DEFAULT_ITEM_ID_IMBOW = 24406;
/*     */   private static final int DEFAULT_ITEM_ID_CATAMIXTURE = 24407;
/*     */   private static final int DEFAULT_ITEM_ID_STABLECATAMIXTURE = 24408;
/*     */   private static final int DEFAULT_ITEM_ID_STABLENEXUSCATA = 24409;
/*     */   private static final int DEFAULT_ITEM_ID_DAMPINGAGENT = 24410;
/*     */   private static final int DEFAULT_ITEM_ID_STRONGDAMPINGAGENT = 24411;
/*     */   private static final int DEFAULT_ITEM_ID_STRANGEBONE = 24412;
/*     */   private static final int DEFAULT_ITEM_ID_PROBE = 24413;
/*     */   private static final int DEFAULT_ITEM_ID_STRONGCATALYST = 24414;
/*     */   private static final int DEFAULT_ITEM_ID_HAMMER = 24415;
/*     */   private static final boolean DEFAULT_SOUNDS_ENABLED = true;
/*     */   private static final boolean DEFAULT_CRAFT_ITEMS_ENABLED = true;
/*     */   private static final boolean DEFAULT_NIGHT_SPAWNS_ENABLED = false;
/*     */   private static final int DEFAULT_MIN_CONT_MODE_DAYS = 2;
/*     */   private static final int DEFAULT_MAX_CONT_MODE_DAYS = 3;
/*     */   private static final int DEFAULT_NIGHT_MOB_SIGHT_RANGE = 20;
/*     */   private static final int DEFAULT_NIGHT_MOB_SENSE_RANGE = 8;
/*     */   private static final int DEFAULT_NIGHT_MOB_SPAWN_CHANCE = 30;
/*     */   private static final int DEFAULT_NIGHT_MOB_MAX_GROUP_SIZE = 3;
/*     */   private static final int DEFAULT_NIGHT_MOB_LIMIT_OVERRIDE = 70;
/*     */   private static final float DEFAULT_NIGHT_MOB_STATS_SCALING = 1.0F;
/*     */   private static final boolean DEFAULT_NIGHT_MOBS_BURN = true;
/* 164 */   public static final String[] DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS = { "zombie_t1_any", "zombie_t2_any_basic", "spider_t2_any", "none", "none", "none" };
/* 165 */   public static final float[] DEFAULT_NIGHT_MOB_PATTERN_1_SLOT_WEIGHTS = { 1.0F, 1.0F, 0.5F, 0.0F, 0.0F, 0.0F };
/*     */ 
/* 170 */   public static final BowHackHandler bowHandler = new BowHackHandler();
/*     */   private static boolean soundsEnabled;
/*     */   private static boolean craftItemsEnabled;
/*     */   private static boolean debugMode;
/*     */   private static int guiIdNexus;
/*     */   private static int minContinuousModeDays;
/*     */   private static int maxContinuousModeDays;
/*     */   private static boolean nightSpawnsEnabled;
/*     */   private static int nexusBlockId;
/*     */   private static int nightMobSightRange;
/*     */   private static int nightMobSenseRange;
/*     */   private static int nightMobSpawnChance;
/*     */   private static int nightMobMaxGroupSize;
/*     */   private static int maxNightMobs;
/*     */   private static float nightMobStatsScaling;
/*     */   private static boolean nightMobsBurnInDay;
/*     */   public static BlockNexus blockNexus;
/*     */   public static ItemHoe itemPhaseCrystal;
/*     */   public static ItemHoe itemRiftFlux;
/*     */   public static ItemHoe itemRemnants;
/*     */   public static ItemHoe itemNexusCatalyst;
/*     */   public static ItemHoe itemInfusedSword;
/*     */   public static ItemHoe itemIMTrap;
/*     */   public static ItemHoe itemPenBow;
/*     */   public static ItemHoe itemCataMixture;
/*     */   public static ItemHoe itemStableCataMixture;
/*     */   public static ItemHoe itemStableNexusCatalyst;
/*     */   public static ItemHoe itemDampingAgent;
/*     */   public static ItemHoe itemStrongDampingAgent;
/*     */   public static ItemHoe itemStrangeBone;
/*     */   public static ItemHoe itemProbe;
/*     */   public static ItemHoe itemStrongCatalyst;
/*     */   public static ItemHoe itemEngyHammer;
/*     */   public static ItemHoe itemDebugWand;
/*     */   public static mod_Invasion instance;
/*     */ 
/* 220 */   public mod_Invasion() { instance = this;
/* 221 */     runFlag = true;
/* 222 */     serverRunFlag = true;
/* 223 */     loginFlag = false;
/* 224 */     timer = 0L;
/* 225 */     clientElapsed = 0L;
/* 226 */     guiHandler = new GuiHandler();
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void preInit(FMLPreInitializationEvent event)
/*     */   {
/* 237 */     File logFile = proxy.getFile("/invasion_log.log");
/*     */     try
/*     */     {
/* 240 */       if (!logFile.exists())
/* 241 */         logFile.createNewFile();
/* 242 */       logOut = new BufferedWriter(new FileWriter(logFile));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 246 */       logOut = null;
/* 247 */       log("Couldn't write to logfile");
/* 248 */       log(e.getMessage());
/*     */     }
/*     */ 
/* 252 */     configFile = proxy.getFile("/invasion_config.txt");
/*     */ 
/* 255 */     configInvasion = new ConfigInvasion();
/* 256 */     configInvasion.loadConfig(configFile);
/*     */ 
/* 260 */     soundsEnabled = configInvasion.getPropertyValueBoolean("sounds-enabled", true);
/* 261 */     craftItemsEnabled = configInvasion.getPropertyValueBoolean("craft-items-enabled", true);
/* 262 */     debugMode = configInvasion.getPropertyValueBoolean("debug", false);
/*     */ 
/* 265 */     guiIdNexus = configInvasion.getPropertyValueInt("guiID-Nexus", 76);
/*     */ 
/* 268 */     minContinuousModeDays = configInvasion.getPropertyValueInt("min-days-to-attack", 2);
/* 269 */     maxContinuousModeDays = configInvasion.getPropertyValueInt("max-days-to-attack", 3);
/*     */ 
/* 272 */     nightSpawnConfig();
/*     */ 
/* 275 */     HashMap strengthOverrides = new HashMap();
/* 276 */     for (int i = 1; i < 4096; i++)
/*     */     {
/* 278 */       String property = configInvasion.getProperty("block" + i + "-strength", "null");
/* 279 */       if (property != "null")
/*     */       {
/* 281 */         float strength = Float.parseFloat(property);
/* 282 */         if (strength > 0.0F)
/*     */         {
/* 284 */           strengthOverrides.put(Integer.valueOf(i), Float.valueOf(strength));
/* 285 */           EntityIMLiving.putBlockStrength(i, strength);
/* 286 */           float pathCost = 1.0F + strength * 0.4F;
/* 287 */           EntityIMLiving.putBlockCost(i, pathCost);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 293 */     configInvasion.saveConfig(configFile, strengthOverrides, debugMode);
/*     */   }
/*     */ 
/*     */   @EventHandler
/*     */   public void load(FMLInitializationEvent event)
/*     */   {
/* 302 */     MinecraftForge.EVENT_BUS.register(soundHandler);
/*     */ 
/* 305 */     NetworkRegistry.instance().registerGuiHandler(instance, guiHandler);
/* 306 */     NetworkRegistry.instance().registerChannel(packetHandler, "data");
/* 307 */     TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
/* 308 */     TickRegistry.registerTickHandler(new TickHandlerServer(), Side.SERVER);
/*     */ 
/* 310 */     loadBlocks();
/* 311 */     loadItems();
/* 312 */     loadEntities();
/* 313 */     loadNames();
/*     */ 
/* 315 */     if (craftItemsEnabled) {
/* 316 */       addRecipes();
/*     */     }
/* 318 */     if (nightSpawnsEnabled)
/*     */     {
/* 320 */       BiomeGenBeach[] biomes = { BiomeGenBeach.c, BiomeGenBeach.e, BiomeGenBeach.f, BiomeGenBeach.g, BiomeGenBeach.h, BiomeGenBeach.t, BiomeGenBeach.u, BiomeGenBeach.v, BiomeGenBeach.w, BiomeGenBeach.x };
/*     */ 
/* 323 */       EntityRegistry.addSpawn(EntityIMSpawnProxy.class, nightMobSpawnChance, 1, 1, EntityLiving.a, biomes);
/* 324 */       EntityRegistry.addSpawn(EntityWitch.class, 1, 1, 1, EntityLiving.a, biomes);
/* 325 */       EntityRegistry.addSpawn(EntitySlime.class, 1, 1, 1, EntityLiving.a, biomes);
/* 326 */       EntityRegistry.addSpawn(EntitySilverfish.class, 1, 1, 1, EntityLiving.a, biomes);
/*     */     }
/*     */ 
/* 330 */     if (maxNightMobs != 70)
/*     */     {
/*     */       try
/*     */       {
/* 334 */         System.out.println(EntityLiving.a.b());
/* 335 */         Class c = EntityLiving.class;
/* 336 */         Object[] consts = c.getEnumConstants();
/* 337 */         Class sub = consts[0].getClass();
/* 338 */         Field field = sub.getDeclaredField("maxNumberOfCreature");
/* 339 */         field.setAccessible(true);
/* 340 */         field.set(EntityLiving.a, Integer.valueOf(maxNightMobs));
/* 341 */         System.out.println(EntityLiving.a.b());
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 345 */         log(e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 349 */     soundHandler.setSoundEnabled(soundsEnabled);
/*     */   }
/*     */ 
/*     */   @Mod.ServerStarting
/*     */   public void onServerStart(FMLServerStartingEvent event)
/*     */   {
/* 355 */     ICommandManager commandManager = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
/* 356 */     if ((commandManager instanceof CommandHandler))
/*     */     {
/* 358 */       ((CommandHandler)commandManager).registerCommand(new InvasionCommand());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void loadBlocks()
/*     */   {
/* 364 */     blockNexus = new BlockNexus(configInvasion.getPropertyValueInt("blockID-Nexus", 216));
/* 365 */     blockNexus.b(6000000.0F).c(3.0F).a(BlockEndPortal.m).c("blockNexus");
/* 366 */     blockNexus.a(ItemMapBase.f);
/* 367 */     GameRegistry.registerBlock(blockNexus, "Nexus");
/* 368 */     GameRegistry.registerTileEntity(TileEntityNexus.class, "Nexus");
/*     */   }
/*     */ 
/*     */   protected void loadItems()
/*     */   {
/* 373 */     itemPhaseCrystal = new ItemIM(configInvasion.getPropertyValueInt("itemID-PhaseCrystal", 24400)).b("phaseCrystal").d(1).a(ItemMapBase.f);
/* 374 */     itemRiftFlux = new ItemRiftFlux(configInvasion.getPropertyValueInt("itemID-RiftFlux", 24401)).b("riftFlux").a(ItemMapBase.f);
/* 375 */     itemRemnants = new ItemRemnants(configInvasion.getPropertyValueInt("itemID-Remnants", 24402)).b("remnants").a(ItemMapBase.f);
/* 376 */     itemNexusCatalyst = new ItemIM(configInvasion.getPropertyValueInt("itemID-NexusCatalyst", 24403)).b("nexusCatalyst").d(1).a(ItemMapBase.f);
/* 377 */     itemInfusedSword = new ItemInfusedSword(configInvasion.getPropertyValueInt("itemID-InfusedSword", 24404)).b("infusedSword").d(1).a(ItemMapBase.f);
/* 378 */     itemPenBow = new ItemIMBow(configInvasion.getPropertyValueInt("itemID-IMBow", 24406)).b("searingBow").a(ItemMapBase.f);
/* 379 */     itemCataMixture = new ItemIM(configInvasion.getPropertyValueInt("itemID-CataMixture", 24407)).b("catalystMixture").d(1).a(ItemMapBase.f);
/* 380 */     itemStableCataMixture = new ItemIM(configInvasion.getPropertyValueInt("itemID-StableCataMixture", 24408)).b("stableCatalystMixture").d(1).a(ItemMapBase.f);
/* 381 */     itemStableNexusCatalyst = new ItemIM(configInvasion.getPropertyValueInt("itemID-StableNexusCatalyst", 24409)).b("stableNexusCatalyst").d(1).a(ItemMapBase.f);
/* 382 */     itemDampingAgent = new ItemIM(configInvasion.getPropertyValueInt("itemID-DampingAgent", 24410)).b("dampingAgent").d(1).a(ItemMapBase.f);
/* 383 */     itemStrongDampingAgent = new ItemIM(configInvasion.getPropertyValueInt("itemID-StrongDampingAgent", 24411)).b("strongDampingAgent").d(1).a(ItemMapBase.f);
/* 384 */     itemStrangeBone = new ItemStrangeBone(configInvasion.getPropertyValueInt("itemID-StrangeBone", 24412)).b("strangeBone").d(1).a(ItemMapBase.f);
/* 385 */     itemStrongCatalyst = new ItemIM(configInvasion.getPropertyValueInt("itemID-StrongCatalyst", 24414)).b("strongCatalyst").d(1).a(ItemMapBase.f);
/* 386 */     itemEngyHammer = new ItemIM(configInvasion.getPropertyValueInt("itemID-EngyHammer", 24415)).b("engyHammer").d(1);
/* 387 */     itemProbe = new ItemProbe(configInvasion.getPropertyValueInt("itemID-Probe", 24413)).b("probe").a(ItemMapBase.f);
/* 388 */     itemIMTrap = new ItemIMTrap(configInvasion.getPropertyValueInt("itemID-IMTrap", 24405)).b("trap").a(ItemMapBase.f);
/*     */ 
/* 390 */     if (debugMode)
/*     */     {
/* 392 */       itemDebugWand = new ItemDebugWand(configInvasion.getPropertyValueInt("itemID-DebugWand", 24399)).b("debugWand");
/*     */     }
/*     */     else
/*     */     {
/* 396 */       itemDebugWand = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void loadEntities()
/*     */   {
/* 402 */     EntityRegistry.registerGlobalEntityID(EntityIMZombie.class, "IMZombie", 110);
/* 403 */     EntityRegistry.registerGlobalEntityID(EntityIMSkeleton.class, "IMSkeleton", 111);
/* 404 */     EntityRegistry.registerGlobalEntityID(EntityIMSpider.class, "IMSpider", 112);
/* 405 */     EntityRegistry.registerGlobalEntityID(EntityIMPigEngy.class, "IMPigEngy", 113);
/*     */ 
/* 407 */     EntityRegistry.registerGlobalEntityID(EntityIMBird.class, "IMBird", 114);
/* 408 */     EntityRegistry.registerGlobalEntityID(EntityIMThrower.class, "IMThrower", 116);
/* 409 */     EntityRegistry.registerGlobalEntityID(EntityIMBoulder.class, "IMBoulder", 117);
/*     */ 
/* 411 */     EntityRegistry.registerGlobalEntityID(EntityIMTrap.class, "IMTrap", 109);
/* 412 */     EntityRegistry.registerGlobalEntityID(EntityIMArrowOld.class, "IMPenArrow", 118);
/* 413 */     EntityRegistry.registerGlobalEntityID(EntityIMWolf.class, "IMWolf", 119);
/* 414 */     EntityRegistry.registerGlobalEntityID(EntityIMBolt.class, "IMBolt", 115);
/* 415 */     EntityRegistry.registerGlobalEntityID(EntityIMEgg.class, "IMEgg", 108);
/* 416 */     EntityRegistry.registerGlobalEntityID(EntityIMCreeper.class, "IMCreeper", 107);
/* 417 */     EntityRegistry.registerGlobalEntityID(EntityIMGiantBird.class, "IMGiantBird", 106);
/*     */ 
/* 419 */     EntityRegistry.registerModEntity(EntityIMBoulder.class, "IMBoulder", 1, this, 36, 4, true);
/* 420 */     EntityRegistry.registerModEntity(EntityIMBolt.class, "IMBolt", 2, this, 36, 5, false);
/* 421 */     EntityRegistry.registerModEntity(EntityIMTrap.class, "IMTrap", 3, this, 36, 5, false);
/* 422 */     EntityRegistry.registerModEntity(EntityIMArrowOld.class, "IMArrow", 4, this, 70, 1, true);
/*     */ 
/* 424 */     proxy.preloadTexture("/mods/invmod/textures/zombie_old.png");
/* 425 */     proxy.preloadTexture("/mods/invmod/textures/zombieT2.png");
/* 426 */     proxy.preloadTexture("/mods/invmod/textures/zombieT2.png");
/* 427 */     proxy.preloadTexture("/mods/invmod/textures/zombieT2a.png");
/* 428 */     proxy.preloadTexture("/mods/invmod/textures/zombietar.png");
/* 429 */     proxy.preloadTexture("/mods/invmod/textures/zombieT1a.png");
/* 430 */     proxy.preloadTexture("/mods/invmod/textures/spiderT2.png");
/* 431 */     proxy.preloadTexture("/mods/invmod/textures/spiderT2b.png");
/* 432 */     proxy.preloadTexture("/mods/invmod/textures/throwerT1.png");
/* 433 */     proxy.preloadTexture("/mods/invmod/textures/pigengT1.png");
/* 434 */     proxy.preloadTexture("/mods/invmod/textures/imp.png");
/* 435 */     proxy.preloadTexture("/mods/invmod/textures/nexusgui.png");
/* 436 */     proxy.preloadTexture("/mods/invmod/textures/boulder.png");
/* 437 */     proxy.preloadTexture("/mods/invmod/textures/trap.png");
/* 438 */     proxy.preloadTexture("/mods/invmod/textures/testmodel.png");
/* 439 */     proxy.preloadTexture("/mods/invmod/textures/burrower.png");
/* 440 */     proxy.preloadTexture("/mods/invmod/textures/spideregg.png");
/* 441 */     proxy.preloadTexture("/mods/invmod/textures/zombieT3.png");
/*     */ 
/* 443 */     proxy.loadAnimations();
/* 444 */     proxy.registerEntityRenderers();
/*     */   }
/*     */ 
/*     */   protected void loadNames()
/*     */   {
/* 449 */     LanguageRegistry.addName(blockNexus, "Nexus");
/* 450 */     LanguageRegistry.addName(itemPhaseCrystal, "Phase Crystal");
/* 451 */     LanguageRegistry.addName(itemNexusCatalyst, "Nexus Catalyst");
/* 452 */     LanguageRegistry.addName(itemInfusedSword, "Infused Sword");
/* 453 */     LanguageRegistry.addName(itemPenBow, "Searing Bow");
/* 454 */     LanguageRegistry.addName(itemCataMixture, "Catalyst Mixture");
/* 455 */     LanguageRegistry.addName(itemStableCataMixture, "Stable Catalyst Mixture");
/* 456 */     LanguageRegistry.addName(itemStableNexusCatalyst, "Stable Catalyst");
/* 457 */     LanguageRegistry.addName(itemDampingAgent, "Damping Agent");
/* 458 */     LanguageRegistry.addName(itemStrongDampingAgent, "Strong Damping Agent");
/* 459 */     LanguageRegistry.addName(itemStrangeBone, "Strange Bone");
/* 460 */     LanguageRegistry.addName(itemProbe, "Probe");
/* 461 */     LanguageRegistry.addName(itemStrongCatalyst, "Strong Catalyst");
/* 462 */     LanguageRegistry.addName(new EnumToolMaterial(itemRemnants, 1, 0), ItemRemnants.remnantNames[0]);
/* 463 */     LanguageRegistry.addName(new EnumToolMaterial(itemRemnants, 1, 1), ItemRemnants.remnantNames[1]);
/* 464 */     LanguageRegistry.addName(new EnumToolMaterial(itemRemnants, 1, 2), ItemRemnants.remnantNames[2]);
/* 465 */     LanguageRegistry.addName(new EnumToolMaterial(itemRiftFlux, 1, 0), ItemRiftFlux.fluxNames[0]);
/* 466 */     LanguageRegistry.addName(new EnumToolMaterial(itemRiftFlux, 1, 1), ItemRiftFlux.fluxNames[1]);
/* 467 */     LanguageRegistry.addName(new EnumToolMaterial(itemRiftFlux, 1, 2), ItemRiftFlux.fluxNames[2]);
/* 468 */     LanguageRegistry.addName(new EnumToolMaterial(itemRiftFlux, 1, 3), ItemRiftFlux.fluxNames[3]);
/* 469 */     LanguageRegistry.addName(new EnumToolMaterial(itemIMTrap, 1, 0), ItemIMTrap.trapNames[0]);
/* 470 */     LanguageRegistry.addName(new EnumToolMaterial(itemIMTrap, 1, 1), ItemIMTrap.trapNames[1]);
/* 471 */     LanguageRegistry.addName(new EnumToolMaterial(itemIMTrap, 1, 2), ItemIMTrap.trapNames[2]);
/* 472 */     LanguageRegistry.addName(new EnumToolMaterial(itemProbe, 1, 0), ItemProbe.probeNames[0]);
/* 473 */     LanguageRegistry.addName(new EnumToolMaterial(itemProbe, 1, 1), ItemProbe.probeNames[1]);
/*     */ 
/* 475 */     if (debugMode)
/* 476 */       LanguageRegistry.addName(itemDebugWand, "Debug Wand");
/*     */   }
/*     */ 
/*     */   protected void addRecipes()
/*     */   {
/* 481 */     GameRegistry.addRecipe(new EnumToolMaterial(blockNexus, 1), new Object[] { " X ", "#D#", " # ", Character.valueOf('X'), itemPhaseCrystal, Character.valueOf('#'), ItemHoe.aE, Character.valueOf('D'), BlockEndPortal.au });
/*     */ 
/* 484 */     GameRegistry.addRecipe(new EnumToolMaterial(itemPhaseCrystal, 1), new Object[] { " X ", "#D#", " X ", Character.valueOf('X'), new EnumToolMaterial(ItemHoe.aY, 1, 4), Character.valueOf('#'), ItemHoe.aE, Character.valueOf('D'), ItemHoe.p });
/*     */ 
/* 487 */     GameRegistry.addRecipe(new EnumToolMaterial(itemPhaseCrystal, 1), new Object[] { " X ", "#D#", " X ", Character.valueOf('X'), ItemHoe.aE, Character.valueOf('#'), new EnumToolMaterial(ItemHoe.aY, 1, 4), Character.valueOf('D'), ItemHoe.p });
/*     */ 
/* 490 */     GameRegistry.addRecipe(new EnumToolMaterial(itemRiftFlux, 1, 1), new Object[] { "XXX", "XXX", "XXX", Character.valueOf('X'), new EnumToolMaterial(itemRemnants, 1, 1) });
/*     */ 
/* 493 */     GameRegistry.addRecipe(new EnumToolMaterial(itemInfusedSword, 1), new Object[] { "X  ", "X# ", "X  ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1), Character.valueOf('#'), ItemHoe.B });
/*     */ 
/* 496 */     GameRegistry.addRecipe(new EnumToolMaterial(itemCataMixture, 1), new Object[] { "   ", "D#H", " X ", Character.valueOf('X'), ItemHoe.G, Character.valueOf('#'), ItemHoe.aE, Character.valueOf('D'), ItemHoe.aZ, Character.valueOf('H'), ItemHoe.bo });
/*     */ 
/* 499 */     GameRegistry.addRecipe(new EnumToolMaterial(itemCataMixture, 1), new Object[] { "   ", "H#D", " X ", Character.valueOf('X'), ItemHoe.G, Character.valueOf('#'), ItemHoe.aE, Character.valueOf('D'), ItemHoe.aZ, Character.valueOf('H'), ItemHoe.bo });
/*     */ 
/* 502 */     GameRegistry.addRecipe(new EnumToolMaterial(itemStableCataMixture, 1), new Object[] { "   ", "D#D", " X ", Character.valueOf('X'), ItemHoe.G, Character.valueOf('#'), ItemHoe.o, Character.valueOf('D'), ItemHoe.aZ, Character.valueOf('H'), ItemHoe.bo });
/*     */ 
/* 505 */     GameRegistry.addRecipe(new EnumToolMaterial(itemDampingAgent, 1), new Object[] { "   ", "#X#", "   ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1), Character.valueOf('#'), new EnumToolMaterial(ItemHoe.aY, 1, 4) });
/*     */ 
/* 508 */     GameRegistry.addRecipe(new EnumToolMaterial(itemStrongDampingAgent, 1), new Object[] { " X ", " X ", " X ", Character.valueOf('X'), itemDampingAgent });
/*     */ 
/* 511 */     GameRegistry.addRecipe(new EnumToolMaterial(itemStrongDampingAgent, 1), new Object[] { "   ", "XXX", "   ", Character.valueOf('X'), itemDampingAgent });
/*     */ 
/* 514 */     GameRegistry.addRecipe(new EnumToolMaterial(itemStrangeBone, 1), new Object[] { "   ", "X#X", "   ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1), Character.valueOf('#'), ItemHoe.aZ });
/*     */ 
/* 517 */     GameRegistry.addRecipe(new EnumToolMaterial(itemPenBow, 1), new Object[] { "XXX", "X# ", "X  ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1), Character.valueOf('#'), ItemHoe.m });
/*     */ 
/* 520 */     GameRegistry.addRecipe(new EnumToolMaterial(ItemHoe.p, 1), new Object[] { " X ", " X ", " X ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1) });
/*     */ 
/* 523 */     GameRegistry.addRecipe(new EnumToolMaterial(ItemHoe.p, 1), new Object[] { "   ", "XXX", "   ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1) });
/*     */ 
/* 526 */     GameRegistry.addRecipe(new EnumToolMaterial(ItemHoe.q, 4), new Object[] { "   ", " X ", "   ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1) });
/*     */ 
/* 529 */     GameRegistry.addRecipe(new EnumToolMaterial(ItemHoe.aE, 24), new Object[] { "   ", "X X", "   ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1) });
/*     */ 
/* 532 */     GameRegistry.addRecipe(new EnumToolMaterial(ItemHoe.aY, 12, 4), new Object[] { " X ", "   ", " X ", Character.valueOf('X'), new EnumToolMaterial(itemRiftFlux, 1, 1) });
/*     */ 
/* 535 */     GameRegistry.addRecipe(new EnumToolMaterial(itemIMTrap, 1, 0), new Object[] { " X ", "X#X", " X ", Character.valueOf('X'), ItemHoe.q, Character.valueOf('#'), new EnumToolMaterial(itemRiftFlux, 1, 1) });
/*     */ 
/* 538 */     GameRegistry.addRecipe(new EnumToolMaterial(itemIMTrap, 1, 2), new Object[] { "   ", " # ", " X ", Character.valueOf('X'), new EnumToolMaterial(itemIMTrap, 1, 0), Character.valueOf('#'), ItemHoe.aA });
/*     */ 
/* 541 */     GameRegistry.addRecipe(new EnumToolMaterial(itemProbe, 1, 0), new Object[] { " X ", "XX ", "XX ", Character.valueOf('X'), ItemHoe.q });
/*     */ 
/* 544 */     GameRegistry.addRecipe(new EnumToolMaterial(itemProbe, 1, 1), new Object[] { " D ", " # ", " X ", Character.valueOf('X'), ItemHoe.bq, Character.valueOf('#'), itemPhaseCrystal, Character.valueOf('D'), new EnumToolMaterial(itemProbe, 1, 0) });
/*     */ 
/* 548 */     if (debugMode);
/* 579 */     GameRegistry.addSmelting(itemCataMixture.itemID, new EnumToolMaterial(itemNexusCatalyst), 1.0F);
/* 580 */     GameRegistry.addSmelting(itemStableCataMixture.itemID, new EnumToolMaterial(itemStableNexusCatalyst), 1.0F);
/*     */   }
/*     */ 
/*     */   protected void nightSpawnConfig()
/*     */   {
/* 585 */     nightSpawnsEnabled = configInvasion.getPropertyValueBoolean("night-spawns-enabled", false);
/* 586 */     nightMobSightRange = configInvasion.getPropertyValueInt("night-mob-sight-range", 20);
/* 587 */     nightMobSenseRange = configInvasion.getPropertyValueInt("night-mob-sense-range", 8);
/* 588 */     nightMobSpawnChance = configInvasion.getPropertyValueInt("night-mob-spawn-chance", 30);
/* 589 */     nightMobMaxGroupSize = configInvasion.getPropertyValueInt("night-mob-max-group-size", 3);
/* 590 */     maxNightMobs = configInvasion.getPropertyValueInt("mob-limit-override", 70);
/* 591 */     nightMobsBurnInDay = configInvasion.getPropertyValueBoolean("night-mobs-burn-in-day", true);
/*     */ 
/* 594 */     String[] pool1Patterns = new String[DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS.length];
/* 595 */     float[] pool1Weights = new float[DEFAULT_NIGHT_MOB_PATTERN_1_SLOT_WEIGHTS.length];
/* 596 */     RandomSelectionPool mobPool = new RandomSelectionPool();
/* 597 */     nightSpawnPool1 = mobPool;
/* 598 */     if (DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS.length == DEFAULT_NIGHT_MOB_PATTERN_1_SLOT_WEIGHTS.length)
/*     */     {
/* 600 */       for (int i = 0; i < DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS.length; i++)
/*     */       {
/* 602 */         pool1Patterns[i] = configInvasion.getPropertyValueString("nm-spawnpool1-slot" + (1 + i), DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS[i]);
/* 603 */         pool1Weights[i] = configInvasion.getPropertyValueFloat("nm-spawnpool1-slot" + (1 + i) + "-weight", DEFAULT_NIGHT_MOB_PATTERN_1_SLOT_WEIGHTS[i]);
/* 604 */         if (IMWaveBuilder.isPatternNameValid(pool1Patterns[i]))
/*     */         {
/* 606 */           log("Added entry for pattern 1 slot " + (i + 1));
/* 607 */           mobPool.addEntry(IMWaveBuilder.getPattern(pool1Patterns[i]), pool1Weights[i]);
/*     */         }
/*     */         else
/*     */         {
/* 611 */           log("Pattern 1 slot " + (i + 1) + " in config not recognised. Proceeding as blank.");
/* 612 */           configInvasion.setProperty("nm-spawnpool1-slot" + (1 + i), "none");
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 618 */       log("Mob pattern table element mismatch. Ensure each slot has a probability weight");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean onClientTick()
/*     */   {
/* 624 */     if (runFlag)
/*     */     {
/* 626 */       if ((soundsEnabled) && (!soundHandler.soundsInstalled()))
/*     */       {
/* 628 */         proxy.printGuiMessage("Invasion Mod Warning: Failed to auto-install sounds. You can disable this process in config or give a bug report");
/*     */       }
/* 630 */       runFlag = false;
/*     */     }
/*     */ 
/* 633 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean onServerTick()
/*     */   {
/* 638 */     if (serverRunFlag)
/*     */     {
/* 640 */       timer = System.currentTimeMillis();
/* 641 */       serverRunFlag = false;
/*     */     }
/*     */ 
/* 644 */     serverElapsed -= timer;
/* 645 */     timer = System.currentTimeMillis();
/* 646 */     serverElapsed += timer;
/* 647 */     if (serverElapsed >= 100L)
/*     */     {
/* 649 */       serverElapsed -= 100L;
/*     */ 
/* 651 */       if (loginFlag)
/*     */       {
/* 653 */         killTimer += 1;
/*     */       }
/*     */ 
/* 656 */       if (killTimer > 35)
/*     */       {
/* 658 */         killTimer = 0;
/* 659 */         loginFlag = false;
/* 660 */         for (Map.Entry entry : deathList.entrySet())
/*     */         {
/* 662 */           if (System.currentTimeMillis() - ((Long)entry.getValue()).longValue() > 300000L)
/*     */           {
/* 664 */             deathList.remove(entry.getKey());
/*     */           }
/*     */           else
/*     */           {
/* 668 */             for (ColorizerGrass world : DimensionManager.getWorlds())
/*     */             {
/* 670 */               CallableItemName player = world.a((String)entry.getKey());
/* 671 */               if (player != null)
/*     */               {
/* 673 */                 player.a(CombatTracker.k, 500.0F);
/* 674 */                 player.w();
/* 675 */                 deathList.remove(player.bu);
/* 676 */                 broadcastToAll("Nexus energies caught up to " + player.bu);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 684 */     return true;
/*     */   }
/*     */ 
/*     */   public static void addToDeathList(String username, long timeStamp)
/*     */   {
/* 689 */     deathList.put(username, Long.valueOf(timeStamp));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 695 */     return "mod_Invasion";
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*     */     try
/*     */     {
/* 703 */       if (logOut != null)
/* 704 */         logOut.close();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 708 */       logOut = null;
/* 709 */       log("Error closing invasion log file");
/*     */     }
/*     */     finally
/*     */     {
/* 713 */       super.finalize();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isInvasionActive()
/*     */   {
/* 719 */     return isInvasionActive;
/*     */   }
/*     */ 
/*     */   public static boolean tryGetInvasionPermission(TileEntityNexus nexus)
/*     */   {
/* 724 */     if (nexus == activeNexus)
/*     */     {
/* 726 */       return true;
/*     */     }
/* 728 */     if (nexus == null)
/*     */     {
/* 730 */       String s = "Nexus entity invalid";
/* 731 */       log(s);
/*     */     }
/*     */     else
/*     */     {
/* 735 */       activeNexus = nexus;
/* 736 */       isInvasionActive = true;
/* 737 */       return true;
/*     */     }
/* 739 */     return false;
/*     */   }
/*     */ 
/*     */   public static void setInvasionEnded(TileEntityNexus nexus)
/*     */   {
/* 744 */     if (activeNexus == nexus)
/*     */     {
/* 746 */       isInvasionActive = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setNexusUnloaded(TileEntityNexus nexus)
/*     */   {
/* 752 */     if (activeNexus == nexus)
/*     */     {
/* 754 */       nexus = null;
/* 755 */       isInvasionActive = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setNexusClicked(TileEntityNexus nexus)
/*     */   {
/* 761 */     focusNexus = nexus;
/*     */   }
/*     */ 
/*     */   public static TileEntityNexus getActiveNexus()
/*     */   {
/* 766 */     return activeNexus;
/*     */   }
/*     */ 
/*     */   public static TileEntityNexus getFocusNexus()
/*     */   {
/* 771 */     return focusNexus;
/*     */   }
/*     */ 
/*     */   public static nm[] getNightMobSpawns1(ColorizerGrass world)
/*     */   {
/* 776 */     ISelect mobPool = getMobSpawnPool();
/* 777 */     int numberOfMobs = world.s.nextInt(nightMobMaxGroupSize) + 1;
/* 778 */     nm[] entities = new nm[numberOfMobs];
/* 779 */     for (int i = 0; i < numberOfMobs; i++)
/*     */     {
/* 781 */       EntityIMLiving mob = getMobBuilder().createMobFromConstruct(((IEntityIMPattern)mobPool.selectNext()).generateEntityConstruct(), world, null);
/* 782 */       mob.setEntityIndependent();
/* 783 */       mob.setAggroRange(getNightMobSightRange());
/* 784 */       mob.setSenseRange(getNightMobSenseRange());
/* 785 */       mob.setBurnsInDay(getNightMobsBurnInDay());
/* 786 */       entities[i] = mob;
/*     */     }
/* 788 */     return entities;
/*     */   }
/*     */ 
/*     */   public static MobBuilder getMobBuilder()
/*     */   {
/* 793 */     return defaultMobBuilder;
/*     */   }
/*     */ 
/*     */   public static ISelect<IEntityIMPattern> getMobSpawnPool()
/*     */   {
/* 798 */     return nightSpawnPool1;
/*     */   }
/*     */ 
/*     */   public static int getMinContinuousModeDays()
/*     */   {
/* 803 */     return minContinuousModeDays;
/*     */   }
/*     */ 
/*     */   public static int getMaxContinuousModeDays()
/*     */   {
/* 808 */     return maxContinuousModeDays;
/*     */   }
/*     */ 
/*     */   public static int getNightMobSightRange()
/*     */   {
/* 813 */     return nightMobSightRange;
/*     */   }
/*     */ 
/*     */   public static int getNightMobSenseRange()
/*     */   {
/* 818 */     return nightMobSenseRange;
/*     */   }
/*     */ 
/*     */   public static boolean getNightMobsBurnInDay()
/*     */   {
/* 823 */     return nightMobsBurnInDay;
/*     */   }
/*     */ 
/*     */   public static EnumToolMaterial getRenderHammerItem()
/*     */   {
/* 828 */     return new EnumToolMaterial(itemEngyHammer, 1);
/*     */   }
/*     */ 
/*     */   public static int getGuiIdNexus()
/*     */   {
/* 833 */     return guiIdNexus;
/*     */   }
/*     */ 
/*     */   public static mod_Invasion getLoadedInstance()
/*     */   {
/* 841 */     return instance;
/*     */   }
/*     */ 
/*     */   public static void sendInvasionPacketToAll(byte[] data)
/*     */   {
/* 849 */     Packet103SetSlot packet = new Packet103SetSlot();
/* 850 */     packet.windowId = "data";
/* 851 */     packet.myItemStack = data;
/* 852 */     packet.itemSlot = packet.myItemStack.length;
/* 853 */     FMLCommonHandler.instance().getMinecraftServerInstance().af().a(packet);
/*     */   }
/*     */ 
/*     */   public static void broadcastToAll(String message)
/*     */   {
/* 863 */     FMLCommonHandler.instance().getMinecraftServerInstance().af().a(cu.e(message));
/*     */   }
/*     */ 
/*     */   public static void sendMessageToPlayer(String user, String message)
/*     */   {
/* 869 */     ServerBlockEventList player = FMLCommonHandler.instance().getMinecraftServerInstance().af().f(user);
/* 870 */     if (player != null)
/*     */     {
/* 872 */       player.a(cu.e(message));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void playGlobalSFX(String s)
/*     */   {
/* 881 */     soundHandler.playGlobalSFX(s);
/*     */   }
/*     */ 
/*     */   public static void playSingleSFX(String s)
/*     */   {
/* 889 */     soundHandler.playSingleSFX(s);
/*     */   }
/*     */ 
/*     */   public static void playSingleSFX(byte id)
/*     */   {
/* 897 */     soundHandler.playSingleSFX(id);
/*     */   }
/*     */ 
/*     */   public static void log(String s)
/*     */   {
/* 902 */     if (s == null) {
/* 903 */       return;
/*     */     }
/*     */     try
/*     */     {
/* 907 */       if (logOut != null)
/*     */       {
/* 909 */         logOut.write(s);
/* 910 */         logOut.newLine();
/* 911 */         logOut.flush();
/*     */       }
/*     */       else
/*     */       {
/* 915 */         System.out.println(s);
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 920 */       System.out.println("Couldn't write to invasion log file");
/* 921 */       System.out.println(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isDebug()
/*     */   {
/* 927 */     return debugMode;
/*     */   }
/*     */ 
/*     */   public static BowHackHandler getBowHackHandler()
/*     */   {
/* 932 */     return bowHandler;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.mod_Invasion
 * JD-Core Version:    0.6.2
 */