import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ko")
public class classKO {
   @ToRemove(unused = "true")
   @ObfuscatedName("cu")
   static final int field3975 = 249;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field3973 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field3970 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field3971 = -2;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field3974 = -3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field3969 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field3972 = 15;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   public static int method6541(int var0, int var1) {
      return var1 + (-3 - var0 << 8);
   }

   classKO() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   public static int method6542(int var0, int var1) {
      return var1 + (-3 - var0 << 8);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   public static int method6543(int var0, int var1) {
      return var1 + (-3 - var0 << 8);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public static int method6544(int var0) {
      return var0 + -512;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   public static int method6545(int var0) {
      return var0 + -512;
   }

   @ObfuscatedSignature(descriptor = "(I)Lvl;")
   @ObfuscatedName("az")
   public static classVL method6546(int var0) {
      try {
         synchronized (classVL.field6520) {
            if (1879977691 * classVL.field6518 == 0) {
               return new classVL();
            } else {
               classVL.method12047(classVL.field6520[(classVL.field6518 -= -272097965) * 1879977691], (byte)7);
               return classVL.field6520[classVL.field6518 * 1879977691];
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ko.az(" + ')');
      }
   }
}
