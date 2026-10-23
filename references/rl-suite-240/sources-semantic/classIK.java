import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ik")
public class classIK extends classHB {
   @ObfuscatedName("az")
   int field2983;
   @ObfuscatedName("af")
   int field2981;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field2982 = 74;

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("bl")
   static int method6262(int var0, byte var1) {
      try {
         return (int)((Math.log(var0) / classBB.field325 - 7.0) * 256.0);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ik.bl(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field2981 = classXY.method13113(var1, (byte)1) * -648578623;
         this.field2983 = classXY.method13113(var1, (byte)1) * 553629733;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ik.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field2981 = classXY.method13113(var1, (byte)1) * -648578623;
      this.field2983 = classXY.method13113(var1, (byte)1) * 553629733;
   }

   classIK(classIF var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "(FFFB)Luz;")
   @ObfuscatedName("af")
   public static classUZ method6261(float var0, float var1, float var2, byte var3) {
      try {
         synchronized (classUZ.field6428) {
            if (-1124722655 * classUZ.field6433 == 0) {
               if (var3 == 16) {
                  throw new IllegalStateException();
               } else {
                  return new classUZ(var0, var1, var2);
               }
            } else {
               classUZ.method11802(classUZ.field6428[(classUZ.field6433 -= 801074145) * -1124722655], var0, var1, var2, 1996170472);
               return classUZ.field6428[-1124722655 * classUZ.field6433];
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ik.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6323(this.field2981 * 1086748175, 1977459629 * this.field2983, (short)-6161);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6323(this.field2981 * -835479999, -2117980742 * this.field2983, (short)-3048);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6323(this.field2981 * -835479999, 264471570 * this.field2983, (short)9639);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIII)V")
   @ObfuscatedName("ez")
   static void method6263(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      try {
         if (var2 == 3) {
            if (var8 == 129979076) {
               return;
            }

            client.field958 = -727473855 * var4;
            client.field959 = var5 * 527203475;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = -877699493 * var1;
            int var9 = var3 & 65535;
            int var10 = var3 >> 16;
            classJL var11 = classEF.method3863(classJS.OPLOC1_V2, client.field796.field1533, (byte)-123);
            PacketBuffer var10000 = var11.field3341;
            byte var10001;
            if (client.field932.method4563(82, 754522953)) {
               if (var8 == 129979076) {
                  throw new IllegalStateException();
               }

               var10001 = 1;
            } else {
               var10001 = 0;
            }

            var10000.method13214(var10001, (byte)38);
            var11.field3341.method13244(var1 + var7, (short)-16375);
            classXY.method13029(var11.field3341, var10, (byte)-9);
            var11.field3341.method13244(var0 + var6, (short)-17819);
            classXY.method13242(var11.field3341, var9, (byte)16);
            client.field796.method3374(var11, -1301097035);
         }

         if (var2 == 4) {
            client.field958 = var4 * -727473855;
            client.field959 = var5 * 527203475;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = var1 * -877699493;
            int var13 = var3 & 65535;
            int var17 = var3 >> 16;
            classJL var21 = classEF.method3863(classJS.OPLOC2_V2, client.field796.field1533, (byte)29);
            var21.field3341.method13031(var1 + var7, -1542382992);
            var21.field3341.method13211(var17, (byte)25);
            PacketBuffer var25 = var21.field3341;
            byte var29;
            if (client.field932.method4563(82, 234334098)) {
               if (var8 == 129979076) {
                  throw new IllegalStateException();
               }

               var29 = 1;
            } else {
               var29 = 0;
            }

            var25.method13214(var29, (byte)-94);
            var21.field3341.method13031(var13, -1542382992);
            classXY.method13239(var21.field3341, var6 + var0, (byte)72);
            client.field796.method3374(var21, -1301097035);
         }

         if (5 == var2) {
            if (var8 == 129979076) {
               throw new IllegalStateException();
            }

            client.field958 = -727473855 * var4;
            client.field959 = var5 * 527203475;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = var0 * -1254957895;
            client.field835 = var1 * -877699493;
            int var14 = var3 & 65535;
            int var18 = var3 >> 16;
            classJL var22 = classEF.method3863(classJS.OPLOC3_V2, client.field796.field1533, (byte)12);
            var22.field3341.method13214(var18, (byte)76);
            PacketBuffer var26 = var22.field3341;
            byte var30;
            if (client.field932.method4563(82, 2109348940)) {
               if (var8 == 129979076) {
                  throw new IllegalStateException();
               }

               var30 = 1;
            } else {
               var30 = 0;
            }

            var26.method13214(var30, (byte)-82);
            classXY.method13242(var22.field3341, var7 + var1, (byte)16);
            var22.field3341.method13244(var14, (short)-2341);
            var22.field3341.method13031(var6 + var0, -1542382992);
            client.field796.method3374(var22, -1301097035);
         }

         if (6 == var2) {
            if (var8 == 129979076) {
               return;
            }

            client.field958 = var4 * -727473855;
            client.field959 = 527203475 * var5;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = -1254957895 * var0;
            client.field835 = var1 * -877699493;
            int var15 = var3 & 65535;
            int var19 = var3 >> 16;
            classJL var23 = classEF.method3863(classJS.OPLOC4_V2, client.field796.field1533, (byte)71);
            PacketBuffer var27 = var23.field3341;
            byte var31;
            if (client.field932.method4563(82, -64012912)) {
               if (var8 == 129979076) {
                  throw new IllegalStateException();
               }

               var31 = 1;
            } else {
               var31 = 0;
            }

            var27.method13209(var31, -1910538783);
            classXY.method13239(var23.field3341, var15, (byte)-11);
            var23.field3341.method13031(var1 + var7, -1542382992);
            var23.field3341.method13209(var19, -1910538783);
            var23.field3341.method13031(var6 + var0, -1542382992);
            client.field796.method3374(var23, -1301097035);
         }

         if (var2 == 1001) {
            client.field958 = var4 * -727473855;
            client.field959 = 527203475 * var5;
            client.field864 = -921463806;
            client.field970 = 0;
            client.field860 = var0 * -1254957895;
            client.field835 = -877699493 * var1;
            int var16 = var3 & 65535;
            int var20 = var3 >> 16;
            classJL var24 = classEF.method3863(classJS.OPLOC5_V2, client.field796.field1533, (byte)-47);
            classXY.method13239(var24.field3341, var7 + var1, (byte)-87);
            var24.field3341.method13031(var6 + var0, -1542382992);
            var24.field3341.method13209(var20, -1910538783);
            PacketBuffer var28 = var24.field3341;
            byte var32;
            if (client.field932.method4563(82, 640552038)) {
               if (var8 == 129979076) {
                  return;
               }

               var32 = 1;
            } else {
               var32 = 0;
            }

            var28.method13214(var32, (byte)23);
            var24.field3341.method13244(var16, (short)-320);
            client.field796.method3374(var24, -1301097035);
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "ik.ez(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6323(this.field2981 * -835479999, 1977459629 * this.field2983, (short)738);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ik.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field2981 = classXY.method13113(var1, (byte)1) * -648578623;
      this.field2983 = classXY.method13113(var1, (byte)1) * 553629733;
   }
}
