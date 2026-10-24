import net.runelite.api.CameraFocusableEntity;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yb")
public interface classYB extends CameraFocusableEntity {
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   int vmethod259();

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("im")
   float vmethod382();

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ea")
   int vmethod371(int var1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("vv")
   default int vmethod386() {
      return this.vmethod258((byte)-13);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("te")
   default int vmethod384() {
      return this.vmethod368(311606126);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bl")
   default int vmethod385() {
      return this.vmethod371(-267974568);
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ey")
   int vmethod368(int var1);

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   int vmethod258(byte var1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hq")
   int vmethod369();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("kh")
   int vmethod372();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwd;)Lzl;")
   @ObfuscatedName("vv")
   static classZL method13350(classWD var0) {
      return var0.field6666;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsm;I)Lsb;")
   @ObfuscatedName("dq")
   static classSB method13351(classSM var0, int var1) {
      return var0.field5975[var1];
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("si")
   float vmethod383();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ky")
   int vmethod370();
}
