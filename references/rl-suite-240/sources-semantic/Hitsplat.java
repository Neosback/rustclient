import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dv")
public class Hitsplat implements net.runelite.api.Hitsplat {
   @ObfuscatedSignature(descriptor = "Ldv;")
   @ObfuscatedName("un")
   public static Hitsplat field1638;
   @ObfuscatedName("af")
   int field1634;
   @ObfuscatedName("az")
   int field1636;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   public static final int field1637 = 29;
   @ObfuscatedName("ae")
   int field1633 = -714249401;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field1635 = 2;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("as")
   int method3627() {
      return this.field1633 * 1682911625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int method3628() {
      return this.field1633 * 1682911625;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ia")
   public void method3643(int var1, int var2, int var3) {
      field1638 = this;
   }

   public int getHitsplatType() {
      return this.field1633 * 1682911625;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   int method3633(int var1) {
      try {
         return -660838951 * this.field1634;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dv.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)V")
   @ObfuscatedName("az")
   void method3625(int var1, int var2, int var3, byte var4) {
      this.method3643(var1, var2, var3);

      try {
         this.field1633 = var1 * 714249401;
         this.field1636 = var2 * -1749090343;
         this.field1634 = 742771305 * var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dv.az(" + ')');
      }
   }

   Hitsplat() {
   }

   public int getAmount() {
      return this.field1636 * 1616351337;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ax")
   int method3629() {
      return this.field1633 * 1682911625;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IZI)V")
   @ObfuscatedName("cd")
   static void method3642(WorldView var0, int var1, boolean var2, int var3) {
      try {
         Player var4 = (Player)IndexedObjectSet.method13449(var0.field1692, var1);
         if (null != var4) {
            if (var3 <= 1222866659) {
               throw new IllegalStateException();
            }

            if (var4.vmethod262((byte)76) && !var4.field1257) {
               if (var3 <= 1222866659) {
                  throw new IllegalStateException();
               }

               int var5;
               label118: {
                  label128: {
                     var5 = -426380455 * var4.field1273;
                     var4.field1262 = false;
                     if (client.field886) {
                        if (var3 <= 1222866659) {
                           throw new IllegalStateException();
                        }

                        if (client.field838.field1417 * -2087374717 > 50) {
                           break label128;
                        }

                        if (var3 <= 1222866659) {
                           throw new IllegalStateException();
                        }
                     }

                     if (-2087374717 * client.field838.field1417 <= 200) {
                        break label118;
                     }

                     if (var3 <= 1222866659) {
                        throw new IllegalStateException();
                     }
                  }

                  if (var2) {
                     if (var3 <= 1222866659) {
                        throw new IllegalStateException();
                     }

                     if (var4.method3091(-872807456) == var4.field1434 * 1457308725) {
                        if (var3 <= 1222866659) {
                           return;
                        }

                        var4.field1262 = true;
                     }
                  }
               }

               int var6 = var4.field1484 * -1547553299 >> 7;
               int var7 = var4.field1486 * -1272026483 >> 7;
               if (WorldView.method3745(var0, var6, var7, (byte)1)) {
                  if (var3 <= 1222866659) {
                     throw new IllegalStateException();
                  }

                  long var8;
                  boolean var12;
                  label131: {
                     var8 = classRF.method9998(0, 0, 0, 0, false, -1315528093 * var4.field1478, var0.field1697 * 2140889407, (short)9495);
                     var12 = false;
                     if (var4.field1282 != null && client.field856 * 1612595797 >= 1411153905 * var4.field1256) {
                        if (var3 <= 1222866659) {
                           throw new IllegalStateException();
                        }

                        if (1612595797 * client.field856 < var4.field1261 * -1847262755) {
                           if (var3 <= 1222866659) {
                              throw new IllegalStateException();
                           }

                           var4.field1262 = false;
                           var4.field1274 = classKC.method6499(
                                 var0, var4.field1484 * -1547553299, var4.field1486 * -1272026483, var5, var4.vmethod267(-1423776655), (short)-3175
                              )
                              * -1668547461;
                           var4.field1487 = 1311089481 * client.field856;
                           var12 = Scene.method4212(
                              var0.field1687,
                              var5,
                              -1547553299 * var4.field1484,
                              -1272026483 * var4.field1486,
                              var4.field1274 * -617053517,
                              60,
                              var4,
                              var4.field1485 * 1457033841,
                              var8,
                              -633555803 * var4.field1277,
                              752753039 * var4.field1272,
                              var4.field1276 * -1556408309,
                              2015178417 * var4.field1278
                           );
                           break label131;
                        }
                     }

                     if (64 == (var4.field1484 * -1547553299 & 127)) {
                        if (var3 <= 1222866659) {
                           throw new IllegalStateException();
                        }

                        if ((var4.field1486 * -1272026483 & 127) == 64) {
                           if (var3 <= 1222866659) {
                              throw new IllegalStateException();
                           }

                           if (var0.field1701[var6][var7] == client.field957 * 834768165) {
                              if (var3 <= 1222866659) {
                                 throw new IllegalStateException();
                              }

                              return;
                           }

                           var0.field1701[var6][var7] = 834768165 * client.field957;
                        }
                     }

                     var4.field1274 = classKC.method6499(
                           var0, var4.field1484 * -1547553299, var4.field1486 * -1272026483, var5, var4.vmethod267(-1423776655), (short)-413
                        )
                        * -1668547461;
                     var4.field1487 = client.field856 * 1311089481;
                     var12 = var0.field1687
                        .method4505(
                           var5,
                           -1547553299 * var4.field1484,
                           -1272026483 * var4.field1486,
                           var4.field1274 * -617053517,
                           60,
                           var4,
                           var4.field1485 * 1457033841,
                           var8,
                           var4.field1438
                        );
                  }

                  if (var12) {
                     if (var3 <= 1222866659) {
                        throw new IllegalStateException();
                     }

                     classHH.method6088(var0, var4, var5, -617053517 * var4.field1274, var8);
                  }
               }
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "dv.cd(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   int method3634() {
      return -660838951 * this.field1634;
   }

   @ObfuscatedSignature(descriptor = "(Ldv;I)I")
   @ObfuscatedName("ko")
   public static int method3630(Hitsplat var0, int var1) {
      if (var0 == null) {
         var0.method3632(var1);
      }

      try {
         return var0.field1633 * 1682911625;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dv.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ag")
   void method3626(int var1, int var2, int var3) {
      this.field1633 = var1 * 714249401;
      this.field1636 = var2 * -1749090343;
      this.field1634 = 742771305 * var3;
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("jc")
   public void method3644(int var1, int var2, int var3) {
      this.method3625(var1, var2, var3, (byte)16);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   int method3636() {
      return 1616351337 * this.field1636;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   int method3637() {
      return 1616351337 * this.field1636;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   int method3638() {
      return 1616351337 * this.field1636;
   }

   @ObfuscatedSignature(descriptor = "(IS)V")
   @ObfuscatedName("ae")
   public static void method3641(int var0, short var1) {
      try {
         if (!classNP.field4696.isEmpty()) {
            if (var1 != -9107) {
               throw new IllegalStateException();
            }

            Iterator var2 = classNP.field4696.iterator();

            while (var2.hasNext()) {
               if (var1 != -9107) {
                  throw new IllegalStateException();
               }

               MidiRequest var3 = (MidiRequest)var2.next();
               if (null != var3) {
                  var3.field4633 = var0 * -1016653583;
               }
            }

            MidiRequest var5 = (MidiRequest)classNP.field4696.get(0);
            if (var5 != null) {
               if (var1 != -9107) {
                  throw new IllegalStateException();
               }

               if (null != var5.field4639) {
                  if (var1 != -9107) {
                     throw new IllegalStateException();
                  }

                  if (classNU.method8085(var5.field4639, (byte)10)) {
                     if (var1 != -9107) {
                        return;
                     }

                     if (!var5.field4632) {
                        if (var1 != -9107) {
                           return;
                        }

                        var5.field4639.method8048(var0, -530242251);
                        var5.field4629 = var0;
                     }
                  }
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dv.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldv;)I")
   @ObfuscatedName("aw")
   public static int method3631(Hitsplat var0) {
      return var0 == null ? var0.method3634() : var0.field1633 * 1682911625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   int method3635() {
      return -1403792956 * this.field1634;
   }

   public int getDisappearsOnGameCycle() {
      return this.field1634 * -660838951;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   int method3632(int var1) {
      try {
         return this.field1634 * 1682911625;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dv.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldv;S)I")
   @ObfuscatedName("tn")
   public static int method3639(Hitsplat var0, short var1) {
      if (var0 == null) {
         var0.method3640(var1);
      }

      try {
         return 1616351337 * var0.field1636;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dv.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ab")
   int method3640(short var1) {
      try {
         return 1616351337 * this.field1636;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dv.ab(" + 41);
      }
   }
}
