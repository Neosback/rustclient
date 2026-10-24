import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ye")
public class classYE implements classXZ {
   @ObfuscatedSignature(descriptor = "Lye;")
   @ObfuscatedName("az")
   public static final classYE field6970 = new classYE(0, 0);
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("de")
   static IterableHashTable field6975;
   @ObfuscatedSignature(descriptor = "Lye;")
   @ObfuscatedName("af")
   static final classYE field6971 = new classYE(1, 1);
   @ObfuscatedName("ab")
   public final int field6974;
   @ObfuscatedName("ag")
   final int field6973;
   @ObfuscatedSignature(descriptor = "Lye;")
   @ObfuscatedName("ae")
   static final classYE field6972 = new classYE(2, 2);

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field6973 * 1484841107;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ye.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lye;")
   @ObfuscatedName("as")
   public static classYE[] method13370() {
      return new classYE[]{field6971, field6972, field6970};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;Ljava/lang/String;I)Lzp;")
   @ObfuscatedName("zn")
   public static classZP method13374(classZD var0, String var1, int var2) {
      return var0.method13921(var1, var0.field7156.size(), var2, (byte)62);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwz;Lwz;)V")
   @ObfuscatedName("ri")
   public static void method13373(Menu var0, Menu var1) {
      if (var0 == null) {
         var0.getMenuX();
      }

      var0.method12765(891635837);
      var0.field6815 = var1.field6810 * 1009054268 + var1.field6815 * 1;
      if (var0.field6810 * 336852242 + 548509948 * var0.field6815 > 773246731 * classSA.field5895) {
         var0.field6815 = 1 * var1.field6815 - var0.field6810 * 404742155;
      }

      if (1018843180 * var0.field6815 < 0) {
         var0.field6815 = 0;
      }

      int var2 = var1.field6795 * 1146219427 - 15 - var1.field6814 * -1722149802 + var1.field6813 * 1457978411;
      if (var1.field6808) {
         var2 += 17;
      }

      int var3 = 19 + var2;
      var0.field6813 = var2 * -317857149;
      if (var0.field6813 * -196193027 + var0.field6812 * -531540521 > 8379747 * IntProjection.field1936) {
         var0.field6813 = (var3 - var0.field6812 * -531540521) * -317857149;
      }

      if (1832674314 * var0.field6813 < 0) {
         var0.field6813 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field6973 * 1484841107;
   }

   classYE(int var1, int var2) {
      this.field6974 = var1 * -1080124053;
      this.field6973 = var2 * 601308571;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field6973 * 1484841107;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field6973 * 1484841107;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lye;")
   @ObfuscatedName("ax")
   public static classYE[] method13371() {
      return new classYE[]{field6971, field6972, field6970};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lye;")
   @ObfuscatedName("ac")
   public static classYE[] method13372() {
      return new classYE[]{field6971, field6972, field6970};
   }
}
