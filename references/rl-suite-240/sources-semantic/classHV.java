import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("hv")
public class classHV {
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   public static final int field2896 = 4;
   @ObfuscatedName("az")
   public int field2895;
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("af")
   public classKY field2893;
   @ObfuscatedSignature(descriptor = "Lky;")
   @ObfuscatedName("ae")
   public classKY field2894;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwc;IIILzl;)V")
   @ObfuscatedName("zz")
   public static void method6160(classWC var0, int var1, int var2, int var3, classZL var4) {
      if (var0.field6658 != null && var1 != var0.field6660 * 1439462015) {
         var0.field6656 = var0.field6658.method546() * 3586789;
         var0.field6657 = classDD.method2988(16711680) * -4042382095557502705L;
         var0.field6660 = -1308502860 * var1;
         var0.field6659 = var0.method12377(var3, var0.field6656 * -1133894741 - var0.field6660 * 1439462015, var2, (short)402) * -1623687273;
         var0.field6661 = var4;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lul;Lul;I)F")
   @ObfuscatedName("ag")
   static final float method6161(classUL var0, classUL var1, int var2) {
      try {
         return var0.method11451(var1, (byte)0);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "hv.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Llh;I)Z")
   @ObfuscatedName("nx")
   public static boolean method6162(classLH var0, int var1) {
      var0.field4157.method13978(var1, (byte)-88);
      if (var0.method7111((byte)-24)) {
         classLH.method7133(var0, (byte)25);
         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method6159(IndexDataBase var0, int var1) {
      try {
         VarbitComposition.field5421 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "hv.az(" + ')');
      }
   }

   public classHV(int var1, classKY var2, classKY var3) {
      this.field2895 = -881797973 * var1;
      this.field2894 = var2;
      this.field2893 = var3;
   }
}
