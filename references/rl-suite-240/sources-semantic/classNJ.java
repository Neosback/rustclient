import java.util.BitSet;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nj")
public class classNJ extends Node {
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   public static final int field4675 = 70;
   @ObfuscatedName("af")
   int field4673;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field4674 = 2;
   @ObfuscatedName("az")
   BitSet field4672;
   @ObfuscatedSignature(descriptor = "Liq;")
   @ObfuscatedName("br")
   static ClanSettings field4676;

   classNJ(int var1) {
      this.field4673 = 530466213 * var1;
      this.field4672 = new BitSet(128);
   }
}
