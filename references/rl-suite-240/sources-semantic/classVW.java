import java.awt.Graphics;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vw")
public class classVW {
   @ObfuscatedName("as")
   int field6632;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("af")
   Widget field6620;
   @ObfuscatedName("ae")
   int field6621;
   @ObfuscatedName("ab")
   int field6622;
   @ObfuscatedName("ag")
   boolean field6624;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("az")
   Widget field6628 = null;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("ax")
   Widget field6623;
   @ObfuscatedName("ac")
   boolean field6619;
   @ObfuscatedName("aa")
   int field6626;
   @ObfuscatedName("aj")
   int field6629;
   @ObfuscatedName("al")
   boolean field6625;
   @ObfuscatedName("ao")
   int field6627;
   @ObfuscatedName("ay")
   int field6630;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field6631 = 33;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cw")
   public int method12307() {
      return this.field6627 * -2119087041;
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;IIB)V")
   @ObfuscatedName("az")
   void method12250(Widget var1, Widget var2, int var3, int var4, byte var5) {
      try {
         this.field6628 = var1;
         this.field6620 = var2;
         this.field6621 = var3 * 897114003;
         this.field6622 = var4 * -1777112801;
         this.field6632 = 0;
         this.field6624 = false;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "vw.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfx;Lfz;)V")
   @ObfuscatedName("ih")
   public static void method12331(Model var0, classFZ var1) {
      if (var0 == null) {
         var0.getFaceIndices1();
      }

      var0.field2421 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method12255(int var1) {
      try {
         this.field6628 = null;
         this.field6620 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   public void method12259() {
      this.field6623 = null;
      this.field6619 = false;
      this.field6626 = 680827073;
      this.field6627 = -783879862;
      this.field6625 = false;
      this.field6629 = -700818709;
      this.field6630 = -1592853406;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ch")
   public int method12308() {
      return this.field6627 * 1267290405;
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIS)Z")
   @ObfuscatedName("ag")
   public boolean method12265(Widget var1, int var2, int var3, short var4) {
      try {
         if (var1 == this.field6620) {
            if (var4 >= 222) {
               throw new IllegalStateException();
            } else {
               this.field6619 = true;
               this.field6626 = var2 * -680827073;
               this.field6627 = var3 * -1948998721;
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vw.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;II)V")
   @ObfuscatedName("an")
   void method12251(Widget var1, Widget var2, int var3, int var4) {
      this.field6628 = var1;
      this.field6620 = var2;
      this.field6621 = var3 * 897114003;
      this.field6622 = var4 * -1777112801;
      this.field6632 = 0;
      this.field6624 = false;
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Z")
   @ObfuscatedName("ba")
   public boolean method12262(Widget var1, int var2, int var3) {
      if (this.field6628 == var1) {
         this.field6625 = true;
         this.field6629 = -1238415985 * var2;
         this.field6630 = var3 * -1575374693;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("aa")
   public Widget method12282(int var1) {
      try {
         return this.field6628;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("ao")
   public Widget method12284(int var1) {
      try {
         return this.field6620;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cn")
   public int method12309() {
      return this.field6627 * -2119087041;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bv")
   public boolean method12277() {
      return null != this.field6628;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltd;Ljava/awt/Graphics;)V")
   @ObfuscatedName("rb")
   public static void method12249(classTD var0, Graphics var1) {
      var0.field6117.update(var1);
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;II)V")
   @ObfuscatedName("bn")
   void method12252(Widget var1, Widget var2, int var3, int var4) {
      this.field6628 = var1;
      this.field6620 = var2;
      this.field6621 = var3 * 897114003;
      this.field6622 = var4 * -1777112801;
      this.field6632 = 0;
      this.field6624 = false;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ac")
   public boolean method12278(int var1) {
      try {
         boolean var10000;
         if (null != this.field6628) {
            if (var1 <= 1375698635) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   public int method12302(int var1) {
      try {
         return this.field6630 * 1430697363;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ad(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   public int method12304(int var1) {
      try {
         return -279181121 * this.field6626;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ap(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("au")
   public int method12310(byte var1) {
      try {
         return this.field6627 * -2119087041;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.au(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ai")
   public int method12314(byte var1) {
      try {
         return 42513585 * this.field6632;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ar")
   public void method12317(byte var1) {
      try {
         if (this.field6632 * 42513585 == 0) {
            this.method12255(2140549221);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("at")
   public boolean method12329(int var1) {
      try {
         return this.field6619;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ax")
   public void method12274(byte var1) {
      try {
         this.field6632 += -1963912111;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("cb")
   public Widget method12285() {
      return this.field6620;
   }

   @ObfuscatedSignature(descriptor = "(Lvw;B)I")
   @ObfuscatedName("qf")
   public static int method12294(classVW var0, byte var1) {
      if (var0 == null) {
         return var0.method12313(var1);
      } else {
         try {
            return -542846241 * var0.field6622;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "vw.ay(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cy")
   public int method12297() {
      return -669309901 * this.field6629;
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Z")
   @ObfuscatedName("bj")
   public boolean method12266(Widget var1, int var2, int var3) {
      if (var1 == this.field6620) {
         this.field6619 = true;
         this.field6626 = var2 * -61871017;
         this.field6627 = var3 * -1948998721;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   public void method12256() {
      this.field6628 = null;
      this.field6620 = null;
   }

   @ObfuscatedSignature(descriptor = "(Llw;III)Z")
   @ObfuscatedName("ab")
   public boolean method12263(Widget var1, int var2, int var3, int var4) {
      try {
         if (this.field6628 == var1) {
            if (var4 == -903373568) {
               throw new IllegalStateException();
            } else {
               this.field6625 = true;
               this.field6629 = -1238415985 * var2;
               this.field6630 = var3 * -1575374693;
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vw.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("bz")
   public Widget method12287() {
      return this.field6623;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cz")
   public int method12311() {
      return this.field6627 * -2119087041;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ce")
   public void method12318() {
      if (this.field6632 * 42513585 == 0) {
         this.method12255(2147319392);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvw;B)I")
   @ObfuscatedName("tv")
   public static int method12298(classVW var0, byte var1) {
      if (var0 == null) {
         return var0.method12301(var1);
      } else {
         try {
            return 985413999 * var0.field6629;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "vw.aq(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   public void method12260() {
      this.field6623 = null;
      this.field6619 = false;
      this.field6626 = 680827073;
      this.field6627 = 1948998721;
      this.field6625 = false;
      this.field6629 = 1238415985;
      this.field6630 = 1575374693;
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Z")
   @ObfuscatedName("bb")
   public boolean method12264(Widget var1, int var2, int var3) {
      if (this.field6628 == var1) {
         this.field6625 = true;
         this.field6629 = -363936268 * var2;
         this.field6630 = var3 * -1575374693;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("av")
   public boolean method12326(int var1) {
      try {
         return this.field6625;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Z")
   @ObfuscatedName("bk")
   public boolean method12267(Widget var1, int var2, int var3) {
      if (var1 == this.field6620) {
         this.field6619 = true;
         this.field6626 = var2 * -680827073;
         this.field6627 = var3 * -1948998721;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Z")
   @ObfuscatedName("bw")
   public boolean method12268(Widget var1, int var2, int var3) {
      if (var1 == this.field6620) {
         this.field6619 = true;
         this.field6626 = var2 * -680827073;
         this.field6627 = var3 * -408688570;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("du")
   public boolean method12330() {
      return this.field6619;
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)Z")
   @ObfuscatedName("bo")
   public boolean method12269(Widget var1, int var2, int var3) {
      if (var1 == this.field6620) {
         this.field6619 = true;
         this.field6626 = var2 * -680827073;
         this.field6627 = var3 * -1948998721;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;II)V")
   @ObfuscatedName("am")
   void method12253(Widget var1, Widget var2, int var3, int var4) {
      this.field6628 = var1;
      this.field6620 = var2;
      this.field6621 = var3 * 897114003;
      this.field6622 = var4 * -1777112801;
      this.field6632 = 0;
      this.field6624 = false;
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("by")
   public void method12271(Widget var1) {
      this.field6623 = var1;
   }

   classVW() {
      this.field6620 = null;
      this.field6621 = 0;
      this.field6622 = 0;
      this.field6624 = false;
      this.field6623 = null;
      this.field6619 = false;
      this.field6626 = 680827073;
      this.field6627 = 1948998721;
      this.field6625 = false;
      this.field6629 = 1238415985;
      this.field6630 = 1575374693;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bd")
   public boolean method12279() {
      return null != this.field6628;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bl")
   public boolean method12280() {
      return null != this.field6628;
   }

   @ObfuscatedSignature(descriptor = "(Lvw;I)V")
   @ObfuscatedName("lv")
   public static void method12321(classVW var0, int var1) {
      if (var0 == null) {
         var0.method12323(var1);
      }

      try {
         var0.field6624 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Llw;")
   @ObfuscatedName("al")
   public Widget method12288(int var1) {
      try {
         return this.field6623;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bh")
   public boolean method12281() {
      return null != this.field6628;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("cu")
   public Widget method12283() {
      return this.field6628;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;)V")
   @ObfuscatedName("oc")
   public static void method12275(classVW var0) {
      if (var0 == null) {
         var0.method12287();
      }

      var0.field6632 += -1963912111;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aj")
   public int method12292(byte var1) {
      try {
         return -314698085 * this.field6621;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("ct")
   public Widget method12286() {
      return this.field6620;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvw;Llw;II)Z")
   @ObfuscatedName("uv")
   public static boolean method12270(classVW var0, Widget var1, int var2, int var3) {
      if (var1 == var0.field6620) {
         var0.field6619 = true;
         var0.field6626 = var2 * -680827073;
         var0.field6627 = var3 * -1948998721;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   public void method12261(int var1) {
      try {
         this.field6623 = null;
         this.field6619 = false;
         this.field6626 = 680827073;
         this.field6627 = 1948998721;
         this.field6625 = false;
         this.field6629 = 1238415985;
         this.field6630 = 1575374693;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("cp")
   public Widget method12289() {
      return this.field6623;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("cl")
   public Widget method12290() {
      return this.field6623;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cm")
   public void method12319() {
      if (this.field6632 * 42513585 == 0) {
         this.method12255(2142530390);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cd")
   public int method12293() {
      return -314698085 * this.field6621;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cv")
   public int method12295() {
      return -291548360 * this.field6622;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cs")
   public int method12296() {
      return -542846241 * this.field6622;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("br")
   public void method12257() {
      this.field6628 = null;
      this.field6620 = null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ck")
   public int method12299() {
      return 985413999 * this.field6629;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ca")
   public int method12303() {
      return this.field6630 * 1850358451;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cc")
   public int method12305() {
      return 929882788 * this.field6626;
   }

   @ObfuscatedSignature(descriptor = "(Lvw;S)Z")
   @ObfuscatedName("zj")
   public static boolean method12324(classVW var0, short var1) {
      if (var0 == null) {
         var0.method12325(var1);
      }

      try {
         return var0.field6624;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cf")
   public int method12306() {
      return -279181121 * this.field6626;
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)V")
   @ObfuscatedName("as")
   public void method12272(Widget var1, byte var2) {
      try {
         this.field6623 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vw.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cg")
   public int method12312() {
      return this.field6627 * -2119087041;
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;II)V")
   @ObfuscatedName("bx")
   void method12254(Widget var1, Widget var2, int var3, int var4) {
      this.field6628 = var1;
      this.field6620 = var2;
      this.field6621 = var3 * 897114003;
      this.field6622 = var4 * -1777112801;
      this.field6632 = 0;
      this.field6624 = false;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cj")
   public int method12315() {
      return 42513585 * this.field6632;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ci")
   public int method12316() {
      return 42513585 * this.field6632;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bp")
   public void method12258() {
      this.field6628 = null;
      this.field6620 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   public void method12276() {
      this.field6632 += -1963912111;
   }

   @ObfuscatedSignature(descriptor = "()Llw;")
   @ObfuscatedName("cr")
   public Widget method12291() {
      return this.field6623;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dc")
   public void method12320() {
      if (this.field6632 * 42513585 == 0) {
         this.method12255(2122234658);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dd")
   public void method12322() {
      this.field6624 = true;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dx")
   public boolean method12327() {
      return this.field6625;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dy")
   public boolean method12328() {
      return this.field6625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("co")
   public int method12300() {
      return 985413999 * this.field6629;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aw")
   public void method12323(int var1) {
      try {
         this.field6625 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("ak")
   public boolean method12325(short var1) {
      try {
         return this.field6625;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aq")
   public int method12301(byte var1) {
      try {
         return 985413999 * this.field6630;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("bi")
   public void method12273(Widget var1) {
      this.field6623 = var1;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ay")
   public int method12313(byte var1) {
      try {
         return this.field6630 * -2119087041;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vw.au(" + 41);
      }
   }
}
