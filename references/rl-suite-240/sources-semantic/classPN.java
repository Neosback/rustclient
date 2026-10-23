import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pn")
public class classPN extends classVJ {
   @ObfuscatedName("ao")
   public final int field5350;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field5355 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field5356 = 7;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field5357 = 24;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ax")
   static IndexDataBase field5360;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ac")
   static NodeCache field5348 = new NodeCache(64);
   @ObfuscatedName("aa")
   final int field5351;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field5354 = 4;
   @ObfuscatedName("al")
   public final long field5352;
   @ObfuscatedName("aj")
   public final String field5349;
   @Deprecated
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5358 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final String field5359 = "m";
   @ObfuscatedName("ay")
   final boolean field5353;

   @ObfuscatedSignature(descriptor = "(Lpn;B)Z")
   @ObfuscatedName("ox")
   public static boolean method9076(classPN var0, byte var1) {
      if (var0 == null) {
         return var0.method9078(var1);
      } else {
         try {
            boolean var10000;
            if (FriendContainer.method10175(var0.field5351 * 1468976603, 1381354391) == classYY.field7113) {
               if (var1 <= 1) {
                  throw new IllegalStateException();
               }

               var10000 = true;
            } else {
               var10000 = false;
            }

            return var10000;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "pn.ag(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("zy")
   public String method9085() {
      return this.field5349;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("aw")
   public Object method9079() {
      if (this.method9075((byte)2)) {
         return this.field5349;
      } else {
         return method9076(this, (byte)36) ? 3335646069137558745L * this.field5352 : -1601970641 * this.field5350;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ax")
   public static void method9064(IndexDataBase var0) {
      field5360 = var0;
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/Object;")
   @ObfuscatedName("as")
   public Object method9080(byte var1) {
      try {
         if (this.method9075((byte)2)) {
            if (var1 == 0) {
               throw new IllegalStateException();
            } else {
               return this.field5349;
            }
         } else if (method9076(this, (byte)79)) {
            if (var1 == 0) {
               throw new IllegalStateException();
            } else {
               return 3335646069137558745L * this.field5352;
            }
         } else {
            return -1601970641 * this.field5350;
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pn.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("mi")
   public int method9086() {
      return this.field5350 * -1601970641;
   }

   classPN(classXY var1, int var2) {
      int var3 = 0;
      int var4 = 0;
      long var5 = 0L;
      String var7 = null;
      boolean var8 = true;
      if (var1 != null && var1.field6952 != null) {
         label61:
         while (true) {
            int var9 = var1.method13095(-346779531);
            switch (var9) {
               case 0:
                  break label61;
               case 1:
                  byte var11 = var1.method13097((byte)17);
                  int var12 = var11 & 255;
                  if (0 == var12) {
                     throw new IllegalArgumentException("" + Integer.toString(var12, 16));
                  }

                  if (var12 >= 128 && var12 < 160) {
                     char var13 = classOH.field4867[var12 - 128];
                     if (var13 == 0) {
                        var13 = '?';
                     }

                     var12 = var13;
                  }

                  char var10 = (char)var12;
                  if ('s' == var10) {
                     var3 = 36;
                  } else {
                     var3 = 0;
                  }
                  continue;
               case 2:
                  var4 = classXY.method13113(var1, (byte)1);
               case 3:
               case 6:
               default:
                  continue;
               case 4:
                  var8 = false;
                  continue;
               case 5:
                  var7 = var1.method13127(146456438);
                  continue;
               case 7:
                  var5 = classXY.method13115(var1, -377259747);
                  continue;
               case 8:
            }

            var3 = var1.method13095(-346779531);
         }
      }

      this.field5351 = var3 * -1905629613;
      this.field5350 = var4 * 1306011855;
      this.field5352 = var5 * -1264378860623596695L;
      this.field5349 = var7;
      this.field5353 = var8;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpn;")
   @ObfuscatedName("ac")
   public static classPN method9065(int var0) {
      classPN var1 = (classPN)NodeCache.method6460(field5348, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5360.method11942(11, var0, -721153574);
         var1 = new classPN(new classXY(var2), var0);
         field5348.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpn;")
   @ObfuscatedName("aa")
   public static classPN method9066(int var0) {
      classPN var1 = (classPN)NodeCache.method6460(field5348, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5360.method11942(11, var0, -1415936845);
         var1 = new classPN(new classXY(var2), var0);
         field5348.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpn;")
   @ObfuscatedName("ao")
   public static classPN method9067(int var0) {
      classPN var1 = (classPN)NodeCache.method6460(field5348, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5360.method11942(11, var0, -863354752);
         var1 = new classPN(new classXY(var2), var0);
         field5348.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public static void method9068() {
      field5348.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aj")
   public static void method9069() {
      field5348.method6468();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ak")
   public Object method9081() {
      if (this.method9075((byte)2)) {
         return this.field5349;
      } else {
         return method9076(this, (byte)108) ? 3335646069137558745L * this.field5352 : -1601970641 * this.field5350;
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method9072() {
      return FriendContainer.method10175(this.field5351 * 1468976603, 1138368900) == classYY.field7111;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   public boolean method9073() {
      return FriendContainer.method10175(this.field5351 * 1468976603, -567015048) == classYY.field7111;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ap")
   public boolean method9074() {
      return FriendContainer.method10175(this.field5351 * 1468976603, -114938271) == classYY.field7111;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("au")
   public boolean method9077() {
      return FriendContainer.method10175(this.field5351 * 1468976603, -68556490) == classYY.field7113;
   }

   @ObfuscatedSignature(descriptor = "()J")
   @ObfuscatedName("bq")
   public long method9087() {
      return this.field5352 * 3335646069137558745L;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ai")
   public Object method9082() {
      if (this.method9075((byte)2)) {
         return this.field5349;
      } else {
         return method9076(this, (byte)41) ? 3335646069137558745L * this.field5352 : -1601970641 * this.field5350;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ay")
   public static void method9070() {
      field5348.method6468();
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("ar")
   public Object method9083() {
      if (this.method9075((byte)2)) {
         return this.field5349;
      } else {
         return method9076(this, (byte)16) ? 3335646069137558745L * this.field5352 : -1601970641 * this.field5350;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ae")
   public static int method9071(int var0, int var1) {
      try {
         var0 = (var0 & 1431655765) + (var0 >>> 1 & 1431655765);
         var0 = (var0 & 858993459) + (var0 >>> 2 & 858993459);
         var0 = var0 + (var0 >>> 4) & 252645135;
         var0 += var0 >>> 8;
         var0 += var0 >>> 16;
         return var0 & 0xFF;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pn.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;III)Lym;")
   @ObfuscatedName("ae")
   public static SpritePixels method9084(IndexDataBase var0, int var1, int var2, int var3) {
      net.runelite.api.SpritePixels var4 = (net.runelite.api.SpritePixels)client.field1041.get(var1);
      if (var4 != null) {
         return ((SpritePixels)var4).method13612();
      } else {
         int var7 = var2;
         int var6 = var1;
         IndexDataBase var5 = var0;
         int var8 = -1864631683;

         try {
            SpritePixels var10000;
            if (!classHK.method6100(var5, var6, var7, (byte)-30)) {
               if (var8 >= -1578504328) {
                  throw new IllegalStateException();
               }

               var5 = null;
               var10000 = var5;
            } else {
               SpritePixels var11 = classSA.method10408((byte)45);
               var10000 = var11;
            }

            return var10000;
         } catch (RuntimeException var9) {
            throw classEG.method3899(var9, "pn.ae(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ab")
   public boolean method9075(byte var1) {
      try {
         boolean var10000;
         if (FriendContainer.method10175(this.field5351 * 1468976603, 1214906981) == classYY.field7111) {
            if (var1 != 2) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pn.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ag")
   public boolean method9078(byte var1) {
      try {
         boolean var10000;
         if (FriendContainer.method10175(this.field5351 * 1468976603, 1381354391) == classYY.field7112) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "pn.ag(" + ')');
      }
   }
}
