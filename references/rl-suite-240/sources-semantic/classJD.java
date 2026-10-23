import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jd")
public class classJD extends classJQ {
   @ObfuscatedName("az")
   Object field3136;

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ab")
   @Override
   Object vmethod512() {
      return this.field3136;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   @Override
   Object vmethod513() {
      return this.field3136;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   boolean vmethod516() {
      return false;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ae")
   @Override
   Object vmethod514() {
      return this.field3136;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ag")
   @Override
   Object vmethod515() {
      return this.field3136;
   }

   classJD(Object var1, int var2) {
      super(var2);
      this.field3136 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("as")
   @Override
   boolean vmethod517() {
      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;IIIIILee;IJZ)Z")
   @ObfuscatedName("ku")
   public static boolean method6397(Scene var0, int var1, int var2, int var3, int var4, int var5, Renderable var6, int var7, long var8, boolean var10) {
      if (var0 == null) {
         var0.method4458();
      }

      if (var6 == null) {
         return true;
      } else {
         int var11 = var2 - var5;
         int var12 = var3 - var5;
         int var13 = var2 + var5;
         int var14 = var3 + var5;
         if (var10) {
            if (var7 > 640 && var7 < 1408) {
               var14 += 128;
            }

            if (var7 > 1152 && var7 < 1920) {
               var13 += 128;
            }

            if (var7 > 1664 || var7 < 384) {
               var12 -= 128;
            }

            if (var7 > 128 && var7 < 896) {
               var11 -= 128;
            }
         }

         var11 /= 128;
         var12 /= 128;
         var13 /= 128;
         var14 /= 128;
         return var0.method4424(var1, var11, var12, var13 - var11 + 1, var14 - var12 + 1, var2, var3, var4, var6, var7, true, var8, 0);
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   @Override
   boolean vmethod518() {
      return false;
   }
}
