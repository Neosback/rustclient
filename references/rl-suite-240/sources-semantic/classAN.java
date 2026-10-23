import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("an")
public class classAN {
   @ObfuscatedName("ag")
   int field241;
   @ObfuscatedName("af")
   int field242;
   @ObfuscatedName("ae")
   int field239;
   @ObfuscatedName("ab")
   int field240;
   @ObfuscatedName("az")
   int field243;
   @ObfuscatedName("as")
   int field244;
   @ObfuscatedName("ax")
   int[] field238;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llz;)Llw;")
   @ObfuscatedName("fg")
   public static Widget method617(classLZ var0) {
      if (var0 == null) {
         var0.method7465();
      }

      int var1 = var0.method7464(-1198889253);
      return var1 >= 0 ? var0.field4449.field4338[var1] : null;
   }

   @ObfuscatedSignature(descriptor = "([FIZLbt;[Lbx;)V")
   @ObfuscatedName("az")
   void method612(float[] var1, int var2, boolean var3, classBT var4, classBX[] var5) {
      for (int var6 = 0; var6 < var2; var6++) {
         var1[var6] = 0.0F;
      }

      if (!var3) {
         int var21 = var5[this.field244].field602;
         int var7 = this.field239 - this.field242;
         int var8 = var7 / this.field240;
         int[] var9 = new int[var8];

         for (int var10 = 0; var10 < 8; var10++) {
            int var11 = 0;

            while (var11 < var8) {
               if (var10 == 0) {
                  int var12 = var5[this.field244].method1353(var4);

                  for (int var13 = var21 - 1; var13 >= 0; var13--) {
                     if (var11 + var13 < var8) {
                        var9[var11 + var13] = var12 % this.field241;
                     }

                     var12 /= this.field241;
                  }
               }

               for (int var22 = 0; var22 < var21; var22++) {
                  int var23 = var9[var11];
                  int var14 = this.field238[var23 * 8 + var10];
                  if (var14 >= 0) {
                     int var15 = this.field242 + var11 * this.field240;
                     classBX var16 = var5[var14];
                     if (this.field243 == 0) {
                        int var24 = this.field240 / var16.field602;

                        for (int var25 = 0; var25 < var24; var25++) {
                           float[] var26 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var20 = 0; var20 < var16.field602; var20++) {
                              var1[var15 + var25 + var20 * var24] = var1[var15 + var25 + var20 * var24] + var26[var20];
                           }
                        }
                     } else {
                        int var17 = 0;

                        while (var17 < this.field240) {
                           float[] var18 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var19 = 0; var19 < var16.field602; var19++) {
                              var1[var15 + var17] = var1[var15 + var17] + var18[var19];
                              var17++;
                           }
                        }
                     }
                  }

                  if (++var11 >= var8) {
                     break;
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lan;[FIZLbt;[Lbx;)V")
   @ObfuscatedName("cm")
   public static void method613(classAN var0, float[] var1, int var2, boolean var3, classBT var4, classBX[] var5) {
      if (var0 == null) {
         var0.method616(var1, var2, var3, var4, var5);
      }

      for (int var6 = 0; var6 < var2; var6++) {
         var1[var6] = 0.0F;
      }

      if (!var3) {
         int var21 = var5[var0.field244].field602;
         int var7 = var0.field239 - var0.field242;
         int var8 = var7 / var0.field240;
         int[] var9 = new int[var8];

         for (int var10 = 0; var10 < 8; var10++) {
            int var11 = 0;

            while (var11 < var8) {
               if (var10 == 0) {
                  int var12 = var5[var0.field244].method1353(var4);

                  for (int var13 = var21 - 1; var13 >= 0; var13--) {
                     if (var11 + var13 < var8) {
                        var9[var11 + var13] = var12 % var0.field241;
                     }

                     var12 /= var0.field241;
                  }
               }

               for (int var22 = 0; var22 < var21; var22++) {
                  int var23 = var9[var11];
                  int var14 = var0.field238[var23 * 8 + var10];
                  if (var14 >= 0) {
                     int var15 = var0.field242 + var11 * var0.field240;
                     classBX var16 = var5[var14];
                     if (var0.field243 == 0) {
                        int var24 = var0.field240 / var16.field602;

                        for (int var25 = 0; var25 < var24; var25++) {
                           float[] var26 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var20 = 0; var20 < var16.field602; var20++) {
                              var1[var15 + var25 + var20 * var24] = var1[var15 + var25 + var20 * var24] + var26[var20];
                           }
                        }
                     } else {
                        int var17 = 0;

                        while (var17 < var0.field240) {
                           float[] var18 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var19 = 0; var19 < var16.field602; var19++) {
                              var1[var15 + var17] = var1[var15 + var17] + var18[var19];
                              var17++;
                           }
                        }
                     }
                  }

                  if (++var11 >= var8) {
                     break;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([FIZLbt;[Lbx;)V")
   @ObfuscatedName("af")
   void method614(float[] var1, int var2, boolean var3, classBT var4, classBX[] var5) {
      for (int var6 = 0; var6 < var2; var6++) {
         var1[var6] = 0.0F;
      }

      if (!var3) {
         int var21 = var5[this.field244].field602;
         int var7 = this.field239 - this.field242;
         int var8 = var7 / this.field240;
         int[] var9 = new int[var8];

         for (int var10 = 0; var10 < 8; var10++) {
            int var11 = 0;

            while (var11 < var8) {
               if (var10 == 0) {
                  int var12 = var5[this.field244].method1353(var4);

                  for (int var13 = var21 - 1; var13 >= 0; var13--) {
                     if (var11 + var13 < var8) {
                        var9[var11 + var13] = var12 % this.field241;
                     }

                     var12 /= this.field241;
                  }
               }

               for (int var22 = 0; var22 < var21; var22++) {
                  int var23 = var9[var11];
                  int var14 = this.field238[var23 * 8 + var10];
                  if (var14 >= 0) {
                     int var15 = this.field242 + var11 * this.field240;
                     classBX var16 = var5[var14];
                     if (this.field243 == 0) {
                        int var24 = this.field240 / var16.field602;

                        for (int var25 = 0; var25 < var24; var25++) {
                           float[] var26 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var20 = 0; var20 < var16.field602; var20++) {
                              var1[var15 + var25 + var20 * var24] = var1[var15 + var25 + var20 * var24] + var26[var20];
                           }
                        }
                     } else {
                        int var17 = 0;

                        while (var17 < this.field240) {
                           float[] var18 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var19 = 0; var19 < var16.field602; var19++) {
                              var1[var15 + var17] = var1[var15 + var17] + var18[var19];
                              var17++;
                           }
                        }
                     }
                  }

                  if (++var11 >= var8) {
                     break;
                  }
               }
            }
         }
      }
   }

   classAN(classBT var1) {
      this.field243 = classBT.method1234(var1, 16, -2062694823);
      this.field242 = classBT.method1234(var1, 24, -1982826681);
      this.field239 = classBT.method1234(var1, 24, -1930873174);
      this.field240 = classBT.method1234(var1, 24, -1971959893) + 1;
      this.field241 = classBT.method1234(var1, 6, -1962648877) + 1;
      this.field244 = classBT.method1234(var1, 8, -1967226719);
      int[] var2 = new int[this.field241];

      for (int var3 = 0; var3 < this.field241; var3++) {
         int var4 = 0;
         int var5 = classBT.method1234(var1, 3, -2054432032);
         boolean var6 = var1.method1241(645430661) != 0;
         if (var6) {
            var4 = classBT.method1234(var1, 5, -2046201344);
         }

         var2[var3] = var4 << 3 | var5;
      }

      this.field238 = new int[this.field241 * 8];

      for (int var7 = 0; var7 < this.field241 * 8; var7++) {
         this.field238[var7] = (var2[var7 >> 3] & 1 << (var7 & 7)) != 0 ? classBT.method1234(var1, 8, -2007295539) : -1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lan;[FIZLbt;[Lbx;)V")
   @ObfuscatedName("bw")
   public static void method615(classAN var0, float[] var1, int var2, boolean var3, classBT var4, classBX[] var5) {
      if (var0 == null) {
         var0.method612(var1, var2, var3, var4, var5);
      }

      for (int var6 = 0; var6 < var2; var6++) {
         var1[var6] = 0.0F;
      }

      if (!var3) {
         int var21 = var5[var0.field244].field602;
         int var7 = var0.field239 - var0.field242;
         int var8 = var7 / var0.field240;
         int[] var9 = new int[var8];

         for (int var10 = 0; var10 < 8; var10++) {
            int var11 = 0;

            while (var11 < var8) {
               if (var10 == 0) {
                  int var12 = var5[var0.field244].method1353(var4);

                  for (int var13 = var21 - 1; var13 >= 0; var13--) {
                     if (var11 + var13 < var8) {
                        var9[var11 + var13] = var12 % var0.field241;
                     }

                     var12 /= var0.field241;
                  }
               }

               for (int var22 = 0; var22 < var21; var22++) {
                  int var23 = var9[var11];
                  int var14 = var0.field238[var23 * 8 + var10];
                  if (var14 >= 0) {
                     int var15 = var0.field242 + var11 * var0.field240;
                     classBX var16 = var5[var14];
                     if (var0.field243 == 0) {
                        int var24 = var0.field240 / var16.field602;

                        for (int var25 = 0; var25 < var24; var25++) {
                           float[] var26 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var20 = 0; var20 < var16.field602; var20++) {
                              var1[var15 + var25 + var20 * var24] = var1[var15 + var25 + var20 * var24] + var26[var20];
                           }
                        }
                     } else {
                        int var17 = 0;

                        while (var17 < var0.field240) {
                           float[] var18 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var19 = 0; var19 < var16.field602; var19++) {
                              var1[var15 + var17] = var1[var15 + var17] + var18[var19];
                              var17++;
                           }
                        }
                     }
                  }

                  if (++var11 >= var8) {
                     break;
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([FIZLbt;[Lbx;)V")
   @ObfuscatedName("ab")
   void method616(float[] var1, int var2, boolean var3, classBT var4, classBX[] var5) {
      for (int var6 = 0; var6 < var2; var6++) {
         var1[var6] = 0.0F;
      }

      if (!var3) {
         int var21 = var5[this.field244].field602;
         int var7 = this.field239 - this.field242;
         int var8 = var7 / this.field240;
         int[] var9 = new int[var8];

         for (int var10 = 0; var10 < 8; var10++) {
            int var11 = 0;

            while (var11 < var8) {
               if (var10 == 0) {
                  int var12 = var5[this.field244].method1353(var4);

                  for (int var13 = var21 - 1; var13 >= 0; var13--) {
                     if (var11 + var13 < var8) {
                        var9[var11 + var13] = var12 % this.field241;
                     }

                     var12 /= this.field241;
                  }
               }

               for (int var22 = 0; var22 < var21; var22++) {
                  int var23 = var9[var11];
                  int var14 = this.field238[var23 * 8 + var10];
                  if (var14 >= 0) {
                     int var15 = this.field242 + var11 * this.field240;
                     classBX var16 = var5[var14];
                     if (this.field243 == 0) {
                        int var24 = this.field240 / var16.field602;

                        for (int var25 = 0; var25 < var24; var25++) {
                           float[] var26 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var20 = 0; var20 < var16.field602; var20++) {
                              var1[var15 + var25 + var20 * var24] = var1[var15 + var25 + var20 * var24] + var26[var20];
                           }
                        }
                     } else {
                        int var17 = 0;

                        while (var17 < this.field240) {
                           float[] var18 = AmbientSoundEffect.method3674(var16, var4);

                           for (int var19 = 0; var19 < var16.field602; var19++) {
                              var1[var15 + var17] = var1[var15 + var17] + var18[var19];
                              var17++;
                           }
                        }
                     }
                  }

                  if (++var11 >= var8) {
                     break;
                  }
               }
            }
         }
      }
   }
}
