import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl16")
class rl16 implements Comparator {
   @ObfuscatedSignature(descriptor = "Lfv;")
   @ObfuscatedName("pw")
   public static Projection field5700;

   @ObfuscatedSignature(descriptor = "(Lfb;Lfb;)I")
   @ObfuscatedName("ed")
   public int method10089(GameObject var1, GameObject var2) {
      return Integer.compare(method10090(var2), method10090(var1));
   }

   @ObfuscatedSignature(descriptor = "(Lfb;)I")
   @ObfuscatedName("ao")
   public static int method10090(GameObject var0) {
      int var1 = (int)field5700.project(var0.field2193 * -1405731525, var0.field2188 * 1269801851, var0.field2194 * -1706390585)[2];
      if (var0.field2198 == classDC.field1391) {
         var1 -= 12;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)V")
   @ObfuscatedName("kx")
   public static void method10088(RenderOverview var0) {
      if (var0 == null) {
         var0.method12659();
      }

      var0.field6691 = 739603646;
   }

   public rl16() {
   }
}
