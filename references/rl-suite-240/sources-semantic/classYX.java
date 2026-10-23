import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yx")
public class classYX {
   @ObfuscatedName("af")
   short[] field7103;
   @ObfuscatedName("az")
   int[] field7104 = new int[8];

   public classYX(NPCComposition var1) {
      this.field7103 = new short[8];
      int var2 = 0;
      if (NPCComposition.method9014(var1, -267153217)) {
         var2 = var1.method9015((byte)112).length;
         System.arraycopy(var1.method9015((byte)105), 0, this.field7104, 0, var2);
         System.arraycopy(var1.method9021(-908907734), 0, this.field7103, 0, var2);
      }

      for (int var3 = var2; var3 < 8; var3++) {
         this.field7104[var3] = -1;
         this.field7103[var3] = -1;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[S")
   @ObfuscatedName("af")
   public short[] method13809(int var1) {
      try {
         return this.field7103;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yx.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IISI)V")
   @ObfuscatedName("ae")
   public void method13811(int var1, int var2, short var3, int var4) {
      try {
         this.field7104[var1] = var2;
         this.field7103[var1] = var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "yx.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()[I")
   @ObfuscatedName("ag")
   public int[] method13805() {
      return this.field7104;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwd;Lzl;)V")
   @ObfuscatedName("xl")
   public static void method13808(classWD var0, classZL var1) {
      if (var0 == null) {
         var0.method12392(var1);
      } else {
         var0.field6666 = var1;
      }
   }

   @ObfuscatedSignature(descriptor = "()[S")
   @ObfuscatedName("as")
   public short[] method13810() {
      return this.field7103;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyx;[I[S)V")
   @ObfuscatedName("bw")
   public static void method13815(classYX var0, int[] var1, short[] var2) {
      if (var0 == null) {
         var0.method13805();
      }

      var0.field7104 = var1;
      var0.field7103 = var2;
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("ac")
   public void method13812(int var1, int var2, short var3) {
      this.field7104[var1] = var2;
      this.field7103[var1] = var3;
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("aa")
   public void method13813(int var1, int var2, short var3) {
      this.field7104[var1] = var2;
      this.field7103[var1] = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lom;IJ)J")
   @ObfuscatedName("rs")
   public static long method13817(ObjectComposition var0, int var1, long var2) {
      return classBW.method1275(var0.field4929, var1, var2);
   }

   @ObfuscatedSignature(descriptor = "([I[SI)V")
   @ObfuscatedName("ab")
   public void method13816(int[] var1, short[] var2, int var3) {
      try {
         this.field7104 = var1;
         this.field7103 = var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "yx.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIS)V")
   @ObfuscatedName("ao")
   public void method13814(int var1, int var2, short var3) {
      this.field7104[var1] = var2;
      this.field7103[var1] = var3;
   }

   @ObfuscatedSignature(descriptor = "(Lyx;I)[I")
   @ObfuscatedName("vl")
   public static int[] method13806(classYX var0, int var1) {
      if (var0 == null) {
         var0.method13807(var1);
      }

      try {
         return var0.field7104;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yx.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[I")
   @ObfuscatedName("az")
   public int[] method13807(int var1) {
      try {
         return this.field7104;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "yx.az(" + ')');
      }
   }
}
