import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mk")
public class classMK {
   @ObfuscatedName("ax")
   static Thread field4520;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4519 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4518 = 5;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("ze")
   public static int method7637(classLH var0) {
      return classZD.method13965(var0.field4157, 2040537729);
   }

   classMK() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(Llw;I[B[BB)V")
   @ObfuscatedName("ap")
   static final void method7638(Widget var0, int var1, byte[] var2, byte[] var3, byte var4) {
      try {
         if (null == var0.field4310) {
            if (var4 == 1) {
               throw new IllegalStateException();
            }

            if (var2 == null) {
               return;
            }

            var0.field4310 = new byte[11][];
            var0.field4344 = new byte[11][];
            var0.field4368 = new int[11];
            var0.field4369 = new int[11];
         }

         var0.field4310[var1] = var2;
         if (null != var2) {
            if (var4 == 1) {
               throw new IllegalStateException();
            }

            var0.field4358 = true;
         } else {
            var0.field4358 = false;

            for (int var5 = 0; var5 < var0.field4310.length; var5++) {
               if (var4 == 1) {
                  return;
               }

               if (var0.field4310[var5] != null) {
                  if (var4 == 1) {
                     return;
                  }

                  var0.field4358 = true;
                  break;
               }
            }
         }

         var0.field4344[var1] = var3;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "mk.ap(" + ')');
      }
   }
}
