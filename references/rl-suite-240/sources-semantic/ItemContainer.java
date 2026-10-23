import javax.annotation.Nonnull;
import net.runelite.api.Item;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bh")
public class ItemContainer extends Node implements net.runelite.api.ItemContainer {
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field458 = 64;
   @ObfuscatedName("af")
   int[] field456 = new int[]{-1};
   @ObfuscatedName("ae")
   int[] field455 = new int[]{0};
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hb")
   static classVP field462;
   @ObfuscatedSignature(descriptor = "Lyi;")
   @ObfuscatedName("az")
   static HashTable field454 = new HashTable(32);
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("il")
   static classVP field463;
   @ToRemove(unused = "true")
   @ObfuscatedName("cr")
   static final int field461 = 4095;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field459 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field457 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("at")
   public static final int field460 = 44;

   public int find(int var1) {
      for (int var2 = 0; var2 < this.field456.length; var2++) {
         if (this.field456[var2] == var1) {
            return var2;
         }
      }

      return -1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ao")
   static int method1083(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (null == var2) {
         return 0;
      } else {
         return var1 >= 0 && var1 < var2.field455.length ? var2.field455[var1] : 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ax")
   static int method1081(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (null == var2) {
         return -1;
      } else {
         return var1 >= 0 && var1 < var2.field456.length ? var2.field456[var1] : -1;
      }
   }

   public int count() {
      int var1 = 0;

      for (int var5 : this.field456) {
         if (var5 != -1) {
            var1++;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ac")
   static int method1084(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (null == var2) {
         return 0;
      } else {
         return var1 >= 0 && var1 < var2.field455.length ? var2.field455[var1] : 0;
      }
   }

   public int size() {
      return this.field456.length;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ay")
   static int method1086(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (var2 == null) {
         return 0;
      } else if (-1 == var1) {
         return 0;
      } else {
         int var3 = 0;

         for (int var4 = 0; var4 < var2.field455.length; var4++) {
            if (var1 == var2.field456[var4]) {
               var3 += var2.field455[var4];
            }
         }

         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("au")
   static void method1091(int var0) {
      ItemContainer var1 = (ItemContainer)HashTable.method13432(field454, var0);
      if (null != var1) {
         for (int var2 = 0; var2 < var1.field456.length; var2++) {
            var1.field456[var2] = -1;
            var1.field455[var2] = 0;
         }
      }
   }

   public int count(int var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < this.field456.length; var3++) {
         if (this.field456[var3] == var1) {
            int var4 = this.field455[var3];
            if (var4 > 1) {
               return var4;
            }

            var2++;
         }
      }

      return var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("al")
   static int method1087(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (var2 == null) {
         return 0;
      } else if (-1 == var1) {
         return 0;
      } else {
         int var3 = 0;

         for (int var4 = 0; var4 < var2.field455.length; var4++) {
            if (var1 == var2.field456[var4]) {
               var3 += var2.field455[var4];
            }
         }

         return var3;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aj")
   static int method1088(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (var2 == null) {
         return 0;
      } else if (-1 == var1) {
         return 0;
      } else {
         int var3 = 0;

         for (int var4 = 0; var4 < var2.field455.length; var4++) {
            if (var1 == var2.field456[var4]) {
               var3 += var2.field455[var4];
            }
         }

         return var3;
      }
   }

   public Item getItem(int var1) {
      return var1 >= 0 && var1 < this.field456.length && this.field456[var1] != -1 ? new Item(this.field456[var1], this.field455[var1]) : null;
   }

   public boolean contains(int var1) {
      for (int var5 : this.field456) {
         if (var5 == var1) {
            return true;
         }
      }

      return false;
   }

   @Nonnull
   public Item[] getItems() {
      Item[] var1 = new Item[this.field456.length];

      for (int var2 = 0; var2 < this.field456.length; var2++) {
         var1[var2] = new Item(this.field456[var2], this.field455[var2]);
      }

      return var1;
   }

   ItemContainer() {
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("aq")
   static void method1089(int var0, int var1, int var2, int var3) {
      ItemContainer var4 = (ItemContainer)HashTable.method13432(field454, var0);
      if (var4 == null) {
         var4 = new ItemContainer();
         field454.method13418(var4, var0);
      }

      if (var4.field456.length <= var1) {
         int[] var5 = new int[var1 + 1];
         int[] var6 = new int[1 + var1];

         for (int var7 = 0; var7 < var4.field456.length; var7++) {
            var5[var7] = var4.field456[var7];
            var6[var7] = var4.field455[var7];
         }

         for (int var8 = var4.field456.length; var8 < var1; var8++) {
            var5[var8] = -1;
            var6[var8] = 0;
         }

         var4.field456 = var5;
         var4.field455 = var6;
      }

      var4.field456[var1] = var2;
      var4.field455[var1] = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("ad")
   static void method1090(int var0, int var1, int var2, int var3) {
      ItemContainer var4 = (ItemContainer)HashTable.method13432(field454, var0);
      if (var4 == null) {
         var4 = new ItemContainer();
         field454.method13418(var4, var0);
      }

      if (var4.field456.length <= var1) {
         int[] var5 = new int[var1 + 1];
         int[] var6 = new int[1 + var1];

         for (int var7 = 0; var7 < var4.field456.length; var7++) {
            var5[var7] = var4.field456[var7];
            var6[var7] = var4.field455[var7];
         }

         for (int var8 = var4.field456.length; var8 < var1; var8++) {
            var5[var8] = -1;
            var6[var8] = 0;
         }

         var4.field456 = var5;
         var4.field455 = var6;
      }

      var4.field456[var1] = var2;
      var4.field455[var1] = var3;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ap")
   static void method1092(int var0) {
      ItemContainer var1 = (ItemContainer)HashTable.method13432(field454, var0);
      if (null != var1) {
         for (int var2 = 0; var2 < var1.field456.length; var2++) {
            var1.field456[var2] = -1;
            var1.field455[var2] = 0;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aa")
   static int method1085(int var0, int var1) {
      ItemContainer var2 = (ItemContainer)HashTable.method13432(field454, var0);
      if (null == var2) {
         return 0;
      } else {
         return var1 >= 0 && var1 < var2.field455.length ? var2.field455[var1] : 0;
      }
   }

   public int getId() {
      return (int)this.getHash();
   }

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method1082(IndexDataBase var0, int var1) {
      try {
         classZW.field7284 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bh.az(" + ')');
      }
   }
}
