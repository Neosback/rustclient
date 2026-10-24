import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fa")
public class classFA implements classTL {
   @ObfuscatedName("aq")
   boolean[] field2171;
   @ObfuscatedName("dc")
   public int[] field2180;
   @ObfuscatedName("ay")
   boolean[] field2172;
   @ObfuscatedName("ae")
   public int field2162;
   @ObfuscatedName("ab")
   int[] field2170;
   @ObfuscatedName("af")
   public int[] field2165 = new int[128];
   @ObfuscatedName("as")
   public char field2173;
   @ObfuscatedName("ax")
   public int field2174;
   @ObfuscatedName("xa")
   public int field2179;
   @ObfuscatedName("ac")
   char[] field2164;
   @ObfuscatedName("ag")
   int field2161;
   @ObfuscatedName("np")
   public int field2178;
   @ObfuscatedName("ao")
   int field2167;
   @ObfuscatedName("al")
   int field2168;
   @ObfuscatedName("aj")
   int field2169;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2175 = 128;
   @ObfuscatedName("aa")
   int[] field2166;
   @ObfuscatedName("ad")
   boolean[] field2163;
   @ObfuscatedName("lu")
   public int[] field2181;
   @ToRemove(unused = "true")
   @ObfuscatedName("bo")
   static final int field2177 = 106;
   @ToRemove(unused = "true")
   @ObfuscatedName("ba")
   static final int field2176 = 109;

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("br")
   public int[] method4568() {
      int[] var1 = new int[422327897 * this.field2162];

      for (int var2 = 0; var2 < this.field2162 * 422327897; var2++) {
         var1[var2] = this.field2165[var2];
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod447(int var1, byte var2) {
      if (classTP.field6184) {
         if (this.field2162 * 422327897 < this.field2165.length) {
            int[] var10000 = this.field2165;
            int var10001 = this.field2162 * 422327897;
            this.field2162 = (this.field2162 * 422327897 + 1) * -1228426775;
            var10000[var10001] = var1;
         }
      } else {
         int var3 = var1;
         classFA var6 = this;
         byte var4 = 2;

         try {
            method4550(var6, var3, (byte)5);
            var6.field2172[var3] = true;
            var6.field2171[var3] = true;
            var6.field2163[var3] = false;
            if (var6.field2162 * 422327897 < 128) {
               if (var4 != 2) {
                  throw new IllegalStateException();
               }

               var6.field2165[(var6.field2162 += -1228426775) * 422327897 - 1] = var3;
            }

            boolean var7 = true;
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "fa.ab(" + ')');
         }

         if (this.field2178 < this.field2181.length) {
            this.field2181[this.field2178++] = var1;
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("yh")
   public void method4580() {
      this.field2178 = 0;
      this.field2179 = 0;
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod448(int var1, byte var2) {
      if (classTP.field6184) {
         if (this.field2161 * -1467379791 < this.field2170.length) {
            int[] var10000 = this.field2170;
            int var10001 = this.field2161 * -1467379791;
            this.field2161 = (this.field2161 * -1467379791 + 1) * 269834577;
            var10000[var10001] = var1;
         }
      } else {
         int var3 = var1;
         classFA var6 = this;
         boolean var4 = true;

         try {
            var6.field2172[var3] = false;
            var6.field2171[var3] = false;
            var6.field2163[var3] = true;
            if (-1467379791 * var6.field2161 < 128) {
               var6.field2170[(var6.field2161 += 269834577) * -1467379791 - 1] = var3;
            }

            boolean var7 = true;
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "fa.ag(" + ')');
         }

         if (this.field2179 < this.field2180.length) {
            this.field2180[this.field2179++] = var1;
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("as")
   @Override
   public boolean vmethod446(char var1, int var2) {
      if (!classTP.field6184) {
         char var3 = var1;
         classFA var7 = this;
         int var4 = 1791336663;

         try {
            int var5 = 1793736147 * var7.field2168 + 1 & 127;
            if (var7.field2167 * 1493235637 != var5) {
               var7.field2166[var7.field2168 * 1793736147] = -1;
               var7.field2164[var7.field2168 * 1793736147] = var3;
               var7.field2168 = 1369580123 * var5;
            }

            boolean var8 = false;
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "fa.as(" + ')');
         }
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(ZI)Z")
   @ObfuscatedName("ax")
   @Override
   public boolean vmethod444(boolean var1, int var2) {
      try {
         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fa.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method4552(int var1) {
      this.method4580();

      try {
         this.field2167 = 1238797461 * this.field2169;
         this.field2169 = this.field2168 * 794645003;
         this.field2162 = 0;
         this.field2161 = 0;
         Arrays.fill(this.field2171, false);
         Arrays.fill(this.field2163, false);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fa.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aa")
   public boolean method4557(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 == -1847010979) {
               throw new IllegalStateException();
            }

            if (var1 < 112) {
               return this.field2171[var1];
            }

            if (var2 == -1847010979) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fa.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[Z")
   @ObfuscatedName("rb")
   public boolean[] method4581() {
      return this.field2172;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfa;)[I")
   @ObfuscatedName("do")
   public static int[] method4570(classFA var0) {
      int[] var1 = new int[var0.field2161 * -1467379791];

      for (int var2 = 0; var2 < var0.field2161 * 1822050080; var2++) {
         var1[var2] = var0.field2170[var2];
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIII)Z")
   @ObfuscatedName("dq")
   static boolean method4577(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      Scene.method4476(var0, var1, var2, var3, var4, var5, var6);

      try {
         Scene var8 = classCQ.field1218.field1687;
         if (var5 == 1946452601 * classKV.field4032.field4036) {
            if (var7 != -208526133) {
               throw new IllegalStateException();
            }

            WallObject var9 = var8.method4515(var0, var1, var2);
            if (null != var9) {
               if (var7 != -208526133) {
                  throw new IllegalStateException();
               }

               int var10 = classCN.method2542(7159317397185328723L * var9.field2238);
               if (78062377 * classKB.field3515.field3536 == var3) {
                  if (var7 != -208526133) {
                     throw new IllegalStateException();
                  }

                  var9.field2247 = new DynamicObject(classCQ.field1218, var10, 2, var4 + 4, var0, var1, var2, var6, false, var9.field2247);
                  var9.field2246 = new DynamicObject(classCQ.field1218, var10, 2, var4 + 1 & 3, var0, var1, var2, var6, false, var9.field2246);
               } else {
                  var9.field2247 = new DynamicObject(classCQ.field1218, var10, var3, var4, var0, var1, var2, var6, false, var9.field2247);
               }

               Scene.method4489(var0, var1, var2, var3, var4, var5, var6);
               return true;
            }
         } else if (var5 == 1946452601 * classKV.field4034.field4036) {
            if (var7 != -208526133) {
               throw new IllegalStateException();
            }

            DecorativeObject var12 = var8.method4498(var0, var1, var2);
            if (null != var12) {
               if (var7 != -208526133) {
                  throw new IllegalStateException();
               }

               label111: {
                  int var15 = classCN.method2542(-3608662145112923099L * var12.field2264);
                  if (var3 != classKB.field3518.field3536 * 78062377) {
                     if (var7 != -208526133) {
                        throw new IllegalStateException();
                     }

                     if (78062377 * classKB.field3523.field3536 != var3) {
                        if (78062377 * classKB.field3520.field3536 == var3) {
                           if (var7 != -208526133) {
                              throw new IllegalStateException();
                           }

                           var12.field2277 = new DynamicObject(classCQ.field1218, var15, 4, 4 + var4, var0, var1, var2, var6, false, var12.field2277);
                        } else if (var3 == 78062377 * classKB.field3521.field3536) {
                           if (var7 != -208526133) {
                              throw new IllegalStateException();
                           }

                           var12.field2277 = new DynamicObject(classCQ.field1218, var15, 4, (var4 + 2 & 3) + 4, var0, var1, var2, var6, false, var12.field2277);
                        } else if (classKB.field3522.field3536 * 78062377 == var3) {
                           var12.field2277 = new DynamicObject(classCQ.field1218, var15, 4, 4 + var4, var0, var1, var2, var6, false, var12.field2277);
                           var12.field2276 = new DynamicObject(classCQ.field1218, var15, 4, 4 + (var4 + 2 & 3), var0, var1, var2, var6, false, var12.field2276);
                        }
                        break label111;
                     }

                     if (var7 != -208526133) {
                        throw new IllegalStateException();
                     }
                  }

                  var12.field2277 = new DynamicObject(classCQ.field1218, var15, 4, var4, var0, var1, var2, var6, false, var12.field2277);
               }

               Scene.method4489(var0, var1, var2, var3, var4, var5, var6);
               return true;
            }
         } else if (1946452601 * classKV.field4033.field4036 == var5) {
            if (var7 != -208526133) {
               throw new IllegalStateException();
            }

            GameObject var13 = Scene.method4487(var8, var0, var1, var2);
            if (var3 == 78062377 * classKB.field3524.field3536) {
               var3 = classKB.field3519.field3536 * 78062377;
            }

            if (null != var13) {
               if (var7 != -208526133) {
                  throw new IllegalStateException();
               }

               var13.field2198 = new DynamicObject(
                  classCQ.field1218, classCN.method2542(var13.field2183 * 1745853678706228013L), var3, var4, var0, var1, var2, var6, false, var13.field2198
               );
               Scene.method4489(var0, var1, var2, var3, var4, var5, var6);
               return true;
            }
         } else if (1946452601 * classKV.field4035.field4036 == var5) {
            if (var7 != -208526133) {
               throw new IllegalStateException();
            }

            GroundObject var14 = var8.method4468(var0, var1, var2);
            if (null != var14) {
               if (var7 != -208526133) {
                  throw new IllegalStateException();
               }

               var14.field1814 = new DynamicObject(
                  classCQ.field1218, classCN.method2542(var14.field1813 * 5132338419383099835L), 22, var4, var0, var1, var2, var6, false, var14.field1814
               );
               Scene.method4489(var0, var1, var2, var3, var4, var5, var6);
               return true;
            }
         }

         Scene.method4489(var0, var1, var2, var3, var4, var5, var6);
         return false;
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "fa.dq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bn")
   public boolean method4562(int var1) {
      return var1 >= 0 && var1 < 112 ? this.field2172[var1] : false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwd;)I")
   @ObfuscatedName("dc")
   public static int method4549(classWD var0) {
      return 425005993 * var0.field6668;
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("al")
   public int[] method4578(int var1) {
      int[] var2 = new int[this.field2178];

      for (int var3 = 0; var3 < this.field2178; var3++) {
         var2[var3] = this.field2181[var3];
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("an")
   @Override
   public boolean vmethod443(char var1) {
      int var2 = -2063803178 * this.field2168 + 1 & 127;
      if (this.field2167 * 1417519993 != var2) {
         this.field2166[this.field2168 * 1793736147] = -1;
         this.field2164[this.field2168 * 1062444591] = var1;
         this.field2168 = 1369580123 * var2;
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("av")
   @Override
   public boolean vmethod438(int var1) {
      method4550(this, var1, (byte)5);
      this.field2172[var1] = true;
      this.field2171[var1] = true;
      this.field2163[var1] = false;
      if (this.field2162 * 1684796856 < 128) {
         this.field2165[(this.field2162 += -1070132417) * -1712614544 - 1] = var1;
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aw")
   @Override
   public boolean vmethod440(int var1) {
      this.field2172[var1] = false;
      this.field2171[var1] = false;
      this.field2163[var1] = true;
      if (-1467379791 * this.field2161 < -344169988) {
         this.field2170[(this.field2161 += -1510737587) * -1467379791 - 1] = var1;
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ac")
   public boolean method4563(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= -270403001) {
               throw new IllegalStateException();
            }

            if (var1 < 112) {
               return this.field2172[var1];
            }

            if (var2 <= -270403001) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fa.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   @Override
   public boolean vmethod441(int var1) {
      this.field2172[var1] = false;
      this.field2171[var1] = false;
      this.field2163[var1] = true;
      if (-1467379791 * this.field2161 < 128) {
         this.field2170[(this.field2161 += 269834577) * -1467379791 - 1] = var1;
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("in")
   public void method4579() {
      this.field2181 = new int[this.field2165.length];
      this.field2180 = new int[this.field2170.length];
   }

   @ObfuscatedSignature(descriptor = "(Lfa;IB)V")
   @ObfuscatedName("qs")
   public static void method4550(classFA var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method4551(var1, var2);
      } else {
         try {
            int var3 = 1 + var0.field2168 * 1793736147 & 127;
            if (var0.field2167 * 1493235637 != var3) {
               if (var2 != 5) {
                  throw new IllegalStateException();
               }

               var0.field2166[var0.field2168 * 1793736147] = var1;
               var0.field2164[1793736147 * var0.field2168] = 0;
               var0.field2168 = 1369580123 * var3;
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "fa.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("au")
   public boolean method4558(int var1) {
      return var1 >= 0 && var1 < 112 ? this.field2171[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "(Z)Z")
   @ObfuscatedName("am")
   @Override
   public boolean vmethod445(boolean var1) {
      return false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method4553() {
      this.field2167 = 1238797461 * this.field2169;
      this.field2169 = this.field2168 * -1159169878;
      this.field2162 = 0;
      this.field2161 = 0;
      Arrays.fill(this.field2171, false);
      Arrays.fill(this.field2163, false);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public final boolean method4554() {
      if (1493235637 * this.field2167 == 55912537 * this.field2169) {
         return false;
      } else {
         this.field2174 = -1809238967 * this.field2166[this.field2167 * 1493235637];
         this.field2173 = this.field2164[1493235637 * this.field2167];
         this.field2167 = 44662429 * (this.field2167 * 1493235637 + 1 & 127);
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ad")
   public boolean method4559(int var1) {
      return var1 >= 0 && var1 < -1630000651 ? this.field2171[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ao")
   public boolean method4567(int var1, byte var2) {
      try {
         if (var1 >= 0) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            if (var1 < 112) {
               return this.field2163[var1];
            }

            if (var2 >= 0) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "fa.ao(" + ')');
      }
   }

   public classFA() {
      this.field2162 = 0;
      this.field2170 = new int[128];
      this.field2161 = 0;
      this.field2164 = new char[128];
      this.field2166 = new int[128];
      this.field2167 = 0;
      this.field2168 = 0;
      this.field2169 = 0;
      this.field2172 = new boolean[112];
      this.field2171 = new boolean[112];
      this.field2163 = new boolean[112];
      this.method4579();
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ai")
   public boolean method4560(int var1) {
      return var1 >= 0 && var1 < 112 ? this.field2171[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ar")
   @Override
   public boolean vmethod439(int var1) {
      method4550(this, var1, (byte)5);
      this.field2172[var1] = true;
      this.field2171[var1] = true;
      this.field2163[var1] = false;
      if (this.field2162 * 422327897 < 128) {
         this.field2165[(this.field2162 += -1228426775) * 422327897 - 1] = var1;
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("ae")
   public final boolean method4555(short var1) {
      try {
         if (1493235637 * this.field2167 == 55912537 * this.field2169) {
            return false;
         } else {
            this.field2174 = -1809238967 * this.field2166[this.field2167 * 1493235637];
            this.field2173 = this.field2164[1493235637 * this.field2167];
            this.field2167 = 44662429 * (this.field2167 * 1493235637 + 1 & 127);
            return true;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "fa.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaa;Ljava/lang/String;IIIII)V")
   @ObfuscatedName("tf")
   public static void method4573(AbstractFont var0, String var1, int var2, int var3, int var4, int var5, int var6) {
      if (var0 == null) {
         var0.method41(var1, var2);
      }

      if (var1 != null) {
         var0.method101(var4, var5);
         AbstractFont.field13 = var6;
         var0.method110(var1, var2, var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("hc")
   public boolean method4582(int var1) {
      return this.method4563(var1, 1486897778);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfa;I)Z")
   @ObfuscatedName("qu")
   public static boolean method4564(classFA var0, int var1) {
      if (var0 == null) {
         var0.vmethod442(var1);
      }

      return var1 >= 0 && var1 < 112 ? var0.field2172[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bc")
   public boolean method4565(int var1) {
      return var1 >= 0 && var1 < 112 ? this.field2172[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bp")
   public int[] method4569() {
      int[] var1 = new int[422327897 * this.field2162];

      for (int var2 = 0; var2 < this.field2162 * 422327897; var2++) {
         var1[var2] = this.field2165[var2];
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bx")
   public boolean method4566(int var1) {
      return var1 >= 0 && var1 < 112 ? this.field2172[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "(Lsp;Ldh;I)Z")
   @ObfuscatedName("ca")
   static boolean method4576(WorldEntity var0, Actor var1, int var2) {
      try {
         if (null != var0) {
            if (var2 != -1555620546) {
               throw new IllegalStateException();
            }

            if (var1 != null) {
               int var3 = -14785344 * var1.field1439;
               int var4 = -1547553299 * var1.field1484 - var3;
               int var5 = -1272026483 * var1.field1486 - var3;
               int var6 = -1547553299 * var1.field1484 + var3 - 1;
               int var7 = var3 + -1272026483 * var1.field1486 - 1;
               classVL var8 = classKO.method6546(524131857);
               var8.method12064(
                  var0.field6024.method8515(-1030535322), var0.vmethod368(311606126), var0.vmethod371(-1383677015), var0.method10604(-914918388), 1946119928
               );
               boolean var9 = classSW.method10741(var8, var4, var5, var6, var7, 1183312927);
               var8.method12044(586690183);
               return var9;
            }

            if (var2 != -1555620546) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "fa.ca(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bg")
   public int[] method4571() {
      int[] var1 = new int[this.field2161 * -1467379791];

      for (int var2 = 0; var2 < this.field2161 * -1467379791; var2++) {
         var1[var2] = this.field2170[var2];
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("bj")
   public int[] method4572() {
      int[] var1 = new int[this.field2161 * -1467379791];

      for (int var2 = 0; var2 < this.field2161 * -1467379791; var2++) {
         var1[var2] = this.field2170[var2];
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lfa;I)[I")
   @ObfuscatedName("ya")
   public static int[] method4574(classFA var0, int var1) {
      if (var0 == null) {
         return var0.method4575(var1);
      } else {
         int[] var2 = new int[var0.field2179];

         for (int var3 = 0; var3 < var0.field2179; var3++) {
            var2[var3] = var0.field2180[var3];
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("at")
   @Override
   public boolean vmethod442(int var1) {
      this.field2172[var1] = false;
      this.field2171[var1] = false;
      this.field2163[var1] = true;
      if (958590431 * this.field2161 < 1246857244) {
         this.field2170[(this.field2161 += -1568993007) * 1885719854 - 1] = var1;
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(IB)Lze;")
   @ObfuscatedName("af")
   public static DBRowConfig method4556(int var0, byte var1) {
      try {
         DBRowConfig var2 = (DBRowConfig)NodeCache.method6460(DBRowConfig.field7169, var0);
         if (null != var2) {
            return var2;
         } else {
            byte[] var3 = DBRowConfig.field7175.method11942(38, var0, -2122935696);
            var2 = new DBRowConfig(new classXY(var3), var0, -1);
            DBRowConfig.field7169.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "fa.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ap")
   public boolean method4561(int var1) {
      return var1 >= 0 && var1 < 112 ? this.field2171[var1] : false;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("az")
   void method4551(int var1, byte var2) {
      try {
         int var3 = 1 + this.field2169 * 1793736147 & 127;
         if (this.field2161 * 1493235637 != var3) {
            if (var2 != 5) {
               throw new IllegalStateException();
            }

            this.field2166[this.field2169 * 1793736147] = var1;
            this.field2164[1793736147 * this.field2168] = 0;
            this.field2161 = 1369580123 * var3;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "fa.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("aj")
   public int[] method4575(int var1) {
      int[] var2 = new int[this.field2179];

      for (int var3 = 0; var3 < this.field2179; var3++) {
         var2[var3] = this.field2165[var3];
      }

      return var2;
   }
}
