import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gq")
public class classGQ {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2651 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   public static final int field2652 = 33;
   @ObfuscatedSignature(descriptor = "Lyz;")
   @ObfuscatedName("cv")
   static IndexedSprite field2653;
   @ObfuscatedName("az")
   static final Map field2650 = new HashMap();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public static void method5629() {
      field2650.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ab")
   public static void method5626(String var0, String var1) {
      field2650.put(var0.toLowerCase(), var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ag")
   public static void method5627(String var0, String var1) {
      field2650.put(var0.toLowerCase(), var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("as")
   public static void method5628(String var0, String var1) {
      field2650.put(var0.toLowerCase(), var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method5630() {
      field2650.clear();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("al")
   public static String method5631(String var0) {
      return (String)field2650.get(var0.toLowerCase());
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("aa")
   public static String method5632(String var0) {
      return (String)field2650.get(var0.toLowerCase());
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("ao")
   public static String method5633(String var0) {
      return (String)field2650.get(var0.toLowerCase());
   }

   @ObfuscatedSignature(descriptor = "(Lyn;III)I")
   @ObfuscatedName("af")
   static int method5635(IterableHashTable var0, int var1, int var2, int var3) {
      try {
         if (null == var0) {
            if (var3 >= 1334493976) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            IntegerNode var4 = (IntegerNode)var0.method13637(var1);
            if (null == var4) {
               if (var3 >= 1334493976) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               return var4.field6501;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "gq.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("aj")
   public static String method5634(String var0) {
      return (String)field2650.get(var0.toLowerCase());
   }

   @ObfuscatedSignature(descriptor = "(IB)Ljava/lang/String;")
   @ObfuscatedName("ag")
   static String method5636(int var0, byte var1) {
      try {
         if (var0 >= 0) {
            if (var0 < 364868) {
               long var2 = (11745L + var0) * 86400000L;
               Calendar var4 = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
               var4.setTime(new Date(var2));
               int var5 = var4.get(5);
               int var6 = var4.get(2);
               int var7 = var4.get(1);
               return var5 + "-" + classRZ.field5882[var6] + "-" + var7;
            }

            if (var1 != 19) {
               throw new IllegalStateException();
            }
         }

         return "-";
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "gq.ag(" + ')');
      }
   }

   classGQ() throws Throwable {
      throw new Error();
   }
}
