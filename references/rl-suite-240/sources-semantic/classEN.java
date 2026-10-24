import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("en")
public class classEN implements classXZ {
   @ObfuscatedName("as")
   public final int field1791;
   @ObfuscatedSignature(descriptor = "Len;")
   @ObfuscatedName("az")
   public static final classEN field1793 = new classEN(1, 0);
   @ObfuscatedSignature(descriptor = "Len;")
   @ObfuscatedName("ae")
   public static final classEN field1796 = new classEN(3, 2);
   @ObfuscatedSignature(descriptor = "Len;")
   @ObfuscatedName("ab")
   public static final classEN field1795 = new classEN(2, 3);
   @ObfuscatedSignature(descriptor = "Len;")
   @ObfuscatedName("ag")
   public static final classEN field1794 = new classEN(0, 4);
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final String field1799 = "services";
   @ObfuscatedName("ax")
   final int field1792;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("an")
   static IndexDataBase field1801;
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hr")
   static classVP field1802;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("al")
   public static IndexDataBase field1800;
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   public static final int field1798 = 61;
   @ObfuscatedSignature(descriptor = "Len;")
   @ObfuscatedName("af")
   public static final classEN field1797 = new classEN(4, 1);

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return this.field1792 * 692765925;
   }

   classEN(int var1, int var2) {
      this.field1791 = var1 * -2139568645;
      this.field1792 = 1495619821 * var2;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("az")
   public static int method3955(int var0, int var1) {
      try {
         return var0 >> 24 & 3;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "en.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return this.field1792 * 692765925;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "en.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return this.field1792 * 692765925;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return this.field1792 * 692765925;
   }

   @ObfuscatedSignature(descriptor = "(ILcl;I)Lct;")
   @ObfuscatedName("az")
   public static Player method3954(int var0, classCL var1, int var2) {
      try {
         Player var3 = null;
         Iterator var4 = var1.iterator();

         while (var4.hasNext()) {
            if (var2 == 697875933) {
               throw new IllegalStateException();
            }

            WorldView var5 = (WorldView)var4.next();
            Player var6 = (Player)IndexedObjectSet.method13449(var5.field1692, var0);
            if (null != var6) {
               if (var2 == 697875933) {
                  throw new IllegalStateException();
               }

               if (!var5.method3734(-2087374717)) {
                  return var6;
               }

               if (var2 == 697875933) {
                  throw new IllegalStateException();
               }

               var3 = var6;
            }
         }

         return var3;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "en.az(" + ')');
      }
   }
}
