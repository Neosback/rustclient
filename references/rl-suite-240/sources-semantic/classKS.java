import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ks")
public class classKS {
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field4012 = 65536;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4011 = -2;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4013 = 0;

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("aa")
   public static final void method6598(int var0, int var1, byte var2) {
      try {
         classFO.field2347 = var0 * -750923805;
         classFO.field2346 = var1 * 1000488527;
         classFO.field2350 = true;
         classFO.field2348 = 0;
         classFO.field2349[0] = -1L;
         classFO.field2345[0] = Integer.MAX_VALUE;
         classFO.field2344 = false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ks.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/StringBuilder;Lqi;B)V")
   @ObfuscatedName("ab")
   static void method6599(String var0, StringBuilder var1, classQI var2, byte var3) {
      try {
         String[] var4 = var0.split("=");
         if (1 == var4.length) {
            if (var3 <= -1) {
               return;
            }

            var4 = var0.split(":");
            long var5 = var2.method9616(var4[0], (byte)-1);
            String var7 = Long.toString(var5);
            var1.append(var7);
         } else {
            var2.method9599(var4[0], var4[1], 598319735);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "ks.ab(" + ')');
      }
   }

   classKS() throws Throwable {
      throw new Error();
   }
}
