import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("iu")
public class classIU extends classHB {
   @ObfuscatedName("pm")
   static int field3063;
   @ObfuscatedName("az")
   String field3064;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field3061 = 1076101384;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field3060 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bc")
   static final int field3062 = 30;

   @ObfuscatedSignature(descriptor = "(D)I")
   @ObfuscatedName("ag")
   public static int method6359(double var0) {
      try {
         return (int)(2607.5945876176133 * var0) & 16383;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iu.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field3064 = var1.method13127(-329009483);
         classXY.method13113(var1, (byte)1);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iu.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.field3014 = this.field3064;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "iu.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field3064 = var1.method13127(593986710);
      classXY.method13113(var1, (byte)1);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field3064 = var1.method13127(113709177);
      classXY.method13113(var1, (byte)1);
   }

   classIU(classIF var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.field3014 = this.field3064;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.field3014 = this.field3064;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpl;)Ljava/lang/String;")
   @ObfuscatedName("gs")
   public static String method6360(NPCComposition var0) {
      if (var0 == null) {
         var0.method9040();
      }

      return var0.field5291;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.field3014 = this.field3064;
   }
}
