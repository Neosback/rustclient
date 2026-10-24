import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gn")
public class classGN {
   @ObfuscatedName("ax")
   int field2629;
   @ObfuscatedName("af")
   final int[][] field2622;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("an")
   public static IndexDataBase field2631;
   @ObfuscatedName("ab")
   final int[] field2621;
   @ObfuscatedName("az")
   final int[][] field2625;
   @ObfuscatedName("as")
   int field2628;
   @ObfuscatedName("ae")
   final int[] field2623;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   public static final int field2627 = 8;
   @ObfuscatedName("ag")
   final int field2624;
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   static final String field2630 = "wm";
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   public static final int field2626 = 256;

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ap")
   void method5544(int var1, int var2) {
      this.field2628 = var1 * -1574930361;
      this.field2629 = var2 * -476938375;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   int method5551(int var1) {
      try {
         return 1050179627 * this.field2629;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()[[I")
   @ObfuscatedName("bg")
   int[][] method5565() {
      return this.field2622;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   int method5560() {
      return this.field2625[0].length;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   int method5554(int var1) {
      try {
         return this.field2625.length;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   int method5561(int var1) {
      try {
         return this.field2625[0].length;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bw")
   int method5555() {
      return this.field2625.length;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bp")
   int method5548() {
      return 1086959991 * this.field2628;
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("aa")
   int[] method5570(int var1) {
      try {
         return this.field2623;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgn;S)[[I")
   @ObfuscatedName("ua")
   public static int[][] method5566(classGN var0, short var1) {
      if (var0 == null) {
         var0.method5569(var1);
      }

      try {
         return var0.field2622;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ao")
   int[] method5572(int var1) {
      try {
         return this.field2621;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldg;)I")
   @ObfuscatedName("af")
   public static int method5539(classDG var0) {
      return var0 == null ? var0.method3069() : var0.field1428 * -110280257;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("al")
   int method5575(byte var1) {
      try {
         return this.field2624 * -1760234091;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.al(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   void method5540() {
      for (int var1 = 0; var1 < this.field2625.length; var1++) {
         for (int var2 = 0; var2 < this.field2625[var1].length; var2++) {
            this.field2625[var1][var2] = 0;
            this.field2622[var1][var2] = 99999999;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method5541() {
      for (int var1 = 0; var1 < this.field2625.length; var1++) {
         for (int var2 = 0; var2 < this.field2625[var1].length; var2++) {
            this.field2625[var1][var2] = 0;
            this.field2622[var1][var2] = 99999999;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ad")
   void method5545(int var1, int var2) {
      this.field2628 = var1 * 1666635716;
      this.field2629 = var2 * 374245466;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method5542() {
      for (int var1 = 0; var1 < this.field2625.length; var1++) {
         for (int var2 = 0; var2 < this.field2625[var1].length; var2++) {
            this.field2625[var1][var2] = 0;
            this.field2622[var1][var2] = 99999999;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   void method5543(byte var1) {
      try {
         for (int var2 = 0; var2 < this.field2625.length; var2++) {
            if (var1 >= 0) {
               throw new IllegalStateException();
            }

            for (int var3 = 0; var3 < this.field2625[var2].length; var3++) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               this.field2625[var2][var3] = 0;
               this.field2622[var2][var3] = 99999999;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gn.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[[I")
   @ObfuscatedName("ax")
   int[][] method5563(int var1) {
      try {
         return this.field2625;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   int method5549() {
      return 1086959991 * this.field2628;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   int method5552() {
      return 1050179627 * this.field2629;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   int method5553() {
      return 1050179627 * this.field2629;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("au")
   void method5546(int var1, int var2) {
      this.field2628 = var1 * -1574930361;
      this.field2629 = var2 * 2101118595;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bc")
   int method5556() {
      return this.field2625.length;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   int method5557() {
      return this.field2625.length;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   int method5558() {
      return this.field2625.length;
   }

   @ObfuscatedSignature(descriptor = "()[[I")
   @ObfuscatedName("bm")
   int[][] method5564() {
      return this.field2625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   int method5562() {
      return this.field2625[0].length;
   }

   classGN(int var1, int var2) {
      this.field2625 = new int[var1][var2];
      this.field2622 = new int[var1][var2];
      int var3 = var1 * var2;
      int var4 = classBZ.method1387(var3 / 4, -1689796505);
      this.field2623 = new int[var4];
      this.field2621 = new int[var4];
      this.field2624 = -955404867 * (var4 - 1);
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ae")
   int method5550(short var1) {
      try {
         return 1086959991 * this.field2628;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("af")
   void method5547(int var1, int var2, int var3) {
      try {
         this.field2628 = var1 * -1574930361;
         this.field2629 = var2 * 2101118595;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gn.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[[I")
   @ObfuscatedName("bj")
   int[][] method5567() {
      return this.field2622;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bu")
   int[] method5571() {
      return this.field2623;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("ba")
   int[] method5573() {
      return this.field2621;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   int method5576() {
      return this.field2624 * -1760234091;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   int method5577() {
      return this.field2624 * -1760234091;
   }

   @ObfuscatedSignature(descriptor = "()[[I")
   @ObfuscatedName("bt")
   int[][] method5568() {
      return this.field2622;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   static final void method5578(int var0) {
      try {
         classLP.field4194 = null;
         classOX.field5190 = null;
         classEY.field1981 = 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gn.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   static int method5537(byte var0) {
      try {
         return classDG.method3067(98, 198, (byte)53);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gn.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Loy;")
   @ObfuscatedName("az")
   public static classOY method5538(int var0, byte var1) {
      try {
         classOY var2 = (classOY)NodeCache.method6460(classOY.field5192, var0);
         if (null != var2) {
            if (var1 != 1) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classOY.field5199.method11942(16, var0, -2032314649);
            var2 = new classOY(new classXY(var3));
            classOY.field5192.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gn.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bb")
   int method5559() {
      return this.field2625.length;
   }

   @ObfuscatedSignature(descriptor = "(S)[[I")
   @ObfuscatedName("ac")
   int[][] method5569(short var1) {
      try {
         return this.field2622;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gn.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bo")
   int[] method5574() {
      return this.field2621;
   }
}
