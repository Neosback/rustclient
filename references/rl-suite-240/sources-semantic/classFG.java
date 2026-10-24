import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("fg")
public interface classFG {
   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ay")
   int[] vmethod407(int var1);

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ab")
   int vmethod408(int var1, int var2);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lma;)Lmu;")
   @ObfuscatedName("ll")
   static classMU method4702(classMA var0) {
      return var0 == null ? var0.method7473() : var0.field4458;
   }

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ag")
   boolean vmethod412(int var1, int var2);

   @ObfuscatedSignature(descriptor = "(IB)[I")
   @ObfuscatedName("ae")
   int[] vmethod402(int var1, byte var2);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ub")
   default int method4703(int var1) {
      return this.vmethod408(var1, -1340787172);
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("aq")
   int[] vmethod404(int var1);

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ad")
   int[] vmethod403(int var1);

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("ap")
   int[] vmethod405(int var1);

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("au")
   int[] vmethod406(int var1);

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ak")
   boolean vmethod413(int var1);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   int vmethod409(int var1);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   int vmethod411(int var1);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aw")
   int vmethod410(int var1);
}
