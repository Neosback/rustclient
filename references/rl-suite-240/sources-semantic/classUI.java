import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ui")
public interface classUI extends classUN {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lym;IIIII)V")
   @ObfuscatedName("hd")
   static void method11386(SpritePixels var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         var0.method13528();
      }

      if (var3 > 0 && var4 > 0) {
         int var6 = var0.field7005;
         int var7 = var0.field7007;
         int var8 = 0;
         int var9 = 0;
         int var10 = var0.field7004;
         int var11 = var0.field7006;
         int var12 = (var10 << 16) / var3;
         int var13 = (var11 << 16) / var4;
         if (var0.field7008 > 0) {
            int var14 = ((var0.field7008 << 16) + var12 - 1) / var12;
            var1 += var14;
            var8 += var14 * var12 - (var0.field7008 << 16);
         }

         if (var0.field7009 > 0) {
            int var17 = ((var0.field7009 << 16) + var13 - 1) / var13;
            var2 += var17;
            var9 += var17 * var13 - (var0.field7009 << 16);
         }

         if (var6 < var10) {
            var3 = ((var6 << 16) - var8 + var12 - 1) / var12;
         }

         if (var7 < var11) {
            var4 = ((var7 << 16) - var9 + var13 - 1) / var13;
         }

         int var18 = var1 + var2 * SpritePixels.field7100;
         int var15 = SpritePixels.field7100 - var3;
         if (var2 + var4 > SpritePixels.field7096) {
            var4 -= var2 + var4 - SpritePixels.field7096;
         }

         if (var2 < SpritePixels.field7094) {
            int var16 = SpritePixels.field7094 - var2;
            var4 -= var16;
            var18 += var16 * SpritePixels.field7100;
            var9 += var13 * var16;
         }

         if (var1 + var3 > SpritePixels.field7097) {
            int var19 = var1 + var3 - SpritePixels.field7097;
            var3 -= var19;
            var15 += var19;
         }

         if (var1 < SpritePixels.field7095) {
            int var20 = SpritePixels.field7095 - var1;
            var3 -= var20;
            var18 += var20;
            var8 += var12 * var20;
            var15 += var20;
         }

         SpritePixels.method13500(SpritePixels.field7102, var0.field7003, 0, var8, var9, var18, var15, var3, var4, var12, var13, var6, var5);
      }
   }
}
