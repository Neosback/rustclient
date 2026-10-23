import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ag")
public class classAG implements classAX {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final String field149 = "SHA-256";
   @ObfuscatedName("af")
   final MessageDigest field148 = this.method354();

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("af")
   static int method340(byte var0) {
      int var1 = 0;
      if (var0 == 0) {
         var1 = 8;
      } else {
         for (int var2 = var0 & 255; (var2 & 128) == 0; var2 <<= 1) {
            var1++;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "([B)I")
   @ObfuscatedName("az")
   static int method334(byte[] var0) {
      int var1 = 0;
      byte[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         byte var4 = var2[var3];
         int var5 = method340(var4);
         var1 += var5;
         if (var5 != 8) {
            break;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;J)Z")
   @ObfuscatedName("ad")
   boolean method345(int var1, String var2, long var3) {
      byte[] var5 = method347(this, var2, var3);
      return method334(var5) >= var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)I")
   @ObfuscatedName("aa")
   static int method335(byte[] var0) {
      int var1 = 0;
      byte[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         byte var4 = var2[var3];
         int var5 = method340(var4);
         var1 += var5;
         if (var5 != 8) {
            break;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/MessageDigest;")
   @ObfuscatedName("ag")
   MessageDigest method354() {
      try {
         return MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)I")
   @ObfuscatedName("as")
   static int method336(byte[] var0) {
      int var1 = 0;
      byte[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         byte var4 = var2[var3];
         int var5 = method340(var4);
         var1 += var5;
         if (var5 != 8) {
            break;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/MessageDigest;")
   @ObfuscatedName("ak")
   MessageDigest method355() {
      try {
         return MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var2) {
         var2.printStackTrace();
         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)I")
   @ObfuscatedName("ac")
   static int method337(byte[] var0) {
      int var1 = 0;
      byte[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         byte var4 = var2[var3];
         int var5 = method340(var4);
         var1 += var5;
         if (var5 != 8) {
            break;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)I")
   @ObfuscatedName("ax")
   static int method338(byte[] var0) {
      int var1 = 0;
      byte[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         byte var4 = var2[var3];
         int var5 = method340(var4);
         var1 += var5;
         if (var5 != 8) {
            break;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)I")
   @ObfuscatedName("ao")
   static int method339(byte[] var0) {
      int var1 = 0;
      byte[] var2 = var0;

      for (int var3 = 0; var3 < var2.length; var3++) {
         byte var4 = var2[var3];
         int var5 = method340(var4);
         var1 += var5;
         if (var5 != 8) {
            break;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;I)Lzs;")
   @ObfuscatedName("mr")
   public static ScriptEventBuilder method356(ScriptEventBuilder var0, int var1) {
      if (var0 == null) {
         var0.method14211(var1);
      }

      var0.field7255 = -1978668005 * var1;
      return var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("al")
   static int method341(byte var0) {
      int var1 = 0;
      if (var0 == 0) {
         var1 = 8;
      } else {
         for (int var2 = var0 & 255; (var2 & 128) == 0; var2 <<= 1) {
            var1++;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aj")
   static int method342(byte var0) {
      int var1 = 0;
      if (var0 == 0) {
         var1 = 8;
      } else {
         for (int var2 = var0 & 255; (var2 & 128) == 0; var2 <<= 1) {
            var1++;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ay")
   static int method343(byte var0) {
      int var1 = 0;
      if (var0 == 0) {
         var1 = 8;
      } else {
         for (int var2 = var0 & 255; (var2 & 128) == 0; var2 <<= 1) {
            var1++;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aq")
   static int method344(byte var0) {
      int var1 = 0;
      if (var0 == 0) {
         var1 = 8;
      } else {
         for (int var2 = var0 & 255; (var2 & 1622426376) == 0; var2 <<= 1) {
            var1++;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lag;Ljava/lang/String;J)[B")
   @ObfuscatedName("fa")
   public static byte[] method347(classAG var0, String var1, long var2) {
      if (var0 == null) {
         var0.method353(var1, var2);
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      var0.field148.reset();

      try {
         var0.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return var0.field148.digest();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;J)[B")
   @ObfuscatedName("ap")
   byte[] method348(String var1, long var2) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      this.field148.reset();

      try {
         this.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return this.field148.digest();
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;J)Z")
   @ObfuscatedName("ae")
   boolean method346(int var1, String var2, long var3) {
      byte[] var5 = method347(this, var2, var3);
      return method334(var5) >= var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;J)[B")
   @ObfuscatedName("au")
   byte[] method349(String var1, long var2) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      this.field148.reset();

      try {
         this.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return this.field148.digest();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;J)[B")
   @ObfuscatedName("ai")
   byte[] method350(String var1, long var2) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      this.field148.reset();

      try {
         this.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return this.field148.digest();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lag;Ljava/lang/String;J)[B")
   @ObfuscatedName("em")
   public static byte[] method351(classAG var0, String var1, long var2) {
      if (var0 == null) {
         var0.method350(var1, var2);
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      var0.field148.reset();

      try {
         var0.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return var0.field148.digest();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;J)[B")
   @ObfuscatedName("ar")
   byte[] method352(String var1, long var2) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      this.field148.reset();

      try {
         this.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return this.field148.digest();
   }

   classAG(classAA var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;J)[B")
   @ObfuscatedName("ab")
   byte[] method353(String var1, long var2) {
      StringBuilder var4 = new StringBuilder();
      var4.append(var1).append(Long.toHexString(var2));
      this.field148.reset();

      try {
         this.field148.update(var4.toString().getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var6) {
         var6.printStackTrace();
      }

      return this.field148.digest();
   }
}
