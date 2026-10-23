import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qx")
class classQX implements Comparator {
   @ObfuscatedSignature(descriptor = "Lus;")
   @ObfuscatedName("pr")
   static classUS field5621;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ax")
   public int method9878(Object var1, Object var2) {
      return method9874(this, (Entry)var1, (Entry)var2, 1183408284);
   }

   @Override
   public int compare(Object var1, Object var2) {
      try {
         return method9874(this, (Entry)var1, (Entry)var2, 1848410095);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qx.compare(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qx.equals(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqx;Ljava/lang/Object;)Z")
   @ObfuscatedName("wk")
   public static boolean method9879(classQX var0, Object var1) {
      if (var0 == null) {
         var0.compare(var1, var1);
      }

      return var0.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqx;Ljava/lang/Object;)Z")
   @ObfuscatedName("at")
   public static boolean method9880(classQX var0, Object var1) {
      if (var0 == null) {
         var0.equals(var1);
      }

      return var0.equals(var1);
   }

   classQX(classQY var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lqx;Ljava/util/Map$Entry;Ljava/util/Map$Entry;I)I")
   @ObfuscatedName("sg")
   public static int method9874(classQX var0, Entry var1, Entry var2, int var3) {
      if (var0 == null) {
         var0.method9877(var1, var1, var3);
      }

      try {
         return ((Float)var2.getValue()).compareTo((Float)var1.getValue());
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qx.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Map$Entry;Ljava/util/Map$Entry;)I")
   @ObfuscatedName("af")
   int method9875(Entry var1, Entry var2) {
      return ((Float)var2.getValue()).compareTo((Float)var1.getValue());
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Map$Entry;Ljava/util/Map$Entry;)I")
   @ObfuscatedName("ae")
   int method9876(Entry var1, Entry var2) {
      return ((Float)var2.getValue()).compareTo((Float)var1.getValue());
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("fy")
   static final void method9881(int var0, int var1, int var2) {
      try {
         int[] var3 = new int[classCL.method1597(client.field815, (byte)0)];
         float[] var4 = new float[classCL.method1597(client.field815, (byte)0)];
         int var5 = 0;

         for (Iterator var6 = client.field815.iterator(); var6.hasNext(); var5++) {
            if (var2 == -1439422728) {
               return;
            }

            WorldView var7 = (WorldView)var6.next();
            var3[var5] = var7.field1697 * 2140889407;
            var4[var5] = var7.field1687.field2040;
         }

         classKD.method6501(var4, var3, 2092092092);
         HashMap var31 = new HashMap();

         for (int var29 = var3.length - 1; var29 >= 0; var29--) {
            if (var2 == -1439422728) {
               return;
            }

            if (!(var4[var29] > 0.0F)) {
               if (var2 == -1439422728) {
                  return;
               }

               if (0 != var3[var29]) {
                  continue;
               }

               if (var2 == -1439422728) {
                  return;
               }
            }

            classTN var32 = client.field815.method1609(var3[var29], 206128317);
            if (!var31.containsKey(var32)) {
               if (var2 == -1439422728) {
                  throw new IllegalStateException();
               }

               var31.put(var32, var3[var29]);
            }
         }

         for (int var30 = 0; var30 < var3.length; var30++) {
            if (var2 == -1439422728) {
               throw new IllegalStateException();
            }

            int var33 = var3[var30];
            classTN var8 = client.field815.method1609(var33, 206128317);
            Integer var9 = (Integer)var31.get(var8);
            if (var9 != null) {
               if (var2 == -1439422728) {
                  throw new IllegalStateException();
               }

               if (!var9.equals(var33)) {
                  if (var2 == -1439422728) {
                     return;
                  }
               } else if (classTN.field6170 == var8) {
                  if (var2 == -1439422728) {
                     throw new IllegalStateException();
                  }

                  Friend.method10203(classKK.field3777, "", 23, 0, var0, var1, 0, false, var33, -324749371);
               } else if (var8 == classTN.field6171) {
                  if (var2 == -1439422728) {
                     throw new IllegalStateException();
                  }

                  int var10 = 27064125 * client.field991 / 2;
                  int var11 = client.field983 * 1158148203 / 2;
                  int var12 = client.field879 * 1129651895;
                  Scene var14 = classIS.field3051.field1687;
                  int var15 = -452033647 * client.field885;
                  int var16 = classKI.field3596 * -325062789;
                  int var17 = 1615527037 * classNL.field4682;
                  int var18 = 1343311673 * classAI.field167;
                  float var19 = 1.0E-5F;
                  classTW.method11268((byte)-51);
                  classEV.method4118(var14.field2085, var14.field2088, var14.field2092, var14.field2090, var10, var11, var12, (byte)-114);
                  classUZ var20 = classIK.method6261(0.0F, 1.0F, 0.0F, (byte)76);
                  float var21 = classLV.method7202(var20, classFO.field2343, (byte)-59);
                  classUZ var13;
                  if (Math.abs(var21) < 1.0E-5F) {
                     if (var2 == -1439422728) {
                        return;
                     }

                     var20.method11797(2124130229);
                     var13 = null;
                  } else {
                     classUZ var22 = classIK.method6261(var16, var17, var18, (byte)44);
                     classUZ var23 = classIK.method6261(var22.field6425, var22.field6423 - var15, var22.field6424, (byte)58);
                     float var24 = -classLV.method7202(var20, var23, (byte)-111) / var21;
                     var20.method11797(1688078599);
                     if (var24 < 0.0F) {
                        if (var2 == -1439422728) {
                           throw new IllegalStateException();
                        }

                        var23.method11797(1873798979);
                        var22.method11797(1883209680);
                        var13 = null;
                     } else {
                        classUZ var25 = Script.method1139(classFO.field2343, var24, -1280452024);
                        classUZ var27 = WorldMapData.method5868(var22, (byte)61);
                        var27.method11822(var25, -457597272);
                        var25.method11797(1865024924);
                        var23.method11797(2146296988);
                        var22.method11797(2057987562);
                        var13 = var27;
                     }
                  }

                  if (var13 != null) {
                     if (var2 == -1439422728) {
                        throw new IllegalStateException();
                     }

                     var20 = classIK.method6261(2106329293 * client.field963, -452033647 * client.field885, client.field987 * -2126074583, (byte)-62);
                     classUZ var35 = classWN.method12676(var13, var20, (byte)1);
                     classUZ var36 = classIZ.method6384(classUZ.field6429, var35, -1635324929);
                     float var37 = (float)Math.atan2(
                        classLV.method7202(var36, classUZ.field6430, (byte)-53), classLV.method7202(classUZ.field6429, var35, (byte)-52)
                     );
                     int var38 = (int)(var37 / (Math.PI * 2) * 2048.0) & 2047;
                     int var39 = var38 + 64;
                     int var26 = (var39 & 2047) / 128;
                     Friend.method10203(classKK.field3779, "", 60, var26, 0, 0, 0, false, var33, -324749371);
                     var20.method11797(2025799119);
                     var35.method11797(2056660213);
                     var36.method11797(1690364234);
                     var13.method11797(1846231364);
                  }
               }
            }
         }
      } catch (RuntimeException var28) {
         throw classEG.method3899(var28, "qx.fy(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Map$Entry;Ljava/util/Map$Entry;I)I")
   @ObfuscatedName("az")
   int method9877(Entry var1, Entry var2, int var3) {
      try {
         return ((Float)var2.getValue()).compareTo((Float)var1.getValue());
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "qx.az(" + 41);
      }
   }
}
