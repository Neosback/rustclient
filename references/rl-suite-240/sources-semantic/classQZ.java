import java.security.Principal;
import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.security.cert.X509Certificate;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qz")
class classQZ implements SSLSession {
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   public int method9941() {
      throw new UnsupportedOperationException();
   }

   @Override
   public int getApplicationBufferSize() {
      try {
         return 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getApplicationBufferSize(" + 41);
      }
   }

   @Override
   public String getCipherSuite() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getCipherSuite(" + ')');
      }
   }

   @Override
   public long getCreationTime() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getCreationTime(" + ')');
      }
   }

   @Override
   public byte[] getId() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getId(" + ')');
      }
   }

   @Override
   public long getLastAccessedTime() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getLastAccessedTime(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("am")
   public String method9947() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/security/cert/Certificate;")
   @ObfuscatedName("aq")
   public Certificate[] method9934() {
      throw new UnsupportedOperationException();
   }

   @Override
   public int getPacketBufferSize() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getPacketBufferSize(" + 41);
      }
   }

   @Override
   public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getPeerCertificateChain(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/Object;")
   @ObfuscatedName("bk")
   public Object method9958(String var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public String getPeerHost() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getPeerHost(" + ')');
      }
   }

   @Override
   public int getPeerPort() {
      try {
         return 0;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getPeerPort(" + 41);
      }
   }

   @Override
   public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
      try {
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getPeerPrincipal(" + ')');
      }
   }

   @Override
   public String getProtocol() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getProtocol(" + ')');
      }
   }

   @Override
   public SSLSessionContext getSessionContext() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getSessionContext(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/security/cert/Certificate;")
   @ObfuscatedName("ay")
   public Certificate[] method9935() {
      throw new UnsupportedOperationException();
   }

   @Override
   public String[] getValueNames() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getValueNames(" + ')');
      }
   }

   @Override
   public void invalidate() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.invalidate(" + ')');
      }
   }

   @Override
   public boolean isValid() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.isValid(" + ')');
      }
   }

   @Override
   public void putValue(String var1, Object var2) {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "qz.putValue(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;Ljava/lang/String;)Ljava/lang/Object;")
   @ObfuscatedName("wj")
   public static Object method9959(classQZ var0, String var1) {
      if (var0 == null) {
         var0.method9940();
      }

      throw new UnsupportedOperationException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;)Ljava/security/Principal;")
   @ObfuscatedName("ky")
   public static Principal method9937(classQZ var0) {
      if (var0 == null) {
         return var0.method9940();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @Override
   public void removeValue(String var1) {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qz.removeValue(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("az")
   public int method9921() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   public int method9922() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("ac")
   public long method9926() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bx")
   public String method9924() {
      throw new UnsupportedOperationException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;)Ljava/lang/String;")
   @ObfuscatedName("tq")
   public static String method9948(classQZ var0) {
      if (var0 == null) {
         var0.getPacketBufferSize();
      }

      throw new UnsupportedOperationException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;)Ljavax/net/ssl/SSLSessionContext;")
   @ObfuscatedName("be")
   public static SSLSessionContext method9954(classQZ var0) {
      if (var0 == null) {
         var0.method9971();
      }

      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bn")
   public String method9925() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("as")
   public long method9927() {
      throw new UnsupportedOperationException();
   }

   @Override
   public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
      try {
         return this.this$1.field5513;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getPeerCertificates(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("ao")
   public byte[] method9930() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   public int method9942() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("al")
   public long method9932() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("bi")
   public String[] method9963() {
      throw new UnsupportedOperationException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;Ljava/lang/String;)Ljava/lang/Object;")
   @ObfuscatedName("ns")
   public static Object method9960(classQZ var0, String var1) {
      if (var0 == null) {
         var0.method9944();
      }

      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/Principal;")
   @ObfuscatedName("bp")
   public Principal method9952() throws SSLPeerUnverifiedException {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/security/cert/Certificate;")
   @ObfuscatedName("ad")
   public Certificate[] method9936() {
      throw new UnsupportedOperationException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;)Z")
   @ObfuscatedName("jc")
   public static boolean method9970(classQZ var0) {
      if (var0 == null) {
         var0.getLastAccessedTime();
      }

      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/Principal;")
   @ObfuscatedName("ap")
   public Principal method9938() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   public int method9943() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("bb")
   public String[] method9964() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/Object;)V")
   @ObfuscatedName("bv")
   public void method9972(String var1, Object var2) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Object getValue(String var1) {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qz.getValue(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   public int method9944() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljavax/security/cert/X509Certificate;")
   @ObfuscatedName("at")
   public X509Certificate[] method9945() throws SSLPeerUnverifiedException {
      return null;
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/security/cert/Certificate;")
   @ObfuscatedName("an")
   public Certificate[] method9946() throws SSLPeerUnverifiedException {
      return this.this$1.field5513;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ab")
   public String method9949() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("bh")
   public void method9973(String var1) {
      throw new UnsupportedOperationException();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;)J")
   @ObfuscatedName("zt")
   public static long method9928(classQZ var0) {
      if (var0 == null) {
         return var0.method9929();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   public int method9923() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("br")
   public String method9950() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bc")
   public int method9951() {
      return 0;
   }

   @ObfuscatedSignature(descriptor = "()[B")
   @ObfuscatedName("aa")
   public byte[] method9931() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ag")
   public String method9953() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljavax/net/ssl/SSLSessionContext;")
   @ObfuscatedName("bg")
   public SSLSessionContext method9955() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljavax/net/ssl/SSLSessionContext;")
   @ObfuscatedName("bj")
   public SSLSessionContext method9956() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljavax/net/ssl/SSLSessionContext;")
   @ObfuscatedName("bt")
   public SSLSessionContext method9957() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   public void method9967() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/Object;")
   @ObfuscatedName("bw")
   public Object method9961(String var1) {
      throw new UnsupportedOperationException();
   }

   @Override
   public Certificate[] getLocalCertificates() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getLocalCertificates(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/Principal;")
   @ObfuscatedName("ai")
   public Principal method9939() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("be")
   public String[] method9965() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("by")
   public String[] method9966() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   public void method9968() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   public void method9969() {
      throw new UnsupportedOperationException();
   }

   @Override
   public Principal getLocalPrincipal() {
      try {
         throw new UnsupportedOperationException();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "qz.getLocalPrincipal(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqz;)J")
   @ObfuscatedName("ue")
   public static long method9933(classQZ var0) {
      if (var0 == null) {
         return var0.method9929();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bd")
   public boolean method9971() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Ljava/lang/Object;")
   @ObfuscatedName("bo")
   public Object method9962(String var1) {
      throw new UnsupportedOperationException();
   }

   classQZ(classQC var1) {
      this.this$1 = var1;
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("ax")
   public long method9929() {
      throw new UnsupportedOperationException();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/security/Principal;")
   @ObfuscatedName("au")
   public Principal method9940() {
      throw new UnsupportedOperationException();
   }
}
