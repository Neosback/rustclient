import net.runelite.api.events.PostHealthBarConfig;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("px")
public class HealthBarConfig extends classVJ implements net.runelite.api.HealthBarConfig {
   @ObfuscatedName("av")
   public final int field5481;
   @ObfuscatedName("ar")
   public final int field5479;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field5486 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("dm")
   public static final int field5488 = 102;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field5485 = 5;
   @ObfuscatedName("ai")
   public final int field5482;
   @ObfuscatedName("ah")
   public final int field5483;
   @ObfuscatedName("aw")
   public final int field5480;
   @ObfuscatedName("ak")
   public final int field5475;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("au")
   static NodeCache field5474 = new NodeCache(64);
   @ObfuscatedName("at")
   public final int field5478;
   @ObfuscatedName("an")
   final int field5477;
   @ObfuscatedName("am")
   final int field5476;
   @rl8(method10167 = 17)
   @ObfuscatedName("bn")
   public int field5484;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5487 = 1;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ap")
   static NodeCache field5473 = new NodeCache(64);

   @ObfuscatedSignature(descriptor = "(I)Lym;")
   @ObfuscatedName("ae")
   public SpritePixels method9411(int var1) {
      try {
         if (1254761701 * this.field5477 < 0) {
            if (var1 == -988824982) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5474, this.field5477 * 1254761701);
            if (var2 != null) {
               if (var1 == -988824982) {
                  throw new IllegalStateException();
               } else {
                  return var2;
               }
            } else {
               var2 = classPN.method9084(classKJ.field3603, 1254761701 * this.field5477, 0, -1829350990);
               if (null != var2) {
                  if (var1 == -988824982) {
                     throw new IllegalStateException();
                  }

                  field5474.method6464(var2, 1254761701 * this.field5477);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "px.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Lym;")
   @ObfuscatedName("ab")
   public SpritePixels method9414(byte var1) {
      try {
         if (this.field5476 * 1914620357 < 0) {
            return null;
         } else {
            SpritePixels var2 = (SpritePixels)NodeCache.method6460(field5474, this.field5476 * 1914620357);
            if (var2 != null) {
               return var2;
            } else {
               var2 = classPN.method9084(classKJ.field3603, 1914620357 * this.field5476, 0, -2123192596);
               if (null != var2) {
                  if (var1 == 31) {
                     throw new IllegalStateException();
                  }

                  field5474.method6464(var2, 1914620357 * this.field5476);
               }

               return var2;
            }
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "px.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("su")
   public int method9420() {
      return this.field5483 * -1485258143;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("al")
   public SpritePixels method9421() {
      return this.method9411(782773199);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqc;)Z")
   @ObfuscatedName("du")
   public static boolean method9418(classQC var0) {
      if (var0 == null) {
         var0.method9494();
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("li")
   public void method9419() {
      PostHealthBarConfig var1 = new PostHealthBarConfig();
      var1.setHealthBarConfig(this);
      classOE.field4841.getCallbacks().post(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpx;")
   @ObfuscatedName("ag")
   public static HealthBarConfig method9406(int var0) {
      HealthBarConfig var1 = (HealthBarConfig)NodeCache.method6460(field5473, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classMN.field4543.method11942(33, var0, -1602994962);
         var1 = new HealthBarConfig(new classXY(var2), var0);
         field5473.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpx;")
   @ObfuscatedName("as")
   public static HealthBarConfig method9407(int var0) {
      HealthBarConfig var1 = (HealthBarConfig)NodeCache.method6460(field5473, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classMN.field4543.method11942(-1953443042, var0, -1788520182);
         var1 = new HealthBarConfig(new classXY(var2), var0);
         field5473.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpx;)Lym;")
   @ObfuscatedName("iu")
   public static SpritePixels method9415(HealthBarConfig var0) {
      if (var0.field5476 * 1914620357 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5474, var0.field5476 * 1914620357);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classKJ.field3603, 1914620357 * var0.field5476, 0, -1747499911);
            if (null != var1) {
               field5474.method6464(var1, 1914620357 * var0.field5476);
            }

            return var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpx;)Lym;")
   @ObfuscatedName("jh")
   public static SpritePixels method9416(HealthBarConfig var0) {
      if (var0 == null) {
         var0.method9417();
      }

      if (var0.field5476 * 1914620357 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5474, var0.field5476 * 1914620357);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classKJ.field3603, 1914620357 * var0.field5476, 0, -2146458288);
            if (null != var1) {
               field5474.method6464(var1, 1914620357 * var0.field5476);
            }

            return var1;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ax")
   public static void method9408() {
      field5473.method6468();
      field5474.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public static void method9409() {
      field5473.method6468();
      field5474.method6468();
   }

   public int getHealthBarFrontSpriteId() {
      return this.field5477 * 1254761701;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("az")
   public SpritePixels method9412() {
      if (-1087166743 * this.field5477 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5474, this.field5477 * 1254761701);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classKJ.field3603, 4154527 * this.field5477, 0, -1954221896);
            if (null != var1) {
               field5474.method6464(var1, -1733022372 * this.field5477);
            }

            return var1;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("ao")
   public SpritePixels method9413() {
      if (1254761701 * this.field5477 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5474, this.field5477 * 1254761701);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classKJ.field3603, 1254761701 * this.field5477, 0, -1923425365);
            if (null != var1) {
               field5474.method6464(var1, 1254761701 * this.field5477);
            }

            return var1;
         }
      }
   }

   public void setPadding(int var1) {
      this.field5484 = var1 * -1615725109;
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("aq")
   public SpritePixels method9422() {
      return this.method9414((byte)-90);
   }

   HealthBarConfig(classXY var1, int var2) {
      this.field5482 = var2 * 99929699;
      int var3 = 255;
      int var4 = 255;
      int var5 = -1;
      byte var6 = 1;
      int var7 = 70;
      int var8 = -1;
      int var9 = -1;
      int var10 = 30;
      int var11 = 0;
      if (var1 != null && null != var1.field6952) {
         label53:
         while (true) {
            int var12 = var1.method13095(-346779531);
            switch (var12) {
               case 0:
                  break label53;
               case 1:
                  var1.method13099(-477261215);
                  continue;
               case 2:
                  var3 = var1.method13095(-346779531);
                  continue;
               case 3:
                  var4 = var1.method13095(-346779531);
                  continue;
               case 4:
                  var5 = 0;
                  continue;
               case 5:
                  var7 = var1.method13099(659653123);
                  continue;
               case 6:
                  var1.method13095(-346779531);
                  continue;
               case 7:
                  var8 = classXY.method13169(var1, -324749371);
                  continue;
               case 8:
                  var9 = classXY.method13169(var1, -324749371);
               case 9:
               case 10:
               case 12:
               case 13:
               default:
                  continue;
               case 11:
                  var5 = var1.method13099(-1638589704);
                  continue;
               case 14:
                  var10 = var1.method13095(-346779531);
                  continue;
               case 15:
            }

            var11 = var1.method13095(-346779531);
         }
      }

      this.field5479 = -1608811953 * var3;
      this.field5480 = 1368171699 * var4;
      this.field5475 = var5 * -2128041703;
      this.field5481 = 1049500111 * var6;
      this.field5478 = var7 * 102288865;
      this.field5477 = var8 * 1858533613;
      this.field5476 = var9 * 540658957;
      this.field5483 = var10 * 315056545;
      this.field5484 = var11 * -1615725109;
      this.method9419();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public static void method9410() {
      field5473.method6468();
      field5474.method6468();
   }

   @ObfuscatedSignature(descriptor = "()Lym;")
   @ObfuscatedName("zl")
   public SpritePixels method9417() {
      if (this.field5476 * 1914620357 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)NodeCache.method6460(field5474, this.field5476 * 1914620357);
         if (var1 != null) {
            return var1;
         } else {
            var1 = classPN.method9084(classKJ.field3603, 1914620357 * this.field5477, 0, -2146458288);
            if (null != var1) {
               field5473.method6464(var1, 1914620357 * this.field5477);
            }

            return var1;
         }
      }
   }
}
