import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("oi")
public class WorldEntityConfig extends classVJ implements net.runelite.api.WorldEntityConfig {
   @ObfuscatedSignature(descriptor = "[Lvh;")
   @ObfuscatedName("bt")
   final classVH[] field4872;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4890 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4889 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4888 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4897 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field4894 = 25;
   @ObfuscatedSignature(descriptor = "Ljb;")
   @ObfuscatedName("bo")
   final classJB field4873;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field4893 = 25;
   @ObfuscatedName("am")
   public final String field4879;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("an")
   static NodeCache field4870 = new NodeCache(64);
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field4892 = 20;
   @ObfuscatedName("ah")
   final int field4880;
   @ObfuscatedName("bn")
   public final String[] field4881;
   @ObfuscatedName("bx")
   public final boolean field4882;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field4895 = 14;
   @ObfuscatedName("bp")
   final int field4877;
   @ObfuscatedName("br")
   final int field4878;
   @ObfuscatedSignature(descriptor = "Lvh;")
   @ObfuscatedName("bm")
   classVH field4874;
   @ObfuscatedName("ba")
   final int field4887;
   @ObfuscatedName("bg")
   final int field4876;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field4896 = 17;
   @ObfuscatedSignature(descriptor = "Lkw;")
   @ObfuscatedName("bk")
   final classKW field4884;
   @ObfuscatedName("bj")
   final int field4885;
   @ObfuscatedName("bu")
   final int field4886;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field4875 = 16;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("bw")
   public static NodeCache field4871 = new NodeCache(64);
   @ObfuscatedName("bc")
   final int field4883;
   @ToRemove(unused = "true")
   @ObfuscatedName("ak")
   static final int field4891 = 27;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   public int method8503() {
      return this.field4883 * 594099331;
   }

   public int getBoundsY() {
      return this.field4874.field6643 * -1680480153;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   public int method8500(int var1) {
      try {
         return this.field4885 * 1455014693;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lkw;")
   @ObfuscatedName("ba")
   public classKW method8519() {
      return this.field4884;
   }

   @ObfuscatedSignature(descriptor = "(I)Lkw;")
   @ObfuscatedName("ac")
   public classKW method8520(int var1) {
      try {
         return this.field4884;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("as")
   public int method8512(byte var1) {
      try {
         return 540130837 * this.field4878;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.as(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loi;")
   @ObfuscatedName("aj")
   public static WorldEntityConfig method8489(int var0) {
      WorldEntityConfig var1 = (WorldEntityConfig)NodeCache.method6460(field4870, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = classCB.field649.method11942(-132306035, var0, -693118684);
         var1 = new WorldEntityConfig(new classXY(var2), var0);
         field4870.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljb;")
   @ObfuscatedName("aa")
   public classJB method8523(int var1) {
      try {
         return this.field4873;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ao")
   public SpritePixels method8525(int var1) {
      try {
         if (this.field4886 * -486845133 < 0) {
            return null;
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field4871, -486845133 * this.field4886);
            if (var2 != null) {
               return var2;
            } else {
               var2 = classPN.method9084(classJI.field3165, this.field4886 * -486845133, 0, -2018802584);
               if (var2 != null) {
                  if (var1 == -1892459964) {
                     throw new IllegalStateException();
                  }

                  field4871.method6464(var2, this.field4886 * -486845133);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "oi.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   public int method8530(int var1) {
      try {
         return 1745230265 * this.field4887;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.al(" + 41);
      }
   }

   public int getCategory() {
      return this.field4880 * 1743605379;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loi;")
   @ObfuscatedName("ay")
   public static WorldEntityConfig method8490(int var0) {
      WorldEntityConfig var1 = (WorldEntityConfig)NodeCache.method6460(field4870, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = classCB.field649.method11942(72, var0, -785881483);
         var1 = new WorldEntityConfig(new classXY(var2), var0);
         field4870.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loi;")
   @ObfuscatedName("aq")
   public static WorldEntityConfig method8491(int var0) {
      WorldEntityConfig var1 = (WorldEntityConfig)NodeCache.method6460(field4870, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = classCB.field649.method11942(72, var0, -1556852003);
         var1 = new WorldEntityConfig(new classXY(var2), var0);
         field4870.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loi;")
   @ObfuscatedName("ad")
   public static WorldEntityConfig method8492(int var0) {
      WorldEntityConfig var1 = (WorldEntityConfig)NodeCache.method6460(field4870, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = classCB.field649.method11942(72, var0, -1399830928);
         var1 = new WorldEntityConfig(new classXY(var2), var0);
         field4870.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loi;")
   @ObfuscatedName("ap")
   public static WorldEntityConfig method8493(int var0) {
      WorldEntityConfig var1 = (WorldEntityConfig)NodeCache.method6460(field4870, var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = classCB.field649.method11942(72, var0, -1903885803);
         var1 = new WorldEntityConfig(new classXY(var2), var0);
         field4870.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   public int method8494() {
      return this.field4876 * 2104740550;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   public int method8495() {
      return this.field4876 * -324844483;
   }

   public int getId() {
      return this.field4876 * -324844483;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   public int method8496() {
      return this.field4876 * 1944094787;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   public int method8497() {
      return this.field4876 * -324844483;
   }

   @ObfuscatedSignature(descriptor = "(I)Lvh;")
   @ObfuscatedName("ax")
   public classVH method8515(int var1) {
      try {
         return this.field4874;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   public int method8501() {
      return this.field4885 * 1455014693;
   }

   @ObfuscatedSignature(descriptor = "()Lkw;")
   @ObfuscatedName("bk")
   public classKW method8521() {
      return this.field4884;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public int method8504() {
      return this.field4883 * 594099331;
   }

   WorldEntityConfig(classXY var1, int var2) {
      this.field4876 = var2 * -972786923;
      String var3 = classKK.field3613;
      int var4 = -1;
      String[] var5 = new String[5];
      boolean var6 = false;
      int var7 = 0;
      int var8 = 0;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      int var12 = 0;
      int var13 = -1;
      int var14 = 0;
      classKW var15 = classKW.field4040;
      classJB var16 = classJB.field3114;
      int var17 = -1;
      int var18 = 39188;
      if (var1 != null && var1.field6952 != null) {
         label64:
         while (true) {
            int var19 = var1.method13095(-346779531);
            switch (var19) {
               case 0:
                  break label64;
               case 1:
               case 3:
               case 10:
               case 11:
               case 13:
               case 21:
               case 22:
               default:
                  continue;
               case 2:
                  var14 = var1.method13095(-346779531);
                  continue;
               case 4:
                  var7 = var1.method13104((byte)16);
                  continue;
               case 5:
                  var8 = var1.method13104((byte)16);
                  continue;
               case 6:
                  var9 = var1.method13104((byte)16);
                  continue;
               case 7:
                  var10 = var1.method13104((byte)16);
                  continue;
               case 8:
                  var11 = var1.method13099(569407538);
                  continue;
               case 9:
                  var12 = var1.method13099(-1997974894);
                  continue;
               case 12:
                  var3 = var1.method13127(418132268);
                  continue;
               case 14:
                  var6 = true;
                  continue;
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
                  int var21 = var19 - 15;
                  var5[var21] = var1.method13127(-947086534);
                  if (var5[var21].equalsIgnoreCase("hidden")) {
                     var5[var21] = null;
                  }

                  var6 = true;
                  continue;
               case 20:
                  var4 = var1.method13099(-942119645);
                  continue;
               case 23:
                  var15 = (classKW)classOF.method8436(classMA.method7471((byte)-103), var1.method13095(-346779531), 346173919);
                  continue;
               case 24:
                  classJB[] var20 = new classJB[]{classJB.field3112, classJB.field3115, classJB.field3113};
                  var16 = (classJB)classOF.method8436(var20, var1.method13095(-346779531), 1234798768);
                  continue;
               case 25:
                  var13 = var1.method13099(1383209310);
                  continue;
               case 26:
                  var17 = classXY.method13169(var1, -324749371);
                  continue;
               case 27:
            }

            var18 = var1.method13099(1675846563);
         }
      }

      this.field4879 = var3;
      this.field4880 = var4 * 1644425259;
      this.field4881 = var5;
      this.field4882 = var6;
      this.field4883 = -650776021 * var7;
      this.field4877 = -1487457123 * var8;
      this.field4878 = var13 * 777068861;
      this.field4885 = var14 * 1484824749;
      this.field4884 = var15;
      this.field4873 = var16;
      this.field4886 = var17 * -117395973;
      this.field4887 = var18 * -1085530999;
      this.field4874 = new classVH(var11, var12, var9, var10);
      this.field4872 = new classVH[4];
      short var22 = 256;
      this.field4872[0] = new classVH(var22 + var11, var22 + var12, var9, var10);
      var22 = 362;
      this.field4872[2] = new classVH(var22 + var11, var12 + var22, var9, var10);
      var22 = 334;
      this.field4872[1] = new classVH(var22 + var11, var22 + var12, var9, var10);
      this.field4872[3] = this.field4872[1];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   public int method8505() {
      return this.field4883 * 594099331;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bs")
   public int method8531() {
      return -1779143755 * this.field4887;
   }

   @ObfuscatedSignature(descriptor = "(Loi;B)I")
   @ObfuscatedName("pu")
   public static int method8498(WorldEntityConfig var0, byte var1) {
      if (var0 == null) {
         return var0.method8499(var1);
      } else {
         try {
            return var0.field4876 * -324844483;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "oi.af(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bx")
   public int method8508() {
      return 1896935861 * this.field4877;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bc")
   public int method8509() {
      return 1896935861 * this.field4877;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bp")
   public int method8510() {
      return -1653926378 * this.field4877;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;Ljava/lang/String;I)V")
   @ObfuscatedName("um")
   public static void method8488(classQI var0, String var1, int var2) {
      if (var0 == null) {
         var0.method9598(var1, var2);
      } else {
         var0.field5543.put(var1, var2);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   public int method8502() {
      return this.field4885 * 1455014693;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("cl")
   public SpritePixels method8537() {
      return this.method8525(-2019079217);
   }

   public int getBoundsWidth() {
      return this.field4874.field6646 * -105665105;
   }

   public int getBoundsX() {
      return this.field4874.field6644 * -1311898939;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bm")
   public int method8513() {
      return 540130837 * this.field4878;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Loi;)Lvh;")
   @ObfuscatedName("xt")
   public static classVH method8516(WorldEntityConfig var0) {
      return var0.field4874;
   }

   @ObfuscatedSignature(descriptor = "()Lvh;")
   @ObfuscatedName("bt")
   public classVH method8517() {
      return this.field4874;
   }

   @ObfuscatedSignature(descriptor = "()Lvh;")
   @ObfuscatedName("bg")
   public classVH method8518() {
      return this.field4874;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("ba")
   static int method8536(int var0, Script var1, boolean var2, int var3) {
      try {
         if (3800 == var0) {
            if (null != classBM.field513) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
               classNJ.field4676 = classBM.field513;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            }

            return 1;
         } else if (3801 == var0) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               int var20 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (client.field866[var20] != null) {
                  if (var3 == -1863749917) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                  classNJ.field4676 = client.field866[var20];
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               }

               return 1;
            }
         } else if (var0 == 3802) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classNJ.field4676.field3014;
               return 1;
            }
         } else if (var0 == 3803) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               int[] var26 = classBB.field323;
               int var28 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var30;
               if (classNJ.field4676.field3032) {
                  if (var3 == -1863749917) {
                     throw new IllegalStateException();
                  }

                  var30 = 1;
               } else {
                  var30 = 0;
               }

               var26[var28] = var30;
               return 1;
            }
         } else if (var0 == 3804) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3021;
               return 1;
            }
         } else if (3805 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3023;
            return 1;
         } else if (var0 == 3806) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3022;
               return 1;
            }
         } else if (var0 == 3807) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3017;
               return 1;
            }
         } else if (3809 == var0) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3026 * -1967686263;
               return 1;
            }
         } else if (3810 == var0) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               int var19 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classNJ.field4676.field3041[var19];
               return 1;
            }
         } else if (var0 == 3811) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               int var18 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3035[var18];
               return 1;
            }
         } else if (var0 == 3812) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3025 * -845393113;
               return 1;
            }
         } else if (var0 == 3813) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               int var17 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classNJ.field4676.field3042[var17];
               return 1;
            }
         } else if (3814 == var0) {
            DynamicObject.field1676 -= 360123687;
            int var16 = classBB.field323[-324749371 * DynamicObject.field1676];
            int var22 = classBB.field323[1 + DynamicObject.field1676 * -324749371];
            int var23 = classBB.field323[DynamicObject.field1676 * -324749371 + 2];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.method6286(var16, var22, var23, -841278672);
            return 1;
         } else if (var0 == 3815) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3012 * -577292071;
               return 1;
            }
         } else if (var0 == 3816) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1725108575 * classNJ.field4676.field3015;
               return 1;
            }
         } else if (var0 == 3817) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classQU.method9867(
                  classNJ.field4676, (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635], -2067157559
               );
               return 1;
            }
         } else if (3818 == var0) {
            classBB.field323[DynamicObject.field1676 * -324749371 - 1] = classNJ.field4676.method6291((byte)-64)[classBB.field323[DynamicObject.field1676
                  * -324749371
               - 1]];
            return 1;
         } else if (var0 == 3819) {
            if (var3 == -1863749917) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var15 = classBB.field323[-324749371 * DynamicObject.field1676];
               int var21 = classBB.field323[-324749371 * DynamicObject.field1676 + 1];
               classVX.method12334(var21, var15, (byte)85);
               return 1;
            }
         } else if (var0 == 3820) {
            int var14 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classNJ.field4676.field3037[var14];
            return 1;
         } else {
            if (3821 == var0) {
               DynamicObject.field1676 -= 360123687;
               int var4 = classBB.field323[DynamicObject.field1676 * -324749371];
               boolean var10000;
               if (classBB.field323[1 + DynamicObject.field1676 * -324749371] == 1) {
                  if (var3 == -1863749917) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var5 = var10000;
               int var6 = classBB.field323[DynamicObject.field1676 * -324749371 + 2];
               VarbitComposition.method9260(var6, var4, var5, 45209452);
            }

            if (3822 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  int var13 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  int[] var25 = classBB.field323;
                  int var27 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
                  byte var29;
                  if (classNJ.field4676.field3043[var13]) {
                     if (var3 == -1863749917) {
                        throw new IllegalStateException();
                     }

                     var29 = 1;
                  } else {
                     var29 = 0;
                  }

                  var25[var27] = var29;
                  return 1;
               }
            } else if (var0 == 3850) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  if (classKA.field3511 != null) {
                     if (var3 == -1863749917) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                     EntityOps.field5366 = classKA.field3511;
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  }

                  return 1;
               }
            } else if (var0 == 3851) {
               int var12 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               if (null != client.field867[var12]) {
                  if (var3 == -1863749917) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1;
                  EntityOps.field5366 = client.field867[var12];
                  classHU.field2889 = 1507670665 * var12;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               }

               return 1;
            } else if (var0 == 3852) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = EntityOps.field5366.field2946;
                  return 1;
               }
            } else if (3853 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = EntityOps.field5366.field2948;
                  return 1;
               }
            } else if (3854 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = EntityOps.field5366.field2947;
                  return 1;
               }
            } else if (3855 == var0) {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ClanChannel.method6222(EntityOps.field5366, (byte)-26);
               return 1;
            } else if (var0 == 3856) {
               int var11 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = classAAE.method157(
                  ((ClanChannelMember)EntityOps.field5366.field2953.get(var11)).field2844, 1702373393
               );
               return 1;
            } else if (3857 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  int var10 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ((ClanChannelMember)EntityOps.field5366.field2953.get(var10)).field2839;
                  return 1;
               }
            } else if (3858 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  int var9 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ((ClanChannelMember)EntityOps.field5366.field2953.get(var9)).field2840
                     * -906550845;
                  return 1;
               }
            } else if (3859 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  int var8 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                  MessageNode.method1493(-1270719559 * classHU.field2889, var8, 973334607);
                  return 1;
               }
            } else if (var0 == 3860) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = ClanChannel.method6226(
                     EntityOps.field5366, (String)classBB.field328[(classBB.field338 -= 1222866659) * 1375698635], (byte)33
                  );
                  return 1;
               }
            } else if (3861 == var0) {
               classBB.field323[DynamicObject.field1676 * -324749371 - 1] = ClanChannel.method6213(EntityOps.field5366, -1957709273)[classBB.field323[-324749371
                     * DynamicObject.field1676
                  - 1]];
               return 1;
            } else if (3890 == var0) {
               if (var3 == -1863749917) {
                  throw new IllegalStateException();
               } else {
                  int[] var24 = classBB.field323;
                  int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
                  byte var10002;
                  if (null != classOH.field4869) {
                     if (var3 == -1863749917) {
                        throw new IllegalStateException();
                     }

                     var10002 = 1;
                  } else {
                     var10002 = 0;
                  }

                  var24[var10001] = var10002;
                  return 1;
               }
            } else {
               return 2;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "oi.ba(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ag")
   public int method8511(byte var1) {
      try {
         return 1896935861 * this.field4877;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("by")
   public SpritePixels method8526() {
      if (this.field4886 * -486845133 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field4871, -486845133 * this.field4886);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classJI.field3165, this.field4886 * -486845133, 0, -1854532348);
            if (var1 != null) {
               field4871.method6464(var1, this.field4886 * -486845133);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("br")
   public int method8514() {
      return 804505943 * this.field4878;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method8506() {
      return this.field4883 * -714504734;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("bb")
   public SpritePixels method8527() {
      if (this.field4886 * 1536601507 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field4871, -1546897097 * this.field4886);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classJI.field3165, this.field4886 * 155563273, 0, -1885655451);
            if (var1 != null) {
               field4871.method6464(var1, this.field4886 * -895453213);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("bi")
   public SpritePixels method8528() {
      if (this.field4886 * -486845133 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field4871, -486845133 * this.field4886);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classJI.field3165, this.field4886 * -486845133, 0, -1609789149);
            if (var1 != null) {
               field4871.method6464(var1, this.field4886 * -486845133);
            }

            return var1;
         }
      }
   }

   public int getBoundsHeight() {
      return this.field4874.field6647 * 1760347583;
   }

   @ObfuscatedSignature(descriptor = "()Lkw;")
   @ObfuscatedName("bo")
   public classKW method8522() {
      return this.field4884;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method8507(int var1) {
      try {
         return this.field4883 * 594099331;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bq")
   public int method8532() {
      return 1745230265 * this.field4887;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bf")
   public int method8533() {
      return 1745230265 * this.field4887;
   }

   @ObfuscatedSignature(descriptor = "()Ljb;")
   @ObfuscatedName("bw")
   public classJB method8524() {
      return this.field4873;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bd")
   public int method8534() {
      return 2092474365 * this.field4887;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   public int method8535() {
      return 1745230265 * this.field4887;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("be")
   public SpritePixels method8529() {
      if (this.field4886 * -486845133 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field4871, -486845133 * this.field4886);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classJI.field3165, this.field4886 * -486845133, 0, -1966443163);
            if (var1 != null) {
               field4871.method6464(var1, this.field4886 * -486845133);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   public int method8499(byte var1) {
      try {
         return this.field4877 * -324844483;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "oi.af(" + 41);
      }
   }
}
