import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gt")
public class classGT implements classXZ {
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field2681 = 512;
   @ObfuscatedSignature(descriptor = "Lgt;")
   @ObfuscatedName("af")
   public static final classGT field2677 = new classGT((byte)0);
   @ObfuscatedSignature(descriptor = "Lgt;")
   @ObfuscatedName("ae")
   public static final classGT field2678 = new classGT((byte)1);
   @ObfuscatedName("pp")
   static int field2683;
   @ObfuscatedName("ag")
   final byte field2680;
   @ObfuscatedSignature(descriptor = "Lgt;")
   @ObfuscatedName("az")
   public static final classGT field2676 = new classGT((byte)-1);
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   static final int field2682 = 6;
   @ObfuscatedSignature(descriptor = "Lgt;")
   @ObfuscatedName("ab")
   public static final classGT field2679 = new classGT((byte)2);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field2680;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field2680;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gt.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lgt;")
   @ObfuscatedName("ax")
   public static classGT[] method5775() {
      return new classGT[]{field2679, field2678, field2677, field2676};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field2680;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field2680;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lgt;")
   @ObfuscatedName("as")
   public static classGT[] method5776() {
      return new classGT[]{field2679, field2678, field2677, field2676};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Laap;)Z")
   @ObfuscatedName("jm")
   public static boolean method5777(AccountType var0) {
      if (var0 == null) {
         var0.method226();
      }

      return AccountType.field92 == var0;
   }

   classGT(byte var1) {
      this.field2680 = var1;
   }
}
