import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nh")
public final class classNH {
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("az")
   classVJ field4669 = new classVJ();

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("ac")
   public classVJ method7893() {
      classVJ var1 = this.field4669.field6513;
      return var1 == this.field4669 ? null : var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("az")
   public void method7887(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4669.field6514;
      var1.field6513 = this.field4669;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("af")
   public void method7889(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4669;
      var1.field6513 = this.field4669.field6513;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("ae")
   public classVJ method7894() {
      classVJ var1 = this.field4669.field6513;
      return var1 == this.field4669 ? null : var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("ab")
   public void method7888(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4669.field6514;
      var1.field6513 = this.field4669;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("ag")
   public void method7890(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4669;
      var1.field6513 = this.field4669.field6513;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   public classNH() {
      this.field4669.field6513 = this.field4669;
      this.field4669.field6514 = this.field4669;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzh;)V")
   @ObfuscatedName("qs")
   public static void method7891(classZH var0) {
      if (var0 == null) {
         var0.method14052();
      } else {
         var0.field7183 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("as")
   public void method7892(classVJ var1) {
      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = this.field4669;
      var1.field6513 = this.field4669.field6513;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }
}
