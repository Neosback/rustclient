import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vh")
public class classVH extends classVZ {
   @ObfuscatedSignature(descriptor = "[Luq;")
   @ObfuscatedName("ac")
   final classUQ[] field6509;
   @ObfuscatedName("af")
   final int[][] field6504;
   @ObfuscatedSignature(descriptor = "[Luq;")
   @ObfuscatedName("aa")
   final classUQ[] field6508;
   @ObfuscatedName("ab")
   final int[] field6510;
   @ObfuscatedName("ag")
   final int[] field6505;
   @ObfuscatedName("as")
   final int[] field6506;
   @ObfuscatedSignature(descriptor = "[Luq;")
   @ObfuscatedName("ax")
   final classUQ[] field6507;
   @ObfuscatedName("az")
   final int[][] field6502 = new int[16][4];
   @ObfuscatedName("ae")
   final int[] field6503;

   classVH(int var1, int var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5, var6);
      this.field6504 = new int[16][4];
      this.field6503 = new int[16];
      this.field6510 = new int[16];
      this.field6505 = new int[16];
      this.field6506 = new int[16];
      this.field6507 = new classUQ[16];
      this.field6509 = new classUQ[16];
      this.field6508 = new classUQ[16];

      for (int var7 = 0; var7 < 16; var7++) {
         int var8 = 128 * var7;
         int var9 = classGP.method5582(var8, 1208260883);
         int var10 = classGV.field2703[var8 & 2047];
         this.field6503[var7] = -(var9 * var3 - var4 * var10 >> 16);
         this.field6510[var7] = var10 * var3 + var4 * var9 >> 16;
         int var12 = this.field6648 * 1905552099 * var9 - this.field6645 * 455672321 * var10 >> 16;
         int var13 = var9 * this.field6645 * 455672321 + var10 * 1905552099 * this.field6648 >> 16;
         int var14 = 455672321 * this.field6645 * var10 + 1905552099 * this.field6648 * var9 >> 16;
         int var15 = var9 * this.field6645 * 455672321 - var10 * this.field6648 * 1905552099 >> 16;
         this.field6502[var7] = new int[4];
         this.field6504[var7] = new int[4];
         this.field6502[var7][0] = this.field6503[var7] - var12;
         this.field6504[var7][0] = var13 + this.field6510[var7];
         this.field6502[var7][1] = var14 + this.field6503[var7];
         this.field6504[var7][1] = var15 + this.field6510[var7];
         this.field6502[var7][2] = this.field6503[var7] + var12;
         this.field6504[var7][2] = this.field6510[var7] - var13;
         this.field6502[var7][3] = this.field6503[var7] - var14;
         this.field6504[var7][3] = this.field6510[var7] - var15;
         int var16 = this.field6648 * 1905552099 * var9 >> 16;
         int var17 = this.field6648 * 1905552099 * var10 >> 16;
         int var18 = this.field6503[var7] - var16;
         int var19 = this.field6510[var7] + var17;
         int var20 = this.field6503[var7] + var16;
         int var21 = this.field6510[var7] - var17;
         this.field6505[var7] = Math.abs(var10 * 455672321 * this.field6645) >> 16;
         this.field6506[var7] = Math.abs(var9 * 455672321 * this.field6645) >> 16;
         int var22 = Math.min(this.field6502[var7][0], Math.min(this.field6502[var7][1], Math.min(this.field6502[var7][2], this.field6502[var7][3])));
         int var23 = Math.max(this.field6502[var7][0], Math.max(this.field6502[var7][1], Math.max(this.field6502[var7][2], this.field6502[var7][3])));
         int var24 = Math.min(this.field6504[var7][0], Math.min(this.field6504[var7][1], Math.min(this.field6504[var7][2], this.field6504[var7][3])));
         int var25 = Math.max(this.field6504[var7][0], Math.max(this.field6504[var7][1], Math.max(this.field6504[var7][2], this.field6504[var7][3])));
         this.field6507[var7] = new classUQ(var22, var24, var23 - var22, var25 - var24);
         var22 = Math.min(this.field6502[var7][2], Math.min(this.field6502[var7][3], Math.min(var20, var18)));
         var23 = Math.max(this.field6502[var7][2], Math.max(this.field6502[var7][3], Math.max(var20, var18)));
         var24 = Math.min(this.field6504[var7][2], Math.min(this.field6504[var7][3], Math.min(var21, var19)));
         var25 = Math.max(this.field6504[var7][2], Math.max(this.field6504[var7][3], Math.max(var21, var19)));
         this.field6509[var7] = new classUQ(var22, var24, var23 - var22, var25 - var24);
         var22 = Math.min(this.field6502[var7][0], Math.min(this.field6502[var7][1], Math.min(var18, var20)));
         var23 = Math.max(this.field6502[var7][0], Math.max(this.field6502[var7][1], Math.max(var18, var20)));
         var24 = Math.min(this.field6504[var7][0], Math.min(this.field6504[var7][1], Math.min(var19, var21)));
         var25 = Math.max(this.field6504[var7][0], Math.max(this.field6504[var7][1], Math.max(var19, var21)));
         this.field6508[var7] = new classUQ(var22, var24, var23 - var22, var25 - var24);
      }
   }

   public classVH(int var1, int var2, int var3, int var4) {
      this(var1, var2, var3, var4, 2359552, 0);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   int method12026(int var1, int var2) {
      try {
         return this.field6510[var1 & 15];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vh.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   int method12022(int var1, int var2, int var3) {
      try {
         return this.field6504[var1][var2];
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vh.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyy;Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("um")
   public static Object method12030(classYY var0, classXY var1) {
      return var0.field7108.vmethod653(var1, (byte)-24);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   int method12024(int var1, int var2) {
      try {
         return this.field6503[var1 & 15];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vh.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aa")
   int method12025(int var1) {
      return this.field6503[var1 & 15];
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ac")
   int method12019(int var1, int var2) {
      return this.field6502[var1][var2];
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("az")
   int method12020(int var1, int var2, byte var3) {
      try {
         return this.field6502[var1][var2];
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "vh.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnc;II)I")
   @ObfuscatedName("dr")
   public static int method12029(classNC var0, int var1, int var2) {
      if (var0 == null) {
         var0.method7820();
      }

      if (var2 == -894745750) {
         int var7 = var0.field4644.method13095(-346779531);
         int var8 = var0.field4644.method13172(-890567453);
         if (var7 == 47) {
            var0.field4644.field6953 += var8 * 382150135;
            return 1;
         } else if (var7 == 81) {
            int var5 = var0.field4644.method13105(-758448158);
            var8 -= 3;
            int var6 = var0.field4654[var1];
            var0.field4653 = var0.field4653 + (long)var6 * (var0.field4651 - var5);
            var0.field4651 = var5;
            var0.field4644.field6953 += var8 * -1315956917;
            return 2;
         } else {
            var0.field4644.field6953 += var8 * -1095856699;
            return 3;
         }
      } else {
         byte var3 = classNC.field4645[var2 - -1043136130];
         int var4 = var2;
         if (var3 >= 1) {
            var4 = var2 | var0.field4644.method13095(-346779531) << 8;
         }

         if (var3 >= 2) {
            var4 |= var0.field4644.method13095(-346779531) << 16;
         }

         return var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   int method12027(int var1) {
      return this.field6510[var1 & 15];
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   int method12023(int var1, int var2) {
      return this.field6504[var1][var2];
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   int method12028(int var1) {
      return this.field6510[var1 & 15];
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   int method12021(int var1, int var2) {
      return this.field6502[var1][var2];
   }
}
