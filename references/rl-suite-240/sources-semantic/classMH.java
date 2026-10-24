import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mh")
public abstract class classMH {
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field4503 = 41;
   @ToRemove(unused = "true")
   @ObfuscatedName("cc")
   static final int field4504 = 160;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field4502 = 7;

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("ba")
   static final void method7615(int var0, int var1, short var2) {
      try {
         var0 = classKC.method6497(var0, -181998943);
         int var3 = client.field915.method11500((short)-28167);
         int var4 = client.field797.method11500((short)-4430);
         if (var3 < var0) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var3 += WorldMapRenderer.field2697 * -463609277 * (var0 - var3) / 1000 + classHE.field2779 * 836632525;
            if (var3 > var0) {
               if (var2 == 255) {
                  throw new IllegalStateException();
               }

               var3 = var0;
            }
         } else if (var3 > var0) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var3 -= -463609277 * WorldMapRenderer.field2697 * (var3 - var0) / 1000 + classHE.field2779 * 836632525;
            if (var3 < var0) {
               if (var2 == 255) {
                  throw new IllegalStateException();
               }

               var3 = var0;
            }
         }

         var1 &= classCS.method2567(-1520924805);
         int var5 = var1 - var4;
         short var7 = 8192;
         if (var5 > var7) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var5 -= classKG.method6508(-2085028226);
         } else {
            short var9 = 8192;
            if (var5 < -var9) {
               var5 += classKG.method6508(199047527);
            }
         }

         if (var5 > 0) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var4 += var5 * WorldMapRenderer.field2697 * -463609277 / 1000 + 836632525 * classHE.field2779;
         } else if (var5 < 0) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var4 -= 836632525 * classHE.field2779 + -var5 * -463609277 * WorldMapRenderer.field2697 / 1000;
         }

         int var8 = var1 - var4;
         short var10 = 8192;
         if (var8 > var10) {
            if (var2 == 255) {
               throw new IllegalStateException();
            }

            var8 -= classKG.method6508(-459448143);
         } else {
            short var12 = 8192;
            if (var8 < -var12) {
               if (var2 == 255) {
                  throw new IllegalStateException();
               }

               var8 += classKG.method6508(-18800356);
            }
         }

         label114: {
            label119: {
               if (var8 < 0) {
                  if (var2 == 255) {
                     throw new IllegalStateException();
                  }

                  if (var5 > 0) {
                     break label119;
                  }

                  if (var2 == 255) {
                     throw new IllegalStateException();
                  }
               }

               if (var8 <= 0 || var5 >= 0) {
                  break label114;
               }

               if (var2 == 255) {
                  throw new IllegalStateException();
               }
            }

            var4 = var1;
         }

         classUP.method11494(client.field915, var3, -1499893710);
         classUP.method11494(client.field797, var4, -1273130461);
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "mh.ba(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   public abstract void vmethod530(byte var1);

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ip")
   public int vmethod536(int var1, int var2) {
      return this.vmethod535(var1, var2, -1162884564);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   public abstract int vmethod535(int var1, int var2, int var3);

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public abstract void vmethod532();

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ag")
   public abstract int vmethod534(int var1, int var2);

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("az")
   public static int method7613(int var0, int var1, byte var2) {
      try {
         return (var0 << 8) + var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mh.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   public abstract int vmethod533(int var1, int var2);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lmh;")
   @ObfuscatedName("ac")
   public static classMH method7612() {
      try {
         return new classMG();
      } catch (Throwable var1) {
         return new classMQ();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)V")
   @ObfuscatedName("ur")
   public static void method7614(classMA var0) {
      if (var0 == null) {
         var0.method7511();
      }

      var0.field4456 = null;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("gv")
   static final int method7616(byte var0) {
      try {
         return classKF.field3569.method3438(-1960183053);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "mh.gv(" + 41);
      }
   }

   classMH() {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   public abstract void vmethod531();
}
