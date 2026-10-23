import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.SyncFailedException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("aar")
public final class classAAR {
   @ObfuscatedName("az")
   final File field96;
   @ObfuscatedName("af")
   final long field97;
   @ObfuscatedName("ae")
   RandomAccessFile field98;
   @ObfuscatedName("ab")
   long field99;

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("av")
   public final void method246(boolean var1) throws IOException {
      if (this.field98 != null) {
         if (var1) {
            try {
               this.field98.getFD().sync();
            } catch (SyncFailedException var3) {
            }
         }

         this.field98.close();
         this.field98 = null;
      }
   }

   public classAAR(File var1, String var2, long var3) throws IOException {
      if (-1L == var3) {
         var3 = Long.MAX_VALUE;
      }

      if (var1.length() > var3) {
         var1.delete();
      }

      this.field98 = new RandomAccessFile(var1, var2);
      this.field96 = var1;
      this.field97 = 4558015941283848441L * var3;
      this.field99 = 0L;
      int var5 = this.field98.read();
      if (var5 != -1 && !var2.equals("r")) {
         this.field98.seek(0L);
         this.field98.write(var5);
      }

      this.field98.seek(0L);
   }

   @ObfuscatedSignature(descriptor = "([BIII)V")
   @ObfuscatedName("ae")
   public final void method238(byte[] var1, int var2, int var3, int var4) throws IOException {
      try {
         if (var3 + 8826329765573070539L * this.field99 > this.field97 * -1097077856284434103L) {
            if (var4 >= 689229467) {
               throw new IllegalStateException();
            } else {
               this.field98.seek(-1097077856284434103L * this.field97);
               this.field98.write(1);
               throw new EOFException();
            }
         } else {
            this.field98.write(var1, var2, var3);
            this.field99 += var3 * 7580072378778380003L;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "aar.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("cg")
   public void method259(byte[] var1, int var2, int var3) {
      while (var3 > 0) {
         int var4 = this.field98.read(var1, var2, var3);
         if (var4 == -1) {
            throw new EOFException();
         }

         this.field99 = (this.field99 * 8826329765573070539L + var4) * 7580072378778380003L;
         var2 += var4;
         var3 -= var4;
      }
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("ju")
   public void method260(byte[] var1, int var2, int var3) {
      this.method238(var1, var2, var3, -1522899189);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/io/File;")
   @ObfuscatedName("aa")
   final File method231() {
      return this.field96;
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ag")
   public final void method247(boolean var1, int var2) throws IOException {
      try {
         if (this.field98 != null) {
            if (var2 == 1032478127) {
               throw new IllegalStateException();
            }

            if (var1) {
               if (var2 == 1032478127) {
                  return;
               }

               try {
                  this.field98.getFD().sync();
               } catch (SyncFailedException var4) {
               }
            }

            this.field98.close();
            this.field98 = null;
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "aar.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laar;Z)V")
   @ObfuscatedName("gz")
   public static void method248(classAAR var0, boolean var1) throws IOException {
      if (var0.field98 != null) {
         if (var1) {
            try {
               var0.field98.getFD().sync();
            } catch (SyncFailedException var3) {
            }
         }

         var0.field98.close();
         var0.field98 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("fp")
   public long method261() {
      return this.method251(-153393736);
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("as")
   public final long method251(int var1) throws IOException {
      try {
         return this.field98.length();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aar.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([BIII)I")
   @ObfuscatedName("ax")
   public final int method255(byte[] var1, int var2, int var3, int var4) throws IOException {
      try {
         int var5 = this.field98.read(var1, var2, var3);
         if (var5 > 0) {
            if (var4 != -1397369950) {
               throw new IllegalStateException();
            }

            this.field99 += var5 * 7580072378778380003L;
         }

         return var5;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "aar.ax(" + 41);
      }
   }

   @Override
   protected void finalize() throws Throwable {
      try {
         if (this.field98 != null) {
            this.method242((byte)1);
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "aar.finalize(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ab")
   public final void method242(byte var1) throws IOException {
      try {
         this.method247(false, 1787136346);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aar.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("abn")
   protected void method256() throws Throwable {
      if (this.field98 != null) {
         this.method242((byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("abc")
   protected void method257() throws Throwable {
      if (this.field98 != null) {
         this.method242((byte)1);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laar;Z)V")
   @ObfuscatedName("hc")
   public static void method249(classAAR var0, boolean var1) throws IOException {
      if (var0 == null) {
         var0.finalize();
      } else {
         if (var0.field98 != null) {
            if (var1) {
               try {
                  var0.field98.getFD().sync();
               } catch (SyncFailedException var3) {
               }
            }

            var0.field98.close();
            var0.field98 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("al")
   final void method233(long var1) throws IOException {
      this.field98.seek(var1);
      this.field99 = var1 * 7580072378778380003L;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laar;[BII)V")
   @ObfuscatedName("gn")
   public static void method239(classAAR var0, byte[] var1, int var2, int var3) throws IOException {
      if (var3 + 8826329765573070539L * var0.field99 > var0.field97 * -1097077856284434103L) {
         var0.field98.seek(-1097077856284434103L * var0.field97);
         var0.field98.write(1);
         throw new EOFException();
      } else {
         var0.field98.write(var1, var2, var3);
         var0.field99 += var3 * 7580072378778380003L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("pr")
   public void method262(boolean var1) {
      this.method247(var1, 1787136346);
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("af")
   final void method234(long var1) throws IOException {
      this.field98.seek(var1);
      this.field99 = var1 * 7580072378778380003L;
   }

   @ObfuscatedSignature(descriptor = "(Laar;J)V")
   @ObfuscatedName("gc")
   public static void method235(classAAR var0, long var1) throws IOException {
      if (var0 == null) {
         var0.method237(var1);
      }

      try {
         var0.field98.seek(var1);
         var0.field99 = var1 * 7580072378778380003L;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "aar.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("ao")
   final void method236(long var1) throws IOException {
      this.field98.seek(var1);
      this.field99 = var1 * 7580072378778380003L;
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("ay")
   public final void method240(byte[] var1, int var2, int var3) throws IOException {
      if (var3 + 8826329765573070539L * this.field99 > this.field97 * -1097077856284434103L) {
         this.field98.seek(-1097077856284434103L * this.field97);
         this.field98.write(1);
         throw new EOFException();
      } else {
         this.field98.write(var1, var2, var3);
         this.field99 += var3 * 7580072378778380003L;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laar;)J")
   @ObfuscatedName("fm")
   public static long method252(classAAR var0) throws IOException {
      return var0.field98.length();
   }

   @ObfuscatedSignature(descriptor = "([BII)V")
   @ObfuscatedName("aq")
   public final void method241(byte[] var1, int var2, int var3) throws IOException {
      if (var3 + 8826329765573070539L * this.field99 > this.field97 * -1097077856284434103L) {
         this.field98.seek(-1097077856284434103L * this.field97);
         this.field98.write(1);
         throw new EOFException();
      } else {
         this.field98.write(var1, var2, var3);
         this.field99 += var3 * 7580072378778380003L;
      }
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("ah")
   public final long method253() throws IOException {
      return this.field98.length();
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("eo")
   public void method263(long var1) {
      method235(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ak")
   public final void method250(boolean var1) throws IOException {
      if (this.field98 != null) {
         if (var1) {
            try {
               this.field98.getFD().sync();
            } catch (SyncFailedException var3) {
            }
         }

         this.field98.close();
         this.field98 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public final void method243() throws IOException {
      this.method247(false, 1494157377);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public final void method244() throws IOException {
      this.method247(false, -347069786);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public final void method245() throws IOException {
      this.method247(false, 1573672214);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("abt")
   protected void method258() throws Throwable {
      if (this.field98 != null) {
         this.method242((byte)1);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/io/File;")
   @ObfuscatedName("az")
   final File method232(int var1) {
      try {
         return this.field96;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "aar.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("at")
   public final long method254() throws IOException {
      return this.field98.length();
   }

   @ObfuscatedSignature(descriptor = "(J)V")
   @ObfuscatedName("aj")
   final void method237(long var1) {
      try {
         this.field98.seek(var1);
         this.field99 = var1 * 7580072378778380003L;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "aar.af(" + ')');
      }
   }
}
