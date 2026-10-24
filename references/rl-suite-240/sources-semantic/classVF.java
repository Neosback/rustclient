import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vf")
public class classVF extends classVY {
   @ObfuscatedName("af")
   int field6497;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field6500 = 20;
   @ObfuscatedName("az")
   int field6498 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field6499 = 5;

   public classVF(int var1, int var2, int var3, int var4) {
      super(var3, var4);
      this.field6497 = 0;
      this.field6498 = 727214093 * var1;
      this.field6497 = var2 * 1639991611;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   public int method12014(byte var1) {
      try {
         double var2 = this.method12342(-1233746673);
         return (int)Math.round(var2 * (-1328568333 * this.field6497 - this.field6498 * -1541527867) + -1541527867 * this.field6498);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vf.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   public int method12015() {
      double var1 = this.method12342(-1709188435);
      return (int)Math.round(var1 * (1454249051 * this.field6497 - this.field6498 * -1541527867) + -1541527867 * this.field6498);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   public int method12016() {
      double var1 = this.method12342(858067524);
      return (int)Math.round(var1 * (-1328568333 * this.field6497 - this.field6498 * -1541527867) + -1541527867 * this.field6498);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   public int method12017() {
      double var1 = this.method12342(-1373319283);
      return (int)Math.round(var1 * (-1328568333 * this.field6497 - this.field6498 * -1541527867) + -1541527867 * this.field6498);
   }
}
