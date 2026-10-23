import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sn")
public class classSN extends classVJ {
   @ObfuscatedName("ic")
   public float[] field5996;
   @ObfuscatedSignature(descriptor = "Lem;")
   @ObfuscatedName("as")
   public classEM field5981;
   @ObfuscatedName("bh")
   public byte[] field5994;
   @ObfuscatedName("eh")
   public short[] field6003;
   @ObfuscatedName("si")
   public int[] field6002;
   @ObfuscatedName("jv")
   public float[] field5997;
   @ObfuscatedName("ax")
   int field5980;
   @ObfuscatedName("pj")
   public int[] field6000;
   @ObfuscatedSignature(descriptor = "[[Lsu;")
   @ObfuscatedName("ab")
   public classSU[][] field6004;
   @ObfuscatedName("ae")
   int field5989;
   @ObfuscatedName("ix")
   public static final float field5987 = Math.ulp(1.0F);
   @ObfuscatedName("de")
   public int[] field5998;
   @ObfuscatedName("va")
   public static final float[] field5986 = new float[5];
   @ObfuscatedName("aa")
   Future field5982;
   @ObfuscatedSignature(descriptor = "[[Lsu;")
   @ObfuscatedName("ag")
   classSU[][] field6005;
   @ObfuscatedName("ac")
   boolean field5993;
   @ObfuscatedName("up")
   public static final float[] field5985 = new float[4];
   @ObfuscatedName("bf")
   public float[] field5995;
   @ObfuscatedName("iv")
   public int[] field5999;
   @ObfuscatedName("pk")
   public int[] field6001;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("dt")
   public static NodeCache field5988 = new NodeCache(128);
   @ObfuscatedName("az")
   static int field5991;
   @ObfuscatedName("ao")
   List field5983;
   @ToRemove(unused = "true")
   @ObfuscatedName("ce")
   static final int field5992 = 20;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field5990 = 16;

   @ObfuscatedSignature(descriptor = "(Lsn;Luu;ILsg;II)V")
   @ObfuscatedName("wh")
   public static void method10566(classSN var0, classUU var1, int var2, classSG var3, int var4, int var5) {
      if (var0 == null) {
         var0.method10568(var1, var2, var3, var2, var2);
      } else {
         try {
            float[] var6 = var3.method10453(-1268206765 * var0.field5989, -2034173247);
            float var7 = var6[0];
            float var8 = var6[1];
            float var9 = var6[2];
            if (null != var0.field6005[var2]) {
               if (var5 == -2059694667) {
                  return;
               }

               classSU var10 = var0.field6005[var2][0];
               classSU var11 = var0.field6005[var2][1];
               classSU var12 = var0.field6005[var2][2];
               if (null != var10) {
                  if (var5 == -2059694667) {
                     throw new IllegalStateException();
                  }

                  var7 = var10.method10709(var4, -805817691);
               }

               if (null != var11) {
                  var8 = var11.method10709(var4, -1732400191);
               }

               if (var12 != null) {
                  if (var5 == -2059694667) {
                     return;
                  }

                  var9 = var12.method10709(var4, -544400739);
               }
            }

            classUL var16 = classAV.method715((byte)0);
            var16.method11435(1.0F, 0.0F, 0.0F, var7, (short)-24168);
            classUL var17 = classAV.method715((byte)0);
            var17.method11435(0.0F, 1.0F, 0.0F, var8, (short)-26915);
            classUL var18 = classAV.method715((byte)0);
            var18.method11435(0.0F, 0.0F, 1.0F, var9, (short)-30774);
            classUL var13 = classAV.method715((byte)0);
            var13.method11454(var18, 1591236864);
            var13.method11454(var16, 1591236864);
            var13.method11454(var17, 1591236864);
            classUU var14 = classRM.method10172(1778071523);
            var14.method11661(var13, 777754821);
            var1.method11656(var14, -382262825);
            classUL.method11416(var16, -1304519495);
            classUL.method11416(var17, -1193863561);
            classUL.method11416(var18, -701725761);
            classUL.method11416(var13, -207024086);
            var14.method11615(1805636757);
         } catch (RuntimeException var15) {
            throw classEG.method3899(var15, "sn.ax(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;I)Lsn;")
   @ObfuscatedName("al")
   public static classSN method10552(IndexDataBase var0, IndexDataBase var1, int var2) {
      byte[] var4 = var0.method11937(var2 >> 16 & 65535, var2 & 65535, -377859792);
      boolean var3;
      if (null == var4) {
         var3 = false;
      } else {
         int var5 = (var4[1] & 255) << 8 | var4[2] & 255;
         byte[] var6 = var1.method11937(var5, 0, -697260666);
         var3 = null != var6;
      }

      if (!var3) {
         return null;
      } else {
         if (classXN.field6889 == null) {
            field5991 = Runtime.getRuntime().availableProcessors() * 1058142237;
            classXN.field6889 = new ThreadPoolExecutor(
               0, -517436875 * field5991, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100 + -204079948 * field5991), new classSF()
            );
         }

         try {
            return new classSN(var0, var1, var2);
         } catch (Exception var7) {
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;I)V")
   @ObfuscatedName("at")
   void method10567(classUU var1, int var2, classSG var3, int var4) {
      float[] var5 = var3.method10453(-1268206765 * this.field5989, -2034173247);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      if (null != this.field6005[var2]) {
         classSU var9 = this.field6005[var2][0];
         classSU var10 = this.field6005[var2][1];
         classSU var11 = this.field6005[var2][2];
         if (null != var9) {
            var6 = var9.method10709(var4, -1557225341);
         }

         if (null != var10) {
            var7 = var10.method10709(var4, -1949484415);
         }

         if (var11 != null) {
            var8 = var11.method10709(var4, -1531153766);
         }
      }

      classUL var14 = classAV.method715((byte)0);
      var14.method11435(1.0F, 0.0F, 0.0F, var6, (short)-16336);
      classUL var15 = classAV.method715((byte)0);
      var15.method11435(0.0F, 1.0F, 0.0F, var7, (short)-25814);
      classUL var16 = classAV.method715((byte)0);
      var16.method11435(0.0F, 0.0F, 1.0F, var8, (short)-20889);
      classUL var12 = classAV.method715((byte)0);
      var12.method11454(var16, 1591236864);
      var12.method11454(var14, 1591236864);
      var12.method11454(var15, 1591236864);
      classUU var13 = classRM.method10172(1778071523);
      var13.method11661(var12, 777754821);
      var1.method11656(var13, -1150667124);
      classUL.method11416(var14, -2065053766);
      classUL.method11416(var15, 1700567985);
      classUL.method11416(var16, -759227899);
      classUL.method11416(var12, -1807500268);
      var13.method11615(1870675289);
   }

   @ObfuscatedSignature(descriptor = "(ILsg;II)V")
   @ObfuscatedName("ar")
   public void method10564(int var1, classSG var2, int var3, int var4) {
      classUU var5 = classRM.method10172(1778071523);
      method10566(this, var5, var3, var2, var1, 1852786445);
      method10571(this, var5, var3, var2, var1, (short)256);
      this.method10569(var5, var3, var2, var1, (byte)-9);
      var2.method10445(var5, 380315265);
      var5.method11615(2134675276);
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("vk")
   public boolean method10590(int var1, int var2, int var3, int var4) {
      int var5 = this.field6002[var1];
      if (var5 < 0 || this.field6003[var5] > var2 || var5 + 1 < var4 && this.field6003[var5 + 1] <= var2) {
         int var6 = var4 - var3;
         if (var6 > 0) {
            int var7 = var3;
            int var8 = var4 - 1;

            do {
               int var9 = var8 + var7 >> 1;
               if (var2 < this.field6003[var9]) {
                  if (var2 > this.field6003[var9 - 1]) {
                     var5 = var9 - 1;
                     break;
                  }

                  var8 = var9 - 1;
               } else {
                  if (var2 <= this.field6003[var9]) {
                     var5 = var9;
                     break;
                  }

                  if (var2 < this.field6003[var9 + 1]) {
                     var5 = var9;
                     break;
                  }

                  var7 = var9 + 1;
               }
            } while (var7 <= var8);
         }

         assert var5 != -1;

         if (var5 != this.field6002[var1]) {
            this.field6002[var1] = var5;
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method10559(int var1) {
      try {
         return this.field5989 * -1268206765;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sn.ab(" + 41);
      }
   }

   classSN(IndexDataBase var1, IndexDataBase var2, int var3) {
      this.field5980 = var3 * -1390589219;
      classXY var4 = new classXY(var1.loadData(this.field5980 * 36494709 >> 16 & 65535, this.field5980 * 36494709 & 65535));
      int var5 = var4.method13322();
      int var6 = var4.method13312();
      byte[] var7 = var2.method11949(var6, 0);
      this.field5981 = new classEM(var6, var7);
      this.field5982 = classXN.field6889.submit(() -> this.method10577(var4));
      classXN.field6889.submit(() -> this.field5981.field1786.method3887());
      this.field5983 = Collections.emptyList();
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;IB)V")
   @ObfuscatedName("ac")
   void method10569(classUU var1, int var2, classSG var3, int var4, byte var5) {
      try {
         float[] var6 = var3.method10460(this.field5989 * -1268206765, -316051969);
         float var7 = var6[0];
         float var8 = var6[1];
         float var9 = var6[2];
         if (this.field6005[var2] != null) {
            classSU var10 = this.field6005[var2][3];
            classSU var11 = this.field6005[var2][4];
            classSU var12 = this.field6005[var2][5];
            if (var10 != null) {
               if (var5 >= 0) {
                  throw new IllegalStateException();
               }

               var7 = var10.method10709(var4, -1619814348);
            }

            if (null != var11) {
               if (var5 >= 0) {
                  throw new IllegalStateException();
               }

               var8 = var11.method10709(var4, -632917876);
            }

            if (null != var12) {
               if (var5 >= 0) {
                  throw new IllegalStateException();
               }

               var9 = var12.method10709(var4, -1727092833);
            }
         }

         var1.field6391 = var7;
         var1.field6399 = var8;
         var1.field6395 = var9;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "sn.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrl21;ILsg;II)V")
   @ObfuscatedName("vg")
   public void method10589(rl21 var1, int var2, classSG var3, int var4, int var5) {
      float var6 = var2 + (float)classMG.field4498;
      classUU var7 = classUU.method11711();
      this.method10581(var7, var4, var3, var6);
      this.method10588(var7, var4, var3, var6);
      this.method10587(var7, var4, var3, var6);
      var1.method10099(var7, var4);
      var7.method11729();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;I)Lsn;")
   @ObfuscatedName("ao")
   public static classSN method10553(IndexDataBase var0, IndexDataBase var1, int var2) {
      byte[] var4 = var0.method11937(var2 >> 16 & 65535, var2 & 1551507207, -214121856);
      boolean var3;
      if (null == var4) {
         var3 = false;
      } else {
         int var5 = (var4[1] & -1912984364) << 8 | var4[2] & -1630191000;
         byte[] var6 = var1.method11937(var5, 0, 1596615103);
         var3 = null != var6;
      }

      if (!var3) {
         return null;
      } else {
         if (classXN.field6889 == null) {
            field5991 = Runtime.getRuntime().availableProcessors() * 1058142237;
            classXN.field6889 = new ThreadPoolExecutor(
               0, -517436875 * field5991, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(-430134785 + -456228500 * field5991), new classSF()
            );
         }

         try {
            return new classSN(var0, var1, var2);
         } catch (Exception var7) {
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ILsg;II)V")
   @ObfuscatedName("ai")
   public void method10565(int var1, classSG var2, int var3, int var4) {
      classUU var5 = classRM.method10172(1778071523);
      method10566(this, var5, var3, var2, var1, 1119198647);
      method10571(this, var5, var3, var2, var1, (short)256);
      this.method10569(var5, var3, var2, var1, (byte)-85);
      var2.method10445(var5, -45412633);
      var5.method11615(1861208492);
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ae")
   public boolean method10576(byte var1) {
      Future var2 = this.field5982;
      if (var2 == null) {
         return true;
      } else if (var2.isDone()) {
         this.field5982 = null;
         this.field5983 = null;
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ay")
   void method10555(classXY var1, int var2) {
      var1.method13099(676450745);
      var1.method13099(1682992360);
      this.field5989 = var1.method13095(-346779531) * -514062117;
      int var3 = var1.method13099(1198305036);
      this.field6005 = new classSU[this.field5981.method3948((byte)-6).method3864(-1583038942)][];
      this.field6004 = new classSU[this.field5981.method3947(532989419)][];
      classSV[] var4 = new classSV[var3];

      for (int var5 = 0; var5 < var3; var5++) {
         int var7 = var1.method13095(-346779531);
         classSK var8 = (classSK)classOF.method8436(classRA.method9974(-480535655), var7, 537646506);
         if (null == var8) {
            var8 = classSK.field5966;
         }

         int var9 = var1.method13146(-391173161);
         classSH var10 = IntProjection.method4109(var1.method13095(-346779531), 2101652372);
         classSU var11 = new classSU();
         var11.method10702(var1, var2, -1653980620);
         var4[var5] = new classSV(this, var11, var8, var10, var9);
         int var12 = var8.method10507(1572322037);
         classSU[][] var13;
         if (var8 == classSK.field5971) {
            var13 = this.field6005;
         } else {
            var13 = this.field6004;
         }

         if (null == var13[var9]) {
            var13[var9] = new classSU[var12];
         }

         if (classSK.field5970 == var8) {
            this.field5993 = true;
         }
      }

      int var14 = var3 / (-517436875 * field5991);
      int var6 = var3 % (-517436875 * field5991);
      int var16 = 0;

      for (int var17 = 0; var17 < field5991 * -517436875; var17++) {
         int var15 = var16;
         var16 += var14;
         if (var6 > 0) {
            var16++;
            var6--;
         }

         if (var15 == var16) {
            break;
         }

         this.field5983.add(classXN.field6889.submit(new classTB(this, var15, var16, var4)));
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method10558() {
      if (null == this.field5982 && null == this.field5983) {
         return true;
      } else {
         if (this.field5982 != null) {
            if (!this.field5982.isDone()) {
               return false;
            }

            this.field5982 = null;
         }

         boolean var1 = true;

         for (int var2 = 0; var2 < this.field5983.size(); var2++) {
            if (!((Future)this.field5983.get(var2)).isDone()) {
               var1 = false;
            } else {
               this.field5983.remove(var2);
               var2--;
            }
         }

         if (!var1) {
            return false;
         } else {
            this.field5983 = null;
            return true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsn;Lxy;II)V")
   @ObfuscatedName("vc")
   public static void method10556(classSN var0, classXY var1, int var2, int var3) {
      if (var0 == null) {
         var0.method10557(var1, var2, var2);
      } else {
         try {
            var1.method13099(-1008441641);
            var1.method13099(1360168439);
            var0.field5989 = var1.method13095(-346779531) * -514062117;
            int var4 = var1.method13099(-1112808975);
            var0.field6005 = new classSU[var0.field5981.method3948((byte)12).method3864(1786141898)][];
            var0.field6004 = new classSU[var0.field5981.method3947(162047720)][];
            classSV[] var5 = new classSV[var4];

            for (int var6 = 0; var6 < var4; var6++) {
               if (var3 == -435781219) {
                  throw new IllegalStateException();
               }

               int var8 = var1.method13095(-346779531);
               classSK var9 = (classSK)classOF.method8436(classRA.method9974(-819351264), var8, 714166249);
               if (null == var9) {
                  if (var3 == -435781219) {
                     return;
                  }

                  var9 = classSK.field5966;
               }

               int var10 = var1.method13146(-1383485269);
               classSH var11 = IntProjection.method4109(var1.method13095(-346779531), -2064244240);
               classSU var12 = new classSU();
               var12.method10702(var1, var2, -1970030991);
               var5[var6] = new classSV(var0, var12, var9, var11, var10);
               int var13 = var9.method10507(1572322037);
               classSU[][] var14;
               if (var9 == classSK.field5971) {
                  if (var3 == -435781219) {
                     return;
                  }

                  var14 = var0.field6005;
               } else {
                  var14 = var0.field6004;
               }

               if (null == var14[var10]) {
                  if (var3 == -435781219) {
                     return;
                  }

                  var14[var10] = new classSU[var13];
               }

               if (classSK.field5970 == var9) {
                  var0.field5993 = true;
               }
            }

            int var16 = var4 / (-517436875 * field5991);
            int var7 = var4 % (-517436875 * field5991);
            int var18 = 0;

            for (int var19 = 0; var19 < field5991 * -517436875; var19++) {
               if (var3 == -435781219) {
                  throw new IllegalStateException();
               }

               int var17 = var18;
               var18 += var16;
               if (var7 > 0) {
                  if (var3 == -435781219) {
                     throw new IllegalStateException();
                  }

                  var18++;
                  var7--;
               }

               if (var17 == var18) {
                  if (var3 == -435781219) {
                     return;
                  }
                  break;
               }

               var0.field5983.add(classXN.field6889.submit(new classTB(var0, var17, var18, var5)));
            }

            return;
         } catch (RuntimeException var15) {
            throw classEG.method3899(var15, "sn.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;F)V")
   @ObfuscatedName("kz")
   public void method10588(classUU var1, int var2, classSG var3, float var4) {
      float[] var5 = var3.method10480(this.field5989 * -1268206765);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      int var9 = this.field5999[var2 * 9 + 6];
      int var10 = this.field5999[var2 * 9 + 7];
      int var11 = this.field5999[var2 * 9 + 8];
      if (var9 != 0) {
         var6 = this.method10585(var9, var4);
      }

      if (var10 != 0) {
         var7 = this.method10585(var10, var4);
      }

      if (var11 != 0) {
         var8 = this.method10585(var11, var4);
      }

      classUU var12 = classUU.method11711();
      var12.method11723(var6, var7, var8);
      var1.method11728(var12);
      var12.method11729();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   public int method10560() {
      return this.field5989 * -1268206765;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ef")
   public boolean method10591() {
      return this.method10576((byte)-20);
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;F)V")
   @ObfuscatedName("hq")
   public void method10581(classUU var1, int var2, classSG var3, float var4) {
      float[] var5 = var3.method10479(this.field5989 * -1268206765);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      int var9 = this.field5999[var2 * 9 + 0];
      int var10 = this.field5999[var2 * 9 + 1];
      int var11 = this.field5999[var2 * 9 + 2];
      if (var9 != 0) {
         var6 = this.method10585(var9, var4);
      }

      if (var10 != 0) {
         var7 = this.method10585(var10, var4);
      }

      if (var11 != 0) {
         var8 = this.method10585(var11, var4);
      }

      classUL var12 = classUL.method11470();
      classUL var13 = classUL.method11470();
      var13.method11466(0.0F, 0.0F, 1.0F, var8);
      var12.method11469(var13);
      var13.method11466(1.0F, 0.0F, 0.0F, var6);
      var12.method11469(var13);
      var13.method11466(0.0F, 1.0F, 0.0F, var7);
      var12.method11469(var13);
      classUU var14 = classUU.method11711();
      var14.method11718(var12);
      var1.method11728(var14);
      var14.method11729();
      var12.method11471();
      var13.method11471();
   }

   @ObfuscatedSignature(descriptor = "(IF)F")
   @ObfuscatedName("gq")
   public float method10582(int var1, float var2) {
      int var3 = this.field6000[var1 * 2];
      int var4 = this.field6000[var1 * 2 + 1];
      short var5 = this.field6003[var3];
      short var6 = this.field6003[var4 - 1];
      if (var2 < var5) {
         byte var40 = this.field5994[var1 * 2];
         return var40 == 0 ? this.field5997[var3 * 5 + 0] : this.method10579(var1, var2, true);
      } else if (var2 > var6) {
         byte var39 = this.field5994[var1 * 2 + 1];
         return var39 == 0 ? this.field5997[(var4 - 1) * 5 + 0] : this.method10579(var1, var2, false);
      } else {
         boolean var7 = this.method10590(var1, (int)var2, var3, var4);
         int var8 = this.field6002[var1];
         int var9 = this.field6001[var1];
         int var10 = var4 - var3;
         if (var10 <= 0) {
            return 0.0F;
         } else {
            float var11 = this.field5997[var8 * 5 + 3];
            float var12 = this.field5997[var8 * 5 + 4];
            boolean var13 = false;
            boolean var14 = false;
            if (0.0F == var11 && 0.0F == var12) {
               var13 = true;
            } else if (Float.MAX_VALUE == var11 && Float.MAX_VALUE == var12) {
               var14 = true;
            } else if (var8 + 1 < var4) {
               if (var7) {
                  float var16 = this.field6003[var8];
                  float var17 = this.field5997[var8 * 5 + 0];
                  float var18 = var16 + 0.33333334F * var11;
                  float var19 = var12 * 0.33333334F + var17;
                  float var20 = this.field6003[var8 + 1];
                  float var21 = this.field5997[(var8 + 1) * 5 + 0];
                  float var22 = var20 - 0.33333334F * this.field5997[var8 * 5 + 6];
                  float var23 = var21 - this.field5997[(var8 + 1) * 5 + 2] * 0.33333334F;
                  if (var9 != -1) {
                     float var24 = var20 - var16;
                     if (var24 != 0.0F) {
                        float var25 = var18 - var16;
                        float var26 = var22 - var16;
                        float var27 = var25 / var24;
                        float var28 = var26 / var24;
                        this.field5995[var9 + 5] = var27 == 0.33333334F && var28 == 0.6666667F ? 1.0F : 0.0F;
                        float var29 = var27;
                        float var30 = var28;
                        if (var27 < 0.0F) {
                           var27 = 0.0F;
                        }

                        if (var28 > 1.0F) {
                           var28 = 1.0F;
                        }

                        if (var27 > 1.0F || var28 < -1.0F) {
                           var28 = 1.0F - var28;
                           if (var28 < 0.0F) {
                              var28 = 0.0F;
                           }

                           if (var27 > 1.0F || var28 > 1.0F) {
                              float var31 = 1.0F + var27 * (var27 - 2.0F + var28) + (var28 - 2.0F) * var28;
                              if (var31 + field5987 > 0.0F) {
                                 if (field5987 + var27 < 1.3333334F) {
                                    float var32 = var27 - 2.0F;
                                    float var33 = var27 - 1.0F;
                                    float var34 = (float)Math.sqrt(var32 * var32 - var33 * 4.0F * var33);
                                    float var35 = (-var32 + var34) * 0.5F;
                                    if (var28 + field5987 > var35) {
                                       var28 = var35 - field5987;
                                    } else {
                                       var35 = 0.5F * (-var32 - var34);
                                       if (var28 < var35 + field5987) {
                                          var28 = var35 + field5987;
                                       }
                                    }
                                 } else {
                                    var27 = 1.3333334F - field5987;
                                    var28 = 0.33333334F - field5987;
                                 }
                              }
                           }

                           var28 = 1.0F - var28;
                        }

                        if (var27 != var29 && var29 != 0.0F) {
                           var19 = var17 + (var19 - var17) * var27 / var29;
                        }

                        if (var28 != var30 && var30 != 0.0F) {
                           var23 = var21 - (var21 - var23) * (1.0F - var28) / (1.0F - var30);
                        }

                        this.field5996[var1 * 5 + 4] = var16;
                        this.field5995[var9 + 4] = var20;
                        float var54 = var27 - 0.0F;
                        float var55 = var28 - var27;
                        float var56 = 1.0F - var28;
                        float var57 = var55 - var54;
                        this.field5996[var1 * 5 + 3] = var56 - var55 - var57;
                        this.field5996[var1 * 5 + 2] = var57 + var57 + var57;
                        this.field5996[var1 * 5 + 1] = var54 + var54 + var54;
                        this.field5996[var1 * 5 + 0] = 0.0F;
                        float var59 = var19 - var17;
                        float var36 = var23 - var19;
                        float var37 = var21 - var23;
                        float var38 = var36 - var59;
                        this.field5995[var9 + 3] = var37 - var36 - var38;
                        this.field5995[var9 + 2] = var38 + var38 + var38;
                        this.field5995[var9 + 1] = var59 + var59 + var59;
                        this.field5995[var9 + 0] = var17;
                     }
                  } else {
                     this.field5996[var1 * 5 + 4] = var16;
                     float var15 = var20 - var16;
                     float var45 = var21 - var17;
                     float var46 = var18 - var16;
                     float var48 = 0.0F;
                     float var49 = 0.0F;
                     if (var46 != 0.0F) {
                        var48 = (var19 - var17) / var46;
                     }

                     var46 = var20 - var22;
                     if (var46 != 0.0F) {
                        var49 = (var21 - var23) / var46;
                     }

                     float var51 = 1.0F / (var15 * var15);
                     float var52 = var15 * var48;
                     float var53 = var15 * var49;
                     this.field5996[var1 * 5 + 0] = var51 * (var52 + var53 - var45 - var45) / var15;
                     this.field5996[var1 * 5 + 1] = (var45 + var45 + var45 - var52 - var52 - var53) * var51;
                     this.field5996[var1 * 5 + 2] = var48;
                     this.field5996[var1 * 5 + 3] = var17;
                  }
               }
            } else {
               var13 = true;
            }

            if (var13) {
               return this.field5997[var8 * 5 + 0];
            } else if (!var14) {
               if (var9 != -1) {
                  float var43;
                  if (this.field5996[var1 * 5 + 4] == var2) {
                     var43 = 0.0F;
                  } else if (var2 == this.field5995[var9 + 4]) {
                     var43 = 1.0F;
                  } else {
                     var43 = (var2 - this.field5996[var1 * 5 + 4]) / (this.field5995[var9 + 4] - this.field5996[var1 * 5 + 4]);
                  }

                  float var42;
                  if (this.field5995[var9 + 5] != 0.0F) {
                     var42 = var43;
                  } else {
                     field5985[3] = this.field5996[var1 * 5 + 3];
                     field5985[2] = this.field5996[var1 * 5 + 2];
                     field5985[1] = this.field5996[var1 * 5 + 1];
                     field5985[0] = this.field5996[var1 * 5 + 0] - var43;
                     Arrays.fill(field5986, 0.0F);
                     int var44 = method10592(field5985, 3, 0.0F, true, 1.0F, true, field5986);
                     if (var44 == 1) {
                        var42 = field5986[0];
                     } else {
                        var42 = 0.0F;
                     }
                  }

                  return var42 * (this.field5995[var9 + 1] + (var42 * this.field5995[var9 + 3] + this.field5995[var9 + 2]) * var42) + this.field5995[var9 + 0];
               } else {
                  float var41 = var2 - this.field5996[var1 * 5 + 4];
                  return this.field5996[var1 * 5 + 3]
                     + var41 * (this.field5996[var1 * 5 + 2] + (this.field5996[var1 * 5 + 1] + var41 * this.field5996[var1 * 5 + 0]) * var41);
               }
            } else {
               return this.field6003[var8] != var2 && var8 + 1 < var4 ? this.field5997[(var8 + 1) * 5 + 0] : this.field5997[var8 * 5 + 0];
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method10561() {
      return this.field5993;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("dc")
   public void method10577(classXY var1) {
      try {
         int var2 = var1.method13312();
         int var3 = var1.method13312();
         this.field5989 = var1.method13322() * -514062117;
         this.field5999 = new int[this.field5981.field1786.method3889() * 9];
         this.field5998 = new int[this.field5981.field1784 * 1690200285 * 1];
         int var4 = var1.method13312();
         int[] var5 = new int[var4];
         this.field6000 = new int[var4 * 2];
         this.field6002 = new int[var4];
         Arrays.fill(this.field6002, -1);
         this.field6001 = new int[var4];
         this.field5996 = new float[var4 * 5];
         this.field5994 = new byte[var4 * 2];
         int var6 = 0;
         int var7 = 0;

         for (int var8 = 0; var8 < var4; var8++) {
            int var9 = var1.method13322();
            int var10 = var1.method13317();
            int var11 = var1.method13322();
            int var12 = var1.method13312();
            var1.method13322();
            this.field5994[var8 * 2] = var1.method13318();
            this.field5994[var8 * 2 + 1] = var1.method13318();
            byte var13 = var1.method13322() != 0;
            this.field6001[var8] = var13 ? 6 * var7++ : -1;
            var5[var8] = var1.method13314();
            this.field6000[var8 * 2] = var6;
            this.field6000[var8 * 2 + 1] = var6 + var12;
            var6 += var12;
            var1.method13319(var1.method13314() + 22 * var12);
            int[] var20;
            if (var9 == 1) {
               var20 = this.field5999;
               var13 = 9;
            } else {
               var20 = this.field5998;
               var13 = 1;
            }

            int var14 = method10584(var11);
            if (var14 < var13) {
               var20[var10 * var13 + var14] = var8 + 1;
            }

            if (var9 == 4) {
               this.field5993 = true;
            }
         }

         this.field5995 = new float[6 * var7];
         this.field6003 = new short[var6];
         this.field5997 = new float[var6 * 5];

         for (int var16 = 0; var16 < var4; var16++) {
            var1.method13319(var5[var16]);
            int var17 = this.field6000[var16 * 2];

            for (int var18 = this.field6000[var16 * 2 + 1]; var17 < var18; var17++) {
               this.field6003[var17] = (short)var1.method13327();

               for (int var19 = 0; var19 < 5; var19++) {
                  this.field5997[var17 * 5 + var19] = var1.method13326();
               }
            }
         }
      } catch (Exception var15) {
         client.field1028.error("AnimMaya{}", this.field5980 * 36494709, var15);
         throw var15;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   public boolean method10562(int var1) {
      try {
         return this.field5993;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sn.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IF)F")
   @ObfuscatedName("kf")
   public float method10585(int var1, float var2) {
      return this.method10582(var1 - 1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lsn;Luu;ILsg;IS)V")
   @ObfuscatedName("oo")
   public static void method10571(classSN var0, classUU var1, int var2, classSG var3, int var4, short var5) {
      if (var0 == null) {
         var0.method10574(var1, var2, var3, var2, var5);
      } else {
         try {
            float[] var6 = var3.method10462(var0.field5989 * -1268206765, -16766288);
            float var7 = var6[0];
            float var8 = var6[1];
            float var9 = var6[2];
            if (var0.field6005[var2] != null) {
               if (var5 != 256) {
                  throw new IllegalStateException();
               }

               classSU var10 = var0.field6005[var2][6];
               classSU var11 = var0.field6005[var2][7];
               classSU var12 = var0.field6005[var2][8];
               if (null != var10) {
                  if (var5 != 256) {
                     return;
                  }

                  var7 = var10.method10709(var4, -1878576001);
               }

               if (null != var11) {
                  if (var5 != 256) {
                     throw new IllegalStateException();
                  }

                  var8 = var11.method10709(var4, -509834605);
               }

               if (null != var12) {
                  if (var5 != 256) {
                     throw new IllegalStateException();
                  }

                  var9 = var12.method10709(var4, -1625958300);
               }
            }

            classUU var14 = classRM.method10172(1778071523);
            var14.method11651(var7, var8, var9, -2144009080);
            var1.method11656(var14, 791745756);
            var14.method11615(1651346424);
         } catch (RuntimeException var13) {
            throw classEG.method3899(var13, "sn.aa(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;F)V")
   @ObfuscatedName("at")
   public void method10587(classUU var1, int var2, classSG var3, float var4) {
      float[] var5 = var3.method10483(this.field5989 * -1268206765);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      int var9 = this.field5999[var2 * 9 + 3];
      int var10 = this.field5999[var2 * 9 + 4];
      int var11 = this.field5999[var2 * 9 + 5];
      if (var9 != 0) {
         var6 = this.method10585(var9, var4);
      }

      if (var10 != 0) {
         var7 = this.method10585(var10, var4);
      }

      if (var11 != 0) {
         var8 = this.method10585(var11, var4);
      }

      var1.field6391 = var6;
      var1.field6399 = var7;
      var1.field6395 = var8;
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;I)V")
   @ObfuscatedName("ak")
   void method10572(classUU var1, int var2, classSG var3, int var4) {
      float[] var5 = var3.method10462(this.field5989 * -85587021, -1992505766);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      if (this.field6005[var2] != null) {
         classSU var9 = this.field6005[var2][6];
         classSU var10 = this.field6005[var2][7];
         classSU var11 = this.field6005[var2][8];
         if (null != var9) {
            var6 = var9.method10709(var4, -1472378621);
         }

         if (null != var10) {
            var7 = var10.method10709(var4, -1628440073);
         }

         if (null != var11) {
            var8 = var11.method10709(var4, -870875110);
         }
      }

      classUU var12 = classRM.method10172(1778071523);
      var12.method11651(var6, var7, var8, -2142493065);
      var1.method11656(var12, 161997312);
      var12.method11615(1686622920);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;I)Lsn;")
   @ObfuscatedName("aj")
   public static classSN method10554(IndexDataBase var0, IndexDataBase var1, int var2) {
      byte[] var4 = var0.method11937(var2 >> 16 & 65535, var2 & 1938265762, -179901292);
      boolean var3;
      if (null == var4) {
         var3 = false;
      } else {
         int var5 = (var4[1] & 657266300) << 8 | var4[2] & 255;
         byte[] var6 = var1.method11937(var5, 0, -831728089);
         var3 = null != var6;
      }

      if (!var3) {
         return null;
      } else {
         if (classXN.field6889 == null) {
            field5991 = Runtime.getRuntime().availableProcessors() * -1069883518;
            classXN.field6889 = new ThreadPoolExecutor(
               0, -1442751278 * field5991, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(100 + -204079948 * field5991), new classSF()
            );
         }

         try {
            return new classSN(var0, var1, var2);
         } catch (Exception var7) {
            return null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;I)V")
   @ObfuscatedName("av")
   void method10570(classUU var1, int var2, classSG var3, int var4) {
      float[] var5 = var3.method10460(this.field5989 * -1268206765, -316051969);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      if (this.field6005[var2] != null) {
         classSU var9 = this.field6005[var2][3];
         classSU var10 = this.field6005[var2][4];
         classSU var11 = this.field6005[var2][5];
         if (var9 != null) {
            var6 = var9.method10709(var4, -1202514749);
         }

         if (null != var10) {
            var7 = var10.method10709(var4, -658281798);
         }

         if (null != var11) {
            var8 = var11.method10709(var4, -882306359);
         }
      }

      var1.field6391 = var6;
      var1.field6399 = var7;
      var1.field6395 = var8;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method10563() {
      return this.field5993;
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;I)V")
   @ObfuscatedName("aw")
   void method10573(classUU var1, int var2, classSG var3, int var4) {
      float[] var5 = var3.method10462(this.field5989 * -1268206765, -1136523565);
      float var6 = var5[0];
      float var7 = var5[1];
      float var8 = var5[2];
      if (this.field6005[var2] != null) {
         classSU var9 = this.field6005[var2][6];
         classSU var10 = this.field6005[var2][7];
         classSU var11 = this.field6005[var2][8];
         if (null != var9) {
            var6 = var9.method10709(var4, -1746856060);
         }

         if (null != var10) {
            var7 = var10.method10709(var4, -1449765769);
         }

         if (null != var11) {
            var8 = var11.method10709(var4, -1669248191);
         }
      }

      classUU var12 = classRM.method10172(1778071523);
      var12.method11651(var6, var7, var8, -2146230438);
      var1.method11656(var12, 1267259805);
      var12.method11615(2118943037);
   }

   @ObfuscatedSignature(descriptor = "(I)Lsn;")
   @ObfuscatedName("wc")
   public static synchronized classSN method10580(int var0) {
      classSN var1 = (classSN)field5988.method6479(var0);
      if (var1 != null) {
         return var1;
      } else {
         classVP var3 = client.field1030[22];
         classVP var4 = client.field1030[1];
         byte[] var5 = var3.method11949(var0 >> 16 & 65535, var0 & 65535);
         boolean var2;
         if (var5 == null) {
            var2 = false;
         } else {
            int var6 = (var5[1] & 255) << 8 | var5[2] & 255;
            byte[] var7 = var4.method11949(var6, 0);
            var2 = var7 != null;
         }

         if (!var2) {
            return null;
         } else {
            if (classXN.field6889 == null) {
               field5991 = Runtime.getRuntime().availableProcessors() * 1058142237;
               classXN.field6889 = new ThreadPoolExecutor(
                  0, field5991 * -517436875, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(field5991 * -517436875 * 100 + 100), new classSF()
               );
            }

            var1 = new classSN(client.field1030[22], client.field1030[1], var0);
            classZP.method14133(field5988, var1, var0);
            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IFZ)F")
   @ObfuscatedName("qu")
   public float method10579(int var1, float var2, boolean var3) {
      byte var4 = this.field5994[var1 * 2];
      byte var5 = this.field5994[var1 * 2 + 1];
      int var6 = this.field6000[var1 * 2];
      int var7 = this.field6000[var1 * 2 + 1];
      float var8 = 0.0F;
      int var9 = var7 - var6;
      if (var9 == 0) {
         return var8;
      } else {
         float var10 = this.field6003[var6];
         float var11 = this.field6003[var7 - 1];
         float var12 = var11 - var10;
         if (0.0F == var12) {
            return this.field5997[var6 * 5 + 0];
         } else {
            float var13;
            if (var2 > var11) {
               var13 = (var2 - var11) / var12;
            } else {
               var13 = (var2 - var10) / var12;
            }

            float var14 = (int)var13;
            float var15 = Math.abs(var13 - var14);
            float var16 = var15 * var12;
            var14 = Math.abs(var14 + 1.0F);
            float var17 = var14 / 2.0F;
            float var18 = (int)var17;
            var15 = var17 - var18;
            if (var3) {
               if (var4 == 4) {
                  if (0.0F != var15) {
                     var16 += var10;
                  } else {
                     var16 = var11 - var16;
                  }
               } else if (var4 != 2 && var4 != 3) {
                  if (var4 == 1) {
                     var16 = var10 - var2;
                     float var19 = this.field5997[var6 * 5 + 1];
                     float var20 = this.field5997[var6 * 5 + 2];
                     var8 = this.field5997[var6 * 5 + 0];
                     if (0.0F != var19) {
                        var8 -= var20 * var16 / var19;
                     }

                     return var8;
                  }
               } else {
                  var16 = var11 - var16;
               }
            } else if (var5 == 4) {
               if (var15 != 0.0F) {
                  var16 = var11 - var16;
               } else {
                  var16 += var10;
               }
            } else if (var5 != 2 && var5 != 3) {
               if (var5 == 1) {
                  var16 = var2 - var11;
                  float var29 = this.field5997[(var7 - 1) * 5 + 3];
                  float var30 = this.field5997[(var7 - 1) * 5 + 4];
                  var8 = this.field5997[(var7 - 1) * 5 + 0];
                  if (0.0F != var29) {
                     var8 += var30 * var16 / var29;
                  }

                  return var8;
               }
            } else {
               var16 += var10;
            }

            var8 = this.method10582(var1, var16);
            if (var3 && var4 == 3) {
               float var31 = this.field5997[(var7 - 1) * 5 + 0] - this.field5997[var6 * 5 + 0];
               var8 -= var14 * var31;
            } else if (!var3 && var5 == 3) {
               float var21 = this.field5997[(var7 - 1) * 5 + 0] - this.field5997[var6 * 5 + 0];
               var8 += var21 * var14;
            }

            return var8;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "([FIFZFZ[F)I")
   @ObfuscatedName("ma")
   public static int method10592(float[] var0, int var1, float var2, boolean var3, float var4, boolean var5, float[] var6) {
      return classEL.method3935(var0, var1, var2, var3, var4, var5, var6, -401031406);
   }

   @ObfuscatedSignature(descriptor = "(CI)Z")
   @ObfuscatedName("aq")
   public static boolean method10575(char var0, int var1) {
      try {
         if (var0 >= '0') {
            if (var1 == 1296729483) {
               throw new IllegalStateException();
            }

            if (var0 <= '9') {
               return true;
            }

            if (var1 == 1296729483) {
               throw new IllegalStateException();
            }
         }

         if (var0 >= 'A') {
            if (var1 == 1296729483) {
               throw new IllegalStateException();
            }

            if (var0 <= 'Z') {
               return true;
            }

            if (var1 == 1296729483) {
               throw new IllegalStateException();
            }
         }

         if (var0 >= 'a') {
            if (var1 == 1296729483) {
               throw new IllegalStateException();
            }

            if (var0 <= 'z') {
               if (var1 == 1296729483) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sn.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ILsg;III)V")
   @ObfuscatedName("as")
   public void method10583(int var1, classSG var2, int var3, int var4, int var5) {
      this.method10589(rl21.field5726, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;II)V")
   @ObfuscatedName("ax")
   void method10568(classUU var1, int var2, classSG var3, int var4, int var5) {
      try {
         float[] var6 = var3.method10460(-1268206765 * this.field5980, -2034173247);
         float var7 = var6[0];
         float var8 = var6[1];
         float var9 = var6[2];
         if (null != this.field6005[var2]) {
            if (var5 == -2059694667) {
               return;
            }

            classSU var10 = this.field6005[var2][0];
            classSU var11 = this.field6005[var2][1];
            classSU var12 = this.field6005[var2][2];
            if (null != var10) {
               if (var5 == -2059694667) {
                  throw new IllegalStateException();
               }

               var7 = var10.method10709(var4, -805817691);
            }

            if (null != var11) {
               var8 = var11.method10709(var4, -1732400191);
            }

            if (var12 != null) {
               if (var5 == -2059694667) {
                  return;
               }

               var9 = var12.method10709(var4, -544400739);
            }
         }

         classUL var16 = classAV.method715((byte)0);
         var16.method11435(1.0F, 0.0F, 0.0F, var7, (short)-24168);
         classUL var17 = classAV.method715((byte)0);
         var17.method11435(0.0F, 1.0F, 0.0F, var8, (short)-26915);
         classUL var18 = classAV.method715((byte)0);
         var18.method11435(0.0F, 0.0F, 1.0F, var9, (short)-30774);
         classUL var13 = classAV.method715((byte)0);
         var13.method11454(var18, 1591236864);
         var13.method11454(var16, 1591236864);
         var13.method11454(var17, 1591236864);
         classUU var14 = classRM.method10172(1778071523);
         var14.method11661(var13, 777754821);
         var1.method11656(var14, -382262825);
         classUL.method11416(var16, -1304519495);
         classUL.method11416(var17, -1193863561);
         classUL.method11416(var18, -701725761);
         classUL.method11416(var13, -207024086);
         var14.method11640(1805636757);
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "sn.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luu;ILsg;IS)V")
   @ObfuscatedName("aa")
   void method10574(classUU var1, int var2, classSG var3, int var4, short var5) {
      try {
         float[] var6 = var3.method10460(this.field5989 * -1268206765, -16766288);
         float var7 = var6[0];
         float var8 = var6[1];
         float var9 = var6[2];
         if (this.field6005[var2] != null) {
            if (var5 != 256) {
               throw new IllegalStateException();
            }

            classSU var10 = this.field6005[var2][6];
            classSU var11 = this.field6005[var2][7];
            classSU var12 = this.field6005[var2][8];
            if (null != var10) {
               if (var5 != 256) {
                  return;
               }

               var7 = var10.method10709(var4, -1878576001);
            }

            if (null != var11) {
               if (var5 != 256) {
                  throw new IllegalStateException();
               }

               var8 = var11.method10709(var4, -509834605);
            }

            if (null != var12) {
               if (var5 != 256) {
                  throw new IllegalStateException();
               }

               var9 = var12.method10709(var4, -1625958300);
            }
         }

         classUU var14 = classRM.method10172(1778071523);
         var14.method11651(var7, var8, var9, -2144009080);
         var1.method11656(var14, 791745756);
         var14.method11615(1651346424);
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "sn.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ws")
   public static int method10584(int var0) {
      if (var0 >= 0 && var0 <= 9) {
         return var0 - 1;
      } else if (var0 >= 10 && var0 <= 15) {
         return var0 - 10;
      } else {
         return var0 == 16 ? 0 : -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("af")
   void method10557(classXY var1, int var2, int var3) {
      try {
         var1.method13107(-1008441641);
         var1.method13201(1360168439);
         this.field5980 = var1.method13266(-346779531) * -514062117;
         int var4 = var1.method13099(-1112808975);
         this.field6005 = new classSU[this.field5981.method3948((byte)12).method3864(1786141898)][];
         this.field6004 = new classSU[this.field5981.method3947(162047720)][];
         classSV[] var5 = new classSV[var4];

         for (int var6 = 0; var6 < var4; var6++) {
            if (var3 == -435781219) {
               throw new IllegalStateException();
            }

            int var8 = var1.method13152(-346779531);
            classSK var9 = (classSK)classOF.method8436(classRA.method9974(-819351264), var8, 714166249);
            if (null == var9) {
               if (var3 == -435781219) {
                  return;
               }

               var9 = classSK.field5970;
            }

            int var10 = var1.method13152(-1383485269);
            classSH var11 = IntProjection.method4109(var1.method13107(-346779531), -2064244240);
            classSU var12 = new classSU();
            var12.method10702(var1, var2, -1970030991);
            var5[var6] = new classSV(this, var12, var9, var11, var10);
            int var13 = var9.method10507(1572322037);
            classSU[][] var14;
            if (var9 == classSK.field5967) {
               if (var3 == -435781219) {
                  return;
               }

               var14 = this.field6005;
            } else {
               var14 = this.field6004;
            }

            if (null == var14[var10]) {
               if (var3 == -435781219) {
                  return;
               }

               var14[var10] = new classSU[var13];
            }

            if (classSK.field5967 == var9) {
               this.field5993 = true;
            }
         }

         int var16 = var4 / (-517436875 * field5991);
         int var7 = var4 % (-517436875 * field5991);
         int var18 = 0;

         for (int var19 = 0; var19 < field5991 * -517436875; var19++) {
            if (var3 == -435781219) {
               throw new IllegalStateException();
            }

            int var17 = var18;
            var18 += var16;
            if (var7 > 0) {
               if (var3 == -435781219) {
                  throw new IllegalStateException();
               }

               var18++;
               var7--;
            }

            if (var17 == var18) {
               if (var3 == -435781219) {
                  return;
               }
               break;
            }

            this.field5983.add(classXN.field6889.submit(new classTB(this, var17, var18, var5)));
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "sn.af(" + ')');
      }
   }
}
