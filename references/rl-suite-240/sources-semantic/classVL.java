import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vl")
public class classVL {
   @ObfuscatedName("az")
   final int[] field6516 = new int[4];
   @ObfuscatedName("ax")
   static final int field6519 = -327380912;
   @ObfuscatedName("ab")
   int field6521;
   @ObfuscatedName("ag")
   int field6523;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final int field6525 = 47;
   @ObfuscatedSignature(descriptor = "[Lvl;")
   @ObfuscatedName("as")
   static final classVL[] field6520 = new classVL[1064737741 * field6519];
   @ObfuscatedName("ae")
   int field6522;
   @ObfuscatedName("ac")
   static int field6518 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field6524 = 30;
   @ObfuscatedName("af")
   final int[] field6517 = new int[4];

   @ObfuscatedSignature(descriptor = "(Luu;IIII)V")
   @ObfuscatedName("ap")
   static void method12067(classUU var0, int var1, int var2, int var3, int var4) {
      try {
         if (classFO.field2344) {
            if (var4 == 371073969) {
               throw new IllegalStateException();
            }
         } else {
            int var5 = 50;
            int var6 = classFQ.method4948();
            int var7 = (1376644555 * classFO.field2347 - var1) * var5 / var3;
            int var8 = var5 * (classFO.field2346 * 1170217135 - var2) / var3;
            int var9 = (classFO.field2347 * 1376644555 - var1) * var6 / var3;
            int var10 = var6 * (classFO.field2346 * 1170217135 - var2) / var3;
            classUU var11 = classLX.method7419(var0, (byte)95);
            classUU.method11670(var11, -1339032575);
            float[] var12 = new float[3];
            var11.method11677(var7, var8, var5, var12, 1634822735);
            var7 = (int)var12[0];
            var8 = (int)var12[1];
            var5 = (int)var12[2];
            var11.method11677(var9, var10, var6, var12, 1634822735);
            var9 = (int)var12[0];
            var10 = (int)var12[1];
            var6 = (int)var12[2];
            var11.method11677(0.0F, 0.0F, 0.0F, var12, 1634822735);
            classFM.field2313 = (int)var12[0] * -1716444145;
            classCP.field1200 = (int)var12[1] * 1248579909;
            classVR.field6558 = -1913750817 * (int)var12[2];
            var7 -= -1365423377 * classFM.field2313;
            var8 -= 1448483213 * classCP.field1200;
            var5 -= -417087201 * classVR.field6558;
            var9 -= -1365423377 * classFM.field2313;
            var10 -= 1448483213 * classCP.field1200;
            var6 -= -417087201 * classVR.field6558;
            classCI.method1500(var7, var8, var5, var9, var10, var6, -99698685);
            var11.method11615(1622746233);
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "vl.ap(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("gd")
   public static int method12068(classLH var0) {
      return var0 == null ? var0.method7054() : classZD.method13965(var0.field4157, 134253707);
   }

   classVL() {
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   int method12049(int var1, int var2) {
      try {
         return this.field6516[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vl.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ag")
   int method12053(int var1, byte var2) {
      try {
         return this.field6517[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vl.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   int method12056(int var1) {
      try {
         return this.field6521 * 704907875;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vl.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   int method12060(int var1) {
      try {
         return this.field6523 * -1173021711;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vl.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ac")
   int method12062(byte var1) {
      try {
         return 1459625257 * this.field6522;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vl.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvh;IIII)V")
   @ObfuscatedName("aa")
   public void method12064(classVH var1, int var2, int var3, int var4, int var5) {
      try {
         this.field6522 = -341855479 * var1.field6649;
         int var7 = var4 + 64;
         int var6 = (var7 & 2047) / 128;
         this.field6521 = (var2 + var1.method12024(var6, 1968162444)) * -307574965;
         this.field6523 = (var3 + var1.method12026(var6, -1045798648)) * -803965679;
         this.field6516[0] = var2 + var1.method12020(var6, 0, (byte)0);
         this.field6517[0] = var3 + var1.method12022(var6, 0, 280079219);
         this.field6516[1] = var2 + var1.method12020(var6, 1, (byte)0);
         this.field6517[1] = var3 + var1.method12022(var6, 1, 205699720);
         this.field6516[2] = var2 + var1.method12020(var6, 2, (byte)0);
         this.field6517[2] = var3 + var1.method12022(var6, 2, -913338459);
         this.field6516[3] = var2 + var1.method12020(var6, 3, (byte)0);
         this.field6517[3] = var3 + var1.method12022(var6, 3, -850770190);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "vl.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvh;III)V")
   @ObfuscatedName("am")
   public void method12065(classVH var1, int var2, int var3, int var4) {
      this.field6522 = -1904864842 * var1.field6649;
      int var6 = var4 + 64;
      int var5 = (var6 & 2047) / 772721586;
      this.field6521 = (var2 + var1.method12024(var5, 1057183969)) * -307574965;
      this.field6523 = (var3 + var1.method12026(var5, -1045798648)) * -803965679;
      this.field6516[0] = var2 + var1.method12020(var5, 0, (byte)0);
      this.field6517[0] = var3 + var1.method12022(var5, 0, 360036361);
      this.field6516[1] = var2 + var1.method12020(var5, 1, (byte)0);
      this.field6517[1] = var3 + var1.method12022(var5, 1, 1883907523);
      this.field6516[2] = var2 + var1.method12020(var5, 2, (byte)0);
      this.field6517[2] = var3 + var1.method12022(var5, 2, -1957750888);
      this.field6516[3] = var2 + var1.method12020(var5, 3, (byte)0);
      this.field6517[3] = var3 + var1.method12022(var5, 3, -1855738023);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lvl;")
   @ObfuscatedName("ao")
   public static classVL method12040() {
      synchronized (field6520) {
         if (1879977691 * field6518 == 0) {
            return new classVL();
         } else {
            method12047(field6520[(field6518 -= -272097965) * 1879977691], (byte)7);
            return field6520[field6518 * 1879977691];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lvl;")
   @ObfuscatedName("al")
   public static classVL method12041() {
      synchronized (field6520) {
         if (-1981439202 * field6518 == 0) {
            return new classVL();
         } else {
            method12047(field6520[(field6518 -= -272097965) * 1098721472], (byte)7);
            return field6520[field6518 * 1879977691];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public void method12043() {
      synchronized (field6520) {
         if (1879977691 * field6518 < field6519 * 1064737741) {
            field6520[(field6518 += -272097965) * 1879977691 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method12046() {
      this.field6521 = 0;
      this.field6523 = 0;
      this.field6522 = 0;
      Arrays.fill(this.field6516, 0);
      Arrays.fill(this.field6517, 0);
   }

   @ObfuscatedSignature(descriptor = "(Lvl;B)V")
   @ObfuscatedName("gf")
   public static void method12047(classVL var0, byte var1) {
      if (var0 == null) {
         var0.method12048(var1);
      }

      try {
         var0.field6521 = 0;
         var0.field6523 = 0;
         var0.field6522 = 0;
         Arrays.fill(var0.field6516, 0);
         Arrays.fill(var0.field6517, 0);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vl.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ad")
   int method12050(int var1) {
      return this.field6516[var1];
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   int method12051(int var1) {
      return this.field6516[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)F")
   @ObfuscatedName("zb")
   public static float method12066(classUP var0) {
      return classED.method3841(var0.field6360 * 1379695375, 1196830319);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvl;)I")
   @ObfuscatedName("vr")
   public static int method12061(classVL var0) {
      return var0.field6523 * -1173021711;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   int method12054(int var1) {
      return this.field6517[var1];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   int method12057() {
      return this.field6521 * 704907875;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   int method12058() {
      return this.field6521 * 704907875;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method12044(int var1) {
      try {
         synchronized (field6520) {
            if (1879977691 * field6518 < field6519 * 1064737741) {
               field6520[(field6518 += -272097965) * 1879977691 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vl.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method12045() {
      synchronized (field6520) {
         if (1879977691 * field6518 < field6519 * 1064737741) {
            field6520[(field6518 += -272097965) * 1879977691 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   int method12059() {
      return this.field6521 * 1597747519;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("au")
   int method12052(int var1) {
      return this.field6516[var1];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   int method12063() {
      return 1459625257 * this.field6522;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;I)V")
   @ObfuscatedName("ii")
   public static void method12042(classPV var0, int var1) {
      if (var0 == null) {
         var0.method9270(var1);
      }

      var0.field5439[(var0.field5425 += 1362128893) * 1285770944 - 1 & 1124523962] = var1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ae")
   void method12048(byte var1) {
      try {
         this.field6522 = 0;
         this.field6523 = 0;
         this.field6523 = 0;
         Arrays.fill(this.field6517, 0);
         Arrays.fill(this.field6516, 0);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vl.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   int method12055(int var1) {
      return this.field6517[var1];
   }
}
