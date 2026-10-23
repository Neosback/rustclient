import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dq")
final class classDQ implements classLM {
   @ObfuscatedName("ab")
   public static String field1602;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field1599 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   public static final int field1600 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field1598 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("bb")
   static final int field1601 = 36;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod147(int var1) {
      try {
         if (this.val$item.method7343(939136870).field4424 != null) {
            if (var1 >= -1750661258) {
               throw new IllegalStateException();
            }

            ScriptEvent var2 = classUQ.method11527(this.val$item, 1964705240)
               .method14195(this.val$item.method7343(-450775735).field4424, (byte)2)
               .method14226((byte)65);
            client.field824.method9709(var2, 1697206599);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dq.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   @Override
   public void vmethod151() {
      if (this.val$item.method7343(-191908874).field4424 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 2136518866)
            .method14195(this.val$item.method7343(1128949515).field4424, (byte)2)
            .method14226((byte)62);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   @Override
   public void vmethod148() {
      if (this.val$item.method7343(1442863420).field4424 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 169726857)
            .method14195(this.val$item.method7343(580253534).field4424, (byte)2)
            .method14226((byte)2);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod149() {
      if (this.val$item.method7343(-43080985).field4424 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 2074542754)
            .method14195(this.val$item.method7343(1047276279).field4424, (byte)2)
            .method14226((byte)87);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   @Override
   public void vmethod150() {
      if (this.val$item.method7343(-1358463930).field4424 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 659724188)
            .method14195(this.val$item.method7343(65186911).field4424, (byte)2)
            .method14226((byte)27);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lxk;Lxs;)V")
   @ObfuscatedName("ox")
   public static void method3535(classXK var0, PacketBuffer var1) {
      if (var0 == null) {
         var0.method12879();
      }

      var0.method12895(var1, 4, 13, 13, -78621124);
   }

   classDQ(Widget var1) {
      this.val$item = var1;
   }
}
