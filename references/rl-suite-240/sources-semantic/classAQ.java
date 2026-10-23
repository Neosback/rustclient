import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aq")
public class classAQ {
   @ObfuscatedName("ag")
   int[] field249;
   @ObfuscatedName("aa")
   int field252;
   @ObfuscatedName("af")
   int field255;
   @ObfuscatedName("ab")
   int field250 = 2;
   @ObfuscatedName("as")
   int[] field251;
   @ObfuscatedName("al")
   int field256;
   @ObfuscatedName("ax")
   int field258;
   @ObfuscatedName("ac")
   int field253;
   @ObfuscatedName("az")
   int field259;
   @ObfuscatedName("ao")
   int field257;
   @ObfuscatedName("ae")
   int field254;

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   final int method655(int var1) {
      if (this.field256 >= this.field258) {
         this.field257 = this.field251[this.field253++] << 15;
         if (this.field253 >= this.field250) {
            this.field253 = this.field250 - 1;
         }

         this.field258 = (int)(this.field249[this.field253] / 65536.0 * var1);
         if (this.field258 > this.field256) {
            this.field252 = ((this.field251[this.field253] << 15) - this.field257) / (this.field258 - this.field256);
         }
      }

      this.field257 = this.field257 + this.field252;
      this.field256++;
      return this.field257 - this.field252 >> 15;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("az")
   final void method646(classXY var1) {
      this.field254 = var1.method13095(-346779531);
      this.field259 = classXY.method13113(var1, (byte)1);
      this.field255 = classXY.method13113(var1, (byte)1);
      this.method650(var1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   final void method653() {
      this.field258 = 0;
      this.field253 = 0;
      this.field252 = 0;
      this.field257 = 0;
      this.field256 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laq;Lxy;)V")
   @ObfuscatedName("vr")
   public static void method649(classAQ var0, classXY var1) {
      if (var0 == null) {
         var0.method647(var1);
      } else {
         var0.field250 = var1.method13095(-346779531);
         var0.field249 = new int[var0.field250];
         var0.field251 = new int[var0.field250];

         for (int var2 = 0; var2 < var0.field250; var2++) {
            var0.field249[var2] = var1.method13099(-1838758401);
            var0.field251[var2] = var1.method13099(-1797386924);
         }
      }
   }

   classAQ() {
      this.field249 = new int[2];
      this.field251 = new int[2];
      this.field249[0] = 0;
      this.field249[1] = 65535;
      this.field251[0] = 0;
      this.field251[1] = 65535;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   final int method656(int var1) {
      if (this.field256 >= this.field258) {
         this.field257 = this.field251[this.field253++] << 15;
         if (this.field253 >= this.field250) {
            this.field253 = this.field250 - 1;
         }

         this.field258 = (int)(this.field249[this.field253] / 65536.0 * var1);
         if (this.field258 > this.field256) {
            this.field252 = ((this.field251[this.field253] << 15) - this.field257) / (this.field258 - this.field256);
         }
      }

      this.field257 = this.field257 + this.field252;
      this.field256++;
      return this.field257 - this.field252 >> 15;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("af")
   final void method650(classXY var1) {
      this.field250 = var1.method13095(-346779531);
      this.field249 = new int[this.field250];
      this.field251 = new int[this.field250];

      for (int var2 = 0; var2 < this.field250; var2++) {
         this.field249[var2] = var1.method13099(-558593366);
         this.field251[var2] = var1.method13099(-1806044823);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ag")
   final void method647(classXY var1) {
      this.field254 = var1.method13095(-346779531);
      this.field259 = classXY.method13113(var1, (byte)1);
      this.field255 = classXY.method13113(var1, (byte)1);
      this.method650(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ac")
   final void method651(classXY var1) {
      this.field250 = var1.method13095(-346779531);
      this.field249 = new int[this.field250];
      this.field251 = new int[this.field250];

      for (int var2 = 0; var2 < this.field250; var2++) {
         this.field249[var2] = var1.method13099(-401698618);
         this.field251[var2] = var1.method13099(-951704202);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("aa")
   final void method652(classXY var1) {
      this.field250 = var1.method13095(-346779531);
      this.field249 = new int[this.field250];
      this.field251 = new int[this.field250];

      for (int var2 = 0; var2 < this.field250; var2++) {
         this.field249[var2] = var1.method13099(-924138602);
         this.field251[var2] = var1.method13099(1514957370);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   final void method654() {
      this.field258 = 0;
      this.field253 = 0;
      this.field252 = 0;
      this.field257 = 0;
      this.field256 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("as")
   final void method648(classXY var1) {
      this.field254 = var1.method13095(-346779531);
      this.field259 = classXY.method13113(var1, (byte)1);
      this.field255 = classXY.method13113(var1, (byte)1);
      this.method650(var1);
   }
}
