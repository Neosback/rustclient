import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yi")
public final class HashTable implements net.runelite.api.HashTable {
   @ObfuscatedName("ag")
   int field6986 = 0;
   @ObfuscatedSignature(descriptor = "[Lvq;")
   @ObfuscatedName("af")
   Node[] field6984;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("ae")
   Node field6988;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("ab")
   Node field6987;
   @ObfuscatedName("az")
   int field6985;

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("tb")
   public void method13433(Node var1, long var2) {
      this.method13418(var1, var2);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ap")
   public Node method13422() {
      this.field6986 = 0;
      return this.method13427();
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("ae")
   public void method13418(Node var1, long var2) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = this.field6984[(int)(var2 & this.field6985 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("sc")
   public Node method13434(long var1) {
      return method13432(this, var1);
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ab")
   public Node method13423() {
      this.field6986 = 0;
      return this.method13427();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ag")
   public Node method13427() {
      if (this.field6986 > 0 && this.field6987 != this.field6984[this.field6986 - 1]) {
         Node var2 = this.field6987;
         this.field6987 = var2.field6552;
         return var2;
      } else {
         while (this.field6986 < this.field6985) {
            Node var1 = this.field6984[this.field6986++].field6552;
            if (var1 != this.field6984[this.field6986 - 1]) {
               this.field6987 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }

   public HashTable(int var1) {
      this.field6985 = var1;
      this.field6984 = new Node[var1];

      for (int var2 = 0; var2 < var1; var2++) {
         Node var3 = this.field6984[var2] = new Node();
         var3.field6552 = var3;
         var3.field6551 = var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("ax")
   public Node method13411(long var1) {
      Node var3 = this.field6984[(int)(var1 & this.field6985 - 1)];

      for (this.field6988 = var3.field6552; this.field6988 != var3; this.field6988 = this.field6988.field6552) {
         if (this.field6988.field6550 == var1) {
            Node var4 = this.field6988;
            this.field6988 = this.field6988.field6552;
            return var4;
         }
      }

      this.field6988 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("as")
   public Node method13412(long var1) {
      Node var3 = this.field6984[(int)(var1 & this.field6985 - 1)];

      for (this.field6988 = var3.field6552; this.field6988 != var3; this.field6988 = this.field6988.field6552) {
         if (this.field6988.field6550 == var1) {
            Node var4 = this.field6988;
            this.field6988 = this.field6988.field6552;
            return var4;
         }
      }

      this.field6988 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ad")
   public Node method13424() {
      this.field6986 = 0;
      return this.method13427();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ao")
   public int method13415() {
      int var1 = 0;

      for (int var2 = 0; var2 < this.field6985; var2++) {
         Node var3 = this.field6984[var2];

         for (Node var4 = var3.field6552; var4 != var3; var4 = var4.field6552) {
            var1++;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("al")
   public int method13416() {
      int var1 = 0;

      for (int var2 = 0; var2 < this.field6985; var2++) {
         Node var3 = this.field6984[var2];

         for (Node var4 = var3.field6552; var4 != var3; var4 = var4.field6552) {
            var1++;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lyi;J)Lvq;")
   @ObfuscatedName("kr")
   public static Node method13432(HashTable var0, long var1) {
      if (var0 == null) {
         var0.method13414(var1);
      }

      Node var3 = var0.field6984[(int)(var1 & var0.field6985 - 1)];

      for (Node var4 = var3.field6552; var3 != var4; var4 = var4.field6552) {
         if (var4.field6550 == var1) {
            return var4;
         }
      }

      return null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyi;J)Lvq;")
   @ObfuscatedName("ln")
   public static Node method13413(HashTable var0, long var1) {
      Node var3 = var0.field6984[(int)(var1 & var0.field6985 - 1)];

      for (var0.field6988 = var3.field6552; var0.field6988 != var3; var0.field6988 = var0.field6988.field6552) {
         if (var0.field6988.field6550 == var1) {
            Node var4 = var0.field6988;
            var0.field6988 = var0.field6988.field6552;
            return var4;
         }
      }

      var0.field6988 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("aj")
   public void method13419(Node var1, long var2) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = this.field6984[(int)(var2 & this.field6985 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   public int method13417() {
      int var1 = 0;

      for (int var2 = 0; var2 < this.field6985; var2++) {
         Node var3 = this.field6984[var2];

         for (Node var4 = var3.field6552; var4 != var3; var4 = var4.field6552) {
            var1++;
         }
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ai")
   public Node method13425() {
      this.field6986 = 0;
      return this.method13427();
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("au")
   public Node method13426() {
      this.field6986 = 0;
      return this.method13427();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyi;)Lvq;")
   @ObfuscatedName("pc")
   public static Node method13428(HashTable var0) {
      if (var0.field6986 > 0 && var0.field6987 != var0.field6984[var0.field6986 - 1]) {
         Node var2 = var0.field6987;
         var0.field6987 = var2.field6552;
         return var2;
      } else {
         while (var0.field6986 < var0.field6985) {
            Node var1 = var0.field6984[var0.field6986++].field6552;
            if (var1 != var0.field6984[var0.field6986 - 1]) {
               var0.field6987 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyi;Lvq;J)V")
   @ObfuscatedName("xs")
   public static void method13420(HashTable var0, Node var1, long var2) {
      if (var0 == null) {
         var0.method13431();
      }

      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = var0.field6984[(int)(var2 & var0.field6985 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Lvq;J)V")
   @ObfuscatedName("ay")
   public void method13421(Node var1, long var2) {
      if (var1.field6551 != null) {
         var1.vmethod398();
      }

      Node var4 = this.field6984[(int)(var2 & this.field6985 - 1)];
      var1.field6551 = var4.field6551;
      var1.field6552 = var4;
      var1.field6551.field6552 = var1;
      var1.field6552.field6551 = var1;
      var1.field6550 = var2;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("aw")
   public Node method13429() {
      if (this.field6986 > 0 && this.field6987 != this.field6984[this.field6986 - 1]) {
         Node var2 = this.field6987;
         this.field6987 = var2.field6552;
         return var2;
      } else {
         while (this.field6986 < this.field6985) {
            Node var1 = this.field6984[this.field6986++].field6552;
            if (var1 != this.field6984[this.field6986 - 1]) {
               this.field6987 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ak")
   public Node method13430() {
      if (this.field6986 > 0 && this.field6987 != this.field6984[this.field6986 - 1]) {
         Node var2 = this.field6987;
         this.field6987 = var2.field6552;
         return var2;
      } else {
         while (this.field6986 < this.field6985) {
            Node var1 = this.field6984[this.field6986++].field6552;
            if (var1 != this.field6984[this.field6986 - 1]) {
               this.field6987 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("av")
   public Node method13431() {
      if (this.field6986 > 0 && this.field6987 != this.field6984[this.field6986 - 1]) {
         Node var2 = this.field6987;
         this.field6987 = var2.field6552;
         return var2;
      } else {
         while (this.field6986 < this.field6985) {
            Node var1 = this.field6984[this.field6986++].field6552;
            if (var1 != this.field6984[this.field6986 - 1]) {
               this.field6987 = var1.field6552;
               return var1;
            }
         }

         return null;
      }
   }

   public Iterator iterator() {
      return new rl14(this);
   }

   @ObfuscatedSignature(descriptor = "(J)Lvq;")
   @ObfuscatedName("az")
   public Node method13414(long var1) {
      Node var3 = this.field6984[(int)(var1 & this.field6986 - 1)];

      for (this.field6987 = var3.field6551; this.field6987 != var3; this.field6987 = this.field6988.field6552) {
         if (this.field6987.field6550 == var1) {
            Node var4 = this.field6988;
            this.field6988 = this.field6988.field6551;
            return var4;
         }
      }

      this.field6988 = null;
      return null;
   }
}
