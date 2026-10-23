import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pf")
public class classPF extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5234 = 11;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("az")
   static NodeCache field5233 = new NodeCache(64);
   @ObfuscatedName("mk")
   static int field5235;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("as")
   public static void method8881(IndexDataBase var0) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ae")
   public static void method8882(IndexDataBase var0) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method8883(IndexDataBase var0) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lym;Lol;)I")
   @ObfuscatedName("nf")
   public static int method8892(WorldMapRegion var0, SpritePixels var1, classOL var2) {
      if (var0 == null) {
         var0.method5677();
      }

      switch (-1205275063 * var2.field4915) {
         case 0:
            return -var1.field7007 / 2;
         case 1:
            return 0;
         default:
            return -var1.field7007;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method8884(IndexDataBase var0) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public static void method8885() {
      field5233.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method8886() {
      field5233.method6468();
   }

   @ObfuscatedSignature(descriptor = "(Llw;IIZLvv;Lqn;S)V")
   @ObfuscatedName("ag")
   static void method8890(Widget var0, int var1, int var2, boolean var3, classVV var4, classQN var5, short var6) {
      try {
         int var7 = var0.field4247 * 1115597881;
         int var8 = var0.field4257 * 293773697;
         if (var0.field4249 * -1966367247 == 0) {
            var0.field4247 = var0.field4253 * 2070538269;
         } else if (1 == -1966367247 * var0.field4249) {
            var0.field4247 = (var1 - var0.field4253 * 1523460725) * -1261469175;
         } else if (-1966367247 * var0.field4249 == 2) {
            if (var6 <= 255) {
               throw new IllegalStateException();
            }

            var0.field4247 = (var0.field4253 * 1523460725 * var1 >> 14) * -1261469175;
         }

         if (0 == var0.field4250 * 372143361) {
            var0.field4257 = 2102819615 * var0.field4333;
         } else if (372143361 * var0.field4250 == 1) {
            if (var6 <= 255) {
               throw new IllegalStateException();
            }

            var0.field4257 = -1358979455 * (var2 - 549489055 * var0.field4333);
         } else if (2 == 372143361 * var0.field4250) {
            if (var6 <= 255) {
               throw new IllegalStateException();
            }

            var0.field4257 = -1358979455 * (var0.field4333 * 549489055 * var2 >> 14);
         }

         if (4 == var0.field4249 * -1966367247) {
            if (var6 <= 255) {
               return;
            }

            var0.field4247 = var0.field4272 * 400524509 * var0.field4257 * 293773697 / (var0.field4259 * 2089641037) * -1261469175;
         }

         if (var0.field4250 * 372143361 == 4) {
            if (var6 <= 255) {
               return;
            }

            var0.field4257 = -1358979455 * (var0.field4247 * 1115597881 * 2089641037 * var0.field4259 / (400524509 * var0.field4272));
         }

         if (1337 == -90794217 * var0.field4332) {
            if (var6 <= 255) {
               return;
            }

            var4.field6618 = var0;
         }

         if (12 == -1510882375 * var0.field4373) {
            classLH.method6865(var0.method7334(-1720891802), 1115597881 * var0.field4247, var0.field4257 * 293773697, -1834164345);
         }

         if (var3) {
            if (var6 <= 255) {
               return;
            }

            if (var0.field4244 != null) {
               if (var6 <= 255) {
                  throw new IllegalStateException();
               }

               if (var7 == 1115597881 * var0.field4247) {
                  if (var6 <= 255) {
                     throw new IllegalStateException();
                  }

                  if (var8 == var0.field4257 * 293773697) {
                     return;
                  }

                  if (var6 <= 255) {
                     throw new IllegalStateException();
                  }
               }

               ScriptEvent var9 = classUQ.method11527(var0, 1435437416).method14195(var0.field4244, (byte)2).method14226((byte)73);
               var5.method9709(var9, 1697206599);
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "pf.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public static void method8887() {
      field5233.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnm;)Ljava/lang/String;")
   @ObfuscatedName("yt")
   public static String method8891(classNM var0) {
      return var0.field4686;
   }

   @ObfuscatedSignature(descriptor = "(IIII[Ljava/lang/Object;B)V")
   @ObfuscatedName("fl")
   static void method8893(int var0, int var1, int var2, int var3, Object[] var4, byte var5) {
      try {
         boolean var6 = false;
         Widget var7 = classWK.field6689.method7422(var0, var1, (byte)-106);
         if (!var6) {
            if (var5 == 4) {
               throw new IllegalStateException();
            }

            if (var7 == null) {
               if (var5 == 4) {
                  return;
               }

               return;
            }
         }

         if (!var6) {
            int var9 = classVV.method12246(classWK.field6689, var7, (byte)0);
            boolean var10000;
            if ((var9 >> 23 & 1) != 0) {
               if (var5 == 4) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var8 = var10000;
            if (!var8) {
               if (var5 == 4) {
                  throw new IllegalStateException();
               }

               return;
            }
         }

         classJL var18 = classEF.method3863(classJS.IF_SCRIPT_TRIGGER, client.field796.field1533, (byte)74);
         var18.field3341.method13031(0, -1542382992);
         int var19 = 702114061 * var18.field3341.field6953;
         var18.field3341.method13287(var3, -1529732366);
         var18.field3341.method13031(var2, -1542382992);
         var18.field3341.method13287(var0, -701215652);
         classXY.method13239(var18.field3341, var1, (byte)22);
         Object[] var10 = var4;

         for (int var11 = 0; var11 < var10.length; var11++) {
            if (var5 == 4) {
               throw new IllegalStateException();
            }

            Object var12 = var10[var11];
            if (var12 instanceof Integer) {
               if (var5 == 4) {
                  throw new IllegalStateException();
               }

               var18.field3341.method13093((Integer)var12, (byte)-49);
            } else if (var12 instanceof String) {
               if (var5 == 4) {
                  throw new IllegalStateException();
               }

               var18.field3341.method13054((String)var12, (short)25054);
            } else if (var12 instanceof classTE) {
               if (var5 == 4) {
                  throw new IllegalStateException();
               }

               classTE var13 = (classTE)var12;
               int var14 = var13.method10797(231441325);
               var18.field3341.method13084(var14, -169584588);
               if (classYY.field7109 == var13.field6126) {
                  if (var5 == 4) {
                     throw new IllegalStateException();
                  }

                  int[] var20 = var13.method10791(-399979208);

                  for (int var21 = 0; var21 < var14; var21++) {
                     if (var5 == 4) {
                        throw new IllegalStateException();
                     }

                     var18.field3341.method13093(var20[var21], (byte)-48);
                  }
               } else {
                  Object[] var15 = var13.method10796(1859895154);

                  for (int var16 = 0; var16 < var14; var16++) {
                     if (var5 == 4) {
                        throw new IllegalStateException();
                     }

                     var18.field3341.method13054((String)var15[var16], (short)21661);
                  }
               }
            } else if (null == var12) {
               if (var5 == 4) {
                  throw new IllegalStateException();
               }

               classXY.method13029(var18.field3341, 0, (byte)-21);
            }
         }

         var18.field3341.method13074(702114061 * var18.field3341.field6953 - var19, 2119019222);
         client.field796.method3374(var18, -1301097035);
      } catch (RuntimeException var17) {
         throw classEG.method3899(var17, "pf.fl(" + ')');
      }
   }

   classPF() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("af")
   public static void method8889(byte var0) {
      try {
         WorldMapRegion.field2661.method6200(5);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "pf.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIIIB)Z")
   @ObfuscatedName("af")
   public static boolean method8888(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, byte var8) {
      try {
         if (!classAK.method394(var0, var1, var2, var3, var4, var5, (byte)2)) {
            if (var8 != -1) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            return classAK.method394(var0, var1, var4, var5, var6, var7, (byte)-118);
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "pf.af(" + ')');
      }
   }
}
