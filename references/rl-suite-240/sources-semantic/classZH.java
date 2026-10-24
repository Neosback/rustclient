import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zh")
public class classZH {
   @ToRemove(unused = "true")
   @ObfuscatedName("bi")
   static final int field7184 = 75;
   @ObfuscatedName("az")
   final int[] field7182;
   @ObfuscatedName("af")
   int field7183;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;)V")
   @ObfuscatedName("xy")
   public static void method14054(classLE var0) {
      if (var0 == null) {
         var0.method6820();
      } else {
         classLE.method6814(var0, 1715663530);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   public void method14052() {
      this.field7183 = 0;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("az")
   public void method14046(int var1, byte var2) {
      try {
         this.field7182[(this.field7183 += 1397954045) * -1078628523 - 1] = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zh.az(" + ')');
      }
   }

   public classZH(int var1) {
      this.field7182 = new int[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;)I")
   @ObfuscatedName("ed")
   public static int method14056(classPV var0) {
      return var0 == null ? var0.method9362() : var0.field5438 * 718414679;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;Z)V")
   @ObfuscatedName("gk")
   public static void method14055(classQC var0, boolean var1) {
      if (var0 == null) {
         var0.method9515();
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   public int method14048(int var1, int var2) {
      try {
         return this.field7182[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zh.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kr")
   public int method14058() {
      return this.method14050((byte)10);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public void method14053(int var1) {
      try {
         this.field7183 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "zh.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bg")
   public int method14059(int var1) {
      return this.method14048(var1, 2011977896);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzh;I)V")
   @ObfuscatedName("xu")
   public static void method14047(classZH var0, int var1) {
      var0.field7182[(var0.field7183 += 1397954045) * -1520696924 - 1] = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("as")
   public int method14049() {
      return -1078628523 * this.field7183;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ae")
   public int method14050(byte var1) {
      try {
         return -1078628523 * this.field7183;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "zh.ae(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;)I")
   @ObfuscatedName("cb")
   public static int method14057(classZD var0) {
      return 2145444479 * var0.field7157;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ax")
   public int method14051() {
      return -1078628523 * this.field7183;
   }
}
