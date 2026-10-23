import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("yk")
public final class IndexedObjectSet implements Iterable, net.runelite.api.IndexedObjectSet {
   @ObfuscatedSignature(descriptor = "[Lvo;")
   @ObfuscatedName("ae")
   classVO[] field6994;
   @ObfuscatedSignature(descriptor = "Lyu;")
   @ObfuscatedName("az")
   final classYU field6993;
   @ObfuscatedSignature(descriptor = "Lvo;")
   @ObfuscatedName("ab")
   classVO field6996;
   @ObfuscatedName("af")
   int field6995;

   @ObfuscatedSignature(descriptor = "(Lyk;J)Lvo;")
   @ObfuscatedName("do")
   public static classVO method13449(IndexedObjectSet var0, long var1) {
      if (var0 == null) {
         var0.method13452(var1);
      }

      classVO var3 = var0.field6994[(int)(var1 & var0.field6995 - 1)];

      for (classVO var4 = var3.field6529; var3 != var4; var4 = var4.field6529) {
         if (var4.field6528 == var1) {
            return var4;
         }
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(Lvo;J)V")
   @ObfuscatedName("as")
   public void method13439(classVO var1, long var2) {
      if (var1.field6530 != null) {
         var1.method12078();
      }

      classVO var4 = this.field6994[(int)(var2 & this.field6995 - 1)];
      var1.field6530 = var4.field6530;
      var1.field6529 = var4;
      var1.field6530.field6529 = var1;
      var1.field6529.field6530 = var1;
      var1.field6528 = var2;
   }

   @ObfuscatedSignature(descriptor = "(Lvo;J)V")
   @ObfuscatedName("af")
   public void method13440(classVO var1, long var2) {
      if (var1.field6530 != null) {
         var1.method12078();
      }

      classVO var4 = this.field6994[(int)(var2 & this.field6995 - 1)];
      var1.field6530 = var4.field6530;
      var1.field6529 = var4;
      var1.field6530.field6529 = var1;
      var1.field6529.field6530 = var1;
      var1.field6528 = var2;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("be")
   public Iterator method13447() {
      this.field6993.method13686();
      return this.field6993;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvo;")
   @ObfuscatedName("or")
   public classVO method13451(long var1) {
      return method13449(this, var1);
   }

   @ObfuscatedSignature(descriptor = "(I)Lvo;")
   @ObfuscatedName("aa")
   public classVO method13450(int var1) {
      return this.method13451(var1);
   }

   public IndexedObjectSet(int var1) {
      this.field6995 = var1;
      this.field6994 = new classVO[var1];

      for (int var2 = 0; var2 < var1; var2++) {
         classVO var3 = this.field6994[var2] = new classVO();
         var3.field6529 = var3;
         var3.field6530 = var3;
      }

      this.field6993 = new classYU(this);
   }

   @ObfuscatedSignature(descriptor = "(Lvo;J)V")
   @ObfuscatedName("ag")
   public void method13441(classVO var1, long var2) {
      if (var1.field6530 != null) {
         var1.method12078();
      }

      classVO var4 = this.field6994[(int)(var2 & this.field6995 - 1)];
      var1.field6530 = var4.field6530;
      var1.field6529 = var4;
      var1.field6530.field6529 = var1;
      var1.field6529.field6530 = var1;
      var1.field6528 = var2;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ae")
   public void method13443() {
      for (int var1 = 0; var1 < this.field6995; var1++) {
         classVO var2 = this.field6994[var1];

         while (true) {
            classVO var3 = var2.field6529;
            if (var3 == var2) {
               break;
            }

            var3.method12078();
         }
      }

      this.field6996 = null;
   }

   @Override
   public Iterator iterator() {
      return new classYU(this);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyk;)V")
   @ObfuscatedName("bt")
   public static void method13444(IndexedObjectSet var0) {
      if (var0 == null) {
         var0.method13446();
      }

      for (int var1 = 0; var1 < var0.field6995; var1++) {
         classVO var2 = var0.field6994[var1];

         while (true) {
            classVO var3 = var2.field6529;
            if (var3 == var2) {
               break;
            }

            var3.method12078();
         }
      }

      var0.field6996 = null;
   }

   @ObfuscatedSignature(descriptor = "(Lvo;J)V")
   @ObfuscatedName("ax")
   public void method13442(classVO var1, long var2) {
      if (var1.field6530 != null) {
         var1.method12078();
      }

      classVO var4 = this.field6994[(int)(var2 & this.field6995 - 1)];
      var1.field6530 = var4.field6530;
      var1.field6529 = var4;
      var1.field6530.field6529 = var1;
      var1.field6529.field6530 = var1;
      var1.field6528 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lyk;J)Lvo;")
   @ObfuscatedName("wn")
   public static classVO method13438(IndexedObjectSet var0, long var1) {
      classVO var3 = var0.field6994[(int)(var1 & var0.field6995 - 1)];

      for (var0.field6996 = var3.field6529; var0.field6996 != var3; var0.field6996 = var0.field6996.field6529) {
         if (var0.field6996.field6528 == var1) {
            classVO var4 = var0.field6996;
            var0.field6996 = var0.field6996.field6529;
            return var4;
         }
      }

      var0.field6996 = null;
      return null;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public void method13445() {
      for (int var1 = 0; var1 < this.field6995; var1++) {
         classVO var2 = this.field6994[var1];

         while (true) {
            classVO var3 = var2.field6529;
            if (var3 == var2) {
               break;
            }

            var3.method12078();
         }
      }

      this.field6996 = null;
   }

   @ObfuscatedSignature(descriptor = "(J)Lvo;")
   @ObfuscatedName("az")
   public classVO method13452(long var1) {
      return method13449(this, var1);
   }

   @ObfuscatedSignature(descriptor = "()Ljava/util/Iterator;")
   @ObfuscatedName("by")
   public Iterator method13448() {
      this.field6993.method13686();
      return this.field6993;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public void method13446() {
      for (int var1 = 0; var1 < this.field6995; var1++) {
         classVO var2 = this.field6994[var1];

         while (true) {
            classVO var3 = var2.field6529;
            if (var3 == var2) {
               break;
            }

            var3.method12078();
         }
      }

      this.field6996 = null;
   }
}
