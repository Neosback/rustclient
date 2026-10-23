import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("wt")
public abstract class classWT {
   @ObfuscatedName("ac")
   boolean field6777;
   @ObfuscatedSignature(descriptor = "Lwt;")
   @ObfuscatedName("ag")
   classWT field6773;
   @ObfuscatedName("as")
   String field6774;
   @ObfuscatedName("ax")
   String field6775;
   @ObfuscatedName("ab")
   boolean field6776;

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   public abstract boolean vmethod627(byte var1);

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ap")
   public String method12686() {
      return "Error in task: " + this.field6775 + ", Error message: " + this.field6774;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aa")
   public boolean method12683(byte var1) {
      try {
         return this.field6776;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wt.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Ljava/lang/String;")
   @ObfuscatedName("ao")
   public String method12687(int var1) {
      try {
         return "Error in task: " + this.field6775 + ", Error message: " + this.field6774;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wt.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(S)Lwt;")
   @ObfuscatedName("al")
   public classWT method12690(short var1) {
      try {
         return this.field6773;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wt.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Lwt;")
   @ObfuscatedName("ai")
   public classWT method12691() {
      return this.field6773;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("af")
   public abstract boolean vmethod628();

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ae")
   public abstract boolean vmethod629();

   @ObfuscatedSignature(descriptor = "()Lwt;")
   @ObfuscatedName("aw")
   public classWT method12692() {
      return this.field6773;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqm;)Z")
   @ObfuscatedName("iq")
   public static boolean method12693(classQM var0) {
      return var0.field5563 != null || var0.field5558 == null;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ag")
   public abstract boolean vmethod630();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwt;)Ljava/lang/String;")
   @ObfuscatedName("oi")
   public static String method12688(classWT var0) {
      if (var0 == null) {
         var0.method12691();
      }

      return "Error in task: " + var0.field6775 + ", Error message: " + var0.field6774;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   public boolean method12684() {
      return this.field6776;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ay")
   public boolean method12685() {
      return this.field6776;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("au")
   public String method12689() {
      return "Error in task: " + this.field6775 + ", Error message: " + this.field6774;
   }

   @ObfuscatedSignature(descriptor = "(Lwt;Ljava/lang/String;B)V")
   @ObfuscatedName("vv")
   public static void method12694(classWT var0, String var1, byte var2) {
      if (var0 == null) {
         var0.method12696(var1, var2);
      }

      try {
         var0.field6777 = true;
         var0.field6774 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wt.aj(" + ')');
      }
   }

   classWT(classWT var1) {
      this.field6773 = var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ab")
   public abstract boolean vmethod626();

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ac")
   public boolean method12682(int var1) {
      try {
         return this.field6777;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "wt.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("av")
   void method12695(String var1) {
      this.field6777 = true;
      this.field6774 = var1;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)V")
   @ObfuscatedName("aj")
   void method12696(String var1, byte var2) {
      try {
         this.field6776 = true;
         this.field6774 = var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "wt.aj(" + ')');
      }
   }
}
