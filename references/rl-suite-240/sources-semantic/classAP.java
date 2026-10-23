import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ap")
public interface classAP {
   @ObfuscatedSignature(descriptor = "(I)Lak;")
   @ObfuscatedName("az")
   classAK vmethod66(int var1);

   @ObfuscatedSignature(descriptor = "()Lak;")
   @ObfuscatedName("ag")
   classAK vmethod67();

   @ObfuscatedSignature(descriptor = "()Lak;")
   @ObfuscatedName("ae")
   classAK vmethod68();

   @ObfuscatedSignature(descriptor = "()Lak;")
   @ObfuscatedName("ab")
   classAK vmethod69();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxy;)B")
   @ObfuscatedName("hh")
   static byte method645(classXY var0) {
      if (var0 == null) {
         var0.method13257();
      }

      return var0.field6952[(var0.field6953 += -1095856699) * 702114061 - 1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;)I")
   @ObfuscatedName("uj")
   static int method644(classPV var0) {
      return 1056232063 * var0.field5437;
   }

   @ObfuscatedSignature(descriptor = "()Lak;")
   @ObfuscatedName("af")
   classAK vmethod70();
}
