import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kv")
public class classKV implements classXZ {
   @ObfuscatedName("ag")
   public final int field4036;
   @ObfuscatedSignature(descriptor = "Lkv;")
   @ObfuscatedName("az")
   public static final classKV field4032 = new classKV(0);
   @ObfuscatedSignature(descriptor = "Lkv;")
   @ObfuscatedName("ae")
   public static final classKV field4033 = new classKV(2);
   @ObfuscatedSignature(descriptor = "Lkv;")
   @ObfuscatedName("ab")
   public static final classKV field4035 = new classKV(3);
   @ObfuscatedSignature(descriptor = "Lkv;")
   @ObfuscatedName("af")
   public static final classKV field4034 = new classKV(1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field4036 * 1473463690;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field4036 * 1946452601;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kv.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field4036 * 1946452601;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field4036 * 1946452601;
   }

   @ObfuscatedSignature(descriptor = "([BB)[I")
   @ObfuscatedName("af")
   public static int[] method6611(byte[] var0, byte var1) {
      try {
         if (null != var0) {
            if (var1 != 0) {
               throw new IllegalStateException();
            }

            if (var0.length != 0) {
               if (var1 != 0) {
                  throw new IllegalStateException();
               }

               if (var0.length <= 8) {
                  int[] var2 = new int[var0.length];
                  int var3 = 0;

                  while (true) {
                     if (var3 >= var0.length) {
                        return var2;
                     }

                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     if (var0[var3] < 0) {
                        break;
                     }

                     if (var1 != 0) {
                        throw new IllegalStateException();
                     }

                     if (var0[var3] >= classZM.field7223.length) {
                        if (var1 != 0) {
                           throw new IllegalStateException();
                        }
                        break;
                     }

                     var2[var3] = classZM.field7223[var0[var3]];
                     var3++;
                  }

                  return null;
               }

               if (var1 != 0) {
                  throw new IllegalStateException();
               }
            }
         }

         return null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "kv.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;I)I")
   @ObfuscatedName("ag")
   public static int method6612(classTE var0, int var1) {
      try {
         classCQ.method2564(var0, -2007890828);
         byte var2 = 0;
         int var3 = var0.method10797(-549521539);
         int var4 = -1;
         if (var0.field6126 == classYY.field7109) {
            if (var1 >= 900828305) {
               throw new IllegalStateException();
            }

            int[] var5 = var0.method10791(590874125);

            for (int var6 = var2; var6 < var3; var6++) {
               if (var1 >= 900828305) {
                  throw new IllegalStateException();
               }

               if (var4 == -1 || var5[var6] < var5[var4]) {
                  var4 = var6;
               }
            }
         } else if (var0.field6126 == classYY.field7113) {
            if (var1 >= 900828305) {
               throw new IllegalStateException();
            }

            long[] var10 = var0.method10793(-1962241304);

            for (int var12 = var2; var12 < var3; var12++) {
               if (var1 >= 900828305) {
                  throw new IllegalStateException();
               }

               if (-1 != var4) {
                  if (var10[var12] >= var10[var4]) {
                     continue;
                  }

                  if (var1 >= 900828305) {
                     throw new IllegalStateException();
                  }
               }

               var4 = var12;
            }
         } else if (classYY.field7111 == var0.field6126) {
            if (var1 >= 900828305) {
               throw new IllegalStateException();
            }

            String var11 = null;
            Object[] var13 = var0.method10796(960228369);

            for (int var7 = var2; var7 < var3; var7++) {
               if (var1 >= 900828305) {
                  throw new IllegalStateException();
               }

               String var8 = (String)var13[var7];
               if (var4 != -1) {
                  if (var1 >= 900828305) {
                     throw new IllegalStateException();
                  }

                  if (null == var8) {
                     continue;
                  }

                  if (var1 >= 900828305) {
                     throw new IllegalStateException();
                  }

                  if (var8.compareTo(var11) >= 0) {
                     continue;
                  }

                  if (var1 >= 900828305) {
                     throw new IllegalStateException();
                  }
               }

               var4 = var7;
               var11 = var8;
            }
         }

         return var4;
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "kv.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)J")
   @ObfuscatedName("ag")
   static long method6610(int var0, int var1, int var2, int var3, int var4) {
      try {
         long var5 = 1073741824L;
         long var7 = -1073741824L;
         if (var2 != var0) {
            if (var4 == 1985051429) {
               throw new IllegalStateException();
            } else {
               long var9 = var3 - var1;
               long var11 = (var9 << 15) / (var2 - var0);
               return Math.min(1073741824L, Math.max(-1073741824L, var11));
            }
         } else if (var1 > var3) {
            if (var4 == 1985051429) {
               throw new IllegalStateException();
            } else {
               return -1073741824L;
            }
         } else {
            return 1073741824L;
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "kv.ag(" + ')');
      }
   }

   classKV(int var1) {
      this.field4036 = -1997125687 * var1;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIB)I")
   @ObfuscatedName("cj")
   static final int method6613(WorldView var0, int var1, int var2, int var3, byte var4) {
      try {
         return var0.method3794(var1, var2, var3, (byte)0);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "kv.cj(" + 41);
      }
   }
}
