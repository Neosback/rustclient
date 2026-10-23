import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yu")
public class classYU implements Iterator {
   @ObfuscatedName("ae")
   int field7088;
   @ObfuscatedSignature(descriptor = "Lyk;")
   @ObfuscatedName("az")
   IndexedObjectSet field7086;
   @ObfuscatedSignature(descriptor = "Lvo;")
   @ObfuscatedName("ab")
   classVO field7087 = null;
   @ObfuscatedSignature(descriptor = "Lvo;")
   @ObfuscatedName("af")
   classVO field7089;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method13692() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         return true;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            if (this.field7086.field6994[this.field7088++].field6529 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = this.field7086.field6994[this.field7088 - 1].field6529;
               return true;
            }

            this.field7089 = this.field7086.field6994[this.field7088 - 1];
         }

         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyc;Lrr;Lrr;)I")
   @ObfuscatedName("qc")
   public static int method13685(classYC var0, Nameable var1, Nameable var2) {
      return var0.field6963 ? var1.vmethod562(var2, -30374874) : var2.vmethod562(var1, -30374874);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   void method13686() {
      this.field7089 = this.field7086.field6994[0].field6529;
      this.field7088 = 1;
      this.field7087 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   public void method13696() {
      if (this.field7087 == null) {
         throw new IllegalStateException();
      } else {
         this.field7087.method12078();
         this.field7087 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method13697() {
      if (this.field7087 == null) {
         throw new IllegalStateException();
      } else {
         this.field7087.method12078();
         this.field7087 = null;
      }
   }

   @Override
   public void remove() {
      if (this.field7087 == null) {
         throw new IllegalStateException();
      } else {
         this.field7087.method12078();
         this.field7087 = null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyu;)Ljava/lang/Object;")
   @ObfuscatedName("dy")
   public static Object method13688(classYU var0) {
      if (var0.field7089 != var0.field7086.field6994[var0.field7088 - 1]) {
         classVO var2 = var0.field7089;
         var0.field7089 = var2.field6529;
         var0.field7087 = var2;
         return var2;
      } else {
         while (var0.field7088 < var0.field7086.field6995) {
            classVO var1 = var0.field7086.field6994[var0.field7088++].field6529;
            if (var1 != var0.field7086.field6994[var0.field7088 - 1]) {
               var0.field7089 = var1.field6529;
               var0.field7087 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("aj")
   public Object method13689() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         classVO var2 = this.field7089;
         this.field7089 = var2.field6529;
         this.field7087 = var2;
         return var2;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            classVO var1 = this.field7086.field6994[this.field7088++].field6529;
            if (var1 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = var1.field6529;
               this.field7087 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ao")
   public Object method13690() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         classVO var2 = this.field7089;
         this.field7089 = var2.field6529;
         this.field7087 = var2;
         return var2;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            classVO var1 = this.field7086.field6994[this.field7088++].field6529;
            if (var1 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = var1.field6529;
               this.field7087 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method13693() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         return true;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            if (this.field7086.field6994[this.field7088++].field6529 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = this.field7086.field6994[this.field7088 - 1].field6529;
               return true;
            }

            this.field7089 = this.field7086.field6994[this.field7088 - 1];
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvo;")
   @ObfuscatedName("cd")
   public classVO method13698() {
      return (classVO)this.next();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   void method13687() {
      this.field7089 = this.field7086.field6994[0].field6529;
      this.field7088 = 1;
      this.field7087 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method13694() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         return true;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            if (this.field7086.field6994[this.field7088++].field6529 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = this.field7086.field6994[this.field7088 - 1].field6529;
               return true;
            }

            this.field7089 = this.field7086.field6994[this.field7088 - 1];
         }

         return false;
      }
   }

   @Override
   public boolean hasNext() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         return true;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            if (this.field7086.field6994[this.field7088++].field6529 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = this.field7086.field6994[this.field7088 - 1].field6529;
               return true;
            }

            this.field7089 = this.field7086.field6994[this.field7088 - 1];
         }

         return false;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyu;)Z")
   @ObfuscatedName("cj")
   public static boolean method13695(classYU var0) {
      if (var0.field7089 != var0.field7086.field6994[var0.field7088 - 1]) {
         return true;
      } else {
         while (var0.field7088 < var0.field7086.field6995) {
            if (var0.field7086.field6994[var0.field7088++].field6529 != var0.field7086.field6994[var0.field7088 - 1]) {
               var0.field7089 = var0.field7086.field6994[var0.field7088 - 1].field6529;
               return true;
            }

            var0.field7089 = var0.field7086.field6994[var0.field7088 - 1];
         }

         return false;
      }
   }

   classYU(IndexedObjectSet var1) {
      this.field7086 = var1;
      this.method13686();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method13691() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         classVO var2 = this.field7089;
         this.field7089 = var2.field6529;
         this.field7087 = var2;
         return var2;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            classVO var1 = this.field7086.field6994[this.field7088++].field6529;
            if (var1 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = var1.field6529;
               this.field7087 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @Override
   public Object next() {
      if (this.field7089 != this.field7086.field6994[this.field7088 - 1]) {
         classVO var2 = this.field7089;
         this.field7089 = var2.field6529;
         this.field7087 = var2;
         return var2;
      } else {
         while (this.field7088 < this.field7086.field6995) {
            classVO var1 = this.field7086.field6994[this.field7088++].field6529;
            if (var1 != this.field7086.field6994[this.field7088 - 1]) {
               this.field7089 = var1.field6529;
               this.field7087 = var1;
               return var1;
            }
         }

         return null;
      }
   }
}
