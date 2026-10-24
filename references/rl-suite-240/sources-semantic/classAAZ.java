import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aaz")
public class classAAZ {
   @ObfuscatedName("af")
   public Object field120;
   @ObfuscatedName("az")
   public final int field119;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaz;)I")
   @ObfuscatedName("zs")
   public static int method292(classAAZ var0) {
      return var0 == null ? var0.method293() : var0.hashCode();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method296(Object var1) {
      if (!(var1 instanceof classAAZ)) {
         return false;
      } else {
         classAAZ var2 = (classAAZ)var1;
         if (null == var2.field120 && null != this.field120) {
            return false;
         } else {
            return this.field120 == null && null != var2.field120
               ? false
               : this.field119 * -1072901121 == var2.field119 * 1688097432 && var2.field120.equals(this.field120);
         }
      }
   }

   @Override
   public int hashCode() {
      try {
         return super.hashCode();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "aaz.hashCode(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (!(var1 instanceof classAAZ)) {
            return false;
         } else {
            classAAZ var2 = (classAAZ)var1;
            if (null == var2.field120 && null != this.field120) {
               return false;
            } else {
               return this.field120 == null && null != var2.field120
                  ? false
                  : this.field119 * -1072901121 == var2.field119 * -1072901121 && var2.field120.equals(this.field120);
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "aaz.equals(" + ')');
      }
   }

   public classAAZ(int var1, Object var2) {
      this.field119 = 1885547519 * var1;
      this.field120 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method297(Object var1) {
      if (!(var1 instanceof classAAZ)) {
         return false;
      } else {
         classAAZ var2 = (classAAZ)var1;
         if (null == var2.field120 && null != this.field120) {
            return false;
         } else {
            return this.field120 == null && null != var2.field120
               ? false
               : this.field119 * -1072901121 == var2.field119 * -1072901121 && var2.field120.equals(this.field120);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method293() {
      return super.hashCode();
   }

   public classAAZ(int var1) {
      this.field119 = 1885547519 * var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method294() {
      return super.hashCode();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcl;)I")
   @ObfuscatedName("tj")
   public static int method298(classCL var0) {
      if (var0 == null) {
         var0.method1587();
      }

      return var0.field779 * -1688595207;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method295() {
      return super.hashCode();
   }
}
