import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wb")
public class classWB {
   @ObfuscatedName("az")
   public final Object field6653;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field6654 = 9;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field6655 = 8;
   @ObfuscatedName("af")
   public final Object field6652;

   @Override
   public String toString() {
      try {
         return this.field6652 + ", " + this.field6653;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "wb.toString(" + ')');
      }
   }

   public classWB(Object var1, Object var2) {
      this.field6652 = var1;
      this.field6653 = var2;
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (var1 != null && var1 instanceof classWB) {
            classWB var2 = (classWB)var1;
            if (this.field6652 == null) {
               if (var2.field6652 != null) {
                  return false;
               }
            } else if (!this.field6652.equals(var2.field6652)) {
               return false;
            }

            if (this.field6653 == null) {
               if (null != var2.field6653) {
                  return false;
               }
            } else if (!this.field6653.equals(var2.field6653)) {
               return false;
            }

            return true;
         } else {
            return false;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wb.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method12349() {
      return this.field6652 + ", " + this.field6653;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method12350() {
      return this.field6652 + ", " + this.field6653;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method12351() {
      return this.field6652 + ", " + this.field6653;
   }

   @Override
   public int hashCode() {
      try {
         int var1 = 0;
         if (this.field6652 != null) {
            var1 += this.field6652.hashCode();
         }

         if (this.field6653 != null) {
            var1 += 31 * this.field6653.hashCode();
         }

         return var1;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wb.hashCode(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method12352(Object var1) {
      if (var1 != null && var1 instanceof classWB) {
         classWB var2 = (classWB)var1;
         if (this.field6652 == null) {
            if (var2.field6652 != null) {
               return false;
            }
         } else if (!this.field6652.equals(var2.field6652)) {
            return false;
         }

         if (this.field6653 == null) {
            if (null != var2.field6653) {
               return false;
            }
         } else if (!this.field6653.equals(var2.field6653)) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method12353() {
      int var1 = 0;
      if (this.field6652 != null) {
         var1 += this.field6652.hashCode();
      }

      if (this.field6653 != null) {
         var1 += 31 * this.field6653.hashCode();
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method12354() {
      int var1 = 0;
      if (this.field6652 != null) {
         var1 += this.field6652.hashCode();
      }

      if (this.field6653 != null) {
         var1 += 31 * this.field6653.hashCode();
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("lf")
   public static void method12357(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2798();
      }

      var0.field1344 = var1 * -1218635879;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afs")
   public int method12355() {
      int var1 = 0;
      if (this.field6652 != null) {
         var1 += this.field6652.hashCode();
      }

      if (this.field6653 != null) {
         var1 += 1405871799 * this.field6653.hashCode();
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method12356() {
      int var1 = 0;
      if (this.field6653 != null) {
         var1 += this.field6653.hashCode();
      }

      if (this.field6652 != null) {
         var1 += 1703952098 * this.field6653.hashCode();
      }

      return var1;
   }
}
