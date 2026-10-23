import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rx")
public class classRX {
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("az")
   static final classRX field5867 = new classRX(1);
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("ag")
   static final classRX field5872 = new classRX(0);
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("af")
   static final classRX field5869 = new classRX(2);
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("ab")
   static final classRX field5870 = new classRX(4);
   @ToRemove(unused = "true")
   @ObfuscatedName("du")
   public static final int field5876 = 112;
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("as")
   static final classRX field5871 = new classRX(3);
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("ax")
   static final classRX field5868 = new classRX(5);
   @ObfuscatedName("ac")
   final int field5866;
   @ObfuscatedSignature(descriptor = "Lrx;")
   @ObfuscatedName("ae")
   static final classRX field5873 = new classRX(6);
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field5874 = 24;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   public static final int field5875 = 8192;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyc;Lrr;Lrr;)I")
   @ObfuscatedName("qg")
   public static int method10385(classYC var0, Nameable var1, Nameable var2) {
      return var0.field6963 ? var1.vmethod562(var2, -30374874) : var2.vmethod562(var1, -30374874);
   }

   classRX(int var1) {
      this.field5866 = var1 * -185299593;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lam;)V")
   @ObfuscatedName("xd")
   public static void method10387(classAM var0) {
      if (var0 == null) {
         var0.method595();
      }

      if (var0.field233 != 0) {
         if (var0.field223 == Integer.MIN_VALUE) {
            var0.field223 = 0;
         }

         var0.field233 = 0;
         var0.method539();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Luo;")
   @ObfuscatedName("ag")
   static classUO[] method10386(int var0) {
      try {
         return new classUO[]{classUO.field6358, classUO.field6356, classUO.field6357, classUO.field6355};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "rx.ag(" + ')');
      }
   }
}
