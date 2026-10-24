import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl7")
public interface rl7 {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwd;)Lzl;")
   @ObfuscatedName("gf")
   static classZL method10164(classWD var0) {
      return var0.field6667;
   }

   @ObfuscatedSignature(descriptor = "(Lez;IIIIIII)V")
   @ObfuscatedName("ln")
   void method10165(Scene var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8);
}
