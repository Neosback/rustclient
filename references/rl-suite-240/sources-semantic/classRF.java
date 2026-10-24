import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rf")
public class classRF extends NameableContainer {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5660 = 1;
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ag")
   final classAAO field5658;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field5661 = 100;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field5659 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5662 = 400;

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ac")
   public void method9995(classXY var1, int var2) {
      while (var1.field6953 * 702114061 < var2) {
         int var3 = var1.method13095(-346779531);
         if (4 == var3) {
            classAAE var9 = new classAAE(var1.method13127(177483067), this.field5658);
            if (!var9.method166(506837222)) {
               throw new IllegalStateException();
            }

            boolean var10 = false;
            classQQ.field5595.method1331(classAAE.method157(var9, 1541102987), var10, 1434553979);
         } else {
            boolean var4 = (var3 & 1) != 0;
            classAAE var5 = new classAAE(var1.method13127(1353230006), this.field5658);
            classAAE var6 = new classAAE(var1.method13127(601360729), this.field5658);
            var1.method13127(-549384224);
            if (!var5.method166(1000658932)) {
               throw new IllegalStateException();
            }

            Ignore var7 = (Ignore)this.method10258(var5, -1528113242);
            if (var4) {
               Ignore var8 = (Ignore)this.method10258(var6, 908692591);
               if (var8 != null && var8 != var7) {
                  if (null != var7) {
                     NameableContainer.method10270(this, var8, -39841176);
                  } else {
                     var7 = var8;
                  }
               }
            }

            if (null != var7) {
               this.method10286(var7, var5, var6, 55745139);
            } else if (this.method10243(-1228256686) < 400) {
               int var12 = this.method10243(-880076749);
               var7 = (Ignore)this.method10276(var5, var6, (byte)-5);
               var7.field5834 = -3340741 * var12;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lrr;")
   @ObfuscatedName("az")
   @Override
   Nameable vmethod555(int var1) {
      try {
         return new Ignore();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rf.az(" + ')');
      }
   }

   public classRF(classAAO var1) {
      super(400);
      this.field5658 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("ax")
   public void method9996(classXY var1, int var2) {
      while (var1.field6953 * 702114061 < var2) {
         int var3 = var1.method13095(-346779531);
         if (4 == var3) {
            classAAE var9 = new classAAE(var1.method13127(-894833178), this.field5658);
            if (!var9.method166(572791587)) {
               throw new IllegalStateException();
            }

            boolean var10 = false;
            classQQ.field5595.method1331(classAAE.method157(var9, 1380916912), var10, 1466229663);
         } else {
            boolean var4 = (var3 & 1) != 0;
            classAAE var5 = new classAAE(var1.method13127(-55642484), this.field5658);
            classAAE var6 = new classAAE(var1.method13127(1667417888), this.field5658);
            var1.method13127(637208751);
            if (!var5.method166(244066639)) {
               throw new IllegalStateException();
            }

            Ignore var7 = (Ignore)this.method10258(var5, 781999007);
            if (var4) {
               Ignore var8 = (Ignore)this.method10258(var6, 1544105364);
               if (var8 != null && var8 != var7) {
                  if (null != var7) {
                     NameableContainer.method10270(this, var8, 139962439);
                  } else {
                     var7 = var8;
                  }
               }
            }

            if (null != var7) {
               this.method10286(var7, var5, var6, -1617610550);
            } else if (this.method10243(-1241797278) < 400) {
               int var12 = this.method10243(-668432367);
               var7 = (Ignore)this.method10276(var5, var6, (byte)-3);
               var7.field5834 = -3340741 * var12;
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ab")
   @Override
   Nameable vmethod556() {
      return new Ignore();
   }

   @ObfuscatedSignature(descriptor = "()Lrr;")
   @ObfuscatedName("ag")
   @Override
   Nameable vmethod557() {
      return new Ignore();
   }

   @ObfuscatedSignature(descriptor = "(IB)[Lrr;")
   @ObfuscatedName("af")
   @Override
   Nameable[] vmethod558(int var1, byte var2) {
      try {
         return new Ignore[var1];
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rf.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lrr;")
   @ObfuscatedName("as")
   @Override
   Nameable[] vmethod559(int var1) {
      return new Ignore[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpw;)Lym;")
   @ObfuscatedName("kx")
   public static SpritePixels method9999(classPW var0) {
      if (-269152068 * var0.field5455 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(classPW.field5444, -1968104432 * var0.field5455);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classWN.field6756, -548947605 * var0.field5455, 0, -1656010224);
            if (null != var1) {
               classPW.field5444.method6464(var1, var0.field5455 * 2008958772);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIZIIS)J")
   @ObfuscatedName("ac")
   public static long method9998(int var0, int var1, int var2, int var3, boolean var4, int var5, int var6, short var7) {
      try {
         long var8 = (var1 & 127) << 0 | (var2 & 127) << 7 | (var0 & 3) << 14 | (var3 & 7) << 16 | (var5 & 4294967295L) << 20 | (var6 & 4095L) << 52;
         if (var4) {
            if (var7 <= 3642) {
               throw new IllegalStateException();
            }

            var8 |= 524288L;
         }

         return var8;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "rf.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("ae")
   public void method9997(classXY var1, int var2, int var3) {
      try {
         while (var1.field6953 * 702114061 < var2) {
            if (var3 != 400186886) {
               throw new IllegalStateException();
            }

            int var4 = var1.method13095(-346779531);
            if (4 == var4) {
               if (var3 != 400186886) {
                  return;
               }

               classAAE var11 = new classAAE(var1.method13127(1363699488), this.field5658);
               if (!var11.method166(717563486)) {
                  if (var3 != 400186886) {
                     throw new IllegalStateException();
                  }

                  throw new IllegalStateException();
               }

               boolean var12 = false;
               classQQ.field5595.method1331(classAAE.method157(var11, 888944905), var12, 1519412064);
            } else {
               boolean var10000;
               if ((var4 & 1) != 0) {
                  if (var3 != 400186886) {
                     return;
                  }

                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var5 = var10000;
               classAAE var6 = new classAAE(var1.method13127(169997208), this.field5658);
               classAAE var7 = new classAAE(var1.method13127(1825906559), this.field5658);
               var1.method13127(2056146868);
               if (!var6.method166(1031274073)) {
                  if (var3 != 400186886) {
                     throw new IllegalStateException();
                  }

                  throw new IllegalStateException();
               }

               Ignore var8 = (Ignore)this.method10258(var6, -1470289739);
               if (var5) {
                  Ignore var9 = (Ignore)this.method10258(var7, 1893778780);
                  if (var9 != null) {
                     if (var3 != 400186886) {
                        throw new IllegalStateException();
                     }

                     if (var9 != var8) {
                        if (var3 != 400186886) {
                           throw new IllegalStateException();
                        }

                        if (null != var8) {
                           if (var3 != 400186886) {
                              return;
                           }

                           NameableContainer.method10270(this, var9, 672387961);
                        } else {
                           var8 = var9;
                        }
                     }
                  }
               }

               if (null != var8) {
                  if (var3 != 400186886) {
                     throw new IllegalStateException();
                  }

                  this.method10286(var8, var6, var7, -1105072263);
               } else if (this.method10243(-1724515338) < 400) {
                  int var14 = this.method10243(-1670008609);
                  var8 = (Ignore)this.method10276(var6, var7, (byte)-25);
                  var8.field5834 = -3340741 * var14;
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "rf.ae(" + ')');
      }
   }
}
