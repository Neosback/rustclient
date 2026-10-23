import java.util.Arrays;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gw")
public class classGW {
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("af")
   static final classGW field2718 = new classGW(2, 16, 16);
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("ae")
   static final classGW field2717 = new classGW(6, 32, 32);
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("az")
   static final classGW field2715 = new classGW(3, 8, 8);
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("ab")
   static final classGW field2716 = new classGW(0, 48, 48);
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("ax")
   static final classGW field2721 = new classGW(1, 128, 128);
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("as")
   static final classGW field2720 = new classGW(5, 96, 96);
   @ObfuscatedSignature(descriptor = "Lgw;")
   @ObfuscatedName("ag")
   static final classGW field2719 = new classGW(4, 64, 64);
   @ObfuscatedName("ao")
   final int field2725;
   @ObfuscatedSignature(descriptor = "[Lgw;")
   @ObfuscatedName("aa")
   static final classGW[] field2722 = classEA.method3812(-971908582);
   @ObfuscatedName("al")
   final int field2724;
   @ObfuscatedName("ac")
   final int field2723;
   @ToRemove(unused = "true")
   @ObfuscatedName("ct")
   static final int field2727 = 109;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   public static final int field2726 = 31;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lgw;")
   @ObfuscatedName("ae")
   static classGW[] method5840() {
      return new classGW[]{field2716, field2721, field2718, field2715, field2719, field2720, field2717};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgw;)I")
   @ObfuscatedName("as")
   public static int method5842(classGW var0) {
      return var0.field2723 * 185898137 * var0.field2724 * 1062075444;
   }

   static {
      Arrays.sort(field2722, new classGD());
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ac")
   int method5843() {
      return this.field2723 * 645983023 * this.field2724 * 2032697513;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Ljava/lang/String;)I")
   @ObfuscatedName("le")
   public static int method5848(IndexDataBase var0, String var1) {
      if (var0 == null) {
         var0.method11911();
      }

      var1 = var1.toLowerCase();
      return var0.field6442.method14236(classQI.method9632(var1, 904186689), -2110438931);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lgw;")
   @ObfuscatedName("ab")
   static classGW[] method5841() {
      return new classGW[]{field2716, field2721, field2718, field2715, field2719, field2720, field2717};
   }

   classGW(int var1, int var2, int var3) {
      this.field2725 = var1 * -1672527929;
      this.field2724 = 1319833 * var2;
      this.field2723 = var3 * -1244513329;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ax")
   int method5844() {
      return this.field2723 * 645983023 * this.field2724 * 2032697513;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ag")
   int method5845() {
      return this.field2723 * 645983023 * this.field2724 * 2032697513;
   }

   @ObfuscatedSignature(descriptor = "(Lgw;I)I")
   @ObfuscatedName("ks")
   public static int method5846(classGW var0, int var1) {
      if (var0 == null) {
         return var0.method5847(var1);
      } else {
         try {
            return var0.field2723 * 645983023 * var0.field2724 * 2032697513;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "gw.af(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   int method5847(int var1) {
      try {
         return this.field2724 * 645983023 * this.field2723 * 2032697513;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gw.af(" + 41);
      }
   }
}
