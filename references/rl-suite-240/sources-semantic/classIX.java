import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ix")
public class classIX {
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("ac")
   classNV field3081;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field3083 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field3082 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field3084 = 5;
   @ObfuscatedName("as")
   long field3086;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field3085 = 1;
   @ObfuscatedName("ax")
   public long field3080 = 8226759114265297917L;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ag")
   void method6368(classXY var1) {
      this.field3086 = classXY.method13115(var1, 1464582720);
      this.field3080 = classXY.method13115(var1, 1774728430) * -8226759114265297917L;

      for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
         Object var3;
         if (1 == var2) {
            var3 = new classIR(this);
         } else if (4 == var2) {
            var3 = new classIT(this);
         } else if (3 == var2) {
            var3 = new classIM(this);
         } else if (var2 == 2) {
            var3 = new classIA(this);
         } else {
            if (5 != var2) {
               throw new RuntimeException("");
            }

            var3 = new classIB(this);
         }

         ((classIS)var3).vmethod507(var1, 386832497);
         this.field3081.method8196((Node)var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;S)V")
   @ObfuscatedName("az")
   void method6369(classXY var1, short var2) {
      try {
         this.field3086 = classXY.method13115(var1, -1384984816);
         this.field3080 = classXY.method13115(var1, -532145666) * -8226759114265297917L;

         for (int var3 = var1.method13095(-346779531); 0 != var3; var3 = var1.method13095(-346779531)) {
            if (var2 >= 255) {
               throw new IllegalStateException();
            }

            Object var4;
            if (1 == var3) {
               if (var2 >= 255) {
                  throw new IllegalStateException();
               }

               var4 = new classIR(this);
            } else if (4 == var3) {
               if (var2 >= 255) {
                  throw new IllegalStateException();
               }

               var4 = new classIT(this);
            } else if (3 == var3) {
               if (var2 >= 255) {
                  return;
               }

               var4 = new classIM(this);
            } else if (var3 == 2) {
               if (var2 >= 255) {
                  throw new IllegalStateException();
               }

               var4 = new classIA(this);
            } else {
               if (5 != var3) {
                  throw new RuntimeException("");
               }

               if (var2 >= 255) {
                  throw new IllegalStateException();
               }

               var4 = new classIB(this);
            }

            ((classIS)var4).vmethod507(var1, -719199796);
            this.field3081.method8196((Node)var4);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ix.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   void method6370(classXY var1) {
      this.field3086 = classXY.method13115(var1, 520075859);
      this.field3080 = classXY.method13115(var1, 289186193) * -8226759114265297917L;

      for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
         Object var3;
         if (1 == var2) {
            var3 = new classIR(this);
         } else if (4 == var2) {
            var3 = new classIT(this);
         } else if (3 == var2) {
            var3 = new classIM(this);
         } else if (var2 == 2) {
            var3 = new classIA(this);
         } else {
            if (5 != var2) {
               throw new RuntimeException("");
            }

            var3 = new classIB(this);
         }

         ((classIS)var3).vmethod507(var1, -1602566673);
         this.field3081.method8196((Node)var3);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   void method6371(classXY var1) {
      this.field3086 = classXY.method13115(var1, -632078937);
      this.field3080 = classXY.method13115(var1, 2120238084) * -8226759114265297917L;

      for (int var2 = var1.method13095(-346779531); 0 != var2; var2 = var1.method13095(-346779531)) {
         Object var3;
         if (1 == var2) {
            var3 = new classIR(this);
         } else if (4 == var2) {
            var3 = new classIT(this);
         } else if (3 == var2) {
            var3 = new classIM(this);
         } else if (var2 == 2) {
            var3 = new classIA(this);
         } else {
            if (5 != var2) {
               throw new RuntimeException("");
            }

            var3 = new classIB(this);
         }

         ((classIS)var3).vmethod507(var1, -1723543446);
         this.field3081.method8196((Node)var3);
      }
   }

   public classIX(classXY var1) {
      this.field3081 = new classNV();
      this.method6369(var1, (short)-29660);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   public void method6373(ClanChannel var1) {
      if (var1.field6550 == this.field3086 && -8128530336532066645L * this.field3080 == -8293005437275873549L * var1.field2951) {
         for (classIS var2 = (classIS)this.field3081.method8208(); var2 != null; var2 = (classIS)this.field3081.method8216()) {
            var2.vmethod509(var1, -1454512603);
         }

         var1.field2951 += 2301448292752648763L;
      } else {
         throw new RuntimeException("");
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;I)Lhw;")
   @ObfuscatedName("nl")
   public static classHW method6378(WorldMapRegion var0, int var1) {
      if (var0 == null) {
         var0.getMapIcons();
      }

      classPS var2 = SceneTilePaint.method4817(var1, (byte)-103);
      return var0.method5756(var2, 1926760881);
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   public void method6374(ClanChannel var1) {
      if (var1.field6550 == this.field3086 && -8128530336532066645L * this.field3080 == -8293005437275873549L * var1.field2951) {
         for (classIS var2 = (classIS)this.field3081.method8208(); var2 != null; var2 = (classIS)this.field3081.method8216()) {
            var2.vmethod509(var1, -1408873289);
         }

         var1.field2951 += 2301448292752648763L;
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedSignature(descriptor = "(Lsp;III)V")
   @ObfuscatedName("af")
   static void method6377(WorldEntity var0, int var1, int var2, int var3) {
      try {
         if (var1 == 65535) {
            if (var3 > 360123687) {
               byte var7 = -1;
               classQR.method9770(var0.field6015, var7, -2136593812);
               classQR.method9791(var0.field6015, -180040373);
               var0.field6016 = var2 * -1033857357;
            }
         } else {
            Animation var4 = var0.field6015.method9775((byte)30);
            if (var4 != null) {
               if (var3 <= 360123687) {
                  throw new IllegalStateException();
               }

               if (var1 == var0.method10644((byte)-65)) {
                  if (var3 <= 360123687) {
                     return;
                  }

                  int var5 = var4.field5120 * 1697446663;
                  if (1 == var5) {
                     if (var3 <= 360123687) {
                        throw new IllegalStateException();
                     }

                     classQR.method9791(var0.field6015, -126942821);
                     var0.field6016 = var2 * -1033857357;
                  }

                  if (2 == var5) {
                     if (var3 <= 360123687) {
                        throw new IllegalStateException();
                     }

                     var0.field6015.method9793(228977571);
                  }

                  return;
               }

               if (classQA.method9477(var1, 1926809740).field5119 * -1968168817 < var4.field5119 * -1968168817) {
                  if (var3 <= 360123687) {
                     throw new IllegalStateException();
                  }

                  return;
               }
            }

            classQR.method9770(var0.field6015, var1, -2145678588);
            classQR.method9791(var0.field6015, -2021238633);
            var0.field6016 = var2 * -1033857357;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ix.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lix;Lie;B)V")
   @ObfuscatedName("aw")
   public static void method6375(classIX var0, ClanChannel var1, byte var2) {
      if (var0 == null) {
         var0.method6376(var1, var2);
      } else {
         try {
            if (var1.field6550 == var0.field3086) {
               if (var2 >= 0) {
                  throw new IllegalStateException();
               }

               if (-8128530336532066645L * var0.field3080 == -8293005437275873549L * var1.field2951) {
                  for (classIS var3 = (classIS)var0.field3081.method8208(); var3 != null; var3 = (classIS)var0.field3081.method8216()) {
                     if (var2 >= 0) {
                        return;
                     }

                     var3.vmethod509(var1, 1059836227);
                  }

                  var1.field2951 += 2301448292752648763L;
                  return;
               }

               if (var2 >= 0) {
                  throw new IllegalStateException();
               }
            }

            throw new RuntimeException("");
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "ix.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   public static int method6372(int var0, int var1) {
      try {
         return var0 & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ix.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;B)V")
   @ObfuscatedName("af")
   public void method6376(ClanChannel var1, byte var2) {
      try {
         if (var1.field6550 == this.field3086) {
            if (var2 >= 0) {
               throw new IllegalStateException();
            }

            if (-8128530336532066645L * this.field3080 == -8293005437275873549L * var1.field2951) {
               for (classIS var3 = (classIS)this.field3081.method8263(); var3 != null; var3 = (classIS)this.field3081.method8204()) {
                  if (var2 >= 0) {
                     return;
                  }

                  var3.vmethod509(var1, 1059836227);
               }

               var1.field2951 += 2301448292752648763L;
               return;
            }

            if (var2 >= 0) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException("");
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ix.af(" + ')');
      }
   }
}
