import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jh")
public final class classJH {
   @ToRemove(unused = "true")
   @ObfuscatedName("bw")
   static final int field3153 = 9;
   @ObfuscatedName("af")
   static long field3154;
   @ObfuscatedName("az")
   static long field3155;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field3152 = 61;
   @ObfuscatedName("bs")
   static String field3156;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   public static final int field3151 = 6;

   classJH() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("af")
   public static final synchronized long method6409() {
      long var0 = System.currentTimeMillis();
      if (var0 < -3314051952393191505L * field3155) {
         field3154 = field3154 + (field3155 * -3314051952393191505L - var0) * -5842852769318758503L;
      }

      field3155 = 6736306076594779983L * var0;
      return -8691437873563546455L * field3154 + var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("ae")
   public static final synchronized long method6410() {
      long var0 = System.currentTimeMillis();
      if (var0 < -3314051952393191505L * field3155) {
         field3154 = field3154 + (field3155 * -3314051952393191505L - var0) * -5842852769318758503L;
      }

      field3155 = 6736306076594779983L * var0;
      return -8691437873563546455L * field3154 + var0;
   }
}
