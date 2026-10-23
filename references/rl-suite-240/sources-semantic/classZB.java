import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zb")
final class classZB implements classYH {
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final int field7144 = 200;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field7143 = 1;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;I)V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod656(Object var1, classXY var2, int var3) {
      try {
         this.method13867((String)var1, var2, 601017928);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "zb.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lxy;I)V")
   @ObfuscatedName("az")
   void method13867(String var1, classXY var2, int var3) {
      try {
         var2.method13054(var1, (short)2569);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "zb.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lxy;)V")
   @ObfuscatedName("ao")
   void method13868(String var1, classXY var2) {
      var2.method13054(var1, (short)21261);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)Ljava/lang/Object;")
   @ObfuscatedName("af")
   @Override
   public Object vmethod653(classXY var1, byte var2) {
      try {
         return var1.method13127(793584546);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "zb.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   @Override
   public Object vmethod654(classXY var1) {
      return var1.method13127(-413073463);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ax")
   @Override
   public Object vmethod655(classXY var1) {
      return var1.method13127(1201931987);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lxy;)V")
   @ObfuscatedName("ab")
   void method13869(String var1, classXY var2) {
      var2.method13054(var1, (short)9466);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod657(Object var1, classXY var2) {
      this.method13867((String)var1, var2, 601017928);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Lxy;)V")
   @ObfuscatedName("as")
   void method13870(String var1, classXY var2) {
      var2.method13054(var1, (short)13128);
   }
}
