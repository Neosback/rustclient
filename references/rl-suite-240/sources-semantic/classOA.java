import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("oa")
public class classOA extends classPB {
   @ObfuscatedName("ab")
   int field4801;
   @ObfuscatedName("af")
   int field4800;
   @ObfuscatedName("ae")
   int field4802;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field4804 = 65535;
   @ToRemove(unused = "true")
   @ObfuscatedName("ah")
   static final int field4805 = 32;
   @ObfuscatedName("az")
   int field4803;

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ac")
   public static int method8319(byte var0) {
      try {
         return -1529735849 * classEY.field1981;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "oa.ac(" + 41);
      }
   }

   classOA(String var1, int var2, int var3, int var4, int var5) {
      super(var1);
      this.field4803 = 425793223 * var2;
      this.field4800 = var3 * -1293835387;
      this.field4802 = var4 * 1339993387;
      this.field4801 = var5 * 2110327357;
   }

   @ObfuscatedSignature(descriptor = "(I)Lky;")
   @ObfuscatedName("eh")
   static classKY method8320(int var0) {
      try {
         WorldView var3 = client.field815.method1584(client.field847 * -2034209657, (short)542);
         Player var5 = var3.method3752(client.field846 * -2130951373, (byte)1);
         classUZ var2;
         if (var5 != null) {
            if (var0 != 1544651080) {
               throw new IllegalStateException();
            }

            classUZ var6 = classYY.method13833(var3, var5.vmethod368(311606126), var5.vmethod371(-1909982160), (byte)107);
            var6.field6423 = var5.vmethod258((byte)-91);
            var2 = var6;
         } else {
            var2 = classHZ.method6171(16777216);
         }

         classKY var1;
         if (var2.method11816((byte)-71)) {
            if (var0 != 1544651080) {
               throw new IllegalStateException();
            }

            var1 = new classKY();
         } else {
            int var4 = classKY.method6652((int)var2.field6425, (byte)-67);
            int var8 = classKY.method6652((int)var2.field6424, (byte)-80);
            var1 = new classKY((int)var2.field6423, var4, var8);
         }

         if (var1.method6674(-1912810292)) {
            if (var0 != 1544651080) {
               throw new IllegalStateException();
            }

            var1.field4053 = var1.field4053 - 1982338775 * classIS.field3051.field1706;
            var1.field4054 = var1.field4054 - classIS.field3051.field1707 * 697183897;
         }

         return var1;
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "oa.eh(" + ')');
      }
   }
}
