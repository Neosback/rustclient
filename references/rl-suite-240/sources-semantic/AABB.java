import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fp")
public class AABB implements net.runelite.api.AABB {
   @ObfuscatedName("az")
   final int field2362;
   @ObfuscatedName("ax")
   int field2365;
   @ObfuscatedName("ag")
   int field2364;
   @ObfuscatedName("as")
   int field2367;
   @ObfuscatedName("af")
   int field2366;
   @ObfuscatedName("ab")
   int field2368;
   @ObfuscatedName("ae")
   int field2363;
   @ObfuscatedSignature(descriptor = "Lfp;")
   @ObfuscatedName("ac")
   AABB field2369;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("ck")
   static IndexedSprite field2370;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("mj")
   static IndexedSprite[] field2371;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   int method4931() {
      return 2105673973 * this.field2368 + this.field2367 * -81102275;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   int method4919(int var1) {
      try {
         return this.field2366 * 1301855055 + -915116879 * this.field2365;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.af(" + 41);
      }
   }

   public int getExtremeZ() {
      return this.field2368 * 2105673973;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   int method4923(int var1) {
      try {
         return this.field2364 * -1027280203 - this.field2363 * -623192411;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("as")
   int method4927(byte var1) {
      try {
         return -623192411 * this.field2363 + this.field2364 * -1027280203;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfp;B)I")
   @ObfuscatedName("gr")
   public static int method4932(AABB var0, byte var1) {
      if (var0 == null) {
         return var0.method4934(var1);
      } else {
         try {
            return 2105673973 * var0.field2368 + var0.field2367 * -81102275;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "fp.as(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   int method4929(int var1) {
      try {
         return -81102275 * this.field2367 - 2105673973 * this.field2368;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   int method4933() {
      return 2105673973 * this.field2368 + this.field2367 * -81102275;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   int method4924() {
      return this.field2364 * -1027280203 - this.field2363 * -623192411;
   }

   public int getCenterY() {
      return this.field2364 * -1027280203;
   }

   @ObfuscatedSignature(descriptor = "([BIIIIS)Z")
   @ObfuscatedName("al")
   static boolean method4936(byte[] var0, int var1, int var2, int var3, int var4, short var5) {
      try {
         boolean var6 = true;
         classXY var7 = new classXY(var0);
         int var8 = -1;

         label124:
         while (true) {
            int var9 = var7.method13156((byte)27);
            if (var9 == 0) {
               return var6;
            }

            var8 += var9;
            int var10 = 0;
            boolean var11 = false;

            while (true) {
               while (!var11) {
                  int var12 = var7.method13150((short)11060);
                  if (0 == var12) {
                     if (var5 == 16384) {
                        throw new IllegalStateException();
                     }
                     continue label124;
                  }

                  var10 += var12 - 1;
                  int var13 = var10 & 63;
                  int var14 = var10 >> 6 & 63;
                  int var15 = var7.method13095(-346779531) >> 2;
                  int var16 = var1 + var14;
                  int var17 = var2 + var13;
                  if (var16 >= 0) {
                     if (var5 == 16384) {
                        throw new IllegalStateException();
                     }

                     if (var17 >= 0 && var16 <= var3) {
                        if (var5 == 16384) {
                           throw new IllegalStateException();
                        }

                        if (var17 <= var4) {
                           if (var5 == 16384) {
                              throw new IllegalStateException();
                           }

                           ObjectComposition var18 = classMU.method7771(var8, 2001168762);
                           boolean var10000;
                           if (classKB.field3535.field3536 * 78062377 == var15) {
                              if (var5 == 16384) {
                                 throw new IllegalStateException();
                              }

                              var10000 = true;
                           } else {
                              var10000 = false;
                           }

                           boolean var19 = var10000;
                           if (var19) {
                              if (var5 == 16384) {
                                 throw new IllegalStateException();
                              }

                              if (client.field886) {
                                 if (var5 == 16384) {
                                    throw new IllegalStateException();
                                 }

                                 if (0 == 1663954033 * var18.field4921 && var18.field4944 * -2040437663 != 1) {
                                    if (var5 == 16384) {
                                       throw new IllegalStateException();
                                    }

                                    if (!var18.field4931) {
                                       continue;
                                    }
                                 }
                              }
                           }

                           if (!var18.method8567(-874336012)) {
                              if (var5 == 16384) {
                                 throw new IllegalStateException();
                              }

                              client.field817 += -1004880473;
                              var6 = false;
                           }

                           var11 = true;
                        }
                     }
                  }
               }

               int var21 = var7.method13150((short)21120);
               if (var21 == 0) {
                  if (var5 == 16384) {
                     throw new IllegalStateException();
                  }
                  break;
               }

               var7.method13095(-346779531);
            }
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "fp.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lfp;I)I")
   @ObfuscatedName("an")
   public static int method4917(AABB var0, int var1) {
      if (var0 == null) {
         var0.method4922(var1);
      }

      try {
         return -915116879 * var0.field2365 - var0.field2366 * 1301855055;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.az(" + 41);
      }
   }

   public int getExtremeX() {
      return this.field2366 * 1301855055;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   int method4925() {
      return this.field2364 * -1027280203 - this.field2363 * -623192411;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int method4926() {
      return this.field2364 * -1027280203 - this.field2363 * -623192411;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;)V")
   @ObfuscatedName("uz")
   public static void method4935(PlayerComposition var0) {
      long var1 = 4603856865473899623L * var0.field4104;
      long[] var3 = classXY.field6954;
      var0.field4104 = -5187355721230823255L;

      for (int var4 = 0; var4 < 12; var4++) {
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4093[var4] >> 24) & 255L)]);
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4093[var4] >> 16) & 255L)]);
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4093[var4] >> 8) & 255L)]);
         var0.field4104 = 5187355721230823255L
            * (var0.field4104 * 4603856865473899623L >>> 8 ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4093[var4]) & 255L)]);
      }

      if (var0.field4095 != null) {
         for (int var6 = 0; var6 < var0.field4095.length; var6++) {
            if (var0.field4095[var6] != null) {
               if (var0.field4095[var6].field5259 != null) {
                  for (int var5 = 0; var5 < var0.field4095[var6].field5259.length; var5++) {
                     var0.field4104 = (
                           var0.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var6].field5259[var5] >> 8) & 255L)]
                        )
                        * 5187355721230823255L;
                     var0.field4104 = 5187355721230823255L
                        * (
                           var0.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var6].field5259[var5]) & 255L)]
                        );
                  }
               }

               if (null != var0.field4095[var6].field5258) {
                  for (int var8 = 0; var8 < var0.field4095[var6].field5258.length; var8++) {
                     var0.field4104 = (
                           var0.field4104 * 4603856865473899623L >>> 8
                              ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4095[var6].field5258[var8] >> 8) & 255L)]
                        )
                        * 5187355721230823255L;
                     var0.field4104 = (
                           4603856865473899623L * var0.field4104 >>> 8
                              ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4095[var6].field5258[var8]) & 255L)]
                        )
                        * 5187355721230823255L;
                  }
               }
            }
         }
      }

      for (int var7 = 0; var7 < 5; var7++) {
         var0.field4104 = 5187355721230823255L
            * (4603856865473899623L * var0.field4104 >>> 8 ^ var3[(int)((var0.field4104 * 4603856865473899623L ^ var0.field4096[var7]) & 255L)]);
      }

      var0.field4104 = 5187355721230823255L
         * (var0.field4104 * 4603856865473899623L >>> 8 ^ var3[(int)((4603856865473899623L * var0.field4104 ^ var0.field4092 * 1705409403) & 255L)]);
      if (0L != var1 && var1 != var0.field4104 * 4603856865473899623L || var0.field4094) {
         PlayerComposition.field4098.method6461(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ax")
   int method4918() {
      return -732726773 * this.field2365 - this.field2366 * 1512821760;
   }

   public int getCenterX() {
      return this.field2365 * -915116879;
   }

   public int getCenterZ() {
      return this.field2367 * -81102275;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   int method4928() {
      return 1450116013 * this.field2363 + this.field2364 * -1027280203;
   }

   public int getExtremeY() {
      return this.field2363 * -623192411;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   int method4930() {
      return -81102275 * this.field2367 - 2105673973 * this.field2368;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   int method4920() {
      return this.field2366 * 1301855055 + -915116879 * this.field2365;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   int method4921() {
      return this.field2366 * -1071915259 + 2125240123 * this.field2365;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eq")
   static void method4937(int var0) {
      try {
         client.field975.field6795 = 0;
         client.field975.method12757(-1);
         client.field974 = false;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "fp.eq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method4916(IndexDataBase var0, int var1) {
      try {
         classPH.field5252 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.az(" + ')');
      }
   }

   AABB(int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.field2362 = -640752131 * var1;
      this.field2365 = 993680465 * var2;
      this.field2364 = 1956637597 * var3;
      this.field2367 = -1663537899 * var4;
      this.field2366 = -1181254737 * var5;
      this.field2363 = var6 * -522641107;
      this.field2368 = var7 * 1704747869;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   int method4922(int var1) {
      try {
         return this.field2366 * 1301855055 + -915116879 * this.field2367;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ab")
   int method4934(byte var1) {
      try {
         return 2105673973 * this.field2365 + this.field2366 * -81102275;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fp.as(" + 41);
      }
   }
}
