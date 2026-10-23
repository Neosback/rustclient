import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("tb")
class classTB implements Callable {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field6115 = -1412584499;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   static final int field6114 = 10;

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ab")
   public static void method10775(int var0) {
      try {
         synchronized (classUJ.field6313) {
            if (classUJ.field6312 * 508619279 != 0) {
               if (var0 != -1927903443) {
                  throw new IllegalStateException();
               }

               classUJ.field6312 = 379503855;

               try {
                  classUJ.field6313.wait();
               } catch (InterruptedException var6) {
               }
            }
         }

         synchronized (classUJ.field6311) {
            Deque2.method7942(classUJ.field6311);
            Deque2.method7942(classUJ.field6314);
         }
      } catch (RuntimeException var8) {
         throw classEG.method3899(var8, "tb.ab(" + ')');
      }
   }

   @Override
   public Object call() {
      try {
         for (int var1 = this.val$workStart; var1 < this.val$workEnd; var1++) {
            this.val$curveLoadJobs[var1].call();
         }

         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "tb.call(" + ')');
      }
   }

   classTB(classSN var1, int var2, int var3, classSV[] var4) {
      this.this$0 = var1;
      this.val$workStart = var2;
      this.val$workEnd = var3;
      this.val$curveLoadJobs = var4;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ldh;)Lyn;")
   @ObfuscatedName("fw")
   public static IterableHashTable method10778(Actor var0) {
      return var0.field1467;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method10776() {
      for (int var1 = this.val$workStart; var1 < this.val$workEnd; var1++) {
         this.val$curveLoadJobs[var1].call();
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method10777() {
      for (int var1 = this.val$workStart; var1 < this.val$workEnd; var1++) {
         this.val$curveLoadJobs[var1].call();
      }

      return null;
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("gp")
   static final void method10780(byte var0) {
      try {
         classJL var1 = classEF.method3863(classJS.CLOSE_MODAL, client.field796.field1533, (byte)-86);
         client.field796.method3374(var1, -1301097035);
         classBB.field335 = true;

         for (WidgetNode var2 = (WidgetNode)classWK.field6689.field6611.method13423();
            null != var2;
            var2 = (WidgetNode)classWK.field6689.field6611.method13427()
         ) {
            if (var0 == 1) {
               return;
            }

            if (0 != var2.field5274 * -691218541) {
               if (var0 == 1) {
                  return;
               }

               if (-691218541 * var2.field5274 != 3) {
                  continue;
               }
            }

            classGB.field2544.method9150(var2, true, classWK.field6689, (byte)-98);
         }

         classWK.field6689.method12240((byte)8);
         classBB.field335 = false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "tb.gp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("et")
   static final boolean method10779(int var0) {
      try {
         return client.field974;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "tb.et(" + ')');
      }
   }
}
