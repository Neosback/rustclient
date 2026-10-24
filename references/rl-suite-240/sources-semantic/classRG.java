import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rg")
public interface classRG {
   @ObfuscatedSignature(descriptor = "(Laj;)Z")
   @ObfuscatedName("ey")
   static boolean method10000(classAJ var0) {
      return var0 == null ? var0.method393() : var0.field188;
   }

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("aec")
   classAAE vmethod257();

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("aen")
   classAAE vmethod256();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqy;Lwy;)V")
   @ObfuscatedName("dh")
   static void method10001(classQY var0, classWY var1) {
      if (var0 == null) {
         var0.method9916();
      } else {
         var0.method9911(var1, 1.0F, 2107764056);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Laae;")
   @ObfuscatedName("iq")
   classAAE vmethod255(byte var1);
}
