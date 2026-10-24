import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wh")
public class classWH {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field6680 = 12;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6681 = 0;
   @ObfuscatedSignature(descriptor = "Lgx;")
   @ObfuscatedName("ag")
   WorldMapData field6683;
   @ObfuscatedName("ac")
   boolean field6676;
   @ObfuscatedName("ax")
   int field6677 = 0;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("as")
   IndexDataBase field6678;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   public static final int field6679 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6682 = 100;

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("as")
   void method12432(WorldMapData var1) {
      if (null != var1) {
         if (this.field6683 == null || var1.method5876(-608589044) != this.field6683.method5876(-608589044)) {
            this.field6683 = var1;
            this.field6677 = 0;
            this.field6676 = false;
            method12439(this, 1747005689);
         }
      }
   }

   classWH(IndexDataBase var1) {
      this.field6676 = false;
      this.field6678 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   int method12443(int var1) {
      try {
         return -1390089701 * this.field6677;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wh.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("al")
   boolean method12441() {
      return this.field6676;
   }

   @ObfuscatedSignature(descriptor = "(Lgx;B)V")
   @ObfuscatedName("az")
   void method12433(WorldMapData var1, byte var2) {
      try {
         if (null == var1) {
            if (var2 == 1) {
               throw new IllegalStateException();
            }
         } else if (this.field6683 == null || var1.method5876(-608589044) != this.field6683.method5876(-608589044)) {
            this.field6683 = var1;
            this.field6677 = 0;
            this.field6676 = false;
            method12439(this, 1566102928);
         } else if (var2 != 1) {
            ;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wh.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgx;)V")
   @ObfuscatedName("ag")
   void method12434(WorldMapData var1) {
      if (null != var1) {
         if (this.field6683 == null || var1.method5876(-608589044) != this.field6683.method5876(-608589044)) {
            this.field6683 = var1;
            this.field6677 = 0;
            this.field6676 = false;
            method12439(this, 1413072693);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwh;Lgx;)V")
   @ObfuscatedName("ue")
   public static void method12435(classWH var0, WorldMapData var1) {
      if (var0 == null) {
         var0.method12434(var1);
      }

      if (null != var1) {
         if (var0.field6683 == null || var1.method5876(-608589044) != var0.field6683.method5876(-608589044)) {
            var0.field6683 = var1;
            var0.field6677 = 0;
            var0.field6676 = false;
            method12439(var0, 1719483020);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   int method12436() {
      if (this.field6683 == null) {
         this.field6677 = 645629804;
         this.field6676 = true;
      } else {
         if (this.field6677 * -1390089701 < 33) {
            if (!this.field6678.method11944(classHE.field2776.field2771 * -1013526269, this.field6683.method5876(-608589044), (byte)-46)) {
               return this.field6677 * -1390089701;
            }

            this.field6677 = 556655219;
         }

         if (33 == this.field6677 * -1390089701) {
            if (!this.field6678.method11944(-1013526269 * classHE.field2774.field2771, this.field6683.method5876(-608589044), (byte)-3)) {
               return -1390089701 * this.field6677;
            }

            this.field6677 = 1113310438;
         }

         if (66 == -1390089701 * this.field6677) {
            this.field6677 = 645629804;
            this.field6676 = true;
         }
      }

      return this.field6677 * -1390089701;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   int method12437() {
      if (this.field6683 == null) {
         this.field6677 = 645629804;
         this.field6676 = true;
      } else {
         if (this.field6677 * -1390089701 < -1995057897) {
            if (!this.field6678.method11944(classHE.field2776.field2771 * 1655986644, this.field6683.method5876(-608589044), (byte)113)) {
               return this.field6677 * -1390089701;
            }

            this.field6677 = 556655219;
         }

         if (-350220522 == this.field6677 * 1594460078) {
            if (!this.field6678.method11944(200712732 * classHE.field2774.field2771, this.field6683.method5876(-608589044), (byte)-22)) {
               return -1390089701 * this.field6677;
            }

            this.field6677 = 2036622097;
         }

         if (-659571991 == -670497307 * this.field6677) {
            this.field6677 = -69675072;
            this.field6676 = true;
         }
      }

      return this.field6677 * -1390089701;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   int method12438() {
      if (this.field6683 == null) {
         this.field6677 = 645629804;
         this.field6676 = true;
      } else {
         if (this.field6677 * -1390089701 < 33) {
            if (!this.field6678.method11944(classHE.field2776.field2771 * -1013526269, this.field6683.method5876(-608589044), (byte)-16)) {
               return this.field6677 * -1390089701;
            }

            this.field6677 = 556655219;
         }

         if (33 == this.field6677 * -1390089701) {
            if (!this.field6678.method11944(-1013526269 * classHE.field2774.field2771, this.field6683.method5876(-608589044), (byte)8)) {
               return -1390089701 * this.field6677;
            }

            this.field6677 = 1113310438;
         }

         if (66 == -1390089701 * this.field6677) {
            this.field6677 = 645629804;
            this.field6676 = true;
         }
      }

      return this.field6677 * -1390089701;
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("ae")
   boolean method12442(short var1) {
      try {
         return this.field6676;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wh.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lkq;)I")
   @ObfuscatedName("mf")
   public static int method12440(classKQ var0) {
      return -955075037 * var0.field3995;
   }

   @ObfuscatedSignature(descriptor = "(Lwh;I)I")
   @ObfuscatedName("fr")
   public static int method12439(classWH var0, int var1) {
      if (var0 == null) {
         return var0.method12444(var1);
      } else {
         try {
            if (var0.field6683 == null) {
               if (var1 <= 1312733671) {
                  throw new IllegalStateException();
               }

               var0.field6677 = 645629804;
               var0.field6676 = true;
            } else {
               if (var0.field6677 * -1390089701 < 33) {
                  if (var1 <= 1312733671) {
                     throw new IllegalStateException();
                  }

                  if (!var0.field6678.method11944(classHE.field2776.field2771 * -1013526269, var0.field6683.method5876(-608589044), (byte)33)) {
                     if (var1 <= 1312733671) {
                        throw new IllegalStateException();
                     }

                     return var0.field6677 * -1390089701;
                  }

                  var0.field6677 = 556655219;
               }

               if (33 == var0.field6677 * -1390089701) {
                  if (!var0.field6678.method11944(-1013526269 * classHE.field2774.field2771, var0.field6683.method5876(-608589044), (byte)-3)) {
                     return -1390089701 * var0.field6677;
                  }

                  var0.field6677 = 1113310438;
               }

               if (66 == -1390089701 * var0.field6677) {
                  var0.field6677 = 645629804;
                  var0.field6676 = true;
               }
            }

            return var0.field6677 * -1390089701;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "wh.af(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   int method12444(int var1) {
      try {
         return -1390089701 * this.field6677;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wh.ab(" + 41);
      }
   }
}
