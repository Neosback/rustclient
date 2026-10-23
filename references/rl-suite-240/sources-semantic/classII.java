import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ii")
public interface classII {
   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)V")
   @ObfuscatedName("ae")
   void method6255(Object var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcs;)V")
   @ObfuscatedName("po")
   static void method6254(classCS var0) {
      var0.field1250 = classIW.method6365(
         var0.field1229[0][0], var0.field1229[0][1], var0.field1229[0][2], var0.field1229[0][3], var0.field1229[0][4], (byte)127
      );
      var0.field1249 = classIW.method6365(
         var0.field1229[1][0], var0.field1229[1][1], var0.field1229[1][2], var0.field1229[1][3], var0.field1229[1][4], (byte)63
      );
      var0.field1255 = classIW.method6365(
         var0.field1229[2][0], var0.field1229[2][1], var0.field1229[2][2], var0.field1229[2][3], var0.field1229[2][4], (byte)85
      );
      var0.field1251 = new int[256];
      var0.field1232 = 0;
      var0.field1248 = new int[32768];
      var0.field1253 = new int[32768];
      var0.method2610(null, 1130018239);
      var0.field1252 = new int[32768];
      var0.field1254 = new int[32768];
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)V")
   @ObfuscatedName("af")
   void method6256(Object var1);

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)V")
   @ObfuscatedName("az")
   void method6257(Object var1);
}
