import java.util.function.Consumer;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("by")
public class classBY {
   @ToRemove(unused = "true")
   @ObfuscatedName("ad")
   public static final int field607 = 12;
   @ObfuscatedName("af")
   int field609;
   @ObfuscatedName("ae")
   int field608;
   @ObfuscatedName("ab")
   int field606;
   @ObfuscatedName("ag")
   int field610;
   @ObfuscatedName("as")
   int field612;
   @ObfuscatedName("ax")
   String field616;
   @ObfuscatedName("ac")
   String field615;
   @ObfuscatedName("aa")
   boolean field618;
   @ObfuscatedName("az")
   int field613;
   @ObfuscatedName("ay")
   static byte[][][] field619;
   @ObfuscatedName("bs")
   static int field614;
   @ObfuscatedName("ln")
   public Consumer field617;
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field611 = 3;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("qx")
   public int method1360() {
      return this.field610 * -93349349;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("cg")
   public void method1361(String var1) {
      this.field616 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("zf")
   public String method1362() {
      return this.field615;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("qm")
   public void method1363(int var1) {
      this.field610 = var1 * 2074062867;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ya")
   public void method1364(int var1) {
      this.field613 = var1 * 100846287;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("me")
   public void method1365(int var1) {
      this.field612 = var1 * 829407595;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("up")
   public void method1366(int var1) {
      this.field606 = var1 * 1973962571;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("pu")
   public void method1367(int var1) {
      this.field608 = var1 * -282371447;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("sm")
   public int method1368() {
      return this.field608 * 568212409;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("em")
   public String method1369() {
      return this.field616;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("zw")
   public int method1370() {
      return this.field612 * -522427581;
   }

   @ObfuscatedSignature(descriptor = "(IIIIB)I")
   @ObfuscatedName("ab")
   static int method1357(int var0, int var1, int var2, int var3, byte var4) {
      try {
         return var0 * var2 + var1 * var3;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "by.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("fc")
   static final void method1359(int var0, byte var1) {
      try {
         var0 = Math.max(Math.min(var0, 100), 0);
         var0 = 100 - var0;
         float var2 = var0 / 200.0F + 0.5F;
         classHA.method6019(var2);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "by.fc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("xx")
   public void method1371(int var1) {
      this.field609 = var1 * -385576485;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("gm")
   public void method1372(String var1) {
      this.field615 = var1;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("im")
   public int method1373() {
      return this.field609 * 1064501843;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fn")
   public int method1374() {
      return this.field606 * -675640733;
   }

   classBY() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgn;)[I")
   @ObfuscatedName("nz")
   public static int[] method1358(classGN var0) {
      if (var0 == null) {
         var0.method5574();
      }

      return var0.field2621;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("iz")
   public int method1375() {
      return this.field613 * -1898447825;
   }

   @ObfuscatedSignature(descriptor = "(III)I")
   @ObfuscatedName("af")
   static int method1356(int var0, int var1, int var2) {
      try {
         ItemContainer var3 = (ItemContainer)HashTable.method13432(ItemContainer.field454, var0);
         if (null == var3) {
            if (var2 <= -829989290) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else {
            if (var1 >= 0) {
               if (var2 <= -829989290) {
                  throw new IllegalStateException();
               }

               if (var1 < var3.field455.length) {
                  return var3.field455[var1];
               }

               if (var2 <= -829989290) {
                  throw new IllegalStateException();
               }
            }

            return 0;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "by.af(" + 41);
      }
   }
}
