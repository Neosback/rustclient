import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mj")
public class classMJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4512 = 2;
   @ObfuscatedName("ax")
   int field4513;
   @ObfuscatedName("ae")
   public volatile int field4510 = 0;
   @ObfuscatedName("ab")
   public int field4511;
   @ObfuscatedName("ag")
   public volatile Object field4516;
   @ObfuscatedSignature(descriptor = "Lmj;")
   @ObfuscatedName("as")
   classMJ field4517;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4514 = 1;
   @ObfuscatedName("ac")
   Object field4515;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaa;Ljava/lang/String;IIIII[I)V")
   @ObfuscatedName("jk")
   public static void method7636(AbstractFont var0, String var1, int var2, int var3, int var4, int var5, int var6, int[] var7) {
      if (var0 == null) {
         var0.method79(var1, var2, var2, var2, var2, var2, var2, var7);
      }

      if (var1 != null) {
         var0.method101(var4, var5);
         int[] var8 = null;
         if (var7 != null) {
            var8 = var0.method88(var7, var1.length());
         }

         int[] var9 = new int[var1.length()];

         for (int var10 = 0; var10 < var1.length(); var10++) {
            var9[var10] = (int)(Math.sin(var10 / 2.0 + var6 / 5.0) * 5.0);
         }

         var0.method115(var1, var2 - var0.method33(var1) / 2, var3, var8, null, var9);
      }
   }

   classMJ() {
   }
}
