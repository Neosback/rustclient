import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wi")
public interface classWI extends Iterable {
   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)V")
   @ObfuscatedName("ax")
   void vmethod639(int var1, Object var2);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;)I")
   @ObfuscatedName("mt")
   static int method12445(classGY var0) {
      return var0.field2740 * 729467839;
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;I)V")
   @ObfuscatedName("af")
   void vmethod640(int var1, Object var2, int var3);

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("az")
   int vmethod641(int var1, byte var2);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   int vmethod642(int var1);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ag")
   int vmethod643(int var1);

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)V")
   @ObfuscatedName("as")
   void vmethod644(int var1, Object var2);

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   int vmethod645(int var1);

   @ObfuscatedSignature(descriptor = "(ILjava/lang/Object;)V")
   @ObfuscatedName("ac")
   void vmethod646(int var1, Object var2);
}
