import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("up")
public class classUP {
   @ObfuscatedName("az")
   int field6360 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field6362 = 7;
   @ObfuscatedName("ar")
   static int field6361;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bd")
   public int method11514() {
      int var2 = this.field6360 * 1379695375;
      return var2 >> 3;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bz")
   public void method11517(int var1) {
      this.field6360 = (this.field6360 * 1379695375 + var1 & classCS.method2567(-1520924805)) * -1993796625;
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("ac")
   public void method11490(int var1, int var2, byte var3) {
      try {
         var1 &= classCS.method2567(-1520924805);
         var2 &= classCS.method2567(-1520924805);
         this.field6360 = Math.max(var1, Math.min(var2, 1379695375 * this.field6360)) * -1993796625;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "up.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("ba")
   public float method11501() {
      int var2 = this.field6360 * 1379695375;
      int var4 = var2 & 16383;
      return (float)(3.834951969714103E-4 * var4);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)F")
   @ObfuscatedName("ye")
   public static float method11502(classUP var0) {
      int var2 = var0.field6360 * 166390186;
      int var4 = var2 & 16383;
      return (float)(3.834951969714103E-4 * var4);
   }

   @ObfuscatedSignature(descriptor = "(S)I")
   @ObfuscatedName("ao")
   public int method11500(short var1) {
      try {
         return 1379695375 * this.field6360;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "up.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ay")
   public int method11507(byte var1) {
      try {
         return classLL.method7162(this.field6360 * 1379695375, 1222866659);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "up.ay(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("al")
   public float method11510(int var1) {
      try {
         return classED.method3841(this.field6360 * 1379695375, 1196830319);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "up.aq(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)F")
   @ObfuscatedName("ad")
   public float method11512(byte var1) {
      try {
         int var3 = this.field6360 * 1379695375;
         return classGB.field2537[var3];
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "up.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ap")
   public int method11515(int var1) {
      try {
         int var3 = this.field6360 * 1379695375;
         return var3 >> 3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "up.ap(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   public static int method11478(int var0) {
      return Actor.method3090(var0, 2028453267);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public static int method11484() {
      return 16383;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("aw")
   static float method11480(int var0) {
      return classGB.field2536[var0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("ak")
   static float method11481(int var0) {
      return classGB.field2536[var0];
   }

   @ObfuscatedSignature(descriptor = "(Lup;II)V")
   @ObfuscatedName("io")
   public static void method11518(classUP var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11498(var1, var1);
      }

      try {
         var0.field6360 = (var0.field6360 * 1379695375 + var1 & classCS.method2567(-1520924805)) * -1993796625;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "up.au(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   public static int method11482() {
      return 16384;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   public static int method11479(int var0) {
      return Actor.method3090(var0, 1974396234);
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("aq")
   public float method11503(int var1) {
      try {
         int var3 = this.field6360 * 1379695375;
         int var5 = var3 & 16383;
         return (float)(3.834951969714103E-4 * var5);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "up.al(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public static int method11485() {
      return 16383;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(D)I")
   @ObfuscatedName("ah")
   public static int method11487(double var0) {
      return (int)(2607.5945876176133 * var0) & -328054676;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)F")
   @ObfuscatedName("tl")
   public static float method11513(classUP var0) {
      if (var0 == null) {
         var0.method11509();
      }

      int var2 = var0.field6360 * 1379695375;
      return classGB.field2537[var2];
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bx")
   public void method11491(int var1, int var2) {
      var1 &= classCS.method2567(-1520924805);
      var2 &= classCS.method2567(-1520924805);
      this.field6360 = Math.max(var1, Math.min(var2, 1379695375 * this.field6360)) * -1993796625;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   public static int method11486() {
      return 16383;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bp")
   public void method11492(int var1, int var2) {
      var1 &= classCS.method2567(-1520924805);
      var2 &= classCS.method2567(-1520924805);
      this.field6360 = Math.max(var1, Math.min(var2, 1379695375 * this.field6360)) * -3930023;
   }

   @ObfuscatedSignature(descriptor = "(Lup;II)V")
   @ObfuscatedName("ag")
   public static void method11494(classUP var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11499(var1, var1);
      } else {
         try {
            var0.field6360 = (var1 & classCS.method2567(-1520924805)) * -1993796625;
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "up.aa(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzs;I)Lzs;")
   @ObfuscatedName("qu")
   public static ScriptEventBuilder method11522(ScriptEventBuilder var0, int var1) {
      if (var0 == null) {
         var0.method14222(var1, var1);
      }

      var0.field7262 = -1434072017 * var1;
      return var0;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bg")
   public void method11495(int var1) {
      this.field6360 = (var1 & classCS.method2567(-1520924805)) * -1993796625;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;II)V")
   @ObfuscatedName("ed")
   public static void method11493(classUP var0, int var1, int var2) {
      if (var0 == null) {
         var0.method11508();
      }

      var1 &= classCS.method2567(-1520924805);
      var2 &= classCS.method2567(-1520924805);
      var0.field6360 = Math.max(var1, Math.min(var2, 1379695375 * var0.field6360)) * -1993796625;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("br")
   public void method11496(int var1) {
      this.field6360 = (var1 & classCS.method2567(-1520924805)) * 648604933;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bl")
   public void method11497(int var1) {
      this.field6360 = (var1 & classCS.method2567(-1520924805)) * -1993796625;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bn")
   public static int method11488(int var0) {
      return var0 << 3;
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("by")
   public float method11511() {
      return classED.method3841(this.field6360 * 1379695375, 1196830319);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bw")
   public int method11505() {
      int var2 = this.field6360 * 1379695375;
      return classBF.method1000(var2, (byte)-28);
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("il")
   public float method11523() {
      return this.method11512((byte)111);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dm")
   public int method11524() {
      return this.method11507((byte)-45);
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("lu")
   public float method11525() {
      return this.method11510(2092171063);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bi")
   public int method11508() {
      return classLL.method7162(this.field6360 * -990654914, 1222866659);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;I)V")
   @ObfuscatedName("qz")
   public static void method11519(classUP var0, int var1) {
      if (var0 == null) {
         var0.method11516(var1);
      }

      var0.field6360 = (var0.field6360 * 1379695375 + var1 & classCS.method2567(-1520924805)) * 259646430;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzb;Ljava/lang/String;Lxy;)V")
   @ObfuscatedName("bs")
   public static void method11489(classZB var0, String var1, classXY var2) {
      if (var0 == null) {
         var0.method13868(var1, var2);
      }

      var2.method13054(var1, (short)23706);
   }

   public classUP(int var1) {
      this.field6360 = (var1 & classCS.method2567(-1520924805)) * -1993796625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bb")
   public int method11509() {
      return classLL.method7162(this.field6360 * 1379695375, 1222866659);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lup;)F")
   @ObfuscatedName("dx")
   public static float method11504(classUP var0) {
      int var2 = var0.field6360 * 1379695375;
      int var4 = var2 & 16383;
      return (float)(3.834951969714103E-4 * var4);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bt")
   public void method11520(int var1) {
      this.field6360 = (this.field6360 * 1379695375 + var1 & classCS.method2567(-1520924805)) * -1993796625;
   }

   @ObfuscatedSignature(descriptor = "(Lup;I)I")
   @ObfuscatedName("co")
   public static int method11506(classUP var0, int var1) {
      if (var0 == null) {
         var0.method11516(var1);
      }

      try {
         int var3 = var0.field6360 * 1379695375;
         return classBF.method1000(var3, (byte)-79);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "up.aj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bv")
   public void method11521(int var1) {
      this.field6360 = (this.field6360 * 277125606 + var1 & classCS.method2567(-1520924805)) * -1993796625;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("id")
   public int method11526() {
      return method11506(this, 1486466529);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;ILjava/lang/StringBuilder;Lqi;B)Ljava/lang/String;")
   @ObfuscatedName("az")
   public static String method11483(String var0, int var1, StringBuilder var2, classQI var3, byte var4) {
      try {
         if (null == var0) {
            if (var4 != 0) {
               throw new IllegalStateException();
            } else {
               return null;
            }
         } else if (var0.isEmpty()) {
            if (var4 != 0) {
               throw new IllegalStateException();
            } else {
               return var0;
            }
         } else {
            String var5;
            try {
               var5 = classLA.method6700(var0, var1, var2, var3, -1453270208);
            } finally {
               var2.setLength(0);
               var3.method9628(1452371636);
            }

            return var5;
         }
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "up.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aj")
   public int method11516(int var1) {
      try {
         int var3 = this.field6360 * 1379695375;
         return var3 >> 3;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "up.ap(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("au")
   public void method11498(int var1, int var2) {
      try {
         this.field6360 = (var1 & classCS.method2567(-1520924805)) * -1993796625;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "up.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("aa")
   public void method11499(int var1, int var2) {
      try {
         this.field6360 = (var1 & classCS.method2567(-1520924805)) * -1993796625;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "up.aa(" + ')');
      }
   }
}
