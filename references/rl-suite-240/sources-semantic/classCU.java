import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cu")
public class classCU implements classXZ {
   @ObfuscatedName("as")
   final int field1290;
   @ObfuscatedSignature(descriptor = "Lcu;")
   @ObfuscatedName("af")
   static final classCU field1286 = new classCU(1, 1);
   @ObfuscatedSignature(descriptor = "Lcu;")
   @ObfuscatedName("ae")
   static final classCU field1288 = new classCU(2, 7);
   @ObfuscatedSignature(descriptor = "Lcu;")
   @ObfuscatedName("ab")
   static final classCU field1285 = new classCU(3, 8);
   @ObfuscatedSignature(descriptor = "Lcu;")
   @ObfuscatedName("ag")
   static final classCU field1287 = new classCU(4, 9);
   @ObfuscatedSignature(descriptor = "Lcu;")
   @ObfuscatedName("az")
   static final classCU field1284 = new classCU(0, -1);
   @ObfuscatedName("ax")
   final int field1289;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field1291 = 11;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   public static final int field1292 = 1536;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   public static final int field1293 = 1048576;
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field1294 = 1076101432;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field1289 * -1686243597;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcu;")
   @ObfuscatedName("ac")
   static classCU[] method2667() {
      return new classCU[]{field1284, field1287, field1288, field1285, field1286};
   }

   @ObfuscatedSignature(descriptor = "(Lct;III)V")
   @ObfuscatedName("bl")
   static void method2672(Player var0, int var1, int var2, int var3) {
      try {
         if (var0 == null) {
            if (var3 == 1659085546) {
               throw new IllegalStateException();
            }
         } else {
            Animation var4 = var0.field1463.method9775((byte)44);
            if (var1 == var0.field1463.method9776(500216846)) {
               if (var3 == 1659085546) {
                  throw new IllegalStateException();
               }

               if (-1 != var1) {
                  if (var3 == 1659085546) {
                     throw new IllegalStateException();
                  }

                  int var5 = 1697446663 * var4.field5120;
                  if (1 == var5) {
                     if (var3 == 1659085546) {
                        throw new IllegalStateException();
                     }

                     classQR.method9791(var0.field1463, -15344493);
                     var0.field1465 = var2 * -285300085;
                  }

                  if (2 == var5) {
                     if (var3 == 1659085546) {
                        throw new IllegalStateException();
                     }

                     var0.field1463.method9793(-216097173);
                  }

                  return;
               }
            }

            if (var1 != -1 && var0.field1463.method9772(374233424)) {
               if (var3 == 1659085546) {
                  throw new IllegalStateException();
               }

               if (classQA.method9477(var1, -1755248303).field5119 * -1968168817 < var4.field5119 * -1968168817) {
                  return;
               }

               if (var3 == 1659085546) {
                  throw new IllegalStateException();
               }
            }

            classQR.method9770(var0.field1463, var1, -2141639215);
            classQR.method9791(var0.field1463, 650004181);
            var0.field1465 = -285300085 * var2;
            var0.field1476 = -574940415 * var0.field1471;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cu.bl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field1289 * -1686243597;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field1289 * -1686243597;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field1289 * -1686243597;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cu.az(" + 41);
      }
   }

   classCU(int var1, int var2) {
      this.field1290 = var1 * 929275599;
      this.field1289 = var2 * 62460475;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcu;")
   @ObfuscatedName("ax")
   static classCU[] method2668() {
      return new classCU[]{field1284, field1287, field1288, field1285, field1286};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcu;")
   @ObfuscatedName("aa")
   static classCU[] method2669() {
      return new classCU[]{field1284, field1287, field1288, field1285, field1286};
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ej")
   static void method2674(int var0, int var1) {
      try {
         WorldView var2 = client.field815.method1584(var0, (short)-6788);
         if (var2 == null) {
            if (var1 == -1615596217) {
               throw new IllegalStateException();
            }
         } else {
            for (int var3 = 0; var3 < -2087374717 * client.field838.field1417; var3++) {
               int var4 = client.field838.field1421[var3];
               client.field838.method3054((Player)IndexedObjectSet.method13449(var2.field1692, var4), -406854399);
            }

            classCL.method1577(client.field815, var0, (byte)-1);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "cu.ej(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Lcd;I)V")
   @ObfuscatedName("da")
   static final void method2673(WorldView var0, classCD var1, int var2) {
      try {
         Scene var3 = var0.field1687;
         long var4 = 0L;
         int var6 = -1;
         int var7 = 0;
         int var8 = 0;
         if (0 == var1.field678 * 2125153617) {
            if (var2 != -324749371) {
               throw new IllegalStateException();
            }

            var4 = var3.method4461(-1436432311 * var1.field679, var1.field676 * -1779660971, -522576623 * var1.field680);
         }

         if (var1.field678 * 2125153617 == 1) {
            if (var2 != -324749371) {
               return;
            }

            var4 = var3.method4450(-1436432311 * var1.field679, -1779660971 * var1.field676, var1.field680 * -522576623);
         }

         if (2125153617 * var1.field678 == 2) {
            if (var2 != -324749371) {
               return;
            }

            var4 = rl0.method10065(var3, var1.field679 * -1436432311, -1779660971 * var1.field676, -522576623 * var1.field680);
         }

         if (3 == 2125153617 * var1.field678) {
            if (var2 != -324749371) {
               throw new IllegalStateException();
            }

            var4 = var3.method4413(var1.field679 * -1436432311, -1779660971 * var1.field676, var1.field680 * -522576623);
         }

         if (var4 != 0L) {
            if (var2 != -324749371) {
               throw new IllegalStateException();
            }

            int var9 = classKK.method6532(var3, -1436432311 * var1.field679, -1779660971 * var1.field676, -522576623 * var1.field680, var4);
            var6 = classCN.method2542(var4);
            var7 = var9 & 31;
            var8 = var9 >> 6 & 3;
         }

         var1.field685 = var6 * 1523159321;
         var1.field677 = -1022331799 * var7;
         var1.field686 = -519869943 * var8;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "cu.da(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lcu;")
   @ObfuscatedName("as")
   static classCU[] method2670() {
      return new classCU[]{field1284, field1287, field1288, field1285, field1286};
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lva;Ljava/lang/String;Ljava/lang/String;I)Lzv;")
   @ObfuscatedName("as")
   public static classZV method2671(IndexDataBase var0, IndexDataBase var1, String var2, String var3, int var4) {
      try {
         if (!IndexDataBase.method11923(var0, var2, var3, 2038265183)) {
            if (var4 == 896511757) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            int var5 = IndexDataBase.method11918(var0, var2, 31481744);
            int var6 = var0.method11922(var5, var3, (byte)-5);
            classZV var7;
            if (!classHK.method6100(var0, var5, var6, (byte)-127)) {
               if (var4 == 896511757) {
                  throw new IllegalStateException();
               }

               var7 = null;
            } else {
               byte[] var9 = var1.method11942(var5, var6, -1447675577);
               classZV var8;
               if (var9 == null) {
                  if (var4 == 896511757) {
                     throw new IllegalStateException();
                  }

                  var8 = null;
               } else {
                  classZV var10 = new classZV(
                     var9, classYL.field7000, classYL.field7001, EntityOps.field5369, classGG.field2576, classRB.field5637, classYL.field7002
                  );
                  classVZ.method12347((byte)-70);
                  var8 = var10;
               }

               var7 = var8;
            }

            return var7;
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "cu.as(" + ')');
      }
   }
}
