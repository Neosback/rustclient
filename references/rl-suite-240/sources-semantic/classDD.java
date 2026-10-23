import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("dd")
public class classDD {
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field1396 = 4;
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field1397 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("ag")
   static final int field1398 = 6;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field1400 = 2;
   @ObfuscatedName("al")
   static final BigInteger field1394 = new BigInteger("80782894952180643741752986186714059433953886149239752893425047584684715842049");
   @ObfuscatedName("aj")
   static final BigInteger field1395 = new BigInteger(
      "7237300117305667488707183861728052766358166655052137727439795191253340127955075499635575104901523446809299097934591732635674173519120047404024393881551683"
   );
   @ObfuscatedName("qo")
   protected static boolean field1403;
   @ToRemove(unused = "true")
   @ObfuscatedName("aw")
   static final int field1399 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("bl")
   static final int field1401 = 126;
   @ToRemove(unused = "true")
   @ObfuscatedName("br")
   public static final int field1402 = 8;

   classDD() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("ax")
   static boolean method2995(String var0) {
      if (var0 == null) {
         return false;
      } else {
         try {
            new URL(var0);
            return true;
         } catch (MalformedURLException var2) {
            return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;)I")
   @ObfuscatedName("ab")
   static final int method2989(long var0, String var2) {
      Random var3 = new Random();
      classXY var4 = new classXY(128);
      classXY var5 = new classXY(128);
      int[] var6 = new int[]{var3.nextInt(), var3.nextInt(), (int)(var0 >> 32), (int)var0};
      classXY.method13029(var4, 10, (byte)-68);

      for (int var7 = 0; var7 < 4; var7++) {
         var4.method13037(var3.nextInt(), 835048006);
      }

      var4.method13037(var6[0], -367961697);
      var4.method13037(var6[1], 774369842);
      classXY.method13047(var4, var0);
      classXY.method13047(var4, 0L);

      for (int var20 = 0; var20 < 4; var20++) {
         var4.method13037(var3.nextInt(), -727748692);
      }

      var4.method13195(field1394, field1395, -74279588);
      classXY.method13029(var5, 10, (byte)-3);

      for (int var21 = 0; var21 < 3; var21++) {
         var5.method13037(var3.nextInt(), 1961805673);
      }

      classXY.method13047(var5, var3.nextLong());
      var5.method13039(var3.nextLong());
      if (null != client.field945) {
         var5.method13067(client.field945, 0, client.field945.length, (byte)121);
      } else {
         byte[] var22 = classJL.method6431((byte)0);
         var5.method13067(var22, 0, var22.length, (byte)46);
      }

      classXY.method13047(var5, var3.nextLong());
      var5.method13195(field1394, field1395, -641440734);
      int var23 = classAU.method709(var2, (byte)58);
      if (var23 % 8 != 0) {
         var23 += 8 - var23 % 8;
      }

      classXY var8 = new classXY(var23);
      var8.method13054(var2, (short)24309);
      var8.field6953 = -1095856699 * var23;
      var8.method13182(var6, (byte)3);
      classXY var9 = new classXY(702114061 * var8.field6953 + 702114061 * var5.field6953 + 5 + 702114061 * var4.field6953);
      classXY.method13029(var9, 2, (byte)-121);
      classXY.method13029(var9, var4.field6953 * 702114061, (byte)-15);
      var9.method13067(var4.field6952, 0, var4.field6953 * 702114061, (byte)27);
      classXY.method13029(var9, 702114061 * var5.field6953, (byte)-22);
      var9.method13067(var5.field6952, 0, 702114061 * var5.field6953, (byte)71);
      var9.method13031(702114061 * var8.field6953, -1542382992);
      var9.method13067(var8.field6952, 0, var8.field6953 * 702114061, (byte)76);
      byte[] var11 = var9.field6952;
      int var13 = var11.length;
      StringBuilder var14 = new StringBuilder();

      for (byte var15 = 0; var15 < var13 + 0; var15 += 3) {
         int var16 = var11[var15] & 255;
         var14.append(classOX.field5184[var16 >>> 2]);
         if (var15 < var13 - 1) {
            int var17 = var11[1 + var15] & 255;
            var14.append(classOX.field5184[(var16 & 3) << 4 | var17 >>> 4]);
            if (var15 < var13 - 2) {
               int var18 = var11[var15 + 2] & 255;
               var14.append(classOX.field5184[(var17 & 15) << 2 | var18 >>> 6]).append(classOX.field5184[var18 & 63]);
            } else {
               var14.append(classOX.field5184[(var17 & 15) << 2]).append("=");
            }
         } else {
            var14.append(classOX.field5184[(var16 & 3) << 4]).append("==");
         }
      }

      String var12 = var14.toString();
      var12 = var12;

      try {
         URL var26 = new URL(classLZ.method7470("services", false, -1616697665) + "m=accountappeal/login.ws");
         URLConnection var27 = var26.openConnection();
         var27.setDoInput(true);
         var27.setDoOutput(true);
         var27.setConnectTimeout(5000);
         OutputStreamWriter var28 = new OutputStreamWriter(var27.getOutputStream());
         var28.write("data2=" + NPC.method3448(var12, (byte)93) + "&dest=" + NPC.method3448("passwordchoice.ws", (byte)17));
         var28.flush();
         InputStream var29 = var27.getInputStream();
         var9 = new classXY(new byte[1000]);

         do {
            int var30 = var29.read(var9.field6952, var9.field6953 * 702114061, 1000 - var9.field6953 * 702114061);
            if (var30 == -1) {
               var28.close();
               var29.close();
               String var31 = new String(var9.field6952);
               if (var31.startsWith("OFFLINE")) {
                  return 4;
               } else if (var31.startsWith("WRONG")) {
                  return 7;
               } else if (var31.startsWith("RELOAD")) {
                  return 3;
               } else if (var31.startsWith("Not permitted for social network accounts.")) {
                  return 6;
               } else {
                  var9.method13186(var6, 1656256084);

                  while (702114061 * var9.field6953 > 0 && 0 == var9.field6952[702114061 * var9.field6953 - 1]) {
                     var9.field6953 -= -1095856699;
                  }

                  String var32 = new String(var9.field6952, 0, var9.field6953 * 702114061);
                  if (MessageNode.method1492(var32, 1163143878)) {
                     classUF.method11359(var32, (byte)62);
                     return 2;
                  } else {
                     return 5;
                  }
               }
            }

            var9.field6953 += -1095856699 * var30;
         } while (var9.field6953 * 702114061 < 1000);

         return 5;
      } catch (Throwable var19) {
         var19.printStackTrace();
         return 5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;)I")
   @ObfuscatedName("ag")
   static final int method2990(long var0, String var2) {
      Random var3 = new Random();
      classXY var4 = new classXY(1538782702);
      classXY var5 = new classXY(128);
      int[] var6 = new int[]{var3.nextInt(), var3.nextInt(), (int)(var0 >> 32), (int)var0};
      classXY.method13029(var4, 10, (byte)-20);

      for (int var7 = 0; var7 < 4; var7++) {
         var4.method13037(var3.nextInt(), 697759857);
      }

      var4.method13037(var6[0], 1724257004);
      var4.method13037(var6[1], -898408552);
      classXY.method13047(var4, var0);
      classXY.method13047(var4, 0L);

      for (int var20 = 0; var20 < 4; var20++) {
         var4.method13037(var3.nextInt(), 1024409210);
      }

      var4.method13195(field1394, field1395, -1278356950);
      classXY.method13029(var5, 10, (byte)-75);

      for (int var21 = 0; var21 < 3; var21++) {
         var5.method13037(var3.nextInt(), 627640041);
      }

      classXY.method13047(var5, var3.nextLong());
      var5.method13039(var3.nextLong());
      if (null != client.field945) {
         var5.method13067(client.field945, 0, client.field945.length, (byte)59);
      } else {
         byte[] var22 = classJL.method6431((byte)0);
         var5.method13067(var22, 0, var22.length, (byte)34);
      }

      classXY.method13047(var5, var3.nextLong());
      var5.method13195(field1394, field1395, -1450541613);
      int var23 = classAU.method709(var2, (byte)104);
      if (var23 % 8 != 0) {
         var23 += 8 - var23 % 8;
      }

      classXY var8 = new classXY(var23);
      var8.method13054(var2, (short)11012);
      var8.field6953 = -1095856699 * var23;
      var8.method13182(var6, (byte)28);
      classXY var9 = new classXY(-1332734761 * var8.field6953 + 606524701 * var5.field6953 + 5 + -917339784 * var4.field6953);
      classXY.method13029(var9, 2, (byte)-45);
      classXY.method13029(var9, var4.field6953 * 702114061, (byte)-93);
      var9.method13067(var4.field6952, 0, var4.field6953 * 702114061, (byte)96);
      classXY.method13029(var9, 702114061 * var5.field6953, (byte)-38);
      var9.method13067(var5.field6952, 0, -1684193283 * var5.field6953, (byte)32);
      var9.method13031(1205919196 * var8.field6953, -1542382992);
      var9.method13067(var8.field6952, 0, var8.field6953 * 702114061, (byte)91);
      byte[] var11 = var9.field6952;
      int var13 = var11.length;
      StringBuilder var14 = new StringBuilder();

      for (byte var15 = 0; var15 < var13 + 0; var15 += 3) {
         int var16 = var11[var15] & 700365093;
         var14.append(classOX.field5184[var16 >>> 2]);
         if (var15 < var13 - 1) {
            int var17 = var11[1 + var15] & 1971789109;
            var14.append(classOX.field5184[(var16 & 3) << 4 | var17 >>> 4]);
            if (var15 < var13 - 2) {
               int var18 = var11[var15 + 2] & 255;
               var14.append(classOX.field5184[(var17 & 15) << 2 | var18 >>> 6]).append(classOX.field5184[var18 & -1999115477]);
            } else {
               var14.append(classOX.field5184[(var17 & 15) << 2]).append("=");
            }
         } else {
            var14.append(classOX.field5184[(var16 & 3) << 4]).append("==");
         }
      }

      String var12 = var14.toString();
      var12 = var12;

      try {
         URL var26 = new URL(classLZ.method7470("services", false, 583723529) + "m=accountappeal/login.ws");
         URLConnection var27 = var26.openConnection();
         var27.setDoInput(true);
         var27.setDoOutput(true);
         var27.setConnectTimeout(5000);
         OutputStreamWriter var28 = new OutputStreamWriter(var27.getOutputStream());
         var28.write("data2=" + NPC.method3448(var12, (byte)28) + "&dest=" + NPC.method3448("passwordchoice.ws", (byte)44));
         var28.flush();
         InputStream var29 = var27.getInputStream();
         var9 = new classXY(new byte[1000]);

         do {
            int var30 = var29.read(var9.field6952, var9.field6953 * -373900180, 1259992177 - var9.field6953 * -461319478);
            if (var30 == -1) {
               var28.close();
               var29.close();
               String var31 = new String(var9.field6952);
               if (var31.startsWith("OFFLINE")) {
                  return 4;
               } else if (var31.startsWith("WRONG")) {
                  return 7;
               } else if (var31.startsWith("RELOAD")) {
                  return 3;
               } else if (var31.startsWith("Not permitted for social network accounts.")) {
                  return 6;
               } else {
                  var9.method13186(var6, 1656256084);

                  while (702114061 * var9.field6953 > 0 && 0 == var9.field6952[702114061 * var9.field6953 - 1]) {
                     var9.field6953 -= 2061856494;
                  }

                  String var32 = new String(var9.field6952, 0, var9.field6953 * 702114061);
                  if (MessageNode.method1492(var32, 1533628026)) {
                     classUF.method11359(var32, (byte)-71);
                     return 2;
                  } else {
                     return 5;
                  }
               }
            }

            var9.field6953 += -1095856699 * var30;
         } while (var9.field6953 * 702114061 < -993687384);

         return 5;
      } catch (Throwable var19) {
         var19.printStackTrace();
         return 5;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ar")
   static void method2992(int var0) {
      try {
         for (AmbientSoundEffect var1 = (AmbientSoundEffect)AmbientSoundEffect.field1643.method7957();
            var1 != null;
            var1 = (AmbientSoundEffect)AmbientSoundEffect.field1643.method7965()
         ) {
            if (var1.method3649(1017752756)) {
               if (var0 == -569703815) {
                  return;
               }

               var1.vmethod398();
            }
         }
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dd.ar(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;)I")
   @ObfuscatedName("ae")
   static final int method2991(long var0, String var2) {
      Random var3 = new Random();
      classXY var4 = new classXY(128);
      classXY var5 = new classXY(128);
      int[] var6 = new int[]{var3.nextInt(), var3.nextInt(), (int)(var0 >> 32), (int)var0};
      classXY.method13029(var4, 10, (byte)-128);

      for (int var7 = 0; var7 < 4; var7++) {
         var4.method13037(var3.nextInt(), 1978821546);
      }

      var4.method13037(var6[0], 999062379);
      var4.method13037(var6[1], 1401939045);
      classXY.method13047(var4, var0);
      classXY.method13047(var4, 0L);

      for (int var20 = 0; var20 < 4; var20++) {
         var4.method13037(var3.nextInt(), 926490380);
      }

      var4.method13195(field1394, field1395, -212804795);
      classXY.method13029(var5, 10, (byte)-111);

      for (int var21 = 0; var21 < 3; var21++) {
         var5.method13037(var3.nextInt(), 931787819);
      }

      classXY.method13047(var5, var3.nextLong());
      var5.method13039(var3.nextLong());
      if (null != client.field945) {
         var5.method13067(client.field945, 0, client.field945.length, (byte)91);
      } else {
         byte[] var22 = classJL.method6431((byte)0);
         var5.method13067(var22, 0, var22.length, (byte)111);
      }

      classXY.method13047(var5, var3.nextLong());
      var5.method13195(field1394, field1395, -1300373359);
      int var23 = classAU.method709(var2, (byte)96);
      if (var23 % 8 != 0) {
         var23 += 8 - var23 % 8;
      }

      classXY var8 = new classXY(var23);
      var8.method13054(var2, (short)15815);
      var8.field6953 = -1095856699 * var23;
      var8.method13182(var6, (byte)91);
      classXY var9 = new classXY(702114061 * var8.field6953 + 702114061 * var5.field6953 + 5 + 702114061 * var4.field6953);
      classXY.method13029(var9, 2, (byte)-118);
      classXY.method13029(var9, var4.field6953 * 702114061, (byte)-84);
      var9.method13067(var4.field6952, 0, var4.field6953 * 702114061, (byte)110);
      classXY.method13029(var9, 702114061 * var5.field6953, (byte)-89);
      var9.method13067(var5.field6952, 0, 702114061 * var5.field6953, (byte)90);
      var9.method13031(702114061 * var8.field6953, -1542382992);
      var9.method13067(var8.field6952, 0, var8.field6953 * 702114061, (byte)65);
      byte[] var11 = var9.field6952;
      int var13 = var11.length;
      StringBuilder var14 = new StringBuilder();

      for (byte var15 = 0; var15 < var13 + 0; var15 += 3) {
         int var16 = var11[var15] & 255;
         var14.append(classOX.field5184[var16 >>> 2]);
         if (var15 < var13 - 1) {
            int var17 = var11[1 + var15] & 255;
            var14.append(classOX.field5184[(var16 & 3) << 4 | var17 >>> 4]);
            if (var15 < var13 - 2) {
               int var18 = var11[var15 + 2] & 255;
               var14.append(classOX.field5184[(var17 & 15) << 2 | var18 >>> 6]).append(classOX.field5184[var18 & 63]);
            } else {
               var14.append(classOX.field5184[(var17 & 15) << 2]).append("=");
            }
         } else {
            var14.append(classOX.field5184[(var16 & 3) << 4]).append("==");
         }
      }

      String var12 = var14.toString();
      var12 = var12;

      try {
         URL var26 = new URL(classLZ.method7470("services", false, -415817955) + "m=accountappeal/login.ws");
         URLConnection var27 = var26.openConnection();
         var27.setDoInput(true);
         var27.setDoOutput(true);
         var27.setConnectTimeout(5000);
         OutputStreamWriter var28 = new OutputStreamWriter(var27.getOutputStream());
         var28.write("data2=" + NPC.method3448(var12, (byte)125) + "&dest=" + NPC.method3448("passwordchoice.ws", (byte)63));
         var28.flush();
         InputStream var29 = var27.getInputStream();
         var9 = new classXY(new byte[1000]);

         do {
            int var30 = var29.read(var9.field6952, var9.field6953 * 702114061, 1000 - var9.field6953 * 702114061);
            if (var30 == -1) {
               var28.close();
               var29.close();
               String var31 = new String(var9.field6952);
               if (var31.startsWith("OFFLINE")) {
                  return 4;
               } else if (var31.startsWith("WRONG")) {
                  return 7;
               } else if (var31.startsWith("RELOAD")) {
                  return 3;
               } else if (var31.startsWith("Not permitted for social network accounts.")) {
                  return 6;
               } else {
                  var9.method13186(var6, 1656256084);

                  while (702114061 * var9.field6953 > 0 && 0 == var9.field6952[702114061 * var9.field6953 - 1]) {
                     var9.field6953 -= -1095856699;
                  }

                  String var32 = new String(var9.field6952, 0, var9.field6953 * 702114061);
                  if (MessageNode.method1492(var32, 483993095)) {
                     classUF.method11359(var32, (byte)-90);
                     return 2;
                  } else {
                     return 5;
                  }
               }
            }

            var9.field6953 += -1095856699 * var30;
         } while (var9.field6953 * 702114061 < 1000);

         return 5;
      } catch (Throwable var19) {
         var19.printStackTrace();
         return 5;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("ac")
   static boolean method2996(String var0) {
      if (var0 == null) {
         return false;
      } else {
         try {
            new URL(var0);
            return true;
         } catch (MalformedURLException var2) {
            return false;
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("az")
   public static final synchronized long method2988(int var0) {
      try {
         long var1 = System.currentTimeMillis();
         if (var1 < -3314051952393191505L * classJH.field3155) {
            if (var0 != 16711680) {
               throw new IllegalStateException();
            }

            classJH.field3154 = classJH.field3154 + (classJH.field3155 * -3314051952393191505L - var1) * -5842852769318758503L;
         }

         classJH.field3155 = 6736306076594779983L * var1;
         return -8691437873563546455L * classJH.field3154 + var1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "dd.az(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Luz;I)Lul;")
   @ObfuscatedName("as")
   static classUL method2993(classUZ var0, int var1) {
      try {
         classUZ var2 = classIZ.method6384(classUZ.field6431, var0, 1432038878);
         var2.method11817(1213748358);
         float var3 = classLV.method7202(classUZ.field6431, var0, (byte)-64);
         float var4 = (float)Math.acos(var3);
         classUL var5 = classAV.method715((byte)0);
         classUL.method11431(var5, var2, var4, (byte)64);
         var2.method11797(1778910694);
         return var5;
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "dd.as(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)Z")
   @ObfuscatedName("as")
   static boolean method2997(String var0) {
      if (var0 == null) {
         return false;
      } else {
         try {
            new URL(var0);
            return true;
         } catch (MalformedURLException var2) {
            return false;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lez;)V")
   @ObfuscatedName("wh")
   public static void method2994(Scene var0) {
      if (var0 == null) {
         var0.method4455();
      }

      var0.field2062 = -1;
      var0.field2147 = null;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;B)Ljava/lang/String;")
   @ObfuscatedName("ae")
   public static String method2998(String var0, byte var1) {
      try {
         if (var0 != null) {
            if (var1 >= 8) {
               throw new IllegalStateException();
            }

            if (!var0.isEmpty()) {
               if (var1 >= 8) {
                  throw new IllegalStateException();
               }

               if (var0.charAt(0) != '#') {
                  return var0;
               }

               if (var1 >= 8) {
                  throw new IllegalStateException();
               }
            }
         }

         return "";
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "dd.ae(" + ')');
      }
   }
}
