import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("sw")
public class classSW {
   @ObfuscatedName("ay")
   public int field6090;
   @ObfuscatedName("af")
   public int field6077;
   @ObfuscatedName("aw")
   public int field6088;
   @ObfuscatedName("ae")
   public int field6076;
   @ObfuscatedName("ag")
   public int field6078;
   @ObfuscatedName("as")
   public int field6086;
   @ObfuscatedName("ax")
   public int field6089;
   @ObfuscatedName("ad")
   public int field6075;
   @ObfuscatedName("aa")
   public int field6072;
   @ObfuscatedName("ac")
   public int field6074;
   @ObfuscatedName("al")
   public int field6081;
   @ObfuscatedName("az")
   public int field6091;
   @ObfuscatedName("ao")
   public int field6082;
   @ObfuscatedName("aq")
   public int field6084;
   @ObfuscatedName("ab")
   public int field6073;
   @ObfuscatedName("ap")
   public int field6083;
   @ObfuscatedName("au")
   public int field6087;
   @ObfuscatedName("aj")
   public int field6080;
   @ObfuscatedName("ar")
   public int field6085;
   @ObfuscatedName("ai")
   public int field6079;

   @ObfuscatedSignature(descriptor = "(Lyk;IIIIIIS)V")
   @ObfuscatedName("ei")
   static void method10742(IndexedObjectSet var0, int var1, int var2, int var3, int var4, int var5, int var6, short var7) {
      try {
         if (9 == var3) {
            if (var7 == 765) {
               throw new IllegalStateException();
            }

            int var8 = var4 & 65535;
            NPC var9 = (NPC)IndexedObjectSet.method13449(var0, var8);
            if (null != var9) {
               if (var7 == 765) {
                  throw new IllegalStateException();
               }

               client.field958 = var5 * -727473855;
               client.field959 = 527203475 * var6;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = var1 * -1254957895;
               client.field835 = var2 * -877699493;
               int var10 = var4 >> 16;
               classJL var11 = classEF.method3863(classJS.OPNPC1_V2, client.field796.field1533, (byte)88);
               classXY.method13239(var11.field3341, var8, (byte)65);
               classXY.method13029(var11.field3341, var10, (byte)-86);
               var11.field3341.method13209(client.field932.method4563(82, 1700143560) ? 1 : 0, -1910538783);
               client.field796.method3374(var11, -1301097035);
            }
         }

         if (var3 == 10) {
            int var13 = var4 & 65535;
            NPC var17 = (NPC)IndexedObjectSet.method13449(var0, var13);
            if (var17 != null) {
               if (var7 == 765) {
                  return;
               }

               client.field958 = var5 * -727473855;
               client.field959 = var6 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = var1 * -1254957895;
               client.field835 = var2 * -877699493;
               int var21 = var4 >> 16;
               classJL var25 = classEF.method3863(classJS.OPNPC2_V2, client.field796.field1533, (byte)52);
               classXY.method13239(var25.field3341, var13, (byte)11);
               var25.field3341.method13209(var21, -1910538783);
               var25.field3341.method13214(client.field932.method4563(82, 748892206) ? 1 : 0, (byte)-8);
               client.field796.method3374(var25, -1301097035);
            }
         }

         if (11 == var3) {
            if (var7 == 765) {
               throw new IllegalStateException();
            }

            int var14 = var4 & 65535;
            NPC var18 = (NPC)IndexedObjectSet.method13449(var0, var14);
            if (var18 != null) {
               if (var7 == 765) {
                  throw new IllegalStateException();
               }

               client.field958 = var5 * -727473855;
               client.field959 = var6 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var1;
               client.field835 = -877699493 * var2;
               int var22 = var4 >> 16;
               classJL var26 = classEF.method3863(classJS.OPNPC3_V2, client.field796.field1533, (byte)-45);
               classXY.method13239(var26.field3341, var14, (byte)-114);
               var26.field3341.method13214(client.field932.method4563(82, 1084239123) ? 1 : 0, (byte)107);
               var26.field3341.method13211(var22, (byte)86);
               client.field796.method3374(var26, -1301097035);
            }
         }

         if (var3 == 12) {
            int var15 = var4 & 65535;
            NPC var19 = (NPC)IndexedObjectSet.method13449(var0, var15);
            if (null != var19) {
               if (var7 == 765) {
                  return;
               }

               client.field958 = var5 * -727473855;
               client.field959 = var6 * 527203475;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = var1 * -1254957895;
               client.field835 = -877699493 * var2;
               int var23 = var4 >> 16;
               classJL var27 = classEF.method3863(classJS.OPNPC4_V2, client.field796.field1533, (byte)14);
               PacketBuffer var10000 = var27.field3341;
               byte var10001;
               if (client.field932.method4563(82, 807060348)) {
                  if (var7 == 765) {
                     throw new IllegalStateException();
                  }

                  var10001 = 1;
               } else {
                  var10001 = 0;
               }

               var10000.method13214(var10001, (byte)-15);
               classXY.method13029(var27.field3341, var23, (byte)-6);
               classXY.method13242(var27.field3341, var15, (byte)16);
               client.field796.method3374(var27, -1301097035);
            }
         }

         if (var3 == 13) {
            int var16 = var4 & 65535;
            NPC var20 = (NPC)IndexedObjectSet.method13449(var0, var16);
            if (var20 != null) {
               if (var7 == 765) {
                  return;
               }

               client.field958 = -727473855 * var5;
               client.field959 = 527203475 * var6;
               client.field864 = -921463806;
               client.field970 = 0;
               client.field860 = -1254957895 * var1;
               client.field835 = var2 * -877699493;
               int var24 = var4 >> 16;
               classJL var28 = classEF.method3863(classJS.OPNPC5_V2, client.field796.field1533, (byte)108);
               classXY.method13029(var28.field3341, var24, (byte)-23);
               PacketBuffer var29 = var28.field3341;
               byte var30;
               if (client.field932.method4563(82, 1388532673)) {
                  if (var7 == 765) {
                     throw new IllegalStateException();
                  }

                  var30 = 1;
               } else {
                  var30 = 0;
               }

               var29.method13211(var30, (byte)59);
               var28.field3341.method13031(var16, -1542382992);
               client.field796.method3374(var28, -1301097035);
            }
         }
      } catch (RuntimeException var12) {
         throw classEG.method3899(var12, "sw.ei(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lvl;IIIII)Z")
   @ObfuscatedName("as")
   public static boolean method10741(classVL var0, int var1, int var2, int var3, int var4, int var5) {
      try {
         return !classTW.method11267(var0, var1, var2, var3, var4, 1345844916)
            ? false
            : classNL.method7926(
               var0.method12049(0, -1811283006),
               var0.method12053(0, (byte)-91),
               var0.method12049(1, 647057276),
               var0.method12053(1, (byte)-14),
               var0.method12049(2, -1900315998),
               var0.method12053(2, (byte)6),
               var0.method12049(3, -1969765473),
               var0.method12053(3, (byte)-11),
               var1,
               var2,
               var3,
               var4,
               1417269648
            );
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "sw.as(" + ')');
      }
   }
}
