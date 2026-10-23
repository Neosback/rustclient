import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;
import org.json.JSONObject;

@ObfuscatedName("nk")
public class Deque implements net.runelite.api.Deque {
   @ObfuscatedSignature(descriptor = "Lvm;")
   @ObfuscatedName("az")
   classVM field4678;
   @ObfuscatedSignature(descriptor = "Lvm;")
   @ObfuscatedName("af")
   classVM field4677 = new classVM();

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("ac")
   public classVM method7913() {
      classVM var1 = this.field4677.field6526;
      if (var1 == this.field4677) {
         this.field4678 = null;
         return null;
      } else {
         this.field4678 = var1.field6526;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("af")
   public classVM method7914() {
      classVM var1 = this.field4677.field6526;
      if (var1 == this.field4677) {
         this.field4678 = null;
         return null;
      } else {
         this.field4678 = var1.field6526;
         return var1;
      }
   }

   public Deque() {
      this.field4677.field6526 = this.field4677;
      this.field4677.field6527 = this.field4677;
   }

   public Iterator iterator() {
      return new rl15(this);
   }

   @ObfuscatedSignature(descriptor = "(Lvm;)V")
   @ObfuscatedName("ab")
   public void method7908(classVM var1) {
      if (var1.field6527 != null) {
         classVM.method12069(var1);
      }

      var1.field6527 = this.field4677.field6527;
      var1.field6526 = this.field4677;
      var1.field6527.field6526 = var1;
      var1.field6526.field6527 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lrh;)V")
   @ObfuscatedName("qx")
   public void method7924(PendingLogin var1) {
      method7911(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(Lvm;)V")
   @ObfuscatedName("as")
   public void method7909(classVM var1) {
      if (var1.field6527 != null) {
         classVM.method12069(var1);
      }

      var1.field6527 = this.field4677.field6527;
      var1.field6526 = this.field4677;
      var1.field6527.field6526 = var1;
      var1.field6526.field6527 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwe;)Lorg/json/JSONObject;")
   @ObfuscatedName("xf")
   public static JSONObject method7923(classWE var0) {
      return var0 == null ? var0.method12424() : var0.field6672;
   }

   @ObfuscatedSignature(descriptor = "(Lvm;)V")
   @ObfuscatedName("ag")
   public void method7910(classVM var1) {
      if (var1.field6527 != null) {
         classVM.method12069(var1);
      }

      var1.field6527 = this.field4677.field6527;
      var1.field6526 = this.field4677;
      var1.field6527.field6526 = var1;
      var1.field6526.field6527 = var1;
   }

   public void clear() {
      Iterator var1 = this.iterator();

      while (var1.hasNext()) {
         var1.remove();
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("aa")
   public classVM method7915() {
      classVM var1 = this.field4677.field6526;
      if (var1 == this.field4677) {
         this.field4678 = null;
         return null;
      } else {
         this.field4678 = var1.field6526;
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnk;)Lvm;")
   @ObfuscatedName("oe")
   public static classVM method7919(Deque var0) {
      classVM var1 = var0.field4678;
      if (var1 == var0.field4677) {
         var0.field4678 = null;
         return null;
      } else {
         var0.field4678 = var1.field6526;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnk;Lvm;)V")
   @ObfuscatedName("vl")
   public static void method7911(Deque var0, classVM var1) {
      if (var0 == null) {
         var0.method7912(var1);
      } else {
         if (var1.field6527 != null) {
            classVM.method12069(var1);
         }

         var1.field6527 = var0.field4677.field6527;
         var1.field6526 = var0.field4677;
         var1.field6527.field6526 = var1;
         var1.field6526.field6527 = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("ax")
   public classVM method7916() {
      classVM var1 = this.field4677.field6526;
      if (var1 == this.field4677) {
         this.field4678 = null;
         return null;
      } else {
         this.field4678 = var1.field6526;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("ao")
   public classVM method7917() {
      classVM var1 = this.field4677.field6526;
      if (var1 == this.field4677) {
         this.field4678 = null;
         return null;
      } else {
         this.field4678 = var1.field6526;
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("al")
   public classVM method7920() {
      classVM var1 = this.field4678;
      if (var1 == this.field4677) {
         this.field4678 = null;
         return null;
      } else {
         this.field4678 = var1.field6526;
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljz;J)Lvj;")
   @ObfuscatedName("by")
   public static classVJ method7918(NodeCache var0, long var1) {
      classVJ var3 = (classVJ)var0.field3500.method13637(var1);
      if (var3 != null) {
         var0.field3502.method8302(var3);
      }

      return var3;
   }

   @ObfuscatedSignature(descriptor = "(Lvm;)V")
   @ObfuscatedName("az")
   public void method7912(classVM var1) {
      if (var1.field6527 != null) {
         classVM.method12069(var1);
      }

      var1.field6527 = this.field4677.field6526;
      var1.field6526 = this.field4677;
      var1.field6527.field6526 = var1;
      var1.field6527.field6527 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Lnk;)Lvm;")
   @ObfuscatedName("kd")
   public static classVM method7921(Deque var0) {
      if (var0 == null) {
         return var0.method7922();
      } else {
         classVM var1 = var0.field4678;
         if (var1 == var0.field4677) {
            var0.field4678 = null;
            return null;
         } else {
            var0.field4678 = var1.field6526;
            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("ae")
   public classVM method7922() {
      classVM var1 = this.field4678;
      if (var1 == this.field4678) {
         this.field4678 = null;
         return null;
      } else {
         this.field4677 = var1.field6526;
         return var1;
      }
   }
}
