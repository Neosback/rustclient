import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xp")
final class classXP implements classXF {
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field6904 = 3;

   @ObfuscatedSignature(descriptor = "()Lxt;")
   @ObfuscatedName("af")
   @Override
   public classXT vmethod647() {
      return classXT.field6935;
   }

   @ObfuscatedSignature(descriptor = "(B)Lxt;")
   @ObfuscatedName("az")
   @Override
   public classXT vmethod648(byte var1) {
      try {
         return classXT.field6935;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xp.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("bt")
   static void method12927(int var0, int var1, int var2, int var3, int var4) {
      try {
         Player var5 = classMB.method7564(1056436667);
         classJL var6 = classEF.method3863(classJS.MOVE_MINIMAPCLICK, client.field796.field1533, (byte)-67);
         classXY.method13029(var6.field3341, 18, (byte)-47);
         var6.field3341.method13244(var1 + -351145363 * classIS.field3051.field1707, (short)-17424);
         PacketBuffer var10000 = var6.field3341;
         byte var10001;
         if (client.field932.method4563(82, 842830430)) {
            if (var4 <= 99079134) {
               return;
            }

            if (client.field932.method4563(81, 1295652530)) {
               if (var4 <= 99079134) {
                  throw new IllegalStateException();
               }

               var10001 = 2;
            } else {
               var10001 = 1;
            }
         } else {
            var10001 = 0;
         }

         var10000.method13211(var10001, (byte)67);
         var6.field3341.method13244(-1444178379 * classIS.field3051.field1706 + var0, (short)-3113);
         classXY.method13029(var6.field3341, var2, (byte)-65);
         classXY.method13029(var6.field3341, var3, (byte)-21);
         var6.field3341.method13031(client.field995.method11515(556954181), -1542382992);
         classXY.method13029(var6.field3341, 57, (byte)-118);
         classXY.method13029(var6.field3341, 0, (byte)-49);
         classXY.method13029(var6.field3341, 0, (byte)-73);
         classXY.method13029(var6.field3341, 89, (byte)-23);
         if (null == var5) {
            if (var4 <= 99079134) {
               throw new IllegalStateException();
            }

            var10001 = 0;
         } else {
            var10001 = -1547553299 * var5.field1484;
         }

         var6.field3341.method13031(var10001, -1542382992);
         if (null == var5) {
            if (var4 <= 99079134) {
               throw new IllegalStateException();
            }

            var10001 = 0;
         } else {
            var10001 = var5.field1486 * -1272026483;
         }

         var6.field3341.method13031(var10001, -1542382992);
         classXY.method13029(var6.field3341, 63, (byte)-66);
         client.field796.method3374(var6, -1301097035);
         client.field860 = var0 * -1254957895;
         client.field835 = -877699493 * var1;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "xp.bt(" + ')');
      }
   }
}
