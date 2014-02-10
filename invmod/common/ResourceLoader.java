/*    */ package invmod.common;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.channels.Channels;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ 
/*    */ public class ResourceLoader
/*    */ {
/*    */   public boolean copyResource(ClassLoader classLoader, String resourcePath, File destFile)
/*    */   {
/* 15 */     InputStream stream = null;
/* 16 */     FileChannel destChannel = null;
/* 17 */     ReadableByteChannel sourceChannel = null;
/*    */     try
/*    */     {
/*    */       boolean bool1;
/* 20 */       if (!destFile.exists())
/*    */       {
/* 22 */         mod_Invasion.log("Starting copying of sound file");
/* 23 */         if (!destFile.createNewFile())
/*    */         {
/* 25 */           mod_Invasion.log("Unable to create new sound file.");
/* 26 */           return false;
/*    */         }
/*    */       }
/*    */ 
/* 30 */       destChannel = new FileOutputStream(destFile).getChannel();
/* 31 */       stream = classLoader.getResourceAsStream(resourcePath);
/* 32 */       if (stream == null)
/*    */       {
/* 34 */         mod_Invasion.log("Failed to find resource: " + resourcePath);
/* 35 */         return false;
/*    */       }
/* 37 */       sourceChannel = Channels.newChannel(stream);
/* 38 */       long written = 0L;
/* 39 */       int position = 0;
/*    */       do
/*    */       {
/* 42 */         written = destChannel.transferFrom(sourceChannel, position, 1024L);
/* 43 */         position = (int)(position + written);
/*    */       }
/* 45 */       while (written > 0L);
/* 46 */       return true;
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 50 */       mod_Invasion.log("Problem creating file channels");
/* 51 */       mod_Invasion.log(e.getMessage());
/* 52 */       return 0;
/*    */     }
/*    */     finally
/*    */     {
/*    */       try
/*    */       {
/* 58 */         if (sourceChannel != null)
/* 59 */           sourceChannel.close();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 63 */         mod_Invasion.log("Problem closing source file channel");
/* 64 */         mod_Invasion.log(e.getMessage());
/*    */       }
/*    */ 
/*    */       try
/*    */       {
/* 69 */         if (destChannel != null)
/* 70 */           destChannel.close();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 74 */         mod_Invasion.log("Problem closing destination file channel");
/* 75 */         mod_Invasion.log(e.getMessage());
/*    */       }
/*    */ 
/*    */       try
/*    */       {
/* 80 */         if (stream != null)
/* 81 */           stream.close();
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 85 */         mod_Invasion.log("Problem closing input stream");
/* 86 */         mod_Invasion.log(e.getMessage());
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.ResourceLoader
 * JD-Core Version:    0.6.2
 */