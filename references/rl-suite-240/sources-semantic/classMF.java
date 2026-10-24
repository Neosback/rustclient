import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mf")
public interface classMF {
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   int vmethod527();

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   int vmethod528(int var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbz;)V")
   @ObfuscatedName("tp")
   static void method7606(classBZ var0) {
      if (var0 == null) {
         var0.method1379();
      } else {
         var0.field623 = classLJ.field4172;
         var0.field631 = -2126001287;
         var0.field633 = 1502303207;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   int vmethod529();
}
