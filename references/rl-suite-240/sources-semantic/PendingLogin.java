import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rh")
public class PendingLogin extends classVM implements net.runelite.api.PendingLogin {
   @ObfuscatedSignature(descriptor = "Laae;")
   @ObfuscatedName("af")
   public classAAE field5663;
   @ObfuscatedName("az")
   public int field5665 = (int)(classDD.method2988(16711680) / 1000L) * 866162873;
   @ObfuscatedName("ae")
   public short field5664;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5666 = 7;

   @ObfuscatedSignature(descriptor = "()Laae;")
   @ObfuscatedName("wo")
   public classAAE method10003() {
      return this.field5663;
   }

   public String getName() {
      return this.method10003().method183();
   }

   @ObfuscatedSignature(descriptor = "(Laae;I)V")
   @ObfuscatedName("sj")
   public void method10002(classAAE var1, int var2) {
      this.field5665 = (this.field5665 * 1669590409 + 5) * 866162873;
   }

   public short getWorld() {
      return this.field5664;
   }

   PendingLogin(classAAE var1, int var2) {
      this.field5663 = var1;
      this.field5664 = (short)var2;
      this.method10002(var1, var2);
   }
}
