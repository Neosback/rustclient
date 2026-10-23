import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.Certificate;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.bouncycastle.crypto.tls.TlsClientProtocol;

@ObfuscatedName("qc")
class classQC extends SSLSocket {
   @ObfuscatedName("az")
   Certificate[] field5513;

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("bt")
   public String[] method9511() {
      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)[Ljava/lang/String;")
   @ObfuscatedName("xr")
   public static String[] method9508(classQC var0) {
      return var0 == null ? var0.getEnabledProtocols() : null;
   }

   @Override
   public InputStream getInputStream() throws IOException {
      try {
         return this.val$tlsClientProtocol.getInputStream();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getInputStream(" + ')');
      }
   }

   @Override
   public OutputStream getOutputStream() throws IOException {
      try {
         return this.val$tlsClientProtocol.getOutputStream();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getOutputStream(" + ')');
      }
   }

   @Override
   public synchronized void close() throws IOException {
      try {
         this.val$tlsClientProtocol.close();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.close(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)Z")
   @ObfuscatedName("ge")
   public static boolean method9513(classQC var0) {
      if (var0 == null) {
         var0.method9482();
      }

      return false;
   }

   @Override
   public void addHandshakeCompletedListener(HandshakeCompletedListener var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.addHandshakeCompletedListener(" + ')');
      }
   }

   @Override
   public boolean getEnableSessionCreation() {
      try {
         return false;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getEnableSessionCreation(" + ')');
      }
   }

   @Override
   public void setEnableSessionCreation(boolean var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setEnableSessionCreation(" + ')');
      }
   }

   @Override
   public String[] getEnabledCipherSuites() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getEnabledCipherSuites(" + ')');
      }
   }

   @Override
   public void setEnabledCipherSuites(String[] var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setEnabledCipherSuites(" + ')');
      }
   }

   @Override
   public String[] getEnabledProtocols() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getEnabledProtocols(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)Ljavax/net/ssl/SSLSession;")
   @ObfuscatedName("sa")
   public static SSLSession method9505(classQC var0) {
      if (var0 == null) {
         var0.method9486();
      }

      return new classQZ(var0);
   }

   @Override
   public void setEnabledProtocols(String[] var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setEnabledProtocols(" + ')');
      }
   }

   @Override
   public boolean getNeedClientAuth() {
      try {
         return false;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getNeedClientAuth(" + ')');
      }
   }

   classQC(classQL var1, TlsClientProtocol var2, String var3) {
      this.this$0 = var1;
      this.val$tlsClientProtocol = var2;
      this.val$host = var3;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cu")
   public void method9527() throws IOException {
      this.val$tlsClientProtocol.connect(new classQU(this));
   }

   @Override
   public String[] getSupportedCipherSuites() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getSupportedCipherSuites(" + ')');
      }
   }

   @Override
   public boolean getUseClientMode() {
      try {
         return false;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getUseClientMode(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)V")
   @ObfuscatedName("af")
   public static void method9528(classQC var0) throws IOException {
      if (var0 == null) {
         var0.method9489();
      }

      var0.val$tlsClientProtocol.connect(new classQU(var0));
   }

   @Override
   public void setUseClientMode(boolean var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setUseClientMode(" + ')');
      }
   }

   @Override
   public boolean getWantClientAuth() {
      try {
         return false;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getWantClientAuth(" + ')');
      }
   }

   @Override
   public void removeHandshakeCompletedListener(HandshakeCompletedListener var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.removeHandshakeCompletedListener(" + ')');
      }
   }

   @Override
   public void startHandshake() throws IOException {
      try {
         this.val$tlsClientProtocol.connect(new classQU(this));
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.startHandshake(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/InputStream;")
   @ObfuscatedName("af")
   public InputStream method9482() throws IOException {
      return this.val$tlsClientProtocol.getInputStream();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/OutputStream;")
   @ObfuscatedName("ag")
   public OutputStream method9485() throws IOException {
      return this.val$tlsClientProtocol.getOutputStream();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/OutputStream;")
   @ObfuscatedName("as")
   public OutputStream method9486() throws IOException {
      return this.val$tlsClientProtocol.getOutputStream();
   }

   @Override
   public void setWantClientAuth(boolean var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setWantClientAuth(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HandshakeCompletedListener;)V")
   @ObfuscatedName("bh")
   public void method9490(HandshakeCompletedListener var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HandshakeCompletedListener;)V")
   @ObfuscatedName("bv")
   public void method9491(HandshakeCompletedListener var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bi")
   public void method9516(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("aj")
   public void method9492(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ay")
   public void method9493(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/InputStream;")
   @ObfuscatedName("az")
   public InputStream method9483() throws IOException {
      return this.val$tlsClientProtocol.getInputStream();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("ad")
   public String[] method9494() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("at")
   public void method9499(String[] var1) {
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("ak")
   public void method9500(String[] var1) {
   }

   @Override
   public SSLSession getSession() {
      try {
         return new classQZ(this);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getSession(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("an")
   public boolean method9502() {
      return false;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ba")
   public boolean method9514() {
      return false;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("av")
   public void method9501(String[] var1) {
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("au")
   public void method9496(String[] var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bn")
   public void method9504(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cr")
   public void method9529() throws IOException {
      this.val$tlsClientProtocol.connect(new classQU(this));
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bw")
   public void method9517(boolean var1) {
   }

   @Override
   public void setNeedClientAuth(boolean var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setNeedClientAuth(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("bp")
   public String[] method9509() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("bm")
   public String[] method9510() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("bg")
   public String[] method9512() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bk")
   public boolean method9515() {
      return false;
   }

   @Override
   public String[] getSupportedProtocols() {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qc.getSupportedProtocols(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("am")
   public boolean method9503() {
      return false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public synchronized void method9488() throws IOException {
      this.val$tlsClientProtocol.close();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/InputStream;")
   @ObfuscatedName("ae")
   public InputStream method9484() throws IOException {
      return this.val$tlsClientProtocol.getInputStream();
   }

   @ObfuscatedSignature(descriptor = "()Ljavax/net/ssl/SSLSession;")
   @ObfuscatedName("bx")
   public SSLSession method9506() {
      return new classQZ(this);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("be")
   public void method9518(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bq")
   public boolean method9519() {
      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)V")
   @ObfuscatedName("ee")
   public static void method9530(classQC var0) throws IOException {
      var0.val$tlsClientProtocol.connect(new classQU(var0));
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bs")
   public void method9521(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bd")
   public void method9522(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HandshakeCompletedListener;)V")
   @ObfuscatedName("bz")
   public void method9523(HandshakeCompletedListener var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HandshakeCompletedListener;)V")
   @ObfuscatedName("bl")
   public void method9524(HandshakeCompletedListener var1) {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpx;)Lym;")
   @ObfuscatedName("ac")
   public static SpritePixels method9507(HealthBarConfig var0) {
      if (var0.field5476 * -1922748515 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(HealthBarConfig.field5474, var0.field5476 * 1104423047);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classKJ.field3603, 1914620357 * var0.field5476, 0, -1592646970);
            if (null != var1) {
               HealthBarConfig.field5474.method6464(var1, 1720677464 * var0.field5476);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HandshakeCompletedListener;)V")
   @ObfuscatedName("aa")
   public void method9525(HandshakeCompletedListener var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljavax/net/ssl/HandshakeCompletedListener;)V")
   @ObfuscatedName("ac")
   public void method9526(HandshakeCompletedListener var1) {
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("ai")
   public void method9497(String[] var1) {
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("ap")
   public String[] method9495() {
      return null;
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;)V")
   @ObfuscatedName("ar")
   public void method9498(String[] var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qc.setEnabledCipherSuites(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("by")
   public boolean method9520() {
      return false;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/OutputStream;")
   @ObfuscatedName("ab")
   public OutputStream method9487() {
      return this.val$tlsClientProtocol.getOutputStream();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cb")
   public void method9489() {
      this.val$tlsClientProtocol.close();
   }
}
