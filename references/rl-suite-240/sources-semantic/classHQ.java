import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hq")
public class classHQ implements classGE {
   @ToRemove(unused = "true")
   @ObfuscatedName("ct")
   static final int field2867 = 512;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   public static final int field2865 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2864 = 14;
   @ObfuscatedName("bf")
   static int field2866;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2863 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field2862 = 4;

   @ObfuscatedSignature(descriptor = "(ILxy;B)Lpi;")
   @ObfuscatedName("az")
   public static ColorTextureOverride method6126(int var0, classXY var1, byte var2) {
      try {
         int var3 = var1.method13095(-346779531);
         boolean var10000;
         if ((var3 & 1) != 0) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var4 = var10000;
         if (0 != (var3 & 2)) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var5 = var10000;
         boolean var6 = (var3 & 4) != 0;
         if (0 != (var3 & 8)) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var7 = var10000;
         ColorTextureOverride var8 = new ColorTextureOverride(var0);
         if (var4) {
            int[] var10;
            label154: {
               int var9 = var1.method13095(-346779531);
               var10 = new int[]{var9 & 15, var9 >> 4 & 15};
               if (null != var8.field5259) {
                  if (var2 <= 2) {
                     throw new IllegalStateException();
                  }

                  if (var8.field5259.length == var10.length) {
                     if (var2 <= 2) {
                        throw new IllegalStateException();
                     }

                     var10000 = true;
                     break label154;
                  }
               }

               var10000 = false;
            }

            boolean var11 = var10000;

            for (int var12 = 0; var12 < 2; var12++) {
               if (var2 <= 2) {
                  throw new IllegalStateException();
               }

               if (15 != var10[var12]) {
                  if (var2 <= 2) {
                     throw new IllegalStateException();
                  }

                  short var13 = (short)var1.method13099(-2092843255);
                  if (var11) {
                     var8.field5259[var10[var12]] = var13;
                  }
               }
            }
         }

         if (var5) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            int var15 = var1.method13095(-346779531);
            int[] var16 = new int[]{var15 & 15, var15 >> 4 & 15};
            if (null != var8.field5258 && var8.field5258.length == var16.length) {
               if (var2 <= 2) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var17 = var10000;

            for (int var18 = 0; var18 < 2; var18++) {
               if (var2 <= 2) {
                  throw new IllegalStateException();
               }

               if (15 != var16[var18]) {
                  if (var2 <= 2) {
                     throw new IllegalStateException();
                  }

                  short var19 = (short)var1.method13099(-1898895509);
                  if (var17) {
                     if (var2 <= 2) {
                        throw new IllegalStateException();
                     }

                     var8.field5258[var16[var18]] = var19;
                  }
               }
            }
         }

         if (var6) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            var8.field5255 = var1.method13173(classXM.field6886.field6888, 1566271411) * -1424760829;
            var8.field5256 = var1.method13173(classXM.field6886.field6888, 1755187850) * -286785645;
         }

         if (var7) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            var8.field5257 = var1.method13173(classXM.field6886.field6888, 1488196929) * 1097885037;
            var8.field5260 = var1.method13173(classXM.field6886.field6888, 1868658130) * -74289375;
         }

         return var8;
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "hq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Low;")
   @ObfuscatedName("az")
   @Override
   public classOW vmethod463(int var1, byte var2) {
      try {
         classOW var4 = (classOW)NodeCache.method6460(classOW.field5181, var1);
         classOW var3;
         if (var4 != null) {
            var3 = var4;
         } else {
            byte[] var5 = classOW.field5183.method11942(4, var1, -1693839991);
            var4 = new classOW(new classXY(var5), var1);
            classOW.field5181.method6464(var4, var1);
            var3 = var4;
         }

         return var3;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "hq.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnz;)Lvj;")
   @ObfuscatedName("dz")
   public static classVJ method6127(classNZ var0) {
      if (var0 == null) {
         var0.method8297();
      }

      return classZM.method14116(var0, null);
   }

   @ObfuscatedSignature(descriptor = "(I)Low;")
   @ObfuscatedName("af")
   @Override
   public classOW vmethod464(int var1) {
      classOW var3 = (classOW)NodeCache.method6460(classOW.field5181, var1);
      classOW var2;
      if (var3 != null) {
         var2 = var3;
      } else {
         byte[] var4 = classOW.field5183.method11942(4, var1, -803899226);
         var3 = new classOW(new classXY(var4), var1);
         classOW.field5181.method6464(var3, var1);
         var2 = var3;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Low;")
   @ObfuscatedName("ae")
   @Override
   public classOW vmethod465(int var1) {
      classOW var3 = (classOW)NodeCache.method6460(classOW.field5181, var1);
      classOW var2;
      if (var3 != null) {
         var2 = var3;
      } else {
         byte[] var4 = classOW.field5183.method11942(4, var1, -1030441563);
         var3 = new classOW(new classXY(var4), var1);
         classOW.field5181.method6464(var3, var1);
         var2 = var3;
      }

      return var2;
   }

   classHQ() {
   }
}
