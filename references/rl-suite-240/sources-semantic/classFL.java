import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fl")
public class classFL {
   @ObfuscatedName("ag")
   byte field2301;
   @ObfuscatedName("ab")
   byte field2300;
   @ObfuscatedName("ae")
   byte field2299;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final byte field2302 = 127;
   @ObfuscatedName("af")
   byte field2298 = -1;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method4852() {
      this.field2298 = -1;
      this.field2299 = -1;
      this.field2300 = -1;
      this.field2301 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfl;Lfl;)V")
   @ObfuscatedName("ta")
   public static void method4842(classFL var0, classFL var1) {
      if (var0 == null) {
         var0.method4846(var0);
      } else {
         var0.field2298 = var1.field2298;
         var0.field2299 = var1.field2299;
         var0.field2300 = var1.field2300;
         var0.field2301 = var1.field2301;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfl;)V")
   @ObfuscatedName("as")
   public void method4843(classFL var1) {
      this.field2298 = var1.field2298;
      this.field2299 = var1.field2299;
      this.field2300 = var1.field2300;
      this.field2301 = var1.field2301;
   }

   public classFL() {
      this.field2299 = -1;
      this.field2300 = -1;
      this.field2301 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lfl;)V")
   @ObfuscatedName("si")
   public void method4844(classFL var1) {
      this.field2298 = var1.field2298;
      this.field2299 = var1.field2299;
      this.field2300 = var1.field2300;
      this.field2301 = var1.field2301;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method4857() {
      return this.field2301 > 0;
   }

   @ObfuscatedSignature(descriptor = "(Lfl;)V")
   @ObfuscatedName("az")
   public void method4861(classFL var1) {
      this.method4843(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lfl;)V")
   @ObfuscatedName("ag")
   public void method4845(classFL var1) {
      this.field2298 = var1.field2298;
      this.field2299 = var1.field2299;
      this.field2300 = var1.field2300;
      this.field2301 = var1.field2301;
   }

   @ObfuscatedSignature(descriptor = "(BBBB)V")
   @ObfuscatedName("aa")
   public void method4847(byte var1, byte var2, byte var3, byte var4) {
      this.field2298 = var1;
      this.field2299 = var2;
      this.field2300 = var3;
      this.field2301 = var4;
   }

   @ObfuscatedSignature(descriptor = "(BBBB)V")
   @ObfuscatedName("ac")
   public void method4848(byte var1, byte var2, byte var3, byte var4) {
      this.field2298 = var1;
      this.field2299 = var2;
      this.field2300 = var3;
      this.field2301 = var4;
   }

   @ObfuscatedSignature(descriptor = "(BBBB)V")
   @ObfuscatedName("ao")
   public void method4849(byte var1, byte var2, byte var3, byte var4) {
      this.field2298 = var1;
      this.field2299 = var2;
      this.field2300 = var3;
      this.field2301 = var4;
   }

   @ObfuscatedSignature(descriptor = "(Lfl;BBBB)V")
   @ObfuscatedName("ip")
   public static void method4850(classFL var0, byte var1, byte var2, byte var3, byte var4) {
      if (var0 == null) {
         var0.method4851(var1, var1, var1, var1);
      } else {
         var0.field2298 = var1;
         var0.field2299 = var2;
         var0.field2300 = var3;
         var0.field2301 = var4;
      }
   }

   public classFL(byte var1, byte var2, byte var3, byte var4) {
      this.field2299 = -1;
      this.field2300 = -1;
      this.field2301 = 0;
      this.field2298 = var1;
      this.field2299 = var2;
      this.field2300 = var3;
      this.field2301 = var4;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public void method4853() {
      this.field2298 = -1;
      this.field2299 = -1;
      this.field2300 = -1;
      this.field2301 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public void method4854() {
      this.field2298 = -1;
      this.field2299 = -1;
      this.field2300 = -1;
      this.field2301 = 0;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   public boolean method4858() {
      return this.field2301 > 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfl;)Z")
   @ObfuscatedName("vc")
   public static boolean method4859(classFL var0) {
      return var0 == null ? var0.method4860() : var0.field2301 > 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method4855() {
      this.field2298 = -1;
      this.field2299 = -1;
      this.field2300 = -1;
      this.field2298 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lfl;)V")
   @ObfuscatedName("ax")
   public void method4846(classFL var1) {
      this.field2299 = var1.field2298;
      this.field2299 = var1.field2300;
      this.field2300 = var1.field2299;
      this.field2301 = var1.field2299;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method4860() {
      return this.field2298 > 0;
   }

   @ObfuscatedSignature(descriptor = "(Lfl;)V")
   @ObfuscatedName("tr")
   public static void method4856(classFL var0) {
      if (var0 == null) {
         var0.method4855();
      }

      var0.field2298 = -1;
      var0.field2299 = -1;
      var0.field2300 = -1;
      var0.field2301 = 0;
   }

   @ObfuscatedSignature(descriptor = "(BBBB)V")
   @ObfuscatedName("af")
   public void method4851(byte var1, byte var2, byte var3, byte var4) {
      this.field2300 = var1;
      this.field2298 = var2;
      this.field2301 = var3;
      this.field2299 = var4;
   }
}
