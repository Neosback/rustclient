import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sv")
public class classSV implements Callable {
   @ObfuscatedName("ab")
   final int field6068;
   @ObfuscatedSignature(descriptor = "Lsk;")
   @ObfuscatedName("af")
   final classSK field6067;
   @ObfuscatedSignature(descriptor = "Lsh;")
   @ObfuscatedName("ae")
   final classSH field6069;
   @ObfuscatedSignature(descriptor = "Lsu;")
   @ObfuscatedName("az")
   final classSU field6070;
   @ToRemove(unused = "true")
   @ObfuscatedName("au")
   static final int field6071 = 31;

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method10738() {
      this.field6070.method10706(144967014);
      classSU[][] var1;
      if (this.field6067 == classSK.field5971) {
         var1 = this.this$0.field6005;
      } else {
         var1 = this.this$0.field6004;
      }

      var1[this.field6068 * 31034317][this.field6069.method10493(-2029965570)] = this.field6070;
      return null;
   }

   @Override
   public Object call() {
      try {
         this.field6070.method10706(736490956);
         classSU[][] var1;
         if (this.field6067 == classSK.field5971) {
            var1 = this.this$0.field6005;
         } else {
            var1 = this.this$0.field6004;
         }

         var1[this.field6068 * 31034317][this.field6069.method10493(317216094)] = this.field6070;
         return null;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sv.call(" + ')');
      }
   }

   classSV(classSN var1, classSU var2, classSK var3, classSH var4, int var5) {
      this.this$0 = var1;
      this.field6070 = var2;
      this.field6067 = var3;
      this.field6069 = var4;
      this.field6068 = var5 * -541173499;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method10739() {
      this.field6070.method10706(1132842632);
      classSU[][] var1;
      if (this.field6067 == classSK.field5971) {
         var1 = this.this$0.field6005;
      } else {
         var1 = this.this$0.field6004;
      }

      var1[this.field6068 * 31034317][this.field6069.method10493(1389720683)] = this.field6070;
      return null;
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/util/Date;")
   @ObfuscatedName("ao")
   static Date method10740(int var0) {
      try {
         Calendar var1 = Calendar.getInstance();
         var1.set(2, 0);
         var1.set(5, 1);
         var1.set(1, 1900);
         return var1.getTime();
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sv.ao(" + ')');
      }
   }
}
