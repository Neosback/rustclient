import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nc")
public class classNC {
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4646 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4649 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4648 = 1;
   @ObfuscatedName("ay")
   int[] field4657;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field4650 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4652 = 500000;
   @ObfuscatedName("as")
   static final byte[] field4645 = new byte[]{
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      1,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      2,
      0,
      1,
      2,
      1,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0
   };
   @ObfuscatedName("ac")
   int field4647;
   @ObfuscatedName("aa")
   int[] field4654;
   @ObfuscatedName("ao")
   int field4651;
   @ObfuscatedName("al")
   int[] field4656;
   @ObfuscatedName("aj")
   int[] field4655;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("ax")
   classXY field4644 = new classXY(null);
   @ObfuscatedName("aq")
   long field4653;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bs")
   int method7850() {
      int var1 = this.field4655.length;
      int var2 = -1;
      int var3 = Integer.MAX_VALUE;

      for (int var4 = 0; var4 < var1; var4++) {
         if (this.field4655[var4] >= 0 && this.field4654[var4] < var3) {
            var2 = var4;
            var3 = this.field4654[var4];
         }
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnc;)I")
   @ObfuscatedName("dt")
   public static int method7821(classNC var0) {
      return var0.field4655.length;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnc;I)I")
   @ObfuscatedName("gq")
   public static int method7836(classNC var0, int var1) {
      int var2 = var0.field4644.field6952[var0.field4644.field6953 * 702114061];
      if (var2 < 0) {
         var2 &= 255;
         var0.field4657[var1] = var2;
         var0.field4644.field6953 += -1095856699;
      } else {
         var2 = var0.field4657[var1];
      }

      if (var2 != 240 && var2 != 247) {
         return var0.method7841(var1, var2);
      } else {
         int var3 = var0.field4644.method13172(-945193828);
         if (var2 == 247 && var3 > 0) {
            int var4 = var0.field4644.field6952[var0.field4644.field6953 * 702114061] & 255;
            if (var4 >= 241 && var4 <= 243 || var4 == 246 || var4 == 248 || var4 >= 250 && var4 <= 252 || var4 == 254) {
               var0.field4644.field6953 += -1095856699;
               var0.field4657[var1] = var4;
               return var0.method7841(var1, var4);
            }
         }

         var0.field4644.field6953 += var3 * -1095856699;
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ah")
   void method7823(int var1) {
      this.field4644.field6953 = this.field4655[var1] * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("az")
   void method7810(byte[] var1) {
      this.field4644.field6952 = var1;
      this.field4644.field6953 = 1926334898;
      int var2 = this.field4644.method13099(-1420262210);
      this.field4647 = this.field4644.method13099(525215387);
      this.field4651 = 500000;
      this.field4656 = new int[var2];
      int var3 = 0;

      while (var3 < var2) {
         int var4 = classXY.method13113(this.field4644, (byte)1);
         int var5 = classXY.method13113(this.field4644, (byte)1);
         if (var4 == 1297379947) {
            this.field4656[var3] = this.field4644.field6953 * 702114061;
            var3++;
         }

         this.field4644.field6953 += var5 * -1095856699;
      }

      this.field4653 = 0L;
      this.field4655 = new int[var2];

      for (int var6 = 0; var6 < var2; var6++) {
         this.field4655[var6] = this.field4656[var6];
      }

      this.field4654 = new int[var2];
      this.field4657 = new int[var2];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnc;)Z")
   @ObfuscatedName("ut")
   public static boolean method7855(classNC var0) {
      if (var0 == null) {
         var0.method7853();
      }

      int var1 = var0.field4655.length;

      for (int var2 = 0; var2 < var1; var2++) {
         if (var0.field4655[var2] >= 0) {
            return false;
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(Lnc;I)I")
   @ObfuscatedName("wz")
   public static int method7837(classNC var0, int var1) {
      if (var0 == null) {
         var0.method7840(var1);
      }

      int var2 = var0.field4644.field6952[var0.field4644.field6953 * 702114061];
      if (var2 < 0) {
         var2 &= 255;
         var0.field4657[var1] = var2;
         var0.field4644.field6953 += -1095856699;
      } else {
         var2 = var0.field4657[var1];
      }

      if (var2 != 240 && var2 != 247) {
         return var0.method7841(var1, var2);
      } else {
         int var3 = var0.field4644.method13172(-1917731728);
         if (var2 == 247 && var3 > 0) {
            int var4 = var0.field4644.field6952[var0.field4644.field6953 * 702114061] & 255;
            if (var4 >= 241 && var4 <= 243 || var4 == 246 || var4 == 248 || var4 >= 250 && var4 <= 252 || var4 == 254) {
               var0.field4644.field6953 += -1095856699;
               var0.field4657[var1] = var4;
               return var0.method7841(var1, var4);
            }
         }

         var0.field4644.field6953 += var3 * -1095856699;
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   void method7813() {
      this.field4644.field6952 = null;
      this.field4656 = null;
      this.field4655 = null;
      this.field4654 = null;
      this.field4657 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   boolean method7817() {
      return this.field4644.field6952 != null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   int method7822() {
      return this.field4655.length;
   }

   @ObfuscatedSignature(descriptor = "(Lnc;J)V")
   @ObfuscatedName("ca")
   public static void method7859(classNC var0, long var1) {
      if (var0 == null) {
         var0.method7862(var1);
      }

      var0.field4653 = var1;
      int var3 = var0.field4655.length;

      for (int var4 = 0; var4 < var3; var4++) {
         var0.field4654[var4] = 0;
         var0.field4657[var4] = 0;
         var0.field4644.field6953 = var0.field4656[var4] * -1095856699;
         classTO.method10989(var0, var4);
         var0.field4655[var4] = var0.field4644.field6953 * 702114061;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   void method7824(int var1) {
      this.field4644.field6953 = this.field4655[var1] * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   void method7825(int var1) {
      this.field4655[var1] = this.field4644.field6953 * 702114061;
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("by")
   long method7845(int var1) {
      return this.field4653 + (long)var1 * this.field4651;
   }

   classNC(byte[] var1) {
      this.method7810(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   int method7835(int var1) {
      return method7837(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("al")
   int method7841(int var1, int var2) {
      if (var2 == 255) {
         int var7 = this.field4644.method13095(-346779531);
         int var8 = this.field4644.method13172(-1869452137);
         if (var7 == 47) {
            this.field4644.field6953 += var8 * -1095856699;
            return 1;
         } else if (var7 == 81) {
            int var5 = this.field4644.method13105(-758448158);
            var8 -= 3;
            int var6 = this.field4654[var1];
            this.field4653 = this.field4653 + (long)var6 * (this.field4651 - var5);
            this.field4651 = var5;
            this.field4644.field6953 += var8 * -1095856699;
            return 2;
         } else {
            this.field4644.field6953 += var8 * -1095856699;
            return 3;
         }
      } else {
         byte var3 = field4645[var2 - 128];
         int var4 = var2;
         if (var3 >= 1) {
            var4 = var2 | this.field4644.method13095(-346779531) << 8;
         }

         if (var3 >= 2) {
            var4 |= this.field4644.method13095(-346779531) << 16;
         }

         return var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnc;)I")
   @ObfuscatedName("js")
   public static int method7851(classNC var0) {
      if (var0 == null) {
         return var0.method7854();
      } else {
         int var1 = var0.field4655.length;
         int var2 = -1;
         int var3 = Integer.MAX_VALUE;

         for (int var4 = 0; var4 < var1; var4++) {
            if (var0.field4655[var4] >= 0 && var0.field4654[var4] < var3) {
               var2 = var4;
               var3 = var0.field4654[var4];
            }
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("aj")
   long method7846(int var1) {
      return this.field4653 + (long)var1 * this.field4651;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bk")
   int method7838(int var1) {
      int var2 = this.field4644.field6952[this.field4644.field6953 * 702114061];
      if (var2 < 0) {
         var2 &= 255;
         this.field4657[var1] = var2;
         this.field4644.field6953 += -1095856699;
      } else {
         var2 = this.field4657[var1];
      }

      if (var2 != 240 && var2 != 247) {
         return this.method7841(var1, var2);
      } else {
         int var3 = this.field4644.method13172(-1086726186);
         if (var2 == 247 && var3 > 0) {
            int var4 = this.field4644.field6952[this.field4644.field6953 * 702114061] & 255;
            if (var4 >= 241 && var4 <= 243 || var4 == 246 || var4 == 248 || var4 >= 250 && var4 <= 252 || var4 == 254) {
               this.field4644.field6953 += -1095856699;
               this.field4657[var1] = var4;
               return this.method7841(var1, var4);
            }
         }

         this.field4644.field6953 += var3 * -1095856699;
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bx")
   void method7826(int var1) {
      this.field4655[var1] = this.field4644.field6953 * 702114061;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bn")
   void method7827(int var1) {
      this.field4655[var1] = this.field4644.field6953 * 702114061;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   void method7828() {
      this.field4644.field6953 = -1074980126;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("au")
   void method7811(byte[] var1) {
      this.field4644.field6952 = var1;
      this.field4644.field6953 = 1926334898;
      int var2 = this.field4644.method13099(521387646);
      this.field4647 = this.field4644.method13099(1146209906);
      this.field4651 = 500000;
      this.field4656 = new int[var2];
      int var3 = 0;

      while (var3 < var2) {
         int var4 = classXY.method13113(this.field4644, (byte)1);
         int var5 = classXY.method13113(this.field4644, (byte)1);
         if (var4 == 1297379947) {
            this.field4656[var3] = this.field4644.field6953 * 702114061;
            var3++;
         }

         this.field4644.field6953 += var5 * -1095856699;
      }

      this.field4653 = 0L;
      this.field4655 = new int[var2];

      for (int var6 = 0; var6 < var2; var6++) {
         this.field4655[var6] = this.field4656[var6];
      }

      this.field4654 = new int[var2];
      this.field4657 = new int[var2];
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   void method7814() {
      this.field4644.field6952 = null;
      this.field4656 = null;
      this.field4655 = null;
      this.field4654 = null;
      this.field4657 = null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bd")
   int method7852() {
      int var1 = this.field4655.length;
      int var2 = -1;
      int var3 = Integer.MAX_VALUE;

      for (int var4 = 0; var4 < var1; var4++) {
         if (this.field4655[var4] >= 0 && this.field4654[var4] < var3) {
            var2 = var4;
            var3 = this.field4654[var4];
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   void method7815() {
      this.field4644.field6952 = null;
      this.field4656 = null;
      this.field4655 = null;
      this.field4654 = null;
      this.field4657 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ak")
   boolean method7818() {
      return this.field4644.field6952 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("av")
   boolean method7819() {
      return this.field4644.field6952 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("at")
   boolean method7820() {
      return this.field4644.field6952 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bv")
   boolean method7856() {
      int var1 = this.field4655.length;

      for (int var2 = 0; var2 < var1; var2++) {
         if (this.field4655[var2] >= 0) {
            return false;
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bc")
   void method7829() {
      this.field4644.field6953 = 1095856699;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bp")
   void method7830() {
      this.field4644.field6953 = -167842463;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("br")
   void method7831() {
      this.field4644.field6953 = 1095856699;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   void method7832() {
      this.field4644.field6953 = 1095856699;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bt")
   void method7833(int var1) {
      int var2 = this.field4644.method13172(-604448193);
      this.field4654[var1] = this.field4654[var1] + var2;
   }

   classNC() {
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aa")
   int method7839(int var1) {
      int var2 = this.field4644.field6952[this.field4644.field6953 * 702114061];
      if (var2 < 0) {
         var2 &= 255;
         this.field4657[var1] = var2;
         this.field4644.field6953 += -1095856699;
      } else {
         var2 = this.field4657[var1];
      }

      if (var2 != 240 && var2 != 247) {
         return this.method7841(var1, var2);
      } else {
         int var3 = this.field4644.method13172(-1014127211);
         if (var2 == 247 && var3 > 0) {
            int var4 = this.field4644.field6952[this.field4644.field6953 * 702114061] & 255;
            if (var4 >= 241 && var4 <= 243 || var4 == 246 || var4 == 248 || var4 >= 250 && var4 <= 252 || var4 == 254) {
               this.field4644.field6953 += -1095856699;
               this.field4657[var1] = var4;
               return this.method7841(var1, var4);
            }
         }

         this.field4644.field6953 += var3 * -1095856699;
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("bi")
   long method7847(int var1) {
      return this.field4653 + (long)var1 * this.field4651;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bu")
   int method7842(int var1, int var2) {
      if (var2 == 255) {
         int var7 = this.field4644.method13095(-346779531);
         int var8 = this.field4644.method13172(-1521398117);
         if (var7 == 47) {
            this.field4644.field6953 += var8 * -1095856699;
            return 1;
         } else if (var7 == 81) {
            int var5 = this.field4644.method13105(-758448158);
            var8 -= 3;
            int var6 = this.field4654[var1];
            this.field4653 = this.field4653 + (long)var6 * (this.field4651 - var5);
            this.field4651 = var5;
            this.field4644.field6953 += var8 * -1095856699;
            return 2;
         } else {
            this.field4644.field6953 += var8 * -1095856699;
            return 3;
         }
      } else {
         byte var3 = field4645[var2 - 128];
         int var4 = var2;
         if (var3 >= 1) {
            var4 = var2 | this.field4644.method13095(-346779531) << 8;
         }

         if (var3 >= 2) {
            var4 |= this.field4644.method13095(-346779531) << 16;
         }

         return var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ba")
   int method7843(int var1, int var2) {
      if (var2 == 255) {
         int var7 = this.field4644.method13095(-346779531);
         int var8 = this.field4644.method13172(-892171095);
         if (var7 == 47) {
            this.field4644.field6953 += var8 * -1095856699;
            return 1;
         } else if (var7 == 81) {
            int var5 = this.field4644.method13105(-758448158);
            var8 -= 3;
            int var6 = this.field4654[var1];
            this.field4653 = this.field4653 + (long)var6 * (this.field4651 - var5);
            this.field4651 = var5;
            this.field4644.field6953 += var8 * -1095856699;
            return 2;
         } else {
            this.field4644.field6953 += var8 * -1095856699;
            return 3;
         }
      } else {
         byte var3 = field4645[var2 - 128];
         int var4 = var2;
         if (var3 >= 1) {
            var4 = var2 | this.field4644.method13095(-346779531) << 8;
         }

         if (var3 >= 2) {
            var4 |= this.field4644.method13095(-346779531) << 16;
         }

         return var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bw")
   int method7844(int var1, int var2) {
      if (var2 == 255) {
         int var7 = this.field4644.method13095(-346779531);
         int var8 = this.field4644.method13172(-1109954305);
         if (var7 == 47) {
            this.field4644.field6953 += var8 * -1517860803;
            return 1;
         } else if (var7 == 914786944) {
            int var5 = this.field4644.method13105(-758448158);
            var8 -= 3;
            int var6 = this.field4654[var1];
            this.field4653 = this.field4653 + (long)var6 * (this.field4651 - var5);
            this.field4651 = var5;
            this.field4644.field6953 += var8 * -1095856699;
            return 2;
         } else {
            this.field4644.field6953 += var8 * -98035949;
            return 3;
         }
      } else {
         byte var3 = field4645[var2 - -1087043011];
         int var4 = var2;
         if (var3 >= 1) {
            var4 = var2 | this.field4644.method13095(-346779531) << 8;
         }

         if (var3 >= 2) {
            var4 |= this.field4644.method13095(-346779531) << 16;
         }

         return var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("be")
   long method7848(int var1) {
      return this.field4653 + (long)var1 * this.field4651;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   boolean method7857() {
      int var1 = this.field4655.length;

      for (int var2 = 0; var2 < var1; var2++) {
         if (this.field4655[var2] >= 0) {
            return false;
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ap")
   void method7812(byte[] var1) {
      this.field4644.field6952 = var1;
      this.field4644.field6953 = 1926334898;
      int var2 = this.field4644.method13099(1174598881);
      this.field4647 = this.field4644.method13099(-970218992);
      this.field4651 = 500000;
      this.field4656 = new int[var2];
      int var3 = 0;

      while (var3 < var2) {
         int var4 = classXY.method13113(this.field4644, (byte)1);
         int var5 = classXY.method13113(this.field4644, (byte)1);
         if (var4 == -1360957737) {
            this.field4656[var3] = this.field4644.field6953 * 1130038081;
            var3++;
         }

         this.field4644.field6953 += var5 * -1095856699;
      }

      this.field4653 = 0L;
      this.field4655 = new int[var2];

      for (int var6 = 0; var6 < var2; var6++) {
         this.field4655[var6] = this.field4656[var6];
      }

      this.field4654 = new int[var2];
      this.field4657 = new int[var2];
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("bq")
   long method7849(int var1) {
      return this.field4653 + (long)var1 * this.field4651;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bf")
   int method7853() {
      int var1 = this.field4655.length;
      int var2 = -1;
      int var3 = 2076686818;

      for (int var4 = 0; var4 < var1; var4++) {
         if (this.field4655[var4] >= 0 && this.field4654[var4] < var3) {
            var2 = var4;
            var3 = this.field4654[var4];
         }
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   void method7816() {
      this.field4644.field6952 = null;
      this.field4656 = null;
      this.field4655 = null;
      this.field4654 = null;
      this.field4657 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bh")
   boolean method7858() {
      int var1 = this.field4655.length;

      for (int var2 = 0; var2 < var1; var2++) {
         if (this.field4655[var2] >= 0) {
            return false;
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("cr")
   void method7860(long var1) {
      this.field4653 = var1;
      int var3 = this.field4655.length;

      for (int var4 = 0; var4 < var3; var4++) {
         this.field4654[var4] = 0;
         this.field4657[var4] = 0;
         this.field4644.field6953 = this.field4656[var4] * -2082209081;
         classTO.method10989(this, var4);
         this.field4655[var4] = this.field4644.field6953 * 1805312808;
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ad")
   void method7861(long var1) {
      this.field4653 = var1;
      int var3 = this.field4655.length;

      for (int var4 = 0; var4 < var3; var4++) {
         this.field4654[var4] = 0;
         this.field4657[var4] = 0;
         this.field4644.field6953 = this.field4656[var4] * -1095856699;
         classTO.method10989(this, var4);
         this.field4655[var4] = this.field4644.field6953 * 702114061;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bo")
   int method7840(int var1) {
      int var2 = this.field4644.field6952[this.field4644.field6953 * 702114061];
      if (var2 < 0) {
         var2 &= 255;
         this.field4656[var1] = var2;
         this.field4644.field6953 += -1095856699;
      } else {
         var2 = this.field4654[var1];
      }

      if (var2 != 240 && var2 != 247) {
         return this.method7841(var1, var2);
      } else {
         int var3 = this.field4644.method13107(-1917731728);
         if (var2 == 247 && var3 > 0) {
            int var4 = this.field4644.field6952[this.field4644.field6953 * 702114061] & 255;
            if (var4 >= 241 && var4 <= 243 || var4 == 246 || var4 == 248 || var4 >= 250 && var4 <= 252 || var4 == 254) {
               this.field4644.field6953 += -1095856699;
               this.field4657[var1] = var4;
               return this.method7844(var1, var4);
            }
         }

         this.field4644.field6953 += var3 * -1095856699;
         return 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("bz")
   void method7862(long var1) {
      this.field4653 = var1;
      int var3 = this.field4656.length;

      for (int var4 = 0; var4 < var3; var4++) {
         this.field4657[var4] = 0;
         this.field4657[var4] = 0;
         this.field4644.field6953 = this.field4655[var4] * -1095856699;
         classTO.method10989(this, var4);
         this.field4655[var4] = this.field4644.field6953 * 702114061;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ac")
   void method7834(int var1) {
      int var2 = this.field4644.method13300(-1379519921);
      this.field4656[var1] = this.field4656[var1] + var2;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   int method7854() {
      int var1 = this.field4656.length;
      int var2 = -1;
      int var3 = Integer.MAX_VALUE;

      for (int var4 = 0; var4 < var1; var4++) {
         if (this.field4656[var4] >= 0 && this.field4655[var4] < var3) {
            var2 = var4;
            var3 = this.field4655[var4];
         }
      }

      return var2;
   }
}
