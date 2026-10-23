import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cf")
public class classCF implements classJV {
   @ObfuscatedSignature(descriptor = "[Lyz;")
   @ObfuscatedName("dk")
   static IndexedSprite[] field720;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   public static final String field719 = "[a-zA-Z0-9_]+";
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   public static final int field716 = 8;
   @ToRemove(unused = "true")
   @ObfuscatedName("dx")
   public static final int field718 = 32;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   static final int field717 = 69;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field715 = 50;

   classCF() {
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("al")
   @Override
   public boolean vmethod152() {
      return client.field974;
   }

   @ObfuscatedSignature(descriptor = "(J)J")
   @ObfuscatedName("ax")
   static long method1457(long var0) {
      try {
         return var0 & -524289L;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ae")
   @Override
   public boolean vmethod163(int var1) {
      try {
         boolean var10000;
         if (classBA.field305 * -782895767 < 3) {
            if (var1 <= -1231316179) {
               throw new IllegalStateException();
            }

            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ae(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   @Override
   public int vmethod158(int var1) {
      try {
         Menu var2 = client.field975;
         return var2.field6795 * -1927903443;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cf.af(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Llw;II)I")
   @ObfuscatedName("aa")
   @Override
   public int vmethod181(Widget var1, int var2, int var3) {
      try {
         return classEL.method3945(var1, var2, -352598277);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cf.aa(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   @Override
   public int vmethod172(int var1) {
      try {
         return GameEngine.field6191 * -380757181;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ab")
   @Override
   public boolean vmethod166(byte var1) {
      try {
         return client.field892;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ac")
   @Override
   public int vmethod178(int var1) {
      try {
         return client.field993.field4092 * 1705409403;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ac(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bj")
   @Override
   public int vmethod185() {
      return client.field995.method11500((short)-26951);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lbv;)I")
   @ObfuscatedName("yk")
   public static int method1458(ChatLineBuffer var0) {
      return var0.field583 * 1160694231;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ao")
   @Override
   public int vmethod186(int var1) {
      try {
         return client.field995.method11500((short)-18022);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ao(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   @Override
   public boolean vmethod168(int var1) {
      try {
         return classON.field4993;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aw")
   @Override
   public boolean vmethod164() {
      return classBA.field305 * -782895767 < 3;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ay")
   @Override
   public boolean vmethod153() {
      return client.field974;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aq")
   @Override
   public boolean vmethod154() {
      return client.field974;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ad")
   @Override
   public boolean vmethod155() {
      return client.field974;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("am")
   @Override
   public boolean vmethod169() {
      return classON.field4993;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ar")
   @Override
   public int vmethod159() {
      Menu var1 = client.field975;
      return var1.field6795 * -1927903443;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   @Override
   public int vmethod160() {
      Menu var1 = client.field975;
      return var1.field6795 * -1927903443;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bn")
   @Override
   public int vmethod173() {
      return GameEngine.field6191 * -380757181;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bc")
   @Override
   public boolean vmethod175() {
      return client.field886;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ak")
   @Override
   public boolean vmethod165() {
      return classBA.field305 * -782895767 < 3;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("av")
   @Override
   public boolean vmethod167() {
      return client.field892;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("at")
   @Override
   public boolean vmethod170() {
      return classON.field4993;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("an")
   @Override
   public boolean vmethod171() {
      return classON.field4993;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   @Override
   public int vmethod161() {
      Menu var1 = client.field975;
      return var1.field6795 * -1927903443;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ah")
   @Override
   public int vmethod174() {
      return GameEngine.field6191 * -380757181;
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("az")
   @Override
   public boolean vmethod156(byte var1) {
      try {
         return client.field974;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bx")
   @Override
   public boolean vmethod176() {
      return client.field886;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("aj")
   @Override
   public boolean vmethod157() {
      return client.field974;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bp")
   @Override
   public int vmethod179() {
      return client.field993.field4092 * -1016111219;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("br")
   @Override
   public int vmethod180() {
      return client.field993.field4092 * 1705409403;
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)I")
   @ObfuscatedName("bm")
   @Override
   public int vmethod182(Widget var1, int var2) {
      return classEL.method3945(var1, var2, -315464401);
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)I")
   @ObfuscatedName("bt")
   @Override
   public int vmethod183(Widget var1, int var2) {
      return classEL.method3945(var1, var2, 1068669056);
   }

   @ObfuscatedSignature(descriptor = "(Llw;I)I")
   @ObfuscatedName("bg")
   @Override
   public int vmethod184(Widget var1, int var2) {
      return classEL.method3945(var1, var2, -1214519160);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   @Override
   public int vmethod162() {
      Menu var1 = client.field975;
      return var1.field6795 * -1927903443;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bk")
   @Override
   public int vmethod187() {
      return client.field995.method11500((short)-30843);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("bo")
   @Override
   public int vmethod188() {
      return client.field995.method11500((short)-16655);
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ax")
   @Override
   public boolean vmethod177(int var1) {
      try {
         return client.field886;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bg")
   static void method1459(int var0, int var1) {
      try {
         classJL var2 = classEF.method3863(classJS.SET_HEADING, client.field796.field1533, (byte)103);
         var2.field3341.method13209(var0, -1910538783);
         client.field796.method3374(var2, -1301097035);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cf.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("ab")
   public static int method1456(int var0, byte var1) {
      try {
         return var0 & 65535;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cf.ab(" + 41);
      }
   }
}
