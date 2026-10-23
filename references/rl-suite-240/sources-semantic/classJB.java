import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jb")
public class classJB implements classXZ {
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("cl")
   static IndexedSprite field3118;
   @ObfuscatedSignature(descriptor = "Ljb;")
   @ObfuscatedName("af")
   public static final classJB field3115 = new classJB(1, 1, true, false);
   @ObfuscatedSignature(descriptor = "Ljb;")
   @ObfuscatedName("ae")
   public static final classJB field3113 = new classJB(2, 2, true, true);
   @ObfuscatedName("ag")
   final int field3110;
   @ObfuscatedName("ab")
   final int field3109;
   @ObfuscatedSignature(descriptor = "Ljb;")
   @ObfuscatedName("az")
   public static final classJB field3112 = new classJB(0, 0, false, false);
   @ObfuscatedName("ax")
   public final boolean field3111;
   @ObfuscatedSignature(descriptor = "Ljb;")
   @ObfuscatedName("ac")
   public static final classJB field3114 = field3113;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field3116 = 10;
   @ObfuscatedName("as")
   public final boolean field3108;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field3117 = 9;

   classJB(int var1, int var2, boolean var3, boolean var4) {
      this.field3110 = -847524539 * var1;
      this.field3109 = 1160914555 * var2;
      this.field3108 = var3;
      this.field3111 = var4;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return 1894631603 * this.field3109;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return 1894631603 * this.field3109;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return 1894631603 * this.field3109;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;III)V")
   @ObfuscatedName("qr")
   public static void method6385(RenderOverview var0, int var1, int var2, int var3) {
      if (var0.field6742 != null) {
         int[] var4 = WorldMapData.method5869(var0.field6742, var1, var2, var3, (short)10483);
         if (null != var4) {
            var0.method12568(var4[0], var4[1], 628621539);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return 1894631603 * this.field3109;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "jb.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIII)V")
   @ObfuscatedName("dv")
   static void method6387(WorldView var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      try {
         Deque2 var7 = var0.field1699[var1][var2][var3];
         if (var7 != null) {
            if (var6 <= -1090888932) {
               throw new IllegalStateException();
            }

            for (TileItem var8 = (TileItem)var7.method7957(); var8 != null; var8 = (TileItem)var7.method7965()) {
               if (var6 <= -1090888932) {
                  return;
               }

               if (var8.field1549 * -1370204663 == var4) {
                  if (var6 <= -1090888932) {
                     throw new IllegalStateException();
                  }

                  if (var5 == 1413659045 * var8.field1548) {
                     var8.vmethod398();
                     break;
                  }
               }
            }

            if (var7.method7957() == null) {
               if (var6 <= -1090888932) {
                  throw new IllegalStateException();
               }

               var0.field1699[var1][var2][var3] = null;
            }

            classLJ.method7159(var0, var1, var2, var3, (byte)-111);
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "jb.dv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ac")
   static void method6386(int var0, int var1) {
      try {
         if (var0 == -44590225 * client.field829) {
            if (var1 != -1192966987) {
               throw new IllegalStateException();
            }
         } else {
            if (30 == -44590225 * client.field829) {
               client.field909.method4605((byte)118);
            }

            if (-44590225 * client.field829 == 0) {
               if (var1 != -1192966987) {
                  throw new IllegalStateException();
               }

               classOE.field4841.method11126(-2025028862);
            }

            label167: {
               if (var0 != 20) {
                  if (var1 != -1192966987) {
                     return;
                  }

                  if (var0 != 40) {
                     if (var1 != -1192966987) {
                        return;
                     }

                     if (45 != var0) {
                        if (50 != var0) {
                           break label167;
                        }

                        if (var1 != -1192966987) {
                           return;
                        }
                     }
                  }
               }

               classRL.method10063(classCX.field1308, 249948929);
               client.field906 = 0;
               client.field816 = 0;
               classLE.method6811(client.field918, var0, 1630105049);
               if (var0 != 20) {
                  if (var1 != -1192966987) {
                     throw new IllegalStateException();
                  }

                  classXQ.method12934(false, 402128428);
               }
            }

            if (20 != var0) {
               if (var1 != -1192966987) {
                  throw new IllegalStateException();
               }

               if (var0 != 40) {
                  if (var1 != -1192966987) {
                     return;
                  }

                  if (classDS.field1621 != null) {
                     if (var1 != -1192966987) {
                        throw new IllegalStateException();
                     }

                     classDS.field1621.vmethod604(1271658352);
                     classDS.field1621 = null;
                  }
               }
            }

            if (25 == -44590225 * client.field829) {
               client.field914 = 0;
               client.field910 = 0;
               client.field911 = 196357067;
               client.field817 = 0;
               client.field913 = 882774979;
            }

            label175: {
               if (5 != var0) {
                  if (var1 != -1192966987) {
                     throw new IllegalStateException();
                  }

                  if (var0 != 10) {
                     if (var0 == 20) {
                        if (var1 != -1192966987) {
                           throw new IllegalStateException();
                        }

                        int var4 = client.field829 * -44590225 == 11 ? 4 : 0;
                        classJF.method6403(ItemContainer.field462, classHC.field2765, classOV.field5167, false, var4, 1600397897);
                     } else if (var0 == 11) {
                        if (var1 != -1192966987) {
                           throw new IllegalStateException();
                        }

                        classJF.method6403(ItemContainer.field462, classHC.field2765, classOV.field5167, false, 4, 1600397897);
                     } else if (50 == var0) {
                        if (var1 != -1192966987) {
                           return;
                        }

                        WorldEntity.method10671("", "Updating date of birth...", "", (byte)-44);
                        classJF.method6403(ItemContainer.field462, classHC.field2765, classOV.field5167, false, 7, 1600397897);
                     } else if (var0 != 0) {
                        if (var1 != -1192966987) {
                           throw new IllegalStateException();
                        }

                        if (!classPA.field5207) {
                           if (var1 != -1192966987) {
                              throw new IllegalStateException();
                           }
                        } else {
                           field3118 = null;
                           classKA.field3512 = null;
                           classBM.field514 = null;
                           classNI.field4671 = null;
                           classFR.field2389 = null;
                           AABB.field2370 = null;
                           MessageNode.field733 = null;
                           classCK.field778 = null;
                           classEK.field1777 = null;
                           classCR.field1228 = null;
                           classCF.field720 = null;
                           classPM.field5347 = null;
                           classPD.field5225 = null;
                           SceneTilePaint.field2292 = null;
                           classML.field4529.method2575(1347397579);
                           classBK.method1122(0, 100, (byte)115);
                           classWU.method12698((byte)100).method11959(true, 116730183);
                           classPA.field5207 = false;
                           client.method2373(-1);
                        }
                     }
                     break label175;
                  }

                  if (var1 != -1192966987) {
                     throw new IllegalStateException();
                  }
               }

               byte var10000;
               if (IndexDataBase.method11935(-2053271753)) {
                  if (var1 != -1192966987) {
                     throw new IllegalStateException();
                  }

                  var10000 = 0;
               } else {
                  var10000 = 12;
               }

               byte var2 = var10000;
               classJF.method6403(ItemContainer.field462, classHC.field2765, classOV.field5167, true, var2, 1600397897);
            }

            client.field829 = var0 * -214948977;
            client.method2276(-1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "jb.ac(" + ')');
      }
   }
}
