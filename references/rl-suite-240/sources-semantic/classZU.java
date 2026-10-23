import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("zu")
public class classZU extends classVJ {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field7274 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field7273 = 255;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   static IndexDataBase field7275;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ab")
   static NodeCache field7269 = new NodeCache(64);
   @ObfuscatedName("ag")
   public final int[][] field7272;
   @ObfuscatedName("as")
   public final Object[][] field7270;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field7271 = 4;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   public static void method14246() {
      field7269.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method14240(IndexDataBase var0) {
      field7275 = var0;
   }

   @ObfuscatedSignature(descriptor = "()[[Ljava/lang/Object;")
   @ObfuscatedName("xy")
   public Object[][] method14248() {
      return this.field7270;
   }

   @ObfuscatedSignature(descriptor = "()[[I")
   @ObfuscatedName("ry")
   public int[][] method14249() {
      return this.field7272;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public static void method14247() {
      field7269.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("as")
   public static void method14241(IndexDataBase var0) {
      field7275 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ax")
   public static void method14242(IndexDataBase var0) {
      field7275 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lzu;")
   @ObfuscatedName("ac")
   public static classZU method14244(int var0) {
      classZU var1 = (classZU)NodeCache.method6460(field7269, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field7275.method11942(-1466087862, var0, -644551988);
         var1 = new classZU(new classXY(var2), var0, -1);
         field7269.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lzu;")
   @ObfuscatedName("aa")
   public static classZU method14245(int var0) {
      classZU var1 = (classZU)NodeCache.method6460(field7269, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field7275.method11942(39, var0, -1938956184);
         var1 = new classZU(new classXY(var2), var0, -1);
         field7269.method6464(var1, var0);
         return var1;
      }
   }

   classZU(classXY var1, int var2, int var3) {
      int[][] var4 = (int[][])null;
      Object[][] var5 = (Object[][])null;
      if (null != var1 && null != var1.field6952) {
         label106:
         while (true) {
            int var6 = var1.method13095(-346779531);
            switch (var6) {
               case 0:
                  break label106;
               case 1:
                  int var7 = Math.max(var1.method13095(-346779531), var3);
                  var4 = new int[var7][];
                  Object[][] var9 = var5;

                  for (int var10 = var1.method13095(-346779531); 255 != var10; var10 = var1.method13095(-346779531)) {
                     int var11 = var10 & 127;
                     boolean var12 = 0 != (var10 & 128);
                     int[] var13 = new int[var1.method13095(-346779531)];

                     for (int var14 = 0; var14 < var13.length; var14++) {
                        var13[var14] = var1.method13150((short)26360);
                     }

                     var4[var11] = var13;
                     if (var12) {
                        if (null == var9) {
                           var9 = new Object[var4.length][];
                        }

                        int var17 = var1.method13150((short)32343);
                        Object[] var18 = new Object[var13.length * var17];

                        for (int var19 = 0; var19 < var17; var19++) {
                           for (int var20 = 0; var20 < var13.length; var20++) {
                              int var21 = var20 + var19 * var13.length;
                              classYY var22 = FriendContainer.method10175(var13[var20], 447096713);
                              var18[var21] = classYY.method13827(var22, var1, 827988912);
                           }
                        }

                        var9[var11] = var18;
                     }
                  }

                  var5 = var9;
            }
         }
      }

      if (null == var4 && var3 >= 0) {
         var4 = new int[var3][];
      }

      if (null == var5 && var3 >= 0) {
         var5 = new Object[var3][];
      }

      this.field7272 = var4;
      this.field7270 = var5;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method14243(IndexDataBase var0) {
      field7275 = var0;
   }
}
