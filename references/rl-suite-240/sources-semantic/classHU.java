import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hu")
public final class classHU {
   @ObfuscatedName("bt")
   static int field2889;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2888 = 4096;
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   static final int field2890 = 107;
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   static final String field2892 = "jagex.disableBouncyCastle";
   @ObfuscatedName("af")
   final int[] field2887;
   @ToRemove(unused = "true")
   @ObfuscatedName("ca")
   static final int field2891 = 149;

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   final int method6150(int var1, int var2) {
      return this.field2887[var1 + var2 * 64];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwd;Lzl;)V")
   @ObfuscatedName("eo")
   public static void method6155(classWD var0, classZL var1) {
      if (var0 == null) {
         var0.method12400();
      }

      var0.field6666 = var1;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   final int method6151(int var1, int var2) {
      return this.field2887[var1 + var2 * 64];
   }

   classHU(int[] var1) {
      this.field2887 = var1;
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("az")
   final int method6152(int var1, int var2, byte var3) {
      try {
         return this.field2887[var1 + var2 * 64];
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "hu.az(" + 41);
      }
   }

   classHU() {
      this.field2887 = new int[4096];
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   final int method6153(int var1, int var2) {
      return this.field2887[var1 + var2 * 400616896];
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIII)V")
   @ObfuscatedName("em")
   static void method6158(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      try {
         if (18 == var2) {
            if (var8 != 492469016) {
               throw new IllegalStateException();
            }

            client.field958 = -727473855 * var4;
            client.field959 = var5 * 527203475;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = -877699493 * var1;
            int var9 = var3 & 65535;
            int var10 = var3 >> 16;
            classJL var11 = classEF.method3863(classJS.OPOBJ1_V2, client.field796.field1533, (byte)-36);
            var11.field3341.method13211(var10, (byte)38);
            classXY.method13242(var11.field3341, var0 + var6, (byte)16);
            var11.field3341.method13244(var1 + var7, (short)-5004);
            PacketBuffer var10000 = var11.field3341;
            byte var10001;
            if (client.field932.method4563(82, 230349540)) {
               if (var8 != 492469016) {
                  return;
               }

               var10001 = 1;
            } else {
               var10001 = 0;
            }

            var10000.method13214(var10001, (byte)-31);
            var11.field3341.method13031(var9, -1542382992);
            client.field796.method3374(var11, -1301097035);
         }

         if (var2 == 19) {
            if (var8 != 492469016) {
               throw new IllegalStateException();
            }

            client.field958 = -727473855 * var4;
            client.field959 = 527203475 * var5;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = -877699493 * var1;
            int var13 = var3 & 65535;
            int var17 = var3 >> 16;
            classJL var21 = classEF.method3863(classJS.OPOBJ2_V2, client.field796.field1533, (byte)-7);
            var21.field3341.method13211(var17, (byte)34);
            var21.field3341.method13031(var7 + var1, -1542382992);
            classXY.method13242(var21.field3341, var13, (byte)16);
            classXY.method13242(var21.field3341, var0 + var6, (byte)16);
            PacketBuffer var25 = var21.field3341;
            byte var29;
            if (client.field932.method4563(82, 1240789726)) {
               if (var8 != 492469016) {
                  return;
               }

               var29 = 1;
            } else {
               var29 = 0;
            }

            var25.method13209(var29, -1910538783);
            client.field796.method3374(var21, -1301097035);
         }

         if (20 == var2) {
            if (var8 != 492469016) {
               throw new IllegalStateException();
            }

            client.field958 = -727473855 * var4;
            client.field959 = 527203475 * var5;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = -877699493 * var1;
            int var14 = var3 & 65535;
            int var18 = var3 >> 16;
            classJL var22 = classEF.method3863(classJS.OPOBJ3_V2, client.field796.field1533, (byte)35);
            classXY.method13242(var22.field3341, var0 + var6, (byte)16);
            PacketBuffer var26 = var22.field3341;
            byte var30;
            if (client.field932.method4563(82, 1101700517)) {
               if (var8 != 492469016) {
                  return;
               }

               var30 = 1;
            } else {
               var30 = 0;
            }

            var26.method13211(var30, (byte)-28);
            classXY.method13029(var22.field3341, var18, (byte)-110);
            classXY.method13239(var22.field3341, var14, (byte)-71);
            var22.field3341.method13031(var1 + var7, -1542382992);
            client.field796.method3374(var22, -1301097035);
         }

         if (var2 == 21) {
            client.field958 = var4 * -727473855;
            client.field959 = var5 * 527203475;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = -877699493 * var1;
            int var15 = var3 & 65535;
            int var19 = var3 >> 16;
            classJL var23 = classEF.method3863(classJS.OPOBJ4_V2, client.field796.field1533, (byte)-60);
            var23.field3341.method13211(var19, (byte)-10);
            PacketBuffer var27 = var23.field3341;
            byte var31;
            if (client.field932.method4563(82, 901003751)) {
               if (var8 != 492469016) {
                  throw new IllegalStateException();
               }

               var31 = 1;
            } else {
               var31 = 0;
            }

            var27.method13214(var31, (byte)7);
            var23.field3341.method13244(var1 + var7, (short)-1309);
            classXY.method13242(var23.field3341, var15, (byte)16);
            classXY.method13239(var23.field3341, var6 + var0, (byte)-4);
            client.field796.method3374(var23, -1301097035);
         }

         if (22 == var2) {
            if (var8 != 492469016) {
               throw new IllegalStateException();
            }

            client.field958 = -727473855 * var4;
            client.field959 = var5 * 527203475;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = -877699493 * var1;
            int var16 = var3 & 65535;
            int var20 = var3 >> 16;
            classJL var24 = classEF.method3863(classJS.OPOBJ5_V2, client.field796.field1533, (byte)-58);
            var24.field3341.method13244(var16, (short)-32414);
            classXY.method13029(var24.field3341, var20, (byte)-73);
            classXY.method13239(var24.field3341, var6 + var0, (byte)-33);
            PacketBuffer var28 = var24.field3341;
            byte var32;
            if (client.field932.method4563(82, 1261564632)) {
               if (var8 != 492469016) {
                  throw new IllegalStateException();
               }

               var32 = 1;
            } else {
               var32 = 0;
            }

            var28.method13214(var32, (byte)33);
            classXY.method13242(var24.field3341, var1 + var7, (byte)16);
            client.field796.method3374(var24, -1301097035);
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "hu.em(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("bo")
   static int method6157(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (var0 == 3600) {
            if (0 == 215887637 * classQQ.field5595.field594) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -2;
            } else if (215887637 * classQQ.field5595.field594 == 1) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classQQ.field5595.field593.method10243(-1975366404);
            }

            return 1;
         } else if (3601 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var50 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (classQQ.field5595.method1276(-1995215397)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (var50 >= 0) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (var50 < classQQ.field5595.field593.method10243(-265945226)) {
                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        }

                        Friend var53 = (Friend)NameableContainer.method10281(classQQ.field5595.field593, var50, 1650310413);
                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var53.method10220((byte)1);
                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = Nameable.method10224(var53, (byte)91);
                        return 1;
                     }
                  }
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               return 1;
            }
         } else if (3602 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var49 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (classQQ.field5595.method1276(-38469855)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (var49 >= 0) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (var49 < classQQ.field5595.field593.method10243(-1705074353)) {
                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        }

                        classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ((ChatPlayer)NameableContainer.method10281(
                                 classQQ.field5595.field593, var49, 1759341845
                              ))
                              .field5667
                           * -760305529;
                        return 1;
                     }
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               return 1;
            }
         } else if (3603 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var48 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (classQQ.field5595.method1276(-1945240379)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (var48 >= 0) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (var48 < classQQ.field5595.field593.method10243(-1811344605)) {
                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        }

                        classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ((ChatPlayer)NameableContainer.method10281(
                                 classQQ.field5595.field593, var48, 1710017987
                              ))
                              .field5669
                           * 1234122453;
                        return 1;
                     }
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               return 1;
            }
         } else if (3604 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var47 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               int var52 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classCR.method2565(var47, var52, -2124018804);
               return 1;
            }
         } else if (3605 == var0) {
            String var46 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
            classQQ.field5595.method1305(var46, -850092971);
            return 1;
         } else if (var0 == 3606) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var45 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               classQQ.field5595.method1325(var45, (byte)38);
               return 1;
            }
         } else if (3607 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var44 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               classBW.method1319(classQQ.field5595, var44, -169290065);
               return 1;
            }
         } else if (3608 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var43 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               boolean var51 = true;
               classQQ.field5595.method1331(var43, var51, 1787057995);
               return 1;
            }
         } else if (3609 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var41 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               var41 = classKW.method6620(var41, -391222627);
               int[] var74 = classBB.field323;
               int var77 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var80;
               if (classQQ.field5595.method1295(new classAAE(var41, WidgetNode.field5277), false, 728254179)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var80 = 1;
               } else {
                  var80 = 0;
               }

               var74[var77] = var80;
               return 1;
            }
         } else if (3611 == var0) {
            if (classIB.field2933 != null) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classIB.field2933.field5858;
            } else {
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
            }

            return 1;
         } else if (3612 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               if (null != classIB.field2933) {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classIB.field2933.method10243(-1805895116);
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               }

               return 1;
            }
         } else if (3613 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var40 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (classIB.field2933 != null && var40 < classIB.field2933.method10243(-2018528307)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classAAE.method157(
                     NameableContainer.method10281(classIB.field2933, var40, 1740076631).method10216((byte)16), 925124102
                  );
               } else {
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               }

               return 1;
            }
         } else if (3614 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var39 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (null != classIB.field2933 && var39 < classIB.field2933.method10243(-1010776819)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ((ChatPlayer)NameableContainer.method10281(
                        classIB.field2933, var39, 1814276808
                     ))
                     .method10007(1888854034);
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               }

               return 1;
            }
         } else if (3615 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var38 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (null != classIB.field2933 && var38 < classIB.field2933.method10243(-1796962933)) {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ((ChatPlayer)NameableContainer.method10281(
                           classIB.field2933, var38, 1388187095
                        ))
                        .field5669
                     * 1234122453;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               }

               return 1;
            }
         } else if (3616 == var0) {
            int[] var73 = classBB.field323;
            int var76 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
            byte var79;
            if (null != classIB.field2933) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               var79 = classIB.field2933.field5863;
            } else {
               var79 = 0;
            }

            var73[var76] = var79;
            return 1;
         } else if (var0 == 3617) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var37 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               classCV.method2683(var37, (byte)37);
               return 1;
            }
         } else if (3618 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int[] var72 = classBB.field323;
               int var75 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               int var78;
               if (classIB.field2933 != null) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var78 = classIB.field2933.field5864 * 1979783621;
               } else {
                  var78 = 0;
               }

               var72[var75] = var78;
               return 1;
            }
         } else if (3619 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var36 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               classIJ.method6260(var36, -1142457594);
               return 1;
            }
         } else if (3620 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               classIO.method6273((byte)-59);
               return 1;
            }
         } else if (var0 == 3621) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               if (!classQQ.field5595.method1276(-1462513166)) {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classQQ.field5595.field591.method10243(-396855415);
               }

               return 1;
            }
         } else if (var0 == 3622) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var35 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (classQQ.field5595.method1276(-1335537384)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (var35 >= 0) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (var35 < classQQ.field5595.field591.method10243(-823470546)) {
                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        }

                        Ignore var5 = (Ignore)NameableContainer.method10281(classQQ.field5595.field591, var35, 1297124491);
                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var5.method10220((byte)1);
                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = Nameable.method10224(var5, (byte)40);
                        return 1;
                     }
                  }
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               return 1;
            }
         } else if (var0 == 3623) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               String var33 = (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
               var33 = classKW.method6620(var33, -1592022605);
               int[] var71 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (classQQ.field5595.method1299(new classAAE(var33, WidgetNode.field5277), -1181618808)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var71[var10001] = var10002;
               return 1;
            }
         } else if (3624 == var0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               int var32 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (null != classIB.field2933) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (var32 < classIB.field2933.method10243(-1481923975)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     if (NameableContainer.method10281(classIB.field2933, var32, 2092533098)
                        .method10216((byte)16)
                        .equals(classOE.field4841.vmethod255((byte)101))) {
                        classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                        return 1;
                     }
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               return 1;
            }
         } else if (var0 == 3625) {
            if (null != classIB.field2933) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (classIB.field2933.field5862 != null) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classIB.field2933.field5862;
                  return 1;
               }
            }

            classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
            return 1;
         } else if (3626 == var0) {
            int var31 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            if (classIB.field2933 != null && var31 < classIB.field2933.method10243(-647202361)) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (((FriendsChatMember)NameableContainer.method10281(classIB.field2933, var31, 2132647428)).method10322(1742624293)) {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                  return 1;
               }
            }

            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            return 1;
         } else if (3627 != var0) {
            if (var0 == 3628) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classQQ.field5595.field593.method10309(-2131694580);
                  return 1;
               }
            } else if (3629 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var70;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var70 = true;
                  } else {
                     var70 = false;
                  }

                  boolean var30 = var70;
                  NameableContainer.method10310(classQQ.field5595.field593, new classYC(var30), 1419690830);
                  return 1;
               }
            } else if (3630 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var69;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var69 = true;
                  } else {
                     var69 = false;
                  }

                  boolean var29 = var69;
                  NameableContainer.method10310(classQQ.field5595.field593, new classYS(var29), 2098901630);
                  return 1;
               }
            } else if (3631 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var68;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var68 = true;
                  } else {
                     var68 = false;
                  }

                  boolean var28 = var68;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEV(var28), 997350778);
                  return 1;
               }
            } else if (var0 == 3632) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var67;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var67 = true;
                  } else {
                     var67 = false;
                  }

                  boolean var27 = var67;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEA(var27), -1030307237);
                  return 1;
               }
            } else if (3633 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var66;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var66 = true;
                  } else {
                     var66 = false;
                  }

                  boolean var26 = var66;
                  NameableContainer.method10310(classQQ.field5595.field593, new classED(var26), 1203228726);
                  return 1;
               }
            } else if (3634 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var65;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var65 = true;
                  } else {
                     var65 = false;
                  }

                  boolean var25 = var65;
                  NameableContainer.method10310(classQQ.field5595.field593, new classES(var25), 673065149);
                  return 1;
               }
            } else if (var0 == 3635) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var64;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var64 = true;
                  } else {
                     var64 = false;
                  }

                  boolean var24 = var64;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEK(var24), 1731973139);
                  return 1;
               }
            } else if (3636 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var63;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var63 = true;
                  } else {
                     var63 = false;
                  }

                  boolean var23 = var63;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEJ(var23), 1678940149);
                  return 1;
               }
            } else if (3637 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var62;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var62 = true;
                  } else {
                     var62 = false;
                  }

                  boolean var22 = var62;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEL(var22), -376068010);
                  return 1;
               }
            } else if (3638 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var21 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEH(var21), -89086832);
                  return 1;
               }
            } else if (3639 == var0) {
               classQQ.field5595.field593.method10284((byte)11);
               return 1;
            } else if (3640 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classQQ.field5595.field591.method10309(-2131694580);
                  return 1;
               }
            } else if (3641 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var20 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
                  NameableContainer.method10310(classQQ.field5595.field591, new classYC(var20), -723922616);
                  return 1;
               }
            } else if (3642 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var61;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var61 = true;
                  } else {
                     var61 = false;
                  }

                  boolean var19 = var61;
                  NameableContainer.method10310(classQQ.field5595.field591, new classYS(var19), -1825666007);
                  return 1;
               }
            } else if (3643 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  classQQ.field5595.field591.method10284((byte)42);
                  return 1;
               }
            } else if (var0 == 3644) {
               if (classIB.field2933 != null) {
                  classIB.field2933.method10309(-2131694580);
               }

               return 1;
            } else if (var0 == 3645) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var18 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
                  if (null != classIB.field2933) {
                     NameableContainer.method10310(classIB.field2933, new classYC(var18), -788685941);
                  }

                  return 1;
               }
            } else if (3646 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var60;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var60 = true;
                  } else {
                     var60 = false;
                  }

                  boolean var17 = var60;
                  if (null != classIB.field2933) {
                     NameableContainer.method10310(classIB.field2933, new classYS(var17), -431056583);
                  }

                  return 1;
               }
            } else if (3647 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var16 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
                  if (null != classIB.field2933) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     NameableContainer.method10310(classIB.field2933, new classEV(var16), 1741176929);
                  }

                  return 1;
               }
            } else if (3648 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var15 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
                  if (null != classIB.field2933) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     NameableContainer.method10310(classIB.field2933, new classEA(var15), -1058933788);
                  }

                  return 1;
               }
            } else if (var0 == 3649) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var59;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var59 = true;
                  } else {
                     var59 = false;
                  }

                  boolean var14 = var59;
                  if (classIB.field2933 != null) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     NameableContainer.method10310(classIB.field2933, new classED(var14), -1230854395);
                  }

                  return 1;
               }
            } else if (3650 == var0) {
               boolean var58;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var58 = true;
               } else {
                  var58 = false;
               }

               boolean var13 = var58;
               if (classIB.field2933 != null) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  NameableContainer.method10310(classIB.field2933, new classES(var13), 1303752651);
               }

               return 1;
            } else if (var0 == 3651) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var57;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var57 = true;
                  } else {
                     var57 = false;
                  }

                  boolean var12 = var57;
                  if (classIB.field2933 != null) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     NameableContainer.method10310(classIB.field2933, new classEK(var12), 1901866740);
                  }

                  return 1;
               }
            } else if (3652 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var56;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var56 = true;
                  } else {
                     var56 = false;
                  }

                  boolean var11 = var56;
                  if (null != classIB.field2933) {
                     NameableContainer.method10310(classIB.field2933, new classEJ(var11), 103868125);
                  }

                  return 1;
               }
            } else if (var0 == 3653) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var55;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var55 = true;
                  } else {
                     var55 = false;
                  }

                  boolean var10 = var55;
                  if (null != classIB.field2933) {
                     NameableContainer.method10310(classIB.field2933, new classEL(var10), 235917122);
                  }

                  return 1;
               }
            } else if (3654 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var9 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1;
                  if (classIB.field2933 != null) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     NameableContainer.method10310(classIB.field2933, new classEH(var9), -1534611160);
                  }

                  return 1;
               }
            } else if (3655 == var0) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  if (classIB.field2933 != null) {
                     classIB.field2933.method10284((byte)36);
                  }

                  return 1;
               }
            } else if (var0 == 3656) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               } else {
                  boolean var54;
                  if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     var54 = true;
                  } else {
                     var54 = false;
                  }

                  boolean var8 = var54;
                  NameableContainer.method10310(classQQ.field5595.field593, new classEG(var8), 1203362536);
                  return 1;
               }
            } else if (var0 == 3657) {
               boolean var10000;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var7 = var10000;
               if (null != classIB.field2933) {
                  NameableContainer.method10310(classIB.field2933, new classEG(var7), 1792264609);
               }

               return 1;
            } else {
               return 2;
            }
         } else {
            int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            if (classIB.field2933 != null) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (var4 < classIB.field2933.method10243(-1948161542)) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  if (((FriendsChatMember)NameableContainer.method10281(classIB.field2933, var4, 1433024429)).method10332(-1714319706)) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                     return 1;
                  }
               }
            }

            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            return 1;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hu.bo(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;Ljava/lang/String;)V")
   @ObfuscatedName("sl")
   public static void method6156(NPC var0, String var1) {
      if (var0 == null) {
         var0.method3452(var1);
      }

      var0.field1569 = var1 == null ? "" : var1;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("az")
   public static int method6154(int var0, int var1, int var2) {
      try {
         int var3 = 1;

         while (var1 > 1) {
            if (var2 != -474194162) {
               throw new IllegalStateException();
            }

            if ((var1 & 1) != 0) {
               if (var2 != -474194162) {
                  throw new IllegalStateException();
               }

               var3 *= var0;
            }

            var0 *= var0;
            var1 >>= 1;
         }

         if (var1 != 1) {
            return var3;
         } else if (var2 != -474194162) {
            throw new IllegalStateException();
         } else {
            return var3 * var0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "hu.az(" + 41);
      }
   }
}
