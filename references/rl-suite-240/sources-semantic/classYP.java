import java.awt.Graphics;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yp")
final class classYP implements classYH {
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final float field7022 = 325.9493F;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field7023 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field7024 = 12;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field7025 = 31;
   @ObfuscatedSignature(descriptor = "Lkx;")
   @ObfuscatedName("gc")
   static classKX field7026;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqb;)Ljava/lang/String;")
   @ObfuscatedName("yq")
   public static String method13651(classQB var0) {
      return var0.field5511;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod657(Object var1, classXY var2) {
      throw new UnsupportedOperationException("Cannot serialise this object type");
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpz;)Ljava/lang/Object;")
   @ObfuscatedName("mv")
   public static Object method13653(classPZ var0) {
      return var0.field5495 * 287626351 == 0 ? null : var0.field5493[0].field5222;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;I)V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod656(Object var1, classXY var2, int var3) {
      try {
         throw new UnsupportedOperationException("Cannot serialise this object type");
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yp.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)Ljava/lang/Object;")
   @ObfuscatedName("af")
   @Override
   public Object vmethod653(classXY var1, byte var2) {
      try {
         throw new UnsupportedOperationException("Cannot deserialise this object type");
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "yp.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ax")
   @Override
   public Object vmethod655(classXY var1) {
      throw new UnsupportedOperationException("Cannot deserialise this object type");
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltd;Ljava/awt/Graphics;)V")
   @ObfuscatedName("hk")
   public static void method13652(classTD var0, Graphics var1) {
      var0.field6117.update(var1);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   @Override
   public Object vmethod654(classXY var1) {
      throw new UnsupportedOperationException("Cannot deserialise this object type");
   }
}
