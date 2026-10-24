import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zi")
public class classZI {
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field7186 = 0;
   @ObfuscatedName("az")
   static long[] field7185 = new long[65];

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ad")
   public static int method14060(int var0, int var1) {
      int var2 = 1;

      while (var1 > 1) {
         if ((var1 & 1) != 0) {
            var2 *= var0;
         }

         var0 *= var0;
         var1 >>= 1;
      }

      return var1 == 1 ? var2 * var0 : var2;
   }

   static {
      new Object();
      field7185[0] = 0L;
      long var0 = 2L;

      for (int var2 = 1; var2 < field7185.length; var2++) {
         field7185[var2] = var0 - 1L;
         var0 += var0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JII)J")
   @ObfuscatedName("an")
   public static long method14069(long var0, int var2, int var3) {
      long var4 = classOW.method8842(var3 - var2 + 1, 898533552);
      var4 <<= var2;
      long var6;
      return var6 = var0 & ~var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ap")
   public static int method14061(int var0, int var1) {
      int var2 = 1;

      while (var1 > 1) {
         if ((var1 & 1) != 0) {
            var2 *= var0;
         }

         var0 *= var0;
         var1 >>= 1;
      }

      return var1 == 1 ? var2 * var0 : var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("bw")
   public static float method14086(float var0, float var1, float var2) {
      if (var1 < var0) {
         throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
      } else {
         return Math.max(var0, Math.min(var2, var1));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   public static int method14063(int var0) {
      var0 = (var0 & 1431655765) + (var0 >>> 1 & 1431655765);
      var0 = (var0 & 721327271) + (var0 >>> 2 & 729523669);
      var0 = var0 + (var0 >>> 4) & -878518751;
      var0 += var0 >>> 8;
      var0 += var0 >>> 16;
      return var0 & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   public static int method14064(int var0) {
      var0 = (var0 & 1431655765) + (var0 >>> 1 & 1431655765);
      var0 = (var0 & 858993459) + (var0 >>> 2 & 858993459);
      var0 = var0 + (var0 >>> 4) & -1018652240;
      var0 += var0 >>> 8;
      var0 += var0 >>> 16;
      return var0 & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aw")
   public static int method14065(int var0) {
      var0 = (var0 & 1431655765) + (var0 >>> 1 & 1431655765);
      var0 = (var0 & 858993459) + (var0 >>> 2 & 365786094);
      var0 = var0 + (var0 >>> 4) & 252645135;
      var0 += var0 >>> 8;
      var0 += var0 >>> 16;
      return var0 & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("av")
   public static int method14067(long var0) {
      var0 = (var0 & 6148914691236517205L) + (var0 >>> 1 & 6148914691236517205L);
      var0 = (var0 >>> 2 & 3689348814741910323L) + (var0 & 3689348814741910323L);
      var0 = (var0 >>> 4) + var0 & 1085102592571150095L;
      var0 += var0 >>> 8;
      var0 += var0 >>> 16;
      var0 += var0 >>> 32;
      return (int)(var0 & 255L);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bc")
   public static int method14074(int var0) {
      int var1 = 0;
      if (var0 < 0 || var0 >= 65536) {
         var0 >>>= 16;
         var1 += 16;
      }

      if (var0 >= 256) {
         var0 >>>= 8;
         var1 += 8;
      }

      if (var0 >= 16) {
         var0 >>>= 4;
         var1 += 4;
      }

      if (var0 >= 4) {
         var0 >>>= 2;
         var1 += 2;
      }

      if (var0 >= 1) {
         var0 >>>= 1;
         var1++;
      }

      return var1 + var0;
   }

   classZI() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bt")
   public static int method14078(int var0, int var1) {
      return var0 + (int)(Math.random() * (var1 - var0));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)Z")
   @ObfuscatedName("sw")
   public static boolean method14077(classQC var0) {
      return var0 == null ? var0.method9515() : false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bx")
   public static int method14073(int var0) {
      var0 = --var0 | var0 >>> 1;
      var0 |= var0 >>> 2;
      var0 |= var0 >>> 4;
      var0 |= var0 >>> 8;
      var0 |= var0 >>> 16;
      return var0 + 1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ah")
   public static int method14070(int var0, int var1, int var2) {
      int var3 = (int)classOW.method8842(var2 - var1 + 1, 1770186300);
      var3 <<= var1;
      return var0 | var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JII)J")
   @ObfuscatedName("bn")
   public static long method14072(long var0, int var2, int var3) {
      long var4 = classOW.method8842(var3 - var2 + 1, 1557563467);
      var4 <<= var2;
      return var0 | var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIFFF)F")
   @ObfuscatedName("bi")
   public static float method14088(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10) {
      float var11 = (var5 - var7) * (var4 - var3) + (var2 - var4) * (var6 - var7);
      float var12 = ((var0 - var4) * (var6 - var7) + (var1 - var7) * (var4 - var3)) / var11;
      float var13 = ((var2 - var4) * (var1 - var7) + (var0 - var4) * (var7 - var5)) / var11;
      float var14 = 1.0F - var12 - var13;
      return var13 * var9 + var12 * var8 + var14 * var10;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bj")
   public static int method14079(int var0, int var1) {
      return var0 + (int)(Math.random() * (var1 - var0));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bp")
   public static int method14075(int var0) {
      int var1 = 0;
      if (var0 < 0 || var0 >= 65536) {
         var0 >>>= 16;
         var1 += 16;
      }

      if (var0 >= 256) {
         var0 >>>= 8;
         var1 += 8;
      }

      if (var0 >= 16) {
         var0 >>>= 4;
         var1 += 4;
      }

      if (var0 >= 4) {
         var0 >>>= 2;
         var1 += 2;
      }

      if (var0 >= 1) {
         var0 >>>= 1;
         var1++;
      }

      return var1 + var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("br")
   public static int method14076(int var0) {
      int var1 = 0;
      if (var0 < 0 || var0 >= -1591099519) {
         var0 >>>= 16;
         var1 += 16;
      }

      if (var0 >= 256) {
         var0 >>>= 8;
         var1 += 8;
      }

      if (var0 >= 16) {
         var0 >>>= 4;
         var1 += 4;
      }

      if (var0 >= 4) {
         var0 >>>= 2;
         var1 += 2;
      }

      if (var0 >= 1) {
         var0 >>>= 1;
         var1++;
      }

      return var1 + var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bm")
   public static int method14080(int var0, int var1) {
      return var0 + (int)(Math.random() * (var1 - var0));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ak")
   public static int method14066(int var0) {
      var0 = (var0 & 1431655765) + (var0 >>> 1 & 1431655765);
      var0 = (var0 & 858993459) + (var0 >>> 2 & 858993459);
      var0 = var0 + (var0 >>> 4) & 1087454964;
      var0 += var0 >>> 8;
      var0 += var0 >>> 16;
      return var0 & 0xFF;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bg")
   public static int method14081(int var0, int var1) {
      return var0 + (int)(Math.random() * (var1 - var0));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("au")
   public static long method14062(int var0) {
      return field7185[var0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bk")
   public static int method14082(int var0, int var1, int var2) {
      if (var1 < var0) {
         throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
      } else {
         return Math.max(var0, Math.min(var2, var1));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bo")
   public static int method14083(int var0, int var1, int var2) {
      if (var1 < var0) {
         throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
      } else {
         return Math.max(var0, Math.min(var2, var1));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("bu")
   public static int method14084(int var0, int var1, int var2) {
      if (var1 < var0) {
         throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
      } else {
         return Math.max(var0, Math.min(var2, var1));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("ba")
   public static int method14085(int var0, int var1, int var2) {
      if (var1 < var0) {
         throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
      } else {
         return Math.max(var0, Math.min(var2, var1));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("at")
   public static int method14068(int var0, int var1, int var2) {
      int var3 = (int)classOW.method8842(1 + (var2 - var1), 1484354237);
      var3 <<= var1;
      int var4;
      return var4 = var0 & ~var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(FFF)F")
   @ObfuscatedName("bb")
   public static float method14087(float var0, float var1, float var2) {
      if (var1 < var0) {
         throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
      } else {
         return Math.max(var0, Math.min(var2, var1));
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("am")
   public static int method14071(int var0, int var1, int var2) {
      int var3 = (int)classOW.method8842(var2 - var1 + 1, 2012847091);
      var3 <<= var1;
      return var0 | var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIIIIIFFF)F")
   @ObfuscatedName("be")
   public static float method14089(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8, float var9, float var10) {
      float var11 = (var5 - var7) * (var4 - var3) + (var2 - var4) * (var6 - var7);
      float var12 = ((var0 - var4) * (var6 - var7) + (var1 - var7) * (var4 - var3)) / var11;
      float var13 = ((var2 - var4) * (var1 - var7) + (var0 - var4) * (var7 - var5)) / var11;
      float var14 = 1.0F - var12 - var13;
      return var13 * var9 + var12 * var8 + var14 * var10;
   }
}
