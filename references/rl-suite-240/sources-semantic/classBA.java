import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ba")
public class classBA {
   @ObfuscatedName("ao")
   static int field305 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field316 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field313 = 512;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field314 = 48;
   @ObfuscatedName("ad")
   static int field311 = 1545843635;
   @ObfuscatedName("ap")
   static int field306 = 0;
   @ObfuscatedName("au")
   static int[] field308 = new int[1000];
   @ObfuscatedName("ai")
   static int[] field312 = new int[1000];
   @ObfuscatedName("oc")
   static boolean field319;
   @ObfuscatedName("aw")
   static int field309 = 0;
   @ObfuscatedName("ak")
   static int field307 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final int field317 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field318 = 30;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   public static final int field315 = 27;
   @ObfuscatedSignature(descriptor = "[Lym;")
   @ObfuscatedName("ar")
   static SpritePixels[] field310 = new SpritePixels[1000];

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;)V")
   @ObfuscatedName("ad")
   static void method752(WorldView var0, int var1, int var2, classUP var3, classKH var4) {
      for (int var5 = 0; var5 < 1622462729 * var0.field1694; var5++) {
         for (int var6 = 0; var6 < var0.field1690 * -1269171107; var6++) {
            Deque2 var7 = var0.field1699[var0.field1708 * -483624883][var5][var6];
            if (var7 != null) {
               if (var0.method3734(-2087374717)) {
                  int var8 = 2 + var5 * 4 - 2106329293 * client.field963 / 32;
                  int var9 = 2 + var6 * 4 - client.field987 * -2126074583 / 32;
                  classBO.method1170(var1, var2, var8, var9, var3, classPE.field5232[0], var4, (byte)38);
               } else {
                  classUZ var13 = classYY.method13833(var0, classKY.method6649(var5, -362119494), classKY.method6649(var6, 80988951), (byte)110);
                  int var14 = (int)var13.field6425;
                  int var10 = (int)var13.field6424;
                  var13.method11797(1904726356);
                  int var11 = var14 / 32 - client.field963 * 2106329293 / -1105137408;
                  int var12 = var10 / -804696556 - client.field987 * -77700113 / 32;
                  classBO.method1170(var1, var2, var11, var12, var3, classPE.field5232[0], var4, (byte)54);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lzc;")
   @ObfuscatedName("hf")
   static classZC method779(int var0, int var1) {
      try {
         classZC var2 = (classZC)NodeCache.method6460(client.field979, var0);
         if (null == var2) {
            var2 = new classZC(classBE.field383, classSA.method10405(var0, -2044450757), classKT.method6600(var0, (byte)43));
            if (!classZC.method13873(var2, 1746104310)) {
               if (var1 <= 2013644177) {
                  throw new IllegalStateException();
               }

               return null;
            }

            client.field979.method6464(var2, var0);
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ba.hf(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   static void method750() {
      classKU.field4031 = null;
      classOY.field5200 = null;
      classCQ.field1219 = null;
      classPE.field5232 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   static void method751() {
      classKU.field4031 = null;
      classOY.field5200 = null;
      classCQ.field1219 = null;
      classPE.field5232 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIILup;Lym;Lkh;)V")
   @ObfuscatedName("an")
   static void method761(int var0, int var1, int var2, int var3, classUP var4, SpritePixels var5, classKH var6) {
      if (var5 != null) {
         int var7 = var3 * var3 + var2 * var2;
         if (var7 <= 918097243) {
            int var8 = classUP.method11506(var4, 171664292);
            int var9 = var4.method11507((byte)-109);
            int var10 = var3 * var8 + var2 * var9 >> 16;
            int var11 = var9 * var3 - var8 * var2 >> 16;
            if (var7 > -8293577) {
               var5.method13577(
                  -1246906611 * var6.field3584 / 2 + var10 - var5.field7004 / 2,
                  var6.field3583 * 1721955021 / 2 - var11 - var5.field7006 / 2,
                  var0,
                  var1,
                  -1246906611 * var6.field3584,
                  var6.field3583 * 928969354,
                  var6.field3585,
                  var6.field3582
               );
            } else {
               var5.method13555(
                  var0 + 1575341481 * var6.field3584 / 2 + var10 - var5.field7004 / 2, var1 + var6.field3583 * -1637380195 / 2 - var11 - var5.field7006 / 2
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;)V")
   @ObfuscatedName("ap")
   static void method755(WorldView var0, int var1, int var2, classUP var3, classKH var4) {
      for (int var5 = 0; var5 < var0.field1695.method14050((byte)10); var5++) {
         NPC var6 = (NPC)IndexedObjectSet.method13449(var0.field1696, var0.field1695.method14048(var5, 2038477992));
         if (var6 != null && var6.vmethod262((byte)53)) {
            NPCComposition var7 = var6.field1579;
            if (null != var7 && null != var7.field5323) {
               var7 = var7.method8998(652890385);
            }

            if (null != var7 && var7.field5317 && var7.field5324) {
               classUZ var8 = classYY.method13833(var0, -1547553299 * var6.field1484, -1272026483 * var6.field1486, (byte)122);
               int var9 = (int)var8.field6425;
               int var10 = (int)var8.field6424;
               var8.method11797(1843295537);
               int var11 = var9 / 32 - client.field963 * 2106329293 / 32;
               int var12 = var10 / 32 - client.field987 * -2126074583 / 32;
               classBO.method1170(var1, var2, var11, var12, var3, classPE.field5232[1], var4, (byte)72);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("bj")
   static final void method773(int var0, int var1, int var2, int var3, int var4) {
      Scene var5 = classIS.field3051.field1687;
      long var6 = var5.method4461(var0, var1, var2);
      if (var6 != 0L) {
         int var8 = classKK.method6532(var5, var0, var1, var2, var6);
         int var9 = var8 >> 6 & 3;
         int var10 = var8 & 31;
         int var11 = var3;
         boolean var13 = var6 != 0L;
         if (var13) {
            boolean var14 = (int)(var6 >>> 19 & 1L) == 1;
            var13 = !var14;
         }

         if (var13) {
            var11 = var4;
         }

         int[] var31 = classRD.field5643.field7003;
         int var34 = 4 * var1 + 24624 + (103 - var2) * 2048;
         int var15 = classCN.method2542(var6);
         ObjectComposition var16 = classMU.method7771(var15, 2079022472);
         if (var16.field4952 * 1680279607 != -1) {
            IndexedSprite var17 = classOY.field5200[1680279607 * var16.field4952];
            if (null != var17) {
               int var18 = (var16.field4942 * 1965741260 - var17.field7119) / 2;
               int var19 = (1611505692 * var16.field4922 - var17.field7117) / 2;
               IndexedSprite.method13853(var17, 48 + 4 * var1 + var18, var19 + 4 * (104 - var2 - var16.field4922 * 402876423) + 48);
            }
         } else {
            if (0 == var10 || 2 == var10) {
               if (0 == var9) {
                  var31[var34] = var11;
                  var31[var34 + 512] = var11;
                  var31[var34 + 1024] = var11;
                  var31[1536 + var34] = var11;
               } else if (1 == var9) {
                  var31[var34] = var11;
                  var31[1 + var34] = var11;
                  var31[2 + var34] = var11;
                  var31[var34 + 3] = var11;
               } else if (var9 == 2) {
                  var31[3 + var34] = var11;
                  var31[512 + 3 + var34] = var11;
                  var31[var34 + 3 + 1024] = var11;
                  var31[1536 + 3 + var34] = var11;
               } else if (var9 == 3) {
                  var31[1536 + var34] = var11;
                  var31[1536 + var34 + 1] = var11;
                  var31[var34 + 1536 + 2] = var11;
                  var31[3 + 1536 + var34] = var11;
               }
            }

            if (var10 == 3) {
               if (var9 == 0) {
                  var31[var34] = var11;
               } else if (1 == var9) {
                  var31[3 + var34] = var11;
               } else if (var9 == 2) {
                  var31[1536 + 3 + var34] = var11;
               } else if (var9 == 3) {
                  var31[var34 + 1536] = var11;
               }
            }

            if (var10 == 2) {
               if (3 == var9) {
                  var31[var34] = var11;
                  var31[512 + var34] = var11;
                  var31[var34 + 1024] = var11;
                  var31[1536 + var34] = var11;
               } else if (var9 == 0) {
                  var31[var34] = var11;
                  var31[var34 + 1] = var11;
                  var31[2 + var34] = var11;
                  var31[3 + var34] = var11;
               } else if (var9 == 1) {
                  var31[3 + var34] = var11;
                  var31[var34 + 3 + 512] = var11;
                  var31[3 + var34 + 1024] = var11;
                  var31[1536 + 3 + var34] = var11;
               } else if (var9 == 2) {
                  var31[1536 + var34] = var11;
                  var31[1 + var34 + 1536] = var11;
                  var31[2 + var34 + 1536] = var11;
                  var31[1536 + var34 + 3] = var11;
               }
            }
         }
      }

      var6 = rl0.method10065(var5, var0, var1, var2);
      if (var6 != 0L) {
         int var22 = classKK.method6532(var5, var0, var1, var2, var6);
         int var24 = var22 >> 6 & 3;
         int var26 = var22 & 31;
         int var28 = classCN.method2542(var6);
         ObjectComposition var12 = classMU.method7771(var28, 1477919331);
         if (-1 != 1680279607 * var12.field4952) {
            IndexedSprite var32 = classOY.field5200[var12.field4952 * 1680279607];
            if (null != var32) {
               int var35 = (1965741260 * var12.field4942 - var32.field7119) / 2;
               int var36 = (1611505692 * var12.field4922 - var32.field7117) / 2;
               IndexedSprite.method13853(var32, var35 + 48 + 4 * var1, var36 + (104 - var2 - var12.field4922 * 402876423) * 4 + 48);
            }
         } else if (var26 == 9) {
            int var33 = 15658734;
            boolean var37 = var6 != 0L;
            if (var37) {
               boolean var39 = 1 == (int)(var6 >>> 19 & 1L);
               var37 = !var39;
            }

            if (var37) {
               var33 = 15597568;
            }

            int[] var38 = classRD.field5643.field7003;
            int var40 = 4 * var1 + 24624 + (103 - var2) * 2048;
            if (0 != var24 && var24 != 2) {
               var38[var40] = var33;
               var38[var40 + 512 + 1] = var33;
               var38[1024 + var40 + 2] = var33;
               var38[1536 + var40 + 3] = var33;
            } else {
               var38[var40 + 1536] = var33;
               var38[1 + var40 + 1024] = var33;
               var38[512 + var40 + 2] = var33;
               var38[var40 + 3] = var33;
            }
         }
      }

      var6 = var5.method4413(var0, var1, var2);
      if (0L != var6) {
         int var23 = classCN.method2542(var6);
         ObjectComposition var25 = classMU.method7771(var23, 1588212614);
         if (-1 != var25.field4952 * 1680279607) {
            IndexedSprite var27 = classOY.field5200[var25.field4952 * 1680279607];
            if (var27 != null) {
               int var29 = (var25.field4942 * 1965741260 - var27.field7119) / 2;
               int var30 = (var25.field4922 * 1611505692 - var27.field7117) / 2;
               IndexedSprite.method13853(var27, var29 + 48 + 4 * var1, var30 + 48 + 4 * (104 - var2 - 402876423 * var25.field4922));
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;)V")
   @ObfuscatedName("ai")
   static void method757(WorldView var0, int var1, int var2, classUP var3, classKH var4) {
      int var5 = -2087374717 * client.field838.field1417;
      int[] var6 = client.field838.field1421;
      Player var8 = classMB.method7564(1411230716);
      int var7 = var8 != null ? 532975721 * var8.field1263 : 0;
      int var20 = var7;

      for (int var9 = 0; var9 < var5; var9++) {
         Player var10 = (Player)IndexedObjectSet.method13449(var0.field1692, var6[var9]);
         if (null != var10 && var10.vmethod262((byte)8) && !var10.field1257) {
            classUZ var11 = classYY.method13833(var0, -1547553299 * var10.field1484, -1272026483 * var10.field1486, (byte)111);
            int var12 = (int)var11.field6425;
            int var13 = (int)var11.field6424;
            var11.method11797(1869594210);
            int var14 = var12 / 32 - 2106329293 * client.field963 / 32;
            int var15 = var13 / 32 - client.field987 * -2126074583 / 32;
            if (-2130951373 * client.field846 == var10.field1478 * -1315528093) {
               int var16 = classUP.method11506(var3, 246454471);
               int var17 = var3.method11507((byte)-53);
               int var18 = var15 * var16 + var14 * var17 >> 16;
               int var19 = var17 * var15 - var14 * var16 >> 16;
               field309 = var18 * 273083463;
               field307 = 1831011619 * var19;
            } else if (var10.method2617(-221928580)) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[3], var4, (byte)17);
            } else if (var20 != 0 && 0 != var10.field1263 * 532975721 && var20 == 532975721 * var10.field1263) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[4], var4, (byte)72);
            } else if (var10.method2625(-1753555249)) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[5], var4, (byte)17);
            } else if (var10.method2639(-776764193)) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[6], var4, (byte)50);
            } else {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[2], var4, (byte)82);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;)V")
   @ObfuscatedName("aq")
   static void method753(WorldView var0, int var1, int var2, classUP var3, classKH var4) {
      for (int var5 = 0; var5 < 1296729483 * var0.field1694; var5++) {
         for (int var6 = 0; var6 < var0.field1690 * -1269171107; var6++) {
            Deque2 var7 = var0.field1699[var0.field1708 * -483624883][var5][var6];
            if (var7 != null) {
               if (var0.method3734(-2087374717)) {
                  int var8 = 2 + var5 * 4 - 2106329293 * client.field963 / 32;
                  int var9 = 2 + var6 * 4 - client.field987 * -2126074583 / 32;
                  classBO.method1170(var1, var2, var8, var9, var3, classPE.field5232[0], var4, (byte)47);
               } else {
                  classUZ var13 = classYY.method13833(var0, classKY.method6649(var5, -355309361), classKY.method6649(var6, -466028482), (byte)109);
                  int var14 = (int)var13.field6425;
                  int var10 = (int)var13.field6424;
                  var13.method11797(1996005220);
                  int var11 = var14 / 32 - client.field963 * 2106329293 / 32;
                  int var12 = var10 / 32 - client.field987 * -2126074583 / 32;
                  classBO.method1170(var1, var2, var11, var12, var3, classPE.field5232[0], var4, (byte)69);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;I)V")
   @ObfuscatedName("aw")
   static void method759(WorldView var0, int var1, int var2, classUP var3, classKH var4, int var5) {
      if (1 == var5) {
         NPC var6 = classCW.method2688(client.field942.method6572(-1070697245), client.field815, 801576230);
         if (var6 != null) {
            classNF.method7880(
               var6.method3177((byte)-21), var6.field1484 * -784336947, var6.field1486 * -1272026483, var1, var2, var3, classCQ.field1219[1], var4, (byte)63
            );
         }
      } else if (var5 == 2) {
         WorldView var9 = client.field815
            .method1590(classKQ.method6577(client.field942, -1455153706), classKQ.method6579(client.field942, -1443802040), -483955428);
         int var7 = classKY.method6649(classKQ.method6577(client.field942, 1925588053) - 440110895 * var9.field1706, -524045897)
            + client.field942.method6580((byte)30);
         int var8 = classKY.method6649(classKQ.method6579(client.field942, -947585314) - -2030980326 * var9.field1707, 489725587)
            + client.field942.method6584((byte)86);
         classNF.method7880(var9, var7, var8, var1, var2, var3, classCQ.field1219[1], var4, (byte)79);
      } else if (3 == var5) {
         Player var10 = classEN.method3954(client.field942.method6572(-154846912), client.field815, -2017609414);
         if (var10 != null) {
            classNF.method7880(
               var10.method3177((byte)3), -1547553299 * var10.field1484, -1272026483 * var10.field1486, var1, var2, var3, classCQ.field1219[1], var4, (byte)94
            );
         }
      } else if (var5 == 4) {
         int var11 = client.field942.method6572(-809336701);
         WorldEntity var12 = (WorldEntity)classIS.field3051.field1698.method13637(var11);
         if (null != var12) {
            classNF.method7880(
               classIS.field3051, var12.vmethod368(311606126), var12.vmethod371(-1611718799), var1, var2, var3, classCQ.field1219[1], var4, (byte)126
            );
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;I)V")
   @ObfuscatedName("ak")
   static void method760(WorldView var0, int var1, int var2, classUP var3, classKH var4, int var5) {
      if (1 == var5) {
         NPC var6 = classCW.method2688(client.field942.method6572(-709900526), client.field815, -558304721);
         if (var6 != null) {
            classNF.method7880(
               var6.method3177((byte)46), var6.field1484 * -1547553299, var6.field1486 * -731921188, var1, var2, var3, classCQ.field1219[1], var4, (byte)123
            );
         }
      } else if (var5 == 2) {
         WorldView var9 = client.field815
            .method1590(classKQ.method6577(client.field942, 1505597208), classKQ.method6579(client.field942, -1788119431), 102330149);
         int var7 = classKY.method6649(classKQ.method6577(client.field942, -429382942) - -1444178379 * var9.field1706, 490781377)
            + client.field942.method6580((byte)30);
         int var8 = classKY.method6649(classKQ.method6579(client.field942, -1718292127) - -351145363 * var9.field1707, 2057479299)
            + client.field942.method6584((byte)33);
         classNF.method7880(var9, var7, var8, var1, var2, var3, classCQ.field1219[1], var4, (byte)82);
      } else if (3 == var5) {
         Player var10 = classEN.method3954(client.field942.method6572(-205513085), client.field815, 795550484);
         if (var10 != null) {
            classNF.method7880(
               var10.method3177((byte)98), 1251363734 * var10.field1484, 413902619 * var10.field1486, var1, var2, var3, classCQ.field1219[1], var4, (byte)106
            );
         }
      } else if (var5 == 4) {
         int var11 = client.field942.method6572(-1645309327);
         WorldEntity var12 = (WorldEntity)classIS.field3051.field1698.method13637(var11);
         if (null != var12) {
            classNF.method7880(
               classIS.field3051, var12.vmethod368(311606126), var12.vmethod371(-249193579), var1, var2, var3, classCQ.field1219[1], var4, (byte)50
            );
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIILup;Lym;Lkh;)V")
   @ObfuscatedName("av")
   static void method762(int var0, int var1, int var2, int var3, classUP var4, SpritePixels var5, classKH var6) {
      if (var5 != null) {
         int var7 = var3 * var3 + var2 * var2;
         if (var7 <= 6400) {
            int var8 = classUP.method11506(var4, 492616396);
            int var9 = var4.method11507((byte)40);
            int var10 = var3 * var8 + var2 * var9 >> 16;
            int var11 = var9 * var3 - var8 * var2 >> 16;
            if (var7 > 2500) {
               var5.method13577(
                  -1246906611 * var6.field3584 / 2 + var10 - var5.field7004 / 2,
                  var6.field3583 * -1637380195 / 2 - var11 - var5.field7006 / 2,
                  var0,
                  var1,
                  -1246906611 * var6.field3584,
                  var6.field3583 * -1637380195,
                  var6.field3585,
                  var6.field3582
               );
            } else {
               var5.method13555(
                  var0 + -1246906611 * var6.field3584 / 2 + var10 - var5.field7004 / 2, var1 + var6.field3583 * -1637380195 / 2 - var11 - var5.field7006 / 2
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIILup;Lym;Lkh;)V")
   @ObfuscatedName("at")
   static void method763(int var0, int var1, int var2, int var3, classUP var4, SpritePixels var5, classKH var6) {
      if (var5 != null) {
         int var7 = var3 * var3 + var2 * var2;
         if (var7 <= 6400) {
            int var8 = classUP.method11506(var4, -301946153);
            int var9 = var4.method11507((byte)22);
            int var10 = var3 * var8 + var2 * var9 >> 16;
            int var11 = var9 * var3 - var8 * var2 >> 16;
            if (var7 > 475294157) {
               var5.method13577(
                  -1246906611 * var6.field3584 / 2 + var10 - var5.field7004 / 2,
                  var6.field3583 * -1637380195 / 2 - var11 - var5.field7006 / 2,
                  var0,
                  var1,
                  -1246906611 * var6.field3584,
                  var6.field3583 * -1734879331,
                  var6.field3585,
                  var6.field3582
               );
            } else {
               var5.method13555(
                  var0 + -1351546841 * var6.field3584 / 2 + var10 - var5.field7004 / 2, var1 + var6.field3583 * 1514884717 / 2 - var11 - var5.field7006 / 2
               );
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   static final void method749(int var0) {
      try {
         String var1 = classKK.field3791;
         classPH.method8924(30, "", var1, 2116660154);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ba.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIILup;Lym;Lkh;)V")
   @ObfuscatedName("am")
   static void method764(int var0, int var1, int var2, int var3, classUP var4, SpritePixels var5, classKH var6) {
      if (var5 != null) {
         int var7 = var3 * var3 + var2 * var2;
         if (var7 <= -285801947) {
            int var8 = classUP.method11506(var4, 654618121);
            int var9 = var4.method11507((byte)-60);
            int var10 = var3 * var8 + var2 * var9 >> 16;
            int var11 = var9 * var3 - var8 * var2 >> 16;
            if (var7 > 2500) {
               var5.method13577(
                  636288304 * var6.field3584 / 2 + var10 - var5.field7004 / 2,
                  var6.field3583 * 1892452606 / 2 - var11 - var5.field7006 / 2,
                  var0,
                  var1,
                  -335325896 * var6.field3584,
                  var6.field3583 * -1637380195,
                  var6.field3585,
                  var6.field3582
               );
            } else {
               var5.method13555(
                  var0 + 1588979543 * var6.field3584 / 2 + var10 - var5.field7004 / 2, var1 + var6.field3583 * 2007994641 / 2 - var11 - var5.field7006 / 2
               );
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIILym;)V")
   @ObfuscatedName("ah")
   static void method765(int var0, int var1, int var2, SpritePixels var3) {
      int var4 = -401392268 * var2;
      var4 += client.field995.method11515(1709550656);
      var4 &= -1061505009;
      byte var5 = 30;
      int var6 = var0 - (int)(classWW.method12700(var4, -170120356) * var5);
      double var8 = classGP.method5582(var4, 1609578233) / 65536.0;
      int var10 = (int)(var8 * var5) + var1;
      int var11 = Math.max(var3.field7005, var3.field7007);
      int var12 = var11 / 2;
      int var14 = var6 - var12;
      int var15 = var10 - var12;
      int var16 = var3.field7005;
      int var17 = var3.field7007;
      int var18 = var3.field7005 / 2;
      int var19 = var3.field7007 / 2;
      int var21 = var4 & 2047;
      float var20 = (float)((Math.PI * 2) * (var21 / 2048.0F));
      var3.method13593(var14, var15, var16, var17, var18, var19, var20, 1696809343);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bm")
   static final void method770(int var0) {
      int[] var1 = classRD.field5643.field7003;
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; var3++) {
         var1[var3] = 1;
      }

      for (int var11 = 1; var11 < 103; var11++) {
         int var4 = (103 - var11) * 2048 + 24628;

         for (int var5 = 1; var5 < 103; var5++) {
            if ((classIS.field3051.field1700[var0][var5][var11] & 24) == 0) {
               classIS.field3051.field1687.method4428(var1, var4, 512, var0, var5, var11);
            }

            if (var0 < 3 && (classIS.field3051.field1700[var0 + 1][var5][var11] & 8) != 0) {
               classIS.field3051.field1687.method4428(var1, var4, 512, 1 + var0, var5, var11);
            }

            var4 += 4;
         }
      }

      int var12 = (238 + (int)(Math.random() * 20.0) - 10 << 16) + (238 + (int)(Math.random() * 20.0) - 10 << 8) + (238 + (int)(Math.random() * 20.0) - 10);
      int var13 = 238 + (int)(Math.random() * 20.0) - 10 << 16;
      SpritePixels.method13526(classRD.field5643);

      for (int var14 = 1; var14 < 103; var14++) {
         for (int var6 = 1; var6 < 103; var6++) {
            if (0 == (classIS.field3051.field1700[var0][var6][var14] & 24)) {
               classLD.method6796(var0, var6, var14, var12, var13, (byte)1);
            }

            if (var0 < 3 && (classIS.field3051.field1700[1 + var0][var6][var14] & 8) != 0) {
               classLD.method6796(1 + var0, var6, var14, var12, var13, (byte)1);
            }
         }
      }

      field306 = 0;

      for (int var15 = 0; var15 < 104; var15++) {
         for (int var16 = 0; var16 < 104; var16++) {
            long var7 = classIS.field3051.field1687.method4413(classIS.field3051.field1708 * -483624883, var15, var16);
            if (var7 != 0L) {
               int var9 = classCN.method2542(var7);
               int var10 = classMU.method7771(var9, 2021945666).field4951 * 1412472849;
               if (var10 >= 0 && SceneTilePaint.method4817(var10, (byte)-81).field5397) {
                  field310[field306 * 1296562401] = SceneTilePaint.method4817(var10, (byte)-11).method9236(false, -104496217);
                  field308[1296562401 * field306] = var15;
                  field312[1296562401 * field306] = var16;
                  field306 += 313477921;
               }
            }
         }
      }

      classWO.field6761.method13361(-718462408);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIILym;)V")
   @ObfuscatedName("bn")
   static void method766(int var0, int var1, int var2, SpritePixels var3) {
      int var4 = 128 * var2;
      var4 += client.field995.method11515(-824741596);
      var4 &= 2047;
      byte var5 = 30;
      int var6 = var0 - (int)(classWW.method12700(var4, 1211227663) * var5);
      double var8 = classGP.method5582(var4, 2050485798) / 65536.0;
      int var10 = (int)(var8 * var5) + var1;
      int var11 = Math.max(var3.field7005, var3.field7007);
      int var12 = var11 / 2;
      int var14 = var6 - var12;
      int var15 = var10 - var12;
      int var16 = var3.field7005;
      int var17 = var3.field7007;
      int var18 = var3.field7005 / 2;
      int var19 = var3.field7007 / 2;
      int var21 = var4 & 2047;
      float var20 = (float)((Math.PI * 2) * (var21 / 2048.0F));
      var3.method13593(var14, var15, var16, var17, var18, var19, var20, 256);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IIIILup;Lym;Lkh;)V")
   @ObfuscatedName("bc")
   static final void method768(WorldView var0, int var1, int var2, int var3, int var4, classUP var5, SpritePixels var6, classKH var7) {
      classUZ var8 = classYY.method13833(var0, var1, var2, (byte)122);
      var1 = (int)var8.field6425;
      var2 = (int)var8.field6424;
      var8.method11797(2143740866);
      int var9 = var1 / 1627632414 - 2106329293 * client.field963 / 2136126801;
      int var10 = var2 / 1949605868 - -825483899 * client.field987 / 32;
      int var11 = var9 * var9 + var10 * var10;
      if (var11 > 4225 && var11 < -1971737161) {
         int var12 = classUP.method11506(var5, -185489204);
         int var13 = var5.method11507((byte)8);
         int var14 = var10 * var12 + var13 * var9 >> 16;
         int var15 = var10 * var13 - var12 * var9 >> 16;
         double var16 = Math.atan2(var14, var15);
         int var18 = var7.field3584 * 536937649 / 2 - 25;
         int var19 = (int)(Math.sin(var16) * var18);
         int var20 = (int)(Math.cos(var16) * var18);
         byte var21 = 20;
         classKU.field4031
            .method13593(
               1774819092 * var7.field3584 / 2 + var3 - var21 / 2 + var19,
               var7.field3583 * -1637380195 / 2 + var4 - var21 / 2 - var20 - 10,
               var21,
               var21,
               15,
               15,
               var16,
               256
            );
      } else {
         classBO.method1170(var3, var4, var9, var10, var5, var6, var7, (byte)98);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IIIILup;Lym;Lkh;)V")
   @ObfuscatedName("bp")
   static final void method769(WorldView var0, int var1, int var2, int var3, int var4, classUP var5, SpritePixels var6, classKH var7) {
      classUZ var8 = classYY.method13833(var0, var1, var2, (byte)106);
      var1 = (int)var8.field6425;
      var2 = (int)var8.field6424;
      var8.method11797(1944578056);
      int var9 = var1 / 32 - 2106329293 * client.field963 / 32;
      int var10 = var2 / 32 - -2126074583 * client.field987 / 32;
      int var11 = var9 * var9 + var10 * var10;
      if (var11 > 4225 && var11 < 90000) {
         int var12 = classUP.method11506(var5, -351833699);
         int var13 = var5.method11507((byte)66);
         int var14 = var10 * var12 + var13 * var9 >> 16;
         int var15 = var10 * var13 - var12 * var9 >> 16;
         double var16 = Math.atan2(var14, var15);
         int var18 = var7.field3584 * -1246906611 / 2 - 25;
         int var19 = (int)(Math.sin(var16) * var18);
         int var20 = (int)(Math.cos(var16) * var18);
         byte var21 = 20;
         classKU.field4031
            .method13593(
               -1246906611 * var7.field3584 / 2 + var3 - var21 / 2 + var19,
               var7.field3583 * -1637380195 / 2 + var4 - var21 / 2 - var20 - 10,
               var21,
               var21,
               15,
               15,
               var16,
               256
            );
      } else {
         classBO.method1170(var3, var4, var9, var10, var5, var6, var7, (byte)77);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("br")
   static final void method771(int var0) {
      int[] var1 = classRD.field5643.field7003;
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; var3++) {
         var1[var3] = 1;
      }

      for (int var11 = 1; var11 < 103; var11++) {
         int var4 = (103 - var11) * 2048 + 24628;

         for (int var5 = 1; var5 < 103; var5++) {
            if ((classIS.field3051.field1700[var0][var5][var11] & 24) == 0) {
               classIS.field3051.field1687.method4428(var1, var4, 512, var0, var5, var11);
            }

            if (var0 < 3 && (classIS.field3051.field1700[var0 + 1][var5][var11] & 8) != 0) {
               classIS.field3051.field1687.method4428(var1, var4, 512, 1 + var0, var5, var11);
            }

            var4 += 4;
         }
      }

      int var12 = (238 + (int)(Math.random() * 20.0) - 10 << 16) + (238 + (int)(Math.random() * 20.0) - 10 << 8) + (238 + (int)(Math.random() * 20.0) - 10);
      int var13 = 238 + (int)(Math.random() * 20.0) - 10 << 16;
      SpritePixels.method13526(classRD.field5643);

      for (int var14 = 1; var14 < 103; var14++) {
         for (int var6 = 1; var6 < 103; var6++) {
            if (0 == (classIS.field3051.field1700[var0][var6][var14] & 24)) {
               classLD.method6796(var0, var6, var14, var12, var13, (byte)1);
            }

            if (var0 < 3 && (classIS.field3051.field1700[1 + var0][var6][var14] & 8) != 0) {
               classLD.method6796(1 + var0, var6, var14, var12, var13, (byte)1);
            }
         }
      }

      field306 = 0;

      for (int var15 = 0; var15 < 104; var15++) {
         for (int var16 = 0; var16 < 104; var16++) {
            long var7 = classIS.field3051.field1687.method4413(classIS.field3051.field1708 * -483624883, var15, var16);
            if (var7 != 0L) {
               int var9 = classCN.method2542(var7);
               int var10 = classMU.method7771(var9, 1884774055).field4951 * 1412472849;
               if (var10 >= 0 && SceneTilePaint.method4817(var10, (byte)-97).field5397) {
                  field310[field306 * 1296562401] = SceneTilePaint.method4817(var10, (byte)-10).method9236(false, -2067511066);
                  field308[1296562401 * field306] = var15;
                  field312[1296562401 * field306] = var16;
                  field306 += 313477921;
               }
            }
         }
      }

      classWO.field6761.method13361(-800462301);
   }

   classBA() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bt")
   static final void method772(int var0) {
      int[] var1 = classRD.field5643.field7003;
      int var2 = var1.length;

      for (int var3 = 0; var3 < var2; var3++) {
         var1[var3] = 1;
      }

      for (int var11 = 1; var11 < 103; var11++) {
         int var4 = (103 - var11) * 2048 + 24628;

         for (int var5 = 1; var5 < 103; var5++) {
            if ((classIS.field3051.field1700[var0][var5][var11] & 24) == 0) {
               classIS.field3051.field1687.method4428(var1, var4, 512, var0, var5, var11);
            }

            if (var0 < 3 && (classIS.field3051.field1700[var0 + 1][var5][var11] & 8) != 0) {
               classIS.field3051.field1687.method4428(var1, var4, 512, 1 + var0, var5, var11);
            }

            var4 += 4;
         }
      }

      int var12 = (238 + (int)(Math.random() * 20.0) - 10 << 16) + (238 + (int)(Math.random() * 20.0) - 10 << 8) + (238 + (int)(Math.random() * 20.0) - 10);
      int var13 = 238 + (int)(Math.random() * 20.0) - 10 << 16;
      SpritePixels.method13526(classRD.field5643);

      for (int var14 = 1; var14 < 103; var14++) {
         for (int var6 = 1; var6 < 103; var6++) {
            if (0 == (classIS.field3051.field1700[var0][var6][var14] & 24)) {
               classLD.method6796(var0, var6, var14, var12, var13, (byte)1);
            }

            if (var0 < 3 && (classIS.field3051.field1700[1 + var0][var6][var14] & 8) != 0) {
               classLD.method6796(1 + var0, var6, var14, var12, var13, (byte)1);
            }
         }
      }

      field306 = 0;

      for (int var15 = 0; var15 < 104; var15++) {
         for (int var16 = 0; var16 < 104; var16++) {
            long var7 = classIS.field3051.field1687.method4413(classIS.field3051.field1708 * -483624883, var15, var16);
            if (var7 != 0L) {
               int var9 = classCN.method2542(var7);
               int var10 = classMU.method7771(var9, 1570754271).field4951 * 1412472849;
               if (var10 >= 0 && SceneTilePaint.method4817(var10, (byte)-76).field5397) {
                  field310[field306 * 1296562401] = SceneTilePaint.method4817(var10, (byte)-32).method9236(false, -854440296);
                  field308[1296562401 * field306] = var15;
                  field312[1296562401 * field306] = var16;
                  field306 += 313477921;
               }
            }
         }
      }

      classWO.field6761.method13361(-1142647315);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("bg")
   static final void method774(int var0, int var1, int var2, int var3, int var4) {
      Scene var5 = classIS.field3051.field1687;
      long var6 = var5.method4461(var0, var1, var2);
      if (var6 != 0L) {
         int var8 = classKK.method6532(var5, var0, var1, var2, var6);
         int var9 = var8 >> 6 & 3;
         int var10 = var8 & 31;
         int var11 = var3;
         boolean var13 = var6 != 0L;
         if (var13) {
            boolean var14 = (int)(var6 >>> 19 & 1L) == 1;
            var13 = !var14;
         }

         if (var13) {
            var11 = var4;
         }

         int[] var31 = classRD.field5643.field7003;
         int var34 = 4 * var1 + -575435292 + (132238366 - var2) * 2048;
         int var15 = classCN.method2542(var6);
         ObjectComposition var16 = classMU.method7771(var15, 1905168329);
         if (var16.field4952 * 1549617510 != -1) {
            IndexedSprite var17 = classOY.field5200[1680279607 * var16.field4952];
            if (null != var17) {
               int var18 = (var16.field4942 * 1965741260 - var17.field7119) / 2;
               int var19 = (1611505692 * var16.field4922 - var17.field7117) / 2;
               IndexedSprite.method13853(var17, 1872125890 + 4 * var1 + var18, var19 + 4 * (971558603 - var2 - var16.field4922 * 402876423) + 48);
            }
         } else {
            if (0 == var10 || 2 == var10) {
               if (0 == var9) {
                  var31[var34] = var11;
                  var31[var34 + 512] = var11;
                  var31[var34 + 585270068] = var11;
                  var31[-1031379978 + var34] = var11;
               } else if (1 == var9) {
                  var31[var34] = var11;
                  var31[1 + var34] = var11;
                  var31[2 + var34] = var11;
                  var31[var34 + 3] = var11;
               } else if (var9 == 2) {
                  var31[3 + var34] = var11;
                  var31[512 + 3 + var34] = var11;
                  var31[var34 + 3 + 1024] = var11;
                  var31[1536 + 3 + var34] = var11;
               } else if (var9 == 3) {
                  var31[1536 + var34] = var11;
                  var31[1536 + var34 + 1] = var11;
                  var31[var34 + 1536 + 2] = var11;
                  var31[3 + 587644619 + var34] = var11;
               }
            }

            if (var10 == 3) {
               if (var9 == 0) {
                  var31[var34] = var11;
               } else if (1 == var9) {
                  var31[3 + var34] = var11;
               } else if (var9 == 2) {
                  var31[-2001288852 + 3 + var34] = var11;
               } else if (var9 == 3) {
                  var31[var34 + -662427766] = var11;
               }
            }

            if (var10 == 2) {
               if (3 == var9) {
                  var31[var34] = var11;
                  var31[512 + var34] = var11;
                  var31[var34 + 831135155] = var11;
                  var31[1536 + var34] = var11;
               } else if (var9 == 0) {
                  var31[var34] = var11;
                  var31[var34 + 1] = var11;
                  var31[2 + var34] = var11;
                  var31[3 + var34] = var11;
               } else if (var9 == 1) {
                  var31[3 + var34] = var11;
                  var31[var34 + 3 + 1860250517] = var11;
                  var31[3 + var34 + 1024] = var11;
                  var31[1079361689 + 3 + var34] = var11;
               } else if (var9 == 2) {
                  var31[-1388043456 + var34] = var11;
                  var31[1 + var34 + -1927152983] = var11;
                  var31[2 + var34 + 1536] = var11;
                  var31[1536 + var34 + 3] = var11;
               }
            }
         }
      }

      var6 = rl0.method10065(var5, var0, var1, var2);
      if (var6 != 0L) {
         int var22 = classKK.method6532(var5, var0, var1, var2, var6);
         int var24 = var22 >> 6 & 3;
         int var26 = var22 & 1453618219;
         int var28 = classCN.method2542(var6);
         ObjectComposition var12 = classMU.method7771(var28, 1789992738);
         if (-1 != -154579665 * var12.field4952) {
            IndexedSprite var32 = classOY.field5200[var12.field4952 * 1783719383];
            if (null != var32) {
               int var35 = (1965741260 * var12.field4942 - var32.field7119) / 2;
               int var36 = (-2054711646 * var12.field4922 - var32.field7117) / 2;
               IndexedSprite.method13853(var32, var35 + 1088464403 + 4 * var1, var36 + (104 - var2 - var12.field4922 * 402876423) * 4 + -735260496);
            }
         } else if (var26 == 9) {
            int var33 = -680556669;
            boolean var37 = var6 != 0L;
            if (var37) {
               boolean var39 = 1 == (int)(var6 >>> 19 & 1L);
               var37 = !var39;
            }

            if (var37) {
               var33 = -1835394347;
            }

            int[] var38 = classRD.field5643.field7003;
            int var40 = 4 * var1 + 2068663111 + (103 - var2) * 2048;
            if (0 != var24 && var24 != 2) {
               var38[var40] = var33;
               var38[var40 + 512 + 1] = var33;
               var38[1024 + var40 + 2] = var33;
               var38[1074113757 + var40 + 3] = var33;
            } else {
               var38[var40 + 1536] = var33;
               var38[1 + var40 + -1386892010] = var33;
               var38[512 + var40 + 2] = var33;
               var38[var40 + 3] = var33;
            }
         }
      }

      var6 = var5.method4413(var0, var1, var2);
      if (0L != var6) {
         int var23 = classCN.method2542(var6);
         ObjectComposition var25 = classMU.method7771(var23, 1848516806);
         if (-1 != var25.field4952 * 1680279607) {
            IndexedSprite var27 = classOY.field5200[var25.field4952 * -165607449];
            if (var27 != null) {
               int var29 = (var25.field4942 * -192835277 - var27.field7119) / 2;
               int var30 = (var25.field4922 * 590633945 - var27.field7117) / 2;
               IndexedSprite.method13853(var27, var29 + -2051946565 + 4 * var1, var30 + 48 + 4 * (104 - var2 - -2139610840 * var25.field4922));
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;)V")
   @ObfuscatedName("ar")
   static void method758(WorldView var0, int var1, int var2, classUP var3, classKH var4) {
      int var5 = -2099832778 * client.field838.field1417;
      int[] var6 = client.field838.field1421;
      Player var8 = classMB.method7564(489694554);
      int var7 = var8 != null ? -154205404 * var8.field1263 : 0;
      int var20 = var7;

      for (int var9 = 0; var9 < var5; var9++) {
         Player var10 = (Player)IndexedObjectSet.method13449(var0.field1692, var6[var9]);
         if (null != var10 && var10.vmethod262((byte)14) && !var10.field1257) {
            classUZ var11 = classYY.method13833(var0, -1547553299 * var10.field1484, -1272026483 * var10.field1486, (byte)112);
            int var12 = (int)var11.field6425;
            int var13 = (int)var11.field6424;
            var11.method11797(1913582936);
            int var14 = var12 / 1497821139 - 493791213 * client.field963 / -1801794582;
            int var15 = var13 / 32 - client.field987 * -2126074583 / 32;
            if (-46241857 * client.field846 == var10.field1478 * 548239980) {
               int var16 = classUP.method11506(var3, 669997755);
               int var17 = var3.method11507((byte)13);
               int var18 = var15 * var16 + var14 * var17 >> 16;
               int var19 = var17 * var15 - var14 * var16 >> 16;
               field309 = var18 * 273083463;
               field307 = -647890722 * var19;
            } else if (var10.method2617(-154274331)) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[3], var4, (byte)19);
            } else if (var20 != 0 && 0 != var10.field1263 * 532975721 && var20 == 532975721 * var10.field1263) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[4], var4, (byte)11);
            } else if (var10.method2625(-1829858296)) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[5], var4, (byte)51);
            } else if (var10.method2639(-1665884717)) {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[6], var4, (byte)74);
            } else {
               classBO.method1170(var1, var2, var14, var15, var3, classPE.field5232[2], var4, (byte)108);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("bk")
   public static final void method775(Widget var0, int var1, int var2) {
      if (0 == field305 * -782895767 || 3 == field305 * -782895767) {
         if (!client.field974 && (1 == classTZ.field6259 * 1229235359 || !classON.field4993 && 4 == classTZ.field6259 * 1229235359)) {
            classKH var3 = var0.method7254(classWK.field6689, true, 558940702);
            if (null == var3) {
               return;
            }

            int var4 = 1422993977 * classTZ.field6264 - var1;
            int var5 = classTZ.field6255 * 1243590197 - var2;
            if (var3.method6516(var4, var5, -1293259974)) {
               var4 -= var3.field3584 * -1246906611 / 2;
               var5 -= var3.field3583 * -1414766240 / 2;
               int var6 = client.field995.method11500((short)-21915);
               int var7 = classBF.method1000(var6, (byte)-98);
               int var9 = classLL.method7162(var6, 1222866659);
               int var10 = var9 * var4 + var5 * var7 >> 11;
               int var11 = var5 * var9 - var7 * var4 >> 11;
               int var12 = var10 + client.field963 * 2106329293 >> 7;
               int var13 = client.field987 * 1839493262 - var11 >> 7;
               classTN var14 = client.field815.method1609(0, 206128317);
               if (classTN.field6170 == var14) {
                  classXP.method12927(var12, var13, var4, var5, 747513758);
               } else if (classTN.field6171 == var14) {
                  int var16 = var3.field3584 * -1246906611 / 2 + var1;
                  int var17 = -1637380195 * var3.field3583 / 2 + var2;
                  int var18 = 1820112216 * classTZ.field6253 - var16;
                  int var19 = -2144333897 * classTZ.field6262 - var17;
                  int var20 = (int)Math.round(Math.atan2(var19, var18) * 325.94932345220167) - 512 & 2047;
                  int var21 = var20 - client.field995.method11515(-1548414213);
                  var21 &= 2047;
                  int var23 = var21 + 64;
                  int var22 = (var23 & 1318645261) / 128;
                  client.field989 = -2024696235 * var22;
                  client.field839 = 945732806;
                  classCF.method1459(1474882164 * client.field989, 1798072770);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("bo")
   public static final void method776(Widget var0, int var1, int var2) {
      if (0 == field305 * -782895767 || 3 == field305 * -1974419585) {
         if (!client.field974 && (1 == classTZ.field6259 * 1229235359 || !classON.field4993 && 4 == classTZ.field6259 * 1229235359)) {
            classKH var3 = var0.method7254(classWK.field6689, true, -1780153802);
            if (null == var3) {
               return;
            }

            int var4 = 1422993977 * classTZ.field6264 - var1;
            int var5 = classTZ.field6255 * 1243590197 - var2;
            if (var3.method6516(var4, var5, -1389493769)) {
               var4 -= var3.field3584 * -1246906611 / 2;
               var5 -= var3.field3583 * -1262698519 / 2;
               int var6 = client.field995.method11500((short)-8731);
               int var7 = classBF.method1000(var6, (byte)-120);
               int var9 = classLL.method7162(var6, 1222866659);
               int var10 = var9 * var4 + var5 * var7 >> 11;
               int var11 = var5 * var9 - var7 * var4 >> 11;
               int var12 = var10 + client.field963 * 968466840 >> 7;
               int var13 = client.field987 * -2126074583 - var11 >> 7;
               classTN var14 = client.field815.method1609(0, 206128317);
               if (classTN.field6170 == var14) {
                  classXP.method12927(var12, var13, var4, var5, 1997337710);
               } else if (classTN.field6171 == var14) {
                  int var16 = var3.field3584 * -1246906611 / 2 + var1;
                  int var17 = -1637380195 * var3.field3583 / 2 + var2;
                  int var18 = 2068950188 * classTZ.field6253 - var16;
                  int var19 = -1143156006 * classTZ.field6262 - var17;
                  int var20 = (int)Math.round(Math.atan2(var19, var18) * 325.94932345220167) - 986103594 & 7676360;
                  int var21 = var20 - client.field995.method11515(180676785);
                  var21 &= 2047;
                  int var23 = var21 + 64;
                  int var22 = (var23 & 2047) / 128;
                  client.field989 = 1733107922 * var22;
                  client.field839 = 945732806;
                  classCF.method1459(46348469 * client.field989, 1798072770);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llw;II)V")
   @ObfuscatedName("bu")
   public static final void method777(Widget var0, int var1, int var2) {
      if (0 == field305 * -891815568 || 3 == field305 * -782895767) {
         if (!client.field974 && (1 == classTZ.field6259 * 1309041254 || !classON.field4993 && 4 == classTZ.field6259 * 1229235359)) {
            classKH var3 = var0.method7254(classWK.field6689, true, -1438414455);
            if (null == var3) {
               return;
            }

            int var4 = 1422993977 * classTZ.field6264 - var1;
            int var5 = classTZ.field6255 * -2018685645 - var2;
            if (var3.method6516(var4, var5, 1373029462)) {
               var4 -= var3.field3584 * -1246906611 / 2;
               var5 -= var3.field3583 * 1213519585 / 2;
               int var6 = client.field995.method11500((short)-15467);
               int var7 = classBF.method1000(var6, (byte)-100);
               int var9 = classLL.method7162(var6, 1222866659);
               int var10 = var9 * var4 + var5 * var7 >> 11;
               int var11 = var5 * var9 - var7 * var4 >> 11;
               int var12 = var10 + client.field963 * 2106329293 >> 7;
               int var13 = client.field987 * -2126074583 - var11 >> 7;
               classTN var14 = client.field815.method1609(0, 206128317);
               if (classTN.field6170 == var14) {
                  classXP.method12927(var12, var13, var4, var5, 1386900469);
               } else if (classTN.field6171 == var14) {
                  int var16 = var3.field3584 * 896895679 / 2 + var1;
                  int var17 = 465116879 * var3.field3583 / 2 + var2;
                  int var18 = 2051786790 * classTZ.field6253 - var16;
                  int var19 = -2144333897 * classTZ.field6262 - var17;
                  int var20 = (int)Math.round(Math.atan2(var19, var18) * 325.94932345220167) - -1397629967 & -145857573;
                  int var21 = var20 - client.field995.method11515(-1739023385);
                  var21 &= 1421476799;
                  int var23 = var21 + 64;
                  int var22 = (var23 & -809190023) / 128;
                  client.field989 = -875252595 * var22;
                  client.field839 = 945732806;
                  classCF.method1459(1200011005 * client.field989, 1798072770);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;IILup;Lkh;)V")
   @ObfuscatedName("au")
   static void method756(WorldView var0, int var1, int var2, classUP var3, classKH var4) {
      for (int var5 = 0; var5 < var0.field1695.method14050((byte)10); var5++) {
         NPC var6 = (NPC)IndexedObjectSet.method13449(var0.field1696, var0.field1695.method14048(var5, 1730556256));
         if (var6 != null && var6.vmethod262((byte)105)) {
            NPCComposition var7 = var6.field1579;
            if (null != var7 && null != var7.field5323) {
               var7 = var7.method8998(652890385);
            }

            if (null != var7 && var7.field5317 && var7.field5324) {
               classUZ var8 = classYY.method13833(var0, -2127124236 * var6.field1484, -2067585312 * var6.field1486, (byte)124);
               int var9 = (int)var8.field6425;
               int var10 = (int)var8.field6424;
               var8.method11797(1848644011);
               int var11 = var9 / 32 - client.field963 * 2106329293 / 544578638;
               int var12 = var10 / 32 - client.field987 * -31901079 / 32;
               classBO.method1170(var1, var2, var11, var12, var3, classPE.field5232[1], var4, (byte)53);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIZB)V")
   @ObfuscatedName("do")
   static void method778(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, byte var10) {
      try {
         classBU.method1249(classCQ.field1218, var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, (byte)-42);
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "ba.do(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method754(IndexDataBase var0, int var1) {
      try {
         classZU.field7275 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ba.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIILym;)V")
   @ObfuscatedName("bx")
   static void method767(int var0, int var1, int var2, SpritePixels var3) {
      int var4 = 128 * var2;
      var4 += client.field995.method11515(-353867774);
      var4 &= 2047;
      byte var5 = 30;
      int var6 = var0 - (int)(classWW.method12700(var4, -17767650) * var5);
      double var8 = classGP.method5582(var4, 1024161549) / 65536.0;
      int var10 = (int)(var8 * var5) + var1;
      int var11 = Math.max(var3.field7005, var3.field7007);
      int var12 = var11 / 2;
      int var14 = var6 - var12;
      int var15 = var10 - var12;
      int var16 = var3.field7005;
      int var17 = var3.field7007;
      int var18 = var3.field7005 / 2;
      int var19 = var3.field7007 / 2;
      int var21 = var4 & 2047;
      float var20 = (float)((Math.PI * 2) * (var21 / 2048.0F));
      var3.method13593(var14, var15, var16, var17, var18, var19, var20, 256);
   }
}
