import java.security.SecureRandom;
import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("du")
public class classDU implements Callable {
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   public static final int field1630 = 51;
   @ObfuscatedName("bw")
   static int field1631;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field1629 = 22;
   @ObfuscatedName("al")
   static long[] field1632;

   @ObfuscatedSignature(descriptor = "(IIIIIII)I")
   @ObfuscatedName("az")
   public static int method3617(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         if (1 == (var5 & 1)) {
            if (var6 == -843196892) {
               throw new IllegalStateException();
            }

            int var7 = var3;
            var3 = var4;
            var4 = var7;
         }

         var2 &= 3;
         if (var2 == 0) {
            if (var6 == -843196892) {
               throw new IllegalStateException();
            } else {
               return var0;
            }
         } else if (1 == var2) {
            if (var6 == -843196892) {
               throw new IllegalStateException();
            } else {
               return var1;
            }
         } else {
            return var2 == 2 ? 7 - var0 - (var3 - 1) : 7 - var1 - (var4 - 1);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "du.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/security/SecureRandom;")
   @ObfuscatedName("as")
   static SecureRandom method3618() {
      SecureRandom var0 = new SecureRandom();
      var0.nextInt();
      return var0;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method3621() {
      return classGP.method5581((byte)1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method3622() {
      return classGP.method5581((byte)1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Lrr;)I")
   @ObfuscatedName("xb")
   public static int method3624(NameableContainer var0, Nameable var1) {
      if (var0 == null) {
         var0.method10247();
      }

      for (int var2 = 0; var2 < 1744080879 * var0.field5840; var2++) {
         if (var0.field5841[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/security/SecureRandom;")
   @ObfuscatedName("ab")
   static SecureRandom method3619() {
      SecureRandom var0 = new SecureRandom();
      var0.nextInt();
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/security/SecureRandom;")
   @ObfuscatedName("ag")
   static SecureRandom method3620() {
      SecureRandom var0 = new SecureRandom();
      var0.nextInt();
      return var0;
   }

   @Override
   public Object call() {
      try {
         return classGP.method5581((byte)1);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "du.call(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("afp")
   protected static final int method3623(byte var0) {
      try {
         return GameEngine.field6196.method10862(1732339090);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "du.afp(" + 41);
      }
   }

   classDU() {
   }
}
