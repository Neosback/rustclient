import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("pa")
public class classPA extends classVJ {
   @ObfuscatedName("as")
   public final int field5202;
   @ObfuscatedSignature(descriptor = "Lva;")
   @ObfuscatedName("ae")
   static IndexDataBase field5206;
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ab")
   static NodeCache field5201 = new NodeCache(64);
   @ObfuscatedName("ag")
   public final boolean field5203;
   @ToRemove(unused = "true")
   @ObfuscatedName("ba")
   public static final int field5205 = 49;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field5204 = 2;
   @ObfuscatedName("bw")
   static boolean field5207;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("al")
   public static void method8866() {
      field5201.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ab")
   public static void method8861(IndexDataBase var0) {
      field5206 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ao")
   public static void method8867() {
      field5201.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("as")
   public static void method8862(IndexDataBase var0) {
      field5206 = var0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpa;")
   @ObfuscatedName("ax")
   public static classPA method8864(int var0) {
      classPA var1 = (classPA)NodeCache.method6460(field5201, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5206.method11942(19, var0, -1462983089);
         var1 = new classPA(new classXY(var2));
         field5201.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lpa;")
   @ObfuscatedName("ac")
   public static classPA method8865(int var0) {
      classPA var1 = (classPA)NodeCache.method6460(field5201, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = field5206.method11942(19, var0, -1619578252);
         var1 = new classPA(new classXY(var2));
         field5201.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aa")
   public static void method8868() {
      field5201.method6468();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method8863(IndexDataBase var0) {
      field5206 = var0;
   }

   classPA(classXY var1) {
      boolean var2 = false;
      int var3 = -1;
      if (var1 != null && var1.field6952 != null) {
         label29:
         while (true) {
            int var4 = var1.method13095(-346779531);
            switch (var4) {
               case 0:
                  break label29;
               case 1:
               default:
                  continue;
               case 2:
                  var2 = true;
                  continue;
               case 3:
            }

            var3 = var1.method13099(-2006674292);
         }
      }

      this.field5203 = var2;
      this.field5202 = var3 * -1405933545;
   }
}
