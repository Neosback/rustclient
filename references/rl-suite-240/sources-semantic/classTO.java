import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("to")
public class classTO implements classSD {
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6175 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final String field6177 = "_";
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field6176 = 15;

   @ObfuscatedSignature(descriptor = "(Lnc;I)V")
   @ObfuscatedName("vu")
   public static void method10989(classNC var0, int var1) {
      if (var0 == null) {
         var0.method7834(var1);
      } else {
         int var2 = var0.field4644.method13172(-1379519921);
         var0.field4654[var1] = var0.field4654[var1] + var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)Z")
   @ObfuscatedName("rb")
   public static boolean method10990(NPCComposition var0) {
      return var0.field5328 != null && null != var0.field5329;
   }

   @ObfuscatedSignature(descriptor = "(IZI)[B")
   @ObfuscatedName("az")
   public static byte[] method10987(int var0, boolean var1, int var2) {
      try {
         synchronized (classZA.field7125) {
            label207: {
               if (100 != var0) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (var0 >= 100) {
                     break label207;
                  }

                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (!var1) {
                     break label207;
                  }
               }

               if (1951739973 * classZA.field7133 > 0) {
                  byte[] var4 = classZA.field7134[(classZA.field7133 -= 1490182797) * 1951739973];
                  classZA.field7134[1951739973 * classZA.field7133] = null;
                  return var4;
               }
            }

            label198: {
               if (var0 != 5000) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (var0 >= 5000) {
                     break label198;
                  }

                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (!var1) {
                     break label198;
                  }
               }

               if (1052148383 * classZA.field7127 > 0) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  byte[] var9 = classZA.field7135[(classZA.field7127 -= -839116449) * 1052148383];
                  classZA.field7135[classZA.field7127 * 1052148383] = null;
                  return var9;
               }
            }

            label189: {
               if (var0 != 10000) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (var0 >= 10000) {
                     break label189;
                  }

                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (!var1) {
                     break label189;
                  }

                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }
               }

               if (-2070154251 * classZA.field7138 > 0) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  byte[] var10 = classZA.field7126[(classZA.field7138 -= -618995619) * -2070154251];
                  classZA.field7126[classZA.field7138 * -2070154251] = null;
                  return var10;
               }
            }

            label179: {
               if (30000 != var0) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (var0 >= 30000 || !var1) {
                     break label179;
                  }
               }

               if (classZA.field7129 * 1155531987 > 0) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  byte[] var11 = classZA.field7136[(classZA.field7129 -= 1255076699) * 1155531987];
                  classZA.field7136[classZA.field7129 * 1155531987] = null;
                  return var11;
               }
            }

            if (classZA.field7142 != null) {
               if (var2 != 1154989421) {
                  throw new IllegalStateException();
               }

               for (int var12 = 0; var12 < classSQ.field6026.length; var12++) {
                  if (var2 != 1154989421) {
                     throw new IllegalStateException();
                  }

                  if (classSQ.field6026[var12] != var0) {
                     if (var2 != 1154989421) {
                        throw new IllegalStateException();
                     }

                     if (var0 >= classSQ.field6026[var12]) {
                        continue;
                     }

                     if (var2 != 1154989421) {
                        throw new IllegalStateException();
                     }

                     if (!var1) {
                        continue;
                     }

                     if (var2 != 1154989421) {
                        throw new IllegalStateException();
                     }
                  }

                  if (classZA.field7141[var12] > 0) {
                     if (var2 != 1154989421) {
                        throw new IllegalStateException();
                     }

                     byte[] var5 = classZA.field7142[var12][--classZA.field7141[var12]];
                     classZA.field7142[var12][classZA.field7141[var12]] = null;
                     return var5;
                  }
               }
            }

            if (var1) {
               if (var2 != 1154989421) {
                  throw new IllegalStateException();
               }

               if (null != classSQ.field6026) {
                  for (int var13 = 0; var13 < classSQ.field6026.length; var13++) {
                     if (var2 != 1154989421) {
                        throw new IllegalStateException();
                     }

                     if (var0 <= classSQ.field6026[var13]) {
                        if (var2 != 1154989421) {
                           throw new IllegalStateException();
                        }

                        if (classZA.field7141[var13] < classZA.field7142[var13].length) {
                           if (var2 != 1154989421) {
                              throw new IllegalStateException();
                           }

                           return new byte[classSQ.field6026[var13]];
                        }
                     }
                  }
               }
            }
         }

         return new byte[var0];
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "to.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvy;)D")
   @ObfuscatedName("us")
   public static double method10986(classVY var0) {
      return var0.field6640;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpr;ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ms")
   public static String method10988(StructComposition var0, int var1, String var2) {
      return classFS.method4998(var0.field5385, var1, var2, (byte)99);
   }
}
