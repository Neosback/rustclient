import java.util.Arrays;
import java.util.Objects;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uu")
public final class classUU {
   @ObfuscatedName("ra")
   public float field6395;
   @ObfuscatedName("ub")
   public float field6397;
   @ObfuscatedName("ag")
   public float[] field6401;
   @ObfuscatedName("hs")
   public float field6383;
   @ObfuscatedSignature(descriptor = "Luu;")
   @ObfuscatedName("mu")
   public static classUU field6382 = new classUU();
   @ObfuscatedName("fz")
   public float field6392;
   @ObfuscatedName("ya")
   public float field6398;
   @ObfuscatedName("sx")
   public float field6396;
   @ObfuscatedName("ij")
   public float field6385;
   @ObfuscatedName("cd")
   public float field6389;
   @ObfuscatedName("cu")
   public float field6386;
   @ObfuscatedName("dn")
   public float field6390;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final float field6387 = 1.0E-4F;
   @ObfuscatedName("cb")
   public float field6384;
   @ObfuscatedName("ae")
   static final int field6379 = 887137936;
   @ObfuscatedName("ab")
   static int field6381 = 0;
   @ObfuscatedName("lw")
   public float field6394;
   @ObfuscatedSignature(descriptor = "[Luu;")
   @ObfuscatedName("af")
   static final classUU[] field6380 = new classUU[field6379 * -711449127];
   @ObfuscatedName("bb")
   public float field6388;
   @ObfuscatedName("yj")
   public float field6399;
   @ObfuscatedName("ht")
   public float field6393;
   @ObfuscatedName("fo")
   public float field6391;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field6400 = 15574765;

   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("zw")
   public static classUU method11711() {
      synchronized (field6380) {
         if (field6381 * -1618719293 == 0) {
            return new classUU();
         } else {
            classUU[] var10000 = field6380;
            int var10001 = field6381 * -1618719293 - 1;
            field6381 = (field6381 * -1618719293 - 1) * 943786731;
            classUU var1 = var10000[var10001];
            var1.method11724();
            return var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Ljava/lang/Object;)Z")
   @ObfuscatedName("hp")
   public static boolean method11699(classUU var0, Object var1) {
      if (var0 == null) {
         var0.method11622();
      }

      if (!(var1 instanceof classUU)) {
         return false;
      } else {
         classUU var2 = (classUU)var1;

         for (int var3 = 0; var3 < 16; var3++) {
            if (var0.field6401[var3] != var2.field6401[var3]) {
               return false;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("bq")
   public float[] method11628() {
      float[] var1 = new float[]{(float)(-Math.asin(this.field6396)), 0.0F, 0.0F};
      double var2 = Math.cos(var1[0]);
      if (Math.abs(var2) > 0.005) {
         double var4 = this.field6394;
         double var6 = this.field6385;
         double var8 = this.field6392;
         double var10 = this.field6384;
         var1[1] = (float)Math.atan2(var4, var6);
         var1[2] = (float)Math.atan2(var8, var10);
      } else {
         double var12 = this.field6398;
         double var13 = this.field6383;
         if (this.field6396 < 0.0F) {
            var1[1] = (float)Math.atan2(var12, var13);
         } else {
            var1[1] = (float)(-Math.atan2(var12, var13));
         }

         var1[2] = 0.0F;
      }

      return var1;
   }

   public classUU(classUU var1) {
      this.method11720(var1);
   }

   public classUU(classXY var1, boolean var2) {
      this.method11714(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Z)V")
   @ObfuscatedName("yt")
   public void method11714(classXY var1, boolean var2) {
      this.method11712(var1, var2, -1231321475);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   public void method11615(int var1) {
      try {
         synchronized (field6380) {
            if (field6381 * -1618719293 < -711449127 * field6379) {
               if (var1 <= 1525934998) {
                  throw new IllegalStateException();
               }

               field6380[(field6381 += 943786731) * -1618719293 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("vc")
   public static void method11634(classUU var0) {
      if (var0 == null) {
         var0.method11623();
      }

      var0.field6383 = 1.0F;
      var0.field6398 = 0.0F;
      var0.field6394 = 0.0F;
      var0.field6390 = 0.0F;
      var0.field6392 = 0.0F;
      var0.field6384 = 1.0F;
      var0.field6396 = 0.0F;
      var0.field6393 = 0.0F;
      var0.field6388 = 0.0F;
      var0.field6389 = 0.0F;
      var0.field6385 = 1.0F;
      var0.field6397 = 0.0F;
      var0.field6391 = 0.0F;
      var0.field6399 = 0.0F;
      var0.field6395 = 0.0F;
      var0.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;ZI)V")
   @ObfuscatedName("ab")
   void method11712(classXY var1, boolean var2, int var3) {
      if (var2) {
         boolean var5 = var2;
         classXY var4 = var1;
         classUU var18 = this;
         int var6 = -1231321475;

         try {
            if (var5) {
               classUX var7 = new classUX();
               int var10 = var4.method13104((byte)16);
               var10 &= 16383;
               float var9 = (float)(var10 * 3.834951969714103E-4);
               var7.method11748(var9, 173632492);
               int var13 = var4.method13104((byte)16);
               var13 &= 16383;
               float var12 = (float)(var13 * 3.834951969714103E-4);
               var7.method11753(var12, 982132619);
               int var16 = var4.method13104((byte)16);
               var16 &= 16383;
               float var15 = (float)(var16 * 3.834951969714103E-4);
               classUX.method11754(var7, var15, -2028165068);
               classUX.method11760(var7, var4.method13104((byte)16), var4.method13104((byte)16), var4.method13104((byte)16), (byte)105);
               method11668(var18, var7, -1959054899);
            } else {
               for (int var19 = 0; var19 < 16 && var6 > -1547886893; var19++) {
                  var18.field6401[var19] = var4.method13119(37945336);
               }
            }
         } catch (RuntimeException var17) {
            throw classEG.method3899(var17, "uu.ab(" + ')');
         }
      } else {
         this.field6383 = var1.method13326();
         this.field6398 = var1.method13326();
         this.field6394 = var1.method13326();
         this.field6390 = var1.method13326();
         this.field6392 = var1.method13326();
         this.field6384 = var1.method13326();
         this.field6396 = var1.method13326();
         this.field6393 = var1.method13326();
         this.field6388 = var1.method13326();
         this.field6389 = var1.method13326();
         this.field6385 = var1.method13326();
         this.field6397 = var1.method13326();
         this.field6391 = var1.method13326();
         this.field6399 = var1.method13326();
         this.field6395 = var1.method13326();
         this.field6386 = var1.method13326();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Lxy;Z)V")
   @ObfuscatedName("cv")
   public static void method11619(classUU var0, classXY var1, boolean var2) {
      if (var0 == null) {
         var0.method11705();
      }

      if (var2) {
         classUX var3 = new classUX();
         int var6 = var1.method13104((byte)16);
         var6 &= 1975280137;
         float var5 = (float)(var6 * 3.834951969714103E-4);
         var3.method11748(var5, 173632492);
         int var9 = var1.method13104((byte)16);
         var9 &= 16383;
         float var8 = (float)(var9 * 3.834951969714103E-4);
         var3.method11753(var8, 982132619);
         int var12 = var1.method13104((byte)16);
         var12 &= 16383;
         float var11 = (float)(var12 * 3.834951969714103E-4);
         classUX.method11754(var3, var11, -10133714);
         classUX.method11760(var3, var1.method13104((byte)16), var1.method13104((byte)16), var1.method13104((byte)16), (byte)26);
         method11668(var0, var3, -1959054899);
      } else {
         for (int var13 = 0; var13 < 16; var13++) {
            var0.field6401[var13] = var1.method13119(406632560);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ac")
   public void method11640(int var1) {
      try {
         this.field6383 = 0.0F;
         this.field6398 = 0.0F;
         this.field6394 = 0.0F;
         this.field6390 = 0.0F;
         this.field6392 = 0.0F;
         this.field6384 = 0.0F;
         this.field6396 = 0.0F;
         this.field6393 = 0.0F;
         this.field6388 = 0.0F;
         this.field6389 = 0.0F;
         this.field6385 = 0.0F;
         this.field6397 = 0.0F;
         this.field6391 = 0.0F;
         this.field6399 = 0.0F;
         this.field6395 = 0.0F;
         this.field6386 = 0.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uu.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   public void method11635(int var1) {
      try {
         this.field6383 = 1.0F;
         this.field6398 = 0.0F;
         this.field6394 = 0.0F;
         this.field6390 = 0.0F;
         this.field6392 = 0.0F;
         this.field6384 = 1.0F;
         this.field6396 = 0.0F;
         this.field6393 = 0.0F;
         this.field6388 = 0.0F;
         this.field6389 = 0.0F;
         this.field6385 = 1.0F;
         this.field6397 = 0.0F;
         this.field6391 = 0.0F;
         this.field6399 = 0.0F;
         this.field6395 = 0.0F;
         this.field6386 = 1.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uu.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF[FI)V")
   @ObfuscatedName("aw")
   public void method11677(float var1, float var2, float var3, float[] var4, int var5) {
      try {
         var4[0] = var1 * this.field6383 + var2 * this.field6392 + var3 * this.field6388 + this.field6391;
         var4[1] = var2 * this.field6384 + var1 * this.field6398 + var3 * this.field6389 + this.field6399;
         var4[2] = this.field6395 + (var3 * this.field6385 + (var2 * this.field6396 + var1 * this.field6394));
         if (var4.length > 3) {
            var4[3] = var1 * this.field6390 + this.field6393 * var2 + this.field6397 * var3 + this.field6386;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uu.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bl")
   public void method11636() {
      this.field6383 = 1.0F;
      this.field6398 = 0.0F;
      this.field6394 = 0.0F;
      this.field6390 = 0.0F;
      this.field6392 = 0.0F;
      this.field6384 = 1.0F;
      this.field6396 = 0.0F;
      this.field6393 = 0.0F;
      this.field6388 = 0.0F;
      this.field6389 = 0.0F;
      this.field6385 = 1.0F;
      this.field6397 = 0.0F;
      this.field6391 = 0.0F;
      this.field6399 = 0.0F;
      this.field6395 = 0.0F;
      this.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(FFFI)V")
   @ObfuscatedName("al")
   public void method11651(float var1, float var2, float var3, int var4) {
      try {
         this.method11635(-1605400150);
         this.field6383 = var1;
         this.field6384 = var2;
         this.field6385 = var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;I)V")
   @ObfuscatedName("ay")
   public void method11656(classUU var1, int var2) {
      try {
         float var3 = this.field6390 * var1.field6391 + (var1.field6388 * this.field6394 + (this.field6383 * var1.field6383 + this.field6398 * var1.field6392));
         float var4 = this.field6394 * var1.field6389 + (this.field6398 * var1.field6384 + this.field6383 * var1.field6398) + var1.field6399 * this.field6390;
         float var5 = this.field6390 * var1.field6395 + (this.field6398 * var1.field6396 + var1.field6394 * this.field6383 + var1.field6385 * this.field6394);
         float var6 = this.field6394 * var1.field6397 + (var1.field6390 * this.field6383 + this.field6398 * var1.field6393) + this.field6390 * var1.field6386;
         float var7 = this.field6384 * var1.field6392 + this.field6392 * var1.field6383 + var1.field6388 * this.field6396 + var1.field6391 * this.field6393;
         float var8 = this.field6396 * var1.field6389 + (this.field6392 * var1.field6398 + this.field6384 * var1.field6384) + var1.field6399 * this.field6393;
         float var9 = var1.field6396 * this.field6384 + var1.field6394 * this.field6392 + this.field6396 * var1.field6385 + var1.field6395 * this.field6393;
         float var10 = this.field6384 * var1.field6393 + this.field6392 * var1.field6390 + this.field6396 * var1.field6397 + var1.field6386 * this.field6393;
         float var11 = var1.field6391 * this.field6397
            + (var1.field6388 * this.field6385 + (var1.field6383 * this.field6388 + var1.field6392 * this.field6389));
         float var12 = this.field6397 * var1.field6399
            + (this.field6385 * var1.field6389 + (this.field6388 * var1.field6398 + this.field6389 * var1.field6384));
         float var13 = var1.field6385 * this.field6385 + (var1.field6396 * this.field6389 + var1.field6394 * this.field6388) + this.field6397 * var1.field6395;
         float var14 = this.field6397 * var1.field6386 + (this.field6388 * var1.field6390 + this.field6389 * var1.field6393 + this.field6385 * var1.field6397);
         float var15 = this.field6386 * var1.field6391 + (var1.field6383 * this.field6391 + this.field6399 * var1.field6392 + this.field6395 * var1.field6388);
         float var16 = this.field6386 * var1.field6399
            + (this.field6395 * var1.field6389 + (this.field6391 * var1.field6398 + this.field6399 * var1.field6384));
         float var17 = this.field6395 * var1.field6385 + (var1.field6394 * this.field6391 + this.field6399 * var1.field6396) + var1.field6395 * this.field6386;
         float var18 = var1.field6390 * this.field6391 + this.field6399 * var1.field6393 + this.field6395 * var1.field6397 + this.field6386 * var1.field6386;
         this.field6383 = var3;
         this.field6398 = var4;
         this.field6394 = var5;
         this.field6390 = var6;
         this.field6392 = var7;
         this.field6384 = var8;
         this.field6396 = var9;
         this.field6393 = var10;
         this.field6388 = var11;
         this.field6389 = var12;
         this.field6385 = var13;
         this.field6397 = var14;
         this.field6391 = var15;
         this.field6399 = var16;
         this.field6395 = var17;
         this.field6386 = var18;
      } catch (RuntimeException var19) {
         throw classEG.method3899(var19, "uu.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;I)V")
   @ObfuscatedName("ad")
   public void method11661(classUL var1, int var2) {
      try {
         float var3 = var1.field6325 * var1.field6325;
         float var4 = var1.field6322 * var1.field6325;
         float var5 = var1.field6323 * var1.field6325;
         float var6 = var1.field6324 * var1.field6325;
         float var7 = var1.field6322 * var1.field6322;
         float var8 = var1.field6323 * var1.field6322;
         float var9 = var1.field6322 * var1.field6324;
         float var10 = var1.field6323 * var1.field6323;
         float var11 = var1.field6323 * var1.field6324;
         float var12 = var1.field6324 * var1.field6324;
         this.field6383 = var3 + var7 - var12 - var10;
         this.field6398 = var8 + var6 + var8 + var6;
         this.field6394 = var9 - var5 - var5 + var9;
         this.field6392 = var8 + (var8 - var6 - var6);
         this.field6384 = var3 + var10 - var7 - var12;
         this.field6396 = var4 + (var11 + var4 + var11);
         this.field6388 = var5 + (var9 + var5 + var9);
         this.field6389 = var11 - var4 - var4 + var11;
         this.field6385 = var3 + var12 - var10 - var7;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "uu.ad(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)Luu;")
   @ObfuscatedName("bm")
   public static classUU method11610(classUU var0) {
      synchronized (field6380) {
         if (0 == field6381 * -1618719293) {
            return new classUU(var0);
         } else {
            classUZ.method11857(field6380[(field6381 -= 943786731) * -1258993443], var0, (byte)99);
            return field6380[field6381 * 1887936142];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFFI)V")
   @ObfuscatedName("ar")
   public void method11675(float var1, float var2, float var3, float var4, int var5) {
      try {
         this.field6383 = var1;
         this.field6398 = 0.0F;
         this.field6394 = 0.0F;
         this.field6390 = 0.0F;
         this.field6392 = 0.0F;
         this.field6384 = var2;
         this.field6396 = 0.0F;
         this.field6393 = 0.0F;
         this.field6388 = 0.0F;
         this.field6389 = 0.0F;
         this.field6385 = var3;
         this.field6397 = 0.0F;
         this.field6391 = 0.0F;
         this.field6399 = 0.0F;
         this.field6395 = 0.0F;
         this.field6386 = var4;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uu.ar(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Luu;)V")
   @ObfuscatedName("gd")
   public static void method11654(classUU var0, classUU var1) {
      if (var0 == null) {
         var0.method11721();
      }

      for (int var2 = 0; var2 < var0.field6401.length; var2++) {
         var0.field6401[var2] = var0.field6401[var2] + var1.field6401[var2];
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFI)F")
   @ObfuscatedName("at")
   public float method11687(float var1, float var2, float var3, int var4) {
      try {
         return this.field6399 + (var3 * this.field6389 + (this.field6384 * var2 + var1 * this.field6398));
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.at(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;FFF)F")
   @ObfuscatedName("jt")
   public static float method11688(classUU var0, float var1, float var2, float var3) {
      return var0.field6399 + (var3 * var0.field6389 + (var0.field6384 * var2 + var1 * var0.field6398));
   }

   static {
      new classUU();
   }

   @ObfuscatedSignature(descriptor = "(FFFI)F")
   @ObfuscatedName("an")
   public float method11689(float var1, float var2, float var3, int var4) {
      try {
         return this.field6385 * var3 + (this.field6396 * var2 + var1 * this.field6394) + this.field6395;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.an(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;FFFF)V")
   @ObfuscatedName("um")
   public static void method11676(classUU var0, float var1, float var2, float var3, float var4) {
      if (var0 == null) {
         var0.method11628();
      }

      var0.field6383 = var1;
      var0.field6398 = 0.0F;
      var0.field6394 = 0.0F;
      var0.field6390 = 0.0F;
      var0.field6392 = 0.0F;
      var0.field6384 = var2;
      var0.field6396 = 0.0F;
      var0.field6393 = 0.0F;
      var0.field6388 = 0.0F;
      var0.field6389 = 0.0F;
      var0.field6385 = var3;
      var0.field6397 = 0.0F;
      var0.field6391 = 0.0F;
      var0.field6399 = 0.0F;
      var0.field6395 = 0.0F;
      var0.field6386 = var4;
   }

   @Override
   public String toString() {
      return "Mat4{m0="
         + this.field6383
         + ", m1="
         + this.field6398
         + ", m2="
         + this.field6394
         + ", m3="
         + this.field6390
         + ", m4="
         + this.field6392
         + ", m5="
         + this.field6384
         + ", m6="
         + this.field6396
         + ", m7="
         + this.field6393
         + ", m8="
         + this.field6388
         + ", m9="
         + this.field6389
         + ", m10="
         + this.field6385
         + ", m11="
         + this.field6397
         + ", m12="
         + this.field6391
         + ", m13="
         + this.field6399
         + ", m14="
         + this.field6395
         + ", m15="
         + this.field6386
         + "}";
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("am")
   public float[] method11629(int var1) {
      try {
         float[] var2 = new float[]{(float)(-Math.asin(this.field6396)), 0.0F, 0.0F};
         double var3 = Math.cos(var2[0]);
         if (Math.abs(var3) > 0.005) {
            if (var1 == -1423591330) {
               throw new IllegalStateException();
            }

            double var5 = this.field6394;
            double var7 = this.field6385;
            double var9 = this.field6392;
            double var11 = this.field6384;
            var2[1] = (float)Math.atan2(var5, var7);
            var2[2] = (float)Math.atan2(var9, var11);
         } else {
            double var14 = this.field6398;
            double var15 = this.field6383;
            if (this.field6396 < 0.0F) {
               var2[1] = (float)Math.atan2(var14, var15);
            } else {
               var2[1] = (float)(-Math.atan2(var14, var15));
            }

            var2[2] = 0.0F;
         }

         return var2;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "uu.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)Luu;")
   @ObfuscatedName("bp")
   public static classUU method11611(classUU var0) {
      synchronized (field6380) {
         if (0 == field6381 * 280754414) {
            return new classUU(var0);
         } else {
            classUZ.method11857(field6380[(field6381 -= 943786731) * -1526793145], var0, (byte)42);
            return field6380[field6381 * 1190115707];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("bx")
   public static classUU method11607() {
      synchronized (field6380) {
         if (717099664 * field6381 == 0) {
            return new classUU();
         } else {
            field6380[(field6381 -= 1500138072) * -681667817].method11635(-107151084);
            return field6380[field6381 * 34801148];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;FFF)V")
   @ObfuscatedName("cp")
   public static void method11652(classUU var0, float var1, float var2, float var3) {
      if (var0 == null) {
         var0.method11695();
      }

      var0.method11635(-1660665592);
      var0.field6383 = var1;
      var0.field6384 = var2;
      var0.field6385 = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Luu;)V")
   @ObfuscatedName("fw")
   public static void method11642(classUU var0, classUU var1) {
      System.arraycopy(var1.field6401, 0, var0.field6401, 0, 16);
   }

   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("ln")
   public float method11715(float var1, float var2, float var3) {
      return method11685(this, var1, var2, var3, (byte)-6);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;)V")
   @ObfuscatedName("ia")
   public static void method11639(classVV var0) {
      if (var0 == null) {
         var0.method12188();
      }

      var0.field6615 = null;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("cy")
   public void method11657(classUU var1) {
      float var2 = this.field6390 * var1.field6391 + (var1.field6388 * this.field6394 + (this.field6383 * var1.field6383 + this.field6398 * var1.field6392));
      float var3 = this.field6394 * var1.field6389 + (this.field6398 * var1.field6384 + this.field6383 * var1.field6398) + var1.field6399 * this.field6390;
      float var4 = this.field6390 * var1.field6395 + (this.field6398 * var1.field6396 + var1.field6394 * this.field6383 + var1.field6385 * this.field6394);
      float var5 = this.field6394 * var1.field6397 + (var1.field6390 * this.field6383 + this.field6398 * var1.field6393) + this.field6390 * var1.field6386;
      float var6 = this.field6384 * var1.field6392 + this.field6392 * var1.field6383 + var1.field6388 * this.field6396 + var1.field6391 * this.field6393;
      float var7 = this.field6396 * var1.field6389 + (this.field6392 * var1.field6398 + this.field6384 * var1.field6384) + var1.field6399 * this.field6393;
      float var8 = var1.field6396 * this.field6384 + var1.field6394 * this.field6392 + this.field6396 * var1.field6385 + var1.field6395 * this.field6393;
      float var9 = this.field6384 * var1.field6393 + this.field6392 * var1.field6390 + this.field6396 * var1.field6397 + var1.field6386 * this.field6393;
      float var10 = var1.field6391 * this.field6397 + (var1.field6388 * this.field6385 + (var1.field6383 * this.field6388 + var1.field6392 * this.field6389));
      float var11 = this.field6397 * var1.field6399 + (this.field6385 * var1.field6389 + (this.field6388 * var1.field6398 + this.field6389 * var1.field6384));
      float var12 = var1.field6385 * this.field6385 + (var1.field6396 * this.field6389 + var1.field6394 * this.field6388) + this.field6397 * var1.field6395;
      float var13 = this.field6397 * var1.field6386 + (this.field6388 * var1.field6390 + this.field6389 * var1.field6393 + this.field6385 * var1.field6397);
      float var14 = this.field6386 * var1.field6391 + (var1.field6383 * this.field6391 + this.field6399 * var1.field6392 + this.field6395 * var1.field6388);
      float var15 = this.field6386 * var1.field6399 + (this.field6395 * var1.field6389 + (this.field6391 * var1.field6398 + this.field6399 * var1.field6384));
      float var16 = this.field6395 * var1.field6385 + (var1.field6394 * this.field6391 + this.field6399 * var1.field6396) + var1.field6395 * this.field6386;
      float var17 = var1.field6390 * this.field6391 + this.field6399 * var1.field6393 + this.field6395 * var1.field6397 + this.field6386 * var1.field6386;
      this.field6383 = var2;
      this.field6398 = var3;
      this.field6394 = var4;
      this.field6390 = var5;
      this.field6392 = var6;
      this.field6384 = var7;
      this.field6396 = var8;
      this.field6393 = var9;
      this.field6388 = var10;
      this.field6389 = var11;
      this.field6385 = var12;
      this.field6397 = var13;
      this.field6391 = var14;
      this.field6399 = var15;
      this.field6395 = var16;
      this.field6386 = var17;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method11692() {
      StringBuilder var1 = new StringBuilder();
      this.method11629(-1060025548);
      method11625(this, (byte)21);

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 4; var3++) {
            if (var3 > 0) {
               var1.append("\t");
            }

            float var4 = this.field6401[4 * var2 + var3];
            if (Math.sqrt(var4 * var4) < 1.0E-4F) {
               var4 = 0.0F;
            }

            var1.append(var4);
         }

         var1.append("\n");
      }

      return var1.toString();
   }

   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("oc")
   public float method11716(float var1, float var2, float var3) {
      return this.method11689(var1, var2, var3, 1629962595);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method11700(Object var1) {
      if (!(var1 instanceof classUU)) {
         return false;
      } else {
         classUU var2 = (classUU)var1;

         for (int var3 = 0; var3 < 16; var3++) {
            if (this.field6401[var3] != var2.field6401[var3]) {
               return false;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)F")
   @ObfuscatedName("au")
   float method11669(byte var1) {
      try {
         return this.field6388 * (this.field6390 * this.field6384) * this.field6395
            + (
               this.field6388 * (this.field6394 * this.field6393) * this.field6399
                  + (
                     this.field6391 * (this.field6397 * (this.field6384 * this.field6394))
                        + (
                           this.field6398 * this.field6393 * this.field6385 * this.field6391
                              + (
                                 this.field6392 * this.field6398 * this.field6397 * this.field6395
                                    + (
                                       this.field6383 * this.field6384 * this.field6385 * this.field6386
                                          - this.field6395 * (this.field6383 * this.field6384 * this.field6397)
                                          - this.field6389 * (this.field6396 * this.field6383) * this.field6386
                                          + this.field6399 * (this.field6383 * this.field6396 * this.field6397)
                                          + this.field6395 * (this.field6383 * this.field6393 * this.field6389)
                                          - this.field6383 * this.field6393 * this.field6385 * this.field6399
                                          - this.field6386 * (this.field6385 * (this.field6392 * this.field6398))
                                    )
                                    + this.field6388 * (this.field6398 * this.field6396) * this.field6386
                                    - this.field6391 * (this.field6398 * this.field6396 * this.field6397)
                                    - this.field6388 * (this.field6398 * this.field6393) * this.field6395
                              )
                              + this.field6389 * (this.field6394 * this.field6392) * this.field6386
                              - this.field6397 * (this.field6394 * this.field6392) * this.field6399
                              - this.field6384 * this.field6394 * this.field6388 * this.field6386
                        )
                  )
                  - this.field6389 * (this.field6394 * this.field6393) * this.field6391
                  - this.field6395 * (this.field6392 * this.field6390 * this.field6389)
                  + this.field6392 * this.field6390 * this.field6385 * this.field6399
            )
            - this.field6385 * (this.field6390 * this.field6384) * this.field6391
            - this.field6388 * (this.field6396 * this.field6390) * this.field6399
            + this.field6396 * this.field6390 * this.field6389 * this.field6391;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uu.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFF)V")
   @ObfuscatedName("ea")
   public void method11717(float var1, float var2, float var3, float var4) {
      this.method11675(var1, var2, var3, var4, -1412584499);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)[F")
   @ObfuscatedName("iq")
   public static float[] method11701(classUU var0) {
      if (var0 == null) {
         var0.hashCode();
      }

      float[] var1 = new float[3];
      classUZ var2 = new classUZ(var0.field6383, var0.field6398, var0.field6394);
      classUZ var3 = new classUZ(var0.field6392, var0.field6384, var0.field6396);
      classUZ var4 = new classUZ(var0.field6388, var0.field6389, var0.field6385);
      var1[0] = var2.method11844(-1808401369);
      var1[1] = var3.method11844(-1808401369);
      var1[2] = var4.method11844(-1808401369);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)[F")
   @ObfuscatedName("as")
   public float[] method11702(int var1) {
      try {
         float[] var2 = new float[3];
         classUZ var3 = new classUZ(this.field6383, this.field6398, this.field6394);
         classUZ var4 = new classUZ(this.field6392, this.field6384, this.field6396);
         classUZ var5 = new classUZ(this.field6388, this.field6389, this.field6385);
         var2[0] = var3.method11844(-1808401369);
         var2[1] = var4.method11844(-1808401369);
         var2[2] = var5.method11844(-1808401369);
         return var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uu.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("sf")
   public void method11718(classUL var1) {
      this.method11661(var1, 777754821);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afs")
   public int method11695() {
      byte var1 = 31;
      byte var2 = 1;
      return 31 * var2 + Arrays.hashCode(this.field6401);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;FFF)Luz;")
   @ObfuscatedName("ej")
   public static classUZ method11679(classUU var0, float var1, float var2, float var3) {
      if (var0 == null) {
         var0.method11631();
      }

      float var4 = 1.0F / (var0.field6386 + (var2 * var0.field6393 + var1 * var0.field6390 + var0.field6397 * var3));
      return classIK.method6261(
         var4 * (var0.field6391 + (var0.field6383 * var1 + var2 * var0.field6392 + var0.field6388 * var3)),
         var4 * (var0.field6399 + (var0.field6398 * var1 + var2 * var0.field6384 + var3 * var0.field6389)),
         (var0.field6395 + (var3 * var0.field6385 + (var0.field6396 * var2 + var1 * var0.field6394))) * var4,
         (byte)-33
      );
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("yz")
   public static void method11616(classUU var0) {
      if (var0 == null) {
         var0.method11721();
      } else {
         synchronized (field6380) {
            if (field6381 * -2060926419 < -82390220 * field6379) {
               field6380[(field6381 += 1681980045) * -2072202381 - 1] = var0;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("ah")
   public static classUU method11608() {
      synchronized (field6380) {
         if (-1276566354 * field6381 == 0) {
            return new classUU();
         } else {
            field6380[(field6381 -= 943786731) * 1141092157].method11635(-808306951);
            return field6380[field6381 * -1143955944];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)[F")
   @ObfuscatedName("su")
   public static float[] method11621(classUU var0) {
      if (var0 == null) {
         var0.method11636();
      }

      float[] var1 = new float[3];
      if (var0.field6394 < 0.999 && var0.field6394 > -0.999) {
         var1[1] = (float)(-Math.asin(var0.field6394));
         double var2 = Math.cos(var1[1]);
         var1[0] = (float)Math.atan2(var0.field6396 / var2, var0.field6385 / var2);
         var1[2] = (float)Math.atan2(var0.field6398 / var2, var0.field6383 / var2);
      } else {
         var1[0] = 0.0F;
         var1[1] = (float)Math.atan2(var0.field6394, 0.0);
         var1[2] = (float)Math.atan2(-var0.field6389, var0.field6384);
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Luu;I)V")
   @ObfuscatedName("wc")
   public static void method11670(classUU var0, int var1) {
      if (var0 == null) {
         var0.method11674(var1);
      } else {
         try {
            float var2 = 1.0F / var0.method11669((byte)40);
            float var3 = (
                  var0.field6386 * (var0.field6385 * var0.field6384)
                     - var0.field6397 * var0.field6384 * var0.field6395
                     - var0.field6396 * var0.field6389 * var0.field6386
                     + var0.field6399 * (var0.field6396 * var0.field6397)
                     + var0.field6393 * var0.field6389 * var0.field6395
                     - var0.field6399 * (var0.field6385 * var0.field6393)
               )
               * var2;
            float var4 = (
                  var0.field6390 * var0.field6385 * var0.field6399
                     + (
                        var0.field6395 * (var0.field6398 * var0.field6397)
                           + -var0.field6398 * var0.field6385 * var0.field6386
                           + var0.field6386 * (var0.field6394 * var0.field6389)
                           - var0.field6399 * (var0.field6397 * var0.field6394)
                           - var0.field6395 * (var0.field6390 * var0.field6389)
                     )
               )
               * var2;
            float var5 = var2
               * (
                  var0.field6394 * var0.field6393 * var0.field6399
                     + (
                        var0.field6398 * var0.field6396 * var0.field6386
                           - var0.field6398 * var0.field6393 * var0.field6395
                           - var0.field6386 * (var0.field6384 * var0.field6394)
                     )
                     + var0.field6384 * var0.field6390 * var0.field6395
                     - var0.field6390 * var0.field6396 * var0.field6399
               );
            float var6 = var2
               * (
                  var0.field6396 * -var0.field6398 * var0.field6397
                     + var0.field6385 * (var0.field6398 * var0.field6393)
                     + var0.field6397 * (var0.field6384 * var0.field6394)
                     - var0.field6394 * var0.field6393 * var0.field6389
                     - var0.field6385 * (var0.field6390 * var0.field6384)
                     + var0.field6396 * var0.field6390 * var0.field6389
               );
            float var7 = (
                  var0.field6396 * var0.field6388 * var0.field6386
                     + (var0.field6385 * -var0.field6392 * var0.field6386 + var0.field6392 * var0.field6397 * var0.field6395)
                     - var0.field6391 * (var0.field6397 * var0.field6396)
                     - var0.field6388 * var0.field6393 * var0.field6395
                     + var0.field6393 * var0.field6385 * var0.field6391
               )
               * var2;
            float var8 = (
                  var0.field6391 * (var0.field6397 * var0.field6394)
                     + (
                        var0.field6386 * (var0.field6383 * var0.field6385)
                           - var0.field6395 * (var0.field6397 * var0.field6383)
                           - var0.field6394 * var0.field6388 * var0.field6386
                     )
                     + var0.field6388 * var0.field6390 * var0.field6395
                     - var0.field6391 * (var0.field6385 * var0.field6390)
               )
               * var2;
            float var9 = (
                  var0.field6395 * (var0.field6383 * var0.field6393)
                     + -var0.field6383 * var0.field6396 * var0.field6386
                     + var0.field6394 * var0.field6392 * var0.field6386
                     - var0.field6394 * var0.field6393 * var0.field6391
                     - var0.field6390 * var0.field6392 * var0.field6395
                     + var0.field6391 * (var0.field6390 * var0.field6396)
               )
               * var2;
            float var10 = var2
               * (
                  var0.field6385 * (var0.field6390 * var0.field6392)
                     + (
                        var0.field6397 * (var0.field6383 * var0.field6396)
                           - var0.field6393 * var0.field6383 * var0.field6385
                           - var0.field6394 * var0.field6392 * var0.field6397
                           + var0.field6394 * var0.field6393 * var0.field6388
                     )
                     - var0.field6390 * var0.field6396 * var0.field6388
               );
            float var11 = (
                  var0.field6392 * var0.field6389 * var0.field6386
                     - var0.field6397 * var0.field6392 * var0.field6399
                     - var0.field6386 * (var0.field6384 * var0.field6388)
                     + var0.field6384 * var0.field6397 * var0.field6391
                     + var0.field6399 * (var0.field6393 * var0.field6388)
                     - var0.field6393 * var0.field6389 * var0.field6391
               )
               * var2;
            float var12 = (
                  var0.field6391 * (var0.field6390 * var0.field6389)
                     + (
                        var0.field6399 * (var0.field6397 * var0.field6383)
                           + var0.field6386 * (var0.field6389 * -var0.field6383)
                           + var0.field6398 * var0.field6388 * var0.field6386
                           - var0.field6391 * (var0.field6398 * var0.field6397)
                           - var0.field6388 * var0.field6390 * var0.field6399
                     )
               )
               * var2;
            float var13 = (
                  var0.field6383 * var0.field6384 * var0.field6386
                     - var0.field6393 * var0.field6383 * var0.field6399
                     - var0.field6392 * var0.field6398 * var0.field6386
                     + var0.field6391 * (var0.field6393 * var0.field6398)
                     + var0.field6399 * (var0.field6390 * var0.field6392)
                     - var0.field6391 * (var0.field6384 * var0.field6390)
               )
               * var2;
            float var14 = var2
               * (
                  var0.field6397 * (var0.field6398 * var0.field6392)
                     + (var0.field6397 * (-var0.field6383 * var0.field6384) + var0.field6393 * var0.field6383 * var0.field6389)
                     - var0.field6388 * (var0.field6398 * var0.field6393)
                     - var0.field6392 * var0.field6390 * var0.field6389
                     + var0.field6388 * (var0.field6390 * var0.field6384)
               );
            float var15 = var2
               * (
                  var0.field6396 * var0.field6389 * var0.field6391
                     + (
                        var0.field6395 * (var0.field6384 * var0.field6388)
                           + (var0.field6392 * var0.field6385 * var0.field6399 + var0.field6389 * -var0.field6392 * var0.field6395)
                           - var0.field6384 * var0.field6385 * var0.field6391
                           - var0.field6399 * (var0.field6396 * var0.field6388)
                     )
               );
            float var16 = (
                  var0.field6389 * var0.field6383 * var0.field6395
                     - var0.field6385 * var0.field6383 * var0.field6399
                     - var0.field6395 * (var0.field6388 * var0.field6398)
                     + var0.field6391 * (var0.field6398 * var0.field6385)
                     + var0.field6394 * var0.field6388 * var0.field6399
                     - var0.field6394 * var0.field6389 * var0.field6391
               )
               * var2;
            float var17 = var2
               * (
                  var0.field6396 * var0.field6383 * var0.field6399
                     + var0.field6384 * -var0.field6383 * var0.field6395
                     + var0.field6398 * var0.field6392 * var0.field6395
                     - var0.field6391 * (var0.field6398 * var0.field6396)
                     - var0.field6394 * var0.field6392 * var0.field6399
                     + var0.field6391 * (var0.field6384 * var0.field6394)
               );
            float var18 = var2
               * (
                  var0.field6383 * var0.field6384 * var0.field6385
                     - var0.field6389 * (var0.field6396 * var0.field6383)
                     - var0.field6385 * (var0.field6398 * var0.field6392)
                     + var0.field6396 * var0.field6398 * var0.field6388
                     + var0.field6392 * var0.field6394 * var0.field6389
                     - var0.field6388 * (var0.field6394 * var0.field6384)
               );
            var0.field6383 = var3;
            var0.field6398 = var4;
            var0.field6394 = var5;
            var0.field6390 = var6;
            var0.field6392 = var7;
            var0.field6384 = var8;
            var0.field6396 = var9;
            var0.field6393 = var10;
            var0.field6388 = var11;
            var0.field6389 = var12;
            var0.field6385 = var13;
            var0.field6397 = var14;
            var0.field6391 = var15;
            var0.field6399 = var16;
            var0.field6395 = var17;
            var0.field6386 = var18;
         } catch (RuntimeException var19) {
            throw classEG.method3899(var19, "uu.ai(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Luu;")
   @ObfuscatedName("bn")
   public static classUU method11609() {
      synchronized (field6380) {
         if (-1618719293 * field6381 == 0) {
            return new classUU();
         } else {
            field6380[(field6381 -= -1880037778) * -1618719293].method11635(-613313620);
            return field6380[field6381 * 1966952103];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)Luu;")
   @ObfuscatedName("bc")
   public static classUU method11612(classUU var0) {
      synchronized (field6380) {
         if (0 == field6381 * -1618719293) {
            return new classUU(var0);
         } else {
            classUZ.method11857(field6380[(field6381 -= 943786731) * -1618719293], var0, (byte)63);
            return field6380[field6381 * -1618719293];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;Luu;B)V")
   @ObfuscatedName("wj")
   public static void method11707(classUU var0, classUU var1, byte var2) {
      if (var0 == null) {
         var0.method11708(var0, var2);
      }

      var0.field6383 = var0.field6383 + var1.field6383;
      var0.field6398 = var0.field6398 + var1.field6398;
      var0.field6394 = var0.field6394 + var1.field6394;
      var0.field6390 = var0.field6390 + var1.field6390;
      var0.field6392 = var0.field6392 + var1.field6392;
      var0.field6384 = var0.field6384 + var1.field6384;
      var0.field6396 = var0.field6396 + var1.field6396;
      var0.field6393 = var0.field6393 + var1.field6393;
      var0.field6388 = var0.field6388 + var1.field6388;
      var0.field6389 = var0.field6389 + var1.field6389;
      var0.field6385 = var0.field6385 + var1.field6385;
      var0.field6397 = var0.field6397 + var1.field6397;
      var0.field6391 = var0.field6391 + var1.field6391;
      var0.field6399 = var0.field6399 + var1.field6399;
      var0.field6395 = var0.field6395 + var1.field6395;
      var0.field6386 = var0.field6386 + var1.field6386;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   public void method11719() {
      this.method11640(1782054023);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Luu;)V")
   @ObfuscatedName("ni")
   public static void method11643(classUU var0, classUU var1) {
      if (var0 == null) {
         var0.method11646(var0);
      }

      System.arraycopy(var1.field6401, 0, var0.field6401, 0, 16);
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("bu")
   float[] method11622() {
      float[] var1 = new float[3];
      if (this.field6394 < 0.999 && this.field6394 > -0.999) {
         var1[1] = (float)(-Math.asin(this.field6394));
         double var2 = Math.cos(var1[1]);
         var1[0] = (float)Math.atan2(this.field6396 / var2, this.field6385 / var2);
         var1[2] = (float)Math.atan2(this.field6398 / var2, this.field6383 / var2);
      } else {
         var1[0] = 0.0F;
         var1[1] = (float)Math.atan2(this.field6394, 0.0);
         var1[2] = (float)Math.atan2(-this.field6389, this.field6384);
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Luu;FFFI)Luz;")
   @ObfuscatedName("wm")
   public static classUZ method11680(classUU var0, float var1, float var2, float var3, int var4) {
      if (var0 == null) {
         var0.method11683(var1, var1, var1, var4);
      }

      try {
         float var5 = 1.0F / (var0.field6386 + (var2 * var0.field6393 + var1 * var0.field6390 + var0.field6397 * var3));
         return classIK.method6261(
            var5 * (var0.field6391 + (var0.field6383 * var1 + var2 * var0.field6392 + var0.field6388 * var3)),
            var5 * (var0.field6399 + (var0.field6398 * var1 + var2 * var0.field6384 + var3 * var0.field6389)),
            (var0.field6395 + (var3 * var0.field6385 + (var0.field6396 * var2 + var1 * var0.field6394))) * var5,
            (byte)13
         );
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uu.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("bw")
   float[] method11623() {
      float[] var1 = new float[3];
      if (this.field6394 < 0.999 && this.field6394 > -0.999) {
         var1[1] = (float)(-Math.asin(this.field6394));
         double var2 = Math.cos(var1[1]);
         var1[0] = (float)Math.atan2(this.field6396 / var2, this.field6385 / var2);
         var1[2] = (float)Math.atan2(this.field6398 / var2, this.field6383 / var2);
      } else {
         var1[0] = 0.0F;
         var1[1] = (float)Math.atan2(this.field6394, 0.0);
         var1[2] = (float)Math.atan2(-this.field6389, this.field6384);
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   public void method11617() {
      synchronized (field6380) {
         if (field6381 * -1618719293 < -711449127 * field6379) {
            field6380[(field6381 += 943786731) * -1618719293 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("ba")
   public void method11720(classUU var1) {
      classUZ.method11857(this, var1, (byte)-47);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dm")
   public void method11721() {
      method11670(this, -1126922930);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   public void method11618() {
      synchronized (field6380) {
         if (field6381 * 1928824724 < -711449127 * field6379) {
            field6380[(field6381 += 943786731) * 251910781 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;Z)V")
   @ObfuscatedName("bo")
   void method11620(classXY var1, boolean var2) {
      if (var2) {
         classUX var3 = new classUX();
         int var6 = var1.method13104((byte)16);
         var6 &= 16383;
         float var5 = (float)(var6 * 3.834951969714103E-4);
         var3.method11748(var5, 173632492);
         int var9 = var1.method13104((byte)16);
         var9 &= 16383;
         float var8 = (float)(var9 * 3.834951969714103E-4);
         var3.method11753(var8, 982132619);
         int var12 = var1.method13104((byte)16);
         var12 &= 16383;
         float var11 = (float)(var12 * 3.834951969714103E-4);
         classUX.method11754(var3, var11, 1324180918);
         classUX.method11760(var3, var1.method13104((byte)16), var1.method13104((byte)16), var1.method13104((byte)16), (byte)98);
         method11668(this, var3, -1959054899);
      } else {
         for (int var13 = 0; var13 < 16; var13++) {
            this.field6401[var13] = var1.method13119(536070982);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method11696() {
      byte var1 = 31;
      byte var2 = 1;
      return 31 * var2 + Arrays.hashCode(this.field6401);
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("ba")
   float[] method11624() {
      float[] var1 = new float[3];
      if (this.field6394 < 0.999 && this.field6394 > -0.999) {
         var1[1] = (float)(-Math.asin(this.field6394));
         double var2 = Math.cos(var1[1]);
         var1[0] = (float)Math.atan2(this.field6396 / var2, this.field6385 / var2);
         var1[2] = (float)Math.atan2(this.field6398 / var2, this.field6383 / var2);
      } else {
         var1[0] = 0.0F;
         var1[1] = (float)Math.atan2(this.field6394, 0.0);
         var1[2] = (float)Math.atan2(-this.field6389, this.field6384);
      }

      return var1;
   }

   public classUU() {
      this.field6383 = 1.0F;
      this.field6384 = 1.0F;
      this.field6385 = 1.0F;
      this.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("bi")
   public float[] method11630() {
      float[] var1 = new float[]{(float)(-Math.asin(this.field6396)), 0.0F, 0.0F};
      double var2 = Math.cos(var1[0]);
      if (Math.abs(var2) > 0.005) {
         double var4 = this.field6394;
         double var6 = this.field6385;
         double var8 = this.field6392;
         double var10 = this.field6384;
         var1[1] = (float)Math.atan2(var4, var6);
         var1[2] = (float)Math.atan2(var8, var10);
      } else {
         double var12 = this.field6398;
         double var13 = this.field6383;
         if (this.field6396 < 0.0F) {
            var1[1] = (float)Math.atan2(var12, var13);
         } else {
            var1[1] = (float)(-Math.atan2(var12, var13));
         }

         var1[2] = 0.0F;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("be")
   public float[] method11631() {
      float[] var1 = new float[]{(float)(-Math.asin(this.field6396)), 0.0F, 0.0F};
      double var2 = Math.cos(var1[0]);
      if (Math.abs(var2) > 0.005) {
         double var4 = this.field6394;
         double var6 = this.field6385;
         double var8 = this.field6392;
         double var10 = this.field6384;
         var1[1] = (float)Math.atan2(var4, var6);
         var1[2] = (float)Math.atan2(var8, var10);
      } else {
         double var12 = this.field6398;
         double var13 = this.field6383;
         if (this.field6396 < 0.0F) {
            var1[1] = (float)Math.atan2(var12, var13);
         } else {
            var1[1] = (float)(-Math.atan2(var12, var13));
         }

         var1[2] = 0.0F;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public void method11637() {
      this.field6383 = 1.0F;
      this.field6398 = 0.0F;
      this.field6394 = 0.0F;
      this.field6390 = 0.0F;
      this.field6392 = 0.0F;
      this.field6384 = 1.0F;
      this.field6396 = 0.0F;
      this.field6393 = 0.0F;
      this.field6388 = 0.0F;
      this.field6389 = 0.0F;
      this.field6385 = 1.0F;
      this.field6397 = 0.0F;
      this.field6391 = 0.0F;
      this.field6399 = 0.0F;
      this.field6395 = 0.0F;
      this.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   public void method11638() {
      this.field6383 = 1.0F;
      this.field6398 = 0.0F;
      this.field6394 = 0.0F;
      this.field6390 = 0.0F;
      this.field6392 = 0.0F;
      this.field6384 = 1.0F;
      this.field6396 = 0.0F;
      this.field6393 = 0.0F;
      this.field6388 = 0.0F;
      this.field6389 = 0.0F;
      this.field6385 = 1.0F;
      this.field6397 = 0.0F;
      this.field6391 = 0.0F;
      this.field6399 = 0.0F;
      this.field6395 = 0.0F;
      this.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bv")
   public void method11641() {
      this.field6383 = 0.0F;
      this.field6398 = 0.0F;
      this.field6394 = 0.0F;
      this.field6390 = 0.0F;
      this.field6392 = 0.0F;
      this.field6384 = 0.0F;
      this.field6396 = 0.0F;
      this.field6393 = 0.0F;
      this.field6388 = 0.0F;
      this.field6389 = 0.0F;
      this.field6385 = 0.0F;
      this.field6397 = 0.0F;
      this.field6391 = 0.0F;
      this.field6399 = 0.0F;
      this.field6395 = 0.0F;
      this.field6386 = 0.0F;
   }

   @ObfuscatedSignature(descriptor = "(FFF)Luz;")
   @ObfuscatedName("cx")
   public classUZ method11681(float var1, float var2, float var3) {
      float var4 = 1.0F / (this.field6386 + (var2 * this.field6393 + var1 * this.field6390 + this.field6397 * var3));
      return classIK.method6261(
         var4 * (this.field6391 + (this.field6383 * var1 + var2 * this.field6392 + this.field6388 * var3)),
         var4 * (this.field6399 + (this.field6398 * var1 + var2 * this.field6384 + var3 * this.field6389)),
         (this.field6395 + (var3 * this.field6385 + (this.field6396 * var2 + var1 * this.field6394))) * var4,
         (byte)67
      );
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("bz")
   public void method11644(classUU var1) {
      System.arraycopy(var1.field6401, 0, this.field6401, 0, 16);
   }

   @ObfuscatedSignature(descriptor = "(FFF[F)V")
   @ObfuscatedName("ac")
   public void method11722(float var1, float var2, float var3, float[] var4) {
      this.method11677(var1, var2, var3, var4, 1634822735);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)Ljava/lang/String;")
   @ObfuscatedName("wa")
   public static String method11693(classUU var0) {
      if (var0 == null) {
         var0.method11704();
      }

      StringBuilder var1 = new StringBuilder();
      var0.method11629(517080097);
      method11625(var0, (byte)34);

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 4; var3++) {
            if (var3 > 0) {
               var1.append("\t");
            }

            float var4 = var0.field6401[4 * var2 + var3];
            if (Math.sqrt(var4 * var4) < 1.0E-4F) {
               var4 = 0.0F;
            }

            var1.append(var4);
         }

         var1.append("\n");
      }

      return var1.toString();
   }

   @ObfuscatedSignature(descriptor = "(Luu;B)[F")
   @ObfuscatedName("xi")
   public static float[] method11625(classUU var0, byte var1) {
      if (var0 == null) {
         return var0.method11626(var1);
      } else {
         try {
            float[] var2 = new float[3];
            if (var0.field6394 < 0.999) {
               if (var1 == 3) {
                  throw new IllegalStateException();
               }

               if (var0.field6394 > -0.999) {
                  if (var1 == 3) {
                     throw new IllegalStateException();
                  }

                  var2[1] = (float)(-Math.asin(var0.field6394));
                  double var3 = Math.cos(var2[1]);
                  var2[0] = (float)Math.atan2(var0.field6396 / var3, var0.field6385 / var3);
                  var2[2] = (float)Math.atan2(var0.field6398 / var3, var0.field6383 / var3);
                  return var2;
               }
            }

            var2[0] = 0.0F;
            var2[1] = (float)Math.atan2(var0.field6394, 0.0);
            var2[2] = (float)Math.atan2(-var0.field6389, var0.field6384);
            return var2;
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "uu.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("ko")
   public void method11723(float var1, float var2, float var3) {
      this.method11651(var1, var2, var3, -2138377657);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Lug;)V")
   @ObfuscatedName("ux")
   public static void method11660(classUU var0, classUG var1) {
      if (var0 == null) {
         var0.method11704();
      }

      float var2 = var1.field6303.field6325 * var1.field6303.field6325;
      float var3 = var1.field6303.field6325 * var1.field6303.field6322;
      float var4 = var1.field6303.field6323 * var1.field6303.field6325;
      float var5 = var1.field6303.field6325 * var1.field6303.field6324;
      float var6 = var1.field6303.field6322 * var1.field6303.field6322;
      float var7 = var1.field6303.field6323 * var1.field6303.field6322;
      float var8 = var1.field6303.field6324 * var1.field6303.field6322;
      float var9 = var1.field6303.field6323 * var1.field6303.field6323;
      float var10 = var1.field6303.field6324 * var1.field6303.field6323;
      float var11 = var1.field6303.field6324 * var1.field6303.field6324;
      var0.field6383 = var2 + var6 - var11 - var9;
      var0.field6398 = var5 + (var7 + (var5 + var7));
      var0.field6394 = var8 - var4 - var4 + var8;
      var0.field6392 = var7 - var5 - var5 + var7;
      var0.field6384 = var2 + var9 - var6 - var11;
      var0.field6396 = var3 + (var10 + (var10 + var3));
      var0.field6388 = var4 + (var8 + (var8 + var4));
      var0.field6389 = var10 - var3 - var3 + var10;
      var0.field6385 = var2 + var11 - var9 - var6;
      float[] var12 = var0.field6401;
      float[] var13 = var0.field6401;
      var0.field6397 = 0.0F;
      var13[7] = 0.0F;
      var12[3] = 0.0F;
      var0.field6391 = var1.field6305.field6425;
      var0.field6399 = var1.field6305.field6423;
      var0.field6395 = var1.field6305.field6424;
      var0.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("cr")
   public void method11645(classUU var1) {
      System.arraycopy(var1.field6401, 0, this.field6401, 0, 16);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)Luu;")
   @ObfuscatedName("br")
   public static classUU method11613(classUU var0) {
      synchronized (field6380) {
         if (0 == field6381 * -1618719293) {
            return new classUU(var0);
         } else {
            classUZ.method11857(field6380[(field6381 -= 943786731) * -1618719293], var0, (byte)-14);
            return field6380[field6381 * -1618719293];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method11697() {
      byte var1 = 31;
      byte var2 = 1;
      return -725005571 * var2 + Arrays.hashCode(this.field6401);
   }

   @ObfuscatedSignature(descriptor = "([F)V")
   @ObfuscatedName("fz")
   public void method11713(float[] var1) {
      var1[0] = this.field6383;
      var1[1] = this.field6398;
      var1[2] = this.field6394;
      var1[3] = this.field6390;
      var1[4] = this.field6392;
      var1[5] = this.field6384;
      var1[6] = this.field6396;
      var1[7] = this.field6393;
      var1[8] = this.field6388;
      var1[9] = this.field6389;
      var1[10] = this.field6385;
      var1[11] = this.field6397;
      var1[12] = this.field6391;
      var1[13] = this.field6399;
      var1[14] = this.field6395;
      var1[15] = this.field6386;
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("ct")
   public void method11647(float var1) {
      this.method11651(var1, var1, var1, -2136018681);
   }

   @ObfuscatedSignature(descriptor = "(FFF)V")
   @ObfuscatedName("cp")
   public void method11653(float var1, float var2, float var3) {
      this.method11635(-143128600);
      this.field6383 = var1;
      this.field6384 = var2;
      this.field6385 = var3;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("cl")
   public void method11655(classUU var1) {
      for (int var2 = 0; var2 < this.field6401.length; var2++) {
         this.field6401[var2] = this.field6401[var2] + var1.field6401[var2];
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("nj")
   public void method11724() {
      this.method11635(-166618756);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;)I")
   @ObfuscatedName("rx")
   public static int method11627(classUQ var0) {
      return var0.field6366 * -1357616065 + 518523273 * var0.field6368;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("cv")
   public void method11658(classUU var1) {
      float var2 = this.field6390 * var1.field6391 + (var1.field6388 * this.field6394 + (this.field6383 * var1.field6383 + this.field6398 * var1.field6392));
      float var3 = this.field6394 * var1.field6389 + (this.field6398 * var1.field6384 + this.field6383 * var1.field6398) + var1.field6399 * this.field6390;
      float var4 = this.field6390 * var1.field6395 + (this.field6398 * var1.field6396 + var1.field6394 * this.field6383 + var1.field6385 * this.field6394);
      float var5 = this.field6394 * var1.field6397 + (var1.field6390 * this.field6383 + this.field6398 * var1.field6393) + this.field6390 * var1.field6386;
      float var6 = this.field6384 * var1.field6392 + this.field6392 * var1.field6383 + var1.field6388 * this.field6396 + var1.field6391 * this.field6393;
      float var7 = this.field6396 * var1.field6389 + (this.field6392 * var1.field6398 + this.field6384 * var1.field6384) + var1.field6399 * this.field6393;
      float var8 = var1.field6396 * this.field6384 + var1.field6394 * this.field6392 + this.field6396 * var1.field6385 + var1.field6395 * this.field6393;
      float var9 = this.field6384 * var1.field6393 + this.field6392 * var1.field6390 + this.field6396 * var1.field6397 + var1.field6386 * this.field6393;
      float var10 = var1.field6391 * this.field6397 + (var1.field6388 * this.field6385 + (var1.field6383 * this.field6388 + var1.field6392 * this.field6389));
      float var11 = this.field6397 * var1.field6399 + (this.field6385 * var1.field6389 + (this.field6388 * var1.field6398 + this.field6389 * var1.field6384));
      float var12 = var1.field6385 * this.field6385 + (var1.field6396 * this.field6389 + var1.field6394 * this.field6388) + this.field6397 * var1.field6395;
      float var13 = this.field6397 * var1.field6386 + (this.field6388 * var1.field6390 + this.field6389 * var1.field6393 + this.field6385 * var1.field6397);
      float var14 = this.field6386 * var1.field6391 + (var1.field6383 * this.field6391 + this.field6399 * var1.field6392 + this.field6395 * var1.field6388);
      float var15 = this.field6386 * var1.field6399 + (this.field6395 * var1.field6389 + (this.field6391 * var1.field6398 + this.field6399 * var1.field6384));
      float var16 = this.field6395 * var1.field6385 + (var1.field6394 * this.field6391 + this.field6399 * var1.field6396) + var1.field6395 * this.field6386;
      float var17 = var1.field6390 * this.field6391 + this.field6399 * var1.field6393 + this.field6395 * var1.field6397 + this.field6386 * var1.field6386;
      this.field6383 = var2;
      this.field6398 = var3;
      this.field6394 = var4;
      this.field6390 = var5;
      this.field6392 = var6;
      this.field6384 = var7;
      this.field6396 = var8;
      this.field6393 = var9;
      this.field6388 = var10;
      this.field6389 = var11;
      this.field6385 = var12;
      this.field6397 = var13;
      this.field6391 = var14;
      this.field6399 = var15;
      this.field6395 = var16;
      this.field6386 = var17;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("cs")
   public void method11659(classUU var1) {
      float var2 = this.field6390 * var1.field6391 + (var1.field6388 * this.field6394 + (this.field6383 * var1.field6383 + this.field6398 * var1.field6392));
      float var3 = this.field6394 * var1.field6389 + (this.field6398 * var1.field6384 + this.field6383 * var1.field6398) + var1.field6399 * this.field6390;
      float var4 = this.field6390 * var1.field6395 + (this.field6398 * var1.field6396 + var1.field6394 * this.field6383 + var1.field6385 * this.field6394);
      float var5 = this.field6394 * var1.field6397 + (var1.field6390 * this.field6383 + this.field6398 * var1.field6393) + this.field6390 * var1.field6386;
      float var6 = this.field6384 * var1.field6392 + this.field6392 * var1.field6383 + var1.field6388 * this.field6396 + var1.field6391 * this.field6393;
      float var7 = this.field6396 * var1.field6389 + (this.field6392 * var1.field6398 + this.field6384 * var1.field6384) + var1.field6399 * this.field6393;
      float var8 = var1.field6396 * this.field6384 + var1.field6394 * this.field6392 + this.field6396 * var1.field6385 + var1.field6395 * this.field6393;
      float var9 = this.field6384 * var1.field6393 + this.field6392 * var1.field6390 + this.field6396 * var1.field6397 + var1.field6386 * this.field6393;
      float var10 = var1.field6391 * this.field6397 + (var1.field6388 * this.field6385 + (var1.field6383 * this.field6388 + var1.field6392 * this.field6389));
      float var11 = this.field6397 * var1.field6399 + (this.field6385 * var1.field6389 + (this.field6388 * var1.field6398 + this.field6389 * var1.field6384));
      float var12 = var1.field6385 * this.field6385 + (var1.field6396 * this.field6389 + var1.field6394 * this.field6388) + this.field6397 * var1.field6395;
      float var13 = this.field6397 * var1.field6386 + (this.field6388 * var1.field6390 + this.field6389 * var1.field6393 + this.field6385 * var1.field6397);
      float var14 = this.field6386 * var1.field6391 + (var1.field6383 * this.field6391 + this.field6399 * var1.field6392 + this.field6395 * var1.field6388);
      float var15 = this.field6386 * var1.field6399 + (this.field6395 * var1.field6389 + (this.field6391 * var1.field6398 + this.field6399 * var1.field6384));
      float var16 = this.field6395 * var1.field6385 + (var1.field6394 * this.field6391 + this.field6399 * var1.field6396) + var1.field6395 * this.field6386;
      float var17 = var1.field6390 * this.field6391 + this.field6399 * var1.field6393 + this.field6395 * var1.field6397 + this.field6386 * var1.field6386;
      this.field6383 = var2;
      this.field6398 = var3;
      this.field6394 = var4;
      this.field6390 = var5;
      this.field6392 = var6;
      this.field6384 = var7;
      this.field6396 = var8;
      this.field6393 = var9;
      this.field6388 = var10;
      this.field6389 = var11;
      this.field6385 = var12;
      this.field6397 = var13;
      this.field6391 = var14;
      this.field6399 = var15;
      this.field6395 = var16;
      this.field6386 = var17;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("kf")
   public void method11725(classUU var1) {
      method11707(this, var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(Lug;)V")
   @ObfuscatedName("ne")
   public void method11726(classUG var1) {
      this.method11706(var1, 1861666336);
   }

   @ObfuscatedSignature(descriptor = "(Lux;)V")
   @ObfuscatedName("bd")
   public void method11727(classUX var1) {
      method11668(this, var1, -1959054899);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         classUU var2 = (classUU)var1;
         return Float.compare(var2.field6383, this.field6383) == 0
            && Float.compare(var2.field6398, this.field6398) == 0
            && Float.compare(var2.field6394, this.field6394) == 0
            && Float.compare(var2.field6390, this.field6390) == 0
            && Float.compare(var2.field6392, this.field6392) == 0
            && Float.compare(var2.field6384, this.field6384) == 0
            && Float.compare(var2.field6396, this.field6396) == 0
            && Float.compare(var2.field6393, this.field6393) == 0
            && Float.compare(var2.field6388, this.field6388) == 0
            && Float.compare(var2.field6389, this.field6389) == 0
            && Float.compare(var2.field6385, this.field6385) == 0
            && Float.compare(var2.field6397, this.field6397) == 0
            && Float.compare(var2.field6391, this.field6391) == 0
            && Float.compare(var2.field6399, this.field6399) == 0
            && Float.compare(var2.field6395, this.field6395) == 0
            && Float.compare(var2.field6386, this.field6386) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.field6383,
         this.field6398,
         this.field6394,
         this.field6390,
         this.field6392,
         this.field6384,
         this.field6396,
         this.field6393,
         this.field6388,
         this.field6389,
         this.field6385,
         this.field6397,
         this.field6391,
         this.field6399,
         this.field6395,
         this.field6386
      );
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("ck")
   public void method11662(classUL var1) {
      float var2 = var1.field6325 * var1.field6325;
      float var3 = var1.field6322 * var1.field6325;
      float var4 = var1.field6323 * var1.field6325;
      float var5 = var1.field6324 * var1.field6325;
      float var6 = var1.field6322 * var1.field6322;
      float var7 = var1.field6323 * var1.field6322;
      float var8 = var1.field6322 * var1.field6324;
      float var9 = var1.field6323 * var1.field6323;
      float var10 = var1.field6323 * var1.field6324;
      float var11 = var1.field6324 * var1.field6324;
      this.field6383 = var2 + var6 - var11 - var9;
      this.field6398 = var7 + var5 + var7 + var5;
      this.field6394 = var8 - var4 - var4 + var8;
      this.field6392 = var7 + (var7 - var5 - var5);
      this.field6384 = var2 + var9 - var6 - var11;
      this.field6396 = var3 + (var10 + var3 + var10);
      this.field6388 = var4 + (var8 + var4 + var8);
      this.field6389 = var10 - var3 - var3 + var10;
      this.field6385 = var2 + var11 - var9 - var6;
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("ca")
   public void method11663(classUL var1) {
      float var2 = var1.field6325 * var1.field6325;
      float var3 = var1.field6322 * var1.field6325;
      float var4 = var1.field6323 * var1.field6325;
      float var5 = var1.field6324 * var1.field6325;
      float var6 = var1.field6322 * var1.field6322;
      float var7 = var1.field6323 * var1.field6322;
      float var8 = var1.field6322 * var1.field6324;
      float var9 = var1.field6323 * var1.field6323;
      float var10 = var1.field6323 * var1.field6324;
      float var11 = var1.field6324 * var1.field6324;
      this.field6383 = var2 + var6 - var11 - var9;
      this.field6398 = var7 + var5 + var7 + var5;
      this.field6394 = var8 - var4 - var4 + var8;
      this.field6392 = var7 + (var7 - var5 - var5);
      this.field6384 = var2 + var9 - var6 - var11;
      this.field6396 = var3 + (var10 + var3 + var10);
      this.field6388 = var4 + (var8 + var4 + var8);
      this.field6389 = var10 - var3 - var3 + var10;
      this.field6385 = var2 + var11 - var9 - var6;
   }

   @ObfuscatedSignature(descriptor = "(Lux;)V")
   @ObfuscatedName("cn")
   public void method11665(classUX var1) {
      this.field6383 = var1.field6420;
      this.field6398 = var1.field6413;
      this.field6394 = var1.field6412;
      this.field6390 = 0.0F;
      this.field6392 = var1.field6410;
      this.field6384 = var1.field6414;
      this.field6396 = var1.field6418;
      this.field6393 = 0.0F;
      this.field6388 = var1.field6419;
      this.field6389 = var1.field6411;
      this.field6385 = var1.field6409;
      this.field6397 = 0.0F;
      this.field6391 = var1.field6417;
      this.field6399 = var1.field6416;
      this.field6395 = var1.field6415;
      this.field6386 = 1.0F;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ley;)Z")
   @ObfuscatedName("gk")
   public static boolean method11633(classEY var0) {
      return 1182504533 * var0.field1980 != classEY.field1977 * -430419313;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)[F")
   @ObfuscatedName("cg")
   public static float[] method11632(classUU var0) {
      if (var0 == null) {
         var0.method11636();
      }

      float[] var1 = new float[]{(float)(-Math.asin(var0.field6396)), 0.0F, 0.0F};
      double var2 = Math.cos(var1[0]);
      if (Math.abs(var2) > 0.005) {
         double var4 = var0.field6394;
         double var6 = var0.field6385;
         double var8 = var0.field6392;
         double var10 = var0.field6384;
         var1[1] = (float)Math.atan2(var4, var6);
         var1[2] = (float)Math.atan2(var8, var10);
      } else {
         double var12 = var0.field6398;
         double var13 = var0.field6383;
         if (var0.field6396 < 0.0F) {
            var1[1] = (float)Math.atan2(var12, var13);
         } else {
            var1[1] = (float)(-Math.atan2(var12, var13));
         }

         var1[2] = 0.0F;
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lux;)V")
   @ObfuscatedName("ch")
   public void method11666(classUX var1) {
      this.field6383 = var1.field6420;
      this.field6398 = var1.field6413;
      this.field6394 = var1.field6412;
      this.field6390 = 0.0F;
      this.field6392 = var1.field6410;
      this.field6384 = var1.field6414;
      this.field6396 = var1.field6418;
      this.field6393 = 0.0F;
      this.field6388 = var1.field6419;
      this.field6389 = var1.field6411;
      this.field6385 = var1.field6409;
      this.field6397 = 0.0F;
      this.field6391 = var1.field6417;
      this.field6399 = var1.field6416;
      this.field6395 = var1.field6415;
      this.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cz")
   public void method11671() {
      float var1 = 1.0F / this.method11669((byte)-28);
      float var2 = (
            this.field6386 * (this.field6385 * this.field6384)
               - this.field6397 * this.field6384 * this.field6395
               - this.field6396 * this.field6389 * this.field6386
               + this.field6399 * (this.field6396 * this.field6397)
               + this.field6393 * this.field6389 * this.field6395
               - this.field6399 * (this.field6385 * this.field6393)
         )
         * var1;
      float var3 = (
            this.field6390 * this.field6385 * this.field6399
               + (
                  this.field6395 * (this.field6398 * this.field6397)
                     + -this.field6398 * this.field6385 * this.field6386
                     + this.field6386 * (this.field6394 * this.field6389)
                     - this.field6399 * (this.field6397 * this.field6394)
                     - this.field6395 * (this.field6390 * this.field6389)
               )
         )
         * var1;
      float var4 = var1
         * (
            this.field6394 * this.field6393 * this.field6399
               + (
                  this.field6398 * this.field6396 * this.field6386
                     - this.field6398 * this.field6393 * this.field6395
                     - this.field6386 * (this.field6384 * this.field6394)
               )
               + this.field6384 * this.field6390 * this.field6395
               - this.field6390 * this.field6396 * this.field6399
         );
      float var5 = var1
         * (
            this.field6396 * -this.field6398 * this.field6397
               + this.field6385 * (this.field6398 * this.field6393)
               + this.field6397 * (this.field6384 * this.field6394)
               - this.field6394 * this.field6393 * this.field6389
               - this.field6385 * (this.field6390 * this.field6384)
               + this.field6396 * this.field6390 * this.field6389
         );
      float var6 = (
            this.field6396 * this.field6388 * this.field6386
               + (this.field6385 * -this.field6392 * this.field6386 + this.field6392 * this.field6397 * this.field6395)
               - this.field6391 * (this.field6397 * this.field6396)
               - this.field6388 * this.field6393 * this.field6395
               + this.field6393 * this.field6385 * this.field6391
         )
         * var1;
      float var7 = (
            this.field6391 * (this.field6397 * this.field6394)
               + (
                  this.field6386 * (this.field6383 * this.field6385)
                     - this.field6395 * (this.field6397 * this.field6383)
                     - this.field6394 * this.field6388 * this.field6386
               )
               + this.field6388 * this.field6390 * this.field6395
               - this.field6391 * (this.field6385 * this.field6390)
         )
         * var1;
      float var8 = (
            this.field6395 * (this.field6383 * this.field6393)
               + -this.field6383 * this.field6396 * this.field6386
               + this.field6394 * this.field6392 * this.field6386
               - this.field6394 * this.field6393 * this.field6391
               - this.field6390 * this.field6392 * this.field6395
               + this.field6391 * (this.field6390 * this.field6396)
         )
         * var1;
      float var9 = var1
         * (
            this.field6385 * (this.field6390 * this.field6392)
               + (
                  this.field6397 * (this.field6383 * this.field6396)
                     - this.field6393 * this.field6383 * this.field6385
                     - this.field6394 * this.field6392 * this.field6397
                     + this.field6394 * this.field6393 * this.field6388
               )
               - this.field6390 * this.field6396 * this.field6388
         );
      float var10 = (
            this.field6392 * this.field6389 * this.field6386
               - this.field6397 * this.field6392 * this.field6399
               - this.field6386 * (this.field6384 * this.field6388)
               + this.field6384 * this.field6397 * this.field6391
               + this.field6399 * (this.field6393 * this.field6388)
               - this.field6393 * this.field6389 * this.field6391
         )
         * var1;
      float var11 = (
            this.field6391 * (this.field6390 * this.field6389)
               + (
                  this.field6399 * (this.field6397 * this.field6383)
                     + this.field6386 * (this.field6389 * -this.field6383)
                     + this.field6398 * this.field6388 * this.field6386
                     - this.field6391 * (this.field6398 * this.field6397)
                     - this.field6388 * this.field6390 * this.field6399
               )
         )
         * var1;
      float var12 = (
            this.field6383 * this.field6384 * this.field6386
               - this.field6393 * this.field6383 * this.field6399
               - this.field6392 * this.field6398 * this.field6386
               + this.field6391 * (this.field6393 * this.field6398)
               + this.field6399 * (this.field6390 * this.field6392)
               - this.field6391 * (this.field6384 * this.field6390)
         )
         * var1;
      float var13 = var1
         * (
            this.field6397 * (this.field6398 * this.field6392)
               + (this.field6397 * (-this.field6383 * this.field6384) + this.field6393 * this.field6383 * this.field6389)
               - this.field6388 * (this.field6398 * this.field6393)
               - this.field6392 * this.field6390 * this.field6389
               + this.field6388 * (this.field6390 * this.field6384)
         );
      float var14 = var1
         * (
            this.field6396 * this.field6389 * this.field6391
               + (
                  this.field6395 * (this.field6384 * this.field6388)
                     + (this.field6392 * this.field6385 * this.field6399 + this.field6389 * -this.field6392 * this.field6395)
                     - this.field6384 * this.field6385 * this.field6391
                     - this.field6399 * (this.field6396 * this.field6388)
               )
         );
      float var15 = (
            this.field6389 * this.field6383 * this.field6395
               - this.field6385 * this.field6383 * this.field6399
               - this.field6395 * (this.field6388 * this.field6398)
               + this.field6391 * (this.field6398 * this.field6385)
               + this.field6394 * this.field6388 * this.field6399
               - this.field6394 * this.field6389 * this.field6391
         )
         * var1;
      float var16 = var1
         * (
            this.field6396 * this.field6383 * this.field6399
               + this.field6384 * -this.field6383 * this.field6395
               + this.field6398 * this.field6392 * this.field6395
               - this.field6391 * (this.field6398 * this.field6396)
               - this.field6394 * this.field6392 * this.field6399
               + this.field6391 * (this.field6384 * this.field6394)
         );
      float var17 = var1
         * (
            this.field6383 * this.field6384 * this.field6385
               - this.field6389 * (this.field6396 * this.field6383)
               - this.field6385 * (this.field6398 * this.field6392)
               + this.field6396 * this.field6398 * this.field6388
               + this.field6392 * this.field6394 * this.field6389
               - this.field6388 * (this.field6394 * this.field6384)
         );
      this.field6383 = var2;
      this.field6398 = var3;
      this.field6394 = var4;
      this.field6390 = var5;
      this.field6392 = var6;
      this.field6384 = var7;
      this.field6396 = var8;
      this.field6393 = var9;
      this.field6388 = var10;
      this.field6389 = var11;
      this.field6385 = var12;
      this.field6397 = var13;
      this.field6391 = var14;
      this.field6399 = var15;
      this.field6395 = var16;
      this.field6386 = var17;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cg")
   public void method11672() {
      float var1 = 1.0F / this.method11669((byte)-103);
      float var2 = (
            this.field6386 * (this.field6385 * this.field6384)
               - this.field6397 * this.field6384 * this.field6395
               - this.field6396 * this.field6389 * this.field6386
               + this.field6399 * (this.field6396 * this.field6397)
               + this.field6393 * this.field6389 * this.field6395
               - this.field6399 * (this.field6385 * this.field6393)
         )
         * var1;
      float var3 = (
            this.field6390 * this.field6385 * this.field6399
               + (
                  this.field6395 * (this.field6398 * this.field6397)
                     + -this.field6398 * this.field6385 * this.field6386
                     + this.field6386 * (this.field6394 * this.field6389)
                     - this.field6399 * (this.field6397 * this.field6394)
                     - this.field6395 * (this.field6390 * this.field6389)
               )
         )
         * var1;
      float var4 = var1
         * (
            this.field6394 * this.field6393 * this.field6399
               + (
                  this.field6398 * this.field6396 * this.field6386
                     - this.field6398 * this.field6393 * this.field6395
                     - this.field6386 * (this.field6384 * this.field6394)
               )
               + this.field6384 * this.field6390 * this.field6395
               - this.field6390 * this.field6396 * this.field6399
         );
      float var5 = var1
         * (
            this.field6396 * -this.field6398 * this.field6397
               + this.field6385 * (this.field6398 * this.field6393)
               + this.field6397 * (this.field6384 * this.field6394)
               - this.field6394 * this.field6393 * this.field6389
               - this.field6385 * (this.field6390 * this.field6384)
               + this.field6396 * this.field6390 * this.field6389
         );
      float var6 = (
            this.field6396 * this.field6388 * this.field6386
               + (this.field6385 * -this.field6392 * this.field6386 + this.field6392 * this.field6397 * this.field6395)
               - this.field6391 * (this.field6397 * this.field6396)
               - this.field6388 * this.field6393 * this.field6395
               + this.field6393 * this.field6385 * this.field6391
         )
         * var1;
      float var7 = (
            this.field6391 * (this.field6397 * this.field6394)
               + (
                  this.field6386 * (this.field6383 * this.field6385)
                     - this.field6395 * (this.field6397 * this.field6383)
                     - this.field6394 * this.field6388 * this.field6386
               )
               + this.field6388 * this.field6390 * this.field6395
               - this.field6391 * (this.field6385 * this.field6390)
         )
         * var1;
      float var8 = (
            this.field6395 * (this.field6383 * this.field6393)
               + -this.field6383 * this.field6396 * this.field6386
               + this.field6394 * this.field6392 * this.field6386
               - this.field6394 * this.field6393 * this.field6391
               - this.field6390 * this.field6392 * this.field6395
               + this.field6391 * (this.field6390 * this.field6396)
         )
         * var1;
      float var9 = var1
         * (
            this.field6385 * (this.field6390 * this.field6392)
               + (
                  this.field6397 * (this.field6383 * this.field6396)
                     - this.field6393 * this.field6383 * this.field6385
                     - this.field6394 * this.field6392 * this.field6397
                     + this.field6394 * this.field6393 * this.field6388
               )
               - this.field6390 * this.field6396 * this.field6388
         );
      float var10 = (
            this.field6392 * this.field6389 * this.field6386
               - this.field6397 * this.field6392 * this.field6399
               - this.field6386 * (this.field6384 * this.field6388)
               + this.field6384 * this.field6397 * this.field6391
               + this.field6399 * (this.field6393 * this.field6388)
               - this.field6393 * this.field6389 * this.field6391
         )
         * var1;
      float var11 = (
            this.field6391 * (this.field6390 * this.field6389)
               + (
                  this.field6399 * (this.field6397 * this.field6383)
                     + this.field6386 * (this.field6389 * -this.field6383)
                     + this.field6398 * this.field6388 * this.field6386
                     - this.field6391 * (this.field6398 * this.field6397)
                     - this.field6388 * this.field6390 * this.field6399
               )
         )
         * var1;
      float var12 = (
            this.field6383 * this.field6384 * this.field6386
               - this.field6393 * this.field6383 * this.field6399
               - this.field6392 * this.field6398 * this.field6386
               + this.field6391 * (this.field6393 * this.field6398)
               + this.field6399 * (this.field6390 * this.field6392)
               - this.field6391 * (this.field6384 * this.field6390)
         )
         * var1;
      float var13 = var1
         * (
            this.field6397 * (this.field6398 * this.field6392)
               + (this.field6397 * (-this.field6383 * this.field6384) + this.field6393 * this.field6383 * this.field6389)
               - this.field6388 * (this.field6398 * this.field6393)
               - this.field6392 * this.field6390 * this.field6389
               + this.field6388 * (this.field6390 * this.field6384)
         );
      float var14 = var1
         * (
            this.field6396 * this.field6389 * this.field6391
               + (
                  this.field6395 * (this.field6384 * this.field6388)
                     + (this.field6392 * this.field6385 * this.field6399 + this.field6389 * -this.field6392 * this.field6395)
                     - this.field6384 * this.field6385 * this.field6391
                     - this.field6399 * (this.field6396 * this.field6388)
               )
         );
      float var15 = (
            this.field6389 * this.field6383 * this.field6395
               - this.field6385 * this.field6383 * this.field6399
               - this.field6395 * (this.field6388 * this.field6398)
               + this.field6391 * (this.field6398 * this.field6385)
               + this.field6394 * this.field6388 * this.field6399
               - this.field6394 * this.field6389 * this.field6391
         )
         * var1;
      float var16 = var1
         * (
            this.field6396 * this.field6383 * this.field6399
               + this.field6384 * -this.field6383 * this.field6395
               + this.field6398 * this.field6392 * this.field6395
               - this.field6391 * (this.field6398 * this.field6396)
               - this.field6394 * this.field6392 * this.field6399
               + this.field6391 * (this.field6384 * this.field6394)
         );
      float var17 = var1
         * (
            this.field6383 * this.field6384 * this.field6385
               - this.field6389 * (this.field6396 * this.field6383)
               - this.field6385 * (this.field6398 * this.field6392)
               + this.field6396 * this.field6398 * this.field6388
               + this.field6392 * this.field6394 * this.field6389
               - this.field6388 * (this.field6394 * this.field6384)
         );
      this.field6383 = var2;
      this.field6398 = var3;
      this.field6394 = var4;
      this.field6390 = var5;
      this.field6392 = var6;
      this.field6384 = var7;
      this.field6396 = var8;
      this.field6393 = var9;
      this.field6388 = var10;
      this.field6389 = var11;
      this.field6385 = var12;
      this.field6397 = var13;
      this.field6391 = var14;
      this.field6399 = var15;
      this.field6395 = var16;
      this.field6386 = var17;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cw")
   public void method11673() {
      float var1 = 1.0F / this.method11669((byte)-94);
      float var2 = (
            this.field6386 * (this.field6385 * this.field6384)
               - this.field6397 * this.field6384 * this.field6395
               - this.field6396 * this.field6389 * this.field6386
               + this.field6399 * (this.field6396 * this.field6397)
               + this.field6393 * this.field6389 * this.field6395
               - this.field6399 * (this.field6385 * this.field6393)
         )
         * var1;
      float var3 = (
            this.field6390 * this.field6385 * this.field6399
               + (
                  this.field6395 * (this.field6398 * this.field6397)
                     + -this.field6398 * this.field6385 * this.field6386
                     + this.field6386 * (this.field6394 * this.field6389)
                     - this.field6399 * (this.field6397 * this.field6394)
                     - this.field6395 * (this.field6390 * this.field6389)
               )
         )
         * var1;
      float var4 = var1
         * (
            this.field6394 * this.field6393 * this.field6399
               + (
                  this.field6398 * this.field6396 * this.field6386
                     - this.field6398 * this.field6393 * this.field6395
                     - this.field6386 * (this.field6384 * this.field6394)
               )
               + this.field6384 * this.field6390 * this.field6395
               - this.field6390 * this.field6396 * this.field6399
         );
      float var5 = var1
         * (
            this.field6396 * -this.field6398 * this.field6397
               + this.field6385 * (this.field6398 * this.field6393)
               + this.field6397 * (this.field6384 * this.field6394)
               - this.field6394 * this.field6393 * this.field6389
               - this.field6385 * (this.field6390 * this.field6384)
               + this.field6396 * this.field6390 * this.field6389
         );
      float var6 = (
            this.field6396 * this.field6388 * this.field6386
               + (this.field6385 * -this.field6392 * this.field6386 + this.field6392 * this.field6397 * this.field6395)
               - this.field6391 * (this.field6397 * this.field6396)
               - this.field6388 * this.field6393 * this.field6395
               + this.field6393 * this.field6385 * this.field6391
         )
         * var1;
      float var7 = (
            this.field6391 * (this.field6397 * this.field6394)
               + (
                  this.field6386 * (this.field6383 * this.field6385)
                     - this.field6395 * (this.field6397 * this.field6383)
                     - this.field6394 * this.field6388 * this.field6386
               )
               + this.field6388 * this.field6390 * this.field6395
               - this.field6391 * (this.field6385 * this.field6390)
         )
         * var1;
      float var8 = (
            this.field6395 * (this.field6383 * this.field6393)
               + -this.field6383 * this.field6396 * this.field6386
               + this.field6394 * this.field6392 * this.field6386
               - this.field6394 * this.field6393 * this.field6391
               - this.field6390 * this.field6392 * this.field6395
               + this.field6391 * (this.field6390 * this.field6396)
         )
         * var1;
      float var9 = var1
         * (
            this.field6385 * (this.field6390 * this.field6392)
               + (
                  this.field6397 * (this.field6383 * this.field6396)
                     - this.field6393 * this.field6383 * this.field6385
                     - this.field6394 * this.field6392 * this.field6397
                     + this.field6394 * this.field6393 * this.field6388
               )
               - this.field6390 * this.field6396 * this.field6388
         );
      float var10 = (
            this.field6392 * this.field6389 * this.field6386
               - this.field6397 * this.field6392 * this.field6399
               - this.field6386 * (this.field6384 * this.field6388)
               + this.field6384 * this.field6397 * this.field6391
               + this.field6399 * (this.field6393 * this.field6388)
               - this.field6393 * this.field6389 * this.field6391
         )
         * var1;
      float var11 = (
            this.field6391 * (this.field6390 * this.field6389)
               + (
                  this.field6399 * (this.field6397 * this.field6383)
                     + this.field6386 * (this.field6389 * -this.field6383)
                     + this.field6398 * this.field6388 * this.field6386
                     - this.field6391 * (this.field6398 * this.field6397)
                     - this.field6388 * this.field6390 * this.field6399
               )
         )
         * var1;
      float var12 = (
            this.field6383 * this.field6384 * this.field6386
               - this.field6393 * this.field6383 * this.field6399
               - this.field6392 * this.field6398 * this.field6386
               + this.field6391 * (this.field6393 * this.field6398)
               + this.field6399 * (this.field6390 * this.field6392)
               - this.field6391 * (this.field6384 * this.field6390)
         )
         * var1;
      float var13 = var1
         * (
            this.field6397 * (this.field6398 * this.field6392)
               + (this.field6397 * (-this.field6383 * this.field6384) + this.field6393 * this.field6383 * this.field6389)
               - this.field6388 * (this.field6398 * this.field6393)
               - this.field6392 * this.field6390 * this.field6389
               + this.field6388 * (this.field6390 * this.field6384)
         );
      float var14 = var1
         * (
            this.field6396 * this.field6389 * this.field6391
               + (
                  this.field6395 * (this.field6384 * this.field6388)
                     + (this.field6392 * this.field6385 * this.field6399 + this.field6389 * -this.field6392 * this.field6395)
                     - this.field6384 * this.field6385 * this.field6391
                     - this.field6399 * (this.field6396 * this.field6388)
               )
         );
      float var15 = (
            this.field6389 * this.field6383 * this.field6395
               - this.field6385 * this.field6383 * this.field6399
               - this.field6395 * (this.field6388 * this.field6398)
               + this.field6391 * (this.field6398 * this.field6385)
               + this.field6394 * this.field6388 * this.field6399
               - this.field6394 * this.field6389 * this.field6391
         )
         * var1;
      float var16 = var1
         * (
            this.field6396 * this.field6383 * this.field6399
               + this.field6384 * -this.field6383 * this.field6395
               + this.field6398 * this.field6392 * this.field6395
               - this.field6391 * (this.field6398 * this.field6396)
               - this.field6394 * this.field6392 * this.field6399
               + this.field6391 * (this.field6384 * this.field6394)
         );
      float var17 = var1
         * (
            this.field6383 * this.field6384 * this.field6385
               - this.field6389 * (this.field6396 * this.field6383)
               - this.field6385 * (this.field6398 * this.field6392)
               + this.field6396 * this.field6398 * this.field6388
               + this.field6392 * this.field6394 * this.field6389
               - this.field6388 * (this.field6394 * this.field6384)
         );
      this.field6383 = var2;
      this.field6398 = var3;
      this.field6394 = var4;
      this.field6390 = var5;
      this.field6392 = var6;
      this.field6384 = var7;
      this.field6396 = var8;
      this.field6393 = var9;
      this.field6388 = var10;
      this.field6389 = var11;
      this.field6385 = var12;
      this.field6397 = var13;
      this.field6391 = var14;
      this.field6399 = var15;
      this.field6395 = var16;
      this.field6386 = var17;
   }

   @ObfuscatedSignature(descriptor = "(Lug;I)V")
   @ObfuscatedName("aq")
   public void method11706(classUG var1, int var2) {
      float var3 = var1.field6303.field6325 * var1.field6303.field6325;
      float var4 = var1.field6303.field6325 * var1.field6303.field6322;
      float var5 = var1.field6303.field6323 * var1.field6303.field6325;
      float var6 = var1.field6303.field6324 * var1.field6303.field6325;
      float var7 = var1.field6303.field6322 * var1.field6303.field6322;
      float var8 = var1.field6303.field6323 * var1.field6303.field6322;
      float var9 = var1.field6303.field6324 * var1.field6303.field6322;
      float var10 = var1.field6303.field6323 * var1.field6303.field6323;
      float var11 = var1.field6303.field6324 * var1.field6303.field6323;
      float var12 = var1.field6303.field6324 * var1.field6303.field6324;
      this.field6383 = var3 + var7 - var12 - var10;
      this.field6398 = var6 + var8 + var6 + var8;
      this.field6394 = var9 - var5 - var5 + var9;
      this.field6392 = var8 - var6 - var6 + var8;
      this.field6384 = var3 + var10 - var7 - var12;
      this.field6396 = var11 + var11 + var4 + var4;
      this.field6388 = var9 + var9 + var5 + var5;
      this.field6389 = var11 - var4 - var4 + var11;
      this.field6385 = var3 + var12 - var10 - var7;
      this.field6397 = 0.0F;
      this.field6393 = 0.0F;
      this.field6390 = 0.0F;
      this.field6391 = var1.field6305.field6425;
      this.field6399 = var1.field6305.field6423;
      this.field6395 = var1.field6305.field6424;
      this.field6386 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "(FFF)Luz;")
   @ObfuscatedName("ce")
   public classUZ method11682(float var1, float var2, float var3) {
      float var4 = 1.0F / (this.field6386 + (var2 * this.field6393 + var1 * this.field6390 + this.field6397 * var3));
      return classIK.method6261(
         var4 * (this.field6391 + (this.field6383 * var1 + var2 * this.field6392 + this.field6388 * var3)),
         var4 * (this.field6399 + (this.field6398 * var1 + var2 * this.field6384 + var3 * this.field6389)),
         (this.field6395 + (var3 * this.field6385 + (this.field6396 * var2 + var1 * this.field6394))) * var4,
         (byte)-64
      );
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method11698() {
      byte var1 = 31;
      byte var2 = 1;
      return 31 * var2 + Arrays.hashCode(this.field6401);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;)Ljava/lang/String;")
   @ObfuscatedName("lj")
   public static String method11694(classUU var0) {
      if (var0 == null) {
         var0.method11672();
      }

      StringBuilder var1 = new StringBuilder();
      var0.method11629(46404346);
      method11625(var0, (byte)-51);

      for (int var2 = 0; var2 < 4; var2++) {
         for (int var3 = 0; var3 < 4; var3++) {
            if (var3 > 0) {
               var1.append("\t");
            }

            float var4 = var0.field6401[4 * var2 + var3];
            if (Math.sqrt(var4 * var4) < 1.0E-4F) {
               var4 = 0.0F;
            }

            var1.append(var4);
         }

         var1.append("\n");
      }

      return var1.toString();
   }

   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("dc")
   public float method11684(float var1, float var2, float var3) {
      return this.field6383 * var1 + var2 * this.field6392 + var3 * this.field6388 + this.field6391;
   }

   @ObfuscatedSignature(descriptor = "(F)V")
   @ObfuscatedName("cb")
   public void method11648(float var1) {
      this.method11651(var1, var1, var1, -2129160486);
   }

   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("dm")
   public float method11690(float var1, float var2, float var3) {
      return this.field6385 * var3 + (this.field6396 * var2 + var1 * this.field6394) + this.field6395;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)Luu;")
   @ObfuscatedName("hm")
   public static classUU method11709(classUU var0) {
      synchronized (field6380) {
         if (field6381 * -1618719293 == 0) {
            return new classUU(var0);
         } else {
            classUU[] var10000 = field6380;
            int var10001 = field6381 * -1618719293 - 1;
            field6381 = (field6381 * -1618719293 - 1) * 943786731;
            classUU var2 = var10000[var10001];
            var2.method11720(var0);
            return var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("dx")
   public float method11691(float var1, float var2, float var3) {
      return this.field6385 * var3 + (this.field6396 * var2 + var1 * this.field6394) + this.field6395;
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("dy")
   public float[] method11703() {
      float[] var1 = new float[3];
      classUZ var2 = new classUZ(this.field6383, this.field6398, this.field6394);
      classUZ var3 = new classUZ(this.field6392, this.field6384, this.field6396);
      classUZ var4 = new classUZ(this.field6388, this.field6389, this.field6385);
      var1[0] = var2.method11844(-1808401369);
      var1[1] = var3.method11844(-1808401369);
      var1[2] = var4.method11844(-1808401369);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("we")
   public void method11728(classUU var1) {
      this.method11656(var1, -121561848);
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("du")
   public float[] method11704() {
      float[] var1 = new float[3];
      classUZ var2 = new classUZ(this.field6383, this.field6398, this.field6394);
      classUZ var3 = new classUZ(this.field6392, this.field6384, this.field6396);
      classUZ var4 = new classUZ(this.field6388, this.field6389, this.field6385);
      var1[0] = var2.method11844(-1808401369);
      var1[1] = var3.method11844(-1808401369);
      var1[2] = var4.method11844(-1808401369);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()[F")
   @ObfuscatedName("dt")
   public float[] method11705() {
      float[] var1 = new float[3];
      classUZ var2 = new classUZ(this.field6383, this.field6398, this.field6394);
      classUZ var3 = new classUZ(this.field6392, this.field6384, this.field6396);
      classUZ var4 = new classUZ(this.field6388, this.field6389, this.field6385);
      var1[0] = var2.method11844(-1808401369);
      var1[1] = var3.method11844(-1808401369);
      var1[2] = var4.method11844(-1808401369);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Luu;FI)V")
   @ObfuscatedName("tn")
   public static void method11649(classUU var0, float var1, int var2) {
      if (var0 == null) {
         var0.method11650(var1, var2);
      } else {
         try {
            var0.method11651(var1, var1, var1, -2138377657);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "uu.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lom;I)Z")
   @ObfuscatedName("ae")
   static boolean method11614(ObjectComposition var0, int var1) {
      try {
         if (var0.field4928 != null) {
            if (var1 != 1217057510) {
               throw new IllegalStateException();
            }

            int[] var2 = var0.field4928;

            for (int var3 = 0; var3 < var2.length; var3++) {
               if (var1 != 1217057510) {
                  throw new IllegalStateException();
               }

               int var4 = var2[var3];
               ObjectComposition var5 = classMU.method7771(var4, 1688742965);
               if (var5.field4951 * 1412472849 != -1) {
                  if (var1 != 1217057510) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         } else if (1412472849 * var0.field4951 != -1) {
            if (var1 != 1217057510) {
               throw new IllegalStateException();
            }

            return true;
         }

         return false;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uu.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;FFFB)F")
   @ObfuscatedName("kv")
   public static float method11685(classUU var0, float var1, float var2, float var3, byte var4) {
      if (var0 == null) {
         var0.method11686(var1, var1, var1, var4);
      }

      try {
         return var0.field6383 * var1 + var2 * var0.field6392 + var3 * var0.field6388 + var0.field6391;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[F")
   @ObfuscatedName("ag")
   float[] method11626(byte var1) {
      try {
         float[] var2 = new float[3];
         if (this.field6384 < 0.999) {
            if (var1 == 3) {
               throw new IllegalStateException();
            }

            if (this.field6386 > -0.999) {
               if (var1 == 3) {
                  throw new IllegalStateException();
               }

               var2[1] = (float)(-Math.asin(this.field6386));
               double var3 = Math.cos(var2[1]);
               var2[0] = (float)Math.atan2(this.field6391 / var3, this.field6396 / var3);
               var2[2] = (float)Math.atan2(this.field6388 / var3, this.field6386 / var3);
               return var2;
            }
         }

         var2[0] = 0.0F;
         var2[1] = (float)Math.atan2(this.field6394, 0.0);
         var2[2] = (float)Math.atan2(-this.field6385, this.field6392);
         return var2;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mm")
   public void method11729() {
      this.method11615(2033466195);
   }

   @ObfuscatedSignature(descriptor = "(Luu;B)V")
   @ObfuscatedName("aj")
   public void method11708(classUU var1, byte var2) {
      this.field6391 = this.field6398 + var1.field6398;
      this.field6396 = this.field6386 + var1.field6393;
      this.field6388 = this.field6391 + var1.field6390;
      this.field6396 = this.field6383 + var1.field6392;
      this.field6384 = this.field6397 + var1.field6396;
      this.field6397 = this.field6397 + var1.field6396;
      this.field6399 = this.field6389 + var1.field6392;
      this.field6396 = this.field6397 + var1.field6389;
      this.field6396 = this.field6383 + var1.field6393;
      this.field6394 = this.field6391 + var1.field6390;
      this.field6399 = this.field6397 + var1.field6384;
      this.field6393 = this.field6389 + var1.field6392;
      this.field6390 = this.field6384 + var1.field6399;
      this.field6399 = this.field6398 + var1.field6383;
      this.field6388 = this.field6383 + var1.field6389;
      this.field6391 = this.field6394 + var1.field6391;
   }

   @ObfuscatedSignature(descriptor = "(Lux;I)V")
   @ObfuscatedName("ap")
   public void method11667(classUX var1, int var2) {
      try {
         this.field6397 = var1.field6412;
         this.field6384 = var1.field6417;
         this.field6397 = var1.field6419;
         this.field6390 = 0.0F;
         this.field6397 = var1.field6414;
         this.field6391 = var1.field6414;
         this.field6394 = var1.field6416;
         this.field6399 = 0.0F;
         this.field6394 = var1.field6417;
         this.field6389 = var1.field6413;
         this.field6389 = var1.field6415;
         this.field6392 = 0.0F;
         this.field6398 = var1.field6418;
         this.field6399 = var1.field6419;
         this.field6398 = var1.field6416;
         this.field6388 = 1.0F;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uu.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ai")
   public void method11674(int var1) {
      try {
         float var2 = 1.0F / this.method11669((byte)40);
         float var3 = (
               this.field6398 * (this.field6397 * this.field6391)
                  - this.field6393 * this.field6385 * this.field6383
                  - this.field6394 * this.field6388 * this.field6385
                  + this.field6398 * (this.field6399 * this.field6399)
                  + this.field6390 * this.field6385 * this.field6393
                  - this.field6392 * (this.field6390 * this.field6396)
            )
            * var2;
         float var4 = (
               this.field6389 * this.field6399 * this.field6394
                  + (
                     this.field6392 * (this.field6393 * this.field6385)
                        + -this.field6386 * this.field6395 * this.field6399
                        + this.field6399 * (this.field6392 * this.field6388)
                        - this.field6399 * (this.field6392 * this.field6386)
                        - this.field6398 * (this.field6391 * this.field6396)
                  )
            )
            * var2;
         float var5 = var2
            * (
               this.field6399 * this.field6398 * this.field6397
                  + (
                     this.field6394 * this.field6386 * this.field6386
                        - this.field6397 * this.field6384 * this.field6391
                        - this.field6385 * (this.field6392 * this.field6390)
                  )
                  + this.field6384 * this.field6384 * this.field6391
                  - this.field6386 * this.field6392 * this.field6399
            );
         float var6 = var2
            * (
               this.field6398 * -this.field6395 * this.field6395
                  + this.field6383 * (this.field6386 * this.field6384)
                  + this.field6392 * (this.field6394 * this.field6396)
                  - this.field6398 * this.field6390 * this.field6383
                  - this.field6390 * (this.field6386 * this.field6396)
                  + this.field6388 * this.field6384 * this.field6385
            );
         float var7 = (
               this.field6398 * this.field6383 * this.field6393
                  + (this.field6393 * -this.field6384 * this.field6399 + this.field6390 * this.field6389 * this.field6392)
                  - this.field6399 * (this.field6393 * this.field6392)
                  - this.field6384 * this.field6385 * this.field6390
                  + this.field6383 * this.field6384 * this.field6390
            )
            * var2;
         float var8 = (
               this.field6389 * (this.field6385 * this.field6384)
                  + (
                     this.field6386 * (this.field6383 * this.field6390)
                        - this.field6394 * (this.field6384 * this.field6393)
                        - this.field6399 * this.field6384 * this.field6397
                  )
                  + this.field6396 * this.field6393 * this.field6385
                  - this.field6397 * (this.field6397 * this.field6397)
            )
            * var2;
         float var9 = (
               this.field6398 * (this.field6397 * this.field6383)
                  + -this.field6392 * this.field6394 * this.field6391
                  + this.field6383 * this.field6396 * this.field6390
                  - this.field6391 * this.field6398 * this.field6398
                  - this.field6389 * this.field6383 * this.field6394
                  + this.field6386 * (this.field6395 * this.field6396)
            )
            * var2;
         float var10 = var2
            * (
               this.field6399 * (this.field6392 * this.field6385)
                  + (
                     this.field6386 * (this.field6388 * this.field6394)
                        - this.field6392 * this.field6386 * this.field6384
                        - this.field6386 * this.field6391 * this.field6398
                        + this.field6397 * this.field6385 * this.field6390
                  )
                  - this.field6384 * this.field6383 * this.field6384
            );
         float var11 = (
               this.field6383 * this.field6389 * this.field6384
                  - this.field6395 * this.field6396 * this.field6399
                  - this.field6384 * (this.field6397 * this.field6390)
                  + this.field6391 * this.field6394 * this.field6388
                  + this.field6384 * (this.field6397 * this.field6393)
                  - this.field6388 * this.field6390 * this.field6396
            )
            * var2;
         float var12 = (
               this.field6393 * (this.field6394 * this.field6383)
                  + (
                     this.field6394 * (this.field6396 * this.field6396)
                        + this.field6399 * (this.field6397 * -this.field6394)
                        + this.field6389 * this.field6393 * this.field6388
                        - this.field6388 * (this.field6391 * this.field6399)
                        - this.field6399 * this.field6389 * this.field6393
                  )
            )
            * var2;
         float var13 = (
               this.field6393 * this.field6391 * this.field6389
                  - this.field6391 * this.field6395 * this.field6397
                  - this.field6394 * this.field6395 * this.field6392
                  + this.field6390 * (this.field6389 * this.field6398)
                  + this.field6386 * (this.field6397 * this.field6383)
                  - this.field6386 * (this.field6393 * this.field6386)
            )
            * var2;
         float var14 = var2
            * (
               this.field6384 * (this.field6392 * this.field6388)
                  + (this.field6391 * (-this.field6397 * this.field6386) + this.field6385 * this.field6398 * this.field6385)
                  - this.field6388 * (this.field6383 * this.field6388)
                  - this.field6399 * this.field6396 * this.field6394
                  + this.field6396 * (this.field6386 * this.field6388)
            );
         float var15 = var2
            * (
               this.field6393 * this.field6399 * this.field6393
                  + (
                     this.field6394 * (this.field6389 * this.field6391)
                        + (this.field6396 * this.field6394 * this.field6385 + this.field6393 * -this.field6386 * this.field6391)
                        - this.field6398 * this.field6398 * this.field6398
                        - this.field6398 * (this.field6392 * this.field6386)
                  )
            );
         float var16 = (
               this.field6395 * this.field6392 * this.field6395
                  - this.field6383 * this.field6388 * this.field6394
                  - this.field6394 * (this.field6393 * this.field6388)
                  + this.field6388 * (this.field6393 * this.field6383)
                  + this.field6392 * this.field6392 * this.field6399
                  - this.field6384 * this.field6392 * this.field6386
            )
            * var2;
         float var17 = var2
            * (
               this.field6388 * this.field6388 * this.field6399
                  + this.field6385 * -this.field6397 * this.field6398
                  + this.field6389 * this.field6389 * this.field6391
                  - this.field6394 * (this.field6390 * this.field6399)
                  - this.field6384 * this.field6396 * this.field6388
                  + this.field6391 * (this.field6392 * this.field6389)
            );
         float var18 = var2
            * (
               this.field6389 * this.field6389 * this.field6388
                  - this.field6395 * (this.field6398 * this.field6384)
                  - this.field6398 * (this.field6395 * this.field6398)
                  + this.field6388 * this.field6390 * this.field6392
                  + this.field6393 * this.field6383 * this.field6399
                  - this.field6398 * (this.field6398 * this.field6398)
            );
         this.field6383 = var3;
         this.field6388 = var4;
         this.field6394 = var5;
         this.field6394 = var6;
         this.field6398 = var7;
         this.field6386 = var8;
         this.field6384 = var9;
         this.field6383 = var10;
         this.field6392 = var11;
         this.field6395 = var12;
         this.field6383 = var13;
         this.field6391 = var14;
         this.field6386 = var15;
         this.field6391 = var16;
         this.field6397 = var17;
         this.field6398 = var18;
      } catch (RuntimeException var19) {
         throw classEG.method3899(var19, "uu.ai(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;FFF[F)V")
   @ObfuscatedName("xk")
   public static void method11678(classUU var0, float var1, float var2, float var3, float[] var4) {
      var4[0] = var1 * var0.field6383 + var2 * var0.field6392 + var3 * var0.field6388 + var0.field6391;
      var4[1] = var2 * var0.field6384 + var1 * var0.field6398 + var3 * var0.field6389 + var0.field6399;
      var4[2] = var0.field6395 + (var3 * var0.field6385 + (var2 * var0.field6396 + var1 * var0.field6394));
      if (var4.length > 3) {
         var4[3] = var1 * var0.field6390 + var0.field6393 * var2 + var0.field6397 * var3 + var0.field6386;
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFB)F")
   @ObfuscatedName("av")
   public float method11686(float var1, float var2, float var3, byte var4) {
      try {
         return this.field6396 * var1 + var2 * this.field6395 + var3 * this.field6396 + this.field6399;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "uu.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;Lux;I)V")
   @ObfuscatedName("uc")
   public static void method11668(classUU var0, classUX var1, int var2) {
      if (var0 == null) {
         var0.method11667(var1, var2);
      }

      try {
         var0.field6383 = var1.field6420;
         var0.field6398 = var1.field6413;
         var0.field6394 = var1.field6412;
         var0.field6390 = 0.0F;
         var0.field6392 = var1.field6410;
         var0.field6384 = var1.field6414;
         var0.field6396 = var1.field6418;
         var0.field6393 = 0.0F;
         var0.field6388 = var1.field6419;
         var0.field6389 = var1.field6411;
         var0.field6385 = var1.field6409;
         var0.field6397 = 0.0F;
         var0.field6391 = var1.field6417;
         var0.field6399 = var1.field6416;
         var0.field6395 = var1.field6415;
         var0.field6386 = 1.0F;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uu.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;B)V")
   @ObfuscatedName("aa")
   public void method11710(classUU var1, byte var2) {
      this.field6398 = var1.field6389;
      this.field6398 = var1.field6392;
      this.field6386 = var1.field6398;
      this.field6391 = var1.field6383;
      this.field6383 = var1.field6385;
      this.field6391 = var1.field6384;
      this.field6388 = var1.field6386;
      this.field6395 = var1.field6392;
      this.field6398 = var1.field6393;
      this.field6396 = var1.field6388;
      this.field6388 = var1.field6395;
      this.field6393 = var1.field6389;
      this.field6396 = var1.field6397;
      this.field6389 = var1.field6390;
      this.field6384 = var1.field6388;
      this.field6392 = var1.field6399;
   }

   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("rx")
   public float method11730(float var1, float var2, float var3) {
      return this.method11687(var1, var2, var3, 1097366312);
   }

   @ObfuscatedSignature(descriptor = "(Luu;)V")
   @ObfuscatedName("cu")
   public void method11646(classUU var1) {
      System.arraycopy(var1.field6401, 0, this.field6401, 0, 16);
   }

   @ObfuscatedSignature(descriptor = "(FFFI)Luz;")
   @ObfuscatedName("ak")
   public classUZ method11683(float var1, float var2, float var3, int var4) {
      try {
         float var5 = 1.0F / (this.field6389 + (var2 * this.field6396 + var1 * this.field6388 + this.field6394 * var3));
         return classIK.method6261(
            var5 * (this.field6391 + (this.field6394 * var1 + var2 * this.field6391 + this.field6386 * var3)),
            var5 * (this.field6393 + (this.field6396 * var1 + var2 * this.field6386 + var3 * this.field6392)),
            (this.field6385 + (var3 * this.field6390 + (this.field6394 * var2 + var1 * this.field6384))) * var5,
            (byte)13
         );
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uu.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FI)V")
   @ObfuscatedName("ao")
   public void method11650(float var1, int var2) {
      try {
         this.method11651(var1, var1, var1, -2138377657);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uu.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;)V")
   @ObfuscatedName("cf")
   public void method11664(classUL var1) {
      float var2 = var1.field6323 * var1.field6322;
      float var3 = var1.field6322 * var1.field6322;
      float var4 = var1.field6324 * var1.field6323;
      float var5 = var1.field6324 * var1.field6322;
      float var6 = var1.field6322 * var1.field6325;
      float var7 = var1.field6322 * var1.field6323;
      float var8 = var1.field6325 * var1.field6323;
      float var9 = var1.field6324 * var1.field6325;
      float var10 = var1.field6322 * var1.field6323;
      float var11 = var1.field6324 * var1.field6324;
      this.field6396 = var2 + var6 - var11 - var9;
      this.field6398 = var7 + var5 + var7 + var5;
      this.field6389 = var8 - var4 - var4 + var8;
      this.field6397 = var7 + (var7 - var5 - var5);
      this.field6398 = var2 + var9 - var6 - var11;
      this.field6394 = var3 + (var10 + var3 + var10);
      this.field6385 = var4 + (var8 + var4 + var8);
      this.field6385 = var10 - var3 - var3 + var10;
      this.field6393 = var2 + var11 - var9 - var6;
   }
}
