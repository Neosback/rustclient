import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fj")
public final class SceneTilePaint implements net.runelite.api.SceneTilePaint {
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field2288 = 2;
   @ObfuscatedName("af")
   int field2286;
   @ObfuscatedName("ae")
   int field2284;
   @ObfuscatedName("ou")
   public int field2290;
   @ObfuscatedName("ab")
   int field2282;
   @ObfuscatedName("az")
   int field2283;
   @ObfuscatedName("as")
   public boolean field2281 = true;
   @ObfuscatedName("ao")
   static final int[][] field2280 = new int[][]{{2, 3, 1}, {0, 1, 3}};
   @ObfuscatedName("ac")
   static final int[] field2278 = new int[]{0, 1, 1, 0};
   @ObfuscatedName("aa")
   static final int[] field2279 = new int[]{0, 0, 1, 1};
   @ObfuscatedName("rp")
   public int field2291;
   @ObfuscatedName("mq")
   public int field2289;
   @ObfuscatedName("ag")
   int field2285;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("dh")
   static IndexedSprite field2292;
   @ObfuscatedName("ax")
   int field2287;

   @ObfuscatedSignature(descriptor = "(IB)Lps;")
   @ObfuscatedName("az")
   public static classPS method4817(int var0, byte var1) {
      try {
         if (var0 >= 0 && var0 < classYV.field7093.length) {
            if (var1 >= 13) {
               throw new IllegalStateException();
            }

            if (classYV.field7093[var0] != null) {
               if (var1 >= 13) {
                  throw new IllegalStateException();
               }

               return classYV.field7093[var0];
            }
         }

         return new classPS(var0);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fj.az(" + ')');
      }
   }

   public int getSwColor() {
      return this.field2283 * 1145127777;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I[[[IIII[I[I[I)V")
   @ObfuscatedName("ab")
   public static void method4815(int var0, int[][][] var1, int var2, int var3, int var4, int[] var5, int[] var6, int[] var7) {
      int[] var8 = field2280[var0];

      for (int var9 = 0; var9 < var8.length; var9++) {
         int var13 = var8[var9];
         int var12 = (var3 << 7) + field2278[var13] * 128;
         var5[var9] = var12;
         var6[var9] = VarbitComposition.method9251(var8[var9], var1, var2, var3, var4, (byte)9);
         var7[var9] = classFO.method4867(var8[var9], var4, -45520089);
      }
   }

   public int getRBG() {
      return this.field2287 * 1843846065;
   }

   public int getNeColor() {
      return this.field2284 * -665850825;
   }

   public void setNwColor(int var1) {
      this.field2282 = var1 * 863961671;
   }

   public int getTexture() {
      return this.field2285 * 1479555085;
   }

   public int getUvBufferOffset() {
      return this.field2290;
   }

   SceneTilePaint(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7) {
      this.field2283 = -807886687 * var1;
      this.field2286 = var2 * -241249223;
      this.field2284 = -862484089 * var3;
      this.field2282 = var4 * 863961671;
      this.field2285 = -2018322747 * var5;
      this.field2287 = var6 * 1664388433;
      this.field2281 = var7;
   }

   public void setSwColor(int var1) {
      this.field2283 = var1 * -807886687;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I[[[IIII[I[I[I)V")
   @ObfuscatedName("ag")
   public static void method4816(int var0, int[][][] var1, int var2, int var3, int var4, int[] var5, int[] var6, int[] var7) {
      int[] var8 = field2280[var0];

      for (int var9 = 0; var9 < var8.length; var9++) {
         int var13 = var8[var9];
         int var12 = (var3 << 7) + field2278[var13] * 128;
         var5[var9] = var12;
         var6[var9] = VarbitComposition.method9251(var8[var9], var1, var2, var3, var4, (byte)-32);
         var7[var9] = classFO.method4867(var8[var9], var4, 1512551737);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   static int method4818(int var0, int var1) {
      return (var1 << 7) + 128 * field2279[var0];
   }

   public int getSeColor() {
      return this.field2286 * -633153015;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ac")
   static int method4819(int var0, int var1) {
      return (var1 << 7) + 128 * field2279[var0];
   }

   public int getBufferOffset() {
      return this.field2289;
   }

   public int getBufferLen() {
      return this.field2291;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aa")
   static int method4820(int var0, int var1) {
      return (var1 << 7) + 1234770612 * field2279[var0];
   }

   public int getNwColor() {
      return this.field2282 * 1860831095;
   }

   public void setTexture(int var1) {
      this.field2285 = var1 * -2018322747;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I[[[IIII)I")
   @ObfuscatedName("ao")
   static int method4822(int var0, int[][][] var1, int var2, int var3, int var4) {
      return var1[var2][field2278[var0] + var3][field2279[var0] + var4];
   }

   @ObfuscatedSignature(descriptor = "(IIIIISISIII)V")
   @ObfuscatedName("db")
   static void method4824(int var0, int var1, int var2, int var3, int var4, short var5, int var6, short var7, int var8, int var9, int var10) {
      try {
         WorldView.method3788(classCQ.field1218, var0, var1, var2, var3, var4, var5, var6, var7, var8, var9, 1465980917);
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "fj.db(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ax")
   static int method4821(int var0, int var1) {
      return (var1 << 7) + 128 * field2279[var0];
   }

   public void setNeColor(int var1) {
      this.field2284 = var1 * -862484089;
   }

   public void setUvBufferOffset(int var1) {
      this.field2290 = var1;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;I)Lte;")
   @ObfuscatedName("aq")
   static classTE method4823(String[] var0, int var1) {
      try {
         classTE var2 = new classTE(classYY.field7111, true);
         var2.field6123 = var0;
         var2.field6129 = -1063196331 * var0.length;
         var2.field6121 = var0.length * 404453943;
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fj.aq(" + ')');
      }
   }

   public void setSeColor(int var1) {
      this.field2286 = var1 * -241249223;
   }

   public boolean isFlat() {
      return this.field2281;
   }

   public void setBufferOffset(int var1) {
      this.field2289 = var1;
   }

   public void setBufferLen(int var1) {
      this.field2291 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljl;")
   @ObfuscatedName("az")
   static classJL method4814(int var0) {
      try {
         if (1275112637 * classJL.field3332 == 0) {
            if (var0 <= 1375698635) {
               throw new IllegalStateException();
            } else {
               return new classJL();
            }
         } else {
            return classJL.field3331[(classJL.field3332 -= 766488213) * 1275112637];
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "fj.az(" + ')');
      }
   }
}
