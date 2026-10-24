import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ch")
public final class classCH extends AmbientSoundEffect {
   @ToRemove(unused = "true")
   @ObfuscatedName("bn")
   static final int field738 = 202;
   @ToRemove(unused = "true")
   @ObfuscatedName("el")
   static final int field739 = 5;
   @ObfuscatedSignature(descriptor = "Ldn;")
   @ObfuscatedName("az")
   final NPC field734;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field735 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final int field737 = 24;
   @ObfuscatedName("fm")
   static Iterator field741;
   @ToRemove(unused = "true")
   @ObfuscatedName("lz")
   static String field740;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field736 = 6;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   @Override
   int vmethod204() {
      return this.field734.field1486 * -1272026483;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod189(int var1) {
      try {
         if (this.field734 == null) {
            if (var1 < 832295818) {
               ;
            }
         } else if (this.field734.field1579 == null) {
            if (var1 >= 832295818) {
               throw new IllegalStateException();
            } else {
               this.method3675(-621591188);
            }
         } else if (this.field734.field1579.field5323 != null) {
            NPCComposition var2 = this.field734.field1579.method8998(652890385);
            if (var2 == null) {
               if (var1 >= 832295818) {
                  throw new IllegalStateException();
               } else {
                  this.field1641 = true;
               }
            } else {
               int var3 = -1727985133 * this.field1651.field6687;
               this.method3661(var2.field5281, var2.field5333, (byte)-56);
               if (var3 != -1727985133 * this.field1651.field6687) {
                  this.field1641 = true;
               }
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ch.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   @Override
   int vmethod194(int var1) {
      try {
         return -1547553299 * this.field734.field1484;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ch.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   @Override
   int vmethod197(int var1) {
      try {
         return -1547553299 * this.field734.field1484;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ch.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   @Override
   int vmethod202(int var1) {
      try {
         return -1272026483 * this.field734.field1486;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ch.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   @Override
   int vmethod205(int var1) {
      try {
         return this.field734.field1486 * -1272026483;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ch.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   @Override
   void vmethod190() {
      if (this.field734 != null) {
         if (this.field734.field1579 == null) {
            this.method3675(560337998);
         } else if (this.field734.field1579.field5323 != null) {
            NPCComposition var1 = this.field734.field1579.method8998(652890385);
            if (var1 == null) {
               this.field1641 = true;
            } else {
               int var2 = -1727985133 * this.field1651.field6687;
               this.method3661(var1.field5281, var1.field5333, (byte)-105);
               if (var2 != -1727985133 * this.field1651.field6687) {
                  this.field1641 = true;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   @Override
   void vmethod191() {
      if (this.field734 != null) {
         if (this.field734.field1579 == null) {
            this.method3675(-934662401);
         } else if (this.field734.field1579.field5323 != null) {
            NPCComposition var1 = this.field734.field1579.method8998(652890385);
            if (var1 == null) {
               this.field1641 = true;
            } else {
               int var2 = -1727985133 * this.field1651.field6687;
               this.method3661(var1.field5281, var1.field5333, (byte)-36);
               if (var2 != -1727985133 * this.field1651.field6687) {
                  this.field1641 = true;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   @Override
   int vmethod203() {
      return 1896303741 * this.field734.field1486;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   @Override
   int vmethod195() {
      return -1547553299 * this.field734.field1484;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   @Override
   int vmethod196() {
      return -1547553299 * this.field734.field1484;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;Z)V")
   @ObfuscatedName("cr")
   public static void method1496(classQC var0, boolean var1) {
      if (var0 == null) {
         var0.method9510();
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIIIIIILct;I)V")
   @ObfuscatedName("dz")
   static void method1498(
      int var0,
      int var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      int var11,
      int var12,
      Player var13,
      int var14
   ) {
      try {
         ObjectComposition var15;
         int var16;
         int var17;
         label64: {
            var15 = classMU.method7771(var6, 1501978861);
            if (var4 != 1) {
               if (var14 <= 1600128213) {
                  throw new IllegalStateException();
               }

               if (3 != var4) {
                  var16 = var15.field4942 * -582306509;
                  var17 = var15.field4922 * 402876423;
                  break label64;
               }

               if (var14 <= 1600128213) {
                  throw new IllegalStateException();
               }
            }

            var16 = 402876423 * var15.field4922;
            var17 = var15.field4942 * -582306509;
         }

         int var18 = var1 + (var16 >> 1);
         int var19 = (1 + var16 >> 1) + var1;
         int var20 = var2 + (var17 >> 1);
         int var21 = (1 + var17 >> 1) + var2;
         int[][] var22 = classCQ.field1218.field1686[var0];
         int var23 = var22[var18][var21] + var22[var19][var20] + var22[var18][var20] + var22[var19][var21] >> 2;
         int var24 = (var1 << 7) + (var16 << 6);
         int var25 = (var17 << 6) + (var2 << 7);
         Model var26 = var15.method8575(var3, var4, var22, var24, var23, var25, -1320580868);
         if (var26 != null) {
            if (var14 <= 1600128213) {
               throw new IllegalStateException();
            }

            classAT.method691(classCQ.field1218, var0, var1, var2, var5, -1, 0, 0, 31, null, 1 + var7, var8 + 1, -497118428);
            var13.field1256 = -2082172655 * (client.field856 * 1612595797 + var7);
            var13.field1261 = (1612595797 * client.field856 + var8) * -1789698443;
            var13.field1282 = var26;
            var13.field1279 = -843265152 * var1 + var16 * -421632576;
            var13.field1271 = -1031821696 * var2 + var17 * -515910848;
            var13.field1275 = 606289519 * var23;
            if (var9 > var11) {
               int var27 = var9;
               var9 = var11;
               var11 = var27;
            }

            if (var10 > var12) {
               if (var14 <= 1600128213) {
                  throw new IllegalStateException();
               }

               int var29 = var10;
               var10 = var12;
               var12 = var29;
            }

            var13.field1277 = (var1 + var9) * 2054951725;
            var13.field1276 = -1464814685 * (var11 + var1);
            var13.field1272 = (var2 + var10) * 214623599;
            var13.field1278 = 1014651473 * (var12 + var2);
         }
      } catch (RuntimeException var28) {
         throw classEG.method3899(var28, "ch.dz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   @Override
   void vmethod192() {
      if (this.field734 != null) {
         if (this.field734.field1579 == null) {
            this.method3675(1016567356);
         } else if (this.field734.field1579.field5323 != null) {
            NPCComposition var1 = this.field734.field1579.method8998(652890385);
            if (var1 == null) {
               this.field1641 = true;
            } else {
               int var2 = -1727985133 * this.field1651.field6687;
               this.method3661(var1.field5281, var1.field5333, (byte)-93);
               if (var2 != 1766848654 * this.field1651.field6687) {
                  this.field1641 = true;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ay")
   @Override
   int vmethod198() {
      return -1547553299 * this.field734.field1484;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   @Override
   int vmethod199() {
      return -1547553299 * this.field734.field1484;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   @Override
   int vmethod200() {
      return -781849870 * this.field734.field1484;
   }

   classCH(NPC var1) {
      super(var1.vmethod258((byte)-74), var1.field1579.field5281, var1.field1579.field5333);
      this.field734 = var1;
      if (var1.field1579.field5323 != null) {
         NPCComposition var2 = var1.field1579.method8998(652890385);
         if (var2 != null) {
            this.method3661(var2.field5281, var2.field5333, (byte)-79);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   @Override
   int vmethod206() {
      return this.field734.field1486 * -1272026483;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   @Override
   int vmethod201() {
      return -1547553299 * this.field734.field1484;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aw")
   public static int method1497(int var0, int var1) {
      try {
         if (var0 > 0) {
            if (var1 == 883999582) {
               throw new IllegalStateException();
            } else {
               return 1;
            }
         } else if (var0 < 0) {
            if (var1 == 883999582) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ch.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   @Override
   void vmethod193() {
      if (this.field734 != null) {
         if (this.field734.field1579 == null) {
            this.method3675(-1847618187);
         } else if (this.field734.field1579.field5323 != null) {
            NPCComposition var1 = this.field734.field1579.method8998(652890385);
            if (var1 == null) {
               this.field1641 = true;
            } else {
               int var2 = 1730521640 * this.field1651.field6687;
               this.method3661(var1.field5281, var1.field5333, (byte)-67);
               if (var2 != 1180142742 * this.field1651.field6687) {
                  this.field1641 = true;
               }
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("cn")
   static void method1499(Widget var0, int var1) {
      try {
         var0.method7332(1995595493);
         classLH.method7015(var0.method7334(-1446730120), new classDQ(var0), 332209502);
         var0.method7334(-1696437786).method7009(new classCA(var0), (byte)3);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ch.cn(" + ')');
      }
   }
}
