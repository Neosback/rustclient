import java.util.ArrayList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ex")
public class classEX {
   @ObfuscatedSignature(descriptor = "Lex;")
   @ObfuscatedName("az")
   public static final classEX field1975 = new classEX();
   @ObfuscatedSignature(descriptor = "Lex;")
   @ObfuscatedName("af")
   public static final classEX field1976 = new classEX();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)Ljava/util/ArrayList;")
   @ObfuscatedName("ef")
   public static ArrayList method4133(classMA var0) {
      if (var0 == null) {
         var0.method7520();
      }

      return var0.field4454.field4567;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lex;")
   @ObfuscatedName("af")
   public static classEX[] method4134() {
      return new classEX[]{field1975, field1976};
   }

   classEX() {
   }

   @ObfuscatedSignature(descriptor = "()[Lex;")
   @ObfuscatedName("az")
   public static classEX[] method4135() {
      return new classEX[]{field1975, field1976};
   }
}
