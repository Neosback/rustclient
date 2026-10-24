import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("vg")
public class IntegerNode extends Node implements net.runelite.api.IntegerNode {
   @ObfuscatedName("az")
   public int field6501;

   public void setValue(int var1) {
      this.field6501 = var1;
   }

   public int getValue() {
      return this.field6501;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;Lfs;ILfs;I[I)V")
   @ObfuscatedName("nv")
   public static void method12018(Model var0, classFS var1, int var2, classFS var3, int var4, int[] var5) {
      if (var0 == null) {
         var0.method5148(var1, var2, var1, var2, var5);
      } else if (var2 != -1) {
         if (var5 != null && var4 != -1) {
            classET var6 = var1.field2390[var2];
            classET var7 = var3.field2390[var4];
            classEM var8 = var6.field1909;
            Model.field2484 = 0;
            Model.field2486 = 0;
            Model.field2485 = 0;
            Model.method5246(-1);
            int var9 = 0;
            int var10 = var5[var9++];

            for (int var11 = 0; var11 < var6.field1911; var11++) {
               int var12 = var6.field1910[var11];

               while (var12 > var10) {
                  var10 = var5[var9++];
               }

               if (var12 != var10 || var8.field1783[var12] == 0) {
                  Model.method5260(var0, var8.field1783[var12], var8.field1785[var12], var6.field1907[var11], var6.field1913[var11], var6.field1912[var11]);
               }
            }

            Model.field2484 = 0;
            Model.field2486 = 0;
            Model.field2485 = 0;
            Model.method5246(-1);
            var9 = 0;
            var10 = var5[var9++];

            for (int var17 = 0; var17 < var7.field1911; var17++) {
               int var18 = var7.field1910[var17];

               while (var18 > var10) {
                  var10 = var5[var9++];
               }

               if (var18 == var10 || var8.field1783[var18] == 0) {
                  Model.method5260(var0, var8.field1783[var18], var8.field1785[var18], var7.field1907[var17], var7.field1913[var17], var7.field1912[var17]);
               }
            }

            var0.method5129();
         } else {
            var0.method5135(var1, var2);
         }
      }
   }

   public IntegerNode(int var1) {
      this.field6501 = var1;
   }
}
