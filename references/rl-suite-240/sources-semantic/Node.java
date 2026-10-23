import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vq")
public class Node implements net.runelite.api.Node {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("hu")
   public Node field6552;
   @ObfuscatedName("ho")
   public long field6550;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("hj")
   public Node field6551;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("na")
   public boolean method12128() {
      return this.field6551 != null;
   }

   public long getHash() {
      return this.field6550;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("mo")
   public boolean method12129() {
      return this.field6551 != null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gh")
   public void vmethod399() {
      if (this.field6551 != null) {
         this.field6551.field6552 = this.field6552;
         this.field6552.field6551 = this.field6551;
         this.field6552 = null;
         this.field6551 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("ml")
   public Node method12134() {
      return this.field6552;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gr")
   public void vmethod400() {
      if (this.field6551 != null) {
         this.field6551.field6552 = this.field6552;
         this.field6552.field6551 = this.field6551;
         this.field6552 = null;
         this.field6551 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gu")
   public void vmethod398() {
      this.method12133();
      if (this.field6551 != null) {
         this.field6551.field6552 = this.field6552;
         this.field6552.field6551 = this.field6551;
         this.field6552 = null;
         this.field6551 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("pz")
   public void method12135() {
      this.vmethod398();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnh;)Lvj;")
   @ObfuscatedName("uh")
   public static classVJ method12132(classNH var0) {
      if (var0 == null) {
         return var0.method7894();
      } else {
         classVJ var1 = var0.field4669.field6513;
         return var1 == var0.field4669 ? null : var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("up")
   public void method12133() {
      this.vmethod387();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("jk")
   public void vmethod387() {
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("mx")
   public boolean method12130() {
      return this.field6551 != null;
   }

   @ObfuscatedSignature(descriptor = "()Lvq;")
   @ObfuscatedName("mq")
   public Node method12136() {
      return this.field6551;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("mr")
   public boolean method12131() {
      return this.field6551 != null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("gf")
   public void vmethod401() {
      if (this.field6551 != null) {
         this.field6551.field6552 = this.field6552;
         this.field6552.field6551 = this.field6551;
         this.field6552 = null;
         this.field6551 = null;
      }
   }
}
