import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pb")
public class classPB {
   @ObfuscatedName("ag")
   String field5208 = null;
   @ObfuscatedName("as")
   List field5209;
   @ObfuscatedSignature(descriptor = "Lpo;")
   @ObfuscatedName("ax")
   EntityOps field5210;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)Z")
   @ObfuscatedName("nt")
   public static boolean method8869(classQC var0) {
      if (var0 == null) {
         var0.method9511();
      }

      return false;
   }

   classPB(String var1) {
      if (null != var1 && !var1.isEmpty()) {
         this.field5208 = var1;
      }
   }
}
