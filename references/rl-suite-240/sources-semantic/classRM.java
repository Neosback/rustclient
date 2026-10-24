import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("rm")
public final class classRM {
   @ObfuscatedName("az")
   String field5806;
   @ObfuscatedName("af")
   String field5805;
   @ObfuscatedName("ae")
   String field5804;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("as")
   public static IndexDataBase field5807;
   @ObfuscatedName("ag")
   boolean field5809;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field5803 = 6;
   @ObfuscatedName("ab")
   boolean field5808;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;IZ)V")
   @ObfuscatedName("wq")
   public static void method10174(RenderOverview var0, int var1, boolean var2) {
      if (var0 == null) {
         var0.method12471(var1);
      } else {
         if (!var2) {
            var0.field6711.add(var1);
         } else {
            var0.field6711.remove(var1);
         }

         var0.method12638((byte)105);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Luu;")
   @ObfuscatedName("az")
   public static classUU method10172(int var0) {
      try {
         synchronized (classUU.field6380) {
            if (-1618719293 * classUU.field6381 == 0) {
               if (var0 != 1778071523) {
                  throw new IllegalStateException();
               } else {
                  return new classUU();
               }
            } else {
               classUU.field6380[(classUU.field6381 -= 943786731) * -1618719293].method11635(-1594911430);
               return classUU.field6380[classUU.field6381 * -1618719293];
            }
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "rm.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lco;)Z")
   @ObfuscatedName("tw")
   public static boolean method10173(classCO var0) {
      return var0 == null ? var0.method2558() : var0.field1174.method8221();
   }

   classRM() {
   }
}
