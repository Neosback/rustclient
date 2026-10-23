import java.util.concurrent.ThreadPoolExecutor;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xn")
public final class classXN {
   @ObfuscatedName("af")
   static ThreadPoolExecutor field6889;

   classXN() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;)I")
   @ObfuscatedName("ae")
   public static int method12908(CharSequence var0) {
      int var1 = var0.length();
      int var2 = 0;

      for (int var3 = 0; var3 < var1; var3++) {
         char var4 = var0.charAt(var3);
         if (var4 <= 127) {
            var2++;
         } else if (var4 <= 2047) {
            var2 += 2;
         } else {
            var2 += 3;
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(II)Lpa;")
   @ObfuscatedName("af")
   public static classPA method12910(int var0, int var1) {
      try {
         classPA var2 = (classPA)NodeCache.method6460(classPA.field5201, var0);
         if (null != var2) {
            if (var1 <= 1503693208) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classPA.field5206.method11942(19, var0, -1983156126);
            var2 = new classPA(new classXY(var3));
            classPA.field5201.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xn.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BII)Ljava/lang/String;")
   @ObfuscatedName("ag")
   public static String method12911(byte[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      int var4 = 0;
      int var5 = var1;
      int var6 = var2 + var1;

      while (var5 < var6) {
         int var7 = var0[var5++] & 255;
         int var8;
         if (var7 < 128) {
            if (0 == var7) {
               var8 = 65533;
            } else {
               var8 = var7;
            }
         } else if (var7 < 192) {
            var8 = 65533;
         } else if (var7 < 224) {
            if (var5 < var6 && 128 == (var0[var5] & 192)) {
               var8 = (var7 & 31) << 6 | var0[var5++] & 63;
               if (var8 < 128) {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else if (var7 < 240) {
            if (var5 + 1 < var6 && 128 == (var0[var5] & 192) && 128 == (var0[1 + var5] & 192)) {
               var8 = (var7 & 15) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
               if (var8 < 2048) {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else if (var7 < 248) {
            if (2 + var5 < var6 && 128 == (var0[var5] & 192) && 128 == (var0[var5 + 1] & 192) && 128 == (var0[var5 + 2] & 192)) {
               var8 = (var7 & 7) << 18 | (var0[var5++] & 63) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
               if (var8 >= 65536 && var8 <= 1114111) {
                  var8 = 65533;
               } else {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else {
            var8 = 65533;
         }

         var3[var4++] = (char)var8;
      }

      return new String(var3, 0, var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;)I")
   @ObfuscatedName("ab")
   public static int method12909(CharSequence var0) {
      int var1 = var0.length();
      int var2 = 0;

      for (int var3 = 0; var3 < var1; var3++) {
         char var4 = var0.charAt(var3);
         if (var4 <= -820644025) {
            var2++;
         } else if (var4 <= 2132470776) {
            var2 += 2;
         } else {
            var2 += 3;
         }
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BII)Ljava/lang/String;")
   @ObfuscatedName("as")
   public static String method12912(byte[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      int var4 = 0;
      int var5 = var1;
      int var6 = var2 + var1;

      while (var5 < var6) {
         int var7 = var0[var5++] & 255;
         int var8;
         if (var7 < 128) {
            if (0 == var7) {
               var8 = 65533;
            } else {
               var8 = var7;
            }
         } else if (var7 < 192) {
            var8 = 65533;
         } else if (var7 < 224) {
            if (var5 < var6 && 128 == (var0[var5] & 192)) {
               var8 = (var7 & 31) << 6 | var0[var5++] & 63;
               if (var8 < 128) {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else if (var7 < 240) {
            if (var5 + 1 < var6 && 128 == (var0[var5] & 192) && 128 == (var0[1 + var5] & 192)) {
               var8 = (var7 & 15) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
               if (var8 < 2048) {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else if (var7 < 248) {
            if (2 + var5 < var6 && 128 == (var0[var5] & 192) && 128 == (var0[var5 + 1] & 192) && 128 == (var0[var5 + 2] & 192)) {
               var8 = (var7 & 7) << 18 | (var0[var5++] & 63) << 12 | (var0[var5++] & 63) << 6 | var0[var5++] & 63;
               if (var8 >= 65536 && var8 <= 1114111) {
                  var8 = 65533;
               } else {
                  var8 = 65533;
               }
            } else {
               var8 = 65533;
            }
         } else {
            var8 = 65533;
         }

         var3[var4++] = (char)var8;
      }

      return new String(var3, 0, var4);
   }
}
