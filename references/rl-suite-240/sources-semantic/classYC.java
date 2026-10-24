import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yc")
public class classYC implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field6964 = 255;
   @ObfuscatedName("az")
   final boolean field6963;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method13355(Object var1, Object var2) {
      return this.method13352((Nameable)var1, (Nameable)var2, (byte)89);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Lrr;B)I")
   @ObfuscatedName("az")
   int method13352(Nameable var1, Nameable var2, byte var3) {
      try {
         return this.field6963 ? var1.vmethod562(var2, -30374874) : var2.vmethod562(var1, -30374874);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yc.az(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yc.equals(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;)I")
   @ObfuscatedName("vq")
   public static int method13360(classSU var0) {
      return var0 == null ? var0.method10734() : var0.field6058 * -1666621055;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laax;)V")
   @ObfuscatedName("gm")
   public static void method13358(classAAX var0) {
      if (var0 == null) {
         var0.method287();
      } else {
         var0.field113 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyc;Lrr;Lrr;)I")
   @ObfuscatedName("xk")
   public static int method13353(classYC var0, Nameable var1, Nameable var2) {
      return var0.field6963 ? var1.vmethod562(var2, -30374874) : var2.vmethod562(var1, -30374874);
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method13352((Nameable)var1, (Nameable)var2, (byte)42);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "yc.compare(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqg;)Ljava/lang/String;")
   @ObfuscatedName("yg")
   public static String method13359(classQG var0) {
      return var0.field5533;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyc;Ljava/lang/Object;)Z")
   @ObfuscatedName("rn")
   public static boolean method13356(classYC var0, Object var1) {
      if (var0 == null) {
         var0.method13357(var1);
      }

      return var0.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method13357(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Lrr;)I")
   @ObfuscatedName("af")
   int method13354(Nameable var1, Nameable var2) {
      return this.field6963 ? var1.vmethod562(var2, -30374874) : var2.vmethod562(var1, -30374874);
   }

   public classYC(boolean var1) {
      this.field6963 = var1;
   }
}
