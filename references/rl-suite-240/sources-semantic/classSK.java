import java.io.IOException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sk")
public class classSK implements classXZ {
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("ag")
   static final classSK field5970 = new classSK(4, 4, null, 1);
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("af")
   static final classSK field5971 = new classSK(1, 1, null, 9);
   @ObfuscatedName("ac")
   final int field5964;
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("ab")
   static final classSK field5969 = new classSK(3, 3, null, 6);
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("az")
   static final classSK field5966 = new classSK(0, 0, null, 0);
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("as")
   static final classSK field5967 = new classSK(5, 5, null, 3);
   @ObfuscatedName("ax")
   final int field5965;
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("ae")
   static final classSK field5968 = new classSK(2, 2, null, 3);
   @ObfuscatedName("aa")
   final int field5963;

   classSK(int var1, int var2, String var3, int var4) {
      this.field5965 = var1 * 987462429;
      this.field5964 = 896897993 * var2;
      this.field5963 = var4 * -77781789;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laar;)J")
   @ObfuscatedName("qa")
   public static long method10512(classAAR var0) throws IOException {
      return var0.field98.length();
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   int method10507(int var1) {
      try {
         return -78036789 * this.field5963;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sk.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field5964 * 1427830393;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sk.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field5964 * 1427830393;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field5964 * 1427830393;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lsk;")
   @ObfuscatedName("ax")
   static classSK[] method10504() {
      return new classSK[]{field5966, field5971, field5968, field5969, field5970, field5967};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lsk;")
   @ObfuscatedName("ac")
   static classSK[] method10505() {
      return new classSK[]{field5966, field5971, field5968, field5969, field5970, field5967};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int method10508() {
      return -78036789 * this.field5963;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   int method10509() {
      return -78036789 * this.field5963;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aj")
   int method10510() {
      return -78036789 * this.field5963;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsk;)I")
   @ObfuscatedName("ps")
   public static int method10511(classSK var0) {
      return -78036789 * var0.field5963;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field5964 * 1427830393;
   }

   @ObfuscatedSignature(descriptor = "(II)Lbl;")
   @ObfuscatedName("az")
   static Script method10506(int var0, int var1) {
      try {
         Script var2 = (Script)NodeCache.method6460(Script.field491, var0);
         if (null != var2) {
            if (var1 == 57435997) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classWN.field6757.method11942(var0, 0, -1379686198);
            if (var3 == null) {
               if (var1 == 57435997) {
                  throw new IllegalStateException();
               } else {
                  return null;
               }
            } else {
               var2 = Widget.method7204(var3, -2042870765);
               Script.field491.method6464(var2, var0);
               return var2;
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "sk.az(" + ')');
      }
   }
}
