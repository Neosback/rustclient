import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vn")
public interface classVN {
   @ObfuscatedSignature(descriptor = "()Lvt;")
   @ObfuscatedName("ag")
   classVT vmethod624();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcg;)V")
   @ObfuscatedName("lx")
   static void method12077(MessageNode var0) {
      if (var0 == null) {
         var0.method1478();
      } else {
         var0.field723 = classRC.field5638;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lvt;")
   @ObfuscatedName("az")
   classVT vmethod625(int var1);
}
