import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nw")
public class classNW implements Iterator {
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("ae")
   Node field4784;
   @ObfuscatedSignature(descriptor = "Lnv;")
   @ObfuscatedName("az")
   classNV field4783;
   @ObfuscatedSignature(descriptor = "Lvq;")
   @ObfuscatedName("af")
   Node field4782 = null;

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   void method8269() {
      this.field4784 = this.field4783 != null ? this.field4783.field4780.field6552 : null;
      this.field4782 = null;
   }

   classNW(classNV var1) {
      this.method8268(var1);
   }

   @Override
   public boolean hasNext() {
      return this.field4784 != this.field4783.field4780 && this.field4784 != null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnw;)Ljava/lang/Object;")
   @ObfuscatedName("yx")
   public static Object method8273(classNW var0) {
      if (var0 == null) {
         return var0.method8277();
      } else {
         Node var1 = var0.field4784;
         if (var1 == var0.field4783.field4780) {
            var1 = null;
            var0.field4784 = null;
         } else {
            var0.field4784 = var1.field6552;
         }

         var0.field4782 = var1;
         return var1;
      }
   }

   @Override
   public Object next() {
      Node var1 = this.field4784;
      if (var1 == this.field4783.field4780) {
         var1 = null;
         this.field4784 = null;
      } else {
         this.field4784 = var1.field6552;
      }

      this.field4782 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("al")
   public Object method8274() {
      Node var1 = this.field4784;
      if (var1 == this.field4783.field4780) {
         var1 = null;
         this.field4784 = null;
      } else {
         this.field4784 = var1.field6552;
      }

      this.field4782 = var1;
      return var1;
   }

   @Override
   public void remove() {
      if (this.field4782 == null) {
         throw new IllegalStateException();
      } else {
         this.field4782.vmethod398();
         this.field4782 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method8279() {
      return this.field4784 != this.field4783.field4780 && this.field4784 != null;
   }

   @ObfuscatedSignature(descriptor = "(Lnv;)V")
   @ObfuscatedName("ab")
   void method8266(classNV var1) {
      this.field4783 = var1;
      this.method8269();
   }

   @ObfuscatedSignature(descriptor = "(Lnv;)V")
   @ObfuscatedName("ag")
   void method8267(classNV var1) {
      this.field4783 = var1;
      this.method8269();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("as")
   void method8270() {
      this.field4784 = this.field4783 != null ? this.field4783.field4780.field6552 : null;
      this.field4782 = null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method8280() {
      return this.field4784 != this.field4783.field4780 && this.field4784 != null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   void method8271() {
      this.field4784 = this.field4783 != null ? this.field4783.field4780.field6552 : null;
      this.field4782 = null;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnw;)Ljava/lang/Object;")
   @ObfuscatedName("ln")
   public static Object method8275(classNW var0) {
      if (var0 == null) {
         var0.method8278();
      }

      Node var1 = var0.field4784;
      if (var1 == var0.field4783.field4780) {
         var1 = null;
         var0.field4784 = null;
      } else {
         var0.field4784 = var1.field6552;
      }

      var0.field4782 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   void method8272() {
      this.field4784 = this.field4783 != null ? this.field4783.field4780.field6552 : null;
      this.field4782 = null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ay")
   public Object method8276() {
      Node var1 = this.field4784;
      if (var1 == this.field4783.field4780) {
         var1 = null;
         this.field4784 = null;
      } else {
         this.field4784 = var1.field6552;
      }

      this.field4782 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method8281() {
      return this.field4784 != this.field4783.field4780 && this.field4784 != null;
   }

   @ObfuscatedSignature(descriptor = "(Lnv;)V")
   @ObfuscatedName("az")
   void method8268(classNV var1) {
      this.field4783 = var1;
      this.method8269();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ai")
   public void method8282() {
      if (this.field4782 == null) {
         throw new IllegalStateException();
      } else {
         this.field4782.vmethod398();
         this.field4782 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ar")
   public void method8283() {
      if (this.field4782 == null) {
         throw new IllegalStateException();
      } else {
         this.field4782.vmethod398();
         this.field4782 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ao")
   public Object method8277() {
      Node var1 = this.field4784;
      if (var1 == this.field4783.field4780) {
         var1 = null;
         this.field4782 = null;
      } else {
         this.field4782 = var1.field6551;
      }

      this.field4784 = var1;
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("aj")
   public Object method8278() {
      Node var1 = this.field4784;
      if (var1 == this.field4783.field4780) {
         var1 = null;
         this.field4782 = null;
      } else {
         this.field4782 = var1.field6552;
      }

      this.field4784 = var1;
      return var1;
   }
}
