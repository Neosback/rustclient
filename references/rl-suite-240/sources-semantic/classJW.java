import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jw")
public class classJW {
   @ToRemove(unused = "true")
   @ObfuscatedName("bq")
   public static final int field3490 = 69;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   public static final int field3489 = 25;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;IILjava/lang/String;)V")
   @ObfuscatedName("ui")
   public static void method6455(Widget var0, int var1, int var2, String var3) {
      if (var0.field4351 == null || var0.field4351.length <= var1) {
         String[][] var4 = new String[var1 + 1][];
         if (null != var0.field4351) {
            System.arraycopy(var0.field4351, 0, var4, 0, var0.field4351.length);
         }

         var0.field4351 = var4;
      }

      if (null == var0.field4351[var1] || var0.field4351[var1].length <= var2) {
         String[] var5 = new String[var2 + 1];
         if (var0.field4351[var1] != null) {
            System.arraycopy(var0.field4351[var1], 0, var5, 0, var0.field4351[var1].length);
         }

         var0.field4351[var1] = var5;
      }

      var0.field4351[var1][var2] = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("af")
   public static final void method6450(long var0) {
      if (var0 > 0L) {
         if (0L == var0 % 10L) {
            long var2 = var0 - 1L;

            try {
               Thread.sleep(var2);
            } catch (InterruptedException var7) {
            }

            try {
               Thread.sleep(1L);
            } catch (InterruptedException var6) {
            }
         } else {
            try {
               Thread.sleep(var0);
            } catch (InterruptedException var5) {
            }
         }
      }
   }

   classJW() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ae")
   public static final void method6451(long var0) {
      if (var0 > 0L) {
         if (0L == var0 % 10L) {
            long var2 = var0 - 1L;

            try {
               Thread.sleep(var2);
            } catch (InterruptedException var7) {
            }

            try {
               Thread.sleep(1L);
            } catch (InterruptedException var6) {
            }
         } else {
            try {
               Thread.sleep(var0);
            } catch (InterruptedException var5) {
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   public static boolean method6453(int var0, int var1) {
      try {
         if (var0 >= classKB.field3513.field3536 * 78062377) {
            if (var1 <= -597053532) {
               throw new IllegalStateException();
            }

            if (var0 <= classKB.field3516.field3536 * 78062377) {
               return true;
            }

            if (var1 <= -597053532) {
               throw new IllegalStateException();
            }
         }

         if (var0 != classKB.field3517.field3536 * 78062377) {
            return false;
         } else if (var1 <= -597053532) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "jw.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)Z")
   @ObfuscatedName("ae")
   public static boolean method6454(ClientPreferences var0) {
      return var0.field1339;
   }

   @ObfuscatedSignature(descriptor = "(IB)Log;")
   @ObfuscatedName("af")
   public static classOG method6452(int var0, byte var1) {
      try {
         switch (var0 >> 18 & 3) {
            case 0:
               return classOG.field4860;
            case 1:
               return classOG.field4861;
            case 2:
               return classOG.field4862;
            default:
               throw new IllegalArgumentException("invalid scope");
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "jw.af(" + ')');
      }
   }
}
