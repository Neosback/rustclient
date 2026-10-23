import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ne")
public final class classNE {
   @ObfuscatedSignature(descriptor = "Lzv;")
   @ObfuscatedName("dh")
   public static classZV field4665;
   @ToRemove(unused = "true")
   @ObfuscatedName("ci")
   public static final int field4664 = 96;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;Llw;Llw;II)V")
   @ObfuscatedName("zo")
   public static void method7873(classVW var0, Widget var1, Widget var2, int var3, int var4) {
      if (var0 == null) {
         var0.method12258();
      } else {
         var0.field6628 = var1;
         var0.field6620 = var2;
         var0.field6621 = var3 * 897114003;
         var0.field6622 = var4 * -1083081708;
         var0.field6632 = 0;
         var0.field6624 = false;
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Lpn;")
   @ObfuscatedName("af")
   public static classPN method7875(int var0, byte var1) {
      try {
         classPN var2 = (classPN)NodeCache.method6460(classPN.field5348, var0);
         if (null != var2) {
            if (var1 == 0) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classPN.field5360.method11942(11, var0, -908610794);
            var2 = new classPN(new classXY(var3), var0);
            classPN.field5348.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ne.af(" + ')');
      }
   }

   classNE() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   public static int method7874(int var0, int var1) {
      try {
         return classKG.method6508(-2041997219) - var0 & classCS.method2567(-1520924805);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ne.as(" + 41);
      }
   }
}
