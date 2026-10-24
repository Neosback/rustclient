import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gs")
public class classGS extends WorldMapIcon {
   @ObfuscatedSignature(descriptor = "Lmj;")
   @ObfuscatedName("lw")
   static classMJ field2675;
   @ObfuscatedSignature(descriptor = "Lgr;")
   @ObfuscatedName("af")
   final WorldMapRegion field2669;
   @ObfuscatedName("ae")
   int field2670;
   @ObfuscatedSignature(descriptor = "Lhw;")
   @ObfuscatedName("ab")
   classHW field2674;
   @ObfuscatedName("az")
   final int field2668;
   @ObfuscatedName("as")
   int field2672;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   public static final int field2673 = 8;
   @ObfuscatedName("ag")
   int field2671;

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ag")
   @Override
   int vmethod494(short var1) {
      try {
         return -848641265 * this.field2672;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gs.ag(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgs;)V")
   @ObfuscatedName("ir")
   public static void method5770(classGS var0) {
      var0.field2670 = ObjectComposition.method8585(classMU.method7771(-1330273219 * var0.field2668, 1302487244), -890834553).field4951 * 1742286829;
      var0.field2674 = var0.field2669.method5756(SceneTilePaint.method4817(438436917 * var0.field2670, (byte)-86), 1683798244);
      classPS var1 = SceneTilePaint.method4817(var0.vmethod486((byte)-71), (byte)-23);
      SpritePixels var2 = var1.method9236(false, 696385653);
      if (null != var2) {
         var0.field2671 = var2.field7005 * 1497079364;
         var0.field2672 = var2.field7007 * -434551356;
      } else {
         var0.field2671 = 0;
         var0.field2672 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method5771(int var1) {
      try {
         this.field2670 = ObjectComposition.method8585(classMU.method7771(-2124088909 * this.field2668, 1341031024), -1235016594).field4951 * 1742286829;
         this.field2674 = this.field2669.method5756(SceneTilePaint.method4817(438436917 * this.field2670, (byte)-117), 1548070523);
         classPS var2 = SceneTilePaint.method4817(this.vmethod486((byte)-77), (byte)-60);
         SpritePixels var3 = var2.method9236(false, -979775303);
         if (null != var3) {
            if (var1 >= -550936162) {
               throw new IllegalStateException();
            }

            this.field2671 = var3.field7005 * 243856479;
            this.field2672 = var3.field7007 * 2028588015;
         } else {
            this.field2671 = 0;
            this.field2672 = 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gs.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgs;)V")
   @ObfuscatedName("rn")
   public static void method5772(classGS var0) {
      var0.field2670 = ObjectComposition.method8585(classMU.method7771(-2124088909 * var0.field2668, 1531493942), 1148526281).field4951 * -428529809;
      var0.field2674 = var0.field2669.method5756(SceneTilePaint.method4817(438436917 * var0.field2670, (byte)-100), 2116896056);
      classPS var1 = SceneTilePaint.method4817(var0.vmethod486((byte)-100), (byte)-88);
      SpritePixels var2 = var1.method9236(false, -1028849822);
      if (null != var2) {
         var0.field2671 = var2.field7005 * 243856479;
         var0.field2672 = var2.field7007 * -602961950;
      } else {
         var0.field2671 = 0;
         var0.field2672 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod486(byte var1) {
      try {
         return 438436917 * this.field2670;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gs.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   @Override
   public int vmethod487() {
      return 438436917 * this.field2670;
   }

   classGS(classKY var1, classKY var2, int var3, WorldMapRegion var4) {
      super(var1, var2);
      this.field2668 = var3 * 337796475;
      this.field2669 = var4;
      this.method5771(-2056555655);
   }

   @ObfuscatedSignature(descriptor = "()Lhw;")
   @ObfuscatedName("aj")
   @Override
   classHW vmethod490() {
      return this.field2674;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   void method5773() {
      this.field2670 = ObjectComposition.method8585(classMU.method7771(-2124088909 * this.field2668, 2061643910), 787246566).field4951 * -321587624;
      this.field2674 = this.field2669.method5756(SceneTilePaint.method4817(438436917 * this.field2670, (byte)-120), 2100600624);
      classPS var1 = SceneTilePaint.method4817(this.vmethod486((byte)-56), (byte)-127);
      SpritePixels var2 = var1.method9236(false, -1091971536);
      if (null != var2) {
         this.field2671 = var2.field7005 * 983030740;
         this.field2672 = var2.field7007 * 2028588015;
      } else {
         this.field2671 = 0;
         this.field2672 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lhw;")
   @ObfuscatedName("ae")
   @Override
   classHW vmethod491(byte var1) {
      try {
         return this.field2674;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gs.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   @Override
   public int vmethod488() {
      return 438436917 * this.field2670;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   @Override
   public int vmethod489() {
      return 438436917 * this.field2670;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   @Override
   int vmethod495() {
      return 1265877192 * this.field2672;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   @Override
   int vmethod492() {
      return this.field2671 * -702388321;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   @Override
   int vmethod493(int var1) {
      try {
         return this.field2671 * -702388321;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gs.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   @Override
   int vmethod496() {
      return -848641265 * this.field2672;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   @Override
   int vmethod497() {
      return -848641265 * this.field2672;
   }

   @ObfuscatedSignature(descriptor = "([I[JIII)V")
   @ObfuscatedName("ac")
   public static void method5774(int[] var0, long[] var1, int var2, int var3, int var4) {
      try {
         if (var2 < var3) {
            if (var4 == -1188251123) {
               throw new IllegalStateException();
            }

            int var5 = (var2 + var3) / 2;
            int var6 = var2;
            int var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            long var8 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var8;
            byte var10000;
            if (Integer.MAX_VALUE == var7) {
               if (var4 == -1188251123) {
                  throw new IllegalStateException();
               }

               var10000 = 0;
            } else {
               var10000 = 1;
            }

            byte var10 = var10000;

            for (int var11 = var2; var11 < var3; var11++) {
               if (var4 == -1188251123) {
                  return;
               }

               if (var0[var11] < var7 + (var11 & var10)) {
                  if (var4 == -1188251123) {
                     throw new IllegalStateException();
                  }

                  int var12 = var0[var11];
                  var0[var11] = var0[var6];
                  var0[var6] = var12;
                  long var13 = var1[var11];
                  var1[var11] = var1[var6];
                  var1[var6++] = var13;
               }
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var8;
            method5774(var0, var1, var2, var6 - 1, 1038806780);
            method5774(var0, var1, var6 + 1, var3, 1270778146);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "gs.ac(" + ')');
      }
   }
}
