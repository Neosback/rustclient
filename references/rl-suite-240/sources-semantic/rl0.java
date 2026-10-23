import java.net.URL;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("rl0")
interface rl0 {
   @ObfuscatedSignature(descriptor = "(Ljava/net/URL;Ljava/lang/String;)V")
   @ObfuscatedName("gs")
   void vmethod560(URL var1, String var2);

   @ObfuscatedSignature(descriptor = "(Ljava/net/URL;)V")
   @ObfuscatedName("cl")
   void vmethod561(URL var1);

   @ObfuscatedSignature(descriptor = "(Lez;III)J")
   @ObfuscatedName("ct")
   static long method10065(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4437(var1, var1, var1);
      }

      int var4 = var2 + var0.field2146;
      int var5 = var3 + var0.field2146;
      int var6 = var0.method4438(var1, var4, var5);
      if (!var0.method4426(var6)) {
         return 0L;
      } else {
         byte var7 = var0.field2011[var6];

         for (int var8 = 0; var8 < var7; var8++) {
            GameObject var9 = var0.field2012[var6 * 5 + var8];
            long var10 = var9.field2183 * 1745853678706228013L;
            int var12 = (int)(var10 >> 16 & 7L);
            if (var12 == 2 && var2 == var9.field2186 * 1138017605 && var3 == var9.field2192 * -555977777) {
               return var9.field2183 * 1745853678706228013L;
            }
         }

         return 0L;
      }
   }
}
