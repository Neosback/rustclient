import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lh")
public class classLH {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("aa")
   public static IndexDataBase field4166;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field4161 = 0;
   @ObfuscatedName("av")
   int field4150;
   @ObfuscatedName("am")
   int field4151;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field4162 = 0;
   @ObfuscatedSignature(descriptor = "Lzd;")
   @ObfuscatedName("ar")
   classZD field4157 = new classZD();
   @ObfuscatedSignature(descriptor = "Lzd;")
   @ObfuscatedName("ai")
   classZD field4142 = new classZD();
   @ObfuscatedName("aw")
   boolean field4141 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4159 = 2;
   @ObfuscatedName("br")
   int field4148;
   @ObfuscatedName("at")
   boolean field4145;
   @ObfuscatedName("bt")
   int field4146;
   @ObfuscatedName("bp")
   int field4147;
   @ObfuscatedName("bj")
   int field4158;
   @ObfuscatedName("bc")
   int field4153;
   @ObfuscatedSignature(descriptor = "Llm;")
   @ObfuscatedName("bo")
   classLM field4164;
   @ObfuscatedName("bg")
   int field4149;
   @ObfuscatedName("bx")
   int field4154;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4163 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4160 = 1;
   @ObfuscatedName("bk")
   int field4156;
   @ObfuscatedName("an")
   int field4140;
   @ObfuscatedName("bn")
   int field4152;
   @ObfuscatedName("ak")
   boolean field4143 = true;
   @ObfuscatedName("bm")
   int field4144 = 0;
   @ObfuscatedSignature(descriptor = "Llm;")
   @ObfuscatedName("bu")
   classLM field4165;
   @ObfuscatedName("ah")
   int field4155;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("js")
   public int method7064() {
      return this.field4157.method13982(-2008736246);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fh")
   public void method6925() {
      this.method6930(0, classZD.method13896(this.field4157, -130672433), 1122842785);
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("km")
   void method7113(int var1, boolean var2) {
      if (var2) {
         this.method6930(1072642345 * this.field4150, var1, 1122842785);
      } else {
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("cb")
   public boolean method7043(byte var1) {
      try {
         return this.field4145;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("aw")
   public void method6918(int var1) {
      try {
         if (!this.method6924(455461300)) {
            if (var1 == 1818385) {
               throw new IllegalStateException();
            }

            if (-698145269 * this.field4155 < classZD.method13896(this.field4157, 2092781701)) {
               classWB var2 = this.method7102(-698145269 * this.field4155, 1700027085);
               int var3 = this.field4157.method13935(this.field4155 * -698145269, (Integer)var2.field6653, 1228397626);
               method7133(this, (byte)94);
               this.method6930(var3, var3, 1122842785);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.aw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fn")
   public void method6931(boolean var1) {
      classWB var2 = this.method7106(this.field4155 * -1951118393, -473099497);
      this.method7114((Integer)var2.field6652, var1, -1999332591);
   }

   @ObfuscatedSignature(descriptor = "(Llh;IB)Z")
   @ObfuscatedName("ai")
   public static boolean method6877(classLH var0, int var1, byte var2) {
      if (var0 == null) {
         return var0.method6882(var1, var2);
      } else {
         try {
            var0.field4140 = 1211480737 * var1;
            if (var0.method7111((byte)-78)) {
               if (var2 == 0) {
                  throw new IllegalStateException();
               } else {
                  method7133(var0, (byte)33);
                  return true;
               }
            } else {
               return false;
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lh.ao(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)V")
   @ObfuscatedName("oe")
   public static void method7083(classLH var0, int var1) {
      var0.field4157.method13902(var1, 732616076);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("fs")
   public boolean method6869(int var1, int var2) {
      try {
         if (var1 < 0) {
            if (var2 >= 897355686) {
               throw new IllegalStateException();
            }

            var1 = Integer.MAX_VALUE;
         }

         boolean var10000;
         if (var1 == classZD.method13965(this.field4157, 347520185)) {
            if (var2 >= 897355686) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var3 = var10000;
         this.field4157.method13970(var1, -2107277976);
         this.field4142.method13970(var1, -2089009959);
         if (this.method7111((byte)-54)) {
            if (var2 >= 897355686) {
               throw new IllegalStateException();
            }

            method7133(this, (byte)55);
            var3 = true;
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("al")
   public boolean method6884(int var1, int var2, int var3) {
      try {
         if (!this.method7043((byte)-48)) {
            if (var3 >= 869659365) {
               throw new IllegalStateException();
            } else {
               this.field4152 = var1 * -942303129;
               this.field4158 = var2 * -1673193747;
               return false;
            }
         } else {
            int var4 = -1184890025 * this.field4152;
            int var5 = -457263387 * this.field4158;
            int var6 = Math.max(0, this.field4157.method13947((byte)-74) - this.field4156 * 2084178789 + 2);
            int var7 = Math.max(0, classZD.method13949(this.field4157, (byte)23) - 236265915 * this.field4151 + 1);
            this.field4152 = Math.max(0, Math.min(var6, var1)) * -942303129;
            this.field4158 = Math.max(0, Math.min(var7, var2)) * -1673193747;
            if (var4 == this.field4152 * -1184890025) {
               if (var3 >= 869659365) {
                  throw new IllegalStateException();
               }

               if (-457263387 * this.field4158 == var5) {
                  return false;
               }

               if (var3 >= 869659365) {
                  throw new IllegalStateException();
               }
            }

            return true;
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lh.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("df")
   public boolean method6870(int var1) {
      if (var1 < 0) {
         var1 = Integer.MAX_VALUE;
      }

      boolean var2 = var1 == classZD.method13965(this.field4157, 1755643114);
      this.field4157.method13970(var1, -2052920692);
      this.field4142.method13970(var1, -2040748860);
      if (this.method7111((byte)-44)) {
         method7133(this, (byte)114);
         var2 = true;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ay")
   public void method6890(int var1, int var2) {
      try {
         this.field4157.method13913(var1, (byte)116);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("do")
   public boolean method6895(int var1, int var2) {
      try {
         this.field4148 = var1 * -1502904977;
         String var3 = this.field4157.method13899((byte)-99);
         int var4 = var3.length();
         var3 = method7095(this, var3, 1949274329);
         if (var3.length() != var4) {
            if (var2 == -136218518) {
               throw new IllegalStateException();
            } else {
               this.field4157.method13931(var3, 903945741);
               this.method6884(this.field4152 * -1184890025, this.field4158 * -457263387, -1496690152);
               this.method7111((byte)-64);
               method7133(this, (byte)67);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("es")
   public boolean method6878(int var1) {
      this.field4140 = 1211480737 * var1;
      if (this.method7111((byte)-128)) {
         method7133(this, (byte)58);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ef")
   public void method6899() {
      this.field4145 = false;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ai")
   public void method6913(byte var1) {
      try {
         if (!this.method6924(455461300)) {
            if (var1 <= 2) {
               throw new IllegalStateException();
            }

            if (this.field4155 * -698145269 < classZD.method13896(this.field4157, 1950622253)) {
               if (var1 <= 2) {
                  return;
               }

               int var2 = this.field4157.method13934(this.field4155 * -698145269, -1596338929);
               method7133(this, (byte)38);
               this.method6930(var2, var2, 1122842785);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ai(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Lwb;Lwb;)V")
   @ObfuscatedName("tr")
   public static void method7098(classLH var0, classWB var1, classWB var2) {
      if (var0 == null) {
         var0.method7112();
      }

      if ((Integer)var2.field6652 < (Integer)var1.field6652) {
         var0.method6930((Integer)var1.field6653, (Integer)var2.field6652, 1122842785);
      } else {
         var0.method6930((Integer)var1.field6652, (Integer)var2.field6653, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)I")
   @ObfuscatedName("kp")
   public static int method7118(classLH var0, int var1) {
      if (var0 == null) {
         return var0.method7122(var1);
      } else {
         try {
            return 236265915 * var0.field4151 / classZD.method13953(var0.field4157, (byte)-26);
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lh.cx(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ar")
   public void method6917(byte var1) {
      try {
         if (!this.method6924(455461300)) {
            if (var1 == 2) {
               return;
            }

            if (this.field4155 * -698145269 > 0) {
               if (var1 == 2) {
                  throw new IllegalStateException();
               }

               classWB var2 = this.method7102(-698145269 * this.field4155 - 1, 1573931620);
               int var3 = this.field4157.method13935((Integer)var2.field6652, this.field4155 * -698145269, 1205805656);
               method7133(this, (byte)109);
               this.method6930(var3, var3, 1122842785);
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;ZB)V")
   @ObfuscatedName("oq")
   public static void method6952(classLH var0, boolean var1, byte var2) {
      if (var0 == null) {
         var0.method6953(var1, var2);
      }

      try {
         if (var0.method7089(2012791869)) {
            if (var2 <= 10) {
               throw new IllegalStateException();
            }

            if (!var1) {
               var0.method7114(Math.max(var0.field4150 * 1072642345, -698145269 * var0.field4155), var1, -1470377285);
               return;
            }
         }

         if (var0.field4155 * -698145269 < classZD.method13896(var0.field4157, 69845744)) {
            if (var2 <= 10) {
               throw new IllegalStateException();
            }

            var0.method7114(1 + -698145269 * var0.field4155, var1, -1257110877);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llm;)V")
   @ObfuscatedName("hh")
   public void method7013(classLM var1) {
      this.field4165 = var1;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("av")
   public void method6926(byte var1) {
      try {
         this.method6930(0, classZD.method13896(this.field4157, 87000098), 1122842785);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.av(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cn")
   boolean method7089(int var1) {
      try {
         boolean var10000;
         if (this.field4155 * -698145269 != this.field4150 * 1072642345) {
            if (var1 != 2012791869) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("ah")
   public void method6932(boolean var1, byte var2) {
      try {
         classWB var3 = this.method7106(this.field4155 * -698145269, -2100457842);
         this.method7114((Integer)var3.field6652, var1, -2102365134);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.an(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gs")
   public void method6972(boolean var1) {
      if (this.field4155 * -698145269 > 0) {
         int var2 = method7118(this, 2101998004);
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, -var2, -1901213098), var1, -2109015603);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)V")
   @ObfuscatedName("yn")
   public static void method6907(classLH var0, int var1) {
      if (var0 == null) {
         var0.method6912(var1);
      } else {
         try {
            if (!var0.method6924(455461300)) {
               if (var1 >= -1927903443) {
                  throw new IllegalStateException();
               }

               if (-698145269 * var0.field4155 > 0) {
                  if (var1 >= -1927903443) {
                     throw new IllegalStateException();
                  }

                  int var2 = var0.field4157.method13934(var0.field4155 * -698145269 - 1, -1596338929);
                  method7133(var0, (byte)75);
                  var0.method6930(var2, var2, 1122842785);
               }
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lh.au(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bn")
   public void method6947(boolean var1, int var2) {
      try {
         if (this.method7089(2012791869)) {
            if (var2 == -1751455896) {
               throw new IllegalStateException();
            }

            if (!var1) {
               this.method7114(Math.min(this.field4150 * 1072642345, -698145269 * this.field4155), var1, -2137200963);
               return;
            }
         }

         if (-698145269 * this.field4155 > 0) {
            this.method7114(this.field4155 * -698145269 - 1, var1, -1469394953);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bx(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lzd;")
   @ObfuscatedName("hc")
   public classZD method7019() {
      return this.field4142;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Ljava/lang/String;)Z")
   @ObfuscatedName("at")
   public static boolean method6857(classLH var0, String var1) {
      var0.field4142.method13931(var1, 900459530);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("bp")
   public void method6960(boolean var1, byte var2) {
      try {
         if (this.field4155 * -698145269 > 0) {
            if (var2 == 1) {
               return;
            }

            this.method7114(this.field4157.method13942(this.field4155 * -698145269, -1, -600257831), var1, -1831247593);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bm(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("se")
   public static boolean method7127(classLH var0, int var1) {
      if (var0 == null) {
         var0.method6889(var1, var1, var1);
      }

      return 32 == var1 || var1 == 10 || var1 == 9;
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bx")
   public void method6973(boolean var1, int var2) {
      try {
         if (this.field4155 * -698145269 > 0) {
            if (var2 <= -565590623) {
               return;
            }

            int var3 = method7118(this, 1731156472);
            this.method7114(this.field4157.method13942(this.field4155 * -698145269, -var3, -656796446), var1, -998892416);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("br")
   public void method6975(boolean var1, int var2) {
      try {
         if (this.field4155 * -698145269 < classZD.method13896(this.field4157, -540295366)) {
            if (var2 != 988636327) {
               throw new IllegalStateException();
            }

            int var3 = method7118(this, 1715761966);
            this.method7114(this.field4157.method13942(this.field4155 * -698145269, var3, -1245485915), var1, -1104674755);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bj")
   public void method6977(boolean var1, int var2) {
      try {
         classAAG var3 = this.field4157.method13915(0, this.field4155 * -698145269, 1003674895);
         classWB var4 = classAAG.method213(var3, (byte)-25);
         this.method7114(this.field4157.method13939((Integer)var4.field6652, this.field4158 * -457263387, -683973827), var1, -1667191298);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ae")
   public void method6982(boolean var1, int var2) {
      try {
         classAAG var3 = this.field4157.method13915(0, -698145269 * this.field4155, 740848598);
         classWB var4 = classAAG.method213(var3, (byte)-50);
         this.method7114(
            this.field4157.method13939((Integer)var4.field6652, 236265915 * this.field4151 + this.field4158 * -457263387, -1610974692), var1, -2063257757
         );
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.bo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIZZI)V")
   @ObfuscatedName("bu")
   public void method6987(int var1, int var2, boolean var3, boolean var4, int var5) {
      try {
         int var6 = 0;
         if (!this.field4145) {
            if (var5 != 14704747) {
               return;
            }

            var6 = 0;
         } else {
            var1 += this.field4152 * -1184890025;
            var2 += -457263387 * this.field4158;
            classWB var7 = method7139(this, -1079082866);
            var6 = this.field4157.method13939(var1 - (Integer)var7.field6652, var2 - (Integer)var7.field6653, -889448140);
         }

         if (var3) {
            if (var5 != 14704747) {
               throw new IllegalStateException();
            }

            if (var4) {
               if (var5 != 14704747) {
                  throw new IllegalStateException();
               }

               this.field4147 = 328689669;
               classWB var16 = this.method7102(var6, 1531811288);
               classWB var8 = this.method7102(this.field4149 * -1558750221, 1656527774);
               method7099(this, var8, var16, 1801667578);
               return;
            }
         }

         if (var3) {
            this.field4147 = 328689669;
            classWB var13 = this.method7102(var6, 941917352);
            this.method6930((Integer)var13.field6652, (Integer)var13.field6653, 1122842785);
            this.field4149 = (Integer)var13.field6652 * -2118535877;
         } else if (var4) {
            this.method6930(this.field4149 * -1558750221, var6, 1122842785);
         } else {
            label69: {
               if (this.field4154 * 1398532195 > 0) {
                  if (var5 != 14704747) {
                     throw new IllegalStateException();
                  }

                  if (-1558750221 * this.field4149 == var6) {
                     if (var5 != 14704747) {
                        throw new IllegalStateException();
                     }

                     if (1072642345 * this.field4150 == -698145269 * this.field4155) {
                        if (var5 != 14704747) {
                           throw new IllegalStateException();
                        }

                        this.field4147 = 328689669;
                        classWB var14 = this.method7102(var6, 1840928457);
                        this.method6930((Integer)var14.field6652, (Integer)var14.field6653, 1122842785);
                     } else {
                        this.field4147 = 657379338;
                        classWB var15 = this.method7106(var6, -798068502);
                        this.method6930((Integer)var15.field6652, (Integer)var15.field6653, 1122842785);
                     }
                     break label69;
                  }
               }

               this.field4147 = 0;
               this.method6930(var6, var6, 1122842785);
               this.field4149 = -2118535877 * var6;
            }

            this.field4154 = -745916333;
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "lh.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;I)V")
   @ObfuscatedName("bw")
   public void method6995(Clipboard var1, int var2) {
      try {
         classAAG var3 = this.field4157.method13915(this.field4150 * 1072642345, this.field4155 * -698145269, 574211831);
         if (!var3.method200(760872983)) {
            if (var2 != -887645341) {
               throw new IllegalStateException();
            }

            String var4 = var3.method193(1486656855);
            if (!var4.isEmpty()) {
               if (var2 != -887645341) {
                  throw new IllegalStateException();
               }

               var1.setContents(new StringSelection(var4), null);
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.bw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;I)V")
   @ObfuscatedName("bb")
   public void method6998(Clipboard var1, int var2) {
      try {
         if (this.method7089(2012791869)) {
            this.method6995(var1, -887645341);
            this.method6924(455461300);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bb(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("be")
   public void method7004(byte var1) {
      try {
         this.field4158 = Math.max(0, this.field4158 * -457263387 - classZD.method13953(this.field4157, (byte)54)) * -1673193747;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.be(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("gh")
   public static void method6966(classLH var0, boolean var1) {
      if (var0.field4155 * 682319188 < classZD.method13896(var0.field4157, -1426061732)) {
         var0.method7114(var0.field4157.method13942(var0.field4155 * -306933806, 1, -1120610461), var1, -1294314322);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llm;B)V")
   @ObfuscatedName("bq")
   public void method7009(classLM var1, byte var2) {
      try {
         this.field4164 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzd;")
   @ObfuscatedName("bs")
   public classZD method7017(int var1) {
      try {
         return this.field4157;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.bs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lzd;")
   @ObfuscatedName("bd")
   public classZD method7020(int var1) {
      try {
         return this.field4142;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.bd(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;IIZZ)V")
   @ObfuscatedName("oo")
   public static void method6988(classLH var0, int var1, int var2, boolean var3, boolean var4) {
      if (var0 == null) {
         var0.method6919();
      }

      int var5 = 0;
      if (!var0.field4145) {
         var5 = 0;
      } else {
         var1 += var0.field4152 * -1184890025;
         var2 += -457263387 * var0.field4158;
         classWB var6 = method7139(var0, -1079082866);
         var5 = var0.field4157.method13939(var1 - (Integer)var6.field6652, var2 - (Integer)var6.field6653, -4008281);
      }

      if (var3 && var4) {
         var0.field4147 = 328689669;
         classWB var14 = var0.method7102(var5, 1766856383);
         classWB var7 = var0.method7102(var0.field4149 * -1558750221, 788898853);
         method7099(var0, var7, var14, 1801667578);
      } else if (var3) {
         var0.field4147 = 328689669;
         classWB var11 = var0.method7102(var5, 1851460603);
         var0.method6930((Integer)var11.field6652, (Integer)var11.field6653, 1122842785);
         var0.field4149 = (Integer)var11.field6652 * -2118535877;
      } else if (var4) {
         var0.method6930(var0.field4149 * -1558750221, var5, 1122842785);
      } else {
         if (var0.field4154 * 1398532195 <= 0 || -1558750221 * var0.field4149 != var5) {
            var0.field4147 = 0;
            var0.method6930(var5, var5, 1122842785);
            var0.field4149 = -2118535877 * var5;
         } else if (1072642345 * var0.field4150 == -698145269 * var0.field4155) {
            var0.field4147 = 328689669;
            classWB var12 = var0.method7102(var5, 1266328072);
            var0.method6930((Integer)var12.field6652, (Integer)var12.field6653, 1122842785);
         } else {
            var0.field4147 = 657379338;
            classWB var13 = var0.method7106(var5, -439983550);
            var0.method6930((Integer)var13.field6652, (Integer)var13.field6653, 1122842785);
         }

         var0.field4154 = -745916333;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Laag;")
   @ObfuscatedName("bl")
   public classAAG method7023(int var1) {
      try {
         return this.field4157.method13915(this.field4150 * 1072642345, this.field4155 * -698145269, 176225300);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.bl(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bv")
   public boolean method7027(int var1) {
      try {
         return this.field4141;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.bv(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;Lwb;Lwb;I)V")
   @ObfuscatedName("ua")
   public static void method7099(classLH var0, classWB var1, classWB var2, int var3) {
      if (var0 == null) {
         var0.method7100(var1, var1, var3);
      }

      try {
         if ((Integer)var2.field6652 < (Integer)var1.field6652) {
            if (var3 != 1801667578) {
               throw new IllegalStateException();
            }

            var0.method6930((Integer)var1.field6653, (Integer)var2.field6652, 1122842785);
         } else {
            var0.method6930((Integer)var1.field6652, (Integer)var2.field6653, 1122842785);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.cz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fv")
   public void method6933(boolean var1) {
      classWB var2 = this.method7106(this.field4155 * -698145269, -1688894818);
      this.method7114((Integer)var2.field6652, var1, -1368753634);
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("bz")
   public boolean method7034(byte var1) {
      try {
         if (this.method7027(1172600452)) {
            if (var1 != 3) {
               throw new IllegalStateException();
            }

            if (-1569689927 * this.field4144 % 60 < 30) {
               if (var1 != 3) {
                  throw new IllegalStateException();
               }

               return true;
            }
         }

         return false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.bz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bt")
   public void method6848(boolean var1, int var2) {
      try {
         boolean var10001;
         label24: {
            this.field4143 = var1;
            if (var1) {
               if (var2 == -531711453) {
                  throw new IllegalStateException();
               }

               if (this.field4141) {
                  if (var2 == -531711453) {
                     throw new IllegalStateException();
                  }

                  var10001 = true;
                  break label24;
               }
            }

            var10001 = false;
         }

         this.field4141 = var10001;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)I")
   @ObfuscatedName("qx")
   public static int method7045(classLH var0, int var1) {
      if (var0 == null) {
         return var0.method7046(var1);
      } else {
         try {
            return -1184890025 * var0.field4152;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lh.ct(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Ljava/lang/String;")
   @ObfuscatedName("ag")
   static final String method7144(int var0, int var1) {
      try {
         if (var0 < 100000) {
            if (var1 <= 781150786) {
               throw new IllegalStateException();
            } else {
               return "<col=ffff00>" + var0 + "</col>";
            }
         } else if (var0 < 10000000) {
            if (var1 <= 781150786) {
               throw new IllegalStateException();
            } else {
               return "<col=ffffff>" + var0 / 1000 + classKK.field3804 + "</col>";
            }
         } else {
            return "<col=00ff80>" + var0 / 1000000 + classKK.field3785 + "</col>";
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("df")
   public static boolean method6873(classLH var0, int var1) {
      if (var0 == null) {
         return var0.method6875(var1);
      } else {
         var0.field4157.method13978(var1, (byte)-54);
         if (var0.method7111((byte)-92)) {
            method7133(var0, (byte)71);
            return true;
         } else {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ff")
   public void method6934(boolean var1) {
      classWB var2 = this.method7106(-698145269 * this.field4155, -1023878629);
      this.method7114((Integer)var2.field6653, var1, -1724040841);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("li")
   boolean method7128(int var1) {
      return 32 == var1 || var1 == 10 || var1 == 9;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ct")
   public int method7055(int var1) {
      try {
         return this.field4157.method13974(-1980073237);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cl(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("am")
   public void method6945(boolean var1, int var2) {
      try {
         this.method7114(classZD.method13896(this.field4157, 206174522), var1, -1593943994);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bn(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("qm")
   public static void method6954(classLH var0, boolean var1) {
      if (-698145269 * var0.field4155 > 0) {
         classWB var2 = var0.method7102(var0.field4155 * -698145269 - 1, 1662927646);
         var0.method7114((Integer)var2.field6652, var1, -1466259815);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cu")
   public int method7058(int var1) {
      try {
         return this.field4146 * 747182635;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cv(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("cs")
   public void method7060(int var1, byte var2) {
      try {
         this.field4146 = -1971006333 * var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.cs(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cv")
   public int method7065(int var1) {
      try {
         return this.field4157.method13982(1715472693);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cy(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ck")
   public int method7074(byte var1) {
      try {
         return -1136623217 * this.field4148;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.ck(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Laag;")
   @ObfuscatedName("ix")
   public classAAG method7024() {
      return this.field4157.method13915(this.field4150 * 1072642345, this.field4155 * -698145269, -33115129);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)Z")
   @ObfuscatedName("qy")
   public static boolean method6844(classLH var0, boolean var1) {
      if (var0 == null) {
         var0.method7067();
      }

      var1 = var1 && var0.field4143;
      boolean var2 = var1 != var0.field4141;
      var0.field4141 = var1;
      if (!var0.field4141) {
         var0.method6930(var0.field4155 * -698145269, var0.field4155 * -698145269, 1122842785);
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("cc")
   public void method7084(int var1, int var2) {
      try {
         this.field4157.method13902(var1, 1691814797);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.cc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("cf")
   public boolean method7086(byte var1) {
      try {
         boolean var10000;
         if (this.method7055(-2118783281) > 1) {
            if (var1 != 0) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("dg")
   public boolean method6887(int var1, int var2) {
      boolean var3 = true;
      if (var1 < 0 || var1 > 2) {
         var3 = false;
      }

      if (var2 < 0 || var2 > 2) {
         var3 = false;
      }

      return var3 ? classZD.method13910(this.field4157, var1, var2, 64272116) : false;
   }

   @ObfuscatedSignature(descriptor = "(II)Lwb;")
   @ObfuscatedName("cg")
   classWB method7102(int var1, int var2) {
      try {
         int var3 = classZD.method13896(this.field4157, -466004839);
         int var4 = 0;
         int var5 = var3;

         for (int var6 = var1; var6 > 0; var6--) {
            if (var2 <= 778248094) {
               throw new IllegalStateException();
            }

            if (this.method7129(this.field4157.method13883(var6 - 1, -1052717093).field117, -1953563735)) {
               var4 = var6;
               break;
            }
         }

         for (int var8 = var1; var8 < var3; var8++) {
            if (var2 <= 778248094) {
               throw new IllegalStateException();
            }

            if (this.method7129(this.field4157.method13883(var8, -1052717093).field117, 719843947)) {
               var5 = var8;
               break;
            }
         }

         return new classWB(var4, var5);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lh.cg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lwb;")
   @ObfuscatedName("cw")
   classWB method7106(int var1, int var2) {
      try {
         int var3 = classZD.method13896(this.field4157, 1076947893);
         int var4 = 0;
         int var5 = var3;

         for (int var6 = var1; var6 > 0; var6--) {
            if (var2 >= -324749371) {
               throw new IllegalStateException();
            }

            if (this.field4157.method13883(var6 - 1, -1052717093).field117 == '\n') {
               if (var2 >= -324749371) {
                  throw new IllegalStateException();
               }

               var4 = var6;
               break;
            }
         }

         for (int var8 = var1; var8 < var3; var8++) {
            if (var2 >= -324749371) {
               throw new IllegalStateException();
            }

            if (this.field4157.method13883(var8, -1052717093).field117 == '\n') {
               var5 = var8;
               break;
            }
         }

         return new classWB(var4, var5);
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lh.cw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("cj")
   boolean method7111(byte var1) {
      try {
         if (!this.method7043((byte)-72)) {
            return false;
         } else {
            boolean var2 = false;
            if (classZD.method13896(this.field4157, 797760493) > this.field4140 * 1701882209) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               this.field4157.method13935(this.field4140 * 1701882209, classZD.method13896(this.field4157, 457927206), 2091425062);
               var2 = true;
            }

            int var3 = this.method7055(-2118783281);
            if (this.field4157.method13958(-97436395) > var3) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               int var4 = this.field4157.method13942(0, var3, -1456277372) - 1;
               this.field4157.method13935(var4, classZD.method13896(this.field4157, 632060943), 1020210013);
               var2 = true;
            }

            if (var2) {
               if (var1 >= 0) {
                  throw new IllegalStateException();
               }

               int var8 = -698145269 * this.field4155;
               int var5 = 1072642345 * this.field4150;
               int var6 = classZD.method13896(this.field4157, 9500134);
               if (this.field4155 * -698145269 > var6) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  var8 = var6;
               }

               if (1072642345 * this.field4150 > var6) {
                  if (var1 >= 0) {
                     throw new IllegalStateException();
                  }

                  var5 = var6;
               }

               this.method6930(var5, var8, 1122842785);
            }

            return var2;
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lh.cj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IZI)V")
   @ObfuscatedName("ci")
   void method7114(int var1, boolean var2, int var3) {
      try {
         if (var2) {
            this.method6930(1072642345 * this.field4150, var1, 1122842785);
         } else {
            this.method6930(var1, var1, 1122842785);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.ci(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("el")
   public boolean method6928(int var1, int var2) {
      if (!this.method7043((byte)-104)) {
         this.field4150 = var1 * -737774823;
         this.field4155 = 1468773795 * var2;
         return false;
      } else {
         if (var1 > classZD.method13896(this.field4157, 1522423452)) {
            var1 = classZD.method13896(this.field4157, -1774138685);
         }

         if (var2 > classZD.method13896(this.field4157, -230861502)) {
            var2 = classZD.method13896(this.field4157, -456691850);
         }

         boolean var3 = var1 != 1072642345 * this.field4150 || this.field4155 * -698145269 != var2;
         this.field4150 = var1 * -737774823;
         if (this.field4155 * -698145269 != var2) {
            this.field4155 = 1468773795 * var2;
            this.field4144 = 0;
            this.method7123(1546724843);
         }

         if (var3 && null != this.field4165) {
            this.field4165.vmethod147(-2048435553);
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   void method7123(int var1) {
      try {
         classAAG var2 = this.field4157.method13915(0, -698145269 * this.field4155, 200682512);
         classWB var3 = classAAG.method213(var2, (byte)-106);
         int var4 = classZD.method13953(this.field4157, (byte)-29);
         int var5 = (Integer)var3.field6652 - 10;
         int var6 = var5 + 20;
         int var7 = (Integer)var3.field6653 - 3;
         int var8 = var7 + 6 + var4;
         int var9 = -1184890025 * this.field4152;
         int var10 = 2084178789 * this.field4156 + var9;
         int var11 = -457263387 * this.field4158;
         int var12 = 236265915 * this.field4151 + var11;
         int var13 = -1184890025 * this.field4152;
         int var14 = this.field4158 * -457263387;
         if (var5 < var9) {
            if (var1 <= 1409580481) {
               throw new IllegalStateException();
            }

            var13 = var5;
         } else if (var6 > var10) {
            if (var1 <= 1409580481) {
               throw new IllegalStateException();
            }

            var13 = var6 - 2084178789 * this.field4156;
         }

         if (var7 < var11) {
            if (var1 <= 1409580481) {
               throw new IllegalStateException();
            }

            var14 = var7;
         } else if (var8 > var12) {
            if (var1 <= 1409580481) {
               throw new IllegalStateException();
            }

            var14 = var8 - 236265915 * this.field4151;
         }

         this.method6884(var13, var14, 865215021);
      } catch (RuntimeException var15) {
         throw classEG.method3899(var15, "lh.ce(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("cm")
   boolean method7129(int var1, int var2) {
      try {
         if (32 != var1) {
            if (var2 >= 1053045388) {
               throw new IllegalStateException();
            }

            if (var1 != 10 && var1 != 9) {
               return false;
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.cm(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("dx")
   public static boolean method6871(classLH var0, int var1) {
      if (var0 == null) {
         var0.method6899();
      }

      if (var1 < 0) {
         var1 = Integer.MAX_VALUE;
      }

      boolean var2 = var1 == classZD.method13965(var0.field4157, 1308842644);
      var0.field4157.method13970(var1, -2073116382);
      var0.field4142.method13970(var1, -2046413928);
      if (var0.method7111((byte)-3)) {
         method7133(var0, (byte)36);
         var2 = true;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bo")
   public void method6958(boolean var1, int var2) {
      try {
         if (-698145269 * this.field4155 < classZD.method13896(this.field4157, 2015287633)) {
            if (var2 >= 1759765807) {
               throw new IllegalStateException();
            }

            classWB var3 = this.method7102(1 + this.field4155 * -698145269, 1958930142);
            this.method7114((Integer)var3.field6653, var1, -1972416505);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ig")
   public boolean method7035() {
      return this.method7027(1386916343) && 138766506 * this.field4144 % 60 < 30;
   }

   @ObfuscatedSignature(descriptor = "(IIZZ)V")
   @ObfuscatedName("hm")
   public void method6989(int var1, int var2, boolean var3, boolean var4) {
      int var5 = 0;
      if (!this.field4145) {
         var5 = 0;
      } else {
         var1 += this.field4152 * -1184890025;
         var2 += -457263387 * this.field4158;
         classWB var6 = method7139(this, -1079082866);
         var5 = this.field4157.method13939(var1 - (Integer)var6.field6652, var2 - (Integer)var6.field6653, 155745000);
      }

      if (var3 && var4) {
         this.field4147 = 328689669;
         classWB var14 = this.method7102(var5, 1156104700);
         classWB var7 = this.method7102(this.field4149 * -1558750221, 1427318757);
         method7099(this, var7, var14, 1801667578);
      } else if (var3) {
         this.field4147 = 328689669;
         classWB var11 = this.method7102(var5, 1182576901);
         this.method6930((Integer)var11.field6652, (Integer)var11.field6653, 1122842785);
         this.field4149 = (Integer)var11.field6652 * -2118535877;
      } else if (var4) {
         this.method6930(this.field4149 * -1558750221, var5, 1122842785);
      } else {
         if (this.field4154 * 1398532195 <= 0 || -1558750221 * this.field4149 != var5) {
            this.field4147 = 0;
            this.method6930(var5, var5, 1122842785);
            this.field4149 = -2118535877 * var5;
         } else if (1072642345 * this.field4150 == -698145269 * this.field4155) {
            this.field4147 = 328689669;
            classWB var12 = this.method7102(var5, 1883015778);
            this.method6930((Integer)var12.field6652, (Integer)var12.field6653, 1122842785);
         } else {
            this.field4147 = 657379338;
            classWB var13 = this.method7106(var5, -1210966479);
            this.method6930((Integer)var13.field6652, (Integer)var13.field6653, 1122842785);
         }

         this.field4154 = -745916333;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)Z")
   @ObfuscatedName("du")
   public boolean method6845(boolean var1) {
      var1 = var1 && this.field4143;
      boolean var2 = var1 != this.field4141;
      this.field4141 = var1;
      if (!this.field4141) {
         this.method6930(this.field4155 * -698145269, this.field4155 * -698145269, 1122842785);
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Llh;B)V")
   @ObfuscatedName("pn")
   public static void method7006(classLH var0, byte var1) {
      if (var0 == null) {
         var0.method7005(var1);
      }

      try {
         int var2 = Math.max(0, classZD.method13949(var0.field4157, (byte)93) - var0.field4151 * 236265915);
         var0.field4158 = Math.min(var2, var0.field4158 * -457263387 + classZD.method13953(var0.field4157, (byte)-61)) * -1673193747;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.by(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lzd;")
   @ObfuscatedName("hv")
   public classZD method7021() {
      return this.field4142;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("dk")
   public void method6849(boolean var1) {
      this.field4143 = var1;
      this.field4141 = var1 && this.field4141;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("db")
   public void method6850(boolean var1) {
      this.field4143 = var1;
      this.field4141 = var1 && this.field4141;
   }

   @ObfuscatedSignature(descriptor = "(Llh;B)V")
   @ObfuscatedName("jo")
   public static void method7133(classLH var0, byte var1) {
      if (var0 == null) {
         var0.method7136(var1);
      } else {
         try {
            if (var0.field4164 != null) {
               if (var1 <= 8) {
                  return;
               }

               var0.field4164.vmethod147(-1854632780);
            }
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lh.dc(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("dh")
   public boolean method6853(String var1) {
      String var2 = this.field4157.method13899((byte)-78);
      if (!var2.equals(var1)) {
         var1 = method7095(this, var1, 1955686191);
         this.field4157.method13931(var1, 1202114375);
         this.method6930(this.field4150 * -921251019, this.field4155 * -180045852, 1122842785);
         this.method6884(this.field4152 * -1752598530, -457263387 * this.field4158, -1362499256);
         this.method7111((byte)-69);
         method7133(this, (byte)117);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("dw")
   public boolean method6854(String var1) {
      String var2 = this.field4157.method13899((byte)-91);
      if (!var2.equals(var1)) {
         var1 = method7095(this, var1, 2101837519);
         this.field4157.method13931(var1, 1420762832);
         this.method6930(this.field4150 * 1072642345, this.field4155 * -698145269, 1122842785);
         this.method6884(this.field4152 * -1184890025, -457263387 * this.field4158, -1944944322);
         this.method7111((byte)-94);
         method7133(this, (byte)103);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("de")
   boolean method6858(String var1) {
      this.field4142.method13931(var1, 1424169415);
      return true;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;)V")
   @ObfuscatedName("hg")
   public void method6999(Clipboard var1) {
      if (this.method7089(2012791869)) {
         this.method6995(var1, -887645341);
         this.method6924(455461300);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Lwb;")
   @ObfuscatedName("dt")
   public static classWB method7107(classLH var0, int var1) {
      int var2 = classZD.method13896(var0.field4157, -1063084926);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (var0.field4157.method13883(var5 - 1, -1052717093).field117 == '\n') {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (var0.field4157.method13883(var6, -1052717093).field117 == '\n') {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("fp")
   public boolean method6863(int var1, int var2) {
      boolean var3 = 2084178789 * this.field4156 != var1 || this.field4151 * 236265915 != var2;
      this.field4156 = -1191629203 * var1;
      this.field4151 = -526943373 * var2;
      return var3 | this.method6884(this.field4152 * -1184890025, this.field4158 * -457263387, 204110968);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("dq")
   public boolean method6864(int var1, int var2) {
      boolean var3 = 2084178789 * this.field4156 != var1 || this.field4151 * 236265915 != var2;
      this.field4156 = 1821386384 * var1;
      this.field4151 = -526943373 * var2;
      return var3 | this.method6884(this.field4152 * 741902702, this.field4158 * -457263387, -801801654);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fq")
   public void method6927() {
      this.method6930(0, classZD.method13896(this.field4157, 1289136605), 1122842785);
   }

   @ObfuscatedSignature(descriptor = "(Llm;)V")
   @ObfuscatedName("hj")
   public void method7010(classLM var1) {
      this.field4164 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bh")
   public boolean method7030(int var1) {
      try {
         return this.field4143;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.bh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)V")
   @ObfuscatedName("fe")
   public static void method6840(classLH var0, int var1) {
      if (var0 == null) {
         var0.method6843(var1);
      } else {
         try {
            var0.field4144 = (1 + var0.field4144 * -1569689927) % 60 * -1662830711;
            if (1398532195 * var0.field4154 > 0) {
               if (var1 == 643906573) {
                  return;
               }

               var0.field4154 -= -373434037;
            }
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "lh.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ea")
   public boolean method6879(int var1) {
      this.field4140 = 1211480737 * var1;
      if (this.method7111((byte)-8)) {
         method7133(this, (byte)59);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ek")
   public boolean method6880(int var1) {
      this.field4140 = 1211480737 * var1;
      if (this.method7111((byte)-5)) {
         method7133(this, (byte)42);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;III)Z")
   @ObfuscatedName("tb")
   public static boolean method6865(classLH var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method6868(var1, var1, var1);
      } else {
         try {
            boolean var10000;
            label25: {
               if (2084178789 * var0.field4156 == var1) {
                  if (var3 >= -483624883) {
                     throw new IllegalStateException();
                  }

                  if (var0.field4151 * 236265915 == var2) {
                     var10000 = false;
                     break label25;
                  }
               }

               var10000 = true;
            }

            boolean var4 = var10000;
            var0.field4156 = -1191629203 * var1;
            var0.field4151 = -526943373 * var2;
            return var4 | var0.method6884(var0.field4152 * -1184890025, var0.field4158 * -457263387, -1950004505);
         } catch (RuntimeException var5) {
            throw classEG.method3899(var5, "lh.ax(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cd")
   public int method7039(int var1) {
      try {
         return this.field4150 * 1072642345;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cu(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ed")
   public boolean method6881(int var1) {
      this.field4140 = 1211480737 * var1;
      if (this.method7111((byte)-76)) {
         method7133(this, (byte)31);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fg")
   public void method6919() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 < classZD.method13896(this.field4157, -1335663263)) {
         classWB var1 = this.method7102(-698145269 * this.field4155, 1600008299);
         int var2 = this.field4157.method13935(this.field4155 * -1532942369, (Integer)var1.field6653, -202510427);
         method7133(this, (byte)27);
         this.method6930(var2, var2, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ac")
   public boolean method6885(int var1, int var2) {
      if (!this.method7043((byte)-119)) {
         this.field4152 = var1 * -1509044448;
         this.field4158 = var2 * -6592661;
         return false;
      } else {
         int var3 = -1184890025 * this.field4152;
         int var4 = -2126043422 * this.field4158;
         int var5 = Math.max(0, this.field4157.method13947((byte)-119) - this.field4156 * -1780625469 + 2);
         int var6 = Math.max(0, classZD.method13949(this.field4157, (byte)12) - 1178306276 * this.field4151 + 1);
         this.field4152 = Math.max(0, Math.min(var5, var1)) * -942303129;
         this.field4158 = Math.max(0, Math.min(var6, var2)) * -1673193747;
         return var3 != this.field4152 * -1184890025 || -457263387 * this.field4158 != var4;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jk")
   public int method7079() {
      return classZD.method13991(this.field4157, -504149574);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("eh")
   public boolean method6888(int var1, int var2) {
      boolean var3 = true;
      if (var1 < 0 || var1 > 2) {
         var3 = false;
      }

      if (var2 < 0 || var2 > 2) {
         var3 = false;
      }

      return var3 ? classZD.method13910(this.field4157, var1, var2, 100169848) : false;
   }

   @ObfuscatedSignature(descriptor = "(Llh;Ljava/awt/datatransfer/Clipboard;I)V")
   @ObfuscatedName("sy")
   public static void method7002(classLH var0, Clipboard var1, int var2) {
      if (var0 == null) {
         var0.method7003(var1, var2);
      } else {
         try {
            Transferable var3 = var1.getContents(null);
            if (var3 != null && var3.isDataFlavorSupported(DataFlavor.stringFlavor)) {
               if (var2 <= -324749371) {
                  return;
               }

               try {
                  String var4 = method7095(var0, (String)var3.getTransferData(DataFlavor.stringFlavor), 2013546798);
                  var0.method6924(455461300);
                  classZP var5 = var0.field4157.method13921(var4, -698145269 * var0.field4155, var0.field4153 * 1030611083, (byte)39);
                  var0.method6930(classZP.method14129(var5, -589811403), classZP.method14129(var5, -391538325), 1122842785);
                  var0.method7111((byte)-5);
                  method7133(var0, (byte)39);
               } catch (Exception var6) {
               }
            }
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "lh.bi(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lx")
   void method7134() {
      if (this.field4164 != null) {
         this.field4164.vmethod147(-2143360612);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("vu")
   public static void method6974(classLH var0, boolean var1) {
      if (var0.field4155 * -698145269 > 0) {
         int var2 = method7118(var0, 1862321022);
         var0.method7114(var0.field4157.method13942(var0.field4155 * -698145269, -var2, -1470055672), var1, -1422404283);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Lwb;")
   @ObfuscatedName("dx")
   public static classWB method7103(classLH var0, int var1) {
      int var2 = classZD.method13896(var0.field4157, 187315475);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (var0.method7129(var0.field4157.method13883(var5 - 1, -1052717093).field117, -1747129619)) {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (var0.method7129(var0.field4157.method13883(var6, -1052717093).field117, 804032749)) {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ex")
   public void method6891(int var1) {
      this.field4157.method13913(var1, (byte)110);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aq")
   public boolean method6886(int var1, int var2) {
      if (!this.method7043((byte)7)) {
         this.field4152 = var1 * 616459606;
         this.field4158 = var2 * 614320697;
         return false;
      } else {
         int var3 = -1184890025 * this.field4152;
         int var4 = -457263387 * this.field4158;
         int var5 = Math.max(0, this.field4157.method13947((byte)-122) - this.field4156 * -1560688385 + 2);
         int var6 = Math.max(0, classZD.method13949(this.field4157, (byte)96) - 236265915 * this.field4151 + 1);
         this.field4152 = Math.max(0, Math.min(var5, var1)) * -942303129;
         this.field4158 = Math.max(0, Math.min(var6, var2)) * -1610204324;
         return var3 != this.field4152 * -1638711732 || -457263387 * this.field4158 != var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("er")
   public void method6892(int var1) {
      this.field4157.method13913(var1, (byte)112);
   }

   classLH() {
      this.field4145 = false;
      this.field4146 = 0;
      this.field4147 = 0;
      this.field4148 = 0;
      this.field4155 = 0;
      this.field4150 = 0;
      this.field4149 = 0;
      this.field4154 = 0;
      this.field4153 = -47417123;
      this.field4140 = 936002911;
      this.field4156 = 0;
      this.field4151 = 0;
      this.field4152 = 0;
      this.field4158 = 0;
      this.field4157.method13984(1, 1265711088);
      this.field4142.method13984(1, 517338926);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("et")
   public boolean method6896(int var1) {
      this.field4148 = var1 * -1502904977;
      String var2 = this.field4157.method13899((byte)-87);
      int var3 = var2.length();
      var2 = method7095(this, var2, 2130136860);
      if (var2.length() != var3) {
         this.field4157.method13931(var2, 1139096933);
         this.method6884(this.field4152 * 564515252, this.field4158 * -457263387, 14451625);
         this.method7111((byte)-13);
         method7133(this, (byte)20);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ev")
   public boolean method6866(int var1, int var2) {
      boolean var3 = 2084178789 * this.field4156 != var1 || this.field4151 * 236265915 != var2;
      this.field4156 = -1191629203 * var1;
      this.field4151 = -526943373 * var2;
      return var3 | this.method6884(this.field4152 * -1184890025, this.field4158 * -457263387, -548633443);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("eu")
   public void method6900() {
      this.field4145 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ep")
   public void method6908() {
      if (!this.method6924(455461300) && -1410705525 * this.field4155 > 0) {
         int var1 = this.field4157.method13934(this.field4155 * -698145269 - 1, -1596338929);
         method7133(this, (byte)81);
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ez")
   public void method6901() {
      this.field4145 = false;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jd")
   public int method7051() {
      return classZD.method13965(this.field4157, 79138326);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("il")
   public boolean method7031() {
      return this.field4143;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ca")
   public int method7080(byte var1) {
      try {
         return classZD.method13991(this.field4157, -504149574);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.ca(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("vt")
   public static void method6967(classLH var0, boolean var1) {
      if (var0.field4155 * -698145269 < classZD.method13896(var0.field4157, -1974791741)) {
         var0.method7114(var0.field4157.method13942(var0.field4155 * -698145269, 1, -1144557682), var1, -1196027771);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ec")
   public void method6909() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 > 0) {
         int var1 = this.field4157.method13934(this.field4155 * -698145269 - 1, -1596338929);
         method7133(this, (byte)124);
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fo")
   public void method6910() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 > 0) {
         int var1 = this.field4157.method13934(this.field4155 * -698145269 - 1, -1596338929);
         method7133(this, (byte)71);
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fl")
   public void method6911() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 > 0) {
         int var1 = this.field4157.method13934(this.field4155 * -698145269 - 1, -1596338929);
         method7133(this, (byte)36);
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laaa;I)Z")
   @ObfuscatedName("as")
   boolean method6860(AbstractFont var1, int var2) {
      try {
         boolean var10000;
         if (!this.field4145) {
            if (var2 >= 693068370) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var3 = var10000;
         this.field4157.method13905(var1, 180707768);
         this.field4142.method13905(var1, -707528356);
         this.field4145 = true;
         var3 |= this.method6884(this.field4152 * -1184890025, this.field4158 * -457263387, -586031462);
         var3 |= this.method6930(this.field4150 * 1072642345, -698145269 * this.field4155, 1122842785);
         if (this.method7111((byte)-7)) {
            method7133(this, (byte)80);
            var3 = true;
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fy")
   public void method6914() {
      if (!this.method6924(455461300) && this.field4155 * -698145269 < classZD.method13896(this.field4157, -1329704435)) {
         int var1 = this.field4157.method13934(this.field4155 * -698145269, -1596338929);
         method7133(this, (byte)109);
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("ao")
   public boolean method6874(int var1, byte var2) {
      try {
         this.field4157.method13978(var1, (byte)-40);
         if (this.method7111((byte)-124)) {
            if (var2 != 1) {
               throw new IllegalStateException();
            } else {
               method7133(this, (byte)99);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.aa(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)V")
   @ObfuscatedName("lp")
   public static void method6902(classLH var0) {
      if (var0 == null) {
         var0.method6916();
      }

      var0.field4145 = false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fi")
   public void method6920() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 < classZD.method13896(this.field4157, 1315844644)) {
         classWB var1 = this.method7102(-698145269 * this.field4155, 1346880431);
         int var2 = this.field4157.method13935(this.field4155 * -698145269, (Integer)var1.field6653, 2058236458);
         method7133(this, (byte)23);
         this.method6930(var2, var2, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fb")
   public void method6921() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 < classZD.method13896(this.field4157, -2015533720)) {
         classWB var1 = this.method7102(-698145269 * this.field4155, 1250434340);
         int var2 = this.field4157.method13935(this.field4155 * -698145269, (Integer)var1.field6653, 1949173927);
         method7133(this, (byte)12);
         this.method6930(var2, var2, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fu")
   public void method6922() {
      if (!this.method6924(455461300) && -698145269 * this.field4155 < classZD.method13896(this.field4157, -1847763938)) {
         classWB var1 = this.method7102(-698145269 * this.field4155, 1465306933);
         int var2 = this.field4157.method13935(this.field4155 * -698145269, (Integer)var1.field6653, 1369095791);
         method7133(this, (byte)43);
         this.method6930(var2, var2, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("jf")
   public void method7061(int var1) {
      this.field4146 = -1061050160 * var1;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gx")
   public void method6976(boolean var1) {
      if (this.field4155 * -698145269 < classZD.method13896(this.field4157, -39775339)) {
         int var2 = method7118(this, 1853316520);
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, var2, -1183341872), var1, -1106264835);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)Lwb;")
   @ObfuscatedName("xa")
   public static classWB method7139(classLH var0, int var1) {
      if (var0 == null) {
         return var0.method7143(var1);
      } else {
         try {
            int var2 = var0.field4157.method13993(var0.field4156 * 2084178789, (byte)-38);
            int var3 = classZD.method13995(var0.field4157, var0.field4151 * 236265915, -605299333);
            return new classWB(var2, var3);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "lh.dm(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ee")
   public boolean method6904(int var1) {
      if (this.method7137(var1, -32389191)) {
         this.method6924(455461300);
         classZP var2 = this.field4157.method13918((char)var1, -698145269 * this.field4155, this.field4153 * 1030611083, (byte)97);
         this.method6930(classZP.method14129(var2, 169038737), classZP.method14129(var2, -39684417), 1122842785);
         this.method7111((byte)-119);
         method7133(this, (byte)104);
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("eg")
   public boolean method6929(int var1, int var2) {
      if (!this.method7043((byte)-41)) {
         this.field4150 = var1 * -737774823;
         this.field4155 = -897094938 * var2;
         return false;
      } else {
         if (var1 > classZD.method13896(this.field4157, -2093072720)) {
            var1 = classZD.method13896(this.field4157, -2144335305);
         }

         if (var2 > classZD.method13896(this.field4157, -118391600)) {
            var2 = classZD.method13896(this.field4157, -513331700);
         }

         boolean var3 = var1 != 1072642345 * this.field4150 || this.field4155 * -698145269 != var2;
         this.field4150 = var1 * -740689143;
         if (this.field4155 * -1073262000 != var2) {
            this.field4155 = 1468773795 * var2;
            this.field4144 = 0;
            this.method7123(1956662936);
         }

         if (var3 && null != this.field4165) {
            this.field4165.vmethod147(-2138664229);
         }

         return var3;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("id")
   public int method7052() {
      return classZD.method13965(this.field4157, 1353637852);
   }

   @ObfuscatedSignature(descriptor = "()Lwb;")
   @ObfuscatedName("ll")
   classWB method7140() {
      int var1 = this.field4157.method13993(this.field4156 * 2084178789, (byte)-117);
      int var2 = classZD.method13995(this.field4157, this.field4151 * 236265915, -1639788080);
      return new classWB(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Z)Z")
   @ObfuscatedName("di")
   public boolean method6846(boolean var1) {
      var1 = var1 && this.field4143;
      boolean var2 = var1 != this.field4141;
      this.field4141 = var1;
      if (!this.field4141) {
         this.method6930(this.field4155 * -698145269, this.field4155 * -698145269, 1122842785);
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fx")
   public void method6935(boolean var1) {
      classWB var2 = this.method7106(-698145269 * this.field4155, -2144259910);
      this.method7114((Integer)var2.field6653, var1, -2014321361);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fr")
   public void method6936(boolean var1) {
      classWB var2 = this.method7106(-722278368 * this.field4155, -399582529);
      this.method7114((Integer)var2.field6653, var1, -2053657812);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ft")
   public void method6937(boolean var1) {
      classWB var2 = this.method7106(-698145269 * this.field4155, -1923711887);
      this.method7114((Integer)var2.field6653, var1, -1321521928);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("eo")
   public boolean method6897(int var1) {
      this.field4148 = var1 * 689527944;
      String var2 = this.field4157.method13899((byte)-81);
      int var3 = var2.length();
      var2 = method7095(this, var2, 2074294826);
      if (var2.length() != var3) {
         this.field4157.method13931(var2, 1167144126);
         this.method6884(this.field4152 * -1184890025, this.field4158 * -457263387, -1935850458);
         this.method7111((byte)-85);
         method7133(this, (byte)80);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fe")
   public void method6941(boolean var1) {
      this.method7114(0, var1, -953555876);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fm")
   public void method6942(boolean var1) {
      this.method7114(0, var1, -1831449096);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gq")
   public void method6948(boolean var1) {
      if (this.method7089(2012791869) && !var1) {
         this.method7114(Math.min(this.field4150 * 1072642345, -698145269 * this.field4155), var1, -1300962940);
      } else if (-698145269 * this.field4155 > 0) {
         this.method7114(this.field4155 * -698145269 - 1, var1, -1973562466);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Ljava/awt/datatransfer/Clipboard;)V")
   @ObfuscatedName("rm")
   public static void method7000(classLH var0, Clipboard var1) {
      if (var0 == null) {
         var0.method7075();
      }

      if (var0.method7089(2012791869)) {
         var0.method6995(var1, -887645341);
         var0.method6924(455461300);
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("at")
   public boolean method6930(int var1, int var2, int var3) {
      try {
         if (!this.method7043((byte)-116)) {
            if (var3 != 1122842785) {
               throw new IllegalStateException();
            } else {
               this.field4150 = var1 * -737774823;
               this.field4155 = 1468773795 * var2;
               return false;
            }
         } else {
            if (var1 > classZD.method13896(this.field4157, -381452321)) {
               if (var3 != 1122842785) {
                  throw new IllegalStateException();
               }

               var1 = classZD.method13896(this.field4157, 2055901482);
            }

            if (var2 > classZD.method13896(this.field4157, -1869711221)) {
               if (var3 != 1122842785) {
                  throw new IllegalStateException();
               }

               var2 = classZD.method13896(this.field4157, -1122154423);
            }

            boolean var10000;
            label61: {
               if (var1 == 1072642345 * this.field4150) {
                  if (var3 != 1122842785) {
                     throw new IllegalStateException();
                  }

                  if (this.field4155 * -698145269 == var2) {
                     var10000 = false;
                     break label61;
                  }
               }

               var10000 = true;
            }

            boolean var4 = var10000;
            this.field4150 = var1 * -737774823;
            if (this.field4155 * -698145269 != var2) {
               this.field4155 = 1468773795 * var2;
               this.field4144 = 0;
               this.method7123(2141238320);
            }

            if (var4) {
               if (var3 != 1122842785) {
                  throw new IllegalStateException();
               }

               if (null != this.field4165) {
                  if (var3 != 1122842785) {
                     throw new IllegalStateException();
                  }

                  this.field4165.vmethod147(-1805182445);
               }
            }

            return var4;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.at(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("my")
   public static void method6978(classLH var0, boolean var1) {
      classAAG var2 = var0.field4157.method13915(0, var0.field4155 * -698145269, 804525473);
      classWB var3 = classAAG.method213(var2, (byte)-106);
      var0.method7114(var0.field4157.method13939((Integer)var3.field6652, var0.field4158 * -457263387, -364913884), var1, -1441286663);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ei")
   public boolean method6905(int var1) {
      if (this.method7137(var1, -32389191)) {
         this.method6924(455461300);
         classZP var2 = this.field4157.method13918((char)var1, -698145269 * this.field4155, this.field4153 * -602529696, (byte)23);
         this.method6930(classZP.method14129(var2, -2138140052), classZP.method14129(var2, -1856819948), 1122842785);
         this.method7111((byte)-23);
         method7133(this, (byte)99);
      }

      return true;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gp")
   public void method6961(boolean var1) {
      if (this.field4155 * -698145269 > 0) {
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, -1, -1848367762), var1, -2071892668);
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ga")
   public void method6962(boolean var1) {
      if (this.field4155 * -698145269 > 0) {
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, -1, -556410341), var1, -1592076731);
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gw")
   public void method6963(boolean var1) {
      if (this.field4155 * -698145269 > 0) {
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, -1, -1408172875), var1, -785557906);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   boolean method6924(int var1) {
      try {
         if (!this.method7089(2012791869)) {
            if (var1 != 455461300) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            int var2 = this.field4157.method13935(1072642345 * this.field4150, -698145269 * this.field4155, -477828373);
            method7133(this, (byte)83);
            this.method6930(var2, var2, 1122842785);
            return true;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ak(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("rb")
   public static void method6949(classLH var0, boolean var1) {
      if (var0 == null) {
         var0.method6845(var1);
      }

      if (var0.method7089(2012791869) && !var1) {
         var0.method7114(Math.min(var0.field4150 * 1072642345, -698145269 * var0.field4155), var1, -736398567);
      } else if (-698145269 * var0.field4155 > 0) {
         var0.method7114(var0.field4155 * -698145269 - 1, var1, -1471273416);
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gm")
   public void method6968(boolean var1) {
      if (this.field4155 * -698145269 < classZD.method13896(this.field4157, -678491045)) {
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, 1, -1528076684), var1, -1831124647);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("vj")
   public static void method6950(classLH var0, boolean var1) {
      if (var0 == null) {
         var0.method6941(var1);
      }

      if (var0.method7089(2012791869) && !var1) {
         var0.method7114(Math.min(var0.field4150 * 1072642345, -698145269 * var0.field4155), var1, -889581291);
      } else if (-698145269 * var0.field4155 > 0) {
         var0.method7114(var0.field4155 * -698145269 - 1, var1, -1062212942);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)V")
   @ObfuscatedName("ok")
   public static void method6841(classLH var0) {
      if (var0 == null) {
         var0.method6920();
      }

      var0.field4144 = (1 + var0.field4144 * 234562754) % 60 * 765444008;
      if (1778357441 * var0.field4154 > 0) {
         var0.field4154 -= -373434037;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jw")
   public int method7075() {
      return -1136623217 * this.field4148;
   }

   @ObfuscatedSignature(descriptor = "(I)Lwb;")
   @ObfuscatedName("kb")
   classWB method7108(int var1) {
      int var2 = classZD.method13896(this.field4157, 1063975601);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (this.field4157.method13883(var5 - 1, -1052717093).field117 == '\n') {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (this.field4157.method13883(var6, -1052717093).field117 == '\n') {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ge")
   public void method6979(boolean var1) {
      classAAG var2 = this.field4157.method13915(0, this.field4155 * -698145269, 421337897);
      classWB var3 = classAAG.method213(var2, (byte)-116);
      this.method7114(this.field4157.method13939((Integer)var3.field6652, this.field4158 * -457263387, -2138511533), var1, -1084516753);
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("dd")
   boolean method7137(int var1, int var2) {
      try {
         switch (this.field4148 * -1136623217) {
            case 1:
               return classSN.method10575((char)var1, 1399850494);
            case 2:
               return classTT.method11222((char)var1, -1158089553);
            case 3:
               return classEB.method3818((char)var1, (byte)8);
            case 4:
               char var3 = (char)var1;
               if (classEB.method3818(var3, (byte)-51)) {
                  if (var2 != -32389191) {
                     throw new IllegalStateException();
                  }

                  return true;
               } else {
                  if (var3 != 'k') {
                     if (var2 != -32389191) {
                        throw new IllegalStateException();
                     }

                     if (var3 != 'K') {
                        if (var2 != -32389191) {
                           throw new IllegalStateException();
                        }

                        if ('m' != var3) {
                           if (var2 != -32389191) {
                              throw new IllegalStateException();
                           }

                           if ('M' != var3 && var3 != 'b') {
                              if (var2 != -32389191) {
                                 throw new IllegalStateException();
                              }

                              if ('B' != var3) {
                                 return false;
                              }

                              if (var2 != -32389191) {
                                 throw new IllegalStateException();
                              }
                           }
                        }
                     }
                  }

                  return true;
               }
            default:
               return true;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.dd(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("wh")
   public static void method6980(classLH var0, boolean var1) {
      classAAG var2 = var0.field4157.method13915(0, var0.field4155 * -698145269, 1948301296);
      classWB var3 = classAAG.method213(var2, (byte)-89);
      var0.method7114(var0.field4157.method13939((Integer)var3.field6652, var0.field4158 * -457263387, 544607211), var1, -1983529983);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("hz")
   public void method6983(boolean var1) {
      classAAG var2 = this.field4157.method13915(0, -851926162 * this.field4155, 1589760682);
      classWB var3 = classAAG.method213(var2, (byte)-97);
      this.method7114(
         this.field4157.method13939((Integer)var3.field6652, 671983326 * this.field4151 + this.field4158 * 1643728608, -1628755494), var1, -2117895141
      );
   }

   @ObfuscatedSignature(descriptor = "(IIZZ)V")
   @ObfuscatedName("hd")
   public void method6990(int var1, int var2, boolean var3, boolean var4) {
      int var5 = 0;
      if (!this.field4145) {
         var5 = 0;
      } else {
         var1 += this.field4152 * -1184890025;
         var2 += -457263387 * this.field4158;
         classWB var6 = method7139(this, -1079082866);
         var5 = this.field4157.method13939(var1 - (Integer)var6.field6652, var2 - (Integer)var6.field6653, 272125167);
      }

      if (var3 && var4) {
         this.field4147 = 328689669;
         classWB var14 = this.method7102(var5, 1436579323);
         classWB var7 = this.method7102(this.field4149 * -1558750221, 1781407754);
         method7099(this, var7, var14, 1801667578);
      } else if (var3) {
         this.field4147 = 328689669;
         classWB var11 = this.method7102(var5, 946604564);
         this.method6930((Integer)var11.field6652, (Integer)var11.field6653, 1122842785);
         this.field4149 = (Integer)var11.field6652 * -2118535877;
      } else if (var4) {
         this.method6930(this.field4149 * -1558750221, var5, 1122842785);
      } else {
         if (this.field4154 * 1398532195 <= 0 || -1558750221 * this.field4149 != var5) {
            this.field4147 = 0;
            this.method6930(var5, var5, 1122842785);
            this.field4149 = -2118535877 * var5;
         } else if (1072642345 * this.field4150 == -698145269 * this.field4155) {
            this.field4147 = 328689669;
            classWB var12 = this.method7102(var5, 1950114081);
            this.method6930((Integer)var12.field6652, (Integer)var12.field6653, 1122842785);
         } else {
            this.field4147 = 657379338;
            classWB var13 = this.method7106(var5, -443098235);
            this.method6930((Integer)var13.field6652, (Integer)var13.field6653, 1122842785);
         }

         this.field4154 = -745916333;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("bb")
   public static String method7093(classLH var0, String var1) {
      if (var0 == null) {
         var0.method7037();
      }

      StringBuilder var2 = new StringBuilder(var1.length());

      for (int var3 = 0; var3 < var1.length(); var3++) {
         char var4 = var1.charAt(var3);
         if (var0.method7137(var4, -32389191)) {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("if")
   public static void method6851(classLH var0, boolean var1) {
      if (var0 == null) {
         var0.method7068();
      }

      var0.field4143 = var1;
      var0.field4141 = var1 && var0.field4141;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("hl")
   public void method6991(int var1, int var2) {
      if (this.field4145 && this.method7027(1315498564)) {
         var1 += this.field4152 * -1184890025;
         var2 += -457263387 * this.field4158;
         classWB var3 = method7139(this, -1079082866);
         int var4 = this.field4157.method13939(var1 - (Integer)var3.field6652, var2 - (Integer)var3.field6653, -876285557);
         switch (this.field4147 * 658423413) {
            case 0:
               this.method6930(1072642345 * this.field4150, var4, 1122842785);
               break;
            case 1:
               classWB var9 = this.method7102(-1558750221 * this.field4149, 973723901);
               classWB var10 = this.method7102(var4, 896946424);
               method7099(this, var9, var10, 1801667578);
               break;
            case 2:
               classWB var5 = this.method7106(this.field4149 * 2126895099, -1282422350);
               classWB var6 = this.method7106(var4, -969888218);
               method7099(this, var5, var6, 1801667578);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("hp")
   public void method6992(int var1, int var2) {
      if (this.field4145 && this.method7027(2104483808)) {
         var1 += this.field4152 * -1776137327;
         var2 += 1316530823 * this.field4158;
         classWB var3 = method7139(this, -1079082866);
         int var4 = this.field4157.method13939(var1 - (Integer)var3.field6652, var2 - (Integer)var3.field6653, 1317483387);
         switch (this.field4147 * 325251303) {
            case 0:
               this.method6930(-121419683 * this.field4150, var4, 1122842785);
               break;
            case 1:
               classWB var9 = this.method7102(-1558750221 * this.field4149, 1619787905);
               classWB var10 = this.method7102(var4, 1650949096);
               method7099(this, var9, var10, 1801667578);
               break;
            case 2:
               classWB var5 = this.method7106(this.field4149 * -912129595, -775987779);
               classWB var6 = this.method7106(var4, -1161205663);
               method7099(this, var5, var6, 1801667578);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;)V")
   @ObfuscatedName("hx")
   public void method6996(Clipboard var1) {
      classAAG var2 = this.field4157.method13915(this.field4150 * -553250984, this.field4155 * -698145269, 879259947);
      if (!var2.method200(1947462440)) {
         String var3 = var2.method193(1191066898);
         if (!var3.isEmpty()) {
            var1.setContents(new StringSelection(var3), null);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;)V")
   @ObfuscatedName("hf")
   public void method6997(Clipboard var1) {
      classAAG var2 = this.field4157.method13915(this.field4150 * 1072642345, this.field4155 * -698145269, 856911178);
      if (!var2.method200(1730357919)) {
         String var3 = var2.method193(1492401288);
         if (!var3.isEmpty()) {
            var1.setContents(new StringSelection(var3), null);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;)V")
   @ObfuscatedName("hr")
   public void method7001(Clipboard var1) {
      if (this.method7089(2012791869)) {
         this.method6995(var1, -887645341);
         this.method6924(455461300);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ly")
   boolean method7130(int var1) {
      return 32 == var1 || var1 == 10 || var1 == 9;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("go")
   public void method6955(boolean var1) {
      if (-698145269 * this.field4155 > 0) {
         classWB var2 = this.method7102(this.field4155 * -698145269 - 1, 1710452106);
         this.method7114((Integer)var2.field6652, var1, -1949573059);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;I)V")
   @ObfuscatedName("fc")
   public static void method6903(classLH var0, int var1) {
      if (var0 == null) {
         var0.method6894(var1);
      }

      try {
         var0.field4145 = false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gn")
   public void method6969(boolean var1) {
      if (this.field4155 * -698145269 < classZD.method13896(this.field4157, 949849243)) {
         this.method7114(this.field4157.method13942(this.field4155 * -698145269, 1, -2074372504), var1, -1115995114);
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("an")
   public void method6956(boolean var1, byte var2) {
      try {
         if (-698145269 * this.field4155 > 0) {
            classWB var3 = this.method7102(this.field4155 * -698145269 - 1, 1743339920);
            this.method7114((Integer)var3.field6652, var1, -1392853539);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llm;)V")
   @ObfuscatedName("hq")
   public void method7011(classLM var1) {
      this.field4164 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Llh;ZI)V")
   @ObfuscatedName("th")
   public static void method6970(classLH var0, boolean var1, int var2) {
      if (var0 == null) {
         var0.method6971(var1, var2);
      }

      try {
         if (var0.field4155 * -698145269 < classZD.method13896(var0.field4157, 1117277900)) {
            if (var2 == 1461892107) {
               return;
            }

            var0.method7114(var0.field4157.method13942(var0.field4155 * -698145269, 1, -533399198), var1, -1459067041);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llm;)V")
   @ObfuscatedName("he")
   public void method7012(classLM var1) {
      this.field4164 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lzd;")
   @ObfuscatedName("hn")
   public classZD method7018() {
      return this.field4157;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ki")
   String method7094(String var1) {
      StringBuilder var2 = new StringBuilder(var1.length());

      for (int var3 = 0; var3 < var1.length(); var3++) {
         char var4 = var1.charAt(var3);
         if (this.method7137(var4, -32389191)) {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gh")
   public void method6984(boolean var1) {
      classAAG var2 = this.field4157.method13915(0, -698145269 * this.field4155, 1614579070);
      classWB var3 = classAAG.method213(var2, (byte)-126);
      this.method7114(
         this.field4157.method13939((Integer)var3.field6652, 236265915 * this.field4151 + this.field4158 * -457263387, -1678073262), var1, -1411214580
      );
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("hu")
   public void method7007() {
      int var1 = Math.max(0, classZD.method13949(this.field4157, (byte)5) - this.field4151 * -1486692866);
      this.field4158 = Math.min(var1, this.field4158 * -1875705624 + classZD.method13953(this.field4157, (byte)-26)) * -859764701;
   }

   @ObfuscatedSignature(descriptor = "()Lzd;")
   @ObfuscatedName("hb")
   public classZD method7022() {
      return this.field4142;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("iv")
   public static boolean method6898(classLH var0, int var1) {
      if (var0 == null) {
         var0.method7017(var1);
      }

      var0.field4148 = var1 * -1652512911;
      String var2 = var0.field4157.method13899((byte)-14);
      int var3 = var2.length();
      var2 = method7095(var0, var2, 1912788008);
      if (var2.length() != var3) {
         var0.field4157.method13931(var2, 900227482);
         var0.method6884(var0.field4152 * 995601190, var0.field4158 * -810314250, -1069773317);
         var0.method7111((byte)-85);
         method7133(var0, (byte)109);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)Z")
   @ObfuscatedName("af")
   public boolean method6847(boolean var1, int var2) {
      try {
         boolean var10000;
         label42: {
            if (var1) {
               if (var2 <= 1378509712) {
                  throw new IllegalStateException();
               }

               if (this.field4143) {
                  if (var2 <= 1378509712) {
                     throw new IllegalStateException();
                  }

                  var10000 = true;
                  break label42;
               }
            }

            var10000 = false;
         }

         var1 = var10000;
         if (var1 != this.field4141) {
            if (var2 <= 1378509712) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         boolean var3 = var10000;
         this.field4141 = var1;
         if (!this.field4141) {
            if (var2 <= 1378509712) {
               throw new IllegalStateException();
            }

            this.method6930(this.field4155 * -698145269, this.field4155 * -698145269, 1122842785);
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("in")
   public boolean method7044() {
      return this.field4145;
   }

   @ObfuscatedSignature(descriptor = "()Laag;")
   @ObfuscatedName("ir")
   public classAAG method7025() {
      return this.field4157.method13915(this.field4150 * 1072642345, this.field4155 * -698145269, 415687298);
   }

   @ObfuscatedSignature(descriptor = "()Laag;")
   @ObfuscatedName("is")
   public classAAG method7026() {
      return this.field4157.method13915(this.field4150 * 1072642345, this.field4155 * -698145269, 390586135);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kx")
   public boolean method7028() {
      return this.field4141;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("gm")
   public static void method6946(classLH var0, boolean var1) {
      var0.method7114(classZD.method13896(var0.field4157, -1128555433), var1, -1959632676);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ij")
   public boolean method7029() {
      return this.field4141;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gk")
   public void method6957(boolean var1) {
      if (552303156 * this.field4155 > 0) {
         classWB var2 = this.method7102(this.field4155 * -698145269 - 1, 1588892639);
         this.method7114((Integer)var2.field6652, var1, -1662582798);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;Ljava/lang/String;I)Z")
   @ObfuscatedName("is")
   public static boolean method6855(classLH var0, String var1, int var2) {
      if (var0 == null) {
         var0.method6856(var1, var2);
      }

      try {
         String var3 = var0.field4157.method13899((byte)-94);
         if (!var3.equals(var1)) {
            if (var2 == -1187145812) {
               throw new IllegalStateException();
            } else {
               var1 = method7095(var0, var1, 2103407397);
               var0.field4157.method13931(var1, 1731040047);
               var0.method6930(var0.field4150 * 1072642345, var0.field4155 * -698145269, 1122842785);
               var0.method6884(var0.field4152 * -1184890025, -457263387 * var0.field4158, -1937779588);
               var0.method7111((byte)-97);
               method7133(var0, (byte)83);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ie")
   public boolean method7032() {
      return this.field4143;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("iq")
   public boolean method7033() {
      return this.field4143;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("it")
   public boolean method7036() {
      return this.method7027(1082709922) && -1569689927 * this.field4144 % 1027280650 < 30;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)V")
   @ObfuscatedName("ik")
   public static void method6923(classLH var0) {
      if (var0 == null) {
         var0.method7021();
      }

      if (!var0.method6924(455461300) && -698145269 * var0.field4155 < classZD.method13896(var0.field4157, 807692534)) {
         classWB var1 = var0.method7102(-698145269 * var0.field4155, 1303400190);
         int var2 = var0.field4157.method13935(var0.field4155 * -698145269, (Integer)var1.field6653, 402751031);
         method7133(var0, (byte)77);
         var0.method6930(var2, var2, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Z")
   @ObfuscatedName("ag")
   boolean method6859(String var1, int var2) {
      try {
         this.field4142.method13931(var1, 1609355660);
         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("if")
   public int method7037() {
      return -698145269 * this.field4155;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iw")
   public int method7040() {
      return this.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ia")
   public int method7041() {
      return this.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("im")
   public int method7042() {
      return this.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cl")
   public int method7056(int var1) {
      try {
         return this.field4140 * 1701882209;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cd(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ik")
   public int method7047() {
      return -457263387 * this.field4158;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ap")
   public boolean method6867(int var1, int var2) {
      boolean var3 = -568560274 * this.field4156 != var1 || this.field4151 * 236265915 != var2;
      this.field4156 = -1429954145 * var1;
      this.field4151 = 1394555105 * var2;
      return var3 | this.method6884(this.field4152 * -1184890025, this.field4158 * -457263387, 674609066);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ii")
   public int method7048() {
      return -457263387 * this.field4158;
   }

   @ObfuscatedSignature(descriptor = "(Llh;ZB)V")
   @ObfuscatedName("qs")
   public static void method6943(classLH var0, boolean var1, byte var2) {
      if (var0 == null) {
         var0.method6944(var1, var2);
      } else {
         try {
            var0.method7114(0, var1, -711214799);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lh.ah(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;II)Ljava/lang/Object;")
   @ObfuscatedName("aw")
   public static Object method7063(classTE var0, int var1, int var2) {
      try {
         classOL.method8544(var0, 1327640383);
         if (var1 >= 0) {
            if (var1 < -1943548931 * var0.field6129) {
               Object var3 = classTE.method10803(var0, var1, (byte)-1);
               if (var1 < -1943548931 * var0.field6129 - 1) {
                  if (var2 <= -1095404274) {
                     throw new IllegalStateException();
                  }

                  classMX.method7786(var0, var1 + 1, var0, var1, -1943548931 * var0.field6129 - (var1 + 1), -2146130524);
               }

               var0.method10828(-1943548931 * var0.field6129 - 1, (byte)2);
               var0.field6131 = true;
               return var3;
            }

            if (var2 <= -1095404274) {
               throw new IllegalStateException();
            }
         }

         throw new RuntimeException();
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.aw(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)V")
   @ObfuscatedName("wh")
   public static void method6915(classLH var0) {
      if (var0 == null) {
         var0.method7119();
      }

      if (!var0.method6924(455461300) && var0.field4155 * 1881504364 < classZD.method13896(var0.field4157, 1156680128)) {
         int var1 = var0.field4157.method13934(var0.field4155 * -805335206, -1596338929);
         method7133(var0, (byte)26);
         var0.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("cq")
   public int method7053(byte var1) {
      try {
         return classZD.method13965(this.field4157, 1105844075);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ja")
   public int method7059() {
      return this.field4146 * 747182635;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jm")
   public int method7076() {
      return -1136623217 * this.field4148;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)Lwb;")
   @ObfuscatedName("am")
   public static classWB method7141(classLH var0) {
      int var1 = var0.field4157.method13993(var0.field4156 * 2084178789, (byte)-51);
      int var2 = classZD.method13995(var0.field4157, var0.field4151 * 236265915, -302596493);
      return new classWB(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ji")
   public void method7062(int var1) {
      this.field4146 = -1971006333 * var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("fd")
   public void method6916() {
      if (!this.method6924(455461300) && this.field4155 * -442353210 < classZD.method13896(this.field4157, 1323492499)) {
         int var1 = this.field4157.method13934(this.field4155 * -698145269, -1596338929);
         method7133(this, (byte)32);
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jc")
   public int method7066() {
      return this.field4157.method13982(-291544566);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)V")
   @ObfuscatedName("qr")
   public static void method7124(classLH var0) {
      classAAG var1 = var0.field4157.method13915(0, -1717450966 * var0.field4155, 542889153);
      classWB var2 = classAAG.method213(var1, (byte)-80);
      int var3 = classZD.method13953(var0.field4157, (byte)37);
      int var4 = (Integer)var2.field6652 - 10;
      int var5 = var4 + 20;
      int var6 = (Integer)var2.field6653 - 3;
      int var7 = var6 + 6 + var3;
      int var8 = -469357513 * var0.field4152;
      int var9 = -264213154 * var0.field4156 + var8;
      int var10 = -457263387 * var0.field4158;
      int var11 = 1858361115 * var0.field4151 + var10;
      int var12 = -1184890025 * var0.field4152;
      int var13 = var0.field4158 * -457263387;
      if (var4 < var8) {
         var12 = var4;
      } else if (var5 > var9) {
         var12 = var5 - 2084178789 * var0.field4156;
      }

      if (var6 < var10) {
         var13 = var6;
      } else if (var7 > var11) {
         var13 = var7 - 236265915 * var0.field4151;
      }

      var0.method6884(var12, var13, 177778694);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jo")
   public int method7067() {
      return this.field4157.method13982(-1204457614);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("jt")
   public void method7070(int var1) {
      this.field4157.method13984(var1, 1893807923);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("jx")
   public void method7071(int var1) {
      this.field4157.method13984(var1, 745205026);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jv")
   public int method7077() {
      return -1136623217 * this.field4148;
   }

   @ObfuscatedSignature(descriptor = "(Llh;Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("yy")
   public static String method7095(classLH var0, String var1, int var2) {
      if (var0 == null) {
         return var0.method7097(var1, var2);
      } else {
         try {
            StringBuilder var3 = new StringBuilder(var1.length());

            for (int var4 = 0; var4 < var1.length(); var4++) {
               if (var2 <= 1906148269) {
                  throw new IllegalStateException();
               }

               char var5 = var1.charAt(var4);
               if (var0.method7137(var5, -32389191)) {
                  if (var2 <= 1906148269) {
                     throw new IllegalStateException();
                  }

                  var3.append(var5);
               }
            }

            return var3.toString();
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "lh.ch(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jh")
   public int method7078() {
      return 1426203687 * this.field4148;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ds")
   public boolean method6872(int var1) {
      if (var1 < 0) {
         var1 = Integer.MAX_VALUE;
      }

      boolean var2 = var1 == classZD.method13965(this.field4157, 1709469416);
      this.field4157.method13970(var1, -2054023757);
      this.field4142.method13970(var1, -2091352093);
      if (this.method7111((byte)-50)) {
         method7133(this, (byte)24);
         var2 = true;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(Llh;III)V")
   @ObfuscatedName("ou")
   public static void method6993(classLH var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method6994(var1, var1, var1);
      }

      try {
         if (var0.field4145) {
            if (var3 == 610066815) {
               throw new IllegalStateException();
            }

            if (var0.method7027(1491129216)) {
               if (var3 == 610066815) {
                  return;
               }

               var1 += var0.field4152 * -1184890025;
               var2 += -457263387 * var0.field4158;
               classWB var4 = method7139(var0, -1079082866);
               int var5 = var0.field4157.method13939(var1 - (Integer)var4.field6652, var2 - (Integer)var4.field6653, 924454399);
               switch (var0.field4147 * -1083366195) {
                  case 0:
                     var0.method6930(1072642345 * var0.field4150, var5, 1122842785);
                     break;
                  case 1:
                     classWB var11 = var0.method7102(-1558750221 * var0.field4149, 1833946392);
                     classWB var12 = var0.method7102(var5, 925050001);
                     method7099(var0, var11, var12, 1801667578);
                     break;
                  case 2:
                     classWB var6 = var0.method7106(var0.field4149 * -1558750221, -1848636692);
                     classWB var7 = var0.method7106(var5, -355309306);
                     method7099(var0, var6, var7, 1801667578);
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lh.ba(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jp")
   public int method7081() {
      return classZD.method13991(this.field4157, -504149574);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jb")
   public int method7082() {
      return classZD.method13991(this.field4157, -504149574);
   }

   @ObfuscatedSignature(descriptor = "(Llh;II)Z")
   @ObfuscatedName("sa")
   public static boolean method6906(classLH var0, int var1, int var2) {
      if (var0 == null) {
         var0.method7132(var1, var1);
      }

      try {
         if (var0.method7137(var1, -32389191)) {
            var0.method6924(455461300);
            classZP var3 = var0.field4157.method13918((char)var1, -698145269 * var0.field4155, var0.field4153 * 1030611083, (byte)27);
            var0.method6930(classZP.method14129(var3, 916955013), classZP.method14129(var3, -1295548710), 1122842785);
            var0.method7111((byte)-65);
            method7133(var0, (byte)88);
         }

         return true;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("hi")
   public void method6985(boolean var1) {
      classAAG var2 = this.field4157.method13915(0, -698145269 * this.field4155, 1492145276);
      classWB var3 = classAAG.method213(var2, (byte)-111);
      this.method7114(
         this.field4157.method13939((Integer)var3.field6652, 236265915 * this.field4151 + this.field4158 * -457263387, -1660367962), var1, -1827828406
      );
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("je")
   public void method7085(int var1) {
      this.field4157.method13902(var1, 850957696);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ih")
   public boolean method7087() {
      return this.method7055(-2118783281) > 1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kf")
   public boolean method7088() {
      return this.method7055(-2118783281) > 1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kg")
   boolean method7090() {
      return this.field4155 * -698145269 != this.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ks")
   boolean method7091() {
      return this.field4155 * -698145269 != this.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ko")
   boolean method7092() {
      return this.field4155 * 1795960488 != this.field4150 * 1072642345;
   }

   @ObfuscatedSignature(descriptor = "(Llm;)V")
   @ObfuscatedName("hy")
   public void method7014(classLM var1) {
      this.field4165 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ka")
   String method7096(String var1) {
      StringBuilder var2 = new StringBuilder(var1.length());

      for (int var3 = 0; var3 < var1.length(); var3++) {
         char var4 = var1.charAt(var3);
         if (this.method7137(var4, -32389191)) {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ho")
   public void method7008() {
      int var1 = Math.max(0, classZD.method13949(this.field4157, (byte)85) - this.field4151 * 236265915);
      this.field4158 = Math.min(var1, this.field4158 * -457263387 + classZD.method13953(this.field4157, (byte)63)) * -1673193747;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lz")
   void method6842() {
      this.field4144 = (1 + this.field4144 * -1569689927) % 60 * -1662830711;
      if (1398532195 * this.field4154 > 0) {
         this.field4154 -= -373434037;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lwb;")
   @ObfuscatedName("dm")
   classWB method7104(int var1) {
      int var2 = classZD.method13896(this.field4157, 659229269);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (this.method7129(this.field4157.method13883(var5 - 1, -1052717093).field117, -1487936950)) {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (this.method7129(this.field4157.method13883(var6, -1052717093).field117, 511322815)) {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(I)Lwb;")
   @ObfuscatedName("ku")
   classWB method7105(int var1) {
      int var2 = classZD.method13896(this.field4157, 2209403);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (this.method7129(this.field4157.method13883(var5 - 1, -1052717093).field117, -1803259437)) {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (this.method7129(this.field4157.method13883(var6, -1052717093).field117, 370641079)) {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(I)Lwb;")
   @ObfuscatedName("kd")
   classWB method7109(int var1) {
      int var2 = classZD.method13896(this.field4157, 749673156);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (this.field4157.method13883(var5 - 1, -1052717093).field117 == '\n') {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (this.field4157.method13883(var6, -1052717093).field117 == '\n') {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(Llh;IS)V")
   @ObfuscatedName("sx")
   public static void method7072(classLH var0, int var1, short var2) {
      if (var0 == null) {
         var0.method7073(var1, var2);
      }

      try {
         var0.field4157.method13984(var1, 879949851);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.co(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)V")
   @ObfuscatedName("ay")
   public static void method6893(classLH var0, int var1) {
      var0.field4157.method13913(var1, (byte)119);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("kk")
   boolean method7112() {
      if (!this.method7043((byte)-83)) {
         return false;
      } else {
         boolean var1 = false;
         if (classZD.method13896(this.field4157, 1524979700) > this.field4140 * 1701882209) {
            this.field4157.method13935(this.field4140 * 1701882209, classZD.method13896(this.field4157, -465672214), 841198054);
            var1 = true;
         }

         int var2 = this.method7055(-2118783281);
         if (this.field4157.method13958(-1578415338) > var2) {
            int var3 = this.field4157.method13942(0, var2, -461842083) - 1;
            this.field4157.method13935(var3, classZD.method13896(this.field4157, 1863939241), 1152939592);
            var1 = true;
         }

         if (var1) {
            int var6 = -698145269 * this.field4155;
            int var4 = 1072642345 * this.field4150;
            int var5 = classZD.method13896(this.field4157, -1402302409);
            if (this.field4155 * -698145269 > var5) {
               var6 = var5;
            }

            if (1072642345 * this.field4150 > var5) {
               var4 = var5;
            }

            this.method6930(var4, var6, 1122842785);
         }

         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("kh")
   void method7115(int var1, boolean var2) {
      if (var2) {
         this.method6930(1072642345 * this.field4150, var1, 1122842785);
      } else {
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("kc")
   void method7116(int var1, boolean var2) {
      if (var2) {
         this.method6930(1072642345 * this.field4150, var1, 1122842785);
      } else {
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ObfuscatedSignature(descriptor = "(IZ)V")
   @ObfuscatedName("kl")
   void method7117(int var1, boolean var2) {
      if (var2) {
         this.method6930(-636870908 * this.field4150, var1, 1122842785);
      } else {
         this.method6930(var1, var1, 1122842785);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("jr")
   public static void method6964(classLH var0, boolean var1) {
      if (var0.field4155 * 2070233846 > 0) {
         var0.method7114(var0.field4157.method13942(var0.field4155 * -49688484, -1, -1107810966), var1, -899536712);
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fk")
   public void method6938(boolean var1) {
      classWB var2 = this.method7106(1134208878 * this.field4155, -378442949);
      this.method7114((Integer)var2.field6653, var1, -1238332320);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lp")
   int method7119() {
      return 236265915 * this.field4151 / classZD.method13953(this.field4157, (byte)17);
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("cr")
   public int method7038(byte var1) {
      try {
         return -698145269 * this.field4155;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cr(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ky")
   int method7120() {
      return 236265915 * this.field4151 / classZD.method13953(this.field4157, (byte)6);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("lm")
   int method7121() {
      return 236265915 * this.field4151 / classZD.method13953(this.field4157, (byte)62);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("zn")
   public static void method6959(classLH var0, boolean var1) {
      if (var0 == null) {
         var0.method6940(var1);
      }

      if (-698145269 * var0.field4155 < classZD.method13896(var0.field4157, 2130163215)) {
         classWB var2 = var0.method7102(1 + var0.field4155 * -698145269, 2129664245);
         var0.method7114((Integer)var2.field6653, var1, -2025593619);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("lu")
   void method7125() {
      classAAG var1 = this.field4157.method13915(0, -1559125646 * this.field4155, 1631990628);
      classWB var2 = classAAG.method213(var1, (byte)-117);
      int var3 = classZD.method13953(this.field4157, (byte)-64);
      int var4 = (Integer)var2.field6652 - 10;
      int var5 = var4 + 20;
      int var6 = (Integer)var2.field6653 - 3;
      int var7 = var6 + 6 + var3;
      int var8 = -1379499806 * this.field4152;
      int var9 = 2084178789 * this.field4156 + var8;
      int var10 = -457263387 * this.field4158;
      int var11 = 236265915 * this.field4151 + var10;
      int var12 = 81567582 * this.field4152;
      int var13 = this.field4158 * -457263387;
      if (var4 < var8) {
         var12 = var4;
      } else if (var5 > var9) {
         var12 = var5 - 2084178789 * this.field4156;
      }

      if (var6 < var10) {
         var13 = var6;
      } else if (var7 > var11) {
         var13 = var7 - 236265915 * this.field4151;
      }

      this.method6884(var12, var13, -524743527);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("ls")
   public static void method6965(classLH var0, boolean var1) {
      if (var0.field4155 * -698145269 > 0) {
         var0.method7114(var0.field4157.method13942(var0.field4155 * -698145269, -1, -1298631965), var1, -1699655484);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;ZI)V")
   @ObfuscatedName("oe")
   public static void method6939(classLH var0, boolean var1, int var2) {
      if (var0 == null) {
         var0.method6981(var1, var2);
      } else {
         try {
            classWB var3 = var0.method7106(-698145269 * var0.field4155, -1177535132);
            var0.method7114((Integer)var3.field6653, var1, -1342884739);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "lh.am(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("wn")
   public static int method7057(classLH var0) {
      return var0.field4140 * -865118543;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;)I")
   @ObfuscatedName("xb")
   public static int method6852(classPV var0) {
      return var0.field5430 * 257906651;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("lb")
   boolean method7131(int var1) {
      return -311851207 == var1 || var1 == 10 || var1 == 9;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cy")
   public int method7049(int var1) {
      try {
         return -457263387 * this.field4158;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cp(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ip")
   public int method7050() {
      return -457263387 * this.field4158;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dy")
   void method7135() {
      if (this.field4164 != null) {
         this.field4164.vmethod147(-1927177938);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("lc")
   boolean method7138(int var1) {
      switch (this.field4148 * -1136623217) {
         case 1:
            return classSN.method10575((char)var1, 1931919572);
         case 2:
            return classTT.method11222((char)var1, -1342668229);
         case 3:
            return classEB.method3818((char)var1, (byte)-81);
         case 4:
            char var2 = (char)var1;
            if (classEB.method3818(var2, (byte)104)) {
               return true;
            } else {
               if (var2 != 'k' && var2 != 'K' && 'm' != var2 && 'M' != var2 && var2 != 'b' && 'B' != var2) {
                  return false;
               }

               return true;
            }
         default:
            return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lwb;")
   @ObfuscatedName("lv")
   classWB method7142() {
      int var1 = this.field4157.method13993(this.field4156 * 2084178789, (byte)-82);
      int var2 = classZD.method13995(this.field4157, this.field4151 * -1536341234, 2052033943);
      return new classWB(var1, var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Laaa;)Z")
   @ObfuscatedName("yg")
   public static boolean method6861(classLH var0, AbstractFont var1) {
      if (var0 == null) {
         var0.method6862(var1);
      }

      boolean var2 = !var0.field4145;
      var0.field4157.method13905(var1, -1905729892);
      var0.field4142.method13905(var1, 1240189256);
      var0.field4145 = true;
      var2 |= var0.method6884(var0.field4152 * -1184890025, var0.field4158 * -457263387, 714673537);
      var2 |= var0.method6930(var0.field4150 * 1072642345, -698145269 * var0.field4155, 1122842785);
      if (var0.method7111((byte)-99)) {
         method7133(var0, (byte)72);
         var2 = true;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jn")
   public int method7068() {
      return this.field4157.method13982(1847526189);
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("aj")
   public boolean method6889(int var1, int var2, int var3) {
      try {
         boolean var4;
         label40: {
            var4 = true;
            if (var1 >= 0) {
               if (var3 >= 585054447) {
                  throw new IllegalStateException();
               }

               if (var1 <= 2) {
                  break label40;
               }

               if (var3 >= 585054447) {
                  throw new IllegalStateException();
               }
            }

            var4 = false;
         }

         if (var2 < 0 || var2 > 2) {
            var4 = false;
         }

         if (var4) {
            if (var3 >= 585054447) {
               throw new IllegalStateException();
            } else {
               return classZD.method13910(this.field4157, var1, var2, -142603297);
            }
         } else {
            return false;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.aj(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("cz")
   public static void method6986(classLH var0, boolean var1) {
      classAAG var2 = var0.field4157.method13915(0, -698145269 * var0.field4155, 1569814670);
      classWB var3 = classAAG.method213(var2, (byte)-87);
      var0.method7114(
         var0.field4157.method13939((Integer)var3.field6652, 236265915 * var0.field4151 + var0.field4158 * -457263387, -66543215), var1, -815356890
      );
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bv")
   static int method7145(int var0) {
      try {
         byte var10000;
         if (client.field862) {
            if (var0 == -1798574594) {
               throw new IllegalStateException();
            }

            var10000 = 2;
         } else {
            var10000 = 1;
         }

         return var10000;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "lh.bv(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ad")
   public void method6894(int var1) {
      this.field4142.method13913(var1, (byte)119);
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("aa")
   public boolean method6882(int var1, byte var2) {
      try {
         this.field4151 = 1211480737 * var1;
         if (this.method7111((byte)-78)) {
            if (var2 == 0) {
               throw new IllegalStateException();
            } else {
               method7133(this, (byte)33);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("dl")
   public boolean method7132(int var1, int var2) {
      try {
         if (32 != var1) {
            if (var2 >= 1053045388) {
               throw new IllegalStateException();
            }

            if (var1 != 10 && var1 != 9) {
               return false;
            }
         }

         return true;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.cm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bk")
   public void method6981(boolean var1, int var2) {
      try {
         classAAG var3 = this.field4142.method13915(0, this.field4156 * -698145269, 1003674895);
         classWB var4 = classAAG.method213(var3, (byte)-25);
         this.method7114(this.field4157.method13939((Integer)var4.field6653, this.field4147 * -457263387, -683973827), var1, -1667191298);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("bc")
   public void method6944(boolean var1, byte var2) {
      try {
         this.method7114(0, var1, -711214799);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.ah(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("bm")
   public void method6953(boolean var1, byte var2) {
      try {
         if (this.method7089(2012791869)) {
            if (var2 <= 10) {
               throw new IllegalStateException();
            }

            if (!var1) {
               this.method7114(Math.max(this.field4150 * 1072642345, -698145269 * this.field4158), var1, -1470377285);
               return;
            }
         }

         if (this.field4149 * -698145269 < classZD.method13896(this.field4157, 69845744)) {
            if (var2 <= 10) {
               throw new IllegalStateException();
            }

            this.method7114(1 + -698145269 * this.field4151, var1, -1257110877);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("bg")
   public void method6971(boolean var1, int var2) {
      try {
         if (this.field4140 * -698145269 < classZD.method13896(this.field4142, 1117277900)) {
            if (var2 == 1461892107) {
               return;
            }

            this.method7114(this.field4142.method13942(this.field4152 * -698145269, 1, -533399198), var1, -1459067041);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)V")
   @ObfuscatedName("ba")
   public void method6994(int var1, int var2, int var3) {
      try {
         if (this.field4143) {
            if (var3 == 610066815) {
               throw new IllegalStateException();
            }

            if (this.method6897(1491129216)) {
               if (var3 == 610066815) {
                  return;
               }

               var1 += this.field4146 * -1184890025;
               var2 += -457263387 * this.field4144;
               classWB var4 = method7139(this, -1079082866);
               int var5 = this.field4157.method13935(var1 - (Integer)var4.field6652, var2 - (Integer)var4.field6653, 924454399);
               switch (this.field4140 * -1083366195) {
                  case 0:
                     this.method6889(1072642345 * this.field4149, var5, 1122842785);
                     break;
                  case 1:
                     classWB var11 = this.method7102(-1558750221 * this.field4149, 1833946392);
                     classWB var12 = this.method7106(var5, 925050001);
                     method7099(this, var11, var12, 1801667578);
                     break;
                  case 2:
                     classWB var6 = this.method7106(this.field4152 * -1558750221, -1848636692);
                     classWB var7 = this.method7102(var5, -355309306);
                     method7099(this, var6, var7, 1801667578);
               }
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "lh.ba(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/datatransfer/Clipboard;I)V")
   @ObfuscatedName("bi")
   public void method7003(Clipboard var1, int var2) {
      try {
         Transferable var3 = var1.getContents(null);
         if (var3 != null && var3.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            if (var2 <= -324749371) {
               return;
            }

            try {
               String var4 = method7095(this, (String)var3.getTransferData(DataFlavor.stringFlavor), 2013546798);
               this.method7089(455461300);
               classZP var5 = this.field4157.method13921(var4, -698145269 * this.field4147, this.field4156 * 1030611083, (byte)39);
               this.method6889(classZP.method14129(var5, -589811403), classZP.method14129(var5, -391538325), 1122842785);
               this.method7111((byte)-5);
               method7133(this, (byte)39);
            } catch (Exception var6) {
            }
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "lh.bi(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("by")
   public void method7005(byte var1) {
      try {
         this.field4151 = Math.max(0, this.field4150 * -457263387 - classZD.method13953(this.field4157, (byte)54)) * -1673193747;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.be(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Lwb;")
   @ObfuscatedName("iv")
   public static classWB method7110(classLH var0, int var1) {
      int var2 = classZD.method13896(var0.field4157, 1473584921);
      int var3 = 0;
      int var4 = var2;

      for (int var5 = var1; var5 > 0; var5--) {
         if (var0.field4157.method13883(var5 - 1, -1052717093).field117 == '\n') {
            var3 = var5;
            break;
         }
      }

      for (int var6 = var1; var6 < var2; var6++) {
         if (var0.field4157.method13883(var6, -1052717093).field117 == '\n') {
            var4 = var6;
            break;
         }
      }

      return new classWB(var3, var4);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ce")
   void method6843(int var1) {
      try {
         this.field4154 = (1 + this.field4151 * -1569689927) % 60 * -1662830711;
         if (1398532195 * this.field4152 > 0) {
            if (var1 == 643906573) {
               return;
            }

            this.field4144 = this.field4151 - -373434037;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)V")
   @ObfuscatedName("kt")
   public static void method7126(classLH var0) {
      classAAG var1 = var0.field4157.method13915(0, -698145269 * var0.field4155, 1641481856);
      classWB var2 = classAAG.method213(var1, (byte)-74);
      int var3 = classZD.method13953(var0.field4157, (byte)-7);
      int var4 = (Integer)var2.field6652 - 10;
      int var5 = var4 + 20;
      int var6 = (Integer)var2.field6653 - 3;
      int var7 = var6 + 6 + var3;
      int var8 = -1184890025 * var0.field4152;
      int var9 = 2084178789 * var0.field4156 + var8;
      int var10 = -457263387 * var0.field4158;
      int var11 = 236265915 * var0.field4151 + var10;
      int var12 = -1184890025 * var0.field4152;
      int var13 = var0.field4158 * -457263387;
      if (var4 < var8) {
         var12 = var4;
      } else if (var5 > var9) {
         var12 = var5 - 2084178789 * var0.field4156;
      }

      if (var6 < var10) {
         var13 = var6;
      } else if (var7 > var11) {
         var13 = var7 - 236265915 * var0.field4151;
      }

      var0.method6884(var12, var13, 493636127);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cp")
   public int method7046(int var1) {
      try {
         return -1184890025 * this.field4154;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.ct(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IS)V")
   @ObfuscatedName("co")
   public void method7073(int var1, short var2) {
      try {
         this.field4157.method13970(var1, 879949851);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.co(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Llh;Llm;I)V")
   @ObfuscatedName("de")
   public static void method7015(classLH var0, classLM var1, int var2) {
      if (var0 == null) {
         var0.method7016(var1, var2);
      } else {
         try {
            var0.field4165 = var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "lh.bf(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwb;Lwb;I)V")
   @ObfuscatedName("cz")
   void method7100(classWB var1, classWB var2, int var3) {
      try {
         if ((Integer)var2.field6652 < (Integer)var1.field6653) {
            if (var3 != 1801667578) {
               throw new IllegalStateException();
            }

            this.method6930((Integer)var1.field6652, (Integer)var2.field6652, 1122842785);
         } else {
            this.method6930((Integer)var1.field6652, (Integer)var2.field6653, 1122842785);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.cz(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("dc")
   void method7136(byte var1) {
      try {
         if (this.field4164 != null) {
            if (var1 <= 8) {
               return;
            }

            this.field4165.vmethod147(-1854632780);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.dc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lwb;")
   @ObfuscatedName("kj")
   classWB method7143(int var1) {
      try {
         int var2 = this.field4157.method13993(this.field4156 * 2084178789, (byte)-38);
         int var3 = classZD.method13995(this.field4157, this.field4147 * 236265915, -605299333);
         return new classWB(var2, var3);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.dm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gr")
   public void method6951(boolean var1) {
      if (this.method7130(2012791869) && !var1) {
         this.method7114(Math.min(this.field4144 * 1072642345, -698145269 * this.field4146), var1, -736398567);
      } else if (-698145269 * this.field4140 > 0) {
         this.method7114(this.field4149 * -698145269 - 1, var1, -1471273416);
      }
   }

   @ObfuscatedSignature(descriptor = "(Laaa;)Z")
   @ObfuscatedName("dv")
   boolean method6862(AbstractFont var1) {
      boolean var2 = !this.field4145;
      this.field4142.method13905(var1, -1905729892);
      this.field4157.method13905(var1, 1240189256);
      this.field4145 = true;
      var2 |= this.method6930(this.field4140 * -1184890025, this.field4150 * -457263387, 714673537);
      var2 |= this.method6889(this.field4158 * 1072642345, -698145269 * this.field4154, 1122842785);
      if (this.method7111((byte)-99)) {
         method7133(this, (byte)72);
         var2 = true;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ey")
   public boolean method6875(int var1) {
      this.field4157.method13978(var1, (byte)-54);
      if (this.method7111((byte)-92)) {
         method7133(this, (byte)71);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ax")
   public boolean method6868(int var1, int var2, int var3) {
      try {
         boolean var10000;
         label22: {
            if (2084178789 * this.field4153 == var1) {
               if (var3 >= -483624883) {
                  throw new IllegalStateException();
               }

               if (this.field4149 * 236265915 == var2) {
                  var10000 = false;
                  break label22;
               }
            }

            var10000 = true;
         }

         boolean var4 = var10000;
         this.field4144 = -1191629203 * var1;
         this.field4147 = -526943373 * var2;
         return var4 | this.method6884(this.field4148 * -1184890025, this.field4144 * -457263387, -1950004505);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "lh.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("au")
   public void method6912(int var1) {
      try {
         if (!this.method7131(455461300)) {
            if (var1 >= -1927903443) {
               throw new IllegalStateException();
            }

            if (-698145269 * this.field4140 > 0) {
               if (var1 >= -1927903443) {
                  throw new IllegalStateException();
               }

               int var2 = this.field4142.method13940(this.field4158 * -698145269 - 1, -1596338929);
               method7133(this, (byte)75);
               this.method6930(var2, var2, 1122842785);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Z")
   @ObfuscatedName("ab")
   public boolean method6856(String var1, int var2) {
      try {
         String var3 = this.field4157.method13899((byte)-94);
         if (!var3.equals(var1)) {
            if (var2 == -1187145812) {
               throw new IllegalStateException();
            } else {
               var1 = method7095(this, var1, 2103407397);
               this.field4157.method13931(var1, 1731040047);
               this.method6868(this.field4148 * 1072642345, this.field4147 * -698145269, 1122842785);
               this.method6868(this.field4155 * -1184890025, -457263387 * this.field4154, -1937779588);
               this.method7111((byte)-97);
               method7133(this, (byte)83);
               return true;
            }
         } else {
            return false;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lh.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("cx")
   int method7122(int var1) {
      try {
         return 236265915 * this.field4155 / classZD.method13953(this.field4142, (byte)-26);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "lh.cx(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ju")
   public int method7054() {
      return classZD.method13965(this.field4142, 134253707);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("gz")
   public void method6940(boolean var1) {
      classWB var2 = this.method7106(-722278368 * this.field4144, -399582529);
      this.method7114((Integer)var2.field6653, var1, -2053657812);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("jj")
   public int method7069() {
      return this.field4157.method13974(1212092495);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("ft")
   public static boolean method6883(classLH var0, int var1) {
      if (var0 == null) {
         return var0.method6885(var1, var1);
      } else {
         var0.field4140 = 1211480737 * var1;
         if (var0.method7111((byte)-127)) {
            method7133(var0, (byte)54);
            return true;
         } else {
            return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Lwb;Lwb;)V")
   @ObfuscatedName("qi")
   public static void method7101(classLH var0, classWB var1, classWB var2) {
      if (var0 == null) {
         var0.method7092();
      }

      if ((Integer)var2.field6652 < (Integer)var1.field6652) {
         var0.method6930((Integer)var1.field6653, (Integer)var2.field6652, 1122842785);
      } else {
         var0.method6930((Integer)var1.field6652, (Integer)var2.field6653, 1122842785);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("sq")
   public static boolean method6876(classLH var0, int var1) {
      var0.field4157.method13978(var1, (byte)-106);
      if (var0.method7111((byte)-56)) {
         method7133(var0, (byte)12);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Llm;I)V")
   @ObfuscatedName("bf")
   public void method7016(classLM var1, int var2) {
      try {
         this.field4164 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "lh.bf(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/lang/String;")
   @ObfuscatedName("ch")
   String method7097(String var1, int var2) {
      try {
         StringBuilder var3 = new StringBuilder(var1.length());

         for (int var4 = 0; var4 < var1.length(); var4++) {
            if (var2 <= 1906148269) {
               throw new IllegalStateException();
            }

            char var5 = var1.charAt(var4);
            if (this.method7129(var5, -32389191)) {
               if (var2 <= 1906148269) {
                  throw new IllegalStateException();
               }

               var3.append(var5);
            }
         }

         return var3.toString();
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "lh.ch(" + ')');
      }
   }
}
