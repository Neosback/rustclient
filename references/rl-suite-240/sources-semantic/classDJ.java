import java.io.IOException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dj")
public class classDJ {
   @ObfuscatedSignature(descriptor = "Lyt;")
   @ObfuscatedName("ab")
   public classYT field1533;
   @ObfuscatedName("af")
   int field1519;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("ae")
   classXY field1525;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("az")
   classNV field1520 = new classNV();
   @ObfuscatedSignature(descriptor = "Lxs;")
   @ObfuscatedName("ag")
   PacketBuffer field1521;
   @ObfuscatedName("ac")
   boolean field1524;
   @ObfuscatedName("al")
   int field1527;
   @ObfuscatedSignature(descriptor = "Ljj;")
   @ObfuscatedName("as")
   classJJ field1518;
   @ObfuscatedName("aa")
   int field1522;
   @ObfuscatedName("ao")
   int field1526;
   @ObfuscatedSignature(descriptor = "Ljj;")
   @ObfuscatedName("aq")
   classJJ field1530;
   @ObfuscatedName("ax")
   int field1523;
   @ObfuscatedSignature(descriptor = "Ljj;")
   @ObfuscatedName("ay")
   classJJ field1531;
   @ObfuscatedName("aj")
   int field1528;
   @ObfuscatedSignature(descriptor = "Ljj;")
   @ObfuscatedName("ad")
   classJJ field1529;
   @ObfuscatedSignature(descriptor = "Luk;")
   @ObfuscatedName("ap")
   classUK field1532;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   final void method3366() {
      classNV.method8193(this.field1520);
      this.field1519 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   void method3386() {
      this.field1532 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   final void method3371(int var1) throws IOException {
      try {
         if (this.field1532 != null) {
            if (var1 != 740672100) {
               throw new IllegalStateException();
            }

            if (this.field1519 * -1528349015 > 0) {
               if (var1 != 740672100) {
                  throw new IllegalStateException();
               }

               this.field1525.field6953 = 0;

               while (true) {
                  classJL var2 = (classJL)this.field1520.method8208();
                  if (var2 == null) {
                     if (var1 != 740672100) {
                        throw new IllegalStateException();
                     }
                     break;
                  }

                  if (var2.field3333 * -1886141527 > this.field1525.field6952.length - this.field1525.field6953 * 702114061) {
                     if (var1 != 740672100) {
                        throw new IllegalStateException();
                     }
                     break;
                  }

                  this.field1525.method13067(var2.field3341.field6952, 0, -1886141527 * var2.field3333, (byte)119);
                  this.field1519 = this.field1519 - 150240513 * var2.field3333;
                  var2.vmethod398();
                  var2.field3341.vmethod649(-1571242264);
                  var2.method6428(-949635012);
               }

               this.field1532.vmethod597(this.field1525.field6952, 0, 702114061 * this.field1525.field6953, -1032071721);
               this.field1526 = 0;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dj.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljl;I)V")
   @ObfuscatedName("ae")
   public final void method3374(classJL var1, int var2) {
      try {
         this.field1520.method8196(var1);
         var1.field3333 = var1.field3341.field6953 * -1142155067;
         var1.field3341.field6953 = 0;
         this.field1519 = this.field1519 + 150240513 * var1.field3333;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dj.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("ab")
   void method3381(short var1) {
      try {
         if (this.field1532 != null) {
            if (var1 <= 233) {
               throw new IllegalStateException();
            }

            this.field1532.vmethod604(1271658352);
            this.field1532 = null;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dj.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljl;)V")
   @ObfuscatedName("ap")
   public final void method3375(classJL var1) {
      this.field1520.method8196(var1);
      var1.field3333 = var1.field3341.field6953 * -1142155067;
      var1.field3341.field6953 = 0;
      this.field1519 = this.field1519 + 150240513 * var1.field3333;
   }

   @ObfuscatedSignature(descriptor = "(I)Luk;")
   @ObfuscatedName("as")
   classUK method3394(int var1) {
      try {
         return this.field1532;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dj.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luk;I)V")
   @ObfuscatedName("ax")
   void method3397(classUK var1, int var2) {
      try {
         this.field1532 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dj.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldj;I)V")
   @ObfuscatedName("kr")
   public static void method3387(classDJ var0, int var1) {
      if (var0 == null) {
         var0.method3392(var1);
      }

      try {
         var0.field1532 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dj.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   void method3388() {
      this.field1532 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   final void method3367() {
      classNV.method8193(this.field1520);
      this.field1519 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   final void method3368() {
      classNV.method8193(this.field1520);
      this.field1519 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   final void method3369() {
      classNV.method8193(this.field1520);
      this.field1519 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   void method3389() {
      this.field1532 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   final void method3372() throws IOException {
      if (this.field1532 != null && this.field1519 * -1528349015 > 0) {
         this.field1525.field6953 = 0;

         while (true) {
            classJL var1 = (classJL)this.field1520.method8208();
            if (var1 == null || var1.field3333 * -1886141527 > this.field1525.field6952.length - this.field1525.field6953 * 702114061) {
               this.field1532.vmethod597(this.field1525.field6952, 0, 702114061 * this.field1525.field6953, -1032071721);
               this.field1526 = 0;
               break;
            }

            this.field1525.method13067(var1.field3341.field6952, 0, -1886141527 * var1.field3333, (byte)41);
            this.field1519 = this.field1519 - 150240513 * var1.field3333;
            var1.vmethod398();
            var1.field3341.vmethod649(-1793791069);
            var1.method6428(-205629758);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljl;)V")
   @ObfuscatedName("ad")
   public final void method3376(classJL var1) {
      this.field1520.method8196(var1);
      var1.field3333 = var1.field3341.field6953 * -1340218895;
      var1.field3341.field6953 = 0;
      this.field1519 = this.field1519 + 150240513 * var1.field3333;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)Z")
   @ObfuscatedName("sl")
   public static boolean method3399(client var0) {
      return -965729533 * var0.field794 == 1;
   }

   @ObfuscatedSignature(descriptor = "(Ljl;)V")
   @ObfuscatedName("au")
   public final void method3377(classJL var1) {
      this.field1520.method8196(var1);
      var1.field3333 = var1.field3341.field6953 * -1142155067;
      var1.field3341.field6953 = 0;
      this.field1519 = this.field1519 + 150240513 * var1.field3333;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   final void method3373() throws IOException {
      if (this.field1532 != null && this.field1519 * -1528349015 > 0) {
         this.field1525.field6953 = 0;

         while (true) {
            classJL var1 = (classJL)this.field1520.method8208();
            if (var1 == null || var1.field3333 * -1886141527 > this.field1525.field6952.length - this.field1525.field6953 * 702114061) {
               this.field1532.vmethod597(this.field1525.field6952, 0, 702114061 * this.field1525.field6953, -1032071721);
               this.field1526 = 0;
               break;
            }

            this.field1525.method13067(var1.field3341.field6952, 0, -1886141527 * var1.field3333, (byte)47);
            this.field1519 = this.field1519 - 150240513 * var1.field3333;
            var1.vmethod398();
            var1.field3341.vmethod649(-1710805187);
            var1.method6428(-115563169);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldj;Ljl;)V")
   @ObfuscatedName("sc")
   public static void method3378(classDJ var0, classJL var1) {
      var0.field1520.method8196(var1);
      var1.field3333 = var1.field3341.field6953 * -1142155067;
      var1.field3341.field6953 = 0;
      var0.field1519 = var0.field1519 + 150240513 * var1.field3333;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   void method3382() {
      if (this.field1532 != null) {
         this.field1532.vmethod604(1271658352);
         this.field1532 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   void method3383() {
      if (this.field1532 != null) {
         this.field1532.vmethod604(1271658352);
         this.field1532 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("at")
   void method3384() {
      if (this.field1532 != null) {
         this.field1532.vmethod604(1271658352);
         this.field1532 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   void method3390() {
      this.field1532 = null;
   }

   classDJ() {
      this.field1519 = 0;
      this.field1525 = new classXY(5000);
      this.field1521 = new PacketBuffer(40000);
      this.field1518 = null;
      this.field1523 = 0;
      this.field1524 = true;
      this.field1522 = 0;
      this.field1526 = 0;
      this.field1527 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   void method3391() {
      this.field1532 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   void method3385() {
      if (this.field1532 != null) {
         this.field1532.vmethod604(1271658352);
         this.field1532 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldj;I)V")
   @ObfuscatedName("wu")
   public static void method3370(classDJ var0, int var1) {
      if (var0 == null) {
         var0.method3393(var1);
      } else {
         try {
            classNV.method8193(var0.field1520);
            var0.field1519 = 0;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "dj.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Luk;")
   @ObfuscatedName("bc")
   classUK method3395() {
      return this.field1532;
   }

   @ObfuscatedSignature(descriptor = "()Luk;")
   @ObfuscatedName("bp")
   classUK method3396() {
      return this.field1532;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("cb")
   static int method3398(int var0, Script var1, boolean var2, int var3) {
      try {
         return 2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dj.cb(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldj;Ljl;)V")
   @ObfuscatedName("mh")
   public static void method3379(classDJ var0, classJL var1) {
      var0.field1520.method8196(var1);
      var1.field3333 = var1.field3341.field6953 * -1142155067;
      var1.field3341.field6953 = 0;
      var0.field1519 = var0.field1519 + 150240513 * var1.field3333;
   }

   @ObfuscatedSignature(descriptor = "(IB)Lpw;")
   @ObfuscatedName("af")
   public static classPW method3380(int var0, byte var1) {
      try {
         classPW var2 = (classPW)NodeCache.method6460(classPW.field5443, var0);
         if (null != var2) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classES.field1902.method11942(32, var0, -1028312346);
            var2 = new classPW(new classXY(var3));
            classPW.field5443.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dj.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   void method3392(int var1) {
      try {
         this.field1532 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dj.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   final void method3393(int var1) {
      try {
         this.field1532 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dj.ag(" + ')');
      }
   }
}
