import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sb")
public class classSB {
   @ObfuscatedName("ax")
   public int field5901;
   @ObfuscatedName("ab")
   public int field5896;
   @ObfuscatedName("as")
   public int field5900;
   @ObfuscatedName("af")
   public int field5898;
   @ObfuscatedSignature(descriptor = "Lal;")
   @ObfuscatedName("az")
   public classAL field5902;
   @ObfuscatedName("ag")
   public int field5899;
   @ObfuscatedName("ae")
   public int field5897;
   @ObfuscatedName("ac")
   public boolean field5903;

   @ObfuscatedSignature(descriptor = "(Lsb;)V")
   @ObfuscatedName("bb")
   public void method10413(classSB var1) {
      method10410(this, var1, -2134176686);
   }

   classSB() {
   }

   @ObfuscatedSignature(descriptor = "(Lsb;)V")
   @ObfuscatedName("ae")
   void method10409(classSB var1) {
      this.field5900 = 1 * var1.field5900;
      this.field5902 = var1.field5902;
      this.field5901 = 1 * var1.field5901;
      this.field5897 = var1.field5897 * 1;
      this.field5899 = var1.field5899 * 1;
      this.field5898 = var1.field5898 * 1;
      this.field5896 = 1 * var1.field5896;
      this.field5903 = var1.field5903;
   }

   @ObfuscatedSignature(descriptor = "(Lsb;Lsb;I)V")
   @ObfuscatedName("fj")
   public static void method10410(classSB var0, classSB var1, int var2) {
      if (var0 == null) {
         var0.method10411(var0, var2);
      } else {
         try {
            var0.field5900 = 1 * var1.field5900;
            var0.field5902 = var1.field5902;
            var0.field5901 = 1 * var1.field5901;
            var0.field5897 = var1.field5897 * 1;
            var0.field5899 = var1.field5899 * 1;
            var0.field5898 = var1.field5898 * 1;
            var0.field5896 = 1 * var1.field5896;
            var0.field5903 = var1.field5903;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "sb.az(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lha;)I")
   @ObfuscatedName("ex")
   public static int method10412(classHA var0) {
      return -456391909 * var0.field2748;
   }

   @ObfuscatedSignature(descriptor = "(Lsb;I)V")
   @ObfuscatedName("az")
   void method10411(classSB var1, int var2) {
      try {
         this.field5898 = 1 * var1.field5898;
         this.field5902 = var1.field5902;
         this.field5900 = 1 * var1.field5896;
         this.field5896 = var1.field5901 * 1;
         this.field5901 = var1.field5900 * 1;
         this.field5899 = var1.field5899 * 1;
         this.field5899 = 1 * var1.field5896;
         this.field5903 = var1.field5903;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "sb.az(" + ')');
      }
   }
}
