import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ya")
public class classYA implements Iterator {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("af")
   Node field6962;
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("az")
   IterableHashTable field6960;
   @ObfuscatedName("ae")
   int field6961;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("ab")
   Node field6959 = null;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   void method13330() {
      this.field6962 = this.field6960.field7015[0].field6552;
      this.field6961 = 1;
      this.field6959 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method13348() {
      if (this.field6959 == null) {
         throw new IllegalStateException();
      } else {
         this.field6959.vmethod398();
         this.field6959 = null;
      }
   }

   public classYA(IterableHashTable var1) {
      this.field6960 = var1;
      this.method13331();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   void method13331() {
      this.field6962 = this.field6960.field7015[0].field6552;
      this.field6961 = 1;
      this.field6959 = null;
   }

   public classYA() {
   }

   @Override
   public Object next() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         Node var2 = this.field6962;
         this.field6962 = var2.field6552;
         this.field6959 = var2;
         return var2;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            Node var1 = this.field6960.field7015[this.field6961++].field6552;
            if (var1 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = var1.field6552;
               this.field6959 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @Override
   public boolean hasNext() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         return true;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            if (this.field6960.field7015[this.field6961++].field6552 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = this.field6960.field7015[this.field6961 - 1].field6552;
               return true;
            }

            this.field6962 = this.field6960.field7015[this.field6961 - 1];
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   void method13332() {
      this.field6962 = this.field6960.field7015[0].field6552;
      this.field6961 = 1;
      this.field6959 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   public void method13349() {
      if (this.field6959 == null) {
         throw new IllegalStateException();
      } else {
         this.field6959.vmethod398();
         this.field6959 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("aj")
   public Object method13340() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         Node var2 = this.field6962;
         this.field6962 = var2.field6552;
         this.field6959 = var2;
         return var2;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            Node var1 = this.field6960.field7015[this.field6961++].field6552;
            if (var1 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = var1.field6552;
               this.field6959 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lya;)Ljava/lang/Object;")
   @ObfuscatedName("pf")
   public static Object method13341(classYA var0) {
      if (var0.field6962 != var0.field6960.field7015[var0.field6961 - 1]) {
         Node var2 = var0.field6962;
         var0.field6962 = var2.field6552;
         var0.field6959 = var2;
         return var2;
      } else {
         while (var0.field6961 < var0.field6960.field7016) {
            Node var1 = var0.field6960.field7015[var0.field6961++].field6552;
            if (var1 != var0.field6960.field7015[var0.field6961 - 1]) {
               var0.field6962 = var1.field6552;
               var0.field6959 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ay")
   public Object method13342() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         Node var2 = this.field6962;
         this.field6962 = var2.field6552;
         this.field6959 = var2;
         return var2;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            Node var1 = this.field6960.field7015[this.field6961++].field6552;
            if (var1 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = var1.field6552;
               this.field6959 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method13344() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         return true;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            if (this.field6960.field7015[this.field6961++].field6552 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = this.field6960.field7015[this.field6961 - 1].field6552;
               return true;
            }

            this.field6962 = this.field6960.field7015[this.field6961 - 1];
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ae")
   public Node method13336() {
      this.method13331();
      return (Node)this.next();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   void method13333() {
      this.field6962 = this.field6960.field7015[0].field6552;
      this.field6961 = 1;
      this.field6959 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method13345() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         return true;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            if (this.field6960.field7015[this.field6961++].field6552 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = this.field6960.field7015[this.field6961 - 1].field6552;
               return true;
            }

            this.field6962 = this.field6960.field7015[this.field6961 - 1];
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method13343() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         Node var2 = this.field6962;
         this.field6962 = var2.field6552;
         this.field6959 = var2;
         return var2;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            Node var1 = this.field6960.field7015[this.field6961++].field6552;
            if (var1 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = var1.field6552;
               this.field6959 = var1;
               return var1;
            }
         }

         return null;
      }
   }

   @Override
   public void remove() {
      if (this.field6959 == null) {
         throw new IllegalStateException();
      } else {
         this.field6959.vmethod398();
         this.field6959 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method13346() {
      if (this.field6962 != this.field6960.field7015[this.field6961 - 1]) {
         return true;
      } else {
         while (this.field6961 < this.field6960.field7016) {
            if (this.field6960.field7015[this.field6961++].field6552 != this.field6960.field7015[this.field6961 - 1]) {
               this.field6962 = this.field6960.field7015[this.field6961 - 1].field6552;
               return true;
            }

            this.field6962 = this.field6960.field7015[this.field6961 - 1];
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ag")
   void method13334() {
      this.field6962 = this.field6960.field7015[0].field6552;
      this.field6961 = 1;
      this.field6959 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   void method13335() {
      this.field6962 = this.field6960.field7015[0].field6552;
      this.field6961 = 1;
      this.field6959 = null;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("aa")
   public Node method13337() {
      this.method13331();
      return (Node)this.next();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("aw")
   public Node method13338() {
      this.method13331();
      return (Node)this.next();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ak")
   public Node method13339() {
      this.method13331();
      return (Node)this.next();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lya;)Z")
   @ObfuscatedName("tc")
   public static boolean method13347(classYA var0) {
      if (var0.field6962 != var0.field6960.field7015[var0.field6961 - 1]) {
         return true;
      } else {
         while (var0.field6961 < var0.field6960.field7016) {
            if (var0.field6960.field7015[var0.field6961++].field6552 != var0.field6960.field7015[var0.field6961 - 1]) {
               var0.field6962 = var0.field6960.field7015[var0.field6961 - 1].field6552;
               return true;
            }

            var0.field6962 = var0.field6960.field7015[var0.field6961 - 1];
         }

         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lyn;)V")
   @ObfuscatedName("az")
   public void method13329(IterableHashTable var1) {
      this.field6960 = var1;
      this.method13333();
   }
}
