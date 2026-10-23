import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("av")
public class classAV implements Runnable {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field289 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   public static final int field292 = 48;
   @ObfuscatedSignature(descriptor = "[Lak;")
   @ObfuscatedName("az")
   public volatile classAK[] field287 = new classAK[2];
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field291 = 11;
   @ObfuscatedSignature(descriptor = "Lda;")
   @ObfuscatedName("jz")
   static classDA field294;
   @ToRemove(unused = "true")
   @ObfuscatedName("dd")
   public static final int field293 = 101;
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   static final int field288 = 1;
   @ObfuscatedName("ag")
   public static int field290;

   @Override
   public void run() {
      this.method716();

      try {
         try {
            for (int var1 = 0; var1 < 2; var1++) {
               classAK var2 = this.field287[var1];
               if (null != var2) {
                  var2.method400((byte)-124);
               }
            }
         } catch (Exception var3) {
            classAAU.method270(null, var3, (byte)102);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "av.run(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method716() {
      Thread.currentThread().setName("Sound Engine");
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("wp")
   public void method712() {
      try {
         for (int var1 = 0; var1 < 2; var1++) {
            classAK var2 = this.field287[var1];
            if (null != var2) {
               var2.method400((byte)-1);
            }
         }
      } catch (Exception var3) {
         classAAU.method270(null, var3, (byte)23);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lav;)V")
   @ObfuscatedName("pt")
   public static void method713(classAV var0) {
      if (var0 == null) {
         var0.run();
      } else {
         try {
            for (int var1 = 0; var1 < 2; var1++) {
               classAK var2 = var0.field287[var1];
               if (null != var2) {
                  var2.method400((byte)-23);
               }
            }
         } catch (Exception var3) {
            classAAU.method270(null, var3, (byte)11);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lul;")
   @ObfuscatedName("az")
   public static classUL method715(byte var0) {
      try {
         synchronized (classUL.field6321) {
            if (0 == classUL.field6319 * 1171203923) {
               return new classUL();
            } else {
               classUL.field6321[(classUL.field6319 -= -1712212773) * 1171203923].method11442(-574479160);
               return classUL.field6321[classUL.field6319 * 1171203923];
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "av.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public void method714() {
      try {
         for (int var1 = 0; var1 < 2; var1++) {
            classAK var2 = this.field287[var1];
            if (null != var2) {
               var2.method400((byte)-22);
            }
         }
      } catch (Exception var3) {
         classAAU.method270(null, var3, (byte)10);
      }
   }
}
