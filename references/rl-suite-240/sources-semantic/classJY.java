import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jy")
public class classJY {
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field3492 = 15;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field3493 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final int field3495 = 35;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field3494 = 1;
   @ObfuscatedName("mg")
   static int field3498;
   @ToRemove(unused = "true")
   @ObfuscatedName("bv")
   static final int field3496 = 1003;
   @ToRemove(unused = "true")
   @ObfuscatedName("cd")
   public static final int field3497 = 81;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field3491 = 3;

   classJY() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laf;)Ljava/lang/Object;")
   @ObfuscatedName("rv")
   public static Object method6457(classAF var0) {
      return var0.field142.vmethod20(var0.field143);
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;B)Lte;")
   @ObfuscatedName("ab")
   static classTE method6458(int var0, Object var1, byte var2) {
      try {
         classTE var3 = (classTE)var1;
         if (var3 == null) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException();
            }
         } else {
            return var3;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "jy.ab(" + ')');
      }
   }
}
