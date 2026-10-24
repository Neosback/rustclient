import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ky")
public class classKY {
   @ObfuscatedName("af")
   public int field4053;
   @ObfuscatedName("ae")
   public int field4054;
   @ObfuscatedName("az")
   public int field4055;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Lul;)V")
   @ObfuscatedName("lw")
   public static void method6691(classUU var0, classUL var1) {
      if (var0 == null) {
         var0.method11664(var1);
      } else {
         float var2 = var1.field6325 * var1.field6325;
         float var3 = var1.field6322 * var1.field6325;
         float var4 = var1.field6323 * var1.field6325;
         float var5 = var1.field6324 * var1.field6325;
         float var6 = var1.field6322 * var1.field6322;
         float var7 = var1.field6323 * var1.field6322;
         float var8 = var1.field6322 * var1.field6324;
         float var9 = var1.field6323 * var1.field6323;
         float var10 = var1.field6323 * var1.field6324;
         float var11 = var1.field6324 * var1.field6324;
         var0.field6383 = var2 + var6 - var11 - var9;
         var0.field6398 = var7 + var5 + var7 + var5;
         var0.field6394 = var8 - var4 - var4 + var8;
         var0.field6392 = var7 + (var7 - var5 - var5);
         var0.field6384 = var2 + var9 - var6 - var11;
         var0.field6396 = var3 + (var10 + var3 + var10);
         var0.field6388 = var4 + (var8 + var4 + var8);
         var0.field6389 = var10 - var3 - var3 + var10;
         var0.field6385 = var2 + var11 - var9 - var6;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bp")
   public static int method6639(int var0) {
      return var0 & 16383;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ap")
   String method6686(String var1, int var2) {
      try {
         return -210579119 * this.field4055
            + var1
            + (this.field4053 * 1870919123 >> 6)
            + var1
            + (this.field4054 * 819227381 >> 6)
            + var1
            + (this.field4053 * 1870919123 & 63)
            + var1
            + (this.field4054 * 819227381 & 63);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ky.ap(" + ')');
      }
   }

   public classKY(classKY var1) {
      this.field4055 = var1.field4055 * 1;
      this.field4053 = 1 * var1.field4053;
      this.field4054 = 1 * var1.field4054;
   }

   public classKY(int var1) {
      if (-1 == var1) {
         this.field4055 = 1004595279;
      } else {
         this.field4055 = (var1 >> 28 & 3) * -1004595279;
         this.field4053 = (var1 >> 14 & 16383) * -1705956773;
         this.field4054 = (var1 & 16383) * -1950125731;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ah")
   public static int method6633(int var0) {
      return var0 >> 14 & 16383;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   public static int method6626(int var0, int var1) {
      try {
         return var0 >> 28;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.af(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (this == var1) {
            return true;
         } else {
            return !(var1 instanceof classKY) ? false : this.method6679((classKY)var1, -1054612885);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   public static int method6640(int var0, int var1) {
      try {
         return var0 & 16383;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.ab(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ak")
   public static int method6627(int var0) {
      return var0 >> 28;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("am")
   public static int method6634(int var0) {
      return var0 >> 14 & 243900667;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ax")
   public static int method6649(int var0, int var1) {
      try {
         return var0 << 7;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ac")
   public static int method6652(int var0, byte var1) {
      try {
         return var0 >> 7;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aa")
   public static int method6656(int var0, int var1) {
      try {
         return 64 + (var0 << 7);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lky;I)I")
   @ObfuscatedName("cz")
   public static int method6663(classKY var0, int var1) {
      if (var0 == null) {
         var0.method6667(var1);
      }

      try {
         if (!var0.method6674(-2012113469)) {
            if (var1 >= -1473998415) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return method6623(var0.field4055 * -210579119, var0.field4053 * 1870919123, var0.field4054 * 819227381, 869394388);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.al(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ao")
   public void method6660(int var1, byte var2) {
      try {
         if (var1 == -1) {
            if (var2 <= 0) {
               throw new IllegalStateException();
            }

            this.field4055 = 1004595279;
         } else {
            this.field4055 = -1004595279 * (var1 >> 28 & 3);
            this.field4053 = -1705956773 * (var1 >> 14 & 16383);
            this.field4054 = -1950125731 * (var1 & 16383);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ky.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ay")
   public void method6672(int var1, int var2, int var3, int var4) {
      try {
         int var5 = var1 >> 14 & 0xFF;
         int var6 = var1 & 0xFF;
         this.field4055 = (var1 >> 28) * -1004595279;
         this.field4053 = -1705956773 * ((var5 << 13) + var2);
         this.field4054 = ((var6 << 13) + var3) * -1950125731;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ky.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aq")
   public boolean method6674(int var1) {
      try {
         boolean var10000;
         if (-210579119 * this.field4055 != -1) {
            if (var1 >= -1898890544) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.aq(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bo")
   public static int method6650(int var0) {
      return var0 << 7;
   }

   @ObfuscatedSignature(descriptor = "(Lky;I)Z")
   @ObfuscatedName("ad")
   boolean method6679(classKY var1, int var2) {
      try {
         if (-210579119 * var1.field4055 != this.field4055 * -210579119) {
            return false;
         } else if (this.field4053 * 1870919123 != var1.field4053 * 1870919123) {
            if (var2 >= -124787136) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            return this.field4054 * 819227381 == var1.field4054 * 819227381;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ky.ad(" + ')');
      }
   }

   public classKY() {
      this.field4055 = 1004595279;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("at")
   public static int method6628(int var0) {
      return var0 >> 28;
   }

   @Override
   public String toString() {
      try {
         return this.method6686(",", -1109686973);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ky.toString(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lky;)Z")
   @ObfuscatedName("wk")
   public static boolean method6675(classKY var0) {
      return -210579119 * var0.field4055 != -1;
   }

   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("az")
   public static int method6623(int var0, int var1, int var2, int var3) {
      try {
         return var0 << 28 | var1 << 14 | var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ky.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("be")
   public static int method6657(int var0) {
      return -1557812876 + (var0 << 7);
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("as")
   public static int method6646(int var0, byte var1) {
      try {
         return var0 >> 3;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method6677(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof classKY) ? false : this.method6679((classKY)var1, -637708466);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bx")
   public static int method6635(int var0) {
      return var0 >> 14 & 933827305;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afs")
   public int method6681() {
      return method6663(this, -2145175243);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("au")
   public static int method6624(int var0, int var1, int var2) {
      return var0 << 28 | var1 << 14 | var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ai")
   public static int method6625(int var0, int var1, int var2) {
      return var0 << 28 | var1 << 14 | var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   public static int method6629(int var0) {
      return var0 >> 28;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aw")
   public static int method6630(int var0) {
      return var0 >> 28;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bw")
   public static int method6653(int var0) {
      return var0 >> 7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bt")
   public static int method6641(int var0) {
      return var0 & 16383;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method6682() {
      return method6663(this, -1835121997);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("av")
   public static int method6631(int var0) {
      return var0 >> 28;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("an")
   public static int method6632(int var0) {
      return var0 >> 28;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bn")
   public static int method6636(int var0) {
      return var0 >> 14 & 16383;
   }

   @Override
   public int hashCode() {
      try {
         return method6663(this, -1513500240);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ky.hashCode(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bc")
   public static int method6637(int var0) {
      return var0 >> 14 & -143063952;
   }

   public classKY(int var1, int var2, int var3) {
      this.field4055 = -1004595279 * var1;
      this.field4053 = var2 * -1705956773;
      this.field4054 = var3 * -1950125731;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("br")
   public static int method6642(int var0) {
      return var0 & 16383;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bm")
   public static int method6643(int var0) {
      return var0 & 708592;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cp")
   String method6687(String var1) {
      return -210579119 * this.field4055
         + var1
         + (this.field4053 * 1870919123 >> 6)
         + var1
         + (this.field4054 * 1630460076 >> 6)
         + var1
         + (this.field4053 * 498009113 & 63)
         + var1
         + (this.field4054 * 819227381 & 1298002963);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bg")
   public static int method6644(int var0) {
      return var0 >> 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bj")
   public static int method6647(int var0) {
      return var0 >> 3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bk")
   public static int method6648(int var0) {
      return var0 >> 3;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method6683() {
      return method6663(this, -1682488359);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bu")
   public static int method6651(int var0) {
      return var0 << 7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ba")
   public static int method6654(int var0) {
      return var0 >> 7;
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ae")
   public static int method6638(int var0, byte var1) {
      try {
         return var0 >> 14 & 16383;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.ae(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bb")
   public static int method6655(int var0) {
      return var0 >> 7;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bi")
   public static int method6658(int var0) {
      return 64 + (var0 << 7);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bd")
   public int method6664() {
      return !this.method6674(-2080653222) ? -1 : method6623(this.field4055 * -210579119, this.field4053 * 1870919123, this.field4054 * 819227381, -180570067);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("by")
   public static int method6659(int var0) {
      return 64 + (var0 << 7);
   }

   @ObfuscatedSignature(descriptor = "(Lky;I)I")
   @ObfuscatedName("ov")
   public static int method6668(classKY var0, int var1) {
      if (var0 == null) {
         var0.method6671(var1);
      }

      try {
         return method6623(-210579119 * var0.field4055, 1870919123 * var0.field4053 >> 13, 819227381 * var0.field4054 >> 13, 616464649);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bf")
   public void method6661(int var1) {
      if (var1 == -1) {
         this.field4055 = 1004595279;
      } else {
         this.field4055 = -616499308 * (var1 >> 28 & 3);
         this.field4053 = -1705956773 * (var1 >> 14 & 401549677);
         this.field4054 = -1950125731 * (var1 & 16383);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bq")
   public void method6662(int var1) {
      if (var1 == -1) {
         this.field4055 = 1004595279;
      } else {
         this.field4055 = -1004595279 * (var1 >> 28 & 3);
         this.field4053 = -1705956773 * (var1 >> 14 & 16383);
         this.field4054 = -1950125731 * (var1 & 16383);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bs")
   public int method6665() {
      return !this.method6674(-2095806649) ? -1 : method6623(this.field4055 * -210579119, this.field4053 * 1870919123, this.field4054 * 819227381, 1028471499);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method6684() {
      return this.method6686(",", -1962339892);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   public int method6666() {
      return !this.method6674(-1994073201) ? -1 : method6623(this.field4055 * -1739848449, this.field4053 * 1870919123, this.field4054 * 819227381, 1148981277);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bv")
   public int method6669() {
      return method6623(-210579119 * this.field4055, 1870919123 * this.field4053 >> 13, 819227381 * this.field4054 >> 13, 171924525);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bz")
   public int method6670() {
      return method6623(-1728786546 * this.field4055, -681315860 * this.field4053 >> 13, 819227381 * this.field4054 >> 13, 866976691);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("cr")
   public void method6673(int var1, int var2, int var3) {
      int var4 = var1 >> 14 & 1564559674;
      int var5 = var1 & 1602257188;
      this.field4055 = (var1 >> 28) * -1004595279;
      this.field4053 = 472346902 * ((var4 << 13) + var2);
      this.field4054 = ((var5 << 13) + var3) * -630899474;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lky;Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("gh")
   public static String method6688(classKY var0, String var1) {
      return var0 == null
         ? var0.method6690(var1)
         : 1580882188 * var0.field4055
            + var1
            + (var0.field4053 * -1300012944 >> 6)
            + var1
            + (var0.field4054 * 819227381 >> 6)
            + var1
            + (var0.field4053 * 1638215549 & 1844740443)
            + var1
            + (var0.field4054 * -177044557 & 63);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cb")
   public boolean method6676() {
      return -210579119 * this.field4055 != -1;
   }

   @ObfuscatedSignature(descriptor = "(Lky;)Z")
   @ObfuscatedName("ct")
   boolean method6680(classKY var1) {
      if (-210579119 * var1.field4055 != this.field4055 * -210579119) {
         return false;
      } else {
         return this.field4053 * 1870919123 != var1.field4053 * 1062514199 ? false : this.field4054 * 819227381 == var1.field4054 * -1140473026;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   public static int method6645(int var0, int var1) {
      try {
         return var0 >> 3;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method6685() {
      return this.method6686(",", 1179979620);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cl")
   String method6689(String var1) {
      return -1044644918 * this.field4055
         + var1
         + (this.field4053 * 1870919123 >> 6)
         + var1
         + (this.field4054 * 819227381 >> 6)
         + var1
         + (this.field4053 * -1420340128 & -524589859)
         + var1
         + (this.field4054 * 1712142581 & 63);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   public int method6667(int var1) {
      try {
         if (!this.method6674(-2012113469)) {
            if (var1 >= -1473998415) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return method6623(this.field4055 * -210579119, this.field4054 * 1870919123, this.field4055 * 819227381, 869394388);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.al(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   public int method6671(int var1) {
      try {
         return method6623(-210579119 * this.field4055, 1870919123 * this.field4054 >> 13, 819227381 * this.field4054 >> 13, 616464649);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ky.aj(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lky;Ljava/lang/Object;)Z")
   @ObfuscatedName("qr")
   public static boolean method6678(classKY var0, Object var1) {
      if (var0 == null) {
         var0.method6681();
      }

      if (var0 == var1) {
         return true;
      } else {
         return !(var1 instanceof classKY) ? false : var0.method6679((classKY)var1, -690740347);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("cq")
   String method6690(String var1) {
      return 1580882188 * this.field4053
         + var1
         + (this.field4054 * -1300012944 >> 6)
         + var1
         + (this.field4054 * 819227381 >> 6)
         + var1
         + (this.field4054 * 1638215549 & 1844740443)
         + var1
         + (this.field4055 * -177044557 & 63);
   }
}
