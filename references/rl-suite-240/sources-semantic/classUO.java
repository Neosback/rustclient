import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("uo")
public class classUO implements classXZ {
   @ObfuscatedSignature(descriptor = "Luo;")
   @ObfuscatedName("az")
   static final classUO field6355 = new classUO(3, 1);
   @ObfuscatedSignature(descriptor = "Luo;")
   @ObfuscatedName("af")
   static final classUO field6357 = new classUO(0, 2);
   @ObfuscatedSignature(descriptor = "Luo;")
   @ObfuscatedName("ae")
   static final classUO field6358 = new classUO(2, 3);
   @ObfuscatedSignature(descriptor = "Luo;")
   @ObfuscatedName("ab")
   static final classUO field6356 = new classUO(1, 10);
   @ObfuscatedName("ag")
   final int field6354;
   @ObfuscatedName("as")
   final int field6353;
   @ObfuscatedSignature(descriptor = "[Lym;")
   @ObfuscatedName("ac")
   static SpritePixels[] field6359;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return -364102079 * this.field6353;
   }

   classUO(int var1, int var2) {
      this.field6354 = var1 * -979953685;
      this.field6353 = var2 * -1254960703;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return 1182923174 * this.field6353;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return -364102079 * this.field6353;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "uo.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -295184380 * this.field6353;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Luo;")
   @ObfuscatedName("as")
   static classUO[] method11474() {
      return new classUO[]{field6358, field6356, field6357, field6355};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Luo;")
   @ObfuscatedName("ax")
   static classUO[] method11475() {
      return new classUO[]{field6358, field6356, field6357, field6355};
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bh")
   static void method11477(int var0, int var1) {
      try {
         client.field861 = 0L;
         if (var0 >= 2) {
            if (var1 >= -2042311248) {
               return;
            }

            client.field862 = true;
            client.method2318(-1);
         } else {
            client.field862 = false;
            client.method2318(-1);
         }

         if (classLH.method7145(-615017728) == 1) {
            if (var1 >= -2042311248) {
               return;
            }

            classOE.field4841.method11144(765, 503, (byte)82);
         } else {
            classOE.field4841.method11144(7680, 2160, (byte)81);
         }

         if (client.field829 * -44590225 >= 25) {
            if (var1 >= -2042311248) {
               throw new IllegalStateException();
            }

            if (client.field796 != null) {
               if (var1 >= -2042311248) {
                  return;
               }

               if (null == client.field796.field1533) {
                  if (var1 >= -2042311248) {
                     throw new IllegalStateException();
                  }
               } else {
                  classJL var2 = classEF.method3863(classJS.WINDOW_STATUS, client.field796.field1533, (byte)43);
                  classXY.method13029(var2.field3341, classLH.method7145(1212026946), (byte)-100);
                  var2.field3341.method13031(773246731 * classSA.field5895, -1542382992);
                  var2.field3341.method13031(8379747 * IntProjection.field1936, -1542382992);
                  client.field796.method3374(var2, -1301097035);
               }
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uo.bh(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "([Ljava/lang/String;[II)V")
   @ObfuscatedName("ae")
   public static void method11476(String[] var0, int[] var1, int var2) {
      try {
         classCZ.method2848(var0, var1, 0, var0.length - 1, 361165951);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "uo.ae(" + ')');
      }
   }
}
