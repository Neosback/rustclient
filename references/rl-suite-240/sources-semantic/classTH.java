import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("th")
public abstract class classTH implements classUT {
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   static final int field6136 = 25;
   @ObfuscatedName("az")
   protected int field6135;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field6137 = 2048;

   @ObfuscatedSignature(descriptor = "(Lva;III)Lyz;")
   @ObfuscatedName("az")
   static IndexedSprite method10857(IndexDataBase var0, int var1, int var2, int var3) {
      try {
         if (!classHK.method6100(var0, var1, var2, (byte)-42)) {
            return null;
         } else {
            IndexedSprite var5 = new IndexedSprite();
            var5.field7122 = classYL.field6998 * -1073038619;
            var5.field7118 = classYL.field6997 * -1062567517;
            var5.field7120 = classYL.field7000[0];
            var5.field7121 = classYL.field7001[0];
            var5.field7119 = EntityOps.field5369[0];
            var5.field7117 = classGG.field2576[0];
            var5.field7124 = classRB.field5637;
            var5.field7123 = classYL.field7002[0];
            classVZ.method12347((byte)-102);
            return var5;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "th.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luu;Lul;)V")
   @ObfuscatedName("ho")
   public static void method10858(classUU var0, classUL var1) {
      if (var0 == null) {
         var0.method11724();
      }

      float var2 = var1.field6325 * var1.field6325;
      float var3 = var1.field6322 * var1.field6325;
      float var4 = var1.field6323 * var1.field6325;
      float var5 = var1.field6324 * var1.field6325;
      float var6 = var1.field6322 * var1.field6322;
      float var7 = var1.field6323 * var1.field6322;
      float var8 = var1.field6322 * var1.field6324;
      float var9 = var1.field6323 * var1.field6323;
      float var10 = var1.field6323 * var1.field6324;
      float var11 = var1.field6324 * var1.field6324;
      var0.field6383 = var2 + var6 - var11 - var9;
      var0.field6398 = var7 + var5 + var7 + var5;
      var0.field6394 = var8 - var4 - var4 + var8;
      var0.field6392 = var7 + (var7 - var5 - var5);
      var0.field6384 = var2 + var9 - var6 - var11;
      var0.field6396 = var3 + (var10 + var3 + var10);
      var0.field6388 = var4 + (var8 + var4 + var8);
      var0.field6389 = var10 - var3 - var3 + var10;
      var0.field6385 = var2 + var11 - var9 - var6;
   }

   protected classTH(classKX var1, classXO var2, int var3) {
      this.field6135 = -425669175 * var3;
   }
}
