import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("il")
public class classIL extends classHB {
   @ToRemove(unused = "true")
   @ObfuscatedName("cf")
   static final int field2987 = 74;
   @ObfuscatedName("af")
   long field2988;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field2984 = 50;
   @ToRemove(unused = "true")
   @ObfuscatedName("cb")
   static final int field2986 = 149;
   @ObfuscatedName("az")
   int field2985;
   @ObfuscatedSignature(descriptor = "Lmj;")
   @ObfuscatedName("hp")
   static classMJ field2989;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lle;Lxy;)V")
   @ObfuscatedName("qf")
   public static void method6264(classLE var0, classXY var1) {
      if (var0 == null) {
         var0.method6820();
      }

      long var2 = 8891344905762583551L * var0.field4115;
      var2 /= 10L;
      if (var2 < 0L) {
         var2 = 0L;
      } else if (var2 > 65535L) {
         var2 = 65535L;
      }

      var1.method13031((int)var2, -1542382992);
      long var4 = -1605016655774630343L * var0.field4114;
      var4 /= 10L;
      if (var4 < 0L) {
         var4 = 0L;
      } else if (var4 > 65535L) {
         var4 = 65535L;
      }

      var1.method13031((int)var4, -1542382992);
      long var6 = var0.field4111 * 1011984226548346587L;
      var6 /= 10L;
      if (var6 < 0L) {
         var6 = 0L;
      } else if (var6 > 65535L) {
         var6 = 65535L;
      }

      var1.method13031((int)var6, -1542382992);
      var1.method13031(968782671 * var0.field4116, -1542382992);
      var1.method13031(-1694632695 * var0.field4118, -1542382992);
      var1.method13031(-1144147315 * var0.field4117, -1542382992);
      var1.method13031(var0.field4120 * -154512739, -1542382992);
   }

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   @Override
   void vmethod499(ClanSettings var1, byte var2) {
      try {
         var1.method6328(this.field2985 * -369383987, -2440418005129830071L * this.field2988);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "il.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   @Override
   void vmethod498(classXY var1, int var2) {
      try {
         this.field2985 = classXY.method13113(var1, (byte)1) * -904278267;
         this.field2988 = classXY.method13115(var1, 1820342398) * 3653361061942911225L;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "il.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   @Override
   void vmethod503(ClanSettings var1) {
      var1.method6328(this.field2985 * -369383987, -2440418005129830071L * this.field2988);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   @Override
   void vmethod500(classXY var1) {
      this.field2985 = classXY.method13113(var1, (byte)1) * -904278267;
      this.field2988 = classXY.method13115(var1, -642246786) * 3653361061942911225L;
   }

   classIL(classIF var1) {
      this.this$0 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   @Override
   void vmethod502(ClanSettings var1) {
      var1.method6328(this.field2985 * -369383987, -2440418005129830071L * this.field2988);
   }

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   @Override
   void vmethod501(classXY var1) {
      this.field2985 = classXY.method13113(var1, (byte)1) * -904278267;
      this.field2988 = classXY.method13115(var1, 1420762225) * 3653361061942911225L;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   @Override
   void vmethod504(ClanSettings var1) {
      var1.method6328(this.field2985 * -369383987, -2440418005129830071L * this.field2988);
   }
}
