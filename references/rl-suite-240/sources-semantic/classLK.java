import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lk")
public class classLK {
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4179 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4178 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bi")
   public static final int field4181 = 43;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4180 = 240;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;I)[I")
   @ObfuscatedName("dt")
   public static int[] method7160(PlayerComposition var0, int var1) {
      if (var0 == null) {
         var0.method6762(var1, var1, var1);
      }

      int[] var2 = new int[12];

      for (int var3 = 0; var3 < 7; var3++) {
         classGB.method5333(var1, var2, var3, (byte)8);
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "([IIII)V")
   @ObfuscatedName("bx")
   static void method7161(int[] var0, int var1, int var2, int var3) {
      try {
         while (var1 < var2) {
            int var4 = var0[var1];
            var0[var1] = var0[var2];
            var0[var2] = var4;
            var1++;
            var2--;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lk.bx(" + ')');
      }
   }

   classLK() throws Throwable {
      throw new Error();
   }
}
