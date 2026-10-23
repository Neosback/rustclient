import java.util.Iterator;
import java.util.NoSuchElementException;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("rl14")
class rl14 implements Iterator {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("fv")
   public Node field5697;
   @ObfuscatedName("nl")
   public int field5696;

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("pz")
   public Node method10084() {
      if (this.field5696 > 0 && this.field5695.field6984[this.field5696 - 1] != this.field5697) {
         Node var3 = this.field5697;
         this.field5697 = var3.method12134();
         return var3;
      } else {
         while (this.field5696 < this.field5695.field6985) {
            Node var1 = this.field5695.field6984[this.field5696++];
            Node var2 = var1.method12134();
            if (var1 != var2) {
               this.field5697 = var2.method12134();
               return var2;
            }
         }

         throw new NoSuchElementException();
      }
   }

   @Override
   public boolean hasNext() {
      if (this.field5696 > 0 && this.field5695.field6984[this.field5696 - 1] != this.field5697) {
         return true;
      } else {
         for (int var1 = this.field5696; var1 < this.field5695.field6985; var1++) {
            Node var2 = this.field5695.field6984[var1];
            Node var3 = var2.method12134();
            if (var2 != var3) {
               return true;
            }
         }

         return false;
      }
   }

   public rl14(HashTable var1) {
      this.field5695 = var1;
   }
}
