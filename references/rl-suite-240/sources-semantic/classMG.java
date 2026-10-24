import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mg")
public class classMG extends classMH {
   @ObfuscatedName("ij")
   public static boolean field4501;
   @ObfuscatedName("tk")
   public long field4500;
   @ObfuscatedName("eu")
   public long field4499;
   @ObfuscatedName("zc")
   public static double field4498;
   @ObfuscatedName("az")
   long field4497 = System.nanoTime() * -8688469985774214489L;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)Lmu;")
   @ObfuscatedName("kq")
   public static classMU method7608(classMA var0) {
      return var0.field4458;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   @Override
   public int vmethod533(int var1, int var2) {
      long var3 = 1000000L * var2;
      long var5 = 7670220157275514135L * this.field4497 - System.nanoTime();
      if (var5 < var3) {
         var5 = var3;
      }

      Friend.method10197(var5 / 1000000L);
      long var7 = System.nanoTime();
      int var9 = 0;

      while (var9 < 10 && (var9 < 1 || this.field4497 * 7670220157275514135L < var7)) {
         var9++;
         this.field4497 += var1 * 1813175205450790848L;
      }

      if (this.field4497 * 7670220157275514135L < var7) {
         this.field4497 = var7 * -8688469985774214489L;
      }

      return var9;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod530(byte var1) {
      try {
         this.field4497 = System.nanoTime() * -8688469985774214489L;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mg.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod535(int var1, int var2, int var3) {
      return client.field1145 && client.field829 * -44590225 >= 25 ? this.method7610(var1, var2) : this.method7611(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ih")
   public int method7611(int var1, int var2) {
      long var3 = 1000000L * var2;
      long var5 = this.field4497 * 7670220157275514135L - System.nanoTime();
      if (var5 < var3) {
         var5 = var3;
      }

      method7609(var5 / 1000000L);
      long var7 = System.nanoTime();
      int var9 = 0;

      while (var9 < 10 && (var9 < 1 || this.field4497 * 7670220157275514135L < var7)) {
         var9++;
         this.field4497 = (this.field4497 * 7670220157275514135L + var1 * 1000000L) * -8688469985774214489L;
      }

      if (this.field4497 * 7670220157275514135L < var7) {
         this.field4497 = var7 * -8688469985774214489L;
      }

      field4501 = true;
      return var9;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   @Override
   public int vmethod534(int var1, int var2) {
      long var3 = 1000000L * var2;
      long var5 = 7670220157275514135L * this.field4497 - System.nanoTime();
      if (var5 < var3) {
         var5 = var3;
      }

      Friend.method10197(var5 / 1000000L);
      long var7 = System.nanoTime();
      int var9 = 0;

      while (var9 < 10 && (var9 < 1 || this.field4497 * 7670220157275514135L < var7)) {
         var9++;
         this.field4497 += var1 * 1813175205450790848L;
      }

      if (this.field4497 * 7670220157275514135L < var7) {
         this.field4497 = var7 * -8688469985774214489L;
      }

      return var9;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("md")
   public int method7610(int var1, int var2) {
      long var3 = System.nanoTime();
      field4501 = true;
      if (client.field1099 > 0L) {
         long var5 = this.field4497 * 7670220157275514135L - var3;
         long var7 = this.field4499 - var3;
         long var9 = Math.min(var5, var7);
         var9 /= 1000000L;
         method7609(var9);
         var3 = System.nanoTime();
         if (var3 < this.field4497 * 7670220157275514135L && var5 > var7) {
            field4501 = false;
         } else {
            this.field4497 = (this.field4497 * 7670220157275514135L + client.field1099) * -8688469985774214489L;
            if (this.field4497 * 7670220157275514135L < var3) {
               this.field4497 = var3 * -8688469985774214489L;
            }
         }
      }

      long var11 = 1000000L * var1;

      int var12;
      for (var12 = 0; var12 < 10 && (var12 < 1 && !field4501 || this.field4499 < var3); this.field4499 += var11) {
         var12++;
         this.field4500 = this.field4499;
      }

      if (this.field4499 < var3) {
         this.field4499 = var3;
      }

      field4498 = (double)Math.min(var3 - this.field4500, var11) / var11;
      return var12;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   @Override
   public void vmethod531() {
      this.field4497 = System.nanoTime() * -8688469985774214489L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lek;Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("bn")
   public static int method7607(classEK var0, Object var1, Object var2) {
      if (var0 == null) {
         var0.method3927(var1, var1);
      }

      return var0.method3923((ChatPlayer)var1, (ChatPlayer)var2, (short)28438);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod532() {
      this.field4497 = System.nanoTime() * -8688469985774214489L;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ea")
   public static void method7609(long var0) {
      if (var0 > 0L) {
         if (var0 % 10L == 0L) {
            try {
               Thread.sleep(var0 - 1L);
            } catch (InterruptedException var5) {
            }

            try {
               Thread.sleep(1L);
            } catch (InterruptedException var4) {
            }
         } else {
            try {
               Thread.sleep(var0);
            } catch (InterruptedException var3) {
            }
         }
      }
   }

   classMG() {
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ip")
   @Override
   public int vmethod536(int var1, int var2) {
      return this.vmethod535(var1, var2, -1162884564);
   }
}
