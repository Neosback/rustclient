import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ro")
public final class classRO implements Comparable {
   @ObfuscatedName("az")
   Object field5819;
   @ObfuscatedName("af")
   Object field5820;
   @ObfuscatedName("ae")
   long field5822;
   @ObfuscatedName("ab")
   long field5821;
   @ObfuscatedSignature(descriptor = "Lke;")
   @ObfuscatedName("gp")
   static classKE field5823;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method10190() {
      return this.field5820.hashCode();
   }

   @ObfuscatedSignature(descriptor = "(Lro;I)I")
   @ObfuscatedName("az")
   int method10185(classRO var1, int var2) {
      try {
         if (6150833583405228361L * this.field5821 < 6150833583405228361L * var1.field5821) {
            if (var2 <= -1305254149) {
               throw new IllegalStateException();
            } else {
               return -1;
            }
         } else if (6150833583405228361L * this.field5821 > 6150833583405228361L * var1.field5821) {
            if (var2 <= -1305254149) {
               throw new IllegalStateException();
            } else {
               return 1;
            }
         } else {
            return 0;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ro.az(" + 41);
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (var1 instanceof classRO) {
            return this.field5820.equals(((classRO)var1).field5820);
         } else {
            throw new IllegalArgumentException();
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ro.equals(" + ')');
      }
   }

   @Override
   public int hashCode() {
      try {
         return this.field5820.hashCode();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ro.hashCode(" + 41);
      }
   }

   @Override
   public int compareTo(Object var1) {
      try {
         return this.method10185((classRO)var1, 1370849369);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ro.compareTo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)I")
   @ObfuscatedName("ae")
   public int method10194(Object var1) {
      return this.method10185((classRO)var1, -1122528872);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method10187(Object var1) {
      if (var1 instanceof classRO) {
         return this.field5820.equals(((classRO)var1).field5820);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method10191() {
      return this.field5820.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method10192() {
      return this.field5820.hashCode();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lro;Lro;)I")
   @ObfuscatedName("tv")
   public static int method10186(classRO var0, classRO var1) {
      if (6150833583405228361L * var0.field5821 < 6150833583405228361L * var1.field5821) {
         return -1;
      } else {
         return 6150833583405228361L * var0.field5821 > 6150833583405228361L * var1.field5821 ? 1 : 0;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afs")
   public int method10193() {
      return this.field5820.hashCode();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;)Lvw;")
   @ObfuscatedName("or")
   public static classVW method10196(classVV var0) {
      return var0.field6612;
   }

   classRO(Object var1, Object var2) {
      this.field5819 = var1;
      this.field5820 = var2;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   static void method10195(int var0) {
      try {
         classKU.field4031 = null;
         classOY.field5200 = null;
         classCQ.field1219 = null;
         classPE.field5232 = null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ro.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lro;Ljava/lang/Object;)Z")
   @ObfuscatedName("vx")
   public static boolean method10188(classRO var0, Object var1) {
      if (var0 == null) {
         var0.method10194(var1);
      }

      if (var1 instanceof classRO) {
         return var0.field5820.equals(((classRO)var1).field5820);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("az")
   public static int method10189(int var0, int var1, int var2) {
      try {
         return (int)Math.round(Math.atan2(var0, var1) * 2607.5945876176133) & 16383;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ro.az(" + 41);
      }
   }
}
