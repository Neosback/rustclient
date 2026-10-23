import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ww")
public class classWW {
   @ObfuscatedName("af")
   public int field6781;
   @ObfuscatedName("az")
   public int[] field6783;
   @ObfuscatedSignature(descriptor = "Lmb;")
   @ObfuscatedName("qm")
   protected static classMB field6784;
   @ObfuscatedName("ae")
   public int field6782 = 0;

   public classWW() {
      this.field6781 = 0;
   }

   public classWW(int[] var1, int var2, int var3) {
      this.field6781 = 0;
      this.field6783 = var1;
      this.field6782 = var2 * -1412665981;
      this.field6781 = -736985931 * var3;
   }

   @ObfuscatedSignature(descriptor = "(II)D")
   @ObfuscatedName("ae")
   public static double method12700(int var0, int var1) {
      try {
         return classLO.method7178(var0, (byte)21) / 65536.0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ww.ae(" + ')');
      }
   }
}
