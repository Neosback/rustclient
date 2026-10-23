import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xs")
public class PacketBuffer extends classXY implements net.runelite.api.PacketBuffer {
   @ObfuscatedName("az")
   static final int[] field6924 = new int[]{
      0,
      1,
      3,
      7,
      15,
      31,
      63,
      127,
      255,
      511,
      1023,
      2047,
      4095,
      8191,
      16383,
      32767,
      65535,
      131071,
      262143,
      524287,
      1048575,
      2097151,
      4194303,
      8388607,
      16777215,
      33554431,
      67108863,
      134217727,
      268435455,
      536870911,
      1073741823,
      Integer.MAX_VALUE,
      -1
   };
   @ObfuscatedSignature(descriptor = "Lyt;")
   @ObfuscatedName("af")
   classYT field6927;
   @ObfuscatedName("ae")
   int field6925;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   public static final int field6926 = 1;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   public void method12960(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + this.field6927.method13667((byte)1));
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod649(int var1) {
      try {
         super.vmethod649(-1721213701);
         this.field6927 = null;
         this.field6925 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xs.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([II)V")
   @ObfuscatedName("af")
   public void method12955(int[] var1, int var2) {
      try {
         this.field6927 = new classYT(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xs.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("as")
   public boolean method12969(int var1) {
      try {
         int var2 = this.field6952[this.field6953 * 702114061] - this.field6927.method13670(-1969645377) & 0xFF;
         if (var2 < 128) {
            if (var1 != 127450546) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            return true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xs.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;)V")
   @ObfuscatedName("eh")
   public static void method12979(PacketBuffer var0) {
      var0.field6925 = var0.field6953 * -1286969624;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public int method12965(int var1) {
      try {
         return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xs.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   public void method12980() {
      this.field6925 = this.field6953 * -1286969624;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;)Z")
   @ObfuscatedName("zr")
   public static boolean method12970(PacketBuffer var0) {
      if (var0 == null) {
         return var0.method12972();
      } else {
         int var1 = var0.field6952[var0.field6953 * 702114061] - var0.field6927.method13670(-2023992503) & 0xFF;
         return var1 >= 128;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ax")
   public int method12973(byte var1) {
      try {
         int var2 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF;
         if (var2 < 128) {
            if (var1 <= 2) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            return (var2 - 128 << 8) + (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xs.ax(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("dk")
   public static void method12961(PacketBuffer var0, int var1) {
      if (var0 == null) {
         var0.method12954(var1);
      } else {
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + var0.field6927.method13667((byte)1));
      }
   }

   @ObfuscatedSignature(descriptor = "([BIII)V")
   @ObfuscatedName("ac")
   public void method12976(byte[] var1, int var2, int var3, int var4) {
      try {
         for (int var5 = 0; var5 < var3; var5++) {
            if (var4 >= 107076391) {
               throw new IllegalStateException();
            }

            var1[var5 + var2] = (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1));
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xs.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aa")
   public void method12981(byte var1) {
      try {
         this.field6925 = this.field6953 * -1286969624;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xs.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   @Override
   public void vmethod650() {
      super.vmethod649(-1688114718);
      this.field6927 = null;
      this.field6925 = 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   public void method12987(int var1) {
      try {
         this.field6953 = (7 + this.field6925 * -653463055) / 8 * -1095856699;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xs.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bw")
   public void method12988() {
      this.field6953 = (7 + this.field6925 * -723669565) / 8 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   public boolean method12971() {
      int var1 = this.field6952[this.field6953 * 702114061] - this.field6927.method13670(-1984765983) & 0xFF;
      return var1 >= 1819681526;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ay")
   public int method12991(int var1, int var2) {
      try {
         return var1 * 8 - this.field6925 * -653463055;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xs.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   public void method12962(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 670931612 - 1] = (byte)(var1 + this.field6927.method13667((byte)1));
   }

   public PacketBuffer(int var1) {
      super(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lyt;)V")
   @ObfuscatedName("au")
   public void method12956(classYT var1) {
      this.field6927 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lyt;)V")
   @ObfuscatedName("ai")
   public void method12957(classYT var1) {
      this.field6927 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lyt;)V")
   @ObfuscatedName("ar")
   public void method12958(classYT var1) {
      this.field6927 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   public void method12963(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + this.field6927.method13667((byte)1));
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public int method12966() {
      return this.field6952[(this.field6953 += -1095856699) * 305740227 - 1] - this.field6927.method13667((byte)1) & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ab")
   public void method12964(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + this.field6927.method13667((byte)1));
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyt;I)V")
   @ObfuscatedName("ae")
   public void method12959(classYT var1, int var2) {
      try {
         this.field6927 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xs.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bc")
   public int method12974() {
      int var1 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF;
      return var1 < 128
         ? var1
         : (var1 - 128 << 8) + (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bp")
   public int method12975() {
      int var1 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF;
      return var1 < 128
         ? var1
         : (var1 - 128 << 8) + (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1) & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("br")
   public void method12977(byte[] var1, int var2, int var3) {
      for (int var4 = 0; var4 < var3; var4++) {
         var1[var4 + var2] = (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - this.field6927.method13667((byte)1));
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("bt")
   public void method12978(byte[] var1, int var2, int var3) {
      for (int var4 = 0; var4 < var3; var4++) {
         var1[var4 + var2] = (byte)(this.field6952[(this.field6953 += -1067028931) * 702114061 - 1] - this.field6927.method13667((byte)1));
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aj")
   public int method12982(int var1, int var2) {
      try {
         int var3 = -653463055 * this.field6925 >> 3;
         int var4 = 8 - (this.field6925 * -653463055 & 7);
         int var5 = 0;

         for (this.field6925 += var1 * -2104238319; var1 > var4; var4 = 8) {
            var5 += (this.field6952[var3++] & field6924[var4]) << var1 - var4;
            var1 -= var4;
         }

         if (var4 == var1) {
            if (var2 <= -1001260316) {
               throw new IllegalStateException();
            }

            var5 += this.field6952[var3] & field6924[var4];
         } else {
            var5 += this.field6952[var3] >> var4 - var1 & field6924[var1];
         }

         return var5;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xs.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   @Override
   public void vmethod651() {
      super.vmethod649(-1854594923);
      this.field6927 = null;
      this.field6925 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;)I")
   @ObfuscatedName("ft")
   public static int method12967(PacketBuffer var0) {
      return var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] - var0.field6927.method13667((byte)1) & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   @Override
   public void vmethod652() {
      super.vmethod649(-1678019292);
      this.field6927 = null;
      this.field6925 = 0;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bu")
   public int method12983(int var1) {
      int var2 = -653463055 * this.field6925 >> 3;
      int var3 = 8 - (this.field6925 * -653463055 & 7);
      int var4 = 0;

      for (this.field6925 += var1 * -1158609679; var1 > var3; var3 = 8) {
         var4 += (this.field6952[var2++] & field6924[var3]) << var1 - var3;
         var1 -= var3;
      }

      if (var3 == var1) {
         var4 += this.field6952[var2] & field6924[var3];
      } else {
         var4 += this.field6952[var2] >> var3 - var1 & field6924[var1];
      }

      return var4;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ba")
   public int method12984(int var1) {
      int var2 = -653463055 * this.field6925 >> 3;
      int var3 = 8 - (this.field6925 * -2133011107 & 7);
      int var4 = 0;

      for (this.field6925 += var1 * -2104238319; var1 > var3; var3 = 8) {
         var4 += (this.field6952[var2++] & field6924[var3]) << var1 - var3;
         var1 -= var3;
      }

      if (var3 == var1) {
         var4 += this.field6952[var2] & field6924[var3];
      } else {
         var4 += this.field6952[var2] >> var3 - var1 & field6924[var1];
      }

      return var4;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   public void method12989() {
      this.field6953 = (7 + this.field6925 * -1058862881) / 8 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bi")
   public void method12990() {
      this.field6953 = (7 + this.field6925 * -653463055) / 8 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("du")
   static void method12993(int var0, int var1, int var2, int var3, int var4, int var5) {
      try {
         classCZ.method2851(classCQ.field1218, var0, var1, var2, var3, var4, -287734684);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xs.du(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aw")
   public void method12954(int var1) {
      try {
         super.vmethod649(-1721213701);
         this.field6927 = null;
         this.field6925 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xs.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxs;)I")
   @ObfuscatedName("jo")
   public static int method12968(PacketBuffer var0) {
      return var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] - var0.field6927.method13667((byte)1) & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ao")
   public int method12985(int var1, int var2) {
      try {
         int var3 = -653463055 * this.field6925 >> 3;
         int var4 = 8 - (this.field6925 * -653463055 & 7);
         int var5 = 0;

         for (this.field6925 += var1 * -2104238319; var1 > var4; var4 = 8) {
            var5 += (this.field6952[var3++] & field6924[var4]) << var1 - var4;
            var1 -= var4;
         }

         if (var4 == var1) {
            if (var2 <= -1001260316) {
               throw new IllegalStateException();
            }

            var5 += this.field6952[var3] & field6924[var4];
         } else {
            var5 += this.field6952[var3] >> var4 - var1 & field6924[var1];
         }

         return var5;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xs.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;II)I")
   @ObfuscatedName("og")
   public static int method12992(PacketBuffer var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method12985(var1, var1);
      } else {
         try {
            return var1 - 702114061 * var0.field6953;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "xs.ay(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bn")
   public boolean method12972() {
      int var1 = this.field6952[this.field6953 * 702114061] - this.field6927.method13670(-1984765983) & 0xFF;
      return var1 >= 1819681526;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bo")
   public int method12986(int var1) {
      int var2 = 512124769 * this.field6925 >> 3;
      int var3 = 8 - (this.field6925 * -653463055 & 7);
      int var4 = 0;

      for (this.field6925 += var1 * -1278857492; var1 > var3; var3 = 8) {
         var4 += (this.field6952[var2++] & field6924[var3]) << var1 - var3;
         var1 -= var3;
      }

      if (var3 == var1) {
         var4 += this.field6952[var2] & field6924[var3];
      } else {
         var4 += this.field6952[var2] >> var3 - var1 & field6924[var1];
      }

      return var4;
   }
}
