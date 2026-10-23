import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jo")
public class classJO {
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field3349 = 25;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field3347 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field3350 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field3348 = 2;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqh;IIZ)V")
   @ObfuscatedName("hq")
   public static void method6439(classQH var0, int var1, int var2, boolean var3) {
      if (var0 == null) {
         var0.method9586(var1);
      }

      var0.field5538 = 1718621873 * var1;
      var0.field5536 = -376511642 * var2;
      var0.field5537 = var3;
   }

   classJO() throws Throwable {
      throw new Error();
   }
}
