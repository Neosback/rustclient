import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ca")
final class classCA implements classLM {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field635 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final String field637 = "clk";
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field636 = 3;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method1394(int var0) {
      try {
         EnumComposition.field4818.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ca.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("az")
   @Override
   public void vmethod147(int var1) {
      try {
         if (this.val$item.method7343(1681066350).field4426 != null) {
            ScriptEvent var2 = classUQ.method11527(this.val$item, 1351188757)
               .method14195(this.val$item.method7343(831022479).field4426, (byte)2)
               .method14226((byte)60);
            client.field824.method9709(var2, 1697206599);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ca.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   @Override
   public void vmethod148() {
      if (this.val$item.method7343(-1689102841).field4426 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 1798756905)
            .method14195(this.val$item.method7343(-1682057913).field4426, (byte)2)
            .method14226((byte)30);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod149() {
      if (this.val$item.method7343(-1868444860).field4426 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 1634000256)
            .method14195(this.val$item.method7343(1217704895).field4426, (byte)2)
            .method14226((byte)93);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   @Override
   public void vmethod150() {
      if (this.val$item.method7343(633115180).field4426 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 400907152)
            .method14195(this.val$item.method7343(-560912922).field4426, (byte)2)
            .method14226((byte)50);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   @Override
   public void vmethod151() {
      if (this.val$item.method7343(1727865402).field4426 != null) {
         ScriptEvent var1 = classUQ.method11527(this.val$item, 154611908)
            .method14195(this.val$item.method7343(-467736828).field4426, (byte)2)
            .method14226((byte)111);
         client.field824.method9709(var1, 1697206599);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lzs;")
   @ObfuscatedName("af")
   public static ScriptEventBuilder method1393(int var0, int var1) {
      try {
         ScriptEventBuilder var3 = new ScriptEventBuilder();
         var3.field7267 = 790618167 * var0;
         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ca.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(CLxo;B)I")
   @ObfuscatedName("ae")
   static int method1395(char var0, classXO var1, byte var2) {
      try {
         int var3;
         label32: {
            var3 = var0 << 4;
            if (!Character.isUpperCase(var0)) {
               if (var2 <= 46) {
                  throw new IllegalStateException();
               }

               if (!Character.isTitleCase(var0)) {
                  break label32;
               }

               if (var2 <= 46) {
                  throw new IllegalStateException();
               }
            }

            var0 = Character.toLowerCase(var0);
            var3 = 1 + (var0 << 4);
         }

         if (241 == var0 && classXO.field6892 == var1) {
            var3 = 1762;
         }

         return var3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ca.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("go")
   static final int method1396(int var0) {
      try {
         return classKF.field3569.vmethod393(826200091);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ca.go(" + 41);
      }
   }

   classCA(Widget var1) {
      this.val$item = var1;
   }
}
