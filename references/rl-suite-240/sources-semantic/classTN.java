import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tn")
public class classTN implements classXZ {
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field6174 = 34;
   @ObfuscatedName("ag")
   final int field6167;
   @ObfuscatedSignature(descriptor = "Ltn;")
   @ObfuscatedName("af")
   public static final classTN field6170 = new classTN(1, 1);
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   public static final int field6173 = 50;
   @ObfuscatedName("ab")
   final int field6168;
   @ObfuscatedSignature(descriptor = "Ltn;")
   @ObfuscatedName("ae")
   public static final classTN field6171 = new classTN(2, 2);
   @ObfuscatedSignature(descriptor = "Ltn;")
   @ObfuscatedName("az")
   static final classTN field6169 = new classTN(0, 0);
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field6172 = 10000;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return 328973957 * this.field6168;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tn.az(" + 41);
      }
   }

   classTN(int var1, int var2) {
      this.field6167 = var1 * 1026322027;
      this.field6168 = var2 * -874214835;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return -108641300 * this.field6168;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return -143993294 * this.field6168;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return 328973957 * this.field6168;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ltn;")
   @ObfuscatedName("as")
   public static classTN[] method10985() {
      return new classTN[]{field6169, field6170, field6171};
   }
}
