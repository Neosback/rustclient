import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xf")
public interface classXF {
   @ObfuscatedSignature(descriptor = "()Lxt;")
   @ObfuscatedName("af")
   classXT vmethod647();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Loi;)I")
   @ObfuscatedName("ro")
   static int method12817(WorldEntityConfig var0) {
      return var0.field4883 * 594099331;
   }

   @ObfuscatedSignature(descriptor = "(B)Lxt;")
   @ObfuscatedName("az")
   classXT vmethod648(byte var1);
}
