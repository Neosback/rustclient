import java.awt.event.WindowEvent;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("st")
public class classST {
   @ObfuscatedName("ab")
   public static final float field6037 = Math.ulp(1.0F);
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final float field6039 = 1.3333334F;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final float field6040 = 0.6666667F;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final float field6041 = 0.33333334F;
   @ObfuscatedName("ag")
   public static final float field6038 = 2.0F * field6037;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field6042 = 256;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;FZ)F")
   @ObfuscatedName("ao")
   static float method10692(classSU var0, float var1, boolean var2) {
      float var3 = 0.0F;
      if (var0 != null && var0.method10735((byte)-21) != 0) {
         float var4 = var0.field6066[0].field6101 * 1210377865;
         float var5 = var0.field6066[var0.method10735((byte)-21) - 1].field6101 * -1881433073;
         float var6 = var5 - var4;
         if (var6 == 0.0F) {
            return var0.field6066[0].field6100;
         } else {
            float var7 = 0.0F;
            if (var1 > var5) {
               var7 = (var1 - var5) / var6;
            } else {
               var7 = (var1 - var4) / var6;
            }

            float var8 = (int)var7;
            float var9 = Math.abs(var7 - var8);
            float var10 = var9 * var6;
            var8 = Math.abs(var8 + 1.0F);
            float var11 = var8 / 2.0F;
            float var12 = (int)var11;
            var9 = var11 - var12;
            if (var2) {
               if (classSE.field5910 == var0.field6061) {
                  if (var9 != 0.0F) {
                     var10 += var4;
                  } else {
                     var10 = var5 - var10;
                  }
               } else if (var0.field6061 != classSE.field5913 && classSE.field5912 != var0.field6061) {
                  if (classSE.field5911 == var0.field6061) {
                     var10 = var4 - var1;
                     float var13 = var0.field6066[0].field6099;
                     float var14 = var0.field6066[0].field6097;
                     var3 = var0.field6066[0].field6100;
                     if (var13 != 0.0F) {
                        var3 -= var14 * var10 / var13;
                     }

                     return var3;
                  }
               } else {
                  var10 = var5 - var10;
               }
            } else if (var0.field6060 == classSE.field5910) {
               if (0.0F != var9) {
                  var10 = var5 - var10;
               } else {
                  var10 += var4;
               }
            } else if (var0.field6060 != classSE.field5913 && var0.field6060 != classSE.field5912) {
               if (classSE.field5911 == var0.field6060) {
                  var10 = var1 - var5;
                  float var24 = var0.field6066[var0.method10735((byte)-77) - 1].field6096;
                  float var25 = var0.field6066[var0.method10735((byte)-8) - 1].field6098;
                  var3 = var0.field6066[var0.method10735((byte)-111) - 1].field6100;
                  if (0.0F != var24) {
                     var3 += var25 * var10 / var24;
                  }

                  return var3;
               }
            } else {
               var10 += var4;
            }

            var3 = classGL.method5519(var0, var10, (byte)127);
            if (var2 && var0.field6061 == classSE.field5912) {
               float var26 = var0.field6066[var0.method10735((byte)-82) - 1].field6100 - var0.field6066[0].field6100;
               var3 -= var26 * var8;
            } else if (!var2 && classSE.field5912 == var0.field6060) {
               float var15 = var0.field6066[var0.method10735((byte)-51) - 1].field6100 - var0.field6066[0].field6100;
               var3 += var8 * var15;
            }

            return var3;
         }
      } else {
         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFFLsu;)V")
   @ObfuscatedName("au")
   static void method10700(float var0, float var1, float var2, float var3, classSU var4) {
      float var5 = var1 - var0;
      float var6 = var2 - var1;
      float var7 = var3 - var2;
      float var8 = var6 - var5;
      var4.field6048 = var7 - var6 - var8;
      var4.field6049 = var8 + var8 + var8;
      var4.field6050 = var5 + (var5 + var5);
      var4.field6045 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("cx")
   public static void method10701(GameEngine var0, WindowEvent var1) {
      if (var0 == null) {
         var0.windowOpened(var1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpr;II)I")
   @ObfuscatedName("tf")
   public static int method10691(StructComposition var0, int var1, int var2) {
      return classGQ.method5635(var0.field5385, var1, var2, -863267617);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;F)F")
   @ObfuscatedName("ac")
   static float method10689(classSU var0, float var1) {
      if (null == var0 || var0.method10735((byte)-126) == 0) {
         return 0.0F;
      } else if (!(var1 < var0.field6066[0].field6101 * 1210377865)) {
         if (var1 > var0.field6066[var0.method10735((byte)-40) - 1].field6101 * 1210377865) {
            return classSE.field5909 == var0.field6060
               ? var0.field6066[var0.method10735((byte)-49) - 1].field6100
               : NpcOverrides.method9136(var0, var1, false, (byte)1);
         } else if (var0.field6062) {
            return var0.field6066[0].field6100;
         } else {
            classSY var2 = var0.method10731(var1, 1391304917);
            boolean var3 = false;
            boolean var4 = false;
            if (null == var2) {
               return 0.0F;
            } else {
               if (0.0F == var2.field6096 && var2.field6098 == 0.0F) {
                  var3 = true;
               } else if (var2.field6096 == Float.MAX_VALUE && Float.MAX_VALUE == var2.field6098) {
                  var4 = true;
               } else if (null != var2.field6102) {
                  if (var0.field6043) {
                     float var5 = var2.field6101 * 1210377865;
                     float var9 = var2.field6100;
                     float var6 = 0.33333334F * var2.field6096 + var5;
                     float var10 = var2.field6098 * 0.33333334F + var9;
                     float var8 = var2.field6102.field6101 * 1210377865;
                     float var12 = var2.field6102.field6100;
                     float var7 = var8 - 0.33333334F * var2.field6102.field6099;
                     float var11 = var12 - 0.33333334F * var2.field6102.field6097;
                     if (var0.field6064) {
                        Projectile.method1225(var0, var5, var6, var7, var8, var9, var10, var11, var12, (byte)-110);
                     } else if (var0 != null) {
                        var0.field6055 = var5;
                        float var13 = var8 - var5;
                        float var14 = var12 - var9;
                        float var15 = var6 - var5;
                        float var16 = 0.0F;
                        float var17 = 0.0F;
                        if (0.0F != var15) {
                           var16 = (var10 - var9) / var15;
                        }

                        var15 = var8 - var7;
                        if (0.0F != var15) {
                           var17 = (var12 - var11) / var15;
                        }

                        float var18 = 1.0F / (var13 * var13);
                        float var19 = var13 * var16;
                        float var20 = var13 * var17;
                        var0.field6045 = var18 * (var19 + var20 - var14 - var14) / var13;
                        var0.field6050 = (var14 + (var14 + var14) - var19 - var19 - var20) * var18;
                        var0.field6049 = var16;
                        var0.field6048 = var9;
                     }

                     var0.field6043 = false;
                  }
               } else {
                  var3 = true;
               }

               if (var3) {
                  return var2.field6100;
               } else if (var4) {
                  return var1 != var2.field6101 * 1210377865 && var2.field6102 != null ? var2.field6102.field6100 : var2.field6100;
               } else if (var0.field6064) {
                  float var22;
                  if (var0 == null) {
                     var22 = 0.0F;
                  } else {
                     float var24;
                     if (var1 == var0.field6055) {
                        var24 = 0.0F;
                     } else if (var1 == var0.field6046) {
                        var24 = 1.0F;
                     } else {
                        var24 = (var1 - var0.field6055) / (var0.field6046 - var0.field6055);
                     }

                     float var25;
                     if (var0.field6063) {
                        var25 = var24;
                     } else {
                        float[] var26 = new float[4];
                        var26[3] = var0.field6048;
                        var26[2] = var0.field6049;
                        var26[1] = var0.field6050;
                        var26[0] = var0.field6045 - var24;
                        float[] var27 = new float[5];
                        int var28 = classEL.method3935(var26, 3, 0.0F, true, 1.0F, true, var27, 894678397);
                        if (var28 == 1) {
                           var25 = var27[0];
                        } else {
                           var25 = 0.0F;
                        }
                     }

                     var22 = var0.field6056 + var25 * (var25 * (var0.field6051 * var25 + var0.field6047) + var0.field6052);
                  }

                  return var22;
               } else {
                  float var21;
                  if (var0 == null) {
                     var21 = 0.0F;
                  } else {
                     float var23 = var1 - var0.field6055;
                     var21 = var0.field6048 + (var0.field6049 + var23 * (var0.field6045 * var23 + var0.field6050)) * var23;
                  }

                  return var21;
               }
            }
         }
      } else {
         return var0.field6061 == classSE.field5909 ? var0.field6066[0].field6100 : NpcOverrides.method9136(var0, var1, true, (byte)1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;FZ)F")
   @ObfuscatedName("aa")
   static float method10693(classSU var0, float var1, boolean var2) {
      float var3 = 0.0F;
      if (var0 != null && var0.method10735((byte)-23) != 0) {
         float var4 = var0.field6066[0].field6101 * 1210377865;
         float var5 = var0.field6066[var0.method10735((byte)-64) - 1].field6101 * 1210377865;
         float var6 = var5 - var4;
         if (var6 == 0.0F) {
            return var0.field6066[0].field6100;
         } else {
            float var7 = 0.0F;
            if (var1 > var5) {
               var7 = (var1 - var5) / var6;
            } else {
               var7 = (var1 - var4) / var6;
            }

            float var8 = (int)var7;
            float var9 = Math.abs(var7 - var8);
            float var10 = var9 * var6;
            var8 = Math.abs(var8 + 1.0F);
            float var11 = var8 / 2.0F;
            float var12 = (int)var11;
            var9 = var11 - var12;
            if (var2) {
               if (classSE.field5910 == var0.field6061) {
                  if (var9 != 0.0F) {
                     var10 += var4;
                  } else {
                     var10 = var5 - var10;
                  }
               } else if (var0.field6061 != classSE.field5913 && classSE.field5912 != var0.field6061) {
                  if (classSE.field5911 == var0.field6061) {
                     var10 = var4 - var1;
                     float var13 = var0.field6066[0].field6099;
                     float var14 = var0.field6066[0].field6097;
                     var3 = var0.field6066[0].field6100;
                     if (var13 != 0.0F) {
                        var3 -= var14 * var10 / var13;
                     }

                     return var3;
                  }
               } else {
                  var10 = var5 - var10;
               }
            } else if (var0.field6060 == classSE.field5910) {
               if (0.0F != var9) {
                  var10 = var5 - var10;
               } else {
                  var10 += var4;
               }
            } else if (var0.field6060 != classSE.field5913 && var0.field6060 != classSE.field5912) {
               if (classSE.field5911 == var0.field6060) {
                  var10 = var1 - var5;
                  float var24 = var0.field6066[var0.method10735((byte)-88) - 1].field6096;
                  float var25 = var0.field6066[var0.method10735((byte)-76) - 1].field6098;
                  var3 = var0.field6066[var0.method10735((byte)-96) - 1].field6100;
                  if (0.0F != var24) {
                     var3 += var25 * var10 / var24;
                  }

                  return var3;
               }
            } else {
               var10 += var4;
            }

            var3 = classGL.method5519(var0, var10, (byte)127);
            if (var2 && var0.field6061 == classSE.field5912) {
               float var26 = var0.field6066[var0.method10735((byte)-58) - 1].field6100 - var0.field6066[0].field6100;
               var3 -= var26 * var8;
            } else if (!var2 && classSE.field5912 == var0.field6060) {
               float var15 = var0.field6066[var0.method10735((byte)-62) - 1].field6100 - var0.field6066[0].field6100;
               var3 += var8 * var15;
            }

            return var3;
         }
      } else {
         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;FZ)F")
   @ObfuscatedName("al")
   static float method10694(classSU var0, float var1, boolean var2) {
      float var3 = 0.0F;
      if (var0 != null && var0.method10735((byte)-2) != 0) {
         float var4 = var0.field6066[0].field6101 * 1210377865;
         float var5 = var0.field6066[var0.method10735((byte)7) - 1].field6101 * 1210377865;
         float var6 = var5 - var4;
         if (var6 == 0.0F) {
            return var0.field6066[0].field6100;
         } else {
            float var7 = 0.0F;
            if (var1 > var5) {
               var7 = (var1 - var5) / var6;
            } else {
               var7 = (var1 - var4) / var6;
            }

            float var8 = (int)var7;
            float var9 = Math.abs(var7 - var8);
            float var10 = var9 * var6;
            var8 = Math.abs(var8 + 1.0F);
            float var11 = var8 / 2.0F;
            float var12 = (int)var11;
            var9 = var11 - var12;
            if (var2) {
               if (classSE.field5910 == var0.field6061) {
                  if (var9 != 0.0F) {
                     var10 += var4;
                  } else {
                     var10 = var5 - var10;
                  }
               } else if (var0.field6061 != classSE.field5913 && classSE.field5912 != var0.field6061) {
                  if (classSE.field5911 == var0.field6061) {
                     var10 = var4 - var1;
                     float var13 = var0.field6066[0].field6099;
                     float var14 = var0.field6066[0].field6097;
                     var3 = var0.field6066[0].field6100;
                     if (var13 != 0.0F) {
                        var3 -= var14 * var10 / var13;
                     }

                     return var3;
                  }
               } else {
                  var10 = var5 - var10;
               }
            } else if (var0.field6060 == classSE.field5910) {
               if (0.0F != var9) {
                  var10 = var5 - var10;
               } else {
                  var10 += var4;
               }
            } else if (var0.field6060 != classSE.field5913 && var0.field6060 != classSE.field5912) {
               if (classSE.field5911 == var0.field6060) {
                  var10 = var1 - var5;
                  float var24 = var0.field6066[var0.method10735((byte)-85) - 1].field6096;
                  float var25 = var0.field6066[var0.method10735((byte)-118) - 1].field6098;
                  var3 = var0.field6066[var0.method10735((byte)-10) - 1].field6100;
                  if (0.0F != var24) {
                     var3 += var25 * var10 / var24;
                  }

                  return var3;
               }
            } else {
               var10 += var4;
            }

            var3 = classGL.method5519(var0, var10, (byte)127);
            if (var2 && var0.field6061 == classSE.field5912) {
               float var26 = var0.field6066[var0.method10735((byte)-62) - 1].field6100 - var0.field6066[0].field6100;
               var3 -= var26 * var8;
            } else if (!var2 && classSE.field5912 == var0.field6060) {
               float var15 = var0.field6066[var0.method10735((byte)-96) - 1].field6100 - var0.field6066[0].field6100;
               var3 += var8 * var15;
            }

            return var3;
         }
      } else {
         return var3;
      }
   }

   classST() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;FFFFFFFF)V")
   @ObfuscatedName("aj")
   static void method10695(classSU var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (null != var0) {
         float var9 = var4 - var1;
         if (var9 != 0.0F) {
            float var10 = var2 - var1;
            float var11 = var3 - var1;
            float[] var12 = new float[]{var10 / var9, var11 / var9};
            var0.field6063 = var12[0] == 0.33333334F && 0.6666667F == var12[1];
            float var13 = var12[0];
            float var14 = var12[1];
            if (var12[0] < 0.0F) {
               var12[0] = 0.0F;
            }

            if (var12[1] > 1.0F) {
               var12[1] = 1.0F;
            }

            if (var12[0] > 1.0F || var12[1] < -1.0F) {
               classBU.method1248(var12, 290446099);
            }

            if (var13 != var12[0]) {
               var2 = var12[0] * var9 + var1;
               if (0.0F != var13) {
                  var6 = var5 + (var6 - var5) * var12[0] / var13;
               }
            }

            if (var12[1] != var14) {
               var3 = var1 + var12[1] * var9;
               if (1.0F != var14) {
                  var7 = var8 - (1.0F - var12[1]) * (var8 - var7) / (1.0F - var14);
               }
            }

            var0.field6055 = var1;
            var0.field6046 = var4;
            Script.method1153(0.0F, var12[0], var12[1], 1.0F, var0, 2105431674);
            float var15 = var6 - var5;
            float var16 = var7 - var6;
            float var17 = var8 - var7;
            float var18 = var16 - var15;
            var0.field6051 = var17 - var16 - var18;
            var0.field6047 = var18 + (var18 + var18);
            var0.field6052 = var15 + var15 + var15;
            var0.field6056 = var5;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([F)V")
   @ObfuscatedName("ay")
   static void method10696(float[] var0) {
      if (field6037 + var0[0] < 1.3333334F) {
         float var1 = var0[0] - 2.0F;
         float var2 = var0[0] - 1.0F;
         float var3 = (float)Math.sqrt(var1 * var1 - var2 * 4.0F * var2);
         float var4 = (-var1 + var3) * 0.5F;
         if (field6037 + var0[1] > var4) {
            var0[1] = var4 - field6037;
         } else {
            var4 = 0.5F * (-var1 - var3);
            if (var0[1] < field6037 + var4) {
               var0[1] = var4 + field6037;
            }
         }
      } else {
         var0[0] = 1.3333334F - field6037;
         var0[1] = 0.33333334F - field6037;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([F)V")
   @ObfuscatedName("aq")
   static void method10697(float[] var0) {
      var0[1] = 1.0F - var0[1];
      if (var0[0] < 0.0F) {
         var0[0] = 0.0F;
      }

      if (var0[1] < 0.0F) {
         var0[1] = 0.0F;
      }

      if (var0[0] > 1.0F || var0[1] > 1.0F) {
         float var1 = 1.0F + (var0[0] * (var0[0] - 2.0F + var0[1]) + (var0[1] - 2.0F) * var0[1]);
         if (var1 + field6037 > 0.0F) {
            classIF.method6248(var0, -850399243);
         }
      }

      var0[1] = 1.0F - var0[1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([F)V")
   @ObfuscatedName("ad")
   static void method10698(float[] var0) {
      var0[1] = 1.0F - var0[1];
      if (var0[0] < 0.0F) {
         var0[0] = 0.0F;
      }

      if (var0[1] < 0.0F) {
         var0[1] = 0.0F;
      }

      if (var0[0] > 1.0F || var0[1] > 1.0F) {
         float var1 = 1.0F + (var0[0] * (var0[0] - 2.0F + var0[1]) + (var0[1] - 2.0F) * var0[1]);
         if (var1 + field6037 > 0.0F) {
            classIF.method6248(var0, -850399243);
         }
      }

      var0[1] = 1.0F - var0[1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([F)V")
   @ObfuscatedName("ap")
   static void method10699(float[] var0) {
      var0[1] = 1.0F - var0[1];
      if (var0[0] < 0.0F) {
         var0[0] = 0.0F;
      }

      if (var0[1] < 0.0F) {
         var0[1] = 0.0F;
      }

      if (var0[0] > 1.0F || var0[1] > 1.0F) {
         float var1 = 1.0F + (var0[0] * (var0[0] - 2.0F + var0[1]) + (var0[1] - 2.0F) * var0[1]);
         if (var1 + field6037 > 0.0F) {
            classIF.method6248(var0, -850399243);
         }
      }

      var0[1] = 1.0F - var0[1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;F)F")
   @ObfuscatedName("ax")
   static float method10690(classSU var0, float var1) {
      if (null == var0 || var0.method10735((byte)1) == 0) {
         return 0.0F;
      } else if (!(var1 < var0.field6066[0].field6101 * -118462844)) {
         if (var1 > var0.field6066[var0.method10735((byte)-29) - 1].field6101 * -2144139951) {
            return classSE.field5909 == var0.field6060
               ? var0.field6066[var0.method10735((byte)-28) - 1].field6100
               : NpcOverrides.method9136(var0, var1, false, (byte)1);
         } else if (var0.field6062) {
            return var0.field6066[0].field6100;
         } else {
            classSY var2 = var0.method10731(var1, 1391304917);
            boolean var3 = false;
            boolean var4 = false;
            if (null == var2) {
               return 0.0F;
            } else {
               if (0.0F == var2.field6096 && var2.field6098 == 0.0F) {
                  var3 = true;
               } else if (var2.field6096 == Float.MAX_VALUE && Float.MAX_VALUE == var2.field6098) {
                  var4 = true;
               } else if (null != var2.field6102) {
                  if (var0.field6043) {
                     float var5 = var2.field6101 * 1210377865;
                     float var9 = var2.field6100;
                     float var6 = 0.33333334F * var2.field6096 + var5;
                     float var10 = var2.field6098 * 0.33333334F + var9;
                     float var8 = var2.field6102.field6101 * 1210377865;
                     float var12 = var2.field6102.field6100;
                     float var7 = var8 - 0.33333334F * var2.field6102.field6099;
                     float var11 = var12 - 0.33333334F * var2.field6102.field6097;
                     if (var0.field6064) {
                        Projectile.method1225(var0, var5, var6, var7, var8, var9, var10, var11, var12, (byte)-103);
                     } else if (var0 != null) {
                        var0.field6055 = var5;
                        float var13 = var8 - var5;
                        float var14 = var12 - var9;
                        float var15 = var6 - var5;
                        float var16 = 0.0F;
                        float var17 = 0.0F;
                        if (0.0F != var15) {
                           var16 = (var10 - var9) / var15;
                        }

                        var15 = var8 - var7;
                        if (0.0F != var15) {
                           var17 = (var12 - var11) / var15;
                        }

                        float var18 = 1.0F / (var13 * var13);
                        float var19 = var13 * var16;
                        float var20 = var13 * var17;
                        var0.field6045 = var18 * (var19 + var20 - var14 - var14) / var13;
                        var0.field6050 = (var14 + (var14 + var14) - var19 - var19 - var20) * var18;
                        var0.field6049 = var16;
                        var0.field6048 = var9;
                     }

                     var0.field6043 = false;
                  }
               } else {
                  var3 = true;
               }

               if (var3) {
                  return var2.field6100;
               } else if (var4) {
                  return var1 != var2.field6101 * 1210377865 && var2.field6102 != null ? var2.field6102.field6100 : var2.field6100;
               } else if (var0.field6064) {
                  float var22;
                  if (var0 == null) {
                     var22 = 0.0F;
                  } else {
                     float var24;
                     if (var1 == var0.field6055) {
                        var24 = 0.0F;
                     } else if (var1 == var0.field6046) {
                        var24 = 1.0F;
                     } else {
                        var24 = (var1 - var0.field6055) / (var0.field6046 - var0.field6055);
                     }

                     float var25;
                     if (var0.field6063) {
                        var25 = var24;
                     } else {
                        float[] var26 = new float[4];
                        var26[3] = var0.field6048;
                        var26[2] = var0.field6049;
                        var26[1] = var0.field6050;
                        var26[0] = var0.field6045 - var24;
                        float[] var27 = new float[5];
                        int var28 = classEL.method3935(var26, 3, 0.0F, true, 1.0F, true, var27, 653864010);
                        if (var28 == 1) {
                           var25 = var27[0];
                        } else {
                           var25 = 0.0F;
                        }
                     }

                     var22 = var0.field6056 + var25 * (var25 * (var0.field6051 * var25 + var0.field6047) + var0.field6052);
                  }

                  return var22;
               } else {
                  float var21;
                  if (var0 == null) {
                     var21 = 0.0F;
                  } else {
                     float var23 = var1 - var0.field6055;
                     var21 = var0.field6048 + (var0.field6049 + var23 * (var0.field6045 * var23 + var0.field6050)) * var23;
                  }

                  return var21;
               }
            }
         }
      } else {
         return var0.field6061 == classSE.field5909 ? var0.field6066[0].field6100 : NpcOverrides.method9136(var0, var1, true, (byte)1);
      }
   }
}
