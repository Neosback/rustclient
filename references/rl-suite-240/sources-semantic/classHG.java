import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hg")
public class classHG {
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   public static final int field2792 = 74;
   @ObfuscatedName("af")
   int field2790;
   @ObfuscatedName("ae")
   int field2788;
   @ObfuscatedName("ab")
   int field2789;
   @ToRemove(unused = "true")
   @ObfuscatedName("bd")
   public static final int field2791 = 73;
   @ObfuscatedName("az")
   int field2787 = 1187461017;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public static void method6039(int var0) {
      try {
         HealthBarConfig.field5473.method6468();
         HealthBarConfig.field5474.method6468();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "hg.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("gw")
   static final void method6042(byte var0) {
      try {
         for (MessageNode var2 : classCN.field1172) {
            var2.method1479(-425903031);
         }

         if (null != classIB.field2933) {
            if (var0 == 0) {
               throw new IllegalStateException();
            }

            FriendsChatManager.method10375(classIB.field2933, 853246097);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "hg.gw(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIB)I")
   @ObfuscatedName("av")
   static final int method6041(int var0, int var1, int var2, byte var3) {
      try {
         if (var2 > 179) {
            var1 /= 2;
         }

         if (var2 > 192) {
            var1 /= 2;
         }

         if (var2 > 217) {
            if (var3 != 2) {
               throw new IllegalStateException();
            }

            var1 /= 2;
         }

         if (var2 > 243) {
            if (var3 != 2) {
               throw new IllegalStateException();
            }

            var1 /= 2;
         }

         return var2 / 2 + (var1 / 32 << 7) + (var0 / 4 << 10);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "hg.av(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("af")
   public static void method6040(byte var0) {
      try {
         classMT.field4579.clear();
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "hg.af(" + ')');
      }
   }

   classHG() {
      this.field2790 = 0;
      this.field2788 = -548323909;
      this.field2789 = 0;
   }
}
