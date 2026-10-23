import java.util.concurrent.ScheduledExecutorService;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ak")
public class classAK {
   @ObfuscatedName("bc")
   boolean field200;
   @ObfuscatedName("bp")
   int field201;
   @ObfuscatedName("ay")
   public static ScheduledExecutorService field212;
   @ObfuscatedSignature(descriptor = "Lap;")
   @ObfuscatedName("ap")
   public static classAP field211;
   @ObfuscatedName("au")
   int field194 = -1703959584;
   @ObfuscatedName("bx")
   long field193;
   @ObfuscatedSignature(descriptor = "Lah;")
   @ObfuscatedName("ar")
   classAH field210;
   @ObfuscatedSignature(descriptor = "[Lah;")
   @ObfuscatedName("br")
   classAH[] field198;
   @ObfuscatedName("an")
   long field195;
   @ObfuscatedName("ak")
   public int field206;
   @ObfuscatedName("av")
   public int field208;
   @ObfuscatedName("ai")
   public int[] field214;
   @ObfuscatedName("am")
   int field196;
   @ObfuscatedName("ah")
   int field197;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field205 = 286331153;
   @ObfuscatedName("at")
   int field207;
   @ObfuscatedName("aw")
   long field202 = classDD.method2988(16711680) * 1443198699679539285L;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field209 = 512;
   @ObfuscatedName("bn")
   int field199;
   @ObfuscatedSignature(descriptor = "[Lah;")
   @ObfuscatedName("bm")
   classAH[] field203;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final byte field204 = 0;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("cq")
   static IndexDataBase field213;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   public void vmethod49(int var1) throws Exception {
   }

   @ObfuscatedSignature(descriptor = "(Lah;B)V")
   @ObfuscatedName("ae")
   public final synchronized void method396(classAH var1, byte var2) {
      try {
         this.field210 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ak.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ab")
   public final synchronized void method400(byte var1) {
      try {
         if (this.field214 == null) {
            if (var1 < 62) {
               ;
            }
         } else {
            long var2 = classDD.method2988(16711680);

            try {
               if (0L != this.field195 * -8595143364790996559L) {
                  if (var2 < this.field195 * -8595143364790996559L) {
                     return;
                  }

                  this.vmethod50(this.field208 * -1077408521, 364500251);
                  this.field195 = 0L;
                  this.field200 = true;
               }

               int var4 = this.vmethod54(-2035463840);
               if (-1677107327 * this.field199 - var4 > this.field196 * 707572161) {
                  if (var1 >= 62) {
                     throw new IllegalStateException();
                  }

                  this.field196 = (-1677107327 * this.field199 - var4) * 904027713;
               }

               int var5 = this.field207 * -1927991187 + this.field206 * -1043815553;
               if (512 + var5 > 32768) {
                  if (var1 >= 62) {
                     throw new IllegalStateException();
                  }

                  var5 = 32256;
               }

               if (512 + var5 > -1077408521 * this.field208) {
                  if (var1 >= 62) {
                     return;
                  }

                  this.field208 += 1238572032;
                  if (this.field208 * -1077408521 > 32768) {
                     if (var1 >= 62) {
                        throw new IllegalStateException();
                     }

                     this.field208 = 979599360;
                  }

                  this.vmethod60((byte)-66);
                  this.vmethod50(this.field208 * -1077408521, 364500251);
                  var4 = 0;
                  this.field200 = true;
                  if (var5 + 512 > -1077408521 * this.field208) {
                     var5 = this.field208 * -1077408521 - 512;
                     this.field207 = -951690907 * (var5 - this.field206 * -1043815553);
                  }
               }

               while (var4 < var5) {
                  if (var1 >= 62) {
                     throw new IllegalStateException();
                  }

                  method417(this, this.field214, 512);
                  this.vmethod58();
                  var4 += 512;
               }

               if (var2 > 5613825365808117069L * this.field193) {
                  if (var1 >= 62) {
                     return;
                  }

                  if (!this.field200) {
                     if (0 == this.field196 * 707572161) {
                        if (var1 >= 62) {
                           throw new IllegalStateException();
                        }

                        if (this.field197 * 830055437 == 0) {
                           if (var1 >= 62) {
                              throw new IllegalStateException();
                           }

                           this.vmethod60((byte)13);
                           this.field195 = (var2 + 2000L) * -6194306818789607599L;
                           return;
                        }
                     }

                     this.field207 = Math.min(this.field197 * 830055437, this.field196 * 707572161) * -951690907;
                     this.field197 = 723751813 * this.field196;
                  } else {
                     this.field200 = false;
                  }

                  this.field196 = 0;
                  this.field193 = 2550979937234733957L * (var2 + 2000L);
               }

               this.field199 = 1186390145 * var4;
            } catch (Exception var7) {
               this.vmethod60((byte)-35);
               this.field195 = (2000L + var2) * -6194306818789607599L;
            }

            try {
               if (var2 > -1586169227219985155L * this.field202 + 500000L) {
                  if (var1 >= 62) {
                     throw new IllegalStateException();
                  }

                  var2 = this.field202 * -1586169227219985155L;
               }

               while (var2 > 5000L + -1586169227219985155L * this.field202) {
                  if (var1 >= 62) {
                     return;
                  }

                  this.method412(512, (short)-24289);
                  this.field202 = this.field202 + 512000 / (675076789 * WidgetConfigNode.field4136) * 1443198699679539285L;
               }
            } catch (Exception var6) {
               this.field202 = 1443198699679539285L * var2;
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ak.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public final void method402(int var1) {
      try {
         this.field200 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ak.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   public final synchronized void method405(int var1) {
      try {
         this.field200 = true;

         try {
            this.vmethod62(-188411513);
         } catch (Exception var3) {
            this.vmethod60((byte)-102);
            this.field195 = (classDD.method2988(16711680) + 2000L) * -6194306818789607599L;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ak.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lak;Lah;I)V")
   @ObfuscatedName("ch")
   public static void method419(classAK var0, classAH var1, int var2) {
      if (var0 == null) {
         var0.method423(var1, var2);
      } else {
         int var3 = var2 >> 5;
         classAH var4 = var0.field203[var3];
         if (null == var4) {
            var0.field198[var3] = var1;
         } else {
            var4.field152 = var1;
         }

         var0.field203[var3] = var1;
         var1.field151 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ax")
   public final synchronized void method408(byte var1) {
      try {
         if (classXC.field6823 != null) {
            if (var1 != -1) {
               return;
            }

            boolean var2 = true;

            for (int var3 = 0; var3 < 2; var3++) {
               if (var1 != -1) {
                  return;
               }

               if (this == classXC.field6823.field287[var3]) {
                  classXC.field6823.field287[var3] = null;
               }

               if (null != classXC.field6823.field287[var3]) {
                  var2 = false;
               }
            }

            if (var2) {
               if (var1 != -1) {
                  throw new IllegalStateException();
               }

               field212.shutdownNow();
               field212 = null;
               classXC.field6823 = null;
            }
         }

         this.vmethod60((byte)59);
         this.field214 = null;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ak.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IS)V")
   @ObfuscatedName("ac")
   final void method412(int var1, short var2) {
      try {
         this.field201 -= var1 * 1233157839;
         if (this.field201 * -1980118993 < 0) {
            if (var2 >= 157) {
               throw new IllegalStateException();
            }

            this.field201 = 0;
         }

         if (null != this.field210) {
            if (var2 >= 157) {
               throw new IllegalStateException();
            }

            this.field210.vmethod31(var1);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ak.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lah;IB)V")
   @ObfuscatedName("ao")
   final void method420(classAH var1, int var2, byte var3) {
      try {
         int var4 = var2 >> 5;
         classAH var5 = this.field203[var4];
         if (null == var5) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            this.field198[var4] = var1;
         } else {
            var5.field152 = var1;
         }

         this.field203[var4] = var1;
         var1.field151 = var2;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ak.ao(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;Llw;IILvv;)V")
   @ObfuscatedName("oo")
   public static void method424(classGP var0, Widget var1, int var2, int var3, classVV var4) {
      if (var0 == null) {
         var0.method5618(var2, var4, var2);
      } else {
         classKH var5 = var1.method7254(var4, false, -297012961);
         if (null != var5) {
            if (var0.field2637.vmethod163(712213356)) {
               SpritePixels.method13589(
                  var0.field2635.vmethod111(-217993828),
                  var2,
                  var3,
                  var5.field3584 * -1246906611,
                  var5.field3583 * 342325999,
                  25,
                  25,
                  var0.field2637.vmethod186(2137414314),
                  -620371225,
                  var2,
                  var3,
                  var5.field3585,
                  var5.field3582
               );
            } else {
               classYW.method13791(var2, var3, 0, var5.field3585, var5.field3582);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("al")
   public void vmethod45(byte var1) throws Exception {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ak.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   public void vmethod50(int var1, int var2) throws Exception {
      try {
         ;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ak.aj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Loc;ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("gw")
   public static String method401(classOC var0, int var1, String var2) {
      return classFS.method4998(var0.field4814, var1, var2, (byte)95);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   protected int vmethod54(int var1) throws Exception {
      try {
         return -1077408521 * this.field208;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ak.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   protected void vmethod58() throws Exception {
      try {
         ;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ak.aq(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lak;Lah;I)V")
   @ObfuscatedName("gk")
   public static void method421(classAK var0, classAH var1, int var2) {
      if (var0 == null) {
         var0.vmethod65();
      } else {
         int var3 = var2 >> 5;
         classAH var4 = var0.field203[var3];
         if (null == var4) {
            var0.field198[var3] = var1;
         } else {
            var4.field152 = var1;
         }

         var0.field203[var3] = var1;
         var1.field151 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ad")
   protected void vmethod60(byte var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ak.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ap")
   protected void vmethod62(int var1) throws Exception {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ak.ap(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("au")
   static final void method395(classAH var0) {
      var0.field150 = false;
      if (var0.field153 != null) {
         var0.field153.field295 = 0;
      }

      for (classAH var1 = var0.vmethod29(); var1 != null; var1 = var0.vmethod35()) {
         classAD.method324(var1, (byte)-33);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   public final void method403() {
      this.field200 = true;
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("ar")
   public final synchronized void method397(classAH var1) {
      this.field210 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("aw")
   public final synchronized void method398(classAH var1) {
      this.field210 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lak;I)V")
   @ObfuscatedName("yr")
   public static void method413(classAK var0, int var1) {
      if (var0 == null) {
         var0.method411();
      } else {
         var0.field201 -= var1 * 243502909;
         if (var0.field201 * -1980118993 < 0) {
            var0.field201 = 0;
         }

         if (null != var0.field210) {
            var0.field210.vmethod31(var1);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   protected void vmethod63() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bw")
   public void vmethod51(int var1) throws Exception {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lak;I)V")
   @ObfuscatedName("ie")
   public static void method414(classAK var0, int var1) {
      if (var0 == null) {
         var0.method404(var1);
      }

      var0.field201 -= var1 * 1233157839;
      if (var0.field201 * -1980118993 < 0) {
         var0.field201 = 0;
      }

      if (null != var0.field210) {
         var0.field210.vmethod31(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("at")
   public final synchronized void method406() {
      this.field200 = true;

      try {
         this.vmethod62(1238245580);
      } catch (Exception var2) {
         this.vmethod60((byte)118);
         this.field195 = (classDD.method2988(16711680) + 2000L) * -6194306818789607599L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("an")
   public final synchronized void method409() {
      if (classXC.field6823 != null) {
         boolean var1 = true;

         for (int var2 = 0; var2 < 2; var2++) {
            if (this == classXC.field6823.field287[var2]) {
               classXC.field6823.field287[var2] = null;
            }

            if (null != classXC.field6823.field287[var2]) {
               var1 = false;
            }
         }

         if (var1) {
            field212.shutdownNow();
            field212 = null;
            classXC.field6823 = null;
         }
      }

      this.vmethod60((byte)-16);
      this.field214 = null;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("be")
   protected int vmethod55() throws Exception {
      return -1077408521 * this.field208;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public final synchronized void method410() {
      if (classXC.field6823 != null) {
         boolean var1 = true;

         for (int var2 = 0; var2 < 2; var2++) {
            if (this == classXC.field6823.field287[var2]) {
               classXC.field6823.field287[var2] = null;
            }

            if (null != classXC.field6823.field287[var2]) {
               var1 = false;
            }
         }

         if (var1) {
            field212.shutdownNow();
            field212 = null;
            classXC.field6823 = null;
         }
      }

      this.vmethod60((byte)-40);
      this.field214 = null;
   }

   public classAK() {
      this.field195 = 0L;
      this.field196 = 0;
      this.field197 = 0;
      this.field199 = 0;
      this.field193 = 0L;
      this.field200 = true;
      this.field201 = 0;
      this.field198 = new classAH[8];
      this.field203 = new classAH[8];
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bx")
   final void method415(int var1) {
      this.field201 -= var1 * 1233157839;
      if (this.field201 * -1980118993 < 0) {
         this.field201 = 0;
      }

      if (null != this.field210) {
         this.field210.vmethod31(var1);
      }
   }

   @ObfuscatedSignature(descriptor = "([II)V")
   @ObfuscatedName("bp")
   final void method416(int[] var1, int var2) {
      int var3 = var2;
      if (classKG.field3581) {
         var3 = var2 << 1;
      }

      classZZ.method14312(var1, 0, var3);
      this.field201 -= var2 * 1233157839;
      if (this.field210 != null && this.field201 * -823697344 <= 0) {
         this.field201 = this.field201 + 1233157839 * (675076789 * WidgetConfigNode.field4136 >> 4);
         classAD.method324(this.field210, (byte)35);
         this.method420(this.field210, this.field210.vmethod26(), (byte)-46);
         int var4 = 0;
         int var5 = 255;

         label138:
         for (int var6 = 7; 0 != var5; var6--) {
            int var7;
            int var8;
            if (var6 < 0) {
               var7 = var6 & 3;
               var8 = -(var6 >> 2);
            } else {
               var7 = var6;
               var8 = 0;
            }

            for (int var9 = var5 >>> var7 & -1303353033; 0 != var9; var9 >>>= 4) {
               if (0 != (var9 & 1)) {
                  var5 &= ~(1 << var7);
                  classAH var10 = null;
                  classAH var11 = this.field198[var7];

                  while (null != var11) {
                     classAW var12 = var11.field153;
                     if (var12 != null && var12.field295 > var8) {
                        var5 |= 1 << var7;
                        var10 = var11;
                        var11 = var11.field152;
                     } else {
                        var11.field150 = true;
                        int var13 = var11.vmethod32();
                        var4 += var13;
                        if (null != var12) {
                           var12.field295 += var13;
                        }

                        if (var4 >= this.field194 * 1361054477) {
                           break label138;
                        }

                        classAH var14 = var11.vmethod29();
                        if (null != var14) {
                           for (int var15 = var11.field151; var14 != null; var14 = var11.vmethod35()) {
                              this.method420(var14, var15 * var14.vmethod26() >> 8, (byte)-37);
                           }
                        }

                        classAH var20 = var11.field152;
                        var11.field152 = null;
                        if (null == var10) {
                           this.field198[var7] = var20;
                        } else {
                           var10.field152 = var20;
                        }

                        if (null == var20) {
                           this.field203[var7] = var10;
                        }

                        var11 = var20;
                     }
                  }
               }

               var7 += 4;
               var8++;
            }
         }

         for (int var16 = 0; var16 < 8; var16++) {
            classAH var17 = this.field198[var16];
            classAH[] var18 = this.field198;
            this.field203[var16] = null;
            var18[var16] = null;

            while (var17 != null) {
               classAH var19 = var17.field152;
               var17.field152 = null;
               var17 = var19;
            }
         }
      }

      if (-1980118993 * this.field201 < 0) {
         this.field201 = 0;
      }

      if (this.field210 != null) {
         this.field210.vmethod30(var1, 0, var2);
      }

      this.field202 = classDD.method2988(16711680) * 1443198699679539285L;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   public void vmethod52(int var1) throws Exception {
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bb")
   protected int vmethod56() throws Exception {
      return -1470790426 * this.field208;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   public void vmethod46() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "(Lak;[II)V")
   @ObfuscatedName("na")
   public static void method417(classAK var0, int[] var1, int var2) {
      if (var0 == null) {
         var0.method418(var1, var2);
      }

      int var3 = var2;
      if (classKG.field3581) {
         var3 = var2 << 1;
      }

      classZZ.method14312(var1, 0, var3);
      var0.field201 -= var2 * 1233157839;
      if (var0.field210 != null && var0.field201 * -1980118993 <= 0) {
         var0.field201 = var0.field201 + 1233157839 * (675076789 * WidgetConfigNode.field4136 >> 4);
         classAD.method324(var0.field210, (byte)-83);
         var0.method420(var0.field210, var0.field210.vmethod26(), (byte)-7);
         int var4 = 0;
         int var5 = 255;

         label142:
         for (int var6 = 7; 0 != var5; var6--) {
            int var7;
            int var8;
            if (var6 < 0) {
               var7 = var6 & 3;
               var8 = -(var6 >> 2);
            } else {
               var7 = var6;
               var8 = 0;
            }

            for (int var9 = var5 >>> var7 & 286331153; 0 != var9; var9 >>>= 4) {
               if (0 != (var9 & 1)) {
                  var5 &= ~(1 << var7);
                  classAH var10 = null;
                  classAH var11 = var0.field198[var7];

                  while (null != var11) {
                     classAW var12 = var11.field153;
                     if (var12 != null && var12.field295 > var8) {
                        var5 |= 1 << var7;
                        var10 = var11;
                        var11 = var11.field152;
                     } else {
                        var11.field150 = true;
                        int var13 = var11.vmethod32();
                        var4 += var13;
                        if (null != var12) {
                           var12.field295 += var13;
                        }

                        if (var4 >= var0.field194 * 2096611039) {
                           break label142;
                        }

                        classAH var14 = var11.vmethod29();
                        if (null != var14) {
                           for (int var15 = var11.field151; var14 != null; var14 = var11.vmethod35()) {
                              var0.method420(var14, var15 * var14.vmethod26() >> 8, (byte)-119);
                           }
                        }

                        classAH var20 = var11.field152;
                        var11.field152 = null;
                        if (null == var10) {
                           var0.field198[var7] = var20;
                        } else {
                           var10.field152 = var20;
                        }

                        if (null == var20) {
                           var0.field203[var7] = var10;
                        }

                        var11 = var20;
                     }
                  }
               }

               var7 += 4;
               var8++;
            }
         }

         for (int var16 = 0; var16 < 8; var16++) {
            classAH var17 = var0.field198[var16];
            classAH[] var18 = var0.field198;
            var0.field203[var16] = null;
            var18[var16] = null;

            while (var17 != null) {
               classAH var19 = var17.field152;
               var17.field152 = null;
               var17 = var19;
            }
         }
      }

      if (-1980118993 * var0.field201 < 0) {
         var0.field201 = 0;
      }

      if (var0.field210 != null) {
         var0.field210.vmethod30(var1, 0, var2);
      }

      var0.field202 = classDD.method2988(16711680) * 1443198699679539285L;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   public void vmethod47() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   protected void vmethod64() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   public void vmethod53(int var1) throws Exception {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   public final synchronized void method411() {
      if (classXC.field6823 != null) {
         boolean var1 = true;

         for (int var2 = 0; var2 < 2; var2++) {
            if (this == classXC.field6823.field287[var2]) {
               classXC.field6823.field287[var2] = null;
            }

            if (null != classXC.field6823.field287[var2]) {
               var1 = false;
            }
         }

         if (var1) {
            field212.shutdownNow();
            field212 = null;
            classXC.field6823 = null;
         }
      }

      this.vmethod60((byte)64);
      this.field214 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("sw")
   public void method426() {
      if (this.field214 == null) {
         classAO var1 = classOE.field4841.method2421();
         synchronized (var1) {
            var1.method642().clear();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lah;)V")
   @ObfuscatedName("ai")
   public final synchronized void method399(classAH var1) {
      this.field210 = var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   public final synchronized void method407() {
      this.field200 = true;

      try {
         this.vmethod62(-270817956);
      } catch (Exception var2) {
         this.vmethod60((byte)47);
         this.field195 = (classDD.method2988(16711680) + 2000L) * -6194306818789607599L;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bi")
   protected int vmethod57() throws Exception {
      return -1077408521 * this.field208;
   }

   @ObfuscatedSignature(descriptor = "(Lah;I)V")
   @ObfuscatedName("br")
   final void method422(classAH var1, int var2) {
      int var3 = var2 >> 5;
      classAH var4 = this.field203[var3];
      if (null == var4) {
         this.field198[var3] = var1;
      } else {
         var4.field152 = var1;
      }

      this.field203[var3] = var1;
      var1.field151 = var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   public void vmethod48() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   protected void vmethod61() {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   protected void vmethod65() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   protected void vmethod59() throws Exception {
   }

   @ObfuscatedSignature(descriptor = "(IIIIIIB)Z")
   @ObfuscatedName("ae")
   static boolean method394(int var0, int var1, int var2, int var3, int var4, int var5, byte var6) {
      try {
         int var7 = var4 - var2;
         int var8 = var5 - var3;
         int var9 = var0 - var2;
         int var10 = var1 - var3;
         int var11 = classBY.method1357(var7, var8, var9, var10, (byte)2);
         int var12 = classBY.method1357(var7, var8, var7, var8, (byte)2);
         if (0 <= var11) {
            if (var6 >= 4) {
               throw new IllegalStateException();
            }

            if (var11 <= var12) {
               if (var6 >= 4) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var13) {
         throw classEG.method3899(var13, "ak.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ar")
   static final int method425(int var0, int var1, int var2) {
      try {
         int var3 = var0 + 57 * var1;
         var3 = var3 << 13 ^ var3;
         int var4 = (789221 + 15731 * var3 * var3) * var3 + 1376312589 & 2147483647;
         return var4 >> 19 & 0xFF;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ak.ar(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "([II)V")
   @ObfuscatedName("aa")
   final void method418(int[] var1, int var2) {
      int var3 = var2;
      if (classKG.field3581) {
         var3 = var2 << 1;
      }

      classZZ.method14312(var1, 0, var3);
      this.field207 = this.field197 - var2 * 1233157839;
      if (this.field210 != null && this.field194 * -1980118993 <= 0) {
         this.field194 = this.field197 + 1233157839 * (675076789 * WidgetConfigNode.field4136 >> 4);
         classAD.method324(this.field210, (byte)-83);
         this.method420(this.field210, this.field210.vmethod24(), (byte)-7);
         int var4 = 0;
         int var5 = 255;

         label140:
         for (int var6 = 7; 0 != var5; var6--) {
            int var7;
            int var8;
            if (var6 < 0) {
               var7 = var6 & 3;
               var8 = -(var6 >> 2);
            } else {
               var7 = var6;
               var8 = 0;
            }

            for (int var9 = var5 >>> var7 & 286331153; 0 != var9; var9 >>>= 4) {
               if (0 != (var9 & 1)) {
                  var5 &= ~(1 << var7);
                  classAH var10 = null;
                  classAH var11 = this.field203[var7];

                  while (null != var11) {
                     classAW var12 = var11.field153;
                     if (var12 != null && var12.field295 > var8) {
                        var5 |= 1 << var7;
                        var10 = var11;
                        var11 = var11.field152;
                     } else {
                        var11.field150 = true;
                        int var13 = var11.vmethod32();
                        var4 += var13;
                        if (null != var12) {
                           var12.field295 += var13;
                        }

                        if (var4 >= this.field197 * 2096611039) {
                           break label140;
                        }

                        classAH var14 = var11.vmethod35();
                        if (null != var14) {
                           for (int var15 = var11.field151; var14 != null; var14 = var11.vmethod34()) {
                              this.method420(var14, var15 * var14.vmethod24() >> 8, (byte)-119);
                           }
                        }

                        classAH var20 = var11.field152;
                        var11.field152 = null;
                        if (null == var10) {
                           this.field198[var7] = var20;
                        } else {
                           var10.field152 = var20;
                        }

                        if (null == var20) {
                           this.field203[var7] = var10;
                        }

                        var11 = var20;
                     }
                  }
               }

               var7 += 4;
               var8++;
            }
         }

         for (int var16 = 0; var16 < 8; var16++) {
            classAH var17 = this.field198[var16];
            classAH[] var18 = this.field198;
            this.field198[var16] = null;
            var18[var16] = null;

            while (var17 != null) {
               classAH var19 = var17.field152;
               var17.field152 = null;
               var17 = var19;
            }
         }
      }

      if (-1980118993 * this.field194 < 0) {
         this.field201 = 0;
      }

      if (this.field210 != null) {
         this.field210.vmethod36(var1, 0, var2);
      }

      this.field193 = classDD.method2988(16711680) * 1443198699679539285L;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bn")
   final void method404(int var1) {
      try {
         this.field200 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ak.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lah;I)V")
   @ObfuscatedName("bt")
   final void method423(classAH var1, int var2) {
      int var3 = var2 >> 5;
      classAH var4 = this.field203[var3];
      if (null == var4) {
         this.field198[var3] = var1;
      } else {
         var4.field152 = var1;
      }

      this.field198[var3] = var1;
      var1.field151 = var2;
   }
}
