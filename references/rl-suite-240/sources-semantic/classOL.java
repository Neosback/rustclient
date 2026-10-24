import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ol")
public class classOL implements classXZ {
   @ObfuscatedSignature(descriptor = "Lol;")
   @ObfuscatedName("az")
   static final classOL field4912 = new classOL(1, 0);
   @ObfuscatedSignature(descriptor = "Lol;")
   @ObfuscatedName("af")
   static final classOL field4913 = new classOL(0, 1);
   @ObfuscatedName("ab")
   public final int field4915;
   @ObfuscatedName("ag")
   final int field4916;
   @ObfuscatedSignature(descriptor = "Lol;")
   @ObfuscatedName("ae")
   static final classOL field4914 = new classOL(2, 2);
   @ToRemove(unused = "true")
   @ObfuscatedName("bl")
   static final int field4917 = 102;

   @ObfuscatedSignature(descriptor = "(DDD)D")
   @ObfuscatedName("af")
   static double method8541(double var0, double var2, double var4) {
      try {
         return classCX.method2695((var0 - var2) / var4) / var4;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ol.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return -1887012697 * this.field4916;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ol.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return -1887012697 * this.field4916;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return -1887012697 * this.field4916;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -1887012697 * this.field4916;
   }

   @ObfuscatedSignature(descriptor = "(IS)Lpt;")
   @ObfuscatedName("af")
   public static VarbitComposition method8542(int var0, short var1) {
      try {
         VarbitComposition var2 = (VarbitComposition)NodeCache.method6460(VarbitComposition.field5416, var0);
         if (null != var2) {
            if (var1 == 206) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = VarbitComposition.field5421.method11942(14, var0, -1667244085);
            var2 = new VarbitComposition(new classXY(var3));
            VarbitComposition.field5416.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ol.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;I)V")
   @ObfuscatedName("am")
   static void method8544(classTE var0, int var1) {
      try {
         method8543(var0, null, true, (byte)49);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ol.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lyy;ZB)V")
   @ObfuscatedName("at")
   static void method8543(classTE var0, classYY var1, boolean var2, byte var3) {
      try {
         if (var0 == null) {
            if (var3 <= 0) {
               throw new IllegalStateException();
            } else {
               throw new RuntimeException();
            }
         } else {
            if (var1 != null) {
               if (var3 <= 0) {
                  throw new IllegalStateException();
               }

               if (var0.field6126 != var1) {
                  if (var3 <= 0) {
                     throw new IllegalStateException();
                  }

                  throw new RuntimeException();
               }
            }

            if (var2) {
               if (var3 <= 0) {
                  throw new IllegalStateException();
               }

               if (!var0.field6122) {
                  if (var3 <= 0) {
                     throw new IllegalStateException();
                  }

                  throw new RuntimeException();
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ol.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ao")
   static boolean method8545(int var0) {
      try {
         if (client.field1003 != null) {
            if (-1506417701 * client.field803 < client.field1003.size()) {
               while (client.field803 * -1506417701 < client.field1003.size()) {
                  if (var0 == 2130080319) {
                     throw new IllegalStateException();
                  }

                  classDT var1 = (classDT)client.field1003.get(-1506417701 * client.field803);
                  if (!var1.method3612(-795884518)) {
                     if (var0 == 2130080319) {
                        throw new IllegalStateException();
                     }

                     return false;
                  }

                  client.field803 += -875601837;
               }

               return true;
            }

            if (var0 == 2130080319) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ol.ao(" + ')');
      }
   }

   classOL(int var1, int var2) {
      this.field4915 = 1236851705 * var1;
      this.field4916 = var2 * 1797762839;
   }
}
