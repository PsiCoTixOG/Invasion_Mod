/*    */ package invmod.common;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
import java.util.Map;
/*    */ 
/*    */ public class ConfigInvasion extends Config
/*    */ {
/*    */   public void saveConfig(File saveFile, HashMap<Integer, Float> strengthOverrides, boolean debug)
/*    */   {
/*    */     try
/*    */     {
/* 18 */       BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
/*    */       try
/*    */       {
/* 21 */         writer.write("# Invasion Mod config");
/* 22 */         writer.newLine();
/* 23 */         writer.write("# Delete this file to restore defaults");
/* 24 */         writer.newLine();
/* 25 */         writer.newLine();
/* 26 */         writer.write("# General settings and IDs");
/* 27 */         writer.newLine();
/*    */ 
/* 29 */         if (debug)
/*    */         {
/* 31 */           writeProperty(writer, "debug");
/* 32 */           writeProperty(writer, "itemID-DebugWand");
/*    */         }
/*    */ 
/* 35 */         writeProperty(writer, "sounds-enabled");
/* 36 */         writeProperty(writer, "blockID-Nexus");
/* 37 */         writeProperty(writer, "guiID-Nexus");
/* 38 */         writeProperty(writer, "craft-items-enabled");
/* 39 */         writeProperty(writer, "itemID-PhaseCrystal");
/* 40 */         writeProperty(writer, "itemID-RiftFlux");
/* 41 */         writeProperty(writer, "itemID-Remnants");
/* 42 */         writeProperty(writer, "itemID-NexusCatalyst");
/* 43 */         writeProperty(writer, "itemID-InfusedSword");
/* 44 */         writeProperty(writer, "itemID-IMTrap");
/* 45 */         writeProperty(writer, "itemID-IMBow");
/* 46 */         writeProperty(writer, "itemID-CataMixture");
/* 47 */         writeProperty(writer, "itemID-StableCataMixture");
/* 48 */         writeProperty(writer, "itemID-StableNexusCatalyst");
/* 49 */         writeProperty(writer, "itemID-DampingAgent");
/* 50 */         writeProperty(writer, "itemID-StrongDampingAgent");
/* 51 */         writeProperty(writer, "itemID-StrangeBone");
/* 52 */         writeProperty(writer, "itemID-Probe");
/* 53 */         writeProperty(writer, "itemID-StrongCatalyst");
/* 54 */         writeProperty(writer, "itemID-EngyHammer");
/* 55 */         writer.newLine();
/*    */ 
/* 57 */         writer.write("# Nexus Continuous Mode");
/* 58 */         writer.newLine();
/* 59 */         writeProperty(writer, "min-days-to-attack");
/* 60 */         writeProperty(writer, "max-days-to-attack");
/* 61 */         writer.newLine();
/*    */ 
/* 63 */         writer.write("# Block strengths");
/* 64 */         writer.newLine();
/* 65 */         writer.write("# Add entries here for other mods' blocks");
/* 66 */         writer.newLine();
/* 67 */         writer.write("# Reference values: dirt=3.125, gravel=2.5, obsidian=7.7, stone=5.5 (plus up to 50% from special)");
/* 68 */         writer.newLine();
/* 69 */         writer.write("# Format:  block<id>-strength=<strength>");
/* 70 */         writer.newLine();
/* 71 */         if (strengthOverrides.size() == 0)
/*    */         {
/* 73 */           writer.write("# First example, reinforced stone from IC2 (remove comment symbol '#')");
/* 74 */           writer.newLine();
/* 75 */           writer.write("# block231-strength=10.5");
/* 76 */           writer.newLine();
/*    */         }
/*    */         else
/*    */         {
/* 80 */           for (@SuppressWarnings("rawtypes") Map.Entry entry : strengthOverrides.entrySet())
/*    */           {
/* 82 */             writer.write("block" + entry.getKey() + "-strength=" + entry.getValue());
/* 83 */             writer.newLine();
/*    */           }
/*    */         }
/* 86 */         writer.newLine();
/*    */ 
/* 88 */         writer.write("# Nighttime mob spawning behaviour (does not affect the nexus)");
/* 89 */         writer.newLine();
/* 90 */         writer.write("# mob-limit-override: The maximum number of randomly spawned mobs that may exist in the world. This applies to ALL of minecraft (default: 70)");
/* 91 */         writer.newLine();
/* 92 */         writer.write("# night-spawns-enabled: Currently does not remove any default mobs, only adds new spawns");
/* 93 */         writer.newLine();
/* 94 */         writer.write("# night-mob-spawn-chance: Higher number means mobs are more common");
/* 95 */         writer.newLine();
/* 96 */         writer.write("# night-mob-group-size: The maximum number of mobs that may spawn together");
/* 97 */         writer.newLine();
/* 98 */         writer.write("# night-mob-sense-range: How far mobs can sense a player's presence");
/* 99 */         writer.newLine();
/* 100 */         writeProperty(writer, "mob-limit-override");
/* 101 */         writeProperty(writer, "night-spawns-enabled");
/* 102 */         writeProperty(writer, "night-mob-spawn-chance");
/* 103 */         writeProperty(writer, "night-mob-max-group-size");
/* 104 */         writeProperty(writer, "night-mob-sight-range");
/* 105 */         writeProperty(writer, "night-mob-sense-range");
/* 106 */         writer.newLine();
/* 107 */         writer.write("# Nightime mob spawning tables (also does not affect the nexus)");
/* 108 */         writer.newLine();
/* 109 */         writer.write("# A spawnpool contains things that can possibly spawn, depending on a random chance or something else.");
/* 110 */         writer.newLine();
/* 111 */         writer.write("# Example: First entry is Zombie and 3.0 probability weight. Second entry is Spider and 1.0 weight.");
/* 112 */         writer.newLine();
/* 113 */         writer.write("# Zombies would spawn 75% of the time and spiders 25% of the time");
/* 114 */         writer.newLine();
/* 115 */         for (int i = 0; i < mod_Invasion.DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS.length; i++)
/*    */         {
/* 117 */           writeProperty(writer, "nm-spawnpool1-slot" + (1 + i));
/* 118 */           writeProperty(writer, "nm-spawnpool1-slot" + (1 + i) + "-weight");
/*    */         }
/*    */ 
/* 121 */         writer.newLine();
/* 122 */         writer.write("# Available mob patterns");
/* 123 */         writer.newLine();
/* 124 */         writer.write("# zombie_t1_any     25% chance of holding something");
/* 125 */         writer.newLine();
/* 126 */         writer.write("# zombie_t2_any_basic      30% chance of holding something, 12% chance of tar zombie");
/* 127 */         writer.newLine();
/* 128 */         writer.write("# zombie_t2_plain     no tar zombies");
/* 129 */         writer.newLine();
/* 130 */         writer.write("# zombie_t2_tar");
/* 131 */         writer.newLine();
/* 132 */         writer.write("# zombie_t2_pigman");
/* 133 */         writer.newLine();
/* 134 */         writer.write("# spider_t1_any    basic spider");
/* 135 */         writer.newLine();
/* 136 */         writer.write("# spider_t2_any    blue jumping spider");
/* 137 */         writer.newLine();
/* 138 */         writer.write("# pigengy_t1_any");
/* 139 */         writer.newLine();
/* 140 */         writer.write("# skeleton_t1_any    just a skeleton");
/* 141 */         writer.newLine();
/* 142 */         writer.write("# thrower");
/*    */ 
/* 144 */         writer.flush();
/*    */       }
/*    */       finally
/*    */       {
/* 148 */         writer.close();
/*    */       }
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 153 */       mod_Invasion.log(e.getMessage());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.ConfigInvasion
 * JD-Core Version:    0.6.2
 */