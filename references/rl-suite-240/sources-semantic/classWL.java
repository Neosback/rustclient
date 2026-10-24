import java.util.ArrayList;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wl")
public class classWL extends classWT {
   @ObfuscatedName("az")
   ArrayList field6690;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      if (this.field6690.isEmpty()) {
         return true;
      } else {
         for (MidiRequest var2 : this.field6690) {
            try {
               if (classNP.field4696.contains(var2)) {
                  if (null == var2) {
                     classNP.field4696.remove(var2);
                  } else {
                     if (var2.field4639.field4749 * 917328323 > 0) {
                        var2.field4639.field4749 -= 16852715;
                     }

                     if (0 == 917328323 * var2.field4639.field4749) {
                        var2.field4639.method8079((byte)36);
                        var2.field4639.method8066((byte)0);
                        var2.field4639.method8048(0, -755079536);
                     }

                     classAC.method311(var2.field4630 * 1942111947, var2.field4628 * 450234221, 1776810699);
                     classNP.field4696.remove(var2);
                  }
               }
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)32);
               classWT.method12694(this, var4.getMessage(), (byte)15);
               classNP.field4696.clear();
               return true;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         if (this.field6690.isEmpty()) {
            return true;
         } else {
            Iterator var2 = this.field6690.iterator();

            while (var2.hasNext()) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               MidiRequest var3 = (MidiRequest)var2.next();

               try {
                  if (classNP.field4696.contains(var3)) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     if (null == var3) {
                        classNP.field4696.remove(var3);
                     } else {
                        if (var3.field4639.field4749 * 917328323 > 0) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           var3.field4639.field4749 -= 16852715;
                        }

                        if (0 == 917328323 * var3.field4639.field4749) {
                           var3.field4639.method8079((byte)-20);
                           var3.field4639.method8066((byte)0);
                           var3.field4639.method8048(0, -1059042597);
                        }

                        classAC.method311(var3.field4630 * 1942111947, var3.field4628 * 450234221, 469369778);
                        classNP.field4696.remove(var3);
                     }
                  }
               } catch (Exception var5) {
                  classAAU.method270(null, var5, (byte)122);
                  classWT.method12694(this, var5.getMessage(), (byte)48);
                  classNP.field4696.clear();
                  return true;
               }
            }

            return true;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wl.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;III)V")
   @ObfuscatedName("op")
   public static void method12447(WorldMapRegion var0, int var1, int var2, int var3) {
      int var5 = 911628504 * var0.field2660;
      int var6 = var0.field2654 * 1312423097;
      int var7 = var0.field2665 * 680806733;
      SpritePixels var4 = (SpritePixels)classID.method6185(WorldMapRegion.field2661, classCX.method2699(var5, var6, var7, (byte)1));
      if (null != var4) {
         if (621957952 * var0.field2665 == var3) {
            var4.method13552(var1, var2);
         } else {
            var4.method13604(var1, var2, var3, var3);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      if (this.field6690.isEmpty()) {
         return true;
      } else {
         for (MidiRequest var2 : this.field6690) {
            try {
               if (classNP.field4696.contains(var2)) {
                  if (null == var2) {
                     classNP.field4696.remove(var2);
                  } else {
                     if (var2.field4639.field4749 * 917328323 > 0) {
                        var2.field4639.field4749 -= 16852715;
                     }

                     if (0 == 917328323 * var2.field4639.field4749) {
                        var2.field4639.method8079((byte)-64);
                        var2.field4639.method8066((byte)0);
                        var2.field4639.method8048(0, -730811034);
                     }

                     classAC.method311(var2.field4630 * 1942111947, var2.field4628 * 450234221, 824226375);
                     classNP.field4696.remove(var2);
                  }
               }
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)69);
               classWT.method12694(this, var4.getMessage(), (byte)-10);
               classNP.field4696.clear();
               return true;
            }
         }

         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      if (this.field6690.isEmpty()) {
         return true;
      } else {
         for (MidiRequest var2 : this.field6690) {
            try {
               if (classNP.field4696.contains(var2)) {
                  if (null == var2) {
                     classNP.field4696.remove(var2);
                  } else {
                     if (var2.field4639.field4749 * 917328323 > 0) {
                        var2.field4639.field4749 -= 16852715;
                     }

                     if (0 == 917328323 * var2.field4639.field4749) {
                        var2.field4639.method8079((byte)-27);
                        var2.field4639.method8066((byte)0);
                        var2.field4639.method8048(0, -1611185541);
                     }

                     classAC.method311(var2.field4630 * 1942111947, var2.field4628 * 450234221, 1863757659);
                     classNP.field4696.remove(var2);
                  }
               }
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)64);
               classWT.method12694(this, var4.getMessage(), (byte)74);
               classNP.field4696.clear();
               return true;
            }
         }

         return true;
      }
   }

   public classWL(classWT var1, ArrayList var2) {
      super(var1);
      this.field6775 = "ClearRequestTask";
      this.field6690 = var2;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      if (this.field6690.isEmpty()) {
         return true;
      } else {
         for (MidiRequest var2 : this.field6690) {
            try {
               if (classNP.field4696.contains(var2)) {
                  if (null == var2) {
                     classNP.field4696.remove(var2);
                  } else {
                     if (var2.field4639.field4749 * 917328323 > 0) {
                        var2.field4639.field4749 -= 16852715;
                     }

                     if (0 == 917328323 * var2.field4639.field4749) {
                        var2.field4639.method8079((byte)8);
                        var2.field4639.method8066((byte)0);
                        var2.field4639.method8048(0, -2083927480);
                     }

                     classAC.method311(var2.field4630 * 1942111947, var2.field4628 * 450234221, 1445320128);
                     classNP.field4696.remove(var2);
                  }
               }
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)59);
               classWT.method12694(this, var4.getMessage(), (byte)-17);
               classNP.field4696.clear();
               return true;
            }
         }

         return true;
      }
   }
}
