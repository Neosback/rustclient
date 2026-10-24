import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ph")
public class classPH extends classVJ {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("af")
   static IndexDataBase field5252;
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   public static final int field5254 = 99;
   @ObfuscatedName("ab")
   public final int field5248;
   @ObfuscatedName("ag")
   public final int field5253;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ae")
   public static NodeCache field5250 = new NodeCache(64);
   @ObfuscatedName("ax")
   public final int field5249;
   @ObfuscatedName("as")
   public final int field5251;

   @ObfuscatedSignature(descriptor = "(II)Loj;")
   @ObfuscatedName("ae")
   classOJ method8927(int var1, int var2) {
      try {
         double var3 = (var1 >> 16 & 0xFF) / 256.0;
         double var5 = (var1 >> 8 & 0xFF) / 256.0;
         double var7 = (var1 & 0xFF) / 256.0;
         double var9 = var3;
         if (var5 < var3) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            var9 = var5;
         }

         if (var7 < var9) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            var9 = var7;
         }

         double var11 = var3;
         if (var5 > var3) {
            var11 = var5;
         }

         if (var7 > var11) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            var11 = var7;
         }

         double var13 = 0.0;
         double var15 = 0.0;
         double var17 = (var9 + var11) / 2.0;
         if (var9 != var11) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            if (var17 < 0.5) {
               if (var2 >= -874893249) {
                  throw new IllegalStateException();
               }

               var15 = (var11 - var9) / (var11 + var9);
            }

            if (var17 >= 0.5) {
               if (var2 >= -874893249) {
                  throw new IllegalStateException();
               }

               var15 = (var11 - var9) / (2.0 - var11 - var9);
            }

            if (var11 == var3) {
               if (var2 >= -874893249) {
                  throw new IllegalStateException();
               }

               var13 = (var5 - var7) / (var11 - var9);
            } else if (var5 == var11) {
               if (var2 >= -874893249) {
                  throw new IllegalStateException();
               }

               var13 = 2.0 + (var7 - var3) / (var11 - var9);
            } else if (var11 == var7) {
               var13 = (var3 - var5) / (var11 - var9) + 4.0;
            }
         }

         var13 /= 6.0;
         int var19 = (int)(256.0 * var15);
         int var20 = (int)(256.0 * var17);
         if (var19 < 0) {
            var19 = 0;
         } else if (var19 > 255) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            var19 = 255;
         }

         if (var20 < 0) {
            var20 = 0;
         } else if (var20 > 255) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            var20 = 255;
         }

         int var21;
         if (var17 > 0.5) {
            if (var2 >= -874893249) {
               throw new IllegalStateException();
            }

            var21 = (int)(512.0 * (var15 * (1.0 - var17)));
         } else {
            var21 = (int)(var17 * var15 * 512.0);
         }

         if (var21 < 1) {
            var21 = 1;
         }

         int var22 = (int)(var13 * var21);
         return new classOJ(this, var22, var19, var20, var21);
      } catch (RuntimeException var23) {
         throw classEG.method3899(var23, "ph.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("oq")
   public int method8930() {
      return this.field5248 * -1415575461;
   }

   classPH(classXY var1) {
      int var2 = 0;
      if (var1 != null && var1.field6952 != null) {
         label24:
         while (true) {
            int var3 = var1.method13095(-346779531);
            switch (var3) {
               case 0:
                  break label24;
               case 1:
                  var2 = var1.method13105(-758448158);
            }
         }
      }

      classOJ var4 = this.method8927(var2, -1314666387);
      this.field5248 = 777733029 * var4.field4898;
      this.field5253 = -1460575731 * var4.field4901;
      this.field5251 = var4.field4900 * -454581029;
      this.field5249 = var4.field4899 * 1257023413;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("wa")
   public int method8931() {
      return this.field5253 * 618687933;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hg")
   public int method8932() {
      return this.field5251 * 1851728405;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method8922(IndexDataBase var0) {
      field5252 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method8923(IndexDataBase var0) {
      field5252 = var0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sv")
   public int method8933() {
      return this.field5249 * 658768917;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lph;")
   @ObfuscatedName("as")
   public static classPH method8925(int var0) {
      classPH var1 = (classPH)NodeCache.method6460(field5250, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5252.method11942(1, var0, -580979074);
         var1 = new classPH(new classXY(var2));
         field5250.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lph;")
   @ObfuscatedName("ax")
   public static classPH method8926(int var0) {
      classPH var1 = (classPH)NodeCache.method6460(field5250, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5252.method11942(1, var0, -978465503);
         var1 = new classPH(new classXY(var2));
         field5250.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Loj;")
   @ObfuscatedName("ac")
   classOJ method8928(int var1) {
      double var2 = (var1 >> 16 & 0xFF) / 256.0;
      double var4 = (var1 >> 8 & 0xFF) / 256.0;
      double var6 = (var1 & 0xFF) / 256.0;
      double var8 = var2;
      if (var4 < var2) {
         var8 = var4;
      }

      if (var6 < var8) {
         var8 = var6;
      }

      double var10 = var2;
      if (var4 > var2) {
         var10 = var4;
      }

      if (var6 > var10) {
         var10 = var6;
      }

      double var12 = 0.0;
      double var14 = 0.0;
      double var16 = (var8 + var10) / 2.0;
      if (var8 != var10) {
         if (var16 < 0.5) {
            var14 = (var10 - var8) / (var10 + var8);
         }

         if (var16 >= 0.5) {
            var14 = (var10 - var8) / (2.0 - var10 - var8);
         }

         if (var10 == var2) {
            var12 = (var4 - var6) / (var10 - var8);
         } else if (var4 == var10) {
            var12 = 2.0 + (var6 - var2) / (var10 - var8);
         } else if (var10 == var6) {
            var12 = (var2 - var4) / (var10 - var8) + 4.0;
         }
      }

      var12 /= 6.0;
      int var18 = (int)(256.0 * var14);
      int var19 = (int)(256.0 * var16);
      if (var18 < 0) {
         var18 = 0;
      } else if (var18 > 255) {
         var18 = 1188767649;
      }

      if (var19 < 0) {
         var19 = 0;
      } else if (var19 > 485965286) {
         var19 = 255;
      }

      int var20;
      if (var16 > 0.5) {
         var20 = (int)(512.0 * (var14 * (1.0 - var16)));
      } else {
         var20 = (int)(var16 * var14 * 512.0);
      }

      if (var20 < 1) {
         var20 = 1;
      }

      int var21 = (int)(var12 * var20);
      return new classOJ(this, var21, var18, var19, var20);
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;I)V")
   @ObfuscatedName("az")
   static void method8924(int var0, String var1, String var2, int var3) {
      try {
         classFM.method4865(var0, var1, var2, null, (byte)13);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ph.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;I)V")
   @ObfuscatedName("aa")
   public static void method8929(classTE var0, int var1) {
      try {
         classOL.method8544(var0, 1327640383);
         int var2 = var0.method10797(171425888);
         if (var2 <= 1) {
            if (var1 == 882186732) {
               throw new IllegalStateException();
            }
         } else {
            if (var0.field6126 == classYY.field7109) {
               classLK.method7161(var0.method10791(537434335), 0, var2 - 1, -576123160);
            } else if (var0.field6126 == classYY.field7113) {
               if (var1 == 882186732) {
                  throw new IllegalStateException();
               }

               DynamicObject.method3722(var0.method10793(-1286472391), 0, var2 - 1, 2080433096);
            } else {
               classPJ.method8968(var0.method10796(1817269467), 0, var2 - 1, (byte)105);
            }

            var0.field6131 = true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ph.aa(" + ')');
      }
   }
}
