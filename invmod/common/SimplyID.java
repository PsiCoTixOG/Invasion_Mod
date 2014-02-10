/*     */ package invmod.common;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.src.nm;
/*     */ import net.minecraft.src.ns;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.storage.CallableLevelStorageVersion;
/*     */ 
/*     */ public class SimplyID
/*     */ {
/*     */   private static int nextSimplyID;
/*  21 */   private static Set<String> loadedIDs = new HashSet();
/*  22 */   private static String loadedWorld = null;
/*  23 */   private static File file = null;
/*  24 */   private static PrintWriter writer = null;
/*     */ 
/*     */   public static String getNextSimplyID(nm par1Entity) {
/*  27 */     loadSession(par1Entity.q);
/*     */ 
/*  29 */     nextSimplyID = 0;
/*     */ 
/*  31 */     int i = nextSimplyID;
/*     */     while (true) {
/*  33 */       String id = ns.b(par1Entity) + nextSimplyID++;
/*  34 */       if (loadedIDs.add(id)) {
/*  35 */         writeIDToFile(id);
/*  36 */         return id;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void loadSession(ColorizerGrass worldObj) {
/*  42 */     if ((loadedWorld == null) || (!worldObj.M().g().equals(loadedWorld)))
/*  43 */       resetSimplyIDTo(worldObj);
/*     */   }
/*     */ 
/*     */   public static void resetSimplyIDTo(ColorizerGrass world)
/*     */   {
/*  49 */     if (writer != null) {
/*  50 */       writer.flush();
/*  51 */       writer.close();
/*     */     }
/*  53 */     loadedIDs.clear();
/*     */ 
/*  56 */     loadedWorld = world.M().g();
/*  57 */     String directory = "saves/" + loadedWorld + "/";
/*  58 */     file = new File(directory + "savedIDs.txt");
/*     */     try
/*     */     {
/*  61 */       writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true))); } catch (FileNotFoundException e) {
/*     */     } catch (IOException e) {
/*  63 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  66 */     populateSet();
/*     */   }
/*     */ 
/*     */   public static void writeIDToFile(String id) {
/*  70 */     writer.println(id);
/*  71 */     writer.flush();
/*     */   }
/*     */ 
/*     */   public static void populateSet() {
/*  75 */     FileReader pre = null;
/*  76 */     BufferedReader reader = null;
/*     */     try {
/*  78 */       pre = new FileReader(file);
/*  79 */       reader = new BufferedReader(pre);
/*     */ 
/*  81 */       String line = null;
/*     */       try
/*     */       {
/*  84 */         while ((line = reader.readLine()) != null)
/*  85 */           if (line.startsWith("delete ")) {
/*  86 */             deleteID(line, Boolean.valueOf(false));
/*     */           }
/*     */           else
/*  89 */             addID(line);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  93 */         e.printStackTrace();
/*     */       }
/*     */       catch (Exception e) {
/*  96 */         e.printStackTrace();
/*     */       }
/*     */       try
/*     */       {
/* 100 */         if (reader != null)
/* 101 */           reader.close();
/*     */       }
/*     */       catch (IOException e) {
/* 104 */         e.printStackTrace();
/*     */       }
/*     */     } catch (FileNotFoundException e) {
/*     */     }
/* 108 */     if (reader != null) {
/*     */       try {
/* 110 */         reader.close();
/*     */       } catch (IOException e) {
/* 112 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 116 */     refreshLoadedIDFile();
/*     */   }
/*     */ 
/*     */   private static void refreshLoadedIDFile() {
/*     */     try {
/* 121 */       PrintWriter writer = new PrintWriter(file);
/*     */ 
/* 123 */       for (String id : loadedIDs) {
/* 124 */         writer.println(id);
/*     */       }
/*     */ 
/* 127 */       writer.flush();
/* 128 */       writer.close();
/*     */     } catch (FileNotFoundException e) {
/* 130 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Set<String> getLoadedIDs()
/*     */   {
/* 152 */     return loadedIDs;
/*     */   }
/*     */ 
/*     */   public static void setLoadedIDs(Set<String> loadedIDs) {
/* 156 */     loadedIDs = loadedIDs;
/*     */   }
/*     */ 
/*     */   public static void addID(String newID) {
/* 160 */     loadedIDs.add(newID);
/*     */   }
/*     */ 
/*     */   public static void deleteID(String deletedID, Boolean flag)
/*     */   {
/* 165 */     if ((!flag.booleanValue()) && (deletedID.startsWith("delete "))) {
/* 166 */       deletedID = deletedID.split(" ")[1];
/*     */     }
/*     */ 
/* 169 */     if (flag.booleanValue()) {
/* 170 */       writeIDToFile("delete " + deletedID);
/*     */     }
/*     */ 
/* 173 */     loadedIDs.remove(deletedID);
/*     */   }
/*     */ 
/*     */   public static void deleteID(ColorizerGrass world, String string)
/*     */   {
/* 178 */     loadSession(world);
/*     */ 
/* 180 */     deleteID(string, Boolean.valueOf(true));
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.SimplyID
 * JD-Core Version:    0.6.2
 */