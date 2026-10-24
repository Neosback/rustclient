import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tc")
public final class classTC implements classMF, MouseWheelListener {
   @ObfuscatedName("az")
   int field6116 = 0;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IIZ)V")
   @ObfuscatedName("jg")
   public static void method10787(RenderOverview var0, int var1, int var2, boolean var3) {
      if (var0 == null) {
         var0.method12611(var1);
      }

      var0.field6738 = -931684775 * var1;
      var0.field6734 = 1048815237 * var2;
      classDD.method2988(16711680);
      if (var3) {
         var0.method12482(1429288549);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;I)V")
   @ObfuscatedName("ab")
   void method10781(Component var1, int var2) {
      try {
         var1.addMouseWheelListener(this);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tc.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;I)V")
   @ObfuscatedName("ag")
   void method10785(Component var1, int var2) {
      try {
         var1.removeMouseWheelListener(this);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tc.ag(" + ')');
      }
   }

   @Override
   public synchronized void mouseWheelMoved(MouseWheelEvent var1) {
      var1 = classOE.field4841.getCallbacks().mouseWheelMoved(var1);
      if (!var1.isConsumed()) {
         MouseWheelEvent var3 = var1;
         classTC var2 = this;

         try {
            var2.field6116 = var2.field6116 + var3.getWheelRotation() * 842598801;
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "tc.mouseWheelMoved(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   @Override
   public synchronized int vmethod528(int var1) {
      try {
         int var2 = this.field6116 * -1674913935;
         this.field6116 = 0;
         return var2;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tc.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public synchronized int vmethod529() {
      int var1 = this.field6116 * -1674913935;
      this.field6116 = 0;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public synchronized int vmethod527() {
      int var1 = this.field6116 * -1674913935;
      this.field6116 = 0;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("as")
   void method10782(Component var1) {
      var1.addMouseWheelListener(this);
   }

   classTC() {
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("ax")
   void method10783(Component var1) {
      var1.addMouseWheelListener(this);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("ac")
   void method10784(Component var1) {
      var1.addMouseWheelListener(this);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Component;)V")
   @ObfuscatedName("ao")
   void method10786(Component var1) {
      var1.removeMouseWheelListener(this);
   }
}
