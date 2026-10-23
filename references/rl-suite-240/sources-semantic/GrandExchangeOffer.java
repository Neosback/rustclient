import net.runelite.api.GrandExchangeOfferState;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mm")
public class GrandExchangeOffer implements net.runelite.api.GrandExchangeOffer {
   @ObfuscatedName("as")
   public int field4535;
   @ObfuscatedName("ab")
   public int field4533;
   @ObfuscatedName("ag")
   public int field4532;
   @ObfuscatedName("ae")
   public int field4534;
   @ObfuscatedName("az")
   byte field4537;
   @ObfuscatedName("af")
   public int field4536;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field4538 = 14;

   public int getSpent() {
      return this.field4536 * 464461553;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   public int method7647() {
      return this.field4537 & 7;
   }

   public GrandExchangeOffer(classXY var1, boolean var2) {
      this.field4537 = var1.method13097((byte)17);
      this.field4533 = var1.method13099(1747243124) * 443314353;
      this.field4532 = classXY.method13113(var1, (byte)1) * 961265821;
      this.field4534 = classXY.method13113(var1, (byte)1) * 373867205;
      this.field4535 = classXY.method13113(var1, (byte)1) * -424806523;
      this.field4536 = classXY.method13113(var1, (byte)1) * -263056879;
      if (var2) {
         this.method7645(classUC.method11322(var1, -1964428479), (byte)-1);
      }
   }

   public int getItemId() {
      return this.field4533 * 1561057361;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Integer;B)V")
   @ObfuscatedName("af")
   void method7645(Integer var1, byte var2) {
      try {
         ;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mm.af(" + ')');
      }
   }

   public int getPrice() {
      return this.field4532 * -1718041163;
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ae")
   public int method7648(short var1) {
      try {
         return this.field4537 & 7;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mm.ae(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Integer;")
   @ObfuscatedName("ac")
   static Integer method7641(classXY var0) {
      int var1 = 0;
      boolean var2 = false;

      while (true) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 1214633080) {
            return var2 ? var1 : null;
         }

         if (var3 != 0) {
            throw new IllegalStateException("");
         }

         while (true) {
            int var4 = var0.method13095(-346779531);
            if (255 == var4) {
               break;
            }

            var0.field6953 -= -507022095;
            if (var0.method13099(1291323804) != 0) {
               throw new IllegalStateException("");
            }

            if (var2) {
               throw new IllegalStateException("");
            }

            var1 = classXY.method13113(var0, (byte)1);
            var2 = true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   public int method7652() {
      return 8 == (this.field4537 & 8) ? 1 : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Integer;")
   @ObfuscatedName("ax")
   static Integer method7642(classXY var0) {
      int var1 = 0;
      boolean var2 = false;

      while (true) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 255) {
            return var2 ? var1 : null;
         }

         if (var3 != 0) {
            throw new IllegalStateException("");
         }

         while (true) {
            int var4 = var0.method13095(-346779531);
            if (255 == var4) {
               break;
            }

            var0.field6953 -= -1095856699;
            if (var0.method13099(1780122919) != 0) {
               throw new IllegalStateException("");
            }

            if (var2) {
               throw new IllegalStateException("");
            }

            var1 = classXY.method13113(var0, (byte)1);
            var2 = true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ax")
   static void method7662(int var0) {
      try {
         classGG.field2575 = null;
         classMS.field4577 = null;
         AABB.field2371 = null;
         classDT.field1628 = null;
         classRO.method10195(-2044472822);
         classCE.field714 = null;
         classCE.field713 = null;
         classUO.field6359 = null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "mm.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lmm;II)V")
   @ObfuscatedName("ad")
   public static void method7656(GrandExchangeOffer var0, int var1, int var2) {
      if (var0 == null) {
         var0.method7659(var1, var1);
      } else {
         try {
            var0.field4537 &= -9;
            if (1 == var1) {
               if (var2 <= 485290041) {
                  throw new IllegalStateException();
               }

               var0.field4537 = (byte)(var0.field4537 | 8);
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "mm.as(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lmm;I)I")
   @ObfuscatedName("pz")
   public static int method7653(GrandExchangeOffer var0, int var1) {
      if (var0 == null) {
         var0.method7655(var1);
      }

      try {
         byte var10000;
         if (8 == (var0.field4537 & 8)) {
            if (var1 >= 1911999792) {
               throw new IllegalStateException();
            }

            var10000 = 1;
         } else {
            var10000 = 0;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mm.ag(" + 41);
      }
   }

   public GrandExchangeOfferState getState() {
      byte var1 = this.method7663();
      boolean var2 = (var1 & 8) == 8;
      boolean var3 = (var1 & 4) == 4;
      if (var1 == 0) {
         return GrandExchangeOfferState.EMPTY;
      } else if (var3 && this.getQuantitySold() < this.getTotalQuantity()) {
         return var2 ? GrandExchangeOfferState.CANCELLED_SELL : GrandExchangeOfferState.CANCELLED_BUY;
      } else if (var2) {
         return var3 ? GrandExchangeOfferState.SOLD : GrandExchangeOfferState.SELLING;
      } else {
         return var3 ? GrandExchangeOfferState.BOUGHT : GrandExchangeOfferState.BUYING;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Integer;")
   @ObfuscatedName("aa")
   static Integer method7643(classXY var0) {
      int var1 = 0;
      boolean var2 = false;

      while (true) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 255) {
            return var2 ? var1 : null;
         }

         if (var3 != 0) {
            throw new IllegalStateException("");
         }

         while (true) {
            int var4 = var0.method13095(-346779531);
            if (255 == var4) {
               break;
            }

            var0.field6953 -= -1095856699;
            if (var0.method13099(-1852010553) != 0) {
               throw new IllegalStateException("");
            }

            if (var2) {
               throw new IllegalStateException("");
            }

            var1 = classXY.method13113(var0, (byte)1);
            var2 = true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()B")
   @ObfuscatedName("bt")
   public byte method7663() {
      return this.field4537;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Integer;")
   @ObfuscatedName("ao")
   static Integer method7644(classXY var0) {
      int var1 = 0;
      boolean var2 = false;

      while (true) {
         int var3 = var0.method13095(-346779531);
         if (var3 == 255) {
            return var2 ? var1 : null;
         }

         if (var3 != 0) {
            throw new IllegalStateException("");
         }

         while (true) {
            int var4 = var0.method13095(-346779531);
            if (255 == var4) {
               break;
            }

            var0.field6953 -= -1095856699;
            if (var0.method13099(-1630959316) != 0) {
               throw new IllegalStateException("");
            }

            if (var2) {
               throw new IllegalStateException("");
            }

            var1 = classXY.method13113(var0, (byte)1);
            var2 = true;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ab")
   void method7651(int var1, byte var2) {
      try {
         this.field4537 &= -8;
         this.field4537 = (byte)(this.field4537 | var1 & 7);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mm.ab(" + ')');
      }
   }

   public GrandExchangeOffer() {
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   public int method7649() {
      return this.field4537 & 7;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   public int method7650() {
      return this.field4537 & 7;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Integer;)V")
   @ObfuscatedName("al")
   void method7646(Integer var1) {
   }

   public int getTotalQuantity() {
      return this.field4534 * 29846541;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   public int method7654() {
      return 8 == (this.field4537 & 8) ? 1 : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ar")
   void method7657(int var1) {
      this.field4537 = (byte)(this.field4537 & 1485640661);
      if (1 == var1) {
         this.field4537 = (byte)(this.field4537 | 8);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ai")
   void method7658(int var1) {
      this.field4537 &= -9;
      if (1 == var1) {
         this.field4537 = (byte)(this.field4537 | 8);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;Ldh;II)V")
   @ObfuscatedName("ax")
   static void method7661(WorldView var0, Actor var1, int var2, int var3) {
      try {
         classLL.method7164(var0, var1.field1484 * -1547553299, var1.field1486 * -1272026483, var2, var1.vmethod267(-1423776655), 1489480712);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mm.ax(" + ')');
      }
   }

   public int getQuantitySold() {
      return this.field4535 * 834365773;
   }

   @ObfuscatedSignature(descriptor = "(Lfx;IIIIB)Z")
   @ObfuscatedName("ar")
   static boolean method7660(Model var0, int var1, int var2, int var3, int var4, byte var5) {
      try {
         AABB var6 = var0.method5118(var1);
         int var7 = -915116879 * var6.field2365 + var2;
         int var8 = var3 + var6.field2364 * -1027280203;
         int var9 = var6.field2367 * -81102275 + var4;
         int var10 = var6.field2366 * 1301855055;
         int var11 = var6.field2363 * -623192411;
         int var12 = var6.field2368 * 2105673973;
         int var13 = -1229211407 * classLS.field4217 - var7;
         int var14 = 1234160379 * classKT.field4020 - var8;
         int var15 = 1574685861 * classHQ.field2866 - var9;
         if (Math.abs(var13) > classVI.field6511 * 756191369 + var10) {
            return false;
         } else if (Math.abs(var14) > 1266420275 * classJI.field3162 + var11) {
            if (var5 == -1) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (Math.abs(var15) > 1304241803 * classEV.field1941 + var12) {
            if (var5 == -1) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (Math.abs(var15 * 348139717 * classDK.field1540 - GroundObject.field1809 * -1947307103 * var14)
            > classEV.field1941 * 1304241803 * var11 + classJI.field3162 * 1266420275 * var12) {
            return false;
         } else if (Math.abs(-1947307103 * GroundObject.field1809 * var13 - -331219525 * classBY.field614 * var15)
            > var10 * classEV.field1941 * 1304241803 + classVI.field6511 * 756191369 * var12) {
            if (var5 == -1) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else if (Math.abs(-331219525 * classBY.field614 * var14 - classDK.field1540 * 348139717 * var13)
            > var11 * 756191369 * classVI.field6511 + classJI.field3162 * 1266420275 * var10) {
            if (var5 == -1) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            return true;
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "mm.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("as")
   void method7659(int var1, int var2) {
      try {
         this.field4537 &= -9;
         if (1 == var1) {
            if (var2 <= 485290041) {
               throw new IllegalStateException();
            }

            this.field4537 = (byte)(this.field4537 | 8);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mm.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public int method7655(int var1) {
      try {
         byte var10000;
         if (8 == (this.field4537 & 8)) {
            if (var1 >= 1911999792) {
               throw new IllegalStateException();
            }

            var10000 = 1;
         } else {
            var10000 = 0;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mm.ag(" + 41);
      }
   }
}
