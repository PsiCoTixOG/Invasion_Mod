/*    */ package invmod.client;
/*    */ 
/*    */ import cpw.mods.fml.common.ITickHandler;
/*    */ import cpw.mods.fml.common.TickType;
/*    */ import invmod.common.mod_Invasion;
/*    */ import java.util.EnumSet;
/*    */ 
/*    */ public class TickHandlerClient
/*    */   implements ITickHandler
/*    */ {
/*    */   public void tickStart(EnumSet<TickType> type, Object[] tickData)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void tickEnd(EnumSet<TickType> type, Object[] tickData)
/*    */   {
/* 21 */     if (type.contains(TickType.CLIENT))
/*    */     {
/* 23 */       clientTick();
/*    */     }
/*    */   }
/*    */ 
/*    */   public EnumSet<TickType> ticks()
/*    */   {
/* 30 */     return EnumSet.of(TickType.CLIENT);
/*    */   }
/*    */ 
/*    */   public String getLabel()
/*    */   {
/* 36 */     return "IM Client Tick";
/*    */   }
/*    */ 
/*    */   protected void clientTick()
/*    */   {
/* 41 */     mod_Invasion.onClientTick();
/* 42 */     mod_Invasion.getBowHackHandler().onUpdate();
/*    */   }
/*    */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.TickHandlerClient
 * JD-Core Version:    0.6.2
 */