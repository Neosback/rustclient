import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bt")
public class classBT {
   @ToRemove(unused = "true")
   @ObfuscatedName("bf")
   static final int field576 = 92;
   @ObfuscatedName("az")
   int field573;
   @ObfuscatedName("af")
   int field574 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field575 = 3;
   @ObfuscatedName("ae")
   byte[] field572;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbt;I)I")
   @ObfuscatedName("go")
   public static int method1232(classBT var0, int var1) {
      if (var0 == null) {
         var0.method1235(var1);
      }

      int var2 = 0;
      int var3 = 0;

      while (var1 >= 8 - -573840275 * var0.field573) {
         int var4 = 8 - var0.field573 * -573840275;
         int var5 = (1 << var4) - 1;
         var2 += (var0.field572[var0.field574 * -1863749917] >> var0.field573 * -573840275 & var5) << var3;
         var0.field573 = 0;
         var0.field574 += -57588021;
         var3 += var4;
         var1 -= var4;
      }

      if (var1 > 0) {
         int var6 = (1 << var1) - 1;
         var2 += (var0.field572[-1863749917 * var0.field574] >> -573840275 * var0.field573 & var6) << var3;
         var0.field573 += 75744613 * var1;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   int method1238() {
      int var1 = this.field572[-1863749917 * this.field574] >> this.field573 * -573840275 & 1;
      this.field573 += 75744613;
      this.field574 = this.field574 + (-573840275 * this.field573 >> 3) * -57588021;
      this.field573 = 75744613 * (this.field573 * -573840275 & 7);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   int method1233(int var1) {
      int var2 = 0;
      int var3 = 0;

      while (var1 >= 8 - -573840275 * this.field573) {
         int var4 = 8 - this.field573 * -573840275;
         int var5 = (1 << var4) - 1;
         var2 += (this.field572[this.field574 * -1863749917] >> this.field573 * -573840275 & var5) << var3;
         this.field573 = 0;
         this.field574 += -57588021;
         var3 += var4;
         var1 -= var4;
      }

      if (var1 > 0) {
         int var6 = (1 << var1) - 1;
         var2 += (this.field572[-1863749917 * this.field574] >> -573840275 * this.field573 & var6) << var3;
         this.field573 += 75744613 * var1;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("ae")
   void method1242(byte[] var1, int var2, int var3) {
      try {
         this.field572 = var1;
         this.field574 = -57588021 * var2;
         this.field573 = 0;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bt.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbt;)I")
   @ObfuscatedName("mx")
   public static int method1239(classBT var0) {
      int var1 = var0.field572[-1863749917 * var0.field574] >> var0.field573 * 1246820109 & 1;
      var0.field573 += 75744613;
      var0.field574 = var0.field574 + (-573840275 * var0.field573 >> 3) * -57588021;
      var0.field573 = 730965295 * (var0.field573 * -1333644747 & 7);
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbt;)I")
   @ObfuscatedName("lz")
   public static int method1240(classBT var0) {
      int var1 = var0.field572[-1863749917 * var0.field574] >> var0.field573 * -573840275 & 1;
      var0.field573 += 75744613;
      var0.field574 = var0.field574 + (-573840275 * var0.field573 >> 3) * -57588021;
      var0.field573 = 75744613 * (var0.field573 * -573840275 & 7);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lbt;II)I")
   @ObfuscatedName("ch")
   public static int method1234(classBT var0, int var1, int var2) {
      if (var0 == null) {
         return var0.method1237(var1, var1);
      } else {
         try {
            int var3 = 0;
            int var4 = 0;

            while (var1 >= 8 - -573840275 * var0.field573) {
               if (var2 >= -1911617180) {
                  throw new IllegalStateException();
               }

               int var5 = 8 - var0.field573 * -573840275;
               int var6 = (1 << var5) - 1;
               var3 += (var0.field572[var0.field574 * -1863749917] >> var0.field573 * -573840275 & var6) << var4;
               var0.field573 = 0;
               var0.field574 += -57588021;
               var4 += var5;
               var1 -= var5;
            }

            if (var1 > 0) {
               if (var2 >= -1911617180) {
                  throw new IllegalStateException();
               }

               int var8 = (1 << var1) - 1;
               var3 += (var0.field572[-1863749917 * var0.field574] >> -573840275 * var0.field573 & var8) << var4;
               var0.field573 += 75744613 * var1;
            }

            return var3;
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "bt.az(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   int method1235(int var1) {
      int var2 = 0;
      int var3 = 0;

      while (var1 >= 8 - -573840275 * this.field573) {
         int var4 = 8 - this.field573 * -573840275;
         int var5 = (1 << var4) - 1;
         var2 += (this.field572[this.field574 * -1863749917] >> this.field573 * -573840275 & var5) << var3;
         this.field573 = 0;
         this.field574 += -57588021;
         var3 += var4;
         var1 -= var4;
      }

      if (var1 > 0) {
         int var6 = (1 << var1) - 1;
         var2 += (this.field572[-1863749917 * this.field574] >> -573840275 * this.field573 & var6) << var3;
         this.field573 += 75744613 * var1;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   int method1236(int var1) {
      int var2 = 0;
      int var3 = 0;

      while (var1 >= 8 - -573840275 * this.field573) {
         int var4 = 8 - this.field573 * -573840275;
         int var5 = (1 << var4) - 1;
         var2 += (this.field572[this.field574 * -1863749917] >> this.field573 * -573840275 & var5) << var3;
         this.field573 = 0;
         this.field574 += -57588021;
         var3 += var4;
         var1 -= var4;
      }

      if (var1 > 0) {
         int var6 = (1 << var1) - 1;
         var2 += (this.field572[-1863749917 * this.field574] >> -573840275 * this.field573 & var6) << var3;
         this.field573 += 75744613 * var1;
      }

      return var2;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   int method1241(int var1) {
      try {
         int var2 = this.field572[-1863749917 * this.field574] >> this.field573 * -573840275 & 1;
         this.field573 += 75744613;
         this.field574 = this.field574 + (-573840275 * this.field573 >> 3) * -57588021;
         this.field573 = 75744613 * (this.field573 * -573840275 & 7);
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bt.af(" + 41);
      }
   }

   classBT() {
      this.field573 = 0;
      this.field572 = null;
   }

   @ObfuscatedSignature(descriptor = "([BI)V")
   @ObfuscatedName("ay")
   void method1243(byte[] var1, int var2) {
      this.field572 = var1;
      this.field574 = 1742419465 * var2;
      this.field573 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Ldh;Ldz;Ldz;IIB)I")
   @ObfuscatedName("bf")
   static int method1244(Actor var0, WorldView var1, WorldView var2, int var3, int var4, byte var5) {
      try {
         classUZ var6 = classIK.method6261(var0.field1484 * -1547553299, 0.0F, var0.field1486 * -1272026483, (byte)97);
         classUZ var7;
         if (var2.field1697 * 2140889407 != 2140889407 * var1.field1697) {
            if (var5 != 1) {
               throw new IllegalStateException();
            }

            if (0 == 2140889407 * var1.field1697) {
               WorldEntity var8 = (WorldEntity)classIS.field3051.field1698.method13637(2140889407 * var2.field1697);
               var7 = var8.method10666(var3, var4, (byte)-47);
            } else if (2140889407 * var2.field1697 == 0) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               WorldEntity var13 = (WorldEntity)classIS.field3051.field1698.method13637(var1.field1697 * 2140889407);
               var7 = var13.method10667(var3, var4, (byte)6);
            } else {
               WorldEntity var14 = (WorldEntity)classIS.field3051.field1698.method13637(var2.field1697 * 2140889407);
               WorldEntity var9 = (WorldEntity)classIS.field3051.field1698.method13637(var1.field1697 * 2140889407);
               classUZ var10 = var14.method10666(var3, var4, (byte)-56);
               var7 = var9.method10667((int)var10.field6425, (int)var10.field6424, (byte)18);
               var10.method11797(1880953733);
            }
         } else {
            var7 = classIK.method6261(var3, 0.0F, var4, (byte)9);
         }

         int var15;
         label51: {
            var15 = -1;
            int var16 = (int)(var6.field6425 - var7.field6425);
            int var17 = (int)(var6.field6424 - var7.field6424);
            if (0 == var16) {
               if (var5 != 1) {
                  throw new IllegalStateException();
               }

               if (var17 == 0) {
                  break label51;
               }

               if (var5 != 1) {
                  throw new IllegalStateException();
               }
            }

            int var11 = (int)(Math.atan2(var16, var17) * 325.94932345220167) & 2047;
            var15 = var11;
         }

         var6.method11797(1938438973);
         var7.method11797(2110727963);
         return var15;
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "bt.bf(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fh")
   static void method1245(int var0) {
      try {
         int var1 = client.field975.field6795 * -1927903443 - 1;
         if (-1 != var1) {
            if (var0 <= -144383729) {
               throw new IllegalStateException();
            }

            classON.field4991 = new classBY();
            client.method1684(-1);
            classON.field4991.field613 = client.field975.field6796[var1] * 100846287;
            classON.field4991.field609 = -385576485 * client.field975.field6797[var1];
            classON.field4991.field608 = -282371447 * client.field975.field6798[var1];
            classON.field4991.field606 = 1973962571 * client.field975.field6799[var1];
            classON.field4991.field610 = client.field975.field6800[var1] * 2074062867;
            classON.field4991.field616 = client.field975.field6802[var1];
            classON.field4991.field615 = client.field975.field6803[var1];
            classON.field4991.field612 = client.field975.field6801[var1] * 829407595;
            classON.field4991.field618 = client.field975.field6805[var1];
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "bt.fh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("az")
   int method1237(int var1, int var2) {
      try {
         int var3 = 0;
         int var4 = 0;

         while (var1 >= 8 - -573840275 * this.field574) {
            if (var2 >= -1911617180) {
               throw new IllegalStateException();
            }

            int var5 = 8 - this.field573 * -573840275;
            int var6 = (1 << var5) - 1;
            var3 += (this.field572[this.field573 * -1863749917] >> this.field574 * -573840275 & var6) << var4;
            this.field573 = 0;
            this.field573 += -57588021;
            var4 += var5;
            var1 -= var5;
         }

         if (var1 > 0) {
            if (var2 >= -1911617180) {
               throw new IllegalStateException();
            }

            int var8 = (1 << var1) - 1;
            var3 += (this.field572[-1863749917 * this.field573] >> -573840275 * this.field573 & var8) << var4;
            this.field573 += 75744613 * var1;
         }

         return var3;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "bt.az(" + 41);
      }
   }
}
