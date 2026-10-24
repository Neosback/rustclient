import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mx")
final class classMX implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final String field4614 = "(?<!\\\\),";
   @ToRemove(unused = "true")
   @ObfuscatedName("cv")
   static final int field4613 = 114;
   @ToRemove(unused = "true")
   @ObfuscatedName("cr")
   static final int field4612 = 102;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final String field4615 = "Authorization";
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field4611 = 9;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method7784(Object var1) {
      return super.equals(var1);
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return method7779(this, (classNM)var1, (classNM)var2, 1997029192);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mx.compare(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmx;Lnm;Lnm;)I")
   @ObfuscatedName("vi")
   public static int method7778(classMX var0, classNM var1, classNM var2) {
      if (var0 == null) {
         return var0.method7780(var1, var1);
      } else {
         return var1.field4685 * 4044309507405125089L < 4044309507405125089L * var2.field4685
            ? -1
            : (4044309507405125089L * var1.field4685 == 4044309507405125089L * var2.field4685 ? 0 : 1);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mx.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method7782(Object var1, Object var2) {
      return method7779(this, (classNM)var1, (classNM)var2, -320542281);
   }

   @ObfuscatedSignature(descriptor = "(Lmx;Lnm;Lnm;I)I")
   @ObfuscatedName("rn")
   public static int method7779(classMX var0, classNM var1, classNM var2, int var3) {
      if (var0 == null) {
         var0.method7781(var1, var1, var3);
      }

      try {
         byte var10000;
         if (var1.field4685 * 4044309507405125089L < 4044309507405125089L * var2.field4685) {
            if (var3 == 731437002) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else if (4044309507405125089L * var1.field4685 == 4044309507405125089L * var2.field4685) {
            if (var3 == 731437002) {
               throw new IllegalStateException();
            }

            var10000 = 0;
         } else {
            var10000 = 1;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mx.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method7785(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method7783(Object var1, Object var2) {
      return method7779(this, (classNM)var1, (classNM)var2, 500367300);
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("ae")
   int method7780(classNM var1, classNM var2) {
      return var1.field4685 * 4044309507405125089L < 4044309507405125089L * var2.field4685
         ? -1
         : (4044309507405125089L * var1.field4685 == 4044309507405125089L * var2.field4685 ? 0 : 1);
   }

   @ObfuscatedSignature(descriptor = "(Lte;ILte;III)V")
   @ObfuscatedName("br")
   static void method7786(classTE var0, int var1, classTE var2, int var3, int var4, int var5) {
      try {
         if (classYY.field7109 == var0.field6126) {
            System.arraycopy(var0.field6124, var1, var2.field6124, var3, var4);
         } else if (var0.field6126 == classYY.field7113) {
            if (var5 >= -2122634010) {
               throw new IllegalStateException();
            }

            System.arraycopy(var0.field6125, var1, var2.field6125, var3, var4);
         } else {
            System.arraycopy(var0.field6123, var1, var2.field6123, var3, var4);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "mx.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;I)I")
   @ObfuscatedName("az")
   int method7781(classNM var1, classNM var2, int var3) {
      try {
         byte var10000;
         if (var1.field4685 * 4044309507405125089L < 4044309507405125089L * var2.field4685) {
            if (var3 == 731437002) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else if (4044309507405125089L * var1.field4685 == 4044309507405125089L * var2.field4685) {
            if (var3 == 731437002) {
               throw new IllegalStateException();
            }

            var10000 = 0;
         } else {
            var10000 = 1;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mx.az(" + 41);
      }
   }
}
