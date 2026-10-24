import net.runelite.api.Perspective;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fh")
public class classFH {
   @ObfuscatedSignature(descriptor = "Lfq;")
   @ObfuscatedName("az")
   static final classFQ field2258;
   @ObfuscatedSignature(descriptor = "Lfq;")
   @ObfuscatedName("af")
   static final classFQ field2256;
   @ObfuscatedName("ae")
   public static int[] field2249 = new int[65536];
   @ObfuscatedName("ab")
   static int[] field2254 = new int[512];
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field2262 = 254;
   @ObfuscatedName("as")
   public static int[] field2251 = new int[2048];
   @ObfuscatedName("ax")
   public static int[] field2252 = new int[2048];
   @ObfuscatedName("ac")
   static float[] field2253 = new float[2048];
   @ObfuscatedName("aa")
   static float[] field2257 = new float[2048];
   @ObfuscatedSignature(descriptor = "Lfd;")
   @ObfuscatedName("ao")
   public static classFD field2255;
   @ObfuscatedSignature(descriptor = "Lfq;")
   @ObfuscatedName("aj")
   static classFQ field2259;
   @ObfuscatedName("ag")
   static int[] field2250 = new int[2048];
   @ObfuscatedName("al")
   static double field2260;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2261 = 253;

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("ak")
   static void method4778(
      float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      field2259.vmethod452(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("az")
   public static void method4704(boolean var0) {
      if (var0 && classYW.field7101 != null) {
         field2259 = field2256;
      } else {
         field2259 = field2258;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfg;)V")
   @ObfuscatedName("bp")
   public static void method4708(classFG var0) {
      field2255.field2237 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("bt")
   public static void method4712(double var0) {
      method4716(var0, 0, 512);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dd")
   public static void method4775(int var0, int var1) {
      int var2 = field2255.field2212[0];
      int var3 = var2 / classYW.field7100;
      int var4 = var2 - var3 * classYW.field7100;
      field2255.method4619(var0, var4, var1, var3);
   }

   @ObfuscatedSignature(descriptor = "(ID)I")
   @ObfuscatedName("ag")
   static int method4718(int var0, double var1) {
      double var3 = (var0 >> 16) / 256.0;
      double var5 = (var0 >> 8 & 0xFF) / 256.0;
      double var7 = (var0 & 0xFF) / 256.0;
      var3 = Math.pow(var3, var1);
      var5 = Math.pow(var5, var1);
      var7 = Math.pow(var7, var1);
      int var9 = (int)(var3 * 256.0);
      int var10 = (int)(var5 * 256.0);
      int var11 = (int)(var7 * 256.0);
      return (var9 << 16) + (var10 << 8) + var11;
   }

   @ObfuscatedSignature(descriptor = "()D")
   @ObfuscatedName("as")
   public static double method4722() {
      return field2260;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ax")
   static int method4725() {
      return field2255.field2230;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   static int method4730() {
      return field2255.field2233;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   static int method4732() {
      return field2255.field2231;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   static int method4736() {
      return field2255.field2228;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   static int method4740() {
      return field2255.field2224;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cu")
   static int method4742() {
      return field2255.field2227;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   public static int method4747() {
      return field2255.field2207;
   }

   @ObfuscatedSignature(descriptor = "(Lfg;)V")
   @ObfuscatedName("af")
   public static void method4709(classFG var0) {
      field2255.field2237 = var0;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   static boolean method4755() {
      return field2259.vmethod449();
   }

   @ObfuscatedSignature(descriptor = "([III[F)V")
   @ObfuscatedName("ap")
   public static void method4758(int[] var0, int var1, int var2, float[] var3) {
      if (var3 == null && field2259 == field2256) {
         field2259 = field2258;
      }

      field2259.method4959(var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public static void method4760() {
      method4764(classYW.field7095, classYW.field7094, classYW.field7097, classYW.field7096);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ai")
   static void method4764(int var0, int var1, int var2, int var3) {
      field2255.field2225 = var2 - var0;
      field2255.field2232 = var3 - var1;
      method4774();
      if (field2255.field2212.length < field2255.field2232) {
         field2255.field2212 = new int[classBZ.method1387(field2255.field2232, -1302004563)];
      }

      int var4 = var1 * classYW.field7100 + var0;

      for (int var5 = 0; var5 < field2255.field2232; var5++) {
         field2255.field2212[var5] = var4;
         var4 += classYW.field7100;
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("an")
   static void method4789(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20,
      int var21
   ) {
      field2259.vmethod460(
         var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21
      );
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aw")
   public static void method4776(int var0, int var1) {
      int var2 = field2255.field2212[0];
      int var3 = var2 / classYW.field7100;
      int var4 = var2 - var3 * classYW.field7100;
      field2255.method4619(var0, var4, var1, var3);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("ah")
   static void method4793(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      classUK.method11393(field2259, var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("av")
   static void method4782(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      field2259.vmethod455(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("at")
   static void method4787(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20,
      int var21
   ) {
      field2259.vmethod457(
         var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21
      );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cp")
   static int method4743() {
      return field2255.field2227;
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("am")
   static void method4792(
      float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      field2259.method4964(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("de")
   static void method4794(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      classUK.method11393(field2259, var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   static {
      for (int var0 = 1; var0 < 512; var0++) {
         field2254[var0] = 32768 / var0;
      }

      for (int var5 = 1; var5 < field2250.length; var5++) {
         field2250[var5] = 65536 / var5;
      }

      for (int var6 = 0; var6 < 2048; var6++) {
         double var1 = Math.sin(var6 * 0.0030679615);
         double var3 = Math.cos(var6 * 0.0030679615);
         field2251[var6] = (int)(65536.0 * var1);
         field2252[var6] = (int)(65536.0 * var3);
         field2253[var6] = (float)var1;
         field2257[var6] = (float)var3;
      }

      field2255 = new classFD();
      field2258 = new classFT(field2255);
      field2256 = new classFF(field2255);
      field2259 = field2258;
      field2251 = Perspective.SINE;
      field2252 = Perspective.COSINE;
      field2253 = Perspective.SINEF;
      field2257 = Perspective.COSINEF;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   static int method4751() {
      return field2255.field2225;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bx")
   public static void method4705(boolean var0) {
      if (var0 && classYW.field7101 != null) {
         field2259 = field2256;
      } else {
         field2259 = field2258;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bc")
   public static void method4706(boolean var0) {
      if (var0 && classYW.field7101 != null) {
         field2259 = field2256;
      } else {
         field2259 = field2258;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   static int method4744() {
      return field2255.field2227;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DII)V")
   @ObfuscatedName("bk")
   static void method4715(double var0, int var2, int var3) {
      field2260 = var0;
      int var4 = var2 * 128;

      for (int var5 = var2; var5 < var3; var5++) {
         double var6 = (var5 >> 3) / 64.0 + 0.0078125;
         double var8 = (var5 & 7) / 8.0 + 0.0625;

         for (int var10 = 0; var10 < 128; var10++) {
            double var11 = var10 / 128.0;
            double var13 = var11;
            double var15 = var11;
            double var17 = var11;
            if (var8 != 0.0) {
               double var19;
               if (var11 < 0.5) {
                  var19 = var11 * (1.0 + var8);
               } else {
                  var19 = var11 + var8 - var11 * var8;
               }

               double var21 = 2.0 * var11 - var19;
               double var23 = var6 + 0.3333333333333333;
               if (var23 > 1.0) {
                  var23--;
               }

               double var27 = var6 - 0.3333333333333333;
               if (var27 < 0.0) {
                  var27++;
               }

               if (6.0 * var23 < 1.0) {
                  var13 = var21 + (var19 - var21) * 6.0 * var23;
               } else if (2.0 * var23 < 1.0) {
                  var13 = var19;
               } else if (3.0 * var23 < 2.0) {
                  var13 = var21 + (var19 - var21) * (0.6666666666666666 - var23) * 6.0;
               } else {
                  var13 = var21;
               }

               if (6.0 * var6 < 1.0) {
                  var15 = var21 + (var19 - var21) * 6.0 * var6;
               } else if (2.0 * var6 < 1.0) {
                  var15 = var19;
               } else if (3.0 * var6 < 2.0) {
                  var15 = var21 + (var19 - var21) * (0.6666666666666666 - var6) * 6.0;
               } else {
                  var15 = var21;
               }

               if (6.0 * var27 < 1.0) {
                  var17 = var21 + (var19 - var21) * 6.0 * var27;
               } else if (2.0 * var27 < 1.0) {
                  var17 = var19;
               } else if (3.0 * var27 < 2.0) {
                  var17 = var21 + (var19 - var21) * (0.6666666666666666 - var27) * 6.0;
               } else {
                  var17 = var21;
               }
            }

            int var29 = (int)(var13 * 256.0);
            int var20 = (int)(var15 * 256.0);
            int var30 = (int)(var17 * 256.0);
            int var22 = (var29 << 16) + (var20 << 8) + var30;
            var22 = method4718(var22, var0);
            if (var22 == 0) {
               var22 = 1;
            }

            field2249[var4++] = var22;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfg;)V")
   @ObfuscatedName("bm")
   public static void method4710(classFG var0) {
      field2255.field2237 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ch")
   static void method4765(int var0, int var1, int var2, int var3) {
      field2255.field2225 = var2 - var0;
      field2255.field2232 = var3 - var1;
      method4774();
      if (field2255.field2212.length < field2255.field2232) {
         field2255.field2212 = new int[classBZ.method1387(field2255.field2232, 1402450308)];
      }

      int var4 = var1 * classYW.field7100 + var0;

      for (int var5 = 0; var5 < field2255.field2232; var5++) {
         field2255.field2212[var5] = var4;
         var4 += classYW.field7100;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("bg")
   public static void method4713(double var0) {
      method4716(var0, 0, 512);
   }

   @ObfuscatedSignature(descriptor = "(DII)V")
   @ObfuscatedName("ab")
   static void method4716(double var0, int var2, int var3) {
      field2260 = var0;
      int var4 = var2 * 128;

      for (int var5 = var2; var5 < var3; var5++) {
         double var6 = (var5 >> 3) / 64.0 + 0.0078125;
         double var8 = (var5 & 7) / 8.0 + 0.0625;

         for (int var10 = 0; var10 < 128; var10++) {
            double var11 = var10 / 128.0;
            double var13 = var11;
            double var15 = var11;
            double var17 = var11;
            if (var8 != 0.0) {
               double var19;
               if (var11 < 0.5) {
                  var19 = var11 * (1.0 + var8);
               } else {
                  var19 = var11 + var8 - var11 * var8;
               }

               double var21 = 2.0 * var11 - var19;
               double var23 = var6 + 0.3333333333333333;
               if (var23 > 1.0) {
                  var23--;
               }

               double var27 = var6 - 0.3333333333333333;
               if (var27 < 0.0) {
                  var27++;
               }

               if (6.0 * var23 < 1.0) {
                  var13 = var21 + (var19 - var21) * 6.0 * var23;
               } else if (2.0 * var23 < 1.0) {
                  var13 = var19;
               } else if (3.0 * var23 < 2.0) {
                  var13 = var21 + (var19 - var21) * (0.6666666666666666 - var23) * 6.0;
               } else {
                  var13 = var21;
               }

               if (6.0 * var6 < 1.0) {
                  var15 = var21 + (var19 - var21) * 6.0 * var6;
               } else if (2.0 * var6 < 1.0) {
                  var15 = var19;
               } else if (3.0 * var6 < 2.0) {
                  var15 = var21 + (var19 - var21) * (0.6666666666666666 - var6) * 6.0;
               } else {
                  var15 = var21;
               }

               if (6.0 * var27 < 1.0) {
                  var17 = var21 + (var19 - var21) * 6.0 * var27;
               } else if (2.0 * var27 < 1.0) {
                  var17 = var19;
               } else if (3.0 * var27 < 2.0) {
                  var17 = var21 + (var19 - var21) * (0.6666666666666666 - var27) * 6.0;
               } else {
                  var17 = var21;
               }
            }

            int var29 = (int)(var13 * 256.0);
            int var20 = (int)(var15 * 256.0);
            int var30 = (int)(var17 * 256.0);
            int var22 = (var29 << 16) + (var20 << 8) + var30;
            var22 = method4718(var22, var0);
            if (var22 == 0) {
               var22 = 1;
            }

            field2249[var4++] = var22;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("dw")
   static void method4790(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20,
      int var21
   ) {
      field2259.vmethod460(
         var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21
      );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ID)I")
   @ObfuscatedName("bo")
   static int method4719(int var0, double var1) {
      double var3 = (var0 >> 16) / 256.0;
      double var5 = (var0 >> 8 & -1222517291) / 256.0;
      double var7 = (var0 & 0xFF) / 256.0;
      var3 = Math.pow(var3, var1);
      var5 = Math.pow(var5, var1);
      var7 = Math.pow(var7, var1);
      int var9 = (int)(var3 * 256.0);
      int var10 = (int)(var5 * 256.0);
      int var11 = (int)(var7 * 256.0);
      return (var9 << 16) + (var10 << 8) + var11;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ID)I")
   @ObfuscatedName("bu")
   static int method4720(int var0, double var1) {
      double var3 = (var0 >> 16) / 256.0;
      double var5 = (var0 >> 8 & 0xFF) / 256.0;
      double var7 = (var0 & 0xFF) / 256.0;
      var3 = Math.pow(var3, var1);
      var5 = Math.pow(var5, var1);
      var7 = Math.pow(var7, var1);
      int var9 = (int)(var3 * 256.0);
      int var10 = (int)(var5 * 256.0);
      int var11 = (int)(var7 * 256.0);
      return (var9 << 16) + (var10 << 8) + var11;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cv")
   static int method4752() {
      return field2255.field2225;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()D")
   @ObfuscatedName("bw")
   public static double method4723() {
      return field2260;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cm")
   public static void method4770() {
      field2255.method4617();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()D")
   @ObfuscatedName("bb")
   public static double method4724() {
      return field2260;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("be")
   static int method4726() {
      return field2255.field2230;
   }

   classFH() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("co")
   static boolean method4756() {
      return field2259.vmethod449();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bf")
   static int method4731() {
      return field2255.field2233;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bs")
   static int method4733() {
      return field2255.field2231;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bd")
   static int method4734() {
      return field2255.field2231;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   static int method4735() {
      return field2255.field2231;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bv")
   static int method4737() {
      return field2255.field2228;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bh")
   static int method4738() {
      return field2255.field2228;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bz")
   static int method4739() {
      return field2255.field2228;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("cz")
   static void method4766(int var0, int var1, int var2, int var3) {
      field2255.field2225 = var2 - var0;
      field2255.field2232 = var3 - var1;
      method4774();
      if (field2255.field2212.length < field2255.field2232) {
         field2255.field2212 = new int[classBZ.method1387(field2255.field2232, -195760272)];
      }

      int var4 = var1 * classYW.field7100 + var0;

      for (int var5 = 0; var5 < field2255.field2232; var5++) {
         field2255.field2212[var5] = var4;
         var4 += classYW.field7100;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("dc")
   public static void method4777(int var0, int var1) {
      int var2 = field2255.field2212[0];
      int var3 = var2 / classYW.field7100;
      int var4 = var2 - var3 * classYW.field7100;
      field2255.method4619(var0, var4, var1, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cb")
   static int method4745() {
      return field2255.field2227;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cs")
   static int method4753() {
      return field2255.field2225;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ci")
   public static void method4771() {
      field2255.method4617();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cq")
   public static int method4748() {
      return field2255.field2207;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cl")
   public static int method4749() {
      return field2255.field2207;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cd")
   public static int method4750() {
      return field2255.field2207;
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("ae")
   public static void method4714(double var0) {
      method4716(var0, 0, 512);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("dr")
   static void method4783(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      field2259.vmethod455(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bq")
   static int method4727() {
      return field2255.field2230;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bn")
   public static void method4707(boolean var0) {
      if (var0 && classYW.field7101 != null) {
         field2259 = field2256;
      } else {
         field2259 = field2258;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ck")
   static boolean method4757() {
      return field2259.vmethod449();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([III[F)V")
   @ObfuscatedName("ca")
   public static void method4759(int[] var0, int var1, int var2, float[] var3) {
      if (var3 == null && field2259 == field2256) {
         field2259 = field2258;
      }

      field2259.method4959(var0, var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cc")
   public static void method4761() {
      method4764(classYW.field7095, classYW.field7094, classYW.field7097, classYW.field7096);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cf")
   public static void method4762() {
      method4764(classYW.field7095, classYW.field7094, classYW.field7097, classYW.field7096);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cn")
   public static void method4763() {
      method4764(classYW.field7095, classYW.field7094, classYW.field7097, classYW.field7096);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("by")
   static int method4728() {
      return field2255.field2230;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ID)I")
   @ObfuscatedName("ba")
   static int method4721(int var0, double var1) {
      double var3 = (var0 >> 16) / 256.0;
      double var5 = (var0 >> 8 & 993066255) / 256.0;
      double var7 = (var0 & 398615773) / 256.0;
      var3 = Math.pow(var3, var1);
      var5 = Math.pow(var5, var1);
      var7 = Math.pow(var7, var1);
      int var9 = (int)(var3 * 256.0);
      int var10 = (int)(var5 * 256.0);
      int var11 = (int)(var7 * 256.0);
      return (var9 << 16) + (var10 << 8) + var11;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("cg")
   static void method4767(int var0, int var1, int var2, int var3) {
      field2255.field2225 = var2 - var0;
      field2255.field2232 = var3 - var1;
      method4774();
      if (field2255.field2212.length < field2255.field2232) {
         field2255.field2212 = new int[classBZ.method1387(field2255.field2232, -393870383)];
      }

      int var4 = var1 * classYW.field7100 + var0;

      for (int var5 = 0; var5 < field2255.field2232; var5++) {
         field2255.field2212[var5] = var4;
         var4 += classYW.field7100;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ct")
   static int method4746() {
      return field2255.field2227;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("cj")
   static void method4768(int var0, int var1, int var2, int var3) {
      field2255.field2225 = var2 - var0;
      field2255.field2232 = var3 - var1;
      method4774();
      if (field2255.field2212.length < field2255.field2232) {
         field2255.field2212 = new int[classBZ.method1387(field2255.field2232, -1593368818)];
      }

      int var4 = var1 * classYW.field7100 + var0;

      for (int var5 = 0; var5 < field2255.field2232; var5++) {
         field2255.field2212[var5] = var4;
         var4 += classYW.field7100;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bi")
   static int method4729() {
      return field2255.field2230;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cx")
   public static void method4772() {
      field2255.method4617();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ce")
   public static void method4773() {
      field2255.method4617();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfg;)V")
   @ObfuscatedName("br")
   public static void method4711(classFG var0) {
      field2255.field2237 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DII)V")
   @ObfuscatedName("bj")
   static void method4717(double var0, int var2, int var3) {
      field2260 = var0;
      int var4 = var2 * -612406923;

      for (int var5 = var2; var5 < var3; var5++) {
         double var6 = (var5 >> 3) / 64.0 + 0.0078125;
         double var8 = (var5 & 7) / 8.0 + 0.0625;

         for (int var10 = 0; var10 < 128; var10++) {
            double var11 = var10 / 128.0;
            double var13 = var11;
            double var15 = var11;
            double var17 = var11;
            if (var8 != 0.0) {
               double var19;
               if (var11 < 0.5) {
                  var19 = var11 * (1.0 + var8);
               } else {
                  var19 = var11 + var8 - var11 * var8;
               }

               double var21 = 2.0 * var11 - var19;
               double var23 = var6 + 0.3333333333333333;
               if (var23 > 1.0) {
                  var23--;
               }

               double var27 = var6 - 0.3333333333333333;
               if (var27 < 0.0) {
                  var27++;
               }

               if (6.0 * var23 < 1.0) {
                  var13 = var21 + (var19 - var21) * 6.0 * var23;
               } else if (2.0 * var23 < 1.0) {
                  var13 = var19;
               } else if (3.0 * var23 < 2.0) {
                  var13 = var21 + (var19 - var21) * (0.6666666666666666 - var23) * 6.0;
               } else {
                  var13 = var21;
               }

               if (6.0 * var6 < 1.0) {
                  var15 = var21 + (var19 - var21) * 6.0 * var6;
               } else if (2.0 * var6 < 1.0) {
                  var15 = var19;
               } else if (3.0 * var6 < 2.0) {
                  var15 = var21 + (var19 - var21) * (0.6666666666666666 - var6) * 6.0;
               } else {
                  var15 = var21;
               }

               if (6.0 * var27 < 1.0) {
                  var17 = var21 + (var19 - var21) * 6.0 * var27;
               } else if (2.0 * var27 < 1.0) {
                  var17 = var19;
               } else if (3.0 * var27 < 2.0) {
                  var17 = var21 + (var19 - var21) * (0.6666666666666666 - var27) * 6.0;
               } else {
                  var17 = var21;
               }
            }

            int var29 = (int)(var13 * 256.0);
            int var20 = (int)(var15 * 256.0);
            int var30 = (int)(var17 * 256.0);
            int var22 = (var29 << 16) + (var20 << 8) + var30;
            var22 = method4718(var22, var0);
            if (var22 == 0) {
               var22 = 1;
            }

            field2249[var4++] = var22;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   public static void method4774() {
      field2255.method4617();
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("dm")
   static void method4779(
      float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      field2259.vmethod452(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("dx")
   static void method4780(
      float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      field2259.vmethod452(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("dy")
   static void method4781(
      float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      field2259.vmethod452(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("di")
   static void method4784(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      field2259.vmethod455(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("du")
   static void method4785(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      field2259.vmethod455(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("dt")
   static void method4786(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      field2259.vmethod455(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cy")
   static int method4754() {
      return field2255.field2225;
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("dk")
   static void method4788(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20,
      int var21
   ) {
      field2259.vmethod457(
         var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21
      );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("db")
   static void method4791(
      float var0,
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      int var9,
      int var10,
      int var11,
      int var12,
      int var13,
      int var14,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19,
      int var20,
      int var21
   ) {
      field2259.vmethod460(
         var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21
      );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cr")
   static int method4741() {
      return field2255.field2224;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("dh")
   static void method4795(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      classUK.method11393(field2259, var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("cw")
   static void method4769(int var0, int var1, int var2, int var3) {
      field2255.field2225 = var2 - var0;
      field2255.field2232 = var3 - var1;
      method4774();
      if (field2255.field2212.length < field2255.field2232) {
         field2255.field2212 = new int[classBZ.method1387(field2255.field2232, 667682975)];
      }

      int var4 = var1 * classYW.field7100 + var0;

      for (int var5 = 0; var5 < field2255.field2232; var5++) {
         field2255.field2212[var5] = var4;
         var4 += classYW.field7100;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("dz")
   static void method4796(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      classUK.method11393(field2259, var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }
}
