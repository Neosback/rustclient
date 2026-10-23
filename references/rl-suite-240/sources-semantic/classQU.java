import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.bouncycastle.crypto.tls.DefaultTlsClient;
import org.bouncycastle.crypto.tls.TlsAuthentication;

@ObfuscatedName("qu")
class classQU extends DefaultTlsClient {
   @ObfuscatedSignature(descriptor = "()Ljava/util/Hashtable;")
   @ObfuscatedName("az")
   public Hashtable method9860() throws IOException {
      Hashtable var1 = super.getClientExtensions();
      if (var1 == null) {
         var1 = new Hashtable();
      }

      byte[] var2 = this.this$1.val$host.getBytes();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      DataOutputStream var4 = new DataOutputStream(var3);
      var4.writeShort(var2.length + 3);
      var4.writeByte(0);
      var4.writeShort(var2.length);
      var4.write(var2);
      var4.close();
      var1.put(0, var3.toByteArray());
      return var1;
   }

   @Override
   public Hashtable getClientExtensions() throws IOException {
      try {
         Hashtable var1 = super.getClientExtensions();
         if (var1 == null) {
            var1 = new Hashtable();
         }

         byte[] var2 = this.this$1.val$host.getBytes();
         ByteArrayOutputStream var3 = new ByteArrayOutputStream();
         DataOutputStream var4 = new DataOutputStream(var3);
         var4.writeShort(var2.length + 3);
         var4.writeByte(0);
         var4.writeShort(var2.length);
         var4.write(var2);
         var4.close();
         var1.put(0, var3.toByteArray());
         return var1;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "qu.getClientExtensions(" + ')');
      }
   }

   public TlsAuthentication getAuthentication() throws IOException {
      try {
         return new classQT(this);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qu.getAuthentication(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lorg/bouncycastle/crypto/tls/TlsAuthentication;")
   @ObfuscatedName("ab")
   public TlsAuthentication method9863() throws IOException {
      return new classQT(this);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Hashtable;")
   @ObfuscatedName("af")
   public Hashtable method9861() throws IOException {
      Hashtable var1 = super.getClientExtensions();
      if (var1 == null) {
         var1 = new Hashtable();
      }

      byte[] var2 = this.this$1.val$host.getBytes();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      DataOutputStream var4 = new DataOutputStream(var3);
      var4.writeShort(var2.length + 3);
      var4.writeByte(0);
      var4.writeShort(var2.length);
      var4.write(var2);
      var4.close();
      var1.put(0, var3.toByteArray());
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lod;I)I")
   @ObfuscatedName("xu")
   public static int method9859(EnumComposition var0, int var1) {
      if (var0 == null) {
         return var0.method8362(var1, var1);
      } else {
         for (int var2 = 0; var2 < var0.field4821 * -49805; var2++) {
            if (var0.field4822[var2] == var1) {
               return var0.field4826[var2];
            }
         }

         return var0.field4819 * -1138023367;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Hashtable;")
   @ObfuscatedName("ae")
   public Hashtable method9862() throws IOException {
      Hashtable var1 = super.getClientExtensions();
      if (var1 == null) {
         var1 = new Hashtable();
      }

      byte[] var2 = this.this$1.val$host.getBytes();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      DataOutputStream var4 = new DataOutputStream(var3);
      var4.writeShort(var2.length + 3);
      var4.writeByte(0);
      var4.writeShort(var2.length);
      var4.write(var2);
      var4.close();
      var1.put(0, var3.toByteArray());
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Liq;Ljava/lang/String;I)I")
   @ObfuscatedName("ah")
   public static int method9867(ClanSettings var0, String var1, int var2) {
      return var0 == null ? var0.method6341(var1, var2) : var0.method6343(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lorg/bouncycastle/crypto/tls/TlsAuthentication;")
   @ObfuscatedName("as")
   public TlsAuthentication method9864() throws IOException {
      return new classQT(this);
   }

   @ObfuscatedSignature(descriptor = "()Lorg/bouncycastle/crypto/tls/TlsAuthentication;")
   @ObfuscatedName("ag")
   public TlsAuthentication method9865() throws IOException {
      return new classQT(this);
   }

   @ObfuscatedSignature(descriptor = "()Lorg/bouncycastle/crypto/tls/TlsAuthentication;")
   @ObfuscatedName("ax")
   public TlsAuthentication method9866() throws IOException {
      return new classQT(this);
   }

   classQU(classQC var1) {
      this.this$1 = var1;
   }
}
