import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jp")
public class classJP {
   @ObfuscatedName("dr")
   public static boolean field3355;
   @ObfuscatedSignature(descriptor = "Ljp;")
   @ObfuscatedName("af")
   static final classJP field3352 = new classJP(classGN.method5537((byte)77) + 1);
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field3353 = 1;
   @ObfuscatedSignature(descriptor = "Ljp;")
   @ObfuscatedName("az")
   static final classJP field3351 = new classJP(classGN.method5537((byte)70) + 1);
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   static final int field3354 = 60;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;)V")
   @ObfuscatedName("uh")
   public static void method6443(NameableContainer var0, Nameable var1) {
      var0.field5841[(var0.field5840 += 2063367951) * 1744080879 - 1] = var1;
   }

   classJP(int var1) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   static int method6440() {
      return classDG.method3067(98, 198, (byte)-12);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   public static int method6441(int var0, int var1) {
      return var0 << 8 | var1;
   }

   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ag")
   public static int method6442(int var0, int var1, int var2, int var3) {
      try {
         int var4 = (int)classOW.method8842(1 + (var2 - var1), 2039590920);
         var4 <<= var1;
         int var6;
         return var6 = var0 & ~var4;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "jp.ag(" + 41);
      }
   }
}
