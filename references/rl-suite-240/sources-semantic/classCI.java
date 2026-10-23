import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ci")
public class classCI implements Comparator {
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field742 = 31;
   @ObfuscatedName("az")
   boolean field744;
   @ToRemove(unused = "true")
   @ObfuscatedName("by")
   public static final int field743 = 68;

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;B)I")
   @ObfuscatedName("az")
   int method1504(classNM var1, classNM var2, byte var3) {
      try {
         if (var2.field4684 * -906797091 == -906797091 * var1.field4684) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else {
            if (this.field744) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               if (-906797091 * var1.field4684 == 1311484143 * client.field828) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  return -1;
               }

               if (-906797091 * var2.field4684 == client.field828 * 1311484143) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  return 1;
               }
            }

            byte var10000;
            if (var1.field4684 * -906797091 < -906797091 * var2.field4684) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               var10000 = -1;
            } else {
               var10000 = 1;
            }

            return var10000;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ci.az(" + 41);
      }
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return this.method1504((classNM)var1, (classNM)var2, (byte)-60);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ci.compare(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ci.equals(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("wa")
   public static int method1501(classLH var0) {
      return classZD.method13991(var0.field4157, -504149574);
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIIILgc;I)V")
   @ObfuscatedName("aq")
   static final void method1514(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6, CollisionData var7, int var8) {
      rl4 var9 = var0.isTopLevel() ? client.field1121 : client.field1122;
      if (var1 < classDI.field1509 * -791964067) {
         classDI.field1509 = var1 * 360312309;
      }

      Scene var10 = var9.field5787;
      ObjectComposition var11 = ObjectComposition.method8638(var4);
      int var12;
      int var13;
      if (var5 != 1 && var5 != 3) {
         var12 = var11.field4942 * -582306509;
         var13 = var11.field4922 * 402876423;
      } else {
         var12 = var11.field4922 * 402876423;
         var13 = var11.field4942 * -582306509;
      }

      int var14;
      int var15;
      if (var12 + var2 <= var10.field2142) {
         var14 = (var12 >> 1) + var2;
         var15 = (var12 + 1 >> 1) + var2;
      } else {
         var14 = var2;
         var15 = var2 + 1;
      }

      var14 += var10.field2146;
      var15 += var10.field2146;
      int var16;
      int var17;
      if (var13 + var3 <= var10.field2139) {
         var16 = (var13 >> 1) + var3;
         var17 = (var13 + 1 >> 1) + var3;
      } else {
         var16 = var3;
         var17 = var3 + 1;
      }

      var16 += var10.field2146;
      var17 += var10.field2146;
      int var18 = var2 + var10.field2146;
      int var19 = var3 + var10.field2146;
      boolean var20 = var2 > 0 && var2 < 103 && var3 > 0 && var3 < 103;
      int[][] var21 = var9.field5795[var1];
      int var22 = var21[var14][var17] + var21[var14][var16] + var21[var15][var16] + var21[var15][var17] >> 2;
      int var23 = (var18 << 7) + (var12 << 6);
      int var24 = (var19 << 7) + (var13 << 6);
      boolean var25 = var11.field4921 * 1663954033 != 0 && var2 >= 0 && var2 < 128 && var3 >= 0 && var3 < 128;
      long var26 = client.method2368(var1, var2, var3, 2, !var25, var4, var0.field1697 * 2140889407);
      int var28 = var6 + (var5 << 6);
      if (var11.field4954 * 1291058669 == 1) {
         var28 += 256;
      }

      if (var11.method8637()) {
         client.method2323(var9, var1, var2, var3, var11, var5);
      }

      if (var6 == 22) {
         boolean var29 = !client.field886 || var11.field4921 * 1663954033 != 0 || var11.field4944 * -2040437663 == 1 || var11.field4931;
         if (!var29) {
            if (!client.$assertionsDisabled && !client.field886) {
               throw new AssertionError();
            }

            int var30 = var10.field2159[var1][var18][var19] & 32767;
            if (var30 > 0) {
               classOW var31 = var9.method10125(var30 - 1);
               if (var31.method8848() < 0 && var31.method8847() == 16711935) {
                  var29 = true;
               }
            }
         }

         if (var29) {
            Object var54;
            if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
               var54 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, var6, var5, var1, var2, var3, var23, var22, var24);
            } else {
               DynamicObject var67 = new DynamicObject(var0, var4, 22, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
               var67.field1665 = var10;
               var54 = var67;
            }

            var10.method4527(var1, var2, var3, var22, (Renderable)var54, var26, var28);
            if (var11.field4944 * -2040437663 == 1 && var7 != null && var20) {
               var7.method5418(var2, var3);
            }
         }
      } else if (var6 >= 12) {
         Object var40;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var40 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, var6, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var55 = new DynamicObject(var0, var4, var6, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var55.field1665 = var10;
            var40 = var55;
         }

         var10.method4519(var1, var2, var3, var22, 1, 1, (Renderable)var40, 0, var26, var28);
         if (var6 <= 17 && var6 != 13 && var1 > 0) {
            var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 2340;
         }

         if (var11.field4944 * -2040437663 != 0 && var7 != null && var20) {
            var7.method5419(var2, var3, var12, var13, var11.field4931);
         }
      } else if (var6 == 10 || var6 == 11) {
         Object var53;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var53 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, 10, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var65 = new DynamicObject(var0, var4, 10, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var65.field1665 = var10;
            var53 = var65;
         }

         if (var53 != null && var10.method4519(var1, var2, var3, var22, var12, var13, (Renderable)var53, var6 == 11 ? 256 : 0, var26, var28) && var11.field4936
            )
          {
            int var66 = 15;
            if (var53 instanceof Model) {
               Model var73 = (Model)var53;
               var73.calculateBoundsCylinder();
               var66 = var73.getXYZMag() / 4;
               if (var66 > 30) {
                  var66 = 30;
               }
            }

            for (int var74 = 0; var74 <= var12; var74++) {
               for (int var78 = 0; var78 <= var13; var78++) {
                  if (var66 > var9.field5774[var1][var74 + var18][var78 + var19]) {
                     var9.field5774[var1][var74 + var18][var78 + var19] = (byte)var66;
                  }
               }
            }
         }

         if (var11.field4944 * -2040437663 != 0 && var7 != null && var20) {
            var7.method5419(var2, var3, var12, var13, var11.field4945);
         }
      } else if (var6 == 0) {
         Object var41;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var41 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, var6, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var56 = new DynamicObject(var0, var4, 0, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var56.field1665 = var10;
            var41 = var56;
         }

         var10.method4523(var1, var2, var3, var22, (Renderable)var41, null, 1 << var5, 0, var26, var28);
         if (var5 == 0) {
            if (var11.field4936) {
               var9.field5774[var1][var18][var19] = 50;
               var9.field5774[var1][var18][var19 + 1] = 50;
            }

            if (var11.field4948) {
               var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 585;
            }
         } else if (var5 == 1) {
            if (var11.field4936) {
               var9.field5774[var1][var18][var19 + 1] = 50;
               var9.field5774[var1][var18 + 1][var19 + 1] = 50;
            }

            if (var11.field4948) {
               var9.field5773[var1][var18][var19 + 1] = var9.field5773[var1][var18][var19 + 1] | 1170;
            }
         } else if (var5 == 2) {
            if (var11.field4936) {
               var9.field5774[var1][var18 + 1][var19] = 50;
               var9.field5774[var1][var18 + 1][var19 + 1] = 50;
            }

            if (var11.field4948) {
               var9.field5773[var1][var18 + 1][var19] = var9.field5773[var1][var18 + 1][var19] | 585;
            }
         } else if (var5 == 3) {
            if (var11.field4936) {
               var9.field5774[var1][var18][var19] = 50;
               var9.field5774[var1][var18 + 1][var19] = 50;
            }

            if (var11.field4948) {
               var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 1170;
            }
         }

         if (var11.field4944 * -2040437663 != 0 && var7 != null && var20) {
            var7.method5417(var2, var3, var6, var5, var11.field4945);
         }

         if (var11.field4925 * -1659674841 != 16) {
            var10.method4543(var1, var2, var3, var11.field4925 * -1659674841 + 1);
         }
      } else if (var6 == 1 || var6 == 3) {
         Object var52;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var52 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, var6, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var64 = new DynamicObject(var0, var4, var6, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var64.field1665 = var10;
            var52 = var64;
         }

         var10.method4523(var1, var2, var3, var22, (Renderable)var52, null, 16 << var5, 0, var26, var28);
         if (var11.field4936) {
            if (var5 == 0) {
               var9.field5774[var1][var18][var19 + 1] = 50;
            } else if (var5 == 1) {
               var9.field5774[var1][var18 + 1][var19 + 1] = 50;
            } else if (var5 == 2) {
               var9.field5774[var1][var18 + 1][var19] = 50;
            } else if (var5 == 3) {
               var9.field5774[var1][var18][var19] = 50;
            }
         }

         if (var11.field4944 * -2040437663 != 0 && var7 != null && var20) {
            var7.method5417(var2, var3, var6, var5, var11.field4945);
         }
      } else if (var6 == 2) {
         int var42 = var5 + 1 & 3;
         Object var57;
         Object var68;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var68 = var11.method8636(2, var5 + 4, var21, var23, var22, var24);
            var57 = var11.method8636(2, var42, var21, var23, var22, var24);
         } else {
            DynamicObject var32 = new DynamicObject(var0, var4, 2, var5 + 4, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            DynamicObject var33 = new DynamicObject(var0, var4, 2, var42, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var32.field1665 = var33.field1665 = var10;
            var57 = var32;
            var68 = var33;
         }

         var10.method4523(var1, var2, var3, var22, (Renderable)var68, (Renderable)var57, 1 << var5, 1 << var42, var26, var28);
         if (var11.field4948) {
            if (var5 == 0) {
               var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 585;
               var9.field5773[var1][var18][var19 + 1] = var9.field5773[var1][var18][var19 + 1] | 1170;
            } else if (var5 == 1) {
               var9.field5773[var1][var18][var19 + 1] = var9.field5773[var1][var18][var19 + 1] | 1170;
               var9.field5773[var1][var18 + 1][var19] = var9.field5773[var1][var18 + 1][var19] | 585;
            } else if (var5 == 2) {
               var9.field5773[var1][var18 + 1][var19] = var9.field5773[var1][var18 + 1][var19] | 585;
               var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 1170;
            } else if (var5 == 3) {
               var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 1170;
               var9.field5773[var1][var18][var19] = var9.field5773[var1][var18][var19] | 585;
            }
         }

         if (var11.field4944 * -2040437663 != 0 && var7 != null && var20) {
            var7.method5417(var2, var3, var6, var5, var11.field4945);
         }

         if (var11.field4925 * -1659674841 != 16) {
            var10.method4543(var1, var2, var3, var11.field4925 * -1659674841 + 1);
         }
      } else if (var6 == 9) {
         Object var43;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var43 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, var6, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var58 = new DynamicObject(var0, var4, var6, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var58.field1665 = var10;
            var43 = var58;
         }

         var10.method4519(var1, var2, var3, var22, 1, 1, (Renderable)var43, 0, var26, var28);
         if (var11.field4944 * -2040437663 != 0 && var7 != null && var20) {
            var7.method5419(var2, var3, var12, var13, var11.field4945);
         }

         if (var11.field4925 * -1659674841 != 16) {
            var10.method4543(var1, var2, var3, var11.field4925 * -1659674841 / 2 + 1);
         }
      } else if (var6 == 4) {
         Object var44;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var44 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, var6, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var59 = new DynamicObject(var0, var4, 4, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var59.field1665 = var10;
            var44 = var59;
         }

         var10.method4525(var1, var2, var3, var22, (Renderable)var44, null, 1 << var5, 0, classDI.field1505[var5], classDI.field1506[var5], 0, 0, var26, var28);
      } else if (var6 == 5) {
         int var45 = 16;
         WallObject var60 = var10.method4530(var1, var2, var3);
         if (var60 != null) {
            var45 = ObjectComposition.method8638(var60.getId()).field4925 * -1659674841;
         }

         var45++;
         Object var69;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var69 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, 4, var5, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var75 = new DynamicObject(var0, var4, 4, var5, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var75.field1665 = var10;
            var69 = var75;
         }

         var10.method4525(
            var1,
            var2,
            var3,
            var22,
            (Renderable)var69,
            null,
            1 << var5,
            0,
            var45 * classDI.field1505[var5],
            var45 * classDI.field1506[var5],
            0,
            0,
            var26,
            var28
         );
      } else if (var6 == 6) {
         int var47 = 8;
         GameObject var61 = var10.method4531(var1, var2, var3);
         if (var61 != null) {
            var47 = ObjectComposition.method8638(var61.getId()).field4925 * -1659674841 / 2;
         }

         var47++;
         Object var70;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var70 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, 4, var5 + 4, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var76 = new DynamicObject(var0, var4, 4, var5 + 4, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var76.field1665 = var10;
            var70 = var76;
         }

         var10.method4525(
            var1, var2, var3, var22, (Renderable)var70, null, 256, var5, var47 * classDI.field1507[var5], var47 * classDI.field1508[var5], 0, 0, var26, var28
         );
      } else if (var6 == 7) {
         int var49 = var5 + 2 & 3;
         Object var62;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var62 = client.method1656(var0.field1687, var10, var11, var21, var6, var5, 4, var49 + 4, var1, var2, var3, var23, var22, var24);
         } else {
            DynamicObject var71 = new DynamicObject(var0, var4, 4, var49 + 4, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var71.field1665 = var10;
            var62 = var71;
         }

         var10.method4525(var1, var2, var3, var22, (Renderable)var62, null, 256, var49, classDI.field1507[var49], classDI.field1508[var49], 0, 0, var26, var28);
      } else if (var6 == 8) {
         int var50 = 8;
         GameObject var63 = var10.method4531(var1, var2, var3);
         if (var63 != null) {
            var50 = ObjectComposition.method8638(var63.getId()).field4925 * -1659674841 / 2;
         }

         var50++;
         int var72 = var5 + 2 & 3;
         Object var77;
         Object var79;
         if (var11.field4949 * -1542230501 == -1 && var11.field4928 == null) {
            var77 = var11.method8636(4, var5 + 4, var21, var23, var22, var24);
            var79 = var11.method8636(4, var72 + 4, var21, var23, var22, var24);
         } else {
            DynamicObject var34 = new DynamicObject(var0, var4, 4, var5 + 4, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            DynamicObject var35 = new DynamicObject(var0, var4, 4, var72 + 4, var1, var2, var3, var11.field4949 * -1542230501, var11.field4962, null);
            var34.field1665 = var35.field1665 = var10;
            var77 = var34;
            var79 = var35;
         }

         var10.method4525(
            var1,
            var2,
            var3,
            var22,
            (Renderable)var77,
            (Renderable)var79,
            256,
            var5,
            var50 * classDI.field1507[var5],
            var50 * classDI.field1508[var5],
            classDI.field1507[var72],
            classDI.field1508[var72],
            var26,
            var28
         );
      } else {
         client.field1028.debug("unknown loc type {}", var6);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("ag")
   int method1505(classNM var1, classNM var2) {
      if (var2.field4684 * 1684346448 == -906797091 * var1.field4684) {
         return 0;
      } else {
         if (this.field744) {
            if (-208785858 * var1.field4684 == 1311484143 * client.field828) {
               return -1;
            }

            if (105879339 * var2.field4684 == client.field828 * 1311484143) {
               return 1;
            }
         }

         return var1.field4684 * -1029759094 < -906797091 * var2.field4684 ? -1 : 1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("ae")
   int method1506(classNM var1, classNM var2) {
      if (var2.field4684 * -906797091 == -906797091 * var1.field4684) {
         return 0;
      } else {
         if (this.field744) {
            if (-906797091 * var1.field4684 == 1311484143 * client.field828) {
               return -1;
            }

            if (-906797091 * var2.field4684 == client.field828 * 1311484143) {
               return 1;
            }
         }

         return var1.field4684 * -906797091 < -906797091 * var2.field4684 ? -1 : 1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lci;Ljava/lang/Object;)Z")
   @ObfuscatedName("qg")
   public static boolean method1511(classCI var0, Object var1) {
      return var0 == null ? var0.method1513(var1) : var0.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lnm;Lnm;)I")
   @ObfuscatedName("ab")
   int method1507(classNM var1, classNM var2) {
      if (var2.field4684 * -906797091 == -906797091 * var1.field4684) {
         return 0;
      } else {
         if (this.field744) {
            if (-906797091 * var1.field4684 == 1311484143 * client.field828) {
               return -1;
            }

            if (-906797091 * var2.field4684 == client.field828 * 1311484143) {
               return 1;
            }
         }

         return var1.field4684 * -906797091 < -906797091 * var2.field4684 ? -1 : 1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("as")
   public int method1509(Object var1, Object var2) {
      return this.method1504((classNM)var1, (classNM)var2, (byte)-80);
   }

   @ObfuscatedSignature(descriptor = "(IIIIIII)V")
   @ObfuscatedName("au")
   static void method1500(int var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         client.field1069 = var0 * -1232180137;
         classDX.field1660 = -339051177 * var1;
         classDU.field1631 = -2072039747 * var2;
         classDB.field1373 = var3 * 1321524983;
         classYJ.field6992 = 1289423847 * var4;
         World.field1163 = -1152046927 * var5;
         classLS.field4217 = (var3 + var0) / 2 * -28035055;
         classKT.field4020 = 2097852467 * ((var4 + var1) / 2);
         classHQ.field2866 = (var5 + var2) / 2 * -1040762067;
         classBY.field614 = (var3 - var0) / 2 * -551665805;
         classDK.field1540 = 15977997 * ((var4 - var1) / 2);
         GroundObject.field1809 = (var5 - var2) / 2 * -1577570207;
         classVI.field6511 = Math.abs(classBY.field614 * -331219525) * 255059385;
         classJI.field3162 = Math.abs(classDK.field1540 * 348139717) * 1863713019;
         classEV.field1941 = Math.abs(-1947307103 * GroundObject.field1809) * -36859613;
         classUZ.method11802(classFO.field2343, var3 - var0, var4 - var1, var5 - var2, 1764530601);
         classFO.field2343.method11817(-114514689);
         classFO.field2344 = true;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ci.au(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lci;Ljava/lang/Object;)Z")
   @ObfuscatedName("yq")
   public static boolean method1512(classCI var0, Object var1) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return var0.equals(var1);
   }

   classCI() {
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ar")
   static void method1502(int var0, byte var1) {
      client.method2024(var0);

      try {
         if (var0 != 56 && var0 != 57) {
            classJB.method6386(10, -1192966987);
            classAC.method321(9, 2007366360);
         } else {
            classJB.method6386(11, -1192966987);
         }

         if (var0 == -3) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            WorldEntity.method10671(classKK.field3706, classKK.field3651, classKK.field3809, (byte)-26);
         } else if (var0 == -2) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            WorldEntity.method10671(classKK.field3735, classKK.field3918, classKK.field3787, (byte)-61);
         } else if (var0 == -1) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            WorldEntity.method10671(classKK.field3697, classKK.field3685, classKK.field3658, (byte)-5);
         } else if (var0 == 3) {
            classAC.method321(3, 2073840155);
            classBF.field388 = -945703767;
         } else if (var0 == 4) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            classAC.method321(14, 2021207162);
            classBF.field387 = 0;
         } else if (var0 == 5) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            classBF.field388 = -1891407534;
            WorldEntity.method10671(classKK.field3662, classKK.field3663, classKK.field3664, (byte)-55);
         } else if (68 == var0) {
            if (var1 <= 0) {
               return;
            }

            if (!client.field832) {
               if (var1 <= 0) {
                  return;
               }

               client.field832 = true;
               classAC.method319((byte)11);
               return;
            }

            WorldEntity.method10671(classKK.field3665, classKK.field3666, classKK.field3609, (byte)-60);
         } else {
            if (!client.field891) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               if (var0 == 6) {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  WorldEntity.method10671(classKK.field3665, classKK.field3666, classKK.field3609, (byte)-66);
                  return;
               }
            }

            if (var0 == 7) {
               WorldEntity.method10671(classKK.field3668, classKK.field3669, classKK.field3746, (byte)-16);
            } else if (var0 == 8) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3671, classKK.field3672, classKK.field3673, (byte)-8);
            } else if (9 == var0) {
               WorldEntity.method10671(classKK.field3674, classKK.field3702, classKK.field3676, (byte)-103);
            } else if (10 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3833, classKK.field3678, classKK.field3679, (byte)-41);
            } else if (11 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3622, classKK.field3681, classKK.field3682, (byte)-16);
            } else if (12 == var0) {
               if (var1 <= 0) {
                  return;
               }

               WorldEntity.method10671(classKK.field3683, classKK.field3684, classKK.field3736, (byte)-89);
               classAC.method321(34, 2039091207);
            } else if (13 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3659, classKK.field3913, classKK.field3688, (byte)-121);
            } else if (14 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3689, classKK.field3690, classKK.field3729, (byte)-49);
            } else if (var0 == 16) {
               if (var1 <= 0) {
                  return;
               }

               WorldEntity.method10671(classKK.field3692, classKK.field3693, classKK.field3694, (byte)-113);
               classAC.method321(33, 2001585971);
            } else if (var0 == 17) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3806, classKK.field3696, classKK.field3903, (byte)-113);
            } else if (var0 == 18) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               classAC.method321(14, 2006625909);
               classBF.field387 = 1431935615;
            } else if (19 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3701, classKK.field3612, classKK.field3703, (byte)-121);
            } else if (var0 == 20) {
               if (var1 <= 0) {
                  return;
               }

               WorldEntity.method10671(classKK.field3625, classKK.field3705, classKK.field3657, (byte)-6);
            } else if (22 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3727, classKK.field3912, classKK.field3808, (byte)-115);
            } else if (var0 == 23) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3710, classKK.field3691, classKK.field3712, (byte)-4);
            } else if (24 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3713, classKK.field3714, classKK.field3715, (byte)-20);
            } else if (25 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3716, classKK.field3717, classKK.field3718, (byte)-81);
            } else if (var0 == 26) {
               WorldEntity.method10671(classKK.field3719, classKK.field3720, classKK.field3721, (byte)-75);
            } else if (var0 == 27) {
               WorldEntity.method10671(classKK.field3722, classKK.field3723, classKK.field3724, (byte)-50);
            } else if (31 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3731, classKK.field3789, classKK.field3846, (byte)-38);
            } else if (var0 == 32) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               classAC.method321(14, 2012149136);
               classBF.field387 = -1431096066;
            } else if (37 == var0) {
               if (var1 <= 0) {
                  return;
               }

               WorldEntity.method10671(classKK.field3737, classKK.field3738, classKK.field3826, (byte)-20);
            } else if (var0 == 38) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3740, classKK.field3741, classKK.field3742, (byte)-56);
            } else if (var0 == 74) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3871, classKK.field3874, classKK.field3744, (byte)-4);
            } else if (55 == var0) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               classAC.method321(8, 2063350143);
            } else if (var0 == 56) {
               if (var1 <= 0) {
                  return;
               }

               WorldEntity.method10671(classKK.field3750, classKK.field3751, classKK.field3752, (byte)-9);
            } else if (var0 == 57) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671(classKK.field3753, classKK.field3754, classKK.field3755, (byte)-1);
            } else if (var0 == 61) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               WorldEntity.method10671("", "Please enter your date of birth (DD/MM/YYYY)", "", (byte)-63);
               classAC.method321(7, 2133821696);
            } else if (var0 == 62) {
               WorldEntity.method10671(classKK.field3756, classKK.field3759, classKK.field3758, (byte)-85);
            } else if (63 == var0) {
               WorldEntity.method10671(classKK.field3831, classKK.field3709, classKK.field3762, (byte)-38);
            } else {
               if (var0 != 65) {
                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }

                  if (67 != var0) {
                     if (var0 == 71) {
                        if (var1 <= 0) {
                           throw new IllegalStateException();
                        }

                        classAC.method321(7, 2031839193);
                        WorldEntity.method10671(
                           "There was a problem updating your DOB.",
                           "Please try again later. If the problem ",
                           "persists, please contact Jagex Support.",
                           (byte)-9
                        );
                        return;
                     } else if (73 == var0) {
                        if (var1 <= 0) {
                           throw new IllegalStateException();
                        }

                        classAC.method321(6, 2031150934);
                        WorldEntity.method10671(classKK.field3670, classKK.field3771, classKK.field3861, (byte)-101);
                        return;
                     } else {
                        if (var0 == 72) {
                           if (var1 <= 0) {
                              throw new IllegalStateException();
                           }

                           classAC.method321(32, 2093167256);
                        } else {
                           WorldEntity.method10671(classKK.field3650, classKK.field3732, classKK.field3768, (byte)-35);
                        }

                        return;
                     }
                  }

                  if (var1 <= 0) {
                     throw new IllegalStateException();
                  }
               }

               WorldEntity.method10671(classKK.field3763, classKK.field3764, classKK.field3765, (byte)-99);
            }
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ci.ar(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lci;Lnm;Lnm;)I")
   @ObfuscatedName("sr")
   public static int method1508(classCI var0, classNM var1, classNM var2) {
      if (var0 == null) {
         return var0.method1507(var1, var1);
      } else if (var2.field4684 * 825289476 == 437735073 * var1.field4684) {
         return 0;
      } else {
         if (var0.field744) {
            if (-906797091 * var1.field4684 == 1311484143 * client.field828) {
               return -1;
            }

            if (-658041769 * var2.field4684 == client.field828 * 1311484143) {
               return 1;
            }
         }

         return var1.field4684 * -906797091 < -906797091 * var2.field4684 ? -1 : 1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;ILfx;B)V")
   @ObfuscatedName("cp")
   static void method1503(WorldView var0, int var1, Model var2, byte var3) {
      try {
         short var4 = 512;
         float var5 = client.field983 * 1158148203 / 334.0F;
         float var6 = 1129651895 * client.field879 * 4.0F / var5;
         int var7 = Math.max(512, (int)(1400.0F - var6));
         int var8 = client.field963 * 2106329293 - (int)(classWW.method12700(var1, -518891120) * var7);
         int var9 = -2126074583 * client.field987;
         double var10 = classGP.method5582(var1, 1136716073) / 65536.0;
         int var12 = var9 - (int)(var10 * var7);
         long var13 = classRF.method9998(
            var0.field1708 * -483624883,
            classKY.method6652(var8, (byte)-1),
            classKY.method6652(var12, (byte)4),
            5,
            false,
            -1,
            var0.field1697 * 2140889407,
            (short)4429
         );
         var0.field1687.method4505(-483624883 * var0.field1708, var8, var12, client.field885 * -452033647, 60, var2, var1, var13, false);
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "ci.cp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method1510(Object var1, Object var2) {
      return this.method1504((classNM)var1, (classNM)var2, (byte)-56);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method1513(Object var1) {
      return super.equals(var1);
   }
}
