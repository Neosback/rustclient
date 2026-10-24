import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ax")
public interface classAX {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqm;)Ljava/lang/String;")
   @ObfuscatedName("vr")
   static String method720(classQM var0) {
      return var0.field5563;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("ys")
   static int method721(classLH var0) {
      return 177023888 * var0.field4151 / classZD.method13953(var0.field4157, (byte)26);
   }
}
