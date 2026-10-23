import java.util.ArrayList;
import java.util.List;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pg")
public class classPG extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field5246 = 48;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5243 = 2;
   @ObfuscatedName("al")
   final int[] field5240;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5241 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field5244 = 8;
   @ObfuscatedSignature(descriptor = "Lwd;")
   @ObfuscatedName("ay")
   final classWD field5237;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ac")
   static NodeCache field5236 = new NodeCache(16);
   @ObfuscatedName("ao")
   final int field5238;
   @ObfuscatedName("aj")
   final List field5239 = new ArrayList(8);
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5242 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5247 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   public static final int field5245 = 8;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpg;")
   @ObfuscatedName("ap")
   public static classPG method8899(int var0) {
      classPG var1 = (classPG)NodeCache.method6460(field5236, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classBQ.field531.method11942(15, var0, -923256858);
         var1 = new classPG(new classXY(var2), var0);
         field5236.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ab")
   public int method8904(byte var1) {
      try {
         return this.field5238 * 874995967;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pg.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ag")
   public int method8907(byte var1) {
      try {
         int var10000;
         if (null != this.field5240) {
            if (var1 <= 60) {
               throw new IllegalStateException();
            }

            var10000 = this.field5240.length;
         } else {
            var10000 = 0;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pg.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   public int method8914(int var1) {
      try {
         return this.field5239.size();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pg.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lwd;")
   @ObfuscatedName("aa")
   public classWD method8921(int var1) {
      try {
         return this.field5237;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pg.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpg;")
   @ObfuscatedName("ai")
   public static classPG method8900(int var0) {
      classPG var1 = (classPG)NodeCache.method6460(field5236, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classBQ.field531.method11942(15, var0, -777243510);
         var1 = new classPG(new classXY(var2), var0);
         field5236.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpg;IB)Lww;")
   @ObfuscatedName("bp")
   public static classWW method8915(classPG var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method8920(var1, var2);
      }

      try {
         return (classWW)var0.field5239.get(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pg.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lww;")
   @ObfuscatedName("bx")
   public classWW method8916(int var1) {
      return (classWW)this.field5239.get(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("al")
   public static void method8894(IndexDataBase var0) {
      classBQ.field531 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public static void method8896() {
      field5236.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public static void method8897() {
      field5236.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public static void method8898() {
      field5236.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpg;")
   @ObfuscatedName("ad")
   public static classPG method8901(int var0) {
      classPG var1 = (classPG)NodeCache.method6460(field5236, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classBQ.field531.method11942(15, var0, -1382632228);
         var1 = new classPG(new classXY(var2), var0);
         field5236.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lem;)Lef;")
   @ObfuscatedName("kt")
   public static classEF method8903(classEM var0) {
      return var0.field1786;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   public int method8908() {
      return null != this.field5240 ? this.field5240.length : 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   public int method8905() {
      return this.field5238 * 874995967;
   }

   @ObfuscatedSignature(descriptor = "(Lpg;II)I")
   @ObfuscatedName("ua")
   public static int method8911(classPG var0, int var1, int var2) {
      if (var0 == null) {
         var0.method8913(var1, var1);
      }

      try {
         int var10000;
         if (null != var0.field5240) {
            if (var2 == -46986528) {
               throw new IllegalStateException();
            }

            var10000 = var0.field5240[var1];
         } else {
            var10000 = -1;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pg.as(" + 41);
      }
   }

   classPG(classXY var1, int var2) {
      this.field5237 = new classWD();
      this.field5238 = -132669697 * var2;
      int[] var3 = null;
      if (var1 != null && var1.field6952 != null) {
         label66:
         while (true) {
            int var4 = var1.method13095(-346779531);
            switch (var4) {
               case 0:
                  break label66;
               case 1:
                  int var12 = var1.method13095(-346779531);
                  var3 = new int[var12];

                  for (int var15 = 0; var15 < var12; var15++) {
                     var3[var15] = var1.method13099(-1461538641);
                  }
                  break;
               case 2:
                  int var11 = var1.method13099(-36182986) * 20;
                  int var14 = var1.method13099(141911779) * 20;
                  int var7 = var1.method13095(-346779531);
                  int[] var17 = new int[var7];

                  for (int var9 = 0; var9 < var7; var9++) {
                     var17[var9] = var1.method13099(-1095243703);
                  }

                  if (var7 <= 48 && this.field5239.size() < 8) {
                     this.field5239.add(new classWW(var17, var11, var14));
                  }
                  break;
               case 3:
                  int var13 = var1.method13095(-346779531);
                  classZL var10 = classZL.field7199[var13];
                  int var16 = var1.method13099(1583176156) * 20;
                  classWD.method12386(this.field5237, var10, var16, (byte)23);
                  break;
               case 4:
                  int var6 = var1.method13095(-346779531);
                  classZL var5 = classZL.field7199[var6];
                  int var8 = var1.method13099(665438358) * 20;
                  this.field5237.method12389(var5, var8, -388881497);
            }
         }
      }

      this.field5240 = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpg;")
   @ObfuscatedName("au")
   public static classPG method8902(int var0) {
      classPG var1 = (classPG)NodeCache.method6460(field5236, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classBQ.field531.method11942(15, var0, -997029302);
         var1 = new classPG(new classXY(var2), var0);
         field5236.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   public int method8906() {
      return this.field5238 * -1569817733;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   public int method8909() {
      return null != this.field5240 ? this.field5240.length : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpg;I)Lww;")
   @ObfuscatedName("ik")
   public static classWW method8917(classPG var0, int var1) {
      if (var0 == null) {
         var0.method8914(var1);
      }

      return (classWW)var0.field5239.get(var1);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("an")
   public int method8912(int var1) {
      return null != this.field5240 ? this.field5240[var1] : -1;
   }

   @ObfuscatedSignature(descriptor = "(I)Lww;")
   @ObfuscatedName("am")
   public classWW method8918(int var1) {
      return (classWW)this.field5239.get(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpg;)I")
   @ObfuscatedName("hh")
   public static int method8910(classPG var0) {
      return null != var0.field5240 ? var0.field5240.length : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)Lww;")
   @ObfuscatedName("ah")
   public classWW method8919(int var1) {
      return (classWW)this.field5239.get(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ao")
   public static void method8895(IndexDataBase var0) {
      classBQ.field531 = var0;
   }

   @ObfuscatedSignature(descriptor = "(IB)Lww;")
   @ObfuscatedName("ac")
   public classWW method8920(int var1, byte var2) {
      try {
         return (classWW)this.field5239.get(var1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pg.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   public int method8913(int var1, int var2) {
      try {
         int var10000;
         if (null != this.field5240) {
            if (var2 == -46986528) {
               throw new IllegalStateException();
            }

            var10000 = this.field5240[var1];
         } else {
            var10000 = -1;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "pg.as(" + 41);
      }
   }
}
