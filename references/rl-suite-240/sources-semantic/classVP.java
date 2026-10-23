import java.util.zip.CRC32;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vp")
public class classVP extends IndexDataBase {
   @ObfuscatedName("ap")
   volatile boolean[] field6549;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6547 = 200;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6543 = 200;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6544 = 2;
   @ObfuscatedName("ac")
   static CRC32 field6531 = new CRC32();
   @ObfuscatedName("aa")
   boolean field6534 = false;
   @ObfuscatedSignature(descriptor = "Lus;")
   @ObfuscatedName("al")
   classUS field6538;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final byte field6542 = 4;
   @ObfuscatedSignature(descriptor = "Lus;")
   @ObfuscatedName("aj")
   classUS field6539;
   @ObfuscatedName("ay")
   int field6533;
   @ObfuscatedName("aq")
   volatile int field6537;
   @ObfuscatedName("ad")
   boolean field6536;
   @ObfuscatedName("ao")
   boolean field6535 = false;
   @ObfuscatedName("au")
   int field6546;
   @ObfuscatedName("ai")
   int field6545;
   @ObfuscatedName("ar")
   int field6540;
   @ObfuscatedSignature(descriptor = "Lvb;")
   @ObfuscatedName("aw")
   classVB field6541;
   @ObfuscatedSignature(descriptor = "Lzv;")
   @ObfuscatedName("di")
   public static classZV field6548;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bg")
   public boolean method12111(int var1) {
      return this.field6549[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvp;)I")
   @ObfuscatedName("uv")
   public static int method12115(classVP var0) {
      if (var0 == null) {
         var0.method12087();
      }

      byte var1 = 0;
      int var2 = 0;

      for (int var3 = 0; var3 < var0.field6451.length; var3++) {
         if (var0.field6446[var3] > 0) {
            var1 += 100;
            var2 += var0.method12109(var3, -1943569917);
         }
      }

      return 0 == var1 ? 100 : 100 * var2 / var1;
   }

   @ObfuscatedSignature(descriptor = "(Lus;Lus;Lvb;IZZZ)V")
   @ObfuscatedName("nz")
   public void method12121(classUS var1, classUS var2, classVB var3, int var4, boolean var5, boolean var6, boolean var7) {
      if (var4 >= 0 && var4 < 23) {
         client.field1030[var4] = this;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method12084() {
      return 1 == this.field6537 * 1031852869;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvp;II)V")
   @ObfuscatedName("kn")
   public static void method12099(classVP var0, int var1, int var2) {
      if (var0 == null) {
         var0.method12104(var1, var1);
      } else {
         var0.field6546 = var1 * -1493060365;
         var0.field6545 = var2 * 376657241;
         if (var0.field6539 != null) {
            int var3 = var0.field6533 * 1618452037;
            classUS var4 = var0.field6539;
            byte[] var6 = null;
            synchronized (classUJ.field6311) {
               for (classVU var8 = (classVU)classUJ.field6311.method7957(); var8 != null; var8 = (classVU)classUJ.field6311.method7965()) {
                  if (var8.field6550 == var3 && var8.field6606 == var4 && 0 == var8.field6605 * -1312025255) {
                     var6 = var8.field6608;
                     break;
                  }
               }
            }

            if (null != var6) {
               var0.method12119(var4, var3, var6, true, (byte)112);
            } else {
               byte[] var11 = var4.method11587(var3, -1142684788);
               var0.method12119(var4, var3, var11, true, (byte)6);
            }
         } else {
            classVB.method11990(
               var0.field6541,
               var0,
               classUM.field6327.field6326 * -1661953164,
               var0.field6533 * -722774945,
               var0.field6546 * -2057620979,
               (byte)0,
               true,
               -1183449495
            );
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   public int method12116(int var1) {
      try {
         byte var2 = 0;
         int var3 = 0;

         for (int var4 = 0; var4 < this.field6451.length; var4++) {
            if (var1 >= -1370204663) {
               throw new IllegalStateException();
            }

            if (this.field6446[var4] > 0) {
               if (var1 >= -1370204663) {
                  throw new IllegalStateException();
               }

               var2 += 100;
               var3 += this.method12109(var4, -1795423275);
            }
         }

         if (0 != var2) {
            return 100 * var3 / var2;
         } else if (var1 >= -1370204663) {
            throw new IllegalStateException();
         } else {
            return 100;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "vp.ay(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvp;)I")
   @ObfuscatedName("ug")
   public static int method12089(classVP var0) {
      if (var0.field6537 * 1031852869 != 1 && (!var0.field6535 || 2 != 1031852869 * var0.field6537)) {
         if (var0.field6451 != null) {
            return 99;
         } else {
            int var1 = var0.field6541.method11982(classUM.field6327.field6326 * 1060637953, -169317867 * var0.field6533, -1779850424);
            if (var1 >= 100) {
               var1 = 99;
            }

            return var1;
         }
      } else {
         return 100;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   public void method12122(int var1) {
      this.method12094(var1, -161570027);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ae")
   public void method12094(int var1, int var2) {
      try {
         classVB.method11992(this.field6541, -169317867 * this.field6533, var1, (byte)1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vp.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod610(int var1, int var2) {
      try {
         if (null != this.field6538 && null != this.field6549 && this.field6549[var1]) {
            if (var2 >= 1284385685) {
               throw new IllegalStateException();
            }

            classUS var3 = this.field6538;
            byte[] var5 = null;
            synchronized (classUJ.field6311) {
               for (classVU var7 = (classVU)classUJ.field6311.method7957(); var7 != null; var7 = (classVU)classUJ.field6311.method7965()) {
                  if (var2 >= 1284385685) {
                     throw new IllegalStateException();
                  }

                  if (var7.field6550 == var1) {
                     if (var2 >= 1284385685) {
                        return;
                     }

                     if (var7.field6606 == var3) {
                        if (var2 >= 1284385685) {
                           throw new IllegalStateException();
                        }

                        if (var7.field6605 * -1312025255 == 0) {
                           if (var2 >= 1284385685) {
                              throw new IllegalStateException();
                           }

                           var5 = var7.field6608;
                           break;
                        }
                     }
                  }
               }
            }

            if (var5 != null) {
               if (var2 >= 1284385685) {
                  return;
               }

               this.method12119(var3, var1, var5, true, (byte)9);
            } else {
               byte[] var11 = var3.method11587(var1, -1142684788);
               this.method12119(var3, var1, var11, true, (byte)67);
            }
         } else {
            classVB.method11990(this.field6541, this, this.field6533 * -169317867, var1, this.field6450[var1], (byte)4, true, -1536139829);
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "vp.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BI[BII)I")
   @ObfuscatedName("zf")
   public static int method12123(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      return classYG.method13380(var0, var1, var2, var3, var4);
   }

   @ObfuscatedSignature(descriptor = "([BZ)Ljava/lang/Object;")
   @ObfuscatedName("tz")
   public static Object method12120(byte[] var0, boolean var1) {
      if (var0 == null) {
         return null;
      } else if (var0.length > 136) {
         classZW var2 = new classZW();
         var2.method14254(var0);
         return var2;
      } else {
         return var0;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ag")
   void method12097(byte var1) {
      try {
         this.field6537 = 1379634970;
         this.field6445 = new int[0];
         this.field6450 = new int[0];
         this.field6444 = new int[0];
         this.field6446 = new int[0];
         this.field6453 = new int[0][];
         this.field6451 = new Object[0];
         this.field6455 = new Object[0][];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vp.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method12093(int var0) {
      try {
         classPS.field5404.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "vp.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([B)[B")
   @ObfuscatedName("up")
   public static byte[] method12124(byte[] var0) {
      return classMD.method7581(var0, (byte)27);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   public int method12090(int var1) {
      try {
         if (this.field6537 * 1031852869 != 1) {
            if (var1 == -2329933) {
               throw new IllegalStateException();
            }

            if (!this.field6535 || 2 != 1031852869 * this.field6537) {
               if (this.field6451 != null) {
                  if (var1 == -2329933) {
                     throw new IllegalStateException();
                  }

                  return 99;
               }

               int var2 = this.field6541.method11982(classUM.field6327.field6326 * 1060637953, -169317867 * this.field6533, -1779850424);
               if (var2 >= 100) {
                  if (var1 == -2329933) {
                     throw new IllegalStateException();
                  }

                  var2 = 99;
               }

               return var2;
            }

            if (var1 == -2329933) {
               throw new IllegalStateException();
            }
         }

         return 100;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vp.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bk")
   public int method12117() {
      byte var1 = 0;
      int var2 = 0;

      for (int var3 = 0; var3 < this.field6451.length; var3++) {
         if (this.field6446[var3] > 0) {
            var1 += 100;
            var2 += this.method12109(var3, -1338221037);
         }
      }

      return 0 == var1 ? 1965985208 : -1333453745 * var2 / var1;
   }

   @ObfuscatedSignature(descriptor = "(I[BZZI)V")
   @ObfuscatedName("ax")
   void method12105(int var1, byte[] var2, boolean var3, boolean var4, int var5) {
      try {
         if (var3) {
            if (var5 == 685517991) {
               return;
            }

            if (1 == this.field6537 * 1031852869) {
               if (var5 == 685517991) {
                  throw new IllegalStateException();
               }

               throw new RuntimeException();
            }

            if (this.field6539 != null) {
               if (var5 == 685517991) {
                  return;
               }

               int var6 = -169317867 * this.field6533;
               classUS var7 = this.field6539;
               classVU var8 = new classVU();
               var8.field6605 = 0;
               var8.field6550 = var6;
               var8.field6608 = var2;
               var8.field6606 = var7;
               synchronized (classUJ.field6311) {
                  classUJ.field6311.method7946(var8);
               }

               synchronized (classUJ.field6313) {
                  if (0 == 508619279 * classUJ.field6312) {
                     if (var5 == 685517991) {
                        throw new IllegalStateException();
                     }

                     classMK.field4520 = new Thread(new classUJ());
                     client.method1732(-1);
                     classMK.field4520.setDaemon(true);
                     classMK.field4520.start();
                     classMK.field4520.setPriority(5);
                  }

                  classUJ.field6312 = 69046312;
               }
            }

            IndexDataBase.method11868(this, var2, 1479807699);
            this.method12108(-3211026);
         } else {
            var2[var2.length - 4] = (byte)(this.field6444[var1] >> 24);
            var2[var2.length - 3] = (byte)(this.field6444[var1] >> 16);
            var2[var2.length - 2] = (byte)(this.field6444[var1] >> 8);
            var2[var2.length - 1] = (byte)this.field6444[var1];
            if (null != this.field6538) {
               classUS var19 = this.field6538;
               classVU var20 = new classVU();
               var20.field6605 = 0;
               var20.field6550 = var1;
               var20.field6608 = var2;
               var20.field6606 = var19;
               synchronized (classUJ.field6311) {
                  classUJ.field6311.method7946(var20);
               }

               synchronized (classUJ.field6313) {
                  if (0 == 508619279 * classUJ.field6312) {
                     if (var5 == 685517991) {
                        throw new IllegalStateException();
                     }

                     classMK.field4520 = new Thread(new classUJ());
                     client.method1732(-1);
                     classMK.field4520.setDaemon(true);
                     classMK.field4520.start();
                     classMK.field4520.setPriority(5);
                  }

                  classUJ.field6312 = 69046312;
               }

               this.field6549[var1] = true;
            }

            if (var4) {
               if (var5 == 685517991) {
                  throw new IllegalStateException();
               }

               this.field6451[var1] = classOQ.method8752(var2, false, 1657375579);
            }
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "vp.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ao")
   public int method12109(int var1, int var2) {
      try {
         if (null != this.field6451[var1]) {
            if (var2 >= -324749371) {
               throw new IllegalStateException();
            } else {
               return 100;
            }
         } else if (this.field6549[var1]) {
            if (var2 >= -324749371) {
               throw new IllegalStateException();
            } else {
               return 100;
            }
         } else {
            return this.field6541.method11982(this.field6533 * -169317867, var1, -1779850424);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vp.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("al")
   public boolean method12112(int var1, int var2) {
      try {
         return this.field6549[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vp.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aj")
   public boolean method12113(int var1, int var2) {
      try {
         boolean var10000;
         if (this.method11898(var1, -1777905122) != null) {
            if (var2 != 1100671196) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "vp.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method12085() {
      return 1 == this.field6537 * 1031852869;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method12086() {
      return 1 == this.field6537 * 1031852869;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   @Override
   void vmethod612(int var1) {
      if (null != this.field6538 && null != this.field6549 && this.field6549[var1]) {
         classUS var2 = this.field6538;
         byte[] var4 = null;
         synchronized (classUJ.field6311) {
            for (classVU var6 = (classVU)classUJ.field6311.method7957(); var6 != null; var6 = (classVU)classUJ.field6311.method7965()) {
               if (var6.field6550 == var1 && var6.field6606 == var2 && var6.field6605 * -1312025255 == 0) {
                  var4 = var6.field6608;
                  break;
               }
            }
         }

         if (var4 != null) {
            this.method12119(var2, var1, var4, true, (byte)99);
         } else {
            byte[] var9 = var2.method11587(var1, -1142684788);
            this.method12119(var2, var1, var9, true, (byte)93);
         }
      } else {
         classVB.method11990(this.field6541, this, this.field6533 * -169317867, var1, this.field6450[var1], (byte)4, true, -571323226);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   @Override
   void vmethod611(int var1) {
      if (null != this.field6538 && null != this.field6549 && this.field6549[var1]) {
         classUS var2 = this.field6538;
         byte[] var4 = null;
         synchronized (classUJ.field6311) {
            for (classVU var6 = (classVU)classUJ.field6311.method7957(); var6 != null; var6 = (classVU)classUJ.field6311.method7965()) {
               if (var6.field6550 == var1 && var6.field6606 == var2 && var6.field6605 * -1312025255 == 0) {
                  var4 = var6.field6608;
                  break;
               }
            }
         }

         if (var4 != null) {
            this.method12119(var2, var1, var4, true, (byte)16);
         } else {
            byte[] var9 = var2.method11587(var1, -1142684788);
            this.method12119(var2, var1, var9, true, (byte)27);
         }
      } else {
         classVB.method11990(this.field6541, this, this.field6533 * -169317867, var1, this.field6450[var1], (byte)4, true, -587411731);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("am")
   @Override
   void vmethod613(int var1) {
      if (null != this.field6538 && null != this.field6549 && this.field6549[var1]) {
         classUS var2 = this.field6538;
         byte[] var4 = null;
         synchronized (classUJ.field6311) {
            for (classVU var6 = (classVU)classUJ.field6311.method7957(); var6 != null; var6 = (classVU)classUJ.field6311.method7965()) {
               if (var6.field6550 == var1 && var6.field6606 == var2 && var6.field6605 * -1312025255 == 0) {
                  var4 = var6.field6608;
                  break;
               }
            }
         }

         if (var4 != null) {
            this.method12119(var2, var1, var4, true, (byte)120);
         } else {
            byte[] var9 = var2.method11587(var1, -1142684788);
            this.method12119(var2, var1, var9, true, (byte)120);
         }
      } else {
         classVB.method11990(this.field6541, this, this.field6533 * -169317867, var1, this.field6450[var1], (byte)4, true, -367119212);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   public int method12091() {
      if (this.field6537 * 1031852869 != 1 && (!this.field6535 || 2 != 1031852869 * this.field6537)) {
         if (this.field6451 != null) {
            return 99;
         } else {
            int var1 = this.field6541.method11982(classUM.field6327.field6326 * 1060637953, -169317867 * this.field6533, -1779850424);
            if (var1 >= 100) {
               var1 = 99;
            }

            return var1;
         }
      } else {
         return 100;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   public int method12092() {
      if (this.field6537 * 1031852869 != 1 && (!this.field6535 || 2 != 1031852869 * this.field6537)) {
         if (this.field6451 != null) {
            return 99;
         } else {
            int var1 = this.field6541.method11982(classUM.field6327.field6326 * 1060637953, -169317867 * this.field6533, -1779850424);
            if (var1 >= 100) {
               var1 = 99;
            }

            return var1;
         }
      } else {
         return 100;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("na")
   public void method12095(int var1) {
      classVB.method11992(this.field6541, -169317867 * this.field6533, var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   public void method12096(int var1) {
      classVB.method11992(this.field6541, -169317867 * this.field6533, var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aa")
   void method12108(int var1) {
      try {
         this.field6549 = new boolean[this.field6451.length];

         for (int var2 = 0; var2 < this.field6549.length; var2++) {
            if (var1 >= 16777215) {
               throw new IllegalStateException();
            }

            this.field6549[var2] = false;
         }

         if (null == this.field6538) {
            if (var1 >= 16777215) {
               throw new IllegalStateException();
            } else {
               this.field6537 = 689817485;
            }
         } else {
            this.field6540 = -1916962393;

            for (int var12 = 0; var12 < this.field6549.length; var12++) {
               if (this.field6446[var12] > 0) {
                  if (var1 >= 16777215) {
                     throw new IllegalStateException();
                  }

                  classUS var3 = this.field6538;
                  classVU var5 = new classVU();
                  var5.field6605 = 11068137;
                  var5.field6550 = var12;
                  var5.field6606 = var3;
                  var5.field6607 = this;
                  synchronized (classUJ.field6311) {
                     classUJ.field6311.method7946(var5);
                  }

                  synchronized (classUJ.field6313) {
                     if (0 == 508619279 * classUJ.field6312) {
                        classMK.field4520 = new Thread(new classUJ());
                        client.method1732(-1);
                        classMK.field4520.setDaemon(true);
                        classMK.field4520.start();
                        classMK.field4520.setPriority(5);
                     }

                     classUJ.field6312 = 69046312;
                  }

                  this.field6540 = var12 * 1916962393;
               }
            }

            if (-1 == 929849833 * this.field6540) {
               if (var1 >= 16777215) {
                  throw new IllegalStateException();
               }

               this.field6537 = 689817485;
            }
         }
      } catch (RuntimeException var11) {
         throw classEG.method3899(var11, "vp.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("as")
   void method12100(int var1, int var2, int var3) {
      try {
         this.field6546 = var1 * -1493060365;
         this.field6545 = var2 * 376657241;
         if (this.field6539 != null) {
            if (var3 >= -1055295741) {
               throw new IllegalStateException();
            }

            int var4 = this.field6533 * -169317867;
            classUS var5 = this.field6539;
            byte[] var7 = null;
            synchronized (classUJ.field6311) {
               for (classVU var9 = (classVU)classUJ.field6311.method7957(); var9 != null; var9 = (classVU)classUJ.field6311.method7965()) {
                  if (var9.field6550 == var4) {
                     if (var3 >= -1055295741) {
                        throw new IllegalStateException();
                     }

                     if (var9.field6606 == var5) {
                        if (var3 >= -1055295741) {
                           throw new IllegalStateException();
                        }

                        if (0 == var9.field6605 * -1312025255) {
                           var7 = var9.field6608;
                           break;
                        }
                     }
                  }
               }
            }

            if (null != var7) {
               if (var3 >= -1055295741) {
                  throw new IllegalStateException();
               }

               this.method12119(var5, var4, var7, true, (byte)48);
            } else {
               byte[] var13 = var5.method11587(var4, -1142684788);
               this.method12119(var5, var4, var13, true, (byte)23);
            }
         } else {
            classVB.method11990(
               this.field6541,
               this,
               classUM.field6327.field6326 * 1060637953,
               this.field6533 * -169317867,
               this.field6546 * 1694139451,
               (byte)0,
               true,
               -843457140
            );
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "vp.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method12087() {
      return 1 == this.field6537 * 1031852869;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bn")
   void method12101(int var1, int var2) {
      this.field6546 = var1 * -1493060365;
      this.field6545 = var2 * 376657241;
      if (this.field6539 != null) {
         int var3 = this.field6533 * -169317867;
         classUS var4 = this.field6539;
         byte[] var6 = null;
         synchronized (classUJ.field6311) {
            for (classVU var8 = (classVU)classUJ.field6311.method7957(); var8 != null; var8 = (classVU)classUJ.field6311.method7965()) {
               if (var8.field6550 == var3 && var8.field6606 == var4 && 0 == var8.field6605 * -1312025255) {
                  var6 = var8.field6608;
                  break;
               }
            }
         }

         if (null != var6) {
            this.method12119(var4, var3, var6, true, (byte)106);
         } else {
            byte[] var11 = var4.method11587(var3, -1142684788);
            this.method12119(var4, var3, var11, true, (byte)11);
         }
      } else {
         classVB.method11990(
            this.field6541,
            this,
            classUM.field6327.field6326 * 1060637953,
            this.field6533 * -169317867,
            this.field6546 * 1694139451,
            (byte)0,
            true,
            -1028318273
         );
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bc")
   void method12102(int var1, int var2) {
      this.field6546 = var1 * -1493060365;
      this.field6545 = var2 * 376657241;
      if (this.field6539 != null) {
         int var3 = this.field6533 * -169317867;
         classUS var4 = this.field6539;
         byte[] var6 = null;
         synchronized (classUJ.field6311) {
            for (classVU var8 = (classVU)classUJ.field6311.method7957(); var8 != null; var8 = (classVU)classUJ.field6311.method7965()) {
               if (var8.field6550 == var3 && var8.field6606 == var4 && 0 == var8.field6605 * -1312025255) {
                  var6 = var8.field6608;
                  break;
               }
            }
         }

         if (null != var6) {
            this.method12119(var4, var3, var6, true, (byte)90);
         } else {
            byte[] var11 = var4.method11587(var3, -1142684788);
            this.method12119(var4, var3, var11, true, (byte)11);
         }
      } else {
         classVB.method11990(
            this.field6541, this, classUM.field6327.field6326 * 1060637953, this.field6533 * -169317867, this.field6546 * 1694139451, (byte)0, true, -485351830
         );
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvp;)V")
   @ObfuscatedName("td")
   public static void method12098(classVP var0) {
      if (var0 == null) {
         var0.method12091();
      }

      var0.field6537 = 1379634970;
      var0.field6445 = new int[0];
      var0.field6450 = new int[0];
      var0.field6444 = new int[0];
      var0.field6446 = new int[0];
      var0.field6453 = new int[0][];
      var0.field6451 = new Object[0];
      var0.field6455 = new Object[0][];
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bp")
   void method12103(int var1, int var2) {
      this.field6546 = var1 * -411187916;
      this.field6545 = var2 * -2113858107;
      if (this.field6539 != null) {
         int var3 = this.field6533 * 31712364;
         classUS var4 = this.field6539;
         byte[] var6 = null;
         synchronized (classUJ.field6311) {
            for (classVU var8 = (classVU)classUJ.field6311.method7957(); var8 != null; var8 = (classVU)classUJ.field6311.method7965()) {
               if (var8.field6550 == var3 && var8.field6606 == var4 && 0 == var8.field6605 * -917611729) {
                  var6 = var8.field6608;
                  break;
               }
            }
         }

         if (null != var6) {
            this.method12119(var4, var3, var6, true, (byte)31);
         } else {
            byte[] var11 = var4.method11587(var3, -1142684788);
            this.method12119(var4, var3, var11, true, (byte)73);
         }
      } else {
         classVB.method11990(
            this.field6541, this, classUM.field6327.field6326 * 1295346181, this.field6533 * -169317867, this.field6546 * 594987468, (byte)0, true, -1675479739
         );
      }
   }

   @ObfuscatedSignature(descriptor = "(I[BZZ)V")
   @ObfuscatedName("br")
   void method12106(int var1, byte[] var2, boolean var3, boolean var4) {
      if (var3) {
         if (1 == this.field6537 * 1031852869) {
            throw new RuntimeException();
         }

         if (this.field6539 != null) {
            int var5 = -169317867 * this.field6533;
            classUS var6 = this.field6539;
            classVU var7 = new classVU();
            var7.field6605 = 0;
            var7.field6550 = var5;
            var7.field6608 = var2;
            var7.field6606 = var6;
            synchronized (classUJ.field6311) {
               classUJ.field6311.method7946(var7);
            }

            synchronized (classUJ.field6313) {
               if (0 == 508619279 * classUJ.field6312) {
                  classMK.field4520 = new Thread(new classUJ());
                  classMK.field4520.setDaemon(true);
                  classMK.field4520.start();
                  classMK.field4520.setPriority(5);
               }

               classUJ.field6312 = 69046312;
            }
         }

         IndexDataBase.method11868(this, var2, 1479807699);
         this.method12108(-163465794);
      } else {
         var2[var2.length - 4] = (byte)(this.field6444[var1] >> 24);
         var2[var2.length - 3] = (byte)(this.field6444[var1] >> 16);
         var2[var2.length - 2] = (byte)(this.field6444[var1] >> 8);
         var2[var2.length - 1] = (byte)this.field6444[var1];
         if (null != this.field6538) {
            classUS var17 = this.field6538;
            classVU var18 = new classVU();
            var18.field6605 = 0;
            var18.field6550 = var1;
            var18.field6608 = var2;
            var18.field6606 = var17;
            synchronized (classUJ.field6311) {
               classUJ.field6311.method7946(var18);
            }

            synchronized (classUJ.field6313) {
               if (0 == 508619279 * classUJ.field6312) {
                  classMK.field4520 = new Thread(new classUJ());
                  classMK.field4520.setDaemon(true);
                  classMK.field4520.start();
                  classMK.field4520.setPriority(5);
               }

               classUJ.field6312 = 69046312;
            }

            this.field6549[var1] = true;
         }

         if (var4) {
            this.field6451[var1] = classOQ.method8752(var2, false, 969952615);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lus;I[BZ)V")
   @ObfuscatedName("bm")
   public void method12107(classUS var1, int var2, byte[] var3, boolean var4) {
      if (var1 == this.field6539) {
         if (-1741645360 * this.field6537 == 1) {
            throw new RuntimeException();
         } else if (null == var3) {
            classVB.method11990(
               this.field6541,
               this,
               -2120889335 * classUM.field6327.field6326,
               -169317867 * this.field6533,
               1694139451 * this.field6546,
               (byte)0,
               true,
               -1272075715
            );
         } else {
            field6531.reset();
            field6531.update(var3, 0, var3.length);
            int var9 = (int)field6531.getValue();
            if (var9 != this.field6546 * -628655536) {
               classVB.method11990(
                  this.field6541,
                  this,
                  classUM.field6327.field6326 * 1060637953,
                  -169317867 * this.field6533,
                  this.field6546 * 1694139451,
                  (byte)0,
                  true,
                  -1295882108
               );
            } else {
               classXY var10 = new classXY(classMD.method7581(var3, (byte)113));
               int var7 = var10.method13095(-346779531);
               if (var7 < 5 || var7 > 7) {
                  throw new RuntimeException(var7 + "," + this.field6533 * 915858580 + "," + var2);
               } else {
                  int var8 = 0;
                  if (var7 >= 6) {
                     var8 = classXY.method13113(var10, (byte)1);
                  }

                  if (var8 != 723372223 * this.field6545) {
                     classVB.method11990(
                        this.field6541,
                        this,
                        -1063587421 * classUM.field6327.field6326,
                        this.field6533 * -169317867,
                        this.field6546 * 180990173,
                        (byte)0,
                        true,
                        -1653106887
                     );
                  } else {
                     IndexDataBase.method11868(this, var3, 1479807699);
                     this.method12108(3383084);
                  }
               }
            }
         }
      } else {
         if (!var4 && 929849833 * this.field6540 == var2) {
            this.field6537 = -1765763023;
         }

         if (null != var3 && var3.length > 4) {
            field6531.reset();
            field6531.update(var3, 0, var3.length - 4);
            int var5 = (int)field6531.getValue();
            int var6 = (var3[var3.length - 1] & 255)
               + ((var3[var3.length - 2] & 1843389681) << 8)
               + ((var3[var3.length - 4] & -1767763752) << 24)
               + ((var3[var3.length - 3] & 1525934998) << 16);
            if (var5 != this.field6450[var2] || var6 != this.field6444[var2]) {
               field6531.reset();
               field6531.update(var3, 0, var3.length - 2);
               var5 = (int)field6531.getValue();
               var6 = (var3[var3.length - 1] & -755679546) + ((var3[var3.length - 2] & 1620932388) << 8);
            }

            if (this.field6450[var2] == var5 && this.field6444[var2] == var6) {
               this.field6549[var2] = true;
               if (var4) {
                  this.field6451[var2] = classOQ.method8752(var3, false, -1452292921);
               }
            } else {
               this.field6549[var2] = false;
               if (this.field6536 || var4) {
                  classVB.method11990(this.field6541, this, -169317867 * this.field6533, var2, this.field6450[var2], (byte)4, var4, -1672103699);
               }
            }
         } else {
            this.field6549[var2] = false;
            if (this.field6536 || var4) {
               classVB.method11990(this.field6541, this, this.field6533 * -1929466463, var2, this.field6450[var2], (byte)4, var4, -1944941817);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("oi")
   public int method12125() {
      return this.field6533 * -169317867;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bt")
   public int method12110(int var1) {
      if (null != this.field6451[var1]) {
         return 100;
      } else {
         return this.field6549[var1] ? 100 : this.field6541.method11982(this.field6533 * -1219249535, var1, -1779850424);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lus;I[BZB)V")
   @ObfuscatedName("ac")
   public void method12119(classUS var1, int var2, byte[] var3, boolean var4, byte var5) {
      if (var1 == this.field6539) {
         if (this.field6537 * 1031852869 == 1) {
            throw new RuntimeException();
         }

         if (var3 == null) {
            this.field6541.method11994(this, 255, this.field6533 * -169317867, this.field6546 * 1694139451, (byte)0, true);
         } else {
            int var6;
            synchronized (field6531) {
               field6531.reset();
               field6531.update(var3, 0, var3.length);
               var6 = (int)field6531.getValue();
            }

            if (var6 != this.field6546 * 1694139451) {
               this.field6541.method11994(this, 255, this.field6533 * -169317867, this.field6546 * 1694139451, (byte)0, true);
            } else {
               classXY var16 = new classXY(method12124(var3));
               int var8 = var16.method13322();
               if (var8 < 5 || var8 > 7) {
                  throw new RuntimeException(var8 + "," + this.field6533 * -169317867 + "," + var2);
               }

               int var9 = 0;
               if (var8 >= 6) {
                  var9 = var16.method13325();
               }

               if (var9 != this.field6545 * -879910167) {
                  this.field6541.method11994(this, 255, this.field6533 * -169317867, this.field6546 * 1694139451, (byte)0, true);
               } else {
                  this.method12127(var3);
                  this.method12126();
               }
            }
         }
      } else {
         if (!var4 && var2 == this.field6540 * 929849833) {
            this.field6537 = 1 * 689817485;
         }

         if (var3 != null && var3.length > 4) {
            int var15;
            synchronized (field6531) {
               field6531.reset();
               field6531.update(var3, 0, var3.length - 4);
               var15 = (int)field6531.getValue();
            }

            int var18 = (var3[var3.length - 4] & 255) << 24
               | (var3[var3.length - 3] & 255) << 16
               | (var3[var3.length - 2] & 255) << 8
               | var3[var3.length - 1] & 255;
            if (var15 != super.field6450[var2] || var18 != super.field6444[var2]) {
               synchronized (field6531) {
                  field6531.reset();
                  field6531.update(var3, 0, var3.length - 2);
                  var15 = (int)field6531.getValue();
               }

               var18 = ((var3[var3.length - 2] & 255) << 8) + (var3[var3.length - 1] & 255);
            }

            if (var15 == super.field6450[var2] && var18 == super.field6444[var2]) {
               this.field6549[var2] = true;
               if (var4) {
                  assert super.field6451[var2] == null;

                  super.field6451[var2] = method12120(var3, false);
               }
            } else {
               this.field6549[var2] = false;
               if (this.field6536 || var4) {
                  this.field6541.method11994(this, this.field6533 * -169317867, var2, super.field6450[var2], (byte)4, var4);
               }
            }
         } else {
            this.field6549[var2] = false;
            if (this.field6536 || var4) {
               this.field6541.method11994(this, this.field6533 * -169317867, var2, super.field6450[var2], (byte)4, var4);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bj")
   public boolean method12114(int var1) {
      return this.method11898(var1, -1637457958) != null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mk")
   public void method12126() {
      this.method12108(-1610851517);
   }

   public classVP(classUS var1, classUS var2, classVB var3, int var4, boolean var5, boolean var6, boolean var7, boolean var8, boolean var9) {
      super(var5, var6);
      this.field6537 = 0;
      this.field6536 = false;
      this.field6540 = -1916962393;
      this.field6538 = var1;
      this.field6539 = var2;
      this.field6533 = var4 * -50391747;
      this.field6536 = var7;
      this.field6535 = var8;
      this.field6534 = var9;
      this.field6541 = var3;
      classVB.method11969(this.field6541, this, -169317867 * this.field6533, 2124696322);
      this.method12121(var1, var2, var3, var4, var5, var6, var7);
   }

   @ObfuscatedSignature(descriptor = "([B)V")
   @ObfuscatedName("oi")
   public void method12127(byte[] var1) {
      IndexDataBase.method11868(this, var1, 1479807699);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   public int method12118() {
      byte var1 = 0;
      int var2 = 0;

      for (int var3 = 0; var3 < this.field6451.length; var3++) {
         if (this.field6446[var3] > 0) {
            var1 += 100;
            var2 += this.method12109(var3, -774554380);
         }
      }

      return 0 == var1 ? -2011217011 : 756422341 * var2 / var1;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   public boolean method12088(byte var1) {
      try {
         return 1 == this.field6537 * 1031852869;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "vp.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bx")
   void method12104(int var1, int var2) {
      this.field6546 = var1 * -1493060365;
      this.field6546 = var2 * 376657241;
      if (this.field6539 != null) {
         int var3 = this.field6540 * 1618452037;
         classUS var4 = this.field6539;
         byte[] var6 = null;
         synchronized (classUJ.field6311) {
            for (classVU var8 = (classVU)classUJ.field6311.method7959(); var8 != null; var8 = (classVU)classUJ.field6311.method7959()) {
               if (var8.field6550 == var3 && var8.field6606 == var4 && 0 == var8.field6605 * -1312025255) {
                  var6 = var8.field6608;
                  break;
               }
            }
         }

         if (null != var6) {
            this.method12119(var4, var3, var6, true, (byte)112);
         } else {
            byte[] var11 = var4.method11587(var3, -1142684788);
            this.method12119(var4, var3, var11, true, (byte)6);
         }
      } else {
         classVB.method11990(
            this.field6541,
            this,
            classUM.field6347.field6326 * -1661953164,
            this.field6533 * -722774945,
            this.field6546 * -2057620979,
            (byte)0,
            true,
            -1183449495
         );
      }
   }
}
