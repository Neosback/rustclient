import java.util.HashMap;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xl")
public class classXL {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Lxz;I)Lxz;")
   @ObfuscatedName("ab")
   public static classXZ method12901(classXZ[] var0, int var1) {
      classXZ[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classXZ var4 = var2[var3];
         if (var1 == var4.vmethod8((byte)1)) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxh;)V")
   @ObfuscatedName("fd")
   public static void method12900(classXH var0) {
      if (var0 == null) {
         var0.method12825();
      } else {
         var0.field6867 = new classXG[0];
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnc;)I")
   @ObfuscatedName("gn")
   public static int method12904(classNC var0) {
      return var0.field4655.length;
   }

   static {
      new HashMap();
   }

   classXL() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Lxz;I)Lxz;")
   @ObfuscatedName("ae")
   public static classXZ method12902(classXZ[] var0, int var1) {
      classXZ[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classXZ var4 = var2[var3];
         if (var1 == var4.vmethod8((byte)1)) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)V")
   @ObfuscatedName("sb")
   public static void method12905(classQR var0) {
      if (var0 == null) {
         var0.method9794();
      } else {
         var0.field5600 = 0;
         var0.field5602 = 0;
         var0.field5599 = 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([Lxz;I)Lxz;")
   @ObfuscatedName("af")
   public static classXZ method12903(classXZ[] var0, int var1) {
      classXZ[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         classXZ var4 = var2[var3];
         if (var1 == var4.vmethod8((byte)1)) {
            return var4;
         }
      }

      return null;
   }
}
