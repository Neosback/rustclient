import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ua")
public class classUA implements classUI {
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final String field6273 = "scape main";
   @ObfuscatedSignature(descriptor = "Lwi;")
   @ObfuscatedName("az")
   public final classWI field6272;

   classUA(classWX var1) {
      this.field6272 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldc;Lct;)V")
   @ObfuscatedName("qt")
   public static void method11297(classDC var0, Player var1) {
      var0.field1387.add(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbx;Lbt;)[F")
   @ObfuscatedName("lh")
   public static float[] method11299(classBX var0, classBT var1) {
      if (var0 == null) {
         var0.method1355(var1);
      }

      return var0.field603[var0.method1353(var1)];
   }

   @ObfuscatedSignature(descriptor = "(Lua;II)I")
   @ObfuscatedName("si")
   public static int method11295(classUA var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method11296(var1, var1);
      } else {
         try {
            return var0.field6272.vmethod641(var1, (byte)8);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "ua.az(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([BI)V")
   @ObfuscatedName("af")
   public static void method11298(byte[] var0, int var1) {
      try {
         synchronized (classZA.field7125) {
            if (100 == var0.length) {
               if (var1 != 2022759572) {
                  return;
               }

               if (1951739973 * classZA.field7133 < -2141706585 * classZA.field7130) {
                  if (var1 != 2022759572) {
                     throw new IllegalStateException();
                  }

                  classZA.field7134[(classZA.field7133 += 1490182797) * 1951739973 - 1] = var0;
                  return;
               }
            }

            if (var0.length == 5000 && classZA.field7127 * 1052148383 < -817333317 * classZA.field7131) {
               classZA.field7135[(classZA.field7127 += -839116449) * 1052148383 - 1] = var0;
            } else {
               if (var0.length == 10000) {
                  if (var1 != 2022759572) {
                     throw new IllegalStateException();
                  }

                  if (-2070154251 * classZA.field7138 < classZA.field7132 * 273271753) {
                     if (var1 != 2022759572) {
                        throw new IllegalStateException();
                     }

                     classZA.field7126[(classZA.field7138 += -618995619) * -2070154251 - 1] = var0;
                     return;
                  }
               }

               if (var0.length == 30000) {
                  if (var1 != 2022759572) {
                     throw new IllegalStateException();
                  }

                  if (classZA.field7129 * 1155531987 < -1438188161 * classZA.field7128) {
                     if (var1 != 2022759572) {
                        throw new IllegalStateException();
                     }

                     classZA.field7136[(classZA.field7129 += 1255076699) * 1155531987 - 1] = var0;
                     return;
                  }
               }

               if (null != classZA.field7142) {
                  if (var1 != 2022759572) {
                     throw new IllegalStateException();
                  }

                  for (int var3 = 0; var3 < classSQ.field6026.length; var3++) {
                     if (var1 != 2022759572) {
                        throw new IllegalStateException();
                     }

                     if (classSQ.field6026[var3] == var0.length) {
                        if (var1 != 2022759572) {
                           throw new IllegalStateException();
                        }

                        if (classZA.field7141[var3] < classZA.field7142[var3].length) {
                           if (var1 != 2022759572) {
                              throw new IllegalStateException();
                           }

                           classZA.field7142[var3][classZA.field7141[var3]++] = var0;
                           return;
                        }
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ua.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ek")
   static final void method11300(int var0, int var1, int var2, int var3) {
      try {
         classLJ.method7159(classCQ.field1218, var0, var1, var2, (byte)17);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ua.ek(" + ')');
      }
   }

   public classUA(classUY var1) {
      this(new classWX(var1));
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("az")
   public int method11296(int var1, int var2) {
      try {
         return this.field6272.vmethod641(var1, (byte)8);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ua.az(" + 41);
      }
   }
}
