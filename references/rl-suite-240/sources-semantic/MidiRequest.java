import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("nb")
public class MidiRequest implements net.runelite.api.MidiRequest {
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("az")
   public IndexDataBase field4640;
   @ObfuscatedSignature(descriptor = "Lno;")
   @ObfuscatedName("ay")
   public classNO field4638;
   @ObfuscatedName("pl")
   public boolean field4643;
   @ObfuscatedName("ae")
   public int field4628;
   @ObfuscatedName("ak")
   static int field4635;
   @ObfuscatedName("ag")
   public float field4629;
   @ObfuscatedName("af")
   public int field4630 = -1190162147;
   @ObfuscatedName("ax")
   public boolean field4642;
   @ObfuscatedName("ac")
   public boolean field4641;
   @ObfuscatedName("aa")
   public boolean field4632;
   @ObfuscatedName("ao")
   boolean field4631;
   @ObfuscatedName("ab")
   public int field4633;
   @ObfuscatedSignature(descriptor = "Lat;")
   @ObfuscatedName("aj")
   public classAT field4637;
   @ObfuscatedName("as")
   public boolean field4634;
   @ObfuscatedSignature(descriptor = "Lnu;")
   @ObfuscatedName("al")
   public classNU field4639;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   static final int field4636 = 45;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnb;Lva;IIIZ)V")
   @ObfuscatedName("su")
   public static void method7804(MidiRequest var0, IndexDataBase var1, int var2, int var3, int var4, boolean var5) {
      if (var0 == null) {
         var0.isJingle();
      }

      var0.field4640 = var1;
      var0.field4630 = var2 * 1190162147;
      var0.field4628 = 424024677 * var3;
      var0.field4633 = var4 * -1016653583;
      var0.field4634 = var5;
   }

   @ObfuscatedSignature(descriptor = "(Lva;IIIZ)V")
   @ObfuscatedName("kl")
   public void method7809(IndexDataBase var1, int var2, int var3, int var4, boolean var5) {
      classVP var6 = (classVP)var1;
      this.field4643 = var6.method12125() == 11;
   }

   @ObfuscatedSignature(descriptor = "(Lnb;Lva;IIIZS)V")
   @ObfuscatedName("mt")
   public static void method7805(MidiRequest var0, IndexDataBase var1, int var2, int var3, int var4, boolean var5, short var6) {
      if (var0 == null) {
         var0.method7808(var1, var2, var2, var2, var5, var6);
      } else {
         var0.method7809(var1, var2, var3, var4, var5);

         try {
            var0.field4640 = var1;
            var0.field4630 = var2 * 1190162147;
            var0.field4628 = 424024677 * var3;
            var0.field4633 = var4 * -1016653583;
            var0.field4634 = var5;
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "nb.az(" + ')');
         }
      }
   }

   public MidiRequest(IndexDataBase var1, int var2, int var3, int var4, boolean var5) {
      this.field4628 = -424024677;
      this.field4633 = 0;
      this.field4629 = 0.0F;
      this.field4634 = false;
      this.field4632 = false;
      this.field4631 = false;
      method7805(this, var1, var2, var3, var4, var5, (short)8320);
   }

   @ObfuscatedSignature(descriptor = "(Lva;IIIZ)V")
   @ObfuscatedName("af")
   void method7806(IndexDataBase var1, int var2, int var3, int var4, boolean var5) {
      this.field4640 = var1;
      this.field4630 = var2 * 1190162147;
      this.field4628 = -1438544821 * var3;
      this.field4633 = var4 * -1016653583;
      this.field4634 = var5;
   }

   public MidiRequest(IndexDataBase var1, String var2, String var3, int var4, boolean var5) {
      this.field4628 = -424024677;
      this.field4633 = 0;
      this.field4629 = 0.0F;
      this.field4634 = false;
      this.field4632 = false;
      this.field4631 = false;
      this.field4630 = IndexDataBase.method11918(var1, var2, 1028821867) * 1190162147;
      this.field4628 = var1.method11922(this.field4630 * 1942111947, var3, (byte)-74) * 424024677;
      method7805(this, var1, 1942111947 * this.field4630, 450234221 * this.field4628, var4, var5, (short)8320);
   }

   public boolean isJingle() {
      return this.field4643;
   }

   @ObfuscatedSignature(descriptor = "(Lva;IIIZ)V")
   @ObfuscatedName("ae")
   void method7807(IndexDataBase var1, int var2, int var3, int var4, boolean var5) {
      this.field4640 = var1;
      this.field4630 = var2 * 1190162147;
      this.field4628 = 424024677 * var3;
      this.field4633 = var4 * -1016653583;
      this.field4634 = var5;
   }

   public int getArchiveId() {
      return this.field4630 * 1942111947;
   }

   @ObfuscatedSignature(descriptor = "(Lva;IIIZS)V")
   @ObfuscatedName("az")
   void method7808(IndexDataBase var1, int var2, int var3, int var4, boolean var5, short var6) {
      this.method7809(var1, var2, var3, var4, var5);

      try {
         this.field4640 = var1;
         this.field4630 = var2 * 1190162147;
         this.field4628 = 424024677 * var3;
         this.field4628 = var4 * -1016653583;
         this.field4642 = var5;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "nb.az(" + ')');
      }
   }
}
