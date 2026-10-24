import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ig")
public class classIG extends classHB {
   @ObfuscatedName("az")
   long field2965;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2966 = 16;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field2967 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   static final int field2968 = 114;
   @ObfuscatedName("af")
   String field2964;

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6304(-2076329511100161443L * this.field2965, this.field2964, (byte)69);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         if (var1.method13095(-346779531) != 255) {
            if (var2 >= -989877912) {
               throw new IllegalStateException();
            }

            var1.field6953 -= -1095856699;
            this.field2965 = classXY.method13115(var1, -810938496) * -148600610369633803L;
         }

         this.field2964 = var1.method13125((byte)3);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ig.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6304(-2076329511100161443L * this.field2965, this.field2964, (byte)44);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ig.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      if (var1.method13095(-346779531) != 255) {
         var1.field6953 -= -1095856699;
         this.field2965 = classXY.method13115(var1, -1534093694) * -148600610369633803L;
      }

      this.field2964 = var1.method13125((byte)-64);
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6304(-2076329511100161443L * this.field2965, this.field2964, (byte)45);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgc;III)V")
   @ObfuscatedName("uh")
   public static void method6250(CollisionData var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method5370(var1, var1, var1);
      }

      var0.field2548[var1][var2] = var0.field2548[var1][var2] & ~var3;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6304(-2076329511100161443L * this.field2965, this.field2964, (byte)-80);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      if (var1.method13095(-346779531) != -1087359743) {
         var1.field6953 -= -1095856699;
         this.field2965 = classXY.method13115(var1, 1083767445) * -148600610369633803L;
      }

      this.field2964 = var1.method13125((byte)-70);
   }

   classIG(classIF var1) {
      this.this$0 = var1;
      this.field2965 = 148600610369633803L;
      this.field2964 = null;
   }

   @ObfuscatedSignature(descriptor = "(ILbl;ZI)I")
   @ObfuscatedName("aj")
   static int method6251(int var0, Script var1, boolean var2, int var3) {
      try {
         Widget var4;
         if (var0 >= 2000) {
            if (var3 != -1306305670) {
               throw new IllegalStateException();
            }

            var0 -= 1000;
            int var5 = classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371];
            var4 = classWK.field6689.method7421(var5, (byte)109);
         } else {
            Widget var10000;
            if (var2) {
               if (var3 != -1306305670) {
                  throw new IllegalStateException();
               }

               var10000 = classSZ.field6108;
            } else {
               var10000 = TileItem.field1556;
            }

            var4 = var10000;
         }

         if (1000 == var0) {
            if (var3 != -1306305670) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 480164916;
               var4.field4246 = classBB.field323[DynamicObject.field1676 * -324749371] * -1076753689;
               var4.field4283 = 1989424741 * classBB.field323[1 + DynamicObject.field1676 * -324749371];
               var4.field4262 = 1632281619 * classBB.field323[DynamicObject.field1676 * -324749371 + 2];
               var4.field4248 = classBB.field323[3 + DynamicObject.field1676 * -324749371] * 454876301;
               classCD.method1427(var4, classSA.field5895 * 773246731, IntProjection.field1936 * 8379747, classWK.field6689, client.field824, -2055866783);
               if (-1510882375 * var4.field4373 == 0) {
                  if (var3 != -1306305670) {
                     throw new IllegalStateException();
                  }

                  Widget[] var12;
                  if (-1 == var4.field4258 * 63131743) {
                     if (var3 != -1306305670) {
                        throw new IllegalStateException();
                     }

                     var12 = classWK.field6689.field4438[var4.field4327 * -1163656551 >> 16];
                  } else {
                     var12 = classWK.field6689.method7421(var4.field4340 * -1502647899, (byte)120).field4338;
                  }

                  Widget[] var9 = var12;
                  classFC.method4609(var9, var4, false, classWK.field6689, client.field824, 1225806495);
               }

               return 1;
            }
         } else if (1001 == var0) {
            if (var3 != -1306305670) {
               throw new IllegalStateException();
            } else {
               DynamicObject.field1676 -= 480164916;
               var4.field4253 = classBB.field323[DynamicObject.field1676 * -324749371] * 1034606045;
               var4.field4333 = -259501473 * classBB.field323[1 + DynamicObject.field1676 * -324749371];
               var4.field4249 = classBB.field323[2 + DynamicObject.field1676 * -324749371] * -1430539503;
               var4.field4250 = classBB.field323[DynamicObject.field1676 * -324749371 + 3] * -565540095;
               classCD.method1427(var4, 773246731 * classSA.field5895, IntProjection.field1936 * 8379747, classWK.field6689, client.field824, -1983695323);
               if (0 == var4.field4373 * -1510882375) {
                  if (var3 != -1306305670) {
                     throw new IllegalStateException();
                  }

                  Widget[] var11;
                  if (-1 == 63131743 * var4.field4258) {
                     if (var3 != -1306305670) {
                        throw new IllegalStateException();
                     }

                     var11 = classWK.field6689.field4438[var4.field4327 * -1163656551 >> 16];
                  } else {
                     var11 = classWK.field6689.method7421(-1502647899 * var4.field4340, (byte)82).field4338;
                  }

                  Widget[] var8 = var11;
                  classFC.method4609(var8, var4, false, classWK.field6689, client.field824, -1742273654);
               }

               return 1;
            }
         } else if (var0 == 1003) {
            boolean var10;
            if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
               if (var3 != -1306305670) {
                  throw new IllegalStateException();
               }

               var10 = true;
            } else {
               var10 = false;
            }

            boolean var7 = var10;
            if (var4.field4287 != var7) {
               if (var3 != -1306305670) {
                  throw new IllegalStateException();
               }

               var4.field4287 = var7;
            }

            return 1;
         } else if (var0 == 1005) {
            if (var3 != -1306305670) {
               throw new IllegalStateException();
            } else {
               boolean var13;
               if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
                  if (var3 != -1306305670) {
                     throw new IllegalStateException();
                  }

                  var13 = true;
               } else {
                  var13 = false;
               }

               var4.field4401 = var13;
               return 1;
            }
         } else if (1006 == var0) {
            boolean var10001;
            if (classBB.field323[(DynamicObject.field1676 -= 120041229) * -324749371] == 1) {
               if (var3 != -1306305670) {
                  throw new IllegalStateException();
               }

               var10001 = true;
            } else {
               var10001 = false;
            }

            var4.field4326 = var10001;
            return 1;
         } else {
            return 2;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ig.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(JIIZLrz;I)Ljava/lang/String;")
   @ObfuscatedName("ab")
   static String method6249(long var0, int var2, int var3, boolean var4, classRZ var5, int var6) {
      try {
         boolean var7 = var0 < 0L;
         long var8 = Math.abs(var0);
         if (Long.toString(var8).length() <= var2) {
            if (var6 <= 722349416) {
               throw new IllegalStateException();
            } else {
               return Long.toString(var0);
            }
         } else {
            long var10 = -1L;
            String var12 = "";
            if (var8 >= classRE.field5645) {
               if (var6 <= 722349416) {
                  throw new IllegalStateException();
               }

               long var13 = var8 / classRE.field5644;
               if (Long.toString(var13).length() <= var2) {
                  if (var6 <= 722349416) {
                     throw new IllegalStateException();
                  }

                  var10 = classRE.field5644;
                  var12 = "K";
               } else if (var8 >= classRE.field5649) {
                  long var15 = var8 / classRE.field5647;
                  if (Long.toString(var15).length() <= var2) {
                     if (var6 <= 722349416) {
                        throw new IllegalStateException();
                     }

                     var10 = classRE.field5647;
                     var12 = "M";
                  } else if (var8 >= classRE.field5646) {
                     if (var6 <= 722349416) {
                        throw new IllegalStateException();
                     }

                     long var17 = var8 / classRE.field5648;
                     if (Long.toString(var17).length() <= var2) {
                        if (var6 <= 722349416) {
                           throw new IllegalStateException();
                        }

                        var10 = classRE.field5648;
                        var12 = "B";
                     }
                  }
               }
            }

            if (-1L == var10) {
               return Long.toString(var0);
            } else {
               long var20 = var8;

               for (int var22 = 0; var22 < var3; var22++) {
                  if (var6 <= 722349416) {
                     throw new IllegalStateException();
                  }

                  var20 *= 10L;
               }

               var20 /= var10;
               String var23 = classXW.method13010(var20, var3, false, var4, var5, (byte)10);
               StringBuilder var10000 = new StringBuilder();
               String var10001;
               if (var7) {
                  if (var6 <= 722349416) {
                     throw new IllegalStateException();
                  }

                  var10001 = "-";
               } else {
                  var10001 = "";
               }

               return var10000.append(var10001).append(var23).append(var12).toString();
            }
         }
      } catch (RuntimeException var19) {
         throw classEG.method3899(var19, "ig.ab(" + ')');
      }
   }
}
