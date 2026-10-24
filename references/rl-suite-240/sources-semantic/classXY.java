import java.math.BigInteger;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xy")
public class classXY extends Node {
   @ObfuscatedName("aj")
   public byte[] field6952;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field6957 = 5000;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final int field6958 = 30000;
   @ObfuscatedName("ao")
   public static long[] field6954 = new long[256];
   @ObfuscatedName("al")
   static int[] field6955 = new int[256];
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field6956 = 100;
   @ObfuscatedName("ay")
   public int field6953;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;[BII)V")
   @ObfuscatedName("vx")
   public static void method13306(classXY var0, byte[] var1, int var2, int var3) {
      if (var0 == null) {
         var0.method13276();
      }

      for (int var4 = var2 + var3 - 1; var4 >= var2; var4--) {
         var1[var4] = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1];
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lz")
   public void method13243(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + 128);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)I")
   @ObfuscatedName("pg")
   public static int method13251(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13253(var1);
      }

      try {
         var0.field6953 += 2103253898;
         return ((var0.field6952[var0.field6953 * 702114061 - 2] & 0xFF) << 8) + (var0.field6952[var0.field6953 * 702114061 - 1] - 128 & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.eg(" + 41);
      }
   }

   public classXY(byte[] var1) {
      this.field6952 = var1;
      this.field6953 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)B")
   @ObfuscatedName("rt")
   public static byte method13227(classXY var0) {
      return (byte)(var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] - 128);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)J")
   @ObfuscatedName("ho")
   public static long method13114(classXY var0) {
      if (var0 == null) {
         return var0.method13118();
      } else {
         long var1 = method13113(var0, (byte)1) & 4294967295L;
         long var3 = method13113(var0, (byte)1) & 4294967295L;
         return var3 + (var1 << 32);
      }
   }

   @ObfuscatedSignature(descriptor = "(IZB)V")
   @ObfuscatedName("bq")
   void method13015(int var1, boolean var2, byte var3) {
      try {
         this.vmethod649(-1910474756);
         this.field6952 = classTO.method10987(var1, var2, 1154989421);
         this.field6953 = 0;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.bq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("zv")
   public int method13312() {
      return this.method13099(-1008441641);
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("sb")
   public byte[] method13313() {
      return this.field6952;
   }

   @ObfuscatedSignature(descriptor = "([I)V")
   @ObfuscatedName("jx")
   public void method13181(int[] var1) {
      int var2 = this.field6953 * 567469678 / 8;
      this.field6953 = 0;

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = method13113(this, (byte)1);
         int var5 = method13113(this, (byte)1);
         int var6 = 0;
         int var7 = -1640531527;
         int var8 = -472401010;

         while (var8-- > 0) {
            var4 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var6 & 3] + var6;
            var6 += var7;
            var5 += var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6;
         }

         this.field6953 -= -176919000;
         this.method13037(var4, 446561558);
         this.method13037(var5, 1300884961);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ex")
   public int method13258(byte var1) {
      try {
         this.field6953 += 2103253898;
         int var2 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 8) + (this.field6952[702114061 * this.field6953 - 2] & 255);
         if (var2 > 32767) {
            var2 -= 65536;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ex(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bz")
   public void method13031(int var1, int var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.bv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bh")
   public void method13032(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.bh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;[BIII)V")
   @ObfuscatedName("nn")
   public static void method13307(classXY var0, byte[] var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method13308(var1, var2, var2, var2);
      } else {
         try {
            for (int var5 = var2 + var3 - 1; var5 >= var2; var5--) {
               var1[var5] = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1];
            }
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "xy.ep(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("pl")
   public int method13314() {
      return this.field6953 * 702114061;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cy")
   public void method13037(int var1, int var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.bz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("cr")
   public void method13039(long var1) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 40);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cr(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;S)V")
   @ObfuscatedName("ct")
   public void method13054(String var1, short var2) {
      try {
         int var3 = var1.indexOf(0);
         if (var3 >= 0) {
            if (var2 <= 185) {
               throw new IllegalStateException();
            } else {
               throw new IllegalArgumentException("");
            }
         } else {
            this.field6953 = this.field6953 + classOR.method8764(var1, 0, var1.length(), this.field6952, 702114061 * this.field6953, 305293424) * -1095856699;
            this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.ct(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("cp")
   public void method13057(String var1, int var2) {
      try {
         int var3 = var1.indexOf(0);
         if (var3 >= 0) {
            if (var2 != -966414083) {
               throw new IllegalStateException();
            } else {
               throw new IllegalArgumentException("");
            }
         } else {
            this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
            this.field6953 = this.field6953 + classOR.method8764(var1, 0, var1.length(), this.field6952, 702114061 * this.field6953, 305293424) * -1095856699;
            this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.cp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iv")
   public int method13147() {
      int var1 = this.field6952[this.field6953 * 1381285267] & 1189011997;
      return var1 < -1013609909 ? this.method13095(-346779531) : this.method13099(181741858) - 2028574499;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("cd")
   public void method13068(classXY var1, byte var2) {
      try {
         this.method13067(var1.field6952, 0, var1.field6953 * 702114061, (byte)51);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cd(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("dz")
   public int method13177(byte var1) {
      try {
         int var2 = this.method13176(-1614020296);
         return var2 >>> 1 ^ -(var2 & 1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.dz(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bv")
   public void method13074(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == -467790787) {
               throw new IllegalStateException();
            }

            if (var1 <= 65535) {
               this.field6952[702114061 * this.field6953 - var1 - 2] = (byte)(var1 >> 8);
               this.field6952[702114061 * this.field6953 - var1 - 1] = (byte)var1;
               return;
            }

            if (var2 == -467790787) {
               throw new IllegalStateException();
            }
         }

         throw new IllegalArgumentException();
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lf")
   public void method13241(int var1) {
      this.field6952[(this.field6953 += 1193493350) * -2084340756 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += 2135393065) * 702114061 - 1] = (byte)(1644197307 + var1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jg")
   public int method13166() {
      if (this.field6952[this.field6953 * 702114061] < 0) {
         return method13113(this, (byte)1) & 1101508388;
      } else {
         int var1 = this.method13099(673927479);
         return var1 == 902300339 ? -1 : var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("ky")
   public byte method13233() {
      return (byte)(128 - this.field6952[(this.field6953 += 1750683781) * 702114061 - 1]);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ml")
   public int method13299() {
      this.field6953 += -88459500;
      return ((this.field6952[this.field6953 * 702114061 - 2] & 0xFF) << 24)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 4] & 0xFF) << 8)
         + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("ua")
   public void method13315(byte[] var1, int var2, int var3) {
      this.method13140(var1, var2, var3, (byte)94);
   }

   @ObfuscatedSignature(descriptor = "(B)B")
   @ObfuscatedName("cn")
   public byte method13097(byte var1) {
      try {
         return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ch")
   public int method13099(int var1) {
      try {
         this.field6953 += 2103253898;
         return ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 1] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ch(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("js")
   public int method13178() {
      int var1 = this.method13176(-442548348);
      return var1 >>> 1 ^ -(var1 & 1);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cg")
   public int method13105(int var1) {
      try {
         this.field6953 += 1007397199;
         return (this.field6952[this.field6953 * 702114061 - 1] & 0xFF)
            + ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 8)
            + ((this.field6952[this.field6953 * 702114061 - 3] & 0xFF) << 16);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cg(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cw")
   public int method13107(int var1) {
      try {
         this.field6953 += 1007397199;
         int var2 = (this.field6952[this.field6953 * 702114061 - 1] & 255)
            + ((this.field6952[this.field6953 * 702114061 - 3] & 255) << 16)
            + ((this.field6952[this.field6953 * 702114061 - 2] & 255) << 8);
         if (var2 > 8388607) {
            if (var1 != 831951752) {
               throw new IllegalStateException();
            }

            var2 -= 16777216;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cw(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("mw")
   public static int method13144(classXY var0) {
      int var1 = var0.field6952[var0.field6953 * 193865929] & 255;
      return var1 < -2058339421 ? var0.method13095(-346779531) - 979202461 : var0.method13099(-557719652) - 49152;
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("cx")
   public float method13119(int var1) {
      try {
         return Float.intBitsToFloat(method13113(this, (byte)1));
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cx(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("yj")
   public static int method13145(classXY var0) {
      int var1 = var0.field6952[var0.field6953 * 702114061] & 255;
      return var1 < 128 ? var0.method13095(-346779531) - 64 : var0.method13099(1069490016) - 49152;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ce")
   public boolean method13122(byte var1) {
      try {
         boolean var10000;
         if ((this.method13095(-346779531) & 1) == 1) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ce(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("gk")
   public static int method13248(classXY var0) {
      if (var0 == null) {
         var0.method13318();
      }

      var0.field6953 += 2103253898;
      return ((var0.field6952[var0.field6953 * 702114061 - 1] & 0xFF) << 8) + (var0.field6952[var0.field6953 * 702114061 - 2] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("cm")
   public String method13125(byte var1) {
      try {
         if (this.field6952[702114061 * this.field6953] == 0) {
            if (var1 == 1) {
               throw new IllegalStateException();
            } else {
               this.field6953 += -1095856699;
               return null;
            }
         } else {
            return this.method13127(393802544);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("dc")
   public String method13127(int var1) {
      try {
         int var2 = 702114061 * this.field6953;

         while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
            if (var1 <= -1369556939) {
               throw new IllegalStateException();
            }
         }

         int var3 = this.field6953 * 702114061 - var2 - 1;
         return 0 == var3 ? "" : classKQ.method6590(this.field6952, var2, var3, (byte)-95);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.dc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BIIB)V")
   @ObfuscatedName("dx")
   public void method13140(byte[] var1, int var2, int var3, byte var4) {
      try {
         for (int var5 = var2; var5 < var2 + var3; var5++) {
            if (var4 <= 1) {
               throw new IllegalStateException();
            }

            var1[var5] = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xy.dx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("mc")
   public void method13309(byte[] var1, int var2, int var3) {
      for (int var4 = var3 + var2 - 1; var4 >= var2; var4--) {
         var1[var4] = (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128);
      }
   }

   @ObfuscatedSignature(descriptor = "([IIII)V")
   @ObfuscatedName("dg")
   public void method13191(int[] var1, int var2, int var3, int var4) {
      try {
         int var5 = this.field6953 * 702114061;
         this.field6953 = var2 * -1095856699;
         int var6 = (var3 - var2) / 8;

         for (int var7 = 0; var7 < var6; var7++) {
            if (var4 != -1664008178) {
               return;
            }

            int var8 = method13113(this, (byte)1);
            int var9 = method13113(this, (byte)1);
            int var10 = -957401312;
            int var11 = -1640531527;
            int var12 = 32;

            while (var12-- > 0) {
               if (var4 != -1664008178) {
                  throw new IllegalStateException();
               }

               var9 -= var8 + (var8 << 4 ^ var8 >>> 5) ^ var10 + var1[var10 >>> 11 & 3];
               var10 -= var11;
               var8 -= var9 + (var9 << 4 ^ var9 >>> 5) ^ var10 + var1[var10 & 3];
            }

            this.field6953 -= -176919000;
            this.method13037(var8, -265300598);
            this.method13037(var9, 490672456);
         }

         this.field6953 = var5 * -1095856699;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "xy.dg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("rf")
   public void method13316(int var1) {
      method13029(this, var1, (byte)-55);
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("kl")
   public byte method13231() {
      return (byte)(0 - this.field6952[(this.field6953 += 74762743) * 1105930774 - 1]);
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("dt")
   public int method13156(byte var1) {
      try {
         short var2 = 0;

         int var3;
         for (var3 = this.method13150((short)28463); 32767 == var3; var3 = this.method13150((short)9880)) {
            if (var1 <= 2) {
               throw new IllegalStateException();
            }

            var2 += 32767;
         }

         return var2 + var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.dt(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("dr")
   public int method13158(byte var1) {
      try {
         int var2 = this.method13095(-346779531);
         if (var2 < 252) {
            if (var1 == 1) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            return (var2 - 252 << 8) + this.method13095(-346779531);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.dr(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void vmethod649(int var1) {
      try {
         if (null != this.field6952) {
            classUA.method11298(this.field6952, 2022759572);
         }

         this.field6952 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.az(" + ')');
      }
   }

   public classXY(int var1, boolean var2) {
      this.method13015(var1, var2, (byte)55);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/String;")
   @ObfuscatedName("uo")
   public static String method13135(classXY var0) {
      byte var1 = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1];
      if (0 != var1) {
         throw new IllegalStateException("");
      } else {
         int var2 = var0.method13172(-1717263042);
         if (702114061 * var0.field6953 + var2 > var0.field6952.length) {
            throw new IllegalStateException("");
         } else {
            String var3 = classBF.method999(var0.field6952, 702114061 * var0.field6953, var2, (byte)-96);
            var0.field6953 += var2 * -1095856699;
            return var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxf;I)I")
   @ObfuscatedName("dh")
   public int method13173(classXF var1, int var2) {
      try {
         switch (var1.vmethod648((byte)32).field6928 * 539966297) {
            case 0:
               return this.method13097((byte)17);
            case 1:
               return this.method13095(-346779531);
            case 2:
               return this.method13104((byte)16);
            case 3:
               return this.method13099(-217498571);
            case 4:
               return this.method13107(831951752);
            case 5:
               return this.method13105(-758448158);
            case 6:
               return method13113(this, (byte)1);
            default:
               throw new IllegalArgumentException();
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.dh(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hm")
   public void method13085(int var1) {
      if ((var1 & 1267271701) != 0) {
         if ((var1 & -16384) != 0) {
            if ((var1 & -1774545842) != 0) {
               if ((var1 & 1958504830) != 0) {
                  method13029(this, var1 >>> 28 | 128, (byte)-48);
               }

               method13029(this, var1 >>> 21 | 128, (byte)-106);
            }

            method13029(this, var1 >>> 14 | 128, (byte)-27);
         }

         method13029(this, var1 >>> 7 | 128, (byte)-62);
      }

      method13029(this, var1 & -700163830, (byte)-109);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mu")
   public int method13292() {
      this.field6953 += -88459500;
      return ((this.field6952[1987360038 * this.field6953 - 3] & 0xFF) << 8)
         + ((this.field6952[this.field6953 * 1485073752 - 1] & 0xFF) << 24)
         + ((this.field6952[702114061 * this.field6953 - 2] & -569776059) << 16)
         + (this.field6952[this.field6953 * 702114061 - 4] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "([IB)V")
   @ObfuscatedName("dn")
   public void method13182(int[] var1, byte var2) {
      try {
         int var3 = this.field6953 * 702114061 / 8;
         this.field6953 = 0;

         for (int var4 = 0; var4 < var3; var4++) {
            int var5 = method13113(this, (byte)1);
            int var6 = method13113(this, (byte)1);
            int var7 = 0;
            int var8 = -1640531527;
            int var9 = 32;

            while (var9-- > 0) {
               if (var2 == 1) {
                  throw new IllegalStateException();
               }

               var5 += var6 + (var6 << 4 ^ var6 >>> 5) ^ var1[var7 & 3] + var7;
               var7 += var8;
               var6 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var7 >>> 11 & 3] + var7;
            }

            this.field6953 -= -176919000;
            this.method13037(var5, -701378243);
            this.method13037(var6, -710888510);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "xy.dn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([II)V")
   @ObfuscatedName("dv")
   public void method13186(int[] var1, int var2) {
      try {
         int var3 = 702114061 * this.field6953 / 8;
         this.field6953 = 0;

         for (int var4 = 0; var4 < var3; var4++) {
            if (var2 != 1656256084) {
               throw new IllegalStateException();
            }

            int var5 = method13113(this, (byte)1);
            int var6 = method13113(this, (byte)1);
            int var7 = -957401312;
            int var8 = -1640531527;
            int var9 = 32;

            while (var9-- > 0) {
               if (var2 != 1656256084) {
                  throw new IllegalStateException();
               }

               var6 -= var5 + (var5 << 4 ^ var5 >>> 5) ^ var7 + var1[var7 >>> 11 & 3];
               var7 -= var8;
               var5 -= var6 + (var6 << 4 ^ var6 >>> 5) ^ var1[var7 & 3] + var7;
            }

            this.field6953 -= -176919000;
            this.method13037(var5, 1547450637);
            this.method13037(var6, 915468689);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "xy.dv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("he")
   public int method13108() {
      this.field6953 += -938827362;
      int var1 = (this.field6952[this.field6953 * 853215974 - 1] & -1789864503)
         + ((this.field6952[this.field6953 * 702114061 - 3] & 1482456563) << 16)
         + ((this.field6952[this.field6953 * 1657656786 - 2] & -1191743872) << 8);
      if (var1 > 8388607) {
         var1 -= -905156477;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("dp")
   public int method13218(byte var1) {
      try {
         return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128 & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.dp(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)J")
   @ObfuscatedName("cd")
   public static long method13115(classXY var0, int var1) {
      if (var0 == null) {
         return var0.method13117(var1);
      } else {
         try {
            long var2 = method13113(var0, (byte)1) & 4294967295L;
            long var4 = method13113(var0, (byte)1) & 4294967295L;
            return var4 + (var2 << 32);
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "xy.ci(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("xl")
   public static int method13094(classXY var0) {
      return var0 == null ? var0.method13096() : var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Ljava/lang/CharSequence;)V")
   @ObfuscatedName("hi")
   public static void method13059(classXY var0, CharSequence var1) {
      if (var0 == null) {
         var0.method13293();
      }

      int var2 = classAC.method304(var1, (byte)74);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = 0;
      var0.method13087(var2, (byte)15);
      int var3 = var0.field6953;
      byte[] var5 = var0.field6952;
      int var6 = var0.field6953 * 1951849305;
      int var7 = var1.length();
      int var8 = var6;

      for (int var9 = 0; var9 < var7; var9++) {
         char var10 = var1.charAt(var9);
         if (var10 <= 1419252854) {
            var5[var8++] = (byte)var10;
         } else if (var10 <= 2047) {
            var5[var8++] = (byte)(-676197916 | var10 >> 6);
            var5[var8++] = (byte)(128 | var10 & 1613020380);
         } else {
            var5[var8++] = (byte)(224 | var10 >> '\f');
            var5[var8++] = (byte)(-2112376634 | var10 >> 6 & 222512753);
            var5[var8++] = (byte)(-1351942012 | var10 & '?');
         }
      }

      int var4 = var8 - var6;
      var0.field6953 = var3 + -1095856699 * var4;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/math/BigInteger;Ljava/math/BigInteger;I)V")
   @ObfuscatedName("dl")
   public void method13195(BigInteger var1, BigInteger var2, int var3) {
      try {
         int var4 = this.field6953 * 702114061;
         this.field6953 = 0;
         byte[] var5 = new byte[var4];
         this.method13140(var5, 0, var4, (byte)127);
         BigInteger var6 = new BigInteger(var5);
         BigInteger var7 = var6.modPow(var1, var2);
         byte[] var8 = var7.toByteArray();
         this.field6953 = 0;
         this.method13031(var8.length, -1542382992);
         this.method13067(var8, 0, var8.length, (byte)40);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "xy.dl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("if")
   public void method13141(byte[] var1, int var2, int var3) {
      for (int var4 = var2; var4 < var2 + var3; var4++) {
         var1[var4] = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("co")
   public void method13209(int var1, int var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + 128);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ds(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("dj")
   public void method13211(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(0 - var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.dj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("da")
   public void method13214(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(128 - var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.da(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("em")
   public void method13284(int var1, int var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ef(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ea")
   public int method13225(int var1) {
      try {
         return 128 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ea(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)B")
   @ObfuscatedName("es")
   public byte method13232(byte var1) {
      try {
         return (byte)(0 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1]);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.es(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("qp")
   public static void method13289(classXY var0, int var1) {
      var0.field6952[(var0.field6953 += 1284233221) * 702114061 - 1] = (byte)(var1 >> 16);
      var0.field6952[(var0.field6953 += -1719166144) * 1826159714 - 1] = (byte)(var1 >> 24);
      var0.field6952[(var0.field6953 += -1095856699) * 1119306866 - 1] = (byte)var1;
      var0.field6952[(var0.field6953 += -1095856699) * -510214640 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mq")
   public int method13293() {
      this.field6953 += -88459500;
      return ((this.field6952[702114061 * this.field6953 - 3] & 0xFF) << 8)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 24)
         + ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 16)
         + (this.field6952[this.field6953 * 702114061 - 4] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void vmethod652() {
      if (null != this.field6952) {
         classUA.method11298(this.field6952, 2022759572);
      }

      this.field6952 = null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fi")
   public int method13317() {
      return this.method13146(-1383485269);
   }

   @ObfuscatedSignature(descriptor = "(IS)V")
   @ObfuscatedName("el")
   public void method13244(int var1, short var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + 128);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.el(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("en")
   public int method13254(int var1) {
      try {
         this.field6953 += 2103253898;
         return ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 2] - 128 & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.en(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("dy")
   public int method13146(int var1) {
      try {
         int var2 = this.field6952[this.field6953 * 702114061] & 255;
         if (var2 < 128) {
            if (var1 >= 16776960) {
               throw new IllegalStateException();
            } else {
               return this.method13095(-346779531) - 64;
            }
         } else {
            return this.method13099(1484717513) - 49152;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.dy(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kw")
   public int method13219() {
      return this.field6952[(this.field6953 += -823724438) * 702114061 - 1] - 128 & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hp")
   public void method13091(int var1) {
      var1 = var1 << 1 ^ var1 >> 31;
      this.method13090(var1, (byte)33);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)I")
   @ObfuscatedName("yn")
   public static int method13269(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13273(var1);
      }

      try {
         var0.field6953 += 1007397199;
         return ((var0.field6952[702114061 * var0.field6953 - 2] & 0xFF) << 8)
            + ((var0.field6952[702114061 * var0.field6953 - 1] & 0xFF) << 16)
            + (var0.field6952[702114061 * var0.field6953 - 3] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.eo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ew")
   public int method13278(int var1) {
      try {
         this.field6953 += 1007397199;
         int var2 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 8)
            + ((this.field6952[702114061 * this.field6953 - 3] & 255) << 16)
            + (this.field6952[702114061 * this.field6953 - 2] & 255);
         if (var2 > 8388607) {
            var2 -= 16777216;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ew(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("fl")
   void method13016(int var1, boolean var2) {
      this.vmethod649(-1721927912);
      this.field6952 = classTO.method10987(var1, var2, 1154989421);
      this.field6953 = 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mw")
   public int method13274() {
      this.field6953 += 1007397199;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 2] & 255) << 8)
         + (this.field6952[this.field6953 * 702114061 - 3] & 255);
      if (var1 > 8388607) {
         var1 -= 16777216;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("aw")
   public static int method13294(classXY var0) {
      if (var0 == null) {
         var0.method13102();
      }

      var0.field6953 += -88459500;
      return ((var0.field6952[702114061 * var0.field6953 - 3] & 0xFF) << 8)
         + ((var0.field6952[var0.field6953 * 702114061 - 1] & 0xFF) << 24)
         + ((var0.field6952[702114061 * var0.field6953 - 2] & 0xFF) << 16)
         + (var0.field6952[var0.field6953 * 702114061 - 4] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ei")
   public int method13295(int var1) {
      try {
         this.field6953 += -88459500;
         return ((this.field6952[702114061 * this.field6953 - 3] & 0xFF) << 8)
            + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 24)
            + ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 16)
            + (this.field6952[this.field6953 * 702114061 - 4] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ei(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ee")
   public int method13300(int var1) {
      try {
         this.field6953 += -88459500;
         return ((this.field6952[this.field6953 * 702114061 - 2] & 0xFF) << 24)
            + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 16)
            + ((this.field6952[this.field6953 * 702114061 - 4] & 0xFF) << 8)
            + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ee(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("eb")
   public int method13303(byte var1) {
      try {
         this.field6953 += -88459500;
         return (this.field6952[702114061 * this.field6953 - 2] & 0xFF)
            + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 8)
            + ((this.field6952[702114061 * this.field6953 - 4] & 0xFF) << 16)
            + ((this.field6952[this.field6953 * 702114061 - 3] & 0xFF) << 24);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.eb(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BIII)V")
   @ObfuscatedName("ec")
   public void method13310(byte[] var1, int var2, int var3, int var4) {
      try {
         for (int var5 = var3 + var2 - 1; var5 >= var2; var5--) {
            if (var4 <= 750051714) {
               throw new IllegalStateException();
            }

            var1[var5] = (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xy.ec(" + ')');
      }
   }

   static {
      for (int var1 = 0; var1 < 256; var1++) {
         int var0 = var1;

         for (int var2 = 0; var2 < 8; var2++) {
            if ((var0 & 1) == 1) {
               var0 = var0 >>> 1 ^ -306674912;
            } else {
               var0 >>>= 1;
            }
         }

         field6955[var1] = var0;
      }

      for (int var5 = 0; var5 < 256; var5++) {
         long var4 = var5;

         for (int var3 = 0; var3 < 8; var3++) {
            if ((var4 & 1L) == 1L) {
               var4 = var4 >>> 1 ^ -3932672073523589310L;
            } else {
               var4 >>>= 1;
            }
         }

         field6954[var5] = var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("il")
   public static int method13249(classXY var0) {
      if (var0 == null) {
         var0.method13128();
      }

      var0.field6953 += -1455235636;
      return ((var0.field6952[var0.field6953 * 264827605 - 1] & 0xFF) << 8) + (var0.field6952[var0.field6953 * 814475767 - 2] & 0xFF);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("vd")
   public static void method13025(classXY var0, int var1) {
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("jb")
   public void method13192(int[] var1, int var2, int var3) {
      int var4 = this.field6953 * 702114061;
      this.field6953 = var2 * -1095856699;
      int var5 = (var3 - var2) / 8;

      for (int var6 = 0; var6 < var5; var6++) {
         int var7 = method13113(this, (byte)1);
         int var8 = method13113(this, (byte)1);
         int var9 = -957401312;
         int var10 = -1640531527;
         int var11 = 32;

         while (var11-- > 0) {
            var8 -= var7 + (var7 << 4 ^ var7 >>> 5) ^ var9 + var1[var9 >>> 11 & 3];
            var9 -= var10;
            var7 -= var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3];
         }

         this.field6953 -= -176919000;
         this.method13037(var7, 1128193373);
         this.method13037(var8, 396811701);
      }

      this.field6953 = var4 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("gm")
   public void method13069(classXY var1) {
      this.method13067(var1.field6952, 0, var1.field6953 * 702114061, (byte)67);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("il")
   public String method13136() {
      byte var1 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      if (0 != var1) {
         throw new IllegalStateException("");
      } else {
         int var2 = this.method13172(-1432241281);
         if (702114061 * this.field6953 + var2 > this.field6952.length) {
            throw new IllegalStateException("");
         } else {
            String var3 = classBF.method999(this.field6952, 702114061 * this.field6953, var2, (byte)-26);
            this.field6953 += var2 * -1095856699;
            return var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("fo")
   void method13017(int var1, boolean var2) {
      this.vmethod649(-1634418800);
      this.field6952 = classTO.method10987(var1, var2, 1154989421);
      this.field6953 = 0;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("it")
   public String method13132() {
      byte var1 = this.field6952[(this.field6953 += -1095856699) * 2046008688 - 1];
      if (0 != var1) {
         throw new IllegalStateException("");
      } else {
         int var2 = -932858373 * this.field6953;

         while (this.field6952[(this.field6953 += -1509055837) * 1003278507 - 1] != 0) {
         }

         int var3 = 702114061 * this.field6953 - var2 - 1;
         return 0 == var3 ? "" : classKQ.method6590(this.field6952, var2, var3, (byte)-9);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("yy")
   public static int method13170(classXY var0) {
      byte var1 = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1];

      int var2;
      for (var2 = 0; var1 < 0; var1 = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1]) {
         var2 = (var2 | var1 & 127) << 7;
      }

      return var2 | var1;
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("fj")
   void method13018(int var1, boolean var2) {
      this.vmethod649(-1809870631);
      this.field6952 = classTO.method10987(var1, var2, 1154989421);
      this.field6953 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("pl")
   public static int method13261(classXY var0) {
      if (var0 == null) {
         var0.method13297();
      }

      var0.field6953 += 2103253898;
      int var1 = ((var0.field6952[702114061 * var0.field6953 - 2] & 255) << 8) + (var0.field6952[var0.field6953 * 702114061 - 1] - 128 & 0xFF);
      if (var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BII)I")
   @ObfuscatedName("fd")
   static int method13019(byte[] var0, int var1, int var2) {
      int var3 = -1;

      for (int var4 = var1; var4 < var2; var4++) {
         var3 = var3 >>> 8 ^ field6955[(var3 ^ var0[var4]) & -417317427];
      }

      return ~var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BI)I")
   @ObfuscatedName("fy")
   public static int method13020(byte[] var0, int var1) {
      return classPY.method9423(var0, 0, var1, -1989390138);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;J)V")
   @ObfuscatedName("cs")
   public static void method13046(classXY var0, long var1) {
      var0.field6952[(var0.field6953 += -1603988889) * 702114061 - 1] = (byte)(var1 >> 56);
      var0.field6952[(var0.field6953 += -1095856699) * -14991749 - 1] = (byte)(var1 >> 48);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 826860753);
      var0.field6952[(var0.field6953 += 643732765) * 702114061 - 1] = (byte)(var1 >> 32);
      var0.field6952[(var0.field6953 += -975985165) * 656952373 - 1] = (byte)(var1 >> 24);
      var0.field6952[(var0.field6953 += -1095856699) * -531556949 - 1] = (byte)(var1 >> 16);
      var0.field6952[(var0.field6953 += -346827319) * 702114061 - 1] = (byte)(var1 >> 8);
      var0.field6952[(var0.field6953 += -441374056) * -281416531 - 1] = (byte)var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BI)I")
   @ObfuscatedName("fg")
   public static int method13021(byte[] var0, int var1) {
      return classPY.method9423(var0, 0, var1, -2033880481);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BI)I")
   @ObfuscatedName("fi")
   public static int method13022(byte[] var0, int var1) {
      return classPY.method9423(var0, 0, var1, -1786775575);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lu")
   public int method13255() {
      this.field6953 += 2103253898;
      return ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 2] - 467618478 & 207023864);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("av")
   public static int method13167(classXY var0) {
      if (var0.field6952[var0.field6953 * 702114061] < 0) {
         return method13113(var0, (byte)1) & 2147483647;
      } else {
         int var1 = var0.method13099(-1524327791);
         return var1 == 32767 ? -1 : var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;J)V")
   @ObfuscatedName("fi")
   public static void method13047(classXY var0, long var1) {
      if (var0 == null) {
         var0.method13045(var1);
      } else {
         try {
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 56);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 48);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 40);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "xy.cu(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("fu")
   public static int method13024(String var0) {
      return var0.length() + 1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fz")
   public void method13026(int var1) {
      this.field6952[(this.field6953 += -1199341552) * 680942549 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("zj")
   public byte method13318() {
      return this.method13097((byte)17);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iu")
   public int method13148() {
      int var1 = this.field6952[this.field6953 * 702114061] & 1104873993;
      return var1 < 128 ? this.method13095(-346779531) : this.method13099(-1091999984) - 32768;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fq")
   public void method13027(int var1) {
      this.field6952[(this.field6953 += 1128119975) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fp")
   public void method13028(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("gv")
   public void method13055(String var1) {
      int var2 = var1.indexOf(0);
      if (var2 >= 0) {
         throw new IllegalArgumentException("");
      } else {
         this.field6953 = this.field6953 + classOR.method8764(var1, 0, var1.length(), this.field6952, 702114061 * this.field6953, 305293424) * -1095856699;
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fv")
   public void method13033(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 1935020851 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1771215539) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 1593212274 - 1] = (byte)var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("md")
   public static void method13086(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13323(var1);
      }

      if ((var1 & -128) != 0) {
         if ((var1 & -16384) != 0) {
            if ((var1 & -2097152) != 0) {
               if ((var1 & -268435456) != 0) {
                  method13029(var0, var1 >>> 28 | 128, (byte)-59);
               }

               method13029(var0, var1 >>> 21 | 128, (byte)-117);
            }

            method13029(var0, var1 >>> 14 | 128, (byte)-81);
         }

         method13029(var0, var1 >>> 7 | 128, (byte)-69);
      }

      method13029(var0, var1 & 127, (byte)-98);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IB)V")
   @ObfuscatedName("sr")
   public static void method13071(classXY var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method13217(var1, var2);
      } else {
         try {
            if (var1 < 0) {
               if (var2 == 104) {
                  throw new IllegalStateException();
               } else {
                  throw new IllegalArgumentException();
               }
            } else {
               var0.field6952[702114061 * var0.field6953 - var1 - 4] = (byte)(var1 >> 24);
               var0.field6952[702114061 * var0.field6953 - var1 - 3] = (byte)(var1 >> 16);
               var0.field6952[702114061 * var0.field6953 - var1 - 2] = (byte)(var1 >> 8);
               var0.field6952[var0.field6953 * 702114061 - var1 - 1] = (byte)var1;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "xy.cv(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fk")
   public void method13034(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mz")
   public int method13279() {
      this.field6953 += 1007397199;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 8)
         + ((this.field6952[702114061 * this.field6953 - 3] & 255) << 16)
         + (this.field6952[702114061 * this.field6953 - 2] & 255);
      if (var1 > 8388607) {
         var1 -= 16777216;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("fr")
   public void method13040(long var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 40);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ft")
   public void method13041(long var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 40);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;ZI)V")
   @ObfuscatedName("yu")
   public static void method13049(classXY var0, boolean var1, int var2) {
      if (var0 == null) {
         var0.method13053(var1, var2);
      }

      try {
         byte var10001;
         if (var1) {
            if (var2 != -1737488759) {
               return;
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         method13029(var0, var10001, (byte)-108);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ff")
   public void method13042(long var1) {
      this.field6952[(this.field6953 += -1686147559) * -331654220 - 1] = (byte)(var1 >> 2052552526);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> -80594588);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      this.field6952[(this.field6953 += 1861293942) * -500881110 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += 19499404) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("fa")
   public void method13048(long var1) {
      this.field6952[(this.field6953 += 1022654588) * 702114061 - 1] = (byte)(var1 >> 56);
      this.field6952[(this.field6953 += -1676737851) * 702114061 - 1] = (byte)(var1 >> 48);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 506646547);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      this.field6952[(this.field6953 += -1095856699) * 102047568 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 1438090105 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += 1355663608) * -393969585 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gq")
   public void method13050(boolean var1) {
      method13029(this, var1 ? 1 : 0, (byte)-91);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gb")
   public void method13051(boolean var1) {
      method13029(this, var1 ? 1 : 0, (byte)-65);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("gk")
   public void method13056(String var1) {
      int var2 = var1.indexOf(0);
      if (var2 >= 0) {
         throw new IllegalArgumentException("");
      } else {
         this.field6953 = this.field6953 + classOR.method8764(var1, 0, var1.length(), this.field6952, 702114061 * this.field6953, 305293424) * 1587581883;
         this.field6952[(this.field6953 += 442244364) * 314959979 - 1] = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("go")
   public void method13058(String var1) {
      int var2 = var1.indexOf(0);
      if (var2 >= 0) {
         throw new IllegalArgumentException("");
      } else {
         this.field6952[(this.field6953 += -1340200791) * 702114061 - 1] = 0;
         this.field6953 = this.field6953 + classOR.method8764(var1, 0, var1.length(), this.field6952, 1506017435 * this.field6953, 305293424) * -2142957717;
         this.field6952[(this.field6953 += -1095856699) * 128449052 - 1] = 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("yk")
   public static void method13286(classXY var0, int var1) {
      var0.field6952[(var0.field6953 += -1095856699) * 1011851338 - 1] = (byte)(var1 >> 8);
      var0.field6952[(var0.field6953 += -1948244849) * 702114061 - 1] = (byte)var1;
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      var0.field6952[(var0.field6953 += 1648634503) * 702114061 - 1] = (byte)(var1 >> 16);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;)V")
   @ObfuscatedName("gz")
   public void method13060(CharSequence var1) {
      int var2 = classAC.method304(var1, (byte)15);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
      this.method13087(var2, (byte)-8);
      int var3 = this.field6953;
      byte[] var5 = this.field6952;
      int var6 = this.field6953 * -1219401553;
      int var7 = var1.length();
      int var8 = var6;

      for (int var9 = 0; var9 < var7; var9++) {
         char var10 = var1.charAt(var9);
         if (var10 <= 127) {
            var5[var8++] = (byte)var10;
         } else if (var10 <= 2047) {
            var5[var8++] = (byte)(192 | var10 >> 6);
            var5[var8++] = (byte)(128 | var10 & -110900941);
         } else {
            var5[var8++] = (byte)(224 | var10 >> '\f');
            var5[var8++] = (byte)(2027591184 | var10 >> 6 & -1945094716);
            var5[var8++] = (byte)(128 | var10 & '?');
         }
      }

      int var4 = var8 - var6;
      this.field6953 = var3 + 1924630173 * var4;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;)V")
   @ObfuscatedName("ga")
   public void method13061(CharSequence var1) {
      int var2 = classAC.method304(var1, (byte)9);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
      this.method13087(var2, (byte)17);
      int var3 = this.field6953;
      byte[] var5 = this.field6952;
      int var6 = this.field6953 * 702114061;
      int var7 = var1.length();
      int var8 = var6;

      for (int var9 = 0; var9 < var7; var9++) {
         char var10 = var1.charAt(var9);
         if (var10 <= 127) {
            var5[var8++] = (byte)var10;
         } else if (var10 <= 2047) {
            var5[var8++] = (byte)(192 | var10 >> 6);
            var5[var8++] = (byte)(128 | var10 & '?');
         } else {
            var5[var8++] = (byte)(224 | var10 >> '\f');
            var5[var8++] = (byte)(128 | var10 >> 6 & 63);
            var5[var8++] = (byte)(128 | var10 & '?');
         }
      }

      int var4 = var8 - var6;
      this.field6953 = var3 + -1095856699 * var4;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kb")
   public int method13221() {
      return 0 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;)V")
   @ObfuscatedName("gw")
   public void method13062(CharSequence var1) {
      int var2 = classAC.method304(var1, (byte)54);
      this.field6952[(this.field6953 += 937734424) * -2144720155 - 1] = 0;
      this.method13087(var2, (byte)-89);
      int var3 = this.field6953;
      byte[] var5 = this.field6952;
      int var6 = this.field6953 * 763438068;
      int var7 = var1.length();
      int var8 = var6;

      for (int var9 = 0; var9 < var7; var9++) {
         char var10 = var1.charAt(var9);
         if (var10 <= -235996150) {
            var5[var8++] = (byte)var10;
         } else if (var10 <= 2047) {
            var5[var8++] = (byte)(138375470 | var10 >> 6);
            var5[var8++] = (byte)(1461619992 | var10 & 161015874);
         } else {
            var5[var8++] = (byte)(224 | var10 >> '\f');
            var5[var8++] = (byte)(-1090401455 | var10 >> 6 & 63);
            var5[var8++] = (byte)(-32890985 | var10 & -934239767);
         }
      }

      int var4 = var8 - var6;
      this.field6953 = var3 + -1095856699 * var4;
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("gg")
   public void method13066(byte[] var1, int var2, int var3) {
      for (int var4 = var2; var4 < var2 + var3; var4++) {
         this.field6952[(this.field6953 += -1748865270) * -548462840 - 1] = var1[var4];
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("gt")
   public void method13070(classXY var1) {
      this.method13067(var1.field6952, 0, var1.field6953 * 702114061, (byte)114);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gj")
   public void method13075(int var1) {
      if (var1 >= 0 && var1 <= 65535) {
         this.field6952[763660067 * this.field6953 - var1 - 2] = (byte)(var1 >> 8);
         this.field6952[702114061 * this.field6953 - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gx")
   public void method13076(int var1) {
      if (var1 >= 0 && var1 <= 65535) {
         this.field6952[702114061 * this.field6953 - var1 - 2] = (byte)(var1 >> 8);
         this.field6952[702114061 * this.field6953 - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ge")
   public void method13078(int var1) {
      if (var1 >= 0 && var1 <= 255) {
         this.field6952[this.field6953 * 702114061 - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hz")
   public void method13079(int var1) {
      if (var1 >= 0 && var1 <= 255) {
         this.field6952[this.field6953 * 702114061 - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "([I)V")
   @ObfuscatedName("jh")
   public void method13187(int[] var1) {
      int var2 = 702114061 * this.field6953 / 8;
      this.field6953 = 0;

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = method13113(this, (byte)1);
         int var5 = method13113(this, (byte)1);
         int var6 = -1624817924;
         int var7 = 1484499080;
         int var8 = -863059770;

         while (var8-- > 0) {
            var5 -= var4 + (var4 << 4 ^ var4 >>> 5) ^ var6 + var1[var6 >>> 11 & 3];
            var6 -= var7;
            var4 -= var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var6 & 3] + var6;
         }

         this.field6953 -= 643367028;
         this.method13037(var4, 1884081702);
         this.method13037(var5, 1083778016);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)I")
   @ObfuscatedName("qx")
   public static int method13262(classXY var0, byte var1) {
      if (var0 == null) {
         var0.method13265(var1);
      }

      try {
         var0.field6953 += 2103253898;
         int var2 = ((var0.field6952[702114061 * var0.field6953 - 2] & 255) << 8) + (var0.field6952[var0.field6953 * 702114061 - 1] - 128 & 0xFF);
         if (var2 > 32767) {
            var2 -= 65536;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.eq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hi")
   public void method13082(int var1) {
      if (var1 >= 0 && var1 < 128) {
         method13029(this, var1, (byte)-7);
      } else if (var1 >= 0 && var1 < 653603893) {
         this.method13031(32768 + var1, -1542382992);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("du")
   public int method13152(int var1) {
      try {
         int var2 = this.field6952[this.field6953 * 702114061] & 255;
         if (var2 < 128) {
            if (var1 <= -1422727666) {
               throw new IllegalStateException();
            } else {
               return this.method13095(-346779531) - 1;
            }
         } else {
            return this.method13099(-128414864) - 32769;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.du(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kj")
   public int method13222() {
      return 0 - this.field6952[(this.field6953 += -1095856699) * -1726304157 - 1] & 1831091440;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hl")
   void method13089(int var1) {
      while (var1 < 0 || var1 > 127) {
         method13029(this, 128 | var1 & 127, (byte)-44);
         var1 >>>= 7;
      }

      method13029(this, var1, (byte)-80);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;J)V")
   @ObfuscatedName("li")
   public static void method13043(classXY var0, long var1) {
      var0.field6952[(var0.field6953 += -1095856699) * -1872607112 - 1] = (byte)(var1 >> -1778874428);
      var0.field6952[(var0.field6953 += -1095856699) * -2020785323 - 1] = (byte)(var1 >> 32);
      var0.field6952[(var0.field6953 += 990512678) * 702114061 - 1] = (byte)(var1 >> 24);
      var0.field6952[(var0.field6953 += -1468723926) * 702114061 - 1] = (byte)(var1 >> 16);
      var0.field6952[(var0.field6953 += -472495183) * 938706690 - 1] = (byte)(var1 >> 8);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ih")
   public String method13128() {
      int var1 = 702114061 * this.field6953;

      while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
      }

      int var2 = this.field6953 * 702114061 - var1 - 1;
      return 0 == var2 ? "" : classKQ.method6590(this.field6952, var1, var2, (byte)-118);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hx")
   public void method13092(int var1) {
      var1 = var1 << 1 ^ var1 >> 31;
      this.method13090(var1, (byte)-32);
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ck")
   public void method13087(int var1, byte var2) {
      try {
         if ((var1 & -128) != 0) {
            if ((var1 & -16384) != 0) {
               if (var2 == 1) {
                  return;
               }

               if ((var1 & -2097152) != 0) {
                  if (var2 == 1) {
                     throw new IllegalStateException();
                  }

                  if ((var1 & -268435456) != 0) {
                     if (var2 == 1) {
                        throw new IllegalStateException();
                     }

                     method13029(this, var1 >>> 28 | 128, (byte)-115);
                  }

                  method13029(this, var1 >>> 21 | 128, (byte)-72);
               }

               method13029(this, var1 >>> 14 | 128, (byte)-27);
            }

            method13029(this, var1 >>> 7 | 128, (byte)-12);
         }

         method13029(this, var1 & 127, (byte)-13);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ck(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("hs")
   public boolean method13123() {
      return (this.method13095(-346779531) & 1) == 1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ha")
   public int method13100() {
      this.field6953 += 2103253898;
      return ((this.field6952[67149838 * this.field6953 - 2] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 1] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("er")
   public int method13266(int var1) {
      try {
         this.field6953 += 2103253898;
         int var2 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 8) + (this.field6952[this.field6953 * 702114061 - 2] - 128 & 0xFF);
         if (var2 > 32767) {
            if (var1 <= -4371317) {
               throw new IllegalStateException();
            }

            var2 -= 65536;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.er(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hu")
   public int method13102() {
      this.field6953 += 2103253898;
      int var1 = (this.field6952[336153451 * this.field6953 - 1] & 255) + ((this.field6952[702114061 * this.field6953 - 2] & -205230730) << 8);
      if (var1 > -1724683251) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hj")
   public int method13106() {
      this.field6953 += 1007397199;
      return (this.field6952[this.field6953 * 702114061 - 1] & 747140538)
         + ((this.field6952[-443083867 * this.field6953 - 2] & 0xFF) << 8)
         + ((this.field6952[this.field6953 * 702114061 - 3] & 0xFF) << 16);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hq")
   public int method13109() {
      this.field6953 += 1007397199;
      int var1 = (this.field6952[this.field6953 * 702114061 - 1] & 255)
         + ((this.field6952[this.field6953 * 702114061 - 3] & 255) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 2] & 255) << 8);
      if (var1 > 8388607) {
         var1 -= 16777216;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("hr")
   public byte method13098() {
      return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("db")
   public static void method13083(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13322();
      }

      if (var1 >= 0 && var1 < 128) {
         method13029(var0, var1, (byte)-77);
      } else if (var1 >= 0 && var1 < -764223002) {
         var0.method13031(-1076056430 + var1, -1542382992);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hy")
   public int method13110() {
      this.field6953 += -88459500;
      return (this.field6952[-1179954455 * this.field6953 - 1] & -2109733244)
         + ((this.field6952[this.field6953 * 836149807 - 2] & -409619611) << 8)
         + ((this.field6952[this.field6953 * 702114061 - 3] & 0xFF) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 4] & 671493176) << 24);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hh")
   public int method13111() {
      this.field6953 += -88459500;
      return (this.field6952[702114061 * this.field6953 - 1] & 0xFF)
         + ((this.field6952[this.field6953 * -1348338299 - 2] & -1494625167) << 8)
         + ((this.field6952[this.field6953 * 2011675830 - 3] & -1451914529) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 4] & 0xFF) << 24);
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("hc")
   public long method13116() {
      long var1 = method13113(this, (byte)1) & 4294967295L;
      long var3 = method13113(this, (byte)1) & 4294967295L;
      return var3 + (var1 << -1317137739);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iq")
   public int method13153() {
      int var1 = this.field6952[this.field6953 * 1262713184] & 120713348;
      return var1 < 1324328879 ? this.method13095(-346779531) - 1 : this.method13099(-744827610) - 32769;
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("hb")
   public float method13120() {
      return Float.intBitsToFloat(method13113(this, (byte)1));
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("hn")
   public float method13121() {
      return Float.intBitsToFloat(method13113(this, (byte)1));
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ln")
   public void method13245(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + 128);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fs")
   public void method13035(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ib")
   public String method13126() {
      if (this.field6952[-1756316449 * this.field6953] == 0) {
         this.field6953 += 1755796878;
         return null;
      } else {
         return this.method13127(290401256);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("is")
   public String method13129() {
      int var1 = 702114061 * this.field6953;

      while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
      }

      int var2 = this.field6953 * 702114061 - var1 - 1;
      return 0 == var2 ? "" : classKQ.method6590(this.field6952, var1, var2, (byte)-100);
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("cc")
   public void method13093(int var1, byte var2) {
      try {
         var1 = var1 << 1 ^ var1 >> 31;
         this.method13090(var1, (byte)68);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("la")
   public int method13263() {
      this.field6953 += 2052860826;
      int var1 = ((this.field6952[-1106394113 * this.field6953 - 2] & 255) << 8) + (this.field6952[this.field6953 * -695284266 - 1] - 128 & -2085775090);
      if (var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("et")
   public int method13275(int var1) {
      try {
         this.field6953 += 1007397199;
         int var2 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 16)
            + ((this.field6952[this.field6953 * 702114061 - 2] & 255) << 8)
            + (this.field6952[this.field6953 * 702114061 - 3] & 255);
         if (var2 > 8388607) {
            if (var1 >= 643717713) {
               throw new IllegalStateException();
            }

            var2 -= 16777216;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.et(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ho")
   public int method13103() {
      this.field6953 += 2103253898;
      int var1 = (this.field6952[702114061 * this.field6953 - 1] & 255) + ((this.field6952[702114061 * this.field6953 - 2] & 255) << 8);
      if (var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   void vmethod650() {
      if (null != this.field6952) {
         classUA.method11298(this.field6952, 2022759572);
      }

      this.field6952 = null;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("cz")
   public int method13104(byte var1) {
      try {
         this.field6953 += 2103253898;
         int var2 = (this.field6952[702114061 * this.field6953 - 1] & 255) + ((this.field6952[702114061 * this.field6953 - 2] & 255) << 8);
         if (var2 > 32767) {
            if (var1 != 16) {
               throw new IllegalStateException();
            }

            var2 -= 65536;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cz(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fw")
   public void method13052(boolean var1) {
      method13029(this, var1 ? 1 : 0, (byte)-81);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fo")
   public void method13319(int var1) {
      this.field6953 = var1 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IB)V")
   @ObfuscatedName("lg")
   public static void method13242(classXY var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method13073(var1, var2);
      }

      try {
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(128 + var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ev(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("iz")
   public void method13142(byte[] var1, int var2, int var3) {
      for (int var4 = var2; var4 < var2 + var3; var4++) {
         var1[var4] = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kd")
   public int method13226() {
      return 128 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mp")
   public int method13280() {
      this.field6953 += 1007397199;
      int var1 = (this.field6952[this.field6953 * 1049684668 - 1] & 255)
         + ((this.field6952[702114061 * this.field6953 - 2] & 2098791897) << 16)
         + ((this.field6952[-104142781 * this.field6953 - 3] & 1885754947) << 8);
      if (var1 > 8388607) {
         var1 -= -1831103933;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("im")
   public int method13149() {
      int var1 = this.field6952[this.field6953 * 702114061] & 255;
      return var1 < 128 ? this.method13095(-346779531) : this.method13099(362246589) - 32768;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("eh")
   public int method13250(int var1) {
      try {
         this.field6953 += 2103253898;
         return ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 2] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.eh(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ef")
   public void method13084(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == 1222866659) {
               return;
            }

            if (var1 < 128) {
               if (var2 == 1222866659) {
                  throw new IllegalStateException();
               }

               method13029(this, var1, (byte)-10);
               return;
            }
         }

         if (var1 >= 0) {
            if (var2 == 1222866659) {
               throw new IllegalStateException();
            }

            if (var1 < 32768) {
               if (var2 == 1222866659) {
                  return;
               }

               this.method13031(32768 + var1, -1542382992);
               return;
            }
         }

         throw new IllegalArgumentException();
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.co(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ls")
   public int method13267() {
      this.field6953 += 2103253898;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 8) + (this.field6952[this.field6953 * 702114061 - 2] - 128 & 0xFF);
      if (var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iy")
   public int method13154() {
      int var1 = this.field6952[this.field6953 * 702114061] & 255;
      return var1 < 128 ? this.method13095(-346779531) - 1 : this.method13099(-1167083753) - 32769;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ik")
   public int method13157() {
      short var1 = 0;

      int var2;
      for (var2 = this.method13150((short)12224); 32767 == var2; var2 = this.method13150((short)13883)) {
         var1 += 32767;
      }

      return var1 + var2;
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("dk")
   public int method13150(short var1) {
      try {
         int var2 = this.field6952[this.field6953 * 702114061] & 255;
         return var2 < 128 ? this.method13095(-346779531) : this.method13099(-1234107299) - 32768;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.di(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ii")
   public int method13159() {
      int var1 = this.method13095(-346779531);
      return var1 < 252 ? var1 : (var1 - -993418956 << 8) + this.method13095(-346779531);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("id")
   public int method13160() {
      int var1 = this.method13095(-346779531);
      return var1 < 252 ? var1 : (var1 - 252 << 8) + this.method13095(-346779531);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ju")
   public int method13162() {
      return this.field6952[203919647 * this.field6953] < 0 ? method13113(this, (byte)1) & 2147483647 : this.method13099(-1407068589);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbw;)Z")
   @ObfuscatedName("jx")
   public static boolean method13014(classBW var0) {
      return 215887637 * var0.field594 == 2;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jd")
   public int method13163() {
      return this.field6952[702114061 * this.field6953] < 0 ? method13113(this, (byte)1) & 2147483647 : this.method13099(1714810987);
   }

   public classXY(int var1) {
      byte[] var2 = classTO.method10987(var1, false, 1154989421);
      this.field6952 = var2;
      this.field6953 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lxf;)I")
   @ObfuscatedName("ji")
   public int method13174(classXF var1) {
      switch (var1.vmethod648((byte)32).field6928 * 539966297) {
         case 0:
            return this.method13097((byte)17);
         case 1:
            return this.method13095(-346779531);
         case 2:
            return this.method13104((byte)16);
         case 3:
            return this.method13099(-1118158241);
         case 4:
            return this.method13107(831951752);
         case 5:
            return this.method13105(-758448158);
         case 6:
            return method13113(this, (byte)1);
         default:
            throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jn")
   public int method13175() {
      int var2 = 0;
      byte var3 = 0;

      int var1;
      do {
         var1 = this.method13095(-346779531);
         var2 |= (var1 & 127) << var3;
         var3 += 7;
      } while (var1 > -146359649);

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)Ljava/lang/String;")
   @ObfuscatedName("yv")
   public static String method13137(classXY var0, int var1) {
      if (var0 == null) {
         return var0.method13139(var1);
      } else {
         try {
            byte var2 = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1];
            if (0 != var2) {
               if (var1 == -67173954) {
                  throw new IllegalStateException();
               } else {
                  throw new IllegalStateException("");
               }
            } else {
               int var3 = var0.method13172(-1673473703);
               if (702114061 * var0.field6953 + var3 > var0.field6952.length) {
                  if (var1 == -67173954) {
                     throw new IllegalStateException();
                  } else {
                     throw new IllegalStateException("");
                  }
               } else {
                  String var4 = classBF.method999(var0.field6952, 702114061 * var0.field6953, var3, (byte)-5);
                  var0.field6953 += var3 * -1095856699;
                  return var4;
               }
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "xy.dm(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cd")
   public int method13320() {
      return this.method13150((short)17353);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;)V")
   @ObfuscatedName("gc")
   public void method13063(CharSequence var1) {
      int var2 = classAC.method304(var1, (byte)84);
      this.field6952[(this.field6953 += -1095856699) * -580813439 - 1] = 0;
      this.method13087(var2, (byte)26);
      int var3 = this.field6953;
      byte[] var5 = this.field6952;
      int var6 = this.field6953 * -1107026129;
      int var7 = var1.length();
      int var8 = var6;

      for (int var9 = 0; var9 < var7; var9++) {
         char var10 = var1.charAt(var9);
         if (var10 <= 276343490) {
            var5[var8++] = (byte)var10;
         } else if (var10 <= 2047) {
            var5[var8++] = (byte)(-427086650 | var10 >> 6);
            var5[var8++] = (byte)(1838949323 | var10 & 406853741);
         } else {
            var5[var8++] = (byte)(224 | var10 >> '\f');
            var5[var8++] = (byte)(-1595117182 | var10 >> 6 & 63);
            var5[var8++] = (byte)(1584480419 | var10 & '?');
         }
      }

      int var4 = var8 - var6;
      this.field6953 = var3 + -937760916 * var4;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("kv")
   public void method13321(String var1) {
      this.method13054(var1, (short)8579);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("gc")
   public static void method13077(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13081(var1);
      } else if (var1 >= 0 && var1 <= 65535) {
         var0.field6952[-1557075232 * var0.field6953 - var1 - 2] = (byte)(var1 >> 8);
         var0.field6952[-322954203 * var0.field6953 - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Ljava/lang/CharSequence;I)V")
   @ObfuscatedName("ig")
   public static void method13064(classXY var0, CharSequence var1, int var2) {
      if (var0 == null) {
         var0.method13065(var1, var2);
      }

      try {
         int var3 = classAC.method304(var1, (byte)24);
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = 0;
         var0.method13087(var3, (byte)35);
         int var4 = var0.field6953;
         byte[] var6 = var0.field6952;
         int var7 = var0.field6953 * 702114061;
         int var8 = var1.length();
         int var9 = var7;

         for (int var10 = 0; var10 < var8; var10++) {
            if (var2 != -910745300) {
               throw new IllegalStateException();
            }

            char var11 = var1.charAt(var10);
            if (var11 <= 127) {
               if (var2 != -910745300) {
                  throw new IllegalStateException();
               }

               var6[var9++] = (byte)var11;
            } else if (var11 <= 2047) {
               if (var2 != -910745300) {
                  throw new IllegalStateException();
               }

               var6[var9++] = (byte)(192 | var11 >> 6);
               var6[var9++] = (byte)(128 | var11 & '?');
            } else {
               var6[var9++] = (byte)(224 | var11 >> '\f');
               var6[var9++] = (byte)(128 | var11 >> 6 & 63);
               var6[var9++] = (byte)(128 | var11 & '?');
            }
         }

         int var5 = var9 - var7;
         var0.field6953 = var4 + -1095856699 * var5;
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "xy.cq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jc")
   public int method13179() {
      int var1 = this.method13176(-1861532550);
      return var1 >>> 1 ^ -(var1 & 1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jo")
   public int method13180() {
      int var1 = this.method13176(-1164554069);
      return var1 >>> 1 ^ -(var1 & 1);
   }

   @ObfuscatedSignature(descriptor = "([I)V")
   @ObfuscatedName("jj")
   public void method13183(int[] var1) {
      int var2 = this.field6953 * 702114061 / 8;
      this.field6953 = 0;

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = method13113(this, (byte)1);
         int var5 = method13113(this, (byte)1);
         int var6 = 0;
         int var7 = -1640531527;
         int var8 = 32;

         while (var8-- > 0) {
            var4 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var6 & 3] + var6;
            var6 += var7;
            var5 += var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6;
         }

         this.field6953 -= -176919000;
         this.method13037(var4, -707461072);
         this.method13037(var5, 1626500652);
      }
   }

   @ObfuscatedSignature(descriptor = "([I)V")
   @ObfuscatedName("jt")
   public void method13184(int[] var1) {
      int var2 = this.field6953 * 1215515737 / 8;
      this.field6953 = 0;

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = method13113(this, (byte)1);
         int var5 = method13113(this, (byte)1);
         int var6 = 0;
         int var7 = -320058636;
         int var8 = 1124338910;

         while (var8-- > 0) {
            var4 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var6 & 3] + var6;
            var6 += var7;
            var5 += var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6;
         }

         this.field6953 -= -176919000;
         this.method13037(var4, 2052674087);
         this.method13037(var5, -334843501);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ig")
   public String method13133() {
      byte var1 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      if (0 != var1) {
         throw new IllegalStateException("");
      } else {
         int var2 = 1519465169 * this.field6953;

         while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
         }

         int var3 = 1740595012 * this.field6953 - var2 - 1;
         return 0 == var3 ? "" : classKQ.method6590(this.field6952, var2, var3, (byte)-52);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lv")
   public int method13259() {
      this.field6953 += 2103253898;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 8) + (this.field6952[702114061 * this.field6953 - 2] & 255);
      if (var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "([I)V")
   @ObfuscatedName("jm")
   public void method13188(int[] var1) {
      int var2 = 702114061 * this.field6953 / 8;
      this.field6953 = 0;

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = method13113(this, (byte)1);
         int var5 = method13113(this, (byte)1);
         int var6 = -957401312;
         int var7 = -1640531527;
         int var8 = 32;

         while (var8-- > 0) {
            var5 -= var4 + (var4 << 4 ^ var4 >>> 5) ^ var6 + var1[var6 >>> 11 & 3];
            var6 -= var7;
            var4 -= var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var6 & 3] + var6;
         }

         this.field6953 -= -176919000;
         this.method13037(var4, 737109350);
         this.method13037(var5, 1771558898);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ie")
   public String method13130() {
      int var1 = 702114061 * this.field6953;

      while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
      }

      int var2 = this.field6953 * 702114061 - var1 - 1;
      return 0 == var2 ? "" : classKQ.method6590(this.field6952, var1, var2, (byte)-91);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("jw")
   public void method13189(int[] var1, int var2, int var3) {
      int var4 = 1292325706 * this.field6953;
      this.field6953 = -1254940865 * var2;
      int var5 = (var3 - var2) / 8;

      for (int var6 = 0; var6 < var5; var6++) {
         int var7 = method13113(this, (byte)1);
         int var8 = method13113(this, (byte)1);
         int var9 = 0;
         int var10 = 2104204502;
         int var11 = 32;

         while (var11-- > 0) {
            var7 += var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3];
            var9 += var10;
            var8 += (var7 << 4 ^ var7 >>> 5) + var7 ^ var1[var9 >>> 11 & 3] + var9;
         }

         this.field6953 -= 853303095;
         this.method13037(var7, -524666268);
         this.method13037(var8, -92347503);
      }

      this.field6953 = var4 * -1783043332;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("md")
   public int method13304() {
      this.field6953 += -397441016;
      return (this.field6952[702114061 * this.field6953 - 2] & 0xFF)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 8)
         + ((this.field6952[702114061 * this.field6953 - 4] & 0xFF) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 3] & 1854004724) << 24);
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("jy")
   public void method13193(int[] var1, int var2, int var3) {
      int var4 = this.field6953 * 702114061;
      this.field6953 = var2 * -1095856699;
      int var5 = (var3 - var2) / 8;

      for (int var6 = 0; var6 < var5; var6++) {
         int var7 = method13113(this, (byte)1);
         int var8 = method13113(this, (byte)1);
         int var9 = -1355758882;
         int var10 = -1640531527;
         int var11 = 32;

         while (var11-- > 0) {
            var8 -= var7 + (var7 << 4 ^ var7 >>> 5) ^ var9 + var1[var9 >>> 11 & 3];
            var9 -= var10;
            var7 -= var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3];
         }

         this.field6953 -= -79212316;
         this.method13037(var7, -475285763);
         this.method13037(var8, -617899535);
      }

      this.field6953 = var4 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ms")
   public int method13301() {
      this.field6953 += -88459500;
      return ((this.field6952[this.field6953 * 702114061 - 2] & 0xFF) << 24)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 4] & 0xFF) << 8)
         + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/math/BigInteger;Ljava/math/BigInteger;)V")
   @ObfuscatedName("jk")
   public void method13196(BigInteger var1, BigInteger var2) {
      int var3 = this.field6953 * 1120276224;
      this.field6953 = 0;
      byte[] var4 = new byte[var3];
      this.method13140(var4, 0, var3, (byte)125);
      BigInteger var5 = new BigInteger(var4);
      BigInteger var6 = var5.modPow(var1, var2);
      byte[] var7 = var6.toByteArray();
      this.field6953 = 0;
      this.method13031(var7.length, -1542382992);
      this.method13067(var7, 0, var7.length, (byte)15);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("mp")
   public static int method13168(classXY var0) {
      if (var0.field6952[var0.field6953 * 702114061] < 0) {
         return method13113(var0, (byte)1) & -1283242690;
      } else {
         int var1 = var0.method13099(-11953539);
         return var1 == 32767 ? -1 : var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("mq")
   public static int method13252(classXY var0) {
      if (var0 == null) {
         return var0.method13257();
      } else {
         var0.field6953 += 2103253898;
         return ((var0.field6952[var0.field6953 * 702114061 - 2] & 0xFF) << 8) + (var0.field6952[var0.field6953 * 702114061 - 1] - 128 & 0xFF);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/math/BigInteger;Ljava/math/BigInteger;)V")
   @ObfuscatedName("je")
   public void method13197(BigInteger var1, BigInteger var2) {
      int var3 = this.field6953 * 702114061;
      this.field6953 = 0;
      byte[] var4 = new byte[var3];
      this.method13140(var4, 0, var3, (byte)6);
      BigInteger var5 = new BigInteger(var4);
      BigInteger var6 = var5.modPow(var1, var2);
      byte[] var7 = var6.toByteArray();
      this.field6953 = 0;
      this.method13031(var7.length, -1542382992);
      this.method13067(var7, 0, var7.length, (byte)58);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   public int method13322() {
      return this.method13095(-346779531);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("rn")
   public static int method13151(classXY var0) {
      int var1 = var0.field6952[var0.field6953 * 702114061] & 1843144058;
      return var1 < 399892850 ? var0.method13095(-346779531) : var0.method13099(1628482492) - -697614494;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/math/BigInteger;Ljava/math/BigInteger;)V")
   @ObfuscatedName("ke")
   public void method13198(BigInteger var1, BigInteger var2) {
      int var3 = this.field6953 * 702114061;
      this.field6953 = 0;
      byte[] var4 = new byte[var3];
      this.method13140(var4, 0, var3, (byte)106);
      BigInteger var5 = new BigInteger(var4);
      BigInteger var6 = var5.modPow(var1, var2);
      byte[] var7 = var6.toByteArray();
      this.field6953 = 0;
      this.method13031(var7.length, -1542382992);
      this.method13067(var7, 0, var7.length, (byte)56);
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("dd")
   public String method13134(int var1) {
      try {
         byte var2 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
         if (0 != var2) {
            throw new IllegalStateException("");
         } else {
            int var3 = 702114061 * this.field6953;

            while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
            }

            int var4 = 702114061 * this.field6953 - var3 - 1;
            if (0 == var4) {
               if (var1 == 1596161501) {
                  throw new IllegalStateException();
               } else {
                  return "";
               }
            } else {
               return classKQ.method6590(this.field6952, var3, var4, (byte)-2);
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xy.dd(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("jl")
   public void method13323(int var1) {
      this.method13037(var1, 1840758601);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("kx")
   public int method13200(int var1) {
      int var2 = classPY.method9423(this.field6952, var1, 702114061 * this.field6953, -1872029579);
      this.method13037(var2, 1855530005);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("kp")
   public int method13201(int var1) {
      int var2 = classPY.method9423(this.field6952, var1, 702114061 * this.field6953, -1829759419);
      this.method13037(var2, -148783494);
      return var2;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kg")
   public boolean method13204() {
      this.field6953 -= -88459500;
      int var1 = classPY.method9423(this.field6952, 0, this.field6953 * 702114061, -1397183401);
      int var2 = method13113(this, (byte)1);
      return var2 == var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ks")
   public boolean method13205() {
      this.field6953 -= -88459500;
      int var1 = classPY.method9423(this.field6952, 0, this.field6953 * 3251480, -1702057589);
      int var2 = method13113(this, (byte)1);
      return var2 == var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("cu")
   public static int method13171(classXY var0) {
      byte var1 = var0.field6952[(var0.field6953 += -607032926) * 702114061 - 1];

      int var2;
      for (var2 = 0; var1 < 0; var1 = var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1]) {
         var2 = (var2 | var1 & -92018440) << 7;
      }

      return var2 | var1;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ca")
   void method13090(int var1, byte var2) {
      try {
         while (true) {
            if (var1 >= 0) {
               if (var2 == 1) {
                  throw new IllegalStateException();
               }

               if (var1 <= 127) {
                  method13029(this, var1, (byte)-15);
                  return;
               }

               if (var2 == 1) {
                  return;
               }
            }

            method13029(this, 128 | var1 & 127, (byte)-6);
            var1 >>>= 7;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ca(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("jp")
   public void method13194(int[] var1, int var2, int var3) {
      int var4 = this.field6953 * 702114061;
      this.field6953 = var2 * -1095856699;
      int var5 = (var3 - var2) / 8;

      for (int var6 = 0; var6 < var5; var6++) {
         int var7 = method13113(this, (byte)1);
         int var8 = method13113(this, (byte)1);
         int var9 = -957401312;
         int var10 = -1640531527;
         int var11 = 32;

         while (var11-- > 0) {
            var8 -= var7 + (var7 << 4 ^ var7 >>> 5) ^ var9 + var1[var9 >>> 11 & 3];
            var9 -= var10;
            var7 -= var8 + (var8 << 4 ^ var8 >>> 5) ^ var9 + var1[var9 & 3];
         }

         this.field6953 -= -176919000;
         this.method13037(var7, 2109995366);
         this.method13037(var8, 1438356079);
      }

      this.field6953 = var4 * -1095856699;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)I")
   @ObfuscatedName("se")
   public static int method13202(classXY var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method13203(var1, var1);
      } else {
         try {
            int var3 = classPY.method9423(var0.field6952, var1, 702114061 * var0.field6953, -1480136460);
            var0.method13037(var3, 1549079448);
            return var3;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "xy.dq(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("kq")
   public void method13212(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(0 - var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ka")
   public void method13213(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(0 - var1);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;S)I")
   @ObfuscatedName("jy")
   public static int method13164(classXY var0, short var1) {
      if (var0 == null) {
         return var0.method13165(var1);
      } else {
         try {
            if (var0.field6952[702114061 * var0.field6953] < 0) {
               if (var1 == 256) {
                  throw new IllegalStateException();
               } else {
                  return method13113(var0, (byte)1) & 2147483647;
               }
            } else {
               return var0.method13099(1004254197);
            }
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "xy.dk(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ld")
   public int method13270() {
      this.field6953 += 1007397199;
      return ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 8)
         + ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 16)
         + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("kv")
   public void method13215(int var1) {
      this.field6952[(this.field6953 += 354887611) * 702114061 - 1] = (byte)(1140765656 - var1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ip")
   public int method13161() {
      int var1 = this.method13095(-346779531);
      return var1 < 252 ? var1 : (var1 - 252 << 8) + this.method13095(-346779531);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kt")
   public int method13220() {
      return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128 & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "([BIIB)V")
   @ObfuscatedName("cl")
   public void method13067(byte[] var1, int var2, int var3, byte var4) {
      try {
         for (int var5 = var2; var5 < var2 + var3; var5++) {
            if (var4 <= 4) {
               throw new IllegalStateException();
            }

            this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = var1[var5];
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xy.cl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ic")
   public int method13155() {
      int var1 = this.field6952[this.field6953 * 702114061] & 255;
      return var1 < 128 ? this.method13095(-346779531) - 1 : this.method13099(-1562734945) - 32769;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kn")
   public int method13223() {
      return 0 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ez")
   public void method13287(int var1, int var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.em(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hd")
   public void method13088(int var1) {
      if ((var1 & -128) != 0) {
         if ((var1 & -16384) != 0) {
            if ((var1 & -2097152) != 0) {
               if ((var1 & -268435456) != 0) {
                  method13029(this, var1 >>> 28 | 128, (byte)-18);
               }

               method13029(this, var1 >>> 21 | 128, (byte)-15);
            }

            method13029(this, var1 >>> 14 | 128, (byte)-102);
         }

         method13029(this, var1 >>> 7 | 128, (byte)-76);
      }

      method13029(this, var1 & 127, (byte)-94);
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("kc")
   public byte method13228() {
      return (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128);
   }

   @ObfuscatedSignature(descriptor = "(B)B")
   @ObfuscatedName("ek")
   public byte method13229(byte var1) {
      try {
         return (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ek(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("my")
   public int method13324() {
      return this.method13156((byte)76);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ms")
   public static void method13210(classXY var0, int var1) {
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + -2060422152);
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("kz")
   public byte method13230() {
      return (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128);
   }

   @ObfuscatedSignature(descriptor = "([I)V")
   @ObfuscatedName("jv")
   public void method13185(int[] var1) {
      int var2 = this.field6953 * 702114061 / 8;
      this.field6953 = 0;

      for (int var3 = 0; var3 < var2; var3++) {
         int var4 = method13113(this, (byte)1);
         int var5 = method13113(this, (byte)1);
         int var6 = 0;
         int var7 = -1640531527;
         int var8 = 32;

         while (var8-- > 0) {
            var4 += var5 + (var5 << 4 ^ var5 >>> 5) ^ var1[var6 & 3] + var6;
            var6 += var7;
            var5 += var4 + (var4 << 4 ^ var4 >>> 5) ^ var1[var6 >>> 11 & 3] + var6;
         }

         this.field6953 -= -176919000;
         this.method13037(var4, -195451918);
         this.method13037(var5, -602136646);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lj")
   public void method13236(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lm")
   public void method13237(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lp")
   public void method13238(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([BI)I")
   @ObfuscatedName("fb")
   public static int method13023(byte[] var0, int var1) {
      return classPY.method9423(var0, 0, var1, -1868039813);
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("km")
   public byte method13234() {
      return (byte)(-21296263 - this.field6952[(this.field6953 += 1983978372) * 702114061 - 1]);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ll")
   public int method13325() {
      return method13113(this, (byte)1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Z")
   @ObfuscatedName("ik")
   public static boolean method13206(classXY var0) {
      if (var0 == null) {
         var0.method13226();
      }

      var0.field6953 -= -88459500;
      int var1 = classPY.method9423(var0.field6952, 0, var0.field6953 * 702114061, -1848030444);
      int var2 = method13113(var0, (byte)1);
      return var2 == var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lh")
   public void method13246(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 1723032646 - 1] = (byte)(var1 + 128);
      this.field6952[(this.field6953 += -1095856699) * -615007973 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ix")
   public String method13131() {
      int var1 = 702114061 * this.field6953;

      while (this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] != 0) {
      }

      int var2 = this.field6953 * 702114061 - var1 - 1;
      return 0 == var2 ? "" : classKQ.method6590(this.field6952, var1, var2, (byte)-46);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/math/BigInteger;Ljava/math/BigInteger;)V")
   @ObfuscatedName("kf")
   public void method13199(BigInteger var1, BigInteger var2) {
      int var3 = this.field6953 * 1250165784;
      this.field6953 = 0;
      byte[] var4 = new byte[var3];
      this.method13140(var4, 0, var3, (byte)53);
      BigInteger var5 = new BigInteger(var4);
      BigInteger var6 = var5.modPow(var1, var2);
      byte[] var7 = var6.toByteArray();
      this.field6953 = 0;
      this.method13031(var7.length, -1542382992);
      this.method13067(var7, 0, var7.length, (byte)16);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)I")
   @ObfuscatedName("gi")
   public static int method13169(classXY var0, int var1) {
      if (var0 == null) {
         return var0.method13101(var1);
      } else {
         try {
            if (var0.field6952[var0.field6953 * 702114061] < 0) {
               return method13113(var0, (byte)1) & 2147483647;
            } else {
               int var2 = var0.method13099(-174107367);
               return var2 == 32767 ? -1 : var2;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "xy.db(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("de")
   public int method13176(int var1) {
      try {
         int var3 = 0;
         byte var4 = 0;

         int var2;
         do {
            var2 = this.method13095(-346779531);
            var3 |= (var2 & 127) << var4;
            var4 += 7;
         } while (var2 > 127);

         if (var1 >= 465362749) {
            throw new IllegalStateException();
         } else {
            return var3;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xy.de(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lx")
   public int method13256() {
      this.field6953 += 2103253898;
      return ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 2] - 128 & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ds")
   public void method13080(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= -1365423377) {
               return;
            }

            if (var1 <= 255) {
               this.field6952[this.field6953 * 702114061 - var1 - 1] = (byte)var1;
               return;
            }

            if (var2 <= -1365423377) {
               throw new IllegalStateException();
            }
         }

         throw new IllegalArgumentException();
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cy(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("in")
   public String method13138() {
      byte var1 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      if (0 != var1) {
         throw new IllegalStateException("");
      } else {
         int var2 = this.method13172(-2142495228);
         if (702114061 * this.field6953 + var2 > this.field6952.length) {
            throw new IllegalStateException("");
         } else {
            String var3 = classBF.method999(this.field6952, 702114061 * this.field6953, var2, (byte)-53);
            this.field6953 += var2 * -1095856699;
            return var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   void vmethod651() {
      if (null != this.field6952) {
         classUA.method11298(this.field6952, 2022759572);
      }

      this.field6952 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gy")
   public void method13072(int var1) {
      if (var1 < 0) {
         throw new IllegalArgumentException();
      } else {
         this.field6952[158248154 * this.field6953 - var1 - 4] = (byte)(var1 >> 24);
         this.field6952[702114061 * this.field6953 - var1 - 3] = (byte)(var1 >> 16);
         this.field6952[-286122995 * this.field6953 - var1 - 2] = (byte)(var1 >> 8);
         this.field6952[this.field6953 * 702114061 - var1 - 1] = (byte)var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ir")
   public boolean method13124() {
      return (this.method13095(-346779531) & 1) == 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)I")
   @ObfuscatedName("pi")
   public static int method13271(classXY var0) {
      if (var0 == null) {
         return var0.method13148();
      } else {
         var0.field6953 += 1007397199;
         return ((var0.field6952[702114061 * var0.field6953 - 2] & 0xFF) << 8)
            + ((var0.field6952[702114061 * var0.field6953 - 1] & 0xFF) << 16)
            + (var0.field6952[702114061 * var0.field6953 - 3] & 0xFF);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IB)V")
   @ObfuscatedName("zu")
   public static void method13029(classXY var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method13030(var1, var2);
      } else {
         try {
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "xy.bl(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)B")
   @ObfuscatedName("ej")
   public byte method13235(byte var1) {
      try {
         return (byte)(128 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1]);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ej(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lo")
   public int method13264() {
      this.field6953 += -26810507;
      int var1 = ((this.field6952[702114061 * this.field6953 - 2] & -246843815) << 8) + (this.field6952[this.field6953 * 702114061 - 1] - 128 & 0xFF);
      if (var1 > -783748228) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cf")
   public int method13095(int var1) {
      try {
         return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cf(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lw")
   public int method13260() {
      this.field6953 += 2103253898;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 2143870035) << 8) + (this.field6952[702114061 * this.field6953 - 2] & -878567563);
      if (var1 > -1832339327) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("le")
   public int method13272() {
      this.field6953 += 1007397199;
      return ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 8)
         + ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 16)
         + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("lb")
   public void method13247(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 + 128);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lq")
   public int method13276() {
      this.field6953 += 1007397199;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 2] & 255) << 8)
         + (this.field6952[this.field6953 * 702114061 - 3] & 255);
      if (var1 > 8388607) {
         var1 -= 16777216;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("fz")
   public static void method13036(classXY var0, int var1) {
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      var0.field6952[(var0.field6953 += 1869839175) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mb")
   public int method13281() {
      this.field6953 += 1007397199;
      int var1 = (this.field6952[this.field6953 * 702114061 - 1] & 255)
         + ((this.field6952[702114061 * this.field6953 - 2] & 255) << 16)
         + ((this.field6952[702114061 * this.field6953 - 3] & 255) << 8);
      if (var1 > 8388607) {
         var1 -= 16777216;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;J)V")
   @ObfuscatedName("fl")
   public static void method13044(classXY var0, long var1) {
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 40);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ob")
   public static void method13038(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13081(var1);
      } else {
         var0.field6952[(var0.field6953 += -1095856699) * -544099772 - 1] = (byte)(var1 >> 24);
         var0.field6952[(var0.field6953 += -2076956018) * 702114061 - 1] = (byte)(var1 >> 16);
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         var0.field6952[(var0.field6953 += -1095856699) * -672089042 - 1] = (byte)var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mf")
   public int method13282() {
      this.field6953 += 1007397199;
      int var1 = (this.field6952[this.field6953 * 702114061 - 1] & 255)
         + ((this.field6952[702114061 * this.field6953 - 2] & -1024288003) << 16)
         + ((this.field6952[702114061 * this.field6953 - 3] & -1857014102) << 8);
      if (var1 > 8388607) {
         var1 -= 16777216;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("dw")
   public int method13172(int var1) {
      try {
         byte var2 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];

         int var3;
         for (var3 = 0; var2 < 0; var2 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1]) {
            if (var1 >= -488065562) {
               throw new IllegalStateException();
            }

            var3 = (var3 | var2 & 127) << 7;
         }

         return var3 | var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.dw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("mj")
   public void method13285(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -523510191) * -1602272621 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 108914099 - 1] = (byte)(var1 >> 24);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ma")
   public void method13288(int var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 587433947 - 1] = (byte)var1;
      this.field6952[(this.field6953 += -1095856699) * 1727043761 - 1] = (byte)(var1 >> 24);
      this.field6952[(this.field6953 += -1095856699) * -275353355 - 1] = (byte)(var1 >> 16);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mt")
   public int method13296() {
      this.field6953 += -88459500;
      return ((this.field6952[702114061 * this.field6953 - 3] & 0xFF) << 8)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 24)
         + ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 16)
         + (this.field6952[this.field6953 * 702114061 - 4] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lc")
   public int method13257() {
      this.field6953 += 2103253898;
      return ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 2] - 128 & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ey")
   public int method13224(int var1) {
      try {
         return 0 - this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ey(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mi")
   public int method13302() {
      this.field6953 += -88459500;
      return ((this.field6952[this.field6953 * 702114061 - 2] & 0xFF) << 24)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 4] & 0xFF) << 8)
         + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lk")
   public int method13268() {
      this.field6953 += 2103253898;
      int var1 = ((this.field6952[702114061 * this.field6953 - 1] & 659833714) << 8) + (this.field6952[this.field6953 * 418609119 - 2] - 1546368771 & 0xFF);
      if (var1 > 32767) {
         var1 -= 65536;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mh")
   public int method13305() {
      this.field6953 += -88459500;
      return (this.field6952[702114061 * this.field6953 - 2] & 373161518)
         + ((this.field6952[this.field6953 * 1270135386 - 1] & 591869391) << 8)
         + ((this.field6952[-989019978 * this.field6953 - 4] & 0xFF) << 16)
         + ((this.field6952[this.field6953 * 702114061 - 3] & 0xFF) << 24);
   }

   @ObfuscatedSignature(descriptor = "([IIII)V")
   @ObfuscatedName("do")
   public void method13190(int[] var1, int var2, int var3, int var4) {
      try {
         int var5 = 702114061 * this.field6953;
         this.field6953 = -1095856699 * var2;
         int var6 = (var3 - var2) / 8;

         for (int var7 = 0; var7 < var6; var7++) {
            if (var4 <= 1347323342) {
               throw new IllegalStateException();
            }

            int var8 = method13113(this, (byte)1);
            int var9 = method13113(this, (byte)1);
            int var10 = 0;
            int var11 = -1640531527;
            int var12 = 32;

            while (var12-- > 0) {
               if (var4 <= 1347323342) {
                  throw new IllegalStateException();
               }

               var8 += var9 + (var9 << 4 ^ var9 >>> 5) ^ var10 + var1[var10 & 3];
               var10 += var11;
               var9 += (var8 << 4 ^ var8 >>> 5) + var8 ^ var1[var10 >>> 11 & 3] + var10;
            }

            this.field6953 -= -176919000;
            this.method13037(var8, -650528597);
            this.method13037(var9, 1975893354);
         }

         this.field6953 = var5 * -1095856699;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "xy.do(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)I")
   @ObfuscatedName("xs")
   public static int method13283(classXY var0, int var1) {
      if (var0 == null) {
         var0.method13277(var1);
      }

      try {
         var0.field6953 += 1007397199;
         int var2 = (var0.field6952[var0.field6953 * 702114061 - 1] & 255)
            + ((var0.field6952[702114061 * var0.field6953 - 2] & 255) << 16)
            + ((var0.field6952[702114061 * var0.field6953 - 3] & 255) << 8);
         if (var2 > 8388607) {
            if (var1 != 1575549434) {
               throw new IllegalStateException();
            }

            var2 -= 16777216;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.eu(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("my")
   public void method13311(byte[] var1, int var2, int var3) {
      for (int var4 = var3 + var2 - 1; var4 >= var2; var4--) {
         var1[var4] = (byte)(this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] - 128);
      }
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("mo")
   public float method13326() {
      return this.method13119(1386282862);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IB)V")
   @ObfuscatedName("wk")
   public static void method13239(classXY var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method13240(var1, var2);
      } else {
         try {
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
            var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "xy.ed(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("cu")
   public void method13045(long var1) {
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 40);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 32);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("cb")
   public void method13053(boolean var1, int var2) {
      try {
         byte var10001;
         if (var1) {
            if (var2 != -1737488759) {
               return;
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         method13029(this, var10001, (byte)-108);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;I)V")
   @ObfuscatedName("cq")
   public void method13065(CharSequence var1, int var2) {
      try {
         int var3 = classAC.method304(var1, (byte)24);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = 0;
         this.method13087(var3, (byte)35);
         int var4 = this.field6953;
         byte[] var6 = this.field6952;
         int var7 = this.field6953 * 702114061;
         int var8 = var1.length();
         int var9 = var7;

         for (int var10 = 0; var10 < var8; var10++) {
            if (var2 != -910745300) {
               throw new IllegalStateException();
            }

            char var11 = var1.charAt(var10);
            if (var11 <= 127) {
               if (var2 != -910745300) {
                  throw new IllegalStateException();
               }

               var6[var9++] = (byte)var11;
            } else if (var11 <= 2047) {
               if (var2 != -910745300) {
                  throw new IllegalStateException();
               }

               var6[var9++] = (byte)(192 | var11 >> 6);
               var6[var9++] = (byte)(128 | var11 & '?');
            } else {
               var6[var9++] = (byte)(224 | var11 >> '\f');
               var6[var9++] = (byte)(128 | var11 >> 6 & 63);
               var6[var9++] = (byte)(128 | var11 & '?');
            }
         }

         int var5 = var9 - var7;
         this.field6953 = var4 + -1095856699 * var5;
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "xy.cq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fx")
   public void method13081(int var1) {
      if (var1 >= 0 && var1 <= 255) {
         this.field6952[this.field6953 * 702114061 - var1 - 1] = (byte)var1;
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("cj")
   public int method13112(byte var1) {
      try {
         this.field6953 += -88459500;
         return (this.field6952[702114061 * this.field6953 - 1] & 0xFF)
            + ((this.field6952[this.field6953 * 702114061 - 2] & 0xFF) << 8)
            + ((this.field6952[this.field6953 * 702114061 - 3] & 0xFF) << 16)
            + ((this.field6952[this.field6953 * 702114061 - 4] & 0xFF) << 24);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("ci")
   public long method13117(int var1) {
      try {
         long var2 = method13113(this, (byte)1) & 4294967295L;
         long var4 = method13113(this, (byte)1) & 4294967295L;
         return var4 + (var2 << 32);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xy.ci(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("di")
   public int method13165(short var1) {
      try {
         if (this.field6952[702114061 * this.field6953] < 0) {
            if (var1 == 256) {
               throw new IllegalStateException();
            } else {
               return method13113(this, (byte)1) & 2147483647;
            }
         } else {
            return this.method13146(1004254197);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.dk(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("iu")
   public static void method13290(classXY var0, int var1, int var2) {
      if (var0 == null) {
         var0.method13291(var1, var1);
      }

      try {
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ez(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("dm")
   public String method13139(int var1) {
      try {
         byte var2 = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
         if (0 != var2) {
            if (var1 == -67173954) {
               throw new IllegalStateException();
            } else {
               throw new IllegalStateException("");
            }
         } else {
            int var3 = this.method13200(-1673473703);
            if (702114061 * this.field6953 + var3 > this.field6952.length) {
               if (var1 == -67173954) {
                  throw new IllegalStateException();
               } else {
                  throw new IllegalStateException("");
               }
            } else {
               String var4 = classBF.method999(this.field6952, 702114061 * this.field6953, var3, (byte)-5);
               this.field6953 += var3 * -1095856699;
               return var4;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xy.dm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("db")
   public int method13101(int var1) {
      try {
         this.field6953 += 2103253898;
         return ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 1] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.ch(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("dq")
   public int method13203(int var1, int var2) {
      try {
         int var3 = classPY.method9423(this.field6952, var1, 702114061 * this.field6953, -1480136460);
         this.method13084(var3, 1549079448);
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.dq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("eo")
   public int method13273(int var1) {
      try {
         this.field6953 += 1007397199;
         return ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 8)
            + ((this.field6952[702114061 * this.field6953 - 1] & 0xFF) << 16)
            + (this.field6952[702114061 * this.field6953 - 3] & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.eo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("eg")
   public int method13253(int var1) {
      try {
         this.field6953 += 2103253898;
         return ((this.field6952[this.field6953 * 702114061 - 2] & 0xFF) << 8) + (this.field6952[this.field6953 * 702114061 - 1] - 128 & 0xFF);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.eg(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ed")
   public void method13240(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ed(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("eq")
   public int method13265(byte var1) {
      try {
         this.field6953 += 2103253898;
         int var2 = ((this.field6952[702114061 * this.field6953 - 2] & 255) << 8) + (this.field6952[this.field6953 * 702114061 - 1] - 128 & 0xFF);
         if (var2 > 32767) {
            var2 -= 65536;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.eq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("eu")
   public int method13277(int var1) {
      try {
         this.field6953 += 1007397199;
         int var2 = ((this.field6952[702114061 * this.field6953 - 1] & 255) << 16)
            + ((this.field6952[this.field6953 * 702114061 - 2] & 255) << 8)
            + (this.field6952[this.field6953 * 702114061 - 3] & 255);
         if (var2 > 8388607) {
            if (var1 >= 643717713) {
               throw new IllegalStateException();
            }

            var2 -= 16777216;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.et(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("kt")
   public static void method13216(classXY var0, int var1) {
      var0.field6952[(var0.field6953 += -1095856699) * -764142065 - 1] = (byte)(128 - var1);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cs")
   public void method13291(int var1, int var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 16);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 24);
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(var1 >> 8);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.ez(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BIII)V")
   @ObfuscatedName("ep")
   public void method13308(byte[] var1, int var2, int var3, int var4) {
      try {
         for (int var5 = var2 + var3 - 1; var5 >= var2; var5--) {
            var1[var5] = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "xy.ep(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("eh")
   public int method13327() {
      return this.method13104((byte)16);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("df")
   public boolean method13207(int var1) {
      try {
         this.field6953 -= -88459500;
         int var2 = classPY.method9423(this.field6952, 0, this.field6953 * 702114061, -1961045550);
         int var3 = method13113(this, (byte)1);
         if (var3 == var2) {
            if (var1 >= -511275367) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "xy.df(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hf")
   public int method13096() {
      return this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ev")
   public void method13073(int var1, byte var2) {
      try {
         if (var1 < 0) {
            if (var2 == 104) {
               throw new IllegalStateException();
            } else {
               throw new IllegalArgumentException();
            }
         } else {
            this.field6952[702114061 * this.field6953 - var1 - 4] = (byte)(var1 >> 24);
            this.field6952[702114061 * this.field6953 - var1 - 3] = (byte)(var1 >> 16);
            this.field6952[702114061 * this.field6953 - var1 - 2] = (byte)(var1 >> 8);
            this.field6952[this.field6953 * 702114061 - var1 - 1] = (byte)var1;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.cv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lr")
   public int method13297() {
      this.field6953 += -88459500;
      return ((this.field6952[702114061 * this.field6953 - 3] & 0xFF) << 8)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 24)
         + ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 16)
         + (this.field6952[this.field6953 * 702114061 - 4] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)Z")
   @ObfuscatedName("yv")
   public static boolean method13208(classXY var0, int var1) {
      if (var0 == null) {
         return var0.method13207(var1);
      } else {
         try {
            var0.field6953 -= -88459500;
            int var2 = classPY.method9423(var0.field6952, 0, var0.field6953 * 702114061, -1961045550);
            int var3 = method13113(var0, (byte)1);
            if (var3 == var2) {
               if (var1 >= -511275367) {
                  throw new IllegalStateException();
               } else {
                  return true;
               }
            } else {
               return false;
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "xy.df(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("cv")
   public void method13217(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)(128 - var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.da(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mn")
   public int method13298() {
      this.field6953 += -88459500;
      return ((this.field6952[702114061 * this.field6953 - 3] & 0xFF) << 8)
         + ((this.field6952[this.field6953 * 702114061 - 1] & 0xFF) << 24)
         + ((this.field6952[702114061 * this.field6953 - 2] & 0xFF) << 16)
         + (this.field6952[this.field6953 * 702114061 - 4] & 0xFF);
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bl")
   public void method13030(int var1, byte var2) {
      try {
         this.field6952[(this.field6953 += -1095856699) * 702114061 - 1] = (byte)var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xy.bl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("hv")
   public long method13118() {
      long var1 = method13113(this, (byte)1) & 4294967295L;
      long var3 = method13113(this, (byte)1) & 4294967295L;
      return var3 + (var1 << -1317137739);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)I")
   @ObfuscatedName("zh")
   public static int method13113(classXY var0, byte var1) {
      if (var0 == null) {
         var0.method13112(var1);
      }

      try {
         var0.field6953 += -88459500;
         return (var0.field6952[702114061 * var0.field6953 - 1] & 0xFF)
            + ((var0.field6952[var0.field6953 * 702114061 - 2] & 0xFF) << 8)
            + ((var0.field6952[var0.field6953 * 702114061 - 3] & 0xFF) << 16)
            + ((var0.field6952[var0.field6953 * 702114061 - 4] & 0xFF) << 24);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xy.cj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("mm")
   public void method13143(byte[] var1, int var2, int var3) {
      for (int var4 = var2; var4 < var2 + var3; var4++) {
         var1[var4] = this.field6952[(this.field6953 += -1095856699) * 702114061 - 1];
      }
   }
}
