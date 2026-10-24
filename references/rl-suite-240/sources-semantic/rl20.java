import com.google.common.collect.Iterators;
import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl20")
class rl20 implements net.runelite.api.IndexedObjectSet {
   @ObfuscatedSignature(descriptor = "(I)Ldz;")
   @ObfuscatedName("ng")
   public WorldView method10097(int var1) {
      WorldEntity var2 = (WorldEntity)this.field5719.field1698.method13634(var1);
      return var2 == null ? null : var2.field6010;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqq;)Z")
   @ObfuscatedName("qj")
   public static boolean method10095(classQQ var0) {
      if (var0 == null) {
         var0.method9753();
      }

      return var0.field5593;
   }

   public Iterator iterator() {
      return Iterators.transform(this.field5719.field1698.iterator(), var0 -> var0.field6010);
   }

   @ObfuscatedSignature(descriptor = "(Lfx;)I")
   @ObfuscatedName("ka")
   public static int method10096(Model var0) {
      if (var0 == null) {
         return var0.method5128();
      } else {
         Model.method5254(var0);
         return var0.field2488;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyc;Ljava/lang/Object;Ljava/lang/Object;)I")
   @ObfuscatedName("ve")
   public static int method10094(classYC var0, Object var1, Object var2) {
      return var0 == null ? var0.compare(var1, var1) : var0.method13352((Nameable)var1, (Nameable)var2, (byte)49);
   }

   public rl20(WorldView var1) {
      this.field5719 = var1;
   }
}
