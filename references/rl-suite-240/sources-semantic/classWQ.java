import java.util.ArrayList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wq")
public class classWQ extends classWT {
   @ObfuscatedName("az")
   ArrayList field6764;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final int field6765 = 48;
   @ObfuscatedName("av")
   static String[] field6766;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lfl;)Z")
   @ObfuscatedName("jm")
   public static boolean method12679(classFL var0) {
      return var0.field2301 > 0;
   }

   public classWQ(classWT var1, ArrayList var2) {
      super(var1);
      this.field6764 = var2;
      this.field6775 = "ConcurrentMidiTask";
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      for (int var1 = 0; var1 < this.field6764.size(); var1++) {
         classWT var2 = (classWT)this.field6764.get(var1);
         if (var2 == null) {
            this.field6764.remove(var1);
            var1--;
         } else if (var2.vmethod627((byte)45)) {
            if (var2.method12682(1785831228)) {
               classWT.method12694(this, var2.method12687(-357106440), (byte)-61);
               this.field6764.clear();
               return true;
            }

            if (var2.method12690((short)-15369) != null) {
               this.field6764.add(var2.method12690((short)1808));
            }

            this.field6776 = var2.field6776;
            this.field6764.remove(var1);
            var1--;
         }
      }

      return this.field6764.isEmpty();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      for (int var1 = 0; var1 < this.field6764.size(); var1++) {
         classWT var2 = (classWT)this.field6764.get(var1);
         if (var2 == null) {
            this.field6764.remove(var1);
            var1--;
         } else if (var2.vmethod627((byte)55)) {
            if (var2.method12682(-1235614441)) {
               classWT.method12694(this, var2.method12687(476200571), (byte)23);
               this.field6764.clear();
               return true;
            }

            if (var2.method12690((short)15556) != null) {
               this.field6764.add(var2.method12690((short)-7044));
            }

            this.field6776 = var2.field6776;
            this.field6764.remove(var1);
            var1--;
         }
      }

      return this.field6764.isEmpty();
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         for (int var2 = 0; var2 < this.field6764.size(); var2++) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            classWT var3 = (classWT)this.field6764.get(var2);
            if (var3 == null) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               this.field6764.remove(var2);
               var2--;
            } else if (var3.vmethod627((byte)81)) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               if (var3.method12682(-1918286699)) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  classWT.method12694(this, var3.method12687(-1627650028), (byte)-32);
                  this.field6764.clear();
                  return true;
               }

               if (var3.method12690((short)15013) != null) {
                  if (var1 <= 1) {
                     throw new IllegalStateException();
                  }

                  this.field6764.add(var3.method12690((short)-11428));
               }

               this.field6776 = var3.field6776;
               this.field6764.remove(var2);
               var2--;
            }
         }

         if (!this.field6764.isEmpty()) {
            return false;
         } else if (var1 <= 1) {
            throw new IllegalStateException();
         } else {
            return true;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "wq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      for (int var1 = 0; var1 < this.field6764.size(); var1++) {
         classWT var2 = (classWT)this.field6764.get(var1);
         if (var2 == null) {
            this.field6764.remove(var1);
            var1--;
         } else if (var2.vmethod627((byte)14)) {
            if (var2.method12682(-1679124125)) {
               classWT.method12694(this, var2.method12687(-1239469063), (byte)60);
               this.field6764.clear();
               return true;
            }

            if (var2.method12690((short)-18078) != null) {
               this.field6764.add(var2.method12690((short)420));
            }

            this.field6776 = var2.field6776;
            this.field6764.remove(var1);
            var1--;
         }
      }

      return this.field6764.isEmpty();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      for (int var1 = 0; var1 < this.field6764.size(); var1++) {
         classWT var2 = (classWT)this.field6764.get(var1);
         if (var2 == null) {
            this.field6764.remove(var1);
            var1--;
         } else if (var2.vmethod627((byte)94)) {
            if (var2.method12682(86539143)) {
               classWT.method12694(this, var2.method12687(1384148841), (byte)16);
               this.field6764.clear();
               return true;
            }

            if (var2.method12690((short)-17883) != null) {
               this.field6764.add(var2.method12690((short)18678));
            }

            this.field6776 = var2.field6776;
            this.field6764.remove(var1);
            var1--;
         }
      }

      return this.field6764.isEmpty();
   }
}
