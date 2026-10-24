import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("er")
public class ModelData extends Renderable implements net.runelite.api.ModelData {
   @ObfuscatedName("an")
   byte field1849;
   @ObfuscatedName("bf")
   int field1888;
   @ObfuscatedName("av")
   short[] field1851;
   @ObfuscatedName("lr")
   public int[] field1896;
   @ObfuscatedName("sk")
   public float[] field1855;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1885 = -2;
   @ObfuscatedName("ag")
   static int[] field1881 = new int[10000];
   @ObfuscatedName("as")
   static int field1880 = 0;
   @ObfuscatedSignature(descriptor = "[Leb;")
   @ObfuscatedName("bp")
   classEB[] field1872;
   @ObfuscatedName("am")
   int field1852;
   @ObfuscatedName("ab")
   static int[] field1879 = new int[10000];
   @ObfuscatedName("ao")
   int[] field1894;
   @ObfuscatedName("mp")
   public float[] field1854;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1886 = -3;
   @ObfuscatedName("aj")
   int[] field1892;
   @ObfuscatedName("xt")
   public int[] field1897;
   @ObfuscatedName("ay")
   int field1847;
   @ObfuscatedName("aq")
   int[] field1878;
   @ObfuscatedName("ac")
   static int[] field1883 = classFH.field2252;
   @ObfuscatedName("bn")
   short[] field1867;
   @ObfuscatedName("au")
   byte[] field1864;
   @ObfuscatedName("ai")
   byte[] field1865;
   @ObfuscatedName("ar")
   byte[] field1857;
   @ObfuscatedName("aw")
   byte[] field1866;
   @ObfuscatedName("ak")
   short[] field1850;
   @ObfuscatedName("bq")
   int field1890;
   @ObfuscatedName("at")
   byte[] field1870;
   @ObfuscatedName("ba")
   int[][] field1858;
   @ObfuscatedName("aa")
   int field1846 = 0;
   @ObfuscatedName("ah")
   byte[] field1859;
   @ObfuscatedName("hx")
   public int[] field1895;
   @ObfuscatedName("bt")
   public short field1877;
   @ObfuscatedName("bx")
   short[] field1856;
   @ObfuscatedName("bc")
   short[] field1860;
   @ObfuscatedName("ad")
   int[] field1862;
   @ObfuscatedSignature(descriptor = "[Lfr;")
   @ObfuscatedName("br")
   classFR[] field1871;
   @ObfuscatedName("jc")
   public float[] field1853;
   @ObfuscatedName("bm")
   public short field1876;
   @ObfuscatedName("ax")
   static int[] field1882 = classFH.field2251;
   @ObfuscatedName("bg")
   int[][] field1874;
   @ObfuscatedName("bj")
   int[][] field1875;
   @ObfuscatedName("bk")
   int[] field1868;
   @ObfuscatedName("bo")
   int[] field1869;
   @ObfuscatedName("bu")
   int[][] field1861;
   @ObfuscatedName("al")
   int[] field1893;
   @ObfuscatedSignature(descriptor = "[Lfr;")
   @ObfuscatedName("bw")
   classFR[] field1873;
   @ObfuscatedName("bb")
   boolean field1848;
   @ObfuscatedName("bi")
   int field1889;
   @ObfuscatedName("be")
   int field1887;
   @ObfuscatedName("by")
   int field1891;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field1884 = -1;
   @ObfuscatedName("ap")
   int[] field1863;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;[[IIIIZI)Ler;")
   @ObfuscatedName("nq")
   public static ModelData method4012(ModelData var0, int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      if (var0 == null) {
         return var0.method4016(var1, var2, var2, var2, var5, var2);
      } else {
         var0.method4053();
         int var7 = var2 + var0.field1887;
         int var8 = var2 + var0.field1891;
         int var9 = var4 + var0.field1888;
         int var10 = var4 + var0.field1890;
         if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
            var7 >>= 7;
            var8 = var8 + 127 >> 7;
            var9 >>= 7;
            var10 = var10 + 127 >> 7;
            if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
               return var0;
            } else {
               ModelData var11;
               if (var5) {
                  var11 = new ModelData(var0, true, true, true, true);
                  var11.field1854 = new float[var11.field1846];
               } else {
                  var11 = var0;
               }

               if (var6 == 0) {
                  for (int var12 = 0; var12 < var11.field1846; var12++) {
                     int var13 = (int)var0.field1853[var12] + var2;
                     int var14 = (int)var0.field1855[var12] + var4;
                     int var15 = var13 & 127;
                     int var16 = var14 & 127;
                     int var17 = var13 >> 7;
                     int var18 = var14 >> 7;
                     int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                     int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                     int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                     var11.field1854[var12] = (int)var0.field1854[var12] + var21 - var3;
                  }
               } else {
                  for (int var27 = 0; var27 < var11.field1846; var27++) {
                     int var28 = (-((int)var0.field1854[var27]) << 16) / (var0.field1741 * -1256242689);
                     if (var28 < var6) {
                        int var29 = (int)var0.field1853[var27] + var2;
                        int var30 = (int)var0.field1855[var27] + var4;
                        int var31 = var29 & 127;
                        int var32 = var30 & 127;
                        int var33 = var29 >> 7;
                        int var34 = var30 >> 7;
                        int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                        int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                        int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                        var11.field1854[var27] = (int)var0.field1854[var27] + (var22 - var3) * (var6 - var28) / var6;
                     } else {
                        var11.field1854[var27] = var0.field1854[var27];
                     }
                  }
               }

               var11.method4049();
               return var11;
            }
         } else {
            return var0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Ler;")
   @ObfuscatedName("fh")
   public ModelData method4079(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      return method4014(this, var1, var2, var3, var4, var5, var6);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dc")
   public void method4044(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field1846; var4++) {
         this.field1853[var4] = (int)this.field1853[var4] * var1 / 128;
         this.field1854[var4] = (int)this.field1854[var4] * var2 / 128;
         this.field1855[var4] = (int)this.field1855[var4] * var3 / 160667711;
      }

      this.method4049();
   }

   ModelData(byte[] var1) {
      this.field1847 = 0;
      this.field1849 = 0;
      this.field1848 = false;
      if (var1[var1.length - 1] == -3 && var1[var1.length - 2] == -1) {
         this.method3995(var1);
      } else if (var1[var1.length - 1] == -2 && var1[var1.length - 2] == -1) {
         this.method3998(var1);
      } else if (var1[var1.length - 1] == -1 && var1[var1.length - 2] == -1) {
         this.method4002(var1);
      } else {
         this.method4004(var1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lym;I)V")
   @ObfuscatedName("pv")
   public static void method3996(SpritePixels var0, int var1) {
      if (var0 == null) {
         var0.method13550(var1);
      } else {
         for (int var2 = var0.field7007 - 1; var2 > 0; var2--) {
            int var3 = var2 * var0.field7005;

            for (int var4 = var0.field7005 - 1; var4 > 0; var4--) {
               if (var0.field7003[var4 + var3] == 0 && var0.field7003[var4 + var3 - 1 - var0.field7005] != 0) {
                  var0.field7003[var4 + var3] = var1;
               }
            }
         }
      }
   }

   public float[] getVerticesX() {
      return this.field1853;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("fp")
   public static void method4037(ModelData var0) {
      if (var0 == null) {
         var0.method4056();
      }

      for (int var1 = 0; var1 < var0.field1846; var1++) {
         var0.field1855[var1] = -((int)var0.field1855[var1]);
      }

      for (int var3 = 0; var3 < var0.field1847; var3++) {
         int var2 = var0.field1878[var3];
         var0.field1878[var3] = var0.field1863[var3];
         var0.field1863[var3] = var2;
      }

      var0.method4049();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;Ler;I)I")
   @ObfuscatedName("bm")
   public static int method4006(ModelData var0, ModelData var1, int var2) {
      if (var0 == null) {
         return var0.method4009(var0, var2);
      } else {
         int var3 = -1;
         int var4 = (int)var1.field1853[var2];
         int var5 = (int)var1.field1854[var2];
         int var6 = (int)var1.field1855[var2];

         for (int var7 = 0; var7 < var0.field1846; var7++) {
            if (var4 == (int)var0.field1853[var7] && var5 == (int)var0.field1854[var7] && var6 == (int)var0.field1855[var7]) {
               var3 = var7;
               break;
            }
         }

         if (var3 == -1) {
            var0.field1853[var0.field1846] = var4;
            var0.field1854[var0.field1846] = var5;
            var0.field1855[var0.field1846] = var6;
            if (var1.field1868 != null) {
               var0.field1868[var0.field1846] = var1.field1868[var2];
            }

            if (var1.field1874 != null) {
               var0.field1874[var0.field1846] = var1.field1874[var2];
               var0.field1875[var0.field1846] = var1.field1875[var2];
            }

            var3 = var0.field1846++;
         }

         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;)Lfx;")
   @ObfuscatedName("zr")
   public static Model method4061(ModelData var0) {
      if (var0 == null) {
         var0.cloneTransparencies();
      }

      return var0.method4070(128, 43690, 0, -1, 0);
   }

   @ObfuscatedSignature(descriptor = "(SS)Ler;")
   @ObfuscatedName("qn")
   public ModelData method4080(short var1, short var2) {
      this.method4035(var1, var2);
      return this;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("cr")
   void method3998(byte[] var1) {
      boolean var2 = false;
      boolean var3 = false;
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var4.field6953 = (var1.length - 23) * -1095856699;
      int var9 = var4.method13099(-1714581989);
      int var10 = var4.method13099(-753735522);
      int var11 = var4.method13095(-346779531);
      int var12 = var4.method13095(-346779531);
      int var13 = var4.method13095(-346779531);
      int var14 = var4.method13095(-346779531);
      int var15 = var4.method13095(-346779531);
      int var16 = var4.method13095(-346779531);
      int var17 = var4.method13095(-346779531);
      int var18 = var4.method13099(-281970823);
      int var19 = var4.method13099(-1637054274);
      int var20 = var4.method13099(-661393680);
      int var21 = var4.method13099(-489789117);
      int var22 = var4.method13099(1159326408);
      int var23 = 0;
      var23 += var9;
      var23 += var10;
      int var26 = var23;
      if (var13 == 255) {
         var23 += var10;
      }

      int var27 = var23;
      if (var15 == 1) {
         var23 += var10;
      }

      int var28 = var23;
      if (var12 == 1) {
         var23 += var10;
      }

      var23 += var22;
      int var30 = var23;
      if (var14 == 1) {
         var23 += var10;
      }

      var23 += var21;
      var23 += var10 * 2;
      var23 += var11 * 6;
      var23 += var18;
      var23 += var19;
      var23 += var20;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      if (var16 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
         this.field1866 = new byte[var10];
         this.field1851 = new short[var10];
      }

      if (var13 == 255) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      if (var17 == 1) {
         this.field1874 = new int[var9][];
         this.field1875 = new int[var9][];
      }

      this.field1850 = new short[var10];
      var4.field6953 = var23 * -1095856699;
      var5.field6953 = var23 * -1095856699;
      var6.field6953 = var23 * -1095856699;
      var7.field6953 = var23 * -1095856699;
      var8.field6953 = var23 * -1095856699;
      int var38 = 0;
      int var39 = 0;
      int var40 = 0;

      for (int var41 = 0; var41 < var9; var41++) {
         int var42 = var4.method13095(-346779531);
         int var43 = 0;
         if ((var42 & 1) != 0) {
            var43 = var5.method13146(-885484049);
         }

         int var44 = 0;
         if ((var42 & 2) != 0) {
            var44 = var6.method13146(-1392179173);
         }

         int var45 = 0;
         if ((var42 & 4) != 0) {
            var45 = var7.method13146(-864852339);
         }

         this.field1853[var41] = var38 + var43;
         this.field1854[var41] = var39 + var44;
         this.field1855[var41] = var40 + var45;
         var38 = (int)this.field1853[var41];
         var39 = (int)this.field1854[var41];
         var40 = (int)this.field1855[var41];
         if (var16 == 1) {
            this.field1868[var41] = var8.method13095(-346779531);
         }
      }

      if (var17 == 1) {
         for (int var58 = 0; var58 < var9; var58++) {
            int var61 = var8.method13095(-346779531);
            this.field1874[var58] = new int[var61];
            this.field1875[var58] = new int[var61];

            for (int var64 = 0; var64 < var61; var64++) {
               this.field1874[var58][var64] = var8.method13095(-346779531);
               this.field1875[var58][var64] = var8.method13095(-346779531);
            }
         }
      }

      var4.field6953 = var23 * -1095856699;
      var5.field6953 = var28 * -1095856699;
      var6.field6953 = var26 * -1095856699;
      var7.field6953 = var30 * -1095856699;
      var8.field6953 = var27 * -1095856699;

      for (int var59 = 0; var59 < var10; var59++) {
         this.field1850[var59] = (short)var4.method13099(-371921787);
         if (var12 == 1) {
            int var62 = var5.method13095(-346779531);
            if ((var62 & 1) == 1) {
               this.field1864[var59] = 1;
               var2 = true;
            } else {
               this.field1864[var59] = 0;
            }

            if ((var62 & 2) == 2) {
               this.field1866[var59] = (byte)(var62 >> 2);
               this.field1851[var59] = this.field1850[var59];
               this.field1850[var59] = 127;
               if (this.field1851[var59] != -1) {
                  var3 = true;
               }
            } else {
               this.field1866[var59] = -1;
               this.field1851[var59] = -1;
            }
         }

         if (var13 == 255) {
            this.field1865[var59] = var6.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var59] = var7.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var59] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var23 * -1095856699;
      var5.field6953 = var23 * -1095856699;
      int var60 = 0;
      int var63 = 0;
      int var65 = 0;
      int var66 = 0;

      for (int var67 = 0; var67 < var10; var67++) {
         int var46 = var5.method13095(-346779531);
         if (var46 == 1) {
            var60 = var4.method13146(-1537553239) + var66;
            var63 = var4.method13146(-1089015080) + var60;
            var65 = var4.method13146(-1660895507) + var63;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 2) {
            var63 = var65;
            var65 = var4.method13146(-695893318) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 3) {
            var60 = var65;
            var65 = var4.method13146(-168541258) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 4) {
            int var47 = var60;
            var60 = var63;
            var63 = var47;
            var65 = var4.method13146(-686313281) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var47;
            this.field1863[var67] = var65;
         }
      }

      var4.field6953 = var23 * -1095856699;

      for (int var68 = 0; var68 < var11; var68++) {
         this.field1859[var68] = 0;
         this.field1867[var68] = (short)var4.method13099(-1481512556);
         this.field1856[var68] = (short)var4.method13099(-1483053944);
         this.field1860[var68] = (short)var4.method13099(-1580398189);
      }

      var4.field6953 = var23 * -1095856699;
      boolean var69 = var4.method13095(-346779531) == 1;
      if (var69) {
         this.field1870 = new byte[var10];

         for (int var70 = 0; var70 < var10; var70++) {
            this.field1870[var70] = var4.method13097((byte)17);
         }
      }

      if (this.field1866 != null) {
         boolean var71 = false;

         for (int var72 = 0; var72 < var10; var72++) {
            int var48 = this.field1866[var72] & 255;
            if (var48 != 255) {
               if ((this.field1867[var48] & '\uffff') == this.field1878[var72]
                  && (this.field1856[var48] & '\uffff') == this.field1862[var72]
                  && (this.field1860[var48] & '\uffff') == this.field1863[var72]) {
                  this.field1866[var72] = -1;
               } else {
                  var71 = true;
               }
            }
         }

         if (!var71) {
            this.field1866 = null;
         }
      }

      if (!var3) {
         this.field1851 = null;
      }

      if (!var2) {
         this.field1864 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;[B)V")
   @ObfuscatedName("gq")
   public static void method3994(ModelData var0, byte[] var1) {
      if (var0 == null) {
         var0.method4025();
      } else {
         classXY var2 = new classXY(var1);
         classXY var3 = new classXY(var1);
         classXY var4 = new classXY(var1);
         classXY var5 = new classXY(var1);
         classXY var6 = new classXY(var1);
         classXY var7 = new classXY(var1);
         classXY var8 = new classXY(var1);
         var2.field6953 = (var1.length - 26) * -1312014050;
         int var9 = var2.method13099(943286945);
         int var10 = var2.method13099(-1832277032);
         int var11 = var2.method13095(-346779531);
         int var12 = var2.method13095(-346779531);
         int var13 = var2.method13095(-346779531);
         int var14 = var2.method13095(-346779531);
         int var15 = var2.method13095(-346779531);
         int var16 = var2.method13095(-346779531);
         int var17 = var2.method13095(-346779531);
         int var18 = var2.method13095(-346779531);
         int var19 = var2.method13099(-266792912);
         int var20 = var2.method13099(-973816511);
         int var21 = var2.method13099(-2097658870);
         int var22 = var2.method13099(995001239);
         int var23 = var2.method13099(-1544607665);
         int var24 = var2.method13099(1622989543);
         int var25 = 0;
         int var26 = 0;
         int var27 = 0;
         if (var11 > 0) {
            var0.field1859 = new byte[var11];
            var2.field6953 = 0;

            for (int var28 = 0; var28 < var11; var28++) {
               byte var29 = var0.field1859[var28] = var2.method13097((byte)17);
               if (var29 == 0) {
                  var25++;
               }

               if (var29 >= 1 && var29 <= 3) {
                  var26++;
               }

               if (var29 == 2) {
                  var27++;
               }
            }
         }

         int var60 = var11 + var9;
         int var30 = var60;
         if (var12 == 1) {
            var60 += var10;
         }

         var60 += var10;
         int var32 = var60;
         if (var13 == 255) {
            var60 += var10;
         }

         int var33 = var60;
         if (var15 == 1) {
            var60 += var10;
         }

         var60 += var24;
         int var35 = var60;
         if (var14 == 1) {
            var60 += var10;
         }

         var60 += var22;
         int var37 = var60;
         if (var16 == 1) {
            var60 += var10 * 2;
         }

         var60 += var23;
         var60 += var10 * 2;
         var60 += var19;
         var60 += var20;
         var60 += var21;
         var60 += var25 * 6;
         var60 += var26 * 6;
         var60 += var26 * 6;
         var60 += var26 * 2;
         var60 += var26;
         var60 += var26 * 2 + var27 * 2;
         var0.field1846 = var9;
         var0.field1847 = var10;
         var0.field1852 = var11;
         var0.field1853 = new float[var9];
         var0.field1854 = new float[var9];
         var0.field1855 = new float[var9];
         var0.field1878 = new int[var10];
         var0.field1862 = new int[var10];
         var0.field1863 = new int[var10];
         if (var17 == 1) {
            var0.field1868 = new int[var9];
         }

         if (var12 == 1) {
            var0.field1864 = new byte[var10];
         }

         if (var13 == -1198027113) {
            var0.field1865 = new byte[var10];
         } else {
            var0.field1849 = (byte)var13;
         }

         if (var14 == 1) {
            var0.field1857 = new byte[var10];
         }

         if (var15 == 1) {
            var0.field1869 = new int[var10];
         }

         if (var16 == 1) {
            var0.field1851 = new short[var10];
         }

         if (var16 == 1 && var11 > 0) {
            var0.field1866 = new byte[var10];
         }

         if (var18 == 1) {
            var0.field1874 = new int[var9][];
            var0.field1875 = new int[var9][];
         }

         var0.field1850 = new short[var10];
         if (var11 > 0) {
            var0.field1867 = new short[var11];
            var0.field1856 = new short[var11];
            var0.field1860 = new short[var11];
         }

         var2.field6953 = var11 * 1257410277;
         var3.field6953 = var60 * -1095856699;
         var4.field6953 = var60 * -1095856699;
         var5.field6953 = var60 * -1095856699;
         var6.field6953 = var60 * -1095856699;
         int var50 = 0;
         int var51 = 0;
         int var52 = 0;

         for (int var53 = 0; var53 < var9; var53++) {
            int var54 = var2.method13095(-346779531);
            int var55 = 0;
            if ((var54 & 1) != 0) {
               var55 = var3.method13146(-1266949422);
            }

            int var56 = 0;
            if ((var54 & 2) != 0) {
               var56 = var4.method13146(-357876484);
            }

            int var57 = 0;
            if ((var54 & 4) != 0) {
               var57 = var5.method13146(-700610924);
            }

            var0.field1853[var53] = var50 + var55;
            var0.field1854[var53] = var51 + var56;
            var0.field1855[var53] = var52 + var57;
            var50 = (int)var0.field1853[var53];
            var51 = (int)var0.field1854[var53];
            var52 = (int)var0.field1855[var53];
            if (var17 == 1) {
               var0.field1868[var53] = var6.method13095(-346779531);
            }
         }

         if (var18 == 1) {
            for (int var75 = 0; var75 < var9; var75++) {
               int var78 = var6.method13095(-346779531);
               var0.field1874[var75] = new int[var78];
               var0.field1875[var75] = new int[var78];

               for (int var80 = 0; var80 < var78; var80++) {
                  var0.field1874[var75][var80] = var6.method13095(-346779531);
                  var0.field1875[var75][var80] = var6.method13095(-346779531);
               }
            }
         }

         var2.field6953 = var60 * 64708594;
         var3.field6953 = var30 * 1155047495;
         var4.field6953 = var32 * -636543447;
         var5.field6953 = var35 * -1689854064;
         var6.field6953 = var33 * -1095856699;
         var7.field6953 = var37 * -1095856699;
         var8.field6953 = var60 * 64210140;

         for (int var76 = 0; var76 < var10; var76++) {
            var0.field1850[var76] = (short)var2.method13099(-729629920);
            if (var12 == 1) {
               var0.field1864[var76] = var3.method13097((byte)17);
            }

            if (var13 == 255) {
               var0.field1865[var76] = var4.method13097((byte)17);
            }

            if (var14 == 1) {
               var0.field1857[var76] = var5.method13097((byte)17);
            }

            if (var15 == 1) {
               var0.field1869[var76] = var6.method13095(-346779531);
            }

            if (var16 == 1) {
               var0.field1851[var76] = (short)(var7.method13099(-931554207) - 1);
            }

            if (var0.field1866 != null && var0.field1851[var76] != -1) {
               var0.field1866[var76] = (byte)(var8.method13095(-346779531) - 1);
            }
         }

         var2.field6953 = var60 * -1095856699;
         var3.field6953 = var60 * -1095856699;
         int var77 = 0;
         int var79 = 0;
         int var81 = 0;
         int var82 = 0;

         for (int var83 = 0; var83 < var10; var83++) {
            int var58 = var3.method13095(-346779531);
            if (var58 == 1) {
               var77 = var2.method13146(-1759890500) + var82;
               var79 = var2.method13146(-1901725482) + var77;
               var81 = var2.method13146(-37560037) + var79;
               var82 = var81;
               var0.field1878[var83] = var77;
               var0.field1862[var83] = var79;
               var0.field1863[var83] = var81;
            }

            if (var58 == 2) {
               var79 = var81;
               var81 = var2.method13146(-985378165) + var82;
               var82 = var81;
               var0.field1878[var83] = var77;
               var0.field1862[var83] = var79;
               var0.field1863[var83] = var81;
            }

            if (var58 == 3) {
               var77 = var81;
               var81 = var2.method13146(-40263168) + var82;
               var82 = var81;
               var0.field1878[var83] = var77;
               var0.field1862[var83] = var79;
               var0.field1863[var83] = var81;
            }

            if (var58 == 4) {
               int var59 = var77;
               var77 = var79;
               var79 = var59;
               var81 = var2.method13146(-1564399988) + var82;
               var82 = var81;
               var0.field1878[var83] = var77;
               var0.field1862[var83] = var59;
               var0.field1863[var83] = var81;
            }
         }

         var2.field6953 = var60 * 1752414164;
         var3.field6953 = var60 * -1241614583;
         var4.field6953 = var60 * -704567144;
         var5.field6953 = var60 * -2006696866;
         var6.field6953 = var60 * 1525379766;
         var7.field6953 = var60 * -1317083559;

         for (int var84 = 0; var84 < var11; var84++) {
            int var86 = var0.field1859[var84] & -2136465011;
            if (var86 == 0) {
               var0.field1867[var84] = (short)var2.method13099(610495080);
               var0.field1856[var84] = (short)var2.method13099(-1407439569);
               var0.field1860[var84] = (short)var2.method13099(-770670681);
            }
         }

         var2.field6953 = var60 * -1261238482;
         int var85 = var2.method13095(-346779531);
         if (var85 != 0) {
            new classFY();
            var2.method13099(-2128997857);
            var2.method13099(-1189846446);
            var2.method13099(-22901189);
            classXY.method13113(var2, (byte)1);
         }

         boolean var87 = var2.method13095(-346779531) == 1;
         if (var87) {
            var0.field1870 = new byte[var10];

            for (int var88 = 0; var88 < var10; var88++) {
               var0.field1870[var88] = var2.method13097((byte)17);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(SS)V")
   @ObfuscatedName("cg")
   public void method4030(short var1, short var2) {
      for (int var3 = 0; var3 < this.field1847; var3++) {
         if (this.field1850[var3] == var1) {
            this.field1850[var3] = var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;Ler;IIIZ)V")
   @ObfuscatedName("af")
   static void method3987(ModelData var0, ModelData var1, int var2, int var3, int var4, boolean var5) {
      var0.method4053();
      method4048(var0);
      var1.method4053();
      method4048(var1);
      field1880++;
      int var6 = 0;
      float[] var7 = var1.field1853;
      int var8 = var1.field1846;

      for (int var9 = 0; var9 < var0.field1846; var9++) {
         classFR var10 = var0.field1871[var9];
         if (var10.field2384 * 1200753713 != 0) {
            int var11 = (int)var0.field1854[var9] - var3;
            if (var11 <= var1.field1889) {
               int var12 = (int)var0.field1853[var9] - var2;
               if (var12 >= var1.field1887 && var12 <= var1.field1891) {
                  int var13 = (int)var0.field1855[var9] - var4;
                  if (var13 >= var1.field1888 && var13 <= var1.field1890) {
                     for (int var14 = 0; var14 < var8; var14++) {
                        classFR var15 = var1.field1871[var14];
                        if (var12 == (int)var7[var14]
                           && var13 == (int)var1.field1855[var14]
                           && var11 == (int)var1.field1854[var14]
                           && var15.field2384 * 1200753713 != 0) {
                           if (var0.field1873 == null) {
                              var0.field1873 = new classFR[var0.field1846];
                           }

                           if (var1.field1873 == null) {
                              var1.field1873 = new classFR[var8];
                           }

                           classFR var16 = var0.field1873[var9];
                           if (var16 == null) {
                              var16 = var0.field1873[var9] = new classFR(var10);
                           }

                           classFR var17 = var1.field1873[var14];
                           if (var17 == null) {
                              var17 = var1.field1873[var14] = new classFR(var15);
                           }

                           var16.field2386 = var16.field2386 + var15.field2386 * 1;
                           var16.field2385 = var16.field2385 + var15.field2385 * 1;
                           var16.field2383 = var16.field2383 + var15.field2383 * 1;
                           var16.field2384 = var16.field2384 + var15.field2384 * 1;
                           var17.field2386 = var17.field2386 + var10.field2386 * 1;
                           var17.field2385 = var17.field2385 + var10.field2385 * 1;
                           var17.field2383 = var17.field2383 + var10.field2383 * 1;
                           var17.field2384 = var17.field2384 + var10.field2384 * 1;
                           var6++;
                           field1879[var9] = field1880;
                           field1881[var14] = field1880;
                        }
                     }
                  }
               }
            }
         }
      }

      if (var6 >= 3 && var5) {
         for (int var18 = 0; var18 < var0.field1847; var18++) {
            if (field1879[var0.field1878[var18]] == field1880 && field1879[var0.field1862[var18]] == field1880 && field1879[var0.field1863[var18]] == field1880
               )
             {
               if (var0.field1864 == null) {
                  var0.field1864 = new byte[var0.field1847];
               }

               var0.field1864[var18] = 2;
            }
         }

         for (int var19 = 0; var19 < var1.field1847; var19++) {
            if (field1881[var1.field1878[var19]] == field1880 && field1881[var1.field1862[var19]] == field1880 && field1881[var1.field1863[var19]] == field1880
               )
             {
               if (var1.field1864 == null) {
                  var1.field1864 = new byte[var1.field1847];
               }

               var1.field1864[var19] = 2;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dt")
   void method4052() {
      if (!this.field1848) {
         this.field1741 = 0;
         this.field1889 = 0;
         this.field1887 = 1945163118;
         this.field1891 = -999999;
         this.field1890 = -99999;
         this.field1888 = 99999;

         for (int var1 = 0; var1 < this.field1846; var1++) {
            int var2 = (int)this.field1853[var1];
            int var3 = (int)this.field1854[var1];
            int var4 = (int)this.field1855[var1];
            if (var2 < this.field1887) {
               this.field1887 = var2;
            }

            if (var2 > this.field1891) {
               this.field1891 = var2;
            }

            if (var4 < this.field1888) {
               this.field1888 = var4;
            }

            if (var4 > this.field1890) {
               this.field1890 = var4;
            }

            if (-var3 > this.field1741 * 1684608448) {
               this.field1741 = -var3 * 874955715;
            }

            if (var3 > this.field1889) {
               this.field1889 = var3;
            }
         }

         this.field1848 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cd")
   void method4017() {
      if (this.field1868 != null) {
         int[] var1 = new int[256];
         int var2 = 0;

         for (int var3 = 0; var3 < this.field1846; var3++) {
            int var4 = this.field1868[var3];
            var1[var4]++;
            if (var4 > var2) {
               var2 = var4;
            }
         }

         this.field1861 = new int[var2 + 1][];

         for (int var7 = 0; var7 <= var2; var7++) {
            this.field1861[var7] = new int[var1[var7]];
            var1[var7] = 0;
         }

         int var8 = 0;

         while (var8 < this.field1846) {
            int var12 = this.field1868[var8];
            this.field1861[var12][var1[var12]++] = var8++;
         }

         this.field1868 = null;
      }

      if (this.field1869 != null) {
         int[] var5 = new int[568201891];
         int var6 = 0;

         for (int var9 = 0; var9 < this.field1847; var9++) {
            int var13 = this.field1869[var9];
            var5[var13]++;
            if (var13 > var6) {
               var6 = var13;
            }
         }

         this.field1858 = new int[var6 + 1][];

         for (int var10 = 0; var10 <= var6; var10++) {
            this.field1858[var10] = new int[var5[var10]];
            var5[var10] = 0;
         }

         int var11 = 0;

         while (var11 < this.field1847) {
            int var14 = this.field1869[var11];
            this.field1858[var14][var5[var14]++] = var11++;
         }

         this.field1869 = null;
      }
   }

   public short[] getFaceTextures() {
      return this.field1851;
   }

   @ObfuscatedSignature(descriptor = "(SS)V")
   @ObfuscatedName("br")
   public void method4031(short var1, short var2) {
      for (int var3 = 0; var3 < this.field1847; var3++) {
         if (this.field1850[var3] == var1) {
            this.field1850[var3] = var2;
         }
      }
   }

   public ModelData(ModelData var1, boolean var2, boolean var3, boolean var4, boolean var5) {
      this.field1847 = 0;
      this.field1849 = 0;
      this.field1848 = false;
      this.field1846 = var1.field1846;
      this.field1847 = var1.field1847;
      this.field1852 = var1.field1852;
      if (var2) {
         this.field1853 = var1.field1853;
         this.field1854 = var1.field1854;
         this.field1855 = var1.field1855;
      } else {
         this.field1853 = new float[this.field1846];
         this.field1854 = new float[this.field1846];
         this.field1855 = new float[this.field1846];

         for (int var6 = 0; var6 < this.field1846; var6++) {
            this.field1853[var6] = var1.field1853[var6];
            this.field1854[var6] = var1.field1854[var6];
            this.field1855[var6] = var1.field1855[var6];
         }
      }

      if (var3) {
         this.field1850 = var1.field1850;
      } else {
         this.field1850 = new short[this.field1847];

         for (int var7 = 0; var7 < this.field1847; var7++) {
            this.field1850[var7] = var1.field1850[var7];
         }
      }

      if (!var4 && var1.field1851 != null) {
         this.field1851 = new short[this.field1847];

         for (int var8 = 0; var8 < this.field1847; var8++) {
            this.field1851[var8] = var1.field1851[var8];
         }
      } else {
         this.field1851 = var1.field1851;
      }

      if (var5) {
         this.field1857 = var1.field1857;
      } else {
         this.field1857 = new byte[this.field1847];
         if (var1.field1857 == null) {
            for (int var9 = 0; var9 < this.field1847; var9++) {
               this.field1857[var9] = 0;
            }
         } else {
            for (int var10 = 0; var10 < this.field1847; var10++) {
               this.field1857[var10] = var1.field1857[var10];
            }
         }
      }

      this.field1878 = var1.field1878;
      this.field1862 = var1.field1862;
      this.field1863 = var1.field1863;
      this.field1864 = var1.field1864;
      this.field1865 = var1.field1865;
      this.field1866 = var1.field1866;
      this.field1849 = var1.field1849;
      this.field1859 = var1.field1859;
      this.field1867 = var1.field1867;
      this.field1856 = var1.field1856;
      this.field1860 = var1.field1860;
      this.field1868 = var1.field1868;
      this.field1869 = var1.field1869;
      this.field1861 = var1.field1861;
      this.field1858 = var1.field1858;
      this.field1871 = var1.field1871;
      this.field1872 = var1.field1872;
      this.field1873 = var1.field1873;
      this.field1874 = var1.field1874;
      this.field1875 = var1.field1875;
      this.field1876 = var1.field1876;
      this.field1877 = var1.field1877;
      this.field1870 = var1.field1870;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ao")
   void method4004(byte[] var1) {
      boolean var2 = false;
      boolean var3 = false;
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var4.field6953 = (var1.length - 18) * -1095856699;
      int var9 = var4.method13099(259951655);
      int var10 = var4.method13099(488098787);
      int var11 = var4.method13095(-346779531);
      int var12 = var4.method13095(-346779531);
      int var13 = var4.method13095(-346779531);
      int var14 = var4.method13095(-346779531);
      int var15 = var4.method13095(-346779531);
      int var16 = var4.method13095(-346779531);
      int var17 = var4.method13099(377229697);
      int var18 = var4.method13099(-222495383);
      int var19 = var4.method13099(-175409656);
      int var20 = var4.method13099(1857075666);
      int var21 = 0;
      var21 += var9;
      var21 += var10;
      int var24 = var21;
      if (var13 == 255) {
         var21 += var10;
      }

      int var25 = var21;
      if (var15 == 1) {
         var21 += var10;
      }

      int var26 = var21;
      if (var12 == 1) {
         var21 += var10;
      }

      int var27 = var21;
      if (var16 == 1) {
         var21 += var9;
      }

      int var28 = var21;
      if (var14 == 1) {
         var21 += var10;
      }

      var21 += var20;
      var21 += var10 * 2;
      var21 += var11 * 6;
      var21 += var17;
      var21 += var18;
      var21 += var19;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      if (var16 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
         this.field1866 = new byte[var10];
         this.field1851 = new short[var10];
      }

      if (var13 == 255) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      this.field1850 = new short[var10];
      var4.field6953 = var21 * -1095856699;
      var5.field6953 = var21 * -1095856699;
      var6.field6953 = var21 * -1095856699;
      var7.field6953 = var21 * -1095856699;
      var8.field6953 = var27 * -1095856699;
      int var35 = 0;
      int var36 = 0;
      int var37 = 0;

      for (int var38 = 0; var38 < var9; var38++) {
         int var39 = var4.method13095(-346779531);
         int var40 = 0;
         if ((var39 & 1) != 0) {
            var40 = var5.method13146(-90884172);
         }

         int var41 = 0;
         if ((var39 & 2) != 0) {
            var41 = var6.method13146(-7497088);
         }

         int var42 = 0;
         if ((var39 & 4) != 0) {
            var42 = var7.method13146(-558765863);
         }

         this.field1853[var38] = var35 + var40;
         this.field1854[var38] = var36 + var41;
         this.field1855[var38] = var37 + var42;
         var35 = (int)this.field1853[var38];
         var36 = (int)this.field1854[var38];
         var37 = (int)this.field1855[var38];
         if (var16 == 1) {
            this.field1868[var38] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var21 * -1095856699;
      var5.field6953 = var26 * -1095856699;
      var6.field6953 = var24 * -1095856699;
      var7.field6953 = var28 * -1095856699;
      var8.field6953 = var25 * -1095856699;

      for (int var53 = 0; var53 < var10; var53++) {
         this.field1850[var53] = (short)var4.method13099(270460424);
         if (var12 == 1) {
            int var55 = var5.method13095(-346779531);
            if ((var55 & 1) == 1) {
               this.field1864[var53] = 1;
               var2 = true;
            } else {
               this.field1864[var53] = 0;
            }

            if ((var55 & 2) == 2) {
               this.field1866[var53] = (byte)(var55 >> 2);
               this.field1851[var53] = this.field1850[var53];
               this.field1850[var53] = 127;
               if (this.field1851[var53] != -1) {
                  var3 = true;
               }
            } else {
               this.field1866[var53] = -1;
               this.field1851[var53] = -1;
            }
         }

         if (var13 == 255) {
            this.field1865[var53] = var6.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var53] = var7.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var53] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var21 * -1095856699;
      var5.field6953 = var21 * -1095856699;
      int var54 = 0;
      int var56 = 0;
      int var57 = 0;
      int var58 = 0;

      for (int var59 = 0; var59 < var10; var59++) {
         int var43 = var5.method13095(-346779531);
         if (var43 == 1) {
            var54 = var4.method13146(-1742133160) + var58;
            var56 = var4.method13146(-1312021698) + var54;
            var57 = var4.method13146(-1825470473) + var56;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var56;
            this.field1863[var59] = var57;
         }

         if (var43 == 2) {
            var56 = var57;
            var57 = var4.method13146(-51449) + var58;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var56;
            this.field1863[var59] = var57;
         }

         if (var43 == 3) {
            var54 = var57;
            var57 = var4.method13146(-609148183) + var58;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var56;
            this.field1863[var59] = var57;
         }

         if (var43 == 4) {
            int var44 = var54;
            var54 = var56;
            var56 = var44;
            var57 = var4.method13146(-1582619706) + var58;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var44;
            this.field1863[var59] = var57;
         }
      }

      var4.field6953 = var21 * -1095856699;

      for (int var60 = 0; var60 < var11; var60++) {
         this.field1859[var60] = 0;
         this.field1867[var60] = (short)var4.method13099(317544948);
         this.field1856[var60] = (short)var4.method13099(-1756873900);
         this.field1860[var60] = (short)var4.method13099(851920230);
      }

      if (this.field1866 != null) {
         boolean var61 = false;

         for (int var62 = 0; var62 < var10; var62++) {
            int var63 = this.field1866[var62] & 255;
            if (var63 != 255) {
               if ((this.field1867[var63] & '\uffff') == this.field1878[var62]
                  && (this.field1856[var63] & '\uffff') == this.field1862[var62]
                  && (this.field1860[var63] & '\uffff') == this.field1863[var62]) {
                  this.field1866[var62] = -1;
               } else {
                  var61 = true;
               }
            }
         }

         if (!var61) {
            this.field1866 = null;
         }
      }

      if (!var3) {
         this.field1851 = null;
      }

      if (!var2) {
         this.field1864 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("ok")
   public short[] method4081() {
      return this.field1856;
   }

   @ObfuscatedSignature(descriptor = "(Ler;I)I")
   @ObfuscatedName("aj")
   final int method4007(ModelData var1, int var2) {
      int var3 = -1;
      int var4 = (int)var1.field1853[var2];
      int var5 = (int)var1.field1854[var2];
      int var6 = (int)var1.field1855[var2];

      for (int var7 = 0; var7 < this.field1846; var7++) {
         if (var4 == (int)this.field1853[var7] && var5 == (int)this.field1854[var7] && var6 == (int)this.field1855[var7]) {
            var3 = var7;
            break;
         }
      }

      if (var3 == -1) {
         this.field1853[this.field1846] = var4;
         this.field1854[this.field1846] = var5;
         this.field1855[this.field1846] = var6;
         if (var1.field1868 != null) {
            this.field1868[this.field1846] = var1.field1868[var2];
         }

         if (var1.field1874 != null) {
            this.field1874[this.field1846] = var1.field1874[var2];
            this.field1875[this.field1846] = var1.field1875[var2];
         }

         var3 = this.field1846++;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Ler;Ler;IIIZ)V")
   @ObfuscatedName("xj")
   public static void method4082(ModelData var0, ModelData var1, int var2, int var3, int var4, boolean var5) {
      method3987(var0, var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ay")
   public ModelData method4010() {
      ModelData var1 = new ModelData();
      if (this.field1864 != null) {
         var1.field1864 = new byte[this.field1847];

         for (int var2 = 0; var2 < this.field1847; var2++) {
            var1.field1864[var2] = this.field1864[var2];
         }
      }

      var1.field1846 = this.field1846;
      var1.field1847 = this.field1847;
      var1.field1852 = this.field1852;
      var1.field1853 = this.field1853;
      var1.field1854 = this.field1854;
      var1.field1855 = this.field1855;
      var1.field1878 = this.field1878;
      var1.field1862 = this.field1862;
      var1.field1863 = this.field1863;
      var1.field1865 = this.field1865;
      var1.field1857 = this.field1857;
      var1.field1866 = this.field1866;
      var1.field1850 = this.field1850;
      var1.field1851 = this.field1851;
      var1.field1849 = this.field1849;
      var1.field1859 = this.field1859;
      var1.field1867 = this.field1867;
      var1.field1856 = this.field1856;
      var1.field1860 = this.field1860;
      var1.field1868 = this.field1868;
      var1.field1869 = this.field1869;
      var1.field1861 = this.field1861;
      var1.field1858 = this.field1858;
      var1.field1871 = this.field1871;
      var1.field1872 = this.field1872;
      var1.field1876 = this.field1876;
      var1.field1877 = this.field1877;
      var1.field1870 = this.field1870;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("sf")
   public short[] method4083() {
      return this.field1860;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   void method4018() {
      if (this.field1868 != null) {
         int[] var1 = new int[256];
         int var2 = 0;

         for (int var3 = 0; var3 < this.field1846; var3++) {
            int var4 = this.field1868[var3];
            var1[var4]++;
            if (var4 > var2) {
               var2 = var4;
            }
         }

         this.field1861 = new int[var2 + 1][];

         for (int var7 = 0; var7 <= var2; var7++) {
            this.field1861[var7] = new int[var1[var7]];
            var1[var7] = 0;
         }

         int var8 = 0;

         while (var8 < this.field1846) {
            int var12 = this.field1868[var8];
            this.field1861[var12][var1[var12]++] = var8++;
         }

         this.field1868 = null;
      }

      if (this.field1869 != null) {
         int[] var5 = new int[256];
         int var6 = 0;

         for (int var9 = 0; var9 < this.field1847; var9++) {
            int var13 = this.field1869[var9];
            var5[var13]++;
            if (var13 > var6) {
               var6 = var13;
            }
         }

         this.field1858 = new int[var6 + 1][];

         for (int var10 = 0; var10 <= var6; var10++) {
            this.field1858[var10] = new int[var5[var10]];
            var5[var10] = 0;
         }

         int var11 = 0;

         while (var11 < this.field1847) {
            int var14 = this.field1869[var11];
            this.field1858[var14][var5[var14]++] = var11++;
         }

         this.field1869 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("az")
   public ModelData method4071() {
      return this.method4077(false);
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("dn")
   public final Model method4062() {
      return this.method4070(-890625137, 43690, 0, -1, 0);
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bp")
   public void method4072(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field1846; var4++) {
         this.field1853[var4] = this.field1853[var4] + var1;
         this.field1854[var4] = this.field1854[var4] + var2;
         this.field1855[var4] = this.field1855[var4] + var3;
      }

      this.method4084();
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("as")
   static final int method3989(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public void method4084() {
      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "(SS)V")
   @ObfuscatedName("bm")
   public void method4035(short var1, short var2) {
      if (this.field1851 != null) {
         for (int var3 = 0; var3 < this.field1847; var3++) {
            if (this.field1851[var3] == var1) {
               this.field1851[var3] = var2;
            }
         }
      }
   }

   ModelData() {
      this.field1847 = 0;
      this.field1849 = 0;
      this.field1848 = false;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   static final int method3992(int var0) {
      if (var0 < 2) {
         var0 = 2;
      } else if (var0 > 126) {
         var0 = 126;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bx")
   public void method4073() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         float var2 = this.field1853[var1];
         this.field1853[var1] = this.field1855[var1];
         this.field1855[var1] = -var2;
      }

      this.method4084();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bo")
   void method4049() {
      this.field1871 = null;
      this.field1873 = null;
      this.field1872 = null;
      this.field1848 = false;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("aa")
   void method3995(byte[] var1) {
      classXY var2 = new classXY(var1);
      classXY var3 = new classXY(var1);
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var2.field6953 = (var1.length - 26) * -1095856699;
      int var9 = var2.method13099(1277798693);
      int var10 = var2.method13099(-1655556399);
      int var11 = var2.method13095(-346779531);
      int var12 = var2.method13095(-346779531);
      int var13 = var2.method13095(-346779531);
      int var14 = var2.method13095(-346779531);
      int var15 = var2.method13095(-346779531);
      int var16 = var2.method13095(-346779531);
      int var17 = var2.method13095(-346779531);
      int var18 = var2.method13095(-346779531);
      int var19 = var2.method13099(-1724857162);
      int var20 = var2.method13099(1158831635);
      int var21 = var2.method13099(1454202988);
      int var22 = var2.method13099(-1210610823);
      int var23 = var2.method13099(-1120978296);
      int var24 = var2.method13099(1455100402);
      int var25 = 0;
      int var26 = 0;
      int var27 = 0;
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         var2.field6953 = 0;

         for (int var28 = 0; var28 < var11; var28++) {
            byte var29 = this.field1859[var28] = var2.method13097((byte)17);
            if (var29 == 0) {
               var25++;
            }

            if (var29 >= 1 && var29 <= 3) {
               var26++;
            }

            if (var29 == 2) {
               var27++;
            }
         }
      }

      int var60 = var11 + var9;
      int var30 = var60;
      if (var12 == 1) {
         var60 += var10;
      }

      var60 += var10;
      int var32 = var60;
      if (var13 == 255) {
         var60 += var10;
      }

      int var33 = var60;
      if (var15 == 1) {
         var60 += var10;
      }

      var60 += var24;
      int var35 = var60;
      if (var14 == 1) {
         var60 += var10;
      }

      var60 += var22;
      int var37 = var60;
      if (var16 == 1) {
         var60 += var10 * 2;
      }

      var60 += var23;
      var60 += var10 * 2;
      var60 += var19;
      var60 += var20;
      var60 += var21;
      var60 += var25 * 6;
      var60 += var26 * 6;
      var60 += var26 * 6;
      var60 += var26 * 2;
      var60 += var26;
      var60 += var26 * 2 + var27 * 2;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var17 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
      }

      if (var13 == 255) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      if (var16 == 1) {
         this.field1851 = new short[var10];
      }

      if (var16 == 1 && var11 > 0) {
         this.field1866 = new byte[var10];
      }

      if (var18 == 1) {
         this.field1874 = new int[var9][];
         this.field1875 = new int[var9][];
      }

      this.field1850 = new short[var10];
      if (var11 > 0) {
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      var2.field6953 = var11 * -1095856699;
      var3.field6953 = var60 * -1095856699;
      var4.field6953 = var60 * -1095856699;
      var5.field6953 = var60 * -1095856699;
      var6.field6953 = var60 * -1095856699;
      int var50 = 0;
      int var51 = 0;
      int var52 = 0;

      for (int var53 = 0; var53 < var9; var53++) {
         int var54 = var2.method13095(-346779531);
         int var55 = 0;
         if ((var54 & 1) != 0) {
            var55 = var3.method13146(-2019001641);
         }

         int var56 = 0;
         if ((var54 & 2) != 0) {
            var56 = var4.method13146(-1998852412);
         }

         int var57 = 0;
         if ((var54 & 4) != 0) {
            var57 = var5.method13146(-26943008);
         }

         this.field1853[var53] = var50 + var55;
         this.field1854[var53] = var51 + var56;
         this.field1855[var53] = var52 + var57;
         var50 = (int)this.field1853[var53];
         var51 = (int)this.field1854[var53];
         var52 = (int)this.field1855[var53];
         if (var17 == 1) {
            this.field1868[var53] = var6.method13095(-346779531);
         }
      }

      if (var18 == 1) {
         for (int var75 = 0; var75 < var9; var75++) {
            int var78 = var6.method13095(-346779531);
            this.field1874[var75] = new int[var78];
            this.field1875[var75] = new int[var78];

            for (int var80 = 0; var80 < var78; var80++) {
               this.field1874[var75][var80] = var6.method13095(-346779531);
               this.field1875[var75][var80] = var6.method13095(-346779531);
            }
         }
      }

      var2.field6953 = var60 * -1095856699;
      var3.field6953 = var30 * -1095856699;
      var4.field6953 = var32 * -1095856699;
      var5.field6953 = var35 * -1095856699;
      var6.field6953 = var33 * -1095856699;
      var7.field6953 = var37 * -1095856699;
      var8.field6953 = var60 * -1095856699;

      for (int var76 = 0; var76 < var10; var76++) {
         this.field1850[var76] = (short)var2.method13099(1109783010);
         if (var12 == 1) {
            this.field1864[var76] = var3.method13097((byte)17);
         }

         if (var13 == 255) {
            this.field1865[var76] = var4.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var76] = var5.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var76] = var6.method13095(-346779531);
         }

         if (var16 == 1) {
            this.field1851[var76] = (short)(var7.method13099(1418114394) - 1);
         }

         if (this.field1866 != null && this.field1851[var76] != -1) {
            this.field1866[var76] = (byte)(var8.method13095(-346779531) - 1);
         }
      }

      var2.field6953 = var60 * -1095856699;
      var3.field6953 = var60 * -1095856699;
      int var77 = 0;
      int var79 = 0;
      int var81 = 0;
      int var82 = 0;

      for (int var83 = 0; var83 < var10; var83++) {
         int var58 = var3.method13095(-346779531);
         if (var58 == 1) {
            var77 = var2.method13146(-820866785) + var82;
            var79 = var2.method13146(-1269051955) + var77;
            var81 = var2.method13146(-531881062) + var79;
            var82 = var81;
            this.field1878[var83] = var77;
            this.field1862[var83] = var79;
            this.field1863[var83] = var81;
         }

         if (var58 == 2) {
            var79 = var81;
            var81 = var2.method13146(-371137809) + var82;
            var82 = var81;
            this.field1878[var83] = var77;
            this.field1862[var83] = var79;
            this.field1863[var83] = var81;
         }

         if (var58 == 3) {
            var77 = var81;
            var81 = var2.method13146(-2091946271) + var82;
            var82 = var81;
            this.field1878[var83] = var77;
            this.field1862[var83] = var79;
            this.field1863[var83] = var81;
         }

         if (var58 == 4) {
            int var59 = var77;
            var77 = var79;
            var79 = var59;
            var81 = var2.method13146(-1564569824) + var82;
            var82 = var81;
            this.field1878[var83] = var77;
            this.field1862[var83] = var59;
            this.field1863[var83] = var81;
         }
      }

      var2.field6953 = var60 * -1095856699;
      var3.field6953 = var60 * -1095856699;
      var4.field6953 = var60 * -1095856699;
      var5.field6953 = var60 * -1095856699;
      var6.field6953 = var60 * -1095856699;
      var7.field6953 = var60 * -1095856699;

      for (int var84 = 0; var84 < var11; var84++) {
         int var86 = this.field1859[var84] & 255;
         if (var86 == 0) {
            this.field1867[var84] = (short)var2.method13099(-1784628299);
            this.field1856[var84] = (short)var2.method13099(1486773971);
            this.field1860[var84] = (short)var2.method13099(-73506983);
         }
      }

      var2.field6953 = var60 * -1095856699;
      int var85 = var2.method13095(-346779531);
      if (var85 != 0) {
         new classFY();
         var2.method13099(403106955);
         var2.method13099(1934831337);
         var2.method13099(1865626131);
         classXY.method13113(var2, (byte)1);
      }

      boolean var87 = var2.method13095(-346779531) == 1;
      if (var87) {
         this.field1870 = new byte[var10];

         for (int var88 = 0; var88 < var10; var88++) {
            this.field1870[var88] = var2.method13097((byte)17);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("uq")
   public short[] method4085() {
      return this.field1867;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Lfx;")
   @ObfuscatedName("ba")
   public final Model method4070(int var1, int var2, int var3, int var4, int var5) {
      client.field1028.trace("Lighting model {}", this);
      int var12 = var5;
      int var11 = var4;
      int var10 = var3;
      int var8 = var1;
      ModelData var7 = this;
      method4048(this);
      int var13 = (int)Math.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
      int var14 = var2 * var13 >> 8;
      Model var15 = new Model();
      var15.field2432 = new int[this.field1847];
      var15.field2426 = new int[this.field1847];
      var15.field2433 = new int[this.field1847];
      if (this.field1852 > 0 && this.field1866 != null) {
         int[] var16 = new int[this.field1852];

         for (int var17 = 0; var17 < var7.field1847; var17++) {
            if (var7.field1866[var17] != -1) {
               var16[var7.field1866[var17] & 255]++;
            }
         }

         var15.field2416 = 0;

         for (int var24 = 0; var24 < var7.field1852; var24++) {
            if (var16[var24] > 0 && var7.field1859[var24] == 0) {
               var15.field2416++;
            }
         }

         var15.field2437 = new int[var15.field2416];
         var15.field2438 = new int[var15.field2416];
         var15.field2439 = new int[var15.field2416];
         int var25 = 0;

         for (int var18 = 0; var18 < var7.field1852; var18++) {
            if (var16[var18] > 0 && var7.field1859[var18] == 0) {
               var15.field2437[var25] = var7.field1867[var18] & '\uffff';
               var15.field2438[var25] = var7.field1856[var18] & '\uffff';
               var15.field2439[var25] = var7.field1860[var18] & '\uffff';
               var16[var18] = var25++;
            } else {
               var16[var18] = -1;
            }
         }

         var15.field2434 = new byte[var7.field1847];

         for (int var27 = 0; var27 < var7.field1847; var27++) {
            if (var7.field1866[var27] != -1) {
               var15.field2434[var27] = (byte)var16[var7.field1866[var27] & 255];
            } else {
               var15.field2434[var27] = -1;
            }
         }
      }

      for (int var23 = 0; var23 < var7.field1847; var23++) {
         byte var26;
         if (var7.field1864 == null) {
            var26 = 0;
         } else {
            var26 = var7.field1864[var23];
         }

         byte var28;
         if (var7.field1857 == null) {
            var28 = 0;
         } else {
            var28 = var7.field1857[var23];
         }

         short var19;
         if (var7.field1851 == null) {
            var19 = -1;
         } else {
            var19 = var7.field1851[var23];
         }

         if (var28 == -2) {
            var26 = 3;
         }

         if (var28 == -1) {
            var26 = 2;
         }

         if (var19 == -1) {
            if (var26 == 0) {
               int var22 = var7.field1850[var23] & '\uffff';
               classFR var20;
               if (var7.field1873 != null && var7.field1873[var7.field1878[var23]] != null) {
                  var20 = var7.field1873[var7.field1878[var23]];
               } else {
                  var20 = var7.field1871[var7.field1878[var23]];
               }

               int var21 = var8
                  + (var10 * var20.field2386 * -1174074983 + var11 * var20.field2385 * -794112439 + var12 * var20.field2383 * -949378689)
                     / (var14 * var20.field2384 * 1200753713);
               var15.field2432[var23] = method3989(var22, var21);
               if (var7.field1873 != null && var7.field1873[var7.field1862[var23]] != null) {
                  var20 = var7.field1873[var7.field1862[var23]];
               } else {
                  var20 = var7.field1871[var7.field1862[var23]];
               }

               var21 = var8
                  + (var10 * var20.field2386 * -1174074983 + var11 * var20.field2385 * -794112439 + var12 * var20.field2383 * -949378689)
                     / (var14 * var20.field2384 * 1200753713);
               var15.field2426[var23] = method3989(var22, var21);
               if (var7.field1873 != null && var7.field1873[var7.field1863[var23]] != null) {
                  var20 = var7.field1873[var7.field1863[var23]];
               } else {
                  var20 = var7.field1871[var7.field1863[var23]];
               }

               var21 = var8
                  + (var10 * var20.field2386 * -1174074983 + var11 * var20.field2385 * -794112439 + var12 * var20.field2383 * -949378689)
                     / (var14 * var20.field2384 * 1200753713);
               var15.field2433[var23] = method3989(var22, var21);
            } else if (var26 == 1) {
               classEB var31 = var7.field1872[var23];
               int var38 = var8
                  + (var10 * var31.field1722 * 835925423 + var11 * var31.field1718 * 696545605 + var12 * var31.field1717 * -1298928853) / (var14 + var14 / 2);
               var15.field2432[var23] = method3989(var7.field1850[var23] & '\uffff', var38);
               var15.field2433[var23] = -1;
            } else if (var26 == 3) {
               var15.field2432[var23] = 128;
               var15.field2433[var23] = -1;
            } else {
               var15.field2433[var23] = -2;
            }
         } else if (var26 == 0) {
            classFR var32;
            if (var7.field1873 != null && var7.field1873[var7.field1878[var23]] != null) {
               var32 = var7.field1873[var7.field1878[var23]];
            } else {
               var32 = var7.field1871[var7.field1878[var23]];
            }

            int var39 = var8
               + (var10 * var32.field2386 * -1174074983 + var11 * var32.field2385 * -794112439 + var12 * var32.field2383 * -949378689)
                  / (var14 * var32.field2384 * 1200753713);
            var15.field2432[var23] = method3992(var39);
            if (var7.field1873 != null && var7.field1873[var7.field1862[var23]] != null) {
               var32 = var7.field1873[var7.field1862[var23]];
            } else {
               var32 = var7.field1871[var7.field1862[var23]];
            }

            var39 = var8
               + (var10 * var32.field2386 * -1174074983 + var11 * var32.field2385 * -794112439 + var12 * var32.field2383 * -949378689)
                  / (var14 * var32.field2384 * 1200753713);
            var15.field2426[var23] = method3992(var39);
            if (var7.field1873 != null && var7.field1873[var7.field1863[var23]] != null) {
               var32 = var7.field1873[var7.field1863[var23]];
            } else {
               var32 = var7.field1871[var7.field1863[var23]];
            }

            var39 = var8
               + (var10 * var32.field2386 * -1174074983 + var11 * var32.field2385 * -794112439 + var12 * var32.field2383 * -949378689)
                  / (var14 * var32.field2384 * 1200753713);
            var15.field2433[var23] = method3992(var39);
         } else if (var26 == 1) {
            classEB var35 = var7.field1872[var23];
            int var42 = var8
               + (var10 * var35.field1722 * 835925423 + var11 * var35.field1718 * 696545605 + var12 * var35.field1717 * -1298928853) / (var14 + var14 / 2);
            var15.field2432[var23] = method3992(var42);
            var15.field2433[var23] = -1;
         } else {
            var15.field2433[var23] = -2;
         }
      }

      var7.method4018();
      var15.field2422 = var7.field1846;
      var15.field2425 = var7.field1853;
      var15.field2428 = var7.field1854;
      var15.field2424 = var7.field1855;
      var15.field2417 = var7.field1847;
      var15.field2427 = var7.field1878;
      var15.field2430 = var7.field1862;
      var15.field2431 = var7.field1863;
      var15.field2429 = var7.field1865;
      var15.field2442 = var7.field1857;
      var15.field2418 = var7.field1849;
      var15.field2440 = var7.field1861;
      var15.field2441 = var7.field1858;
      var15.field2435 = var7.field1851;
      var15.field2506 = var7.field1874;
      var15.field2505 = var7.field1875;
      var15.field2436 = var7.field1870;
      if (var15 == null) {
         return null;
      } else {
         if ((client.field1021 & 4) == 4) {
            this.method4069();
            var15.field2501 = this.field1897;
            var15.field2503 = this.field1896;
            var15.field2502 = this.field1895;
         }

         if ((client.field1021 & 64) == 64) {
            var15.field2504 = this.field1850;
         }

         return var15;
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("no")
   public byte[] method4086() {
      return this.field1866;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("bw")
   public final Model method4063() {
      return this.method4070(128, 43690, 0, -1, 0);
   }

   public int[] getFaceIndices3() {
      return this.field1863;
   }

   @ObfuscatedSignature(descriptor = "(Z)Ler;")
   @ObfuscatedName("gd")
   public ModelData method4077(boolean var1) {
      if (this.field1857 != null) {
         this.field1857 = (byte[])this.field1857.clone();
      } else if (var1) {
         this.field1857 = new byte[this.getFaceCount()];
      }

      return this;
   }

   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("ga")
   public static void method4078(ModelData var0) {
      if (var0 == null) {
         var0.method4057();
      } else {
         for (int var1 = 0; var1 < var0.field1846; var1++) {
            var0.field1853[var1] = -var0.field1853[var1];
            var0.field1855[var1] = -var0.field1855[var1];
         }

         var0.method4084();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("de")
   public final Model method4064() {
      return this.method4070(128, 43690, 0, -1, 0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;II)Ler;")
   @ObfuscatedName("bb")
   public static ModelData method3984(IndexDataBase var0, int var1, int var2) {
      byte[] var3 = var0.method11942(var1, var2, -1949070972);
      return var3 == null ? null : new ModelData(var3);
   }

   @ObfuscatedSignature(descriptor = "(Lva;II)Ler;")
   @ObfuscatedName("bi")
   public static ModelData method3985(IndexDataBase var0, int var1, int var2) {
      byte[] var3 = var0.method11942(var1, var2, -1706517238);
      return var3 == null ? null : new ModelData(var3);
   }

   @ObfuscatedSignature(descriptor = "(Lva;II)Ler;")
   @ObfuscatedName("be")
   public static ModelData method3986(IndexDataBase var0, int var1, int var2) {
      byte[] var3 = var0.method11942(var1, var2, -789070626);
      return var3 == null ? null : new ModelData(var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;[B)V")
   @ObfuscatedName("wc")
   public static void method4001(ModelData var0, byte[] var1) {
      if (var0 == null) {
         var0.method4053();
      }

      classXY var2 = new classXY(var1);
      classXY var3 = new classXY(var1);
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var2.field6953 = (var1.length - 23) * -1095856699;
      int var9 = var2.method13099(1971080851);
      int var10 = var2.method13099(-665527169);
      int var11 = var2.method13095(-346779531);
      int var12 = var2.method13095(-346779531);
      int var13 = var2.method13095(-346779531);
      int var14 = var2.method13095(-346779531);
      int var15 = var2.method13095(-346779531);
      int var16 = var2.method13095(-346779531);
      int var17 = var2.method13095(-346779531);
      int var18 = var2.method13099(-560916877);
      int var19 = var2.method13099(1655697284);
      int var20 = var2.method13099(-728015589);
      int var21 = var2.method13099(1658603141);
      int var22 = var2.method13099(1156011406);
      int var23 = 0;
      int var24 = 0;
      int var25 = 0;
      if (var11 > 0) {
         var0.field1859 = new byte[var11];
         var2.field6953 = 0;

         for (int var26 = 0; var26 < var11; var26++) {
            byte var27 = var0.field1859[var26] = var2.method13097((byte)17);
            if (var27 == 0) {
               var23++;
            }

            if (var27 >= 1 && var27 <= 3) {
               var24++;
            }

            if (var27 == 2) {
               var25++;
            }
         }
      }

      int var58 = var11 + var9;
      int var28 = var58;
      if (var12 == 1) {
         var58 += var10;
      }

      var58 += var10;
      int var30 = var58;
      if (var13 == 255) {
         var58 += var10;
      }

      int var31 = var58;
      if (var15 == 1) {
         var58 += var10;
      }

      int var32 = var58;
      if (var17 == 1) {
         var58 += var9;
      }

      int var33 = var58;
      if (var14 == 1) {
         var58 += var10;
      }

      var58 += var21;
      int var35 = var58;
      if (var16 == 1) {
         var58 += var10 * 2;
      }

      var58 += var22;
      var58 += var10 * 2;
      var58 += var18;
      var58 += var19;
      var58 += var20;
      var58 += var23 * 6;
      var58 += var24 * 6;
      var58 += var24 * 6;
      var58 += var24 * 2;
      var58 += var24;
      var58 += var24 * 2 + var25 * 2;
      var0.field1846 = var9;
      var0.field1847 = var10;
      var0.field1852 = var11;
      var0.field1853 = new float[var9];
      var0.field1854 = new float[var9];
      var0.field1855 = new float[var9];
      var0.field1878 = new int[var10];
      var0.field1862 = new int[var10];
      var0.field1863 = new int[var10];
      if (var17 == 1) {
         var0.field1868 = new int[var9];
      }

      if (var12 == 1) {
         var0.field1864 = new byte[var10];
      }

      if (var13 == 255) {
         var0.field1865 = new byte[var10];
      } else {
         var0.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         var0.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         var0.field1869 = new int[var10];
      }

      if (var16 == 1) {
         var0.field1851 = new short[var10];
      }

      if (var16 == 1 && var11 > 0) {
         var0.field1866 = new byte[var10];
      }

      var0.field1850 = new short[var10];
      if (var11 > 0) {
         var0.field1867 = new short[var11];
         var0.field1856 = new short[var11];
         var0.field1860 = new short[var11];
      }

      var2.field6953 = var11 * -1095856699;
      var3.field6953 = var58 * -1095856699;
      var4.field6953 = var58 * -1095856699;
      var5.field6953 = var58 * -1095856699;
      var6.field6953 = var32 * -1095856699;
      int var48 = 0;
      int var49 = 0;
      int var50 = 0;

      for (int var51 = 0; var51 < var9; var51++) {
         int var52 = var2.method13095(-346779531);
         int var53 = 0;
         if ((var52 & 1) != 0) {
            var53 = var3.method13146(-386225327);
         }

         int var54 = 0;
         if ((var52 & 2) != 0) {
            var54 = var4.method13146(-1940783975);
         }

         int var55 = 0;
         if ((var52 & 4) != 0) {
            var55 = var5.method13146(-21021359);
         }

         var0.field1853[var51] = var48 + var53;
         var0.field1854[var51] = var49 + var54;
         var0.field1855[var51] = var50 + var55;
         var48 = (int)var0.field1853[var51];
         var49 = (int)var0.field1854[var51];
         var50 = (int)var0.field1855[var51];
         if (var17 == 1) {
            var0.field1868[var51] = var6.method13095(-346779531);
         }
      }

      var2.field6953 = var58 * -1095856699;
      var3.field6953 = var28 * -1095856699;
      var4.field6953 = var30 * -1095856699;
      var5.field6953 = var33 * -1095856699;
      var6.field6953 = var31 * -1095856699;
      var7.field6953 = var35 * -1095856699;
      var8.field6953 = var58 * -1095856699;

      for (int var72 = 0; var72 < var10; var72++) {
         var0.field1850[var72] = (short)var2.method13099(-853462108);
         if (var12 == 1) {
            var0.field1864[var72] = var3.method13097((byte)17);
         }

         if (var13 == 255) {
            var0.field1865[var72] = var4.method13097((byte)17);
         }

         if (var14 == 1) {
            var0.field1857[var72] = var5.method13097((byte)17);
         }

         if (var15 == 1) {
            var0.field1869[var72] = var6.method13095(-346779531);
         }

         if (var16 == 1) {
            var0.field1851[var72] = (short)(var7.method13099(340792080) - 1);
         }

         if (var0.field1866 != null && var0.field1851[var72] != -1) {
            var0.field1866[var72] = (byte)(var8.method13095(-346779531) - 1);
         }
      }

      var2.field6953 = var58 * -1095856699;
      var3.field6953 = var58 * -1095856699;
      int var73 = 0;
      int var74 = 0;
      int var75 = 0;
      int var76 = 0;

      for (int var77 = 0; var77 < var10; var77++) {
         int var56 = var3.method13095(-346779531);
         if (var56 == 1) {
            var73 = var2.method13146(-1728352149) + var76;
            var74 = var2.method13146(-623581729) + var73;
            var75 = var2.method13146(-1049427202) + var74;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var74;
            var0.field1863[var77] = var75;
         }

         if (var56 == 2) {
            var74 = var75;
            var75 = var2.method13146(-1942578595) + var76;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var74;
            var0.field1863[var77] = var75;
         }

         if (var56 == 3) {
            var73 = var75;
            var75 = var2.method13146(-1311174308) + var76;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var74;
            var0.field1863[var77] = var75;
         }

         if (var56 == 4) {
            int var57 = var73;
            var73 = var74;
            var74 = var57;
            var75 = var2.method13146(-543361082) + var76;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var57;
            var0.field1863[var77] = var75;
         }
      }

      var2.field6953 = var58 * -1095856699;
      var3.field6953 = var58 * -1095856699;
      var4.field6953 = var58 * -1095856699;
      var5.field6953 = var58 * -1095856699;
      var6.field6953 = var58 * -1095856699;
      var7.field6953 = var58 * -1095856699;

      for (int var78 = 0; var78 < var11; var78++) {
         int var80 = var0.field1859[var78] & 255;
         if (var80 == 0) {
            var0.field1867[var78] = (short)var2.method13099(150452590);
            var0.field1856[var78] = (short)var2.method13099(998773603);
            var0.field1860[var78] = (short)var2.method13099(1242794812);
         }
      }

      var2.field6953 = var58 * -1095856699;
      int var79 = var2.method13095(-346779531);
      if (var79 != 0) {
         new classFY();
         var2.method13099(1662002950);
         var2.method13099(1197587470);
         var2.method13099(-871509973);
         classXY.method13113(var2, (byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ge")
   public ModelData method4087() {
      this.method4073();
      return this;
   }

   @ObfuscatedSignature(descriptor = "(Ler;Ler;IIIZ)V")
   @ObfuscatedName("by")
   static void method3988(ModelData var0, ModelData var1, int var2, int var3, int var4, boolean var5) {
      var0.method4053();
      method4048(var0);
      var1.method4053();
      method4048(var1);
      field1880++;
      int var6 = 0;
      float[] var7 = var1.field1853;
      int var8 = var1.field1846;

      for (int var9 = 0; var9 < var0.field1846; var9++) {
         classFR var10 = var0.field1871[var9];
         if (var10.field2384 * 1200753713 != 0) {
            int var11 = (int)var0.field1854[var9] - var3;
            if (var11 <= var1.field1889) {
               int var12 = (int)var0.field1853[var9] - var2;
               if (var12 >= var1.field1887 && var12 <= var1.field1891) {
                  int var13 = (int)var0.field1855[var9] - var4;
                  if (var13 >= var1.field1888 && var13 <= var1.field1890) {
                     for (int var14 = 0; var14 < var8; var14++) {
                        classFR var15 = var1.field1871[var14];
                        if (var12 == (int)var7[var14]
                           && var13 == (int)var1.field1855[var14]
                           && var11 == (int)var1.field1854[var14]
                           && var15.field2384 * 1200753713 != 0) {
                           if (var0.field1873 == null) {
                              var0.field1873 = new classFR[var0.field1846];
                           }

                           if (var1.field1873 == null) {
                              var1.field1873 = new classFR[var8];
                           }

                           classFR var16 = var0.field1873[var9];
                           if (var16 == null) {
                              var16 = var0.field1873[var9] = new classFR(var10);
                           }

                           classFR var17 = var1.field1873[var14];
                           if (var17 == null) {
                              var17 = var1.field1873[var14] = new classFR(var15);
                           }

                           var16.field2386 = var16.field2386 + var15.field2386 * 1;
                           var16.field2385 = var16.field2385 + var15.field2385 * 1;
                           var16.field2383 = var16.field2383 + var15.field2383 * 1;
                           var16.field2384 = var16.field2384 + var15.field2384 * 1;
                           var17.field2386 = var17.field2386 + var10.field2386 * 1;
                           var17.field2385 = var17.field2385 + var10.field2385 * 1;
                           var17.field2383 = var17.field2383 + var10.field2383 * 1;
                           var17.field2384 = var17.field2384 + var10.field2384 * 1;
                           var6++;
                           field1879[var9] = field1880;
                           field1881[var14] = field1880;
                        }
                     }
                  }
               }
            }
         }
      }

      if (var6 >= 3 && var5) {
         for (int var18 = 0; var18 < var0.field1847; var18++) {
            if (field1879[var0.field1878[var18]] == field1880 && field1879[var0.field1862[var18]] == field1880 && field1879[var0.field1863[var18]] == field1880
               )
             {
               if (var0.field1864 == null) {
                  var0.field1864 = new byte[var0.field1847];
               }

               var0.field1864[var18] = 2;
            }
         }

         for (int var19 = 0; var19 < var1.field1847; var19++) {
            if (field1881[var1.field1878[var19]] == field1880 && field1881[var1.field1862[var19]] == field1880 && field1881[var1.field1863[var19]] == field1880
               )
             {
               if (var1.field1864 == null) {
                  var1.field1864 = new byte[var1.field1847];
               }

               var1.field1864[var19] = 2;
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bq")
   static final int method3990(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 950961110;
      }

      return (var0 & -640308086) + var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bc")
   public void method4027(int var1) {
      int var2 = field1882[var1];
      int var3 = field1883[var1];

      for (int var4 = 0; var4 < this.field1846; var4++) {
         int var5 = (int)this.field1855[var4] * var2 + (int)this.field1853[var4] * var3 >> 16;
         this.field1855[var4] = (int)this.field1855[var4] * var3 - (int)this.field1853[var4] * var2 >> 16;
         this.field1853[var4] = var5;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("fp")
   public ModelData method4068() {
      this.field1850 = (short[])this.field1850.clone();
      return this;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bs")
   static final int method3993(int var0) {
      if (var0 < 2) {
         var0 = 2;
      } else if (var0 > 690581751) {
         var0 = 126;
      }

      return var0;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("bv")
   void method3999(byte[] var1) {
      boolean var2 = false;
      boolean var3 = false;
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var4.field6953 = (var1.length - 23) * 123268252;
      int var9 = var4.method13099(1910827335);
      int var10 = var4.method13099(838602305);
      int var11 = var4.method13095(-346779531);
      int var12 = var4.method13095(-346779531);
      int var13 = var4.method13095(-346779531);
      int var14 = var4.method13095(-346779531);
      int var15 = var4.method13095(-346779531);
      int var16 = var4.method13095(-346779531);
      int var17 = var4.method13095(-346779531);
      int var18 = var4.method13099(235013632);
      int var19 = var4.method13099(1116533836);
      int var20 = var4.method13099(-2015401052);
      int var21 = var4.method13099(1884579926);
      int var22 = var4.method13099(-1964109579);
      int var23 = 0;
      var23 += var9;
      var23 += var10;
      int var26 = var23;
      if (var13 == 255) {
         var23 += var10;
      }

      int var27 = var23;
      if (var15 == 1) {
         var23 += var10;
      }

      int var28 = var23;
      if (var12 == 1) {
         var23 += var10;
      }

      var23 += var22;
      int var30 = var23;
      if (var14 == 1) {
         var23 += var10;
      }

      var23 += var21;
      var23 += var10 * 2;
      var23 += var11 * 6;
      var23 += var18;
      var23 += var19;
      var23 += var20;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      if (var16 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
         this.field1866 = new byte[var10];
         this.field1851 = new short[var10];
      }

      if (var13 == -943129300) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      if (var17 == 1) {
         this.field1874 = new int[var9][];
         this.field1875 = new int[var9][];
      }

      this.field1850 = new short[var10];
      var4.field6953 = var23 * -44275543;
      var5.field6953 = var23 * 2132904190;
      var6.field6953 = var23 * 1597765480;
      var7.field6953 = var23 * -1095856699;
      var8.field6953 = var23 * -1095856699;
      int var38 = 0;
      int var39 = 0;
      int var40 = 0;

      for (int var41 = 0; var41 < var9; var41++) {
         int var42 = var4.method13095(-346779531);
         int var43 = 0;
         if ((var42 & 1) != 0) {
            var43 = var5.method13146(-1402273016);
         }

         int var44 = 0;
         if ((var42 & 2) != 0) {
            var44 = var6.method13146(-113933763);
         }

         int var45 = 0;
         if ((var42 & 4) != 0) {
            var45 = var7.method13146(-468696328);
         }

         this.field1853[var41] = var38 + var43;
         this.field1854[var41] = var39 + var44;
         this.field1855[var41] = var40 + var45;
         var38 = (int)this.field1853[var41];
         var39 = (int)this.field1854[var41];
         var40 = (int)this.field1855[var41];
         if (var16 == 1) {
            this.field1868[var41] = var8.method13095(-346779531);
         }
      }

      if (var17 == 1) {
         for (int var58 = 0; var58 < var9; var58++) {
            int var61 = var8.method13095(-346779531);
            this.field1874[var58] = new int[var61];
            this.field1875[var58] = new int[var61];

            for (int var64 = 0; var64 < var61; var64++) {
               this.field1874[var58][var64] = var8.method13095(-346779531);
               this.field1875[var58][var64] = var8.method13095(-346779531);
            }
         }
      }

      var4.field6953 = var23 * -1095856699;
      var5.field6953 = var28 * -1631420608;
      var6.field6953 = var26 * -1095856699;
      var7.field6953 = var30 * -1095856699;
      var8.field6953 = var27 * 1899692954;

      for (int var59 = 0; var59 < var10; var59++) {
         this.field1850[var59] = (short)var4.method13099(-1387701971);
         if (var12 == 1) {
            int var62 = var5.method13095(-346779531);
            if ((var62 & 1) == 1) {
               this.field1864[var59] = 1;
               var2 = true;
            } else {
               this.field1864[var59] = 0;
            }

            if ((var62 & 2) == 2) {
               this.field1866[var59] = (byte)(var62 >> 2);
               this.field1851[var59] = this.field1850[var59];
               this.field1850[var59] = (short)2142449124;
               if (this.field1851[var59] != -1) {
                  var3 = true;
               }
            } else {
               this.field1866[var59] = -1;
               this.field1851[var59] = -1;
            }
         }

         if (var13 == -2146224790) {
            this.field1865[var59] = var6.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var59] = var7.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var59] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var23 * -807114937;
      var5.field6953 = var23 * -1095856699;
      int var60 = 0;
      int var63 = 0;
      int var65 = 0;
      int var66 = 0;

      for (int var67 = 0; var67 < var10; var67++) {
         int var46 = var5.method13095(-346779531);
         if (var46 == 1) {
            var60 = var4.method13146(-260723791) + var66;
            var63 = var4.method13146(-1405011711) + var60;
            var65 = var4.method13146(-920675607) + var63;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 2) {
            var63 = var65;
            var65 = var4.method13146(-22342246) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 3) {
            var60 = var65;
            var65 = var4.method13146(-1914868462) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 4) {
            int var47 = var60;
            var60 = var63;
            var63 = var47;
            var65 = var4.method13146(-1534744898) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var47;
            this.field1863[var67] = var65;
         }
      }

      var4.field6953 = var23 * -1095856699;

      for (int var68 = 0; var68 < var11; var68++) {
         this.field1859[var68] = 0;
         this.field1867[var68] = (short)var4.method13099(940791349);
         this.field1856[var68] = (short)var4.method13099(36624017);
         this.field1860[var68] = (short)var4.method13099(1660472306);
      }

      var4.field6953 = var23 * -1095856699;
      boolean var69 = var4.method13095(-346779531) == 1;
      if (var69) {
         this.field1870 = new byte[var10];

         for (int var70 = 0; var70 < var10; var70++) {
            this.field1870[var70] = var4.method13097((byte)17);
         }
      }

      if (this.field1866 != null) {
         boolean var71 = false;

         for (int var72 = 0; var72 < var10; var72++) {
            int var48 = this.field1866[var72] & 1537777256;
            if (var48 != 255) {
               if ((this.field1867[var48] & 1234833934) == this.field1878[var72]
                  && (this.field1856[var48] & '\uffff') == this.field1862[var72]
                  && (this.field1860[var48] & -1564257935) == this.field1863[var72]) {
                  this.field1866[var72] = -1;
               } else {
                  var71 = true;
               }
            }
         }

         if (!var71) {
            this.field1866 = null;
         }
      }

      if (!var3) {
         this.field1851 = null;
      }

      if (!var2) {
         this.field1864 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("al")
   void method4000(byte[] var1) {
      boolean var2 = false;
      boolean var3 = false;
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var4.field6953 = (var1.length - 23) * -1095856699;
      int var9 = var4.method13099(-107791080);
      int var10 = var4.method13099(-768146370);
      int var11 = var4.method13095(-346779531);
      int var12 = var4.method13095(-346779531);
      int var13 = var4.method13095(-346779531);
      int var14 = var4.method13095(-346779531);
      int var15 = var4.method13095(-346779531);
      int var16 = var4.method13095(-346779531);
      int var17 = var4.method13095(-346779531);
      int var18 = var4.method13099(795681871);
      int var19 = var4.method13099(-2010188365);
      int var20 = var4.method13099(-762798976);
      int var21 = var4.method13099(-2050234948);
      int var22 = var4.method13099(-833548673);
      int var23 = 0;
      var23 += var9;
      var23 += var10;
      int var26 = var23;
      if (var13 == 1822631066) {
         var23 += var10;
      }

      int var27 = var23;
      if (var15 == 1) {
         var23 += var10;
      }

      int var28 = var23;
      if (var12 == 1) {
         var23 += var10;
      }

      var23 += var22;
      int var30 = var23;
      if (var14 == 1) {
         var23 += var10;
      }

      var23 += var21;
      var23 += var10 * 2;
      var23 += var11 * 6;
      var23 += var18;
      var23 += var19;
      var23 += var20;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      if (var16 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
         this.field1866 = new byte[var10];
         this.field1851 = new short[var10];
      }

      if (var13 == -32858800) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      if (var17 == 1) {
         this.field1874 = new int[var9][];
         this.field1875 = new int[var9][];
      }

      this.field1850 = new short[var10];
      var4.field6953 = var23 * -1095856699;
      var5.field6953 = var23 * -1095856699;
      var6.field6953 = var23 * -1095856699;
      var7.field6953 = var23 * -1095856699;
      var8.field6953 = var23 * -2100283791;
      int var38 = 0;
      int var39 = 0;
      int var40 = 0;

      for (int var41 = 0; var41 < var9; var41++) {
         int var42 = var4.method13095(-346779531);
         int var43 = 0;
         if ((var42 & 1) != 0) {
            var43 = var5.method13146(-1601290666);
         }

         int var44 = 0;
         if ((var42 & 2) != 0) {
            var44 = var6.method13146(-1962808119);
         }

         int var45 = 0;
         if ((var42 & 4) != 0) {
            var45 = var7.method13146(-1604606323);
         }

         this.field1853[var41] = var38 + var43;
         this.field1854[var41] = var39 + var44;
         this.field1855[var41] = var40 + var45;
         var38 = (int)this.field1853[var41];
         var39 = (int)this.field1854[var41];
         var40 = (int)this.field1855[var41];
         if (var16 == 1) {
            this.field1868[var41] = var8.method13095(-346779531);
         }
      }

      if (var17 == 1) {
         for (int var58 = 0; var58 < var9; var58++) {
            int var61 = var8.method13095(-346779531);
            this.field1874[var58] = new int[var61];
            this.field1875[var58] = new int[var61];

            for (int var64 = 0; var64 < var61; var64++) {
               this.field1874[var58][var64] = var8.method13095(-346779531);
               this.field1875[var58][var64] = var8.method13095(-346779531);
            }
         }
      }

      var4.field6953 = var23 * -1530650428;
      var5.field6953 = var28 * -1095856699;
      var6.field6953 = var26 * -1095856699;
      var7.field6953 = var30 * -2123116226;
      var8.field6953 = var27 * -464439939;

      for (int var59 = 0; var59 < var10; var59++) {
         this.field1850[var59] = (short)var4.method13099(487913714);
         if (var12 == 1) {
            int var62 = var5.method13095(-346779531);
            if ((var62 & 1) == 1) {
               this.field1864[var59] = 1;
               var2 = true;
            } else {
               this.field1864[var59] = 0;
            }

            if ((var62 & 2) == 2) {
               this.field1866[var59] = (byte)(var62 >> 2);
               this.field1851[var59] = this.field1850[var59];
               this.field1850[var59] = (short)-1173186371;
               if (this.field1851[var59] != -1) {
                  var3 = true;
               }
            } else {
               this.field1866[var59] = -1;
               this.field1851[var59] = -1;
            }
         }

         if (var13 == 255) {
            this.field1865[var59] = var6.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var59] = var7.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var59] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var23 * -1095856699;
      var5.field6953 = var23 * -1095856699;
      int var60 = 0;
      int var63 = 0;
      int var65 = 0;
      int var66 = 0;

      for (int var67 = 0; var67 < var10; var67++) {
         int var46 = var5.method13095(-346779531);
         if (var46 == 1) {
            var60 = var4.method13146(-1061225280) + var66;
            var63 = var4.method13146(-1001751831) + var60;
            var65 = var4.method13146(-169230939) + var63;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 2) {
            var63 = var65;
            var65 = var4.method13146(-324407637) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 3) {
            var60 = var65;
            var65 = var4.method13146(-1059913088) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var63;
            this.field1863[var67] = var65;
         }

         if (var46 == 4) {
            int var47 = var60;
            var60 = var63;
            var63 = var47;
            var65 = var4.method13146(-181421895) + var66;
            var66 = var65;
            this.field1878[var67] = var60;
            this.field1862[var67] = var47;
            this.field1863[var67] = var65;
         }
      }

      var4.field6953 = var23 * -1095856699;

      for (int var68 = 0; var68 < var11; var68++) {
         this.field1859[var68] = 0;
         this.field1867[var68] = (short)var4.method13099(-857983386);
         this.field1856[var68] = (short)var4.method13099(-141855442);
         this.field1860[var68] = (short)var4.method13099(-1836913907);
      }

      var4.field6953 = var23 * -926635913;
      boolean var69 = var4.method13095(-346779531) == 1;
      if (var69) {
         this.field1870 = new byte[var10];

         for (int var70 = 0; var70 < var10; var70++) {
            this.field1870[var70] = var4.method13097((byte)17);
         }
      }

      if (this.field1866 != null) {
         boolean var71 = false;

         for (int var72 = 0; var72 < var10; var72++) {
            int var48 = this.field1866[var72] & 255;
            if (var48 != 552178001) {
               if ((this.field1867[var48] & -432877611) == this.field1878[var72]
                  && (this.field1856[var48] & 1121576698) == this.field1862[var72]
                  && (this.field1860[var48] & 618463106) == this.field1863[var72]) {
                  this.field1866[var72] = -1;
               } else {
                  var71 = true;
               }
            }
         }

         if (!var71) {
            this.field1866 = null;
         }
      }

      if (!var3) {
         this.field1851 = null;
      }

      if (!var2) {
         this.field1864 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()[Lfr;")
   @ObfuscatedName("fw")
   public classFR[] method4088() {
      return this.field1873;
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("bl")
   void method4005(byte[] var1) {
      boolean var2 = false;
      boolean var3 = false;
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var4.field6953 = (var1.length - 18) * 958010523;
      int var9 = var4.method13099(658472533);
      int var10 = var4.method13099(-1811695499);
      int var11 = var4.method13095(-346779531);
      int var12 = var4.method13095(-346779531);
      int var13 = var4.method13095(-346779531);
      int var14 = var4.method13095(-346779531);
      int var15 = var4.method13095(-346779531);
      int var16 = var4.method13095(-346779531);
      int var17 = var4.method13099(-1572120862);
      int var18 = var4.method13099(-1171902964);
      int var19 = var4.method13099(1124454136);
      int var20 = var4.method13099(1427155933);
      int var21 = 0;
      var21 += var9;
      var21 += var10;
      int var24 = var21;
      if (var13 == -748518367) {
         var21 += var10;
      }

      int var25 = var21;
      if (var15 == 1) {
         var21 += var10;
      }

      int var26 = var21;
      if (var12 == 1) {
         var21 += var10;
      }

      int var27 = var21;
      if (var16 == 1) {
         var21 += var9;
      }

      int var28 = var21;
      if (var14 == 1) {
         var21 += var10;
      }

      var21 += var20;
      var21 += var10 * 2;
      var21 += var11 * 6;
      var21 += var17;
      var21 += var18;
      var21 += var19;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      if (var16 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
         this.field1866 = new byte[var10];
         this.field1851 = new short[var10];
      }

      if (var13 == 255) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      this.field1850 = new short[var10];
      var4.field6953 = var21 * -500860398;
      var5.field6953 = var21 * -1095856699;
      var6.field6953 = var21 * -597221962;
      var7.field6953 = var21 * -10157058;
      var8.field6953 = var27 * -1310792932;
      int var35 = 0;
      int var36 = 0;
      int var37 = 0;

      for (int var38 = 0; var38 < var9; var38++) {
         int var39 = var4.method13095(-346779531);
         int var40 = 0;
         if ((var39 & 1) != 0) {
            var40 = var5.method13146(-2039579531);
         }

         int var41 = 0;
         if ((var39 & 2) != 0) {
            var41 = var6.method13146(-966355095);
         }

         int var42 = 0;
         if ((var39 & 4) != 0) {
            var42 = var7.method13146(-1015758645);
         }

         this.field1853[var38] = var35 + var40;
         this.field1854[var38] = var36 + var41;
         this.field1855[var38] = var37 + var42;
         var35 = (int)this.field1853[var38];
         var36 = (int)this.field1854[var38];
         var37 = (int)this.field1855[var38];
         if (var16 == 1) {
            this.field1868[var38] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var21 * -1095856699;
      var5.field6953 = var26 * -1095856699;
      var6.field6953 = var24 * -1095856699;
      var7.field6953 = var28 * -1095856699;
      var8.field6953 = var25 * 486336687;

      for (int var53 = 0; var53 < var10; var53++) {
         this.field1850[var53] = (short)var4.method13099(-358413927);
         if (var12 == 1) {
            int var55 = var5.method13095(-346779531);
            if ((var55 & 1) == 1) {
               this.field1864[var53] = 1;
               var2 = true;
            } else {
               this.field1864[var53] = 0;
            }

            if ((var55 & 2) == 2) {
               this.field1866[var53] = (byte)(var55 >> 2);
               this.field1851[var53] = this.field1850[var53];
               this.field1850[var53] = 127;
               if (this.field1851[var53] != -1) {
                  var3 = true;
               }
            } else {
               this.field1866[var53] = -1;
               this.field1851[var53] = -1;
            }
         }

         if (var13 == 255) {
            this.field1865[var53] = var6.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var53] = var7.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var53] = var8.method13095(-346779531);
         }
      }

      var4.field6953 = var21 * 191032453;
      var5.field6953 = var21 * -977377560;
      int var54 = 0;
      int var56 = 0;
      int var57 = 0;
      int var58 = 0;

      for (int var59 = 0; var59 < var10; var59++) {
         int var43 = var5.method13095(-346779531);
         if (var43 == 1) {
            var54 = var4.method13146(-2010730729) + var58;
            var56 = var4.method13146(-824868791) + var54;
            var57 = var4.method13146(-1171847246) + var56;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var56;
            this.field1863[var59] = var57;
         }

         if (var43 == 2) {
            var56 = var57;
            var57 = var4.method13146(-1399220863) + var58;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var56;
            this.field1863[var59] = var57;
         }

         if (var43 == 3) {
            var54 = var57;
            var57 = var4.method13146(-1773693113) + var58;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var56;
            this.field1863[var59] = var57;
         }

         if (var43 == 4) {
            int var44 = var54;
            var54 = var56;
            var56 = var44;
            var57 = var4.method13146(-1061470964) + var58;
            var58 = var57;
            this.field1878[var59] = var54;
            this.field1862[var59] = var44;
            this.field1863[var59] = var57;
         }
      }

      var4.field6953 = var21 * -1021084074;

      for (int var60 = 0; var60 < var11; var60++) {
         this.field1859[var60] = 0;
         this.field1867[var60] = (short)var4.method13099(1137077164);
         this.field1856[var60] = (short)var4.method13099(1636225419);
         this.field1860[var60] = (short)var4.method13099(1880898436);
      }

      if (this.field1866 != null) {
         boolean var61 = false;

         for (int var62 = 0; var62 < var10; var62++) {
            int var63 = this.field1866[var62] & 255;
            if (var63 != 255) {
               if ((this.field1867[var63] & '\uffff') == this.field1878[var62]
                  && (this.field1856[var63] & 1854159271) == this.field1862[var62]
                  && (this.field1860[var63] & '\uffff') == this.field1863[var62]) {
                  this.field1866[var62] = -1;
               } else {
                  var61 = true;
               }
            }
         }

         if (!var61) {
            this.field1866 = null;
         }
      }

      if (!var3) {
         this.field1851 = null;
      }

      if (!var2) {
         this.field1864 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ct")
   public ModelData method4011() {
      ModelData var1 = new ModelData();
      if (this.field1864 != null) {
         var1.field1864 = new byte[this.field1847];

         for (int var2 = 0; var2 < this.field1847; var2++) {
            var1.field1864[var2] = this.field1864[var2];
         }
      }

      var1.field1846 = this.field1846;
      var1.field1847 = this.field1847;
      var1.field1852 = this.field1852;
      var1.field1853 = this.field1853;
      var1.field1854 = this.field1854;
      var1.field1855 = this.field1855;
      var1.field1878 = this.field1878;
      var1.field1862 = this.field1862;
      var1.field1863 = this.field1863;
      var1.field1865 = this.field1865;
      var1.field1857 = this.field1857;
      var1.field1866 = this.field1866;
      var1.field1850 = this.field1850;
      var1.field1851 = this.field1851;
      var1.field1849 = this.field1849;
      var1.field1859 = this.field1859;
      var1.field1867 = this.field1867;
      var1.field1856 = this.field1856;
      var1.field1860 = this.field1860;
      var1.field1868 = this.field1868;
      var1.field1869 = this.field1869;
      var1.field1861 = this.field1861;
      var1.field1858 = this.field1858;
      var1.field1871 = this.field1871;
      var1.field1872 = this.field1872;
      var1.field1876 = this.field1876;
      var1.field1877 = this.field1877;
      var1.field1870 = this.field1870;
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("nq")
   public static void method4038(ModelData var0) {
      if (var0 == null) {
         var0.getFaceIndices3();
      }

      for (int var1 = 0; var1 < var0.field1846; var1++) {
         var0.field1855[var1] = -((int)var0.field1855[var1]);
      }

      for (int var3 = 0; var3 < var0.field1847; var3++) {
         int var2 = var0.field1878[var3];
         var0.field1878[var3] = var0.field1863[var3];
         var0.field1863[var3] = var2;
      }

      var0.method4049();
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Ler;")
   @ObfuscatedName("cl")
   public ModelData method4013(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      this.method4053();
      int var7 = var2 + this.field1887;
      int var8 = var2 + this.field1891;
      int var9 = var4 + this.field1888;
      int var10 = var4 + this.field1890;
      if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
            return this;
         } else {
            ModelData var11;
            if (var5) {
               var11 = new ModelData(this, true, true, true, true);
               var11.field1854 = new float[var11.field1846];
            } else {
               var11 = this;
            }

            if (var6 == 0) {
               for (int var12 = 0; var12 < var11.field1846; var12++) {
                  int var13 = (int)this.field1853[var12] + var2;
                  int var14 = (int)this.field1855[var12] + var4;
                  int var15 = var13 & 127;
                  int var16 = var14 & 127;
                  int var17 = var13 >> 7;
                  int var18 = var14 >> 7;
                  int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                  int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.field1854[var12] = (int)this.field1854[var12] + var21 - var3;
               }
            } else {
               for (int var27 = 0; var27 < var11.field1846; var27++) {
                  int var28 = (-((int)this.field1854[var27]) << 16) / (this.field1741 * -1256242689);
                  if (var28 < var6) {
                     int var29 = (int)this.field1853[var27] + var2;
                     int var30 = (int)this.field1855[var27] + var4;
                     int var31 = var29 & 127;
                     int var32 = var30 & 127;
                     int var33 = var29 >> 7;
                     int var34 = var30 >> 7;
                     int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                     int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                     int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                     var11.field1854[var27] = (int)this.field1854[var27] + (var22 - var3) * (var6 - var28) / var6;
                  } else {
                     var11.field1854[var27] = this.field1854[var27];
                  }
               }
            }

            var11.method4049();
            return var11;
         }
      } else {
         return this;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("xd")
   public void method4039() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         this.field1855[var1] = -((int)this.field1855[var1]);
      }

      for (int var3 = 0; var3 < this.field1847; var3++) {
         int var2 = this.field1878[var3];
         this.field1878[var3] = this.field1863[var3];
         this.field1863[var3] = var2;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cv")
   void method4019() {
      if (this.field1868 != null) {
         int[] var1 = new int[-1609438000];
         int var2 = 0;

         for (int var3 = 0; var3 < this.field1846; var3++) {
            int var4 = this.field1868[var3];
            var1[var4]++;
            if (var4 > var2) {
               var2 = var4;
            }
         }

         this.field1861 = new int[var2 + 1][];

         for (int var7 = 0; var7 <= var2; var7++) {
            this.field1861[var7] = new int[var1[var7]];
            var1[var7] = 0;
         }

         int var8 = 0;

         while (var8 < this.field1846) {
            int var12 = this.field1868[var8];
            this.field1861[var12][var1[var12]++] = var8++;
         }

         this.field1868 = null;
      }

      if (this.field1869 != null) {
         int[] var5 = new int[1280990010];
         int var6 = 0;

         for (int var9 = 0; var9 < this.field1847; var9++) {
            int var13 = this.field1869[var9];
            var5[var13]++;
            if (var13 > var6) {
               var6 = var13;
            }
         }

         this.field1858 = new int[var6 + 1][];

         for (int var10 = 0; var10 <= var6; var10++) {
            this.field1858[var10] = new int[var5[var10]];
            var5[var10] = 0;
         }

         int var11 = 0;

         while (var11 < this.field1847) {
            int var14 = this.field1869[var11];
            this.field1858[var14][var5[var14]++] = var11++;
         }

         this.field1869 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fy")
   public void method4021() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         int var2 = (int)this.field1853[var1];
         this.field1853[var1] = this.field1855[var1];
         this.field1855[var1] = -var2;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Lfx;")
   @ObfuscatedName("dw")
   public final Model method4058(int var1, int var2, int var3, int var4, int var5) {
      method4048(this);
      int var6 = (int)Math.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
      int var7 = var2 * var6 >> 8;
      Model var8 = new Model();
      var8.field2432 = new int[this.field1847];
      var8.field2426 = new int[this.field1847];
      var8.field2433 = new int[this.field1847];
      if (this.field1852 > 0 && this.field1866 != null) {
         int[] var9 = new int[this.field1852];

         for (int var10 = 0; var10 < this.field1847; var10++) {
            if (this.field1866[var10] != -1) {
               var9[this.field1866[var10] & 255]++;
            }
         }

         var8.field2416 = 0;

         for (int var17 = 0; var17 < this.field1852; var17++) {
            if (var9[var17] > 0 && this.field1859[var17] == 0) {
               var8.field2416++;
            }
         }

         var8.field2437 = new int[var8.field2416];
         var8.field2438 = new int[var8.field2416];
         var8.field2439 = new int[var8.field2416];
         int var18 = 0;

         for (int var11 = 0; var11 < this.field1852; var11++) {
            if (var9[var11] > 0 && this.field1859[var11] == 0) {
               var8.field2437[var18] = this.field1867[var11] & '\uffff';
               var8.field2438[var18] = this.field1856[var11] & '\uffff';
               var8.field2439[var18] = this.field1860[var11] & '\uffff';
               var9[var11] = var18++;
            } else {
               var9[var11] = -1;
            }
         }

         var8.field2434 = new byte[this.field1847];

         for (int var20 = 0; var20 < this.field1847; var20++) {
            if (this.field1866[var20] != -1) {
               var8.field2434[var20] = (byte)var9[this.field1866[var20] & 255];
            } else {
               var8.field2434[var20] = -1;
            }
         }
      }

      for (int var16 = 0; var16 < this.field1847; var16++) {
         byte var19;
         if (this.field1864 == null) {
            var19 = 0;
         } else {
            var19 = this.field1864[var16];
         }

         byte var21;
         if (this.field1857 == null) {
            var21 = 0;
         } else {
            var21 = this.field1857[var16];
         }

         short var12;
         if (this.field1851 == null) {
            var12 = -1;
         } else {
            var12 = this.field1851[var16];
         }

         if (var21 == -2) {
            var19 = 3;
         }

         if (var21 == -1) {
            var19 = 2;
         }

         if (var12 == -1) {
            if (var19 == 0) {
               int var15 = this.field1850[var16] & '\uffff';
               classFR var13;
               if (this.field1873 != null && this.field1873[this.field1878[var16]] != null) {
                  var13 = this.field1873[this.field1878[var16]];
               } else {
                  var13 = this.field1871[this.field1878[var16]];
               }

               int var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2432[var16] = method3989(var15, var14);
               if (this.field1873 != null && this.field1873[this.field1862[var16]] != null) {
                  var13 = this.field1873[this.field1862[var16]];
               } else {
                  var13 = this.field1871[this.field1862[var16]];
               }

               var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2426[var16] = method3989(var15, var14);
               if (this.field1873 != null && this.field1873[this.field1863[var16]] != null) {
                  var13 = this.field1873[this.field1863[var16]];
               } else {
                  var13 = this.field1871[this.field1863[var16]];
               }

               var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2433[var16] = method3989(var15, var14);
            } else if (var19 == 1) {
               classEB var24 = this.field1872[var16];
               int var31 = var1
                  + (var3 * var24.field1722 * 835925423 + var4 * var24.field1718 * 696545605 + var5 * var24.field1717 * -1298928853) / (var7 + var7 / 2);
               var8.field2432[var16] = method3989(this.field1850[var16] & '\uffff', var31);
               var8.field2433[var16] = -1;
            } else if (var19 == 3) {
               var8.field2432[var16] = 128;
               var8.field2433[var16] = -1;
            } else {
               var8.field2433[var16] = -2;
            }
         } else if (var19 == 0) {
            classFR var25;
            if (this.field1873 != null && this.field1873[this.field1878[var16]] != null) {
               var25 = this.field1873[this.field1878[var16]];
            } else {
               var25 = this.field1871[this.field1878[var16]];
            }

            int var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2432[var16] = method3992(var32);
            if (this.field1873 != null && this.field1873[this.field1862[var16]] != null) {
               var25 = this.field1873[this.field1862[var16]];
            } else {
               var25 = this.field1871[this.field1862[var16]];
            }

            var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2426[var16] = method3992(var32);
            if (this.field1873 != null && this.field1873[this.field1863[var16]] != null) {
               var25 = this.field1873[this.field1863[var16]];
            } else {
               var25 = this.field1871[this.field1863[var16]];
            }

            var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2433[var16] = method3992(var32);
         } else if (var19 == 1) {
            classEB var28 = this.field1872[var16];
            int var35 = var1
               + (var3 * var28.field1722 * 835925423 + var4 * var28.field1718 * 696545605 + var5 * var28.field1717 * -1298928853) / (var7 + var7 / 2);
            var8.field2432[var16] = method3992(var35);
            var8.field2433[var16] = -1;
         } else {
            var8.field2433[var16] = -2;
         }
      }

      this.method4018();
      var8.field2422 = this.field1846;
      var8.field2425 = this.field1853;
      var8.field2428 = this.field1854;
      var8.field2424 = this.field1855;
      var8.field2417 = this.field1847;
      var8.field2427 = this.field1878;
      var8.field2430 = this.field1862;
      var8.field2431 = this.field1863;
      var8.field2429 = this.field1865;
      var8.field2442 = this.field1857;
      var8.field2418 = this.field1849;
      var8.field2440 = this.field1861;
      var8.field2441 = this.field1858;
      var8.field2435 = this.field1851;
      var8.field2506 = this.field1874;
      var8.field2505 = this.field1875;
      var8.field2436 = this.field1870;
      return var8;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ca")
   public void method4022() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         int var2 = (int)this.field1853[var1];
         this.field1853[var1] = this.field1855[var1];
         this.field1855[var1] = -var2;
      }

      this.method4049();
   }

   public short[] getFaceColors() {
      return this.field1850;
   }

   public byte[] getFaceTransparencies() {
      return this.field1857;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bf")
   static final int method3991(int var0, int var1) {
      var1 = var1 * (var0 & 127) >> 7;
      if (var1 < 2) {
         var1 = 2;
      } else if (var1 > 126) {
         var1 = 126;
      }

      return (var0 & 65408) + var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;SS)V")
   @ObfuscatedName("sh")
   public static void method4032(ModelData var0, short var1, short var2) {
      if (var0 == null) {
         var0.method4034(var1, var1);
      } else {
         for (int var3 = 0; var3 < var0.field1847; var3++) {
            if (var0.field1850[var3] == var1) {
               var0.field1850[var3] = var2;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("we")
   public ModelData method4089() {
      method4078(this);
      return this;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cy")
   public void method4025() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         int var2 = (int)this.field1855[var1];
         this.field1855[var1] = this.field1853[var1];
         this.field1853[var1] = -var2;
      }

      this.method4049();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("zo")
   public static void method4047(ModelData var0) {
      if (var0.field1871 == null) {
         var0.field1871 = new classFR[var0.field1846];

         for (int var1 = 0; var1 < var0.field1846; var1++) {
            var0.field1871[var1] = new classFR();
         }

         for (int var17 = 0; var17 < var0.field1847; var17++) {
            int var2 = var0.field1878[var17];
            int var3 = var0.field1862[var17];
            int var4 = var0.field1863[var17];
            int var5 = (int)var0.field1853[var3] - (int)var0.field1853[var2];
            int var6 = (int)var0.field1854[var3] - (int)var0.field1854[var2];
            int var7 = (int)var0.field1855[var3] - (int)var0.field1855[var2];
            int var8 = (int)var0.field1853[var4] - (int)var0.field1853[var2];
            int var9 = (int)var0.field1854[var4] - (int)var0.field1854[var2];
            int var10 = (int)var0.field1855[var4] - (int)var0.field1855[var2];
            int var11 = var6 * var10 - var9 * var7;
            int var12 = var7 * var8 - var10 * var5;

            int var13;
            for (var13 = var5 * var9 - var8 * var6;
               var11 > 8192 || var12 > 8192 || var13 > 8192 || var11 < -8192 || var12 < -8192 || var13 < -8192;
               var13 >>= 1
            ) {
               var11 >>= 1;
               var12 >>= 1;
            }

            int var14 = (int)Math.sqrt(var11 * var11 + var12 * var12 + var13 * var13);
            if (var14 <= 0) {
               var14 = 1;
            }

            var11 = var11 * -1268471083 / var14;
            var12 = var12 * 256 / var14;
            var13 = var13 * 256 / var14;
            byte var15;
            if (var0.field1864 == null) {
               var15 = 0;
            } else {
               var15 = var0.field1864[var17];
            }

            if (var15 == 0) {
               classFR var16 = var0.field1871[var2];
               var16.field2386 += var11 * -257626986;
               var16.field2385 += var12 * 1650876054;
               var16.field2383 += var13 * 1280581247;
               var16.field2384 += 466110237;
               var16 = var0.field1871[var3];
               var16.field2386 += var11 * -1349817055;
               var16.field2385 += var12 * 342169220;
               var16.field2383 += var13 * 1280581247;
               var16.field2384 += 1144194558;
               var16 = var0.field1871[var4];
               var16.field2386 += var11 * -1837090135;
               var16.field2385 += var12 * -941718535;
               var16.field2383 += var13 * -1389952198;
               var16.field2384 += -1768043311;
            } else if (var15 == 1) {
               if (var0.field1872 == null) {
                  var0.field1872 = new classEB[var0.field1847];
               }

               classEB var23 = var0.field1872[var17] = new classEB();
               var23.field1722 = var11 * 1704282964;
               var23.field1718 = var12 * 1804973374;
               var23.field1717 = var13 * 1654084164;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("co")
   public void method4090() {
      method4048(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bt")
   public void method4026() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         int var2 = (int)this.field1855[var1];
         this.field1855[var1] = this.field1853[var1];
         this.field1853[var1] = -var2;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("jj")
   public ModelData method3997() {
      this.field1853 = (float[])this.field1853.clone();
      this.field1854 = (float[])this.field1854.clone();
      this.field1855 = (float[])this.field1855.clone();
      return this;
   }

   public float[] getVerticesY() {
      return this.field1854;
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ur")
   public ModelData method4091() {
      Ignore.method10238(this);
      return this;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cn")
   public void method4028(int var1) {
      int var2 = field1882[var1];
      int var3 = field1883[var1];

      for (int var4 = 0; var4 < this.field1846; var4++) {
         int var5 = (int)this.field1855[var4] * var2 + (int)this.field1853[var4] * var3 >> 16;
         this.field1855[var4] = (int)this.field1855[var4] * var3 - (int)this.field1853[var4] * var2 >> 16;
         this.field1853[var4] = var5;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ch")
   public void method4029(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field1846; var4++) {
         this.field1853[var4] = (int)this.field1853[var4] + var1;
         this.field1854[var4] = (int)this.field1854[var4] + var2;
         this.field1855[var4] = (int)this.field1855[var4] + var3;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cf")
   public void method4023() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         int var2 = (int)this.field1853[var1];
         this.field1853[var1] = this.field1855[var1];
         this.field1855[var1] = -var2;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("ks")
   public static void method4048(ModelData var0) {
      if (var0 == null) {
         var0.method4041();
      }

      if (var0.field1871 == null) {
         var0.field1871 = new classFR[var0.field1846];

         for (int var1 = 0; var1 < var0.field1846; var1++) {
            var0.field1871[var1] = new classFR();
         }

         for (int var17 = 0; var17 < var0.field1847; var17++) {
            int var2 = var0.field1878[var17];
            int var3 = var0.field1862[var17];
            int var4 = var0.field1863[var17];
            int var5 = (int)var0.field1853[var3] - (int)var0.field1853[var2];
            int var6 = (int)var0.field1854[var3] - (int)var0.field1854[var2];
            int var7 = (int)var0.field1855[var3] - (int)var0.field1855[var2];
            int var8 = (int)var0.field1853[var4] - (int)var0.field1853[var2];
            int var9 = (int)var0.field1854[var4] - (int)var0.field1854[var2];
            int var10 = (int)var0.field1855[var4] - (int)var0.field1855[var2];
            int var11 = var6 * var10 - var9 * var7;
            int var12 = var7 * var8 - var10 * var5;

            int var13;
            for (var13 = var5 * var9 - var8 * var6;
               var11 > 8192 || var12 > 8192 || var13 > 8192 || var11 < -8192 || var12 < -8192 || var13 < -8192;
               var13 >>= 1
            ) {
               var11 >>= 1;
               var12 >>= 1;
            }

            int var14 = (int)Math.sqrt(var11 * var11 + var12 * var12 + var13 * var13);
            if (var14 <= 0) {
               var14 = 1;
            }

            var11 = var11 * 256 / var14;
            var12 = var12 * 256 / var14;
            var13 = var13 * 256 / var14;
            byte var15;
            if (var0.field1864 == null) {
               var15 = 0;
            } else {
               var15 = var0.field1864[var17];
            }

            if (var15 == 0) {
               classFR var16 = var0.field1871[var2];
               var16.field2386 += var11 * -1837090135;
               var16.field2385 += var12 * -941718535;
               var16.field2383 += var13 * 1280581247;
               var16.field2384 += -1768043311;
               var16 = var0.field1871[var3];
               var16.field2386 += var11 * -1837090135;
               var16.field2385 += var12 * -941718535;
               var16.field2383 += var13 * 1280581247;
               var16.field2384 += -1768043311;
               var16 = var0.field1871[var4];
               var16.field2386 += var11 * -1837090135;
               var16.field2385 += var12 * -941718535;
               var16.field2383 += var13 * 1280581247;
               var16.field2384 += -1768043311;
            } else if (var15 == 1) {
               if (var0.field1872 == null) {
                  var0.field1872 = new classEB[var0.field1847];
               }

               classEB var23 = var0.field1872[var17] = new classEB();
               var23.field1722 = var11 * 824131919;
               var23.field1718 = var12 * -1800111731;
               var23.field1717 = var13 * -1200391293;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("jf")
   public byte[] method4092() {
      return this.field1859;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Lfx;")
   @ObfuscatedName("fa")
   public Model method4093(int var1, int var2, int var3, int var4, int var5) {
      return this.method4070(var1, var2, var3, var4, var5);
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("ft")
   public ModelData method4074() {
      return new ModelData(this, true, true, true, true);
   }

   @ObfuscatedSignature(descriptor = "(Ler;[[IIIIZI)Ler;")
   @ObfuscatedName("bj")
   public static ModelData method4014(ModelData var0, int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      if (var0 == null) {
         return var0.method4015(var1, var2, var2, var2, var5, var2);
      } else {
         var0.method4053();
         int var7 = var2 + var0.field1887;
         int var8 = var2 + var0.field1891;
         int var9 = var4 + var0.field1888;
         int var10 = var4 + var0.field1890;
         if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
            var7 >>= 7;
            var8 = var8 + 127 >> 7;
            var9 >>= 7;
            var10 = var10 + 127 >> 7;
            if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
               return var0;
            } else {
               ModelData var11;
               if (var5) {
                  var11 = new ModelData(var0, true, true, true, true);
                  var11.field1854 = new float[var11.field1846];
               } else {
                  var11 = var0;
               }

               if (var6 == 0) {
                  for (int var12 = 0; var12 < var11.field1846; var12++) {
                     int var13 = (int)var0.field1853[var12] + var2;
                     int var14 = (int)var0.field1855[var12] + var4;
                     int var15 = var13 & 127;
                     int var16 = var14 & 127;
                     int var17 = var13 >> 7;
                     int var18 = var14 >> 7;
                     int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                     int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                     int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                     var11.field1854[var12] = (int)var0.field1854[var12] + var21 - var3;
                  }
               } else {
                  for (int var27 = 0; var27 < var11.field1846; var27++) {
                     int var28 = (-((int)var0.field1854[var27]) << 16) / (var0.field1741 * -1256242689);
                     if (var28 < var6) {
                        int var29 = (int)var0.field1853[var27] + var2;
                        int var30 = (int)var0.field1855[var27] + var4;
                        int var31 = var29 & 127;
                        int var32 = var30 & 127;
                        int var33 = var29 >> 7;
                        int var34 = var30 >> 7;
                        int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                        int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                        int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                        var11.field1854[var27] = (int)var0.field1854[var27] + (var22 - var3) * (var6 - var28) / var6;
                     } else {
                        var11.field1854[var27] = var0.field1854[var27];
                     }
                  }
               }

               var11.method4049();
               return var11;
            }
         } else {
            return var0;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(SS)V")
   @ObfuscatedName("cw")
   public void method4033(short var1, short var2) {
      for (int var3 = 0; var3 < this.field1847; var3++) {
         if (this.field1850[var3] == var1) {
            this.field1850[var3] = var2;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(SS)V")
   @ObfuscatedName("cj")
   public void method4036(short var1, short var2) {
      if (this.field1851 != null) {
         for (int var3 = 0; var3 < this.field1847; var3++) {
            if (this.field1851[var3] == var1) {
               this.field1851[var3] = var2;
            }
         }
      }
   }

   public int[] getFaceIndices1() {
      return this.field1878;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   public void method4024() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         this.field1853[var1] = -((int)this.field1853[var1]);
         this.field1855[var1] = -((int)this.field1855[var1]);
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bu")
   void method4053() {
      if (!this.field1848) {
         this.field1741 = 0;
         this.field1889 = 0;
         this.field1887 = 999999;
         this.field1891 = -999999;
         this.field1890 = -99999;
         this.field1888 = 99999;

         for (int var1 = 0; var1 < this.field1846; var1++) {
            int var2 = (int)this.field1853[var1];
            int var3 = (int)this.field1854[var1];
            int var4 = (int)this.field1855[var1];
            if (var2 < this.field1887) {
               this.field1887 = var2;
            }

            if (var2 > this.field1891) {
               this.field1891 = var2;
            }

            if (var4 < this.field1888) {
               this.field1888 = var4;
            }

            if (var4 > this.field1890) {
               this.field1890 = var4;
            }

            if (-var3 > this.field1741 * -1256242689) {
               this.field1741 = -var3 * 903659007;
            }

            if (var3 > this.field1889) {
               this.field1889 = var3;
            }
         }

         this.field1848 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("jk")
   public ModelData method4075() {
      this.field1851 = (short[])this.field1851.clone();
      return this;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ck")
   public void method4069() {
      classFR[] var1 = this.method4094();
      classFR[] var2 = this.method4088();
      if (var1 != null && this.field1897 == null) {
         int var3 = this.getVerticesCount();
         this.field1897 = new int[var3];
         this.field1896 = new int[var3];
         this.field1895 = new int[var3];

         for (int var4 = 0; var4 < var3; var4++) {
            classFR var5;
            if (var2 != null && (var5 = var2[var4]) != null) {
               this.field1897[var4] = var5.method4988();
               this.field1896[var4] = var5.method4991();
               this.field1895[var4] = var5.method4990();
            } else if ((var5 = var1[var4]) != null) {
               this.field1897[var4] = var5.method4988();
               this.field1896[var4] = var5.method4991();
               this.field1895[var4] = var5.method4990();
            }
         }
      }
   }

   public ModelData(ModelData[] var1, int var2) {
      this.field1847 = 0;
      this.field1849 = 0;
      this.field1848 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;
      boolean var10 = false;
      this.field1846 = 0;
      this.field1847 = 0;
      this.field1852 = 0;
      this.field1849 = -1;

      for (int var11 = 0; var11 < var2; var11++) {
         ModelData var12 = var1[var11];
         if (var12 != null) {
            this.field1846 = this.field1846 + var12.field1846;
            this.field1847 = this.field1847 + var12.field1847;
            this.field1852 = this.field1852 + var12.field1852;
            if (var12.field1865 != null) {
               var4 = true;
            } else {
               if (this.field1849 == -1) {
                  this.field1849 = var12.field1849;
               }

               if (this.field1849 != var12.field1849) {
                  var4 = true;
               }
            }

            var3 |= var12.field1864 != null;
            var5 |= var12.field1857 != null;
            var6 |= var12.field1869 != null;
            var7 |= var12.field1851 != null;
            var8 |= var12.field1866 != null;
            var9 |= var12.field1874 != null;
            var10 |= var12.field1870 != null;
         }
      }

      this.field1853 = new float[this.field1846];
      this.field1854 = new float[this.field1846];
      this.field1855 = new float[this.field1846];
      this.field1868 = new int[this.field1846];
      this.field1878 = new int[this.field1847];
      this.field1862 = new int[this.field1847];
      this.field1863 = new int[this.field1847];
      if (var3) {
         this.field1864 = new byte[this.field1847];
      }

      if (var4) {
         this.field1865 = new byte[this.field1847];
      }

      if (var5) {
         this.field1857 = new byte[this.field1847];
      }

      if (var6) {
         this.field1869 = new int[this.field1847];
      }

      if (var7) {
         this.field1851 = new short[this.field1847];
      }

      if (var8) {
         this.field1866 = new byte[this.field1847];
      }

      if (var9) {
         this.field1874 = new int[this.field1846][];
         this.field1875 = new int[this.field1846][];
      }

      if (var10) {
         this.field1870 = new byte[this.field1847];
      }

      this.field1850 = new short[this.field1847];
      if (this.field1852 > 0) {
         this.field1859 = new byte[this.field1852];
         this.field1867 = new short[this.field1852];
         this.field1856 = new short[this.field1852];
         this.field1860 = new short[this.field1852];
      }

      this.field1846 = 0;
      this.field1847 = 0;
      this.field1852 = 0;

      for (int var15 = 0; var15 < var2; var15++) {
         ModelData var16 = var1[var15];
         if (var16 != null) {
            for (int var13 = 0; var13 < var16.field1847; var13++) {
               if (var3 && var16.field1864 != null) {
                  this.field1864[this.field1847] = var16.field1864[var13];
               }

               if (var4) {
                  if (var16.field1865 != null) {
                     this.field1865[this.field1847] = var16.field1865[var13];
                  } else {
                     this.field1865[this.field1847] = var16.field1849;
                  }
               }

               if (var5 && var16.field1857 != null) {
                  this.field1857[this.field1847] = var16.field1857[var13];
               }

               if (var6 && var16.field1869 != null) {
                  this.field1869[this.field1847] = var16.field1869[var13];
               }

               if (var7) {
                  if (var16.field1851 != null) {
                     this.field1851[this.field1847] = var16.field1851[var13];
                  } else {
                     this.field1851[this.field1847] = -1;
                  }
               }

               if (var8) {
                  if (var16.field1866 != null && var16.field1866[var13] != -1) {
                     this.field1866[this.field1847] = (byte)(var16.field1866[var13] + this.field1852);
                  } else {
                     this.field1866[this.field1847] = -1;
                  }
               }

               if (var10 && var16.field1870 != null) {
                  this.field1870[this.field1847] = var16.field1870[var13];
               }

               this.field1850[this.field1847] = var16.field1850[var13];
               this.field1878[this.field1847] = this.method4007(var16, var16.field1878[var13]);
               this.field1862[this.field1847] = this.method4007(var16, var16.field1862[var13]);
               this.field1863[this.field1847] = this.method4007(var16, var16.field1863[var13]);
               this.field1847++;
            }

            for (int var17 = 0; var17 < var16.field1852; var17++) {
               byte var14 = this.field1859[this.field1852] = var16.field1859[var17];
               if (var14 == 0) {
                  this.field1867[this.field1852] = (short)this.method4007(var16, var16.field1867[var17]);
                  this.field1856[this.field1852] = (short)this.method4007(var16, var16.field1856[var17]);
                  this.field1860[this.field1852] = (short)this.method4007(var16, var16.field1860[var17]);
               }

               this.field1852++;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dm")
   public void method4045(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field1846; var4++) {
         this.field1853[var4] = (int)this.field1853[var4] * var1 / 128;
         this.field1854[var4] = (int)this.field1854[var4] * var2 / -537893264;
         this.field1855[var4] = (int)this.field1855[var4] * var3 / 128;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()[Lfr;")
   @ObfuscatedName("lo")
   public classFR[] method4094() {
      return this.field1871;
   }

   public float[] getVerticesZ() {
      return this.field1855;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dy")
   void method4050() {
      this.field1871 = null;
      this.field1873 = null;
      this.field1872 = null;
      this.field1848 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("di")
   void method4051() {
      this.field1871 = null;
      this.field1873 = null;
      this.field1872 = null;
      this.field1848 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("du")
   void method4054() {
      if (!this.field1848) {
         this.field1741 = 0;
         this.field1889 = 0;
         this.field1887 = 999999;
         this.field1891 = -999999;
         this.field1890 = -99999;
         this.field1888 = 99999;

         for (int var1 = 0; var1 < this.field1846; var1++) {
            int var2 = (int)this.field1853[var1];
            int var3 = (int)this.field1854[var1];
            int var4 = (int)this.field1855[var1];
            if (var2 < this.field1887) {
               this.field1887 = var2;
            }

            if (var2 > this.field1891) {
               this.field1891 = var2;
            }

            if (var4 < this.field1888) {
               this.field1888 = var4;
            }

            if (var4 > this.field1890) {
               this.field1890 = var4;
            }

            if (-var3 > this.field1741 * -1256242689) {
               this.field1741 = -var3 * 903659007;
            }

            if (var3 > this.field1889) {
               this.field1889 = var3;
            }
         }

         this.field1848 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("ac")
   void method4002(byte[] var1) {
      classXY var2 = new classXY(var1);
      classXY var3 = new classXY(var1);
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var2.field6953 = (var1.length - 23) * -1095856699;
      int var9 = var2.method13099(-1975589991);
      int var10 = var2.method13099(-1787785927);
      int var11 = var2.method13095(-346779531);
      int var12 = var2.method13095(-346779531);
      int var13 = var2.method13095(-346779531);
      int var14 = var2.method13095(-346779531);
      int var15 = var2.method13095(-346779531);
      int var16 = var2.method13095(-346779531);
      int var17 = var2.method13095(-346779531);
      int var18 = var2.method13099(1648062840);
      int var19 = var2.method13099(-655551909);
      int var20 = var2.method13099(-732802342);
      int var21 = var2.method13099(161945279);
      int var22 = var2.method13099(-578341193);
      int var23 = 0;
      int var24 = 0;
      int var25 = 0;
      if (var11 > 0) {
         this.field1859 = new byte[var11];
         var2.field6953 = 0;

         for (int var26 = 0; var26 < var11; var26++) {
            byte var27 = this.field1859[var26] = var2.method13097((byte)17);
            if (var27 == 0) {
               var23++;
            }

            if (var27 >= 1 && var27 <= 3) {
               var24++;
            }

            if (var27 == 2) {
               var25++;
            }
         }
      }

      int var58 = var11 + var9;
      int var28 = var58;
      if (var12 == 1) {
         var58 += var10;
      }

      var58 += var10;
      int var30 = var58;
      if (var13 == 255) {
         var58 += var10;
      }

      int var31 = var58;
      if (var15 == 1) {
         var58 += var10;
      }

      int var32 = var58;
      if (var17 == 1) {
         var58 += var9;
      }

      int var33 = var58;
      if (var14 == 1) {
         var58 += var10;
      }

      var58 += var21;
      int var35 = var58;
      if (var16 == 1) {
         var58 += var10 * 2;
      }

      var58 += var22;
      var58 += var10 * 2;
      var58 += var18;
      var58 += var19;
      var58 += var20;
      var58 += var23 * 6;
      var58 += var24 * 6;
      var58 += var24 * 6;
      var58 += var24 * 2;
      var58 += var24;
      var58 += var24 * 2 + var25 * 2;
      this.field1846 = var9;
      this.field1847 = var10;
      this.field1852 = var11;
      this.field1853 = new float[var9];
      this.field1854 = new float[var9];
      this.field1855 = new float[var9];
      this.field1878 = new int[var10];
      this.field1862 = new int[var10];
      this.field1863 = new int[var10];
      if (var17 == 1) {
         this.field1868 = new int[var9];
      }

      if (var12 == 1) {
         this.field1864 = new byte[var10];
      }

      if (var13 == 255) {
         this.field1865 = new byte[var10];
      } else {
         this.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         this.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         this.field1869 = new int[var10];
      }

      if (var16 == 1) {
         this.field1851 = new short[var10];
      }

      if (var16 == 1 && var11 > 0) {
         this.field1866 = new byte[var10];
      }

      this.field1850 = new short[var10];
      if (var11 > 0) {
         this.field1867 = new short[var11];
         this.field1856 = new short[var11];
         this.field1860 = new short[var11];
      }

      var2.field6953 = var11 * -1095856699;
      var3.field6953 = var58 * -1095856699;
      var4.field6953 = var58 * -1095856699;
      var5.field6953 = var58 * -1095856699;
      var6.field6953 = var32 * -1095856699;
      int var48 = 0;
      int var49 = 0;
      int var50 = 0;

      for (int var51 = 0; var51 < var9; var51++) {
         int var52 = var2.method13095(-346779531);
         int var53 = 0;
         if ((var52 & 1) != 0) {
            var53 = var3.method13146(-253909922);
         }

         int var54 = 0;
         if ((var52 & 2) != 0) {
            var54 = var4.method13146(-1068350675);
         }

         int var55 = 0;
         if ((var52 & 4) != 0) {
            var55 = var5.method13146(-732969970);
         }

         this.field1853[var51] = var48 + var53;
         this.field1854[var51] = var49 + var54;
         this.field1855[var51] = var50 + var55;
         var48 = (int)this.field1853[var51];
         var49 = (int)this.field1854[var51];
         var50 = (int)this.field1855[var51];
         if (var17 == 1) {
            this.field1868[var51] = var6.method13095(-346779531);
         }
      }

      var2.field6953 = var58 * -1095856699;
      var3.field6953 = var28 * -1095856699;
      var4.field6953 = var30 * -1095856699;
      var5.field6953 = var33 * -1095856699;
      var6.field6953 = var31 * -1095856699;
      var7.field6953 = var35 * -1095856699;
      var8.field6953 = var58 * -1095856699;

      for (int var72 = 0; var72 < var10; var72++) {
         this.field1850[var72] = (short)var2.method13099(-1567601339);
         if (var12 == 1) {
            this.field1864[var72] = var3.method13097((byte)17);
         }

         if (var13 == 255) {
            this.field1865[var72] = var4.method13097((byte)17);
         }

         if (var14 == 1) {
            this.field1857[var72] = var5.method13097((byte)17);
         }

         if (var15 == 1) {
            this.field1869[var72] = var6.method13095(-346779531);
         }

         if (var16 == 1) {
            this.field1851[var72] = (short)(var7.method13099(-639982632) - 1);
         }

         if (this.field1866 != null && this.field1851[var72] != -1) {
            this.field1866[var72] = (byte)(var8.method13095(-346779531) - 1);
         }
      }

      var2.field6953 = var58 * -1095856699;
      var3.field6953 = var58 * -1095856699;
      int var73 = 0;
      int var74 = 0;
      int var75 = 0;
      int var76 = 0;

      for (int var77 = 0; var77 < var10; var77++) {
         int var56 = var3.method13095(-346779531);
         if (var56 == 1) {
            var73 = var2.method13146(-1767038727) + var76;
            var74 = var2.method13146(-33335713) + var73;
            var75 = var2.method13146(-795617608) + var74;
            var76 = var75;
            this.field1878[var77] = var73;
            this.field1862[var77] = var74;
            this.field1863[var77] = var75;
         }

         if (var56 == 2) {
            var74 = var75;
            var75 = var2.method13146(-145083022) + var76;
            var76 = var75;
            this.field1878[var77] = var73;
            this.field1862[var77] = var74;
            this.field1863[var77] = var75;
         }

         if (var56 == 3) {
            var73 = var75;
            var75 = var2.method13146(-1394156646) + var76;
            var76 = var75;
            this.field1878[var77] = var73;
            this.field1862[var77] = var74;
            this.field1863[var77] = var75;
         }

         if (var56 == 4) {
            int var57 = var73;
            var73 = var74;
            var74 = var57;
            var75 = var2.method13146(-1183143936) + var76;
            var76 = var75;
            this.field1878[var77] = var73;
            this.field1862[var77] = var57;
            this.field1863[var77] = var75;
         }
      }

      var2.field6953 = var58 * -1095856699;
      var3.field6953 = var58 * -1095856699;
      var4.field6953 = var58 * -1095856699;
      var5.field6953 = var58 * -1095856699;
      var6.field6953 = var58 * -1095856699;
      var7.field6953 = var58 * -1095856699;

      for (int var78 = 0; var78 < var11; var78++) {
         int var80 = this.field1859[var78] & 255;
         if (var80 == 0) {
            this.field1867[var78] = (short)var2.method13099(-1543259803);
            this.field1856[var78] = (short)var2.method13099(1852002342);
            this.field1860[var78] = (short)var2.method13099(1714746368);
         }
      }

      var2.field6953 = var58 * -1095856699;
      int var79 = var2.method13095(-346779531);
      if (var79 != 0) {
         new classFY();
         var2.method13099(-1186071790);
         var2.method13099(-1191891964);
         var2.method13099(1217187388);
         classXY.method13113(var2, (byte)1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;Ler;I)I")
   @ObfuscatedName("pv")
   public static int method4008(ModelData var0, ModelData var1, int var2) {
      if (var0 == null) {
         var0.cloneColors();
      }

      int var3 = -1;
      int var4 = (int)var1.field1853[var2];
      int var5 = (int)var1.field1854[var2];
      int var6 = (int)var1.field1855[var2];

      for (int var7 = 0; var7 < var0.field1846; var7++) {
         if (var4 == (int)var0.field1853[var7] && var5 == (int)var0.field1854[var7] && var6 == (int)var0.field1855[var7]) {
            var3 = var7;
            break;
         }
      }

      if (var3 == -1) {
         var0.field1853[var0.field1846] = var4;
         var0.field1854[var0.field1846] = var5;
         var0.field1855[var0.field1846] = var6;
         if (var1.field1868 != null) {
            var0.field1868[var0.field1846] = var1.field1868[var2];
         }

         if (var1.field1874 != null) {
            var0.field1874[var0.field1846] = var1.field1874[var2];
            var0.field1875[var0.field1846] = var1.field1875[var2];
         }

         var3 = var0.field1846++;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dr")
   void method4055() {
      if (!this.field1848) {
         this.field1741 = 0;
         this.field1889 = 0;
         this.field1887 = 999999;
         this.field1891 = -999999;
         this.field1890 = -99999;
         this.field1888 = 99999;

         for (int var1 = 0; var1 < this.field1846; var1++) {
            int var2 = (int)this.field1853[var1];
            int var3 = (int)this.field1854[var1];
            int var4 = (int)this.field1855[var1];
            if (var2 < this.field1887) {
               this.field1887 = var2;
            }

            if (var2 > this.field1891) {
               this.field1891 = var2;
            }

            if (var4 < this.field1888) {
               this.field1888 = var4;
            }

            if (var4 > this.field1890) {
               this.field1890 = var4;
            }

            if (-var3 > this.field1741 * -1256242689) {
               this.field1741 = -var3 * 903659007;
            }

            if (var3 > this.field1889) {
               this.field1889 = var3;
            }
         }

         this.field1848 = true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;[B)V")
   @ObfuscatedName("fa")
   public static void method4003(ModelData var0, byte[] var1) {
      if (var0 == null) {
         var0.getFaceTransparencies();
      }

      classXY var2 = new classXY(var1);
      classXY var3 = new classXY(var1);
      classXY var4 = new classXY(var1);
      classXY var5 = new classXY(var1);
      classXY var6 = new classXY(var1);
      classXY var7 = new classXY(var1);
      classXY var8 = new classXY(var1);
      var2.field6953 = (var1.length - 23) * -1095856699;
      int var9 = var2.method13099(-1694051442);
      int var10 = var2.method13099(1847073970);
      int var11 = var2.method13095(-346779531);
      int var12 = var2.method13095(-346779531);
      int var13 = var2.method13095(-346779531);
      int var14 = var2.method13095(-346779531);
      int var15 = var2.method13095(-346779531);
      int var16 = var2.method13095(-346779531);
      int var17 = var2.method13095(-346779531);
      int var18 = var2.method13099(1864519031);
      int var19 = var2.method13099(-692031952);
      int var20 = var2.method13099(-1245741013);
      int var21 = var2.method13099(1841048898);
      int var22 = var2.method13099(-557880775);
      int var23 = 0;
      int var24 = 0;
      int var25 = 0;
      if (var11 > 0) {
         var0.field1859 = new byte[var11];
         var2.field6953 = 0;

         for (int var26 = 0; var26 < var11; var26++) {
            byte var27 = var0.field1859[var26] = var2.method13097((byte)17);
            if (var27 == 0) {
               var23++;
            }

            if (var27 >= 1 && var27 <= 3) {
               var24++;
            }

            if (var27 == 2) {
               var25++;
            }
         }
      }

      int var58 = var11 + var9;
      int var28 = var58;
      if (var12 == 1) {
         var58 += var10;
      }

      var58 += var10;
      int var30 = var58;
      if (var13 == 255) {
         var58 += var10;
      }

      int var31 = var58;
      if (var15 == 1) {
         var58 += var10;
      }

      int var32 = var58;
      if (var17 == 1) {
         var58 += var9;
      }

      int var33 = var58;
      if (var14 == 1) {
         var58 += var10;
      }

      var58 += var21;
      int var35 = var58;
      if (var16 == 1) {
         var58 += var10 * 2;
      }

      var58 += var22;
      var58 += var10 * 2;
      var58 += var18;
      var58 += var19;
      var58 += var20;
      var58 += var23 * 6;
      var58 += var24 * 6;
      var58 += var24 * 6;
      var58 += var24 * 2;
      var58 += var24;
      var58 += var24 * 2 + var25 * 2;
      var0.field1846 = var9;
      var0.field1847 = var10;
      var0.field1852 = var11;
      var0.field1853 = new float[var9];
      var0.field1854 = new float[var9];
      var0.field1855 = new float[var9];
      var0.field1878 = new int[var10];
      var0.field1862 = new int[var10];
      var0.field1863 = new int[var10];
      if (var17 == 1) {
         var0.field1868 = new int[var9];
      }

      if (var12 == 1) {
         var0.field1864 = new byte[var10];
      }

      if (var13 == 255) {
         var0.field1865 = new byte[var10];
      } else {
         var0.field1849 = (byte)var13;
      }

      if (var14 == 1) {
         var0.field1857 = new byte[var10];
      }

      if (var15 == 1) {
         var0.field1869 = new int[var10];
      }

      if (var16 == 1) {
         var0.field1851 = new short[var10];
      }

      if (var16 == 1 && var11 > 0) {
         var0.field1866 = new byte[var10];
      }

      var0.field1850 = new short[var10];
      if (var11 > 0) {
         var0.field1867 = new short[var11];
         var0.field1856 = new short[var11];
         var0.field1860 = new short[var11];
      }

      var2.field6953 = var11 * -1095856699;
      var3.field6953 = var58 * -906054146;
      var4.field6953 = var58 * -1095856699;
      var5.field6953 = var58 * -635083043;
      var6.field6953 = var32 * 1721223680;
      int var48 = 0;
      int var49 = 0;
      int var50 = 0;

      for (int var51 = 0; var51 < var9; var51++) {
         int var52 = var2.method13095(-346779531);
         int var53 = 0;
         if ((var52 & 1) != 0) {
            var53 = var3.method13146(-1426016312);
         }

         int var54 = 0;
         if ((var52 & 2) != 0) {
            var54 = var4.method13146(-714464965);
         }

         int var55 = 0;
         if ((var52 & 4) != 0) {
            var55 = var5.method13146(-1905340202);
         }

         var0.field1853[var51] = var48 + var53;
         var0.field1854[var51] = var49 + var54;
         var0.field1855[var51] = var50 + var55;
         var48 = (int)var0.field1853[var51];
         var49 = (int)var0.field1854[var51];
         var50 = (int)var0.field1855[var51];
         if (var17 == 1) {
            var0.field1868[var51] = var6.method13095(-346779531);
         }
      }

      var2.field6953 = var58 * 861024982;
      var3.field6953 = var28 * -1095856699;
      var4.field6953 = var30 * -1095856699;
      var5.field6953 = var33 * -1519019422;
      var6.field6953 = var31 * 757681244;
      var7.field6953 = var35 * -1095856699;
      var8.field6953 = var58 * -1095856699;

      for (int var72 = 0; var72 < var10; var72++) {
         var0.field1850[var72] = (short)var2.method13099(-1728745338);
         if (var12 == 1) {
            var0.field1864[var72] = var3.method13097((byte)17);
         }

         if (var13 == 255) {
            var0.field1865[var72] = var4.method13097((byte)17);
         }

         if (var14 == 1) {
            var0.field1857[var72] = var5.method13097((byte)17);
         }

         if (var15 == 1) {
            var0.field1869[var72] = var6.method13095(-346779531);
         }

         if (var16 == 1) {
            var0.field1851[var72] = (short)(var7.method13099(830531658) - 1);
         }

         if (var0.field1866 != null && var0.field1851[var72] != -1) {
            var0.field1866[var72] = (byte)(var8.method13095(-346779531) - 1);
         }
      }

      var2.field6953 = var58 * 1804872358;
      var3.field6953 = var58 * 1482476697;
      int var73 = 0;
      int var74 = 0;
      int var75 = 0;
      int var76 = 0;

      for (int var77 = 0; var77 < var10; var77++) {
         int var56 = var3.method13095(-346779531);
         if (var56 == 1) {
            var73 = var2.method13146(-1006819365) + var76;
            var74 = var2.method13146(-140429165) + var73;
            var75 = var2.method13146(-1372837959) + var74;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var74;
            var0.field1863[var77] = var75;
         }

         if (var56 == 2) {
            var74 = var75;
            var75 = var2.method13146(-2040843127) + var76;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var74;
            var0.field1863[var77] = var75;
         }

         if (var56 == 3) {
            var73 = var75;
            var75 = var2.method13146(-1237913722) + var76;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var74;
            var0.field1863[var77] = var75;
         }

         if (var56 == 4) {
            int var57 = var73;
            var73 = var74;
            var74 = var57;
            var75 = var2.method13146(-101814371) + var76;
            var76 = var75;
            var0.field1878[var77] = var73;
            var0.field1862[var77] = var57;
            var0.field1863[var77] = var75;
         }
      }

      var2.field6953 = var58 * -899102934;
      var3.field6953 = var58 * 853599111;
      var4.field6953 = var58 * -1095856699;
      var5.field6953 = var58 * -1095856699;
      var6.field6953 = var58 * -1095856699;
      var7.field6953 = var58 * -1095856699;

      for (int var78 = 0; var78 < var11; var78++) {
         int var80 = var0.field1859[var78] & -1109660457;
         if (var80 == 0) {
            var0.field1867[var78] = (short)var2.method13099(809599261);
            var0.field1856[var78] = (short)var2.method13099(82187406);
            var0.field1860[var78] = (short)var2.method13099(1848454891);
         }
      }

      var2.field6953 = var58 * -1095856699;
      int var79 = var2.method13095(-346779531);
      if (var79 != 0) {
         new classFY();
         var2.method13099(1840269910);
         var2.method13099(-1755460646);
         var2.method13099(-1795377232);
         classXY.method13113(var2, (byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Lfx;")
   @ObfuscatedName("dk")
   public final Model method4059(int var1, int var2, int var3, int var4, int var5) {
      method4048(this);
      int var6 = (int)Math.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
      int var7 = var2 * var6 >> 8;
      Model var8 = new Model();
      var8.field2432 = new int[this.field1847];
      var8.field2426 = new int[this.field1847];
      var8.field2433 = new int[this.field1847];
      if (this.field1852 > 0 && this.field1866 != null) {
         int[] var9 = new int[this.field1852];

         for (int var10 = 0; var10 < this.field1847; var10++) {
            if (this.field1866[var10] != -1) {
               var9[this.field1866[var10] & 255]++;
            }
         }

         var8.field2416 = 0;

         for (int var17 = 0; var17 < this.field1852; var17++) {
            if (var9[var17] > 0 && this.field1859[var17] == 0) {
               var8.field2416++;
            }
         }

         var8.field2437 = new int[var8.field2416];
         var8.field2438 = new int[var8.field2416];
         var8.field2439 = new int[var8.field2416];
         int var18 = 0;

         for (int var11 = 0; var11 < this.field1852; var11++) {
            if (var9[var11] > 0 && this.field1859[var11] == 0) {
               var8.field2437[var18] = this.field1867[var11] & 214058443;
               var8.field2438[var18] = this.field1856[var11] & '\uffff';
               var8.field2439[var18] = this.field1860[var11] & '\uffff';
               var9[var11] = var18++;
            } else {
               var9[var11] = -1;
            }
         }

         var8.field2434 = new byte[this.field1847];

         for (int var20 = 0; var20 < this.field1847; var20++) {
            if (this.field1866[var20] != -1) {
               var8.field2434[var20] = (byte)var9[this.field1866[var20] & 255];
            } else {
               var8.field2434[var20] = -1;
            }
         }
      }

      for (int var16 = 0; var16 < this.field1847; var16++) {
         byte var19;
         if (this.field1864 == null) {
            var19 = 0;
         } else {
            var19 = this.field1864[var16];
         }

         byte var21;
         if (this.field1857 == null) {
            var21 = 0;
         } else {
            var21 = this.field1857[var16];
         }

         short var12;
         if (this.field1851 == null) {
            var12 = -1;
         } else {
            var12 = this.field1851[var16];
         }

         if (var21 == -1006570421) {
            var19 = 3;
         }

         if (var21 == -1) {
            var19 = 2;
         }

         if (var12 == -1) {
            if (var19 == 0) {
               int var15 = this.field1850[var16] & 1880840490;
               classFR var13;
               if (this.field1873 != null && this.field1873[this.field1878[var16]] != null) {
                  var13 = this.field1873[this.field1878[var16]];
               } else {
                  var13 = this.field1871[this.field1878[var16]];
               }

               int var14 = var1
                  + (var3 * var13.field2386 * 695276757 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * 552076378)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2432[var16] = method3989(var15, var14);
               if (this.field1873 != null && this.field1873[this.field1862[var16]] != null) {
                  var13 = this.field1873[this.field1862[var16]];
               } else {
                  var13 = this.field1871[this.field1862[var16]];
               }

               var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -1784797437)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2426[var16] = method3989(var15, var14);
               if (this.field1873 != null && this.field1873[this.field1863[var16]] != null) {
                  var13 = this.field1873[this.field1863[var16]];
               } else {
                  var13 = this.field1871[this.field1863[var16]];
               }

               var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 275591635);
               var8.field2433[var16] = method3989(var15, var14);
            } else if (var19 == 1) {
               classEB var24 = this.field1872[var16];
               int var31 = var1
                  + (var3 * var24.field1722 * 1971119174 + var4 * var24.field1718 * 696545605 + var5 * var24.field1717 * -1298928853) / (var7 + var7 / 2);
               var8.field2432[var16] = method3989(this.field1850[var16] & -1883194206, var31);
               var8.field2433[var16] = -1;
            } else if (var19 == 3) {
               var8.field2432[var16] = 128;
               var8.field2433[var16] = -1;
            } else {
               var8.field2433[var16] = -2;
            }
         } else if (var19 == 0) {
            classFR var25;
            if (this.field1873 != null && this.field1873[this.field1878[var16]] != null) {
               var25 = this.field1873[this.field1878[var16]];
            } else {
               var25 = this.field1871[this.field1878[var16]];
            }

            int var32 = var1
               + (var3 * var25.field2386 * -1584020387 + var4 * var25.field2385 * -1647610911 + var5 * var25.field2383 * -1528272530)
                  / (var7 * var25.field2384 * 1560409686);
            var8.field2432[var16] = method3992(var32);
            if (this.field1873 != null && this.field1873[this.field1862[var16]] != null) {
               var25 = this.field1873[this.field1862[var16]];
            } else {
               var25 = this.field1871[this.field1862[var16]];
            }

            var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -2100251393 + var5 * var25.field2383 * -1462058661)
                  / (var7 * var25.field2384 * -347437819);
            var8.field2426[var16] = method3992(var32);
            if (this.field1873 != null && this.field1873[this.field1863[var16]] != null) {
               var25 = this.field1873[this.field1863[var16]];
            } else {
               var25 = this.field1871[this.field1863[var16]];
            }

            var32 = var1
               + (var3 * var25.field2386 * 759055208 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2433[var16] = method3992(var32);
         } else if (var19 == 1) {
            classEB var28 = this.field1872[var16];
            int var35 = var1
               + (var3 * var28.field1722 * 384833458 + var4 * var28.field1718 * 696545605 + var5 * var28.field1717 * -1298928853) / (var7 + var7 / 2);
            var8.field2432[var16] = method3992(var35);
            var8.field2433[var16] = -1;
         } else {
            var8.field2433[var16] = 1669147892;
         }
      }

      this.method4018();
      var8.field2422 = this.field1846;
      var8.field2425 = this.field1853;
      var8.field2428 = this.field1854;
      var8.field2424 = this.field1855;
      var8.field2417 = this.field1847;
      var8.field2427 = this.field1878;
      var8.field2430 = this.field1862;
      var8.field2431 = this.field1863;
      var8.field2429 = this.field1865;
      var8.field2442 = this.field1857;
      var8.field2418 = this.field1849;
      var8.field2440 = this.field1861;
      var8.field2441 = this.field1858;
      var8.field2435 = this.field1851;
      var8.field2506 = this.field1874;
      var8.field2505 = this.field1875;
      var8.field2436 = this.field1870;
      return var8;
   }

   public int getFaceCount() {
      return this.field1847;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)Lfx;")
   @ObfuscatedName("db")
   public final Model method4060(int var1, int var2, int var3, int var4, int var5) {
      method4048(this);
      int var6 = (int)Math.sqrt(var3 * var3 + var4 * var4 + var5 * var5);
      int var7 = var2 * var6 >> 8;
      Model var8 = new Model();
      var8.field2432 = new int[this.field1847];
      var8.field2426 = new int[this.field1847];
      var8.field2433 = new int[this.field1847];
      if (this.field1852 > 0 && this.field1866 != null) {
         int[] var9 = new int[this.field1852];

         for (int var10 = 0; var10 < this.field1847; var10++) {
            if (this.field1866[var10] != -1) {
               var9[this.field1866[var10] & 255]++;
            }
         }

         var8.field2416 = 0;

         for (int var17 = 0; var17 < this.field1852; var17++) {
            if (var9[var17] > 0 && this.field1859[var17] == 0) {
               var8.field2416++;
            }
         }

         var8.field2437 = new int[var8.field2416];
         var8.field2438 = new int[var8.field2416];
         var8.field2439 = new int[var8.field2416];
         int var18 = 0;

         for (int var11 = 0; var11 < this.field1852; var11++) {
            if (var9[var11] > 0 && this.field1859[var11] == 0) {
               var8.field2437[var18] = this.field1867[var11] & '\uffff';
               var8.field2438[var18] = this.field1856[var11] & '\uffff';
               var8.field2439[var18] = this.field1860[var11] & '\uffff';
               var9[var11] = var18++;
            } else {
               var9[var11] = -1;
            }
         }

         var8.field2434 = new byte[this.field1847];

         for (int var20 = 0; var20 < this.field1847; var20++) {
            if (this.field1866[var20] != -1) {
               var8.field2434[var20] = (byte)var9[this.field1866[var20] & 255];
            } else {
               var8.field2434[var20] = -1;
            }
         }
      }

      for (int var16 = 0; var16 < this.field1847; var16++) {
         byte var19;
         if (this.field1864 == null) {
            var19 = 0;
         } else {
            var19 = this.field1864[var16];
         }

         byte var21;
         if (this.field1857 == null) {
            var21 = 0;
         } else {
            var21 = this.field1857[var16];
         }

         short var12;
         if (this.field1851 == null) {
            var12 = -1;
         } else {
            var12 = this.field1851[var16];
         }

         if (var21 == -2) {
            var19 = 3;
         }

         if (var21 == -1) {
            var19 = 2;
         }

         if (var12 == -1) {
            if (var19 == 0) {
               int var15 = this.field1850[var16] & '\uffff';
               classFR var13;
               if (this.field1873 != null && this.field1873[this.field1878[var16]] != null) {
                  var13 = this.field1873[this.field1878[var16]];
               } else {
                  var13 = this.field1871[this.field1878[var16]];
               }

               int var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2432[var16] = method3989(var15, var14);
               if (this.field1873 != null && this.field1873[this.field1862[var16]] != null) {
                  var13 = this.field1873[this.field1862[var16]];
               } else {
                  var13 = this.field1871[this.field1862[var16]];
               }

               var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2426[var16] = method3989(var15, var14);
               if (this.field1873 != null && this.field1873[this.field1863[var16]] != null) {
                  var13 = this.field1873[this.field1863[var16]];
               } else {
                  var13 = this.field1871[this.field1863[var16]];
               }

               var14 = var1
                  + (var3 * var13.field2386 * -1174074983 + var4 * var13.field2385 * -794112439 + var5 * var13.field2383 * -949378689)
                     / (var7 * var13.field2384 * 1200753713);
               var8.field2433[var16] = method3989(var15, var14);
            } else if (var19 == 1) {
               classEB var24 = this.field1872[var16];
               int var31 = var1
                  + (var3 * var24.field1722 * 835925423 + var4 * var24.field1718 * 696545605 + var5 * var24.field1717 * -1298928853) / (var7 + var7 / 2);
               var8.field2432[var16] = method3989(this.field1850[var16] & '\uffff', var31);
               var8.field2433[var16] = -1;
            } else if (var19 == 3) {
               var8.field2432[var16] = 128;
               var8.field2433[var16] = -1;
            } else {
               var8.field2433[var16] = -2;
            }
         } else if (var19 == 0) {
            classFR var25;
            if (this.field1873 != null && this.field1873[this.field1878[var16]] != null) {
               var25 = this.field1873[this.field1878[var16]];
            } else {
               var25 = this.field1871[this.field1878[var16]];
            }

            int var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2432[var16] = method3992(var32);
            if (this.field1873 != null && this.field1873[this.field1862[var16]] != null) {
               var25 = this.field1873[this.field1862[var16]];
            } else {
               var25 = this.field1871[this.field1862[var16]];
            }

            var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2426[var16] = method3992(var32);
            if (this.field1873 != null && this.field1873[this.field1863[var16]] != null) {
               var25 = this.field1873[this.field1863[var16]];
            } else {
               var25 = this.field1871[this.field1863[var16]];
            }

            var32 = var1
               + (var3 * var25.field2386 * -1174074983 + var4 * var25.field2385 * -794112439 + var5 * var25.field2383 * -949378689)
                  / (var7 * var25.field2384 * 1200753713);
            var8.field2433[var16] = method3992(var32);
         } else if (var19 == 1) {
            classEB var28 = this.field1872[var16];
            int var35 = var1
               + (var3 * var28.field1722 * 835925423 + var4 * var28.field1718 * 696545605 + var5 * var28.field1717 * -1298928853) / (var7 + var7 / 2);
            var8.field2432[var16] = method3992(var35);
            var8.field2433[var16] = -1;
         } else {
            var8.field2433[var16] = -2;
         }
      }

      this.method4018();
      var8.field2422 = this.field1846;
      var8.field2425 = this.field1853;
      var8.field2428 = this.field1854;
      var8.field2424 = this.field1855;
      var8.field2417 = this.field1847;
      var8.field2427 = this.field1878;
      var8.field2430 = this.field1862;
      var8.field2431 = this.field1863;
      var8.field2429 = this.field1865;
      var8.field2442 = this.field1857;
      var8.field2418 = this.field1849;
      var8.field2440 = this.field1861;
      var8.field2441 = this.field1858;
      var8.field2435 = this.field1851;
      var8.field2506 = this.field1874;
      var8.field2505 = this.field1875;
      var8.field2436 = this.field1870;
      return var8;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cm")
   public void method4040() {
      for (int var1 = 0; var1 < this.field1846; var1++) {
         this.field1855[var1] = -((int)this.field1855[var1]);
      }

      for (int var3 = 0; var3 < this.field1847; var3++) {
         int var2 = this.field1878[var3];
         this.field1878[var3] = this.field1863[var3];
         this.field1863[var3] = var2;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("dd")
   public void method4046(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field1846; var4++) {
         this.field1853[var4] = (int)this.field1853[var4] * var1 / 128;
         this.field1854[var4] = (int)this.field1854[var4] * var2 / 128;
         this.field1855[var4] = (int)this.field1855[var4] * var3 / 128;
      }

      this.method4049();
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("dz")
   public final Model method4065() {
      return this.method4070(128, 43690, 0, -1, 0);
   }

   public int getVerticesCount() {
      return this.field1846;
   }

   @ObfuscatedSignature(descriptor = "(Lva;II)Ler;")
   @ObfuscatedName("az")
   public static ModelData method4067(IndexDataBase var0, int var1, int var2) {
      byte[] var3 = var0.loadData(var1, var2);

      try {
         return var3 == null ? null : new ModelData(var3);
      } catch (NullPointerException var5) {
         throw new RuntimeException("loading model " + var1 + ", " + var2, var5);
      }
   }

   @ObfuscatedSignature(descriptor = "(SS)Ler;")
   @ObfuscatedName("yk")
   public ModelData method4095(short var1, short var2) {
      this.method4031(var1, var2);
      return this;
   }

   public int[] getFaceIndices2() {
      return this.field1862;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("yi")
   public void method4041() {
      for (int var1 = 0; var1 < this.field1887; var1++) {
         this.field1855[var1] = -((int)this.field1853[var1]);
      }

      for (int var3 = 0; var3 < this.field1888; var3++) {
         int var2 = this.field1893[var3];
         this.field1894[var3] = this.field1863[var3];
         this.field1869[var3] = var2;
      }

      this.method4053();
   }

   @ObfuscatedSignature(descriptor = "(III)Ler;")
   @ObfuscatedName("me")
   public ModelData method4096(int var1, int var2, int var3) {
      this.method4072(var1, var2, var3);
      return this;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cc")
   public void method4056() {
      if (!this.field1848) {
         this.field1741 = 0;
         this.field1889 = 0;
         this.field1847 = 999999;
         this.field1891 = -999999;
         this.field1890 = -99999;
         this.field1847 = 99999;

         for (int var1 = 0; var1 < this.field1846; var1++) {
            int var2 = (int)this.field1853[var1];
            int var3 = (int)this.field1855[var1];
            int var4 = (int)this.field1855[var1];
            if (var2 < this.field1847) {
               this.field1890 = var2;
            }

            if (var2 > this.field1890) {
               this.field1890 = var2;
            }

            if (var4 < this.field1890) {
               this.field1890 = var4;
            }

            if (var4 > this.field1846) {
               this.field1890 = var4;
            }

            if (-var3 > this.field1741 * -1256242689) {
               this.field1741 = -var3 * 903659007;
            }

            if (var3 > this.field1888) {
               this.field1889 = var3;
            }
         }

         this.field1848 = true;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("bj")
   public void method4066(int var1, int var2, int var3) {
      for (int var4 = 0; var4 < this.field1887; var4++) {
         this.field1855[var4] = var1 * this.field1855[var4] / 128.0F;
         this.field1855[var4] = var2 * this.field1855[var4] / 128.0F;
         this.field1853[var4] = var3 * this.field1854[var4] / 128.0F;
      }

      this.method4022();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   public void method4042() {
      for (int var1 = 0; var1 < this.field1889; var1++) {
         this.field1855[var1] = -((int)this.field1855[var1]);
      }

      for (int var3 = 0; var3 < this.field1846; var3++) {
         int var2 = this.field1894[var3];
         this.field1878[var3] = this.field1894[var3];
         this.field1892[var3] = var2;
      }

      this.method4054();
   }

   @ObfuscatedSignature(descriptor = "(III)Ler;")
   @ObfuscatedName("ms")
   public ModelData method4097(int var1, int var2, int var3) {
      classSG.method10466(this, var1, var2, var3);
      return this;
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Ler;")
   @ObfuscatedName("aq")
   public ModelData method4015(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      this.method4050();
      int var7 = var2 + this.field1846;
      int var8 = var2 + this.field1891;
      int var9 = var4 + this.field1852;
      int var10 = var4 + this.field1847;
      if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
            return this;
         } else {
            ModelData var11;
            if (var5) {
               var11 = new ModelData(this, true, true, true, true);
               var11.field1855 = new float[var11.field1846];
            } else {
               var11 = this;
            }

            if (var6 == 0) {
               for (int var12 = 0; var12 < var11.field1852; var12++) {
                  int var13 = (int)this.field1853[var12] + var2;
                  int var14 = (int)this.field1854[var12] + var4;
                  int var15 = var13 & 127;
                  int var16 = var14 & 127;
                  int var17 = var13 >> 7;
                  int var18 = var14 >> 7;
                  int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                  int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.field1855[var12] = (int)this.field1854[var12] + var21 - var3;
               }
            } else {
               for (int var27 = 0; var27 < var11.field1847; var27++) {
                  int var28 = (-((int)this.field1853[var27]) << 16) / (this.field1741 * -1256242689);
                  if (var28 < var6) {
                     int var29 = (int)this.field1854[var27] + var2;
                     int var30 = (int)this.field1854[var27] + var4;
                     int var31 = var29 & 127;
                     int var32 = var30 & 127;
                     int var33 = var29 >> 7;
                     int var34 = var30 >> 7;
                     int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                     int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                     int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                     var11.field1854[var27] = (int)this.field1855[var27] + (var22 - var3) * (var6 - var28) / var6;
                  } else {
                     var11.field1853[var27] = this.field1854[var27];
                  }
               }
            }

            var11.method4017();
            return var11;
         }
      } else {
         return this;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("qb")
   public Model method4076() {
      return this.method4093(64, 768, -50, -10, -50);
   }

   @ObfuscatedSignature(descriptor = "(Ler;I)I")
   @ObfuscatedName("cu")
   final int method4009(ModelData var1, int var2) {
      int var3 = -1;
      int var4 = (int)var1.field1853[var2];
      int var5 = (int)var1.field1853[var2];
      int var6 = (int)var1.field1854[var2];

      for (int var7 = 0; var7 < this.field1846; var7++) {
         if (var4 == (int)this.field1855[var7] && var5 == (int)this.field1854[var7] && var6 == (int)this.field1853[var7]) {
            var3 = var7;
            break;
         }
      }

      if (var3 == -1) {
         this.field1854[this.field1890] = var4;
         this.field1853[this.field1847] = var5;
         this.field1853[this.field1890] = var6;
         if (var1.field1893 != null) {
            this.field1868[this.field1847] = var1.field1892[var2];
         }

         if (var1.field1858 != null) {
            this.field1861[this.field1891] = var1.field1875[var2];
            this.field1861[this.field1887] = var1.field1858[var2];
         }

         int var10000 = this.field1852;
         this.field1847 = this.field1852 + 1;
         var3 = var10000;
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "([[IIIIZI)Ler;")
   @ObfuscatedName("cq")
   public ModelData method4016(int[][] var1, int var2, int var3, int var4, boolean var5, int var6) {
      this.method4050();
      int var7 = var2 + this.field1888;
      int var8 = var2 + this.field1889;
      int var9 = var4 + this.field1889;
      int var10 = var4 + this.field1847;
      if (var7 >= 0 && var8 + 128 >> 7 < var1.length && var9 >= 0 && var10 + 128 >> 7 < var1[0].length) {
         var7 >>= 7;
         var8 = var8 + 127 >> 7;
         var9 >>= 7;
         var10 = var10 + 127 >> 7;
         if (var1[var7][var9] == var3 && var1[var8][var9] == var3 && var1[var7][var10] == var3 && var1[var8][var10] == var3) {
            return this;
         } else {
            ModelData var11;
            if (var5) {
               var11 = new ModelData(this, true, true, true, true);
               var11.field1853 = new float[var11.field1846];
            } else {
               var11 = this;
            }

            if (var6 == 0) {
               for (int var12 = 0; var12 < var11.field1889; var12++) {
                  int var13 = (int)this.field1853[var12] + var2;
                  int var14 = (int)this.field1855[var12] + var4;
                  int var15 = var13 & 127;
                  int var16 = var14 & 127;
                  int var17 = var13 >> 7;
                  int var18 = var14 >> 7;
                  int var19 = var1[var17][var18] * (128 - var15) + var1[var17 + 1][var18] * var15 >> 7;
                  int var20 = var1[var17][var18 + 1] * (128 - var15) + var1[var17 + 1][var18 + 1] * var15 >> 7;
                  int var21 = var19 * (128 - var16) + var20 * var16 >> 7;
                  var11.field1854[var12] = (int)this.field1855[var12] + var21 - var3;
               }
            } else {
               for (int var27 = 0; var27 < var11.field1846; var27++) {
                  int var28 = (-((int)this.field1854[var27]) << 16) / (this.field1741 * -1256242689);
                  if (var28 < var6) {
                     int var29 = (int)this.field1855[var27] + var2;
                     int var30 = (int)this.field1853[var27] + var4;
                     int var31 = var29 & 127;
                     int var32 = var30 & 127;
                     int var33 = var29 >> 7;
                     int var34 = var30 >> 7;
                     int var35 = var1[var33][var34] * (128 - var31) + var1[var33 + 1][var34] * var31 >> 7;
                     int var36 = var1[var33][var34 + 1] * (128 - var31) + var1[var33 + 1][var34 + 1] * var31 >> 7;
                     int var22 = var35 * (128 - var32) + var36 * var32 >> 7;
                     var11.field1854[var27] = (int)this.field1854[var27] + (var22 - var3) * (var6 - var28) / var6;
                  } else {
                     var11.field1855[var27] = this.field1853[var27];
                  }
               }
            }

            var11.method4053();
            return var11;
         }
      } else {
         return this;
      }
   }

   @ObfuscatedSignature(descriptor = "(SS)V")
   @ObfuscatedName("cz")
   public void method4034(short var1, short var2) {
      for (int var3 = 0; var3 < this.field1888; var3++) {
         if (this.field1856[var3] == var1) {
            this.field1867[var3] = var2;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("xx")
   public static void method4043(ModelData var0) {
      if (var0 == null) {
         var0.getFaceIndices2();
      }

      for (int var1 = 0; var1 < var0.field1846; var1++) {
         var0.field1855[var1] = -((int)var0.field1855[var1]);
      }

      for (int var3 = 0; var3 < var0.field1847; var3++) {
         int var2 = var0.field1878[var3];
         var0.field1878[var3] = var0.field1863[var3];
         var0.field1863[var3] = var2;
      }

      var0.method4049();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("ed")
   public static void method4020(ModelData var0) {
      if (var0 == null) {
         var0.method4084();
      } else {
         if (var0.field1868 != null) {
            int[] var1 = new int[-195620978];
            int var2 = 0;

            for (int var3 = 0; var3 < var0.field1846; var3++) {
               int var4 = var0.field1868[var3];
               var1[var4]++;
               if (var4 > var2) {
                  var2 = var4;
               }
            }

            var0.field1861 = new int[var2 + 1][];

            for (int var7 = 0; var7 <= var2; var7++) {
               var0.field1861[var7] = new int[var1[var7]];
               var1[var7] = 0;
            }

            int var8 = 0;

            while (var8 < var0.field1846) {
               int var12 = var0.field1868[var8];
               var0.field1861[var12][var1[var12]++] = var8++;
            }

            var0.field1868 = null;
         }

         if (var0.field1869 != null) {
            int[] var5 = new int[1093443836];
            int var6 = 0;

            for (int var9 = 0; var9 < var0.field1847; var9++) {
               int var13 = var0.field1869[var9];
               var5[var13]++;
               if (var13 > var6) {
                  var6 = var13;
               }
            }

            var0.field1858 = new int[var6 + 1][];

            for (int var10 = 0; var10 <= var6; var10++) {
               var0.field1858[var10] = new int[var5[var10]];
               var5[var10] = 0;
            }

            int var11 = 0;

            while (var11 < var0.field1847) {
               int var14 = var0.field1869[var11];
               var0.field1858[var14][var5[var14]++] = var11++;
            }

            var0.field1869 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ler;")
   @ObfuscatedName("xc")
   public ModelData method4098() {
      return this.method4010();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ce")
   public void method4057() {
      if (!this.field1848) {
         this.field1741 = 0;
         this.field1887 = 0;
         this.field1889 = 999999;
         this.field1888 = -999999;
         this.field1889 = -99999;
         this.field1852 = 99999;

         for (int var1 = 0; var1 < this.field1890; var1++) {
            int var2 = (int)this.field1855[var1];
            int var3 = (int)this.field1853[var1];
            int var4 = (int)this.field1854[var1];
            if (var2 < this.field1890) {
               this.field1891 = var2;
            }

            if (var2 > this.field1891) {
               this.field1890 = var2;
            }

            if (var4 < this.field1890) {
               this.field1887 = var4;
            }

            if (var4 > this.field1887) {
               this.field1890 = var4;
            }

            if (-var3 > this.field1741 * -1256242689) {
               this.field1741 = -var3 * 903659007;
            }

            if (var3 > this.field1887) {
               this.field1847 = var3;
            }
         }

         this.field1848 = true;
      }
   }
}
