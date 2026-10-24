import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kx")
public class classKX implements classXZ {
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("as")
   public static final classKX field4048 = new classKX("oldscape", "RuneScape 2007", 5);
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("ag")
   static final classKX field4047 = new classKX("game5", "Game 5", 4);
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("ae")
   static final classKX field4046 = new classKX("game3", "Game 3", 2);
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("ab")
   static final classKX field4045 = new classKX("game4", "Game 4", 3);
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("af")
   static final classKX field4049 = new classKX("stellardawn", "Stellar Dawn", 1);
   @ObfuscatedName("ac")
   final int field4050;
   @ObfuscatedName("ax")
   public final String field4051;
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("az")
   static final classKX field4044 = new classKX("runescape", "RuneScape", 0);
   @ToRemove(unused = "true")
   @ObfuscatedName("bs")
   static final int field4052 = 93;

   classKX(String var1, String var2, int var3) {
      this.field4051 = var1;
      this.field4050 = -350634179 * var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lkx;")
   @ObfuscatedName("as")
   public static classKX[] method6621() {
      return new classKX[]{field4047, field4045, field4049, field4044, field4048, field4046};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;)V")
   @ObfuscatedName("kr")
   public static void method6622(classPV var0) {
      var0.field5433 = var0.field5430 * 1753564287;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return 1839566869 * this.field4050;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return 1839566869 * this.field4050;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return 1839566869 * this.field4050;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return 1839566869 * this.field4050;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kx.az(" + 41);
      }
   }
}
