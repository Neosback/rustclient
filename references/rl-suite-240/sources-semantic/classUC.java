import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("uc")
public class classUC extends classVJ {
   @ObfuscatedName("af")
   int field6283;
   @ObfuscatedName("ae")
   byte field6282;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("az")
   classVP field6284;

   classUC() {
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)Ljava/lang/Integer;")
   @ObfuscatedName("az")
   static Integer method11322(classXY var0, int var1) {
      try {
         int var2 = 0;
         boolean var3 = false;

         label65:
         while (true) {
            int var4 = var0.method13095(-346779531);
            if (var4 == 255) {
               if (var1 != -1964428479) {
                  throw new IllegalStateException();
               }

               return var3 ? var2 : null;
            }

            if (var4 != 0) {
               if (var1 != -1964428479) {
                  throw new IllegalStateException();
               }

               throw new IllegalStateException("");
            }

            while (true) {
               int var5 = var0.method13095(-346779531);
               if (255 == var5) {
                  if (var1 != -1964428479) {
                     break label65;
                  }
                  break;
               }

               var0.field6953 -= -1095856699;
               if (var0.method13099(-1645209627) != 0) {
                  if (var1 != -1964428479) {
                     throw new IllegalStateException();
                  }

                  throw new IllegalStateException("");
               }

               if (var3) {
                  if (var1 != -1964428479) {
                     throw new IllegalStateException();
                  }

                  throw new IllegalStateException("");
               }

               var2 = classXY.method13113(var0, (byte)1);
               var3 = true;
            }
         }

         throw new IllegalStateException();
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "uc.az(" + 41);
      }
   }
}
