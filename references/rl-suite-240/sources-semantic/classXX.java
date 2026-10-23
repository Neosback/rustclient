import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xx")
final class classXX implements classXF {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field6949 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6951 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field6950 = 2;

   @ObfuscatedSignature(descriptor = "(B)Lxt;")
   @ObfuscatedName("az")
   @Override
   public classXT vmethod648(byte var1) {
      try {
         return classXT.field6935;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "xx.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lxt;")
   @ObfuscatedName("af")
   @Override
   public classXT vmethod647() {
      return classXT.field6935;
   }

   @ObfuscatedSignature(descriptor = "(Ldz;IIIII)Lcd;")
   @ObfuscatedName("ds")
   static final classCD method13013(WorldView var0, int var1, int var2, int var3, int var4, int var5) {
      try {
         for (classCD var6 = (classCD)var0.field1688.method7957(); var6 != null; var6 = (classCD)var0.field1688.method7965()) {
            if (var5 <= -1031526441) {
               throw new IllegalStateException();
            }

            if (-1436432311 * var6.field679 == var1) {
               if (var5 <= -1031526441) {
                  throw new IllegalStateException();
               }

               if (var6.field676 * -1779660971 == var2 && var6.field680 * -522576623 == var3) {
                  if (var5 <= -1031526441) {
                     throw new IllegalStateException();
                  }

                  if (2125153617 * var6.field678 == var4) {
                     if (var5 <= -1031526441) {
                        throw new IllegalStateException();
                     }

                     return var6;
                  }
               }
            }
         }

         return null;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "xx.ds(" + ')');
      }
   }
}
