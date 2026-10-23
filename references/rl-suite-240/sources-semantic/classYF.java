import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yf")
final class classYF implements classYH {
   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzd;)I")
   @ObfuscatedName("xo")
   public static int method13379(classZD var0) {
      return var0 == null ? var0.method13964() : classZD.method13949(var0, (byte)100) / var0.field7168.field8;
   }

   @ObfuscatedSignature(descriptor = "(Lyf;Ljava/lang/Long;Lxy;I)V")
   @ObfuscatedName("vq")
   public static void method13375(classYF var0, Long var1, classXY var2, int var3) {
      if (var0 == null) {
         var0.method13378(var1, var2, var3);
      } else {
         try {
            classXY.method13047(var2, var1);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "yf.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Long;Lxy;)V")
   @ObfuscatedName("ab")
   void method13376(Long var1, classXY var2) {
      classXY.method13047(var2, var1);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)Ljava/lang/Object;")
   @ObfuscatedName("af")
   @Override
   public Object vmethod653(classXY var1, byte var2) {
      try {
         return classXY.method13115(var1, 1472908214);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "yf.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;I)V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod656(Object var1, classXY var2, int var3) {
      try {
         method13375(this, (Long)var1, var2, 1948608740);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yf.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Long;Lxy;)V")
   @ObfuscatedName("as")
   void method13377(Long var1, classXY var2) {
      classXY.method13047(var2, var1);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   @Override
   public Object vmethod654(classXY var1) {
      return classXY.method13115(var1, -1125198728);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ax")
   @Override
   public Object vmethod655(classXY var1) {
      return classXY.method13115(var1, -630621740);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod657(Object var1, classXY var2) {
      method13375(this, (Long)var1, var2, 1948608740);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Long;Lxy;I)V")
   @ObfuscatedName("az")
   void method13378(Long var1, classXY var2, int var3) {
      try {
         classXY.method13047(var2, var1);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yf.az(" + ')');
      }
   }
}
