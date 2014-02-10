/*     */ package invmod.common;
/*     */ 
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class Config
/*     */ {
/*     */   protected Properties properties;
/*     */ 
/*     */   public void loadConfig(File configFile)
/*     */   {
/*  16 */     mod_Invasion.log("Loading config");
/*  17 */     this.properties = new Properties();
/*     */     try
/*     */     {
/*  21 */       if (!configFile.exists())
/*     */       {
/*  23 */         mod_Invasion.log("Config not found. Creating file 'invasion_config.txt' in minecraft directory");
/*  24 */         if (!configFile.createNewFile())
/*  25 */           mod_Invasion.log("Unable to create new config file.");
/*     */       }
/*     */       else
/*     */       {
/*  29 */         FileReader configRead = new FileReader(configFile);
/*     */         try
/*     */         {
/*  32 */           this.properties.load(configRead);
/*     */         }
/*     */         finally
/*     */         {
/*  36 */           configRead.close();
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (FileNotFoundException e)
/*     */     {
/*  43 */       mod_Invasion.log(e.getMessage());
/*  44 */       mod_Invasion.log("Proceeding with default config");
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  48 */       mod_Invasion.log(e.getMessage());
/*  49 */       mod_Invasion.log("Proceeding with default config");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeProperty(BufferedWriter writer, String key) throws IOException
/*     */   {
/*  55 */     writeProperty(writer, key, null);
/*     */   }
/*     */ 
/*     */   public void writeProperty(BufferedWriter writer, String key, String comment) throws IOException
/*     */   {
/*  60 */     if (comment != null)
/*     */     {
/*  62 */       writer.write("# " + comment);
/*  63 */       writer.newLine();
/*     */     }
/*     */ 
/*  66 */     writer.write(key + "=" + this.properties.getProperty(key));
/*  67 */     writer.newLine();
/*     */   }
/*     */ 
/*     */   public void setProperty(String key, String value)
/*     */   {
/*  72 */     this.properties.setProperty(key, value);
/*     */   }
/*     */ 
/*     */   public String getProperty(String key, String defaultValue)
/*     */   {
/*  77 */     return this.properties.getProperty(key, defaultValue);
/*     */   }
/*     */ 
/*     */   public int getPropertyValueInt(String keyName, int defaultValue)
/*     */   {
/*  82 */     String property = this.properties.getProperty(keyName, "null");
/*  83 */     if (!property.equals("null"))
/*     */     {
/*  85 */       return Integer.parseInt(property);
/*     */     }
/*     */ 
/*  89 */     this.properties.setProperty(keyName, Integer.toString(defaultValue));
/*  90 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public float getPropertyValueFloat(String keyName, float defaultValue)
/*     */   {
/*  96 */     String property = this.properties.getProperty(keyName, "null");
/*  97 */     if (!property.equals("null"))
/*     */     {
/*  99 */       return Float.parseFloat(property);
/*     */     }
/*     */ 
/* 103 */     this.properties.setProperty(keyName, Float.toString(defaultValue));
/* 104 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public boolean getPropertyValueBoolean(String keyName, boolean defaultValue)
/*     */   {
/* 110 */     String property = this.properties.getProperty(keyName, "null");
/* 111 */     if (!property.equals("null"))
/*     */     {
/* 113 */       return Boolean.parseBoolean(property);
/*     */     }
/*     */ 
/* 117 */     this.properties.setProperty(keyName, Boolean.toString(defaultValue));
/* 118 */     return defaultValue;
/*     */   }
/*     */ 
/*     */   public String getPropertyValueString(String keyName, String defaultValue)
/*     */   {
/* 124 */     String property = this.properties.getProperty(keyName, "null");
/* 125 */     if (!property.equals("null"))
/*     */     {
/* 127 */       return property;
/*     */     }
/*     */ 
/* 131 */     this.properties.setProperty(keyName, defaultValue);
/* 132 */     return defaultValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.Config
 * JD-Core Version:    0.6.2
 */