import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bp")
public abstract class classBP extends Node {
   @ObfuscatedName("az")
   int field522;

   @ObfuscatedSignature(descriptor = "(Lao;)I")
   @ObfuscatedName("ag")
   abstract int method1173(classAO var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   abstract void method1174();

   @ObfuscatedSignature(descriptor = "(Lao;)I")
   @ObfuscatedName("af")
   abstract int method1175(classAO var1);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   abstract void method1176();

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   abstract void method1177();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)Z")
   @ObfuscatedName("sx")
   public static boolean method1172(classLH var0) {
      return var0.field4145;
   }

   @ObfuscatedSignature(descriptor = "(Lao;)I")
   @ObfuscatedName("as")
   abstract int method1178(classAO var1);

   @ObfuscatedSignature(descriptor = "(Lao;)I")
   @ObfuscatedName("ac")
   abstract int method1179(classAO var1);

   @ObfuscatedSignature(descriptor = "(Lao;)I")
   @ObfuscatedName("ax")
   abstract int method1180(classAO var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;IIII)V")
   @ObfuscatedName("md")
   public static void method1171(classDC var0, int var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < var0.field1387.size(); var5++) {
         ((Actor)var0.field1387.get(var5)).method3138(var1, var2, client.field856 * 1612595797, var3, var4, 731061816);
      }
   }

   classBP() throws Throwable {
      throw new Error();
   }
}
