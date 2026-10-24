import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("if")
public class classIF {
   @ObfuscatedName("ap")
   long field2963;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2960 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2959 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2962 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field2958 = 12;
   @ObfuscatedName("au")
   public int field2956 = 1051425213;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("ai")
   classNV field2957 = new classNV();
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2961 = 5;

   @ObfuscatedSignature(descriptor = "(Lif;Lxy;I)V")
   @ObfuscatedName("tp")
   public static void method6238(classIF var0, classXY var1, int var2) {
      if (var0 == null) {
         var0.method6243(var1, var2);
      } else {
         try {
            var0.field2963 = classXY.method13115(var1, 592163377);
            var0.field2956 = classXY.method13113(var1, (byte)1) * -1051425213;

            for (int var3 = var1.method13095(-346779531); 0 != var3; var3 = var1.method13095(-346779531)) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               Object var4;
               if (3 == var3) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIG(var0);
               } else if (var3 == 1) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classHC(var0);
               } else if (var3 == 13) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIY(var0);
               } else if (4 == var3) {
                  var4 = new classHS(var0);
               } else if (6 == var3) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIC(var0);
               } else if (var3 == 5) {
                  var4 = new classIJ(var0);
               } else if (var3 == 2) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIW(var0);
               } else if (var3 == 7) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIN(var0);
               } else if (14 == var3) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIZ(var0);
               } else if (var3 == 8) {
                  if (var2 != 1871139444) {
                     return;
                  }

                  var4 = new classIK(var0);
               } else if (9 == var3) {
                  var4 = new classIL(var0);
               } else if (var3 == 10) {
                  if (var2 != 1871139444) {
                     return;
                  }

                  var4 = new classIV(var0);
               } else if (var3 == 11) {
                  var4 = new classIO(var0);
               } else if (var3 == 12) {
                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIU(var0);
               } else {
                  if (var3 != 15) {
                     throw new RuntimeException("");
                  }

                  if (var2 != 1871139444) {
                     throw new IllegalStateException();
                  }

                  var4 = new classIP(var0);
               }

               ((classHB)var4).vmethod498(var1, -1411701108);
               var0.field2957.method8196((Node)var4);
            }
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "if.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   void method6239(classXY var1) {
      this.field2963 = classXY.method13115(var1, 271624158);
      this.field2956 = classXY.method13113(var1, (byte)1) * -1051425213;

      for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
         Object var3;
         if (3 == var2) {
            var3 = new classIG(this);
         } else if (var2 == 1) {
            var3 = new classHC(this);
         } else if (var2 == 13) {
            var3 = new classIY(this);
         } else if (4 == var2) {
            var3 = new classHS(this);
         } else if (6 == var2) {
            var3 = new classIC(this);
         } else if (var2 == 5) {
            var3 = new classIJ(this);
         } else if (var2 == 2) {
            var3 = new classIW(this);
         } else if (var2 == 7) {
            var3 = new classIN(this);
         } else if (14 == var2) {
            var3 = new classIZ(this);
         } else if (var2 == 8) {
            var3 = new classIK(this);
         } else if (9 == var2) {
            var3 = new classIL(this);
         } else if (var2 == 10) {
            var3 = new classIV(this);
         } else if (var2 == 11) {
            var3 = new classIO(this);
         } else if (var2 == 12) {
            var3 = new classIU(this);
         } else {
            if (var2 != 15) {
               throw new RuntimeException("");
            }

            var3 = new classIP(this);
         }

         ((classHB)var3).vmethod498(var1, -1540742998);
         this.field2957.method8196((Node)var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ag")
   void method6240(classXY var1) {
      this.field2963 = classXY.method13115(var1, 1324457612);
      this.field2956 = classXY.method13113(var1, (byte)1) * -1051425213;

      for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
         Object var3;
         if (3 == var2) {
            var3 = new classIG(this);
         } else if (var2 == 1) {
            var3 = new classHC(this);
         } else if (var2 == 13) {
            var3 = new classIY(this);
         } else if (4 == var2) {
            var3 = new classHS(this);
         } else if (6 == var2) {
            var3 = new classIC(this);
         } else if (var2 == 5) {
            var3 = new classIJ(this);
         } else if (var2 == 2) {
            var3 = new classIW(this);
         } else if (var2 == 7) {
            var3 = new classIN(this);
         } else if (14 == var2) {
            var3 = new classIZ(this);
         } else if (var2 == 8) {
            var3 = new classIK(this);
         } else if (9 == var2) {
            var3 = new classIL(this);
         } else if (var2 == 10) {
            var3 = new classIV(this);
         } else if (var2 == 11) {
            var3 = new classIO(this);
         } else if (var2 == 12) {
            var3 = new classIU(this);
         } else {
            if (var2 != 15) {
               throw new RuntimeException("");
            }

            var3 = new classIP(this);
         }

         ((classHB)var3).vmethod498(var1, -1491853646);
         this.field2957.method8196((Node)var3);
      }
   }

   public classIF(classXY var1) {
      method6238(this, var1, 1871139444);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lif;Lxy;)V")
   @ObfuscatedName("dc")
   public static void method6241(classIF var0, classXY var1) {
      if (var0 == null) {
         var0.method6242(var1);
      } else {
         var0.field2963 = classXY.method13115(var1, 2044571467);
         var0.field2956 = classXY.method13113(var1, (byte)1) * -1051425213;

         for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
            Object var3;
            if (3 == var2) {
               var3 = new classIG(var0);
            } else if (var2 == 1) {
               var3 = new classHC(var0);
            } else if (var2 == 13) {
               var3 = new classIY(var0);
            } else if (4 == var2) {
               var3 = new classHS(var0);
            } else if (6 == var2) {
               var3 = new classIC(var0);
            } else if (var2 == 5) {
               var3 = new classIJ(var0);
            } else if (var2 == 2) {
               var3 = new classIW(var0);
            } else if (var2 == 7) {
               var3 = new classIN(var0);
            } else if (14 == var2) {
               var3 = new classIZ(var0);
            } else if (var2 == 8) {
               var3 = new classIK(var0);
            } else if (9 == var2) {
               var3 = new classIL(var0);
            } else if (var2 == 10) {
               var3 = new classIV(var0);
            } else if (var2 == 11) {
               var3 = new classIO(var0);
            } else if (var2 == 12) {
               var3 = new classIU(var0);
            } else {
               if (var2 != 15) {
                  throw new RuntimeException("");
               }

               var3 = new classIP(var0);
            }

            ((classHB)var3).vmethod498(var1, -2133361549);
            var0.field2957.method8196((Node)var3);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgn;)[I")
   @ObfuscatedName("lg")
   public static int[] method6244(classGN var0) {
      if (var0 == null) {
         var0.method5553();
      }

      return var0.field2623;
   }

   @ObfuscatedSignature(descriptor = "(Liq;I)V")
   @ObfuscatedName("af")
   public void method6245(ClanSettings var1, int var2) {
      try {
         if (var1.field3030 * -7981298375333928091L == this.field2963) {
            if (var1.field3011 * 573608791 == -1560054165 * this.field2956) {
               for (classHB var3 = (classHB)this.field2957.method8208(); null != var3; var3 = (classHB)this.field2957.method8216()) {
                  if (var2 == -616858415) {
                     throw new IllegalStateException();
                  }

                  var3.vmethod499(var1, (byte)123);
               }

               var1.field3011 += -1766455193;
               return;
            }

            if (var2 == -616858415) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException("");
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "if.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("as")
   void method6242(classXY var1) {
      this.field2963 = classXY.method13115(var1, 608776554);
      this.field2956 = classXY.method13113(var1, (byte)1) * -1051425213;

      for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
         Object var3;
         if (3 == var2) {
            var3 = new classIG(this);
         } else if (var2 == 1) {
            var3 = new classHC(this);
         } else if (var2 == 13) {
            var3 = new classIY(this);
         } else if (4 == var2) {
            var3 = new classHS(this);
         } else if (6 == var2) {
            var3 = new classIC(this);
         } else if (var2 == 5) {
            var3 = new classIJ(this);
         } else if (var2 == 2) {
            var3 = new classIW(this);
         } else if (var2 == 7) {
            var3 = new classIN(this);
         } else if (14 == var2) {
            var3 = new classIZ(this);
         } else if (var2 == 8) {
            var3 = new classIK(this);
         } else if (9 == var2) {
            var3 = new classIL(this);
         } else if (var2 == 10) {
            var3 = new classIV(this);
         } else if (var2 == 11) {
            var3 = new classIO(this);
         } else if (var2 == 12) {
            var3 = new classIU(this);
         } else {
            if (var2 != 15) {
               throw new RuntimeException("");
            }

            var3 = new classIP(this);
         }

         ((classHB)var3).vmethod498(var1, -1556884441);
         this.field2957.method8196((Node)var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   public void method6246(ClanSettings var1) {
      if (var1.field3030 * -7981298375333928091L == this.field2963 && var1.field3011 * 573608791 == -1560054165 * this.field2956) {
         for (classHB var2 = (classHB)this.field2957.method8208(); null != var2; var2 = (classHB)this.field2957.method8216()) {
            var2.vmethod499(var1, (byte)52);
         }

         var1.field3011 += -1766455193;
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "([FI)V")
   @ObfuscatedName("ab")
   static void method6248(float[] var0, int var1) {
      try {
         if (classST.field6037 + var0[0] < 1.3333334F) {
            if (var1 != -850399243) {
               return;
            }

            float var2 = var0[0] - 2.0F;
            float var3 = var0[0] - 1.0F;
            float var4 = (float)Math.sqrt(var2 * var2 - var3 * 4.0F * var3);
            float var5 = (-var2 + var4) * 0.5F;
            if (classST.field6037 + var0[1] > var5) {
               if (var1 != -850399243) {
                  return;
               }

               var0[1] = var5 - classST.field6037;
            } else {
               var5 = 0.5F * (-var2 - var4);
               if (var0[1] < classST.field6037 + var5) {
                  var0[1] = var5 + classST.field6037;
               }
            }
         } else {
            var0[0] = 1.3333334F - classST.field6037;
            var0[1] = 0.33333334F - classST.field6037;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "if.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIB)V")
   @ObfuscatedName("aj")
   static void method6247(int var0, int var1, int var2, int var3, byte var4) {
      try {
         classNP.field4701 = var0 * 173745189;
         classNP.field4700 = -1367216911 * var1;
         classNP.field4694 = -269856411 * var2;
         classNP.field4699 = -1506539367 * var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "if.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   void method6243(classXY var1, int var2) {
      try {
         this.field2963 = classXY.method13115(var1, 592163377);
         this.field2956 = classXY.method13113(var1, (byte)1) * -1051425213;

         for (int var3 = var1.method13107(-346779531); 0 != var3; var3 = var1.method13225(-346779531)) {
            if (var2 != 1871139444) {
               throw new IllegalStateException();
            }

            Object var4;
            if (3 == var3) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIG(this);
            } else if (var3 == 1) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classHC(this);
            } else if (var3 == 13) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIY(this);
            } else if (4 == var3) {
               var4 = new classHS(this);
            } else if (6 == var3) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIC(this);
            } else if (var3 == 5) {
               var4 = new classIJ(this);
            } else if (var3 == 2) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIW(this);
            } else if (var3 == 7) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIN(this);
            } else if (14 == var3) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIZ(this);
            } else if (var3 == 8) {
               if (var2 != 1871139444) {
                  return;
               }

               var4 = new classIK(this);
            } else if (9 == var3) {
               var4 = new classIL(this);
            } else if (var3 == 10) {
               if (var2 != 1871139444) {
                  return;
               }

               var4 = new classIV(this);
            } else if (var3 == 11) {
               var4 = new classIO(this);
            } else if (var3 == 12) {
               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIU(this);
            } else {
               if (var3 != 15) {
                  throw new RuntimeException("");
               }

               if (var2 != 1871139444) {
                  throw new IllegalStateException();
               }

               var4 = new classIP(this);
            }

            ((classHB)var4).vmethod498(var1, -1411701108);
            this.field2957.method8203((Node)var4);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "if.az(" + ')');
      }
   }
}
