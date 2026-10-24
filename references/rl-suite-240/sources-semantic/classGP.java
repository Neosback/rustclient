import java.awt.Rectangle;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.api.widgets.WidgetUtil;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gp")
public class classGP {
   @ObfuscatedSignature(descriptor = "Ljx;")
   @ObfuscatedName("af")
   final classJX field2632;
   @ObfuscatedSignature(descriptor = "Ljv;")
   @ObfuscatedName("ae")
   final classJV field2637;
   @ObfuscatedSignature(descriptor = "Ljt;")
   @ObfuscatedName("ab")
   final classJT field2635;
   @ObfuscatedSignature(descriptor = "Lga;")
   @ObfuscatedName("ax")
   final classGA field2638 = new classGA();
   @ObfuscatedName("xz")
   public static List field2641 = new ArrayList();
   @ObfuscatedName("rs")
   public static int field2648;
   @ObfuscatedSignature(descriptor = "Lgz;")
   @ObfuscatedName("as")
   final classGZ field2639;
   @ObfuscatedName("aj")
   int field2643;
   @ObfuscatedName("ac")
   int field2633 = 1498649735;
   @ObfuscatedName("aa")
   int field2634 = -1013110651;
   @ObfuscatedSignature(descriptor = "[Llw;")
   @ObfuscatedName("ao")
   Widget[] field2649;
   @ObfuscatedName("al")
   int field2644;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field2645 = 1076101390;
   @ObfuscatedName("tp")
   public static List field2642 = new ArrayList();
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2646 = 5063219;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   static final int field2647 = 73;
   @ObfuscatedSignature(descriptor = "Lgo;")
   @ObfuscatedName("ag")
   final classGO field2636;

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("av")
   void method5596(Widget var1, int var2) {
      int var3 = var1.field4332 * -90794217;
      if (var3 == -318425325) {
         if (1542917833 * this.field2633 == -1) {
            this.field2633 = var1.field4346 * 1013652457;
            this.field2634 = var1.field4347 * 261153745;
         }

         if (this.field2637.vmethod178(-982871142) == 1) {
            var1.field4346 = -2137944143 * this.field2633;
         } else {
            var1.field4346 = this.field2634 * 1718596635;
         }
      } else if (var3 == -1582829748) {
         if (-1 == 419342058 * this.field2633) {
            this.field2633 = -2072594371 * var1.field4346;
            this.field2634 = 261153745 * var1.field4347;
         }

         if (this.field2637.vmethod178(-1178403926) == 1) {
            var1.field4346 = 575002083 * this.field2634;
         } else {
            var1.field4346 = -1309316007 * this.field2633;
         }
      } else if (var3 == 721226762) {
         var1.field4354 = 1656612962;
         var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 2047) * 993003239;
         var1.field4331 = -1013325341;
         var1.field4372 = 0;
      } else if (var3 == -267225782) {
         var1.field4354 = 2146444786;
         var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 2047) * 993003239;
         var1.field4331 = -1013325341;
         var1.field4372 = 641350865;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("br")
   static String method5610(int var0) {
      String var1 = Integer.toString(var0);

      for (int var2 = var1.length() - 3; var2 > 0; var2 -= 3) {
         var1 = var1.substring(0, var2) + classDO.field1590 + var1.substring(var2);
      }

      if (var1.length() > 9) {
         return " "
            + classQY.method9885(65408, (byte)66)
            + var1.substring(0, var1.length() - 8)
            + classKK.field3827
            + " "
            + classDO.field1591
            + var1
            + classDO.field1589
            + classDO.field1592;
      } else {
         return var1.length() > 6
            ? " "
               + classQY.method9885(16777215, (byte)75)
               + var1.substring(0, var1.length() - 4)
               + classKK.field3655
               + " "
               + classDO.field1591
               + var1
               + classDO.field1589
               + classDO.field1592
            : " " + classQY.method9885(16776960, (byte)119) + var1 + classDO.field1592;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIILvv;II)V")
   @ObfuscatedName("af")
   void method5587(int var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9, int var10) {
      try {
         if (!var8.method7425(var1, 1023807851)) {
            if (var10 != 545645039) {
               throw new IllegalStateException();
            }
         } else {
            this.field2649 = null;
            this.method5594(var8.field4438[var1], 0, var8.field4438[var1].length - 1, -1, -1, var2, var3, var4, var5, var6, var7, var8, var9, 1690058415);
            if (this.field2649 != null) {
               if (var10 != 545645039) {
                  throw new IllegalStateException();
               }

               this.method5594(
                  this.field2649,
                  0,
                  this.field2649.length - 1,
                  -1412584499,
                  -1412584499,
                  var2,
                  var3,
                  var4,
                  var5,
                  this.field2644 * 869302911,
                  -1361289429 * this.field2643,
                  var8,
                  var9,
                  1690058415
               );
               this.field2649 = null;
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gp.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Llw;)Ljava/lang/String;")
   @ObfuscatedName("bp")
   String method5608(String var1, Widget var2) {
      if (var1.indexOf("%") != -1) {
         for (int var3 = 1; var3 <= 5; var3++) {
            while (true) {
               int var4 = var1.indexOf("%" + var3);
               if (var4 == -1) {
                  break;
               }

               int var5 = this.field2637.vmethod181(var2, var3 - 1, -1331144978);
               String var6;
               if (var5 < 1541404362) {
                  var6 = Integer.toString(var5);
               } else {
                  var6 = "*";
               }

               var1 = var1.substring(0, var4) + var6 + var1.substring(var4 + 2);
            }
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;IB)V")
   @ObfuscatedName("ab")
   void method5597(Widget var1, int var2, byte var3) {
      try {
         int var4 = var1.field4332 * -90794217;
         if (var4 == 324) {
            if (var3 == 122) {
               if (1542917833 * this.field2633 == -1) {
                  if (var3 != 122) {
                     throw new IllegalStateException();
                  }

                  this.field2633 = var1.field4346 * 1013652457;
                  this.field2634 = var1.field4347 * 261153745;
               }

               if (this.field2637.vmethod178(-1277628182) == 1) {
                  if (var3 != 122) {
                     return;
                  }

                  var1.field4346 = -1309316007 * this.field2633;
               } else {
                  var1.field4346 = this.field2634 * 575002083;
               }
            }
         } else if (var4 == 325) {
            if (var3 != 122) {
               throw new IllegalStateException();
            } else {
               if (-1 == 1542917833 * this.field2633) {
                  this.field2633 = 1013652457 * var1.field4346;
                  this.field2634 = 261153745 * var1.field4347;
               }

               if (this.field2637.vmethod178(-836548708) == 1) {
                  if (var3 != 122) {
                     throw new IllegalStateException();
                  }

                  var1.field4346 = 575002083 * this.field2634;
               } else {
                  var1.field4346 = -1309316007 * this.field2633;
               }
            }
         } else if (var4 == 327) {
            if (var3 != 122) {
               throw new IllegalStateException();
            } else {
               var1.field4354 = 2146444786;
               var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 2047) * 993003239;
               var1.field4331 = -1013325341;
               var1.field4372 = 0;
            }
         } else if (var4 == 328) {
            if (var3 == 122) {
               var1.field4354 = 2146444786;
               var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 2047) * 993003239;
               var1.field4331 = -1013325341;
               var1.field4372 = 434741949;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gp.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;III)V")
   @ObfuscatedName("ep")
   public static void method5601(classGP var0, int var1, int var2, int var3) {
      if (var0.field2632.vmethod89(797563684).method2738(1803222332)) {
         var2 += 15;
         classVP.field6548.method57("Fps:" + var0.field2637.vmethod172(-554702036), var3 + var1, var2, 16776960, -1);
         var2 += 15;
         Runtime var4 = Runtime.getRuntime();
         int var5 = (int)((var4.totalMemory() - var4.freeMemory()) / 1024L);
         int var6 = 16776960;
         if (var5 > 327680 && !var0.field2637.vmethod177(2088505584)) {
            var6 = 16711680;
         }

         classVP.field6548.method57("Mem:" + var5 + "k", var1 + var3, var2, var6, -1);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IILvv;B)V")
   @ObfuscatedName("ag")
   void method5600(Widget var1, int var2, int var3, classVV var4, byte var5) {
      try {
         classKH var6 = var1.method7254(var4, false, -1052618474);
         if (null != var6) {
            if (this.field2637.vmethod163(-918955062)) {
               if (var5 == -1) {
                  throw new IllegalStateException();
               }

               SpritePixels.method13589(
                  this.field2635.vmethod111(-217993828),
                  var2,
                  var3,
                  var6.field3584 * -1246906611,
                  var6.field3583 * -1637380195,
                  25,
                  25,
                  this.field2637.vmethod186(2137414314),
                  256,
                  var2,
                  var3,
                  var6.field3585,
                  var6.field3582
               );
            } else {
               classYW.method13791(var2, var3, 0, var6.field3585, var6.field3582);
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gp.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;I)V")
   @ObfuscatedName("hb")
   public void method5625(
      Widget[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, classVV var12, int var13
   ) {
      Callbacks var14 = classOE.field4841.getCallbacks();
      classVW var15 = var12.field6612;
      int var16 = field2641.size();

      for (int var17 = var2; var17 <= var3; var17++) {
         Widget var18 = var1[var17];
         if (var18 != null && var18.field4340 * -1502647899 == var4 && var18.field4261 * 653784551 == var5 && !var18.isSelfHidden()) {
            int var19 = var18.getType();
            if (var19 == 5 && var18.getItemId() != -1 && var18.getItemId() != 6512) {
               Rectangle var20 = null;
               if (var18 == classOE.field4841.method1969() && var15.field6624 && var15.field6619) {
                  int var21 = classTZ.field6253 * -38255113;
                  int var22 = classTZ.field6262 * -2144333897;
                  var21 -= var15.field6621 * -314698085;
                  var22 -= var15.field6622 * -542846241;
                  if (var21 < var15.field6626 * -279181121) {
                     var21 = var15.field6626 * -279181121;
                  }

                  if (var21 + var18.getWidth() > var15.field6626 * -279181121 + var15.field6620.getWidth()) {
                     var21 = var15.field6626 * -279181121 + var15.field6620.getWidth() - var18.getWidth();
                  }

                  if (var22 < var15.field6627 * -2119087041) {
                     var22 = var15.field6627 * -2119087041;
                  }

                  if (var22 + var18.getHeight() > var15.field6627 * -2119087041 + var15.field6620.getHeight()) {
                     var22 = var15.field6627 * -2119087041 + var15.field6620.getHeight() - var18.getHeight();
                  }

                  var20 = new Rectangle(var21, var22, var18.getWidth(), var18.getHeight());
               }

               int var36 = var10 + var18.getRelativeX();
               int var38 = var11 + var18.getRelativeY();
               var18.method7368(var36);
               var18.method7364(var38);
               if (var36 >= var6 && var36 <= var8 && var38 >= var7 && var38 <= var9) {
                  WidgetItem var23 = new WidgetItem(var18.getItemId(), var18.getItemQuantity(), var18.getBounds(), var18, var20);
                  field2641.add(var23);
               }
            }
         }
      }

      List var24 = Collections.emptyList();
      if (var16 < field2641.size()) {
         if (var16 > 0) {
            var24 = field2641.subList(var16, field2641.size());
         } else {
            var24 = field2641;
         }
      }

      if (var4 == -1412584499) {
         assert this.field2649 == var1;

         int var25 = -1;

         for (int var28 = 0; var28 < var1.length; var28++) {
            Widget var32 = var1[var28];
            if (var32 != null) {
               var25 = WidgetUtil.componentToInterface(var32.getId());
               break;
            }
         }

         if (var25 == -1) {
            return;
         }

         var14.drawInterface(var25, field2641);
         field2641.clear();
      } else if (var4 != -1) {
         if (var5 != -1) {
            return;
         }

         Widget var26 = classOE.field4841.method2281(var4);
         net.runelite.api.widgets.Widget[] var29 = var26.getChildren();
         if (var29 == null || var29 == var1) {
            var14.drawLayer(var26, var24);
         }
      } else {
         int var27 = -1;

         for (int var30 = 0; var30 < var1.length; var30++) {
            Widget var33 = var1[var30];
            if (var33 != null) {
               var27 = WidgetUtil.componentToInterface(var33.getId());
               break;
            }
         }

         if (var27 == -1) {
            return;
         }

         if (this.field2649 == null) {
            var14.drawInterface(var27, field2641);
            field2641.clear();
         }

         for (int var31 = field2642.size() - 1; var31 >= 0; var31--) {
            net.runelite.api.widgets.Widget var34 = (net.runelite.api.widgets.Widget)field2642.get(var31);
            if (WidgetUtil.componentToInterface(var34.getId()) == var27) {
               var34.setHidden(false);
               field2642.remove(var31);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgp;IIIIIB)V")
   @ObfuscatedName("lp")
   public static void method5606(classGP var0, int var1, int var2, int var3, int var4, int var5, byte var6) {
      if (var0 == null) {
         var0.method5607(var1, var1, var1, var1, var1, var6);
      } else {
         try {
            IndexedSprite[] var7 = var0.field2635.vmethod108(1569969832);
            IndexedSprite.method13853(var7[0], var1, var2);
            IndexedSprite.method13853(var7[1], var1, var4 + var2 - 16);
            classYW.method13737(var1, var2 + 16, 16, var4 - 32, 2301979);
            int var8 = (var4 - 32) * var4 / var5;
            if (var8 < 8) {
               if (var6 != -1) {
                  return;
               }

               var8 = 8;
            }

            int var9 = (var4 - 32 - var8) * var3 / (var5 - var4);
            classYW.method13737(var1, var9 + 16 + var2, 16, var8, 5063219);
            classYW.method13762(var1, var2 + 16 + var9, var8, 7759444);
            classYW.method13762(1 + var1, var2 + 16 + var9, var8, 7759444);
            classYW.method13756(var1, var9 + var2 + 16, 16, 7759444);
            classYW.method13756(var1, 17 + var2 + var9, 16, 7759444);
            classYW.method13762(var1 + 15, var2 + 16 + var9, var8, 3353893);
            classYW.method13762(14 + var1, var9 + 17 + var2, var8 - 1, 3353893);
            classYW.method13756(var1, var9 + 15 + var2 + var8, 16, 3353893);
            classYW.method13756(var1 + 1, var8 + 14 + var2 + var9, 15, 3353893);
         } catch (RuntimeException var10) {
            throw classEG.method3899(var10, "gp.ax(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;IIIIIIILvv;I)V")
   @ObfuscatedName("uq")
   public static void method5588(classGP var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9) {
      if (var0 == null) {
         var0.method5583(var1, var1, var8, var1, var1);
      } else if (var8.method7425(var1, -104541086)) {
         var0.field2649 = null;
         var0.method5594(var8.field4438[var1], 0, var8.field4438[var1].length - 1, -1, -1, var2, var3, var4, var5, var6, var7, var8, var9, 1690058415);
         if (var0.field2649 != null) {
            var0.method5594(
               var0.field2649,
               0,
               var0.field2649.length - 1,
               465860321,
               236557616,
               var2,
               var3,
               var4,
               var5,
               var0.field2644 * 869302911,
               -1560065790 * var0.field2643,
               var8,
               var9,
               1690058415
            );
            var0.field2649 = null;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;[Llw;IIIIIIIIIILvv;I)V")
   @ObfuscatedName("mi")
   public static void method5590(
      classGP var0,
      Widget[] var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      classVV var12,
      int var13
   ) {
      if (var0 == null) {
         var0.method5620(var1, var2, var2, var2, var2, var12, var2);
      } else {
         classYW.method13710(var6, var7, var8, var9);
         classFH.method4760();

         for (int var14 = var2; var14 <= var3; var14++) {
            Widget var15 = var1[var14];
            if (null != var15
               && (
                  -1502647899 * var15.field4340 == var4 && var15.field4261 * 653784551 == var5
                     || var4 == -1412584499 && var15 == classVV.method12228(var12, (byte)-77)
               )
               && (!var15.field4273 || !var12.method12196(var15, (byte)0))) {
               if (-90794217 * var15.field4332 > 0) {
                  var0.method5597(var15, var13, (byte)122);
               }

               int var16 = var10 + 744286069 * var15.field4279;
               int var17 = var11 + 1570844829 * var15.field4255;
               int var18 = 1517457381 * var15.field4345;
               if (classVV.method12228(var12, (byte)-13) == var15) {
                  classVW var19 = var12.method12226((byte)0);
                  if (var4 != -1412584499 && !var15.field4312) {
                     var0.field2649 = var1;
                     var0.field2644 = -100057729 * var10;
                     var0.field2643 = 5465987 * var11;
                     continue;
                  }

                  if (classVW.method12324(var19, (short)29981) && var19.method12329(-342361481)) {
                     int var20 = var19.method12304(-44854887);
                     int var21 = var19.method12310((byte)-63);
                     int var22 = var19.method12284(1387801660).field4247 * 1115597881;
                     int var23 = var19.method12284(232000347).field4257 * 293773697;
                     int var24 = classTZ.field6253 * -38255113;
                     int var25 = classTZ.field6262 * -2144333897;
                     var24 -= var19.method12292((byte)82);
                     var25 -= classVW.method12294(var19, (byte)-111);
                     if (var24 < var20) {
                        var24 = var20;
                     }

                     if (1115597881 * var15.field4247 + var24 > var22 + var20) {
                        var24 = var22 + var20 - 1115597881 * var15.field4247;
                     }

                     if (var25 < var21) {
                        var25 = var21;
                     }

                     if (var25 + var15.field4257 * 293773697 > var21 + var23) {
                        var25 = var21 + var23 - var15.field4257 * 293773697;
                     }

                     var16 = var24;
                     var17 = var25;
                  }

                  if (!var15.field4312) {
                     var18 = 128;
                  }
               }

               int var33;
               int var34;
               int var35;
               int var36;
               if (9 == -1510882375 * var15.field4373) {
                  int var37 = var16;
                  int var49 = var17;
                  int var59 = var16 + var15.field4247 * 1115597881;
                  int var26 = var15.field4257 * 293773697 + var17;
                  if (var59 < var16) {
                     var37 = var59;
                     var59 = var16;
                  }

                  if (var26 < var17) {
                     var49 = var26;
                     var26 = var17;
                  }

                  var59++;
                  var26++;
                  var33 = var37 > var6 ? var37 : var6;
                  var34 = var49 > var7 ? var49 : var7;
                  var35 = var59 < var8 ? var59 : var8;
                  var36 = var26 < var9 ? var26 : var9;
               } else {
                  int var38 = var15.field4247 * 1115597881 + var16;
                  int var50 = var17 + var15.field4257 * 293773697;
                  var33 = var16 > var6 ? var16 : var6;
                  var34 = var17 > var7 ? var17 : var7;
                  var35 = var38 < var8 ? var38 : var8;
                  var36 = var50 < var9 ? var50 : var9;
               }

               if (!var15.field4273 || var33 < var35 && var34 < var36) {
                  if (0 != -90794217 * var15.field4332) {
                     if (1336 == var15.field4332 * -90794217) {
                        var0.method5602(var16, var17, 1115597881 * var15.field4247, 825915897);
                        continue;
                     }

                     if (1337 == var15.field4332 * -90794217) {
                        var0.field2639.vmethod281(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, -1347788195);
                        classYW.method13710(var6, var7, var8, var9);
                        continue;
                     }

                     if (1338 == -90794217 * var15.field4332) {
                        var0.field2639.vmethod298(var15, var16, var17, (short)-6813);
                        classYW.method13710(var6, var7, var8, var9);
                        continue;
                     }

                     if (1339 == var15.field4332 * -90794217) {
                        var0.method5600(var15, var16, var17, var12, (byte)59);
                        classYW.method13710(var6, var7, var8, var9);
                        continue;
                     }

                     if (1400 == var15.field4332 * -90794217) {
                        var0.field2639
                           .vmethod285(
                              var16,
                              var17,
                              var15.field4247 * 1115597881,
                              293773697 * var15.field4257,
                              var13,
                              ClientPreferences.method2755(var0.field2632.vmethod89(797563684), -1674478907)
                           );
                     }

                     if (1401 == var15.field4332 * -90794217) {
                        var0.field2639.vmethod291(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, -828301679);
                     }

                     if (var15.field4332 * -90794217 == 1402) {
                        var0.field2639.vmethod296(var16, var13, 1479865562);
                     }
                  }

                  if (0 == -1510882375 * var15.field4373) {
                     if (!var15.field4273 && var12.method12196(var15, (byte)0) && !classVV.method12187(var12, 509304192).method12002(var15, (byte)66)) {
                        continue;
                     }

                     if (!var15.field4273) {
                        if (var15.field4329 * -41424189 > var15.field4267 * -714521463 - 293773697 * var15.field4257) {
                           var15.field4329 = var15.field4267 * -1471306045 - var15.field4257 * 577918571;
                        }

                        if (var15.field4329 * -41424189 < 0) {
                           var15.field4329 = 0;
                        }
                     }

                     var0.method5594(
                        var1,
                        var15.field4263 * -1210177335,
                        var15.field4264 * 379541057,
                        -1163656551 * var15.field4327,
                        var15.field4258 * 63131743,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - var15.field4339 * -34809441,
                        var17 - var15.field4329 * -41424189,
                        var12,
                        var13,
                        1690058415
                     );
                     if (var15.field4338 != null) {
                        var0.method5594(
                           var15.field4338,
                           0,
                           var15.field4338.length - 1,
                           -1163656551 * var15.field4327,
                           -1,
                           var33,
                           var34,
                           var35,
                           var36,
                           var16 - var15.field4339 * -34809441,
                           var17 - -41424189 * var15.field4329,
                           var12,
                           var13,
                           1690058415
                        );
                     }

                     if (-1 == var15.field4258 * 63131743) {
                        WidgetNode var39 = (WidgetNode)HashTable.method13432(var12.field6611, var15.field4327 * -1163656551);
                        if (null != var39) {
                           var0.method5587(var39.field5275 * 2023647411, var33, var34, var35, var36, var16, var17, var12, var13, 545645039);
                        }
                     }

                     classYW.method13710(var6, var7, var8, var9);
                     classFH.method4760();
                  } else if (11 == -1510882375 * var15.field4373) {
                     if (var12.method12196(var15, (byte)0) && !classVV.method12187(var12, 271392205).method12002(var15, (byte)43)) {
                        continue;
                     }

                     if (null != var15.field4338) {
                        var0.method5594(
                           var15.field4338,
                           0,
                           var15.field4338.length - 1,
                           var15.field4327 * -1163656551,
                           -1,
                           var33,
                           var34,
                           var35,
                           var36,
                           var16 - -34809441 * var15.field4339,
                           var17 - var15.field4329 * -41424189,
                           var12,
                           var13,
                           1690058415
                        );
                     }

                     classYW.method13710(var6, var7, var8, var9);
                     classFH.method4760();
                  }

                  if (var15.field4373 * -1510882375 == 0 && !var15.field4273 && var15.field4267 * -714521463 > 293773697 * var15.field4257) {
                     method5606(
                        var0,
                        var16 + 1115597881 * var15.field4247,
                        var17,
                        -41424189 * var15.field4329,
                        293773697 * var15.field4257,
                        -714521463 * var15.field4267,
                        (byte)-1
                     );
                  }

                  if (var15.field4373 * -1510882375 != 1) {
                     if (-1510882375 * var15.field4373 == 3) {
                        int var47;
                        if (method5615(var0, var15, (byte)-65)) {
                           var47 = var15.field4293 * 948260113;
                           if (classVV.method12187(var12, 202755170).method12002(var15, (byte)37) && 0 != var15.field4271 * 1396903265) {
                              var47 = var15.field4271 * 1396903265;
                           }
                        } else {
                           var47 = 1740831551 * var15.field4268;
                           if (classVV.method12187(var12, 1121166560).method12002(var15, (byte)12) && 318120869 * var15.field4270 != 0) {
                              var47 = 318120869 * var15.field4270;
                           }
                        }

                        if (var15.field4269) {
                           switch (1768204099 * var15.field4305.field6974) {
                              case 1:
                                 classYW.method13738(
                                    var16,
                                    var17,
                                    1115597881 * var15.field4247,
                                    293773697 * var15.field4257,
                                    1740831551 * var15.field4268,
                                    var15.field4293 * 948260113
                                 );
                                 break;
                              case 2:
                                 classYW.method13804(
                                    var16,
                                    var17,
                                    var15.field4247 * 1115597881,
                                    293773697 * var15.field4257,
                                    var15.field4268 * 1740831551,
                                    var15.field4293 * 948260113,
                                    255 - (var15.field4345 * 1517457381 & 0xFF),
                                    255 - (var15.field4274 * 1991172799 & 0xFF)
                                 );
                                 break;
                              default:
                                 if (var18 == 0) {
                                    classYW.method13737(var16, var17, var15.field4247 * 1115597881, 293773697 * var15.field4257, var47);
                                 } else {
                                    classYW.method13733(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, var47, 256 - (var18 & 0xFF));
                                 }
                           }
                        } else if (0 == var18) {
                           classYW.method13746(var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697, var47);
                        } else {
                           classYW.method13750(var16, var17, var15.field4247 * 1115597881, var15.field4257 * 293773697, var47, 256 - (var18 & 0xFF));
                        }
                     } else if (4 == var15.field4373 * -1510882375) {
                        classZV var46 = Widget.method7244(var15, var12, (byte)-21);
                        if (var46 != null) {
                           String var67 = var15.field4299;
                           int var57;
                           if (method5615(var0, var15, (byte)-77)) {
                              var57 = 948260113 * var15.field4293;
                              if (classVV.method12187(var12, 729081074).method12002(var15, (byte)67) && var15.field4271 * 1396903265 != 0) {
                                 var57 = var15.field4271 * 1396903265;
                              }

                              if (!var15.field4300.isEmpty()) {
                                 var67 = var15.field4300;
                              }
                           } else {
                              var57 = 1740831551 * var15.field4268;
                              if (classVV.method12187(var12, 281984666).method12002(var15, (byte)69) && 0 != var15.field4270 * 318120869) {
                                 var57 = 318120869 * var15.field4270;
                              }
                           }

                           if (var15.field4273 && var15.field4317 * 2073671441 != -1) {
                              ItemComposition var75 = classOB.method8329(2073671441 * var15.field4317, (byte)-48);
                              var67 = var75.method8741(1248604557);
                              if (var67 == null) {
                                 var67 = classKK.field3613;
                              }

                              if ((1633663665 * var75.field5053 == 1 || var15.field4342 * 1190987649 != 1) && -1 != var15.field4342 * 1190987649) {
                                 var67 = classQY.method9885(16748608, (byte)49)
                                    + var67
                                    + classDO.field1592
                                    + " "
                                    + 'x'
                                    + classSX.method10756(var15.field4342 * 1190987649, 1998877936);
                              }
                           }

                           if (var12.method12241(var15, (byte)83)) {
                              var67 = classKK.field3781;
                              var57 = var15.field4268 * 1740831551;
                           }

                           if (!var15.field4273) {
                              var67 = var0.method5609(var67, var15, 1820943487);
                           }

                           var46.method63(
                              var67,
                              var16,
                              var17,
                              var15.field4247 * 1115597881,
                              var15.field4257 * 293773697,
                              var57,
                              var15.field4304 ? 0 : -1,
                              classGK.method5514(var15.field4345 * 1517457381, -1080792665),
                              var15.field4302 * 1409141973,
                              -338086361 * var15.field4303,
                              704962289 * var15.field4301
                           );
                        }
                     } else if (-1510882375 * var15.field4373 == 5) {
                        if (!var15.field4273) {
                           SpritePixels var45 = var15.method7369(var12, method5615(var0, var15, (byte)119), var0.field2632.vmethod78((byte)-12), -1357849894);
                           if (null != var45) {
                              var45.method13555(var16, var17);
                           }
                        } else {
                           SpritePixels var44;
                           if (-1 != 2073671441 * var15.field4317) {
                              var44 = classLJ.method7157(
                                 2073671441 * var15.field4317,
                                 var15.field4342 * 1190987649,
                                 var15.field4281 * -2011859635,
                                 880555563 * var15.field4282,
                                 -278245589 * var15.field4297,
                                 false,
                                 Math.max(36, var15.field4247 * 1115597881),
                                 Math.max(32, 293773697 * var15.field4257),
                                 1683831829
                              );
                           } else {
                              var44 = var15.method7369(var12, false, var0.field2632.vmethod78((byte)0), -503857986);
                           }

                           if (null != var44) {
                              int var56 = var44.field7004;
                              int var66 = var44.field7006;
                              if (!var15.field4280) {
                                 int var74 = -356282368 * var15.field4247 / var56;
                                 if (var15.field4349 * 142218501 != 0) {
                                    classUY.method11780(
                                       var44,
                                       var16 + 1115597881 * var15.field4247 / 2,
                                       var17 + var15.field4257 * 293773697 / 2,
                                       var15.field4349 * 142218501,
                                       var74
                                    );
                                 } else if (0 != var18) {
                                    SpritePixels.method13568(
                                       var44, var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, 256 - (var18 & 0xFF)
                                    );
                                 } else if (var56 == var15.field4247 * 1115597881 && 293773697 * var15.field4257 == var66) {
                                    var44.method13555(var16, var17);
                                 } else {
                                    SpritePixels.method13558(var44, var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697);
                                 }
                              } else {
                                 classYW.method13713(var16, var17, var16 + var15.field4247 * 1115597881, var17 + 293773697 * var15.field4257);
                                 int var73 = (1115597881 * var15.field4247 + (var56 - 1)) / var56;
                                 int var81 = (var66 - 1 + var15.field4257 * 293773697) / var66;

                                 for (int var84 = 0; var84 < var73; var84++) {
                                    for (int var87 = 0; var87 < var81; var87++) {
                                       if (0 != var15.field4349 * 142218501) {
                                          classUY.method11780(
                                             var44, var56 / 2 + var84 * var56 + var16, var66 * var87 + var17 + var66 / 2, 142218501 * var15.field4349, 4096
                                          );
                                       } else if (var18 != 0) {
                                          var44.method13565(var84 * var56 + var16, var17 + var87 * var66, 256 - (var18 & 0xFF));
                                       } else {
                                          var44.method13555(var84 * var56 + var16, var17 + var66 * var87);
                                       }
                                    }
                                 }

                                 classYW.method13710(var6, var7, var8, var9);
                              }
                           }
                        }
                     } else if (6 == -1510882375 * var15.field4373) {
                        Animation var43 = null;
                        int var55 = -1;
                        boolean var65 = method5615(var0, var15, (byte)72);
                        int var72;
                        if (var65) {
                           var72 = 821151237 * var15.field4291;
                        } else {
                           var72 = 2114568197 * var15.field4286;
                        }

                        if (-1 != var72) {
                           var43 = classQA.method9477(var72, -1919805112);
                           var55 = -814893963 * var15.field4319;
                        }

                        Model var79 = null;
                        int var83 = 0;
                        if (2073671441 * var15.field4317 != -1) {
                           var79 = var0.field2636.vmethod115(var15, (byte)-24);
                           if (var79 != null) {
                              Model.method5254(var79);
                              var83 = var79.field1741 * -1256242689 / 2;
                           }
                        } else if (5 == var15.field4331 * -910601) {
                           var79 = var0.field2636.vmethod119(var15, (byte)4);
                        } else if (var15.field4331 * -910601 == 7) {
                           var79 = var0.field2636.vmethod122(var15, (byte)23);
                        } else if (8 == var15.field4331 * -910601) {
                           var79 = var0.field2636.vmethod127(var15, var43, var55, (byte)14);
                        } else if (9 == var15.field4331 * -910601) {
                           var79 = var0.field2636.vmethod129(var15, var43, var55, (byte)43);
                        } else if (6 == var15.field4331 * -910601) {
                           var79 = var0.field2636.vmethod133(var15, var43, var55, var65, 357498072);
                        } else if (var15.field4331 * -910601 == 2) {
                           var79 = var0.field2636.vmethod139(var15, var43, var55, var65, -1760841734);
                        } else {
                           var79 = var0.field2636.vmethod140(var15, var43, var55, var65, (byte)-34);
                        }

                        classFH.method4776(1115597881 * var15.field4247 / 2 + var16, 293773697 * var15.field4257 / 2 + var17);
                        int var86 = classFH.field2251[-1246332317 * var15.field4354] * var15.field4336 * 245118287 >> 16;
                        int var89 = var15.field4336 * 245118287 * classFH.field2252[var15.field4354 * -1246332317] >> 16;
                        if (null != var79) {
                           classFH.method4704(false);
                           if (!var15.field4273) {
                              var79.method5269(0, -1008620329 * var15.field4290, 0, var15.field4354 * -1246332317, 0, var86, var89);
                           } else {
                              Model.method5254(var79);
                              if (var15.field4295) {
                                 Model.method5188(
                                    var79,
                                    0,
                                    var15.field4290 * -1008620329,
                                    827587461 * var15.field4292,
                                    var15.field4354 * -1246332317,
                                    var15.field4288 * -1469553671,
                                    var86 + var83 + var15.field4289 * -30521739,
                                    var15.field4289 * -30521739 + var89,
                                    var15.field4336 * 245118287
                                 );
                              } else {
                                 var79.method5269(
                                    0,
                                    -1008620329 * var15.field4290,
                                    827587461 * var15.field4292,
                                    -1246332317 * var15.field4354,
                                    var15.field4288 * -1469553671,
                                    var83 + var86 + -30521739 * var15.field4289,
                                    var89 + var15.field4289 * -30521739
                                 );
                              }
                           }
                        }

                        classFH.method4774();
                     } else if (-1510882375 * var15.field4373 == 8) {
                        if (classVC.method12008(classVV.method12187(var12, -246102247), var15, (byte)53)) {
                           classZV var42 = var0.field2635.vmethod105(classXJ.field6876, -725461430);
                           int var53 = 0;
                           int var63 = 0;
                           classZV var71 = var42;
                           String var27 = var15.field4299;

                           for (String var76 = var0.method5609(var27, var15, 1881874325); !var76.isEmpty(); var63 += var71.field8 + 1) {
                              int var29 = var76.indexOf(classDO.field1586);
                              String var28;
                              if (-1 != var29) {
                                 var28 = var76.substring(0, var29);
                                 var76 = var76.substring(var29 + 4);
                              } else {
                                 var28 = var76;
                                 var76 = "";
                              }

                              int var30 = var71.method33(var28);
                              if (var30 > var53) {
                                 var53 = var30;
                              }
                           }

                           var53 += 6;
                           var63 += 7;
                           int var85 = var16 + 1115597881 * var15.field4247 - 5 - var53;
                           int var88 = 5 + var15.field4257 * 293773697 + var17;
                           if (var85 < 5 + var16) {
                              var85 = 5 + var16;
                           }

                           if (var85 + var53 > var8) {
                              var85 = var8 - var53;
                           }

                           if (var88 + var63 > var9) {
                              var88 = var9 - var63;
                           }

                           classYW.method13737(var85, var88, var53, var63, 16777120);
                           classYW.method13746(var85, var88, var53, var63, 0);
                           var27 = var15.field4299;
                           int var31 = 2 + var88 + var71.field8;

                           for (String var78 = var0.method5609(var27, var15, 1858786297); !var78.isEmpty(); var31 += 1 + var71.field8) {
                              int var32 = var78.indexOf(classDO.field1586);
                              String var82;
                              if (-1 != var32) {
                                 var82 = var78.substring(0, var32);
                                 var78 = var78.substring(4 + var32);
                              } else {
                                 var82 = var78;
                                 var78 = "";
                              }

                              var71.method51(var82, 3 + var85, var31, 0, -1);
                           }
                        }
                     } else if (9 == -1510882375 * var15.field4373) {
                        int var41;
                        int var52;
                        int var62;
                        int var70;
                        if (var15.field4276) {
                           var41 = var16;
                           var52 = var15.field4257 * 293773697 + var17;
                           var62 = 1115597881 * var15.field4247 + var16;
                           var70 = var17;
                        } else {
                           var41 = var16;
                           var52 = var17;
                           var62 = 1115597881 * var15.field4247 + var16;
                           var70 = var15.field4257 * 293773697 + var17;
                        }

                        if (var15.field4275 * 845712065 == 1) {
                           classYW.method13771(var41, var52, var62, var70, 1740831551 * var15.field4268);
                        } else {
                           classYW.method13784(var41, var52, var62, var70, 845712065 * var15.field4275, var15.field4268 * 1740831551);
                        }
                     } else if (-1510882375 * var15.field4373 == 12) {
                        classLH var40 = var15.method7334(-220764);
                        classLI var51 = var15.method7337(1887235301);
                        if (var40 != null && var51 != null && var40.method7043((byte)-107)) {
                           classZV var61 = Widget.method7244(var15, var12, (byte)90);
                           if (null != var61) {
                              var0.field2638
                                 .method5312(
                                    var16,
                                    var17,
                                    1115597881 * var15.field4247,
                                    293773697 * var15.field4257,
                                    classLH.method7045(var40, 1551027022),
                                    var40.method7049(-319522320),
                                    var40.method7038((byte)-89),
                                    var40.method7039(-76744899),
                                    var40.method7034((byte)3),
                                    395641138
                                 );
                              int var69 = var15.field4304 ? var15.field4282 * 880555563 : -1;
                              if (!var40.method7027(1195932113) && classZD.method13889(var40.method7017(-847223427), -779339817)) {
                                 classGA.method5313(
                                    var0.field2638, var51.field4167 * 201043577, var69, -211319977 * var51.field4170, var51.field4168 * 1532740787, -160511588
                                 );
                                 classGA.method5318(var0.field2638, var40.method7020(228546374), var61, 120041229);
                              } else {
                                 classGA.method5313(
                                    var0.field2638, 1740831551 * var15.field4268, var69, var51.field4170 * -211319977, 1532740787 * var51.field4168, 271374387
                                 );
                                 classGA.method5318(var0.field2638, var40.method7017(-1198434150), var61, 120041229);
                              }

                              classYW.method13710(var6, var7, var8, var9);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("as")
   void method5602(int var1, int var2, int var3, int var4) {
      try {
         if (this.field2632.vmethod89(797563684).method2738(-64602112)) {
            var2 += 15;
            classVP.field6548.method57("Fps:" + this.field2637.vmethod172(-1370570823), var3 + var1, var2, 16776960, -1);
            var2 += 15;
            Runtime var5 = Runtime.getRuntime();
            int var6 = (int)((var5.totalMemory() - var5.freeMemory()) / 1024L);
            int var7 = 16776960;
            if (var6 > 327680 && !this.field2637.vmethod177(2144167031)) {
               if (var4 != 825915897) {
                  return;
               }

               var7 = 16711680;
            }

            classVP.field6548.method57("Mem:" + var6 + "k", var1 + var3, var2, var7, -1);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gp.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;III)V")
   @ObfuscatedName("nl")
   public static void method5603(classGP var0, int var1, int var2, int var3) {
      if (var0.field2632.vmethod89(797563684).method2738(-310743968)) {
         var2 += 15;
         classVP.field6548.method57("Fps:" + var0.field2637.vmethod172(392729646), var3 + var1, var2, 16776960, -1);
         var2 += 15;
         Runtime var4 = Runtime.getRuntime();
         int var5 = (int)((var4.totalMemory() - var4.freeMemory()) / 1024L);
         int var6 = 16776960;
         if (var5 > 327680 && !var0.field2637.vmethod177(2136143972)) {
            var6 = 16711680;
         }

         classVP.field6548.method57("Mem:" + var5 + "k", var1 + var3, var2, var6, -1);
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIILvv;I)V")
   @ObfuscatedName("bw")
   void method5620(Widget[] var1, int var2, int var3, int var4, int var5, classVV var6, int var7) {
      for (int var8 = var2; var8 <= var3; var8++) {
         Widget var9 = var1[var8];
         if (null != var9
            && var4 == var9.field4340 * -1502647899
            && 653784551 * var9.field4261 == var5
            && (!var9.field4273 || !var6.method12196(var9, (byte)0))) {
            if (var9.field4373 * -1510882375 == 0) {
               if (!var9.field4273 && var6.method12196(var9, (byte)0) && !classVV.method12187(var6, 1268851671).method12002(var9, (byte)49)) {
                  continue;
               }

               this.method5621(
                  var1,
                  var9.field4263 * -1210177335,
                  var9.field4264 * 379541057,
                  var9.field4327 * -1163656551,
                  63131743 * var9.field4258,
                  var6,
                  var7,
                  (byte)-128
               );
               if (var9.field4338 != null) {
                  this.method5621(var9.field4338, 0, var9.field4338.length - 1, var9.field4327 * -1163656551, -1, var6, var7, (byte)-112);
               }

               if (var9.field4258 * 63131743 == -1) {
                  WidgetNode var10 = (WidgetNode)HashTable.method13432(var6.field6611, -1163656551 * var9.field4327);
                  if (null != var10) {
                     this.method5617(var10.field5275 * 2023647411, var6, var7, (byte)1);
                  }
               }
            }

            if (6 == -1510882375 * var9.field4373) {
               if (var9.field4286 * 2114568197 != -1 || 821151237 * var9.field4291 != -1) {
                  boolean var14 = method5615(this, var9, (byte)-114);
                  int var11;
                  if (var14) {
                     var11 = var9.field4291 * 821151237;
                  } else {
                     var11 = 2114568197 * var9.field4286;
                  }

                  if (-1 != var11) {
                     Animation var12 = classQA.method9477(var11, -957838605);
                     if (!Animation.method8805(var12, (byte)0)) {
                        var9.field4325 += 650277867 * var7;

                        while (var9.field4325 * 916200643 > var12.field5123[-814893963 * var9.field4319]) {
                           var9.field4325 = var9.field4325 - 650277867 * var12.field5123[-814893963 * var9.field4319];
                           var9.field4319 += 1734048733;
                           if (-814893963 * var9.field4319 >= var12.field5125.length) {
                              var9.field4319 = var9.field4319 - var12.field5132 * -696471245;
                              if (-814893963 * var9.field4319 < 0 || -814893963 * var9.field4319 >= var12.field5125.length) {
                                 var9.field4319 = 0;
                              }
                           }
                        }
                     } else {
                        var9.field4319 += 1734048733 * var7;
                        int var13 = var12.method8809(1812625088);
                        if (-814893963 * var9.field4319 >= var13) {
                           var9.field4319 = var9.field4319 - var12.field5132 * -696471245;
                           if (var9.field4319 * -814893963 < 0 || -814893963 * var9.field4319 >= var13) {
                              var9.field4319 = 0;
                           }
                        }
                     }
                  }
               }

               if (var9.field4260 * 759310449 != 0 && !var9.field4273) {
                  int var15 = 759310449 * var9.field4260 >> 16;
                  int var17 = 759310449 * var9.field4260 << 16 >> 16;
                  var15 *= var7;
                  var17 *= var7;
                  var9.field4354 = (-1246332317 * var9.field4354 + var15 & 2047) * 1703663435;
                  var9.field4290 = (var17 + -1008620329 * var9.field4290 & 2047) * 993003239;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Llw;I)Ljava/lang/String;")
   @ObfuscatedName("ac")
   String method5609(String var1, Widget var2, int var3) {
      try {
         if (var1.indexOf("%") != -1) {
            if (var3 <= 1719251183) {
               throw new IllegalStateException();
            }

            for (int var4 = 1; var4 <= 5; var4++) {
               if (var3 <= 1719251183) {
                  throw new IllegalStateException();
               }

               while (true) {
                  int var5 = var1.indexOf("%" + var4);
                  if (var5 == -1) {
                     if (var3 <= 1719251183) {
                        throw new IllegalStateException();
                     }
                     break;
                  }

                  int var6 = this.field2637.vmethod181(var2, var4 - 1, -1039980405);
                  String var7;
                  if (var6 < 999999999) {
                     if (var3 <= 1719251183) {
                        throw new IllegalStateException();
                     }

                     var7 = Integer.toString(var6);
                  } else {
                     var7 = "*";
                  }

                  var1 = var1.substring(0, var5) + var7 + var1.substring(var5 + 2);
               }
            }
         }

         return var1;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gp.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;IB)V")
   @ObfuscatedName("al")
   void method5617(int var1, classVV var2, int var3, byte var4) {
      try {
         if (!var2.method7425(var1, 1883254673)) {
            if (var4 != 1) {
               throw new IllegalStateException();
            }
         } else {
            this.method5621(var2.field4438[var1], 0, var2.field4438[var1].length - 1, -1, -1, var2, var3, (byte)-24);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gp.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIILvv;IB)V")
   @ObfuscatedName("aj")
   void method5621(Widget[] var1, int var2, int var3, int var4, int var5, classVV var6, int var7, byte var8) {
      try {
         for (int var9 = var2; var9 <= var3; var9++) {
            if (var8 >= 1) {
               throw new IllegalStateException();
            }

            Widget var10 = var1[var9];
            if (null == var10) {
               if (var8 >= 1) {
                  throw new IllegalStateException();
               }
            } else if (var4 == var10.field4340 * -1502647899) {
               if (var8 >= 1) {
                  throw new IllegalStateException();
               }

               if (653784551 * var10.field4261 != var5) {
                  if (var8 >= 1) {
                     throw new IllegalStateException();
                  }
               } else {
                  if (var10.field4273) {
                     if (var8 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (var6.method12196(var10, (byte)0)) {
                        if (var8 >= 1) {
                           throw new IllegalStateException();
                        }
                        continue;
                     }
                  }

                  if (var10.field4373 * -1510882375 == 0) {
                     if (var8 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (!var10.field4273) {
                        if (var8 >= 1) {
                           throw new IllegalStateException();
                        }

                        if (var6.method12196(var10, (byte)0)) {
                           if (var8 >= 1) {
                              throw new IllegalStateException();
                           }

                           if (!classVV.method12187(var6, -208306595).method12002(var10, (byte)37)) {
                              if (var8 >= 1) {
                                 return;
                              }
                              continue;
                           }
                        }
                     }

                     this.method5621(
                        var1,
                        var10.field4263 * -1210177335,
                        var10.field4264 * 379541057,
                        var10.field4327 * -1163656551,
                        63131743 * var10.field4258,
                        var6,
                        var7,
                        (byte)-27
                     );
                     if (var10.field4338 != null) {
                        if (var8 >= 1) {
                           return;
                        }

                        this.method5621(var10.field4338, 0, var10.field4338.length - 1, var10.field4327 * -1163656551, -1, var6, var7, (byte)-36);
                     }

                     if (var10.field4258 * 63131743 == -1) {
                        if (var8 >= 1) {
                           throw new IllegalStateException();
                        }

                        WidgetNode var11 = (WidgetNode)HashTable.method13432(var6.field6611, -1163656551 * var10.field4327);
                        if (null != var11) {
                           if (var8 >= 1) {
                              throw new IllegalStateException();
                           }

                           this.method5617(var11.field5275 * 2023647411, var6, var7, (byte)1);
                        }
                     }
                  }

                  if (6 == -1510882375 * var10.field4373) {
                     if (var8 >= 1) {
                        throw new IllegalStateException();
                     }

                     label215: {
                        if (var10.field4286 * 2114568197 == -1) {
                           if (var8 >= 1) {
                              return;
                           }

                           if (821151237 * var10.field4291 == -1) {
                              break label215;
                           }
                        }

                        boolean var16 = method5615(this, var10, (byte)59);
                        int var12;
                        if (var16) {
                           if (var8 >= 1) {
                              throw new IllegalStateException();
                           }

                           var12 = var10.field4291 * 821151237;
                        } else {
                           var12 = 2114568197 * var10.field4286;
                        }

                        if (-1 != var12) {
                           if (var8 >= 1) {
                              throw new IllegalStateException();
                           }

                           Animation var13 = classQA.method9477(var12, 1650869054);
                           if (!Animation.method8805(var13, (byte)0)) {
                              if (var8 >= 1) {
                                 throw new IllegalStateException();
                              }

                              var10.field4325 += 650277867 * var7;

                              while (var10.field4325 * 916200643 > var13.field5123[-814893963 * var10.field4319]) {
                                 if (var8 >= 1) {
                                    return;
                                 }

                                 var10.field4325 = var10.field4325 - 650277867 * var13.field5123[-814893963 * var10.field4319];
                                 var10.field4319 += 1734048733;
                                 if (-814893963 * var10.field4319 >= var13.field5125.length) {
                                    if (var8 >= 1) {
                                       return;
                                    }

                                    var10.field4319 = var10.field4319 - var13.field5132 * -696471245;
                                    if (-814893963 * var10.field4319 >= 0) {
                                       if (var8 >= 1) {
                                          throw new IllegalStateException();
                                       }

                                       if (-814893963 * var10.field4319 < var13.field5125.length) {
                                          continue;
                                       }

                                       if (var8 >= 1) {
                                          return;
                                       }
                                    }

                                    var10.field4319 = 0;
                                 }
                              }
                           } else {
                              var10.field4319 += 1734048733 * var7;
                              int var14 = var13.method8809(1403803941);
                              label173:
                              if (-814893963 * var10.field4319 >= var14) {
                                 if (var8 >= 1) {
                                    throw new IllegalStateException();
                                 }

                                 var10.field4319 = var10.field4319 - var13.field5132 * -696471245;
                                 if (var10.field4319 * -814893963 >= 0) {
                                    if (var8 >= 1) {
                                       throw new IllegalStateException();
                                    }

                                    if (-814893963 * var10.field4319 < var14) {
                                       break label173;
                                    }
                                 }

                                 var10.field4319 = 0;
                              }
                           }
                        }
                     }

                     if (var10.field4260 * 759310449 != 0) {
                        if (var8 >= 1) {
                           throw new IllegalStateException();
                        }

                        if (!var10.field4273) {
                           if (var8 >= 1) {
                              throw new IllegalStateException();
                           }

                           int var17 = 759310449 * var10.field4260 >> 16;
                           int var19 = 759310449 * var10.field4260 << 16 >> 16;
                           var17 *= var7;
                           var19 *= var7;
                           var10.field4354 = (-1246332317 * var10.field4354 + var17 & 2047) * 1703663435;
                           var10.field4290 = (var19 + -1008620329 * var10.field4290 & 2047) * 993003239;
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "gp.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILvv;II)V")
   @ObfuscatedName("ay")
   public void method5583(int var1, int var2, classVV var3, int var4, int var5) {
      if (var3.field6610 * -77072447 != -1) {
         this.method5617(-77072447 * var3.field6610, var3, var5, (byte)1);
         this.method5587(var3.field6610 * -77072447, 0, 0, var1, var2, 0, 0, var3, var4, 545645039);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIILvv;I)V")
   @ObfuscatedName("ad")
   void method5589(int var1, int var2, int var3, int var4, int var5, int var6, int var7, classVV var8, int var9) {
      if (var8.method7425(var1, 1199514442)) {
         this.field2649 = null;
         this.method5594(var8.field4438[var1], 0, var8.field4438[var1].length - 1, -1, -1, var2, var3, var4, var5, var6, var7, var8, var9, 1690058415);
         if (this.field2649 != null) {
            this.method5594(
               this.field2649,
               0,
               this.field2649.length - 1,
               -1412584499,
               -1412584499,
               var2,
               var3,
               var4,
               var5,
               this.field2644 * 869302911,
               -1361289429 * this.field2643,
               var8,
               var9,
               1690058415
            );
            this.field2649 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("bj")
   boolean method5612(Widget var1) {
      if (null == var1.field4398) {
         return false;
      } else {
         for (int var2 = 0; var2 < var1.field4398.length; var2++) {
            int var3 = this.field2637.vmethod181(var1, var2, 440430089);
            int var4 = var1.field4399[var2];
            if (2 == var1.field4398[var2]) {
               if (var3 >= var4) {
                  return false;
               }
            } else if (3 == var1.field4398[var2]) {
               if (var3 <= var4) {
                  return false;
               }
            } else if (var1.field4398[var2] == 4) {
               if (var4 == var3) {
                  return false;
               }
            } else if (var4 != var3) {
               return false;
            }
         }

         return true;
      }
   }

   public classGP(classJX var1, classJV var2, classJT var3, classGO var4, classGZ var5) {
      this.field2632 = var1;
      this.field2637 = var2;
      this.field2635 = var3;
      this.field2636 = var4;
      this.field2639 = var5;
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;I)V")
   @ObfuscatedName("ar")
   void method5591(
      Widget[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, classVV var12, int var13
   ) {
      classYW.method13710(var6, var7, var8, var9);
      classFH.method4760();

      for (int var14 = var2; var14 <= var3; var14++) {
         Widget var15 = var1[var14];
         if (null != var15
            && (
               -1502647899 * var15.field4340 == var4 && var15.field4261 * 653784551 == var5
                  || var4 == -1412584499 && var15 == classVV.method12228(var12, (byte)-104)
            )
            && (!var15.field4273 || !var12.method12196(var15, (byte)0))) {
            if (-90794217 * var15.field4332 > 0) {
               this.method5597(var15, var13, (byte)122);
            }

            int var16 = var10 + 744286069 * var15.field4279;
            int var17 = var11 + 1570844829 * var15.field4255;
            int var18 = 1517457381 * var15.field4345;
            if (classVV.method12228(var12, (byte)-67) == var15) {
               classVW var19 = var12.method12226((byte)0);
               if (var4 != -1412584499 && !var15.field4312) {
                  this.field2649 = var1;
                  this.field2644 = -100057729 * var10;
                  this.field2643 = 5465987 * var11;
                  continue;
               }

               if (classVW.method12324(var19, (short)15505) && var19.method12329(-1340309653)) {
                  int var20 = var19.method12304(-92209757);
                  int var21 = var19.method12310((byte)5);
                  int var22 = var19.method12284(-117260710).field4247 * 1115597881;
                  int var23 = var19.method12284(124016499).field4257 * 293773697;
                  int var24 = classTZ.field6253 * -38255113;
                  int var25 = classTZ.field6262 * -2144333897;
                  var24 -= var19.method12292((byte)109);
                  var25 -= classVW.method12294(var19, (byte)-120);
                  if (var24 < var20) {
                     var24 = var20;
                  }

                  if (1115597881 * var15.field4247 + var24 > var22 + var20) {
                     var24 = var22 + var20 - 1115597881 * var15.field4247;
                  }

                  if (var25 < var21) {
                     var25 = var21;
                  }

                  if (var25 + var15.field4257 * 293773697 > var21 + var23) {
                     var25 = var21 + var23 - var15.field4257 * 293773697;
                  }

                  var16 = var24;
                  var17 = var25;
               }

               if (!var15.field4312) {
                  var18 = 128;
               }
            }

            int var33;
            int var34;
            int var35;
            int var36;
            if (9 == -1510882375 * var15.field4373) {
               int var37 = var16;
               int var49 = var17;
               int var59 = var16 + var15.field4247 * 1115597881;
               int var26 = var15.field4257 * 293773697 + var17;
               if (var59 < var16) {
                  var37 = var59;
                  var59 = var16;
               }

               if (var26 < var17) {
                  var49 = var26;
                  var26 = var17;
               }

               var59++;
               var26++;
               var33 = var37 > var6 ? var37 : var6;
               var34 = var49 > var7 ? var49 : var7;
               var35 = var59 < var8 ? var59 : var8;
               var36 = var26 < var9 ? var26 : var9;
            } else {
               int var38 = var15.field4247 * 1115597881 + var16;
               int var50 = var17 + var15.field4257 * 293773697;
               var33 = var16 > var6 ? var16 : var6;
               var34 = var17 > var7 ? var17 : var7;
               var35 = var38 < var8 ? var38 : var8;
               var36 = var50 < var9 ? var50 : var9;
            }

            if (!var15.field4273 || var33 < var35 && var34 < var36) {
               if (0 != -90794217 * var15.field4332) {
                  if (1336 == var15.field4332 * -90794217) {
                     this.method5602(var16, var17, 1115597881 * var15.field4247, 825915897);
                     continue;
                  }

                  if (1337 == var15.field4332 * -90794217) {
                     this.field2639.vmethod281(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, -1172287256);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1338 == -90794217 * var15.field4332) {
                     this.field2639.vmethod298(var15, var16, var17, (short)-25715);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1339 == var15.field4332 * -90794217) {
                     this.method5600(var15, var16, var17, var12, (byte)2);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1400 == var15.field4332 * -90794217) {
                     this.field2639
                        .vmethod285(
                           var16,
                           var17,
                           var15.field4247 * 1115597881,
                           293773697 * var15.field4257,
                           var13,
                           ClientPreferences.method2755(this.field2632.vmethod89(797563684), -1674478907)
                        );
                  }

                  if (1401 == var15.field4332 * -90794217) {
                     this.field2639.vmethod291(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, 1090011500);
                  }

                  if (var15.field4332 * -90794217 == 1402) {
                     this.field2639.vmethod296(var16, var13, -309695139);
                  }
               }

               if (0 == -1510882375 * var15.field4373) {
                  if (!var15.field4273 && var12.method12196(var15, (byte)0) && !classVV.method12187(var12, 2064171573).method12002(var15, (byte)56)) {
                     continue;
                  }

                  if (!var15.field4273) {
                     if (var15.field4329 * -41424189 > var15.field4267 * -714521463 - 293773697 * var15.field4257) {
                        var15.field4329 = var15.field4267 * -1471306045 - var15.field4257 * 577918571;
                     }

                     if (var15.field4329 * -41424189 < 0) {
                        var15.field4329 = 0;
                     }
                  }

                  this.method5594(
                     var1,
                     var15.field4263 * -1210177335,
                     var15.field4264 * 379541057,
                     -1163656551 * var15.field4327,
                     var15.field4258 * 63131743,
                     var33,
                     var34,
                     var35,
                     var36,
                     var16 - var15.field4339 * -34809441,
                     var17 - var15.field4329 * -41424189,
                     var12,
                     var13,
                     1690058415
                  );
                  if (var15.field4338 != null) {
                     this.method5594(
                        var15.field4338,
                        0,
                        var15.field4338.length - 1,
                        -1163656551 * var15.field4327,
                        -1,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - var15.field4339 * -34809441,
                        var17 - -41424189 * var15.field4329,
                        var12,
                        var13,
                        1690058415
                     );
                  }

                  if (-1 == var15.field4258 * 63131743) {
                     WidgetNode var39 = (WidgetNode)HashTable.method13432(var12.field6611, var15.field4327 * -1163656551);
                     if (null != var39) {
                        this.method5587(var39.field5275 * 2023647411, var33, var34, var35, var36, var16, var17, var12, var13, 545645039);
                     }
                  }

                  classYW.method13710(var6, var7, var8, var9);
                  classFH.method4760();
               } else if (11 == -1510882375 * var15.field4373) {
                  if (var12.method12196(var15, (byte)0) && !classVV.method12187(var12, 942535804).method12002(var15, (byte)100)) {
                     continue;
                  }

                  if (null != var15.field4338) {
                     this.method5594(
                        var15.field4338,
                        0,
                        var15.field4338.length - 1,
                        var15.field4327 * -1163656551,
                        -1,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - -34809441 * var15.field4339,
                        var17 - var15.field4329 * -41424189,
                        var12,
                        var13,
                        1690058415
                     );
                  }

                  classYW.method13710(var6, var7, var8, var9);
                  classFH.method4760();
               }

               if (var15.field4373 * -1510882375 == 0 && !var15.field4273 && var15.field4267 * -714521463 > 293773697 * var15.field4257) {
                  method5606(
                     this,
                     var16 + 1115597881 * var15.field4247,
                     var17,
                     -41424189 * var15.field4329,
                     293773697 * var15.field4257,
                     -714521463 * var15.field4267,
                     (byte)-1
                  );
               }

               if (var15.field4373 * -1510882375 != 1) {
                  if (-1510882375 * var15.field4373 == 3) {
                     int var47;
                     if (method5615(this, var15, (byte)72)) {
                        var47 = var15.field4293 * 948260113;
                        if (classVV.method12187(var12, 224915379).method12002(var15, (byte)84) && 0 != var15.field4271 * 1396903265) {
                           var47 = var15.field4271 * 1396903265;
                        }
                     } else {
                        var47 = 1740831551 * var15.field4268;
                        if (classVV.method12187(var12, -67540092).method12002(var15, (byte)44) && 318120869 * var15.field4270 != 0) {
                           var47 = 318120869 * var15.field4270;
                        }
                     }

                     if (var15.field4269) {
                        switch (1768204099 * var15.field4305.field6974) {
                           case 1:
                              classYW.method13738(
                                 var16,
                                 var17,
                                 1115597881 * var15.field4247,
                                 293773697 * var15.field4257,
                                 1740831551 * var15.field4268,
                                 var15.field4293 * 948260113
                              );
                              break;
                           case 2:
                              classYW.method13804(
                                 var16,
                                 var17,
                                 var15.field4247 * 1115597881,
                                 293773697 * var15.field4257,
                                 var15.field4268 * 1740831551,
                                 var15.field4293 * 948260113,
                                 255 - (var15.field4345 * 1517457381 & 0xFF),
                                 255 - (var15.field4274 * 1991172799 & 0xFF)
                              );
                              break;
                           default:
                              if (var18 == 0) {
                                 classYW.method13737(var16, var17, var15.field4247 * 1115597881, 293773697 * var15.field4257, var47);
                              } else {
                                 classYW.method13733(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, var47, 256 - (var18 & 0xFF));
                              }
                        }
                     } else if (0 == var18) {
                        classYW.method13746(var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697, var47);
                     } else {
                        classYW.method13750(var16, var17, var15.field4247 * 1115597881, var15.field4257 * 293773697, var47, 256 - (var18 & 0xFF));
                     }
                  } else if (4 == var15.field4373 * -1510882375) {
                     classZV var46 = Widget.method7244(var15, var12, (byte)-76);
                     if (var46 != null) {
                        String var67 = var15.field4299;
                        int var57;
                        if (method5615(this, var15, (byte)-54)) {
                           var57 = 948260113 * var15.field4293;
                           if (classVV.method12187(var12, 277717963).method12002(var15, (byte)83) && var15.field4271 * 1396903265 != 0) {
                              var57 = var15.field4271 * 1396903265;
                           }

                           if (!var15.field4300.isEmpty()) {
                              var67 = var15.field4300;
                           }
                        } else {
                           var57 = 1740831551 * var15.field4268;
                           if (classVV.method12187(var12, 191497182).method12002(var15, (byte)89) && 0 != var15.field4270 * 318120869) {
                              var57 = 318120869 * var15.field4270;
                           }
                        }

                        if (var15.field4273 && var15.field4317 * 2073671441 != -1) {
                           ItemComposition var75 = classOB.method8329(2073671441 * var15.field4317, (byte)34);
                           var67 = var75.method8741(1248604557);
                           if (var67 == null) {
                              var67 = classKK.field3613;
                           }

                           if ((1633663665 * var75.field5053 == 1 || var15.field4342 * 1190987649 != 1) && -1 != var15.field4342 * 1190987649) {
                              var67 = classQY.method9885(16748608, (byte)49)
                                 + var67
                                 + classDO.field1592
                                 + " "
                                 + 'x'
                                 + classSX.method10756(var15.field4342 * 1190987649, -268294114);
                           }
                        }

                        if (var12.method12241(var15, (byte)-86)) {
                           var67 = classKK.field3781;
                           var57 = var15.field4268 * 1740831551;
                        }

                        if (!var15.field4273) {
                           var67 = this.method5609(var67, var15, 2036642790);
                        }

                        var46.method63(
                           var67,
                           var16,
                           var17,
                           var15.field4247 * 1115597881,
                           var15.field4257 * 293773697,
                           var57,
                           var15.field4304 ? 0 : -1,
                           classGK.method5514(var15.field4345 * 1517457381, -1312142721),
                           var15.field4302 * 1409141973,
                           -338086361 * var15.field4303,
                           704962289 * var15.field4301
                        );
                     }
                  } else if (-1510882375 * var15.field4373 == 5) {
                     if (!var15.field4273) {
                        SpritePixels var45 = var15.method7369(var12, method5615(this, var15, (byte)-72), this.field2632.vmethod78((byte)-67), -304707674);
                        if (null != var45) {
                           var45.method13555(var16, var17);
                        }
                     } else {
                        SpritePixels var44;
                        if (-1 != 2073671441 * var15.field4317) {
                           var44 = classLJ.method7157(
                              2073671441 * var15.field4317,
                              var15.field4342 * 1190987649,
                              var15.field4281 * -2011859635,
                              880555563 * var15.field4282,
                              -278245589 * var15.field4297,
                              false,
                              Math.max(36, var15.field4247 * 1115597881),
                              Math.max(32, 293773697 * var15.field4257),
                              1683831829
                           );
                        } else {
                           var44 = var15.method7369(var12, false, this.field2632.vmethod78((byte)29), -604027302);
                        }

                        if (null != var44) {
                           int var56 = var44.field7004;
                           int var66 = var44.field7006;
                           if (!var15.field4280) {
                              int var74 = -356282368 * var15.field4247 / var56;
                              if (var15.field4349 * 142218501 != 0) {
                                 classUY.method11780(
                                    var44,
                                    var16 + 1115597881 * var15.field4247 / 2,
                                    var17 + var15.field4257 * 293773697 / 2,
                                    var15.field4349 * 142218501,
                                    var74
                                 );
                              } else if (0 != var18) {
                                 SpritePixels.method13568(var44, var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, 256 - (var18 & 0xFF));
                              } else if (var56 == var15.field4247 * 1115597881 && 293773697 * var15.field4257 == var66) {
                                 var44.method13555(var16, var17);
                              } else {
                                 SpritePixels.method13558(var44, var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697);
                              }
                           } else {
                              classYW.method13713(var16, var17, var16 + var15.field4247 * 1115597881, var17 + 293773697 * var15.field4257);
                              int var73 = (1115597881 * var15.field4247 + (var56 - 1)) / var56;
                              int var81 = (var66 - 1 + var15.field4257 * 293773697) / var66;

                              for (int var84 = 0; var84 < var73; var84++) {
                                 for (int var87 = 0; var87 < var81; var87++) {
                                    if (0 != var15.field4349 * 142218501) {
                                       classUY.method11780(
                                          var44, var56 / 2 + var84 * var56 + var16, var66 * var87 + var17 + var66 / 2, 142218501 * var15.field4349, 4096
                                       );
                                    } else if (var18 != 0) {
                                       var44.method13565(var84 * var56 + var16, var17 + var87 * var66, 256 - (var18 & 0xFF));
                                    } else {
                                       var44.method13555(var84 * var56 + var16, var17 + var66 * var87);
                                    }
                                 }
                              }

                              classYW.method13710(var6, var7, var8, var9);
                           }
                        }
                     }
                  } else if (6 == -1510882375 * var15.field4373) {
                     Animation var43 = null;
                     int var55 = -1;
                     boolean var65 = method5615(this, var15, (byte)-67);
                     int var72;
                     if (var65) {
                        var72 = 821151237 * var15.field4291;
                     } else {
                        var72 = 2114568197 * var15.field4286;
                     }

                     if (-1 != var72) {
                        var43 = classQA.method9477(var72, -1327675644);
                        var55 = -814893963 * var15.field4319;
                     }

                     Model var79 = null;
                     int var83 = 0;
                     if (2073671441 * var15.field4317 != -1) {
                        var79 = this.field2636.vmethod115(var15, (byte)-50);
                        if (var79 != null) {
                           Model.method5254(var79);
                           var83 = var79.field1741 * -1256242689 / 2;
                        }
                     } else if (5 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod119(var15, (byte)4);
                     } else if (var15.field4331 * -910601 == 7) {
                        var79 = this.field2636.vmethod122(var15, (byte)62);
                     } else if (8 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod127(var15, var43, var55, (byte)14);
                     } else if (9 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod129(var15, var43, var55, (byte)43);
                     } else if (6 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod133(var15, var43, var55, var65, -837018650);
                     } else if (var15.field4331 * -910601 == 2) {
                        var79 = this.field2636.vmethod139(var15, var43, var55, var65, -1595185582);
                     } else {
                        var79 = this.field2636.vmethod140(var15, var43, var55, var65, (byte)21);
                     }

                     classFH.method4776(1115597881 * var15.field4247 / 2 + var16, 293773697 * var15.field4257 / 2 + var17);
                     int var86 = classFH.field2251[-1246332317 * var15.field4354] * var15.field4336 * 245118287 >> 16;
                     int var89 = var15.field4336 * 245118287 * classFH.field2252[var15.field4354 * -1246332317] >> 16;
                     if (null != var79) {
                        classFH.method4704(false);
                        if (!var15.field4273) {
                           var79.method5269(0, -1008620329 * var15.field4290, 0, var15.field4354 * -1246332317, 0, var86, var89);
                        } else {
                           Model.method5254(var79);
                           if (var15.field4295) {
                              Model.method5188(
                                 var79,
                                 0,
                                 var15.field4290 * -1008620329,
                                 827587461 * var15.field4292,
                                 var15.field4354 * -1246332317,
                                 var15.field4288 * -1469553671,
                                 var86 + var83 + var15.field4289 * -30521739,
                                 var15.field4289 * -30521739 + var89,
                                 var15.field4336 * 245118287
                              );
                           } else {
                              var79.method5269(
                                 0,
                                 -1008620329 * var15.field4290,
                                 827587461 * var15.field4292,
                                 -1246332317 * var15.field4354,
                                 var15.field4288 * -1469553671,
                                 var83 + var86 + -30521739 * var15.field4289,
                                 var89 + var15.field4289 * -30521739
                              );
                           }
                        }
                     }

                     classFH.method4774();
                  } else if (-1510882375 * var15.field4373 == 8) {
                     if (classVC.method12008(classVV.method12187(var12, 131338097), var15, (byte)93)) {
                        classZV var42 = this.field2635.vmethod105(classXJ.field6876, -725461430);
                        int var53 = 0;
                        int var63 = 0;
                        classZV var71 = var42;
                        String var27 = var15.field4299;

                        for (String var76 = this.method5609(var27, var15, 1988596906); !var76.isEmpty(); var63 += var71.field8 + 1) {
                           int var29 = var76.indexOf(classDO.field1586);
                           String var28;
                           if (-1 != var29) {
                              var28 = var76.substring(0, var29);
                              var76 = var76.substring(var29 + 4);
                           } else {
                              var28 = var76;
                              var76 = "";
                           }

                           int var30 = var71.method33(var28);
                           if (var30 > var53) {
                              var53 = var30;
                           }
                        }

                        var53 += 6;
                        var63 += 7;
                        int var85 = var16 + 1115597881 * var15.field4247 - 5 - var53;
                        int var88 = 5 + var15.field4257 * 293773697 + var17;
                        if (var85 < 5 + var16) {
                           var85 = 5 + var16;
                        }

                        if (var85 + var53 > var8) {
                           var85 = var8 - var53;
                        }

                        if (var88 + var63 > var9) {
                           var88 = var9 - var63;
                        }

                        classYW.method13737(var85, var88, var53, var63, 16777120);
                        classYW.method13746(var85, var88, var53, var63, 0);
                        var27 = var15.field4299;
                        int var31 = 2 + var88 + var71.field8;

                        for (String var78 = this.method5609(var27, var15, 1802283647); !var78.isEmpty(); var31 += 1 + var71.field8) {
                           int var32 = var78.indexOf(classDO.field1586);
                           String var82;
                           if (-1 != var32) {
                              var82 = var78.substring(0, var32);
                              var78 = var78.substring(4 + var32);
                           } else {
                              var82 = var78;
                              var78 = "";
                           }

                           var71.method51(var82, 3 + var85, var31, 0, -1);
                        }
                     }
                  } else if (9 == -1510882375 * var15.field4373) {
                     int var41;
                     int var52;
                     int var62;
                     int var70;
                     if (var15.field4276) {
                        var41 = var16;
                        var52 = var15.field4257 * 293773697 + var17;
                        var62 = 1115597881 * var15.field4247 + var16;
                        var70 = var17;
                     } else {
                        var41 = var16;
                        var52 = var17;
                        var62 = 1115597881 * var15.field4247 + var16;
                        var70 = var15.field4257 * 293773697 + var17;
                     }

                     if (var15.field4275 * 845712065 == 1) {
                        classYW.method13771(var41, var52, var62, var70, 1740831551 * var15.field4268);
                     } else {
                        classYW.method13784(var41, var52, var62, var70, 845712065 * var15.field4275, var15.field4268 * 1740831551);
                     }
                  } else if (-1510882375 * var15.field4373 == 12) {
                     classLH var40 = var15.method7334(-996162157);
                     classLI var51 = var15.method7337(565240322);
                     if (var40 != null && var51 != null && var40.method7043((byte)-29)) {
                        classZV var61 = Widget.method7244(var15, var12, (byte)11);
                        if (null != var61) {
                           this.field2638
                              .method5312(
                                 var16,
                                 var17,
                                 1115597881 * var15.field4247,
                                 293773697 * var15.field4257,
                                 classLH.method7045(var40, 1819415552),
                                 var40.method7049(1252681617),
                                 var40.method7038((byte)-7),
                                 var40.method7039(1509379795),
                                 var40.method7034((byte)3),
                                 395641138
                              );
                           int var69 = var15.field4304 ? var15.field4282 * 880555563 : -1;
                           if (!var40.method7027(1494528679) && classZD.method13889(var40.method7017(-243119125), -1062272653)) {
                              classGA.method5313(
                                 this.field2638, var51.field4167 * 201043577, var69, -211319977 * var51.field4170, var51.field4168 * 1532740787, 1181372109
                              );
                              classGA.method5318(this.field2638, var40.method7020(-549869436), var61, 120041229);
                           } else {
                              classGA.method5313(
                                 this.field2638, 1740831551 * var15.field4268, var69, var51.field4170 * -211319977, 1532740787 * var51.field4168, -121025944
                              );
                              classGA.method5318(this.field2638, var40.method7017(1667101354), var61, 120041229);
                           }

                           classYW.method13710(var6, var7, var8, var9);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;I)V")
   @ObfuscatedName("aw")
   void method5592(
      Widget[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, classVV var12, int var13
   ) {
      classYW.method13710(var6, var7, var8, var9);
      classFH.method4760();

      for (int var14 = var2; var14 <= var3; var14++) {
         Widget var15 = var1[var14];
         if (null != var15
            && (
               -1502647899 * var15.field4340 == var4 && var15.field4261 * 653784551 == var5
                  || var4 == -1412584499 && var15 == classVV.method12228(var12, (byte)-40)
            )
            && (!var15.field4273 || !var12.method12196(var15, (byte)0))) {
            if (-90794217 * var15.field4332 > 0) {
               this.method5597(var15, var13, (byte)122);
            }

            int var16 = var10 + 744286069 * var15.field4279;
            int var17 = var11 + 1570844829 * var15.field4255;
            int var18 = 1517457381 * var15.field4345;
            if (classVV.method12228(var12, (byte)-93) == var15) {
               classVW var19 = var12.method12226((byte)0);
               if (var4 != -1412584499 && !var15.field4312) {
                  this.field2649 = var1;
                  this.field2644 = -100057729 * var10;
                  this.field2643 = 5465987 * var11;
                  continue;
               }

               if (classVW.method12324(var19, (short)9906) && var19.method12329(-1772000595)) {
                  int var20 = var19.method12304(-839918489);
                  int var21 = var19.method12310((byte)-106);
                  int var22 = var19.method12284(-52026959).field4247 * 1115597881;
                  int var23 = var19.method12284(1111697437).field4257 * 293773697;
                  int var24 = classTZ.field6253 * -38255113;
                  int var25 = classTZ.field6262 * -2144333897;
                  var24 -= var19.method12292((byte)86);
                  var25 -= classVW.method12294(var19, (byte)-67);
                  if (var24 < var20) {
                     var24 = var20;
                  }

                  if (1115597881 * var15.field4247 + var24 > var22 + var20) {
                     var24 = var22 + var20 - 1115597881 * var15.field4247;
                  }

                  if (var25 < var21) {
                     var25 = var21;
                  }

                  if (var25 + var15.field4257 * 293773697 > var21 + var23) {
                     var25 = var21 + var23 - var15.field4257 * 293773697;
                  }

                  var16 = var24;
                  var17 = var25;
               }

               if (!var15.field4312) {
                  var18 = 128;
               }
            }

            int var33;
            int var34;
            int var35;
            int var36;
            if (9 == -1510882375 * var15.field4373) {
               int var37 = var16;
               int var49 = var17;
               int var59 = var16 + var15.field4247 * 1115597881;
               int var26 = var15.field4257 * 293773697 + var17;
               if (var59 < var16) {
                  var37 = var59;
                  var59 = var16;
               }

               if (var26 < var17) {
                  var49 = var26;
                  var26 = var17;
               }

               var59++;
               var26++;
               var33 = var37 > var6 ? var37 : var6;
               var34 = var49 > var7 ? var49 : var7;
               var35 = var59 < var8 ? var59 : var8;
               var36 = var26 < var9 ? var26 : var9;
            } else {
               int var38 = var15.field4247 * 1115597881 + var16;
               int var50 = var17 + var15.field4257 * 293773697;
               var33 = var16 > var6 ? var16 : var6;
               var34 = var17 > var7 ? var17 : var7;
               var35 = var38 < var8 ? var38 : var8;
               var36 = var50 < var9 ? var50 : var9;
            }

            if (!var15.field4273 || var33 < var35 && var34 < var36) {
               if (0 != -90794217 * var15.field4332) {
                  if (1336 == var15.field4332 * -90794217) {
                     this.method5602(var16, var17, 1115597881 * var15.field4247, 825915897);
                     continue;
                  }

                  if (1337 == var15.field4332 * -90794217) {
                     this.field2639.vmethod281(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, -12535752);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1338 == -90794217 * var15.field4332) {
                     this.field2639.vmethod298(var15, var16, var17, (short)-6786);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1339 == var15.field4332 * -90794217) {
                     this.method5600(var15, var16, var17, var12, (byte)56);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1400 == var15.field4332 * -90794217) {
                     this.field2639
                        .vmethod285(
                           var16,
                           var17,
                           var15.field4247 * 1115597881,
                           293773697 * var15.field4257,
                           var13,
                           ClientPreferences.method2755(this.field2632.vmethod89(797563684), -1674478907)
                        );
                  }

                  if (1401 == var15.field4332 * -90794217) {
                     this.field2639.vmethod291(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, 1554558740);
                  }

                  if (var15.field4332 * -90794217 == 1402) {
                     this.field2639.vmethod296(var16, var13, -1565153214);
                  }
               }

               if (0 == -1510882375 * var15.field4373) {
                  if (!var15.field4273 && var12.method12196(var15, (byte)0) && !classVV.method12187(var12, -405374057).method12002(var15, (byte)86)) {
                     continue;
                  }

                  if (!var15.field4273) {
                     if (var15.field4329 * -41424189 > var15.field4267 * -714521463 - 293773697 * var15.field4257) {
                        var15.field4329 = var15.field4267 * -1471306045 - var15.field4257 * 577918571;
                     }

                     if (var15.field4329 * -41424189 < 0) {
                        var15.field4329 = 0;
                     }
                  }

                  this.method5594(
                     var1,
                     var15.field4263 * -1210177335,
                     var15.field4264 * 379541057,
                     -1163656551 * var15.field4327,
                     var15.field4258 * 63131743,
                     var33,
                     var34,
                     var35,
                     var36,
                     var16 - var15.field4339 * -34809441,
                     var17 - var15.field4329 * -41424189,
                     var12,
                     var13,
                     1690058415
                  );
                  if (var15.field4338 != null) {
                     this.method5594(
                        var15.field4338,
                        0,
                        var15.field4338.length - 1,
                        -1163656551 * var15.field4327,
                        -1,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - var15.field4339 * -34809441,
                        var17 - -41424189 * var15.field4329,
                        var12,
                        var13,
                        1690058415
                     );
                  }

                  if (-1 == var15.field4258 * 63131743) {
                     WidgetNode var39 = (WidgetNode)HashTable.method13432(var12.field6611, var15.field4327 * -1163656551);
                     if (null != var39) {
                        this.method5587(var39.field5275 * 2023647411, var33, var34, var35, var36, var16, var17, var12, var13, 545645039);
                     }
                  }

                  classYW.method13710(var6, var7, var8, var9);
                  classFH.method4760();
               } else if (11 == -1510882375 * var15.field4373) {
                  if (var12.method12196(var15, (byte)0) && !classVV.method12187(var12, -314599428).method12002(var15, (byte)57)) {
                     continue;
                  }

                  if (null != var15.field4338) {
                     this.method5594(
                        var15.field4338,
                        0,
                        var15.field4338.length - 1,
                        var15.field4327 * -1163656551,
                        -1,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - -34809441 * var15.field4339,
                        var17 - var15.field4329 * -41424189,
                        var12,
                        var13,
                        1690058415
                     );
                  }

                  classYW.method13710(var6, var7, var8, var9);
                  classFH.method4760();
               }

               if (var15.field4373 * -1510882375 == 0 && !var15.field4273 && var15.field4267 * -714521463 > 293773697 * var15.field4257) {
                  method5606(
                     this,
                     var16 + 1115597881 * var15.field4247,
                     var17,
                     -41424189 * var15.field4329,
                     293773697 * var15.field4257,
                     -714521463 * var15.field4267,
                     (byte)-1
                  );
               }

               if (var15.field4373 * -1510882375 != 1) {
                  if (-1510882375 * var15.field4373 == 3) {
                     int var47;
                     if (method5615(this, var15, (byte)15)) {
                        var47 = var15.field4293 * 948260113;
                        if (classVV.method12187(var12, 1280247474).method12002(var15, (byte)52) && 0 != var15.field4271 * 1396903265) {
                           var47 = var15.field4271 * 1396903265;
                        }
                     } else {
                        var47 = 1740831551 * var15.field4268;
                        if (classVV.method12187(var12, 1920763831).method12002(var15, (byte)63) && 318120869 * var15.field4270 != 0) {
                           var47 = 318120869 * var15.field4270;
                        }
                     }

                     if (var15.field4269) {
                        switch (1768204099 * var15.field4305.field6974) {
                           case 1:
                              classYW.method13738(
                                 var16,
                                 var17,
                                 1115597881 * var15.field4247,
                                 293773697 * var15.field4257,
                                 1740831551 * var15.field4268,
                                 var15.field4293 * 948260113
                              );
                              break;
                           case 2:
                              classYW.method13804(
                                 var16,
                                 var17,
                                 var15.field4247 * 1115597881,
                                 293773697 * var15.field4257,
                                 var15.field4268 * 1740831551,
                                 var15.field4293 * 948260113,
                                 255 - (var15.field4345 * 1517457381 & 0xFF),
                                 255 - (var15.field4274 * 1991172799 & 0xFF)
                              );
                              break;
                           default:
                              if (var18 == 0) {
                                 classYW.method13737(var16, var17, var15.field4247 * 1115597881, 293773697 * var15.field4257, var47);
                              } else {
                                 classYW.method13733(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, var47, 256 - (var18 & 0xFF));
                              }
                        }
                     } else if (0 == var18) {
                        classYW.method13746(var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697, var47);
                     } else {
                        classYW.method13750(var16, var17, var15.field4247 * 1115597881, var15.field4257 * 293773697, var47, 256 - (var18 & 0xFF));
                     }
                  } else if (4 == var15.field4373 * -1510882375) {
                     classZV var46 = Widget.method7244(var15, var12, (byte)-2);
                     if (var46 != null) {
                        String var67 = var15.field4299;
                        int var57;
                        if (method5615(this, var15, (byte)-43)) {
                           var57 = 948260113 * var15.field4293;
                           if (classVV.method12187(var12, -251751247).method12002(var15, (byte)70) && var15.field4271 * 1396903265 != 0) {
                              var57 = var15.field4271 * 1396903265;
                           }

                           if (!var15.field4300.isEmpty()) {
                              var67 = var15.field4300;
                           }
                        } else {
                           var57 = 1740831551 * var15.field4268;
                           if (classVV.method12187(var12, 332890184).method12002(var15, (byte)30) && 0 != var15.field4270 * 318120869) {
                              var57 = 318120869 * var15.field4270;
                           }
                        }

                        if (var15.field4273 && var15.field4317 * 2073671441 != -1) {
                           ItemComposition var75 = classOB.method8329(2073671441 * var15.field4317, (byte)35);
                           var67 = var75.method8741(1248604557);
                           if (var67 == null) {
                              var67 = classKK.field3613;
                           }

                           if ((1633663665 * var75.field5053 == 1 || var15.field4342 * 1190987649 != 1) && -1 != var15.field4342 * 1190987649) {
                              var67 = classQY.method9885(16748608, (byte)80)
                                 + var67
                                 + classDO.field1592
                                 + " "
                                 + 'x'
                                 + classSX.method10756(var15.field4342 * 1190987649, -1052888622);
                           }
                        }

                        if (var12.method12241(var15, (byte)-109)) {
                           var67 = classKK.field3781;
                           var57 = var15.field4268 * 1740831551;
                        }

                        if (!var15.field4273) {
                           var67 = this.method5609(var67, var15, 1942349116);
                        }

                        var46.method63(
                           var67,
                           var16,
                           var17,
                           var15.field4247 * 1115597881,
                           var15.field4257 * 293773697,
                           var57,
                           var15.field4304 ? 0 : -1,
                           classGK.method5514(var15.field4345 * 1517457381, -1825471066),
                           var15.field4302 * 1409141973,
                           -338086361 * var15.field4303,
                           704962289 * var15.field4301
                        );
                     }
                  } else if (-1510882375 * var15.field4373 == 5) {
                     if (!var15.field4273) {
                        SpritePixels var45 = var15.method7369(var12, method5615(this, var15, (byte)-6), this.field2632.vmethod78((byte)49), -1694061909);
                        if (null != var45) {
                           var45.method13555(var16, var17);
                        }
                     } else {
                        SpritePixels var44;
                        if (-1 != 2073671441 * var15.field4317) {
                           var44 = classLJ.method7157(
                              2073671441 * var15.field4317,
                              var15.field4342 * 1190987649,
                              var15.field4281 * -2011859635,
                              880555563 * var15.field4282,
                              -278245589 * var15.field4297,
                              false,
                              Math.max(36, var15.field4247 * 1115597881),
                              Math.max(32, 293773697 * var15.field4257),
                              1683831829
                           );
                        } else {
                           var44 = var15.method7369(var12, false, this.field2632.vmethod78((byte)-65), -1625225312);
                        }

                        if (null != var44) {
                           int var56 = var44.field7004;
                           int var66 = var44.field7006;
                           if (!var15.field4280) {
                              int var74 = -356282368 * var15.field4247 / var56;
                              if (var15.field4349 * 142218501 != 0) {
                                 classUY.method11780(
                                    var44,
                                    var16 + 1115597881 * var15.field4247 / 2,
                                    var17 + var15.field4257 * 293773697 / 2,
                                    var15.field4349 * 142218501,
                                    var74
                                 );
                              } else if (0 != var18) {
                                 SpritePixels.method13568(var44, var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, 256 - (var18 & 0xFF));
                              } else if (var56 == var15.field4247 * 1115597881 && 293773697 * var15.field4257 == var66) {
                                 var44.method13555(var16, var17);
                              } else {
                                 SpritePixels.method13558(var44, var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697);
                              }
                           } else {
                              classYW.method13713(var16, var17, var16 + var15.field4247 * 1115597881, var17 + 293773697 * var15.field4257);
                              int var73 = (1115597881 * var15.field4247 + (var56 - 1)) / var56;
                              int var81 = (var66 - 1 + var15.field4257 * 293773697) / var66;

                              for (int var84 = 0; var84 < var73; var84++) {
                                 for (int var87 = 0; var87 < var81; var87++) {
                                    if (0 != var15.field4349 * 142218501) {
                                       classUY.method11780(
                                          var44, var56 / 2 + var84 * var56 + var16, var66 * var87 + var17 + var66 / 2, 142218501 * var15.field4349, 4096
                                       );
                                    } else if (var18 != 0) {
                                       var44.method13565(var84 * var56 + var16, var17 + var87 * var66, 256 - (var18 & 0xFF));
                                    } else {
                                       var44.method13555(var84 * var56 + var16, var17 + var66 * var87);
                                    }
                                 }
                              }

                              classYW.method13710(var6, var7, var8, var9);
                           }
                        }
                     }
                  } else if (6 == -1510882375 * var15.field4373) {
                     Animation var43 = null;
                     int var55 = -1;
                     boolean var65 = method5615(this, var15, (byte)58);
                     int var72;
                     if (var65) {
                        var72 = 821151237 * var15.field4291;
                     } else {
                        var72 = 2114568197 * var15.field4286;
                     }

                     if (-1 != var72) {
                        var43 = classQA.method9477(var72, 1783553366);
                        var55 = -814893963 * var15.field4319;
                     }

                     Model var79 = null;
                     int var83 = 0;
                     if (2073671441 * var15.field4317 != -1) {
                        var79 = this.field2636.vmethod115(var15, (byte)-12);
                        if (var79 != null) {
                           Model.method5254(var79);
                           var83 = var79.field1741 * -1256242689 / 2;
                        }
                     } else if (5 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod119(var15, (byte)4);
                     } else if (var15.field4331 * -910601 == 7) {
                        var79 = this.field2636.vmethod122(var15, (byte)115);
                     } else if (8 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod127(var15, var43, var55, (byte)14);
                     } else if (9 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod129(var15, var43, var55, (byte)43);
                     } else if (6 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod133(var15, var43, var55, var65, 1212958472);
                     } else if (var15.field4331 * -910601 == 2) {
                        var79 = this.field2636.vmethod139(var15, var43, var55, var65, -1886850572);
                     } else {
                        var79 = this.field2636.vmethod140(var15, var43, var55, var65, (byte)2);
                     }

                     classFH.method4776(1115597881 * var15.field4247 / 2 + var16, 293773697 * var15.field4257 / 2 + var17);
                     int var86 = classFH.field2251[-1246332317 * var15.field4354] * var15.field4336 * 245118287 >> 16;
                     int var89 = var15.field4336 * 245118287 * classFH.field2252[var15.field4354 * -1246332317] >> 16;
                     if (null != var79) {
                        classFH.method4704(false);
                        if (!var15.field4273) {
                           var79.method5269(0, -1008620329 * var15.field4290, 0, var15.field4354 * -1246332317, 0, var86, var89);
                        } else {
                           Model.method5254(var79);
                           if (var15.field4295) {
                              Model.method5188(
                                 var79,
                                 0,
                                 var15.field4290 * -1008620329,
                                 827587461 * var15.field4292,
                                 var15.field4354 * -1246332317,
                                 var15.field4288 * -1469553671,
                                 var86 + var83 + var15.field4289 * -30521739,
                                 var15.field4289 * -30521739 + var89,
                                 var15.field4336 * 245118287
                              );
                           } else {
                              var79.method5269(
                                 0,
                                 -1008620329 * var15.field4290,
                                 827587461 * var15.field4292,
                                 -1246332317 * var15.field4354,
                                 var15.field4288 * -1469553671,
                                 var83 + var86 + -30521739 * var15.field4289,
                                 var89 + var15.field4289 * -30521739
                              );
                           }
                        }
                     }

                     classFH.method4774();
                  } else if (-1510882375 * var15.field4373 == 8) {
                     if (classVC.method12008(classVV.method12187(var12, 2106647350), var15, (byte)28)) {
                        classZV var42 = this.field2635.vmethod105(classXJ.field6876, -725461430);
                        int var53 = 0;
                        int var63 = 0;
                        classZV var71 = var42;
                        String var27 = var15.field4299;

                        for (String var76 = this.method5609(var27, var15, 1885462901); !var76.isEmpty(); var63 += var71.field8 + 1) {
                           int var29 = var76.indexOf(classDO.field1586);
                           String var28;
                           if (-1 != var29) {
                              var28 = var76.substring(0, var29);
                              var76 = var76.substring(var29 + 4);
                           } else {
                              var28 = var76;
                              var76 = "";
                           }

                           int var30 = var71.method33(var28);
                           if (var30 > var53) {
                              var53 = var30;
                           }
                        }

                        var53 += 6;
                        var63 += 7;
                        int var85 = var16 + 1115597881 * var15.field4247 - 5 - var53;
                        int var88 = 5 + var15.field4257 * 293773697 + var17;
                        if (var85 < 5 + var16) {
                           var85 = 5 + var16;
                        }

                        if (var85 + var53 > var8) {
                           var85 = var8 - var53;
                        }

                        if (var88 + var63 > var9) {
                           var88 = var9 - var63;
                        }

                        classYW.method13737(var85, var88, var53, var63, 16777120);
                        classYW.method13746(var85, var88, var53, var63, 0);
                        var27 = var15.field4299;
                        int var31 = 2 + var88 + var71.field8;

                        for (String var78 = this.method5609(var27, var15, 2065494022); !var78.isEmpty(); var31 += 1 + var71.field8) {
                           int var32 = var78.indexOf(classDO.field1586);
                           String var82;
                           if (-1 != var32) {
                              var82 = var78.substring(0, var32);
                              var78 = var78.substring(4 + var32);
                           } else {
                              var82 = var78;
                              var78 = "";
                           }

                           var71.method51(var82, 3 + var85, var31, 0, -1);
                        }
                     }
                  } else if (9 == -1510882375 * var15.field4373) {
                     int var41;
                     int var52;
                     int var62;
                     int var70;
                     if (var15.field4276) {
                        var41 = var16;
                        var52 = var15.field4257 * 293773697 + var17;
                        var62 = 1115597881 * var15.field4247 + var16;
                        var70 = var17;
                     } else {
                        var41 = var16;
                        var52 = var17;
                        var62 = 1115597881 * var15.field4247 + var16;
                        var70 = var15.field4257 * 293773697 + var17;
                     }

                     if (var15.field4275 * 845712065 == 1) {
                        classYW.method13771(var41, var52, var62, var70, 1740831551 * var15.field4268);
                     } else {
                        classYW.method13784(var41, var52, var62, var70, 845712065 * var15.field4275, var15.field4268 * 1740831551);
                     }
                  } else if (-1510882375 * var15.field4373 == 12) {
                     classLH var40 = var15.method7334(-1671051614);
                     classLI var51 = var15.method7337(1825762370);
                     if (var40 != null && var51 != null && var40.method7043((byte)-117)) {
                        classZV var61 = Widget.method7244(var15, var12, (byte)-96);
                        if (null != var61) {
                           this.field2638
                              .method5312(
                                 var16,
                                 var17,
                                 1115597881 * var15.field4247,
                                 293773697 * var15.field4257,
                                 classLH.method7045(var40, 1684905505),
                                 var40.method7049(658593938),
                                 var40.method7038((byte)-96),
                                 var40.method7039(1219239234),
                                 var40.method7034((byte)3),
                                 395641138
                              );
                           int var69 = var15.field4304 ? var15.field4282 * 880555563 : -1;
                           if (!var40.method7027(1267044611) && classZD.method13889(var40.method7017(-219773290), 735880602)) {
                              classGA.method5313(
                                 this.field2638, var51.field4167 * 201043577, var69, -211319977 * var51.field4170, var51.field4168 * 1532740787, 1564115800
                              );
                              classGA.method5318(this.field2638, var40.method7020(-635239825), var61, 120041229);
                           } else {
                              classGA.method5313(
                                 this.field2638, 1740831551 * var15.field4268, var69, var51.field4170 * -211319977, 1532740787 * var51.field4168, 1421514085
                              );
                              classGA.method5318(this.field2638, var40.method7017(-1861932607), var61, 120041229);
                           }

                           classYW.method13710(var6, var7, var8, var9);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("ak")
   void method5598(Widget var1, int var2) {
      int var3 = var1.field4332 * -1208938619;
      if (var3 == 440463794) {
         if (1542917833 * this.field2633 == -1) {
            this.field2633 = var1.field4346 * -794511402;
            this.field2634 = var1.field4347 * 1178689048;
         }

         if (this.field2637.vmethod178(-1548549706) == 1) {
            var1.field4346 = -908744055 * this.field2633;
         } else {
            var1.field4346 = this.field2634 * -1144797714;
         }
      } else if (var3 == 1998739821) {
         if (-1 == 1542917833 * this.field2633) {
            this.field2633 = 1013652457 * var1.field4346;
            this.field2634 = 261153745 * var1.field4347;
         }

         if (this.field2637.vmethod178(-990270765) == 1) {
            var1.field4346 = 575002083 * this.field2634;
         } else {
            var1.field4346 = 165903446 * this.field2633;
         }
      } else if (var3 == 327) {
         var1.field4354 = 2146444786;
         var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 2047) * 993003239;
         var1.field4331 = -1013325341;
         var1.field4372 = 0;
      } else if (var3 == 1932681869) {
         var1.field4354 = 2146444786;
         var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 58902721) * 993003239;
         var1.field4331 = -1190268717;
         var1.field4372 = -148119952;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bn")
   void method5604(int var1, int var2, int var3) {
      if (this.field2632.vmethod89(797563684).method2738(-207714472)) {
         var2 += 15;
         classVP.field6548.method57("Fps:" + this.field2637.vmethod172(868104458), var3 + var1, var2, 16776960, -1);
         var2 += 15;
         Runtime var4 = Runtime.getRuntime();
         int var5 = (int)((var4.totalMemory() - var4.freeMemory()) / 1024L);
         int var6 = -1774382915;
         if (var5 > -1192065500 && !this.field2637.vmethod177(2111760693)) {
            var6 = -1133107897;
         }

         classVP.field6548.method57("Mem:" + var5 + "k", var1 + var3, var2, var6, -1);
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIILvv;I)V")
   @ObfuscatedName("ba")
   void method5622(Widget[] var1, int var2, int var3, int var4, int var5, classVV var6, int var7) {
      for (int var8 = var2; var8 <= var3; var8++) {
         Widget var9 = var1[var8];
         if (null != var9
            && var4 == var9.field4340 * -1502647899
            && 653784551 * var9.field4261 == var5
            && (!var9.field4273 || !var6.method12196(var9, (byte)0))) {
            if (var9.field4373 * -1510882375 == 0) {
               if (!var9.field4273 && var6.method12196(var9, (byte)0) && !classVV.method12187(var6, 1182732135).method12002(var9, (byte)113)) {
                  continue;
               }

               this.method5621(
                  var1,
                  var9.field4263 * -1210177335,
                  var9.field4264 * 379541057,
                  var9.field4327 * -1163656551,
                  63131743 * var9.field4258,
                  var6,
                  var7,
                  (byte)-37
               );
               if (var9.field4338 != null) {
                  this.method5621(var9.field4338, 0, var9.field4338.length - 1, var9.field4327 * -1163656551, -1, var6, var7, (byte)-37);
               }

               if (var9.field4258 * 63131743 == -1) {
                  WidgetNode var10 = (WidgetNode)HashTable.method13432(var6.field6611, -1163656551 * var9.field4327);
                  if (null != var10) {
                     this.method5617(var10.field5275 * 2023647411, var6, var7, (byte)1);
                  }
               }
            }

            if (6 == -1510882375 * var9.field4373) {
               if (var9.field4286 * 2114568197 != -1 || 821151237 * var9.field4291 != -1) {
                  boolean var14 = method5615(this, var9, (byte)44);
                  int var11;
                  if (var14) {
                     var11 = var9.field4291 * 821151237;
                  } else {
                     var11 = 2114568197 * var9.field4286;
                  }

                  if (-1 != var11) {
                     Animation var12 = classQA.method9477(var11, 1873479033);
                     if (!Animation.method8805(var12, (byte)0)) {
                        var9.field4325 += 650277867 * var7;

                        while (var9.field4325 * 916200643 > var12.field5123[-814893963 * var9.field4319]) {
                           var9.field4325 = var9.field4325 - 650277867 * var12.field5123[-814893963 * var9.field4319];
                           var9.field4319 += 1734048733;
                           if (-814893963 * var9.field4319 >= var12.field5125.length) {
                              var9.field4319 = var9.field4319 - var12.field5132 * -696471245;
                              if (-814893963 * var9.field4319 < 0 || -814893963 * var9.field4319 >= var12.field5125.length) {
                                 var9.field4319 = 0;
                              }
                           }
                        }
                     } else {
                        var9.field4319 += 1734048733 * var7;
                        int var13 = var12.method8809(1963319995);
                        if (-814893963 * var9.field4319 >= var13) {
                           var9.field4319 = var9.field4319 - var12.field5132 * -696471245;
                           if (var9.field4319 * -814893963 < 0 || -814893963 * var9.field4319 >= var13) {
                              var9.field4319 = 0;
                           }
                        }
                     }
                  }
               }

               if (var9.field4260 * 759310449 != 0 && !var9.field4273) {
                  int var15 = 759310449 * var9.field4260 >> 16;
                  int var17 = 759310449 * var9.field4260 << 16 >> 16;
                  var15 *= var7;
                  var17 *= var7;
                  var9.field4354 = (-1246332317 * var9.field4354 + var15 & 2047) * 1703663435;
                  var9.field4290 = (var17 + -1008620329 * var9.field4290 & 2047) * 993003239;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;I)V")
   @ObfuscatedName("ai")
   void method5593(
      Widget[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, classVV var12, int var13
   ) {
      classYW.method13710(var6, var7, var8, var9);
      classFH.method4760();

      for (int var14 = var2; var14 <= var3; var14++) {
         Widget var15 = var1[var14];
         if (null != var15
            && (
               -1502647899 * var15.field4340 == var4 && var15.field4261 * 653784551 == var5
                  || var4 == 578975898 && var15 == classVV.method12228(var12, (byte)-79)
            )
            && (!var15.field4273 || !var12.method12196(var15, (byte)0))) {
            if (704945840 * var15.field4332 > 0) {
               this.method5597(var15, var13, (byte)122);
            }

            int var16 = var10 + 744286069 * var15.field4279;
            int var17 = var11 + 1570844829 * var15.field4255;
            int var18 = -1887796431 * var15.field4345;
            if (classVV.method12228(var12, (byte)-100) == var15) {
               classVW var19 = var12.method12226((byte)0);
               if (var4 != -1412584499 && !var15.field4312) {
                  this.field2649 = var1;
                  this.field2644 = -100057729 * var10;
                  this.field2643 = -1327834564 * var11;
                  continue;
               }

               if (classVW.method12324(var19, (short)1981) && var19.method12329(-1996589236)) {
                  int var20 = var19.method12304(-842815482);
                  int var21 = var19.method12310((byte)-16);
                  int var22 = var19.method12284(-1012748042).field4247 * -213997419;
                  int var23 = var19.method12284(-1504140299).field4257 * 293773697;
                  int var24 = classTZ.field6253 * -38255113;
                  int var25 = classTZ.field6262 * -2044969658;
                  var24 -= var19.method12292((byte)35);
                  var25 -= classVW.method12294(var19, (byte)-84);
                  if (var24 < var20) {
                     var24 = var20;
                  }

                  if (707519696 * var15.field4247 + var24 > var22 + var20) {
                     var24 = var22 + var20 - 1115597881 * var15.field4247;
                  }

                  if (var25 < var21) {
                     var25 = var21;
                  }

                  if (var25 + var15.field4257 * -300667092 > var21 + var23) {
                     var25 = var21 + var23 - var15.field4257 * 293773697;
                  }

                  var16 = var24;
                  var17 = var25;
               }

               if (!var15.field4312) {
                  var18 = 1885175640;
               }
            }

            int var33;
            int var34;
            int var35;
            int var36;
            if (9 == -1510882375 * var15.field4373) {
               int var37 = var16;
               int var49 = var17;
               int var59 = var16 + var15.field4247 * -1652021331;
               int var26 = var15.field4257 * -1040669975 + var17;
               if (var59 < var16) {
                  var37 = var59;
                  var59 = var16;
               }

               if (var26 < var17) {
                  var49 = var26;
                  var26 = var17;
               }

               var59++;
               var26++;
               var33 = var37 > var6 ? var37 : var6;
               var34 = var49 > var7 ? var49 : var7;
               var35 = var59 < var8 ? var59 : var8;
               var36 = var26 < var9 ? var26 : var9;
            } else {
               int var38 = var15.field4247 * 1986756906 + var16;
               int var50 = var17 + var15.field4257 * 293773697;
               var33 = var16 > var6 ? var16 : var6;
               var34 = var17 > var7 ? var17 : var7;
               var35 = var38 < var8 ? var38 : var8;
               var36 = var50 < var9 ? var50 : var9;
            }

            if (!var15.field4273 || var33 < var35 && var34 < var36) {
               if (0 != -90794217 * var15.field4332) {
                  if (1336 == var15.field4332 * -331159416) {
                     this.method5602(var16, var17, 1115597881 * var15.field4247, 825915897);
                     continue;
                  }

                  if (1683099239 == var15.field4332 * 588338033) {
                     this.field2639.vmethod281(var16, var17, 538289636 * var15.field4247, 624085268 * var15.field4257, -1965048335);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1338 == -90794217 * var15.field4332) {
                     this.field2639.vmethod298(var15, var16, var17, (short)-17918);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (348618723 == var15.field4332 * -1406534993) {
                     this.method5600(var15, var16, var17, var12, (byte)37);
                     classYW.method13710(var6, var7, var8, var9);
                     continue;
                  }

                  if (1400 == var15.field4332 * -90794217) {
                     this.field2639
                        .vmethod285(
                           var16,
                           var17,
                           var15.field4247 * 1115597881,
                           728799626 * var15.field4257,
                           var13,
                           ClientPreferences.method2755(this.field2632.vmethod89(797563684), -1674478907)
                        );
                  }

                  if (1801875046 == var15.field4332 * -90794217) {
                     this.field2639.vmethod291(var16, var17, 531716514 * var15.field4247, -1484066938 * var15.field4257, -220068847);
                  }

                  if (var15.field4332 * -1862129840 == 1112583630) {
                     this.field2639.vmethod296(var16, var13, -1801362181);
                  }
               }

               if (0 == 794003742 * var15.field4373) {
                  if (!var15.field4273 && var12.method12196(var15, (byte)0) && !classVV.method12187(var12, 1222613233).method12002(var15, (byte)44)) {
                     continue;
                  }

                  if (!var15.field4273) {
                     if (var15.field4329 * -41424189 > var15.field4267 * -714521463 - 13368137 * var15.field4257) {
                        var15.field4329 = var15.field4267 * 319473860 - var15.field4257 * 472401220;
                     }

                     if (var15.field4329 * -41424189 < 0) {
                        var15.field4329 = 0;
                     }
                  }

                  this.method5594(
                     var1,
                     var15.field4263 * 1801198622,
                     var15.field4264 * 379541057,
                     -1163656551 * var15.field4327,
                     var15.field4258 * 63131743,
                     var33,
                     var34,
                     var35,
                     var36,
                     var16 - var15.field4339 * 1602984040,
                     var17 - var15.field4329 * 221836320,
                     var12,
                     var13,
                     1690058415
                  );
                  if (var15.field4338 != null) {
                     this.method5594(
                        var15.field4338,
                        0,
                        var15.field4338.length - 1,
                        -1162162653 * var15.field4327,
                        -1,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - var15.field4339 * -545055835,
                        var17 - 1189725210 * var15.field4329,
                        var12,
                        var13,
                        1690058415
                     );
                  }

                  if (-1 == var15.field4258 * 477675055) {
                     WidgetNode var39 = (WidgetNode)HashTable.method13432(var12.field6611, var15.field4327 * -1288939967);
                     if (null != var39) {
                        this.method5587(var39.field5275 * -676082177, var33, var34, var35, var36, var16, var17, var12, var13, 545645039);
                     }
                  }

                  classYW.method13710(var6, var7, var8, var9);
                  classFH.method4760();
               } else if (11 == -1510882375 * var15.field4373) {
                  if (var12.method12196(var15, (byte)0) && !classVV.method12187(var12, 473080514).method12002(var15, (byte)71)) {
                     continue;
                  }

                  if (null != var15.field4338) {
                     this.method5594(
                        var15.field4338,
                        0,
                        var15.field4338.length - 1,
                        var15.field4327 * -1163656551,
                        -1,
                        var33,
                        var34,
                        var35,
                        var36,
                        var16 - -34809441 * var15.field4339,
                        var17 - var15.field4329 * 2098984090,
                        var12,
                        var13,
                        1690058415
                     );
                  }

                  classYW.method13710(var6, var7, var8, var9);
                  classFH.method4760();
               }

               if (var15.field4373 * -1362909363 == 0 && !var15.field4273 && var15.field4267 * -714521463 > 1527430434 * var15.field4257) {
                  method5606(
                     this,
                     var16 + 379840328 * var15.field4247,
                     var17,
                     -41424189 * var15.field4329,
                     293773697 * var15.field4257,
                     -228592617 * var15.field4267,
                     (byte)-1
                  );
               }

               if (var15.field4373 * 487390321 != 1) {
                  if (1961620684 * var15.field4373 == 3) {
                     int var47;
                     if (method5615(this, var15, (byte)29)) {
                        var47 = var15.field4293 * 948260113;
                        if (classVV.method12187(var12, 1912509518).method12002(var15, (byte)74) && 0 != var15.field4271 * 1396903265) {
                           var47 = var15.field4271 * 1530320220;
                        }
                     } else {
                        var47 = 1740831551 * var15.field4268;
                        if (classVV.method12187(var12, -1487956).method12002(var15, (byte)36) && 641374494 * var15.field4270 != 0) {
                           var47 = 318120869 * var15.field4270;
                        }
                     }

                     if (var15.field4269) {
                        switch (-944411224 * var15.field4305.field6974) {
                           case 1:
                              classYW.method13738(
                                 var16,
                                 var17,
                                 -736875774 * var15.field4247,
                                 293773697 * var15.field4257,
                                 1740831551 * var15.field4268,
                                 var15.field4293 * 948260113
                              );
                              break;
                           case 2:
                              classYW.method13804(
                                 var16,
                                 var17,
                                 var15.field4247 * 1115597881,
                                 293773697 * var15.field4257,
                                 var15.field4268 * 1740831551,
                                 var15.field4293 * 1161295665,
                                 255 - (var15.field4345 * -94236783 & 0xFF),
                                 1095791635 - (var15.field4274 * 1991172799 & -128112223)
                              );
                              break;
                           default:
                              if (var18 == 0) {
                                 classYW.method13737(var16, var17, var15.field4247 * 1115597881, 293773697 * var15.field4257, var47);
                              } else {
                                 classYW.method13733(var16, var17, 1115597881 * var15.field4247, 293773697 * var15.field4257, var47, 256 - (var18 & 0xFF));
                              }
                        }
                     } else if (0 == var18) {
                        classYW.method13746(var16, var17, 1980955564 * var15.field4247, var15.field4257 * -2058024786, var47);
                     } else {
                        classYW.method13750(var16, var17, var15.field4247 * 1115597881, var15.field4257 * 1310046682, var47, 29012165 - (var18 & 0xFF));
                     }
                  } else if (4 == var15.field4373 * -1510882375) {
                     classZV var46 = Widget.method7244(var15, var12, (byte)-22);
                     if (var46 != null) {
                        String var67 = var15.field4299;
                        int var57;
                        if (method5615(this, var15, (byte)13)) {
                           var57 = 948260113 * var15.field4293;
                           if (classVV.method12187(var12, 236380357).method12002(var15, (byte)73) && var15.field4271 * 1396903265 != 0) {
                              var57 = var15.field4271 * 1396903265;
                           }

                           if (!var15.field4300.isEmpty()) {
                              var67 = var15.field4300;
                           }
                        } else {
                           var57 = 1740831551 * var15.field4268;
                           if (classVV.method12187(var12, -403333244).method12002(var15, (byte)27) && 0 != var15.field4270 * 318120869) {
                              var57 = -1503241307 * var15.field4270;
                           }
                        }

                        if (var15.field4273 && var15.field4317 * 2073671441 != -1) {
                           ItemComposition var75 = classOB.method8329(2073671441 * var15.field4317, (byte)55);
                           var67 = var75.method8741(1248604557);
                           if (var67 == null) {
                              var67 = classKK.field3613;
                           }

                           if ((5340012 * var75.field5053 == 1 || var15.field4342 * 1190987649 != 1) && -1 != var15.field4342 * 818622209) {
                              var67 = classQY.method9885(16748608, (byte)24)
                                 + var67
                                 + classDO.field1592
                                 + " "
                                 + 'x'
                                 + classSX.method10756(var15.field4342 * 1190987649, 1055927637);
                           }
                        }

                        if (var12.method12241(var15, (byte)-113)) {
                           var67 = classKK.field3781;
                           var57 = var15.field4268 * 1740831551;
                        }

                        if (!var15.field4273) {
                           var67 = this.method5609(var67, var15, 1888031382);
                        }

                        var46.method63(
                           var67,
                           var16,
                           var17,
                           var15.field4247 * 1115597881,
                           var15.field4257 * 712874227,
                           var57,
                           var15.field4304 ? 0 : -1,
                           classGK.method5514(var15.field4345 * 1517457381, -1900528652),
                           var15.field4302 * 1409141973,
                           -338086361 * var15.field4303,
                           917945065 * var15.field4301
                        );
                     }
                  } else if (-1510882375 * var15.field4373 == 5) {
                     if (!var15.field4273) {
                        SpritePixels var45 = var15.method7369(var12, method5615(this, var15, (byte)18), this.field2632.vmethod78((byte)-33), -704709251);
                        if (null != var45) {
                           var45.method13555(var16, var17);
                        }
                     } else {
                        SpritePixels var44;
                        if (-1 != 188916281 * var15.field4317) {
                           var44 = classLJ.method7157(
                              2073671441 * var15.field4317,
                              var15.field4342 * 1190987649,
                              var15.field4281 * -2011859635,
                              -1011829132 * var15.field4282,
                              -278245589 * var15.field4297,
                              false,
                              Math.max(36, var15.field4247 * 2135811768),
                              Math.max(32, -1342646210 * var15.field4257),
                              1683831829
                           );
                        } else {
                           var44 = var15.method7369(var12, false, this.field2632.vmethod78((byte)8), 1510190564);
                        }

                        if (null != var44) {
                           int var56 = var44.field7004;
                           int var66 = var44.field7006;
                           if (!var15.field4280) {
                              int var74 = -356282368 * var15.field4247 / var56;
                              if (var15.field4349 * 423402571 != 0) {
                                 classUY.method11780(
                                    var44,
                                    var16 + 1115597881 * var15.field4247 / 2,
                                    var17 + var15.field4257 * 293773697 / 2,
                                    var15.field4349 * -1317423003,
                                    var74
                                 );
                              } else if (0 != var18) {
                                 SpritePixels.method13568(
                                    var44, var16, var17, -2085080349 * var15.field4247, 293773697 * var15.field4257, 256 - (var18 & -2095025005)
                                 );
                              } else if (var56 == var15.field4247 * 1115597881 && -2006483044 * var15.field4257 == var66) {
                                 var44.method13555(var16, var17);
                              } else {
                                 SpritePixels.method13558(var44, var16, var17, 1115597881 * var15.field4247, var15.field4257 * 293773697);
                              }
                           } else {
                              classYW.method13713(var16, var17, var16 + var15.field4247 * -1439552671, var17 + 293773697 * var15.field4257);
                              int var73 = (1136031601 * var15.field4247 + (var56 - 1)) / var56;
                              int var81 = (var66 - 1 + var15.field4257 * 293773697) / var66;

                              for (int var84 = 0; var84 < var73; var84++) {
                                 for (int var87 = 0; var87 < var81; var87++) {
                                    if (0 != var15.field4349 * -1375669202) {
                                       classUY.method11780(
                                          var44, var56 / 2 + var84 * var56 + var16, var66 * var87 + var17 + var66 / 2, 142218501 * var15.field4349, 4096
                                       );
                                    } else if (var18 != 0) {
                                       var44.method13565(var84 * var56 + var16, var17 + var87 * var66, 1964883830 - (var18 & 0xFF));
                                    } else {
                                       var44.method13555(var84 * var56 + var16, var17 + var66 * var87);
                                    }
                                 }
                              }

                              classYW.method13710(var6, var7, var8, var9);
                           }
                        }
                     }
                  } else if (6 == -1392861212 * var15.field4373) {
                     Animation var43 = null;
                     int var55 = -1;
                     boolean var65 = method5615(this, var15, (byte)5);
                     int var72;
                     if (var65) {
                        var72 = 1198637428 * var15.field4291;
                     } else {
                        var72 = 2114568197 * var15.field4286;
                     }

                     if (-1 != var72) {
                        var43 = classQA.method9477(var72, -1247929542);
                        var55 = -814893963 * var15.field4319;
                     }

                     Model var79 = null;
                     int var83 = 0;
                     if (2073671441 * var15.field4317 != -1) {
                        var79 = this.field2636.vmethod115(var15, (byte)-105);
                        if (var79 != null) {
                           Model.method5254(var79);
                           var83 = var79.field1741 * 1194171296 / 2;
                        }
                     } else if (5 == var15.field4331 * 569920856) {
                        var79 = this.field2636.vmethod119(var15, (byte)4);
                     } else if (var15.field4331 * -910601 == 7) {
                        var79 = this.field2636.vmethod122(var15, (byte)68);
                     } else if (8 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod127(var15, var43, var55, (byte)14);
                     } else if (9 == var15.field4331 * -910601) {
                        var79 = this.field2636.vmethod129(var15, var43, var55, (byte)43);
                     } else if (6 == var15.field4331 * -929741793) {
                        var79 = this.field2636.vmethod133(var15, var43, var55, var65, -1205241094);
                     } else if (var15.field4331 * 2032165483 == 2) {
                        var79 = this.field2636.vmethod139(var15, var43, var55, var65, 129757519);
                     } else {
                        var79 = this.field2636.vmethod140(var15, var43, var55, var65, (byte)-80);
                     }

                     classFH.method4776(1115597881 * var15.field4247 / 2 + var16, 293773697 * var15.field4257 / 2 + var17);
                     int var86 = classFH.field2251[-1736340430 * var15.field4354] * var15.field4336 * -504026218 >> 16;
                     int var89 = var15.field4336 * 373703660 * classFH.field2252[var15.field4354 * -2142836023] >> 16;
                     if (null != var79) {
                        classFH.method4704(false);
                        if (!var15.field4273) {
                           var79.method5269(0, -1008620329 * var15.field4290, 0, var15.field4354 * -1428257987, 0, var86, var89);
                        } else {
                           Model.method5254(var79);
                           if (var15.field4295) {
                              Model.method5188(
                                 var79,
                                 0,
                                 var15.field4290 * 1597201692,
                                 -558221811 * var15.field4292,
                                 var15.field4354 * -1246332317,
                                 var15.field4288 * -1215475791,
                                 var86 + var83 + var15.field4289 * -30521739,
                                 var15.field4289 * -2087768157 + var89,
                                 var15.field4336 * 245118287
                              );
                           } else {
                              var79.method5269(
                                 0,
                                 -1595265599 * var15.field4290,
                                 -1325176614 * var15.field4292,
                                 743439871 * var15.field4354,
                                 var15.field4288 * 556908976,
                                 var83 + var86 + 1030882151 * var15.field4289,
                                 var89 + var15.field4289 * -629111388
                              );
                           }
                        }
                     }

                     classFH.method4774();
                  } else if (507660617 * var15.field4373 == 8) {
                     if (classVC.method12008(classVV.method12187(var12, 1552136440), var15, (byte)117)) {
                        classZV var42 = this.field2635.vmethod105(classXJ.field6876, -725461430);
                        int var53 = 0;
                        int var63 = 0;
                        classZV var71 = var42;
                        String var27 = var15.field4299;

                        for (String var76 = this.method5609(var27, var15, 2143150379); !var76.isEmpty(); var63 += var71.field8 + 1) {
                           int var29 = var76.indexOf(classDO.field1586);
                           String var28;
                           if (-1 != var29) {
                              var28 = var76.substring(0, var29);
                              var76 = var76.substring(var29 + 4);
                           } else {
                              var28 = var76;
                              var76 = "";
                           }

                           int var30 = var71.method33(var28);
                           if (var30 > var53) {
                              var53 = var30;
                           }
                        }

                        var53 += 6;
                        var63 += 7;
                        int var85 = var16 + 1115597881 * var15.field4247 - 5 - var53;
                        int var88 = 5 + var15.field4257 * 1978089065 + var17;
                        if (var85 < 5 + var16) {
                           var85 = 5 + var16;
                        }

                        if (var85 + var53 > var8) {
                           var85 = var8 - var53;
                        }

                        if (var88 + var63 > var9) {
                           var88 = var9 - var63;
                        }

                        classYW.method13737(var85, var88, var53, var63, 16777120);
                        classYW.method13746(var85, var88, var53, var63, 0);
                        var27 = var15.field4299;
                        int var31 = 2 + var88 + var71.field8;

                        for (String var78 = this.method5609(var27, var15, 2024540653); !var78.isEmpty(); var31 += 1 + var71.field8) {
                           int var32 = var78.indexOf(classDO.field1586);
                           String var82;
                           if (-1 != var32) {
                              var82 = var78.substring(0, var32);
                              var78 = var78.substring(4 + var32);
                           } else {
                              var82 = var78;
                              var78 = "";
                           }

                           var71.method51(var82, 3 + var85, var31, 0, -1);
                        }
                     }
                  } else if (9 == -584922293 * var15.field4373) {
                     int var41;
                     int var52;
                     int var62;
                     int var70;
                     if (var15.field4276) {
                        var41 = var16;
                        var52 = var15.field4257 * 1878391001 + var17;
                        var62 = 1115597881 * var15.field4247 + var16;
                        var70 = var17;
                     } else {
                        var41 = var16;
                        var52 = var17;
                        var62 = 1382057966 * var15.field4247 + var16;
                        var70 = var15.field4257 * -1706957336 + var17;
                     }

                     if (var15.field4275 * 845712065 == 1) {
                        classYW.method13771(var41, var52, var62, var70, 144628425 * var15.field4268);
                     } else {
                        classYW.method13784(var41, var52, var62, var70, 845712065 * var15.field4275, var15.field4268 * -1759505403);
                     }
                  } else if (-1510882375 * var15.field4373 == 12) {
                     classLH var40 = var15.method7334(-89648524);
                     classLI var51 = var15.method7337(1746716631);
                     if (var40 != null && var51 != null && var40.method7043((byte)-20)) {
                        classZV var61 = Widget.method7244(var15, var12, (byte)65);
                        if (null != var61) {
                           this.field2638
                              .method5312(
                                 var16,
                                 var17,
                                 1115597881 * var15.field4247,
                                 293773697 * var15.field4257,
                                 classLH.method7045(var40, 1318844803),
                                 var40.method7049(691227399),
                                 var40.method7038((byte)-6),
                                 var40.method7039(2064059700),
                                 var40.method7034((byte)3),
                                 395641138
                              );
                           int var69 = var15.field4304 ? var15.field4282 * 880555563 : -1;
                           if (!var40.method7027(1098784425) && classZD.method13889(var40.method7017(-1442380956), -581958393)) {
                              classGA.method5313(
                                 this.field2638, var51.field4167 * 352906769, var69, -211319977 * var51.field4170, var51.field4168 * 1532740787, 390210280
                              );
                              classGA.method5318(this.field2638, var40.method7020(425235102), var61, 120041229);
                           } else {
                              classGA.method5313(
                                 this.field2638, 1132105675 * var15.field4268, var69, var51.field4170 * 430826393, 1720214873 * var51.field4168, 622643803
                              );
                              classGA.method5318(this.field2638, var40.method7017(389208221), var61, 120041229);
                           }

                           classYW.method13710(var6, var7, var8, var9);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("bm")
   static String method5611(int var0) {
      String var1 = Integer.toString(var0);

      for (int var2 = var1.length() - 3; var2 > 0; var2 -= 3) {
         var1 = var1.substring(0, var2) + classDO.field1590 + var1.substring(var2);
      }

      if (var1.length() > 9) {
         return " "
            + classQY.method9885(65408, (byte)83)
            + var1.substring(0, var1.length() - 8)
            + classKK.field3827
            + " "
            + classDO.field1591
            + var1
            + classDO.field1589
            + classDO.field1592;
      } else {
         return var1.length() > 6
            ? " "
               + classQY.method9885(16777215, (byte)43)
               + var1.substring(0, var1.length() - 4)
               + classKK.field3655
               + " "
               + classDO.field1591
               + var1
               + classDO.field1589
               + classDO.field1592
            : " " + classQY.method9885(16776960, (byte)91) + var1 + classDO.field1592;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("bg")
   boolean method5613(Widget var1) {
      if (null == var1.field4398) {
         return false;
      } else {
         for (int var2 = 0; var2 < var1.field4398.length; var2++) {
            int var3 = this.field2637.vmethod181(var1, var2, 1272022286);
            int var4 = var1.field4399[var2];
            if (2 == var1.field4398[var2]) {
               if (var3 >= var4) {
                  return false;
               }
            } else if (3 == var1.field4398[var2]) {
               if (var3 <= var4) {
                  return false;
               }
            } else if (var1.field4398[var2] == 4) {
               if (var4 == var3) {
                  return false;
               }
            } else if (var4 != var3) {
               return false;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;I)V")
   @ObfuscatedName("bu")
   void method5618(int var1, classVV var2, int var3) {
      if (var2.method7425(var1, 1537802264)) {
         this.method5621(var2.field4438[var1], 0, var2.field4438[var1].length - 1, -1, -1, var2, var3, (byte)-6);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("bk")
   boolean method5614(Widget var1) {
      if (null == var1.field4398) {
         return false;
      } else {
         for (int var2 = 0; var2 < var1.field4398.length; var2++) {
            int var3 = this.field2637.vmethod181(var1, var2, -1709351486);
            int var4 = var1.field4399[var2];
            if (2 == var1.field4398[var2]) {
               if (var3 >= var4) {
                  return false;
               }
            } else if (3 == var1.field4398[var2]) {
               if (var3 <= var4) {
                  return false;
               }
            } else if (var1.field4398[var2] == 4) {
               if (var4 == var3) {
                  return false;
               }
            } else if (var4 != var3) {
               return false;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgp;Llw;B)Z")
   @ObfuscatedName("pv")
   public static boolean method5615(classGP var0, Widget var1, byte var2) {
      if (var0 == null) {
         return var0.method5616(var1, var2);
      } else {
         try {
            if (null == var1.field4398) {
               return false;
            } else {
               for (int var3 = 0; var3 < var1.field4398.length; var3++) {
                  if (var2 == -108) {
                     throw new IllegalStateException();
                  }

                  int var4 = var0.field2637.vmethod181(var1, var3, -1470283120);
                  int var5 = var1.field4399[var3];
                  if (2 == var1.field4398[var3]) {
                     if (var2 == -108) {
                        throw new IllegalStateException();
                     }

                     if (var4 >= var5) {
                        if (var2 == -108) {
                           throw new IllegalStateException();
                        }

                        return false;
                     }
                  } else if (3 == var1.field4398[var3]) {
                     if (var4 <= var5) {
                        if (var2 == -108) {
                           throw new IllegalStateException();
                        }

                        return false;
                     }
                  } else if (var1.field4398[var3] == 4) {
                     if (var5 == var4) {
                        return false;
                     }
                  } else if (var5 != var4) {
                     return false;
                  }
               }

               return true;
            }
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "gp.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILvv;I)V")
   @ObfuscatedName("bo")
   void method5619(int var1, classVV var2, int var3) {
      if (var2.method7425(var1, 429811542)) {
         this.method5621(var2.field4438[var1], 0, var2.field4438[var1].length - 1, -1, -1, var2, var3, (byte)-79);
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;I)V")
   @ObfuscatedName("du")
   public void method5624(
      Widget[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, classVV var12, int var13
   ) {
      for (int var14 = var2; var14 <= var3; var14++) {
         Widget var15 = var1[var14];
         if (var15 != null && var15.field4340 * -1502647899 == var4 && var15.field4261 * 653784551 == var5 && !var15.isSelfHidden()) {
            int var16 = var10 + var15.getRelativeX();
            int var17 = var11 + var15.getRelativeY();
            if (var15.getType() == 3
               && var16 == classOE.field4841.getViewportXOffset()
               && var17 == classOE.field4841.getViewportYOffset()
               && var15.getWidth() == classOE.field4841.getViewportWidth()
               && var15.getHeight() == classOE.field4841.getViewportHeight()
               && var15.getOpacity() > 0
               && var15.isFilled()
               && var15.method7400().method13328() == 0
               && classOE.field4841.isGpu()) {
               int var18 = var15.getTextColor();
               int var19 = var15.getOpacity() & 0xFF;
               int var20 = 256 - var19;
               int var21 = field2648;
               int var22 = (var20 * (var18 & 16711935) >> 8 & 16711935) + (var20 * (var18 & 0xFF00) >> 8 & 0xFF00);
               int var23 = ((var21 & 16711935) * var19 >> 8 & 16711935) + (var19 * (var21 & 0xFF00) >> 8 & 0xFF00);
               int var24 = var20 + ((var21 >>> 24) * (255 - var20) * 32897 >>> 23);
               field2648 = var24 << 24 | var22 + var23;
               var15.method7408(true);
               field2642.add(var15);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)I")
   @ObfuscatedName("gy")
   public static int method5595(IndexDataBase var0) {
      return var0.field6440 * -442718787;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ah")
   void method5605(int var1, int var2, int var3) {
      if (this.field2632.vmethod89(797563684).method2738(1150434614)) {
         var2 += 15;
         classVP.field6548.method57("Fps:" + this.field2637.vmethod172(-390513682), var3 + var1, var2, 16776960, -1);
         var2 += 15;
         Runtime var4 = Runtime.getRuntime();
         int var5 = (int)((var4.totalMemory() - var4.freeMemory()) / 1024L);
         int var6 = 16776960;
         if (var5 > 327680 && !this.field2637.vmethod177(2130255174)) {
            var6 = 1757339270;
         }

         classVP.field6548.method57("Mem:" + var5 + "k", var1 + var3, var2, var6, -1);
      }
   }

   @ObfuscatedSignature(descriptor = "([Llw;IIIIIIIIIILvv;II)V")
   @ObfuscatedName("ae")
   void method5594(
      Widget[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, classVV var12, int var13, int var14
   ) {
      this.method5624(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);

      try {
         classYW.method13710(var6, var7, var8, var9);
         classFH.method4760();

         for (int var15 = var2; var15 <= var3; var15++) {
            Widget var16 = var1[var15];
            if (null == var16) {
               if (var14 != 1690058415) {
                  this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                  return;
               }
            } else {
               label1035: {
                  if (-1502647899 * var16.field4340 == var4) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     if (var16.field4261 * 653784551 == var5) {
                        break label1035;
                     }

                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }
                  }

                  if (var4 != -1412584499) {
                     continue;
                  }

                  if (var14 != 1690058415) {
                     throw new IllegalStateException();
                  }

                  if (var16 != classVV.method12228(var12, (byte)-59)) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }
                     continue;
                  }
               }

               if (var16.field4273 && var12.method12196(var16, (byte)0)) {
                  if (var14 != 1690058415) {
                     this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                     return;
                  }
               } else {
                  if (-90794217 * var16.field4332 > 0) {
                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }

                     this.method5597(var16, var13, (byte)122);
                  }

                  int var17 = var10 + 744286069 * var16.field4279;
                  int var18 = var11 + 1570844829 * var16.field4255;
                  int var19 = 1517457381 * var16.field4345;
                  if (classVV.method12228(var12, (byte)-105) == var16) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     classVW var20 = var12.method12226((byte)0);
                     if (var4 != -1412584499) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        if (!var16.field4312) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           this.field2649 = var1;
                           this.field2644 = -100057729 * var10;
                           this.field2643 = 5465987 * var11;
                           continue;
                        }
                     }

                     if (classVW.method12324(var20, (short)1693)) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        if (var20.method12329(-1223649978)) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           int var21 = var20.method12304(-1458575824);
                           int var22 = var20.method12310((byte)-91);
                           int var23 = var20.method12284(487783638).field4247 * 1115597881;
                           int var24 = var20.method12284(-365832785).field4257 * 293773697;
                           int var25 = classTZ.field6253 * -38255113;
                           int var26 = classTZ.field6262 * -2144333897;
                           var25 -= var20.method12292((byte)36);
                           var26 -= classVW.method12294(var20, (byte)-90);
                           if (var25 < var21) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var25 = var21;
                           }

                           if (1115597881 * var16.field4247 + var25 > var23 + var21) {
                              if (var14 != 1690058415) {
                                 this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                                 return;
                              }

                              var25 = var23 + var21 - 1115597881 * var16.field4247;
                           }

                           if (var26 < var22) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var26 = var22;
                           }

                           if (var26 + var16.field4257 * 293773697 > var22 + var24) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var26 = var22 + var24 - var16.field4257 * 293773697;
                           }

                           var17 = var25;
                           var18 = var26;
                        }
                     }

                     if (!var16.field4312) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var19 = 128;
                     }
                  }

                  int var35;
                  int var36;
                  int var37;
                  int var38;
                  if (9 == -1510882375 * var16.field4373) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     int var39 = var17;
                     int var51 = var18;
                     int var61 = var17 + var16.field4247 * 1115597881;
                     int var27 = var16.field4257 * 293773697 + var18;
                     if (var61 < var17) {
                        var39 = var61;
                        var61 = var17;
                     }

                     if (var27 < var18) {
                        var51 = var27;
                        var27 = var18;
                     }

                     var61++;
                     var27++;
                     var35 = var39 > var6 ? var39 : var6;
                     int var10000;
                     if (var51 > var7) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        var10000 = var51;
                     } else {
                        var10000 = var7;
                     }

                     var36 = var10000;
                     if (var61 < var8) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var10000 = var61;
                     } else {
                        var10000 = var8;
                     }

                     var37 = var10000;
                     var38 = var27 < var9 ? var27 : var9;
                  } else {
                     int var40 = var16.field4247 * 1115597881 + var17;
                     int var52 = var18 + var16.field4257 * 293773697;
                     int var93;
                     if (var17 > var6) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var93 = var17;
                     } else {
                        var93 = var6;
                     }

                     var35 = var93;
                     if (var18 > var7) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var93 = var18;
                     } else {
                        var93 = var7;
                     }

                     var36 = var93;
                     if (var40 < var8) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var93 = var40;
                     } else {
                        var93 = var8;
                     }

                     var37 = var93;
                     var38 = var52 < var9 ? var52 : var9;
                  }

                  if (var16.field4273) {
                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }

                     if (var35 >= var37) {
                        continue;
                     }

                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }

                     if (var36 >= var38) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }
                        continue;
                     }
                  }

                  if (0 != -90794217 * var16.field4332) {
                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }

                     if (1336 == var16.field4332 * -90794217) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        this.method5602(var17, var18, 1115597881 * var16.field4247, 825915897);
                        continue;
                     }

                     if (1337 == var16.field4332 * -90794217) {
                        this.field2639.vmethod281(var17, var18, 1115597881 * var16.field4247, 293773697 * var16.field4257, -1657364698);
                        classYW.method13710(var6, var7, var8, var9);
                        continue;
                     }

                     if (1338 == -90794217 * var16.field4332) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        this.field2639.vmethod298(var16, var17, var18, (short)-10739);
                        classYW.method13710(var6, var7, var8, var9);
                        continue;
                     }

                     if (1339 == var16.field4332 * -90794217) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        this.method5600(var16, var17, var18, var12, (byte)74);
                        classYW.method13710(var6, var7, var8, var9);
                        continue;
                     }

                     if (1400 == var16.field4332 * -90794217) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        this.field2639
                           .vmethod285(
                              var17,
                              var18,
                              var16.field4247 * 1115597881,
                              293773697 * var16.field4257,
                              var13,
                              ClientPreferences.method2755(this.field2632.vmethod89(797563684), -1674478907)
                           );
                     }

                     if (1401 == var16.field4332 * -90794217) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        this.field2639.vmethod291(var17, var18, 1115597881 * var16.field4247, 293773697 * var16.field4257, -1714675824);
                     }

                     if (var16.field4332 * -90794217 == 1402) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        this.field2639.vmethod296(var17, var13, 1137310854);
                     }
                  }

                  if (0 == -1510882375 * var16.field4373) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     if (!var16.field4273) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        if (var12.method12196(var16, (byte)0) && !classVV.method12187(var12, 2061916916).method12002(var16, (byte)72)) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }
                           continue;
                        }
                     }

                     if (!var16.field4273) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        if (var16.field4329 * -41424189 > var16.field4267 * -714521463 - 293773697 * var16.field4257) {
                           var16.field4329 = var16.field4267 * -1471306045 - var16.field4257 * 577918571;
                        }

                        if (var16.field4329 * -41424189 < 0) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           var16.field4329 = 0;
                        }
                     }

                     this.method5594(
                        var1,
                        var16.field4263 * -1210177335,
                        var16.field4264 * 379541057,
                        -1163656551 * var16.field4327,
                        var16.field4258 * 63131743,
                        var35,
                        var36,
                        var37,
                        var38,
                        var17 - var16.field4339 * -34809441,
                        var18 - var16.field4329 * -41424189,
                        var12,
                        var13,
                        1690058415
                     );
                     if (var16.field4338 != null) {
                        this.method5594(
                           var16.field4338,
                           0,
                           var16.field4338.length - 1,
                           -1163656551 * var16.field4327,
                           -1,
                           var35,
                           var36,
                           var37,
                           var38,
                           var17 - var16.field4339 * -34809441,
                           var18 - -41424189 * var16.field4329,
                           var12,
                           var13,
                           1690058415
                        );
                     }

                     if (-1 == var16.field4258 * 63131743) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        WidgetNode var41 = (WidgetNode)HashTable.method13432(var12.field6611, var16.field4327 * -1163656551);
                        if (null != var41) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           this.method5587(var41.field5275 * 2023647411, var35, var36, var37, var38, var17, var18, var12, var13, 545645039);
                        }
                     }

                     classYW.method13710(var6, var7, var8, var9);
                     classFH.method4760();
                  } else if (11 == -1510882375 * var16.field4373) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     if (var12.method12196(var16, (byte)0)) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        if (!classVV.method12187(var12, 1653497242).method12002(var16, (byte)72)) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }
                           continue;
                        }
                     }

                     if (null != var16.field4338) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        this.method5594(
                           var16.field4338,
                           0,
                           var16.field4338.length - 1,
                           var16.field4327 * -1163656551,
                           -1,
                           var35,
                           var36,
                           var37,
                           var38,
                           var17 - -34809441 * var16.field4339,
                           var18 - var16.field4329 * -41424189,
                           var12,
                           var13,
                           1690058415
                        );
                     }

                     classYW.method13710(var6, var7, var8, var9);
                     classFH.method4760();
                  }

                  if (var16.field4373 * -1510882375 == 0) {
                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }

                     if (!var16.field4273) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        if (var16.field4267 * -714521463 > 293773697 * var16.field4257) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           method5606(
                              this,
                              var17 + 1115597881 * var16.field4247,
                              var18,
                              -41424189 * var16.field4329,
                              293773697 * var16.field4257,
                              -714521463 * var16.field4267,
                              (byte)-1
                           );
                        }
                     }
                  }

                  if (var16.field4373 * -1510882375 == 1) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }
                  } else if (-1510882375 * var16.field4373 == 3) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     int var49;
                     if (method5615(this, var16, (byte)-16)) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var49 = var16.field4293 * 948260113;
                        if (classVV.method12187(var12, -90191525).method12002(var16, (byte)32) && 0 != var16.field4271 * 1396903265) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           var49 = var16.field4271 * 1396903265;
                        }
                     } else {
                        var49 = 1740831551 * var16.field4268;
                        if (classVV.method12187(var12, 617931125).method12002(var16, (byte)101)) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           if (318120869 * var16.field4270 != 0) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var49 = 318120869 * var16.field4270;
                           }
                        }
                     }

                     if (var16.field4269) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        switch (1768204099 * var16.field4305.field6974) {
                           case 1:
                              classYW.method13738(
                                 var17,
                                 var18,
                                 1115597881 * var16.field4247,
                                 293773697 * var16.field4257,
                                 1740831551 * var16.field4268,
                                 var16.field4293 * 948260113
                              );
                              break;
                           case 2:
                              classYW.method13804(
                                 var17,
                                 var18,
                                 var16.field4247 * 1115597881,
                                 293773697 * var16.field4257,
                                 var16.field4268 * 1740831551,
                                 var16.field4293 * 948260113,
                                 255 - (var16.field4345 * 1517457381 & 0xFF),
                                 255 - (var16.field4274 * 1991172799 & 0xFF)
                              );
                              break;
                           default:
                              if (var19 == 0) {
                                 classYW.method13737(var17, var18, var16.field4247 * 1115597881, 293773697 * var16.field4257, var49);
                              } else {
                                 classYW.method13733(var17, var18, 1115597881 * var16.field4247, 293773697 * var16.field4257, var49, 256 - (var19 & 0xFF));
                              }
                        }
                     } else if (0 == var19) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        classYW.method13746(var17, var18, 1115597881 * var16.field4247, var16.field4257 * 293773697, var49);
                     } else {
                        classYW.method13750(var17, var18, var16.field4247 * 1115597881, var16.field4257 * 293773697, var49, 256 - (var19 & 0xFF));
                     }
                  } else if (4 == var16.field4373 * -1510882375) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     classZV var48 = Widget.method7244(var16, var12, (byte)20);
                     if (var48 == null) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }
                     } else {
                        String var69 = var16.field4299;
                        int var59;
                        if (method5615(this, var16, (byte)-68)) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           var59 = 948260113 * var16.field4293;
                           if (classVV.method12187(var12, 813215143).method12002(var16, (byte)115) && var16.field4271 * 1396903265 != 0) {
                              var59 = var16.field4271 * 1396903265;
                           }

                           if (!var16.field4300.isEmpty()) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var69 = var16.field4300;
                           }
                        } else {
                           var59 = 1740831551 * var16.field4268;
                           if (classVV.method12187(var12, -212020466).method12002(var16, (byte)104)) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              if (0 != var16.field4270 * 318120869) {
                                 if (var14 != 1690058415) {
                                    throw new IllegalStateException();
                                 }

                                 var59 = 318120869 * var16.field4270;
                              }
                           }
                        }

                        if (var16.field4273) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           label971:
                           if (var16.field4317 * 2073671441 != -1) {
                              if (var14 != 1690058415) {
                                 this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                                 return;
                              }

                              ItemComposition var77 = classOB.method8329(2073671441 * var16.field4317, (byte)-66);
                              var69 = var77.method8741(1248604557);
                              if (var69 == null) {
                                 if (var14 != 1690058415) {
                                    this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                                    return;
                                 }

                                 var69 = classKK.field3613;
                              }

                              if (1633663665 * var77.field5053 != 1) {
                                 if (var14 != 1690058415) {
                                    throw new IllegalStateException();
                                 }

                                 if (var16.field4342 * 1190987649 == 1) {
                                    break label971;
                                 }

                                 if (var14 != 1690058415) {
                                    this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                                    return;
                                 }
                              }

                              if (-1 != var16.field4342 * 1190987649) {
                                 if (var14 != 1690058415) {
                                    throw new IllegalStateException();
                                 }

                                 var69 = classQY.method9885(16748608, (byte)74)
                                    + var69
                                    + classDO.field1592
                                    + " "
                                    + 'x'
                                    + classSX.method10756(var16.field4342 * 1190987649, 1269917943);
                              }
                           }
                        }

                        if (var12.method12241(var16, (byte)-38)) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           var69 = classKK.field3781;
                           var59 = var16.field4268 * 1740831551;
                        }

                        if (!var16.field4273) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           var69 = this.method5609(var69, var16, 1977830334);
                        }

                        int var10004 = var16.field4247 * 1115597881;
                        int var10005 = var16.field4257 * 293773697;
                        byte var10007;
                        if (var16.field4304) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           var10007 = 0;
                        } else {
                           var10007 = -1;
                        }

                        var48.method63(
                           var69,
                           var17,
                           var18,
                           var10004,
                           var10005,
                           var59,
                           var10007,
                           classGK.method5514(var16.field4345 * 1517457381, -2066863073),
                           var16.field4302 * 1409141973,
                           -338086361 * var16.field4303,
                           704962289 * var16.field4301
                        );
                     }
                  } else if (-1510882375 * var16.field4373 == 5) {
                     if (var14 != 1690058415) {
                        this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                        return;
                     }

                     if (!var16.field4273) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        SpritePixels var47 = var16.method7369(var12, method5615(this, var16, (byte)-76), this.field2632.vmethod78((byte)-1), 1522102538);
                        if (null != var47) {
                           var47.method13555(var17, var18);
                        }
                     } else {
                        SpritePixels var46;
                        if (-1 != 2073671441 * var16.field4317) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           var46 = classLJ.method7157(
                              2073671441 * var16.field4317,
                              var16.field4342 * 1190987649,
                              var16.field4281 * -2011859635,
                              880555563 * var16.field4282,
                              -278245589 * var16.field4297,
                              false,
                              Math.max(36, var16.field4247 * 1115597881),
                              Math.max(32, 293773697 * var16.field4257),
                              1683831829
                           );
                        } else {
                           var46 = var16.method7369(var12, false, this.field2632.vmethod78((byte)-37), -1169119797);
                        }

                        if (null != var46) {
                           int var58 = var46.field7004;
                           int var68 = var46.field7006;
                           if (!var16.field4280) {
                              int var76 = -356282368 * var16.field4247 / var58;
                              if (var16.field4349 * 142218501 != 0) {
                                 classUY.method11780(
                                    var46,
                                    var17 + 1115597881 * var16.field4247 / 2,
                                    var18 + var16.field4257 * 293773697 / 2,
                                    var16.field4349 * 142218501,
                                    var76
                                 );
                              } else if (0 != var19) {
                                 SpritePixels.method13568(var46, var17, var18, 1115597881 * var16.field4247, 293773697 * var16.field4257, 256 - (var19 & 0xFF));
                              } else {
                                 if (var58 == var16.field4247 * 1115597881) {
                                    if (var14 != 1690058415) {
                                       throw new IllegalStateException();
                                    }

                                    if (293773697 * var16.field4257 == var68) {
                                       var46.method13555(var17, var18);
                                       continue;
                                    }
                                 }

                                 SpritePixels.method13558(var46, var17, var18, 1115597881 * var16.field4247, var16.field4257 * 293773697);
                              }
                           } else {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              classYW.method13713(var17, var18, var17 + var16.field4247 * 1115597881, var18 + 293773697 * var16.field4257);
                              int var75 = (1115597881 * var16.field4247 + (var58 - 1)) / var58;
                              int var83 = (var68 - 1 + var16.field4257 * 293773697) / var68;

                              for (int var86 = 0; var86 < var75; var86++) {
                                 if (var14 != 1690058415) {
                                    throw new IllegalStateException();
                                 }

                                 for (int var89 = 0; var89 < var83; var89++) {
                                    if (var14 != 1690058415) {
                                       throw new IllegalStateException();
                                    }

                                    if (0 != var16.field4349 * 142218501) {
                                       if (var14 != 1690058415) {
                                          throw new IllegalStateException();
                                       }

                                       classUY.method11780(
                                          var46, var58 / 2 + var86 * var58 + var17, var68 * var89 + var18 + var68 / 2, 142218501 * var16.field4349, 4096
                                       );
                                    } else if (var19 != 0) {
                                       if (var14 != 1690058415) {
                                          throw new IllegalStateException();
                                       }

                                       var46.method13565(var86 * var58 + var17, var18 + var89 * var68, 256 - (var19 & 0xFF));
                                    } else {
                                       var46.method13555(var86 * var58 + var17, var18 + var68 * var89);
                                    }
                                 }
                              }

                              classYW.method13710(var6, var7, var8, var9);
                           }
                        }
                     }
                  } else if (6 == -1510882375 * var16.field4373) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     Animation var45 = null;
                     int var57 = -1;
                     boolean var67 = method5615(this, var16, (byte)10);
                     int var74;
                     if (var67) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        var74 = 821151237 * var16.field4291;
                     } else {
                        var74 = 2114568197 * var16.field4286;
                     }

                     if (-1 != var74) {
                        var45 = classQA.method9477(var74, -1277205194);
                        var57 = -814893963 * var16.field4319;
                     }

                     Model var81 = null;
                     int var85 = 0;
                     if (2073671441 * var16.field4317 != -1) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var81 = this.field2636.vmethod115(var16, (byte)-120);
                        if (var81 != null) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           Model.method5254(var81);
                           var85 = var81.field1741 * -1256242689 / 2;
                        }
                     } else if (5 == var16.field4331 * -910601) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var81 = this.field2636.vmethod119(var16, (byte)4);
                     } else if (var16.field4331 * -910601 == 7) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        var81 = this.field2636.vmethod122(var16, (byte)64);
                     } else if (8 == var16.field4331 * -910601) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var81 = this.field2636.vmethod127(var16, var45, var57, (byte)14);
                     } else if (9 == var16.field4331 * -910601) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var81 = this.field2636.vmethod129(var16, var45, var57, (byte)43);
                     } else if (6 == var16.field4331 * -910601) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var81 = this.field2636.vmethod133(var16, var45, var57, var67, -1993548421);
                     } else if (var16.field4331 * -910601 == 2) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var81 = this.field2636.vmethod139(var16, var45, var57, var67, -1390393847);
                     } else {
                        var81 = this.field2636.vmethod140(var16, var45, var57, var67, (byte)-65);
                     }

                     classFH.method4776(1115597881 * var16.field4247 / 2 + var17, 293773697 * var16.field4257 / 2 + var18);
                     int var88 = classFH.field2251[-1246332317 * var16.field4354] * var16.field4336 * 245118287 >> 16;
                     int var91 = var16.field4336 * 245118287 * classFH.field2252[var16.field4354 * -1246332317] >> 16;
                     if (null != var81) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        classFH.method4704(false);
                        if (!var16.field4273) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           var81.method5269(0, -1008620329 * var16.field4290, 0, var16.field4354 * -1246332317, 0, var88, var91);
                        } else {
                           Model.method5254(var81);
                           if (var16.field4295) {
                              Model.method5188(
                                 var81,
                                 0,
                                 var16.field4290 * -1008620329,
                                 827587461 * var16.field4292,
                                 var16.field4354 * -1246332317,
                                 var16.field4288 * -1469553671,
                                 var88 + var85 + var16.field4289 * -30521739,
                                 var16.field4289 * -30521739 + var91,
                                 var16.field4336 * 245118287
                              );
                           } else {
                              var81.method5269(
                                 0,
                                 -1008620329 * var16.field4290,
                                 827587461 * var16.field4292,
                                 -1246332317 * var16.field4354,
                                 var16.field4288 * -1469553671,
                                 var85 + var88 + -30521739 * var16.field4289,
                                 var91 + var16.field4289 * -30521739
                              );
                           }
                        }
                     }

                     classFH.method4774();
                  } else if (-1510882375 * var16.field4373 == 8) {
                     if (classVC.method12008(classVV.method12187(var12, 2061692895), var16, (byte)11)) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        classZV var44 = this.field2635.vmethod105(classXJ.field6876, -725461430);
                        int var55 = 0;
                        int var65 = 0;
                        classZV var73 = var44;
                        String var28 = var16.field4299;

                        for (String var78 = this.method5609(var28, var16, 1855216883); !var78.isEmpty(); var65 += var73.field8 + 1) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           int var30 = var78.indexOf(classDO.field1586);
                           String var29;
                           if (-1 != var30) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var29 = var78.substring(0, var30);
                              var78 = var78.substring(var30 + 4);
                           } else {
                              var29 = var78;
                              var78 = "";
                           }

                           int var31 = var73.method33(var29);
                           if (var31 > var55) {
                              if (var14 != 1690058415) {
                                 this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                                 return;
                              }

                              var55 = var31;
                           }
                        }

                        var55 += 6;
                        var65 += 7;
                        int var87 = var17 + 1115597881 * var16.field4247 - 5 - var55;
                        int var90 = 5 + var16.field4257 * 293773697 + var18;
                        if (var87 < 5 + var17) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           var87 = 5 + var17;
                        }

                        if (var87 + var55 > var8) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           var87 = var8 - var55;
                        }

                        if (var90 + var65 > var9) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           var90 = var9 - var65;
                        }

                        classYW.method13737(var87, var90, var55, var65, 16777120);
                        classYW.method13746(var87, var90, var55, var65, 0);
                        var28 = var16.field4299;
                        int var32 = 2 + var90 + var73.field8;

                        for (String var80 = this.method5609(var28, var16, 2043879658); !var80.isEmpty(); var32 += 1 + var73.field8) {
                           if (var14 != 1690058415) {
                              throw new IllegalStateException();
                           }

                           int var33 = var80.indexOf(classDO.field1586);
                           String var84;
                           if (-1 != var33) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              var84 = var80.substring(0, var33);
                              var80 = var80.substring(4 + var33);
                           } else {
                              var84 = var80;
                              var80 = "";
                           }

                           var73.method51(var84, 3 + var87, var32, 0, -1);
                        }
                     }
                  } else if (9 == -1510882375 * var16.field4373) {
                     if (var14 != 1690058415) {
                        throw new IllegalStateException();
                     }

                     int var43;
                     int var54;
                     int var64;
                     int var72;
                     if (var16.field4276) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        var43 = var17;
                        var54 = var16.field4257 * 293773697 + var18;
                        var64 = 1115597881 * var16.field4247 + var17;
                        var72 = var18;
                     } else {
                        var43 = var17;
                        var54 = var18;
                        var64 = 1115597881 * var16.field4247 + var17;
                        var72 = var16.field4257 * 293773697 + var18;
                     }

                     if (var16.field4275 * 845712065 == 1) {
                        if (var14 != 1690058415) {
                           throw new IllegalStateException();
                        }

                        classYW.method13771(var43, var54, var64, var72, 1740831551 * var16.field4268);
                     } else {
                        classYW.method13784(var43, var54, var64, var72, 845712065 * var16.field4275, var16.field4268 * 1740831551);
                     }
                  } else if (-1510882375 * var16.field4373 == 12) {
                     classLH var42 = var16.method7334(-988626374);
                     classLI var53 = var16.method7337(810441855);
                     if (var42 != null) {
                        if (var14 != 1690058415) {
                           this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                           return;
                        }

                        if (var53 != null) {
                           if (var14 != 1690058415) {
                              this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
                              return;
                           }

                           if (var42.method7043((byte)-102)) {
                              if (var14 != 1690058415) {
                                 throw new IllegalStateException();
                              }

                              classZV var63 = Widget.method7244(var16, var12, (byte)-37);
                              if (null != var63) {
                                 if (var14 != 1690058415) {
                                    throw new IllegalStateException();
                                 }

                                 label1001: {
                                    this.field2638
                                       .method5312(
                                          var17,
                                          var18,
                                          1115597881 * var16.field4247,
                                          293773697 * var16.field4257,
                                          classLH.method7045(var42, 1815138794),
                                          var42.method7049(-1306679676),
                                          var42.method7038((byte)-85),
                                          var42.method7039(-6445662),
                                          var42.method7034((byte)3),
                                          395641138
                                       );
                                    int var71 = var16.field4304 ? var16.field4282 * 880555563 : -1;
                                    if (!var42.method7027(1398060670)) {
                                       if (var14 != 1690058415) {
                                          throw new IllegalStateException();
                                       }

                                       if (classZD.method13889(var42.method7017(-50111782), 1308995388)) {
                                          if (var14 != 1690058415) {
                                             throw new IllegalStateException();
                                          }

                                          classGA.method5313(
                                             this.field2638,
                                             var53.field4167 * 201043577,
                                             var71,
                                             -211319977 * var53.field4170,
                                             var53.field4168 * 1532740787,
                                             -358572900
                                          );
                                          classGA.method5318(this.field2638, var42.method7020(-74330507), var63, 120041229);
                                          break label1001;
                                       }
                                    }

                                    classGA.method5313(
                                       this.field2638,
                                       1740831551 * var16.field4268,
                                       var71,
                                       var53.field4170 * -211319977,
                                       1532740787 * var53.field4168,
                                       2022870815
                                    );
                                    classGA.method5318(this.field2638, var42.method7017(109922931), var63, 120041229);
                                 }

                                 classYW.method13710(var6, var7, var8, var9);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         this.method5625(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var11, var12, var13);
      } catch (RuntimeException var34) {
         throw classEG.method3899(var34, "gp.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("af")
   public static void method5580(int var0, int var1, int var2) {
      try {
         int var4;
         int var5;
         int var7;
         label29: {
            VarbitComposition var3 = classOL.method8542(var0, (short)-2595);
            var4 = -1681019861 * var3.field5418;
            var5 = -526564793 * var3.field5417;
            int var6 = var3.field5419 * 2095283109;
            var7 = classLB.field4083[var6 - var5];
            if (var1 >= 0) {
               if (var2 <= -2106322914) {
                  return;
               }

               if (var1 <= var7) {
                  break label29;
               }

               if (var2 <= -2106322914) {
                  throw new IllegalStateException();
               }
            }

            var1 = 0;
         }

         var7 <<= var5;
         classLB.field4088[var4] = classLB.field4088[var4] & ~var7 | var1 << var5 & var7;
         client.method2241(var4);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gp.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/security/SecureRandom;")
   @ObfuscatedName("ae")
   static SecureRandom method5581(byte var0) {
      try {
         SecureRandom var1 = new SecureRandom();
         var1.nextInt();
         return var1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gp.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   public static int method5582(int var0, int var1) {
      try {
         return classGV.field2704[var0 & 2047];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gp.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("ar")
   static int method5623(int var0, Script var1, boolean var2, int var3) {
      try {
         Widget var4;
         if (var0 >= 2000) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var0 -= 1000;
            var4 = classWK.field6689.method7421(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], (byte)96);
         } else {
            Widget var10000;
            if (var2) {
               if (var3 != 1839316601) {
                  throw new IllegalStateException();
               }

               var10000 = classSZ.field6108;
            } else {
               var10000 = TileItem.field1556;
            }

            var4 = var10000;
         }

         String var5 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
         int[] var6 = null;
         if (!var5.isEmpty()) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            if (var5.charAt(var5.length() - 1) == 'Y') {
               if (var3 != 1839316601) {
                  throw new IllegalStateException();
               }

               int var7 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (var7 > 0) {
                  var6 = new int[var7];

                  while (var7-- > 0) {
                     var6[var7] = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  }
               }

               var5 = var5.substring(0, var5.length() - 1);
            }
         }

         Object[] var11 = new Object[var5.length() + 1];

         for (int var8 = var11.length - 1; var8 >= 1; var8--) {
            switch (var5.charAt(var8 - 1)) {
               case 'W':
               case 'X':
               case 's':
                  var11[var8] = classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
                  break;
               case 'Ï':
                  var11[var8] = new Long(classBB.field322[(classXT.field6937 -= -1297854163) * 18294437]);
                  break;
               default:
                  var11[var8] = new Integer(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
            }
         }

         int var12 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
         if (-1 != var12) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var11[0] = new Integer(var12);
         } else {
            var11 = null;
         }

         if (var0 == 1400) {
            var4.field4357 = var11;
         } else if (1401 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4376 = var11;
         } else if (1402 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4355 = var11;
         } else if (1403 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4400 = var11;
         } else if (1404 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4359 = var11;
         } else if (1405 == var0) {
            var4.field4377 = var11;
         } else if (var0 == 1406) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4348 = var11;
         } else if (var0 == 1407) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4380 = var11;
            var4.field4278 = var6;
         } else if (var0 == 1408) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4341 = var11;
         } else if (var0 == 1409) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4384 = var11;
         } else if (var0 == 1410) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4378 = var11;
         } else if (1411 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4375 = var11;
         } else if (1412 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4284 = var11;
         } else if (var0 == 1414) {
            var4.field4353 = var11;
            var4.field4265 = var6;
         } else if (var0 == 1415) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4382 = var11;
            var4.field4383 = var6;
         } else if (var0 == 1416) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4379 = var11;
         } else if (1417 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4385 = var11;
         } else if (1418 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4363 = var11;
         } else if (1419 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4386 = var11;
         } else if (1420 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4381 = var11;
         } else if (var0 == 1421) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4389 = var11;
         } else if (var0 == 1422) {
            var4.field4392 = var11;
         } else if (1423 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4404 = var11;
         } else if (1424 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4393 = var11;
         } else if (1425 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4394 = var11;
         } else if (1426 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4395 = var11;
         } else if (var0 == 1427) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4244 = var11;
         } else if (var0 == 1428) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4390 = var11;
         } else if (var0 == 1429) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4391 = var11;
         } else if (1430 == var0) {
            var4.field4387 = var11;
         } else if (1431 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4361 = var11;
         } else if (var0 == 1434) {
            var4.field4396 = var11;
         } else if (1435 == var0) {
            if (var3 != 1839316601) {
               throw new IllegalStateException();
            }

            var4.field4252 = var11;
         } else {
            label416: {
               if (var0 >= 1436) {
                  if (var3 != 1839316601) {
                     throw new IllegalStateException();
                  }

                  if (var0 <= 1439) {
                     if (var3 != 1839316601) {
                        throw new IllegalStateException();
                     }

                     classLX var9 = var4.method7343(-1876593199);
                     if (var9 != null) {
                        if (var3 != 1839316601) {
                           throw new IllegalStateException();
                        }

                        if (var0 == 1436) {
                           if (var3 != 1839316601) {
                              throw new IllegalStateException();
                           }

                           var9.field4423 = var11;
                        } else if (1437 == var0) {
                           if (var3 != 1839316601) {
                              throw new IllegalStateException();
                           }

                           var9.field4424 = var11;
                        } else if (var0 == 1438) {
                           if (var3 != 1839316601) {
                              throw new IllegalStateException();
                           }

                           var9.field4425 = var11;
                        } else if (var0 == 1439) {
                           var9.field4426 = var11;
                        }
                     }
                     break label416;
                  }
               }

               return 2;
            }
         }

         var4.field4313 = true;
         return 1;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gp.ar(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;IILvv;II)V")
   @ObfuscatedName("bb")
   public static void method5584(classGP var0, int var1, int var2, classVV var3, int var4, int var5) {
      if (var0 == null) {
         var0.method5589(var1, var1, var1, var1, var1, var1, var1, var3, var1);
      }

      if (var3.field6610 * -77072447 != -1) {
         var0.method5617(-77072447 * var3.field6610, var3, var5, (byte)1);
         var0.method5587(var3.field6610 * -77072447, 0, 0, var1, var2, 0, 0, var3, var4, 545645039);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Z")
   @ObfuscatedName("ao")
   boolean method5616(Widget var1, byte var2) {
      try {
         if (null == var1.field4402) {
            return false;
         } else {
            for (int var3 = 0; var3 < var1.field4265.length; var3++) {
               if (var2 == -108) {
                  throw new IllegalStateException();
               }

               int var4 = this.field2637.vmethod181(var1, var3, -1470283120);
               int var5 = var1.field4402[var3];
               if (2 == var1.field4368[var3]) {
                  if (var2 == -108) {
                     throw new IllegalStateException();
                  }

                  if (var4 >= var5) {
                     if (var2 == -108) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
               } else if (3 == var1.field4383[var3]) {
                  if (var4 <= var5) {
                     if (var2 == -108) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }
               } else if (var1.field4402[var3] == 4) {
                  if (var5 == var4) {
                     return false;
                  }
               } else if (var5 != var4) {
                  return false;
               }
            }

            return true;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gp.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIB)V")
   @ObfuscatedName("ax")
   void method5607(int var1, int var2, int var3, int var4, int var5, byte var6) {
      try {
         IndexedSprite[] var7 = this.field2635.vmethod108(1569969832);
         IndexedSprite.method13853(var7[0], var1, var2);
         IndexedSprite.method13853(var7[1], var1, var4 + var2 - 16);
         classYW.method13749(var1, var2 + 16, 16, var4 - 32, 2301979);
         int var8 = (var4 - 32) * var4 / var5;
         if (var8 < 8) {
            if (var6 != -1) {
               return;
            }

            var8 = 8;
         }

         int var9 = (var4 - 32 - var8) * var3 / (var5 - var4);
         classYW.method13730(var1, var9 + 16 + var2, 16, var8, 5063219);
         classYW.method13712(var1, var2 + 16 + var9, var8, 7759444);
         classYW.method13712(1 + var1, var2 + 16 + var9, var8, 7759444);
         classYW.method13710(var1, var9 + var2 + 16, 16, 7759444);
         classYW.method13711(var1, 17 + var2 + var9, 16, 7759444);
         classYW.method13712(var1 + 15, var2 + 16 + var9, var8, 3353893);
         classYW.method13756(14 + var1, var9 + 17 + var2, var8 - 1, 3353893);
         classYW.method13712(var1, var9 + 15 + var2 + var8, 16, 3353893);
         classYW.method13711(var1 + 1, var8 + 14 + var2 + var9, 15, 3353893);
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gp.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgp;IILvv;III)V")
   @ObfuscatedName("tl")
   public static void method5585(classGP var0, int var1, int var2, classVV var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method5586(var1, var1, var3, var1, var1, var1);
      }

      try {
         if (var3.field6610 * -77072447 != -1) {
            if (var6 >= -200174874) {
               throw new IllegalStateException();
            }

            var0.method5617(-77072447 * var3.field6610, var3, var5, (byte)1);
            var0.method5587(var3.field6610 * -77072447, 0, 0, var1, var2, 0, 0, var3, var4, 545645039);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gp.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;Llw;I)V")
   @ObfuscatedName("uu")
   public static void method5599(classGP var0, Widget var1, int var2) {
      if (var0 == null) {
         var0.method5598(var1, var2);
      } else {
         int var3 = var1.field4332 * -90794217;
         if (var3 == 324) {
            if (1095022465 * var0.field2633 == -1) {
               var0.field2633 = var1.field4346 * 1013652457;
               var0.field2634 = var1.field4347 * 1610883489;
            }

            if (var0.field2637.vmethod178(-1963818833) == 1) {
               var1.field4346 = -1309316007 * var0.field2633;
            } else {
               var1.field4346 = var0.field2634 * 575002083;
            }
         } else if (var3 == 325) {
            if (-1 == 712895439 * var0.field2633) {
               var0.field2633 = -2063943867 * var1.field4346;
               var0.field2634 = 261153745 * var1.field4347;
            }

            if (var0.field2637.vmethod178(-1661790117) == 1) {
               var1.field4346 = 1567519918 * var0.field2634;
            } else {
               var1.field4346 = -230072226 * var0.field2633;
            }
         } else if (var3 == 327) {
            var1.field4354 = 777190345;
            var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 2047) * 881145893;
            var1.field4331 = -1013325341;
            var1.field4372 = 0;
         } else if (var3 == -1096988311) {
            var1.field4354 = -1432036539;
            var1.field4290 = ((int)(Math.sin(var2 / 40.0) * 256.0) & 713937275) * 1398957021;
            var1.field4331 = -1013325341;
            var1.field4372 = 434741949;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILvv;III)V")
   @ObfuscatedName("az")
   public void method5586(int var1, int var2, classVV var3, int var4, int var5, int var6) {
      try {
         if (var3.field6613 * -77072447 != -1) {
            if (var6 >= -200174874) {
               throw new IllegalStateException();
            }

            this.method5617(-77072447 * var3.field6610, var3, var5, (byte)1);
            this.method5587(var3.field6610 * -77072447, 0, 0, var1, var2, 0, 0, var3, var4, 545645039);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gp.az(" + ')');
      }
   }
}
