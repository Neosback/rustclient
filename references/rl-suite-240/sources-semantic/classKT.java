import java.awt.Graphics;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kt")
public class classKT implements classXZ {
   @ObfuscatedSignature(descriptor = "Lkt;")
   @ObfuscatedName("ab")
   public static final classKT field4018 = new classKT(3);
   @ObfuscatedSignature(descriptor = "Lkt;")
   @ObfuscatedName("ae")
   static final classKT field4017 = new classKT(2);
   @ObfuscatedName("bq")
   static int field4020;
   @ObfuscatedSignature(descriptor = "Lkt;")
   @ObfuscatedName("af")
   static final classKT field4019 = new classKT(1);
   @ObfuscatedSignature(descriptor = "Lkt;")
   @ObfuscatedName("ag")
   static final classKT field4016 = new classKT(4);
   @ObfuscatedName("as")
   final int field4014;
   @ObfuscatedSignature(descriptor = "Lkt;")
   @ObfuscatedName("az")
   static final classKT field4015 = new classKT(0);

   classKT(int var1) {
      this.field4014 = var1 * 789673457;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field4014 * -183313647;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kt.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/awt/Graphics;)V")
   @ObfuscatedName("ub")
   public static void method6602(GameEngine var0, Graphics var1) {
      if (var0 == null) {
         var0.method11027();
      }

      var0.paint(var1);
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   public static int method6601(int var0, int var1) {
      try {
         return var0 >> 3 & 2047;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kt.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field4014 * -183313647;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field4014 * -183313647;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field4014 * -183313647;
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("af")
   public static int method6600(int var0, byte var1) {
      try {
         return var0 >>> 4 & classZK.field7190 * -145882333;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kt.af(" + 41);
      }
   }
}
