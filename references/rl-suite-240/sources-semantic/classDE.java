import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("de")
public class classDE implements classPU {
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field1405 = 19;
   @ToRemove(unused = "true")
   @ObfuscatedName("bd")
   static final int field1406 = 101;
   @ObfuscatedName("ao")
   static int[] field1407;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field1404 = 0;
   @ObfuscatedName("av")
   static int[][][] field1408;

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("bu")
   @Override
   public void vmethod323(Widget var1) {
      if (1 == var1.field4245 * -851058883) {
         classKB.method6492(var1.field4316, "", 24, 0, 0, -1163656551 * var1.field4327, var1.field4317 * 2073671441, -1923026805);
      }

      if (-851058883 * var1.field4245 == 2 && !client.field980) {
         String var2 = classWK.field6689.method12201(var1, (byte)-91);
         if (null != var2) {
            classKB.method6492(
               var2, classQY.method9885(65280, (byte)119) + var1.field4315, 25, 0, -1, -1163656551 * var1.field4327, 2073671441 * var1.field4317, -2018058017
            );
         }
      }

      if (3 == -851058883 * var1.field4245) {
         classTA.method10774(classKK.field3853, "", 26, 0, 0, var1.field4327 * -1163656551, (byte)27);
      }

      if (var1.field4245 * -851058883 == 4) {
         classTA.method10774(var1.field4316, "", 28, 0, 0, var1.field4327 * -1163656551, (byte)71);
      }

      if (5 == -851058883 * var1.field4245) {
         classTA.method10774(var1.field4316, "", 29, 0, 0, var1.field4327 * -1163656551, (byte)0);
      }

      if (-851058883 * var1.field4245 == 6 && classWK.field6689.method12234((byte)78)) {
         classTA.method10774(var1.field4316, "", 30, 0, -1, var1.field4327 * -1163656551, (byte)-21);
      }

      if (var1.field4273) {
         if (client.field980) {
            int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var9 = (var3 >> 21 & 1) != 0;
            if (var9 && (1547634457 * classLF.field4129 & 32) == 32) {
               classKB.method6492(
                  client.field925,
                  client.field984 + " " + classDO.field1588 + " " + var1.field4307,
                  58,
                  0,
                  var1.field4258 * 63131743,
                  -1163656551 * var1.field4327,
                  var1.field4317 * 2073671441,
                  -1884162094
               );
            }
         } else {
            for (int var10 = 31; var10 >= 0; var10--) {
               if (var10 == 2085260293 * var1.field4308) {
                  String var12 = classWK.field6689.method12201(var1, (byte)-70);
                  if (null != var12) {
                     classKB.method6492(
                        var12, var1.field4307, 25, 0, 63131743 * var1.field4258, var1.field4327 * -1163656551, var1.field4317 * 2073671441, -2080429073
                     );
                  }
               }

               String var13 = classVV.method12200(classWK.field6689, var1, var10, -1760527185);
               if (null != var13) {
                  int var4;
                  short var5;
                  if (var10 > 2085260293 * var1.field4308) {
                     var5 = 1007;
                     var4 = classKB.method6492(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        63131743 * var1.field4258,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        -2026483121
                     );
                  } else {
                     var5 = 57;
                     var4 = Friend.method10203(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        var1.field4258 * 63131743,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        var1.field4371,
                        0,
                        -324749371
                     );
                  }

                  if (null != var1.field4351 && var10 < var1.field4351.length && var1.field4351[var10] != null) {
                     String[] var6 = var1.field4351[var10];

                     for (int var7 = var6.length - 1; var7 >= 0; var7--) {
                        int var8 = var7 + 1 << 16 | var10 + 1;
                        if (var6[var7] != null && !var6[var7].isEmpty()) {
                           classZO.method14128(
                              var4,
                              var6[var7],
                              "",
                              var5,
                              var8,
                              63131743 * var1.field4258,
                              var1.field4327 * -1163656551,
                              2073671441 * var1.field4317,
                              0,
                              -1739941876
                           );
                        }
                     }
                  }
               }
            }

            int var14 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var11 = 0 != (var14 & 1);
            if (var11) {
               classTA.method10774(classKK.field3775, "", 30, 0, 63131743 * var1.field4258, -1163656551 * var1.field4327, (byte)51);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod317(int var1) {
      try {
         classEY.method4149((byte)39);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "de.az(" + ')');
      }
   }

   classDE() {
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)V")
   @ObfuscatedName("al")
   @Override
   public void vmethod324(Widget var1, byte var2) {
      try {
         if (1 == var1.field4245 * -851058883) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            }

            classKB.method6492(var1.field4316, "", 24, 0, 0, -1163656551 * var1.field4327, var1.field4317 * 2073671441, -2137960400);
         }

         if (-851058883 * var1.field4245 == 2 && !client.field980) {
            if (var2 >= 1) {
               return;
            }

            String var3 = classWK.field6689.method12201(var1, (byte)-75);
            if (null != var3) {
               classKB.method6492(
                  var3, classQY.method9885(65280, (byte)82) + var1.field4315, 25, 0, -1, -1163656551 * var1.field4327, 2073671441 * var1.field4317, -2110413438
               );
            }
         }

         if (3 == -851058883 * var1.field4245) {
            if (var2 >= 1) {
               return;
            }

            classTA.method10774(classKK.field3853, "", 26, 0, 0, var1.field4327 * -1163656551, (byte)26);
         }

         if (var1.field4245 * -851058883 == 4) {
            if (var2 >= 1) {
               return;
            }

            classTA.method10774(var1.field4316, "", 28, 0, 0, var1.field4327 * -1163656551, (byte)-61);
         }

         if (5 == -851058883 * var1.field4245) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            }

            classTA.method10774(var1.field4316, "", 29, 0, 0, var1.field4327 * -1163656551, (byte)-18);
         }

         if (-851058883 * var1.field4245 == 6 && classWK.field6689.method12234((byte)35)) {
            classTA.method10774(var1.field4316, "", 30, 0, -1, var1.field4327 * -1163656551, (byte)107);
         }

         if (var1.field4273) {
            if (client.field980) {
               if (var2 >= 1) {
                  return;
               }

               int var16 = classVV.method12246(classWK.field6689, var1, (byte)0);
               boolean var17;
               if ((var16 >> 21 & 1) != 0) {
                  if (var2 >= 1) {
                     return;
                  }

                  var17 = true;
               } else {
                  var17 = false;
               }

               boolean var13 = var17;
               if (var13) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  if ((1547634457 * classLF.field4129 & 32) == 32) {
                     if (var2 >= 1) {
                        throw new IllegalStateException();
                     }

                     classKB.method6492(
                        client.field925,
                        client.field984 + " " + classDO.field1588 + " " + var1.field4307,
                        58,
                        0,
                        var1.field4258 * 63131743,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        -1892911211
                     );
                  }
               }
            } else {
               for (int var11 = 31; var11 >= 0; var11--) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  if (var11 == 2085260293 * var1.field4308) {
                     if (var2 >= 1) {
                        throw new IllegalStateException();
                     }

                     String var4 = classWK.field6689.method12201(var1, (byte)-74);
                     if (null != var4) {
                        if (var2 >= 1) {
                           return;
                        }

                        classKB.method6492(
                           var4, var1.field4307, 25, 0, 63131743 * var1.field4258, var1.field4327 * -1163656551, var1.field4317 * 2073671441, -1948501537
                        );
                     }
                  }

                  String var14 = classVV.method12200(classWK.field6689, var1, var11, -1760527185);
                  if (null != var14) {
                     if (var2 >= 1) {
                        throw new IllegalStateException();
                     }

                     int var5;
                     short var6;
                     if (var11 > 2085260293 * var1.field4308) {
                        if (var2 >= 1) {
                           throw new IllegalStateException();
                        }

                        var6 = 1007;
                        var5 = classKB.method6492(
                           var14,
                           var1.field4307,
                           var6,
                           1 + var11,
                           63131743 * var1.field4258,
                           -1163656551 * var1.field4327,
                           var1.field4317 * 2073671441,
                           -2076642034
                        );
                     } else {
                        var6 = 57;
                        var5 = Friend.method10203(
                           var14,
                           var1.field4307,
                           var6,
                           1 + var11,
                           var1.field4258 * 63131743,
                           -1163656551 * var1.field4327,
                           var1.field4317 * 2073671441,
                           var1.field4371,
                           0,
                           -324749371
                        );
                     }

                     if (null != var1.field4351) {
                        if (var2 >= 1) {
                           throw new IllegalStateException();
                        }

                        if (var11 < var1.field4351.length) {
                           if (var2 >= 1) {
                              throw new IllegalStateException();
                           }

                           if (var1.field4351[var11] != null) {
                              if (var2 >= 1) {
                                 return;
                              }

                              String[] var7 = var1.field4351[var11];

                              for (int var8 = var7.length - 1; var8 >= 0; var8--) {
                                 if (var2 >= 1) {
                                    throw new IllegalStateException();
                                 }

                                 int var9 = var8 + 1 << 16 | var11 + 1;
                                 if (var7[var8] != null && !var7[var8].isEmpty()) {
                                    classZO.method14128(
                                       var5,
                                       var7[var8],
                                       "",
                                       var6,
                                       var9,
                                       63131743 * var1.field4258,
                                       var1.field4327 * -1163656551,
                                       2073671441 * var1.field4317,
                                       0,
                                       -1739941876
                                    );
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               int var15 = classVV.method12246(classWK.field6689, var1, (byte)0);
               boolean var10000;
               if (0 != (var15 & 1)) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var12 = var10000;
               if (var12) {
                  if (var2 >= 1) {
                     throw new IllegalStateException();
                  }

                  classTA.method10774(classKK.field3775, "", 30, 0, 63131743 * var1.field4258, -1163656551 * var1.field4327, (byte)14);
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "de.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;I)V")
   @ObfuscatedName("ab")
   @Override
   public void vmethod329(int var1, int var2, int var3, int var4, String var5, int var6) {
      try {
         classEA.method3815(var1, var2, var3, var4, var5, -746579000);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "de.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;Ljava/lang/String;B)V")
   @ObfuscatedName("ag")
   @Override
   public void vmethod334(int var1, int var2, int var3, int var4, String var5, String var6, byte var7) {
      try {
         classBQ.method1184(var1, var2, var3, var4, var5, var6, -2085845878);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "de.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   @Override
   public void vmethod337(byte var1) {
      try {
         ClanChannel.method6234((byte)-18);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "de.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   @Override
   public void vmethod341() {
      classBT.method1245(388459967);
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("ac")
   @Override
   public void vmethod344(int var1, int var2, byte var3) {
      try {
         classON.method8649(var1, var2, (byte)1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "de.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;I)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod347(ScriptEvent var1, int var2) {
      try {
         classSZ.method10771(var1, -1176057113);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "de.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;I)V")
   @ObfuscatedName("ao")
   @Override
   public void vmethod349(ScriptEvent var1, int var2) {
      try {
         ItemLayer.method3976(var1, 5000000, 0, (byte)-76);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "de.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("aq")
   @Override
   public void vmethod363(int var1, int var2, int var3) {
      try {
         classBW.method1338(var1, var2, 1535715072);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "de.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("aj")
   @Override
   public void vmethod357(Widget var1, int var2) {
      try {
         if (var1 == null) {
            if (var2 <= 1664618047) {
               return;
            }

            client.field909.method4605((byte)29);
         } else {
            classLH var3 = var1.method7334(-444850991);
            if (var3 != null && var3.method7030(-880967958)) {
               if (var2 <= 1664618047) {
                  return;
               }

               client.field909.method4600(var1, (byte)-6);
               var3.method6847(true, 1979854656);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "de.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;I)V")
   @ObfuscatedName("ay")
   @Override
   public void vmethod360(Widget var1, Widget var2, int var3) {
      try {
         classTM.method10984(var1, var2, 1238625483);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "de.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;)V")
   @ObfuscatedName("bb")
   @Override
   public void vmethod361(Widget var1, Widget var2) {
      classTM.method10984(var1, var2, 1238625483);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ad")
   @Override
   public void vmethod320(int var1, int var2, int var3, int var4) {
      if (0 == client.field946 * 1246897865 && !client.field980) {
         classQX.method9881(var1 - var3, var2 - var4, 1247962170);
      }

      long var5 = -1L;
      int var7 = 0;
      int var8 = 0;

      while (true) {
         int var10 = classFO.field2348 * 613116963 + 1;
         if (var8 >= var10) {
            if (var5 != -1L) {
               var8 = (int)(var5 >>> 0 & 127L);
               var10 = classRC.method9976(var5);
               Player var32 = classEN.method3954(client.field972 * -1749610311, client.field815, -700951001);
               if (null != var32) {
                  classHO.method6114(var32, 927693091 * client.field972, var8, var10, var7, (byte)4);
               }
            }

            return;
         }

         long var11 = classDA.method2866(var8, -1627477799);
         label390:
         if (var11 != -1L) {
            int var13 = (int)(var11 >>> 0 & 127L);
            int var14 = var13;
            int var15 = classRC.method9976(var11);
            int var16 = classCV.method2679(var11);
            int var17 = classCN.method2542(var11);
            int var18 = classXO.method12924(var11);
            WorldView var19 = null;
            WorldEntity var20 = null;
            if (0 == var18) {
               var19 = classIS.field3051;
            } else {
               var20 = (WorldEntity)classIS.field3051.field1698.method13637(var18);
               if (var20 != null) {
                  if (var20.method10601(2046690355)) {
                     break label390;
                  }

                  var19 = var20.field6010;
               }
            }

            if (null != var19) {
               int var22 = -1;
               if (client.field847 * -2034209657 == var18) {
                  var22 = var19.field1708 * -483624883;
               } else if (0 == client.field847 * -2038496211) {
                  var22 = var20.field6024.method8500(-1821270104);
               } else {
                  WorldEntity var23 = EnumComposition.method8383((byte)0);
                  if (var23 != null) {
                     WorldView var24 = var23.field6010;
                     if (-483624883 * var24.field1708 == var23.field6024.method8500(-1821270104)) {
                        if (0 == var18) {
                           var22 = var23.vmethod258((byte)-8);
                        } else {
                           var22 = var20.field6024.method8500(-1821270104);
                        }
                     }
                  }
               }

               var22 = var22;
               label381:
               if (var22 != -1) {
                  if (2 == var16 && classKK.method6532(var19.field1687, var22, var13, var15, var11) >= 0) {
                     ObjectComposition var35 = classMU.method7771(var17, 1718652496);
                     if (var35.field4928 != null) {
                        var35 = ObjectComposition.method8585(var35, 1271988919);
                     }

                     if (null == var35) {
                        break label381;
                     }

                     classCD var40 = null;

                     for (classCD var25 = (classCD)var19.field1688.method7957(); var25 != null; var25 = (classCD)var19.field1688.method7965()) {
                        if (var22 == 1181548283 * var25.field679
                           && var14 == -1779660971 * var25.field676
                           && var15 == 363793528 * var25.field680
                           && var17 == 1490231037 * var25.field675) {
                           var40 = var25;
                           break;
                        }
                     }

                     if (!classIV.field3069 && 1 == -512792823 * client.field946) {
                        Friend.method10203(
                           classKK.field3772,
                           client.field805
                              + " "
                              + classDO.field1588
                              + " "
                              + classQY.method9885(167336952, (byte)103)
                              + ObjectComposition.method8624(var35, (short)-8369),
                           1,
                           var17,
                           var14,
                           var15,
                           -1,
                           false,
                           var18,
                           -324749371
                        );
                     } else if (client.field980) {
                        if (!classIV.field3069 && (1883831468 * classLF.field4129 & 4) == 4) {
                           Friend.method10203(
                              client.field925,
                              client.field984
                                 + " "
                                 + classDO.field1588
                                 + " "
                                 + classQY.method9885(-1181657652, (byte)80)
                                 + ObjectComposition.method8624(var35, (short)-31095),
                              2,
                              var17,
                              var14,
                              var15,
                              -1,
                              false,
                              var18,
                              -324749371
                           );
                        }
                     } else {
                        if (var35.method8548((byte)64).vmethod546((byte)0)) {
                           for (int var47 = 4; var47 >= 0; var47--) {
                              if (!classIV.field3069 && (var40 == null || var40.method1413(var47, -1621303750))) {
                                 String var26 = var35.method8548((byte)25).vmethod551(var47, classLB.field4088, (byte)-49);
                                 if (var40 != null && var40.method1417(var47, 881207265)) {
                                    var26 = var40.method1423(var47, -1175792767);
                                 }

                                 if (client.field823) {
                                    var26 = classQM.method9703(var26, var47, (byte)-114);
                                 }

                                 if (var26 != null) {
                                    short var27 = 0;
                                    if (0 == var47) {
                                       var27 = 3;
                                    }

                                    if (1 == var47) {
                                       var27 = 4;
                                    }

                                    if (var47 == 2) {
                                       var27 = 5;
                                    }

                                    if (3 == var47) {
                                       var27 = 6;
                                    }

                                    if (4 == var47) {
                                       var27 = 1001;
                                    }

                                    int var28 = Friend.method10203(
                                       var26,
                                       classQY.method9885(65535, (byte)70) + ObjectComposition.method8624(var35, (short)-2969),
                                       var27,
                                       var17,
                                       var14,
                                       var15,
                                       -1,
                                       false,
                                       var18,
                                       -324749371
                                    );
                                    classJC.method6396(
                                       var28,
                                       var35.method8548((byte)51),
                                       var47,
                                       classQY.method9885(-187943981, (byte)31) + ObjectComposition.method8624(var35, (short)-325),
                                       var27,
                                       var17,
                                       var14,
                                       var15,
                                       var18,
                                       -1533141989
                                    );
                                 }
                              }
                           }
                        }

                        Friend.method10203(
                           classKK.field3773,
                           classQY.method9885(65535, (byte)86) + ObjectComposition.method8624(var35, (short)-11956),
                           1002,
                           -1391169667 * var35.field4920,
                           var14,
                           var15,
                           -1,
                           false,
                           var18,
                           -324749371
                        );
                     }
                  }

                  if (1 == var16) {
                     NPC var36 = (NPC)IndexedObjectSet.method13449(var19.field1696, var17);
                     if (null == var36) {
                        break label381;
                     }

                     if (-743625358 * var36.field1579.field5282 == 1
                        && 64 == (var36.field1484 * -1547553299 & 127)
                        && (1216928792 * var36.field1486 & 520846524) == 1488619198) {
                        for (int var41 = 0; var41 < var19.field1695.method14050((byte)10); var41++) {
                           NPC var48 = (NPC)IndexedObjectSet.method13449(var19.field1696, var19.field1695.method14048(var41, 1740067311));
                           if (null != var48
                              && var48 != var36
                              && 1 == -1560125940 * var48.field1579.field5282
                              && var48.field1484 * -846840256 == -130276445 * var36.field1484
                              && var36.field1486 * -1272026483 == -1272026483 * var48.field1486) {
                              WorldMapRenderer.method5822(var48, var48.field1478 * -948999146, var14, var15, var18, -1272411425);
                           }
                        }

                        int var42 = -2087374717 * client.field838.field1417;
                        int[] var49 = client.field838.field1421;

                        for (int var54 = 0; var54 < var42; var54++) {
                           Player var58 = (Player)IndexedObjectSet.method13449(var19.field1692, var49[var54]);
                           if (var58 != null
                              && var36.field1484 * -1547553299 == -1547553299 * var58.field1484
                              && -1272026483 * var36.field1486 == -1272026483 * var58.field1486) {
                              classHO.method6114(var58, var49[var54], var14, var15, var18, (byte)105);
                           }
                        }
                     }

                     WorldMapRenderer.method5822(var36, var17, var14, var15, var18, -1899678684);
                  }

                  if (var16 == 0) {
                     Player var37 = (Player)IndexedObjectSet.method13449(var19.field1692, var17);
                     if (var37 == null) {
                        break label381;
                     }

                     if ((878961075 * var37.field1484 & -278783992) == -1544910622 && 1670126444 == (var37.field1486 * -65813981 & -1202524725)) {
                        for (int var43 = 0; var43 < var19.field1695.method14050((byte)10); var43++) {
                           NPC var50 = (NPC)IndexedObjectSet.method13449(var19.field1696, var19.field1695.method14048(var43, 2087684651));
                           if (null != var50
                              && 1 == -1216348145 * var50.field1579.field5282
                              && -1760754110 * var37.field1484 == var50.field1484 * 1689465376
                              && -1272026483 * var37.field1486 == var50.field1486 * 1349220952) {
                              WorldMapRenderer.method5822(var50, var19.field1695.method14048(var43, 1645664100), var14, var15, var18, -1916761897);
                           }
                        }

                        int var44 = client.field838.field1417 * -2087374717;
                        int[] var51 = client.field838.field1421;

                        for (int var55 = 0; var55 < var44; var55++) {
                           Player var59 = (Player)IndexedObjectSet.method13449(var19.field1692, var51[var55]);
                           if (var59 != null
                              && var59 != var37
                              && -252901333 * var59.field1484 == var37.field1484 * -1547553299
                              && 489838422 * var59.field1486 == -1272026483 * var37.field1486) {
                              classHO.method6114(var59, var51[var55], var14, var15, var18, (byte)77);
                           }
                        }
                     }

                     if (331439937 * client.field972 != var17) {
                        classHO.method6114(var37, var17, var14, var15, var18, (byte)43);
                     } else {
                        var5 = var11;
                        var7 = var18;
                     }
                  }

                  if (var16 == 3) {
                     Deque2 var38 = var19.field1699[var22][var14][var15];
                     if (var38 != null) {
                        for (TileItem var45 = (TileItem)Deque2.method7962(var38); var45 != null; var45 = (TileItem)Deque2.method7968(var38)) {
                           ItemComposition var52 = classOB.method8329(-1078363104 * var45.field1549, (byte)5);
                           if (!WidgetNode.field5278 && client.field946 * 1515841483 == 1) {
                              Friend.method10203(
                                 classKK.field3772,
                                 client.field805 + " " + classDO.field1588 + " " + classQY.method9885(16748608, (byte)19) + var52.method8741(1248604557),
                                 16,
                                 -1370204663 * var45.field1549,
                                 var14,
                                 var15,
                                 -1,
                                 false,
                                 var18,
                                 -324749371
                              );
                           } else if (client.field980) {
                              if (!WidgetNode.field5278 && (1547634457 * classLF.field4129 & 1) == 1) {
                                 Friend.method10203(
                                    client.field925,
                                    client.field984 + " " + classDO.field1588 + " " + classQY.method9885(-1547193816, (byte)26) + var52.method8741(1248604557),
                                    17,
                                    1638531669 * var45.field1549,
                                    var14,
                                    var15,
                                    -1,
                                    false,
                                    var18,
                                    -324749371
                                 );
                              }
                           } else {
                              for (int var56 = 4; var56 >= 0; var56--) {
                                 if (!WidgetNode.field5278 && TileItem.method3407(var45, var56, 1426573798)) {
                                    String var60 = ItemComposition.method8666(var52, (byte)68).vmethod551(var56, classLB.field4088, (byte)70);
                                    if (client.field823) {
                                       var60 = classQM.method9703(var60, var56, (byte)-124);
                                    }

                                    if (var60 != null) {
                                       byte var61 = 0;
                                       if (0 == var56) {
                                          var61 = 18;
                                       }

                                       if (1 == var56) {
                                          var61 = 19;
                                       }

                                       if (var56 == 2) {
                                          var61 = 20;
                                       }

                                       if (3 == var56) {
                                          var61 = 21;
                                       }

                                       if (var56 == 4) {
                                          var61 = 22;
                                       }

                                       int var29 = Friend.method10203(
                                          var60,
                                          classQY.method9885(273770505, (byte)122) + var52.method8741(1248604557),
                                          var61,
                                          -1370204663 * var45.field1549,
                                          var14,
                                          var15,
                                          -1,
                                          false,
                                          var18,
                                          -324749371
                                       );
                                       classJC.method6396(
                                          var29,
                                          ItemComposition.method8666(var52, (byte)70),
                                          var56,
                                          classQY.method9885(16748608, (byte)17) + var52.method8741(1248604557),
                                          var61,
                                          var45.field1549 * -1370204663,
                                          var14,
                                          var15,
                                          var18,
                                          -1533141989
                                       );
                                    }
                                 }
                              }

                              Friend.method10203(
                                 classKK.field3773,
                                 classQY.method9885(16748608, (byte)78) + var52.method8741(1248604557),
                                 -767147577,
                                 -1370204663 * var45.field1549,
                                 var14,
                                 var15,
                                 -1,
                                 false,
                                 var18,
                                 -324749371
                              );
                           }
                        }
                     }
                  }

                  if (var16 == 4) {
                     var20 = (WorldEntity)var19.field1698.method13637(var17);
                     if (var20 != null) {
                        WorldEntityConfig var39 = var20.field6024;
                        if (1 == client.field946 * -512792823) {
                           Friend.method10203(
                              classKK.field3772,
                              client.field805 + " " + classDO.field1588 + " " + classQY.method9885(-1069629520, (byte)63) + var39.field4879,
                              -1592091818,
                              var17,
                              var14,
                              var15,
                              -1,
                              false,
                              var18,
                              -324749371
                           );
                        } else if (!client.field980) {
                           String[] var46 = var39.field4881;
                           if (var46 != null) {
                              for (int var53 = 4; var53 >= 0; var53--) {
                                 if (var20.method10631(var53, (byte)96) && var46[var53] != null) {
                                    int var57 = 0;
                                    if (var53 == 0) {
                                       var57 = -330540535;
                                    }

                                    if (1 == var53) {
                                       var57 = -1759116192;
                                    }

                                    if (2 == var53) {
                                       var57 = 65;
                                    }

                                    if (var53 == 3) {
                                       var57 = 66;
                                    }

                                    if (var53 == 4) {
                                       var57 = 67;
                                    }

                                    Friend.method10203(
                                       var46[var53],
                                       classQY.method9885(15574765, (byte)58) + var39.field4879,
                                       var57,
                                       var17,
                                       var14,
                                       var15,
                                       -1,
                                       false,
                                       var18,
                                       -324749371
                                    );
                                 }
                              }
                           }

                           Friend.method10203(
                              classKK.field3773,
                              classQY.method9885(15574765, (byte)83) + var39.field4879,
                              1013,
                              WorldEntityConfig.method8498(var39, (byte)-77),
                              var14,
                              var15,
                              -1,
                              false,
                              var18,
                              -324749371
                           );
                        }
                     }
                  }
               }
            }
         }

         var8++;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bc")
   @Override
   public void vmethod345(int var1, int var2) {
      classON.method8649(var1, var2, (byte)-53);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqn;)Lzr;")
   @ObfuscatedName("uv")
   public static ScriptEvent method2999(classQN var0) {
      return (ScriptEvent)var0.field5566.method8204();
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("bk")
   @Override
   public void vmethod325(Widget var1) {
      if (1 == var1.field4245 * -1642853798) {
         classKB.method6492(var1.field4316, "", 24, 0, 0, -1347153358 * var1.field4327, var1.field4317 * -1292404675, -2007679801);
      }

      if (-851058883 * var1.field4245 == 2 && !client.field980) {
         String var2 = classWK.field6689.method12201(var1, (byte)1);
         if (null != var2) {
            classKB.method6492(
               var2,
               classQY.method9885(-913980542, (byte)101) + var1.field4315,
               25,
               0,
               -1,
               -1163656551 * var1.field4327,
               2073671441 * var1.field4317,
               -1956535645
            );
         }
      }

      if (3 == 1011250338 * var1.field4245) {
         classTA.method10774(classKK.field3853, "", 26, 0, 0, var1.field4327 * -755207048, (byte)44);
      }

      if (var1.field4245 * 1725765931 == 4) {
         classTA.method10774(var1.field4316, "", 28, 0, 0, var1.field4327 * -2063359329, (byte)-46);
      }

      if (5 == -1533546012 * var1.field4245) {
         classTA.method10774(var1.field4316, "", 29, 0, 0, var1.field4327 * -1163656551, (byte)44);
      }

      if (-851058883 * var1.field4245 == 6 && classWK.field6689.method12234((byte)121)) {
         classTA.method10774(var1.field4316, "", 30, 0, -1, var1.field4327 * -1478771511, (byte)14);
      }

      if (var1.field4273) {
         if (client.field980) {
            int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var9 = (var3 >> 21 & 1) != 0;
            if (var9 && (1547634457 * classLF.field4129 & 32) == 32) {
               classKB.method6492(
                  client.field925,
                  client.field984 + " " + classDO.field1588 + " " + var1.field4307,
                  58,
                  0,
                  var1.field4258 * 63131743,
                  -1896183749 * var1.field4327,
                  var1.field4317 * 1064234788,
                  -2109866013
               );
            }
         } else {
            for (int var10 = 1144502211; var10 >= 0; var10--) {
               if (var10 == -1208090548 * var1.field4308) {
                  String var12 = classWK.field6689.method12201(var1, (byte)-78);
                  if (null != var12) {
                     classKB.method6492(
                        var12, var1.field4307, 25, 0, -1976660504 * var1.field4258, var1.field4327 * 1446734397, var1.field4317 * 396419694, -2132978866
                     );
                  }
               }

               String var13 = classVV.method12200(classWK.field6689, var1, var10, -1760527185);
               if (null != var13) {
                  int var4;
                  int var5;
                  if (var10 > 2085260293 * var1.field4308) {
                     var5 = 926126659;
                     var4 = classKB.method6492(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        -579896293 * var1.field4258,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        -1947879650
                     );
                  } else {
                     var5 = 57;
                     var4 = Friend.method10203(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        var1.field4258 * -1807322069,
                        -1163656551 * var1.field4327,
                        var1.field4317 * -257059502,
                        var1.field4371,
                        0,
                        -324749371
                     );
                  }

                  if (null != var1.field4351 && var10 < var1.field4351.length && var1.field4351[var10] != null) {
                     String[] var6 = var1.field4351[var10];

                     for (int var7 = var6.length - 1; var7 >= 0; var7--) {
                        int var8 = var7 + 1 << 16 | var10 + 1;
                        if (var6[var7] != null && !var6[var7].isEmpty()) {
                           classZO.method14128(
                              var4,
                              var6[var7],
                              "",
                              var5,
                              var8,
                              458369137 * var1.field4258,
                              var1.field4327 * -1163656551,
                              -1614165918 * var1.field4317,
                              0,
                              -1739941876
                           );
                        }
                     }
                  }
               }
            }

            int var14 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var11 = 0 != (var14 & 1);
            if (var11) {
               classTA.method10774(classKK.field3775, "", 30, 0, -1112617167 * var1.field4258, -1163656551 * var1.field4327, (byte)10);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ar")
   @Override
   public void vmethod326(Widget var1) {
      if (1 == var1.field4245 * -851058883) {
         classKB.method6492(var1.field4316, "", 24, 0, 0, -1163656551 * var1.field4327, var1.field4317 * -1935250509, -1915855589);
      }

      if (-851058883 * var1.field4245 == 2 && !client.field980) {
         String var2 = classWK.field6689.method12201(var1, (byte)-117);
         if (null != var2) {
            classKB.method6492(
               var2, classQY.method9885(65280, (byte)97) + var1.field4315, 25, 0, -1, 94792626 * var1.field4327, -2099944960 * var1.field4317, -2068459732
            );
         }
      }

      if (3 == -851058883 * var1.field4245) {
         classTA.method10774(classKK.field3853, "", 26, 0, 0, var1.field4327 * -1775087592, (byte)3);
      }

      if (var1.field4245 * -851058883 == 4) {
         classTA.method10774(var1.field4316, "", 28, 0, 0, var1.field4327 * -223408938, (byte)-18);
      }

      if (5 == -851058883 * var1.field4245) {
         classTA.method10774(var1.field4316, "", 29, 0, 0, var1.field4327 * 964775103, (byte)47);
      }

      if (-502014269 * var1.field4245 == 6 && classWK.field6689.method12234((byte)14)) {
         classTA.method10774(var1.field4316, "", 30, 0, -1, var1.field4327 * 414843339, (byte)65);
      }

      if (var1.field4273) {
         if (client.field980) {
            int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var9 = (var3 >> 21 & 1) != 0;
            if (var9 && (1547634457 * classLF.field4129 & 32) == 32) {
               classKB.method6492(
                  client.field925,
                  client.field984 + " " + classDO.field1588 + " " + var1.field4307,
                  58,
                  0,
                  var1.field4258 * 63131743,
                  -889012711 * var1.field4327,
                  var1.field4317 * 2073671441,
                  -1980877138
               );
            }
         } else {
            for (int var10 = -1549650590; var10 >= 0; var10--) {
               if (var10 == 2085260293 * var1.field4308) {
                  String var12 = classWK.field6689.method12201(var1, (byte)-54);
                  if (null != var12) {
                     classKB.method6492(
                        var12, var1.field4307, 25, 0, 63131743 * var1.field4258, var1.field4327 * 132602113, var1.field4317 * -1163952009, -1994122768
                     );
                  }
               }

               String var13 = classVV.method12200(classWK.field6689, var1, var10, -1760527185);
               if (null != var13) {
                  int var4;
                  int var5;
                  if (var10 > 2085260293 * var1.field4308) {
                     var5 = 1007;
                     var4 = classKB.method6492(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        63131743 * var1.field4258,
                        1683958939 * var1.field4327,
                        var1.field4317 * 1638715783,
                        -2117526800
                     );
                  } else {
                     var5 = 1395894348;
                     var4 = Friend.method10203(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        var1.field4258 * 63131743,
                        -58237279 * var1.field4327,
                        var1.field4317 * 2073671441,
                        var1.field4371,
                        0,
                        -324749371
                     );
                  }

                  if (null != var1.field4351 && var10 < var1.field4351.length && var1.field4351[var10] != null) {
                     String[] var6 = var1.field4351[var10];

                     for (int var7 = var6.length - 1; var7 >= 0; var7--) {
                        int var8 = var7 + 1 << 16 | var10 + 1;
                        if (var6[var7] != null && !var6[var7].isEmpty()) {
                           classZO.method14128(
                              var4,
                              var6[var7],
                              "",
                              var5,
                              var8,
                              2101716183 * var1.field4258,
                              var1.field4327 * 1761621469,
                              2073671441 * var1.field4317,
                              0,
                              -1739941876
                           );
                        }
                     }
                  }
               }
            }

            int var14 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var11 = 0 != (var14 & 1);
            if (var11) {
               classTA.method10774(classKK.field3775, "", 30, 0, 63131743 * var1.field4258, -491203284 * var1.field4327, (byte)58);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("bg")
   @Override
   public void vmethod350(ScriptEvent var1) {
      ItemLayer.method3976(var1, 5000000, 0, (byte)-6);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bi")
   @Override
   public void vmethod364(int var1, int var2) {
      classBW.method1338(var1, var2, 851336019);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   @Override
   public void vmethod318() {
      classEY.method4149((byte)-80);
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("av")
   @Override
   public void vmethod335(int var1, int var2, int var3, int var4, String var5, String var6) {
      classBQ.method1184(var1, var2, var3, var4, var5, var6, -2085845878);
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;)V")
   @ObfuscatedName("bv")
   @Override
   public void vmethod330(int var1, int var2, int var3, int var4, String var5) {
      classEA.method3815(var1, var2, var3, var4, var5, -425967447);
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;)V")
   @ObfuscatedName("bd")
   @Override
   public void vmethod331(int var1, int var2, int var3, int var4, String var5) {
      classEA.method3815(var1, var2, var3, var4, var5, -1477558090);
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("bj")
   @Override
   public void vmethod351(ScriptEvent var1) {
      ItemLayer.method3976(var1, 539961741, 0, (byte)-60);
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("at")
   @Override
   public void vmethod336(int var1, int var2, int var3, int var4, String var5, String var6) {
      classBQ.method1184(var1, var2, var3, var4, var5, var6, -2085845878);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   @Override
   public void vmethod342() {
      classBT.method1245(2113559562);
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("br")
   @Override
   public void vmethod348(ScriptEvent var1) {
      classSZ.method10771(var1, -1593182478);
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod354(Widget var1, byte var2) {
      try {
         if (null != var1) {
            if (var2 != 127) {
               throw new IllegalStateException();
            }

            if (Widget.method7322(var1, -1626603432) == null) {
               if (var2 != 127) {
                  throw new IllegalStateException();
               }
            } else {
               Widget.method7322(var1, -1626603432).method7472((byte)1).method7741(1, Widget.method7322(var1, -1626603432).method7521(-662365790), (byte)10);
               switch (Widget.method7305(var1, 1614596444)) {
                  case 0:
                     classUF.method11359(var1.method7313(1730107948), (byte)19);
                     break;
                  case 1:
                     int var4 = classVV.method12246(classWK.field6689, var1, (byte)0);
                     boolean var10000;
                     if ((var4 >> 22 & 1) != 0) {
                        if (var2 != 127) {
                           throw new IllegalStateException();
                        }

                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     boolean var3 = var10000;
                     if (var3) {
                        int[] var5 = Widget.method7319(var1, (byte)106);
                        if (var5 != null) {
                           if (var2 != 127) {
                              throw new IllegalStateException();
                           }

                           classJL var6 = classEF.method3863(classJS.IF_CRMVIEW_OP, client.field796.field1533, (byte)-1);
                           var6.field3341.method13037(var5[1], 1932252672);
                           var6.field3341.method13284(var5[0], 730941790);
                           classXY.method13290(var6.field3341, var5[2], -3050263);
                           var6.field3341.method13037(Widget.method7310(var1, (byte)-3), -209054070);
                           classXY.method13242(var6.field3341, 63131743 * var1.field4258, (byte)16);
                           var6.field3341.method13037(-1163656551 * var1.field4327, -528334131);
                           client.field796.method3374(var6, -1301097035);
                        }
                     }
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "de.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bp")
   @Override
   public void vmethod346(int var1, int var2) {
      classON.method8649(var1, var2, (byte)-82);
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("bo")
   @Override
   public void vmethod327(Widget var1) {
      if (1 == var1.field4245 * -851058883) {
         classKB.method6492(var1.field4316, "", 24, 0, 0, -1163656551 * var1.field4327, var1.field4317 * 2073671441, -2002789384);
      }

      if (-851058883 * var1.field4245 == 2 && !client.field980) {
         String var2 = classWK.field6689.method12201(var1, (byte)-101);
         if (null != var2) {
            classKB.method6492(
               var2, classQY.method9885(65280, (byte)78) + var1.field4315, 25, 0, -1, -1163656551 * var1.field4327, 2073671441 * var1.field4317, -2028942514
            );
         }
      }

      if (3 == -851058883 * var1.field4245) {
         classTA.method10774(classKK.field3853, "", 26, 0, 0, var1.field4327 * -1163656551, (byte)33);
      }

      if (var1.field4245 * -851058883 == 4) {
         classTA.method10774(var1.field4316, "", 28, 0, 0, var1.field4327 * -1163656551, (byte)-53);
      }

      if (5 == -851058883 * var1.field4245) {
         classTA.method10774(var1.field4316, "", 29, 0, 0, var1.field4327 * -1163656551, (byte)111);
      }

      if (-851058883 * var1.field4245 == 6 && classWK.field6689.method12234((byte)68)) {
         classTA.method10774(var1.field4316, "", 30, 0, -1, var1.field4327 * -1163656551, (byte)40);
      }

      if (var1.field4273) {
         if (client.field980) {
            int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var9 = (var3 >> 21 & 1) != 0;
            if (var9 && (1547634457 * classLF.field4129 & 32) == 32) {
               classKB.method6492(
                  client.field925,
                  client.field984 + " " + classDO.field1588 + " " + var1.field4307,
                  58,
                  0,
                  var1.field4258 * 63131743,
                  -1163656551 * var1.field4327,
                  var1.field4317 * 2073671441,
                  -1994913413
               );
            }
         } else {
            for (int var10 = 31; var10 >= 0; var10--) {
               if (var10 == 2085260293 * var1.field4308) {
                  String var12 = classWK.field6689.method12201(var1, (byte)-85);
                  if (null != var12) {
                     classKB.method6492(
                        var12, var1.field4307, 25, 0, 63131743 * var1.field4258, var1.field4327 * -1163656551, var1.field4317 * 2073671441, -2107349943
                     );
                  }
               }

               String var13 = classVV.method12200(classWK.field6689, var1, var10, -1760527185);
               if (null != var13) {
                  int var4;
                  short var5;
                  if (var10 > 2085260293 * var1.field4308) {
                     var5 = 1007;
                     var4 = classKB.method6492(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        63131743 * var1.field4258,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        -2026467997
                     );
                  } else {
                     var5 = 57;
                     var4 = Friend.method10203(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        var1.field4258 * 63131743,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        var1.field4371,
                        0,
                        -324749371
                     );
                  }

                  if (null != var1.field4351 && var10 < var1.field4351.length && var1.field4351[var10] != null) {
                     String[] var6 = var1.field4351[var10];

                     for (int var7 = var6.length - 1; var7 >= 0; var7--) {
                        int var8 = var7 + 1 << 16 | var10 + 1;
                        if (var6[var7] != null && !var6[var7].isEmpty()) {
                           classZO.method14128(
                              var4,
                              var6[var7],
                              "",
                              var5,
                              var8,
                              63131743 * var1.field4258,
                              var1.field4327 * -1163656551,
                              2073671441 * var1.field4317,
                              0,
                              -1739941876
                           );
                        }
                     }
                  }
               }
            }

            int var14 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var11 = 0 != (var14 & 1);
            if (var11) {
               classTA.method10774(classKK.field3775, "", 30, 0, 63131743 * var1.field4258, -1163656551 * var1.field4327, (byte)-7);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("aw")
   @Override
   public void vmethod328(Widget var1) {
      if (1 == var1.field4245 * -851058883) {
         classKB.method6492(var1.field4316, "", 24, 0, 0, -1163656551 * var1.field4327, var1.field4317 * 2073671441, -2029541147);
      }

      if (-851058883 * var1.field4245 == 2 && !client.field980) {
         String var2 = classWK.field6689.method12201(var1, (byte)-102);
         if (null != var2) {
            classKB.method6492(
               var2, classQY.method9885(65280, (byte)124) + var1.field4315, 25, 0, -1, -1163656551 * var1.field4327, 2073671441 * var1.field4317, -2118604915
            );
         }
      }

      if (3 == -851058883 * var1.field4245) {
         classTA.method10774(classKK.field3853, "", 26, 0, 0, var1.field4327 * -1163656551, (byte)39);
      }

      if (var1.field4245 * -851058883 == 4) {
         classTA.method10774(var1.field4316, "", 28, 0, 0, var1.field4327 * -1163656551, (byte)22);
      }

      if (5 == -851058883 * var1.field4245) {
         classTA.method10774(var1.field4316, "", 29, 0, 0, var1.field4327 * -1163656551, (byte)33);
      }

      if (-851058883 * var1.field4245 == 6 && classWK.field6689.method12234((byte)52)) {
         classTA.method10774(var1.field4316, "", 30, 0, -1, var1.field4327 * -1163656551, (byte)-32);
      }

      if (var1.field4273) {
         if (client.field980) {
            int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var9 = (var3 >> 21 & 1) != 0;
            if (var9 && (1547634457 * classLF.field4129 & 32) == 32) {
               classKB.method6492(
                  client.field925,
                  client.field984 + " " + classDO.field1588 + " " + var1.field4307,
                  58,
                  0,
                  var1.field4258 * 63131743,
                  -1163656551 * var1.field4327,
                  var1.field4317 * 2073671441,
                  -1946258191
               );
            }
         } else {
            for (int var10 = 31; var10 >= 0; var10--) {
               if (var10 == 2085260293 * var1.field4308) {
                  String var12 = classWK.field6689.method12201(var1, (byte)-125);
                  if (null != var12) {
                     classKB.method6492(
                        var12, var1.field4307, 25, 0, 63131743 * var1.field4258, var1.field4327 * -1163656551, var1.field4317 * 2073671441, -2026623781
                     );
                  }
               }

               String var13 = classVV.method12200(classWK.field6689, var1, var10, -1760527185);
               if (null != var13) {
                  int var4;
                  short var5;
                  if (var10 > 2085260293 * var1.field4308) {
                     var5 = 1007;
                     var4 = classKB.method6492(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        63131743 * var1.field4258,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        -1930726539
                     );
                  } else {
                     var5 = 57;
                     var4 = Friend.method10203(
                        var13,
                        var1.field4307,
                        var5,
                        1 + var10,
                        var1.field4258 * 63131743,
                        -1163656551 * var1.field4327,
                        var1.field4317 * 2073671441,
                        var1.field4371,
                        0,
                        -324749371
                     );
                  }

                  if (null != var1.field4351 && var10 < var1.field4351.length && var1.field4351[var10] != null) {
                     String[] var6 = var1.field4351[var10];

                     for (int var7 = var6.length - 1; var7 >= 0; var7--) {
                        int var8 = var7 + 1 << 16 | var10 + 1;
                        if (var6[var7] != null && !var6[var7].isEmpty()) {
                           classZO.method14128(
                              var4,
                              var6[var7],
                              "",
                              var5,
                              var8,
                              63131743 * var1.field4258,
                              var1.field4327 * -1163656551,
                              2073671441 * var1.field4317,
                              0,
                              -1739941876
                           );
                        }
                     }
                  }
               }
            }

            int var14 = classVV.method12246(classWK.field6689, var1, (byte)0);
            boolean var11 = 0 != (var14 & 1);
            if (var11) {
               classTA.method10774(classKK.field3775, "", 30, 0, 63131743 * var1.field4258, -1163656551 * var1.field4327, (byte)21);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("bt")
   @Override
   public void vmethod352(ScriptEvent var1) {
      ItemLayer.method3976(var1, 1948865235, 0, (byte)0);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   @Override
   public void vmethod338() {
      ClanChannel.method6234((byte)-5);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   @Override
   public void vmethod339() {
      ClanChannel.method6234((byte)-81);
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ba")
   @Override
   public void vmethod355(Widget var1) {
      if (null != var1 && Widget.method7322(var1, -1626603432) != null) {
         Widget.method7322(var1, -1626603432).method7472((byte)-66).method7741(1, Widget.method7322(var1, -1626603432).method7521(1174920141), (byte)10);
         switch (Widget.method7305(var1, 2100872111)) {
            case 0:
               classUF.method11359(var1.method7313(1971700019), (byte)118);
               break;
            case 1:
               int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
               boolean var2 = (var3 >> 22 & 1) != 0;
               if (var2) {
                  int[] var4 = Widget.method7319(var1, (byte)120);
                  if (var4 != null) {
                     classJL var5 = classEF.method3863(classJS.IF_CRMVIEW_OP, client.field796.field1533, (byte)-50);
                     var5.field3341.method13037(var4[1], 340587824);
                     var5.field3341.method13284(var4[0], 730941790);
                     classXY.method13290(var5.field3341, var4[2], -169980871);
                     var5.field3341.method13037(Widget.method7310(var1, (byte)-118), 1596445826);
                     classXY.method13242(var5.field3341, 63131743 * var1.field4258, (byte)16);
                     var5.field3341.method13037(-1163656551 * var1.field4327, 24552);
                     client.field796.method3374(var5, -1301097035);
                  }
               }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("au")
   @Override
   public void vmethod356(Widget var1) {
      if (null != var1 && Widget.method7322(var1, -1626603432) != null) {
         Widget.method7322(var1, -1626603432).method7472((byte)6).method7741(1, Widget.method7322(var1, -1626603432).method7521(-909541264), (byte)10);
         switch (Widget.method7305(var1, 365562534)) {
            case 0:
               classUF.method11359(var1.method7313(1103888816), (byte)30);
               break;
            case 1:
               int var3 = classVV.method12246(classWK.field6689, var1, (byte)0);
               boolean var2 = (var3 >> 22 & 1) != 0;
               if (var2) {
                  int[] var4 = Widget.method7319(var1, (byte)44);
                  if (var4 != null) {
                     classJL var5 = classEF.method3863(classJS.IF_CRMVIEW_OP, client.field796.field1533, (byte)-25);
                     var5.field3341.method13037(var4[1], -868942297);
                     var5.field3341.method13284(var4[0], 730941790);
                     classXY.method13290(var5.field3341, var4[2], -2007552624);
                     var5.field3341.method13037(Widget.method7310(var1, (byte)-71), 1452091200);
                     classXY.method13242(var5.field3341, 63131743 * var1.field4258, (byte)16);
                     var5.field3341.method13037(-1163656551 * var1.field4327, 2141115132);
                     client.field796.method3374(var5, -1301097035);
                  }
               }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ai")
   @Override
   public void vmethod358(Widget var1) {
      if (var1 == null) {
         client.field909.method4605((byte)79);
      } else {
         classLH var2 = var1.method7334(-122010138);
         if (var2 != null && var2.method7030(-880967958)) {
            client.field909.method4600(var1, (byte)56);
            var2.method6847(true, 2138752399);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ak")
   @Override
   public void vmethod359(Widget var1) {
      if (var1 == null) {
         client.field909.method4605((byte)52);
      } else {
         classLH var2 = var1.method7334(-934857128);
         if (var2 != null && var2.method7030(-880967958)) {
            client.field909.method4600(var1, (byte)70);
            var2.method6847(true, 1991845528);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzr;)V")
   @ObfuscatedName("bm")
   @Override
   public void vmethod353(ScriptEvent var1) {
      ItemLayer.method3976(var1, 5000000, 0, (byte)-114);
   }

   @ObfuscatedSignature(descriptor = "(Llw;Llw;)V")
   @ObfuscatedName("bw")
   @Override
   public void vmethod362(Widget var1, Widget var2) {
      classTM.method10984(var1, var2, 1238625483);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("af")
   @Override
   public void vmethod321(int var1, int var2, int var3, int var4, int var5) {
      try {
         if (0 == client.field946 * -512792823) {
            if (var5 >= 120041229) {
               throw new IllegalStateException();
            }

            if (!client.field980) {
               classQX.method9881(var1 - var3, var2 - var4, 1135279228);
            }
         }

         long var6 = -1L;
         int var8 = 0;
         int var9 = 0;

         while (true) {
            int var11 = classFO.field2348 * 613116963 + 1;
            if (var9 >= var11) {
               if (var6 != -1L) {
                  var9 = (int)(var6 >>> 0 & 127L);
                  var11 = classRC.method9976(var6);
                  Player var34 = classEN.method3954(client.field972 * 927693091, client.field815, -360499318);
                  if (null != var34) {
                     if (var5 >= 120041229) {
                        throw new IllegalStateException();
                     }

                     classHO.method6114(var34, 927693091 * client.field972, var9, var11, var8, (byte)18);
                  }
               }

               return;
            }

            long var12 = classDA.method2866(var9, -1831332723);
            if (var12 == -1L) {
               if (var5 >= 120041229) {
                  throw new IllegalStateException();
               }
            } else {
               label813: {
                  int var14 = (int)(var12 >>> 0 & 127L);
                  int var15 = var14;
                  int var16 = classRC.method9976(var12);
                  int var17 = classCV.method2679(var12);
                  int var18 = classCN.method2542(var12);
                  int var19 = classXO.method12924(var12);
                  WorldView var20 = null;
                  WorldEntity var21 = null;
                  if (0 == var19) {
                     if (var5 >= 120041229) {
                        throw new IllegalStateException();
                     }

                     var20 = classIS.field3051;
                  } else {
                     var21 = (WorldEntity)classIS.field3051.field1698.method13637(var19);
                     if (var21 != null) {
                        if (var5 >= 120041229) {
                           throw new IllegalStateException();
                        }

                        if (var21.method10601(1954640952)) {
                           if (var5 >= 120041229) {
                              throw new IllegalStateException();
                           }
                           break label813;
                        }

                        var20 = var21.field6010;
                     }
                  }

                  if (null == var20) {
                     if (var5 >= 120041229) {
                        return;
                     }
                  } else {
                     int var23 = -1;
                     if (client.field847 * -2034209657 == var19) {
                        if (var5 >= 120041229) {
                           throw new IllegalStateException();
                        }

                        var23 = var20.field1708 * -483624883;
                     } else if (0 == client.field847 * -2034209657) {
                        if (var5 >= 120041229) {
                           throw new IllegalStateException();
                        }

                        var23 = var21.field6024.method8500(-1821270104);
                     } else {
                        WorldEntity var24 = EnumComposition.method8383((byte)0);
                        if (var24 != null) {
                           if (var5 >= 120041229) {
                              throw new IllegalStateException();
                           }

                           WorldView var25 = var24.field6010;
                           if (-483624883 * var25.field1708 == var24.field6024.method8500(-1821270104)) {
                              if (var5 >= 120041229) {
                                 throw new IllegalStateException();
                              }

                              if (0 == var19) {
                                 if (var5 >= 120041229) {
                                    throw new IllegalStateException();
                                 }

                                 var23 = var24.vmethod258((byte)-60);
                              } else {
                                 var23 = var21.field6024.method8500(-1821270104);
                              }
                           }
                        }
                     }

                     var23 = var23;
                     if (var23 == -1) {
                        if (var5 >= 120041229) {
                           return;
                        }
                     } else {
                        label815: {
                           if (2 == var17) {
                              if (var5 >= 120041229) {
                                 throw new IllegalStateException();
                              }

                              label773:
                              if (classKK.method6532(var20.field1687, var23, var14, var16, var12) >= 0) {
                                 if (var5 >= 120041229) {
                                    return;
                                 }

                                 ObjectComposition var37 = classMU.method7771(var18, 1870995068);
                                 if (var37.field4928 != null) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    var37 = ObjectComposition.method8585(var37, 659445715);
                                 }

                                 if (null == var37) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }
                                    break label815;
                                 }

                                 classCD var42 = null;

                                 for (classCD var26 = (classCD)var20.field1688.method7957(); var26 != null; var26 = (classCD)var20.field1688.method7965()) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    if (var23 == -1436432311 * var26.field679 && var15 == -1779660971 * var26.field676) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       if (var16 == -522576623 * var26.field680) {
                                          if (var5 >= 120041229) {
                                             return;
                                          }

                                          if (var18 == 1490231037 * var26.field675) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             var42 = var26;
                                             break;
                                          }
                                       }
                                    }
                                 }

                                 if (!classIV.field3069) {
                                    if (var5 >= 120041229) {
                                       return;
                                    }

                                    if (1 == -512792823 * client.field946) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       Friend.method10203(
                                          classKK.field3772,
                                          client.field805
                                             + " "
                                             + classDO.field1588
                                             + " "
                                             + classQY.method9885(65535, (byte)111)
                                             + ObjectComposition.method8624(var37, (short)-12777),
                                          1,
                                          var18,
                                          var15,
                                          var16,
                                          -1,
                                          false,
                                          var19,
                                          -324749371
                                       );
                                       break label773;
                                    }
                                 }

                                 if (client.field980) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    if (!classIV.field3069) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       if ((1547634457 * classLF.field4129 & 4) == 4) {
                                          Friend.method10203(
                                             client.field925,
                                             client.field984
                                                + " "
                                                + classDO.field1588
                                                + " "
                                                + classQY.method9885(65535, (byte)109)
                                                + ObjectComposition.method8624(var37, (short)-4192),
                                             2,
                                             var18,
                                             var15,
                                             var16,
                                             -1,
                                             false,
                                             var19,
                                             -324749371
                                          );
                                       }
                                    }
                                 } else {
                                    if (var37.method8548((byte)97).vmethod546((byte)0)) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       for (int var49 = 4; var49 >= 0; var49--) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          if (!classIV.field3069) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             if (var42 != null) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                if (!var42.method1413(var49, -2035539133)) {
                                                   if (var5 >= 120041229) {
                                                      throw new IllegalStateException();
                                                   }
                                                   continue;
                                                }
                                             }

                                             String var27 = var37.method8548((byte)92).vmethod551(var49, classLB.field4088, (byte)-13);
                                             if (var42 != null && var42.method1417(var49, 156968958)) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                var27 = var42.method1423(var49, -1148961647);
                                             }

                                             if (client.field823) {
                                                if (var5 >= 120041229) {
                                                   return;
                                                }

                                                var27 = classQM.method9703(var27, var49, (byte)-29);
                                             }

                                             if (var27 != null) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                short var28 = 0;
                                                if (0 == var49) {
                                                   if (var5 >= 120041229) {
                                                      throw new IllegalStateException();
                                                   }

                                                   var28 = 3;
                                                }

                                                if (1 == var49) {
                                                   if (var5 >= 120041229) {
                                                      throw new IllegalStateException();
                                                   }

                                                   var28 = 4;
                                                }

                                                if (var49 == 2) {
                                                   if (var5 >= 120041229) {
                                                      return;
                                                   }

                                                   var28 = 5;
                                                }

                                                if (3 == var49) {
                                                   if (var5 >= 120041229) {
                                                      return;
                                                   }

                                                   var28 = 6;
                                                }

                                                if (4 == var49) {
                                                   var28 = 1001;
                                                }

                                                int var29 = Friend.method10203(
                                                   var27,
                                                   classQY.method9885(65535, (byte)75) + ObjectComposition.method8624(var37, (short)-24839),
                                                   var28,
                                                   var18,
                                                   var15,
                                                   var16,
                                                   -1,
                                                   false,
                                                   var19,
                                                   -324749371
                                                );
                                                classJC.method6396(
                                                   var29,
                                                   var37.method8548((byte)47),
                                                   var49,
                                                   classQY.method9885(65535, (byte)95) + ObjectComposition.method8624(var37, (short)-4775),
                                                   var28,
                                                   var18,
                                                   var15,
                                                   var16,
                                                   var19,
                                                   -1533141989
                                                );
                                             }
                                          }
                                       }
                                    }

                                    Friend.method10203(
                                       classKK.field3773,
                                       classQY.method9885(65535, (byte)31) + ObjectComposition.method8624(var37, (short)-23232),
                                       1002,
                                       -381960671 * var37.field4920,
                                       var15,
                                       var16,
                                       -1,
                                       false,
                                       var19,
                                       -324749371
                                    );
                                 }
                              }
                           }

                           if (1 == var17) {
                              if (var5 >= 120041229) {
                                 return;
                              }

                              NPC var38 = (NPC)IndexedObjectSet.method13449(var20.field1696, var18);
                              if (null == var38) {
                                 if (var5 >= 120041229) {
                                    return;
                                 }
                                 break label815;
                              }

                              if (-1216348145 * var38.field1579.field5282 == 1) {
                                 if (var5 >= 120041229) {
                                    throw new IllegalStateException();
                                 }

                                 if (64 == (var38.field1484 * -1547553299 & 127)) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    if ((-1272026483 * var38.field1486 & 127) == 64) {
                                       for (int var43 = 0; var43 < var20.field1695.method14050((byte)10); var43++) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          NPC var50 = (NPC)IndexedObjectSet.method13449(var20.field1696, var20.field1695.method14048(var43, 1562229847));
                                          if (null != var50) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             if (var50 != var38) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                if (1 == -1216348145 * var50.field1579.field5282
                                                   && var50.field1484 * -1547553299 == -1547553299 * var38.field1484) {
                                                   if (var5 >= 120041229) {
                                                      return;
                                                   }

                                                   if (var38.field1486 * -1272026483 == -1272026483 * var50.field1486) {
                                                      if (var5 >= 120041229) {
                                                         throw new IllegalStateException();
                                                      }

                                                      WorldMapRenderer.method5822(var50, var50.field1478 * -1315528093, var15, var16, var19, -1340115537);
                                                   }
                                                }
                                             }
                                          }
                                       }

                                       int var44 = -2087374717 * client.field838.field1417;
                                       int[] var51 = client.field838.field1421;

                                       for (int var56 = 0; var56 < var44; var56++) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          Player var60 = (Player)IndexedObjectSet.method13449(var20.field1692, var51[var56]);
                                          if (var60 != null) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             if (var38.field1484 * -1547553299 == -1547553299 * var60.field1484) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                if (-1272026483 * var38.field1486 == -1272026483 * var60.field1486) {
                                                   if (var5 >= 120041229) {
                                                      return;
                                                   }

                                                   classHO.method6114(var60, var51[var56], var15, var16, var19, (byte)35);
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }

                              WorldMapRenderer.method5822(var38, var18, var15, var16, var19, -832884634);
                           }

                           if (var17 == 0) {
                              if (var5 >= 120041229) {
                                 throw new IllegalStateException();
                              }

                              Player var39 = (Player)IndexedObjectSet.method13449(var20.field1692, var18);
                              if (var39 == null) {
                                 break label815;
                              }

                              if ((-1547553299 * var39.field1484 & 127) == 64) {
                                 if (var5 >= 120041229) {
                                    throw new IllegalStateException();
                                 }

                                 if (64 == (var39.field1486 * -1272026483 & 127)) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    for (int var45 = 0; var45 < var20.field1695.method14050((byte)10); var45++) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       NPC var52 = (NPC)IndexedObjectSet.method13449(var20.field1696, var20.field1695.method14048(var45, 1917329195));
                                       if (null != var52
                                          && 1 == -1216348145 * var52.field1579.field5282
                                          && -1547553299 * var39.field1484 == var52.field1484 * -1547553299
                                          && -1272026483 * var39.field1486 == var52.field1486 * -1272026483) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          WorldMapRenderer.method5822(var52, var20.field1695.method14048(var45, 1717660553), var15, var16, var19, -1159596289);
                                       }
                                    }

                                    int var46 = client.field838.field1417 * -2087374717;
                                    int[] var53 = client.field838.field1421;

                                    for (int var57 = 0; var57 < var46; var57++) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       Player var61 = (Player)IndexedObjectSet.method13449(var20.field1692, var53[var57]);
                                       if (var61 != null) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          if (var61 != var39) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             if (-1547553299 * var61.field1484 == var39.field1484 * -1547553299) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                if (-1272026483 * var61.field1486 == -1272026483 * var39.field1486) {
                                                   classHO.method6114(var61, var53[var57], var15, var16, var19, (byte)50);
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }

                              if (927693091 * client.field972 != var18) {
                                 if (var5 >= 120041229) {
                                    return;
                                 }

                                 classHO.method6114(var39, var18, var15, var16, var19, (byte)126);
                              } else {
                                 var6 = var12;
                                 var8 = var19;
                              }
                           }

                           if (var17 == 3) {
                              if (var5 >= 120041229) {
                                 return;
                              }

                              Deque2 var40 = var20.field1699[var23][var15][var16];
                              if (var40 != null) {
                                 if (var5 >= 120041229) {
                                    throw new IllegalStateException();
                                 }

                                 for (TileItem var47 = (TileItem)Deque2.method7962(var40); var47 != null; var47 = (TileItem)Deque2.method7968(var40)) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    ItemComposition var54 = classOB.method8329(-1370204663 * var47.field1549, (byte)41);
                                    if (!WidgetNode.field5278) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       if (client.field946 * -512792823 == 1) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          Friend.method10203(
                                             classKK.field3772,
                                             client.field805
                                                + " "
                                                + classDO.field1588
                                                + " "
                                                + classQY.method9885(16748608, (byte)115)
                                                + var54.method8741(1248604557),
                                             16,
                                             -1370204663 * var47.field1549,
                                             var15,
                                             var16,
                                             -1,
                                             false,
                                             var19,
                                             -324749371
                                          );
                                          continue;
                                       }
                                    }

                                    if (client.field980) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       if (!WidgetNode.field5278) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          if ((1547634457 * classLF.field4129 & 1) == 1) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             Friend.method10203(
                                                client.field925,
                                                client.field984
                                                   + " "
                                                   + classDO.field1588
                                                   + " "
                                                   + classQY.method9885(16748608, (byte)117)
                                                   + var54.method8741(1248604557),
                                                17,
                                                -1370204663 * var47.field1549,
                                                var15,
                                                var16,
                                                -1,
                                                false,
                                                var19,
                                                -324749371
                                             );
                                          }
                                       }
                                    } else {
                                       for (int var58 = 4; var58 >= 0; var58--) {
                                          if (var5 >= 120041229) {
                                             throw new IllegalStateException();
                                          }

                                          if (!WidgetNode.field5278) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             if (!TileItem.method3407(var47, var58, 1443781489)) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }
                                             } else {
                                                String var62 = ItemComposition.method8666(var54, (byte)108).vmethod551(var58, classLB.field4088, (byte)46);
                                                if (client.field823) {
                                                   if (var5 >= 120041229) {
                                                      throw new IllegalStateException();
                                                   }

                                                   var62 = classQM.method9703(var62, var58, (byte)-3);
                                                }

                                                if (var62 != null) {
                                                   if (var5 >= 120041229) {
                                                      throw new IllegalStateException();
                                                   }

                                                   byte var63 = 0;
                                                   if (0 == var58) {
                                                      if (var5 >= 120041229) {
                                                         throw new IllegalStateException();
                                                      }

                                                      var63 = 18;
                                                   }

                                                   if (1 == var58) {
                                                      if (var5 >= 120041229) {
                                                         throw new IllegalStateException();
                                                      }

                                                      var63 = 19;
                                                   }

                                                   if (var58 == 2) {
                                                      var63 = 20;
                                                   }

                                                   if (3 == var58) {
                                                      if (var5 >= 120041229) {
                                                         return;
                                                      }

                                                      var63 = 21;
                                                   }

                                                   if (var58 == 4) {
                                                      var63 = 22;
                                                   }

                                                   int var30 = Friend.method10203(
                                                      var62,
                                                      classQY.method9885(16748608, (byte)17) + var54.method8741(1248604557),
                                                      var63,
                                                      -1370204663 * var47.field1549,
                                                      var15,
                                                      var16,
                                                      -1,
                                                      false,
                                                      var19,
                                                      -324749371
                                                   );
                                                   classJC.method6396(
                                                      var30,
                                                      ItemComposition.method8666(var54, (byte)45),
                                                      var58,
                                                      classQY.method9885(16748608, (byte)60) + var54.method8741(1248604557),
                                                      var63,
                                                      var47.field1549 * -1370204663,
                                                      var15,
                                                      var16,
                                                      var19,
                                                      -1533141989
                                                   );
                                                }
                                             }
                                          }
                                       }

                                       Friend.method10203(
                                          classKK.field3773,
                                          classQY.method9885(16748608, (byte)124) + var54.method8741(1248604557),
                                          1004,
                                          -1370204663 * var47.field1549,
                                          var15,
                                          var16,
                                          -1,
                                          false,
                                          var19,
                                          -324749371
                                       );
                                    }
                                 }
                              }
                           }

                           if (var17 == 4) {
                              if (var5 >= 120041229) {
                                 return;
                              }

                              var21 = (WorldEntity)var20.field1698.method13637(var18);
                              if (var21 == null) {
                                 if (var5 >= 120041229) {
                                    throw new IllegalStateException();
                                 }
                              } else {
                                 WorldEntityConfig var41 = var21.field6024;
                                 if (1 == client.field946 * -512792823) {
                                    if (var5 >= 120041229) {
                                       throw new IllegalStateException();
                                    }

                                    Friend.method10203(
                                       classKK.field3772,
                                       client.field805 + " " + classDO.field1588 + " " + classQY.method9885(15574765, (byte)120) + var41.field4879,
                                       61,
                                       var18,
                                       var15,
                                       var16,
                                       -1,
                                       false,
                                       var19,
                                       -324749371
                                    );
                                 } else if (!client.field980) {
                                    String[] var48 = var41.field4881;
                                    if (var48 != null) {
                                       if (var5 >= 120041229) {
                                          throw new IllegalStateException();
                                       }

                                       for (int var55 = 4; var55 >= 0; var55--) {
                                          if (var5 >= 120041229) {
                                             return;
                                          }

                                          if (!var21.method10631(var55, (byte)127)) {
                                             if (var5 >= 120041229) {
                                                return;
                                             }
                                          } else if (var48[var55] != null) {
                                             if (var5 >= 120041229) {
                                                throw new IllegalStateException();
                                             }

                                             byte var59 = 0;
                                             if (var55 == 0) {
                                                if (var5 >= 120041229) {
                                                   return;
                                                }

                                                var59 = 63;
                                             }

                                             if (1 == var55) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                var59 = 64;
                                             }

                                             if (2 == var55) {
                                                var59 = 65;
                                             }

                                             if (var55 == 3) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                var59 = 66;
                                             }

                                             if (var55 == 4) {
                                                if (var5 >= 120041229) {
                                                   throw new IllegalStateException();
                                                }

                                                var59 = 67;
                                             }

                                             Friend.method10203(
                                                var48[var55],
                                                classQY.method9885(15574765, (byte)56) + var41.field4879,
                                                var59,
                                                var18,
                                                var15,
                                                var16,
                                                -1,
                                                false,
                                                var19,
                                                -324749371
                                             );
                                          }
                                       }
                                    }

                                    Friend.method10203(
                                       classKK.field3773,
                                       classQY.method9885(15574765, (byte)18) + var41.field4879,
                                       1013,
                                       WorldEntityConfig.method8498(var41, (byte)-80),
                                       var15,
                                       var16,
                                       -1,
                                       false,
                                       var19,
                                       -324749371
                                    );
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            var9++;
         }
      } catch (RuntimeException var31) {
         throw classEG.method3899(var31, "de.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("be")
   @Override
   public void vmethod365(int var1, int var2) {
      classBW.method1338(var1, var2, 1359175481);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("by")
   @Override
   public void vmethod366(int var1, int var2) {
      classBW.method1338(var1, var2, 1030015873);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bq")
   @Override
   public void vmethod367(int var1, int var2) {
      classBW.method1338(var1, var2, 1894478385);
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ax")
   @Override
   public void vmethod343(byte var1) {
      try {
         classBT.method1245(1606376156);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "de.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   @Override
   public void vmethod319() {
      classEY.method4149((byte)-85);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ap")
   @Override
   public void vmethod322(int var1, int var2, int var3, int var4) {
      if (0 == client.field946 * -512792823 && !client.field980) {
         classQX.method9881(var1 - var3, var2 - var4, -1309353332);
      }

      long var5 = -1L;
      int var7 = 0;
      int var8 = 0;

      while (true) {
         int var10 = classFO.field2348 * 613116963 + 1;
         if (var8 >= var10) {
            if (var5 != -1L) {
               var8 = (int)(var5 >>> 0 & 127L);
               var10 = classRC.method9976(var5);
               Player var32 = classEN.method3954(client.field972 * 927693091, client.field815, -237129264);
               if (null != var32) {
                  classHO.method6114(var32, 927693091 * client.field972, var8, var10, var7, (byte)96);
               }
            }

            return;
         }

         long var11 = classDA.method2866(var8, -878362327);
         label394:
         if (var11 != -1L) {
            int var13 = (int)(var11 >>> 0 & 127L);
            int var14 = var13;
            int var15 = classRC.method9976(var11);
            int var16 = classCV.method2679(var11);
            int var17 = classCN.method2542(var11);
            int var18 = classXO.method12924(var11);
            WorldView var19 = null;
            WorldEntity var20 = null;
            if (0 == var18) {
               var19 = classIS.field3051;
            } else {
               var20 = (WorldEntity)classIS.field3051.field1698.method13637(var18);
               if (var20 != null) {
                  if (var20.method10601(1925836606)) {
                     break label394;
                  }

                  var19 = var20.field6010;
               }
            }

            if (null != var19) {
               int var22 = -1;
               if (client.field847 * -2034209657 == var18) {
                  var22 = var19.field1708 * -483624883;
               } else if (0 == client.field847 * -2034209657) {
                  var22 = var20.field6024.method8500(-1821270104);
               } else {
                  WorldEntity var23 = EnumComposition.method8383((byte)0);
                  if (var23 != null) {
                     WorldView var24 = var23.field6010;
                     if (-483624883 * var24.field1708 == var23.field6024.method8500(-1821270104)) {
                        if (0 == var18) {
                           var22 = var23.vmethod258((byte)-85);
                        } else {
                           var22 = var20.field6024.method8500(-1821270104);
                        }
                     }
                  }
               }

               var22 = var22;
               label385:
               if (var22 != -1) {
                  if (2 == var16 && classKK.method6532(var19.field1687, var22, var13, var15, var11) >= 0) {
                     ObjectComposition var35 = classMU.method7771(var17, 1561567370);
                     if (var35.field4928 != null) {
                        var35 = ObjectComposition.method8585(var35, -45020966);
                     }

                     if (null == var35) {
                        break label385;
                     }

                     classCD var40 = null;

                     for (classCD var25 = (classCD)var19.field1688.method7957(); var25 != null; var25 = (classCD)var19.field1688.method7965()) {
                        if (var22 == -1436432311 * var25.field679
                           && var14 == -1779660971 * var25.field676
                           && var15 == -522576623 * var25.field680
                           && var17 == 1490231037 * var25.field675) {
                           var40 = var25;
                           break;
                        }
                     }

                     if (!classIV.field3069 && 1 == -512792823 * client.field946) {
                        Friend.method10203(
                           classKK.field3772,
                           client.field805
                              + " "
                              + classDO.field1588
                              + " "
                              + classQY.method9885(65535, (byte)120)
                              + ObjectComposition.method8624(var35, (short)-30543),
                           1,
                           var17,
                           var14,
                           var15,
                           -1,
                           false,
                           var18,
                           -324749371
                        );
                     } else if (client.field980) {
                        if (!classIV.field3069 && (1547634457 * classLF.field4129 & 4) == 4) {
                           Friend.method10203(
                              client.field925,
                              client.field984
                                 + " "
                                 + classDO.field1588
                                 + " "
                                 + classQY.method9885(65535, (byte)87)
                                 + ObjectComposition.method8624(var35, (short)-21592),
                              2,
                              var17,
                              var14,
                              var15,
                              -1,
                              false,
                              var18,
                              -324749371
                           );
                        }
                     } else {
                        if (var35.method8548((byte)86).vmethod546((byte)0)) {
                           for (int var47 = 4; var47 >= 0; var47--) {
                              if (!classIV.field3069 && (var40 == null || var40.method1413(var47, -1066604047))) {
                                 String var26 = var35.method8548((byte)102).vmethod551(var47, classLB.field4088, (byte)-51);
                                 if (var40 != null && var40.method1417(var47, -62587769)) {
                                    var26 = var40.method1423(var47, -616194689);
                                 }

                                 if (client.field823) {
                                    var26 = classQM.method9703(var26, var47, (byte)-9);
                                 }

                                 if (var26 != null) {
                                    short var27 = 0;
                                    if (0 == var47) {
                                       var27 = 3;
                                    }

                                    if (1 == var47) {
                                       var27 = 4;
                                    }

                                    if (var47 == 2) {
                                       var27 = 5;
                                    }

                                    if (3 == var47) {
                                       var27 = 6;
                                    }

                                    if (4 == var47) {
                                       var27 = 1001;
                                    }

                                    int var28 = Friend.method10203(
                                       var26,
                                       classQY.method9885(65535, (byte)61) + ObjectComposition.method8624(var35, (short)-30277),
                                       var27,
                                       var17,
                                       var14,
                                       var15,
                                       -1,
                                       false,
                                       var18,
                                       -324749371
                                    );
                                    classJC.method6396(
                                       var28,
                                       var35.method8548((byte)119),
                                       var47,
                                       classQY.method9885(65535, (byte)76) + ObjectComposition.method8624(var35, (short)-31825),
                                       var27,
                                       var17,
                                       var14,
                                       var15,
                                       var18,
                                       -1533141989
                                    );
                                 }
                              }
                           }
                        }

                        Friend.method10203(
                           classKK.field3773,
                           classQY.method9885(65535, (byte)65) + ObjectComposition.method8624(var35, (short)-8482),
                           1002,
                           -381960671 * var35.field4920,
                           var14,
                           var15,
                           -1,
                           false,
                           var18,
                           -324749371
                        );
                     }
                  }

                  if (1 == var16) {
                     NPC var36 = (NPC)IndexedObjectSet.method13449(var19.field1696, var17);
                     if (null == var36) {
                        break label385;
                     }

                     if (-1216348145 * var36.field1579.field5282 == 1
                        && 64 == (var36.field1484 * -1547553299 & 127)
                        && (-1272026483 * var36.field1486 & 127) == 64) {
                        for (int var41 = 0; var41 < var19.field1695.method14050((byte)10); var41++) {
                           NPC var48 = (NPC)IndexedObjectSet.method13449(var19.field1696, var19.field1695.method14048(var41, 2128307644));
                           if (null != var48
                              && var48 != var36
                              && 1 == -1216348145 * var48.field1579.field5282
                              && var48.field1484 * -1547553299 == -1547553299 * var36.field1484
                              && var36.field1486 * -1272026483 == -1272026483 * var48.field1486) {
                              WorldMapRenderer.method5822(var48, var48.field1478 * -1315528093, var14, var15, var18, -1588262171);
                           }
                        }

                        int var42 = -2087374717 * client.field838.field1417;
                        int[] var49 = client.field838.field1421;

                        for (int var54 = 0; var54 < var42; var54++) {
                           Player var58 = (Player)IndexedObjectSet.method13449(var19.field1692, var49[var54]);
                           if (var58 != null
                              && var36.field1484 * -1547553299 == -1547553299 * var58.field1484
                              && -1272026483 * var36.field1486 == -1272026483 * var58.field1486) {
                              classHO.method6114(var58, var49[var54], var14, var15, var18, (byte)67);
                           }
                        }
                     }

                     WorldMapRenderer.method5822(var36, var17, var14, var15, var18, -1777651759);
                  }

                  if (var16 == 0) {
                     Player var37 = (Player)IndexedObjectSet.method13449(var19.field1692, var17);
                     if (var37 == null) {
                        break label385;
                     }

                     if ((-1547553299 * var37.field1484 & 127) == 64 && 64 == (var37.field1486 * -1272026483 & 127)) {
                        for (int var43 = 0; var43 < var19.field1695.method14050((byte)10); var43++) {
                           NPC var50 = (NPC)IndexedObjectSet.method13449(var19.field1696, var19.field1695.method14048(var43, 1649978556));
                           if (null != var50
                              && 1 == -1216348145 * var50.field1579.field5282
                              && -1547553299 * var37.field1484 == var50.field1484 * -1547553299
                              && -1272026483 * var37.field1486 == var50.field1486 * -1272026483) {
                              WorldMapRenderer.method5822(var50, var19.field1695.method14048(var43, 1699354202), var14, var15, var18, -1921242026);
                           }
                        }

                        int var44 = client.field838.field1417 * -2087374717;
                        int[] var51 = client.field838.field1421;

                        for (int var55 = 0; var55 < var44; var55++) {
                           Player var59 = (Player)IndexedObjectSet.method13449(var19.field1692, var51[var55]);
                           if (var59 != null
                              && var59 != var37
                              && -1547553299 * var59.field1484 == var37.field1484 * -1547553299
                              && -1272026483 * var59.field1486 == -1272026483 * var37.field1486) {
                              classHO.method6114(var59, var51[var55], var14, var15, var18, (byte)8);
                           }
                        }
                     }

                     if (927693091 * client.field972 != var17) {
                        classHO.method6114(var37, var17, var14, var15, var18, (byte)63);
                     } else {
                        var5 = var11;
                        var7 = var18;
                     }
                  }

                  if (var16 == 3) {
                     Deque2 var38 = var19.field1699[var22][var14][var15];
                     if (var38 != null) {
                        for (TileItem var45 = (TileItem)Deque2.method7962(var38); var45 != null; var45 = (TileItem)Deque2.method7968(var38)) {
                           ItemComposition var52 = classOB.method8329(-1370204663 * var45.field1549, (byte)-20);
                           if (!WidgetNode.field5278 && client.field946 * -512792823 == 1) {
                              Friend.method10203(
                                 classKK.field3772,
                                 client.field805 + " " + classDO.field1588 + " " + classQY.method9885(16748608, (byte)54) + var52.method8741(1248604557),
                                 16,
                                 -1370204663 * var45.field1549,
                                 var14,
                                 var15,
                                 -1,
                                 false,
                                 var18,
                                 -324749371
                              );
                           } else if (client.field980) {
                              if (!WidgetNode.field5278 && (1547634457 * classLF.field4129 & 1) == 1) {
                                 Friend.method10203(
                                    client.field925,
                                    client.field984 + " " + classDO.field1588 + " " + classQY.method9885(16748608, (byte)80) + var52.method8741(1248604557),
                                    17,
                                    -1370204663 * var45.field1549,
                                    var14,
                                    var15,
                                    -1,
                                    false,
                                    var18,
                                    -324749371
                                 );
                              }
                           } else {
                              for (int var56 = 4; var56 >= 0; var56--) {
                                 if (!WidgetNode.field5278 && TileItem.method3407(var45, var56, 1837644683)) {
                                    String var60 = ItemComposition.method8666(var52, (byte)40).vmethod551(var56, classLB.field4088, (byte)17);
                                    if (client.field823) {
                                       var60 = classQM.method9703(var60, var56, (byte)-10);
                                    }

                                    if (var60 != null) {
                                       byte var61 = 0;
                                       if (0 == var56) {
                                          var61 = 18;
                                       }

                                       if (1 == var56) {
                                          var61 = 19;
                                       }

                                       if (var56 == 2) {
                                          var61 = 20;
                                       }

                                       if (3 == var56) {
                                          var61 = 21;
                                       }

                                       if (var56 == 4) {
                                          var61 = 22;
                                       }

                                       int var29 = Friend.method10203(
                                          var60,
                                          classQY.method9885(16748608, (byte)81) + var52.method8741(1248604557),
                                          var61,
                                          -1370204663 * var45.field1549,
                                          var14,
                                          var15,
                                          -1,
                                          false,
                                          var18,
                                          -324749371
                                       );
                                       classJC.method6396(
                                          var29,
                                          ItemComposition.method8666(var52, (byte)57),
                                          var56,
                                          classQY.method9885(16748608, (byte)18) + var52.method8741(1248604557),
                                          var61,
                                          var45.field1549 * -1370204663,
                                          var14,
                                          var15,
                                          var18,
                                          -1533141989
                                       );
                                    }
                                 }
                              }

                              Friend.method10203(
                                 classKK.field3773,
                                 classQY.method9885(16748608, (byte)15) + var52.method8741(1248604557),
                                 1004,
                                 -1370204663 * var45.field1549,
                                 var14,
                                 var15,
                                 -1,
                                 false,
                                 var18,
                                 -324749371
                              );
                           }
                        }
                     }
                  }

                  if (var16 == 4) {
                     var20 = (WorldEntity)var19.field1698.method13637(var17);
                     if (var20 != null) {
                        WorldEntityConfig var39 = var20.field6024;
                        if (1 == client.field946 * -512792823) {
                           Friend.method10203(
                              classKK.field3772,
                              client.field805 + " " + classDO.field1588 + " " + classQY.method9885(15574765, (byte)15) + var39.field4879,
                              61,
                              var17,
                              var14,
                              var15,
                              -1,
                              false,
                              var18,
                              -324749371
                           );
                        } else if (!client.field980) {
                           String[] var46 = var39.field4881;
                           if (var46 != null) {
                              for (int var53 = 4; var53 >= 0; var53--) {
                                 if (var20.method10631(var53, (byte)73) && var46[var53] != null) {
                                    byte var57 = 0;
                                    if (var53 == 0) {
                                       var57 = 63;
                                    }

                                    if (1 == var53) {
                                       var57 = 64;
                                    }

                                    if (2 == var53) {
                                       var57 = 65;
                                    }

                                    if (var53 == 3) {
                                       var57 = 66;
                                    }

                                    if (var53 == 4) {
                                       var57 = 67;
                                    }

                                    Friend.method10203(
                                       var46[var53],
                                       classQY.method9885(15574765, (byte)69) + var39.field4879,
                                       var57,
                                       var17,
                                       var14,
                                       var15,
                                       -1,
                                       false,
                                       var18,
                                       -324749371
                                    );
                                 }
                              }
                           }

                           Friend.method10203(
                              classKK.field3773,
                              classQY.method9885(15574765, (byte)31) + var39.field4879,
                              1013,
                              WorldEntityConfig.method8498(var39, (byte)-24),
                              var14,
                              var15,
                              -1,
                              false,
                              var18,
                              -324749371
                           );
                        }
                     }
                  }
               }
            }
         }

         var8++;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;)V")
   @ObfuscatedName("bl")
   @Override
   public void vmethod332(int var1, int var2, int var3, int var4, String var5) {
      classEA.method3815(var1, var2, var3, var4, var5, -1646017699);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   @Override
   public void vmethod340() {
      ClanChannel.method6234((byte)-45);
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;)V")
   @ObfuscatedName("bh")
   @Override
   public void vmethod333(int var1, int var2, int var3, int var4, String var5) {
      classEA.method3815(var1, var2, var3, var4, var5, 449433322);
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;I)Lry;")
   @ObfuscatedName("as")
   static classRY method3000(long var0, String var2, int var3) {
      try {
         classRY var4 = new classRY();
         var4.field5880 = var0 % 1000L * 1535572724674340763L;
         long var5 = var0 / 1000L;
         long var7 = var5 / 60L;
         long var9 = var7 / 60L;
         if (var2.equals("m")) {
            if (var3 != -765485579) {
               throw new IllegalStateException();
            }

            var4.field5878 = -8050594295617501823L * var7;
            var4.field5877 = var5 % 60L * -503644033987797831L;
         } else if (var2.equals("h")) {
            var4.field5879 = -8919200205680249099L * var9;
            var4.field5878 = var7 % 60L * -8050594295617501823L;
            var4.field5877 = -503644033987797831L * (var5 % 60L);
         } else if (var2.equals("d")) {
            var4.field5881 = var9 / 24L * -7447662746889974663L;
            var4.field5879 = -8919200205680249099L * (var9 % 24L);
            var4.field5878 = var7 % 60L * -8050594295617501823L;
            var4.field5877 = var5 % 60L * -503644033987797831L;
         }

         return var4;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "de.as(" + ')');
      }
   }
}
