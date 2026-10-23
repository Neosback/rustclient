import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.LinkedList;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.crypto.tls.CertificateRequest;
import org.bouncycastle.crypto.tls.TlsAuthentication;
import org.bouncycastle.crypto.tls.TlsCredentials;

@ObfuscatedName("qt")
class classQT implements TlsAuthentication {
   classQT(classQU var1) {
      this.this$2 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwy;)Ljava/lang/String;")
   @ObfuscatedName("lj")
   public static String method9855(classWY var0) {
      return var0.field6788;
   }

   public void notifyServerCertificate(Certificate var1) throws IOException {
      try {
         try {
            CertificateFactory var2 = CertificateFactory.getInstance("X.509");
            LinkedList var3 = new LinkedList();
            org.bouncycastle.asn1.x509.Certificate[] var4 = var1.getCertificateList();

            for (int var5 = 0; var5 < var4.length; var5++) {
               org.bouncycastle.asn1.x509.Certificate var6 = var4[var5];
               var3.add(var2.generateCertificate(new ByteArrayInputStream(var6.getEncoded())));
            }

            this.this$2.this$1.field5513 = var3.toArray(new java.security.cert.Certificate[0]);
         } catch (CertificateException var7) {
            throw new IOException(var7);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "qt.notifyServerCertificate(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/bouncycastle/crypto/tls/CertificateRequest;)Lorg/bouncycastle/crypto/tls/TlsCredentials;")
   @ObfuscatedName("ae")
   public TlsCredentials method9857(CertificateRequest var1) throws IOException {
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Lorg/bouncycastle/crypto/tls/Certificate;)V")
   @ObfuscatedName("az")
   public void method9856(Certificate var1) throws IOException {
      try {
         CertificateFactory var2 = CertificateFactory.getInstance("X.509");
         LinkedList var3 = new LinkedList();
         org.bouncycastle.asn1.x509.Certificate[] var4 = var1.getCertificateList();

         for (int var5 = 0; var5 < var4.length; var5++) {
            org.bouncycastle.asn1.x509.Certificate var6 = var4[var5];
            var3.add(var2.generateCertificate(new ByteArrayInputStream(var6.getEncoded())));
         }

         this.this$2.this$1.field5513 = var3.toArray(new java.security.cert.Certificate[0]);
      } catch (CertificateException var7) {
         throw new IOException(var7);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lorg/bouncycastle/crypto/tls/CertificateRequest;)Lorg/bouncycastle/crypto/tls/TlsCredentials;")
   @ObfuscatedName("af")
   public TlsCredentials method9858(CertificateRequest var1) throws IOException {
      return null;
   }

   public TlsCredentials getClientCredentials(CertificateRequest var1) throws IOException {
      try {
         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qt.getClientCredentials(" + ')');
      }
   }
}
