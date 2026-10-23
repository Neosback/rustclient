import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wf")
public class classWF implements classWG {
   @ObfuscatedName("az")
   final Map field6675;

   @ObfuscatedSignature(descriptor = "()Lwy;")
   @ObfuscatedName("ae")
   @Override
   public classWY vmethod633() {
      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbi;)Z")
   @ObfuscatedName("ye")
   public static boolean method12431(GraphicsObject var0) {
      if (var0 == null) {
         var0.method1097();
      }

      return !var0.field464.method9772(374233424);
   }

   @ObfuscatedSignature(descriptor = "(B)Lwy;")
   @ObfuscatedName("az")
   @Override
   public classWY vmethod631(byte var1) {
      try {
         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wf.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[B")
   @ObfuscatedName("af")
   @Override
   public byte[] vmethod637(int var1) throws UnsupportedEncodingException {
      try {
         return this.method12427((byte)1).getBytes("UTF-8");
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wf.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwf;)Ljava/lang/String;")
   @ObfuscatedName("fv")
   public static String method12426(classWF var0) throws UnsupportedEncodingException {
      if (var0 == null) {
         var0.method12430();
      }

      StringBuilder var1 = new StringBuilder();

      for (Entry var3 : var0.field6675.entrySet()) {
         String var4 = URLEncoder.encode((String)var3.getKey(), "UTF-8");
         String var5 = URLEncoder.encode((String)var3.getValue(), "UTF-8");
         var1.append(var4).append("=").append(var5).append("&");
      }

      if (var1.length() == 0) {
         return "";
      } else {
         var1.deleteCharAt(var1.length() - 1);
         var1.insert(0, "?");
         return var1.toString();
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("aa")
   public String method12427(byte var1) throws UnsupportedEncodingException {
      try {
         StringBuilder var2 = new StringBuilder();
         Iterator var3 = this.field6675.entrySet().iterator();

         while (var3.hasNext()) {
            if (var1 != 1) {
               throw new IllegalStateException();
            }

            Entry var4 = (Entry)var3.next();
            String var5 = URLEncoder.encode((String)var4.getKey(), "UTF-8");
            String var6 = URLEncoder.encode((String)var4.getValue(), "UTF-8");
            var2.append(var5).append("=").append(var6).append("&");
         }

         if (var2.length() != 0) {
            var2.deleteCharAt(var2.length() - 1);
            var2.insert(0, "?");
            return var2.toString();
         } else if (var1 != 1) {
            throw new IllegalStateException();
         } else {
            return "";
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "wf.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lwy;")
   @ObfuscatedName("ab")
   @Override
   public classWY vmethod632() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ac")
   @Override
   public byte[] vmethod638() throws UnsupportedEncodingException {
      return this.method12427((byte)1).getBytes("UTF-8");
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ag")
   @Override
   public byte[] vmethod634() throws UnsupportedEncodingException {
      return this.method12427((byte)1).getBytes("UTF-8");
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("as")
   @Override
   public byte[] vmethod635() throws UnsupportedEncodingException {
      return this.method12427((byte)1).getBytes("UTF-8");
   }

   public classWF(Map var1) {
      this.field6675 = var1;
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ax")
   @Override
   public byte[] vmethod636() throws UnsupportedEncodingException {
      return this.method12427((byte)1).getBytes("UTF-8");
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String method12428() throws UnsupportedEncodingException {
      StringBuilder var1 = new StringBuilder();

      for (Entry var3 : this.field6675.entrySet()) {
         String var4 = URLEncoder.encode((String)var3.getKey(), "UTF-8");
         String var5 = URLEncoder.encode((String)var3.getValue(), "UTF-8");
         var1.append(var4).append("=").append(var5).append("&");
      }

      if (var1.length() == 0) {
         return "";
      } else {
         var1.deleteCharAt(var1.length() - 1);
         var1.insert(0, "?");
         return var1.toString();
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("al")
   public String method12429() throws UnsupportedEncodingException {
      StringBuilder var1 = new StringBuilder();

      for (Entry var3 : this.field6675.entrySet()) {
         String var4 = URLEncoder.encode((String)var3.getKey(), "UTF-8");
         String var5 = URLEncoder.encode((String)var3.getValue(), "UTF-8");
         var1.append(var4).append("=").append(var5).append("&");
      }

      if (var1.length() == 0) {
         return "";
      } else {
         var1.deleteCharAt(var1.length() - 1);
         var1.insert(0, "?");
         return var1.toString();
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String method12430() {
      StringBuilder var1 = new StringBuilder();

      for (Entry var3 : this.field6675.entrySet()) {
         String var4 = URLEncoder.encode((String)var3.getKey(), "UTF-8");
         String var5 = URLEncoder.encode((String)var3.getValue(), "UTF-8");
         var1.append(var4).append("=").append(var5).append("&");
      }

      if (var1.length() == 0) {
         return "";
      } else {
         var1.deleteCharAt(var1.length() - 1);
         var1.insert(0, "?");
         return var1.toString();
      }
   }
}
