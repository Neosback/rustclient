import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gm")
public class classGM {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field2620 = 1;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Lgy;II)V")
   @ObfuscatedName("ab")
   public static void method5529(classXY var0, classGY var1, int var2, int var3) {
      var1.method5957(classKY.method6649(var2, 254158521), classKY.method6649(var3, 1629084526), (byte)5);
      var1.method5961(0, (short)255);
      var1.method5950(0, 598729790);
      byte var4 = var0.method13097((byte)17);
      if (var4 != 0) {
         int var5 = classQK.method9651(var0, var4, 0, (byte)75);
         int var6 = classQK.method9651(var0, var4, 2, (byte)14);
         int var7 = classQK.method9651(var0, var4, 4, (byte)22);
         int var8 = classQK.method9651(var0, var4, 6, (byte)84);
         var1.method5967(var5, var6, var7, var8, -1896731343);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;)V")
   @ObfuscatedName("pn")
   public static void method5536(NPC var0) {
      if (var0 == null) {
         var0.getId();
      }

      var0.field1580 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;BI)I")
   @ObfuscatedName("as")
   public static int method5532(classXY var0, byte var1, int var2) {
      int var3 = var1 >> var2 & 3;
      if (var3 == 3) {
         return classXY.method13113(var0, (byte)1);
      } else if (2 == var3) {
         return var0.method13104((byte)16);
      } else {
         return 1 == var3 ? var0.method13097((byte)17) : 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llc;[I[I[III)V")
   @ObfuscatedName("fg")
   public static void method5535(PlayerComposition var0, int[] var1, int[] var2, int[] var3, int var4, int var5) {
      if (var0 == null) {
         var0.method6769(var4, var4);
      }

      if (null == var1) {
         var1 = PlayerComposition.method6727(var0, var4, -613532315);
      }

      if (var2 == null) {
         var2 = PlayerComposition.method6727(var0, var4, -613532315);
      }

      var0.field4090 = var1;
      var0.field4093 = var2;
      var0.field4096 = var3;
      var0.field4092 = var4 * 537316249;
      var0.field4097 = -739655249 * var5;
      PlayerComposition.method6742(var0, -2104720347);
   }

   classGM() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;BI)I")
   @ObfuscatedName("ag")
   public static int method5533(classXY var0, byte var1, int var2) {
      int var3 = var1 >> var2 & 3;
      if (var3 == 3) {
         return classXY.method13113(var0, (byte)1);
      } else if (2 == var3) {
         return var0.method13104((byte)16);
      } else {
         return 1 == var3 ? var0.method13097((byte)17) : 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;Lgy;II)V")
   @ObfuscatedName("ae")
   public static void method5530(classXY var0, classGY var1, int var2, int var3) {
      var1.method5957(classKY.method6649(var2, 427595868), classKY.method6649(var3, 347163338), (byte)5);
      var1.method5961(0, (short)255);
      var1.method5950(0, -868268101);
      byte var4 = var0.method13097((byte)17);
      if (var4 != 0) {
         int var5 = classQK.method9651(var0, var4, 0, (byte)9);
         int var6 = classQK.method9651(var0, var4, 2, (byte)41);
         int var7 = classQK.method9651(var0, var4, 4, (byte)47);
         int var8 = classQK.method9651(var0, var4, 6, (byte)17);
         var1.method5967(var5, var6, var7, var8, 2006765241);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;)V")
   @ObfuscatedName("ny")
   public static void method5531(classLE var0) {
      if (var0 == null) {
         var0.method6822();
      }

      var0.field4112 = classDD.method2988(16711680) * 4380477302914991203L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;BI)I")
   @ObfuscatedName("ax")
   public static int method5534(classXY var0, byte var1, int var2) {
      int var3 = var1 >> var2 & 3;
      if (var3 == 3) {
         return classXY.method13113(var0, (byte)1);
      } else if (2 == var3) {
         return var0.method13104((byte)16);
      } else {
         return 1 == var3 ? var0.method13097((byte)17) : 0;
      }
   }
}
