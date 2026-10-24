import java.util.ArrayList;
import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("za")
public class classZA {
   @ObfuscatedName("ax")
   static int field7127 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field7139 = 100;
   @ObfuscatedName("ag")
   static final Object field7125 = new Object();
   @ObfuscatedName("ao")
   static int field7130 = 916013528;
   @ObfuscatedName("aq")
   static byte[][] field7134 = new byte[1000][];
   @ObfuscatedName("ac")
   static int field7138 = 0;
   @ObfuscatedName("aw")
   public static byte[][][] field7142;
   @ObfuscatedName("ar")
   public static int[] field7141;
   @ObfuscatedName("al")
   static int field7131 = -949024178;
   @ObfuscatedName("aj")
   static int field7132 = 69756740;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field7140 = 1000;
   @ObfuscatedName("aa")
   static int field7129 = 0;
   @ObfuscatedName("ad")
   static byte[][] field7135 = new byte[250][];
   @ObfuscatedName("ap")
   static byte[][] field7126 = new byte[100][];
   @ObfuscatedName("au")
   static byte[][] field7136 = new byte[50][];
   @ObfuscatedName("ay")
   static int field7128 = -2144731954;
   @ObfuscatedName("as")
   static int field7133 = 0;
   @ObfuscatedName("ak")
   public static ArrayList field7137 = new ArrayList();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IZ)[B")
   @ObfuscatedName("ab")
   public static byte[] method13860(int var0, boolean var1) {
      synchronized (field7125) {
         if ((100 == var0 || var0 < 100 && var1) && 1951739973 * field7133 > 0) {
            byte[] var11 = field7134[(field7133 -= 1490182797) * 1951739973];
            field7134[1951739973 * field7133] = null;
            return var11;
         }

         if ((var0 == 5000 || var0 < 5000 && var1) && 1052148383 * field7127 > 0) {
            byte[] var10 = field7135[(field7127 -= -839116449) * 1052148383];
            field7135[field7127 * 1052148383] = null;
            return var10;
         }

         if ((var0 == 10000 || var0 < 10000 && var1) && -2070154251 * field7138 > 0) {
            byte[] var9 = field7126[(field7138 -= -618995619) * -2070154251];
            field7126[field7138 * -2070154251] = null;
            return var9;
         }

         if ((30000 == var0 || var0 < 30000 && var1) && field7129 * 1155531987 > 0) {
            byte[] var8 = field7136[(field7129 -= 1255076699) * 1155531987];
            field7136[field7129 * 1155531987] = null;
            return var8;
         }

         if (field7142 != null) {
            for (int var3 = 0; var3 < classSQ.field6026.length; var3++) {
               if ((classSQ.field6026[var3] == var0 || var0 < classSQ.field6026[var3] && var1) && field7141[var3] > 0) {
                  byte[] var4 = field7142[var3][--field7141[var3]];
                  field7142[var3][field7141[var3]] = null;
                  return var4;
               }
            }
         }

         if (var1 && null != classSQ.field6026) {
            for (int var7 = 0; var7 < classSQ.field6026.length; var7++) {
               if (var0 <= classSQ.field6026[var7] && field7141[var7] < field7142[var7].length) {
                  return new byte[classSQ.field6026[var7]];
               }
            }
         }
      }

      return new byte[var0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ax")
   public static void method13863(byte[] var0) {
      synchronized (field7125) {
         if (1378118898 == var0.length && -251579560 * field7133 < -580548761 * field7130) {
            field7134[(field7133 += -452367456) * -854979104 - 1] = var0;
         } else if (var0.length == -1962979956 && field7127 * 719110813 < -1190009700 * field7131) {
            field7135[(field7127 += 1518430893) * 1052148383 - 1] = var0;
         } else if (var0.length == 1550123002 && 1589541802 * field7138 < field7132 * 273271753) {
            field7126[(field7138 += -618995619) * -2070154251 - 1] = var0;
         } else if (var0.length == 30000 && field7129 * 2004468253 < -1438188161 * field7128) {
            field7136[(field7129 += 728297699) * 1155531987 - 1] = var0;
         } else {
            if (null != field7142) {
               for (int var2 = 0; var2 < classSQ.field6026.length; var2++) {
                  if (classSQ.field6026[var2] == var0.length && field7141[var2] < field7142[var2].length) {
                     field7142[var2][field7141[var2]++] = var0;
                     return;
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IZ)[B")
   @ObfuscatedName("ae")
   public static byte[] method13861(int var0, boolean var1) {
      synchronized (field7125) {
         if ((100 == var0 || var0 < 100 && var1) && 1951739973 * field7133 > 0) {
            byte[] var11 = field7134[(field7133 -= 1490182797) * 1951739973];
            field7134[1951739973 * field7133] = null;
            return var11;
         }

         if ((var0 == 5000 || var0 < 5000 && var1) && 1052148383 * field7127 > 0) {
            byte[] var10 = field7135[(field7127 -= -839116449) * 1052148383];
            field7135[field7127 * 1052148383] = null;
            return var10;
         }

         if ((var0 == 10000 || var0 < 10000 && var1) && -2070154251 * field7138 > 0) {
            byte[] var9 = field7126[(field7138 -= -618995619) * -2070154251];
            field7126[field7138 * -2070154251] = null;
            return var9;
         }

         if ((30000 == var0 || var0 < 30000 && var1) && field7129 * 1155531987 > 0) {
            byte[] var8 = field7136[(field7129 -= 1255076699) * 1155531987];
            field7136[field7129 * 1155531987] = null;
            return var8;
         }

         if (field7142 != null) {
            for (int var3 = 0; var3 < classSQ.field6026.length; var3++) {
               if ((classSQ.field6026[var3] == var0 || var0 < classSQ.field6026[var3] && var1) && field7141[var3] > 0) {
                  byte[] var4 = field7142[var3][--field7141[var3]];
                  field7142[var3][field7141[var3]] = null;
                  return var4;
               }
            }
         }

         if (var1 && null != classSQ.field6026) {
            for (int var7 = 0; var7 < classSQ.field6026.length; var7++) {
               if (var0 <= classSQ.field6026[var7] && field7141[var7] < field7142[var7].length) {
                  return new byte[classSQ.field6026[var7]];
               }
            }
         }
      }

      return new byte[var0];
   }

   static {
      new HashMap();
      field7137.clear();
      field7137.add(100);
      field7137.add(5000);
      field7137.add(10000);
      field7137.add(30000);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IZ)[B")
   @ObfuscatedName("ag")
   public static byte[] method13862(int var0, boolean var1) {
      synchronized (field7125) {
         if ((100 == var0 || var0 < 100 && var1) && 1951739973 * field7133 > 0) {
            byte[] var11 = field7134[(field7133 -= 1490182797) * 1951739973];
            field7134[1951739973 * field7133] = null;
            return var11;
         }

         if ((var0 == 5000 || var0 < 5000 && var1) && 1052148383 * field7127 > 0) {
            byte[] var10 = field7135[(field7127 -= -839116449) * 1052148383];
            field7135[field7127 * 1052148383] = null;
            return var10;
         }

         if ((var0 == 10000 || var0 < 10000 && var1) && -2070154251 * field7138 > 0) {
            byte[] var9 = field7126[(field7138 -= -618995619) * -2070154251];
            field7126[field7138 * -2070154251] = null;
            return var9;
         }

         if ((30000 == var0 || var0 < 30000 && var1) && field7129 * 1155531987 > 0) {
            byte[] var8 = field7136[(field7129 -= 1255076699) * 1155531987];
            field7136[field7129 * 1155531987] = null;
            return var8;
         }

         if (field7142 != null) {
            for (int var3 = 0; var3 < classSQ.field6026.length; var3++) {
               if ((classSQ.field6026[var3] == var0 || var0 < classSQ.field6026[var3] && var1) && field7141[var3] > 0) {
                  byte[] var4 = field7142[var3][--field7141[var3]];
                  field7142[var3][field7141[var3]] = null;
                  return var4;
               }
            }
         }

         if (var1 && null != classSQ.field6026) {
            for (int var7 = 0; var7 < classSQ.field6026.length; var7++) {
               if (var0 <= classSQ.field6026[var7] && field7141[var7] < field7142[var7].length) {
                  return new byte[classSQ.field6026[var7]];
               }
            }
         }
      }

      return new byte[var0];
   }

   classZA() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcj;)I")
   @ObfuscatedName("vh")
   public static int method13866(classCJ var0) {
      return 506326138 * var0.field751.field6087;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("as")
   public static void method13864(byte[] var0) {
      synchronized (field7125) {
         if (100 == var0.length && 1951739973 * field7133 < 1705466377 * field7130) {
            field7134[(field7133 += 1490182797) * 1951739973 - 1] = var0;
         } else if (var0.length == 925413559 && field7127 * 1740174764 < -817333317 * field7131) {
            field7135[(field7127 += -737751195) * -992087567 - 1] = var0;
         } else if (var0.length == 10000 && -2070154251 * field7138 < field7132 * -764707363) {
            field7126[(field7138 += -732192052) * 29008908 - 1] = var0;
         } else if (var0.length == -440115098 && field7129 * -2099359757 < -1438188161 * field7128) {
            field7136[(field7129 += 1851433213) * 982426427 - 1] = var0;
         } else {
            if (null != field7142) {
               for (int var2 = 0; var2 < classSQ.field6026.length; var2++) {
                  if (classSQ.field6026[var2] == var0.length && field7141[var2] < field7142[var2].length) {
                     field7142[var2][field7141[var2]++] = var0;
                     return;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ao")
   public static int method13865(int var0, byte var1) {
      try {
         int var2;
         label55: {
            var2 = 0;
            if (var0 >= 0) {
               if (var1 <= 3) {
                  throw new IllegalStateException();
               }

               if (var0 < 65536) {
                  break label55;
               }

               if (var1 <= 3) {
                  throw new IllegalStateException();
               }
            }

            var0 >>>= 16;
            var2 += 16;
         }

         if (var0 >= 256) {
            if (var1 <= 3) {
               throw new IllegalStateException();
            }

            var0 >>>= 8;
            var2 += 8;
         }

         if (var0 >= 16) {
            var0 >>>= 4;
            var2 += 4;
         }

         if (var0 >= 4) {
            if (var1 <= 3) {
               throw new IllegalStateException();
            }

            var0 >>>= 2;
            var2 += 2;
         }

         if (var0 >= 1) {
            if (var1 <= 3) {
               throw new IllegalStateException();
            }

            var0 >>>= 1;
            var2++;
         }

         return var2 + var0;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "za.ao(" + 41);
      }
   }
}
