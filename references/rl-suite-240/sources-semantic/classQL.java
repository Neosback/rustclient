import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.bouncycastle.crypto.tls.TlsClientProtocol;

@ObfuscatedName("ql")
public class classQL extends SSLSocketFactory {
   @ObfuscatedSignature(descriptor = "Lql;")
   @ObfuscatedName("az")
   static classQL field5557;
   @ObfuscatedName("af")
   SecureRandom field5556 = new SecureRandom();

   @Override
   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      try {
         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ql.createSocket(" + ')');
      }
   }

   @Override
   public String[] getSupportedCipherSuites() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ql.getSupportedCipherSuites(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lql;")
   @ObfuscatedName("ae")
   public static classQL method9656() {
      if (field5557 == null) {
         field5557 = new classQL();
      }

      return field5557;
   }

   @Override
   public String[] getDefaultCipherSuites() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ql.getDefaultCipherSuites(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;")
   @ObfuscatedName("as")
   public Socket method9658(Socket var1, String var2, int var3, boolean var4) throws IOException {
      if (null == var1) {
         var1 = new Socket();
      }

      if (!var1.isConnected()) {
         var1.connect(new InetSocketAddress(var2, var3));
      }

      TlsClientProtocol var5 = new TlsClientProtocol(var1.getInputStream(), var1.getOutputStream(), this.field5556);
      return this.method9675(var2, var5, -939263023);
   }

   @Override
   public Socket createSocket(String var1, int var2) throws IOException, UnknownHostException {
      try {
         return null;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ql.createSocket(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)Ljava/net/Socket;")
   @ObfuscatedName("ad")
   public Socket method9669(String var1, int var2) throws IOException, UnknownHostException {
      return null;
   }

   @Override
   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      try {
         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ql.createSocket(" + ')');
      }
   }

   @Override
   public Socket createSocket(Socket var1, String var2, int var3, boolean var4) throws IOException {
      try {
         if (null == var1) {
            var1 = new Socket();
         }

         if (!var1.isConnected()) {
            var1.connect(new InetSocketAddress(var2, var3));
         }

         TlsClientProtocol var5 = new TlsClientProtocol(var1.getInputStream(), var1.getOutputStream(), this.field5556);
         return this.method9675(var2, var5, -1631893111);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "ql.createSocket(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lorg/bouncycastle/crypto/tls/TlsClientProtocol;I)Ljavax/net/ssl/SSLSocket;")
   @ObfuscatedName("af")
   SSLSocket method9675(String var1, TlsClientProtocol var2, int var3) {
      try {
         return new classQC(this, var2, var1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ql.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/net/InetAddress;I)Ljava/net/Socket;")
   @ObfuscatedName("ai")
   public Socket method9672(String var1, int var2, InetAddress var3, int var4) throws IOException, UnknownHostException {
      return null;
   }

   @Override
   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      try {
         return null;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ql.createSocket(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;")
   @ObfuscatedName("ag")
   public Socket method9659(Socket var1, String var2, int var3, boolean var4) throws IOException {
      if (null == var1) {
         var1 = new Socket();
      }

      if (!var1.isConnected()) {
         var1.connect(new InetSocketAddress(var2, var3));
      }

      TlsClientProtocol var5 = new TlsClientProtocol(var1.getInputStream(), var1.getOutputStream(), this.field5556);
      return this.method9675(var2, var5, -1237978517);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/Socket;Ljava/lang/String;IZ)Ljava/net/Socket;")
   @ObfuscatedName("ab")
   public Socket method9660(Socket var1, String var2, int var3, boolean var4) throws IOException {
      if (null == var1) {
         var1 = new Socket();
      }

      if (!var1.isConnected()) {
         var1.connect(new InetSocketAddress(var2, var3));
      }

      TlsClientProtocol var5 = new TlsClientProtocol(var1.getInputStream(), var1.getOutputStream(), this.field5556);
      return this.method9675(var2, var5, -329328132);
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("ax")
   public String[] method9661() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("ac")
   public String[] method9662() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("aa")
   public String[] method9663() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String[] method9664() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("al")
   public String[] method9665() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String[] method9666() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("ay")
   public String[] method9667() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("aq")
   public String[] method9668() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)Lql;")
   @ObfuscatedName("az")
   public static classQL method9657(int var0) {
      try {
         if (field5557 == null) {
            if (var0 <= -640788119) {
               throw new IllegalStateException();
            }

            field5557 = new classQL();
         }

         return field5557;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ql.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/InetAddress;I)Ljava/net/Socket;")
   @ObfuscatedName("au")
   public Socket method9670(InetAddress var1, int var2) throws IOException {
      return null;
   }

   classQL() {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/InetAddress;I)Ljava/net/Socket;")
   @ObfuscatedName("ap")
   public Socket method9671(InetAddress var1, int var2) throws IOException {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/InetAddress;ILjava/net/InetAddress;I)Ljava/net/Socket;")
   @ObfuscatedName("ar")
   public Socket method9673(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/net/InetAddress;ILjava/net/InetAddress;I)Ljava/net/Socket;")
   @ObfuscatedName("aw")
   public Socket method9674(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lorg/bouncycastle/crypto/tls/TlsClientProtocol;)Ljavax/net/ssl/SSLSocket;")
   @ObfuscatedName("ak")
   SSLSocket method9676(String var1, TlsClientProtocol var2) {
      return new classQC(this, var2, var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lorg/bouncycastle/crypto/tls/TlsClientProtocol;)Ljavax/net/ssl/SSLSocket;")
   @ObfuscatedName("av")
   SSLSocket method9677(String var1, TlsClientProtocol var2) {
      return new classQC(this, var2, var1);
   }
}
