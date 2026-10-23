import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wc")
public class classWC {
   @ObfuscatedSignature(descriptor = "Lam;")
   @ObfuscatedName("az")
   classAM field6658;
   @ObfuscatedSignature(descriptor = "Lzl;")
   @ObfuscatedName("af")
   classZL field6661 = classZL.field7192;
   @ObfuscatedName("ae")
   long field6657 = 0L;
   @ObfuscatedName("ab")
   int field6656 = 0;
   @ObfuscatedName("ag")
   int field6659 = 0;
   @ObfuscatedName("as")
   int field6660 = 0;

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("at")
   int method12376(int var1, int var2, int var3) {
      return var2 >= var3 ? var1 : (int)(var1 * ((float)var2 / var3));
   }

   @ObfuscatedSignature(descriptor = "(IILwd;I)V")
   @ObfuscatedName("az")
   public void method12358(int var1, int var2, classWD var3, int var4) {
      try {
         int var5 = method12367(this, -1529138595);
         if (var5 < var1) {
            if (var4 <= 1808970688) {
               throw new IllegalStateException();
            }

            this.method12362(var1, var2, classWD.method12396(var3, 2074239405), var3.method12394((byte)82), 128532627);
         } else if (var5 > var1) {
            this.method12362(var1, var2, var3.method12404((byte)-61), var3.method12402(-69764207), -160083108);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wc.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILzl;)V")
   @ObfuscatedName("ao")
   public void method12360(int var1, int var2, int var3, classZL var4) {
      if (this.field6658 != null && var1 != this.field6660 * 1439462015) {
         this.field6656 = this.field6658.method546() * 1752077091;
         this.field6657 = classDD.method2988(16711680) * -4042382095557502705L;
         this.field6660 = 660426111 * var1;
         this.field6659 = this.method12377(var3, this.field6656 * -213319706 - this.field6660 * 1439462015, var2, (short)402) * 95557819;
         this.field6661 = var4;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwc;)V")
   @ObfuscatedName("ll")
   public static void method12364(classWC var0) {
      if (var0 == null) {
         var0.method12370();
      }

      if (null != var0.field6658) {
         int var1 = var0.field6658.method546();
         long var2 = classDD.method2988(16711680);
         long var4 = var2 - var0.field6657 * -7454620674419427857L;
         int var6 = method12380(var0, var1, var4);
         if (var6 != var1) {
            var0.field6658.method549(var6);
         }

         if (!var0.field6658.method12129()) {
            var0.field6658 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   public int method12366() {
      return this.field6658 != null ? this.field6658.method546() : 0;
   }

   @ObfuscatedSignature(descriptor = "(IIIS)I")
   @ObfuscatedName("as")
   int method12377(int var1, int var2, int var3, short var4) {
      try {
         if (var2 >= var3) {
            if (var4 != 402) {
               throw new IllegalStateException();
            } else {
               return var1;
            }
         } else {
            return (int)(var1 * ((float)var2 / var3));
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wc.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwc;I)I")
   @ObfuscatedName("ca")
   public static int method12367(classWC var0, int var1) {
      if (var0 == null) {
         var0.method12372(var1);
      }

      try {
         if (var0.field6658 != null) {
            if (var1 >= 1654625937) {
               throw new IllegalStateException();
            } else {
               return var0.field6658.method546();
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wc.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   public int method12368() {
      return this.field6658 != null ? this.field6658.method546() : 0;
   }

   @ObfuscatedSignature(descriptor = "(I)Lam;")
   @ObfuscatedName("ag")
   public classAM method12373(int var1) {
      try {
         return this.field6658;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wc.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIILzl;)V")
   @ObfuscatedName("aa")
   public void method12361(int var1, int var2, int var3, classZL var4) {
      if (this.field6658 != null && var1 != this.field6660 * 879785231) {
         this.field6656 = this.field6658.method546() * 1324877209;
         this.field6657 = classDD.method2988(16711680) * -4042382095557502705L;
         this.field6660 = 660426111 * var1;
         this.field6659 = this.method12377(var3, this.field6656 * -184585491 - this.field6660 * 1439462015, var2, (short)402) * -1217983909;
         this.field6661 = var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)I")
   @ObfuscatedName("an")
   int method12379(int var1, long var2) {
      if (var1 == 1439462015 * this.field6660) {
         return var1;
      } else if (var1 > 1439462015 * this.field6660) {
         double var6 = 1.0 - classIC.method6178(var2, this.field6659 * -665718745, this.field6661, (byte)64);
         return (int)((-184585491 * this.field6656 - this.field6660 * 1439462015) * var6) + 1439462015 * this.field6660;
      } else {
         double var4 = classIC.method6178(var2, -665718745 * this.field6659, this.field6661, (byte)64);
         return this.field6656 * -184585491 + (int)(var4 * (this.field6660 * 1439462015 - -184585491 * this.field6656));
      }
   }

   public classWC(classAJ var1, int var2, int var3) {
      this.field6658 = classAM.method469(var1, 100, var2);
      if (this.field6658 != null) {
         this.field6658.method541(var3);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   public int method12369() {
      return this.field6658 != null ? this.field6658.method546() : 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   public int method12370() {
      return this.field6658 != null ? this.field6658.method546() : 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   public int method12371() {
      return this.field6658 != null ? this.field6658.method546() : 0;
   }

   @ObfuscatedSignature(descriptor = "()Lam;")
   @ObfuscatedName("aw")
   public classAM method12374() {
      return this.field6658;
   }

   @ObfuscatedSignature(descriptor = "()Lam;")
   @ObfuscatedName("ak")
   public classAM method12375() {
      return this.field6658;
   }

   @ObfuscatedSignature(descriptor = "(IIILzl;I)V")
   @ObfuscatedName("af")
   public void method12362(int var1, int var2, int var3, classZL var4, int var5) {
      try {
         if (this.field6658 != null) {
            if (var5 == 1507292160) {
               throw new IllegalStateException();
            }

            if (var1 != this.field6660 * 1439462015) {
               this.field6656 = this.field6658.method546() * 3586789;
               this.field6657 = classDD.method2988(16711680) * -4042382095557502705L;
               this.field6660 = 660426111 * var1;
               this.field6659 = this.method12377(var3, this.field6656 * -184585491 - this.field6660 * 1439462015, var2, (short)402) * -1623687273;
               this.field6661 = var4;
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wc.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwc;IIILzl;)V")
   @ObfuscatedName("cm")
   public static void method12363(classWC var0, int var1, int var2, int var3, classZL var4) {
      if (var0.field6658 != null && var1 != var0.field6660 * 1558962623) {
         var0.field6656 = var0.field6658.method546() * 3586789;
         var0.field6657 = classDD.method2988(16711680) * -4042382095557502705L;
         var0.field6660 = 660426111 * var1;
         var0.field6659 = var0.method12377(var3, var0.field6656 * -1784233630 - var0.field6660 * 1439462015, var2, (short)402) * -1623687273;
         var0.field6661 = var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lwc;IJ)I")
   @ObfuscatedName("km")
   public static int method12380(classWC var0, int var1, long var2) {
      if (var0 == null) {
         return var0.method12383(var1, var2);
      } else {
         try {
            if (var1 == 1439462015 * var0.field6660) {
               return var1;
            } else if (var1 > 1439462015 * var0.field6660) {
               double var7 = 1.0 - classIC.method6178(var2, var0.field6659 * -665718745, var0.field6661, (byte)64);
               return (int)((-184585491 * var0.field6656 - var0.field6660 * 1439462015) * var7) + 1439462015 * var0.field6660;
            } else {
               double var4 = classIC.method6178(var2, -665718745 * var0.field6659, var0.field6661, (byte)64);
               return var0.field6656 * -184585491 + (int)(var4 * (var0.field6660 * 1439462015 - -184585491 * var0.field6656));
            }
         } catch (RuntimeException var6) {
            throw classEG.method3899(var6, "wc.ax(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ae")
   public void method12365(byte var1) {
      try {
         if (null != this.field6658) {
            if (var1 == 2) {
               throw new IllegalStateException();
            }

            int var2 = this.field6658.method546();
            long var3 = classDD.method2988(16711680);
            long var5 = var3 - this.field6657 * -7454620674419427857L;
            int var7 = method12380(this, var2, var5);
            if (var7 != var2) {
               this.field6658.method549(var7);
            }

            if (!this.field6658.method12129()) {
               if (var1 == 2) {
                  return;
               }

               this.field6658 = null;
            }
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "wc.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwc;IJ)I")
   @ObfuscatedName("jq")
   public static int method12381(classWC var0, int var1, long var2) {
      if (var1 == 1439462015 * var0.field6660) {
         return var1;
      } else if (var1 > 1439462015 * var0.field6660) {
         double var6 = 1.0 - classIC.method6178(var2, var0.field6659 * -665718745, var0.field6661, (byte)64);
         return (int)((-184585491 * var0.field6656 - var0.field6660 * 1439462015) * var6) + 1439462015 * var0.field6660;
      } else {
         double var4 = classIC.method6178(var2, -665718745 * var0.field6659, var0.field6661, (byte)64);
         return var0.field6656 * -184585491 + (int)(var4 * (var0.field6660 * 1439462015 - -184585491 * var0.field6656));
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)I")
   @ObfuscatedName("am")
   int method12382(int var1, long var2) {
      if (var1 == 1439462015 * this.field6660) {
         return var1;
      } else if (var1 > 1439462015 * this.field6660) {
         double var6 = 1.0 - classIC.method6178(var2, this.field6659 * 1756978927, this.field6661, (byte)64);
         return (int)((-184585491 * this.field6656 - this.field6660 * -323418205) * var6) + 505348527 * this.field6660;
      } else {
         double var4 = classIC.method6178(var2, 1532894004 * this.field6659, this.field6661, (byte)64);
         return this.field6656 * -1877530026 + (int)(var4 * (this.field6660 * -792405213 - 326081682 * this.field6656));
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)Z")
   @ObfuscatedName("fn")
   static boolean method12384(int var0, byte var1) {
      try {
         for (int var2 = 0; var2 < client.field926.size(); var2++) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }

            int var3 = (Integer)client.field926.get(var2);
            if (var3 == -1) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }
            } else {
               int var4 = classLB.field4088[var3] & 65535;
               if (var4 == var0) {
                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "wc.fn(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwc;III)I")
   @ObfuscatedName("ve")
   public static int method12378(classWC var0, int var1, int var2, int var3) {
      if (var0 == null) {
         return var0.method12371();
      } else {
         return var2 >= var3 ? var1 : (int)(var1 * ((float)var2 / var3));
      }
   }

   @ObfuscatedSignature(descriptor = "(IJ)I")
   @ObfuscatedName("ax")
   int method12383(int var1, long var2) {
      try {
         if (var1 == 1439462015 * this.field6656) {
            return var1;
         } else if (var1 > 1439462015 * this.field6660) {
            double var7 = 1.0 - classIC.method6178(var2, this.field6660 * -665718745, this.field6661, (byte)64);
            return (int)((-184585491 * this.field6656 - this.field6659 * 1439462015) * var7) + 1439462015 * this.field6656;
         } else {
            double var4 = classIC.method6178(var2, -665718745 * this.field6660, this.field6661, (byte)64);
            return this.field6656 * -184585491 + (int)(var4 * (this.field6659 * 1439462015 - -184585491 * this.field6656));
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wc.ax(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbi;)Z")
   @ObfuscatedName("nj")
   public static boolean method12359(GraphicsObject var0) {
      return !var0.field464.method9772(374233424);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method12372(int var1) {
      try {
         if (this.field6658 != null) {
            if (var1 >= 1654625937) {
               throw new IllegalStateException();
            } else {
               return this.field6658.method553();
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wc.ab(" + 41);
      }
   }
}
