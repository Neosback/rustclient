import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("eb")
public class classEB {
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field1719 = 17;
   @ObfuscatedSignature(descriptor = "Lvt;")
   @ObfuscatedName("pt")
   static classVT field1725;
   @ObfuscatedName("ae")
   int field1717;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field1723 = 29;
   @ObfuscatedSignature(descriptor = "Lvb;")
   @ObfuscatedName("pw")
   static classVB field1724;
   @ObfuscatedName("af")
   int field1718;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field1720 = 31;
   @ObfuscatedName("az")
   int field1722;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   public static final int field1721 = 37;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)I")
   @ObfuscatedName("ci")
   public static int method3819(Actor var0) {
      if (var0 == null) {
         var0.method3265();
      }

      return 2030011479 * var0.field1461.field629;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Ldh;I)V")
   @ObfuscatedName("bs")
   static final void method3820(WorldView var0, Actor var1, int var2) {
      try {
         int var3;
         boolean var4;
         label157: {
            var3 = -1;
            var4 = false;
            if (var1.method3162(1857490025)) {
               if (var2 >= -1317666184) {
                  throw new IllegalStateException();
               }

               if (var1.method3216(1542551662)) {
                  var3 = var1.method3270(162820460);
                  var4 = Actor.method3236(var1, 1386776059);
                  var1.method3170(-983329598);
                  break label157;
               }
            }

            if (var1.method3165(1718590827)) {
               if (var2 >= -1317666184) {
                  return;
               }

               if (var1.method3222((byte)55)) {
                  if (var2 >= -1317666184) {
                     return;
                  }

                  int var15 = classKY.method6656(var1.method3258(1254222995) - var0.field1706 * -1444178379, 744949980);
                  int var17 = classKY.method6656(var1.method3260(695894775) - -351145363 * var0.field1707, -1642766449);
                  int var18 = classKY.method6656(var1.method3258(1254222995) + (var1.method3263((byte)31) - 1) - -1444178379 * var0.field1706, 791326245);
                  int var21 = classKY.method6656(var1.method3260(-423127899) + (Actor.method3266(var1, (byte)-83) - 1) - var0.field1707 * -351145363, 882389400);
                  int var10 = (var15 + var18) / 2;
                  int var11 = (var21 + var17) / 2;
                  int var12 = classBT.method1244(var1, var0, var0, var10, var11, (byte)1);
                  int var22;
                  if (-1 == var12) {
                     if (var2 >= -1317666184) {
                        throw new IllegalStateException();
                     }

                     var22 = 677773095 * var1.field1493;
                  } else {
                     var22 = var12;
                  }

                  var3 = var22;
                  var4 = Actor.method3236(var1, -1123446094);
                  var1.method3243(false, 51205404);
                  break label157;
               }
            }

            if (var1.method3163(-685442936)) {
               if (var2 >= -1317666184) {
                  throw new IllegalStateException();
               }

               WorldView var5 = null;
               int var6 = -1;
               int var7 = -1;
               classYB var8 = null;
               if (var1.method3244(-1217021755) == classLJ.field4175) {
                  if (var2 >= -1317666184) {
                     throw new IllegalStateException();
                  }

                  var5 = classHB.method6020(var1.method3252((short)148), client.field815, -139095293);
                  var8 = (classYB)IndexedObjectSet.method13449(var5.field1696, var1.method3252((short)148));
               } else if (var1.method3244(1812366758) == classLJ.field4174) {
                  if (var2 >= -1317666184) {
                     throw new IllegalStateException();
                  }

                  int var9 = var1.method3252((short)148);
                  var5 = classQB.method9481(var9, client.field815, -1248209298);
                  var8 = (classYB)IndexedObjectSet.method13449(var5.field1692, var9);
               } else if (var1.method3244(-1853778300) == classLJ.field4173) {
                  int var19 = var1.method3252((short)148);
                  var5 = classIS.field3051;
                  var8 = (classYB)var5.field1698.method13637(var19);
               }

               if (var8 != null) {
                  if (var2 >= -1317666184) {
                     throw new IllegalStateException();
                  }

                  var6 = var8.vmethod368(311606126);
                  var7 = var8.vmethod371(-1485838393);
                  int var20 = classBT.method1244(var1, var0, var5, var6, var7, (byte)1);
                  int var10000;
                  if (var20 == -1) {
                     if (var2 >= -1317666184) {
                        throw new IllegalStateException();
                     }

                     var10000 = 677773095 * var1.field1493;
                  } else {
                     var10000 = var20;
                  }

                  var3 = var10000;
               } else if (var1.method3256((byte)-20) != -1) {
                  if (var2 >= -1317666184) {
                     throw new IllegalStateException();
                  }

                  var3 = var1.method3256((byte)-3);
               } else if (var1.field1473) {
                  var1.method3248((byte)23);
                  var1.field1473 = false;
               }
            }
         }

         if (-1 == var3 && -1798356091 * var1.field1471 > 0) {
            if (var2 >= -1317666184) {
               throw new IllegalStateException();
            }

            if (var1.method3213((byte)1)) {
               var3 = Actor.method3205(var1, 1593126723);
            }
         }

         int var10002;
         if (var3 != -1) {
            if (var2 >= -1317666184) {
               throw new IllegalStateException();
            }

            var10002 = var3;
         } else {
            var10002 = 677773095 * var1.field1493;
         }

         var1.field1493 = -1685749609 * var10002;
         if (var4) {
            if (var2 >= -1317666184) {
               return;
            }

            if (-909895021 * var1.field1470 > 0) {
               if (var2 >= -1317666184) {
                  throw new IllegalStateException();
               }

               var1.field1485 = var1.field1493 * -1169438953;
            }
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "eb.bs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;B)I")
   @ObfuscatedName("al")
   public static int method3817(CharSequence var0, byte var1) {
      try {
         int var2 = var0.length();
         int var3 = 0;

         for (int var4 = 0; var4 < var2; var4++) {
            if (var1 <= 13) {
               throw new IllegalStateException();
            }

            var3 = (var3 << 5) - var3 + var0.charAt(var4);
         }

         return var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "eb.al(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(CB)Z")
   @ObfuscatedName("ay")
   public static boolean method3818(char var0, byte var1) {
      try {
         if (var0 >= '0') {
            if (var1 == 0) {
               throw new IllegalStateException();
            }

            if (var0 <= '9') {
               if (var1 == 0) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "eb.ay(" + ')');
      }
   }

   classEB() {
   }
}
