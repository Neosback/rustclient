import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ea")
public class classEA implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("bh")
   static final int field1716 = 65536;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field1714 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field1715 = 1;
   @ObfuscatedName("az")
   final boolean field1713;

   @ObfuscatedSignature(descriptor = "(Lri;Lri;I)I")
   @ObfuscatedName("az")
   int method3806(ChatPlayer var1, ChatPlayer var2, int var3) {
      try {
         int var10000;
         if (this.field1713) {
            if (var3 != -1530233874) {
               throw new IllegalStateException();
            }

            var10000 = var1.field5668 * 1145202131 - 1145202131 * var2.field5668;
         } else {
            var10000 = var2.field5668 * 1145202131 - var1.field5668 * 1145202131;
         }

         return var10000;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ea.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lgw;")
   @ObfuscatedName("az")
   static classGW[] method3812(int var0) {
      try {
         return new classGW[]{
            classGW.field2716, classGW.field2721, classGW.field2718, classGW.field2715, classGW.field2719, classGW.field2720, classGW.field2717
         };
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ea.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lea;Ljava/lang/Object;)Z")
   @ObfuscatedName("ij")
   public static boolean method3810(classEA var0, Object var1) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return var0.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method3809(Object var1, Object var2) {
      return this.method3806((ChatPlayer)var1, (ChatPlayer)var2, -1530233874);
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ea.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("gk")
   static final int method3816(byte var0) {
      try {
         return classKF.field3569.vmethod388(-662124149);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ea.gk(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lea;Ljava/lang/Object;)Z")
   @ObfuscatedName("ry")
   public static boolean method3811(classEA var0, Object var1) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return var0.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("ae")
   int method3807(ChatPlayer var1, ChatPlayer var2) {
      return this.field1713 ? var1.field5668 * 1145202131 - 1145202131 * var2.field5668 : var2.field5668 * 1145202131 - var1.field5668 * 1145202131;
   }

   @ObfuscatedSignature(descriptor = "(Lri;Lri;)I")
   @ObfuscatedName("af")
   int method3808(ChatPlayer var1, ChatPlayer var2) {
      return this.field1713 ? var1.field5668 * 1145202131 - 1145202131 * var2.field5668 : var2.field5668 * 1145202131 - var1.field5668 * -1934088667;
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method3806((ChatPlayer)var1, (ChatPlayer)var2, -1530233874);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ea.compare(" + 41);
      }
   }

   public classEA(boolean var1) {
      this.field1713 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqb;)Ljava/lang/String;")
   @ObfuscatedName("rs")
   public static String method3805(classQB var0) {
      return var0.field5511;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;)I")
   @ObfuscatedName("gs")
   public static int method3813(classSM var0) {
      return 2086456713 * var0.field5974;
   }

   @ObfuscatedSignature(descriptor = "(IIIILjava/lang/String;I)V")
   @ObfuscatedName("fo")
   static void method3815(int var0, int var1, int var2, int var3, String var4, int var5) {
      try {
         int var6 = var0 >>> 16;
         int var7 = var0 & 65535;
         Widget var8 = classWK.field6689.method7422(var1, var2, (byte)-32);
         if (null == var8) {
            if (var5 < 1311907745) {
               ;
            }
         } else {
            if (null != var8.field4384) {
               if (var5 >= 1311907745) {
                  throw new IllegalStateException();
               }

               ScriptEvent var9 = classUQ.method11527(var8, 1123605922)
                  .method14195(var8.field4384, (byte)2)
                  .method14204(var7, -2038314960)
                  .method14210(var6, -1403051862)
                  .method14213(var4, (byte)19)
                  .method14226((byte)127);
               classSZ.method10771(var9, -1484194837);
            }

            boolean var15 = true;
            if (var8.field4332 * -90794217 > 0) {
               if (var5 >= 1311907745) {
                  throw new IllegalStateException();
               }

               var15 = classCR.method2566(var8, 926553544);
            }

            if (!var15) {
               if (var5 >= 1311907745) {
                  throw new IllegalStateException();
               }
            } else {
               int var11 = classVV.method12214(classWK.field6689, var8, (short)255);
               int var12 = var7 - 1;
               boolean var10 = 0 != (var11 >> var12 & 1);
               if (!var10) {
                  if (var5 < 1311907745) {
                     ;
                  }
               } else {
                  if (var6 == 0) {
                     if (var5 >= 1311907745) {
                        throw new IllegalStateException();
                     }

                     classJL var13 = classEF.method3863(classJS.IF_BUTTONX, client.field796.field1533, (byte)88);
                     var13.field3341.method13037(var1, 616039869);
                     var13.field3341.method13031(var2, -1542382992);
                     var13.field3341.method13031(var3, -1542382992);
                     classXY.method13029(var13.field3341, var7, (byte)-52);
                     client.field796.method3374(var13, -1301097035);
                  } else {
                     classJL var16 = classEF.method3863(classJS.IF_SUBOP, client.field796.field1533, (byte)20);
                     var16.field3341.method13037(var1, 104651136);
                     var16.field3341.method13031(var2, -1542382992);
                     var16.field3341.method13031(var3, -1542382992);
                     classXY.method13029(var16.field3341, var7, (byte)-122);
                     classXY.method13029(var16.field3341, var6 - 1, (byte)-127);
                     client.field796.method3374(var16, -1301097035);
                  }
               }
            }
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "ea.fo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lvt;")
   @ObfuscatedName("ab")
   static classVT method3814(int var0) {
      try {
         return classEB.field1725;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ea.ab(" + ')');
      }
   }
}
