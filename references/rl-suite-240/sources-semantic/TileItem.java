import net.runelite.api.events.ItemQuantityChanged;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dl")
public final class TileItem extends Renderable implements net.runelite.api.TileItem {
   @ObfuscatedName("ae")
   int field1548;
   @ObfuscatedName("sd")
   public int field1544;
   @ObfuscatedName("vm")
   public int field1554;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1553 = 31;
   @ObfuscatedName("af")
   int field1549;
   @ObfuscatedName("tn")
   public int field1543 = -1;
   @ObfuscatedName("ab")
   int field1547;
   @ObfuscatedName("ag")
   int field1550;
   @ObfuscatedName("as")
   int field1552;
   @ObfuscatedName("ax")
   boolean field1558;
   @ObfuscatedSignature(descriptor = "Ldl;")
   @ObfuscatedName("yn")
   public static TileItem field1555;
   @ObfuscatedSignature(descriptor = "Lpi;")
   @ObfuscatedName("aa")
   ColorTextureOverride field1557;
   @ObfuscatedName("ac")
   int field1546;
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("ao")
   classUZ field1545;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field1551 = 11;
   @ObfuscatedSignature(descriptor = "Llw;")
   @ObfuscatedName("av")
   static Widget field1556;

   @ObfuscatedSignature(descriptor = "(Lpi;I)V")
   @ObfuscatedName("ac")
   void method3413(ColorTextureOverride var1, int var2) {
      try {
         this.field1557 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dl.ac(" + ')');
      }
   }

   public int getOwnership() {
      return this.field1552 * -1876787891;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldl;I)Z")
   @ObfuscatedName("wg")
   public static boolean method3406(TileItem var0, int var1) {
      if (var0 == null) {
         var0.method3422(var1);
      }

      return var1 >= 0 && var1 <= 4 ? 0 != (var0.field1546 * -1737503501 & 1 << var1) : true;
   }

   @ObfuscatedSignature(descriptor = "(Ldl;II)Z")
   @ObfuscatedName("nv")
   public static boolean method3407(TileItem var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method3408(var1, var1);
      } else {
         try {
            if (var1 >= 0) {
               if (var2 <= 1282433543) {
                  throw new IllegalStateException();
               }

               if (var1 <= 4) {
                  if (var2 <= 1282433543) {
                     throw new IllegalStateException();
                  }

                  boolean var10000;
                  if (0 != (var0.field1546 * -1737503501 & 1 << var1)) {
                     if (var2 <= 1282433543) {
                        throw new IllegalStateException();
                     }

                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  return var10000;
               }
            }

            return true;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "dl.af(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lfx;")
   @ObfuscatedName("ae")
   @Override
   protected final Model vmethod104(int var1) {
      try {
         ItemComposition var2 = classOB.method8329(-1370204663 * this.field1549, (byte)36);
         Model var10000;
         if (null == this.field1557) {
            if (var1 <= -1767431455) {
               throw new IllegalStateException();
            }

            var10000 = ItemComposition.method8696(var2, this.field1548 * 1413659045, 329498713);
         } else {
            var10000 = var2.method8698(this.field1557, -904091968);
         }

         Model var3 = var10000;
         if (null == var3) {
            if (var1 <= -1767431455) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            Model var4 = null;
            if (!classUZ.method11813(classUZ.field6431, this.field1545, (byte)-121)) {
               classUL var5 = classDD.method2993(this.field1545, 2120827972);
               classUL.method11450(var5, 1392053111);
               var4 = var3.method5106(false);
               var4.method5248(var5);
               classUL.method11416(var5, -1740924855);
            }

            if (var4 == null) {
               if (var1 <= -1767431455) {
                  throw new IllegalStateException();
               }

               var10000 = var3;
            } else {
               var10000 = var4;
            }

            return var10000;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "dl.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ab")
   @Override
   protected boolean vmethod95(int var1) {
      try {
         ItemComposition var2 = classOB.method8329(this.field1549 * -1370204663, (byte)-30);
         Model var3 = null == this.field1557
            ? ItemComposition.method8696(var2, this.field1548 * 1413659045, -2005049212)
            : var2.method8698(this.field1557, -500626418);
         if (var3 != null) {
            if (var1 == -702405562) {
               throw new IllegalStateException();
            }

            if (null != var3.field2442) {
               if (var1 == -702405562) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "dl.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luz;)Lul;")
   @ObfuscatedName("aq")
   static classUL method3409(classUZ var0) {
      classUZ var1 = classIZ.method6384(classUZ.field6431, var0, -1853298155);
      var1.method11817(-2143745352);
      float var2 = classLV.method7202(classUZ.field6431, var0, (byte)-55);
      float var3 = (float)Math.acos(var2);
      classUL var4 = classAV.method715((byte)0);
      classUL.method11431(var4, var1, var3, (byte)-42);
      var1.method11797(1828111105);
      return var4;
   }

   public int getQuantity() {
      return this.field1548 * 1413659045;
   }

   @ObfuscatedSignature(descriptor = "()Lrl10;")
   @ObfuscatedName("ep")
   public rl10 method3426() {
      int var1 = this.field1543;
      int var2 = this.field1544;
      WorldView var3 = WorldView.method3792(this.field1554);
      if (var1 != -1 && var2 != -1 && var3 != null) {
         rl10[][][] var4 = var3.method3798().method4458();
         return var4[var3.getPlane()][var1][var2];
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method3415(int var1) {
      try {
         this.field1557 = null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dl.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ldl;S)Lpi;")
   @ObfuscatedName("pf")
   public static ColorTextureOverride method3410(TileItem var0, short var1) {
      if (var0 == null) {
         var0.method3412(var1);
      }

      try {
         return var0.field1557;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dl.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gi")
   public void method3425(int var1) {
      this.field1544 = var1;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("az")
   void method3403(int var1, int var2) {
      try {
         this.field1546 = -713675717 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dl.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jx")
   public int method3424() {
      return this.field1544;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   @Override
   protected boolean vmethod96() {
      ItemComposition var1 = classOB.method8329(this.field1549 * -1370204663, (byte)-55);
      Model var2 = null == this.field1557
         ? ItemComposition.method8696(var1, this.field1548 * 1413659045, -70068740)
         : var1.method8698(this.field1557, -517475351);
      return var2 != null && null != var2.field2442;
   }

   public int getId() {
      return this.field1549 * -1370204663;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ap")
   @Override
   protected final Model vmethod93() {
      ItemComposition var1 = classOB.method8329(310600877 * this.field1549, (byte)-54);
      Model var2 = null == this.field1557
         ? ItemComposition.method8696(var1, this.field1548 * 1413659045, -1856510028)
         : var1.method8698(this.field1557, -1812681976);
      if (null == var2) {
         return null;
      } else {
         Model var3 = null;
         if (!classUZ.method11813(classUZ.field6431, this.field1545, (byte)20)) {
            classUL var4 = classDD.method2993(this.field1545, 2029001904);
            classUL.method11450(var4, -458924421);
            var3 = var2.method5106(false);
            var3.method5248(var4);
            classUL.method11416(var4, -98941053);
         }

         return var3 == null ? var2 : var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lpi;)V")
   @ObfuscatedName("bc")
   void method3414(ColorTextureOverride var1) {
      this.field1557 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ai")
   @Override
   protected boolean vmethod94() {
      ItemComposition var1 = classOB.method8329(this.field1549 * -1370204663, (byte)8);
      Model var2 = null == this.field1557
         ? ItemComposition.method8696(var1, this.field1548 * 1413659045, -998107844)
         : var1.method8698(this.field1557, -1833782936);
      return var2 != null && null != var2.field2442;
   }

   @ObfuscatedSignature(descriptor = "(Luz;)V")
   @ObfuscatedName("bp")
   void method3416(classUZ var1) {
      this.field1545 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method3404(int var1) {
      this.field1546 = 577109677 * var1;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aj")
   void method3405(int var1) {
      this.field1546 = -713675717 * var1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("cr")
   static final void method3420(byte var0) {
      try {
         client.field957 += -451557715;
         classIS.field3051.field1687.method4375(client.field856 * 1612595797);
         if (client.field872 * -185864825 != -1) {
            if (var0 != 4) {
               throw new IllegalStateException();
            }

            if (Scene.method4153(classIS.field3051.field1687) != -185864825 * client.field872) {
               ModelData var1 = ModelData.method4067(classSX.field6095, client.field872 * -185864825, 0);
               if (null != var1) {
                  if (var0 != 4) {
                     return;
                  }

                  classIS.field3051.field1687.method4157(-185864825 * client.field872, var1.method4063());
               }
            }
         }

         classCC.method1406(-806820883);
         classLO.method7179(classIS.field3051, (byte)115);
         WorldView var6 = classIS.field3051;
         int var2 = -1927903443 * client.field975.field6795 - 1;
         if (classLJ.field4177 != null) {
            if (var0 != 4) {
               throw new IllegalStateException();
            }

            if (classIS.field3051 == var6 && var2 >= 0) {
               if (var0 != 4) {
                  throw new IllegalStateException();
               }

               if (60 == client.field975.field6798[var2]) {
                  if (var0 != 4) {
                     throw new IllegalStateException();
                  }

                  if (client.field839 * 1272389989 > 0 && client.field989 * 1200011005 == client.field975.field6799[var2]) {
                     if (var0 != 4) {
                        throw new IllegalStateException();
                     }
                  } else {
                     int var4 = 128 * client.field975.field6799[var2];
                     classCI.method1503(var6, var4, classLJ.field4177, (byte)71);
                  }
               }
            }
         }

         classCW.method2694(classIS.field3051, 2053550871);
         WorldView var7 = classIS.field3051;
         if (927693091 * client.field972 >= 0) {
            if (var0 != 4) {
               throw new IllegalStateException();
            }

            if (IndexedObjectSet.method13449(var7.field1692, client.field972 * 927693091) != null) {
               if (var0 != 4) {
                  return;
               }

               Hitsplat.method3642(var7, 927693091 * client.field972, false, 1572604104);
            }
         }

         Renderable.method3852(classIS.field3051, (byte)0);
         classFY.method5305(classIS.field3051, classKR.field4005, (byte)40);
         classFY.method5305(classIS.field3051, classKR.field4004, (byte)119);
         classHO.method6113(classIS.field3051, classOQ.field5077, (byte)0);
         classNG.method7886(classIS.field3051, 1770667891);
         classHO.method6113(classIS.field3051, classOQ.field5079, (byte)0);
         classHO.method6113(classIS.field3051, classOQ.field5078, (byte)0);
         classCC.method1407((byte)0);
         classIH.method6253((short)8342);
         WorldView var3 = classIS.field3051;

         for (GraphicsObject var8 = (GraphicsObject)var3.field1689.method7957(); var8 != null; var8 = (GraphicsObject)var3.field1689.method7965()) {
            if (var0 != 4) {
               throw new IllegalStateException();
            }

            if (var3.field1708 * -483624883 == var8.field471 * 1040651135) {
               if (var0 != 4) {
                  throw new IllegalStateException();
               }

               if (!var8.method1096(-1583423215)) {
                  if (client.field856 * 1612595797 >= var8.field470 * 1835952965) {
                     if (var0 != 4) {
                        return;
                     }

                     var8.method1109(-322689719 * client.field950, 2001249014);
                     if (var8.method1096(-1145533270)) {
                        if (var0 != 4) {
                           return;
                        }

                        var8.vmethod398();
                     } else {
                        var3.field1687
                           .method4505(
                              1040651135 * var8.field471,
                              1586395561 * var8.field467,
                              var8.field468 * -1285652349,
                              var8.field469 * -1107927935,
                              60,
                              var8,
                              0,
                              -1L,
                              false
                           );
                     }
                  }
                  continue;
               }

               if (var0 != 4) {
                  return;
               }
            }

            var8.vmethod398();
         }

         classFY.method5305(classIS.field3051, classKR.field4006, (byte)11);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "dl.cr(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;)Z")
   @ObfuscatedName("bb")
   public static boolean method3418(classVV var0) {
      return null == var0.field6615;
   }

   @ObfuscatedSignature(descriptor = "()Lpi;")
   @ObfuscatedName("am")
   ColorTextureOverride method3411() {
      return this.field1557;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("gf")
   public void method3423(int var1) {
      if (this.field1543 != -1) {
         client.field1028.debug("Item quantity changed: {} ({} -> {})", new Object[]{this.getId(), this.getQuantity(), var1});
         ItemQuantityChanged var2 = new ItemQuantityChanged(this, this.method3426(), this.getQuantity(), var1);
         classOE.field4841.getCallbacks().post(var2);
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;I)V")
   @ObfuscatedName("ao")
   void method3417(classUZ var1, int var2) {
      try {
         this.field1545 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dl.ao(" + ')');
      }
   }

   public int getVisibleTime() {
      return this.field1547 * 1358941319 - client.field893 * -581422983 + client.field1085;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("od")
   public int method3421() {
      return this.field1543;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ar")
   @Override
   protected boolean vmethod97() {
      ItemComposition var1 = classOB.method8329(this.field1549 * -1370204663, (byte)-58);
      Model var2 = null == this.field1557
         ? ItemComposition.method8696(var1, this.field1548 * 1413659045, 1135635870)
         : var1.method8698(this.field1557, -1237182525);
      return var2 != null && null != var2.field2442;
   }

   TileItem() {
      this.field1544 = -1;
      this.field1546 = -649110747;
      this.field1545 = classUZ.field6426;
   }

   @ObfuscatedSignature(descriptor = "()Lfx;")
   @ObfuscatedName("ad")
   @Override
   protected final Model vmethod92() {
      ItemComposition var1 = classOB.method8329(-1370204663 * this.field1549, (byte)-43);
      Model var2 = null == this.field1557
         ? ItemComposition.method8696(var1, this.field1548 * 1413659045, 1169975203)
         : var1.method8698(this.field1557, -1717004033);
      if (null == var2) {
         return null;
      } else {
         Model var3 = null;
         if (!classUZ.method11813(classUZ.field6431, this.field1545, (byte)-103)) {
            classUL var4 = classDD.method2993(this.field1545, 2064394615);
            classUL.method11450(var4, -95899695);
            var3 = var2.method5106(false);
            var3.method5248(var4);
            classUL.method11416(var4, 1180979049);
         }

         return var3 == null ? var2 : var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ac")
   public static boolean method3419(int var0) {
      try {
         boolean var10000;
         if (!classNP.field4697.isEmpty()) {
            if (var0 != -1496781286) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "dl.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("jk")
   @Override
   public void vmethod387() {
      if (this.field1543 != -1 && field1555 == null) {
         field1555 = this;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("je")
   public void method3422(int var1) {
      this.field1543 = var1;
   }

   public int getDespawnTime() {
      return this.field1550 * 289254265 - client.field893 * -581422983 + client.field1085;
   }

   public boolean isPrivate() {
      return this.field1558;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("af")
   boolean method3408(int var1, int var2) {
      try {
         if (var1 >= 0) {
            if (var2 <= 1282433543) {
               throw new IllegalStateException();
            }

            if (var1 <= 4) {
               if (var2 <= 1282433543) {
                  throw new IllegalStateException();
               }

               boolean var10000;
               if (0 != (this.field1548 * -1737503501 & 1 << var1)) {
                  if (var2 <= 1282433543) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               return var10000;
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dl.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Lpi;")
   @ObfuscatedName("ax")
   ColorTextureOverride method3412(short var1) {
      try {
         return this.field1557;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dl.ax(" + ')');
      }
   }
}
