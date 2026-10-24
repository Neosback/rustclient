import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yo")
final class classYO implements classYH {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field7020 = 0;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("ir")
   static classVP field7021;

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ac")
   @Override
   public Object vmethod654(classXY var1) {
      return classPD.method8877(var1, false, -193629541);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;I)V")
   @ObfuscatedName("ae")
   @Override
   public void vmethod656(Object var1, classXY var2, int var3) {
      try {
         method13644(this, (classTE)var1, var2, -1771415505);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yo.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)I")
   @ObfuscatedName("rd")
   public static int method13649(ClientPreferences var0) {
      return -1250316135 * var0.field1338;
   }

   @ObfuscatedSignature(descriptor = "(Lxy;B)Ljava/lang/Object;")
   @ObfuscatedName("af")
   @Override
   public Object vmethod653(classXY var1, byte var2) {
      try {
         return classPD.method8877(var1, false, -193629541);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "yo.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)Ljava/lang/Object;")
   @ObfuscatedName("ax")
   @Override
   public Object vmethod655(classXY var1) {
      return classPD.method8877(var1, false, -193629541);
   }

   @ObfuscatedSignature(descriptor = "(Lyo;Lte;Lxy;I)V")
   @ObfuscatedName("pk")
   public static void method13644(classYO var0, classTE var1, classXY var2, int var3) {
      if (var0 == null) {
         var0.method13648(var1, var2, var3);
      } else {
         try {
            var1.method10840(var2, (byte)-34);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "yo.az(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;Lxy;)V")
   @ObfuscatedName("aa")
   @Override
   public void vmethod657(Object var1, classXY var2) {
      method13644(this, (classTE)var1, var2, -588465721);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgp;Llw;IILvv;)V")
   @ObfuscatedName("vx")
   public static void method13650(classGP var0, Widget var1, int var2, int var3, classVV var4) {
      if (var0 == null) {
         var0.method5619(var2, var4, var2);
      } else {
         classKH var5 = var1.method7254(var4, false, -973700585);
         if (null != var5) {
            if (var0.field2637.vmethod163(-1148830535)) {
               SpritePixels.method13589(
                  var0.field2635.vmethod111(-217993828),
                  var2,
                  var3,
                  var5.field3584 * -1246906611,
                  var5.field3583 * -1637380195,
                  25,
                  25,
                  var0.field2637.vmethod186(2137414314),
                  256,
                  var2,
                  var3,
                  var5.field3585,
                  var5.field3582
               );
            } else {
               classYW.method13791(var2, var3, 0, var5.field3585, var5.field3582);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lxy;)V")
   @ObfuscatedName("as")
   void method13645(classTE var1, classXY var2) {
      var1.method10840(var2, (byte)-21);
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lxy;)V")
   @ObfuscatedName("ab")
   void method13646(classTE var1, classXY var2) {
      var1.method10840(var2, (byte)-63);
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lxy;)V")
   @ObfuscatedName("ag")
   void method13647(classTE var1, classXY var2) {
      var1.method10840(var2, (byte)-11);
   }

   @ObfuscatedSignature(descriptor = "(Lte;Lxy;I)V")
   @ObfuscatedName("az")
   void method13648(classTE var1, classXY var2, int var3) {
      try {
         var1.method10840(var2, (byte)-34);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yo.az(" + ')');
      }
   }
}
