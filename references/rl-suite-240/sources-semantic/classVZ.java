import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vz")
public class classVZ {
   @ObfuscatedName("ao")
   final int field6646;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field6650 = 2;
   @ObfuscatedName("aj")
   final int field6648;
   @ObfuscatedName("ay")
   final int field6645;
   @ObfuscatedName("aq")
   final int field6644;
   @ObfuscatedName("ad")
   final int field6643;
   @ObfuscatedName("ap")
   final int field6649;
   @ObfuscatedName("al")
   final int field6647;

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ay")
   public static void method12347(byte var0) {
      try {
         classYL.field7000 = null;
         classYL.field7001 = null;
         EntityOps.field5369 = null;
         classGG.field2576 = null;
         classRB.field5637 = null;
         classYL.field7002 = (byte[][])null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "vz.ay(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lkq;)I")
   @ObfuscatedName("st")
   public static int method12346(classKQ var0) {
      return var0.field3998 * -1052801789;
   }

   @Override
   public String toString() {
      try {
         return String.format(
            "%dx%d (offset %d,%d)", this.field6646 * -105665105, this.field6647 * 1760347583, this.field6644 * -1311898939, -1680480153 * this.field6643
         );
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "vz.toString(" + ')');
      }
   }

   classVZ(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field6646 = var1 * 2004994383;
      this.field6647 = var2 * 557197887;
      this.field6644 = var3 * 282078221;
      this.field6643 = 402830167 * var4;
      this.field6648 = var1 / 2 * 1073883851;
      this.field6645 = -2133131775 * (var2 / 2);
      this.field6649 = -513216623 * (this.field6648 * 1905552099 * 1905552099 * this.field6648 + this.field6645 * 455672321 * this.field6645 * 455672321);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method12344() {
      return String.format(
         "%dx%d (offset %d,%d)", this.field6646 * 2127347528, this.field6647 * 1750965116, this.field6644 * -1311898939, 440527287 * this.field6643
      );
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method12345() {
      return String.format(
         "%dx%d (offset %d,%d)", this.field6646 * 335305802, this.field6647 * 1760347583, this.field6644 * -679072695, 652342371 * this.field6643
      );
   }
}
