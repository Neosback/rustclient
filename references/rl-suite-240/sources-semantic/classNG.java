import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ng")
class classNG implements Callable {
   @ToRemove(unused = "true")
   @ObfuscatedName("cn")
   static final int field4668 = 3;

   @Override
   public Object call() {
      try {
         PriorityQueue var1 = this.this$0.field4755;

         while (true) {
            Object var2 = null;
            synchronized (var1) {
               if (var1.isEmpty() || this.val$cancelled.get()) {
                  return null;
               }

               var2 = (classNS)var1.remove();
            }

            ((classNS)var2).field4714.method697(1321256158);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ng.call(" + ')');
      }
   }

   classNG(classNU var1, AtomicBoolean var2) {
      this.this$0 = var1;
      this.val$cancelled = var2;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method7884() {
      PriorityQueue var1 = this.this$0.field4755;

      while (true) {
         Object var2 = null;
         synchronized (var1) {
            if (var1.isEmpty() || this.val$cancelled.get()) {
               return null;
            }

            var2 = (classNS)var1.remove();
         }

         ((classNS)var2).field4714.method697(1959768971);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method7885() {
      PriorityQueue var1 = this.this$0.field4755;

      while (true) {
         Object var2 = null;
         synchronized (var1) {
            if (var1.isEmpty() || this.val$cancelled.get()) {
               return null;
            }

            var2 = (classNS)var1.remove();
         }

         ((classNS)var2).field4714.method697(842700106);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;I)V")
   @ObfuscatedName("cl")
   static void method7886(WorldView var0, int var1) {
      try {
         int var2 = client.field838.field1417 * -2087374717;
         int[] var3 = client.field838.field1421;

         for (int var4 = 0; var4 < var2; var4++) {
            if (var1 != 1770667891) {
               throw new IllegalStateException();
            }

            if (var3[var4] != client.field972 * 927693091) {
               if (var1 != 1770667891) {
                  throw new IllegalStateException();
               }

               if (var3[var4] != client.field846 * -2130951373) {
                  Hitsplat.method3642(var0, var3[var4], true, 1805215117);
               }
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ng.cl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIILcl;I)Luz;")
   @ObfuscatedName("aa")
   static classUZ method7883(int var0, int var1, int var2, int var3, classCL var4, int var5) {
      try {
         classUZ var6 = null;
         WorldView var7 = null;
         if (classBZ.method1383(var0, (byte)-59)) {
            boolean var10000;
            if (var0 > 0) {
               if (var5 >= -314200228) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var9 = var10000;
            Object var8;
            if (var9) {
               if (var5 >= -314200228) {
                  throw new IllegalStateException();
               }

               int var10 = classHI.method6090(var0, -390386984);
               var7 = classHB.method6020(var10, var4, -1845572351);
               var8 = (Actor)IndexedObjectSet.method13449(var7.field1696, var10);
            } else {
               int var17 = classGA.method5323(var0, -1282125261);
               var7 = classQB.method9481(var17, var4, 1290700236);
               var8 = var7.method3752(var17, (byte)1);
            }

            if (var8 != null) {
               if (var5 >= -314200228) {
                  throw new IllegalStateException();
               }

               classKY var18 = Actor.method3175((Actor)var8, var7, (short)8639);
               var6 = classHZ.method6171(16777216);
               var6.field6425 = 1870919123 * var18.field4053;
               var6.field6424 = var18.field4054 * 819227381;
            }
         }

         if (var6 == null) {
            if (var5 >= -314200228) {
               throw new IllegalStateException();
            }

            var7 = var4.method1590(var1, var2, -168421765);
            int var15 = var1 - -1444178379 * var7.field1706;
            int var16 = var2 - -351145363 * var7.field1707;
            WorldEntity var19 = (WorldEntity)var4.method1588(1101306946).field1698.method13637(2140889407 * var7.field1697);
            if (var19 != null) {
               if (var5 >= -314200228) {
                  throw new IllegalStateException();
               }

               var6 = var19.method10666(classKY.method6656(var15, 107638560), classKY.method6656(var16, 25081989), (byte)67);
            } else {
               var6 = classIK.method6261(classKY.method6656(var15, 858107525), 0.0F, classKY.method6656(var16, 113134476), (byte)-8);
            }
         }

         int var20 = (int)var6.field6425;
         int var11 = (int)var6.field6424;
         int var12 = classKV.method6613(var7, var20, var11, var3, (byte)122);
         WorldEntity var13 = (WorldEntity)classIS.field3051.field1698.method13637(var7.field1697 * 2140889407);
         if (null != var13) {
            var12 += classKV.method6613(classIS.field3051, var13.vmethod368(311606126), var13.vmethod371(-2097434147), var13.vmethod258((byte)-99), (byte)122);
         }

         var6.field6423 = var12;
         return var6;
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "ng.aa(" + ')');
      }
   }
}
