import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pe")
public class classPE implements classXZ {
   @ObfuscatedName("ag")
   final int field5230;
   @ObfuscatedSignature(descriptor = "Lpe;")
   @ObfuscatedName("az")
   static final classPE field5226 = new classPE(0, 0);
   @ObfuscatedSignature(descriptor = "Lpe;")
   @ObfuscatedName("ae")
   static final classPE field5227 = new classPE(2, 2);
   @ObfuscatedName("ab")
   public final int field5229;
   @ObfuscatedSignature(descriptor = "Lpe;")
   @ObfuscatedName("af")
   static final classPE field5228 = new classPE(1, 1);
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field5231 = 8;
   @ObfuscatedSignature(descriptor = "[Lym;")
   @ObfuscatedName("aj")
   static SpritePixels[] field5232;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field5230 * -2126097231;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pe.az(" + 41);
      }
   }

   classPE(int var1, int var2) {
      this.field5229 = var1 * -943669939;
      this.field5230 = var2 * -2079361967;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field5230 * -2126097231;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field5230 * -2126097231;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field5230 * -2126097231;
   }
}
