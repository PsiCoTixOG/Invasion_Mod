/*     */ package invmod.client.render.animation;
/*     */ 
/*     */ import invmod.common.util.MathUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class KeyFrame
/*     */ {
/*     */   private float time;
/*     */   private float rotX;
/*     */   private float rotY;
/*     */   private float rotZ;
/*     */   private float posX;
/*     */   private float posY;
/*     */   private float posZ;
/*     */   private InterpType interpType;
/*     */   private float[][] mods;
/*     */   private boolean hasPos;
/*     */ 
/*     */   public KeyFrame(float time, float rotX, float rotY, float rotZ, InterpType interpType)
/*     */   {
/*  39 */     this(time, rotX, rotY, rotZ, 0.0F, 0.0F, 0.0F, interpType);
/*  40 */     this.hasPos = false;
/*     */   }
/*     */ 
/*     */   public KeyFrame(float time, float rotX, float rotY, float rotZ, float posX, float posY, float posZ, InterpType interpType)
/*     */   {
/*  45 */     this.time = time;
/*  46 */     this.rotX = rotX;
/*  47 */     this.rotY = rotY;
/*  48 */     this.rotZ = rotZ;
/*  49 */     this.posX = posX;
/*  50 */     this.posY = posY;
/*  51 */     this.posZ = posZ;
/*  52 */     this.interpType = interpType;
/*  53 */     this.hasPos = true;
/*     */   }
/*     */ 
/*     */   public float getTime()
/*     */   {
/*  58 */     return this.time;
/*     */   }
/*     */ 
/*     */   public float getRotX()
/*     */   {
/*  63 */     return this.rotX;
/*     */   }
/*     */ 
/*     */   public float getRotY()
/*     */   {
/*  68 */     return this.rotY;
/*     */   }
/*     */ 
/*     */   public float getRotZ()
/*     */   {
/*  73 */     return this.rotZ;
/*     */   }
/*     */ 
/*     */   public float getPosX()
/*     */   {
/*  78 */     return this.posX;
/*     */   }
/*     */ 
/*     */   public float getPosY()
/*     */   {
/*  83 */     return this.posY;
/*     */   }
/*     */ 
/*     */   public float getPosZ()
/*     */   {
/*  88 */     return this.posZ;
/*     */   }
/*     */ 
/*     */   public InterpType getInterpType()
/*     */   {
/*  93 */     return this.interpType;
/*     */   }
/*     */ 
/*     */   public boolean hasPos()
/*     */   {
/*  98 */     return this.hasPos;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 103 */     return "(" + this.time + ", " + this.rotX + ", " + this.rotY + ", " + this.rotZ + ")";
/*     */   }
/*     */ 
/*     */   public static List<KeyFrame> cloneFrames(List<KeyFrame> keyFrames)
/*     */   {
/* 108 */     return new ArrayList(keyFrames);
/*     */   }
/*     */ 
/*     */   public static void toRadians(List<KeyFrame> keyFrames)
/*     */   {
/* 113 */     ListIterator iter = keyFrames.listIterator();
/* 114 */     while (iter.hasNext())
/*     */     {
/* 116 */       float radDeg = 0.01745329F;
/* 117 */       KeyFrame keyFrame = (KeyFrame)iter.next();
/* 118 */       KeyFrame newFrame = new KeyFrame(keyFrame.getTime(), keyFrame.getRotX() * radDeg, keyFrame.getRotY() * radDeg, keyFrame.getRotZ() * radDeg, keyFrame.getPosX(), keyFrame.getPosY(), keyFrame.getPosZ(), keyFrame.getInterpType());
/*     */ 
/* 120 */       newFrame.hasPos = keyFrame.hasPos;
/* 121 */       iter.set(newFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void mirrorFramesX(List<KeyFrame> keyFrames)
/*     */   {
/* 127 */     ListIterator iter = keyFrames.listIterator();
/* 128 */     while (iter.hasNext())
/*     */     {
/* 130 */       KeyFrame keyFrame = (KeyFrame)iter.next();
/* 131 */       KeyFrame newFrame = new KeyFrame(keyFrame.getTime(), keyFrame.getRotX(), -keyFrame.getRotY(), -keyFrame.getRotZ(), -keyFrame.getPosX(), keyFrame.getPosY(), keyFrame.getPosZ(), keyFrame.getInterpType());
/*     */ 
/* 133 */       newFrame.hasPos = keyFrame.hasPos;
/* 134 */       iter.set(newFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void mirrorFramesY(List<KeyFrame> keyFrames)
/*     */   {
/* 140 */     ListIterator iter = keyFrames.listIterator();
/* 141 */     while (iter.hasNext())
/*     */     {
/* 143 */       KeyFrame keyFrame = (KeyFrame)iter.next();
/* 144 */       KeyFrame newFrame = new KeyFrame(keyFrame.getTime(), -keyFrame.getRotX(), keyFrame.getRotY(), -keyFrame.getRotZ(), keyFrame.getPosX(), -keyFrame.getPosY(), keyFrame.getPosZ(), keyFrame.getInterpType());
/*     */ 
/* 146 */       newFrame.hasPos = keyFrame.hasPos;
/* 147 */       iter.set(newFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void mirrorFramesZ(List<KeyFrame> keyFrames)
/*     */   {
/* 153 */     ListIterator iter = keyFrames.listIterator();
/* 154 */     while (iter.hasNext())
/*     */     {
/* 156 */       KeyFrame keyFrame = (KeyFrame)iter.next();
/* 157 */       KeyFrame newFrame = new KeyFrame(keyFrame.getTime(), -keyFrame.getRotX(), -keyFrame.getRotY(), keyFrame.getRotZ(), keyFrame.getPosX(), keyFrame.getPosY(), -keyFrame.getPosZ(), keyFrame.getInterpType());
/*     */ 
/* 159 */       newFrame.hasPos = keyFrame.hasPos;
/* 160 */       iter.set(newFrame);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void offsetFramesCircular(List<KeyFrame> keyFrames, float start, float end, float offset)
/*     */   {
/* 166 */     if (keyFrames.size() < 1) {
/* 167 */       return;
/*     */     }
/* 169 */     float diff = end - start;
/* 170 */     offset %= diff;
/* 171 */     float k1 = end - offset;
/*     */ 
/* 173 */     List copy = cloneFrames(keyFrames);
/* 174 */     keyFrames.clear();
/* 175 */     KeyFrame currFrame = null;
/* 176 */     ListIterator iter = copy.listIterator();
/*     */ 
/* 179 */     while (iter.hasNext())
/*     */     {
/* 181 */       currFrame = (KeyFrame)iter.next();
/* 182 */       if (currFrame.getTime() >= start) break;
/* 183 */       keyFrames.add(currFrame);
/*     */     }
/*     */ 
/* 189 */     List buffer = new ArrayList();
/* 190 */     buffer.add(currFrame);
/* 191 */     while (iter.hasNext())
/*     */     {
/* 193 */       currFrame = (KeyFrame)iter.next();
/* 194 */       if (currFrame.getTime() >= k1) {
/*     */         break;
/*     */       }
/* 197 */       buffer.add(currFrame);
/*     */     }
/*     */     KeyFrame fencepostStart;
/*     */     KeyFrame fencepostStart;
/* 202 */     if (!MathUtil.floatEquals(currFrame.getTime(), k1, 0.001F))
/*     */     {
/* 204 */       iter.previous();
/* 205 */       KeyFrame prev = (KeyFrame)iter.previous();
/*     */ 
/* 207 */       float dt = k1 - prev.getTime();
/* 208 */       float dtFrame = currFrame.getTime() - prev.getTime();
/* 209 */       float r = dt / dtFrame;
/* 210 */       float x = prev.getRotX() + r * (currFrame.getRotX() - prev.getRotX());
/* 211 */       float y = prev.getRotY() + r * (currFrame.getRotY() - prev.getRotY());
/* 212 */       float z = prev.getRotZ() + r * (currFrame.getRotZ() - prev.getRotZ());
/* 213 */       fencepostStart = new KeyFrame(start, x, y, z, InterpType.LINEAR);
/*     */     }
/*     */     else
/*     */     {
/* 217 */       fencepostStart = currFrame;
/*     */     }
/*     */ 
/* 221 */     keyFrames.add(fencepostStart);
/*     */ 
/* 224 */     while (iter.hasNext())
/*     */     {
/* 226 */       currFrame = (KeyFrame)iter.next();
/* 227 */       if (currFrame.getTime() <= end)
/*     */       {
/* 229 */         float t = currFrame.getTime() + offset - diff;
/* 230 */         KeyFrame newFrame = new KeyFrame(t, currFrame.getRotX(), currFrame.getRotY(), currFrame.getRotZ(), currFrame.getPosX(), currFrame.getPosY(), currFrame.getPosZ(), InterpType.LINEAR);
/*     */ 
/* 232 */         newFrame.hasPos = currFrame.hasPos;
/* 233 */         keyFrames.add(newFrame);
/*     */       }
/*     */       else
/*     */       {
/* 237 */         iter.previous();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 243 */     Iterator iter2 = buffer.iterator();
/* 244 */     while (iter2.hasNext())
/*     */     {
/* 246 */       currFrame = (KeyFrame)iter2.next();
/* 247 */       float t = currFrame.getTime() + offset;
/* 248 */       KeyFrame newFrame = new KeyFrame(t, currFrame.getRotX(), currFrame.getRotY(), currFrame.getRotZ(), currFrame.getPosX(), currFrame.getPosY(), currFrame.getPosZ(), InterpType.LINEAR);
/*     */ 
/* 250 */       newFrame.hasPos = currFrame.hasPos;
/* 251 */       keyFrames.add(newFrame);
/*     */     }
/*     */ 
/* 254 */     keyFrames.add(new KeyFrame(end, fencepostStart.getRotX(), fencepostStart.getRotY(), fencepostStart.getRotZ(), InterpType.LINEAR));
/*     */ 
/* 257 */     while (iter.hasNext())
/*     */     {
/* 259 */       keyFrames.add(iter.next());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.client.render.animation.KeyFrame
 * JD-Core Version:    0.6.2
 */