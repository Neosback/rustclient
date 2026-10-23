import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fk")
public class classFK {
   @ToRemove(unused = "true")
   @ObfuscatedName("co")
   static final int field2295 = 140;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2294 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("cv")
   static final int field2296 = 252;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final long field2297 = -3932672073523589310L;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   public static final int field2293 = 128;

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIIIIILgc;Lee;Lee;I)V")
   @ObfuscatedName("at")
   static final void method4839(
      WorldView var0,
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      CollisionData var9,
      Renderable var10,
      Renderable var11,
      int var12
   ) {
      try {
         ObjectComposition var13;
         int var14;
         int var15;
         int var16;
         label530: {
            var13 = classMU.method7771(var5, 1762392591);
            var14 = var8 >= 0 ? var8 : var13.field4949 * -1542230501;
            if (var6 != 1) {
               if (var12 == 892962765) {
                  return;
               }

               if (3 != var6) {
                  var15 = var13.field4942 * -582306509;
                  var16 = var13.field4922 * 402876423;
                  break label530;
               }

               if (var12 == 892962765) {
                  throw new IllegalStateException();
               }
            }

            var15 = var13.field4922 * 402876423;
            var16 = -582306509 * var13.field4942;
         }

         int var17;
         int var18;
         if (var3 + var15 <= var0.field1694 * 1296729483) {
            if (var12 == 892962765) {
               throw new IllegalStateException();
            }

            var17 = var3 + (var15 >> 1);
            var18 = var3 + (1 + var15 >> 1);
         } else {
            var17 = var3;
            var18 = 1 + var3;
         }

         int var19;
         int var20;
         if (var4 + var16 <= var0.field1690 * -1269171107) {
            var19 = var4 + (var16 >> 1);
            var20 = var4 + (1 + var16 >> 1);
         } else {
            var19 = var4;
            var20 = var4 + 1;
         }

         int[][] var21 = var0.field1686[var2];
         int var22 = var21[var18][var19] + var21[var17][var19] + var21[var17][var20] + var21[var18][var20] >> 2;
         int var23 = (var15 << 6) + (var3 << 7);
         int var24 = (var16 << 6) + (var4 << 7);
         Scene var25 = var0.field1687;
         boolean var10004;
         if (0 == 1663954033 * var13.field4921) {
            if (var12 == 892962765) {
               throw new IllegalStateException();
            }

            var10004 = true;
         } else {
            var10004 = false;
         }

         long var26 = classRF.method9998(var1, var3, var4, 2, var10004, var5, 2140889407 * var0.field1697, (short)21022);
         int var28 = (var6 << 6) + var7;
         if (1291058669 * var13.field4954 == 1) {
            if (var12 == 892962765) {
               throw new IllegalStateException();
            }

            var28 += 256;
         }

         if (22 == var7) {
            Object var50;
            label411: {
               if (-1 == var14) {
                  if (var12 == 892962765) {
                     throw new IllegalStateException();
                  }

                  if (var13.field4928 == null) {
                     var50 = var13.method8575(22, var6, var21, var23, var22, var24, 1056473218);
                     break label411;
                  }
               }

               var50 = new DynamicObject(var0, var5, 22, var6, var2, var3, var4, var14, var13.field4962, var10);
            }

            var25.method4470(var1, var3, var4, var22, (Renderable)var50, var26, var28);
            if (1 == var13.field4944 * -2040437663) {
               if (var12 == 892962765) {
                  return;
               }

               if (null != var9) {
                  if (var12 == 892962765) {
                     return;
                  }

                  CollisionData.method5400(var9, var3, var4, (byte)4);
               }
            }
         } else {
            if (var7 != 10) {
               if (var12 == 892962765) {
                  throw new IllegalStateException();
               }

               if (var7 != 11) {
                  if (var7 >= 12) {
                     Object var49;
                     if (var14 == -1 && var13.field4928 == null) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        var49 = var13.method8575(var7, var6, var21, var23, var22, var24, 282245732);
                     } else {
                        var49 = new DynamicObject(var0, var5, var7, var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     Scene.method4208(var25, var1, var3, var4, var22, 1, 1, (Renderable)var49, 0, var26, var28);
                     if (0 != -2040437663 * var13.field4944) {
                        if (var12 == 892962765) {
                           return;
                        }

                        if (var9 != null) {
                           if (var12 == 892962765) {
                              return;
                           }

                           var9.method5389(var3, var4, var15, var16, var13.field4945, (byte)28);
                        }
                     }

                     return;
                  }

                  if (0 == var7) {
                     if (var12 == 892962765) {
                        throw new IllegalStateException();
                     }

                     Object var48;
                     label425: {
                        if (-1 == var14) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (var13.field4928 == null) {
                              var48 = var13.method8575(0, var6, var21, var23, var22, var24, -1046390359);
                              break label425;
                           }
                        }

                        var48 = new DynamicObject(var0, var5, 0, var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     var25.method4473(var1, var3, var4, var22, (Renderable)var48, null, classDI.field1503[var6], 0, var26, var28);
                     if (-2040437663 * var13.field4944 != 0 && null != var9) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        CollisionData.method5383(var9, var3, var4, var7, var6, var13.field4945, -2007446649);
                     }

                     return;
                  }

                  if (var7 == 1) {
                     if (var12 == 892962765) {
                        throw new IllegalStateException();
                     }

                     Object var47;
                     label434: {
                        if (-1 == var14) {
                           if (var12 == 892962765) {
                              return;
                           }

                           if (null == var13.field4928) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var47 = var13.method8575(1, var6, var21, var23, var22, var24, 1008285301);
                              break label434;
                           }
                        }

                        var47 = new DynamicObject(var0, var5, 1, var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     var25.method4473(var1, var3, var4, var22, (Renderable)var47, null, classDI.field1511[var6], 0, var26, var28);
                     if (var13.field4944 * -2040437663 != 0) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        if (null != var9) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           CollisionData.method5383(var9, var3, var4, var7, var6, var13.field4945, 1181693377);
                        }
                     }

                     return;
                  }

                  if (2 == var7) {
                     Object var31;
                     int var46;
                     Object var54;
                     label443: {
                        var46 = var6 + 1 & 3;
                        if (-1 == var14) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (null == var13.field4928) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var54 = var13.method8575(2, 4 + var6, var21, var23, var22, var24, -1710523705);
                              var31 = var13.method8575(2, var46, var21, var23, var22, var24, -543004295);
                              break label443;
                           }
                        }

                        var54 = new DynamicObject(var0, var5, 2, var6 + 4, var2, var3, var4, var14, var13.field4962, var10);
                        var31 = new DynamicObject(var0, var5, 2, var46, var2, var3, var4, var14, var13.field4962, var11);
                     }

                     var25.method4473(
                        var1, var3, var4, var22, (Renderable)var54, (Renderable)var31, classDI.field1503[var6], classDI.field1503[var46], var26, var28
                     );
                     if (-2040437663 * var13.field4944 != 0 && var9 != null) {
                        CollisionData.method5383(var9, var3, var4, var7, var6, var13.field4945, -1396123545);
                     }

                     return;
                  }

                  if (3 == var7) {
                     if (var12 == 892962765) {
                        throw new IllegalStateException();
                     }

                     Object var45;
                     label451: {
                        if (var14 == -1) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (var13.field4928 == null) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var45 = var13.method8575(3, var6, var21, var23, var22, var24, -1649434716);
                              break label451;
                           }
                        }

                        var45 = new DynamicObject(var0, var5, 3, var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     var25.method4473(var1, var3, var4, var22, (Renderable)var45, null, classDI.field1511[var6], 0, var26, var28);
                     if (0 != var13.field4944 * -2040437663) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        if (null != var9) {
                           CollisionData.method5383(var9, var3, var4, var7, var6, var13.field4945, 26301346);
                        }
                     }

                     return;
                  }

                  if (var7 == 9) {
                     Object var44;
                     label460: {
                        if (var14 == -1) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (null == var13.field4928) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var44 = var13.method8575(var7, var6, var21, var23, var22, var24, 921259943);
                              break label460;
                           }
                        }

                        var44 = new DynamicObject(var0, var5, var7, var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     Scene.method4208(var25, var1, var3, var4, var22, 1, 1, (Renderable)var44, 0, var26, var28);
                     if (-2040437663 * var13.field4944 != 0) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        if (null != var9) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           var9.method5389(var3, var4, var15, var16, var13.field4945, (byte)10);
                        }
                     }

                     return;
                  }

                  if (var7 == classKB.field3518.vmethod8((byte)1)) {
                     if (var12 == 892962765) {
                        return;
                     }

                     Object var43;
                     label468: {
                        if (-1 == var14) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (null == var13.field4928) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var43 = var13.method8575(var7, var6, var21, var23, var22, var24, -1495204728);
                              break label468;
                           }
                        }

                        var43 = new DynamicObject(var0, var5, var7, var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     Scene.method4495(
                        var25,
                        var1,
                        var3,
                        var4,
                        var22,
                        (Renderable)var43,
                        null,
                        classDI.field1503[var6],
                        0,
                        classDI.field1505[var6],
                        classDI.field1506[var6],
                        0,
                        0,
                        var26,
                        var28
                     );
                     return;
                  }

                  if (var7 == classKB.field3523.vmethod8((byte)1)) {
                     if (var12 == 892962765) {
                        return;
                     }

                     int var41 = 16;
                     long var53 = var25.method4461(var1, var3, var4);
                     if (var53 != 0L) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        var41 = classMU.method7771(classCN.method2542(var53), 1336987204).field4925 * -1659674841;
                     }

                     Object var56;
                     label477: {
                        var41++;
                        if (-1 == var14) {
                           if (var12 == 892962765) {
                              return;
                           }

                           if (var13.field4928 == null) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var56 = var13.method8575(classKB.field3518.vmethod8((byte)1), var6, var21, var23, var22, var24, -964167384);
                              break label477;
                           }
                        }

                        var56 = new DynamicObject(var0, var5, classKB.field3518.vmethod8((byte)1), var6, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     Scene.method4495(
                        var25,
                        var1,
                        var3,
                        var4,
                        var22,
                        (Renderable)var56,
                        null,
                        classDI.field1503[var6],
                        0,
                        classDI.field1505[var6] * var41,
                        var41 * classDI.field1506[var6],
                        0,
                        0,
                        var26,
                        var28
                     );
                     return;
                  }

                  if (var7 == classKB.field3520.vmethod8((byte)1)) {
                     if (var12 == 892962765) {
                        throw new IllegalStateException();
                     }

                     int var39 = 8;
                     long var52 = rl0.method10065(var25, var1, var3, var4);
                     if (var52 != 0L) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        var39 = classMU.method7771(classCN.method2542(var52), 1796451721).field4925 * -1659674841 / 2;
                     }

                     Object var55;
                     label488: {
                        var39++;
                        if (-1 == var14) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (var13.field4928 == null) {
                              var55 = var13.method8575(classKB.field3518.vmethod8((byte)1), 4 + var6, var21, var23, var22, var24, 403213395);
                              break label488;
                           }
                        }

                        var55 = new DynamicObject(var0, var5, classKB.field3518.vmethod8((byte)1), var6 + 4, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     Scene.method4495(
                        var25,
                        var1,
                        var3,
                        var4,
                        var22,
                        (Renderable)var55,
                        null,
                        256,
                        var6,
                        var39 * classDI.field1507[var6],
                        classDI.field1508[var6] * var39,
                        0,
                        0,
                        var26,
                        var28
                     );
                     return;
                  }

                  if (var7 == classKB.field3521.vmethod8((byte)1)) {
                     if (var12 == 892962765) {
                        throw new IllegalStateException();
                     }

                     Object var38;
                     int var51;
                     label499: {
                        var51 = var6 + 2 & 3;
                        if (var14 == -1) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (null == var13.field4928) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var38 = var13.method8575(classKB.field3518.vmethod8((byte)1), var51 + 4, var21, var23, var22, var24, -2138695091);
                              break label499;
                           }
                        }

                        var38 = new DynamicObject(var0, var5, classKB.field3518.vmethod8((byte)1), var51 + 4, var2, var3, var4, var14, var13.field4962, var10);
                     }

                     Scene.method4495(
                        var25,
                        var1,
                        var3,
                        var4,
                        var22,
                        (Renderable)var38,
                        null,
                        256,
                        var51,
                        classDI.field1507[var51],
                        classDI.field1508[var51],
                        0,
                        0,
                        var26,
                        var28
                     );
                     return;
                  }

                  if (var7 == classKB.field3522.vmethod8((byte)1)) {
                     int var36 = 8;
                     long var30 = rl0.method10065(var25, var1, var3, var4);
                     if (0L != var30) {
                        if (var12 == 892962765) {
                           throw new IllegalStateException();
                        }

                        var36 = classMU.method7771(classCN.method2542(var30), 1599776449).field4925 * -1659674841 / 2;
                     }

                     Object var32;
                     Object var33;
                     int var34;
                     label508: {
                        var36++;
                        var34 = 2 + var6 & 3;
                        if (var14 == -1) {
                           if (var12 == 892962765) {
                              throw new IllegalStateException();
                           }

                           if (null == var13.field4928) {
                              if (var12 == 892962765) {
                                 throw new IllegalStateException();
                              }

                              var32 = var13.method8575(classKB.field3518.vmethod8((byte)1), var6 + 4, var21, var23, var22, var24, 625289706);
                              var33 = var13.method8575(classKB.field3518.vmethod8((byte)1), var34 + 4, var21, var23, var22, var24, 893889394);
                              break label508;
                           }
                        }

                        var32 = new DynamicObject(var0, var5, classKB.field3518.vmethod8((byte)1), var6 + 4, var2, var3, var4, var14, var13.field4962, var10);
                        var33 = new DynamicObject(var0, var5, classKB.field3518.vmethod8((byte)1), 4 + var34, var2, var3, var4, var14, var13.field4962, var11);
                     }

                     Scene.method4495(
                        var25,
                        var1,
                        var3,
                        var4,
                        var22,
                        (Renderable)var32,
                        (Renderable)var33,
                        256,
                        var6,
                        classDI.field1507[var6] * var36,
                        var36 * classDI.field1508[var6],
                        classDI.field1507[var34],
                        classDI.field1508[var34],
                        var26,
                        var28
                     );
                     return;
                  }

                  return;
               }

               if (var12 == 892962765) {
                  throw new IllegalStateException();
               }
            }

            Object var29;
            if (var14 == -1 && var13.field4928 == null) {
               if (var12 == 892962765) {
                  throw new IllegalStateException();
               }

               var29 = var13.method8575(10, var6, var21, var23, var22, var24, -1581037939);
            } else {
               var29 = new DynamicObject(var0, var5, 10, var6, var2, var3, var4, var14, var13.field4962, var10);
            }

            if (null != var29) {
               short var10008;
               if (11 == var7) {
                  if (var12 == 892962765) {
                     throw new IllegalStateException();
                  }

                  var10008 = 256;
               } else {
                  var10008 = 0;
               }

               Scene.method4208(var25, var1, var3, var4, var22, var15, var16, (Renderable)var29, var10008, var26, var28);
            }

            if (-2040437663 * var13.field4944 != 0) {
               if (var12 == 892962765) {
                  throw new IllegalStateException();
               }

               if (var9 != null) {
                  if (var12 == 892962765) {
                     throw new IllegalStateException();
                  }

                  var9.method5389(var3, var4, var15, var16, var13.field4945, (byte)-127);
               }
            }
         }
      } catch (RuntimeException var35) {
         throw classEG.method3899(var35, "fk.at(" + ')');
      }
   }

   classFK() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ax")
   static final int method4825(int var0, int var1, int var2, int var3) {
      return var3 * var1 + var2 * var0 >> 16;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ac")
   static final int method4826(int var0, int var1, int var2, int var3) {
      return var3 * var1 + var2 * var0 >> 16;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("aa")
   static final int method4827(int var0, int var1, int var2, int var3) {
      return var3 * var1 + var2 * var0 >> 16;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFF)F")
   @ObfuscatedName("ad")
   static final float method4833(float var0, float var1, float var2, float var3) {
      return var1 * var2 - var3 * var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("al")
   static final int method4829(int var0, int var1, int var2, int var3) {
      return var1 * var2 - var3 * var0 >> 16;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("aj")
   static final int method4830(int var0, int var1, int var2, int var3) {
      return var1 * var2 - var3 * var0 >> 16;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFF)F")
   @ObfuscatedName("ay")
   static final float method4832(float var0, float var1, float var2, float var3) {
      return var2 * var0 - var1 * var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ao")
   static final int method4831(int var0, int var1, int var2, int var3) {
      return var1 * var2 - var3 * var0 >> 16;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("dx")
   static final void method4840(byte var0) {
      if (!client.$assertionsDisabled && !client.field939) {
         throw new AssertionError();
      } else {
         if (client.field1121 == null) {
            rl4 var1 = new rl4(client.field1117, client.field1095, classIS.field3051, client.field1045);
            var1.field5783 = var1.field5769.field1706 * -1444178379;
            var1.field5786 = var1.field5769.field1707 * -351145363;
            var1.field5754 = FloatProjection.field1770 * -1535860629;
            var1.field5784 = classBQ.field528 * 2016174363;
            var1.field5785 = classCQ.field1218.field1708 * -483624883;
            if (!client.$assertionsDisabled && client.field1123 != null) {
               throw new AssertionError();
            }

            client.field1121 = var1;
            var1.method10126();
         } else if (client.field1121.method10127()) {
            rl4 var2 = client.field1121;
            if (!client.$assertionsDisabled && client.field1123 != null) {
               throw new AssertionError();
            }

            client.field1121 = null;
            client.field1137 = false;
            classIS.field3051.field1708 = var2.field5785 * 339443845;
            client.method1632(var2);
            client.method2174(var2);
            if (!client.$assertionsDisabled && classOE.field4841.method2442() != 25) {
               throw new AssertionError();
            }

            if (!client.$assertionsDisabled && client.field939) {
               throw new AssertionError();
            }

            if (client.field1117 != var2.field5758 && client.field1117 != null) {
               client.field1028.info("Reloading due to draw callbacks change while loading!");
               client.field939 = true;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(F)F")
   @ObfuscatedName("ap")
   static final float method4835(float var0) {
      var0 = (var0 - 75.0F) * 1.0100503F;
      return 150.75377F / var0 + 1.0100503F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(F)F")
   @ObfuscatedName("au")
   static final float method4836(float var0) {
      var0 = (var0 - 75.0F) * 1.0100503F;
      return 150.75377F / var0 + 1.0100503F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(F)F")
   @ObfuscatedName("ai")
   static final float method4837(float var0) {
      var0 = (var0 - 75.0F) * 1.0100503F;
      return 150.75377F / var0 + 1.0100503F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("as")
   static final int method4828(int var0, int var1, int var2, int var3) {
      return var3 * var1 + var2 * var0 >> 16;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("cq")
   static int method4841(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (7463 == var0) {
            if (var3 <= 2) {
               throw new IllegalStateException();
            } else {
               boolean var10000;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 <= 2) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var4 = var10000;
               classON.method8650(var4, (byte)1);
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "fk.cq(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFFF)F")
   @ObfuscatedName("aq")
   static final float method4834(float var0, float var1, float var2, float var3) {
      return var1 * var2 - var3 * var0;
   }

   @ObfuscatedSignature(descriptor = "(Lte;IB)I")
   @ObfuscatedName("ap")
   public static int method4838(classTE var0, int var1, byte var2) {
      try {
         classON.method8646(var0, classYY.field7109, -174188555);
         if (var1 >= 0) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            int[] var3 = var0.method10791(608246344);
            int var4 = var0.method10797(199963441);

            for (int var5 = 0; var5 < var4; var5++) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               if (var1 < var3[var5]) {
                  return var5;
               }

               var1 -= var3[var5];
            }
         }

         return -1;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "fk.ap(" + 41);
      }
   }
}
