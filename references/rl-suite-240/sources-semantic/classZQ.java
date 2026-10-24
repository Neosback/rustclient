import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zq")
public class classZQ extends Node {
   @ObfuscatedName("as")
   int[] field7237;
   @ObfuscatedName("af")
   int field7233;
   @ObfuscatedName("ae")
   int[] field7236;
   @ObfuscatedName("ab")
   int[] field7235;
   @ObfuscatedName("ax")
   Method[] field7239;
   @ObfuscatedName("az")
   int field7234;
   @ObfuscatedName("ag")
   Field[] field7238;
   @ObfuscatedName("ac")
   byte[][][] field7240;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lff;[I[F[IIIIF)V")
   @ObfuscatedName("xn")
   public static void method14134(classFF var0, int[] var1, float[] var2, int[] var3, int var4, int var5, int var6, float var7) {
      if (var0 == null) {
         var0.method4663(var1, var2, var1, var4, var4, var4, var7);
      }

      int var8 = var3[(var6 & 16256) + (var6 >>> 25)];
      var8 = ((var8 & 333924918) * var5 & -16711936) + ((var8 & 0xFF00) * var5 & 0xFF0000) >> 8;
      classFF.method4660(var0, var1, var2, var4, var8, var7, classFF.method4653(var2[var4], var7));
   }

   classZQ() {
   }
}
