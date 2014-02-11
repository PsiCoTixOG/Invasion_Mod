/*     */ package invmod.common.entity;
/*     */ 
/*     */ import com.google.common.io.ByteArrayDataInput;
/*     */ import com.google.common.io.ByteArrayDataOutput;

/*     */ import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

/*     */ import java.util.Random;

import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTTagByte;
/*     */ import net.minecraft.src.lu;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.LongHashMapEntry;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityIMBolt extends Entity implements IEntityAdditionalSpawnData
/*     */ {
/*     */   private int age;
/*     */   private int ticksToRender;
/*     */   private long timeCreated;
/*     */   private double[][] vertices;
/*     */   private long lastVertexUpdate;
/*     */   private float yaw;
/*     */   private float pitch;
/*     */   private double distance;
/*     */   private float widthVariance;
/*     */   private float vecX;
/*     */   private float vecY;
/*     */   private float vecZ;
/*     */   private int soundMade;
/*     */ 
/*     */   public EntityIMBolt(World world)
/*     */   {
/*  31 */     super(world);
/*  32 */     this.age = 0;
/*  33 */     this.timeCreated = (this.lastVertexUpdate = System.currentTimeMillis());
/*  34 */     this.vertices = new double[3][0];
/*  35 */     this.widthVariance = 6.0F;
/*  36 */     this.am = true;
/*     */   }
/*     */ 
/*     */   public EntityIMBolt(World world, double x, double y, double z)
/*     */   {
/*  41 */     this(world);
/*  42 */     b(x, y, z);
/*     */   }
/*     */ 
/*     */   public EntityIMBolt(World world, double x, double y, double z, double x2, double y2, double z2, int ticksToRender, int soundMade)
/*     */   {
/*  47 */     this(world, x, y, z);
/*  48 */     this.vecX = ((float)(x2 - x));
/*  49 */     this.vecY = ((float)(y2 - y));
/*  50 */     this.vecZ = ((float)(z2 - z));
/*  51 */     this.ticksToRender = ticksToRender;
/*  52 */     this.soundMade = soundMade;
/*  53 */     setHeading(this.vecX, this.vecY, this.vecZ);
/*  54 */     doVertexUpdate();
/*     */   }
/*     */ 
/*     */   public void writeSpawnData(ByteArrayDataOutput data)
/*     */   {
/*  60 */     data.writeShort((short)this.ticksToRender);
/*  61 */     data.writeFloat((float)this.u);
/*  62 */     data.writeFloat((float)this.v);
/*  63 */     data.writeFloat((float)this.w);
/*  64 */     data.writeFloat(this.vecX);
/*  65 */     data.writeFloat(this.vecY);
/*  66 */     data.writeFloat(this.vecZ);
/*  67 */     data.writeByte((byte)this.soundMade);
/*     */   }
/*     */ 
/*     */   public void readSpawnData(ByteArrayDataInput data)
/*     */   {
/*  73 */     this.ticksToRender = data.readShort();
/*  74 */     b(data.readFloat(), data.readFloat(), data.readFloat());
/*  75 */     setHeading(data.readFloat(), data.readFloat(), data.readFloat());
/*  76 */     this.soundMade = data.readByte();
/*  77 */     doVertexUpdate();
/*     */   }
/*     */ 
/*     */   public void l_()
/*     */   {
/*  83 */     super.l_();
/*  84 */     this.age += 1;
/*  85 */     if ((this.age == 1) && (this.soundMade == 1)) {
/*  86 */       this.q.a(this, "invmod:zap", 1.0F, 1.0F);
/*     */     }
/*  88 */     if (this.age > this.ticksToRender)
/*  89 */       w();
/*     */   }
/*     */ 
/*     */   public double[][] getVertices()
/*     */   {
/*  95 */     long time = System.currentTimeMillis();
/*  96 */     if (time - this.timeCreated > this.ticksToRender * 50) {
/*  97 */       return (double[][])null;
/*     */     }
/*  99 */     if (time - this.lastVertexUpdate >= 75L)
/*     */     {
/* 101 */       doVertexUpdate();
/* 102 */       while (this.lastVertexUpdate + 50L <= time) {
/* 103 */         this.lastVertexUpdate += 50L;
/*     */       }
/*     */     }
/* 106 */     return this.vertices;
/*     */   }
/*     */ 
/*     */   public float getYaw()
/*     */   {
/* 111 */     return this.yaw;
/*     */   }
/*     */ 
/*     */   public float getPitch()
/*     */   {
/* 116 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   public void a(byte byte0)
/*     */   {
/* 122 */     if (byte0 == 0)
/*     */     {
/* 124 */       this.q.a(this, "invmod:zap", 1.0F, 1.0F);
/*     */     }
/* 126 */     else if (byte0 != 1)
/*     */     {
/* 129 */       if (byte0 != 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void a()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void a(NBTTagByte nbttagcompound)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void b(NBTTagByte nbttagcompound)
/*     */   {
/*     */   }
/*     */ 
/*     */   private void setHeading(float x, float y, float z)
/*     */   {
/* 151 */     float xzSq = x * x + z * z;
/* 152 */     this.yaw = ((float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D) + 90.0F);
/* 153 */     this.pitch = ((float)(Math.atan2(LongHashMapEntry.a(xzSq), y) * 180.0D / 3.141592653589793D));
/* 154 */     this.distance = Math.sqrt(xzSq + y * y);
/*     */   }
/*     */ 
/*     */   private void doVertexUpdate()
/*     */   {
/* 159 */     this.q.C.a("IMBolt");
/* 160 */     this.widthVariance = (10.0F / (float)Math.log10(this.distance + 1.0D));
/* 161 */     int numberOfVertexes = 60;
/* 162 */     if (numberOfVertexes != this.vertices[0].length)
/*     */     {
/* 164 */       this.vertices[0] = new double[numberOfVertexes];
/* 165 */       this.vertices[1] = new double[numberOfVertexes];
/* 166 */       this.vertices[2] = new double[numberOfVertexes];
/*     */     }
/*     */ 
/* 169 */     for (int vertex = 0; vertex < numberOfVertexes; vertex++)
/*     */     {
/* 171 */       this.vertices[1][vertex] = (vertex * this.distance / (numberOfVertexes - 1));
/*     */     }
/*     */ 
/* 174 */     createSegment(0, numberOfVertexes - 1);
/* 175 */     this.q.C.b();
/*     */   }
/*     */ 
/*     */   private void createSegment(int begin, int end)
/*     */   {
/* 180 */     int points = end + 1 - begin;
/* 181 */     if (points <= 4)
/*     */     {
/* 183 */       if (points == 3)
/*     */       {
/* 185 */         createVertex(begin, begin + 1, end);
/*     */       }
/*     */       else
/*     */       {
/* 189 */         createVertex(begin, begin + 1, end);
/* 190 */         createVertex(begin, begin + 2, end);
/*     */       }
/* 192 */       return;
/*     */     }
/* 194 */     int midPoint = begin + points / 2;
/* 195 */     createVertex(begin, midPoint, end);
/* 196 */     createSegment(begin, midPoint);
/* 197 */     createSegment(midPoint, end);
/*     */   }
/*     */ 
/*     */   private void createVertex(int begin, int mid, int end)
/*     */   {
/* 202 */     double difference = this.vertices[0][end] - this.vertices[0][begin];
/* 203 */     double yDiffToMid = this.vertices[1][mid] - this.vertices[1][begin];
/* 204 */     double yRatio = yDiffToMid / (this.vertices[1][end] - this.vertices[1][begin]);
/* 205 */     this.vertices[0][mid] = (this.vertices[0][begin] + difference * yRatio + (this.q.s.nextFloat() - 0.5D) * yDiffToMid * this.widthVariance);
/* 206 */     difference = this.vertices[2][end] - this.vertices[2][begin];
/* 207 */     this.vertices[2][mid] = (this.vertices[2][begin] + difference * yRatio + (this.q.s.nextFloat() - 0.5D) * yDiffToMid * this.widthVariance);
/*     */   }
/*     */ }

/* Location:           C:\Users\PsiCoTix\Downloads\_NOOBHAUS\MCDev\DeOp\DeOpInvasionMod.zip
 * Qualified Name:     invmod.common.entity.EntityIMBolt
 * JD-Core Version:    0.6.2
 */