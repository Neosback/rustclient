import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zo")
public class classZO {
   @ObfuscatedName("ae")
   int field7229;
   @ObfuscatedName("af")
   int field7230;
   @ObfuscatedName("az")
   int field7231;
   @ObfuscatedName("ab")
   int field7227;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field7228 = 128;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzo;)Ljava/lang/String;")
   @ObfuscatedName("qk")
   public static String method14125(classZO var0) {
      if (var0 == null) {
         return var0.toString();
      } else {
         byte var1 = 10;
         int var2 = 10 - Integer.toString(1929138094 * var0.field7231).length();
         int var3 = 10 - Integer.toString(var0.field7229 * -564568847).length();
         int var4 = 10 - Integer.toString(1147745443 * var0.field7230).length();
         String var5 = "          ".substring(10 - var2);
         String var6 = "          ".substring(10 - var3);
         String var7 = "          ".substring(10 - var4);
         return "    Size: "
            + var0.field7231 * -2068604773
            + var5
            + "Created: "
            + var0.field7230 * 1147745443
            + var7
            + "Total used: "
            + -673493137 * var0.field7229
            + var6
            + "Max-In-Use: "
            + var0.field7227 * 281771763;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method14126() {
      byte var1 = 10;
      int var2 = 10 - Integer.toString(-2068604773 * this.field7231).length();
      int var3 = 10 - Integer.toString(this.field7229 * -564568847).length();
      int var4 = 10 - Integer.toString(1147745443 * this.field7230).length();
      String var5 = "          ".substring(10 - var2);
      String var6 = "          ".substring(10 - var3);
      String var7 = "          ".substring(10 - var4);
      return "    Size: "
         + this.field7231 * -2068604773
         + var5
         + "Created: "
         + this.field7230 * 1147745443
         + var7
         + "Total used: "
         + -564568847 * this.field7229
         + var6
         + "Max-In-Use: "
         + this.field7227 * 281771763;
   }

   @Override
   public String toString() {
      try {
         byte var1 = 10;
         int var2 = 10 - Integer.toString(-2068604773 * this.field7231).length();
         int var3 = 10 - Integer.toString(this.field7229 * -564568847).length();
         int var4 = 10 - Integer.toString(1147745443 * this.field7230).length();
         String var5 = "          ".substring(10 - var2);
         String var6 = "          ".substring(10 - var3);
         String var7 = "          ".substring(10 - var4);
         return "    Size: "
            + this.field7231 * -2068604773
            + var5
            + "Created: "
            + this.field7230 * 1147745443
            + var7
            + "Total used: "
            + -564568847 * this.field7229
            + var6
            + "Max-In-Use: "
            + this.field7227 * 281771763;
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "zo.toString(" + ')');
      }
   }

   classZO() throws Throwable {
      throw new Error();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method14127() {
      byte var1 = 10;
      int var2 = 10 - Integer.toString(1461035955 * this.field7231).length();
      int var3 = 10 - Integer.toString(this.field7229 * -307748291).length();
      int var4 = 10 - Integer.toString(1147745443 * this.field7230).length();
      String var5 = "          ".substring(10 - var2);
      String var6 = "          ".substring(10 - var3);
      String var7 = "          ".substring(10 - var4);
      return "    Size: "
         + this.field7231 * -2068604773
         + var5
         + "Created: "
         + this.field7230 * 1147745443
         + var7
         + "Total used: "
         + -564568847 * this.field7229
         + var6
         + "Max-In-Use: "
         + this.field7227 * 1866999262;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;IIIIIII)V")
   @ObfuscatedName("fu")
   static final void method14128(int var0, String var1, String var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      try {
         if (client.field974) {
            if (var9 == -1739941876) {
               ;
            }
         } else if (!classTK.method10982(var8, var3, (byte)-52)) {
            if (var9 != -1739941876) {
               throw new IllegalStateException();
            }
         } else {
            if (var0 >= 0) {
               if (var9 != -1739941876) {
                  throw new IllegalStateException();
               }

               if (var0 < client.field975.field6804.length) {
                  if (client.field975.field6804[var0] == null) {
                     if (var9 != -1739941876) {
                        throw new IllegalStateException();
                     }

                     client.field975.field6804[var0] = new Menu(false);
                  }

                  client.field975.field6804[var0].method12747(var1, var2, var3, var4, var5, var6, var7, false, var8, -1505694045);
                  return;
               }

               if (var9 != -1739941876) {
                  throw new IllegalStateException();
               }
            }
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "zo.fu(" + ')');
      }
   }
}
