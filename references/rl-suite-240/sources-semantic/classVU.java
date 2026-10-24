import java.io.IOException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vu")
public class classVU extends Node {
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ab")
   public classVP field6607;
   @ObfuscatedName("af")
   public byte[] field6608;
   @ObfuscatedSignature(descriptor = "Lus;")
   @ObfuscatedName("ae")
   public classUS field6606;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field6603 = 11;
   @ObfuscatedName("az")
   int field6605;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field6604 = 12;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laac;)V")
   @ObfuscatedName("se")
   public static void method12181(classAAC var0) throws IOException {
      if (var0 == null) {
         var0.method133();
      }

      if (var0.field33 * 695819223253367491L != -1L) {
         if (var0.field33 * 695819223253367491L != var0.field40 * -8875074085580969595L) {
            classAAR.method235(var0.field31, 695819223253367491L * var0.field33);
            var0.field40 = -4565816884240621145L * var0.field33;
         }

         var0.field31.method238(var0.field34, 0, var0.field32 * -1202147915, -921777407);
         var0.field40 = var0.field40 + var0.field32 * 4982648314025189359L;
         if (-8875074085580969595L * var0.field40 > -2811425842963315237L * var0.field29) {
            var0.field29 = 486754378564656159L * var0.field40;
         }

         long var1 = -1L;
         long var3 = -1L;
         if (var0.field33 * 695819223253367491L >= var0.field36 * -682698895895007857L
            && var0.field33 * 695819223253367491L < var0.field38 * 950427542 + -682698895895007857L * var0.field36) {
            var1 = 695819223253367491L * var0.field33;
         } else if (var0.field36 * -682698895895007857L >= var0.field33 * 695819223253367491L
            && -682698895895007857L * var0.field36 < var0.field32 * -387998165 + var0.field33 * 695819223253367491L) {
            var1 = var0.field36 * -682698895895007857L;
         }

         if (var0.field32 * -387998165 + 695819223253367491L * var0.field33 > -682698895895007857L * var0.field36
            && 695819223253367491L * var0.field33 + -387998165 * var0.field32 <= -1605680973 * var0.field38 + -682698895895007857L * var0.field36) {
            var3 = 695819223253367491L * var0.field33 + var0.field32 * -387998165;
         } else if (var0.field36 * -682698895895007857L + var0.field38 * -1605680973 > var0.field33 * 695819223253367491L
            && var0.field36 * -682698895895007857L + var0.field38 * -1605680973 <= 695819223253367491L * var0.field33 + -387998165 * var0.field32) {
            var3 = -682698895895007857L * var0.field36 + -1605680973 * var0.field38;
         }

         if (var1 > -1L && var3 > var1) {
            int var5 = (int)(var3 - var1);
            System.arraycopy(
               var0.field34, (int)(var1 - 695819223253367491L * var0.field33), var0.field35, (int)(var1 - -682698895895007857L * var0.field36), var5
            );
         }

         var0.field33 = 1077159670856333845L;
         var0.field32 = 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldx;IIIII)V")
   @ObfuscatedName("de")
   public static void method12180(classDX var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method3701();
      }

      if (var4 == var2 && var2 != -1 && var5 != var3) {
         var0.method3705(-871565127 * classDX.field1656, var1, -1, var2, -1, var5 - var3, 3288651);
      } else if (-1 == var2 && -1 != var4 && var5 > 0) {
         boolean var9 = false;

         for (int var10 = 0; var10 < var0.field1653 * -1432564885; var10++) {
            classDB var11 = (classDB)var0.field1652.get(var10);
            if (-1315156953 * var11.field1372 == classDX.field1658 * 48170419 && 1073934853 * var11.field1366 == var4 && var5 == var11.field1371 * -979715423) {
               var11.field1372 = 788642921 * classDX.field1657;
               var11.field1367 = var1 * 881833361;
               var11.field1369 = 1839559751;
               var11.field1371 = 1907644063;
               var9 = true;
               break;
            }
         }

         if (!var9) {
            var0.method3705(-1231891449 * classDX.field1655, var1, -1, var4, -1, var5, -1694606632);
         }
      } else if (var2 != -1 && var4 == -1 && var3 > 0) {
         boolean var6 = false;

         for (int var7 = 0; var7 < var0.field1653 * -1432564885; var7++) {
            classDB var8 = (classDB)var0.field1652.get(var7);
            if (-1231891449 * classDX.field1655 == var8.field1372 * -1315156953 && var8.field1366 * 1073934853 == var2 && var8.field1371 * -979715423 == var3) {
               var8.field1372 = classDX.field1657 * 788642921;
               var8.field1367 = var1 * 881833361;
               var8.field1369 = var8.field1366 * -884237155;
               var8.field1366 = -644877005;
               var8.field1371 = 1907644063;
               var6 = true;
               break;
            }
         }

         if (!var6) {
            var0.method3705(48170419 * classDX.field1658, var1, -1, var2, -1, var3, -2041427219);
         }
      } else if (-1 != var2 && -1 != var4 && var2 != var4) {
         var0.method3705(48170419 * classDX.field1658, var1, -1, var2, -1, var3, -1978356816);
         var0.method3705(classDX.field1655 * -1231891449, var1, -1, var4, -1, var5, -780192499);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaa;Ljava/lang/String;II)Luq;")
   @ObfuscatedName("sb")
   public static classUQ method12182(AbstractFont var0, String var1, int var2, int var3) {
      if (var0 == null) {
         var0.method49(var1, var2, var2);
      }

      if (var1 != null && !var1.isEmpty()) {
         int var4 = var0.method33(var1);
         int var5 = var0.field9 + var0.field10;
         int var6 = var2 - var4 / 2;
         int var7 = var3 - var0.field9;
         return classPW.method9374(var6, var7, var4, var5, 1444081395);
      } else {
         return classPW.method9374(var2, var3, 0, 0, 1444081395);
      }
   }

   classVU() {
   }
}
