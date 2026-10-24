import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("it")
public class classIT extends classIS {
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field3056 = 17;
   @ObfuscatedName("ae")
   byte field3053;
   @ObfuscatedName("af")
   byte field3054;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field3057 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("cz")
   static final int field3058 = 88;
   @ObfuscatedName("az")
   String field3059;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field3055 = 8;

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod506(classXY var1) {
      this.field3059 = var1.method13125((byte)-56);
      if (null != this.field3059) {
         var1.method13095(-346779531);
         this.field3053 = var1.method13097((byte)17);
         this.field3054 = var1.method13097((byte)17);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lam;[IIIII)I")
   @ObfuscatedName("ft")
   public static int method6358(classAM var0, int[] var1, int var2, int var3, int var4, int var5) {
      if (var0 == null) {
         return var0.method599(var1, var2, var2, var2, var2);
      } else {
         while (var0.field233 > 0) {
            int var6 = var2 + var0.field233;
            if (var6 > var4) {
               var6 = var4;
            }

            var0.field233 += var2;
            if (var0.field228 == 256 && (var0.field230 & 0xFF) == 0) {
               if (classKG.field3581) {
                  var2 = classAM.method513(
                     0,
                     ((classAJ)var0.field153).field189,
                     var1,
                     var0.field230,
                     var2,
                     var0.field235,
                     var0.field236,
                     var0.field234,
                     var0.field232,
                     0,
                     var6,
                     var3,
                     var0
                  );
               } else {
                  var2 = classAM.method510(((classAJ)var0.field153).field189, var1, var0.field230, var2, var0.field226, var0.field237, 0, var6, var3, var0);
               }
            } else if (classKG.field3581) {
               var2 = classAM.method526(
                  0,
                  0,
                  ((classAJ)var0.field153).field189,
                  var1,
                  var0.field230,
                  var2,
                  var0.field235,
                  var0.field236,
                  var0.field234,
                  var0.field232,
                  0,
                  var6,
                  var3,
                  var0,
                  var0.field228,
                  var5
               );
            } else {
               var2 = classAM.method525(
                  0, 0, ((classAJ)var0.field153).field189, var1, var0.field230, var2, var0.field226, var0.field237, 0, var6, var3, var0, var0.field228, var5
               );
            }

            var0.field233 -= var2;
            if (var0.field233 != 0) {
               return var2;
            }

            if (var0.method606()) {
               return var4;
            }
         }

         if (var0.field228 == 256 && (var0.field230 & 0xFF) == 0) {
            return classKG.field3581
               ? classAM.method479(
                  0,
                  ((classAJ)var0.field153).field189,
                  var1,
                  var0.field230,
                  var2,
                  var0.field235,
                  var0.field236,
                  0,
                  var4,
                  var3,
                  var0,
                  classRG.method10000((classAJ)var0.field153)
               )
               : classAM.method477(((classAJ)var0.field153).field189, var1, var0.field230, var2, var0.field226, 0, var4, var3, var0);
         } else {
            return classKG.field3581
               ? classAM.method496(
                  0, 0, ((classAJ)var0.field153).field189, var1, var0.field230, var2, var0.field235, var0.field236, 0, var4, var3, var0, var0.field228, var5
               )
               : classAM.method490(0, 0, ((classAJ)var0.field153).field189, var1, var0.field230, var2, var0.field226, 0, var4, var3, var0, var0.field228, var5);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod507(classXY var1, int var2) {
      try {
         this.field3059 = var1.method13125((byte)52);
         if (null != this.field3059) {
            var1.method13095(-346779531);
            this.field3053 = var1.method13097((byte)17);
            this.field3054 = var1.method13097((byte)17);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "it.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;I)V")
   @ObfuscatedName("af")
   @Override
   void vmethod509(ClanChannel var1, int var2) {
      try {
         var1.field2946 = this.field3059;
         if (this.field3059 != null) {
            var1.field2947 = this.field3053;
            var1.field2948 = this.field3054;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "it.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod511(ClanChannel var1) {
      var1.field2946 = this.field3059;
      if (this.field3059 != null) {
         var1.field2947 = this.field3053;
         var1.field2948 = this.field3054;
      }
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("al")
   public static int method6357(int var0, int var1, int var2) {
      try {
         return var0 + (int)(Math.random() * (var1 - var0));
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "it.al(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod508(ClanChannel var1) {
      var1.field2946 = this.field3059;
      if (this.field3059 != null) {
         var1.field2947 = this.field3053;
         var1.field2948 = this.field3054;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lie;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod510(ClanChannel var1) {
      var1.field2946 = this.field3059;
      if (this.field3059 != null) {
         var1.field2947 = this.field3053;
         var1.field2948 = this.field3054;
      }
   }

   classIT(classIX var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "([BI)Lym;")
   @ObfuscatedName("az")
   public static final SpritePixels method6356(byte[] var0, int var1) {
      try {
         BufferedImage var2 = null;

         try {
            synchronized (ImageIO.class) {
               var2 = ImageIO.read(new ByteArrayInputStream(var0));
            }

            if (var2 != null) {
               int var12 = var2.getWidth();
               int var4 = var2.getHeight();
               int[] var5 = new int[var12 * var4];
               PixelGrabber var6 = new PixelGrabber(var2, 0, 0, var12, var4, var5, 0, var12);
               var6.grabPixels();
               return new SpritePixels(var5, var12, var4);
            }
         } catch (IOException var8) {
         } catch (InterruptedException var9) {
         }

         return null;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "it.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod505(classXY var1) {
      this.field3059 = var1.method13125((byte)62);
      if (null != this.field3059) {
         var1.method13095(-346779531);
         this.field3053 = var1.method13097((byte)17);
         this.field3054 = var1.method13097((byte)17);
      }
   }
}
