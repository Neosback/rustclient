import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wo")
public class classWO extends classWT {
   @ObfuscatedSignature(descriptor = "Lyd;")
   @ObfuscatedName("qi")
   public static BufferProvider field6761;
   @ObfuscatedName("af")
   int field6758;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   public static final long field6760 = -1L;
   @ObfuscatedSignature(descriptor = "Lnb;")
   @ObfuscatedName("az")
   MidiRequest field6759 = null;

   public classWO(classWT var1, int var2, boolean var3, int var4) {
      super(var1);
      this.field6758 = 0;
      this.field6775 = "FadeOutTask";
      if (var2 >= 0) {
         if (var3 && var2 < classNP.field4695.size()) {
            this.field6759 = (MidiRequest)classNP.field4695.get(var2);
         } else if (!var3 && var2 < classNP.field4696.size()) {
            this.field6759 = (MidiRequest)classNP.field4696.get(var2);
         }

         this.field6758 = var4 * -2087356477;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      if (null != this.field6759 && this.field6759.field4639 != null) {
         this.field6759.field4632 = true;

         try {
            if (this.field6759.field4629 > 0.0F && classNU.method8085(this.field6759.field4639, (byte)10)) {
               float var1 = 0 == this.field6758 * 1324117947
                  ? 572965618 * this.field6758
                  : (float)(this.field6759.field4633 * 434534042) / (this.field6758 * -1090266437);
               this.field6759.field4629 = this.field6759.field4629 - (0.0F == var1 ? -415146132 * this.field6759.field4633 : var1);
               if (this.field6759.field4629 < 0.0F) {
                  this.field6759.field4629 = 0.0F;
               }

               this.field6759.field4639.method8048((int)this.field6759.field4629, -1865827252);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)76);
            return true;
         }

         this.field6759.field4632 = false;
         return true;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         if (null != this.field6759) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            if (this.field6759.field4639 != null) {
               this.field6759.field4632 = true;

               try {
                  if (this.field6759.field4629 > 0.0F) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     if (classNU.method8085(this.field6759.field4639, (byte)10)) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        float var2 = 0 == this.field6758 * 1818734827
                           ? 1818734827 * this.field6758
                           : (float)(this.field6759.field4633 * 987551761) / (this.field6758 * 1818734827);
                        float var10002;
                        if (0.0F == var2) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           var10002 = 987551761 * this.field6759.field4633;
                        } else {
                           var10002 = var2;
                        }

                        this.field6759.field4629 -= var10002;
                        if (this.field6759.field4629 < 0.0F) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           this.field6759.field4629 = 0.0F;
                        }

                        this.field6759.field4639.method8048((int)this.field6759.field4629, -1976221404);
                        return false;
                     }
                  }
               } catch (Exception var3) {
                  classWT.method12694(this, var3.getMessage(), (byte)-15);
                  return true;
               }

               this.field6759.field4632 = false;
               return true;
            }

            if (var1 <= 1) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wo.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      if (null != this.field6759 && this.field6759.field4639 != null) {
         this.field6759.field4632 = true;

         try {
            if (this.field6759.field4629 > 0.0F && classNU.method8085(this.field6759.field4639, (byte)10)) {
               float var1 = 0 == this.field6758 * -1267987378
                  ? 1818734827 * this.field6758
                  : (float)(this.field6759.field4633 * 987551761) / (this.field6758 * 19374187);
               this.field6759.field4629 = this.field6759.field4629 - (0.0F == var1 ? -363074491 * this.field6759.field4633 : var1);
               if (this.field6759.field4629 < 0.0F) {
                  this.field6759.field4629 = 0.0F;
               }

               this.field6759.field4639.method8048((int)this.field6759.field4629, -1452296843);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)9);
            return true;
         }

         this.field6759.field4632 = false;
         return true;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      if (null != this.field6759 && this.field6759.field4639 != null) {
         this.field6759.field4632 = true;

         try {
            if (this.field6759.field4629 > 0.0F && classNU.method8085(this.field6759.field4639, (byte)10)) {
               float var1 = 0 == this.field6758 * 474687356
                  ? -1518414692 * this.field6758
                  : (float)(this.field6759.field4633 * -1162595035) / (this.field6758 * 1818734827);
               this.field6759.field4629 = this.field6759.field4629 - (0.0F == var1 ? 987551761 * this.field6759.field4633 : var1);
               if (this.field6759.field4629 < 0.0F) {
                  this.field6759.field4629 = 0.0F;
               }

               this.field6759.field4639.method8048((int)this.field6759.field4629, -1492130809);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)-5);
            return true;
         }

         this.field6759.field4632 = false;
         return true;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      if (null != this.field6759 && this.field6759.field4639 != null) {
         this.field6759.field4632 = true;

         try {
            if (this.field6759.field4629 > 0.0F && classNU.method8085(this.field6759.field4639, (byte)10)) {
               float var1 = 0 == this.field6758 * -1941772200
                  ? 1818734827 * this.field6758
                  : (float)(this.field6759.field4633 * -1465627315) / (this.field6758 * 1818734827);
               this.field6759.field4629 = this.field6759.field4629 - (0.0F == var1 ? 987551761 * this.field6759.field4633 : var1);
               if (this.field6759.field4629 < 0.0F) {
                  this.field6759.field4629 = 0.0F;
               }

               this.field6759.field4639.method8048((int)this.field6759.field4629, -1468411703);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)20);
            return true;
         }

         this.field6759.field4632 = false;
         return true;
      } else {
         return true;
      }
   }
}
