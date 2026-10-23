import java.util.concurrent.ThreadFactory;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sf")
final class classSF implements ThreadFactory {
   @ToRemove(unused = "true")
   @ObfuscatedName("cj")
   public static final int field5919 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field5918 = 41;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsf;Ljava/lang/Runnable;)Ljava/lang/Thread;")
   @ObfuscatedName("xb")
   public static Thread method10428(classSF var0, Runnable var1) {
      return var0 == null ? var0.method10429(var1) : new Thread(var1, "OSRS Maya Anim Load");
   }

   @Override
   public Thread newThread(Runnable var1) {
      try {
         return new Thread(var1, "OSRS Maya Anim Load");
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sf.newThread(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Runnable;)Ljava/lang/Thread;")
   @ObfuscatedName("af")
   public Thread method10429(Runnable var1) {
      return new Thread(var1, "OSRS Maya Anim Load");
   }
}
