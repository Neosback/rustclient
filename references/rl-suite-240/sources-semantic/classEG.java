import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("eg")
public class classEG extends classRD {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field1748 = 20;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field1749 = 512;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final String field1751 = "Please ensure all characters are populated.";
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   public static final int field1750 = 4;
   @ObfuscatedName("az")
   final boolean field1747;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Throwable;Ljava/lang/String;)Laaf;")
   @ObfuscatedName("af")
   public static classAAF method3899(Throwable var0, String var1) {
      classAAF var2;
      if (var0 instanceof classAAF) {
         var2 = (classAAF)var0;
      } else {
         var2 = new classAAF(var0, "");
      }

      if (!var1.endsWith("()")) {
         if (var2.field45.length() != 0) {
            var2.field45 = var2.field45 + " ";
         }

         var2.field45 = var2.field45 + var1;
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Leg;Lri;Lri;)I")
   @ObfuscatedName("gf")
   public static int method3891(classEG var0, ChatPlayer var1, ChatPlayer var2) {
      if (var0 == null) {
         return var0.method3893(var1, var1);
      } else if (2040157276 * var1.field5669 != 1234122453 * var2.field5669) {
         return var0.field1747 ? var1.field5669 * 1234122453 - var2.field5669 * 1234122453 : 1932572038 * var2.field5669 - -1513552045 * var1.field5669;
      } else {
         return var0.method9983(var1, var2, 1279543742);
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method3892((ChatPlayer)var1, (ChatPlayer)var2, 1822819216);
      } catch (RuntimeException var3) {
         throw method3899(var3, "eg.compare(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method3894(Object var1, Object var2) {
      return this.method3892((ChatPlayer)var1, (ChatPlayer)var2, 1895651778);
   }

   public classEG(boolean var1) {
      this.field1747 = var1;
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ax")
   public static int method3897(int var0, byte var1) {
      try {
         return var0 << 3;
      } catch (RuntimeException var2) {
         throw method3899(var2, "eg.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;I)I")
   @ObfuscatedName("az")
   int method3892(ChatPlayer var1, ChatPlayer var2, int var3) {
      try {
         if (1234122453 * var1.field5669 != 1234122453 * var2.field5669) {
            int var10000;
            if (this.field1747) {
               if (var3 <= 1003982056) {
                  throw new IllegalStateException();
               }

               var10000 = var1.field5669 * 1234122453 - var2.field5669 * 1234122453;
            } else {
               var10000 = 1234122453 * var2.field5669 - 1234122453 * var1.field5669;
            }

            return var10000;
         } else {
            return this.method9983(var1, var2, 1672872765);
         }
      } catch (RuntimeException var4) {
         throw method3899(var4, "eg.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)I")
   @ObfuscatedName("at")
   static int method3898(Widget var0, int var1) {
      try {
         if (11 != var0.field4373 * -1510882375) {
            classBB.field338 -= 1222866659;
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            return 1;
         } else {
            String var2 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var0.method7299(var2, 918177898);
            return 1;
         }
      } catch (RuntimeException var3) {
         throw method3899(var3, "eg.at(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("af")
   int method3893(ChatPlayer var1, ChatPlayer var2) {
      if (1633223758 * var1.field5669 != 1234122453 * var2.field5669) {
         return this.field1747 ? var1.field5669 * -710042430 - var2.field5669 * 1234122453 : 1234122453 * var2.field5669 - -1896362558 * var1.field5669;
      } else {
         return this.method9983(var1, var2, 1961943907);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Leg;Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("yw")
   public static int method3895(classEG var0, Object var1, Object var2) {
      if (var0 == null) {
         var0.method3896(var1, var1);
      }

      return var0.method3892((ChatPlayer)var1, (ChatPlayer)var2, 1500200517);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method3896(Object var1, Object var2) {
      return this.method3892((ChatPlayer)var1, (ChatPlayer)var2, 1500200517);
   }
}
