import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gv")
public class classGV {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final double field2706 = 325.94932345220167;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field2709 = 2047;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2712 = 256;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   public static final int field2713 = 1280;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   public static final int field2707 = 256;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   public static final int field2710 = 768;
   @ObfuscatedName("ar")
   static final int[][] field2705 = new int[512][512];
   @ObfuscatedName("au")
   public static final int[] field2703 = new int[2048];
   @ObfuscatedName("ai")
   static final int[] field2704 = new int[2048];
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field2708 = 65536;
   @ObfuscatedName("ju")
   static String field2714;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2711 = 512;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aq")
   public static int method5835(int var0, int var1) {
      int var2 = var1 - var0 & 2047;
      return var2 > 1024 ? -(2048 - var2) : var2;
   }

   static {
      double var0 = 0.0030679615757712823;

      for (int var2 = 0; var2 < 2048; var2++) {
         field2703[var2] = (int)(65536.0 * Math.sin(var2 * var0));
         field2704[var2] = (int)(65536.0 * Math.cos(var0 * var2));
      }

      for (int var4 = -256; var4 < 256; var4++) {
         for (int var3 = -256; var3 < 256; var3++) {
            field2705[256 + var4][256 + var3] = (int)Math.round(Math.atan2(var4, var3) * 325.94932345220167) - 512 & 2047;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ad")
   public static int method5836(int var0, int var1) {
      int var2 = var1 - var0 & 2047;
      return var2 > 1024 ? -(2048 - var2) : var2;
   }

   classGV() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ax")
   static int method5827(int var0) {
      return field2703[var0 & 2047];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   public static int method5832(int var0) {
      return field2704[var0 & 2047];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aa")
   static int method5828(int var0) {
      return field2703[var0 & 2047];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   static int method5829(int var0) {
      return field2703[var0 & 2047];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("al")
   public static int method5833(int var0) {
      return field2704[var0 & -122529128];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   static int method5830(int var0) {
      return field2703[var0 & 2047];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ay")
   public static int method5837(int var0, int var1) {
      int var2 = var1 - var0 & 2047;
      return var2 > 1024 ? -(2048 - var2) : var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ac")
   static int method5831(int var0) {
      return field2703[var0 & 2047];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)D")
   @ObfuscatedName("aj")
   public static double method5834(int var0) {
      return classLO.method7178(var0, (byte)77) / 65536.0;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ag")
   static void method5838(int var0, byte var1) {
      try {
         ItemContainer var2 = (ItemContainer)HashTable.method13432(ItemContainer.field454, var0);
         if (null == var2) {
            if (var1 <= 0) {
               throw new IllegalStateException();
            }
         } else {
            for (int var3 = 0; var3 < var2.field456.length; var3++) {
               if (var1 <= 0) {
                  throw new IllegalStateException();
               }

               var2.field456[var3] = -1;
               var2.field455[var3] = 0;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gv.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIIIB)V")
   @ObfuscatedName("dh")
   static void method5839(int var0, int var1, int var2, int var3, int var4, byte var5) {
      try {
         classAD.method325(classCQ.field1218, var0, var1, var2, var3, var4, -728364943);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gv.dh(" + ')');
      }
   }
}
