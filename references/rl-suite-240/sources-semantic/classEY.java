import java.net.URL;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ey")
public class classEY {
   @ObfuscatedName("ar")
   public static int field1981;
   @ObfuscatedName("af")
   static int field1978 = -261056662;
   @ObfuscatedName("ae")
   final URL field1979;
   @ObfuscatedName("ab")
   volatile int field1980;
   @ObfuscatedName("ag")
   volatile byte[] field1985;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   public static final int field1983 = 64;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field1982 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("cx")
   public static final int field1984 = 97;
   @ObfuscatedName("az")
   static int field1977 = 208499601;

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("az")
   public boolean method4136(int var1) {
      try {
         boolean var10000;
         if (1182504533 * this.field1980 != field1977 * -430419313) {
            if (var1 <= 1308041153) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ey.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[B")
   @ObfuscatedName("af")
   public byte[] method4141(int var1) {
      try {
         return this.field1985;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ey.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("aa")
   public byte[] method4142() {
      return this.field1985;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   public boolean method4137() {
      return 1182504533 * this.field1980 != field1977 * -430419313;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   public boolean method4138() {
      return 1182504533 * this.field1980 != field1977 * -430419313;
   }

   classEY(URL var1) {
      this.field1980 = field1977 * 1269776723;
      this.field1979 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   public boolean method4139() {
      return 1182504533 * this.field1980 != field1977 * 970768241;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aq")
   public String method4143() {
      return this.field1979.toString();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ay")
   public String method4144() {
      return this.field1979.toString();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String method4145() {
      return this.field1979.toString();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("al")
   public String method4146() {
      return this.field1979.toString();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String method4147() {
      return this.field1979.toString();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   public boolean method4140() {
      return 1182504533 * this.field1980 != field1977 * -430419313;
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("ae")
   public String method4148(byte var1) {
      try {
         return this.field1979.toString();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ey.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("er")
   static void method4149(byte var0) {
      try {
         AABB.method4937(-1905538131);
         client.field975.field6801[0] = 0;
         client.field975.field6802[0] = classKK.field3852;
         client.field975.field6803[0] = "";
         client.field975.field6798[0] = 1006;
         client.field975.field6805[0] = false;
         client.field975.field6804[0] = null;
         client.field975.field6795 = 1932681381;
         client.field975.method12757(-1);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ey.er(" + ')');
      }
   }
}
