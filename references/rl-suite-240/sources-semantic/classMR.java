import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("mr")
public class classMR {
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   public static final int field4565 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final int field4563 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   public static final int field4562 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("aq")
   static final int field4564 = 14;
   @ToRemove(unused = "true")
   @ObfuscatedName("aa")
   static final int field4561 = 8;

   @ObfuscatedSignature(descriptor = "(IB)Loc;")
   @ObfuscatedName("af")
   public static classOC method7688(int var0, byte var1) {
      try {
         classOC var2 = (classOC)NodeCache.method6460(classOC.field4812, var0);
         if (null != var2) {
            if (var1 == 10) {
               throw new IllegalStateException();
            } else {
               return var2;
            }
         } else {
            byte[] var3 = classZW.field7284.method11942(5, var0, -753352399);
            var2 = new classOC(new classXY(var3));
            classOC.field4812.method6464(var2, var0);
            return var2;
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "mr.af(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;Ljava/lang/String;)Z")
   @ObfuscatedName("aw")
   public static boolean method7689(ClientPreferences var0, String var1) {
      if (var0 == null) {
         var0.method2840();
      }

      int var2 = var0.method2810(var1, (byte)32);
      return var0.field1340.containsKey(var2);
   }

   classMR() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lop;)Z")
   @ObfuscatedName("wz")
   public static boolean method7690(ItemComposition var0) {
      return var0.field5059;
   }

   @ObfuscatedSignature(descriptor = "(Lva;I)V")
   @ObfuscatedName("az")
   public static void method7687(IndexDataBase var0, int var1) {
      try {
         classPA.field5206 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "mr.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lxy;II)V")
   @ObfuscatedName("gf")
   static void method7691(classXY var0, int var1, int var2) {
      try {
         classCK.method1571(var0.field6952, var1, 1087227559);
         classBZ.method1392(var0, var1, -1038651980);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "mr.gf(" + ')');
      }
   }
}
