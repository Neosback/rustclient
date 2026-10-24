import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hl")
public class classHL {
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2834 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2835 = 24;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2829 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2833 = 11;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2836 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   static final String field2838 = "JX_ACCESS_TOKEN";
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   public static final int field2830 = 13;
   @ToRemove(unused = "true")
   @ObfuscatedName("dy")
   static final int field2837 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2832 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2831 = 1;

   @ObfuscatedSignature(descriptor = "(Ldz;[BIIIIIIIIII)V")
   @ObfuscatedName("ag")
   static final void method6104(
      WorldView var0, byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11
   ) {
      try {
         CollisionData[] var12 = var0.field1685;
         if (var12 != null) {
            if (var11 == -842430615) {
               throw new IllegalStateException();
            }

            if (var2 >= 0) {
               if (var11 == -842430615) {
                  throw new IllegalStateException();
               }

               if (var2 < var12.length) {
                  CollisionData var13 = var12[var2];

                  for (int var14 = var3; var14 < 8 + var3; var14++) {
                     for (int var15 = var4; var15 < 8 + var4; var15++) {
                        if (var13.method5375(var14, var15, (byte)-34)) {
                           if (var11 == -842430615) {
                              throw new IllegalStateException();
                           }

                           var13.method5371(var14, var15, 1073741824, -2134326968);
                        }
                     }
                  }
               }
            }
         }

         classXY var32 = new classXY(var1);

         for (int var33 = 0; var33 < 4; var33++) {
            if (var11 == -842430615) {
               throw new IllegalStateException();
            }

            for (int var35 = 0; var35 < 64; var35++) {
               if (var11 == -842430615) {
                  throw new IllegalStateException();
               }

               for (int var16 = 0; var16 < 64; var16++) {
                  if (var11 == -842430615) {
                     throw new IllegalStateException();
                  }

                  if (var33 == var5) {
                     if (var11 == -842430615) {
                        throw new IllegalStateException();
                     }

                     if (var35 >= var6) {
                        if (var11 == -842430615) {
                           return;
                        }

                        if (var35 < var6 + 8) {
                           if (var11 == -842430615) {
                              throw new IllegalStateException();
                           }

                           if (var16 >= var7) {
                              if (var11 == -842430615) {
                                 throw new IllegalStateException();
                              }

                              if (var16 < 8 + var7) {
                                 if (var11 == -842430615) {
                                    throw new IllegalStateException();
                                 }

                                 int var19 = var35 & 7;
                                 int var20 = var16 & 7;
                                 int var21 = var8 & 3;
                                 int var18;
                                 if (0 == var21) {
                                    var18 = var19;
                                 } else if (1 == var21) {
                                    if (var11 == -842430615) {
                                       throw new IllegalStateException();
                                    }

                                    var18 = var20;
                                 } else if (2 == var21) {
                                    if (var11 == -842430615) {
                                       return;
                                    }

                                    var18 = 7 - var19;
                                 } else {
                                    var18 = 7 - var20;
                                 }

                                 int var22 = var18 + var3;
                                 int var25 = var35 & 7;
                                 int var26 = var16 & 7;
                                 int var27 = var8 & 3;
                                 int var24;
                                 if (var27 == 0) {
                                    if (var11 == -842430615) {
                                       throw new IllegalStateException();
                                    }

                                    var24 = var26;
                                 } else if (1 == var27) {
                                    if (var11 == -842430615) {
                                       return;
                                    }

                                    var24 = 7 - var25;
                                 } else if (2 == var27) {
                                    if (var11 == -842430615) {
                                       throw new IllegalStateException();
                                    }

                                    var24 = 7 - var26;
                                 } else {
                                    var24 = var25;
                                 }

                                 int var28 = var24 + var4;
                                 int var29 = var3 + var9 + (var35 & 7);
                                 int var30 = var4 + var10 + (var16 & 7);
                                 classKG.method6510(var0, var32, var2, var22, var28, var29, var30, var8, 124276289);
                                 continue;
                              }
                           }
                        }
                     }
                  }

                  classKG.method6510(var0, var32, 0, -1, -1, 0, 0, 0, -2084329643);
               }
            }
         }

         int var10000;
         if (var32.field6953 * 702114061 < var32.field6952.length) {
            if (var11 == -842430615) {
               throw new IllegalStateException();
            }

            var10000 = var32.method13095(-346779531);
         } else {
            var10000 = 0;
         }

         int var34 = var10000;
         boolean var38;
         if ((var34 & 1) != 0) {
            if (var11 == -842430615) {
               throw new IllegalStateException();
            }

            var38 = true;
         } else {
            var38 = false;
         }

         boolean var36 = var38;
         if (var36) {
            for (int var37 = 0; var37 < 64; var37++) {
               if (var11 == -842430615) {
                  throw new IllegalStateException();
               }

               for (int var17 = 0; var17 < 64; var17++) {
                  if (var11 == -842430615) {
                     return;
                  }

                  classLF.method6828(var32, 1347820516);
               }
            }
         }
      } catch (RuntimeException var31) {
         throw classEG.method3899(var31, "hl.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcj;F)V")
   @ObfuscatedName("hk")
   public static void method6106(classCJ var0, float var1) {
      if (null != var0.field746) {
         var0.field754 = var0.field759;
         var0.field745 = var0.field752;
         var0.field748 = var0.field753;
         var0.field758 = var1;
         var0.field747 = true;
         var0.field751.field6072 = 55373099 * var0.field746.field6036;
         var0.field751.field6082 = 1435548701 * var0.field746.field6032;
         var0.field751.field6081 = var0.field746.field6031 * 1047513907;
         var0.field751.field6080 = var0.field746.field6030 * -1473560751;
         var0.field751.field6090 = -817499283 * var0.field746.field6033;
         var0.field751.field6084 = var0.field746.field6034 * 4884061;
         var0.field751.field6075 = var0.field746.field6035 * -13412519;
         var0.field746 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnw;)V")
   @ObfuscatedName("ex")
   public static void method6102(classNW var0) {
      var0.field4784 = var0.field4783 != null ? var0.field4783.field4780.field6552 : null;
      var0.field4782 = null;
   }

   classHL() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIII)Z")
   @ObfuscatedName("az")
   static boolean method6103(
      int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12
   ) {
      try {
         if (!classIY.method6380(var0, var1, var2, var3, var8, var9, var10, var11, -2040924807)) {
            if (var12 != -1453501658) {
               throw new IllegalStateException();
            }

            if (!classIY.method6380(var2, var3, var4, var5, var8, var9, var10, var11, -1690056408)
               && !classIY.method6380(var4, var5, var6, var7, var8, var9, var10, var11, -1766381226)) {
               if (var12 != -1453501658) {
                  throw new IllegalStateException();
               }

               if (!classIY.method6380(var6, var7, var0, var1, var8, var9, var10, var11, -1677007233)) {
                  return false;
               }

               if (var12 != -1453501658) {
                  throw new IllegalStateException();
               }
            }
         }

         return true;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "hl.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([I[IIIB)V")
   @ObfuscatedName("ax")
   public static void method6105(int[] var0, int[] var1, int var2, int var3, byte var4) {
      try {
         if (var2 < var3) {
            if (var4 == 0) {
               return;
            }

            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            int var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            int var8 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var8;
            byte var10000;
            if (var7 == Integer.MAX_VALUE) {
               if (var4 == 0) {
                  throw new IllegalStateException();
               }

               var10000 = 0;
            } else {
               var10000 = 1;
            }

            byte var9 = var10000;

            for (int var10 = var2; var10 < var3; var10++) {
               if (var4 == 0) {
                  return;
               }

               if (var0[var10] < (var10 & var9) + var7) {
                  if (var4 == 0) {
                     return;
                  }

                  int var11 = var0[var10];
                  var0[var10] = var0[var6];
                  var0[var6] = var11;
                  int var12 = var1[var10];
                  var1[var10] = var1[var6];
                  var1[var6++] = var12;
               }
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var8;
            method6105(var0, var1, var2, var6 - 1, (byte)51);
            method6105(var0, var1, var6 + 1, var3, (byte)95);
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "hl.ax(" + ')');
      }
   }
}
