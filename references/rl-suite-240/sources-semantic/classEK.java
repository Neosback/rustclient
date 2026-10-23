import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ek")
public class classEK extends classRD {
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field1775 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field1776 = 3;
   @ObfuscatedName("az")
   final boolean field1774;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("cn")
   static IndexedSprite field1777;

   @ObfuscatedSignature(descriptor = "(Lri;Lri;S)I")
   @ObfuscatedName("az")
   int method3923(ChatPlayer var1, ChatPlayer var2, short var3) {
      try {
         if (-760305529 * var1.field5667 != 0) {
            if (var3 <= 256) {
               throw new IllegalStateException();
            }

            if (var2.field5667 * -760305529 != 0) {
               if (var3 <= 256) {
                  throw new IllegalStateException();
               }

               int var10000;
               if (this.field1774) {
                  if (var3 <= 256) {
                     throw new IllegalStateException();
                  }

                  var10000 = 1145202131 * var1.field5668 - 1145202131 * var2.field5668;
               } else {
                  var10000 = 1145202131 * var2.field5668 - 1145202131 * var1.field5668;
               }

               return var10000;
            }
         }

         return this.method9983(var1, var2, 1476272604);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ek.az(" + 41);
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method3923((ChatPlayer)var1, (ChatPlayer)var2, (short)26972);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ek.compare(" + 41);
      }
   }

   public classEK(boolean var1) {
      this.field1774 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("af")
   int method3924(ChatPlayer var1, ChatPlayer var2) {
      if (-760305529 * var1.field5667 != 0 && var2.field5667 * -760305529 != 0) {
         return this.field1774 ? 1145202131 * var1.field5668 - 1145202131 * var2.field5668 : 1145202131 * var2.field5668 - 1145202131 * var1.field5668;
      } else {
         return this.method9983(var1, var2, 1841521752);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method3926(Object var1, Object var2) {
      return this.method3923((ChatPlayer)var1, (ChatPlayer)var2, (short)31267);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lek;Lri;Lri;)I")
   @ObfuscatedName("xc")
   public static int method3925(classEK var0, ChatPlayer var1, ChatPlayer var2) {
      if (var0 == null) {
         var0.method3924(var1, var1);
      }

      if (-438323549 * var1.field5667 != 0 && var2.field5667 * -760305529 != 0) {
         return var0.field1774 ? 1145202131 * var1.field5668 - 1145202131 * var2.field5668 : 1145202131 * var2.field5668 - 1145202131 * var1.field5668;
      } else {
         return var0.method9983(var1, var2, 1535978393);
      }
   }

   @ObfuscatedSignature(descriptor = "([JB)Lte;")
   @ObfuscatedName("ay")
   static classTE method3929(long[] var0, byte var1) {
      try {
         classTE var2 = new classTE(classYY.field7113, true);
         var2.field6125 = var0;
         var2.field6129 = var0.length * -1063196331;
         var2.field6121 = var0.length * 404453943;
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ek.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)I")
   @ObfuscatedName("aj")
   public static int method3928(int var0, int var1, int var2, byte var3) {
      try {
         if (var1 < var0) {
            throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
         } else {
            return Math.max(var0, Math.min(var2, var1));
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ek.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method3927(Object var1, Object var2) {
      return this.method3923((ChatPlayer)var1, (ChatPlayer)var2, (short)28438);
   }
}
