import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ut")
public interface classUT extends Iterable {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqg;)I")
   @ObfuscatedName("ah")
   static int method11605(classQG var0) {
      return -1998439311 * var0.field5531;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;B)V")
   @ObfuscatedName("ms")
   static void method11606(Model var0, byte var1) {
      if (var0 == null) {
         var0.method5292();
      }

      var0.field2423 = var1;
   }
}
