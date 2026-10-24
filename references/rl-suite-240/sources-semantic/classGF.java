import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gf")
public class classGF extends classHA {
   @ObfuscatedName("af")
   int field2566;
   @ObfuscatedName("ip")
   static int field2568;
   @ObfuscatedName("ae")
   int field2565;
   @ObfuscatedName("ab")
   int field2564;
   @ObfuscatedName("az")
   int field2567;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod466(classXY var1) {
      this.field2750 = Math.min(-1667236931 * this.field2750, 4) * -765552747;
      this.field2756 = new short[1][64][64];
      this.field2757 = new short[-1667236931 * this.field2750][64][64];
      this.field2755 = new byte[-1667236931 * this.field2750][64][64];
      this.field2754 = new byte[-1667236931 * this.field2750][64][64];
      this.field2758 = new classHZ[-1667236931 * this.field2750][64][64][];

      for (int var2 = 0; var2 < 8; var2++) {
         for (int var3 = 0; var3 < 8; var3++) {
            this.method6001(this.field2565 * 1849701208 + var2, var3 + this.field2564 * 223314696, var1, (byte)-76);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   void method5431(classXY var1, int var2) {
      try {
         int var3 = var1.method13095(-346779531);
         if (var3 != 1177860011 * classHI.field2801.field2799) {
            if (var2 == -1452870557) {
               throw new IllegalStateException();
            } else {
               throw new IllegalStateException("");
            }
         } else {
            this.field2751 = var1.method13095(-346779531) * -2015926021;
            this.field2750 = var1.method13095(-346779531) * -765552747;
            this.field2752 = var1.method13099(-2124153845) * -735630941;
            this.field2753 = var1.method13099(-1018264231) * -1209921667;
            this.field2567 = var1.method13095(-346779531) * 113218633;
            this.field2566 = var1.method13095(-346779531) * 2071171191;
            this.field2749 = var1.method13099(4478478) * -1652832041;
            this.field2748 = var1.method13099(-406885852) * -1381327085;
            this.field2565 = var1.method13095(-346779531) * 1465087555;
            this.field2564 = var1.method13095(-346779531) * 747418529;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gf.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("af")
   @Override
   void vmethod467(classXY var1, int var2) {
      try {
         this.field2750 = Math.min(-1667236931 * this.field2750, 4) * -765552747;
         this.field2756 = new short[1][64][64];
         this.field2757 = new short[-1667236931 * this.field2750][64][64];
         this.field2755 = new byte[-1667236931 * this.field2750][64][64];
         this.field2754 = new byte[-1667236931 * this.field2750][64][64];
         this.field2758 = new classHZ[-1667236931 * this.field2750][64][64][];

         for (int var3 = 0; var3 < 8; var3++) {
            if (var2 != -1811901292) {
               throw new IllegalStateException();
            }

            for (int var4 = 0; var4 < 8; var4++) {
               if (var2 != -1811901292) {
                  return;
               }

               this.method6001(this.field2565 * 1849701208 + var3, var4 + this.field2564 * 223314696, var1, (byte)-60);
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gf.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method5436(Object var1) {
      if (!(var1 instanceof classGF)) {
         return false;
      } else {
         classGF var2 = (classGF)var1;
         return this.field2749 * 1151970023 == var2.field2749 * 1151970023 && var2.field2748 * -456391909 == this.field2748 * -456391909
            ? this.field2565 * 1304954475 == var2.field2565 * 1304954475 && var2.field2564 * 27914337 == 27914337 * this.field2564
            : false;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ag")
   int method5442(byte var1) {
      try {
         return -201810439 * this.field2567;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gf.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   int method5445(int var1) {
      try {
         return this.field2566 * 1619478855;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gf.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("aa")
   void method5432(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (var2 != 1177860011 * classHI.field2801.field2799) {
         throw new IllegalStateException("");
      } else {
         this.field2751 = var1.method13095(-346779531) * -2015926021;
         this.field2750 = var1.method13095(-346779531) * -765552747;
         this.field2752 = var1.method13099(-1163671264) * -735630941;
         this.field2753 = var1.method13099(563495098) * -1209921667;
         this.field2567 = var1.method13095(-346779531) * 113218633;
         this.field2566 = var1.method13095(-346779531) * 2071171191;
         this.field2749 = var1.method13099(-847400085) * -1652832041;
         this.field2748 = var1.method13099(-1658254727) * -1381327085;
         this.field2565 = var1.method13095(-346779531) * 1465087555;
         this.field2564 = var1.method13095(-346779531) * 747418529;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgf;)I")
   @ObfuscatedName("no")
   public static int method5438(classGF var0) {
      return var0.field2749 * 1151970023 | -456391909 * var0.field2748 << 8 | var0.field2565 * 1304954475 << 16 | 27914337 * var0.field2564 << 24;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ao")
   void method5433(classXY var1) {
      int var2 = var1.method13095(-346779531);
      if (var2 != 1177860011 * classHI.field2801.field2799) {
         throw new IllegalStateException("");
      } else {
         this.field2751 = var1.method13095(-346779531) * -2015926021;
         this.field2750 = var1.method13095(-346779531) * -765552747;
         this.field2752 = var1.method13099(-61034170) * -735630941;
         this.field2753 = var1.method13099(685524750) * -1209921667;
         this.field2567 = var1.method13095(-346779531) * 113218633;
         this.field2566 = var1.method13095(-346779531) * 2071171191;
         this.field2749 = var1.method13099(1045258800) * -1652832041;
         this.field2748 = var1.method13099(1695287848) * -1381327085;
         this.field2565 = var1.method13095(-346779531) * 1465087555;
         this.field2564 = var1.method13095(-346779531) * 747418529;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgf;)I")
   @ObfuscatedName("sl")
   public static int method5439(classGF var0) {
      return var0.field2749 * 255931660 | 1712704720 * var0.field2748 << 8 | var0.field2565 * 1304954475 << 16 | 27914337 * var0.field2564 << 24;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgf;)I")
   @ObfuscatedName("qv")
   public static int method5440(classGF var0) {
      return var0.field2749 * 1151970023 | -456391909 * var0.field2748 << 8 | var0.field2565 * 1304954475 << 16 | 27914337 * var0.field2564 << 24;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method5437(Object var1) {
      if (!(var1 instanceof classGF)) {
         return false;
      } else {
         classGF var2 = (classGF)var1;
         return this.field2749 * 1151970023 == var2.field2749 * 1151970023 && var2.field2748 * -456391909 == this.field2748 * 1545877767
            ? this.field2565 * 1312316055 == var2.field2565 * 1304954475 && var2.field2564 * 1479451307 == 27914337 * this.field2564
            : false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod468(classXY var1) {
      this.field2750 = Math.min(387424936 * this.field2750, 4) * -765552747;
      this.field2756 = new short[1][1138436403][64];
      this.field2757 = new short[-1667236931 * this.field2750][-396746423][824068161];
      this.field2755 = new byte[-911637386 * this.field2750][-1930861672][64];
      this.field2754 = new byte[-1667236931 * this.field2750][-278110485][64];
      this.field2758 = new classHZ[-1667236931 * this.field2750][64][64][];

      for (int var2 = 0; var2 < 8; var2++) {
         for (int var3 = 0; var3 < 8; var3++) {
            this.method6001(this.field2565 * 1849701208 + var2, var3 + this.field2564 * 223314696, var1, (byte)27);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   int method5446() {
      return this.field2566 * 1619478855;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;)Z")
   @ObfuscatedName("ca")
   public static boolean method5434(NameableContainer var0) {
      return var0.field5838 * 488407471 == 1744080879 * var0.field5840;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method5441() {
      return this.field2749 * 1151970023 | -456391909 * this.field2748 << 8 | this.field2565 * 1304954475 << 16 | 27914337 * this.field2564 << 24;
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof classGF)) {
            return false;
         } else {
            classGF var2 = (classGF)var1;
            return this.field2749 * 1151970023 == var2.field2749 * 1151970023 && var2.field2748 * -456391909 == this.field2748 * -456391909
               ? this.field2565 * 1304954475 == var2.field2565 * 1304954475 && var2.field2564 * 27914337 == 27914337 * this.field2564
               : false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gf.equals(" + ')');
      }
   }

   @Override
   public int hashCode() {
      try {
         return this.field2749 * 1151970023 | -456391909 * this.field2748 << 8 | this.field2565 * 1304954475 << 16 | 27914337 * this.field2564 << 24;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gf.hashCode(" + 41);
      }
   }

   classGF() {
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int method5443() {
      return -201810439 * this.field2567;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   int method5444() {
      return -81434242 * this.field2567;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   int method5447() {
      return this.field2566 * 1619478855;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   int method5448(int var1) {
      try {
         return 1304954475 * this.field2565;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gf.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgf;I)I")
   @ObfuscatedName("xg")
   public static int method5453(classGF var0, int var1) {
      if (var0 == null) {
         var0.method5452(var1);
      }

      try {
         return var0.field2564 * 27914337;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gf.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   int method5449() {
      return 1304954475 * this.field2565;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   int method5450() {
      return 1304954475 * this.field2565;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   int method5451() {
      return 1304954475 * this.field2565;
   }

   @ObfuscatedSignature(descriptor = "([FIFB)F")
   @ObfuscatedName("af")
   static float method5454(float[] var0, int var1, float var2, byte var3) {
      try {
         float var4 = var0[var1];

         for (int var5 = var1 - 1; var5 >= 0; var5--) {
            if (var3 != 10) {
               throw new IllegalStateException();
            }

            var4 = var2 * var4 + var0[var5];
         }

         return var4;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gf.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)Lcg;")
   @ObfuscatedName("ae")
   static MessageNode method5435(int var0, int var1, byte var2) {
      try {
         ChatLineBuffer var3 = (ChatLineBuffer)classCN.field1169.get(var0);
         return var3.method1252(var1, 1248547089);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gf.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ac")
   int method5452(int var1) {
      try {
         return 1304954475 * this.field2564;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gf.ax(" + 41);
      }
   }
}
