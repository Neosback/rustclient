import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ug")
public class classUG {
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("af")
   public classUZ field6305;
   @ObfuscatedSignature(descriptor = "Lul;")
   @ObfuscatedName("az")
   public classUL field6303 = new classUL();
   @ObfuscatedSignature(descriptor = "Luz;")
   @ObfuscatedName("ae")
   public classUZ field6304;
   @ToRemove(unused = "true")
   @ObfuscatedName("cq")
   static final int field6306 = 111;
   @ObfuscatedName("ag")
   static final int field6301 = 660588080;
   @ObfuscatedName("as")
   static int field6300 = 0;
   @ObfuscatedSignature(descriptor = "[Lug;")
   @ObfuscatedName("ab")
   static final classUG[] field6302 = new classUG[980706635 * field6301];

   @Override
   public int hashCode() {
      try {
         byte var1 = 31;
         int var2 = 1;
         var2 = var2 * 31 + this.field6303.hashCode();
         var2 = var2 * 31 + this.field6305.hashCode();
         return 31 * var2 + this.field6304.hashCode();
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ug.hashCode(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("af")
   public void method11364(int var1) {
      try {
         synchronized (field6302) {
            if (492103913 * field6300 < field6301 * 980706635) {
               if (var1 >= 1618312660) {
                  throw new IllegalStateException();
               }

               field6302[(field6300 += 1775136601) * 492103913 - 1] = this;
            }
         }
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "ug.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ae")
   void method11366(int var1) {
      try {
         this.field6303.method11442(561192176);
         this.field6305.method11809(-1974382800);
         this.field6304.field6425 = 1.0F;
         this.field6304.field6423 = 1.0F;
         this.field6304.field6424 = 1.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ug.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gd")
   public String method11368() {
      return "[" + this.field6303.toString() + "|" + this.field6305.toString() + "|" + this.field6304.toString() + "]";
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lug;")
   @ObfuscatedName("ax")
   public static classUG method11360() {
      synchronized (field6302) {
         if (0 == field6300 * 492103913) {
            return new classUG();
         } else {
            field6302[(field6300 -= 1775136601) * 492103913].method11366(968854401);
            return field6302[field6300 * 492103913];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ff")
   public void method11379() {
      this.method11364(-428825938);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldl;Lpi;)V")
   @ObfuscatedName("ne")
   public static void method11377(TileItem var0, ColorTextureOverride var1) {
      if (var0 == null) {
         var0.method3411();
      }

      var0.field1557 = var1;
   }

   classUG() {
      this.field6305 = new classUZ();
      this.field6304 = new classUZ(1.0F, 1.0F, 1.0F);
   }

   @Override
   public boolean equals(Object var1) {
      try {
         if (var1 == null) {
            return false;
         } else if (this == var1) {
            return true;
         } else if (!(var1 instanceof classUG)) {
            return false;
         } else {
            classUG var2 = (classUG)var1;
            return this.field6303.equals(var2.field6303)
               && classUZ.method11813(this.field6305, var2.field6305, (byte)36)
               && classUZ.method11813(this.field6304, var2.field6304, (byte)-69);
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ug.equals(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gl")
   public String method11369() {
      return "[" + this.field6303.toString() + "|" + this.field6305.toString() + "|" + this.field6304.toString() + "]";
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lug;Ljava/lang/Object;)Z")
   @ObfuscatedName("oi")
   public static boolean method11371(classUG var0, Object var1) {
      if (var0 == null) {
         var0.method11376();
      }

      if (var1 == null) {
         return false;
      } else if (var0 == var1) {
         return true;
      } else if (!(var1 instanceof classUG)) {
         return false;
      } else {
         classUG var2 = (classUG)var1;
         return var0.field6303.equals(var2.field6303)
            && classUZ.method11813(var0.field6305, var2.field6305, (byte)64)
            && classUZ.method11813(var0.field6304, var2.field6304, (byte)-88);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lug;)Ljava/lang/String;")
   @ObfuscatedName("ct")
   public static String method11370(classUG var0) {
      if (var0 == null) {
         var0.method11374();
      }

      return "[" + var0.field6303.toString() + "|" + var0.field6305.toString() + "|" + var0.field6304.toString() + "]";
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method11372(Object var1) {
      if (var1 == null) {
         return false;
      } else if (this == var1) {
         return true;
      } else if (!(var1 instanceof classUG)) {
         return false;
      } else {
         classUG var2 = (classUG)var1;
         return this.field6303.equals(var2.field6303)
            && classUZ.method11813(this.field6305, var2.field6305, (byte)27)
            && classUZ.method11813(this.field6304, var2.field6304, (byte)10);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afh")
   public int method11373() {
      int var1 = 463694583;
      int var2 = 1;
      var2 = var2 * -582685269 + this.field6303.hashCode();
      var2 = var2 * 26118768 + this.field6305.hashCode();
      return -1449515366 * var2 + this.field6304.hashCode();
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afe")
   public int method11374() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var2 * 31 + this.field6303.hashCode();
      var2 = var2 * 31 + this.field6305.hashCode();
      return 31 * var2 + this.field6304.hashCode();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lug;)I")
   @ObfuscatedName("tt")
   public static int method11375(classUG var0) {
      if (var0 == null) {
         var0.method11374();
      }

      int var1 = 1943928421;
      int var2 = 1;
      var2 = var2 * 31 + var0.field6303.hashCode();
      var2 = var2 * 1138499505 + var0.field6305.hashCode();
      return 31 * var2 + var0.field6304.hashCode();
   }

   @ObfuscatedSignature(descriptor = "(II)Lsn;")
   @ObfuscatedName("ab")
   public static classSN method11378(int var0, int var1) {
      try {
         classSN var2 = (classSN)NodeCache.method6460(Animation.field5142, var0);
         if (null != var2) {
            return var2;
         } else {
            var2 = classMB.method7550(classGN.field2631, classIY.field3097, var0, -1929466463);
            if (var2 != null) {
               Animation.field5142.method6464(var2, var0);
            }

            return var2;
         }
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "ug.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lug;")
   @ObfuscatedName("ab")
   public static classUG method11361() {
      synchronized (field6302) {
         if (0 == field6300 * -716979174) {
            return new classUG();
         } else {
            field6302[(field6300 -= 1775136601) * 492103913].method11366(1115923696);
            return field6302[field6300 * 1495403731];
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lug;")
   @ObfuscatedName("ag")
   public static classUG method11362() {
      synchronized (field6302) {
         if (0 == field6300 * 492103913) {
            return new classUG();
         } else {
            field6302[(field6300 -= 1775136601) * 492103913].method11366(1949189705);
            return field6302[field6300 * 492103913];
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Lug;")
   @ObfuscatedName("qa")
   public static classUG method11380() {
      return classMQ.method7682(-989125358);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lug;")
   @ObfuscatedName("as")
   public static classUG method11363() {
      synchronized (field6302) {
         if (0 == field6300 * 933579665) {
            return new classUG();
         } else {
            field6302[(field6300 -= 1982063216) * -1237855958].method11366(1422225741);
            return field6302[field6300 * 492103913];
         }
      }
   }

   @Override
   public String toString() {
      try {
         return "[" + this.field6303.toString() + "|" + this.field6305.toString() + "|" + this.field6304.toString() + "]";
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "ug.toString(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ac")
   public void method11365() {
      synchronized (field6302) {
         if (492103913 * field6300 < field6301 * 980706635) {
            field6302[(field6300 += 1775136601) * 492103913 - 1] = this;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   void method11367() {
      this.field6303.method11442(-1306624059);
      this.field6305.method11809(-151411178);
      this.field6304.field6425 = 1.0F;
      this.field6304.field6423 = 1.0F;
      this.field6304.field6424 = 1.0F;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("afr")
   public int method11376() {
      byte var1 = 31;
      int var2 = 1;
      var2 = var2 * 31 + this.field6303.hashCode();
      var2 = var2 * 31 + this.field6305.hashCode();
      return 31 * var2 + this.field6304.hashCode();
   }
}
