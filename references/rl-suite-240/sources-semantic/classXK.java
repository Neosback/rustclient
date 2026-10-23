import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xk")
public class classXK {
   @ObfuscatedName("ae")
   final int[][][] field6883;
   @ObfuscatedName("af")
   int[] field6885;
   @ObfuscatedName("az")
   boolean field6882 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   static final int field6884 = 38;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxk;Lxs;III)V")
   @ObfuscatedName("ph")
   public static void method12894(classXK var0, PacketBuffer var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method12882(var2, var2, var2);
      }

      var0.field6882 = true;
      int var5 = var1.method13099(-85887301);
      var1.method12981((byte)51);

      for (int var6 = 0; var6 < var2; var6++) {
         for (int var7 = 0; var7 < var3; var7++) {
            for (int var8 = 0; var8 < var4; var8++) {
               int var9 = var1.method12982(1, -935446910);
               if (1 == var9) {
                  var0.field6883[var6][var7][var8] = var1.method12982(26, -508447026);
               } else {
                  var0.field6883[var6][var7][var8] = -1;
               }
            }
         }
      }

      var1.method12987(-2101878870);
      var0.field6885 = new int[var5];
      Arrays.fill(var0.field6885, -1);
      var5 = 0;

      for (int var17 = 0; var17 < var2; var17++) {
         for (int var18 = 0; var18 < var3; var18++) {
            for (int var19 = 0; var19 < var4; var19++) {
               int var20 = var0.field6883[var17][var18][var19];
               if (-1 != var20) {
                  int var10 = classHA.method5987(var20, -2088305504);
                  int var11 = classKT.method6601(var20, -2082202631);
                  int var12 = classKY.method6646(var10, (byte)90);
                  int var13 = classKY.method6646(var11, (byte)-30);
                  int var14 = classMH.method7613(var12, var13, (byte)122);

                  for (int var15 = 0; var15 < var5; var15++) {
                     if (var0.field6885[var15] == var14) {
                        var14 = -1;
                        break;
                     }
                  }

                  if (var14 != -1) {
                     var0.field6885[var5] = var14;
                     var5++;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("br")
   public int method12882(int var1, int var2, int var3) {
      return this.field6883[var1][var2][var3];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aj")
   public static int method12863(int var0, int var1) {
      return (var0 << 8) + var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public int method12876(int var1) {
      try {
         return this.field6885.length;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xk.ag(" + 41);
      }
   }

   public classXK() {
      this.field6883 = new int[4][13][13];
   }

   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ax")
   public int method12883(int var1, int var2, int var3, int var4) {
      try {
         return this.field6883[var1][var2][var3];
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xk.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ac")
   public void method12885(int var1, int var2, int var3) {
      try {
         this.field6882 = false;
         int var4 = classKY.method6646(var1 - 6, (byte)9);
         int var5 = classKY.method6646(6 + var1, (byte)-93);
         int var6 = classKY.method6646(var2 - 6, (byte)-19);
         int var7 = classKY.method6646(var2 + 6, (byte)-5);
         int var8 = (var5 - var4 + 1) * (var7 - var6 + 1);
         this.field6885 = new int[var8];
         int var9 = 0;

         for (int var10 = var4; var10 <= var5; var10++) {
            if (var3 == 762685179) {
               return;
            }

            for (int var11 = var6; var11 <= var7; var11++) {
               this.field6885[var9] = classMH.method7613(var10, var11, (byte)17);
               var9++;
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "xk.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;I)V")
   @ObfuscatedName("aa")
   public void method12891(PacketBuffer var1, int var2) {
      try {
         this.method12895(var1, 4, 13, 13, -1906825040);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xk.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;IIB)V")
   @ObfuscatedName("ao")
   public void method12893(PacketBuffer var1, int var2, int var3, byte var4) {
      try {
         this.method12895(var1, 4, var2, var3, 635592237);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "xk.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;IIII)V")
   @ObfuscatedName("al")
   void method12895(PacketBuffer var1, int var2, int var3, int var4, int var5) {
      try {
         this.field6882 = true;
         int var6 = var1.method13099(-1737903474);
         var1.method12981((byte)120);

         for (int var7 = 0; var7 < var2; var7++) {
            if (var5 == -1926460509) {
               throw new IllegalStateException();
            }

            for (int var8 = 0; var8 < var3; var8++) {
               if (var5 == -1926460509) {
                  throw new IllegalStateException();
               }

               for (int var9 = 0; var9 < var4; var9++) {
                  if (var5 == -1926460509) {
                     throw new IllegalStateException();
                  }

                  int var10 = var1.method12982(1, -607133939);
                  if (1 == var10) {
                     if (var5 == -1926460509) {
                        throw new IllegalStateException();
                     }

                     this.field6883[var7][var8][var9] = var1.method12982(26, 2052400899);
                  } else {
                     this.field6883[var7][var8][var9] = -1;
                  }
               }
            }
         }

         var1.method12987(-2145664019);
         this.field6885 = new int[var6];
         Arrays.fill(this.field6885, -1);
         var6 = 0;

         for (int var19 = 0; var19 < var2; var19++) {
            if (var5 == -1926460509) {
               return;
            }

            for (int var20 = 0; var20 < var3; var20++) {
               if (var5 == -1926460509) {
                  return;
               }

               for (int var21 = 0; var21 < var4; var21++) {
                  if (var5 == -1926460509) {
                     throw new IllegalStateException();
                  }

                  int var22 = this.field6883[var19][var20][var21];
                  if (-1 != var22) {
                     int var11 = classHA.method5987(var22, -2088305504);
                     int var12 = classKT.method6601(var22, -2097626076);
                     int var13 = classKY.method6646(var11, (byte)54);
                     int var14 = classKY.method6646(var12, (byte)0);
                     int var15 = classMH.method7613(var13, var14, (byte)54);

                     for (int var16 = 0; var16 < var6; var16++) {
                        if (var5 == -1926460509) {
                           throw new IllegalStateException();
                        }

                        if (this.field6885[var16] == var15) {
                           if (var5 == -1926460509) {
                              return;
                           }

                           var15 = -1;
                           break;
                        }
                     }

                     if (var15 != -1) {
                        if (var5 == -1926460509) {
                           return;
                        }

                        this.field6885[var6] = var15;
                        var6++;
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var17) {
         throw classEG.method3899(var17, "xk.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ay")
   public static int method12864(int var0, int var1) {
      return (var0 << 8) + var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aq")
   public static int method12865(int var0) {
      return var0 >> 8;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ak")
   public static int method12869(int var0) {
      return var0 & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   public static int method12866(int var0) {
      return var0 >> 8;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("au")
   public static int method12867(int var0) {
      return var0 >> 8;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   public static int method12870(int var0) {
      return var0 & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   public static int method12871(int var0) {
      return var0 & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bx")
   public int method12880(int var1) {
      return this.field6885[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   public static int method12868(int var0) {
      return var0 >> 8;
   }

   @ObfuscatedSignature(descriptor = "(Lxs;)V")
   @ObfuscatedName("bk")
   public void method12892(PacketBuffer var1) {
      this.method12895(var1, 4, 13, 13, 829052598);
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("as")
   public int method12881(int var1, byte var2) {
      try {
         return this.field6885[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "xk.as(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)Z")
   @ObfuscatedName("fw")
   public static boolean method12899(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method4400(var1, var1, var1);
      } else {
         int var4 = var0.field2029[var1][var2][var3];
         if (var4 == -var0.field2063) {
            return false;
         } else if (var4 == var0.field2063) {
            return true;
         } else {
            int var5 = var2 << 7;
            int var6 = var3 << 7;
            if (var0.method4411(var5 + 1, var0.field2058[var1][var2][var3], var6 + 1)
               && var0.method4411(var5 + 128 - 1, var0.field2058[var1][var2 + 1][var3], var6 + 1)
               && var0.method4411(var5 + 128 - 1, var0.field2058[var1][var2 + 1][var3 + 1], var6 + 128 - 1)
               && var0.method4411(var5 + 1, var0.field2058[var1][var2][var3 + 1], var6 + 128 - 1)) {
               var0.field2029[var1][var2][var3] = var0.field2063;
               return true;
            } else {
               var0.field2029[var1][var2][var3] = -var0.field2063;
               return false;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("an")
   public boolean method12873() {
      return this.field6882;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method12877() {
      return this.field6885.length;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxk;Lxs;III)V")
   @ObfuscatedName("sw")
   public static void method12896(classXK var0, PacketBuffer var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method12897(var1, var2, var2, var2);
      } else {
         var0.field6882 = true;
         int var5 = var1.method13099(-80072341);
         var1.method12981((byte)4);

         for (int var6 = 0; var6 < var2; var6++) {
            for (int var7 = 0; var7 < var3; var7++) {
               for (int var8 = 0; var8 < var4; var8++) {
                  int var9 = var1.method12982(1, -910046796);
                  if (1 == var9) {
                     var0.field6883[var6][var7][var8] = var1.method12982(26, 654595080);
                  } else {
                     var0.field6883[var6][var7][var8] = -1;
                  }
               }
            }
         }

         var1.method12987(-2037731173);
         var0.field6885 = new int[var5];
         Arrays.fill(var0.field6885, -1);
         var5 = 0;

         for (int var17 = 0; var17 < var2; var17++) {
            for (int var18 = 0; var18 < var3; var18++) {
               for (int var19 = 0; var19 < var4; var19++) {
                  int var20 = var0.field6883[var17][var18][var19];
                  if (-1 != var20) {
                     int var10 = classHA.method5987(var20, -2088305504);
                     int var11 = classKT.method6601(var20, -2130649328);
                     int var12 = classKY.method6646(var10, (byte)104);
                     int var13 = classKY.method6646(var11, (byte)-62);
                     int var14 = classMH.method7613(var12, var13, (byte)21);

                     for (int var15 = 0; var15 < var5; var15++) {
                        if (var0.field6885[var15] == var14) {
                           var14 = -1;
                           break;
                        }
                     }

                     if (var14 != -1) {
                        var0.field6885[var5] = var14;
                        var5++;
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   public int method12878() {
      return this.field6885.length;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   public int method12879() {
      return this.field6885.length;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   public boolean method12874(int var1) {
      try {
         return this.field6882;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xk.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxk;II)V")
   @ObfuscatedName("ew")
   public static void method12886(classXK var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12883(var1, var1, var1, var1);
      }

      var0.field6882 = false;
      int var3 = classKY.method6646(var1 - 6, (byte)-46);
      int var4 = classKY.method6646(6 + var1, (byte)-87);
      int var5 = classKY.method6646(var2 - 6, (byte)-36);
      int var6 = classKY.method6646(var2 + 6, (byte)-34);
      int var7 = (var4 - var3 + 1) * (var6 - var5 + 1);
      var0.field6885 = new int[var7];
      int var8 = 0;

      for (int var9 = var3; var9 <= var4; var9++) {
         for (int var10 = var5; var10 <= var6; var10++) {
            var0.field6885[var8] = classMH.method7613(var9, var10, (byte)109);
            var8++;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bp")
   public int method12884(int var1, int var2, int var3) {
      return this.field6883[var1][var2][var3];
   }

   @ObfuscatedSignature(descriptor = "(Lxs;III)V")
   @ObfuscatedName("bw")
   void method12897(PacketBuffer var1, int var2, int var3, int var4) {
      this.field6882 = true;
      int var5 = var1.method13099(-1772956017);
      var1.method12981((byte)88);

      for (int var6 = 0; var6 < var2; var6++) {
         for (int var7 = 0; var7 < var3; var7++) {
            for (int var8 = 0; var8 < var4; var8++) {
               int var9 = var1.method12982(1, 1958226558);
               if (1 == var9) {
                  this.field6883[var6][var7][var8] = var1.method12982(26, 1859449697);
               } else {
                  this.field6883[var6][var7][var8] = -1;
               }
            }
         }
      }

      var1.method12987(-2124279271);
      this.field6885 = new int[var5];
      Arrays.fill(this.field6885, -1);
      var5 = 0;

      for (int var17 = 0; var17 < var2; var17++) {
         for (int var18 = 0; var18 < var3; var18++) {
            for (int var19 = 0; var19 < var4; var19++) {
               int var20 = this.field6883[var17][var18][var19];
               if (-1 != var20) {
                  int var10 = classHA.method5987(var20, -2088305504);
                  int var11 = classKT.method6601(var20, -2067324039);
                  int var12 = classKY.method6646(var10, (byte)65);
                  int var13 = classKY.method6646(var11, (byte)-64);
                  int var14 = classMH.method7613(var12, var13, (byte)59);

                  for (int var15 = 0; var15 < var5; var15++) {
                     if (this.field6885[var15] == var14) {
                        var14 = -1;
                        break;
                     }
                  }

                  if (var14 != -1) {
                     this.field6885[var5] = var14;
                     var5++;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bm")
   public void method12887(int var1, int var2) {
      this.field6882 = false;
      int var3 = classKY.method6646(var1 - 6, (byte)-30);
      int var4 = classKY.method6646(6 + var1, (byte)83);
      int var5 = classKY.method6646(var2 - 6, (byte)10);
      int var6 = classKY.method6646(var2 + 6, (byte)-48);
      int var7 = (var4 - var3 + 1) * (var6 - var5 + 1);
      this.field6885 = new int[var7];
      int var8 = 0;

      for (int var9 = var3; var9 <= var4; var9++) {
         for (int var10 = var5; var10 <= var6; var10++) {
            this.field6885[var8] = classMH.method7613(var9, var10, (byte)7);
            var8++;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bt")
   public void method12888(int var1, int var2) {
      this.field6882 = false;
      int var3 = classKY.method6646(var1 - 6, (byte)-4);
      int var4 = classKY.method6646(6 + var1, (byte)59);
      int var5 = classKY.method6646(var2 - 6, (byte)35);
      int var6 = classKY.method6646(var2 + 6, (byte)3);
      int var7 = (var4 - var3 + 1) * (var6 - var5 + 1);
      this.field6885 = new int[var7];
      int var8 = 0;

      for (int var9 = var3; var9 <= var4; var9++) {
         for (int var10 = var5; var10 <= var6; var10++) {
            this.field6885[var8] = classMH.method7613(var9, var10, (byte)29);
            var8++;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bj")
   public void method12889(int var1, int var2) {
      this.field6882 = false;
      int var3 = classKY.method6646(var1 - 6, (byte)-52);
      int var4 = classKY.method6646(6 + var1, (byte)-21);
      int var5 = classKY.method6646(var2 - 6, (byte)27);
      int var6 = classKY.method6646(var2 + 6, (byte)-87);
      int var7 = (var4 - var3 + 1) * (var6 - var5 + 1);
      this.field6885 = new int[var7];
      int var8 = 0;

      for (int var9 = var3; var9 <= var4; var9++) {
         for (int var10 = var5; var10 <= var6; var10++) {
            this.field6885[var8] = classMH.method7613(var9, var10, (byte)12);
            var8++;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxs;III)V")
   @ObfuscatedName("ba")
   void method12898(PacketBuffer var1, int var2, int var3, int var4) {
      this.field6882 = true;
      int var5 = var1.method13099(-1055517426);
      var1.method12981((byte)87);

      for (int var6 = 0; var6 < var2; var6++) {
         for (int var7 = 0; var7 < var3; var7++) {
            for (int var8 = 0; var8 < var4; var8++) {
               int var9 = var1.method12982(1, -866579681);
               if (1 == var9) {
                  this.field6883[var6][var7][var8] = var1.method12982(26, 1948589036);
               } else {
                  this.field6883[var6][var7][var8] = -1;
               }
            }
         }
      }

      var1.method12987(-2001055688);
      this.field6885 = new int[var5];
      Arrays.fill(this.field6885, -1);
      var5 = 0;

      for (int var17 = 0; var17 < var2; var17++) {
         for (int var18 = 0; var18 < var3; var18++) {
            for (int var19 = 0; var19 < var4; var19++) {
               int var20 = this.field6883[var17][var18][var19];
               if (-1 != var20) {
                  int var10 = classHA.method5987(var20, -2088305504);
                  int var11 = classKT.method6601(var20, -2090640557);
                  int var12 = classKY.method6646(var10, (byte)-13);
                  int var13 = classKY.method6646(var11, (byte)-23);
                  int var14 = classMH.method7613(var12, var13, (byte)120);

                  for (int var15 = 0; var15 < var5; var15++) {
                     if (this.field6885[var15] == var14) {
                        var14 = -1;
                        break;
                     }
                  }

                  if (var14 != -1) {
                     this.field6885[var5] = var14;
                     var5++;
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aw")
   public static int method12872(int var0) {
      return var0 & 0xFF;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("av")
   public boolean method12875() {
      return this.field6882;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method12890(int var0) {
      try {
         classOR.field5096.method6468();
         classOR.field5097.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xk.af(" + ')');
      }
   }
}
