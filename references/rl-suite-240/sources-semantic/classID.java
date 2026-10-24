import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("id")
public final class classID {
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("ae")
   IterableHashTable field2943;
   @ObfuscatedName("az")
   int field2940;
   @ObfuscatedSignature(descriptor = "Lii;")
   @ObfuscatedName("ag")
   classII field2944;
   @ObfuscatedSignature(descriptor = "Lnz;")
   @ObfuscatedName("ab")
   classNZ field2942 = new classNZ();
   @ObfuscatedName("af")
   int field2941;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public void method6206() {
      this.field2942.method8296();
      this.field2943.method13623();
      this.field2941 = this.field2940;
   }

   public classID(int var1, int var2) {
      this.field2940 = var1;
      this.field2941 = var1;
      byte var3 = 1;

      while (var3 + var3 < var1 && var3 < var2) {
         var3 += var3;
      }

      this.field2943 = new IterableHashTable(var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lid;J)Ljava/lang/Object;")
   @ObfuscatedName("he")
   public static Object method6180(classID var0, long var1) {
      classJQ var3 = (classJQ)var0.field2943.method13637(var1);
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.vmethod513();
         if (var4 == null) {
            var3.vmethod398();
            var3.method12035();
            var0.field2941 = var0.field2941 + var3.field3356;
            return null;
         } else {
            if (var3.vmethod516()) {
               classJD var5 = new classJD(var4, var3.field3356);
               IterableHashTable.method13620(var0.field2943, var5, var3.field6550);
               var0.field2942.method8302(var5);
               var5.field6512 = 0L;
               var3.vmethod398();
               var3.method12035();
            } else {
               var0.field2942.method8302(var3);
               var3.field6512 = 0L;
            }

            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method6181(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13637(var1);
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.vmethod513();
         if (var4 == null) {
            var3.vmethod398();
            var3.method12035();
            this.field2941 = this.field2941 + var3.field3356;
            return null;
         } else {
            if (var3.vmethod516()) {
               classJD var5 = new classJD(var4, var3.field3356);
               IterableHashTable.method13620(this.field2943, var5, var3.field6550);
               this.field2942.method8302(var5);
               var5.field6512 = 0L;
               var3.vmethod398();
               var3.method12035();
            } else {
               this.field2942.method8302(var3);
               var3.field6512 = 0L;
            }

            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("af")
   void method6188(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13637(var1);
      this.method6191(var3);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bn")
   public void method6207() {
      this.field2942.method8296();
      this.field2943.method13623();
      this.field2941 = this.field2940;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;J)V")
   @ObfuscatedName("ab")
   public void method6194(Object var1, long var2) {
      this.method6197(var1, var2, 1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgd;Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("uc")
   public static int method6187(classGD var0, Object var1, Object var2) {
      if (var0 == null) {
         var0.equals(var1);
      }

      return var0.method5424((classGW)var1, (classGW)var2, -858395083);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;JI)V")
   @ObfuscatedName("ag")
   public void method6197(Object var1, long var2, int var4) {
      if (var4 > this.field2940) {
         throw new IllegalStateException();
      } else {
         this.method6188(var2);
         this.field2941 -= var4;

         while (this.field2941 < 0) {
            classJQ var5 = (classJQ)this.field2942.method8303();
            if (var5 == null) {
               throw new RuntimeException("");
            }

            if (!var5.vmethod516()) {
            }

            this.method6191(var5);
            if (this.field2944 != null) {
               this.field2944.method6257(var5.vmethod513());
            }
         }

         classJD var6 = new classJD(var1, var4);
         IterableHashTable.method13620(this.field2943, var6, var2);
         this.field2942.method8302(var6);
         var6.field6512 = 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("as")
   public void method6200(int var1) {
      for (classJQ var2 = (classJQ)this.field2942.method8304(); var2 != null; var2 = (classJQ)this.field2942.method8311()) {
         if (var2.vmethod516()) {
            if (var2.vmethod513() == null) {
               var2.vmethod398();
               var2.method12035();
               this.field2941 = this.field2941 + var2.field3356;
            }
         } else if (++var2.field6512 > var1) {
            classJU var3 = new classJU(var2.vmethod513(), var2.field3356);
            IterableHashTable.method13620(this.field2943, var3, var2.field6550);
            classNZ.method8294(var3, var2);
            var2.vmethod398();
            var2.method12035();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("am")
   public void method6208() {
      this.field2942.method8296();
      this.field2943.method13623();
      this.field2941 = this.field2940;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lid;J)Ljava/lang/Object;")
   @ObfuscatedName("ob")
   public static Object method6182(classID var0, long var1) {
      classJQ var3 = (classJQ)var0.field2943.method13637(var1);
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.vmethod513();
         if (var4 == null) {
            var3.vmethod398();
            var3.method12035();
            var0.field2941 = var0.field2941 + var3.field3356;
            return null;
         } else {
            if (var3.vmethod516()) {
               classJD var5 = new classJD(var4, var3.field3356);
               IterableHashTable.method13620(var0.field2943, var5, var3.field6550);
               var0.field2942.method8302(var5);
               var5.field6512 = 0L;
               var3.vmethod398();
               var3.method12035();
            } else {
               var0.field2942.method8302(var3);
               var3.field6512 = 0L;
            }

            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/Object;")
   @ObfuscatedName("aa")
   public Object method6183(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13637(var1);
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.vmethod513();
         if (var4 == null) {
            var3.vmethod398();
            var3.method12035();
            this.field2941 = this.field2941 + var3.field3356;
            return null;
         } else {
            if (var3.vmethod516()) {
               classJD var5 = new classJD(var4, var3.field3356);
               IterableHashTable.method13620(this.field2943, var5, var3.field6550);
               this.field2942.method8302(var5);
               var5.field6512 = 0L;
               var3.vmethod398();
               var3.method12035();
            } else {
               this.field2942.method8302(var3);
               var3.field6512 = 0L;
            }

            return var4;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lid;Ljava/lang/Object;JI)V")
   @ObfuscatedName("pc")
   public static void method6198(classID var0, Object var1, long var2, int var4) {
      if (var4 > var0.field2940) {
         throw new IllegalStateException();
      } else {
         var0.method6188(var2);
         var0.field2941 -= var4;

         while (var0.field2941 < 0) {
            classJQ var5 = (classJQ)var0.field2942.method8303();
            if (var5 == null) {
               throw new RuntimeException("");
            }

            if (!var5.vmethod516()) {
            }

            var0.method6191(var5);
            if (var0.field2944 != null) {
               var0.field2944.method6257(var5.vmethod513());
            }
         }

         classJD var6 = new classJD(var1, var4);
         IterableHashTable.method13620(var0.field2943, var6, var2);
         var0.field2942.method8302(var6);
         var6.field6512 = 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/Object;")
   @ObfuscatedName("ao")
   public Object method6184(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13637(var1);
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.vmethod513();
         if (var4 == null) {
            var3.vmethod398();
            var3.method12035();
            this.field2941 = this.field2941 + var3.field3356;
            return null;
         } else {
            if (var3.vmethod516()) {
               classJD var5 = new classJD(var4, var3.field3356);
               IterableHashTable.method13620(this.field2943, var5, var3.field6550);
               this.field2942.method8302(var5);
               var5.field6512 = 0L;
               var3.vmethod398();
               var3.method12035();
            } else {
               this.field2942.method8302(var3);
               var3.field6512 = 0L;
            }

            return var4;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("an")
   public void method6201(int var1) {
      for (classJQ var2 = (classJQ)this.field2942.method8304(); var2 != null; var2 = (classJQ)this.field2942.method8311()) {
         if (var2.vmethod516()) {
            if (var2.vmethod513() == null) {
               var2.vmethod398();
               var2.method12035();
               this.field2941 = this.field2941 + var2.field3356;
            }
         } else if (++var2.field6512 > var1) {
            classJU var3 = new classJU(var2.vmethod513(), var2.field3356);
            IterableHashTable.method13620(this.field2943, var3, var2.field6550);
            classNZ.method8294(var3, var2);
            var2.vmethod398();
            var2.method12035();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ay")
   void method6189(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13637(var1);
      this.method6191(var3);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   public void method6202(int var1) {
      for (classJQ var2 = (classJQ)this.field2942.method8304(); var2 != null; var2 = (classJQ)this.field2942.method8311()) {
         if (var2.vmethod516()) {
            if (var2.vmethod513() == null) {
               var2.vmethod398();
               var2.method12035();
               this.field2941 = this.field2941 + var2.field3356;
            }
         } else if (++var2.field6512 > var1) {
            classJU var3 = new classJU(var2.vmethod513(), var2.field3356);
            IterableHashTable.method13620(this.field2943, var3, var2.field6550);
            classNZ.method8294(var3, var2);
            var2.vmethod398();
            var2.method12035();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljq;)V")
   @ObfuscatedName("ae")
   void method6191(classJQ var1) {
      if (var1 != null) {
         var1.vmethod398();
         var1.method12035();
         this.field2941 = this.field2941 + var1.field3356;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljq;)V")
   @ObfuscatedName("ap")
   void method6192(classJQ var1) {
      if (var1 != null) {
         var1.vmethod398();
         var1.method12035();
         this.field2941 = this.field2941 + var1.field3356;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;J)V")
   @ObfuscatedName("au")
   public void method6195(Object var1, long var2) {
      this.method6197(var1, var2, 1);
   }

   @ObfuscatedSignature(descriptor = "(Lid;J)Ljava/lang/Object;")
   @ObfuscatedName("ux")
   public static Object method6185(classID var0, long var1) {
      if (var0 == null) {
         return var0.method6186(var1);
      } else {
         classJQ var3 = (classJQ)var0.field2943.method13637(var1);
         if (var3 == null) {
            return null;
         } else {
            Object var4 = var3.vmethod513();
            if (var4 == null) {
               var3.vmethod398();
               var3.method12035();
               var0.field2941 = var0.field2941 + var3.field3356;
               return null;
            } else {
               if (var3.vmethod516()) {
                  classJD var5 = new classJD(var4, var3.field3356);
                  IterableHashTable.method13620(var0.field2943, var5, var3.field6550);
                  var0.field2942.method8302(var5);
                  var5.field6512 = 0L;
                  var3.vmethod398();
                  var3.method12035();
               } else {
                  var0.field2942.method8302(var3);
                  var3.field6512 = 0L;
               }

               return var4;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;J)V")
   @ObfuscatedName("ai")
   public void method6196(Object var1, long var2) {
      this.method6197(var1, var2, 1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lid;I)V")
   @ObfuscatedName("hg")
   public static void method6203(classID var0, int var1) {
      if (var0 == null) {
         var0.method6205(var1);
      } else {
         for (classJQ var2 = (classJQ)var0.field2942.method8304(); var2 != null; var2 = (classJQ)var0.field2942.method8311()) {
            if (var2.vmethod516()) {
               if (var2.vmethod513() == null) {
                  var2.vmethod398();
                  var2.method12035();
                  var0.field2941 = var0.field2941 + var2.field3356;
               }
            } else if (++var2.field6512 > var1) {
               classJU var3 = new classJU(var2.vmethod513(), var2.field3356);
               IterableHashTable.method13620(var0.field2943, var3, var2.field6550);
               classNZ.method8294(var3, var2);
               var2.vmethod398();
               var2.method12035();
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;JI)V")
   @ObfuscatedName("ar")
   public void method6199(Object var1, long var2, int var4) {
      if (var4 > this.field2940) {
         throw new IllegalStateException();
      } else {
         this.method6188(var2);
         this.field2941 -= var4;

         while (this.field2941 < 0) {
            classJQ var5 = (classJQ)this.field2942.method8303();
            if (var5 == null) {
               throw new RuntimeException("");
            }

            if (!var5.vmethod516()) {
            }

            this.method6191(var5);
            if (this.field2944 != null) {
               this.field2944.method6257(var5.vmethod513());
            }
         }

         classJD var6 = new classJD(var1, var4);
         IterableHashTable.method13620(this.field2943, var6, var2);
         this.field2942.method8302(var6);
         var6.field6512 = 0L;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ah")
   public void method6209() {
      this.field2942.method8296();
      this.field2943.method13623();
      this.field2941 = this.field2940;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ak")
   public void method6204(int var1) {
      for (classJQ var2 = (classJQ)this.field2942.method8304(); var2 != null; var2 = (classJQ)this.field2942.method8311()) {
         if (var2.vmethod516()) {
            if (var2.vmethod513() == null) {
               var2.vmethod398();
               var2.method12035();
               this.field2941 = this.field2941 + var2.field3356;
            }
         } else if (++var2.field6512 > var1) {
            classJU var3 = new classJU(var2.vmethod513(), var2.field3356);
            IterableHashTable.method13620(this.field2943, var3, var2.field6550);
            classNZ.method8294(var3, var2);
            var2.vmethod398();
            var2.method12035();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljq;)V")
   @ObfuscatedName("ad")
   void method6193(classJQ var1) {
      if (var1 != null) {
         var1.vmethod398();
         var1.method12035();
         this.field2941 = this.field2941 + var1.field3356;
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("aq")
   void method6190(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13637(var1);
      this.method6191(var3);
   }

   public classID(int var1) {
      this(var1, var1);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("av")
   public void method6205(int var1) {
      for (classJQ var2 = (classJQ)this.field2942.method8313(); var2 != null; var2 = (classJQ)this.field2942.method8304()) {
         if (var2.vmethod518()) {
            if (var2.vmethod513() == null) {
               var2.method12135();
               var2.method12036();
               this.field2940 = this.field2941 + var2.field3356;
            }
         } else if (++var2.field6512 > var1) {
            classJU var3 = new classJU(var2.vmethod513(), var2.field3356);
            IterableHashTable.method13620(this.field2943, var3, var2.field6550);
            classNZ.method8295(var3, var2);
            var2.vmethod399();
            var2.method12036();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method6186(long var1) {
      classJQ var3 = (classJQ)this.field2943.method13616(var1);
      if (var3 == null) {
         return null;
      } else {
         Object var4 = var3.vmethod515();
         if (var4 == null) {
            var3.vmethod400();
            var3.method12035();
            this.field2941 = this.field2940 + var3.field3356;
            return null;
         } else {
            if (var3.vmethod516()) {
               classJD var5 = new classJD(var4, var3.field3356);
               IterableHashTable.method13620(this.field2943, var5, var3.field6550);
               this.field2942.method8302(var5);
               var5.field6512 = 0L;
               var3.vmethod399();
               var3.method12035();
            } else {
               this.field2942.method8299(var3);
               var3.field6512 = 0L;
            }

            return var4;
         }
      }
   }
}
