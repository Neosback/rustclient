import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hh")
public class classHH {
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final int field2795 = 46;
   @ObfuscatedName("af")
   byte[][][] field2798;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   public static final int field2796 = 18;
   @ObfuscatedName("az")
   int field2793;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field2794 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("cs")
   static final int field2797 = 115;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   void method6066() {
      byte[] var1 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
      int var2 = 0;

      for (int var3 = this.field2793 * 1966526081 - 1; var3 >= 0; var3--) {
         for (int var4 = 1966526081 * this.field2793 - 1; var4 >= 0; var4--) {
            if (var4 <= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var11 = 1966526081 * this.field2793 - 1; var11 >= 0; var11--) {
         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            if (var14 >= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var12 = 0; var12 < 1966526081 * this.field2793; var12++) {
         for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
            if (var15 <= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][2] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var13 = 0; var13 < this.field2793 * 1966526081; var13++) {
         for (int var16 = 1966526081 * this.field2793 - 1; var16 >= 0; var16--) {
            if (var16 >= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lhh;I)V")
   @ObfuscatedName("ae")
   public static void method6074(classHH var0, int var1) {
      if (var0 == null) {
         var0.method6058(var1);
      }

      try {
         byte[] var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         int var3 = 0;

         for (int var4 = var0.field2793 * 1966526081 - 1; var4 >= 0; var4--) {
            if (var1 == -2066089748) {
               throw new IllegalStateException();
            }

            for (int var5 = 1966526081 * var0.field2793 - 1; var5 >= 0; var5--) {
               if (var1 == -2066089748) {
                  throw new IllegalStateException();
               }

               if (var5 >= var4 >> 1) {
                  if (var1 == -2066089748) {
                     return;
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[4][0] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         var3 = 0;

         for (int var13 = var0.field2793 * 1966526081 - 1; var13 >= 0; var13--) {
            if (var1 == -2066089748) {
               throw new IllegalStateException();
            }

            for (int var16 = 0; var16 < 1966526081 * var0.field2793; var16++) {
               if (var1 == -2066089748) {
                  throw new IllegalStateException();
               }

               if (var16 <= var13 << 1) {
                  if (var1 == -2066089748) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[4][1] = var2;
         var2 = new byte[1966526081 * var0.field2793 * var0.field2793 * 1966526081];
         var3 = 0;

         for (int var14 = 0; var14 < var0.field2793 * 1966526081; var14++) {
            if (var1 == -2066089748) {
               throw new IllegalStateException();
            }

            for (int var17 = 0; var17 < 1966526081 * var0.field2793; var17++) {
               if (var1 == -2066089748) {
                  throw new IllegalStateException();
               }

               if (var17 >= var14 >> 1) {
                  if (var1 == -2066089748) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[4][2] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         var3 = 0;

         for (int var15 = 0; var15 < var0.field2793 * 1966526081; var15++) {
            if (var1 == -2066089748) {
               return;
            }

            for (int var18 = 1966526081 * var0.field2793 - 1; var18 >= 0; var18--) {
               if (var1 == -2066089748) {
                  throw new IllegalStateException();
               }

               if (var18 <= var15 << 1) {
                  if (var1 == -2066089748) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[4][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   void method6070() {
      byte[] var1 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
      int var2 = 0;

      for (int var3 = this.field2793 * 1966526081 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var4 >= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var11 = 0; var11 < 1966526081 * this.field2793; var11++) {
         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            if (var14 <= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var12 = 0; var12 < 1966526081 * this.field2793; var12++) {
         for (int var15 = 1966526081 * this.field2793 - 1; var15 >= 0; var15--) {
            if (var15 >= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][2] = var1;
      var1 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var13 = this.field2793 * 1966526081 - 1; var13 >= 0; var13--) {
         for (int var16 = this.field2793 * 1966526081 - 1; var16 >= 0; var16--) {
            if (var16 <= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   void method6054(int var1) {
      try {
         if (null != this.field2798) {
            if (var1 < 1417202999) {
               ;
            }
         } else {
            this.field2798 = new byte[8][4][];
            this.method6059(-765631177);
            this.method6064(-1221611462);
            method6067(this, 1215487248);
            this.method6071((byte)84);
            method6074(this, 2092088359);
            this.method6077(-1757506717);
            method6082(this, -1120709063);
            this.method6084(-79698778);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hh.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   void method6059(int var1) {
      try {
         byte[] var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         int var3 = 0;

         for (int var4 = 0; var4 < this.field2793 * 1966526081; var4++) {
            if (var1 >= 79433715) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < 1966526081 * this.field2793; var5++) {
               if (var1 >= 79433715) {
                  throw new IllegalStateException();
               }

               if (var5 <= var4) {
                  if (var1 >= 79433715) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[0][0] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var13 = 1966526081 * this.field2793 - 1; var13 >= 0; var13--) {
            if (var1 >= 79433715) {
               return;
            }

            for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
               if (var16 <= var13) {
                  if (var1 >= 79433715) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[0][1] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            if (var1 >= 79433715) {
               return;
            }

            for (int var17 = 0; var17 < 1966526081 * this.field2793; var17++) {
               if (var1 >= 79433715) {
                  throw new IllegalStateException();
               }

               if (var17 >= var14) {
                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[0][2] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var15 = 1966526081 * this.field2793 - 1; var15 >= 0; var15--) {
            if (var1 >= 79433715) {
               return;
            }

            for (int var18 = 0; var18 < this.field2793 * 1966526081; var18++) {
               if (var1 >= 79433715) {
                  throw new IllegalStateException();
               }

               if (var18 >= var15) {
                  if (var1 >= 79433715) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[0][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Ldh;IIJ)V")
   @ObfuscatedName("cs")
   static void method6088(WorldView var0, Actor var1, int var2, int var3, long var4) {
      try {
         if (var1.field1445 * 49255335 != 0) {
            Texture.method5024(client.field807, var1.field1467);

            for (ActorSpotAnim var6 = (ActorSpotAnim)client.field807.method13336(); null != var6; var6 = (ActorSpotAnim)client.field807.next()) {
               if (1612595797 * client.field856 >= 1168432457 * var6.field5959) {
                  long var7 = var6.method10496(2103253898) ? var4 : -1L;
                  var0.field1687.method4499(var2, var1.field1484 * -1547553299, -1272026483 * var1.field1486, var3, var6, var1.field1485 * 1457033841, var7);
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "hh.cs(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhh;)V")
   @ObfuscatedName("vc")
   public static void method6062(classHH var0) {
      byte[] var1 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
      int var2 = 0;

      for (int var3 = 1966526081 * var0.field2793 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < var0.field2793 * -986072301; var4++) {
            if (var4 <= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[1][0] = var1;
      var1 = new byte[var0.field2793 * 766595857 * 805251320 * var0.field2793];
      var2 = 0;

      for (int var11 = 0; var11 < 1966526081 * var0.field2793; var11++) {
         for (int var14 = 0; var14 < 1966526081 * var0.field2793; var14++) {
            if (var2 >= 0 && var2 < var1.length) {
               if (var14 >= var11 << 1) {
                  var1[var2] = -1;
               }

               var2++;
            } else {
               var2++;
            }
         }
      }

      var0.field2798[1][1] = var1;
      var1 = new byte[-2125093505 * var0.field2793 * var0.field2793 * 1151256729];
      var2 = 0;

      for (int var12 = 0; var12 < var0.field2793 * 706579257; var12++) {
         for (int var15 = var0.field2793 * -551478393 - 1; var15 >= 0; var15--) {
            if (var15 <= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[1][2] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
      var2 = 0;

      for (int var13 = 1966526081 * var0.field2793 - 1; var13 >= 0; var13--) {
         for (int var16 = 1966526081 * var0.field2793 - 1; var16 >= 0; var16--) {
            if (var16 >= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[1][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ac")
   void method6071(byte var1) {
      try {
         byte[] var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         int var3 = 0;

         for (int var4 = this.field2793 * 1966526081 - 1; var4 >= 0; var4--) {
            if (var1 <= 3) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < 1966526081 * this.field2793; var5++) {
               if (var5 >= var4 >> 1) {
                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[3][0] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var13 = 0; var13 < 1966526081 * this.field2793; var13++) {
            if (var1 <= 3) {
               return;
            }

            for (int var16 = 0; var16 < 1966526081 * this.field2793; var16++) {
               if (var1 <= 3) {
                  return;
               }

               if (var16 <= var13 << 1) {
                  if (var1 <= 3) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[3][1] = var2;
         var2 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
         var3 = 0;

         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            for (int var17 = 1966526081 * this.field2793 - 1; var17 >= 0; var17--) {
               if (var1 <= 3) {
                  throw new IllegalStateException();
               }

               if (var17 >= var14 >> 1) {
                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[3][2] = var2;
         var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         var3 = 0;

         for (int var15 = this.field2793 * 1966526081 - 1; var15 >= 0; var15--) {
            if (var1 <= 3) {
               return;
            }

            for (int var18 = this.field2793 * 1966526081 - 1; var18 >= 0; var18--) {
               if (var1 <= 3) {
                  throw new IllegalStateException();
               }

               if (var18 <= var15 << 1) {
                  if (var1 <= 3) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[3][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   void method6077(int var1) {
      try {
         byte[] var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         int var3 = 0;
         var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var1 >= -106583783) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < 1966526081 * this.field2793; var5++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var5 <= this.field2793 * 1966526081 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][0] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
            for (int var18 = 0; var18 < this.field2793 * 1966526081; var18++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var15 <= 1966526081 * this.field2793 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][1] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
            if (var1 >= -106583783) {
               throw new IllegalStateException();
            }

            for (int var19 = 0; var19 < this.field2793 * 1966526081; var19++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var19 >= this.field2793 * 1966526081 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][2] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var17 = 0; var17 < this.field2793 * 1966526081; var17++) {
            for (int var20 = 0; var20 < this.field2793 * 1966526081; var20++) {
               if (var17 >= 1966526081 * this.field2793 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhh;)V")
   @ObfuscatedName("wr")
   public static void method6072(classHH var0) {
      byte[] var1 = new byte[1966526081 * var0.field2793 * 1966526081 * var0.field2793];
      int var2 = 0;

      for (int var3 = var0.field2793 * 1966526081 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < 1966526081 * var0.field2793; var4++) {
            if (var4 >= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[3][0] = var1;
      var1 = new byte[var0.field2793 * -382224420 * var0.field2793 * 1966526081];
      var2 = 0;

      for (int var11 = 0; var11 < 1966526081 * var0.field2793; var11++) {
         for (int var14 = 0; var14 < 1966526081 * var0.field2793; var14++) {
            if (var14 <= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[3][1] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * 1178790533 * var0.field2793];
      var2 = 0;

      for (int var12 = 0; var12 < -146580325 * var0.field2793; var12++) {
         for (int var15 = 1966526081 * var0.field2793 - 1; var15 >= 0; var15--) {
            if (var15 >= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[3][2] = var1;
      var1 = new byte[-142347060 * var0.field2793 * 989013989 * var0.field2793];
      var2 = 0;

      for (int var13 = var0.field2793 * 84012071 - 1; var13 >= 0; var13--) {
         for (int var16 = var0.field2793 * 1966526081 - 1; var16 >= 0; var16--) {
            if (var16 <= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[3][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lhh;I)V")
   @ObfuscatedName("zc")
   public static void method6067(classHH var0, int var1) {
      if (var0 == null) {
         var0.method6080(var1);
      }

      try {
         byte[] var2 = new byte[1966526081 * var0.field2793 * 1966526081 * var0.field2793];
         int var3 = 0;

         for (int var4 = var0.field2793 * 1966526081 - 1; var4 >= 0; var4--) {
            if (var1 == -324749371) {
               throw new IllegalStateException();
            }

            for (int var5 = 1966526081 * var0.field2793 - 1; var5 >= 0; var5--) {
               if (var1 == -324749371) {
                  throw new IllegalStateException();
               }

               if (var5 <= var4 >> 1) {
                  if (var1 == -324749371) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[2][0] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * var0.field2793 * 1966526081];
         var3 = 0;

         for (int var13 = 1966526081 * var0.field2793 - 1; var13 >= 0; var13--) {
            if (var1 == -324749371) {
               throw new IllegalStateException();
            }

            for (int var16 = 0; var16 < 1966526081 * var0.field2793; var16++) {
               if (var1 == -324749371) {
                  throw new IllegalStateException();
               }

               if (var16 >= var13 << 1) {
                  if (var1 == -324749371) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[2][1] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         var3 = 0;

         for (int var14 = 0; var14 < 1966526081 * var0.field2793; var14++) {
            if (var1 == -324749371) {
               throw new IllegalStateException();
            }

            for (int var17 = 0; var17 < var0.field2793 * 1966526081; var17++) {
               if (var1 == -324749371) {
                  throw new IllegalStateException();
               }

               if (var17 <= var14 >> 1) {
                  if (var1 == -324749371) {
                     return;
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[2][2] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         var3 = 0;

         for (int var15 = 0; var15 < var0.field2793 * 1966526081; var15++) {
            if (var1 == -324749371) {
               throw new IllegalStateException();
            }

            for (int var18 = 1966526081 * var0.field2793 - 1; var18 >= 0; var18--) {
               if (var1 == -324749371) {
                  throw new IllegalStateException();
               }

               if (var18 >= var15 << 1) {
                  if (var1 == -324749371) {
                     return;
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[2][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   void method6084(int var1) {
      try {
         byte[] var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         int var3 = 0;
         var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         var3 = 0;

         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            for (int var5 = 0; var5 < 1966526081 * this.field2793; var5++) {
               if (var1 >= 1258966269) {
                  throw new IllegalStateException();
               }

               if (var5 >= var4 - this.field2793 * 1966526081 / 2) {
                  if (var1 >= 1258966269) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[7][0] = var2;
         var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var15 = this.field2793 * 1966526081 - 1; var15 >= 0; var15--) {
            if (var1 >= 1258966269) {
               throw new IllegalStateException();
            }

            for (int var18 = 0; var18 < 1966526081 * this.field2793; var18++) {
               if (var1 >= 1258966269) {
                  return;
               }

               if (var18 >= var15 - 1966526081 * this.field2793 / 2) {
                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[7][1] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var16 = 1966526081 * this.field2793 - 1; var16 >= 0; var16--) {
            if (var1 >= 1258966269) {
               return;
            }

            for (int var19 = this.field2793 * 1966526081 - 1; var19 >= 0; var19--) {
               if (var1 >= 1258966269) {
                  throw new IllegalStateException();
               }

               if (var19 >= var16 - 1966526081 * this.field2793 / 2) {
                  if (var1 >= 1258966269) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[7][2] = var2;
         var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var17 = 0; var17 < this.field2793 * 1966526081; var17++) {
            if (var1 >= 1258966269) {
               throw new IllegalStateException();
            }

            for (int var20 = this.field2793 * 1966526081 - 1; var20 >= 0; var20--) {
               if (var1 >= 1258966269) {
                  throw new IllegalStateException();
               }

               if (var20 >= var17 - 1966526081 * this.field2793 / 2) {
                  if (var1 >= 1258966269) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[7][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IS)Z")
   @ObfuscatedName("af")
   static final boolean method6069(int var0, short var1) {
      try {
         if (1 != var0) {
            if (var1 <= 1954) {
               throw new IllegalStateException();
            }

            if (2 != var0) {
               if (var1 <= 1954) {
                  throw new IllegalStateException();
               }

               if (var0 >= 3) {
                  if (var1 <= 1954) {
                     throw new IllegalStateException();
                  }

                  if (var0 <= 6) {
                     return true;
                  }

                  if (var1 <= 1954) {
                     throw new IllegalStateException();
                  }
               }

               if (var0 != 1001) {
                  return false;
               }

               if (var1 <= 1954) {
                  throw new IllegalStateException();
               }
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hh.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("ad")
   void method6043(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (0 != var7 && 1966526081 * this.field2793 != 0 && this.field2798 != null) {
         var8 = this.method6049(var8, var7, (byte)5);
         var7 = this.method6051(var7, (byte)44);
         classYW.method13803(var1, var2, var5, var6, var3, var4, this.field2798[var7 - 1][var8], 1966526081 * this.field2793, true);
      }
   }

   classHH(int var1) {
      this.field2793 = -423923327 * var1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("ay")
   void method6044(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (0 != var7 && 1966526081 * this.field2793 != 0 && this.field2798 != null) {
         var8 = this.method6049(var8, var7, (byte)-2);
         var7 = this.method6051(var7, (byte)54);
         classYW.method13803(var1, var2, var5, var6, var3, var4, this.field2798[var7 - 1][var8], 1966526081 * this.field2793, true);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;I)V")
   @ObfuscatedName("uf")
   public static void method6082(classHH var0, int var1) {
      if (var0 == null) {
         var0.method6081(var1);
      }

      try {
         byte[] var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         int var3 = 0;
         var2 = new byte[1966526081 * var0.field2793 * var0.field2793 * 1966526081];
         var3 = 0;

         for (int var4 = 0; var4 < 1966526081 * var0.field2793; var4++) {
            if (var1 >= -350545080) {
               return;
            }

            for (int var5 = 0; var5 < var0.field2793 * 1966526081; var5++) {
               if (var1 >= -350545080) {
                  throw new IllegalStateException();
               }

               if (var5 <= var4 - var0.field2793 * 1966526081 / 2) {
                  if (var1 >= -350545080) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[6][0] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * var0.field2793 * 1966526081];
         var3 = 0;

         for (int var15 = var0.field2793 * 1966526081 - 1; var15 >= 0; var15--) {
            if (var1 >= -350545080) {
               return;
            }

            for (int var18 = 0; var18 < 1966526081 * var0.field2793; var18++) {
               if (var1 >= -350545080) {
                  return;
               }

               if (var18 <= var15 - var0.field2793 * 1966526081 / 2) {
                  if (var1 >= -350545080) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[6][1] = var2;
         var2 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
         var3 = 0;

         for (int var16 = 1966526081 * var0.field2793 - 1; var16 >= 0; var16--) {
            if (var1 >= -350545080) {
               return;
            }

            for (int var19 = 1966526081 * var0.field2793 - 1; var19 >= 0; var19--) {
               if (var1 >= -350545080) {
                  throw new IllegalStateException();
               }

               if (var19 <= var16 - 1966526081 * var0.field2793 / 2) {
                  if (var1 >= -350545080) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[6][2] = var2;
         var2 = new byte[1966526081 * var0.field2793 * 1966526081 * var0.field2793];
         var3 = 0;

         for (int var17 = 0; var17 < 1966526081 * var0.field2793; var17++) {
            if (var1 >= -350545080) {
               return;
            }

            for (int var20 = 1966526081 * var0.field2793 - 1; var20 >= 0; var20--) {
               if (var20 <= var17 - var0.field2793 * 1966526081 / 2) {
                  if (var1 >= -350545080) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         var0.field2798[6][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhh;)V")
   @ObfuscatedName("gk")
   public static void method6060(classHH var0) {
      byte[] var1 = new byte[1966526081 * var0.field2793 * var0.field2793 * 1966526081];
      int var2 = 0;

      for (int var3 = 0; var3 < var0.field2793 * 1966526081; var3++) {
         for (int var4 = 0; var4 < 1966526081 * var0.field2793; var4++) {
            if (var4 <= var3) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[0][0] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * var0.field2793 * 1966526081];
      var2 = 0;

      for (int var11 = 1966526081 * var0.field2793 - 1; var11 >= 0; var11--) {
         for (int var14 = 0; var14 < var0.field2793 * 1966526081; var14++) {
            if (var14 <= var11) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[0][1] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * var0.field2793 * 1966526081];
      var2 = 0;

      for (int var12 = 0; var12 < 1966526081 * var0.field2793; var12++) {
         for (int var15 = 0; var15 < 1966526081 * var0.field2793; var15++) {
            if (var15 >= var12) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[0][2] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * var0.field2793 * 1966526081];
      var2 = 0;

      for (int var13 = 1966526081 * var0.field2793 - 1; var13 >= 0; var13--) {
         for (int var16 = 0; var16 < var0.field2793 * 1966526081; var16++) {
            if (var16 >= var13) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[0][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ae")
   int method6051(int var1, byte var2) {
      try {
         if (9 != var1) {
            if (var2 <= 0) {
               throw new IllegalStateException();
            }

            if (var1 != 10) {
               if (var1 == 11) {
                  return 8;
               }

               return var1;
            }

            if (var2 <= 0) {
               throw new IllegalStateException();
            }
         }

         return 1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "hh.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   void method6063() {
      byte[] var1 = new byte[this.field2793 * -1179191606 * -2111278427 * this.field2793];
      int var2 = 0;

      for (int var3 = 1892782012 * this.field2793 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < this.field2793 * 1385383905; var4++) {
            if (var4 <= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[1][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * -1158749411 * this.field2793];
      var2 = 0;

      for (int var11 = 0; var11 < 1966526081 * this.field2793; var11++) {
         for (int var14 = 0; var14 < 1472599212 * this.field2793; var14++) {
            if (var2 >= 0 && var2 < var1.length) {
               if (var14 >= var11 << 1) {
                  var1[var2] = -1;
               }

               var2++;
            } else {
               var2++;
            }
         }
      }

      this.field2798[1][1] = var1;
      var1 = new byte[-586639119 * this.field2793 * this.field2793 * -495767958];
      var2 = 0;

      for (int var12 = 0; var12 < this.field2793 * -920890785; var12++) {
         for (int var15 = this.field2793 * 1966526081 - 1; var15 >= 0; var15--) {
            if (var15 <= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[1][2] = var1;
      var1 = new byte[this.field2793 * 1528503359 * -370296244 * this.field2793];
      var2 = 0;

      for (int var13 = -537924661 * this.field2793 - 1; var13 >= 0; var13--) {
         for (int var16 = 1966526081 * this.field2793 - 1; var16 >= 0; var16--) {
            if (var16 >= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[1][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   int method6052(int var1) {
      if (9 == var1 || var1 == 10) {
         return 1;
      } else {
         return var1 == 11 ? 8 : var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("aq")
   void method6045(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (0 != var7 && 1966526081 * this.field2793 != 0 && this.field2798 != null) {
         var8 = this.method6049(var8, var7, (byte)-29);
         var7 = this.method6051(var7, (byte)91);
         classYW.method13803(var1, var2, var5, var6, var3, var4, this.field2798[var7 - 1][var8], -422487276 * this.field2793, true);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("ap")
   void method6046(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (0 != var7 && 1966526081 * this.field2793 != 0 && this.field2798 != null) {
         var8 = this.method6049(var8, var7, (byte)-20);
         var7 = this.method6051(var7, (byte)13);
         classYW.method13803(var1, var2, var5, var6, var3, var4, this.field2798[var7 - 1][var8], -1543916918 * this.field2793, true);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhh;)V")
   @ObfuscatedName("yb")
   public static void method6055(classHH var0) {
      if (null == var0.field2798) {
         var0.field2798 = new byte[8][4][];
         var0.method6059(-1119773031);
         var0.method6064(-1602591116);
         method6067(var0, -527976127);
         var0.method6071((byte)106);
         method6074(var0, -594536814);
         var0.method6077(-344248597);
         method6082(var0, -990139903);
         var0.method6084(1190641041);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   void method6056() {
      if (null == this.field2798) {
         this.field2798 = new byte[8][4][];
         this.method6059(-98635826);
         this.method6064(-1648850880);
         method6067(this, -1304316058);
         this.method6071((byte)117);
         method6074(this, -1939933608);
         this.method6077(-1213012945);
         method6082(this, -414934560);
         this.method6084(1075928771);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bp")
   void method6057() {
      if (null == this.field2798) {
         this.field2798 = new byte[8][4][];
         this.method6059(-1310176922);
         this.method6064(-637360630);
         method6067(this, -354414751);
         this.method6071((byte)64);
         method6074(this, 1467721390);
         this.method6077(-1069318237);
         method6082(this, -1116332431);
         this.method6084(-1935185519);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bw")
   void method6061() {
      byte[] var1 = new byte[1966526081 * this.field2793 * this.field2793 * -1128185679];
      int var2 = 0;

      for (int var3 = 0; var3 < this.field2793 * -1057787703; var3++) {
         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var4 <= var3) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[0][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var11 = 1966526081 * this.field2793 - 1; var11 >= 0; var11--) {
         for (int var14 = 0; var14 < this.field2793 * -57055142; var14++) {
            if (var14 <= var11) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[0][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var12 = 0; var12 < 1966526081 * this.field2793; var12++) {
         for (int var15 = 0; var15 < -622764471 * this.field2793; var15++) {
            if (var15 >= var12) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[0][2] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * -390089108];
      var2 = 0;

      for (int var13 = -2065972051 * this.field2793 - 1; var13 >= 0; var13--) {
         for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
            if (var16 >= var13) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[0][3] = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhh;)V")
   @ObfuscatedName("hm")
   public static void method6085(classHH var0) {
      if (var0 == null) {
         var0.method6075();
      }

      byte[] var1 = new byte[1093632563 * var0.field2793 * 1966526081 * var0.field2793];
      int var2 = 0;
      var1 = new byte[1966526081 * var0.field2793 * -1079618244 * var0.field2793];
      var2 = 0;

      for (int var3 = 0; var3 < 950179958 * var0.field2793; var3++) {
         for (int var4 = 0; var4 < 1966526081 * var0.field2793; var4++) {
            if (var4 >= var3 - var0.field2793 * -1619308330 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[7][0] = var1;
      var1 = new byte[-864676662 * var0.field2793 * var0.field2793 * -318268472];
      var2 = 0;

      for (int var13 = var0.field2793 * 1966526081 - 1; var13 >= 0; var13--) {
         for (int var16 = 0; var16 < 1966526081 * var0.field2793; var16++) {
            if (var16 >= var13 - 1966526081 * var0.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[7][1] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * var0.field2793 * 2024229408];
      var2 = 0;

      for (int var14 = -984087136 * var0.field2793 - 1; var14 >= 0; var14--) {
         for (int var17 = var0.field2793 * 1966526081 - 1; var17 >= 0; var17--) {
            if (var17 >= var14 - -2095603240 * var0.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[7][2] = var1;
      var1 = new byte[-1474652087 * var0.field2793 * var0.field2793 * 1966526081];
      var2 = 0;

      for (int var15 = 0; var15 < var0.field2793 * 1150389672; var15++) {
         for (int var18 = var0.field2793 * 1468210804 - 1; var18 >= 0; var18--) {
            if (var18 >= var15 - 1948078276 * var0.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[7][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("af")
   int method6049(int var1, int var2, byte var3) {
      try {
         if (9 == var2) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            var1 = 1 + var1 & 3;
         }

         if (var2 == 10) {
            if (var3 == 1) {
               throw new IllegalStateException();
            }

            var1 = var1 + 3 & 3;
         }

         if (11 == var2) {
            var1 = 3 + var1 & 3;
         }

         return var1;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "hh.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   void method6068() {
      byte[] var1 = new byte[1966526081 * this.field2793 * -998161041 * this.field2793];
      int var2 = 0;

      for (int var3 = this.field2793 * 928232500 - 1; var3 >= 0; var3--) {
         for (int var4 = 1966526081 * this.field2793 - 1; var4 >= 0; var4--) {
            if (var4 <= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][0] = var1;
      var1 = new byte[this.field2793 * 464866844 * this.field2793 * -720591219];
      var2 = 0;

      for (int var11 = -128001422 * this.field2793 - 1; var11 >= 0; var11--) {
         for (int var14 = 0; var14 < -754432249 * this.field2793; var14++) {
            if (var14 >= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][1] = var1;
      var1 = new byte[this.field2793 * -584020568 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var12 = 0; var12 < -17483011 * this.field2793; var12++) {
         for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
            if (var15 <= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][2] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var13 = 0; var13 < this.field2793 * 1966526081; var13++) {
         for (int var16 = 1966526081 * this.field2793 - 1; var16 >= 0; var16--) {
            if (var16 >= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[2][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   void method6083() {
      byte[] var1 = new byte[this.field2793 * 1966526081 * -1686101039 * this.field2793];
      int var2 = 0;
      var1 = new byte[-1299975728 * this.field2793 * this.field2793 * 932173975];
      var2 = 0;

      for (int var3 = 0; var3 < 1966526081 * this.field2793; var3++) {
         for (int var4 = 0; var4 < this.field2793 * 758429403; var4++) {
            if (var4 <= var3 - this.field2793 * 1966526081 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[6][0] = var1;
      var1 = new byte[this.field2793 * -588113431 * this.field2793 * 1785927516];
      var2 = 0;

      for (int var13 = this.field2793 * 1966526081 - 1; var13 >= 0; var13--) {
         for (int var16 = 0; var16 < 954594769 * this.field2793; var16++) {
            if (var16 <= var13 - this.field2793 * 1966526081 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[6][1] = var1;
      var1 = new byte[this.field2793 * 220393792 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var14 = -1728613637 * this.field2793 - 1; var14 >= 0; var14--) {
         for (int var17 = 1966526081 * this.field2793 - 1; var17 >= 0; var17--) {
            if (var17 <= var14 - -658378580 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[6][2] = var1;
      var1 = new byte[736069148 * this.field2793 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var15 = 0; var15 < 228850112 * this.field2793; var15++) {
         for (int var18 = 1966526081 * this.field2793 - 1; var18 >= 0; var18--) {
            if (var18 <= var15 - this.field2793 * 1904358585 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[6][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   void method6073() {
      byte[] var1 = new byte[1966526081 * this.field2793 * -1149994002 * this.field2793];
      int var2 = 0;

      for (int var3 = this.field2793 * 1966526081 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < -1785901634 * this.field2793; var4++) {
            if (var4 >= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 156544363];
      var2 = 0;

      for (int var11 = 0; var11 < -1334582242 * this.field2793; var11++) {
         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            if (var14 <= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var12 = 0; var12 < 1245983870 * this.field2793; var12++) {
         for (int var15 = 1966526081 * this.field2793 - 1; var15 >= 0; var15--) {
            if (var15 >= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][2] = var1;
      var1 = new byte[1966526081 * this.field2793 * -31641891 * this.field2793];
      var2 = 0;

      for (int var13 = this.field2793 * -1777406430 - 1; var13 >= 0; var13--) {
         for (int var16 = this.field2793 * -1534795972 - 1; var16 >= 0; var16--) {
            if (var16 <= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[3][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("au")
   int method6050(int var1, int var2) {
      if (9 == var2) {
         var1 = 1 + var1 & 3;
      }

      if (var2 == 10) {
         var1 = var1 + 3 & 3;
      }

      if (11 == var2) {
         var1 = 3 + var1 & 3;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   void method6075() {
      byte[] var1 = new byte[this.field2793 * 1260012386 * -2056530486 * this.field2793];
      int var2 = 0;

      for (int var3 = this.field2793 * 1966526081 - 1; var3 >= 0; var3--) {
         for (int var4 = -1117534390 * this.field2793 - 1; var4 >= 0; var4--) {
            if (var4 >= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][0] = var1;
      var1 = new byte[this.field2793 * 1469799415 * -1628091254 * this.field2793];
      var2 = 0;

      for (int var11 = this.field2793 * 715326988 - 1; var11 >= 0; var11--) {
         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            if (var14 <= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][1] = var1;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var12 = 0; var12 < this.field2793 * 283750147; var12++) {
         for (int var15 = 0; var15 < 1966526081 * this.field2793; var15++) {
            if (var15 >= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][2] = var1;
      var1 = new byte[this.field2793 * 980275457 * -291694239 * this.field2793];
      var2 = 0;

      for (int var13 = 0; var13 < this.field2793 * 1966526081; var13++) {
         for (int var16 = 2134666943 * this.field2793 - 1; var16 >= 0; var16--) {
            if (var16 <= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   void method6076() {
      byte[] var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      int var2 = 0;

      for (int var3 = this.field2793 * 1966526081 - 1; var3 >= 0; var3--) {
         for (int var4 = 1966526081 * this.field2793 - 1; var4 >= 0; var4--) {
            if (var4 >= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var11 = this.field2793 * 1966526081 - 1; var11 >= 0; var11--) {
         for (int var14 = 0; var14 < 1966526081 * this.field2793; var14++) {
            if (var14 <= var11 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][1] = var1;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var12 = 0; var12 < this.field2793 * 1966526081; var12++) {
         for (int var15 = 0; var15 < 1966526081 * this.field2793; var15++) {
            if (var15 >= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][2] = var1;
      var1 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
      var2 = 0;

      for (int var13 = 0; var13 < this.field2793 * 1966526081; var13++) {
         for (int var16 = 1966526081 * this.field2793 - 1; var16 >= 0; var16--) {
            if (var16 <= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[4][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   void method6078() {
      byte[] var1 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
      int var2 = 0;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var3 = 0; var3 < 1966526081 * this.field2793; var3++) {
         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var4 <= this.field2793 * 1966526081 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var13 = 0; var13 < this.field2793 * 1966526081; var13++) {
         for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
            if (var13 <= 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var14 = 0; var14 < this.field2793 * 1966526081; var14++) {
         for (int var17 = 0; var17 < this.field2793 * 1966526081; var17++) {
            if (var17 >= this.field2793 * 1966526081 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][2] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
         for (int var18 = 0; var18 < this.field2793 * 1966526081; var18++) {
            if (var15 >= 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   void method6079() {
      byte[] var1 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
      int var2 = 0;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var3 = 0; var3 < 1966526081 * this.field2793; var3++) {
         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var4 <= this.field2793 * 1966526081 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][0] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var13 = 0; var13 < this.field2793 * 1966526081; var13++) {
         for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
            if (var13 <= 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var14 = 0; var14 < this.field2793 * 1966526081; var14++) {
         for (int var17 = 0; var17 < this.field2793 * 1966526081; var17++) {
            if (var17 >= this.field2793 * 1966526081 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][2] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
         for (int var18 = 0; var18 < this.field2793 * 1966526081; var18++) {
            if (var15 >= 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[5][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bm")
   void method6086() {
      byte[] var1 = new byte[1411282603 * this.field2793 * 1157884606 * this.field2793];
      int var2 = 0;
      var1 = new byte[1966526081 * this.field2793 * -66741249 * this.field2793];
      var2 = 0;

      for (int var3 = 0; var3 < 1966526081 * this.field2793; var3++) {
         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var4 >= var3 - this.field2793 * 1893548518 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][0] = var1;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var13 = this.field2793 * -1875543418 - 1; var13 >= 0; var13--) {
         for (int var16 = 0; var16 < -1741534208 * this.field2793; var16++) {
            if (var16 >= var13 - 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][1] = var1;
      var1 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var14 = -1797697569 * this.field2793 - 1; var14 >= 0; var14--) {
         for (int var17 = this.field2793 * -584147576 - 1; var17 >= 0; var17--) {
            if (var17 >= var14 - 750734590 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][2] = var1;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
      var2 = 0;

      for (int var15 = 0; var15 < this.field2793 * 1202827998; var15++) {
         for (int var18 = this.field2793 * -50617423 - 1; var18 >= 0; var18--) {
            if (var18 >= var15 - 515861078 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bc")
   void method6087() {
      byte[] var1 = new byte[1966526081 * this.field2793 * -1480960665 * this.field2793];
      int var2 = 0;
      var1 = new byte[1966526081 * this.field2793 * -1384687418 * this.field2793];
      var2 = 0;

      for (int var3 = 0; var3 < 1966526081 * this.field2793; var3++) {
         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var4 >= var3 - this.field2793 * 1450368592 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][0] = var1;
      var1 = new byte[1966526081 * this.field2793 * this.field2793 * -719526159];
      var2 = 0;

      for (int var13 = this.field2793 * -2038624823 - 1; var13 >= 0; var13--) {
         for (int var16 = 0; var16 < -2068501705 * this.field2793; var16++) {
            if (var16 >= var13 - 953138437 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][1] = var1;
      var1 = new byte[this.field2793 * -1909966693 * this.field2793 * 1407282922];
      var2 = 0;

      for (int var14 = 419994865 * this.field2793 - 1; var14 >= 0; var14--) {
         for (int var17 = this.field2793 * -1036215421 - 1; var17 >= 0; var17--) {
            if (var17 >= var14 - 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][2] = var1;
      var1 = new byte[1329198938 * this.field2793 * this.field2793 * -197174134];
      var2 = 0;

      for (int var15 = 0; var15 < this.field2793 * -73760188; var15++) {
         for (int var18 = this.field2793 * -2103134658 - 1; var18 >= 0; var18--) {
            if (var18 >= var15 - 1966526081 * this.field2793 / 2) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      this.field2798[7][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   int method6053(int var1) {
      if (9 == var1 || var1 == 10) {
         return 1;
      } else {
         return var1 == 11 ? 8 : var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   void method6064(int var1) {
      try {
         byte[] var2 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
         int var3 = 0;

         for (int var4 = 1966526081 * this.field2793 - 1; var4 >= 0; var4--) {
            if (var1 >= -535838835) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < this.field2793 * 1966526081; var5++) {
               if (var1 >= -535838835) {
                  throw new IllegalStateException();
               }

               if (var5 <= var4 >> 1) {
                  if (var1 >= -535838835) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[1][0] = var2;
         var2 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
         var3 = 0;

         for (int var13 = 0; var13 < 1966526081 * this.field2793; var13++) {
            if (var1 >= -535838835) {
               return;
            }

            for (int var16 = 0; var16 < 1966526081 * this.field2793; var16++) {
               if (var1 >= -535838835) {
                  return;
               }

               if (var3 >= 0) {
                  if (var1 >= -535838835) {
                     throw new IllegalStateException();
                  }

                  if (var3 < var2.length) {
                     if (var16 >= var13 << 1) {
                        if (var1 >= -535838835) {
                           throw new IllegalStateException();
                        }

                        var2[var3] = -1;
                     }

                     var3++;
                     continue;
                  }

                  if (var1 >= -535838835) {
                     throw new IllegalStateException();
                  }
               }

               var3++;
            }
         }

         this.field2798[1][1] = var2;
         var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var14 = 0; var14 < this.field2793 * 1966526081; var14++) {
            if (var1 >= -535838835) {
               return;
            }

            for (int var17 = this.field2793 * 1966526081 - 1; var17 >= 0; var17--) {
               if (var1 >= -535838835) {
                  throw new IllegalStateException();
               }

               if (var17 <= var14 >> 1) {
                  if (var1 >= -535838835) {
                     return;
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[1][2] = var2;
         var2 = new byte[this.field2793 * 1966526081 * 1966526081 * this.field2793];
         var3 = 0;

         for (int var15 = 1966526081 * this.field2793 - 1; var15 >= 0; var15--) {
            if (var1 >= -535838835) {
               throw new IllegalStateException();
            }

            for (int var18 = 1966526081 * this.field2793 - 1; var18 >= 0; var18--) {
               if (var1 >= -535838835) {
                  throw new IllegalStateException();
               }

               if (var18 >= var15 << 1) {
                  if (var1 >= -535838835) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[1][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ft")
   static void method6089(boolean var0, int var1) {
      try {
         client.field976 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hh.ft(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lhh;)V")
   @ObfuscatedName("eu")
   public static void method6065(classHH var0) {
      byte[] var1 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
      int var2 = 0;

      for (int var3 = 1966526081 * var0.field2793 - 1; var3 >= 0; var3--) {
         for (int var4 = 0; var4 < var0.field2793 * 1966526081; var4++) {
            if (var4 <= var3 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[1][0] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
      var2 = 0;

      for (int var11 = 0; var11 < 1966526081 * var0.field2793; var11++) {
         for (int var14 = 0; var14 < 1966526081 * var0.field2793; var14++) {
            if (var2 >= 0 && var2 < var1.length) {
               if (var14 >= var11 << 1) {
                  var1[var2] = -1;
               }

               var2++;
            } else {
               var2++;
            }
         }
      }

      var0.field2798[1][1] = var1;
      var1 = new byte[1966526081 * var0.field2793 * var0.field2793 * 1966526081];
      var2 = 0;

      for (int var12 = 0; var12 < var0.field2793 * 1966526081; var12++) {
         for (int var15 = var0.field2793 * 1966526081 - 1; var15 >= 0; var15--) {
            if (var15 <= var12 >> 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[1][2] = var1;
      var1 = new byte[var0.field2793 * 1966526081 * 1966526081 * var0.field2793];
      var2 = 0;

      for (int var13 = 1966526081 * var0.field2793 - 1; var13 >= 0; var13--) {
         for (int var16 = 1966526081 * var0.field2793 - 1; var16 >= 0; var16--) {
            if (var16 >= var13 << 1) {
               var1[var2] = -1;
            }

            var2++;
         }
      }

      var0.field2798[1][3] = var1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIII)V")
   @ObfuscatedName("az")
   void method6047(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      try {
         if (0 != var7) {
            if (var9 != 980481800) {
               return;
            }

            if (1966526081 * this.field2793 != 0) {
               if (var9 != 980481800) {
                  throw new IllegalStateException();
               }

               if (this.field2798 != null) {
                  var8 = this.method6049(var8, var7, (byte)-58);
                  var7 = this.method6051(var7, (byte)125);
                  classYW.method13743(var1, var2, var5, var6, var3, var4, this.field2798[var7 - 1][var8], 1966526081 * this.field2793, true);
                  return;
               }

               if (var9 != 980481800) {
                  throw new IllegalStateException();
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "hh.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   void method6080(int var1) {
      try {
         byte[] var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         int var3 = 0;
         var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var1 >= -106583783) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < 1966526081 * this.field2793; var5++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var5 <= this.field2793 * 1966526081 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][0] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
            for (int var18 = 0; var18 < this.field2793 * 1966526081; var18++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var15 <= 1966526081 * this.field2793 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][1] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
            if (var1 >= -106583783) {
               throw new IllegalStateException();
            }

            for (int var19 = 0; var19 < this.field2793 * 1966526081; var19++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var19 >= this.field2793 * 1966526081 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][2] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var17 = 0; var17 < this.field2793 * 1966526081; var17++) {
            for (int var20 = 0; var20 < this.field2793 * 1966526081; var20++) {
               if (var17 >= 1966526081 * this.field2793 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method6058(int var1) {
      try {
         if (null != this.field2798) {
            if (var1 < 1417202999) {
               ;
            }
         } else {
            this.field2798 = new byte[8][4][];
            this.method6080(-765631177);
            this.method6064(-1221611462);
            method6067(this, 1215487248);
            this.method6071((byte)84);
            method6074(this, 2092088359);
            this.method6077(-1757506717);
            method6082(this, -1120709063);
            this.method6084(-79698778);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hh.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhh;IIIIIIIII)V")
   @ObfuscatedName("jt")
   public static void method6048(classHH var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      if (var0 == null) {
         var0.method6047(var1, var1, var1, var1, var1, var1, var1, var1, var1);
      }

      try {
         if (0 != var7) {
            if (var9 != 980481800) {
               return;
            }

            if (1966526081 * var0.field2793 != 0) {
               if (var9 != 980481800) {
                  throw new IllegalStateException();
               }

               if (var0.field2798 != null) {
                  var8 = var0.method6049(var8, var7, (byte)-58);
                  var7 = var0.method6051(var7, (byte)125);
                  classYW.method13803(var1, var2, var5, var6, var3, var4, var0.field2798[var7 - 1][var8], 1966526081 * var0.field2793, true);
                  return;
               }

               if (var9 != 980481800) {
                  throw new IllegalStateException();
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "hh.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method6081(int var1) {
      try {
         byte[] var2 = new byte[1966526081 * this.field2793 * 1966526081 * this.field2793];
         int var3 = 0;
         var2 = new byte[1966526081 * this.field2793 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var4 = 0; var4 < 1966526081 * this.field2793; var4++) {
            if (var1 >= -106583783) {
               throw new IllegalStateException();
            }

            for (int var5 = 0; var5 < 1966526081 * this.field2793; var5++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var5 <= this.field2793 * 1966526081 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][0] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var15 = 0; var15 < this.field2793 * 1966526081; var15++) {
            for (int var18 = 0; var18 < this.field2793 * 1966526081; var18++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var15 <= 1966526081 * this.field2793 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][1] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var16 = 0; var16 < this.field2793 * 1966526081; var16++) {
            if (var1 >= -106583783) {
               throw new IllegalStateException();
            }

            for (int var19 = 0; var19 < this.field2793 * 1966526081; var19++) {
               if (var1 >= -106583783) {
                  throw new IllegalStateException();
               }

               if (var19 >= this.field2793 * 1966526081 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][2] = var2;
         var2 = new byte[this.field2793 * 1966526081 * this.field2793 * 1966526081];
         var3 = 0;

         for (int var17 = 0; var17 < this.field2793 * 1966526081; var17++) {
            for (int var20 = 0; var20 < this.field2793 * 1966526081; var20++) {
               if (var17 >= 1966526081 * this.field2793 / 2) {
                  if (var1 >= -106583783) {
                     throw new IllegalStateException();
                  }

                  var2[var3] = -1;
               }

               var3++;
            }
         }

         this.field2798[5][3] = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hh.ao(" + ')');
      }
   }
}
