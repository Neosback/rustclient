import java.util.Iterator;
import java.util.NoSuchElementException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("rl11")
class rl11 implements Iterator {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("kh")
   public Node field5693;

   @Override
   public boolean hasNext() {
      return this.field5693 != this.field5692.field4689;
   }

   @Override
   public void remove() {
      Node var1 = this.field5693.method12136();
      if (var1 == this.field5692.field4689) {
         throw new IllegalStateException();
      } else {
         var1.method12135();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("lh")
   public Node method10082() {
      if (this.field5693 == this.field5692.field4689) {
         throw new NoSuchElementException();
      } else {
         Node var1 = this.field5693;
         this.field5693 = this.field5693.method12134();
         return var1;
      }
   }

   public rl11(Deque2 var1) {
      this.field5692 = var1;
      this.field5693 = this.field5692.field4689.method12134();
   }
}
