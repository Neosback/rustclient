import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.invoke.ConstantBootstraps;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("bf")
public class classBF {
   @ObfuscatedName("cx")
   static String field397 = "";
   @ToRemove(unused = "true")
   @ObfuscatedName("af")
   static final int field417 = 1;
   @ToRemove(unused = "true")
   @ObfuscatedName("ae")
   static final int field416 = 2;
   @ToRemove(unused = "true")
   @ObfuscatedName("ab")
   static final int field414 = 3;
   @ToRemove(unused = "true")
   @ObfuscatedName("as")
   static final int field421 = 5;
   @ObfuscatedName("ds")
   static String[] field386 = new String[]{"logo_speedrunning", "logo_ugc_world"};
   @ToRemove(unused = "true")
   @ObfuscatedName("aj")
   static final int field418 = 12;
   @ObfuscatedName("dq")
   static String[] field410 = new String[]{"title.jpg"};
   @ToRemove(unused = "true")
   @ObfuscatedName("ar")
   static final int field420 = 38;
   @ToRemove(unused = "true")
   @ObfuscatedName("av")
   static final int field422 = 2;
   @ObfuscatedName("dt")
   static boolean field412 = false;
   @ObfuscatedName("bb")
   static int field385 = 0;
   @ObfuscatedName("be")
   static int field394 = -1410227574;
   @ToRemove(unused = "true")
   @ObfuscatedName("ac")
   static final int field415 = 7;
   @ObfuscatedName("bq")
   static int field387 = -1431935615;
   @ObfuscatedName("by")
   static String field391 = "";
   @ToRemove(unused = "true")
   @ObfuscatedName("bu")
   static final int field425 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("bh")
   static String field426;
   @ObfuscatedName("cr")
   static int field390 = 0;
   @ObfuscatedName("cu")
   static String[] field406 = new String[8];
   @ObfuscatedName("cp")
   static boolean field392 = true;
   @ToRemove(unused = "true")
   @ObfuscatedName("ap")
   static final int field419 = 32;
   @ObfuscatedName("cw")
   static int field384 = 0;
   @ObfuscatedName("cj")
   static String field395 = "";
   @ObfuscatedName("ci")
   static String field396 = "";
   @ObfuscatedName("di")
   static String field403 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"ï¿½$%^&*()-_=+[{]};:'@#~,<.>/?\\| ";
   @ObfuscatedName("ce")
   static String field398 = "";
   @ObfuscatedName("cm")
   static String field411 = "";
   @ObfuscatedName("dm")
   static boolean field400 = false;
   @ObfuscatedName("bv")
   static String field389 = "";
   @ObfuscatedSignature(descriptor = "Lvp;")
   @ObfuscatedName("hu")
   static classVP field427;
   @ObfuscatedName("df")
   static String[] field413 = new String[]{
      "logo",
      "logo_deadman_mode",
      "logo_seasonal_mode",
      "titlebox",
      "titlebutton",
      "titlebutton_large",
      "play_now_text",
      "titlebutton_wide42,1",
      "runes",
      "title_mute",
      "options_radio_buttons,0",
      "options_radio_buttons,2",
      "options_radio_buttons,4",
      "options_radio_buttons,6",
      "sl_back",
      "sl_flags",
      "sl_arrows",
      "sl_stars",
      "sl_button"
   };
   @ObfuscatedName("du")
   static String field404 = "1234567890";
   @ObfuscatedName("cz")
   static int field393 = 1927755610 + 2007709401 * field385;
   @ObfuscatedName("dn")
   static int field399 = -520043415;
   @ObfuscatedName("dv")
   static int field407 = 0;
   @ObfuscatedName("do")
   static int field408 = 0;
   @ObfuscatedName("dg")
   static long field401 = -7971708337557546757L;
   @ObfuscatedName("dl")
   static long field409 = -3992049254271775187L;
   @ToRemove(unused = "true")
   @ObfuscatedName("az")
   static final int field423 = 0;
   @ObfuscatedName("dy")
   static int field402 = 0;
   @ObfuscatedName("bf")
   static int field388 = -945703767;
   @ObfuscatedName("dx")
   static boolean field405 = false;
   @ToRemove(unused = "true")
   @ObfuscatedName("bp")
   static final int field424 = 32;

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcm;)V")
   @ObfuscatedName("cj")
   static void method1047(World var0) {
      if (var0.method2473((byte)-77) != client.field933) {
         client.field933 = var0.method2473((byte)-53);
         classBD.method994(var0.method2473((byte)-25), 2147170986);
      }

      if (-2024190683 * client.field884 != var0.field1158 * -458552905) {
         classVP var1 = classHC.field2765;
         int var2 = var0.field1158 * -458552905;
         if ((var2 & classXE.field6838.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_deadman_mode", "", -281278256);
         } else if ((var2 & classXE.field6849.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_seasonal_mode", "", -635545411);
         } else if ((var2 & classXE.field6834.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_speedrunning", "", 607941834);
         } else if ((var2 & classXE.field6837.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_ugc_world", "", -488759571);
         } else {
            AABB.field2370 = classSU.method10729(var1, "logo", "", -1251419635);
         }
      }

      WidgetConfigNode.field4137 = var0.field1167;
      client.field828 = var0.field1159 * 894790041;
      client.field884 = var0.field1158 * -635970901;
      classGV.field2714 = var0.field1166;
      classRJ.field5679 = 721437403 * (0 == 2064080735 * client.field941 ? 'ꩊ' : var0.field1159 * 851246551 + 40000);
      classMB.field4476 = 309769881 * (0 == client.field941 * 2064080735 ? 443 : 50000 + var0.field1159 * 851246551);
      classAD.field141 = 1527064229 * classRJ.field5679;
   }

   static {
      new DecimalFormat("##0.00");
      new classTO();
      method1067("");
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;)I")
   @ObfuscatedName("bc")
   static int method1002(IndexDataBase var0) {
      int var1 = field413.length + field410.length;
      String[] var2 = field386;

      for (int var3 = 0; var3 < var2.length; var3++) {
         String var4 = var2[var3];
         if (IndexDataBase.method11918(var0, var4, 1278857994) != -1) {
            var1++;
         }
      }

      return var1;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Laam;ZI)V")
   @ObfuscatedName("bp")
   static void method1003(IndexDataBase var0, IndexDataBase var1, classAAM var2, boolean var3, int var4) {
      if (classPA.field5207) {
         if (var4 == 4) {
            classAC.method321(4, 2115423794);
         }
      } else {
         if (0 == var4) {
            classIV.method6363(var3, (byte)-66);
         } else {
            classAC.method321(var4, 2037796446);
         }

         classYW.method13722();
         byte[] var5 = IndexDataBase.method11925(var0, "title.jpg", "", 1857645463);
         classNI.field4671 = classIT.method6356(var5, 1445159355);
         classFR.field2389 = classNI.field4671.method13517();
         int var6 = 1202735713 * client.field884;
         if ((var6 & classXE.field6838.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_deadman_mode", "", 387721232);
         } else if ((var6 & classXE.field6849.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_seasonal_mode", "", 123681237);
         } else if ((var6 & classXE.field6834.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_speedrunning", "", 148185783);
         } else if ((var6 & classXE.field6837.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_ugc_world", "", -242359272);
         } else {
            AABB.field2370 = classSU.method10729(var1, "logo", "", 692140644);
         }

         classJB.field3118 = classSU.method10729(var1, "titlebox", "", -646323230);
         classKA.field3512 = classSU.method10729(var1, "titlebutton", "", 297778815);
         classGQ.field2653 = classSU.method10729(var1, "titlebutton_large", "", -2122673704);
         classQH.field5542 = classSU.method10729(var1, "play_now_text", "", 520685273);
         classSU.method10729(var1, "titlebutton_wide42,1", "", -89716226);
         classBM.field514 = classOT.method8767(var1, "runes", "", (byte)70);
         MessageNode.field733 = classOT.method8767(var1, "title_mute", "", (byte)74);
         classCK.field778 = classSU.method10729(var1, "options_radio_buttons,0", "", -100564125);
         classHC.field2766 = classSU.method10729(var1, "options_radio_buttons,4", "", -695250291);
         classEK.field1777 = classSU.method10729(var1, "options_radio_buttons,2", "", -1002252120);
         classQN.field5571 = classSU.method10729(var1, "options_radio_buttons,6", "", -1928335979);
         classXR.field6923 = classCK.field778.field7119 * -1666056959;
         classKP.field3983 = -682922689 * classCK.field778.field7117;
         classML.field4529 = new classCS(classBM.field514, var2.field62);
         if (var3) {
            field389 = "";
            method1067("");
            field406 = new String[8];
            field390 = 0;
         }

         classEV.field1942 = 0;
         classQP.field5584 = "";
         field392 = true;
         field412 = false;
         classUR.method11585((byte)0);
         Actor.method3134(-1612757241);
         classWU.method12698((byte)84).method11959(false, 116730183);
         classPA.field5207 = true;
         field385 = -2138661170 * ((classSA.field5895 * 773246731 - -2058373054) / 2);
         field393 = 1927755610 + -2005030050 * field385;
         Ignore.field5836 = 273549476 + 180132130 * field393;
         classNI.field4671.method13552(field385 * -329898575, 0);
         classFR.field2389.method13552(382 + 1050435522 * field385, 0);
         IndexedSprite.method13853(AABB.field2370, -329898575 * field385 + -641358656 - AABB.field2370.field7119 / 2, 18);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lva;Lva;Laam;ZI)V")
   @ObfuscatedName("br")
   static void method1004(IndexDataBase var0, IndexDataBase var1, classAAM var2, boolean var3, int var4) {
      if (classPA.field5207) {
         if (var4 == 4) {
            classAC.method321(4, 2086614621);
         }
      } else {
         if (0 == var4) {
            classIV.method6363(var3, (byte)32);
         } else {
            classAC.method321(var4, 2115175663);
         }

         classYW.method13722();
         byte[] var5 = IndexDataBase.method11925(var0, "title.jpg", "", 1857645463);
         classNI.field4671 = classIT.method6356(var5, 1406013956);
         classFR.field2389 = classNI.field4671.method13517();
         int var6 = -2024190683 * client.field884;
         if ((var6 & classXE.field6838.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_deadman_mode", "", -443951802);
         } else if ((var6 & classXE.field6849.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_seasonal_mode", "", -559264894);
         } else if ((var6 & classXE.field6834.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_speedrunning", "", 533303044);
         } else if ((var6 & classXE.field6837.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_ugc_world", "", 40527408);
         } else {
            AABB.field2370 = classSU.method10729(var1, "logo", "", -1585545407);
         }

         classJB.field3118 = classSU.method10729(var1, "titlebox", "", -1206512656);
         classKA.field3512 = classSU.method10729(var1, "titlebutton", "", -1812469939);
         classGQ.field2653 = classSU.method10729(var1, "titlebutton_large", "", -2043665658);
         classQH.field5542 = classSU.method10729(var1, "play_now_text", "", -770931823);
         classSU.method10729(var1, "titlebutton_wide42,1", "", -758968968);
         classBM.field514 = classOT.method8767(var1, "runes", "", (byte)38);
         MessageNode.field733 = classOT.method8767(var1, "title_mute", "", (byte)67);
         classCK.field778 = classSU.method10729(var1, "options_radio_buttons,0", "", -625147784);
         classHC.field2766 = classSU.method10729(var1, "options_radio_buttons,4", "", 586092587);
         classEK.field1777 = classSU.method10729(var1, "options_radio_buttons,2", "", 441232737);
         classQN.field5571 = classSU.method10729(var1, "options_radio_buttons,6", "", -888775260);
         classXR.field6923 = classCK.field778.field7119 * -1666056959;
         classKP.field3983 = -682922689 * classCK.field778.field7117;
         classML.field4529 = new classCS(classBM.field514, var2.field62);
         if (var3) {
            field389 = "";
            method1067("");
            field406 = new String[8];
            field390 = 0;
         }

         classEV.field1942 = 0;
         classQP.field5584 = "";
         field392 = true;
         field412 = false;
         classUR.method11585((byte)0);
         Actor.method3134(-1612757241);
         classWU.method12698((byte)122).method11959(false, 116730183);
         classPA.field5207 = true;
         field385 = 358417233 * ((classSA.field5895 * 773246731 - 765) / 2);
         field393 = 1927755610 + 2007709401 * field385;
         Ignore.field5836 = 273549476 + -1415132635 * field393;
         classNI.field4671.method13552(field385 * -329898575, 0);
         classFR.field2389.method13552(382 + -329898575 * field385, 0);
         IndexedSprite.method13853(AABB.field2370, -329898575 * field385 + 382 - AABB.field2370.field7119 / 2, 18);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ZZ)Lyz;")
   @ObfuscatedName("bm")
   static IndexedSprite method1005(boolean var0, boolean var1) {
      return var0 ? (var1 ? classQN.field5571 : classEK.field1777) : (var1 ? classHC.field2766 : classCK.field778);
   }

   @ObfuscatedSignature(descriptor = "()[Ljava/lang/String;")
   @ObfuscatedName("hc")
   private static String[] method1064() {
      return new String[1];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ZZ)Lyz;")
   @ObfuscatedName("bt")
   static IndexedSprite method1006(boolean var0, boolean var1) {
      return var0 ? (var1 ? classQN.field5571 : classEK.field1777) : (var1 ? classHC.field2766 : classCK.field778);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ZZ)Lyz;")
   @ObfuscatedName("bg")
   static IndexedSprite method1007(boolean var0, boolean var1) {
      return var0 ? (var1 ? classQN.field5571 : classEK.field1777) : (var1 ? classHC.field2766 : classCK.field778);
   }

   classBF() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("cc")
   static void method1040(String var0, String var1, String var2) {
      field396 = var0;
      field397 = var1;
      field398 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bj")
   static String method1008() {
      return classAB.field122.method2722((short)21364) ? classSL.method10515(field389, (short)21809) : field389;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Lzv;Lzv;)V")
   @ObfuscatedName("bu")
   static void method1010(GameEngine var0, classZV var1, classZV var2) {
      if (field412) {
         classDP.method3534(var0, -644062932);
      } else {
         if ((1229235359 * classTZ.field6259 == 1 || !classON.field4993 && 1229235359 * classTZ.field6259 == 4)
            && classTZ.field6264 * 1422993977 >= field385 * -329898575 + 765 - 50
            && classTZ.field6255 * 1243590197 >= 453
            && 1422993977 * classTZ.field6264 < 765 + -329898575 * field385
            && classTZ.field6255 * 1243590197 < 503) {
            classAB.field122.method2731(!classAB.field122.method2729((byte)36), (byte)16);
            classUR.method11585((byte)0);
            Actor.method3134(-1612757241);
         }

         if (-44590225 * client.field829 != 5) {
            if (-1L == field401 * -6105922944827093555L) {
               field401 = (classDD.method2988(16711680) + 1000L) * 7971708337557546757L;
            }

            long var3 = classDD.method2988(16711680);
            if (classOL.method8545(-1571893942) && -1L == field409 * -5269972525589787045L) {
               field409 = 3992049254271775187L * var3;
               if (-5269972525589787045L * field409 > -6105922944827093555L * field401) {
                  field401 = field409 * -3963514021849324345L;
               }
            }

            if (10 == -44590225 * client.field829 || client.field829 * -44590225 == 11) {
               if (classLU.field4230 == classXO.field6890) {
                  if (classTZ.field6259 * 1229235359 == 1 || !classON.field4993 && classTZ.field6259 * 1229235359 == 4) {
                     int var5 = field385 * -329898575 + 5;
                     short var6 = 463;
                     byte var7 = 100;
                     byte var8 = 35;
                     if (classTZ.field6264 * 1422993977 >= var5
                        && classTZ.field6264 * 1422993977 <= var7 + var5
                        && 1243590197 * classTZ.field6255 >= var6
                        && classTZ.field6255 * 1243590197 <= var6 + var8) {
                        if (classKB.method6484(1725920694)) {
                           field412 = true;
                           field407 = 0;
                           field408 = 0;
                        }

                        return;
                     }
                  }

                  if (classIS.field3052 != null && classKB.method6484(-546066146)) {
                     field412 = true;
                     field407 = 0;
                     field408 = 0;
                  }
               }

               int var23 = 1229235359 * classTZ.field6259;
               int var24 = classTZ.field6264 * 1422993977;
               int var25 = 1243590197 * classTZ.field6255;
               if (0 == var23) {
                  var24 = -38255113 * classTZ.field6253;
                  var25 = classTZ.field6262 * -2144333897;
               }

               if (!classON.field4993 && var23 == 4) {
                  var23 = 1;
               }

               classFA var26 = client.field932;
               classFA var9 = var26;
               if (-47366135 * field384 == 0) {
                  boolean var62 = false;

                  while (var9.method4555((short)8755)) {
                     if (84 == var9.field2174 * 1363213817) {
                        var62 = true;
                     }
                  }

                  int var93 = 1429288549 * Ignore.field5836 - 80;
                  short var108 = 291;
                  if (1 == var23 && var24 >= var93 - 75 && var24 <= var93 + 75 && var25 >= var108 - 20 && var25 <= 20 + var108) {
                     classUF.method11359(classLZ.method7470("secure", true, 461270152) + "m=account-creation/g=oldscape/create_account_funnel.ws", (byte)27);
                  }

                  var93 = Ignore.field5836 * 1429288549 + 80;
                  if (var23 == 1 && var24 >= var93 - 75 && var24 <= 75 + var93 && var25 >= var108 - 20 && var25 <= 20 + var108 || var62) {
                     if (classNU.method8185(-1192583193)) {
                        if (client.field833 && field389 != null && !field389.isEmpty()) {
                           field402 = -878854915;
                        } else {
                           field402 = 0;
                        }
                     } else {
                        classGG.method5457(1602566351);
                     }
                  }
               } else if (-47366135 * field384 == 1) {
                  boolean var61 = classOE.field4841.method2138(1985617997)
                     || client.method2142(classOE.field4841, (byte)110)
                     || classOE.field4841.method2140(-1058609207);

                  while (var9.method4555((short)-11244)) {
                     if (var9.field2174 * 1363213817 == 84) {
                        if (var61) {
                           WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-109);
                           client.field899 = AccountType.field92;
                           classXQ.method12934(false, 402128428);
                           classJB.method6386(20, -1192966987);
                        } else {
                           classGG.method5457(1822178355);
                        }

                        return;
                     }

                     if (13 == var9.field2174 * 1363213817) {
                        classIV.method6363(true, (byte)103);
                        return;
                     }
                  }

                  int var91 = 1429288549 * Ignore.field5836 - 80;
                  short var107 = 321;
                  if (var23 == 1 && var24 >= var91 - 75 && var24 <= var91 + 75 && var25 >= var107 - 20 && var25 <= 20 + var107) {
                     if (var61) {
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-119);
                        client.field899 = AccountType.field92;
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     } else {
                        classGG.method5457(14017380);
                     }

                     return;
                  }

                  var91 = 80 + Ignore.field5836 * 1429288549;
                  if (1 == var23 && var24 >= var91 - 75 && var24 <= 75 + var91 && var25 >= var107 - 20 && var25 <= 20 + var107) {
                     classIV.method6363(true, (byte)118);
                     return;
                  }
               } else if (field384 * -47366135 == 2) {
                  int var56 = 201;
                  var56 += 52;
                  if (1 == var23 && var25 >= var56 - 12 && var25 < 2 + var56) {
                     field402 = 0;
                  }

                  var56 += 15;
                  if (1 == var23 && var25 >= var56 - 12 && var25 < var56 + 2) {
                     field402 = -878854915;
                  }

                  var56 += 15;
                  short var60 = 361;
                  if (classWR.field6768 != null) {
                     int var86 = classWR.field6768.field6368 * 518523273 / 2;
                     if (1 == var23
                        && var24 >= -1357616065 * classWR.field6768.field6366 - var86
                        && var24 <= classWR.field6768.field6366 * -1357616065 + var86
                        && var25 >= var60 - 15
                        && var25 < var60) {
                        switch (-323997799 * field388) {
                           case 1:
                              classUF.method11359(classKK.field3924, (byte)-72);
                              return;
                           case 2:
                              classUF.method11359("https://support.runescape.com/hc/en-gb", (byte)-21);
                        }
                     }
                  }

                  int var87 = 1429288549 * Ignore.field5836 - 80;
                  short var104 = 321;
                  if (1 == var23 && var24 >= var87 - 75 && var24 <= 75 + var87 && var25 >= var104 - 20 && var25 <= 20 + var104) {
                     field389 = field389.trim();
                     if (field389.isEmpty()) {
                        WorldEntity.method10671(classKK.field3656, classKK.field3726, classKK.field3914, (byte)-97);
                     } else if ((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                        WorldEntity.method10671(classKK.field3728, classKK.field3642, classKK.field3730, (byte)-44);
                     } else {
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-63);
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     }

                     return;
                  }

                  var87 = 80 + 180 + -511275367 * field393;
                  if (var23 == 1 && var24 >= var87 - 75 && var24 <= 75 + var87 && var25 >= var104 - 20 && var25 <= var104 + 20) {
                     classAC.method321(0, 2119970406);
                     field389 = "";
                     method1067("");
                     classEV.field1942 = 0;
                     classQP.field5584 = "";
                     field392 = true;
                  }

                  var87 = 1429288549 * Ignore.field5836 + -117;
                  var104 = 277;
                  field400 = var24 >= var87
                     && var24 < var87 + 1381434113 * classXR.field6923
                     && var25 >= var104
                     && var25 < classKP.field3983 * -580844865 + var104;
                  if (1 == var23 && field400) {
                     client.field833 = !client.field833;
                     if (!client.field833 && classAB.field122.method2782((byte)8) != null) {
                        classAB.field122.method2786(null, -1127326555);
                     }
                  }

                  var87 = 24 + 1429288549 * Ignore.field5836;
                  var104 = 277;
                  field405 = var24 >= var87
                     && var24 < classXR.field6923 * 1381434113 + var87
                     && var25 >= var104
                     && var25 < var104 + -580844865 * classKP.field3983;
                  if (var23 == 1 && field405) {
                     classAB.field122.method2723(!classAB.field122.method2722((short)10040), -2057509504);
                     if (!classAB.field122.method2722((short)-1756)) {
                        field389 = "";
                        classAB.field122.method2786(null, 1609800813);
                        if (client.field833 && null != field389 && !field389.isEmpty()) {
                           field402 = -878854915;
                        } else {
                           field402 = 0;
                        }
                     }
                  }

                  label1101:
                  while (var9.method4555((short)1339)) {
                     if (var9.field2174 * 1363213817 == 13) {
                        classAC.method321(0, 2007357290);
                        field389 = "";
                        method1067("");
                        classEV.field1942 = 0;
                        classQP.field5584 = "";
                        field392 = true;
                     } else if (-1187692459 * field402 == 0) {
                        classOG.method8467(var9.field2173, (byte)-38);
                        if (var9.field2174 * 1363213817 == 85 && !field389.isEmpty()) {
                           field389 = field389.substring(0, field389.length() - 1);
                        }

                        if (1363213817 * var9.field2174 == 84 || 1363213817 * var9.field2174 == 80) {
                           field402 = -878854915;
                        }

                        char var119 = var9.field2173;
                        boolean var113 = field403.indexOf(var119) != -1;
                        if (var113 && field389.length() < 320) {
                           field389 = field389 + var9.field2173;
                        }
                     } else if (field402 * -1187692459 == 1) {
                        if (85 == var9.field2174 * 1363213817 && !(String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                           method1067(
                              (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0]
                                 .substring(0, (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length() - 1)
                           );
                        } else if (1363213817 * var9.field2174 == 84 || 80 == 1363213817 * var9.field2174) {
                           field402 = 0;
                           if (1363213817 * var9.field2174 == 84) {
                              field389 = field389.trim();
                              if (field389.isEmpty()) {
                                 WorldEntity.method10671(classKK.field3656, classKK.field3726, classKK.field3914, (byte)-16);
                              } else if ((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                                 WorldEntity.method10671(classKK.field3728, classKK.field3642, classKK.field3730, (byte)-33);
                              } else {
                                 WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-92);
                                 classXQ.method12934(false, 402128428);
                                 classJB.method6386(20, -1192966987);
                              }

                              return;
                           }
                        }

                        if ((var9.method4563(82, -175454537) || var9.method4563(87, 1916482088)) && 67 == 1363213817 * var9.field2174) {
                           Clipboard var115 = Toolkit.getDefaultToolkit().getSystemClipboard();
                           Transferable var121 = var115.getContents(classOE.field4841);
                           int var123 = 20 - (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length();
                           if (var123 > 0) {
                              try {
                                 String var125 = (String)var121.getTransferData(DataFlavor.stringFlavor);
                                 int var17 = Math.min(var123, var125.length());
                                 int var18 = 0;

                                 while (true) {
                                    if (var18 >= var17) {
                                       method1067((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] + var125.substring(0, var17));
                                       continue label1101;
                                    }

                                    if (!classIB.method6177(var125.charAt(var18), 492373917)) {
                                       break;
                                    }

                                    char var20 = var125.charAt(var18);
                                    boolean var19 = field403.indexOf(var20) != -1;
                                    if (!var19) {
                                       break;
                                    }

                                    var18++;
                                 }

                                 classAC.method321(3, 2020226715);
                                 return;
                              } catch (UnsupportedFlavorException var21) {
                              } catch (IOException var22) {
                              }
                           }
                        } else if (classIB.method6177(var9.field2173, 1984879040)) {
                           char var120 = var9.field2173;
                           boolean var114 = field403.indexOf(var120) != -1;
                           if (var114 && (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length() < 20) {
                              method1067((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] + var9.field2173);
                           }
                        }
                     }
                  }
               } else if (3 == -47366135 * field384) {
                  int var10 = 180 + -511275367 * field393;
                  short var11 = 241;
                  classUQ var12 = var1.method46(25, classKK.field3857.length() - 34, classKK.field3857, var10, var11);
                  if (var23 == 1 && var12.method11536(var24, var25, -594779443)) {
                     classUF.method11359(classKK.field3923, (byte)47);
                  }

                  var10 = 180 + field393 * -511275367;
                  var11 = 276;
                  if (1 == var23 && var24 >= var10 - 75 && var24 <= var10 + 75 && var25 >= var11 - 20 && var25 <= var11 + 20) {
                     classIV.method6363(false, (byte)-24);
                  }

                  var10 = 180 + -511275367 * field393;
                  var11 = 326;
                  if (1 == var23 && var24 >= var10 - 75 && var24 <= 75 + var10 && var25 >= var11 - 20 && var25 <= var11 + 20) {
                     classUF.method11359(classKK.field3924, (byte)-101);
                     return;
                  }
               } else if (4 == -47366135 * field384) {
                  int var29 = 180 + -511275367 * field393 - 80;
                  short var65 = 321;
                  if (var23 == 1 && var24 >= var29 - 75 && var24 <= var29 + 75 && var25 >= var65 - 20 && var25 <= var65 + 20) {
                     classQP.field5584.trim();
                     if (classQP.field5584.length() != 6) {
                        WorldEntity.method10671(classKK.field3644, classKK.field3848, classKK.field3743, (byte)-66);
                     } else {
                        classEV.field1942 = Integer.parseInt(classQP.field5584) * 400348643;
                        classQP.field5584 = "";
                        classXQ.method12934(true, 402128428);
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-62);
                        classJB.method6386(20, -1192966987);
                     }

                     return;
                  }

                  if (var23 == 1 && var24 >= 180 + field393 * -511275367 - 9 && var24 <= 180 + -511275367 * field393 + 130 && var25 >= 263 && var25 <= 296) {
                     field392 = !field392;
                  }

                  if (1 == var23 && var24 >= 180 + field393 * -511275367 - 34 && var24 <= 34 + -511275367 * field393 + 180 && var25 >= 351 && var25 <= 363) {
                     classUF.method11359(classKK.field3924, (byte)-17);
                  }

                  var29 = 80 + -511275367 * field393 + 180;
                  if (var23 == 1 && var24 >= var29 - 75 && var24 <= 75 + var29 && var25 >= var65 - 20 && var25 <= 20 + var65) {
                     classAC.method321(0, 2135587509);
                     field389 = "";
                     method1067("");
                     classEV.field1942 = 0;
                     classQP.field5584 = "";
                  }

                  while (var9.method4555((short)20797)) {
                     boolean var95 = false;

                     for (int var13 = 0; var13 < field404.length(); var13++) {
                        if (var9.field2173 == field404.charAt(var13)) {
                           var95 = true;
                           break;
                        }
                     }

                     if (var9.field2174 * 1363213817 == 13) {
                        classAC.method321(0, 2128475217);
                        field389 = "";
                        method1067("");
                        classEV.field1942 = 0;
                        classQP.field5584 = "";
                     } else {
                        if (1363213817 * var9.field2174 == 85 && !classQP.field5584.isEmpty()) {
                           classQP.field5584 = classQP.field5584.substring(0, classQP.field5584.length() - 1);
                        }

                        if (1363213817 * var9.field2174 == 84) {
                           classQP.field5584.trim();
                           if (classQP.field5584.length() != 6) {
                              WorldEntity.method10671(classKK.field3644, classKK.field3848, classKK.field3743, (byte)-110);
                           } else {
                              classEV.field1942 = Integer.parseInt(classQP.field5584) * 400348643;
                              classQP.field5584 = "";
                              classXQ.method12934(true, 402128428);
                              WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-111);
                              classJB.method6386(20, -1192966987);
                           }

                           return;
                        }

                        if (var95 && classQP.field5584.length() < 6) {
                           classQP.field5584 = classQP.field5584 + var9.field2173;
                        }
                     }
                  }
               } else if (-47366135 * field384 == 5) {
                  int var31 = -511275367 * field393 + 180 - 80;
                  short var66 = 321;
                  if (1 == var23 && var24 >= var31 - 75 && var24 <= 75 + var31 && var25 >= var66 - 20 && var25 <= 20 + var66) {
                     classHC.method6023(-486975020);
                     return;
                  }

                  var31 = 80 + field393 * -511275367 + 180;
                  if (1 == var23 && var24 >= var31 - 75 && var24 <= var31 + 75 && var25 >= var66 - 20 && var25 <= var66 + 20) {
                     classBJ.method1121(true, -347989319);
                  }

                  short var96 = 361;
                  if (null != classLB.field4087) {
                     int var109 = 518523273 * classLB.field4087.field6368 / 2;
                     if (var23 == 1
                        && var24 >= classLB.field4087.field6366 * -1357616065 - var109
                        && var24 <= classLB.field4087.field6366 * -1357616065 + var109
                        && var25 >= var96 - 15
                        && var25 < var96) {
                        classUF.method11359(classLZ.method7470("secure", true, -115128950) + "m=weblogin/g=oldscape/cant_log_in", (byte)-5);
                     }
                  }

                  while (var9.method4555((short)-16094)) {
                     boolean var110 = false;

                     for (int var14 = 0; var14 < field403.length(); var14++) {
                        if (var9.field2173 == field403.charAt(var14)) {
                           var110 = true;
                           break;
                        }
                     }

                     if (1363213817 * var9.field2174 == 13) {
                        classBJ.method1121(true, -347989319);
                     } else {
                        if (85 == var9.field2174 * 1363213817 && !field389.isEmpty()) {
                           field389 = field389.substring(0, field389.length() - 1);
                        }

                        if (84 == var9.field2174 * 1363213817) {
                           classHC.method6023(-486975020);
                           return;
                        }

                        if (var110 && field389.length() < 320) {
                           field389 = field389 + var9.field2173;
                        }
                     }
                  }
               } else if (6 != field384 * -47366135) {
                  if (-47366135 * field384 == 7) {
                     if (client.field1133 && !client.field891) {
                        int var36 = 1429288549 * Ignore.field5836 - 150;
                        int var68 = 25 + var36 + 240 + 40;
                        short var97 = 231;
                        int var111 = 40 + var97;
                        if (var23 == 1 && var24 >= var36 && var24 <= var68 && var25 >= var97 && var25 <= var111) {
                           field390 = classBJ.method1120(var36, var24, (byte)1) * 523390187;
                        }

                        int var116 = field393 * -511275367 + 180 - 80;
                        short var15 = 321;
                        if (1 == var23 && var24 >= var116 - 75 && var24 <= 75 + var116 && var25 >= var15 - 20 && var25 <= 20 + var15) {
                           boolean var16 = classCN.method2545((byte)39);
                           if (var16) {
                              classJB.method6386(50, -1192966987);
                              return;
                           }
                        }

                        var116 = 180 + -511275367 * field393 + 80;
                        if (var23 == 1 && var24 >= var116 - 75 && var24 <= var116 + 75 && var25 >= var15 - 20 && var25 <= var15 + 20) {
                           field406 = new String[8];
                           classBJ.method1121(true, -347989319);
                        }

                        while (var9.method4555((short)-12675)) {
                           if (101 == var9.field2174 * 1363213817) {
                              field406[-594543677 * field390] = null;
                           }

                           if (85 == var9.field2174 * 1363213817) {
                              if (field406[field390 * -594543677] == null && field390 * -594543677 > 0) {
                                 field390 -= 523390187;
                              }

                              field406[field390 * -594543677] = null;
                           }

                           if (var9.field2173 >= '0' && var9.field2173 <= '9') {
                              field406[-594543677 * field390] = "" + var9.field2173;
                              if (-594543677 * field390 < 7) {
                                 field390 += 523390187;
                              }
                           }

                           if (var9.field2174 * 1363213817 == 84) {
                              boolean var124 = classCN.method2545((byte)99);
                              if (var124) {
                                 classJB.method6386(50, -1192966987);
                              }

                              return;
                           }
                        }
                     } else {
                        int var34 = 180 + field393 * -511275367 - 80;
                        short var67 = 321;
                        if (1 == var23 && var24 >= var34 - 75 && var24 <= 75 + var34 && var25 >= var67 - 20 && var25 <= 20 + var67) {
                           classUF.method11359(classLZ.method7470("secure", true, 186518940) + "m=dob/set_dob.ws", (byte)12);
                           WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-5);
                           classAC.method321(6, 2071935358);
                           return;
                        }

                        var34 = 80 + 180 + field393 * -511275367;
                        if (1 == var23 && var24 >= var34 - 75 && var24 <= var34 + 75 && var25 >= var67 - 20 && var25 <= 20 + var67) {
                           classBJ.method1121(true, -347989319);
                        }
                     }
                  } else if (8 == field384 * -47366135) {
                     int var37 = field393 * -511275367 + 180 - 80;
                     short var69 = 321;
                     if (1 == var23 && var24 >= var37 - 75 && var24 <= 75 + var37 && var25 >= var69 - 20 && var25 <= 20 + var69) {
                        classUF.method11359("https://legal.jagex.com/docs/policies/privacy", (byte)-42);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-76);
                        classAC.method321(6, 2120096749);
                        return;
                     }

                     var37 = -511275367 * field393 + 180 + 80;
                     if (var23 == 1 && var24 >= var37 - 75 && var24 <= var37 + 75 && var25 >= var69 - 20 && var25 <= var69 + 20) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (field384 * -47366135 == 9) {
                     int var39 = -511275367 * field393 + 180;
                     short var70 = 311;
                     boolean var98 = 1 == var23 && var24 >= var39 - 75 && var24 <= 75 + var39 && var25 >= var70 - 20 && var25 <= 20 + var70;

                     while (var9.method4555((short)23380)) {
                        if (84 == var9.field2174 * 1363213817 || 13 == 1363213817 * var9.field2174) {
                           var98 = true;
                        }
                     }

                     if (var98) {
                        classIV.method6363(false, (byte)80);
                     }
                  } else if (field384 * -47366135 == 10) {
                     int var40 = field393 * -511275367 + 180;
                     short var71 = 209;
                     boolean var99 = 1 == var23 && var24 >= var40 - 109 && var24 <= 109 + var40 && var25 >= var71 && var25 <= var71 + 68;

                     while (var9.method4555((short)-11891)) {
                        if (1363213817 * var9.field2174 == 84) {
                           var99 = true;
                        }
                     }

                     if (var99) {
                        if (classNU.method8185(-1313003698)) {
                           return;
                        }

                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-91);
                        client.field899 = AccountType.field92;
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     }
                  } else if (12 == -47366135 * field384) {
                     int var41 = Ignore.field5836 * 1429288549;
                     int var72 = 233;
                     classUQ var100 = var2.method46(0, 30, classKK.field3898, var41, var72);
                     classUQ var112 = var2.method46(32, 32, classKK.field3898, var41, var72);
                     classUQ var118 = var2.method46(70, 34, classKK.field3898, var41, var72);
                     var72 += 17;
                     classUQ var122 = var2.method46(0, 34, classKK.field3899, var41, var72);
                     if (var23 == 1) {
                        if (var100.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/terms", (byte)9);
                        } else if (var112.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/policies/privacy", (byte)79);
                        } else if (var118.method11536(var24, var25, -594779443) || var122.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/terms/eula", (byte)-46);
                        }
                     }

                     var41 = Ignore.field5836 * 1429288549 - 80;
                     short var74 = 311;
                     if (1 == var23 && var24 >= var41 - 75 && var24 <= 75 + var41 && var25 >= var74 - 20 && var25 <= var74 + 20) {
                        classYY.method13834(84314098);
                        classIV.method6363(true, (byte)36);
                     }

                     var41 = 80 + 1429288549 * Ignore.field5836;
                     if (var23 == 1 && var24 >= var41 - 75 && var24 <= 75 + var41 && var25 >= var74 - 20 && var25 <= 20 + var74) {
                        field384 = 1641412837;
                     }
                  } else if (13 == -47366135 * field384) {
                     int var44 = Ignore.field5836 * 1429288549;
                     short var75 = 321;
                     if (var23 == 1 && var24 >= var44 - 75 && var24 <= 75 + var44 && var25 >= var75 - 20 && var25 <= var75 + 20) {
                        classIV.method6363(true, (byte)-3);
                     }
                  } else if (14 == field384 * -47366135) {
                     String var45 = "";
                     switch (-20437633 * field387) {
                        case 0:
                           var45 = "https://secure.runescape.com/m=offence-appeal/account-history";
                           break;
                        case 1:
                           var45 = "https://secure.runescape.com/m=accountappeal/passwordrecovery";
                           break;
                        case 2:
                           var45 = "https://support.runescape.com/hc/en-gb/articles/207256855-Settle-an-Unpaid-Balance";
                           break;
                        default:
                           classBJ.method1121(false, -347989319);
                     }

                     int var76 = field393 * -511275367 + 180;
                     short var101 = 276;
                     if (1 == var23 && var24 >= var76 - 75 && var24 <= 75 + var76 && var25 >= var101 - 20 && var25 <= 20 + var101) {
                        classUF.method11359(var45, (byte)60);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-12);
                        classAC.method321(6, 2020460469);
                        return;
                     }

                     var76 = -511275367 * field393 + 180;
                     var101 = 326;
                     if (1 == var23 && var24 >= var76 - 75 && var24 <= 75 + var76 && var25 >= var101 - 20 && var25 <= var101 + 20) {
                        classBJ.method1121(false, -347989319);
                     }
                  } else if (-47366135 * field384 == 24) {
                     int var46 = -511275367 * field393 + 180;
                     short var78 = 301;
                     if (1 == var23 && var24 >= var46 - 75 && var24 <= 75 + var46 && var25 >= var78 - 20 && var25 <= 20 + var78) {
                        classIV.method6363(false, (byte)-23);
                     }
                  } else if (32 == field384 * -47366135) {
                     int var47 = 180 + field393 * -511275367 - 80;
                     short var79 = 321;
                     if (var23 == 1 && var24 >= var47 - 75 && var24 <= 75 + var47 && var25 >= var79 - 20 && var25 <= 20 + var79) {
                        classUF.method11359(classLZ.method7470("secure", true, -217420635) + "m=dob/set_dob.ws", (byte)68);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-97);
                        classAC.method321(6, 2002810577);
                        return;
                     }

                     var47 = 180 + -511275367 * field393 + 80;
                     if (1 == var23 && var24 >= var47 - 75 && var24 <= 75 + var47 && var25 >= var79 - 20 && var25 <= 20 + var79) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (33 == -47366135 * field384) {
                     int var49 = field393 * -511275367 + 180;
                     short var80 = 276;
                     if (1 == var23 && var24 >= var49 - 75 && var24 <= 75 + var49 && var25 >= var80 - 20 && var25 <= var80 + 20) {
                        classUF.method11359(classKK.field3923, (byte)6);
                     }

                     var49 = -511275367 * field393 + 180;
                     var80 = 326;
                     if (1 == var23 && var24 >= var49 - 75 && var24 <= 75 + var49 && var25 >= var80 - 20 && var25 <= 20 + var80) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (34 == field384 * -47366135) {
                     int var51 = -511275367 * field393 + 180;
                     short var82 = 276;
                     if (1 == var23 && var24 >= var51 - 75 && var24 <= var51 + 75 && var25 >= var82 - 20 && var25 <= var82 + 20) {
                        if (classKB.method6484(-612098240)) {
                           field412 = true;
                           field407 = 0;
                           field408 = 0;
                        }

                        return;
                     }

                     if (null != classIS.field3052 && classKB.method6484(1313353799)) {
                        field412 = true;
                        field407 = 0;
                        field408 = 0;
                     }

                     var51 = 180 + field393 * -511275367;
                     var82 = 326;
                     if (var23 == 1 && var24 >= var51 - 75 && var24 <= 75 + var51 && var25 >= var82 - 20 && var25 <= 20 + var82) {
                        classBJ.method1121(false, -347989319);
                     }
                  } else if (38 == -47366135 * field384) {
                     int var53 = Ignore.field5836 * 1429288549;
                     short var84 = 281;
                     classUQ var103 = var1.method47(classKK.field3906, var53, var84);
                     if (1 == var23 && var103.method11536(var24, var25, -594779443)) {
                        classUF.method11359(classKK.field3907, (byte)46);
                        return;
                     }

                     classUQ.method11530(var103, (byte)-67);
                     var53 = Ignore.field5836 * 1429288549 - 80;
                     var84 = 321;
                     if (var23 == 1 && var24 >= var53 - 75 && var24 <= 75 + var53 && var25 >= var84 - 20 && var25 <= 20 + var84) {
                        classUF.method11359(classKK.field3923, (byte)12);
                        return;
                     }

                     var53 = 80 + 1429288549 * Ignore.field5836;
                     if (var23 == 1 && var24 >= var53 - 75 && var24 <= var53 + 75 && var25 >= var84 - 20 && var25 <= var84 + 20) {
                        classBJ.method1121(true, -347989319);
                        return;
                     }

                     while (var9.method4555((short)-19592)) {
                        if (var9.field2174 * 1363213817 == 84) {
                           classBJ.method1121(true, -347989319);
                           return;
                        }

                        if (var9.field2174 * 1363213817 == 13) {
                           classIV.method6363(true, (byte)-97);
                           return;
                        }
                     }
                  }
               } else {
                  while (var9.method4555((short)11201)) {
                     if (var9.field2174 * 1363213817 == 84 || 13 == 1363213817 * var9.field2174) {
                        classBJ.method1121(true, -347989319);
                     }
                  }

                  short var33 = 321;
                  if (1 == var23 && var25 >= var33 - 20 && var25 <= 20 + var33) {
                     classBJ.method1121(true, -347989319);
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;)V")
   @ObfuscatedName("db")
   static void method1063(int var0, String var1) {
      field391 = var1;
      field394 = -570519487 * var0;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("sd")
   static String method1065() {
      return (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0];
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dd")
   static void method1054() {
      classAC.method321(24, 2134236033);
      WorldEntity.method10671(classKK.field3842, classKK.field3843, classKK.field3801, (byte)-12);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bb")
   static boolean method1013() {
      Date var0;
      try {
         SimpleDateFormat var2 = new SimpleDateFormat("ddMMyyyyHH", Locale.ENGLISH);
         var2.setLenient(false);
         StringBuilder var3 = new StringBuilder();
         String[] var4 = field406;
         int var5 = 0;

         Date var1;
         while (true) {
            if (var5 >= var4.length) {
               var3.append("12");
               var1 = var2.parse(var3.toString());
               break;
            }

            String var6 = var4[var5];
            if (null == var6) {
               classAB.method301("Date not valid.", "Please ensure all characters are populated.", "", 577955118);
               var1 = null;
               break;
            }

            var3.append(var6);
            var5++;
         }

         var0 = var1;
      } catch (ParseException var7) {
         classAB.method301("Date not valid.", "Please ensure date follows the format", "DD/MM/YYYY and is after 01/01/1900", 1374987089);
         return false;
      }

      if (null == var0) {
         return false;
      } else {
         Calendar var9 = Calendar.getInstance();
         var9.set(1, var9.get(1) - 13);
         var9.set(5, var9.get(5) + 1);
         var9.set(11, 0);
         var9.set(12, 0);
         var9.set(13, 0);
         var9.set(14, 0);
         Date var10 = var9.getTime();
         boolean var8 = var0.before(var10);
         boolean var11 = classLX.method7420(var0, 1671196928);
         if (!var11) {
            classAB.method301("Date not valid.", "Please ensure date follows the format", "DD/MM/YYYY and is after 01/01/1900", -1894342562);
            return false;
         } else {
            if (!var8) {
               IntProjection.field1935 = 485945013;
            } else {
               IntProjection.field1935 = (int)(var0.getTime() / 86400000L - 11745L) * -2004283061;
            }

            return true;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/util/Date;)Z")
   @ObfuscatedName("bi")
   static boolean method1015(Date var0) {
      Date var1 = classSV.method10740(53480384);
      return var0.after(var1);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/util/Date;")
   @ObfuscatedName("be")
   static Date method1016() {
      Calendar var0 = Calendar.getInstance();
      var0.set(2, 0);
      var0.set(5, 1);
      var0.set(1, 1900);
      return var0.getTime();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/util/Date;")
   @ObfuscatedName("by")
   static Date method1017() {
      Calendar var0 = Calendar.getInstance();
      var0.set(2, 0);
      var0.set(5, 1);
      var0.set(1, -162590629);
      return var0.getTime();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/util/Date;")
   @ObfuscatedName("bq")
   static Date method1018() {
      Calendar var0 = Calendar.getInstance();
      var0.set(2, 0);
      var0.set(5, 1);
      var0.set(1, 1233203016);
      return var0.getTime();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("bf")
   static void method1019(String var0, String var1, String var2) {
      classAC.method321(7, 2094222607);
      WorldEntity.method10671(var0, var1, var2, (byte)-119);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("bs")
   static void method1020(String var0, String var1, String var2) {
      classAC.method321(7, 2021562050);
      WorldEntity.method10671(var0, var1, var2, (byte)-47);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("cr")
   static boolean method1024(char var0) {
      for (int var1 = 0; var1 < field403.length(); var1++) {
         if (var0 == field403.charAt(var1)) {
            return true;
         }
      }

      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("bl")
   static void method1021(String var0, String var1, String var2) {
      classAC.method321(7, 2032216560);
      WorldEntity.method10671(var0, var1, var2, (byte)-106);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)I")
   @ObfuscatedName("bv")
   static int method1023(int var0, int var1) {
      for (int var2 = 0; var2 < 8; var2++) {
         if (var1 <= 30 + var0) {
            return var2;
         }

         var0 += 30;
         var0 += 1 != var2 && var2 != 3 ? 5 : 20;
      }

      return 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bh")
   static boolean method1025(char var0) {
      for (int var1 = 0; var1 < field403.length(); var1++) {
         if (var0 == field403.charAt(var1)) {
            return true;
         }
      }

      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("bz")
   static boolean method1026(char var0) {
      for (int var1 = 0; var1 < field403.length(); var1++) {
         if (var0 == field403.charAt(var1)) {
            return true;
         }
      }

      return false;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("du")
   static void method1057(int var0) {
      if (-47366135 * field384 != var0) {
         field384 = 1117408825 * var0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(C)Z")
   @ObfuscatedName("cu")
   static boolean method1027(char var0) {
      for (int var1 = 0; var1 < field403.length(); var1++) {
         if (var0 == field403.charAt(var1)) {
            return true;
         }
      }

      return false;
   }

   @ObfuscatedSignature(descriptor = "(Low;)I")
   @ObfuscatedName("zw")
   public static int method1066(classOW var0) {
      return var0.field5178 * 2106821511;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cb")
   static void method1028(boolean var0) {
      if (!classOE.field4841.method2138(1985617997) && !client.method2142(classOE.field4841, (byte)46) && !classOE.field4841.method2140(-951473990)) {
         field396 = classKK.field3819;
         field397 = classKK.field3778;
         field398 = classKK.field3821;
         classAC.method321(2, 2146421984);
         if (var0) {
            method1067("");
         }

         if (field389 == null || field389.isEmpty()) {
            if (classAB.field122.method2782((byte)8) != null) {
               field389 = classAB.field122.method2782((byte)8);
               client.field833 = true;
            } else {
               client.field833 = false;
            }
         }

         if (client.field833 && field389 != null && !field389.isEmpty()) {
            field402 = -878854915;
         } else {
            field402 = 0;
         }
      } else {
         classAC.method321(10, 2107485936);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dx")
   static void method1055() {
      classAC.method321(24, 2078336123);
      WorldEntity.method10671(classKK.field3842, classKK.field3843, classKK.field3801, (byte)-70);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cp")
   static void method1030() {
      int var0 = classAB.field122.method2831(1873816533);
      int var1 = client.field978 * -1239932395 + var0;
      long var4 = System.currentTimeMillis();
      int var3 = (int)(var4 / 86400000L) - 11745;
      if (var1 <= var3) {
         classAB.field122.method2833(var3, (byte)96);
         classAC.method321(38, 2126361507);
      } else {
         classBJ.method1121(true, -347989319);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dr")
   static void method1061() {
      if (classAB.field122.method2729((byte)100)) {
         classBK.method1122(0, 0, (byte)122);
      } else {
         classPD.method8880("scape main", 255, (byte)32);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cl")
   static void method1031() {
      int var0 = classAB.field122.method2831(1873816533);
      int var1 = client.field978 * -1239932395 + var0;
      long var4 = System.currentTimeMillis();
      int var3 = (int)(var4 / 86400000L) - 11745;
      if (var1 <= var3) {
         classAB.field122.method2833(var3, (byte)119);
         classAC.method321(527865296, 2071481689);
      } else {
         classBJ.method1121(true, -347989319);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)J")
   @ObfuscatedName("av")
   static long method1044(int var0) {
      try {
         return client.field961 * 1625812779590823737L;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "bf.av(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cv")
   static void method1033(boolean var0) {
      byte var1 = 0;
      if (!IndexDataBase.method11935(-2053271753)) {
         var1 = 12;
      } else if (classOE.field4841.method2138(1985617997) || client.method2142(classOE.field4841, (byte)122) || classOE.field4841.method2140(-1939642510)) {
         var1 = 10;
      }

      classAC.method321(var1, 2005160569);
      if (var0) {
         field389 = "";
         method1067("");
         classEV.field1942 = 0;
         classQP.field5584 = "";
      }

      if (null == field389 || field389.isEmpty()) {
         if (classAB.field122.method2782((byte)8) != null) {
            field389 = classAB.field122.method2782((byte)8);
            client.field833 = true;
         } else {
            client.field833 = false;
         }
      }

      if (client.field833 && null != field389 && !field389.isEmpty()) {
         field402 = -878854915;
      } else {
         field402 = 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cs")
   static void method1035() {
      field389 = field389.trim();
      if (field389.isEmpty()) {
         WorldEntity.method10671(classKK.field3711, classKK.field3767, classKK.field3677, (byte)-94);
      } else {
         long var1;
         try {
            URL var3 = new URL(classLZ.method7470("services", false, -1515016473) + "m=accountappeal/login.ws");
            URLConnection var4 = var3.openConnection();
            var4.setRequestProperty("connection", "close");
            var4.setDoInput(true);
            var4.setDoOutput(true);
            var4.setConnectTimeout(5000);
            OutputStreamWriter var5 = new OutputStreamWriter(var4.getOutputStream());
            var5.write("data1=req");
            var5.flush();
            InputStream var6 = var4.getInputStream();
            classXY var7 = new classXY(new byte[1000]);

            while (true) {
               int var8 = var6.read(var7.field6952, var7.field6953 * 702114061, 1000 - var7.field6953 * 702114061);
               if (-1 == var8) {
                  var7.field6953 = 0;
                  long var11 = classXY.method13115(var7, 1897605979);
                  var1 = var11;
                  break;
               }

               var7.field6953 += var8 * -1095856699;
               if (702114061 * var7.field6953 >= 1000) {
                  var1 = 0L;
                  break;
               }
            }
         } catch (Exception var10) {
            var1 = 0L;
         }

         int var0;
         if (0L == var1) {
            var0 = 5;
         } else {
            var0 = classGY.method5949(var1, field389, (byte)-10);
         }

         switch (var0) {
            case 2:
               WorldEntity.method10671(classKK.field3872, classKK.field3904, classKK.field3761, (byte)-6);
               classAC.method321(6, 2141136401);
               break;
            case 3:
               WorldEntity.method10671(classKK.field3725, classKK.field3605, classKK.field3877, (byte)-50);
               break;
            case 4:
               WorldEntity.method10671(classKK.field3878, classKK.field3879, classKK.field3880, (byte)-21);
               break;
            case 5:
               WorldEntity.method10671(classKK.field3881, classKK.field3882, classKK.field3883, (byte)-96);
               break;
            case 6:
               WorldEntity.method10671(classKK.field3884, classKK.field3885, classKK.field3886, (byte)-46);
               break;
            case 7:
               WorldEntity.method10671(classKK.field3766, classKK.field3888, classKK.field3889, (byte)-124);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Z")
   @ObfuscatedName("bw")
   static boolean method1014() {
      Date var0;
      try {
         SimpleDateFormat var2 = new SimpleDateFormat("ddMMyyyyHH", Locale.ENGLISH);
         var2.setLenient(false);
         StringBuilder var3 = new StringBuilder();
         String[] var4 = field406;
         int var5 = 0;

         Date var1;
         while (true) {
            if (var5 >= var4.length) {
               var3.append("12");
               var1 = var2.parse(var3.toString());
               break;
            }

            String var6 = var4[var5];
            if (null == var6) {
               classAB.method301("Date not valid.", "Please ensure all characters are populated.", "", 1922237284);
               var1 = null;
               break;
            }

            var3.append(var6);
            var5++;
         }

         var0 = var1;
      } catch (ParseException var7) {
         classAB.method301("Date not valid.", "Please ensure date follows the format", "DD/MM/YYYY and is after 01/01/1900", 801547909);
         return false;
      }

      if (null == var0) {
         return false;
      } else {
         Calendar var9 = Calendar.getInstance();
         var9.set(1, var9.get(1) - 13);
         var9.set(5, var9.get(5) + 1);
         var9.set(11, 0);
         var9.set(12, 0);
         var9.set(13, 0);
         var9.set(14, 0);
         Date var10 = var9.getTime();
         boolean var8 = var0.before(var10);
         boolean var11 = classLX.method7420(var0, -1375654832);
         if (!var11) {
            classAB.method301("Date not valid.", "Please ensure date follows the format", "DD/MM/YYYY and is after 01/01/1900", -1856828969);
            return false;
         } else {
            if (!var8) {
               IntProjection.field1935 = 485945013;
            } else {
               IntProjection.field1935 = (int)(var0.getTime() / 86400000L - 11745L) * -2004283061;
            }

            return true;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Lzv;Lzv;)V")
   @ObfuscatedName("ba")
   static void method1011(GameEngine var0, classZV var1, classZV var2) {
      if (field412) {
         classDP.method3534(var0, -644062932);
      } else {
         if ((1229235359 * classTZ.field6259 == 1 || !classON.field4993 && 1229235359 * classTZ.field6259 == 4)
            && classTZ.field6264 * 1422993977 >= field385 * -329898575 + 765 - 50
            && classTZ.field6255 * 1243590197 >= 453
            && 1422993977 * classTZ.field6264 < 765 + -329898575 * field385
            && classTZ.field6255 * 1243590197 < 503) {
            classAB.field122.method2731(!classAB.field122.method2729((byte)31), (byte)16);
            classUR.method11585((byte)0);
            Actor.method3134(-1612757241);
         }

         if (-44590225 * client.field829 != 5) {
            if (-1L == field401 * -6105922944827093555L) {
               field401 = (classDD.method2988(16711680) + 1000L) * 7971708337557546757L;
            }

            long var3 = classDD.method2988(16711680);
            if (classOL.method8545(1405545962) && -1L == field409 * -5269972525589787045L) {
               field409 = 3992049254271775187L * var3;
               if (-5269972525589787045L * field409 > -6105922944827093555L * field401) {
                  field401 = field409 * -3963514021849324345L;
               }
            }

            if (10 == -44590225 * client.field829 || client.field829 * -44590225 == 11) {
               if (classLU.field4230 == classXO.field6890) {
                  if (classTZ.field6259 * 1229235359 == 1 || !classON.field4993 && classTZ.field6259 * 1229235359 == 4) {
                     int var5 = field385 * -329898575 + 5;
                     short var6 = 463;
                     byte var7 = 100;
                     byte var8 = 35;
                     if (classTZ.field6264 * 1422993977 >= var5
                        && classTZ.field6264 * 1422993977 <= var7 + var5
                        && 1243590197 * classTZ.field6255 >= var6
                        && classTZ.field6255 * 1243590197 <= var6 + var8) {
                        if (classKB.method6484(206914850)) {
                           field412 = true;
                           field407 = 0;
                           field408 = 0;
                        }

                        return;
                     }
                  }

                  if (classIS.field3052 != null && classKB.method6484(927135030)) {
                     field412 = true;
                     field407 = 0;
                     field408 = 0;
                  }
               }

               int var23 = 1229235359 * classTZ.field6259;
               int var24 = classTZ.field6264 * 1422993977;
               int var25 = 1243590197 * classTZ.field6255;
               if (0 == var23) {
                  var24 = -38255113 * classTZ.field6253;
                  var25 = classTZ.field6262 * -2144333897;
               }

               if (!classON.field4993 && var23 == 4) {
                  var23 = 1;
               }

               classFA var26 = client.field932;
               classFA var9 = var26;
               if (-47366135 * field384 == 0) {
                  boolean var62 = false;

                  while (var9.method4555((short)4921)) {
                     if (84 == var9.field2174 * 1363213817) {
                        var62 = true;
                     }
                  }

                  int var93 = 1429288549 * Ignore.field5836 - 80;
                  short var108 = 291;
                  if (1 == var23 && var24 >= var93 - 75 && var24 <= var93 + 75 && var25 >= var108 - 20 && var25 <= 20 + var108) {
                     classUF.method11359(classLZ.method7470("secure", true, -425771032) + "m=account-creation/g=oldscape/create_account_funnel.ws", (byte)49);
                  }

                  var93 = Ignore.field5836 * 1429288549 + 80;
                  if (var23 == 1 && var24 >= var93 - 75 && var24 <= 75 + var93 && var25 >= var108 - 20 && var25 <= 20 + var108 || var62) {
                     if (classNU.method8185(-2068021330)) {
                        if (client.field833 && field389 != null && !field389.isEmpty()) {
                           field402 = -878854915;
                        } else {
                           field402 = 0;
                        }
                     } else {
                        classGG.method5457(1048463493);
                     }
                  }
               } else if (-47366135 * field384 == 1) {
                  boolean var61 = classOE.field4841.method2138(1985617997)
                     || client.method2142(classOE.field4841, (byte)106)
                     || classOE.field4841.method2140(-1486663592);

                  while (var9.method4555((short)-3262)) {
                     if (var9.field2174 * 1363213817 == 84) {
                        if (var61) {
                           WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-107);
                           client.field899 = AccountType.field92;
                           classXQ.method12934(false, 402128428);
                           classJB.method6386(20, -1192966987);
                        } else {
                           classGG.method5457(-132655086);
                        }

                        return;
                     }

                     if (13 == var9.field2174 * 1363213817) {
                        classIV.method6363(true, (byte)8);
                        return;
                     }
                  }

                  int var91 = 1429288549 * Ignore.field5836 - 80;
                  short var107 = 321;
                  if (var23 == 1 && var24 >= var91 - 75 && var24 <= var91 + 75 && var25 >= var107 - 20 && var25 <= 20 + var107) {
                     if (var61) {
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-37);
                        client.field899 = AccountType.field92;
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     } else {
                        classGG.method5457(440538868);
                     }

                     return;
                  }

                  var91 = 80 + Ignore.field5836 * 1429288549;
                  if (1 == var23 && var24 >= var91 - 75 && var24 <= 75 + var91 && var25 >= var107 - 20 && var25 <= 20 + var107) {
                     classIV.method6363(true, (byte)-50);
                     return;
                  }
               } else if (field384 * -47366135 == 2) {
                  int var56 = 201;
                  var56 += 52;
                  if (1 == var23 && var25 >= var56 - 12 && var25 < 2 + var56) {
                     field402 = 0;
                  }

                  var56 += 15;
                  if (1 == var23 && var25 >= var56 - 12 && var25 < var56 + 2) {
                     field402 = -878854915;
                  }

                  var56 += 15;
                  short var60 = 361;
                  if (classWR.field6768 != null) {
                     int var86 = classWR.field6768.field6368 * 518523273 / 2;
                     if (1 == var23
                        && var24 >= -1357616065 * classWR.field6768.field6366 - var86
                        && var24 <= classWR.field6768.field6366 * -1357616065 + var86
                        && var25 >= var60 - 15
                        && var25 < var60) {
                        switch (-323997799 * field388) {
                           case 1:
                              classUF.method11359(classKK.field3924, (byte)-71);
                              return;
                           case 2:
                              classUF.method11359("https://support.runescape.com/hc/en-gb", (byte)-68);
                        }
                     }
                  }

                  int var87 = 1429288549 * Ignore.field5836 - 80;
                  short var104 = 321;
                  if (1 == var23 && var24 >= var87 - 75 && var24 <= 75 + var87 && var25 >= var104 - 20 && var25 <= 20 + var104) {
                     field389 = field389.trim();
                     if (field389.isEmpty()) {
                        WorldEntity.method10671(classKK.field3656, classKK.field3726, classKK.field3914, (byte)-69);
                     } else if ((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                        WorldEntity.method10671(classKK.field3728, classKK.field3642, classKK.field3730, (byte)-18);
                     } else {
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-98);
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     }

                     return;
                  }

                  var87 = 80 + 180 + -511275367 * field393;
                  if (var23 == 1 && var24 >= var87 - 75 && var24 <= 75 + var87 && var25 >= var104 - 20 && var25 <= var104 + 20) {
                     classAC.method321(0, 2086875933);
                     field389 = "";
                     method1067("");
                     classEV.field1942 = 0;
                     classQP.field5584 = "";
                     field392 = true;
                  }

                  var87 = 1429288549 * Ignore.field5836 + -117;
                  var104 = 277;
                  field400 = var24 >= var87
                     && var24 < var87 + 1381434113 * classXR.field6923
                     && var25 >= var104
                     && var25 < classKP.field3983 * -580844865 + var104;
                  if (1 == var23 && field400) {
                     client.field833 = !client.field833;
                     if (!client.field833 && classAB.field122.method2782((byte)8) != null) {
                        classAB.field122.method2786(null, -1640684454);
                     }
                  }

                  var87 = 24 + 1429288549 * Ignore.field5836;
                  var104 = 277;
                  field405 = var24 >= var87
                     && var24 < classXR.field6923 * 1381434113 + var87
                     && var25 >= var104
                     && var25 < var104 + -580844865 * classKP.field3983;
                  if (var23 == 1 && field405) {
                     classAB.field122.method2723(!classAB.field122.method2722((short)16872), -1003388898);
                     if (!classAB.field122.method2722((short)1706)) {
                        field389 = "";
                        classAB.field122.method2786(null, 1575496339);
                        if (client.field833 && null != field389 && !field389.isEmpty()) {
                           field402 = -878854915;
                        } else {
                           field402 = 0;
                        }
                     }
                  }

                  label1101:
                  while (var9.method4555((short)4530)) {
                     if (var9.field2174 * 1363213817 == 13) {
                        classAC.method321(0, 2142095109);
                        field389 = "";
                        method1067("");
                        classEV.field1942 = 0;
                        classQP.field5584 = "";
                        field392 = true;
                     } else if (-1187692459 * field402 == 0) {
                        classOG.method8467(var9.field2173, (byte)0);
                        if (var9.field2174 * 1363213817 == 85 && !field389.isEmpty()) {
                           field389 = field389.substring(0, field389.length() - 1);
                        }

                        if (1363213817 * var9.field2174 == 84 || 1363213817 * var9.field2174 == 80) {
                           field402 = -878854915;
                        }

                        char var119 = var9.field2173;
                        boolean var113 = field403.indexOf(var119) != -1;
                        if (var113 && field389.length() < 320) {
                           field389 = field389 + var9.field2173;
                        }
                     } else if (field402 * -1187692459 == 1) {
                        if (85 == var9.field2174 * 1363213817 && !(String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                           method1067(
                              (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0]
                                 .substring(0, (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length() - 1)
                           );
                        } else if (1363213817 * var9.field2174 == 84 || 80 == 1363213817 * var9.field2174) {
                           field402 = 0;
                           if (1363213817 * var9.field2174 == 84) {
                              field389 = field389.trim();
                              if (field389.isEmpty()) {
                                 WorldEntity.method10671(classKK.field3656, classKK.field3726, classKK.field3914, (byte)-61);
                              } else if ((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                                 WorldEntity.method10671(classKK.field3728, classKK.field3642, classKK.field3730, (byte)-111);
                              } else {
                                 WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-55);
                                 classXQ.method12934(false, 402128428);
                                 classJB.method6386(20, -1192966987);
                              }

                              return;
                           }
                        }

                        if ((var9.method4563(82, 150717402) || var9.method4563(87, 2066997730)) && 67 == 1363213817 * var9.field2174) {
                           Clipboard var115 = Toolkit.getDefaultToolkit().getSystemClipboard();
                           Transferable var121 = var115.getContents(classOE.field4841);
                           int var123 = 20 - (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length();
                           if (var123 > 0) {
                              try {
                                 String var125 = (String)var121.getTransferData(DataFlavor.stringFlavor);
                                 int var17 = Math.min(var123, var125.length());
                                 int var18 = 0;

                                 while (true) {
                                    if (var18 >= var17) {
                                       method1067((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] + var125.substring(0, var17));
                                       continue label1101;
                                    }

                                    if (!classIB.method6177(var125.charAt(var18), 1447125769)) {
                                       break;
                                    }

                                    char var20 = var125.charAt(var18);
                                    boolean var19 = field403.indexOf(var20) != -1;
                                    if (!var19) {
                                       break;
                                    }

                                    var18++;
                                 }

                                 classAC.method321(3, 2027766083);
                                 return;
                              } catch (UnsupportedFlavorException var21) {
                              } catch (IOException var22) {
                              }
                           }
                        } else if (classIB.method6177(var9.field2173, 1440645145)) {
                           char var120 = var9.field2173;
                           boolean var114 = field403.indexOf(var120) != -1;
                           if (var114 && (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length() < 20) {
                              method1067((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] + var9.field2173);
                           }
                        }
                     }
                  }
               } else if (3 == -47366135 * field384) {
                  int var10 = 180 + -511275367 * field393;
                  short var11 = 241;
                  classUQ var12 = var1.method46(25, classKK.field3857.length() - 34, classKK.field3857, var10, var11);
                  if (var23 == 1 && var12.method11536(var24, var25, -594779443)) {
                     classUF.method11359(classKK.field3923, (byte)-113);
                  }

                  var10 = 180 + field393 * -511275367;
                  var11 = 276;
                  if (1 == var23 && var24 >= var10 - 75 && var24 <= var10 + 75 && var25 >= var11 - 20 && var25 <= var11 + 20) {
                     classIV.method6363(false, (byte)-56);
                  }

                  var10 = 180 + -511275367 * field393;
                  var11 = 326;
                  if (1 == var23 && var24 >= var10 - 75 && var24 <= 75 + var10 && var25 >= var11 - 20 && var25 <= var11 + 20) {
                     classUF.method11359(classKK.field3924, (byte)-55);
                     return;
                  }
               } else if (4 == -47366135 * field384) {
                  int var29 = 180 + -511275367 * field393 - 80;
                  short var65 = 321;
                  if (var23 == 1 && var24 >= var29 - 75 && var24 <= var29 + 75 && var25 >= var65 - 20 && var25 <= var65 + 20) {
                     classQP.field5584.trim();
                     if (classQP.field5584.length() != 6) {
                        WorldEntity.method10671(classKK.field3644, classKK.field3848, classKK.field3743, (byte)-31);
                     } else {
                        classEV.field1942 = Integer.parseInt(classQP.field5584) * 400348643;
                        classQP.field5584 = "";
                        classXQ.method12934(true, 402128428);
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-96);
                        classJB.method6386(20, -1192966987);
                     }

                     return;
                  }

                  if (var23 == 1 && var24 >= 180 + field393 * -511275367 - 9 && var24 <= 180 + -511275367 * field393 + 130 && var25 >= 263 && var25 <= 296) {
                     field392 = !field392;
                  }

                  if (1 == var23 && var24 >= 180 + field393 * -511275367 - 34 && var24 <= 34 + -511275367 * field393 + 180 && var25 >= 351 && var25 <= 363) {
                     classUF.method11359(classKK.field3924, (byte)64);
                  }

                  var29 = 80 + -511275367 * field393 + 180;
                  if (var23 == 1 && var24 >= var29 - 75 && var24 <= 75 + var29 && var25 >= var65 - 20 && var25 <= 20 + var65) {
                     classAC.method321(0, 2009592349);
                     field389 = "";
                     method1067("");
                     classEV.field1942 = 0;
                     classQP.field5584 = "";
                  }

                  while (var9.method4555((short)-5024)) {
                     boolean var95 = false;

                     for (int var13 = 0; var13 < field404.length(); var13++) {
                        if (var9.field2173 == field404.charAt(var13)) {
                           var95 = true;
                           break;
                        }
                     }

                     if (var9.field2174 * 1363213817 == 13) {
                        classAC.method321(0, 2003674242);
                        field389 = "";
                        method1067("");
                        classEV.field1942 = 0;
                        classQP.field5584 = "";
                     } else {
                        if (1363213817 * var9.field2174 == 85 && !classQP.field5584.isEmpty()) {
                           classQP.field5584 = classQP.field5584.substring(0, classQP.field5584.length() - 1);
                        }

                        if (1363213817 * var9.field2174 == 84) {
                           classQP.field5584.trim();
                           if (classQP.field5584.length() != 6) {
                              WorldEntity.method10671(classKK.field3644, classKK.field3848, classKK.field3743, (byte)-126);
                           } else {
                              classEV.field1942 = Integer.parseInt(classQP.field5584) * 400348643;
                              classQP.field5584 = "";
                              classXQ.method12934(true, 402128428);
                              WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-106);
                              classJB.method6386(20, -1192966987);
                           }

                           return;
                        }

                        if (var95 && classQP.field5584.length() < 6) {
                           classQP.field5584 = classQP.field5584 + var9.field2173;
                        }
                     }
                  }
               } else if (-47366135 * field384 == 5) {
                  int var31 = -511275367 * field393 + 180 - 80;
                  short var66 = 321;
                  if (1 == var23 && var24 >= var31 - 75 && var24 <= 75 + var31 && var25 >= var66 - 20 && var25 <= 20 + var66) {
                     classHC.method6023(-486975020);
                     return;
                  }

                  var31 = 80 + field393 * -511275367 + 180;
                  if (1 == var23 && var24 >= var31 - 75 && var24 <= var31 + 75 && var25 >= var66 - 20 && var25 <= var66 + 20) {
                     classBJ.method1121(true, -347989319);
                  }

                  short var96 = 361;
                  if (null != classLB.field4087) {
                     int var109 = 518523273 * classLB.field4087.field6368 / 2;
                     if (var23 == 1
                        && var24 >= classLB.field4087.field6366 * -1357616065 - var109
                        && var24 <= classLB.field4087.field6366 * -1357616065 + var109
                        && var25 >= var96 - 15
                        && var25 < var96) {
                        classUF.method11359(classLZ.method7470("secure", true, 1026054998) + "m=weblogin/g=oldscape/cant_log_in", (byte)-112);
                     }
                  }

                  while (var9.method4555((short)-16088)) {
                     boolean var110 = false;

                     for (int var14 = 0; var14 < field403.length(); var14++) {
                        if (var9.field2173 == field403.charAt(var14)) {
                           var110 = true;
                           break;
                        }
                     }

                     if (1363213817 * var9.field2174 == 13) {
                        classBJ.method1121(true, -347989319);
                     } else {
                        if (85 == var9.field2174 * 1363213817 && !field389.isEmpty()) {
                           field389 = field389.substring(0, field389.length() - 1);
                        }

                        if (84 == var9.field2174 * 1363213817) {
                           classHC.method6023(-486975020);
                           return;
                        }

                        if (var110 && field389.length() < 320) {
                           field389 = field389 + var9.field2173;
                        }
                     }
                  }
               } else if (6 != field384 * -47366135) {
                  if (-47366135 * field384 == 7) {
                     if (client.field1133 && !client.field891) {
                        int var36 = 1429288549 * Ignore.field5836 - 150;
                        int var68 = 25 + var36 + 240 + 40;
                        short var97 = 231;
                        int var111 = 40 + var97;
                        if (var23 == 1 && var24 >= var36 && var24 <= var68 && var25 >= var97 && var25 <= var111) {
                           field390 = classBJ.method1120(var36, var24, (byte)1) * 523390187;
                        }

                        int var116 = field393 * -511275367 + 180 - 80;
                        short var15 = 321;
                        if (1 == var23 && var24 >= var116 - 75 && var24 <= 75 + var116 && var25 >= var15 - 20 && var25 <= 20 + var15) {
                           boolean var16 = classCN.method2545((byte)35);
                           if (var16) {
                              classJB.method6386(50, -1192966987);
                              return;
                           }
                        }

                        var116 = 180 + -511275367 * field393 + 80;
                        if (var23 == 1 && var24 >= var116 - 75 && var24 <= var116 + 75 && var25 >= var15 - 20 && var25 <= var15 + 20) {
                           field406 = new String[8];
                           classBJ.method1121(true, -347989319);
                        }

                        while (var9.method4555((short)-14938)) {
                           if (101 == var9.field2174 * 1363213817) {
                              field406[-594543677 * field390] = null;
                           }

                           if (85 == var9.field2174 * 1363213817) {
                              if (field406[field390 * -594543677] == null && field390 * -594543677 > 0) {
                                 field390 -= 523390187;
                              }

                              field406[field390 * -594543677] = null;
                           }

                           if (var9.field2173 >= '0' && var9.field2173 <= '9') {
                              field406[-594543677 * field390] = "" + var9.field2173;
                              if (-594543677 * field390 < 7) {
                                 field390 += 523390187;
                              }
                           }

                           if (var9.field2174 * 1363213817 == 84) {
                              boolean var124 = classCN.method2545((byte)-59);
                              if (var124) {
                                 classJB.method6386(50, -1192966987);
                              }

                              return;
                           }
                        }
                     } else {
                        int var34 = 180 + field393 * -511275367 - 80;
                        short var67 = 321;
                        if (1 == var23 && var24 >= var34 - 75 && var24 <= 75 + var34 && var25 >= var67 - 20 && var25 <= 20 + var67) {
                           classUF.method11359(classLZ.method7470("secure", true, -1440110447) + "m=dob/set_dob.ws", (byte)13);
                           WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-73);
                           classAC.method321(6, 2026455776);
                           return;
                        }

                        var34 = 80 + 180 + field393 * -511275367;
                        if (1 == var23 && var24 >= var34 - 75 && var24 <= var34 + 75 && var25 >= var67 - 20 && var25 <= 20 + var67) {
                           classBJ.method1121(true, -347989319);
                        }
                     }
                  } else if (8 == field384 * -47366135) {
                     int var37 = field393 * -511275367 + 180 - 80;
                     short var69 = 321;
                     if (1 == var23 && var24 >= var37 - 75 && var24 <= 75 + var37 && var25 >= var69 - 20 && var25 <= 20 + var69) {
                        classUF.method11359("https://legal.jagex.com/docs/policies/privacy", (byte)-86);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-15);
                        classAC.method321(6, 2092138792);
                        return;
                     }

                     var37 = -511275367 * field393 + 180 + 80;
                     if (var23 == 1 && var24 >= var37 - 75 && var24 <= var37 + 75 && var25 >= var69 - 20 && var25 <= var69 + 20) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (field384 * -47366135 == 9) {
                     int var39 = -511275367 * field393 + 180;
                     short var70 = 311;
                     boolean var98 = 1 == var23 && var24 >= var39 - 75 && var24 <= 75 + var39 && var25 >= var70 - 20 && var25 <= 20 + var70;

                     while (var9.method4555((short)10417)) {
                        if (84 == var9.field2174 * 1363213817 || 13 == 1363213817 * var9.field2174) {
                           var98 = true;
                        }
                     }

                     if (var98) {
                        classIV.method6363(false, (byte)-42);
                     }
                  } else if (field384 * -47366135 == 10) {
                     int var40 = field393 * -511275367 + 180;
                     short var71 = 209;
                     boolean var99 = 1 == var23 && var24 >= var40 - 109 && var24 <= 109 + var40 && var25 >= var71 && var25 <= var71 + 68;

                     while (var9.method4555((short)11205)) {
                        if (1363213817 * var9.field2174 == 84) {
                           var99 = true;
                        }
                     }

                     if (var99) {
                        if (classNU.method8185(-1537811462)) {
                           return;
                        }

                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-76);
                        client.field899 = AccountType.field92;
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     }
                  } else if (12 == -47366135 * field384) {
                     int var41 = Ignore.field5836 * 1429288549;
                     int var72 = 233;
                     classUQ var100 = var2.method46(0, 30, classKK.field3898, var41, var72);
                     classUQ var112 = var2.method46(32, 32, classKK.field3898, var41, var72);
                     classUQ var118 = var2.method46(70, 34, classKK.field3898, var41, var72);
                     var72 += 17;
                     classUQ var122 = var2.method46(0, 34, classKK.field3899, var41, var72);
                     if (var23 == 1) {
                        if (var100.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/terms", (byte)108);
                        } else if (var112.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/policies/privacy", (byte)-70);
                        } else if (var118.method11536(var24, var25, -594779443) || var122.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/terms/eula", (byte)47);
                        }
                     }

                     var41 = Ignore.field5836 * 1429288549 - 80;
                     short var74 = 311;
                     if (1 == var23 && var24 >= var41 - 75 && var24 <= 75 + var41 && var25 >= var74 - 20 && var25 <= var74 + 20) {
                        classYY.method13834(44185423);
                        classIV.method6363(true, (byte)60);
                     }

                     var41 = 80 + 1429288549 * Ignore.field5836;
                     if (var23 == 1 && var24 >= var41 - 75 && var24 <= 75 + var41 && var25 >= var74 - 20 && var25 <= 20 + var74) {
                        field384 = 1641412837;
                     }
                  } else if (13 == -47366135 * field384) {
                     int var44 = Ignore.field5836 * 1429288549;
                     short var75 = 321;
                     if (var23 == 1 && var24 >= var44 - 75 && var24 <= 75 + var44 && var25 >= var75 - 20 && var25 <= var75 + 20) {
                        classIV.method6363(true, (byte)-31);
                     }
                  } else if (14 == field384 * -47366135) {
                     String var45 = "";
                     switch (-20437633 * field387) {
                        case 0:
                           var45 = "https://secure.runescape.com/m=offence-appeal/account-history";
                           break;
                        case 1:
                           var45 = "https://secure.runescape.com/m=accountappeal/passwordrecovery";
                           break;
                        case 2:
                           var45 = "https://support.runescape.com/hc/en-gb/articles/207256855-Settle-an-Unpaid-Balance";
                           break;
                        default:
                           classBJ.method1121(false, -347989319);
                     }

                     int var76 = field393 * -511275367 + 180;
                     short var101 = 276;
                     if (1 == var23 && var24 >= var76 - 75 && var24 <= 75 + var76 && var25 >= var101 - 20 && var25 <= 20 + var101) {
                        classUF.method11359(var45, (byte)4);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-14);
                        classAC.method321(6, 2021582746);
                        return;
                     }

                     var76 = -511275367 * field393 + 180;
                     var101 = 326;
                     if (1 == var23 && var24 >= var76 - 75 && var24 <= 75 + var76 && var25 >= var101 - 20 && var25 <= var101 + 20) {
                        classBJ.method1121(false, -347989319);
                     }
                  } else if (-47366135 * field384 == 24) {
                     int var46 = -511275367 * field393 + 180;
                     short var78 = 301;
                     if (1 == var23 && var24 >= var46 - 75 && var24 <= 75 + var46 && var25 >= var78 - 20 && var25 <= 20 + var78) {
                        classIV.method6363(false, (byte)-23);
                     }
                  } else if (32 == field384 * -47366135) {
                     int var47 = 180 + field393 * -511275367 - 80;
                     short var79 = 321;
                     if (var23 == 1 && var24 >= var47 - 75 && var24 <= 75 + var47 && var25 >= var79 - 20 && var25 <= 20 + var79) {
                        classUF.method11359(classLZ.method7470("secure", true, -41079728) + "m=dob/set_dob.ws", (byte)-49);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-40);
                        classAC.method321(6, 2125473289);
                        return;
                     }

                     var47 = 180 + -511275367 * field393 + 80;
                     if (1 == var23 && var24 >= var47 - 75 && var24 <= 75 + var47 && var25 >= var79 - 20 && var25 <= 20 + var79) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (33 == -47366135 * field384) {
                     int var49 = field393 * -511275367 + 180;
                     short var80 = 276;
                     if (1 == var23 && var24 >= var49 - 75 && var24 <= 75 + var49 && var25 >= var80 - 20 && var25 <= var80 + 20) {
                        classUF.method11359(classKK.field3923, (byte)-49);
                     }

                     var49 = -511275367 * field393 + 180;
                     var80 = 326;
                     if (1 == var23 && var24 >= var49 - 75 && var24 <= 75 + var49 && var25 >= var80 - 20 && var25 <= 20 + var80) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (34 == field384 * -47366135) {
                     int var51 = -511275367 * field393 + 180;
                     short var82 = 276;
                     if (1 == var23 && var24 >= var51 - 75 && var24 <= var51 + 75 && var25 >= var82 - 20 && var25 <= var82 + 20) {
                        if (classKB.method6484(790233952)) {
                           field412 = true;
                           field407 = 0;
                           field408 = 0;
                        }

                        return;
                     }

                     if (null != classIS.field3052 && classKB.method6484(520143731)) {
                        field412 = true;
                        field407 = 0;
                        field408 = 0;
                     }

                     var51 = 180 + field393 * -511275367;
                     var82 = 326;
                     if (var23 == 1 && var24 >= var51 - 75 && var24 <= 75 + var51 && var25 >= var82 - 20 && var25 <= 20 + var82) {
                        classBJ.method1121(false, -347989319);
                     }
                  } else if (38 == -47366135 * field384) {
                     int var53 = Ignore.field5836 * 1429288549;
                     short var84 = 281;
                     classUQ var103 = var1.method47(classKK.field3906, var53, var84);
                     if (1 == var23 && var103.method11536(var24, var25, -594779443)) {
                        classUF.method11359(classKK.field3907, (byte)37);
                        return;
                     }

                     classUQ.method11530(var103, (byte)-79);
                     var53 = Ignore.field5836 * 1429288549 - 80;
                     var84 = 321;
                     if (var23 == 1 && var24 >= var53 - 75 && var24 <= 75 + var53 && var25 >= var84 - 20 && var25 <= 20 + var84) {
                        classUF.method11359(classKK.field3923, (byte)37);
                        return;
                     }

                     var53 = 80 + 1429288549 * Ignore.field5836;
                     if (var23 == 1 && var24 >= var53 - 75 && var24 <= var53 + 75 && var25 >= var84 - 20 && var25 <= var84 + 20) {
                        classBJ.method1121(true, -347989319);
                        return;
                     }

                     while (var9.method4555((short)-13221)) {
                        if (var9.field2174 * 1363213817 == 84) {
                           classBJ.method1121(true, -347989319);
                           return;
                        }

                        if (var9.field2174 * 1363213817 == 13) {
                           classIV.method6363(true, (byte)50);
                           return;
                        }
                     }
                  }
               } else {
                  while (var9.method4555((short)-6861)) {
                     if (var9.field2174 * 1363213817 == 84 || 13 == 1363213817 * var9.field2174) {
                        classBJ.method1121(true, -347989319);
                     }
                  }

                  short var33 = 321;
                  if (1 == var23 && var25 >= var33 - 20 && var25 <= 20 + var33) {
                     classBJ.method1121(true, -347989319);
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;Lzv;)V")
   @ObfuscatedName("ck")
   static void method1037(classZV var0, classZV var1, classZV var2) {
      field385 = (773246731 * classSA.field5895 - 1131084549) / 2 * 720492222;
      field393 = 2007709401 * field385 + 1927755610;
      Ignore.field5836 = 273549476 + -1487048516 * field393;
      if (field412) {
         classCS.method2613(var0, var1, (byte)126);
      } else {
         classNI.field4671.method13552(field385 * -918010466, 0);
         classFR.field2389.method13552(-457580149 + field385 * -329898575, 0);
         IndexedSprite.method13853(AABB.field2370, 1759241362 + field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
         if (client.field829 * -280874990 == 0 || client.field829 * -44590225 == 5) {
            byte var3 = 20;
            var0.method59(classKK.field3818, 1480045799 * field393 + 180, 245 - var3, 807935480, -1);
            int var4 = -2006335938 - var3;
            classYW.method13746(1196830319 + 1590241782 * field393 - -1701498398, var4, 310167133, 34, 9179409);
            classYW.method13746(180 + field393 * -511275367 - 151, var4 + 1, 302, -485228553, 0);
            classYW.method13737(-1449015562 + field393 * -1977207312 - 1283803281, var4 + 2, field394 * 1758494531, 30, 9179409);
            classYW.method13737(745626899 * field394 + (field393 * -511275367 + 180 - 150), var4 + 2, 2048821098 - field394 * -293129138, 30, 0);
            var0.method59(field391, 180 + -1036442483 * field393, 276 - var3, -792817489, -1);
         }

         if (20 == -44590225 * client.field829) {
            IndexedSprite.method13853(
               classJB.field3118, field393 * -570871336 + -699716641 - classJB.field3118.field7119 / 2, 785169587 - classJB.field3118.field7117 / 2
            );
            int var11 = 201;
            var0.method59(field396, field393 * -511275367 + 255512494, var11, 16776960, 0);
            var11 += 15;
            var0.method59(field397, 262276244 * field393 + 124602931, var11, 2016689982, 0);
            var11 += 15;
            var0.method59(field398, field393 * -511275367 + 180, var11, 16776960, 0);
            var11 += 15;
            var11 += 7;
            if (field384 * -47366135 != 4 && field384 * -434040578 != 10 && 1 != -47366135 * field384) {
               var0.method51(classKK.field3786, -224992398 + field393 * -511275367 - -1951315623, var11, 934925706, 0);
               int var100 = 251703767;
               String var5 = classBQ.method1183(662521639);

               while (var0.method33(var5) > var100) {
                  var5 = var5.substring(0, var5.length() - 1);
               }

               var0.method51(AbstractFont.method6(var5), 180 + -511275367 * field393 - 2101702998, var11, 16777215, 0);
               var11 += 15;
               String var6 = classSL.method10515((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0], (short)9996);

               while (var0.method33(var6) > var100) {
                  var6 = var6.substring(1);
               }

               var0.method51(classKK.field3836 + var6, 47412999 + -1692522129 * field393 - 108, var11, 874092593, 0);
               var11 += 15;
            }
         }

         if (10 == client.field829 * -44590225 || -44590225 * client.field829 == 11 || client.field829 * -44590225 == 1594984110) {
            IndexedSprite.method13853(classJB.field3118, -511275367 * field393, -48457514);
            if (1294782112 * field384 == 0) {
               int var18 = 251;
               var0.method59(classKK.field3876, 1784322 + field393 * -511275367, var18, 16776960, 0);
               var18 += 30;
               int var101 = 180 + field393 * 1420202706 - 1429922573;
               short var139 = 291;
               IndexedSprite.method13853(classKA.field3512, var101 - 73, var139 - 20);
               classAAO.method225(var0, classKK.field3849, var101 - 73, var139 - 20, -589051593, 40, -1895873899, 0, 1, 1, 0);
               var101 = -2090731978 + -511275367 * field393 + 80;
               IndexedSprite.method13853(classKA.field3512, var101 - 73, var139 - 20);
               classAAO.method225(var0, classKK.field3850, var101 - 1042619399, var139 - 20, -1141962396, -1267570485, 515675746, 0, 1, 1, 0);
            } else if (-47366135 * field384 == 1) {
               var0.method59(field395, -335116810 + 477722604 * field393, 201, 1639424637, 0);
               int var20 = 236;
               var0.method59(field396, 471596656 + field393 * -390706832, var20, 16777215, 0);
               var20 += 15;
               var0.method59(field397, field393 * 203226939 + 180, var20, 520283740, 0);
               var20 += 15;
               var0.method59(field398, field393 * -511275367 + 126916962, var20, 297794538, 0);
               var20 += 15;
               int var103 = -1036394557 * field393 + 434324923 - -847433384;
               short var140 = 321;
               IndexedSprite.method13853(classKA.field3512, var103 - -1936434406, var140 - 20);
               var0.method59(classKK.field3775, var103, var140 + 5, 1790138908, 0);
               var103 = 965392693 + 180 + 1321051376 * field393;
               IndexedSprite.method13853(classKA.field3512, var103 - 560104424, var140 - 20);
               var0.method59(classKK.field3852, var103, 5 + var140, 16777215, 0);
            } else if (-47366135 * field384 == 2) {
               int var24 = 201;
               var0.method59(field396, 1182407941 * Ignore.field5836, var24, -1923227900, 0);
               var24 += 15;
               var0.method59(field397, 1429288549 * Ignore.field5836, var24, 337335160, 0);
               var24 += 15;
               var0.method59(field398, Ignore.field5836 * -1339748935, var24, 16776960, 0);
               var24 += 15;
               var24 += 7;
               var0.method51(classKK.field3786, 1429288549 * Ignore.field5836 - 1826396620, var24, -597470380, 0);
               int var105 = 1945561724;
               String var141 = classBQ.method1183(-1910047082);

               while (var0.method33(var141) > var105) {
                  var141 = var141.substring(1);
               }

               var0.method51(
                  AbstractFont.method6(var141)
                     + (
                        0 == field402 * 916717190 & 1857471468 * client.field856 % -118364587 < 20
                           ? classQY.method9885(591329361, (byte)32) + classDO.field1587
                           : ""
                     ),
                  Ignore.field5836 * -935640214 - 816150437,
                  var24,
                  16777215,
                  0
               );
               var24 += 15;
               String var161 = classSL.method10515((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0], (short)-11117);

               while (var0.method33(var161) > var105) {
                  var161 = var161.substring(1);
               }

               var0.method51(
                  classKK.field3836
                     + var161
                     + (
                        field402 * -1187692459 == 1 & 1612595797 * client.field856 % 1898320819 < 20
                           ? classQY.method9885(1121394401, (byte)38) + classDO.field1587
                           : ""
                     ),
                  234732962 * Ignore.field5836 - 1537343270,
                  var24,
                  16777215,
                  0
               );
               var24 += 15;
               var24 = 2034140217;
               int var7 = -117 + -821193709 * Ignore.field5836;
               IndexedSprite var8 = classLF.method6827(client.field833, field400, 1714764314);
               IndexedSprite.method13853(var8, var7, var24);
               var7 += var8.field7119 + 5;
               var1.method51(classKK.field3667, var7, 13 + var24, 16776960, 0);
               var7 = Ignore.field5836 * 1429288549 + 24;
               var8 = classLF.method6827(classAB.field122.method2722((short)-5967), field405, 164948057);
               IndexedSprite.method13853(var8, var7, var24);
               var7 += var8.field7119 + 5;
               var1.method51(classKK.field3646, var7, 13 + var24, -615649471, 0);
               var24 += 15;
               int var9 = 1429288549 * Ignore.field5836 - 80;
               short var10 = 321;
               IndexedSprite.method13853(classKA.field3512, var9 - -1335687776, var10 - 20);
               var0.method59(classKK.field3707, var9, 5 + var10, 16777215, 0);
               var9 = -1125313134 * Ignore.field5836 + -758855877;
               IndexedSprite.method13853(classKA.field3512, var9 - -1359430765, var10 - 20);
               var0.method59(classKK.field3852, var9, 5 + var10, 360659426, 0);
               short var33 = 357;
               switch (-323997799 * field388) {
                  case 2:
                     classJH.field3156 = classKK.field3866;
                     break;
                  default:
                     classJH.field3156 = classKK.field3864;
               }

               classWR.field6768 = new classUQ(1429288549 * Ignore.field5836, var33, var1.method33(classJH.field3156), 11);
               classLB.field4087 = new classUQ(1429288549 * Ignore.field5836, var33, var1.method33(classKK.field3865), 11);
               var1.method59(classJH.field3156, Ignore.field5836 * 1429288549, var33, 1597580725, 0);
            } else if (3 == 744479829 * field384) {
               int var34 = -812916649;
               var0.method59(classKK.field3922, -511275367 * field393 + -449923283, var34, -912342096, 0);
               var34 += 20;
               var1.method59(classKK.field3856, -511275367 * field393 + -1780143085, var34, 16776960, 0);
               var34 += 20;
               var1.method59(classKK.field3857, 1906241305 + field393 * -511275367, var34, 16776960, 0);
               var34 += 15;
               int var106 = 473221849 + field393 * -511275367;
               int var142 = 259257879;
               IndexedSprite.method13853(classKA.field3512, var106 - 73, var142 - 20);
               var2.method59(classKK.field3858, var106, 5 + var142, 160558504, 0);
               var106 = 180 + field393 * -110202849;
               short var143 = 326;
               IndexedSprite.method13853(classKA.field3512, var106 - 73, var143 - 20);
               var2.method59(classKK.field3859, var106, 5 + var143, -846584686, 0);
            } else if (-47366135 * field384 == 4) {
               var0.method59(classKK.field3835, -511275367 * field393 + -1992233241, 1941028772, 16776960, 0);
               int var38 = 236;
               var0.method59(field396, 1949303819 + field393 * 1489063158, var38, -851308657, 0);
               var38 += 15;
               var0.method59(field397, field393 * -511275367 + 180, var38, -1321538616, 0);
               var38 += 15;
               var0.method59(field398, -640598417 + field393 * -1294010063, var38, 16777215, 0);
               var38 += 15;
               var0.method51(
                  classKK.field3847
                     + classSL.method10515(classQP.field5584, (short)10712)
                     + (client.field856 * -854700000 % 40 < 20 ? classQY.method9885(-1475982407, (byte)50) + classDO.field1587 : ""),
                  -2120068131 * field393 + 180 - 108,
                  var38,
                  -267642608,
                  0
               );
               var38 -= 8;
               var0.method51(classKK.field3614, -511275367 * field393 + 267054851 - 9, var38, 16776960, 0);
               var38 += 15;
               var0.method51(classKK.field3615, -511275367 * field393 + 180 - 9, var38, 16776960, 0);
               int var108 = -1140636516 + field393 * -511275367 - 9 + var0.method33(classKK.field3615) + 15;
               int var144 = var38 - var0.field8;
               IndexedSprite var162;
               if (field392) {
                  var162 = classEK.field1777;
               } else {
                  var162 = classCK.field778;
               }

               IndexedSprite.method13853(var162, var108, var144);
               var38 += 15;
               int var173 = 180 + -511275367 * field393 - 80;
               int var180 = -1810191382;
               IndexedSprite.method13853(classKA.field3512, var173 - -1602904552, var180 - 20);
               var0.method59(classKK.field3775, var173, 5 + var180, 16777215, 0);
               var173 = -579453502 + field393 * -511275367 + 180;
               IndexedSprite.method13853(classKA.field3512, var173 - -1428110483, var180 - 20);
               var0.method59(classKK.field3852, var173, var180 + 5, -672554990, 0);
               var1.method59(classKK.field3864, 180 + -511275367 * field393, 1171286367 + var180, 16777215, 0);
            } else if (field384 * 1379847843 == 5) {
               var0.method59(classKK.field3860, -511275367 * field393 + 180, 644242555, -801972086, 0);
               int var45 = 221;
               var2.method59(field396, -1714141921 * field393 + 180, var45, 16776960, 0);
               var45 += 15;
               var2.method59(field397, -625247436 * field393 + 180, var45, 16776960, 0);
               var45 += 15;
               var2.method59(field398, 180 + 1532601068 * field393, var45, 16776960, 0);
               var45 += 15;
               var45 += 14;
               var0.method51(classKK.field3873, 1727484029 * field393 + 180 - -716288027, var45, 16777215, 0);
               short var109 = 174;
               String var145 = classBQ.method1183(-1706828169);

               while (var0.method33(var145) > var109) {
                  var145 = var145.substring(1);
               }

               var0.method51(
                  AbstractFont.method6(var145)
                     + (1612595797 * client.field856 % -916548152 < 20 ? classQY.method9885(-1203829417, (byte)52) + classDO.field1587 : ""),
                  139053051 * field393 + 180 - 1498494551,
                  var45,
                  -1387998647,
                  0
               );
               var45 += 15;
               int var163 = field393 * 829972991 + 1498050109 - -596233114;
               int var175 = -481485593;
               IndexedSprite.method13853(classKA.field3512, var163 - 1502714650, var175 - 20);
               var0.method59(classKK.field3862, var163, 5 + var175, 16777215, 0);
               var163 = 80 + field393 * 2100436785 + -1345649424;
               IndexedSprite.method13853(classKA.field3512, var163 - 204515538, var175 - 20);
               var0.method59(classKK.field3863, var163, 5 + var175, -970168494, 0);
               short var176 = 356;
               var1.method59(classKK.field3865, Ignore.field5836 * 1429288549, var176, 268435455, 0);
            } else if (field384 * 1626626773 == 6) {
               int var51 = -2046700811;
               var0.method59(field396, 1718595516 * field393 + 180, var51, -1003468442, 0);
               var51 += 15;
               var0.method59(field397, field393 * 1817062624 + 180, var51, -448395677, 0);
               var51 += 15;
               var0.method59(field398, field393 * 1212807811 + 697833220, var51, -1621319646, 0);
               var51 += 15;
               int var110 = -689087509 * field393 + 180;
               short var146 = 321;
               IndexedSprite.method13853(classKA.field3512, var110 - 73, var146 - 20);
               var0.method59(classKK.field3863, var110, 5 + var146, 16777215, 0);
            } else if (7 == 1383821149 * field384) {
               if (client.field1133 && !client.field891) {
                  int var59 = 1500538496;
                  var0.method59(field396, 1429288549 * Ignore.field5836, var59, -1659270328, 0);
                  var59 += 15;
                  var0.method59(field397, 1429288549 * Ignore.field5836, var59, 575222146, 0);
                  var59 += 15;
                  var0.method59(field398, 1429288549 * Ignore.field5836, var59, -1542193426, 0);
                  int var113 = 1362152428 * Ignore.field5836 - 150;
                  var59 += 10;

                  for (int var148 = 0; var148 < 8; var148++) {
                     classKA.field3512.method13856(var113, var59, 30, 645364600);
                     boolean var165 = field390 * -594543677 == var148 & 593081986 * client.field856 % -1711181804 < 20;
                     var0.method51(
                        (field406[var148] == null ? "" : field406[var148]) + (var165 ? classQY.method9885(1390750973, (byte)114) + classDO.field1587 : ""),
                        10 + var113,
                        27 + var59,
                        16777215,
                        0
                     );
                     if (var148 != 1 && 3 != var148) {
                        var113 += 35;
                     } else {
                        var113 += 50;
                        var0.method51(AbstractFont.method6("/"), var113 - 13, 27 + var59, 766942275, 0);
                     }
                  }

                  int var149 = Ignore.field5836 * 1429288549 - 80;
                  int var166 = 913704320;
                  IndexedSprite.method13853(classKA.field3512, var149 - -1939460916, var166 - 20);
                  var0.method59("Submit", var149, 5 + var166, 16777215, 0);
                  var149 = 1429288549 * Ignore.field5836 + 80;
                  IndexedSprite.method13853(classKA.field3512, var149 - 73, var166 - 20);
                  var0.method59(classKK.field3852, var149, 5 + var166, 289005222, 0);
               } else {
                  int var55 = 216;
                  var0.method59(classKK.field3890, field393 * -511275367 + -1590372234, var55, 1310195382, 0);
                  var55 += 15;
                  var2.method59(classKK.field3891, field393 * -511275367 + 1837931750, var55, 185958801, 0);
                  var55 += 15;
                  var2.method59(classKK.field3631, 180 + -511275367 * field393, var55, -1365038012, 0);
                  var55 += 15;
                  int var111 = 180 + field393 * 228791866 - 80;
                  short var147 = 321;
                  IndexedSprite.method13853(classKA.field3512, var111 - -2041692605, var147 - 20);
                  var0.method59(classKK.field3893, var111, var147 + 5, -782094473, 0);
                  var111 = 180 + -511275367 * field393 + 80;
                  IndexedSprite.method13853(classKA.field3512, var111 - 1280422970, var147 - 20);
                  var0.method59(classKK.field3863, var111, 5 + var147, -735079344, 0);
               }
            } else if (8 == -47366135 * field384) {
               int var63 = 1333139687;
               var0.method59(classKK.field3611, 1707910678 + field393 * 212825472, var63, 16776960, 0);
               var63 += 15;
               var2.method59(classKK.field3747, 180 + -511275367 * field393, var63, 16776960, 0);
               var63 += 15;
               var2.method59(classKK.field3748, 180 + 675646157 * field393, var63, 16776960, 0);
               var63 += 15;
               int var114 = -511275367 * field393 + 180 - 80;
               short var151 = 321;
               IndexedSprite.method13853(classKA.field3512, var114 - 73, var151 - 20);
               var0.method59(classKK.field3749, var114, var151 + 5, -2001349019, 0);
               var114 = 80 + -511275367 * field393 + 180;
               IndexedSprite.method13853(classKA.field3512, var114 - 974495066, var151 - 20);
               var0.method59(classKK.field3863, var114, 5 + var151, 16777215, 0);
            } else if (9 == -1357373007 * field384) {
               int var67 = 2123022369;
               var0.method59(field396, -1073913936 + 1719962724 * field393, var67, 16776960, 0);
               var67 += 25;
               var0.method59(field397, -1030067331 + -511275367 * field393, var67, -2015000797, 0);
               var67 += 25;
               var0.method59(field398, -1098811491 + -511275367 * field393, var67, 880788078, 0);
               int var116 = field393 * 2122465052 + 180;
               int var152 = 1420588668;
               IndexedSprite.method13853(classKA.field3512, var116 - 73, var152 - 20);
               var0.method59(classKK.field3858, var116, var152 + 5, 16777215, 0);
            } else if (10 == 1859767338 * field384) {
               int var70 = 180 + -511275367 * field393;
               int var117 = 209;
               var0.method59(classKK.field3876, field393 * -1015984853 + -615449417, var117, -628135529, 0);
               var117 += 20;
               IndexedSprite.method13853(classGQ.field2653, var70 - 109, var117);
               if (field411.isEmpty()) {
                  IndexedSprite.method13853(classQH.field5542, var70 - 1683566575, 18 + var117);
               } else {
                  IndexedSprite.method13853(classQH.field5542, var70 - 48, var117 + 5);
                  var0.method59(field411, var70, 784228926 + var117 - 15, 16776960, 0);
               }
            } else if (1231588691 * field384 == 12) {
               int var71 = -659052429 * Ignore.field5836;
               int var119 = 216;
               var2.method59(classKK.field3897, var71, var119, 16777215, 0);
               var119 += 17;
               var2.method59(classKK.field3898, var71, var119, 16777215, 0);
               var119 += 17;
               var2.method59(classKK.field3899, var71, var119, 16777215, 0);
               var119 += 17;
               var2.method59(classKK.field3900, var71, var119, 16777215, 0);
               var71 = -1511449902 * Ignore.field5836 - 80;
               var119 = 1319791617;
               IndexedSprite.method13853(classKA.field3512, var71 - 73, var119 - 20);
               var0.method59(classKK.field3908, var71, var119 + 5, 1628073010, 0);
               var71 = Ignore.field5836 * 1429288549 + 80;
               IndexedSprite.method13853(classKA.field3512, var71 - 73, var119 - 20);
               var0.method59(classKK.field3909, var71, 5 + var119, 16777215, 0);
            } else if (13 == field384 * -47366135) {
               int var74 = 1086076284;
               var2.method59(classKK.field3901, -1954857712 + -511275367 * field393, var74, 16777215, 0);
               var74 += 20;
               var2.method59(classKK.field3902, 795096365 * field393 + 180, var74, 16777215, 0);
               int var124 = -109147146 + -719546725 * field393;
               var74 = 1662992570;
               IndexedSprite.method13853(classKA.field3512, var124 - 73, var74 - 20);
               var0.method59(classKK.field3863, var124, var74 + 5, 16777215, 0);
            } else if (14 == field384 * -101961388) {
               int var77 = 201;
               String var125 = "";
               String var153 = "";
               String var167 = "";
               switch (-20437633 * field387) {
                  case 0:
                     var125 = classKK.field3905;
                     var153 = classKK.field3660;
                     var167 = classKK.field3661;
                     break;
                  case 1:
                     var125 = classKK.field3698;
                     var153 = classKK.field3699;
                     var167 = classKK.field3700;
                     break;
                  case 2:
                     var125 = classKK.field3782;
                     var153 = classKK.field3895;
                     var167 = classKK.field3620;
                     break;
                  default:
                     classBJ.method1121(false, -347989319);
               }

               var0.method59(var125, field393 * 1525469810 + 180, var77, 16776960, 0);
               var77 += 20;
               var0.method59(var153, 180 + field393 * -511275367, var77, 1835528424, 0);
               var77 += 20;
               var0.method59(var167, 180 + field393 * 1830065618, var77, 16776960, 0);
               int var177 = 180 + field393 * 1742724931;
               int var181 = -1422727666;
               IndexedSprite.method13853(classKA.field3512, var177 - 1836375793, var181 - 20);
               if (1 == -20437633 * field387) {
                  var0.method59(classKK.field3643, var177, var181 + 5, 527666446, 0);
               } else {
                  var0.method59(classKK.field3708, var177, 5 + var181, 16777215, 0);
               }

               var177 = 136475424 * field393 + 180;
               short var182 = 326;
               IndexedSprite.method13853(classKA.field3512, var177 - 73, var182 - 20);
               var0.method59(classKK.field3863, var177, 5 + var182, -797881469, 0);
            } else if (24 == -47366135 * field384) {
               int var80 = 126920361;
               var0.method59(field396, -97731202 * field393 + 180, var80, 16777215, 0);
               var80 += 15;
               var0.method59(field397, field393 * -314548694 + 180, var80, 16777215, 0);
               var80 += 15;
               var0.method59(field398, -511275367 * field393 + 180, var80, 16777215, 0);
               var80 += 15;
               int var126 = -707192948 * field393 + 180;
               int var154 = 1571813227;
               IndexedSprite.method13853(classKA.field3512, var126 - -1907455797, var154 - 20);
               var0.method59(classKK.field3793, var126, 5 + var154, 16777215, 0);
            } else if (32 == field384 * -1638485176) {
               int var84 = 216;
               var0.method59(classKK.field3890, field393 * 1773237363 + 38680934, var84, 16776960, 0);
               var84 += 15;
               var2.method59(classKK.field3891, field393 * -1842270291 + 180, var84, 16776960, 0);
               var84 += 15;
               var2.method59(classKK.field3631, -1772001207 + field393 * -1761888455, var84, 16776960, 0);
               var84 += 15;
               int var127 = -1548992256 + field393 * -511275367 - -1811343017;
               int var155 = -1096467379;
               IndexedSprite.method13853(classKA.field3512, var127 - 1760477534, var155 - 20);
               var0.method59(classKK.field3893, var127, 5 + var155, 16777215, 0);
               var127 = 1391245649 * field393 + 180 + -742532449;
               IndexedSprite.method13853(classKA.field3512, var127 - -755815113, var155 - 20);
               var0.method59(classKK.field3863, var127, 5 + var155, 16777215, 0);
            } else if (33 == -1137910153 * field384) {
               int var88 = 201;
               var0.method59(field396, 337117778 * field393 + 180, var88, 1298173261, 0);
               var88 += 20;
               var1.method59(field397, -48552344 + -511275367 * field393, var88, 16776960, 0);
               var88 += 20;
               var1.method59(field398, 1537870605 * field393 + 180, var88, 1027137124, 0);
               var88 += 15;
               int var129 = field393 * 129767771 + 180;
               int var156 = 276;
               IndexedSprite.method13853(classKA.field3512, var129 - 73, var156 - 20);
               var2.method59(classKK.field3734, var129, var156 + 5, 16777215, 0);
               var129 = field393 * -511275367 + -1536818220;
               var156 = 178394409;
               IndexedSprite.method13853(classKA.field3512, var129 - 73, var156 - 20);
               var2.method59(classKK.field3863, var129, 5 + var156, 1155510458, 0);
            } else if (field384 * -47366135 == -863443001) {
               int var92 = -1168271830;
               var0.method59(field396, 140468799 + field393 * -511275367, var92, 1248320482, 0);
               var92 += 20;
               var1.method59(field397, 180 + field393 * 1068749598, var92, 16776960, 0);
               var92 += 20;
               var1.method59(field398, field393 * -511275367 + -608961884, var92, 16776960, 0);
               int var131 = 180 + field393 * 302218409;
               int var158 = 1074409470;
               IndexedSprite.method13853(classKA.field3512, var131 - 1092248761, var158 - 20);
               String var168 = classIS.field3052 != null ? classKK.field3919 : classKK.field3784;
               var2.method59(var168, var131, var158 + 5, 16777215, 0);
               var131 = -511275367 * field393 + 180;
               short var159 = 326;
               IndexedSprite.method13853(classKA.field3512, var131 - 383617617, var159 - 20);
               var2.method59(classKK.field3863, var131, var159 + 5, -1627316566, 0);
            } else if (38 == field384 * -1445805556) {
               int var95 = Ignore.field5836 * 1429288549;
               int var133 = -535944833;
               var0.method59(classKK.field3757, var95, var133, 16776960, 0);
               var133 += 25;
               var1.method59(classKK.field3654, var95, var133, -916142410, 0);
               var133 += 20;
               var1.method59(classKK.field3875, var95, var133, 1828930689, 0);
               var133 += 20;
               var1.method59(classKK.field3906, var95, var133, 522873385, 0);
               var95 = 1429288549 * Ignore.field5836 - 573417590;
               var133 = -156538164;
               IndexedSprite.method13853(classKA.field3512, var95 - 518342554, var133 - 20);
               var0.method59(classKK.field3734, var95, 5 + var133, 16777215, 0);
               var95 = -504219350 + -675166993 * Ignore.field5836;
               IndexedSprite.method13853(classKA.field3512, var95 - 903396607, var133 - 20);
               var0.method59(classKK.field3707, var95, 5 + var133, 16777215, 0);
            }
         }

         if (-1341454611 * client.field829 >= 10) {
            int[] var98 = new int[4];
            classYW.method13714(var98);
            classYW.method13710(field385 * -2032062330, 0, -329898575 * field385 + 765, -2069016894 * IntProjection.field1936);
            classML.field4529.method2614(field385 * -329898575 - 22, client.field856 * 708061574, 1698595949);
            classML.field4529.method2614(765 + -329898575 * field385 + 22 - 128, client.field856 * 2101438123, 1698595949);
            classYW.method13718(var98);
         }

         IndexedSprite.method13853(MessageNode.field733[classAB.field122.method2729((byte)109) ? 1 : 0], -700798436 + -329898575 * field385 - -1002065549, 463);
         if (-44590225 * client.field829 > 5 && classLU.field4230 == classXO.field6890) {
            if (SceneTilePaint.field2292 != null) {
               int var99 = 1442291896 * field385 + 5;
               int var138 = 1498922825;
               byte var160 = 100;
               byte var169 = 35;
               IndexedSprite.method13853(SceneTilePaint.field2292, var99, var138);
               var0.method59(classKK.field3788 + " " + client.field828 * 1311484143, var99 + var160 / 2, var138 + var169 / 2 - 2, 16777215, 0);
               if (null != classIS.field3052) {
                  var1.method59(classKK.field3919, var99 + var160 / 2, 12 + var169 / 2 + var138, -1846162309, 0);
               } else {
                  var1.method59(classKK.field3815, var99 + var160 / 2, var138 + var169 / 2 + 12, 16777215, 0);
               }
            } else {
               SceneTilePaint.field2292 = classSU.method10729(classHC.field2765, "sl_button", "", -1771673613);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;Lzv;)V")
   @ObfuscatedName("ca")
   static void method1038(classZV var0, classZV var1, classZV var2) {
      field385 = (773246731 * classSA.field5895 - -393078566) / 2 * 87864371;
      field393 = 2007709401 * field385 + 1927755610;
      Ignore.field5836 = -1746170077 + -948983239 * field393;
      if (field412) {
         classCS.method2613(var0, var1, (byte)4);
      } else {
         classNI.field4671.method13552(field385 * -1661456004, 0);
         classFR.field2389.method13552(-663818722 + field385 * -732336348, 0);
         IndexedSprite.method13853(AABB.field2370, 1684415470 + field385 * 408960670 - AABB.field2370.field7119 / 2, 18);
         if (client.field829 * -204561754 == 0 || client.field829 * 1646822502 == 5) {
            byte var3 = 20;
            var0.method59(classKK.field3818, -1800123830 * field393 + 180, 245 - var3, -1116489866, -1);
            int var4 = 253 - var3;
            classYW.method13746(180 + -511275367 * field393 - 2144452806, var4, 304, 34, -562605477);
            classYW.method13746(180 + field393 * 741948583 - 151, var4 + 1, -2113878038, 32, 0);
            classYW.method13737(180 + field393 * 13419645 - 150, var4 + 2, field394 * -1633092332, 30, 9179409);
            classYW.method13737(-956929708 * field394 + (field393 * -511275367 + 180 - 88341582), var4 + 2, -16816697 - field394 * 1758494531, 30, 0);
            var0.method59(field391, 1232105783 + 2133838199 * field393, 1015364052 - var3, -63003792, -1);
         }

         if (20 == 1828858704 * client.field829) {
            IndexedSprite.method13853(classJB.field3118, field393 * -511275367 + 180 - classJB.field3118.field7119 / 2, 271 - classJB.field3118.field7117 / 2);
            int var11 = 201;
            var0.method59(field396, field393 * -511275367 + 358056881, var11, 16776960, 0);
            var11 += 15;
            var0.method59(field397, 1425151201 * field393 + 180, var11, 476479948, 0);
            var11 += 15;
            var0.method59(field398, field393 * -511275367 + 180, var11, 16776960, 0);
            var11 += 15;
            var11 += 7;
            if (field384 * -47366135 != 4 && field384 * 979309161 != 10 && 1 != -47366135 * field384) {
               var0.method51(classKK.field3786, -1840947873 + field393 * -511275367 - 110, var11, 16777215, 0);
               int var100 = 1573429823;
               String var5 = classBQ.method1183(-1137886805);

               while (var0.method33(var5) > var100) {
                  var5 = var5.substring(0, var5.length() - 1);
               }

               var0.method51(AbstractFont.method6(var5), 1773298741 + -511275367 * field393 - 70, var11, -1346391396, 0);
               var11 += 15;
               String var6 = classSL.method10515((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0], (short)-13248);

               while (var0.method33(var6) > var100) {
                  var6 = var6.substring(1);
               }

               var0.method51(classKK.field3836 + var6, 1169300183 + -899133898 * field393 - 1433306790, var11, -1791937421, 0);
               var11 += 15;
            }
         }

         if (10 == client.field829 * -44590225 || -44590225 * client.field829 == 11 || client.field829 * -44590225 == 50) {
            IndexedSprite.method13853(classJB.field3118, 1346605585 * field393, 955202033);
            if (1221978837 * field384 == 0) {
               int var18 = 251;
               var0.method59(classKK.field3876, 180 + field393 * -511275367, var18, 16776960, 0);
               var18 += 30;
               int var101 = 180 + field393 * -511275367 - 1697082701;
               int var139 = -1783105289;
               IndexedSprite.method13853(classKA.field3512, var101 - 1963485774, var139 - 20);
               classAAO.method225(var0, classKK.field3849, var101 - 1520160389, var139 - 20, 144, 1715784783, 16777215, 0, 1, 1, 0);
               var101 = 180 + 502126206 * field393 + 80;
               IndexedSprite.method13853(classKA.field3512, var101 - 73, var139 - 20);
               classAAO.method225(var0, classKK.field3850, var101 - 73, var139 - 20, 144, 40, -1878386831, 0, 1, 1, 0);
            } else if (-47366135 * field384 == 1) {
               var0.method59(field395, 180 + -511275367 * field393, 201, 619902813, 0);
               int var20 = -1683578874;
               var0.method59(field396, 1827188165 + field393 * -511275367, var20, 331754410, 0);
               var20 += 15;
               var0.method59(field397, field393 * -511275367 + 180, var20, -50469683, 0);
               var20 += 15;
               var0.method59(field398, field393 * -2023198662 + 391357984, var20, 305744876, 0);
               var20 += 15;
               int var103 = 1579477914 * field393 + 180 - -1677046028;
               short var140 = 321;
               IndexedSprite.method13853(classKA.field3512, var103 - 1103392854, var140 - 20);
               var0.method59(classKK.field3775, var103, var140 + 5, 16777215, 0);
               var103 = 80 + 180 + 1652940705 * field393;
               IndexedSprite.method13853(classKA.field3512, var103 - -1696637224, var140 - 20);
               var0.method59(classKK.field3852, var103, 5 + var140, 387468185, 0);
            } else if (-47366135 * field384 == 2) {
               int var24 = 201;
               var0.method59(field396, -824161127 * Ignore.field5836, var24, -265265166, 0);
               var24 += 15;
               var0.method59(field397, 1429288549 * Ignore.field5836, var24, 16776960, 0);
               var24 += 15;
               var0.method59(field398, Ignore.field5836 * -1035525718, var24, 16776960, 0);
               var24 += 15;
               var24 += 7;
               var0.method51(classKK.field3786, -907051800 * Ignore.field5836 - -497047783, var24, 16777215, 0);
               short var105 = 200;
               String var141 = classBQ.method1183(-251419245);

               while (var0.method33(var141) > var105) {
                  var141 = var141.substring(1);
               }

               var0.method51(
                  AbstractFont.method6(var141)
                     + (0 == field402 * 1711624139 & -1512452017 * client.field856 % 40 < 20 ? classQY.method9885(16776960, (byte)23) + classDO.field1587 : ""),
                  Ignore.field5836 * 1429288549 - 70,
                  var24,
                  16777215,
                  0
               );
               var24 += 15;
               String var161 = classSL.method10515((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0], (short)-5319);

               while (var0.method33(var161) > var105) {
                  var161 = var161.substring(1);
               }

               var0.method51(
                  classKK.field3836
                     + var161
                     + (
                        field402 * -1187692459 == 1 & -1720131858 * client.field856 % -561185502 < 20
                           ? classQY.method9885(-2838962, (byte)9) + classDO.field1587
                           : ""
                     ),
                  1429288549 * Ignore.field5836 - -1963381044,
                  var24,
                  16777215,
                  0
               );
               var24 += 15;
               var24 = -1695284784;
               int var7 = -1788063647 + 91654015 * Ignore.field5836;
               IndexedSprite var8 = classLF.method6827(client.field833, field400, 2006070908);
               IndexedSprite.method13853(var8, var7, var24);
               var7 += var8.field7119 + 5;
               var1.method51(classKK.field3667, var7, 13 + var24, 16776960, 0);
               var7 = Ignore.field5836 * 1429288549 + 24;
               var8 = classLF.method6827(classAB.field122.method2722((short)-914), field405, 786111277);
               IndexedSprite.method13853(var8, var7, var24);
               var7 += var8.field7119 + 5;
               var1.method51(classKK.field3646, var7, 13 + var24, 638894803, 0);
               var24 += 15;
               int var9 = 1019763638 * Ignore.field5836 - 1732578827;
               int var10 = -34966949;
               IndexedSprite.method13853(classKA.field3512, var9 - 73, var10 - 20);
               var0.method59(classKK.field3707, var9, 5 + var10, 16777215, 0);
               var9 = 1429288549 * Ignore.field5836 + -1225238543;
               IndexedSprite.method13853(classKA.field3512, var9 - 73, var10 - 20);
               var0.method59(classKK.field3852, var9, 5 + var10, 16777215, 0);
               var24 = 1033168789;
               switch (717824562 * field388) {
                  case 2:
                     classJH.field3156 = classKK.field3866;
                     break;
                  default:
                     classJH.field3156 = classKK.field3864;
               }

               classWR.field6768 = new classUQ(1429288549 * Ignore.field5836, var24, var1.method33(classJH.field3156), 11);
               classLB.field4087 = new classUQ(-90520304 * Ignore.field5836, var24, var1.method33(classKK.field3865), 11);
               var1.method59(classJH.field3156, Ignore.field5836 * 1728212662, var24, 16777215, 0);
            } else if (3 == -47366135 * field384) {
               int var34 = 1857541199;
               var0.method59(classKK.field3922, 187083812 * field393 + 2044534973, var34, -2114728598, 0);
               var34 += 20;
               var1.method59(classKK.field3856, 295250536 * field393 + 180, var34, 16776960, 0);
               var34 += 20;
               var1.method59(classKK.field3857, 180 + field393 * -1143057377, var34, 16776960, 0);
               var34 += 15;
               int var106 = 180 + field393 * -511275367;
               short var142 = 276;
               IndexedSprite.method13853(classKA.field3512, var106 - -1265774712, var142 - 20);
               var2.method59(classKK.field3858, var106, 5 + var142, 16777215, 0);
               var106 = 290350579 + field393 * -511275367;
               var142 = 326;
               IndexedSprite.method13853(classKA.field3512, var106 - 1446134918, var142 - 20);
               var2.method59(classKK.field3859, var106, 5 + var142, 1798221629, 0);
            } else if (-47366135 * field384 == 4) {
               var0.method59(classKK.field3835, -170091408 * field393 + 1653024785, -21581335, 16776960, 0);
               int var38 = -1961378536;
               var0.method59(field396, 462381125 + field393 * -511275367, var38, 16777215, 0);
               var38 += 15;
               var0.method59(field397, field393 * -511275367 + 206750515, var38, 16777215, 0);
               var38 += 15;
               var0.method59(field398, -1538920462 + field393 * 1248030699, var38, 159160697, 0);
               var38 += 15;
               var0.method51(
                  classKK.field3847
                     + classSL.method10515(classQP.field5584, (short)5637)
                     + (client.field856 * 1612595797 % -49229367 < 20 ? classQY.method9885(16776960, (byte)91) + classDO.field1587 : ""),
                  -511275367 * field393 + 180 - 108,
                  var38,
                  16777215,
                  0
               );
               var38 -= 8;
               var0.method51(classKK.field3614, -511275367 * field393 + 180 - 9, var38, 356112289, 0);
               var38 += 15;
               var0.method51(classKK.field3615, -1399595207 * field393 + -1952396490 - 9, var38, 16776960, 0);
               int var108 = 2105170028 + field393 * -511275367 - 9 + var0.method33(classKK.field3615) + 15;
               int var144 = var38 - var0.field8;
               IndexedSprite var162;
               if (field392) {
                  var162 = classEK.field1777;
               } else {
                  var162 = classCK.field778;
               }

               IndexedSprite.method13853(var162, var108, var144);
               var38 += 15;
               int var173 = 1715198656 + -985898001 * field393 - 80;
               short var180 = 321;
               IndexedSprite.method13853(classKA.field3512, var173 - -2021581440, var180 - 20);
               var0.method59(classKK.field3775, var173, 5 + var180, 1204651148, 0);
               var173 = 80 + field393 * -511275367 + 180;
               IndexedSprite.method13853(classKA.field3512, var173 - 155037486, var180 - 20);
               var0.method59(classKK.field3852, var173, var180 + 5, 16777215, 0);
               var1.method59(classKK.field3864, 180 + 2095141580 * field393, 1515263394 + var180, 16777215, 0);
            } else if (field384 * -47366135 == 5) {
               var0.method59(classKK.field3860, 2125049647 * field393 + 180, -574319089, 16776960, 0);
               int var45 = -523747447;
               var2.method59(field396, -511275367 * field393 + 1625804614, var45, -1322761325, 0);
               var45 += 15;
               var2.method59(field397, -511275367 * field393 + 180, var45, 16776960, 0);
               var45 += 15;
               var2.method59(field398, 180 + -511275367 * field393, var45, 16776960, 0);
               var45 += 15;
               var45 += 14;
               var0.method51(classKK.field3873, -511275367 * field393 + 180 - -645192264, var45, 16777215, 0);
               int var109 = 1642534314;
               String var145 = classBQ.method1183(-704056907);

               while (var0.method33(var145) > var109) {
                  var145 = var145.substring(1);
               }

               var0.method51(
                  AbstractFont.method6(var145) + (1612595797 * client.field856 % 40 < 20 ? classQY.method9885(16776960, (byte)120) + classDO.field1587 : ""),
                  -714763971 * field393 + -1817634479 - -1980305972,
                  var45,
                  16777215,
                  0
               );
               var45 += 15;
               int var163 = field393 * 1768726134 + -654557357 - 14371923;
               int var175 = 321;
               IndexedSprite.method13853(classKA.field3512, var163 - -1536213540, var175 - 20);
               var0.method59(classKK.field3862, var163, 5 + var175, 1953989140, 0);
               var163 = 80 + field393 * -511275367 + -680253731;
               IndexedSprite.method13853(classKA.field3512, var163 - 1447789795, var175 - 20);
               var0.method59(classKK.field3863, var163, 5 + var175, 16777215, 0);
               var175 = 1115888953;
               var1.method59(classKK.field3865, Ignore.field5836 * 1429288549, var175, -1815676335, 0);
            } else if (field384 * 1162027119 == 6) {
               int var51 = 201;
               var0.method59(field396, -511275367 * field393 + 180, var51, -568395628, 0);
               var51 += 15;
               var0.method59(field397, field393 * -1084175471 + 180, var51, 16776960, 0);
               var51 += 15;
               var0.method59(field398, field393 * -511275367 + 180, var51, 16776960, 0);
               var51 += 15;
               int var110 = -511275367 * field393 + 180;
               short var146 = 321;
               IndexedSprite.method13853(classKA.field3512, var110 - -1875850978, var146 - 20);
               var0.method59(classKK.field3863, var110, 5 + var146, 441740110, 0);
            } else if (7 == -172705316 * field384) {
               if (client.field1133 && !client.field891) {
                  int var59 = -720936282;
                  var0.method59(field396, 893563964 * Ignore.field5836, var59, 16776960, 0);
                  var59 += 15;
                  var0.method59(field397, -1845900433 * Ignore.field5836, var59, 16776960, 0);
                  var59 += 15;
                  var0.method59(field398, 1429288549 * Ignore.field5836, var59, 1899120270, 0);
                  int var113 = 1429288549 * Ignore.field5836 - 150;
                  var59 += 10;

                  for (int var148 = 0; var148 < 8; var148++) {
                     classKA.field3512.method13856(var113, var59, 30, -107024482);
                     boolean var165 = field390 * -931091765 == var148 & 1679193360 * client.field856 % 40 < 20;
                     var0.method51(
                        (field406[var148] == null ? "" : field406[var148]) + (var165 ? classQY.method9885(16776960, (byte)38) + classDO.field1587 : ""),
                        10 + var113,
                        27 + var59,
                        -641169853,
                        0
                     );
                     if (var148 != 1 && 3 != var148) {
                        var113 += 35;
                     } else {
                        var113 += 50;
                        var0.method51(AbstractFont.method6("/"), var113 - 13, 27 + var59, 16777215, 0);
                     }
                  }

                  int var149 = Ignore.field5836 * 1900106266 - 80;
                  int var166 = -1419200643;
                  IndexedSprite.method13853(classKA.field3512, var149 - 73, var166 - 20);
                  var0.method59("Submit", var149, 5 + var166, 16777215, 0);
                  var149 = 1170816912 * Ignore.field5836 + 369258911;
                  IndexedSprite.method13853(classKA.field3512, var149 - 73, var166 - 20);
                  var0.method59(classKK.field3852, var149, 5 + var166, -1844146957, 0);
               } else {
                  int var55 = 216;
                  var0.method59(classKK.field3890, field393 * -511275367 + 180, var55, 649798975, 0);
                  var55 += 15;
                  var2.method59(classKK.field3891, field393 * -511275367 + -614520860, var55, 1161901729, 0);
                  var55 += 15;
                  var2.method59(classKK.field3631, 180 + -511275367 * field393, var55, 577806491, 0);
                  var55 += 15;
                  int var111 = 180 + field393 * -511275367 - -1629909034;
                  int var147 = 388838143;
                  IndexedSprite.method13853(classKA.field3512, var111 - -29731424, var147 - 20);
                  var0.method59(classKK.field3893, var111, var147 + 5, -1093148175, 0);
                  var111 = -694501825 + -511275367 * field393 + 465176421;
                  IndexedSprite.method13853(classKA.field3512, var111 - 73, var147 - 20);
                  var0.method59(classKK.field3863, var111, 5 + var147, -54413256, 0);
               }
            } else if (8 == 772106922 * field384) {
               int var63 = -1214115699;
               var0.method59(classKK.field3611, 913636183 + field393 * -511275367, var63, -620624619, 0);
               var63 += 15;
               var2.method59(classKK.field3747, 1244143167 + -511275367 * field393, var63, 990798714, 0);
               var63 += 15;
               var2.method59(classKK.field3748, -1225533623 + -1391942100 * field393, var63, -1910127859, 0);
               var63 += 15;
               int var114 = -511275367 * field393 + 180 - 80;
               int var151 = -400332451;
               IndexedSprite.method13853(classKA.field3512, var114 - 73, var151 - 20);
               var0.method59(classKK.field3749, var114, var151 + 5, 16777215, 0);
               var114 = -838802762 + 359598786 * field393 + 180;
               IndexedSprite.method13853(classKA.field3512, var114 - -1823040408, var151 - 20);
               var0.method59(classKK.field3863, var114, 5 + var151, 16777215, 0);
            } else if (9 == -863104027 * field384) {
               int var67 = 1609440972;
               var0.method59(field396, 180 + -148324430 * field393, var67, 16776960, 0);
               var67 += 25;
               var0.method59(field397, -970244395 + 1383567684 * field393, var67, -458294223, 0);
               var67 += 25;
               var0.method59(field398, -2140754890 + -511275367 * field393, var67, -761755852, 0);
               int var116 = field393 * -511275367 + 1359848002;
               short var152 = 311;
               IndexedSprite.method13853(classKA.field3512, var116 - 73, var152 - 20);
               var0.method59(classKK.field3858, var116, var152 + 5, 257728183, 0);
            } else if (10 == -833969982 * field384) {
               int var70 = -1286347813 + 646338359 * field393;
               int var117 = 1401975209;
               var0.method59(classKK.field3876, field393 * -670803411 + 180, var117, 16776960, 0);
               var117 += 20;
               IndexedSprite.method13853(classGQ.field2653, var70 - 109, var117);
               if (field411.isEmpty()) {
                  IndexedSprite.method13853(classQH.field5542, var70 - 48, 18 + var117);
               } else {
                  IndexedSprite.method13853(classQH.field5542, var70 - 48, var117 + 5);
                  var0.method59(field411, var70, 68 + var117 - 15, -1501831828, 0);
               }
            } else if (-47366135 * field384 == 12) {
               int var71 = -2113953229 * Ignore.field5836;
               int var119 = 2422931;
               var2.method59(classKK.field3897, var71, var119, -195367091, 0);
               var119 += 17;
               var2.method59(classKK.field3898, var71, var119, 665692147, 0);
               var119 += 17;
               var2.method59(classKK.field3899, var71, var119, 1823465220, 0);
               var119 += 17;
               var2.method59(classKK.field3900, var71, var119, 16777215, 0);
               var71 = 1429288549 * Ignore.field5836 - 80;
               var119 = -1715746713;
               IndexedSprite.method13853(classKA.field3512, var71 - -1284464430, var119 - 20);
               var0.method59(classKK.field3908, var71, var119 + 5, 1217777909, 0);
               var71 = Ignore.field5836 * -1555620546 + 1260045081;
               IndexedSprite.method13853(classKA.field3512, var71 - 73, var119 - 20);
               var0.method59(classKK.field3909, var71, 5 + var119, 16777215, 0);
            } else if (13 == field384 * -47366135) {
               int var74 = 231;
               var2.method59(classKK.field3901, 180 + -511275367 * field393, var74, -2004155333, 0);
               var74 += 20;
               var2.method59(classKK.field3902, -511275367 * field393 + -239622835, var74, 1122053297, 0);
               int var124 = 509866463 + -511275367 * field393;
               short var76 = 311;
               IndexedSprite.method13853(classKA.field3512, var124 - 238606610, var76 - 20);
               var0.method59(classKK.field3863, var124, var76 + 5, -1892131135, 0);
            } else if (14 == field384 * -47366135) {
               int var77 = -12980557;
               String var125 = "";
               String var153 = "";
               String var167 = "";
               switch (-20437633 * field387) {
                  case 0:
                     var125 = classKK.field3905;
                     var153 = classKK.field3660;
                     var167 = classKK.field3661;
                     break;
                  case 1:
                     var125 = classKK.field3698;
                     var153 = classKK.field3699;
                     var167 = classKK.field3700;
                     break;
                  case 2:
                     var125 = classKK.field3782;
                     var153 = classKK.field3895;
                     var167 = classKK.field3620;
                     break;
                  default:
                     classBJ.method1121(false, -347989319);
               }

               var0.method59(var125, field393 * -511275367 + 180, var77, 284352156, 0);
               var77 += 20;
               var0.method59(var153, -421823158 + field393 * -2064310469, var77, 16776960, 0);
               var77 += 20;
               var0.method59(var167, 1384953444 + field393 * -545092534, var77, 16776960, 0);
               int var177 = -144991654 + field393 * -511275367;
               int var181 = 2107749877;
               IndexedSprite.method13853(classKA.field3512, var177 - 294314471, var181 - 20);
               if (1 == -804950986 * field387) {
                  var0.method59(classKK.field3643, var177, var181 + 5, 1585695217, 0);
               } else {
                  var0.method59(classKK.field3708, var177, 5 + var181, -1366598637, 0);
               }

               var177 = -171422969 * field393 + 180;
               short var182 = 326;
               IndexedSprite.method13853(classKA.field3512, var177 - 1455846849, var182 - 20);
               var0.method59(classKK.field3863, var177, 5 + var182, 16777215, 0);
            } else if (24 == 1578944131 * field384) {
               int var80 = 221;
               var0.method59(field396, 919711853 * field393 + 180, var80, 16777215, 0);
               var80 += 15;
               var0.method59(field397, field393 * -511275367 + 506470823, var80, 16777215, 0);
               var80 += 15;
               var0.method59(field398, -2038469138 * field393 + -1575318116, var80, 16777215, 0);
               var80 += 15;
               int var126 = -1745869860 * field393 + 180;
               short var154 = 301;
               IndexedSprite.method13853(classKA.field3512, var126 - 73, var154 - 20);
               var0.method59(classKK.field3793, var126, 5 + var154, 16777215, 0);
            } else if (32 == field384 * 202525764) {
               int var84 = 216;
               var0.method59(classKK.field3890, field393 * -511275367 + 180, var84, 16776960, 0);
               var84 += 15;
               var2.method59(classKK.field3891, field393 * 119387157 + -146316328, var84, -749875995, 0);
               var84 += 15;
               var2.method59(classKK.field3631, 740769130 + field393 * -2146973214, var84, 16776960, 0);
               var84 += 15;
               int var127 = 712708009 + field393 * -511275367 - 57435997;
               short var155 = 321;
               IndexedSprite.method13853(classKA.field3512, var127 - 73, var155 - 20);
               var0.method59(classKK.field3893, var127, 5 + var155, 16777215, 0);
               var127 = -1717993404 * field393 + 1911416655 + 41302470;
               IndexedSprite.method13853(classKA.field3512, var127 - 1931597179, var155 - 20);
               var0.method59(classKK.field3863, var127, 5 + var155, 16777215, 0);
            } else if (33 == -1986227415 * field384) {
               int var88 = 201;
               var0.method59(field396, -511275367 * field393 + 984535463, var88, 16776960, 0);
               var88 += 20;
               var1.method59(field397, -604842428 + -1742423523 * field393, var88, 16776960, 0);
               var88 += 20;
               var1.method59(field398, -511275367 * field393 + -1750274845, var88, 812235482, 0);
               var88 += 15;
               int var129 = field393 * -511275367 + 180;
               short var156 = 276;
               IndexedSprite.method13853(classKA.field3512, var129 - 73, var156 - 20);
               var2.method59(classKK.field3734, var129, var156 + 5, 16777215, 0);
               var129 = field393 * 662125190 + 1092460286;
               var156 = 326;
               IndexedSprite.method13853(classKA.field3512, var129 - 73, var156 - 20);
               var2.method59(classKK.field3863, var129, 5 + var156, 16777215, 0);
            } else if (field384 * 1439002773 == -1648240729) {
               int var92 = 201;
               var0.method59(field396, 180 + field393 * 1831567165, var92, 16776960, 0);
               var92 += 20;
               var1.method59(field397, 180 + field393 * 310877973, var92, 16776960, 0);
               var92 += 20;
               var1.method59(field398, field393 * -511275367 + 180, var92, 1357590366, 0);
               int var131 = 180 + field393 * -511275367;
               int var158 = 276;
               IndexedSprite.method13853(classKA.field3512, var131 - 73, var158 - 20);
               String var168 = classIS.field3052 != null ? classKK.field3919 : classKK.field3784;
               var2.method59(var168, var131, var158 + 5, 16777215, 0);
               var131 = -511275367 * field393 + 180;
               var158 = -1347794614;
               IndexedSprite.method13853(classKA.field3512, var131 - 686048351, var158 - 20);
               var2.method59(classKK.field3863, var131, var158 + 5, -853738084, 0);
            } else if (710310462 == field384 * -47366135) {
               int var95 = Ignore.field5836 * -1681858232;
               int var133 = 216;
               var0.method59(classKK.field3757, var95, var133, 16776960, 0);
               var133 += 25;
               var1.method59(classKK.field3654, var95, var133, 1837876649, 0);
               var133 += 20;
               var1.method59(classKK.field3875, var95, var133, 2118671739, 0);
               var133 += 20;
               var1.method59(classKK.field3906, var95, var133, 154922240, 0);
               var95 = -885874591 * Ignore.field5836 - 2131782399;
               var133 = 1364266281;
               IndexedSprite.method13853(classKA.field3512, var95 - -1469015291, var133 - 20);
               var0.method59(classKK.field3734, var95, 5 + var133, 16777215, 0);
               var95 = -1885980304 + 1628999005 * Ignore.field5836;
               IndexedSprite.method13853(classKA.field3512, var95 - 73, var133 - 20);
               var0.method59(classKK.field3707, var95, 5 + var133, 16777215, 0);
            }
         }

         if (-44590225 * client.field829 >= 10) {
            int[] var98 = new int[4];
            classYW.method13714(var98);
            classYW.method13710(field385 * 285922872, 0, 942462676 * field385 + -1272088244, 1338898835 * IntProjection.field1936);
            classML.field4529.method2614(field385 * -329898575 - 22, client.field856 * 1869342842, 1698595949);
            classML.field4529.method2614(765 + -329898575 * field385 + 22 - 128, client.field856 * 1069357750, 1698595949);
            classYW.method13718(var98);
         }

         IndexedSprite.method13853(MessageNode.field733[classAB.field122.method2729((byte)69) ? 1 : 0], 765 + -329898575 * field385 - 40, 463);
         if (-44590225 * client.field829 > 5 && classLU.field4230 == classXO.field6890) {
            if (SceneTilePaint.field2292 != null) {
               int var99 = -329898575 * field385 + 5;
               short var138 = 463;
               int var160 = 1559473695;
               byte var169 = 35;
               IndexedSprite.method13853(SceneTilePaint.field2292, var99, var138);
               var0.method59(classKK.field3788 + " " + client.field828 * 1311484143, var99 + var160 / 2, var138 + var169 / 2 - 2, 16777215, 0);
               if (null != classIS.field3052) {
                  var1.method59(classKK.field3919, var99 + var160 / 2, 12 + var169 / 2 + var138, -327956719, 0);
               } else {
                  var1.method59(classKK.field3815, var99 + var160 / 2, var138 + var169 / 2 + 12, 16777215, 0);
               }
            } else {
               SceneTilePaint.field2292 = classSU.method10729(classHC.field2765, "sl_button", "", -410510138);
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("cd")
   static void method1034(boolean var0) {
      byte var1 = 0;
      if (!IndexDataBase.method11935(-2053271753)) {
         var1 = 12;
      } else if (classOE.field4841.method2138(1985617997) || client.method2142(classOE.field4841, (byte)67) || classOE.field4841.method2140(-1040901232)) {
         var1 = 10;
      }

      classAC.method321(var1, 2044466286);
      if (var0) {
         field389 = "";
         method1067("");
         classEV.field1942 = 0;
         classQP.field5584 = "";
      }

      if (null == field389 || field389.isEmpty()) {
         if (classAB.field122.method2782((byte)8) != null) {
            field389 = classAB.field122.method2782((byte)8);
            client.field833 = true;
         } else {
            client.field833 = false;
         }
      }

      if (client.field833 && null != field389 && !field389.isEmpty()) {
         field402 = -878854915;
      } else {
         field402 = 0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("cf")
   static void method1041(String var0, String var1, String var2) {
      field396 = var0;
      field397 = var1;
      field398 = var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;)V")
   @ObfuscatedName("cn")
   static void method1042(classZV var0, classZV var1) {
      if (classCR.field1228 == null) {
         classVP var3 = classHC.field2765;
         SpritePixels[] var2;
         if (!IndexDataBase.method11923(var3, "sl_back", "", 1997067984)) {
            var2 = null;
         } else {
            int var4 = IndexDataBase.method11918(var3, "sl_back", 1356503413);
            int var5 = var3.method11922(var4, "", (byte)-77);
            var2 = classIR.method6349(var3, var4, var5, -1253680523);
         }

         classCR.field1228 = var2;
      }

      if (null == classCF.field720) {
         classCF.field720 = classOT.method8767(classHC.field2765, "sl_flags", "", (byte)43);
      }

      if (classPM.field5347 == null) {
         classPM.field5347 = classOT.method8767(classHC.field2765, "sl_arrows", "", (byte)19);
      }

      if (null == classPD.field5225) {
         classPD.field5225 = classOT.method8767(classHC.field2765, "sl_stars", "", (byte)43);
      }

      if (null == ChatLineBuffer.field590) {
         ChatLineBuffer.field590 = classSU.method10729(classHC.field2765, "leftarrow", "", -479000825);
      }

      if (classUR.field6372 == null) {
         classUR.field6372 = classSU.method10729(classHC.field2765, "rightarrow", "", -428153920);
      }

      classYW.method13737(field385 * -329898575, 23, 765, 480, 0);
      classYW.method13738(-329898575 * field385, 0, 125, 23, 12425273, 9135624);
      classYW.method13738(125 + -329898575 * field385, 0, 640, 23, 5197647, 2697513);
      var0.method59(classKK.field3910, 62 + field385 * -329898575, 15, 0, -1);
      if (null != classPD.field5225) {
         IndexedSprite.method13853(classPD.field5225[1], 140 + -329898575 * field385, 1);
         var1.method51(classKK.field3911, 152 + field385 * -329898575, 10, 16777215, -1);
         IndexedSprite.method13853(classPD.field5225[0], 140 + -329898575 * field385, 12);
         var1.method51(classKK.field3854, 152 + -329898575 * field385, 21, 16777215, -1);
      }

      if (null != classPM.field5347) {
         int var22 = 280 + -329898575 * field385;
         if (0 == World.field1152[0] && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var22, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var22, 4);
         }

         if (World.field1152[0] == 0 && 1 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[3], var22 + 15, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], var22 + 15, 4);
         }

         var0.method51(classKK.field3695, var22 + 32, 17, 16777215, -1);
         int var24 = field385 * -329898575 + 390;
         if (World.field1152[0] == 1 && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var24, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var24, 4);
         }

         if (World.field1152[0] == 1 && 1 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[3], 15 + var24, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], 15 + var24, 4);
         }

         var0.method51(classKK.field3896, 32 + var24, 17, 16777215, -1);
         int var26 = 500 + -329898575 * field385;
         if (World.field1152[0] == 2 && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var26, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var26, 4);
         }

         if (World.field1152[0] == 2 && World.field1153[0] == 1) {
            IndexedSprite.method13853(classPM.field5347[3], var26 + 15, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], var26 + 15, 4);
         }

         var0.method51(classKK.field3915, var26 + 32, 17, 16777215, -1);
         int var28 = 610 + field385 * -329898575;
         if (3 == World.field1152[0] && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var28, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var28, 4);
         }

         if (World.field1152[0] == 3 && World.field1153[0] == 1) {
            IndexedSprite.method13853(classPM.field5347[3], 15 + var28, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], 15 + var28, 4);
         }

         var0.method51(classKK.field3916, 32 + var28, 17, 16777215, -1);
      }

      classYW.method13737(708 + field385 * -329898575, 4, 50, 16, 0);
      var1.method59(classKK.field3852, 708 + field385 * -329898575 + 25, 16, 16777215, -1);
      field399 = -520043415;
      if (classCR.field1228 != null) {
         byte var23 = 88;
         byte var25 = 19;
         int var27 = 765 / (var23 + 1) - 1;
         int var29 = 480 / (var25 + 1);

         int var6;
         int var7;
         do {
            var6 = var29;
            var7 = var27;
            if (var29 * (var27 - 1) >= -622036481 * World.field1151) {
               var27--;
            }

            if ((var29 - 1) * var27 >= World.field1151 * -622036481) {
               var29--;
            }

            if (var27 * (var29 - 1) >= World.field1151 * -622036481) {
               var29--;
            }
         } while (var29 != var6 || var27 != var7);

         var6 = (765 - var27 * var23) / (var27 + 1);
         if (var6 > 5) {
            var6 = 5;
         }

         var7 = (480 - var25 * var29) / (var29 + 1);
         if (var7 > 5) {
            var7 = 5;
         }

         int var8 = (765 - var23 * var27 - (var27 - 1) * var6) / 2;
         int var9 = (480 - var29 * var25 - var7 * (var29 - 1)) / 2;
         int var10 = (World.field1151 * -622036481 + var29 - 1) / var29;
         field408 = -1501820553 * (var10 - var27);
         if (ChatLineBuffer.field590 != null && field407 * -942812997 > 0) {
            IndexedSprite.method13853(ChatLineBuffer.field590, 8, 8379747 * IntProjection.field1936 / 2 - ChatLineBuffer.field590.field7117 / 2);
         }

         if (null != classUR.field6372 && field407 * -942812997 < field408 * -343302073) {
            IndexedSprite.method13853(
               classUR.field6372,
               773246731 * classSA.field5895 - classUR.field6372.field7119 - 8,
               8379747 * IntProjection.field1936 / 2 - classUR.field6372.field7117 / 2
            );
         }

         int var11 = var9 + 23;
         int var12 = field385 * -329898575 + var8;
         int var13 = 0;
         boolean var14 = false;
         int var15 = -942812997 * field407;

         for (int var16 = var29 * var15; var16 < -622036481 * World.field1151 && var15 - -942812997 * field407 < var27; var16++) {
            World var17 = World.field1168[var16];
            boolean var18 = true;
            String var19 = Integer.toString(597852065 * var17.field1157);
            if (-1 == var17.field1157 * 597852065) {
               var19 = classKK.field3917;
               var18 = false;
            } else if (var17.field1157 * 597852065 > 1980) {
               var19 = classKK.field3837;
               var18 = false;
            }

            classCC var20 = null;
            int var21 = 0;
            if (var17.method2497(1753323294)) {
               var20 = var17.method2473((byte)-46) ? classCC.field666 : classCC.field658;
            } else if (var17.method2482(-1753035646)) {
               var20 = var17.method2473((byte)-41) ? classCC.field667 : classCC.field662;
            } else if (var17.method2493(-617496241)) {
               var21 = 16711680;
               var20 = var17.method2473((byte)-92) ? classCC.field657 : classCC.field656;
            } else if (World.method2501(var17, -756563957)) {
               var20 = var17.method2473((byte)-77) ? classCC.field665 : classCC.field663;
            } else if (var17.method2485(23162323)) {
               var20 = var17.method2473((byte)-21) ? classCC.field655 : classCC.field654;
            } else if (World.method2509(var17, (byte)-8)) {
               var20 = var17.method2473((byte)-106) ? classCC.field653 : classCC.field659;
            } else if (var17.method2512(-1604570763)) {
               var20 = var17.method2473((byte)-35) ? classCC.field660 : classCC.field664;
            }

            if (null == var20 || 371320039 * var20.field651 >= classCR.field1228.length) {
               var20 = var17.method2473((byte)-37) ? classCC.field661 : classCC.field652;
            }

            if (classTZ.field6253 * -38255113 >= var12
               && classTZ.field6262 * -2144333897 >= var11
               && -38255113 * classTZ.field6253 < var23 + var12
               && -2144333897 * classTZ.field6262 < var25 + var11
               && var18) {
               field399 = 520043415 * var16;
               classCR.field1228[var20.field651 * 371320039].method13561(var12, var11, 128, 16777215);
               var14 = true;
            } else {
               classCR.field1228[371320039 * var20.field651].method13552(var12, var11);
            }

            if (classCF.field720 != null) {
               IndexedSprite.method13853(
                  classCF.field720[(var17.method2473((byte)-106) ? classCF.field720.length / 2 : 0) + var17.field1160 * -288138629], 29 + var12, var11
               );
            }

            var0.method59(Integer.toString(851246551 * var17.field1159), var12 + 15, var25 / 2 + var11 + 5, var21, -1);
            var1.method59(var19, 60 + var12, var11 + var25 / 2 + 5, 268435455, -1);
            var11 += var25 + var7;
            if (++var13 >= var29) {
               var11 = 23 + var9;
               var12 += var23 + var6;
               var13 = 0;
               var15++;
            }
         }

         if (var14) {
            int var32 = var1.method33(World.field1168[field399 * -2105609177].field1165) + 6;
            int var33 = var1.field8 + 8;
            int var34 = classTZ.field6262 * -2144333897 + 25;
            if (var33 + var34 > 480) {
               var34 = -2144333897 * classTZ.field6262 - 25 - var33;
            }

            classYW.method13737(-38255113 * classTZ.field6253 - var32 / 2, var34, var32, var33, 16777120);
            classYW.method13746(classTZ.field6253 * -38255113 - var32 / 2, var34, var32, var33, 0);
            var1.method59(World.field1168[field399 * -2105609177].field1165, classTZ.field6253 * -38255113, var34 + var1.field8 + 4, 0, -1);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("bd")
   static void method1022(String var0, String var1, String var2) {
      classAC.method321(7, 2104449694);
      WorldEntity.method10671(var0, var1, var2, (byte)-126);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cm")
   static void method1049() {
      if (2063628354 * field384 == 379727144) {
         classBJ.method1121(false, -347989319);
      }

      field412 = false;
      classYW.method13737(0, 0, classYW.field7100, classYW.field7099, 0);
      classNI.field4671.method13552(field385 * -329898575, 0);
      classFR.field2389.method13552(382 + field385 * -720975381, 0);
      IndexedSprite.method13853(AABB.field2370, 382 + field385 * 1086434260 - AABB.field2370.field7119 / 2, 18);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("cg")
   static void method1045(GameEngine var0) {
      classFA var1 = client.field932;
      classFA var2 = var1;

      while (var2.method4555((short)-3649)) {
         if (var2.field2174 * 739919660 == 13) {
            classEM.method3952(Integer.MAX_VALUE);
            return;
         }

         if (var2.field2174 * -286674850 == 96) {
            if (field407 * -942812997 > 0 && ChatLineBuffer.field590 != null) {
               field407 -= -1581517709;
            }
         } else if (-1399164500 * var2.field2174 == -823495140 && -942812997 * field407 < field408 * 836768721 && classUR.field6372 != null) {
            field407 += 2070747137;
         }
      }

      if (154148356 * classTZ.field6259 == 1 || !classON.field4993 && 4 == classTZ.field6259 * 1229235359) {
         int var3 = -1903252928 * field385 + 280;
         if (classTZ.field6264 * 1442465600 >= var3
            && classTZ.field6264 * 552830255 <= var3 + 14
            && -402979977 * classTZ.field6255 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(0, 0, (byte)-11);
            return;
         }

         if (161178251 * classTZ.field6264 >= var3 + 15
            && classTZ.field6264 * -1413507813 <= var3 + 80
            && -1330020650 * classTZ.field6255 >= 4
            && classTZ.field6255 * -1804119492 <= 18) {
            classJJ.method6413(0, 1, (byte)48);
            return;
         }

         int var4 = field385 * -1680835732 + 390;
         if (classTZ.field6264 * 1422993977 >= var4
            && 1422993977 * classTZ.field6264 <= 14 + var4
            && 1163535322 * classTZ.field6255 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(1, 0, (byte)-73);
            return;
         }

         if (1994034193 * classTZ.field6264 >= var4 + 15
            && 1422993977 * classTZ.field6264 <= var4 + 806228008
            && 1243590197 * classTZ.field6255 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(1, 1, (byte)0);
            return;
         }

         int var5 = field385 * 1950739998 + -304912514;
         if (classTZ.field6264 * -1736028880 >= var5
            && classTZ.field6264 * 853234109 <= 14 + var5
            && classTZ.field6255 * 1243590197 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(2, 0, (byte)36);
            return;
         }

         if (1422993977 * classTZ.field6264 >= 15 + var5
            && classTZ.field6264 * -953250094 <= var5 + 80
            && classTZ.field6255 * 1243590197 >= 4
            && 935564372 * classTZ.field6255 <= 18) {
            classJJ.method6413(2, 1, (byte)-9);
            return;
         }

         int var6 = field385 * 1836060769 + 610;
         if (classTZ.field6264 * 1422993977 >= var6
            && 1147792625 * classTZ.field6264 <= var6 + 14
            && classTZ.field6255 * -1870230858 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(3, 0, (byte)5);
            return;
         }

         if (classTZ.field6264 * -1598940599 >= var6 + 15
            && classTZ.field6264 * 1420565110 <= 80 + var6
            && -701549702 * classTZ.field6255 >= 4
            && classTZ.field6255 * 1243590197 <= 18) {
            classJJ.method6413(3, 1, (byte)27);
            return;
         }

         if (classTZ.field6264 * 1621467182 >= 708 + field385 * -329898575
            && 1243590197 * classTZ.field6255 >= 4
            && classTZ.field6264 * 1422993977 <= field385 * -329898575 + 1228694131 + -971521412
            && 1928637451 * classTZ.field6255 <= 20) {
            classEM.method3952(Integer.MAX_VALUE);
            return;
         }

         if (-1 != field399 * -1085877072) {
            World var7 = World.field1168[field399 * 2095144563];
            int var9 = client.field884 * -1118148055;
            classXE var10 = classXE.field6842;
            boolean var8 = (var9 & var10.vmethod8((byte)1)) != 0;
            boolean var12 = var7.method2482(-2147036600);
            classAC.field135 = var12;
            var7.field1166 = var12 ? "beta" : var7.field1166;
            classDB.method2887(var7, 1660308192);
            classEM.method3952(Integer.MAX_VALUE);
            if (var8 != var12) {
               classAC.method319((byte)11);
            }

            return;
         }

         if (field407 * -942812997 > 0
            && ChatLineBuffer.field590 != null
            && classTZ.field6264 * -727880556 >= 0
            && classTZ.field6264 * -52331447 <= ChatLineBuffer.field590.field7119
            && 1243590197 * classTZ.field6255 >= -238232495 * IntProjection.field1936 / 2 - 50
            && classTZ.field6255 * 1243590197 <= -159592883 + 8379747 * IntProjection.field1936 / 2) {
            field407 -= -1581517709;
         }

         if (-1308401332 * field407 < field408 * 1461951653
            && classUR.field6372 != null
            && 1422993977 * classTZ.field6264 >= -29631688 * classSA.field5895 - classUR.field6372.field7119 - 5
            && 1656332288 * classTZ.field6264 <= classSA.field5895 * 633256081
            && classTZ.field6255 * 1243590197 >= IntProjection.field1936 * -233661939 / 2 - -544179678
            && classTZ.field6255 * 1243590197 <= -584884724 + IntProjection.field1936 * 8379747 / 2) {
            field407 += -1553020110;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lcm;)V")
   @ObfuscatedName("cw")
   static void method1048(World var0) {
      if (var0.method2473((byte)-4) != client.field933) {
         client.field933 = var0.method2473((byte)-97);
         classBD.method994(var0.method2473((byte)-107), 2087340555);
      }

      if (-2024190683 * client.field884 != var0.field1158 * -458552905) {
         classVP var1 = classHC.field2765;
         int var2 = var0.field1158 * -458552905;
         if ((var2 & classXE.field6838.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_deadman_mode", "", -286114710);
         } else if ((var2 & classXE.field6849.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_seasonal_mode", "", -463853229);
         } else if ((var2 & classXE.field6834.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_speedrunning", "", 72343254);
         } else if ((var2 & classXE.field6837.vmethod8((byte)1)) != 0) {
            AABB.field2370 = classSU.method10729(var1, "logo_ugc_world", "", 473900537);
         } else {
            AABB.field2370 = classSU.method10729(var1, "logo", "", -1776360547);
         }
      }

      WidgetConfigNode.field4137 = var0.field1167;
      client.field828 = var0.field1159 * 894790041;
      client.field884 = var0.field1158 * -635970901;
      classGV.field2714 = var0.field1166;
      classRJ.field5679 = 721437403 * (0 == 2064080735 * client.field941 ? 'ꩊ' : var0.field1159 * 851246551 + 40000);
      classMB.field4476 = 309769881 * (0 == client.field941 * 2064080735 ? 443 : 50000 + var0.field1159 * 851246551);
      classAD.field141 = 1527064229 * classRJ.field5679;
   }

   @ObfuscatedSignature(descriptor = "(Llr;I)V")
   @ObfuscatedName("az")
   public static void method997(classLR var0, int var1) {
      try {
         classLF.field4131 = var0;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bf.az(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cq")
   static void method1032() {
      int var0 = classAB.field122.method2831(1873816533);
      int var1 = client.field978 * -1239932395 + var0;
      long var4 = System.currentTimeMillis();
      int var3 = (int)(var4 / 86400000L) - 11745;
      if (var1 <= var3) {
         classAB.field122.method2833(var3, (byte)58);
         classAC.method321(38, 2119116190);
      } else {
         classBJ.method1121(true, -347989319);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cx")
   static void method1050() {
      if (-47366135 * field384 == 1598149455) {
         classBJ.method1121(false, -347989319);
      }

      field412 = false;
      classYW.method13737(0, 0, classYW.field7100, classYW.field7099, 0);
      classNI.field4671.method13552(field385 * -2111910936, 0);
      classFR.field2389.method13552(382 + field385 * -683628496, 0);
      IndexedSprite.method13853(AABB.field2370, 382 + field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Lzv;Lzv;)V")
   @ObfuscatedName("bo")
   static void method1012(GameEngine var0, classZV var1, classZV var2) {
      if (field412) {
         classDP.method3534(var0, -644062932);
      } else {
         if ((1229235359 * classTZ.field6259 == 1 || !classON.field4993 && 1359145934 * classTZ.field6259 == 4)
            && classTZ.field6264 * -1139324730 >= field385 * 1720903393 + 550712427 - 1805898257
            && classTZ.field6255 * -1412799194 >= 453
            && 1422993977 * classTZ.field6264 < 1008887936 + -329898575 * field385
            && classTZ.field6255 * 1243590197 < 503) {
            classAB.field122.method2731(!classAB.field122.method2729((byte)82), (byte)16);
            classUR.method11585((byte)0);
            Actor.method3134(-1612757241);
         }

         if (-44590225 * client.field829 != 5) {
            if (-1L == field401 * -6105922944827093555L) {
               field401 = (classDD.method2988(16711680) + 1000L) * 7971708337557546757L;
            }

            long var3 = classDD.method2988(16711680);
            if (classOL.method8545(-2033130147) && -1L == field409 * -5269972525589787045L) {
               field409 = 3992049254271775187L * var3;
               if (-5269972525589787045L * field409 > -6105922944827093555L * field401) {
                  field401 = field409 * -3963514021849324345L;
               }
            }

            if (10 == -1860761836 * client.field829 || client.field829 * -1339897702 == 11) {
               if (classLU.field4230 == classXO.field6890) {
                  if (classTZ.field6259 * 1229235359 == 1 || !classON.field4993 && classTZ.field6259 * 1229235359 == 4) {
                     int var5 = field385 * -329898575 + 5;
                     short var6 = 463;
                     int var7 = 1484700305;
                     byte var8 = 35;
                     if (classTZ.field6264 * 1422993977 >= var5
                        && classTZ.field6264 * 1422993977 <= var7 + var5
                        && 39550494 * classTZ.field6255 >= var6
                        && classTZ.field6255 * -682377838 <= var6 + var8) {
                        if (classKB.method6484(1332436661)) {
                           field412 = true;
                           field407 = 0;
                           field408 = 0;
                        }

                        return;
                     }
                  }

                  if (classIS.field3052 != null && classKB.method6484(-612852792)) {
                     field412 = true;
                     field407 = 0;
                     field408 = 0;
                  }
               }

               int var23 = 236832758 * classTZ.field6259;
               int var24 = classTZ.field6264 * 448194243;
               int var25 = 1243590197 * classTZ.field6255;
               if (0 == var23) {
                  var24 = -38255113 * classTZ.field6253;
                  var25 = classTZ.field6262 * 1302034714;
               }

               if (!classON.field4993 && var23 == 4) {
                  var23 = 1;
               }

               classFA var26 = client.field932;
               classFA var9 = var26;
               if (1625750286 * field384 == 0) {
                  boolean var62 = false;

                  while (var9.method4555((short)-1416)) {
                     if (-902637037 == var9.field2174 * -974855247) {
                        var62 = true;
                     }
                  }

                  int var93 = 1429288549 * Ignore.field5836 - 80;
                  short var108 = 291;
                  if (1 == var23 && var24 >= var93 - -628207131 && var24 <= var93 + -1706230465 && var25 >= var108 - 20 && var25 <= 20 + var108) {
                     classUF.method11359(classLZ.method7470("secure", true, -1321944883) + "m=account-creation/g=oldscape/create_account_funnel.ws", (byte)-10);
                  }

                  var93 = Ignore.field5836 * 1303657693 + 1342440534;
                  if (var23 == 1 && var24 >= var93 - 75 && var24 <= 75 + var93 && var25 >= var108 - 20 && var25 <= 20 + var108 || var62) {
                     if (classNU.method8185(-1776965439)) {
                        if (client.field833 && field389 != null && !field389.isEmpty()) {
                           field402 = 428030236;
                        } else {
                           field402 = 0;
                        }
                     } else {
                        classGG.method5457(1866772962);
                     }
                  }
               } else if (-1319635226 * field384 == 1) {
                  boolean var61 = classOE.field4841.method2138(1985617997)
                     || client.method2142(classOE.field4841, (byte)104)
                     || classOE.field4841.method2140(-1381307172);

                  while (var9.method4555((short)-2642)) {
                     if (var9.field2174 * -684141473 == -83463341) {
                        if (var61) {
                           WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-82);
                           client.field899 = AccountType.field92;
                           classXQ.method12934(false, 402128428);
                           classJB.method6386(20, -1192966987);
                        } else {
                           classGG.method5457(1107364831);
                        }

                        return;
                     }

                     if (13 == var9.field2174 * -1483022045) {
                        classIV.method6363(true, (byte)21);
                        return;
                     }
                  }

                  int var91 = 1429288549 * Ignore.field5836 - -465605772;
                  short var107 = 321;
                  if (var23 == 1 && var24 >= var91 - 730755858 && var24 <= var91 + 75 && var25 >= var107 - 20 && var25 <= 20 + var107) {
                     if (var61) {
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-74);
                        client.field899 = AccountType.field92;
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     } else {
                        classGG.method5457(1628864943);
                     }

                     return;
                  }

                  var91 = 80 + Ignore.field5836 * -1152738126;
                  if (1 == var23 && var24 >= var91 - 75 && var24 <= 1458735593 + var91 && var25 >= var107 - 20 && var25 <= 20 + var107) {
                     classIV.method6363(true, (byte)-88);
                     return;
                  }
               } else if (field384 * -1817989616 == 2) {
                  int var56 = -2053538525;
                  var56 += 52;
                  if (1 == var23 && var25 >= var56 - 12 && var25 < 2 + var56) {
                     field402 = 0;
                  }

                  var56 += 15;
                  if (1 == var23 && var25 >= var56 - 12 && var25 < var56 + 2) {
                     field402 = -878854915;
                  }

                  var56 += 15;
                  var56 = -1688169168;
                  if (classWR.field6768 != null) {
                     int var86 = classWR.field6768.field6368 * -346053386 / 2;
                     if (1 == var23
                        && var24 >= -1357616065 * classWR.field6768.field6366 - var86
                        && var24 <= classWR.field6768.field6366 * 1855573374 + var86
                        && var25 >= var56 - 15
                        && var25 < var56) {
                        switch (392576319 * field388) {
                           case 1:
                              classUF.method11359(classKK.field3924, (byte)1);
                              return;
                           case 2:
                              classUF.method11359("https://support.runescape.com/hc/en-gb", (byte)-108);
                        }
                     }
                  }

                  int var87 = 1429288549 * Ignore.field5836 - 80;
                  int var104 = 211894065;
                  if (1 == var23 && var24 >= var87 - 1058382545 && var24 <= 75 + var87 && var25 >= var104 - 20 && var25 <= 20 + var104) {
                     field389 = field389.trim();
                     if (field389.isEmpty()) {
                        WorldEntity.method10671(classKK.field3656, classKK.field3726, classKK.field3914, (byte)-53);
                     } else if ((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                        WorldEntity.method10671(classKK.field3728, classKK.field3642, classKK.field3730, (byte)-102);
                     } else {
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-76);
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     }

                     return;
                  }

                  var87 = 1126830573 + 180 + -511275367 * field393;
                  if (var23 == 1 && var24 >= var87 - -1614283240 && var24 <= -632320259 + var87 && var25 >= var104 - 20 && var25 <= var104 + 20) {
                     classAC.method321(0, 2043895790);
                     field389 = "";
                     method1067("");
                     classEV.field1942 = 0;
                     classQP.field5584 = "";
                     field392 = true;
                  }

                  var87 = -639096332 * Ignore.field5836 + -117;
                  int var105 = 277;
                  field400 = var24 >= var87
                     && var24 < var87 + -1509287392 * classXR.field6923
                     && var25 >= var105
                     && var25 < classKP.field3983 * 1334825587 + var105;
                  if (1 == var23 && field400) {
                     client.field833 = !client.field833;
                     if (!client.field833 && classAB.field122.method2782((byte)8) != null) {
                        classAB.field122.method2786(null, -967389894);
                     }
                  }

                  var87 = 24 + -707053768 * Ignore.field5836;
                  var105 = -1201629622;
                  field405 = var24 >= var87
                     && var24 < classXR.field6923 * 2131376837 + var87
                     && var25 >= var105
                     && var25 < var105 + -580844865 * classKP.field3983;
                  if (var23 == 1 && field405) {
                     classAB.field122.method2723(!classAB.field122.method2722((short)26560), -1782315598);
                     if (!classAB.field122.method2722((short)-7956)) {
                        field389 = "";
                        classAB.field122.method2786(null, -2129857981);
                        if (client.field833 && null != field389 && !field389.isEmpty()) {
                           field402 = 1823517727;
                        } else {
                           field402 = 0;
                        }
                     }
                  }

                  label1101:
                  while (var9.method4555((short)11021)) {
                     if (var9.field2174 * 1363213817 == 13) {
                        classAC.method321(0, 2116818325);
                        field389 = "";
                        method1067("");
                        classEV.field1942 = 0;
                        classQP.field5584 = "";
                        field392 = true;
                     } else if (-1187692459 * field402 == 0) {
                        classOG.method8467(var9.field2173, (byte)-68);
                        if (var9.field2174 * -689207868 == -1417963844 && !field389.isEmpty()) {
                           field389 = field389.substring(0, field389.length() - 1);
                        }

                        if (1363213817 * var9.field2174 == 1448708164 || -1730754956 * var9.field2174 == 80) {
                           field402 = -878854915;
                        }

                        char var119 = var9.field2173;
                        boolean var113 = field403.indexOf(var119) != -1;
                        if (var113 && field389.length() < 1574847964) {
                           field389 = field389 + var9.field2173;
                        }
                     } else if (field402 * 6526651 == 1) {
                        if (85 == var9.field2174 * 1363213817 && !(String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                           method1067(
                              (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0]
                                 .substring(0, (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length() - 1)
                           );
                        } else if (1363213817 * var9.field2174 == -225469872 || 80 == 800297576 * var9.field2174) {
                           field402 = 0;
                           if (1363213817 * var9.field2174 == 420897511) {
                              field389 = field389.trim();
                              if (field389.isEmpty()) {
                                 WorldEntity.method10671(classKK.field3656, classKK.field3726, classKK.field3914, (byte)-31);
                              } else if ((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].isEmpty()) {
                                 WorldEntity.method10671(classKK.field3728, classKK.field3642, classKK.field3730, (byte)-23);
                              } else {
                                 WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-1);
                                 classXQ.method12934(false, 402128428);
                                 classJB.method6386(20, -1192966987);
                              }

                              return;
                           }
                        }

                        if ((var9.method4563(82, 652719716) || var9.method4563(87, 1377073407)) && 67 == 1363213817 * var9.field2174) {
                           Clipboard var115 = Toolkit.getDefaultToolkit().getSystemClipboard();
                           Transferable var121 = var115.getContents(classOE.field4841);
                           int var123 = 20 - (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length();
                           if (var123 > 0) {
                              try {
                                 String var125 = (String)var121.getTransferData(DataFlavor.stringFlavor);
                                 int var17 = Math.min(var123, var125.length());
                                 int var18 = 0;

                                 while (true) {
                                    if (var18 >= var17) {
                                       method1067((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] + var125.substring(0, var17));
                                       continue label1101;
                                    }

                                    if (!classIB.method6177(var125.charAt(var18), -875935692)) {
                                       break;
                                    }

                                    char var20 = var125.charAt(var18);
                                    boolean var19 = field403.indexOf(var20) != -1;
                                    if (!var19) {
                                       break;
                                    }

                                    var18++;
                                 }

                                 classAC.method321(3, 2074725843);
                                 return;
                              } catch (UnsupportedFlavorException var21) {
                              } catch (IOException var22) {
                              }
                           }
                        } else if (classIB.method6177(var9.field2173, 899362435)) {
                           char var120 = var9.field2173;
                           boolean var114 = field403.indexOf(var120) != -1;
                           if (var114 && (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0].length() < 20) {
                              method1067((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] + var9.field2173);
                           }
                        }
                     }
                  }
               } else if (3 == -47366135 * field384) {
                  int var10 = 180 + -1098687177 * field393;
                  int var11 = 241;
                  classUQ var12 = var1.method46(25, classKK.field3857.length() - -841759507, classKK.field3857, var10, var11);
                  if (var23 == 1 && var12.method11536(var24, var25, -594779443)) {
                     classUF.method11359(classKK.field3923, (byte)45);
                  }

                  var10 = 180 + field393 * 681042829;
                  var11 = 101558450;
                  if (1 == var23 && var24 >= var10 - 75 && var24 <= var10 + 191093716 && var25 >= var11 - 20 && var25 <= var11 + 20) {
                     classIV.method6363(false, (byte)56);
                  }

                  var10 = 180 + -511275367 * field393;
                  short var64 = 326;
                  if (1 == var23 && var24 >= var10 - 75 && var24 <= 75 + var10 && var25 >= var64 - 20 && var25 <= var64 + 20) {
                     classUF.method11359(classKK.field3924, (byte)-29);
                     return;
                  }
               } else if (4 == -47366135 * field384) {
                  int var29 = 180 + -511275367 * field393 - -1001676585;
                  short var65 = 321;
                  if (var23 == 1 && var24 >= var29 - 51709693 && var24 <= var29 + -327815314 && var25 >= var65 - 20 && var25 <= var65 + 20) {
                     classQP.field5584.trim();
                     if (classQP.field5584.length() != 6) {
                        WorldEntity.method10671(classKK.field3644, classKK.field3848, classKK.field3743, (byte)-20);
                     } else {
                        classEV.field1942 = Integer.parseInt(classQP.field5584) * 400348643;
                        classQP.field5584 = "";
                        classXQ.method12934(true, 402128428);
                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-32);
                        classJB.method6386(20, -1192966987);
                     }

                     return;
                  }

                  if (var23 == 1
                     && var24 >= 180 + field393 * -511275367 - 9
                     && var24 <= 180 + -1157965610 * field393 + -1273209407
                     && var25 >= 14051166
                     && var25 <= 296) {
                     field392 = !field392;
                  }

                  if (1 == var23
                     && var24 >= 1854789085 + field393 * -511275367 - 34
                     && var24 <= 1060039836 + 5740226 * field393 + 180
                     && var25 >= 351
                     && var25 <= 363) {
                     classUF.method11359(classKK.field3924, (byte)51);
                  }

                  var29 = 80 + 1983538686 * field393 + -665983311;
                  if (var23 == 1 && var24 >= var29 - 75 && var24 <= 493425369 + var29 && var25 >= var65 - 20 && var25 <= 20 + var65) {
                     classAC.method321(0, 2028130125);
                     field389 = "";
                     method1067("");
                     classEV.field1942 = 0;
                     classQP.field5584 = "";
                  }

                  while (var9.method4555((short)2530)) {
                     boolean var95 = false;

                     for (int var13 = 0; var13 < field404.length(); var13++) {
                        if (var9.field2173 == field404.charAt(var13)) {
                           var95 = true;
                           break;
                        }
                     }

                     if (var9.field2174 * 1363213817 == 13) {
                        classAC.method321(0, 2043136252);
                        field389 = "";
                        method1067("");
                        classEV.field1942 = 0;
                        classQP.field5584 = "";
                     } else {
                        if (-1535123030 * var9.field2174 == 227266693 && !classQP.field5584.isEmpty()) {
                           classQP.field5584 = classQP.field5584.substring(0, classQP.field5584.length() - 1);
                        }

                        if (1106423215 * var9.field2174 == 892964457) {
                           classQP.field5584.trim();
                           if (classQP.field5584.length() != 6) {
                              WorldEntity.method10671(classKK.field3644, classKK.field3848, classKK.field3743, (byte)-46);
                           } else {
                              classEV.field1942 = Integer.parseInt(classQP.field5584) * -388521971;
                              classQP.field5584 = "";
                              classXQ.method12934(true, 402128428);
                              WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-34);
                              classJB.method6386(20, -1192966987);
                           }

                           return;
                        }

                        if (var95 && classQP.field5584.length() < 6) {
                           classQP.field5584 = classQP.field5584 + var9.field2173;
                        }
                     }
                  }
               } else if (-1904958527 * field384 == 5) {
                  int var31 = -511275367 * field393 + 52918119 - 80;
                  short var66 = 321;
                  if (1 == var23 && var24 >= var31 - -410095649 && var24 <= 75 + var31 && var25 >= var66 - 20 && var25 <= 20 + var66) {
                     classHC.method6023(-486975020);
                     return;
                  }

                  var31 = 80 + field393 * -990485602 + 180;
                  if (1 == var23 && var24 >= var31 - 1812428323 && var24 <= var31 + 75 && var25 >= var66 - 20 && var25 <= var66 + 20) {
                     classBJ.method1121(true, -347989319);
                  }

                  short var96 = 361;
                  if (null != classLB.field4087) {
                     int var109 = 242332319 * classLB.field4087.field6368 / 2;
                     if (var23 == 1
                        && var24 >= classLB.field4087.field6366 * -1357616065 - var109
                        && var24 <= classLB.field4087.field6366 * -1357616065 + var109
                        && var25 >= var96 - 15
                        && var25 < var96) {
                        classUF.method11359(classLZ.method7470("secure", true, -353723064) + "m=weblogin/g=oldscape/cant_log_in", (byte)-8);
                     }
                  }

                  while (var9.method4555((short)-17892)) {
                     boolean var110 = false;

                     for (int var14 = 0; var14 < field403.length(); var14++) {
                        if (var9.field2173 == field403.charAt(var14)) {
                           var110 = true;
                           break;
                        }
                     }

                     if (-363284909 * var9.field2174 == 13) {
                        classBJ.method1121(true, -347989319);
                     } else {
                        if (284661206 == var9.field2174 * 1363213817 && !field389.isEmpty()) {
                           field389 = field389.substring(0, field389.length() - 1);
                        }

                        if (1667771524 == var9.field2174 * 974485112) {
                           classHC.method6023(-486975020);
                           return;
                        }

                        if (var110 && field389.length() < 320) {
                           field389 = field389 + var9.field2173;
                        }
                     }
                  }
               } else if (6 != field384 * -47366135) {
                  if (-190365541 * field384 == 7) {
                     if (client.field1133 && !client.field891) {
                        int var36 = 566261347 * Ignore.field5836 - 150;
                        int var68 = 25 + var36 + -843380486 + 40;
                        int var97 = -1943946850;
                        int var111 = 40 + var97;
                        if (var23 == 1 && var24 >= var36 && var24 <= var68 && var25 >= var97 && var25 <= var111) {
                           field390 = classBJ.method1120(var36, var24, (byte)1) * 1294471087;
                        }

                        int var116 = field393 * -511275367 + 180 - -60552029;
                        short var15 = 321;
                        if (1 == var23 && var24 >= var116 - -113720544 && var24 <= -947219911 + var116 && var25 >= var15 - 20 && var25 <= 20 + var15) {
                           boolean var16 = classCN.method2545((byte)-19);
                           if (var16) {
                              classJB.method6386(-1895406859, -1192966987);
                              return;
                           }
                        }

                        var116 = 180 + 1971769036 * field393 + 456366793;
                        if (var23 == 1 && var24 >= var116 - 75 && var24 <= var116 + 75 && var25 >= var15 - 20 && var25 <= var15 + 20) {
                           field406 = new String[8];
                           classBJ.method1121(true, -347989319);
                        }

                        while (var9.method4555((short)21003)) {
                           if (101 == var9.field2174 * 1363213817) {
                              field406[-594543677 * field390] = null;
                           }

                           if (85 == var9.field2174 * 1363213817) {
                              if (field406[field390 * -908647452] == null && field390 * 662569670 > 0) {
                                 field390 -= 1610316996;
                              }

                              field406[field390 * 1175152039] = null;
                           }

                           if (var9.field2173 >= '0' && var9.field2173 <= -1889376489) {
                              field406[-594543677 * field390] = "" + var9.field2173;
                              if (-594543677 * field390 < 7) {
                                 field390 += 686613081;
                              }
                           }

                           if (var9.field2174 * 1363213817 == 84) {
                              boolean var124 = classCN.method2545((byte)65);
                              if (var124) {
                                 classJB.method6386(2070951345, -1192966987);
                              }

                              return;
                           }
                        }
                     } else {
                        int var34 = 180 + field393 * -511275367 - 80;
                        int var67 = 819537146;
                        if (1 == var23 && var24 >= var34 - -766326998 && var24 <= 75 + var34 && var25 >= var67 - 20 && var25 <= 20 + var67) {
                           classUF.method11359(classLZ.method7470("secure", true, 506308122) + "m=dob/set_dob.ws", (byte)7);
                           WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-11);
                           classAC.method321(6, 2065575177);
                           return;
                        }

                        var34 = 80 + 254121518 + field393 * 274902303;
                        if (1 == var23 && var24 >= var34 - -353187860 && var24 <= var34 + 1903778217 && var25 >= var67 - 20 && var25 <= 20 + var67) {
                           classBJ.method1121(true, -347989319);
                        }
                     }
                  } else if (8 == field384 * 1413882511) {
                     int var37 = field393 * -511275367 + 1176015988 - 1652348494;
                     short var69 = 321;
                     if (1 == var23 && var24 >= var37 - 75 && var24 <= 748061513 + var37 && var25 >= var69 - 20 && var25 <= 20 + var69) {
                        classUF.method11359("https://legal.jagex.com/docs/policies/privacy", (byte)-13);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-26);
                        classAC.method321(6, 2038690741);
                        return;
                     }

                     var37 = -511275367 * field393 + 180 + 80;
                     if (var23 == 1 && var24 >= var37 - -1532204374 && var24 <= var37 + 147931102 && var25 >= var69 - 20 && var25 <= var69 + 20) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (field384 * 2120415358 == 9) {
                     int var39 = -2052007993 * field393 + 180;
                     int var70 = 1840499422;
                     boolean var98 = 1 == var23 && var24 >= var39 - -1133842193 && var24 <= 75 + var39 && var25 >= var70 - 20 && var25 <= 20 + var70;

                     while (var9.method4555((short)26151)) {
                        if (-1898910312 == var9.field2174 * 1363213817 || 13 == -438434900 * var9.field2174) {
                           var98 = true;
                        }
                     }

                     if (var98) {
                        classIV.method6363(false, (byte)5);
                     }
                  } else if (field384 * 1474399996 == 10) {
                     int var40 = field393 * -1625405121 + -1102637467;
                     short var71 = 209;
                     boolean var99 = 1 == var23 && var24 >= var40 - -939721518 && var24 <= 2055085498 + var40 && var25 >= var71 && var25 <= var71 + -1571564929;

                     while (var9.method4555((short)-25097)) {
                        if (1363213817 * var9.field2174 == -944459765) {
                           var99 = true;
                        }
                     }

                     if (var99) {
                        if (classNU.method8185(-1401357231)) {
                           return;
                        }

                        WorldEntity.method10671(classKK.field3820, classKK.field3799, classKK.field3838, (byte)-53);
                        client.field899 = AccountType.field92;
                        classXQ.method12934(false, 402128428);
                        classJB.method6386(20, -1192966987);
                     }
                  } else if (12 == 1728372279 * field384) {
                     int var41 = Ignore.field5836 * 1429288549;
                     int var72 = 233;
                     classUQ var100 = var2.method46(0, 30, classKK.field3898, var41, var72);
                     classUQ var112 = var2.method46(-920675567, 32, classKK.field3898, var41, var72);
                     classUQ var118 = var2.method46(-1772203686, 34, classKK.field3898, var41, var72);
                     var72 += 17;
                     classUQ var122 = var2.method46(0, 34, classKK.field3899, var41, var72);
                     if (var23 == 1) {
                        if (var100.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/terms", (byte)-9);
                        } else if (var112.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/policies/privacy", (byte)-21);
                        } else if (var118.method11536(var24, var25, -594779443) || var122.method11536(var24, var25, -594779443)) {
                           classUF.method11359("https://legal.jagex.com/docs/terms/eula", (byte)-6);
                        }
                     }

                     var41 = Ignore.field5836 * 1429288549 - 918990043;
                     short var74 = 311;
                     if (1 == var23 && var24 >= var41 - 795608293 && var24 <= 574075493 + var41 && var25 >= var74 - 20 && var25 <= var74 + 20) {
                        classYY.method13834(233340812);
                        classIV.method6363(true, (byte)15);
                     }

                     var41 = 895625624 + 1429288549 * Ignore.field5836;
                     if (var23 == 1 && var24 >= var41 - 75 && var24 <= 75 + var41 && var25 >= var74 - 20 && var25 <= 20 + var74) {
                        field384 = 296620136;
                     }
                  } else if (13 == 907313130 * field384) {
                     int var44 = Ignore.field5836 * 1429288549;
                     short var75 = 321;
                     if (var23 == 1 && var24 >= var44 - 75 && var24 <= 75 + var44 && var25 >= var75 - 20 && var25 <= var75 + 20) {
                        classIV.method6363(true, (byte)12);
                     }
                  } else if (14 == field384 * -100180671) {
                     String var45 = "";
                     switch (1824764894 * field387) {
                        case 0:
                           var45 = "https://secure.runescape.com/m=offence-appeal/account-history";
                           break;
                        case 1:
                           var45 = "https://secure.runescape.com/m=accountappeal/passwordrecovery";
                           break;
                        case 2:
                           var45 = "https://support.runescape.com/hc/en-gb/articles/207256855-Settle-an-Unpaid-Balance";
                           break;
                        default:
                           classBJ.method1121(false, -347989319);
                     }

                     int var76 = field393 * -1811381354 + 180;
                     int var101 = 1832631871;
                     if (1 == var23 && var24 >= var76 - -541256406 && var24 <= 75 + var76 && var25 >= var101 - 20 && var25 <= 20 + var101) {
                        classUF.method11359(var45, (byte)-69);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-110);
                        classAC.method321(6, 2141699842);
                        return;
                     }

                     var76 = -2058850177 * field393 + 1364512412;
                     var101 = -1535302739;
                     if (1 == var23 && var24 >= var76 - -170862930 && var24 <= 243343588 + var76 && var25 >= var101 - 20 && var25 <= var101 + 20) {
                        classBJ.method1121(false, -347989319);
                     }
                  } else if (-47366135 * field384 == 24) {
                     int var46 = -511275367 * field393 + -677029548;
                     int var78 = -951740019;
                     if (1 == var23 && var24 >= var46 - 75 && var24 <= 1670313715 + var46 && var25 >= var78 - 20 && var25 <= 20 + var78) {
                        classIV.method6363(false, (byte)-33);
                     }
                  } else if (32 == field384 * -633879965) {
                     int var47 = 944421307 + field393 * 2075190218 - -279841536;
                     int var79 = 1303638901;
                     if (var23 == 1 && var24 >= var47 - -104268845 && var24 <= 75 + var47 && var25 >= var79 - 20 && var25 <= 20 + var79) {
                        classUF.method11359(classLZ.method7470("secure", true, 1550003268) + "m=dob/set_dob.ws", (byte)-74);
                        WorldEntity.method10671(classKK.field3867, classKK.field3868, classKK.field3760, (byte)-25);
                        classAC.method321(6, 2107506465);
                        return;
                     }

                     var47 = -1265256223 + -511275367 * field393 + -326569031;
                     if (1 == var23 && var24 >= var47 - 75 && var24 <= -1378553889 + var47 && var25 >= var79 - 20 && var25 <= 20 + var79) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (2015583330 == -47366135 * field384) {
                     int var49 = field393 * -115801230 + 180;
                     int var80 = 1581067151;
                     if (1 == var23 && var24 >= var49 - 75 && var24 <= 838667812 + var49 && var25 >= var80 - 20 && var25 <= var80 + 20) {
                        classUF.method11359(classKK.field3923, (byte)13);
                     }

                     var49 = -511275367 * field393 + 180;
                     var80 = 870892005;
                     if (1 == var23 && var24 >= var49 - 75 && var24 <= 75 + var49 && var25 >= var80 - 20 && var25 <= 20 + var80) {
                        classBJ.method1121(true, -347989319);
                     }
                  } else if (34 == field384 * -1162078801) {
                     int var51 = -1874438596 * field393 + -676648016;
                     int var82 = 276;
                     if (1 == var23 && var24 >= var51 - 75 && var24 <= var51 + -628222841 && var25 >= var82 - 20 && var25 <= var82 + 20) {
                        if (classKB.method6484(524126124)) {
                           field412 = true;
                           field407 = 0;
                           field408 = 0;
                        }

                        return;
                     }

                     if (null != classIS.field3052 && classKB.method6484(1618298902)) {
                        field412 = true;
                        field407 = 0;
                        field408 = 0;
                     }

                     var51 = 180 + field393 * -2051753057;
                     var82 = -1713021240;
                     if (var23 == 1 && var24 >= var51 - 75 && var24 <= -70014568 + var51 && var25 >= var82 - 20 && var25 <= 20 + var82) {
                        classBJ.method1121(false, -347989319);
                     }
                  } else if (1608567930 == -47366135 * field384) {
                     int var53 = Ignore.field5836 * 1429288549;
                     int var84 = 332839892;
                     classUQ var103 = var1.method47(classKK.field3906, var53, var84);
                     if (1 == var23 && var103.method11536(var24, var25, -594779443)) {
                        classUF.method11359(classKK.field3907, (byte)71);
                        return;
                     }

                     classUQ.method11530(var103, (byte)-30);
                     var53 = Ignore.field5836 * 1901696418 - 80;
                     var84 = 1533050646;
                     if (var23 == 1 && var24 >= var53 - 75 && var24 <= 75 + var53 && var25 >= var84 - 20 && var25 <= 20 + var84) {
                        classUF.method11359(classKK.field3923, (byte)-58);
                        return;
                     }

                     var53 = 80 + 340657199 * Ignore.field5836;
                     if (var23 == 1 && var24 >= var53 - 1958483472 && var24 <= var53 + 75 && var25 >= var84 - 20 && var25 <= var84 + 20) {
                        classBJ.method1121(true, -347989319);
                        return;
                     }

                     while (var9.method4555((short)21464)) {
                        if (var9.field2174 * 1643407418 == -2013161116) {
                           classBJ.method1121(true, -347989319);
                           return;
                        }

                        if (var9.field2174 * 48229668 == 13) {
                           classIV.method6363(true, (byte)45);
                           return;
                        }
                     }
                  }
               } else {
                  while (var9.method4555((short)3982)) {
                     if (var9.field2174 * 1563728158 == -1977591180 || 13 == 39200397 * var9.field2174) {
                        classBJ.method1121(true, -347989319);
                     }
                  }

                  int var33 = 1500847302;
                  if (1 == var23 && var25 >= var33 - 20 && var25 <= 20 + var33) {
                     classBJ.method1121(true, -347989319);
                  }
               }
            }
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;)V")
   @ObfuscatedName("ch")
   static void method1043(classZV var0, classZV var1) {
      if (classCR.field1228 == null) {
         classVP var3 = classHC.field2765;
         SpritePixels[] var2;
         if (!IndexDataBase.method11923(var3, "sl_back", "", -195683605)) {
            var2 = null;
         } else {
            int var4 = IndexDataBase.method11918(var3, "sl_back", 1467326662);
            int var5 = var3.method11922(var4, "", (byte)-39);
            var2 = classIR.method6349(var3, var4, var5, -1253680523);
         }

         classCR.field1228 = var2;
      }

      if (null == classCF.field720) {
         classCF.field720 = classOT.method8767(classHC.field2765, "sl_flags", "", (byte)119);
      }

      if (classPM.field5347 == null) {
         classPM.field5347 = classOT.method8767(classHC.field2765, "sl_arrows", "", (byte)51);
      }

      if (null == classPD.field5225) {
         classPD.field5225 = classOT.method8767(classHC.field2765, "sl_stars", "", (byte)28);
      }

      if (null == ChatLineBuffer.field590) {
         ChatLineBuffer.field590 = classSU.method10729(classHC.field2765, "leftarrow", "", 337205995);
      }

      if (classUR.field6372 == null) {
         classUR.field6372 = classSU.method10729(classHC.field2765, "rightarrow", "", -101227834);
      }

      classYW.method13737(field385 * -329898575, 23, 765, 1567599481, 0);
      classYW.method13738(-329898575 * field385, 0, 1527579353, 23, 12425273, -700746084);
      classYW.method13738(-181039573 + 1550923104 * field385, 0, 640, 23, 1468243614, -699539519);
      var0.method59(classKK.field3910, 62 + field385 * -329898575, 15, 0, -1);
      if (null != classPD.field5225) {
         IndexedSprite.method13853(classPD.field5225[1], 140 + -329898575 * field385, 1);
         var1.method51(classKK.field3911, 152 + field385 * 286660000, 10, -759350690, -1);
         IndexedSprite.method13853(classPD.field5225[0], 1281474269 + -329898575 * field385, 12);
         var1.method51(classKK.field3854, 1625072086 + -329898575 * field385, 21, -1525801183, -1);
      }

      if (null != classPM.field5347) {
         int var22 = 280 + -912877205 * field385;
         if (0 == World.field1152[0] && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var22, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var22, 4);
         }

         if (World.field1152[0] == 0 && 1 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[3], var22 + 15, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], var22 + 15, 4);
         }

         var0.method51(classKK.field3695, var22 + 32, 17, 16777215, -1);
         int var24 = field385 * -329898575 + -560321488;
         if (World.field1152[0] == 1 && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var24, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var24, 4);
         }

         if (World.field1152[0] == 1 && 1 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[3], 15 + var24, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], 15 + var24, 4);
         }

         var0.method51(classKK.field3896, 32 + var24, 17, 16777215, -1);
         int var26 = 500 + -793762833 * field385;
         if (World.field1152[0] == 2 && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var26, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var26, 4);
         }

         if (World.field1152[0] == 2 && World.field1153[0] == 1) {
            IndexedSprite.method13853(classPM.field5347[3], var26 + 15, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], var26 + 15, 4);
         }

         var0.method51(classKK.field3915, var26 + 32, 17, -1621572649, -1);
         int var28 = 610 + field385 * -329898575;
         if (3 == World.field1152[0] && 0 == World.field1153[0]) {
            IndexedSprite.method13853(classPM.field5347[2], var28, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[0], var28, 4);
         }

         if (World.field1152[0] == 3 && World.field1153[0] == 1) {
            IndexedSprite.method13853(classPM.field5347[3], 15 + var28, 4);
         } else {
            IndexedSprite.method13853(classPM.field5347[1], 15 + var28, 4);
         }

         var0.method51(classKK.field3916, 32 + var28, 17, 16777215, -1);
      }

      classYW.method13737(-1522534192 + field385 * -310648143, 4, 50, 16, 0);
      var1.method59(classKK.field3852, 708 + field385 * -329898575 + 25, 16, 1567616229, -1);
      field399 = -520043415;
      if (classCR.field1228 != null) {
         byte var23 = 88;
         byte var25 = 19;
         int var27 = 1824187540 / (var23 + 1) - 1;
         int var29 = -1477888635 / (var25 + 1);

         int var6;
         int var7;
         do {
            var6 = var29;
            var7 = var27;
            if (var29 * (var27 - 1) >= -622036481 * World.field1151) {
               var27--;
            }

            if ((var29 - 1) * var27 >= World.field1151 * -622036481) {
               var29--;
            }

            if (var27 * (var29 - 1) >= World.field1151 * -1370701007) {
               var29--;
            }
         } while (var29 != var6 || var27 != var7);

         var6 = (-1117994840 - var27 * var23) / (var27 + 1);
         if (var6 > 5) {
            var6 = 5;
         }

         var7 = (551028291 - var25 * var29) / (var29 + 1);
         if (var7 > 5) {
            var7 = 5;
         }

         int var8 = (-1843517481 - var23 * var27 - (var27 - 1) * var6) / 2;
         int var9 = (-1250770241 - var29 * var25 - var7 * (var29 - 1)) / 2;
         int var10 = (World.field1151 * -1424924751 + var29 - 1) / var29;
         field408 = -1501820553 * (var10 - var27);
         if (ChatLineBuffer.field590 != null && field407 * 533664459 > 0) {
            IndexedSprite.method13853(ChatLineBuffer.field590, 8, 2019607096 * IntProjection.field1936 / 2 - ChatLineBuffer.field590.field7117 / 2);
         }

         if (null != classUR.field6372 && field407 * -942812997 < field408 * -782381486) {
            IndexedSprite.method13853(
               classUR.field6372,
               967858679 * classSA.field5895 - classUR.field6372.field7119 - 8,
               -1587380644 * IntProjection.field1936 / 2 - classUR.field6372.field7117 / 2
            );
         }

         int var11 = var9 + 23;
         int var12 = field385 * 1915840324 + var8;
         int var13 = 0;
         boolean var14 = false;
         int var15 = 209420144 * field407;

         for (int var16 = var29 * var15; var16 < 31848248 * World.field1151 && var15 - -942812997 * field407 < var27; var16++) {
            World var17 = World.field1168[var16];
            boolean var18 = true;
            String var19 = Integer.toString(1311203956 * var17.field1157);
            if (-1 == var17.field1157 * 503455939) {
               var19 = classKK.field3917;
               var18 = false;
            } else if (var17.field1157 * -2002363860 > 1212106642) {
               var19 = classKK.field3837;
               var18 = false;
            }

            classCC var20 = null;
            int var21 = 0;
            if (var17.method2497(-2000377570)) {
               var20 = var17.method2473((byte)-36) ? classCC.field666 : classCC.field658;
            } else if (var17.method2482(-2077635663)) {
               var20 = var17.method2473((byte)-122) ? classCC.field667 : classCC.field662;
            } else if (var17.method2493(262407453)) {
               var21 = -810629583;
               var20 = var17.method2473((byte)-13) ? classCC.field657 : classCC.field656;
            } else if (World.method2501(var17, -1840377553)) {
               var20 = var17.method2473((byte)-60) ? classCC.field665 : classCC.field663;
            } else if (var17.method2485(23162323)) {
               var20 = var17.method2473((byte)-38) ? classCC.field655 : classCC.field654;
            } else if (World.method2509(var17, (byte)-37)) {
               var20 = var17.method2473((byte)-19) ? classCC.field653 : classCC.field659;
            } else if (var17.method2512(-1600233376)) {
               var20 = var17.method2473((byte)-84) ? classCC.field660 : classCC.field664;
            }

            if (null == var20 || 1626329630 * var20.field651 >= classCR.field1228.length) {
               var20 = var17.method2473((byte)-90) ? classCC.field661 : classCC.field652;
            }

            if (classTZ.field6253 * -388911654 >= var12
               && classTZ.field6262 * -2144333897 >= var11
               && 437427609 * classTZ.field6253 < var23 + var12
               && 32593241 * classTZ.field6262 < var25 + var11
               && var18) {
               field399 = 610692037 * var16;
               classCR.field1228[var20.field651 * 371320039].method13561(var12, var11, 128, 1836658853);
               var14 = true;
            } else {
               classCR.field1228[371320039 * var20.field651].method13552(var12, var11);
            }

            if (classCF.field720 != null) {
               IndexedSprite.method13853(
                  classCF.field720[(var17.method2473((byte)-1) ? classCF.field720.length / 2 : 0) + var17.field1160 * -53334949], 29 + var12, var11
               );
            }

            var0.method59(Integer.toString(-616858415 * var17.field1159), var12 + 15, var25 / 2 + var11 + 5, var21, -1);
            var1.method59(var19, 852999021 + var12, var11 + var25 / 2 + 5, 1544898548, -1);
            var11 += var25 + var7;
            if (++var13 >= var29) {
               var11 = 23 + var9;
               var12 += var23 + var6;
               var13 = 0;
               var15++;
            }
         }

         if (var14) {
            int var32 = var1.method33(World.field1168[field399 * 1605123169].field1165) + 6;
            int var33 = var1.field8 + 8;
            int var34 = classTZ.field6262 * -1633043681 + 25;
            if (var33 + var34 > 2099029577) {
               var34 = -566390285 * classTZ.field6262 - 25 - var33;
            }

            classYW.method13737(-38255113 * classTZ.field6253 - var32 / 2, var34, var32, var33, 1388540357);
            classYW.method13746(classTZ.field6253 * -306475718 - var32 / 2, var34, var32, var33, 0);
            var1.method59(World.field1168[field399 * -2105609177].field1165, classTZ.field6253 * -38255113, var34 + var1.field8 + 4, 0, -1);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dc")
   static void method1051() {
      if (-47366135 * field384 == 34) {
         classBJ.method1121(false, -347989319);
      }

      field412 = false;
      classYW.method13737(0, 0, classYW.field7100, classYW.field7099, 0);
      classNI.field4671.method13552(field385 * -329898575, 0);
      classFR.field2389.method13552(382 + field385 * -329898575, 0);
      IndexedSprite.method13853(AABB.field2370, 382 + field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("bk")
   static String method1009() {
      return classAB.field122.method2722((short)-16494) ? classSL.method10515(field389, (short)21169) : field389;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dm")
   static void method1056() {
      classAC.method321(24, 2140572166);
      WorldEntity.method10671(classKK.field3842, classKK.field3843, classKK.field3801, (byte)-35);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ce")
   static void method1052() {
      if (-47366135 * field384 == 34) {
         classBJ.method1121(false, -347989319);
      }

      field412 = false;
      classYW.method13737(0, 0, classYW.field7100, classYW.field7099, 0);
      classNI.field4671.method13552(field385 * -329898575, 0);
      classFR.field2389.method13552(382 + field385 * -329898575, 0);
      IndexedSprite.method13853(AABB.field2370, 382 + field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("dy")
   static void method1058(int var0) {
      if (-47366135 * field384 != var0) {
         field384 = 2054751964 * var0;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ci")
   static void method1053() {
      if (-47366135 * field384 == 34) {
         classBJ.method1121(false, -347989319);
      }

      field412 = false;
      classYW.method13737(0, 0, classYW.field7100, classYW.field7099, 0);
      classNI.field4671.method13552(field385 * -329898575, 0);
      classFR.field2389.method13552(382 + field385 * -329898575, 0);
      IndexedSprite.method13853(AABB.field2370, 382 + field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("cy")
   static void method1036() {
      field389 = field389.trim();
      if (field389.isEmpty()) {
         WorldEntity.method10671(classKK.field3711, classKK.field3767, classKK.field3677, (byte)-84);
      } else {
         long var1;
         try {
            URL var3 = new URL(classLZ.method7470("services", false, -720312256) + "m=accountappeal/login.ws");
            URLConnection var4 = var3.openConnection();
            var4.setRequestProperty("connection", "close");
            var4.setDoInput(true);
            var4.setDoOutput(true);
            var4.setConnectTimeout(5000);
            OutputStreamWriter var5 = new OutputStreamWriter(var4.getOutputStream());
            var5.write("data1=req");
            var5.flush();
            InputStream var6 = var4.getInputStream();
            classXY var7 = new classXY(new byte[1000]);

            while (true) {
               int var8 = var6.read(var7.field6952, var7.field6953 * 702114061, -1803703027 - var7.field6953 * 702114061);
               if (-1 == var8) {
                  var7.field6953 = 0;
                  long var11 = classXY.method13115(var7, 445926842);
                  var1 = var11;
                  break;
               }

               var7.field6953 += var8 * -1095856699;
               if (702114061 * var7.field6953 >= 1000) {
                  var1 = 0L;
                  break;
               }
            }
         } catch (Exception var10) {
            var1 = 0L;
         }

         int var0;
         if (0L == var1) {
            var0 = 5;
         } else {
            var0 = classGY.method5949(var1, field389, (byte)-10);
         }

         switch (var0) {
            case 2:
               WorldEntity.method10671(classKK.field3872, classKK.field3904, classKK.field3761, (byte)-53);
               classAC.method321(6, 2135767410);
               break;
            case 3:
               WorldEntity.method10671(classKK.field3725, classKK.field3605, classKK.field3877, (byte)-81);
               break;
            case 4:
               WorldEntity.method10671(classKK.field3878, classKK.field3879, classKK.field3880, (byte)-38);
               break;
            case 5:
               WorldEntity.method10671(classKK.field3881, classKK.field3882, classKK.field3883, (byte)-84);
               break;
            case 6:
               WorldEntity.method10671(classKK.field3884, classKK.field3885, classKK.field3886, (byte)-88);
               break;
            case 7:
               WorldEntity.method10671(classKK.field3766, classKK.field3888, classKK.field3889, (byte)-118);
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("dt")
   static void method1060(String var0) {
      field411 = classDD.method2998(var0, (byte)-126);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Z)V")
   @ObfuscatedName("ct")
   static void method1029(boolean var0) {
      if (!classOE.field4841.method2138(1985617997) && !client.method2142(classOE.field4841, (byte)43) && !classOE.field4841.method2140(-1205077068)) {
         field396 = classKK.field3819;
         field397 = classKK.field3778;
         field398 = classKK.field3821;
         classAC.method321(2, 2022021170);
         if (var0) {
            method1067("");
         }

         if (field389 == null || field389.isEmpty()) {
            if (classAB.field122.method2782((byte)8) != null) {
               field389 = classAB.field122.method2782((byte)8);
               client.field833 = true;
            } else {
               client.field833 = false;
            }
         }

         if (client.field833 && field389 != null && !field389.isEmpty()) {
            field402 = -878854915;
         } else {
            field402 = 0;
         }
      } else {
         classAC.method321(10, 2053607863);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("dk")
   static void method1062() {
      if (classAB.field122.method2729((byte)116)) {
         classBK.method1122(0, 0, (byte)58);
      } else {
         classPD.method8880("scape main", 255, (byte)32);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("cz")
   static void method1046(GameEngine var0) {
      classFA var1 = client.field932;
      classFA var2 = var1;

      while (var2.method4555((short)10380)) {
         if (var2.field2174 * 1363213817 == 13) {
            classEM.method3952(Integer.MAX_VALUE);
            return;
         }

         if (var2.field2174 * 1363213817 == 96) {
            if (field407 * -942812997 > 0 && ChatLineBuffer.field590 != null) {
               field407 -= -1581517709;
            }
         } else if (1363213817 * var2.field2174 == 97 && -942812997 * field407 < field408 * -343302073 && classUR.field6372 != null) {
            field407 += -1581517709;
         }
      }

      if (1229235359 * classTZ.field6259 == 1 || !classON.field4993 && 4 == classTZ.field6259 * 1229235359) {
         int var3 = -329898575 * field385 + 280;
         if (classTZ.field6264 * 1422993977 >= var3
            && classTZ.field6264 * 1422993977 <= var3 + 14
            && 1243590197 * classTZ.field6255 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(0, 0, (byte)22);
            return;
         }

         if (1422993977 * classTZ.field6264 >= var3 + 15
            && classTZ.field6264 * 1422993977 <= var3 + 80
            && 1243590197 * classTZ.field6255 >= 4
            && classTZ.field6255 * 1243590197 <= 18) {
            classJJ.method6413(0, 1, (byte)-46);
            return;
         }

         int var4 = field385 * -329898575 + 390;
         if (classTZ.field6264 * 1422993977 >= var4
            && 1422993977 * classTZ.field6264 <= 14 + var4
            && 1243590197 * classTZ.field6255 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(1, 0, (byte)55);
            return;
         }

         if (1422993977 * classTZ.field6264 >= var4 + 15
            && 1422993977 * classTZ.field6264 <= var4 + 80
            && 1243590197 * classTZ.field6255 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(1, 1, (byte)30);
            return;
         }

         int var5 = field385 * -329898575 + 500;
         if (classTZ.field6264 * 1422993977 >= var5
            && classTZ.field6264 * 1422993977 <= 14 + var5
            && classTZ.field6255 * 1243590197 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(2, 0, (byte)-18);
            return;
         }

         if (1422993977 * classTZ.field6264 >= 15 + var5
            && classTZ.field6264 * 1422993977 <= var5 + 80
            && classTZ.field6255 * 1243590197 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(2, 1, (byte)-43);
            return;
         }

         int var6 = field385 * -329898575 + 610;
         if (classTZ.field6264 * 1422993977 >= var6
            && 1422993977 * classTZ.field6264 <= var6 + 14
            && classTZ.field6255 * 1243590197 >= 4
            && 1243590197 * classTZ.field6255 <= 18) {
            classJJ.method6413(3, 0, (byte)10);
            return;
         }

         if (classTZ.field6264 * 1422993977 >= var6 + 15
            && classTZ.field6264 * 1422993977 <= 80 + var6
            && 1243590197 * classTZ.field6255 >= 4
            && classTZ.field6255 * 1243590197 <= 18) {
            classJJ.method6413(3, 1, (byte)-57);
            return;
         }

         if (classTZ.field6264 * 1422993977 >= 708 + field385 * -329898575
            && 1243590197 * classTZ.field6255 >= 4
            && classTZ.field6264 * 1422993977 <= field385 * -329898575 + 708 + 50
            && 1243590197 * classTZ.field6255 <= 20) {
            classEM.method3952(Integer.MAX_VALUE);
            return;
         }

         if (-1 != field399 * -2105609177) {
            World var7 = World.field1168[field399 * -2105609177];
            int var9 = client.field884 * -2024190683;
            classXE var10 = classXE.field6842;
            boolean var8 = (var9 & var10.vmethod8((byte)1)) != 0;
            boolean var12 = var7.method2482(-1117157050);
            classAC.field135 = var12;
            var7.field1166 = var12 ? "beta" : var7.field1166;
            classDB.method2887(var7, 1660308192);
            classEM.method3952(Integer.MAX_VALUE);
            if (var8 != var12) {
               classAC.method319((byte)11);
            }

            return;
         }

         if (field407 * -942812997 > 0
            && ChatLineBuffer.field590 != null
            && classTZ.field6264 * 1422993977 >= 0
            && classTZ.field6264 * 1422993977 <= ChatLineBuffer.field590.field7119
            && 1243590197 * classTZ.field6255 >= 8379747 * IntProjection.field1936 / 2 - 50
            && classTZ.field6255 * 1243590197 <= 50 + 8379747 * IntProjection.field1936 / 2) {
            field407 -= -1581517709;
         }

         if (-942812997 * field407 < field408 * -343302073
            && classUR.field6372 != null
            && 1422993977 * classTZ.field6264 >= 773246731 * classSA.field5895 - classUR.field6372.field7119 - 5
            && 1422993977 * classTZ.field6264 <= classSA.field5895 * 773246731
            && classTZ.field6255 * 1243590197 >= IntProjection.field1936 * 8379747 / 2 - 50
            && classTZ.field6255 * 1243590197 <= 50 + IntProjection.field1936 * 8379747 / 2) {
            field407 += -1581517709;
         }
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("di")
   static void method1059(int var0) {
      if (-47366135 * field384 != var0) {
         field384 = 1117408825 * var0;
      }
   }

   @ObfuscatedSignature(descriptor = "(IB)I")
   @ObfuscatedName("af")
   public static int method1000(int var0, byte var1) {
      try {
         return classGB.field2535[var0 & 16383];
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "bf.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lzv;Lzv;Lzv;)V")
   @ObfuscatedName("co")
   static void method1039(classZV var0, classZV var1, classZV var2) {
      field385 = (773246731 * classSA.field5895 - 765) / 2 * 358417233;
      field393 = 2007709401 * field385 + 1927755610;
      Ignore.field5836 = 273549476 + -1415132635 * field393;
      if (field412) {
         classCS.method2613(var0, var1, (byte)103);
      } else {
         classNI.field4671.method13552(field385 * -329898575, 0);
         classFR.field2389.method13552(382 + field385 * -329898575, 0);
         IndexedSprite.method13853(AABB.field2370, 382 + field385 * -329898575 - AABB.field2370.field7119 / 2, 18);
         if (client.field829 * -44590225 == 0 || client.field829 * -44590225 == 5) {
            byte var3 = 20;
            var0.method59(classKK.field3818, -511275367 * field393 + 180, 245 - var3, 16777215, -1);
            int var4 = 253 - var3;
            classYW.method13746(180 + -511275367 * field393 - 152, var4, 304, 34, 9179409);
            classYW.method13746(180 + field393 * -511275367 - 151, var4 + 1, 302, 32, 0);
            classYW.method13737(180 + field393 * -511275367 - 150, var4 + 2, field394 * 1758494531, 30, 9179409);
            classYW.method13737(1758494531 * field394 + (field393 * -511275367 + 180 - 150), var4 + 2, 300 - field394 * 1758494531, 30, 0);
            var0.method59(field391, 180 + -511275367 * field393, 276 - var3, 16777215, -1);
         }

         if (20 == -44590225 * client.field829) {
            IndexedSprite.method13853(classJB.field3118, field393 * -511275367 + 180 - classJB.field3118.field7119 / 2, 271 - classJB.field3118.field7117 / 2);
            int var11 = 201;
            var0.method59(field396, field393 * -511275367 + 180, var11, 16776960, 0);
            var11 += 15;
            var0.method59(field397, -511275367 * field393 + 180, var11, 16776960, 0);
            var11 += 15;
            var0.method59(field398, field393 * -511275367 + 180, var11, 16776960, 0);
            var11 += 15;
            var11 += 7;
            if (field384 * -47366135 != 4 && field384 * -47366135 != 10 && 1 != -47366135 * field384) {
               var0.method51(classKK.field3786, 180 + field393 * -511275367 - 110, var11, 16777215, 0);
               short var100 = 200;
               String var5 = classBQ.method1183(-1812796598);

               while (var0.method33(var5) > var100) {
                  var5 = var5.substring(0, var5.length() - 1);
               }

               var0.method51(AbstractFont.method6(var5), 180 + -511275367 * field393 - 70, var11, 16777215, 0);
               var11 += 15;
               String var6 = classSL.method10515((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0], (short)11269);

               while (var0.method33(var6) > var100) {
                  var6 = var6.substring(1);
               }

               var0.method51(classKK.field3836 + var6, 180 + -511275367 * field393 - 108, var11, 16777215, 0);
               var11 += 15;
            }
         }

         if (10 == client.field829 * -44590225 || -44590225 * client.field829 == 11 || client.field829 * -44590225 == 50) {
            IndexedSprite.method13853(classJB.field3118, -511275367 * field393, 171);
            if (-47366135 * field384 == 0) {
               int var18 = 251;
               var0.method59(classKK.field3876, 180 + field393 * -511275367, var18, 16776960, 0);
               var18 += 30;
               int var101 = 180 + field393 * -511275367 - 80;
               short var139 = 291;
               IndexedSprite.method13853(classKA.field3512, var101 - 73, var139 - 20);
               classAAO.method225(var0, classKK.field3849, var101 - 73, var139 - 20, 144, 40, 16777215, 0, 1, 1, 0);
               var101 = 180 + -511275367 * field393 + 80;
               IndexedSprite.method13853(classKA.field3512, var101 - 73, var139 - 20);
               classAAO.method225(var0, classKK.field3850, var101 - 73, var139 - 20, 144, 40, 16777215, 0, 1, 1, 0);
            } else if (-47366135 * field384 == 1) {
               var0.method59(field395, 180 + -511275367 * field393, 201, 16776960, 0);
               int var20 = 236;
               var0.method59(field396, 180 + field393 * -511275367, var20, 16777215, 0);
               var20 += 15;
               var0.method59(field397, field393 * -511275367 + 180, var20, 16777215, 0);
               var20 += 15;
               var0.method59(field398, field393 * -511275367 + 180, var20, 16777215, 0);
               var20 += 15;
               int var103 = -511275367 * field393 + 180 - 80;
               short var140 = 321;
               IndexedSprite.method13853(classKA.field3512, var103 - 73, var140 - 20);
               var0.method59(classKK.field3775, var103, var140 + 5, 16777215, 0);
               var103 = 80 + 180 + -511275367 * field393;
               IndexedSprite.method13853(classKA.field3512, var103 - 73, var140 - 20);
               var0.method59(classKK.field3852, var103, 5 + var140, 16777215, 0);
            } else if (-47366135 * field384 == 2) {
               int var24 = 201;
               var0.method59(field396, 1429288549 * Ignore.field5836, var24, 16776960, 0);
               var24 += 15;
               var0.method59(field397, 1429288549 * Ignore.field5836, var24, 16776960, 0);
               var24 += 15;
               var0.method59(field398, Ignore.field5836 * 1429288549, var24, 16776960, 0);
               var24 += 15;
               var24 += 7;
               var0.method51(classKK.field3786, 1429288549 * Ignore.field5836 - 110, var24, 16777215, 0);
               short var105 = 200;
               String var141 = classBQ.method1183(-472703106);

               while (var0.method33(var141) > var105) {
                  var141 = var141.substring(1);
               }

               var0.method51(
                  AbstractFont.method6(var141)
                     + (0 == field402 * -1187692459 & 1612595797 * client.field856 % 40 < 20 ? classQY.method9885(16776960, (byte)115) + classDO.field1587 : ""),
                  Ignore.field5836 * 1429288549 - 70,
                  var24,
                  16777215,
                  0
               );
               var24 += 15;
               String var161 = classSL.method10515((String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0], (short)17674);

               while (var0.method33(var161) > var105) {
                  var161 = var161.substring(1);
               }

               var0.method51(
                  classKK.field3836
                     + var161
                     + (field402 * -1187692459 == 1 & 1612595797 * client.field856 % 40 < 20 ? classQY.method9885(16776960, (byte)93) + classDO.field1587 : ""),
                  1429288549 * Ignore.field5836 - 108,
                  var24,
                  16777215,
                  0
               );
               var24 += 15;
               int var31 = 277;
               int var7 = -117 + 1429288549 * Ignore.field5836;
               IndexedSprite var8 = classLF.method6827(client.field833, field400, 311410949);
               IndexedSprite.method13853(var8, var7, var31);
               var7 += var8.field7119 + 5;
               var1.method51(classKK.field3667, var7, 13 + var31, 16776960, 0);
               var7 = Ignore.field5836 * 1429288549 + 24;
               var8 = classLF.method6827(classAB.field122.method2722((short)5148), field405, 1060398701);
               IndexedSprite.method13853(var8, var7, var31);
               var7 += var8.field7119 + 5;
               var1.method51(classKK.field3646, var7, 13 + var31, 16776960, 0);
               var31 += 15;
               int var9 = 1429288549 * Ignore.field5836 - 80;
               short var10 = 321;
               IndexedSprite.method13853(classKA.field3512, var9 - 73, var10 - 20);
               var0.method59(classKK.field3707, var9, 5 + var10, 16777215, 0);
               var9 = 1429288549 * Ignore.field5836 + 80;
               IndexedSprite.method13853(classKA.field3512, var9 - 73, var10 - 20);
               var0.method59(classKK.field3852, var9, 5 + var10, 16777215, 0);
               short var33 = 357;
               switch (-323997799 * field388) {
                  case 2:
                     classJH.field3156 = classKK.field3866;
                     break;
                  default:
                     classJH.field3156 = classKK.field3864;
               }

               classWR.field6768 = new classUQ(1429288549 * Ignore.field5836, var33, var1.method33(classJH.field3156), 11);
               classLB.field4087 = new classUQ(1429288549 * Ignore.field5836, var33, var1.method33(classKK.field3865), 11);
               var1.method59(classJH.field3156, Ignore.field5836 * 1429288549, var33, 16777215, 0);
            } else if (3 == -47366135 * field384) {
               int var34 = 201;
               var0.method59(classKK.field3922, -511275367 * field393 + 180, var34, 16776960, 0);
               var34 += 20;
               var1.method59(classKK.field3856, -511275367 * field393 + 180, var34, 16776960, 0);
               var34 += 20;
               var1.method59(classKK.field3857, 180 + field393 * -511275367, var34, 16776960, 0);
               var34 += 15;
               int var106 = 180 + field393 * -511275367;
               short var142 = 276;
               IndexedSprite.method13853(classKA.field3512, var106 - 73, var142 - 20);
               var2.method59(classKK.field3858, var106, 5 + var142, 16777215, 0);
               var106 = 180 + field393 * -511275367;
               var142 = 326;
               IndexedSprite.method13853(classKA.field3512, var106 - 73, var142 - 20);
               var2.method59(classKK.field3859, var106, 5 + var142, 16777215, 0);
            } else if (-47366135 * field384 == 4) {
               var0.method59(classKK.field3835, -511275367 * field393 + 180, 201, 16776960, 0);
               int var38 = 236;
               var0.method59(field396, 180 + field393 * -511275367, var38, 16777215, 0);
               var38 += 15;
               var0.method59(field397, field393 * -511275367 + 180, var38, 16777215, 0);
               var38 += 15;
               var0.method59(field398, 180 + field393 * -511275367, var38, 16777215, 0);
               var38 += 15;
               var0.method51(
                  classKK.field3847
                     + classSL.method10515(classQP.field5584, (short)18365)
                     + (client.field856 * 1612595797 % 40 < 20 ? classQY.method9885(16776960, (byte)120) + classDO.field1587 : ""),
                  -511275367 * field393 + 180 - 108,
                  var38,
                  16777215,
                  0
               );
               var38 -= 8;
               var0.method51(classKK.field3614, -511275367 * field393 + 180 - 9, var38, 16776960, 0);
               var38 += 15;
               var0.method51(classKK.field3615, -511275367 * field393 + 180 - 9, var38, 16776960, 0);
               int var108 = 180 + field393 * -511275367 - 9 + var0.method33(classKK.field3615) + 15;
               int var144 = var38 - var0.field8;
               IndexedSprite var162;
               if (field392) {
                  var162 = classEK.field1777;
               } else {
                  var162 = classCK.field778;
               }

               IndexedSprite.method13853(var162, var108, var144);
               var38 += 15;
               int var173 = 180 + -511275367 * field393 - 80;
               short var180 = 321;
               IndexedSprite.method13853(classKA.field3512, var173 - 73, var180 - 20);
               var0.method59(classKK.field3775, var173, 5 + var180, 16777215, 0);
               var173 = 80 + field393 * -511275367 + 180;
               IndexedSprite.method13853(classKA.field3512, var173 - 73, var180 - 20);
               var0.method59(classKK.field3852, var173, var180 + 5, 16777215, 0);
               var1.method59(classKK.field3864, 180 + -511275367 * field393, 36 + var180, 16777215, 0);
            } else if (field384 * -47366135 == 5) {
               var0.method59(classKK.field3860, -511275367 * field393 + 180, 201, 16776960, 0);
               int var45 = 221;
               var2.method59(field396, -511275367 * field393 + 180, var45, 16776960, 0);
               var45 += 15;
               var2.method59(field397, -511275367 * field393 + 180, var45, 16776960, 0);
               var45 += 15;
               var2.method59(field398, 180 + -511275367 * field393, var45, 16776960, 0);
               var45 += 15;
               var45 += 14;
               var0.method51(classKK.field3873, -511275367 * field393 + 180 - 145, var45, 16777215, 0);
               short var109 = 174;
               String var145 = classBQ.method1183(-646187306);

               while (var0.method33(var145) > var109) {
                  var145 = var145.substring(1);
               }

               var0.method51(
                  AbstractFont.method6(var145) + (1612595797 * client.field856 % 40 < 20 ? classQY.method9885(16776960, (byte)46) + classDO.field1587 : ""),
                  -511275367 * field393 + 180 - 34,
                  var45,
                  16777215,
                  0
               );
               var45 += 15;
               int var163 = field393 * -511275367 + 180 - 80;
               short var175 = 321;
               IndexedSprite.method13853(classKA.field3512, var163 - 73, var175 - 20);
               var0.method59(classKK.field3862, var163, 5 + var175, 16777215, 0);
               var163 = 80 + field393 * -511275367 + 180;
               IndexedSprite.method13853(classKA.field3512, var163 - 73, var175 - 20);
               var0.method59(classKK.field3863, var163, 5 + var175, 16777215, 0);
               var175 = 356;
               var1.method59(classKK.field3865, Ignore.field5836 * 1429288549, var175, 268435455, 0);
            } else if (field384 * -47366135 == 6) {
               int var51 = 201;
               var0.method59(field396, -511275367 * field393 + 180, var51, 16776960, 0);
               var51 += 15;
               var0.method59(field397, field393 * -511275367 + 180, var51, 16776960, 0);
               var51 += 15;
               var0.method59(field398, field393 * -511275367 + 180, var51, 16776960, 0);
               var51 += 15;
               int var110 = -511275367 * field393 + 180;
               short var146 = 321;
               IndexedSprite.method13853(classKA.field3512, var110 - 73, var146 - 20);
               var0.method59(classKK.field3863, var110, 5 + var146, 16777215, 0);
            } else if (7 == -47366135 * field384) {
               if (client.field1133 && !client.field891) {
                  int var59 = 201;
                  var0.method59(field396, 1429288549 * Ignore.field5836, var59, 16776960, 0);
                  var59 += 15;
                  var0.method59(field397, 1429288549 * Ignore.field5836, var59, 16776960, 0);
                  var59 += 15;
                  var0.method59(field398, 1429288549 * Ignore.field5836, var59, 16776960, 0);
                  int var113 = 1429288549 * Ignore.field5836 - 150;
                  var59 += 10;

                  for (int var148 = 0; var148 < 8; var148++) {
                     classKA.field3512.method13856(var113, var59, 30, 40);
                     boolean var165 = field390 * -594543677 == var148 & 1612595797 * client.field856 % 40 < 20;
                     var0.method51(
                        (field406[var148] == null ? "" : field406[var148]) + (var165 ? classQY.method9885(16776960, (byte)53) + classDO.field1587 : ""),
                        10 + var113,
                        27 + var59,
                        16777215,
                        0
                     );
                     if (var148 != 1 && 3 != var148) {
                        var113 += 35;
                     } else {
                        var113 += 50;
                        var0.method51(AbstractFont.method6("/"), var113 - 13, 27 + var59, 16777215, 0);
                     }
                  }

                  int var149 = Ignore.field5836 * 1429288549 - 80;
                  short var166 = 321;
                  IndexedSprite.method13853(classKA.field3512, var149 - 73, var166 - 20);
                  var0.method59("Submit", var149, 5 + var166, 16777215, 0);
                  var149 = 1429288549 * Ignore.field5836 + 80;
                  IndexedSprite.method13853(classKA.field3512, var149 - 73, var166 - 20);
                  var0.method59(classKK.field3852, var149, 5 + var166, 16777215, 0);
               } else {
                  int var55 = 216;
                  var0.method59(classKK.field3890, field393 * -511275367 + 180, var55, 16776960, 0);
                  var55 += 15;
                  var2.method59(classKK.field3891, field393 * -511275367 + 180, var55, 16776960, 0);
                  var55 += 15;
                  var2.method59(classKK.field3631, 180 + -511275367 * field393, var55, 16776960, 0);
                  var55 += 15;
                  int var111 = 180 + field393 * -511275367 - 80;
                  short var147 = 321;
                  IndexedSprite.method13853(classKA.field3512, var111 - 73, var147 - 20);
                  var0.method59(classKK.field3893, var111, var147 + 5, 16777215, 0);
                  var111 = 180 + -511275367 * field393 + 80;
                  IndexedSprite.method13853(classKA.field3512, var111 - 73, var147 - 20);
                  var0.method59(classKK.field3863, var111, 5 + var147, 16777215, 0);
               }
            } else if (8 == -47366135 * field384) {
               int var63 = 216;
               var0.method59(classKK.field3611, 180 + field393 * -511275367, var63, 16776960, 0);
               var63 += 15;
               var2.method59(classKK.field3747, 180 + -511275367 * field393, var63, 16776960, 0);
               var63 += 15;
               var2.method59(classKK.field3748, 180 + -511275367 * field393, var63, 16776960, 0);
               var63 += 15;
               int var114 = -511275367 * field393 + 180 - 80;
               short var151 = 321;
               IndexedSprite.method13853(classKA.field3512, var114 - 73, var151 - 20);
               var0.method59(classKK.field3749, var114, var151 + 5, 16777215, 0);
               var114 = 80 + -511275367 * field393 + 180;
               IndexedSprite.method13853(classKA.field3512, var114 - 73, var151 - 20);
               var0.method59(classKK.field3863, var114, 5 + var151, 16777215, 0);
            } else if (9 == -47366135 * field384) {
               int var67 = 221;
               var0.method59(field396, 180 + -511275367 * field393, var67, 16776960, 0);
               var67 += 25;
               var0.method59(field397, 180 + -511275367 * field393, var67, 16776960, 0);
               var67 += 25;
               var0.method59(field398, 180 + -511275367 * field393, var67, 16776960, 0);
               int var116 = field393 * -511275367 + 180;
               short var152 = 311;
               IndexedSprite.method13853(classKA.field3512, var116 - 73, var152 - 20);
               var0.method59(classKK.field3858, var116, var152 + 5, 16777215, 0);
            } else if (10 == -47366135 * field384) {
               int var70 = 180 + -511275367 * field393;
               int var117 = 209;
               var0.method59(classKK.field3876, field393 * -511275367 + 180, var117, 16776960, 0);
               var117 += 20;
               IndexedSprite.method13853(classGQ.field2653, var70 - 109, var117);
               if (field411.isEmpty()) {
                  IndexedSprite.method13853(classQH.field5542, var70 - 48, 18 + var117);
               } else {
                  IndexedSprite.method13853(classQH.field5542, var70 - 48, var117 + 5);
                  var0.method59(field411, var70, 68 + var117 - 15, 16776960, 0);
               }
            } else if (-47366135 * field384 == 12) {
               int var71 = 1429288549 * Ignore.field5836;
               int var119 = 216;
               var2.method59(classKK.field3897, var71, var119, 16777215, 0);
               var119 += 17;
               var2.method59(classKK.field3898, var71, var119, 16777215, 0);
               var119 += 17;
               var2.method59(classKK.field3899, var71, var119, 16777215, 0);
               var119 += 17;
               var2.method59(classKK.field3900, var71, var119, 16777215, 0);
               var71 = 1429288549 * Ignore.field5836 - 80;
               short var123 = 311;
               IndexedSprite.method13853(classKA.field3512, var71 - 73, var123 - 20);
               var0.method59(classKK.field3908, var71, var123 + 5, 16777215, 0);
               var71 = Ignore.field5836 * 1429288549 + 80;
               IndexedSprite.method13853(classKA.field3512, var71 - 73, var123 - 20);
               var0.method59(classKK.field3909, var71, 5 + var123, 16777215, 0);
            } else if (13 == field384 * -47366135) {
               int var74 = 231;
               var2.method59(classKK.field3901, 180 + -511275367 * field393, var74, 16777215, 0);
               var74 += 20;
               var2.method59(classKK.field3902, -511275367 * field393 + 180, var74, 16777215, 0);
               int var124 = 180 + -511275367 * field393;
               short var76 = 311;
               IndexedSprite.method13853(classKA.field3512, var124 - 73, var76 - 20);
               var0.method59(classKK.field3863, var124, var76 + 5, 16777215, 0);
            } else if (14 == field384 * -47366135) {
               int var77 = 201;
               String var125 = "";
               String var153 = "";
               String var167 = "";
               switch (-20437633 * field387) {
                  case 0:
                     var125 = classKK.field3905;
                     var153 = classKK.field3660;
                     var167 = classKK.field3661;
                     break;
                  case 1:
                     var125 = classKK.field3698;
                     var153 = classKK.field3699;
                     var167 = classKK.field3700;
                     break;
                  case 2:
                     var125 = classKK.field3782;
                     var153 = classKK.field3895;
                     var167 = classKK.field3620;
                     break;
                  default:
                     classBJ.method1121(false, -347989319);
               }

               var0.method59(var125, field393 * -511275367 + 180, var77, 16776960, 0);
               var77 += 20;
               var0.method59(var153, 180 + field393 * -511275367, var77, 16776960, 0);
               var77 += 20;
               var0.method59(var167, 180 + field393 * -511275367, var77, 16776960, 0);
               int var177 = 180 + field393 * -511275367;
               short var181 = 276;
               IndexedSprite.method13853(classKA.field3512, var177 - 73, var181 - 20);
               if (1 == -20437633 * field387) {
                  var0.method59(classKK.field3643, var177, var181 + 5, 16777215, 0);
               } else {
                  var0.method59(classKK.field3708, var177, 5 + var181, 16777215, 0);
               }

               var177 = -511275367 * field393 + 180;
               var181 = 326;
               IndexedSprite.method13853(classKA.field3512, var177 - 73, var181 - 20);
               var0.method59(classKK.field3863, var177, 5 + var181, 16777215, 0);
            } else if (24 == -47366135 * field384) {
               int var80 = 221;
               var0.method59(field396, -511275367 * field393 + 180, var80, 16777215, 0);
               var80 += 15;
               var0.method59(field397, field393 * -511275367 + 180, var80, 16777215, 0);
               var80 += 15;
               var0.method59(field398, -511275367 * field393 + 180, var80, 16777215, 0);
               var80 += 15;
               int var126 = -511275367 * field393 + 180;
               short var154 = 301;
               IndexedSprite.method13853(classKA.field3512, var126 - 73, var154 - 20);
               var0.method59(classKK.field3793, var126, 5 + var154, 16777215, 0);
            } else if (32 == field384 * -47366135) {
               int var84 = 216;
               var0.method59(classKK.field3890, field393 * -511275367 + 180, var84, 16776960, 0);
               var84 += 15;
               var2.method59(classKK.field3891, field393 * -511275367 + 180, var84, 16776960, 0);
               var84 += 15;
               var2.method59(classKK.field3631, 180 + field393 * -511275367, var84, 16776960, 0);
               var84 += 15;
               int var127 = 180 + field393 * -511275367 - 80;
               short var155 = 321;
               IndexedSprite.method13853(classKA.field3512, var127 - 73, var155 - 20);
               var0.method59(classKK.field3893, var127, 5 + var155, 16777215, 0);
               var127 = -511275367 * field393 + 180 + 80;
               IndexedSprite.method13853(classKA.field3512, var127 - 73, var155 - 20);
               var0.method59(classKK.field3863, var127, 5 + var155, 16777215, 0);
            } else if (33 == -47366135 * field384) {
               int var88 = 201;
               var0.method59(field396, -511275367 * field393 + 180, var88, 16776960, 0);
               var88 += 20;
               var1.method59(field397, 180 + -511275367 * field393, var88, 16776960, 0);
               var88 += 20;
               var1.method59(field398, -511275367 * field393 + 180, var88, 16776960, 0);
               var88 += 15;
               int var129 = field393 * -511275367 + 180;
               short var156 = 276;
               IndexedSprite.method13853(classKA.field3512, var129 - 73, var156 - 20);
               var2.method59(classKK.field3734, var129, var156 + 5, 16777215, 0);
               var129 = field393 * -511275367 + 180;
               var156 = 326;
               IndexedSprite.method13853(classKA.field3512, var129 - 73, var156 - 20);
               var2.method59(classKK.field3863, var129, 5 + var156, 16777215, 0);
            } else if (field384 * -47366135 == 34) {
               int var92 = 201;
               var0.method59(field396, 180 + field393 * -511275367, var92, 16776960, 0);
               var92 += 20;
               var1.method59(field397, 180 + field393 * -511275367, var92, 16776960, 0);
               var92 += 20;
               var1.method59(field398, field393 * -511275367 + 180, var92, 16776960, 0);
               int var131 = 180 + field393 * -511275367;
               short var158 = 276;
               IndexedSprite.method13853(classKA.field3512, var131 - 73, var158 - 20);
               String var168 = classIS.field3052 != null ? classKK.field3919 : classKK.field3784;
               var2.method59(var168, var131, var158 + 5, 16777215, 0);
               var131 = -511275367 * field393 + 180;
               var158 = 326;
               IndexedSprite.method13853(classKA.field3512, var131 - 73, var158 - 20);
               var2.method59(classKK.field3863, var131, var158 + 5, 16777215, 0);
            } else if (38 == field384 * -47366135) {
               int var95 = Ignore.field5836 * 1429288549;
               int var133 = 216;
               var0.method59(classKK.field3757, var95, var133, 16776960, 0);
               var133 += 25;
               var1.method59(classKK.field3654, var95, var133, 16777215, 0);
               var133 += 20;
               var1.method59(classKK.field3875, var95, var133, 16777215, 0);
               var133 += 20;
               var1.method59(classKK.field3906, var95, var133, 16765440, 0);
               var95 = 1429288549 * Ignore.field5836 - 80;
               short var137 = 321;
               IndexedSprite.method13853(classKA.field3512, var95 - 73, var137 - 20);
               var0.method59(classKK.field3734, var95, 5 + var137, 16777215, 0);
               var95 = 80 + 1429288549 * Ignore.field5836;
               IndexedSprite.method13853(classKA.field3512, var95 - 73, var137 - 20);
               var0.method59(classKK.field3707, var95, 5 + var137, 16777215, 0);
            }
         }

         if (-44590225 * client.field829 >= 10) {
            int[] var98 = new int[4];
            classYW.method13714(var98);
            classYW.method13710(field385 * -329898575, 0, -329898575 * field385 + 765, 8379747 * IntProjection.field1936);
            classML.field4529.method2614(field385 * -329898575 - 22, client.field856 * 1612595797, 1698595949);
            classML.field4529.method2614(765 + -329898575 * field385 + 22 - 128, client.field856 * 1612595797, 1698595949);
            classYW.method13718(var98);
         }

         IndexedSprite.method13853(MessageNode.field733[classAB.field122.method2729((byte)29) ? 1 : 0], 765 + -329898575 * field385 - 40, 463);
         if (-44590225 * client.field829 > 5 && classLU.field4230 == classXO.field6890) {
            if (SceneTilePaint.field2292 != null) {
               int var99 = -329898575 * field385 + 5;
               short var138 = 463;
               byte var160 = 100;
               byte var169 = 35;
               IndexedSprite.method13853(SceneTilePaint.field2292, var99, var138);
               var0.method59(classKK.field3788 + " " + client.field828 * 1311484143, var99 + var160 / 2, var138 + var169 / 2 - 2, 16777215, 0);
               if (null != classIS.field3052) {
                  var1.method59(classKK.field3919, var99 + var160 / 2, 12 + var169 / 2 + var138, 16777215, 0);
               } else {
                  var1.method59(classKK.field3815, var99 + var160 / 2, var138 + var169 / 2 + 12, 16777215, 0);
               }
            } else {
               SceneTilePaint.field2292 = classSU.method10729(classHC.field2765, "sl_button", "", -1260709029);
            }
         }
      }
   }

   @ObfuscatedSignature(descriptor = "(B)[Ltn;")
   @ObfuscatedName("ag")
   public static classTN[] method998(byte var0) {
      try {
         return new classTN[]{classTN.field6169, classTN.field6170, classTN.field6171};
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "bf.ag(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/String;)V")
   @ObfuscatedName("zm")
   static void method1067(String var0) {
      (String[])ConstantBootstraps.invoke<" ",classBF::method1064>()[0] = var0;
   }

   @ObfuscatedSignature(descriptor = "([BIIB)Ljava/lang/String;")
   @ObfuscatedName("af")
   public static String method999(byte[] var0, int var1, int var2, byte var3) {
      try {
         char[] var4 = new char[var2];
         int var5 = 0;
         int var6 = var1;
         int var7 = var2 + var1;

         while (var6 < var7) {
            if (var3 >= 1) {
               throw new IllegalStateException();
            }

            int var8 = var0[var6++] & 255;
            int var9;
            if (var8 < 128) {
               if (0 == var8) {
                  if (var3 >= 1) {
                     throw new IllegalStateException();
                  }

                  var9 = 65533;
               } else {
                  var9 = var8;
               }
            } else if (var8 < 192) {
               var9 = 65533;
            } else {
               label140:
               if (var8 < 224) {
                  if (var3 >= 1) {
                     throw new IllegalStateException();
                  }

                  if (var6 < var7) {
                     if (var3 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (128 == (var0[var6] & 192)) {
                        if (var3 >= 1) {
                           throw new IllegalStateException();
                        }

                        var9 = (var8 & 31) << 6 | var0[var6++] & 63;
                        if (var9 < 128) {
                           var9 = 65533;
                        }
                        break label140;
                     }
                  }

                  var9 = 65533;
               } else {
                  label139:
                  if (var8 < 240) {
                     if (var3 >= 1) {
                        throw new IllegalStateException();
                     }

                     if (var6 + 1 < var7) {
                        if (var3 >= 1) {
                           throw new IllegalStateException();
                        }

                        if (128 == (var0[var6] & 192)) {
                           if (var3 >= 1) {
                              throw new IllegalStateException();
                           }

                           if (128 == (var0[1 + var6] & 192)) {
                              if (var3 >= 1) {
                                 throw new IllegalStateException();
                              }

                              var9 = (var8 & 15) << 12 | (var0[var6++] & 63) << 6 | var0[var6++] & 63;
                              if (var9 < 2048) {
                                 if (var3 >= 1) {
                                    throw new IllegalStateException();
                                 }

                                 var9 = 65533;
                              }
                              break label139;
                           }
                        }
                     }

                     var9 = 65533;
                  } else {
                     label138:
                     if (var8 < 248) {
                        if (var3 >= 1) {
                           throw new IllegalStateException();
                        }

                        if (2 + var6 < var7 && 128 == (var0[var6] & 192) && 128 == (var0[var6 + 1] & 192)) {
                           if (var3 >= 1) {
                              throw new IllegalStateException();
                           }

                           if (128 == (var0[var6 + 2] & 192)) {
                              var9 = (var8 & 7) << 18 | (var0[var6++] & 63) << 12 | (var0[var6++] & 63) << 6 | var0[var6++] & 63;
                              if (var9 >= 65536) {
                                 if (var3 >= 1) {
                                    throw new IllegalStateException();
                                 }

                                 if (var9 <= 1114111) {
                                    var9 = 65533;
                                    break label138;
                                 }

                                 if (var3 >= 1) {
                                    throw new IllegalStateException();
                                 }
                              }

                              var9 = 65533;
                              break label138;
                           }
                        }

                        var9 = 65533;
                     } else {
                        var9 = 65533;
                     }
                  }
               }
            }

            var4[var5++] = (char)var9;
         }

         return new String(var4, 0, var5);
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "bf.af(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(FFFI)F")
   @ObfuscatedName("ay")
   public static float method1001(float var0, float var1, float var2, int var3) {
      try {
         if (var1 < var0) {
            if (var3 == 1877849950) {
               throw new IllegalStateException();
            } else {
               throw new IllegalArgumentException("max: " + var1 + " can not be lower than min: " + var0);
            }
         } else {
            return Math.max(var0, Math.min(var2, var1));
         }
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "bf.ay(" + ')');
      }
   }
}
