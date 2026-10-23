import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cv")
public class classCV extends Node {
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final long field1302 = 4095L;
   @ObfuscatedName("ab")
   int field1298;
   @ObfuscatedName("ae")
   int field1295;
   @ObfuscatedName("af")
   int field1297;
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field1299 = 19;
   @ObfuscatedName("az")
   int field1296;
   @ObfuscatedName("at")
   static String[] field1303;
   @ObfuscatedName("or")
   static int field1301;
   @ToRemove(unused = "true")
   @ObfuscatedName("bt")
   static final int field1300 = 47;

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ab")
   void method2675(int var1, int var2, int var3, int var4) {
      this.field1298 = -6191587 * var1;
      this.field1296 = var2 * 1027400933;
      this.field1297 = var3 * 460302007;
      this.field1295 = var4 * 1157610687;
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("az")
   void method2676(int var1, int var2, int var3, int var4, int var5) {
      try {
         this.field1298 = -6191587 * var1;
         this.field1296 = var2 * 1027400933;
         this.field1297 = var3 * 460302007;
         this.field1295 = var4 * 1157610687;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cv.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("st")
   public int method2685() {
      return this.field1297 * -808970489;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lclient;)Z")
   @ObfuscatedName("ja")
   public static boolean method2684(client var0) {
      if (var0 == null) {
         var0.method2452();
      }

      return 1998159325 * var0.field794 == 1;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ae")
   void method2677(int var1, int var2, int var3, int var4) {
      this.field1298 = -6191587 * var1;
      this.field1296 = var2 * 1027400933;
      this.field1297 = var3 * 298986330;
      this.field1295 = var4 * 1157610687;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lpv;I)I")
   @ObfuscatedName("am")
   public static int method2681(classPV var0, int var1) {
      return var0.field5428[var1 & 31];
   }

   classCV(int var1, int var2, int var3, int var4) {
      this.field1298 = var1 * -6191587;
      this.field1296 = var2 * 1027400933;
      this.field1297 = var3 * 460302007;
      this.field1295 = var4 * 1157610687;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcv;IIII)V")
   @ObfuscatedName("hf")
   public static void method2678(classCV var0, int var1, int var2, int var3, int var4) {
      if (var0 == null) {
         var0.method2676(var1, var1, var1, var1, var1);
      } else {
         var0.field1298 = -6191587 * var1;
         var0.field1296 = var2 * 1027400933;
         var0.field1297 = var3 * 460302007;
         var0.field1295 = var4 * 1157610687;
      }
   }

   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("ab")
   public static int method2679(long var0) {
      try {
         return (int)(var0 >>> 16 & 7L);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cv.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("gn")
   static final void method2683(String var0, byte var1) {
      try {
         if (null == classIB.field2933) {
            if (var1 <= 1) {
               throw new IllegalStateException();
            }
         } else {
            classJL var2 = classEF.method3863(classJS.FRIENDCHAT_KICK, client.field796.field1533, (byte)-69);
            classXY.method13029(var2.field3341, classAU.method709(var0, (byte)77), (byte)-96);
            var2.field3341.method13054(var0, (short)7263);
            client.field796.method3374(var2, -1301097035);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cv.gn(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;)I")
   @ObfuscatedName("se")
   public static int method2682(classLH var0) {
      return 236265915 * var0.field4151 / classZD.method13953(var0.field4157, (byte)-7);
   }

   @ObfuscatedSignature(descriptor = "([BIB)I")
   @ObfuscatedName("bs")
   public static int method2680(byte[] var0, int var1, byte var2) {
      try {
         return classPY.method9423(var0, 0, var1, -1206335529);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cv.bs(" + 41);
      }
   }
}
