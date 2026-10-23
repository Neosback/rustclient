import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.runelite.api.events.PreMapLoad;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("rl4")
public class rl4 implements Runnable {
   @ObfuscatedName("pr")
   public byte[][][] field5793;
   @ObfuscatedName("vp")
   public byte[][][] field5794;
   @ObfuscatedName("lg")
   public byte[][][] field5792;
   @ObfuscatedName("hd")
   public final byte[][] field5771;
   @ObfuscatedName("gy")
   public final int[] field5779;
   @ObfuscatedName("eh")
   public final int field5767;
   @ObfuscatedName("ru")
   public final int field5764;
   @ObfuscatedSignature(descriptor = "[Lgc;")
   @ObfuscatedName("ml")
   public CollisionData[] field5790;
   @ObfuscatedName("pn")
   public final int field5760;
   @ObfuscatedName("hc")
   public final int field5763;
   @ObfuscatedName("mz")
   public int field5754;
   @ObfuscatedSignature(descriptor = "Lxk;")
   @ObfuscatedName("ed")
   public final classXK field5770;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("mz")
   public NodeCache field5755 = new NodeCache(64);
   @ObfuscatedName("dy")
   public int field5784;
   @ObfuscatedName("pb")
   public final int[] field5776;
   @ObfuscatedName("vm")
   public final int field5759;
   @ObfuscatedName("xx")
   public final int field5766;
   @ObfuscatedName("wh")
   public final int[][] field5775;
   @ObfuscatedName("tw")
   public int[][][] field5796;
   @ObfuscatedName("zf")
   public short[][][] field5798;
   @ObfuscatedName("ap")
   public final byte[][][] field5774;
   @ObfuscatedName("cs")
   public int[][][] field5795;
   @ObfuscatedName("ba")
   public static final ExecutorService field5782 = Executors.newSingleThreadExecutor(var0 -> {
      Thread var1 = new Thread(var0, "Map Loader");
      rl4.field5788 = var1;
      var1.setDaemon(true);
      return var1;
   });
   @ObfuscatedName("zg")
   public static Thread field5788;
   @ObfuscatedName("ng")
   public final int[] field5778;
   @ObfuscatedName("me")
   public final int[][][] field5773;
   @ObfuscatedName("ey")
   public final int field5765;
   @ObfuscatedName("qm")
   public int field5786;
   @ObfuscatedName("fp")
   public final DrawCallbacks field5758;
   @ObfuscatedName("ou")
   public int field5785;
   @ObfuscatedName("ti")
   public final int field5761;
   @ObfuscatedName("ss")
   public final int field5768;
   @ObfuscatedName("us")
   public final byte[][] field5772;
   @ObfuscatedSignature(descriptor = "Lez;")
   @ObfuscatedName("oh")
   public Scene field5787;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("gn")
   public NodeCache field5756 = new NodeCache(64);
   @ObfuscatedSignature(descriptor = "Ldz;")
   @ObfuscatedName("es")
   public final WorldView field5769;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("zi")
   public Deque2 field5757 = new Deque2();
   @ObfuscatedName("kf")
   public final int field5762;
   @ObfuscatedName("xv")
   public final int[] field5777;
   @ObfuscatedName("te")
   public Future field5789;
   @ObfuscatedName("bp")
   public int field5783;
   @ObfuscatedName("my")
   public short[][][] field5797;
   @ObfuscatedName("cm")
   public byte[][][] field5791;
   @ObfuscatedName("wp")
   public final int[] field5780;

   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("lu")
   public static int method10121(int var0, int var1, int var2, int var3, int var4, int var5) {
      if ((var5 & 1) == 1) {
         int var6 = var3;
         var3 = var4;
         var4 = var6;
      }

      int var7 = var2 & 3;
      if (var7 == 0) {
         return var0;
      } else if (var7 == 1) {
         return var1;
      } else {
         return var7 == 2 ? 7 - var0 - (var3 - 1) : 7 - var1 - (var4 - 1);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("tq")
   public void method10128() {
      for (int var1 = 0; var1 < 4; var1++) {
         for (int var2 = 0; var2 < this.field5759; var2++) {
            int var3 = var2 + this.field5763;

            for (int var4 = 0; var4 < this.field5760; var4++) {
               int var5 = var4 + this.field5763;
               if ((this.field5792[var1][var3][var5] & 1) == 1) {
                  int var6 = var1;
                  if ((this.field5792[1][var3][var5] & 2) == 2) {
                     var6 = var1 - 1;
                  }

                  if (var6 >= 0) {
                     this.field5790[var6].method5415(var2, var4);
                  }
               }
            }
         }
      }

      int var33 = (int)Math.sqrt(5100.0) * 768 >> 8;

      for (int var34 = 0; var34 < 4; var34++) {
         byte[][] var36 = this.field5774[var34];

         for (int var38 = 1; var38 < this.field5787.field2030 - 1; var38++) {
            for (int var42 = 1; var42 < this.field5787.field2054 - 1; var42++) {
               int var46 = this.field5795[var34][var42 + 1][var38] - this.field5795[var34][var42 - 1][var38];
               int var7 = this.field5795[var34][var42][var38 + 1] - this.field5795[var34][var42][var38 - 1];
               int var8 = (int)Math.sqrt(var7 * var7 + var46 * var46 + 65536);
               int var9 = (var46 << 8) / var8;
               int var10 = 65536 / var8;
               int var11 = (var7 << 8) / var8;
               int var12 = (var11 * -50 + var9 * -50 + var10 * -10) / var33 + 96;
               int var13 = (var36[var42][var38 + 1] >> 3)
                  + (var36[var42 - 1][var38] >> 2)
                  + (var36[var42][var38 - 1] >> 2)
                  + (var36[var42 + 1][var38] >> 3)
                  + (var36[var42][var38] >> 1);
               this.field5775[var42][var38] = var12 - var13;
            }
         }

         for (int var39 = 0; var39 < this.field5762; var39++) {
            this.field5776[var39] = 0;
            this.field5777[var39] = 0;
            this.field5778[var39] = 0;
            this.field5779[var39] = 0;
            this.field5780[var39] = 0;
         }

         for (int var40 = -5; var40 < this.field5761 + 5; var40++) {
            for (int var43 = 0; var43 < this.field5762; var43++) {
               int var47 = var40 + 5;
               if (var47 >= 0 && var47 < this.field5761) {
                  int var49 = this.field5797[var34][var47][var43] & 32767;
                  if (var49 > 0) {
                     classPH var52 = this.method10141(var49 - 1);
                     this.field5776[var43] = this.field5776[var43] + var52.method8930();
                     this.field5777[var43] = this.field5777[var43] + var52.method8931();
                     this.field5778[var43] = this.field5778[var43] + var52.method8932();
                     this.field5779[var43] = this.field5779[var43] + var52.method8933();
                     this.field5780[var43]++;
                  }
               }

               int var50 = var40 - 5;
               if (var50 >= 0 && var50 < this.field5761) {
                  int var53 = this.field5797[var34][var50][var43] & 32767;
                  if (var53 > 0) {
                     classPH var55 = this.method10141(var53 - 1);
                     this.field5776[var43] = this.field5776[var43] - var55.method8930();
                     this.field5777[var43] = this.field5777[var43] - var55.method8931();
                     this.field5778[var43] = this.field5778[var43] - var55.method8932();
                     this.field5779[var43] = this.field5779[var43] - var55.method8933();
                     this.field5780[var43]--;
                  }
               }
            }

            if (var40 >= 1 && var40 < this.field5761 - 1) {
               int var44 = 0;
               int var48 = 0;
               int var51 = 0;
               int var54 = 0;
               int var56 = 0;

               for (int var57 = -5; var57 < this.field5762 + 5; var57++) {
                  int var58 = var57 + 5;
                  if (var58 >= 0 && var58 < this.field5762) {
                     var44 += this.field5776[var58];
                     var48 += this.field5777[var58];
                     var51 += this.field5778[var58];
                     var54 += this.field5779[var58];
                     var56 += this.field5780[var58];
                  }

                  int var59 = var57 - 5;
                  if (var59 >= 0 && var59 < this.field5762) {
                     var44 -= this.field5776[var59];
                     var48 -= this.field5777[var59];
                     var51 -= this.field5778[var59];
                     var54 -= this.field5779[var59];
                     var56 -= this.field5780[var59];
                  }

                  if (var57 >= 1
                     && var57 < this.field5762 - 1
                     && (!client.field886 || (this.field5792[0][var40][var57] & 2) != 0 || (this.field5792[var34][var40][var57] & 16) == 0)) {
                     if (var34 < classDI.field1509 * -791964067) {
                        classDI.field1509 = var34 * 360312309;
                     }

                     int var60 = this.field5797[var34][var40][var57] & 32767;
                     int var14 = this.field5798[var34][var40][var57] & 32767;
                     if (var60 > 0 || var14 > 0) {
                        int var15 = this.field5795[var34][var40][var57];
                        int var16 = this.field5795[var34][var40 + 1][var57];
                        int var17 = this.field5795[var34][var40 + 1][var57 + 1];
                        int var18 = this.field5795[var34][var40][var57 + 1];
                        int var19 = this.field5775[var40][var57];
                        int var20 = this.field5775[var40 + 1][var57];
                        int var21 = this.field5775[var40 + 1][var57 + 1];
                        int var22 = this.field5775[var40][var57 + 1];
                        int var23 = -1;
                        int var24 = -1;
                        if (var60 > 0) {
                           int var25 = var44 * 256 / var54;
                           int var26 = var48 / var56;
                           int var27 = var51 / var56;
                           var23 = method10132(var25, var26, var27);
                           var24 = var23;
                        }

                        int var61 = 0;
                        if (var24 != -1) {
                           var61 = classFH.field2249[method10134(var24, 96)];
                        }

                        if (var14 == 0) {
                           this.field5787
                              .method4528(
                                 var34,
                                 var40,
                                 var57,
                                 0,
                                 0,
                                 -1,
                                 var15,
                                 var16,
                                 var17,
                                 var18,
                                 method10134(var23, var19),
                                 method10134(var23, var20),
                                 method10134(var23, var21),
                                 method10134(var23, var22),
                                 0,
                                 0,
                                 0,
                                 0,
                                 var61,
                                 0
                              );
                        } else {
                           int var62 = this.field5794[var34][var40][var57] + 1;
                           byte var63 = this.field5793[var34][var40][var57];
                           classOW var28 = this.method10125(var14 - 1);
                           int var29 = var28.method8848();
                           int var30;
                           int var31;
                           if (var29 >= 0) {
                              var31 = Ignore.field5837.getDefaultColor(var29);
                              var30 = -1;
                           } else if (var28.method8847() == 16711935) {
                              var30 = -2;
                              var29 = -1;
                              var31 = -2;
                           } else {
                              var30 = method10132(classBF.method1066(var28), var28.method8850(), var28.method8846());
                              var31 = var30;
                           }

                           int var32 = 0;
                           if (var31 != -2) {
                              var32 = classFH.field2249[method10130(var31, 96)];
                           }

                           if (var28.method8845() != -1) {
                              var31 = method10132(var28.method8852(), var28.method8851(), var28.method8849());
                              var32 = classFH.field2249[method10130(var31, 96)];
                           }

                           this.field5787
                              .method4528(
                                 var34,
                                 var40,
                                 var57,
                                 var62,
                                 var63,
                                 var29,
                                 var15,
                                 var16,
                                 var17,
                                 var18,
                                 method10134(var23, var19),
                                 method10134(var23, var20),
                                 method10134(var23, var21),
                                 method10134(var23, var22),
                                 method10130(var30, var19),
                                 method10130(var30, var20),
                                 method10130(var30, var21),
                                 method10130(var30, var22),
                                 var61,
                                 var32
                              );
                        }
                     }
                  }
               }
            }
         }

         for (int var41 = 1; var41 < this.field5762 - 1; var41++) {
            for (int var45 = 1; var45 < this.field5761 - 1; var45++) {
               this.field5787.method4547(var34, var45, var41, 0);
            }
         }
      }

      this.field5787.method4544(-50, -10, -50);

      for (int var35 = 0; var35 < this.field5761; var35++) {
         for (int var37 = 0; var37 < this.field5762; var37++) {
            if ((this.field5792[1][var35][var37] & 2) == 2) {
               this.field5787.method4546(var35, var37);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/Throwable;)V")
   @ObfuscatedName("rm")
   public static void method10144(String var0, Throwable var1) {
      classAAU.method270(var0, var1, (byte)102);
   }

   @Override
   public void run() {
      try {
         while (!this.method10135()) {
            try {
               Thread.sleep(20L);
            } catch (InterruptedException var2) {
            }
         }
      } catch (Throwable var3) {
         method10144("map loading", var3);
         throw var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ci")
   public void method10143() {
      try {
         this.field5789.get();
      } catch (Throwable var2) {
         throw var2;
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)Z")
   @ObfuscatedName("vo")
   public boolean method10142(byte[] var1, int var2, int var3) {
      classXY var4 = new classXY(var1);
      int var5 = -1;
      boolean var6 = true;

      while (true) {
         int var7 = var4.method13324();
         if (var7 == 0) {
            return var6;
         }

         var5 += var7;
         int var8 = 0;

         while (true) {
            int var9 = var4.method13320();
            if (var9 == 0) {
               break;
            }

            var8 += var9 - 1;
            int var10 = var8 & 63;
            int var11 = var8 >> 6 & 63;
            var4.method13322();
            int var12 = var11 + var2;
            int var13 = var10 + var3;
            if (this.field5770.field6882 || var12 > this.field5765 && var13 > this.field5766 && var12 < this.field5767 - 1 && var13 < this.field5768 - 1) {
               ObjectComposition var14 = ObjectComposition.method8638(var5);
               if ((client.field1021 & 16) != 0 && var14.field4928 != null) {
                  if (!var14.method8632()) {
                     var6 = false;
                  }

                  for (int var15 = 0; var15 < var14.field4928.length; var15++) {
                     ObjectComposition var16 = ObjectComposition.method8638(var14.field4928[var15]);
                     if (!var16.method8632()) {
                        var6 = false;
                     }
                  }
               } else if (!var14.method8632()) {
                  var6 = false;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("tm")
   public static int method10137(int var0, int var1) {
      int var2 = method10145(45365 + var0, 91923 + var1, 4)
         - 128
         + (method10145(var0 + 10294, 37821 + var1, 2) - 128 >> 1)
         + (method10145(var0, var1, 1) - 128 >> 2);
      var2 = (int)(var2 * 0.3) + 35;
      if (var2 < 10) {
         var2 = 10;
      } else if (var2 > 60) {
         var2 = 60;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("xp")
   public static int method10136(int var0, int var1, int var2, int var3, int var4, int var5) {
      if ((var5 & 1) == 1) {
         int var6 = var3;
         var3 = var4;
         var4 = var6;
      }

      var2 &= 3;
      if (var2 == 0) {
         return var1;
      } else if (var2 == 1) {
         return 7 - var0 - (var3 - 1);
      } else {
         return var2 == 2 ? 7 - var1 - (var4 - 1) : var0;
      }
   }

   @ObfuscatedSignature(descriptor = "()[[[B")
   @ObfuscatedName("ml")
   public byte[][][] method10118() {
      byte[][][] var1 = new byte[4][this.field5759][this.field5760];

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < this.field5759; var3++) {
            for (int var4 = 0; var4 < this.field5760; var4++) {
               var1[var2][var3][var4] = this.field5792[var2][var3 + this.field5763][var4 + this.field5763];
            }
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ym")
   public static int method10145(int var0, int var1, int var2) {
      return classTA.method10773(var0, var1, var2, 778053024);
   }

   @ObfuscatedSignature(descriptor = "([BIIIIIIIII)V")
   @ObfuscatedName("ne")
   public void method10140(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      CollisionData var11 = this.field5790[var2];

      for (int var12 = var3; var12 < var3 + 8; var12++) {
         for (int var13 = var4; var13 < var4 + 8; var13++) {
            if (var11.method5414(var12, var13)) {
               var11.method5420(var12, var13, 1073741824);
            }
         }
      }

      classXY var20 = new classXY(var1);

      for (int var21 = 0; var21 < 4; var21++) {
         for (int var14 = 0; var14 < 64; var14++) {
            for (int var15 = 0; var15 < 64; var15++) {
               if (var21 == var5 && var14 >= var6 && var14 < var6 + 8 && var15 >= var7 && var15 < var7 + 8) {
                  int var16 = var3 + method10123(var14 & 7, var15 & 7, var8);
                  int var17 = var4 + method10129(var14 & 7, var15 & 7, var8);
                  int var18 = var9 + var3 + (var14 & 7);
                  int var19 = var10 + var4 + (var15 & 7);
                  this.method10133(var20, var2, var16, var17, var18, var19, var8);
               } else {
                  this.method10133(var20, -1, -1, -1, 0, 0, 0);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ia")
   public static int method10134(int var0, int var1) {
      if (var0 == -1) {
         return 12345678;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;IIIIII)V")
   @ObfuscatedName("cb")
   public void method10133(classXY var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var2 >= 0 && var2 < 4 && var3 >= this.field5765 && var3 < this.field5767 && var4 >= this.field5766 && var4 < this.field5768) {
         int var12 = var3 + this.field5763;
         int var9 = var4 + this.field5763;
         this.field5792[var2][var12][var9] = 0;

         while (true) {
            int var10 = var1.method13312();
            if (var10 == 0) {
               if (var2 == 0) {
                  this.field5795[0][var12][var9] = -method10137(var5 + 932731, var6 + 556238) * 8;
               } else {
                  this.field5795[var2][var12][var9] = this.field5795[var2 - 1][var12][var9] - 240;
               }
               break;
            }

            if (var10 == 1) {
               int var11 = var1.method13322();
               if (var11 == 1) {
                  var11 = 0;
               }

               if (var2 == 0) {
                  this.field5795[0][var12][var9] = -var11 * 8;
               } else {
                  this.field5795[var2][var12][var9] = this.field5795[var2 - 1][var12][var9] - var11 * 8;
               }
               break;
            }

            if (var10 <= 49) {
               this.field5798[var2][var12][var9] = (short)var1.method13327();
               this.field5794[var2][var12][var9] = (byte)((var10 - 2) / 4);
               this.field5793[var2][var12][var9] = (byte)(var10 - 2 + var7 & 3);
            } else if (var10 <= 81) {
               this.field5792[var2][var12][var9] = (byte)(var10 - 49);
            } else {
               this.field5797[var2][var12][var9] = (short)(var10 - 81);
            }
         }
      } else {
         while (true) {
            int var8 = var1.method13312();
            if (var8 == 0) {
               break;
            }

            if (var8 == 1) {
               var1.method13322();
               break;
            }

            if (var8 <= 49) {
               var1.method13327();
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("dw")
   public boolean method10135() {
      classVP var1 = client.field1030[5];
      int var2 = 0;

      for (int var3 = 0; var3 < this.field5770.field6885.length; var3++) {
         int var4 = this.field5770.field6885[var3];
         if (var4 != -1) {
            if (this.field5771[var3] == null) {
               boolean var5 = var1.getFileIds(var4) != null;
               this.field5771[var3] = var1.loadData(var4, 0);
               if (var5 && this.field5771[var3] == null) {
                  var2++;
               }
            }

            if (this.field5772[var3] == null) {
               boolean var34 = var1.getFileIds(var4) != null;
               this.field5772[var3] = var1.loadData(var4, 1);
               if (var34 && this.field5772[var3] == null) {
                  var2++;
               }
            }
         }
      }

      if (var2 > 0) {
         return false;
      } else {
         boolean var27 = true;

         for (int var28 = 0; var28 < this.field5771.length; var28++) {
            byte[] var35 = this.field5772[var28];
            if (var35 != null) {
               int var6 = (this.field5770.field6885[var28] >> 8) * 64 - this.field5783;
               int var7 = (this.field5770.field6885[var28] & 0xFF) * 64 - this.field5786;
               var27 &= this.method10142(var35, var6, var7);
            }
         }

         if (!var27) {
            return false;
         } else {
            this.field5795 = new int[4][this.field5761 + 1][this.field5762 + 1];
            this.field5787 = new Scene(
               this.field5769 == classIS.field3051,
               4,
               this.field5761,
               this.field5762,
               classAB.field122.field1348 * -412733521,
               classEX.field1975,
               this.field5795
            );
            this.field5787.field2106 = this.field5769.field1697 * 2140889407;
            this.field5787.field2158 = new rl10[4][this.field5759][this.field5760];
            this.field5787.field2146 = this.field5763;
            this.field5787.field2110 = this.field5765;
            this.field5787.field2142 = this.field5767;
            this.field5787.field2145 = this.field5766;
            this.field5787.field2139 = this.field5768;
            this.field5787.field2152 = false;
            this.field5790 = new CollisionData[4];

            for (int var29 = 0; var29 < 4; var29++) {
               this.field5790[var29] = new CollisionData(this.field5759, this.field5760, !this.field5769.isTopLevel());
            }

            this.field5792 = new byte[4][this.field5761][this.field5762];
            classDI.field1509 = 99 * 360312309;
            this.field5797 = new short[4][this.field5761][this.field5762];
            this.field5798 = new short[4][this.field5761][this.field5762];
            this.field5794 = new byte[4][this.field5761][this.field5762];
            this.field5793 = new byte[4][this.field5761][this.field5762];
            this.field5787.field2160 = this.field5797;
            this.field5787.field2159 = this.field5798;
            this.field5787.field2156 = this.field5794;
            this.field5787.field2155 = this.field5792;
            this.field5787.field2144 = this.field5783;
            this.field5787.field2109 = this.field5786;
            this.field5787.field2153 = this.field5770.field6882;
            this.field5787.field2157 = this.field5770.field6883;
            this.field5787.field2154 = this.field5770.field6885;
            if (!this.field5770.field6882) {
               byte var8 = 6;
               int var9 = 6 + this.field5764;
               int var10 = this.field5754 - var9 >> 3;
               int var11 = this.field5754 + var9 >> 3;
               int var12 = this.field5784 - var9 >> 3;
               int var13 = this.field5784 + var9 >> 3;
               int var14 = this.field5754 - var8 >> 3;
               int var15 = this.field5754 + var8 >> 3;
               int var16 = this.field5784 - var8 >> 3;
               int var17 = this.field5784 + var8 >> 3;
               int var18 = 0;

               for (int var19 = var10; var19 <= var11; var19++) {
                  for (int var20 = var12; var20 <= var13; var20++) {
                     if (var19 < var14 || var19 > var15 || var20 < var16 || var20 > var17) {
                        var18++;
                     }
                  }
               }

               int[] var30 = new int[var18];
               byte[][] var36 = new byte[var18][];
               byte[][] var39 = new byte[var18][];
               boolean[] var44 = new boolean[var18];
               int var98 = var10;

               for (int var99 = 0; var98 <= var11; var98++) {
                  for (int var21 = var12; var21 <= var13; var21++) {
                     if (var98 < var14 || var98 > var15 || var21 < var16 || var21 > var17) {
                        int var22 = var21 | var98 << 8;
                        var30[var99] = var22;
                        var36[var99] = var1.loadData(var22, 0);
                        var39[var99] = var1.loadData(var22, 1);
                        int var23 = var98 * 64 - this.field5783;
                        int var24 = var21 * 64 - this.field5786;
                        var44[var99] = var36[var99] != null && var39[var99] != null && this.method10142(var39[var99], var23, var24);
                        var99++;
                     }
                  }
               }

               for (int var49 = 0; var49 < this.field5771.length; var49++) {
                  byte[] var59 = this.field5771[var49];
                  if (var59 != null) {
                     var10 = this.field5770.field6885[var49];
                     var11 = (var10 >> 8 << 6) - this.field5783;
                     var12 = ((var10 & 0xFF) << 6) - this.field5786;

                     assert this.field5754 * 8 - 48 == this.field5783;

                     assert this.field5784 * 8 - 48 == this.field5786;

                     this.method10122(var59, var11, var12, this.field5783, this.field5786, this.field5790);
                  }
               }

               for (int var50 = 0; var50 < var30.length; var50++) {
                  if (var44[var50]) {
                     var9 = var30[var50];
                     var10 = (var9 >> 8 << 6) - this.field5783;
                     var11 = ((var9 & 0xFF) << 6) - this.field5786;
                     this.method10122(var36[var50], var10, var11, this.field5783, this.field5786, this.field5790);
                  }
               }

               for (int var51 = 0; var51 < this.field5771.length; var51++) {
                  byte[] var61 = this.field5771[var51];
                  if (var61 == null && this.field5784 < 800) {
                     var10 = this.field5770.field6885[var51];
                     var11 = (var10 >> 8 << 6) - this.field5783;
                     var12 = ((var10 & 0xFF) << 6) - this.field5786;
                     this.method10138(var11, var12, 64, 64);
                  }
               }

               for (int var52 = 0; var52 < var30.length; var52++) {
                  if (!var44[var52] && this.field5784 < 800) {
                     var9 = var30[var52];
                     var10 = (var9 >> 8 << 6) - this.field5783;
                     var11 = ((var9 & 0xFF) << 6) - this.field5786;
                     this.method10138(var10, var11, 64, 64);
                  }
               }

               client.method2292();

               for (int var53 = 0; var53 < this.field5771.length; var53++) {
                  byte[] var63 = this.field5772[var53];
                  if (var63 != null) {
                     var10 = this.field5770.field6885[var53];
                     var11 = (var10 >> 8 << 6) - this.field5783;
                     var12 = ((var10 & 0xFF) << 6) - this.field5786;
                     this.method10124(this.field5769, var63, var11, var12, this.field5790);
                  }
               }

               for (int var54 = 0; var54 < var30.length; var54++) {
                  if (var44[var54]) {
                     var9 = var30[var54];
                     var10 = (var9 >> 8 << 6) - this.field5783;
                     var11 = ((var9 & 0xFF) << 6) - this.field5786;
                     this.method10124(this.field5769, var39[var54], var10, var11, this.field5790);
                  }
               }

               client.method2336();
               client.method2292();
            } else {
               int var31 = this.field5759 / 8;
               int var37 = this.field5760 / 8;

               for (int var40 = 0; var40 < 4; var40++) {
                  for (int var45 = 0; var45 < var31; var45++) {
                     for (int var55 = 0; var55 < var37; var55++) {
                        boolean var65 = false;
                        int var74 = this.field5770.field6883[var40][var45][var55];
                        if (var74 != -1) {
                           int var82 = var74 >> 24 & 3;
                           int var87 = var74 >> 1 & 3;
                           int var89 = var74 >> 14 & 1023;
                           int var91 = var74 >> 3 & 2047;
                           int var93 = (var89 / 8 << 8) + var91 / 8;

                           for (int var95 = 0; var95 < this.field5770.field6885.length; var95++) {
                              if (this.field5770.field6885[var95] == var93 && this.field5771[var95] != null) {
                                 int var96 = (var89 - var45) * 8;
                                 int var97 = (var91 - var55) * 8;
                                 this.method10140(
                                    this.field5771[var95], var40, var45 * 8, var55 * 8, var82, (var89 & 7) * 8, (var91 & 7) * 8, var87, var96, var97
                                 );
                                 var65 = true;
                                 break;
                              }
                           }
                        }

                        if (!var65) {
                           this.method10139(var40, var45 * 8, var55 * 8);
                        }
                     }
                  }
               }

               for (int var41 = 0; var41 < 13; var41++) {
                  for (int var46 = 0; var46 < 13; var46++) {
                     int var56 = this.field5770.field6883[0][var41][var46];
                     if (var56 == -1) {
                        this.method10138(var41 * 8, var46 * 8, 8, 8);
                     }
                  }
               }

               for (int var42 = 0; var42 < 4; var42++) {
                  for (int var47 = 0; var47 < var31; var47++) {
                     for (int var57 = 0; var57 < var37; var57++) {
                        int var66 = this.field5770.field6883[var42][var47][var57];
                        if (var66 != -1) {
                           int var75 = var66 >> 24 & 3;
                           int var83 = var66 >> 1 & 3;
                           int var88 = var66 >> 14 & 1023;
                           int var90 = var66 >> 3 & 2047;
                           int var92 = (var88 / 8 << 8) + var90 / 8;

                           for (int var94 = 0; var94 < this.field5770.field6885.length; var94++) {
                              if (this.field5770.field6885[var94] == var92 && this.field5772[var94] != null) {
                                 this.method10131(
                                    this.field5769,
                                    this.field5772[var94],
                                    var42,
                                    var47 * 8,
                                    var57 * 8,
                                    var75,
                                    (var88 & 7) * 8,
                                    (var90 & 7) * 8,
                                    var83,
                                    this.field5790
                                 );
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }

            this.method10128();
            synchronized (ObjectComposition.field4966) {
               ObjectComposition.field4966.reset();
            }

            ObjectComposition.field4970.reset();
            if (this.field5769.isTopLevel()) {
               if (client.field886) {
                  this.field5787.method4520(classDI.field1509 * -791964067);
               } else {
                  this.field5787.method4520(0);
               }

               if (!this.field5770.field6882) {
                  int var33 = (this.field5754 - 6) / 8;
                  int var38 = (this.field5754 + 6) / 8;
                  int var43 = (this.field5784 - 6) / 8;
                  int var48 = (this.field5784 + 6) / 8;

                  for (int var58 = var33 - 1; var58 <= var38 + 1; var58++) {
                     for (int var67 = var43 - 1; var67 <= var48 + 1; var67++) {
                        if (var58 < var33 || var58 > var38 || var67 < var43 || var67 > var48) {
                           var1.method12122((var58 << 8) + var67);
                        }
                     }
                  }
               }
            }

            this.field5787.method4425();
            this.field5796 = this.method10120();
            this.field5791 = this.method10118();
            if (!this.field5769.isTopLevel()) {
               if (this.field5758 != null) {
                  this.field5758.loadScene(this.field5769, this.field5787);
               }

               return true;
            } else {
               client.method2299(this.field5787);
               classOE.field4841.getCallbacks().post(new PreMapLoad(this.field5769, this.field5787));
               if (this.field5758 != null) {
                  this.field5758.loadScene(this.field5787);
                  this.field5758.loadScene(this.field5769, this.field5787);
               }

               return true;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("fa")
   public boolean method10127() {
      return this.field5789.isDone();
   }

   @ObfuscatedSignature(descriptor = "([BIIII[Lgc;)V")
   @ObfuscatedName("wu")
   public void method10122(byte[] var1, int var2, int var3, int var4, int var5, CollisionData[] var6) {
      assert var6 != null;

      for (int var7 = 0; var7 < 4; var7++) {
         for (int var8 = var2; var8 < var2 + 64; var8++) {
            for (int var9 = var3; var9 < var3 + 64; var9++) {
               if (var6[var7].method5414(var8, var9)) {
                  var6[var7].method5420(var8, var9, 1073741824);
               }
            }
         }
      }

      classXY var13 = new classXY(var1);

      for (int var14 = 0; var14 < 4; var14++) {
         for (int var15 = 0; var15 < 64; var15++) {
            for (int var10 = 0; var10 < 64; var10++) {
               int var11 = var15 + var2;
               int var12 = var10 + var3;
               this.method10133(var13, var14, var11, var12, var4 + var11, var12 + var5, 0);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ki")
   public void method10138(int var1, int var2, int var3, int var4) {
      for (int var5 = var2; var5 <= var4 + var2; var5++) {
         for (int var6 = var1; var6 <= var1 + var3; var6++) {
            if (var6 >= this.field5765 && var6 < this.field5767 && var5 >= this.field5766 && var5 < this.field5768) {
               int var7 = var6 + this.field5763;
               int var8 = var5 + this.field5763;
               this.field5774[0][var7][var8] = 127;
               if (var1 == var6 && var6 > 0) {
                  this.field5795[0][var7][var8] = this.field5795[0][var7 - 1][var8];
               }

               if (var1 + var3 == var6 && var6 < this.field5761 - 1) {
                  this.field5795[0][var7][var8] = this.field5795[0][var7 + 1][var8];
               }

               if (var5 == var2 && var5 > 0) {
                  this.field5795[0][var7][var8] = this.field5795[0][var7][var8 - 1];
               }

               if (var5 == var4 + var2 && var5 < this.field5762 - 1) {
                  this.field5795[0][var7][var8] = this.field5795[0][var7][var8 + 1];
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lph;")
   @ObfuscatedName("yn")
   public classPH method10141(int var1) {
      classPH var2 = (classPH)this.field5755.method6479(var1);
      if (var2 != null) {
         return var2;
      } else {
         classVP var3 = client.field1030[2];
         byte[] var4 = var3.loadData(1, var1);
         var2 = new classPH(new classXY(var4));
         classZP.method14133(this.field5755, var2, var1);
         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("dm")
   public static int method10130(int var0, int var1) {
      if (var0 == -2) {
         return 12345678;
      } else if (var0 == -1) {
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return var1;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 65408) + var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("si")
   public static int method10129(int var0, int var1, int var2) {
      var2 &= 3;
      if (var2 == 0) {
         return var1;
      } else if (var2 == 1) {
         return 7 - var0;
      } else {
         return var2 == 2 ? 7 - var1 : var0;
      }
   }

   @ObfuscatedSignature(descriptor = "()[[[I")
   @ObfuscatedName("ey")
   public int[][][] method10120() {
      int[][][] var1 = new int[4][this.field5759 + 1][this.field5760 + 1];

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < this.field5759 + 1; var3++) {
            for (int var4 = 0; var4 < this.field5760 + 1; var4++) {
               var1[var2][var3][var4] = this.field5795[var2][var3 + this.field5763][var4 + this.field5763];
            }
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIIILgc;)V")
   @ObfuscatedName("is")
   public static void method10146(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6, CollisionData var7) {
      classCI.method1514(var0, var1, var2, var3, var4, var5, var6, var7, 1070436096);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("et")
   public static int method10123(int var0, int var1, int var2) {
      var2 &= 3;
      if (var2 == 0) {
         return var0;
      } else if (var2 == 1) {
         return var1;
      } else {
         return var2 == 2 ? 7 - var0 : 7 - var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ac")
   public static int method10132(int var0, int var1, int var2) {
      if (var2 > 179) {
         var1 /= 2;
      }

      if (var2 > 192) {
         var1 /= 2;
      }

      if (var2 > 217) {
         var1 /= 2;
      }

      if (var2 > 243) {
         var1 /= 2;
      }

      return (var1 / 32 << 7) + (var0 / 4 << 10) + var2 / 2;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ey")
   public void method10139(int var1, int var2, int var3) {
      int var4 = var2 + this.field5763;
      int var5 = var3 + this.field5763;

      for (int var6 = 0; var6 < 8; var6++) {
         for (int var7 = 0; var7 < 8; var7++) {
            this.field5795[var1][var6 + var4][var7 + var5] = 0;
         }
      }

      if (var4 > 0) {
         for (int var8 = 1; var8 < 8; var8++) {
            this.field5795[var1][var4][var8 + var5] = this.field5795[var1][var4 - 1][var8 + var5];
         }
      }

      if (var5 > 0) {
         for (int var9 = 1; var9 < 8; var9++) {
            this.field5795[var1][var9 + var4][var5] = this.field5795[var1][var9 + var4][var5 - 1];
         }
      }

      if (var4 > 0 && this.field5795[var1][var4 - 1][var5] != 0) {
         this.field5795[var1][var4][var5] = this.field5795[var1][var4 - 1][var5];
      } else if (var5 > 0 && this.field5795[var1][var4][var5 - 1] != 0) {
         this.field5795[var1][var4][var5] = this.field5795[var1][var4][var5 - 1];
      } else if (var4 > 0 && var5 > 0 && this.field5795[var1][var4 - 1][var5 - 1] != 0) {
         this.field5795[var1][var4][var5] = this.field5795[var1][var4 - 1][var5 - 1];
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;[BIIIIIII[Lgc;)V")
   @ObfuscatedName("wk")
   public void method10131(WorldView var1, byte[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, CollisionData[] var10) {
      classXY var11 = new classXY(var2);
      int var12 = -1;

      while (true) {
         int var13 = var11.method13324();
         if (var13 == 0) {
            return;
         }

         var12 += var13;
         int var14 = 0;

         while (true) {
            int var15 = var11.method13320();
            if (var15 == 0) {
               break;
            }

            var14 += var15 - 1;
            int var16 = var14 & 63;
            int var17 = var14 >> 6 & 63;
            int var18 = var14 >> 12;
            int var19 = var11.method13322();
            int var20 = var19 >> 2;
            int var21 = var19 & 3;
            if (var18 == var6 && var17 >= var7 && var17 < var7 + 8 && var16 >= var8 && var16 < var8 + 8) {
               ObjectComposition var22 = ObjectComposition.method8638(var12);
               int var23 = var4 + method10121(var17 & 7, var16 & 7, var9, var22.field4942 * -582306509, var22.field4922 * 402876423, var21);
               int var24 = var5 + method10136(var17 & 7, var16 & 7, var9, var22.field4942 * -582306509, var22.field4922 * 402876423, var21);
               if (var23 > this.field5765 && var24 > this.field5766 && var23 < this.field5767 - 1 && var24 < this.field5768 - 1) {
                  int var25 = var3;
                  if ((this.field5792[1][var23 + this.field5763][var24 + this.field5763] & 2) == 2) {
                     var25 = var3 - 1;
                  }

                  CollisionData var26 = null;
                  if (var25 >= 0) {
                     var26 = var10[var25];
                  }

                  method10146(var1, var3, var23, var24, var12, var21 + var9 & 3, var20, var26);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;[BII[Lgc;)V")
   @ObfuscatedName("ax")
   public void method10124(WorldView var1, byte[] var2, int var3, int var4, CollisionData[] var5) {
      classXY var6 = new classXY(var2);
      int var7 = -1;

      while (true) {
         int var8 = var6.method13324();
         if (var8 == 0) {
            return;
         }

         var7 += var8;
         int var9 = 0;

         while (true) {
            int var10 = var6.method13320();
            if (var10 == 0) {
               break;
            }

            var9 += var10 - 1;
            int var11 = var9 & 63;
            int var12 = var9 >> 6 & 63;
            int var13 = var9 >> 12;
            int var14 = var6.method13322();
            int var15 = var14 >> 2;
            int var16 = var14 & 3;
            int var17 = var12 + var3;
            int var18 = var11 + var4;
            if (var17 > this.field5765 && var18 > this.field5766 && var17 < this.field5767 - 1 && var18 < this.field5768 - 1) {
               int var19 = var13;
               if ((this.field5792[1][var17 + this.field5763][var18 + this.field5763] & 2) == 2) {
                  var19 = var13 - 1;
               }

               CollisionData var20 = null;
               if (var19 >= 0) {
                  var20 = var5[var19];
               }

               method10146(var1, var13, var17, var18, var7, var16, var15, var20);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Low;")
   @ObfuscatedName("lm")
   public classOW method10125(int var1) {
      classOW var2 = (classOW)this.field5756.method6479(var1);
      if (var2 != null) {
         return var2;
      } else {
         classVP var3 = client.field1030[2];
         byte[] var4 = var3.loadData(4, var1);
         return new classOW(new classXY(var4), var1);
      }
   }

   public rl4(DrawCallbacks var1, int var2, WorldView var3, classXK var4) {
      assert var2 == 0 || var3.isTopLevel();

      this.field5758 = var1;
      this.field5759 = var3.field1694 * 1296729483;
      this.field5760 = var3.field1690 * -1269171107;
      int var5 = var3.isTopLevel() ? 40 : 0;
      this.field5761 = var3.field1694 * 1296729483 + var5 * 2;
      this.field5762 = var3.field1690 * -1269171107 + var5 * 2;
      this.field5763 = var5;
      this.field5764 = var2;
      this.field5765 = -var2 * 8;
      this.field5766 = -var2 * 8;
      this.field5767 = var3.field1694 * 1296729483 + var2 * 8;
      this.field5768 = var3.field1690 * -1269171107 + var2 * 8;
      this.field5769 = var3;
      this.field5770 = var4;
      this.field5771 = new byte[var4.field6885.length][];
      this.field5772 = new byte[var4.field6885.length][];
      this.field5773 = new int[4][this.field5761 + 1][this.field5762 + 1];
      this.field5774 = new byte[4][this.field5761 + 1][this.field5762 + 1];
      this.field5775 = new int[this.field5761 + 1][this.field5762 + 1];
      this.field5776 = new int[this.field5762];
      this.field5777 = new int[this.field5762];
      this.field5778 = new int[this.field5762];
      this.field5779 = new int[this.field5762];
      this.field5780 = new int[this.field5762];
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("yv")
   public void method10126() {
      assert this.field5789 == null;

      this.field5789 = field5782.submit(this);
   }
}
