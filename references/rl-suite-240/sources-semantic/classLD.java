import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ld")
public class classLD {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4110 = 40000;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field4109 = 0;

   classLD() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)B")
   @ObfuscatedName("ah")
   public static byte method6797(classXY var0) {
      return (byte)(1569037615 - var0.field6952[(var0.field6953 += -2107936509) * -1624266703 - 1]);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lru;)V")
   @ObfuscatedName("bh")
   public static void method6794(FriendsChatMember var0) {
      if (var0 == null) {
         var0.method10334();
      }

      var0.field5844 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIB)V")
   @ObfuscatedName("ao")
   static final void method6796(int var0, int var1, int var2, int var3, int var4, byte var5) {
      try {
         Scene var6 = classIS.field3051.field1687;
         long var7 = var6.method4461(var0, var1, var2);
         if (var7 != 0L) {
            if (var5 != 1) {
               return;
            }

            int var9 = classKK.method6532(var6, var0, var1, var2, var7);
            int var10 = var9 >> 6 & 3;
            int var11 = var9 & 31;
            int var12 = var3;
            boolean var10000;
            if (var7 != 0L) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var14 = var10000;
            if (var14) {
               if (var5 != 1) {
                  return;
               }

               boolean var15 = (int)(var7 >>> 19 & 1L) == 1;
               var14 = !var15;
            }

            if (var14) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               var12 = var4;
            }

            int[] var33 = classRD.field5643.field7003;
            int var36 = 4 * var1 + 24624 + (103 - var2) * 2048;
            int var16 = classCN.method2542(var7);
            ObjectComposition var17 = classMU.method7771(var16, 2132280389);
            if (var17.field4952 * 1680279607 != -1) {
               if (var5 != 1) {
                  return;
               }

               IndexedSprite var18 = classOY.field5200[1680279607 * var17.field4952];
               if (null != var18) {
                  int var19 = (var17.field4942 * 1965741260 - var18.field7119) / 2;
                  int var20 = (1611505692 * var17.field4922 - var18.field7117) / 2;
                  IndexedSprite.method13853(var18, 48 + 4 * var1 + var19, var20 + 4 * (104 - var2 - var17.field4922 * 402876423) + 48);
               }
            } else {
               if (0 == var11 || 2 == var11) {
                  if (0 == var10) {
                     if (var5 != 1) {
                        return;
                     }

                     var33[var36] = var12;
                     var33[var36 + 512] = var12;
                     var33[var36 + 1024] = var12;
                     var33[1536 + var36] = var12;
                  } else if (1 == var10) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var33[var36] = var12;
                     var33[1 + var36] = var12;
                     var33[2 + var36] = var12;
                     var33[var36 + 3] = var12;
                  } else if (var10 == 2) {
                     var33[3 + var36] = var12;
                     var33[512 + 3 + var36] = var12;
                     var33[var36 + 3 + 1024] = var12;
                     var33[1536 + 3 + var36] = var12;
                  } else if (var10 == 3) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var33[1536 + var36] = var12;
                     var33[1536 + var36 + 1] = var12;
                     var33[var36 + 1536 + 2] = var12;
                     var33[3 + 1536 + var36] = var12;
                  }
               }

               if (var11 == 3) {
                  if (var10 == 0) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var33[var36] = var12;
                  } else if (1 == var10) {
                     if (var5 != 1) {
                        return;
                     }

                     var33[3 + var36] = var12;
                  } else if (var10 == 2) {
                     if (var5 != 1) {
                        return;
                     }

                     var33[1536 + 3 + var36] = var12;
                  } else if (var10 == 3) {
                     if (var5 != 1) {
                        return;
                     }

                     var33[var36 + 1536] = var12;
                  }
               }

               if (var11 == 2) {
                  if (var5 != 1) {
                     return;
                  }

                  if (3 == var10) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var33[var36] = var12;
                     var33[512 + var36] = var12;
                     var33[var36 + 1024] = var12;
                     var33[1536 + var36] = var12;
                  } else if (var10 == 0) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var33[var36] = var12;
                     var33[var36 + 1] = var12;
                     var33[2 + var36] = var12;
                     var33[3 + var36] = var12;
                  } else if (var10 == 1) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var33[3 + var36] = var12;
                     var33[var36 + 3 + 512] = var12;
                     var33[3 + var36 + 1024] = var12;
                     var33[1536 + 3 + var36] = var12;
                  } else if (var10 == 2) {
                     var33[1536 + var36] = var12;
                     var33[1 + var36 + 1536] = var12;
                     var33[2 + var36 + 1536] = var12;
                     var33[1536 + var36 + 3] = var12;
                  }
               }
            }
         }

         var7 = rl0.method10065(var6, var0, var1, var2);
         if (var7 != 0L) {
            if (var5 != 1) {
               throw new IllegalStateException();
            }

            int var24 = classKK.method6532(var6, var0, var1, var2, var7);
            int var26 = var24 >> 6 & 3;
            int var28 = var24 & 31;
            int var30 = classCN.method2542(var7);
            ObjectComposition var13 = classMU.method7771(var30, 2143717999);
            if (-1 != 1680279607 * var13.field4952) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               IndexedSprite var34 = classOY.field5200[var13.field4952 * 1680279607];
               if (null != var34) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  int var37 = (1965741260 * var13.field4942 - var34.field7119) / 2;
                  int var38 = (1611505692 * var13.field4922 - var34.field7117) / 2;
                  IndexedSprite.method13853(var34, var37 + 48 + 4 * var1, var38 + (104 - var2 - var13.field4922 * 402876423) * 4 + 48);
               }
            } else {
               label279:
               if (var28 == 9) {
                  if (var5 != 1) {
                     throw new IllegalStateException();
                  }

                  int var35 = 15658734;
                  boolean var39 = var7 != 0L;
                  if (var39) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     boolean var41 = 1 == (int)(var7 >>> 19 & 1L);
                     boolean var43;
                     if (!var41) {
                        if (var5 != 1) {
                           throw new IllegalStateException();
                        }

                        var43 = true;
                     } else {
                        var43 = false;
                     }

                     var39 = var43;
                  }

                  if (var39) {
                     if (var5 != 1) {
                        throw new IllegalStateException();
                     }

                     var35 = 15597568;
                  }

                  int[] var40 = classRD.field5643.field7003;
                  int var42 = 4 * var1 + 24624 + (103 - var2) * 2048;
                  if (0 != var26) {
                     if (var5 != 1) {
                        return;
                     }

                     if (var26 != 2) {
                        var40[var42] = var35;
                        var40[var42 + 512 + 1] = var35;
                        var40[1024 + var42 + 2] = var35;
                        var40[1536 + var42 + 3] = var35;
                        break label279;
                     }

                     if (var5 != 1) {
                        return;
                     }
                  }

                  var40[var42 + 1536] = var35;
                  var40[1 + var42 + 1024] = var35;
                  var40[512 + var42 + 2] = var35;
                  var40[var42 + 3] = var35;
               }
            }
         }

         var7 = var6.method4413(var0, var1, var2);
         if (0L != var7) {
            if (var5 != 1) {
               throw new IllegalStateException();
            }

            int var25 = classCN.method2542(var7);
            ObjectComposition var27 = classMU.method7771(var25, 1360640909);
            if (-1 != var27.field4952 * 1680279607) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               IndexedSprite var29 = classOY.field5200[var27.field4952 * 1680279607];
               if (var29 != null) {
                  if (var5 != 1) {
                     return;
                  }

                  int var31 = (var27.field4942 * 1965741260 - var29.field7119) / 2;
                  int var32 = (var27.field4922 * 1611505692 - var29.field7117) / 2;
                  IndexedSprite.method13853(var29, var31 + 48 + 4 * var1, var32 + 48 + 4 * (104 - var2 - 402876423 * var27.field4922));
               }
            }
         }
      } catch (RuntimeException var21) {
         throw classEG.method3899(var21, "ld.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;IB)V")
   @ObfuscatedName("ao")
   public static void method6795(classTE var0, int var1, byte var2) {
      try {
         classOL.method8544(var0, 1327640383);
         int var3 = var0.method10797(-940741052);
         if (var3 <= 1) {
            if (var2 <= -1) {
               throw new IllegalStateException();
            }
         } else {
            var1 %= var3;
            if (var1 != 0) {
               if (var1 < 0) {
                  var1 += var3;
               }

               if (var0.field6126 == classYY.field7109) {
                  if (var2 <= -1) {
                     throw new IllegalStateException();
                  }

                  int[] var4 = var0.method10791(1214346702);
                  classLK.method7161(var4, 0, var3 - 1, -576123160);
                  classLK.method7161(var4, 0, var1 - 1, -576123160);
                  classLK.method7161(var4, var1, var3 - 1, -576123160);
               } else if (classYY.field7113 == var0.field6126) {
                  if (var2 <= -1) {
                     throw new IllegalStateException();
                  }

                  long[] var7 = var0.method10793(-1216532977);
                  DynamicObject.method3722(var7, 0, var3 - 1, 1303645518);
                  DynamicObject.method3722(var7, 0, var1 - 1, 2074604693);
                  DynamicObject.method3722(var7, var1, var3 - 1, 212841751);
               } else {
                  Object[] var8 = var0.method10796(773802276);
                  classPJ.method8968(var8, 0, var3 - 1, (byte)120);
                  classPJ.method8968(var8, 0, var1 - 1, (byte)2);
                  classPJ.method8968(var8, var1, var3 - 1, (byte)25);
               }

               var0.field6131 = true;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ld.ao(" + ')');
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   @ObfuscatedSignature(descriptor = "(Lzr;Lbl;III)V")
   @ObfuscatedName("ae")
   static void method6799(ScriptEvent var0, Script var1, int var2, int var3, int var4) {
      client.method2381(var1);
      if (classOE.field4841.field790 > var2) {
         var2 = classOE.field4841.field790;
      }

      if (var3 > 0 && classOE.field4841.field1089 > var3) {
         var3 = classOE.field4841.field1089;
      }

      int var7 = var3;
      int var6 = var2;
      Script var5 = var1;
      ScriptEvent var33 = var0;
      int var8 = 1450398201;

      try {
         Object[] var9 = var33.method14142(1771216401);
         DynamicObject.field1676 = 0;
         classXT.field6937 = 0;
         classBB.field338 = 0;
         client.field1098 = -1;
         int var10 = -1;
         int[] var11 = var5.field501;
         int[] var12 = var5.field502;
         int var13 = -1;
         classBB.field331 = 0;
         classBB.field330 = false;
         boolean var14 = false;
         classBB.field327 = 0;
         boolean var29 = false /* VF: Semaphore variable */;

         label1476: {
            label1477: {
               try {
                  label1432: {
                     var29 = true;
                     classDE.field1407 = new int[var5.field493 * -271078995];
                     int var34 = 0;
                     classDU.field1632 = new long[var5.field492 * 1141638279];
                     int var35 = 0;
                     classML.field4531 = new Object[145871583 * var5.field496];
                     int var36 = 0;

                     for (int var18 = 1; var18 < var9.length; var18++) {
                        if (var8 <= -581658423) {
                           throw new IllegalStateException();
                        }

                        if (var9[var18] instanceof Integer) {
                           if (var8 <= -581658423) {
                              throw new IllegalStateException();
                           }

                           int var19 = (Integer)var9[var18];
                           if (var19 == -2147483647) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              var19 = var33.method14150(-2021259983);
                           }

                           if (var19 == -2147483646) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              var19 = var33.method14154((short)18055);
                           }

                           if (var19 == -2147483645) {
                              int var10000;
                              if (var33.method14144(1470996304) != null) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10000 = var33.method14144(-360630356).field4327 * -1163656551;
                              } else {
                                 var10000 = -1;
                              }

                              var19 = var10000;
                           }

                           if (-2147483644 == var19) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              var19 = var33.method14160(-328310946);
                           }

                           if (var19 == -2147483643) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var87;
                              if (var33.method14144(-1926683648) != null) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var87 = var33.method14144(1965647036).field4258 * 63131743;
                              } else {
                                 var87 = -1;
                              }

                              var19 = var87;
                           }

                           if (-2147483642 == var19) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var88;
                              if (var33.method14162((byte)-102) != null) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var88 = var33.method14162((byte)52).field4327 * -1163656551;
                              } else {
                                 var88 = -1;
                              }

                              var19 = var88;
                           }

                           if (-2147483641 == var19) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var89;
                              if (var33.method14162((byte)73) != null) {
                                 if (var8 <= -581658423) {
                                    var29 = false;
                                    return;
                                 }

                                 var89 = var33.method14162((byte)18).field4258 * 63131743;
                              } else {
                                 var89 = -1;
                              }

                              var19 = var89;
                           }

                           if (-2147483640 == var19) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              var19 = var33.method14163(-773759683);
                           }

                           if (var19 == -2147483639) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              var19 = var33.method14169((short)31829);
                           }

                           if (-2147483638 == var19) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              var19 = var33.method14173(-387940731);
                           }

                           classDE.field1407[var34++] = var19;
                        } else if (var9[var18] instanceof String) {
                           String var53 = (String)var9[var18];
                           if (var53.equals("event_opbase")) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              var53 = var33.method14174(811280628);
                           }

                           classML.field4531[var36++] = var53;
                        } else if (var9[var18] instanceof classTE) {
                           if (var8 <= -581658423) {
                              throw new IllegalStateException();
                           }

                           classML.field4531[var36++] = var9[var18];
                        } else if (var9[var18] instanceof Long) {
                           if (var8 <= -581658423) {
                              throw new IllegalStateException();
                           }

                           long var54 = (Long)var9[var18];
                           classDU.field1632[var35++] = var54;
                        }
                     }

                     classBB.field324 = var33.method14176(1707647081) * 1648640743;

                     while (true) {
                        classBB.field327 += 33484609;
                        if (1023779009 * classBB.field327 > var6) {
                           if (var8 <= -581658423) {
                              throw new IllegalStateException();
                           }

                           throw new RuntimeException();
                        }

                        do {
                           client.field1098 = ++var10;
                           var13 = var11[var10];
                        } while (client.method2371(var13));

                        if (var13 < 100) {
                           if (var8 <= -581658423) {
                              throw new IllegalStateException();
                           }

                           if (var13 == 0) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var12[var10];
                           } else if (1 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var52 = var12[var10];
                              classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classLB.field4088[var52];
                           } else if (var13 == 2) {
                              int var51 = var12[var10];
                              classLB.field4088[var51] = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                              client.method2241(var51);
                              classXQ.method12935(var51, 1311677755);
                           } else if (var13 == 3) {
                              classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var5.method1140(var10, 1160357364);
                           } else if (6 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              var10 = client.field1098 = var10 + var12[var10];
                           } else if (7 == var13) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              DynamicObject.field1676 -= 240082458;
                              if (classBB.field323[DynamicObject.field1676 * -324749371 + 1] != classBB.field323[-324749371 * DynamicObject.field1676]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (8 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              DynamicObject.field1676 -= 240082458;
                              if (classBB.field323[DynamicObject.field1676 * -324749371] == classBB.field323[1 + DynamicObject.field1676 * -324749371]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (9 == var13) {
                              DynamicObject.field1676 -= 240082458;
                              if (classBB.field323[-324749371 * DynamicObject.field1676] < classBB.field323[1 + DynamicObject.field1676 * -324749371]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (10 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              DynamicObject.field1676 -= 240082458;
                              if (classBB.field323[-324749371 * DynamicObject.field1676] > classBB.field323[DynamicObject.field1676 * -324749371 + 1]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (68 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classXT.field6937 -= 1699258970;
                              if (classBB.field322[18294437 * classXT.field6937 + 1] != classBB.field322[18294437 * classXT.field6937]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (var13 == 69) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classXT.field6937 -= 1699258970;
                              if (classBB.field322[18294437 * classXT.field6937 + 1] == classBB.field322[classXT.field6937 * 18294437]) {
                                 if (var8 <= -581658423) {
                                    var29 = false;
                                    return;
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (var13 == 70) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classXT.field6937 -= 1699258970;
                              if (classBB.field322[18294437 * classXT.field6937] < classBB.field322[18294437 * classXT.field6937 + 1]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (var13 == 71) {
                              classXT.field6937 -= 1699258970;
                              if (classBB.field322[18294437 * classXT.field6937] > classBB.field322[18294437 * classXT.field6937 + 1]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (21 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              if (-1626238019 * classBB.field331 == 0) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var29 = false;
                                 break label1477;
                              }

                              classBJ var49 = classBB.field321[(classBB.field331 -= -689455211) * -1626238019];
                              Script var91 = var49.field482;
                              client.method2381(var49.field482);
                              var5 = var91;
                              var11 = var5.field501;
                              var12 = var5.field502;
                              var10 = client.field1098 = var49.field478 * 1565617813;
                              classDE.field1407 = var49.field484;
                              classML.field4531 = var49.field486;
                              classDU.field1632 = var49.field485;
                           } else if (var13 == 25) {
                              int var48 = var12[var10];
                              classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classEM.method3946(var48, -1229922096);
                           } else if (var13 == 27) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var47 = var12[var10];
                              classGP.method5580(var47, classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], -390980656);
                           } else if (var13 == 31) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              DynamicObject.field1676 -= 240082458;
                              if (classBB.field323[DynamicObject.field1676 * -324749371] <= classBB.field323[DynamicObject.field1676 * -324749371 + 1]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (32 == var13) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              DynamicObject.field1676 -= 240082458;
                              if (classBB.field323[DynamicObject.field1676 * -324749371] >= classBB.field323[-324749371 * DynamicObject.field1676 + 1]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (72 == var13) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              classXT.field6937 -= 1699258970;
                              if (classBB.field322[18294437 * classXT.field6937] <= classBB.field322[1 + classXT.field6937 * 18294437]) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (73 == var13) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              classXT.field6937 -= 1699258970;
                              if (classBB.field322[classXT.field6937 * 18294437] >= classBB.field322[1 + classXT.field6937 * 18294437]) {
                                 if (var8 <= -581658423) {
                                    var29 = false;
                                    return;
                                 }

                                 var10 = client.field1098 = var10 + var12[var10];
                              }
                           } else if (33 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classDE.field1407[var12[var10]];
                           } else if (34 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classDE.field1407[var12[var10]] = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                           } else if (var13 == 35) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classML.field4531[var12[var10]];
                           } else if (var13 == 36) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classML.field4531[var12[var10]] = classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
                           } else if (var13 == 37) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var46 = var12[var10];
                              classBB.field338 -= 1222866659 * var46;
                              String var63 = classFO.method4866(classBB.field328, classBB.field338 * 1375698635, var46, (byte)-23);
                              classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var63;
                           } else if (38 == var13) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              DynamicObject.field1676 -= 120041229;
                           } else if (39 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classBB.field338 -= 1222866659;
                           } else if (40 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              if (50 == -1626238019 * classBB.field331) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 throw new RuntimeException();
                              }

                              int var45 = var12[var10];
                              Script var62 = classSK.method10506(var45, 2050893446);
                              int[] var65 = new int[-271078995 * var62.field493];
                              Object[] var21 = new Object[var62.field496 * 145871583];
                              long[] var22 = new long[1141638279 * var62.field492];
                              if (306770163 * var62.field495 >= 0) {
                                 System.arraycopy(
                                    classBB.field323,
                                    -324749371 * DynamicObject.field1676 - var62.field495 * 306770163 + 0,
                                    var65,
                                    0,
                                    306770163 * var62.field495
                                 );
                              }

                              if (var62.field494 * 2120664535 >= 0) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 System.arraycopy(
                                    classBB.field328, classBB.field338 * 1375698635 - 2120664535 * var62.field494 + 0, var21, 0, var62.field494 * 2120664535
                                 );
                              }

                              if (var62.field497 * -1713070491 >= 0) {
                                 if (var8 <= -581658423) {
                                    var29 = false;
                                    return;
                                 }

                                 System.arraycopy(
                                    classBB.field322, 0 + (18294437 * classXT.field6937 - var62.field497 * -1713070491), var22, 0, var62.field497 * -1713070491
                                 );
                              }

                              DynamicObject.field1676 = DynamicObject.field1676 - var62.field495 * 611277143;
                              classBB.field338 = classBB.field338 - var62.field494 * 1509394341;
                              classXT.field6937 = classXT.field6937 - var62.field497 * 369216705;
                              classBJ var23 = new classBJ();
                              var23.field482 = var5;
                              var23.field478 = var10 * -651105091;
                              var23.field484 = classDE.field1407;
                              var23.field486 = classML.field4531;
                              var23.field485 = classDU.field1632;
                              classBB.field321[(classBB.field331 += -689455211) * -1626238019 - 1] = var23;
                              client.method2381(var62);
                              var5 = var62;
                              var11 = var62.field501;
                              var12 = var62.field502;
                              client.field1098 = -1;
                              var10 = -1;
                              classDE.field1407 = var65;
                              classML.field4531 = var21;
                              classDU.field1632 = var22;
                           } else if (var13 == 42) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classSX.field6094
                                 .method3557(var12[var10], -468650129);
                           } else if (var13 == 43) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classDS.method3553(
                                 classSX.field6094, var12[var10], classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], -164759631
                              );
                           } else if (51 == var13) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = classSX.field6094.method3562(var12[var10], -1393162482);
                           } else if (var13 == 52) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classDS.method3559(classSX.field6094, var12[var10], classBB.field322[(classXT.field6937 -= -1297854163) * 18294437]);
                           } else if (44 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              int var44 = var12[var10] >> 16;
                              int var61 = var12[var10] & 65535;
                              int var64 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                              if (var64 < 0) {
                                 break;
                              }

                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              if (var64 > 5000) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }
                                 break;
                              }

                              if (115 == var61) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 classML.field4531[var44] = new classTE(classYY.field7111, "", var64, var64);
                              } else {
                                 if (var61 != 105) {
                                    if (var61 != 49) {
                                       classML.field4531[var44] = new classTE(classYY.field7109, -1, var64, var64);
                                       continue;
                                    }

                                    if (var8 <= -581658423) {
                                       throw new IllegalStateException();
                                    }
                                 }

                                 classML.field4531[var44] = new classTE(classYY.field7109, 0, var64, var64);
                              }
                           } else if (var13 == 45) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classTE var38 = classJY.method6458(var12[var10], classML.field4531[var12[var10]], (byte)-65);
                              int var56 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                              classMA.method7541(var38, var56, 1627665745);
                              if (classYY.field7109 == var38.field6126) {
                                 if (var8 <= -581658423) {
                                    var29 = false;
                                    return;
                                 }

                                 classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var38.method10791(1782946828)[var56];
                              } else if (classYY.field7113 == var38.field6126) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = var38.method10793(-36778881)[var56];
                              } else {
                                 classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var38.method10796(-1972267554)[var56];
                              }
                           } else if (var13 == 46) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              classTE var39 = classJY.method6458(var12[var10], classML.field4531[var12[var10]], (byte)-7);
                              if (!var39.field6122) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 throw new RuntimeException();
                              }

                              if (classYY.field7109 == var39.field6126) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 DynamicObject.field1676 -= 240082458;
                                 int var57 = classBB.field323[DynamicObject.field1676 * -324749371];
                                 classMA.method7541(var39, var57, 1531145389);
                                 var39.method10791(1519400143)[var57] = classBB.field323[1 + -324749371 * DynamicObject.field1676];
                              } else if (var39.field6126 == classYY.field7113) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 int var58 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                                 classMA.method7541(var39, var58, 497286422);
                                 var39.method10793(-266582948)[var58] = classBB.field322[(classXT.field6937 -= -1297854163) * 18294437];
                              } else {
                                 int var59 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                                 classMA.method7541(var39, var59, 1282620140);
                                 var39.method10796(-1256600193)[var59] = classBB.field328[(classBB.field338 -= 1222866659) * 1375698635];
                              }

                              var39.field6131 = true;
                           } else if (var13 == 49) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              Object var40 = classDS.method3579(classSX.field6094, var12[var10], (byte)59);
                              classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var40;
                           } else if (50 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classSX.field6094.method3566(var12[var10], classBB.field328[(classBB.field338 -= 1222866659) * 1375698635], -369751506);
                           } else if (var13 == 60) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              IterableHashTable var41 = var5.field505[var12[var10]];
                              IntegerNode var60 = (IntegerNode)var41.method13637(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
                              if (null != var60) {
                                 var10 = client.field1098 = var10 + var60.field6501;
                              }
                           } else if (var13 == 63) {
                              if (var8 <= -581658423) {
                                 var29 = false;
                                 return;
                              }

                              classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = null;
                           } else if (61 == var13) {
                              classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = var5.method1142(var10, (byte)68);
                           } else if (62 == var13) {
                              classXT.field6937 -= -1297854163;
                           } else if (66 == var13) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = classDU.field1632[var12[var10]];
                           } else if (var13 == 67) {
                              classDU.field1632[var12[var10]] = classBB.field322[(classXT.field6937 -= -1297854163) * 18294437];
                           } else if (var13 == 74) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              Integer var42 = classNJ.field4676.method6289(var12[var10], (byte)75);
                              if (var42 == null) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                              } else {
                                 classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var42;
                              }
                           } else {
                              if (var13 != 76) {
                                 throw new IllegalStateException();
                              }

                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              Integer var43 = classUA.method11295(classOH.field4869, var12[var10], 1651222784);
                              if (var43 == null) {
                                 if (var8 <= -581658423) {
                                    throw new IllegalStateException();
                                 }

                                 classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                              } else {
                                 classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var43;
                              }
                           }
                        } else {
                           boolean var90;
                           if (1 == var5.field502[var10]) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              var90 = true;
                           } else {
                              var90 = false;
                           }

                           boolean var37 = var90;
                           int var55 = classQR.method9824(var13, var5, var37, (byte)25);
                           switch (var55) {
                              case 0:
                                 var29 = false;
                                 break label1432;
                              case 1:
                              default:
                                 break;
                              case 2:
                                 throw new IllegalStateException();
                           }
                        }
                     }

                     throw new RuntimeException();
                  }
               } catch (Exception var30) {
                  var14 = true;
                  StringBuilder var16 = new StringBuilder(30);
                  var16.append("").append(var5.field6550).append(" ");

                  for (int var17 = classBB.field331 * -1626238019 - 1; var17 >= 0; var17--) {
                     var16.append("").append(classBB.field321[var17].field482.field6550).append(" ");
                  }

                  var16.append("").append(var13);
                  classAAU.method270(var16.toString(), var30, (byte)125);
                  var29 = false;
                  break label1476;
               } finally {
                  if (var29) {
                     while (!classBB.field332.isEmpty()) {
                        if (var8 <= -581658423) {
                           throw new IllegalStateException();
                        }

                        classDG var25 = (classDG)classBB.field332.remove(0);
                        classEA.method3815(
                           var25.method3068((short)167),
                           classDG.method3075(var25, -65455964),
                           var25.method3076(-554920910),
                           classDG.method3079(var25, (byte)19),
                           "",
                           -329818355
                        );
                     }

                     if (classBB.field330) {
                        if (var8 <= -581658423) {
                           throw new IllegalStateException();
                        }

                        classBB.field330 = false;
                        classTB.method10780((byte)-49);
                     }

                     if (!var14) {
                        if (var8 <= -581658423) {
                           throw new IllegalStateException();
                        }

                        if (var7 > 0) {
                           if (var8 <= -581658423) {
                              throw new IllegalStateException();
                           }

                           if (1023779009 * classBB.field327 >= var7) {
                              if (var8 <= -581658423) {
                                 throw new IllegalStateException();
                              }

                              classAAU.method270(
                                 "Warning: Script " + var5.field500 + " finished at op count " + 1023779009 * classBB.field327 + " of max " + var6,
                                 null,
                                 (byte)75
                              );
                           }
                        }
                     }
                  }
               }

               while (!classBB.field332.isEmpty()) {
                  classDG var20 = (classDG)classBB.field332.remove(0);
                  classEA.method3815(
                     var20.method3068((short)167),
                     classDG.method3075(var20, -65455964),
                     var20.method3076(-554920910),
                     classDG.method3079(var20, (byte)64),
                     "",
                     -608094082
                  );
               }

               if (classBB.field330) {
                  if (var8 <= -581658423) {
                     return;
                  }

                  classBB.field330 = false;
                  classTB.method10780((byte)88);
               }

               if (!var14 && var8 > -581658423 && var7 > 0) {
                  if (var8 <= -581658423) {
                     throw new IllegalStateException();
                  }

                  if (1023779009 * classBB.field327 >= var7) {
                     if (var8 <= -581658423) {
                        throw new IllegalStateException();
                     }

                     classAAU.method270(
                        "Warning: Script " + var5.field500 + " finished at op count " + 1023779009 * classBB.field327 + " of max " + var6, null, (byte)14
                     );
                     return;
                  }
               }

               return;
            }

            while (!classBB.field332.isEmpty()) {
               classDG var50 = (classDG)classBB.field332.remove(0);
               classEA.method3815(
                  var50.method3068((short)167),
                  classDG.method3075(var50, -65455964),
                  var50.method3076(-554920910),
                  classDG.method3079(var50, (byte)14),
                  "",
                  -883657123
               );
            }

            if (classBB.field330) {
               if (var8 <= -581658423) {
                  throw new IllegalStateException();
               }

               classBB.field330 = false;
               classTB.method10780((byte)-17);
            }

            if (!var14) {
               if (var8 <= -581658423) {
                  throw new IllegalStateException();
               }

               if (var7 > 0) {
                  if (var8 <= -581658423) {
                     throw new IllegalStateException();
                  }

                  if (1023779009 * classBB.field327 >= var7) {
                     if (var8 <= -581658423) {
                        throw new IllegalStateException();
                     }

                     classAAU.method270(
                        "Warning: Script " + var5.field500 + " finished at op count " + 1023779009 * classBB.field327 + " of max " + var6, null, (byte)6
                     );
                  }

                  return;
               }
            }

            return;
         }

         while (!classBB.field332.isEmpty()) {
            classDG var15 = (classDG)classBB.field332.remove(0);
            classEA.method3815(
               var15.method3068((short)167),
               classDG.method3075(var15, -65455964),
               var15.method3076(-554920910),
               classDG.method3079(var15, (byte)45),
               "",
               -827061764
            );
         }

         if (classBB.field330) {
            if (var8 <= -581658423) {
               return;
            }

            classBB.field330 = false;
            classTB.method10780((byte)43);
         }

         if (!var14) {
            if (var8 <= -581658423) {
               throw new IllegalStateException();
            }

            if (var7 > 0 && 1023779009 * classBB.field327 >= var7) {
               if (var8 <= -581658423) {
                  throw new IllegalStateException();
               }

               classAAU.method270(
                  "Warning: Script " + var5.field500 + " finished at op count " + 1023779009 * classBB.field327 + " of max " + var6, null, (byte)122
               );
            }
         }
      } catch (RuntimeException var32) {
         throw classEG.method3899(var32, "ld.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("fp")
   static int method6798(int var0, int var1, byte var2) {
      try {
         return var0 << 16 | var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ld.fp(" + 41);
      }
   }
}
