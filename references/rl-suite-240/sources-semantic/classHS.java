import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hs")
public class classHS extends classHB {
   @ObfuscatedName("ag")
   byte field2880;
   @ObfuscatedName("ae")
   byte field2878;
   @ObfuscatedName("ab")
   byte field2877;
   @ObfuscatedName("az")
   boolean field2881;
   @ObfuscatedName("af")
   byte field2879;

   @ObfuscatedSignature(descriptor = "(I)Lmu;")
   @ObfuscatedName("az")
   public static classMU method6136(int var0) {
      try {
         return classMU.field4585;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "hs.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         boolean var10001;
         if (var1.method13095(-346779531) == 1) {
            if (var2 >= -989877912) {
               return;
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         this.field2881 = var10001;
         this.field2879 = var1.method13097((byte)17);
         this.field2878 = var1.method13097((byte)17);
         this.field2877 = var1.method13097((byte)17);
         this.field2880 = var1.method13097((byte)17);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "hs.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(DDD)I")
   @ObfuscatedName("az")
   public static final int method6135(double var0, double var2, double var4) {
      try {
         double var6 = var4;
         double var8 = var4;
         double var10 = var4;
         if (var2 != 0.0) {
            double var12;
            if (var4 < 0.5) {
               var12 = (1.0 + var2) * var4;
            } else {
               var12 = var2 + var4 - var2 * var4;
            }

            double var14 = var4 * 2.0 - var12;
            double var16 = var0 + 0.3333333333333333;
            if (var16 > 1.0) {
               var16--;
            }

            double var20 = var0 - 0.3333333333333333;
            if (var20 < 0.0) {
               var20++;
            }

            if (6.0 * var16 < 1.0) {
               var6 = var16 * ((var12 - var14) * 6.0) + var14;
            } else if (var16 * 2.0 < 1.0) {
               var6 = var12;
            } else if (3.0 * var16 < 2.0) {
               var6 = var14 + (0.6666666666666666 - var16) * (var12 - var14) * 6.0;
            } else {
               var6 = var14;
            }

            if (var0 * 6.0 < 1.0) {
               var8 = var0 * ((var12 - var14) * 6.0) + var14;
            } else if (var0 * 2.0 < 1.0) {
               var8 = var12;
            } else if (var0 * 3.0 < 2.0) {
               var8 = var14 + (var12 - var14) * (0.6666666666666666 - var0) * 6.0;
            } else {
               var8 = var14;
            }

            if (var20 * 6.0 < 1.0) {
               var10 = var20 * (6.0 * (var12 - var14)) + var14;
            } else if (var20 * 2.0 < 1.0) {
               var10 = var12;
            } else if (3.0 * var20 < 2.0) {
               var10 = var14 + 6.0 * ((var12 - var14) * (0.6666666666666666 - var20));
            } else {
               var10 = var14;
            }
         }

         int var23 = (int)(256.0 * var6);
         int var13 = (int)(256.0 * var8);
         int var24 = (int)(var10 * 256.0);
         return var24 + (var23 << 16) + (var13 << 8);
      } catch (RuntimeException var22) {
         throw classEG.method3899(var22, "hs.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field2881 = var1.method13095(-346779531) == 1;
      this.field2879 = var1.method13097((byte)17);
      this.field2878 = var1.method13097((byte)17);
      this.field2877 = var1.method13097((byte)17);
      this.field2880 = var1.method13097((byte)17);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field2881 = var1.method13095(-346779531) == 1;
      this.field2879 = var1.method13097((byte)17);
      this.field2878 = var1.method13097((byte)17);
      this.field2877 = var1.method13097((byte)17);
      this.field2880 = var1.method13097((byte)17);
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.field3032 = this.field2881;
         var1.field3021 = this.field2879;
         var1.field3023 = this.field2878;
         var1.field3022 = this.field2877;
         var1.field3017 = this.field2880;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "hs.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.field3032 = this.field2881;
      var1.field3021 = this.field2879;
      var1.field3023 = this.field2878;
      var1.field3022 = this.field2877;
      var1.field3017 = this.field2880;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgx;)I")
   @ObfuscatedName("qo")
   public static int method6137(WorldMapData var0) {
      return var0 == null ? var0.method5923() : 1772961034 * var0.field2732.field4053;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.field3032 = this.field2881;
      var1.field3021 = this.field2879;
      var1.field3023 = this.field2878;
      var1.field3022 = this.field2877;
      var1.field3017 = this.field2880;
   }

   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;Lzv;I)V")
   @ObfuscatedName("ai")
   static void method6138(classZV var0, classZV var1, classZV var2, int var3) {
      client.method2334(var0, var1, var2);

      try {
         classBF.field385 = (773246731 * classSA.field5895 - 765) / 2 * 358417233;
         classBF.field393 = 2007709401 * classBF.field385 + 1927755610;
         Ignore.field5836 = 273549476 + -1415132635 * classBF.field393;
         if (classBF.field412) {
            classCS.method2613(var0, var1, (byte)54);
         } else {
            label720: {
               classNI.field4671.method13552(classBF.field385 * -329898575, 0);
               classFR.field2389.method13552(382 + classBF.field385 * -329898575, 0);
               IndexedSprite.method13853(AABB.field2370, 382 + classBF.field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
               if (client.field829 * -44590225 != 0) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  if (client.field829 * -44590225 != 5) {
                     break label720;
                  }

                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }
               }

               byte var4 = 20;
               var0.method59(classKK.field3818, -511275367 * classBF.field393 + 180, 245 - var4, 16777215, -1);
               int var5 = 253 - var4;
               classYW.method13746(180 + -511275367 * classBF.field393 - 152, var5, 304, 34, 9179409);
               classYW.method13746(180 + classBF.field393 * -511275367 - 151, var5 + 1, 302, 32, 0);
               classYW.method13737(180 + classBF.field393 * -511275367 - 150, var5 + 2, classBF.field394 * 1758494531, 30, 9179409);
               classYW.method13737(
                  1758494531 * classBF.field394 + (classBF.field393 * -511275367 + 180 - 150), var5 + 2, 300 - classBF.field394 * 1758494531, 30, 0
               );
               var0.method59(classBF.field391, 180 + -511275367 * classBF.field393, 276 - var4, 16777215, -1);
            }

            if (20 == -44590225 * client.field829) {
               if (var3 >= -1329525269) {
                  return;
               }

               IndexedSprite.method13853(
                  classJB.field3118, classBF.field393 * -511275367 + 180 - classJB.field3118.field7119 / 2, 271 - classJB.field3118.field7117 / 2
               );
               int var13 = 201;
               var0.method59(classBF.field396, classBF.field393 * -511275367 + 180, var13, 16776960, 0);
               var13 += 15;
               var0.method59(classBF.field397, -511275367 * classBF.field393 + 180, var13, 16776960, 0);
               var13 += 15;
               var0.method59(classBF.field398, classBF.field393 * -511275367 + 180, var13, 16776960, 0);
               var13 += 15;
               var13 += 7;
               if (classBF.field384 * -47366135 != 4 && classBF.field384 * -47366135 != 10) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  if (1 != -47366135 * classBF.field384) {
                     if (var3 >= -1329525269) {
                        return;
                     }

                     var0.method51(classKK.field3786, 180 + classBF.field393 * -511275367 - 110, var13, 16777215, 0);
                     short var102 = 200;

                     String var6;
                     for (var6 = classBQ.method1183(1383343981); var0.method33(var6) > var102; var6 = var6.substring(0, var6.length() - 1)) {
                        if (var3 >= -1329525269) {
                           throw new IllegalStateException();
                        }
                     }

                     var0.method51(AbstractFont.method6(var6), 180 + -511275367 * classBF.field393 - 70, var13, 16777215, 0);
                     var13 += 15;

                     String var7;
                     for (var7 = classSL.method10515(classBF.method1065(), (short)-17047); var0.method33(var7) > var102; var7 = var7.substring(1)) {
                        if (var3 >= -1329525269) {
                           throw new IllegalStateException();
                        }
                     }

                     var0.method51(classKK.field3836 + var7, 180 + -511275367 * classBF.field393 - 108, var13, 16777215, 0);
                     var13 += 15;
                  }
               }
            }

            label694: {
               if (10 != client.field829 * -44590225) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  if (-44590225 * client.field829 != 11) {
                     if (client.field829 * -44590225 != 50) {
                        break label694;
                     }

                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }
                  }
               }

               IndexedSprite.method13853(classJB.field3118, -511275367 * classBF.field393, 171);
               if (-47366135 * classBF.field384 == 0) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  int var20 = 251;
                  var0.method59(classKK.field3876, 180 + classBF.field393 * -511275367, var20, 16776960, 0);
                  var20 += 30;
                  int var103 = 180 + classBF.field393 * -511275367 - 80;
                  short var141 = 291;
                  IndexedSprite.method13853(classKA.field3512, var103 - 73, var141 - 20);
                  classAAO.method225(var0, classKK.field3849, var103 - 73, var141 - 20, 144, 40, 16777215, 0, 1, 1, 0);
                  var103 = 180 + -511275367 * classBF.field393 + 80;
                  IndexedSprite.method13853(classKA.field3512, var103 - 73, var141 - 20);
                  classAAO.method225(var0, classKK.field3850, var103 - 73, var141 - 20, 144, 40, 16777215, 0, 1, 1, 0);
               } else if (-47366135 * classBF.field384 == 1) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  var0.method59(classBF.field395, 180 + -511275367 * classBF.field393, 201, 16776960, 0);
                  int var22 = 236;
                  var0.method59(classBF.field396, 180 + classBF.field393 * -511275367, var22, 16777215, 0);
                  var22 += 15;
                  var0.method59(classBF.field397, classBF.field393 * -511275367 + 180, var22, 16777215, 0);
                  var22 += 15;
                  var0.method59(classBF.field398, classBF.field393 * -511275367 + 180, var22, 16777215, 0);
                  var22 += 15;
                  int var105 = -511275367 * classBF.field393 + 180 - 80;
                  short var142 = 321;
                  IndexedSprite.method13853(classKA.field3512, var105 - 73, var142 - 20);
                  var0.method59(classKK.field3775, var105, var142 + 5, 16777215, 0);
                  var105 = 80 + 180 + -511275367 * classBF.field393;
                  IndexedSprite.method13853(classKA.field3512, var105 - 73, var142 - 20);
                  var0.method59(classKK.field3852, var105, 5 + var142, 16777215, 0);
               } else if (-47366135 * classBF.field384 == 2) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  int var26 = 201;
                  var0.method59(classBF.field396, 1429288549 * Ignore.field5836, var26, 16776960, 0);
                  var26 += 15;
                  var0.method59(classBF.field397, 1429288549 * Ignore.field5836, var26, 16776960, 0);
                  var26 += 15;
                  var0.method59(classBF.field398, Ignore.field5836 * 1429288549, var26, 16776960, 0);
                  var26 += 15;
                  var26 += 7;
                  var0.method51(classKK.field3786, 1429288549 * Ignore.field5836 - 110, var26, 16777215, 0);
                  short var107 = 200;

                  String var143;
                  for (var143 = classBQ.method1183(-53098227); var0.method33(var143) > var107; var143 = var143.substring(1)) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }
                  }

                  StringBuilder var10001 = new StringBuilder().append(AbstractFont.method6(var143));
                  boolean var10002 = 0 == classBF.field402 * -1187692459;
                  boolean var10003;
                  if (1612595797 * client.field856 % 40 < 20) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var10003 = true;
                  } else {
                     var10003 = false;
                  }

                  String var193;
                  if (var10002 & var10003) {
                     if (var3 >= -1329525269) {
                        return;
                     }

                     var193 = classQY.method9885(16776960, (byte)31) + classDO.field1587;
                  } else {
                     var193 = "";
                  }

                  var0.method51(var10001.append(var193).toString(), Ignore.field5836 * 1429288549 - 70, var26, 16777215, 0);
                  var26 += 15;

                  String var163;
                  for (var163 = classSL.method10515(classBF.method1065(), (short)-21583); var0.method33(var163) > var107; var163 = var163.substring(1)) {
                     if (var3 >= -1329525269) {
                        return;
                     }
                  }

                  var10001 = new StringBuilder().append(classKK.field3836).append(var163);
                  if (classBF.field402 * -1187692459 == 1) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var10002 = true;
                  } else {
                     var10002 = false;
                  }

                  if (1612595797 * client.field856 % 40 < 20) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var10003 = true;
                  } else {
                     var10003 = false;
                  }

                  String var195;
                  if (var10002 & var10003) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var195 = classQY.method9885(16776960, (byte)108) + classDO.field1587;
                  } else {
                     var195 = "";
                  }

                  var0.method51(var10001.append(var195).toString(), 1429288549 * Ignore.field5836 - 108, var26, 16777215, 0);
                  var26 += 15;
                  int var33 = 277;
                  int var8 = -117 + 1429288549 * Ignore.field5836;
                  IndexedSprite var9 = classLF.method6827(client.field833, classBF.field400, 576240197);
                  IndexedSprite.method13853(var9, var8, var33);
                  var8 += var9.field7119 + 5;
                  var1.method51(classKK.field3667, var8, 13 + var33, 16776960, 0);
                  var8 = Ignore.field5836 * 1429288549 + 24;
                  var9 = classLF.method6827(classAB.field122.method2722((short)-7807), classBF.field405, 1512911023);
                  IndexedSprite.method13853(var9, var8, var33);
                  var8 += var9.field7119 + 5;
                  var1.method51(classKK.field3646, var8, 13 + var33, 16776960, 0);
                  var33 += 15;
                  int var10 = 1429288549 * Ignore.field5836 - 80;
                  short var11 = 321;
                  IndexedSprite.method13853(classKA.field3512, var10 - 73, var11 - 20);
                  var0.method59(classKK.field3707, var10, 5 + var11, 16777215, 0);
                  var10 = 1429288549 * Ignore.field5836 + 80;
                  IndexedSprite.method13853(classKA.field3512, var10 - 73, var11 - 20);
                  var0.method59(classKK.field3852, var10, 5 + var11, 16777215, 0);
                  short var35 = 357;
                  switch (-323997799 * classBF.field388) {
                     case 2:
                        classJH.field3156 = classKK.field3866;
                        break;
                     default:
                        classJH.field3156 = classKK.field3864;
                  }

                  classWR.field6768 = new classUQ(1429288549 * Ignore.field5836, var35, var1.method33(classJH.field3156), 11);
                  classLB.field4087 = new classUQ(1429288549 * Ignore.field5836, var35, var1.method33(classKK.field3865), 11);
                  var1.method59(classJH.field3156, Ignore.field5836 * 1429288549, var35, 16777215, 0);
               } else if (3 == -47366135 * classBF.field384) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var36 = 201;
                  var0.method59(classKK.field3922, -511275367 * classBF.field393 + 180, var36, 16776960, 0);
                  var36 += 20;
                  var1.method59(classKK.field3856, -511275367 * classBF.field393 + 180, var36, 16776960, 0);
                  var36 += 20;
                  var1.method59(classKK.field3857, 180 + classBF.field393 * -511275367, var36, 16776960, 0);
                  var36 += 15;
                  int var108 = 180 + classBF.field393 * -511275367;
                  short var144 = 276;
                  IndexedSprite.method13853(classKA.field3512, var108 - 73, var144 - 20);
                  var2.method59(classKK.field3858, var108, 5 + var144, 16777215, 0);
                  var108 = 180 + classBF.field393 * -511275367;
                  var144 = 326;
                  IndexedSprite.method13853(classKA.field3512, var108 - 73, var144 - 20);
                  var2.method59(classKK.field3859, var108, 5 + var144, 16777215, 0);
               } else if (-47366135 * classBF.field384 == 4) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  var0.method59(classKK.field3835, -511275367 * classBF.field393 + 180, 201, 16776960, 0);
                  int var40 = 236;
                  var0.method59(classBF.field396, 180 + classBF.field393 * -511275367, var40, 16777215, 0);
                  var40 += 15;
                  var0.method59(classBF.field397, classBF.field393 * -511275367 + 180, var40, 16777215, 0);
                  var40 += 15;
                  var0.method59(classBF.field398, 180 + classBF.field393 * -511275367, var40, 16777215, 0);
                  var40 += 15;
                  StringBuilder var188 = new StringBuilder().append(classKK.field3847).append(classSL.method10515(classQP.field5584, (short)12520));
                  String var196;
                  if (client.field856 * 1612595797 % 40 < 20) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var196 = classQY.method9885(16776960, (byte)24) + classDO.field1587;
                  } else {
                     var196 = "";
                  }

                  var0.method51(var188.append(var196).toString(), -511275367 * classBF.field393 + 180 - 108, var40, 16777215, 0);
                  var40 -= 8;
                  var0.method51(classKK.field3614, -511275367 * classBF.field393 + 180 - 9, var40, 16776960, 0);
                  var40 += 15;
                  var0.method51(classKK.field3615, -511275367 * classBF.field393 + 180 - 9, var40, 16776960, 0);
                  int var110 = 180 + classBF.field393 * -511275367 - 9 + var0.method33(classKK.field3615) + 15;
                  int var146 = var40 - var0.field8;
                  IndexedSprite var164;
                  if (classBF.field392) {
                     if (var3 >= -1329525269) {
                        return;
                     }

                     var164 = classEK.field1777;
                  } else {
                     var164 = classCK.field778;
                  }

                  IndexedSprite.method13853(var164, var110, var146);
                  var40 += 15;
                  int var175 = 180 + -511275367 * classBF.field393 - 80;
                  short var182 = 321;
                  IndexedSprite.method13853(classKA.field3512, var175 - 73, var182 - 20);
                  var0.method59(classKK.field3775, var175, 5 + var182, 16777215, 0);
                  var175 = 80 + classBF.field393 * -511275367 + 180;
                  IndexedSprite.method13853(classKA.field3512, var175 - 73, var182 - 20);
                  var0.method59(classKK.field3852, var175, var182 + 5, 16777215, 0);
                  var1.method59(classKK.field3864, 180 + -511275367 * classBF.field393, 36 + var182, 16777215, 0);
               } else if (classBF.field384 * -47366135 == 5) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  var0.method59(classKK.field3860, -511275367 * classBF.field393 + 180, 201, 16776960, 0);
                  int var47 = 221;
                  var2.method59(classBF.field396, -511275367 * classBF.field393 + 180, var47, 16776960, 0);
                  var47 += 15;
                  var2.method59(classBF.field397, -511275367 * classBF.field393 + 180, var47, 16776960, 0);
                  var47 += 15;
                  var2.method59(classBF.field398, 180 + -511275367 * classBF.field393, var47, 16776960, 0);
                  var47 += 15;
                  var47 += 14;
                  var0.method51(classKK.field3873, -511275367 * classBF.field393 + 180 - 145, var47, 16777215, 0);
                  short var111 = 174;

                  String var147;
                  for (var147 = classBQ.method1183(947688212); var0.method33(var147) > var111; var147 = var147.substring(1)) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }
                  }

                  StringBuilder var189 = new StringBuilder().append(AbstractFont.method6(var147));
                  String var197;
                  if (1612595797 * client.field856 % 40 < 20) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var197 = classQY.method9885(16776960, (byte)33) + classDO.field1587;
                  } else {
                     var197 = "";
                  }

                  var0.method51(var189.append(var197).toString(), -511275367 * classBF.field393 + 180 - 34, var47, 16777215, 0);
                  var47 += 15;
                  int var165 = classBF.field393 * -511275367 + 180 - 80;
                  short var177 = 321;
                  IndexedSprite.method13853(classKA.field3512, var165 - 73, var177 - 20);
                  var0.method59(classKK.field3862, var165, 5 + var177, 16777215, 0);
                  var165 = 80 + classBF.field393 * -511275367 + 180;
                  IndexedSprite.method13853(classKA.field3512, var165 - 73, var177 - 20);
                  var0.method59(classKK.field3863, var165, 5 + var177, 16777215, 0);
                  var177 = 356;
                  var1.method59(classKK.field3865, Ignore.field5836 * 1429288549, var177, 268435455, 0);
               } else if (classBF.field384 * -47366135 == 6) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var53 = 201;
                  var0.method59(classBF.field396, -511275367 * classBF.field393 + 180, var53, 16776960, 0);
                  var53 += 15;
                  var0.method59(classBF.field397, classBF.field393 * -511275367 + 180, var53, 16776960, 0);
                  var53 += 15;
                  var0.method59(classBF.field398, classBF.field393 * -511275367 + 180, var53, 16776960, 0);
                  var53 += 15;
                  int var112 = -511275367 * classBF.field393 + 180;
                  short var148 = 321;
                  IndexedSprite.method13853(classKA.field3512, var112 - 73, var148 - 20);
                  var0.method59(classKK.field3863, var112, 5 + var148, 16777215, 0);
               } else if (7 == -47366135 * classBF.field384) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  if (client.field1133 && !client.field891) {
                     if (var3 >= -1329525269) {
                        return;
                     }

                     int var61 = 201;
                     var0.method59(classBF.field396, 1429288549 * Ignore.field5836, var61, 16776960, 0);
                     var61 += 15;
                     var0.method59(classBF.field397, 1429288549 * Ignore.field5836, var61, 16776960, 0);
                     var61 += 15;
                     var0.method59(classBF.field398, 1429288549 * Ignore.field5836, var61, 16776960, 0);
                     int var115 = 1429288549 * Ignore.field5836 - 150;
                     var61 += 10;

                     for (int var150 = 0; var150 < 8; var150++) {
                        if (var3 >= -1329525269) {
                           return;
                        }

                        classKA.field3512.method13856(var115, var61, 30, 40);
                        boolean var10000;
                        if (classBF.field390 * -594543677 == var150) {
                           if (var3 >= -1329525269) {
                              return;
                           }

                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        boolean var190;
                        if (1612595797 * client.field856 % 40 < 20) {
                           if (var3 >= -1329525269) {
                              throw new IllegalStateException();
                           }

                           var190 = true;
                        } else {
                           var190 = false;
                        }

                        boolean var167 = var10000 & var190;
                        StringBuilder var191 = new StringBuilder();
                        String var198;
                        if (classBF.field406[var150] == null) {
                           if (var3 >= -1329525269) {
                              return;
                           }

                           var198 = "";
                        } else {
                           var198 = classBF.field406[var150];
                        }

                        StringBuilder var192 = var191.append(var198);
                        if (var167) {
                           if (var3 >= -1329525269) {
                              return;
                           }

                           var198 = classQY.method9885(16776960, (byte)53) + classDO.field1587;
                        } else {
                           var198 = "";
                        }

                        var0.method51(var192.append(var198).toString(), 10 + var115, 27 + var61, 16777215, 0);
                        if (var150 != 1) {
                           if (var3 >= -1329525269) {
                              throw new IllegalStateException();
                           }

                           if (3 != var150) {
                              var115 += 35;
                              continue;
                           }

                           if (var3 >= -1329525269) {
                              throw new IllegalStateException();
                           }
                        }

                        var115 += 50;
                        var0.method51(AbstractFont.method6("/"), var115 - 13, 27 + var61, 16777215, 0);
                     }

                     int var151 = Ignore.field5836 * 1429288549 - 80;
                     short var168 = 321;
                     IndexedSprite.method13853(classKA.field3512, var151 - 73, var168 - 20);
                     var0.method59("Submit", var151, 5 + var168, 16777215, 0);
                     var151 = 1429288549 * Ignore.field5836 + 80;
                     IndexedSprite.method13853(classKA.field3512, var151 - 73, var168 - 20);
                     var0.method59(classKK.field3852, var151, 5 + var168, 16777215, 0);
                  } else {
                     int var57 = 216;
                     var0.method59(classKK.field3890, classBF.field393 * -511275367 + 180, var57, 16776960, 0);
                     var57 += 15;
                     var2.method59(classKK.field3891, classBF.field393 * -511275367 + 180, var57, 16776960, 0);
                     var57 += 15;
                     var2.method59(classKK.field3631, 180 + -511275367 * classBF.field393, var57, 16776960, 0);
                     var57 += 15;
                     int var113 = 180 + classBF.field393 * -511275367 - 80;
                     short var149 = 321;
                     IndexedSprite.method13853(classKA.field3512, var113 - 73, var149 - 20);
                     var0.method59(classKK.field3893, var113, var149 + 5, 16777215, 0);
                     var113 = 180 + -511275367 * classBF.field393 + 80;
                     IndexedSprite.method13853(classKA.field3512, var113 - 73, var149 - 20);
                     var0.method59(classKK.field3863, var113, 5 + var149, 16777215, 0);
                  }
               } else if (8 == -47366135 * classBF.field384) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var65 = 216;
                  var0.method59(classKK.field3611, 180 + classBF.field393 * -511275367, var65, 16776960, 0);
                  var65 += 15;
                  var2.method59(classKK.field3747, 180 + -511275367 * classBF.field393, var65, 16776960, 0);
                  var65 += 15;
                  var2.method59(classKK.field3748, 180 + -511275367 * classBF.field393, var65, 16776960, 0);
                  var65 += 15;
                  int var116 = -511275367 * classBF.field393 + 180 - 80;
                  short var153 = 321;
                  IndexedSprite.method13853(classKA.field3512, var116 - 73, var153 - 20);
                  var0.method59(classKK.field3749, var116, var153 + 5, 16777215, 0);
                  var116 = 80 + -511275367 * classBF.field393 + 180;
                  IndexedSprite.method13853(classKA.field3512, var116 - 73, var153 - 20);
                  var0.method59(classKK.field3863, var116, 5 + var153, 16777215, 0);
               } else if (9 == -47366135 * classBF.field384) {
                  int var69 = 221;
                  var0.method59(classBF.field396, 180 + -511275367 * classBF.field393, var69, 16776960, 0);
                  var69 += 25;
                  var0.method59(classBF.field397, 180 + -511275367 * classBF.field393, var69, 16776960, 0);
                  var69 += 25;
                  var0.method59(classBF.field398, 180 + -511275367 * classBF.field393, var69, 16776960, 0);
                  int var118 = classBF.field393 * -511275367 + 180;
                  short var154 = 311;
                  IndexedSprite.method13853(classKA.field3512, var118 - 73, var154 - 20);
                  var0.method59(classKK.field3858, var118, var154 + 5, 16777215, 0);
               } else if (10 == -47366135 * classBF.field384) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var72 = 180 + -511275367 * classBF.field393;
                  int var119 = 209;
                  var0.method59(classKK.field3876, classBF.field393 * -511275367 + 180, var119, 16776960, 0);
                  var119 += 20;
                  IndexedSprite.method13853(classGQ.field2653, var72 - 109, var119);
                  if (classBF.field411.isEmpty()) {
                     if (var3 >= -1329525269) {
                        return;
                     }

                     IndexedSprite.method13853(classQH.field5542, var72 - 48, 18 + var119);
                  } else {
                     IndexedSprite.method13853(classQH.field5542, var72 - 48, var119 + 5);
                     var0.method59(classBF.field411, var72, 68 + var119 - 15, 16776960, 0);
                  }
               } else if (-47366135 * classBF.field384 == 12) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  int var73 = 1429288549 * Ignore.field5836;
                  int var121 = 216;
                  var2.method59(classKK.field3897, var73, var121, 16777215, 0);
                  var121 += 17;
                  var2.method59(classKK.field3898, var73, var121, 16777215, 0);
                  var121 += 17;
                  var2.method59(classKK.field3899, var73, var121, 16777215, 0);
                  var121 += 17;
                  var2.method59(classKK.field3900, var73, var121, 16777215, 0);
                  var73 = 1429288549 * Ignore.field5836 - 80;
                  short var125 = 311;
                  IndexedSprite.method13853(classKA.field3512, var73 - 73, var125 - 20);
                  var0.method59(classKK.field3908, var73, var125 + 5, 16777215, 0);
                  var73 = Ignore.field5836 * 1429288549 + 80;
                  IndexedSprite.method13853(classKA.field3512, var73 - 73, var125 - 20);
                  var0.method59(classKK.field3909, var73, 5 + var125, 16777215, 0);
               } else if (13 == classBF.field384 * -47366135) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  int var76 = 231;
                  var2.method59(classKK.field3901, 180 + -511275367 * classBF.field393, var76, 16777215, 0);
                  var76 += 20;
                  var2.method59(classKK.field3902, -511275367 * classBF.field393 + 180, var76, 16777215, 0);
                  int var126 = 180 + -511275367 * classBF.field393;
                  short var78 = 311;
                  IndexedSprite.method13853(classKA.field3512, var126 - 73, var78 - 20);
                  var0.method59(classKK.field3863, var126, var78 + 5, 16777215, 0);
               } else if (14 == classBF.field384 * -47366135) {
                  int var79 = 201;
                  String var127 = "";
                  String var155 = "";
                  String var169 = "";
                  switch (-20437633 * classBF.field387) {
                     case 0:
                        var127 = classKK.field3905;
                        var155 = classKK.field3660;
                        var169 = classKK.field3661;
                        break;
                     case 1:
                        var127 = classKK.field3698;
                        var155 = classKK.field3699;
                        var169 = classKK.field3700;
                        break;
                     case 2:
                        var127 = classKK.field3782;
                        var155 = classKK.field3895;
                        var169 = classKK.field3620;
                        break;
                     default:
                        classBJ.method1121(false, -347989319);
                  }

                  var0.method59(var127, classBF.field393 * -511275367 + 180, var79, 16776960, 0);
                  var79 += 20;
                  var0.method59(var155, 180 + classBF.field393 * -511275367, var79, 16776960, 0);
                  var79 += 20;
                  var0.method59(var169, 180 + classBF.field393 * -511275367, var79, 16776960, 0);
                  int var179 = 180 + classBF.field393 * -511275367;
                  short var183 = 276;
                  IndexedSprite.method13853(classKA.field3512, var179 - 73, var183 - 20);
                  if (1 == -20437633 * classBF.field387) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var0.method59(classKK.field3643, var179, var183 + 5, 16777215, 0);
                  } else {
                     var0.method59(classKK.field3708, var179, 5 + var183, 16777215, 0);
                  }

                  var179 = -511275367 * classBF.field393 + 180;
                  var183 = 326;
                  IndexedSprite.method13853(classKA.field3512, var179 - 73, var183 - 20);
                  var0.method59(classKK.field3863, var179, 5 + var183, 16777215, 0);
               } else if (24 == -47366135 * classBF.field384) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var82 = 221;
                  var0.method59(classBF.field396, -511275367 * classBF.field393 + 180, var82, 16777215, 0);
                  var82 += 15;
                  var0.method59(classBF.field397, classBF.field393 * -511275367 + 180, var82, 16777215, 0);
                  var82 += 15;
                  var0.method59(classBF.field398, -511275367 * classBF.field393 + 180, var82, 16777215, 0);
                  var82 += 15;
                  int var128 = -511275367 * classBF.field393 + 180;
                  short var156 = 301;
                  IndexedSprite.method13853(classKA.field3512, var128 - 73, var156 - 20);
                  var0.method59(classKK.field3793, var128, 5 + var156, 16777215, 0);
               } else if (32 == classBF.field384 * -47366135) {
                  int var86 = 216;
                  var0.method59(classKK.field3890, classBF.field393 * -511275367 + 180, var86, 16776960, 0);
                  var86 += 15;
                  var2.method59(classKK.field3891, classBF.field393 * -511275367 + 180, var86, 16776960, 0);
                  var86 += 15;
                  var2.method59(classKK.field3631, 180 + classBF.field393 * -511275367, var86, 16776960, 0);
                  var86 += 15;
                  int var129 = 180 + classBF.field393 * -511275367 - 80;
                  short var157 = 321;
                  IndexedSprite.method13853(classKA.field3512, var129 - 73, var157 - 20);
                  var0.method59(classKK.field3893, var129, 5 + var157, 16777215, 0);
                  var129 = -511275367 * classBF.field393 + 180 + 80;
                  IndexedSprite.method13853(classKA.field3512, var129 - 73, var157 - 20);
                  var0.method59(classKK.field3863, var129, 5 + var157, 16777215, 0);
               } else if (33 == -47366135 * classBF.field384) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var90 = 201;
                  var0.method59(classBF.field396, -511275367 * classBF.field393 + 180, var90, 16776960, 0);
                  var90 += 20;
                  var1.method59(classBF.field397, 180 + -511275367 * classBF.field393, var90, 16776960, 0);
                  var90 += 20;
                  var1.method59(classBF.field398, -511275367 * classBF.field393 + 180, var90, 16776960, 0);
                  var90 += 15;
                  int var131 = classBF.field393 * -511275367 + 180;
                  short var158 = 276;
                  IndexedSprite.method13853(classKA.field3512, var131 - 73, var158 - 20);
                  var2.method59(classKK.field3734, var131, var158 + 5, 16777215, 0);
                  var131 = classBF.field393 * -511275367 + 180;
                  var158 = 326;
                  IndexedSprite.method13853(classKA.field3512, var131 - 73, var158 - 20);
                  var2.method59(classKK.field3863, var131, 5 + var158, 16777215, 0);
               } else if (classBF.field384 * -47366135 == 34) {
                  if (var3 >= -1329525269) {
                     throw new IllegalStateException();
                  }

                  int var94 = 201;
                  var0.method59(classBF.field396, 180 + classBF.field393 * -511275367, var94, 16776960, 0);
                  var94 += 20;
                  var1.method59(classBF.field397, 180 + classBF.field393 * -511275367, var94, 16776960, 0);
                  var94 += 20;
                  var1.method59(classBF.field398, classBF.field393 * -511275367 + 180, var94, 16776960, 0);
                  int var133 = 180 + classBF.field393 * -511275367;
                  short var160 = 276;
                  IndexedSprite.method13853(classKA.field3512, var133 - 73, var160 - 20);
                  String var186;
                  if (classIS.field3052 != null) {
                     if (var3 >= -1329525269) {
                        throw new IllegalStateException();
                     }

                     var186 = classKK.field3919;
                  } else {
                     var186 = classKK.field3784;
                  }

                  String var170 = var186;
                  var2.method59(var170, var133, var160 + 5, 16777215, 0);
                  var133 = -511275367 * classBF.field393 + 180;
                  var160 = 326;
                  IndexedSprite.method13853(classKA.field3512, var133 - 73, var160 - 20);
                  var2.method59(classKK.field3863, var133, var160 + 5, 16777215, 0);
               } else if (38 == classBF.field384 * -47366135) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  int var97 = Ignore.field5836 * 1429288549;
                  int var135 = 216;
                  var0.method59(classKK.field3757, var97, var135, 16776960, 0);
                  var135 += 25;
                  var1.method59(classKK.field3654, var97, var135, 16777215, 0);
                  var135 += 20;
                  var1.method59(classKK.field3875, var97, var135, 16777215, 0);
                  var135 += 20;
                  var1.method59(classKK.field3906, var97, var135, 16765440, 0);
                  var97 = 1429288549 * Ignore.field5836 - 80;
                  short var139 = 321;
                  IndexedSprite.method13853(classKA.field3512, var97 - 73, var139 - 20);
                  var0.method59(classKK.field3734, var97, 5 + var139, 16777215, 0);
                  var97 = 80 + 1429288549 * Ignore.field5836;
                  IndexedSprite.method13853(classKA.field3512, var97 - 73, var139 - 20);
                  var0.method59(classKK.field3707, var97, 5 + var139, 16777215, 0);
               }
            }

            if (-44590225 * client.field829 >= 10) {
               if (var3 >= -1329525269) {
                  throw new IllegalStateException();
               }

               int[] var100 = new int[4];
               classYW.method13714(var100);
               classYW.method13710(classBF.field385 * -329898575, 0, -329898575 * classBF.field385 + 765, 8379747 * IntProjection.field1936);
               classML.field4529.method2614(classBF.field385 * -329898575 - 22, client.field856 * 1612595797, 1698595949);
               classML.field4529.method2614(765 + -329898575 * classBF.field385 + 22 - 128, client.field856 * 1612595797, 1698595949);
               classYW.method13718(var100);
            }

            IndexedSprite.method13853(MessageNode.field733[classAB.field122.method2729((byte)14) ? 1 : 0], 765 + -329898575 * classBF.field385 - 40, 463);
            if (-44590225 * client.field829 > 5) {
               if (var3 >= -1329525269) {
                  throw new IllegalStateException();
               }

               if (classLU.field4230 == classXO.field6890) {
                  if (var3 >= -1329525269) {
                     return;
                  }

                  if (SceneTilePaint.field2292 != null) {
                     if (var3 >= -1329525269) {
                        return;
                     }

                     int var101 = -329898575 * classBF.field385 + 5;
                     short var140 = 463;
                     byte var162 = 100;
                     byte var171 = 35;
                     IndexedSprite.method13853(SceneTilePaint.field2292, var101, var140);
                     var0.method59(classKK.field3788 + " " + client.field828 * 1311484143, var101 + var162 / 2, var140 + var171 / 2 - 2, 16777215, 0);
                     if (null != classIS.field3052) {
                        if (var3 >= -1329525269) {
                           throw new IllegalStateException();
                        }

                        var1.method59(classKK.field3919, var101 + var162 / 2, 12 + var171 / 2 + var140, 16777215, 0);
                     } else {
                        var1.method59(classKK.field3815, var101 + var162 / 2, var140 + var171 / 2 + 12, 16777215, 0);
                     }
                  } else {
                     SceneTilePaint.field2292 = classSU.method10729(classHC.field2765, "sl_button", "", -1504741754);
                  }
               }
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "hs.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.field3032 = this.field2881;
      var1.field3021 = this.field2879;
      var1.field3023 = this.field2878;
      var1.field3022 = this.field2877;
      var1.field3017 = this.field2880;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZB)I")
   @ObfuscatedName("bz")
   static int method6139(int var0, Script var1, boolean var2, byte var3) {
      try {
         RenderOverview var4 = classIO.method6272((byte)-66);
         if (6600 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var41 = classOA.method8320(1544651080);
               if (var41.method6674(-1905351182)) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  var4.method12487(-210579119 * var41.field4055, var41.field4053 * 1870919123, 819227381 * var41.field4054, true, (short)28249);
               }

               return 1;
            }
         } else if (var0 == 6601) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var40 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               String var58 = "";
               WorldMapData var62 = var4.method12558(var40, 1737537585);
               if (null != var62) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  var58 = var62.method5888(-500044624);
               }

               classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var58;
               return 1;
            }
         } else if (var0 == 6602) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var39 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               var4.method12492(var39, -547639807);
               return 1;
            }
         } else if (6603 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = RenderOverview.method12547(var4, (byte)-6);
            return 1;
         } else if (6604 == var0) {
            int var38 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            RenderOverview.method12553(var4, var38, 945231587);
            return 1;
         } else if (6605 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int[] var67 = classBB.field323;
               int var69 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var71;
               if (var4.method12556(1029607166)) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  var71 = 1;
               } else {
                  var71 = 0;
               }

               var67[var69] = var71;
               return 1;
            }
         } else if (6606 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var37 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               var4.method12563(var37.field4053 * 1870919123, 819227381 * var37.field4054, (byte)-81);
               return 1;
            }
         } else if (6607 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var36 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               var4.method12568(var36.field4053 * 1870919123, var36.field4054 * 819227381, -799160146);
               return 1;
            }
         } else if (var0 == 6608) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var35 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               var4.method12574(var35.field4055 * -210579119, var35.field4053 * 1870919123, 819227381 * var35.field4054, (byte)1);
               return 1;
            }
         } else if (6609 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var34 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               var4.method12576(var34.field4055 * -210579119, var34.field4053 * 1870919123, var34.field4054 * 819227381, (byte)0);
               return 1;
            }
         } else if (6610 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.method12579(-425530838);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.method12580(-1134140564);
               return 1;
            }
         } else if (var0 == 6611) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var33 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               WorldMapData var57 = var4.method12558(var33, 2120512138);
               if (null == var57) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(var57.method5929(-1866243526), -1873274489);
               }

               return 1;
            }
         } else if (6612 == var0) {
            int var32 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            WorldMapData var56 = var4.method12558(var32, 2140534194);
            if (var56 == null) {
               if (var3 >= 85) {
                  throw new IllegalStateException();
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = (var56.method5900(-2105928043) - var56.method5899(2104574153) + 1)
                  * 64;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = (
                     var56.method5911(-2005057880) - WorldMapData.method5907(var56, (short)-23493) + 1
                  )
                  * 64;
            }

            return 1;
         } else if (6613 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var31 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               WorldMapData var55 = var4.method12558(var31, 1933407261);
               if (var55 == null) {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var55.method5899(2121269751) * 64;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = WorldMapData.method5907(var55, (short)-11138) * 64;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var55.method5900(-2059842380) * 64 + 64 - 1;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var55.method5911(-1775957318) * 64 + 64 - 1;
               }

               return 1;
            }
         } else if (6614 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var30 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               WorldMapData var54 = var4.method12558(var30, 2025188083);
               if (null == var54) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = WorldMapData.method5897(var54, (byte)33);
               }

               return 1;
            }
         } else if (6615 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var29 = RenderOverview.method12585(var4, 2116046708);
               if (null == var29) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var29.field4053 * 1870919123;
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var29.field4054 * 819227381;
               }

               return 1;
            }
         } else if (6616 == var0) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = RenderOverview.method12493(var4, -2024075624);
            return 1;
         } else if (6617 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var28 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               WorldMapData var53 = RenderOverview.method12498(var4, -539086250);
               if (var53 == null) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                     return 1;
                  }
               } else {
                  int[] var61 = WorldMapData.method5869(
                     var53, -210579119 * var28.field4055, 1870919123 * var28.field4053, 819227381 * var28.field4054, (short)3310
                  );
                  if (var61 == null) {
                     if (var3 >= 85) {
                        throw new IllegalStateException();
                     }

                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var61[0];
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var61[1];
                  }

                  return 1;
               }
            }
         } else if (var0 == 6618) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var27 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               WorldMapData var52 = RenderOverview.method12498(var4, -539086250);
               if (var52 == null) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                     return 1;
                  }
               } else {
                  classKY var60 = var52.method5871(1870919123 * var27.field4053, 819227381 * var27.field4054, (byte)123);
                  if (null == var60) {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(var60, -1735582275);
                  }

                  return 1;
               }
            }
         } else if (var0 == 6619) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var26 = classBB.field323[DynamicObject.field1676 * -324749371];
               classKY var51 = new classKY(classBB.field323[-324749371 * DynamicObject.field1676 + 1]);
               classPC.method8875(var26, var51, false, -1555610915);
               return 1;
            }
         } else if (6620 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var25 = classBB.field323[-324749371 * DynamicObject.field1676];
               classKY var50 = new classKY(classBB.field323[DynamicObject.field1676 * -324749371 + 1]);
               classPC.method8875(var25, var50, true, -552667590);
               return 1;
            }
         } else if (var0 == 6621) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var24 = classBB.field323[DynamicObject.field1676 * -324749371];
               classKY var49 = new classKY(classBB.field323[-324749371 * DynamicObject.field1676 + 1]);
               WorldMapData var59 = var4.method12558(var24, 2099779103);
               if (null == var59) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  } else {
                     classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 0;
                     return 1;
                  }
               } else {
                  int[] var66 = classBB.field323;
                  int var68 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
                  byte var70;
                  if (WorldMapData.method5858(var59, var49.field4055 * -210579119, 1870919123 * var49.field4053, 819227381 * var49.field4054, (byte)123)) {
                     if (var3 >= 85) {
                        throw new IllegalStateException();
                     }

                     var70 = 1;
                  } else {
                     var70 = 0;
                  }

                  var66[var68] = var70;
                  return 1;
               }
            }
         } else if (6622 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.method12589((byte)-65);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.method12592(-1117430456);
               return 1;
            }
         } else if (var0 == 6623) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classKY var23 = new classKY(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371]);
               WorldMapData var48 = var4.method12486(-210579119 * var23.field4055, 1870919123 * var23.field4053, 819227381 * var23.field4054, (byte)12);
               if (null == var48) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var48.method5876(-608589044);
               }

               return 1;
            }
         } else if (var0 == 6624) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               RenderOverview.method12596(var4, classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], (byte)17);
               return 1;
            }
         } else if (var0 == 6625) {
            var4.method12600((byte)120);
            return 1;
         } else if (6626 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               var4.method12602(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], 1515465245);
               return 1;
            }
         } else if (var0 == 6627) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               var4.method12605((byte)118);
               return 1;
            }
         } else if (var0 == 6628) {
            boolean var65;
            if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
               if (var3 >= 85) {
                  throw new IllegalStateException();
               }

               var65 = true;
            } else {
               var65 = false;
            }

            boolean var22 = var65;
            var4.method12608(var22, 861017044);
            return 1;
         } else if (var0 == 6629) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var21 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               var4.method12610(var21, (byte)-1);
               return 1;
            }
         } else if (6630 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var20 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               RenderOverview.method12614(var4, var20, 1791589508);
               return 1;
            }
         } else if (6631 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               var4.method12616(-254931415);
               return 1;
            }
         } else if (var0 == 6632) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               boolean var64;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  var64 = true;
               } else {
                  var64 = false;
               }

               boolean var19 = var64;
               RenderOverview.method12627(var4, var19, (byte)-22);
               return 1;
            }
         } else if (6633 == var0) {
            DynamicObject.field1676 -= 240082458;
            int var18 = classBB.field323[-324749371 * DynamicObject.field1676];
            boolean var47 = 1 == classBB.field323[1 + DynamicObject.field1676 * -324749371];
            var4.method12620(var18, var47, -1774238068);
            return 1;
         } else if (6634 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var17 = classBB.field323[-324749371 * DynamicObject.field1676];
               boolean var63;
               if (classBB.field323[DynamicObject.field1676 * -324749371 + 1] == 1) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  var63 = true;
               } else {
                  var63 = false;
               }

               boolean var46 = var63;
               RenderOverview.method12622(var4, var17, var46, (byte)51);
               return 1;
            }
         } else if (6635 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int[] var10000 = classBB.field323;
               int var10001 = (DynamicObject.field1676 += 120041229) * -324749371 - 1;
               byte var10002;
               if (var4.method12625(404322590)) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  var10002 = 1;
               } else {
                  var10002 = 0;
               }

               var10000[var10001] = var10002;
               return 1;
            }
         } else if (6636 == var0) {
            int var16 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = RenderOverview.method12630(var4, var16, (byte)13) ? 1 : 0;
            return 1;
         } else if (var0 == 6637) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var15 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var4.method12635(var15, -1181761071) ? 1 : 0;
               return 1;
            }
         } else if (6638 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 240082458;
               int var14 = classBB.field323[-324749371 * DynamicObject.field1676];
               classKY var45 = new classKY(classBB.field323[1 + -324749371 * DynamicObject.field1676]);
               classKY var7 = var4.method12646(var14, var45, 241883862);
               if (null == var7) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(var7, -1646416364);
               }

               return 1;
            }
         } else if (6639 == var0) {
            WorldMapIcon var13 = RenderOverview.method12653(var4, (short)-17984);
            if (null == var13) {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var13.vmethod486((byte)-55);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(var13.field2767, -1744487000);
            }

            return 1;
         } else if (var0 == 6640) {
            WorldMapIcon var12 = var4.method12657((byte)-88);
            if (var12 == null) {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var12.vmethod486((byte)-114);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(var12.field2767, -2086769174);
            }

            return 1;
         } else if (6693 == var0) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var11 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classPS var44 = SceneTilePaint.method4817(var11, (byte)12);
               if (null == var44.field5409) {
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = "";
               } else {
                  classBB.field328[(classBB.field338 += 1222866659) * 1375698635 - 1] = var44.field5409;
               }

               return 1;
            }
         } else if (var0 == 6694) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var10 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classPS var43 = SceneTilePaint.method4817(var10, (byte)-34);
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var43.field5392 * 1698532127;
               return 1;
            }
         } else if (var0 == 6695) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               int var9 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
               classPS var42 = SceneTilePaint.method4817(var9, (byte)-18);
               if (var42 == null) {
                  if (var3 >= 85) {
                     throw new IllegalStateException();
                  }

                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
               } else {
                  classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = 1254355271 * var42.field5402;
               }

               return 1;
            }
         } else if (var0 == 6696) {
            int var5 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            classPS var6 = SceneTilePaint.method4817(var5, (byte)0);
            if (var6 == null) {
               if (var3 >= 85) {
                  throw new IllegalStateException();
               }

               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = -1;
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = var6.field5393 * 1274039073;
            }

            return 1;
         } else if (var0 == 6697) {
            classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classAC.field132.field2895 * -753629693;
            return 1;
         } else if (var0 == 6698) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(classAC.field132.field2894, -1968935754);
               return 1;
            }
         } else if (var0 == 6699) {
            if (var3 >= 85) {
               throw new IllegalStateException();
            } else {
               classBB.field323[(DynamicObject.field1676 += 120041229) * -324749371 - 1] = classKY.method6663(classAC.field132.field2893, -1491760677);
               return 1;
            }
         } else {
            return 2;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "hs.bz(" + 41);
      }
   }

   classHS(classIF var1) {
      this.this$0 = var1;
   }
}
