import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("ih")
public class classIH {
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   public static final int field2969 = 12;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field2971 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field2973 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ao")
   public static final int field2972 = 14;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field2970 = 4;

   @ObfuscatedSignature(descriptor = "(S)V")
   @ObfuscatedName("cn")
   static final void method6253(short var0) {
      try {
         for (classCJ var1 = (classCJ)classYE.field6975.method13628(); null != var1; var1 = (classCJ)classDI.method3311(classYE.field6975)) {
            if (var0 == 209) {
               throw new IllegalStateException();
            }

            if (1612595797 * client.field856 > var1.method1551((short)1088)) {
               if (var0 == 209) {
                  throw new IllegalStateException();
               }

               var1.vmethod398();
            } else if (1612595797 * client.field856 >= classCJ.method1546(var1, (byte)0)) {
               if (var0 == 209) {
                  throw new IllegalStateException();
               }

               var1.method1527(client.field815, 1612595797 * client.field856, -322689719 * client.field950, (byte)-48);
               classIS.field3051
                  .field1687
                  .method4505(
                     var1.method1556((byte)4),
                     var1.method1557(-2100060870),
                     var1.method1561((byte)42),
                     var1.method1559((byte)8),
                     60,
                     var1,
                     var1.method1563((byte)-43),
                     -1L,
                     false
                  );
            }
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "ih.cn(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lvv;Llw;)Z")
   @ObfuscatedName("ez")
   public static boolean method6252(classVV var0, Widget var1) {
      if (var0 == null) {
         var0.method12210(var1);
      }

      return var1.field4287;
   }

   classIH() throws Throwable {
      throw new Error();
   }
}
