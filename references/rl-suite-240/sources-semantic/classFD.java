import java.awt.Container;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fd")
public class classFD {
   @ObfuscatedName("aq")
   boolean field2209;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2223 = 512;
   @ObfuscatedName("ak")
   int field2225;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final float field2216 = 15000.0F;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final float field2219 = 1.0100503F;
   @ObfuscatedName("ad")
   public boolean field2210;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field2226 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field2222 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field2221 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field2229 = 1;
   @ObfuscatedName("at")
   int field2231;
   @ObfuscatedName("aj")
   int field2215 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final float field2217 = 75.0F;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final float field2220 = 150.75377F;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final float field2218 = 14925.0F;
   @ObfuscatedName("ap")
   int field2211;
   @ObfuscatedName("au")
   public int field2207;
   @ObfuscatedSignature(descriptor = "Lfg;")
   @ObfuscatedName("ai")
   public classFG field2237;
   @ObfuscatedSignature(descriptor = "Lfl;")
   @ObfuscatedName("ay")
   final classFL field2213 = new classFL();
   @ObfuscatedName("aw")
   int field2233;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2234 = 128;
   @ObfuscatedName("av")
   int field2232;
   @ObfuscatedName("ar")
   int field2230;
   @ObfuscatedName("an")
   int field2228;
   @ObfuscatedName("am")
   int field2227;
   @ObfuscatedName("ah")
   int field2224;
   @ObfuscatedName("bn")
   int[] field2212;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field2236 = -1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field2235 = 0;
   @ObfuscatedName("bp")
   int field2208;
   @ObfuscatedName("br")
   int field2214;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   void method4614() {
      this.field2230 = this.field2225 / 2;
      this.field2233 = this.field2232 / 2;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;)I")
   @ObfuscatedName("qw")
   public static int method4622(classSU var0) {
      return var0.field6058 * -1666621055;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("pa")
   public void method4625(int var1) {
      this.field2207 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;)I")
   @ObfuscatedName("io")
   public static int method4623(WorldEntity var0) {
      return var0.field6010.field1690 * -1414382856 + var0.field6024.method8511((byte)23);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfd;IIII)V")
   @ObfuscatedName("zc")
   public static void method4618(classFD var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method4619(var1, var1, var1, var1);
      }

      var0.field2230 = var1 - var2;
      var0.field2233 = var3 - var4;
      var0.field2231 = -var0.field2230;
      var0.field2228 = var0.field2225 - var0.field2230;
      var0.field2227 = -var0.field2233;
      var0.field2224 = var0.field2232 - var0.field2233;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("xa")
   public void method4626(boolean var1) {
      this.field2210 = var1;
   }

   classFD() {
      this.field2209 = false;
      this.field2210 = true;
      this.field2211 = 0;
      this.field2207 = 512;
      this.field2212 = new int[1024];
      this.field2208 = -1;
      this.field2214 = -1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("nt")
   public int method4627() {
      return this.field2231;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   void method4615() {
      this.field2230 = this.field2225 / 2;
      this.field2233 = this.field2232 / 2;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("yp")
   public int method4628() {
      return this.field2227;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sz")
   public int method4629() {
      return this.field2233;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("oq")
   public int method4630() {
      return this.field2228;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)Ljava/awt/Container;")
   @ObfuscatedName("su")
   public static Container method4624(GameEngine var0) {
      return (Container)(null != var0.field6219 ? var0.field6219 : var0);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   void method4616() {
      this.field2230 = this.field2225 / 2;
      this.field2233 = this.field2232 / 2;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("af")
   void method4619(int var1, int var2, int var3, int var4) {
      this.field2230 = var1 - var2;
      this.field2233 = var3 - var4;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("px")
   public int method4631() {
      return this.field2224;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("yy")
   public int method4632() {
      return this.field2207;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   void method4617() {
      this.field2230 = this.field2225 / 2;
      this.field2233 = this.field2232 / 2;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mf")
   public int method4633() {
      return this.field2230;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("lp")
   public boolean method4634() {
      return this.field2210;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("as")
   void method4620(int var1, int var2, int var3, int var4) {
      this.field2230 = var1 - var2;
      this.field2233 = var3 - var4;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ax")
   void method4621(int var1, int var2, int var3, int var4) {
      this.field2230 = var1 - var2;
      this.field2233 = var3 - var4;
      this.field2231 = -this.field2230;
      this.field2228 = this.field2225 - this.field2230;
      this.field2227 = -this.field2233;
      this.field2224 = this.field2232 - this.field2233;
   }
}
