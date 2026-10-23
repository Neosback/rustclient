import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ty")
public interface classTY extends classTU {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzp;)I")
   @ObfuscatedName("mf")
   static int method11270(classZP var0) {
      return 203459897 * var0.field7232;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvh;II)I")
   @ObfuscatedName("rf")
   static int method11271(classVH var0, int var1, int var2) {
      return var0.field6504[var1][var2];
   }
}
