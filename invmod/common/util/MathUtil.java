/*     */ package invmod.common.util;
/*     */ 
/*     */ public class MathUtil
/*     */ {
/*     */   public static boolean floatEquals(float f1, float f2, float tolerance)
/*     */   {
/*   7 */     float diff = f1 - f2;
/*   8 */     if (diff >= 0.0F) {
/*   9 */       return diff < tolerance;
/*     */     }
/*  11 */     return -diff < tolerance;
/*     */   }
/*     */ 
/*     */   public static double boundAnglePiRad(double angle)
/*     */   {
/*  19 */     angle %= 6.283185307179586D;
/*  20 */     if (angle >= 3.141592653589793D)
/*  21 */       angle -= 6.283185307179586D;
/*  22 */     else if (angle < -3.141592653589793D) {
/*  23 */       angle += 6.283185307179586D;
/*     */     }
/*  25 */     return angle;
/*     */   }
/*     */ 
/*     */   public static double boundAngle180Deg(double angle)
/*     */   {
/*  33 */     angle %= 360.0D;
/*  34 */     if (angle >= 180.0D)
/*  35 */       angle -= 360.0D;
/*  36 */     else if (angle < -180.0D) {
/*  37 */       angle += 360.0D;
/*     */     }
/*  39 */     return angle;
/*     */   }
/*     */ 
/*     */   public static float interpRotationRad(float rot1, float rot2, float t)
/*     */   {
/*  55 */     return interpWrapped(rot1, rot2, t, -3.141593F, 3.141593F);
/*     */   }
/*     */ 
/*     */   public static float interpRotationDeg(float rot1, float rot2, float t)
/*     */   {
/*  71 */     return interpWrapped(rot1, rot2, t, -180.0F, 180.0F);
/*     */   }
/*     */ 
/*     */   public static float interpWrapped(float val1, float val2, float t, float min, float max)
/*     */   {
/*  90 */     float dVal = val2 - val1;
/*  91 */     while (dVal < min)
/*     */     {
/*  93 */       dVal += max - min;
/*     */     }
/*  95 */     while (dVal >= max)
/*     */     {
/*  97 */       dVal -= max - min;
/*     */     }
/*  99 */     return val1 + t * dVal;
/*     */   }
/*     */ 
/*     */   public static float unpackFloat(int i)
/*     */   {
/* 104 */     return Float.intBitsToFloat(i);
/*     */   }
/*     */ 
/*     */   public static int packFloat(float f)
/*     */   {
/* 109 */     return Float.floatToIntBits(f);
/*     */   }
/*     */ 
/*     */   public static int packAnglesDeg(float a1, float a2, float a3, float a4)
/*     */   {
/* 114 */     return packBytes((byte)(int)(a1 / 360.0F * 256.0F), (byte)(int)(a2 / 360.0F * 256.0F), (byte)(int)(a3 / 360.0F * 256.0F), (byte)(int)(a4 / 360.0F * 256.0F));
/*     */   }
/*     */ 
/*     */   public static float unpackAnglesDeg_1(int i)
/*     */   {
/* 119 */     return unpackBytes_1(i) * 360.0F / 256.0F;
/*     */   }
/*     */ 
/*     */   public static float unpackAnglesDeg_2(int i)
/*     */   {
/* 124 */     return unpackBytes_2(i) * 360.0F / 256.0F;
/*     */   }
/*     */ 
/*     */   public static float unpackAnglesDeg_3(int i)
/*     */   {
/* 129 */     return unpackBytes_3(i) * 360.0F / 256.0F;
/*     */   }
/*     */ 
/*     */   public static float unpackAnglesDeg_4(int i)
/*     */   {
/* 134 */     return unpackBytes_4(i) * 360.0F / 256.0F;
/*     */   }
/*     */ 
/*     */   public static int packBytes(int i1, int i2, int i3, int i4)
/*     */   {
/* 139 */     return i1 << 24 & 0xFF000000 | i2 << 16 & 0xFF0000 | i3 << 8 & 0xFF00 | i4 & 0xFF;
/*     */   }
/*     */ 
/*     */   public static byte unpackBytes_1(int i)
/*     */   {
/* 144 */     return (byte)(i >>> 24);
/*     */   }
/*     */ 
/*     */   public static byte unpackBytes_2(int i)
/*     */   {
/* 149 */     return (byte)(i >>> 16 & 0xFF);
/*     */   }
/*     */ 
/*     */   public static byte unpackBytes_3(int i)
/*     */   {
/* 154 */     return (byte)(i >>> 8 & 0xFF);
/*     */   }
/*     */ 
/*     */   public static byte unpackBytes_4(int i)
/*     */   {
/* 159 */     return (byte)(i & 0xFF);
/*     */   }
/*     */ 
/*     */   public static int packShorts(int i1, int i2)
/*     */   {
/* 164 */     return i1 << 16 | i2 & 0xFFFF;
/*     */   }
/*     */ 
/*     */   public static short unhopackSrts_1(int i)
/*     */   {
/* 169 */     return (short)(i >>> 16);
/*     */   }
/*     */ 
/*     */   public static int unpackShorts_2(int i)
/*     */   {
/* 174 */     return (short)(i & 0xFFFF);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.util.MathUtil
 * JD-Core Version:    0.6.2
 */