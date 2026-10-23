import java.io.File;
import java.io.RandomAccessFile;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bj")
public class classBJ {
   @ObfuscatedSignature(descriptor = "Lzf;")
   @ObfuscatedName("kg")
   static classZF field483;
   @ObfuscatedName("af")
   int field478 = 651105091;
   @ObfuscatedName("ae")
   int[] field484;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field479 = 12;
   @ObfuscatedName("ag")
   long[] field485;
   @ObfuscatedSignature(descriptor = "Lak;")
   @ObfuscatedName("oz")
   static classAK field481;
   @ObfuscatedName("ab")
   Object[] field486;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   public static final int field480 = 2;
   @ObfuscatedSignature(descriptor = "Lbl;")
   @ObfuscatedName("az")
   Script field482;

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("aj")
   static int method1120(int var0, int var1, byte var2) {
      try {
         for (int var3 = 0; var3 < 8; var3++) {
            if (var2 != 1) {
               throw new IllegalStateException();
            }

            if (var1 <= 30 + var0) {
               return var3;
            }

            byte var10001;
            label35: {
               var0 += 30;
               if (1 != var3) {
                  if (var3 != 3) {
                     var10001 = 5;
                     break label35;
                  }

                  if (var2 != 1) {
                     throw new IllegalStateException();
                  }
               }

               var10001 = 20;
            }

            var0 += var10001;
         }

         return 0;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bj.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/io/File;")
   @ObfuscatedName("az")
   static File method1118(String var0, int var1) {
      try {
         if (!classMT.field4578) {
            if (var1 >= 642137221) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException("");
            }
         } else {
            File var2 = (File)classMT.field4579.get(var0);
            if (null != var2) {
               if (var1 >= 642137221) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               File var3 = new File(classMT.field4584, var0);
               RandomAccessFile var4 = null;

               try {
                  File var5 = new File(var3.getParent());
                  if (!var5.exists()) {
                     if (var1 >= 642137221) {
                        throw new IllegalStateException();
                     } else {
                        throw new RuntimeException("");
                     }
                  } else {
                     var4 = new RandomAccessFile(var3, "rw");
                     int var6 = var4.read();
                     var4.seek(0L);
                     var4.write(var6);
                     var4.seek(0L);
                     var4.close();
                     classMT.field4579.put(var0, var3);
                     return var3;
                  }
               } catch (Exception var8) {
                  try {
                     if (null != var4) {
                        var4.close();
                        Object var10 = null;
                     }
                  } catch (Exception var7) {
                  }

                  throw new RuntimeException();
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "bj.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("aq")
   static void method1121(boolean var0, int var1) {
      try {
         if (!classOE.field4841.method2138(1985617997) && !client.method2142(classOE.field4841, (byte)109)) {
            if (!classOE.field4841.method2140(-1618112219)) {
               classBF.field396 = classKK.field3819;
               classBF.field397 = classKK.field3778;
               classBF.field398 = classKK.field3821;
               classAC.method321(2, 2097818110);
               if (var0) {
                  classBF.method1067("");
               }

               label69: {
                  if (classBF.field389 != null) {
                     if (var1 != -347989319) {
                        throw new IllegalStateException();
                     }

                     if (!classBF.field389.isEmpty()) {
                        if (var1 != -347989319) {
                           throw new IllegalStateException();
                        }
                        break label69;
                     }
                  }

                  if (classAB.field122.method2782((byte)8) != null) {
                     if (var1 != -347989319) {
                        throw new IllegalStateException();
                     }

                     classBF.field389 = classAB.field122.method2782((byte)8);
                     client.method1779(-1);
                     client.field833 = true;
                  } else {
                     client.field833 = false;
                  }
               }

               if (client.field833) {
                  if (var1 != -347989319) {
                     throw new IllegalStateException();
                  }

                  if (classBF.field389 != null) {
                     if (var1 != -347989319) {
                        throw new IllegalStateException();
                     }

                     if (!classBF.field389.isEmpty()) {
                        if (var1 != -347989319) {
                           return;
                        }

                        classBF.field402 = -878854915;
                        return;
                     }
                  }
               }

               classBF.field402 = 0;
               return;
            }

            if (var1 != -347989319) {
               throw new IllegalStateException();
            }
         }

         classAC.method321(10, 2047579352);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bj.aq(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldz;I)Lct;")
   @ObfuscatedName("kj")
   public static Player method1119(WorldView var0, int var1) {
      if (var0 == null) {
         var0.method3754(var1);
      }

      return (Player)IndexedObjectSet.method13449(var0.field1692, var1);
   }

   classBJ() {
   }
}
