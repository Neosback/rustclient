import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.DirectColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("tg")
public final class MainBufferProvider extends BufferProvider implements net.runelite.api.MainBufferProvider {
   @ObfuscatedName("af")
   Component field6133;
   @ObfuscatedName("kb")
   public Graphics field6134;
   @ObfuscatedName("az")
   Image field6132;

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("as")
   final void method10847(Component var1) {
      this.field6133 = var1;
   }

   MainBufferProvider(int var1, int var2, Component var3, boolean var4) {
      super.field6966 = var1 * 389319299;
      super.field6965 = var2 * 1180487575;
      super.field6969 = new int[var1 * var2 + 1];
      boolean var5 = classOE.field4841.isGpu();
      if (var4 && !var5) {
         super.field6968 = new float[var1 * var2 + 1];
      }

      DataBufferInt var6 = new DataBufferInt(super.field6969, super.field6969.length);
      DirectColorModel var7;
      if (var5) {
         var7 = new DirectColorModel(ColorSpace.getInstance(1000), 32, 16711680, 65280, 255, -16777216, true, 3);
      } else {
         var7 = new DirectColorModel(32, 16711680, 65280, 255);
      }

      WritableRaster var8 = Raster.createWritableRaster(
         var7.createCompatibleSampleModel(super.field6966 * -1238133717, super.field6965 * 1855103015), var6, (Point)null
      );
      this.field6132 = new BufferedImage(var7, var8, false, new Hashtable());
      this.field6133 = var3;
      this.method13368();
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("ab")
   final void method10848(Component var1) {
      this.field6133 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("ag")
   final void method10849(Component var1) {
      this.field6133 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/awt/Component;")
   @ObfuscatedName("ku")
   public Component method10855() {
      return this.field6133;
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("af")
   @Override
   public final void vmethod578(int var1, int var2, byte var3) {
      if (this.field6134 == null) {
         Canvas var4 = (Canvas)this.method10855();
         this.field6134 = var4.getGraphics();
      }

      classOE.field4841.getCallbacks().draw(this, this.field6134, var1, var2);
   }

   public Image getImage() {
      return this.field6132;
   }

   @ObfuscatedSignature(descriptor = "(Ltg;Ljava/awt/Component;B)V")
   @ObfuscatedName("pm")
   public static void method10850(MainBufferProvider var0, Component var1, byte var2) {
      if (var0 == null) {
         var0.method10851(var1, var2);
      } else {
         try {
            var0.field6133 = var1;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "tg.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ax")
   @Override
   public final void vmethod577(int var1, int var2) {
      method10853(this, this.field6133.getGraphics(), var1, var2, 1690449305);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;II)V")
   @ObfuscatedName("ac")
   final void method10852(Graphics var1, int var2, int var3) {
      try {
         var1.drawImage(this.field6132, var2, var3, this.field6133);
      } catch (Exception var5) {
         this.field6133.repaint();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Image;)V")
   @ObfuscatedName("sh")
   public void method10856(Image var1) {
      this.field6132 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ltg;Ljava/awt/Graphics;III)V")
   @ObfuscatedName("ir")
   public static void method10853(MainBufferProvider var0, Graphics var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method10854(var1, var2, var2, var2);
      } else {
         try {
            try {
               var1.drawImage(var0.field6132, var2, var3, var0.field6133);
            } catch (Exception var6) {
               var0.field6133.repaint();
            }
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "tg.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;III)V")
   @ObfuscatedName("ae")
   final void method10854(Graphics var1, int var2, int var3, int var4) {
      try {
         try {
            var1.drawImage(this.field6132, var2, var3, this.field6133);
         } catch (Exception var6) {
            this.field6133.repaint();
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "tg.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;B)V")
   @ObfuscatedName("az")
   final void method10851(Component var1, byte var2) {
      try {
         this.field6133 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tg.az(" + ')');
      }
   }
}
