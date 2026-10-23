import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rs")
public class Ignore extends Nameable implements net.runelite.api.Ignore {
   @ObfuscatedSignature(descriptor = "Lec;")
   @ObfuscatedName("mr")
   static TextureProvider field5837;
   @ToRemove(unused = "true")
   @ObfuscatedName("bf")
   public static final int field5835 = 71;
   @ObfuscatedName("cg")
   static int field5836;
   @ObfuscatedName("az")
   int field5834;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)I")
   @ObfuscatedName("ae")
   @Override
   public int vmethod565(Object var1) {
      return method10235(this, (Ignore)var1, (byte)-116);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqa;)Lqy;")
   @ObfuscatedName("qg")
   public static classQY method10237(classQA var0) {
      return var0.field5502;
   }

   @ObfuscatedSignature(descriptor = "(Lrr;I)I")
   @ObfuscatedName("aw")
   @Override
   public int vmethod562(Nameable var1, int var2) {
      try {
         return method10235(this, (Ignore)var1, (byte)-38);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rs.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Ler;)V")
   @ObfuscatedName("md")
   public static void method10238(ModelData var0) {
      if (var0 == null) {
         var0.method4042();
      } else {
         for (int var1 = 0; var1 < var0.field1846; var1++) {
            float var2 = var0.field1855[var1];
            var0.field1855[var1] = var0.field1853[var1];
            var0.field1853[var1] = -var2;
         }

         var0.method4084();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrs;)I")
   @ObfuscatedName("ab")
   int method10232(Ignore var1) {
      return -847650061 * this.field5834 - var1.field5834 * -847650061;
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("ak")
   @Override
   public int vmethod563(Nameable var1) {
      return method10235(this, (Ignore)var1, (byte)-51);
   }

   Ignore() {
   }

   @ObfuscatedSignature(descriptor = "(Lrr;)I")
   @ObfuscatedName("av")
   @Override
   public int vmethod564(Nameable var1) {
      return method10235(this, (Ignore)var1, (byte)-94);
   }

   @ObfuscatedSignature(descriptor = "(Lrs;)I")
   @ObfuscatedName("ag")
   int method10233(Ignore var1) {
      return -847650061 * this.field5834 - var1.field5834 * -847650061;
   }

   @ObfuscatedSignature(descriptor = "(Lrs;)I")
   @ObfuscatedName("af")
   int method10234(Ignore var1) {
      return -847650061 * this.field5834 - var1.field5834 * 2003665183;
   }

   @ObfuscatedSignature(descriptor = "(Lrs;Lrs;B)I")
   @ObfuscatedName("fl")
   public static int method10235(Ignore var0, Ignore var1, byte var2) {
      if (var0 == null) {
         var0.method10236(var0, var2);
      }

      try {
         return -847650061 * var0.field5834 - var1.field5834 * -847650061;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rs.az(" + 41);
      }
   }

   @Override
   public int compareTo(Object var1) {
      try {
         return method10235(this, (Ignore)var1, (byte)-28);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "rs.compareTo(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lrs;B)I")
   @ObfuscatedName("az")
   int method10236(Ignore var1, byte var2) {
      try {
         return -847650061 * this.field5834 - var1.field5834 * -847650061;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "rs.az(" + 41);
      }
   }
}
