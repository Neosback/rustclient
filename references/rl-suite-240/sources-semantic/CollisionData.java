import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gc")
public class CollisionData implements net.runelite.api.CollisionData {
   @ObfuscatedName("bb")
   final int[][] field2548;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2549 = 8;
   @ObfuscatedName("bo")
   int field2546;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   public static final int field2550 = 1073741824;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field2558 = 1076101438;
   @ObfuscatedName("bu")
   int field2556;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2554 = 1;
   @ObfuscatedName("bw")
   int field2557;
   @ObfuscatedName("ba")
   int field2547;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2553 = 32;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   public static final int field2551 = 15;
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   static final int field2555 = 50;
   @ToRemove(unused = "true")
   @ObfuscatedName("cx")
   static final int field2559 = -2;
   @ObfuscatedSignature(descriptor = "Luk;")
   @ObfuscatedName("hg")
   static classUK field2560;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field2552 = 16;

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ao")
   public void method5371(int var1, int var2, int var3, int var4) {
      try {
         method5367(this, var1 - this.field2556 * 370701143, var2 - this.field2557 * 1140151217, var3, (byte)-45);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gc.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIZB)V")
   @ObfuscatedName("ar")
   public void method5408(int var1, int var2, int var3, int var4, int var5, boolean var6, byte var7) {
      try {
         int var8 = 256;
         if (var6) {
            if (var7 <= 1) {
               throw new IllegalStateException();
            }

            var8 += 131072;
         }

         label79: {
            var1 -= this.field2556 * 370701143;
            var2 -= 1140151217 * this.field2557;
            if (var5 != 1) {
               if (var7 <= 1) {
                  return;
               }

               if (3 != var5) {
                  break label79;
               }

               if (var7 <= 1) {
                  throw new IllegalStateException();
               }
            }

            int var9 = var3;
            var3 = var4;
            var4 = var9;
         }

         for (int var14 = var1; var14 < var1 + var3; var14++) {
            if (var7 <= 1) {
               return;
            }

            if (var14 >= 0 && var14 < this.field2546 * -148155585) {
               if (var7 <= 1) {
                  return;
               }

               for (int var10 = var2; var10 < var4 + var2; var10++) {
                  if (var7 <= 1) {
                     return;
                  }

                  if (var10 >= 0 && var10 < this.field2547 * 1473114359) {
                     if (var7 <= 1) {
                        throw new IllegalStateException();
                     }

                     method5367(this, var14, var10, var8, (byte)-124);
                  }
               }
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gc.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIZ)V")
   @ObfuscatedName("cn")
   public void method5409(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      int var7 = 256;
      if (var6) {
         var7 += 131072;
      }

      var1 -= this.field2556 * -49641372;
      var2 -= -63844124 * this.field2557;
      if (var5 == 1 || 3 == var5) {
         int var8 = var3;
         var3 = var4;
         var4 = var8;
      }

      for (int var12 = var1; var12 < var1 + var3; var12++) {
         if (var12 >= 0 && var12 < this.field2546 * -148155585) {
            for (int var9 = var2; var9 < var4 + var2; var9++) {
               if (var9 >= 0 && var9 < this.field2547 * 1473114359) {
                  method5367(this, var12, var9, var7, (byte)-25);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ck")
   public void method5398(int var1, int var2) {
      var1 -= this.field2556 * 682520820;
      var2 -= this.field2557 * 1140151217;
      this.field2548[var1][var2] = this.field2548[var1][var2] | -1045931674;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bw")
   void method5361(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] | var3;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("co")
   public void method5399(int var1, int var2) {
      var1 -= this.field2556 * 370701143;
      var2 -= this.field2557 * 1140151217;
      this.field2548[var1][var2] = this.field2548[var1][var2] | 262144;
   }

   @ObfuscatedSignature(descriptor = "(IIIB)Z")
   @ObfuscatedName("ax")
   boolean method5358(int var1, int var2, int var3, byte var4) {
      try {
         return 0 != (this.field2548[var1][var2] & var3);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gc.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ac")
   void method5362(int var1, int var2, int var3, int var4) {
      try {
         this.field2548[var1][var2] = this.field2548[var1][var2] | var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gc.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ew")
   public boolean method5414(int var1, int var2) {
      return this.method5375(var1, var2, (byte)-108);
   }

   @ObfuscatedSignature(descriptor = "(IIB)Z")
   @ObfuscatedName("al")
   public boolean method5375(int var1, int var2, byte var3) {
      try {
         return method5378(this, var1 - 370701143 * this.field2556, var2 - this.field2557 * 1140151217, (byte)-4);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgc;I)I")
   @ObfuscatedName("ef")
   public static int method5343(CollisionData var0, int var1) {
      if (var0 == null) {
         var0.method5346(var1);
      }

      try {
         return var0.field2547 * 1473114359;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gc.ab(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;II)V")
   @ObfuscatedName("hx")
   public static void method5394(CollisionData var0, int var1, int var2) {
      if (var0 == null) {
         var0.method5403(var1, var1);
      }

      var1 -= var0.field2556 * 370701143;
      var2 -= var0.field2557 * 1140151217;
      var0.field2548[var1][var2] = var0.field2548[var1][var2] | 2097152;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;III)V")
   @ObfuscatedName("pt")
   public static void method5372(CollisionData var0, int var1, int var2, int var3) {
      method5367(var0, var1 - var0.field2556 * 370701143, var2 - var0.field2557 * 1140151217, var3, (byte)-77);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIZI)V")
   @ObfuscatedName("aq")
   void method5386(int var1, int var2, int var3, int var4, int var5, boolean var6, int var7) {
      try {
         int var8 = 256;
         if (var6) {
            if (var7 >= -1713240564) {
               throw new IllegalStateException();
            }

            var8 += 131072;
         }

         label75: {
            var1 -= this.field2556 * 370701143;
            var2 -= this.field2557 * 1140151217;
            if (1 != var5) {
               if (3 != var5) {
                  break label75;
               }

               if (var7 >= -1713240564) {
                  return;
               }
            }

            int var9 = var3;
            var3 = var4;
            var4 = var9;
         }

         for (int var14 = var1; var14 < var1 + var3; var14++) {
            if (var7 >= -1713240564) {
               throw new IllegalStateException();
            }

            if (var14 >= 0) {
               if (var7 >= -1713240564) {
                  throw new IllegalStateException();
               }

               if (var14 < this.field2546 * -148155585) {
                  for (int var10 = var2; var10 < var4 + var2; var10++) {
                     if (var7 >= -1713240564) {
                        throw new IllegalStateException();
                     }

                     if (var10 >= 0 && var10 < 1473114359 * this.field2547) {
                        if (var7 >= -1713240564) {
                           throw new IllegalStateException();
                        }

                        this.method5362(var14, var10, var8, -1841167818);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "gc.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   int method5335(int var1) {
      try {
         return 370701143 * this.field2556;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gc.az(" + 41);
      }
   }

   public int[][] getFlags() {
      return this.field2548;
   }

   @ObfuscatedSignature(descriptor = "(IIIIZB)V")
   @ObfuscatedName("ad")
   public void method5389(int var1, int var2, int var3, int var4, boolean var5, byte var6) {
      try {
         this.method5386(var1, var2, var3, var4, 0, var5, -1837603788);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gc.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("cp")
   public void method5390(int var1, int var2, int var3, int var4, boolean var5) {
      this.method5386(var1, var2, var3, var4, 0, var5, -2069477590);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bx")
   final void method5347(int var1, int var2, int var3, int var4) {
      this.field2556 = 1954293863 * var1;
      this.field2557 = -143452847 * var2;
      this.field2546 = var3 * 1273896127;
      this.field2547 = var4 * 380096711;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("aw")
   public void method5411(int var1, int var2, int var3) {
      try {
         var1 -= this.field2556 * 370701143;
         var2 -= this.field2557 * 1140151217;
         this.field2548[var1][var2] = this.field2548[var1][var2] & -262145;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   int method5336() {
      return -1942466721 * this.field2556;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("be")
   void method5366(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] & ~var3;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   int method5338() {
      return this.field2557 * -1515697340;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   public void method5353() {
      for (int var1 = 0; var1 < -148155585 * this.field2546; var1++) {
         for (int var2 = 0; var2 < this.field2547 * 1473114359; var2++) {
            if (var1 >= 1 && var2 >= 1 && var1 < this.field2546 * -148155585 - 5 && var2 < this.field2547 * 1473114359 - 5) {
               this.field2548[var1][var2] = 1073741824;
            } else {
               this.field2548[var1][var2] = 16777215;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   public int method5340() {
      return -148155585 * this.field2546;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public int method5344() {
      return this.field2547 * 1473114359;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   public int method5345() {
      return this.field2547 * -1930950539;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cv")
   public void method5395(int var1, int var2) {
      var1 -= this.field2556 * 1305017015;
      var2 -= this.field2557 * 325544783;
      this.field2548[var1][var2] = this.field2548[var1][var2] | 2097152;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bc")
   final void method5348(int var1, int var2, int var3, int var4) {
      this.field2556 = 1954293863 * var1;
      this.field2557 = 48012060 * var2;
      this.field2546 = var3 * 1273896127;
      this.field2547 = var4 * 380096711;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bp")
   final void method5349(int var1, int var2, int var3, int var4) {
      this.field2556 = 1954293863 * var1;
      this.field2557 = -143452847 * var2;
      this.field2546 = var3 * 1273896127;
      this.field2547 = var4 * -1542058307;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;IIIIZ)V")
   @ObfuscatedName("ky")
   public static void method5391(CollisionData var0, int var1, int var2, int var3, int var4, boolean var5) {
      if (var0 == null) {
         var0.method5409(var1, var1, var1, var1, var1, var5);
      }

      var0.method5386(var1, var2, var3, var4, 0, var5, -1787137347);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("br")
   final void method5350(int var1, int var2, int var3, int var4) {
      this.field2556 = 1079891361 * var1;
      this.field2557 = -1287396833 * var2;
      this.field2546 = var3 * 935631792;
      this.field2547 = var4 * 1667417018;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   public void method5354() {
      for (int var1 = 0; var1 < -148155585 * this.field2546; var1++) {
         for (int var2 = 0; var2 < this.field2547 * 1473114359; var2++) {
            if (var1 >= 1 && var2 >= 1 && var1 < this.field2546 * -148155585 - 5 && var2 < this.field2547 * 1998543355 - 5) {
               this.field2548[var1][var2] = 1271937005;
            } else {
               this.field2548[var1][var2] = 16777215;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("az")
   public void method5415(int var1, int var2) {
      this.method5396(var1, var2, -2018061884);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIZ)V")
   @ObfuscatedName("cb")
   void method5387(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      int var7 = 256;
      if (var6) {
         var7 += 1343564344;
      }

      var1 -= this.field2556 * 370701143;
      var2 -= this.field2557 * 1140151217;
      if (1 == var5 || 3 == var5) {
         int var8 = var3;
         var3 = var4;
         var4 = var8;
      }

      for (int var12 = var1; var12 < var1 + var3; var12++) {
         if (var12 >= 0 && var12 < this.field2546 * -148155585) {
            for (int var9 = var2; var9 < var4 + var2; var9++) {
               if (var9 >= 0 && var9 < 1473114359 * this.field2547) {
                  this.method5362(var12, var9, var7, -1882426066);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   public void method5355() {
      for (int var1 = 0; var1 < -148155585 * this.field2546; var1++) {
         for (int var2 = 0; var2 < this.field2547 * 1473114359; var2++) {
            if (var1 >= 1 && var2 >= 1 && var1 < this.field2546 * -2000189147 - 5 && var2 < this.field2547 * 1473114359 - 5) {
               this.field2548[var1][var2] = 654740841;
            } else {
               this.field2548[var1][var2] = -6365322;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgc;IIB)V")
   @ObfuscatedName("nr")
   public static void method5400(CollisionData var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         var0.method5402(var1, var1, var3);
      } else {
         try {
            var1 -= var0.field2556 * 370701143;
            var2 -= var0.field2557 * 1140151217;
            var0.field2548[var1][var2] = var0.field2548[var1][var2] | 262144;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "gc.au(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIII)V")
   @ObfuscatedName("au")
   static final void method5413(Widget var0, int var1, int var2, int var3, int var4) {
      try {
         if (null == var0.field4368) {
            throw new RuntimeException();
         } else {
            var0.field4368[var1] = var2;
            var0.field4369[var1] = var3;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gc.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgc;IIIB)V")
   @ObfuscatedName("qz")
   public static void method5367(CollisionData var0, int var1, int var2, int var3, byte var4) {
      if (var0 == null) {
         var0.method5369(var1, var1, var1, var4);
      }

      try {
         var0.field2548[var1][var2] = var0.field2548[var1][var2] & ~var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gc.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("bo")
   boolean method5359(int var1, int var2, int var3) {
      return 0 != (this.field2548[var1][var2] & var3);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bu")
   void method5363(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] | var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ba")
   void method5364(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] | var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bs")
   public void method5373(int var1, int var2, int var3) {
      method5367(this, var1 - this.field2556 * 370701143, var2 - this.field2557 * 1140151217, var3, (byte)-31);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bb")
   void method5365(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] | var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bi")
   void method5368(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] & ~var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bq")
   public void method5374(int var1, int var2, int var3) {
      method5367(this, var1 - this.field2556 * 370701143, var2 - this.field2557 * 1432708128, var3, (byte)-55);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bd")
   public boolean method5376(int var1, int var2) {
      return method5378(this, var1 - -976114656 * this.field2556, var2 - this.field2557 * 1140151217, (byte)-5);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bl")
   boolean method5377(int var1, int var2) {
      return var1 >= 1 && var1 < -148155585 * this.field2546 - 5 && var2 >= 1 && var2 < this.field2547 * 1473114359 - 5;
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("bz")
   public void method5392(int var1, int var2, int var3, int var4, boolean var5) {
      this.method5386(var1, var2, var3, var4, 0, var5, -1983712900);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ap")
   public void method5396(int var1, int var2, int var3) {
      try {
         var1 -= this.field2556 * 370701143;
         var2 -= this.field2557 * 1140151217;
         this.field2548[var1][var2] = this.field2548[var1][var2] | 2097152;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("ca")
   public void method5380(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= this.field2556 * 1554750442;
      var2 -= this.field2557 * 1140151217;
      if (0 == var3) {
         if (0 == var4) {
            this.method5362(var1, var2, 128, -1980544934);
            this.method5362(var1 - 1, var2, 8, -1724495958);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 2, -1833166704);
            this.method5362(var1, 1 + var2, 32, -2115811624);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 8, -2000089347);
            this.method5362(1 + var1, var2, 128, -1905342973);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 32, -1777801848);
            this.method5362(var1, var2 - 1, 2, -2066154041);
         }
      }

      if (var3 == 1 || var3 == 3) {
         if (0 == var4) {
            this.method5362(var1, var2, 1, -2142332782);
            this.method5362(var1 - 1, var2 + 1, 16, -1986516418);
         }

         if (1 == var4) {
            this.method5362(var1, var2, 4, -1841774023);
            this.method5362(var1 + 1, var2 + 1, 64, -2009881783);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 16, -1881926369);
            this.method5362(1 + var1, var2 - 1, 1, -1986714740);
         }

         if (var4 == 3) {
            this.method5362(var1, var2, -910370048, -1848393251);
            this.method5362(var1 - 1, var2 - 1, 4, -1975012421);
         }
      }

      if (var3 == 2) {
         if (var4 == 0) {
            this.method5362(var1, var2, 130, -1972319955);
            this.method5362(var1 - 1, var2, 8, -2137897859);
            this.method5362(var1, var2 + 1, 32, -2031331940);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 10, -1961890691);
            this.method5362(var1, var2 + 1, -910394968, -2143647488);
            this.method5362(1 + var1, var2, -263562746, -1723702375);
         }

         if (2 == var4) {
            this.method5362(var1, var2, -1466108559, -2145795471);
            this.method5362(1 + var1, var2, -15260984, -1811920885);
            this.method5362(var1, var2 - 1, 2, -1978133845);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 1564949021, -1842243320);
            this.method5362(var1, var2 - 1, 2, -1796558392);
            this.method5362(var1 - 1, var2, 8, -2099470347);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 65536, -1885737414);
               this.method5362(var1 - 1, var2, 4096, -1814400076);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 1024, -1989402455);
               this.method5362(var1, 1 + var2, 16384, -1996838132);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 1703843363, -1723251392);
               this.method5362(var1 + 1, var2, -918027248, -1939460582);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 1741075043, -1719888900);
               this.method5362(var1, var2 - 1, 1024, -2076483708);
            }
         }

         if (var3 == 1 || 3 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 512, -1913898957);
               this.method5362(var1 - 1, 1 + var2, 8192, -1683237838);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 2048, -2061097953);
               this.method5362(1 + var1, 1 + var2, -424656766, -2044926660);
            }

            if (2 == var4) {
               this.method5362(var1, var2, 8192, -1905967368);
               this.method5362(var1 + 1, var2 - 1, 512, -2137705781);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 32768, -2134337063);
               this.method5362(var1 - 1, var2 - 1, 2048, -1902665295);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               this.method5362(var1, var2, 66560, -1796966946);
               this.method5362(var1 - 1, var2, 4096, -1904161390);
               this.method5362(var1, 1 + var2, 16384, -1847578571);
            }

            if (var4 == 1) {
               this.method5362(var1, var2, -348386413, -2006764319);
               this.method5362(var1, var2 + 1, -829318440, -2004791391);
               this.method5362(var1 + 1, var2, 65536, -1791489196);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 20480, -1882884860);
               this.method5362(1 + var1, var2, 229566932, -2051009569);
               this.method5362(var1, var2 - 1, 52343708, -1839312443);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 81985326, -2117107679);
               this.method5362(var1, var2 - 1, -1682584262, -2074543438);
               this.method5362(var1 - 1, var2, -1178444394, -1647762631);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("cf")
   public void method5381(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= this.field2556 * 370701143;
      var2 -= this.field2557 * 1140151217;
      if (0 == var3) {
         if (0 == var4) {
            this.method5362(var1, var2, 128, -1802989674);
            this.method5362(var1 - 1, var2, 8, -1966817926);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 2, -2104683614);
            this.method5362(var1, 1 + var2, 32, -1906403888);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 8, -2114287223);
            this.method5362(1 + var1, var2, 128, -2031640852);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 32, -1819231843);
            this.method5362(var1, var2 - 1, 2, -1733812984);
         }
      }

      if (var3 == 1 || var3 == 3) {
         if (0 == var4) {
            this.method5362(var1, var2, 1, -1686430853);
            this.method5362(var1 - 1, var2 + 1, 16, -1754623693);
         }

         if (1 == var4) {
            this.method5362(var1, var2, 4, -1859142076);
            this.method5362(var1 + 1, var2 + 1, 64, -2136587224);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 16, -1823491307);
            this.method5362(1 + var1, var2 - 1, 1, -1737788961);
         }

         if (var4 == 3) {
            this.method5362(var1, var2, 64, -1840868134);
            this.method5362(var1 - 1, var2 - 1, 4, -1949907126);
         }
      }

      if (var3 == 2) {
         if (var4 == 0) {
            this.method5362(var1, var2, 130, -2104113602);
            this.method5362(var1 - 1, var2, 8, -2113252944);
            this.method5362(var1, var2 + 1, 32, -1919802620);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 10, -2125592882);
            this.method5362(var1, var2 + 1, 32, -2118850670);
            this.method5362(1 + var1, var2, 128, -2016974372);
         }

         if (2 == var4) {
            this.method5362(var1, var2, 40, -2026354429);
            this.method5362(1 + var1, var2, 128, -2119077449);
            this.method5362(var1, var2 - 1, 2, -1941774932);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 160, -1766745503);
            this.method5362(var1, var2 - 1, 2, -1759941127);
            this.method5362(var1 - 1, var2, 8, -1884629921);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 65536, -2105935122);
               this.method5362(var1 - 1, var2, 4096, -1639852874);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 1024, -1679263386);
               this.method5362(var1, 1 + var2, 16384, -2015178141);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 4096, -2076562725);
               this.method5362(var1 + 1, var2, 65536, -1762369433);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 16384, -1716734967);
               this.method5362(var1, var2 - 1, 1024, -1880885542);
            }
         }

         if (var3 == 1 || 3 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 512, -1644757515);
               this.method5362(var1 - 1, 1 + var2, 8192, -1800841925);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 2048, -2051091554);
               this.method5362(1 + var1, 1 + var2, 32768, -2091796020);
            }

            if (2 == var4) {
               this.method5362(var1, var2, 8192, -1850368268);
               this.method5362(var1 + 1, var2 - 1, 512, -1936180818);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 32768, -1745723690);
               this.method5362(var1 - 1, var2 - 1, 2048, -1764681714);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               this.method5362(var1, var2, 66560, -1944442087);
               this.method5362(var1 - 1, var2, 4096, -1640983734);
               this.method5362(var1, 1 + var2, 16384, -1695824944);
            }

            if (var4 == 1) {
               this.method5362(var1, var2, 5120, -1698770041);
               this.method5362(var1, var2 + 1, 16384, -2141957397);
               this.method5362(var1 + 1, var2, 65536, -2056202241);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 20480, -1851434775);
               this.method5362(1 + var1, var2, 65536, -1914722081);
               this.method5362(var1, var2 - 1, 1024, -1976917867);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 81920, -1909895626);
               this.method5362(var1, var2 - 1, 1024, -1682594941);
               this.method5362(var1 - 1, var2, 4096, -1964847894);
            }
         }
      }
   }

   public CollisionData(int var1, int var2, boolean var3) {
      if (var3) {
         method5351(this, -1, -1, var1 + 6, 6 + var2, 476650753);
      } else {
         method5351(this, 0, 0, var1, var2, -541912044);
      }

      this.field2548 = new int[-148155585 * this.field2546][this.field2547 * 1473114359];
      method5356(this, (byte)-27);
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("cr")
   public void method5393(int var1, int var2, int var3, int var4, boolean var5) {
      this.method5386(var1, var2, var3, var4, 0, var5, -2066823809);
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   int method5339(byte var1) {
      try {
         return this.field2557 * 1140151217;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gc.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("ct")
   public void method5382(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= this.field2556 * 370701143;
      var2 -= this.field2557 * 1140151217;
      if (0 == var3) {
         if (0 == var4) {
            this.method5362(var1, var2, 128, -1739465523);
            this.method5362(var1 - 1, var2, 8, -1766394011);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 2, -1671075051);
            this.method5362(var1, 1 + var2, 32, -1961120060);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 8, -1852111940);
            this.method5362(1 + var1, var2, 128, -1962531790);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 32, -2116203888);
            this.method5362(var1, var2 - 1, 2, -2027182466);
         }
      }

      if (var3 == 1 || var3 == 3) {
         if (0 == var4) {
            this.method5362(var1, var2, 1, -2099106197);
            this.method5362(var1 - 1, var2 + 1, 16, -1691317177);
         }

         if (1 == var4) {
            this.method5362(var1, var2, 4, -1812723593);
            this.method5362(var1 + 1, var2 + 1, 64, -1773312899);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 16, -1916116678);
            this.method5362(1 + var1, var2 - 1, 1, -1669339564);
         }

         if (var4 == 3) {
            this.method5362(var1, var2, 64, -1675656091);
            this.method5362(var1 - 1, var2 - 1, 4, -2089683173);
         }
      }

      if (var3 == 2) {
         if (var4 == 0) {
            this.method5362(var1, var2, 130, -1835666715);
            this.method5362(var1 - 1, var2, 8, -2100593696);
            this.method5362(var1, var2 + 1, 32, -1982362550);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 10, -1980840480);
            this.method5362(var1, var2 + 1, 32, -1820288991);
            this.method5362(1 + var1, var2, 128, -1979491366);
         }

         if (2 == var4) {
            this.method5362(var1, var2, 40, -2058875903);
            this.method5362(1 + var1, var2, 128, -1781102535);
            this.method5362(var1, var2 - 1, 2, -1870986839);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 160, -2010844568);
            this.method5362(var1, var2 - 1, 2, -1859861257);
            this.method5362(var1 - 1, var2, 8, -1887515603);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 65536, -1756839726);
               this.method5362(var1 - 1, var2, 4096, -1997113410);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 1024, -1841504791);
               this.method5362(var1, 1 + var2, 16384, -1677339399);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 4096, -1773001972);
               this.method5362(var1 + 1, var2, 65536, -2019187060);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 16384, -1909128956);
               this.method5362(var1, var2 - 1, 1024, -2096409580);
            }
         }

         if (var3 == 1 || 3 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 512, -1831199650);
               this.method5362(var1 - 1, 1 + var2, 8192, -1906166057);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 2048, -1840244182);
               this.method5362(1 + var1, 1 + var2, 32768, -2131439266);
            }

            if (2 == var4) {
               this.method5362(var1, var2, 8192, -1855272371);
               this.method5362(var1 + 1, var2 - 1, 512, -2093365139);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 32768, -1883161467);
               this.method5362(var1 - 1, var2 - 1, 2048, -2064569875);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               this.method5362(var1, var2, 66560, -1947009404);
               this.method5362(var1 - 1, var2, 4096, -1644957908);
               this.method5362(var1, 1 + var2, 16384, -1901317095);
            }

            if (var4 == 1) {
               this.method5362(var1, var2, 5120, -1729418887);
               this.method5362(var1, var2 + 1, 16384, -1951170033);
               this.method5362(var1 + 1, var2, 65536, -1751155730);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 20480, -2007651224);
               this.method5362(1 + var1, var2, 65536, -1957460532);
               this.method5362(var1, var2 - 1, 1024, -1771325033);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 81920, -1725326534);
               this.method5362(var1, var2 - 1, 1024, -1776242052);
               this.method5362(var1 - 1, var2, 4096, -1766064490);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgc;IIB)Z")
   @ObfuscatedName("ip")
   public static boolean method5378(CollisionData var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         var0.method5379(var1, var1, var3);
      }

      try {
         if (var1 >= 1 && var1 < -148155585 * var0.field2546 - 5) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            if (var2 >= 1) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (var2 < var0.field2547 * 1473114359 - 5) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cs")
   public void method5397(int var1, int var2) {
      var1 -= this.field2556 * 370701143;
      var2 -= this.field2557 * 1140151217;
      this.field2548[var1][var2] = this.field2548[var1][var2] | 2097152;
   }

   @ObfuscatedSignature(descriptor = "(Lgc;IIIII)V")
   @ObfuscatedName("cz")
   public static void method5351(CollisionData var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method5352(var1, var1, var1, var1, var1);
      } else {
         try {
            var0.field2556 = 1954293863 * var1;
            var0.field2557 = -143452847 * var2;
            var0.field2546 = var3 * 1273896127;
            var0.field2547 = var4 * 380096711;
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "gc.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method5341() {
      return 1476838491 * this.field2546;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   public int method5342(int var1) {
      try {
         return -148155585 * this.field2546;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gc.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("cl")
   public void method5404(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= 1823652646 * this.field2556;
      var2 -= -503176542 * this.field2557;
      if (var3 == 0) {
         if (0 == var4) {
            method5367(this, var1, var2, 128, (byte)-64);
            method5367(this, var1 - 1, var2, 8, (byte)-31);
         }

         if (var4 == 1) {
            method5367(this, var1, var2, 2, (byte)-72);
            method5367(this, var1, var2 + 1, 32, (byte)-91);
         }

         if (var4 == 2) {
            method5367(this, var1, var2, 8, (byte)-55);
            method5367(this, 1 + var1, var2, 128, (byte)-93);
         }

         if (var4 == 3) {
            method5367(this, var1, var2, 32, (byte)-75);
            method5367(this, var1, var2 - 1, 2, (byte)-68);
         }
      }

      if (1 == var3 || 3 == var3) {
         if (var4 == 0) {
            method5367(this, var1, var2, 1, (byte)-52);
            method5367(this, var1 - 1, var2 + 1, 16, (byte)-38);
         }

         if (1 == var4) {
            method5367(this, var1, var2, 4, (byte)-113);
            method5367(this, 1 + var1, var2 + 1, -127837899, (byte)-38);
         }

         if (2 == var4) {
            method5367(this, var1, var2, 16, (byte)-118);
            method5367(this, var1 + 1, var2 - 1, 1, (byte)-63);
         }

         if (var4 == 3) {
            method5367(this, var1, var2, 951289278, (byte)-93);
            method5367(this, var1 - 1, var2 - 1, 4, (byte)-77);
         }
      }

      if (var3 == 2) {
         if (0 == var4) {
            method5367(this, var1, var2, 130, (byte)-54);
            method5367(this, var1 - 1, var2, 8, (byte)-92);
            method5367(this, var1, 1 + var2, -1037371107, (byte)-19);
         }

         if (1 == var4) {
            method5367(this, var1, var2, 10, (byte)-80);
            method5367(this, var1, var2 + 1, 949745157, (byte)-44);
            method5367(this, var1 + 1, var2, 128, (byte)-99);
         }

         if (var4 == 2) {
            method5367(this, var1, var2, 2112555489, (byte)-14);
            method5367(this, 1 + var1, var2, 128, (byte)-48);
            method5367(this, var1, var2 - 1, 2, (byte)-54);
         }

         if (var4 == 3) {
            method5367(this, var1, var2, 160, (byte)13);
            method5367(this, var1, var2 - 1, 2, (byte)-57);
            method5367(this, var1 - 1, var2, 8, (byte)-3);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               method5367(this, var1, var2, 65536, (byte)-108);
               method5367(this, var1 - 1, var2, 4096, (byte)-38);
            }

            if (1 == var4) {
               method5367(this, var1, var2, 1024, (byte)-25);
               method5367(this, var1, var2 + 1, 16384, (byte)5);
            }

            if (2 == var4) {
               method5367(this, var1, var2, 4096, (byte)-112);
               method5367(this, 1 + var1, var2, 2119081622, (byte)-18);
            }

            if (3 == var4) {
               method5367(this, var1, var2, 16384, (byte)-29);
               method5367(this, var1, var2 - 1, 1024, (byte)-118);
            }
         }

         if (1 == var3 || var3 == 3) {
            if (var4 == 0) {
               method5367(this, var1, var2, 391600091, (byte)-107);
               method5367(this, var1 - 1, 1 + var2, 8192, (byte)-22);
            }

            if (var4 == 1) {
               method5367(this, var1, var2, 2048, (byte)-103);
               method5367(this, 1 + var1, 1 + var2, -1990157554, (byte)-72);
            }

            if (2 == var4) {
               method5367(this, var1, var2, 1754307813, (byte)-106);
               method5367(this, var1 + 1, var2 - 1, 512, (byte)-107);
            }

            if (3 == var4) {
               method5367(this, var1, var2, -1093688366, (byte)-2);
               method5367(this, var1 - 1, var2 - 1, 2048, (byte)-65);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               method5367(this, var1, var2, 66560, (byte)-104);
               method5367(this, var1 - 1, var2, 1466401037, (byte)-20);
               method5367(this, var1, var2 + 1, 16384, (byte)-1);
            }

            if (1 == var4) {
               method5367(this, var1, var2, 363916962, (byte)-65);
               method5367(this, var1, 1 + var2, -2099299204, (byte)-58);
               method5367(this, 1 + var1, var2, 2131474092, (byte)-94);
            }

            if (var4 == 2) {
               method5367(this, var1, var2, -1389801346, (byte)-110);
               method5367(this, var1 + 1, var2, 65536, (byte)-86);
               method5367(this, var1, var2 - 1, 1876923136, (byte)-111);
            }

            if (3 == var4) {
               method5367(this, var1, var2, 81920, (byte)-2);
               method5367(this, var1, var2 - 1, -1797614032, (byte)-116);
               method5367(this, var1 - 1, var2, 4096, (byte)-86);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("bh")
   public void method5405(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= 370701143 * this.field2556;
      var2 -= 823945243 * this.field2557;
      if (var3 == 0) {
         if (0 == var4) {
            method5367(this, var1, var2, 1852087506, (byte)-31);
            method5367(this, var1 - 1, var2, 8, (byte)-47);
         }

         if (var4 == 1) {
            method5367(this, var1, var2, 2, (byte)-47);
            method5367(this, var1, var2 + 1, 32, (byte)-35);
         }

         if (var4 == 2) {
            method5367(this, var1, var2, 8, (byte)-120);
            method5367(this, 1 + var1, var2, 128, (byte)13);
         }

         if (var4 == 3) {
            method5367(this, var1, var2, -1529997671, (byte)-11);
            method5367(this, var1, var2 - 1, 2, (byte)-17);
         }
      }

      if (1 == var3 || 3 == var3) {
         if (var4 == 0) {
            method5367(this, var1, var2, 1, (byte)4);
            method5367(this, var1 - 1, var2 + 1, 16, (byte)-117);
         }

         if (1 == var4) {
            method5367(this, var1, var2, 4, (byte)-2);
            method5367(this, 1 + var1, var2 + 1, -27097336, (byte)-50);
         }

         if (2 == var4) {
            method5367(this, var1, var2, 16, (byte)-12);
            method5367(this, var1 + 1, var2 - 1, 1, (byte)-60);
         }

         if (var4 == 3) {
            method5367(this, var1, var2, -1162485754, (byte)-14);
            method5367(this, var1 - 1, var2 - 1, 4, (byte)-38);
         }
      }

      if (var3 == 2) {
         if (0 == var4) {
            method5367(this, var1, var2, -1452379513, (byte)-37);
            method5367(this, var1 - 1, var2, 8, (byte)-119);
            method5367(this, var1, 1 + var2, 768799898, (byte)-34);
         }

         if (1 == var4) {
            method5367(this, var1, var2, 10, (byte)-87);
            method5367(this, var1, var2 + 1, -805537417, (byte)-102);
            method5367(this, var1 + 1, var2, 128, (byte)-100);
         }

         if (var4 == 2) {
            method5367(this, var1, var2, 40, (byte)-36);
            method5367(this, 1 + var1, var2, 175628984, (byte)-128);
            method5367(this, var1, var2 - 1, 2, (byte)-77);
         }

         if (var4 == 3) {
            method5367(this, var1, var2, 160, (byte)-7);
            method5367(this, var1, var2 - 1, 2, (byte)-123);
            method5367(this, var1 - 1, var2, 8, (byte)-30);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               method5367(this, var1, var2, 65536, (byte)-73);
               method5367(this, var1 - 1, var2, -1382578359, (byte)-68);
            }

            if (1 == var4) {
               method5367(this, var1, var2, 1024, (byte)-92);
               method5367(this, var1, var2 + 1, 16384, (byte)-97);
            }

            if (2 == var4) {
               method5367(this, var1, var2, 1515237953, (byte)-117);
               method5367(this, 1 + var1, var2, 65536, (byte)-73);
            }

            if (3 == var4) {
               method5367(this, var1, var2, -464398882, (byte)-122);
               method5367(this, var1, var2 - 1, 1024, (byte)-63);
            }
         }

         if (1 == var3 || var3 == 3) {
            if (var4 == 0) {
               method5367(this, var1, var2, 512, (byte)13);
               method5367(this, var1 - 1, 1 + var2, 8192, (byte)-111);
            }

            if (var4 == 1) {
               method5367(this, var1, var2, 2048, (byte)-36);
               method5367(this, 1 + var1, 1 + var2, 32768, (byte)-110);
            }

            if (2 == var4) {
               method5367(this, var1, var2, 8192, (byte)-112);
               method5367(this, var1 + 1, var2 - 1, 557792472, (byte)-121);
            }

            if (3 == var4) {
               method5367(this, var1, var2, -1082505814, (byte)-51);
               method5367(this, var1 - 1, var2 - 1, 2048, (byte)-105);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               method5367(this, var1, var2, -1990010400, (byte)-37);
               method5367(this, var1 - 1, var2, 4096, (byte)-128);
               method5367(this, var1, var2 + 1, 1584272784, (byte)-63);
            }

            if (1 == var4) {
               method5367(this, var1, var2, 1457884246, (byte)-43);
               method5367(this, var1, 1 + var2, 16384, (byte)-121);
               method5367(this, 1 + var1, var2, 178098116, (byte)-78);
            }

            if (var4 == 2) {
               method5367(this, var1, var2, -970060145, (byte)-20);
               method5367(this, var1 + 1, var2, 65536, (byte)-68);
               method5367(this, var1, var2 - 1, 1024, (byte)-113);
            }

            if (3 == var4) {
               method5367(this, var1, var2, -5537139, (byte)-110);
               method5367(this, var1, var2 - 1, 1024, (byte)-120);
               method5367(this, var1 - 1, var2, 4096, (byte)12);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgc;IIIIZI)V")
   @ObfuscatedName("nt")
   public static void method5383(CollisionData var0, int var1, int var2, int var3, int var4, boolean var5, int var6) {
      if (var0 == null) {
         var0.method5384(var1, var1, var1, var1, var5, var1);
      }

      try {
         var1 -= var0.field2556 * 370701143;
         var2 -= var0.field2557 * 1140151217;
         if (0 == var3) {
            if (var6 == -844046823) {
               throw new IllegalStateException();
            }

            if (0 == var4) {
               var0.method5362(var1, var2, 128, -1944782814);
               var0.method5362(var1 - 1, var2, 8, -2017347689);
            }

            if (var4 == 1) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 2, -1832069893);
               var0.method5362(var1, 1 + var2, 32, -2113667690);
            }

            if (var4 == 2) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 8, -2084468035);
               var0.method5362(1 + var1, var2, 128, -1936094734);
            }

            if (3 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 32, -2127752639);
               var0.method5362(var1, var2 - 1, 2, -1858133694);
            }
         }

         label282: {
            if (var3 != 1) {
               if (var6 == -844046823) {
                  return;
               }

               if (var3 != 3) {
                  break label282;
               }

               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }
            }

            if (0 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 1, -1835486972);
               var0.method5362(var1 - 1, var2 + 1, 16, -1939959206);
            }

            if (1 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 4, -2067328367);
               var0.method5362(var1 + 1, var2 + 1, 64, -2127732276);
            }

            if (var4 == 2) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 16, -1949810605);
               var0.method5362(1 + var1, var2 - 1, 1, -1696090516);
            }

            if (var4 == 3) {
               if (var6 == -844046823) {
                  return;
               }

               var0.method5362(var1, var2, 64, -1727130978);
               var0.method5362(var1 - 1, var2 - 1, 4, -1972983877);
            }
         }

         if (var3 == 2) {
            if (var6 == -844046823) {
               return;
            }

            if (var4 == 0) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 130, -2134603303);
               var0.method5362(var1 - 1, var2, 8, -1684198962);
               var0.method5362(var1, var2 + 1, 32, -1750815827);
            }

            if (var4 == 1) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 10, -1994608051);
               var0.method5362(var1, var2 + 1, 32, -1964716481);
               var0.method5362(1 + var1, var2, 128, -1792421191);
            }

            if (2 == var4) {
               var0.method5362(var1, var2, 40, -1945241599);
               var0.method5362(1 + var1, var2, 128, -1705295343);
               var0.method5362(var1, var2 - 1, 2, -2127317004);
            }

            if (3 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               var0.method5362(var1, var2, 160, -1727916046);
               var0.method5362(var1, var2 - 1, 2, -1874098790);
               var0.method5362(var1 - 1, var2, 8, -2025616037);
            }
         }

         if (var5) {
            if (var6 == -844046823) {
               throw new IllegalStateException();
            }

            if (0 == var3) {
               if (var6 == -844046823) {
                  return;
               }

               if (var4 == 0) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 65536, -1724402597);
                  var0.method5362(var1 - 1, var2, 4096, -2042904219);
               }

               if (1 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 1024, -1733698039);
                  var0.method5362(var1, 1 + var2, 16384, -1651610283);
               }

               if (var4 == 2) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 4096, -2040994205);
                  var0.method5362(var1 + 1, var2, 65536, -1651040437);
               }

               if (3 == var4) {
                  if (var6 == -844046823) {
                     return;
                  }

                  var0.method5362(var1, var2, 16384, -2060408324);
                  var0.method5362(var1, var2 - 1, 1024, -2075485411);
               }
            }

            label287: {
               if (var3 != 1) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  if (3 != var3) {
                     break label287;
                  }

                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }
               }

               if (var4 == 0) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 512, -1661086008);
                  var0.method5362(var1 - 1, 1 + var2, 8192, -1700149170);
               }

               if (1 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 2048, -2026804949);
                  var0.method5362(1 + var1, 1 + var2, 32768, -2068634649);
               }

               if (2 == var4) {
                  if (var6 == -844046823) {
                     return;
                  }

                  var0.method5362(var1, var2, 8192, -1760685271);
                  var0.method5362(var1 + 1, var2 - 1, 512, -1878868895);
               }

               if (3 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 32768, -1811933477);
                  var0.method5362(var1 - 1, var2 - 1, 2048, -1866680525);
               }
            }

            if (var3 == 2) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               if (0 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 66560, -1942242079);
                  var0.method5362(var1 - 1, var2, 4096, -2101316973);
                  var0.method5362(var1, 1 + var2, 16384, -1776977110);
               }

               if (var4 == 1) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  var0.method5362(var1, var2, 5120, -1886332349);
                  var0.method5362(var1, var2 + 1, 16384, -1784642342);
                  var0.method5362(var1 + 1, var2, 65536, -1888346968);
               }

               if (var4 == 2) {
                  if (var6 == -844046823) {
                     return;
                  }

                  var0.method5362(var1, var2, 20480, -1858047169);
                  var0.method5362(1 + var1, var2, 65536, -1927590555);
                  var0.method5362(var1, var2 - 1, 1024, -2038386445);
               }

               if (3 == var4) {
                  var0.method5362(var1, var2, 81920, -1902073585);
                  var0.method5362(var1, var2 - 1, 1024, -1897615855);
                  var0.method5362(var1 - 1, var2, 4096, -1660567675);
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gc.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method5416() {
      method5356(this, (byte)-61);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cy")
   public void method5401(int var1, int var2) {
      var1 -= this.field2556 * 370701143;
      var2 -= this.field2557 * 1140151217;
      this.field2548[var1][var2] = this.field2548[var1][var2] | 262144;
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("ai")
   public void method5417(int var1, int var2, int var3, int var4, boolean var5) {
      method5383(this, var1, var2, var3, var4, var5, -1761732661);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIZ)V")
   @ObfuscatedName("ch")
   public void method5410(int var1, int var2, int var3, int var4, int var5, boolean var6) {
      int var7 = 256;
      if (var6) {
         var7 += 131072;
      }

      var1 -= this.field2556 * 370701143;
      var2 -= 1140151217 * this.field2557;
      if (var5 == 1 || 3 == var5) {
         int var8 = var3;
         var3 = var4;
         var4 = var8;
      }

      for (int var12 = var1; var12 < var1 + var3; var12++) {
         if (var12 >= 0 && var12 < this.field2546 * -148155585) {
            for (int var9 = var2; var9 < var4 + var2; var9++) {
               if (var9 >= 0 && var9 < this.field2547 * 1473114359) {
                  method5367(this, var12, var9, var7, (byte)-104);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cz")
   public void method5412(int var1, int var2) {
      var1 -= this.field2556 * 1830473737;
      var2 -= this.field2557 * 1140151217;
      this.field2548[var1][var2] = this.field2548[var1][var2] & -1575545659;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method5337(int var0) {
      try {
         classOF.field4850.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("kf")
   public void method5418(int var1, int var2) {
      method5400(this, var1, var2, (byte)4);
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("bk")
   boolean method5360(int var1, int var2, int var3) {
      return 0 != (this.field2548[var1][var2] & var3);
   }

   @ObfuscatedSignature(descriptor = "(Lgc;IIIIZI)V")
   @ObfuscatedName("ms")
   public static void method5406(CollisionData var0, int var1, int var2, int var3, int var4, boolean var5, int var6) {
      if (var0 == null) {
         var0.method5407(var1, var1, var1, var1, var5, var1);
      }

      try {
         var1 -= 370701143 * var0.field2556;
         var2 -= 1140151217 * var0.field2557;
         if (var3 == 0) {
            if (var6 >= -2095241843) {
               throw new IllegalStateException();
            }

            if (0 == var4) {
               method5367(var0, var1, var2, 128, (byte)-40);
               method5367(var0, var1 - 1, var2, 8, (byte)-106);
            }

            if (var4 == 1) {
               if (var6 >= -2095241843) {
                  return;
               }

               method5367(var0, var1, var2, 2, (byte)-31);
               method5367(var0, var1, var2 + 1, 32, (byte)-68);
            }

            if (var4 == 2) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 8, (byte)-93);
               method5367(var0, 1 + var1, var2, 128, (byte)-58);
            }

            if (var4 == 3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 32, (byte)-92);
               method5367(var0, var1, var2 - 1, 2, (byte)-23);
            }
         }

         label270: {
            if (1 != var3) {
               if (var6 >= -2095241843) {
                  return;
               }

               if (3 != var3) {
                  break label270;
               }

               if (var6 >= -2095241843) {
                  return;
               }
            }

            if (var4 == 0) {
               method5367(var0, var1, var2, 1, (byte)-22);
               method5367(var0, var1 - 1, var2 + 1, 16, (byte)-20);
            }

            if (1 == var4) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 4, (byte)-8);
               method5367(var0, 1 + var1, var2 + 1, 64, (byte)-106);
            }

            if (2 == var4) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 16, (byte)-123);
               method5367(var0, var1 + 1, var2 - 1, 1, (byte)-24);
            }

            if (var4 == 3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 64, (byte)-86);
               method5367(var0, var1 - 1, var2 - 1, 4, (byte)-15);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 130, (byte)-66);
               method5367(var0, var1 - 1, var2, 8, (byte)-92);
               method5367(var0, var1, 1 + var2, 32, (byte)-26);
            }

            if (1 == var4) {
               if (var6 >= -2095241843) {
                  return;
               }

               method5367(var0, var1, var2, 10, (byte)0);
               method5367(var0, var1, var2 + 1, 32, (byte)12);
               method5367(var0, var1 + 1, var2, 128, (byte)-43);
            }

            if (var4 == 2) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 40, (byte)-87);
               method5367(var0, 1 + var1, var2, 128, (byte)-51);
               method5367(var0, var1, var2 - 1, 2, (byte)-35);
            }

            if (var4 == 3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(var0, var1, var2, 160, (byte)-119);
               method5367(var0, var1, var2 - 1, 2, (byte)-1);
               method5367(var0, var1 - 1, var2, 8, (byte)-69);
            }
         }

         if (var5) {
            if (var6 >= -2095241843) {
               throw new IllegalStateException();
            }

            if (0 == var3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               if (var4 == 0) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 65536, (byte)-22);
                  method5367(var0, var1 - 1, var2, 4096, (byte)-96);
               }

               if (1 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 1024, (byte)-78);
                  method5367(var0, var1, var2 + 1, 16384, (byte)-99);
               }

               if (2 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 4096, (byte)-78);
                  method5367(var0, 1 + var1, var2, 65536, (byte)-8);
               }

               if (3 == var4) {
                  method5367(var0, var1, var2, 16384, (byte)-25);
                  method5367(var0, var1, var2 - 1, 1024, (byte)-100);
               }
            }

            label275: {
               if (1 != var3) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  if (var3 != 3) {
                     break label275;
                  }

                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }
               }

               if (var4 == 0) {
                  if (var6 >= -2095241843) {
                     return;
                  }

                  method5367(var0, var1, var2, 512, (byte)-35);
                  method5367(var0, var1 - 1, 1 + var2, 8192, (byte)-91);
               }

               if (var4 == 1) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 2048, (byte)13);
                  method5367(var0, 1 + var1, 1 + var2, 32768, (byte)-95);
               }

               if (2 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 8192, (byte)-109);
                  method5367(var0, var1 + 1, var2 - 1, 512, (byte)-18);
               }

               if (3 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 32768, (byte)-41);
                  method5367(var0, var1 - 1, var2 - 1, 2048, (byte)-54);
               }
            }

            if (var3 == 2) {
               if (0 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 66560, (byte)-18);
                  method5367(var0, var1 - 1, var2, 4096, (byte)-66);
                  method5367(var0, var1, var2 + 1, 16384, (byte)-6);
               }

               if (1 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 5120, (byte)-91);
                  method5367(var0, var1, 1 + var2, 16384, (byte)-27);
                  method5367(var0, 1 + var1, var2, 65536, (byte)-90);
               }

               if (var4 == 2) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 20480, (byte)-16);
                  method5367(var0, var1 + 1, var2, 65536, (byte)-75);
                  method5367(var0, var1, var2 - 1, 1024, (byte)-108);
               }

               if (3 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(var0, var1, var2, 81920, (byte)-120);
                  method5367(var0, var1, var2 - 1, 1024, (byte)-90);
                  method5367(var0, var1 - 1, var2, 4096, (byte)-73);
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gc.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("cc")
   public void method5419(int var1, int var2, int var3, int var4, boolean var5) {
      this.method5389(var1, var2, var3, var4, var5, (byte)-41);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("ag")
   final void method5352(int var1, int var2, int var3, int var4, int var5) {
      try {
         this.field2556 = 1954293863 * var1;
         this.field2556 = -143452847 * var2;
         this.field2556 = var3 * 1273896127;
         this.field2547 = var4 * 380096711;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)V")
   @ObfuscatedName("aa")
   void method5369(int var1, int var2, int var3, byte var4) {
      try {
         this.field2548[var1][var2] = this.field2548[var1][var2] & ~var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gc.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)Z")
   @ObfuscatedName("aj")
   boolean method5379(int var1, int var2, byte var3) {
      try {
         if (var1 >= 1 && var1 < -148155585 * this.field2557 - 5) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            if (var2 >= 1) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (var2 < this.field2557 * 1473114359 - 5) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgc;B)V")
   @ObfuscatedName("pv")
   public static void method5356(CollisionData var0, byte var1) {
      if (var0 == null) {
         var0.method5357(var1);
      } else {
         try {
            for (int var2 = 0; var2 < -148155585 * var0.field2546; var2++) {
               if (var1 == 26) {
                  throw new IllegalStateException();
               }

               for (int var3 = 0; var3 < var0.field2547 * 1473114359; var3++) {
                  if (var1 == 26) {
                     throw new IllegalStateException();
                  }

                  if (var2 >= 1) {
                     if (var1 == 26) {
                        return;
                     }

                     if (var3 >= 1) {
                        if (var1 == 26) {
                           return;
                        }

                        if (var2 < var0.field2546 * -148155585 - 5) {
                           if (var1 == 26) {
                              throw new IllegalStateException();
                           }

                           if (var3 < var0.field2547 * 1473114359 - 5) {
                              var0.field2548[var2][var3] = 1073741824;
                              continue;
                           }

                           if (var1 == 26) {
                              throw new IllegalStateException();
                           }
                        }
                     }
                  }

                  var0.field2548[var2][var3] = 16777215;
               }
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "gc.as(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZI)V")
   @ObfuscatedName("ay")
   public void method5384(int var1, int var2, int var3, int var4, boolean var5, int var6) {
      try {
         var1 -= this.field2546 * 370701143;
         var2 -= this.field2546 * 1140151217;
         if (0 == var3) {
            if (var6 == -844046823) {
               throw new IllegalStateException();
            }

            if (0 == var4) {
               this.method5362(var1, var2, 128, -1944782814);
               this.method5362(var1 - 1, var2, 8, -2017347689);
            }

            if (var4 == 1) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 2, -1832069893);
               this.method5362(var1, 1 + var2, 32, -2113667690);
            }

            if (var4 == 2) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 8, -2084468035);
               this.method5362(1 + var1, var2, 128, -1936094734);
            }

            if (3 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 32, -2127752639);
               this.method5362(var1, var2 - 1, 2, -1858133694);
            }
         }

         label278: {
            if (var3 != 1) {
               if (var6 == -844046823) {
                  return;
               }

               if (var3 != 3) {
                  break label278;
               }

               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }
            }

            if (0 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 1, -1835486972);
               this.method5362(var1 - 1, var2 + 1, 16, -1939959206);
            }

            if (1 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 4, -2067328367);
               this.method5362(var1 + 1, var2 + 1, 64, -2127732276);
            }

            if (var4 == 2) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 16, -1949810605);
               this.method5362(1 + var1, var2 - 1, 1, -1696090516);
            }

            if (var4 == 3) {
               if (var6 == -844046823) {
                  return;
               }

               this.method5362(var1, var2, 64, -1727130978);
               this.method5362(var1 - 1, var2 - 1, 4, -1972983877);
            }
         }

         if (var3 == 2) {
            if (var6 == -844046823) {
               return;
            }

            if (var4 == 0) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 130, -2134603303);
               this.method5362(var1 - 1, var2, 8, -1684198962);
               this.method5362(var1, var2 + 1, 32, -1750815827);
            }

            if (var4 == 1) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 10, -1994608051);
               this.method5362(var1, var2 + 1, 32, -1964716481);
               this.method5362(1 + var1, var2, 128, -1792421191);
            }

            if (2 == var4) {
               this.method5362(var1, var2, 40, -1945241599);
               this.method5362(1 + var1, var2, 128, -1705295343);
               this.method5362(var1, var2 - 1, 2, -2127317004);
            }

            if (3 == var4) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               this.method5362(var1, var2, 160, -1727916046);
               this.method5362(var1, var2 - 1, 2, -1874098790);
               this.method5362(var1 - 1, var2, 8, -2025616037);
            }
         }

         if (var5) {
            if (var6 == -844046823) {
               throw new IllegalStateException();
            }

            if (0 == var3) {
               if (var6 == -844046823) {
                  return;
               }

               if (var4 == 0) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 65536, -1724402597);
                  this.method5362(var1 - 1, var2, 4096, -2042904219);
               }

               if (1 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 1024, -1733698039);
                  this.method5362(var1, 1 + var2, 16384, -1651610283);
               }

               if (var4 == 2) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 4096, -2040994205);
                  this.method5362(var1 + 1, var2, 65536, -1651040437);
               }

               if (3 == var4) {
                  if (var6 == -844046823) {
                     return;
                  }

                  this.method5362(var1, var2, 16384, -2060408324);
                  this.method5362(var1, var2 - 1, 1024, -2075485411);
               }
            }

            label283: {
               if (var3 != 1) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  if (3 != var3) {
                     break label283;
                  }

                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }
               }

               if (var4 == 0) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 512, -1661086008);
                  this.method5362(var1 - 1, 1 + var2, 8192, -1700149170);
               }

               if (1 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 2048, -2026804949);
                  this.method5362(1 + var1, 1 + var2, 32768, -2068634649);
               }

               if (2 == var4) {
                  if (var6 == -844046823) {
                     return;
                  }

                  this.method5362(var1, var2, 8192, -1760685271);
                  this.method5362(var1 + 1, var2 - 1, 512, -1878868895);
               }

               if (3 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 32768, -1811933477);
                  this.method5362(var1 - 1, var2 - 1, 2048, -1866680525);
               }
            }

            if (var3 == 2) {
               if (var6 == -844046823) {
                  throw new IllegalStateException();
               }

               if (0 == var4) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 66560, -1942242079);
                  this.method5362(var1 - 1, var2, 4096, -2101316973);
                  this.method5362(var1, 1 + var2, 16384, -1776977110);
               }

               if (var4 == 1) {
                  if (var6 == -844046823) {
                     throw new IllegalStateException();
                  }

                  this.method5362(var1, var2, 5120, -1886332349);
                  this.method5362(var1, var2 + 1, 16384, -1784642342);
                  this.method5362(var1 + 1, var2, 65536, -1888346968);
               }

               if (var4 == 2) {
                  if (var6 == -844046823) {
                     return;
                  }

                  this.method5362(var1, var2, 20480, -1858047169);
                  this.method5362(1 + var1, var2, 65536, -1927590555);
                  this.method5362(var1, var2 - 1, 1024, -2038386445);
               }

               if (3 == var4) {
                  this.method5362(var1, var2, 81920, -1902073585);
                  this.method5362(var1, var2 - 1, 1024, -1897615855);
                  this.method5362(var1 - 1, var2, 4096, -1660567675);
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gc.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("au")
   public void method5402(int var1, int var2, byte var3) {
      try {
         var1 -= this.field2547 * 370701143;
         var2 -= this.field2546 * 1140151217;
         this.field2548[var1][var2] = this.field2548[var1][var2] | 262144;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZI)V")
   @ObfuscatedName("ai")
   public void method5407(int var1, int var2, int var3, int var4, boolean var5, int var6) {
      try {
         var1 -= 370701143 * this.field2546;
         var2 -= 1140151217 * this.field2556;
         if (var3 == 0) {
            if (var6 >= -2095241843) {
               throw new IllegalStateException();
            }

            if (0 == var4) {
               method5367(this, var1, var2, 128, (byte)-40);
               method5367(this, var1 - 1, var2, 8, (byte)-106);
            }

            if (var4 == 1) {
               if (var6 >= -2095241843) {
                  return;
               }

               method5367(this, var1, var2, 2, (byte)-31);
               method5367(this, var1, var2 + 1, 32, (byte)-68);
            }

            if (var4 == 2) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 8, (byte)-93);
               method5367(this, 1 + var1, var2, 128, (byte)-58);
            }

            if (var4 == 3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 32, (byte)-92);
               method5367(this, var1, var2 - 1, 2, (byte)-23);
            }
         }

         label266: {
            if (1 != var3) {
               if (var6 >= -2095241843) {
                  return;
               }

               if (3 != var3) {
                  break label266;
               }

               if (var6 >= -2095241843) {
                  return;
               }
            }

            if (var4 == 0) {
               method5367(this, var1, var2, 1, (byte)-22);
               method5367(this, var1 - 1, var2 + 1, 16, (byte)-20);
            }

            if (1 == var4) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 4, (byte)-8);
               method5367(this, 1 + var1, var2 + 1, 64, (byte)-106);
            }

            if (2 == var4) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 16, (byte)-123);
               method5367(this, var1 + 1, var2 - 1, 1, (byte)-24);
            }

            if (var4 == 3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 64, (byte)-86);
               method5367(this, var1 - 1, var2 - 1, 4, (byte)-15);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 130, (byte)-66);
               method5367(this, var1 - 1, var2, 8, (byte)-92);
               method5367(this, var1, 1 + var2, 32, (byte)-26);
            }

            if (1 == var4) {
               if (var6 >= -2095241843) {
                  return;
               }

               method5367(this, var1, var2, 10, (byte)0);
               method5367(this, var1, var2 + 1, 32, (byte)12);
               method5367(this, var1 + 1, var2, 128, (byte)-43);
            }

            if (var4 == 2) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 40, (byte)-87);
               method5367(this, 1 + var1, var2, 128, (byte)-51);
               method5367(this, var1, var2 - 1, 2, (byte)-35);
            }

            if (var4 == 3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               method5367(this, var1, var2, 160, (byte)-119);
               method5367(this, var1, var2 - 1, 2, (byte)-1);
               method5367(this, var1 - 1, var2, 8, (byte)-69);
            }
         }

         if (var5) {
            if (var6 >= -2095241843) {
               throw new IllegalStateException();
            }

            if (0 == var3) {
               if (var6 >= -2095241843) {
                  throw new IllegalStateException();
               }

               if (var4 == 0) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 65536, (byte)-22);
                  method5367(this, var1 - 1, var2, 4096, (byte)-96);
               }

               if (1 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 1024, (byte)-78);
                  method5367(this, var1, var2 + 1, 16384, (byte)-99);
               }

               if (2 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 4096, (byte)-78);
                  method5367(this, 1 + var1, var2, 65536, (byte)-8);
               }

               if (3 == var4) {
                  method5367(this, var1, var2, 16384, (byte)-25);
                  method5367(this, var1, var2 - 1, 1024, (byte)-100);
               }
            }

            label271: {
               if (1 != var3) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  if (var3 != 3) {
                     break label271;
                  }

                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }
               }

               if (var4 == 0) {
                  if (var6 >= -2095241843) {
                     return;
                  }

                  method5367(this, var1, var2, 512, (byte)-35);
                  method5367(this, var1 - 1, 1 + var2, 8192, (byte)-91);
               }

               if (var4 == 1) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 2048, (byte)13);
                  method5367(this, 1 + var1, 1 + var2, 32768, (byte)-95);
               }

               if (2 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 8192, (byte)-109);
                  method5367(this, var1 + 1, var2 - 1, 512, (byte)-18);
               }

               if (3 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 32768, (byte)-41);
                  method5367(this, var1 - 1, var2 - 1, 2048, (byte)-54);
               }
            }

            if (var3 == 2) {
               if (0 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 66560, (byte)-18);
                  method5367(this, var1 - 1, var2, 4096, (byte)-66);
                  method5367(this, var1, var2 + 1, 16384, (byte)-6);
               }

               if (1 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 5120, (byte)-91);
                  method5367(this, var1, 1 + var2, 16384, (byte)-27);
                  method5367(this, 1 + var1, var2, 65536, (byte)-90);
               }

               if (var4 == 2) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 20480, (byte)-16);
                  method5367(this, var1 + 1, var2, 65536, (byte)-75);
                  method5367(this, var1, var2 - 1, 1024, (byte)-108);
               }

               if (3 == var4) {
                  if (var6 >= -2095241843) {
                     throw new IllegalStateException();
                  }

                  method5367(this, var1, var2, 81920, (byte)-120);
                  method5367(this, var1, var2 - 1, 1024, (byte)-90);
                  method5367(this, var1 - 1, var2, 4096, (byte)-73);
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "gc.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("by")
   void method5370(int var1, int var2, int var3) {
      this.field2548[var1][var2] = this.field2548[var1][var2] & ~var3;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   public void method5357(byte var1) {
      try {
         for (int var2 = 0; var2 < -148155585 * this.field2556; var2++) {
            if (var1 == 26) {
               throw new IllegalStateException();
            }

            for (int var3 = 0; var3 < this.field2547 * 1473114359; var3++) {
               if (var1 == 26) {
                  throw new IllegalStateException();
               }

               if (var2 >= 1) {
                  if (var1 == 26) {
                     return;
                  }

                  if (var3 >= 1) {
                     if (var1 == 26) {
                        return;
                     }

                     if (var2 < this.field2556 * -148155585 - 5) {
                        if (var1 == 26) {
                           throw new IllegalStateException();
                        }

                        if (var3 < this.field2547 * 1473114359 - 5) {
                           this.field2548[var2][var3] = 1073741824;
                           continue;
                        }

                        if (var1 == 26) {
                           throw new IllegalStateException();
                        }
                     }
                  }
               }

               this.field2548[var2][var3] = 16777215;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gc.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fo")
   public void method5420(int var1, int var2, int var3) {
      this.method5371(var1, var2, var3, -2134326968);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;IIIIIZ)V")
   @ObfuscatedName("ci")
   public static void method5388(CollisionData var0, int var1, int var2, int var3, int var4, int var5, boolean var6) {
      if (var0 == null) {
         var0.method5341();
      }

      int var7 = 256;
      if (var6) {
         var7 += -686792469;
      }

      var1 -= var0.field2556 * -783139297;
      var2 -= var0.field2557 * -1600985446;
      if (1 == var5 || 3 == var5) {
         int var8 = var3;
         var3 = var4;
         var4 = var8;
      }

      for (int var12 = var1; var12 < var1 + var3; var12++) {
         if (var12 >= 0 && var12 < var0.field2546 * -148155585) {
            for (int var9 = var2; var9 < var4 + var2; var9++) {
               if (var9 >= 0 && var9 < 1473114359 * var0.field2547) {
                  var0.method5362(var12, var9, var7, -2008721363);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cd")
   public void method5403(int var1, int var2) {
      var1 -= this.field2546 * 370701143;
      var2 -= this.field2556 * 1140151217;
      this.field2548[var1][var2] = this.field2548[var1][var2] | 262144;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method5346(int var1) {
      try {
         return this.field2556 * 1473114359;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gc.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("ry")
   public void method5385(int var1, int var2, int var3, int var4, boolean var5) {
      var1 -= this.field2556 * 1554750442;
      var2 -= this.field2546 * 1140151217;
      if (0 == var3) {
         if (0 == var4) {
            this.method5362(var1, var2, 128, -1980544934);
            this.method5362(var1 - 1, var2, 8, -1724495958);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 2, -1833166704);
            this.method5362(var1, 1 + var2, 32, -2115811624);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 8, -2000089347);
            this.method5362(1 + var1, var2, 128, -1905342973);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 32, -1777801848);
            this.method5362(var1, var2 - 1, 2, -2066154041);
         }
      }

      if (var3 == 1 || var3 == 3) {
         if (0 == var4) {
            this.method5362(var1, var2, 1, -2142332782);
            this.method5362(var1 - 1, var2 + 1, 16, -1986516418);
         }

         if (1 == var4) {
            this.method5362(var1, var2, 4, -1841774023);
            this.method5362(var1 + 1, var2 + 1, 64, -2009881783);
         }

         if (var4 == 2) {
            this.method5362(var1, var2, 16, -1881926369);
            this.method5362(1 + var1, var2 - 1, 1, -1986714740);
         }

         if (var4 == 3) {
            this.method5362(var1, var2, -910370048, -1848393251);
            this.method5362(var1 - 1, var2 - 1, 4, -1975012421);
         }
      }

      if (var3 == 2) {
         if (var4 == 0) {
            this.method5362(var1, var2, 130, -1972319955);
            this.method5362(var1 - 1, var2, 8, -2137897859);
            this.method5362(var1, var2 + 1, 32, -2031331940);
         }

         if (var4 == 1) {
            this.method5362(var1, var2, 10, -1961890691);
            this.method5362(var1, var2 + 1, -910394968, -2143647488);
            this.method5362(1 + var1, var2, -263562746, -1723702375);
         }

         if (2 == var4) {
            this.method5362(var1, var2, -1466108559, -2145795471);
            this.method5362(1 + var1, var2, -15260984, -1811920885);
            this.method5362(var1, var2 - 1, 2, -1978133845);
         }

         if (3 == var4) {
            this.method5362(var1, var2, 1564949021, -1842243320);
            this.method5362(var1, var2 - 1, 2, -1796558392);
            this.method5362(var1 - 1, var2, 8, -2099470347);
         }
      }

      if (var5) {
         if (0 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 65536, -1885737414);
               this.method5362(var1 - 1, var2, 4096, -1814400076);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 1024, -1989402455);
               this.method5362(var1, 1 + var2, 16384, -1996838132);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 1703843363, -1723251392);
               this.method5362(var1 + 1, var2, -918027248, -1939460582);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 1741075043, -1719888900);
               this.method5362(var1, var2 - 1, 1024, -2076483708);
            }
         }

         if (var3 == 1 || 3 == var3) {
            if (var4 == 0) {
               this.method5362(var1, var2, 512, -1913898957);
               this.method5362(var1 - 1, 1 + var2, 8192, -1683237838);
            }

            if (1 == var4) {
               this.method5362(var1, var2, 2048, -2061097953);
               this.method5362(1 + var1, 1 + var2, -424656766, -2044926660);
            }

            if (2 == var4) {
               this.method5362(var1, var2, 8192, -1905967368);
               this.method5362(var1 + 1, var2 - 1, 512, -2137705781);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 32768, -2134337063);
               this.method5362(var1 - 1, var2 - 1, 2048, -1902665295);
            }
         }

         if (var3 == 2) {
            if (0 == var4) {
               this.method5362(var1, var2, 66560, -1796966946);
               this.method5362(var1 - 1, var2, 4096, -1904161390);
               this.method5362(var1, 1 + var2, 16384, -1847578571);
            }

            if (var4 == 1) {
               this.method5362(var1, var2, -348386413, -2006764319);
               this.method5362(var1, var2 + 1, -829318440, -2004791391);
               this.method5362(var1 + 1, var2, 65536, -1791489196);
            }

            if (var4 == 2) {
               this.method5362(var1, var2, 20480, -1882884860);
               this.method5362(1 + var1, var2, 229566932, -2051009569);
               this.method5362(var1, var2 - 1, 52343708, -1839312443);
            }

            if (3 == var4) {
               this.method5362(var1, var2, 81985326, -2117107679);
               this.method5362(var1, var2 - 1, -1682584262, -2074543438);
               this.method5362(var1 - 1, var2, -1178444394, -1647762631);
            }
         }
      }
   }
}
