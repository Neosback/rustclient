import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vc")
public class classVC {
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field6486 = 50;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("az")
   Widget field6488;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("af")
   Widget field6487;
   @ObfuscatedName("ae")
   int field6485 = 0;

   @ObfuscatedSignature(descriptor = "(Lvc;Llw;I)V")
   @ObfuscatedName("tq")
   public static void method12003(classVC var0, Widget var1, int var2) {
      if (var0 == null) {
         var0.method12007(var1, var2);
      }

      try {
         var0.field6487 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vc.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvc;Llw;B)Z")
   @ObfuscatedName("kj")
   public static boolean method12008(classVC var0, Widget var1, byte var2) {
      if (var0 == null) {
         var0.method12009(var1, var2);
      }

      try {
         if (var1 == var0.field6488) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            if (50 == var0.field6485 * -740803857) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("al")
   public void method12004(Widget var1) {
      this.field6487 = var1;
   }

   classVC() {
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("ag")
   public void method12010(Widget var1, int var2) {
      try {
         this.field6488 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public void method11995() {
      if (this.field6488 != null) {
         if (-740803857 * this.field6485 < 50) {
            this.field6485 += 429501998;
         }
      } else if (303009574 * this.field6485 > 0) {
         this.field6485 -= -1419496433;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)Z")
   @ObfuscatedName("aa")
   public boolean method12001(Widget var1) {
      return this.field6487 == var1;
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Z")
   @ObfuscatedName("af")
   public boolean method12002(Widget var1, byte var2) {
      try {
         return this.field6487 == var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vc.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("ao")
   public void method12005(Widget var1) {
      this.field6487 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public void method11996() {
      if (this.field6488 != null) {
         if (-597373801 * this.field6485 < -1027524225) {
            this.field6485 += -592925861;
         }
      } else if (876333252 * this.field6485 > 0) {
         this.field6485 -= -1419496433;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;)V")
   @ObfuscatedName("aj")
   public void method12006(Widget var1) {
      this.field6487 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lva;B)I")
   @ObfuscatedName("az")
   static int method12011(IndexDataBase var0, IndexDataBase var1, byte var2) {
      try {
         int var3 = 0;
         String[] var4 = classBF.field410;

         for (int var5 = 0; var5 < var4.length; var5++) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            String var6 = var4[var5];
            if (IndexDataBase.method11930(var0, var6, "", -1093961304)) {
               if (var2 <= 2) {
                  throw new IllegalStateException();
               }

               var3++;
            }
         }

         var4 = classBF.field413;

         for (int var10 = 0; var10 < var4.length; var10++) {
            if (var2 <= 2) {
               throw new IllegalStateException();
            }

            String var12 = var4[var10];
            if (IndexDataBase.method11930(var1, var12, "", -144849564)) {
               if (var2 <= 2) {
                  throw new IllegalStateException();
               }

               var3++;
            }
         }

         var4 = classBF.field386;

         for (int var11 = 0; var11 < var4.length; var11++) {
            String var13 = var4[var11];
            if (IndexDataBase.method11918(var1, var13, 218437170) != -1) {
               if (var2 <= 2) {
                  throw new IllegalStateException();
               }

               if (IndexDataBase.method11930(var1, var13, "", 825023448)) {
                  if (var2 <= 2) {
                     throw new IllegalStateException();
                  }

                  var3++;
               }
            }
         }

         return var3;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "vc.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvc;)V")
   @ObfuscatedName("mi")
   public static void method11997(classVC var0) {
      if (var0 == null) {
         var0.method12000();
      } else {
         if (var0.field6488 != null) {
            if (-1786359775 * var0.field6485 < 411698692) {
               var0.field6485 += -1419496433;
            }
         } else if (-740803857 * var0.field6485 > 0) {
            var0.field6485 -= 1648860763;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("az")
   public void method11998(byte var1) {
      try {
         if (this.field6487 != null) {
            if (var1 >= 4) {
               throw new IllegalStateException();
            }

            if (-740803857 * this.field6485 < 50) {
               if (var1 >= 4) {
                  throw new IllegalStateException();
               }

               this.field6485 += -1419496433;
            }
         } else if (-740803857 * this.field6485 > 0) {
            if (var1 >= 4) {
               return;
            }

            this.field6485 -= -1419496433;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vc.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;B)Z")
   @ObfuscatedName("ab")
   public boolean method12009(Widget var1, byte var2) {
      try {
         if (var1 == this.field6488) {
            if (var2 <= 1) {
               throw new IllegalStateException();
            }

            if (50 == this.field6485 * -740803857) {
               if (var2 <= 1) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvc;B)V")
   @ObfuscatedName("pe")
   public static void method11999(classVC var0, byte var1) {
      if (var0 == null) {
         var0.method11998(var1);
      } else {
         try {
            if (var0.field6488 != null) {
               if (var1 >= 4) {
                  throw new IllegalStateException();
               }

               if (-740803857 * var0.field6485 < 50) {
                  if (var1 >= 4) {
                     throw new IllegalStateException();
                  }

                  var0.field6485 += -1419496433;
               }
            } else if (-740803857 * var0.field6485 > 0) {
               if (var1 >= 4) {
                  return;
               }

               var0.field6485 -= -1419496433;
            }
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "vc.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)V")
   @ObfuscatedName("ae")
   public void method12007(Widget var1, int var2) {
      try {
         this.field6487 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vc.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   public void method12000() {
      if (this.field6488 != null) {
         if (-1786359775 * this.field6485 < 411698692) {
            this.field6485 += -1419496433;
         }
      } else if (-740803857 * this.field6485 > 0) {
         this.field6485 -= 1648860763;
      }
   }
}
