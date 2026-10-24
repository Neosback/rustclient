import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hz")
public class classHZ {
   @ObfuscatedName("az")
   final int field2914;
   @ObfuscatedName("af")
   final int field2912;
   @ObfuscatedName("ae")
   final int field2913;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field2915 = 32;
   @ToRemove(unused = "true")
   @ObfuscatedName("be")
   static final int field2916 = 53;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzr;)I")
   @ObfuscatedName("go")
   public static int method6172(ScriptEvent var0) {
      return var0.field7245 * -1357185485;
   }

   @ObfuscatedSignature(descriptor = "(CB)C")
   @ObfuscatedName("af")
   static char method6173(char var0, byte var1) {
      try {
         switch (var0) {
            case ' ':
            case '-':
            case '_':
            case ' ':
               return '_';
            case '#':
            case '[':
            case ']':
               return var0;
            case 'À':
            case 'Á':
            case 'Â':
            case 'Ã':
            case 'Ä':
            case 'à':
            case 'á':
            case 'â':
            case 'ã':
            case 'ä':
               return 'a';
            case 'Ç':
            case 'ç':
               return 'c';
            case 'È':
            case 'É':
            case 'Ê':
            case 'Ë':
            case 'è':
            case 'é':
            case 'ê':
            case 'ë':
               return 'e';
            case 'Í':
            case 'Î':
            case 'Ï':
            case 'í':
            case 'î':
            case 'ï':
               return 'i';
            case 'Ñ':
            case 'ñ':
               return 'n';
            case 'Ò':
            case 'Ó':
            case 'Ô':
            case 'Õ':
            case 'Ö':
            case 'ò':
            case 'ó':
            case 'ô':
            case 'õ':
            case 'ö':
               return 'o';
            case 'Ù':
            case 'Ú':
            case 'Û':
            case 'Ü':
            case 'ù':
            case 'ú':
            case 'û':
            case 'ü':
               return 'u';
            case 'ß':
               return 'b';
            case 'ÿ':
            case 'Ÿ':
               return 'y';
            default:
               return Character.toLowerCase(var0);
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hz.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luz;")
   @ObfuscatedName("az")
   public static classUZ method6171(int var0) {
      try {
         synchronized (classUZ.field6428) {
            if (-1124722655 * classUZ.field6433 == 0) {
               if (var0 != 16777216) {
                  throw new IllegalStateException();
               } else {
                  return new classUZ();
               }
            } else {
               classUZ.field6428[(classUZ.field6433 -= 801074145) * -1124722655].method11809(-1737493511);
               return classUZ.field6428[-1124722655 * classUZ.field6433];
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "hz.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)I")
   @ObfuscatedName("zs")
   public static int method6174(NPCComposition var0) {
      return var0 == null ? var0.method9051() : var0.field5330 * -128106731;
   }

   classHZ(int var1, int var2, int var3) {
      this.field2914 = var1 * 873874881;
      this.field2912 = var2 * 176806705;
      this.field2913 = -266101677 * var3;
   }
}
