import java.awt.datatransfer.Clipboard;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lf")
public class classLF {
   @ObfuscatedSignature(descriptor = "Llr;")
   @ObfuscatedName("az")
   static classLR field4131;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4125 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   public static final int field4126 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final String field4130 = "main_file_cache.dat2";
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   public static final int field4127 = 512;
   @ToRemove(unused = "true")
   @ObfuscatedName("ci")
   public static final int field4128 = -1;
   @ObfuscatedName("ne")
   static int field4129;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Ljava/awt/datatransfer/Clipboard;)V")
   @ObfuscatedName("ai")
   public static void method6829(classLH var0, Clipboard var1) {
      if (var0 == null) {
         var0.method7040();
      }

      if (var0.method7089(2012791869)) {
         var0.method6995(var1, -887645341);
         var0.method6924(455461300);
      }
   }

   classLF() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(ZZI)Lyz;")
   @ObfuscatedName("ab")
   static IndexedSprite method6827(boolean var0, boolean var1, int var2) {
      try {
         IndexedSprite var10000;
         if (var0) {
            if (var1) {
               if (var2 <= 26835265) {
                  throw new IllegalStateException();
               }

               var10000 = classQN.field5571;
            } else {
               var10000 = classEK.field1777;
            }
         } else if (var1) {
            if (var2 <= 26835265) {
               throw new IllegalStateException();
            }

            var10000 = classHC.field2766;
         } else {
            var10000 = classCK.field778;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lf.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ac")
   static void method6828(classXY var0, int var1) {
      try {
         while (true) {
            int var2 = var0.method13099(170180413);
            if (var2 == 0) {
               if (var1 <= 188513080) {
                  throw new IllegalStateException();
               }
            } else {
               if (1 != var2) {
                  if (var2 > 49) {
                     continue;
                  }

                  if (var1 <= 188513080) {
                     throw new IllegalStateException();
                  }

                  var0.method13104((byte)16);
                  continue;
               }

               if (var1 <= 188513080) {
                  throw new IllegalStateException();
               }

               var0.method13095(-346779531);
            }

            return;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lf.ac(" + ')');
      }
   }
}
