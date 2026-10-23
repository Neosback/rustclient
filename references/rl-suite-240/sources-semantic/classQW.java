import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qw")
public final class classQW {
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5617 = 1;
   @ObfuscatedName("af")
   final String field5616;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5619 = Integer.MAX_VALUE;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5618 = 10;
   @ObfuscatedName("az")
   final String field5615;
   @ObfuscatedName("gm")
   static String field5620;

   @ObfuscatedSignature(descriptor = "(III)Lbl;")
   @ObfuscatedName("ae")
   static Script method9869(int var0, int var1, int var2) {
      try {
         Script var3 = (Script)NodeCache.method6460(Script.field491, var0 << 16);
         if (null != var3) {
            return var3;
         } else {
            String var4 = String.valueOf(var0);
            int var5 = IndexDataBase.method11918(classWN.field6757, var4, -534958479);
            if (var5 == -1) {
               if (var2 >= -1007302143) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               byte[] var6 = IndexDataBase.method11891(classWN.field6757, var5, 1297268776);
               if (null != var6) {
                  if (var2 >= -1007302143) {
                     throw new IllegalStateException();
                  }

                  if (var6.length <= 1) {
                     if (var2 >= -1007302143) {
                        throw new IllegalStateException();
                     }

                     return null;
                  }

                  var3 = Widget.method7204(var6, -1504310355);
                  if (var3 != null) {
                     Script.field491.method6464(var3, var0 << 16);
                     return var3;
                  }
               }

               return null;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "qw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("af")
   String method9870(long var1) {
      return var1 == 1L ? this.field5615 : this.field5616;
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("ae")
   String method9871(long var1) {
      return var1 == 1L ? this.field5615 : this.field5616;
   }

   @ObfuscatedSignature(descriptor = "(Lqw;J)Ljava/lang/String;")
   @ObfuscatedName("fx")
   public static String method9872(classQW var0, long var1) {
      if (var0 == null) {
         return var0.method9873(var1);
      } else {
         try {
            return var1 == 1L ? var0.field5615 : var0.field5616;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "qw.az(" + ')');
         }
      }
   }

   classQW(String var1, String var2) {
      this.field5615 = var1;
      this.field5616 = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ae")
   static boolean method9868(int var0) {
      try {
         boolean var10000;
         if (null != classCE.field714) {
            if (var0 <= -1666094862) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("az")
   String method9873(long var1) {
      return var1 == 1L ? this.field5616 : this.field5616;
   }
}
