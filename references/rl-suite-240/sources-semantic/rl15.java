import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rl15")
class rl15 implements Iterator {
   @ObfuscatedSignature(descriptor = "Lvm;")
   @ObfuscatedName("dc")
   public classVM field5699;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqi;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("mu")
   public static void method10085(classQI var0, String var1, String var2) {
      var0.field5546.put(var1, var2);
   }

   @Override
   public void remove() {
      classVM var1 = this.field5699.method12074();
      if (var1 == this.field5698.field4677) {
         throw new IllegalStateException();
      } else {
         var1.method12076();
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgr;Lgl;Ljava/util/List;)V")
   @ObfuscatedName("xz")
   public static void method10086(WorldMapRegion var0, classGL var1, List var2) {
      var0.field2657.clear();
      var0.field2667 = var1;
      var0.method5670(var2, (byte)-122);
   }

   @Override
   public boolean hasNext() {
      return this.field5699 != this.field5698.field4677;
   }

   @ObfuscatedSignature(descriptor = "()Lrh;")
   @ObfuscatedName("qx")
   public PendingLogin method10087() {
      if (this.field5699 == this.field5698.field4677) {
         throw new NoSuchElementException();
      } else {
         PendingLogin var1 = (PendingLogin)this.field5699;
         this.field5699 = this.field5699.method12075();
         return var1;
      }
   }

   public rl15(Deque var1) {
      this.field5698 = var1;
      this.field5699 = this.field5698.field4677.method12075();
   }
}
