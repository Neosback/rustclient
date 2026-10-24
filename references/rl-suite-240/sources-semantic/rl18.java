import java.io.File;
import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl18")
class rl18 implements Comparator {
   @ObfuscatedSignature(descriptor = "(Lrl17;)I")
   @ObfuscatedName("kv")
   public int method10092(rl17 var1) {
      int var2 = var1.field5710 * 8 + 4 - this.field5712.field2146 << 7;
      int var3 = var1.field5705 * 8 + 4 - this.field5712.field2146 << 7;
      return (var2 - this.field5712.field2116) * (var2 - this.field5712.field2116) + (var3 - this.field5712.field2114) * (var3 - this.field5712.field2114);
   }

   @ObfuscatedSignature(descriptor = "(Lrl17;Lrl17;)I")
   @ObfuscatedName("zi")
   public int method10093(rl17 var1, rl17 var2) {
      return Integer.compare(this.method10092(var1), this.method10092(var2));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laar;)Ljava/io/File;")
   @ObfuscatedName("ug")
   public static File method10091(classAAR var0) {
      return var0.field96;
   }

   public rl18(Scene var1) {
      this.field5712 = var1;
   }
}
