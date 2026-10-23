import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zg")
public interface classZG {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrw;Ljava/lang/String;)V")
   @ObfuscatedName("wp")
   static void method14045(FriendsChatManager var0, String var1) {
      if (var0 == null) {
         var0.method10383();
      }

      long var5 = 0L;
      int var7 = var1.length();

      for (int var8 = 0; var8 < var7; var8++) {
         var5 *= 37L;
         char var9 = var1.charAt(var8);
         if (var9 >= 'A' && var9 <= 'Z') {
            var5 += 1 + var9 - 1818942960;
         } else if (var9 >= 'a' && var9 <= 'z') {
            var5 += 1 + var9 - 97;
         } else if (var9 >= '0' && var9 <= '9') {
            var5 += 27 + var9 - 290575987;
         }

         if (var5 >= 177917621779460413L) {
            break;
         }
      }

      while (var5 % 37L == 0L && var5 != 0L) {
         var5 /= 37L;
      }

      String var10 = classHF.method6038(var5);
      if (var10 == null) {
         var10 = "";
      }

      var0.field5862 = var10;
   }
}
