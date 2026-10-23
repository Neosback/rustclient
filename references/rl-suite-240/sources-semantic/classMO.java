import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mo")
final class classMO implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("bl")
   static final int field4547 = 95;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   public static final int field4544 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field4545 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("bz")
   static final int field4548 = 105;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field4546 = 75;

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   static int method7677(int var0) {
      try {
         return classTK.field6147.length;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "mo.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)I")
   @ObfuscatedName("lw")
   public static int method7676(classQR var0) {
      if (var0 == null) {
         var0.method9779();
      }

      return var0.field5596 * 1684838611;
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("ab")
   int method7668(classNM var1, classNM var2) {
      return -1235623083 * var1.field4683.field4532 < -1718041163 * var2.field4683.field4532
         ? -1
         : (-1460435179 * var2.field4683.field4532 == -1718041163 * var1.field4683.field4532 ? 0 : 1);
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mo.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method7674(Object var1) {
      return super.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method7675(Object var1) {
      return super.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lmo;Lnm;Lnm;)I")
   @ObfuscatedName("qh")
   public static int method7669(classMO var0, classNM var1, classNM var2) {
      if (var0 == null) {
         var0.method7668(var1, var1);
      }

      return -1718041163 * var1.field4683.field4532 < -1718041163 * var2.field4683.field4532
         ? -1
         : (-1718041163 * var2.field4683.field4532 == -1718041163 * var1.field4683.field4532 ? 0 : 1);
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("ae")
   int method7670(classNM var1, classNM var2) {
      return -1718041163 * var1.field4683.field4532 < -1718041163 * var2.field4683.field4532
         ? -1
         : (-1718041163 * var2.field4683.field4532 == -1718041163 * var1.field4683.field4532 ? 0 : 1);
   }

   @ObfuscatedSignature(descriptor = "(Lmo;Lnm;Lnm;I)I")
   @ObfuscatedName("ww")
   public static int method7671(classMO var0, classNM var1, classNM var2, int var3) {
      if (var0 == null) {
         var0.method7672(var1, var1, var3);
      }

      try {
         int var10000;
         if (-1718041163 * var1.field4683.field4532 < -1718041163 * var2.field4683.field4532) {
            if (var3 >= 1528565262) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else {
            var10000 = -1718041163 * var2.field4683.field4532 == -1718041163 * var1.field4683.field4532 ? 0 : 1;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mo.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfx;Z)V")
   @ObfuscatedName("ct")
   public static void method7679(Model var0, boolean var1) {
      if (var0 == null) {
         var0.method5209(var1);
      }

      for (char var2 = 0; var2 < var0.field2417; var2++) {
         if (!Model.field2447[var2]) {
            int var3 = (Model.field2449[var0.field2427[var2]] + Model.field2449[var0.field2430[var2]] + Model.field2449[var0.field2431[var2]]) / 3
               + var0.field2487;
            char[] var10000 = Model.field2454[var3];
            Model.field2453[var3]++;
            var10000[Model.field2453[var3]] = var2;
         }
      }

      if (var0.field2429 != null && var1) {
         var0.method5210();
      } else {
         for (int var6 = var0.field2489 - 1; var6 >= 0; var6--) {
            char var7 = Model.field2453[var6];
            if (var7 > 0) {
               char[] var4 = Model.field2454[var6];

               for (int var5 = 0; var5 < var7; var5++) {
                  var0.method5217(var4[var5]);
               }
            }
         }
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return method7671(this, (classNM)var1, (classNM)var2, 678431448);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mo.compare(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method7673(Object var1, Object var2) {
      return method7671(this, (classNM)var1, (classNM)var2, -956761337);
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("af")
   static int method7667(int var0, byte var1) {
      try {
         return (var0 >> 7) + 1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mo.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("iv")
   public static void method7678(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2746();
      }

      var0.field1345 = 1565965903 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;I)I")
   @ObfuscatedName("az")
   int method7672(classNM var1, classNM var2, int var3) {
      try {
         int var10000;
         if (-1718041163 * var1.field4683.field4535 < -1718041163 * var2.field4683.field4534) {
            if (var3 >= 1528565262) {
               throw new IllegalStateException();
            }

            var10000 = -1;
         } else {
            var10000 = -1718041163 * var2.field4683.field4532 == -1718041163 * var1.field4683.field4532 ? 0 : 1;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mo.az(" + 41);
      }
   }
}
