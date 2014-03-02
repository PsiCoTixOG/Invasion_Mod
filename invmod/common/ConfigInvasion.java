package invmod.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigInvasion extends Config
{
  public void saveConfig(File saveFile, HashMap<Integer, Float> strengthOverrides, boolean debug)
  {
    try
    {
      BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
      try
      {
        writer.write("# Invasion Mod config");
        writer.newLine();
        writer.write("# Delete this file to restore defaults");
        writer.newLine();
        writer.newLine();
        writer.write("# General settings and IDs");
        writer.newLine();

        if (debug)
        {
          writeProperty(writer, "debug");
          writeProperty(writer, "itemID-DebugWand");
        }

        writeProperty(writer, "sounds-enabled");
        writeProperty(writer, "blockID-Nexus");
        writeProperty(writer, "guiID-Nexus");
        writeProperty(writer, "craft-items-enabled");
        writeProperty(writer, "itemID-PhaseCrystal");
        writeProperty(writer, "itemID-RiftFlux");
        writeProperty(writer, "itemID-Remnants");
        writeProperty(writer, "itemID-NexusCatalyst");
        writeProperty(writer, "itemID-InfusedSword");
        writeProperty(writer, "itemID-IMTrap");
        writeProperty(writer, "itemID-IMBow");
        writeProperty(writer, "itemID-CataMixture");
        writeProperty(writer, "itemID-StableCataMixture");
        writeProperty(writer, "itemID-StableNexusCatalyst");
        writeProperty(writer, "itemID-DampingAgent");
        writeProperty(writer, "itemID-StrongDampingAgent");
        writeProperty(writer, "itemID-StrangeBone");
        writeProperty(writer, "itemID-Probe");
        writeProperty(writer, "itemID-StrongCatalyst");
        writeProperty(writer, "itemID-EngyHammer");
        writer.newLine();

        writer.write("# Nexus Continuous Mode");
        writer.newLine();
        writeProperty(writer, "min-days-to-attack");
        writeProperty(writer, "max-days-to-attack");
        writer.newLine();

        writer.write("# Block strengths");
        writer.newLine();
        writer.write("# Add entries here for other mods' blocks");
        writer.newLine();
        writer.write("# Reference values: dirt=3.125, gravel=2.5, obsidian=7.7, stone=5.5 (plus up to 50% from special)");
        writer.newLine();
        writer.write("# Format:  block<id>-strength=<strength>");
        writer.newLine();
        if (strengthOverrides.size() == 0)
        {
          writer.write("# First example, reinforced stone from IC2 (remove comment symbol '#')");
          writer.newLine();
          writer.write("# block231-strength=10.5");
          writer.newLine();
        }
        else
        {
					for (Map.Entry entry : strengthOverrides.entrySet())
          {
            writer.write("block" + entry.getKey() + "-strength=" + entry.getValue());
            writer.newLine();
          }
        }
        writer.newLine();

        writer.write("# Nighttime mob spawning behaviour (does not affect the nexus)");
        writer.newLine();
        writer.write("# mob-limit-override: The maximum number of randomly spawned mobs that may exist in the world. This applies to ALL of minecraft (default: 70)");
        writer.newLine();
        writer.write("# night-spawns-enabled: Currently does not remove any default mobs, only adds new spawns");
        writer.newLine();
        writer.write("# night-mob-spawn-chance: Higher number means mobs are more common");
        writer.newLine();
        writer.write("# night-mob-group-size: The maximum number of mobs that may spawn together");
        writer.newLine();
        writer.write("# night-mob-sense-range: How far mobs can sense a player's presence");
        writer.newLine();
        writeProperty(writer, "mob-limit-override");
        writeProperty(writer, "night-spawns-enabled");
        writeProperty(writer, "night-mob-spawn-chance");
        writeProperty(writer, "night-mob-max-group-size");
        writeProperty(writer, "night-mob-sight-range");
        writeProperty(writer, "night-mob-sense-range");
        writer.newLine();
        writer.write("# Nightime mob spawning tables (also does not affect the nexus)");
        writer.newLine();
        writer.write("# A spawnpool contains things that can possibly spawn, depending on a random chance or something else.");
        writer.newLine();
        writer.write("# Example: First entry is Zombie and 3.0 probability weight. Second entry is Spider and 1.0 weight.");
        writer.newLine();
        writer.write("# Zombies would spawn 75% of the time and spiders 25% of the time");
        writer.newLine();
        for (int i = 0; i < mod_Invasion.DEFAULT_NIGHT_MOB_PATTERN_1_SLOTS.length; i++)
        {
          writeProperty(writer, "nm-spawnpool1-slot" + (1 + i));
          writeProperty(writer, "nm-spawnpool1-slot" + (1 + i) + "-weight");
        }

        writer.newLine();
        writer.write("# Available mob patterns");
        writer.newLine();
        writer.write("# zombie_t1_any     25% chance of holding something");
        writer.newLine();
        writer.write("# zombie_t2_any_basic      30% chance of holding something, 12% chance of tar zombie");
        writer.newLine();
        writer.write("# zombie_t2_plain     no tar zombies");
        writer.newLine();
        writer.write("# zombie_t2_tar");
        writer.newLine();
        writer.write("# zombie_t2_pigman");
        writer.newLine();
        writer.write("# spider_t1_any    basic spider");
        writer.newLine();
        writer.write("# spider_t2_any    blue jumping spider");
        writer.newLine();
        writer.write("# pigengy_t1_any");
        writer.newLine();
        writer.write("# skeleton_t1_any    just a skeleton");
        writer.newLine();
        writer.write("# thrower");

        writer.flush();
      }
      finally
      {
        writer.close();
      }
    }
    catch (IOException e)
    {
      mod_Invasion.log(e.getMessage());
    }
  }
}