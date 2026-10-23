import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nx")
public class classNX implements Iterator {
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("af")
   classVJ field4786;
   @ObfuscatedSignature(descriptor = "Lnz;")
   @ObfuscatedName("az")
   classNZ field4785;
   @ObfuscatedSignature(descriptor = "Lvj;")
   @ObfuscatedName("ae")
   classVJ field4787 = null;

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method8288() {
      return this.field4786 != this.field4785.field4798;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnx;)V")
   @ObfuscatedName("sp")
   public static void method8292(classNX var0) {
      if (var0.field4787 == null) {
         throw new IllegalStateException();
      } else {
         var0.field4787.method12035();
         var0.field4787 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method8284() {
      classVJ var1 = this.field4786;
      if (var1 == this.field4785.field4798) {
         var1 = null;
         this.field4786 = null;
      } else {
         this.field4786 = var1.field6513;
      }

      this.field4787 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method8289() {
      return this.field4786 != this.field4785.field4798;
   }

   @Override
   public void remove() {
      if (this.field4787 == null) {
         throw new IllegalStateException();
      } else {
         this.field4787.method12035();
         this.field4787 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method8290() {
      return this.field4786 != this.field4785.field4798;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ao")
   public Object method8285() {
      classVJ var1 = this.field4786;
      if (var1 == this.field4785.field4798) {
         var1 = null;
         this.field4786 = null;
      } else {
         this.field4786 = var1.field6513;
      }

      this.field4787 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method8291() {
      return this.field4786 != this.field4785.field4798;
   }

   @Override
   public Object next() {
      classVJ var1 = this.field4786;
      if (var1 == this.field4785.field4798) {
         var1 = null;
         this.field4786 = null;
      } else {
         this.field4786 = var1.field6513;
      }

      this.field4787 = var1;
      return var1;
   }

   @Override
   public boolean hasNext() {
      return this.field4786 != this.field4785.field4798;
   }

   classNX(classNZ var1) {
      this.field4785 = var1;
      this.field4786 = this.field4785.field4798.field6513;
      this.field4787 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnx;)Ljava/lang/Object;")
   @ObfuscatedName("ld")
   public static Object method8286(classNX var0) {
      if (var0 == null) {
         var0.method8284();
      }

      classVJ var1 = var0.field4786;
      if (var1 == var0.field4785.field4798) {
         var1 = null;
         var0.field4786 = null;
      } else {
         var0.field4786 = var1.field6513;
      }

      var0.field4787 = var1;
      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnx;)Ljava/lang/Object;")
   @ObfuscatedName("iz")
   public static Object method8287(classNX var0) {
      classVJ var1 = var0.field4786;
      if (var1 == var0.field4785.field4798) {
         var1 = null;
         var0.field4786 = null;
      } else {
         var0.field4786 = var1.field6513;
      }

      var0.field4787 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method8293() {
      if (this.field4787 == null) {
         throw new IllegalStateException();
      } else {
         this.field4787.method12035();
         this.field4787 = null;
      }
   }
}
