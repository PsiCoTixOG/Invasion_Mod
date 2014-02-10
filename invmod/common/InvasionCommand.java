/*     */ package invmod.common;
/*     */ 
/*     */ import invmod.common.nexus.TileEntityNexus;
/*     */ import net.minecraft.command.CommandBase;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.src.cu;
/*     */ 
/*     */ public class InvasionCommand extends CommandBase
/*     */ {
/*     */   public void processCommand(ICommandSender sender, String[] args)
/*     */   {
/*  13 */     String username = sender.getCommandSenderName();
/*  14 */     if ((args.length > 0) && (args.length <= 7))
/*     */     {
/*  16 */       if (args[0].equals("begin"))
/*     */       {
/*  18 */         if (args.length == 2)
/*     */         {
/*  20 */           int startWave = Integer.parseInt(args[1]);
/*  21 */           if (mod_Invasion.getFocusNexus() != null)
/*     */           {
/*  23 */             mod_Invasion.getFocusNexus().debugStartInvaion(startWave);
/*     */           }
/*     */         }
/*     */       }
/*  27 */       else if (args[0].equals("end"))
/*     */       {
/*  30 */         if (mod_Invasion.getActiveNexus() != null)
/*     */         {
/*  32 */           mod_Invasion.getActiveNexus().emergencyStop();
/*  33 */           mod_Invasion.broadcastToAll(username + " ended invasion");
/*     */         }
/*     */         else
/*     */         {
/*  37 */           sender.a(cu.e(username + ": No invasion to end"));
/*     */         }
/*     */       }
/*  40 */       else if (args[0].equals("range"))
/*     */       {
/*  42 */         if (args.length == 2)
/*     */         {
/*  44 */           int radius = Integer.parseInt(args[1]);
/*  45 */           if (mod_Invasion.getFocusNexus() != null)
/*     */           {
/*  47 */             if ((radius >= 32) && (radius <= 128))
/*     */             {
/*  49 */               if (mod_Invasion.getFocusNexus().setSpawnRadius(radius))
/*     */               {
/*  51 */                 sender.a(cu.e("Set nexus range to " + radius));
/*     */               }
/*     */               else
/*     */               {
/*  60 */                 sender.a(cu.e(username + ": Can't change range while nexus is active"));
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/*  65 */               sender.a(cu.e(username + ": Range must be between 32 and 128"));
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/*  70 */             sender.a(cu.e(username + ": Right-click the nexus first to set target for command"));
/*     */           }
/*     */         }
/*     */       }
/*  74 */       else if (args[0].equals("spawnertest"))
/*     */       {
/*  76 */         int startWave = 1;
/*  77 */         int endWave = 11;
/*     */ 
/*  79 */         if (args.length >= 4)
/*  80 */           return;
/*  81 */         if (args.length >= 3)
/*  82 */           endWave = Integer.parseInt(args[2]);
/*  83 */         if (args.length >= 2) {
/*  84 */           startWave = Integer.parseInt(args[1]);
/*     */         }
/*  86 */         Tester tester = new Tester();
/*  87 */         tester.doWaveSpawnerTest(startWave, endWave);
/*     */       }
/*  89 */       else if (args[0].equals("pointcontainertest"))
/*     */       {
/*  91 */         Tester tester = new Tester();
/*  92 */         tester.doSpawnPointSelectionTest();
/*     */       }
/*  94 */       else if (args[0].equals("wavebuildertest"))
/*     */       {
/*  96 */         float difficulty = 1.0F;
/*  97 */         float tierLevel = 1.0F;
/*  98 */         int lengthSeconds = 160;
/*     */ 
/* 100 */         if (args.length >= 5)
/* 101 */           return;
/* 102 */         if (args.length >= 4)
/* 103 */           lengthSeconds = Integer.parseInt(args[3]);
/* 104 */         if (args.length >= 3)
/* 105 */           tierLevel = Float.parseFloat(args[2]);
/* 106 */         if (args.length >= 2) {
/* 107 */           difficulty = Float.parseFloat(args[1]);
/*     */         }
/* 109 */         Tester tester = new Tester();
/* 110 */         tester.doWaveBuilderTest(difficulty, tierLevel, lengthSeconds);
/*     */       }
/* 112 */       else if (args[0].equals("nexusstatus"))
/*     */       {
/* 114 */         if (mod_Invasion.getFocusNexus() != null)
/* 115 */           mod_Invasion.getFocusNexus().debugStatus();
/*     */       }
/* 117 */       else if (args[0].equals("bolt"))
/*     */       {
/* 119 */         if (mod_Invasion.getFocusNexus() != null)
/*     */         {
/* 121 */           int x = mod_Invasion.getFocusNexus().getXCoord();
/* 122 */           int y = mod_Invasion.getFocusNexus().getYCoord();
/* 123 */           int z = mod_Invasion.getFocusNexus().getZCoord();
/* 124 */           int time = 40;
/* 125 */           if (args.length >= 6)
/* 126 */             return;
/* 127 */           if (args.length >= 5)
/* 128 */             time = Integer.parseInt(args[4]);
/* 129 */           if (args.length >= 4)
/* 130 */             z += Integer.parseInt(args[3]);
/* 131 */           if (args.length >= 3)
/* 132 */             y += Integer.parseInt(args[2]);
/* 133 */           if (args.length >= 2) {
/* 134 */             x += Integer.parseInt(args[1]);
/*     */           }
/* 136 */           mod_Invasion.getFocusNexus().createBolt(x, y, z, time);
/*     */         }
/*     */       }
/*     */       else;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getCommandName()
/*     */   {
/* 150 */     return "invasion";
/*     */   }
/*     */ 
/*     */   public String getCommandUsage(ICommandSender icommandsender)
/*     */   {
/* 156 */     return "";
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.InvasionCommand
 * JD-Core Version:    0.6.2
 */