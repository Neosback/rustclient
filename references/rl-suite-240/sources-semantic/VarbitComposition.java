import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pt")
public class VarbitComposition extends classVJ implements net.runelite.api.VarbitComposition {
   @ObfuscatedName("ax")
   public final int field5419;
   @ObfuscatedName("af")
   static final int[] field5415 = new int[32];
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   static IndexDataBase field5421;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5420 = 1;
   @ObfuscatedName("ag")
   public final int field5418;
   @ObfuscatedName("as")
   public final int field5417;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ab")
   public static NodeCache field5416 = new NodeCache(64);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method9246(IndexDataBase var0) {
      field5421 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpt;")
   @ObfuscatedName("ac")
   public static VarbitComposition method9249(int var0) {
      VarbitComposition var1 = (VarbitComposition)NodeCache.method6460(field5416, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5421.method11942(14, var0, -1917771461);
         var1 = new VarbitComposition(new classXY(var2));
         field5416.method6464(var1, var0);
         return var1;
      }
   }

   static {
      byte var0 = 2;

      for (int var1 = 0; var1 < 32; var1++) {
         field5415[var1] = var0 - 1;
         var0 += var0;
      }
   }

   public int getMostSignificantBit() {
      return this.field5419 * 2095283109;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   int method9252(int var1) {
      int var2 = field5415[2095283109 * this.field5419 - -526564793 * this.field5417];
      return var1 >> -526564793 * this.field5417 & var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method9247(IndexDataBase var0) {
      field5421 = var0;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("bj")
   static final void method9259(String var0, byte var1) {
      try {
         if (var0.equalsIgnoreCase("toggleroof")) {
            ClientPreferences var10000 = classAB.field122;
            boolean var10001;
            if (!classAB.field122.method2714(-1315528093)) {
               if (var1 == 8) {
                  return;
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            ClientPreferences.method2720(var10000, var10001, 571683163);
            if (classAB.field122.method2714(-1315528093)) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               classPH.method8924(99, "", "Roofs are now all hidden", 1608904738);
            } else {
               classPH.method8924(99, "", "Roofs will only be removed selectively", 1571821419);
            }
         }

         if (var0.startsWith("zbuf")) {
            if (var1 == 8) {
               throw new IllegalStateException();
            }

            boolean var14;
            if (classJF.method6402(var0.substring(5).trim(), 156456283) == 1) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               var14 = true;
            } else {
               var14 = false;
            }

            boolean var2 = var14;
            classOE.field4841.method11152(var2, 1150763619);
            classFH.method4704(var2);
         }

         if (var0.equalsIgnoreCase("z")) {
            if (var1 == 8) {
               return;
            }

            boolean var15;
            if (!client.field900) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               var15 = true;
            } else {
               var15 = false;
            }

            client.field900 = var15;
         }

         if (var0.equalsIgnoreCase("displayfps")) {
            classAB.field122.method2734(-1640349003);
         }

         if (var0.equalsIgnoreCase("renderself")) {
            if (var1 == 8) {
               throw new IllegalStateException();
            }

            boolean var16;
            if (!client.field852) {
               if (var1 == 8) {
                  return;
               }

               var16 = true;
            } else {
               var16 = false;
            }

            client.field852 = var16;
         }

         if (var0.equalsIgnoreCase("mouseovertext")) {
            if (var1 == 8) {
               throw new IllegalStateException();
            }

            boolean var17;
            if (!client.field859) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               var17 = true;
            } else {
               var17 = false;
            }

            client.field859 = var17;
         }

         if (var0.startsWith("setdrawdistance")) {
            if (var1 == 8) {
               return;
            }

            String[] var8 = var0.split(" ");

            try {
               int var3 = Integer.parseInt(var8[1]);
               classAB.field122.method2812(var3, 1311981730);
            } catch (NumberFormatException var6) {
               classPH.method8924(
                  99,
                  "",
                  String.format(
                     "Error setting draw distance. setdrawdistance should be in the format \"::setdrawdistance X\" where X is a valid number. Value provided: %s",
                     var8[1]
                  ),
                  1820934249
               );
            }
         }

         if (var0.startsWith("settilerendermode")) {
            if (var1 == 8) {
               return;
            }

            String[] var9 = var0.split(" ");

            try {
               int var13 = Integer.parseInt(var9[1]);
               classIS.field3051.field1687.method4284(classEX.method4135()[var13]);
            } catch (NumberFormatException var5) {
               classPH.method8924(
                  99,
                  "",
                  String.format(
                     "Error setting tile render mode. settilerendermode should be in the format \"::settilerendermode X\" where X is a valid number from 0-1. 0=camera 1=target Value provided: %s",
                     var9[1]
                  ),
                  1170668280
               );
            }
         }

         if (var0.equalsIgnoreCase("getdrawdistance")) {
            classPH.method8924(99, "", String.format("%d", classAB.field122.method2816((byte)0)), 1807902546);
         }

         if (313368463 * client.field912 >= 2) {
            if (var1 == 8) {
               throw new IllegalStateException();
            }

            if (var0.equalsIgnoreCase("errortest")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               throw new RuntimeException();
            }

            if (var0.equalsIgnoreCase("showcoord")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               client.field1124.field6699 = !client.field1124.field6699;
            }

            if (var0.equalsIgnoreCase("fpson")) {
               if (var1 == 8) {
                  return;
               }

               ClientPreferences.method2740(classAB.field122, true, (byte)-100);
            }

            if (var0.equalsIgnoreCase("fpsoff")) {
               if (var1 == 8) {
                  return;
               }

               ClientPreferences.method2740(classAB.field122, false, (byte)-96);
            }

            if (var0.equalsIgnoreCase("gc")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               System.gc();
            }

            if (var0.equalsIgnoreCase("clientdrop")) {
               classCN.method2546((byte)-51);
            }

            if (var0.equalsIgnoreCase("clientreload")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               classAC.method319((byte)11);
            } else if (var0.equalsIgnoreCase("sfx8bit")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               ClientPreferences.method2828(classAB.field122, true, -22926792);
            } else if (var0.equalsIgnoreCase("sfx16bit")) {
               if (var1 == 8) {
                  return;
               }

               ClientPreferences.method2828(classAB.field122, false, -1649203021);
            } else if (var0.startsWith("playsong ")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               String var10 = var0.substring("playsong ".length());

               try {
                  if (classMH.method7616((byte)11) == 0) {
                     if (var1 == 8) {
                        throw new IllegalStateException();
                     }

                     classBK.method1122(0, 0, (byte)107);
                  } else {
                     classPD.method8880(var10, classMH.method7616((byte)11), (byte)32);
                  }
               } catch (Exception var4) {
                  classPH.method8924(99, "", "Can't play the midi named " + var10, 1500884789);
               }
            } else if (var0.equalsIgnoreCase("stopsong")) {
               if (var1 == 8) {
                  return;
               }

               classBK.method1122(0, 0, (byte)41);
            }

            if (var0.equalsIgnoreCase("freecam")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               classUK.method11395(1, -867453667);
            }

            if (var0.startsWith("unlockcam")) {
               if (var1 == 8) {
                  throw new IllegalStateException();
               }

               String[] var11 = var0.split(" ");
               client.field840 = Boolean.parseBoolean(var11[1]);
            }
         }

         classJL var12 = classEF.method3863(classJS.CLIENT_CHEAT, client.field796.field1533, (byte)-10);
         if (289984869 * classAAU.field102 >= 240) {
            classXY.method13029(var12.field3341, var0.length() + 2, (byte)-104);
         } else {
            classXY.method13029(var12.field3341, var0.length() + 1, (byte)-34);
         }

         var12.field3341.method13054(var0, (short)20904);
         if (289984869 * classAAU.field102 >= 240) {
            if (var1 == 8) {
               throw new IllegalStateException();
            }

            classXY.method13029(var12.field3341, 0, (byte)-59);
         }

         client.field796.method3374(var12, -1301097035);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "pt.bj(" + ')');
      }
   }

   public int getLeastSignificantBit() {
      return this.field5417 * -526564793;
   }

   VarbitComposition(classXY var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;
      if (var1 != null && null != var1.field6952) {
         label30:
         while (true) {
            int var5 = var1.method13095(-346779531);
            switch (var5) {
               case 0:
                  break label30;
               case 1:
                  var2 = var1.method13099(188196754);
                  var3 = var1.method13095(-346779531);
                  var4 = var1.method13095(-346779531);
            }
         }
      }

      this.field5418 = 2007085699 * var2;
      this.field5417 = 2081933175 * var3;
      this.field5419 = var4 * 1097004077;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aa")
   int method9253(int var1) {
      int var2 = field5415[1870062131 * this.field5419 - -526564793 * this.field5417];
      return var1 >> 811454747 * this.field5417 & var2;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   int method9254(int var1) {
      int var2 = field5415[2095283109 * this.field5419 - -1292603107 * this.field5417];
      return var1 >> 1187990771 * this.field5417 & var2;
   }

   @ObfuscatedSignature(descriptor = "(Lpt;IS)I")
   @ObfuscatedName("yx")
   public static int method9255(VarbitComposition var0, int var1, short var2) {
      if (var0 == null) {
         return var0.method9257(var1, var2);
      } else {
         try {
            int var3 = field5415[2095283109 * var0.field5419 - -526564793 * var0.field5417];
            return var1 >> -526564793 * var0.field5417 & var3;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "pt.ae(" + 41);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("as")
   public static void method9248(IndexDataBase var0) {
      field5421 = var0;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   int method9256(int var1) {
      int var2 = field5415[2095283109 * this.field5419 - -28672466 * this.field5417];
      return var1 >> -526564793 * this.field5417 & var2;
   }

   @ObfuscatedSignature(descriptor = "(I[[[IIIIB)I")
   @ObfuscatedName("ae")
   static int method9251(int var0, int[][][] var1, int var2, int var3, int var4, byte var5) {
      try {
         return var1[var2][SceneTilePaint.field2278[var0] + var3][SceneTilePaint.field2279[var0] + var4];
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "pt.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;III)V")
   @ObfuscatedName("av")
   public static void method9258(classTE var0, int var1, int var2, int var3) {
      try {
         classOL.method8544(var0, 1327640383);
         if (0 != var1) {
            if (var2 >= 0) {
               if (var3 != 1487907647) {
                  throw new IllegalStateException();
               }

               if (var2 < -1943548931 * var0.field6129 && var1 >= 0) {
                  if (var1 + var2 <= -1943548931 * var0.field6129) {
                     if (var2 < -1943548931 * var0.field6129 - var1) {
                        classMX.method7786(var0, var1 + var2, var0, var2, var0.field6129 * -1943548931 - (var1 + var2), -2127307838);
                     }

                     var0.method10828(-1943548931 * var0.field6129 - var1, (byte)2);
                     var0.field6131 = true;
                     return;
                  }

                  if (var3 != 1487907647) {
                     throw new IllegalStateException();
                  }
               }
            }

            throw new RuntimeException();
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pt.av(" + ')');
      }
   }

   public int getIndex() {
      return this.field5418 * -1681019861;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpt;")
   @ObfuscatedName("ax")
   public static VarbitComposition method9250(int var0) {
      VarbitComposition var1 = (VarbitComposition)NodeCache.method6460(field5416, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5421.method11942(14, var0, -1934803759);
         var1 = new VarbitComposition(new classXY(var2));
         field5416.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZI)V")
   @ObfuscatedName("gj")
   static final void method9260(int var0, int var1, boolean var2, int var3) {
      try {
         if (null == client.field867[var0]) {
            if (var3 < 1501863785) {
               ;
            }
         } else {
            if (var1 >= 0) {
               if (var3 >= 1501863785) {
                  throw new IllegalStateException();
               }

               if (var1 < ClanChannel.method6222(client.field867[var0], (byte)-66)) {
                  ClanChannelMember var4 = (ClanChannelMember)client.field867[var0].field2953.get(var1);
                  classJL var5 = classEF.method3863(classJS.AFFINEDCLANSETTINGS_SETMUTED_FROMCHANNEL, client.field796.field1533, (byte)60);
                  classXY.method13029(var5.field3341, 4 + classAU.method709(classAAE.method157(var4.field2844, 1081337898), (byte)102), (byte)-60);
                  classXY.method13029(var5.field3341, var0, (byte)-78);
                  var5.field3341.method13031(var1, -1542382992);
                  classXY.method13049(var5.field3341, var2, -1737488759);
                  var5.field3341.method13054(classAAE.method157(var4.field2844, 752125537), (short)29816);
                  client.field796.method3374(var5, -1301097035);
                  return;
               }

               if (var3 >= 1501863785) {
                  throw new IllegalStateException();
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "pt.gj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IS)I")
   @ObfuscatedName("ae")
   int method9257(int var1, short var2) {
      try {
         int var3 = field5415[2095283109 * this.field5417 - -526564793 * this.field5417];
         return var1 >> -526564793 * this.field5418 & var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "pt.ae(" + 41);
      }
   }
}
