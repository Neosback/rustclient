import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ri")
public class ChatPlayer extends Nameable implements net.runelite.api.ChatPlayer {
   @ObfuscatedName("as")
   public int field5668;
   @ObfuscatedName("ax")
   public int field5669;
   @ObfuscatedName("ag")
   public int field5667 = -849976631;
   @ToRemove(unused = "true")
   @ObfuscatedName("bg")
   static final int field5670 = 40;

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bg")
   public int method10007(int var1) {
      try {
         return -760305529 * this.field5667;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ri.bg(" + 41);
      }
   }

   ChatPlayer() {
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bo")
   void method10004(int var1, int var2) {
      this.field5667 = var1 * 849976631;
      this.field5668 = 282868315 * var2;
   }

   @ObfuscatedSignature(descriptor = "(Lri;I)Z")
   @ObfuscatedName("fr")
   public static boolean method10010(ChatPlayer var0, int var1) {
      if (var0 == null) {
         var0.method10013(var1);
      }

      try {
         boolean var10000;
         if (var0.field5667 * -760305529 > 0) {
            if (var1 >= -2021751741) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ri.bj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bk")
   void method10005(int var1, int var2) {
      this.field5667 = var1 * 849976631;
      this.field5668 = 282868315 * var2;
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("bt")
   void method10006(int var1, int var2, byte var3) {
      try {
         this.field5667 = var1 * 849976631;
         this.field5668 = 282868315 * var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "ri.bt(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bu")
   public int method10008() {
      return -760305529 * this.field5667;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvh;II)I")
   @ObfuscatedName("vw")
   public static int method10014(classVH var0, int var1, int var2) {
      return var0 == null ? var0.method12021(var1, var1) : var0.field6502[var1][var2];
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bb")
   public boolean method10011() {
      return this.field5667 * -714129562 > 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lri;)I")
   @ObfuscatedName("xu")
   public static int method10009(ChatPlayer var0) {
      if (var0 == null) {
         var0.method10012();
      }

      return -1921188773 * var0.field5667;
   }

   public int getWorld() {
      return this.field5667 * -760305529;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bw")
   public boolean method10012() {
      return this.field5667 * -760305529 > 0;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fy")
   public int method10015() {
      return this.field5669 * 1234122453;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bj")
   public boolean method10013(int var1) {
      try {
         boolean var10000;
         if (this.field5667 * -760305529 > 0) {
            if (var1 >= -2021751741) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ri.bj(" + ')');
      }
   }
}
