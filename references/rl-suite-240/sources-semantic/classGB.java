import net.runelite.api.Perspective;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gb")
public class classGB {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field2543 = 16384;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field2540 = 16383;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field2539 = 14;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field2541 = 65536;
   @ObfuscatedName("ax")
   static final int[] field2538 = new int[16384];
   @ObfuscatedName("km")
   static boolean field2545;
   @ObfuscatedName("aa")
   public static final float[] field2537 = new float[16384];
   @ObfuscatedName("as")
   static final int[] field2535 = new int[16384];
   @ObfuscatedSignature(descriptor = "Lpq;")
   @ObfuscatedName("co")
   static classPQ field2544;
   @ObfuscatedName("ac")
   public static final float[] field2536 = new float[16384];
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field2542 = 12;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;II)V")
   @ObfuscatedName("jk")
   public static void method5334(classUQ var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11564(var1);
      }

      var0.field6366 = -136215617 * var1;
      var0.field6367 = -211068161 * var2;
   }

   static {
      for (int var0 = 0; var0 < 16384; var0++) {
         field2535[var0] = (int)(65536.0 * Math.sin(3.834951969714103E-4 * var0));
         field2538[var0] = (int)(65536.0 * Math.cos(3.834951969714103E-4 * var0));
         field2536[var0] = (float)Math.sin(3.834951969714103E-4 * var0);
         field2537[var0] = (float)Math.cos(3.834951969714103E-4 * var0);
      }

      field2535 = Perspective.SINE14;
      field2538 = Perspective.COSINE14;
      field2536 = Perspective.SINEF14;
      field2537 = Perspective.COSINEF14;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public static int method5326(int var0) {
      return field2535[var0 & 16383];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public static int method5327(int var0) {
      return field2535[var0 & 855428982];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   public static int method5328(int var0) {
      return field2535[var0 & 16383];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   public static int method5329(int var0) {
      return field2535[var0 & 16383];
   }

   classGB() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ac")
   public static int method5330(int var0) {
      return field2535[var0 & 16383];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aa")
   public static int method5331(int var0) {
      return field2538[var0 & 753270889];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   public static int method5332(int var0) {
      return field2538[var0 & 16383];
   }

   @ObfuscatedSignature(descriptor = "(I[IIB)V")
   @ObfuscatedName("az")
   static void method5333(int var0, int[] var1, int var2, byte var3) {
      try {
         for (int var4 = 0; var4 < classOA.method8319((byte)81); var4++) {
            classOF var5 = classHW.method6163(var4, -1876621299);
            if (null != var5) {
               if (var3 != 8) {
                  throw new IllegalStateException();
               }

               if (!var5.field4849) {
                  if (var3 != 8) {
                     throw new IllegalStateException();
                  }

                  if (var5.method8444(var2, var0, -2073253202)) {
                     if (var3 != 8) {
                        throw new IllegalStateException();
                     }

                     var1[classON.method8644(var2, (byte)69)] = 256 + var4;
                     break;
                  }
               }
            }
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gb.az(" + ')');
      }
   }
}
