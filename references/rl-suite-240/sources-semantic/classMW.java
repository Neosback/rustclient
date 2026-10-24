import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mw")
public class classMW {
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field4609 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4610 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field4608 = 5;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;Ljava/lang/String;I)V")
   @ObfuscatedName("jf")
   public static void method7775(classQI var0, String var1, int var2) {
      var0.field5543.put(var1, var2);
   }

   classMW() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)[Ljava/lang/String;")
   @ObfuscatedName("ot")
   public static String[] method7776(classQC var0) {
      if (var0 == null) {
         var0.method9484();
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   static void method7777(int var0) {
      ClientPreferences var1 = classOE.field4841.method2432();
      classXY var2 = var1.method2841();
      classOE.field4841.field1114.execute(client::method2306);
   }
}
