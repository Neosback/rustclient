import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("qb")
public class classQB {
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5512 = 6;
   @ObfuscatedSignature(descriptor = "Lqb;")
   @ObfuscatedName("af")
   static final classQB field5510 = new classQB("Bearer");
   @ObfuscatedName("ae")
   final String field5511;
   @ObfuscatedSignature(descriptor = "Lqb;")
   @ObfuscatedName("az")
   static final classQB field5509 = new classQB("Basic");

   @ObfuscatedSignature(descriptor = "(ILcl;I)Ldz;")
   @ObfuscatedName("ae")
   public static WorldView method9481(int var0, classCL var1, int var2) {
      try {
         for (WorldView var4 : var1) {
            Player var5 = (Player)IndexedObjectSet.method13449(var4.field1692, var0);
            if (null != var5 && !var4.method3734(-2087374717)) {
               if (var2 == -431797757) {
                  throw new IllegalStateException();
               }

               return var4;
            }
         }

         return var1.method1588(-1275085187);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "qb.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lqb;I)Ljava/lang/String;")
   @ObfuscatedName("le")
   public static String method9478(classQB var0, int var1) {
      if (var0 == null) {
         var0.method9480(var1);
      }

      try {
         return var0.field5511;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qb.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("af")
   String method9479() {
      return this.field5511;
   }

   classQB(String var1) {
      this.field5511 = var1;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("az")
   String method9480(int var1) {
      try {
         return this.field5511;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "qb.az(" + ')');
      }
   }
}
