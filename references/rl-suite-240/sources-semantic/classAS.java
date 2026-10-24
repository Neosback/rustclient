import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("as")
public class classAS implements classXZ {
   @ObfuscatedSignature(descriptor = "Las;")
   @ObfuscatedName("az")
   public static final classAS field261 = new classAS(0, 0);
   @ObfuscatedName("af")
   final int field263;
   @ToRemove(unused = "true")
   @ObfuscatedName("cq")
   public static final int field271 = 1012;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field270 = 52;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field267 = 31;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field265 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("bf")
   static final int field268 = 66;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   static final int field269 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final int field266 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   public static final int field264 = 23;
   @ObfuscatedName("ae")
   final int field262;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ay")
   static final boolean method657(int var0) {
      try {
         return classFO.field2350;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "as.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field262 * 806516801;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "as.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field262 * 806516801;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field262 * 806516801;
   }

   classAS(int var1, int var2) {
      this.field263 = var1 * 47309441;
      this.field262 = -1947887679 * var2;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;ZLxs;II)V")
   @ObfuscatedName("en")
   static final void method661(WorldView var0, boolean var1, PacketBuffer var2, int var3, int var4) {
      client.method2335(var0, var1, var2, var3);

      try {
         client.field967 = 0;
         client.field965 = 0;
         var2.method12981((byte)122);
         int var5 = var2.method12982(8, 308191265);
         if (var5 < var0.field1695.method14050((byte)10)) {
            if (var4 != 1181900450) {
               throw new IllegalStateException();
            }

            for (int var6 = var5; var6 < var0.field1695.method14050((byte)10); var6++) {
               if (var4 != 1181900450) {
                  throw new IllegalStateException();
               }

               int var7 = var0.field1695.method14048(var6, 2040213812);
               client.field880[(client.field967 += -865693015) * 329265561 - 1] = var7;
               NPC var8 = (NPC)IndexedObjectSet.method13449(var0.field1696, var7);
               var8.field1567 = true;
            }
         }

         if (var5 > var0.field1695.method14050((byte)10)) {
            if (var4 != 1181900450) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException("");
            }
         } else {
            var0.field1695.method14053(1993245191);

            for (int var23 = 0; var23 < var5; var23++) {
               int var27 = var0.field1695.method14048(var23, 1722192272);
               NPC var31 = (NPC)IndexedObjectSet.method13449(var0.field1696, var27);
               int var9 = var2.method12982(1, -145365480);
               if (0 == var9) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  var0.field1695.method14046(var27, (byte)44);
               } else {
                  int var10 = var2.method12982(2, 1402237970);
                  if (var10 == 0) {
                     var0.field1695.method14046(var27, (byte)44);
                     client.field820[(client.field965 += 1698390547) * -1700415461 - 1] = var27;
                  } else if (1 == var10) {
                     var0.field1695.method14046(var27, (byte)44);
                     int var11 = var2.method12982(3, 368184984);
                     NPC.method3465(var31, var11, classGT.field2678, -364941558);
                     int var12 = var2.method12982(1, 353055031);
                     if (var12 == 1) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        client.field820[(client.field965 += 1698390547) * -1700415461 - 1] = var27;
                     }
                  } else if (var10 == 2) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var0.field1695.method14046(var27, (byte)44);
                     if (var2.method12982(1, 194788446) == 1) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var65 = var2.method12982(3, -564204753);
                        NPC.method3465(var31, var65, classGT.field2679, -1564807351);
                        int var84 = var2.method12982(3, 749194850);
                        NPC.method3465(var31, var84, classGT.field2679, 2062628786);
                     } else {
                        int var66 = var2.method12982(3, 603083976);
                        NPC.method3465(var31, var66, classGT.field2677, 98253588);
                     }

                     int var67 = var2.method12982(1, 1434471390);
                     if (var67 == 1) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        client.field820[(client.field965 += 1698390547) * -1700415461 - 1] = var27;
                     }
                  } else if (var10 == 3) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     client.field880[(client.field967 += -865693015) * 329265561 - 1] = var27;
                     var31.field1567 = true;
                  }
               }
            }

            while (true) {
               byte var20 = 16;
               int var24 = 1 << var20;
               if (var2.method12991(client.field796.field1523 * 1265354011, 1833920460) < var20 + 12) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }
                  break;
               }

               int var28 = var2.method12982(var20, 2116811684);
               if (var24 - 1 == var28) {
                  break;
               }

               boolean var32 = false;
               NPC var34 = (NPC)IndexedObjectSet.method13449(var0.field1696, var28);
               if (var34 == null) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  var34 = new NPC(var28);
                  var0.field1696.method13440(var34, var28);
                  var32 = true;
               }

               var0.field1695.method14046(var28, (byte)44);
               var34.field1567 = false;
               var34.field1437 = var0.field1697 * -1063547535;
               int var52;
               int var68;
               int var85;
               if (var3 == 6) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  boolean var10000;
                  if (var2.method12982(1, -134337388) == 1) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  boolean var13 = var10000;
                  if (var13) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     int var14 = var2.method12982(2, -736691179);
                     var2.method12982(classKA.field3507[var14], 415692621);
                  }

                  int var123 = var2.method12982(2, 915659293);
                  NPCComposition var10001 = classOH.method8480(var2.method12982(classKA.field3506[var123], -462300259), 1362367125);
                  var34.method3510(var10001);
                  var34.field1579 = var10001;
                  int var15 = var2.method12982(1, -722704948);
                  if (1 == var15) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     client.field820[(client.field965 += 1698390547) * -1700415461 - 1] = var28;
                  }

                  if (var1) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var68 = var2.method12982(8, 1536175689);
                     if (var68 > 127) {
                        var68 -= 256;
                     }
                  } else {
                     var68 = var2.method12982(6, 317335627);
                     if (var68 > 31) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var68 -= 64;
                     }
                  }

                  int var16 = client.field971[var2.method12982(3, 1968931012)];
                  if (var32) {
                     var34.field1493 = (var34.field1485 = var16 * 573632145) * 44818599;
                     Actor.method3273(var34, 677773095 * var34.field1493, 1057159638);
                  }

                  var52 = var2.method12982(1, -673170020);
                  if (var1) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var85 = var2.method12982(8, 1319207052);
                     if (var85 > 127) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var85 -= 256;
                     }
                  } else {
                     var85 = var2.method12982(6, 760795403);
                     if (var85 > 31) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var85 -= 64;
                     }
                  }
               } else {
                  boolean var140;
                  if (var2.method12982(1, 499194133) == 1) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var140 = true;
                  } else {
                     var140 = false;
                  }

                  boolean var103 = var140;
                  if (var103) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var2.method12982(32, 1883625718);
                  }

                  NPCComposition var148 = classOH.method8480(var2.method12982(14, -876955785), 214657866);
                  var34.method3510(var148);
                  var34.field1579 = var148;
                  int var124 = client.field971[var2.method12982(3, -412576029)];
                  if (var32) {
                     var34.field1493 = (var34.field1485 = 573632145 * var124) * 44818599;
                     Actor.method3273(var34, var34.field1493 * 677773095, 1057159638);
                  }

                  if (var1) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var68 = var2.method12982(8, -99572022);
                     if (var68 > 127) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var68 -= 256;
                     }
                  } else {
                     var68 = var2.method12982(6, 622761200);
                     if (var68 > 31) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var68 -= 64;
                     }
                  }

                  var52 = var2.method12982(1, -301809421);
                  if (var1) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var85 = var2.method12982(8, -815067267);
                     if (var85 > 127) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var85 -= 256;
                     }
                  } else {
                     var85 = var2.method12982(6, 1987925524);
                     if (var85 > 31) {
                        var85 -= 64;
                     }
                  }

                  int var135 = var2.method12982(1, -700936429);
                  if (1 == var135) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     client.field820[(client.field965 += 1698390547) * -1700415461 - 1] = var28;
                  }
               }

               classGA.method5325(var34, (byte)17);
               if (0 == var34.field1470 * -909895021) {
                  var34.field1485 = 0;
               }

               boolean var141;
               label1286: {
                  if (var52 != 1) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     if (!var32) {
                        var141 = false;
                        break label1286;
                     }

                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }
                  }

                  var141 = true;
               }

               boolean var104 = var141;
               var34.method3471(var68 + classLX.field4421 * 115065451, classKL.field3956 * 1747376027 + var85, var104, (byte)-36);
               if (var32) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  var0.method3758(var34, (byte)-84);
               }
            }

            var2.method12987(-2046354893);

            for (int var21 = 0; var21 < -1700415461 * client.field965; var21++) {
               if (var4 != 1181900450) {
                  client.method2226(var0, var1, var2, var3);
                  return;
               }

               int var25 = client.field820[var21];
               NPC var29 = (NPC)IndexedObjectSet.method13449(var0.field1696, var25);
               int var33 = var2.method13095(-346779531);
               if (0 != (var33 & 32)) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  int var35 = var2.method13095(-346779531);
                  var33 += var35 << 8;
               }

               if ((var33 & 4096) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var36 = var2.method13095(-346779531);
                  var33 += var36 << 16;
               }

               if ((var33 & 262144) != 0) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  int var37 = var2.method13095(-346779531);
                  var33 += var37 << 24;
               }

               if ((var33 & 512) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var38 = 1612595797 * client.field856 + var2.method13104((byte)16);
                  int var53 = 1612595797 * client.field856 + classXY.method13262(var2, (byte)78);
                  byte var69 = var2.method13232((byte)-85);
                  byte var86 = var2.method13235((byte)-83);
                  byte var105 = var2.method13235((byte)-53);
                  byte var125 = (byte)var2.method13224(-1228588202);
                  Actor.method3192(var29, var38, var53, var69, var86, var105, var125, 1609649769);
               }

               if (0 != (var33 & 2097152)) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  int var39 = var2.method13303((byte)-96);
                  int var149;
                  if (0 != (var39 & 1)) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var149 = var2.method13254(-753344475);
                  } else {
                     var149 = var29.field1579.field5289 * 200737075;
                  }

                  var29.field1442 = var149 * -349066389;
                  if ((var39 & 2) != 0) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var149 = var2.method13250(563730555);
                  } else {
                     var149 = var29.field1579.field5305 * -684030479;
                  }

                  var29.field1455 = var149 * 190249931;
                  var29.field1443 = ((var39 & 4) != 0 ? var2.method13099(-217049696) : var29.field1579.field5283 * -385365945) * -950783279;
                  if ((var39 & 8) != 0) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var149 = var2.method13250(933688625);
                  } else {
                     var149 = 606272783 * var29.field1579.field5306;
                  }

                  var29.field1460 = var149 * 164191859;
                  var29.field1448 = (0 != (var39 & 16) ? var2.method13250(521904082) : var29.field1579.field5284 * -753562291) * 1936897965;
                  if (0 != (var39 & 32)) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var149 = var2.method13254(-1456735787);
                  } else {
                     var149 = -1701410385 * var29.field1579.field5307;
                  }

                  var29.field1446 = var149 * -1239814673;
                  if ((var39 & 64) != 0) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var149 = var2.method13099(1172273662);
                  } else {
                     var149 = -175493449 * var29.field1579.field5279;
                  }

                  var29.field1447 = var149 * -627296855;
                  if ((var39 & 128) != 0) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var149 = classXY.method13251(var2, 1461580632);
                  } else {
                     var149 = var29.field1579.field5308 * -1439621047;
                  }

                  var29.field1441 = var149 * -982254409;
                  var29.field1449 = ((var39 & 256) != 0 ? var2.method13250(-923456327) : var29.field1579.field5299 * 1028381445) * -1508127879;
                  if (0 != (var39 & 512)) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var149 = classXY.method13251(var2, 857858681);
                  } else {
                     var149 = 687196259 * var29.field1579.field5309;
                  }

                  var29.field1450 = var149 * 245610447;
                  if (0 != (var39 & 1024)) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var149 = var2.method13250(-1773702892);
                  } else {
                     var149 = var29.field1579.field5280 * -1933236387;
                  }

                  var29.field1451 = var149 * -367560861;
                  if (0 != (var39 & 2048)) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var149 = classXY.method13251(var2, -612453192);
                  } else {
                     var149 = 626211571 * var29.field1579.field5296;
                  }

                  var29.field1444 = var149 * 202285279;
                  if (0 != (var39 & 4096)) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var149 = var2.method13254(-1267940853);
                  } else {
                     var149 = var29.field1579.field5301 * -143551633;
                  }

                  var29.field1453 = var149 * 1186961409;
                  if (0 != (var39 & 8192)) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var149 = var2.method13099(-118190364);
                  } else {
                     var149 = -959115373 * var29.field1579.field5310;
                  }

                  var29.field1454 = var149 * 1823934115;
                  if (0 != (var39 & 16384)) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var149 = var2.method13099(-2122349918);
                  } else {
                     var149 = 1841277299 * var29.field1579.field5304;
                  }

                  var29.field1434 = var149 * -506237411;
               }

               if ((var33 & 2) != 0) {
                  int var40 = var2.method13225(-1017529306);

                  for (int var54 = 0; var54 < var40; var54++) {
                     int var70 = var2.method13095(-346779531);
                     int var87 = var2.method13099(-2042417023);
                     int var106 = var2.method13295(-2100807916);
                     boolean var142;
                     if (var2.method13218((byte)-73) == 1) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var142 = true;
                     } else {
                        var142 = false;
                     }

                     boolean var126 = var142;
                     var29.method3148(var70, var87, var106 >> 16, var106 & 65535, var126, (byte)35);
                  }
               }

               if ((var33 & 524288) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var41 = var2.method13218((byte)-63);
                  if ((var41 & 1) == 1) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     NPC.method3501(var29, 2077516204);
                  } else {
                     int[] var55 = null;
                     if (2 == (var41 & 2)) {
                        int var71 = var2.method13218((byte)-128);
                        var55 = new int[var71];

                        for (int var88 = 0; var88 < var71; var88++) {
                           if (var4 != 1181900450) {
                              client.method2226(var0, var1, var2, var3);
                              return;
                           }

                           int var107 = var2.method13303((byte)-37);
                           var55[var88] = var107;
                        }
                     }

                     short[] var72 = null;
                     if (4 == (var41 & 4)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var89 = 0;
                        if (var29.field1579.field5313 != null) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var89 = var29.field1579.field5313.length;
                        }

                        var72 = new short[var89];

                        for (int var108 = 0; var108 < var89; var108++) {
                           var72[var108] = (short)classXY.method13251(var2, 1645510728);
                        }
                     }

                     short[] var90 = null;
                     if (8 == (var41 & 8)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var109 = 0;
                        if (var29.field1579.field5315 != null) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var109 = var29.field1579.field5315.length;
                        }

                        var90 = new short[var109];

                        for (int var127 = 0; var127 < var109; var127++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var90[var127] = (short)var2.method13254(-47108502);
                        }
                     }

                     boolean var110 = false;
                     if ((var41 & 16) != 0) {
                        boolean var143;
                        if (var2.method13225(-844686236) == 1) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var143 = true;
                        } else {
                           var143 = false;
                        }

                        var110 = var143;
                     }

                     long var128 = (NPC.field1571 += 1679619839) * 2123499775 - 1;
                     var29.method3496(new NpcOverrides(var128, var55, var72, var90, var110), (byte)-86);
                  }
               }

               if (0 != (var33 & 65536)) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var42 = var2.method13218((byte)-7);
                  if (0 == var42) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var29.method3491(291614349);
                  } else {
                     int[] var56 = new int[8];
                     short[] var73 = new short[8];

                     for (int var91 = 0; var91 < 8; var91++) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        if (0 != (var42 & 1 << var91)) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var56[var91] = classXY.method13169(var2, -324749371);
                           var73[var91] = (short)var2.method13152(180438991);
                        } else {
                           var56[var91] = -1;
                           var73[var91] = -1;
                        }
                     }

                     var29.method3488(var56, var73, -185757508);
                  }
               }

               if (0 != (var33 & 1024)) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  var29.method3453(var2.method13225(-1613937393), (byte)81);
               }

               if ((var33 & 33554432) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var43 = var2.method13225(-1937274999);
                  if (1 == (var43 & 1)) {
                     var29.method3503(1205188322);
                  } else {
                     int[] var57 = null;
                     if ((var43 & 2) == 2) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var74 = var2.method13095(-346779531);
                        var57 = new int[var74];

                        for (int var92 = 0; var92 < var74; var92++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           int var111 = classXY.method13113(var2, (byte)1);
                           var57[var92] = var111;
                        }
                     }

                     short[] var75 = null;
                     if (4 == (var43 & 4)) {
                        if (var4 != 1181900450) {
                           client.method2226(var0, var1, var2, var3);
                           return;
                        }

                        int var93 = var2.method13095(-346779531);
                        var75 = new short[var93];

                        for (int var112 = 0; var112 < var93; var112++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var75[var112] = (short)var2.method13099(-2125748454);
                        }
                     }

                     short[] var94 = null;
                     if ((var43 & 8) == 8) {
                        if (var4 != 1181900450) {
                           client.method2226(var0, var1, var2, var3);
                           return;
                        }

                        int var113 = var2.method13224(-317715787);
                        var94 = new short[var113];

                        for (int var129 = 0; var129 < var113; var129++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var94[var129] = (short)var2.method13250(490176080);
                        }
                     }

                     boolean var114 = false;
                     if (0 != (var43 & 16)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        var114 = true;
                     }

                     boolean var130 = false;
                     int var136 = 0;
                     int[] var138 = null;
                     if ((var43 & 32) != 0) {
                        if (var4 != 1181900450) {
                           client.method2226(var0, var1, var2, var3);
                           return;
                        }

                        var130 = true;
                        var136 = var2.method13218((byte)-100);
                        int var17 = var2.method13218((byte)-89);
                        var138 = new int[var17];

                        for (int var18 = 0; var18 < var17; var18++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var138[var18] = var2.method13254(104280378);
                        }
                     }

                     long var139 = (NPC.field1570 += 1703859013) * -1914714227 - 1;
                     var29.method3500(new NpcOverrides(var139, var57, var75, var94, var114, var130, var136, var138), (short)13586);
                  }
               }

               if (0 != (var33 & 16)) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  var29.field1459 = var2.method13127(1545703876);
                  var29.method3279(-1);
                  var29.field1457 = 91279092;
               }

               if ((var33 & 8388608) != 0) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  classXY.method13251(var2, 503543077);
                  var2.method13225(-1812821151);
               }

               if (0 != (var33 & 1)) {
                  int var44 = var2.method13254(-799196645);
                  if (65535 == var44) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var44 = -1;
                  }

                  int var58 = classXY.method13251(var2, -458713957);
                  if (var58 == 65535) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var58 = -1;
                  }

                  boolean var144;
                  if (var2.method13095(-346779531) != 0) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var144 = true;
                  } else {
                     var144 = false;
                  }

                  boolean var76 = var144;
                  label1176:
                  if (null != var29) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     if (-1 != var44) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        if (var58 != -1) {
                           var29.method3099(var44, var58, var76, (byte)1);
                           break label1176;
                        }
                     }

                     var29.method3105(2127788256);
                     if (var76) {
                        Actor.method3111(var29, -345992269);
                     }
                  }
               }

               if ((var33 & 16384) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  var2.method13095(-346779531);
                  var2.method13224(-1482696659);
                  classXY.method13251(var2, 1237155844);
                  var2.method13099(1766961035);
                  var2.method13250(-1361810613);
                  var2.method13218((byte)-94);
               }

               if ((var33 & 2048) != 0) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  var29.field1477 = var2.method13295(-2100807916) * 2128850993;
               }

               if ((var33 & 64) != 0) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  var2.method13250(-468840059);
                  var2.method13218((byte)-93);
               }

               if ((var33 & 8192) != 0) {
                  var29.field1488 = var2.method13229((byte)22) * -896779669;
                  var29.field1490 = var2.method13235((byte)-3) * 749833987;
                  var29.field1489 = var2.method13097((byte)17) * 1685383399;
                  var29.field1492 = var2.method13229((byte)116) * -441508695;
                  var29.field1494 = (var2.method13250(1291608415) + client.field856 * 1612595797) * -1146707731;
                  var29.field1495 = (classXY.method13251(var2, 174297425) + 1612595797 * client.field856) * -590932479;
                  var29.field1491 = var2.method13250(278623629) * 1569538929;
                  var29.field1471 = 0;
                  var29.field1476 = 0;
                  var29.field1488 = var29.field1488 + -896779669 * var29.field1472[0];
                  var29.field1490 = var29.field1490 + 749833987 * var29.field1431[0];
                  var29.field1489 = var29.field1489 + 1685383399 * var29.field1472[0];
                  var29.field1492 = var29.field1492 + -441508695 * var29.field1431[0];
               }

               label1208:
               if (0 != (var33 & 4)) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var45 = var2.method13250(-10347803);
                  if (var45 == 65535) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var45 = -1;
                  }

                  int var59 = var2.method13218((byte)-17);
                  Animation var77 = var29.field1463.method9775((byte)58);
                  if (var45 == var29.method3106(423793591)) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     if (var45 != -1) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var95 = var77.field5120 * 1697446663;
                        if (var95 == 1) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           classQR.method9791(var29.field1463, 640471857);
                           var29.field1465 = var59 * -285300085;
                        }

                        if (var95 == 2) {
                           var29.field1463.method9793(-1855252861);
                        }
                        break label1208;
                     }
                  }

                  if (var45 != -1) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     if (var29.field1463.method9772(374233424)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        if (classQA.method9477(var45, 1042211581).field5119 * -1968168817 < var77.field5119 * -1968168817) {
                           break label1208;
                        }

                        if (var4 != 1181900450) {
                           client.method2226(var0, var1, var2, var3);
                           return;
                        }
                     }
                  }

                  classQR.method9770(var29.field1463, var45, -2131313163);
                  classQR.method9791(var29.field1463, -1014337303);
                  var29.field1465 = var59 * -285300085;
                  var29.field1476 = -574940415 * var29.field1471;
               }

               if ((var33 & 131072) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var46 = var2.method13218((byte)-104);
                  if (var46 > 0) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     for (int var60 = 0; var60 < var46; var60++) {
                        if (var4 != 1181900450) {
                           client.method2226(var0, var1, var2, var3);
                           return;
                        }

                        int var78 = var2.method13150((short)12002);
                        int var96 = var2.method13150((short)10199);
                        int var115 = var2.method13150((short)19497);
                        int var131 = var2.method13150((short)19467);
                        var29.method3138(var78, var96, client.field856 * 1612595797, var115, var131, 1590447644);
                     }
                  }
               }

               if (0 != (var33 & 4194304)) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var47 = var2.method13218((byte)-99);

                  for (int var61 = 0; var61 < var47; var61++) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     int var79 = var2.method13095(-346779531);
                     int var97 = var2.method13254(-1808856422);
                     int var116 = var2.method13303((byte)-62);
                     var29.method3148(var79, var97, var116 >> 16, var116 & 65535, false, (byte)24);
                  }
               }

               if ((var33 & 32768) != 0) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  int var48 = 1612595797 * client.field856 + var2.method13258((byte)62);
                  int var62 = 1612595797 * client.field856 + var2.method13104((byte)16);
                  byte var80 = var2.method13229((byte)27);
                  byte var98 = var2.method13097((byte)17);
                  boolean var145;
                  if (var2.method13095(-346779531) != 0) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     var145 = true;
                  } else {
                     var145 = false;
                  }

                  boolean var117 = var145;
                  if (var29.field1475 == null) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var29.field1475 = new classAAV();
                  }

                  var29.field1475.method271(1612595797 * client.field856, var48, var62, var80, var98, var117);
               }

               if ((var33 & 1048576) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var49 = var2.method13218((byte)-86);
                  if (1 == (var49 & 1)) {
                     if (var4 != 1181900450) {
                        client.method2226(var0, var1, var2, var3);
                        return;
                     }

                     var29.method3503(1939407468);
                  } else {
                     int[] var63 = null;
                     if (2 == (var49 & 2)) {
                        int var81 = var2.method13224(-2012626631);
                        var63 = new int[var81];

                        for (int var99 = 0; var99 < var81; var99++) {
                           if (var4 != 1181900450) {
                              client.method2226(var0, var1, var2, var3);
                              return;
                           }

                           int var118 = classXY.method13113(var2, (byte)1);
                           var63[var99] = var118;
                        }
                     }

                     short[] var82 = null;
                     if (4 == (var49 & 4)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var100 = 0;
                        if (var29.field1579.field5313 != null) {
                           var100 = var29.field1579.field5313.length;
                        }

                        var82 = new short[var100];

                        for (int var119 = 0; var119 < var100; var119++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var82[var119] = (short)classXY.method13251(var2, 2128443635);
                        }
                     }

                     short[] var101 = null;
                     if (8 == (var49 & 8)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        int var120 = 0;
                        if (null != var29.field1579.field5315) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var120 = var29.field1579.field5315.length;
                        }

                        var101 = new short[var120];

                        for (int var132 = 0; var132 < var120; var132++) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var101[var132] = (short)classXY.method13251(var2, -329506068);
                        }
                     }

                     boolean var121 = false;
                     if (0 != (var49 & 16)) {
                        if (var4 != 1181900450) {
                           throw new IllegalStateException();
                        }

                        boolean var146;
                        if (var2.method13225(-1728816533) == 1) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           var146 = true;
                        } else {
                           var146 = false;
                        }

                        var121 = var146;
                     }

                     long var133 = (NPC.field1570 += 1703859013) * -1914714227 - 1;
                     var29.method3500(new NpcOverrides(var133, var63, var82, var101, var121), (short)4958);
                  }
               }

               if ((var33 & 256) != 0) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  var29.method3449(var2.method13127(-500143673), (byte)49);
               }

               if (0 != (var33 & 8)) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  NPCComposition var161 = classOH.method8480(classXY.method13251(var2, 2118233529), -1465353705);
                  var29.method3510(var161);
                  var29.field1579 = var161;
                  classGA.method5325(var29, (byte)41);
               }

               if (0 != (var33 & 128)) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  int var50 = var2.method13218((byte)-120);
                  Actor.method3228(var29, var50, var2, (byte)10);
               }

               if (0 != (var33 & 16777216)) {
                  if (var4 != 1181900450) {
                     throw new IllegalStateException();
                  }

                  int var51 = var2.method13218((byte)-94);
                  if (var51 > 0) {
                     if (var4 != 1181900450) {
                        throw new IllegalStateException();
                     }

                     for (int var64 = 0; var64 < var51; var64++) {
                        if (var4 != 1181900450) {
                           client.method2226(var0, var1, var2, var3);
                           return;
                        }

                        int var83 = var2.method13150((short)12224);
                        int var102 = var2.method13150((short)4985);
                        if (var102 != 32767) {
                           if (var4 != 1181900450) {
                              throw new IllegalStateException();
                           }

                           int var122 = var2.method13150((short)14167);
                           int var134 = var2.method13225(-871203925);
                           int var147;
                           if (var102 > 0) {
                              if (var4 != 1181900450) {
                                 client.method2226(var0, var1, var2, var3);
                                 return;
                              }

                              var147 = var2.method13224(-1659888913);
                           } else {
                              var147 = var134;
                           }

                           int var137 = var147;
                           var29.method3144(var83, client.field856 * 1612595797, var102, var122, var134, var137, 1874940363);
                        } else {
                           var29.method3145(var83, -411514021);
                        }
                     }
                  }
               }
            }

            for (int var22 = 0; var22 < 329265561 * client.field967; var22++) {
               if (var4 != 1181900450) {
                  client.method2226(var0, var1, var2, var3);
                  return;
               }

               int var26 = client.field880[var22];
               NPC var30 = (NPC)IndexedObjectSet.method13449(var0.field1696, var26);
               if (var30.field1567) {
                  if (var4 != 1181900450) {
                     client.method2226(var0, var1, var2, var3);
                     return;
                  }

                  var30.method3510((NPCComposition)null);
                  var30.field1579 = null;
                  var30.method12078();
               }
            }

            if (var2.field6953 * 702114061 == 1265354011 * client.field796.field1523) {
               client.method2226(var0, var1, var2, var3);
            } else if (var4 != 1181900450) {
               client.method2226(var0, var1, var2, var3);
            } else {
               throw new RuntimeException(702114061 * var2.field6953 + classDO.field1590 + 1265354011 * client.field796.field1523);
            }
         }
      } catch (RuntimeException var19) {
         throw classEG.method3899(var19, "as.en(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IZZZZZB)Lvp;")
   @ObfuscatedName("ap")
   static classVP method660(int var0, boolean var1, boolean var2, boolean var3, boolean var4, boolean var5, byte var6) {
      try {
         classUS var7 = null;
         if (classME.field4486 != null) {
            var7 = new classUS(var0, classME.field4486, GraphicsObject.field477[var0], 1000000);
         }

         return new classVP(var7, classQX.field5621, classEB.field1724, var0, var1, var2, var3, var4, var5);
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "as.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field262 * 238046463;
   }

   @ObfuscatedSignature(descriptor = "([J[IIII)V")
   @ObfuscatedName("aa")
   public static void method658(long[] var0, int[] var1, int var2, int var3, int var4) {
      try {
         if (var2 < var3) {
            if (var4 == 633113997) {
               return;
            }

            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            long var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            int var9 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var9;
            byte var10000;
            if (var7 == Long.MAX_VALUE) {
               if (var4 == 633113997) {
                  throw new IllegalStateException();
               }

               var10000 = 0;
            } else {
               var10000 = 1;
            }

            byte var10 = var10000;

            for (int var11 = var2; var11 < var3; var11++) {
               if (var0[var11] < var7 + (var11 & var10)) {
                  long var12 = var0[var11];
                  var0[var11] = var0[var6];
                  var0[var6] = var12;
                  int var14 = var1[var11];
                  var1[var11] = var1[var6];
                  var1[var6++] = var14;
               }
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var9;
            method658(var0, var1, var2, var6 - 1, -1792627335);
            method658(var0, var1, var6 + 1, var3, -1447236944);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "as.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;IIB)V")
   @ObfuscatedName("aq")
   public static void method659(classTE var0, int var1, int var2, byte var3) {
      try {
         if (var0 != null) {
            if (var3 != 0) {
               return;
            }

            if (var0.method10797(-2130200299) > 1) {
               classOL.method8544(var0, 1327640383);
               if (classYY.field7109 == var0.field6126) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  classZZ.method14320(var0.method10791(1800778077), var0.method10797(-1838339939), var1, var2);
               } else if (var0.field6126 == classYY.field7113) {
                  if (var3 != 0) {
                     throw new IllegalStateException();
                  }

                  classZZ.method14323(var0.method10793(-1003435742), var0.method10797(-1989139370), var1, var2);
               } else {
                  classZZ.method14330(var0.method10796(-1830948448), var0.method10797(-1416703478), var1, var2);
               }

               var0.field6131 = true;
               return;
            }

            if (var3 != 0) {
               throw new IllegalStateException();
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "as.aq(" + ')');
      }
   }
}
