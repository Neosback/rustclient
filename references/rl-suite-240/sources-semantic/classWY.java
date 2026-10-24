import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wy")
public class classWY {
   @ObfuscatedSignature(descriptor = "Lwy;")
   @ObfuscatedName("az")
   public static final classWY field6789 = new classWY("application/json");
   @ObfuscatedName("ab")
   String field6788;
   @ObfuscatedSignature(descriptor = "Lwy;")
   @ObfuscatedName("ae")
   static final classWY field6791 = new classWY("application/x-www-form-urlencoded");
   @ObfuscatedSignature(descriptor = "Lwy;")
   @ObfuscatedName("af")
   static final classWY field6790 = new classWY("text/plain");
   @ToRemove(unused = "true")
   @ObfuscatedName("by")
   public static final int field6792 = 50;

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("az")
   public String method12703(int var1) {
      try {
         return this.field6788;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wy.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;)Loz;")
   @ObfuscatedName("jr")
   public static classOZ method12708(ItemComposition var0) {
      if (var0 == null) {
         var0.method8670();
      }

      return var0.field5046;
   }

   classWY(String var1) {
      this.field6788 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lha;)Z")
   @ObfuscatedName("dw")
   public static boolean method12707(classHA var0) {
      return var0.field2746 && var0.field2747;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("af")
   public String method12704() {
      return this.field6788;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ab")
   public String method12705() {
      return this.field6788;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ag")
   public String method12706() {
      return this.field6788;
   }
}
