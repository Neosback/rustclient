import java.lang.ref.SoftReference;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ju")
public class classJU extends classJQ {
   @ObfuscatedName("az")
   SoftReference field3488;

   classJU(Object var1, int var2) {
      super(var2);
      this.field3488 = new SoftReference<>(var1);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("as")
   @Override
   boolean vmethod517() {
      return true;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   @Override
   boolean vmethod516() {
      return true;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ae")
   @Override
   Object vmethod514() {
      return this.field3488.get();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ab")
   @Override
   Object vmethod512() {
      return this.field3488.get();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ag")
   @Override
   Object vmethod515() {
      return this.field3488.get();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   @Override
   Object vmethod513() {
      return this.field3488.get();
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ax")
   @Override
   boolean vmethod518() {
      return true;
   }
}
