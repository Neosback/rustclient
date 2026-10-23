import java.util.LinkedList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ha")
public abstract class classHA {
   @ObfuscatedName("as")
   int field2752;
   @ObfuscatedName("aa")
   int field2748;
   @ObfuscatedName("ax")
   int field2753;
   @ObfuscatedName("ac")
   int field2749;
   @ObfuscatedName("aq")
   byte[][][] field2754;
   @ObfuscatedName("ao")
   int field2751;
   @ObfuscatedName("al")
   int field2750;
   @ObfuscatedName("aj")
   short[][][] field2757;
   @ObfuscatedName("ay")
   byte[][][] field2755;
   @ObfuscatedName("au")
   boolean field2747;
   @ObfuscatedSignature(descriptor = "[[[[Lhz;")
   @ObfuscatedName("ad")
   classHZ[][][][] field2758;
   @ObfuscatedName("ap")
   boolean field2746 = false;
   @ObfuscatedName("ag")
   short[][][] field2756;

   @ObfuscatedSignature(descriptor = "(IILxy;I)V")
   @ObfuscatedName("be")
   void method6010(int var1, int var2, classXY var3, int var4) {
      int var5 = ((var4 & 24) >> 3) + 1;
      boolean var6 = 0 != (var4 & 2);
      boolean var7 = 0 != (var4 & 4);
      this.field2756[0][var1][var2] = (short)var3.method13099(-509588321);
      if (var6) {
         int var8 = var3.method13095(-346779531);

         for (int var9 = 0; var9 < var8; var9++) {
            int var10 = var3.method13099(1101615544);
            if (var10 != 0) {
               this.field2757[var9][var1][var2] = (short)var10;
               int var11 = var3.method13095(-346779531);
               this.field2755[var9][var1][var2] = (byte)(var11 >> 2);
               this.field2754[var9][var1][var2] = (byte)(var11 & 3);
            }
         }
      }

      if (var7) {
         for (int var14 = 0; var14 < var5; var14++) {
            int var15 = var3.method13095(-346779531);
            if (0 != var15) {
               classHZ[] var16 = this.field2758[var14][var1][var2] = new classHZ[var15];

               for (int var17 = 0; var17 < var15; var17++) {
                  int var12 = var3.method13173(classXM.field6887.field6888, 1448250251);
                  int var13 = var3.method13095(-346779531);
                  var16[var17] = new classHZ(var12, var13 >> 2, var13 & 3);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ai")
   boolean method5988(byte var1) {
      try {
         boolean var10000;
         if (this.field2746 && this.field2747) {
            if (var1 == 4) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ha.ai(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("aw")
   void method5997(byte var1) {
      try {
         this.field2756 = (short[][][])null;
         this.field2757 = (short[][][])null;
         this.field2755 = (byte[][][])null;
         this.field2754 = (byte[][][])null;
         this.field2758 = (classHZ[][][][])null;
         this.field2746 = false;
         this.field2747 = false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ha.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILxy;B)V")
   @ObfuscatedName("ak")
   void method6001(int var1, int var2, classXY var3, byte var4) {
      try {
         int var5 = var3.method13095(-346779531);
         if (0 == var5) {
            if (var4 < 33) {
               ;
            }
         } else {
            if ((var5 & 1) != 0) {
               if (var4 >= 33) {
                  throw new IllegalStateException();
               }

               method6006(this, var1, var2, var3, var5, (byte)59);
            } else {
               this.method6011(var1, var2, var3, var5, (byte)5);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ha.ak(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lha;IILxy;I)V")
   @ObfuscatedName("vf")
   public static void method6005(classHA var0, int var1, int var2, classXY var3, int var4) {
      if (var0 == null) {
         var0.method6008(var1, var1, var3, var1);
      }

      boolean var5 = (var4 & 2) != 0;
      if (var5) {
         var0.field2757[0][var1][var2] = (short)var3.method13099(369527776);
      }

      var0.field2756[0][var1][var2] = (short)var3.method13099(-1809869849);
   }

   @ObfuscatedSignature(descriptor = "(Lha;IILxy;IB)V")
   @ObfuscatedName("yg")
   public static void method6006(classHA var0, int var1, int var2, classXY var3, int var4, byte var5) {
      if (var0 == null) {
         var0.method6009(var1, var1, var3, var1, var5);
      }

      try {
         boolean var10000;
         if ((var4 & 2) != 0) {
            if (var5 == -1) {
               return;
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var6 = var10000;
         if (var6) {
            if (var5 == -1) {
               throw new IllegalStateException();
            }

            var0.field2757[0][var1][var2] = (short)var3.method13099(221517528);
         }

         var0.field2756[0][var1][var2] = (short)var3.method13099(1753846269);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ha.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("am")
   int method6014(byte var1) {
      try {
         return this.field2749 * 1151970023;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ha.an(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IILxy;IB)V")
   @ObfuscatedName("at")
   void method6011(int var1, int var2, classXY var3, int var4, byte var5) {
      try {
         int var6 = ((var4 & 24) >> 3) + 1;
         boolean var10000;
         if (0 != (var4 & 2)) {
            if (var5 == 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var7 = var10000;
         if (0 != (var4 & 4)) {
            if (var5 == 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var8 = var10000;
         this.field2756[0][var1][var2] = (short)var3.method13099(130975000);
         if (var7) {
            if (var5 == 1) {
               throw new IllegalStateException();
            }

            int var9 = var3.method13095(-346779531);

            for (int var10 = 0; var10 < var9; var10++) {
               if (var5 == 1) {
                  return;
               }

               int var11 = var3.method13099(1858909979);
               if (var11 != 0) {
                  if (var5 == 1) {
                     throw new IllegalStateException();
                  }

                  this.field2757[var10][var1][var2] = (short)var11;
                  int var12 = var3.method13095(-346779531);
                  this.field2755[var10][var1][var2] = (byte)(var12 >> 2);
                  this.field2754[var10][var1][var2] = (byte)(var12 & 3);
               }
            }
         }

         if (var8) {
            if (var5 == 1) {
               throw new IllegalStateException();
            }

            for (int var16 = 0; var16 < var6; var16++) {
               if (var5 == 1) {
                  throw new IllegalStateException();
               }

               int var17 = var3.method13095(-346779531);
               if (0 == var17) {
                  if (var5 == 1) {
                     throw new IllegalStateException();
                  }
               } else {
                  classHZ[] var18 = this.field2758[var16][var1][var2] = new classHZ[var17];

                  for (int var19 = 0; var19 < var17; var19++) {
                     if (var5 == 1) {
                        throw new IllegalStateException();
                     }

                     int var13 = var3.method13173(classXM.field6887.field6888, 1767541666);
                     int var14 = var3.method13095(-346779531);
                     var18[var19] = new classHZ(var13, var14 >> 2, var14 & 3);
                  }
               }
            }
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "ha.at(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lha;IILxy;)V")
   @ObfuscatedName("ur")
   public static void method6002(classHA var0, int var1, int var2, classXY var3) {
      if (var0 == null) {
         var0.method6015();
      }

      int var4 = var3.method13095(-346779531);
      if (0 != var4) {
         if ((var4 & 1) != 0) {
            method6006(var0, var1, var2, var3, var4, (byte)-46);
         } else {
            var0.method6011(var1, var2, var3, var4, (byte)-37);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lha;)Z")
   @ObfuscatedName("mm")
   public static boolean method5989(classHA var0) {
      return var0.field2746 && var0.field2747;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   void method5998() {
      this.field2756 = (short[][][])null;
      this.field2757 = (short[][][])null;
      this.field2755 = (byte[][][])null;
      this.field2754 = (byte[][][])null;
      this.field2758 = (classHZ[][][][])null;
      this.field2746 = false;
      this.field2747 = false;
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/String;")
   @ObfuscatedName("az")
   public static String method5986(long var0) {
      try {
         if (var0 > 0L && var0 < 6582952005840035281L) {
            if (var0 % 37L == 0L) {
               return null;
            } else {
               int var2 = 0;

               for (long var3 = var0; var3 != 0L; var3 /= 37L) {
                  var2++;
               }

               StringBuilder var5 = new StringBuilder(var2);

               while (var0 != 0L) {
                  long var6 = var0;
                  var0 /= 37L;
                  var5.append(classOB.field4806[(int)(var6 - var0 * 37L)]);
               }

               return var5.reverse().toString();
            }
         } else {
            return null;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ha.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("an")
   int method6016(byte var1) {
      try {
         return -456391909 * this.field2748;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ha.am(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bq")
   int method6017() {
      return -456391909 * this.field2748;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   abstract void vmethod466(classXY var1);

   @ObfuscatedSignature(descriptor = "(Lha;Lxy;B)V")
   @ObfuscatedName("nx")
   public static void method5992(classHA var0, classXY var1, byte var2) {
      if (var0 == null) {
         var0.method5996(var1, var2);
      }

      try {
         if (var1 != null) {
            if (var2 <= 0) {
               throw new IllegalStateException();
            }

            var0.vmethod467(var1, -1811901292);
            var0.field2746 = true;
            var0.field2747 = true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ha.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ah")
   boolean method5990() {
      return this.field2746 && this.field2747;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bn")
   boolean method5991() {
      return this.field2746 && this.field2747;
   }

   classHA() {
      this.field2747 = false;
      new LinkedList();
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("br")
   void method5993(classXY var1) {
      if (var1 != null) {
         this.vmethod467(var1, -1811901292);
         this.field2746 = true;
         this.field2747 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("bm")
   void method5994(classXY var1) {
      if (var1 != null) {
         this.vmethod467(var1, -1811901292);
         this.field2746 = true;
         this.field2747 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("by")
   int method6015() {
      return this.field2749 * 1522518828;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   void method5999() {
      this.field2756 = (short[][][])null;
      this.field2757 = (short[][][])null;
      this.field2755 = (byte[][][])null;
      this.field2754 = (byte[][][])null;
      this.field2758 = (classHZ[][][][])null;
      this.field2746 = false;
      this.field2747 = false;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("af")
   public static int method5987(int var0, int var1) {
      try {
         return var0 >> 14 & 1023;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ha.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   void method6000() {
      this.field2756 = (short[][][])null;
      this.field2757 = (short[][][])null;
      this.field2755 = (byte[][][])null;
      this.field2754 = (byte[][][])null;
      this.field2758 = (classHZ[][][][])null;
      this.field2746 = false;
      this.field2747 = false;
   }

   @ObfuscatedSignature(descriptor = "(IILxy;)V")
   @ObfuscatedName("bo")
   void method6003(int var1, int var2, classXY var3) {
      int var4 = var3.method13095(-346779531);
      if (0 != var4) {
         if ((var4 & 1) != 0) {
            method6006(this, var1, var2, var3, var4, (byte)26);
         } else {
            this.method6011(var1, var2, var3, var4, (byte)-27);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IILxy;)V")
   @ObfuscatedName("bu")
   void method6004(int var1, int var2, classXY var3) {
      int var4 = var3.method13095(-346779531);
      if (0 != var4) {
         if ((var4 & 1) != 0) {
            method6006(this, var1, var2, var3, var4, (byte)-78);
         } else {
            this.method6011(var1, var2, var3, var4, (byte)43);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("af")
   abstract void vmethod467(classXY var1, int var2);

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   abstract void vmethod468(classXY var1);

   @ObfuscatedSignature(descriptor = "(IILxy;I)V")
   @ObfuscatedName("bw")
   void method6007(int var1, int var2, classXY var3, int var4) {
      boolean var5 = (var4 & 2) != 0;
      if (var5) {
         this.field2757[0][var1][var2] = (short)var3.method13099(1702094832);
      }

      this.field2756[0][var1][var2] = (short)var3.method13099(-1733085897);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("bt")
   void method5995(classXY var1) {
      if (var1 != null) {
         this.vmethod467(var1, -1811901292);
         this.field2746 = true;
         this.field2747 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;[BIIIIB)V")
   @ObfuscatedName("ab")
   static final void method6013(WorldView var0, byte[] var1, int var2, int var3, int var4, int var5, byte var6) {
      try {
         if (var0.field1685 != null) {
            if (var6 == -1) {
               throw new IllegalStateException();
            }

            for (int var7 = 0; var7 < 4; var7++) {
               if (var6 == -1) {
                  throw new IllegalStateException();
               }

               CollisionData var8 = var0.field1685[var7];

               for (int var9 = var2; var9 < 64 + var2; var9++) {
                  if (var6 == -1) {
                     throw new IllegalStateException();
                  }

                  for (int var10 = var3; var10 < 64 + var3; var10++) {
                     if (var6 == -1) {
                        return;
                     }

                     if (var8.method5375(var9, var10, (byte)-108)) {
                        if (var6 == -1) {
                           return;
                        }

                        var8.method5371(var9, var10, 1073741824, -2134326968);
                     }
                  }
               }
            }
         }

         classXY var14 = new classXY(var1);

         for (int var15 = 0; var15 < 4; var15++) {
            if (var6 == -1) {
               throw new IllegalStateException();
            }

            for (int var17 = 0; var17 < 64; var17++) {
               if (var6 == -1) {
                  return;
               }

               for (int var19 = 0; var19 < 64; var19++) {
                  int var11 = var2 + var17;
                  int var12 = var19 + var3;
                  classKG.method6510(var0, var14, var15, var11, var12, var4 + var11, var12 + var5, 0, 1872302919);
               }
            }
         }

         int var10000;
         if (702114061 * var14.field6953 < var14.field6952.length) {
            if (var6 == -1) {
               throw new IllegalStateException();
            }

            var10000 = var14.method13095(-346779531);
         } else {
            var10000 = 0;
         }

         int var16 = var10000;
         boolean var22;
         if ((var16 & 1) != 0) {
            if (var6 == -1) {
               throw new IllegalStateException();
            }

            var22 = true;
         } else {
            var22 = false;
         }

         boolean var18 = var22;
         if (var18) {
            if (var6 == -1) {
               throw new IllegalStateException();
            }

            for (int var20 = 0; var20 < 64; var20++) {
               if (var6 == -1) {
                  throw new IllegalStateException();
               }

               for (int var21 = 0; var21 < 64; var21++) {
                  if (var6 == -1) {
                     return;
                  }

                  classLF.method6828(var14, 1868266747);
               }
            }
         }
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "ha.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("fm")
   static final void method6019(double var0) {
      try {
         classFH.method4714(var0);
         ((TextureProvider)classFH.field2255.field2237).method3826(var0);
         if (client.field1124 != null) {
            client.field1124.method12520(-1405629956);
         }

         ItemComposition.field5006.method6468();
         classAB.field122.method2757(var0);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ha.fm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bu")
   static final void method6018(int var0, int var1, int var2, int var3) {
      try {
         if (classKI.field3596 * -325062789 < var0) {
            if (var3 <= 851236585) {
               throw new IllegalStateException();
            }

            classKI.field3596 = classKI.field3596
               + (classCV.field1301 * 528799137 + classHE.field2778 * 93292033 * (var0 - classKI.field3596 * -325062789) / 1000) * -1197566029;
            if (-325062789 * classKI.field3596 > var0) {
               classKI.field3596 = var0 * -1197566029;
            }
         }

         if (-325062789 * classKI.field3596 > var0) {
            classKI.field3596 = classKI.field3596
               - -1197566029 * ((-325062789 * classKI.field3596 - var0) * 93292033 * classHE.field2778 / 1000 + classCV.field1301 * 528799137);
            if (-325062789 * classKI.field3596 < var0) {
               classKI.field3596 = -1197566029 * var0;
            }
         }

         if (1615527037 * classNL.field4682 < var1) {
            if (var3 <= 851236585) {
               throw new IllegalStateException();
            }

            classNL.field4682 = classNL.field4682
               + ((var1 - classNL.field4682 * 1615527037) * classHE.field2778 * 93292033 / 1000 + 528799137 * classCV.field1301) * -477405995;
            if (1615527037 * classNL.field4682 > var1) {
               if (var3 <= 851236585) {
                  throw new IllegalStateException();
               }

               classNL.field4682 = var1 * -477405995;
            }
         }

         if (1615527037 * classNL.field4682 > var1) {
            if (var3 <= 851236585) {
               throw new IllegalStateException();
            }

            classNL.field4682 = classNL.field4682
               - -477405995 * ((1615527037 * classNL.field4682 - var1) * 93292033 * classHE.field2778 / 1000 + classCV.field1301 * 528799137);
            if (classNL.field4682 * 1615527037 < var1) {
               if (var3 <= 851236585) {
                  return;
               }

               classNL.field4682 = var1 * -477405995;
            }
         }

         if (1343311673 * classAI.field167 < var2) {
            if (var3 <= 851236585) {
               throw new IllegalStateException();
            }

            classAI.field167 = classAI.field167
               + (528799137 * classCV.field1301 + classHE.field2778 * 93292033 * (var2 - 1343311673 * classAI.field167) / 1000) * -337711351;
            if (classAI.field167 * 1343311673 > var2) {
               if (var3 <= 851236585) {
                  throw new IllegalStateException();
               }

               classAI.field167 = var2 * -337711351;
            }
         }

         if (1343311673 * classAI.field167 > var2) {
            if (var3 <= 851236585) {
               throw new IllegalStateException();
            }

            classAI.field167 = classAI.field167
               - (528799137 * classCV.field1301 + 93292033 * classHE.field2778 * (1343311673 * classAI.field167 - var2) / 1000) * -337711351;
            if (classAI.field167 * 1343311673 < var2) {
               if (var3 <= 851236585) {
                  throw new IllegalStateException();
               }

               classAI.field167 = -337711351 * var2;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ha.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Lcu;")
   @ObfuscatedName("ag")
   static classCU[] method5985(byte var0) {
      try {
         return new classCU[]{classCU.field1284, classCU.field1287, classCU.field1288, classCU.field1285, classCU.field1286};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ha.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILxy;I)V")
   @ObfuscatedName("bi")
   void method6012(int var1, int var2, classXY var3, int var4) {
      int var5 = ((var4 & 24) >> 3) + 1;
      boolean var6 = 0 != (var4 & 2);
      boolean var7 = 0 != (var4 & 4);
      this.field2756[0][var1][var2] = (short)var3.method13099(1340774430);
      if (var6) {
         int var8 = var3.method13095(-346779531);

         for (int var9 = 0; var9 < var8; var9++) {
            int var10 = var3.method13099(-550142747);
            if (var10 != 0) {
               this.field2757[var9][var1][var2] = (short)var10;
               int var11 = var3.method13095(-346779531);
               this.field2755[var9][var1][var2] = (byte)(var11 >> 2);
               this.field2754[var9][var1][var2] = (byte)(var11 & 3);
            }
         }
      }

      if (var7) {
         for (int var14 = 0; var14 < var5; var14++) {
            int var15 = var3.method13095(-346779531);
            if (0 != var15) {
               classHZ[] var16 = this.field2758[var14][var1][var2] = new classHZ[var15];

               for (int var17 = 0; var17 < var15; var17++) {
                  int var12 = var3.method13173(classXM.field6887.field6888, 1524934831);
                  int var13 = var3.method13095(-346779531);
                  var16[var17] = new classHZ(var12, var13 >> 2, var13 & 3);
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("ar")
   void method5996(classXY var1, byte var2) {
      try {
         if (var1 != null) {
            if (var2 <= 0) {
               throw new IllegalStateException();
            }

            this.vmethod467(var1, -1811901292);
            this.field2746 = true;
            this.field2746 = true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ha.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IILxy;I)V")
   @ObfuscatedName("bb")
   void method6008(int var1, int var2, classXY var3, int var4) {
      boolean var5 = (var4 & 2) != 0;
      if (var5) {
         this.field2757[0][var1][var2] = (short)var3.method13107(369527776);
      }

      this.field2756[0][var1][var2] = (short)var3.method13266(-1809869849);
   }

   @ObfuscatedSignature(descriptor = "(IILxy;IB)V")
   @ObfuscatedName("av")
   void method6009(int var1, int var2, classXY var3, int var4, byte var5) {
      try {
         boolean var10000;
         if ((var4 & 2) != 0) {
            if (var5 == -1) {
               return;
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var6 = var10000;
         if (var6) {
            if (var5 == -1) {
               throw new IllegalStateException();
            }

            this.field2757[0][var1][var2] = (short)var3.method13254(221517528);
         }

         this.field2756[0][var1][var2] = (short)var3.method13201(1753846269);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ha.av(" + ')');
      }
   }
}
