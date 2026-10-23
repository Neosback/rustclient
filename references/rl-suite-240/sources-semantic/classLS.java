import java.awt.Component;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ls")
public class classLS {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4214 = 25;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field4215 = 21;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4216 = 149;
   @ObfuscatedName("by")
   static int field4217;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltc;Ljava/awt/Component;)V")
   @ObfuscatedName("lb")
   public static void method7195(classTC var0, Component var1) {
      if (var0 == null) {
         var0.vmethod527();
      }

      var1.removeMouseWheelListener(var0);
   }

   classLS() throws Throwable {
      throw new Error();
   }
}
