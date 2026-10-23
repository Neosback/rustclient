import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aau")
public class classAAU {
   @ObfuscatedName("az")
   public static int field102 = -214038637;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field103 = 1;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/Throwable;B)V")
   @ObfuscatedName("az")
   public static void method270(String var0, Throwable var1, byte var2) {
      Throwable var3 = var1;
      if (var1 instanceof classAAF && "".equals(var1.getMessage())) {
         var3 = var1.getCause();
      }

      if (var0 == null) {
         client.field1028.error("Client error", var3);
      } else {
         client.field1028.error("Client error: {}", var0, var3);
      }

      classOE.field4841.getCallbacks().error(var0, var3);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lym;I)V")
   @ObfuscatedName("jp")
   public static void method269(SpritePixels var0, int var1) {
      for (int var2 = var0.field7007 - 1; var2 > 0; var2--) {
         int var3 = var2 * var0.field7005;

         for (int var4 = var0.field7005 - 1; var4 > 0; var4--) {
            if (var0.field7003[var4 + var3] == 0 && var0.field7003[var4 + var3 - 1 - var0.field7005] != 0) {
               var0.field7003[var4 + var3] = var1;
            }
         }
      }
   }

   classAAU() throws Throwable {
      throw new Error();
   }
}
