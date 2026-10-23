import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("lp")
public class classLP {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("aw")
   public static IndexDataBase field4194;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4193 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field4192 = 2;

   @ObfuscatedSignature(descriptor = "([BI)[B")
   @ObfuscatedName("ae")
   static byte[] method7186(byte[] var0, int var1) {
      try {
         int var2 = var0.length;
         byte[] var3 = new byte[var2];
         System.arraycopy(var0, 0, var3, 0, var2);
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "lp.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("ae")
   public static int method7181(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (1 == (var5 & 1)) {
         int var6 = var3;
         var3 = var4;
         var4 = var6;
      }

      var2 &= 3;
      if (var2 == 0) {
         return var0;
      } else if (1 == var2) {
         return var1;
      } else {
         return var2 == 2 ? 7 - var0 - (var3 - 1) : 7 - var1 - (var4 - 1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldn;)V")
   @ObfuscatedName("hq")
   public static void method7187(NPC var0) {
      if (var0 == null) {
         var0.method3506();
      }

      var0.field1581 = null;
      var0.field1578 = null;
   }

   classLP() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("ab")
   public static int method7182(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (1 == (var5 & 1)) {
         int var6 = var3;
         var3 = var4;
         var4 = var6;
      }

      var2 &= 3;
      if (var2 == 0) {
         return var0;
      } else if (1 == var2) {
         return var1;
      } else {
         return var2 == 2 ? 7 - var0 - (var3 - 1) : 7 - var1 - (var4 - 1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("ag")
   public static int method7183(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (1 == (var5 & 1)) {
         int var6 = var3;
         var3 = var4;
         var4 = var6;
      }

      var2 &= 3;
      if (var2 == 0) {
         return var0;
      } else if (1 == var2) {
         return var1;
      } else {
         return var2 == 2 ? 7 - var0 - (var3 - 1) : 7 - var1 - (var4 - 1);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lpe;")
   @ObfuscatedName("ag")
   static classPE[] method7185(int var0) {
      try {
         return new classPE[]{classPE.field5226, classPE.field5228, classPE.field5227};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "lp.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIIIII)I")
   @ObfuscatedName("af")
   public static int method7184(int var0, int var1, int var2, int var3, int var4, int var5) {
      if (1 == (var5 & 1)) {
         int var6 = var3;
         var3 = var4;
         var4 = var6;
      }

      var2 &= 3;
      if (var2 == 0) {
         return var0;
      } else if (1 == var2) {
         return var1;
      } else {
         return var2 == 2 ? 7 - var0 - (var3 - 1) : 7 - var1 - (var4 - 1);
      }
   }
}
