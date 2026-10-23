import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("aao")
public class classAAO {
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("as")
   static final classAAO field84 = new classAAO(6, 5, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("af")
   static final classAAO field86 = new classAAO(2, 1, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ae")
   static final classAAO field81 = new classAAO(1, 2, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ac")
   static final classAAO field85 = new classAAO(0, 7, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ab")
   static final classAAO field82 = new classAAO(8, 3, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ag")
   static final classAAO field83 = new classAAO(4, 4, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("aa")
   public static final classAAO field87 = new classAAO(3, -1, "", "", true, new classAAO[]{classAAO.field79, field86, field81, field83, field82});
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("ax")
   static final classAAO field80 = new classAAO(5, 6, "", "");
   @ObfuscatedSignature(descriptor = "Laao;")
   @ObfuscatedName("az")
   public static final classAAO field79 = new classAAO(7, 0, "", "");
   @ObfuscatedName("ao")
   final int field77;
   @ObfuscatedName("al")
   final String field78;

   classAAO(int var1, int var2, String var3, String var4, boolean var5, classAAO[] var6) {
      this.field77 = -2094962513 * var1;
      this.field78 = var4;
   }

   @Override
   public String toString() {
      try {
         return this.field78;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "aao.toString(" + ')');
      }
   }

   classAAO(int var1, int var2, String var3, String var4) {
      this.field77 = var1 * -2094962513;
      this.field78 = var4;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method223() {
      return this.field78;
   }

   @ObfuscatedSignature(descriptor = "(Laaa;Ljava/lang/String;IIIIIIIII)I")
   @ObfuscatedName("yk")
   public static int method225(AbstractFont var0, String var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      if (var0 == null) {
         var0.method61(var1, var2, var2, var2, var2, var2, var2, var2, var2, var2);
      }

      return var0.method63(var1, var2, var3, var4, var5, var6, var7, 256, var8, var9, var10);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method224() {
      return this.field78;
   }
}
