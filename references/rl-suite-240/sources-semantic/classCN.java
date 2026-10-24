import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("cn")
public class classCN {
   @ObfuscatedSignature(descriptor = "Lyn;")
   @ObfuscatedName("af")
   static final IterableHashTable field1172 = new IterableHashTable(1024);
   @ObfuscatedName("ab")
   static int field1170 = 0;
   @ObfuscatedSignature(descriptor = "Lnz;")
   @ObfuscatedName("ae")
   static final classNZ field1171 = new classNZ();
   @ToRemove(unused = "true")
   @ObfuscatedName("bk")
   static final int field1173 = 50;
   @ObfuscatedName("az")
   static final Map field1169 = new HashMap();

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("aw")
   static void method2531() {
      field1169.clear();
      field1172.method13623();
      field1171.method8296();
      field1170 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("aa")
   static void method2520(int var0, String var1, String var2) {
      classFM.method4865(var0, var1, var2, null, (byte)-61);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("ao")
   static void method2521(int var0, String var1, String var2) {
      classFM.method4865(var0, var1, var2, null, (byte)-67);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bx")
   static int method2538(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6513 == field1171.field4798 ? -1 : -1176735578 * ((MessageNode)var1.field6513).field727;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)V")
   @ObfuscatedName("ah")
   static final void method2546(byte var0) {
      try {
         if (client.field821 * -355071157 > 0) {
            if (var0 < 0) {
               classGI.method5492((byte)-107);
            }
         } else {
            client.field918.method6806(-1020808753);
            classJB.method6386(40, -1192966987);
            classDS.field1621 = client.field796.method3394(856860878);
            classDJ.method3387(client.field796, -580316386);
         }
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "cn.ah(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)Lcg;")
   @ObfuscatedName("ay")
   static MessageNode method2523(int var0, int var1) {
      ChatLineBuffer var2 = (ChatLineBuffer)field1169.get(var0);
      return var2.method1252(var1, -771078329);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)Lcg;")
   @ObfuscatedName("aq")
   static MessageNode method2524(int var0, int var1) {
      ChatLineBuffer var2 = (ChatLineBuffer)field1169.get(var0);
      return var2.method1252(var1, 1058172900);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("av")
   static void method2532() {
      field1169.clear();
      field1172.method13623();
      field1171.method8296();
      field1170 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lcg;")
   @ObfuscatedName("ap")
   static MessageNode method2526(int var0) {
      return (MessageNode)field1172.method13637(var0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lcg;")
   @ObfuscatedName("au")
   static MessageNode method2527(int var0) {
      return (MessageNode)field1172.method13637(var0);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ai")
   static int method2529(int var0) {
      ChatLineBuffer var1 = (ChatLineBuffer)field1169.get(var0);
      return var1 == null ? 0 : ChatLineBuffer.method1255(var1, (byte)-30);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ar")
   static int method2530(int var0) {
      ChatLineBuffer var1 = (ChatLineBuffer)field1169.get(var0);
      return var1 == null ? 0 : ChatLineBuffer.method1255(var1, (byte)-62);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(II)Lcg;")
   @ObfuscatedName("aj")
   static MessageNode method2525(int var0, int var1) {
      ChatLineBuffer var2 = (ChatLineBuffer)field1169.get(var0);
      return var2.method1252(var1, 1671620827);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "()V")
   @ObfuscatedName("ak")
   static void method2533() {
      field1169.clear();
      field1172.method13623();
      field1171.method8296();
      field1170 = 0;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)Lcg;")
   @ObfuscatedName("ad")
   static MessageNode method2528(int var0) {
      return (MessageNode)field1172.method13637(var0);
   }

   classCN() throws Throwable {
      throw new Error();
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("an")
   static int method2534(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6514 == field1171.field4798 ? -1 : 707758669 * ((MessageNode)var1.field6514).field727;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("am")
   static int method2535(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6514 == field1171.field4798 ? -1 : -1216729741 * ((MessageNode)var1.field6514).field727;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ah")
   static int method2536(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6514 == field1171.field4798 ? -1 : 707758669 * ((MessageNode)var1.field6514).field727;
      }
   }

   @ObfuscatedSignature(descriptor = "(B)Z")
   @ObfuscatedName("ac")
   static boolean method2545(byte var0) {
      try {
         Date var1;
         try {
            SimpleDateFormat var3 = new SimpleDateFormat("ddMMyyyyHH", Locale.ENGLISH);
            var3.setLenient(false);
            StringBuilder var4 = new StringBuilder();
            String[] var5 = classBF.field406;
            int var6 = 0;

            Date var2;
            while (true) {
               if (var6 >= var5.length) {
                  var4.append("12");
                  var2 = var3.parse(var4.toString());
                  break;
               }

               String var7 = var5[var6];
               if (null == var7) {
                  if (var0 == -1) {
                     throw new IllegalStateException();
                  }

                  classAB.method301("Date not valid.", "Please ensure all characters are populated.", "", 1688879482);
                  var2 = null;
                  break;
               }

               var4.append(var7);
               var6++;
            }

            var1 = var2;
         } catch (ParseException var8) {
            classAB.method301("Date not valid.", "Please ensure date follows the format", "DD/MM/YYYY and is after 01/01/1900", -177917866);
            return false;
         }

         if (null == var1) {
            if (var0 == -1) {
               throw new IllegalStateException();
            } else {
               return false;
            }
         } else {
            Calendar var11 = Calendar.getInstance();
            var11.set(1, var11.get(1) - 13);
            var11.set(5, var11.get(5) + 1);
            var11.set(11, 0);
            var11.set(12, 0);
            var11.set(13, 0);
            var11.set(14, 0);
            Date var12 = var11.getTime();
            boolean var10 = var1.before(var12);
            boolean var13 = classLX.method7420(var1, 531702551);
            if (!var13) {
               if (var0 == -1) {
                  throw new IllegalStateException();
               } else {
                  classAB.method301("Date not valid.", "Please ensure date follows the format", "DD/MM/YYYY and is after 01/01/1900", -884389559);
                  return false;
               }
            } else {
               if (!var10) {
                  IntProjection.field1935 = 485945013;
               } else {
                  IntProjection.field1935 = (int)(var1.getTime() / 86400000L - 11745L) * -2004283061;
               }

               return true;
            }
         }
      } catch (RuntimeException var9) {
         throw classEG.method3899(var9, "cn.ac(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bn")
   static int method2539(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6513 == field1171.field4798 ? -1 : 707758669 * ((MessageNode)var1.field6513).field727;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("bc")
   static int method2540(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6513 == field1171.field4798 ? -1 : -282470758 * ((MessageNode)var1.field6513).field727;
      }
   }

   @ObfuscatedSignature(descriptor = "(II)Lfs;")
   @ObfuscatedName("af")
   static classFS method2547(int var0, int var1) {
      synchronized (Animation.field5143) {
         int var4 = var0;
         int var5 = 1275003202;

         classFS var10000;
         try {
            classFS var6 = (classFS)NodeCache.method6460(Animation.field5143, var4);
            if (var6 != null) {
               if (var5 <= 741528736) {
                  throw new IllegalStateException();
               }

               var10000 = var6;
            } else {
               var6 = classMS.method7692(classKP.field3984, classIY.field3097, var4, -2117599441);
               if (null != var6) {
                  if (var5 <= 741528736) {
                     throw new IllegalStateException();
                  }

                  Animation.field5143.method6464(var6, var4);
               }

               var10000 = var6;
            }
         } catch (RuntimeException var7) {
            throw classEG.method3899(var7, "cn.af(" + ')');
         }

         return var10000;
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIZLvv;Lqn;S)V")
   @ObfuscatedName("ae")
   public static final void method2541(int var0, int var1, int var2, boolean var3, classVV var4, classQN var5, short var6) {
      try {
         if (var4.method7425(var0, 2131710019)) {
            NpcOverrides.method9135(var4.field4438[var0], 0, var4.field4438[var0].length - 1, -1, -1, var1, var2, var3, var4, var5, 1452131038);
         }
      } catch (RuntimeException var7) {
         throw classEG.method3899(var7, "cn.ae(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("at")
   static int method2537(int var0) {
      MessageNode var1 = (MessageNode)field1172.method13637(var0);
      if (var1 == null) {
         return -1;
      } else {
         return var1.field6514 == field1171.field4798 ? -1 : 707758669 * ((MessageNode)var1.field6514).field727;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;)V")
   @ObfuscatedName("dh")
   public static void method2544(GameEngine var0) {
      if (var0 == null) {
         var0.method11044();
      } else {
         var0.field6206 = true;
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V")
   @ObfuscatedName("al")
   static void method2522(int var0, String var1, String var2, String var3) {
      ChatLineBuffer var4 = (ChatLineBuffer)field1169.get(var0);
      if (null == var4) {
         var4 = new ChatLineBuffer();
         field1169.put(var0, var4);
      }

      MessageNode var5 = var4.method1250(var0, var1, var2, var3, -464900164);
      IterableHashTable.method13620(field1172, var5, var5.field727 * -142289306);
      field1171.method8302(var5);
      classPV.method9308(client.field825, 1963850608);
   }

   @ObfuscatedSignature(descriptor = "(J)I")
   @ObfuscatedName("ag")
   public static int method2542(long var0) {
      try {
         return (int)(var0 >>> 20 & 4294967295L);
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "cn.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lte;IIIIB)V")
   @ObfuscatedName("ac")
   public static void method2543(classTE var0, int var1, int var2, int var3, int var4, byte var5) {
      try {
         classHO.method6112(var0, classYY.field7109, (byte)63);
         if (var3 < 0) {
            if (var5 != 0) {
               throw new IllegalStateException();
            }

            var3 = 0;
         }

         int var6;
         label52: {
            var6 = var4 + var3;
            if (var4 >= 0) {
               if (var5 != 0) {
                  throw new IllegalStateException();
               }

               if (var6 >= 0) {
                  if (var5 != 0) {
                     throw new IllegalStateException();
                  }

                  if (var6 <= var0.method10797(-199101565)) {
                     break label52;
                  }

                  if (var5 != 0) {
                     throw new IllegalStateException();
                  }
               }
            }

            var6 = var0.method10797(-995563996);
         }

         int[] var7 = var0.method10791(1815488006);
         int var8 = var1;

         for (int var9 = var3; var9 < var6; var9++) {
            if (var5 != 0) {
               throw new IllegalStateException();
            }

            var7[var9] = var8;
            var8 += var2;
         }

         var0.field6131 = true;
      } catch (RuntimeException var10) {
         throw classEG.method3899(var10, "cn.ac(" + ')');
      }
   }
}
