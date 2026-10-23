import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("vm")
public class classVM {
   @ObfuscatedSignature(descriptor = "Lvm;")
   @ObfuscatedName("ab")
   public classVM field6526;
   @ObfuscatedSignature(descriptor = "Lvm;")
   @ObfuscatedName("ag")
   public classVM field6527;

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("hx")
   public classVM method12074() {
      return this.field6527;
   }

   @ObfuscatedSignature(descriptor = "(Lvm;)V")
   @ObfuscatedName("vr")
   public static void method12069(classVM var0) {
      if (var0 == null) {
         var0.method12073();
      }

      if (var0.field6527 != null) {
         var0.field6527.field6526 = var0.field6526;
         var0.field6526.field6527 = var0.field6527;
         var0.field6526 = null;
         var0.field6527 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("af")
   public void method12070() {
      if (this.field6527 != null) {
         this.field6527.field6526 = this.field6526;
         this.field6526.field6527 = this.field6527;
         this.field6526 = null;
         this.field6527 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method12071() {
      if (this.field6527 != null) {
         this.field6527.field6526 = this.field6526;
         this.field6526.field6527 = this.field6527;
         this.field6526 = null;
         this.field6527 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()Lvm;")
   @ObfuscatedName("wl")
   public classVM method12075() {
      return this.field6526;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("jg")
   public void method12076() {
      method12069(this);
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ab")
   public void method12072() {
      if (this.field6527 != null) {
         this.field6527.field6526 = this.field6526;
         this.field6526.field6527 = this.field6527;
         this.field6526 = null;
         this.field6527 = null;
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("az")
   public void method12073() {
      if (this.field6527 != null) {
         this.field6527.field6527 = this.field6527;
         this.field6527.field6526 = this.field6526;
         this.field6526 = null;
         this.field6526 = null;
      }
   }
}
