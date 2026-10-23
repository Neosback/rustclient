import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("os")
public class classOS {
   @ObfuscatedName("af")
   final int field5104;
   @ObfuscatedName("ae")
   final int field5105;
   @ToRemove(unused = "true")
   @ObfuscatedName("bs")
   static final int field5106 = 100;
   @ToRemove(unused = "true")
   @ObfuscatedName("bz")
   static final int field5107 = 1006;
   @ObfuscatedName("az")
   final int field5103;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lnu;Lnt;)Z")
   @ObfuscatedName("jo")
   public static boolean method8765(classNU var0, classNT var1) {
      if (var0 == null) {
         var0.method8068();
      }

      if (null == var1.field4742) {
         if (67131625 * var1.field4727 >= 0) {
            var1.vmethod398();
            if (-1530244505 * var1.field4720 > 0 && var0.field4772[-1604551355 * var1.field4732][var1.field4720 * -1530244505] == var1) {
               var0.field4772[-1057694491 * var1.field4732][631676779 * var1.field4720] = null;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   classOS(classOW var1, int var2, int var3, int var4) {
      this.this$0 = var1;
      this.field5105 = 1046163259 * var2;
      this.field5103 = Math.max(0, Math.min(var3, 255)) * 1286636395;
      this.field5104 = Math.max(0, Math.min(var4, 255)) * -1874551353;
   }
}
