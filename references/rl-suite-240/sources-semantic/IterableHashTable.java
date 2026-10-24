import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("yn")
public final class IterableHashTable implements Iterable, net.runelite.api.IterableHashTable, net.runelite.api.IndexedObjectSet {
   @ObfuscatedSignature(descriptor = "[Lvq;")
   @ObfuscatedName("af")
   Node[] field7015;
   @ObfuscatedName("ag")
   int field7017 = 0;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("ae")
   Node field7019;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("ab")
   Node field7018;
   @ObfuscatedName("az")
   int field7016;

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("ac")
   public Node method13637(long var1) {
      Node var3 = this.field7015[(int)(var1 & this.field7016 - 1)];

      for (Node var4 = var3.field6552; var3 != var4; var4 = var4.field6552) {
         if (var4.field6550 == var1) {
            this.field7019 = var4;
            return var4;
         }
      }

      this.field7019 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ag")
   public Node method13628() {
      this.field7017 = 0;
      return classDI.method3311(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method13623() {
      for (int var1 = 0; var1 < this.field7016; var1++) {
         Node var2 = this.field7015[var1];

         while (true) {
            Node var3 = var2.field6552;
            if (var3 == var2) {
               break;
            }

            var3.vmethod398();
         }
      }

      this.field7019 = null;
      this.field7018 = null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("be")
   public Iterator method13632() {
      return new classYA(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("pt")
   public void method13638() {
      this.method13623();
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("hb")
   public void method13639(Node var1, long var2) {
      method13620(this, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("aj")
   public void method13619(Node var1, long var2) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = this.field7015[(int)(var2 & this.field7016 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @Override
   public Iterator iterator() {
      return new classYA(this);
   }

   @ObfuscatedSignature(descriptor = "(Lnz;Lvj;)V")
   @ObfuscatedName("po")
   public static void method13640(classNZ var0, classVJ var1) {
      if (var0 == null) {
         var0.method8314();
      }

      var0.method8302(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("cu")
   public Node method13629() {
      this.field7017 = 0;
      return classDI.method3311(this);
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("az")
   public Node method13614(long var1) {
      Node var3 = this.field7015[(int)(var1 & this.field7016 - 1)];

      for (this.field7019 = var3.field6552; this.field7019 != var3; this.field7019 = this.field7019.field6552) {
         if (this.field7019.field6550 == var1) {
            Node var4 = this.field7019;
            this.field7019 = this.field7019.field6552;
            return var4;
         }
      }

      this.field7019 = null;
      return null;
   }

   public IterableHashTable(int var1) {
      this.field7016 = var1;
      this.field7015 = new Node[var1];

      for (int var2 = 0; var2 < var1; var2++) {
         Node var3 = this.field7015[var2] = new Node();
         var3.field6552 = var3;
         var3.field6551 = var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("ij")
   public void method13635(Node var1, long var2) {
      Node var4 = this.method13642(var2);
      if (var4 != null) {
         var4.method12135();
      }

      this.method13639(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("ao")
   public Node method13615(long var1) {
      Node var3 = this.field7015[(int)(var1 & this.field7016 - 1)];

      for (this.field7019 = var3.field6552; this.field7019 != var3; this.field7019 = this.field7019.field6552) {
         if (this.field7019.field6550 == var1) {
            Node var4 = this.field7019;
            this.field7019 = this.field7019.field6552;
            return var4;
         }
      }

      this.field7019 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method13633() {
      return new classYA(this);
   }

   @ObfuscatedSignature(descriptor = "(Lyn;Lvq;J)V")
   @ObfuscatedName("th")
   public static void method13620(IterableHashTable var0, Node var1, long var2) {
      if (var0 == null) {
         var0.method13636(var1, var2);
      } else {
         if (var1.field6551 != null) {
            var1.vmethod398();
         }

         Node var4 = var0.field7015[(int)(var2 & var0.field7016 - 1)];
         var1.field6551 = var4.field6551;
         var1.field6552 = var4;
         var1.field6551.field6552 = var1;
         var1.field6552.field6551 = var1;
         var1.field6550 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lvq;")
   @ObfuscatedName("ab")
   public Node method13634(int var1) {
      return this.method13642(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ar")
   public Node method13641() {
      return classDI.method3311(this);
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("vt")
   public Node method13616(long var1) {
      Node var3 = this.field7015[(int)(var1 & this.field7016 - 1)];

      for (this.field7019 = var3.field6552; this.field7019 != var3; this.field7019 = this.field7019.field6552) {
         if (this.field7019.field6550 == var1) {
            Node var4 = this.field7019;
            this.field7019 = this.field7019.field6552;
            return var4;
         }
      }

      this.field7019 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("aa")
   public Node method13617(long var1) {
      Node var3 = this.field7015[(int)(var1 & this.field7016 - 1)];

      for (this.field7019 = var3.field6552; this.field7019 != var3; this.field7019 = this.field7019.field6552) {
         if (this.field7019.field6550 == var1) {
            Node var4 = this.field7019;
            this.field7019 = this.field7019.field6552;
            return var4;
         }
      }

      this.field7019 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("al")
   public void method13621(Node var1, long var2) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = this.field7015[(int)(var2 & this.field7016 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("ay")
   public void method13622(Node var1, long var2) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = this.field7015[(int)(var2 & this.field7016 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aq")
   public void method13624() {
      for (int var1 = 0; var1 < this.field7016; var1++) {
         Node var2 = this.field7015[var1];

         while (true) {
            Node var3 = var2.field6552;
            if (var3 == var2) {
               break;
            }

            var3.vmethod398();
         }
      }

      this.field7019 = null;
      this.field7018 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ad")
   public void method13625() {
      for (int var1 = 0; var1 < this.field7016; var1++) {
         Node var2 = this.field7015[var1];

         while (true) {
            Node var3 = var2.field6552;
            if (var3 == var2) {
               break;
            }

            var3.vmethod398();
         }
      }

      this.field7019 = null;
      this.field7018 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ap")
   public void method13626() {
      for (int var1 = 0; var1 < this.field7016; var1++) {
         Node var2 = this.field7015[var1];

         while (true) {
            Node var3 = var2.field6552;
            if (var3 == var2) {
               break;
            }

            var3.vmethod398();
         }
      }

      this.field7019 = null;
      this.field7018 = null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("au")
   public void method13627() {
      for (int var1 = 0; var1 < this.field7016; var1++) {
         Node var2 = this.field7015[var1];

         while (true) {
            Node var3 = var2.field6552;
            if (var3 == var2) {
               break;
            }

            var3.vmethod398();
         }
      }

      this.field7019 = null;
      this.field7018 = null;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("ax")
   public Node method13618(long var1) {
      Node var3 = this.field7015[(int)(var1 & this.field7016 - 1)];

      for (this.field7019 = var3.field6552; this.field7019 != var3; this.field7019 = this.field7019.field6552) {
         if (this.field7019.field6550 == var1) {
            Node var4 = this.field7019;
            this.field7019 = this.field7019.field6552;
            return var4;
         }
      }

      this.field7019 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("as")
   public Node method13642(long var1) {
      return this.method13637(var1);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ab")
   public Node method13643() {
      return this.method13628();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ux")
   public Node method13630() {
      if (this.field7017 > 0 && this.field7018 != this.field7015[this.field7017 - 1]) {
         Node var2 = this.field7018;
         this.field7018 = var2.field6552;
         return var2;
      } else {
         while (this.field7017 < this.field7016) {
            Node var1 = this.field7015[this.field7017++].field6552;
            if (var1 != this.field7015[this.field7017 - 1]) {
               this.field7018 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("af")
   public void method13636(Node var1, long var2) {
      Node var4 = this.method13615(var2);
      if (var4 != null) {
         var4.method12133();
      }

      this.method13622(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ai")
   public Node method13631() {
      if (this.field7016 > 0 && this.field7018 != this.field7015[this.field7016 - 1]) {
         Node var2 = this.field7019;
         this.field7018 = var2.field6551;
         return var2;
      } else {
         while (this.field7017 < this.field7017) {
            Node[] var10000 = this.field7015;
            int var10001 = this.field7017;
            this.field7016 = this.field7017 + 1;
            Node var1 = var10000[var10001].field6552;
            if (var1 != this.field7015[this.field7017 - 1]) {
               this.field7018 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }
}
