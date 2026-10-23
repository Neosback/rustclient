import java.util.Iterator;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hb")
public abstract class classHB extends Node {
   @ToRemove(unused = "true")
   @ObfuscatedName("ay")
   static final int field2759 = 13;

   classHB() {
   }

   @ObfuscatedSignature(descriptor = "(Lxy;I)V")
   @ObfuscatedName("az")
   abstract void vmethod498(classXY var1, int var2);

   @ObfuscatedSignature(descriptor = "(Liq;B)V")
   @ObfuscatedName("af")
   abstract void vmethod499(ClanSettings var1, byte var2);

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ae")
   abstract void vmethod500(classXY var1);

   @ObfuscatedSignature(descriptor = "(Lxy;)V")
   @ObfuscatedName("ab")
   abstract void vmethod501(classXY var1);

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ag")
   abstract void vmethod502(ClanSettings var1);

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("as")
   abstract void vmethod503(ClanSettings var1);

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lsu;)I")
   @ObfuscatedName("jd")
   public static int method6021(classSU var0) {
      if (var0 == null) {
         var0.method10724();
      }

      return 1286089593 * var0.field6057;
   }

   @ObfuscatedSignature(descriptor = "(Liq;)V")
   @ObfuscatedName("ax")
   abstract void vmethod504(ClanSettings var1);

   @ObfuscatedSignature(descriptor = "(ILcl;I)Ldz;")
   @ObfuscatedName("ab")
   public static WorldView method6020(int var0, classCL var1, int var2) {
      try {
         Iterator var3 = var1.iterator();

         while (var3.hasNext()) {
            if (var2 == -94043672) {
               throw new IllegalStateException();
            }

            WorldView var4 = (WorldView)var3.next();
            if (IndexedObjectSet.method13449(var4.field1696, var0) != null) {
               if (var2 == -94043672) {
                  throw new IllegalStateException();
               }

               return var4;
            }
         }

         return var1.method1588(14925414);
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "hb.ab(" + ')');
      }
   }
}
