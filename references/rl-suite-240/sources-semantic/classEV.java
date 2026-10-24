import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ev")
public class classEV extends classRD {
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field1939 = 12;
   @ObfuscatedName("az")
   final boolean field1938;
   @ObfuscatedName("gt")
   static String field1945;
   @ObfuscatedName("id")
   static String field1946;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final String field1944 = "decimal_";
   @ObfuscatedName("bz")
   static int field1941;
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   static final int field1940 = 46;
   @ToRemove(unused = "true")
   @ObfuscatedName("ct")
   static final int field1943 = 150;
   @ObfuscatedName("cb")
   static int field1942;

   @ObfuscatedSignature(descriptor = "(FFFFIIIB)V")
   @ObfuscatedName("ad")
   public static final void method4118(float var0, float var1, float var2, float var3, int var4, int var5, int var6, byte var7) {
      try {
         if (classFO.field2344) {
            if (var7 >= 0) {
               throw new IllegalStateException();
            }
         } else {
            float var12 = 50.0F;
            float var13 = classFQ.method4948();
            float var14 = var12 * (1376644555 * classFO.field2347 - var4) / var6;
            float var15 = (classFO.field2346 * 1170217135 - var5) * var12 / var6;
            float var16 = (1376644555 * classFO.field2347 - var4) * var13 / var6;
            float var17 = (1170217135 * classFO.field2346 - var5) * var13 / var6;
            float var19 = var15 * var1 + var0 * var12;
            var12 = classDX.method3697(var15, var12, var1, var0, (byte)-3);
            float var20 = var1 * var17 + var13 * var0;
            var13 = classDX.method3697(var17, var13, var1, var0, (byte)-105);
            float var18 = classFY.method5301(var14, var12, var3, var2, (byte)45);
            float var21 = var14 * var2 + var3 * var12;
            float var27 = classFY.method5301(var16, var13, var3, var2, (byte)64);
            float var22 = var3 * var13 + var16 * var2;
            classCI.method1500((int)var18, (int)var19, (int)var21, (int)var27, (int)var20, (int)var22, -99698685);
         }
      } catch (RuntimeException var23) {
         throw classEG.method3899(var23, "ev.ad(" + ')');
      }
   }

   public classEV(boolean var1) {
      this.field1938 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lev;Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("wf")
   public static int method4116(classEV var0, Object var1, Object var2) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return method4112(var0, (ChatPlayer)var1, (ChatPlayer)var2, 1972356957);
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return method4112(this, (ChatPlayer)var1, (ChatPlayer)var2, 1972356957);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ev.compare(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method4117(Object var1, Object var2) {
      return method4112(this, (ChatPlayer)var1, (ChatPlayer)var2, 1972356957);
   }

   @ObfuscatedSignature(descriptor = "(Lev;Lri;Lri;I)I")
   @ObfuscatedName("vy")
   public static int method4112(classEV var0, ChatPlayer var1, ChatPlayer var2, int var3) {
      if (var0 == null) {
         var0.method4115(var1, var1, var3);
      }

      try {
         if (var2.field5667 * -760305529 != var1.field5667 * -760305529) {
            int var10000;
            if (var0.field1938) {
               if (var3 != 1972356957) {
                  throw new IllegalStateException();
               }

               var10000 = -760305529 * var1.field5667 - var2.field5667 * -760305529;
            } else {
               var10000 = -760305529 * var2.field5667 - var1.field5667 * -760305529;
            }

            return var10000;
         } else {
            return var0.method9983(var1, var2, 1604251939);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ev.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("ae")
   int method4113(ChatPlayer var1, ChatPlayer var2) {
      if (var2.field5667 * -760305529 != var1.field5667 * -760305529) {
         return this.field1938 ? -760305529 * var1.field5667 - var2.field5667 * -760305529 : -760305529 * var2.field5667 - var1.field5667 * -760305529;
      } else {
         return this.method9983(var1, var2, 1329000402);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("af")
   int method4114(ChatPlayer var1, ChatPlayer var2) {
      if (var2.field5667 * -760305529 != var1.field5667 * -760305529) {
         return this.field1938 ? -760305529 * var1.field5667 - var2.field5667 * -760305529 : -760305529 * var2.field5667 - var1.field5667 * -760305529;
      } else {
         return this.method9983(var1, var2, 1986026414);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;I)I")
   @ObfuscatedName("az")
   int method4115(ChatPlayer var1, ChatPlayer var2, int var3) {
      try {
         if (var2.field5669 * -760305529 != var1.field5667 * -760305529) {
            int var10000;
            if (this.field1938) {
               if (var3 != 1972356957) {
                  throw new IllegalStateException();
               }

               var10000 = -760305529 * var1.field5667 - var2.field5669 * -760305529;
            } else {
               var10000 = -760305529 * var2.field5667 - var1.field5669 * -760305529;
            }

            return var10000;
         } else {
            return this.method9983(var1, var2, 1604251939);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ev.az(" + 41);
      }
   }
}
