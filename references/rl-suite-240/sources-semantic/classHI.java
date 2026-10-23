import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hi")
public class classHI {
   @ObfuscatedSignature(descriptor = "Lhi;")
   @ObfuscatedName("af")
   static final classHI field2801 = new classHI(1);
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2803 = 7;
   @ObfuscatedSignature(descriptor = "Lhi;")
   @ObfuscatedName("az")
   static final classHI field2800 = new classHI(0);
   @ObfuscatedName("ae")
   final int field2799;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field2802 = 14;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field2804 = 93;

   classHI(int var1) {
      this.field2799 = -520582397 * var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;)I")
   @ObfuscatedName("vq")
   public static int method6091(classZD var0) {
      return var0.field7153 * 1736120249;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   static int method6090(int var0, int var1) {
      try {
         return var0 - 1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hi.af(" + 41);
      }
   }
}
