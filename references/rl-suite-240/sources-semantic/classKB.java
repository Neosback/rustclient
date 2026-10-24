import java.net.URL;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kb")
public class classKB implements classXZ {
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("aa")
   public static final classKB field3521 = new classKB(7, 1);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ao")
   public static final classKB field3522 = new classKB(8, 1);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ae")
   public static final classKB field3515 = new classKB(2, 0);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ab")
   public static final classKB field3516 = new classKB(3, 0);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ax")
   public static final classKB field3523 = new classKB(5, 1);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("as")
   public static final classKB field3518 = new classKB(4, 1);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("az")
   public static final classKB field3513 = new classKB(0, 0);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ac")
   public static final classKB field3520 = new classKB(6, 1);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("at")
   public static final classKB field3535 = new classKB(22, 3);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ap")
   static final classKB field3528 = new classKB(17, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ag")
   public static final classKB field3517 = new classKB(9, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("aj")
   public static final classKB field3533 = new classKB(13, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ay")
   static final classKB field3531 = new classKB(14, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("aq")
   static final classKB field3526 = new classKB(15, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ad")
   static final classKB field3534 = new classKB(16, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("av")
   public static final classKB field3524 = new classKB(11, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("au")
   static final classKB field3529 = new classKB(18, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ai")
   static final classKB field3514 = new classKB(19, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("al")
   static final classKB field3525 = new classKB(12, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("aw")
   static final classKB field3532 = new classKB(21, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ak")
   public static final classKB field3519 = new classKB(10, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("ar")
   static final classKB field3530 = new classKB(20, 2);
   @ObfuscatedSignature(descriptor = "Lkb;")
   @ObfuscatedName("af")
   public static final classKB field3527 = new classKB(1, 0);
   @ObfuscatedName("an")
   public final int field3536;
   @ToRemove(unused = "true")
   @ObfuscatedName("cz")
   static final int field3537 = 249;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   public static boolean method6488(int var0) {
      return var0 >= 533829898 * field3529.field3536 && var0 <= 78062377 * field3532.field3536;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnz;Lvj;)V")
   @ObfuscatedName("wr")
   public static void method6483(classNZ var0, classVJ var1) {
      if (var0 == null) {
         var0.method8313();
      }

      if (var1.field6514 != null) {
         var1.method12035();
      }

      var1.field6514 = var0.field4798.field6514;
      var1.field6513 = var0.field4798;
      var1.field6514.field6513 = var1;
      var1.field6513.field6514 = var1;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field3536 * 78062377;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kb.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field3536 * 1507280082;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field3536 * 78062377;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field3536 * 78062377;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ac")
   public static boolean method6485(int var0) {
      return var0 >= field3513.field3536 * 78062377 && var0 <= field3516.field3536 * 78062377 || var0 == field3517.field3536 * 78062377;
   }

   classKB(int var1, int var2) {
      this.field3536 = var1 * 1546397977;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)Laag;")
   @ObfuscatedName("cg")
   public static classAAG method6490(classLH var0) {
      return var0.field4157.method13915(var0.field4150 * 1072642345, var0.field4155 * -698145269, 49444063);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ao")
   public static boolean method6487(int var0) {
      return var0 >= field3525.field3536 * 78062377 && var0 <= field3528.field3536 * 78062377;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("al")
   public static boolean method6489(int var0) {
      return var0 >= -1621354253 * field3529.field3536 && var0 <= 78062377 * field3532.field3536;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;IIIIII)I")
   @ObfuscatedName("fi")
   static final int method6492(String var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         return Friend.method10203(var0, var1, var2, var3, var4, var5, var6, false, 0, -324749371);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "kb.fi(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Ldh;B)V")
   @ObfuscatedName("bd")
   static final void method6491(WorldView var0, Actor var1, byte var2) {
      try {
         int var3 = 1612595797 * client.field856;
         boolean var4 = false;
         classAT.field277
            .method280(
               var0, -1547553299 * var1.field1484, -1272026483 * var1.field1486, -1315528093 * var1.field1478 == -2130951373 * client.field846, -474153040
            );
         if (var1.field1463.method9772(374233424)) {
            if (var2 != 5) {
               return;
            }

            if (-211489501 * var1.field1465 <= 1) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               Animation var5 = var1.field1463.method9775((byte)62);
               if (-702237139 * var5.field5129 == 1) {
                  if (var2 != 5) {
                     return;
                  }

                  if (var1.field1476 * 642137221 > 0) {
                     if (var2 != 5) {
                        return;
                     }

                     if (var1.field1494 * -665535259 <= client.field856 * 1612595797 && -1055069695 * var1.field1495 < client.field856 * 1612595797) {
                        if (var2 != 5) {
                           throw new IllegalStateException();
                        }

                        var1.field1465 = -285300085;
                        var4 = true;
                     }
                  }
               }
            }
         }

         boolean var12 = false;
         boolean var6 = false;
         if (null != var1.field1497) {
            for (int var7 = 0; var7 < var1.field1497.size(); var7++) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               classQH var8 = (classQH)var1.field1497.get(var7);
               if (var8.method9580(var3, 40402530)) {
                  if (var2 != 5) {
                     throw new IllegalStateException();
                  }

                  if (!var12) {
                     var12 = true;
                     if (!var1.field1466) {
                        if (var2 != 5) {
                           throw new IllegalStateException();
                        }

                        label276: {
                           if (null != var1.field1463) {
                              if (var2 != 5) {
                                 throw new IllegalStateException();
                              }

                              if (var1.field1463.method9772(374233424)) {
                                 if (var2 != 5) {
                                    throw new IllegalStateException();
                                 }

                                 if (-211489501 * var1.field1465 == 0) {
                                    if (var2 != 5) {
                                       throw new IllegalStateException();
                                    }

                                    var1.field1498 = new classQR();
                                    var1.field1498.method9761(var1.field1463, (byte)38);
                                    break label276;
                                 }
                              }
                           }

                           var1.field1498 = null;
                        }

                        var1.field1499 = new classQR();
                        var1.field1499.method9761(var1.field1462, (byte)43);
                     }
                  }

                  if (var8.method9573(2132914664)) {
                     if (var2 != 5) {
                        throw new IllegalStateException();
                     }

                     Actor.method3111(var1, 375241654);
                  }
               }

               var6 |= classQH.method9582(var8, var3, (short)13612);
               var8.method9591(var3, 827834060);
            }
         }

         if (!var6) {
            if (var2 != 5) {
               return;
            }

            if (var1.field1466) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               var1.field1498 = null;
               var1.field1499 = null;
            }
         }

         var1.field1466 = var6;
         if (!var6) {
            if (var2 != 5) {
               throw new IllegalStateException();
            }

            int var13 = classRD.method9980(var1.field1462, 1, classAT.field277, -1896733249);
            if ((var13 & 2) != 0) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               classQR.method9791(var1.field1462, -1753075363);
            }

            classQR.method9770(var1.field1464, var1.field1434 * 1457308725, -2058514964);
            if (var1.field1462.method9799((byte)-13) > 0) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               if (var1.field1464.method9799((byte)24) < 30) {
                  if (var2 != 5) {
                     throw new IllegalStateException();
                  }

                  if (var1.field1462.method9776(500216846) == var1.field1464.method9776(500216846)) {
                     if (var2 != 5) {
                        throw new IllegalStateException();
                     }

                     var1.field1464.method9761(var1.field1462, (byte)-17);
                  } else {
                     int var15 = classRD.method9980(var1.field1464, 1, null, -1046310645);
                     if (0 != (var15 & 2)) {
                        if (var2 != 5) {
                           return;
                        }

                        classQR.method9791(var1.field1464, -541978543);
                     }
                  }
               }
            }
         }

         classYA var14 = new classYA(var1.method3149((byte)16));

         for (ActorSpotAnim var16 = (ActorSpotAnim)var14.method13336(); null != var16; var16 = (ActorSpotAnim)var14.next()) {
            if (var2 != 5) {
               return;
            }

            if (var16.field5957 * -1568506297 != -1) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               if (client.field856 * 1612595797 < 1168432457 * var16.field5959) {
                  if (var2 != 5) {
                     return;
                  }
               } else {
                  classQR var9 = var16.field5956;
                  if (!var9.method9772(374233424)) {
                     var16.vmethod398();
                     var1.field1445 -= 509600791;
                  } else {
                     int var10 = classRD.method9980(var9, 1, classAT.field277, -2046743107);
                     if (0 != (var10 & 1)) {
                        if (var2 != 5) {
                           throw new IllegalStateException();
                        }

                        if (!var16.method10501(-1546679104)) {
                           if (var2 != 5) {
                              throw new IllegalStateException();
                           }

                           var16.vmethod398();
                           var1.field1445 -= 509600791;
                        }
                     }
                  }
               }
            }
         }

         if (!var4) {
            if (var2 != 5) {
               throw new IllegalStateException();
            }

            boolean var17 = false;
            if (-211489501 * var1.field1465 > 0) {
               var1.field1465 -= -285300085;
               var17 = true;
            }

            if (!var6) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               if (!var17) {
                  if (var2 != 5) {
                     throw new IllegalStateException();
                  }

                  int var18 = classRD.method9980(var1.field1463, 1, classAT.field277, -1329619399);
                  if (0 != (var18 & 2)) {
                     if (var2 != 5) {
                        throw new IllegalStateException();
                     }

                     classQR.method9765(var1.field1463, -2082061244);
                     if (var1.field1462.method9776(500216846) == var1.field1434 * 1457308725) {
                        if (var2 != 5) {
                           return;
                        }

                        if (var1.vmethod377(1580792473)) {
                           if (var2 != 5) {
                              throw new IllegalStateException();
                           }

                           classQR.method9791(var1.field1462, -1572818270);
                        }
                     }
                  }
               }

               boolean var10001;
               label247: {
                  if (var1.field1463.method9772(374233424)) {
                     if (var2 != 5) {
                        throw new IllegalStateException();
                     }

                     if (var1.field1463.method9775((byte)32).field5135) {
                        if (var2 != 5) {
                           throw new IllegalStateException();
                        }

                        var10001 = true;
                        break label247;
                     }
                  }

                  var10001 = false;
               }

               var1.field1438 = var10001;
               classAT.field277.method284(-1380558475);
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "kb.bd(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aa")
   public static boolean method6486(int var0) {
      return var0 >= field3513.field3536 * 78062377 && var0 <= field3516.field3536 * 78062377 || var0 == field3517.field3536 * 78062377;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   static boolean method6484(int var0) {
      try {
         try {
            if (classIS.field3052 == null) {
               if (var0 <= -878854915) {
                  throw new IllegalStateException();
               }

               classIS.field3052 = classAV.field294.method2871(new URL(classEV.field1945), 2030769018);
            } else if (classIS.field3052.method4136(1866211443)) {
               if (var0 <= -878854915) {
                  throw new IllegalStateException();
               }

               byte[] var1 = classIS.field3052.method4141(1145062404);
               classXY var2 = new classXY(var1);
               classXY.method13113(var2, (byte)1);
               World.field1151 = var2.method13099(1317994199) * -756054529;
               World.field1168 = new World[-622036481 * World.field1151];

               for (int var3 = 0; var3 < World.field1151 * -622036481; var3++) {
                  World var4 = World.field1168[var3] = new World();
                  var4.field1159 = var2.method13099(-2080660541) * 1473529319;
                  var4.field1158 = classXY.method13113(var2, (byte)1) * 1370694663;
                  var4.field1167 = var2.method13127(-1288052809);
                  var4.field1165 = var2.method13127(-627771964);
                  var4.field1160 = var2.method13095(-346779531) * 774403251;
                  var4.field1157 = var2.method13104((byte)16) * -6609823;
                  var4.method2517(-1);
                  var4.field1161 = 1173469373 * var3;
               }

               classEH.method3907(World.field1168, 0, World.field1168.length - 1, World.field1152, World.field1153, -585601014);
               classIS.field3052 = null;
               return true;
            }
         } catch (Exception var5) {
            var5.printStackTrace();
            classIS.field3052 = null;
         }

         return false;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "kb.az(" + ')');
      }
   }
}
