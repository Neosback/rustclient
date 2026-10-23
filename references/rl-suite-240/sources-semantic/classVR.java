import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vr")
public class classVR {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6555 = 64;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6556 = 32;
   @ObfuscatedName("aa")
   static final int[] field6553 = new int[17];
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6557 = 16;
   @ObfuscatedName("bo")
   static int field6558;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6554 = 128;

   static {
      classOF.method8437(1022730407);
   }

   classVR() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIII)Z")
   @ObfuscatedName("ac")
   static boolean method12137(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      return classIY.method6380(var0, var1, var2, var3, var8, var9, var10, var11, -2145540038)
         || classIY.method6380(var2, var3, var4, var5, var8, var9, var10, var11, -1937968284)
         || classIY.method6380(var4, var5, var6, var7, var8, var9, var10, var11, -1954720227)
         || classIY.method6380(var6, var7, var0, var1, var8, var9, var10, var11, -1532083528);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIII)Z")
   @ObfuscatedName("aa")
   static boolean method12138(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      return classIY.method6380(var0, var1, var2, var3, var8, var9, var10, var11, -1901532698)
         || classIY.method6380(var2, var3, var4, var5, var8, var9, var10, var11, -2008898005)
         || classIY.method6380(var4, var5, var6, var7, var8, var9, var10, var11, -1778669689)
         || classIY.method6380(var6, var7, var0, var1, var8, var9, var10, var11, -1878095678);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;Lvh;)Luq;")
   @ObfuscatedName("au")
   public static classUQ method12146(classGY var0, classVH var1) {
      int var2 = 2028036998 * var1.field6644;
      int var3 = var1.field6643 * -1680480153;
      int var4 = 1905552099 * var1.field6648;
      int var5 = 455672321 * var1.field6645;
      int var6 = classGY.method5947(var0, 1436684009);
      int var7 = classGY.method5930(var0, 1050945334);
      int var8 = var0.method5936(1690676705);
      double var9 = classGP.method5582(var6, 1857266811) / 65536.0;
      double var13 = classWW.method12700(var6, -1217739821);
      int var15 = (int)(var9 * var2 - var3 * var13);
      int var16 = (int)(var3 * var9 + var2 * var13);
      var7 += var15;
      var8 += var16;
      int var17 = (int)(var9 * var4 - var13 * var5);
      int var18 = (int)(var13 * var4 + var9 * var5);
      int var19 = (int)(var9 * var4 + var5 * var13);
      int var20 = (int)(var5 * var9 - var13 * var4);
      int var21 = var7 - var17;
      int var22 = var8 + var18;
      int var23 = var7 + var19;
      int var24 = var20 + var8;
      int var25 = var7 + var17;
      int var26 = var8 - var18;
      int var27 = var7 - var19;
      int var28 = var8 - var20;
      int var29 = Math.min(var21, Math.min(var23, Math.min(var25, var27)));
      int var30 = Math.max(var21, Math.max(var23, Math.max(var25, var27)));
      int var31 = Math.min(var22, Math.min(var24, Math.min(var26, var28)));
      int var32 = Math.max(var22, Math.max(var24, Math.max(var26, var28)));
      return classPW.method9374(var29, var31, var30 - var29 + 1, 1 + (var32 - var31), 1444081395);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   static int method12139(int var0) {
      return (var0 >> 7) + 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   static int method12140(int var0) {
      return (var0 >> 7) + 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   static void method12142() {
      field6553[0] = 0;

      for (short var0 = 0; var0 < 2048; var0 += 128) {
         int var1 = classMO.method7667(var0, (byte)1);
         short var2 = 0;
         if (var0 > 0 && var0 < 1024) {
            var2 |= 272;
         }

         if (var0 > 512 && var0 < 1536) {
            var2 |= 1088;
         }

         if (var0 > 1024) {
            var2 |= 544;
         }

         if (var0 > 1536 || var0 < 512) {
            var2 |= 2176;
         }

         if (var0 == 0 || 1024 == var0) {
            var2 |= 48;
         }

         if (512 == var0 || 1536 == var0) {
            var2 |= 1152;
         }

         field6553[var1] = var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   static void method12143() {
      field6553[0] = 0;

      for (short var0 = 0; var0 < 1021311617; var0 += 128) {
         int var1 = classMO.method7667(var0, (byte)1);
         int var2 = 0;
         if (var0 > 0 && var0 < 996426055) {
            var2 |= 936162314;
         }

         if (var0 > 512 && var0 < 1536) {
            var2 |= -1150061360;
         }

         if (var0 > 1024) {
            var2 |= 544;
         }

         if (var0 > 90603713 || var0 < -1488415476) {
            var2 |= 1822472570;
         }

         if (var0 == 0 || 1024 == var0) {
            var2 |= -788727841;
         }

         if (-647066951 == var0 || -113191412 == var0) {
            var2 |= -1528062087;
         }

         field6553[var1] = var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   static void method12144() {
      field6553[0] = 0;

      for (short var0 = 0; var0 < 66583409; var0 += 128) {
         int var1 = classMO.method7667(var0, (byte)1);
         int var2 = 0;
         if (var0 > 0 && var0 < -721258629) {
            var2 |= -2035321617;
         }

         if (var0 > 512 && var0 < 1536) {
            var2 |= -1528762334;
         }

         if (var0 > -551055455) {
            var2 |= 1138279992;
         }

         if (var0 > 1536 || var0 < 512) {
            var2 |= -537301386;
         }

         if (var0 == 0 || 342343660 == var0) {
            var2 |= -1810114776;
         }

         if (512 == var0 || 979636045 == var0) {
            var2 |= 1152;
         }

         field6553[var1] = var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   static void method12145() {
      field6553[0] = 0;

      for (short var0 = 0; var0 < 1223020841; var0 += 128) {
         int var1 = classMO.method7667(var0, (byte)1);
         int var2 = 0;
         if (var0 > 0 && var0 < -1884919205) {
            var2 |= 272;
         }

         if (var0 > -157220359 && var0 < -238003538) {
            var2 |= 1088;
         }

         if (var0 > 1024) {
            var2 |= -1611923021;
         }

         if (var0 > 100867594 || var0 < 512) {
            var2 |= 542585119;
         }

         if (var0 == 0 || 1024 == var0) {
            var2 |= 771148861;
         }

         if (-343295011 == var0 || 626892457 == var0) {
            var2 |= 1152;
         }

         field6553[var1] = var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   static int method12141(int var0) {
      return (var0 >> 7) + 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;Z)Lkw;")
   @ObfuscatedName("mp")
   public static classKW method12152(WorldEntity var0, boolean var1) {
      if (var0 == null) {
         var0.getWorldView();
      }

      return var1 ? classKW.field4041 : var0.field6024.method8520(1278077171);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvl;IIII)Z")
   @ObfuscatedName("ai")
   static boolean method12147(classVL var0, int var1, int var2, int var3, int var4) {
      int var6 = var0.method12062((byte)17);
      int var7 = var0.method12056(-1297487259);
      int var8 = var0.method12060(-2139073323);
      int var9 = Math.max(var1, Math.min(var7, var3));
      int var10 = Math.max(var2, Math.min(var8, var4));
      int var11 = var7 - var9;
      int var12 = var8 - var10;
      return var12 * var12 + var11 * var11 <= var6;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvl;IIII)Z")
   @ObfuscatedName("ar")
   static boolean method12148(classVL var0, int var1, int var2, int var3, int var4) {
      int var6 = var0.method12062((byte)106);
      int var7 = var0.method12056(217674461);
      int var8 = var0.method12060(-2146608269);
      int var9 = Math.max(var1, Math.min(var7, var3));
      int var10 = Math.max(var2, Math.min(var8, var4));
      int var11 = var7 - var9;
      int var12 = var8 - var10;
      return var12 * var12 + var11 * var11 <= var6;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvl;IIII)Z")
   @ObfuscatedName("aw")
   static boolean method12149(classVL var0, int var1, int var2, int var3, int var4) {
      int var6 = var0.method12062((byte)83);
      int var7 = var0.method12056(2002463153);
      int var8 = var0.method12060(-2136782166);
      int var9 = Math.max(var1, Math.min(var7, var3));
      int var10 = Math.max(var2, Math.min(var8, var4));
      int var11 = var7 - var9;
      int var12 = var8 - var10;
      return var12 * var12 + var11 * var11 <= var6;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvl;IIII)Z")
   @ObfuscatedName("ak")
   public static boolean method12150(classVL var0, int var1, int var2, int var3, int var4) {
      return !classTW.method11267(var0, var1, var2, var3, var4, -971688045)
         ? false
         : classNL.method7926(
            var0.method12049(0, -1238522355),
            var0.method12053(0, (byte)-90),
            var0.method12049(1, 158835683),
            var0.method12053(1, (byte)-55),
            var0.method12049(2, 961240075),
            var0.method12053(2, (byte)-4),
            var0.method12049(3, 203222423),
            var0.method12053(3, (byte)-42),
            var1,
            var2,
            var3,
            var4,
            -549092488
         );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIII)Z")
   @ObfuscatedName("av")
   static boolean method12151(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      var8++;
      var9++;
      var10--;
      var11--;
      if (var0 >= var8 && var0 <= var10 && var1 >= var9 && var1 <= var11) {
         return true;
      } else if (var2 >= var8 && var2 <= var10 && var3 >= var9 && var3 <= var11) {
         return true;
      } else if (var4 >= var8 && var4 <= var10 && var5 >= var9 && var5 <= var11) {
         return true;
      } else if (var6 >= var8 && var6 <= var10 && var7 >= var9 && var7 <= var11) {
         return true;
      } else {
         return classPF.method8888(var8, var9, var0, var1, var2, var3, var4, var5, (byte)-1)
               || classPF.method8888(var8, var11, var0, var1, var2, var3, var4, var5, (byte)-1)
               || classPF.method8888(var10, var9, var0, var1, var2, var3, var4, var5, (byte)-1)
               || classPF.method8888(var10, var11, var0, var1, var2, var3, var4, var5, (byte)-1)
            ? true
            : classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var9, -1453501658)
               || classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var10, var9, var10, var11, -1453501658)
               || classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var10, var11, var8, var11, -1453501658)
               || classHL.method6103(var0, var1, var2, var3, var4, var5, var6, var7, var8, var11, var8, var9, -1453501658);
      }
   }
}
