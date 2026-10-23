import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tz")
public class classTZ implements MouseListener, MouseMotionListener, FocusListener {
   @ObfuscatedName("ak")
   public static volatile long field6266 = 0L;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6268 = 2;
   @ObfuscatedName("aa")
   public static int field6264 = 0;
   @ObfuscatedName("ab")
   public static int field6249 = 0;
   @ObfuscatedName("ag")
   public static int field6253 = 0;
   @ObfuscatedName("as")
   public static int field6262 = 0;
   @ObfuscatedName("al")
   public static long field6256 = 0L;
   @ObfuscatedName("ac")
   public static int field6259 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6269 = 1;
   @ObfuscatedName("aw")
   public static volatile int field6265 = 0;
   @ObfuscatedName("hn")
   public int field6270;
   @ObfuscatedName("ax")
   public static long field6252 = 0L;
   @ObfuscatedName("ao")
   public static int field6255 = 0;
   @ObfuscatedSignature(descriptor = "Ltz;")
   @ObfuscatedName("aj")
   public static classTZ field6257 = new classTZ();
   @ObfuscatedName("aq")
   public static volatile int field6254 = 0;
   @ObfuscatedName("ad")
   public static volatile int field6250 = -604645463;
   @ObfuscatedName("ap")
   public static volatile int field6261 = -1987403529;
   @ObfuscatedName("au")
   public static volatile long field6251 = 5058297159521459785L;
   @ObfuscatedName("ai")
   public static volatile int field6263 = 0;
   @ObfuscatedName("ar")
   public static volatile int field6260 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6267 = 4;
   @ObfuscatedName("ay")
   public static volatile int field6258 = 0;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hq")
   static classVP field6271;

   @Override
   public final synchronized void mouseMoved(MouseEvent var1) {
      if (this.field6270 == 0) {
         var1 = classOE.field4841.getCallbacks().mouseMoved(var1);
      }

      if (!var1.isConsumed()) {
         this.field6270++;

         try {
            MouseEvent var4 = var1;

            try {
               if (null != field6257) {
                  field6258 = 0;
                  client.method1819(-1);
                  field6250 = var4.getX() * 604645463;
                  field6261 = var4.getY() * 1987403529;
                  field6251 = var4.getWhen() * -5058297159521459785L;
               }
            } catch (RuntimeException var7) {
               throw classEG.method3899(var7, "tz.mouseMoved(" + ')');
            }
         } finally {
            this.field6270--;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;I)I")
   @ObfuscatedName("af")
   final int method11274(MouseEvent var1, int var2) {
      try {
         int var3 = var1.getButton();
         if (!var1.isAltDown()) {
            if (var2 != 422353457) {
               throw new IllegalStateException();
            }

            if (2 != var3) {
               if (!var1.isMetaDown()) {
                  if (var2 != 422353457) {
                     throw new IllegalStateException();
                  }

                  if (3 != var3) {
                     return 1;
                  }

                  if (var2 != 422353457) {
                     throw new IllegalStateException();
                  }
               }

               return 2;
            }
         }

         return 4;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "tz.af(" + 41);
      }
   }

   @Override
   public final synchronized void mousePressed(MouseEvent var1) {
      if (this.field6270 == 0) {
         var1 = classOE.field4841.getCallbacks().mousePressed(var1);
      }

      if (!var1.isConsumed()) {
         this.field6270++;

         try {
            MouseEvent var4 = var1;
            classTZ var3 = this;

            try {
               if (field6257 != null) {
                  field6258 = 0;
                  client.method1819(-1);
                  field6260 = var4.getX() * 1076405321;
                  field6265 = var4.getY() * 1741789915;
                  field6266 = classDD.method2988(16711680) * -5704034266074320611L;
                  field6263 = var3.method11274(var4, 422353457) * -1377702117;
                  if (0 != -1694969069 * field6263) {
                     field6254 = field6263 * -1471911103;
                  }
               }

               if (var4.isPopupTrigger()) {
                  var4.consume();
               }
            } catch (RuntimeException var7) {
               throw classEG.method3899(var7, "tz.mousePressed(" + ')');
            }
         } finally {
            this.field6270--;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ao")
   public final synchronized void method11284(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6250 = -604645463;
         field6261 = -1987403529;
         field6251 = var1.getWhen() * -5058297159521459785L;
      }
   }

   @Override
   public final void mouseClicked(MouseEvent var1) {
      var1 = classOE.field4841.getCallbacks().mouseClicked(var1);
      if (!var1.isConsumed()) {
         MouseEvent var3 = var1;

         try {
            if (var3.isPopupTrigger()) {
               var3.consume();
            }
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "tz.mouseClicked(" + ')');
         }
      }
   }

   @Override
   public final synchronized void mouseEntered(MouseEvent var1) {
      if (this.field6270 == 0) {
         var1 = classOE.field4841.getCallbacks().mouseEntered(var1);
      }

      if (!var1.isConsumed()) {
         this.field6270++;

         try {
            MouseEvent var4 = var1;
            classTZ var3 = this;

            try {
               var3.mouseMoved(var4);
            } catch (RuntimeException var7) {
               throw classEG.method3899(var7, "tz.mouseEntered(" + ')');
            }
         } finally {
            this.field6270--;
         }
      }
   }

   @Override
   public final synchronized void mouseExited(MouseEvent var1) {
      if (this.field6270 == 0) {
         var1 = classOE.field4841.getCallbacks().mouseExited(var1);
      }

      if (!var1.isConsumed()) {
         this.field6270++;

         try {
            MouseEvent var4 = var1;

            try {
               if (null != field6257) {
                  field6258 = 0;
                  client.method1819(-1);
                  field6250 = -604645463;
                  field6261 = -1987403529;
                  field6251 = var4.getWhen() * -5058297159521459785L;
               }
            } catch (RuntimeException var7) {
               throw classEG.method3899(var7, "tz.mouseExited(" + ')');
            }
         } finally {
            this.field6270--;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("al")
   public final void method11281(MouseEvent var1) {
      if (var1.isPopupTrigger()) {
         var1.consume();
      }
   }

   @Override
   public final void focusGained(FocusEvent var1) {
      try {
         ;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tz.focusGained(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ar")
   public final synchronized void method11278(MouseEvent var1) {
      if (field6257 != null) {
         field6258 = 0;
         field6260 = var1.getX() * 1076405321;
         field6265 = var1.getY() * 1741789915;
         field6266 = classDD.method2988(16711680) * -5704034266074320611L;
         field6263 = this.method11274(var1, 422353457) * -1377702117;
         if (0 != -1694969069 * field6263) {
            field6254 = field6263 * -1471911103;
         }
      }

      if (var1.isPopupTrigger()) {
         var1.consume();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public static void method11272() {
      if (null != field6257) {
         synchronized (field6257) {
            field6257 = null;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   public static void method11273() {
      if (null != field6257) {
         synchronized (field6257) {
            field6257 = null;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)I")
   @ObfuscatedName("ag")
   final int method11275(MouseEvent var1) {
      int var2 = var1.getButton();
      if (var1.isAltDown() || 2 == var2) {
         return 4;
      } else {
         return !var1.isMetaDown() && 3 != var2 ? 1 : 2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)I")
   @ObfuscatedName("as")
   final int method11276(MouseEvent var1) {
      int var2 = var1.getButton();
      if (var1.isAltDown() || 2 == var2) {
         return 4;
      } else {
         return !var1.isMetaDown() && 3 != var2 ? 1 : 2;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)I")
   @ObfuscatedName("ax")
   final int method11277(MouseEvent var1) {
      int var2 = var1.getButton();
      if (var1.isAltDown() || 2 == var2) {
         return 4;
      } else {
         return !var1.isMetaDown() && 3 != var2 ? 1 : 2;
      }
   }

   @Override
   public final synchronized void mouseDragged(MouseEvent var1) {
      if (this.field6270 == 0) {
         var1 = classOE.field4841.getCallbacks().mouseDragged(var1);
      }

      if (!var1.isConsumed()) {
         this.field6270++;

         try {
            MouseEvent var4 = var1;
            classTZ var3 = this;

            try {
               var3.mouseMoved(var4);
            } catch (RuntimeException var7) {
               throw classEG.method3899(var7, "tz.mouseDragged(" + ')');
            }
         } finally {
            this.field6270--;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("au")
   public final synchronized void method11279(MouseEvent var1) {
      if (field6257 != null) {
         field6258 = 0;
         field6260 = var1.getX() * 1076405321;
         field6265 = var1.getY() * 1741789915;
         field6266 = classDD.method2988(16711680) * -5704034266074320611L;
         field6263 = this.method11274(var1, 422353457) * -1377702117;
         if (0 != -1694969069 * field6263) {
            field6254 = field6263 * -1471911103;
         }
      }

      if (var1.isPopupTrigger()) {
         var1.consume();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("aa")
   public final synchronized void method11280(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6254 = 0;
      }

      if (var1.isPopupTrigger()) {
         var1.consume();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ap")
   public final synchronized void method11287(MouseEvent var1) {
      this.mouseMoved(var1);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("aj")
   public final void method11282(MouseEvent var1) {
      if (var1.isPopupTrigger()) {
         var1.consume();
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ai")
   public final synchronized void method11283(MouseEvent var1) {
      this.mouseMoved(var1);
   }

   @Override
   public final synchronized void mouseReleased(MouseEvent var1) {
      if (this.field6270 == 0) {
         var1 = classOE.field4841.getCallbacks().mouseReleased(var1);
      }

      if (!var1.isConsumed()) {
         this.field6270++;

         try {
            MouseEvent var4 = var1;

            try {
               if (null != field6257) {
                  field6258 = 0;
                  client.method1819(-1);
                  field6254 = 0;
               }

               if (var4.isPopupTrigger()) {
                  var4.consume();
               }
            } catch (RuntimeException var7) {
               throw classEG.method3899(var7, "tz.mouseReleased(" + ')');
            }
         } finally {
            this.field6270--;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ac")
   public final synchronized void method11285(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6250 = -604645463;
         field6261 = -1987403529;
         field6251 = var1.getWhen() * -5058297159521459785L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ad")
   public final synchronized void method11286(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6250 = -604645463;
         field6261 = -1987403529;
         field6251 = var1.getWhen() * -5058297159521459785L;
      }
   }

   classTZ() {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ak")
   public final synchronized void method11288(MouseEvent var1) {
      this.mouseMoved(var1);
   }

   @Override
   public final synchronized void focusLost(FocusEvent var1) {
      try {
         if (field6257 != null) {
            field6254 = 0;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tz.focusLost(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("aq")
   public final synchronized void method11289(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6250 = var1.getX() * 604645463;
         field6261 = var1.getY() * -1665405372;
         field6251 = var1.getWhen() * -5058297159521459785L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("aw")
   public final synchronized void method11290(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6250 = var1.getX() * -220529686;
         field6261 = var1.getY() * 1987403529;
         field6251 = var1.getWhen() * -5058297159521459785L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/MouseEvent;)V")
   @ObfuscatedName("ay")
   public final synchronized void method11291(MouseEvent var1) {
      if (null != field6257) {
         field6258 = 0;
         field6250 = var1.getX() * 604645463;
         field6261 = var1.getY() * 1987403529;
         field6251 = var1.getWhen() * -5058297159521459785L;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afz")
   public final void method11292(FocusEvent var1) {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("afg")
   public final synchronized void method11293(FocusEvent var1) {
      if (field6257 != null) {
         field6254 = 0;
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/event/FocusEvent;)V")
   @ObfuscatedName("aff")
   public final synchronized void method11294(FocusEvent var1) {
      if (field6257 != null) {
         field6254 = 0;
      }
   }
}
