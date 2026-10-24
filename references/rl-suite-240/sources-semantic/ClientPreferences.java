import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.runelite.api.Preferences;
import net.runelite.api.events.VolumeChanged;
import net.runelite.api.events.VolumeChanged.Type;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cy")
public class ClientPreferences implements Preferences {
   @ObfuscatedName("ai")
   int field1348;
   @ObfuscatedName("ag")
   final Map field1340 = new LinkedHashMap();
   @ObfuscatedName("as")
   boolean field1349;
   @ObfuscatedName("ax")
   boolean field1339 = false;
   @ObfuscatedName("ac")
   boolean field1354;
   @ObfuscatedName("aa")
   boolean field1351 = false;
   @ObfuscatedName("ad")
   int field1342;
   @ObfuscatedName("ao")
   int field1344;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1355 = 320;
   @ToRemove(unused = "true")
   @ObfuscatedName("bl")
   static final int field1356 = 64;
   @ObfuscatedName("aq")
   int field1352;
   @ObfuscatedName("aj")
   int field1341;
   @ObfuscatedName("ay")
   int field1338;
   @ObfuscatedName("au")
   int field1343;
   @ObfuscatedName("ap")
   String field1350;
   @ObfuscatedName("ar")
   int field1345;
   @ObfuscatedName("aw")
   boolean field1346;
   @ObfuscatedName("ak")
   int field1347;
   @ObfuscatedName("ii")
   static String field1357;
   @ObfuscatedName("al")
   double field1353 = 0.8;

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("ae")
   void method2731(boolean var1, byte var2) {
      try {
         this.field1354 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ao(" + ')');
      }
   }

   ClientPreferences(classXY var1) {
      this.field1341 = 1880647211;
      this.method2836(-1);
      this.field1338 = -1859112745;
      this.method2837(-1);
      this.field1352 = 1942373673;
      this.method2839(-1);
      this.field1342 = -532488857;
      this.field1350 = null;
      this.field1343 = -2139190381;
      this.field1348 = 850081463;
      this.field1345 = 1977767644;
      this.field1346 = false;
      this.field1347 = 0;
      if (null != var1 && var1.field6952 != null && 0 != var1.field6952.length) {
         int var2 = var1.method13095(-346779531);
         if (var2 >= 0 && var2 <= 13) {
            if (var1.method13095(-346779531) == 1) {
               this.field1349 = true;
            }

            if (var2 > 1) {
               this.field1354 = var1.method13095(-346779531) == 1;
            }

            if (var2 > 3) {
               this.field1343 = var1.method13095(-346779531) * -2139190381;
            }

            if (var2 > 2) {
               int var3 = var1.method13095(-346779531);

               for (int var4 = 0; var4 < var3; var4++) {
                  int var5 = classXY.method13113(var1, (byte)1);
                  int var6 = classXY.method13113(var1, (byte)1);
                  this.field1340.put(var5, var6);
               }
            }

            if (var2 > 4) {
               this.field1350 = var1.method13125((byte)-13);
            }

            if (var2 > 5) {
               this.field1339 = var1.method13122((byte)17);
            }

            if (var2 > 6) {
               this.field1353 = var1.method13095(-346779531) / 100.0;
               this.field1341 = var1.method13095(-346779531) * -188103595;
               this.method2836(-1);
               this.field1338 = var1.method13095(-346779531) * 1067557801;
               this.method2837(-1);
               this.field1352 = var1.method13095(-346779531) * -491985321;
               this.method2839(-1);
            }

            if (var2 > 7) {
               this.field1342 = var1.method13095(-346779531) * 532488857;
            }

            if (var2 > 8) {
               this.field1351 = var1.method13095(-346779531) == 1;
            }

            if (var2 > 9) {
               this.field1344 = classXY.method13113(var1, (byte)1) * -1218635879;
            }

            if (var2 > 10) {
               this.field1348 = var1.method13095(-346779531) * -481392817;
            }

            if (var2 > 11) {
               var1.method13095(-346779531);
               this.field1346 = false;
            }

            if (var2 > 12) {
               this.field1347 = classXY.method13113(var1, (byte)1) * 316561455;
            }
         } else {
            this.method2709(true, (byte)-96);
         }
      } else {
         this.method2709(true, (byte)-122);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("av")
   int method2775(int var1) {
      try {
         return this.field1352 * -1752603289;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.av(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Lxy;")
   @ObfuscatedName("ab")
   classXY method2710(int var1) {
      try {
         classXY var2 = new classXY(423, true);
         classXY.method13029(var2, 13, (byte)-20);
         byte var10001;
         if (this.field1349) {
            if (var1 >= 614604451) {
               throw new IllegalStateException();
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         classXY.method13029(var2, var10001, (byte)-33);
         if (this.field1354) {
            if (var1 >= 614604451) {
               throw new IllegalStateException();
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         classXY.method13029(var2, var10001, (byte)-26);
         classXY.method13029(var2, this.field1343 * -462206309, (byte)-62);
         classXY.method13029(var2, this.field1340.size(), (byte)-115);
         Iterator var3 = this.field1340.entrySet().iterator();

         while (var3.hasNext()) {
            if (var1 >= 614604451) {
               throw new IllegalStateException();
            }

            Entry var4 = (Entry)var3.next();
            var2.method13037((Integer)var4.getKey(), 1289349999);
            var2.method13037((Integer)var4.getValue(), 1976655280);
         }

         var2.method13054(null != this.field1350 ? this.field1350 : "", (short)28546);
         classXY.method13049(var2, this.field1339, -1737488759);
         classXY.method13029(var2, (int)(100.0 * this.field1353), (byte)-96);
         classXY.method13029(var2, 1248663805 * this.field1341, (byte)-80);
         classXY.method13029(var2, this.field1338 * -1250316135, (byte)-88);
         classXY.method13029(var2, -1752603289 * this.field1352, (byte)-79);
         classXY.method13029(var2, this.field1342 * -1030221399, (byte)-20);
         if (this.field1351) {
            if (var1 >= 614604451) {
               throw new IllegalStateException();
            }

            var10001 = 1;
         } else {
            var10001 = 0;
         }

         classXY.method13029(var2, var10001, (byte)-39);
         var2.method13037(this.field1344 * 588926121, -489025558);
         classXY.method13029(var2, this.field1348 * -412733521, (byte)-28);
         classXY.method13029(var2, this.field1346 ? 1 : 0, (byte)-78);
         var2.method13037(-2118783281 * this.field1347, 1185619690);
         return var2;
      } catch (RuntimeException var5) {
         throw classEG.method3899(var5, "cy.ab(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ck")
   void method2732(boolean var1) {
      this.field1354 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fg")
   int method2829() {
      return 118742551 * this.field1347;
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ba")
   void method2723(boolean var1, int var2) {
      try {
         this.field1339 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("db")
   void method2832(int var1) {
      this.field1347 = 316561455 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("fy")
   int method2830() {
      return -2118783281 * this.field1347;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;I)I")
   @ObfuscatedName("sp")
   public static int method2796(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2750(var1);
      }

      try {
         return var0.field1343 * -462206309;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.bx(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dq")
   void method2734(int var1) {
      try {
         boolean var10001;
         if (!this.field1351) {
            if (var1 == 829972991) {
               throw new IllegalStateException();
            }

            var10001 = true;
         } else {
            var10001 = false;
         }

         method2740(this, var10001, (byte)-114);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fd")
   void method2826(boolean var1) {
      this.field1346 = var1;
      classMW.method7777(1041414693);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bs")
   static void method2705() {
      classAAR var0 = null;

      try {
         var0 = WallObject.method4636("", classYP.field7026.field4051, true, (byte)1);
         classXY var1 = classAB.field122.method2710(607593018);
         var0.method238(var1.field6952, 0, 702114061 * var1.field6953, -1735767218);
      } catch (Exception var3) {
      }

      try {
         if (var0 != null) {
            var0.method247(true, 1729748320);
         }
      } catch (Exception var2) {
      }
   }

   public void setSoundEffectVolume(int var1) {
      this.field1338 = var1 * 1067557801;
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("bb")
   void method2752(int var1, byte var2) {
      try {
         this.field1344 = var1 * -1218635879;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ad(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("da")
   void method2783(String var1) {
      this.field1350 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("dt")
   void method2757(double var1) {
      try {
         this.field1353 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.au(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)F")
   @ObfuscatedName("bk")
   float method2820(int var1) {
      try {
         return this.field1345 * 1136367791 / 100.0F;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.bk(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("ag")
   boolean method2714(int var1) {
      try {
         return this.field1349;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)I")
   @ObfuscatedName("bt")
   int method2810(String var1, byte var2) {
      try {
         return classEB.method3817(var1.toLowerCase(), (byte)92);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.bt(" + 41);
      }
   }

   public String getRememberedUsername() {
      return this.field1350;
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ah")
   int method2787(byte var1) {
      try {
         return this.field1342 * -1030221399;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.ah(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcy;Ljava/lang/String;S)Z")
   @ObfuscatedName("mu")
   public static boolean method2805(ClientPreferences var0, String var1, short var2) {
      if (var0 == null) {
         return var0.method2807(var1, var2);
      } else {
         try {
            int var3 = var0.method2810(var1, (byte)93);
            return var0.field1340.containsKey(var3);
         } catch (RuntimeException var4) {
            throw classEG.method3899(var4, "cy.br(" + ')');
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)Z")
   @ObfuscatedName("xe")
   public static boolean method2823(ClientPreferences var0) {
      if (var0 == null) {
         var0.method2838();
      }

      return var0.field1346;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("un")
   public boolean method2840() {
      return this.field1354;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bn")
   void method2799(int var1, int var2) {
      try {
         this.field1343 = var1 * -2139190381;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.bc(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dd")
   void method2777(int var1) {
      this.field1352 = -491985321 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bc")
   void method2812(int var1, int var2) {
      try {
         this.field1348 = var1 * -481392817;
         if (classIS.field3051 != null) {
            if (var2 <= 1259557661) {
               return;
            }

            classIS.field3051.field1687.method4281(classAB.field122.method2816((byte)0));
         }

         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.bg(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("bj")
   int method2816(byte var1) {
      try {
         return -412733521 * this.field1348;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.bj(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()Lxy;")
   @ObfuscatedName("yr")
   public classXY method2841() {
      return this.method2710(496016546);
   }

   public boolean getHideUsername() {
      return this.field1339;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ea")
   int method2788() {
      return this.field1342 * -1030221399;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bg")
   void method2822(int var1, int var2) {
      try {
         this.field1345 = 1565965903 * var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.bo(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("bu")
   boolean method2824(int var1) {
      try {
         return this.field1346;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.bu(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bw")
   int method2831(int var1) {
      try {
         return -2118783281 * this.field1347;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.bw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("at")
   void method2833(int var1, byte var2) {
      try {
         this.field1347 = 316561455 * var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.bb(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lcy;")
   @ObfuscatedName("bi")
   static ClientPreferences method2702() {
      classAAR var0 = null;
      ClientPreferences var1 = new ClientPreferences();

      try {
         var0 = WallObject.method4636("", classYP.field7026.field4051, false, (byte)1);
         byte[] var2 = new byte[(int)var0.method251(-153393736)];
         int var3 = 0;

         while (var3 < var2.length) {
            int var4 = var0.method255(var2, var3, var2.length - var3, -1397369950);
            if (var4 == -1) {
               throw new IOException();
            }

            var3 += var4;
         }

         var1 = new ClientPreferences(new classXY(var2));
      } catch (Exception var6) {
      }

      try {
         if (null != var0) {
            var0.method242((byte)1);
         }
      } catch (Exception var5) {
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lcy;")
   @ObfuscatedName("be")
   static ClientPreferences method2703() {
      classAAR var0 = null;
      ClientPreferences var1 = new ClientPreferences();

      try {
         var0 = WallObject.method4636("", classYP.field7026.field4051, false, (byte)1);
         byte[] var2 = new byte[(int)var0.method251(-153393736)];
         int var3 = 0;

         while (var3 < var2.length) {
            int var4 = var0.method255(var2, var3, var2.length - var3, -1397369950);
            if (var4 == -1) {
               throw new IOException();
            }

            var3 += var4;
         }

         var1 = new ClientPreferences(new classXY(var2));
      } catch (Exception var6) {
      }

      try {
         if (null != var0) {
            var0.method242((byte)1);
         }
      } catch (Exception var5) {
      }

      return var1;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;ZB)V")
   @ObfuscatedName("ml")
   public static void method2740(ClientPreferences var0, boolean var1, byte var2) {
      if (var0 == null) {
         var0.method2743(var1, var2);
      }

      try {
         var0.field1351 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ay(" + ')');
      }
   }

   public void setRememberedUsername(String var1) {
      this.field1350 = var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Lcy;")
   @ObfuscatedName("by")
   static ClientPreferences method2704() {
      classAAR var0 = null;
      ClientPreferences var1 = new ClientPreferences();

      try {
         var0 = WallObject.method4636("", classYP.field7026.field4051, false, (byte)1);
         byte[] var2 = new byte[(int)var0.method251(-153393736)];
         int var3 = 0;

         while (var3 < var2.length) {
            int var4 = var0.method255(var2, var3, var2.length - var3, -1397369950);
            if (var4 == -1) {
               throw new IOException();
            }

            var3 += var4;
         }

         var1 = new ClientPreferences(new classXY(var2));
      } catch (Exception var6) {
      }

      try {
         if (null != var0) {
            var0.method242((byte)1);
         }
      } catch (Exception var5) {
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bq")
   static void method2706() {
      classAAR var0 = null;

      try {
         var0 = WallObject.method4636("", classYP.field7026.field4051, true, (byte)1);
         classXY var1 = classAB.field122.method2710(-499879054);
         var0.method238(var1.field6952, 0, 595193876 * var1.field6953, -1673473414);
      } catch (Exception var3) {
      }

      try {
         if (var0 != null) {
            var0.method247(true, 552050983);
         }
      } catch (Exception var2) {
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("bf")
   static void method2707() {
      classAAR var0 = null;

      try {
         var0 = WallObject.method4636("", classYP.field7026.field4051, true, (byte)1);
         classXY var1 = classAB.field122.method2710(-76062650);
         var0.method238(var1.field6952, 0, 702114061 * var1.field6953, -1792106284);
      } catch (Exception var3) {
      }

      try {
         if (var0 != null) {
            var0.method247(true, -732972066);
         }
      } catch (Exception var2) {
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bo")
   void method2763(int var1, int var2) {
      try {
         this.field1341 = var1 * -188103595;
         this.method2836(-1);
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ar(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("bd")
   void method2708(boolean var1) {
   }

   @ObfuscatedSignature(descriptor = "()Lxy;")
   @ObfuscatedName("bl")
   classXY method2711() {
      classXY var1 = new classXY(423, true);
      classXY.method13029(var1, 13, (byte)-121);
      classXY.method13029(var1, this.field1349 ? 1 : 0, (byte)-55);
      classXY.method13029(var1, this.field1354 ? 1 : 0, (byte)-19);
      classXY.method13029(var1, this.field1343 * -462206309, (byte)-55);
      classXY.method13029(var1, this.field1340.size(), (byte)-72);

      for (Entry var3 : this.field1340.entrySet()) {
         var1.method13037((Integer)var3.getKey(), 758169893);
         var1.method13037((Integer)var3.getValue(), 1314601295);
      }

      var1.method13054(null != this.field1350 ? this.field1350 : "", (short)15792);
      classXY.method13049(var1, this.field1339, -1737488759);
      classXY.method13029(var1, (int)(100.0 * this.field1353), (byte)-45);
      classXY.method13029(var1, -169973855 * this.field1341, (byte)-127);
      classXY.method13029(var1, this.field1338 * 1483221444, (byte)-25);
      classXY.method13029(var1, 224749009 * this.field1352, (byte)-92);
      classXY.method13029(var1, this.field1342 * -2106393233, (byte)-90);
      classXY.method13029(var1, this.field1351 ? 1 : 0, (byte)-119);
      var1.method13037(this.field1344 * -757856000, 200759892);
      classXY.method13029(var1, this.field1348 * -412733521, (byte)-8);
      classXY.method13029(var1, this.field1346 ? 1 : 0, (byte)-96);
      var1.method13037(1300958604 * this.field1347, 643079720);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Lxy;")
   @ObfuscatedName("bv")
   classXY method2712() {
      classXY var1 = new classXY(-1129050635, true);
      classXY.method13029(var1, 13, (byte)-23);
      classXY.method13029(var1, this.field1349 ? 1 : 0, (byte)-40);
      classXY.method13029(var1, this.field1354 ? 1 : 0, (byte)-107);
      classXY.method13029(var1, this.field1343 * 1829080374, (byte)-97);
      classXY.method13029(var1, this.field1340.size(), (byte)-27);

      for (Entry var3 : this.field1340.entrySet()) {
         var1.method13037((Integer)var3.getKey(), 1158626782);
         var1.method13037((Integer)var3.getValue(), 2098741072);
      }

      var1.method13054(null != this.field1350 ? this.field1350 : "", (short)25840);
      classXY.method13049(var1, this.field1339, -1737488759);
      classXY.method13029(var1, (int)(100.0 * this.field1353), (byte)-25);
      classXY.method13029(var1, 1248663805 * this.field1341, (byte)-65);
      classXY.method13029(var1, this.field1338 * -1250316135, (byte)-108);
      classXY.method13029(var1, -1752603289 * this.field1352, (byte)-49);
      classXY.method13029(var1, this.field1342 * -1030221399, (byte)-12);
      classXY.method13029(var1, this.field1351 ? 1 : 0, (byte)-63);
      var1.method13037(this.field1344 * 52298410, -118489349);
      classXY.method13029(var1, this.field1348 * -643597342, (byte)-33);
      classXY.method13029(var1, this.field1346 ? 1 : 0, (byte)-8);
      var1.method13037(-2118783281 * this.field1347, -604304076);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Lxy;")
   @ObfuscatedName("bh")
   classXY method2713() {
      classXY var1 = new classXY(423, true);
      classXY.method13029(var1, 13, (byte)-15);
      classXY.method13029(var1, this.field1349 ? 1 : 0, (byte)-30);
      classXY.method13029(var1, this.field1354 ? 1 : 0, (byte)-95);
      classXY.method13029(var1, this.field1343 * -462206309, (byte)-103);
      classXY.method13029(var1, this.field1340.size(), (byte)-65);

      for (Entry var3 : this.field1340.entrySet()) {
         var1.method13037((Integer)var3.getKey(), 1542201510);
         var1.method13037((Integer)var3.getValue(), 849165815);
      }

      var1.method13054(null != this.field1350 ? this.field1350 : "", (short)23385);
      classXY.method13049(var1, this.field1339, -1737488759);
      classXY.method13029(var1, (int)(100.0 * this.field1353), (byte)-91);
      classXY.method13029(var1, 1248663805 * this.field1341, (byte)-80);
      classXY.method13029(var1, this.field1338 * -1903610175, (byte)-106);
      classXY.method13029(var1, -1752603289 * this.field1352, (byte)-10);
      classXY.method13029(var1, this.field1342 * -242242232, (byte)-60);
      classXY.method13029(var1, this.field1351 ? 1 : 0, (byte)-99);
      var1.method13037(this.field1344 * 588926121, -352339405);
      classXY.method13029(var1, this.field1348 * -412733521, (byte)-93);
      classXY.method13029(var1, this.field1346 ? 1 : 0, (byte)-63);
      var1.method13037(-2118783281 * this.field1347, 578834382);
      return var1;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bz")
   boolean method2715() {
      return this.field1349;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cr")
   boolean method2716() {
      return this.field1349;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;B)I")
   @ObfuscatedName("hq")
   public static int method2766(ClientPreferences var0, byte var1) {
      if (var0 == null) {
         var0.method2769(var1);
      }

      try {
         return -1250316135 * var0.field1338;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ey")
   int method2789() {
      return this.field1342 * -1030221399;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("zc")
   public static void method2834(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2832(var1);
      }

      var0.field1347 = 316561455 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cp")
   void method2719(boolean var1) {
      this.field1349 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cl")
   boolean method2721() {
      return this.field1339;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;ZI)V")
   @ObfuscatedName("ud")
   public static void method2720(ClientPreferences var0, boolean var1, int var2) {
      if (var0 == null) {
         var0.method2727(var1, var2);
      }

      try {
         var0.field1349 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.as(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cu")
   boolean method2717() {
      return this.field1349;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("dj")
   void method2784(String var1) {
      this.field1350 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cs")
   void method2724(boolean var1) {
      this.field1339 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("aa")
   boolean method2729(byte var1) {
      try {
         return this.field1354;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.aa(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cy")
   void method2725(boolean var1) {
      this.field1339 = var1;
      classMW.method7777(1041414693);
   }

   ClientPreferences() {
      this.field1341 = 1880647211;
      this.method2836(-1);
      this.field1338 = -1859112745;
      this.method2837(-1);
      this.field1352 = 1942373673;
      this.method2839(-1);
      this.field1342 = -532488857;
      this.field1350 = null;
      this.field1343 = -2139190381;
      this.field1348 = 850081463;
      this.field1345 = 1977767644;
      this.field1346 = false;
      this.field1347 = 0;
      this.method2709(true, (byte)-111);
      this.method2838();
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cf")
   void method2735() {
      method2740(this, !this.field1351, (byte)-50);
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ch")
   public boolean method2737() {
      return this.field1351;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;Ljava/lang/String;II)V")
   @ObfuscatedName("bz")
   public static void method2802(ClientPreferences var0, String var1, int var2, int var3) {
      if (var0 == null) {
         var0.method2804(var1, var2, var2);
      }

      try {
         int var4 = var0.method2810(var1, (byte)35);
         if (var0.field1340.size() >= 10 && !var0.field1340.containsKey(var4)) {
            Iterator var5 = var0.field1340.entrySet().iterator();
            var5.next();
            var5.remove();
         }

         var0.field1340.put(var4, var2);
         classMW.method7777(1041414693);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cy.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("ct")
   boolean method2718() {
      return this.field1349;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)I")
   @ObfuscatedName("nv")
   public static int method2790(ClientPreferences var0) {
      return var0.field1342 * -1030221399;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cg")
   void method2741(boolean var1) {
      this.field1351 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cw")
   void method2742(boolean var1) {
      this.field1351 = var1;
      classMW.method7777(1041414693);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;Ljava/lang/String;)Z")
   @ObfuscatedName("ca")
   public static boolean method2806(ClientPreferences var0, String var1) {
      if (var0 == null) {
         var0.method2746();
      }

      int var2 = var0.method2810(var1, (byte)62);
      return var0.field1340.containsKey(var2);
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ca")
   void method2733(boolean var1) {
      this.field1354 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cx")
   int method2744() {
      return this.field1344 * 588926121;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ce")
   int method2745() {
      return this.field1344 * 588926121;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("cm")
   int method2746() {
      return this.field1344 * 1173473751;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;)I")
   @ObfuscatedName("xa")
   public static int method2747(ClientPreferences var0) {
      return var0.field1344 * 588926121;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ed")
   void method2770(int var1) {
      this.field1338 = 1067557801 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dz")
   void method2753(int var1) {
      this.field1344 = var1 * -1218635879;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dv")
   void method2754(int var1) {
      this.field1344 = var1 * -1218635879;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("du")
   void method2758(double var1) {
      this.field1353 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Lcy;I)D")
   @ObfuscatedName("oe")
   public static double method2755(ClientPreferences var0, int var1) {
      if (var0 == null) {
         return var0.method2756(var1);
      } else {
         try {
            return var0.field1353;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "cy.ap(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("fb")
   public boolean method2842() {
      return this.field1349;
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("au")
   void method2759(double var1) {
      this.field1353 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(D)V")
   @ObfuscatedName("di")
   void method2760(double var1) {
      this.field1353 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dr")
   int method2761() {
      return 1248663805 * this.field1341;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cc")
   void method2736() {
      method2740(this, !this.field1351, (byte)-98);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dx")
   void method2764(int var1) {
      this.field1341 = var1 * -188103595;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dw")
   int method2767() {
      return -1250316135 * this.field1338;
   }

   public int getAreaSoundEffectVolume() {
      return this.field1352 * -1752603289;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("xd")
   public int method2843() {
      return this.field1343 * -462206309;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dh")
   int method2768() {
      return -1250316135 * this.field1338;
   }

   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("xa")
   public void method2838() {
      this.field1343 = 2 * -2139190381;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cv")
   void method2726(boolean var1) {
      this.field1339 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Lcy;IB)V")
   @ObfuscatedName("rp")
   public static void method2778(ClientPreferences var0, int var1, byte var2) {
      if (var0 == null) {
         var0.method2779(var1, var2);
      } else {
         try {
            var0.field1352 = -491985321 * var1;
            var0.method2839(-1);
            classMW.method7777(1041414693);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "cy.at(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eh")
   void method2771(int var1) {
      this.field1338 = -114101661 * var1;
      classMW.method7777(1041414693);
   }

   public int getSoundEffectVolume() {
      return this.field1338 * -1250316135;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("eg")
   void method2772(int var1) {
      this.field1338 = 1067557801 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("dg")
   int method2776() {
      return this.field1352 * -1752603289;
   }

   @ObfuscatedSignature(descriptor = "(I)Z")
   @ObfuscatedName("aj")
   public boolean method2738(int var1) {
      try {
         return this.field1351;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.aj(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("df")
   String method2780() {
      return this.field1350;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("ds")
   String method2781() {
      return this.field1350;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("dp")
   void method2785(String var1) {
      this.field1350 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(B)Ljava/lang/String;")
   @ObfuscatedName("an")
   String method2782(byte var1) {
      try {
         return this.field1350;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.an(" + ')');
      }
   }

   public void setAreaSoundEffectVolume(int var1) {
      this.field1352 = var1 * -491985321;
   }

   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("fj")
   void method2827(boolean var1) {
      this.field1346 = var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ek")
   int method2791() {
      return this.field1342 * -604012199;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("am")
   void method2786(String var1, int var2) {
      try {
         this.field1350 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.am(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ej")
   int method2792() {
      return this.field1342 * -1030221399;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("fi")
   void method2793(int var1) {
      this.field1342 = 532488857 * var1;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ev")
   int method2797() {
      return this.field1343 * -462206309;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("el")
   int method2798() {
      return this.field1343 * -462206309;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ew")
   void method2800(int var1) {
      this.field1343 = var1 * -2139190381;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("et")
   void method2801(int var1) {
      this.field1343 = var1 * -1027028293;
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)V")
   @ObfuscatedName("en")
   void method2803(String var1, int var2) {
      int var3 = this.method2810(var1, (byte)125);
      if (this.field1340.size() >= 10 && !this.field1340.containsKey(var3)) {
         Iterator var4 = this.field1340.entrySet().iterator();
         var4.next();
         var4.remove();
      }

      this.field1340.put(var3, var2);
      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("ay")
   void method2709(boolean var1, byte var2) {
      try {
         ;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("ql")
   public static void method2765(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2756(var1);
      }

      var0.field1341 = var1 * -188103595;
      classMW.method7777(1041414693);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lwm;)V")
   @ObfuscatedName("kb")
   public static void method2835(RenderOverview var0) {
      if (var0 == null) {
         var0.method12497();
      }

      var0.field6691 = 739603646;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("iy")
   public void method2836(int var1) {
      classOE.field4841.getCallbacks().post(new VolumeChanged(Type.MUSIC));
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("hd")
   public int method2844() {
      return this.field1344 * 588926121;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("er")
   int method2808(String var1) {
      int var2 = this.method2810(var1, (byte)73);
      return !this.field1340.containsKey(var2) ? 0 : (Integer)this.field1340.get(var2);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcy;I)V")
   @ObfuscatedName("wh")
   public static void method2813(ClientPreferences var0, int var1) {
      if (var0 == null) {
         var0.method2819();
      }

      var0.field1348 = var1 * 1641644051;
      if (classIS.field3051 != null) {
         classIS.field3051.field1687.method4281(classAB.field122.method2816((byte)0));
      }

      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)I")
   @ObfuscatedName("eo")
   int method2811(String var1) {
      return classEB.method3817(var1.toLowerCase(), (byte)87);
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ai")
   int method2762(byte var1) {
      try {
         return 1248663805 * this.field1341;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.ai(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("al")
   void method2814(int var1) {
      this.field1348 = var1 * -481392817;
      if (classIS.field3051 != null) {
         classIS.field3051.field1687.method4281(classAB.field122.method2816((byte)0));
      }

      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "()D")
   @ObfuscatedName("qs")
   public double method2845() {
      return this.field1353;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ef")
   int method2817() {
      return -412733521 * this.field1348;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;I)I")
   @ObfuscatedName("bm")
   int method2809(String var1, int var2) {
      try {
         int var3 = this.method2810(var1, (byte)78);
         if (!this.field1340.containsKey(var3)) {
            if (var2 == -1163968665) {
               throw new IllegalStateException();
            } else {
               return 0;
            }
         } else {
            return (Integer)this.field1340.get(var3);
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cy.bm(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcy;ZI)V")
   @ObfuscatedName("pw")
   public static void method2828(ClientPreferences var0, boolean var1, int var2) {
      if (var0 == null) {
         var0.method2728(var1, var2);
      } else {
         try {
            var0.field1346 = var1;
            classMW.method7777(1041414693);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "cy.ba(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(Lcy;I)I")
   @ObfuscatedName("qj")
   public static int method2748(ClientPreferences var0, int var1) {
      if (var0 == null) {
         return var0.method2751(var1);
      } else {
         try {
            return var0.field1344 * 588926121;
         } catch (RuntimeException var2) {
            throw classEG.method3899(var2, "cy.aq(" + 41);
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ez")
   int method2818() {
      return -412733521 * this.field1348;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("nb")
   public int method2846() {
      return this.field1342 * -1030221399;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;II)V")
   @ObfuscatedName("zt")
   public static void method2794(ClientPreferences var0, int var1, int var2) {
      if (var0 == null) {
         var0.method2795(var1, var1);
      } else {
         try {
            var0.field1342 = 532488857 * var1;
            classMW.method7777(1041414693);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "cy.bn(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ei")
   int method2819() {
      return -412733521 * this.field1348;
   }

   @ObfuscatedSignature(descriptor = "()F")
   @ObfuscatedName("eb")
   float method2821() {
      return this.field1345 * 1136367791 / 100.0F;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("us")
   public boolean method2847() {
      return this.field1351;
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("fo")
   boolean method2825() {
      return this.field1346;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("xs")
   public void method2837(int var1) {
      classOE.field4841.getCallbacks().post(new VolumeChanged(Type.EFFECTS));
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ci")
   int method2749() {
      return this.field1344 * 588926121;
   }

   @ObfuscatedSignature(descriptor = "(S)Z")
   @ObfuscatedName("ax")
   boolean method2722(short var1) {
      try {
         return this.field1339;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("cz")
   public boolean method2739() {
      return this.field1351;
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dn")
   void method2815(int var1) {
      this.field1348 = var1 * -481392817;
      if (classIS.field3051 != null) {
         classIS.field3051.field1687.method4281(classAB.field122.method2816((byte)0));
      }

      classMW.method7777(1041414693);
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("ac")
   void method2727(boolean var1, int var2) {
      try {
         this.field1339 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("aw")
   int method2769(byte var1) {
      try {
         return -1250316135 * this.field1344;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.aw(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ar")
   void method2773(int var1, int var2) {
      try {
         this.field1345 = 1067557801 * var1;
         this.setAreaSoundEffectVolume(-1);
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ak(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)V")
   @ObfuscatedName("ad")
   void method2779(int var1, byte var2) {
      try {
         this.field1338 = -491985321 * var1;
         this.method2836(-1);
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.at(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ak")
   void method2795(int var1, int var2) {
      try {
         this.field1345 = 532488857 * var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.bn(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bx")
   int method2750(int var1) {
      try {
         return this.field1352 * 588926121;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.aq(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("cb")
   public void method2839(int var1) {
      classOE.field4841.getCallbacks().post(new VolumeChanged(Type.AREA));
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;II)V")
   @ObfuscatedName("bp")
   void method2804(String var1, int var2, int var3) {
      try {
         int var4 = this.method2810(var1, (byte)35);
         if (this.field1340.size() >= 10 && !this.field1340.containsKey(var4)) {
            Iterator var5 = this.field1340.entrySet().iterator();
            var5.next();
            var5.remove();
         }

         this.field1340.put(var4, var2);
         classMW.method7777(1041414693);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "cy.bp(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;S)Z")
   @ObfuscatedName("br")
   boolean method2807(String var1, short var2) {
      try {
         int var3 = this.method2810(var1, (byte)93);
         return this.field1340.containsKey(var3);
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "cy.br(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZB)V")
   @ObfuscatedName("ao")
   void method2743(boolean var1, byte var2) {
      try {
         this.field1339 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(ZI)V")
   @ObfuscatedName("as")
   void method2728(boolean var1, int var2) {
      try {
         this.field1349 = var1;
         classMW.method7777(1041414693);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "cy.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("co")
   boolean method2730() {
      return this.field1339;
   }

   @ObfuscatedSignature(descriptor = "(Lcy;II)V")
   @ObfuscatedName("ye")
   public static void method2774(ClientPreferences var0, int var1, int var2) {
      if (var0 == null) {
         var0.method2773(var1, var1);
      } else {
         try {
            var0.field1338 = 1067557801 * var1;
            var0.method2837(-1);
            classMW.method7777(1041414693);
         } catch (RuntimeException var3) {
            throw classEG.method3899(var3, "cy.ak(" + ')');
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)D")
   @ObfuscatedName("ap")
   public double method2756(int var1) {
      try {
         return this.field1353;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.ap(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("aq")
   int method2751(int var1) {
      try {
         return this.field1338 * 588926121;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cy.aq(" + 41);
      }
   }
}
