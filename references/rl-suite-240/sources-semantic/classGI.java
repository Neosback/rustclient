import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gi")
public class classGI {
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   public static final int field2590 = 512;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   static final int field2591 = 8;
   @ObfuscatedSignature(descriptor = "[Lgn;")
   @ObfuscatedName("az")
   classGN[] field2592;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field2589 = 4;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public void method5466() {
      this.field2592 = new classGN[1];
      classGW var1 = classGW.field2717;
      this.field2592[0] = new classGN(var1.field2724 * -105180329, var1.field2723 * 645983023);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   public void method5467(int var1) {
      try {
         this.field2592 = new classGN[1];
         classGW var2 = classGW.field2717;
         this.field2592[0] = new classGN(var2.field2724 * 2032697513, var2.field2723 * 645983023);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gi.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Z[I[II)I")
   @ObfuscatedName("af")
   public int method5470(int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8, int var9) {
      try {
         return method5475(this, var1, var2, var3, var4, var5, var6, var7, var8, this.field2592[0], -695297447);
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "gi.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;IIILgg;Lgc;Z[I[I)I")
   @ObfuscatedName("qv")
   public static int method5471(classGI var0, int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8) {
      return method5475(var0, var1, var2, var3, var4, var5, var6, var7, var8, var0.field2592[0], -695297447);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;)V")
   @ObfuscatedName("bn")
   public static void method5468(classGI var0) {
      if (var0 == null) {
         var0.method5466();
      } else {
         var0.field2592 = new classGN[1];
         classGW var1 = classGW.field2717;
         var0.field2592[0] = new classGN(var1.field2724 * 2032697513, var1.field2723 * 645983023);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)I")
   @ObfuscatedName("nr")
   public static int method5478(classQR var0) {
      return var0.field5596 * -1483899724;
   }

   @ObfuscatedSignature(descriptor = "(IILgg;Lgc;Lgn;B)Z")
   @ObfuscatedName("ab")
   final boolean method5480(int var1, int var2, classGG var3, CollisionData var4, classGN var5, byte var6) {
      try {
         int var7 = var5.method5554(-2037121757);
         int var8 = var5.method5561(1517141518);
         int[][] var9 = classGN.method5566(var5, (short)-26350);
         int[][] var10 = var5.method5563(339869670);
         int[] var11 = var5.method5570(1117883003);
         int[] var12 = var5.method5572(-1787708197);
         int var13 = var5.method5575((byte)-60);
         int var14 = var1;
         int var15 = var2;
         int var16 = var7 >> 1;
         int var17 = var8 >> 1;
         int var18 = var1 - var16;
         int var19 = var2 - var17;
         var10[var16][var17] = 99;
         var9[var16][var17] = 0;
         int var20 = 0;
         int var21 = 0;
         var11[var20] = var1;
         var12[var20++] = var2;

         while (var21 != var20) {
            var14 = var11[var21];
            var15 = var12[var21];
            var21 = var21 + 1 & var13;
            var16 = var14 - var18;
            var17 = var15 - var19;
            int var22 = var14 - var4.method5335(-1702279186);
            int var23 = var15 - var4.method5339((byte)103);
            if (var3.vmethod144(1, var14, var15, var4, (byte)1)) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               var5.method5547(var14, var15, -1139149563);
               return true;
            }

            int var24 = var9[var16][var17] + 1;
            if (var16 > 0) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (var10[var16 - 1][var17] == 0 && !var4.method5358(var22 - 1, var23, 1076101384, (byte)51)) {
                  var11[var20] = var14 - 1;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 - 1][var17] = 2;
                  var9[var16 - 1][var17] = var24;
               }
            }

            if (var16 < var7 - 1 && var10[1 + var16][var17] == 0) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (!var4.method5358(1 + var22, var23, 1076101504, (byte)-37)) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  var11[var20] = var14 + 1;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[1 + var16][var17] = 8;
                  var9[var16 + 1][var17] = var24;
               }
            }

            if (var17 > 0) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (var10[var16][var17 - 1] == 0) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  if (!var4.method5358(var22, var23 - 1, 1076101378, (byte)-6)) {
                     var11[var20] = var14;
                     var12[var20] = var15 - 1;
                     var20 = var20 + 1 & var13;
                     var10[var16][var17 - 1] = 1;
                     var9[var16][var17 - 1] = var24;
                  }
               }
            }

            if (var17 < var8 - 1) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (0 == var10[var16][var17 + 1]) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  if (!var4.method5358(var22, var23 + 1, 1076101408, (byte)7)) {
                     if (var6 != 0) {
                        throw new IllegalStateException();
                     }

                     var11[var20] = var14;
                     var12[var20] = var15 + 1;
                     var20 = 1 + var20 & var13;
                     var10[var16][1 + var17] = 4;
                     var9[var16][var17 + 1] = var24;
                  }
               }
            }

            if (var16 > 0) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (var17 > 0 && 0 == var10[var16 - 1][var17 - 1] && !var4.method5358(var22 - 1, var23 - 1, 1076101390, (byte)-24)) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  if (!var4.method5358(var22 - 1, var23, 1076101384, (byte)-58)) {
                     if (var6 != 0) {
                        throw new IllegalStateException();
                     }

                     if (!var4.method5358(var22, var23 - 1, 1076101378, (byte)-12)) {
                        var11[var20] = var14 - 1;
                        var12[var20] = var15 - 1;
                        var20 = 1 + var20 & var13;
                        var10[var16 - 1][var17 - 1] = 3;
                        var9[var16 - 1][var17 - 1] = var24;
                     }
                  }
               }
            }

            if (var16 < var7 - 1 && var17 > 0) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (0 == var10[var16 + 1][var17 - 1]) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  if (!var4.method5358(var22 + 1, var23 - 1, 1076101507, (byte)71)) {
                     if (var6 != 0) {
                        throw new IllegalStateException();
                     }

                     if (!var4.method5358(var22 + 1, var23, 1076101504, (byte)-12)) {
                        if (var6 != 0) {
                           throw new IllegalStateException();
                        }

                        if (!var4.method5358(var22, var23 - 1, 1076101378, (byte)102)) {
                           if (var6 != 0) {
                              throw new IllegalStateException();
                           }

                           var11[var20] = 1 + var14;
                           var12[var20] = var15 - 1;
                           var20 = 1 + var20 & var13;
                           var10[1 + var16][var17 - 1] = 9;
                           var9[1 + var16][var17 - 1] = var24;
                        }
                     }
                  }
               }
            }

            if (var16 > 0) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (var17 < var8 - 1) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var10[var16 - 1][1 + var17] == 0 && !var4.method5358(var22 - 1, var23 + 1, 1076101432, (byte)13)) {
                     if (var6 != 0) {
                        throw new IllegalStateException();
                     }

                     if (!var4.method5358(var22 - 1, var23, 1076101384, (byte)81)) {
                        if (var6 != 0) {
                           throw new IllegalStateException();
                        }

                        if (!var4.method5358(var22, 1 + var23, 1076101408, (byte)49)) {
                           if (var6 != 0) {
                              throw new IllegalStateException();
                           }

                           var11[var20] = var14 - 1;
                           var12[var20] = var15 + 1;
                           var20 = var20 + 1 & var13;
                           var10[var16 - 1][var17 + 1] = 6;
                           var9[var16 - 1][var17 + 1] = var24;
                        }
                     }
                  }
               }
            }

            if (var16 < var7 - 1 && var17 < var8 - 1) {
               if (var6 != 0) {
                  throw new IllegalStateException();
               }

               if (0 == var10[var16 + 1][var17 + 1]) {
                  if (var6 != 0) {
                     throw new IllegalStateException();
                  }

                  if (!var4.method5358(var22 + 1, var23 + 1, 1076101600, (byte)43) && !var4.method5358(var22 + 1, var23, 1076101504, (byte)46)) {
                     if (var6 != 0) {
                        throw new IllegalStateException();
                     }

                     if (!var4.method5358(var22, 1 + var23, 1076101408, (byte)65)) {
                        if (var6 != 0) {
                           throw new IllegalStateException();
                        }

                        var11[var20] = var14 + 1;
                        var12[var20] = 1 + var15;
                        var20 = var20 + 1 & var13;
                        var10[var16 + 1][1 + var17] = 12;
                        var9[1 + var16][1 + var17] = var24;
                     }
                  }
               }
            }
         }

         var5.method5547(var14, var15, -1139149563);
         return false;
      } catch (RuntimeException var25) {
         throw classEG.method3899(var25, "gi.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILgg;Lgc;Lgn;I)Z")
   @ObfuscatedName("ag")
   final boolean method5486(int var1, int var2, classGG var3, CollisionData var4, classGN var5, int var6) {
      try {
         int var7 = var5.method5554(-280749985);
         int var8 = var5.method5561(-1264643632);
         int[][] var9 = classGN.method5566(var5, (short)-3684);
         int[][] var10 = var5.method5563(-111305917);
         int[] var11 = var5.method5570(1117883003);
         int[] var12 = var5.method5572(-1787708197);
         int var13 = var5.method5575((byte)-114);
         int var14 = var1;
         int var15 = var2;
         int var16 = var7 >> 1;
         int var17 = var8 >> 1;
         int var18 = var1 - var16;
         int var19 = var2 - var17;
         var10[var16][var17] = 99;
         var9[var16][var17] = 0;
         int var20 = 0;
         int var21 = 0;
         var11[var20] = var1;
         var12[var20++] = var2;

         while (var20 != var21) {
            if (var6 >= 1560933415) {
               throw new IllegalStateException();
            }

            var14 = var11[var21];
            var15 = var12[var21];
            var21 = 1 + var21 & var13;
            var16 = var14 - var18;
            var17 = var15 - var19;
            int var22 = var14 - var4.method5335(-1702279186);
            int var23 = var15 - var4.method5339((byte)7);
            if (var3.vmethod144(2, var14, var15, var4, (byte)1)) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               var5.method5547(var14, var15, -1139149563);
               return true;
            }

            int var24 = 1 + var9[var16][var17];
            if (var16 > 0) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (0 != var10[var16 - 1][var17]) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (!var4.method5358(var22 - 1, var23, 1076101390, (byte)-58)) {
                  if (var4.method5358(var22 - 1, var23 + 1, 1076101432, (byte)-53)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else {
                     var11[var20] = var14 - 1;
                     var12[var20] = var15;
                     var20 = 1 + var20 & var13;
                     var10[var16 - 1][var17] = 2;
                     var9[var16 - 1][var17] = var24;
                  }
               }
            }

            if (var16 < var7 - 2) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (var10[var16 + 1][var17] != 0) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (var4.method5358(2 + var22, var23, 1076101507, (byte)33)) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (var4.method5358(var22 + 2, 1 + var23, 1076101600, (byte)-34)) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else {
                  var11[var20] = var14 + 1;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 + 1][var17] = 8;
                  var9[1 + var16][var17] = var24;
               }
            }

            if (var17 > 0) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (0 != var10[var16][var17 - 1]) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (var4.method5358(var22, var23 - 1, 1076101390, (byte)35)) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (var4.method5358(var22 + 1, var23 - 1, 1076101507, (byte)-28)) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else {
                  var11[var20] = var14;
                  var12[var20] = var15 - 1;
                  var20 = 1 + var20 & var13;
                  var10[var16][var17 - 1] = 1;
                  var9[var16][var17 - 1] = var24;
               }
            }

            if (var17 < var8 - 2) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (var10[var16][var17 + 1] != 0) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (var4.method5358(var22, 2 + var23, 1076101432, (byte)-54)) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else if (var4.method5358(var22 + 1, var23 + 2, 1076101600, (byte)-47)) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }
               } else {
                  var11[var20] = var14;
                  var12[var20] = var15 + 1;
                  var20 = var20 + 1 & var13;
                  var10[var16][1 + var17] = 4;
                  var9[var16][var17 + 1] = var24;
               }
            }

            if (var16 > 0) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (var17 > 0) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }

                  if (0 != var10[var16 - 1][var17 - 1]) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22 - 1, var23, 1076101438, (byte)-71)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22 - 1, var23 - 1, 1076101390, (byte)-8)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22, var23 - 1, 1076101519, (byte)45)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else {
                     var11[var20] = var14 - 1;
                     var12[var20] = var15 - 1;
                     var20 = var20 + 1 & var13;
                     var10[var16 - 1][var17 - 1] = 3;
                     var9[var16 - 1][var17 - 1] = var24;
                  }
               }
            }

            if (var16 < var7 - 2) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (var17 > 0) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }

                  if (0 != var10[var16 + 1][var17 - 1]) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22 + 1, var23 - 1, 1076101519, (byte)80)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(2 + var22, var23 - 1, 1076101507, (byte)20)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22 + 2, var23, 1076101603, (byte)-49)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else {
                     var11[var20] = var14 + 1;
                     var12[var20] = var15 - 1;
                     var20 = var20 + 1 & var13;
                     var10[var16 + 1][var17 - 1] = 9;
                     var9[var16 + 1][var17 - 1] = var24;
                  }
               }
            }

            if (var16 > 0) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (var17 < var8 - 2) {
                  if (0 != var10[var16 - 1][var17 + 1]) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22 - 1, var23 + 1, 1076101438, (byte)-60)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (var4.method5358(var22 - 1, var23 + 2, 1076101432, (byte)81)) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (!var4.method5358(var22, 2 + var23, 1076101624, (byte)28)) {
                     var11[var20] = var14 - 1;
                     var12[var20] = 1 + var15;
                     var20 = var20 + 1 & var13;
                     var10[var16 - 1][var17 + 1] = 6;
                     var9[var16 - 1][var17 + 1] = var24;
                  }
               }
            }

            if (var16 < var7 - 2) {
               if (var6 >= 1560933415) {
                  throw new IllegalStateException();
               }

               if (var17 < var8 - 2) {
                  if (var6 >= 1560933415) {
                     throw new IllegalStateException();
                  }

                  if (var10[1 + var16][var17 + 1] != 0) {
                     if (var6 >= 1560933415) {
                        throw new IllegalStateException();
                     }
                  } else if (!var4.method5358(var22 + 1, 2 + var23, 1076101624, (byte)-113)) {
                     if (var4.method5358(2 + var22, var23 + 2, 1076101600, (byte)37)) {
                        if (var6 >= 1560933415) {
                           throw new IllegalStateException();
                        }
                     } else if (var4.method5358(2 + var22, 1 + var23, 1076101603, (byte)-58)) {
                        if (var6 >= 1560933415) {
                           throw new IllegalStateException();
                        }
                     } else {
                        var11[var20] = 1 + var14;
                        var12[var20] = var15 + 1;
                        var20 = 1 + var20 & var13;
                        var10[1 + var16][var17 + 1] = 12;
                        var9[1 + var16][1 + var17] = var24;
                     }
                  }
               }
            }
         }

         var5.method5547(var14, var15, -1139149563);
         return false;
      } catch (RuntimeException var25) {
         throw classEG.method3899(var25, "gi.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Lgn;I)Z")
   @ObfuscatedName("as")
   final boolean method5489(int var1, int var2, int var3, classGG var4, CollisionData var5, classGN var6, int var7) {
      try {
         int var8 = var6.method5554(365748658);
         int var9 = var6.method5561(2022267518);
         int[][] var10 = classGN.method5566(var6, (short)16534);
         int[][] var11 = var6.method5563(2000401458);
         int[] var12 = var6.method5570(1117883003);
         int[] var13 = var6.method5572(-1787708197);
         int var14 = var6.method5575((byte)-16);
         int var15 = var1;
         int var16 = var2;
         int var17 = var8 >> 1;
         int var18 = var9 >> 1;
         int var19 = var1 - var17;
         int var20 = var2 - var18;
         var11[var17][var18] = 99;
         var10[var17][var18] = 0;
         int var21 = 0;
         int var22 = 0;
         var12[var21] = var1;
         var13[var21++] = var2;

         label453:
         while (var22 != var21) {
            var15 = var12[var22];
            var16 = var13[var22];
            var22 = var22 + 1 & var14;
            var17 = var15 - var19;
            var18 = var16 - var20;
            int var23 = var15 - var5.method5335(-1702279186);
            int var24 = var16 - var5.method5339((byte)122);
            if (var4.vmethod144(var3, var15, var16, var5, (byte)1)) {
               var6.method5547(var15, var16, -1139149563);
               return true;
            }

            int var25 = var10[var17][var18] + 1;
            if (var17 > 0) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (var11[var17 - 1][var18] != 0) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }
               } else if (var5.method5358(var23 - 1, var24, 1076101390, (byte)-70)) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }
               } else if (var5.method5358(var23 - 1, var24 + var3 - 1, 1076101432, (byte)-26)) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }
               } else {
                  label478: {
                     for (int var26 = 1; var26 < var3 - 1; var26++) {
                        if (var7 == 16711935) {
                           throw new IllegalStateException();
                        }

                        if (var5.method5358(var23 - 1, var24 + var26, 1076101438, (byte)-13)) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }
                           break label478;
                        }
                     }

                     var12[var21] = var15 - 1;
                     var13[var21] = var16;
                     var21 = 1 + var21 & var14;
                     var11[var17 - 1][var18] = 2;
                     var10[var17 - 1][var18] = var25;
                  }
               }
            }

            if (var17 < var8 - var3) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (0 == var11[var17 + 1][var18]) {
                  if (var5.method5358(var3 + var23, var24, 1076101507, (byte)55)) {
                     if (var7 == 16711935) {
                        throw new IllegalStateException();
                     }
                  } else if (var5.method5358(var23 + var3, var24 + var3 - 1, 1076101600, (byte)27)) {
                     if (var7 == 16711935) {
                        throw new IllegalStateException();
                     }
                  } else {
                     label482: {
                        for (int var31 = 1; var31 < var3 - 1; var31++) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }

                           if (var5.method5358(var3 + var23, var24 + var31, 1076101603, (byte)81)) {
                              if (var7 == 16711935) {
                                 throw new IllegalStateException();
                              }
                              break label482;
                           }
                        }

                        var12[var21] = 1 + var15;
                        var13[var21] = var16;
                        var21 = 1 + var21 & var14;
                        var11[var17 + 1][var18] = 8;
                        var10[1 + var17][var18] = var25;
                     }
                  }
               }
            }

            if (var18 > 0) {
               if (0 != var11[var17][var18 - 1]) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }
               } else if (var5.method5358(var23, var24 - 1, 1076101390, (byte)-50)) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }
               } else {
                  label323:
                  if (!var5.method5358(var3 + var23 - 1, var24 - 1, 1076101507, (byte)17)) {
                     for (int var32 = 1; var32 < var3 - 1; var32++) {
                        if (var7 == 16711935) {
                           throw new IllegalStateException();
                        }

                        if (var5.method5358(var23 + var32, var24 - 1, 1076101519, (byte)-17)) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }
                           break label323;
                        }
                     }

                     var12[var21] = var15;
                     var13[var21] = var16 - 1;
                     var21 = 1 + var21 & var14;
                     var11[var17][var18 - 1] = 1;
                     var10[var17][var18 - 1] = var25;
                  }
               }
            }

            if (var18 < var9 - var3) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (var11[var17][1 + var18] == 0 && !var5.method5358(var23, var24 + var3, 1076101432, (byte)-32)) {
                  if (var5.method5358(var23 + var3 - 1, var3 + var24, 1076101600, (byte)-13)) {
                     if (var7 == 16711935) {
                        throw new IllegalStateException();
                     }
                  } else {
                     label490: {
                        for (int var33 = 1; var33 < var3 - 1; var33++) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }

                           if (var5.method5358(var33 + var23, var24 + var3, 1076101624, (byte)-12)) {
                              if (var7 == 16711935) {
                                 throw new IllegalStateException();
                              }
                              break label490;
                           }
                        }

                        var12[var21] = var15;
                        var13[var21] = var16 + 1;
                        var21 = var21 + 1 & var14;
                        var11[var17][1 + var18] = 4;
                        var10[var17][1 + var18] = var25;
                     }
                  }
               }
            }

            if (var17 > 0) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (var18 > 0) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }

                  label361:
                  if (var11[var17 - 1][var18 - 1] == 0 && !var5.method5358(var23 - 1, var24 - 1, 1076101390, (byte)-91)) {
                     for (int var34 = 1; var34 < var3; var34++) {
                        if (var7 == 16711935) {
                           throw new IllegalStateException();
                        }

                        if (var5.method5358(var23 - 1, var24 - 1 + var34, 1076101438, (byte)6)) {
                           break label361;
                        }

                        if (var5.method5358(var23 - 1 + var34, var24 - 1, 1076101519, (byte)-111)) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }
                           break label361;
                        }
                     }

                     var12[var21] = var15 - 1;
                     var13[var21] = var16 - 1;
                     var21 = var21 + 1 & var14;
                     var11[var17 - 1][var18 - 1] = 3;
                     var10[var17 - 1][var18 - 1] = var25;
                  }
               }
            }

            if (var17 < var8 - var3) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (var18 > 0) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }

                  if (0 == var11[1 + var17][var18 - 1]) {
                     if (var5.method5358(var3 + var23, var24 - 1, 1076101507, (byte)-81)) {
                        if (var7 == 16711935) {
                           throw new IllegalStateException();
                        }
                     } else {
                        label498: {
                           for (int var35 = 1; var35 < var3; var35++) {
                              if (var7 == 16711935) {
                                 throw new IllegalStateException();
                              }

                              if (var5.method5358(var3 + var23, var35 + (var24 - 1), 1076101603, (byte)52)) {
                                 if (var7 == 16711935) {
                                    throw new IllegalStateException();
                                 }
                                 break label498;
                              }

                              if (var5.method5358(var23 + var35, var24 - 1, 1076101519, (byte)22)) {
                                 if (var7 == 16711935) {
                                    throw new IllegalStateException();
                                 }
                                 break label498;
                              }
                           }

                           var12[var21] = var15 + 1;
                           var13[var21] = var16 - 1;
                           var21 = 1 + var21 & var14;
                           var11[var17 + 1][var18 - 1] = 9;
                           var10[var17 + 1][var18 - 1] = var25;
                        }
                     }
                  }
               }
            }

            if (var17 > 0) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (var18 < var9 - var3 && 0 == var11[var17 - 1][1 + var18]) {
                  if (var5.method5358(var23 - 1, var3 + var24, 1076101432, (byte)79)) {
                     if (var7 == 16711935) {
                        throw new IllegalStateException();
                     }
                  } else {
                     int var36 = 1;

                     while (true) {
                        if (var36 >= var3) {
                           var12[var21] = var15 - 1;
                           var13[var21] = 1 + var16;
                           var21 = 1 + var21 & var14;
                           var11[var17 - 1][var18 + 1] = 6;
                           var10[var17 - 1][var18 + 1] = var25;
                           break;
                        }

                        if (var5.method5358(var23 - 1, var36 + var24, 1076101438, (byte)-16)) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }
                           break;
                        }

                        if (var5.method5358(var23 - 1 + var36, var3 + var24, 1076101624, (byte)4)) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }
                           break;
                        }

                        var36++;
                     }
                  }
               }
            }

            if (var17 < var8 - var3) {
               if (var7 == 16711935) {
                  throw new IllegalStateException();
               }

               if (var18 < var9 - var3) {
                  if (var7 == 16711935) {
                     throw new IllegalStateException();
                  }

                  if (var11[var17 + 1][var18 + 1] == 0) {
                     if (var5.method5358(var3 + var23, var3 + var24, 1076101600, (byte)-11)) {
                        if (var7 == 16711935) {
                           throw new IllegalStateException();
                        }
                     } else {
                        for (int var37 = 1; var37 < var3; var37++) {
                           if (var7 == 16711935) {
                              throw new IllegalStateException();
                           }

                           if (var5.method5358(var23 + var37, var3 + var24, 1076101624, (byte)-82)) {
                              if (var7 == 16711935) {
                                 throw new IllegalStateException();
                              }
                              continue label453;
                           }

                           if (var5.method5358(var3 + var23, var37 + var24, 1076101603, (byte)42)) {
                              continue label453;
                           }
                        }

                        var12[var21] = var15 + 1;
                        var13[var21] = 1 + var16;
                        var21 = var21 + 1 & var14;
                        var11[1 + var17][var18 + 1] = 12;
                        var10[1 + var17][var18 + 1] = var25;
                     }
                  }
               }
            }
         }

         var6.method5547(var15, var16, -1139149563);
         return false;
      } catch (RuntimeException var27) {
         throw classEG.method3899(var27, "gi.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("ne")
   public static boolean method5487(classGI var0, int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      if (var0 == null) {
         var0.method5491(var1, var1, var1, var3, var4, var5);
      }

      int var6 = var5.method5554(596996904);
      int var7 = var5.method5561(-943440205);
      int[][] var8 = classGN.method5566(var5, (short)-21870);
      int[][] var9 = var5.method5563(2086121643);
      int[] var10 = var5.method5570(1117883003);
      int[] var11 = var5.method5572(-1787708197);
      int var12 = var5.method5575((byte)-27);
      int var13 = var1;
      int var14 = var2;
      int var15 = var6 >> 1;
      int var16 = var7 >> 1;
      int var17 = var1 - var15;
      int var18 = var2 - var16;
      var9[var15][var16] = 99;
      var8[var15][var16] = 0;
      int var19 = 0;
      int var20 = 0;
      var10[var19] = var1;
      var11[var19++] = var2;

      while (var19 != var20) {
         var13 = var10[var20];
         var14 = var11[var20];
         var20 = 1 + var20 & var12;
         var15 = var13 - var17;
         var16 = var14 - var18;
         int var21 = var13 - var4.method5335(-1702279186);
         int var22 = var14 - var4.method5339((byte)39);
         if (var3.vmethod144(2, var13, var14, var4, (byte)1)) {
            var5.method5547(var13, var14, -1139149563);
            return true;
         }

         int var23 = 1 + var8[var15][var16];
         if (var15 > 0
            && 0 == var9[var15 - 1][var16]
            && !var4.method5358(var21 - 1, var22, 1076101390, (byte)53)
            && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)45)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16] = 2;
            var8[var15 - 1][var16] = var23;
         }

         if (var15 < var6 - 2
            && var9[var15 + 1][var16] == 0
            && !var4.method5358(2 + var21, var22, 1076101507, (byte)-9)
            && !var4.method5358(var21 + 2, 1 + var22, 1076101600, (byte)-107)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 + 1][var16] = 8;
            var8[1 + var15][var16] = var23;
         }

         if (var16 > 0
            && 0 == var9[var15][var16 - 1]
            && !var4.method5358(var21, var22 - 1, 1076101390, (byte)11)
            && !var4.method5358(var21 + 1, var22 - 1, 1076101507, (byte)-61)) {
            var10[var19] = var13;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[var15][var16 - 1] = 1;
            var8[var15][var16 - 1] = var23;
         }

         if (var16 < var7 - 2
            && var9[var15][var16 + 1] == 0
            && !var4.method5358(var21, 2 + var22, 1076101432, (byte)-113)
            && !var4.method5358(var21 + 1, var22 + 2, 1076101600, (byte)-63)) {
            var10[var19] = var13;
            var11[var19] = var14 + 1;
            var19 = var19 + 1 & var12;
            var9[var15][1 + var16] = 4;
            var8[var15][var16 + 1] = var23;
         }

         if (var15 > 0
            && var16 > 0
            && 0 == var9[var15 - 1][var16 - 1]
            && !var4.method5358(var21 - 1, var22, 1076101438, (byte)4)
            && !var4.method5358(var21 - 1, var22 - 1, 1076101390, (byte)10)
            && !var4.method5358(var21, var22 - 1, 1076101519, (byte)-12)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 - 1] = 3;
            var8[var15 - 1][var16 - 1] = var23;
         }

         if (var15 < var6 - 2
            && var16 > 0
            && 0 == var9[var15 + 1][var16 - 1]
            && !var4.method5358(var21 + 1, var22 - 1, 1076101519, (byte)-108)
            && !var4.method5358(2 + var21, var22 - 1, 1076101507, (byte)-28)
            && !var4.method5358(var21 + 2, var22, 1076101603, (byte)98)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15 + 1][var16 - 1] = 9;
            var8[var15 + 1][var16 - 1] = var23;
         }

         if (var15 > 0
            && var16 < var7 - 2
            && 0 == var9[var15 - 1][var16 + 1]
            && !var4.method5358(var21 - 1, var22 + 1, 1076101438, (byte)32)
            && !var4.method5358(var21 - 1, var22 + 2, 1076101432, (byte)21)
            && !var4.method5358(var21, 2 + var22, 1076101624, (byte)70)) {
            var10[var19] = var13 - 1;
            var11[var19] = 1 + var14;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 + 1] = 6;
            var8[var15 - 1][var16 + 1] = var23;
         }

         if (var15 < var6 - 2
            && var16 < var7 - 2
            && var9[1 + var15][var16 + 1] == 0
            && !var4.method5358(var21 + 1, 2 + var22, 1076101624, (byte)-38)
            && !var4.method5358(2 + var21, var22 + 2, 1076101600, (byte)18)
            && !var4.method5358(2 + var21, 1 + var22, 1076101603, (byte)-30)) {
            var10[var19] = 1 + var13;
            var11[var19] = var14 + 1;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16 + 1] = 12;
            var8[1 + var15][1 + var16] = var23;
         }
      }

      var5.method5547(var13, var14, -1139149563);
      return false;
   }

   @ObfuscatedSignature(descriptor = "(IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("ai")
   final boolean method5481(int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      int var6 = var5.method5554(709925561);
      int var7 = var5.method5561(1835330660);
      int[][] var8 = classGN.method5566(var5, (short)-15653);
      int[][] var9 = var5.method5563(476981489);
      int[] var10 = var5.method5570(1117883003);
      int[] var11 = var5.method5572(-1787708197);
      int var12 = var5.method5575((byte)8);
      int var13 = var1;
      int var14 = var2;
      int var15 = var6 >> 1;
      int var16 = var7 >> 1;
      int var17 = var1 - var15;
      int var18 = var2 - var16;
      var9[var15][var16] = -374648163;
      var8[var15][var16] = 0;
      int var19 = 0;
      int var20 = 0;
      var10[var19] = var1;
      var11[var19++] = var2;

      while (var20 != var19) {
         var13 = var10[var20];
         var14 = var11[var20];
         var20 = var20 + 1 & var12;
         var15 = var13 - var17;
         var16 = var14 - var18;
         int var21 = var13 - var4.method5335(-1702279186);
         int var22 = var14 - var4.method5339((byte)91);
         if (var3.vmethod144(1, var13, var14, var4, (byte)1)) {
            var5.method5547(var13, var14, -1139149563);
            return true;
         }

         int var23 = var8[var15][var16] + 1;
         if (var15 > 0 && var9[var15 - 1][var16] == 0 && !var4.method5358(var21 - 1, var22, 1076101384, (byte)-44)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16] = 2;
            var8[var15 - 1][var16] = var23;
         }

         if (var15 < var6 - 1 && var9[1 + var15][var16] == 0 && !var4.method5358(1 + var21, var22, 1076101504, (byte)16)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16] = 8;
            var8[var15 + 1][var16] = var23;
         }

         if (var16 > 0 && var9[var15][var16 - 1] == 0 && !var4.method5358(var21, var22 - 1, -2048558621, (byte)46)) {
            var10[var19] = var13;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15][var16 - 1] = 1;
            var8[var15][var16 - 1] = var23;
         }

         if (var16 < var7 - 1 && 0 == var9[var15][var16 + 1] && !var4.method5358(var21, var22 + 1, 1076101408, (byte)11)) {
            var10[var19] = var13;
            var11[var19] = var14 + 1;
            var19 = 1 + var19 & var12;
            var9[var15][1 + var16] = 4;
            var8[var15][var16 + 1] = var23;
         }

         if (var15 > 0
            && var16 > 0
            && 0 == var9[var15 - 1][var16 - 1]
            && !var4.method5358(var21 - 1, var22 - 1, 52632766, (byte)35)
            && !var4.method5358(var21 - 1, var22, -1652621804, (byte)-49)
            && !var4.method5358(var21, var22 - 1, 1076101378, (byte)55)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16 - 1] = 3;
            var8[var15 - 1][var16 - 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 > 0
            && 0 == var9[var15 + 1][var16 - 1]
            && !var4.method5358(var21 + 1, var22 - 1, 1076101507, (byte)15)
            && !var4.method5358(var21 + 1, var22, -2090964271, (byte)60)
            && !var4.method5358(var21, var22 - 1, 1076101378, (byte)22)) {
            var10[var19] = 1 + var13;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16 - 1] = 9;
            var8[1 + var15][var16 - 1] = var23;
         }

         if (var15 > 0
            && var16 < var7 - 1
            && var9[var15 - 1][1 + var16] == 0
            && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)50)
            && !var4.method5358(var21 - 1, var22, 1852686127, (byte)-42)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)72)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 + 1;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 + 1] = 6;
            var8[var15 - 1][var16 + 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 < var7 - 1
            && 0 == var9[var15 + 1][var16 + 1]
            && !var4.method5358(var21 + 1, var22 + 1, -387216851, (byte)42)
            && !var4.method5358(var21 + 1, var22, 745563819, (byte)-17)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)3)) {
            var10[var19] = var13 + 1;
            var11[var19] = 1 + var14;
            var19 = var19 + 1 & var12;
            var9[var15 + 1][1 + var16] = 12;
            var8[1 + var15][1 + var16] = var23;
         }
      }

      var5.method5547(var13, var14, -1139149563);
      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("hj")
   public static boolean method5482(classGI var0, int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      if (var0 == null) {
         return var0.method5486(var1, var1, var3, var4, var5, var1);
      } else {
         int var6 = var5.method5554(-1321046212);
         int var7 = var5.method5561(654872746);
         int[][] var8 = classGN.method5566(var5, (short)-3051);
         int[][] var9 = var5.method5563(-226665057);
         int[] var10 = var5.method5570(1117883003);
         int[] var11 = var5.method5572(-1787708197);
         int var12 = var5.method5575((byte)-1);
         int var13 = var1;
         int var14 = var2;
         int var15 = var6 >> 1;
         int var16 = var7 >> 1;
         int var17 = var1 - var15;
         int var18 = var2 - var16;
         var9[var15][var16] = 99;
         var8[var15][var16] = 0;
         int var19 = 0;
         int var20 = 0;
         var10[var19] = var1;
         var11[var19++] = var2;

         while (var20 != var19) {
            var13 = var10[var20];
            var14 = var11[var20];
            var20 = var20 + 1 & var12;
            var15 = var13 - var17;
            var16 = var14 - var18;
            int var21 = var13 - var4.method5335(-1702279186);
            int var22 = var14 - var4.method5339((byte)5);
            if (var3.vmethod144(1, var13, var14, var4, (byte)1)) {
               var5.method5547(var13, var14, -1139149563);
               return true;
            }

            int var23 = var8[var15][var16] + 1;
            if (var15 > 0 && var9[var15 - 1][var16] == 0 && !var4.method5358(var21 - 1, var22, -1959465606, (byte)8)) {
               var10[var19] = var13 - 1;
               var11[var19] = var14;
               var19 = 1 + var19 & var12;
               var9[var15 - 1][var16] = 2;
               var8[var15 - 1][var16] = var23;
            }

            if (var15 < var6 - 1 && var9[1 + var15][var16] == 0 && !var4.method5358(1 + var21, var22, 1076101504, (byte)-7)) {
               var10[var19] = var13 + 1;
               var11[var19] = var14;
               var19 = 1 + var19 & var12;
               var9[1 + var15][var16] = 8;
               var8[var15 + 1][var16] = var23;
            }

            if (var16 > 0 && var9[var15][var16 - 1] == 0 && !var4.method5358(var21, var22 - 1, 1076101378, (byte)-6)) {
               var10[var19] = var13;
               var11[var19] = var14 - 1;
               var19 = var19 + 1 & var12;
               var9[var15][var16 - 1] = 1;
               var8[var15][var16 - 1] = var23;
            }

            if (var16 < var7 - 1 && 0 == var9[var15][var16 + 1] && !var4.method5358(var21, var22 + 1, -1173756987, (byte)42)) {
               var10[var19] = var13;
               var11[var19] = var14 + 1;
               var19 = 1 + var19 & var12;
               var9[var15][1 + var16] = 4;
               var8[var15][var16 + 1] = var23;
            }

            if (var15 > 0
               && var16 > 0
               && 0 == var9[var15 - 1][var16 - 1]
               && !var4.method5358(var21 - 1, var22 - 1, 1076101390, (byte)-3)
               && !var4.method5358(var21 - 1, var22, 649377530, (byte)23)
               && !var4.method5358(var21, var22 - 1, 1238625483, (byte)-21)) {
               var10[var19] = var13 - 1;
               var11[var19] = var14 - 1;
               var19 = 1 + var19 & var12;
               var9[var15 - 1][var16 - 1] = 3;
               var8[var15 - 1][var16 - 1] = var23;
            }

            if (var15 < var6 - 1
               && var16 > 0
               && 0 == var9[var15 + 1][var16 - 1]
               && !var4.method5358(var21 + 1, var22 - 1, -1843278124, (byte)88)
               && !var4.method5358(var21 + 1, var22, 1476067124, (byte)-59)
               && !var4.method5358(var21, var22 - 1, 1076101378, (byte)-2)) {
               var10[var19] = 1 + var13;
               var11[var19] = var14 - 1;
               var19 = 1 + var19 & var12;
               var9[1 + var15][var16 - 1] = 9;
               var8[1 + var15][var16 - 1] = var23;
            }

            if (var15 > 0
               && var16 < var7 - 1
               && var9[var15 - 1][1 + var16] == 0
               && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)-47)
               && !var4.method5358(var21 - 1, var22, 1685647039, (byte)-26)
               && !var4.method5358(var21, 1 + var22, 1076101408, (byte)-13)) {
               var10[var19] = var13 - 1;
               var11[var19] = var14 + 1;
               var19 = var19 + 1 & var12;
               var9[var15 - 1][var16 + 1] = 6;
               var8[var15 - 1][var16 + 1] = var23;
            }

            if (var15 < var6 - 1
               && var16 < var7 - 1
               && 0 == var9[var15 + 1][var16 + 1]
               && !var4.method5358(var21 + 1, var22 + 1, 1963118462, (byte)-64)
               && !var4.method5358(var21 + 1, var22, 1336876932, (byte)57)
               && !var4.method5358(var21, 1 + var22, 1076101408, (byte)39)) {
               var10[var19] = var13 + 1;
               var11[var19] = 1 + var14;
               var19 = var19 + 1 & var12;
               var9[var15 + 1][1 + var16] = 12;
               var8[1 + var15][1 + var16] = var23;
            }
         }

         var5.method5547(var13, var14, -1139149563);
         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;)V")
   @ObfuscatedName("js")
   public static void method5469(classGI var0) {
      if (var0 == null) {
         var0.method5466();
      } else {
         var0.field2592 = new classGN[1];
         classGW var1 = classGW.field2717;
         var0.field2592[0] = new classGN(var1.field2724 * 1302106641, var1.field2723 * 36441663);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Z[I[ILgn;)I")
   @ObfuscatedName("ay")
   int method5473(int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8, classGN var9) {
      var9.method5543((byte)-37);
      int var10 = var9.method5554(207402268);
      int var11 = var9.method5561(-956415838);
      int[][] var12 = classGN.method5566(var9, (short)-2263);
      int[][] var13 = var9.method5563(-1409551269);
      int[] var14 = var9.method5570(1117883003);
      int[] var15 = var9.method5572(-1787708197);
      boolean var16;
      if (var3 == 1) {
         var16 = this.method5480(var1, var2, var4, var5, var9, (byte)0);
      } else if (2 == var3) {
         var16 = this.method5486(var1, var2, var4, var5, var9, 946542844);
      } else {
         var16 = this.method5489(var1, var2, var3, var4, var5, var9, -181263329);
      }

      int var17 = var1 - (var10 >> 1);
      int var18 = var2 - (var11 >> 1);
      int var19 = var9.method5550((short)4729);
      int var20 = var9.method5551(1417081110);
      if (!var16) {
         if (!var6) {
            return -1;
         }

         int var21 = Integer.MAX_VALUE;
         int var22 = -1506588708;
         byte var23 = 10;
         int var24 = -1553928512 * var4.field2573;
         int var25 = var4.field2571 * -1069935115;
         int var26 = -14157764 * var4.field2570;
         int var27 = -14456739 * var4.field2569;

         for (int var28 = var24 - var23; var28 <= var24 + var23; var28++) {
            for (int var29 = var25 - var23; var29 <= var25 + var23; var29++) {
               int var30 = var28 - var17;
               int var31 = var29 - var18;
               if (var30 >= 0 && var31 >= 0 && var30 < var10 && var31 < var11 && var12[var30][var31] < 100) {
                  int var32 = 0;
                  if (var28 < var24) {
                     var32 = var24 - var28;
                  } else if (var28 > var24 + var26 - 1) {
                     var32 = var28 - (var26 + var24 - 1);
                  }

                  int var33 = 0;
                  if (var29 < var25) {
                     var33 = var25 - var29;
                  } else if (var29 > var27 + var25 - 1) {
                     var33 = var29 - (var27 + var25 - 1);
                  }

                  int var34 = var33 * var33 + var32 * var32;
                  if (var34 < var21 || var21 == var34 && var12[var30][var31] < var22) {
                     var21 = var34;
                     var22 = var12[var30][var31];
                     var19 = var28;
                     var20 = var29;
                  }
               }
            }
         }

         if (var21 == -685012942) {
            return -1;
         }
      }

      if (var19 == var1 && var20 == var2) {
         var7[0] = var19;
         var8[0] = var20;
         return 0;
      } else {
         int var35 = 0;
         var14[var35] = var19;
         var15[var35++] = var20;

         int var38;
         for (int var37 = var38 = var13[var19 - var17][var20 - var18]; var1 != var19 || var2 != var20; var37 = var13[var19 - var17][var20 - var18]) {
            if (var38 != var37) {
               var38 = var37;
               var14[var35] = var19;
               var15[var35++] = var20;
            }

            if ((var37 & 2) != 0) {
               var19++;
            } else if ((var37 & 8) != 0) {
               var19--;
            }

            if (0 != (var37 & 1)) {
               var20++;
            } else if (0 != (var37 & 4)) {
               var20--;
            }
         }

         int var39 = 0;

         while (var35-- > 0) {
            var7[var39] = var14[var35];
            var8[var39++] = var15[var35];
            if (var39 >= var7.length) {
               break;
            }
         }

         return var39;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("av")
   final boolean method5490(int var1, int var2, int var3, classGG var4, CollisionData var5, classGN var6) {
      int var7 = var6.method5554(-341990947);
      int var8 = var6.method5561(668340340);
      int[][] var9 = classGN.method5566(var6, (short)12566);
      int[][] var10 = var6.method5563(1648255177);
      int[] var11 = var6.method5570(1117883003);
      int[] var12 = var6.method5572(-1787708197);
      int var13 = var6.method5575((byte)-75);
      int var14 = var1;
      int var15 = var2;
      int var16 = var7 >> 1;
      int var17 = var8 >> 1;
      int var18 = var1 - var16;
      int var19 = var2 - var17;
      var10[var16][var17] = 99;
      var9[var16][var17] = 0;
      int var20 = 0;
      int var21 = 0;
      var11[var20] = var1;
      var12[var20++] = var2;

      label338:
      while (var21 != var20) {
         var14 = var11[var21];
         var15 = var12[var21];
         var21 = var21 + 1 & var13;
         var16 = var14 - var18;
         var17 = var15 - var19;
         int var22 = var14 - var5.method5335(-1702279186);
         int var23 = var15 - var5.method5339((byte)54);
         if (var4.vmethod144(var3, var14, var15, var5, (byte)1)) {
            var6.method5547(var14, var15, -1139149563);
            return true;
         }

         int var24 = var9[var16][var17] + 1;
         if (var16 > 0
            && var10[var16 - 1][var17] == 0
            && !var5.method5358(var22 - 1, var23, 1076101390, (byte)3)
            && !var5.method5358(var22 - 1, var23 + var3 - 1, 1076101432, (byte)26)) {
            int var25 = 1;

            while (true) {
               if (var25 >= var3 - 1) {
                  var11[var20] = var14 - 1;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 - 1][var17] = 2;
                  var9[var16 - 1][var17] = var24;
                  break;
               }

               if (var5.method5358(var22 - 1, var23 + var25, 1076101438, (byte)-5)) {
                  break;
               }

               var25++;
            }
         }

         if (var16 < var7 - var3
            && 0 == var10[var16 + 1][var17]
            && !var5.method5358(var3 + var22, var23, -427831362, (byte)67)
            && !var5.method5358(var22 + var3, var23 + var3 - 1, -1610392488, (byte)9)) {
            int var29 = 1;

            while (true) {
               if (var29 >= var3 - 1) {
                  var11[var20] = 1 + var14;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 + 1][var17] = 8;
                  var9[1 + var16][var17] = var24;
                  break;
               }

               if (var5.method5358(var3 + var22, var23 + var29, -24364261, (byte)-120)) {
                  break;
               }

               var29++;
            }
         }

         if (var17 > 0
            && 0 == var10[var16][var17 - 1]
            && !var5.method5358(var22, var23 - 1, 1313768272, (byte)88)
            && !var5.method5358(var3 + var22 - 1, var23 - 1, 1076101507, (byte)-22)) {
            int var30 = 1;

            while (true) {
               if (var30 >= var3 - 1) {
                  var11[var20] = var14;
                  var12[var20] = var15 - 1;
                  var20 = 1 + var20 & var13;
                  var10[var16][var17 - 1] = 1;
                  var9[var16][var17 - 1] = var24;
                  break;
               }

               if (var5.method5358(var22 + var30, var23 - 1, 1076101519, (byte)-19)) {
                  break;
               }

               var30++;
            }
         }

         if (var17 < var8 - var3
            && var10[var16][1 + var17] == 0
            && !var5.method5358(var22, var23 + var3, 1076101432, (byte)-101)
            && !var5.method5358(var22 + var3 - 1, var3 + var23, -278871920, (byte)61)) {
            int var31 = 1;

            while (true) {
               if (var31 >= var3 - 1) {
                  var11[var20] = var14;
                  var12[var20] = var15 + 1;
                  var20 = var20 + 1 & var13;
                  var10[var16][1 + var17] = 4;
                  var9[var16][1 + var17] = var24;
                  break;
               }

               if (var5.method5358(var31 + var22, var23 + var3, -1098920791, (byte)-56)) {
                  break;
               }

               var31++;
            }
         }

         if (var16 > 0 && var17 > 0 && var10[var16 - 1][var17 - 1] == 0 && !var5.method5358(var22 - 1, var23 - 1, 1076101390, (byte)-87)) {
            int var32 = 1;

            while (true) {
               if (var32 >= var3) {
                  var11[var20] = var14 - 1;
                  var12[var20] = var15 - 1;
                  var20 = var20 + 1 & var13;
                  var10[var16 - 1][var17 - 1] = 3;
                  var9[var16 - 1][var17 - 1] = var24;
                  break;
               }

               if (var5.method5358(var22 - 1, var23 - 1 + var32, -2046865416, (byte)60) || var5.method5358(var22 - 1 + var32, var23 - 1, 200701709, (byte)-25)) {
                  break;
               }

               var32++;
            }
         }

         if (var16 < var7 - var3 && var17 > 0 && 0 == var10[1 + var16][var17 - 1] && !var5.method5358(var3 + var22, var23 - 1, -191655788, (byte)61)) {
            int var33 = 1;

            while (true) {
               if (var33 >= var3) {
                  var11[var20] = var14 + 1;
                  var12[var20] = var15 - 1;
                  var20 = 1 + var20 & var13;
                  var10[var16 + 1][var17 - 1] = 9;
                  var9[var16 + 1][var17 - 1] = var24;
                  break;
               }

               if (var5.method5358(var3 + var22, var33 + (var23 - 1), 1076101603, (byte)46) || var5.method5358(var22 + var33, var23 - 1, 1076101519, (byte)112)
                  )
                {
                  break;
               }

               var33++;
            }
         }

         if (var16 > 0 && var17 < var8 - var3 && 0 == var10[var16 - 1][1 + var17] && !var5.method5358(var22 - 1, var3 + var23, 1076101432, (byte)35)) {
            int var34 = 1;

            while (true) {
               if (var34 >= var3) {
                  var11[var20] = var14 - 1;
                  var12[var20] = 1 + var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 - 1][var17 + 1] = 6;
                  var9[var16 - 1][var17 + 1] = var24;
                  break;
               }

               if (var5.method5358(var22 - 1, var34 + var23, 1076101438, (byte)8) || var5.method5358(var22 - 1 + var34, var3 + var23, 1076101624, (byte)-38)) {
                  break;
               }

               var34++;
            }
         }

         if (var16 < var7 - var3
            && var17 < var8 - var3
            && var10[var16 + 1][var17 + 1] == 0
            && !var5.method5358(var3 + var22, var3 + var23, 149942973, (byte)-6)) {
            for (int var35 = 1; var35 < var3; var35++) {
               if (var5.method5358(var22 + var35, var3 + var23, 166343638, (byte)62) || var5.method5358(var3 + var22, var35 + var23, 864584232, (byte)55)) {
                  continue label338;
               }
            }

            var11[var20] = var14 + 1;
            var12[var20] = 1 + var15;
            var20 = var20 + 1 & var13;
            var10[1 + var16][var17 + 1] = 12;
            var9[1 + var16][var17 + 1] = var24;
         }
      }

      var6.method5547(var14, var15, -1139149563);
      return false;
   }

   @ObfuscatedSignature(descriptor = "(IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("ad")
   final boolean method5483(int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      int var6 = var5.method5554(-1681902903);
      int var7 = var5.method5561(639604045);
      int[][] var8 = classGN.method5566(var5, (short)-4559);
      int[][] var9 = var5.method5563(-1744318501);
      int[] var10 = var5.method5570(1117883003);
      int[] var11 = var5.method5572(-1787708197);
      int var12 = var5.method5575((byte)-56);
      int var13 = var1;
      int var14 = var2;
      int var15 = var6 >> 1;
      int var16 = var7 >> 1;
      int var17 = var1 - var15;
      int var18 = var2 - var16;
      var9[var15][var16] = 870986003;
      var8[var15][var16] = 0;
      int var19 = 0;
      int var20 = 0;
      var10[var19] = var1;
      var11[var19++] = var2;

      while (var20 != var19) {
         var13 = var10[var20];
         var14 = var11[var20];
         var20 = var20 + 1 & var12;
         var15 = var13 - var17;
         var16 = var14 - var18;
         int var21 = var13 - var4.method5335(-1702279186);
         int var22 = var14 - var4.method5339((byte)33);
         if (var3.vmethod144(1, var13, var14, var4, (byte)1)) {
            var5.method5547(var13, var14, -1139149563);
            return true;
         }

         int var23 = var8[var15][var16] + 1;
         if (var15 > 0 && var9[var15 - 1][var16] == 0 && !var4.method5358(var21 - 1, var22, -1132787528, (byte)40)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16] = 2;
            var8[var15 - 1][var16] = var23;
         }

         if (var15 < var6 - 1 && var9[1 + var15][var16] == 0 && !var4.method5358(1 + var21, var22, 292694585, (byte)19)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16] = 8;
            var8[var15 + 1][var16] = var23;
         }

         if (var16 > 0 && var9[var15][var16 - 1] == 0 && !var4.method5358(var21, var22 - 1, 682264502, (byte)83)) {
            var10[var19] = var13;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15][var16 - 1] = 1;
            var8[var15][var16 - 1] = var23;
         }

         if (var16 < var7 - 1 && 0 == var9[var15][var16 + 1] && !var4.method5358(var21, var22 + 1, -1887895431, (byte)30)) {
            var10[var19] = var13;
            var11[var19] = var14 + 1;
            var19 = 1 + var19 & var12;
            var9[var15][1 + var16] = 4;
            var8[var15][var16 + 1] = var23;
         }

         if (var15 > 0
            && var16 > 0
            && 0 == var9[var15 - 1][var16 - 1]
            && !var4.method5358(var21 - 1, var22 - 1, -435781219, (byte)-49)
            && !var4.method5358(var21 - 1, var22, 1076101384, (byte)15)
            && !var4.method5358(var21, var22 - 1, -1744865591, (byte)35)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16 - 1] = 3;
            var8[var15 - 1][var16 - 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 > 0
            && 0 == var9[var15 + 1][var16 - 1]
            && !var4.method5358(var21 + 1, var22 - 1, -651057300, (byte)-16)
            && !var4.method5358(var21 + 1, var22, 1076101504, (byte)-49)
            && !var4.method5358(var21, var22 - 1, -1305921190, (byte)-27)) {
            var10[var19] = 1 + var13;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16 - 1] = 9;
            var8[1 + var15][var16 - 1] = var23;
         }

         if (var15 > 0
            && var16 < var7 - 1
            && var9[var15 - 1][1 + var16] == 0
            && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)-26)
            && !var4.method5358(var21 - 1, var22, 1076101384, (byte)-15)
            && !var4.method5358(var21, 1 + var22, -1149510724, (byte)-44)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 + 1;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 + 1] = 6;
            var8[var15 - 1][var16 + 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 < var7 - 1
            && 0 == var9[var15 + 1][var16 + 1]
            && !var4.method5358(var21 + 1, var22 + 1, 57646924, (byte)-20)
            && !var4.method5358(var21 + 1, var22, 1676377872, (byte)-48)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)-11)) {
            var10[var19] = var13 + 1;
            var11[var19] = 1 + var14;
            var19 = var19 + 1 & var12;
            var9[var15 + 1][1 + var16] = 12;
            var8[1 + var15][1 + var16] = var23;
         }
      }

      var5.method5547(var13, var14, -1139149563);
      return false;
   }

   @ObfuscatedSignature(descriptor = "(IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("ap")
   final boolean method5484(int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      int var6 = var5.method5554(-1322916268);
      int var7 = var5.method5561(1447469304);
      int[][] var8 = classGN.method5566(var5, (short)9087);
      int[][] var9 = var5.method5563(-1226053852);
      int[] var10 = var5.method5570(1117883003);
      int[] var11 = var5.method5572(-1787708197);
      int var12 = var5.method5575((byte)-64);
      int var13 = var1;
      int var14 = var2;
      int var15 = var6 >> 1;
      int var16 = var7 >> 1;
      int var17 = var1 - var15;
      int var18 = var2 - var16;
      var9[var15][var16] = 99;
      var8[var15][var16] = 0;
      int var19 = 0;
      int var20 = 0;
      var10[var19] = var1;
      var11[var19++] = var2;

      while (var20 != var19) {
         var13 = var10[var20];
         var14 = var11[var20];
         var20 = var20 + 1 & var12;
         var15 = var13 - var17;
         var16 = var14 - var18;
         int var21 = var13 - var4.method5335(-1702279186);
         int var22 = var14 - var4.method5339((byte)14);
         if (var3.vmethod144(1, var13, var14, var4, (byte)1)) {
            var5.method5547(var13, var14, -1139149563);
            return true;
         }

         int var23 = var8[var15][var16] + 1;
         if (var15 > 0 && var9[var15 - 1][var16] == 0 && !var4.method5358(var21 - 1, var22, 1076101384, (byte)67)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16] = 2;
            var8[var15 - 1][var16] = var23;
         }

         if (var15 < var6 - 1 && var9[1 + var15][var16] == 0 && !var4.method5358(1 + var21, var22, 1076101504, (byte)-9)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16] = 8;
            var8[var15 + 1][var16] = var23;
         }

         if (var16 > 0 && var9[var15][var16 - 1] == 0 && !var4.method5358(var21, var22 - 1, 1076101378, (byte)8)) {
            var10[var19] = var13;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15][var16 - 1] = 1;
            var8[var15][var16 - 1] = var23;
         }

         if (var16 < var7 - 1 && 0 == var9[var15][var16 + 1] && !var4.method5358(var21, var22 + 1, 1076101408, (byte)-3)) {
            var10[var19] = var13;
            var11[var19] = var14 + 1;
            var19 = 1 + var19 & var12;
            var9[var15][1 + var16] = 4;
            var8[var15][var16 + 1] = var23;
         }

         if (var15 > 0
            && var16 > 0
            && 0 == var9[var15 - 1][var16 - 1]
            && !var4.method5358(var21 - 1, var22 - 1, 1076101390, (byte)75)
            && !var4.method5358(var21 - 1, var22, 1076101384, (byte)-24)
            && !var4.method5358(var21, var22 - 1, 1076101378, (byte)-15)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16 - 1] = 3;
            var8[var15 - 1][var16 - 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 > 0
            && 0 == var9[var15 + 1][var16 - 1]
            && !var4.method5358(var21 + 1, var22 - 1, 1076101507, (byte)100)
            && !var4.method5358(var21 + 1, var22, 1076101504, (byte)-76)
            && !var4.method5358(var21, var22 - 1, 1076101378, (byte)-43)) {
            var10[var19] = 1 + var13;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16 - 1] = 9;
            var8[1 + var15][var16 - 1] = var23;
         }

         if (var15 > 0
            && var16 < var7 - 1
            && var9[var15 - 1][1 + var16] == 0
            && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)-29)
            && !var4.method5358(var21 - 1, var22, 1076101384, (byte)-81)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)87)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 + 1;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 + 1] = 6;
            var8[var15 - 1][var16 + 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 < var7 - 1
            && 0 == var9[var15 + 1][var16 + 1]
            && !var4.method5358(var21 + 1, var22 + 1, 1076101600, (byte)-76)
            && !var4.method5358(var21 + 1, var22, 1076101504, (byte)-19)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)77)) {
            var10[var19] = var13 + 1;
            var11[var19] = 1 + var14;
            var19 = var19 + 1 & var12;
            var9[var15 + 1][1 + var16] = 12;
            var8[1 + var15][1 + var16] = var23;
         }
      }

      var5.method5547(var13, var14, -1139149563);
      return false;
   }

   @ObfuscatedSignature(descriptor = "(JII)V")
   @ObfuscatedName("aj")
   static final void method5479(long var0, int var2, int var3) {
      try {
         if (classFO.field2345[613116963 * classFO.field2348] != Integer.MAX_VALUE) {
            if (var3 >= 35972318) {
               throw new IllegalStateException();
            }

            if (var0 != classFO.field2349[613116963 * classFO.field2348]) {
               if (var3 >= 35972318) {
                  return;
               }

               classFO.field2348 += 166110091;
               classFO.field2345[classFO.field2348 * 613116963] = Integer.MAX_VALUE;
            }
         }

         classFO.field2349[classFO.field2348 * 613116963] = var0;
         classFO.field2345[classFO.field2348 * 613116963] = Math.min(classFO.field2345[613116963 * classFO.field2348], var2);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gi.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("aw")
   final boolean method5488(int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      int var6 = var5.method5554(-1569427834);
      int var7 = var5.method5561(166319673);
      int[][] var8 = classGN.method5566(var5, (short)668);
      int[][] var9 = var5.method5563(-752783302);
      int[] var10 = var5.method5570(1117883003);
      int[] var11 = var5.method5572(-1787708197);
      int var12 = var5.method5575((byte)-85);
      int var13 = var1;
      int var14 = var2;
      int var15 = var6 >> 1;
      int var16 = var7 >> 1;
      int var17 = var1 - var15;
      int var18 = var2 - var16;
      var9[var15][var16] = -1808871043;
      var8[var15][var16] = 0;
      int var19 = 0;
      int var20 = 0;
      var10[var19] = var1;
      var11[var19++] = var2;

      while (var19 != var20) {
         var13 = var10[var20];
         var14 = var11[var20];
         var20 = 1 + var20 & var12;
         var15 = var13 - var17;
         var16 = var14 - var18;
         int var21 = var13 - var4.method5335(-1702279186);
         int var22 = var14 - var4.method5339((byte)51);
         if (var3.vmethod144(2, var13, var14, var4, (byte)1)) {
            var5.method5547(var13, var14, -1139149563);
            return true;
         }

         int var23 = 1 + var8[var15][var16];
         if (var15 > 0
            && 0 == var9[var15 - 1][var16]
            && !var4.method5358(var21 - 1, var22, 448485783, (byte)-32)
            && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)14)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16] = 2;
            var8[var15 - 1][var16] = var23;
         }

         if (var15 < var6 - 2
            && var9[var15 + 1][var16] == 0
            && !var4.method5358(2 + var21, var22, 1076101507, (byte)-47)
            && !var4.method5358(var21 + 2, 1 + var22, 1076101600, (byte)26)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 + 1][var16] = 8;
            var8[1 + var15][var16] = var23;
         }

         if (var16 > 0
            && 0 == var9[var15][var16 - 1]
            && !var4.method5358(var21, var22 - 1, 1076101390, (byte)4)
            && !var4.method5358(var21 + 1, var22 - 1, -302592277, (byte)16)) {
            var10[var19] = var13;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[var15][var16 - 1] = 1;
            var8[var15][var16 - 1] = var23;
         }

         if (var16 < var7 - 2
            && var9[var15][var16 + 1] == 0
            && !var4.method5358(var21, 2 + var22, 1076101432, (byte)-7)
            && !var4.method5358(var21 + 1, var22 + 2, -1947793311, (byte)-37)) {
            var10[var19] = var13;
            var11[var19] = var14 + 1;
            var19 = var19 + 1 & var12;
            var9[var15][1 + var16] = 4;
            var8[var15][var16 + 1] = var23;
         }

         if (var15 > 0
            && var16 > 0
            && 0 == var9[var15 - 1][var16 - 1]
            && !var4.method5358(var21 - 1, var22, 1076101438, (byte)-19)
            && !var4.method5358(var21 - 1, var22 - 1, 1455082892, (byte)-27)
            && !var4.method5358(var21, var22 - 1, 1076101519, (byte)77)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 - 1] = 3;
            var8[var15 - 1][var16 - 1] = var23;
         }

         if (var15 < var6 - 2
            && var16 > 0
            && 0 == var9[var15 + 1][var16 - 1]
            && !var4.method5358(var21 + 1, var22 - 1, 291565100, (byte)64)
            && !var4.method5358(2 + var21, var22 - 1, 1076101507, (byte)16)
            && !var4.method5358(var21 + 2, var22, -1461628463, (byte)-11)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15 + 1][var16 - 1] = 9;
            var8[var15 + 1][var16 - 1] = var23;
         }

         if (var15 > 0
            && var16 < var7 - 2
            && 0 == var9[var15 - 1][var16 + 1]
            && !var4.method5358(var21 - 1, var22 + 1, 1974262849, (byte)-78)
            && !var4.method5358(var21 - 1, var22 + 2, -975812280, (byte)-21)
            && !var4.method5358(var21, 2 + var22, -70233356, (byte)27)) {
            var10[var19] = var13 - 1;
            var11[var19] = 1 + var14;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 + 1] = 6;
            var8[var15 - 1][var16 + 1] = var23;
         }

         if (var15 < var6 - 2
            && var16 < var7 - 2
            && var9[1 + var15][var16 + 1] == 0
            && !var4.method5358(var21 + 1, 2 + var22, 1886952065, (byte)70)
            && !var4.method5358(2 + var21, var22 + 2, 1076101600, (byte)-71)
            && !var4.method5358(2 + var21, 1 + var22, 1076101603, (byte)2)) {
            var10[var19] = 1 + var13;
            var11[var19] = var14 + 1;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16 + 1] = 12;
            var8[1 + var15][1 + var16] = var23;
         }
      }

      var5.method5547(var13, var14, -1139149563);
      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;IILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("kk")
   public static boolean method5485(classGI var0, int var1, int var2, classGG var3, CollisionData var4, classGN var5) {
      if (var0 == null) {
         var0.method5467(var1);
      }

      int var6 = var5.method5554(323652004);
      int var7 = var5.method5561(1862883987);
      int[][] var8 = classGN.method5566(var5, (short)5186);
      int[][] var9 = var5.method5563(-262252100);
      int[] var10 = var5.method5570(1117883003);
      int[] var11 = var5.method5572(-1787708197);
      int var12 = var5.method5575((byte)26);
      int var13 = var1;
      int var14 = var2;
      int var15 = var6 >> 1;
      int var16 = var7 >> 1;
      int var17 = var1 - var15;
      int var18 = var2 - var16;
      var9[var15][var16] = 99;
      var8[var15][var16] = 0;
      int var19 = 0;
      int var20 = 0;
      var10[var19] = var1;
      var11[var19++] = var2;

      while (var20 != var19) {
         var13 = var10[var20];
         var14 = var11[var20];
         var20 = var20 + 1 & var12;
         var15 = var13 - var17;
         var16 = var14 - var18;
         int var21 = var13 - var4.method5335(-1702279186);
         int var22 = var14 - var4.method5339((byte)35);
         if (var3.vmethod144(1, var13, var14, var4, (byte)1)) {
            var5.method5547(var13, var14, -1139149563);
            return true;
         }

         int var23 = var8[var15][var16] + 1;
         if (var15 > 0 && var9[var15 - 1][var16] == 0 && !var4.method5358(var21 - 1, var22, 1076101384, (byte)-42)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16] = 2;
            var8[var15 - 1][var16] = var23;
         }

         if (var15 < var6 - 1 && var9[1 + var15][var16] == 0 && !var4.method5358(1 + var21, var22, 1076101504, (byte)2)) {
            var10[var19] = var13 + 1;
            var11[var19] = var14;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16] = 8;
            var8[var15 + 1][var16] = var23;
         }

         if (var16 > 0 && var9[var15][var16 - 1] == 0 && !var4.method5358(var21, var22 - 1, 1076101378, (byte)-17)) {
            var10[var19] = var13;
            var11[var19] = var14 - 1;
            var19 = var19 + 1 & var12;
            var9[var15][var16 - 1] = 1;
            var8[var15][var16 - 1] = var23;
         }

         if (var16 < var7 - 1 && 0 == var9[var15][var16 + 1] && !var4.method5358(var21, var22 + 1, 1076101408, (byte)5)) {
            var10[var19] = var13;
            var11[var19] = var14 + 1;
            var19 = 1 + var19 & var12;
            var9[var15][1 + var16] = 4;
            var8[var15][var16 + 1] = var23;
         }

         if (var15 > 0
            && var16 > 0
            && 0 == var9[var15 - 1][var16 - 1]
            && !var4.method5358(var21 - 1, var22 - 1, 1076101390, (byte)-22)
            && !var4.method5358(var21 - 1, var22, 1076101384, (byte)5)
            && !var4.method5358(var21, var22 - 1, 1076101378, (byte)-46)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[var15 - 1][var16 - 1] = 3;
            var8[var15 - 1][var16 - 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 > 0
            && 0 == var9[var15 + 1][var16 - 1]
            && !var4.method5358(var21 + 1, var22 - 1, 1076101507, (byte)-78)
            && !var4.method5358(var21 + 1, var22, 1076101504, (byte)-84)
            && !var4.method5358(var21, var22 - 1, 1076101378, (byte)19)) {
            var10[var19] = 1 + var13;
            var11[var19] = var14 - 1;
            var19 = 1 + var19 & var12;
            var9[1 + var15][var16 - 1] = 9;
            var8[1 + var15][var16 - 1] = var23;
         }

         if (var15 > 0
            && var16 < var7 - 1
            && var9[var15 - 1][1 + var16] == 0
            && !var4.method5358(var21 - 1, var22 + 1, 1076101432, (byte)72)
            && !var4.method5358(var21 - 1, var22, 1076101384, (byte)63)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)-71)) {
            var10[var19] = var13 - 1;
            var11[var19] = var14 + 1;
            var19 = var19 + 1 & var12;
            var9[var15 - 1][var16 + 1] = 6;
            var8[var15 - 1][var16 + 1] = var23;
         }

         if (var15 < var6 - 1
            && var16 < var7 - 1
            && 0 == var9[var15 + 1][var16 + 1]
            && !var4.method5358(var21 + 1, var22 + 1, 1076101600, (byte)40)
            && !var4.method5358(var21 + 1, var22, 1076101504, (byte)42)
            && !var4.method5358(var21, 1 + var22, 1076101408, (byte)28)) {
            var10[var19] = var13 + 1;
            var11[var19] = 1 + var14;
            var19 = var19 + 1 & var12;
            var9[var15 + 1][1 + var16] = 12;
            var8[1 + var15][1 + var16] = var23;
         }
      }

      var5.method5547(var13, var14, -1139149563);
      return false;
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Lgn;)Z")
   @ObfuscatedName("ak")
   final boolean method5491(int var1, int var2, int var3, classGG var4, CollisionData var5, classGN var6) {
      int var7 = var6.method5554(1480698736);
      int var8 = var6.method5561(-564000950);
      int[][] var9 = classGN.method5566(var6, (short)-493);
      int[][] var10 = var6.method5563(489846789);
      int[] var11 = var6.method5570(1117883003);
      int[] var12 = var6.method5572(-1787708197);
      int var13 = var6.method5575((byte)1);
      int var14 = var1;
      int var15 = var2;
      int var16 = var7 >> 1;
      int var17 = var8 >> 1;
      int var18 = var1 - var16;
      int var19 = var2 - var17;
      var10[var16][var17] = 99;
      var9[var16][var17] = 0;
      int var20 = 0;
      int var21 = 0;
      var11[var20] = var1;
      var12[var20++] = var2;

      label336:
      while (var21 != var20) {
         var14 = var11[var21];
         var15 = var12[var21];
         var21 = var21 + 1 & var13;
         var16 = var14 - var18;
         var17 = var15 - var19;
         int var22 = var14 - var5.method5335(-1702279186);
         int var23 = var15 - var5.method5339((byte)53);
         if (var4.vmethod144(var3, var14, var15, var5, (byte)1)) {
            var6.method5547(var14, var15, -1139149563);
            return true;
         }

         int var24 = var9[var16][var17] + 1;
         if (var16 > 0
            && var10[var16 - 1][var17] == 0
            && !var5.method5358(var22 - 1, var23, 1076101390, (byte)-1)
            && !var5.method5358(var22 - 1, var23 + var3 - 1, 1399160126, (byte)-16)) {
            int var25 = 1;

            while (true) {
               if (var25 >= var3 - 1) {
                  var11[var20] = var14 - 1;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 - 1][var17] = 2;
                  var9[var16 - 1][var17] = var24;
                  break;
               }

               if (var5.method5358(var22 - 1, var23 + var25, -1599503926, (byte)31)) {
                  break;
               }

               var25++;
            }
         }

         if (var16 < var7 - var3
            && 0 == var10[var16 + 1][var17]
            && !var5.method5358(var3 + var22, var23, -916317921, (byte)33)
            && !var5.method5358(var22 + var3, var23 + var3 - 1, 755589335, (byte)9)) {
            int var29 = 1;

            while (true) {
               if (var29 >= var3 - 1) {
                  var11[var20] = 1 + var14;
                  var12[var20] = var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 + 1][var17] = 8;
                  var9[1 + var16][var17] = var24;
                  break;
               }

               if (var5.method5358(var3 + var22, var23 + var29, -499935985, (byte)25)) {
                  break;
               }

               var29++;
            }
         }

         if (var17 > 0
            && 0 == var10[var16][var17 - 1]
            && !var5.method5358(var22, var23 - 1, 1076101390, (byte)52)
            && !var5.method5358(var3 + var22 - 1, var23 - 1, 131201384, (byte)-78)) {
            int var30 = 1;

            while (true) {
               if (var30 >= var3 - 1) {
                  var11[var20] = var14;
                  var12[var20] = var15 - 1;
                  var20 = 1 + var20 & var13;
                  var10[var16][var17 - 1] = 1;
                  var9[var16][var17 - 1] = var24;
                  break;
               }

               if (var5.method5358(var22 + var30, var23 - 1, 595005594, (byte)-58)) {
                  break;
               }

               var30++;
            }
         }

         if (var17 < var8 - var3
            && var10[var16][1 + var17] == 0
            && !var5.method5358(var22, var23 + var3, 612428237, (byte)67)
            && !var5.method5358(var22 + var3 - 1, var3 + var23, 1076101600, (byte)13)) {
            int var31 = 1;

            while (true) {
               if (var31 >= var3 - 1) {
                  var11[var20] = var14;
                  var12[var20] = var15 + 1;
                  var20 = var20 + 1 & var13;
                  var10[var16][1 + var17] = 4;
                  var9[var16][1 + var17] = var24;
                  break;
               }

               if (var5.method5358(var31 + var22, var23 + var3, 1148975001, (byte)-23)) {
                  break;
               }

               var31++;
            }
         }

         if (var16 > 0 && var17 > 0 && var10[var16 - 1][var17 - 1] == 0 && !var5.method5358(var22 - 1, var23 - 1, 1076101390, (byte)-70)) {
            int var32 = 1;

            while (true) {
               if (var32 >= var3) {
                  var11[var20] = var14 - 1;
                  var12[var20] = var15 - 1;
                  var20 = var20 + 1 & var13;
                  var10[var16 - 1][var17 - 1] = 3;
                  var9[var16 - 1][var17 - 1] = var24;
                  break;
               }

               if (var5.method5358(var22 - 1, var23 - 1 + var32, 1076101438, (byte)48) || var5.method5358(var22 - 1 + var32, var23 - 1, 1076101519, (byte)26)) {
                  break;
               }

               var32++;
            }
         }

         if (var16 < var7 - var3 && var17 > 0 && 0 == var10[1 + var16][var17 - 1] && !var5.method5358(var3 + var22, var23 - 1, 523688900, (byte)33)) {
            int var33 = 1;

            while (true) {
               if (var33 >= var3) {
                  var11[var20] = var14 + 1;
                  var12[var20] = var15 - 1;
                  var20 = 1 + var20 & var13;
                  var10[var16 + 1][var17 - 1] = 9;
                  var9[var16 + 1][var17 - 1] = var24;
                  break;
               }

               if (var5.method5358(var3 + var22, var33 + (var23 - 1), -1981056189, (byte)41)
                  || var5.method5358(var22 + var33, var23 - 1, 1076101519, (byte)-77)) {
                  break;
               }

               var33++;
            }
         }

         if (var16 > 0 && var17 < var8 - var3 && 0 == var10[var16 - 1][1 + var17] && !var5.method5358(var22 - 1, var3 + var23, -1780441944, (byte)-85)) {
            int var34 = 1;

            while (true) {
               if (var34 >= var3) {
                  var11[var20] = var14 - 1;
                  var12[var20] = 1 + var15;
                  var20 = 1 + var20 & var13;
                  var10[var16 - 1][var17 + 1] = 6;
                  var9[var16 - 1][var17 + 1] = var24;
                  break;
               }

               if (var5.method5358(var22 - 1, var34 + var23, -1118613910, (byte)-27) || var5.method5358(var22 - 1 + var34, var3 + var23, -525131234, (byte)-24)
                  )
                {
                  break;
               }

               var34++;
            }
         }

         if (var16 < var7 - var3
            && var17 < var8 - var3
            && var10[var16 + 1][var17 + 1] == 0
            && !var5.method5358(var3 + var22, var3 + var23, 1076101600, (byte)19)) {
            for (int var35 = 1; var35 < var3; var35++) {
               if (var5.method5358(var22 + var35, var3 + var23, 1076101624, (byte)13) || var5.method5358(var3 + var22, var35 + var23, 1076101603, (byte)15)) {
                  continue label336;
               }
            }

            var11[var20] = var14 + 1;
            var12[var20] = 1 + var15;
            var20 = var20 + 1 & var13;
            var10[1 + var16][var17 + 1] = 12;
            var9[1 + var16][var17 + 1] = var24;
         }
      }

      var6.method5547(var14, var15, -1139149563);
      return false;
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Z[I[I)I")
   @ObfuscatedName("al")
   public int method5472(int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8) {
      return method5475(this, var1, var2, var3, var4, var5, var6, var7, var8, this.field2592[0], -695297447);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbt;)I")
   @ObfuscatedName("qk")
   public static int method5477(classBT var0) {
      int var1 = var0.field572[-1863749917 * var0.field574] >> var0.field573 * -573840275 & 1;
      var0.field573 += 75744613;
      var0.field574 = var0.field574 + (-573840275 * var0.field573 >> 3) * -57588021;
      var0.field573 = 75744613 * (var0.field573 * -573840275 & 7);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aw")
   static final void method5492(byte var0) {
      try {
         client.field796.method3381((short)6575);
         classPM.method9063((short)-13690);
         client.field815.method1615(1860880868);
         Deque2.method7942(client.field818);
         classYE.field6975.method13623();
         client.field838.method3058((byte)11);
         client.field909.method4605((byte)16);
         System.gc();
         classBK.method1122(0, 0, (byte)72);
         Actor.method3134(-1612757241);
         client.field990 = false;
         Iterator var1 = client.field815.iterator();

         while (var1.hasNext()) {
            if (var0 == 4) {
               return;
            }

            WorldView var2 = (WorldView)var1.next();
            WorldView.method3759(var2, (byte)69);
         }

         client.field858.method10346(true, -1702106681);
         classJB.method6386(10, -1192966987);
         client.field893 = 0;
         byte var10000 = -1;
         client.method2383();
         classHS.method6136(-155524628).method7753(2079317156);
         classHS.method6136(-400277045).method7754((byte)16);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gi.aw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgi;IIILgg;Lgc;Z[I[ILgn;)I")
   @ObfuscatedName("eo")
   public static int method5474(
      classGI var0, int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8, classGN var9
   ) {
      if (var0 == null) {
         return var0.method5472(var1, var1, var1, var4, var5, var6, var7, var7);
      } else {
         var9.method5543((byte)-102);
         int var10 = var9.method5554(917295827);
         int var11 = var9.method5561(-444732612);
         int[][] var12 = classGN.method5566(var9, (short)-1750);
         int[][] var13 = var9.method5563(-1335931334);
         int[] var14 = var9.method5570(1117883003);
         int[] var15 = var9.method5572(-1787708197);
         boolean var16;
         if (var3 == 1) {
            var16 = var0.method5480(var1, var2, var4, var5, var9, (byte)0);
         } else if (2 == var3) {
            var16 = var0.method5486(var1, var2, var4, var5, var9, -335263048);
         } else {
            var16 = var0.method5489(var1, var2, var3, var4, var5, var9, -1924466229);
         }

         int var17 = var1 - (var10 >> 1);
         int var18 = var2 - (var11 >> 1);
         int var19 = var9.method5550((short)9168);
         int var20 = var9.method5551(-1747932813);
         if (!var16) {
            if (!var6) {
               return -1;
            }

            int var21 = Integer.MAX_VALUE;
            int var22 = Integer.MAX_VALUE;
            byte var23 = 10;
            int var24 = -1977362375 * var4.field2573;
            int var25 = var4.field2571 * -1069935115;
            int var26 = -1728487744 * var4.field2570;
            int var27 = -14456739 * var4.field2569;

            for (int var28 = var24 - var23; var28 <= var24 + var23; var28++) {
               for (int var29 = var25 - var23; var29 <= var25 + var23; var29++) {
                  int var30 = var28 - var17;
                  int var31 = var29 - var18;
                  if (var30 >= 0 && var31 >= 0 && var30 < var10 && var31 < var11 && var12[var30][var31] < -1892212530) {
                     int var32 = 0;
                     if (var28 < var24) {
                        var32 = var24 - var28;
                     } else if (var28 > var24 + var26 - 1) {
                        var32 = var28 - (var26 + var24 - 1);
                     }

                     int var33 = 0;
                     if (var29 < var25) {
                        var33 = var25 - var29;
                     } else if (var29 > var27 + var25 - 1) {
                        var33 = var29 - (var27 + var25 - 1);
                     }

                     int var34 = var33 * var33 + var32 * var32;
                     if (var34 < var21 || var21 == var34 && var12[var30][var31] < var22) {
                        var21 = var34;
                        var22 = var12[var30][var31];
                        var19 = var28;
                        var20 = var29;
                     }
                  }
               }
            }

            if (var21 == Integer.MAX_VALUE) {
               return -1;
            }
         }

         if (var19 == var1 && var20 == var2) {
            var7[0] = var19;
            var8[0] = var20;
            return 0;
         } else {
            int var35 = 0;
            var14[var35] = var19;
            var15[var35++] = var20;

            int var38;
            for (int var37 = var38 = var13[var19 - var17][var20 - var18]; var1 != var19 || var2 != var20; var37 = var13[var19 - var17][var20 - var18]) {
               if (var38 != var37) {
                  var38 = var37;
                  var14[var35] = var19;
                  var15[var35++] = var20;
               }

               if ((var37 & 2) != 0) {
                  var19++;
               } else if ((var37 & 8) != 0) {
                  var19--;
               }

               if (0 != (var37 & 1)) {
                  var20++;
               } else if (0 != (var37 & 4)) {
                  var20--;
               }
            }

            int var39 = 0;

            while (var35-- > 0) {
               var7[var39] = var14[var35];
               var8[var39++] = var15[var35];
               if (var39 >= var7.length) {
                  break;
               }
            }

            return var39;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgi;IIILgg;Lgc;Z[I[ILgn;I)I")
   @ObfuscatedName("hu")
   public static int method5475(
      classGI var0, int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8, classGN var9, int var10
   ) {
      if (var0 == null) {
         return var0.method5476(var1, var1, var1, var4, var5, var6, var7, var7, var9, var1);
      } else {
         try {
            var9.method5543((byte)-103);
            int var11 = var9.method5554(-922299261);
            int var12 = var9.method5561(-824425354);
            int[][] var13 = classGN.method5566(var9, (short)2062);
            int[][] var14 = var9.method5563(-935648781);
            int[] var15 = var9.method5570(1117883003);
            int[] var16 = var9.method5572(-1787708197);
            boolean var17;
            if (var3 == 1) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               var17 = var0.method5480(var1, var2, var4, var5, var9, (byte)0);
            } else if (2 == var3) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               var17 = var0.method5486(var1, var2, var4, var5, var9, 812684373);
            } else {
               var17 = var0.method5489(var1, var2, var3, var4, var5, var9, 1628601465);
            }

            int var18 = var1 - (var11 >> 1);
            int var19 = var2 - (var12 >> 1);
            int var20 = var9.method5550((short)4444);
            int var21 = var9.method5551(1535365819);
            if (!var17) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               if (!var6) {
                  return -1;
               }

               int var22 = Integer.MAX_VALUE;
               int var23 = Integer.MAX_VALUE;
               byte var24 = 10;
               int var25 = -1977362375 * var4.field2573;
               int var26 = var4.field2571 * -1069935115;
               int var27 = 611191209 * var4.field2570;
               int var28 = -14456739 * var4.field2569;

               for (int var29 = var25 - var24; var29 <= var25 + var24; var29++) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  for (int var30 = var26 - var24; var30 <= var26 + var24; var30++) {
                     if (var10 != -695297447) {
                        throw new IllegalStateException();
                     }

                     int var31 = var29 - var18;
                     int var32 = var30 - var19;
                     if (var31 >= 0) {
                        if (var10 != -695297447) {
                           throw new IllegalStateException();
                        }

                        if (var32 >= 0) {
                           if (var10 != -695297447) {
                              throw new IllegalStateException();
                           }

                           if (var31 < var11) {
                              if (var10 != -695297447) {
                                 throw new IllegalStateException();
                              }

                              if (var32 < var12) {
                                 if (var10 != -695297447) {
                                    throw new IllegalStateException();
                                 }

                                 if (var13[var31][var32] < 100) {
                                    int var33 = 0;
                                    if (var29 < var25) {
                                       if (var10 != -695297447) {
                                          throw new IllegalStateException();
                                       }

                                       var33 = var25 - var29;
                                    } else if (var29 > var25 + var27 - 1) {
                                       if (var10 != -695297447) {
                                          throw new IllegalStateException();
                                       }

                                       var33 = var29 - (var27 + var25 - 1);
                                    }

                                    int var34 = 0;
                                    if (var30 < var26) {
                                       if (var10 != -695297447) {
                                          throw new IllegalStateException();
                                       }

                                       var34 = var26 - var30;
                                    } else if (var30 > var28 + var26 - 1) {
                                       var34 = var30 - (var28 + var26 - 1);
                                    }

                                    int var35 = var34 * var34 + var33 * var33;
                                    if (var35 >= var22) {
                                       if (var10 != -695297447) {
                                          throw new IllegalStateException();
                                       }

                                       if (var22 != var35) {
                                          continue;
                                       }

                                       if (var10 != -695297447) {
                                          throw new IllegalStateException();
                                       }

                                       if (var13[var31][var32] >= var23) {
                                          continue;
                                       }

                                       if (var10 != -695297447) {
                                          throw new IllegalStateException();
                                       }
                                    }

                                    var22 = var35;
                                    var23 = var13[var31][var32];
                                    var20 = var29;
                                    var21 = var30;
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               if (var22 == Integer.MAX_VALUE) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  return -1;
               }
            }

            if (var20 == var1) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               if (var21 == var2) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  var7[0] = var20;
                  var8[0] = var21;
                  return 0;
               }
            }

            int var37 = 0;
            var15[var37] = var20;
            var16[var37++] = var21;
            int var40;
            int var39 = var40 = var14[var20 - var18][var21 - var19];

            while (true) {
               if (var1 == var20) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  if (var2 == var21) {
                     int var41 = 0;

                     while (var37-- > 0) {
                        var7[var41] = var15[var37];
                        var8[var41++] = var16[var37];
                        if (var41 >= var7.length) {
                           if (var10 != -695297447) {
                              throw new IllegalStateException();
                           }
                           break;
                        }
                     }

                     return var41;
                  }

                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }
               }

               if (var40 != var39) {
                  var40 = var39;
                  var15[var37] = var20;
                  var16[var37++] = var21;
               }

               if ((var39 & 2) != 0) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  var20++;
               } else if ((var39 & 8) != 0) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  var20--;
               }

               if (0 != (var39 & 1)) {
                  var21++;
               } else if (0 != (var39 & 4)) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  var21--;
               }

               var39 = var14[var20 - var18][var21 - var19];
            }
         } catch (RuntimeException var36) {
            throw classEG.method3899(var36, "gi.ae(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gb")
   static final void method5493(int var0) {
      try {
         classKF.field3569.method3434(ClientPreferences.method2766(classAB.field122, (byte)18), classAB.field122.method2820(386584458), (byte)1);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gi.gb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILgg;Lgc;Z[I[ILgn;I)I")
   @ObfuscatedName("ae")
   int method5476(int var1, int var2, int var3, classGG var4, CollisionData var5, boolean var6, int[] var7, int[] var8, classGN var9, int var10) {
      try {
         var9.method5543((byte)-103);
         int var11 = var9.method5551(-922299261);
         int var12 = var9.method5561(-824425354);
         int[][] var13 = classGN.method5566(var9, (short)2062);
         int[][] var14 = var9.method5563(-935648781);
         int[] var15 = var9.method5570(1117883003);
         int[] var16 = var9.method5570(-1787708197);
         boolean var17;
         if (var3 == 1) {
            if (var10 != -695297447) {
               throw new IllegalStateException();
            }

            var17 = this.method5480(var1, var2, var4, var5, var9, (byte)0);
         } else if (2 == var3) {
            if (var10 != -695297447) {
               throw new IllegalStateException();
            }

            var17 = this.method5486(var1, var2, var4, var5, var9, 812684373);
         } else {
            var17 = this.method5489(var1, var2, var3, var4, var5, var9, 1628601465);
         }

         int var18 = var1 - (var11 >> 1);
         int var19 = var2 - (var12 >> 1);
         int var20 = var9.method5550((short)4444);
         int var21 = var9.method5561(1535365819);
         if (!var17) {
            if (var10 != -695297447) {
               throw new IllegalStateException();
            }

            if (!var6) {
               return -1;
            }

            int var22 = Integer.MAX_VALUE;
            int var23 = Integer.MAX_VALUE;
            byte var24 = 10;
            int var25 = -1977362375 * var4.field2573;
            int var26 = var4.field2573 * -1069935115;
            int var27 = 611191209 * var4.field2571;
            int var28 = -14456739 * var4.field2573;

            for (int var29 = var25 - var24; var29 <= var25 + var24; var29++) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               for (int var30 = var26 - var24; var30 <= var26 + var24; var30++) {
                  if (var10 != -695297447) {
                     throw new IllegalStateException();
                  }

                  int var31 = var29 - var18;
                  int var32 = var30 - var19;
                  if (var31 >= 0) {
                     if (var10 != -695297447) {
                        throw new IllegalStateException();
                     }

                     if (var32 >= 0) {
                        if (var10 != -695297447) {
                           throw new IllegalStateException();
                        }

                        if (var31 < var11) {
                           if (var10 != -695297447) {
                              throw new IllegalStateException();
                           }

                           if (var32 < var12) {
                              if (var10 != -695297447) {
                                 throw new IllegalStateException();
                              }

                              if (var13[var31][var32] < 100) {
                                 int var33 = 0;
                                 if (var29 < var25) {
                                    if (var10 != -695297447) {
                                       throw new IllegalStateException();
                                    }

                                    var33 = var25 - var29;
                                 } else if (var29 > var25 + var27 - 1) {
                                    if (var10 != -695297447) {
                                       throw new IllegalStateException();
                                    }

                                    var33 = var29 - (var27 + var25 - 1);
                                 }

                                 int var34 = 0;
                                 if (var30 < var26) {
                                    if (var10 != -695297447) {
                                       throw new IllegalStateException();
                                    }

                                    var34 = var26 - var30;
                                 } else if (var30 > var28 + var26 - 1) {
                                    var34 = var30 - (var28 + var26 - 1);
                                 }

                                 int var35 = var34 * var34 + var33 * var33;
                                 if (var35 >= var22) {
                                    if (var10 != -695297447) {
                                       throw new IllegalStateException();
                                    }

                                    if (var22 != var35) {
                                       continue;
                                    }

                                    if (var10 != -695297447) {
                                       throw new IllegalStateException();
                                    }

                                    if (var13[var31][var32] >= var23) {
                                       continue;
                                    }

                                    if (var10 != -695297447) {
                                       throw new IllegalStateException();
                                    }
                                 }

                                 var22 = var35;
                                 var23 = var13[var31][var32];
                                 var20 = var29;
                                 var21 = var30;
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (var22 == Integer.MAX_VALUE) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         }

         if (var20 == var1) {
            if (var10 != -695297447) {
               throw new IllegalStateException();
            }

            if (var21 == var2) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               var7[0] = var20;
               var8[0] = var21;
               return 0;
            }
         }

         int var37 = 0;
         var15[var37] = var20;
         var16[var37++] = var21;
         int var40;
         int var39 = var40 = var14[var20 - var18][var21 - var19];

         while (true) {
            if (var1 == var20) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               if (var2 == var21) {
                  int var41 = 0;

                  while (var37-- > 0) {
                     var7[var41] = var15[var37];
                     var8[var41++] = var16[var37];
                     if (var41 >= var7.length) {
                        if (var10 != -695297447) {
                           throw new IllegalStateException();
                        }
                        break;
                     }
                  }

                  return var41;
               }

               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }
            }

            if (var40 != var39) {
               var40 = var39;
               var15[var37] = var20;
               var16[var37++] = var21;
            }

            if ((var39 & 2) != 0) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               var20++;
            } else if ((var39 & 8) != 0) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               var20--;
            }

            if (0 != (var39 & 1)) {
               var21++;
            } else if (0 != (var39 & 4)) {
               if (var10 != -695297447) {
                  throw new IllegalStateException();
               }

               var21--;
            }

            var39 = var14[var20 - var18][var21 - var19];
         }
      } catch (RuntimeException var36) {
         throw classEG.method3899(var36, "gi.ae(" + 41);
      }
   }
}
