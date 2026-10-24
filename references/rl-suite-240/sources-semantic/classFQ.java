import net.runelite.api.Rasterizer;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fq")
public abstract class classFQ extends classYW {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2379 = 3500;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2378 = 210;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2380 = 14;
   @ObfuscatedName("ax")
   static int field2372 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final float field2377 = 1.5F;
   @ObfuscatedName("qu")
   public static final Rasterizer field2373 = new rl13();
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2381 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2382 = 50;
   @ObfuscatedName("ac")
   boolean field2374 = false;
   @ObfuscatedName("aa")
   int[] field2375 = classFH.field2249;
   @ObfuscatedSignature(descriptor = "Lfd;")
   @ObfuscatedName("ao")
   classFD field2376;

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("bm")
   void method4961(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12) {
      byte var13 = this.field2376.field2213.field2301;
      if (var13 > 0) {
         byte var14 = this.field2376.field2213.field2298;
         byte var15 = this.field2376.field2213.field2299;
         byte var16 = this.field2376.field2213.field2300;
         var10 = method4939(var10, var14, var15, var16, var13);
         var11 = method4939(var11, var14, var15, var16, var13);
         var12 = method4939(var12, var14, var15, var16, var13);
      }

      this.vmethod452(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   @ObfuscatedSignature(descriptor = "(IBBBB)I")
   @ObfuscatedName("az")
   static int method4939(int var0, byte var1, byte var2, byte var3, byte var4) {
      int var5 = var0 >> 10 & 63;
      int var6 = var0 >> 7 & 7;
      int var7 = var0 & 127;
      int var8 = var4 & 255;
      if (var1 != -1) {
         var5 += (var1 - var5) * var8 >> 7;
      }

      if (var2 != -1) {
         var6 += (var2 - var6) * var8 >> 7;
      }

      if (var3 != -1) {
         var7 += (var3 - var7) * var8 >> 7;
      }

      return (var5 << 10 | var6 << 7 | var7) & 65535;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   static final int method4944(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("di")
   public static void method4969(int var0, int var1, int var2, int var3, int var4, int var5) {
      classYW.method13750(var0, var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("ba")
   abstract void vmethod457(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
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
      int var21,
      int var22
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("bt")
   void method4962(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12) {
      byte var13 = this.field2376.field2213.field2301;
      if (var13 > 0) {
         byte var14 = this.field2376.field2213.field2298;
         byte var15 = this.field2376.field2213.field2299;
         byte var16 = this.field2376.field2213.field2300;
         var10 = method4939(var10, var14, var15, var16, var13);
         var11 = method4939(var11, var14, var15, var16, var13);
         var12 = method4939(var12, var14, var15, var16, var13);
      }

      this.vmethod452(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public static void method4955(int var0) {
      int var1 = var0 * 210;
      method4952(var1);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("as")
   abstract boolean vmethod449();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   static final int method4948() {
      return field2372;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   static void method4950(int var0) {
      field2372 = var0;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("bd")
   public static void method4970(int var0, int var1, int var2, int var3, int var4) {
      classYW.method13771(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dp")
   public static void method4971() {
      classYW.method13708();
   }

   @ObfuscatedSignature(descriptor = "(F)F")
   @ObfuscatedName("xw")
   public static float method4967(float var0) {
      var0 = (var0 - 75.0F) * 1.0100503F;
      return 1.0100503F + 150.75377F / var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   static void method4951(int var0) {
      field2372 = var0;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("rn")
   public boolean method4972() {
      return this.vmethod449();
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("ao")
   abstract void vmethod452(
      float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("al")
   abstract void vmethod455(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bn")
   public static void method4956(int var0) {
      int var1 = var0 * 210;
      method4952(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   static void method4952(int var0) {
      field2372 = var0;
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("gi")
   public static void method4973(
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
      classFH.method4789(
         var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13, var14, var15, var16, var17, var18, var19, var20, var21
      );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IBBBB)I")
   @ObfuscatedName("aq")
   static int method4940(int var0, byte var1, byte var2, byte var3, byte var4) {
      int var5 = var0 >> 10 & 63;
      int var6 = var0 >> 7 & 7;
      int var7 = var0 & 127;
      int var8 = var4 & 255;
      if (var1 != -1) {
         var5 += (var1 - var5) * var8 >> 7;
      }

      if (var2 != -1) {
         var6 += (var2 - var6) * var8 >> 7;
      }

      if (var3 != -1) {
         var7 += (var3 - var7) * var8 >> 7;
      }

      return (var5 << 10 | var6 << 7 | var7) & 65535;
   }

   @ObfuscatedSignature(descriptor = "(III[I[I)V")
   @ObfuscatedName("xk")
   public static void method4974(int var0, int var1, int var2, int[] var3, int[] var4) {
      classYW.method13791(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("ei")
   public static void method4975(float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9) {
      classFH.method4782(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("fx")
   public static void method4976(int var0, int var1, int var2, int var3, int var4) {
      classYW.method13746(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "([III[F)V")
   @ObfuscatedName("br")
   void method4958(int[] var1, int var2, int var3, float[] var4) {
      method13701(var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("sa")
   public static void method4977(int var0, int var1, int var2, int var3, int var4) {
      classYW.method13737(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ad")
   public static void method4978(int var0, int var1, int var2, int var3) {
      classYW.method13710(var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("nz")
   public static void method4979(int var0, int var1, int var2, int var3, int var4) {
      classYW.method13728(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("qq")
   public static void method4968() {
      classWO.field6761.method13368();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IBBBB)I")
   @ObfuscatedName("ap")
   static int method4941(int var0, byte var1, byte var2, byte var3, byte var4) {
      int var5 = var0 >> 10 & 1265869542;
      int var6 = var0 >> 7 & 7;
      int var7 = var0 & 127;
      int var8 = var4 & -1237119813;
      if (var1 != -1) {
         var5 += (var1 - var5) * var8 >> 7;
      }

      if (var2 != -1) {
         var6 += (var2 - var6) * var8 >> 7;
      }

      if (var3 != -1) {
         var7 += (var3 - var7) * var8 >> 7;
      }

      return (var5 << 10 | var6 << 7 | var7) & 65535;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("xe")
   public static void method4980(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      classYW.method13804(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IBBBB)I")
   @ObfuscatedName("au")
   static int method4942(int var0, byte var1, byte var2, byte var3, byte var4) {
      int var5 = var0 >> 10 & 63;
      int var6 = var0 >> 7 & 7;
      int var7 = var0 & 127;
      int var8 = var4 & 255;
      if (var1 != -1) {
         var5 += (var1 - var5) * var8 >> 7;
      }

      if (var2 != -1) {
         var6 += (var2 - var6) * var8 >> 7;
      }

      if (var3 != -1) {
         var7 += (var3 - var7) * var8 >> 7;
      }

      return (var5 << 10 | var6 << 7 | var7) & 65535;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrs;Lrs;)I")
   @ObfuscatedName("we")
   public static int method4938(Ignore var0, Ignore var1) {
      return var0 == null ? var0.method10234(var0) : -847650061 * var0.field5834 - var1.field5834 * -847650061;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("zo")
   public static void method4981(int var0, int var1, int var2, int var3) {
      classYW.method13762(var0, var1, var2, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfq;FFFFFFFFFIII)V")
   @ObfuscatedName("eh")
   public static void method4963(
      classFQ var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12
   ) {
      byte var13 = var0.field2376.field2213.field2301;
      if (var13 > 0) {
         byte var14 = var0.field2376.field2213.field2298;
         byte var15 = var0.field2376.field2213.field2299;
         byte var16 = var0.field2376.field2213.field2300;
         var10 = method4939(var10, var14, var15, var16, var13);
         var11 = method4939(var11, var14, var15, var16, var13);
         var12 = method4939(var12, var14, var15, var16, var13);
      }

      var0.vmethod452(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ai")
   static final int method4945(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ar")
   static final int method4946(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aw")
   static final int method4947(int var0, int var1) {
      var1 = var1 * (var0 & 1825587246) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 356556254) {
         var1 = 1360573921;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("pz")
   public static void method4982(int var0, int var1, int var2, int var3, int var4, int var5) {
      classYW.method13733(var0, var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("ac")
   void method4964(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12) {
      byte var13 = this.field2376.field2213.field2301;
      if (var13 > 0) {
         byte var14 = this.field2376.field2213.field2298;
         byte var15 = this.field2376.field2213.field2299;
         byte var16 = this.field2376.field2213.field2300;
         var10 = method4939(var10, var14, var15, var16, var13);
         var11 = method4939(var11, var14, var15, var16, var13);
         var12 = method4939(var12, var14, var15, var16, var13);
      }

      this.vmethod452(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("cb")
   public static void method4983(int var0, int var1, int var2, int var3, int var4) {
      classYW.method13761(var0, var1, var2, var3, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   static void method4953(int var0) {
      field2372 = var0;
   }

   @ObfuscatedSignature(descriptor = "(IBBBB)I")
   @ObfuscatedName("ad")
   static int method4943(int var0, byte var1, byte var2, byte var3, byte var4) {
      int var5 = var0 >> 10 & 63;
      int var6 = var0 >> 7 & 7;
      int var7 = var0 & 127;
      int var8 = var4 & 255;
      if (var1 != -1) {
         var5 += (var1 - var5) * var8 >> 7;
      }

      if (var2 != -1) {
         var6 += (var2 - var6) * var8 >> 7;
      }

      if (var3 != -1) {
         var7 += (var3 - var7) * var8 >> 7;
      }

      return (var5 << 10 | var6 << 7 | var7) & 65535;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   static void method4954(int var0) {
      field2372 = var0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("nl")
   public static void method4984() {
      classFH.method4760();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   static final int method4949() {
      return field2372;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ah")
   public static void method4957(int var0) {
      int var1 = var0 * -1783356637;
      method4952(var1);
   }

   @ObfuscatedSignature(descriptor = "([III[F)V")
   @ObfuscatedName("bp")
   void method4959(int[] var1, int var2, int var3, float[] var4) {
      method13701(var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bc")
   abstract boolean vmethod450();

   classFQ(classFD var1) {
      this.field2376 = var1;
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("td")
   public static void method4985(
      float var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      classFH.method4778(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11);
   }

   @ObfuscatedSignature(descriptor = "([III[F)V")
   @ObfuscatedName("ax")
   void method4960(int[] var1, int var2, int var3, float[] var4) {
      method13701(var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("bb")
   abstract void vmethod458(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
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
      int var21,
      int var22
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("bu")
   abstract void vmethod456(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10);

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("bj")
   void method4965(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10) {
      byte var11 = this.field2376.field2213.field2301;
      if (var11 > 0) {
         int var12 = method4939(
            var10,
            this.field2376.field2213.field2298,
            this.field2376.field2213.field2299,
            this.field2376.field2213.field2300,
            this.field2376.field2213.field2301
         );
         var10 = this.field2375[var12];
      }

      this.vmethod455(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("bk")
   abstract void vmethod453(
      float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIII)V")
   @ObfuscatedName("bo")
   abstract void vmethod454(
      float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10, int var11, int var12
   );

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("wi")
   public static void method4986(int var0, int var1, int var2, int var3, int var4) {
      classYW.method13765(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("jk")
   public static void method4987(int var0, int var1, int var2, int var3) {
      classYW.method13756(var0, var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   abstract boolean vmethod451();

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("bi")
   abstract void vmethod459(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
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
      int var21,
      int var22
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("aj")
   abstract void vmethod460(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
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
      int var21,
      int var22
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("ay")
   abstract void vmethod461(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
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
      int var21,
      int var22
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFIIIIIIIIIIIII)V")
   @ObfuscatedName("bw")
   abstract void vmethod462(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
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
      int var21,
      int var22
   );

   @ObfuscatedSignature(descriptor = "(FFFFFFFFFI)V")
   @ObfuscatedName("aa")
   void method4966(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9, int var10) {
      byte var11 = this.field2376.field2213.field2299;
      if (var11 > 0) {
         int var12 = method4943(
            var10,
            this.field2376.field2213.field2299,
            this.field2376.field2213.field2300,
            this.field2376.field2213.field2300,
            this.field2376.field2213.field2300
         );
         var10 = this.field2375[var12];
      }

      this.vmethod455(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10);
   }
}
