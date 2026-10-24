import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ys")
public class classYS implements Comparator {
   @ObfuscatedName("az")
   final boolean field7073;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field7074 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field7076 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field7075 = 76;

   @ObfuscatedSignature(descriptor = "(Lrr;Lrr;)I")
   @ObfuscatedName("af")
   int method13660(Nameable var1, Nameable var2) {
      return this.field7073
         ? classAAE.method178(var1.method10216((byte)16), var2.method10216((byte)16), 1600959519)
         : classAAE.method178(var2.method10216((byte)16), var1.method10216((byte)16), 1600959519);
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Lrr;I)I")
   @ObfuscatedName("az")
   int method13661(Nameable var1, Nameable var2, int var3) {
      try {
         int var10000;
         if (this.field7073) {
            if (var3 >= -1886357123) {
               throw new IllegalStateException();
            }

            var10000 = classAAE.method178(var1.method10216((byte)16), var2.method10216((byte)16), 1600959519);
         } else {
            var10000 = classAAE.method178(var2.method10216((byte)16), var1.method10216((byte)16), 1600959519);
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ys.az(" + 41);
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method13661((Nameable)var1, (Nameable)var2, -1974561099);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ys.compare(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lys;Ljava/lang/Object;)Z")
   @ObfuscatedName("mt")
   public static boolean method13664(classYS var0, Object var1) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return var0.equals(var1);
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ys.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method13662(Object var1, Object var2) {
      return this.method13661((Nameable)var1, (Nameable)var2, -2147024240);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method13665(Object var1) {
      return super.equals(var1);
   }

   public classYS(boolean var1) {
      this.field7073 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method13663(Object var1, Object var2) {
      return this.method13661((Nameable)var1, (Nameable)var2, -2097789335);
   }

   @ObfuscatedSignature(descriptor = "([BI)V")
   @ObfuscatedName("aj")
   static void method13666(byte[] var0, int var1) {
      try {
         classXY var2 = new classXY(var0);
         var2.field6953 = (var0.length - 2) * -1095856699;
         classYL.field6999 = var2.method13099(1707980289) * 1826761615;
         classYL.field7000 = new int[1616155503 * classYL.field6999];
         classYL.field7001 = new int[classYL.field6999 * 1616155503];
         EntityOps.field5369 = new int[1616155503 * classYL.field6999];
         classGG.field2576 = new int[1616155503 * classYL.field6999];
         classYL.field7002 = new byte[classYL.field6999 * 1616155503][];
         var2.field6953 = -1095856699 * (var0.length - 7 - classYL.field6999 * 44342136);
         classYL.field6998 = var2.method13099(1410422866) * 1527476973;
         classYL.field6997 = var2.method13099(-767132224) * 1330952715;
         int var3 = (var2.method13095(-346779531) & 0xFF) + 1;

         for (int var4 = 0; var4 < classYL.field6999 * 1616155503; var4++) {
            if (var1 != -1108124092) {
               return;
            }

            classYL.field7000[var4] = var2.method13099(-436762751);
         }

         for (int var15 = 0; var15 < classYL.field6999 * 1616155503; var15++) {
            if (var1 != -1108124092) {
               return;
            }

            classYL.field7001[var15] = var2.method13099(-433462387);
         }

         for (int var16 = 0; var16 < classYL.field6999 * 1616155503; var16++) {
            if (var1 != -1108124092) {
               throw new IllegalStateException();
            }

            EntityOps.field5369[var16] = var2.method13099(-715078765);
         }

         for (int var17 = 0; var17 < classYL.field6999 * 1616155503; var17++) {
            if (var1 != -1108124092) {
               throw new IllegalStateException();
            }

            classGG.field2576[var17] = var2.method13099(-996484208);
         }

         var2.field6953 = (var0.length - 7 - classYL.field6999 * 44342136 - (var3 - 1) * 3) * -1095856699;
         classRB.field5637 = new int[var3];

         for (int var18 = 1; var18 < var3; var18++) {
            if (var1 != -1108124092) {
               return;
            }

            classRB.field5637[var18] = var2.method13105(-758448158);
            if (0 == classRB.field5637[var18]) {
               if (var1 != -1108124092) {
                  throw new IllegalStateException();
               }

               classRB.field5637[var18] = 1;
            }
         }

         var2.field6953 = 0;

         for (int var19 = 0; var19 < classYL.field6999 * 1616155503; var19++) {
            if (var1 != -1108124092) {
               return;
            }

            int var5 = EntityOps.field5369[var19];
            int var6 = classGG.field2576[var19];
            int var7 = var5 * var6;
            byte[] var8 = new byte[var7];
            classYL.field7002[var19] = var8;
            int var9 = var2.method13095(-346779531);
            boolean var10000;
            if ((var9 & 1) == 1) {
               if (var1 != -1108124092) {
                  return;
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var10 = var10000;
            if (2 == (var9 & 2)) {
               if (var1 != -1108124092) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var11 = var10000;
            if (!var10) {
               if (var1 != -1108124092) {
                  throw new IllegalStateException();
               }

               for (int var20 = 0; var20 < var7; var20++) {
                  if (var1 != -1108124092) {
                     throw new IllegalStateException();
                  }

                  var8[var20] = var2.method13097((byte)17);
               }
            } else {
               for (int var12 = 0; var12 < var5; var12++) {
                  if (var1 != -1108124092) {
                     return;
                  }

                  for (int var13 = 0; var13 < var6; var13++) {
                     if (var1 != -1108124092) {
                        throw new IllegalStateException();
                     }

                     var8[var12 + var5 * var13] = var2.method13097((byte)17);
                  }
               }
            }

            if (var11) {
               if (var1 != -1108124092) {
                  throw new IllegalStateException();
               }

               var2.field6953 += -1095856699 * var7;
            }
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "ys.aj(" + ')');
      }
   }
}
