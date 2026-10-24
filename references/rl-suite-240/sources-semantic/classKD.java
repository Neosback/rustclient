import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kd")
public class classKD {
   @ObfuscatedSignature(descriptor = "Lkd;")
   @ObfuscatedName("ab")
   static final classKD field3547 = new classKD(3);
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("cl")
   static IndexDataBase field3552;
   @ObfuscatedSignature(descriptor = "Lkd;")
   @ObfuscatedName("ae")
   static final classKD field3546 = new classKD(2);
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   static final int field3551 = 34;
   @ObfuscatedSignature(descriptor = "Lkd;")
   @ObfuscatedName("ag")
   static final classKD field3549 = new classKD(4);
   @ObfuscatedName("as")
   public final int field3550;
   @ObfuscatedSignature(descriptor = "Lkd;")
   @ObfuscatedName("af")
   public static final classKD field3548 = new classKD(1);
   @ObfuscatedSignature(descriptor = "Lkd;")
   @ObfuscatedName("az")
   public static final classKD field3545 = new classKD(0);

   @ObfuscatedSignature(descriptor = "([F[II)V")
   @ObfuscatedName("al")
   public static void method6501(float[] var0, int[] var1, int var2) {
      try {
         Actor.method3141(var0, var1, 0, var0.length - 1, 1851800923);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "kd.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("jc")
   public static void method6503(Scene var0) {
      if (var0 == null) {
         var0.getDrawDistance();
      }

      var0.field2031 = true;
   }

   classKD(int var1) {
      this.field3550 = 33137207 * var1;
   }

   @ObfuscatedSignature(descriptor = "(II)Lod;")
   @ObfuscatedName("az")
   public static EnumComposition method6500(int var0, int var1) {
      try {
         EnumComposition var2 = (EnumComposition)NodeCache.method6460(EnumComposition.field4818, var0);
         if (null != var2) {
            if (var1 <= 240616945) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classLH.field4166.method11942(8, var0, -1038848619);
            var2 = new EnumComposition(new classXY(var3));
            EnumComposition.field4818.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "kd.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("aq")
   static int method6504(int var0, Script var1, boolean var2, int var3) {
      try {
         Widget var4;
         if (var0 >= 2000) {
            if (var3 == 1193425358) {
               throw new IllegalStateException();
            }

            var0 -= 1000;
            var4 = classWK.field6689.method7421(classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371], (byte)66);
         } else {
            Widget var10000;
            if (var2) {
               if (var3 == 1193425358) {
                  throw new IllegalStateException();
               }

               var10000 = classSZ.field6108;
            } else {
               var10000 = TileItem.field1556;
            }

            var4 = var10000;
         }

         if (1200 != var0) {
            if (var3 == 1193425358) {
               throw new IllegalStateException();
            }

            if (var0 != 1205) {
               if (var3 == 1193425358) {
                  throw new IllegalStateException();
               }

               if (var0 != 1212) {
                  if (1201 == var0) {
                     if (var3 == 1193425358) {
                        throw new IllegalStateException();
                     }

                     var4.field4331 = 1312656782;
                     var4.field4372 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 434741949;
                     return 1;
                  }

                  if (var0 == 1202) {
                     if (var3 == 1193425358) {
                        throw new IllegalStateException();
                     }

                     var4.field4331 = 1968985173;
                     Player var13 = classMB.method7564(-1324753833);
                     int var10001;
                     if (var13 != null) {
                        if (var3 == 1193425358) {
                           throw new IllegalStateException();
                        }

                        var10001 = var13.field1283.method6751(170077571);
                     } else {
                        var10001 = -1;
                     }

                     var4.field4372 = var10001 * 434741949;
                     return 1;
                  }

                  if (var0 == 1207) {
                     if (var3 == 1193425358) {
                        throw new IllegalStateException();
                     }

                     boolean var20;
                     if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                        if (var3 == 1193425358) {
                           throw new IllegalStateException();
                        }

                        var20 = true;
                     } else {
                        var20 = false;
                     }

                     boolean var12 = var20;
                     Player var16 = classMB.method7564(858191598);
                     PlayerComposition var21;
                     if (null != var16) {
                        if (var3 == 1193425358) {
                           throw new IllegalStateException();
                        }

                        var21 = var16.field1283;
                     } else {
                        var21 = null;
                     }

                     PlayerComposition var18 = var21;
                     var4.method7353(var18, var12, (byte)66);
                     return 1;
                  }

                  if (1208 == var0) {
                     if (var3 == 1193425358) {
                        throw new IllegalStateException();
                     }

                     int var11 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                     if (var4.field4356 == null) {
                        throw new RuntimeException("");
                     }

                     var4.field4356.method6773(var11, 1270668958);
                     return 1;
                  }

                  if (var0 == 1209) {
                     if (var3 == 1193425358) {
                        throw new IllegalStateException();
                     }

                     DynamicObject.field1676 -= 240082458;
                     int var10 = classBB.field323[-324749371 * DynamicObject.field1676];
                     int var15 = classBB.field323[-324749371 * DynamicObject.field1676 + 1];
                     if (var4.field4356 == null) {
                        if (var3 == 1193425358) {
                           throw new IllegalStateException();
                        }

                        throw new RuntimeException("");
                     }

                     PlayerComposition.method6770(var4.field4356, var10, var15, -1379854773);
                     return 1;
                  }

                  if (var0 == 1210) {
                     int var9 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
                     if (var4.field4356 == null) {
                        if (var3 == 1193425358) {
                           throw new IllegalStateException();
                        }

                        throw new RuntimeException("");
                     }

                     Player var14 = classMB.method7564(92699019);
                     int var19;
                     if (null != var14) {
                        if (var3 == 1193425358) {
                           throw new IllegalStateException();
                        }

                        var19 = var14.field1283.field4092 * 1705409403;
                     } else {
                        var19 = 0;
                     }

                     int var17 = var19;
                     PlayerComposition.method6759(var4.field4356, var17, var9, 1562392691);
                     return 1;
                  }

                  if (var0 == 1214) {
                     var4.field4331 = 955659832;
                     var4.field4372 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 434741949;
                     return 1;
                  }

                  if (1215 == var0) {
                     var4.field4331 = 1611988223;
                     var4.field4372 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] * 434741949;
                     return 1;
                  }

                  return 2;
               }

               if (var3 == 1193425358) {
                  throw new IllegalStateException();
               }
            }
         }

         DynamicObject.field1676 -= 240082458;
         int var5 = classBB.field323[DynamicObject.field1676 * -324749371];
         int var6 = classBB.field323[DynamicObject.field1676 * -324749371 + 1];
         var4.field4317 = 1321867761 * var5;
         var4.field4342 = 911947905 * var6;
         ItemComposition var7 = classOB.method8329(var5, (byte)-87);
         var4.field4354 = -882581749 * var7.field5033;
         var4.field4290 = var7.field5034 * -1704932525;
         var4.field4292 = var7.field5052 * -2019216183;
         var4.field4288 = 141623689 * var7.field5036;
         var4.field4289 = -614150625 * var7.field5037;
         var4.field4336 = var7.field5032 * -379927707;
         if (var0 == 1205) {
            var4.field4297 = 0;
         } else if (var0 == 1212 | 1 == 1633663665 * var7.field5053) {
            var4.field4297 = -349833341;
         } else {
            var4.field4297 = -699666682;
         }

         if (var4.field4294 * 613100595 > 0) {
            if (var3 == 1193425358) {
               throw new IllegalStateException();
            }

            var4.field4336 = var4.field4336 * -746149408 / (613100595 * var4.field4294) * 1556726191;
         } else if (var4.field4253 * 1523460725 > 0) {
            if (var3 == 1193425358) {
               throw new IllegalStateException();
            }

            var4.field4336 = var4.field4336 * -746149408 / (var4.field4253 * 1523460725) * 1556726191;
         }

         return 1;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "kd.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)[Ljava/lang/String;")
   @ObfuscatedName("ax")
   static String[] method6502(String var0, byte var1) {
      try {
         String[] var2 = var0.split("(?<!\\\\),", -1);

         for (int var3 = 0; var3 < var2.length; var3++) {
            if (var1 >= 3) {
               throw new IllegalStateException();
            }

            var2[var3] = classME.method7603(var2[var3], -262140534);
            String var7 = var2[var3];
            String var6 = var7.replaceAll("\\\\,", ",");
            var2[var3] = var6;
         }

         return var2;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "kd.ax(" + ')');
      }
   }
}
