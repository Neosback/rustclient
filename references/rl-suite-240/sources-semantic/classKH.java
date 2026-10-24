import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kh")
public class classKH extends classVJ {
   @ObfuscatedName("az")
   public final int field3584;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field3587 = 30;
   @ObfuscatedName("ae")
   public final int[] field3582;
   @ObfuscatedName("ab")
   public final int[] field3585;
   @ObfuscatedName("af")
   public final int field3583;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field3586 = 32;

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("af")
   public boolean method6514(int var1, int var2) {
      if (var2 >= 0 && var2 < this.field3585.length) {
         int var3 = this.field3585[var2];
         if (var1 >= var3 && var1 <= var3 + this.field3582[var2]) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ic")
   public boolean method6515(int var1, int var2) {
      if (var2 >= 0 && var2 < this.field3585.length) {
         int var3 = this.field3585[var2];
         if (var1 >= var3 && var1 <= var3 + this.field3582[var2]) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("pz")
   public int[] method6517() {
      return this.field3582;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ae")
   public boolean method6518(int var1, int var2) {
      return this.method6516(var1, var2, 700473093);
   }

   @ObfuscatedSignature(descriptor = "(Lva;S)I")
   @ObfuscatedName("af")
   static int method6513(IndexDataBase var0, short var1) {
      try {
         int var2 = classBF.field413.length + classBF.field410.length;
         String[] var3 = classBF.field386;

         for (int var4 = 0; var4 < var3.length; var4++) {
            String var5 = var3[var4];
            if (IndexDataBase.method11918(var0, var5, -123140444) != -1) {
               if (var1 >= 255) {
                  throw new IllegalStateException();
               }

               var2++;
            }
         }

         return var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "kh.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("az")
   public boolean method6516(int var1, int var2, int var3) {
      try {
         if (var2 >= 0) {
            if (var3 <= -2140697775) {
               throw new IllegalStateException();
            }

            if (var2 < this.field3585.length) {
               int var4 = this.field3585[var2];
               if (var1 >= var4) {
                  if (var3 <= -2140697775) {
                     throw new IllegalStateException();
                  }

                  if (var1 <= var4 + this.field3582[var2]) {
                     if (var3 <= -2140697775) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }
         }

         return false;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "kh.az(" + ')');
      }
   }

   classKH(int var1, int var2, int[] var3, int[] var4, int var5) {
      this.field3584 = var1 * 1709305797;
      this.field3583 = var2 * -38113611;
      this.field3582 = var3;
      this.field3585 = var4;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("za")
   public int method6519() {
      return this.field3584 * -1246906611;
   }

   @ObfuscatedSignature(descriptor = "([IB)Lte;")
   @ObfuscatedName("aj")
   public static classTE method6512(int[] var0, byte var1) {
      try {
         classTE var2 = new classTE(classYY.field7109, true);
         var2.field6124 = var0;
         var2.field6129 = var0.length * -1063196331;
         var2.field6121 = var0.length * 404453943;
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "kh.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("nd")
   public int[] method6520() {
      return this.field3585;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("rq")
   public int method6521() {
      return this.field3583 * -1637380195;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;[JIII)V")
   @ObfuscatedName("ag")
   public static void method6511(String[] var0, long[] var1, int var2, int var3, int var4) {
      try {
         if (var2 < var3) {
            int var5 = (var3 + var2) / 2;
            int var6 = var2;
            String var7 = var0[var5];
            var0[var5] = var0[var3];
            var0[var3] = var7;
            long var8 = var1[var5];
            var1[var5] = var1[var3];
            var1[var3] = var8;

            for (int var10 = var2; var10 < var3; var10++) {
               if (var4 == -470927658) {
                  throw new IllegalStateException();
               }

               if (var7 != null) {
                  if (var4 == -470927658) {
                     return;
                  }

                  if (null == var0[var10] || var0[var10].compareTo(var7) >= (var10 & 1)) {
                     continue;
                  }

                  if (var4 == -470927658) {
                     throw new IllegalStateException();
                  }
               }

               String var11 = var0[var10];
               var0[var10] = var0[var6];
               var0[var6] = var11;
               long var12 = var1[var10];
               var1[var10] = var1[var6];
               var1[var6++] = var12;
            }

            var0[var3] = var0[var6];
            var0[var6] = var7;
            var1[var3] = var1[var6];
            var1[var6] = var8;
            method6511(var0, var1, var2, var6 - 1, 797807956);
            method6511(var0, var1, var6 + 1, var3, 289945362);
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "kh.ag(" + ')');
      }
   }
}
