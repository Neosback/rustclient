import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import net.runelite.api.events.FocusChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ti")
public class classTI implements KeyListener, FocusListener {
   @ObfuscatedName("af")
   boolean[] field6139 = new boolean[112];
   @ObfuscatedName("as")
   volatile int field6140;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6144 = 100;
   @ObfuscatedName("ag")
   Collection field6138;
   @ObfuscatedName("ab")
   Collection field6142;
   @ObfuscatedSignature(descriptor = "[Ltl;")
   @ObfuscatedName("ae")
   classTL[] field6141 = new classTL[3];
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field6143 = 9;

   @ObfuscatedSignature(descriptor = "()[Z")
   @ObfuscatedName("bx")
   public boolean[] method10895() {
      return this.field6139;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)Z")
   @ObfuscatedName("ss")
   public static boolean method10890(Actor var0) {
      if (var0 == null) {
         var0.vmethod259();
      }

      return var0.method3232(1536677490) == classLV.field4232 && !var0.method3210(-1174896200);
   }

   @Override
   public final synchronized void keyPressed(KeyEvent var1) {
      if (!method10893(var1)) {
         classOE.field4841.getCallbacks().keyPressed(var1);
      }

      classTP.field6183 = var1.isConsumed();
      KeyEvent var3 = var1;
      classTI var2 = this;

      try {
         int var4 = var3.getKeyCode();
         if (var4 >= 0 && var4 < classMO.method7677(-2145667177)) {
            int var5 = classTK.field6147[var4];
            var4 = var5;
            boolean var6 = (var5 & 128) != 0;
            if (var6) {
               var4 = -1;
            }
         } else {
            var4 = -1;
         }

         if (var4 >= 0) {
            if (!var2.field6139[var4]) {
               var2.field6140 = 0;
            }

            var2.field6139[var4] = true;
            var2.field6138.add(new classTP(1, var4));
         }

         var3.consume();
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ti.keyPressed(" + ')');
      }

      classTP.field6183 = false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lti;)V")
   @ObfuscatedName("fu")
   public static void method10874(classTI var0) {
      if (var0 == null) {
         var0.method10868();
      }

      var0.field6140 += -1767512663;
      var0.method10885((byte)-74);

      for (classTP var2 : var0.field6142) {
         int var3 = 0;

         while (var3 < var0.field6141.length && !classTP.method10991(var2, var0.field6141[var3], -160021231)) {
            var3++;
         }
      }

      var0.field6142.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ltl;II)V")
   @ObfuscatedName("az")
   void method10859(classTL var1, int var2, int var3) {
      try {
         this.field6141[var2] = var1;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ti.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ta")
   public int method10896() {
      return this.field6140 * -1014591847;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ae")
   boolean method10865(int var1) {
      try {
         boolean var10000;
         if (this.field6140 * -1014591847 <= 1) {
            if (var1 >= -106096842) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ti.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   int method10862(int var1) {
      try {
         return this.field6140 * -1014591847;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ti.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;B)V")
   @ObfuscatedName("ag")
   synchronized void method10872(Component var1, byte var2) {
      try {
         var1.removeKeyListener(this);
         var1.removeFocusListener(this);
         synchronized (this) {
            this.field6138.add(new classTP(4, 0));
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ti.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   void method10875(byte var1) {
      try {
         this.field6140 += -1767512663;
         this.method10885((byte)-117);
         Iterator var2 = this.field6142.iterator();

         while (var2.hasNext()) {
            if (var1 >= 42) {
               throw new IllegalStateException();
            }

            classTP var3 = (classTP)var2.next();

            for (int var4 = 0; var4 < this.field6141.length; var4++) {
               if (var1 >= 42) {
                  throw new IllegalStateException();
               }

               if (classTP.method10991(var3, this.field6141[var4], -160021231)) {
                  if (var1 >= 42) {
                     throw new IllegalStateException();
                  }
                  break;
               }
            }
         }

         this.field6142.clear();
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ti.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ltl;I)V")
   @ObfuscatedName("ac")
   void method10860(classTL var1, int var2) {
      this.field6141[var2] = var1;
   }

   @Override
   public final synchronized void keyReleased(KeyEvent var1) {
      if (!method10893(var1)) {
         classOE.field4841.getCallbacks().keyReleased(var1);
      }

      classTP.field6183 = var1.isConsumed();
      KeyEvent var3 = var1;
      classTI var2 = this;

      try {
         int var4 = var3.getKeyCode();
         if (var4 >= 0 && var4 < classMO.method7677(-2082023250)) {
            int var5 = classTK.field6147[var4];
            var4 = var5 & -129;
         } else {
            var4 = -1;
         }

         if (var4 >= 0) {
            var2.field6139[var4] = false;
            var2.field6138.add(new classTP(2, var4));
         }

         var3.consume();
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ti.keyReleased(" + ')');
      }

      classTP.field6183 = false;
   }

   @Override
   public final synchronized void keyTyped(KeyEvent var1) {
      if (!method10893(var1)) {
         classOE.field4841.getCallbacks().keyTyped(var1);
      }

      classTP.field6183 = var1.isConsumed();
      KeyEvent var3 = var1;
      classTI var2 = this;

      try {
         char var4 = var3.getKeyChar();
         if (var4 != 0 && var4 != '\uffff' && classNR.method8027(var4, 971921516)) {
            var2.field6138.add(new classTP(3, var4));
         }

         var3.consume();
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ti.keyTyped(" + ')');
      }

      classTP.field6183 = false;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;I)V")
   @ObfuscatedName("ab")
   void method10869(Component var1, int var2) {
      try {
         var1.setFocusTraversalKeysEnabled(false);
         var1.addKeyListener(this);
         var1.addFocusListener(this);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ti.ab(" + ')');
      }
   }

   @Override
   public final synchronized void focusGained(FocusEvent var1) {
      try {
         this.field6138.add(new classTP(4, 1));
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ti.focusGained(" + ')');
      }
   }

   @Override
   public final synchronized void focusLost(FocusEvent var1) {
      this.method10894(var1);

      try {
         for (int var2 = 0; var2 < 112; var2++) {
            if (this.field6139[var2]) {
               this.field6139[var2] = false;
               this.field6138.add(new classTP(2, var2));
            }
         }

         this.field6138.add(new classTP(4, 0));
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ti.focusLost(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afg")
   public final synchronized void method10886(FocusEvent var1) {
      this.field6138.add(new classTP(4, 1));
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("aff")
   public final synchronized void method10887(FocusEvent var1) {
      this.field6138.add(new classTP(4, 1));
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("ah")
   public final synchronized void method10882(KeyEvent var1) {
      char var2 = var1.getKeyChar();
      if (var2 != 0 && var2 != '\uffff' && classNR.method8027(var2, 2029555553)) {
         this.field6138.add(new classTP(3, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)Z")
   @ObfuscatedName("wz")
   public static boolean method10893(KeyEvent var0) {
      return (classOE.field4841.method2442() == 10 || classOE.field4841.method2442() == 11)
         && classOE.field4841.getCurrentLoginField() == 1
         && !var0.isMetaDown()
         && !var0.isControlDown();
   }

   classTI() {
      this.field6140 = 0;
      this.field6138 = new ArrayList(100);
      this.field6142 = new ArrayList(100);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   boolean method10866() {
      return this.field6140 * 1169335602 <= 1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afl")
   public final synchronized void method10888(FocusEvent var1) {
      for (int var2 = 0; var2 < 112; var2++) {
         if (this.field6139[var2]) {
            this.field6139[var2] = false;
            this.field6138.add(new classTP(2, var2));
         }
      }

      this.field6138.add(new classTP(4, 0));
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int method10863() {
      return this.field6140 * -1582919746;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   int method10864() {
      return this.field6140 * -1014591847;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("ge")
   public void method10894(FocusEvent var1) {
      FocusChanged var2 = new FocusChanged();
      var2.setFocused(false);
      classOE.field4841.getCallbacks().post(var2);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aj")
   boolean method10867() {
      return this.field6140 * -1014591847 <= 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lti;Ljava/awt/Component;)V")
   @ObfuscatedName("ng")
   public static void method10870(classTI var0, Component var1) {
      if (var0 == null) {
         var0.method10896();
      }

      var1.setFocusTraversalKeysEnabled(false);
      var1.addKeyListener(var0);
      var1.addFocusListener(var0);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ay")
   boolean method10868() {
      return this.field6140 * -1014591847 <= 1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("bx")
   public final synchronized void method10883(KeyEvent var1) {
      char var2 = var1.getKeyChar();
      if (var2 != 0 && var2 != '\uffff' && classNR.method8027(var2, -750265435)) {
         this.field6138.add(new classTP(3, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("ad")
   void method10871(Component var1) {
      var1.setFocusTraversalKeysEnabled(false);
      var1.addKeyListener(this);
      var1.addFocusListener(this);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afz")
   public final synchronized void method10889(FocusEvent var1) {
      for (int var2 = 0; var2 < 112; var2++) {
         if (this.field6139[var2]) {
            this.field6139[var2] = false;
            this.field6138.add(new classTP(2, var2));
         }
      }

      this.field6138.add(new classTP(4, 0));
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("au")
   synchronized void method10873(Component var1) {
      var1.removeKeyListener(this);
      var1.removeFocusListener(this);
      synchronized (this) {
         this.field6138.add(new classTP(4, 0));
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   void method10876() {
      this.field6140 += -1767512663;
      this.method10885((byte)-52);

      for (classTP var2 : this.field6142) {
         int var3 = 0;

         while (var3 < this.field6141.length && !classTP.method10991(var2, this.field6141[var3], -160021231)) {
            var3++;
         }
      }

      this.field6142.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)Z")
   @ObfuscatedName("na")
   public static boolean method10891(classLH var0) {
      if (var0 == null) {
         var0.method7068();
      }

      return var0.method7055(-2118783281) > 1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("av")
   public final synchronized void method10877(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      if (var2 >= 0 && var2 < classMO.method7677(-2008485335)) {
         int var3 = classTK.field6147[var2];
         var2 = var3;
         boolean var4 = (var3 & 128) != 0;
         if (var4) {
            var2 = -1;
         }
      } else {
         var2 = -1;
      }

      if (var2 >= 0) {
         if (!this.field6139[var2]) {
            this.field6140 = 0;
         }

         this.field6139[var2] = true;
         this.field6138.add(new classTP(1, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("at")
   public final synchronized void method10878(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      if (var2 >= 0 && var2 < classMO.method7677(-2057518936)) {
         int var3 = classTK.field6147[var2];
         var2 = var3;
         boolean var4 = (var3 & 128) != 0;
         if (var4) {
            var2 = -1;
         }
      } else {
         var2 = -1;
      }

      if (var2 >= 0) {
         if (!this.field6139[var2]) {
            this.field6140 = 0;
         }

         this.field6139[var2] = true;
         this.field6138.add(new classTP(1, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("an")
   public final synchronized void method10879(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      if (var2 >= 0 && var2 < classMO.method7677(-2092039702)) {
         int var3 = classTK.field6147[var2];
         var2 = var3;
         boolean var4 = (var3 & 128) != 0;
         if (var4) {
            var2 = -1;
         }
      } else {
         var2 = -1;
      }

      if (var2 >= 0) {
         if (!this.field6139[var2]) {
            this.field6140 = 0;
         }

         this.field6139[var2] = true;
         this.field6138.add(new classTP(1, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("am")
   public final synchronized void method10880(KeyEvent var1) {
      int var2 = var1.getKeyCode();
      if (var2 >= 0 && var2 < classMO.method7677(-1983154445)) {
         int var3 = classTK.field6147[var2];
         var2 = var3 & -129;
      } else {
         var2 = -1;
      }

      if (var2 >= 0) {
         this.field6139[var2] = false;
         this.field6138.add(new classTP(2, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(Ltl;I)V")
   @ObfuscatedName("aa")
   void method10861(classTL var1, int var2) {
      this.field6141[var2] = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/KeyEvent;)V")
   @ObfuscatedName("bn")
   public final synchronized void method10884(KeyEvent var1) {
      char var2 = var1.getKeyChar();
      if (var2 != 0 && var2 != 281755018 && classNR.method8027(var2, 560348594)) {
         this.field6138.add(new classTP(3, var2));
      }

      var1.consume();
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ax")
   synchronized void method10885(byte var1) {
      try {
         Collection var2 = this.field6142;
         this.field6142 = this.field6138;
         this.field6138 = var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ti.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljc;I)V")
   @ObfuscatedName("di")
   static final void method10892(classJC var0, int var1) {
      try {
         PacketBuffer var2 = client.field796.field1521;
         if (var0 == classJC.field3130) {
            int var35 = var2.method13218((byte)-22);
            int var50 = classCJ.field763 * -189864519 + (var35 >> 4 & 7);
            int var65 = (var35 & 7) + 1180602653 * classNT.field4740;
            int var81 = var2.method13095(-346779531);
            int var98 = var81 >> 2;
            int var112 = var81 & 3;
            int var123 = client.field809[var98];
            int var131 = classXY.method13251(var2, 1973365750);
            if (0 <= var50) {
               if (var1 <= 1421159847) {
                  return;
               }

               if (var50 < classCQ.field1218.field1694 * 1296729483 - 1) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  if (0 <= var65) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     if (var65 < classCQ.field1218.field1690 * -1269171107 - 1) {
                        if (var1 <= 1421159847) {
                           throw new IllegalStateException();
                        }

                        int var164;
                        if (-1 == client.field949 * 1170158913) {
                           if (var1 <= 1421159847) {
                              throw new IllegalStateException();
                           }

                           var164 = -483624883 * classCQ.field1218.field1708;
                        } else {
                           var164 = 1170158913 * client.field949;
                        }

                        int var136 = var164;
                        classCD var141 = classXX.method13013(classCQ.field1218, var136, var50, var65, var123, 1348701295);
                        if (var141 != null) {
                           ObjectComposition var146 = classMU.method7771(1490231037 * var141.field675, 1422957852);
                           if (var146.field4963) {
                              if (var1 <= 1421159847) {
                                 throw new IllegalStateException();
                              }

                              classFA.method4577(var136, var50, var65, var98, var112, var123, var131, -208526133);
                              var141.field682 = var131 * -643835989;
                              return;
                           }
                        }

                        boolean var147 = classFA.method4577(var136, var50, var65, var98, var112, var123, var131, -208526133);
                        if (var147) {
                           if (var1 <= 1421159847) {
                              throw new IllegalStateException();
                           }

                           return;
                        }

                        if (var141 != null) {
                           if (var1 <= 1421159847) {
                              throw new IllegalStateException();
                           }

                           var141.field682 = var131 * -643835989;
                        }
                     }
                  }
               }
            }
         } else if (classJC.field3131 == var0) {
            int var34 = var2.method13099(-1843318509);
            int var49 = var2.method13099(-384183043);
            int var64 = var2.method13303((byte)-76);
            boolean var163;
            if (var2.method13095(-346779531) == 1) {
               if (var1 <= 1421159847) {
                  return;
               }

               var163 = true;
            } else {
               var163 = false;
            }

            boolean var80 = var163;
            int var97 = var2.method13250(-1740297108);
            int var111 = var2.method13218((byte)-107);
            int var122 = classCJ.field763 * -189864519 + (var111 >> 4 & 7);
            int var130 = classNT.field4740 * 1180602653 + (var111 & 7);
            int var135 = var2.method13224(-1025595677);
            int var140 = var2.method13224(-1836435558);
            if (WorldView.method3745(classCQ.field1218, var122, var130, (byte)1)) {
               if (var1 <= 1421159847) {
                  throw new IllegalStateException();
               }

               int var145 = client.field949 * 1170158913 == -1 ? classCQ.field1218.field1708 * -483624883 : client.field949 * 1170158913;
               classBA.method778(var145, var122, var130, var97, var64, var135, var49, var34, var140, var80, (byte)-57);
            }
         } else if (var0 == classJC.field3125) {
            if (var1 <= 1421159847) {
               throw new IllegalStateException();
            } else {
               classSS var33 = new classSS();
               int var47 = -1;
               boolean var62 = false;
               int var79 = var2.method13254(-1434408267);
               int var161;
               if (var79 == 65535) {
                  if (var1 <= 1421159847) {
                     return;
                  }

                  var161 = -1;
               } else {
                  var161 = 1612595797 * client.field856 + var79;
               }

               var47 = var161;
               boolean var162;
               if (var2.method13218((byte)-74) == 1) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  var162 = true;
               } else {
                  var162 = false;
               }

               var62 = var162;
               var33.field6030 = classXY.method13262(var2, (byte)29) * -969970217;
               int var96 = classXY.method13113(var2, (byte)1);
               int var10001;
               if (var96 != -1) {
                  if (var1 <= 1421159847) {
                     return;
                  }

                  var10001 = classKY.method6638(var96, (byte)-105);
               } else {
                  var10001 = -1;
               }

               var33.field6036 = var10001 * 1193805589;
               var33.field6032 = (-1 != var96 ? classKY.method6626(var96, -1055292878) : -1) * -1642715775;
               if (-1 != var96) {
                  if (var1 <= 1421159847) {
                     return;
                  }

                  var10001 = classKY.method6640(var96, -560268380);
               } else {
                  var10001 = -1;
               }

               var33.field6031 = var10001 * 70896537;
               var33.field6034 = var2.method13258((byte)40) * 178294521;
               var33.field6033 = var2.method13266(585941008) * 1699250641;
               int var110 = var2.method13250(1448795767);
               var33.field6035 = var2.method13107(831951752) * 688157855;
               classCJ var121 = (classCJ)classYE.field6975.method13637(var110);
               if (null != var121) {
                  if (var1 <= 1421159847) {
                     return;
                  }

                  var121.method1516(var33, var47, var62, 306770163);
               }
            }
         } else if (var0 == classJC.field3120) {
            if (var1 <= 1421159847) {
               throw new IllegalStateException();
            } else {
               int var32 = var2.method13250(-1108929325);
               int var46 = var2.method13225(-1876840737);
               int var61 = classCJ.field763 * -189864519 + (var46 >> 4 & 7);
               int var78 = 1180602653 * classNT.field4740 + (var46 & 7);
               int var95 = classXY.method13113(var2, (byte)1);
               int var109 = -483624883 * classCQ.field1218.field1708;
               if (WorldView.method3745(classCQ.field1218, var61, var78, (byte)1)) {
                  int var160;
                  if (1170158913 * client.field949 == -1) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     var160 = var109;
                  } else {
                     var160 = client.field949 * 1170158913;
                  }

                  int var120 = var160;
                  classCL.method1618(var120, var61, var78, var32, var95, -159961776);
               }
            }
         } else if (classJC.field3121 == var0) {
            if (var1 <= 1421159847) {
               throw new IllegalStateException();
            } else {
               classSW var31 = new classSW();
               var31.field6075 = classXY.method13283(var2, 1575549434) * 1658557511;
               var31.field6090 = var2.method13104((byte)16) * -278025987;
               var31.field6087 = (client.field856 * 1612595797 + var2.method13254(-1436509297)) * -1585926007;
               int var45 = var2.method13254(-1094835482);
               var31.field6089 = var2.method13104((byte)16) * 1056651425;
               var31.field6074 = var2.method13275(-1135627181) * 448369705;
               var31.field6079 = classXY.method13251(var2, 1322098973) * 846057435;
               var31.field6091 = var2.method13250(-105078933) * -391951485;
               var31.field6078 = var2.method13266(882130167) * -1370366721;
               var31.field6086 = var2.method13104((byte)16) * -1957506295;
               int var60 = var2.method13218((byte)-53);
               var31.field6077 = 691497295 * ((var60 >> 4 & 7) + classCJ.field763 * -189864519 + -1444178379 * classCQ.field1218.field1706);
               var31.field6076 = classCQ.field1218.field1708 * -1751710143;
               var31.field6073 = (classCQ.field1218.field1707 * -351145363 + 1180602653 * classNT.field4740 + (var60 & 7)) * -1216819291;
               int var77 = var2.method13295(-2100807916);
               var31.field6072 = classKY.method6638(var77, (byte)-97) * 1535353223;
               var31.field6082 = classKY.method6626(var77, -876664652) * 429718429;
               var31.field6081 = classKY.method6640(var77, -560268380) * 21815931;
               var31.field6083 = (client.field856 * 1612595797 + var2.method13099(-168999643)) * 928894729;
               var31.field6084 = classXY.method13262(var2, (byte)127) * 992233077;
               var31.field6080 = var2.method13104((byte)16) * -333010425;
               var31.field6088 = var2.method13250(116317201) * 1823187999;
               var31.field6085 = var2.method13254(-1426374592) * 40297083;
               classCJ var93 = (classCJ)classYE.field6975.method13637(var45);
               if (var93 == null) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  var93 = new classCJ(var31, client.field815);
                  IterableHashTable.method13620(classYE.field6975, var93, var45);
               }
            }
         } else if (var0 == classJC.field3126) {
            if (var1 <= 1421159847) {
               throw new IllegalStateException();
            } else {
               int var30 = var2.method13258((byte)44);
               int var44 = classXY.method13251(var2, 1406900354);
               int var59 = var2.method13218((byte)-13);
               int var76 = (var59 >> 4 & 7) + -189864519 * classCJ.field763;
               int var92 = classNT.field4740 * 1180602653 + (var59 & 7);
               short var108 = (short)var2.method13258((byte)99);
               int var119 = var2.method13258((byte)121);
               short var129 = (short)var2.method13266(1080191872);
               int var134 = var2.method13303((byte)-101);
               int var139 = var2.method13254(-845572973);
               if (WorldView.method3745(classCQ.field1218, var76, var92, (byte)1)) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  int var159;
                  if (-1 == 1170158913 * client.field949) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     var159 = -483624883 * classCQ.field1218.field1708;
                  } else {
                     var159 = 1170158913 * client.field949;
                  }

                  int var144 = var159;
                  SceneTilePaint.method4824(var144, var76, var92, var44, var134, var108, var30, var129, var119, var139, -606749454);
               }
            }
         } else {
            if (var0 == classJC.field3122) {
               if (var1 <= 1421159847) {
                  return;
               }

               byte var3 = var2.method13097((byte)17);
               int var4 = var2.method13254(-90049927);
               int var5 = var2.method13099(1798961974);
               int var6 = var2.method13224(-305774216);
               int var7 = var6 >> 2;
               int var8 = var6 & 3;
               int var9 = client.field809[var7];
               int var10 = var2.method13254(-601051358);
               byte var11 = var2.method13232((byte)-14);
               byte var12 = var2.method13097((byte)17);
               byte var13 = var2.method13232((byte)-11);
               int var14 = classXY.method13251(var2, 142476591);
               int var15 = var2.method13224(-865502654);
               int var16 = classCJ.field763 * -189864519 + (var15 >> 4 & 7);
               int var17 = (var15 & 7) + classNT.field4740 * 1180602653;
               Player var18 = (Player)IndexedObjectSet.method13449(classCQ.field1218.field1692, var14);
               if (var18 != null) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  int var19 = client.field949 * 1170158913 == -1 ? classCQ.field1218.field1708 * -483624883 : client.field949 * 1170158913;
                  classCH.method1498(var19, var16, var17, var7, var8, var9, var4, var10, var5, var12, var3, var13, var11, var18, 1785952299);
               }
            }

            if (classJC.field3119 == var0) {
               if (var1 > 1421159847) {
                  int var29 = var2.method13225(-882208960);
                  int var43 = var2.method13095(-346779531);
                  String[] var58 = null;
                  if (var43 > 0) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     var58 = new String[5];

                     for (int var74 = 0; var74 < var43; var74++) {
                        int var90 = var2.method13218((byte)-94);
                        String var106 = var2.method13127(88492239);
                        if (var90 >= 0) {
                           if (var1 <= 1421159847) {
                              throw new IllegalStateException();
                           }

                           if (var90 < 5) {
                              if (var1 <= 1421159847) {
                                 throw new IllegalStateException();
                              }

                              var58[var90] = var106;
                           }
                        }
                     }
                  }

                  int var75 = var2.method13095(-346779531);
                  int var91 = -189864519 * classCJ.field763 + (var75 >> 4 & 7);
                  int var107 = (var75 & 7) + 1180602653 * classNT.field4740;
                  int var118 = classXY.method13251(var2, 1547727932);
                  int var128 = var2.method13095(-346779531);
                  int var133 = var128 >> 2;
                  int var138 = var128 & 3;
                  int var143 = client.field809[var133];
                  if (WorldView.method3745(classCQ.field1218, var91, var107, (byte)1)) {
                     int var158;
                     if (-1 == client.field949 * 1170158913) {
                        if (var1 <= 1421159847) {
                           throw new IllegalStateException();
                        }

                        var158 = -483624883 * classCQ.field1218.field1708;
                     } else {
                        var158 = 1170158913 * client.field949;
                     }

                     int var149 = var158;
                     classAT.method691(classCQ.field1218, var149, var91, var107, var143, var118, var133, var138, var29, var58, 0, -1, 792771515);
                  }
               }
            } else if (var0 == classJC.field3123) {
               if (var1 <= 1421159847) {
                  throw new IllegalStateException();
               } else {
                  int var28 = classXY.method13251(var2, 1135057181);
                  int var42 = var2.method13099(1273392177);
                  int var57 = var2.method13225(-914131415);
                  int var73 = var2.method13254(63131065);
                  int var89 = var2.method13275(-73627511);
                  int var105 = classXY.method13113(var2, (byte)1);
                  int var117 = classKY.method6626(var105, -1613263912);
                  int var127 = classKY.method6638(var105, (byte)-50);
                  int var132 = classKY.method6640(var105, -560268380);
                  int var137 = var2.method13224(-1910970546);
                  int var142 = -483624883 * classCQ.field1218.field1708;
                  int var148 = classCJ.field763 * -189864519 + (var137 >> 4 & 7) + classCQ.field1218.field1706 * -1444178379;
                  int var150 = classNT.field4740 * 1180602653 + (var137 & 7) + classCQ.field1218.field1707 * -351145363;
                  int var151 = var2.method13099(-880103579);
                  int var152 = var2.method13254(-1208611562);
                  int var153 = var2.method13107(831951752);
                  int var154 = var2.method13254(-1540229215);
                  if (65535 != var42) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     Projectile var20 = new Projectile(
                        var142,
                        var148,
                        var150,
                        var151,
                        var153,
                        var117,
                        var127,
                        var132,
                        var73,
                        var89,
                        var42,
                        var154 + 1612595797 * client.field856,
                        1612595797 * client.field856 + var152,
                        var57,
                        var28
                     );
                     client.field818.method7946(var20);
                  }
               }
            } else if (classJC.field3124 == var0) {
               int var27 = classXY.method13251(var2, 921884585);
               int var41 = var2.method13224(-666525720);
               int var56 = var2.method13225(-1495300004);
               int var72 = classCJ.field763 * -189864519 + (var56 >> 4 & 7);
               int var88 = 1180602653 * classNT.field4740 + (var56 & 7);
               if (WorldView.method3745(classCQ.field1218, var72, var88, (byte)1)) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  int var157;
                  if (-1 == client.field949 * 1170158913) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     var157 = -483624883 * classCQ.field1218.field1708;
                  } else {
                     var157 = 1170158913 * client.field949;
                  }

                  int var104 = var157;
                  PacketBuffer.method12993(var104, var72, var88, var27, var41, -2087522223);
               }
            } else if (classJC.field3133 == var0) {
               if (var1 > 1421159847) {
                  int var26 = var2.method13254(-1745711172);
                  int var40 = var2.method13254(-228571177);
                  int var55 = var2.method13095(-346779531);
                  int var70 = (var55 >> 4 & 7) + classCJ.field763 * -189864519;
                  int var86 = (var55 & 7) + classNT.field4740 * 1180602653;
                  int var103 = var2.method13224(-1184958884);
                  if (WorldView.method3745(classCQ.field1218, var70, var86, (byte)1)) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     var70 = classKY.method6656(var70, -1336037287);
                     var86 = classKY.method6656(var86, 1830189184);
                     int var156;
                     if (-1 == client.field949 * 1170158913) {
                        if (var1 <= 1421159847) {
                           throw new IllegalStateException();
                        }

                        var156 = -483624883 * classCQ.field1218.field1708;
                     } else {
                        var156 = client.field949 * 1170158913;
                     }

                     int var116 = var156;
                     GraphicsObject var126 = new GraphicsObject(
                        classCQ.field1218,
                        var26,
                        var116,
                        var70,
                        var86,
                        classKV.method6613(classCQ.field1218, var70, var86, var116, (byte)122) - var103,
                        var40,
                        1612595797 * client.field856
                     );
                     classCQ.field1218.field1689.method7946(var126);
                  }
               }
            } else if (classJC.field3129 == var0) {
               int var25 = var2.method13218((byte)-2);
               int var39 = var2.method13218((byte)-52);
               int var54 = var2.method13225(-830745353);
               int var69 = var2.method13218((byte)-37);
               int var85 = classCJ.field763 * -189864519 + (var69 >> 4 & 7);
               int var102 = 1180602653 * classNT.field4740 + (var69 & 7);
               int var115 = var2.method13224(-1869158557) & 31;
               int var125 = var2.method13254(-1104761026);
               if (WorldView.method3745(classCQ.field1218, var85, var102, (byte)1)) {
                  if (var1 <= 1421159847) {
                     return;
                  }

                  GraphicsObject.field475.method10523(classCQ.field1218.field1697 * 2140889407, var125, var85, var102, var115, var25, var39, var54, -287370627);
               }
            } else if (classJC.field3128 == var0) {
               if (var1 <= 1421159847) {
                  throw new IllegalStateException();
               } else {
                  int var24 = var2.method13095(-346779531);
                  int var38 = -189864519 * classCJ.field763 + (var24 >> 4 & 7);
                  int var53 = (var24 & 7) + 1180602653 * classNT.field4740;
                  int var68 = var2.method13225(-792935715);
                  int var84 = var68 >> 2;
                  int var101 = var68 & 3;
                  int var114 = client.field809[var84];
                  if (WorldView.method3745(classCQ.field1218, var38, var53, (byte)1)) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     int var155;
                     if (1170158913 * client.field949 == -1) {
                        if (var1 <= 1421159847) {
                           return;
                        }

                        var155 = classCQ.field1218.field1708 * -483624883;
                     } else {
                        var155 = 1170158913 * client.field949;
                     }

                     int var124 = var155;
                     classAT.method691(classCQ.field1218, var124, var38, var53, var114, -1, var84, var101, 31, null, 0, -1, -455522648);
                  }
               }
            } else if (var0 == classJC.field3132) {
               if (var1 <= 1421159847) {
                  throw new IllegalStateException();
               } else {
                  int var23 = var2.method13303((byte)-102);
                  int var37 = var2.method13218((byte)-122);
                  int var52 = -189864519 * classCJ.field763 + (var37 >> 4 & 7);
                  int var67 = classNT.field4740 * 1180602653 + (var37 & 7);
                  int var83 = var2.method13250(918820734);
                  if (WorldView.method3745(classCQ.field1218, var52, var67, (byte)1)) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     int var100 = -1 == 1170158913 * client.field949 ? -483624883 * classCQ.field1218.field1708 : client.field949 * 1170158913;
                     classGV.method5839(var100, var52, var67, var83, var23, (byte)101);
                  }
               }
            } else if (var0 == classJC.field3127) {
               int var22 = var2.method13225(-1439614678);
               int var36 = (var22 >> 4 & 7) + -189864519 * classCJ.field763;
               int var51 = (var22 & 7) + 1180602653 * classNT.field4740;
               int var66 = var2.method13303((byte)-6);
               int var82 = classXY.method13113(var2, (byte)1);
               int var99 = var2.method13254(-1865069168);
               if (WorldView.method3745(classCQ.field1218, var36, var51, (byte)1)) {
                  if (var1 <= 1421159847) {
                     throw new IllegalStateException();
                  }

                  int var10000;
                  if (1170158913 * client.field949 == -1) {
                     if (var1 <= 1421159847) {
                        throw new IllegalStateException();
                     }

                     var10000 = -483624883 * classCQ.field1218.field1708;
                  } else {
                     var10000 = 1170158913 * client.field949;
                  }

                  int var113 = var10000;
                  classFW.method5090(var113, var36, var51, var99, var66, var82, 227662474);
               }
            }
         }
      } catch (RuntimeException var21) {
         throw classEG.method3899(var21, "ti.di(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;[Ljava/lang/Object;III)V")
   @ObfuscatedName("as")
   public static void method10881(String[] var0, Object[] var1, int var2, int var3, int var4) {
      try {
         if (var2 < var3) {
            if (var4 >= 1008380135) {
               throw new IllegalStateException();
            }

            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            String var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            Object var8 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var8;

            for (int var9 = var2; var9 < var3; var9++) {
               if (var4 >= 1008380135) {
                  throw new IllegalStateException();
               }

               if (var7 != null) {
                  if (var0[var9] == null) {
                     continue;
                  }

                  if (var4 >= 1008380135) {
                     throw new IllegalStateException();
                  }

                  if (var0[var9].compareTo(var7) >= (var9 & 1)) {
                     continue;
                  }

                  if (var4 >= 1008380135) {
                     return;
                  }
               }

               String var10 = var0[var9];
               var0[var9] = var0[var6];
               var0[var6] = var10;
               Object var11 = var1[var9];
               var1[var9] = var1[var6];
               var1[var6++] = var11;
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var8;
            method10881(var0, var1, var2, var6 - 1, 125288097);
            method10881(var0, var1, var6 + 1, var3, -236490995);
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "ti.as(" + ')');
      }
   }
}
