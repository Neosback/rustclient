import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uv")
public class classUV {
   @ToRemove(unused = "true")
   @ObfuscatedName("bi")
   static final int field6403 = 82;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6402 = 32;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(D)D")
   @ObfuscatedName("ab")
   static double method11731(double var0) {
      return Math.exp(var0 * -var0 / 2.0) / Math.sqrt(Math.PI * 2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(D)D")
   @ObfuscatedName("ag")
   static double method11732(double var0) {
      return Math.exp(var0 * -var0 / 2.0) / Math.sqrt(Math.PI * 2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DDD)D")
   @ObfuscatedName("ax")
   static double method11734(double var0, double var2, double var4) {
      return classCX.method2695((var0 - var2) / var4) / var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DDD)D")
   @ObfuscatedName("aa")
   static double method11735(double var0, double var2, double var4) {
      return classCX.method2695((var0 - var2) / var4) / var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(D)D")
   @ObfuscatedName("as")
   static double method11733(double var0) {
      return Math.exp(var0 * -var0 / 2.0) / Math.sqrt(Math.PI * 2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DDD)D")
   @ObfuscatedName("ac")
   static double method11736(double var0, double var2, double var4) {
      return classCX.method2695((var0 - var2) / var4) / var4;
   }

   classUV() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DDI)[D")
   @ObfuscatedName("ao")
   public static double[] method11737(double var0, double var2, int var4) {
      int var5 = 1 + 2 * var4;
      double[] var6 = new double[var5];
      int var7 = -var4;

      for (int var8 = 0; var7 <= var4; var8++) {
         var6[var8] = classOL.method8541(var7, var0, var2);
         var7++;
      }

      return var6;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(DDI)[D")
   @ObfuscatedName("al")
   public static double[] method11738(double var0, double var2, int var4) {
      int var5 = 1 + 2 * var4;
      double[] var6 = new double[var5];
      int var7 = -var4;

      for (int var8 = 0; var7 <= var4; var8++) {
         var6[var8] = classOL.method8541(var7, var0, var2);
         var7++;
      }

      return var6;
   }
}
