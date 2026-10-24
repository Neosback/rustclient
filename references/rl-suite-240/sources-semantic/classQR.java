import net.runelite.api.events.AnimationChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qr")
public class classQR {
   @ObfuscatedSignature(descriptor = "Ldh;")
   @ObfuscatedName("to")
   public Actor field5604;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field5603 = 30;
   @ObfuscatedName("af")
   int field5596 = 554205349;
   @ObfuscatedSignature(descriptor = "Lou;")
   @ObfuscatedName("ae")
   Animation field5597 = null;
   @ObfuscatedName("ag")
   int field5600 = 885908119;
   @ObfuscatedName("as")
   int field5599 = 0;
   @ObfuscatedSignature(descriptor = "Lqr;")
   @ObfuscatedName("os")
   public static final classQR field5601 = new classQR();
   @ObfuscatedName("ab")
   int field5602;
   @ObfuscatedName("ax")
   int field5598 = 0;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aj")
   public int method9799(byte var1) {
      try {
         return -1550586043 * this.field5598;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bz")
   public boolean method9772(int var1) {
      try {
         boolean var10000;
         if (null != this.field5597) {
            if (var1 != 374233424) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lou;")
   @ObfuscatedName("ag")
   public Animation method9775(byte var1) {
      try {
         return this.field5597;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;Lqr;)V")
   @ObfuscatedName("nv")
   public static void method9760(classQR var0, classQR var1) {
      if (var0 == null) {
         var0.method9764(var0);
      } else {
         var0.field5597 = var1.field5597;
         var0.field5596 = 1 * var1.field5596;
         var0.field5602 = 1 * var1.field5602;
         var0.field5600 = 1 * var1.field5600;
         var0.field5599 = 1 * var1.field5599;
         var0.field5598 = var1.field5598 * 1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public int method9776(int var1) {
      try {
         return this.field5596 * 1684838611;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("pt")
   public void method9825(int var1) {
      method9770(this, var1, -2102492830);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cl")
   public void method9826() {
      method9765(this, 571445264);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   public int method9780(int var1) {
      try {
         return 292569817 * this.field5600;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bz")
   public void method9827(int var1, int var2, int var3) {
      this.method9811(var1, var2, var3, 1587600500);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ac")
   public int method9781(int var1) {
      try {
         return this.field5602 * -1399668821;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aa")
   int method9784(int var1) {
      try {
         return this.field5599 * -1301724989;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   public void method9793(int var1) {
      try {
         this.field5599 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqr;B)V")
   @ObfuscatedName("az")
   public void method9761(classQR var1, byte var2) {
      try {
         this.field5597 = var1.field5597;
         this.field5596 = 1 * var1.field5596;
         this.field5602 = 1 * var1.field5602;
         this.field5600 = 1 * var1.field5600;
         this.field5599 = 1 * var1.field5599;
         this.field5598 = var1.field5598 * 1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qr.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ay")
   void method9801(int var1, int var2) {
      try {
         this.field5598 = 796674957 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qr.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("co")
   public void method9817() {
      if (this.field5597 != null) {
         if (!Animation.method8805(this.field5597, (byte)0)) {
            this.field5600 = (int)(Math.random() * this.field5597.field5125.length) * -1085659464;
            this.field5602 = (int)(Math.random() * this.field5597.field5123[this.field5600 * 1757753956]) * 2092757693;
         } else {
            this.field5600 = (int)(Math.random() * this.field5597.method8809(2092567576)) * -885908119;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqr;I)V")
   @ObfuscatedName("dc")
   public static void method9765(classQR var0, int var1) {
      if (var0 == null) {
         var0.method9798(var1);
      } else {
         try {
            method9770(var0, -1, -2102492830);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "qr.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ad")
   public boolean method9807(byte var1) {
      try {
         if (this.field5597 != null) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            }

            if (!this.field5597.method8820((byte)-97)) {
               return false;
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("au")
   void method9812(int var1, int var2, int var3) {
      try {
         this.field5600 = -885908119 * var1;
         this.field5599 = 1538224107 * var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qr.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ai")
   public void method9818(byte var1) {
      try {
         if (this.field5597 != null) {
            if (var1 != 0) {
               return;
            }

            if (!Animation.method8805(this.field5597, (byte)0)) {
               if (var1 != 0) {
                  throw new IllegalStateException();
               }

               this.field5600 = (int)(Math.random() * this.field5597.field5125.length) * -885908119;
               this.field5602 = (int)(Math.random() * this.field5597.field5123[this.field5600 * 292569817]) * -1873132797;
            } else {
               this.field5600 = (int)(Math.random() * this.field5597.method8809(2120971860)) * -885908119;
            }
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqr;)V")
   @ObfuscatedName("ar")
   public void method9762(classQR var1) {
      this.field5597 = var1.field5597;
      this.field5596 = 1 * var1.field5596;
      this.field5602 = 1 * var1.field5602;
      this.field5600 = 1 * var1.field5600;
      this.field5599 = 1 * var1.field5599;
      this.field5598 = var1.field5598 * 1;
   }

   @ObfuscatedSignature(descriptor = "(Lqr;)V")
   @ObfuscatedName("aw")
   public void method9763(classQR var1) {
      this.field5597 = var1.field5597;
      this.field5596 = 1 * var1.field5596;
      this.field5602 = 1 * var1.field5602;
      this.field5600 = 1 * var1.field5600;
      this.field5599 = 1 * var1.field5599;
      this.field5598 = var1.field5598 * 1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cs")
   public void method9819() {
      if (this.field5597 != null) {
         if (!Animation.method8805(this.field5597, (byte)0)) {
            this.field5600 = (int)(Math.random() * this.field5597.field5125.length) * -885908119;
            this.field5602 = (int)(Math.random() * this.field5597.field5123[this.field5600 * -369120787]) * -1426008394;
         } else {
            this.field5600 = (int)(Math.random() * this.field5597.method8809(2025682754)) * -885908119;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   public boolean method9808() {
      return this.field5597 == null || this.field5597.method8820((byte)45);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;I)Z")
   @ObfuscatedName("ge")
   public static boolean method9804(classQR var0, int var1) {
      return var0 == null ? var0.method9829(var1) : var0.method9772(374233424) && 1020301429 * var0.field5598 > 0 && var0.field5598 * -1550586043 < var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   public void method9768(int var1) {
      if (var1 >= 0) {
         if (var1 != 1684838611 * this.field5596) {
            this.field5596 = -554205349 * var1;
            this.field5597 = classQA.method9477(var1, 899173680);
            this.field5598 = 0;
            if (292569817 * this.field5600 == -1) {
               this.field5600 = 0;
            }
         }
      } else {
         this.field5596 = 554205349;
         this.field5597 = null;
         this.field5600 = 885908119;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   public boolean method9828(int var1) {
      return this.method9805(var1, -1109955801);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cp")
   void method9813(int var1, int var2) {
      this.field5600 = -885908119 * var1;
      this.field5599 = 1538224107 * var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ah")
   public void method9769(int var1) {
      if (var1 >= 0) {
         if (var1 != 1684838611 * this.field5596) {
            this.field5596 = -554205349 * var1;
            this.field5597 = classQA.method9477(var1, -2069588109);
            this.field5598 = 0;
            if (292569817 * this.field5600 == -1) {
               this.field5600 = 0;
            }
         }
      } else {
         this.field5596 = 554205349;
         this.field5597 = null;
         this.field5600 = 885908119;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   public void method9766() {
      method9770(this, -1, -2104827973);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bk")
   public int method9800() {
      return -479048773 * this.field5598;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;III)V")
   @ObfuscatedName("xt")
   public static void method9810(classQR var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method9778();
      }

      var0.field5600 = -885908119 * var1;
      var0.field5602 = var2 * -1873132797;
      var0.field5599 = 1538224107 * var3;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aq")
   public boolean method9805(int var1, int var2) {
      try {
         if (this.method9772(374233424)) {
            if (var2 >= 371870280) {
               throw new IllegalStateException();
            }

            if (-1550586043 * this.field5598 > 0 && this.field5598 * -1550586043 < var1) {
               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qr.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bo")
   public int method9777() {
      return this.field5596 * 1684838611;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cr")
   public boolean method9806(int var1) {
      return this.method9772(374233424) && -1550586043 * this.field5598 > 0 && this.field5598 * -1550586043 < var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   public int method9778() {
      return this.field5596 * 1176213214;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("tg")
   public void method9823(int var1) {
      if (this.field5604 != null) {
         if (Actor.field1501) {
            this.field5604.field1481 = (byte)(this.field5604.field1481 | 2);
         } else {
            AnimationChanged var2 = new AnimationChanged();
            var2.setActor(this.field5604);
            classOE.field4841.getCallbacks().post(var2);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cu")
   public boolean method9773() {
      return null != this.field5597;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bm")
   public int method9782() {
      return this.field5602 * -1399668821;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   int method9785() {
      return this.field5599 * 386289198;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ba")
   int method9786() {
      return this.field5599 * -1301724989;
   }

   @ObfuscatedSignature(descriptor = "(Lqr;II)V")
   @ObfuscatedName("au")
   public static void method9770(classQR var0, int var1, int var2) {
      if (var0 == null) {
         var0.method9816(var1, var1);
      } else {
         try {
            if (var1 >= 0) {
               if (var2 >= -1973812941) {
                  throw new IllegalStateException();
               }

               if (var1 != 1684838611 * var0.field5596) {
                  if (var2 >= -1973812941) {
                     throw new IllegalStateException();
                  }

                  var0.field5596 = -554205349 * var1;
                  var0.field5597 = classQA.method9477(var1, 1223677060);
                  var0.field5598 = 0;
                  if (292569817 * var0.field5600 == -1) {
                     if (var2 >= -1973812941) {
                        throw new IllegalStateException();
                     }

                     var0.field5600 = 0;
                  }
               }
            } else {
               var0.field5596 = 554205349;
               var0.field5597 = null;
               var0.field5600 = 885908119;
            }

            var0.method9823(var1);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "qr.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bw")
   int method9787() {
      return this.field5599 * 964553173;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bb")
   public void method9788() {
      this.field5600 = 0;
      this.field5602 = 0;
      this.field5599 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("be")
   public void method9789() {
      this.field5600 = 0;
      this.field5602 = 0;
      this.field5599 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   public void method9794() {
      this.field5599 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)V")
   @ObfuscatedName("ca")
   public static void method9790(classQR var0) {
      if (var0 == null) {
         var0.method9821();
      } else {
         var0.field5600 = 0;
         var0.field5602 = 0;
         var0.field5599 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public void method9795() {
      this.field5599 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Lqr;I)V")
   @ObfuscatedName("ga")
   public static void method9791(classQR var0, int var1) {
      if (var0 == null) {
         var0.method9792(var1);
      } else {
         try {
            var0.field5600 = 0;
            var0.field5602 = 0;
            var0.field5599 = 0;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "qr.ao(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;II)V")
   @ObfuscatedName("hy")
   public static void method9814(classQR var0, int var1, int var2) {
      if (var0 == null) {
         var0.method9794();
      }

      var0.field5600 = -885908119 * var1;
      var0.field5599 = 746306434 * var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   public void method9796() {
      this.field5599 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   public void method9797() {
      this.field5599 = 0;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ap")
   void method9811(int var1, int var2, int var3, int var4) {
      try {
         this.field5600 = -885908119 * var1;
         this.field5602 = var2 * -1873132797;
         this.field5599 = 1538224107 * var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qr.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bv")
   void method9802(int var1) {
      this.field5598 = 796674957 * var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bh")
   void method9803(int var1) {
      this.field5598 = 104606501 * var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("at")
   public void method9767() {
      method9770(this, -1, -2027683095);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cb")
   public boolean method9809() {
      return this.field5597 == null || this.field5597.method8820((byte)36);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bn")
   public boolean method9774() {
      return null != this.field5597;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bt")
   public int method9783() {
      return this.field5602 * -2017797541;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   public void method9771(int var1) {
      if (var1 >= 0) {
         if (var1 != 1684838611 * this.field5596) {
            this.field5596 = 605768274 * var1;
            this.field5597 = classQA.method9477(var1, -1054689050);
            this.field5598 = 0;
            if (292569817 * this.field5600 == -1) {
               this.field5600 = 0;
            }
         }
      } else {
         this.field5596 = 554205349;
         this.field5597 = null;
         this.field5600 = -48311676;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cl")
   void method9815(int var1, int var2) {
      this.field5600 = -885908119 * var1;
      this.field5599 = 1538224107 * var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cd")
   public void method9820() {
      if (this.field5597 != null) {
         if (!Animation.method8805(this.field5597, (byte)0)) {
            this.field5600 = (int)(Math.random() * this.field5597.field5125.length) * -885908119;
            this.field5602 = (int)(Math.random() * this.field5597.field5123[this.field5600 * 292569817]) * -1873132797;
         } else {
            this.field5600 = (int)(Math.random() * this.field5597.method8809(1826079243)) * -885908119;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cy")
   public void method9821() {
      if (this.field5597 != null) {
         if (!Animation.method8805(this.field5597, (byte)0)) {
            this.field5600 = (int)(Math.random() * this.field5597.field5125.length) * -793994568;
            this.field5602 = (int)(Math.random() * this.field5597.field5123[this.field5600 * 292569817]) * -1873132797;
         } else {
            this.field5600 = (int)(Math.random() * this.field5597.method8809(1399513273)) * -885908119;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("as")
   static int method9824(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (var0 < 1000) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classGD.method5429(var0, var1, var2, (byte)-100);
            }
         } else if (var0 < 1100) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classIG.method6251(var0, var1, var2, -1306305670);
            }
         } else if (var0 < 1200) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classCD.method1429(var0, var1, var2, (byte)0);
            }
         } else if (var0 < 1300) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classKD.method6504(var0, var1, var2, -2080612681);
            }
         } else if (var0 < 1400) {
            return classQI.method9633(var0, var1, var2, (byte)-93);
         } else if (var0 < 1500) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classGP.method5623(var0, var1, var2, 1839316601);
            }
         } else if (var0 < 1600) {
            return classME.method7604(var0, var1, var2, (byte)58);
         } else if (var0 < 1700) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classQM.method9704(var0, var1, var2, -1079799996);
            }
         } else if (var0 < 1800) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classPZ.method9457(var0, var1, var2, 1009186202);
            }
         } else if (var0 < 1900) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classLI.method7150(var0, var1, var2, 287165499);
            }
         } else if (var0 < 2000) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classJG.method6408(var0, var1, var2, -935177099);
            }
         } else if (var0 < 2100) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classIG.method6251(var0, var1, var2, -1306305670);
            }
         } else if (var0 < 2200) {
            return classCD.method1429(var0, var1, var2, (byte)0);
         } else if (var0 < 2300) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classKD.method6504(var0, var1, var2, 1223605913);
            }
         } else if (var0 < 2400) {
            return classQI.method9633(var0, var1, var2, (byte)-45);
         } else if (var0 < 2500) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classGP.method5623(var0, var1, var2, 1839316601);
            }
         } else if (var0 < 2600) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classIP.method6274(var0, var1, var2, 1804259678);
            }
         } else if (var0 < 2700) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classML.method7640(var0, var1, var2, 544148111);
            }
         } else if (var0 < 2800) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classGA.method5324(var0, var1, var2, -1545285505);
            }
         } else if (var0 < 2900) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return Actor.method3274(var0, var1, var2, -1769439732);
            }
         } else if (var0 < 3000) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classJG.method6408(var0, var1, var2, -217545795);
            }
         } else if (var0 < 3200) {
            return classKU.method6609(var0, var1, var2, 980584890);
         } else if (var0 < 3300) {
            return Renderable.method3851(var0, var1, var2, (byte)-48);
         } else if (var0 < 3400) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return BufferProvider.method13367(var0, var1, var2, (short)31379);
            }
         } else if (var0 < 3500) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classEF.method3884(var0, var1, var2, 285197325);
            }
         } else if (var0 < 3600) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classHR.method6134(var0, var1, var2, (byte)58);
            }
         } else if (var0 < 3700) {
            return classHU.method6157(var0, var1, var2, (byte)-51);
         } else if (var0 < 3800) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return WallObject.method4637(var0, var1, var2, (byte)3);
            }
         } else if (var0 < 3900) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return WorldEntityConfig.method8536(var0, var1, var2, -1691546308);
            }
         } else if (var0 < 4000) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classGL.method5528(var0, var1, var2, 1310223740);
            }
         } else if (var0 < 4100) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classMC.method7575(var0, var1, var2, (byte)-106);
            }
         } else if (var0 < 4200) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classJF.method6405(var0, var1, var2, (byte)4);
            }
         } else if (var0 < 4300) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classKI.method6526(var0, var1, var2, 1252810249);
            }
         } else if (var0 < 5100) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classQO.method9743(var0, var1, var2, -1694411180);
            }
         } else if (var0 < 5400) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classXO.method12926(var0, var1, var2, 1507698443);
            }
         } else if (var0 < 5600) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classFC.method4613(var0, var1, var2, -1165804573);
            }
         } else if (var0 < 5700) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classTK.method10981(var0, var1, var2, 1780049984);
            }
         } else if (var0 < 6300) {
            return classLO.method7180(var0, var1, var2, -1503643048);
         } else if (var0 < 6600) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classSU.method10737(var0, var1, var2, (byte)-125);
            }
         } else if (var0 < 6700) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classHS.method6139(var0, var1, var2, (byte)34);
            }
         } else if (var0 < 6800) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classOY.method8858(var0, var1, var2, 709249675);
            }
         } else if (var0 < 6900) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classCX.method2701(var0, var1, var2, (byte)-48);
            }
         } else if (var0 < 7000) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classDJ.method3398(var0, var1, var2, 1581164316);
            }
         } else if (var0 < 7100) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classDI.method3365(var0, var1, var2, -2092023006);
            }
         } else if (var0 < 7200) {
            return Projectile.method1229(var0, var1, var2, -1675433603);
         } else if (var0 < 7500) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classFK.method4841(var0, var1, var2, (byte)84);
            }
         } else if (var0 < 7600) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return Friend.method10202(var0, var1, var2, (byte)-26);
            }
         } else if (var0 < 7700) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classNF.method7882(var0, var1, var2, -136438054);
            }
         } else if (var0 < 8000) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classKR.method6597(var0, var1, var2, (byte)-128);
            }
         } else if (var0 < 8100) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classFW.method5091(var0, var1, var2, 1848311264);
            }
         } else if (var0 < 8600) {
            if (var3 == 1) {
               throw new IllegalStateException();
            } else {
               return classFS.method5002(var0, var1, var2, (byte)-59);
            }
         } else {
            if (var0 >= 13000) {
               if (var3 == 1) {
                  throw new IllegalStateException();
               }

               if (var0 < 14000) {
                  if (var3 == 1) {
                     throw new IllegalStateException();
                  }

                  return classTR.method11179(var0, var1, var2, 1949974820);
               }
            }

            return 2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qr.as(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)V")
   @ObfuscatedName("jt")
   public static void method9822(classQR var0) {
      if (var0 == null) {
         var0.method9821();
      } else {
         if (var0.field5597 != null) {
            if (!Animation.method8805(var0.field5597, (byte)0)) {
               var0.field5600 = (int)(Math.random() * var0.field5597.field5125.length) * -885908119;
               var0.field5602 = (int)(Math.random() * var0.field5597.field5123[var0.field5600 * 292569817]) * -1873132797;
            } else {
               var0.field5600 = (int)(Math.random() * var0.field5597.method8809(2135374796)) * -885908119;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ae")
   public void method9816(int var1, int var2) {
      this.field5600 = -885908119 * var1;
      this.field5600 = 746306434 * var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   public void method9792(int var1) {
      try {
         this.field5599 = 0;
         this.field5598 = 0;
         this.field5602 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method9798(int var1) {
      try {
         this.field5600 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qr.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqr;)V")
   @ObfuscatedName("ak")
   public void method9764(classQR var1) {
      this.field5597 = var1.field5597;
      this.field5600 = 1 * var1.field5596;
      this.field5599 = 1 * var1.field5598;
      this.field5598 = 1 * var1.field5599;
      this.field5600 = 1 * var1.field5596;
      this.field5596 = var1.field5602 * 1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ol")
   public boolean method9829(int var1) {
      return this.method9805(var1, -1109955801);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   public int method9779() {
      return this.field5598 * 1684838611;
   }
}
