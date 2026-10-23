import net.runelite.api.events.MenuOptionClicked;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qp")
public class classQP {
   @ObfuscatedSignature(descriptor = "Lqp;")
   @ObfuscatedName("af")
   static final classQP field5580 = new classQP();
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   public static final int field5581 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   public static final int field5582 = 62;
   @ToRemove(unused = "true")
   @ObfuscatedName("dt")
   static final int field5583 = 128;
   @ObfuscatedSignature(descriptor = "Lqp;")
   @ObfuscatedName("az")
   public static final classQP field5579 = new classQP();
   @ObfuscatedName("ct")
   static String field5584;

   @ObfuscatedSignature(descriptor = "(IIIIIILjava/lang/String;Ljava/lang/String;IIB)V")
   @ObfuscatedName("ef")
   public static final void method9746(int var0, int var1, int var2, int var3, int var4, int var5, String var6, String var7, int var8, int var9, byte var10) {
      rl5 var11 = client.method2330(client.field975, var0, var1, var2, var3, var4, var5, var6, var7);
      boolean var12 = false;
      if (classON.field4991 != null) {
         var12 = classON.field4991.method1368() == var2
            && classON.field4991.method1374() == var3
            && classON.field4991.method1369() == var6
            && classON.field4991.method1362() == var7
            && classON.field4991.method1375() == var0
            && classON.field4991.method1373() == var1
            && classON.field4991.method1360() == var4
            && classON.field4991.method1370() == var5;
      }

      if (var11 == null && var12) {
         int var13;
         if (client.field975.field6795 * -1927903443 < 500) {
            int var10000 = client.field975.field6795 * -1927903443;
            client.field975.field6795 = (client.field975.field6795 * -1927903443 + 1) * 1932681381;
            var13 = var10000;
         } else {
            var13 = 0;
         }

         client.field975.field6798[var13] = var2;
         client.field975.field6799[var13] = var3;
         client.field975.field6802[var13] = var6;
         client.field975.field6803[var13] = var7;
         client.field975.field6796[var13] = var0;
         client.field975.field6797[var13] = var1;
         client.field975.field6800[var13] = var4;
         client.field975.field6801[var13] = var5;
         client.field975.field6805[var13] = false;
         client.field975.field6804[var13] = null;
         var11 = client.field975.field6807[var13];
         if (var11 == null) {
            var11 = client.field975.field6807[var13] = new rl5(client.field975, var13);
         }

         var11.field5801 = classON.field4991.field617;
      }

      if (var11 == null) {
         if (var8 != -1 || var9 != -1) {
            client.field1028
               .warn("Unable to find clicked menu op {} targ {} action {} id {} p0 {} p1 {} world {}", new Object[]{var6, var7, var2, var3, var0, var1, var5});
         }
      } else {
         client.field1028.trace("Menu click op {} targ {} action {} id {} p0 {} p1 {} world {}", new Object[]{var6, var7, var2, var3, var0, var1, var5});
         MenuOptionClicked var36 = new MenuOptionClicked(var11);
         classOE.field4841.getCallbacks().post(var36);
         if (var11.field5801 != null) {
            try {
               var11.field5801.accept(var11);
            } catch (Exception var34) {
               client.field1028.warn("exception in menu callback", var34);
            }
         }

         if (var36.isConsumed()) {
            return;
         }

         var0 = var11.getParam0();
         var1 = var11.getParam1();
         var3 = var11.getIdentifier();
      }

      int var24 = var9;
      int var23 = var8;
      String var22 = var7;
      int var20 = var5;
      int var19 = var4;
      int var18 = var3;
      int var17 = var2;
      int var16 = var1;
      int var15 = var0;
      byte var25 = -44;

      try {
         WorldView var26 = client.field815.method1584(var20, (short)-10131);
         if (var26 != null) {
            int var27 = -1444178379 * var26.field1706;
            int var28 = -351145363 * var26.field1707;
            IndexedObjectSet var29 = var26.field1696;
            IndexedObjectSet var30 = var26.field1692;
            if (var17 >= 2000) {
               if (var25 >= 1) {
                  return;
               }

               var17 -= 2000;
            }

            classIK.method6263(var15, var16, var17, var18, var23, var24, var27, var28, -1377982978);
            if (var17 == 14) {
               Player var31 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (null != var31) {
                  client.field958 = -727473855 * var23;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = -877699493 * var16;
                  classJL var32 = classEF.method3863(classJS.OPPLAYERU, client.field796.field1533, (byte)-111);
                  classXY.method13239(var32.field3341, -1146573313 * classIR.field3049, (byte)-51);
                  classXY.method13290(var32.field3341, classJC.field3135 * -896172231, -796948523);
                  PacketBuffer var94 = var32.field3341;
                  byte var10001;
                  if (client.field932.method4563(82, 242848078)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var10001 = 1;
                  } else {
                     var10001 = 0;
                  }

                  classXY.method13029(var94, var10001, (byte)-32);
                  var32.field3341.method13244(1622907325 * classIJ.field2979, (short)-15818);
                  var32.field3341.method13244(var18, (short)-30940);
                  client.field796.method3374(var32, -1301097035);
               }
            }

            if (64 == var17) {
               if (var25 >= 1) {
                  return;
               }

               client.field958 = var23 * -727473855;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = var16 * -877699493;
               classJL var37 = classEF.method3863(classJS.OPWORLDENTITY2, client.field796.field1533, (byte)-17);
               PacketBuffer var95 = var37.field3341;
               byte var113;
               if (client.field932.method4563(82, 1374077670)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var113 = 1;
               } else {
                  var113 = 0;
               }

               var95.method13209(var113, -1910538783);
               var37.field3341.method13031(var18, -1542382992);
               client.field796.method3374(var37, -1301097035);
            }

            if (29 == var17) {
               classJL var38 = classEF.method3863(classJS.IF_BUTTON, client.field796.field1533, (byte)-114);
               var38.field3341.method13037(var16, 1087780475);
               client.field796.method3374(var38, -1301097035);
               Widget var70 = classWK.field6689.method7421(var16, (byte)90);
               if (null != var70) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  if (null != var70.field4397) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (5 == var70.field4397[0][0]) {
                        if (var25 >= 1) {
                           throw new IllegalStateException();
                        }

                        int var33 = var70.field4397[0][1];
                        if (var70.field4399[0] != classLB.field4088[var33]) {
                           if (var25 >= 1) {
                              throw new IllegalStateException();
                           }

                           classLB.field4088[var33] = var70.field4399[0];
                           client.method2241(var33);
                           classXQ.method12935(var33, -1566261717);
                        }
                     }
                  }
               }
            }

            label967: {
               if (1008 != var17) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  if (1009 != var17) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (1010 != var17) {
                        if (var25 >= 1) {
                           return;
                        }

                        if (var17 != 1011) {
                           if (1012 != var17) {
                              break label967;
                           }

                           if (var25 >= 1) {
                              throw new IllegalStateException();
                           }
                        }
                     }
                  }
               }

               RenderOverview.method12650(client.field1124, var17, var18, new classKY(var15), new classKY(var16), 1153408874);
            }

            if (30 == var17) {
               if (var25 >= 1) {
                  return;
               }

               if (classWK.field6689.method12234((byte)97)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  classUL.method11465(var16, var15, 1283248075);
                  classWK.field6689.method12238(classWK.field6689.method7422(var16, var15, (byte)23), 103438946);
               }
            }

            if (17 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = var23 * -727473855;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = -877699493 * var16;
               classJL var39 = classEF.method3863(classJS.OPOBJT, client.field796.field1533, (byte)-4);
               var39.field3341.method13031(var27 + var15, -1542382992);
               var39.field3341.method13037(565061963 * classBQ.field529, -494359999);
               PacketBuffer var96 = var39.field3341;
               byte var114;
               if (client.field932.method4563(82, 1862151082)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var114 = 1;
               } else {
                  var114 = 0;
               }

               var96.method13214(var114, (byte)-3);
               classXY.method13239(var39.field3341, 1333668003 * client.field981, (byte)16);
               var39.field3341.method13244(var28 + var16, (short)-31202);
               var39.field3341.method13244(var18, (short)-28864);
               var39.field3341.method13031(-2095241843 * client.field982, -1542382992);
               client.field796.method3374(var39, -1301097035);
            }

            if (65 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = -727473855 * var23;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = var16 * -877699493;
               classJL var40 = classEF.method3863(classJS.OPWORLDENTITY3, client.field796.field1533, (byte)-79);
               var40.field3341.method13031(var18, -1542382992);
               PacketBuffer var97 = var40.field3341;
               byte var115;
               if (client.field932.method4563(82, 759805406)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var115 = 1;
               } else {
                  var115 = 0;
               }

               classXY.method13029(var97, var115, (byte)-114);
               client.field796.method3374(var40, -1301097035);
            }

            if (58 == var17) {
               Widget var41 = classWK.field6689.method7422(var16, var15, (byte)-43);
               if (var41 != null) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  if (null != var41.field4252) {
                     if (var25 >= 1) {
                        return;
                     }

                     ScriptEvent var71 = classUQ.method11527(var41, 231327867)
                        .method14195(var41.field4252, (byte)2)
                        .method14204(var18, -1579394610)
                        .method14213(var22, (byte)123)
                        .method14226((byte)31);
                     classSZ.method10771(var71, -1922641172);
                  }

                  classJL var72 = classEF.method3863(classJS.IF_BUTTONT, client.field796.field1533, (byte)25);
                  classXY.method13239(var72.field3341, client.field982 * -2095241843, (byte)-20);
                  var72.field3341.method13037(565061963 * classBQ.field529, -160696217);
                  var72.field3341.method13244(var15, (short)-4924);
                  classXY.method13242(var72.field3341, var19, (byte)16);
                  var72.field3341.method13244(1333668003 * client.field981, (short)-4123);
                  classXY.method13290(var72.field3341, var16, 575053526);
                  client.field796.method3374(var72, -1301097035);
               }
            }

            if (47 == var17) {
               Player var42 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (var42 != null) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = var23 * -727473855;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = var16 * -877699493;
                  classJL var73 = classEF.method3863(classJS.OPPLAYER4, client.field796.field1533, (byte)-7);
                  var73.field3341.method13214(client.field932.method4563(82, 296540644) ? 1 : 0, (byte)76);
                  var73.field3341.method13031(var18, -1542382992);
                  client.field796.method3374(var73, -1301097035);
               }
            }

            classSW.method10742(var29, var15, var16, var17, var18, var23, var24, (short)20431);
            if (23 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               if (client.field974) {
                  var26.field1687.method4361();
               } else {
                  Scene.method4354(var26.field1687, true);
               }
            }

            if (67 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = -727473855 * var23;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = -877699493 * var16;
               classJL var43 = classEF.method3863(classJS.OPWORLDENTITY5, client.field796.field1533, (byte)-41);
               PacketBuffer var98 = var43.field3341;
               byte var116;
               if (client.field932.method4563(82, 1522204832)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var116 = 1;
               } else {
                  var116 = 0;
               }

               var98.method13209(var116, -1910538783);
               var43.field3341.method13244(var18, (short)-20818);
               client.field796.method3374(var43, -1301097035);
            }

            if (var17 == 1004) {
               client.field958 = var23 * -727473855;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               classJL var44 = classEF.method3863(classJS.OPOBJ6, client.field796.field1533, (byte)44);
               classXY.method13239(var44.field3341, var18, (byte)-77);
               var44.field3341.method13244(var28 + var16, (short)-775);
               var44.field3341.method13031(var15 + var27, -1542382992);
               client.field796.method3374(var44, -1301097035);
            }

            if (var17 == 66) {
               if (var25 >= 1) {
                  return;
               }

               client.field958 = -727473855 * var23;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = -877699493 * var16;
               classJL var45 = classEF.method3863(classJS.OPWORLDENTITY4, client.field796.field1533, (byte)13);
               var45.field3341.method13031(var18, -1542382992);
               var45.field3341.method13211(client.field932.method4563(82, -39520575) ? 1 : 0, (byte)-38);
               client.field796.method3374(var45, -1301097035);
            }

            if (var17 == 51) {
               Player var46 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (null != var46) {
                  if (var25 >= 1) {
                     return;
                  }

                  client.field958 = -727473855 * var23;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = var16 * -877699493;
                  classJL var74 = classEF.method3863(classJS.OPPLAYER8, client.field796.field1533, (byte)-56);
                  classXY.method13242(var74.field3341, var18, (byte)16);
                  PacketBuffer var99 = var74.field3341;
                  byte var117;
                  if (client.field932.method4563(82, 1970356523)) {
                     if (var25 >= 1) {
                        return;
                     }

                     var117 = 1;
                  } else {
                     var117 = 0;
                  }

                  classXY.method13029(var99, var117, (byte)-43);
                  client.field796.method3374(var74, -1301097035);
               }
            }

            label915: {
               if (var17 != 57) {
                  if (1007 != var17) {
                     break label915;
                  }

                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }
               }

               Widget var47 = classWK.field6689.method7422(var16, var15, (byte)-84);
               if (null != var47) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  classEA.method3815(var18, var16, var15, var19, var22, -972851303);
               }
            }

            if (45 == var17) {
               Player var48 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (var48 != null) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = -727473855 * var23;
                  client.field959 = var24 * 527203475;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = var16 * -877699493;
                  classJL var75 = classEF.method3863(classJS.OPPLAYER2, client.field796.field1533, (byte)8);
                  PacketBuffer var100 = var75.field3341;
                  byte var118;
                  if (client.field932.method4563(82, 1426972542)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var118 = 1;
                  } else {
                     var118 = 0;
                  }

                  var100.method13214(var118, (byte)-63);
                  classXY.method13239(var75.field3341, var18, (byte)-94);
                  client.field796.method3374(var75, -1301097035);
               }
            }

            if (var17 == 16) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = -727473855 * var23;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = var16 * -877699493;
               classJL var49 = classEF.method3863(classJS.OPOBJU, client.field796.field1533, (byte)33);
               var49.field3341.method13244(classIJ.field2979 * 1622907325, (short)-32490);
               PacketBuffer var101 = var49.field3341;
               byte var119;
               if (client.field932.method4563(82, 199613861)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var119 = 1;
               } else {
                  var119 = 0;
               }

               classXY.method13029(var101, var119, (byte)-39);
               classXY.method13239(var49.field3341, var18, (byte)28);
               classXY.method13239(var49.field3341, -1146573313 * classIR.field3049, (byte)-95);
               var49.field3341.method13031(var16 + var28, -1542382992);
               var49.field3341.method13287(classJC.field3135 * -896172231, -1568292528);
               classXY.method13242(var49.field3341, var15 + var27, (byte)16);
               client.field796.method3374(var49, -1301097035);
            }

            if (var17 == 24) {
               if (var25 >= 1) {
                  return;
               }

               Widget var50 = classWK.field6689.method7421(var16, (byte)80);
               if (var50 != null) {
                  if (var25 >= 1) {
                     return;
                  }

                  boolean var76 = true;
                  if (var50.field4332 * -90794217 > 0) {
                     var76 = classCR.method2566(var50, 843325427);
                  }

                  if (var76) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     classJL var90 = classEF.method3863(classJS.IF_BUTTON, client.field796.field1533, (byte)38);
                     var90.field3341.method13037(var16, 2049446130);
                     client.field796.method3374(var90, -1301097035);
                  }
               }
            }

            if (var17 == 61) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               WorldEntity var51 = (WorldEntity)var26.field1698.method13637(var18);
               if (null != var51) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = -727473855 * var23;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = var15 * -1254957895;
                  client.field835 = var16 * -877699493;
                  classJL var77 = classEF.method3863(classJS.OPWORLDENTITYU, client.field796.field1533, (byte)-39);
                  var77.field3341.method13244(classIJ.field2979 * 1622907325, (short)-3128);
                  var77.field3341.method13037(-896172231 * classJC.field3135, 267871357);
                  var77.field3341.method13244(var18, (short)-3335);
                  classXY.method13239(var77.field3341, -1146573313 * classIR.field3049, (byte)6);
                  PacketBuffer var102 = var77.field3341;
                  byte var120;
                  if (client.field932.method4563(82, 1492528283)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var120 = 1;
                  } else {
                     var120 = 0;
                  }

                  var102.method13209(var120, -1910538783);
                  client.field796.method3374(var77, -1301097035);
               }
            }

            if (8 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               NPC var52 = (NPC)IndexedObjectSet.method13449(var29, var18);
               if (var52 != null) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = var23 * -727473855;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = var15 * -1254957895;
                  client.field835 = -877699493 * var16;
                  classJL var78 = classEF.method3863(classJS.OPNPCT, client.field796.field1533, (byte)19);
                  PacketBuffer var103 = var78.field3341;
                  byte var121;
                  if (client.field932.method4563(82, 674415717)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var121 = 1;
                  } else {
                     var121 = 0;
                  }

                  var103.method13214(var121, (byte)-60);
                  var78.field3341.method13284(565061963 * classBQ.field529, 730941790);
                  var78.field3341.method13031(client.field981 * 1333668003, -1542382992);
                  var78.field3341.method13244(-2095241843 * client.field982, (short)-10174);
                  classXY.method13242(var78.field3341, var18, (byte)16);
                  client.field796.method3374(var78, -1301097035);
               }
            }

            if (var17 == 48) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               Player var53 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (var53 != null) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = var23 * -727473855;
                  client.field959 = var24 * 527203475;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = var15 * -1254957895;
                  client.field835 = -877699493 * var16;
                  classJL var79 = classEF.method3863(classJS.OPPLAYER5, client.field796.field1533, (byte)-23);
                  classXY.method13242(var79.field3341, var18, (byte)16);
                  PacketBuffer var104 = var79.field3341;
                  byte var122;
                  if (client.field932.method4563(82, 1665183896)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var122 = 1;
                  } else {
                     var122 = 0;
                  }

                  var104.method13209(var122, -1910538783);
                  client.field796.method3374(var79, -1301097035);
               }
            }

            if (var17 == 49) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               Player var54 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (var54 != null) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = var23 * -727473855;
                  client.field959 = var24 * 527203475;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = -877699493 * var16;
                  classJL var80 = classEF.method3863(classJS.OPPLAYER6, client.field796.field1533, (byte)-80);
                  PacketBuffer var105 = var80.field3341;
                  byte var123;
                  if (client.field932.method4563(82, -5943396)) {
                     if (var25 >= 1) {
                        return;
                     }

                     var123 = 1;
                  } else {
                     var123 = 0;
                  }

                  var105.method13209(var123, -1910538783);
                  classXY.method13239(var80.field3341, var18, (byte)-75);
                  client.field796.method3374(var80, -1301097035);
               }
            }

            if (1013 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = -727473855 * var23;
               client.field959 = 527203475 * var24;
               client.field864 = -921463806;
               client.field970 = 0;
               classJL var55 = classEF.method3863(classJS.OPWORLDENTITY6, client.field796.field1533, (byte)39);
               classXY.method13242(var55.field3341, var18, (byte)16);
               client.field796.method3374(var55, -1301097035);
            }

            if (var17 == 26) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               classTB.method10780((byte)24);
            }

            classHU.method6158(var15, var16, var17, var18, var23, var24, var27, var28, 492469016);
            if (var17 == 1) {
               if (var25 >= 1) {
                  return;
               }

               client.field958 = var23 * -727473855;
               client.field959 = var24 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = var15 * -1254957895;
               client.field835 = var16 * -877699493;
               classJL var56 = classEF.method3863(classJS.OPLOCU, client.field796.field1533, (byte)-65);
               var56.field3341.method13031(-1146573313 * classIR.field3049, -1542382992);
               classXY.method13239(var56.field3341, 1622907325 * classIJ.field2979, (byte)14);
               classXY.method13239(var56.field3341, var18, (byte)72);
               classXY.method13239(var56.field3341, var16 + var28, (byte)-20);
               var56.field3341.method13244(var27 + var15, (short)-12085);
               PacketBuffer var106 = var56.field3341;
               byte var124;
               if (client.field932.method4563(82, 1247531604)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var124 = 1;
               } else {
                  var124 = 0;
               }

               var106.method13209(var124, -1910538783);
               var56.field3341.method13284(classJC.field3135 * -896172231, 730941790);
               client.field796.method3374(var56, -1301097035);
            }

            if (var17 == 15) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               Player var57 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (null != var57) {
                  client.field958 = var23 * -727473855;
                  client.field959 = var24 * 527203475;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = var16 * -877699493;
                  classJL var81 = classEF.method3863(classJS.OPPLAYERT, client.field796.field1533, (byte)-29);
                  PacketBuffer var107 = var81.field3341;
                  byte var125;
                  if (client.field932.method4563(82, 569211504)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var125 = 1;
                  } else {
                     var125 = 0;
                  }

                  var107.method13211(var125, (byte)66);
                  classXY.method13239(var81.field3341, 1333668003 * client.field981, (byte)-54);
                  var81.field3341.method13287(classBQ.field529 * 565061963, -2018262788);
                  classXY.method13239(var81.field3341, -2095241843 * client.field982, (byte)10);
                  var81.field3341.method13244(var18, (short)-3666);
                  client.field796.method3374(var81, -1301097035);
               }
            }

            if (var17 == 1002) {
               client.field958 = -727473855 * var23;
               client.field959 = var24 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               classJL var58 = classEF.method3863(classJS.OPLOC6, client.field796.field1533, (byte)-16);
               classXY.method13239(var58.field3341, var18, (byte)9);
               client.field796.method3374(var58, -1301097035);
            }

            if (var17 == 44) {
               Player var59 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (null != var59) {
                  if (var25 >= 1) {
                     return;
                  }

                  client.field958 = -727473855 * var23;
                  client.field959 = var24 * 527203475;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = var15 * -1254957895;
                  client.field835 = -877699493 * var16;
                  classJL var82 = classEF.method3863(classJS.OPPLAYER1, client.field796.field1533, (byte)25);
                  PacketBuffer var108 = var82.field3341;
                  byte var126;
                  if (client.field932.method4563(82, 124726501)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var126 = 1;
                  } else {
                     var126 = 0;
                  }

                  var108.method13211(var126, (byte)32);
                  var82.field3341.method13031(var18, -1542382992);
                  client.field796.method3374(var82, -1301097035);
               }
            }

            if (63 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = -727473855 * var23;
               client.field959 = var24 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = -877699493 * var16;
               classJL var60 = classEF.method3863(classJS.OPWORLDENTITY1, client.field796.field1533, (byte)30);
               var60.field3341.method13031(var18, -1542382992);
               PacketBuffer var109 = var60.field3341;
               byte var127;
               if (client.field932.method4563(82, 896480903)) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  var127 = 1;
               } else {
                  var127 = 0;
               }

               var109.method13211(var127, (byte)13);
               client.field796.method3374(var60, -1301097035);
            }

            if (var17 == 7) {
               NPC var61 = (NPC)IndexedObjectSet.method13449(var29, var18);
               if (var61 != null) {
                  client.field958 = var23 * -727473855;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = var15 * -1254957895;
                  client.field835 = -877699493 * var16;
                  classJL var83 = classEF.method3863(classJS.OPNPCU, client.field796.field1533, (byte)-57);
                  classXY.method13239(var83.field3341, classIR.field3049 * -1146573313, (byte)68);
                  PacketBuffer var110 = var83.field3341;
                  byte var128;
                  if (client.field932.method4563(82, 356725132)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var128 = 1;
                  } else {
                     var128 = 0;
                  }

                  var110.method13211(var128, (byte)6);
                  classXY.method13239(var83.field3341, var18, (byte)-115);
                  classXY.method13242(var83.field3341, classIJ.field2979 * 1622907325, (byte)16);
                  var83.field3341.method13037(-896172231 * classJC.field3135, 915138276);
                  client.field796.method3374(var83, -1301097035);
               }
            }

            if (50 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               Player var62 = (Player)IndexedObjectSet.method13449(var30, var18);
               if (null != var62) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = -727473855 * var23;
                  client.field959 = var24 * 527203475;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = var15 * -1254957895;
                  client.field835 = var16 * -877699493;
                  classJL var84 = classEF.method3863(classJS.OPPLAYER7, client.field796.field1533, (byte)-95);
                  PacketBuffer var111 = var84.field3341;
                  byte var129;
                  if (client.field932.method4563(82, 314659735)) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var129 = 1;
                  } else {
                     var129 = 0;
                  }

                  var111.method13209(var129, -1910538783);
                  classXY.method13239(var84.field3341, var18, (byte)74);
                  client.field796.method3374(var84, -1301097035);
               }
            }

            if (2 == var17) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               client.field958 = var23 * -727473855;
               client.field959 = var24 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var15;
               client.field835 = var16 * -877699493;
               classJL var63 = classEF.method3863(classJS.OPLOCT, client.field796.field1533, (byte)-6);
               var63.field3341.method13209(client.field932.method4563(82, 1740903576) ? 1 : 0, -1910538783);
               var63.field3341.method13031(var15 + var27, -1542382992);
               var63.field3341.method13031(var18, -1542382992);
               var63.field3341.method13037(565061963 * classBQ.field529, 344499625);
               classXY.method13239(var63.field3341, 1333668003 * client.field981, (byte)-36);
               classXY.method13239(var63.field3341, var28 + var16, (byte)13);
               classXY.method13242(var63.field3341, client.field982 * -2095241843, (byte)16);
               client.field796.method3374(var63, -1301097035);
            }

            if (var17 == 62) {
               if (var25 >= 1) {
                  return;
               }

               WorldEntity var64 = (WorldEntity)var26.field1698.method13637(var18);
               if (null != var64) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  client.field958 = var23 * -727473855;
                  client.field959 = 527203475 * var24;
                  client.field864 = -921463806;
                  client.field970 = 0;
                  client.field860 = -1254957895 * var15;
                  client.field835 = var16 * -877699493;
                  classJL var85 = classEF.method3863(classJS.OPWORLDENTITYT, client.field796.field1533, (byte)-38);
                  var85.field3341.method13244(var18, (short)-19224);
                  classXY.method13242(var85.field3341, 1333668003 * client.field981, (byte)16);
                  var85.field3341.method13031(client.field982 * -2095241843, -1542382992);
                  var85.field3341.method13037(565061963 * classBQ.field529, 1212251348);
                  var85.field3341.method13214(client.field932.method4563(82, 393261184) ? 1 : 0, (byte)-38);
                  client.field796.method3374(var85, -1301097035);
               }
            }

            if (60 == var17) {
               if (var25 >= 1) {
                  return;
               }

               client.field958 = -727473855 * var23;
               client.field959 = var24 * 527203475;
               client.field864 = 1686751745;
               client.field970 = 0;
               client.field989 = var18 * -2024696235;
               client.field839 = 945732806;
               classJL var65 = classEF.method3863(classJS.SET_HEADING, client.field796.field1533, (byte)-29);
               var65.field3341.method13209(var18, -1910538783);
               client.field796.method3374(var65, -1301097035);
            }

            if (var17 == 1003) {
               client.field958 = -727473855 * var23;
               client.field959 = var24 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               NPC var66 = (NPC)IndexedObjectSet.method13449(var29, var18);
               if (null != var66) {
                  if (var25 >= 1) {
                     return;
                  }

                  NPCComposition var86 = var66.field1579;
                  if (var86.field5323 != null) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     var86 = var86.method8998(652890385);
                  }

                  if (null != var86) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     classJL var91 = classEF.method3863(classJS.OPNPC6, client.field796.field1533, (byte)79);
                     classXY.method13242(var91.field3341, 283969983 * var86.field5300, (byte)16);
                     client.field796.method3374(var91, -1301097035);
                  }
               }
            }

            if (var17 == 25) {
               if (var25 >= 1) {
                  throw new IllegalStateException();
               }

               Widget var67 = classWK.field6689.method7422(var16, var15, (byte)-101);
               if (null != var67) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  ClanChannel.method6234((byte)-76);
                  String var87 = classWK.field6689.method12201(var67, (byte)-103);
                  if (null == var87) {
                     var87 = classKK.field3613;
                  }

                  String var92;
                  if (var67.field4273) {
                     if (var25 >= 1) {
                        return;
                     }

                     var92 = var67.field4307 + classQY.method9885(16777215, (byte)76);
                  } else {
                     var92 = classQY.method9885(65280, (byte)49) + var67.field4315 + classQY.method9885(16777215, (byte)17);
                  }

                  classBQ.method1184(
                     var16, var15, classCK.method1569(classVV.method12246(classWK.field6689, var67, (byte)0), -2122575561), var19, var87, var92, -2085845878
                  );
                  client.field946 = 0;
               }
            } else {
               if (28 == var17) {
                  classJL var68 = classEF.method3863(classJS.IF_BUTTON, client.field796.field1533, (byte)-26);
                  var68.field3341.method13037(var16, 105861394);
                  client.field796.method3374(var68, -1301097035);
                  Widget var88 = classWK.field6689.method7421(var16, (byte)100);
                  if (var88 != null && var88.field4397 != null) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (5 == var88.field4397[0][0]) {
                        if (var25 >= 1) {
                           throw new IllegalStateException();
                        }

                        int var93 = var88.field4397[0][1];
                        classLB.field4088[var93] = 1 - classLB.field4088[var93];
                        client.method2241(var93);
                        classXQ.method12935(var93, 1045365826);
                     }
                  }
               }

               if (var17 == 46) {
                  if (var25 >= 1) {
                     return;
                  }

                  Player var69 = (Player)IndexedObjectSet.method13449(var30, var18);
                  if (null != var69) {
                     if (var25 >= 1) {
                        throw new IllegalStateException();
                     }

                     client.field958 = -727473855 * var23;
                     client.field959 = var24 * 527203475;
                     client.field864 = -921463806;
                     client.field970 = 0;
                     client.field860 = var15 * -1254957895;
                     client.field835 = -877699493 * var16;
                     classJL var89 = classEF.method3863(classJS.OPPLAYER3, client.field796.field1533, (byte)-92);
                     classXY.method13242(var89.field3341, var18, (byte)16);
                     PacketBuffer var112 = var89.field3341;
                     byte var130;
                     if (client.field932.method4563(82, 477111082)) {
                        if (var25 >= 1) {
                           throw new IllegalStateException();
                        }

                        var130 = 1;
                     } else {
                        var130 = 0;
                     }

                     var112.method13209(var130, -1910538783);
                     client.field796.method3374(var89, -1301097035);
                  }
               }

               if (client.field946 * -512792823 != 0) {
                  if (var25 >= 1) {
                     return;
                  }

                  client.field946 = 0;
               }

               if (client.field980) {
                  if (var25 >= 1) {
                     throw new IllegalStateException();
                  }

                  ClanChannel.method6234((byte)-110);
               }
            }
         }
      } catch (RuntimeException var35) {
         throw classEG.method3899(var35, "qp.ef(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;Ljava/lang/Object;IIB)I")
   @ObfuscatedName("ae")
   public static int method9744(classTE var0, Object var1, int var2, int var3, byte var4) {
      try {
         if (var2 < 0) {
            if (var4 != 0) {
               throw new IllegalStateException();
            }

            var2 = 0;
         }

         label114: {
            if (var3 >= 0) {
               if (var4 != 0) {
                  throw new IllegalStateException();
               }

               if (var3 <= var0.method10797(-43506899)) {
                  break label114;
               }

               if (var4 != 0) {
                  throw new IllegalStateException();
               }
            }

            var3 = var0.method10797(-621874871);
         }

         if (classYY.field7109 == var0.field6126) {
            if (var4 != 0) {
               throw new IllegalStateException();
            }

            int[] var5 = var0.method10791(1010647549);
            int var6 = (Integer)var1;

            for (int var7 = var3 - 1; var7 >= var2; var7--) {
               if (var4 != 0) {
                  throw new IllegalStateException();
               }

               if (var6 == var5[var7]) {
                  if (var4 != 0) {
                     throw new IllegalStateException();
                  }

                  return var7;
               }
            }
         } else if (classYY.field7113 == var0.field6126) {
            if (var4 != 0) {
               throw new IllegalStateException();
            }

            long[] var10 = var0.method10793(-1712873289);
            long var12 = (Long)var1;

            for (int var8 = var3 - 1; var8 >= var2; var8--) {
               if (var4 != 0) {
                  throw new IllegalStateException();
               }

               if (var12 == var10[var8]) {
                  if (var4 != 0) {
                     throw new IllegalStateException();
                  }

                  return var8;
               }
            }
         } else {
            Object[] var11 = var0.method10796(1530373053);
            int var13 = var3 - 1;

            while (var13 >= var2) {
               if (var1 != var11[var13]) {
                  if (var4 != 0) {
                     throw new IllegalStateException();
                  }

                  if (null == var11[var13] || !var11[var13].equals(var1)) {
                     var13--;
                     continue;
                  }
               }

               return var13;
            }
         }

         return -1;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "qp.ae(" + 41);
      }
   }

   classQP() {
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("az")
   static final boolean method9745(int var0, int var1) {
      try {
         if (14 != var0) {
            if (var1 == -1383766378) {
               throw new IllegalStateException();
            }

            if (15 != var0) {
               if (var1 == -1383766378) {
                  throw new IllegalStateException();
               }

               if (var0 >= 44) {
                  if (var1 == -1383766378) {
                     throw new IllegalStateException();
                  }

                  if (var0 <= 51) {
                     if (var1 == -1383766378) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }

               return false;
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qp.az(" + ')');
      }
   }
}
