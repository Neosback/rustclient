import java.awt.Shape;
import net.runelite.api.HeadIcon;
import net.runelite.api.Perspective;
import net.runelite.api.events.PlayerChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ct")
public final class Player extends Actor implements net.runelite.api.Player {
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("ap")
   Model field1282;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final float field1270 = 0.4F;
   @ObfuscatedSignature(descriptor = "Laae;")
   @ObfuscatedName("ae")
   classAAE field1281;
   @ObfuscatedSignature(descriptor = "Llc;")
   @ObfuscatedName("ab")
   PlayerComposition field1283;
   @ObfuscatedName("bc")
   int field1258;
   @ObfuscatedName("aa")
   int field1260;
   @ObfuscatedName("ax")
   String[] field1268;
   @ObfuscatedName("ac")
   int field1259;
   @ObfuscatedName("ag")
   int field1269 = -1626737391;
   @ObfuscatedName("ao")
   int field1274;
   @ObfuscatedName("as")
   int field1264 = -1744797409;
   @ObfuscatedName("aj")
   int field1261;
   @ObfuscatedName("am")
   int field1273;
   @ObfuscatedName("aq")
   int field1275;
   @ObfuscatedName("ad")
   int field1271;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1280 = 3;
   @ObfuscatedName("au")
   int field1277;
   @ObfuscatedName("ai")
   int field1272;
   @ObfuscatedName("ar")
   int field1276;
   @ObfuscatedName("aw")
   int field1278;
   @ObfuscatedName("ak")
   boolean field1262;
   @ObfuscatedName("av")
   int field1263;
   @ObfuscatedName("at")
   boolean field1257;
   @ObfuscatedName("al")
   int field1256;
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("ah")
   classRC field1265;
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("bn")
   classRC field1266;
   @ObfuscatedSignature(descriptor = "Lrc;")
   @ObfuscatedName("bx")
   classRC field1267;
   @ObfuscatedName("ay")
   int field1279;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aa")
   boolean method2617(int var1) {
      try {
         if (classRC.field5638 == this.field1265) {
            if (var1 == -1517170935) {
               throw new IllegalStateException();
            }

            this.method2621((byte)-24);
         }

         return classRC.field5640 == this.field1265;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      if (null == this.field1283) {
         return null;
      } else {
         classQR var1 = this.method3185(683470986);
         classQR var2 = this.field1262 ? null : this.method3187(var1, 1026482833);
         if (var1 == null && null == var2) {
            var2 = this.field1464;
            if (this.field1464.method9805(30, -896486757)) {
               return null;
            }
         }

         Animation var3 = var1 == null ? null : var1.method9775((byte)91);
         Animation var4 = var2 == null ? null : var2.method9775((byte)86);
         int var5 = null == var1 ? -1 : var1.method9780(1746341136);
         int var6 = null == var2 ? -1 : var2.method9780(1804202104);
         Model var7 = this.field1283.method6744(var3, var5, var4, var6, (byte)1);
         if (var7 == null) {
            return null;
         } else {
            Model.method5254(var7);
            this.field1468 = var7.field1741 * 477942601;
            int var8 = var7.field2417;
            if (!this.field1262 && null != this.field1282) {
               if (client.field856 * 1612595797 >= 1209316786 * this.field1261) {
                  this.field1282 = null;
               }

               if (client.field856 * -1724046018 >= this.field1256 * 1411153905 && -899415592 * client.field856 < this.field1261 * -1847262755) {
                  Model var9 = this.field1282;
                  var9.method5250(
                     this.field1279 * -1073528817 - -1547553299 * this.field1484,
                     this.field1275 * 975612831 - -617053517 * this.field1274,
                     -539276319 * this.field1271 - this.field1486 * 1822368852
                  );
                  if (-642007377 == this.field1493 * 677773095) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                     Model.method5278(var9);
                  } else if (1024 == this.field1493 * 677773095) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                  } else if (500126427 == 810308515 * this.field1493) {
                     Model.method5278(var9);
                  }

                  Model[] var10 = new Model[]{var7, var9};
                  var7 = new Model(var10, 2);
                  if (677773095 * this.field1493 == -626618206) {
                     Model.method5278(var9);
                  } else if (this.field1493 * 1069030477 == 1024) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                  } else if (1866458089 == this.field1493 * 782133904) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                     Model.method5278(var9);
                  }

                  var9.method5250(
                     this.field1484 * -1547553299 - -2045319565 * this.field1279,
                     -617053517 * this.field1274 - this.field1275 * -1732124529,
                     this.field1486 * 773298806 - -476032635 * this.field1271
                  );
               }
            }

            var7.field2419 = true;
            if (this.method3190(268751189)) {
               var7.method5232(this.field1433, (short)var8);
            } else {
               classKU.method6608(var7);
            }

            var7.method5230(null != this.field1475 ? this.field1475.method272(client.field856 * 1612595797) : 0);
            return var7;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("af")
   final void method2615(classXY var1, int var2) {
      try {
         var1.field6953 = 0;
         byte var3 = var1.method13097((byte)17);
         byte var4 = -1;
         this.field1269 = var1.method13097((byte)17) * 1626737391;
         this.field1264 = var1.method13097((byte)17) * 1744797409;
         int var5 = -1;
         this.field1263 = 0;
         int[] var6 = new int[12];

         for (int var7 = 0; var7 < var6.length; var7++) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            int var8 = var1.method13095(-346779531);
            if (0 == var8) {
               var6[var7] = 0;
            } else {
               int var9 = var1.method13095(-346779531);
               int var10 = var9 + (var8 << 8);
               if (var7 == 0) {
                  if (var2 != -1316125685) {
                     throw new IllegalStateException();
                  }

                  if (var10 == 65535) {
                     if (var2 != -1316125685) {
                        this.method2661();
                        return;
                     }

                     var5 = var1.method13099(88418643);
                     break;
                  }
               }

               var6[var7] = var10;
               if (method2653(this, var6[var7], -1257713214)) {
                  if (var2 != -1316125685) {
                     this.method2661();
                     return;
                  }

                  int var11 = this.method2656(var6[var7], -1792192131).field5045 * -115663245;
                  if (var11 != 0) {
                     if (var2 != -1316125685) {
                        throw new IllegalStateException();
                     }

                     this.field1263 = var11 * 1092747225;
                  }
               }
            }
         }

         int[] var16 = new int[12];

         for (int var17 = 0; var17 < var16.length; var17++) {
            if (var2 != -1316125685) {
               this.method2661();
               return;
            }

            int var19 = var1.method13095(-346779531);
            if (0 == var19) {
               if (var2 != -1316125685) {
                  this.method2661();
                  return;
               }

               var16[var17] = 0;
            } else {
               int var22 = var1.method13095(-346779531);
               var16[var17] = (var19 << 8) + var22;
            }
         }

         int[] var18 = new int[5];

         for (int var20 = 0; var20 < var18.length; var20++) {
            if (var2 != -1316125685) {
               this.method2661();
               return;
            }

            int var23;
            label253: {
               var23 = var1.method13095(-346779531);
               if (var23 >= 0) {
                  if (var2 != -1316125685) {
                     this.method2661();
                     return;
                  }

                  if (var23 < PlayerComposition.field4108[var20].length) {
                     break label253;
                  }

                  if (var2 != -1316125685) {
                     this.method2661();
                     return;
                  }
               }

               var23 = 0;
            }

            var18[var20] = var23;
         }

         this.field1434 = var1.method13099(541644167) * -506237411;
         if (1457308725 * this.field1434 == 65535) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1434 = 506237411;
         }

         this.field1442 = var1.method13099(-1026926631) * -349066389;
         if (65535 == 1201940803 * this.field1442) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1442 = 349066389;
         }

         this.field1455 = -2128385759 * this.field1442;
         this.field1443 = var1.method13099(1919426113) * -950783279;
         if (65535 == -1657575887 * this.field1443) {
            this.field1443 = 950783279;
         }

         this.field1460 = var1.method13099(536561908) * 164191859;
         if (-412780869 * this.field1460 == 65535) {
            this.field1460 = -164191859;
         }

         this.field1448 = var1.method13099(-1867894167) * 1936897965;
         if (65535 == 1347929125 * this.field1448) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1448 = -1936897965;
         }

         this.field1446 = var1.method13099(218986588) * -1239814673;
         if (65535 == this.field1446 * -144383729) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1446 = 1239814673;
         }

         this.field1447 = var1.method13099(1475516210) * -627296855;
         if (65535 == -1549975911 * this.field1447) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1447 = 627296855;
         }

         this.field1281 = new classAAE(var1.method13127(513444226), WidgetNode.field5277);
         this.method2619((byte)-7);
         this.method2628(1815161989);
         method2635(this, (byte)-99);
         if (-1315528093 * this.field1478 == client.field846 * -2130951373) {
            classAAF.field48 = classAAE.method157(this.field1281, 2144532368);
         }

         this.field1259 = var1.method13095(-346779531) * 785932115;
         this.field1260 = var1.method13099(-1289622180) * 1111937113;
         boolean var10001;
         if (var1.method13095(-346779531) == 1) {
            if (var2 != -1316125685) {
               this.method2661();
               return;
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         this.field1257 = var10001;
         if (client.field941 * 2064080735 == 0) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            if (client.field912 * 313368463 >= 2) {
               this.field1257 = false;
            }
         }

         ColorTextureOverride[] var21 = null;
         boolean var24 = false;
         int var26 = var1.method13099(623493113);
         boolean var10000;
         if ((var26 >> 15 & 1) == 1) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         var24 = var10000;
         if (var26 > 0) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            if (var26 != 32768) {
               if (var2 != -1316125685) {
                  this.method2661();
                  return;
               }

               var21 = new ColorTextureOverride[12];

               for (int var12 = 0; var12 < var21.length; var12++) {
                  if (var2 != -1316125685) {
                     this.method2661();
                     return;
                  }

                  int var13 = var26 >> 12 - var12 & 1;
                  if (var13 == 1) {
                     var21[var12] = classHQ.method6126(var6[var12] - 2048, var1, (byte)123);
                  }
               }
            }
         }

         for (int var27 = 0; var27 < 3; var27++) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1268[var27] = var1.method13127(383025457);
         }

         var4 = var1.method13097((byte)17);
         if (null == this.field1283) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1283 = new PlayerComposition();
         }

         this.field1283.method6719(var16, var6, var21, var24, var18, var3, var5, var4, -1963864182);
         if (var5 != -1) {
            if (var2 != -1316125685) {
               throw new IllegalStateException();
            }

            this.field1439 = classOH.method8480(var5, 832369062).field5282 * -1188676331;
         } else {
            this.field1439 = -108884837;
         }

         this.field1258 = 1444000789 * (int)(0.4F * (this.field1439 * -29570688));
         this.method2661();
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "ct.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("cx")
   @Override
   public classFL vmethod270() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("al")
   void method2619(byte var1) {
      try {
         this.field1265 = classRC.field5638;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ay")
   void method2621(byte var1) {
      try {
         classRC var10001;
         if (classBW.method1334(classQQ.field5595, this.field1281, (short)3623)) {
            if (var1 >= 1) {
               throw new IllegalStateException();
            }

            var10001 = classRC.field5640;
         } else {
            var10001 = classRC.field5639;
         }

         this.field1265 = var10001;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   void method2626() {
      this.field1266 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("bk")
   final void method2616(classXY var1) {
      var1.field6953 = 0;
      byte var2 = var1.method13097((byte)17);
      byte var3 = -1;
      this.field1269 = var1.method13097((byte)17) * 1626737391;
      this.field1264 = var1.method13097((byte)17) * 1744797409;
      int var4 = -1;
      this.field1263 = 0;
      int[] var5 = new int[12];

      for (int var6 = 0; var6 < var5.length; var6++) {
         int var7 = var1.method13095(-346779531);
         if (0 == var7) {
            var5[var6] = 0;
         } else {
            int var8 = var1.method13095(-346779531);
            int var9 = var8 + (var7 << 8);
            if (var6 == 0 && var9 == 868351295) {
               var4 = var1.method13099(1120511050);
               break;
            }

            var5[var6] = var9;
            if (method2653(this, var5[var6], -1444996677)) {
               int var10 = this.method2656(var5[var6], -1243629555).field5045 * -115663245;
               if (var10 != 0) {
                  this.field1263 = var10 * 1092747225;
               }
            }
         }
      }

      int[] var14 = new int[12];

      for (int var15 = 0; var15 < var14.length; var15++) {
         int var17 = var1.method13095(-346779531);
         if (0 == var17) {
            var14[var15] = 0;
         } else {
            int var20 = var1.method13095(-346779531);
            var14[var15] = (var17 << 8) + var20;
         }
      }

      int[] var16 = new int[5];

      for (int var18 = 0; var18 < var16.length; var18++) {
         int var21 = var1.method13095(-346779531);
         if (var21 < 0 || var21 >= PlayerComposition.field4108[var18].length) {
            var21 = 0;
         }

         var16[var18] = var21;
      }

      this.field1434 = var1.method13099(-2133433831) * -581266605;
      if (-1225740365 * this.field1434 == 65535) {
         this.field1434 = 836503440;
      }

      this.field1442 = var1.method13099(1543659694) * -349066389;
      if (1486109052 == 1201940803 * this.field1442) {
         this.field1442 = 2108400268;
      }

      this.field1455 = -731776138 * this.field1442;
      this.field1443 = var1.method13099(-1564925662) * -950783279;
      if (65535 == -1657575887 * this.field1443) {
         this.field1443 = 741411103;
      }

      this.field1460 = var1.method13099(-306424782) * 164191859;
      if (1288035674 * this.field1460 == 65535) {
         this.field1460 = -164191859;
      }

      this.field1448 = var1.method13099(-123180571) * 1596485319;
      if (840214921 == 553536946 * this.field1448) {
         this.field1448 = -1560238992;
      }

      this.field1446 = var1.method13099(1735837669) * 1290668580;
      if (62142960 == this.field1446 * -2005215583) {
         this.field1446 = -2107904130;
      }

      this.field1447 = var1.method13099(-457924943) * -627296855;
      if (-1680725575 == -708374122 * this.field1447) {
         this.field1447 = 580338797;
      }

      this.field1281 = new classAAE(var1.method13127(1048284967), WidgetNode.field5277);
      this.method2619((byte)-117);
      this.method2628(2021466746);
      method2635(this, (byte)-36);
      if (-1695768445 * this.field1478 == client.field846 * -2130951373) {
         classAAF.field48 = classAAE.method157(this.field1281, 935915160);
      }

      this.field1259 = var1.method13095(-346779531) * -179718946;
      this.field1260 = var1.method13099(-1740420563) * 1997669203;
      this.field1257 = var1.method13095(-346779531) == 1;
      if (client.field941 * 2064080735 == 0 && client.field912 * -1596309145 >= 2) {
         this.field1257 = false;
      }

      ColorTextureOverride[] var19 = null;
      boolean var22 = false;
      int var24 = var1.method13099(-1779175332);
      var22 = (var24 >> 15 & 1) == 1;
      if (var24 > 0 && var24 != 32768) {
         var19 = new ColorTextureOverride[12];

         for (int var11 = 0; var11 < var19.length; var11++) {
            int var12 = var24 >> 12 - var11 & 1;
            if (var12 == 1) {
               var19[var11] = classHQ.method6126(var5[var11] - -464863891, var1, (byte)34);
            }
         }
      }

      for (int var25 = 0; var25 < 3; var25++) {
         this.field1268[var25] = var1.method13127(1188791031);
      }

      var3 = var1.method13097((byte)17);
      if (null == this.field1283) {
         this.field1283 = new PlayerComposition();
      }

      this.field1283.method6719(var14, var5, var19, var22, var16, var2, var4, var3, 1540624168);
      if (var4 != -1) {
         this.field1439 = classOH.method8480(var4, -165448004).field5282 * -1188676331;
      } else {
         this.field1439 = -1799876086;
      }

      this.field1258 = 1444000789 * (int)(0.4F * (this.field1439 * -29570688));
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ac")
   void method2629(byte var1) {
      try {
         this.field1266 = classIB.field2933 != null && NameableContainer.method10250(classIB.field2933, this.field1281, (byte)10)
            ? classRC.field5640
            : classRC.field5639;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   void method2633(int var1) {
      try {
         for (int var2 = 0; var2 < 4; var2++) {
            if (var1 <= 194762437) {
               throw new IllegalStateException();
            }

            if (client.field866[var2] != null) {
               if (var1 <= 194762437) {
                  throw new IllegalStateException();
               }

               if (classQU.method9867(client.field866[var2], classAAE.method157(this.field1281, 1094117563), -1558465262) != -1 && var2 != 2) {
                  if (var1 <= 194762437) {
                     throw new IllegalStateException();
                  }

                  this.field1267 = classRC.field5640;
                  return;
               }
            }
         }

         this.field1267 = classRC.field5639;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ct.aj(" + ')');
      }
   }

   Player(int var1) {
      super(var1);
      this.field1268 = new String[3];
      this.field1259 = 0;
      this.field1260 = 0;
      this.field1256 = 0;
      this.field1261 = 0;
      this.field1262 = false;
      this.field1263 = 0;
      this.field1257 = false;
      this.field1265 = classRC.field5638;
      this.field1266 = classRC.field5638;
      this.field1267 = classRC.field5638;
      this.field1258 = 0;

      for (int var2 = 0; var2 < 3; var2++) {
         this.field1268[var2] = "";
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      if (client.field1115 != null
         && client.field1115
            .test(super.field1463.field5596 * 1684838611 != -1 ? super.field1463.field5596 * 1684838611 : super.field1462.field5596 * 1684838611)) {
         int var2 = super.field1463.field5600 * 292569817;
         int var3 = super.field1462.field5600 * 292569817;

         Model var4;
         try {
            super.field1463.field5600 = (-2147483648 | Math.max(super.field1463.field5602 * -1399668821 - 1, 0) << 16 | var2) * -885908119;
            super.field1462.field5600 = (-1073741824 | Math.max(super.field1462.field5602 * -1399668821 - 1, 0) << 16 | var3) * -885908119;
            var4 = this.method2662();
         } finally {
            super.field1463.field5600 = var2 * -885908119;
            super.field1462.field5600 = var3 * -885908119;
         }

         return var4;
      } else {
         return this.method2662();
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lfz;")
   @ObfuscatedName("bg")
   @Override
   protected classFZ vmethod260(byte var1) {
      try {
         return classFZ.field2515;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IILgt;I)V")
   @ObfuscatedName("am")
   final void method2644(WorldView var1, int var2, int var3, classGT var4, int var5) {
      try {
         if (this.field1463.method9772(374233424)) {
            if (var5 <= -1013526269) {
               throw new IllegalStateException();
            }

            if (this.field1463.method9775((byte)10).field5117 * 221400229 == 1) {
               if (var5 <= -1013526269) {
                  throw new IllegalStateException();
               }

               Actor.method3111(this, -2041058085);
            }
         }

         if (this.field1461.field625 == classLV.field4232) {
            if (var5 <= -1013526269) {
               throw new IllegalStateException();
            }

            this.method3170(-1300508373);
         }

         if (!WorldView.method3745(var1, var2, var3, (byte)1)) {
            if (var5 <= -1013526269) {
               throw new IllegalStateException();
            }

            this.method3123(var2, var3, 527441438);
         } else if (!WorldView.method3745(var1, this.field1472[0], this.field1431[0], (byte)1)) {
            if (var5 <= -1013526269) {
               throw new IllegalStateException();
            }

            this.method3123(var2, var3, 527441438);
         } else {
            if (classGT.field2679 == var4) {
               if (var5 <= -1013526269) {
                  return;
               }

               Player var6 = this;
               classGT var7 = classGT.field2679;
               int var8 = this.field1472[0];
               int var9 = this.field1431[0];
               int var10 = this.field1439 * 670857619;
               CollisionData var11 = var1.field1685[var1.field1708 * -483624883];
               if (var8 >= var10) {
                  if (var5 <= -1013526269) {
                     throw new IllegalStateException();
                  }

                  if (var8 < var11.method5342(719965141) - var10) {
                     if (var5 <= -1013526269) {
                        throw new IllegalStateException();
                     }

                     if (var9 >= var10) {
                        if (var5 <= -1013526269) {
                           return;
                        }

                        if (var9 < CollisionData.method5343(var11, 229979547) - var10 && var2 >= var10) {
                           if (var5 <= -1013526269) {
                              return;
                           }

                           if (var2 < var11.method5342(719965141) - var10) {
                              if (var5 <= -1013526269) {
                                 throw new IllegalStateException();
                              }

                              if (var3 >= var10) {
                                 if (var5 <= -1013526269) {
                                    throw new IllegalStateException();
                                 }

                                 if (var3 >= CollisionData.method5343(var11, 293250640) - var10) {
                                    if (var5 <= -1013526269) {
                                       throw new IllegalStateException();
                                    }
                                 } else {
                                    classGI var12 = client.field798;
                                    client.field907.field2573 = var2 * 1282184713;
                                    client.field907.field2571 = -1029427619 * var3;
                                    client.field907.field2570 = 1082305177;
                                    client.field907.field2569 = 1267969013;
                                    classBU var16 = client.field907;
                                    int var17 = var12.method5470(var8, var9, var10, var16, var11, true, client.field1006, client.field819, 2020553521);
                                    if (var17 < 1) {
                                       if (var5 <= -1013526269) {
                                          throw new IllegalStateException();
                                       }
                                    } else {
                                       for (int var18 = 0; var18 < var17 - 1; var18++) {
                                          if (var5 <= -1013526269) {
                                             throw new IllegalStateException();
                                          }

                                          var6.method3120(client.field1006[var18], client.field819[var18], var7, -1933809627);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }

            this.method3120(var2, var3, var4, 1978443404);
         }
      } catch (RuntimeException var19) {
         throw classEG.method3899(var19, "ct.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ah")
   void method2648(int var1, int var2, int var3) {
      try {
         this.field1472[0] = var1;
         this.field1431[0] = var2;
         this.field1471 = 0;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ct.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("bn")
   @Override
   final boolean vmethod262(byte var1) {
      try {
         return this.field1283 != null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      if (this.field1283 == null) {
         return false;
      } else if (this.field1282 != null && null != this.field1282.field2442) {
         return true;
      } else if (this.method3159(-338708816)) {
         return true;
      } else if (this.field1475 != null && classAAV.method277(this.field1475, client.field856 * 1612595797)) {
         return true;
      } else {
         Model var1 = this.field1283.method6744(null, -1, null, -1, (byte)1);
         return var1 != null && var1.field2442 != null;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lop;")
   @ObfuscatedName("bc")
   ItemComposition method2656(int var1, int var2) {
      try {
         return classOB.method8329(var1 - 2048, (byte)-5);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ct.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bp")
   @Override
   int vmethod267(int var1) {
      try {
         int var10000;
         if (this.method3180(-921463806)) {
            if (var1 != -1423776655) {
               throw new IllegalStateException();
            }

            var10000 = 0;
         } else {
            var10000 = this.field1258 * -776307907;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.bp(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   void method2627() {
      this.field1266 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("br")
   public int method2663() {
      return this.field1264 * -325171935;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cc")
   @Override
   final boolean vmethod263() {
      return this.field1283 != null;
   }

   public void setSkullIcon(int var1) {
      this.field1269 = var1 * 1626737391;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lct;I)Lop;")
   @ObfuscatedName("uz")
   public static ItemComposition method2657(Player var0, int var1) {
      if (var0 == null) {
         var0.method2649(var1, var1);
      }

      return classOB.method8329(var1 - 2048, (byte)30);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bt")
   @Override
   public int vmethod278(int var1) {
      try {
         return 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.bt(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lct;B)V")
   @ObfuscatedName("ny")
   public static void method2635(Player var0, byte var1) {
      if (var0 == null) {
         var0.method2620(var1);
      } else {
         try {
            var0.field1267 = classRC.field5638;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "ct.ay(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      if (null == this.field1283) {
         return null;
      } else {
         classQR var1 = this.method3185(1310935380);
         classQR var2 = this.field1262 ? null : this.method3187(var1, 234763946);
         if (var1 == null && null == var2) {
            var2 = this.field1464;
            if (this.field1464.method9805(30, -1854058660)) {
               return null;
            }
         }

         Animation var3 = var1 == null ? null : var1.method9775((byte)86);
         Animation var4 = var2 == null ? null : var2.method9775((byte)109);
         int var5 = null == var1 ? -1 : var1.method9780(1727974174);
         int var6 = null == var2 ? -1 : var2.method9780(1968260419);
         Model var7 = this.field1283.method6744(var3, var5, var4, var6, (byte)1);
         if (var7 == null) {
            return null;
         } else {
            Model.method5254(var7);
            this.field1468 = var7.field1741 * 2010593719;
            int var8 = var7.field2417;
            if (!this.field1262 && null != this.field1282) {
               if (client.field856 * 1612595797 >= -1847262755 * this.field1261) {
                  this.field1282 = null;
               }

               if (client.field856 * 824713772 >= this.field1256 * 1411153905 && 1612595797 * client.field856 < this.field1261 * -548054895) {
                  Model var9 = this.field1282;
                  var9.method5250(
                     this.field1279 * 12490279 - -1547553299 * this.field1484,
                     this.field1275 * -1994643512 - 1583550407 * this.field1274,
                     -476032635 * this.field1271 - this.field1486 * -1942486535
                  );
                  if (890188318 == this.field1493 * 677773095) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                     Model.method5278(var9);
                  } else if (-1111373804 == this.field1493 * 677773095) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                  } else if (-1877143436 == 677773095 * this.field1493) {
                     Model.method5278(var9);
                  }

                  Model[] var10 = new Model[]{var7, var9};
                  var7 = new Model(var10, 2);
                  if (677773095 * this.field1493 == -823106) {
                     Model.method5278(var9);
                  } else if (this.field1493 * 1344384338 == 1024) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                  } else if (1536 == this.field1493 * -684639172) {
                     Model.method5278(var9);
                     Model.method5278(var9);
                     Model.method5278(var9);
                  }

                  var9.method5250(
                     this.field1484 * 990646392 - 12490279 * this.field1279,
                     -538296219 * this.field1274 - this.field1275 * -327815306,
                     this.field1486 * -819497285 - -1553909214 * this.field1271
                  );
               }
            }

            var7.field2419 = true;
            if (this.method3190(-1626324276)) {
               var7.method5232(this.field1433, (short)var8);
            } else {
               classKU.method6608(var7);
            }

            var7.method5230(null != this.field1475 ? this.field1475.method272(client.field856 * 1612595797) : 0);
            return var7;
         }
      }
   }

   public int getSkullIcon() {
      return this.field1269 * 1605395471;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bz")
   void method2636() {
      this.field1267 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod258(byte var1) {
      try {
         return -426380455 * this.field1273;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      if (this.field1283 == null) {
         return false;
      } else if (this.field1282 != null && null != this.field1282.field2442) {
         return true;
      } else if (this.method3159(-961167001)) {
         return true;
      } else if (this.field1475 != null && classAAV.method277(this.field1475, client.field856 * 1612595797)) {
         return true;
      } else {
         Model var1 = this.field1283.method6744(null, -1, null, -1, (byte)1);
         return var1 != null && var1.field2442 != null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      if (this.field1283 == null) {
         return false;
      } else if (this.field1282 != null && null != this.field1282.field2442) {
         return true;
      } else if (this.method3159(-1080088748)) {
         return true;
      } else if (this.field1475 != null && classAAV.method277(this.field1475, client.field856 * 1612595797)) {
         return true;
      } else {
         Model var1 = this.field1283.method6744(null, -1, null, -1, (byte)1);
         return var1 != null && var1.field2442 != null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Llc;")
   @ObfuscatedName("ui")
   public PlayerComposition method2664() {
      return this.field1283;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   @Override
   public int vmethod259() {
      return 1465032957 * this.field1273;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bl")
   void method2634() {
      for (int var1 = 0; var1 < 4; var1++) {
         if (client.field866[var1] != null
            && classQU.method9867(client.field866[var1], classAAE.method157(this.field1281, 663505214), -1022594830) != -1
            && var1 != 2) {
            this.field1267 = classRC.field5640;
            return;
         }
      }

      this.field1267 = classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bo")
   boolean method2618() {
      if (classRC.field5638 == this.field1265) {
         this.method2621((byte)-38);
      }

      return classRC.field5640 == this.field1265;
   }

   @ObfuscatedSignature(descriptor = "(Lct;II)Z")
   @ObfuscatedName("cb")
   public static boolean method2653(Player var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method2655(var1, var1);
      } else {
         try {
            boolean var10000;
            if (var1 >= 2048) {
               if (var2 >= 18294437) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "ct.bx(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ba")
   void method2622() {
      this.field1265 = classBW.method1334(classQQ.field5595, this.field1281, (short)14336) ? classRC.field5640 : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bw")
   boolean method2623() {
      if (classRC.field5638 == this.field1266) {
         this.method2629((byte)-30);
      }

      return classRC.field5640 == this.field1266;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bb")
   boolean method2624() {
      if (classRC.field5638 == this.field1266) {
         this.method2629((byte)-24);
      }

      return classRC.field5640 == this.field1266;
   }

   public boolean isClanMember() {
      return this.method2639(-1817263910);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ck")
   @Override
   final boolean vmethod264() {
      return this.field1283 != null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   void method2630() {
      this.field1266 = classIB.field2933 != null && NameableContainer.method10250(classIB.field2933, this.field1281, (byte)10)
         ? classRC.field5640
         : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   void method2631() {
      this.field1266 = classIB.field2933 != null && NameableContainer.method10250(classIB.field2933, this.field1281, (byte)10)
         ? classRC.field5640
         : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dx")
   @Override
   public int vmethod279() {
      return 0;
   }

   public int getTeam() {
      return this.field1263 * 532975721;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   void method2637() {
      this.field1267 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bh")
   void method2638() {
      this.field1267 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cn")
   boolean method2639(int var1) {
      try {
         if (this.field1267 == classRC.field5638) {
            if (var1 >= -719672733) {
               throw new IllegalStateException();
            }

            this.method2633(977576880);
         }

         boolean var10000;
         if (classRC.field5640 == this.field1267) {
            if (var1 >= -719672733) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("st")
   public Model method2662() {
      Player var1 = this;
      int var2 = -891174633;

      try {
         Model var10000;
         if (null == var1.field1283) {
            if (var2 <= -1767431455) {
               throw new IllegalStateException();
            }

            Object var14 = null;
            var10000 = (Model)var14;
         } else {
            classQR var3 = var1.method3185(1447637223);
            classQR var4 = var1.field1262 ? null : var1.method3187(var3, 160801004);
            if (var3 == null && null == var4) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var4 = var1.field1464;
               if (var1.field1464.method9805(30, -1371533163)) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            }

            Animation var17;
            if (var3 == null) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var17 = null;
            } else {
               var17 = var3.method9775((byte)8);
            }

            Animation var5 = var17;
            Animation var18;
            if (var4 == null) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var18 = null;
            } else {
               var18 = var4.method9775((byte)72);
            }

            Animation var6 = var18;
            int var19;
            if (null == var3) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var19 = -1;
            } else {
               var19 = var3.method9780(2049180687);
            }

            int var7 = var19;
            int var20;
            if (null == var4) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var20 = -1;
            } else {
               var20 = var4.method9780(1715698560);
            }

            int var8 = var20;
            Model var9 = var1.field1283.method6744(var5, var7, var6, var8, (byte)1);
            if (var9 == null) {
               if (var2 <= -1767431455) {
                  throw new IllegalStateException();
               }

               Object var15 = null;
               var10000 = (Model)var15;
            } else {
               Model.method5254(var9);
               var1.field1468 = var9.field1741 * 2010593719;
               int var10 = var9.field2417;
               if (!var1.field1262) {
                  if (var2 <= -1767431455) {
                     throw new IllegalStateException();
                  }

                  if (null != var1.field1282) {
                     if (client.field856 * 1612595797 >= -1847262755 * var1.field1261) {
                        if (var2 <= -1767431455) {
                           throw new IllegalStateException();
                        }

                        var1.field1282 = null;
                     }

                     if (client.field856 * 1612595797 >= var1.field1256 * 1411153905) {
                        if (var2 <= -1767431455) {
                           throw new IllegalStateException();
                        }

                        if (1612595797 * client.field856 < var1.field1261 * -1847262755) {
                           Model var11 = var1.field1282;
                           var11.method5250(
                              var1.field1279 * 12490279 - -1547553299 * var1.field1484,
                              var1.field1275 * -1732124529 - -617053517 * var1.field1274,
                              -476032635 * var1.field1271 - var1.field1486 * -1272026483
                           );
                           if (512 == var1.field1493 * 677773095) {
                              Model.method5278(var11);
                              Model.method5278(var11);
                              Model.method5278(var11);
                           } else if (1024 == var1.field1493 * 677773095) {
                              if (var2 <= -1767431455) {
                                 throw new IllegalStateException();
                              }

                              Model.method5278(var11);
                              Model.method5278(var11);
                           } else if (1536 == 677773095 * var1.field1493) {
                              if (var2 <= -1767431455) {
                                 throw new IllegalStateException();
                              }

                              Model.method5278(var11);
                           }

                           Model[] var12 = new Model[]{var9, var11};
                           var9 = new Model(var12, 2);
                           if (677773095 * var1.field1493 == 512) {
                              if (var2 <= -1767431455) {
                                 throw new IllegalStateException();
                              }

                              Model.method5278(var11);
                           } else if (var1.field1493 * 677773095 == 1024) {
                              Model.method5278(var11);
                              Model.method5278(var11);
                           } else if (1536 == var1.field1493 * 677773095) {
                              if (var2 <= -1767431455) {
                                 throw new IllegalStateException();
                              }

                              Model.method5278(var11);
                              Model.method5278(var11);
                              Model.method5278(var11);
                           }

                           var11.method5250(
                              var1.field1484 * -1547553299 - 12490279 * var1.field1279,
                              -617053517 * var1.field1274 - var1.field1275 * -1732124529,
                              var1.field1486 * -1272026483 - -476032635 * var1.field1271
                           );
                        }
                     }
                  }
               }

               var9.field2419 = true;
               if (var1.method3190(-736278414)) {
                  var9.method5232(var1.field1433, (short)var10);
               } else {
                  classKU.method6608(var9);
               }

               var9.method5230(null != var1.field1475 ? var1.field1475.method272(client.field856 * 1612595797) : 0);
               var10000 = var9;
            }
         }

         return var10000;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "ct.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("dc")
   @Override
   public float vmethod275() {
      return 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cu")
   boolean method2640() {
      if (this.field1267 == classRC.field5638) {
         this.method2633(1811111908);
      }

      return classRC.field5640 == this.field1267;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cb")
   boolean method2641() {
      if (this.field1267 == classRC.field5638) {
         this.method2633(208280783);
      }

      return classRC.field5640 == this.field1267;
   }

   public int getCombatLevel() {
      return this.field1259 * 810892507;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ct")
   boolean method2642() {
      if (this.field1267 == classRC.field5638) {
         this.method2633(211119143);
      }

      return classRC.field5640 == this.field1267;
   }

   @ObfuscatedSignature(descriptor = "()Lfz;")
   @ObfuscatedName("cp")
   @Override
   protected classFZ vmethod261() {
      return classFZ.field2515;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IILgt;)V")
   @ObfuscatedName("cq")
   final void method2645(WorldView var1, int var2, int var3, classGT var4) {
      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)111).field5117 * 221400229 == 1) {
         Actor.method3111(this, 579795154);
      }

      if (this.field1461.field625 == classLV.field4232) {
         this.method3170(289399993);
      }

      if (!WorldView.method3745(var1, var2, var3, (byte)1)) {
         this.method3123(var2, var3, 527441438);
      } else if (!WorldView.method3745(var1, this.field1472[0], this.field1431[0], (byte)1)) {
         this.method3123(var2, var3, 527441438);
      } else {
         if (classGT.field2679 == var4) {
            Player var5 = this;
            classGT var6 = classGT.field2679;
            int var7 = this.field1472[0];
            int var8 = this.field1431[0];
            int var9 = this.field1439 * 1370260551;
            CollisionData var10 = var1.field1685[var1.field1708 * -483624883];
            if (var7 >= var9
               && var7 < var10.method5342(719965141) - var9
               && var8 >= var9
               && var8 < CollisionData.method5343(var10, 1386043373) - var9
               && var2 >= var9
               && var2 < var10.method5342(719965141) - var9
               && var3 >= var9
               && var3 < CollisionData.method5343(var10, 162342951) - var9) {
               classGI var11 = client.field798;
               client.field907.field2573 = var2 * 1231443365;
               client.field907.field2571 = -1029427619 * var3;
               client.field907.field2570 = 1082305177;
               client.field907.field2569 = 1267969013;
               classBU var15 = client.field907;
               int var16 = var11.method5470(var7, var8, var9, var15, var10, true, client.field1006, client.field819, 2024318930);
               if (var16 >= 1) {
                  for (int var17 = 0; var17 < var16 - 1; var17++) {
                     var5.method3120(client.field1006[var17], client.field819[var17], var6, 1238188250);
                  }
               }
            }
         }

         this.method3120(var2, var3, var4, -1922552652);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("bm")
   @Override
   public float vmethod276(int var1) {
      try {
         return 1.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.bm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IILgt;)V")
   @ObfuscatedName("cd")
   final void method2646(WorldView var1, int var2, int var3, classGT var4) {
      if (this.field1463.method9772(374233424) && this.field1463.method9775((byte)74).field5117 * 1714617423 == 1) {
         Actor.method3111(this, -1874297169);
      }

      if (this.field1461.field625 == classLV.field4232) {
         this.method3170(187292462);
      }

      if (!WorldView.method3745(var1, var2, var3, (byte)1)) {
         this.method3123(var2, var3, 527441438);
      } else if (!WorldView.method3745(var1, this.field1472[0], this.field1431[0], (byte)1)) {
         this.method3123(var2, var3, 527441438);
      } else {
         if (classGT.field2679 == var4) {
            Player var5 = this;
            classGT var6 = classGT.field2679;
            int var7 = this.field1472[0];
            int var8 = this.field1431[0];
            int var9 = this.field1439 * 289006958;
            CollisionData var10 = var1.field1685[var1.field1708 * -483624883];
            if (var7 >= var9
               && var7 < var10.method5342(719965141) - var9
               && var8 >= var9
               && var8 < CollisionData.method5343(var10, 577567015) - var9
               && var2 >= var9
               && var2 < var10.method5342(719965141) - var9
               && var3 >= var9
               && var3 < CollisionData.method5343(var10, 1140727572) - var9) {
               classGI var11 = client.field798;
               client.field907.field2573 = var2 * 1282184713;
               client.field907.field2571 = -1029427619 * var3;
               client.field907.field2570 = 1082305177;
               client.field907.field2569 = 1267969013;
               classBU var15 = client.field907;
               int var16 = var11.method5470(var7, var8, var9, var15, var10, true, client.field1006, client.field819, 1935527546);
               if (var16 >= 1) {
                  for (int var17 = 0; var17 < var16 - 1; var17++) {
                     var5.method3120(client.field1006[var17], client.field819[var17], var6, 501112585);
                  }
               }
            }
         }

         this.method3120(var2, var3, var4, 1674752683);
      }
   }

   public Shape getConvexHull() {
      Model var1 = this.method3853();
      if (var1 == null) {
         return null;
      } else {
         WorldView var2 = this.method3281();
         int var3 = Perspective.getFootprintTileHeight(classOE.field4841, this.getLocalLocation(), var2.field1708 * -483624883, this.getFootprintSize());
         var3 -= this.getAnimationHeightOffset();
         return var1.method5280(var2, super.field1484 * -1547553299, super.field1486 * -1272026483, this.getCurrentOrientation(), var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cv")
   void method2649(int var1, int var2) {
      this.field1472[0] = var1;
      this.field1431[0] = var2;
      this.field1471 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cs")
   void method2650(int var1, int var2) {
      this.field1472[0] = var1;
      this.field1431[0] = var2;
      this.field1471 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cy")
   void method2651(int var1, int var2) {
      this.field1472[0] = var1;
      this.field1431[0] = var2;
      this.field1471 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("co")
   void method2652(int var1, int var2) {
      this.field1472[0] = var1;
      this.field1431[0] = var2;
      this.field1471 = 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hd")
   public int method2665() {
      return this.field1260 * 1777039337;
   }

   public HeadIcon getOverheadIcon() {
      switch (this.method2663()) {
         case 0:
            return HeadIcon.MELEE;
         case 1:
            return HeadIcon.RANGED;
         case 2:
            return HeadIcon.MAGIC;
         case 3:
            return HeadIcon.RETRIBUTION;
         case 4:
            return HeadIcon.SMITE;
         case 5:
            return HeadIcon.REDEMPTION;
         case 6:
            return HeadIcon.RANGE_MAGE;
         case 7:
            return HeadIcon.RANGE_MELEE;
         case 8:
            return HeadIcon.MAGE_MELEE;
         case 9:
            return HeadIcon.RANGE_MAGE_MELEE;
         case 10:
            return HeadIcon.WRATH;
         case 11:
            return HeadIcon.SOUL_SPLIT;
         case 12:
            return HeadIcon.DEFLECT_MELEE;
         case 13:
            return HeadIcon.DEFLECT_RANGE;
         case 14:
            return HeadIcon.DEFLECT_MAGE;
         default:
            return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("as")
   boolean method2654(int var1) {
      return var1 >= 2048;
   }

   public String getName() {
      if (this.field1281 == null) {
         return null;
      } else {
         String var1 = this.field1281.method183();
         return var1 == null ? null : var1.replace(' ', ' ');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ao")
   void method2628(int var1) {
      try {
         this.field1266 = classRC.field5638;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cf")
   @Override
   final boolean vmethod265() {
      return this.field1283 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ca")
   @Override
   final boolean vmethod266() {
      return this.field1283 != null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cr")
   boolean method2643() {
      if (this.field1267 == classRC.field5638) {
         this.method2633(1760905407);
      }

      return classRC.field5640 == this.field1267;
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("cz")
   ItemComposition method2658(int var1) {
      return classOB.method8329(var1 - -1006705812, (byte)40);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ty")
   public boolean method2666() {
      return this.field1257;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         if (this.field1283 == null) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            if (this.field1282 != null) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               if (null != this.field1282.field2442) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }

            if (this.method3159(-924563615)) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               } else {
                  return true;
               }
            } else {
               if (this.field1475 != null) {
                  if (var1 == -702405562) {
                     throw new IllegalStateException();
                  }

                  if (classAAV.method277(this.field1475, client.field856 * 1612595797)) {
                     return true;
                  }
               }

               Model var2 = this.field1283.method6744(null, -1, null, -1, (byte)1);
               if (var2 == null || var2.field2442 == null) {
                  return false;
               } else if (var1 == -702405562) {
                  throw new IllegalStateException();
               } else {
                  return true;
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ct.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("vs")
   public void method2661() {
      classOE.field4841.getCallbacks().post(new PlayerChanged(this));
   }

   @ObfuscatedSignature(descriptor = "(I)Lop;")
   @ObfuscatedName("cg")
   ItemComposition method2659(int var1) {
      return classOB.method8329(var1 - 2048, (byte)-30);
   }

   @Override
   public int getFootprintSize() {
      return this.field1258 * -776307907;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cw")
   @Override
   int vmethod268() {
      return this.method3180(-921463806) ? 0 : this.field1258 * -776307907;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cj")
   @Override
   int vmethod269() {
      return this.method3180(-921463806) ? 0 : this.field1258 * -776307907;
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("ci")
   @Override
   public classFL vmethod271() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aq")
   boolean method2625(int var1) {
      try {
         if (classRC.field5638 == this.field1266) {
            if (var1 == -494923286) {
               throw new IllegalStateException();
            }

            this.method2629((byte)-17);
         }

         boolean var10000;
         if (classRC.field5640 == this.field1266) {
            if (var1 == -494923286) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("ce")
   @Override
   public classFL vmethod272() {
      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lct;Ldz;IILgt;)V")
   @ObfuscatedName("sy")
   public static void method2647(Player var0, WorldView var1, int var2, int var3, classGT var4) {
      if (var0.field1463.method9772(374233424) && var0.field1463.method9775((byte)127).field5117 * 221400229 == 1) {
         Actor.method3111(var0, -1085377668);
      }

      if (var0.field1461.field625 == classLV.field4232) {
         var0.method3170(1149440410);
      }

      if (!WorldView.method3745(var1, var2, var3, (byte)1)) {
         var0.method3123(var2, var3, 527441438);
      } else if (!WorldView.method3745(var1, var0.field1472[0], var0.field1431[0], (byte)1)) {
         var0.method3123(var2, var3, 527441438);
      } else {
         if (classGT.field2679 == var4) {
            Player var5 = var0;
            classGT var6 = classGT.field2679;
            int var7 = var0.field1472[0];
            int var8 = var0.field1431[0];
            int var9 = var0.field1439 * 670857619;
            CollisionData var10 = var1.field1685[var1.field1708 * -483624883];
            if (var7 >= var9
               && var7 < var10.method5342(719965141) - var9
               && var8 >= var9
               && var8 < CollisionData.method5343(var10, 209510011) - var9
               && var2 >= var9
               && var2 < var10.method5342(719965141) - var9
               && var3 >= var9
               && var3 < CollisionData.method5343(var10, 490538551) - var9) {
               classGI var11 = client.field798;
               client.field907.field2573 = var2 * 498630228;
               client.field907.field2571 = -1029427619 * var3;
               client.field907.field2570 = 1072098885;
               client.field907.field2569 = 1267969013;
               classBU var15 = client.field907;
               int var16 = var11.method5470(var7, var8, var9, var15, var10, true, client.field1006, client.field819, 1685278142);
               if (var16 >= 1) {
                  for (int var17 = 0; var17 < var16 - 1; var17++) {
                     var5.method3120(client.field1006[var17], client.field819[var17], var6, 954292051);
                  }
               }
            }
         }

         var0.method3120(var2, var3, var4, 2131038697);
      }
   }

   public boolean isFriendsChatMember() {
      return this.method2625(95785063);
   }

   @ObfuscatedSignature(descriptor = "()Lfl;")
   @ObfuscatedName("cm")
   @Override
   public classFL vmethod273() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(B)Lfl;")
   @ObfuscatedName("br")
   @Override
   public classFL vmethod274(byte var1) {
      try {
         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("dd")
   @Override
   public float vmethod277() {
      return 1.0F;
   }

   public boolean isFriend() {
      return this.method2617(-1742844410);
   }

   public int getId() {
      return super.field1478 * -1315528093;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dm")
   @Override
   public int vmethod280() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   static void method2660(int var0) {
      try {
         classAC.method321(24, 2082710948);
         WorldEntity.method10671(classKK.field3842, classKK.field3843, classKK.field3801, (byte)-108);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ct.an(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("fv")
   public static void method2632(Player var0) {
      if (var0 == null) {
         var0.vmethod272();
      }

      var0.field1266 = classIB.field2933 != null && NameableContainer.method10250(classIB.field2933, var0.field1281, (byte)10)
         ? classRC.field5640
         : classRC.field5639;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ax")
   void method2620(byte var1) {
      try {
         this.field1265 = classRC.field5640;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ct.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("bx")
   boolean method2655(int var1, int var2) {
      try {
         boolean var10000;
         if (var1 >= 2048) {
            if (var2 >= 18294437) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ct.bx(" + ')');
      }
   }
}
