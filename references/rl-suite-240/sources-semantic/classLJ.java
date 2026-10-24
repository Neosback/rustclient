import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lj")
public class classLJ implements classXZ {
   @ObfuscatedSignature(descriptor = "Llj;")
   @ObfuscatedName("ae")
   public static final classLJ field4174 = new classLJ(2);
   @ObfuscatedSignature(descriptor = "Llj;")
   @ObfuscatedName("af")
   public static final classLJ field4175 = new classLJ(1);
   @ObfuscatedSignature(descriptor = "Llj;")
   @ObfuscatedName("ab")
   public static final classLJ field4173 = new classLJ(3);
   @ObfuscatedSignature(descriptor = "Llj;")
   @ObfuscatedName("az")
   public static final classLJ field4172 = new classLJ(0);
   @ObfuscatedName("ag")
   final int field4176;
   @ObfuscatedSignature(descriptor = "Lfx;")
   @ObfuscatedName("mo")
   static Model field4177;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field4176 * 177564869;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lj.az(" + 41);
      }
   }

   classLJ(int var1) {
      this.field4176 = 53424653 * var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field4176 * 177564869;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Llj;")
   @ObfuscatedName("as")
   public static classLJ[] method7151() {
      return new classLJ[]{field4175, field4172, field4174, field4173};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field4176 * 177564869;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field4176 * 1981206463;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwt;)Lwt;")
   @ObfuscatedName("gh")
   public static classWT method7154(classWT var0) {
      return var0.field6773;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Llj;")
   @ObfuscatedName("ax")
   public static classLJ[] method7152() {
      return new classLJ[]{field4175, field4172, field4174, field4173};
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIB)V")
   @ObfuscatedName("es")
   static final void method7159(WorldView var0, int var1, int var2, int var3, byte var4) {
      try {
         Deque2 var5 = var0.field1699[var1][var2][var3];
         if (null == var5) {
            if (var4 == -1) {
               throw new IllegalStateException();
            } else {
               var0.field1687.method4439(var1, var2, var3);
               client.method1736(var0, var1, var2, var3);
            }
         } else {
            long var6 = -99999999L;
            TileItem var8 = null;

            for (TileItem var9 = (TileItem)var5.method7957(); var9 != null; var9 = (TileItem)var5.method7965()) {
               if (var4 == -1) {
                  throw new IllegalStateException();
               }

               ItemComposition var10 = classOB.method8329(-1370204663 * var9.field1549, (byte)76);
               long var11 = var10.field5041 * 665046345;
               if (var10.field5053 * 1633663665 == 1) {
                  long var10001;
                  if (1413659045 * var9.field1548 < Integer.MAX_VALUE) {
                     if (var4 == -1) {
                        throw new IllegalStateException();
                     }

                     var10001 = var9.field1548 * 1413659045 + 1;
                  } else {
                     var10001 = 1413659045 * var9.field1548;
                  }

                  var11 *= var10001;
               }

               if (var11 > var6) {
                  if (var4 == -1) {
                     client.method1736(var0, var1, var2, var3);
                     return;
                  }

                  var6 = var11;
                  var8 = var9;
               }
            }

            if (null == var8) {
               var0.field1687.method4439(var1, var2, var3);
               client.method1736(var0, var1, var2, var3);
            } else {
               var5.method7949(var8);
               TileItem var18 = null;
               TileItem var19 = null;

               for (TileItem var17 = (TileItem)var5.method7957(); var17 != null; var17 = (TileItem)var5.method7965()) {
                  if (var4 == -1) {
                     throw new IllegalStateException();
                  }

                  if (var8.field1549 * -1370204663 != var17.field1549 * -1370204663) {
                     if (var4 == -1) {
                        throw new IllegalStateException();
                     }

                     if (var18 == null) {
                        if (var4 == -1) {
                           throw new IllegalStateException();
                        }

                        var18 = var17;
                     }

                     if (var17.field1549 * -1370204663 != var18.field1549 * -1370204663) {
                        if (var4 == -1) {
                           throw new IllegalStateException();
                        }

                        if (var19 == null) {
                           if (var4 == -1) {
                              throw new IllegalStateException();
                           }

                           var19 = var17;
                        }
                     }
                  }
               }

               int var12 = var0.field1687.method4491(var1, var2, var3);
               classUZ var13 = classUZ.field6431;
               if (0 == var12) {
                  if (var4 == -1) {
                     throw new IllegalStateException();
                  }

                  var13 = classME.method7605(var0, var2, var3, var1, -435249545);
               }

               var8.method3417(var13, 1364509005);
               if (null != var18) {
                  if (var4 == -1) {
                     throw new IllegalStateException();
                  }

                  var18.method3417(var13, 1364509005);
               }

               if (null != var19) {
                  if (var4 == -1) {
                     throw new IllegalStateException();
                  }

                  var19.method3417(var13, 1364509005);
               }

               long var14 = classRF.method9998(var1, var2, var3, 3, false, 0, 2140889407 * var0.field1697, (short)20381);
               Scene.method4444(
                  var0.field1687,
                  var1,
                  var2,
                  var3,
                  classKV.method6613(var0, classKY.method6656(var2, -673763073), classKY.method6656(var3, 880521706), var1, (byte)122),
                  var8,
                  var14,
                  var18,
                  var19,
                  var12
               );
               client.method1736(var0, var1, var2, var3);
            }
         }
      } catch (RuntimeException var16) {
         throw classEG.method3899(var16, "lj.es(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIZIII)Lym;")
   @ObfuscatedName("ab")
   public static final SpritePixels method7157(int var0, int var1, int var2, int var3, int var4, boolean var5, int var6, int var7, int var8) {
      try {
         label272: {
            if (var6 == 36) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               if (var7 == 32) {
                  if (-1 == var1) {
                     var4 = 0;
                  } else if (2 == var4) {
                     if (var8 != 1683831829) {
                        throw new IllegalStateException();
                     }

                     if (1 != var1) {
                        var4 = 1;
                     }
                  }
                  break label272;
               }

               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }
            }

            var4 = 0;
         }

         long var9 = ((long)var1 << 16) + var0 + ((long)var2 << 38) + ((long)var4 << 40) + ((long)var3 << 42);
         if (!var5) {
            if (var8 != 1683831829) {
               throw new IllegalStateException();
            }

            if (var6 == 36) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               if (var7 == 32) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  SpritePixels var11 = (SpritePixels)NodeCache.method6460(ItemComposition.field5006, var9);
                  if (var11 != null) {
                     if (var8 != 1683831829) {
                        throw new IllegalStateException();
                     }

                     return var11;
                  }
               }
            }
         }

         ItemComposition var12 = classOB.method8329(var0, (byte)1);
         if (var1 > 1) {
            if (var8 != 1683831829) {
               throw new IllegalStateException();
            }

            if (null != var12.field5054) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               int var13 = -1;

               for (int var14 = 0; var14 < 10; var14++) {
                  if (var1 >= var12.field5055[var14]) {
                     if (var8 != 1683831829) {
                        throw new IllegalStateException();
                     }

                     if (0 != var12.field5055[var14]) {
                        if (var8 != 1683831829) {
                           throw new IllegalStateException();
                        }

                        var13 = var12.field5054[var14];
                     }
                  }
               }

               if (-1 != var13) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  var12 = classOB.method8329(var13, (byte)63);
               }
            }
         }

         Model var26 = ItemComposition.method8696(var12, 1, -1367725887);
         if (var26 == null) {
            if (var8 != 1683831829) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var27 = null;
            if (1236138331 * var12.field5049 != -1) {
               var27 = method7157(1357747305 * var12.field5056, 10, 1, 0, 0, true, var6, var7, 1683831829);
               if (var27 == null) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            } else if (-1 != var12.field5050 * 1835686777) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var27 = method7157(var12.field5062 * 641055003, var1, var2, var3, 0, false, var6, var7, 1683831829);
               if (null == var27) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            } else if (-1 != -2023616411 * var12.field5063) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var27 = method7157(1197673963 * var12.field5011, var1, 0, 0, 0, false, var6, var7, 1683831829);
               if (null == var27) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  return null;
               }
            }

            int[] var15 = classYW.field7102;
            int var16 = classYW.field7100;
            int var17 = classYW.field7099;
            float[] var18 = classYW.field7101;
            int[] var19 = new int[4];
            classYW.method13714(var19);
            SpritePixels var25 = new SpritePixels(var6, var7);
            classFH.method4758(var25.field7003, var6, var7, null);
            classYW.method13722();
            classFH.method4760();
            int var20 = var7 >> 1;
            classFH.method4776(var20, var20);
            classFH.field2255.field2210 = false;
            if (var12.field5063 * -2023616411 != -1) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var27.method13555(0, 0);
            }

            int var21 = var12.field5032 * 1694643499;
            if (var6 != 36) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var21 = 32 * var21 / var6;
            }

            if (var5) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var21 = (int)(var21 * 1.5);
            } else if (var2 == 2) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var21 = (int)(1.04 * var21);
            }

            int var22 = var21 * classFH.field2251[var12.field5033 * 320831297] >> 16;
            int var23 = var21 * classFH.field2252[320831297 * var12.field5033] >> 16;
            Model.method5254(var26);
            var26.method5269(
               0,
               -1663465803 * var12.field5034,
               163610477 * var12.field5052,
               320831297 * var12.field5033,
               1898608961 * var12.field5036,
               var22 + -1256242689 * var26.field1741 / 2 + -1593790933 * var12.field5037,
               var23 + var12.field5037 * -1593790933
            );
            if (var12.field5050 * 1835686777 != -1) {
               var27.method13555(0, 0);
            }

            if (var2 >= 1) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var25.method13543(1);
            }

            if (var2 >= 2) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var25.method13543(16777215);
            }

            if (0 != var3) {
               SpritePixels.method13548(var25, var3);
            }

            classFH.method4758(var25.field7003, var6, var7, null);
            if (-1 != 1236138331 * var12.field5049) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               var27.method13555(0, 0);
            }

            label242: {
               if (1 != var4) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  if (2 != var4) {
                     break label242;
                  }

                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  if (var12.field5053 * 1633663665 != 1) {
                     break label242;
                  }
               }

               classNE.field4665.method51(classLH.method7144(var1, 828644827), 0, 9, 16776960, 1);
            }

            if (!var5) {
               if (var8 != 1683831829) {
                  throw new IllegalStateException();
               }

               if (36 == var6) {
                  if (var8 != 1683831829) {
                     throw new IllegalStateException();
                  }

                  if (32 == var7) {
                     ItemComposition.field5006.method6464(var25, var9);
                  }
               }
            }

            classFH.method4758(var15, var16, var17, var18);
            classYW.method13718(var19);
            classFH.method4760();
            classFH.field2255.field2210 = true;
            return var25;
         }
      } catch (RuntimeException var24) {
         throw classEG.method3899(var24, "lj.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;IZLbl;I)V")
   @ObfuscatedName("ax")
   static void method7158(Widget var0, int var1, boolean var2, Script var3, int var4) {
      try {
         if (null == var0.field4338) {
            var0.field4338 = new Widget[1 + var1];
         }

         if (var0.field4338.length <= var1) {
            Widget[] var5 = new Widget[var1 + 1];
            System.arraycopy(var0.field4338, 0, var5, 0, var0.field4338.length);
            var0.field4338 = var5;
         } else {
            Widget var7 = var0.field4338[var1];
            if (var7 != null) {
               if (var4 >= -259325141) {
                  throw new IllegalStateException();
               }

               if (var2) {
                  throw new RuntimeException("");
               }

               classDM.method3447(var7, var0, (byte)-81);
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lj.ax(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrt;Laae;)Lrr;")
   @ObfuscatedName("dv")
   public static Nameable method7155(NameableContainer var0, classAAE var1) {
      if (var0 == null) {
         var0.method10261(var1);
      }

      return var0.method10276(var1, null, (byte)2);
   }

   @ObfuscatedSignature(descriptor = "(Lva;IB)Lyz;")
   @ObfuscatedName("ax")
   public static IndexedSprite method7156(IndexDataBase var0, int var1, byte var2) {
      try {
         if (!classLN.method7170(var0, var1, (byte)0)) {
            return null;
         } else {
            IndexedSprite var4 = new IndexedSprite();
            var4.field7122 = classYL.field6998 * -1073038619;
            var4.field7118 = classYL.field6997 * -1062567517;
            var4.field7120 = classYL.field7000[0];
            var4.field7121 = classYL.field7001[0];
            var4.field7119 = EntityOps.field5369[0];
            var4.field7117 = classGG.field2576[0];
            var4.field7124 = classRB.field5637;
            var4.field7123 = classYL.field7002[0];
            classVZ.method12347((byte)63);
            return var4;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lj.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([IB)[Ljava/lang/Object;")
   @ObfuscatedName("az")
   static Object[] method7153(int[] var0, byte var1) {
      try {
         Object[] var2 = new Object[var0.length];

         for (int var3 = 0; var3 < var0.length; var3++) {
            if (var1 == 1) {
               throw new IllegalStateException();
            }

            switch (FriendContainer.method10175(var0[var3], 1474882567).field7105 * -900901303) {
               case 0:
               case 1:
                  var2[var3] = null;
                  break;
               case 2:
                  var2[var3] = classTM.method10983(var0[var3], -973324355);
                  break;
               case 3:
                  var2[var3] = (long)classTM.method10983(var0[var3], -973324355);
                  break;
               case 4:
                  var2[var3] = "";
            }
         }

         return var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lj.az(" + ')');
      }
   }
}
