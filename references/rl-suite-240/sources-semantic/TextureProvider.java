import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ec")
public class TextureProvider implements classFG, net.runelite.api.TextureProvider {
   @ObfuscatedName("ag")
   double field1727;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   public static final int field1733 = 60;
   @ObfuscatedName("ae")
   int field1728;
   @ObfuscatedName("ab")
   int field1726;
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final int field1734 = 1076101519;
   @ObfuscatedName("as")
   int field1730;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ax")
   IndexDataBase field1729;
   @ToRemove(unused = "true")
   @ObfuscatedName("ly")
   static String field1735;
   @ObfuscatedSignature(descriptor = "[Lfu;")
   @ObfuscatedName("az")
   Texture[] field1732;
   @ObfuscatedSignature(descriptor = "Lnn;")
   @ObfuscatedName("af")
   Deque2 field1731 = new Deque2();

   @ObfuscatedSignature(descriptor = "()[Lfu;")
   @ObfuscatedName("gt")
   public Texture[] method3838() {
      return this.field1732;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod408(int var1, int var2) {
      try {
         if (null != this.field1732[var1]) {
            if (var2 >= -934599992) {
               throw new IllegalStateException();
            } else {
               return this.field1732[var1].field2394;
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ec.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   public int method3822(byte var1) {
      try {
         if (0 == this.field1732.length) {
            if (var1 >= 12) {
               throw new IllegalStateException();
            } else {
               return 100;
            }
         } else {
            int var2 = 0;
            int var3 = 0;
            Texture[] var4 = this.field1732;

            for (int var5 = 0; var5 < var4.length; var5++) {
               if (var1 >= 12) {
                  throw new IllegalStateException();
               }

               Texture var6 = var4[var5];
               if (var6 != null) {
                  if (var1 >= 12) {
                     throw new IllegalStateException();
                  }

                  if (var6.field2398 != -1) {
                     var2++;
                     if (IndexDataBase.method11881(this.field1729, var6.field2398, (byte)-1)) {
                        if (var1 >= 12) {
                           throw new IllegalStateException();
                        }

                        var3++;
                     }
                  }
               }
            }

            if (var2 != 0) {
               return var3 * 100 / var2;
            } else if (var1 >= 12) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "ec.az(" + 41);
      }
   }

   public int[] load(int var1) {
      return this.vmethod402(var1, (byte)-48);
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("af")
   public void method3826(double var1) {
      try {
         this.field1727 = var1;
         this.method3832((byte)-15);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ec.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)[I")
   @ObfuscatedName("ae")
   @Override
   public int[] vmethod402(int var1, byte var2) {
      try {
         Texture var3 = this.field1732[var1];
         if (var3 != null) {
            if (var2 >= 2) {
               throw new IllegalStateException();
            }

            if (null != var3.field2395) {
               if (var2 >= 2) {
                  throw new IllegalStateException();
               }

               this.field1731.method7949(var3);
               var3.field2396 = true;
               return var3.field2395;
            }

            boolean var4 = var3.method5025(this.field1727, 1127091647 * this.field1730, this.field1729);
            if (var4) {
               if (var2 >= 2) {
                  throw new IllegalStateException();
               }

               if (0 == this.field1726 * 500420469) {
                  if (var2 >= 2) {
                     throw new IllegalStateException();
                  }

                  Texture var5 = (Texture)classQD.method9544(this.field1731);
                  var5.method5033();
               } else {
                  this.field1726 -= -1309062947;
               }

               this.field1731.method7949(var3);
               var3.field2396 = true;
               return var3.field2395;
            }
         }

         return null;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ec.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("vc")
   public void method3839(int var1) {
      this.field1726 = var1 * -1309062947;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod412(int var1, int var2) {
      try {
         return this.field1732[var1].field2397;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ec.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I[[[IIII[I[I[II)V")
   @ObfuscatedName("az")
   public static void method3821(int var0, int[][][] var1, int var2, int var3, int var4, int[] var5, int[] var6, int[] var7, int var8) {
      try {
         int[] var9 = SceneTilePaint.field2280[var0];

         for (int var10 = 0; var10 < var9.length; var10++) {
            if (var8 != 1181281029) {
               throw new IllegalStateException();
            }

            int var14 = var9[var10];
            int var13 = (var3 << 7) + SceneTilePaint.field2278[var14] * 128;
            var5[var10] = var13;
            var6[var10] = VarbitComposition.method9251(var9[var10], var1, var2, var3, var4, (byte)-60);
            var7[var10] = classFO.method4867(var9[var10], var4, 260031741);
         }
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "ec.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ax")
   public void method3833(int var1, int var2) {
      try {
         for (int var3 = 0; var3 < this.field1732.length; var3++) {
            if (var2 != -1467379791) {
               this.method3836(var1);
               return;
            }

            Texture var4 = this.field1732[var3];
            if (var4 != null) {
               if (var2 != -1467379791) {
                  this.method3836(var1);
                  return;
               }

               if (var4.field2399 != 0 && var4.field2396) {
                  if (var2 != -1467379791) {
                     throw new IllegalStateException();
                  }

                  var4.method5039(var1);
                  var4.field2396 = false;
               }
            }
         }

         this.method3836(var1);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ec.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   public int method3823() {
      if (0 == this.field1732.length) {
         return 100;
      } else {
         int var1 = 0;
         int var2 = 0;
         Texture[] var3 = this.field1732;

         for (int var4 = 0; var4 < var3.length; var4++) {
            Texture var5 = var3[var4];
            if (var5 != null && var5.field2398 != -1) {
               var1++;
               if (IndexDataBase.method11881(this.field1729, var5.field2398, (byte)-1)) {
                  var2++;
               }
            }
         }

         return var1 == 0 ? 0 : var2 * 100 / var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ad")
   @Override
   public int[] vmethod403(int var1) {
      Texture var2 = this.field1732[var1];
      if (var2 != null) {
         if (null != var2.field2395) {
            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }

         boolean var3 = var2.method5025(this.field1727, 1127091647 * this.field1730, this.field1729);
         if (var3) {
            if (0 == this.field1726 * 500420469) {
               Texture var4 = (Texture)classQD.method9544(this.field1731);
               var4.method5033();
            } else {
               this.field1726 -= 395731437;
            }

            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   @Override
   public int vmethod409(int var1) {
      return null != this.field1732[var1] ? this.field1732[var1].field2394 : 0;
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("aj")
   public void method3827(double var1) {
      this.field1727 = var1;
      this.method3832((byte)33);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   public void method3834(int var1) {
      for (int var2 = 0; var2 < this.field1732.length; var2++) {
         Texture var3 = this.field1732[var2];
         if (var3 != null && var3.field2399 != 0 && var3.field2396) {
            var3.method5039(var1);
            var3.field2396 = false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lec;)V")
   @ObfuscatedName("cm")
   public static void method3830(TextureProvider var0) {
      if (var0 == null) {
         var0.method3823();
      }

      for (int var1 = 0; var1 < var0.field1732.length; var1++) {
         if (null != var0.field1732[var1]) {
            var0.field1732[var1].method5033();
         }
      }

      var0.field1731 = new Deque2();
      var0.field1726 = 47825473 * var0.field1728;
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("aq")
   @Override
   public int[] vmethod404(int var1) {
      Texture var2 = this.field1732[var1];
      if (var2 != null) {
         if (null != var2.field2395) {
            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }

         boolean var3 = var2.method5025(this.field1727, 1127091647 * this.field1730, this.field1729);
         if (var3) {
            if (0 == this.field1726 * 500420469) {
               Texture var4 = (Texture)classQD.method9544(this.field1731);
               var4.method5033();
            } else {
               this.field1726 -= -1309062947;
            }

            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aw")
   @Override
   public int vmethod410(int var1) {
      return null != this.field1732[var1] ? this.field1732[var1].field2394 : 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lec;D)V")
   @ObfuscatedName("zf")
   public static void method3828(TextureProvider var0, double var1) {
      if (var0 == null) {
         var0.getTextures();
      }

      var0.field1727 = var1;
      var0.method3832((byte)17);
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ap")
   @Override
   public int[] vmethod405(int var1) {
      Texture var2 = this.field1732[var1];
      if (var2 != null) {
         if (null != var2.field2395) {
            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }

         boolean var3 = var2.method5025(this.field1727, 1127091647 * this.field1730, this.field1729);
         if (var3) {
            if (0 == this.field1726 * 976587482) {
               Texture var4 = (Texture)classQD.method9544(this.field1731);
               var4.method5033();
            } else {
               this.field1726 -= -1045807641;
            }

            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ix")
   public void method3836(int var1) {
      classOE.field4841.getCallbacks().drawAboveOverheads();
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("au")
   @Override
   public int[] vmethod406(int var1) {
      Texture var2 = this.field1732[var1];
      if (var2 != null) {
         if (null != var2.field2395) {
            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }

         boolean var3 = var2.method5025(this.field1727, 1127091647 * this.field1730, this.field1729);
         if (var3) {
            if (0 == this.field1726 * 500420469) {
               Texture var4 = (Texture)classQD.method9544(this.field1731);
               var4.method5033();
            } else {
               this.field1726 -= -1309062947;
            }

            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }
      }

      return null;
   }

   public TextureProvider(IndexDataBase var1, IndexDataBase var2, int var3, double var4, int var6) {
      this.field1726 = 0;
      this.field1727 = 1.0;
      this.field1730 = 1014046592;
      this.field1729 = var2;
      this.field1728 = var3 * 168126365;
      this.field1726 = 47825473 * this.field1728;
      this.field1727 = var4;
      this.field1730 = var6 * 578347583;
      int[] var7 = var1.method11898(0, -1868977307);
      if (var7 != null) {
         int var8 = var7.length;
         this.field1732 = new Texture[var1.method11943(0, 1597643874)];

         for (int var9 = 0; var9 < var8; var9++) {
            classXY var10 = new classXY(var1.method11942(0, var7[var9], -1089106860));
            this.field1732[var7[var9]] = new Texture(var10);
         }
      } else {
         this.field1732 = new Texture[0];
      }

      this.method3837(var1, var2, var3, var4, var6);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   @Override
   public int vmethod411(int var1) {
      return null != this.field1732[var1] ? this.field1732[var1].field2394 : 0;
   }

   @ObfuscatedSignature(descriptor = "(Lva;Lva;IDI)V")
   @ObfuscatedName("ci")
   public void method3837(IndexDataBase var1, IndexDataBase var2, int var3, double var4, int var6) {
      this.method3840(128);
      this.method3839(128);
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ay")
   @Override
   public int[] vmethod407(int var1) {
      Texture var2 = this.field1732[var1];
      if (var2 != null) {
         if (null != var2.field2395) {
            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }

         boolean var3 = var2.method5025(this.field1727, 941287448 * this.field1730, this.field1729);
         if (var3) {
            if (0 == this.field1726 * 500420469) {
               Texture var4 = (Texture)classQD.method9544(this.field1731);
               var4.method5033();
            } else {
               this.field1726 -= -1275776620;
            }

            this.field1731.method7949(var2);
            var2.field2396 = true;
            return var2.field2395;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   @Override
   public boolean vmethod413(int var1) {
      return this.field1732[var1].field2397;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lec;D)V")
   @ObfuscatedName("cy")
   public static void method3829(TextureProvider var0, double var1) {
      if (var0 == null) {
         var0.getTextures();
      }

      var0.field1727 = var1;
      var0.method3832((byte)-5);
   }

   public double getBrightness() {
      return this.field1727;
   }

   public void setBrightness(double var1) {
      this.method3826(var1);
   }

   public int getDefaultColor(int var1) {
      return this.vmethod408(var1, -1340787172);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   public void method3831() {
      for (int var1 = 0; var1 < this.field1732.length; var1++) {
         if (null != this.field1732[var1]) {
            this.field1732[var1].method5033();
         }
      }

      this.field1731 = new Deque2();
      this.field1726 = 47825473 * this.field1728;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   public int method3824() {
      if (0 == this.field1732.length) {
         return 100;
      } else {
         int var1 = 0;
         int var2 = 0;
         Texture[] var3 = this.field1732;

         for (int var4 = 0; var4 < var3.length; var4++) {
            Texture var5 = var3[var4];
            if (var5 != null && var5.field2398 != -1) {
               var1++;
               if (IndexDataBase.method11881(this.field1729, var5.field2398, (byte)-1)) {
                  var2++;
               }
            }
         }

         return var1 == 0 ? 0 : var2 * 100 / var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("zh")
   public void method3840(int var1) {
      this.field1728 = var1 * 168126365;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   public void method3835(int var1) {
      for (int var2 = 0; var2 < this.field1732.length; var2++) {
         Texture var3 = this.field1732[var2];
         if (var3 != null && var3.field2399 != 0 && var3.field2396) {
            var3.method5039(var1);
            var3.field2396 = false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("as")
   public void method3832(byte var1) {
      try {
         for (int var2 = 0; var2 < this.field1732.length; var2++) {
            if (var1 == 1) {
               return;
            }

            if (null != this.field1732[var2]) {
               this.field1732[var2].method5033();
            }
         }

         this.field1731 = new Deque2();
         this.field1726 = 47825473 * this.field1728;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ec.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljl;")
   @ObfuscatedName("ae")
   public static classJL method3825(int var0) {
      try {
         classJL var1 = SceneTilePaint.method4814(1717678848);
         var1.field3340 = null;
         var1.field3337 = 0;
         var1.field3341 = new PacketBuffer(5000);
         return var1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ec.ae(" + ')');
      }
   }
}
