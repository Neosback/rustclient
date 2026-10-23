import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bm")
public class classBM {
   @ObfuscatedSignature(descriptor = "Lay;")
   @ObfuscatedName("az")
   classAY field507;
   @ObfuscatedName("af")
   boolean field509;
   @ObfuscatedName("ab")
   int[] field510;
   @ObfuscatedName("ae")
   int[] field506;
   @ObfuscatedName("ag")
   boolean[] field508;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("cq")
   static IndexedSprite[] field514;
   @ObfuscatedSignature(descriptor = "Liq;")
   @ObfuscatedName("fo")
   static ClanSettings field513;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field512 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field511 = 39;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   public static void method1162(int var0) {
      try {
         if (null != classTZ.field6257) {
            if (var0 <= -1681080869) {
               throw new IllegalStateException();
            }

            synchronized (classTZ.field6257) {
               classTZ.field6257 = null;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bm.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([FII)V")
   @ObfuscatedName("az")
   void method1156(float[] var1, int var2, int var3) {
      try {
         int var4 = this.field507.field299.length;
         int var5 = classAY.field296[this.field507.field303 - 1];
         boolean[] var6 = this.field508;
         this.field508[1] = true;
         var6[0] = true;

         for (int var7 = 2; var7 < var4; var7++) {
            if (var3 <= 640509623) {
               throw new IllegalStateException();
            }

            int var8 = classAY.method723(this.field507, this.field510, var7);
            int var9 = this.field507.method729(this.field510, var7);
            int var10 = this.field507.method731(this.field510[var8], this.field506[var8], this.field510[var9], this.field506[var9], this.field510[var7]);
            int var11 = this.field506[var7];
            int var12 = var5 - var10;
            int var10000;
            if (var12 < var10) {
               if (var3 <= 640509623) {
                  throw new IllegalStateException();
               }

               var10000 = var12;
            } else {
               var10000 = var10;
            }

            int var14 = var10000 << 1;
            if (var11 != 0) {
               if (var3 <= 640509623) {
                  throw new IllegalStateException();
               }

               boolean[] var15 = this.field508;
               this.field508[var9] = true;
               var15[var8] = true;
               this.field508[var7] = true;
               if (var11 >= var14) {
                  int var10002;
                  if (var12 > var10) {
                     if (var3 <= 640509623) {
                        throw new IllegalStateException();
                     }

                     var10002 = var10 + (var11 - var10);
                  } else {
                     var10002 = var10 - var11 + var12 - 1;
                  }

                  this.field506[var7] = var10002;
               } else {
                  int var25;
                  if ((var11 & 1) != 0) {
                     if (var3 <= 640509623) {
                        throw new IllegalStateException();
                     }

                     var25 = var10 - (1 + var11) / 2;
                  } else {
                     var25 = var11 / 2 + var10;
                  }

                  this.field506[var7] = var25;
               }
            } else {
               this.field508[var7] = false;
               this.field506[var7] = var10;
            }
         }

         this.method1167(0, var4 - 1, 423422923);
         int var18 = 0;
         int var19 = this.field506[0] * this.field507.field303;

         for (int var20 = 1; var20 < var4; var20++) {
            if (var3 <= 640509623) {
               throw new IllegalStateException();
            }

            if (this.field508[var20]) {
               if (var3 <= 640509623) {
                  return;
               }

               int var22 = this.field510[var20];
               int var24 = this.field507.field303 * this.field506[var20];
               this.field507.method734(var18, var19, var22, var24, var1, var2);
               if (var22 >= var2) {
                  return;
               }

               var18 = var22;
               var19 = var24;
            }
         }

         float var21 = classAY.field297[var19];

         for (int var23 = var18; var23 < var2; var23++) {
            if (var3 <= 640509623) {
               throw new IllegalStateException();
            }

            var1[var23] *= var21;
         }
      } catch (RuntimeException var17) {
         throw classEG.method3899(var17, "bm.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("af")
   boolean method1163(int var1) {
      try {
         return this.field509;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bm.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ae")
   void method1167(int var1, int var2, int var3) {
      try {
         if (var1 >= var2) {
            if (var3 > -848354838) {
               ;
            }
         } else {
            int var4 = var1;
            int var5 = this.field510[var1];
            int var6 = this.field506[var1];
            boolean var7 = this.field508[var1];

            for (int var8 = 1 + var1; var8 <= var2; var8++) {
               if (var3 <= -848354838) {
                  throw new IllegalStateException();
               }

               int var9 = this.field510[var8];
               if (var9 < var5) {
                  if (var3 <= -848354838) {
                     throw new IllegalStateException();
                  }

                  this.field510[var4] = var9;
                  this.field506[var4] = this.field506[var8];
                  this.field508[var4] = this.field508[var8];
                  this.field510[var8] = this.field510[++var4];
                  this.field506[var8] = this.field506[var4];
                  this.field508[var8] = this.field508[var4];
               }
            }

            this.field510[var4] = var5;
            this.field506[var4] = var6;
            this.field508[var4] = var7;
            this.method1167(var1, var4 - 1, -640467178);
            this.method1167(var4 + 1, var2, 1853918476);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "bm.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([FI)V")
   @ObfuscatedName("as")
   void method1157(float[] var1, int var2) {
      int var3 = this.field507.field299.length;
      int var4 = classAY.field296[this.field507.field303 - 1];
      boolean[] var5 = this.field508;
      this.field508[1] = true;
      var5[0] = true;

      for (int var6 = 2; var6 < var3; var6++) {
         int var7 = classAY.method723(this.field507, this.field510, var6);
         int var8 = this.field507.method729(this.field510, var6);
         int var9 = this.field507.method731(this.field510[var7], this.field506[var7], this.field510[var8], this.field506[var8], this.field510[var6]);
         int var10 = this.field506[var6];
         int var11 = var4 - var9;
         int var13 = (var11 < var9 ? var11 : var9) << 1;
         if (var10 != 0) {
            boolean[] var14 = this.field508;
            this.field508[var8] = true;
            var14[var7] = true;
            this.field508[var6] = true;
            if (var10 >= var13) {
               this.field506[var6] = var11 > var9 ? var9 + (var10 - var9) : var9 - var10 + var11 - 1;
            } else {
               this.field506[var6] = (var10 & 1) != 0 ? var9 - (1 + var10) / 2 : var10 / 2 + var9;
            }
         } else {
            this.field508[var6] = false;
            this.field506[var6] = var9;
         }
      }

      this.method1167(0, var3 - 1, 2120046685);
      int var16 = 0;
      int var17 = this.field506[0] * this.field507.field303;

      for (int var18 = 1; var18 < var3; var18++) {
         if (this.field508[var18]) {
            int var20 = this.field510[var18];
            int var22 = this.field507.field303 * this.field506[var18];
            this.field507.method734(var16, var17, var20, var22, var1, var2);
            if (var20 >= var2) {
               return;
            }

            var16 = var20;
            var17 = var22;
         }
      }

      float var19 = classAY.field297[var17];

      for (int var21 = var16; var21 < var2; var21++) {
         var1[var21] *= var19;
      }
   }

   @ObfuscatedSignature(descriptor = "([FI)V")
   @ObfuscatedName("ag")
   void method1158(float[] var1, int var2) {
      int var3 = this.field507.field299.length;
      int var4 = classAY.field296[this.field507.field303 - 1];
      boolean[] var5 = this.field508;
      this.field508[1] = true;
      var5[0] = true;

      for (int var6 = 2; var6 < var3; var6++) {
         int var7 = classAY.method723(this.field507, this.field510, var6);
         int var8 = this.field507.method729(this.field510, var6);
         int var9 = this.field507.method731(this.field510[var7], this.field506[var7], this.field510[var8], this.field506[var8], this.field510[var6]);
         int var10 = this.field506[var6];
         int var11 = var4 - var9;
         int var13 = (var11 < var9 ? var11 : var9) << 1;
         if (var10 != 0) {
            boolean[] var14 = this.field508;
            this.field508[var8] = true;
            var14[var7] = true;
            this.field508[var6] = true;
            if (var10 >= var13) {
               this.field506[var6] = var11 > var9 ? var9 + (var10 - var9) : var9 - var10 + var11 - 1;
            } else {
               this.field506[var6] = (var10 & 1) != 0 ? var9 - (1 + var10) / 2 : var10 / 2 + var9;
            }
         } else {
            this.field508[var6] = false;
            this.field506[var6] = var9;
         }
      }

      this.method1167(0, var3 - 1, 312315207);
      int var16 = 0;
      int var17 = this.field506[0] * this.field507.field303;

      for (int var18 = 1; var18 < var3; var18++) {
         if (this.field508[var18]) {
            int var20 = this.field510[var18];
            int var22 = this.field507.field303 * this.field506[var18];
            this.field507.method734(var16, var17, var20, var22, var1, var2);
            if (var20 >= var2) {
               return;
            }

            var16 = var20;
            var17 = var22;
         }
      }

      float var19 = classAY.field297[var17];

      for (int var21 = var16; var21 < var2; var21++) {
         var1[var21] *= var19;
      }
   }

   @ObfuscatedSignature(descriptor = "([FI)V")
   @ObfuscatedName("ab")
   void method1159(float[] var1, int var2) {
      int var3 = this.field507.field299.length;
      int var4 = classAY.field296[this.field507.field303 - 1];
      boolean[] var5 = this.field508;
      this.field508[1] = true;
      var5[0] = true;

      for (int var6 = 2; var6 < var3; var6++) {
         int var7 = classAY.method723(this.field507, this.field510, var6);
         int var8 = this.field507.method729(this.field510, var6);
         int var9 = this.field507.method731(this.field510[var7], this.field506[var7], this.field510[var8], this.field506[var8], this.field510[var6]);
         int var10 = this.field506[var6];
         int var11 = var4 - var9;
         int var13 = (var11 < var9 ? var11 : var9) << 1;
         if (var10 != 0) {
            boolean[] var14 = this.field508;
            this.field508[var8] = true;
            var14[var7] = true;
            this.field508[var6] = true;
            if (var10 >= var13) {
               this.field506[var6] = var11 > var9 ? var9 + (var10 - var9) : var9 - var10 + var11 - 1;
            } else {
               this.field506[var6] = (var10 & 1) != 0 ? var9 - (1 + var10) / 2 : var10 / 2 + var9;
            }
         } else {
            this.field508[var6] = false;
            this.field506[var6] = var9;
         }
      }

      this.method1167(0, var3 - 1, 1869797008);
      int var16 = 0;
      int var17 = this.field506[0] * this.field507.field303;

      for (int var18 = 1; var18 < var3; var18++) {
         if (this.field508[var18]) {
            int var20 = this.field510[var18];
            int var22 = this.field507.field303 * this.field506[var18];
            this.field507.method734(var16, var17, var20, var22, var1, var2);
            if (var20 >= var2) {
               return;
            }

            var16 = var20;
            var17 = var22;
         }
      }

      float var19 = classAY.field297[var17];

      for (int var21 = var16; var21 < var2; var21++) {
         var1[var21] *= var19;
      }
   }

   @ObfuscatedSignature(descriptor = "([FI)V")
   @ObfuscatedName("ax")
   void method1160(float[] var1, int var2) {
      int var3 = this.field507.field299.length;
      int var4 = classAY.field296[this.field507.field303 - 1];
      boolean[] var5 = this.field508;
      this.field508[1] = true;
      var5[0] = true;

      for (int var6 = 2; var6 < var3; var6++) {
         int var7 = classAY.method723(this.field507, this.field510, var6);
         int var8 = this.field507.method729(this.field510, var6);
         int var9 = this.field507.method731(this.field510[var7], this.field506[var7], this.field510[var8], this.field506[var8], this.field510[var6]);
         int var10 = this.field506[var6];
         int var11 = var4 - var9;
         int var13 = (var11 < var9 ? var11 : var9) << 1;
         if (var10 != 0) {
            boolean[] var14 = this.field508;
            this.field508[var8] = true;
            var14[var7] = true;
            this.field508[var6] = true;
            if (var10 >= var13) {
               this.field506[var6] = var11 > var9 ? var9 + (var10 - var9) : var9 - var10 + var11 - 1;
            } else {
               this.field506[var6] = (var10 & 1) != 0 ? var9 - (1 + var10) / 2 : var10 / 2 + var9;
            }
         } else {
            this.field508[var6] = false;
            this.field506[var6] = var9;
         }
      }

      this.method1167(0, var3 - 1, 755914435);
      int var16 = 0;
      int var17 = this.field506[0] * this.field507.field303;

      for (int var18 = 1; var18 < var3; var18++) {
         if (this.field508[var18]) {
            int var20 = this.field510[var18];
            int var22 = this.field507.field303 * this.field506[var18];
            this.field507.method734(var16, var17, var20, var22, var1, var2);
            if (var20 >= var2) {
               return;
            }

            var16 = var20;
            var17 = var22;
         }
      }

      float var19 = classAY.field297[var17];

      for (int var21 = var16; var21 < var2; var21++) {
         var1[var21] *= var19;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   boolean method1164() {
      return this.field509;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aa")
   boolean method1165() {
      return this.field509;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ao")
   boolean method1166() {
      return this.field509;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("al")
   void method1168(int var1, int var2) {
      if (var1 < var2) {
         int var3 = var1;
         int var4 = this.field510[var1];
         int var5 = this.field506[var1];
         boolean var6 = this.field508[var1];

         for (int var7 = 1 + var1; var7 <= var2; var7++) {
            int var8 = this.field510[var7];
            if (var8 < var4) {
               this.field510[var3] = var8;
               this.field506[var3] = this.field506[var7];
               this.field508[var3] = this.field508[var7];
               this.field510[var7] = this.field510[++var3];
               this.field506[var7] = this.field506[var3];
               this.field508[var7] = this.field508[var3];
            }
         }

         this.field510[var3] = var4;
         this.field506[var3] = var5;
         this.field508[var3] = var6;
         this.method1167(var1, var3 - 1, 562975092);
         this.method1167(var3 + 1, var2, 1552784738);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llg;IIIIB)Llg;")
   @ObfuscatedName("ae")
   public static WidgetConfigNode method1161(WidgetConfigNode var0, int var1, int var2, int var3, int var4, byte var5) {
      try {
         WidgetConfigNode var6 = var0;

         for (WidgetConfigNode var7 = null; null != var6; var6 = var6.field4138) {
            if (-1057754721 * var6.field4134 >= var1) {
               if (var5 >= -1) {
                  throw new IllegalStateException();
               }

               if (1443675333 * var6.field4132 <= var2) {
                  if (var5 >= -1) {
                     throw new IllegalStateException();
                  }

                  if (null != var7) {
                     var7.field4138 = var6.field4138;
                  } else {
                     var0 = var6.field4138;
                  }

                  if (var6.field4132 * 1443675333 < var1) {
                     if (var5 >= -1) {
                        throw new IllegalStateException();
                     }

                     WidgetConfigNode var8 = new WidgetConfigNode(
                        var6.field4132 * 1443675333, var1 - 1, var6.field4135 * -802457829, 123180743 * var6.field4133
                     );
                     if (var7 != null) {
                        if (var5 >= -1) {
                           throw new IllegalStateException();
                        }

                        var8.field4138 = var7.field4138;
                     } else {
                        var8.field4138 = var0;
                     }

                     if (null != var7) {
                        if (var5 >= -1) {
                           throw new IllegalStateException();
                        }

                        var7.field4138 = var8;
                     } else {
                        var0 = var8;
                     }

                     var7 = var8;
                  }

                  if (var6.field4134 * -1057754721 > var2) {
                     if (var5 >= -1) {
                        throw new IllegalStateException();
                     }

                     WidgetConfigNode var13 = new WidgetConfigNode(
                        var2 + 1, var6.field4134 * -1057754721, var6.field4135 * -802457829, var6.field4133 * 123180743
                     );
                     if (var7 != null) {
                        if (var5 >= -1) {
                           throw new IllegalStateException();
                        }

                        var13.field4138 = var7.field4138;
                     } else {
                        var13.field4138 = var0;
                     }

                     if (null != var7) {
                        if (var5 >= -1) {
                           throw new IllegalStateException();
                        }

                        var7.field4138 = var13;
                     } else {
                        var0 = var13;
                     }
                  }
               }
            }

            var7 = var6;
         }

         var6 = null;

         for (WidgetConfigNode var11 = var0; null != var11; var11 = var11.field4138) {
            if (var5 >= -1) {
               throw new IllegalStateException();
            }

            if (var11.field4132 * 1443675333 > var1) {
               break;
            }

            var6 = var11;
         }

         WidgetConfigNode var12 = new WidgetConfigNode(var1, var2, var3, var4);
         if (var6 != null) {
            if (var5 >= -1) {
               throw new IllegalStateException();
            }

            var12.field4138 = var6.field4138;
         } else {
            var12.field4138 = var0;
         }

         if (var6 != null) {
            if (var5 >= -1) {
               throw new IllegalStateException();
            }

            var6.field4138 = var12;
         } else {
            var0 = var12;
         }

         return var0;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "bm.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Llj;")
   @ObfuscatedName("ag")
   public static classLJ[] method1155(int var0) {
      try {
         return new classLJ[]{classLJ.field4175, classLJ.field4172, classLJ.field4174, classLJ.field4173};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "bm.ag(" + ')');
      }
   }

   classBM(classAY var1, boolean var2, int[] var3, int[] var4, boolean[] var5) {
      this.field507 = var1;
      this.field509 = var2;
      this.field510 = var3;
      this.field506 = var4;
      this.field508 = var5;
   }
}
