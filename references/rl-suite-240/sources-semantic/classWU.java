import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wu")
public class classWU extends classWT {
   @ObfuscatedName("az")
   long field6779;
   @ObfuscatedName("af")
   int field6778;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;ILxs;)V")
   @ObfuscatedName("dt")
   public static void method12697(Actor var0, int var1, PacketBuffer var2) {
      var0.field1461.method1384(var1, var2, (byte)2);
   }

   @ObfuscatedSignature(descriptor = "(B)Lvb;")
   @ObfuscatedName("ha")
   static classVB method12698(byte var0) {
      try {
         return classEB.field1724;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "wu.ha(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod627(byte var1) {
      try {
         if (this.field6779 * -5771031896953023857L < 283153243 * this.field6778) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            } else {
               this.field6779 += 2912728134211619951L;
               return false;
            }
         } else {
            return true;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wu.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   public boolean vmethod628() {
      if (this.field6779 * -5771031896953023857L < 283153243 * this.field6778) {
         this.field6779 += 2912728134211619951L;
         return false;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod629() {
      if (this.field6779 * -5771031896953023857L < 847285166 * this.field6778) {
         this.field6779 += 2912728134211619951L;
         return false;
      } else {
         return true;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod626() {
      if (this.field6779 * -5771031896953023857L < -364655615 * this.field6778) {
         this.field6779 += 2912728134211619951L;
         return false;
      } else {
         return true;
      }
   }

   public classWU(classWT var1, int var2) {
      super(var1);
      this.field6778 = var2 * -357210925;
      this.field6775 = "DelayFadeTask";
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod630() {
      if (this.field6779 * -5771031896953023857L < 283153243 * this.field6778) {
         this.field6779 += 2912728134211619951L;
         return false;
      } else {
         return true;
      }
   }
}
