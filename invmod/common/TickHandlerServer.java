/*    */ package invmod.common;
/*    */ 
/*    */ import cpw.mods.fml.common.ITickHandler;
/*    */ import cpw.mods.fml.common.TickType;
/*    */ import java.util.EnumSet;
/*    */ 
/*    */ public class TickHandlerServer
/*    */   implements ITickHandler
/*    */ {
/*    */   private int elapsed;
/*    */   private long timer;
/*    */ 
/*    */   public void tickStart(EnumSet<TickType> type, Object[] tickData)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void tickEnd(EnumSet<TickType> type, Object[] tickData)
/*    */   {
/* 21 */     if (type.contains(TickType.SERVER))
/*    */     {
/* 23 */       serverTick();
/*    */     }
/*    */   }
/*    */ 
/*    */   public EnumSet<TickType> ticks()
/*    */   {
/* 30 */     return EnumSet.of(TickType.SERVER);
/*    */   }
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 36 */     return "IM Server Tick";
/*    */   }
/*    */ 
/*    */   protected void serverTick()
/*    */   {
/* 41 */     mod_Invasion.onServerTick();
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.TickHandlerServer
 * JD-Core Version:    0.6.2
 */