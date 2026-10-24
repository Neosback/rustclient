import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zv")
public final class classZV extends AbstractFont {
   @ObfuscatedSignature(descriptor = "([BIIIII)V")
   @ObfuscatedName("ae")
   @Override
   final void vmethod2(byte[] var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = var2 + var3 * field7100;
      int var8 = field7100 - var4;
      int var9 = 0;
      int var10 = 0;
      if (var3 < field7094) {
         int var11 = field7094 - var3;
         var5 -= var11;
         var3 = field7094;
         var10 += var11 * var4;
         var7 += var11 * field7100;
      }

      if (var3 + var5 > field7096) {
         var5 -= var3 + var5 - field7096;
      }

      if (var2 < field7095) {
         int var12 = field7095 - var2;
         var4 -= var12;
         var2 = field7095;
         var10 += var12;
         var7 += var12;
         var9 += var12;
         var8 += var12;
      }

      if (var2 + var4 > field7097) {
         int var13 = var2 + var4 - field7097;
         var4 -= var13;
         var9 += var13;
         var8 += var13;
      }

      if (var4 > 0 && var5 > 0) {
         method19(field7102, var1, var6, var10, var7, var4, var5, var8, var9);
      }
   }

   @ObfuscatedSignature(descriptor = "([BIIIIII)V")
   @ObfuscatedName("ag")
   @Override
   final void vmethod0(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = var2 + var3 * field7100;
      int var9 = field7100 - var4;
      int var10 = 0;
      int var11 = 0;
      if (var3 < field7094) {
         int var12 = field7094 - var3;
         var5 -= var12;
         var3 = field7094;
         var11 += var12 * var4;
         var8 += var12 * field7100;
      }

      if (var3 + var5 > field7096) {
         var5 -= var3 + var5 - field7096;
      }

      if (var2 < field7095) {
         int var13 = field7095 - var2;
         var4 -= var13;
         var2 = field7095;
         var11 += var13;
         var8 += var13;
         var10 += var13;
         var9 += var13;
      }

      if (var2 + var4 > field7097) {
         int var14 = var2 + var4 - field7097;
         var4 -= var14;
         var10 += var14;
         var9 += var14;
      }

      if (var4 > 0 && var5 > 0) {
         method23(field7102, var1, var6, var11, var8, var4, var5, var9, var10, var7);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsg;I)Luu;")
   @ObfuscatedName("kd")
   public static classUU method14251(classSG var0, int var1) {
      return var0.field5923[var1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lea;Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("gc")
   public static int method14250(classEA var0, Object var1, Object var2) {
      return var0 == null ? var0.compare(var1, var1) : var0.method3806((ChatPlayer)var1, (ChatPlayer)var2, -1530233874);
   }

   @ObfuscatedSignature(descriptor = "([BIIIII)V")
   @ObfuscatedName("az")
   @Override
   final void vmethod1(byte[] var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = var2 + var3 * field7100;
      int var8 = field7100 - var4;
      int var9 = 0;
      int var10 = 0;
      if (var3 < field7094) {
         int var11 = field7094 - var3;
         var5 -= var11;
         var3 = field7094;
         var10 += var11 * var4;
         var7 += var11 * field7100;
      }

      if (var3 + var5 > field7096) {
         var5 -= var3 + var5 - field7096;
      }

      if (var2 < field7095) {
         int var12 = field7095 - var2;
         var4 -= var12;
         var2 = field7095;
         var10 += var12;
         var7 += var12;
         var9 += var12;
         var8 += var12;
      }

      if (var2 + var4 > field7097) {
         int var13 = var2 + var4 - field7097;
         var4 -= var13;
         var9 += var13;
         var8 += var13;
      }

      if (var4 > 0 && var5 > 0) {
         method19(field7102, var1, var6, var10, var7, var4, var5, var8, var9);
      }
   }

   public classZV(byte[] var1, int[] var2, int[] var3, int[] var4, int[] var5, int[] var6, byte[][] var7) {
      super(var1, var2, var3, var4, var5, var7);
   }

   public classZV(byte[] var1) {
      super(var1);
   }

   @ObfuscatedSignature(descriptor = "([BIIIII)V")
   @ObfuscatedName("ab")
   @Override
   final void vmethod3(byte[] var1, int var2, int var3, int var4, int var5, int var6) {
      int var7 = var2 + var3 * field7100;
      int var8 = field7100 - var4;
      int var9 = 0;
      int var10 = 0;
      if (var3 < field7094) {
         int var11 = field7094 - var3;
         var5 -= var11;
         var3 = field7094;
         var10 += var11 * var4;
         var7 += var11 * field7100;
      }

      if (var3 + var5 > field7096) {
         var5 -= var3 + var5 - field7096;
      }

      if (var2 < field7095) {
         int var12 = field7095 - var2;
         var4 -= var12;
         var2 = field7095;
         var10 += var12;
         var7 += var12;
         var9 += var12;
         var8 += var12;
      }

      if (var2 + var4 > field7097) {
         int var13 = var2 + var4 - field7097;
         var4 -= var13;
         var9 += var13;
         var8 += var13;
      }

      if (var4 > 0 && var5 > 0) {
         method19(field7102, var1, var6, var10, var7, var4, var5, var8, var9);
      }
   }

   @ObfuscatedSignature(descriptor = "([BIIIIII)V")
   @ObfuscatedName("af")
   @Override
   final void vmethod4(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = var2 + var3 * field7100;
      int var9 = field7100 - var4;
      int var10 = 0;
      int var11 = 0;
      if (var3 < field7094) {
         int var12 = field7094 - var3;
         var5 -= var12;
         var3 = field7094;
         var11 += var12 * var4;
         var8 += var12 * field7100;
      }

      if (var3 + var5 > field7096) {
         var5 -= var3 + var5 - field7096;
      }

      if (var2 < field7095) {
         int var13 = field7095 - var2;
         var4 -= var13;
         var2 = field7095;
         var11 += var13;
         var8 += var13;
         var10 += var13;
         var9 += var13;
      }

      if (var2 + var4 > field7097) {
         int var14 = var2 + var4 - field7097;
         var4 -= var14;
         var10 += var14;
         var9 += var14;
      }

      if (var4 > 0 && var5 > 0) {
         method23(field7102, var1, var6, var11, var8, var4, var5, var9, var10, var7);
      }
   }
}
