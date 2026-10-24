import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ke")
public class classKE {
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field3562 = 65;
   @ObfuscatedSignature(descriptor = "Lke;")
   @ObfuscatedName("af")
   static final classKE field3558 = new classKE("BUILDLIVE", 3);
   @ObfuscatedSignature(descriptor = "Lke;")
   @ObfuscatedName("ae")
   static final classKE field3556 = new classKE("RC", 1);
   @ObfuscatedSignature(descriptor = "Lke;")
   @ObfuscatedName("ab")
   static final classKE field3557 = new classKE("WIP", 2);
   @ObfuscatedName("ag")
   public final String field3554;
   @ObfuscatedName("as")
   final int field3553;
   @ObfuscatedSignature(descriptor = "Lke;")
   @ObfuscatedName("az")
   static final classKE field3555 = new classKE("LIVE", 0);
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   static final int field3561 = 20;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field3560 = 30;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   public static final int field3559 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final String field3563 = "lower";

   classKE(String var1, int var2) {
      this.field3554 = var1;
      this.field3553 = -501525011 * var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lke;")
   @ObfuscatedName("ae")
   public static classKE method6505(int var0) {
      classKE[] var1 = new classKE[]{field3556, field3558, field3555, field3557};
      classKE[] var2 = var1;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classKE var4 = var2[var3];
         if (1535600841 * var4.field3553 == var0) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lke;")
   @ObfuscatedName("af")
   public static classKE method6506(int var0) {
      classKE[] var1 = new classKE[]{field3556, field3558, field3555, field3557};
      classKE[] var2 = var1;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classKE var4 = var2[var3];
         if (1160269797 * var4.field3553 == var0) {
            return var4;
         }
      }

      return null;
   }
}
