import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nn")
public class Deque2 implements net.runelite.api.Deque {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("az")
   public Node field4689 = new Node();
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("af")
   Node field4690;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public void method7941() {
      while (true) {
         Node var1 = this.field4689.field6552;
         if (var1 == this.field4689) {
            this.field4690 = null;
            return;
         }

         var1.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("aa")
   public Node method7965() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ae")
   public void method7946(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4689.field6551;
      var1.field6552 = this.field4689;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   public void clear() {
      method7942(this);
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ab")
   public void method7949(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4689;
      var1.field6552 = this.field4689.field6552;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ag")
   public Node method7951() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   public Deque2() {
      this.field4689.field6552 = this.field4689;
      this.field4689.field6551 = this.field4689;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ax")
   public Node method7957() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnn;)Lvq;")
   @ObfuscatedName("de")
   public static Node method7968(Deque2 var0) {
      if (var0 == null) {
         return var0.method7972();
      } else {
         Node var1 = var0.field4690;
         if (var1 == var0.field4689) {
            var0.field4690 = null;
            return null;
         } else {
            var0.field4690 = var1.field6551;
            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ch")
   public void method7974(Node var1) {
      this.method7946(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ar")
   public Node method7952() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnn;)Lvq;")
   @ObfuscatedName("ol")
   public static Node method7961(Deque2 var0) {
      Node var1 = var0.field4689.field6551;
      if (var1 == var0.field4689) {
         var0.field4690 = null;
         return null;
      } else {
         var0.field4690 = var1.field6551;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("aw")
   public Node method7953() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnn;)V")
   @ObfuscatedName("lk")
   public static void method7942(Deque2 var0) {
      if (var0 == null) {
         var0.method7945();
      } else {
         while (true) {
            Node var1 = var0.field4689.field6552;
            if (var1 == var0.field4689) {
               var0.field4690 = null;
               return;
            }

            var1.vmethod398();
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;Lvq;)V")
   @ObfuscatedName("az")
   public static void method7939(Node var0, Node var1) {
      if (var0.field6551 != null) {
         var0.vmethod398();
      }

      var0.field6551 = var1.field6551;
      var0.field6552 = var1;
      var0.field6551.field6552 = var0;
      var0.field6552.field6551 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnn;)Lvq;")
   @ObfuscatedName("tf")
   public static Node method7955(Deque2 var0) {
      Node var1 = var0.field4689.field6551;
      if (var1 == var0.field4689) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public void method7943() {
      while (true) {
         Node var1 = this.field4689.field6552;
         if (var1 == this.field4689) {
            this.field4690 = null;
            return;
         }

         var1.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method7944() {
      while (true) {
         Node var1 = this.field4689.field6552;
         if (var1 == this.field4689) {
            this.field4690 = null;
            return;
         }

         var1.vmethod398();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("el")
   public Node method7975() {
      return method7962(this);
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ad")
   public void method7947(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4689.field6551;
      var1.field6552 = this.field4689;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;)V")
   @ObfuscatedName("ap")
   public void method7948(Node var1) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      var1.field6551 = this.field4689.field6551;
      var1.field6552 = this.field4689;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("bp")
   public Node method7966() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ak")
   public Node method7954() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("bt")
   public Node method7969() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6551;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnn;)Lvq;")
   @ObfuscatedName("kx")
   public static Node method7962(Deque2 var0) {
      if (var0 == null) {
         var0.method7967();
      }

      Node var1 = var0.field4689.field6551;
      if (var1 == var0.field4689) {
         var0.field4690 = null;
         return null;
      } else {
         var0.field4690 = var1.field6551;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("at")
   public Node method7956() {
      Node var1 = this.field4689.field6551;
      if (var1 == this.field4689) {
         return null;
      } else {
         var1.vmethod398();
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnn;)V")
   @ObfuscatedName("aq")
   public void method7973(Deque2 var1) {
      Node var2 = this.field4689;
      Node var3 = this.field4690;
      this.field4689 = var1.field4689;
      this.field4690 = var1.field4690;
      var1.field4689 = var2;
      var1.field4690 = var3;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("qf")
   public Node method7976() {
      return this.method7957();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("an")
   public Node method7958() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("am")
   public Node method7959() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ah")
   public Node method7960() {
      Node var1 = this.field4689.field6552;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("bc")
   public Node method7963() {
      Node var1 = this.field4689.field6551;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6551;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("br")
   public Node method7970() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6551;
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnn;Lvq;)V")
   @ObfuscatedName("dr")
   public static void method7950(Deque2 var0, Node var1) {
      if (var0 == null) {
         var0.method7947(var1);
      } else {
         if (var1.field6551 != null) {
            var1.vmethod398();
         }

         var1.field6551 = var0.field4689;
         var1.field6552 = var0.field4689.field6552;
         var1.field6551.field6552 = var1;
         var1.field6552.field6551 = var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvq;Lvq;)V")
   @ObfuscatedName("al")
   public static void method7940(Node var0, Node var1) {
      if (var0.field6551 != null) {
         var0.vmethod398();
      }

      var0.field6551 = var1.field6551;
      var0.field6552 = var1;
      var0.field6551.field6552 = var0;
      var0.field6552.field6551 = var0;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("bm")
   public Node method7971() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6551;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("fd")
   public Node method7977() {
      return this.method7965();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("bn")
   public Node method7964() {
      Node var1 = this.field4689.field6551;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6551;
         return var1;
      }
   }

   public Iterator iterator() {
      return new rl11(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method7945() {
      while (true) {
         Node var1 = this.field4689.field6552;
         if (var1 == this.field4689) {
            this.field4690 = null;
            return;
         }

         var1.vmethod401();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ac")
   public Node method7967() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6552;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ao")
   public Node method7972() {
      Node var1 = this.field4690;
      if (var1 == this.field4689) {
         this.field4690 = null;
         return null;
      } else {
         this.field4690 = var1.field6551;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("tg")
   public Node method7978() {
      return this.method7951();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("as")
   public Node method7979() {
      return method7962(this);
   }
}
