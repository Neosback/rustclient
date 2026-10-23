import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("vj")
public class classVJ extends Node {
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("fr")
   public classVJ field6513;
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("ft")
   public classVJ field6514;
   @ObfuscatedName("fx")
   public long field6512;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvj;)V")
   @ObfuscatedName("wk")
   public static void method12034(classVJ var0) {
      if (var0.field6514 != null) {
         var0.field6514.field6513 = var0.field6513;
         var0.field6513.field6514 = var0.field6514;
         var0.field6513 = null;
         var0.field6514 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvj;")
   @ObfuscatedName("kt")
   public classVJ method12037() {
      return this.field6513;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("uf")
   public void method12038() {
      this.method12035();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("mc")
   public void method12035() {
      if (this.field6514 != null) {
         this.field6514.field6513 = this.field6513;
         this.field6513.field6514 = this.field6514;
         this.field6513 = null;
         this.field6514 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("my")
   public void method12036() {
      if (this.field6514 != null) {
         this.field6514.field6513 = this.field6513;
         this.field6513.field6514 = this.field6514;
         this.field6513 = null;
         this.field6514 = null;
      }
   }
}
