import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("oc")
public class classOC extends classVJ {
   @ObfuscatedSignature(descriptor = "Ljz;")
   @ObfuscatedName("ab")
   public static NodeCache field4812 = new NodeCache(64);
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field4815 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field4817 = 2;
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("as")
   final IterableHashTable field4814;
   @ToRemove(unused = "true")
   @ObfuscatedName("al")
   public static final int field4816 = 5;
   @ObfuscatedName("ag")
   public final int field4813;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loc;")
   @ObfuscatedName("as")
   public static classOC method8333(int var0) {
      classOC var1 = (classOC)NodeCache.method6460(field4812, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classZW.field7284.method11942(5, var0, -1506492378);
         var1 = new classOC(new classXY(var2));
         field4812.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;B)Ljava/lang/String;")
   @ObfuscatedName("ab")
   public String method8340(int var1, String var2, byte var3) {
      try {
         return classFS.method4998(this.field4814, var1, var2, (byte)50);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "oc.ab(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)V")
   @ObfuscatedName("ag")
   public static void method8332(IndexDataBase var0) {
      classZW.field7284 = var0;
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("ao")
   public int method8336(int var1, int var2) {
      return classGQ.method5635(this.field4814, var1, var2, 565551096);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loc;")
   @ObfuscatedName("ax")
   public static classOC method8334(int var0) {
      classOC var1 = (classOC)NodeCache.method6460(field4812, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classZW.field7284.method11942(5, var0, -751956593);
         var1 = new classOC(new classXY(var2));
         field4812.method6464(var1, var0);
         return var1;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Loc;")
   @ObfuscatedName("ac")
   public static classOC method8335(int var0) {
      classOC var1 = (classOC)NodeCache.method6460(field4812, var0);
      if (null != var1) {
         return var1;
      } else {
         byte[] var2 = classZW.field7284.method11942(5, var0, -1228399928);
         var1 = new classOC(new classXY(var2));
         field4812.method6464(var1, var0);
         return var1;
      }
   }

   @ObfuscatedSignature(descriptor = "(Loc;IIB)I")
   @ObfuscatedName("vr")
   public static int method8337(classOC var0, int var1, int var2, byte var3) {
      if (var0 == null) {
         return var0.method8339(var1, var1, var3);
      } else {
         try {
            return classGQ.method5635(var0.field4814, var1, var2, -1679790382);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "oc.ae(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("aa")
   public int method8338(int var1, int var2) {
      return classGQ.method5635(this.field4814, var1, var2, 62885504);
   }

   classOC(classXY var1) {
      int var2 = 0;
      IterableHashTable var3 = null;
      if (var1 != null && var1.field6952 != null) {
         while (true) {
            int var4 = var1.method13095(-346779531);
            if (0 == var4) {
               break;
            }

            switch (var4) {
               case 2:
                  var2 = var1.method13099(-1413961900);
                  break;
               case 249:
                  var3 = classQH.method9572(var1, var3, 1978086108);
            }
         }
      }

      this.field4813 = 1739391975 * var2;
      if (var3 == null) {
         this.field4814 = new IterableHashTable(1);
      } else {
         this.field4814 = var3;
      }
   }

   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)Ljava/lang/String;")
   @ObfuscatedName("aj")
   public String method8341(int var1, String var2) {
      return classFS.method4998(this.field4814, var1, var2, (byte)93);
   }

   @ObfuscatedSignature(descriptor = "(IIB)I")
   @ObfuscatedName("ae")
   public int method8339(int var1, int var2, byte var3) {
      try {
         return classGQ.method5635(this.field4814, var1, var2, -1679790382);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "oc.ae(" + 41);
      }
   }
}
