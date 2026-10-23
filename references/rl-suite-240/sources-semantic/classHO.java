import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ho")
public final class classHO {
   @ObfuscatedName("ae")
   int field2846;
   @ObfuscatedName("af")
   int field2847;
   @ObfuscatedName("ab")
   int field2845;
   @ObfuscatedName("az")
   int field2848;
   @ToRemove(unused = "true")
   @ObfuscatedName("cp")
   static final int field2850 = 72;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final int field2849 = 30;

   @ObfuscatedSignature(descriptor = "(Lte;Lyy;B)V")
   @ObfuscatedName("bn")
   static void method6112(classTE var0, classYY var1, byte var2) {
      try {
         classOL.method8543(var0, var1, true, (byte)32);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ho.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Loq;B)V")
   @ObfuscatedName("cv")
   static final void method6113(WorldView var0, classOQ var1, byte var2) {
      try {
         for (int var3 = 0; var3 < var0.field1695.method14050((byte)10); var3++) {
            if (var2 != 0) {
               throw new IllegalStateException();
            }

            NPC var4 = (NPC)IndexedObjectSet.method13449(var0.field1696, var0.field1695.method14048(var3, 1761435756));
            if (null != var4 && var4.vmethod262((byte)50)) {
               if (var2 != 0) {
                  throw new IllegalStateException();
               }

               if (var4.field1579.field5303 == var1 && var4.field1579.method9002((byte)-97)) {
                  if (var2 != 0) {
                     throw new IllegalStateException();
                  }

                  int var5 = -483624883 * var0.field1708;
                  int var6 = classKY.method6652(var4.field1484 * -1547553299, (byte)-93);
                  int var7 = classKY.method6652(-1272026483 * var4.field1486, (byte)8);
                  if (WorldView.method3745(var0, var6, var7, (byte)1)) {
                     if (var2 != 0) {
                        return;
                     }

                     if (670857619 * var4.field1439 == 1) {
                        if (var2 != 0) {
                           throw new IllegalStateException();
                        }

                        if ((-1547553299 * var4.field1484 & 127) == 64) {
                           if (var2 != 0) {
                              throw new IllegalStateException();
                           }

                           if (64 == (-1272026483 * var4.field1486 & 127)) {
                              if (var2 != 0) {
                                 throw new IllegalStateException();
                              }

                              if (client.field957 * 834768165 == var0.field1701[var6][var7]) {
                                 if (var2 != 0) {
                                    throw new IllegalStateException();
                                 }
                                 continue;
                              }

                              var0.field1701[var6][var7] = client.field957 * 834768165;
                           }
                        }
                     }

                     boolean var10004;
                     if (!var4.field1579.field5324) {
                        if (var2 != 0) {
                           throw new IllegalStateException();
                        }

                        var10004 = true;
                     } else {
                        var10004 = false;
                     }

                     long var8 = classRF.method9998(0, 0, 0, 1, var10004, -1315528093 * var4.field1478, var0.field1697 * 2140889407, (short)30035);
                     var4.field1487 = client.field856 * 1311089481;
                     int var10 = classKC.method6499(
                        var0, var4.field1484 * -1547553299, -1272026483 * var4.field1486, var5, var4.vmethod267(-1423776655), (short)-20256
                     );
                     int var11 = var4.field1439 * -14785344 - 64 + 60;
                     boolean var12;
                     if (!var4.field1579.method9031(2095992902)) {
                        if (var2 != 0) {
                           throw new IllegalStateException();
                        }

                        var12 = false;
                     } else {
                        label150: {
                           for (int var13 = 0; var13 < classIS.field3051.field1691.method14050((byte)10); var13++) {
                              if (var2 != 0) {
                                 throw new IllegalStateException();
                              }

                              WorldEntity var14 = (WorldEntity)classIS.field3051
                                 .field1698
                                 .method13637(classIS.field3051.field1691.method14048(var13, 1570533187));
                              if (var14 != null) {
                                 if (var2 != 0) {
                                    return;
                                 }

                                 if (client.field856 * 1612595797 == var14.field6010.field1687.field2143) {
                                    if (var2 != 0) {
                                       return;
                                    }

                                    if (!var14.method10601(1822994519) && classFA.method4576(var14, var4, -1555620546)) {
                                       if (var2 != 0) {
                                          throw new IllegalStateException();
                                       }

                                       var12 = true;
                                       break label150;
                                    }
                                 }
                              }
                           }

                           var12 = false;
                        }
                     }

                     var4.field1584 = var12;
                     boolean var16 = var0.field1687
                        .method4505(
                           var5,
                           var4.field1484 * -1547553299,
                           -1272026483 * var4.field1486,
                           var10,
                           var11,
                           var4,
                           1457033841 * var4.field1485,
                           var8,
                           var4.field1438
                        );
                     if (var16) {
                        if (var2 != 0) {
                           throw new IllegalStateException();
                        }

                        classHH.method6088(var0, var4, var5, var10, var8);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "ho.cv(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnc;I)I")
   @ObfuscatedName("dl")
   public static int method6111(classNC var0, int var1) {
      if (var0 == null) {
         var0.method7814();
      }

      return classNC.method7837(var0, var1);
   }

   classHO(WorldMapRenderer var1) {
      this.this$0 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvh;I)I")
   @ObfuscatedName("wq")
   public static int method6110(classVH var0, int var1) {
      return var0.field6510[var1 & 15];
   }

   @ObfuscatedSignature(descriptor = "(Lct;IIIIB)V")
   @ObfuscatedName("fx")
   static final void method6114(Player var0, int var1, int var2, int var3, int var4, byte var5) {
      boolean var6 = Scene.method4478(var0, false);
      if (var6) {
         int var11 = var4;
         int var10 = var3;
         int var9 = var2;
         int var8 = var1;
         Player var7 = var0;
         byte var12 = 18;

         try {
            if (var7.field1478 * -1315528093 == -2130951373 * client.field846) {
               if (var12 <= 0) {
                  throw new IllegalStateException();
               }
            } else if (-1927903443 * client.field975.field6795 >= 400) {
               if (var12 <= 0) {
                  throw new IllegalStateException();
               }
            } else {
               Player var14 = classMB.method7564(1766208156);
               int var10000;
               if (null != var14) {
                  if (var12 <= 0) {
                     return;
                  }

                  var10000 = var14.field1259 * 810892507;
               } else {
                  var10000 = 0;
               }

               int var13 = var10000;
               int var23 = var13;
               Player var16 = classMB.method7564(873668262);
               if (var16 != null) {
                  if (var12 <= 0) {
                     throw new IllegalStateException();
                  }

                  var10000 = var16.field1263 * 532975721;
               } else {
                  var10000 = 0;
               }

               int var15 = var10000;
               int var24 = var15;
               String var17;
               if (1777039337 * var7.field1260 == 0) {
                  String var18 = var7.field1268[0] + var7.field1281 + var7.field1268[1];
                  int var20 = 810892507 * var7.field1259;
                  int var21 = var13 - var20;
                  String var19;
                  if (var21 < -9) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(16711680, (byte)35);
                  } else if (var21 < -6) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(16723968, (byte)46);
                  } else if (var21 < -3) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(16740352, (byte)123);
                  } else if (var21 < 0) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(16756736, (byte)90);
                  } else if (var21 > 9) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(65280, (byte)84);
                  } else if (var21 > 6) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(4259584, (byte)59);
                  } else if (var21 > 3) {
                     if (var12 <= 0) {
                        return;
                     }

                     var19 = classQY.method9885(8453888, (byte)71);
                  } else if (var21 > 0) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     var19 = classQY.method9885(12648192, (byte)54);
                  } else {
                     var19 = classQY.method9885(16776960, (byte)111);
                  }

                  var17 = var18 + var19 + " " + classDO.field1591 + classKK.field3686 + 810892507 * var7.field1259 + classDO.field1589 + var7.field1268[2];
               } else {
                  var17 = var7.field1268[0]
                     + var7.field1281
                     + var7.field1268[1]
                     + " "
                     + classDO.field1591
                     + classKK.field3780
                     + var7.field1260 * 1777039337
                     + classDO.field1589
                     + var7.field1268[2];
               }

               if (-512792823 * client.field946 == 1) {
                  if (var12 <= 0) {
                     return;
                  }

                  Friend.method10203(
                     classKK.field3772,
                     client.field805 + " " + classDO.field1588 + " " + classQY.method9885(16777215, (byte)112) + var17,
                     14,
                     var8,
                     var9,
                     var10,
                     -1,
                     false,
                     var11,
                     -324749371
                  );
               } else if (client.field980) {
                  if (8 == (1547634457 * classLF.field4129 & 8)) {
                     Friend.method10203(
                        client.field925,
                        client.field984 + " " + classDO.field1588 + " " + classQY.method9885(16777215, (byte)57) + var17,
                        15,
                        var8,
                        var9,
                        var10,
                        -1,
                        false,
                        var11,
                        -324749371
                     );
                  }
               } else {
                  for (int var25 = 7; var25 >= 0; var25--) {
                     if (client.field969[var25] != null) {
                        short var27;
                        var27 = 0;
                        label216:
                        if (client.field969[var25].equalsIgnoreCase(classKK.field3774)) {
                           if (var12 <= 0) {
                              throw new IllegalStateException();
                           }

                           if (client.field917 == classCK.field766) {
                              if (var12 <= 0) {
                                 return;
                              }
                              continue;
                           }

                           label213: {
                              if (client.field917 != classCK.field769) {
                                 if (var12 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 if (classCK.field765 != client.field917) {
                                    break label213;
                                 }

                                 if (var12 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 if (var7.field1259 * 810892507 <= var23) {
                                    break label213;
                                 }
                              }

                              var27 = 2000;
                           }

                           if (0 != var24) {
                              if (var12 <= 0) {
                                 throw new IllegalStateException();
                              }

                              if (var7.field1263 * 532975721 != 0) {
                                 if (var12 <= 0) {
                                    throw new IllegalStateException();
                                 }

                                 if (var7.field1263 * 532975721 == var24) {
                                    if (var12 <= 0) {
                                       throw new IllegalStateException();
                                    }

                                    var27 = 2000;
                                 } else {
                                    var27 = 0;
                                 }
                                 break label216;
                              }
                           }

                           if (client.field917 == classCK.field768) {
                              if (var12 <= 0) {
                                 return;
                              }

                              if (var7.method2639(-1817263910)) {
                                 if (var12 <= 0) {
                                    return;
                                 }

                                 var27 = 2000;
                              }
                           }
                        } else if (client.field802[var25]) {
                           if (var12 <= 0) {
                              return;
                           }

                           var27 = 2000;
                        }

                        int var28 = 0;
                        var28 = var27 + client.field822[var25];
                        Friend.method10203(
                           client.field969[var25], classQY.method9885(16777215, (byte)38) + var17, var28, var8, var9, var10, -1, false, var11, -324749371
                        );
                     }
                  }
               }

               for (int var26 = 0; var26 < -1927903443 * client.field975.field6795; var26++) {
                  if (var12 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (client.field975.field6798[var26] == 23) {
                     if (var12 <= 0) {
                        throw new IllegalStateException();
                     }

                     client.field975.field6803[var26] = classQY.method9885(16777215, (byte)88) + var17;
                     break;
                  }
               }
            }
         } catch (RuntimeException var22) {
            throw classEG.method3899(var22, "ho.fx(" + ')');
         }
      }
   }
}
