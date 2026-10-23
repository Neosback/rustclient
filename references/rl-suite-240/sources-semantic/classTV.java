import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tv")
public class classTV extends classAK {
   @ObfuscatedName("bt")
   AudioFormat field6235;
   @ObfuscatedName("af")
   SourceDataLine field6236;
   @ObfuscatedName("bg")
   int field6234;
   @ObfuscatedName("bj")
   byte[] field6237;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bg")
   @Override
   public void vmethod46() {
      this.field6235 = new AudioFormat(22050.0F, 16, classKG.field3581 ? 2 : 1, true, false);
      this.field6237 = new byte[512 << (classKG.field3581 ? 2 : 1)];
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("al")
   @Override
   public void vmethod45(byte var1) {
      try {
         this.field6235 = new AudioFormat(22050.0F, 16, classKG.field3581 ? 2 : 1, true, false);
         byte var10002;
         if (classKG.field3581) {
            if (var1 != 27) {
               return;
            }

            var10002 = 2;
         } else {
            var10002 = 1;
         }

         this.field6237 = new byte[512 << var10002];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tv.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("be")
   @Override
   protected int vmethod55() {
      return this.field6234 * -426969145 - (this.field6236.available() >> (classKG.field3581 ? 2 : 1));
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ay")
   @Override
   protected int vmethod54(int var1) {
      try {
         int var10000 = this.field6234 * -426969145;
         int var10001 = this.field6236.available();
         byte var10002;
         if (classKG.field3581) {
            if (var1 >= -1768472356) {
               throw new IllegalStateException();
            }

            var10002 = 2;
         } else {
            var10002 = 1;
         }

         return var10000 - (var10001 >> var10002);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tv.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   @Override
   protected void vmethod58() {
      short var1 = 512;
      if (classKG.field3581) {
         var1 <<= 1;
      }

      for (int var2 = 0; var2 < var1; var2++) {
         int var3 = this.field214[var2];
         if ((var3 + 8388608 & 0xFF000000) != 0) {
            var3 = 8388607 ^ var3 >> 31;
         }

         this.field6237[2 * var2] = (byte)(var3 >> 8);
         this.field6237[var2 * 2 + 1] = (byte)(var3 >> 16);
      }

      this.field6236.write(this.field6237, 0, var1 << 1);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bb")
   @Override
   protected int vmethod56() {
      return this.field6234 * -426969145 - (this.field6236.available() >> (classKG.field3581 ? 2 : 1));
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aj")
   @Override
   public void vmethod50(int var1, int var2) throws LineUnavailableException {
      try {
         try {
            Info var10000 = new Info;
            byte var10005;
            if (classKG.field3581) {
               if (var2 != 364500251) {
                  throw new IllegalStateException();
               }

               var10005 = 2;
            } else {
               var10005 = 1;
            }

            var10000./* $VF: Unable to resugar constructor */<init>(SourceDataLine.class, this.field6235, var1 << var10005);
            Info var3 = var10000;
            this.field6236 = (SourceDataLine)AudioSystem.getLine(var3);
            this.field6236.open();
            this.field6236.start();
            this.field6234 = var1 * 954309111;
         } catch (LineUnavailableException var4) {
            if (classPN.method9071(var1, 1742492998) != 1) {
               if (var2 != 364500251) {
                  throw new IllegalStateException();
               } else {
                  this.vmethod50(classBZ.method1387(var1, -1278489192), 364500251);
               }
            } else {
               this.field6236 = null;
               throw var4;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "tv.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ap")
   @Override
   protected void vmethod62(int var1) {
      try {
         this.field6236.flush();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tv.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bj")
   @Override
   public void vmethod47() {
      this.field6235 = new AudioFormat(22050.0F, 16, classKG.field3581 ? 2 : 1, true, false);
      this.field6237 = new byte[512 << (classKG.field3581 ? 2 : 1)];
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bo")
   @Override
   public void vmethod49(int var1) throws LineUnavailableException {
      try {
         Info var2 = new Info(SourceDataLine.class, this.field6235, var1 << (classKG.field3581 ? 2 : 1));
         this.field6236 = (SourceDataLine)AudioSystem.getLine(var2);
         this.field6236.open();
         this.field6236.start();
         this.field6234 = var1 * 954309111;
      } catch (LineUnavailableException var3) {
         if (classPN.method9071(var1, -524488808) != 1) {
            this.vmethod50(classBZ.method1387(var1, -1904331352), 364500251);
         } else {
            this.field6236 = null;
            throw var3;
         }
      }
   }

   classTV() {
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bu")
   @Override
   public void vmethod53(int var1) throws LineUnavailableException {
      try {
         Info var2 = new Info(SourceDataLine.class, this.field6235, var1 << (classKG.field3581 ? 2 : 1));
         this.field6236 = (SourceDataLine)AudioSystem.getLine(var2);
         this.field6236.open();
         this.field6236.start();
         this.field6234 = var1 * 954309111;
      } catch (LineUnavailableException var3) {
         if (classPN.method9071(var1, 1856456230) != 1) {
            this.vmethod50(classBZ.method1387(var1, -2058482033), 364500251);
         } else {
            this.field6236 = null;
            throw var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ad")
   @Override
   protected void vmethod60(byte var1) {
      try {
         if (null != this.field6236) {
            if (var1 == 10) {
               return;
            }

            this.field6236.close();
            this.field6236 = null;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tv.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bw")
   @Override
   public void vmethod51(int var1) throws LineUnavailableException {
      try {
         Info var2 = new Info(SourceDataLine.class, this.field6235, var1 << (classKG.field3581 ? 2 : 1));
         this.field6236 = (SourceDataLine)AudioSystem.getLine(var2);
         this.field6236.open();
         this.field6236.start();
         this.field6234 = var1 * -1120475207;
      } catch (LineUnavailableException var3) {
         if (classPN.method9071(var1, 1380613298) != 1) {
            this.vmethod50(classBZ.method1387(var1, 855704813), 364500251);
         } else {
            this.field6236 = null;
            throw var3;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bk")
   @Override
   public void vmethod48() {
      this.field6235 = new AudioFormat(22050.0F, 16, classKG.field3581 ? 2 : 1, true, false);
      this.field6237 = new byte[512 << (classKG.field3581 ? 2 : 1)];
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bi")
   @Override
   protected int vmethod57() {
      return this.field6234 * -426969145 - (this.field6236.available() >> (classKG.field3581 ? 2 : 1));
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   @Override
   protected void vmethod65() {
      this.field6236.flush();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("by")
   @Override
   protected void vmethod59() {
      short var1 = 512;
      if (classKG.field3581) {
         var1 <<= 1;
      }

      for (int var2 = 0; var2 < var1; var2++) {
         int var3 = this.field214[var2];
         if ((var3 + 264936875 & 0xFF000000) != 0) {
            var3 = 260458448 ^ var3 >> 31;
         }

         this.field6237[2 * var2] = (byte)(var3 >> 8);
         this.field6237[var2 * 2 + 1] = (byte)(var3 >> 16);
      }

      this.field6236.write(this.field6237, 0, var1 << 1);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   @Override
   protected void vmethod61() {
      if (null != this.field6236) {
         this.field6236.close();
         this.field6236 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   @Override
   protected void vmethod63() {
      this.field6236.flush();
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ba")
   @Override
   public void vmethod52(int var1) throws LineUnavailableException {
      try {
         Info var2 = new Info(SourceDataLine.class, this.field6235, var1 << (classKG.field3581 ? 2 : 1));
         this.field6236 = (SourceDataLine)AudioSystem.getLine(var2);
         this.field6236.open();
         this.field6236.start();
         this.field6234 = var1 * 954309111;
      } catch (LineUnavailableException var3) {
         if (classPN.method9071(var1, -860494126) != 1) {
            this.vmethod50(classBZ.method1387(var1, -1215295495), 364500251);
         } else {
            this.field6236 = null;
            throw var3;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lau;)Laj;")
   @ObfuscatedName("bk")
   public static classAJ method11226(classAU var0) {
      if (null != var0.field280 && var0.field280.field139.tryLock()) {
         classAJ var1;
         try {
            var1 = var0.method704(1213681298);
         } finally {
            var0.field280.field139.unlock();
         }

         return var1;
      } else {
         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bd")
   @Override
   protected void vmethod64() {
      this.field6236.flush();
   }
}
