import java.util.Arrays;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pw")
public class classPW extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5465 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5466 = 2;
   @ObfuscatedName("ba")
   public final int field5458;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5463 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field5467 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5469 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field5471 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5464 = 8;
   @ObfuscatedName("bj")
   public final int field5451;
   @ObfuscatedName("bp")
   public final int field5446;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field5470 = 16777215;
   @ObfuscatedName("br")
   final int field5448;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field5462 = 9;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("bn")
   public static NodeCache field5445 = new NodeCache(20);
   @ObfuscatedName("bx")
   final int field5449;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ah")
   public static NodeCache field5444 = new NodeCache(64);
   @ObfuscatedName("bc")
   public final int field5452;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5472 = 1;
   @ObfuscatedName("bm")
   final int field5450;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field5468 = 18;
   @ObfuscatedName("bg")
   final int field5454;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("am")
   public static NodeCache field5443 = new NodeCache(64);
   @ObfuscatedName("bk")
   public final int field5447;
   @ObfuscatedName("bo")
   public final int field5456;
   @ObfuscatedName("bu")
   final String field5457;
   @ObfuscatedName("bw")
   public final int field5459;
   @ObfuscatedName("bt")
   final int field5455;
   @ObfuscatedName("bb")
   public final int[] field5453;
   @ObfuscatedName("bi")
   final int field5460;
   @ObfuscatedName("be")
   final int field5461;

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ax")
   public SpritePixels method9393(int var1) {
      try {
         if (1306359125 * this.field5455 < 0) {
            if (var1 != 1009762333) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5444, 1306359125 * this.field5455);
            if (var2 != null) {
               if (var1 != 1009762333) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               var2 = classPN.method9084(classWN.field6756, 1306359125 * this.field5455, 0, -1631448903);
               if (null != var2) {
                  if (var1 != 1009762333) {
                     throw new IllegalStateException();
                  }

                  field5444.method6464(var2, this.field5455 * 1306359125);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pw.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lpw;")
   @ObfuscatedName("ae")
   public final classPW method9378(byte var1) {
      try {
         int var2 = -1;
         if (-1 != this.field5460 * 1137484171) {
            var2 = classEM.method3946(this.field5460 * 1137484171, -749877325);
         } else if (1654141009 * this.field5461 != -1) {
            var2 = classLB.field4088[1654141009 * this.field5461];
         }

         int var3;
         label39: {
            if (var2 >= 0) {
               if (var1 == 2) {
                  throw new IllegalStateException();
               }

               if (var2 < this.field5453.length - 1) {
                  var3 = this.field5453[var2];
                  break label39;
               }

               if (var1 == 2) {
                  throw new IllegalStateException();
               }
            }

            var3 = this.field5453[this.field5453.length - 1];
         }

         if (-1 != var3) {
            if (var1 == 2) {
               throw new IllegalStateException();
            } else {
               return classDJ.method3380(var3, (byte)65);
            }
         } else {
            return null;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pw.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ag")
   public SpritePixels method9386(int var1) {
      try {
         if (this.field5448 * 1638332811 < 0) {
            if (var1 != -528056669) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5444, 1638332811 * this.field5448);
            if (null != var2) {
               if (var1 != -528056669) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               var2 = classPN.method9084(classWN.field6756, 1638332811 * this.field5448, 0, -1887187427);
               if (null != var2) {
                  if (var1 != -528056669) {
                     throw new IllegalStateException();
                  }

                  field5444.method6464(var2, 1638332811 * this.field5448);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pw.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("as")
   public SpritePixels method9389(int var1) {
      try {
         if (this.field5450 * -277141139 < 0) {
            if (var1 == 1343311673) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5444, -277141139 * this.field5450);
            if (null != var2) {
               return var2;
            } else {
               var2 = classPN.method9084(classWN.field6756, this.field5450 * -277141139, 0, -1715209070);
               if (null != var2) {
                  if (var1 == 1343311673) {
                     throw new IllegalStateException();
                  }

                  field5444.method6464(var2, this.field5450 * -277141139);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pw.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("au")
   public String method9381(int var1) {
      String var2 = this.field5457;

      while (true) {
         int var3 = var2.indexOf("%1");
         if (var3 < 0) {
            return var2;
         }

         var2 = var2.substring(0, var3) + classOJ.method8539(var1, false, (byte)-62) + var2.substring(2 + var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzv;")
   @ObfuscatedName("aa")
   public classZV method9399(int var1) {
      try {
         if (-1 == this.field5449 * 1230047715) {
            if (var1 <= 120041229) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            classZV var2 = (classZV)NodeCache.method6460(field5445, 1230047715 * this.field5449);
            if (null != var2) {
               if (var1 <= 120041229) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               IndexDataBase var4 = classWN.field6756;
               IndexDataBase var5 = classEN.field1801;
               int var6 = this.field5449 * 1230047715;
               classZV var3;
               if (!classHK.method6100(var4, var6, 0, (byte)1)) {
                  if (var1 <= 120041229) {
                     throw new IllegalStateException();
                  }

                  var3 = null;
               } else {
                  byte[] var8 = var5.method11942(var6, 0, -1143570570);
                  classZV var7;
                  if (var8 == null) {
                     if (var1 <= 120041229) {
                        throw new IllegalStateException();
                     }

                     var7 = null;
                  } else {
                     classZV var9 = new classZV(
                        var8, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                     );
                     classVZ.method12347((byte)-8);
                     var7 = var9;
                  }

                  var3 = var7;
               }

               if (var3 != null) {
                  field5445.method6464(var3, 1230047715 * this.field5449);
               }

               return var3;
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "pw.aa(" + ')');
      }
   }

   classPW(classXY var1) {
      int var2 = -1;
      int var3 = 16777215;
      int var4 = 70;
      int var5 = -1;
      int var6 = -1;
      int var7 = -1;
      int var8 = -1;
      int var9 = 0;
      int var10 = 0;
      int var11 = -1;
      String var12 = "";
      int var13 = -1;
      int var14 = 0;
      int[] var15 = null;
      int var16 = -1;
      int var17 = -1;
      if (null != var1 && var1.field6952 != null) {
         label101:
         while (true) {
            int var18 = var1.method13095(-346779531);
            switch (var18) {
               case 0:
                  break label101;
               case 1:
                  var2 = classXY.method13169(var1, -324749371);
                  continue;
               case 2:
                  var3 = var1.method13105(-758448158);
                  continue;
               case 3:
                  var5 = classXY.method13169(var1, -324749371);
                  continue;
               case 4:
                  var7 = classXY.method13169(var1, -324749371);
                  continue;
               case 5:
                  var6 = classXY.method13169(var1, -324749371);
                  continue;
               case 6:
                  var8 = classXY.method13169(var1, -324749371);
                  continue;
               case 7:
                  var9 = var1.method13104((byte)16);
                  continue;
               case 8:
                  var12 = var1.method13134(-1402920923);
                  continue;
               case 9:
                  var4 = var1.method13099(-2069316888);
                  continue;
               case 10:
                  var10 = var1.method13104((byte)16);
                  continue;
               case 11:
                  var11 = 0;
                  continue;
               case 12:
                  var13 = var1.method13095(-346779531);
                  continue;
               case 13:
                  var14 = var1.method13104((byte)16);
                  continue;
               case 14:
                  var11 = var1.method13099(-1497535228);
               case 15:
               case 16:
               default:
                  continue;
               case 17:
               case 18:
            }

            var16 = var1.method13099(-1302817445);
            if (65535 == var16) {
               var16 = -1;
            }

            var17 = var1.method13099(567738210);
            if (var17 == 65535) {
               var17 = -1;
            }

            int var19 = -1;
            if (var18 == 18) {
               var19 = var1.method13099(-651351931);
               if (var19 == 65535) {
                  var19 = -1;
               }
            }

            int var20 = var1.method13095(-346779531);
            var15 = new int[2 + var20];

            for (int var21 = 0; var21 <= var20; var21++) {
               var15[var21] = var1.method13099(1875731730);
               if (65535 == var15[var21]) {
                  var15[var21] = -1;
               }
            }

            var15[1 + var20] = var19;
         }
      }

      this.field5449 = var2 * -537230389;
      this.field5452 = -528735583 * var3;
      this.field5446 = var4 * -583748177;
      this.field5448 = var5 * -619977181;
      this.field5450 = 1015695461 * var6;
      this.field5455 = -684269571 * var7;
      this.field5454 = var8 * -2003517837;
      this.field5451 = 2134533691 * var9;
      this.field5447 = var10 * -176011081;
      this.field5456 = var11 * -545870135;
      this.field5457 = var12;
      this.field5458 = -173691509 * var13;
      this.field5459 = -235141837 * var14;
      this.field5453 = var15;
      this.field5460 = -201654749 * var16;
      this.field5461 = var17 * -1550903119;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Lva;)V")
   @ObfuscatedName("ao")
   public static void method9375(IndexDataBase var0, IndexDataBase var1, IndexDataBase var2) {
      classES.field1902 = var0;
      classWN.field6756 = var1;
      classEN.field1801 = var2;
      classVT var10000 = classEB.field1725;
      classEB.field1725.field6574 = (Arrays.hashCode(classOE.field4841.field1116.getClass().getSigners()) >> 2) * 721999872
         + -1694189056
         + (Widget.field4405 - 1) * 1337221120;
      var10000.field6581 = client.method2203();
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ar")
   public SpritePixels method9387() {
      if (this.field5448 * 1638332811 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, 1638332811 * this.field5448);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, 1638332811 * this.field5448, 0, -1711687038);
            if (null != var1) {
               field5444.method6464(var1, 1638332811 * this.field5448);
            }

            return var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpw;")
   @ObfuscatedName("aj")
   public static classPW method9377(int var0) {
      classPW var1 = (classPW)NodeCache.method6460(field5443, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classES.field1902.method11942(-2007018967, var0, -718487723);
         var1 = new classPW(new classXY(var2));
         field5443.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpw;")
   @ObfuscatedName("ay")
   public final classPW method9379() {
      int var1 = -1;
      if (-1 != this.field5460 * 1993214406) {
         var1 = classEM.method3946(this.field5460 * 1765827592, -1264230156);
      } else if (-44493863 * this.field5461 != -1) {
         var1 = classLB.field4088[1654141009 * this.field5461];
      }

      int var2;
      if (var1 >= 0 && var1 < this.field5453.length - 1) {
         var2 = this.field5453[var1];
      } else {
         var2 = this.field5453[this.field5453.length - 1];
      }

      return -1 != var2 ? classDJ.method3380(var2, (byte)27) : null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Lva;)V")
   @ObfuscatedName("al")
   public static void method9376(IndexDataBase var0, IndexDataBase var1, IndexDataBase var2) {
      classES.field1902 = var0;
      classWN.field6756 = var1;
      classEN.field1801 = var2;
      classVT var10000 = classEB.field1725;
      classEB.field1725.field6574 = (Arrays.hashCode(classOE.field4841.field1116.getClass().getSigners()) >> 2) * 721999872
         + -1694189056
         + (Widget.field4405 - 1) * 1337221120;
      var10000.field6581 = client.method2203();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("nf")
   public static void method9402(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2825();
      }

      var0.field1345 = 1565965903 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()Lpw;")
   @ObfuscatedName("un")
   public classPW method9405() {
      return this.method9378((byte)43);
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ap")
   public String method9382(int var1) {
      String var2 = this.field5457;

      while (true) {
         int var3 = var2.indexOf("%1");
         if (var3 < 0) {
            return var2;
         }

         var2 = var2.substring(0, var3) + classOJ.method8539(var1, false, (byte)18) + var2.substring(2 + var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Luq;")
   @ObfuscatedName("az")
   public static classUQ method9374(int var0, int var1, int var2, int var3, int var4) {
      try {
         synchronized (classUQ.field6365) {
            if (-489960421 * classUQ.field6363 == 0) {
               if (var4 != 1444081395) {
                  throw new IllegalStateException();
               } else {
                  return new classUQ(var0, var1, var2, var3);
               }
            } else {
               classUQ.field6363 -= -1849086445;
               classUQ.field6365[-489960421 * classUQ.field6363].method11533(var0, var1, (byte)106);
               classUQ.field6365[-489960421 * classUQ.field6363].method11534(var2, var3, -1349100992);
               return classUQ.field6365[classUQ.field6363 * -489960421];
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "pw.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ac")
   public SpritePixels method9395(int var1) {
      try {
         if (-1889986885 * this.field5454 < 0) {
            if (var1 != 21303142) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5444, -1889986885 * this.field5454);
            if (null != var2) {
               return var2;
            } else {
               var2 = classPN.method9084(classWN.field6756, -1889986885 * this.field5454, 0, -1886850522);
               if (var2 != null) {
                  if (var1 != 21303142) {
                     throw new IllegalStateException();
                  }

                  field5444.method6464(var2, -1889986885 * this.field5454);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pw.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpw;I)Ljava/lang/String;")
   @ObfuscatedName("oh")
   public static String method9383(classPW var0, int var1) {
      String var2 = var0.field5457;

      while (true) {
         int var3 = var2.indexOf("%1");
         if (var3 < 0) {
            return var2;
         }

         var2 = var2.substring(0, var3) + classOJ.method8539(var1, false, (byte)90) + var2.substring(2 + var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lpw;")
   @ObfuscatedName("aq")
   public final classPW method9380() {
      int var1 = -1;
      if (-1 != this.field5460 * 1137484171) {
         var1 = classEM.method3946(this.field5460 * 1465502619, -1743594932);
      } else if (1654141009 * this.field5461 != -1) {
         var1 = classLB.field4088[-1836150061 * this.field5461];
      }

      int var2;
      if (var1 >= 0 && var1 < this.field5453.length - 1) {
         var2 = this.field5453[var1];
      } else {
         var2 = this.field5453[this.field5453.length - 1];
      }

      return -1 != var2 ? classDJ.method3380(var2, (byte)110) : null;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ak")
   public SpritePixels method9390() {
      if (this.field5450 * 1645401236 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, -1195306926 * this.field5450);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, this.field5450 * 270486352, 0, -1805937533);
            if (null != var1) {
               field5444.method6464(var1, this.field5450 * -277141139);
            }

            return var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpw;)Lym;")
   @ObfuscatedName("yr")
   public static SpritePixels method9391(classPW var0) {
      if (var0 == null) {
         return var0.method9390();
      } else if (var0.field5450 * 646017417 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, -277141139 * var0.field5450);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, var0.field5450 * -1823718866, 0, -1671587492);
            if (null != var1) {
               field5444.method6464(var1, var0.field5450 * -277141139);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("av")
   public SpritePixels method9392() {
      if (this.field5450 * -277141139 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, -277141139 * this.field5450);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, this.field5450 * -277141139, 0, -1713759134);
            if (null != var1) {
               field5444.method6464(var1, this.field5450 * -277141139);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("an")
   public SpritePixels method9394() {
      if (1306359125 * this.field5455 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, 1306359125 * this.field5455);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, 1306359125 * this.field5455, 0, -2026770075);
            if (null != var1) {
               field5444.method6464(var1, this.field5455 * 1306359125);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lpw;")
   @ObfuscatedName("jc")
   public static classPW method9404(int var0) {
      classPW var1 = (classPW)field5443.method6479(var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = classES.field1902.loadData(32, var0);
         var1 = new classPW(new classXY(var2));
         classZP.method14133(field5443, var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpw;)Lym;")
   @ObfuscatedName("ys")
   public static SpritePixels method9388(classPW var0) {
      if (var0.field5448 * 488349178 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, 1721302582 * var0.field5448);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, 1638332811 * var0.field5448, 0, -1909793928);
            if (null != var1) {
               field5444.method6464(var1, 1638332811 * var0.field5448);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("am")
   public SpritePixels method9396() {
      if (-1889986885 * this.field5454 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, -1889986885 * this.field5454);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, -1889986885 * this.field5454, 0, -2146532906);
            if (var1 != null) {
               field5444.method6464(var1, -1889986885 * this.field5454);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ah")
   public SpritePixels method9397() {
      if (-1889986885 * this.field5454 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, -1889986885 * this.field5454);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, -1889986885 * this.field5454, 0, -1952339404);
            if (var1 != null) {
               field5444.method6464(var1, -1889986885 * this.field5454);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lzv;")
   @ObfuscatedName("bx")
   public classZV method9400() {
      if (-1 == this.field5449 * 1230047715) {
         return null;
      } else {
         classZV var1 = (classZV)NodeCache.method6460(field5445, 1230047715 * this.field5449);
         if (null != var1) {
            return var1;
         } else {
            IndexDataBase var3 = classWN.field6756;
            IndexDataBase var4 = classEN.field1801;
            int var5 = this.field5449 * 504379920;
            classZV var2;
            if (!classHK.method6100(var3, var5, 0, (byte)-48)) {
               var2 = null;
            } else {
               byte[] var7 = var4.method11942(var5, 0, -790902691);
               classZV var6;
               if (var7 == null) {
                  var6 = null;
               } else {
                  classZV var8 = new classZV(
                     var7, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                  );
                  classVZ.method12347((byte)-25);
                  var6 = var8;
               }

               var2 = var6;
            }

            if (var2 != null) {
               field5445.method6464(var2, 1230047715 * this.field5449);
            }

            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpw;II)Ljava/lang/String;")
   @ObfuscatedName("er")
   public static String method9384(classPW var0, int var1, int var2) {
      if (var0 == null) {
         var0.method9385(var1, var1);
      }

      try {
         String var3 = var0.field5457;

         while (true) {
            int var4 = var3.indexOf("%1");
            if (var4 < 0) {
               if (var2 <= -1496129683) {
                  throw new IllegalStateException();
               } else {
                  return var3;
               }
            }

            var3 = var3.substring(0, var4) + classOJ.method8539(var1, false, (byte)-9) + var3.substring(2 + var4);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "pw.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lzv;")
   @ObfuscatedName("bc")
   public classZV method9401() {
      if (-1 == this.field5449 * 1230047715) {
         return null;
      } else {
         classZV var1 = (classZV)NodeCache.method6460(field5445, 1230047715 * this.field5449);
         if (null != var1) {
            return var1;
         } else {
            IndexDataBase var3 = classWN.field6756;
            IndexDataBase var4 = classEN.field1801;
            int var5 = this.field5449 * 1230047715;
            classZV var2;
            if (!classHK.method6100(var3, var5, 0, (byte)-20)) {
               var2 = null;
            } else {
               byte[] var7 = var4.method11942(var5, 0, -1832406630);
               classZV var6;
               if (var7 == null) {
                  var6 = null;
               } else {
                  classZV var8 = new classZV(
                     var7, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                  );
                  classVZ.method12347((byte)-1);
                  var6 = var8;
               }

               var2 = var6;
            }

            if (var2 != null) {
               field5445.method6464(var2, 1230047715 * this.field5449);
            }

            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gc")
   static final void method9403(int var0) {
      try {
         Iterator var1 = client.field815.iterator();

         while (var1.hasNext()) {
            if (var0 == 1055994673) {
               throw new IllegalStateException();
            }

            WorldView var2 = (WorldView)var1.next();

            for (int var3 = 0; var3 < client.field838.field1417 * -2087374717; var3++) {
               Player var4 = (Player)IndexedObjectSet.method13449(var2.field1692, client.field838.field1421[var3]);
               if (null != var4) {
                  if (var0 == 1055994673) {
                     throw new IllegalStateException();
                  }

                  var4.method2619((byte)-42);
               }
            }
         }

         var1 = classCN.field1172.iterator();

         while (var1.hasNext()) {
            if (var0 == 1055994673) {
               throw new IllegalStateException();
            }

            MessageNode var7 = (MessageNode)var1.next();
            var7.method1464(1287164533);
         }

         if (null != classIB.field2933) {
            if (var0 == 1055994673) {
               return;
            }

            classIB.field2933.method10372(774906614);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "pw.gc(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpw;)Lym;")
   @ObfuscatedName("tk")
   public static SpritePixels method9398(classPW var0) {
      if (-1889986885 * var0.field5454 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5444, -1889986885 * var0.field5454);
         if (null != var1) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, -1889986885 * var0.field5454, 0, -1727754544);
            if (var1 != null) {
               field5444.method6464(var1, -1889986885 * var0.field5454);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("ab")
   public String method9385(int var1, int var2) {
      try {
         String var3 = this.field5457;

         while (true) {
            int var4 = var3.indexOf("%1");
            if (var4 < 0) {
               if (var2 <= -1496129683) {
                  throw new IllegalStateException();
               } else {
                  return var3;
               }
            }

            var3 = var3.substring(0, var4) + classOJ.method8539(var1, false, (byte)-9) + var3.substring(2 + var4);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "pw.ab(" + ')');
      }
   }
}
