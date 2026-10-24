import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lt")
public class classLT {
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   public static final int field4219 = 32;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4220 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4218 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("lh")
   static String field4222;
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   public static final int field4221 = 32;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwc;)V")
   @ObfuscatedName("ud")
   public static void method7196(classWC var0) {
      if (null != var0.field6658) {
         int var1 = var0.field6658.method546();
         long var2 = classDD.method2988(16711680);
         long var4 = var2 - var0.field6657 * -7454620674419427857L;
         int var6 = classWC.method12380(var0, var1, var4);
         if (var6 != var1) {
            var0.field6658.method549(var6);
         }

         if (!var0.field6658.method12129()) {
            var0.field6658 = null;
         }
      }
   }

   classLT() throws Throwable {
      throw new Error();
   }
}
