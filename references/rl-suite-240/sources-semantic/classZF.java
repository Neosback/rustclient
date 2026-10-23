import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zf")
public class classZF {
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field7181 = 10;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field7180 = 23;
   @ObfuscatedSignature(descriptor = "Lzf;")
   @ObfuscatedName("ae")
   static final classZF field7178 = new classZF(2);
   @ObfuscatedSignature(descriptor = "Lzf;")
   @ObfuscatedName("az")
   static final classZF field7176 = new classZF(0);
   @ObfuscatedName("ab")
   public final int field7179;
   @ObfuscatedSignature(descriptor = "Lzf;")
   @ObfuscatedName("af")
   public static final classZF field7177 = new classZF(1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lzf;")
   @ObfuscatedName("ab")
   public static classZF method14042(int var0) {
      classZF[] var1 = new classZF[]{field7176, field7178, field7177};
      classZF[] var2 = var1;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classZF var4 = var2[var3];
         if (373304937 * var4.field7179 == var0) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lzf;")
   @ObfuscatedName("af")
   public static classZF method14043(int var0) {
      classZF[] var1 = new classZF[]{field7176, field7178, field7177};
      classZF[] var2 = var1;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classZF var4 = var2[var3];
         if (-1766258067 * var4.field7179 == var0) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lzf;")
   @ObfuscatedName("ae")
   public static classZF method14044(int var0) {
      classZF[] var1 = new classZF[]{field7176, field7178, field7177};
      classZF[] var2 = var1;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classZF var4 = var2[var3];
         if (-1382377909 * var4.field7179 == var0) {
            return var4;
         }
      }

      return null;
   }

   classZF(int var1) {
      this.field7179 = -1824586909 * var1;
   }
}
