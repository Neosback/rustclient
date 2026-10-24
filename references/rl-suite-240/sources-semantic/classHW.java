import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hw")
public class classHW {
   @ToRemove(unused = "true")
   @ObfuscatedName("ba")
   static final int field2902 = 58;
   @ObfuscatedName("af")
   final int field2900;
   @ToRemove(unused = "true")
   @ObfuscatedName("bq")
   static final int field2904 = 91;
   @ObfuscatedSignature(descriptor = "Lhp;")
   @ObfuscatedName("ab")
   final classHP field2899;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field2901 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("bm")
   static final int field2903 = 1076101624;
   @ObfuscatedName("az")
   final String field2898;
   @ObfuscatedName("ae")
   final int field2897;

   classHW(String var1, int var2, int var3, classHP var4) {
      this.field2898 = var1;
      this.field2900 = -1801906281 * var2;
      this.field2897 = var3 * -615172887;
      this.field2899 = var4;
   }

   @ObfuscatedSignature(descriptor = "(II)Lof;")
   @ObfuscatedName("az")
   public static classOF method6163(int var0, int var1) {
      try {
         classOF var2 = (classOF)NodeCache.method6460(classOF.field4850, var0);
         if (null != var2) {
            if (var1 >= -392503493) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else if (classLP.field4194 == null) {
            if (var1 >= -392503493) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            byte[] var3 = classLP.field4194.method11942(3, var0, -1241700387);
            var2 = new classOF(new classXY(var3));
            classOF.field4850.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "hw.az(" + ')');
      }
   }
}
