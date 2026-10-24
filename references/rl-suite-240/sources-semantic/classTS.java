import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ts")
public class classTS {
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   static final int field6226 = 41;

   static {
      ImageIO.setUseCache(false);
   }

   classTS() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)Lym;")
   @ObfuscatedName("ae")
   public static final SpritePixels method11180(byte[] var0) {
      BufferedImage var1 = null;

      try {
         synchronized (ImageIO.class) {
            var1 = ImageIO.read(new ByteArrayInputStream(var0));
         }

         if (var1 != null) {
            int var10 = var1.getWidth();
            int var3 = var1.getHeight();
            int[] var4 = new int[var10 * var3];
            PixelGrabber var5 = new PixelGrabber(var1, 0, 0, var10, var3, var4, 0, var10);
            var5.grabPixels();
            return new SpritePixels(var4, var10, var3);
         }
      } catch (IOException var7) {
      } catch (InterruptedException var8) {
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)Lym;")
   @ObfuscatedName("af")
   public static final SpritePixels method11181(byte[] var0) {
      BufferedImage var1 = null;

      try {
         synchronized (ImageIO.class) {
            var1 = ImageIO.read(new ByteArrayInputStream(var0));
         }

         if (var1 != null) {
            int var10 = var1.getWidth();
            int var3 = var1.getHeight();
            int[] var4 = new int[var10 * var3];
            PixelGrabber var5 = new PixelGrabber(var1, 0, 0, var10, var3, var4, 0, var10);
            var5.grabPixels();
            return new SpritePixels(var4, var10, var3);
         }
      } catch (IOException var7) {
      } catch (InterruptedException var8) {
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)Lym;")
   @ObfuscatedName("ab")
   public static final SpritePixels method11182(byte[] var0) {
      BufferedImage var1 = null;

      try {
         synchronized (ImageIO.class) {
            var1 = ImageIO.read(new ByteArrayInputStream(var0));
         }

         if (var1 != null) {
            int var10 = var1.getWidth();
            int var3 = var1.getHeight();
            int[] var4 = new int[var10 * var3];
            PixelGrabber var5 = new PixelGrabber(var1, 0, 0, var10, var3, var4, 0, var10);
            var5.grabPixels();
            return new SpritePixels(var4, var10, var3);
         }
      } catch (IOException var7) {
      } catch (InterruptedException var8) {
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "([B)Lym;")
   @ObfuscatedName("ag")
   public static final SpritePixels method11183(byte[] var0) {
      BufferedImage var1 = null;

      try {
         synchronized (ImageIO.class) {
            var1 = ImageIO.read(new ByteArrayInputStream(var0));
         }

         if (var1 != null) {
            int var10 = var1.getWidth();
            int var3 = var1.getHeight();
            int[] var4 = new int[var10 * var3];
            PixelGrabber var5 = new PixelGrabber(var1, 0, 0, var10, var3, var4, 0, var10);
            var5.grabPixels();
            return new SpritePixels(var4, var10, var3);
         }
      } catch (IOException var7) {
      } catch (InterruptedException var8) {
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)Ljava/util/ArrayList;")
   @ObfuscatedName("pb")
   public static ArrayList method11185(classMA var0) {
      return var0.field4454.field4573;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("az")
   public static boolean method11184(int var0, int var1) {
      try {
         if (var0 != 10) {
            if (var1 != -324749371) {
               throw new IllegalStateException();
            }

            if (11 != var0) {
               if (var1 != -324749371) {
                  throw new IllegalStateException();
               }

               if (var0 != 12) {
                  if (var1 != -324749371) {
                     throw new IllegalStateException();
                  }

                  if (var0 != 13) {
                     if (var1 != -324749371) {
                        throw new IllegalStateException();
                     }

                     if (14 != var0) {
                        if (var1 != -324749371) {
                           throw new IllegalStateException();
                        }

                        if (15 != var0) {
                           if (var1 != -324749371) {
                              throw new IllegalStateException();
                           }

                           if (16 != var0) {
                              if (var0 != 17) {
                                 return false;
                              }

                              if (var1 != -324749371) {
                                 throw new IllegalStateException();
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return true;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ts.az(" + ')');
      }
   }
}
