import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("em")
public class classEM extends Node {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field1789 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field1788 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   public static final int field1787 = 5;
   @ObfuscatedName("as")
   int field1784;
   @ObfuscatedName("ax")
   int[] field1783;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final int field1790 = 1;
   @ObfuscatedSignature(descriptor = "Lef;")
   @ObfuscatedName("aa")
   classEF field1786;
   @ObfuscatedName("ac")
   int[][] field1785;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcm;)Z")
   @ObfuscatedName("mp")
   public static boolean method3951(World var0) {
      return var0 == null ? var0.method2504() : (classXE.field6828.vmethod8((byte)1) & -1521705516 * var0.field1158) != 0;
   }

   @ObfuscatedSignature(descriptor = "(B)Lef;")
   @ObfuscatedName("af")
   public classEF method3948(byte var1) {
      try {
         return this.field1786;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "em.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   public int method3947(int var1) {
      try {
         return 1690200285 * this.field1784;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "em.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lef;")
   @ObfuscatedName("ab")
   public classEF method3949() {
      return this.field1786;
   }

   @ObfuscatedSignature(descriptor = "()Lef;")
   @ObfuscatedName("ae")
   public classEF method3950() {
      return this.field1786;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("at")
   static void method3952(int var0) {
      try {
         if (-47366135 * classBF.field384 == 34) {
            if (var0 != Integer.MAX_VALUE) {
               throw new IllegalStateException();
            }

            classBJ.method1121(false, -347989319);
         }

         classBF.field412 = false;
         classYW.method13737(0, 0, classYW.field7100, classYW.field7099, 0);
         classNI.field4671.method13552(classBF.field385 * -329898575, 0);
         classFR.field2389.method13552(382 + classBF.field385 * -329898575, 0);
         IndexedSprite.method13853(AABB.field2370, 382 + classBF.field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "em.at(" + ')');
      }
   }

   public classEM(int var1, byte[] var2) {
      classXY var3 = new classXY(var2);
      this.field1784 = var3.method13095(-346779531) * 776449909;
      this.field1783 = new int[1690200285 * this.field1784];
      this.field1785 = new int[1690200285 * this.field1784][];

      for (int var4 = 0; var4 < 1690200285 * this.field1784; var4++) {
         this.field1783[var4] = var3.method13095(-346779531);
      }

      for (int var6 = 0; var6 < this.field1784 * 1690200285; var6++) {
         this.field1785[var6] = new int[var3.method13095(-346779531)];
      }

      for (int var7 = 0; var7 < this.field1784 * 1690200285; var7++) {
         for (int var5 = 0; var5 < this.field1785[var7].length; var5++) {
            this.field1785[var7][var5] = var3.method13095(-346779531);
         }
      }

      if (var3.field6953 * 702114061 < var3.field6952.length) {
         int var8 = var3.method13099(1114906404);
         if (var8 > 0) {
            this.field1786 = new classEF(var3, var8);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("az")
   public static int method3946(int var0, int var1) {
      try {
         VarbitComposition var2 = classOL.method8542(var0, (short)1781);
         int var3 = -1681019861 * var2.field5418;
         int var4 = -526564793 * var2.field5417;
         int var5 = 2095283109 * var2.field5419;
         int var6 = classLB.field4083[var5 - var4];
         return classLB.field4088[var3] >> var4 & var6;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "em.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("fa")
   static final void method3953(byte var0) {
      try {
         classKL.method6536(-1889122330);
         classDO.method3523((byte)71);
         classGI.method5493(-1952648005);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "em.fa(" + ')');
      }
   }
}
