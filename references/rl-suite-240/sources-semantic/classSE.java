import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("se")
public class classSE implements classXZ {
   @ToRemove(unused = "true")
   @ObfuscatedName("bx")
   static final int field5917 = 171;
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("af")
   static final classSE field5911 = new classSE(1, 1);
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("ae")
   static final classSE field5913 = new classSE(2, 2);
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("az")
   static final classSE field5909 = new classSE(0, 0);
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("ag")
   static final classSE field5910 = new classSE(4, 4);
   @ObfuscatedName("as")
   final int field5914;
   @ObfuscatedName("ax")
   final int field5915;
   @ObfuscatedSignature(descriptor = "Lse;")
   @ObfuscatedName("ab")
   static final classSE field5912 = new classSE(3, 3);
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5916 = 1;

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod10() {
      return 1514051917 * this.field5915;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("az")
   @Override
   public int vmethod8(byte var1) {
      try {
         return 1514051917 * this.field5915;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "se.az(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwb;Ljava/lang/Object;)Z")
   @ObfuscatedName("vv")
   public static boolean method10421(classWB var0, Object var1) {
      if (var0 == null) {
         var0.method12351();
      }

      if (var1 != null && var1 instanceof classWB) {
         classWB var2 = (classWB)var1;
         if (var0.field6652 == null) {
            if (var2.field6652 != null) {
               return false;
            }
         } else if (!var0.field6652.equals(var2.field6652)) {
            return false;
         }

         if (var0.field6653 == null) {
            if (null != var2.field6653) {
               return false;
            }
         } else if (!var0.field6653.equals(var2.field6653)) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("af")
   @Override
   public int vmethod9() {
      return 1514051917 * this.field5915;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)V")
   @ObfuscatedName("jw")
   public static void method10427(ClientPreferences var0) {
      ClientPreferences.method2740(var0, !var0.field1351, (byte)-98);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ab")
   @Override
   public int vmethod11() {
      return -461943984 * this.field5915;
   }

   classSE(int var1, int var2) {
      this.field5914 = var1 * 299756773;
      this.field5915 = var2 * 2043561861;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lse;")
   @ObfuscatedName("ax")
   static classSE[] method10423() {
      return new classSE[]{field5909, field5911, field5913, field5912, field5910};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lse;")
   @ObfuscatedName("ac")
   static classSE[] method10424() {
      return new classSE[]{field5909, field5911, field5913, field5912, field5910};
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lff;[I[FIIIF)V")
   @ObfuscatedName("ls")
   public static void method10422(classFF var0, int[] var1, float[] var2, int var3, int var4, int var5, float var6) {
      if (var0 == null) {
         var0.vmethod450();
      }

      int var7 = var1[var3];
      var4 = var4 + ((var7 & 16711935) * var5 >> 8 & 16711935) + ((var7 & 0xFF00) * var5 >> 8 & 0xFF00);
      int var8 = var0.field2376.field2208 & classFF.method4653(var2[var3], var6);
      var1[var3] = (var8 & var4) + (~var8 & var7);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lse;")
   @ObfuscatedName("aa")
   static classSE method10425(int var0) {
      classSE var1 = (classSE)classOF.method8436(classQD.method9546(-1831398947), var0, 95108565);
      if (var1 == null) {
         var1 = field5909;
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lse;")
   @ObfuscatedName("ao")
   static classSE method10426(int var0) {
      classSE var1 = (classSE)classOF.method8436(classQD.method9546(-1697277799), var0, -1734029783);
      if (var1 == null) {
         var1 = field5909;
      }

      return var1;
   }
}
