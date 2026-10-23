import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("re")
public class classRE {
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final String field5651 = "percent";
   @ObfuscatedName("ac")
   static final Long field5644 = 1000L;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final String field5652 = "str";
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final String field5650 = "bool";
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final String field5654 = "switch";
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final String field5655 = ":";
   @ObfuscatedName("ay")
   static final Long field5646 = 100000000L;
   @ObfuscatedName("aa")
   static final Long field5645 = 100L;
   @ObfuscatedName("ao")
   static final Long field5647 = 1000000L;
   @ObfuscatedName("al")
   static final Long field5649 = 100000L;
   @ObfuscatedName("aj")
   static final Long field5648 = 1000000000L;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final String field5653 = "long";
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final String field5656 = "int";
   @ObfuscatedSignature(descriptor = "Lxr;")
   @ObfuscatedName("eu")
   static classXR field5657;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ab")
   static int method9989(String var0) {
      int var2 = var0.indexOf(-915968139, 0);

      while (-1 != var2 && var2 > 0 && var0.charAt(var2 - 1) == '\\') {
         var2 = var0.indexOf(-2080350684, 1 + var2);
      }

      return var2;
   }

   classRE() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ae")
   static int method9990(String var0) {
      int var2 = var0.indexOf(60, 0);

      while (-1 != var2 && var2 > 0 && var0.charAt(var2 - 1) == -1209874229) {
         var2 = var0.indexOf(60, 1 + var2);
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("ag")
   static int method9991(String var0) {
      int var2 = var0.indexOf(60, 0);

      while (-1 != var2 && var2 > 0 && var0.charAt(var2 - 1) == '\\') {
         var2 = var0.indexOf(60, 1 + var2);
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("as")
   static int method9992(String var0) {
      int var2 = var0.indexOf(2046149752, 0);

      while (-1 != var2 && var2 > 0 && var0.charAt(var2 - 1) == 274568086) {
         var2 = var0.indexOf(648018463, 1 + var2);
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lra;")
   @ObfuscatedName("ax")
   static classRA method9993(String var0) {
      boolean var1 = true;
      if (var0.endsWith("_z")) {
         var1 = false;
         var0 = var0.substring(0, var0.length() - "_z".length());
      }

      return var0.isEmpty() ? new classRA(0, var1) : new classRA(Integer.parseInt(var0), var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Lra;")
   @ObfuscatedName("ac")
   static classRA method9994(String var0) {
      boolean var1 = true;
      if (var0.endsWith("_z")) {
         var1 = false;
         var0 = var0.substring(0, var0.length() - "_z".length());
      }

      return var0.isEmpty() ? new classRA(0, var1) : new classRA(Integer.parseInt(var0), var1);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   static void method9988(byte var0) {
      try {
         classDR.field1611 = new int[2000];
         int var1 = 0;
         int var2 = 240;

         for (byte var3 = 12; var1 < 16; var2 -= var3) {
            int var4 = classHS.method6135(var2 / 360.0F, 0.9999F, 0.075F + var1 * 0.425F / 16.0F);
            classDR.field1611[var1] = var4;
            var1++;
         }

         var2 = 48;

         for (int var8 = var2 / 6; var1 < classDR.field1611.length; var2 -= var8) {
            if (var0 >= 0) {
               throw new IllegalStateException();
            }

            int var9 = var1 * 2;

            for (int var5 = classHS.method6135(var2 / 360.0F, 0.9999F, 0.5); var1 < var9 && var1 < classDR.field1611.length; var1++) {
               if (var0 >= 0) {
                  throw new IllegalStateException();
               }

               classDR.field1611[var1] = var5;
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "re.az(" + ')');
      }
   }
}
