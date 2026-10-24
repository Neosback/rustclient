import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("na")
final class classNA implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("cb")
   static final int field4625 = 108;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4624 = 1536;
   @ToRemove(unused = "true")
   @ObfuscatedName("cl")
   public static final int field4626 = 80;
   @ToRemove(unused = "true")
   @ObfuscatedName("cp")
   static final int field4627 = 151;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lna;Ljava/lang/Object;)Z")
   @ObfuscatedName("on")
   public static boolean method7802(classNA var0, Object var1) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return var0.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;I)I")
   @ObfuscatedName("az")
   int method7796(classNM var1, classNM var2, int var3) {
      try {
         int var10000;
         if (var1.field4684 * -906797091 < var2.field4684 * -906797091) {
            if (var3 == 710435829) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else {
            var10000 = var1.field4684 * -906797091 == -906797091 * var2.field4684 ? 0 : 1;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "na.az(" + 41);
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method7796((classNM)var1, (classNM)var2, 1498521139);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "na.compare(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "na.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method7799(Object var1, Object var2) {
      return this.method7796((classNM)var1, (classNM)var2, 532334399);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lna;Lnm;Lnm;)I")
   @ObfuscatedName("mv")
   public static int method7797(classNA var0, classNM var1, classNM var2) {
      if (var0 == null) {
         var0.method7798(var1, var1);
      }

      return var1.field4684 * -906797091 < var2.field4684 * -906797091 ? -1 : (var1.field4684 * -906797091 == -906797091 * var2.field4684 ? 0 : 1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method7800(Object var1, Object var2) {
      return this.method7796((classNM)var1, (classNM)var2, 1823600506);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method7803(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("az")
   static final void method7801(String var0, int var1) {
      try {
         String var2 = classKK.field3795 + var0 + classKK.field3796;
         classPH.method8924(30, "", var2, 1421015270);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "na.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("af")
   int method7798(classNM var1, classNM var2) {
      return var1.field4684 * -906797091 < var2.field4684 * -906797091 ? -1 : (var1.field4684 * -906797091 == -906797091 * var2.field4684 ? 0 : 1);
   }
}
