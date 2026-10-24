import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wp")
public class classWP extends classWT {
   @ObfuscatedSignature(descriptor = "Lnb;")
   @ObfuscatedName("az")
   MidiRequest field6763 = null;
   @ObfuscatedName("af")
   int field6762 = 0;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcj;)I")
   @ObfuscatedName("cs")
   public static int method12678(classCJ var0) {
      return var0 == null ? var0.method1565() : (int)var0.field752;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      if (null != this.field6763 && null != this.field6763.field4639) {
         this.field6763.field4632 = true;

         try {
            if (this.field6763.field4629 < 987551761 * this.field6763.field4633 && classNU.method8085(this.field6763.field4639, (byte)10)) {
               float var1 = 0 == 1635624515 * this.field6762
                  ? this.field6762 * 1635624515
                  : (float)(987551761 * this.field6763.field4633) / (this.field6762 * 1635624515);
               this.field6763.field4629 = this.field6763.field4629 + (0.0F == var1 ? 987551761 * this.field6763.field4633 : var1);
               if (this.field6763.field4629 > 987551761 * this.field6763.field4633) {
                  this.field6763.field4629 = 987551761 * this.field6763.field4633;
               }

               this.field6763.field4639.method8048((int)this.field6763.field4629, -2043763359);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)5);
            return true;
         }

         this.field6763.field4632 = false;
         return true;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      if (null != this.field6763 && null != this.field6763.field4639) {
         this.field6763.field4632 = true;

         try {
            if (this.field6763.field4629 < 1492954283 * this.field6763.field4633 && classNU.method8085(this.field6763.field4639, (byte)10)) {
               float var1 = 0 == 1635624515 * this.field6762
                  ? this.field6762 * -925097286
                  : (float)(987551761 * this.field6763.field4633) / (this.field6762 * -411073265);
               this.field6763.field4629 = this.field6763.field4629 + (0.0F == var1 ? 987551761 * this.field6763.field4633 : var1);
               if (this.field6763.field4629 > 987551761 * this.field6763.field4633) {
                  this.field6763.field4629 = -1264233966 * this.field6763.field4633;
               }

               this.field6763.field4639.method8048((int)this.field6763.field4629, -913279221);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)-55);
            return true;
         }

         this.field6763.field4632 = false;
         return true;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      if (null != this.field6763 && null != this.field6763.field4639) {
         this.field6763.field4632 = true;

         try {
            if (this.field6763.field4629 < 987551761 * this.field6763.field4633 && classNU.method8085(this.field6763.field4639, (byte)10)) {
               float var1 = 0 == 1635624515 * this.field6762
                  ? this.field6762 * 1635624515
                  : (float)(987551761 * this.field6763.field4633) / (this.field6762 * -1429153329);
               this.field6763.field4629 = this.field6763.field4629 + (0.0F == var1 ? 987551761 * this.field6763.field4633 : var1);
               if (this.field6763.field4629 > 987551761 * this.field6763.field4633) {
                  this.field6763.field4629 = 987551761 * this.field6763.field4633;
               }

               this.field6763.field4639.method8048((int)this.field6763.field4629, -1903871003);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)-35);
            return true;
         }

         this.field6763.field4632 = false;
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
         if (null != this.field6763) {
            if (null != this.field6763.field4639) {
               this.field6763.field4632 = true;

               try {
                  if (this.field6763.field4629 < 987551761 * this.field6763.field4633) {
                     if (var1 <= 1) {
                        throw new IllegalStateException();
                     }

                     if (classNU.method8085(this.field6763.field4639, (byte)10)) {
                        if (var1 <= 1) {
                           throw new IllegalStateException();
                        }

                        float var10000;
                        if (0 == 1635624515 * this.field6762) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           var10000 = this.field6762 * 1635624515;
                        } else {
                           var10000 = (float)(987551761 * this.field6763.field4633) / (this.field6762 * 1635624515);
                        }

                        float var2 = var10000;
                        float var10002;
                        if (0.0F == var2) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           var10002 = 987551761 * this.field6763.field4633;
                        } else {
                           var10002 = var2;
                        }

                        this.field6763.field4629 += var10002;
                        if (this.field6763.field4629 > 987551761 * this.field6763.field4633) {
                           if (var1 <= 1) {
                              throw new IllegalStateException();
                           }

                           this.field6763.field4629 = 987551761 * this.field6763.field4633;
                        }

                        this.field6763.field4639.method8048((int)this.field6763.field4629, -813147579);
                        return false;
                     }
                  }
               } catch (Exception var3) {
                  classWT.method12694(this, var3.getMessage(), (byte)23);
                  return true;
               }

               this.field6763.field4632 = false;
               return true;
            }

            if (var1 <= 1) {
               throw new IllegalStateException();
            }
         }

         return true;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wp.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      if (null != this.field6763 && null != this.field6763.field4639) {
         this.field6763.field4632 = true;

         try {
            if (this.field6763.field4629 < 987551761 * this.field6763.field4633 && classNU.method8085(this.field6763.field4639, (byte)10)) {
               float var1 = 0 == 1635624515 * this.field6762
                  ? this.field6762 * 1635624515
                  : (float)(987551761 * this.field6763.field4633) / (this.field6762 * 263904644);
               this.field6763.field4629 = this.field6763.field4629 + (0.0F == var1 ? 987551761 * this.field6763.field4633 : var1);
               if (this.field6763.field4629 > -575862831 * this.field6763.field4633) {
                  this.field6763.field4629 = -2025388868 * this.field6763.field4633;
               }

               this.field6763.field4639.method8048((int)this.field6763.field4629, -1171100049);
               return false;
            }
         } catch (Exception var2) {
            classWT.method12694(this, var2.getMessage(), (byte)117);
            return true;
         }

         this.field6763.field4632 = false;
         return true;
      } else {
         return true;
      }
   }

   public classWP(classWT var1, int var2, boolean var3, int var4) {
      super(var1);
      this.field6775 = "FadeInTask";
      if (var2 >= 0) {
         if (var3 && var2 < classNP.field4695.size()) {
            this.field6763 = (MidiRequest)classNP.field4695.get(var2);
         } else if (!var3 && var2 < classNP.field4696.size()) {
            this.field6763 = (MidiRequest)classNP.field4696.get(var2);
         }

         this.field6762 = -2025105813 * var4;
      }
   }
}
