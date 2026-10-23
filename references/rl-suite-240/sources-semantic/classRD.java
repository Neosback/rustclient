import java.util.Comparator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rd")
public abstract class classRD implements Comparator {
   @ObfuscatedName("af")
   Comparator field5642;
   @ObfuscatedSignature(descriptor = "Lym;")
   @ObfuscatedName("ax")
   static SpritePixels field5643;

   @ObfuscatedSignature(descriptor = "(IZIZI)V")
   @ObfuscatedName("ab")
   static void method9987(int var0, boolean var1, int var2, boolean var3, int var4) {
      try {
         if (World.field1168 != null) {
            if (var4 == 379541057) {
               return;
            }

            classLV.method7203(0, World.field1168.length - 1, var0, var1, var2, var3, (byte)1);
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "rd.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrd;Ljava/util/Comparator;I)V")
   @ObfuscatedName("bl")
   public static void method9977(classRD var0, Comparator var1, int var2) {
      if (var0 == null) {
         var0.method9979(var1, var2);
      } else {
         try {
            if (var0.field5642 == null) {
               var0.field5642 = var1;
            } else if (var0.field5642 instanceof classRD) {
               if (var2 != 1072642345) {
                  throw new IllegalStateException();
               }

               method9977((classRD)var0.field5642, var1, 1072642345);
            }
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "rd.ac(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;)V")
   @ObfuscatedName("ao")
   final void method9978(Comparator var1) {
      if (this.field5642 == null) {
         this.field5642 = var1;
      } else if (this.field5642 instanceof classRD) {
         method9977((classRD)this.field5642, var1, 1072642345);
      }
   }

   protected classRD() {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method9984(Object var1) {
      return super.equals(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrd;Lrr;Lrr;)I")
   @ObfuscatedName("cj")
   public static int method9981(classRD var0, Nameable var1, Nameable var2) {
      return null == var0.field5642 ? 0 : var0.field5642.compare(var1, var2);
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return super.equals(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rd.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Lrr;)I")
   @ObfuscatedName("aj")
   protected final int method9982(Nameable var1, Nameable var2) {
      return null == this.field5642 ? 0 : this.field5642.compare(var1, var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lrd;Ljava/lang/Object;)Z")
   @ObfuscatedName("ep")
   public static boolean method9985(classRD var0, Object var1) {
      return var0 == null ? var0.method9986(var1) : var0.equals(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lqr;ILqv;I)I")
   @ObfuscatedName("az")
   public static int method9980(classQR var0, int var1, classQV var2, int var3) {
      try {
         int var4 = 0;
         if (!var0.method9772(374233424)) {
            var0.method9801(0, 198333171);
            return var4;
         } else {
            Animation var5 = var0.method9775((byte)51);
            if (-1 == var5.field5132 * 1665914959) {
               if (var3 >= -113951151) {
                  throw new IllegalStateException();
               }

               var4 |= 8;
            }

            if (var5.method8820((byte)-87)) {
               if (var3 >= -113951151) {
                  throw new IllegalStateException();
               }

               var0.method9801(0, 1128422573);
            } else {
               var0.method9801(var0.method9799((byte)-76) + var1, 8925726);
            }

            if (!Animation.method8805(var5, (byte)0)) {
               if (var3 >= -113951151) {
                  throw new IllegalStateException();
               }

               int var8 = var1;
               byte var9 = 0;
               Animation var10 = var0.method9775((byte)96);
               if (1665914959 * var10.field5132 > 0) {
                  if (var3 >= -113951151) {
                     throw new IllegalStateException();
                  }

                  if (var10.field5131 * -826664243 > 0) {
                     var8 = var1 % (var10.field5131 * -826664243);
                  }
               }

               int var11 = var0.method9780(1719962113);
               int var12 = var0.method9781(929233716);
               int var13 = var0.method9784(22636646);
               if (var11 >= var10.field5125.length) {
                  if (var3 >= -113951151) {
                     throw new IllegalStateException();
                  }

                  var11 = 0;
                  var12 = 0;
               }

               var12 += var8;

               while (var12 > var10.field5123[var11]) {
                  var12 -= var10.field5123[var11];
                  var11++;
                  var9 |= 4;
                  if (0 == (var9 & 2)) {
                     if (var3 >= -113951151) {
                        throw new IllegalStateException();
                     }

                     if (null != var2) {
                        if (var3 >= -113951151) {
                           throw new IllegalStateException();
                        }

                        var2.vmethod13(var10, var11, 895142418);
                     }
                  }

                  if (var11 >= var10.field5125.length) {
                     if (var3 >= -113951151) {
                        throw new IllegalStateException();
                     }

                     var13++;
                     var9 |= 1;
                     var11 -= var10.field5132 * 1665914959;
                     if (var13 >= 2035920365 * var10.field5122) {
                        if (var3 >= -113951151) {
                           throw new IllegalStateException();
                        }

                        var9 |= 2;
                     }

                     label119: {
                        if (var11 >= 0) {
                           if (var3 >= -113951151) {
                              throw new IllegalStateException();
                           }

                           if (var11 < var10.field5125.length) {
                              break label119;
                           }

                           if (var3 >= -113951151) {
                              throw new IllegalStateException();
                           }
                        }

                        var9 |= 2;
                        var11 = 0;
                     }

                     if (0 == (var9 & 2)) {
                        if (var3 >= -113951151) {
                           throw new IllegalStateException();
                        }

                        if (null != var2) {
                           if (var3 >= -113951151) {
                              throw new IllegalStateException();
                           }

                           var2.vmethod13(var10, var11, 1479355802);
                        }
                     }
                  }
               }

               var0.method9811(var11, var12, var13, 1587600500);
               var4 |= var9;
            } else {
               var4 |= classEJ.method3922(var0, var1, var2, 1289411290);
            }

            return var4;
         }
      } catch (RuntimeException var14) {
         throw classEG.method3899(var14, "rd.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrr;Lrr;I)I")
   @ObfuscatedName("aa")
   protected final int method9983(Nameable var1, Nameable var2, int var3) {
      try {
         if (null == this.field5642) {
            if (var3 <= 1195995055) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else {
            return this.field5642.compare(var1, var2);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rd.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/util/Comparator;I)V")
   @ObfuscatedName("ac")
   final void method9979(Comparator var1, int var2) {
      try {
         if (this.field5642 == null) {
            this.field5642 = var1;
         } else if (this.field5642 instanceof classRD) {
            if (var2 != 1072642345) {
               throw new IllegalStateException();
            }

            method9977((classRD)this.field5642, var1, 1072642345);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rd.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method9986(Object var1) {
      return super.equals(var1);
   }
}
