import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zp")
public class classZP {
   @ObfuscatedName("az")
   int field7232;

   @ObfuscatedSignature(descriptor = "(Lzp;I)I")
   @ObfuscatedName("dy")
   public static int method14129(classZP var0, int var1) {
      if (var0 == null) {
         return var0.method14131(var1);
      } else {
         try {
            return 203459897 * var0.field7232;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "zp.az(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljz;Lvj;J)V")
   @ObfuscatedName("up")
   public static void method14133(NodeCache var0, classVJ var1, long var2) {
      if (var0 == null) {
         var0.method6466(var1, var2);
      } else {
         var0.method6464(var1, var2);
      }
   }

   classZP(int var1, boolean var2) {
      this.field7232 = 1670140169 * var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   public int method14130() {
      return 492259730 * this.field7232;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;)V")
   @ObfuscatedName("cu")
   public static void method14132(NPC var0) {
      if (var0 == null) {
         var0.vmethod375();
      }

      var0.field1580 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   public int method14131(int var1) {
      try {
         return 203459897 * this.field7232;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "zp.az(" + 41);
      }
   }
}
