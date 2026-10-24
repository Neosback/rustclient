import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nd")
public class classND extends classAH {
   @ToRemove(unused = "true")
   @ObfuscatedName("cy")
   public static final int field4663 = 84;
   @ObfuscatedSignature(descriptor = "Lao;")
   @ObfuscatedName("af")
   classAO field4660;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field4662 = 77;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field4661 = 8;
   @ObfuscatedSignature(descriptor = "Lnu;")
   @ObfuscatedName("ae")
   classNU field4658;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("az")
   Deque2 field4659 = new Deque2();

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("as")
   @Override
   protected classAH vmethod29() {
      try {
         classNT var1 = (classNT)this.field4659.method7957();
         if (var1 == null) {
            return null;
         } else {
            return (classAH)(null != var1.field4742 ? var1.field4742 : this.vmethod35());
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nd.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("ax")
   @Override
   protected classAH vmethod35() {
      try {
         classNT var1;
         do {
            var1 = (classNT)this.field4659.method7965();
            if (var1 == null) {
               return null;
            }
         } while (null == var1.field4742);

         return var1.field4742;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nd.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   @Override
   protected int vmethod32() {
      try {
         return 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nd.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("aa")
   @Override
   protected void vmethod30(int[] var1, int var2, int var3) {
      try {
         this.field4660.vmethod30(var1, var2, var3);

         label36:
         for (classNT var6 = (classNT)this.field4659.method7957(); var6 != null; var6 = (classNT)this.field4659.method7965()) {
            if (!classNU.method8180(this.field4658, var6, (byte)-30)) {
               int var4 = var2;
               int var5 = var3;

               while (var5 > var6.field4722 * 1837314861) {
                  this.method7865(var6, var1, var4, var6.field4722 * 1837314861, var5 + var4, 1165124713);
                  var4 += 1837314861 * var6.field4722;
                  var5 -= var6.field4722 * 1837314861;
                  if (classNU.method8182(this.field4658, var6, var1, var4, var5, (byte)-1)) {
                     continue label36;
                  }
               }

               this.method7865(var6, var1, var4, var5, var4 + var5, 683812115);
               var6.field4722 -= 1686279333 * var5;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "nd.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   @Override
   protected void vmethod31(int var1) {
      try {
         this.field4660.vmethod31(var1);

         label35:
         for (classNT var3 = (classNT)this.field4659.method7957(); var3 != null; var3 = (classNT)this.field4659.method7965()) {
            if (!classNU.method8180(this.field4658, var3, (byte)-35)) {
               int var2 = var1;

               while (var2 > var3.field4722 * 1837314861) {
                  this.method7871(var3, var3.field4722 * 1837314861, -1959725412);
                  var2 -= 1837314861 * var3.field4722;
                  if (classNU.method8182(this.field4658, var3, null, 0, var2, (byte)-1)) {
                     continue label35;
                  }
               }

               this.method7871(var3, var2, -1580511942);
               var3.field4722 -= 1686279333 * var2;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "nd.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bc")
   @Override
   protected void vmethod37(int[] var1, int var2, int var3) {
      this.field4660.vmethod30(var1, var2, var3);

      label34:
      for (classNT var6 = (classNT)this.field4659.method7957(); var6 != null; var6 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var6, (byte)-33)) {
            int var4 = var2;
            int var5 = var3;

            while (var5 > var6.field4722 * 1837314861) {
               this.method7865(var6, var1, var4, var6.field4722 * 27244750, var5 + var4, 388479067);
               var4 += -2001775667 * var6.field4722;
               var5 -= var6.field4722 * 1837314861;
               if (classNU.method8182(this.field4658, var6, var1, var4, var5, (byte)-1)) {
                  continue label34;
               }
            }

            this.method7865(var6, var1, var4, var5, var4 + var5, 115228323);
            var6.field4722 -= 1686279333 * var5;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("am")
   @Override
   protected classAH vmethod44() {
      classNT var1;
      do {
         var1 = (classNT)this.field4659.method7965();
         if (var1 == null) {
            return null;
         }
      } while (null == var1.field4742);

      return var1.field4742;
   }

   @ObfuscatedSignature(descriptor = "(Lnt;[IIIII)V")
   @ObfuscatedName("az")
   void method7865(classNT var1, int[] var2, int var3, int var4, int var5, int var6) {
      try {
         if (0 != (this.field4658.field4748[-1057694491 * var1.field4732] & 4)) {
            if (var6 == 492762249) {
               throw new IllegalStateException();
            }

            if (67131625 * var1.field4727 < 0) {
               if (var6 == 492762249) {
                  throw new IllegalStateException();
               }

               int var7 = this.field4658.field4751[-1057694491 * var1.field4732] / (675076789 * WidgetConfigNode.field4136);

               while (true) {
                  int var8 = (1048575 + var7 - var1.field4730 * -199578021) / var7;
                  if (var8 > var4) {
                     var1.field4730 += var4 * var7 * 1964954067;
                     break;
                  }

                  var1.field4742.vmethod30(var2, var3, var8);
                  var3 += var8;
                  var4 -= var8;
                  var1.field4730 += (var7 * var8 - 1048576) * 1964954067;
                  int var9 = 675076789 * WidgetConfigNode.field4136 / 100;
                  int var10 = 262144 / var7;
                  if (var10 < var9) {
                     if (var6 == 492762249) {
                        throw new IllegalStateException();
                     }

                     var9 = var10;
                  }

                  classAM var11 = var1.field4742;
                  if (this.field4658.field4750[-1057694491 * var1.field4732] == 0) {
                     if (var6 == 492762249) {
                        throw new IllegalStateException();
                     }

                     var1.field4742 = classAM.method472(var1.field4741, var11.method585(), var11.method552(), var11.method560());
                  } else {
                     var1.field4742 = classAM.method472(var1.field4741, var11.method585(), 0, var11.method560());
                     boolean var10002;
                     if (var1.field4744.field4705[var1.field4729 * -107170957] < 0) {
                        if (var6 == 492762249) {
                           throw new IllegalStateException();
                        }

                        var10002 = true;
                     } else {
                        var10002 = false;
                     }

                     this.field4658.method8107(var1, var10002, 881975798);
                     var1.field4742.method573(var9, var11.method552());
                  }

                  if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
                     var1.field4742.method541(-1);
                  }

                  var11.method583(var9);
                  var11.vmethod30(var2, var3, var5 - var3);
                  if (var11.method595()) {
                     if (var6 == 492762249) {
                        throw new IllegalStateException();
                     }

                     this.field4660.method618(var11);
                  }
               }
            }
         }

         var1.field4742.vmethod30(var2, var3, var4);
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "nd.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Ltw;")
   @ObfuscatedName("ag")
   public static classTW[] method7863(int var0) {
      try {
         return new classTW[]{classTW.field6238, classTW.field6241, classTW.field6239, classTW.field6240};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "nd.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("an")
   @Override
   protected classAH vmethod33() {
      classNT var1;
      do {
         var1 = (classNT)this.field4659.method7965();
         if (var1 == null) {
            return null;
         }
      } while (null == var1.field4742);

      return var1.field4742;
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("av")
   @Override
   protected classAH vmethod43() {
      classNT var1 = (classNT)this.field4659.method7957();
      if (var1 == null) {
         return null;
      } else {
         return (classAH)(null != var1.field4742 ? var1.field4742 : this.vmethod35());
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnd;Lnt;I)V")
   @ObfuscatedName("bu")
   public static void method7870(classND var0, classNT var1, int var2) {
      if (0 != (var0.field4658.field4748[var1.field4732 * -1057694491] & 4) && var1.field4727 * 67131625 < 0) {
         int var3 = var0.field4658.field4751[-1057694491 * var1.field4732] / (WidgetConfigNode.field4136 * 675076789);
         int var4 = (1048575 + var3 - -199578021 * var1.field4730) / var3;
         var1.field4730 = (var2 * var3 + var1.field4730 * -199578021 & 1048575) * 1964954067;
         if (var4 <= var2) {
            if (0 == var0.field4658.field4750[var1.field4732 * -1057694491]) {
               var1.field4742 = classAM.method472(var1.field4741, var1.field4742.method585(), var1.field4742.method552(), var1.field4742.method560());
            } else {
               var1.field4742 = classAM.method472(var1.field4741, var1.field4742.method585(), 0, var1.field4742.method560());
               var0.field4658.method8107(var1, var1.field4744.field4705[var1.field4729 * -107170957] < 0, 881975798);
            }

            if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
               var1.field4742.method541(-1);
            }

            var2 = -199578021 * var1.field4730 / var3;
         }
      }

      var1.field4742.vmethod31(var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnd;Lnt;[IIII)V")
   @ObfuscatedName("jw")
   public static void method7866(classND var0, classNT var1, int[] var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method7869(var1, var2, var3, var3, var3);
      }

      if (0 != (var0.field4658.field4748[-1057694491 * var1.field4732] & 4) && 67131625 * var1.field4727 < 0) {
         int var6 = var0.field4658.field4751[-1057694491 * var1.field4732] / (675076789 * WidgetConfigNode.field4136);

         while (true) {
            int var7 = (1048575 + var6 - var1.field4730 * -199578021) / var6;
            if (var7 > var4) {
               var1.field4730 += var4 * var6 * 1964954067;
               break;
            }

            var1.field4742.vmethod30(var2, var3, var7);
            var3 += var7;
            var4 -= var7;
            var1.field4730 += (var6 * var7 - 1048576) * 1964954067;
            int var8 = 675076789 * WidgetConfigNode.field4136 / 100;
            int var9 = 262144 / var6;
            if (var9 < var8) {
               var8 = var9;
            }

            classAM var10 = var1.field4742;
            if (var0.field4658.field4750[-1057694491 * var1.field4732] == 0) {
               var1.field4742 = classAM.method472(var1.field4741, var10.method585(), var10.method552(), var10.method560());
            } else {
               var1.field4742 = classAM.method472(var1.field4741, var10.method585(), 0, var10.method560());
               var0.field4658.method8107(var1, var1.field4744.field4705[var1.field4729 * -107170957] < 0, 881975798);
               var1.field4742.method573(var8, var10.method552());
            }

            if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
               var1.field4742.method541(-1);
            }

            var10.method583(var8);
            var10.vmethod30(var2, var3, var5 - var3);
            if (var10.method595()) {
               var0.field4660.method618(var10);
            }
         }
      }

      var1.field4742.vmethod30(var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lnt;II)V")
   @ObfuscatedName("af")
   void method7871(classNT var1, int var2, int var3) {
      try {
         if (0 != (this.field4658.field4748[var1.field4732 * -1057694491] & 4)) {
            if (var3 >= -462290098) {
               throw new IllegalStateException();
            }

            if (var1.field4727 * 67131625 < 0) {
               if (var3 >= -462290098) {
                  throw new IllegalStateException();
               }

               int var4 = this.field4658.field4751[-1057694491 * var1.field4732] / (WidgetConfigNode.field4136 * 675076789);
               int var5 = (1048575 + var4 - -199578021 * var1.field4730) / var4;
               var1.field4730 = (var2 * var4 + var1.field4730 * -199578021 & 1048575) * 1964954067;
               if (var5 <= var2) {
                  if (var3 >= -462290098) {
                     throw new IllegalStateException();
                  }

                  if (0 == this.field4658.field4750[var1.field4732 * -1057694491]) {
                     if (var3 >= -462290098) {
                        return;
                     }

                     var1.field4742 = classAM.method472(var1.field4741, var1.field4742.method585(), var1.field4742.method552(), var1.field4742.method560());
                  } else {
                     var1.field4742 = classAM.method472(var1.field4741, var1.field4742.method585(), 0, var1.field4742.method560());
                     this.field4658.method8107(var1, var1.field4744.field4705[var1.field4729 * -107170957] < 0, 881975798);
                  }

                  if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
                     if (var3 >= -462290098) {
                        throw new IllegalStateException();
                     }

                     var1.field4742.method541(-1);
                  }

                  var2 = -199578021 * var1.field4730 / var4;
               }
            }
         }

         var1.field4742.vmethod31(var2);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "nd.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("at")
   @Override
   protected classAH vmethod27() {
      classNT var1 = (classNT)this.field4659.method7957();
      if (var1 == null) {
         return null;
      } else {
         return (classAH)(null != var1.field4742 ? var1.field4742 : this.vmethod35());
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bx")
   @Override
   protected void vmethod36(int[] var1, int var2, int var3) {
      this.field4660.vmethod30(var1, var2, var3);

      label32:
      for (classNT var6 = (classNT)this.field4659.method7957(); var6 != null; var6 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var6, (byte)-37)) {
            int var4 = var2;
            int var5 = var3;

            while (var5 > var6.field4722 * 1837314861) {
               this.method7865(var6, var1, var4, var6.field4722 * 1837314861, var5 + var4, 25977313);
               var4 += 1837314861 * var6.field4722;
               var5 -= var6.field4722 * 1837314861;
               if (classNU.method8182(this.field4658, var6, var1, var4, var5, (byte)-1)) {
                  continue label32;
               }
            }

            this.method7865(var6, var1, var4, var5, var4 + var5, -872285380);
            var6.field4722 -= 1686279333 * var5;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   @Override
   protected int vmethod28() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("bp")
   @Override
   protected void vmethod38(int[] var1, int var2, int var3) {
      this.field4660.vmethod30(var1, var2, var3);

      label35:
      for (classNT var6 = (classNT)this.field4659.method7957(); var6 != null; var6 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var6, (byte)-38)) {
            int var4 = var2;
            int var5 = var3;

            while (var5 > var6.field4722 * -1791738241) {
               this.method7865(var6, var1, var4, var6.field4722 * 187874848, var5 + var4, 1166025627);
               var4 += 1837314861 * var6.field4722;
               var5 -= var6.field4722 * -576527061;
               if (classNU.method8182(this.field4658, var6, var1, var4, var5, (byte)-1)) {
                  continue label35;
               }
            }

            this.method7865(var6, var1, var4, var5, var4 + var5, 513453450);
            var6.field4722 -= -2055628404 * var5;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([III)V")
   @ObfuscatedName("br")
   @Override
   protected void vmethod39(int[] var1, int var2, int var3) {
      this.field4660.vmethod30(var1, var2, var3);

      label34:
      for (classNT var6 = (classNT)this.field4659.method7957(); var6 != null; var6 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var6, (byte)-9)) {
            int var4 = var2;
            int var5 = var3;

            while (var5 > var6.field4722 * 1837314861) {
               this.method7865(var6, var1, var4, var6.field4722 * 2049925664, var5 + var4, -1813298187);
               var4 += 1837314861 * var6.field4722;
               var5 -= var6.field4722 * -944929533;
               if (classNU.method8182(this.field4658, var6, var1, var4, var5, (byte)-1)) {
                  continue label34;
               }
            }

            this.method7865(var6, var1, var4, var5, var4 + var5, -957921784);
            var6.field4722 -= 1686279333 * var5;
         }
      }
   }

   classND(classNU var1) {
      this.field4660 = new classAO();
      this.field4658 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   @Override
   protected void vmethod41(int var1) {
      this.field4660.vmethod31(var1);

      label31:
      for (classNT var3 = (classNT)this.field4659.method7957(); var3 != null; var3 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var3, (byte)-126)) {
            int var2 = var1;

            while (var2 > var3.field4722 * -235769878) {
               this.method7871(var3, var3.field4722 * 719559946, -581445385);
               var2 -= 1837314861 * var3.field4722;
               if (classNU.method8182(this.field4658, var3, null, 0, var2, (byte)-1)) {
                  continue label31;
               }
            }

            this.method7871(var3, var2, -960719055);
            var3.field4722 -= 1686279333 * var2;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnd;Lnt;I)V")
   @ObfuscatedName("mi")
   public static void method7872(classND var0, classNT var1, int var2) {
      if (0 != (var0.field4658.field4748[var1.field4732 * -1057694491] & 4) && var1.field4727 * 1600234656 < 0) {
         int var3 = var0.field4658.field4751[813079663 * var1.field4732] / (WidgetConfigNode.field4136 * 507540756);
         int var4 = (1048575 + var3 - -199578021 * var1.field4730) / var3;
         var1.field4730 = (var2 * var3 + var1.field4730 * -199578021 & -2023934965) * 1964954067;
         if (var4 <= var2) {
            if (0 == var0.field4658.field4750[var1.field4732 * -1057694491]) {
               var1.field4742 = classAM.method472(var1.field4741, var1.field4742.method585(), var1.field4742.method552(), var1.field4742.method560());
            } else {
               var1.field4742 = classAM.method472(var1.field4741, var1.field4742.method585(), 0, var1.field4742.method560());
               var0.field4658.method8107(var1, var1.field4744.field4705[var1.field4729 * -484735148] < 0, 881975798);
            }

            if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
               var1.field4742.method541(-1);
            }

            var2 = 1998654390 * var1.field4730 / var3;
         }
      }

      var1.field4742.vmethod31(var2);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   @Override
   protected void vmethod42(int var1) {
      this.field4660.vmethod31(var1);

      label32:
      for (classNT var3 = (classNT)this.field4659.method7957(); var3 != null; var3 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var3, (byte)-117)) {
            int var2 = var1;

            while (var2 > var3.field4722 * 1837314861) {
               this.method7871(var3, var3.field4722 * -1152341972, -515700187);
               var2 -= -1600987433 * var3.field4722;
               if (classNU.method8182(this.field4658, var3, null, 0, var2, (byte)-1)) {
                  continue label32;
               }
            }

            this.method7871(var3, var2, -1872843376);
            var3.field4722 -= 1042002479 * var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;[IIII)V")
   @ObfuscatedName("ae")
   void method7867(classNT var1, int[] var2, int var3, int var4, int var5) {
      if (0 != (this.field4658.field4748[-1057694491 * var1.field4732] & 4) && 67131625 * var1.field4727 < 0) {
         int var6 = this.field4658.field4751[-1057694491 * var1.field4732] / (675076789 * WidgetConfigNode.field4136);

         while (true) {
            int var7 = (1048575 + var6 - var1.field4730 * -199578021) / var6;
            if (var7 > var4) {
               var1.field4730 += var4 * var6 * 1964954067;
               break;
            }

            var1.field4742.vmethod30(var2, var3, var7);
            var3 += var7;
            var4 -= var7;
            var1.field4730 += (var6 * var7 - 1048576) * 1964954067;
            int var8 = 675076789 * WidgetConfigNode.field4136 / 100;
            int var9 = 262144 / var6;
            if (var9 < var8) {
               var8 = var9;
            }

            classAM var10 = var1.field4742;
            if (this.field4658.field4750[-1057694491 * var1.field4732] == 0) {
               var1.field4742 = classAM.method472(var1.field4741, var10.method585(), var10.method552(), var10.method560());
            } else {
               var1.field4742 = classAM.method472(var1.field4741, var10.method585(), 0, var10.method560());
               this.field4658.method8107(var1, var1.field4744.field4705[var1.field4729 * -107170957] < 0, 881975798);
               var1.field4742.method573(var8, var10.method552());
            }

            if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
               var1.field4742.method541(-1);
            }

            var10.method583(var8);
            var10.vmethod30(var2, var3, var5 - var3);
            if (var10.method595()) {
               this.field4660.method618(var10);
            }
         }
      }

      var1.field4742.vmethod30(var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Lnt;[IIII)V")
   @ObfuscatedName("ab")
   void method7868(classNT var1, int[] var2, int var3, int var4, int var5) {
      if (0 != (this.field4658.field4748[-1057694491 * var1.field4732] & 4) && 67131625 * var1.field4727 < 0) {
         int var6 = this.field4658.field4751[-1057694491 * var1.field4732] / (675076789 * WidgetConfigNode.field4136);

         while (true) {
            int var7 = (1048575 + var6 - var1.field4730 * -199578021) / var6;
            if (var7 > var4) {
               var1.field4730 += var4 * var6 * 1964954067;
               break;
            }

            var1.field4742.vmethod30(var2, var3, var7);
            var3 += var7;
            var4 -= var7;
            var1.field4730 += (var6 * var7 - 1048576) * 1964954067;
            int var8 = 675076789 * WidgetConfigNode.field4136 / 100;
            int var9 = 262144 / var6;
            if (var9 < var8) {
               var8 = var9;
            }

            classAM var10 = var1.field4742;
            if (this.field4658.field4750[-1057694491 * var1.field4732] == 0) {
               var1.field4742 = classAM.method472(var1.field4741, var10.method585(), var10.method552(), var10.method560());
            } else {
               var1.field4742 = classAM.method472(var1.field4741, var10.method585(), 0, var10.method560());
               this.field4658.method8107(var1, var1.field4744.field4705[var1.field4729 * -107170957] < 0, 881975798);
               var1.field4742.method573(var8, var10.method552());
            }

            if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
               var1.field4742.method541(-1);
            }

            var10.method583(var8);
            var10.vmethod30(var2, var3, var5 - var3);
            if (var10.method595()) {
               this.field4660.method618(var10);
            }
         }
      }

      var1.field4742.vmethod30(var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "()Lah;")
   @ObfuscatedName("ah")
   @Override
   protected classAH vmethod34() {
      classNT var1;
      do {
         var1 = (classNT)this.field4659.method7965();
         if (var1 == null) {
            return null;
         }
      } while (null == var1.field4742);

      return var1.field4742;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)Ljava/lang/String;")
   @ObfuscatedName("ae")
   public static String method7864(classXY var0, int var1) {
      try {
         String var2;
         try {
            int var3 = var0.method13150((short)13674);
            if (var3 > 32767) {
               if (var1 == 342471231) {
                  throw new IllegalStateException();
               }

               var3 = 32767;
            }

            byte[] var4 = new byte[var3];
            var0.field6953 = var0.field6953 + classLF.field4131.method7192(var0.field6952, 702114061 * var0.field6953, var4, 0, var3, 2023506199) * -1095856699;
            String var5 = classKQ.method6590(var4, 0, var3, (byte)-23);
            var2 = var5;
         } catch (Exception var6) {
            var2 = "Cabbage";
         }

         return var2;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "nd.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   @Override
   protected void vmethod40(int var1) {
      this.field4660.vmethod31(var1);

      label30:
      for (classNT var3 = (classNT)this.field4659.method7957(); var3 != null; var3 = (classNT)this.field4659.method7965()) {
         if (!classNU.method8180(this.field4658, var3, (byte)-95)) {
            int var2 = var1;

            while (var2 > var3.field4722 * 1837314861) {
               this.method7871(var3, var3.field4722 * 1837314861, -826205370);
               var2 -= 22964488 * var3.field4722;
               if (classNU.method8182(this.field4658, var3, null, 0, var2, (byte)-1)) {
                  continue label30;
               }
            }

            this.method7871(var3, var2, -1320390668);
            var3.field4722 -= 1686279333 * var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnt;[IIII)V")
   @ObfuscatedName("ag")
   void method7869(classNT var1, int[] var2, int var3, int var4, int var5) {
      if (0 != (this.field4658.field4753[-1057694491 * var1.field4737] & 4) && 67131625 * var1.field4720 < 0) {
         int var6 = this.field4658.field4750[-1057694491 * var1.field4721] / (675076789 * WidgetConfigNode.field4136);

         while (true) {
            int var7 = (1048575 + var6 - var1.field4725 * -199578021) / var6;
            if (var7 > var4) {
               var1.field4725 += var4 * var6 * 1964954067;
               break;
            }

            var1.field4742.vmethod30(var2, var3, var7);
            var3 += var7;
            var4 -= var7;
            var1.field4725 = var1.field4737 + (var6 * var7 - 1048576) * 1964954067;
            int var8 = 675076789 * WidgetConfigNode.field4136 / 100;
            int var9 = 262144 / var6;
            if (var9 < var8) {
               var8 = var9;
            }

            classAM var10 = var1.field4742;
            if (this.field4658.field4771[-1057694491 * var1.field4725] == 0) {
               var1.field4742 = classAM.method472(var1.field4741, var10.method560(), var10.method586(), var10.method561());
            } else {
               var1.field4742 = classAM.method474(var1.field4741, var10.method554(), 0, var10.method563());
               this.field4658.method8107(var1, var1.field4744.field4705[var1.field4735 * -107170957] < 0, 881975798);
               var1.field4742.method575(var8, var10.method586());
            }

            if (var1.field4744.field4705[-107170957 * var1.field4729] < 0) {
               var1.field4742.method588(-1);
            }

            var10.method588(var8);
            var10.vmethod30(var2, var3, var5 - var3);
            if (var10.method593()) {
               this.field4660.method619(var10);
            }
         }
      }

      var1.field4742.vmethod30(var2, var3, var4);
   }
}
