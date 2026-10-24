import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gj")
public class classGJ implements classHM {
   @ObfuscatedName("ab")
   int field2594;
   @ObfuscatedName("af")
   int field2597;
   @ObfuscatedName("aa")
   int field2593;
   @ObfuscatedName("ac")
   int field2595;
   @ObfuscatedName("ag")
   int field2598;
   @ObfuscatedName("az")
   int field2604;
   @ObfuscatedName("ax")
   int field2603;
   @ObfuscatedName("ae")
   int field2596;
   @ObfuscatedName("ao")
   int field2600;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field2601 = 14;
   @ObfuscatedName("as")
   int field2602;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field2599 = 1;

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method5494(IndexDataBase var0, int var1) {
      try {
         classPN.field5360 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gj.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhg;I)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod469(classHG var1, int var2) {
      try {
         if (-1293823145 * var1.field2787 > this.field2603 * 1715061181) {
            if (var2 <= -419997227) {
               return;
            }

            var1.field2787 = -1225745653 * this.field2603;
         }

         if (1597382741 * var1.field2790 < this.field2593 * -879293243) {
            var1.field2790 = 271182513 * this.field2593;
         }

         if (var1.field2788 * 1546846349 > this.field2595 * 1749585137) {
            if (var2 <= -419997227) {
               throw new IllegalStateException();
            }

            var1.field2788 = this.field2595 * -2111136011;
         }

         if (252768043 * var1.field2789 < this.field2600 * -575393479) {
            if (var2 <= -419997227) {
               return;
            }

            var1.field2789 = -2025222357 * this.field2600;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gj.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIII)Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod472(int var1, int var2, int var3, int var4) {
      try {
         if (var1 >= this.field2604 * -977032489) {
            if (var4 >= -651641119) {
               throw new IllegalStateException();
            }

            if (var1 < -977032489 * this.field2604 + this.field2597 * -1520162709) {
               if (var2 >> 6 >= this.field2596 * 2033424185) {
                  if (var4 >= -651641119) {
                     throw new IllegalStateException();
                  }

                  if (var2 >> 6 <= this.field2598 * 6816621) {
                     if (var4 >= -651641119) {
                        throw new IllegalStateException();
                     }

                     if (var3 >> 6 >= this.field2594 * -2099985487) {
                        if (var4 >= -651641119) {
                           throw new IllegalStateException();
                        }

                        if (var3 >> 6 <= -969820241 * this.field2602) {
                           if (var4 >= -651641119) {
                              throw new IllegalStateException();
                           }

                           return true;
                        }
                     }
                  }
               }

               return false;
            }

            if (var4 >= -651641119) {
               throw new IllegalStateException();
            }
         }

         return false;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gj.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ay")
   @Override
   public boolean vmethod477(int var1, int var2) {
      return var1 >> 6 >= 1715061181 * this.field2603
         && var1 >> 6 <= -879293243 * this.field2593
         && var2 >> 6 >= 1749585137 * this.field2595
         && var2 >> 6 <= -575393479 * this.field2600;
   }

   @ObfuscatedSignature(descriptor = "(IIIB)[I")
   @ObfuscatedName("ab")
   @Override
   public int[] vmethod478(int var1, int var2, int var3, byte var4) {
      try {
         if (!this.vmethod472(var1, var2, var3, -1584093993)) {
            if (var4 <= 2) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            return new int[]{
               var2 + (-1905234112 * this.field2603 - this.field2596 * 1290128960), var3 + (this.field2595 * 304299072 - -1255084992 * this.field2594)
            };
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gj.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lky;")
   @ObfuscatedName("ak")
   @Override
   public classKY vmethod481(int var1, int var2) {
      if (!this.vmethod476(var1, var2, (byte)60)) {
         return null;
      } else {
         int var3 = this.field2596 * -1993836909 - 1612151602 * this.field2603 + var1;
         int var4 = this.field2594 * 361854417 - this.field2595 * 304299072 + var2;
         return new classKY(this.field2604 * -1144576483, var3, var4);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("as")
   @Override
   public void vmethod484(classXY var1, int var2) {
      try {
         this.field2604 = var1.method13095(-346779531) * 764602599;
         this.field2597 = var1.method13095(-346779531) * -882424765;
         this.field2596 = var1.method13099(513730862) * -1269422327;
         this.field2594 = var1.method13099(-826755470) * 94217553;
         this.field2598 = var1.method13099(1882809038) * 654540389;
         this.field2602 = var1.method13099(856188930) * 899678031;
         this.field2603 = var1.method13099(1974015843) * -68517483;
         this.field2595 = var1.method13099(1407337964) * 1881728017;
         this.field2593 = var1.method13099(1090107343) * -1935896051;
         this.field2600 = var1.method13099(1463824616) * 1784507657;
         this.method5500((byte)68);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gj.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Lky;")
   @ObfuscatedName("ag")
   @Override
   public classKY vmethod480(int var1, int var2, int var3) {
      try {
         if (!this.vmethod476(var1, var2, (byte)60)) {
            if (var3 != 2141092142) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            int var4 = this.field2596 * 1290128960 - -1905234112 * this.field2603 + var1;
            int var5 = this.field2594 * -1255084992 - this.field2595 * 304299072 + var2;
            return new classKY(this.field2604 * -977032489, var4, var5);
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gj.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhg;)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod470(classHG var1) {
      if (-1293823145 * var1.field2787 > this.field2603 * -377161875) {
         var1.field2787 = -1225745653 * this.field2603;
      }

      if (-1287440008 * var1.field2790 < this.field2593 * -879293243) {
         var1.field2790 = 555120282 * this.field2593;
      }

      if (var1.field2788 * 1546846349 > this.field2595 * -1863949235) {
         var1.field2788 = this.field2595 * -2111136011;
      }

      if (-1849420742 * var1.field2789 < this.field2600 * -575393479) {
         var1.field2789 = -2025222357 * this.field2600;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lhg;)V")
   @ObfuscatedName("ac")
   @Override
   public void vmethod471(classHG var1) {
      if (1630948501 * var1.field2787 > this.field2603 * 1715061181) {
         var1.field2787 = -1225745653 * this.field2603;
      }

      if (-553376867 * var1.field2790 < this.field2593 * -879293243) {
         var1.field2790 = 271182513 * this.field2593;
      }

      if (var1.field2788 * -852014902 > this.field2595 * 1749585137) {
         var1.field2788 = this.field2595 * -2111136011;
      }

      if (252768043 * var1.field2789 < this.field2600 * 281154320) {
         var1.field2789 = -2025222357 * this.field2600;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("ao")
   @Override
   public boolean vmethod474(int var1, int var2, int var3) {
      return var1 >= this.field2604 * -977032489 && var1 < -977032489 * this.field2604 + this.field2597 * -1520162709
         ? var2 >> 6 >= this.field2596 * -128663862
            && var2 >> 6 <= this.field2598 * 6816621
            && var3 >> 6 >= this.field2594 * -984447491
            && var3 >> 6 <= -969820241 * this.field2602
         : false;
   }

   @ObfuscatedSignature(descriptor = "(III)Z")
   @ObfuscatedName("al")
   @Override
   public boolean vmethod473(int var1, int var2, int var3) {
      return var1 >= this.field2604 * -222267470 && var1 < 1686407213 * this.field2604 + this.field2597 * -1520162709
         ? var2 >> 6 >= this.field2596 * -300896342
            && var2 >> 6 <= this.field2598 * 6816621
            && var3 >> 6 >= this.field2594 * 1193723299
            && var3 >> 6 <= -969820241 * this.field2602
         : false;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ad")
   @Override
   public void vmethod485(classXY var1) {
      this.field2604 = var1.method13095(-346779531) * 764602599;
      this.field2597 = var1.method13095(-346779531) * -882424765;
      this.field2596 = var1.method13099(1086922347) * -1269422327;
      this.field2594 = var1.method13099(-258846604) * -893933113;
      this.field2598 = var1.method13099(-1897665918) * 654540389;
      this.field2602 = var1.method13099(-55539999) * 899678031;
      this.field2603 = var1.method13099(1292108813) * -743576161;
      this.field2595 = var1.method13099(96242450) * -2081182426;
      this.field2593 = var1.method13099(350670022) * 1876259037;
      this.field2600 = var1.method13099(1408015074) * -1094717737;
      this.method5500((byte)125);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsi;)Z")
   @ObfuscatedName("pa")
   public static boolean method5495(ActorSpotAnim var0) {
      return classOR.method8762(classAI.method367(-1568506297 * var0.field5957, -1568080155), -1167547580);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   void method5497() {
   }

   @ObfuscatedSignature(descriptor = "(III)[I")
   @ObfuscatedName("aq")
   @Override
   public int[] vmethod479(int var1, int var2, int var3) {
      return !this.vmethod472(var1, var2, var3, -2003259957)
         ? null
         : new int[]{var2 + (-1905234112 * this.field2603 - this.field2596 * 1290128960), var3 + (this.field2595 * 304299072 - -1255084992 * this.field2594)};
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   void method5498() {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   void method5499() {
   }

   @ObfuscatedSignature(descriptor = "(IIB)Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod476(int var1, int var2, byte var3) {
      try {
         if (var1 >> 6 >= 1715061181 * this.field2603) {
            if (var3 != 60) {
               throw new IllegalStateException();
            }

            if (var1 >> 6 <= -879293243 * this.field2593) {
               if (var3 != 60) {
                  throw new IllegalStateException();
               }

               if (var2 >> 6 >= 1749585137 * this.field2595 && var2 >> 6 <= -575393479 * this.field2600) {
                  if (var3 != 60) {
                     throw new IllegalStateException();
                  }

                  return true;
               }
            }
         }

         return false;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gj.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("aj")
   @Override
   public boolean vmethod475(int var1, int var2) {
      return var1 >> 6 >= 1715061181 * this.field2603
         && var1 >> 6 <= -879293243 * this.field2593
         && var2 >> 6 >= 1749585137 * this.field2595
         && var2 >> 6 <= -575393479 * this.field2600;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbt;[BI)V")
   @ObfuscatedName("bu")
   public static void method5496(classBT var0, byte[] var1, int var2) {
      if (var0 == null) {
         var0.method1235(var2);
      }

      var0.field572 = var1;
      var0.field574 = -57588021 * var2;
      var0.field573 = 0;
   }

   @ObfuscatedSignature(descriptor = "(II)Lky;")
   @ObfuscatedName("ar")
   @Override
   public classKY vmethod482(int var1, int var2) {
      if (!this.vmethod476(var1, var2, (byte)60)) {
         return null;
      } else {
         int var3 = this.field2596 * 1290128960 - -1905234112 * this.field2603 + var1;
         int var4 = this.field2594 * -1255084992 - this.field2595 * 304299072 + var2;
         return new classKY(this.field2604 * -977032489, var3, var4);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lky;")
   @ObfuscatedName("aw")
   @Override
   public classKY vmethod483(int var1, int var2) {
      if (!this.vmethod476(var1, var2, (byte)60)) {
         return null;
      } else {
         int var3 = this.field2596 * 1290128960 - -1905234112 * this.field2603 + var1;
         int var4 = this.field2594 * -1255084992 - this.field2595 * 304299072 + var2;
         return new classKY(this.field2604 * -977032489, var3, var4);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ax")
   void method5500(byte var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gj.ax(" + ')');
      }
   }

   classGJ() {
   }
}
