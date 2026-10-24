import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("kc")
public class classKC implements classXZ {
   @ObfuscatedSignature(descriptor = "Lkc;")
   @ObfuscatedName("ae")
   public static final classKC field3541 = new classKC(2, 2);
   @ObfuscatedSignature(descriptor = "Lkc;")
   @ObfuscatedName("af")
   static final classKC field3542 = new classKC(1, 1);
   @ObfuscatedSignature(descriptor = "Lkc;")
   @ObfuscatedName("az")
   static final classKC field3540 = new classKC(0, 0);
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final int field3543 = 71;
   @ObfuscatedName("ag")
   final int field3539;
   @ToRemove(unused = "true")
   @ObfuscatedName("by")
   public static final int field3544 = 262144;
   @ObfuscatedName("ab")
   public final int field3538;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return -1798349439 * this.field3539;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return -1798349439 * this.field3539;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kc.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lkc;")
   @ObfuscatedName("as")
   public static classKC[] method6493() {
      return new classKC[]{field3542, field3541, field3540};
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return 1896306702 * this.field3539;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bk")
   static final int method6497(int var0, int var1) {
      try {
         return Math.min(Math.max(var0, Scene.method4161(client.field840)), Scene.method4168(client.field840));
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "kc.bk(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -1798349439 * this.field3539;
   }

   classKC(int var1, int var2) {
      this.field3538 = var1 * -340298519;
      this.field3539 = -1711873407 * var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lkc;")
   @ObfuscatedName("ax")
   public static classKC[] method6494() {
      return new classKC[]{field3542, field3541, field3540};
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIIS)I")
   @ObfuscatedName("ce")
   static int method6499(WorldView var0, int var1, int var2, int var3, int var4, short var5) {
      try {
         if (var4 == 0) {
            return classKV.method6613(var0, var1, var2, var3, (byte)122);
         } else {
            int var6 = var4 / 2;
            int var7 = var1 - var6;
            int var8 = var2 - var6;
            int var9 = var6 + var1;
            int var10 = var2 + var6;
            int var11 = classKY.method6652(var7, (byte)-96) + 1;
            int var12 = classKY.method6652(var8, (byte)2) + 1;
            int var13 = classKY.method6652(var9, (byte)-14);
            int var14 = classKY.method6652(var10, (byte)-10);
            int var15 = Integer.MAX_VALUE;

            for (int var16 = var11; var16 <= var13; var16++) {
               if (var5 >= 160) {
                  throw new IllegalStateException();
               }

               for (int var17 = var12; var17 <= var14; var17++) {
                  var15 = Math.min(
                     var15, classKV.method6613(var0, classKY.method6649(var16, 1976574675), classKY.method6649(var17, 999498438), var3, (byte)122)
                  );
               }
            }

            var15 = Math.min(var15, classKV.method6613(var0, var1, var2, var3, (byte)122));
            var15 = Math.min(var15, classKV.method6613(var0, var1 - var6, var2 - var6, var3, (byte)122));
            var15 = Math.min(var15, classKV.method6613(var0, var1 - var6, var2 + var6, var3, (byte)122));
            var15 = Math.min(var15, classKV.method6613(var0, var1 + var6, var2 - var6, var3, (byte)122));
            return Math.min(var15, classKV.method6613(var0, var6 + var1, var6 + var2, var3, (byte)122));
         }
      } catch (RuntimeException var18) {
         throw classEG.method3899(var18, "kc.ce(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;)Lwj;")
   @ObfuscatedName("bn")
   public static classWJ method6496(ObjectComposition var0) {
      return var0.field4961;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;Z)V")
   @ObfuscatedName("au")
   public static void method6495(classLH var0, boolean var1) {
      if (var0 == null) {
         var0.method6931(var1);
      }

      var0.method7114(0, var1, -1233951363);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("cw")
   static boolean method6498(int var0) {
      try {
         boolean var10000;
         if (0 != (client.field853 * -1072700981 & 8)) {
            if (var0 == -1862731287) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "kc.cw(" + ')');
      }
   }
}
