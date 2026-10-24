import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rc")
public class classRC {
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("ae")
   public static final classRC field5639 = new classRC();
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("af")
   public static final classRC field5640 = new classRC();
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("az")
   public static final classRC field5638 = new classRC();
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   static final int field5641 = 41;

   classRC() {
   }

   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("ae")
   public static int method9976(long var0) {
      try {
         return (int)(var0 >>> 7 & 127L);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rc.ae(" + 41);
      }
   }
}
