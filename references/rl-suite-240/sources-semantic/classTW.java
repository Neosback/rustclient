import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tw")
public class classTW implements classXZ {
   @ObfuscatedName("ao")
   final int field6243;
   @ObfuscatedName("as")
   final int field6242;
   @ObfuscatedSignature(descriptor = "Ltw;")
   @ObfuscatedName("ae")
   static final classTW field6239 = new classTW(2, 2, 338, 338, 338, 507);
   @ObfuscatedSignature(descriptor = "Ltw;")
   @ObfuscatedName("ab")
   static final classTW field6240 = new classTW(3, 3, 499, 499, 499, 507);
   @ObfuscatedName("ag")
   final int field6244;
   @ObfuscatedName("aa")
   final int field6247;
   @ObfuscatedName("ax")
   final int field6246;
   @ObfuscatedSignature(descriptor = "Ltw;")
   @ObfuscatedName("af")
   public static final classTW field6241 = new classTW(1, 1, 507, 507, 507, 507);
   @ObfuscatedName("ac")
   final int field6245;
   @ObfuscatedSignature(descriptor = "Ltw;")
   @ObfuscatedName("az")
   static final classTW field6238 = new classTW(0, 0, 0, 0, 0, 0);
   @ObfuscatedSignature(descriptor = "Lmh;")
   @ObfuscatedName("rz")
   static classMH field6248;

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bj")
   public boolean method11250(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6247 * -1476915659, var1, var2, var3, (byte)-62);
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return -953101483 * this.field6243;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tw.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltw;ZZZ)Z")
   @ObfuscatedName("wz")
   public static boolean method11262(classTW var0, boolean var1, boolean var2, boolean var3) {
      if (var0 == null) {
         var0.method11265(var1, var1, var1);
      }

      return var0.method11236(var0.field6245 * 2113017971, var1, var2, var3, (byte)22);
   }

   @ObfuscatedSignature(descriptor = "(IZZZB)Z")
   @ObfuscatedName("as")
   boolean method11231(int var1, boolean var2, boolean var3, boolean var4, byte var5) {
      try {
         int var6 = 0;
         byte var10001;
         if (var3) {
            if (var5 >= 1) {
               throw new IllegalStateException();
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         var6 |= var10001;
         if (var2) {
            if (var5 >= 1) {
               throw new IllegalStateException();
            }

            var10001 = (byte)8;
         } else {
            var10001 = (byte)32;
         }

         var6 |= var10001;
         if (var4) {
            if (var5 >= 1) {
               throw new IllegalStateException();
            }

            var10001 = 128;
         } else {
            var10001 = 0;
         }

         var6 |= var10001;
         return (var1 & var6) != 0;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "tw.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IZZZB)Z")
   @ObfuscatedName("ax")
   boolean method11236(int var1, boolean var2, boolean var3, boolean var4, byte var5) {
      try {
         int var6 = 0;
         byte var10001;
         if (var3) {
            if (var5 >= 31) {
               throw new IllegalStateException();
            }

            var10001 = 2;
         } else {
            var10001 = 0;
         }

         var6 |= var10001;
         if (var2) {
            if (var5 >= 31) {
               throw new IllegalStateException();
            }

            var10001 = (byte)16;
         } else {
            var10001 = (byte)64;
         }

         var6 |= var10001;
         if (var4) {
            if (var5 >= 31) {
               throw new IllegalStateException();
            }

            var10001 = 256;
         } else {
            var10001 = 0;
         }

         var6 |= var10001;
         boolean var10000;
         if (0 != (var1 & var6)) {
            if (var5 >= 31) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "tw.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ltw;")
   @ObfuscatedName("ad")
   public static classTW[] method11227() {
      return new classTW[]{field6238, field6241, field6239, field6240};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltw;IZZZ)Z")
   @ObfuscatedName("ji")
   public static boolean method11232(classTW var0, int var1, boolean var2, boolean var3, boolean var4) {
      if (var0 == null) {
         return var0.method11240(var2, var2, var2);
      } else {
         int var5 = 0;
         var5 |= var3 ? 1 : 0;
         var5 |= var2 ? 8 : 32;
         var5 |= var4 ? 128 : 0;
         return (var1 & var5) != 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("ah")
   public boolean method11238(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6242 * -1207890233, var1, var2, var3, (byte)-53);
   }

   @ObfuscatedSignature(descriptor = "(ZZZB)Z")
   @ObfuscatedName("ac")
   public boolean method11239(boolean var1, boolean var2, boolean var3, byte var4) {
      try {
         return this.method11231(this.field6242 * -1207890233, var1, var2, var3, (byte)-95);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IZZZ)Z")
   @ObfuscatedName("av")
   boolean method11237(int var1, boolean var2, boolean var3, boolean var4) {
      int var5 = 0;
      var5 |= var3 ? 2 : 0;
      var5 |= var2 ? 16 : 64;
      var5 |= var4 ? 148939176 : 0;
      return 0 != (var1 & var5);
   }

   @ObfuscatedSignature(descriptor = "(JIIB)J")
   @ObfuscatedName("ac")
   public static long method11266(long var0, int var2, int var3, byte var4) {
      try {
         long var5 = classOW.method8842(var3 - var2 + 1, 1890737205);
         var5 <<= var2;
         return var0 | var5;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "tw.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZI)Z")
   @ObfuscatedName("ay")
   public boolean method11258(boolean var1, boolean var2, boolean var3, int var4) {
      try {
         return this.method11236(1369847981 * this.field6246, var1, var2, var3, (byte)-34);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZB)Z")
   @ObfuscatedName("aq")
   public boolean method11263(boolean var1, boolean var2, boolean var3, byte var4) {
      try {
         return this.method11236(this.field6245 * 2113017971, var1, var2, var3, (byte)-61);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.aq(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltw;ZZZ)Z")
   @ObfuscatedName("wr")
   public static boolean method11264(classTW var0, boolean var1, boolean var2, boolean var3) {
      return var0.method11236(var0.field6245 * 2113017971, var1, var2, var3, (byte)-70);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyi;J)Lvq;")
   @ObfuscatedName("xn")
   public static Node method11230(HashTable var0, long var1) {
      Node var3 = var0.field6984[(int)(var1 & var0.field6985 - 1)];

      for (var0.field6988 = var3.field6552; var0.field6988 != var3; var0.field6988 = var0.field6988.field6552) {
         if (var0.field6988.field6550 == var1) {
            Node var4 = var0.field6988;
            var0.field6988 = var0.field6988.field6552;
            return var4;
         }
      }

      var0.field6988 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return -953101483 * this.field6243;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return -953101483 * this.field6243;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -953101483 * this.field6243;
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bm")
   public boolean method11246(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6245 * 568016088, var1, var2, var3, (byte)-94);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ltw;")
   @ObfuscatedName("ap")
   public static classTW[] method11228() {
      return new classTW[]{field6238, field6241, field6239, field6240};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Ltw;")
   @ObfuscatedName("au")
   public static classTW[] method11229() {
      return new classTW[]{field6238, field6241, field6239, field6240};
   }

   @ObfuscatedSignature(descriptor = "(IZZZ)Z")
   @ObfuscatedName("ai")
   boolean method11233(int var1, boolean var2, boolean var3, boolean var4) {
      int var5 = 0;
      var5 |= var3 ? 1 : 0;
      var5 |= var2 ? 8 : 32;
      var5 |= var4 ? 456248824 : 0;
      return (var1 & var5) != 0;
   }

   @ObfuscatedSignature(descriptor = "(IZZZ)Z")
   @ObfuscatedName("ar")
   boolean method11234(int var1, boolean var2, boolean var3, boolean var4) {
      int var5 = 0;
      var5 |= var3 ? 1 : 0;
      var5 |= var2 ? 8 : 32;
      var5 |= var4 ? 128 : 0;
      return (var1 & var5) != 0;
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bk")
   public boolean method11251(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6247 * -1476915659, var1, var2, var3, (byte)-78);
   }

   @ObfuscatedSignature(descriptor = "(IZZZ)Z")
   @ObfuscatedName("ak")
   boolean method11235(int var1, boolean var2, boolean var3, boolean var4) {
      int var5 = 0;
      var5 |= var3 ? 1 : 0;
      var5 |= var2 ? 8 : 32;
      var5 |= var4 ? 128 : 0;
      return (var1 & var5) != 0;
   }

   @ObfuscatedSignature(descriptor = "(ZZZB)Z")
   @ObfuscatedName("aj")
   public boolean method11254(boolean var1, boolean var2, boolean var3, byte var4) {
      try {
         return this.method11236(-1207890233 * this.field6242, var1, var2, var3, (byte)-93);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.aj(" + ')');
      }
   }

   classTW(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field6244 = var1 * -298460797;
      this.field6243 = var2 * 1625068029;
      this.field6242 = var3 * 1254707959;
      this.field6246 = -1071650011 * var4;
      this.field6245 = 1342327995 * var5;
      this.field6247 = -823569379 * var6;
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("at")
   public boolean method11240(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6242 * -1207890233, var1, var2, var3, (byte)-127);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bn")
   public boolean method11242(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6246 * 1369847981, var1, var2, var3, (byte)-67);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bx")
   public boolean method11243(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6246 * 1369847981, var1, var2, var3, (byte)-68);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bc")
   public boolean method11247(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6245 * 2113017971, var1, var2, var3, (byte)-33);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bp")
   public boolean method11248(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6245 * -1345601887, var1, var2, var3, (byte)-125);
   }

   @ObfuscatedSignature(descriptor = "(ZZZI)Z")
   @ObfuscatedName("aa")
   public boolean method11244(boolean var1, boolean var2, boolean var3, int var4) {
      try {
         return this.method11231(this.field6246 * 1369847981, var1, var2, var3, (byte)-90);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bw")
   public boolean method11259(boolean var1, boolean var2, boolean var3) {
      return this.method11236(1940103104 * this.field6246, var1, var2, var3, (byte)-106);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bu")
   public boolean method11255(boolean var1, boolean var2, boolean var3) {
      return this.method11236(-1207890233 * this.field6242, var1, var2, var3, (byte)-6);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltw;ZZZ)Z")
   @ObfuscatedName("gt")
   public static boolean method11256(classTW var0, boolean var1, boolean var2, boolean var3) {
      if (var0 == null) {
         var0.vmethod9();
      }

      return var0.method11236(-1207890233 * var0.field6242, var1, var2, var3, (byte)-62);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("ba")
   public boolean method11257(boolean var1, boolean var2, boolean var3) {
      return this.method11236(-1207890233 * this.field6242, var1, var2, var3, (byte)-106);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("an")
   public boolean method11241(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6242 * -1552051970, var1, var2, var3, (byte)-25);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bi")
   public boolean method11260(boolean var1, boolean var2, boolean var3) {
      return this.method11236(1369847981 * this.field6246, var1, var2, var3, (byte)-127);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("be")
   public boolean method11261(boolean var1, boolean var2, boolean var3) {
      return this.method11236(-874077237 * this.field6246, var1, var2, var3, (byte)-33);
   }

   @ObfuscatedSignature(descriptor = "(Lvl;IIIII)Z")
   @ObfuscatedName("ag")
   static boolean method11267(classVL var0, int var1, int var2, int var3, int var4, int var5) {
      try {
         int var7 = var0.method12062((byte)42);
         int var8 = var0.method12056(-655911622);
         int var9 = var0.method12060(-2138789083);
         int var10 = Math.max(var1, Math.min(var8, var3));
         int var11 = Math.max(var2, Math.min(var9, var4));
         int var12 = var8 - var10;
         int var13 = var9 - var11;
         boolean var10000;
         if (var13 * var13 + var12 * var12 <= var7) {
            if (var5 == 1616155503) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "tw.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZB)Z")
   @ObfuscatedName("al")
   public boolean method11252(boolean var1, boolean var2, boolean var3, byte var4) {
      try {
         return this.method11231(this.field6247 * -1476915659, var1, var2, var3, (byte)-21);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cg")
   static boolean method11269(int var0) {
      try {
         boolean var10000;
         if (0 != (-1072700981 * client.field853 & 2)) {
            if (var0 != -811881628) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tw.cg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZI)Z")
   @ObfuscatedName("ao")
   public boolean method11249(boolean var1, boolean var2, boolean var3, int var4) {
      try {
         return this.method11231(this.field6245 * 2113017971, var1, var2, var3, (byte)-84);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tw.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltw;ZZZ)Z")
   @ObfuscatedName("dw")
   public static boolean method11253(classTW var0, boolean var1, boolean var2, boolean var3) {
      return var0.method11231(var0.field6247 * -1884258484, var1, var2, var3, (byte)-32);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ao")
   public static void method11268(byte var0) {
      try {
         classFO.field2344 = false;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tw.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("by")
   public boolean method11265(boolean var1, boolean var2, boolean var3) {
      return this.method11231(this.field6245 * 2113017971, var1, var2, var3, (byte)22);
   }

   @ObfuscatedSignature(descriptor = "(ZZZ)Z")
   @ObfuscatedName("bt")
   public boolean method11245(boolean var1, boolean var2, boolean var3) {
      return this.method11236(this.field6242 * 1369847981, var1, var2, var3, (byte)-67);
   }
}
