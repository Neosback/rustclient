import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qg")
public class classQG {
   @ToRemove(unused = "true")
   @ObfuscatedName("ek")
   static final int field5535 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   public static final int field5534 = 524288;
   @ObfuscatedName("ae")
   final Map field5532;
   @ObfuscatedName("af")
   final String field5533;
   @ObfuscatedName("az")
   final int field5531;
   @ObfuscatedName("ab")
   final String field5530;

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ax")
   public String method9567() {
      return this.field5533;
   }

   @ObfuscatedSignature(descriptor = "(Lqg;I)Ljava/lang/String;")
   @ObfuscatedName("ri")
   public static String method9558(classQG var0, int var1) {
      if (var0 == null) {
         return var0.method9563(var1);
      } else {
         try {
            return var0.field5530;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "qg.af(" + ')');
         }
      }
   }

   classQG(HttpURLConnection var1) throws IOException {
      this.field5531 = var1.getResponseCode() * -91618159;
      this.field5530 = var1.getResponseMessage();
      this.field5532 = var1.getHeaderFields();
      StringBuilder var2 = new StringBuilder();
      InputStream var3 = -1998439311 * this.field5531 >= 300 ? var1.getErrorStream() : var1.getInputStream();
      if (var3 != null) {
         InputStreamReader var4 = new InputStreamReader(var3);
         BufferedReader var5 = new BufferedReader(var4);

         String var6;
         while ((var6 = var5.readLine()) != null) {
            var2.append(var6);
         }

         var3.close();
      }

      this.field5533 = var2.toString();
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/util/Map;")
   @ObfuscatedName("ae")
   public Map method9564(byte var1) {
      try {
         return this.field5532;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qg.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqg;)Ljava/lang/String;")
   @ObfuscatedName("dy")
   public static String method9568(classQG var0) {
      return var0.field5533;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("af")
   public String method9569(int var1) {
      try {
         return this.field5533;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qg.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aq")
   public String method9559() {
      return this.field5530;
   }

   classQG(String var1) {
      this.field5531 = 2007442064;
      this.field5530 = var1;
      this.field5532 = null;
      this.field5533 = "";
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ac")
   public String method9560() {
      return this.field5530;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("as")
   public String method9561() {
      return this.field5530;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("aj")
   public Map method9565() {
      return this.field5532;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Map;")
   @ObfuscatedName("al")
   public Map method9566() {
      return this.field5532;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aa")
   public String method9562() {
      return this.field5530;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("hm")
   static void method9570(int var0) {
      try {
         if (null != classMC.field4479) {
            if (var0 != -419739223) {
               throw new IllegalStateException();
            }

            client.field882 = client.field856 * -493787601;
            classMC.field4479.method14266(339443845);
            Iterator var1 = classIS.field3051.field1692.iterator();

            while (var1.hasNext()) {
               if (var0 != -419739223) {
                  throw new IllegalStateException();
               }

               Player var2 = (Player)var1.next();
               classMC.field4479
                  .method14262(
                     (var2.field1484 * -1547553299 >> 7) + -1444178379 * classIS.field3051.field1706,
                     (-1272026483 * var2.field1486 >> 7) + -351145363 * classIS.field3051.field1707,
                     (byte)-106
                  );
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qg.hm(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   public int method9557(int var1) {
      try {
         return -1998439311 * this.field5531;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qg.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ab")
   public String method9563(int var1) {
      try {
         return this.field5530;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qg.af(" + ')');
      }
   }
}
