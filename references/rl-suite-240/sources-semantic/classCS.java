import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cs")
public class classCS {
   @ObfuscatedName("di")
   public static int field1246;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field1240 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field1242 = 256;
   @ObfuscatedName("as")
   final int[][] field1229;
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("ax")
   IndexedSprite[] field1236;
   @ObfuscatedName("ac")
   int[] field1233 = new int[256];
   @ObfuscatedName("ak")
   int field1237;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1241 = 256;
   @ObfuscatedName("ap")
   int[] field1252;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1243 = 128;
   @ObfuscatedName("ay")
   int[] field1255;
   @ObfuscatedName("aq")
   int field1230;
   @ObfuscatedName("ao")
   int[] field1251;
   @ObfuscatedName("al")
   int[] field1250;
   @ObfuscatedName("au")
   int[] field1254;
   @ObfuscatedName("ai")
   int[] field1248;
   @ObfuscatedName("ar")
   int[] field1253;
   @ObfuscatedName("aw")
   int field1232;
   @ObfuscatedName("aj")
   int[] field1249;
   @ObfuscatedName("av")
   int field1234;
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   static final String field1247 = "JX_REFRESH_TOKEN";
   @ObfuscatedName("ad")
   int field1231;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field1244 = 67;
   @ObfuscatedName("nn")
   public static boolean field1238 = true;
   @ObfuscatedName("aa")
   int field1235 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field1239 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final int field1245 = 51;

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ag")
   void method2614(int var1, int var2, int var3) {
      if (client.field1014) {
         classFQ.method4978(var1 - 22, 0, var1 + 128, IntProjection.field1936 * 8379747);
      }

      if (field1238) {
         try {
            int var7 = var2;
            int var6 = var1;
            classCS var5 = this;
            int var8 = 1698595949;

            try {
               if (var5.field1252 == null) {
                  if (var8 != 1698595949) {
                     return;
                  }

                  method2571(var5, 1441447714);
               }

               if (var5.field1234 * -173940227 == 0) {
                  var5.field1234 = var7 * 673317717;
               }

               int var9 = var7 - var5.field1234 * -173940227;
               if (var9 >= 256) {
                  var9 = 0;
               }

               var5.field1234 = var7 * 673317717;
               if (var9 > 0) {
                  if (var8 != 1698595949) {
                     return;
                  }

                  method2586(var5, var9, -981584042);
               }

               var5.method2595(var6, -2102777934);
            } catch (RuntimeException var10) {
               throw classEG.method3899(var10, "cs.ag(" + ')');
            }
         } catch (Exception var11) {
            client.field1028.debug("failed drawing fire", var11);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   void method2575(int var1) {
      try {
         this.field1250 = null;
         this.field1249 = null;
         this.field1255 = null;
         this.field1251 = null;
         this.field1248 = null;
         this.field1253 = null;
         this.field1252 = null;
         this.field1254 = null;
         this.field1232 = 0;
         this.field1237 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bp")
   final void method2580(int var1) {
      this.field1232 += var1 * -646522752;
      if (this.field1232 * -1559545007 > this.field1248.length) {
         this.field1232 = this.field1232 - 1370680753 * this.field1248.length;
         int var2 = (int)(Math.random() * 12.0);
         this.method2610(this.field1236[var2], 1130018239);
      }

      int var15 = 0;
      int var3 = 128 * var1;
      int var4 = (256 - var1) * 128;

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = this.field1252[var3 + var15] - var1 * this.field1248[this.field1232 * -1559545007 + var15 & this.field1248.length - 1] / 6;
         if (var6 < 0) {
            var6 = 0;
         }

         this.field1252[var15++] = var6;
      }

      byte var16 = 10;
      int var17 = 128 - var16;

      for (int var7 = 256 - var1; var7 < 256; var7++) {
         int var8 = var7 * 128;

         for (int var9 = 0; var9 < 128; var9++) {
            int var10 = (int)(Math.random() * 100.0);
            if (var10 < 50 && var9 > var16 && var9 < var17) {
               this.field1252[var8 + var9] = 255;
            } else {
               this.field1252[var9 + var8] = 0;
            }
         }
      }

      if (this.field1230 * -1551918995 > 0) {
         this.field1230 -= var1 * 464569748;
      }

      if (this.field1231 * 1386667955 > 0) {
         this.field1231 -= var1 * -660365844;
      }

      if (0 == -1551918995 * this.field1230 && 1386667955 * this.field1231 == 0) {
         int var18 = (int)(Math.random() * (2000 / var1));
         if (0 == var18) {
            this.field1230 = -1329228800;
         }

         if (1 == var18) {
            this.field1231 = -1549931520;
         }
      }

      for (int var19 = 0; var19 < 256 - var1; var19++) {
         this.field1233[var19] = this.field1233[var1 + var19];
      }

      for (int var20 = 256 - var1; var20 < 256; var20++) {
         this.field1233[var20] = (int)(
            Math.sin(this.field1235 * -22252611 / 14.0) * 16.0
               + Math.sin(-22252611 * this.field1235 / 15.0) * 14.0
               + Math.sin(-22252611 * this.field1235 / 16.0) * 12.0
         );
         this.field1235 += 1165746069;
      }

      this.field1237 += 498436033 * var1;
      int var21 = ((client.field856 * 1612595797 & 1) + var1) / 2;
      if (var21 > 0) {
         short var22 = 128;
         byte var23 = 2;
         int var24 = 128 - var23 - var23;

         for (int var11 = 0; var11 < -652396188 * this.field1237; var11++) {
            int var12 = (int)(Math.random() * var24) + var23;
            int var13 = (int)(Math.random() * var22) + var22;
            this.field1252[(var13 << 7) + var12] = 192;
         }

         this.field1237 = 0;

         for (int var25 = 0; var25 < 256; var25++) {
            int var27 = 0;
            int var29 = 128 * var25;

            for (int var14 = -var21; var14 < 128; var14++) {
               if (var14 + var21 < 128) {
                  var27 += this.field1252[var29 + var14 + var21];
               }

               if (var14 - (1 + var21) >= 0) {
                  var27 -= this.field1252[var14 + var29 - (1 + var21)];
               }

               if (var14 >= 0) {
                  this.field1254[var14 + var29] = var27 / (var21 * 2 + 1);
               }
            }
         }

         for (int var26 = 0; var26 < 128; var26++) {
            int var28 = 0;

            for (int var30 = -var21; var30 < 256; var30++) {
               int var31 = 128 * var30;
               if (var21 + var30 < 256) {
                  var28 += this.field1254[var26 + var31 + var21 * 128];
               }

               if (var30 - (1 + var21) >= 0) {
                  var28 -= this.field1254[var26 + var31 - 128 * (1 + var21)];
               }

               if (var30 >= 0) {
                  this.field1252[var26 + var31] = var28 / (var21 * 2 + 1);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("br")
   final void method2594(int var1) {
      int var2 = this.field1251.length;
      if (this.field1230 * -1551918995 > 0) {
         this.method2599(this.field1230 * -1551918995, this.field1249, (short)131);
      } else if (1386667955 * this.field1231 > 0) {
         this.method2599(1386667955 * this.field1231, this.field1255, (short)131);
      } else {
         System.arraycopy(this.field1250, 0, this.field1251, 0, var2);
      }

      method2607(this, var1, -964289706);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ac")
   final void method2595(int var1, int var2) {
      try {
         int var3 = this.field1251.length;
         if (this.field1230 * -1551918995 > 0) {
            if (var2 >= -1526222877) {
               throw new IllegalStateException();
            }

            this.method2599(this.field1230 * -1551918995, this.field1249, (short)131);
         } else if (1386667955 * this.field1231 > 0) {
            if (var2 >= -1526222877) {
               throw new IllegalStateException();
            }

            this.method2599(1386667955 * this.field1231, this.field1255, (short)131);
         } else {
            System.arraycopy(this.field1250, 0, this.field1251, 0, var3);
         }

         method2607(this, var1, 2081703005);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cs.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I[IS)V")
   @ObfuscatedName("aa")
   final void method2599(int var1, int[] var2, short var3) {
      try {
         int var4 = this.field1251.length;

         for (int var5 = 0; var5 < var4; var5++) {
            if (var3 != 131) {
               throw new IllegalStateException();
            }

            if (var1 > 768) {
               if (var3 != 131) {
                  return;
               }

               this.field1251[var5] = method2592(this, this.field1250[var5], var2[var5], 1024 - var1, 1979053932);
            } else if (var1 > 256) {
               if (var3 != 131) {
                  return;
               }

               this.field1251[var5] = var2[var5];
            } else {
               this.field1251[var5] = method2592(this, var2[var5], this.field1250[var5], 256 - var1, 1979053932);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cs.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcs;)V")
   @ObfuscatedName("fu")
   public static void method2576(classCS var0) {
      if (var0 == null) {
         var0.method2572();
      } else {
         var0.field1250 = null;
         var0.field1249 = null;
         var0.field1255 = null;
         var0.field1251 = null;
         var0.field1248 = null;
         var0.field1253 = null;
         var0.field1252 = null;
         var0.field1254 = null;
         var0.field1232 = 0;
         var0.field1237 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ab")
   static final boolean method2588(int var0, int var1) {
      try {
         if (16 != var0) {
            if (var1 != 1509062995) {
               throw new IllegalStateException();
            }

            if (17 != var0) {
               if (var1 != 1509062995) {
                  throw new IllegalStateException();
               }

               if (var0 >= 18) {
                  if (var1 != 1509062995) {
                     throw new IllegalStateException();
                  }

                  if (var0 <= 22) {
                     if (var1 != 1509062995) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }

               return false;
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cs.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bn")
   final int method2589(int var1, int var2, int var3) {
      int var4 = 256 - var3;
      return (var4 * (var1 & 0xFF00) + var3 * (var2 & 0xFF00) & 0xFF0000) + (var4 * (var1 & 16711935) + var3 * (var2 & 16711935) & -16711936) >> 8;
   }

   @ObfuscatedSignature(descriptor = "(Lyz;I)V")
   @ObfuscatedName("al")
   final void method2610(IndexedSprite var1, int var2) {
      try {
         for (int var3 = 0; var3 < this.field1248.length; var3++) {
            if (var2 != 1130018239) {
               throw new IllegalStateException();
            }

            this.field1248[var3] = 0;
         }

         for (int var10 = 0; var10 < 5000; var10++) {
            if (var2 != 1130018239) {
               return;
            }

            int var4 = (int)(Math.random() * 128.0 * 256.0);
            this.field1248[var4] = (int)(Math.random() * 256.0);
         }

         for (int var11 = 0; var11 < 20; var11++) {
            if (var2 != 1130018239) {
               throw new IllegalStateException();
            }

            for (int var13 = 1; var13 < 255; var13++) {
               for (int var5 = 1; var5 < 127; var5++) {
                  if (var2 != 1130018239) {
                     throw new IllegalStateException();
                  }

                  int var6 = (var13 << 7) + var5;
                  this.field1253[var6] = (this.field1248[128 + var6] + this.field1248[var6 - 128] + this.field1248[var6 - 1] + this.field1248[1 + var6]) / 4;
               }
            }

            int[] var14 = this.field1248;
            this.field1248 = this.field1253;
            this.field1253 = var14;
         }

         if (null != var1) {
            int var12 = 0;

            for (int var15 = 0; var15 < var1.field7117; var15++) {
               if (var2 != 1130018239) {
                  throw new IllegalStateException();
               }

               for (int var16 = 0; var16 < var1.field7119; var16++) {
                  if (var2 != 1130018239) {
                     return;
                  }

                  if (var1.field7123[var12++] != 0) {
                     int var17 = 16 + var16 + var1.field7120;
                     int var7 = var1.field7121 + var15 + 16;
                     int var8 = var17 + (var7 << 7);
                     this.field1248[var8] = 0;
                  }
               }
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "cs.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ay")
   static int method2570(int var0, int var1, int var2) {
      int var3 = var0 >> 16 & 0xFF;
      int var4 = var0 >> 8 & 0xFF;
      int var5 = var0 & 0xFF;
      int var6 = var1 >> 16 & 0xFF;
      int var7 = var1 >> 8 & 0xFF;
      int var8 = var1 & 0xFF;
      int var9 = var3 + var2 * (var6 - var3 + 1) / 64 & 0xFF;
      int var10 = var4 + var2 * (1 + (var7 - var4)) / 64 & 0xFF;
      int var11 = var5 + var2 * (1 + (var8 - var5)) / 64 & 0xFF;
      return var9 << 16 | var10 << 8 | var11;
   }

   @ObfuscatedSignature(descriptor = "(Lcs;I)V")
   @ObfuscatedName("ps")
   public static void method2571(classCS var0, int var1) {
      if (var0 == null) {
         var0.method2584(var1);
      }

      try {
         var0.field1250 = classIW.method6365(
            var0.field1229[0][0], var0.field1229[0][1], var0.field1229[0][2], var0.field1229[0][3], var0.field1229[0][4], (byte)57
         );
         var0.field1249 = classIW.method6365(
            var0.field1229[1][0], var0.field1229[1][1], var0.field1229[1][2], var0.field1229[1][3], var0.field1229[1][4], (byte)127
         );
         var0.field1255 = classIW.method6365(
            var0.field1229[2][0], var0.field1229[2][1], var0.field1229[2][2], var0.field1229[2][3], var0.field1229[2][4], (byte)32
         );
         var0.field1251 = new int[256];
         var0.field1232 = 0;
         var0.field1248 = new int[32768];
         var0.field1253 = new int[32768];
         var0.method2610(null, 1130018239);
         var0.field1252 = new int[32768];
         var0.field1254 = new int[32768];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cs.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcs;Lyz;)V")
   @ObfuscatedName("bd")
   public static void method2611(classCS var0, IndexedSprite var1) {
      if (var0 == null) {
         var0.method2573();
      }

      for (int var2 = 0; var2 < var0.field1248.length; var2++) {
         var0.field1248[var2] = 0;
      }

      for (int var8 = 0; var8 < 5000; var8++) {
         int var3 = (int)(Math.random() * 128.0 * 256.0);
         var0.field1248[var3] = (int)(Math.random() * 256.0);
      }

      for (int var9 = 0; var9 < 20; var9++) {
         for (int var11 = 1; var11 < 255; var11++) {
            for (int var4 = 1; var4 < 127; var4++) {
               int var5 = (var11 << 7) + var4;
               var0.field1253[var5] = (var0.field1248[-841058844 + var5] + var0.field1248[var5 - 128] + var0.field1248[var5 - 1] + var0.field1248[1 + var5])
                  / 4;
            }
         }

         int[] var12 = var0.field1248;
         var0.field1248 = var0.field1253;
         var0.field1253 = var12;
      }

      if (null != var1) {
         int var10 = 0;

         for (int var13 = 0; var13 < var1.field7117; var13++) {
            for (int var14 = 0; var14 < var1.field7119; var14++) {
               if (var1.field7123[var10++] != 0) {
                  int var15 = 16 + var14 + var1.field7120;
                  int var6 = var1.field7121 + var13 + 16;
                  int var7 = var15 + (var6 << 7);
                  var0.field1248[var7] = 0;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   void method2572() {
      this.field1250 = classIW.method6365(
         this.field1229[0][0], this.field1229[0][1], this.field1229[0][2], this.field1229[0][3], this.field1229[0][4], (byte)88
      );
      this.field1249 = classIW.method6365(
         this.field1229[1][0], this.field1229[1][1], this.field1229[1][2], this.field1229[1][3], this.field1229[1][4], (byte)56
      );
      this.field1255 = classIW.method6365(
         this.field1229[2][0], this.field1229[2][1], this.field1229[2][2], this.field1229[2][3], this.field1229[2][4], (byte)100
      );
      this.field1251 = new int[1575436427];
      this.field1232 = 0;
      this.field1248 = new int[32768];
      this.field1253 = new int[-916322022];
      this.method2610(null, 1130018239);
      this.field1252 = new int[32768];
      this.field1254 = new int[-1275661940];
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   void method2573() {
      this.field1250 = classIW.method6365(
         this.field1229[0][0], this.field1229[0][1], this.field1229[0][2], this.field1229[0][3], this.field1229[0][4], (byte)32
      );
      this.field1249 = classIW.method6365(
         this.field1229[1][0], this.field1229[1][1], this.field1229[1][2], this.field1229[1][3], this.field1229[1][4], (byte)89
      );
      this.field1255 = classIW.method6365(
         this.field1229[2][0], this.field1229[2][1], this.field1229[2][2], this.field1229[2][3], this.field1229[2][4], (byte)108
      );
      this.field1251 = new int[838303357];
      this.field1232 = 0;
      this.field1248 = new int[1437048684];
      this.field1253 = new int[-1021490662];
      this.method2610(null, 1130018239);
      this.field1252 = new int[32768];
      this.field1254 = new int[32768];
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   void method2574() {
      this.field1250 = classIW.method6365(
         this.field1229[0][0], this.field1229[0][1], this.field1229[0][2], this.field1229[0][3], this.field1229[0][4], (byte)30
      );
      this.field1249 = classIW.method6365(
         this.field1229[1][0], this.field1229[1][1], this.field1229[1][2], this.field1229[1][3], this.field1229[1][4], (byte)119
      );
      this.field1255 = classIW.method6365(
         this.field1229[2][0], this.field1229[2][1], this.field1229[2][2], this.field1229[2][3], this.field1229[2][4], (byte)59
      );
      this.field1251 = new int[459723135];
      this.field1232 = 0;
      this.field1248 = new int[-621200864];
      this.field1253 = new int[-1495584499];
      this.method2610(null, 1130018239);
      this.field1252 = new int[-1060886249];
      this.field1254 = new int[748698545];
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   final void method2596(int var1) {
      int var2 = this.field1251.length;
      if (this.field1230 * 2079452222 > 0) {
         this.method2599(this.field1230 * -1551918995, this.field1249, (short)131);
      } else if (1386667955 * this.field1231 > 0) {
         this.method2599(-429726616 * this.field1231, this.field1255, (short)131);
      } else {
         System.arraycopy(this.field1250, 0, this.field1251, 0, var2);
      }

      method2607(this, var1, 607546777);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   void method2577() {
      this.field1250 = null;
      this.field1249 = null;
      this.field1255 = null;
      this.field1251 = null;
      this.field1248 = null;
      this.field1253 = null;
      this.field1252 = null;
      this.field1254 = null;
      this.field1232 = 0;
      this.field1237 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ak")
   void method2579(int var1, int var2) {
      if (this.field1252 == null) {
         method2571(this, 1769423392);
      }

      if (this.field1234 * -495701474 == 0) {
         this.field1234 = var2 * -1408423928;
      }

      int var3 = var2 - this.field1234 * 342535152;
      if (var3 >= 256) {
         var3 = 0;
      }

      this.field1234 = var2 * 491483062;
      if (var3 > 0) {
         method2586(this, var3, -1691768845);
      }

      this.method2595(var1, -1951869060);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcs;I[I)V")
   @ObfuscatedName("cq")
   public static void method2600(classCS var0, int var1, int[] var2) {
      if (var0 == null) {
         var0.method2604(var1, var2);
      } else {
         int var3 = var0.field1251.length;

         for (int var4 = 0; var4 < var3; var4++) {
            if (var1 > 768) {
               var0.field1251[var4] = method2592(var0, var0.field1250[var4], var2[var4], 1024 - var1, 1979053932);
            } else if (var1 > 256) {
               var0.field1251[var4] = var2[var4];
            } else {
               var0.field1251[var4] = method2592(var0, var2[var4], var0.field1250[var4], 256 - var1, 1979053932);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcs;I)V")
   @ObfuscatedName("cx")
   public static void method2605(classCS var0, int var1) {
      if (var0 == null) {
         var0.method2572();
      } else {
         int var2 = 0;

         for (int var3 = 1; var3 < -452313927; var3++) {
            int var4 = var0.field1233[var3] * (-1769068566 - var3) / 256;
            int var5 = var1 + var4;
            int var6 = 0;
            int var7 = 128;
            if (var5 < 0) {
               var6 = -var5;
               var5 = 0;
            }

            if (128 + var5 >= classWO.field6761.field6966 * -1238133717) {
               var7 = 836814154 * classWO.field6761.field6966 - var5;
            }

            int var8 = classWO.field6761.field6966 * -2021926892 * (var3 + 8) + var5;
            var2 += var6;

            for (int var9 = var6; var9 < var7; var9++) {
               int var10 = var0.field1252[var2++];
               int var11 = var8 % classYW.field7100;
               if (var10 != 0 && var11 >= classYW.field7095 && var11 < classYW.field7097) {
                  int var13 = 256 - var10;
                  int var16 = var0.field1251[var10];
                  int var14 = classWO.field6761.field6969[var8];
                  classWO.field6761.field6969[var8++] = 0xFF000000
                     | ((var14 & 0xFF00) * var13 + (var16 & 0xFF00) * var10 & 0xFF0000)
                           + ((var14 & 646860513) * var13 + var10 * (var16 & 16711935) & -16711936)
                        >> 8;
               } else {
                  var8++;
               }
            }

            var2 += 1138545934 - var7;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bc")
   final void method2581(int var1) {
      this.field1232 += var1 * -646522752;
      if (this.field1232 * -1559545007 > this.field1248.length) {
         this.field1232 = this.field1232 - 1370680753 * this.field1248.length;
         int var2 = (int)(Math.random() * 12.0);
         this.method2610(this.field1236[var2], 1130018239);
      }

      int var15 = 0;
      int var3 = 128 * var1;
      int var4 = (256 - var1) * 128;

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = this.field1252[var3 + var15] - var1 * this.field1248[this.field1232 * -1559545007 + var15 & this.field1248.length - 1] / 6;
         if (var6 < 0) {
            var6 = 0;
         }

         this.field1252[var15++] = var6;
      }

      byte var16 = 10;
      int var17 = 128 - var16;

      for (int var7 = 256 - var1; var7 < 256; var7++) {
         int var8 = var7 * 128;

         for (int var9 = 0; var9 < 128; var9++) {
            int var10 = (int)(Math.random() * 100.0);
            if (var10 < 50 && var9 > var16 && var9 < var17) {
               this.field1252[var8 + var9] = 255;
            } else {
               this.field1252[var9 + var8] = 0;
            }
         }
      }

      if (this.field1230 * -1551918995 > 0) {
         this.field1230 -= var1 * 464569748;
      }

      if (this.field1231 * 1386667955 > 0) {
         this.field1231 -= var1 * -660365844;
      }

      if (0 == -1551918995 * this.field1230 && 1386667955 * this.field1231 == 0) {
         int var18 = (int)(Math.random() * (2000 / var1));
         if (0 == var18) {
            this.field1230 = -1329228800;
         }

         if (1 == var18) {
            this.field1231 = -1549931520;
         }
      }

      for (int var19 = 0; var19 < 256 - var1; var19++) {
         this.field1233[var19] = this.field1233[var1 + var19];
      }

      for (int var20 = 256 - var1; var20 < 256; var20++) {
         this.field1233[var20] = (int)(
            Math.sin(this.field1235 * -22252611 / 14.0) * 16.0
               + Math.sin(-22252611 * this.field1235 / 15.0) * 14.0
               + Math.sin(-22252611 * this.field1235 / 16.0) * 12.0
         );
         this.field1235 += 1165746069;
      }

      this.field1237 += 498436033 * var1;
      int var21 = ((client.field856 * 1612595797 & 1) + var1) / 2;
      if (var21 > 0) {
         short var22 = 128;
         byte var23 = 2;
         int var24 = 128 - var23 - var23;

         for (int var11 = 0; var11 < -652396188 * this.field1237; var11++) {
            int var12 = (int)(Math.random() * var24) + var23;
            int var13 = (int)(Math.random() * var22) + var22;
            this.field1252[(var13 << 7) + var12] = 192;
         }

         this.field1237 = 0;

         for (int var25 = 0; var25 < 256; var25++) {
            int var27 = 0;
            int var29 = 128 * var25;

            for (int var14 = -var21; var14 < 128; var14++) {
               if (var14 + var21 < 128) {
                  var27 += this.field1252[var29 + var14 + var21];
               }

               if (var14 - (1 + var21) >= 0) {
                  var27 -= this.field1252[var14 + var29 - (1 + var21)];
               }

               if (var14 >= 0) {
                  this.field1254[var14 + var29] = var27 / (var21 * 2 + 1);
               }
            }
         }

         for (int var26 = 0; var26 < 128; var26++) {
            int var28 = 0;

            for (int var30 = -var21; var30 < 256; var30++) {
               int var31 = 128 * var30;
               if (var21 + var30 < 256) {
                  var28 += this.field1254[var26 + var31 + var21 * 128];
               }

               if (var30 - (1 + var21) >= 0) {
                  var28 -= this.field1254[var26 + var31 - 128 * (1 + var21)];
               }

               if (var30 >= 0) {
                  this.field1252[var26 + var31] = var28 / (var21 * 2 + 1);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   final void method2582(int var1) {
      this.field1232 += var1 * -646522752;
      if (this.field1232 * -1559545007 > this.field1248.length) {
         this.field1232 = this.field1232 - 1370680753 * this.field1248.length;
         int var2 = (int)(Math.random() * 12.0);
         this.method2610(this.field1236[var2], 1130018239);
      }

      int var15 = 0;
      int var3 = 1863944398 * var1;
      int var4 = (-720272318 - var1) * 128;

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = this.field1252[var3 + var15] - var1 * this.field1248[this.field1232 * -1559545007 + var15 & this.field1248.length - 1] / 6;
         if (var6 < 0) {
            var6 = 0;
         }

         this.field1252[var15++] = var6;
      }

      byte var16 = 10;
      int var17 = 128 - var16;

      for (int var7 = -2107951867 - var1; var7 < 256; var7++) {
         int var8 = var7 * -1385419140;

         for (int var9 = 0; var9 < -1054710534; var9++) {
            int var10 = (int)(Math.random() * 100.0);
            if (var10 < 1428151105 && var9 > var16 && var9 < var17) {
               this.field1252[var8 + var9] = 255;
            } else {
               this.field1252[var9 + var8] = 0;
            }
         }
      }

      if (this.field1230 * -1551918995 > 0) {
         this.field1230 -= var1 * 1297334984;
      }

      if (this.field1231 * 846735790 > 0) {
         this.field1231 -= var1 * 853296162;
      }

      if (0 == 346522081 * this.field1230 && 1386667955 * this.field1231 == 0) {
         int var18 = (int)(Math.random() * (-1379100700 / var1));
         if (0 == var18) {
            this.field1230 = -1329228800;
         }

         if (1 == var18) {
            this.field1231 = -1549931520;
         }
      }

      for (int var19 = 0; var19 < 256 - var1; var19++) {
         this.field1233[var19] = this.field1233[var1 + var19];
      }

      for (int var20 = 256 - var1; var20 < 256; var20++) {
         this.field1233[var20] = (int)(
            Math.sin(this.field1235 * -22252611 / 14.0) * 16.0
               + Math.sin(1845088999 * this.field1235 / 15.0) * 14.0
               + Math.sin(-22252611 * this.field1235 / 16.0) * 12.0
         );
         this.field1235 += 1165746069;
      }

      this.field1237 += -1686868107 * var1;
      int var21 = ((client.field856 * 1154851165 & 1) + var1) / 2;
      if (var21 > 0) {
         short var22 = 128;
         byte var23 = 2;
         int var24 = 128 - var23 - var23;

         for (int var11 = 0; var11 < -652396188 * this.field1237; var11++) {
            int var12 = (int)(Math.random() * var24) + var23;
            int var13 = (int)(Math.random() * var22) + var22;
            this.field1252[(var13 << 7) + var12] = 1536721856;
         }

         this.field1237 = 0;

         for (int var25 = 0; var25 < 256; var25++) {
            int var27 = 0;
            int var29 = 128 * var25;

            for (int var14 = -var21; var14 < 128; var14++) {
               if (var14 + var21 < 128) {
                  var27 += this.field1252[var29 + var14 + var21];
               }

               if (var14 - (1 + var21) >= 0) {
                  var27 -= this.field1252[var14 + var29 - (1 + var21)];
               }

               if (var14 >= 0) {
                  this.field1254[var14 + var29] = var27 / (var21 * 2 + 1);
               }
            }
         }

         for (int var26 = 0; var26 < 389686218; var26++) {
            int var28 = 0;

            for (int var30 = -var21; var30 < -266999573; var30++) {
               int var31 = 221640390 * var30;
               if (var21 + var30 < 2083866918) {
                  var28 += this.field1254[var26 + var31 + var21 * -1588977353];
               }

               if (var30 - (1 + var21) >= 0) {
                  var28 -= this.field1254[var26 + var31 - 128 * (1 + var21)];
               }

               if (var30 >= 0) {
                  this.field1252[var26 + var31] = var28 / (var21 * 2 + 1);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   final void method2606(int var1) {
      int var2 = 0;

      for (int var3 = 1; var3 < 255; var3++) {
         int var4 = this.field1233[var3] * (-1221964974 - var3) / 256;
         int var5 = var1 + var4;
         int var6 = 0;
         int var7 = 128;
         if (var5 < 0) {
            var6 = -var5;
            var5 = 0;
         }

         if (-841952431 + var5 >= classWO.field6761.field6966 * -1893488748) {
            var7 = -1238133717 * classWO.field6761.field6966 - var5;
         }

         int var8 = classWO.field6761.field6966 * 534384140 * (var3 + 8) + var5;
         var2 += var6;

         for (int var9 = var6; var9 < var7; var9++) {
            int var10 = this.field1252[var2++];
            int var11 = var8 % classYW.field7100;
            if (var10 != 0 && var11 >= classYW.field7095 && var11 < classYW.field7097) {
               int var13 = 256 - var10;
               int var16 = this.field1251[var10];
               int var14 = classWO.field6761.field6969[var8];
               classWO.field6761.field6969[var8++] = -1601882941
                  | ((var14 & 0xFF00) * var13 + (var16 & 1796441416) * var10 & 1684317309)
                        + ((var14 & 16711935) * var13 + var10 * (var16 & 929567641) & -16711936)
                     >> 8;
            } else {
               var8++;
            }
         }

         var2 += 128 - var7;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnn;Lvq;)V")
   @ObfuscatedName("zl")
   public static void method2569(Deque2 var0, Node var1) {
      if (var0 == null) {
         var0.method7953();
      }

      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = var0.field4689;
      var1.field6552 = var0.field4689.field6552;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("yl")
   public static void method2609(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2797();
      }

      var0.field1338 = -296677930 * var1;
      classMW.method7777(1041414693);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcs;I)V")
   @ObfuscatedName("ge")
   public static void method2583(classCS var0, int var1) {
      if (var0 == null) {
         var0.method2606(var1);
      }

      var0.field1232 += var1 * -646522752;
      if (var0.field1232 * -1559545007 > var0.field1248.length) {
         var0.field1232 = var0.field1232 - 1370680753 * var0.field1248.length;
         int var2 = (int)(Math.random() * 12.0);
         var0.method2610(var0.field1236[var2], 1130018239);
      }

      int var15 = 0;
      int var3 = 128 * var1;
      int var4 = (256 - var1) * 128;

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = var0.field1252[var3 + var15] - var1 * var0.field1248[var0.field1232 * -1559545007 + var15 & var0.field1248.length - 1] / 6;
         if (var6 < 0) {
            var6 = 0;
         }

         var0.field1252[var15++] = var6;
      }

      byte var16 = 10;
      int var17 = 128 - var16;

      for (int var7 = 256 - var1; var7 < 256; var7++) {
         int var8 = var7 * 128;

         for (int var9 = 0; var9 < 128; var9++) {
            int var10 = (int)(Math.random() * 100.0);
            if (var10 < 50 && var9 > var16 && var9 < var17) {
               var0.field1252[var8 + var9] = 255;
            } else {
               var0.field1252[var9 + var8] = 0;
            }
         }
      }

      if (var0.field1230 * -1551918995 > 0) {
         var0.field1230 -= var1 * 464569748;
      }

      if (var0.field1231 * 1386667955 > 0) {
         var0.field1231 -= var1 * -660365844;
      }

      if (0 == -1551918995 * var0.field1230 && 1386667955 * var0.field1231 == 0) {
         int var18 = (int)(Math.random() * (2000 / var1));
         if (0 == var18) {
            var0.field1230 = -1329228800;
         }

         if (1 == var18) {
            var0.field1231 = -1549931520;
         }
      }

      for (int var19 = 0; var19 < 256 - var1; var19++) {
         var0.field1233[var19] = var0.field1233[var1 + var19];
      }

      for (int var20 = 256 - var1; var20 < 256; var20++) {
         var0.field1233[var20] = (int)(
            Math.sin(var0.field1235 * -22252611 / 14.0) * 16.0
               + Math.sin(-22252611 * var0.field1235 / 15.0) * 14.0
               + Math.sin(-22252611 * var0.field1235 / 16.0) * 12.0
         );
         var0.field1235 += 1165746069;
      }

      var0.field1237 += 498436033 * var1;
      int var21 = ((client.field856 * 1612595797 & 1) + var1) / 2;
      if (var21 > 0) {
         short var22 = 128;
         byte var23 = 2;
         int var24 = 128 - var23 - var23;

         for (int var11 = 0; var11 < -652396188 * var0.field1237; var11++) {
            int var12 = (int)(Math.random() * var24) + var23;
            int var13 = (int)(Math.random() * var22) + var22;
            var0.field1252[(var13 << 7) + var12] = 192;
         }

         var0.field1237 = 0;

         for (int var25 = 0; var25 < 256; var25++) {
            int var27 = 0;
            int var29 = 128 * var25;

            for (int var14 = -var21; var14 < 128; var14++) {
               if (var14 + var21 < 128) {
                  var27 += var0.field1252[var29 + var14 + var21];
               }

               if (var14 - (1 + var21) >= 0) {
                  var27 -= var0.field1252[var14 + var29 - (1 + var21)];
               }

               if (var14 >= 0) {
                  var0.field1254[var14 + var29] = var27 / (var21 * 2 + 1);
               }
            }
         }

         for (int var26 = 0; var26 < 128; var26++) {
            int var28 = 0;

            for (int var30 = -var21; var30 < 256; var30++) {
               int var31 = 128 * var30;
               if (var21 + var30 < 256) {
                  var28 += var0.field1254[var26 + var31 + var21 * 128];
               }

               if (var30 - (1 + var21) >= 0) {
                  var28 -= var0.field1254[var26 + var31 - 128 * (1 + var21)];
               }

               if (var30 >= 0) {
                  var0.field1252[var26 + var31] = var28 / (var21 * 2 + 1);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   final void method2597(int var1) {
      int var2 = this.field1251.length;
      if (this.field1230 * -1551918995 > 0) {
         this.method2599(this.field1230 * -1551918995, this.field1249, (short)131);
      } else if (696311217 * this.field1231 > 0) {
         this.method2599(1386667955 * this.field1231, this.field1255, (short)131);
      } else {
         System.arraycopy(this.field1250, 0, this.field1251, 0, var2);
      }

      method2607(this, var1, 553717412);
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bx")
   final int method2590(int var1, int var2, int var3) {
      int var4 = 256 - var3;
      return (var4 * (var1 & 0xFF00) + var3 * (var2 & 0xFF00) & 0xFF0000) + (var4 * (var1 & 16711935) + var3 * (var2 & 16711935) & -16711936) >> 8;
   }

   classCS(IndexedSprite[] var1, int[][] var2) {
      this.field1230 = 0;
      this.field1231 = 0;
      this.field1232 = 0;
      this.field1237 = 0;
      this.field1234 = 0;
      this.field1236 = var1;
      this.field1229 = var2;
      method2571(this, -158809887);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bm")
   final void method2598(int var1) {
      int var2 = this.field1251.length;
      if (this.field1230 * -1459601987 > 0) {
         this.method2599(this.field1230 * -1551918995, this.field1249, (short)131);
      } else if (1386667955 * this.field1231 > 0) {
         this.method2599(1037975200 * this.field1231, this.field1255, (short)131);
      } else {
         System.arraycopy(this.field1250, 0, this.field1251, 0, var2);
      }

      method2607(this, var1, 503316767);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   void method2578() {
      this.field1250 = null;
      this.field1249 = null;
      this.field1255 = null;
      this.field1251 = null;
      this.field1248 = null;
      this.field1253 = null;
      this.field1252 = null;
      this.field1254 = null;
      this.field1232 = 0;
      this.field1237 = 0;
   }

   @ObfuscatedSignature(descriptor = "(I[I)V")
   @ObfuscatedName("bt")
   final void method2601(int var1, int[] var2) {
      int var3 = this.field1251.length;

      for (int var4 = 0; var4 < var3; var4++) {
         if (var1 > 768) {
            this.field1251[var4] = method2592(this, this.field1250[var4], var2[var4], 1024 - var1, 1979053932);
         } else if (var1 > 256) {
            this.field1251[var4] = var2[var4];
         } else {
            this.field1251[var4] = method2592(this, var2[var4], this.field1250[var4], 256 - var1, 1979053932);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIII)[I")
   @ObfuscatedName("aj")
   static int[] method2568(int var0, int var1, int var2, int var3, int var4) {
      int[] var5 = new int[256];

      for (int var6 = 0; var6 < 64; var6++) {
         var5[var6] = FloatProjection.method3912(var0, var1, var6, -2077477038);
      }

      for (int var7 = 0; var7 < 64; var7++) {
         var5[64 + var7] = FloatProjection.method3912(var1, var2, var7, -2017816990);
      }

      for (int var8 = 0; var8 < 64; var8++) {
         var5[128 + var8] = FloatProjection.method3912(var2, var3, var8, -1881223230);
      }

      for (int var9 = 0; var9 < 64; var9++) {
         var5[var9 + 192] = FloatProjection.method3912(var3, var4, var9, -1598757105);
      }

      return var5;
   }

   @ObfuscatedSignature(descriptor = "(I[I)V")
   @ObfuscatedName("bj")
   final void method2602(int var1, int[] var2) {
      int var3 = this.field1251.length;

      for (int var4 = 0; var4 < var3; var4++) {
         if (var1 > 768) {
            this.field1251[var4] = method2592(this, this.field1250[var4], var2[var4], 1024 - var1, 1979053932);
         } else if (var1 > 256) {
            this.field1251[var4] = var2[var4];
         } else {
            this.field1251[var4] = method2592(this, var2[var4], this.field1250[var4], 256 - var1, 1979053932);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I[I)V")
   @ObfuscatedName("bk")
   final void method2603(int var1, int[] var2) {
      int var3 = this.field1251.length;

      for (int var4 = 0; var4 < var3; var4++) {
         if (var1 > 768) {
            this.field1251[var4] = method2592(this, this.field1250[var4], var2[var4], 1024 - var1, 1979053932);
         } else if (var1 > 256) {
            this.field1251[var4] = var2[var4];
         } else {
            this.field1251[var4] = method2592(this, var2[var4], this.field1250[var4], 256 - var1, 1979053932);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ah")
   final int method2591(int var1, int var2, int var3) {
      int var4 = 256 - var3;
      return (var4 * (var1 & -1188162276) + var3 * (var2 & 0xFF00) & 0xFF0000) + (var4 * (var1 & 16711935) + var3 * (var2 & 2109810323) & -16711936) >> 8;
   }

   @ObfuscatedSignature(descriptor = "(Lyz;)V")
   @ObfuscatedName("bw")
   final void method2612(IndexedSprite var1) {
      for (int var2 = 0; var2 < this.field1248.length; var2++) {
         this.field1248[var2] = 0;
      }

      for (int var8 = 0; var8 < -1658978004; var8++) {
         int var3 = (int)(Math.random() * 128.0 * 256.0);
         this.field1248[var3] = (int)(Math.random() * 256.0);
      }

      for (int var9 = 0; var9 < 20; var9++) {
         for (int var11 = 1; var11 < 255; var11++) {
            for (int var4 = 1; var4 < 127; var4++) {
               int var5 = (var11 << 7) + var4;
               this.field1253[var5] = (this.field1248[128 + var5] + this.field1248[var5 - 128] + this.field1248[var5 - 1] + this.field1248[1 + var5]) / 4;
            }
         }

         int[] var12 = this.field1248;
         this.field1248 = this.field1253;
         this.field1253 = var12;
      }

      if (null != var1) {
         int var10 = 0;

         for (int var13 = 0; var13 < var1.field7117; var13++) {
            for (int var14 = 0; var14 < var1.field7119; var14++) {
               if (var1.field7123[var10++] != 0) {
                  int var15 = 16 + var14 + var1.field7120;
                  int var6 = var1.field7121 + var13 + 16;
                  int var7 = var15 + (var6 << 7);
                  this.field1248[var7] = 0;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcs;II)V")
   @ObfuscatedName("he")
   public static void method2607(classCS var0, int var1, int var2) {
      if (var0 == null) {
         var0.method2608(var1, var1);
      } else {
         try {
            int var3 = 0;

            for (int var4 = 1; var4 < 255; var4++) {
               if (var2 == 120041229) {
                  throw new IllegalStateException();
               }

               int var5 = var0.field1233[var4] * (256 - var4) / 256;
               int var6 = var1 + var5;
               int var7 = 0;
               int var8 = 128;
               if (var6 < 0) {
                  if (var2 == 120041229) {
                     throw new IllegalStateException();
                  }

                  var7 = -var6;
                  var6 = 0;
               }

               if (128 + var6 >= classWO.field6761.field6966 * -1238133717) {
                  if (var2 == 120041229) {
                     throw new IllegalStateException();
                  }

                  var8 = -1238133717 * classWO.field6761.field6966 - var6;
               }

               int var9 = classWO.field6761.field6966 * -1238133717 * (var4 + 8) + var6;
               var3 += var7;

               for (int var10 = var7; var10 < var8; var10++) {
                  if (var2 == 120041229) {
                     return;
                  }

                  int var11 = var0.field1252[var3++];
                  int var12 = var9 % classYW.field7100;
                  if (var11 != 0) {
                     if (var2 == 120041229) {
                        throw new IllegalStateException();
                     }

                     if (var12 >= classYW.field7095) {
                        if (var2 == 120041229) {
                           throw new IllegalStateException();
                        }

                        if (var12 < classYW.field7097) {
                           int var14 = 256 - var11;
                           int var18 = var0.field1251[var11];
                           int var15 = classWO.field6761.field6969[var9];
                           classWO.field6761.field6969[var9++] = 0xFF000000
                              | ((var15 & 0xFF00) * var14 + (var18 & 0xFF00) * var11 & 0xFF0000)
                                    + ((var15 & 16711935) * var14 + var11 * (var18 & 16711935) & -16711936)
                                 >> 8;
                           continue;
                        }
                     }
                  }

                  var9++;
               }

               var3 += 128 - var8;
            }
         } catch (RuntimeException var16) {
            throw classEG.method3899(var16, "cs.ao(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public static int method2567(int var0) {
      try {
         return 16383;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "cs.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcs;IIII)I")
   @ObfuscatedName("sy")
   public static int method2592(classCS var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method2593(var1, var1, var1, var1);
      }

      try {
         int var5 = 256 - var3;
         return (var5 * (var1 & 0xFF00) + var3 * (var2 & 0xFF00) & 0xFF0000) + (var5 * (var1 & 16711935) + var3 * (var2 & 16711935) & -16711936) >> 8;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cs.ax(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aq")
   static final int method2587(int var0) {
      try {
         return classFO.field2347 * 1376644555;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "cs.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;B)V")
   @ObfuscatedName("aw")
   static void method2613(classZV var0, classZV var1, byte var2) {
      try {
         if (classCR.field1228 == null) {
            classVP var4 = classHC.field2765;
            SpritePixels[] var3;
            if (!IndexDataBase.method11923(var4, "sl_back", "", -1199864680)) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               var3 = null;
            } else {
               int var5 = IndexDataBase.method11918(var4, "sl_back", 1835271085);
               int var6 = var4.method11922(var5, "", (byte)-40);
               var3 = classIR.method6349(var4, var5, var6, -1253680523);
            }

            classCR.field1228 = var3;
         }

         if (null == classCF.field720) {
            if (var2 <= 1) {
               return;
            }

            classCF.field720 = classOT.method8767(classHC.field2765, "sl_flags", "", (byte)68);
         }

         if (classPM.field5347 == null) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            classPM.field5347 = classOT.method8767(classHC.field2765, "sl_arrows", "", (byte)44);
         }

         if (null == classPD.field5225) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            classPD.field5225 = classOT.method8767(classHC.field2765, "sl_stars", "", (byte)47);
         }

         if (null == ChatLineBuffer.field590) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            ChatLineBuffer.field590 = classSU.method10729(classHC.field2765, "leftarrow", "", 145885891);
         }

         if (classUR.field6372 == null) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            classUR.field6372 = classSU.method10729(classHC.field2765, "rightarrow", "", -431936289);
         }

         classYW.method13737(classBF.field385 * -329898575, 23, 765, 480, 0);
         classYW.method13738(-329898575 * classBF.field385, 0, 125, 23, 12425273, 9135624);
         classYW.method13738(125 + -329898575 * classBF.field385, 0, 640, 23, 5197647, 2697513);
         var0.method59(classKK.field3910, 62 + classBF.field385 * -329898575, 15, 0, -1);
         if (null != classPD.field5225) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            IndexedSprite.method13853(classPD.field5225[1], 140 + -329898575 * classBF.field385, 1);
            var1.method51(classKK.field3911, 152 + classBF.field385 * -329898575, 10, 16777215, -1);
            IndexedSprite.method13853(classPD.field5225[0], 140 + -329898575 * classBF.field385, 12);
            var1.method51(classKK.field3854, 152 + -329898575 * classBF.field385, 21, 16777215, -1);
         }

         if (null != classPM.field5347) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            int var24 = 280 + -329898575 * classBF.field385;
            if (0 == World.field1152[0] && 0 == World.field1153[0]) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               IndexedSprite.method13853(classPM.field5347[2], var24, 4);
            } else {
               IndexedSprite.method13853(classPM.field5347[0], var24, 4);
            }

            label531: {
               if (World.field1152[0] == 0) {
                  if (var2 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (1 == World.field1153[0]) {
                     if (var2 <= 1) {
                        return;
                     }

                     IndexedSprite.method13853(classPM.field5347[3], var24 + 15, 4);
                     break label531;
                  }
               }

               IndexedSprite.method13853(classPM.field5347[1], var24 + 15, 4);
            }

            var0.method51(classKK.field3695, var24 + 32, 17, 16777215, -1);
            int var26 = classBF.field385 * -329898575 + 390;
            if (World.field1152[0] == 1 && 0 == World.field1153[0]) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               IndexedSprite.method13853(classPM.field5347[2], var26, 4);
            } else {
               IndexedSprite.method13853(classPM.field5347[0], var26, 4);
            }

            label520: {
               if (World.field1152[0] == 1) {
                  if (var2 <= 1) {
                     return;
                  }

                  if (1 == World.field1153[0]) {
                     IndexedSprite.method13853(classPM.field5347[3], 15 + var26, 4);
                     break label520;
                  }
               }

               IndexedSprite.method13853(classPM.field5347[1], 15 + var26, 4);
            }

            int var28;
            label514: {
               var0.method51(classKK.field3896, 32 + var26, 17, 16777215, -1);
               var28 = 500 + -329898575 * classBF.field385;
               if (World.field1152[0] == 2) {
                  if (var2 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (0 == World.field1153[0]) {
                     IndexedSprite.method13853(classPM.field5347[2], var28, 4);
                     break label514;
                  }
               }

               IndexedSprite.method13853(classPM.field5347[0], var28, 4);
            }

            label508: {
               if (World.field1152[0] == 2) {
                  if (var2 <= 1) {
                     return;
                  }

                  if (World.field1153[0] == 1) {
                     if (var2 <= 1) {
                        throw new IllegalStateException();
                     }

                     IndexedSprite.method13853(classPM.field5347[3], var28 + 15, 4);
                     break label508;
                  }
               }

               IndexedSprite.method13853(classPM.field5347[1], var28 + 15, 4);
            }

            int var30;
            label502: {
               var0.method51(classKK.field3915, var28 + 32, 17, 16777215, -1);
               var30 = 610 + classBF.field385 * -329898575;
               if (3 == World.field1152[0]) {
                  if (var2 <= 1) {
                     return;
                  }

                  if (0 == World.field1153[0]) {
                     if (var2 <= 1) {
                        throw new IllegalStateException();
                     }

                     IndexedSprite.method13853(classPM.field5347[2], var30, 4);
                     break label502;
                  }
               }

               IndexedSprite.method13853(classPM.field5347[0], var30, 4);
            }

            if (World.field1152[0] == 3 && World.field1153[0] == 1) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               IndexedSprite.method13853(classPM.field5347[3], 15 + var30, 4);
            } else {
               IndexedSprite.method13853(classPM.field5347[1], 15 + var30, 4);
            }

            var0.method51(classKK.field3916, 32 + var30, 17, 16777215, -1);
         }

         classYW.method13737(708 + classBF.field385 * -329898575, 4, 50, 16, 0);
         var1.method59(classKK.field3852, 708 + classBF.field385 * -329898575 + 25, 16, 16777215, -1);
         classBF.field399 = -520043415;
         if (classCR.field1228 != null) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            byte var25 = 88;
            byte var27 = 19;
            int var29 = 765 / (var25 + 1) - 1;
            int var31 = 480 / (var27 + 1);

            while (true) {
               int var7 = var31;
               int var8 = var29;
               if (var31 * (var29 - 1) >= -622036481 * World.field1151) {
                  if (var2 <= 1) {
                     throw new IllegalStateException();
                  }

                  var29--;
               }

               if ((var31 - 1) * var29 >= World.field1151 * -622036481) {
                  if (var2 <= 1) {
                     throw new IllegalStateException();
                  }

                  var31--;
               }

               if (var29 * (var31 - 1) >= World.field1151 * -622036481) {
                  if (var2 <= 1) {
                     throw new IllegalStateException();
                  }

                  var31--;
               }

               if (var31 == var7) {
                  if (var2 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (var29 == var8) {
                     if (var2 <= 1) {
                        throw new IllegalStateException();
                     }

                     var7 = (765 - var29 * var25) / (var29 + 1);
                     if (var7 > 5) {
                        if (var2 <= 1) {
                           throw new IllegalStateException();
                        }

                        var7 = 5;
                     }

                     var8 = (480 - var27 * var31) / (var31 + 1);
                     if (var8 > 5) {
                        if (var2 <= 1) {
                           throw new IllegalStateException();
                        }

                        var8 = 5;
                     }

                     int var9 = (765 - var25 * var29 - (var29 - 1) * var7) / 2;
                     int var10 = (480 - var31 * var27 - var8 * (var31 - 1)) / 2;
                     int var11 = (World.field1151 * -622036481 + var31 - 1) / var31;
                     classBF.field408 = -1501820553 * (var11 - var29);
                     if (ChatLineBuffer.field590 != null) {
                        if (var2 <= 1) {
                           throw new IllegalStateException();
                        }

                        if (classBF.field407 * -942812997 > 0) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           IndexedSprite.method13853(ChatLineBuffer.field590, 8, 8379747 * IntProjection.field1936 / 2 - ChatLineBuffer.field590.field7117 / 2);
                        }
                     }

                     if (null != classUR.field6372) {
                        if (var2 <= 1) {
                           throw new IllegalStateException();
                        }

                        if (classBF.field407 * -942812997 < classBF.field408 * -343302073) {
                           IndexedSprite.method13853(
                              classUR.field6372,
                              773246731 * classSA.field5895 - classUR.field6372.field7119 - 8,
                              8379747 * IntProjection.field1936 / 2 - classUR.field6372.field7117 / 2
                           );
                        }
                     }

                     int var12 = var10 + 23;
                     int var13 = classBF.field385 * -329898575 + var9;
                     int var14 = 0;
                     boolean var15 = false;
                     int var16 = -942812997 * classBF.field407;

                     for (int var17 = var31 * var16; var17 < -622036481 * World.field1151; var17++) {
                        if (var2 <= 1) {
                           throw new IllegalStateException();
                        }

                        if (var16 - -942812997 * classBF.field407 >= var29) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }
                           break;
                        }

                        World var18 = World.field1168[var17];
                        boolean var19 = true;
                        String var20 = Integer.toString(597852065 * var18.field1157);
                        if (-1 == var18.field1157 * 597852065) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           var20 = classKK.field3917;
                           var19 = false;
                        } else if (var18.field1157 * 597852065 > 1980) {
                           var20 = classKK.field3837;
                           var19 = false;
                        }

                        classCC var21 = null;
                        int var22 = 0;
                        if (var18.method2497(-403512673)) {
                           if (var2 <= 1) {
                              return;
                           }

                           classCC var10000;
                           if (var18.method2473((byte)-9)) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              var10000 = classCC.field666;
                           } else {
                              var10000 = classCC.field658;
                           }

                           var21 = var10000;
                        } else if (var18.method2482(-2093717319)) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           var21 = var18.method2473((byte)-40) ? classCC.field667 : classCC.field662;
                        } else if (var18.method2493(-843087954)) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           var22 = 16711680;
                           var21 = var18.method2473((byte)-11) ? classCC.field657 : classCC.field656;
                        } else if (World.method2501(var18, -1979508513)) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           classCC var37;
                           if (var18.method2473((byte)-64)) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              var37 = classCC.field665;
                           } else {
                              var37 = classCC.field663;
                           }

                           var21 = var37;
                        } else if (var18.method2485(23162323)) {
                           classCC var38;
                           if (var18.method2473((byte)-72)) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              var38 = classCC.field655;
                           } else {
                              var38 = classCC.field654;
                           }

                           var21 = var38;
                        } else if (World.method2509(var18, (byte)20)) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           classCC var39;
                           if (var18.method2473((byte)-123)) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              var39 = classCC.field653;
                           } else {
                              var39 = classCC.field659;
                           }

                           var21 = var39;
                        } else if (var18.method2512(-1945769594)) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           classCC var40;
                           if (var18.method2473((byte)-105)) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              var40 = classCC.field660;
                           } else {
                              var40 = classCC.field664;
                           }

                           var21 = var40;
                        }

                        label563: {
                           if (null != var21) {
                              if (var2 <= 1) {
                                 return;
                              }

                              if (371320039 * var21.field651 < classCR.field1228.length) {
                                 break label563;
                              }

                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }
                           }

                           classCC var41;
                           if (var18.method2473((byte)-1)) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              var41 = classCC.field661;
                           } else {
                              var41 = classCC.field652;
                           }

                           var21 = var41;
                        }

                        label466: {
                           if (classTZ.field6253 * -38255113 >= var13 && classTZ.field6262 * -2144333897 >= var12) {
                              if (var2 <= 1) {
                                 throw new IllegalStateException();
                              }

                              if (-38255113 * classTZ.field6253 < var25 + var13) {
                                 if (var2 <= 1) {
                                    throw new IllegalStateException();
                                 }

                                 if (-2144333897 * classTZ.field6262 < var27 + var12) {
                                    if (var2 <= 1) {
                                       throw new IllegalStateException();
                                    }

                                    if (var19) {
                                       if (var2 <= 1) {
                                          throw new IllegalStateException();
                                       }

                                       classBF.field399 = 520043415 * var17;
                                       classCR.field1228[var21.field651 * 371320039].method13561(var13, var12, 128, 16777215);
                                       var15 = true;
                                       break label466;
                                    }
                                 }
                              }
                           }

                           classCR.field1228[371320039 * var21.field651].method13552(var13, var12);
                        }

                        if (classCF.field720 != null) {
                           if (var2 <= 1) {
                              return;
                           }

                           IndexedSprite[] var42 = classCF.field720;
                           int var10001;
                           if (var18.method2473((byte)-9)) {
                              if (var2 <= 1) {
                                 return;
                              }

                              var10001 = classCF.field720.length / 2;
                           } else {
                              var10001 = 0;
                           }

                           IndexedSprite.method13853(var42[var10001 + var18.field1160 * -288138629], 29 + var13, var12);
                        }

                        var0.method59(Integer.toString(851246551 * var18.field1159), var13 + 15, var27 / 2 + var12 + 5, var22, -1);
                        var1.method59(var20, 60 + var13, var12 + var27 / 2 + 5, 268435455, -1);
                        var12 += var27 + var8;
                        if (++var14 >= var31) {
                           var12 = 23 + var10;
                           var13 += var25 + var7;
                           var14 = 0;
                           var16++;
                        }
                     }

                     if (var15) {
                        if (var2 <= 1) {
                           throw new IllegalStateException();
                        }

                        int var34 = var1.method33(World.field1168[classBF.field399 * -2105609177].field1165) + 6;
                        int var35 = var1.field8 + 8;
                        int var36 = classTZ.field6262 * -2144333897 + 25;
                        if (var35 + var36 > 480) {
                           if (var2 <= 1) {
                              throw new IllegalStateException();
                           }

                           var36 = -2144333897 * classTZ.field6262 - 25 - var35;
                        }

                        classYW.method13737(-38255113 * classTZ.field6253 - var34 / 2, var36, var34, var35, 16777120);
                        classYW.method13746(classTZ.field6253 * -38255113 - var34 / 2, var36, var34, var35, 0);
                        var1.method59(World.field1168[classBF.field399 * -2105609177].field1165, classTZ.field6253 * -38255113, var36 + var1.field8 + 4, 0, -1);
                     }
                     break;
                  }
               }
            }
         }
      } catch (RuntimeException var23) {
         throw classEG.method3899(var23, "cs.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   void method2584(int var1) {
      this.field1230 = this.field1234 + var1 * -646522752;
      if (this.field1230 * -1559545007 > this.field1253.length) {
         this.field1230 = this.field1230 - 1370680753 * this.field1253.length;
         int var2 = (int)(Math.random() * 12.0);
         this.method2610(this.field1236[var2], 1130018239);
      }

      int var15 = 0;
      int var3 = 128 * var1;
      int var4 = (256 - var1) * 128;

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = this.field1249[var3 + var15] - var1 * this.field1253[this.field1234 * -1559545007 + var15 & this.field1250.length - 1] / 6;
         if (var6 < 0) {
            var6 = 0;
         }

         this.field1252[var15++] = var6;
      }

      byte var16 = 10;
      int var17 = 128 - var16;

      for (int var7 = 256 - var1; var7 < 256; var7++) {
         int var8 = var7 * 128;

         for (int var9 = 0; var9 < 128; var9++) {
            int var10 = (int)(Math.random() * 100.0);
            if (var10 < 50 && var9 > var16 && var9 < var17) {
               this.field1253[var8 + var9] = 255;
            } else {
               this.field1252[var9 + var8] = 0;
            }
         }
      }

      if (this.field1235 * -1551918995 > 0) {
         this.field1234 = this.field1232 - var1 * 464569748;
      }

      if (this.field1237 * 1386667955 > 0) {
         this.field1231 = this.field1237 - var1 * -660365844;
      }

      if (0 == -1551918995 * this.field1232 && 1386667955 * this.field1235 == 0) {
         int var18 = (int)(Math.random() * (2000 / var1));
         if (0 == var18) {
            this.field1237 = -1329228800;
         }

         if (1 == var18) {
            this.field1230 = -1549931520;
         }
      }

      for (int var19 = 0; var19 < 256 - var1; var19++) {
         this.field1251[var19] = this.field1249[var1 + var19];
      }

      for (int var20 = 256 - var1; var20 < 256; var20++) {
         this.field1254[var20] = (int)(
            Math.sin(this.field1230 * -22252611 / 14.0) * 16.0
               + Math.sin(-22252611 * this.field1237 / 15.0) * 14.0
               + Math.sin(-22252611 * this.field1234 / 16.0) * 12.0
         );
         this.field1231 = this.field1230 + 1165746069;
      }

      this.field1235 = this.field1232 + 498436033 * var1;
      int var21 = ((client.field1088 * 1612595797 & 1) + var1) / 2;
      if (var21 > 0) {
         short var22 = 128;
         byte var23 = 2;
         int var24 = 128 - var23 - var23;

         for (int var11 = 0; var11 < -652396188 * this.field1235; var11++) {
            int var12 = (int)(Math.random() * var24) + var23;
            int var13 = (int)(Math.random() * var22) + var22;
            this.field1253[(var13 << 7) + var12] = 192;
         }

         this.field1232 = 0;

         for (int var25 = 0; var25 < 256; var25++) {
            int var27 = 0;
            int var29 = 128 * var25;

            for (int var14 = -var21; var14 < 128; var14++) {
               if (var14 + var21 < 128) {
                  var27 += this.field1254[var29 + var14 + var21];
               }

               if (var14 - (1 + var21) >= 0) {
                  var27 -= this.field1251[var14 + var29 - (1 + var21)];
               }

               if (var14 >= 0) {
                  this.field1253[var14 + var29] = var27 / (var21 * 2 + 1);
               }
            }
         }

         for (int var26 = 0; var26 < 128; var26++) {
            int var28 = 0;

            for (int var30 = -var21; var30 < 256; var30++) {
               int var31 = 128 * var30;
               if (var21 + var30 < 256) {
                  var28 += this.field1253[var26 + var31 + var21 * 128];
               }

               if (var30 - (1 + var21) >= 0) {
                  var28 -= this.field1251[var26 + var31 - 128 * (1 + var21)];
               }

               if (var30 >= 0) {
                  this.field1251[var26 + var31] = var28 / (var21 * 2 + 1);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("as")
   final void method2585(int var1, int var2) {
      try {
         this.field1230 = this.field1234 + var1 * -646522752;
         if (this.field1230 * -1559545007 > this.field1250.length) {
            if (var2 >= 554780875) {
               return;
            }

            this.field1237 = this.field1237 - 1370680753 * this.field1250.length;
            int var3 = (int)(Math.random() * 12.0);
            this.method2610(this.field1236[var3], 1130018239);
         }

         int var17 = 0;
         int var4 = 128 * var1;
         int var5 = (256 - var1) * 128;

         for (int var6 = 0; var6 < var5; var6++) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            int var7 = this.field1250[var4 + var17] - var1 * this.field1251[this.field1235 * -1559545007 + var17 & this.field1248.length - 1] / 6;
            if (var7 < 0) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               var7 = 0;
            }

            this.field1233[var17++] = var7;
         }

         byte var18 = 10;
         int var19 = 128 - var18;

         for (int var8 = 256 - var1; var8 < 256; var8++) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            int var9 = var8 * 128;

            for (int var10 = 0; var10 < 128; var10++) {
               int var11 = (int)(Math.random() * 100.0);
               if (var11 < 50) {
                  if (var2 >= 554780875) {
                     throw new IllegalStateException();
                  }

                  if (var10 > var18) {
                     if (var2 >= 554780875) {
                        throw new IllegalStateException();
                     }

                     if (var10 < var19) {
                        if (var2 >= 554780875) {
                           throw new IllegalStateException();
                        }

                        this.field1255[var9 + var10] = 255;
                        continue;
                     }
                  }
               }

               this.field1248[var10 + var9] = 0;
            }
         }

         if (this.field1235 * -1551918995 > 0) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            this.field1235 -= var1 * 464569748;
         }

         if (this.field1231 * 1386667955 > 0) {
            this.field1231 -= var1 * -660365844;
         }

         if (0 == -1551918995 * this.field1231) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            if (1386667955 * this.field1231 == 0) {
               if (var2 >= 554780875) {
                  return;
               }

               int var20 = (int)(Math.random() * (2000 / var1));
               if (0 == var20) {
                  if (var2 >= 554780875) {
                     throw new IllegalStateException();
                  }

                  this.field1235 = -1329228800;
               }

               if (1 == var20) {
                  if (var2 >= 554780875) {
                     throw new IllegalStateException();
                  }

                  this.field1235 = -1549931520;
               }
            }
         }

         for (int var21 = 0; var21 < 256 - var1; var21++) {
            if (var2 >= 554780875) {
               return;
            }

            this.field1251[var21] = this.field1254[var1 + var21];
         }

         for (int var22 = 256 - var1; var22 < 256; var22++) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            this.field1250[var22] = (int)(
               Math.sin(this.field1237 * -22252611 / 14.0) * 16.0
                  + Math.sin(-22252611 * this.field1231 / 15.0) * 14.0
                  + Math.sin(-22252611 * this.field1234 / 16.0) * 12.0
            );
            this.field1237 = this.field1230 + 1165746069;
         }

         this.field1234 = this.field1235 + 498436033 * var1;
         int var23 = ((client.field1087 * 1612595797 & 1) + var1) / 2;
         if (var23 > 0) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            short var24 = 128;
            byte var25 = 2;
            int var26 = 128 - var25 - var25;

            for (int var12 = 0; var12 < -652396188 * this.field1237; var12++) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               int var13 = (int)(Math.random() * var26) + var25;
               int var14 = (int)(Math.random() * var24) + var24;
               this.field1251[(var14 << 7) + var13] = 192;
            }

            this.field1230 = 0;

            for (int var27 = 0; var27 < 256; var27++) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               int var29 = 0;
               int var31 = 128 * var27;

               for (int var15 = -var23; var15 < 128; var15++) {
                  if (var2 >= 554780875) {
                     return;
                  }

                  if (var15 + var23 < 128) {
                     var29 += this.field1255[var31 + var15 + var23];
                  }

                  if (var15 - (1 + var23) >= 0) {
                     if (var2 >= 554780875) {
                        return;
                     }

                     var29 -= this.field1254[var15 + var31 - (1 + var23)];
                  }

                  if (var15 >= 0) {
                     if (var2 >= 554780875) {
                        throw new IllegalStateException();
                     }

                     this.field1252[var15 + var31] = var29 / (var23 * 2 + 1);
                  }
               }
            }

            for (int var28 = 0; var28 < 128; var28++) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               int var30 = 0;

               for (int var32 = -var23; var32 < 256; var32++) {
                  if (var2 >= 554780875) {
                     return;
                  }

                  int var33 = 128 * var32;
                  if (var23 + var32 < 256) {
                     var30 += this.field1250[var28 + var33 + var23 * 128];
                  }

                  if (var32 - (1 + var23) >= 0) {
                     if (var2 >= 554780875) {
                        return;
                     }

                     var30 -= this.field1233[var28 + var33 - 128 * (1 + var23)];
                  }

                  if (var32 >= 0) {
                     if (var2 >= 554780875) {
                        return;
                     }

                     this.field1233[var28 + var33] = var30 / (var23 * 2 + 1);
                  }
               }
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "cs.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcs;II)V")
   @ObfuscatedName("zt")
   public static void method2586(classCS var0, int var1, int var2) {
      if (var0 == null) {
         var0.method2585(var1, var1);
      }

      try {
         var0.field1232 += var1 * -646522752;
         if (var0.field1232 * -1559545007 > var0.field1248.length) {
            if (var2 >= 554780875) {
               return;
            }

            var0.field1232 = var0.field1232 - 1370680753 * var0.field1248.length;
            int var3 = (int)(Math.random() * 12.0);
            var0.method2610(var0.field1236[var3], 1130018239);
         }

         int var17 = 0;
         int var4 = 128 * var1;
         int var5 = (256 - var1) * 128;

         for (int var6 = 0; var6 < var5; var6++) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            int var7 = var0.field1252[var4 + var17] - var1 * var0.field1248[var0.field1232 * -1559545007 + var17 & var0.field1248.length - 1] / 6;
            if (var7 < 0) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               var7 = 0;
            }

            var0.field1252[var17++] = var7;
         }

         byte var18 = 10;
         int var19 = 128 - var18;

         for (int var8 = 256 - var1; var8 < 256; var8++) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            int var9 = var8 * 128;

            for (int var10 = 0; var10 < 128; var10++) {
               int var11 = (int)(Math.random() * 100.0);
               if (var11 < 50) {
                  if (var2 >= 554780875) {
                     throw new IllegalStateException();
                  }

                  if (var10 > var18) {
                     if (var2 >= 554780875) {
                        throw new IllegalStateException();
                     }

                     if (var10 < var19) {
                        if (var2 >= 554780875) {
                           throw new IllegalStateException();
                        }

                        var0.field1252[var9 + var10] = 255;
                        continue;
                     }
                  }
               }

               var0.field1252[var10 + var9] = 0;
            }
         }

         if (var0.field1230 * -1551918995 > 0) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            var0.field1230 -= var1 * 464569748;
         }

         if (var0.field1231 * 1386667955 > 0) {
            var0.field1231 -= var1 * -660365844;
         }

         if (0 == -1551918995 * var0.field1230) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            if (1386667955 * var0.field1231 == 0) {
               if (var2 >= 554780875) {
                  return;
               }

               int var20 = (int)(Math.random() * (2000 / var1));
               if (0 == var20) {
                  if (var2 >= 554780875) {
                     throw new IllegalStateException();
                  }

                  var0.field1230 = -1329228800;
               }

               if (1 == var20) {
                  if (var2 >= 554780875) {
                     throw new IllegalStateException();
                  }

                  var0.field1231 = -1549931520;
               }
            }
         }

         for (int var21 = 0; var21 < 256 - var1; var21++) {
            if (var2 >= 554780875) {
               return;
            }

            var0.field1233[var21] = var0.field1233[var1 + var21];
         }

         for (int var22 = 256 - var1; var22 < 256; var22++) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            var0.field1233[var22] = (int)(
               Math.sin(var0.field1235 * -22252611 / 14.0) * 16.0
                  + Math.sin(-22252611 * var0.field1235 / 15.0) * 14.0
                  + Math.sin(-22252611 * var0.field1235 / 16.0) * 12.0
            );
            var0.field1235 += 1165746069;
         }

         var0.field1237 += 498436033 * var1;
         int var23 = ((client.field856 * 1612595797 & 1) + var1) / 2;
         if (var23 > 0) {
            if (var2 >= 554780875) {
               throw new IllegalStateException();
            }

            short var24 = 128;
            byte var25 = 2;
            int var26 = 128 - var25 - var25;

            for (int var12 = 0; var12 < -652396188 * var0.field1237; var12++) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               int var13 = (int)(Math.random() * var26) + var25;
               int var14 = (int)(Math.random() * var24) + var24;
               var0.field1252[(var14 << 7) + var13] = 192;
            }

            var0.field1237 = 0;

            for (int var27 = 0; var27 < 256; var27++) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               int var29 = 0;
               int var31 = 128 * var27;

               for (int var15 = -var23; var15 < 128; var15++) {
                  if (var2 >= 554780875) {
                     return;
                  }

                  if (var15 + var23 < 128) {
                     var29 += var0.field1252[var31 + var15 + var23];
                  }

                  if (var15 - (1 + var23) >= 0) {
                     if (var2 >= 554780875) {
                        return;
                     }

                     var29 -= var0.field1252[var15 + var31 - (1 + var23)];
                  }

                  if (var15 >= 0) {
                     if (var2 >= 554780875) {
                        throw new IllegalStateException();
                     }

                     var0.field1254[var15 + var31] = var29 / (var23 * 2 + 1);
                  }
               }
            }

            for (int var28 = 0; var28 < 128; var28++) {
               if (var2 >= 554780875) {
                  throw new IllegalStateException();
               }

               int var30 = 0;

               for (int var32 = -var23; var32 < 256; var32++) {
                  if (var2 >= 554780875) {
                     return;
                  }

                  int var33 = 128 * var32;
                  if (var23 + var32 < 256) {
                     var30 += var0.field1254[var28 + var33 + var23 * 128];
                  }

                  if (var32 - (1 + var23) >= 0) {
                     if (var2 >= 554780875) {
                        return;
                     }

                     var30 -= var0.field1254[var28 + var33 - 128 * (1 + var23)];
                  }

                  if (var32 >= 0) {
                     if (var2 >= 554780875) {
                        return;
                     }

                     var0.field1252[var28 + var33] = var30 / (var23 * 2 + 1);
                  }
               }
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "cs.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ao")
   final void method2608(int var1, int var2) {
      try {
         int var3 = 0;

         for (int var4 = 1; var4 < 255; var4++) {
            if (var2 == 120041229) {
               throw new IllegalStateException();
            }

            int var5 = this.field1252[var4] * (256 - var4) / 256;
            int var6 = var1 + var5;
            int var7 = 0;
            int var8 = 128;
            if (var6 < 0) {
               if (var2 == 120041229) {
                  throw new IllegalStateException();
               }

               var7 = -var6;
               var6 = 0;
            }

            if (128 + var6 >= classWO.field6761.field6965 * -1238133717) {
               if (var2 == 120041229) {
                  throw new IllegalStateException();
               }

               var8 = -1238133717 * classWO.field6761.field6966 - var6;
            }

            int var9 = classWO.field6761.field6966 * -1238133717 * (var4 + 8) + var6;
            var3 += var7;

            for (int var10 = var7; var10 < var8; var10++) {
               if (var2 == 120041229) {
                  return;
               }

               int var11 = this.field1248[var3++];
               int var12 = var9 % classYW.field7099;
               if (var11 != 0) {
                  if (var2 == 120041229) {
                     throw new IllegalStateException();
                  }

                  if (var12 >= classYW.field7094) {
                     if (var2 == 120041229) {
                        throw new IllegalStateException();
                     }

                     if (var12 < classYW.field7099) {
                        int var14 = 256 - var11;
                        int var18 = this.field1252[var11];
                        int var15 = classWO.field6761.field6969[var9];
                        classWO.field6761.field6969[var9++] = 0xFF000000
                           | ((var15 & 0xFF00) * var14 + (var18 & 0xFF00) * var11 & 0xFF0000)
                                 + ((var15 & 16711935) * var14 + var11 * (var18 & 16711935) & -16711936)
                              >> 8;
                        continue;
                     }
                  }
               }

               var9++;
            }

            var3 += 128 - var8;
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "cs.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I[I)V")
   @ObfuscatedName("bg")
   final void method2604(int var1, int[] var2) {
      int var3 = this.field1233.length;

      for (int var4 = 0; var4 < var3; var4++) {
         if (var1 > 768) {
            this.field1252[var4] = method2592(this, this.field1233[var4], var2[var4], 1024 - var1, 1979053932);
         } else if (var1 > 256) {
            this.field1248[var4] = var2[var4];
         } else {
            this.field1248[var4] = method2592(this, var2[var4], this.field1252[var4], 256 - var1, 1979053932);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)I")
   @ObfuscatedName("ax")
   final int method2593(int var1, int var2, int var3, int var4) {
      try {
         int var5 = 256 - var3;
         return (var5 * (var1 & 0xFF00) + var3 * (var2 & 0xFF00) & 0xFF0000) + (var5 * (var1 & 16711935) + var3 * (var2 & 16711935) & -16711936) >> 8;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cs.ax(" + 41);
      }
   }
}
