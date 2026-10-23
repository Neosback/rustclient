import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("xj")
public class classXJ {
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("ag")
   public static final classXJ field6877 = new classXJ("verdana_13pt_regular");
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("af")
   public static final classXJ field6876 = new classXJ("p12_full");
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("ae")
   public static final classXJ field6874 = new classXJ("b12_full");
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("ab")
   public static final classXJ field6875 = new classXJ("verdana_11pt_regular");
   @ToRemove(unused = "true")
   @ObfuscatedName("ai")
   static final String field6881 = "zeropad_";
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("as")
   public static final classXJ field6873 = new classXJ("verdana_15pt_regular");
   @ObfuscatedName("ax")
   String field6878;
   @ToRemove(unused = "true")
   @ObfuscatedName("dd")
   static final int field6880 = 2;
   @ObfuscatedSignature(descriptor = "Lxj;")
   @ObfuscatedName("az")
   public static final classXJ field6872 = new classXJ("p11_full");
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field6879 = 40;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()[Lxj;")
   @ObfuscatedName("af")
   public static classXJ[] method12858() {
      return new classXJ[]{field6873, field6874, field6877, field6876, field6872, field6875};
   }

   @ObfuscatedSignature(descriptor = "(B)[Lzl;")
   @ObfuscatedName("ag")
   static classZL[] method12860(byte var0) {
      try {
         return new classZL[]{
            classZL.field7192,
            classZL.field7220,
            classZL.field7193,
            classZL.field7194,
            classZL.field7195,
            classZL.field7196,
            classZL.field7206,
            classZL.field7197,
            classZL.field7198,
            classZL.field7207,
            classZL.field7200,
            classZL.field7219,
            classZL.field7213,
            classZL.field7202,
            classZL.field7203,
            classZL.field7204,
            classZL.field7205,
            classZL.field7216,
            classZL.field7201,
            classZL.field7208,
            classZL.field7209,
            classZL.field7210,
            classZL.field7211,
            classZL.field7212,
            classZL.field7218,
            classZL.field7217,
            classZL.field7214,
            classZL.field7215
         };
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xj.ag(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcl;Ltn;Ltw;)V")
   @ObfuscatedName("hu")
   public static void method12861(classCL var0, classTN var1, classTW var2) {
      if (var0 == null) {
         var0.method1601(var1, var2);
      } else {
         var0.field786 = var1;
         var0.field787 = var2;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)[Lxj;")
   @ObfuscatedName("az")
   public static classXJ[] method12859(int var0) {
      try {
         return new classXJ[]{field6873, field6874, field6877, field6876, field6872, field6875};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xj.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("bb")
   static void method12862(int var0) {
      try {
         client.field850 = client.field846 * 2071496817;
         classLE.field4124 = classKU.field4023;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "xj.bb(" + ')');
      }
   }

   classXJ(String var1) {
      this.field6878 = var1;
   }
}
