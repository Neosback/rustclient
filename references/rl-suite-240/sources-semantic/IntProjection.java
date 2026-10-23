import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("eu")
public class IntProjection extends Projection implements net.runelite.api.IntProjection {
   @ObfuscatedName("az")
   int field1917;
   @ObfuscatedName("dj")
   public float field1926;
   @ObfuscatedName("vq")
   public float field1931;
   @ObfuscatedName("yh")
   public float field1932;
   @ObfuscatedName("ao")
   int field1919;
   @ObfuscatedName("zz")
   public float field1934;
   @ObfuscatedName("lv")
   public float field1928;
   @ObfuscatedName("af")
   int field1918;
   @ObfuscatedSignature(descriptor = "Lup;")
   @ObfuscatedName("ab")
   classUP field1921;
   @ObfuscatedName("qx")
   public static int field1936;
   @ObfuscatedName("kg")
   public float field1927;
   @ObfuscatedName("ax")
   float field1920;
   @ObfuscatedName("bz")
   static int field1935;
   @ObfuscatedName("as")
   float field1922;
   @ObfuscatedSignature(descriptor = "Leu;")
   @ObfuscatedName("rb")
   public static IntProjection field1937;
   @ObfuscatedName("ac")
   float field1923;
   @ObfuscatedName("ti")
   public final float[] field1914 = new float[3];
   @ObfuscatedSignature(descriptor = "Lup;")
   @ObfuscatedName("ag")
   classUP field1916;
   @ObfuscatedName("na")
   public float field1929;
   @ObfuscatedName("ae")
   int field1924;
   @ObfuscatedName("sn")
   public float field1930;
   @ObfuscatedName("yv")
   public float field1933;
   @ObfuscatedName("al")
   int field1925;
   @ObfuscatedName("aa")
   float field1915;

   @ObfuscatedSignature(descriptor = "(Lee;IIIIJ)V")
   @ObfuscatedName("as")
   @Override
   void vmethod422(Renderable var1, int var2, int var3, int var4, int var5, long var6) {
      var1.vmethod415(
         var2,
         this.field1921,
         this.field1916,
         var3,
         var4,
         var5,
         this.field1925 * -244055134,
         this.field1919 * -39987553,
         609299141 * this.field1918,
         var6,
         this.field1917 * -1931395903,
         this.field1924 * 1167982009,
         false
      );
   }

   @ObfuscatedSignature(descriptor = "(Lee;IIIIJ)V")
   @ObfuscatedName("az")
   @Override
   void vmethod435(Renderable var1, int var2, int var3, int var4, int var5, long var6) {
      if ((client.field1021 & 17) == 1) {
         client.field1117.draw(this, null, var1, var2, var3, var4 - var1.getAnimationHeightOffset(), var5, var6);
      } else {
         var1.method3854(
            var2,
            this.field1921,
            this.field1916,
            var3,
            var4,
            var5,
            this.field1925 * -1862793759,
            this.field1919 * -39987553,
            this.field1918 * 706474989,
            var6,
            this.field1917 * -1931395903,
            this.field1924 * 1167982009,
            false
         );
      }
   }

   public float getYawCos() {
      return this.field1923;
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfj;IIII)V")
   @ObfuscatedName("af")
   @Override
   void vmethod426(Scene var1, SceneTilePaint var2, int var3, int var4, int var5, int var6) {
      try {
         if (1 != classFH.field2255.field2215) {
            float var7;
            float var8 = var7 = (var4 << 7) - this.field1925 * -1862793759;
            float var9;
            float var10 = var9 = (var5 << 7) - this.field1918 * 706474989;
            float var11;
            float var12 = var11 = 128.0F + var8;
            float var13;
            float var14 = var13 = var10 + 128.0F;
            float var15 = var1.field2058[var3][var4][var5] - -39987553 * this.field1919;
            float var16 = var1.field2058[var3][var4 + 1][var5] - -39987553 * this.field1919;
            float var17 = var1.field2058[var3][1 + var4][1 + var5] - this.field1919 * -39987553;
            float var18 = var1.field2058[var3][var4][1 + var5] - this.field1919 * -39987553;
            float var19 = var10 * this.field1922 + var8 * this.field1923;
            var10 = var10 * this.field1923 - var8 * this.field1922;
            var19 = this.field1915 * var15 - var10 * this.field1920;
            var10 = var10 * this.field1915 + var15 * this.field1920;
            if (!(var10 < 50.0F)) {
               var19 = var9 * this.field1922 + this.field1923 * var12;
               var9 = this.field1923 * var9 - this.field1922 * var12;
               var19 = this.field1915 * var16 - this.field1920 * var9;
               var9 = this.field1920 * var16 + var9 * this.field1915;
               if (var9 < 50.0F) {
                  if (var6 != -1565439737) {
                     ;
                  }
               } else {
                  var19 = var14 * this.field1922 + var11 * this.field1923;
                  var14 = var14 * this.field1923 - var11 * this.field1922;
                  var19 = var17 * this.field1915 - this.field1920 * var14;
                  var14 = this.field1915 * var14 + this.field1920 * var17;
                  if (var14 < 50.0F) {
                     if (var6 == -1565439737) {
                        throw new IllegalStateException();
                     }
                  } else {
                     var19 = var13 * this.field1922 + this.field1923 * var7;
                     var13 = this.field1923 * var13 - this.field1922 * var7;
                     var19 = var18 * this.field1915 - this.field1920 * var13;
                     var13 = var13 * this.field1915 + this.field1920 * var18;
                     if (!(var13 < 50.0F)) {
                        this.method5056(var1, var2, var4, var5, var19, var19, var19, var19, var19, var19, var19, var19, var10, var9, var14, var13, 650575801);
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var20) {
         throw classEG.method3899(var20, "eu.af(" + ')');
      }
   }

   public float getPitchSin() {
      return this.field1920;
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfn;II)V")
   @ObfuscatedName("aj")
   @Override
   void vmethod433(Scene var1, SceneTileModel var2, int var3, int var4) {
      if (1 != classFH.field2255.field2215) {
         int var5 = var2.field2320.length;

         for (int var6 = 0; var6 < var5; var6++) {
            float var7 = var2.field2320[var6] - this.field1925 * -196075592;
            float var8 = var2.field2321[var6] - this.field1919 * -1226183590;
            float var9 = var2.field2325[var6] - this.field1918 * 1133029733;
            float var10 = var9 * this.field1922 + this.field1923 * var7;
            var9 = this.field1923 * var9 - var7 * this.field1922;
            var10 = var8 * this.field1915 - var9 * this.field1920;
            var9 = var8 * this.field1920 + var9 * this.field1915;
            if (var9 < 50.0F) {
               return;
            }

            if (null != var2.field2322) {
               SceneTileModel.field2337[var6] = (int)var10;
               SceneTileModel.field2333[var6] = (int)var10;
               SceneTileModel.field2339[var6] = (int)var9;
            }

            SceneTileModel.field2335[var6] = classFH.method4725() + var10 * classFH.method4747() / var9;
            SceneTileModel.field2338[var6] = classFH.method4730() + var10 * classFH.method4747() / var9;
            SceneTileModel.field2336[var6] = classJE.method6398(var9, 2113227426);
         }

         Projection.method5052(this, var1, var2, var3, var4, -305409447);
      }
   }

   public float getPitchCos() {
      return this.field1915;
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfn;II)V")
   @ObfuscatedName("aq")
   @Override
   void vmethod434(Scene var1, SceneTileModel var2, int var3, int var4) {
      if (1 != classFH.field2255.field2215) {
         int var5 = var2.field2320.length;

         for (int var6 = 0; var6 < var5; var6++) {
            float var7 = var2.field2320[var6] - this.field1925 * -1862793759;
            float var8 = var2.field2321[var6] - this.field1919 * -39987553;
            float var9 = var2.field2325[var6] - this.field1918 * 706474989;
            float var10 = var9 * this.field1922 + this.field1923 * var7;
            var9 = this.field1923 * var9 - var7 * this.field1922;
            var10 = var8 * this.field1915 - var9 * this.field1920;
            var9 = var8 * this.field1920 + var9 * this.field1915;
            if (var9 < 50.0F) {
               return;
            }

            if (null != var2.field2322) {
               SceneTileModel.field2337[var6] = (int)var10;
               SceneTileModel.field2333[var6] = (int)var10;
               SceneTileModel.field2339[var6] = (int)var9;
            }

            SceneTileModel.field2335[var6] = classFH.method4725() + var10 * classFH.method4747() / var9;
            SceneTileModel.field2338[var6] = classFH.method4730() + var10 * classFH.method4747() / var9;
            SceneTileModel.field2336[var6] = classJE.method6398(var9, 2090944221);
         }

         Projection.method5052(this, var1, var2, var3, var4, -930933709);
      }
   }

   IntProjection(int var1, int var2, int var3, classUP var4, classUP var5, int var6, int var7) {
      this.field1925 = var1 * 477298209;
      this.field1919 = var2 * 1184613727;
      this.field1918 = -442867227 * var3;
      this.field1921 = var4;
      this.field1916 = var5;
      this.field1920 = var4.method11510(2090579777);
      this.field1915 = var4.method11512((byte)127);
      this.field1922 = var5.method11510(2094340517);
      this.field1923 = var5.method11512((byte)127);
      this.field1917 = var6 * -1570942143;
      this.field1924 = 1240004745 * var7;
   }

   public int getCameraX() {
      return this.field1925 * -1862793759;
   }

   public int getCameraZ() {
      return this.field1918 * 706474989;
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfn;III)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod431(Scene var1, SceneTileModel var2, int var3, int var4, int var5) {
      try {
         if (1 != classFH.field2255.field2215) {
            int var6 = var2.field2320.length;

            for (int var7 = 0; var7 < var6; var7++) {
               if (var5 == 52058636) {
                  throw new IllegalStateException();
               }

               float var8 = var2.field2320[var7] - this.field1925 * -1862793759;
               float var9 = var2.field2321[var7] - this.field1919 * -39987553;
               float var10 = var2.field2325[var7] - this.field1918 * 706474989;
               float var11 = var10 * this.field1922 + this.field1923 * var8;
               var10 = this.field1923 * var10 - var8 * this.field1922;
               var11 = var9 * this.field1915 - var10 * this.field1920;
               var10 = var9 * this.field1920 + var10 * this.field1915;
               if (var10 < 50.0F) {
                  if (var5 == 52058636) {
                     throw new IllegalStateException();
                  }

                  return;
               }

               if (null != var2.field2322) {
                  if (var5 == 52058636) {
                     throw new IllegalStateException();
                  }

                  SceneTileModel.field2337[var7] = (int)var11;
                  SceneTileModel.field2333[var7] = (int)var11;
                  SceneTileModel.field2339[var7] = (int)var10;
               }

               SceneTileModel.field2335[var7] = classFH.method4725() + var11 * classFH.method4747() / var10;
               SceneTileModel.field2338[var7] = classFH.method4730() + var11 * classFH.method4747() / var10;
               SceneTileModel.field2336[var7] = classJE.method6398(var10, 2107920547);
            }

            Projection.method5052(this, var1, var2, var3, var4, 474971057);
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "eu.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lee;IIIIJ)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod425(Renderable var1, int var2, int var3, int var4, int var5, long var6) {
      var1.vmethod415(
         var2,
         this.field1921,
         this.field1916,
         var3,
         var4,
         var5,
         this.field1925 * -1862793759,
         this.field1919 * -39987553,
         706474989 * this.field1918,
         var6,
         this.field1917 * -1931395903,
         this.field1924 * 1167982009,
         false
      );
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfj;III)V")
   @ObfuscatedName("ac")
   @Override
   void vmethod427(Scene var1, SceneTilePaint var2, int var3, int var4, int var5) {
      if (1 != classFH.field2255.field2215) {
         float var6;
         float var7 = var6 = (var4 << 7) - this.field1925 * -1862793759;
         float var8;
         float var9 = var8 = (var5 << 7) - this.field1918 * 706474989;
         float var10;
         float var11 = var10 = 128.0F + var7;
         float var12;
         float var13 = var12 = var9 + 128.0F;
         float var14 = var1.field2058[var3][var4][var5] - -39987553 * this.field1919;
         float var15 = var1.field2058[var3][var4 + 1][var5] - -39987553 * this.field1919;
         float var16 = var1.field2058[var3][1 + var4][1 + var5] - this.field1919 * -39987553;
         float var17 = var1.field2058[var3][var4][1 + var5] - this.field1919 * -39987553;
         float var18 = var9 * this.field1922 + var7 * this.field1923;
         var9 = var9 * this.field1923 - var7 * this.field1922;
         var18 = this.field1915 * var14 - var9 * this.field1920;
         var9 = var9 * this.field1915 + var14 * this.field1920;
         if (!(var9 < 50.0F)) {
            var18 = var8 * this.field1922 + this.field1923 * var11;
            var8 = this.field1923 * var8 - this.field1922 * var11;
            var18 = this.field1915 * var15 - this.field1920 * var8;
            var8 = this.field1920 * var15 + var8 * this.field1915;
            if (!(var8 < 50.0F)) {
               var18 = var13 * this.field1922 + var10 * this.field1923;
               var13 = var13 * this.field1923 - var10 * this.field1922;
               var18 = var16 * this.field1915 - this.field1920 * var13;
               var13 = this.field1915 * var13 + this.field1920 * var16;
               if (!(var13 < 50.0F)) {
                  var18 = var12 * this.field1922 + this.field1923 * var6;
                  var12 = this.field1923 * var12 - this.field1922 * var6;
                  var18 = var17 * this.field1915 - this.field1920 * var12;
                  var12 = var12 * this.field1915 + this.field1920 * var17;
                  if (!(var12 < 50.0F)) {
                     this.method5056(var1, var2, var4, var5, var18, var18, var18, var18, var18, var18, var18, var18, var9, var8, var13, var12, 2078436232);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfn;II)V")
   @ObfuscatedName("ay")
   @Override
   void vmethod432(Scene var1, SceneTileModel var2, int var3, int var4) {
      if (1 != classFH.field2255.field2215) {
         int var5 = var2.field2320.length;

         for (int var6 = 0; var6 < var5; var6++) {
            float var7 = var2.field2320[var6] - this.field1925 * 223124833;
            float var8 = var2.field2321[var6] - this.field1919 * -39987553;
            float var9 = var2.field2325[var6] - this.field1918 * -1197555864;
            float var10 = var9 * this.field1922 + this.field1923 * var7;
            var9 = this.field1923 * var9 - var7 * this.field1922;
            var10 = var8 * this.field1915 - var9 * this.field1920;
            var9 = var8 * this.field1920 + var9 * this.field1915;
            if (var9 < 50.0F) {
               return;
            }

            if (null != var2.field2322) {
               SceneTileModel.field2337[var6] = (int)var10;
               SceneTileModel.field2333[var6] = (int)var10;
               SceneTileModel.field2339[var6] = (int)var9;
            }

            SceneTileModel.field2335[var6] = classFH.method4725() + var10 * classFH.method4747() / var9;
            SceneTileModel.field2338[var6] = classFH.method4730() + var10 * classFH.method4747() / var9;
            SceneTileModel.field2336[var6] = classJE.method6398(var9, 2109625642);
         }

         Projection.method5052(this, var1, var2, var3, var4, -427030004);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;B)Z")
   @ObfuscatedName("af")
   public static boolean method4108(CharSequence var0, byte var1) {
      try {
         return classMQ.method7683(var0, 10, true, -349565276);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "eu.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfj;III)V")
   @ObfuscatedName("al")
   @Override
   void vmethod429(Scene var1, SceneTilePaint var2, int var3, int var4, int var5) {
      if (1 != classFH.field2255.field2215) {
         float var6;
         float var7 = var6 = (var4 << 7) - this.field1925 * -1862793759;
         float var8;
         float var9 = var8 = (var5 << 7) - this.field1918 * 706474989;
         float var10;
         float var11 = var10 = 128.0F + var7;
         float var12;
         float var13 = var12 = var9 + 128.0F;
         float var14 = var1.field2058[var3][var4][var5] - -39987553 * this.field1919;
         float var15 = var1.field2058[var3][var4 + 1][var5] - -39987553 * this.field1919;
         float var16 = var1.field2058[var3][1 + var4][1 + var5] - this.field1919 * -39987553;
         float var17 = var1.field2058[var3][var4][1 + var5] - this.field1919 * -39987553;
         float var18 = var9 * this.field1922 + var7 * this.field1923;
         var9 = var9 * this.field1923 - var7 * this.field1922;
         var18 = this.field1915 * var14 - var9 * this.field1920;
         var9 = var9 * this.field1915 + var14 * this.field1920;
         if (!(var9 < 50.0F)) {
            var18 = var8 * this.field1922 + this.field1923 * var11;
            var8 = this.field1923 * var8 - this.field1922 * var11;
            var18 = this.field1915 * var15 - this.field1920 * var8;
            var8 = this.field1920 * var15 + var8 * this.field1915;
            if (!(var8 < 50.0F)) {
               var18 = var13 * this.field1922 + var10 * this.field1923;
               var13 = var13 * this.field1923 - var10 * this.field1922;
               var18 = var16 * this.field1915 - this.field1920 * var13;
               var13 = this.field1915 * var13 + this.field1920 * var16;
               if (!(var13 < 50.0F)) {
                  var18 = var12 * this.field1922 + this.field1923 * var6;
                  var12 = this.field1923 * var12 - this.field1922 * var6;
                  var18 = var17 * this.field1915 - this.field1920 * var12;
                  var12 = var12 * this.field1915 + this.field1920 * var17;
                  if (!(var12 < 50.0F)) {
                     this.method5056(var1, var2, var4, var5, var18, var18, var18, var18, var18, var18, var18, var18, var9, var8, var13, var12, -329453956);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lee;IIIIJ)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod424(Renderable var1, int var2, int var3, int var4, int var5, long var6) {
      var1.vmethod415(
         var2,
         this.field1921,
         this.field1916,
         var3,
         var4,
         var5,
         this.field1925 * -1862793759,
         this.field1919 * -39987553,
         706474989 * this.field1918,
         var6,
         this.field1917 * -1931395903,
         this.field1924 * 1167982009,
         false
      );
   }

   @ObfuscatedSignature(descriptor = "(II)Lsh;")
   @ObfuscatedName("as")
   static classSH method4109(int var0, int var1) {
      try {
         classSH var2 = (classSH)classOF.method8436(classOG.method8466((byte)-27), var0, -455431125);
         if (var2 == null) {
            var2 = classSH.field5934;
         }

         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "eu.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lee;IIIIJ)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod423(Renderable var1, int var2, int var3, int var4, int var5, long var6) {
      var1.vmethod415(
         var2,
         this.field1921,
         this.field1916,
         var3,
         var4,
         var5,
         this.field1925 * -1862793759,
         this.field1919 * -39987553,
         -159365702 * this.field1918,
         var6,
         this.field1917 * -1931395903,
         this.field1924 * 1167982009,
         false
      );
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gg")
   static final void method4111(int var0) {
      try {
         classPV.method9290(client.field825, -226350443);
         classYY.field7116 = true;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "eu.gg(" + ')');
      }
   }

   public float[] project(float var1, float var2, float var3) {
      return this.project(var1, var2, var3, this.field1914);
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfj;III)V")
   @ObfuscatedName("aa")
   @Override
   void vmethod428(Scene var1, SceneTilePaint var2, int var3, int var4, int var5) {
      if (1 != classFH.field2255.field2215) {
         float var6;
         float var7 = var6 = (var4 << 7) - this.field1925 * 505713928;
         float var8;
         float var9 = var8 = (var5 << 7) - this.field1918 * 706474989;
         float var10;
         float var11 = var10 = 128.0F + var7;
         float var12;
         float var13 = var12 = var9 + 128.0F;
         float var14 = var1.field2058[var3][var4][var5] - -1627652707 * this.field1919;
         float var15 = var1.field2058[var3][var4 + 1][var5] - 43222010 * this.field1919;
         float var16 = var1.field2058[var3][1 + var4][1 + var5] - this.field1919 * -482231816;
         float var17 = var1.field2058[var3][var4][1 + var5] - this.field1919 * 623749917;
         float var18 = var9 * this.field1922 + var7 * this.field1923;
         var9 = var9 * this.field1923 - var7 * this.field1922;
         var18 = this.field1915 * var14 - var9 * this.field1920;
         var9 = var9 * this.field1915 + var14 * this.field1920;
         if (!(var9 < 50.0F)) {
            var18 = var8 * this.field1922 + this.field1923 * var11;
            var8 = this.field1923 * var8 - this.field1922 * var11;
            var18 = this.field1915 * var15 - this.field1920 * var8;
            var8 = this.field1920 * var15 + var8 * this.field1915;
            if (!(var8 < 50.0F)) {
               var18 = var13 * this.field1922 + var10 * this.field1923;
               var13 = var13 * this.field1923 - var10 * this.field1922;
               var18 = var16 * this.field1915 - this.field1920 * var13;
               var13 = this.field1915 * var13 + this.field1920 * var16;
               if (!(var13 < 50.0F)) {
                  var18 = var12 * this.field1922 + this.field1923 * var6;
                  var12 = this.field1923 * var12 - this.field1922 * var6;
                  var18 = var17 * this.field1915 - this.field1920 * var12;
                  var12 = var12 * this.field1915 + this.field1920 * var17;
                  if (!(var12 < 50.0F)) {
                     this.method5056(var1, var2, var4, var5, var18, var18, var18, var18, var18, var18, var18, var18, var9, var8, var13, var12, 1163604833);
                  }
               }
            }
         }
      }
   }

   public int getCameraY() {
      return this.field1919 * -39987553;
   }

   public float[] project(float var1, float var2, float var3, float[] var4) {
      var1 -= this.field1926;
      var2 -= this.field1929;
      var3 -= this.field1928;
      float var5 = var1 * this.field1934 + var3 * this.field1930;
      float var6 = var3 * this.field1934 - var1 * this.field1930;
      float var7 = var2 * this.field1933 - var6 * this.field1931;
      float var8 = var6 * this.field1933 + var2 * this.field1931;
      var4[0] = var5;
      var4[1] = var7;
      var4[2] = var8;
      return var4;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method4106(int var0) {
      try {
         classPF.field5233.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "eu.af(" + ')');
      }
   }

   public float getYawSin() {
      return this.field1922;
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfj;III)V")
   @ObfuscatedName("ao")
   @Override
   void vmethod430(Scene var1, SceneTilePaint var2, int var3, int var4, int var5) {
      if (1 != classFH.field2255.field2215) {
         float var6;
         float var7 = var6 = (var4 << 7) - this.field1925 * -1862793759;
         float var8;
         float var9 = var8 = (var5 << 7) - this.field1918 * 706474989;
         float var10;
         float var11 = var10 = 128.0F + var7;
         float var12;
         float var13 = var12 = var9 + 128.0F;
         float var14 = var1.field2058[var3][var4][var5] - -39987553 * this.field1919;
         float var15 = var1.field2058[var3][var4 + 1][var5] - -39987553 * this.field1919;
         float var16 = var1.field2058[var3][1 + var4][1 + var5] - this.field1919 * -39987553;
         float var17 = var1.field2058[var3][var4][1 + var5] - this.field1919 * -39987553;
         float var18 = var9 * this.field1922 + var7 * this.field1923;
         var9 = var9 * this.field1923 - var7 * this.field1922;
         var18 = this.field1915 * var14 - var9 * this.field1920;
         var9 = var9 * this.field1915 + var14 * this.field1920;
         if (!(var9 < 50.0F)) {
            var18 = var8 * this.field1922 + this.field1923 * var11;
            var8 = this.field1923 * var8 - this.field1922 * var11;
            var18 = this.field1915 * var15 - this.field1920 * var8;
            var8 = this.field1920 * var15 + var8 * this.field1915;
            if (!(var8 < 50.0F)) {
               var18 = var13 * this.field1922 + var10 * this.field1923;
               var13 = var13 * this.field1923 - var10 * this.field1922;
               var18 = var16 * this.field1915 - this.field1920 * var13;
               var13 = this.field1915 * var13 + this.field1920 * var16;
               if (!(var13 < 50.0F)) {
                  var18 = var12 * this.field1922 + this.field1923 * var6;
                  var12 = this.field1923 * var12 - this.field1922 * var6;
                  var18 = var17 * this.field1915 - this.field1920 * var12;
                  var12 = var12 * this.field1915 + this.field1920 * var17;
                  if (!(var12 < 50.0F)) {
                     this.method5056(var1, var2, var4, var5, var18, var18, var18, var18, var18, var18, var18, var18, var9, var8, var13, var12, 1259911123);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   public static boolean method4107(int var0) {
      try {
         classZQ var1 = (classZQ)classZJ.field7187.method8208();
         if (null == var1) {
            if (var0 >= -1981470888) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            return true;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "eu.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIIII)V")
   @ObfuscatedName("dk")
   static void method4110(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      try {
         Deque2 var8 = var0.field1699[var1][var2][var3];
         if (null != var8) {
            if (var7 >= 1531627111) {
               throw new IllegalStateException();
            }

            for (TileItem var9 = (TileItem)var8.method7957(); var9 != null; var9 = (TileItem)var8.method7965()) {
               if (var7 >= 1531627111) {
                  throw new IllegalStateException();
               }

               if (var4 == -1370204663 * var9.field1549) {
                  if (var7 >= 1531627111) {
                     return;
                  }

                  if (var5 == var9.field1548 * 1413659045) {
                     if (var7 >= 1531627111) {
                        throw new IllegalStateException();
                     }

                     int var10001 = -513440211 * var6;
                     var9.method3423(-513440211 * var6 * 1413659045);
                     var9.field1548 = var10001;
                     break;
                  }
               }
            }

            classUA.method11300(var1, var2, var3, -79498757);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "eu.dk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfj;III)V")
   @ObfuscatedName("zj")
   @Override
   public void vmethod436(Scene var1, SceneTilePaint var2, int var3, int var4, int var5) {
      if (classFH.field2255.field2215 != 1) {
         int var6 = var4 + var1.field2146;
         int var7 = var5 + var1.field2146;
         float var8;
         float var9 = var8 = (var4 << 7) - this.field1925 * -1862793759;
         float var10;
         float var11 = var10 = (var5 << 7) - this.field1918 * 706474989;
         float var12;
         float var13 = var12 = var9 + 128.0F;
         float var14;
         float var15 = var14 = var11 + 128.0F;
         float var16 = var1.field2058[var3][var6][var7] - this.field1919 * -39987553;
         float var17 = var1.field2058[var3][var6 + 1][var7] - this.field1919 * -39987553;
         float var18 = var1.field2058[var3][var6 + 1][var7 + 1] - this.field1919 * -39987553;
         float var19 = var1.field2058[var3][var6][var7 + 1] - this.field1919 * -39987553;
         float var20 = var9 * this.field1923 + var11 * this.field1922;
         var11 = var11 * this.field1923 - var9 * this.field1922;
         var20 = var16 * this.field1915 - var11 * this.field1920;
         var11 = var11 * this.field1915 + var16 * this.field1920;
         if (var11 >= 50.0F) {
            var20 = var10 * this.field1922 + var13 * this.field1923;
            var10 = var10 * this.field1923 - var13 * this.field1922;
            var20 = var17 * this.field1915 - var10 * this.field1920;
            var10 = var10 * this.field1915 + var17 * this.field1920;
            if (var10 >= 50.0F) {
               var20 = var12 * this.field1923 + var15 * this.field1922;
               var15 = var15 * this.field1923 - var12 * this.field1922;
               var20 = var18 * this.field1915 - var15 * this.field1920;
               var15 = var18 * this.field1920 + var15 * this.field1915;
               if (var15 >= 50.0F) {
                  var20 = var8 * this.field1923 + var14 * this.field1922;
                  var14 = var14 * this.field1923 - var8 * this.field1922;
                  var20 = var19 * this.field1915 - var14 * this.field1920;
                  var14 = var19 * this.field1920 + var14 * this.field1915;
                  if (var14 >= 50.0F) {
                     if (client.field1117 != null) {
                        client.field1117.drawScenePaint(var1, var2, var3, var4, var5);
                     }

                     this.method5059(var1, var2, var4, var5, var20, var20, var20, var20, var20, var20, var20, var20, var11, var10, var15, var14);
                  }
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lez;Lfn;II)V")
   @ObfuscatedName("ag")
   @Override
   public void vmethod437(Scene var1, SceneTileModel var2, int var3, int var4) {
      this.vmethod431(var1, var2, var3, var4, 461027311);
   }
}
