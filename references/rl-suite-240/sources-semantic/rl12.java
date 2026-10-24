import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl12")
class rl12 extends ComponentAdapter {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldw;)V")
   @ObfuscatedName("ly")
   public static void method10083(AmbientSoundEffect var0) {
      if (var0.field1651 != null) {
         classWD var1 = var0.field1651.field6685;
         if (null != var0.field1650) {
            var0.field1650.method12362(0, classWC.method12367(var0.field1650, 1338975808), var1.method12404((byte)60), var1.method12402(255981186), -875112930);
            var0.field1650.method12365((byte)-54);
         }

         if (null != var0.field1649) {
            var0.field1649
               .method12362(0, classWC.method12367(var0.field1649, -1909691864), var1.method12404((byte)-22), var1.method12402(-689636019), 1811085276);
            var0.field1649.method12365((byte)19);
         }
      }
   }

   @Override
   public void componentResized(ComponentEvent var1) {
      this.field5694.field6218 = this.field5694.field6217.getSize();
   }

   public rl12(GameEngine var1) {
      this.field5694 = var1;
   }
}
