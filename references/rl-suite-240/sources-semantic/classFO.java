import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fo")
public class classFO {
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("bp")
   public static final classUZ field2343 = new classUZ();
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field2354 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field2353 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field2352 = 3;
   @ObfuscatedName("cu")
   public static long[] field2349 = new long[1000];
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   public static final int field2357 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field2360 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final int field2358 = 127;
   @ObfuscatedName("cr")
   public static int field2348 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final long field2361 = 4294967295L;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field2356 = 20;
   @ObfuscatedName("br")
   static boolean field2350 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field2355 = 4;
   @ObfuscatedName("bm")
   static int field2347 = 0;
   @ObfuscatedName("bt")
   static int field2346 = 0;
   @ObfuscatedName("bg")
   static boolean field2344 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field2359 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field2351 = 2;
   @ObfuscatedName("cb")
   public static int[] field2345 = new int[1000];

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bk")
   public static int method4881(long var0) {
      int var2 = (int)(var0 >>> 52 & 4095L);
      if (4095L == var2) {
         var2 = -1;
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bg")
   public static int method4878(long var0) {
      return (int)(var0 >>> 20 & 4294967295L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIII)Z")
   @ObfuscatedName("cn")
   static final boolean method4915(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = field2346 * -2028924846 + var6;
      if (var7 < var0 && var7 < var1 && var7 < var2) {
         return false;
      } else {
         var7 = 1170217135 * field2346 - var6;
         if (var7 > var0 && var7 > var1 && var7 > var2) {
            return false;
         } else {
            var7 = field2347 * 1376644555 + var6;
            if (var7 < var3 && var7 < var4 && var7 < var5) {
               return false;
            } else {
               var7 = -2050307264 * field2347 - var6;
               return var7 <= var3 || var7 <= var4 || var7 <= var5;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("at")
   public static long method4869(int var0) {
      return field2349[var0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZII)J")
   @ObfuscatedName("bw")
   public static long method4885(int var0, int var1, int var2, int var3, boolean var4, int var5, int var6) {
      long var7 = (var1 & 127) << 0 | (var2 & 127) << 7 | (var0 & 3) << 14 | (var3 & 7) << 16 | (var5 & 4294967295L) << 20 | (var6 & 4095L) << 52;
      if (var4) {
         var7 |= 524288L;
      }

      return var7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIII)Z")
   @ObfuscatedName("ca")
   static boolean method4910(Model var0, int var1, int var2, int var3, int var4) {
      AABB var5 = var0.method5118(var1);
      int var6 = -915116879 * var5.field2365 + var2;
      int var7 = var3 + var5.field2364 * -1027280203;
      int var8 = var5.field2367 * -81102275 + var4;
      int var9 = var5.field2366 * 1301855055;
      int var10 = var5.field2363 * -623192411;
      int var11 = var5.field2368 * 2105673973;
      int var12 = -1229211407 * classLS.field4217 - var6;
      int var13 = 1234160379 * classKT.field4020 - var7;
      int var14 = 1574685861 * classHQ.field2866 - var8;
      if (Math.abs(var12) > classVI.field6511 * 756191369 + var9) {
         return false;
      } else if (Math.abs(var13) > 1266420275 * classJI.field3162 + var10) {
         return false;
      } else if (Math.abs(var14) > 1304241803 * classEV.field1941 + var11) {
         return false;
      } else if (Math.abs(var14 * 348139717 * classDK.field1540 - GroundObject.field1809 * -1947307103 * var13)
         > classEV.field1941 * 1304241803 * var10 + classJI.field3162 * 1266420275 * var11) {
         return false;
      } else {
         return Math.abs(-1947307103 * GroundObject.field1809 * var12 - -331219525 * classBY.field614 * var14)
               > var9 * classEV.field1941 * 1304241803 + classVI.field6511 * 756191369 * var11
            ? false
            : Math.abs(-331219525 * classBY.field614 * var13 - classDK.field1540 * 348139717 * var12)
               <= var10 * 756191369 * classVI.field6511 + classJI.field3162 * 1266420275 * var9;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("ah")
   public static int method4872(long var0) {
      return (int)(var0 >>> 7 & 127L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bn")
   public static int method4873(long var0) {
      return (int)(var0 >>> 7 & 127L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bx")
   public static int method4874(long var0) {
      return (int)(var0 >>> 7 & 127L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bc")
   public static int method4875(long var0) {
      return (int)(var0 >>> 7 & 127L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bp")
   public static int method4876(long var0) {
      return (int)(var0 >>> 16 & 7L);
   }

   classFO() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bm")
   public static int method4879(long var0) {
      return (int)(var0 >>> 20 & 4294967295L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bq")
   public static final void method4887(int var0, int var1) {
      field2347 = var0 * -750923805;
      field2346 = var1 * 1000488527;
      field2350 = true;
      field2348 = 0;
      field2349[0] = -1L;
      field2345[0] = Integer.MAX_VALUE;
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("am")
   public static long method4870(int var0) {
      return field2349[var0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bj")
   public static int method4882(long var0) {
      int var2 = (int)(var0 >>> 52 & 4095L);
      if (4095L == var2) {
         var2 = -1;
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIII)Z")
   @ObfuscatedName("ck")
   static boolean method4911(Model var0, int var1, int var2, int var3, int var4) {
      AABB var5 = var0.method5118(var1);
      int var6 = -915116879 * var5.field2365 + var2;
      int var7 = var3 + var5.field2364 * -1027280203;
      int var8 = var5.field2367 * 956809555 + var4;
      int var9 = var5.field2366 * 1301855055;
      int var10 = var5.field2363 * -100165370;
      int var11 = var5.field2368 * 2105673973;
      int var12 = -1229211407 * classLS.field4217 - var6;
      int var13 = 1234160379 * classKT.field4020 - var7;
      int var14 = -1589041292 * classHQ.field2866 - var8;
      if (Math.abs(var12) > classVI.field6511 * 756191369 + var9) {
         return false;
      } else if (Math.abs(var13) > 2032559775 * classJI.field3162 + var10) {
         return false;
      } else if (Math.abs(var14) > 390816090 * classEV.field1941 + var11) {
         return false;
      } else if (Math.abs(var14 * -528879009 * classDK.field1540 - GroundObject.field1809 * -1947307103 * var13)
         > classEV.field1941 * 1024561494 * var10 + classJI.field3162 * 392783693 * var11) {
         return false;
      } else {
         return Math.abs(-77712431 * GroundObject.field1809 * var12 - 1627644741 * classBY.field614 * var14)
               > var9 * classEV.field1941 * 1304241803 + classVI.field6511 * 756191369 * var11
            ? false
            : Math.abs(-2123915848 * classBY.field614 * var13 - classDK.field1540 * 348139717 * var12)
               <= var10 * 756191369 * classVI.field6511 + classJI.field3162 * 1266420275 * var9;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)J")
   @ObfuscatedName("bo")
   static long method4883(long var0) {
      return var0 & -524289L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)J")
   @ObfuscatedName("bu")
   static long method4884(long var0) {
      return var0 & -524289L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIIIFFFFIII)Z")
   @ObfuscatedName("cy")
   static final boolean method4909(
      Model var0, int var1, int var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9, int var10, int var11
   ) {
      if (!classAS.method657(2034401484)) {
         return false;
      } else {
         classEV.method4118(var5, var6, var7, var8, var9, var10, var11, (byte)-101);
         return GrandExchangeOffer.method7660(var0, var1, var2, var3, var4, (byte)-55);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JI)V")
   @ObfuscatedName("bz")
   static final void method4896(long var0, int var2) {
      if (field2345[613116963 * field2348] != Integer.MAX_VALUE && var0 != field2349[613116963 * field2348]) {
         field2348 += -1513689632;
         field2345[field2348 * 1142733336] = 910665429;
      }

      field2349[field2348 * 1289349345] = var0;
      field2345[field2348 * 613116963] = Math.min(field2345[-966473575 * field2348], var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JI)V")
   @ObfuscatedName("bv")
   static final void method4897(long var0, int var2) {
      if (field2345[613116963 * field2348] != Integer.MAX_VALUE && var0 != field2349[1693841039 * field2348]) {
         field2348 += 166110091;
         field2345[field2348 * 613116963] = Integer.MAX_VALUE;
      }

      field2349[field2348 * 613116963] = var0;
      field2345[field2348 * 613116963] = Math.min(field2345[548587664 * field2348], var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bb")
   public static final void method4888(int var0, int var1) {
      field2347 = var0 * 1918244488;
      field2346 = var1 * -644687673;
      field2350 = true;
      field2348 = 0;
      field2349[0] = -1L;
      field2345[0] = -520057567;
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ct")
   static final int method4902() {
      return field2347 * 1376644555;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("by")
   public static final void method4889(int var0, int var1) {
      field2347 = var0 * -750923805;
      field2346 = var1 * 1000488527;
      field2350 = true;
      field2348 = 0;
      field2349[0] = -1L;
      field2345[0] = Integer.MAX_VALUE;
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIILfp;)I")
   @ObfuscatedName("cf")
   static int method4913(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, AABB var9) {
      int var10 = var0 + AABB.method4917(var9, 1672551845);
      int var11 = var0 + var9.method4919(-388298457);
      int var12 = var1 + var9.method4923(1894496508);
      int var13 = var1 + var9.method4927((byte)4);
      int var14 = var2 + var9.method4929(1833874923);
      int var15 = var2 + AABB.method4932(var9, (byte)96);
      float var16 = var6 - var3;
      float var17 = var7 - var4;
      float var18 = var8 - var5;
      float var19 = 0.0F;
      float var20 = 1.0F;
      float var21 = 1.0E-9F;
      if (Math.abs(var16) < 1.0E-9F) {
         if (var3 < var10 || var3 > var11) {
            return -1;
         }
      } else {
         float var22 = 1.0F / var16;
         float var23 = (var10 - var3) * var22;
         float var24 = var22 * (var11 - var3);
         if (var23 > var24) {
            float var25 = var23;
            var23 = var24;
            var24 = var25;
         }

         var19 = Math.max(var19, var23);
         var20 = Math.min(var20, var24);
         if (var19 > var20) {
            return -1;
         }
      }

      if (Math.abs(var17) < 1.0E-9F) {
         if (var4 < var12 || var4 > var13) {
            return -1;
         }
      } else {
         float var27 = 1.0F / var17;
         float var30 = var27 * (var12 - var4);
         float var33 = var27 * (var13 - var4);
         if (var30 > var33) {
            float var36 = var30;
            var30 = var33;
            var33 = var36;
         }

         var19 = Math.max(var19, var30);
         var20 = Math.min(var20, var33);
         if (var19 > var20) {
            return -1;
         }
      }

      if (Math.abs(var18) < 1.0E-9F) {
         if (var5 < var14 || var5 > var15) {
            return -1;
         }
      } else {
         float var28 = 1.0F / var18;
         float var31 = var28 * (var14 - var5);
         float var34 = var28 * (var15 - var5);
         if (var31 > var34) {
            float var37 = var31;
            var31 = var34;
            var34 = var37;
         }

         var19 = Math.max(var19, var31);
         var20 = Math.min(var20, var34);
         if (var19 > var20) {
            return -1;
         }
      }

      float var29 = var3 + var19 * var16;
      float var32 = var19 * var17 + var4;
      float var35 = var5 + var18 * var19;
      return (int)Math.sqrt(var35 * var35 + (var29 * var29 + var32 * var32));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public static void method4892() {
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   public static void method4893() {
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   public static void method4894() {
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bl")
   public static final void method4895() {
      field2350 = false;
      field2348 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cb")
   static final boolean method4900() {
      return field2350;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JI)V")
   @ObfuscatedName("bh")
   static final void method4898(long var0, int var2) {
      if (field2345[613116963 * field2348] != Integer.MAX_VALUE && var0 != field2349[613116963 * field2348]) {
         field2348 += 166110091;
         field2345[field2348 * 613116963] = Integer.MAX_VALUE;
      }

      field2349[field2348 * 613116963] = var0;
      field2345[field2348 * 613116963] = Math.min(field2345[613116963 * field2348], var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JI)V")
   @ObfuscatedName("cr")
   static final void method4899(long var0, int var2) {
      if (field2345[613116963 * field2348] != Integer.MAX_VALUE && var0 != field2349[613116963 * field2348]) {
         field2348 += 166110091;
         field2345[field2348 * 613116963] = Integer.MAX_VALUE;
      }

      field2349[field2348 * 613116963] = var0;
      field2345[field2348 * 613116963] = Math.min(field2345[613116963 * field2348], var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cu")
   static final boolean method4901() {
      return field2350;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("br")
   public static int method4877(long var0) {
      return (int)(var0 >>> 16 & 7L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   static int method4868() {
      return 613116963 * field2348 + 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cp")
   static final int method4903() {
      return field2347 * 1376644555;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFIII)V")
   @ObfuscatedName("cq")
   public static final void method4904(float var0, float var1, float var2, float var3, int var4, int var5, int var6) {
      if (!field2344) {
         float var11 = 50.0F;
         float var12 = classFQ.method4948();
         float var13 = var11 * (1376644555 * field2347 - var4) / var6;
         float var14 = (field2346 * 1170217135 - var5) * var11 / var6;
         float var15 = (-852581753 * field2347 - var4) * var12 / var6;
         float var16 = (1170217135 * field2346 - var5) * var12 / var6;
         float var18 = var14 * var1 + var0 * var11;
         var11 = classDX.method3697(var14, var11, var1, var0, (byte)-86);
         float var19 = var1 * var16 + var12 * var0;
         var12 = classDX.method3697(var16, var12, var1, var0, (byte)-114);
         float var17 = classFY.method5301(var13, var11, var3, var2, (byte)53);
         float var20 = var13 * var2 + var3 * var11;
         float var25 = classFY.method5301(var15, var12, var3, var2, (byte)-2);
         float var21 = var3 * var12 + var15 * var2;
         classCI.method1500((int)var17, (int)var18, (int)var20, (int)var25, (int)var19, (int)var21, -99698685);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFIII)V")
   @ObfuscatedName("cl")
   public static final void method4905(float var0, float var1, float var2, float var3, int var4, int var5, int var6) {
      if (!field2344) {
         float var11 = 50.0F;
         float var12 = classFQ.method4948();
         float var13 = var11 * (-1357008780 * field2347 - var4) / var6;
         float var14 = (field2346 * 491979170 - var5) * var11 / var6;
         float var15 = (1376644555 * field2347 - var4) * var12 / var6;
         float var16 = (-322040928 * field2346 - var5) * var12 / var6;
         float var18 = var14 * var1 + var0 * var11;
         var11 = classDX.method3697(var14, var11, var1, var0, (byte)-124);
         float var19 = var1 * var16 + var12 * var0;
         var12 = classDX.method3697(var16, var12, var1, var0, (byte)-111);
         float var17 = classFY.method5301(var13, var11, var3, var2, (byte)40);
         float var20 = var13 * var2 + var3 * var11;
         float var25 = classFY.method5301(var15, var12, var3, var2, (byte)11);
         float var21 = var3 * var12 + var15 * var2;
         classCI.method1500((int)var17, (int)var18, (int)var20, (int)var25, (int)var19, (int)var21, -99698685);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;III)V")
   @ObfuscatedName("cd")
   static void method4906(classUU var0, int var1, int var2, int var3) {
      if (!field2344) {
         int var4 = 50;
         int var5 = classFQ.method4948();
         int var6 = (1376644555 * field2347 - var1) * var4 / var3;
         int var7 = var4 * (field2346 * 1170217135 - var2) / var3;
         int var8 = (field2347 * 1376644555 - var1) * var5 / var3;
         int var9 = var5 * (field2346 * 1170217135 - var2) / var3;
         classUU var10 = classLX.method7419(var0, (byte)91);
         classUU.method11670(var10, -416041636);
         float[] var11 = new float[3];
         var10.method11677(var6, var7, var4, var11, 1634822735);
         var6 = (int)var11[0];
         var7 = (int)var11[1];
         var4 = (int)var11[2];
         var10.method11677(var8, var9, var5, var11, 1634822735);
         var8 = (int)var11[0];
         var9 = (int)var11[1];
         var5 = (int)var11[2];
         var10.method11677(0.0F, 0.0F, 0.0F, var11, 1634822735);
         classFM.field2313 = (int)var11[0] * -1716444145;
         classCP.field1200 = (int)var11[1] * 1248579909;
         classVR.field6558 = -1913750817 * (int)var11[2];
         var6 -= -1365423377 * classFM.field2313;
         var7 -= 1448483213 * classCP.field1200;
         var4 -= -417087201 * classVR.field6558;
         var8 -= -1365423377 * classFM.field2313;
         var9 -= 1448483213 * classCP.field1200;
         var5 -= -417087201 * classVR.field6558;
         classCI.method1500(var6, var7, var4, var8, var9, var5, -99698685);
         var10.method11615(1823061428);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;III)V")
   @ObfuscatedName("cv")
   static void method4907(classUU var0, int var1, int var2, int var3) {
      if (!field2344) {
         int var4 = 50;
         int var5 = classFQ.method4948();
         int var6 = (1376644555 * field2347 - var1) * var4 / var3;
         int var7 = var4 * (field2346 * 1170217135 - var2) / var3;
         int var8 = (field2347 * 1376644555 - var1) * var5 / var3;
         int var9 = var5 * (field2346 * 1170217135 - var2) / var3;
         classUU var10 = classLX.method7419(var0, (byte)24);
         classUU.method11670(var10, -2080871105);
         float[] var11 = new float[3];
         var10.method11677(var6, var7, var4, var11, 1634822735);
         var6 = (int)var11[0];
         var7 = (int)var11[1];
         var4 = (int)var11[2];
         var10.method11677(var8, var9, var5, var11, 1634822735);
         var8 = (int)var11[0];
         var9 = (int)var11[1];
         var5 = (int)var11[2];
         var10.method11677(0.0F, 0.0F, 0.0F, var11, 1634822735);
         classFM.field2313 = (int)var11[0] * -1716444145;
         classCP.field1200 = (int)var11[1] * 1248579909;
         classVR.field6558 = -1913750817 * (int)var11[2];
         var6 -= -1365423377 * classFM.field2313;
         var7 -= 1448483213 * classCP.field1200;
         var4 -= -417087201 * classVR.field6558;
         var8 -= -1365423377 * classFM.field2313;
         var9 -= 1448483213 * classCP.field1200;
         var5 -= -417087201 * classVR.field6558;
         classCI.method1500(var6, var7, var4, var8, var9, var5, -99698685);
         var10.method11615(1647652673);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("cs")
   static void method4908(int var0, int var1, int var2, int var3, int var4, int var5) {
      client.field1069 = var0 * -1232180137;
      classDX.field1660 = -339051177 * var1;
      classDU.field1631 = -2072039747 * var2;
      classDB.field1373 = var3 * -1728875482;
      classYJ.field6992 = 1720455005 * var4;
      World.field1163 = -1730565422 * var5;
      classLS.field4217 = (var3 + var0) / 2 * 1069364959;
      classKT.field4020 = 2097852467 * ((var4 + var1) / 2);
      classHQ.field2866 = (var5 + var2) / 2 * -1040762067;
      classBY.field614 = (var3 - var0) / 2 * -447603648;
      classDK.field1540 = 15977997 * ((var4 - var1) / 2);
      GroundObject.field1809 = (var5 - var2) / 2 * -1577570207;
      classVI.field6511 = Math.abs(classBY.field614 * -2019632920) * 255059385;
      classJI.field3162 = Math.abs(classDK.field1540 * -969337396) * 1863713019;
      classEV.field1941 = Math.abs(-473056817 * GroundObject.field1809) * -36859613;
      classUZ.method11802(field2343, var3 - var0, var4 - var1, var5 - var2, 1882311380);
      field2343.method11817(-860587653);
      field2344 = true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bi")
   public static final void method4890(int var0, int var1) {
      field2347 = var0 * -750923805;
      field2346 = var1 * 1000488527;
      field2350 = true;
      field2348 = 0;
      field2349[0] = -1L;
      field2345[0] = Integer.MAX_VALUE;
      field2344 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;IIII)Z")
   @ObfuscatedName("co")
   static boolean method4912(Model var0, int var1, int var2, int var3, int var4) {
      AABB var5 = var0.method5118(var1);
      int var6 = 845296250 * var5.field2365 + var2;
      int var7 = var3 + var5.field2364 * -1027280203;
      int var8 = var5.field2367 * -81102275 + var4;
      int var9 = var5.field2366 * -985427365;
      int var10 = var5.field2363 * -623192411;
      int var11 = var5.field2368 * 1567972412;
      int var12 = 959526189 * classLS.field4217 - var6;
      int var13 = -621198286 * classKT.field4020 - var7;
      int var14 = 1574685861 * classHQ.field2866 - var8;
      if (Math.abs(var12) > classVI.field6511 * 47532510 + var9) {
         return false;
      } else if (Math.abs(var13) > 1266420275 * classJI.field3162 + var10) {
         return false;
      } else if (Math.abs(var14) > 1304241803 * classEV.field1941 + var11) {
         return false;
      } else if (Math.abs(var14 * 965988066 * classDK.field1540 - GroundObject.field1809 * -1947307103 * var13)
         > classEV.field1941 * 784477369 * var10 + classJI.field3162 * 1266420275 * var11) {
         return false;
      } else {
         return Math.abs(-1947307103 * GroundObject.field1809 * var12 - -331219525 * classBY.field614 * var14)
               > var9 * classEV.field1941 * -1075431906 + classVI.field6511 * 756191369 * var11
            ? false
            : Math.abs(578006589 * classBY.field614 * var13 - classDK.field1540 * -1981251036 * var12)
               <= var10 * 236915868 * classVI.field6511 + classJI.field3162 * 936831665 * var9;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("bt")
   public static int method4880(long var0) {
      return (int)(var0 >>> 20 & 4294967295L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("an")
   public static long method4871(int var0) {
      return field2349[var0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIILfp;)I")
   @ObfuscatedName("cc")
   static int method4914(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, AABB var9) {
      int var10 = var0 + AABB.method4917(var9, 1102065456);
      int var11 = var0 + var9.method4919(-1729968499);
      int var12 = var1 + var9.method4923(1324079504);
      int var13 = var1 + var9.method4927((byte)4);
      int var14 = var2 + var9.method4929(1833874923);
      int var15 = var2 + AABB.method4932(var9, (byte)18);
      float var16 = var6 - var3;
      float var17 = var7 - var4;
      float var18 = var8 - var5;
      float var19 = 0.0F;
      float var20 = 1.0F;
      float var21 = 1.0E-9F;
      if (Math.abs(var16) < 1.0E-9F) {
         if (var3 < var10 || var3 > var11) {
            return -1;
         }
      } else {
         float var22 = 1.0F / var16;
         float var23 = (var10 - var3) * var22;
         float var24 = var22 * (var11 - var3);
         if (var23 > var24) {
            float var25 = var23;
            var23 = var24;
            var24 = var25;
         }

         var19 = Math.max(var19, var23);
         var20 = Math.min(var20, var24);
         if (var19 > var20) {
            return -1;
         }
      }

      if (Math.abs(var17) < 1.0E-9F) {
         if (var4 < var12 || var4 > var13) {
            return -1;
         }
      } else {
         float var27 = 1.0F / var17;
         float var30 = var27 * (var12 - var4);
         float var33 = var27 * (var13 - var4);
         if (var30 > var33) {
            float var36 = var30;
            var30 = var33;
            var33 = var36;
         }

         var19 = Math.max(var19, var30);
         var20 = Math.min(var20, var33);
         if (var19 > var20) {
            return -1;
         }
      }

      if (Math.abs(var18) < 1.0E-9F) {
         if (var5 < var14 || var5 > var15) {
            return -1;
         }
      } else {
         float var28 = 1.0F / var18;
         float var31 = var28 * (var14 - var5);
         float var34 = var28 * (var15 - var5);
         if (var31 > var34) {
            float var37 = var31;
            var31 = var34;
            var34 = var37;
         }

         var19 = Math.max(var19, var31);
         var20 = Math.min(var20, var34);
         if (var19 > var20) {
            return -1;
         }
      }

      float var29 = var3 + var19 * var16;
      float var32 = var19 * var17 + var4;
      float var35 = var5 + var18 * var19;
      return (int)Math.sqrt(var35 * var35 + (var29 * var29 + var32 * var32));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIZII)J")
   @ObfuscatedName("ba")
   public static long method4886(int var0, int var1, int var2, int var3, boolean var4, int var5, int var6) {
      long var7 = (var1 & 127) << 0 | (var2 & 127) << 7 | (var0 & 3) << 14 | (var3 & 7) << 16 | (var5 & 4294967295L) << 20 | (var6 & 4095L) << 52;
      if (var4) {
         var7 |= 524288L;
      }

      return var7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("be")
   public static final void method4891(int var0, int var1) {
      field2347 = var0 * -1995294573;
      field2346 = var1 * -841094251;
      field2350 = true;
      field2348 = 0;
      field2349[0] = -1L;
      field2345[0] = -126015719;
      field2344 = false;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   static int method4867(int var0, int var1, int var2) {
      try {
         return (var1 << 7) + 128 * SceneTilePaint.field2279[var0];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fo.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/Object;IIB)Ljava/lang/String;")
   @ObfuscatedName("az")
   public static String method4866(Object[] var0, int var1, int var2, byte var3) {
      try {
         if (var2 == 0) {
            if (var3 >= 15) {
               throw new IllegalStateException();
            } else {
               return "";
            }
         } else if (var2 == 1) {
            if (var3 >= 15) {
               throw new IllegalStateException();
            } else {
               CharSequence var10 = (CharSequence)var0[var1];
               if (null == var10) {
                  if (var3 >= 15) {
                     throw new IllegalStateException();
                  } else {
                     return "null";
                  }
               } else {
                  return var10.toString();
               }
            }
         } else {
            int var4 = var2 + var1;
            int var5 = 0;

            for (int var6 = var1; var6 < var4; var6++) {
               if (var3 >= 15) {
                  throw new IllegalStateException();
               }

               CharSequence var7 = (CharSequence)var0[var6];
               if (var7 == null) {
                  if (var3 >= 15) {
                     throw new IllegalStateException();
                  }

                  var5 += 4;
               } else {
                  var5 += var7.length();
               }
            }

            StringBuilder var11 = new StringBuilder(var5);

            for (int var12 = var1; var12 < var4; var12++) {
               if (var3 >= 15) {
                  throw new IllegalStateException();
               }

               CharSequence var8 = (CharSequence)var0[var12];
               if (null == var8) {
                  if (var3 >= 15) {
                     throw new IllegalStateException();
                  }

                  var11.append("null");
               } else {
                  var11.append(var8);
               }
            }

            return var11.toString();
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "fo.az(" + ')');
      }
   }
}
