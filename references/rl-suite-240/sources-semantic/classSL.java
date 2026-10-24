import java.io.File;
import java.util.concurrent.Callable;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("sl")
class classSL implements Callable {
   @ObfuscatedName("ai")
   static File field5973;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field5972 = 3;

   @ObfuscatedSignature(descriptor = "(Ljava/lang/CharSequence;S)Ljava/lang/String;")
   @ObfuscatedName("ai")
   public static String method10515(CharSequence var0, short var1) {
      try {
         return classJC.method6395('*', var0.length(), 749532022);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "sl.ai(" + ')');
      }
   }

   @Override
   public Object call() {
      try {
         classSN.method10556(this.this$0, this.val$p, this.val$version, -999443308);
         return null;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "sl.call(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("az")
   public Object method10513() {
      classSN.method10556(this.this$0, this.val$p, this.val$version, -1634561626);
      return null;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/Object;")
   @ObfuscatedName("af")
   public Object method10514() {
      classSN.method10556(this.this$0, this.val$p, this.val$version, -1151485226);
      return null;
   }

   classSL(classSN var1, classXY var2, int var3) {
      this.this$0 = var1;
      this.val$p = var2;
      this.val$version = var3;
   }

   @ObfuscatedSignature(descriptor = "(I)Lsj;")
   @ObfuscatedName("el")
   static classSJ method10516(int var0) {
      try {
         return classDF.method3003(client.field838, -2130951373 * client.field846, -1741491392);
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "sl.el(" + ')');
      }
   }
}
