import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vy")
public abstract class classVY {
   @ObfuscatedName("aa")
   int field6641 = 0;
   @ObfuscatedName("ao")
   int field6639 = 0;
   @ObfuscatedName("al")
   double field6640 = 0.0;
   @ObfuscatedName("aj")
   int field6642 = 0;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method12335() {
      if (1883191353 * this.field6639 < this.field6641 * -665926205) {
         this.field6639 += 155399689;
         int var1 = this.field6639 * 1883191353;
         int var2 = -665926205 * this.field6641;
         int var4 = 684177813 * this.field6642;
         classZL var3 = classZL.field7199[var4];
         this.field6640 = classIC.method6178(var1, var2, var3, (byte)64);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvy;)Z")
   @ObfuscatedName("it")
   public static boolean method12339(classVY var0) {
      return var0.field6639 * 1883191353 >= var0.field6641 * -665926205;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("al")
   public void method12336(byte var1) {
      try {
         if (1883191353 * this.field6639 < this.field6641 * -665926205) {
            this.field6639 += 155399689;
            int var2 = this.field6639 * 1883191353;
            int var3 = -665926205 * this.field6641;
            int var5 = 684177813 * this.field6642;
            classZL var4 = classZL.field7199[var5];
            this.field6640 = classIC.method6178(var2, var3, var4, (byte)64);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "vy.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aj")
   public boolean method12340(byte var1) {
      try {
         boolean var10000;
         if (this.field6639 * 1883191353 >= this.field6641 * -665926205) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vy.aj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvy;)V")
   @ObfuscatedName("ko")
   public static void method12337(classVY var0) {
      if (1883191353 * var0.field6639 < var0.field6641 * -665926205) {
         var0.field6639 += 155399689;
         int var1 = var0.field6639 * 1883191353;
         int var2 = -665926205 * var0.field6641;
         int var4 = 684177813 * var0.field6642;
         classZL var3 = classZL.field7199[var4];
         var0.field6640 = classIC.method6178(var1, var2, var3, (byte)64);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)D")
   @ObfuscatedName("ay")
   double method12342(int var1) {
      try {
         return this.field6640;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vy.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method12338() {
      if (1883191353 * this.field6639 < this.field6641 * -665926205) {
         this.field6639 += -272485565;
         int var1 = this.field6639 * 1883191353;
         int var2 = -665926205 * this.field6641;
         int var4 = -1035534286 * this.field6642;
         classZL var3 = classZL.field7199[var4];
         this.field6640 = classIC.method6178(var1, var2, var3, (byte)64);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method12341() {
      return this.field6639 * 1883191353 >= this.field6641 * -665926205;
   }

   classVY(int var1, int var2) {
      this.field6641 = var1 * 1019761387;
      this.field6639 = 0;
      this.field6642 = (var2 >= 0 && var2 <= 27 ? var2 : 0) * -1426142787;
      int var4 = this.field6639 * 1883191353;
      int var5 = this.field6641 * -665926205;
      int var7 = this.field6642 * 684177813;
      classZL var6 = classZL.field7199[var7];
      this.field6640 = classIC.method6178(var4, var5, var6, (byte)64);
   }

   @ObfuscatedSignature(descriptor = "()D")
   @ObfuscatedName("aw")
   double method12343() {
      return this.field6640;
   }
}
