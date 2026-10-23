import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ra")
public final class classRA {
   @ObfuscatedName("az")
   final int field5629;
   @ObfuscatedName("af")
   final boolean field5628;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5630 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field5631 = 10;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;F)V")
   @ObfuscatedName("kj")
   public static void method9975(classUZ var0, float var1) {
      if (var0 == null) {
         var0.toString();
      }

      var0.field6425 *= var1;
      var0.field6423 *= var1;
      var0.field6424 *= var1;
   }

   @ObfuscatedSignature(descriptor = "(I)[Lsk;")
   @ObfuscatedName("ag")
   static classSK[] method9974(int var0) {
      try {
         return new classSK[]{classSK.field5966, classSK.field5971, classSK.field5968, classSK.field5969, classSK.field5970, classSK.field5967};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ra.ag(" + ')');
      }
   }

   classRA(int var1, boolean var2) {
      this.field5629 = var1 * -593767025;
      this.field5628 = var2;
   }
}
