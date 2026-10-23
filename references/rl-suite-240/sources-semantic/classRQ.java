import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rq")
class classRQ implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final String field5830 = "Date not valid.";
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5828 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("bj")
   static final int field5829 = 49;

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;I)V")
   @ObfuscatedName("bx")
   static void method10215(int var0, String var1, int var2) {
      try {
         classBF.field391 = var1;
         classBF.field394 = -570519487 * var0;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rq.bx(" + ')');
      }
   }

   classRQ(classRJ var1) {
      this.this$0 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrq;Ljava/lang/Object;)Z")
   @ObfuscatedName("bt")
   public static boolean method10212(classRQ var0, Object var1) {
      if (var0 == null) {
         var0.equals(var1);
      }

      return var0.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrq;Lro;Lro;)I")
   @ObfuscatedName("tf")
   public static int method10204(classRQ var0, classRO var1, classRO var2) {
      if (var0 == null) {
         var0.method10208(var1, var1);
      }

      if (-9085244238986792263L * var1.field5822 > -9085244238986792263L * var2.field5822) {
         return 1;
      } else {
         return var1.field5822 * -9085244238986792263L < var2.field5822 * -9085244238986792263L ? -1 : 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrq;Lro;Lro;)I")
   @ObfuscatedName("lq")
   public static int method10205(classRQ var0, classRO var1, classRO var2) {
      if (var0 == null) {
         var0.method10207(var1, var1);
      }

      if (-9085244238986792263L * var1.field5822 > -9085244238986792263L * var2.field5822) {
         return 1;
      } else {
         return var1.field5822 * -9085244238986792263L < var2.field5822 * -9085244238986792263L ? -1 : 0;
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rq.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method10213(Object var1) {
      return super.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;)Z")
   @ObfuscatedName("zj")
   public static boolean method10214(NameableContainer var0) {
      return var0.field5838 * 559538103 == 1744080879 * var0.field5840;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method10209(Object var1, Object var2) {
      return this.method10206((classRO)var1, (classRO)var2, 1766974101);
   }

   @ObfuscatedSignature(descriptor = "(Lro;Lro;I)I")
   @ObfuscatedName("az")
   int method10206(classRO var1, classRO var2, int var3) {
      try {
         if (-9085244238986792263L * var1.field5822 > -9085244238986792263L * var2.field5822) {
            if (var3 <= 399464675) {
               throw new IllegalStateException();
            } else {
               return 1;
            }
         } else if (var1.field5822 * -9085244238986792263L < var2.field5822 * -9085244238986792263L) {
            if (var3 <= 399464675) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rq.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lro;Lro;)I")
   @ObfuscatedName("ab")
   int method10207(classRO var1, classRO var2) {
      if (-9085244238986792263L * var1.field5822 > -9085244238986792263L * var2.field5822) {
         return 1;
      } else {
         return var1.field5822 * -9085244238986792263L < var2.field5822 * -9085244238986792263L ? -1 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method10210(Object var1, Object var2) {
      return this.method10206((classRO)var1, (classRO)var2, 1217642458);
   }

   @ObfuscatedSignature(descriptor = "(Lro;Lro;)I")
   @ObfuscatedName("af")
   int method10208(classRO var1, classRO var2) {
      if (-9085244238986792263L * var1.field5822 > -9085244238986792263L * var2.field5822) {
         return 1;
      } else {
         return var1.field5822 * -9085244238986792263L < var2.field5822 * -9085244238986792263L ? -1 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsp;Lsp;ZB)Z")
   @ObfuscatedName("az")
   public static boolean method10211(WorldEntity var0, WorldEntity var1, boolean var2, byte var3) {
      try {
         if (var0 == var1) {
            if (var3 == 7) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else if (var1 == null) {
            if (var3 == 7) {
               throw new IllegalStateException();
            } else {
               return true;
            }
         } else if (null == var0) {
            if (var3 == 7) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rq.az(" + ')');
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method10206((classRO)var1, (classRO)var2, 1336240482);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rq.compare(" + 41);
      }
   }
}
