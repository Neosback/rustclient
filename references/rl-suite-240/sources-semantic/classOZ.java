import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("oz")
public interface classOZ {
   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("au")
   classOZ vmethod538(int var1);

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("af")
   int vmethod539(short var1);

   @ObfuscatedSignature(descriptor = "(II)Z")
   @ObfuscatedName("ae")
   boolean vmethod540(int var1, int var2);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   int vmethod541();

   @ObfuscatedSignature(descriptor = "(II)Loz;")
   @ObfuscatedName("ag")
   classOZ vmethod542(int var1, int var2);

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("as")
   boolean vmethod543();

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   boolean vmethod544();

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ac")
   boolean vmethod545();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Luq;Luq;)Z")
   @ObfuscatedName("gj")
   static boolean method8860(classUQ var0, classUQ var1) {
      return var0.field6366 * -1357616065 <= var1.method11564(549785370)
         && var1.method11558(1940515483) <= var0.method11564(1322764876)
         && 926655743 * var0.field6367 <= var1.method11568((byte)13)
         && classUQ.method11561(var1, (byte)-92) <= var0.method11568((byte)13);
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   boolean vmethod546(byte var1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   int vmethod547();

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aa")
   int vmethod548();

   @ObfuscatedSignature(descriptor = "(I[I)Ljava/lang/String;")
   @ObfuscatedName("aq")
   String vmethod549(int var1, int[] var2);

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ay")
   boolean vmethod550(int var1);

   @ObfuscatedSignature(descriptor = "(I[IB)Ljava/lang/String;")
   @ObfuscatedName("ab")
   String vmethod551(int var1, int[] var2, byte var3);

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   boolean vmethod552(int var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laae;)Z")
   @ObfuscatedName("ck")
   static boolean method8859(classAAE var0) {
      if (var0 == null) {
         var0.method167();
      }

      return var0.field41 != null;
   }

   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("ad")
   classOZ vmethod553(int var1);

   @ObfuscatedSignature(descriptor = "(I)Loz;")
   @ObfuscatedName("ap")
   classOZ vmethod554(int var1);
}
