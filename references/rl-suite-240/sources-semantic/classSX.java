import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sx")
public class classSX {
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hh")
   static classVP field6095;
   @ObfuscatedSignature(descriptor = "Lds;")
   @ObfuscatedName("et")
   static classDS field6094;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6092 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("bs")
   public static final int field6093 = 72;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Lct;")
   @ObfuscatedName("ag")
   public static Player method10743(int var0, classCL var1) {
      Player var2 = null;

      for (WorldView var4 : var1) {
         Player var5 = (Player)IndexedObjectSet.method13449(var4.field1692, var0);
         if (null != var5) {
            if (!var4.method3734(-2087374717)) {
               return var5;
            }

            var2 = var5;
         }
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvh;I)I")
   @ObfuscatedName("nn")
   public static int method10753(classVH var0, int var1) {
      return var0 == null ? var0.method12019(var1, var1) : var0.field6510[var1 & 15];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Ldn;")
   @ObfuscatedName("aa")
   public static NPC method10747(int var0, classCL var1) {
      for (WorldView var3 : var1) {
         NPC var4 = (NPC)IndexedObjectSet.method13449(var3.field1696, var0);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Lct;")
   @ObfuscatedName("as")
   public static Player method10744(int var0, classCL var1) {
      Player var2 = null;

      for (WorldView var4 : var1) {
         Player var5 = (Player)IndexedObjectSet.method13449(var4.field1692, var0);
         if (null != var5) {
            if (!var4.method3734(-2087374717)) {
               return var5;
            }

            var2 = var5;
         }
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Lct;")
   @ObfuscatedName("ac")
   public static Player method10745(int var0, classCL var1) {
      Player var2 = null;

      for (WorldView var4 : var1) {
         Player var5 = (Player)IndexedObjectSet.method13449(var4.field1692, var0);
         if (null != var5) {
            if (!var4.method3734(-2087374717)) {
               return var5;
            }

            var2 = var5;
         }
      }

      return var2;
   }

   classSX() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Lct;")
   @ObfuscatedName("ax")
   public static Player method10746(int var0, classCL var1) {
      Player var2 = null;

      for (WorldView var4 : var1) {
         Player var5 = (Player)IndexedObjectSet.method13449(var4.field1692, var0);
         if (null != var5) {
            if (!var4.method3734(-2087374717)) {
               return var5;
            }

            var2 = var5;
         }
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Ldn;")
   @ObfuscatedName("ao")
   public static NPC method10748(int var0, classCL var1) {
      for (WorldView var3 : var1) {
         NPC var4 = (NPC)IndexedObjectSet.method13449(var3.field1696, var0);
         if (var4 != null) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Ldz;")
   @ObfuscatedName("al")
   public static WorldView method10749(int var0, classCL var1) {
      for (WorldView var3 : var1) {
         Player var4 = (Player)IndexedObjectSet.method13449(var3.field1692, var0);
         if (null != var4 && !var3.method3734(-2087374717)) {
            return var3;
         }
      }

      return var1.method1588(330971815);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Ldz;")
   @ObfuscatedName("aj")
   public static WorldView method10750(int var0, classCL var1) {
      for (WorldView var3 : var1) {
         Player var4 = (Player)IndexedObjectSet.method13449(var3.field1692, var0);
         if (null != var4 && !var3.method3734(-2087374717)) {
            return var3;
         }
      }

      return var1.method1588(-466450432);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;)I")
   @ObfuscatedName("zn")
   public static int method10754(classPV var0) {
      return var0.field5423 * -2078654985;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Ldz;")
   @ObfuscatedName("ay")
   public static WorldView method10751(int var0, classCL var1) {
      for (WorldView var3 : var1) {
         if (IndexedObjectSet.method13449(var3.field1696, var0) != null) {
            return var3;
         }
      }

      return var1.method1588(-786916178);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILcl;)Ldz;")
   @ObfuscatedName("aq")
   public static WorldView method10752(int var0, classCL var1) {
      for (WorldView var3 : var1) {
         if (IndexedObjectSet.method13449(var3.field1696, var0) != null) {
            return var3;
         }
      }

      return var1.method1588(-1870188710);
   }

   @ObfuscatedSignature(descriptor = "(I)Lcm;")
   @ObfuscatedName("ac")
   static World method10755(int var0) {
      try {
         return -932636273 * World.field1154 < World.field1151 * -622036481 ? World.field1168[(World.field1154 += 2111825263) * -932636273 - 1] : null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "sx.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("aa")
   static String method10756(int var0, int var1) {
      try {
         String var2 = Integer.toString(var0);

         for (int var3 = var2.length() - 3; var3 > 0; var3 -= 3) {
            if (var1 <= -1897223536) {
               throw new IllegalStateException();
            }

            var2 = var2.substring(0, var3) + classDO.field1590 + var2.substring(var3);
         }

         if (var2.length() > 9) {
            if (var1 <= -1897223536) {
               throw new IllegalStateException();
            } else {
               return " "
                  + classQY.method9885(65408, (byte)11)
                  + var2.substring(0, var2.length() - 8)
                  + classKK.field3827
                  + " "
                  + classDO.field1591
                  + var2
                  + classDO.field1589
                  + classDO.field1592;
            }
         } else if (var2.length() <= 6) {
            return " " + classQY.method9885(16776960, (byte)51) + var2 + classDO.field1592;
         } else if (var1 <= -1897223536) {
            throw new IllegalStateException();
         } else {
            return " "
               + classQY.method9885(16777215, (byte)91)
               + var2.substring(0, var2.length() - 4)
               + classKK.field3655
               + " "
               + classDO.field1591
               + var2
               + classDO.field1589
               + classDO.field1592;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sx.aa(" + ')');
      }
   }
}
