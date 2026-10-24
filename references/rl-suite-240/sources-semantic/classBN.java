import net.runelite.mapping.ObfuscatedName;

@ObfuscatedName("bn")
public class classBN {
   @ObfuscatedName("ab")
   int[] field517;
   @ObfuscatedName("az")
   int field516;
   @ObfuscatedName("ae")
   int[] field518;
   @ObfuscatedName("af")
   int field515;

   classBN(classBT var1) {
      classBT.method1234(var1, 16, -2038756090);
      this.field515 = var1.method1241(309636778) != 0 ? classBT.method1234(var1, 4, -1992813142) + 1 : 1;
      if (var1.method1241(-1920686168) != 0) {
         classBT.method1234(var1, 8, -2033555503);
      }

      classBT.method1234(var1, 2, -2013614499);
      if (this.field515 > 1) {
         this.field516 = classBT.method1234(var1, 4, -1958387643);
      }

      this.field517 = new int[this.field515];
      this.field518 = new int[this.field515];

      for (int var2 = 0; var2 < this.field515; var2++) {
         classBT.method1234(var1, 8, -2138771869);
         this.field517[var2] = classBT.method1234(var1, 8, -2026877994);
         this.field518[var2] = classBT.method1234(var1, 8, -2110737677);
      }
   }
}
