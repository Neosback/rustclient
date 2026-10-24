import java.io.IOException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vk")
public class classVK extends Node {
   @ObfuscatedName("az")
   public long field6515;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lus;I)[B")
   @ObfuscatedName("li")
   public static byte[] method12039(classUS var0, int var1) {
      synchronized (var0.field6374) {
         byte[] var21;
         try {
            if (var0.field6377.method132(-1914839275) < var1 * 6 + 6) {
               return null;
            }

            var0.field6377.method131(6 * var1);
            var0.field6377.method142(classUS.field6373, 0, 6, (byte)-60);
            int var3 = (classUS.field6373[2] & 255) + ((classUS.field6373[0] & 255) << 16) + ((classUS.field6373[1] & 255) << 8);
            int var4 = (classUS.field6373[5] & 255) + ((classUS.field6373[4] & 255) << 8) + ((classUS.field6373[3] & 255) << 16);
            if (var3 < 0 || var3 > var0.field6375 * -472857467) {
               return null;
            }

            if (var4 <= 0 || var4 > var0.field6374.method132(-832854620) / 520L) {
               return null;
            }

            byte[] var5 = new byte[var3];
            int var6 = 0;
            int var7 = 0;

            while (var6 < var3) {
               if (var4 == 0) {
                  return null;
               }

               var0.field6374.method131(var4 * 520L);
               int var8 = var3 - var6;
               int var9;
               int var10;
               int var11;
               int var12;
               byte var13;
               if (var1 > 65535) {
                  if (var8 > 510) {
                     var8 = 510;
                  }

                  var13 = 10;
                  var0.field6374.method142(classUS.field6373, 0, var8 + var13, (byte)38);
                  var9 = (classUS.field6373[3] & 255)
                     + ((classUS.field6373[0] & 255) << 24)
                     + ((classUS.field6373[1] & 255) << 16)
                     + ((classUS.field6373[2] & 255) << 8);
                  var10 = (classUS.field6373[5] & 255) + ((classUS.field6373[4] & 255) << 8);
                  var11 = ((classUS.field6373[6] & 255) << 16) + ((classUS.field6373[7] & 255) << 8) + (classUS.field6373[8] & 255);
                  var12 = classUS.field6373[9] & 255;
               } else {
                  if (var8 > 512) {
                     var8 = 512;
                  }

                  var13 = 8;
                  var0.field6374.method142(classUS.field6373, 0, var13 + var8, (byte)-11);
                  var9 = ((classUS.field6373[0] & 255) << 8) + (classUS.field6373[1] & 255);
                  var10 = (classUS.field6373[3] & 255) + ((classUS.field6373[2] & 255) << 8);
                  var11 = ((classUS.field6373[4] & 255) << 16) + ((classUS.field6373[5] & 255) << 8) + (classUS.field6373[6] & 255);
                  var12 = classUS.field6373[7] & 255;
               }

               if (var9 == var1 && var7 == var10 && var12 == -516579491 * var0.field6376) {
                  if (var11 >= 0 && var11 <= var0.field6374.method132(-1251052679) / 520L) {
                     int var14 = var13 + var8;

                     for (int var15 = var13; var15 < var14; var15++) {
                        var5[var6++] = classUS.field6373[var15];
                     }

                     var4 = var11;
                     var7++;
                     continue;
                  }

                  return null;
               }

               return null;
            }

            var21 = var5;
         } catch (IOException var17) {
            return null;
         }

         return var21;
      }
   }

   public classVK(long var1) {
      this.field6515 = var1;
   }
}
