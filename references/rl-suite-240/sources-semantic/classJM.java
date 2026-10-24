import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("jm")
@classZY
public final class classJM {
   @ObfuscatedName("ae")
   static final HashMap field3342 = new HashMap();
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final long field3345 = 86400000L;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field3344 = 11745;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field3343 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final String field3346 = "n";

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/util/TimeZone;")
   @ObfuscatedName("ae")
   static TimeZone method6432(String var0) {
      synchronized (field3342) {
         TimeZone var2 = (TimeZone)field3342.get(var0);
         if (var2 == null) {
            var2 = TimeZone.getTimeZone(var0);
            field3342.put(var0, var2);
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/util/TimeZone;")
   @ObfuscatedName("az")
   static TimeZone method6433(String var0, int var1) {
      try {
         synchronized (field3342) {
            TimeZone var3 = (TimeZone)field3342.get(var0);
            if (var3 == null) {
               if (var1 <= -601431579) {
                  throw new IllegalStateException();
               }

               var3 = TimeZone.getTimeZone(var0);
               field3342.put(var0, var3);
            }

            return var3;
         }
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "jm.az(" + ')');
      }
   }

   static {
      Calendar.getInstance(method6433("Europe/London", 856429648));
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/util/TimeZone;")
   @ObfuscatedName("af")
   static TimeZone method6434(String var0) {
      synchronized (field3342) {
         TimeZone var2 = (TimeZone)field3342.get(var0);
         if (var2 == null) {
            var2 = TimeZone.getTimeZone(var0);
            field3342.put(var0, var2);
         }

         return var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Ljava/lang/String;I)Lte;")
   @ObfuscatedName("cz")
   static classTE method6438(Object var0, String var1, int var2) {
      try {
         if (var0 == null) {
            throw new RuntimeException();
         } else {
            return (classTE)var0;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "jm.cz(" + ')');
      }
   }

   classJM() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laaa;Ljava/lang/String;I)I")
   @ObfuscatedName("bo")
   public static int method6437(AbstractFont var0, String var1, int var2) {
      return var0 == null ? var0.getTextWidth(var1) : AbstractFont.method38(var0, var1, new int[]{var2}, AbstractFont.field21);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/util/TimeZone;")
   @ObfuscatedName("ag")
   static TimeZone method6435(String var0) {
      synchronized (field3342) {
         TimeZone var2 = (TimeZone)field3342.get(var0);
         if (var2 == null) {
            var2 = TimeZone.getTimeZone(var0);
            field3342.put(var0, var2);
         }

         return var2;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/util/TimeZone;")
   @ObfuscatedName("ab")
   static TimeZone method6436(String var0) {
      synchronized (field3342) {
         TimeZone var2 = (TimeZone)field3342.get(var0);
         if (var2 == null) {
            var2 = TimeZone.getTimeZone(var0);
            field3342.put(var0, var2);
         }

         return var2;
      }
   }
}
