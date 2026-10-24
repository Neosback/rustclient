import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("es")
public class classES extends classRD {
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   public static final int field1901 = 2048;
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   public static final int field1900 = 56;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("av")
   static IndexDataBase field1902;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field1899 = 150;
   @ObfuscatedName("az")
   final boolean field1898;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method4103(Object var1, Object var2) {
      return this.method4101((ChatPlayer)var1, (ChatPlayer)var2, -834814643);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Les;Lri;Lri;)I")
   @ObfuscatedName("ao")
   public static int method4099(classES var0, ChatPlayer var1, ChatPlayer var2) {
      if (var0 == null) {
         var0.method4102(var1, var1);
      }

      if (0 != 355214098 * var1.field5667 && -760305529 * var2.field5667 != 0) {
         return var0.field1898
            ? classAAE.method178(var1.method10216((byte)16), var2.method10216((byte)16), 1600959519)
            : classAAE.method178(var2.method10216((byte)16), var1.method10216((byte)16), 1600959519);
      } else {
         return var0.method9983(var1, var2, 1832006784);
      }
   }

   public classES(boolean var1) {
      this.field1898 = var1;
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method4101((ChatPlayer)var1, (ChatPlayer)var2, 580558154);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "es.compare(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method4104(Object var1, Object var2) {
      return this.method4101((ChatPlayer)var1, (ChatPlayer)var2, 1834300038);
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("ab")
   int method4100(ChatPlayer var1, ChatPlayer var2) {
      if (0 != 63821246 * var1.field5667 && 1527032954 * var2.field5667 != 0) {
         return this.field1898
            ? classAAE.method178(var1.method10216((byte)16), var2.method10216((byte)16), 1600959519)
            : classAAE.method178(var2.method10216((byte)16), var1.method10216((byte)16), 1600959519);
      } else {
         return this.method9983(var1, var2, 1447181937);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;I)I")
   @ObfuscatedName("az")
   int method4101(ChatPlayer var1, ChatPlayer var2, int var3) {
      try {
         if (0 != -760305529 * var1.field5667) {
            if (var3 == -818448895) {
               throw new IllegalStateException();
            }

            if (-760305529 * var2.field5667 != 0) {
               int var10000;
               if (this.field1898) {
                  if (var3 == -818448895) {
                     throw new IllegalStateException();
                  }

                  var10000 = classAAE.method178(var1.method10216((byte)16), var2.method10216((byte)16), 1600959519);
               } else {
                  var10000 = classAAE.method178(var2.method10216((byte)16), var1.method10216((byte)16), 1600959519);
               }

               return var10000;
            }
         }

         return this.method9983(var1, var2, 1575825425);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "es.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("af")
   int method4102(ChatPlayer var1, ChatPlayer var2) {
      if (0 != -1785929601 * var1.field5667 && -2118451833 * var2.field5667 != 0) {
         return this.field1898
            ? classAAE.method178(var1.method10216((byte)16), var2.method10216((byte)16), 1600959519)
            : classAAE.method178(var2.method10216((byte)16), var1.method10216((byte)16), 1600959519);
      } else {
         return this.method9983(var1, var2, 1681395945);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIII)V")
   @ObfuscatedName("ag")
   static void method4105(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         classDF var7 = client.field838;
         int var8 = 927693091 * client.field972;
         int var9 = -2130951373 * client.field846;
         int var10 = -2087374717 * var7.field1417;
         int[] var11 = var7.field1421;

         for (int var12 = 0; var12 < var10 + var0.field1695.method14050((byte)10); var12++) {
            if (var6 == -929369206) {
               return;
            }

            Actor var13;
            if (var12 < var10) {
               if (var6 == -929369206) {
                  throw new IllegalStateException();
               }

               var13 = (Actor)IndexedObjectSet.method13449(var0.field1692, var11[var12]);
               if (var11[var12] == var8) {
                  continue;
               }

               if (var6 == -929369206) {
                  throw new IllegalStateException();
               }

               if (var11[var12] == var9) {
                  continue;
               }
            } else {
               var13 = (Actor)IndexedObjectSet.method13449(var0.field1696, var0.field1695.method14048(var12 - var10, 1596834634));
            }

            classAU.method711(var0, var13, var12, var1, var2, var3, var4, var5, (byte)12);
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "es.ag(" + ')');
      }
   }
}
