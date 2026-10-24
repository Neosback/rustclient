import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("wk")
public class classWK extends classWT {
   @ObfuscatedSignature(descriptor = "Lvv;")
   @ObfuscatedName("cy")
   public static classVV field6689;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      for (MidiRequest var2 : classNP.field4696) {
         if (var2 != null && !var2.field4641 && null != var2.field4639) {
            try {
               var2.field4639.method8063((byte)122);
               var2.field4639.method8048(0, -1567552141);
               if (var2.field4638 != null) {
                  var2.field4639.method8071(var2.field4638, var2.field4634, (byte)66);
               }

               var2.field4638 = null;
               var2.field4637 = null;
               var2.field4640 = null;
               var2.field4641 = true;
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)125);
               classWT.method12694(this, var4.getMessage(), (byte)48);
               return true;
            }
         }
      }

      this.field6776 = true;
      return true;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         Iterator var2 = classNP.field4696.iterator();

         while (var2.hasNext()) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            MidiRequest var3 = (MidiRequest)var2.next();
            if (var3 != null) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               if (!var3.field4641) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  if (null == var3.field4639) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }
                  } else {
                     try {
                        var3.field4639.method8063((byte)53);
                        var3.field4639.method8048(0, -1892261057);
                        if (var3.field4638 != null) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           var3.field4639.method8071(var3.field4638, var3.field4634, (byte)28);
                        }

                        var3.field4638 = null;
                        var3.field4637 = null;
                        var3.field4640 = null;
                        var3.field4641 = true;
                     } catch (Exception var5) {
                        classAAU.method270(null, var5, (byte)106);
                        classWT.method12694(this, var5.getMessage(), (byte)97);
                        return true;
                     }
                  }
               }
            }
         }

         this.field6776 = true;
         return true;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "wk.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      for (MidiRequest var2 : classNP.field4696) {
         if (var2 != null && !var2.field4641 && null != var2.field4639) {
            try {
               var2.field4639.method8063((byte)5);
               var2.field4639.method8048(0, -1662800148);
               if (var2.field4638 != null) {
                  var2.field4639.method8071(var2.field4638, var2.field4634, (byte)29);
               }

               var2.field4638 = null;
               var2.field4637 = null;
               var2.field4640 = null;
               var2.field4641 = true;
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)53);
               classWT.method12694(this, var4.getMessage(), (byte)22);
               return true;
            }
         }
      }

      this.field6776 = true;
      return true;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      for (MidiRequest var2 : classNP.field4696) {
         if (var2 != null && !var2.field4641 && null != var2.field4639) {
            try {
               var2.field4639.method8063((byte)43);
               var2.field4639.method8048(0, -1682547890);
               if (var2.field4638 != null) {
                  var2.field4639.method8071(var2.field4638, var2.field4634, (byte)122);
               }

               var2.field4638 = null;
               var2.field4637 = null;
               var2.field4640 = null;
               var2.field4641 = true;
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)117);
               classWT.method12694(this, var4.getMessage(), (byte)-74);
               return true;
            }
         }
      }

      this.field6776 = true;
      return true;
   }

   public classWK(classWT var1) {
      super(var1);
      this.field6775 = "StartSongTask";
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      for (MidiRequest var2 : classNP.field4696) {
         if (var2 != null && !var2.field4641 && null != var2.field4639) {
            try {
               var2.field4639.method8063((byte)62);
               var2.field4639.method8048(0, -1172571768);
               if (var2.field4638 != null) {
                  var2.field4639.method8071(var2.field4638, var2.field4634, (byte)60);
               }

               var2.field4638 = null;
               var2.field4637 = null;
               var2.field4640 = null;
               var2.field4641 = true;
            } catch (Exception var4) {
               classAAU.method270(null, var4, (byte)80);
               classWT.method12694(this, var4.getMessage(), (byte)-13);
               return true;
            }
         }
      }

      this.field6776 = true;
      return true;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("aw")
   static final int method12446(int var0, int var1, int var2) {
      try {
         if (var0 == -1) {
            if (var2 >= 745444668) {
               throw new IllegalStateException();
            } else {
               return 12345678;
            }
         } else {
            var1 = var1 * (var0 & 127) / 128;
            if (var1 < 2) {
               if (var2 >= 745444668) {
                  throw new IllegalStateException();
               }

               var1 = 2;
            } else if (var1 > 126) {
               if (var2 >= 745444668) {
                  throw new IllegalStateException();
               }

               var1 = 126;
            }

            return (var0 & 65408) + var1;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wk.aw(" + 41);
      }
   }
}
