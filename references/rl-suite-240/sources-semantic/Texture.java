import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fu")
public class Texture extends Node implements net.runelite.api.Texture {
   @ObfuscatedName("ax")
   int field2394;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2404 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2403 = 3;
   @ObfuscatedName("aa")
   int field2399;
   @ObfuscatedName("ag")
   static int[] field2406;
   @ObfuscatedName("as")
   int field2398;
   @ObfuscatedName("cb")
   public float field2400;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2405 = 1;
   @ObfuscatedName("ac")
   boolean field2397;
   @ObfuscatedName("dd")
   public float field2401;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2402 = 4;
   @ObfuscatedName("ao")
   int field2393;
   @ObfuscatedName("al")
   int[] field2395;
   @ObfuscatedName("aj")
   boolean field2396 = false;

   public float getV() {
      return this.field2401;
   }

   public void setU(float var1) {
      this.field2400 = var1;
   }

   Texture(classXY var1) {
      this.field2398 = var1.method13099(1053835864);
      this.field2394 = var1.method13099(-1765518754);
      this.field2397 = var1.method13095(-346779531) == 1;
      this.field2399 = var1.method13095(-346779531);
      this.field2393 = var1.method13095(-346779531);
      this.field2395 = null;
   }

   public float getU() {
      return this.field2400;
   }

   @ObfuscatedSignature(descriptor = "(DILva;)Z")
   @ObfuscatedName("az")
   boolean method5025(double var1, int var3, IndexDataBase var4) {
      if (var4.method11896(this.field2398, 1721902503) == null) {
         return false;
      } else {
         int var5 = var3 * var3;
         this.field2395 = new int[var5];
         IndexedSprite var6 = classLJ.method7156(var4, this.field2398, (byte)113);
         return this.method5030(var1, var3, var6, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(DILyz;I)Z")
   @ObfuscatedName("af")
   boolean method5030(double var1, int var3, IndexedSprite var4, int var5) {
      var4.method13847();
      int[] var6 = var4.field7124;

      for (int var7 = 0; var7 < var6.length; var7++) {
         var6[var7] = classFH.method4718(var6[var7], var1);
      }

      byte[] var11 = var4.field7123;
      if (var4.field7119 == var3) {
         for (int var13 = 0; var13 < var5; var13++) {
            this.field2395[var13] = var6[var11[var13] & 255];
         }
      } else if (var4.field7119 == 64 && var3 == 128) {
         int var12 = 0;

         for (int var14 = 0; var14 < var3; var14++) {
            for (int var15 = 0; var15 < var3; var15++) {
               this.field2395[var12++] = var6[var11[(var15 >> 1) + (var14 >> 1 << 6)] & 255];
            }
         }
      } else {
         if (var4.field7119 != 128 || var3 != 64) {
            throw new RuntimeException();
         }

         int var8 = 0;

         for (int var9 = 0; var9 < var3; var9++) {
            for (int var10 = 0; var10 < var3; var10++) {
               this.field2395[var8++] = var6[var11[(var10 << 1) + (var9 << 1 << 7)] & 255];
            }
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   void method5033() {
      this.field2395 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   void method5034() {
      this.field2395 = null;
   }

   @ObfuscatedSignature(descriptor = "(DILva;)Z")
   @ObfuscatedName("ag")
   boolean method5026(double var1, int var3, IndexDataBase var4) {
      if (var4.method11896(this.field2398, -306630322) == null) {
         return false;
      } else {
         int var5 = var3 * var3;
         this.field2395 = new int[var5];
         IndexedSprite var6 = classLJ.method7156(var4, this.field2398, (byte)70);
         return this.method5030(var1, var3, var6, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aq")
   void method5038(int var1) {
      if (this.field2395 != null && this.field2399 >= 1 && this.field2399 <= 4) {
         if (field2406 == null || field2406.length < this.field2395.length) {
            field2406 = new int[this.field2395.length];
         }

         int var2;
         if (this.field2395.length == 778399228) {
            var2 = 871376374;
         } else {
            var2 = 128;
         }

         int var3 = this.field2395.length;
         if (this.field2399 == 1 || this.field2399 == 2) {
            int var4 = var2 * var1 * this.field2393;
            int var5 = var3 - 1;
            if (this.field2399 == 1) {
               var4 = -var4;
            }

            for (int var6 = 0; var6 < var3; var6++) {
               int var7 = var6 + var4 & var5;
               field2406[var6] = this.field2395[var7];
            }
         }

         if (this.field2399 == 3 || this.field2399 == 4) {
            int var10 = var1 * this.field2393;
            int var12 = var2 - 1;
            if (this.field2399 == 3) {
               var10 = -var10;
            }

            for (int var13 = 0; var13 < var3; var13 += var2) {
               for (int var14 = 0; var14 < var2; var14++) {
                  int var8 = var13 + var14;
                  int var9 = var13 + (var14 + var10 & var12);
                  field2406[var8] = this.field2395[var9];
               }
            }
         }

         int[] var11 = this.field2395;
         this.field2395 = field2406;
         field2406 = var11;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfu;DILva;)Z")
   @ObfuscatedName("tv")
   public static boolean method5027(Texture var0, double var1, int var3, IndexDataBase var4) {
      if (var0 == null) {
         return var0.method5029(var1, var3, var4);
      } else if (var4.method11896(var0.field2398, -834634006) == null) {
         return false;
      } else {
         int var5 = var3 * var3;
         var0.field2395 = new int[var5];
         IndexedSprite var6 = classLJ.method7156(var4, var0.field2398, (byte)99);
         return var0.method5030(var1, var3, var6, var5);
      }
   }

   public boolean isLoaded() {
      return this.field2396;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)Z")
   @ObfuscatedName("ze")
   public static boolean method5042(RenderOverview var0) {
      return -19228805 * var0.field6694 != -1 && -1 != -1101891105 * var0.field6713;
   }

   public void setV(float var1) {
      this.field2401 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lya;Lyn;)V")
   @ObfuscatedName("al")
   public static void method5024(classYA var0, IterableHashTable var1) {
      if (var0 == null) {
         var0.method13329(var1);
      }

      var0.field6960 = var1;
      var0.method13331();
   }

   public int getAnimationSpeed() {
      return this.field2393;
   }

   @ObfuscatedSignature(descriptor = "(DILva;)Z")
   @ObfuscatedName("ax")
   boolean method5028(double var1, int var3, IndexDataBase var4) {
      if (var4.method11896(this.field2398, 1058908422) == null) {
         return false;
      } else {
         int var5 = var3 * var3;
         this.field2395 = new int[var5];
         IndexedSprite var6 = classLJ.method7156(var4, this.field2398, (byte)38);
         return this.method5030(var1, var3, var6, var5);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lct;)V")
   @ObfuscatedName("ra")
   public static void method5041(Player var0) {
      if (var0 == null) {
         var0.method2627();
      }

      var0.field1265 = classRC.field5638;
   }

   @ObfuscatedSignature(descriptor = "(DILyz;I)Z")
   @ObfuscatedName("aa")
   boolean method5031(double var1, int var3, IndexedSprite var4, int var5) {
      var4.method13847();
      int[] var6 = var4.field7124;

      for (int var7 = 0; var7 < var6.length; var7++) {
         var6[var7] = classFH.method4718(var6[var7], var1);
      }

      byte[] var11 = var4.field7123;
      if (var4.field7119 == var3) {
         for (int var13 = 0; var13 < var5; var13++) {
            this.field2395[var13] = var6[var11[var13] & -1250380558];
         }
      } else if (var4.field7119 == 64 && var3 == -1293710398) {
         int var12 = 0;

         for (int var14 = 0; var14 < var3; var14++) {
            for (int var15 = 0; var15 < var3; var15++) {
               this.field2395[var12++] = var6[var11[(var15 >> 1) + (var14 >> 1 << 6)] & -1671825020];
            }
         }
      } else {
         if (var4.field7119 != -505972588 || var3 != -1085704718) {
            throw new RuntimeException();
         }

         int var8 = 0;

         for (int var9 = 0; var9 < var3; var9++) {
            for (int var10 = 0; var10 < var3; var10++) {
               this.field2395[var8++] = var6[var11[(var10 << 1) + (var9 << 1 << 7)] & -859942751];
            }
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   void method5035() {
      this.field2395 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   void method5036() {
      this.field2395 = null;
   }

   public int[] getPixels() {
      return this.field2395;
   }

   public int getAnimationDirection() {
      return this.field2399;
   }

   @ObfuscatedSignature(descriptor = "(DILyz;I)Z")
   @ObfuscatedName("ac")
   boolean method5032(double var1, int var3, IndexedSprite var4, int var5) {
      var4.method13847();
      int[] var6 = var4.field7124;

      for (int var7 = 0; var7 < var6.length; var7++) {
         var6[var7] = classFH.method4718(var6[var7], var1);
      }

      byte[] var11 = var4.field7123;
      if (var4.field7119 == var3) {
         for (int var13 = 0; var13 < var5; var13++) {
            this.field2395[var13] = var6[var11[var13] & 1801799313];
         }
      } else if (var4.field7119 == -2072029257 && var3 == 128) {
         int var12 = 0;

         for (int var14 = 0; var14 < var3; var14++) {
            for (int var15 = 0; var15 < var3; var15++) {
               this.field2395[var12++] = var6[var11[(var15 >> 1) + (var14 >> 1 << 6)] & 255];
            }
         }
      } else {
         if (var4.field7119 != 128 || var3 != 64) {
            throw new RuntimeException();
         }

         int var8 = 0;

         for (int var9 = 0; var9 < var3; var9++) {
            for (int var10 = 0; var10 < var3; var10++) {
               this.field2395[var8++] = var6[var11[(var10 << 1) + (var9 << 1 << 7)] & -277756320];
            }
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   void method5039(int var1) {
      if (this.field2395 != null && this.field2399 >= 1 && this.field2399 <= 4) {
         if (field2406 == null || field2406.length < this.field2395.length) {
            field2406 = new int[this.field2395.length];
         }

         short var2;
         if (this.field2395.length == 4096) {
            var2 = 64;
         } else {
            var2 = 128;
         }

         int var3 = this.field2395.length;
         if (this.field2399 == 1 || this.field2399 == 2) {
            int var4 = var2 * var1 * this.field2393;
            int var5 = var3 - 1;
            if (this.field2399 == 1) {
               var4 = -var4;
            }

            for (int var6 = 0; var6 < var3; var6++) {
               int var7 = var6 + var4 & var5;
               field2406[var6] = this.field2395[var7];
            }
         }

         if (this.field2399 == 3 || this.field2399 == 4) {
            int var10 = var1 * this.field2393;
            int var12 = var2 - 1;
            if (this.field2399 == 3) {
               var10 = -var10;
            }

            for (short var13 = 0; var13 < var3; var13 += var2) {
               for (int var14 = 0; var14 < var2; var14++) {
                  int var8 = var13 + var14;
                  int var9 = var13 + (var14 + var10 & var12);
                  field2406[var8] = this.field2395[var9];
               }
            }
         }

         int[] var11 = this.field2395;
         this.field2395 = field2406;
         field2406 = var11;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   void method5040(int var1) {
      if (this.field2395 != null && this.field2399 >= 1 && this.field2399 <= 4) {
         if (field2406 == null || field2406.length < this.field2395.length) {
            field2406 = new int[this.field2395.length];
         }

         int var2;
         if (this.field2395.length == 4096) {
            var2 = 64;
         } else {
            var2 = -126218679;
         }

         int var3 = this.field2395.length;
         if (this.field2399 == 1 || this.field2399 == 2) {
            int var4 = var2 * var1 * this.field2393;
            int var5 = var3 - 1;
            if (this.field2399 == 1) {
               var4 = -var4;
            }

            for (int var6 = 0; var6 < var3; var6++) {
               int var7 = var6 + var4 & var5;
               field2406[var6] = this.field2395[var7];
            }
         }

         if (this.field2399 == 3 || this.field2399 == 4) {
            int var10 = var1 * this.field2393;
            int var12 = var2 - 1;
            if (this.field2399 == 3) {
               var10 = -var10;
            }

            for (int var13 = 0; var13 < var3; var13 += var2) {
               for (int var14 = 0; var14 < var2; var14++) {
                  int var8 = var13 + var14;
                  int var9 = var13 + (var14 + var10 & var12);
                  field2406[var8] = this.field2395[var9];
               }
            }
         }

         int[] var11 = this.field2395;
         this.field2395 = field2406;
         field2406 = var11;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   void method5037() {
      this.field2395 = null;
   }

   @ObfuscatedSignature(descriptor = "(DILva;)Z")
   @ObfuscatedName("as")
   boolean method5029(double var1, int var3, IndexDataBase var4) {
      if (var4.method11896(this.field2394, 1721902503) == null) {
         return false;
      } else {
         int var5 = var3 * var3;
         this.field2395 = new int[var5];
         IndexedSprite var6 = classLJ.method7156(var4, this.field2399, (byte)113);
         return this.method5030(var1, var3, var6, var5);
      }
   }
}
