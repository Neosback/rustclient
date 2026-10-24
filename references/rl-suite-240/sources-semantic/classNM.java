import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nm")
public class classNM {
   @ObfuscatedName("ab")
   String field4687;
   @ObfuscatedName("af")
   public final long field4685;
   @ObfuscatedSignature(descriptor = "Lmm;")
   @ObfuscatedName("ae")
   public final GrandExchangeOffer field4683;
   @ObfuscatedName("ag")
   String field4686;
   @ObfuscatedName("az")
   public final int field4684;
   @ToRemove(unused = "true")
   @ObfuscatedName("by")
   static final int field4688 = 90;

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("af")
   public String method7934(int var1) {
      try {
         return this.field4687;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nm.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lnm;I)Ljava/lang/String;")
   @ObfuscatedName("vs")
   public static String method7936(classNM var0, int var1) {
      if (var0 == null) {
         var0.method7938(var1);
      }

      try {
         return var0.field4686;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nm.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ae")
   public String method7935() {
      return this.field4687;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("as")
   public String method7937() {
      return this.field4686;
   }

   classNM(classXY var1, byte var2, int var3) {
      this.field4687 = var1.method13127(2117716806);
      this.field4686 = var1.method13127(1817698247);
      this.field4684 = var1.method13099(-617856821) * -1507921803;
      this.field4685 = classXY.method13115(var1, -432626029) * 1135738023595011617L;
      int var4 = classXY.method13113(var1, (byte)1);
      int var5 = classXY.method13113(var1, (byte)1);
      this.field4683 = new GrandExchangeOffer();
      this.field4683.method7651(2, (byte)4);
      GrandExchangeOffer.method7656(this.field4683, var2, 2008825883);
      this.field4683.field4532 = 961265821 * var4;
      this.field4683.field4534 = var5 * 373867205;
      this.field4683.field4535 = 0;
      this.field4683.field4536 = 0;
      this.field4683.field4533 = var3 * 443314353;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("az")
   public String method7938(int var1) {
      try {
         return this.field4687;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "nm.af(" + ')');
      }
   }
}
