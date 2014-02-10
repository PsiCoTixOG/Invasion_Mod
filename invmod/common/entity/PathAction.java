/*   */ package invmod.common.entity;
/*   */ 
/*   */ public enum PathAction
/*   */ {
/* 5 */   NONE, LADDER_UP, BRIDGE, SWIM, DIG, LADDER_UP_PX, LADDER_UP_NX, LADDER_UP_PZ, LADDER_UP_NZ, LADDER_TOWER_UP_PX, 
/* 6 */   LADDER_TOWER_UP_NX, LADDER_TOWER_UP_PZ, LADDER_TOWER_UP_NZ, SCAFFOLD_UP;
/*   */ 
/* 8 */   public static final PathAction[] ladderTowerIndexOrient = { LADDER_TOWER_UP_PX, LADDER_TOWER_UP_NX, LADDER_TOWER_UP_PZ, LADDER_TOWER_UP_NZ };
/* 9 */   public static final PathAction[] ladderIndexOrient = { LADDER_UP_PX, LADDER_UP_NX, LADDER_UP_PZ, LADDER_UP_NZ };
/*   */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.PathAction
 * JD-Core Version:    0.6.2
 */