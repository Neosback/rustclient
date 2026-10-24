import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fy")
public class classFY {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field2511 = 2048;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final double field2507 = 2607.5945876176133;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final int field2509 = 32;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2508 = 1024;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2510 = 3;

   classFY() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)V")
   @ObfuscatedName("dr")
   public static void method5306(client var0) {
      if (var0 == null) {
         var0.method2156();
      } else {
         Menu var1 = client.field975;
         int var2 = 0;

         while (!var2) {
            var2 = 1;

            for (int var3 = 0; var3 < -268990005 * var1.field6795 - 1; var3++) {
               if (var1.field6798[var3] < 980774434 && var1.field6798[var3 + 1] > 1000) {
                  String var4 = var1.field6803[var3];
                  var1.field6803[var3] = var1.field6803[1 + var3];
                  var1.field6803[1 + var3] = var4;
                  String var5 = var1.field6802[var3];
                  var1.field6802[var3] = var1.field6802[1 + var3];
                  var1.field6802[1 + var3] = var5;
                  Menu var6 = var1.field6804[var3];
                  var1.field6804[var3] = var1.field6804[1 + var3];
                  var1.field6804[1 + var3] = var6;
                  int var7 = var1.field6798[var3];
                  var1.field6798[var3] = var1.field6798[1 + var3];
                  var1.field6798[1 + var3] = var7;
                  var7 = var1.field6796[var3];
                  var1.field6796[var3] = var1.field6796[1 + var3];
                  var1.field6796[var3 + 1] = var7;
                  var7 = var1.field6797[var3];
                  var1.field6797[var3] = var1.field6797[var3 + 1];
                  var1.field6797[var3 + 1] = var7;
                  var7 = var1.field6799[var3];
                  var1.field6799[var3] = var1.field6799[1 + var3];
                  var1.field6799[1 + var3] = var7;
                  var7 = var1.field6800[var3];
                  var1.field6800[var3] = var1.field6800[var3 + 1];
                  var1.field6800[1 + var3] = var7;
                  var7 = var1.field6801[var3];
                  var1.field6801[var3] = var1.field6801[1 + var3];
                  var1.field6801[1 + var3] = var7;
                  boolean var8 = var1.field6805[var3];
                  var1.field6805[var3] = var1.field6805[var3 + 1];
                  var1.field6805[var3 + 1] = var8;
                  var2 = 0;
               }
            }
         }

         if (!classWK.field6689.method12230(-431955666)) {
            var2 = classTZ.field6259 * 1229235359;
            if (client.field974) {
               if (var2 != 1
                  && (classON.field4993 || var2 != 4)
                  && !client.field975.method12762(-977497311 * classTZ.field6253, -921625312 * classTZ.field6262, -1488813673)) {
                  client.field974 = false;
                  Menu.method12741(client.field975, -1741044210);
               }

               if (var2 == 1 || !classON.field4993 && 4 == var2) {
                  client.field975.method12733(-1353468692 * classTZ.field6264, 1709092842 * classTZ.field6255, (short)-23548);
                  client.field974 = false;
                  Menu.method12741(client.field975, 598241910);
               }
            } else if (client.field975.field6795 * -1362257157 > 0) {
               int var10 = 239115864 * client.field975.field6795 - 1;
               if ((var2 == 1 || !classON.field4993 && var2 == 4)
                  && var0.method2344(client.field975.field6798[var10], client.field975.field6805[var10], (byte)70)) {
                  var2 = 2;
               }

               if (1 == var2 || !classON.field4993 && 4 == var2) {
                  client.field975.method12725(var10, -1111856393);
               }

               if (2 == var2) {
                  var0.method1651(1422993977 * classTZ.field6264, 1149972828 * classTZ.field6255, -1679722925);
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;)I")
   @ObfuscatedName("ab")
   public static int method5304(classSU var0) {
      return var0 == null ? var0.method10715() : classSU.method10718(var0, 2112203563) - classSU.method10714(var0, (byte)3);
   }

   @ObfuscatedSignature(descriptor = "(FFFFB)F")
   @ObfuscatedName("ae")
   static final float method5301(float var0, float var1, float var2, float var3, byte var4) {
      try {
         return var2 * var0 - var1 * var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "fy.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;I)Laay;")
   @ObfuscatedName("jz")
   public static classAAY method5303(classZD var0, int var1) {
      return (classAAY)var0.field7156.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Lkr;B)V")
   @ObfuscatedName("ck")
   static void method5305(WorldView var0, classKR var1, byte var2) {
      try {
         int var3 = 0;

         for (int var4 = 0; var4 < var0.field1691.method14050((byte)10); var4++) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            WorldEntity var5 = (WorldEntity)var0.field1698.method13637(var0.field1691.method14048(var4, 1891622283));
            if (var5 != null) {
               if (-2034209657 * client.field847 == var5.field6010.field1697 * 2140889407) {
                  if (var2 <= 1) {
                     return;
                  }
               } else if (WorldEntity.method10615(var5, -859227413) == var1) {
                  boolean var10000;
                  if (var3 < client.field883 * 1387630175) {
                     if (var2 <= 1) {
                        throw new IllegalStateException();
                     }

                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  boolean var6 = var10000;
                  if (!var6) {
                     if (var2 <= 1) {
                        throw new IllegalStateException();
                     }
                  } else {
                     var3++;
                     boolean var7 = false;
                     if (classKR.field4006 == var1) {
                        if (var2 <= 1) {
                           return;
                        }

                        var7 = classMN.method7666(var5, var0, -734181220);
                     }

                     classDG.method3088(var0, var5, var7, 1772441953);
                  }
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "fy.ck(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("az")
   static int method5300(int var0, int var1, byte var2) {
      try {
         ItemContainer var3 = (ItemContainer)HashTable.method13432(ItemContainer.field454, var0);
         if (null == var3) {
            if (var2 >= 1) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            if (var1 >= 0) {
               if (var2 >= 1) {
                  throw new IllegalStateException();
               }

               if (var1 < var3.field456.length) {
                  return var3.field456[var1];
               }

               if (var2 >= 1) {
                  throw new IllegalStateException();
               }
            }

            return -1;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "fy.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method5302(IndexDataBase var0, int var1) {
      try {
         classBQ.field531 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fy.az(" + ')');
      }
   }
}
