import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aw")
public abstract class classAW extends Node {
   @ObfuscatedName("ax")
   int field295;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;IILha;Lhh;Lhu;Lge;)V")
   @ObfuscatedName("jy")
   public static void method719(WorldMapRegion var0, int var1, int var2, classHA var3, classHH var4, classHU var5, classGE var6) {
      if (var0 == null) {
         var0.method5755(var1);
      }

      int var7 = var3.field2756[0][var1][var2] - 1;
      int var8 = var3.field2757[0][var1][var2] - 1;
      if (-1 == var7 && -1 == var8) {
         classYW.method13737(
            680806733 * var0.field2665 * var1,
            609184860 * var0.field2665 * (893108332 - var2),
            991973274 * var0.field2665,
            var0.field2665 * 1879951,
            759805682 * var0.field2658
         );
      }

      int var9 = 16711935;
      if (var8 != -1) {
         var9 = classPS.method9221(var8, -155991272 * var0.field2658, var6, -959833637);
      }

      if (var8 > -1 && 0 == var3.field2755[0][var1][var2]) {
         classYW.method13737(
            1338066436 * var0.field2665 * var1, (63 - var2) * var0.field2665 * 680806733, var0.field2665 * 680806733, var0.field2665 * -2011495642, var9
         );
      } else {
         int var10 = var0.method5709(var1, var2, var3, var5, -1255554545);
         if (var8 == -1) {
            classYW.method13737(
               var0.field2665 * 680806733 * var1,
               (531519024 - var2) * var0.field2665 * 680806733,
               680806733 * var0.field2665,
               var0.field2665 * 680806733,
               var10
            );
         } else {
            classHH.method6048(
               var4,
               var1 * var0.field2665 * 680806733,
               (1208921947 - var2) * var0.field2665 * 1152669316,
               var10,
               var9,
               var0.field2665 * -796718584,
               var0.field2665 * 866421463,
               var3.field2755[0][var1][var2],
               var3.field2754[0][var1][var2],
               980481800
            );
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)Z")
   @ObfuscatedName("lo")
   public static boolean method718(ClientPreferences var0) {
      return var0.field1339;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnz;)Lvj;")
   @ObfuscatedName("hg")
   public static classVJ method717(classNZ var0) {
      classVJ var1 = var0.field4798.field6513;
      if (var1 == var0.field4798) {
         return null;
      } else {
         var1.method12035();
         return var1;
      }
   }

   classAW() {
   }
}
