import java.util.ArrayList;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("le")
public class classLE {
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final int field4123 = 72;
   @ObfuscatedName("ab")
   long field4111;
   @ObfuscatedName("af")
   long field4113;
   @ObfuscatedName("ae")
   long field4114;
   @ObfuscatedName("as")
   long field4115;
   @ObfuscatedName("ax")
   int field4116;
   @ObfuscatedName("ag")
   long field4112;
   @ObfuscatedName("ac")
   int field4118;
   @ObfuscatedName("aa")
   int field4117;
   @ObfuscatedName("ao")
   int field4120;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   public static final int field4122 = 21;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field4121 = 18;
   @ObfuscatedSignature(descriptor = "Lku;")
   @ObfuscatedName("ea")
   static classKU field4124;
   @ObfuscatedName("az")
   public boolean field4119 = false;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   public void method6805() {
      if (-1L != 7273805839578160459L * this.field4112) {
         this.field4115 = (classDD.method2988(16711680) - this.field4112 * 7273805839578160459L) * 7337258472556907519L;
         this.field4112 = -4380477302914991203L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public void method6806(int var1) {
      try {
         if (-1L != 7273805839578160459L * this.field4112) {
            this.field4115 = (classDD.method2988(16711680) - this.field4112 * 7273805839578160459L) * 7337258472556907519L;
            this.field4112 = -4380477302914991203L;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "le.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lle;I)V")
   @ObfuscatedName("ss")
   public static void method6814(classLE var0, int var1) {
      if (var0 == null) {
         var0.method6817(var1);
      } else {
         try {
            if (-1L != var0.field4113 * -487073909452215031L) {
               if (var1 == -404014210) {
                  return;
               }

               var0.field4114 = (classDD.method2988(16711680) - -487073909452215031L * var0.field4113) * 2788183836609794057L;
               var0.field4113 = 7767324811451944647L;
            }

            var0.field4117 += -1618845627;
            var0.field4119 = true;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "le.ab(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public void method6800() {
      this.field4112 = classDD.method2988(16711680) * 4380477302914991203L;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   public void method6809(int var1) {
      this.field4113 = classDD.method2988(16711680) * -7767324811451944647L;
      this.field4116 = var1 * 1692844975;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;)V")
   @ObfuscatedName("pg")
   public static void method6801(classLE var0) {
      if (var0 == null) {
         var0.method6820();
      } else {
         var0.field4112 = classDD.method2988(16711680) * 4380477302914991203L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method6818(int var1) {
      try {
         this.field4119 = false;
         this.field4118 = 0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "le.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("au")
   public void method6821(int var1) {
      try {
         method6814(this, -811545557);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "le.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ai")
   public void method6802(int var1) {
      try {
         this.field4112 = classDD.method2988(16711680) * 4380477302914991203L;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "le.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   public void method6815() {
      if (-1L != this.field4113 * -487073909452215031L) {
         this.field4114 = (classDD.method2988(16711680) - -487073909452215031L * this.field4113) * 2788183836609794057L;
         this.field4113 = 7767324811451944647L;
      }

      this.field4117 += -1618845627;
      this.field4119 = true;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public void method6803() {
      this.field4112 = classDD.method2988(16711680) * 4380477302914991203L;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   public void method6810(int var1) {
      this.field4113 = classDD.method2988(16711680) * -7767324811451944647L;
      this.field4116 = var1 * 1692844975;
   }

   @ObfuscatedSignature(descriptor = "(Lle;II)V")
   @ObfuscatedName("lu")
   public static void method6811(classLE var0, int var1, int var2) {
      if (var0 == null) {
         var0.method6812(var1, var1);
      } else {
         try {
            var0.field4113 = classDD.method2988(16711680) * -7767324811451944647L;
            var0.field4116 = var1 * 1692844975;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "le.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method6807() {
      if (-1L != 7273805839578160459L * this.field4112) {
         this.field4115 = (classDD.method2988(16711680) - this.field4112 * 7273805839578160459L) * 7337258472556907519L;
         this.field4112 = -4380477302914991203L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   public void method6804() {
      this.field4112 = classDD.method2988(16711680) * 4380477302914991203L;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)V")
   @ObfuscatedName("ax")
   public void method6823(classXY var1, byte var2) {
      try {
         long var3 = 8891344905762583551L * this.field4115;
         var3 /= 10L;
         if (var3 < 0L) {
            if (var2 != 8) {
               throw new IllegalStateException();
            }

            var3 = 0L;
         } else if (var3 > 65535L) {
            if (var2 != 8) {
               throw new IllegalStateException();
            }

            var3 = 65535L;
         }

         var1.method13031((int)var3, -1542382992);
         long var5 = -1605016655774630343L * this.field4114;
         var5 /= 10L;
         if (var5 < 0L) {
            var5 = 0L;
         } else if (var5 > 65535L) {
            if (var2 != 8) {
               return;
            }

            var5 = 65535L;
         }

         var1.method13031((int)var5, -1542382992);
         long var7 = this.field4111 * 1011984226548346587L;
         var7 /= 10L;
         if (var7 < 0L) {
            if (var2 != 8) {
               return;
            }

            var7 = 0L;
         } else if (var7 > 65535L) {
            if (var2 != 8) {
               return;
            }

            var7 = 65535L;
         }

         var1.method13031((int)var7, -1542382992);
         var1.method13031(968782671 * this.field4116, -1542382992);
         var1.method13031(-1694632695 * this.field4118, -1542382992);
         var1.method13031(-1144147315 * this.field4117, -1542382992);
         var1.method13031(this.field4120 * -154512739, -1542382992);
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "le.ax(" + ')');
      }
   }

   public classLE() {
      this.field4112 = -4380477302914991203L;
      this.field4113 = 7767324811451944647L;
      this.field4114 = 0L;
      this.field4115 = 0L;
      this.field4111 = 0L;
      this.field4116 = 0;
      this.field4118 = 0;
      this.field4117 = 0;
      this.field4120 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   public void method6819() {
      this.field4119 = false;
      this.field4118 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   public void method6820() {
      this.field4119 = false;
      this.field4118 = 0;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("at")
   public void method6822() {
      method6814(this, -714468882);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;)V")
   @ObfuscatedName("ty")
   public static void method6816(classLE var0) {
      if (var0 == null) {
         var0.method6804();
      }

      if (-1L != var0.field4113 * -487073909452215031L) {
         var0.field4114 = (classDD.method2988(16711680) - -487073909452215031L * var0.field4113) * 2788183836609794057L;
         var0.field4113 = 7767324811451944647L;
      }

      var0.field4117 += -1618845627;
      var0.field4119 = true;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyk;)Ljava/util/Iterator;")
   @ObfuscatedName("ia")
   public static Iterator method6825(IndexedObjectSet var0) {
      if (var0 == null) {
         return var0.method13448();
      } else {
         var0.field6993.method13686();
         return var0.field6993;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ah")
   public void method6824(classXY var1) {
      long var2 = 8891344905762583551L * this.field4115;
      var2 /= 10L;
      if (var2 < 0L) {
         var2 = 0L;
      } else if (var2 > 65535L) {
         var2 = 65535L;
      }

      var1.method13031((int)var2, -1542382992);
      long var4 = -1605016655774630343L * this.field4114;
      var4 /= 10L;
      if (var4 < 0L) {
         var4 = 0L;
      } else if (var4 > 65535L) {
         var4 = 65535L;
      }

      var1.method13031((int)var4, -1542382992);
      long var6 = this.field4111 * 1011984226548346587L;
      var6 /= 10L;
      if (var6 < 0L) {
         var6 = 0L;
      } else if (var6 > 65535L) {
         var6 = 65535L;
      }

      var1.method13031((int)var6, -1542382992);
      var1.method13031(968782671 * this.field4116, -1542382992);
      var1.method13031(-843119933 * this.field4118, -1542382992);
      var1.method13031(916013409 * this.field4117, -1542382992);
      var1.method13031(this.field4120 * -1474712660, -1542382992);
   }

   @ObfuscatedSignature(descriptor = "(Lva;II)V")
   @ObfuscatedName("ao")
   public static void method6826(IndexDataBase var0, int var1, int var2) {
      try {
         if (classNP.field4697.isEmpty()) {
            if (var2 != 124181514) {
               throw new IllegalStateException();
            }
         } else {
            ArrayList var3 = new ArrayList();
            Iterator var4 = classNP.field4697.iterator();

            while (var4.hasNext()) {
               if (var2 != 124181514) {
                  throw new IllegalStateException();
               }

               MidiRequest var5 = (MidiRequest)var4.next();
               var5.field4641 = false;
               var5.field4642 = false;
               var5.field4632 = false;
               var5.field4631 = false;
               var5.field4640 = var0;
               var5.field4633 = var1 * -1016653583;
               var5.field4629 = 0.0F;
               var3.add(var5);
            }

            classIJ.method6258(
               var3,
               classNP.field4701 * -1331669075,
               classNP.field4700 * 1784906769,
               -1350272915 * classNP.field4694,
               classNP.field4699 * 396217257,
               false,
               -2064319880
            );
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "le.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ae")
   public void method6812(int var1, int var2) {
      try {
         this.field4111 = classDD.method2988(16711680) * -7767324811451944647L;
         this.field4118 = var1 * 1692844975;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "le.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ag")
   public void method6813(int var1) {
      this.field4115 = classDD.method2988(16711680) * -7767324811451944647L;
      this.field4120 = var1 * 1692844975;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;)V")
   @ObfuscatedName("rq")
   public static void method6808(classLE var0) {
      if (-1L != 7273805839578160459L * var0.field4112) {
         var0.field4115 = (classDD.method2988(16711680) - var0.field4112 * 7273805839578160459L) * 7337258472556907519L;
         var0.field4112 = -4380477302914991203L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ap")
   public void method6817(int var1) {
      try {
         if (-1L != this.field4115 * -487073909452215031L) {
            if (var1 == -404014210) {
               return;
            }

            this.field4114 = (classDD.method2988(16711680) - -487073909452215031L * this.field4114) * 2788183836609794057L;
            this.field4112 = 7767324811451944647L;
         }

         this.field4116 += -1618845627;
         this.field4119 = true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "le.ab(" + ')');
      }
   }
}
