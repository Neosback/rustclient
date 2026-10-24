import java.util.ArrayList;
import java.util.Arrays;
import net.runelite.api.events.PlayerDespawned;
import net.runelite.api.events.PlayerSpawned;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dc")
public class classDC implements classSJ {
   @ObfuscatedName("aj")
   int field1385;
   @ObfuscatedName("af")
   int field1379;
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("ae")
   classKY field1380;
   @ObfuscatedName("ab")
   byte field1388;
   @ObfuscatedSignature(descriptor = "Lfl;")
   @ObfuscatedName("ao")
   final classFL field1383;
   @ObfuscatedSignature(descriptor = "Lxy;")
   @ObfuscatedName("as")
   classXY field1393;
   @ObfuscatedSignature(descriptor = "Lct;")
   @ObfuscatedName("pc")
   public static Player field1391;
   @ObfuscatedName("ax")
   String[] field1382;
   @ObfuscatedSignature(descriptor = "Lgt;")
   @ObfuscatedName("ag")
   classGT field1392;
   @ObfuscatedName("ac")
   int field1381;
   @ObfuscatedName("al")
   int field1386;
   @ObfuscatedName("az")
   final ArrayList field1387 = new ArrayList(1);
   @ObfuscatedSignature(descriptor = "Lbz;")
   @ObfuscatedName("aa")
   classBZ field1378;
   @ObfuscatedSignature(descriptor = "Laav;")
   @ObfuscatedName("ay")
   classAAV field1384;
   @ToRemove(unused = "true")
   @ObfuscatedName("bd")
   public static final int field1390 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   public static final int field1389 = 0;

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ao")
   void method2930(int var1, int var2, int var3) {
      try {
         for (int var4 = 0; var4 < this.field1387.size(); var4++) {
            classCU.method2672((Player)this.field1387.get(var4), var1, var2, -1274605594);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dc.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("af")
   void method2891(int var1, int var2) {
      try {
         this.field1379 = var1 * 1621593763;
         this.field1378.method1378((byte)126);
         this.field1388 = 0;
         this.field1392 = classGT.field2678;
         this.field1393 = null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lct;I)V")
   @ObfuscatedName("ab")
   void method2901(Player var1, int var2) {
      this.method2984(var1);

      try {
         this.field1387.add(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   void method2907(byte var1) {
      try {
         this.field1387.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILkl;ZLjava/lang/String;[B)V")
   @ObfuscatedName("dt")
   void method2954(int var1, classKL var2, boolean var3, String var4, byte[] var5) {
      boolean var6 = false;
      String var7 = null;

      for (int var8 = 0; var8 < this.field1387.size(); var8++) {
         Player var9 = (Player)this.field1387.get(var8);
         if (var9.field1281 != null && null != var9.field1283) {
            boolean var10 = false;
            if (var2.field3936 && classQQ.field5595.method1299(var9.field1281, -1181618808)) {
               var10 = true;
            }

            if (!var10 && 1746877283 * client.field837 == 0 && !var9.field1257) {
               var6 = true;
               var7 = classAAE.method157(var9.field1281, 1039959427);
               var9.field1459 = var4.trim();
               var9.field1458 = (var1 >> 8) * 743710599;
               var9.field1479 = (var1 & 0xFF) * 77157821;
               var9.field1457 = 136918638;
               var9.field1436 = classKV.method6611(var5, (byte)0);
               var9.field1500 = var3;
               var9.field1456 = var9.field1478 * -1315528093 != -2130951373 * client.field846
                  && var2.field3936
                  && !client.field930.isEmpty()
                  && var4.toLowerCase().indexOf(client.field930) == -1;
            }
         }
      }

      if (var6 && null != var7) {
         int var11;
         if (var2.field3938) {
            var11 = var3 ? 91 : 1;
         } else {
            var11 = var3 ? 90 : 2;
         }

         if (var2.field3937 * 1654631897 != -1) {
            classPH.method8924(var11, classDG.method3072(1654631897 * var2.field3937, (byte)-48) + var7, var4, 1802083530);
         } else {
            classPH.method8924(var11, var7, var4, 1246997290);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("dx")
   void method2946(int var1, int var2, int var3, int var4, int var5) {
      for (int var6 = 0; var6 < this.field1387.size(); var6++) {
         ((Actor)this.field1387.get(var6)).method3144(var1, client.field856 * 1612595797, var2, var3, var4, var5, 1874940363);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ld")
   public void method2986() {
      field1391 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;Lgt;)V")
   @ObfuscatedName("us")
   public static void method2923(classDC var0, classGT var1) {
      if (var0 == null) {
         var0.vmethod311();
      }

      for (int var2 = 0; var2 < var0.field1387.size(); var2++) {
         Player var3 = (Player)var0.field1387.get(var2);
         WorldView var4 = var3.method3177((byte)-78);
         int var5 = 1870919123 * var0.field1380.field4053 - var4.field1706 * -1444178379;
         int var6 = 819227381 * var0.field1380.field4054 - -351145363 * var4.field1707;
         classGT var7 = var1;
         short var8 = 1536;
         short var9 = 11776;
         if (0 == var4.field1697 * 2140889407
            && client.field850 * 1778071523 == var0.field1381 * 431142127
            && classKU.field4023 == classLE.field4124
            && (
               var3.field1484 * -1547553299 < 1536
                  || var3.field1486 * -1272026483 < 1536
                  || -1547553299 * var3.field1484 >= 11776
                  || var3.field1486 * -1272026483 >= 11776
            )) {
            var7 = classGT.field2676;
         }

         var3.field1273 = 294971065 * var0.field1380.field4055;
         if (var7 == classGT.field2676) {
            var3.method3123(var5, var6, 527441438);
         } else {
            var3.method2644(var4, var5, var6, var7, 1763235575);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("dh")
   void method2958(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      for (int var8 = 0; var8 < this.field1387.size(); var8++) {
         Player var9 = (Player)this.field1387.get(var8);
         WorldView var10 = var9.method3177((byte)98);
         int var11 = this.field1380.field4053 * 1870919123 - var10.field1706 * -1866111489;
         int var12 = 819227381 * this.field1380.field4054 - -93506941 * var10.field1707;
         var9.field1488 = -896779669 * (var11 + var1);
         var9.field1490 = (var12 + var2) * 749833987;
         var9.field1489 = 1685383399 * (var3 + var11);
         var9.field1492 = (var12 + var4) * 1065360726;
         var9.field1494 = var5 * -1146707731;
         var9.field1495 = var6 * -1991014563;
         var9.field1491 = 843162844 * var7;
         var9.field1476 = 0;
         var9.method2648(-1805971573 * var9.field1489, -1067628647 * var9.field1492, -1891441366);
         var9.field1273 = this.field1380.field4055 * 294971065;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldc;B)V")
   @ObfuscatedName("zm")
   public static void method2889(classDC var0, byte var1) {
      if (var0 == null) {
         var0.method2890(var1);
      }

      var0.method2986();

      try {
         var0.field1384 = null;
         classFL.method4856(var0.field1383);
         var0.field1393 = null;
         var0.field1387.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.az(" + ')');
      }
   }

   classDC(int var1) {
      this.field1379 = 0;
      this.field1380 = new classKY();
      this.field1382 = new String[3];
      this.field1381 = 1618281969;
      this.field1378 = new classBZ();
      this.field1383 = new classFL();
      this.field1386 = 1704655051;
      this.field1385 = -1854144323;
      this.field1384 = null;
      this.field1381 = var1 * -1618281969;
      Arrays.fill(this.field1382, "");
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;I)V")
   @ObfuscatedName("li")
   public static void method2892(classDC var0, int var1) {
      if (var0 == null) {
         var0.method2986();
      }

      var0.field1379 = var1 * 795064858;
      var0.field1378.method1378((byte)71);
      var0.field1388 = 0;
      var0.field1392 = classGT.field2678;
      var0.field1393 = null;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   @Override
   public int vmethod314(int var1) {
      try {
         return this.field1380.field4055 * -210579119;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("dw")
   void method2959(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      for (int var8 = 0; var8 < this.field1387.size(); var8++) {
         Player var9 = (Player)this.field1387.get(var8);
         WorldView var10 = var9.method3177((byte)46);
         int var11 = this.field1380.field4053 * 1870919123 - var10.field1706 * -1444178379;
         int var12 = 819227381 * this.field1380.field4054 - -351145363 * var10.field1707;
         var9.field1488 = -896779669 * (var11 + var1);
         var9.field1490 = (var12 + var2) * 749833987;
         var9.field1489 = 1685383399 * (var3 + var11);
         var9.field1492 = (var12 + var4) * -441508695;
         var9.field1494 = var5 * -1146707731;
         var9.field1495 = var6 * -590932479;
         var9.field1491 = 1569538929 * var7;
         var9.field1476 = 0;
         var9.method2648(801221335 * var9.field1489, -1067628647 * var9.field1492, -1530252674);
         var9.field1273 = this.field1380.field4055 * 294971065;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgt;B)V")
   @ObfuscatedName("ad")
   void method2924(classGT var1, byte var2) {
      try {
         for (int var3 = 0; var3 < this.field1387.size(); var3++) {
            if (var2 >= 6) {
               throw new IllegalStateException();
            }

            Player var4 = (Player)this.field1387.get(var3);
            WorldView var5 = var4.method3177((byte)-87);
            int var6 = 1870919123 * this.field1380.field4053 - var5.field1706 * -1444178379;
            int var7 = 819227381 * this.field1380.field4054 - -351145363 * var5.field1707;
            classGT var8 = var1;
            short var9 = 1536;
            short var10 = 11776;
            if (0 == var5.field1697 * 2140889407) {
               if (var2 >= 6) {
                  throw new IllegalStateException();
               }

               label62:
               if (client.field850 * 1778071523 == this.field1381 * 431142127 && classKU.field4023 == classLE.field4124) {
                  if (var2 >= 6) {
                     return;
                  }

                  if (var4.field1484 * -1547553299 >= 1536) {
                     if (var2 >= 6) {
                        throw new IllegalStateException();
                     }

                     if (var4.field1486 * -1272026483 >= 1536 && -1547553299 * var4.field1484 < 11776) {
                        if (var2 >= 6) {
                           throw new IllegalStateException();
                        }

                        if (var4.field1486 * -1272026483 < 11776) {
                           break label62;
                        }
                     }
                  }

                  var8 = classGT.field2676;
               }
            }

            var4.field1273 = 294971065 * this.field1380.field4055;
            if (var8 == classGT.field2676) {
               if (var2 >= 6) {
                  throw new IllegalStateException();
               }

               var4.method3123(var6, var7, 527441438);
            } else {
               var4.method2644(var5, var6, var7, var8, 1287090741);
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "dc.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ap")
   void method2927(classXY var1, int var2) {
      try {
         this.field1393 = var1;

         for (int var3 = 0; var3 < this.field1387.size(); var3++) {
            if (var2 != 1555374895) {
               return;
            }

            ((Player)this.field1387.get(var3)).method2615(this.field1393, -1316125685);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.ap(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;II)V")
   @ObfuscatedName("zz")
   public static void method2915(classDC var0, int var1, int var2) {
      var0.field1380.method6672(-157772533 * var0.field1379, var1, var2, -715956810);
      var0.field1379 = -1621593763;
   }

   @ObfuscatedSignature(descriptor = "(Ldc;ILkl;ZLjava/lang/String;[BI)V")
   @ObfuscatedName("wu")
   public static void method2955(classDC var0, int var1, classKL var2, boolean var3, String var4, byte[] var5, int var6) {
      if (var0 == null) {
         var0.method2957(var1, var2, var3, var4, var5, var1);
      }

      try {
         boolean var7 = false;
         String var8 = null;

         for (int var9 = 0; var9 < var0.field1387.size(); var9++) {
            if (var6 >= -374161745) {
               return;
            }

            Player var10 = (Player)var0.field1387.get(var9);
            if (var10.field1281 != null) {
               if (var6 >= -374161745) {
                  throw new IllegalStateException();
               }

               if (null != var10.field1283) {
                  if (var6 >= -374161745) {
                     return;
                  }

                  boolean var11 = false;
                  if (var2.field3936 && classQQ.field5595.method1299(var10.field1281, -1181618808)) {
                     if (var6 >= -374161745) {
                        throw new IllegalStateException();
                     }

                     var11 = true;
                  }

                  if (!var11) {
                     if (var6 >= -374161745) {
                        throw new IllegalStateException();
                     }

                     if (1746877283 * client.field837 == 0 && !var10.field1257) {
                        if (var6 >= -374161745) {
                           throw new IllegalStateException();
                        }

                        boolean var10001;
                        label131: {
                           var7 = true;
                           var8 = classAAE.method157(var10.field1281, 1902618827);
                           var10.field1459 = var4.trim();
                           var10.method3279(-1);
                           var10.field1458 = (var1 >> 8) * 743710599;
                           var10.field1479 = (var1 & 0xFF) * 77157821;
                           var10.field1457 = 136918638;
                           var10.field1436 = classKV.method6611(var5, (byte)0);
                           var10.field1500 = var3;
                           if (var10.field1478 * -1315528093 != -2130951373 * client.field846) {
                              if (var6 >= -374161745) {
                                 throw new IllegalStateException();
                              }

                              if (var2.field3936) {
                                 if (var6 >= -374161745) {
                                    throw new IllegalStateException();
                                 }

                                 if (!client.field930.isEmpty()) {
                                    if (var6 >= -374161745) {
                                       throw new IllegalStateException();
                                    }

                                    if (var4.toLowerCase().indexOf(client.field930) == -1) {
                                       if (var6 >= -374161745) {
                                          throw new IllegalStateException();
                                       }

                                       var10001 = true;
                                       break label131;
                                    }
                                 }
                              }
                           }

                           var10001 = false;
                        }

                        var10.field1456 = var10001;
                     }
                  }
               }
            }
         }

         if (var7) {
            if (var6 >= -374161745) {
               return;
            }

            if (null != var8) {
               if (var6 >= -374161745) {
                  throw new IllegalStateException();
               }

               byte var13;
               if (var2.field3938) {
                  if (var6 >= -374161745) {
                     throw new IllegalStateException();
                  }

                  byte var10000;
                  if (var3) {
                     if (var6 >= -374161745) {
                        throw new IllegalStateException();
                     }

                     var10000 = 91;
                  } else {
                     var10000 = 1;
                  }

                  var13 = var10000;
               } else {
                  byte var14;
                  if (var3) {
                     if (var6 >= -374161745) {
                        return;
                     }

                     var14 = 90;
                  } else {
                     var14 = 2;
                  }

                  var13 = var14;
               }

               if (var2.field3937 * 1654631897 != -1) {
                  if (var6 >= -374161745) {
                     throw new IllegalStateException();
                  }

                  classPH.method8924(var13, classDG.method3072(1654631897 * var2.field3937, (byte)-21) + var8, var4, 1742813307);
               } else {
                  classPH.method8924(var13, var8, var4, 2094937243);
               }
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "dc.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ac")
   @Override
   public boolean vmethod302(byte var1) {
      try {
         boolean var10000;
         if (-157772533 * this.field1379 == -1) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILxs;I)V")
   @ObfuscatedName("ai")
   void method2933(int var1, PacketBuffer var2, int var3) {
      try {
         this.field1378.method1384(var1, var2, (byte)2);
         this.method2935(1041343536);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZ)V")
   @ObfuscatedName("ds")
   void method2980(int var1, int var2, boolean var3) {
      for (int var4 = 0; var4 < this.field1387.size(); var4++) {
         Player var5 = classEN.method3954(((Actor)this.field1387.get(var4)).field1478 * -1315528093, client.field815, 142211213);
         if (var5 != null) {
            if (-1 != var1 && var2 != -1) {
               var5.method3099(var1, var2, var3, (byte)1);
            } else {
               var5.method3105(2140242708);
               if (var3) {
                  Actor.method3111(var5, 112932728);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldc;IIZI)V")
   @ObfuscatedName("oj")
   public static void method2981(classDC var0, int var1, int var2, boolean var3, int var4) {
      if (var0 == null) {
         var0.method2983(var1, var1, var3, var1);
      }

      try {
         for (int var5 = 0; var5 < var0.field1387.size(); var5++) {
            if (var4 == 811047921) {
               return;
            }

            Player var6 = classEN.method3954(((Actor)var0.field1387.get(var5)).field1478 * -1315528093, client.field815, 408323482);
            if (var6 != null) {
               if (var4 == 811047921) {
                  throw new IllegalStateException();
               }

               if (-1 != var1) {
                  if (var4 == 811047921) {
                     return;
                  }

                  if (var2 != -1) {
                     var6.method3099(var1, var2, var3, (byte)1);
                     continue;
                  }

                  if (var4 == 811047921) {
                     throw new IllegalStateException();
                  }
               }

               var6.method3105(2128205205);
               if (var3) {
                  Actor.method3111(var6, -292214652);
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "dc.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIB)V")
   @ObfuscatedName("ak")
   void method2942(int var1, int var2, int var3, int var4, byte var5) {
      try {
         for (int var6 = 0; var6 < this.field1387.size(); var6++) {
            ((Actor)this.field1387.get(var6)).method3138(var1, var2, client.field856 * 1612595797, var3, var4, 1694102512);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "dc.ak(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;IIIIIII)V")
   @ObfuscatedName("xt")
   public static void method2960(classDC var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var0 == null) {
         var0.method2958(var1, var1, var1, var1, var1, var1, var1);
      }

      for (int var8 = 0; var8 < var0.field1387.size(); var8++) {
         Player var9 = (Player)var0.field1387.get(var8);
         WorldView var10 = var9.method3177((byte)41);
         int var11 = var0.field1380.field4053 * 1870919123 - var10.field1706 * -1444178379;
         int var12 = 819227381 * var0.field1380.field4054 - -351145363 * var10.field1707;
         var9.field1488 = -896779669 * (var11 + var1);
         var9.field1490 = (var12 + var2) * 749833987;
         var9.field1489 = 1685383399 * (var3 + var11);
         var9.field1492 = (var12 + var4) * -441508695;
         var9.field1494 = var5 * -1146707731;
         var9.field1495 = var6 * -590932479;
         var9.field1491 = 1569538929 * var7;
         var9.field1476 = 0;
         var9.method2648(801221335 * var9.field1489, -1067628647 * var9.field1492, 624185393);
         var9.field1273 = var0.field1380.field4055 * 294971065;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIII)V")
   @ObfuscatedName("av")
   void method2947(int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         for (int var7 = 0; var7 < this.field1387.size(); var7++) {
            ((Actor)this.field1387.get(var7)).method3144(var1, client.field856 * 1612595797, var2, var3, var4, var5, 1874940363);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "dc.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("co")
   void method2918(int var1) {
      this.field1380.field4055 = 1062960674 * var1;
   }

   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("zw")
   public void method2984(Player var1) {
      if (var1.getId() == client.field846 * -2130951373) {
         field1391 = var1;
      }

      classOE.field4841.getCallbacks().postDeferred(new PlayerSpawned(var1));
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("dz")
   void method2965(String[] var1) {
      this.field1382 = Arrays.copyOf(var1, var1.length);

      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         ((Player)this.field1387.get(var2)).field1268 = Arrays.copyOf(var1, var1.length);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIBBBBI)V")
   @ObfuscatedName("bn")
   void method2971(int var1, int var2, byte var3, byte var4, byte var5, byte var6, int var7) {
      try {
         this.field1386 = var1 * -1704655051;
         this.field1385 = var2 * 1854144323;
         classFL.method4850(this.field1383, var3, var4, var5, var6);

         for (int var8 = 0; var8 < this.field1387.size(); var8++) {
            Player var9 = (Player)this.field1387.get(var8);
            var9.method3196(this.field1386 * 190337821, this.field1385 * -800586389, this.field1383, 833690333);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "dc.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lct;B)V")
   @ObfuscatedName("ag")
   void method2903(Player var1, byte var2) {
      this.method2987(var1);

      try {
         this.field1387.remove(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   @Override
   public int vmethod309(int var1) {
      try {
         return this.field1380.field4054 * 819227381;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("br")
   void method2893(int var1) {
      this.field1379 = var1 * 1621593763;
      this.field1378.method1378((byte)73);
      this.field1388 = 0;
      this.field1392 = classGT.field2678;
      this.field1393 = null;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("au")
   void method2916(int var1, int var2, int var3) {
      try {
         this.field1380.method6672(-157772533 * this.field1379, var1, var2, 571746028);
         this.field1379 = -1621593763;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("at")
   void method2950(int var1, int var2) {
      try {
         for (int var3 = 0; var3 < this.field1387.size(); var3++) {
            if (var2 <= 1597272529) {
               throw new IllegalStateException();
            }

            ((Actor)this.field1387.get(var3)).method3145(var1, 1511865987);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cn")
   void method2931(int var1, int var2) {
      for (int var3 = 0; var3 < this.field1387.size(); var3++) {
         classCU.method2672((Player)this.field1387.get(var3), var1, var2, -632450447);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bm")
   void method2894(int var1) {
      this.field1379 = var1 * -417712940;
      this.field1378.method1378((byte)85);
      this.field1388 = 0;
      this.field1392 = classGT.field2678;
      this.field1393 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   void method2897() {
      this.field1384 = null;
      this.field1386 = 1704655051;
      this.field1385 = -1854144323;
      classFL.method4856(this.field1383);
      this.field1393 = null;
      this.field1392 = classGT.field2678;
      this.field1378.method1378((byte)94);
      Arrays.fill(this.field1382, "");

      for (int var1 = 0; var1 < this.field1387.size(); var1++) {
         Player var2 = (Player)this.field1387.get(var1);
         var2.field1475 = null;
         var2.method3196(-1, -1, this.field1383, 924566172);
         var2.field1474[0] = this.field1392;
         var2.method3224(this.field1378, -1281555746);
         var2.field1268 = Arrays.copyOf(this.field1382, 3);
         classCU.method2672(var2, -1, 0, 557357417);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   void method2898() {
      this.field1384 = null;
      this.field1386 = 1704655051;
      this.field1385 = -1854144323;
      classFL.method4856(this.field1383);
      this.field1393 = null;
      this.field1392 = classGT.field2678;
      this.field1378.method1378((byte)15);
      Arrays.fill(this.field1382, "");

      for (int var1 = 0; var1 < this.field1387.size(); var1++) {
         Player var2 = (Player)this.field1387.get(var1);
         var2.field1475 = null;
         var2.method3196(-1, -1, this.field1383, 272102676);
         var2.field1474[0] = this.field1392;
         var2.method3224(this.field1378, -920730258);
         var2.field1268 = Arrays.copyOf(this.field1382, 3);
         classCU.method2672(var2, -1, 0, 269507892);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cd")
   @Override
   public int vmethod315() {
      return this.field1380.field4055 * -210579119;
   }

   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("bw")
   void method2902(Player var1) {
      this.field1387.add(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("bb")
   void method2904(Player var1) {
      this.field1387.remove(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("bi")
   void method2905(Player var1) {
      this.field1387.remove(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bt")
   void method2895(int var1) {
      this.field1379 = var1 * 1939007803;
      this.field1378.method1378((byte)90);
      this.field1388 = 0;
      this.field1392 = classGT.field2678;
      this.field1393 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   void method2908() {
      this.field1387.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ldc;I)V")
   @ObfuscatedName("ta")
   public static void method2899(classDC var0, int var1) {
      if (var0 == null) {
         var0.method2922(var1);
      } else {
         try {
            var0.field1384 = null;
            var0.field1386 = 1704655051;
            var0.field1385 = -1854144323;
            classFL.method4856(var0.field1383);
            var0.field1393 = null;
            var0.field1392 = classGT.field2678;
            var0.field1378.method1378((byte)12);
            Arrays.fill(var0.field1382, "");

            for (int var2 = 0; var2 < var0.field1387.size(); var2++) {
               Player var3 = (Player)var0.field1387.get(var2);
               var3.field1475 = null;
               var3.method3196(-1, -1, var0.field1383, 1267335609);
               var3.field1474[0] = var0.field1392;
               var3.method3224(var0.field1378, -1590653909);
               var3.field1268 = Arrays.copyOf(var0.field1382, 3);
               classCU.method2672(var3, -1, 0, -835845865);
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "dc.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   void method2909() {
      this.field1387.clear();
   }

   @ObfuscatedSignature(descriptor = "(Ldc;II)V")
   @ObfuscatedName("zo")
   public static void method2919(classDC var0, int var1, int var2) {
      if (var0 == null) {
         var0.method2953(var1, var1);
      }

      try {
         var0.field1380.field4055 = -1004595279 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dc.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   void method2910() {
      this.field1387.clear();
   }

   @ObfuscatedSignature(descriptor = "(ILdz;)Lct;")
   @ObfuscatedName("bs")
   Player method2911(int var1, WorldView var2) {
      Player var3 = new Player(var1);
      if (null != this.field1393) {
         var3.method2615(this.field1393, -1316125685);
      }

      var3.field1493 = 0;
      var3.method3224(this.field1378, -1267181609);
      var3.method3196(this.field1386 * 190337821, this.field1385 * -800586389, this.field1383, 1867802641);
      if (null != this.field1384) {
         var3.field1475 = new classAAV(this.field1384);
      }

      var3.field1474[0] = this.field1392;
      var3.field1273 = this.field1380.field4055 * 294971065;
      var3.field1437 = -1063547535 * var2.field1697;
      var3.field1268 = Arrays.copyOf(var3.field1268, var3.field1268.length);
      var3.method3123(
         1870919123 * this.field1380.field4053 - var2.field1706 * -1444178379, 819227381 * this.field1380.field4054 - var2.field1707 * -351145363, 527441438
      );
      return var3;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bd")
   @Override
   public boolean vmethod303() {
      return -157772533 * this.field1379 == -1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bl")
   @Override
   public boolean vmethod304() {
      return 1153293943 * this.field1379 == -1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   void method2913() {
      this.field1379 = classKY.method6668(this.field1380, 39949557) * 1621593763;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ar")
   void method2935(int var1) {
      try {
         for (int var2 = 0; var2 < this.field1387.size(); var2++) {
            if (var1 <= 1002757669) {
               throw new IllegalStateException();
            }

            ((Actor)this.field1387.get(var2)).method3224(this.field1378, -429093985);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dc.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bz")
   void method2917(int var1, int var2) {
      this.field1380.method6672(-157772533 * this.field1379, var1, var2, 235990715);
      this.field1379 = -1621593763;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bj")
   void method2896(int var1) {
      this.field1379 = var1 * 1621593763;
      this.field1378.method1378((byte)43);
      this.field1388 = 0;
      this.field1392 = classGT.field2678;
      this.field1393 = null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cu")
   @Override
   public int vmethod305() {
      return this.field1380.field4053 * 1870919123;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;[Ljava/lang/String;)V")
   @ObfuscatedName("yv")
   public static void method2966(classDC var0, String[] var1) {
      if (var0 == null) {
         var0.vmethod311();
      }

      var0.field1382 = Arrays.copyOf(var1, var1.length);

      for (int var2 = 0; var2 < var0.field1387.size(); var2++) {
         ((Player)var0.field1387.get(var2)).field1268 = Arrays.copyOf(var1, var1.length);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldc;IIIBBZB)V")
   @ObfuscatedName("py")
   public static void method2973(classDC var0, int var1, int var2, int var3, byte var4, byte var5, boolean var6, byte var7) {
      if (var0 == null) {
         var0.method2976(var1, var1, var1, var4, var4, var6, var4);
      }

      try {
         if (null == var0.field1384) {
            if (var7 != 0) {
               return;
            }

            var0.field1384 = new classAAV();
         }

         var0.field1384.method271(var1, var2, var3, var4, var5, var6);

         for (int var8 = 0; var8 < var0.field1387.size(); var8++) {
            if (var7 != 0) {
               throw new IllegalStateException();
            }

            Player var9 = (Player)var0.field1387.get(var8);
            if (var9.field1475 == null) {
               if (var7 != 0) {
                  throw new IllegalStateException();
               }

               var9.field1475 = new classAAV();
            }

            var9.field1475.method271(var1, var2, var3, var4, var5, var6);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "dc.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cb")
   @Override
   public int vmethod306() {
      return this.field1380.field4053 * 1870919123;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ct")
   @Override
   public int vmethod310() {
      return this.field1380.field4054 * -417766795;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cp")
   @Override
   public int vmethod311() {
      return this.field1380.field4054 * 60546842;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cq")
   @Override
   public int vmethod312() {
      return this.field1380.field4054 * -921446027;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cl")
   @Override
   public int vmethod313() {
      return this.field1380.field4054 * 819227381;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cs")
   void method2920(int var1) {
      this.field1380.field4055 = -1004595279 * var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cr")
   @Override
   public int vmethod307() {
      return this.field1380.field4053 * -880483854;
   }

   @ObfuscatedSignature(descriptor = "(Lgt;)V")
   @ObfuscatedName("ck")
   void method2925(classGT var1) {
      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         Player var3 = (Player)this.field1387.get(var2);
         WorldView var4 = var3.method3177((byte)-100);
         int var5 = -1954501885 * this.field1380.field4053 - var4.field1706 * -1444178379;
         int var6 = -1164115375 * this.field1380.field4054 - -351145363 * var4.field1707;
         classGT var7 = var1;
         int var8 = 1260918818;
         short var9 = 11776;
         if (0 == var4.field1697 * 2140889407
            && client.field850 * 682945008 == this.field1381 * 431142127
            && classKU.field4023 == classLE.field4124
            && (
               var3.field1484 * -1547553299 < -1252169666
                  || var3.field1486 * -1998736940 < -1527215957
                  || 221723667 * var3.field1484 >= -910152856
                  || var3.field1486 * -1272026483 >= -2048117110
            )) {
            var7 = classGT.field2676;
         }

         var3.field1273 = 294971065 * this.field1380.field4055;
         if (var7 == classGT.field2676) {
            var3.method3123(var5, var6, 527441438);
         } else {
            var3.method2644(var4, var5, var6, var7, -28362462);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldc;IIIIIIII)V")
   @ObfuscatedName("la")
   public static void method2961(classDC var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      if (var0 == null) {
         var0.method2964(var1, var1, var1, var1, var1, var1, var1, var1);
      }

      try {
         for (int var9 = 0; var9 < var0.field1387.size(); var9++) {
            if (var8 >= 1409101674) {
               throw new IllegalStateException();
            }

            Player var10 = (Player)var0.field1387.get(var9);
            WorldView var11 = var10.method3177((byte)8);
            int var12 = var0.field1380.field4053 * 1870919123 - var11.field1706 * -1444178379;
            int var13 = 819227381 * var0.field1380.field4054 - -351145363 * var11.field1707;
            var10.field1488 = -896779669 * (var12 + var1);
            var10.field1490 = (var13 + var2) * 749833987;
            var10.field1489 = 1685383399 * (var3 + var12);
            var10.field1492 = (var13 + var4) * -441508695;
            var10.field1494 = var5 * -1146707731;
            var10.field1495 = var6 * -590932479;
            var10.field1491 = 1569538929 * var7;
            var10.field1476 = 0;
            var10.method2648(801221335 * var10.field1489, -1067628647 * var10.field1492, -1893287312);
            var10.field1273 = var0.field1380.field4055 * 294971065;
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "dc.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cy")
   void method2921(int var1) {
      this.field1380.field4055 = -1004595279 * var1;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cc")
   void method2928(classXY var1) {
      this.field1393 = var1;

      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         ((Player)this.field1387.get(var2)).method2615(this.field1393, -1316125685);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("cf")
   void method2929(classXY var1) {
      this.field1393 = var1;

      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         ((Player)this.field1387.get(var2)).method2615(this.field1393, -1316125685);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ch")
   void method2932(int var1, int var2) {
      for (int var3 = 0; var3 < this.field1387.size(); var3++) {
         classCU.method2672((Player)this.field1387.get(var3), var1, var2, 555794902);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILxs;)V")
   @ObfuscatedName("cz")
   void method2934(int var1, PacketBuffer var2) {
      this.field1378.method1384(var1, var2, (byte)2);
      this.method2935(1591066591);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cg")
   void method2936() {
      for (int var1 = 0; var1 < this.field1387.size(); var1++) {
         ((Actor)this.field1387.get(var1)).method3224(this.field1378, -1243005492);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cw")
   void method2937() {
      for (int var1 = 0; var1 < this.field1387.size(); var1++) {
         ((Actor)this.field1387.get(var1)).method3224(this.field1378, -571836897);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cj")
   void method2938(String var1) {
      boolean var2 = var1.charAt(0) == '~';
      if (var2) {
         var1 = var1.substring(1);
      }

      classAAE var3 = null;

      for (int var4 = 0; var4 < this.field1387.size(); var4++) {
         Player var5 = (Player)this.field1387.get(var4);
         var5.field1500 = false;
         var5.field1458 = 0;
         var5.field1479 = 0;
         var5.field1457 = 136918638;
         var5.field1459 = var1;
         var3 = var5.field1281;
      }

      if (var3 != null) {
         if (var2 || -2130951373 * client.field846 == 431142127 * this.field1381) {
            classPH.method8924(2, classAAE.method157(var3, 1579558494), var1, 1519206609);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("be")
   void method2906(Player var1) {
      this.field1387.remove(var1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cv")
   @Override
   public int vmethod316() {
      return this.field1380.field4055 * -210579119;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   @Override
   public int vmethod308(int var1) {
      try {
         return this.field1380.field4053 * 1870919123;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.al(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("cx")
   void method2943(int var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < this.field1387.size(); var5++) {
         ((Actor)this.field1387.get(var5)).method3138(var1, var2, client.field856 * 1612595797, var3, var4, 2138307786);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("cm")
   void method2944(int var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < this.field1387.size(); var5++) {
         ((Actor)this.field1387.get(var5)).method3138(var1, var2, client.field856 * 1612595797, var3, var4, 472951642);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("dd")
   void method2948(int var1, int var2, int var3, int var4, int var5) {
      for (int var6 = 0; var6 < this.field1387.size(); var6++) {
         ((Actor)this.field1387.get(var6)).method3144(var1, client.field856 * 1612595797, var2, var3, var4, var5, 1874940363);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("dm")
   void method2949(int var1, int var2, int var3, int var4, int var5) {
      for (int var6 = 0; var6 < this.field1387.size(); var6++) {
         ((Actor)this.field1387.get(var6)).method3144(var1, client.field856 * 1612595797, var2, var3, var4, var5, 1874940363);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dy")
   void method2951(int var1) {
      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         ((Actor)this.field1387.get(var2)).method3145(var1, -1950012684);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIBBZ)V")
   @ObfuscatedName("dg")
   void method2974(int var1, int var2, int var3, byte var4, byte var5, boolean var6) {
      if (null == this.field1384) {
         this.field1384 = new classAAV();
      }

      this.field1384.method271(var1, var2, var3, var4, var5, var6);

      for (int var7 = 0; var7 < this.field1387.size(); var7++) {
         Player var8 = (Player)this.field1387.get(var7);
         if (var8.field1475 == null) {
            var8.field1475 = new classAAV();
         }

         var8.field1475.method271(var1, var2, var3, var4, var5, var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(ILkl;ZLjava/lang/String;[B)V")
   @ObfuscatedName("du")
   void method2956(int var1, classKL var2, boolean var3, String var4, byte[] var5) {
      boolean var6 = false;
      String var7 = null;

      for (int var8 = 0; var8 < this.field1387.size(); var8++) {
         Player var9 = (Player)this.field1387.get(var8);
         if (var9.field1281 != null && null != var9.field1283) {
            boolean var10 = false;
            if (var2.field3936 && classQQ.field5595.method1299(var9.field1281, -1181618808)) {
               var10 = true;
            }

            if (!var10 && 1746877283 * client.field837 == 0 && !var9.field1257) {
               var6 = true;
               var7 = classAAE.method157(var9.field1281, 959181506);
               var9.field1459 = var4.trim();
               var9.field1458 = (var1 >> 8) * -371272079;
               var9.field1479 = (var1 & -1627261145) * 77157821;
               var9.field1457 = 885762115;
               var9.field1436 = classKV.method6611(var5, (byte)0);
               var9.field1500 = var3;
               var9.field1456 = var9.field1478 * 2089132006 != -1873862651 * client.field846
                  && var2.field3936
                  && !client.field930.isEmpty()
                  && var4.toLowerCase().indexOf(client.field930) == -1;
            }
         }
      }

      if (var6 && null != var7) {
         int var11;
         if (var2.field3938) {
            var11 = var3 ? 1760207677 : 1;
         } else {
            var11 = var3 ? 2004733920 : 2;
         }

         if (var2.field3937 * 1654631897 != -1) {
            classPH.method8924(var11, classDG.method3072(-645272838 * var2.field3937, (byte)-25) + var7, var4, 1604011846);
         } else {
            classPH.method8924(var11, var7, var4, 1208268826);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("dk")
   void method2962(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      for (int var8 = 0; var8 < this.field1387.size(); var8++) {
         Player var9 = (Player)this.field1387.get(var8);
         WorldView var10 = var9.method3177((byte)-113);
         int var11 = this.field1380.field4053 * 1870919123 - var10.field1706 * -1444178379;
         int var12 = 819227381 * this.field1380.field4054 - -351145363 * var10.field1707;
         var9.field1488 = -896779669 * (var11 + var1);
         var9.field1490 = (var12 + var2) * 749833987;
         var9.field1489 = 1685383399 * (var3 + var11);
         var9.field1492 = (var12 + var4) * -441508695;
         var9.field1494 = var5 * -1146707731;
         var9.field1495 = var6 * -590932479;
         var9.field1491 = 1569538929 * var7;
         var9.field1476 = 0;
         var9.method2648(801221335 * var9.field1489, -1067628647 * var9.field1492, -2129529733);
         var9.field1273 = this.field1380.field4055 * 294971065;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("db")
   void method2963(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      for (int var8 = 0; var8 < this.field1387.size(); var8++) {
         Player var9 = (Player)this.field1387.get(var8);
         WorldView var10 = var9.method3177((byte)-100);
         int var11 = this.field1380.field4053 * 1870919123 - var10.field1706 * -1444178379;
         int var12 = 819227381 * this.field1380.field4054 - -351145363 * var10.field1707;
         var9.field1488 = -896779669 * (var11 + var1);
         var9.field1490 = (var12 + var2) * 749833987;
         var9.field1489 = 1685383399 * (var3 + var11);
         var9.field1492 = (var12 + var4) * -441508695;
         var9.field1494 = var5 * -1146707731;
         var9.field1495 = var6 * -590932479;
         var9.field1491 = 1569538929 * var7;
         var9.field1476 = 0;
         var9.method2648(801221335 * var9.field1489, -1067628647 * var9.field1492, -1669662179);
         var9.field1273 = this.field1380.field4055 * 294971065;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;IIZ)V")
   @ObfuscatedName("gy")
   public static void method2982(classDC var0, int var1, int var2, boolean var3) {
      if (var0 == null) {
         var0.method2918(var1);
      }

      for (int var4 = 0; var4 < var0.field1387.size(); var4++) {
         Player var5 = classEN.method3954(((Actor)var0.field1387.get(var4)).field1478 * -1315528093, client.field815, 148539161);
         if (var5 != null) {
            if (-1 != var1 && var2 != -1) {
               var5.method3099(var1, var2, var3, (byte)1);
            } else {
               var5.method3105(2142808769);
               if (var3) {
                  Actor.method3111(var5, 1879378737);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("ci")
   void method2939(String var1) {
      boolean var2 = var1.charAt(0) == '~';
      if (var2) {
         var1 = var1.substring(1);
      }

      classAAE var3 = null;

      for (int var4 = 0; var4 < this.field1387.size(); var4++) {
         Player var5 = (Player)this.field1387.get(var4);
         var5.field1500 = false;
         var5.field1458 = 0;
         var5.field1479 = 0;
         var5.field1457 = 136918638;
         var5.field1459 = var1;
         var3 = var5.field1281;
      }

      if (var3 != null) {
         if (var2 || -1567029375 * client.field846 == 431142127 * this.field1381) {
            classPH.method8924(2, classAAE.method157(var3, 1646002436), var1, 1754074239);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILdz;I)Lct;")
   @ObfuscatedName("ax")
   Player method2912(int var1, WorldView var2, int var3) {
      try {
         Player var4 = new Player(var1);
         if (null != this.field1393) {
            if (var3 != 844956059) {
               throw new IllegalStateException();
            }

            var4.method2615(this.field1393, -1316125685);
         }

         var4.field1493 = 0;
         var4.method3224(this.field1378, 1307355397);
         var4.method3196(this.field1386 * 190337821, this.field1385 * -800586389, this.field1383, 2032678852);
         if (null != this.field1384) {
            if (var3 != 844956059) {
               throw new IllegalStateException();
            }

            var4.field1475 = new classAAV(this.field1384);
         }

         var4.field1474[0] = this.field1392;
         var4.field1273 = this.field1380.field4055 * 294971065;
         var4.field1437 = -1063547535 * var2.field1697;
         var4.field1268 = Arrays.copyOf(var4.field1268, var4.field1268.length);
         var4.method3123(
            1870919123 * this.field1380.field4053 - var2.field1706 * -1444178379, 819227381 * this.field1380.field4054 - var2.field1707 * -351145363, 527441438
         );
         return var4;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dc.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldc;[Ljava/lang/String;B)V")
   @ObfuscatedName("tb")
   public static void method2967(classDC var0, String[] var1, byte var2) {
      if (var0 == null) {
         var0.method2970(var1, var2);
      }

      try {
         var0.field1382 = Arrays.copyOf(var1, var1.length);

         for (int var3 = 0; var3 < var0.field1387.size(); var3++) {
            if (var2 == 9) {
               throw new IllegalStateException();
            }

            ((Player)var0.field1387.get(var3)).field1268 = Arrays.copyOf(var1, var1.length);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   void method2900() {
      this.field1384 = null;
      this.field1386 = 1704655051;
      this.field1385 = -1854144323;
      classFL.method4856(this.field1383);
      this.field1393 = null;
      this.field1392 = classGT.field2678;
      this.field1378.method1378((byte)45);
      Arrays.fill(this.field1382, "");

      for (int var1 = 0; var1 < this.field1387.size(); var1++) {
         Player var2 = (Player)this.field1387.get(var1);
         var2.field1475 = null;
         var2.method3196(-1, -1, this.field1383, 1394613206);
         var2.field1474[0] = this.field1392;
         var2.method3224(this.field1378, -540660818);
         var2.field1268 = Arrays.copyOf(this.field1382, 3);
         classCU.method2672(var2, -1, 0, -177813505);
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("dn")
   void method2968(String[] var1) {
      this.field1382 = Arrays.copyOf(var1, var1.length);

      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         ((Player)this.field1387.get(var2)).field1268 = Arrays.copyOf(var1, var1.length);
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("dv")
   void method2969(String[] var1) {
      this.field1382 = Arrays.copyOf(var1, var1.length);

      for (int var2 = 0; var2 < this.field1387.size(); var2++) {
         ((Player)this.field1387.get(var2)).field1268 = Arrays.copyOf(var1, var1.length);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIBBBB)V")
   @ObfuscatedName("do")
   void method2972(int var1, int var2, byte var3, byte var4, byte var5, byte var6) {
      this.field1386 = var1 * -1704655051;
      this.field1385 = var2 * -2104600102;
      classFL.method4850(this.field1383, var3, var4, var5, var6);

      for (int var7 = 0; var7 < this.field1387.size(); var7++) {
         Player var8 = (Player)this.field1387.get(var7);
         var8.method3196(this.field1386 * 190337821, this.field1385 * 814151382, this.field1383, 1706564511);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method2914(int var1) {
      try {
         this.field1379 = classKY.method6668(this.field1380, 39949557) * 1621593763;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIBBZ)V")
   @ObfuscatedName("dl")
   void method2975(int var1, int var2, int var3, byte var4, byte var5, boolean var6) {
      if (null == this.field1384) {
         this.field1384 = new classAAV();
      }

      this.field1384.method271(var1, var2, var3, var4, var5, var6);

      for (int var7 = 0; var7 < this.field1387.size(); var7++) {
         Player var8 = (Player)this.field1387.get(var7);
         if (var8.field1475 == null) {
            var8.field1475 = new classAAV();
         }

         var8.field1475.method271(var1, var2, var3, var4, var5, var6);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;I)V")
   @ObfuscatedName("jy")
   public static void method2952(classDC var0, int var1) {
      if (var0 == null) {
         var0.method2962(var1, var1, var1, var1, var1, var1, var1);
      }

      for (int var2 = 0; var2 < var0.field1387.size(); var2++) {
         ((Actor)var0.field1387.get(var2)).method3145(var1, 496850727);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("df")
   void method2977(int var1, int var2, int var3, int var4, boolean var5) {
      for (int var6 = 0; var6 < this.field1387.size(); var6++) {
         ((Actor)this.field1387.get(var6)).method3148(var1, var2, var3, var4, var5, (byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZ)V")
   @ObfuscatedName("dq")
   void method2978(int var1, int var2, int var3, int var4, boolean var5) {
      for (int var6 = 0; var6 < this.field1387.size(); var6++) {
         ((Actor)this.field1387.get(var6)).method3148(var1, var2, var3, var4, var5, (byte)71);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZI)V")
   @ObfuscatedName("bc")
   void method2979(int var1, int var2, int var3, int var4, boolean var5, int var6) {
      try {
         for (int var7 = 0; var7 < this.field1387.size(); var7++) {
            if (var6 <= 802196222) {
               throw new IllegalStateException();
            }

            ((Actor)this.field1387.get(var7)).method3148(var1, var2, var3, var4, var5, (byte)-42);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "dc.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("dc")
   void method2945(int var1, int var2, int var3, int var4) {
      for (int var5 = 0; var5 < this.field1387.size(); var5++) {
         ((Actor)this.field1387.get(var5)).method3138(var1, var2, client.field856 * 1612595797, var3, var4, 1181887244);
      }
   }

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("ab")
   public static void method2926(short var0) {
      try {
         ObjectComposition.field4964.method6468();
         ObjectComposition.field4966.method6468();
         ObjectComposition.field4970.method6468();
         ObjectComposition.field4965.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "dc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("kc")
   public void method2987(Player var1) {
      classOE.field4841.getCallbacks().post(new PlayerDespawned(var1));
      if (field1391 == var1) {
         field1391 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("ge")
   static void method2985(String var0, byte var1) {
      try {
         classQW.field5620 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.ge(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   void method2890(byte var1) {
      this.method2986();

      try {
         this.field1384 = null;
         classFL.method4856(this.field1383);
         this.field1393 = null;
         this.field1387.clear();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dc.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aq")
   void method2953(int var1, int var2) {
      try {
         for (int var3 = 0; var3 < this.field1387.size(); var3++) {
            if (var2 <= 1597272529) {
               throw new IllegalStateException();
            }

            ((Actor)this.field1387.get(var3)).method3145(var1, 1511865987);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   void method2922(int var1) {
      this.field1380.field4053 = -1004595279 * var1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)V")
   @ObfuscatedName("am")
   void method2964(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      try {
         for (int var9 = 0; var9 < this.field1387.size(); var9++) {
            if (var8 >= 1409101674) {
               throw new IllegalStateException();
            }

            Player var10 = (Player)this.field1387.get(var9);
            WorldView var11 = var10.method3177((byte)8);
            int var12 = this.field1380.field4055 * 1870919123 - var11.field1707 * -1444178379;
            int var13 = 819227381 * this.field1380.field4054 - -351145363 * var11.field1707;
            var10.field1486 = -896779669 * (var12 + var1);
            var10.field1477 = (var13 + var2) * 749833987;
            var10.field1452 = 1685383399 * (var3 + var12);
            var10.field1448 = (var13 + var4) * -441508695;
            var10.field1445 = var5 * -1146707731;
            var10.field1476 = var6 * -590932479;
            var10.field1489 = 1569538929 * var7;
            var10.field1447 = 0;
            var10.method2648(801221335 * var10.field1476, -1067628647 * var10.field1469, -1893287312);
            var10.field1258 = this.field1380.field4055 * 294971065;
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "dc.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILkl;ZLjava/lang/String;[BI)V")
   @ObfuscatedName("an")
   void method2957(int var1, classKL var2, boolean var3, String var4, byte[] var5, int var6) {
      try {
         boolean var7 = false;
         String var8 = null;

         for (int var9 = 0; var9 < this.field1387.size(); var9++) {
            if (var6 >= -374161745) {
               return;
            }

            Player var10 = (Player)this.field1387.get(var9);
            if (var10.field1281 != null) {
               if (var6 >= -374161745) {
                  throw new IllegalStateException();
               }

               if (null != var10.field1283) {
                  if (var6 >= -374161745) {
                     return;
                  }

                  boolean var11 = false;
                  if (var2.field3936 && classQQ.field5595.method1299(var10.field1281, -1181618808)) {
                     if (var6 >= -374161745) {
                        throw new IllegalStateException();
                     }

                     var11 = true;
                  }

                  if (!var11) {
                     if (var6 >= -374161745) {
                        throw new IllegalStateException();
                     }

                     if (1746877283 * client.field922 == 0 && !var10.field1262) {
                        if (var6 >= -374161745) {
                           throw new IllegalStateException();
                        }

                        boolean var10001;
                        label129: {
                           var7 = true;
                           var8 = classAAE.method157(var10.field1281, 1902618827);
                           var10.field1459 = var4.trim();
                           var10.setIdleRotateLeft(-1);
                           var10.field1449 = (var1 >> 8) * 743710599;
                           var10.field1441 = (var1 & 0xFF) * 77157821;
                           var10.field1478 = 136918638;
                           var10.field1436 = classKV.method6611(var5, (byte)0);
                           var10.field1466 = var3;
                           if (var10.field1487 * -1315528093 != -2130951373 * client.field1069) {
                              if (var6 >= -374161745) {
                                 throw new IllegalStateException();
                              }

                              if (var2.field3936) {
                                 if (var6 >= -374161745) {
                                    throw new IllegalStateException();
                                 }

                                 if (!client.field805.isEmpty()) {
                                    if (var6 >= -374161745) {
                                       throw new IllegalStateException();
                                    }

                                    if (var4.toLowerCase().indexOf(client.field849) == -1) {
                                       if (var6 >= -374161745) {
                                          throw new IllegalStateException();
                                       }

                                       var10001 = true;
                                       break label129;
                                    }
                                 }
                              }
                           }

                           var10001 = false;
                        }

                        var10.field1438 = var10001;
                     }
                  }
               }
            }
         }

         if (var7) {
            if (var6 >= -374161745) {
               return;
            }

            if (null != var8) {
               if (var6 >= -374161745) {
                  throw new IllegalStateException();
               }

               byte var13;
               if (var2.field3936) {
                  if (var6 >= -374161745) {
                     throw new IllegalStateException();
                  }

                  byte var10000;
                  if (var3) {
                     if (var6 >= -374161745) {
                        throw new IllegalStateException();
                     }

                     var10000 = 91;
                  } else {
                     var10000 = 1;
                  }

                  var13 = var10000;
               } else {
                  byte var14;
                  if (var3) {
                     if (var6 >= -374161745) {
                        return;
                     }

                     var14 = 90;
                  } else {
                     var14 = 2;
                  }

                  var13 = var14;
               }

               if (var2.field3937 * 1654631897 != -1) {
                  if (var6 >= -374161745) {
                     throw new IllegalStateException();
                  }

                  classPH.method8924(var13, classDG.method3072(1654631897 * var2.field3937, (byte)-21) + var8, var4, 1742813307);
               } else {
                  classPH.method8924(var13, var8, var4, 2094937243);
               }
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "dc.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;B)V")
   @ObfuscatedName("ah")
   void method2970(String[] var1, byte var2) {
      try {
         this.field1382 = Arrays.copyOf(var1, var1.length);

         for (int var3 = 0; var3 < this.field1387.size(); var3++) {
            if (var2 == 9) {
               throw new IllegalStateException();
            }

            ((Player)this.field1387.get(var3)).field1268 = Arrays.copyOf(var1, var1.length);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dc.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIBBZB)V")
   @ObfuscatedName("bx")
   void method2976(int var1, int var2, int var3, byte var4, byte var5, boolean var6, byte var7) {
      try {
         if (null == this.field1384) {
            if (var7 != 0) {
               return;
            }

            this.field1384 = new classAAV();
         }

         this.field1384.method271(var1, var2, var3, var4, var5, var6);

         for (int var8 = 0; var8 < this.field1387.size(); var8++) {
            if (var7 != 0) {
               throw new IllegalStateException();
            }

            Player var9 = (Player)this.field1387.get(var8);
            if (var9.field1475 == null) {
               if (var7 != 0) {
                  throw new IllegalStateException();
               }

               var9.field1475 = new classAAV();
            }

            var9.field1475.method271(var1, var2, var3, var4, var5, var6);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "dc.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZI)V")
   @ObfuscatedName("bp")
   void method2983(int var1, int var2, boolean var3, int var4) {
      try {
         for (int var5 = 0; var5 < this.field1387.size(); var5++) {
            if (var4 == 811047921) {
               return;
            }

            Player var6 = classEN.method3954(((Actor)this.field1387.get(var5)).field1435 * -1315528093, client.field815, 408323482);
            if (var6 != null) {
               if (var4 == 811047921) {
                  throw new IllegalStateException();
               }

               if (-1 != var1) {
                  if (var4 == 811047921) {
                     return;
                  }

                  if (var2 != -1) {
                     var6.method3099(var1, var2, var3, (byte)1);
                     continue;
                  }

                  if (var4 == 811047921) {
                     throw new IllegalStateException();
                  }
               }

               var6.method3105(2128205205);
               if (var3) {
                  Actor.method3111(var6, -292214652);
               }
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "dc.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldc;Ljava/lang/String;I)V")
   @ObfuscatedName("xm")
   public static void method2940(classDC var0, String var1, int var2) {
      if (var0 == null) {
         var0.method2941(var1, var2);
      } else {
         try {
            boolean var10000;
            if (var1.charAt(0) == '~') {
               if (var2 == -1017549562) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var3 = var10000;
            if (var3) {
               if (var2 == -1017549562) {
                  throw new IllegalStateException();
               }

               var1 = var1.substring(1);
            }

            classAAE var4 = null;

            for (int var5 = 0; var5 < var0.field1387.size(); var5++) {
               if (var2 == -1017549562) {
                  throw new IllegalStateException();
               }

               Player var6 = (Player)var0.field1387.get(var5);
               var6.field1500 = false;
               var6.field1458 = 0;
               var6.field1479 = 0;
               var6.field1457 = 136918638;
               var6.field1459 = var1;
               var6.method3279(-1);
               var4 = var6.field1281;
            }

            if (var4 == null) {
               if (var2 != -1017549562) {
                  ;
               }
            } else {
               if (!var3) {
                  if (var2 == -1017549562) {
                     throw new IllegalStateException();
                  }

                  if (-2130951373 * client.field846 != 431142127 * var0.field1381) {
                     return;
                  }

                  if (var2 == -1017549562) {
                     throw new IllegalStateException();
                  }
               }

               classPH.method8924(2, classAAE.method157(var4, 1965974550), var1, 1858926038);
            }
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "dc.aw(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("aw")
   void method2941(String var1, int var2) {
      try {
         boolean var10000;
         if (var1.charAt(0) == '~') {
            if (var2 == -1017549562) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var3 = var10000;
         if (var3) {
            if (var2 == -1017549562) {
               throw new IllegalStateException();
            }

            var1 = var1.substring(1);
         }

         classAAE var4 = null;

         for (int var5 = 0; var5 < this.field1387.size(); var5++) {
            if (var2 == -1017549562) {
               throw new IllegalStateException();
            }

            Player var6 = (Player)this.field1387.get(var5);
            var6.field1466 = false;
            var6.field1458 = 0;
            var6.field1448 = 0;
            var6.field1442 = 136918638;
            var6.field1459 = var1;
            var6.setGraphic(-1);
            var4 = var6.field1281;
         }

         if (var4 == null) {
            if (var2 != -1017549562) {
               ;
            }
         } else {
            if (!var3) {
               if (var2 == -1017549562) {
                  throw new IllegalStateException();
               }

               if (-2130951373 * client.field799 != 431142127 * this.field1385) {
                  return;
               }

               if (var2 == -1017549562) {
                  throw new IllegalStateException();
               }
            }

            classPH.method8924(2, classAAE.method157(var4, 1965974550), var1, 1858926038);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "dc.aw(" + ')');
      }
   }
}
