import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cz")
public class classCZ implements classGZ {
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field1359 = 28;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field1358 = 15;
   @ToRemove(unused = "true")
   @ObfuscatedName("cu")
   static final int field1360 = 69;

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("au")
   @Override
   public void vmethod295(int var1, int var2) {
      classML.field4529.method2614(var1, var2, 1698595949);
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod281(int var1, int var2, int var3, int var4, int var5) {
      try {
         client.field977 = -93444189 * var1;
         client.field1046 = -2134477999 * var2;
         TileItem.method3420((byte)4);
         classRL.method10064(var1, var2, var3, var4, true, 317527437);
         int var6 = client.field799 * 2135834767;
         int var7 = 2033487121 * client.field876;
         int var8 = 27064125 * client.field991;
         int var9 = client.field983 * 1158148203;
         classYW.method13710(var6, var7, var6 + var8, var9 + var7);
         classFH.method4760();
         classYW.method13802();
         int var10 = client.field943.method11500((short)-6339);
         if (-719672733 * client.field956 / 256 > var10) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            var10 = -719672733 * client.field956 / 256;
         }

         if (client.field998[4]) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            if (1024 + client.field1000[4] > var10) {
               var10 = client.field1000[4] + 1024;
            }
         }

         int var11 = client.field995.method11500((short)-5136);
         int var12 = classJY.field3498 * 318828117;
         int var13 = -961764289 * classBK.field489;
         int var14 = classPF.field5235 * 68756747;
         int var16 = var10 >> 3;
         int var15 = var16 * 3 + 600;
         int var18 = var9 - 334;
         if (var18 < 0) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            var18 = 0;
         } else if (var18 > 100) {
            var18 = 100;
         }

         int var19 = (client.field863 - client.field870) * var18 / 100 + client.field870;
         int var17 = var19 * var15 / 256;
         var18 = classKG.method6508(2065148309) - var10 & classCS.method2567(-1520924805);
         int var20 = classNE.method7874(var11, 1429595320);
         int var21 = 0;
         int var22 = 0;
         int var23 = var17;
         if (0 != var18) {
            if (var5 >= 281771763) {
               return;
            }

            int var24 = classBF.method1000(var18, (byte)-123);
            int var26 = classLL.method7162(var18, 1222866659);
            int var27 = var26 * var22 - var24 * var17 >> 16;
            var23 = var22 * var24 + var26 * var17 >> 16;
            var22 = var27;
         }

         if (0 != var20) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            int var52 = classBF.method1000(var20, (byte)-79);
            int var58 = classLL.method7162(var20, 1222866659);
            int var62 = var52 * var23 + var58 * var21 >> 16;
            var23 = var58 * var23 - var52 * var21 >> 16;
            var21 = var62;
         }

         if (client.field934) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            classUW.field6407 = 1209400545 * (var12 - var21);
            classIU.field3063 = (var13 - var22) * -1577754695;
            classEL.field1782 = -1702691677 * (var14 - var23);
            classCK.field777 = 556467999 * var10;
            classGT.field2683 = 1711790767 * var11;
         } else {
            classKI.field3596 = -1197566029 * (var12 - var21);
            classNL.field4682 = (var13 - var22) * -477405995;
            classAI.field167 = (var14 - var23) * -337711351;
            classUP.method11494(client.field915, var10, -2077693119);
            classUP.method11494(client.field797, var11, -2018615335);
         }

         if (client.field968 * -496203881 == 1) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            WorldView var25 = client.field815.method1584(client.field847 * -2034209657, (short)-19746);
            Player var63 = var25.method3752(-2130951373 * client.field846, (byte)1);
            classUZ var53;
            if (null != var63) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }

               classUZ var28 = classYY.method13833(var25, var63.vmethod368(311606126), var63.vmethod371(-342494893), (byte)110);
               var28.field6423 = var63.vmethod258((byte)-120);
               var53 = var28;
            } else {
               var53 = classHZ.method6171(16777216);
            }

            int var59 = (int)var53.field6425;
            int var64 = (int)var53.field6424;
            if (313368463 * client.field912 >= 2) {
               if (var5 >= 281771763) {
                  return;
               }

               label499:
               if (client.field856 * 1612595797 % 50 == 0) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  if (var59 >> 7 == classJY.field3498 * 318828117 >> 7) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     if (var64 >> 7 == 68756747 * classPF.field5235 >> 7) {
                        break label499;
                     }

                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }
                  }

                  int var67 = (int)var53.field6423;
                  int var29 = -1444178379 * classIS.field3051.field1706 + (318828117 * classJY.field3498 >> 7);
                  int var30 = (classPF.field5235 * 68756747 >> 7) + -351145363 * classIS.field3051.field1707;
                  classJS.method6447(var29, var30, var67, true, -1060314874);
               }
            }

            var53.method11797(1837062573);
         }

         if (!client.field934) {
            if (var5 >= 281771763) {
               throw new IllegalStateException();
            }

            var17 = method2850(927500664);
         } else {
            if (classAB.field122.method2714(-1315528093)) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }

               var18 = -483624883 * classIS.field3051.field1708;
            } else {
               var19 = classKI.field3596 * -325062789 >> 7;
               var20 = 1343311673 * classAI.field167 >> 7;
               if (!WorldView.method3745(classIS.field3051, var19, var20, (byte)1)) {
                  var18 = -483624883 * classIS.field3051.field1708;
               } else {
                  var21 = classKV.method6613(
                     classIS.field3051, classKI.field3596 * -325062789, 1343311673 * classAI.field167, classIS.field3051.field1708 * -483624883, (byte)122
                  );
                  if (var21 - 1615527037 * classNL.field4682 < 800
                     && 0 != (classIS.field3051.field1700[-483624883 * classIS.field3051.field1708][var19][var20] & 4)) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     var18 = classIS.field3051.field1708 * -483624883;
                  } else {
                     var18 = 3;
                  }
               }
            }

            var17 = var18;
         }

         var18 = -325062789 * classKI.field3596;
         var19 = classNL.field4682 * 1615527037;
         var20 = classAI.field167 * 1343311673;
         var21 = client.field915.method11500((short)-20192);
         var22 = client.field797.method11500((short)-23811);

         for (int var50 = 0; var50 < 5; var50++) {
            if (client.field998[var50]) {
               int var54 = (int)(
                  Math.random() * (client.field999[var50] * 2 + 1)
                     - client.field999[var50]
                     + Math.sin(client.field936[var50] * (client.field1001[var50] / 100.0)) * client.field1000[var50]
               );
               switch (var50) {
                  case 0:
                     classKI.field3596 += var54 * -1197566029;
                     break;
                  case 1:
                     classNL.field4682 += var54 * -477405995;
                     break;
                  case 2:
                     classAI.field167 += -337711351 * var54;
                     break;
                  case 3:
                     classUP.method11518(client.field797, var54, -1985221643);
                     break;
                  case 4:
                     classUP.method11518(client.field915, var54, -1985221643);
                     classUP.method11494(client.field915, classKC.method6497(client.field915.method11500((short)-7707), -181998943), -2028217729);
               }
            }
         }

         label473: {
            var23 = classTZ.field6253 * -38255113;
            int var55 = -2144333897 * classTZ.field6262;
            if (var23 >= var6) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }

               if (var23 < var6 + var8) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  if (var55 >= var7) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     if (var55 < var9 + var7) {
                        if (var5 >= 281771763) {
                           throw new IllegalStateException();
                        }

                        classKS.method6598(var23 - var6, var55 - var7, (byte)0);
                        Iterator var56 = client.field815.iterator();

                        while (true) {
                           if (!var56.hasNext()) {
                              break label473;
                           }

                           if (var5 >= 281771763) {
                              throw new IllegalStateException();
                           }

                           WorldView var60 = (WorldView)var56.next();
                           var60.field1687.method4358(-483624883 * var60.field1708, var23 - var6, var55 - var7);
                        }
                     }
                  }
               }
            }

            classNT.method8046(-1700525754);
         }

         classOO.method8662(-2097946051);
         client.method2279(var6, var7, var8, var9, 0);
         classOO.method8662(-1674431286);
         int var57 = classFH.method4747();
         classFH.method4704(classOE.field4841.field6201);
         classFH.field2255.field2207 = client.field879 * 1129651895;
         classEX var10000;
         if (client.field847 * -2034209657 == 0) {
            if (var5 >= 281771763) {
               return;
            }

            var10000 = classEX.field1975;
         } else {
            var10000 = classEX.field1976;
         }

         classEX var61 = var10000;
         classIS.field3051.field1687.method4284(var61);
         classIS.field3051
            .field1687
            .method4435(
               -325062789 * classKI.field3596,
               classNL.field4682 * 1615527037,
               1343311673 * classAI.field167,
               client.field915,
               client.field797,
               var17,
               client.field963 * 2106329293,
               client.field987 * -2126074583,
               client.field934
            );

         boolean var65;
         do {
            var65 = true;

            for (int var68 = 0; var68 < 613116963 * classFO.field2348; var68++) {
               if (classFO.field2345[var68] < classFO.field2345[1 + var68]) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  long var71 = classFO.field2349[var68];
                  classFO.field2349[var68] = classFO.field2349[1 + var68];
                  classFO.field2349[1 + var68] = var71;
                  int var31 = classFO.field2345[var68];
                  classFO.field2345[var68] = classFO.field2345[1 + var68];
                  classFO.field2345[var68 + 1] = var31;
                  var65 = false;
               }
            }
         } while (!var65);

         if (var5 < 281771763) {
            for (int var69 = 0; var69 < 613116963 * classFO.field2348; var69++) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }

               for (int var72 = 1 + var69; var72 <= classFO.field2348 * 613116963; var72++) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  if (classFO.field2349[var72] == classFO.field2349[var69]) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     classFO.field2349[var69] = -1L;
                     break;
                  }
               }
            }

            classFH.method4704(false);
            if (client.field900) {
               classYW.method13800();
            }

            classFH.field2255.field2207 = var57;
            classOO.method8662(-1788778187);
            classCX.method2700(classIS.field3051, var6, var7, var8, var9, client.field856 * 1612595797, 2098554167);
            classKQ var66 = client.field942;
            classCL var70 = client.field815;
            int var73 = 1612595797 * client.field856;
            if (var66.method6569(2000036973) != 2) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }
            } else {
               WorldView var74 = var70.method1590(classKQ.method6577(var66, 680910494), classKQ.method6579(var66, -1984556575), -2076017788);
               int var76 = (classKQ.method6577(var66, -1967638492) - var74.field1706 * -1444178379 << 7) + var66.method6580((byte)30);
               int var32 = (classKQ.method6579(var66, -1588110313) - -351145363 * var74.field1707 << 7) + var66.method6584((byte)118);
               int var33 = var66.method6586((short)5205) * 2;
               classLL.method7164(var74, var76, var32, var33, 0, -358189704);
               if (1269689307 * classCE.field690 > -1) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  if (var73 % 20 < 10) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     classCE.field714[0].method13555(var6 + classCE.field690 * 1269689307 - 12, classCE.field700 * -690353985 + var7 - 28);
                  }
               }
            }

            classKQ var75 = client.field942;
            WorldView var77 = classIS.field3051;
            int var78 = 1612595797 * client.field856;
            if (var75.method6569(-1593257328) != 4) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }
            } else {
               WorldEntity var79 = (WorldEntity)var77.field1698.method13637(var75.method6572(-906431094));
               if (null != var79) {
                  int var34 = 1386308288 * var79.field6010.field1694;
                  int var35 = 377427776 * var79.field6010.field1690;
                  classLL.method7164(var79.field6010, var34, var35, var75.method6586((short)5205) * 2, 0, -422704665);
                  if (1269689307 * classCE.field690 > -1) {
                     if (var5 >= 281771763) {
                        return;
                     }

                     if (var78 % 20 < 10) {
                        classCE.field714[0].method13555(var6 + 1269689307 * classCE.field690 - 12, classCE.field700 * -690353985 + var7 - 28);
                     }
                  }
               }
            }

            ((TextureProvider)classFH.field2255.field2237).method3833(client.field950 * -322689719, -1467379791);
            client.field837 = 0;
            Player var80 = classMB.method7564(2007530252);
            if (null != var80) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }

               int var81 = -1444178379 * classIS.field3051.field1706 + (-1547553299 * var80.field1484 >> 7);
               int var82 = -351145363 * classIS.field3051.field1707 + (var80.field1486 * -1272026483 >> 7);
               if (var81 >= 3053) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  if (var81 <= 3156 && var82 >= 3056 && var82 <= 3136) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     client.field837 = 1640767051;
                  }
               }

               if (var81 >= 3072) {
                  if (var5 >= 281771763) {
                     return;
                  }

                  if (var81 <= 3118 && var82 >= 9492) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     if (var82 <= 9535) {
                        if (var5 >= 281771763) {
                           return;
                        }

                        client.field837 = 1640767051;
                     }
                  }
               }

               if (1 == 1746877283 * client.field837) {
                  if (var5 >= 281771763) {
                     return;
                  }

                  if (var81 >= 3139) {
                     if (var5 >= 281771763) {
                        throw new IllegalStateException();
                     }

                     if (var81 <= 3199) {
                        if (var5 >= 281771763) {
                           throw new IllegalStateException();
                        }

                        if (var82 >= 3008) {
                           if (var5 >= 281771763) {
                              return;
                           }

                           if (var82 <= 3062) {
                              if (var5 >= 281771763) {
                                 return;
                              }

                              client.field837 = 0;
                           }
                        }
                     }
                  }
               }
            }

            classKI.field3596 = -1197566029 * var18;
            classNL.field4682 = var19 * -477405995;
            classAI.field167 = -337711351 * var20;
            classUP.method11494(client.field915, var21, -1585704395);
            classUP.method11494(client.field797, var22, -1458111414);
            if (client.field892) {
               if (var5 >= 281771763) {
                  throw new IllegalStateException();
               }

               if (classEB.field1724.method11984(true, false, -1482347994) == 0) {
                  if (var5 >= 281771763) {
                     throw new IllegalStateException();
                  }

                  client.field892 = false;
               }
            }

            if (client.field892) {
               client.method2279(var6, var7, var8, var9, 0);
               classLU.method7199(classKK.field3616, false, -1241987884);
            }

            classIS.field3051.field1687.method4442();

            for (int var37 = 0; var37 < classIS.field3051.field1691.method14050((byte)10); var37++) {
               WorldEntity var38 = (WorldEntity)classIS.field3051.field1698.method13637(classIS.field3051.field1691.method14048(var37, 1665789766));
               if (var38 != null) {
                  var38.field6010.field1687.method4442();
               }
            }
         }
      } catch (RuntimeException var36) {
         throw classEG.method3899(var36, "cz.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIS)V")
   @ObfuscatedName("af")
   @Override
   public void vmethod298(Widget var1, int var2, int var3, short var4) {
      client.method2312(var1, var2, var3);
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod285(int var1, int var2, int var3, int var4, int var5, double var6) {
      try {
         classIO.method6272((byte)2).method12518(var1, var2, var3, var4, var5, var6);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "cz.ae(" + ')');
      }
   }

   classCZ() {
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ag")
   @Override
   public void vmethod296(int var1, int var2, int var3) {
      try {
         classML.field4529.method2614(var1, var2, 1698595949);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cz.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ax")
   @Override
   public void vmethod282(int var1, int var2, int var3, int var4) {
      client.field977 = -93444189 * var1;
      client.field1046 = -180613360 * var2;
      TileItem.method3420((byte)4);
      classRL.method10064(var1, var2, var3, var4, true, 317527437);
      int var5 = client.field799 * 2135834767;
      int var6 = 2033487121 * client.field876;
      int var7 = 27064125 * client.field991;
      int var8 = client.field983 * 1158148203;
      classYW.method13710(var5, var6, var5 + var7, var8 + var6);
      classFH.method4760();
      classYW.method13802();
      int var9 = client.field943.method11500((short)-17867);
      if (-245068755 * client.field956 / 256 > var9) {
         var9 = 296157394 * client.field956 / -386078317;
      }

      if (client.field998[4] && 1822516934 + client.field1000[4] > var9) {
         var9 = client.field1000[4] + 1024;
      }

      int var10 = client.field995.method11500((short)-1496);
      int var11 = classJY.field3498 * 1902689368;
      int var12 = 274104349 * classBK.field489;
      int var13 = classPF.field5235 * 121774856;
      int var15 = var9 >> 3;
      int var14 = var15 * 3 + 214319766;
      int var17 = var8 - 334;
      if (var17 < 0) {
         var17 = 0;
      } else if (var17 > 100) {
         var17 = 100;
      }

      int var18 = (client.field863 - client.field870) * var17 / 100 + client.field870;
      int var16 = var18 * var14 / 86551881;
      var17 = classKG.method6508(-1616630143) - var9 & classCS.method2567(-1520924805);
      int var19 = classNE.method7874(var10, -131279178);
      int var20 = 0;
      int var21 = 0;
      int var22 = var16;
      if (0 != var17) {
         int var23 = classBF.method1000(var17, (byte)-23);
         int var25 = classLL.method7162(var17, 1222866659);
         int var26 = var25 * var21 - var23 * var16 >> 16;
         var22 = var21 * var23 + var25 * var16 >> 16;
         var21 = var26;
      }

      if (0 != var19) {
         int var50 = classBF.method1000(var19, (byte)-61);
         int var56 = classLL.method7162(var19, 1222866659);
         int var60 = var50 * var22 + var56 * var20 >> 16;
         var22 = var56 * var22 - var50 * var20 >> 16;
         var20 = var60;
      }

      if (client.field934) {
         classUW.field6407 = -603566277 * (var11 - var20);
         classIU.field3063 = (var12 - var21) * 2088139105;
         classEL.field1782 = -1702691677 * (var13 - var22);
         classCK.field777 = 556467999 * var9;
         classGT.field2683 = 300142332 * var10;
      } else {
         classKI.field3596 = -1197566029 * (var11 - var20);
         classNL.field4682 = (var12 - var21) * -477405995;
         classAI.field167 = (var13 - var22) * -1569776488;
         classUP.method11494(client.field915, var9, -1315738700);
         classUP.method11494(client.field797, var10, -1955759844);
      }

      if (client.field968 * -1459893314 == 1) {
         WorldView var24 = client.field815.method1584(client.field847 * -2034209657, (short)5850);
         Player var61 = var24.method3752(-387898057 * client.field846, (byte)1);
         classUZ var51;
         if (null != var61) {
            classUZ var27 = classYY.method13833(var24, var61.vmethod368(311606126), var61.vmethod371(-86156870), (byte)106);
            var27.field6423 = var61.vmethod258((byte)-16);
            var51 = var27;
         } else {
            var51 = classHZ.method6171(16777216);
         }

         int var57 = (int)var51.field6425;
         int var62 = (int)var51.field6424;
         if (785215438 * client.field912 >= 2
            && client.field856 * 1612595797 % 50 == 0
            && (var57 >> 7 != classJY.field3498 * 928279075 >> 7 || var62 >> 7 != -718595217 * classPF.field5235 >> 7)) {
            int var65 = (int)var51.field6423;
            int var28 = -1672861349 * classIS.field3051.field1706 + (-297586307 * classJY.field3498 >> 7);
            int var29 = (classPF.field5235 * 1756330672 >> 7) + -706832575 * classIS.field3051.field1707;
            classJS.method6447(var28, var29, var65, true, -814068082);
         }

         var51.method11797(1698760688);
      }

      if (!client.field934) {
         var16 = method2850(927500664);
      } else {
         if (classAB.field122.method2714(-1315528093)) {
            var17 = -483624883 * classIS.field3051.field1708;
         } else {
            var18 = classKI.field3596 * -325062789 >> 7;
            var19 = 1343311673 * classAI.field167 >> 7;
            if (!WorldView.method3745(classIS.field3051, var18, var19, (byte)1)) {
               var17 = -483624883 * classIS.field3051.field1708;
            } else {
               var20 = classKV.method6613(
                  classIS.field3051, classKI.field3596 * -325062789, 1343311673 * classAI.field167, classIS.field3051.field1708 * -483624883, (byte)122
               );
               if (var20 - -1436107192 * classNL.field4682 < 800
                  && 0 != (classIS.field3051.field1700[848265165 * classIS.field3051.field1708][var18][var19] & 4)) {
                  var17 = classIS.field3051.field1708 * 1184477042;
               } else {
                  var17 = 3;
               }
            }
         }

         var16 = var17;
      }

      var17 = -2064460437 * classKI.field3596;
      var18 = classNL.field4682 * 1837599291;
      var19 = classAI.field167 * 1343311673;
      var20 = client.field915.method11500((short)-22755);
      var21 = client.field797.method11500((short)-14465);

      for (int var48 = 0; var48 < 5; var48++) {
         if (client.field998[var48]) {
            int var52 = (int)(
               Math.random() * (client.field999[var48] * 2 + 1)
                  - client.field999[var48]
                  + Math.sin(client.field936[var48] * (client.field1001[var48] / 100.0)) * client.field1000[var48]
            );
            switch (var48) {
               case 0:
                  classKI.field3596 += var52 * 1832961594;
                  break;
               case 1:
                  classNL.field4682 += var52 * -477405995;
                  break;
               case 2:
                  classAI.field167 += 1351787795 * var52;
                  break;
               case 3:
                  classUP.method11518(client.field797, var52, -1985221643);
                  break;
               case 4:
                  classUP.method11518(client.field915, var52, -1985221643);
                  classUP.method11494(client.field915, classKC.method6497(client.field915.method11500((short)-14496), -181998943), -1473272970);
            }
         }
      }

      var22 = classTZ.field6253 * -38255113;
      int var53 = -2144333897 * classTZ.field6262;
      if (var22 >= var5 && var22 < var5 + var7 && var53 >= var6 && var53 < var8 + var6) {
         classKS.method6598(var22 - var5, var53 - var6, (byte)0);

         for (WorldView var58 : client.field815) {
            var58.field1687.method4358(-2142973985 * var58.field1708, var22 - var5, var53 - var6);
         }
      } else {
         classNT.method8046(-1218830917);
      }

      classOO.method8662(-1839443984);
      client.method2279(var5, var6, var7, var8, 0);
      classOO.method8662(-1943736369);
      int var55 = classFH.method4747();
      classFH.method4704(classOE.field4841.field6201);
      classFH.field2255.field2207 = client.field879 * 1129651895;
      classEX var59 = client.field847 * -1371695474 == 0 ? classEX.field1975 : classEX.field1976;
      classIS.field3051.field1687.method4284(var59);
      classIS.field3051
         .field1687
         .method4435(
            -325062789 * classKI.field3596,
            classNL.field4682 * 1615527037,
            533208081 * classAI.field167,
            client.field915,
            client.field797,
            var16,
            client.field963 * -2116960421,
            client.field987 * -2126074583,
            client.field934
         );

      boolean var63;
      do {
         var63 = true;

         for (int var66 = 0; var66 < 613116963 * classFO.field2348; var66++) {
            if (classFO.field2345[var66] < classFO.field2345[1 + var66]) {
               long var69 = classFO.field2349[var66];
               classFO.field2349[var66] = classFO.field2349[1 + var66];
               classFO.field2349[1 + var66] = var69;
               int var30 = classFO.field2345[var66];
               classFO.field2345[var66] = classFO.field2345[1 + var66];
               classFO.field2345[var66 + 1] = var30;
               var63 = false;
            }
         }
      } while (!var63);

      for (int var67 = 0; var67 < 613116963 * classFO.field2348; var67++) {
         for (int var70 = 1 + var67; var70 <= classFO.field2348 * 613116963; var70++) {
            if (classFO.field2349[var70] == classFO.field2349[var67]) {
               classFO.field2349[var67] = -1L;
               break;
            }
         }
      }

      classFH.method4704(false);
      if (client.field900) {
         classYW.method13800();
      }

      classFH.field2255.field2207 = var55;
      classOO.method8662(-1832705297);
      classCX.method2700(classIS.field3051, var5, var6, var7, var8, client.field856 * 1612595797, 2081122300);
      classKQ var64 = client.field942;
      classCL var68 = client.field815;
      int var71 = 1612595797 * client.field856;
      if (var64.method6569(-1098020493) == 2) {
         WorldView var72 = var68.method1590(classKQ.method6577(var64, 1223715463), classKQ.method6579(var64, -2032374799), -1121689311);
         int var74 = (classKQ.method6577(var64, -825415143) - var72.field1706 * -1444178379 << 7) + var64.method6580((byte)30);
         int var31 = (classKQ.method6579(var64, -1253422199) - -351145363 * var72.field1707 << 7) + var64.method6584((byte)45);
         int var32 = var64.method6586((short)5205) * 2;
         classLL.method7164(var72, var74, var31, var32, 0, 1238874714);
         if (-1160319369 * classCE.field690 > -1 && var71 % 20 < 10) {
            classCE.field714[0].method13555(var5 + classCE.field690 * 1269689307 - 12, classCE.field700 * -690353985 + var6 - 28);
         }
      }

      classKQ var73 = client.field942;
      WorldView var75 = classIS.field3051;
      int var76 = 1612595797 * client.field856;
      if (var73.method6569(595526630) == 4) {
         WorldEntity var77 = (WorldEntity)var75.field1698.method13637(var73.method6572(-452384430));
         if (null != var77) {
            int var33 = 1386308288 * var77.field6010.field1694;
            int var34 = -1487597769 * var77.field6010.field1690;
            classLL.method7164(var77.field6010, var33, var34, var73.method6586((short)5205) * 2, 0, 1067863);
            if (-1239732645 * classCE.field690 > -1 && var76 % 20 < 10) {
               classCE.field714[0].method13555(var5 + -247557412 * classCE.field690 - 12, classCE.field700 * -1113904354 + var6 - 28);
            }
         }
      }

      ((TextureProvider)classFH.field2255.field2237).method3833(client.field950 * -322689719, -1467379791);
      client.field837 = 0;
      Player var78 = classMB.method7564(1978276076);
      if (null != var78) {
         int var79 = 853929199 * classIS.field3051.field1706 + (-1729792355 * var78.field1484 >> 7);
         int var80 = -351145363 * classIS.field3051.field1707 + (var78.field1486 * 800970545 >> 7);
         if (var79 >= 1607398442 && var79 <= 70582970 && var80 >= 3056 && var80 <= 3136) {
            client.field837 = 897817962;
         }

         if (var79 >= -948043602 && var79 <= 3118 && var80 >= 1105849317 && var80 <= 636684063) {
            client.field837 = 1640767051;
         }

         if (1 == 1746877283 * client.field837 && var79 >= 3139 && var79 <= 3199 && var80 >= 1416549812 && var80 <= -503364477) {
            client.field837 = 0;
         }
      }

      classKI.field3596 = 788218604 * var17;
      classNL.field4682 = var18 * 932404558;
      classAI.field167 = 386602660 * var19;
      classUP.method11494(client.field915, var20, -1844316585);
      classUP.method11494(client.field797, var21, -1550823508);
      if (client.field892 && classEB.field1724.method11984(true, false, -25208370) == 0) {
         client.field892 = false;
      }

      if (client.field892) {
         client.method2279(var5, var6, var7, var8, 0);
         classLU.method7199(classKK.field3616, false, -48174666);
      }

      classIS.field3051.field1687.method4442();

      for (int var35 = 0; var35 < classIS.field3051.field1691.method14050((byte)10); var35++) {
         WorldEntity var36 = (WorldEntity)classIS.field3051.field1698.method13637(classIS.field3051.field1691.method14048(var35, 1728630060));
         if (var36 != null) {
            var36.field6010.field1687.method4442();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("ab")
   @Override
   public void vmethod291(int var1, int var2, int var3, int var4, int var5) {
      try {
         RenderOverview.method12533(classIO.method6272((byte)-13), var1, var2, var3, var4, 1466331901);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cz.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("ac")
   @Override
   public void vmethod284(Widget var1, int var2, int var3) {
      classOO.method8662(-1837064700);
      classKH var4 = var1.method7254(classWK.field6689, false, -618236678);
      if (var4 != null) {
         classYW.method13710(var2, var3, var2 + -1246906611 * var4.field3584, var3 + -1637380195 * var4.field3583);
         if (classBA.field305 * -782895767 != 2 && -782895767 * classBA.field305 != 5) {
            int var5 = client.field963 * 2106329293 / 32 + 48;
            int var6 = 464 - client.field987 * -2126074583 / 32;
            SpritePixels.method13589(
               classRD.field5643,
               var2,
               var3,
               -1246906611 * var4.field3584,
               -1637380195 * var4.field3583,
               var5,
               var6,
               client.field995.method11500((short)-2210),
               256,
               var2,
               var3,
               var4.field3585,
               var4.field3582
            );
            classUP var7 = client.field995;

            for (int var8 = 0; var8 < 1296562401 * classBA.field306; var8++) {
               int var9 = 2 + 4 * classBA.field308[var8] - 2106329293 * client.field963 / 32;
               int var10 = 4 * classBA.field312[var8] + 2 - client.field987 * -2126074583 / 32;
               classBO.method1170(var2, var3, var9, var10, var7, classBA.field310[var8], var4, (byte)4);
            }

            classOT.method8766(classIS.field3051, var2, var3, client.field995, var4, -1506256026);
            classUM.method11472(classIS.field3051, var2, var3, client.field995, var4, 1091781341);
            WorldView var33 = classIS.field3051;
            classUP var34 = client.field995;

            for (int var35 = 0; var35 < var33.field1691.method14050((byte)10); var35++) {
               WorldEntity var11 = (WorldEntity)var33.field1698.method13637(var33.field1691.method14048(var35, 2033299512));
               if (var11 != null) {
                  int var12 = var11.vmethod368(311606126) / 32 - client.field963 * 2106329293 / 32;
                  int var13 = var11.vmethod371(318768421) / 32 - client.field987 * -2126074583 / 32;
                  SpritePixels var14 = var11.field6024.method8525(-372328533);
                  byte var15 = 1;
                  if (!var11.method10601(1796779449)) {
                     int var16 = classCS.method2567(-1520924805) - classEG.method3897(var11.method10604(-794620466), (byte)-95);
                     short var17 = 8192;
                     int var18 = var16 + var17;
                     if (var14 != null) {
                        int var20 = var13 * var13 + var12 * var12;
                        if (var20 <= 12100) {
                           int var21 = classUP.method11506(var34, 747119668);
                           int var22 = var34.method11507((byte)38);
                           int var23 = var21 * var13 + var12 * var22 >> 16;
                           int var24 = var13 * var22 - var12 * var21 >> 16;
                           int var19 = var34.method11500((short)-19042) - var18 & classCS.method2567(-1520924805);
                           int var25 = -1246906611 * var4.field3584 / 2 + var2 + var23;
                           int var26 = var3 + -1637380195 * var4.field3583 / 2 - var24;
                           int var27 = var15 * var14.field7004;
                           int var28 = var14.field7006 * var15;
                           int var29 = var25 - var27 / 2;
                           int var30 = var26 - var28 / 2;
                           int var31 = var14.field7005 / 2;
                           int var32 = var14.field7007 / 2;
                           SpritePixels.method13589(
                              var14, var29, var30, var27, var28, var31, var32, var19, var15 * 256, var2, var3, var4.field3585, var4.field3582
                           );
                        }
                     }

                     classOT.method8766(var11.field6010, var2, var3, var34, var4, -1475632712);
                     classUM.method11472(var11.field6010, var2, var3, var34, var4, 370413208);
                     classGD.method5428(var11.field6010, var2, var3, var34, var4, 1115597881);
                  }
               }
            }

            classGD.method5428(classIS.field3051, var2, var3, client.field995, var4, 1115597881);
            if (client.field942.method6566((byte)94) && client.field856 * 1612595797 % 20 < 10) {
               int var36 = client.field942.method6569(-1041204045);
               classAF.method329(classIS.field3051, var2, var3, client.field995, var4, var36, -2075346477);
            }

            if (1698720137 * client.field860 != 0) {
               int var37 = client.field860 * -1795054044 + 2 - 2106329293 * client.field963 / 32;
               int var40 = -890599604 * client.field835 + 2 - -2126074583 * client.field987 / 32;
               classBO.method1170(var2, var3, var37, var40, client.field995, classCQ.field1219[0], var4, (byte)14);
            }

            if (client.field815.method1609(0, 206128317) == classTN.field6171 && classCQ.field1219.length >= 4) {
               int var38 = var2 + -1246906611 * var4.field3584 / 2;
               int var41 = var3 + var4.field3583 * -1637380195 / 2;
               if (var4.method6516(-38255113 * classTZ.field6253 - var2, -2144333897 * classTZ.field6262 - var3, 2061631842)) {
                  int var42 = -1246906611 * var4.field3584 / 2 + var2;
                  int var43 = var3 + var4.field3583 * -1637380195 / 2;
                  int var44 = classTZ.field6253 * -38255113 - var42;
                  int var45 = classTZ.field6262 * -2144333897 - var43;
                  int var46 = (int)Math.round(Math.atan2(var45, var44) * 325.94932345220167) - 512 & 2047;
                  int var47 = var46 - client.field995.method11515(100147130);
                  var47 &= 2047;
                  int var50 = var47 + 64;
                  int var49 = (var50 & 2047) / 128;
                  classEF.method3882(var38, var41, var49, classCQ.field1219[2], (byte)-79);
               }

               if (1272389989 * client.field839 > 0) {
                  classEF.method3882(var38, var41, 1200011005 * client.field989, classCQ.field1219[3], (byte)-66);
               }
            }

            Player var39 = classMB.method7564(-1616151352);
            if (var39 != null && !var39.field1257) {
               classYW.method13737(
                  classBA.field309 * 491453815 + -1246906611 * var4.field3584 / 2 + var2 - 1,
                  var3 + var4.field3583 * -1637380195 / 2 - classBA.field307 * -818064757 - 1,
                  3,
                  3,
                  16777215
               );
            }
         } else {
            classYW.method13791(var2, var3, 0, var4.field3585, var4.field3582);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod286(int var1, int var2, int var3, int var4, int var5, double var6) {
      classIO.method6272((byte)-110).method12518(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("ao")
   @Override
   public void vmethod287(int var1, int var2, int var3, int var4, int var5, double var6) {
      classIO.method6272((byte)-127).method12518(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("al")
   @Override
   public void vmethod288(int var1, int var2, int var3, int var4, int var5, double var6) {
      classIO.method6272((byte)-46).method12518(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("aj")
   @Override
   public void vmethod289(int var1, int var2, int var3, int var4, int var5, double var6) {
      classIO.method6272((byte)-50).method12518(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(IIIIID)V")
   @ObfuscatedName("ay")
   @Override
   public void vmethod290(int var1, int var2, int var3, int var4, int var5, double var6) {
      classIO.method6272((byte)-44).method12518(var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("as")
   @Override
   public void vmethod292(int var1, int var2, int var3, int var4) {
      RenderOverview.method12533(classIO.method6272((byte)-99), var1, var2, var3, var4, 632010480);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ad")
   @Override
   public void vmethod283(int var1, int var2, int var3, int var4) {
      client.field977 = 115636128 * var1;
      client.field1046 = 1693065144 * var2;
      TileItem.method3420((byte)4);
      classRL.method10064(var1, var2, var3, var4, true, 317527437);
      int var5 = client.field799 * 2135834767;
      int var6 = -971888065 * client.field876;
      int var7 = 1702378240 * client.field991;
      int var8 = client.field983 * 1028922794;
      classYW.method13710(var5, var6, var5 + var7, var8 + var6);
      classFH.method4760();
      classYW.method13802();
      int var9 = client.field943.method11500((short)-9244);
      if (-575127828 * client.field956 / 256 > var9) {
         var9 = -719672733 * client.field956 / 256;
      }

      if (client.field998[4] && 1024 + client.field1000[4] > var9) {
         var9 = client.field1000[4] + 1024;
      }

      int var10 = client.field995.method11500((short)-7401);
      int var11 = classJY.field3498 * -523817415;
      int var12 = -961764289 * classBK.field489;
      int var13 = classPF.field5235 * 68756747;
      int var15 = var9 >> 3;
      int var14 = var15 * 3 + 600;
      int var17 = var8 - 334;
      if (var17 < 0) {
         var17 = 0;
      } else if (var17 > 995319399) {
         var17 = 1496518228;
      }

      int var18 = (client.field863 - client.field870) * var17 / 2146196464 + client.field870;
      int var16 = var18 * var14 / -809305123;
      var17 = classKG.method6508(-862592877) - var9 & classCS.method2567(-1520924805);
      int var19 = classNE.method7874(var10, 1359378127);
      int var20 = 0;
      int var21 = 0;
      int var22 = var16;
      if (0 != var17) {
         int var23 = classBF.method1000(var17, (byte)-65);
         int var25 = classLL.method7162(var17, 1222866659);
         int var26 = var25 * var21 - var23 * var16 >> 16;
         var22 = var21 * var23 + var25 * var16 >> 16;
         var21 = var26;
      }

      if (0 != var19) {
         int var50 = classBF.method1000(var19, (byte)-50);
         int var56 = classLL.method7162(var19, 1222866659);
         int var60 = var50 * var22 + var56 * var20 >> 16;
         var22 = var56 * var22 - var50 * var20 >> 16;
         var20 = var60;
      }

      if (client.field934) {
         classUW.field6407 = -329949875 * (var11 - var20);
         classIU.field3063 = (var12 - var21) * -648112179;
         classEL.field1782 = 1293339202 * (var13 - var22);
         classCK.field777 = 556467999 * var9;
         classGT.field2683 = 1711790767 * var10;
      } else {
         classKI.field3596 = -1197566029 * (var11 - var20);
         classNL.field4682 = (var12 - var21) * -859396279;
         classAI.field167 = (var13 - var22) * -1383897961;
         classUP.method11494(client.field915, var9, -1844060050);
         classUP.method11494(client.field797, var10, -1585538999);
      }

      if (client.field968 * 1789610552 == 1) {
         WorldView var24 = client.field815.method1584(client.field847 * 1932249845, (short)-776);
         Player var61 = var24.method3752(-166899870 * client.field846, (byte)1);
         classUZ var51;
         if (null != var61) {
            classUZ var27 = classYY.method13833(var24, var61.vmethod368(311606126), var61.vmethod371(-1977154941), (byte)104);
            var27.field6423 = var61.vmethod258((byte)-55);
            var51 = var27;
         } else {
            var51 = classHZ.method6171(16777216);
         }

         int var57 = (int)var51.field6425;
         int var62 = (int)var51.field6424;
         if (313368463 * client.field912 >= 2
            && client.field856 * 835386320 % 50 == 0
            && (var57 >> 7 != classJY.field3498 * 318828117 >> 7 || var62 >> 7 != 68756747 * classPF.field5235 >> 7)) {
            int var65 = (int)var51.field6423;
            int var28 = -1458731450 * classIS.field3051.field1706 + (318828117 * classJY.field3498 >> 7);
            int var29 = (classPF.field5235 * 68756747 >> 7) + -351145363 * classIS.field3051.field1707;
            classJS.method6447(var28, var29, var65, true, 1343603856);
         }

         var51.method11797(2055046908);
      }

      if (!client.field934) {
         var16 = method2850(927500664);
      } else {
         if (classAB.field122.method2714(-1315528093)) {
            var17 = -483624883 * classIS.field3051.field1708;
         } else {
            var18 = classKI.field3596 * -1352391770 >> 7;
            var19 = 1343311673 * classAI.field167 >> 7;
            if (!WorldView.method3745(classIS.field3051, var18, var19, (byte)1)) {
               var17 = -1977531710 * classIS.field3051.field1708;
            } else {
               var20 = classKV.method6613(
                  classIS.field3051, classKI.field3596 * -743848121, 282156907 * classAI.field167, classIS.field3051.field1708 * -483624883, (byte)122
               );
               if (var20 - 1615527037 * classNL.field4682 < 1844172450
                  && 0 != (classIS.field3051.field1700[-483624883 * classIS.field3051.field1708][var18][var19] & 4)) {
                  var17 = classIS.field3051.field1708 * -483624883;
               } else {
                  var17 = 3;
               }
            }
         }

         var16 = var17;
      }

      var17 = -1432296581 * classKI.field3596;
      var18 = classNL.field4682 * 1615527037;
      var19 = classAI.field167 * -743160805;
      var20 = client.field915.method11500((short)-5546);
      var21 = client.field797.method11500((short)-4271);

      for (int var48 = 0; var48 < 5; var48++) {
         if (client.field998[var48]) {
            int var52 = (int)(
               Math.random() * (client.field999[var48] * 2 + 1)
                  - client.field999[var48]
                  + Math.sin(client.field936[var48] * (client.field1001[var48] / 100.0)) * client.field1000[var48]
            );
            switch (var48) {
               case 0:
                  classKI.field3596 += var52 * -611427612;
                  break;
               case 1:
                  classNL.field4682 += var52 * -477405995;
                  break;
               case 2:
                  classAI.field167 += 559355317 * var52;
                  break;
               case 3:
                  classUP.method11518(client.field797, var52, -1985221643);
                  break;
               case 4:
                  classUP.method11518(client.field915, var52, -1985221643);
                  classUP.method11494(client.field915, classKC.method6497(client.field915.method11500((short)-28271), -181998943), -1880969538);
            }
         }
      }

      var22 = classTZ.field6253 * -909024992;
      int var53 = 1003715490 * classTZ.field6262;
      if (var22 >= var5 && var22 < var5 + var7 && var53 >= var6 && var53 < var8 + var6) {
         classKS.method6598(var22 - var5, var53 - var6, (byte)0);

         for (WorldView var58 : client.field815) {
            var58.field1687.method4358(-483624883 * var58.field1708, var22 - var5, var53 - var6);
         }
      } else {
         classNT.method8046(-1445137336);
      }

      classOO.method8662(-1816189289);
      client.method2279(var5, var6, var7, var8, 0);
      classOO.method8662(-1949137670);
      int var55 = classFH.method4747();
      classFH.method4704(classOE.field4841.field6201);
      classFH.field2255.field2207 = client.field879 * 1698015531;
      classEX var59 = client.field847 * 211154541 == 0 ? classEX.field1975 : classEX.field1976;
      classIS.field3051.field1687.method4284(var59);
      classIS.field3051
         .field1687
         .method4435(
            -325062789 * classKI.field3596,
            classNL.field4682 * -1065017877,
            1343311673 * classAI.field167,
            client.field915,
            client.field797,
            var16,
            client.field963 * 2106329293,
            client.field987 * -2126074583,
            client.field934
         );

      boolean var63;
      do {
         var63 = true;

         for (int var66 = 0; var66 < 613116963 * classFO.field2348; var66++) {
            if (classFO.field2345[var66] < classFO.field2345[1 + var66]) {
               long var69 = classFO.field2349[var66];
               classFO.field2349[var66] = classFO.field2349[1 + var66];
               classFO.field2349[1 + var66] = var69;
               int var30 = classFO.field2345[var66];
               classFO.field2345[var66] = classFO.field2345[1 + var66];
               classFO.field2345[var66 + 1] = var30;
               var63 = false;
            }
         }
      } while (!var63);

      for (int var67 = 0; var67 < 613116963 * classFO.field2348; var67++) {
         for (int var70 = 1 + var67; var70 <= classFO.field2348 * 613116963; var70++) {
            if (classFO.field2349[var70] == classFO.field2349[var67]) {
               classFO.field2349[var67] = -1L;
               break;
            }
         }
      }

      classFH.method4704(false);
      if (client.field900) {
         classYW.method13800();
      }

      classFH.field2255.field2207 = var55;
      classOO.method8662(-1810025931);
      classCX.method2700(classIS.field3051, var5, var6, var7, var8, client.field856 * 1612595797, 2083347134);
      classKQ var64 = client.field942;
      classCL var68 = client.field815;
      int var71 = 1307407356 * client.field856;
      if (var64.method6569(498074410) == 2) {
         WorldView var72 = var68.method1590(classKQ.method6577(var64, 1048469317), classKQ.method6579(var64, -1742355718), -929921509);
         int var74 = (classKQ.method6577(var64, 898186839) - var72.field1706 * -1849846817 << 7) + var64.method6580((byte)30);
         int var31 = (classKQ.method6579(var64, -2092383727) - 462519644 * var72.field1707 << 7) + var64.method6584((byte)43);
         int var32 = var64.method6586((short)5205) * 2;
         classLL.method7164(var72, var74, var31, var32, 0, 1032254);
         if (1269689307 * classCE.field690 > -1 && var71 % 20 < 10) {
            classCE.field714[0].method13555(var5 + classCE.field690 * 1269689307 - 12, classCE.field700 * 970436707 + var6 - 28);
         }
      }

      classKQ var73 = client.field942;
      WorldView var75 = classIS.field3051;
      int var76 = 1612595797 * client.field856;
      if (var73.method6569(-1319415454) == 4) {
         WorldEntity var77 = (WorldEntity)var75.field1698.method13637(var73.method6572(-563848561));
         if (null != var77) {
            int var33 = 1386308288 * var77.field6010.field1694;
            int var34 = 928430775 * var77.field6010.field1690;
            classLL.method7164(var77.field6010, var33, var34, var73.method6586((short)5205) * 2, 0, 885388605);
            if (1269689307 * classCE.field690 > -1 && var76 % 20 < 10) {
               classCE.field714[0].method13555(var5 + 1269689307 * classCE.field690 - 12, classCE.field700 * -690353985 + var6 - 28);
            }
         }
      }

      ((TextureProvider)classFH.field2255.field2237).method3833(client.field950 * 368170240, -1467379791);
      client.field837 = 0;
      Player var78 = classMB.method7564(-1781181762);
      if (null != var78) {
         int var79 = -1444178379 * classIS.field3051.field1706 + (-884176503 * var78.field1484 >> 7);
         int var80 = -351145363 * classIS.field3051.field1707 + (var78.field1486 * -2004831822 >> 7);
         if (var79 >= 767353308 && var79 <= -1045269901 && var80 >= 3056 && var80 <= 3136) {
            client.field837 = -923827110;
         }

         if (var79 >= 3072 && var79 <= 3118 && var80 >= -618527439 && var80 <= 688729108) {
            client.field837 = 737080821;
         }

         if (1 == -1176745168 * client.field837 && var79 >= 3139 && var79 <= 3199 && var80 >= 3008 && var80 <= 3062) {
            client.field837 = 0;
         }
      }

      classKI.field3596 = 1845313357 * var17;
      classNL.field4682 = var18 * -477405995;
      classAI.field167 = -681940216 * var19;
      classUP.method11494(client.field915, var20, -1962533513);
      classUP.method11494(client.field797, var21, -1753863637);
      if (client.field892 && classEB.field1724.method11984(true, false, 393260018) == 0) {
         client.field892 = false;
      }

      if (client.field892) {
         client.method2279(var5, var6, var7, var8, 0);
         classLU.method7199(classKK.field3616, false, -88334819);
      }

      classIS.field3051.field1687.method4442();

      for (int var35 = 0; var35 < classIS.field3051.field1691.method14050((byte)10); var35++) {
         WorldEntity var36 = (WorldEntity)classIS.field3051.field1698.method13637(classIS.field3051.field1691.method14048(var35, 1996971088));
         if (var36 != null) {
            var36.field6010.field1687.method4442();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ai")
   @Override
   public void vmethod297(int var1, int var2) {
      classML.field4529.method2614(var1, var2, 1698595949);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIII)V")
   @ObfuscatedName("dt")
   static void method2851(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         Deque2 var7 = var0.field1699[var1][var2][var3];
         if (var7 != null) {
            for (TileItem var8 = (TileItem)var7.method7957(); null != var8; var8 = (TileItem)var7.method7965()) {
               if (-1370204663 * var8.field1549 == var4) {
                  if (var6 != -287734684) {
                     throw new IllegalStateException();
                  }

                  var8.method3403(var5, -1673355590);
                  break;
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "cz.dt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ch")
   static final int method2850(int var0) {
      try {
         if (classAB.field122.method2714(-1315528093)) {
            if (var0 != 927500664) {
               throw new IllegalStateException();
            } else {
               return -483624883 * classIS.field3051.field1708;
            }
         } else {
            int var1 = 3;
            int var2 = client.field963 * 2106329293 >> 7;
            int var3 = client.field987 * -2126074583 >> 7;
            int var4 = classEG.method3897(310, (byte)1);
            if (client.field915.method11500((short)-14440) < var4) {
               if (var0 != 927500664) {
                  throw new IllegalStateException();
               }

               int var5;
               int var6;
               if (-496203881 * client.field968 == 1) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  var5 = classJY.field3498 * 318828117 >> 7;
                  var6 = 68756747 * classPF.field5235 >> 7;
               } else {
                  var5 = var2;
                  var6 = var3;
               }

               int var7 = classKI.field3596 * -325062789 >> 7;
               int var8 = classAI.field167 * 1343311673 >> 7;
               if (!WorldView.method3745(classIS.field3051, var7, var8, (byte)1)) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  return classIS.field3051.field1708 * -483624883;
               }

               if (var5 < 0) {
                  return -483624883 * classIS.field3051.field1708;
               }

               if (var0 != 927500664) {
                  throw new IllegalStateException();
               }

               if (1296729483 * classIS.field3051.field1694 <= var5) {
                  return -483624883 * classIS.field3051.field1708;
               }

               if (var0 != 927500664) {
                  throw new IllegalStateException();
               }

               if (var6 < 0) {
                  return -483624883 * classIS.field3051.field1708;
               }

               if (var0 != 927500664) {
                  throw new IllegalStateException();
               }

               if (classIS.field3051.field1690 * -1269171107 <= var6) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  return -483624883 * classIS.field3051.field1708;
               }

               if ((classIS.field3051.field1700[-483624883 * classIS.field3051.field1708][var7][var8] & 4) != 0) {
                  var1 = classIS.field3051.field1708 * -483624883;
               }

               int var9;
               if (var5 > var7) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  var9 = var5 - var7;
               } else {
                  var9 = var7 - var5;
               }

               int var10;
               if (var6 > var8) {
                  var10 = var6 - var8;
               } else {
                  var10 = var8 - var6;
               }

               if (var9 > var10) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  int var11 = var10 * 65536 / var9;
                  int var12 = 32768;

                  while (var7 != var5) {
                     if (var0 != 927500664) {
                        throw new IllegalStateException();
                     }

                     if (var7 < var5) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var7++;
                     } else if (var7 > var5) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var7--;
                     }

                     if (0 != (classIS.field3051.field1700[-483624883 * classIS.field3051.field1708][var7][var8] & 4)) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var1 = classIS.field3051.field1708 * -483624883;
                     }

                     var12 += var11;
                     if (var12 >= 65536) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var12 -= 65536;
                        if (var8 < var6) {
                           if (var0 != 927500664) {
                              throw new IllegalStateException();
                           }

                           var8++;
                        } else if (var8 > var6) {
                           if (var0 != 927500664) {
                              throw new IllegalStateException();
                           }

                           var8--;
                        }

                        if (0 != (classIS.field3051.field1700[classIS.field3051.field1708 * -483624883][var7][var8] & 4)) {
                           if (var0 != 927500664) {
                              throw new IllegalStateException();
                           }

                           var1 = -483624883 * classIS.field3051.field1708;
                        }
                     }
                  }
               } else if (var10 > 0) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  int var14 = 65536 * var9 / var10;
                  int var15 = 32768;

                  while (var8 != var6) {
                     if (var0 != 927500664) {
                        throw new IllegalStateException();
                     }

                     if (var8 < var6) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var8++;
                     } else if (var8 > var6) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var8--;
                     }

                     if ((classIS.field3051.field1700[-483624883 * classIS.field3051.field1708][var7][var8] & 4) != 0) {
                        var1 = -483624883 * classIS.field3051.field1708;
                     }

                     var15 += var14;
                     if (var15 >= 65536) {
                        if (var0 != 927500664) {
                           throw new IllegalStateException();
                        }

                        var15 -= 65536;
                        if (var7 < var5) {
                           if (var0 != 927500664) {
                              throw new IllegalStateException();
                           }

                           var7++;
                        } else if (var7 > var5) {
                           if (var0 != 927500664) {
                              throw new IllegalStateException();
                           }

                           var7--;
                        }

                        if (0 != (classIS.field3051.field1700[classIS.field3051.field1708 * -483624883][var7][var8] & 4)) {
                           if (var0 != 927500664) {
                              throw new IllegalStateException();
                           }

                           var1 = classIS.field3051.field1708 * -483624883;
                        }
                     }
                  }
               }
            }

            if (!WorldView.method3745(classIS.field3051, var2, var3, (byte)1)) {
               if (var0 != 927500664) {
                  throw new IllegalStateException();
               } else {
                  return classIS.field3051.field1708 * -483624883;
               }
            } else {
               if (0 != (classIS.field3051.field1700[classIS.field3051.field1708 * -483624883][var2][var3] & 4)) {
                  if (var0 != 927500664) {
                     throw new IllegalStateException();
                  }

                  var1 = classIS.field3051.field1708 * -483624883;
               }

               return var1;
            }
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "cz.ch(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("by")
   static final void method2849(byte var0) {
      try {
         for (WorldView var2 : client.field815) {
            Iterator var3 = var2.field1696.iterator();

            while (var3.hasNext()) {
               if (var0 == 50) {
                  throw new IllegalStateException();
               }

               NPC var4 = (NPC)var3.next();
               if (var4 != null) {
                  classKN.method6539(var2, var4, -1216348145 * var4.field1579.field5282, -1128559099);
               }
            }

            var2.method3774(-1592433219);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "cz.by(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ap")
   @Override
   public void vmethod293(int var1, int var2, int var3, int var4) {
      RenderOverview.method12533(classIO.method6272((byte)-80), var1, var2, var3, var4, -2183420);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("aq")
   @Override
   public void vmethod294(int var1, int var2, int var3, int var4) {
      RenderOverview.method12533(classIO.method6272((byte)1), var1, var2, var3, var4, -2026251431);
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;[IIII)V")
   @ObfuscatedName("ab")
   public static void method2848(String[] var0, int[] var1, int var2, int var3, int var4) {
      try {
         if (var2 < var3) {
            if (var4 == 667464841) {
               throw new IllegalStateException();
            }

            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            String var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            int var8 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var8;

            for (int var9 = var2; var9 < var3; var9++) {
               if (var4 == 667464841) {
                  throw new IllegalStateException();
               }

               if (null != var7) {
                  if (var4 == 667464841) {
                     throw new IllegalStateException();
                  }

                  if (null == var0[var9]) {
                     continue;
                  }

                  if (var4 == 667464841) {
                     return;
                  }

                  if (var0[var9].compareTo(var7) >= (var9 & 1)) {
                     continue;
                  }

                  if (var4 == 667464841) {
                     throw new IllegalStateException();
                  }
               }

               String var10 = var0[var9];
               var0[var9] = var0[var6];
               var0[var6] = var10;
               int var11 = var1[var9];
               var1[var9] = var1[var6];
               var1[var6++] = var11;
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var8;
            method2848(var0, var1, var2, var6 - 1, -1632524354);
            method2848(var0, var1, var6 + 1, var3, 443142101);
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "cz.ab(" + ')');
      }
   }
}
