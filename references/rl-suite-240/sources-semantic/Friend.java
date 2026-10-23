import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rp")
public class Friend extends ChatPlayer implements net.runelite.api.Friend {
   @ObfuscatedName("af")
   boolean field5826;
   @ObfuscatedName("az")
   boolean field5827;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field5824 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ej")
   static final int field5825 = 2;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod565(Object var1) {
      return this.method10200((Friend)var1, 2138014830);
   }

   @ObfuscatedSignature(descriptor = "(Lrp;)I")
   @ObfuscatedName("af")
   int method10198(Friend var1) {
      if (client.field828 * 1311484143 == this.field5667 * 917928166 && client.field828 * 1177786542 != var1.field5667 * 1100665570) {
         return -1;
      } else if (var1.field5667 * 857205373 == 1311484143 * client.field828 && this.field5667 * -2018574236 != 1311484143 * client.field828) {
         return 1;
      } else if (-692101965 * this.field5667 != 0 && 0 == var1.field5667 * -1204829023) {
         return -1;
      } else if (var1.field5667 * -1071742471 != 0 && 0 == -1254310054 * this.field5667) {
         return 1;
      } else if (this.field5827 && !var1.field5827) {
         return -1;
      } else if (!this.field5827 && var1.field5827) {
         return 1;
      } else if (this.field5826 && !var1.field5826) {
         return -1;
      } else if (!this.field5826 && var1.field5826) {
         return 1;
      } else {
         return this.field5667 * 1137389686 != 0
            ? this.field5668 * 449993508 - 1145202131 * var1.field5668
            : 1145202131 * var1.field5668 - this.field5668 * 1145202131;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)Lou;")
   @ObfuscatedName("ez")
   public static Animation method10201(classQR var0) {
      return var0.field5597;
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)I")
   @ObfuscatedName("aw")
   @Override
   public int vmethod562(Nameable var1, int var2) {
      try {
         return this.method10200((Friend)var1, 703801405);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rp.aw(" + 41);
      }
   }

   @Override
   public int compareTo(Object var1) {
      try {
         return this.method10200((Friend)var1, 346933967);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rp.compareTo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("ak")
   @Override
   public int vmethod563(Nameable var1) {
      return this.method10200((Friend)var1, -430446394);
   }

   Friend() {
   }

   @ObfuscatedSignature(descriptor = "(Lrp;)I")
   @ObfuscatedName("ab")
   int method10199(Friend var1) {
      if (client.field828 * 277987644 == this.field5667 * -346215822 && client.field828 * 1311484143 != var1.field5667 * -760305529) {
         return -1;
      } else if (var1.field5667 * 226304319 == -947351648 * client.field828 && this.field5667 * -9713398 != 1311484143 * client.field828) {
         return 1;
      } else if (714694247 * this.field5667 != 0 && 0 == var1.field5667 * -760305529) {
         return -1;
      } else if (var1.field5667 * 58114707 != 0 && 0 == -760305529 * this.field5667) {
         return 1;
      } else if (this.field5827 && !var1.field5827) {
         return -1;
      } else if (!this.field5827 && var1.field5827) {
         return 1;
      } else if (this.field5826 && !var1.field5826) {
         return -1;
      } else if (!this.field5826 && var1.field5826) {
         return 1;
      } else {
         return this.field5667 * -760305529 != 0
            ? this.field5668 * 1054427734 - 1145202131 * var1.field5668
            : 1145202131 * var1.field5668 - this.field5668 * 1663309296;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("cl")
   static int method10202(int var0, Script var1, boolean var2, byte var3) {
      try {
         if (var0 == 7500) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               int var26 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               Object var32 = classKN.method6540(var26, -948641178);
               int var36 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classZC var39 = classBA.method779(var36, 2135996924);
               if (null == var39) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  } else {
                     throw new RuntimeException();
                  }
               } else {
                  int var42 = FloatProjection.method3910(var36, 685397891);
                  classAB.field125 = var39.method13878(var32, var42, (byte)-7);
                  if (null != classAB.field125) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     client.field808 = classSA.method10405(var36, -73434959) * -925333359;
                     classCH.field741 = classAB.field125.iterator();
                     if (var0 == 7500) {
                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }

                        classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classAB.field125.size();
                     }
                  } else {
                     client.field808 = 925333359;
                     classCH.field741 = null;
                     if (var0 == 7500) {
                        classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                     }
                  }

                  return 1;
               }
            }
         } else if (7501 == var0) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               if (null != classCH.field741) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  }

                  if (classCH.field741.hasNext()) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = (Integer)classCH.field741.next();
                     return 1;
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               return 1;
            }
         } else if (7502 == var0) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 360123687;
               int var25 = classBB.field323[-324749371 * DynamicObject.field1676];
               int var31 = classBB.field323[1 + -324749371 * DynamicObject.field1676];
               int var35 = classBB.field323[2 + -324749371 * DynamicObject.field1676];
               int var38 = classSA.method10405(var31, -348025329);
               int var41 = classKT.method6600(var31, (byte)123);
               int var44 = FloatProjection.method3910(var31, 1396095709);
               classZU var45 = classBB.method780(var38, -320209137);
               int[] var46 = var45.field7272[var41];
               int var47 = 0;
               int var13 = var46.length;
               if (-1 == var25) {
                  for (int var48 = var47; var48 < var13; var48++) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     classYY var49 = FriendContainer.method10175(var46[var48], -53215312);
                     if (var49 == classYY.field7111) {
                        classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
                     } else if (classYY.field7113 == var49) {
                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }

                        classBB.field322[(classXT.field6937 += -1297854163) * 18294437 - 1] = -1L;
                     } else {
                        classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classTM.method10983(var46[var48], -973324355);
                     }
                  }

                  return 1;
               } else {
                  DBRowConfig var14 = classFA.method4556(var25, (byte)-94);
                  if (var44 >= 0) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     if (var44 >= var13) {
                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }

                        throw new RuntimeException("Tuple index out-of-bounds. Requested: " + var44 + ", Max: " + var13);
                     }

                     var47 = var44;
                     var13 = 1 + var44;
                  }

                  Object[] var15 = DBRowConfig.method14036(var14, var41, -2015790818);
                  if (null == var15) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     if (null != var45.field7270) {
                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }

                        var15 = var45.field7270[var41];
                     }
                  }

                  if (var15 == null) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     } else {
                        for (int var50 = var47; var50 < var13; var50++) {
                           if (var3 >= 16) {
                              throw new IllegalStateException();
                           }

                           int var51 = var46[var50];
                           classYY var52 = FriendContainer.method10175(var51, 1431840310);
                           if (var52 == classYY.field7111) {
                              classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
                           } else {
                              classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classTM.method10983(var51, -973324355);
                           }
                        }

                        return 1;
                     }
                  } else {
                     int var16 = var15.length / var46.length;
                     if (var35 >= 0) {
                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }

                        if (var35 < var16) {
                           for (int var17 = var47; var17 < var13; var17++) {
                              int var18 = var46.length * var35 + var17;
                              classYY var19 = FriendContainer.method10175(var46[var17], 1728636977);
                              if (classYY.field7111 == var19) {
                                 if (var3 >= 16) {
                                    throw new IllegalStateException();
                                 }

                                 classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var15[var18];
                              } else {
                                 classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = (Integer)var15[var18];
                              }
                           }

                           return 1;
                        }

                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }
                     }

                     throw new RuntimeException();
                  }
               }
            }
         } else if (var0 == 7503) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var24 = classBB.field323[-324749371 * DynamicObject.field1676];
               int var30 = classBB.field323[1 + -324749371 * DynamicObject.field1676];
               int var34 = 0;
               int var37 = classSA.method10405(var30, -596418410);
               int var40 = classKT.method6600(var30, (byte)82);
               DBRowConfig var43 = classFA.method4556(var24, (byte)48);
               classZU var10 = classBB.method780(var37, 1415733811);
               int[] var11 = var10.field7272[var40];
               Object[] var12 = DBRowConfig.method14036(var43, var40, -1748206208);
               if (var12 == null) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  }

                  if (var10.field7270 != null) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     var12 = var10.field7270[var40];
                  }
               }

               if (var12 != null) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  }

                  var34 = var12.length / var11.length;
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var34;
               return 1;
            }
         } else if (7504 == var0) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 120041229;
               int var23 = classBB.field323[-324749371 * DynamicObject.field1676];
               classZC var29 = classHT.method6149(var23, -1765104593);
               if (null == var29) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  } else {
                     throw new RuntimeException();
                  }
               } else {
                  classAB.field125 = var29.method13878(0, 0, (byte)27);
                  int var33 = 0;
                  if (null != classAB.field125) {
                     client.field808 = var23 * -925333359;
                     classCH.field741 = classAB.field125.iterator();
                     var33 = classAB.field125.size();
                  }

                  if (7504 == var0) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var33;
                  }

                  return 1;
               }
            }
         } else if (7505 == var0) {
            int var22 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            DBRowConfig var28 = classFA.method4556(var22, (byte)-55);
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var28.field7171 * 1026569721;
            return 1;
         } else if (7506 == var0) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               int var21 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               int var27 = -1;
               if (null != classAB.field125) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  }

                  if (var21 >= 0) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     if (var21 < classAB.field125.size()) {
                        if (var3 >= 16) {
                           throw new IllegalStateException();
                        }

                        var27 = (Integer)classAB.field125.get(var21);
                     }
                  }
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var27;
               return 1;
            }
         } else if (var0 == 7507) {
            if (var3 >= 16) {
               throw new IllegalStateException();
            } else {
               int var4 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               Object var5 = classKN.method6540(var4, -1532425218);
               int var6 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classZC var7 = classBA.method779(var6, 2050988826);
               if (null == var7) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  } else {
                     throw new RuntimeException();
                  }
               } else if (classSA.method10405(var6, -1399240820) != client.field808 * 936130673) {
                  if (var3 >= 16) {
                     throw new IllegalStateException();
                  } else {
                     throw new RuntimeException();
                  }
               } else if (classAB.field125 == null && classAB.field125.isEmpty()) {
                  throw new RuntimeException();
               } else {
                  int var8 = FloatProjection.method3910(var6, 891239732);
                  List var9 = var7.method13878(var5, var8, (byte)-43);
                  classAB.field125 = new LinkedList(classAB.field125);
                  if (var9 != null) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     classAB.field125.retainAll(var9);
                  } else {
                     classAB.field125.clear();
                  }

                  classCH.field741 = classAB.field125.iterator();
                  if (7507 == var0) {
                     if (var3 >= 16) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classAB.field125.size();
                  }

                  return 1;
               }
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "rp.cl(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("av")
   @Override
   public int vmethod564(Nameable var1) {
      return this.method10200((Friend)var1, 1324484102);
   }

   @ObfuscatedSignature(descriptor = "(Lrp;I)I")
   @ObfuscatedName("az")
   int method10200(Friend var1, int var2) {
      try {
         if (client.field828 * 1311484143 == this.field5667 * -760305529) {
            if (var2 == 827217865) {
               throw new IllegalStateException();
            }

            if (client.field828 * 1311484143 != var1.field5667 * -760305529) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               return -1;
            }
         }

         if (var1.field5667 * -760305529 == 1311484143 * client.field828) {
            if (var2 == 827217865) {
               throw new IllegalStateException();
            }

            if (this.field5667 * -760305529 != 1311484143 * client.field828) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               return 1;
            }
         }

         if (-760305529 * this.field5667 != 0 && 0 == var1.field5667 * -760305529) {
            if (var2 == 827217865) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            if (var1.field5667 * -760305529 != 0) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               if (0 == -760305529 * this.field5667) {
                  if (var2 == 827217865) {
                     throw new IllegalStateException();
                  }

                  return 1;
               }
            }

            if (this.field5827) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               if (!var1.field5827) {
                  if (var2 == 827217865) {
                     throw new IllegalStateException();
                  }

                  return -1;
               }
            }

            if (!this.field5827) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               if (var1.field5827) {
                  if (var2 == 827217865) {
                     throw new IllegalStateException();
                  }

                  return 1;
               }
            }

            if (this.field5826) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               if (!var1.field5826) {
                  if (var2 == 827217865) {
                     throw new IllegalStateException();
                  }

                  return -1;
               }
            }

            if (!this.field5826) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               }

               if (var1.field5826) {
                  if (var2 == 827217865) {
                     throw new IllegalStateException();
                  }

                  return 1;
               }
            }

            if (this.field5667 * -760305529 != 0) {
               if (var2 == 827217865) {
                  throw new IllegalStateException();
               } else {
                  return this.field5668 * 1145202131 - 1145202131 * var1.field5668;
               }
            } else {
               return 1145202131 * var1.field5668 - this.field5668 * 1145202131;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rp.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;IIIIIZII)I")
   @ObfuscatedName("fb")
   static final int method10203(String var0, String var1, int var2, int var3, int var4, int var5, int var6, boolean var7, int var8, int var9) {
      try {
         if (client.field974) {
            if (var9 != -324749371) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else if (!classTK.method10982(var8, var2, (byte)15)) {
            if (var9 != -324749371) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return client.field975.method12747(var0, var1, var2, var3, var4, var5, var6, var7, var8, -1354756541);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "rp.fb(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("az")
   public static final void method10197(long var0) {
      try {
         if (var0 > 0L) {
            if (0L == var0 % 10L) {
               long var2 = var0 - 1L;

               try {
                  Thread.sleep(var2);
               } catch (InterruptedException var7) {
               }

               try {
                  Thread.sleep(1L);
               } catch (InterruptedException var6) {
               }
            } else {
               try {
                  Thread.sleep(var0);
               } catch (InterruptedException var5) {
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "rp.az(" + ')');
      }
   }
}
