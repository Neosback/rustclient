import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl")
public final class classRL {
   @ObfuscatedName("af")
   final int field5685;
   @ObfuscatedName("az")
   final int field5683;
   @ObfuscatedName("ae")
   final boolean field5684;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final int field5686 = 15;

   @ObfuscatedSignature(descriptor = "(Lcx;I)V")
   @ObfuscatedName("hg")
   static void method10063(classCX var0, int var1) {
      try {
         if (classFM.field2316 == var0) {
            if (var1 >= 1546383445) {
               throw new IllegalStateException();
            }
         } else {
            classFM.field2316 = var0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rl.hg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZI)V")
   @ObfuscatedName("cu")
   static final void method10064(int var0, int var1, int var2, int var3, boolean var4, int var5) {
      if (var4) {
         rl24.method10115(classIS.field3051);
      }

      if (var2 < 1) {
         var2 = 1;
      }

      if (var3 < 1) {
         var3 = 1;
      }

      int var6 = var3 - 334;
      double var7;
      if (var6 < 0) {
         var7 = client.field868;
      } else if (var6 >= 100) {
         var7 = client.field869;
      } else {
         var7 = (client.field869 - client.field868) * var6 / 100 + client.field868;
      }

      double var9 = var3 * var7 * 512.0 / (var2 * 334);
      if (var9 < client.field874) {
         var9 = client.field874;
         var7 = var9 * var2 * 334.0 / (var3 * 512);
         if (var7 > client.field873) {
            var7 = client.field873;
            double var11 = var3 * var7 * 512.0 / (var9 * 334.0);
            int var13 = (int)((var2 - var11) / 2.0);
            if (var4) {
               classFQ.method4971();
               classFQ.method4977(var0, var1, var13, var3, -16777216);
               classFQ.method4977(var0 + var2 - var13, var1, var13, var3, -16777216);
            }

            var0 += var13;
            var2 -= var13 * 2;
         }
      } else if (var9 > client.field875) {
         var9 = client.field875;
         var7 = var9 * var2 * 334.0 / (var3 * 512);
         if (var7 < client.field877) {
            var7 = client.field877;
            double var16 = var9 * var2 * 334.0 / (var7 * 512.0);
            int var17 = (int)((var3 - var16) / 2.0);
            if (var4) {
               classFQ.method4971();
               classFQ.method4977(var0, var1, var2, var17, -16777216);
               classFQ.method4977(var0, var3 + var1 - var17, var2, var17, -16777216);
            }

            var1 += var17;
            var3 -= var17 * 2;
         }
      }

      client.field879 = (int)(var3 * var7 / 334.0) * -2119426297;
      if (var2 != client.field991 * 27064125 || var3 != client.field983 * 1158148203) {
         client.method2280(var2, var3);
      }

      client.field799 = var0 * -1668620689;
      client.field876 = var1 * 1239417841;
      client.field991 = var2 * -505800683;
      client.field983 = var3 * 1350122563;
   }

   classRL(int var1, int var2, boolean var3) {
      this.field5683 = var1 * 1582851707;
      this.field5685 = -833668557 * var2;
      this.field5684 = var3;
   }
}
