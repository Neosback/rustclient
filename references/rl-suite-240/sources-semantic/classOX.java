import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ox")
public class classOX {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ak")
   public static IndexDataBase field5190;
   @ObfuscatedName("af")
   static char[] field5187;
   @ObfuscatedName("ae")
   static char[] field5186;
   @ObfuscatedName("ab")
   static int[] field5185;
   @ObfuscatedName("az")
   public static char[] field5184 = new char[64];
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field5188 = 27;
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   static final int field5189 = 52;

   static {
      for (int var0 = 0; var0 < 26; var0++) {
         field5184[var0] = (char)(65 + var0);
      }

      for (int var2 = 26; var2 < 52; var2++) {
         field5184[var2] = (char)(97 + var2 - 26);
      }

      for (int var3 = 52; var3 < 62; var3++) {
         field5184[var3] = (char)(var3 + 48 - 52);
      }

      field5184[62] = '+';
      field5184[63] = '/';
      field5187 = new char[64];

      for (int var4 = 0; var4 < 26; var4++) {
         field5187[var4] = (char)(var4 + 65);
      }

      for (int var5 = 26; var5 < 52; var5++) {
         field5187[var5] = (char)(97 + var5 - 26);
      }

      for (int var6 = 52; var6 < 62; var6++) {
         field5187[var6] = (char)(48 + var6 - 52);
      }

      field5187[62] = '*';
      field5187[63] = '-';
      field5186 = new char[64];

      for (int var7 = 0; var7 < 26; var7++) {
         field5186[var7] = (char)(var7 + 65);
      }

      for (int var8 = 26; var8 < 52; var8++) {
         field5186[var8] = (char)(97 + var8 - 26);
      }

      for (int var9 = 52; var9 < 62; var9++) {
         field5186[var9] = (char)(48 + var9 - 52);
      }

      field5186[62] = '-';
      field5186[63] = '_';
      field5185 = new int[128];

      for (int var10 = 0; var10 < field5185.length; var10++) {
         field5185[var10] = -1;
      }

      for (int var11 = 65; var11 <= 90; var11++) {
         field5185[var11] = var11 - 65;
      }

      for (int var12 = 97; var12 <= 122; var12++) {
         field5185[var12] = 26 + (var12 - 97);
      }

      for (int var13 = 48; var13 <= 57; var13++) {
         field5185[var13] = 52 + (var13 - 48);
      }

      int[] var14 = field5185;
      field5185[43] = 62;
      var14[42] = 62;
      int[] var1 = field5185;
      field5185[47] = 63;
      var1[45] = 63;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsp;)Lkr;")
   @ObfuscatedName("ox")
   public static classKR method8853(WorldEntity var0) {
      return var0.field6019;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;III)V")
   @ObfuscatedName("xr")
   public static void method8854(Scene var0, int var1, int var2, int var3) {
      if (var0 == null) {
         var0.method4239(var1, var1, var1);
      }

      Tile var4 = var0.field2056[var1][var2][var3];
      if (var4 != null) {
         for (int var5 = 0; var5 < var4.field1961 * -1176003376; var5++) {
            GameObject var6 = var4.field1949[var5];
            long var8 = var6.field2183 * 1745853678706228013L;
            boolean var7 = classCV.method2679(var8) == 2;
            if (var7 && var6.field2186 * -1928190230 == var2 && var6.field2192 * -555977777 == var3) {
               var0.method4475(var6);
               return;
            }
         }
      }
   }

   classOX() throws Throwable {
      throw new Error();
   }
}
