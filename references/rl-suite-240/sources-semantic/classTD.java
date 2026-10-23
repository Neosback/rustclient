import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("td")
public final class classTD extends Canvas {
   @ObfuscatedName("az")
   Component field6117;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final int field6119 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   public static final int field6118 = 42;

   classTD(Component var1) {
      this.field6117 = var1;
   }

   @Override
   public final void update(Graphics var1) {
      try {
         this.field6117.update(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "td.update(" + ')');
      }
   }

   @Override
   public final void paint(Graphics var1) {
      try {
         this.field6117.paint(var1);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "td.paint(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;)V")
   @ObfuscatedName("afk")
   public final void method10788(Graphics var1) {
      this.field6117.update(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldv;)I")
   @ObfuscatedName("qe")
   public static int method10789(Hitsplat var0) {
      return var0.field1633 * 1682911625;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/awt/Graphics;)V")
   @ObfuscatedName("afq")
   public final void method10790(Graphics var1) {
      this.field6117.paint(var1);
   }
}
