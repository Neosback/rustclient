import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kn")
public class classKN {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field3965 = 128;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field3966 = 2048;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field3968 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final int field3967 = 27;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field3964 = 64;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field3963 = 0;

   classKN() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("at")
   static long method6538(int var0) {
      try {
         return 1026637691757090767L * client.field962;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "kn.at(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;)I")
   @ObfuscatedName("yh")
   public static int method6537(classNU var0, classNT var1) {
      if (var0 == null) {
         var0.method8175();
      }

      classNY var2 = var1.field4745;
      int var3 = 4096 + var0.field4753[-1057694491 * var1.field4732] * var0.field4769[-1057694491 * var1.field4732] >> 13;
      var3 = var3 * var3 + 16384 >> 15;
      var3 = var1.field4735 * -983353503 * var3 + 16384 >> 15;
      var3 = 1238197527 * var0.field4752 * var3 + 128 >> 8;
      if (-939533713 * var2.field4791 > 0) {
         var3 = (int)(var3 * Math.pow(0.5, -939533713 * var2.field4791 * (1.953125E-5 * (934628419 * var1.field4731))) + 0.5);
      }

      if (var2.field4796 != null) {
         int var4 = 1539106655 * var1.field4736;
         int var5 = var2.field4796[1 + var1.field4734 * 1565767631];
         if (var1.field4734 * 1565767631 < var2.field4796.length - 2) {
            int var6 = (var2.field4796[1565767631 * var1.field4734] & 255) << 8;
            int var7 = (var2.field4796[var1.field4734 * 1565767631 + 2] & 255) << 8;
            var5 += (var2.field4796[1565767631 * var1.field4734 + 3] - var5) * (var4 - var6) / (var7 - var6);
         }

         var3 = var5 * var3 + 32 >> 6;
      }

      if (67131625 * var1.field4727 > 0 && var2.field4797 != null) {
         int var11 = var1.field4727 * 67131625;
         int var12 = var2.field4797[1 + var1.field4726 * -463641621];
         if (-463641621 * var1.field4726 < var2.field4797.length - 2) {
            int var13 = (var2.field4797[var1.field4726 * -463641621] & 255) << 8;
            int var14 = (var2.field4797[-463641621 * var1.field4726 + 2] & 255) << 8;
            var12 += (var11 - var13) * (var2.field4797[3 + var1.field4726 * -463641621] - var12) / (var14 - var13);
         }

         var3 = var3 * var12 + 32 >> 6;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Ldh;II)V")
   @ObfuscatedName("bq")
   static final void method6539(WorldView var0, Actor var1, int var2, int var3) {
      Actor.method3291(var0, var1, var2);

      try {
         if (var1.field1466) {
            if (var3 != -1128559099) {
               throw new IllegalStateException();
            }
         } else {
            int var8 = 677773095 * var1.field1493 - 1457033841 * var1.field1485 & 2047;
            if (var8 != 0) {
               byte var9 = -1;
               boolean var10 = true;
               var1.field1469 += -1813526965;
               byte var10000;
               if (var8 > 1024) {
                  if (var3 != -1128559099) {
                     throw new IllegalStateException();
                  }

                  var10000 = -1;
               } else {
                  var10000 = 1;
               }

               byte var11;
               boolean var12;
               label465: {
                  var11 = var10000;
                  var1.field1485 = var1.field1485 + -909895021 * var1.field1470 * var11 * 573632145;
                  var12 = true;
                  if (var8 >= -909895021 * var1.field1470) {
                     if (var3 != -1128559099) {
                        throw new IllegalStateException();
                     }

                     if (var8 <= 2048 - var1.field1470 * -909895021) {
                        break label465;
                     }

                     if (var3 != -1128559099) {
                        Actor.method3289(var0, var1, var2);
                        return;
                     }
                  }

                  var1.field1485 = -1169438953 * var1.field1493;
                  var12 = false;
               }

               if (-909895021 * var1.field1470 > 0) {
                  if (var3 != -1128559099) {
                     throw new IllegalStateException();
                  }

                  label454:
                  if (var1.method3091(-1521983044) == var1.field1434 * 1457308725) {
                     if (var3 != -1128559099) {
                        throw new IllegalStateException();
                     }

                     if (var1.field1469 * 2075939683 <= 25) {
                        if (!var12) {
                           break label454;
                        }

                        if (var3 != -1128559099) {
                           throw new IllegalStateException();
                        }
                     }

                     if (-1 == var11) {
                        if (var3 != -1128559099) {
                           throw new IllegalStateException();
                        }

                        if (1201940803 * var1.field1442 != -1) {
                           if (var3 != -1128559099) {
                              Actor.method3289(var0, var1, var2);
                              return;
                           }

                           var1.method3096(1201940803 * var1.field1442, -2066439426);
                           break label454;
                        }
                     }

                     if (1 == var11) {
                        if (var3 != -1128559099) {
                           throw new IllegalStateException();
                        }

                        if (var1.field1455 * -47525405 != -1) {
                           if (var3 != -1128559099) {
                              throw new IllegalStateException();
                           }

                           var1.method3096(var1.field1455 * -47525405, -1088755749);
                           break label454;
                        }
                     }

                     var1.method3096(var1.field1443 * -1657575887, -1009977976);
                  }
               }

               var1.field1485 = 573632145 * (var1.field1485 * 1457033841 & 2047);
            } else {
               if (var1.field1473) {
                  var1.method3248((byte)-34);
                  var1.field1473 = false;
               }

               var1.field1469 = 0;
            }
         }

         classKB.method6491(var0, var1, (byte)5);
         Actor.method3289(var0, var1, var2);
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "kn.bq(" + ')');
      }
   }

   static {
      Math.sqrt(8192.0);
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/Object;")
   @ObfuscatedName("cc")
   static Object method6540(int var0, int var1) {
      try {
         if (-1 == var0) {
            if (var1 == 16777215) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            return classAB.method302((classYY)classOF.method8436(classYY.method13822((byte)54), var0, 635415869), 1465889783);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kn.cc(" + ')');
      }
   }
}
