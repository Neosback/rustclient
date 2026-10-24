import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.mapping.ToRemove;

@ObfuscatedName("gy")
public class classGY {
   @ToRemove(unused = "true")
   @ObfuscatedName("am")
   static final int field2743 = 2;
   @ObfuscatedName("af")
   int field2739;
   @ObfuscatedName("ae")
   int field2740;
   @ObfuscatedName("ab")
   int field2741;
   @ObfuscatedName("az")
   int field2742 = 0;
   @ToRemove(unused = "true")
   @ObfuscatedName("ax")
   static final int field2745 = 5;
   @ToRemove(unused = "true")
   @ObfuscatedName("an")
   static final int field2744 = 8;

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bg")
   public void method5962(int var1, int var2) {
      this.field2739 += var1 * 1397226421;
      this.field2740 += var2 * 1296710373;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("af")
   public int method5933(int var1) {
      try {
         return 1650177407 * this.field2741;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.af(" + 41);
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;)Ljava/lang/String;")
   @ObfuscatedName("xa")
   public static String method5976(classGY var0) {
      if (var0 == null) {
         var0.method5935();
      }

      return var0.field2739 * -787916131 + ", " + 1650177407 * var0.field2741 + ", " + -478154003 * var0.field2740 + ", " + var0.field2742 * 1171001325;
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ae")
   public int method5936(int var1) {
      try {
         return this.field2740 * -478154003;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;I)I")
   @ObfuscatedName("qo")
   public static int method5930(classGY var0, int var1) {
      if (var0 == null) {
         var0.method5932(var1);
      }

      try {
         return -787916131 * var0.field2739;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("ab")
   public int method5940(int var1) {
      try {
         return -787916131 * this.field2739 >> 7;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.ab(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(B)I")
   @ObfuscatedName("ag")
   public int method5944(byte var1) {
      try {
         return this.field2740 * -478154003 >> 7;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.ag(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;I)I")
   @ObfuscatedName("vt")
   public static int method5947(classGY var0, int var1) {
      if (var0 == null) {
         var0.method5939(var1);
      }

      try {
         return 1859166197 * var0.field2742;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.as(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ai")
   public int method5937() {
      return this.field2740 * -478154003;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("bn")
   public void method5952(classGY var1) {
      this.field2742 = var1.field2742 * 1;
      this.field2739 = 1 * var1.field2739;
      this.field2741 = 1 * var1.field2741;
      this.field2740 = var1.field2740 * 1;
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("al")
   public void method5957(int var1, int var2, byte var3) {
      try {
         this.field2739 = 1397226421 * var1;
         this.field2740 = 1296710373 * var2;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gy.aa(" + ')');
      }
   }

   @Override
   public String toString() {
      try {
         return this.field2739 * -787916131 + ", " + 1650177407 * this.field2741 + ", " + -478154003 * this.field2740 + ", " + this.field2742 * 1859166197;
      } catch (RuntimeException var1) {
         throw classEG.method3899(var1, "gy.toString(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIB)V")
   @ObfuscatedName("aa")
   public void method5963(int var1, int var2, byte var3) {
      try {
         this.field2739 += var1 * 1397226421;
         this.field2740 += var2 * 1296710373;
      } catch (RuntimeException var4) {
         throw classEG.method3899(var4, "gy.al(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(IIIII)V")
   @ObfuscatedName("aj")
   public void method5967(int var1, int var2, int var3, int var4, int var5) {
      try {
         this.field2739 += var1 * 1397226421;
         this.field2741 += var2 * 966134399;
         this.field2740 += var3 * 1296710373;
         this.method5950(var4 + this.field2742 * 1859166197, -2001730299);
      } catch (RuntimeException var6) {
         throw classEG.method3899(var6, "gy.aj(" + ')');
      }
   }

   @Override
   public boolean equals(Object var1) {
      try {
         return var1 instanceof classGY ? this.method5972((classGY)var1, -735246478) : false;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.equals(" + ')');
      }
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;)Ljava/lang/String;")
   @ObfuscatedName("dg")
   public static String method5977(classGY var0) {
      if (var0 == null) {
         var0.method5938();
      }

      return var0.field2739 * -787916131 + ", " + 1650177407 * var0.field2741 + ", " + -478154003 * var0.field2740 + ", " + var0.field2742 * 1859166197;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;I)Z")
   @ObfuscatedName("ay")
   boolean method5972(classGY var1, int var2) {
      try {
         if (-787916131 * this.field2739 == -787916131 * var1.field2739) {
            if (var2 == -324749371) {
               throw new IllegalStateException();
            }

            if (this.field2741 * 1650177407 == var1.field2741 * 1650177407) {
               if (var2 == -324749371) {
                  throw new IllegalStateException();
               }

               if (this.field2740 * -478154003 == var1.field2740 * -478154003) {
                  if (var2 == -324749371) {
                     throw new IllegalStateException();
                  }

                  if (1859166197 * var1.field2742 == 1859166197 * this.field2742) {
                     if (var2 == -324749371) {
                        throw new IllegalStateException();
                     }

                     return true;
                  }
               }
            }
         }

         return false;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gy.ay(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ap")
   public int method5934() {
      return 1650177407 * this.field2741;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jl")
   public boolean method5970(Object var1) {
      return var1 instanceof classGY ? this.method5972((classGY)var1, -1362004316) : false;
   }

   public classGY() {
      this.field2739 = 0;
      this.field2740 = 0;
      this.field2741 = 0;
   }

   @ObfuscatedSignature(descriptor = "(Ljava/lang/Object;)Z")
   @ObfuscatedName("jr")
   public boolean method5971(Object var1) {
      return var1 instanceof classGY ? this.method5972((classGY)var1, 936510594) : false;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aq")
   public int method5931() {
      return -787916131 * this.field2739;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("ax")
   public void method5950(int var1, int var2) {
      try {
         this.field2742 = -429665187 * (var1 & 2047);
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gy.ax(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bp")
   public void method5958(int var1, int var2) {
      this.field2739 = 1397226421 * var1;
      this.field2740 = 1296710373 * var2;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lqr;)Z")
   @ObfuscatedName("zs")
   public static boolean method5956(classQR var0) {
      return null != var0.field5597;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("au")
   public int method5938() {
      return this.field2740 * 2094420036;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ad")
   public int method5935() {
      return 1650177407 * this.field2741;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("aw")
   public int method5941() {
      return 1120243403 * this.field2739 >> 7;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("ak")
   public int method5942() {
      return -787916131 * this.field2739 >> 7;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("av")
   public int method5943() {
      return -787916131 * this.field2739 >> 7;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("at")
   public int method5945() {
      return this.field2740 * 1989307627 >> 7;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("an")
   public int method5946() {
      return this.field2740 * -1045233154 >> 7;
   }

   @ObfuscatedSignature(descriptor = "()I")
   @ObfuscatedName("am")
   public int method5948() {
      return 1859166197 * this.field2742;
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Lgy;II)V")
   @ObfuscatedName("uz")
   public static void method5964(classGY var0, int var1, int var2) {
      if (var0 == null) {
         var0.method5966(var1, var1);
      } else {
         var0.field2739 += var1 * 1397226421;
         var0.field2740 += var2 * 1296710373;
      }
   }

   @ObfuscatedSignature(descriptor = "(I)V")
   @ObfuscatedName("ah")
   public void method5951(int var1) {
      this.field2742 = -429665187 * (var1 & 2047);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;I)V")
   @ObfuscatedName("ac")
   public void method5953(classGY var1, int var2) {
      try {
         this.field2742 = var1.field2742 * 1;
         this.field2739 = 1 * var1.field2739;
         this.field2741 = 1 * var1.field2741;
         this.field2740 = var1.field2740 * 1;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gy.ac(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("bx")
   public void method5954(classGY var1) {
      this.field2742 = var1.field2742 * 1;
      this.field2739 = 1 * var1.field2739;
      this.field2741 = 1 * var1.field2741;
      this.field2740 = var1.field2740 * 1;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("bc")
   public void method5955(classGY var1) {
      this.field2742 = var1.field2742 * 1;
      this.field2739 = 1 * var1.field2739;
      this.field2741 = 1 * var1.field2741;
      this.field2740 = var1.field2740 * 1;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("br")
   public void method5959(int var1, int var2) {
      this.field2739 = 1397226421 * var1;
      this.field2740 = 1296710373 * var2;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)Z")
   @ObfuscatedName("bw")
   boolean method5973(classGY var1) {
      return 2077926236 * this.field2739 == -787916131 * var1.field2739
         && this.field2741 * -9602252 == var1.field2741 * 1650177407
         && this.field2740 * -478154003 == var1.field2740 * -478154003
         && 1859166197 * var1.field2742 == -1940900237 * this.field2742;
   }

   @ObfuscatedSignature(descriptor = "(IS)V")
   @ObfuscatedName("ao")
   public void method5961(int var1, short var2) {
      try {
         this.field2741 = var1 * 966134399;
      } catch (RuntimeException var3) {
         throw classEG.method3899(var3, "gy.ao(" + ')');
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bm")
   public void method5960(int var1, int var2) {
      this.field2739 = 2144975346 * var1;
      this.field2740 = 1296710373 * var2;
   }

   @ObfuscatedSignature(descriptor = "()Ljava/lang/String;")
   @ObfuscatedName("gi")
   public String method5978() {
      return this.field2739 * -787916131 + ", " + 1650177407 * this.field2741 + ", " + -478154003 * this.field2740 + ", " + this.field2742 * 1859166197;
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bk")
   public void method5968(int var1, int var2, int var3, int var4) {
      this.field2739 += var1 * -1593759415;
      this.field2741 += var2 * -1271660423;
      this.field2740 += var3 * 1296710373;
      this.method5950(var4 + this.field2742 * 1808185059, -437316553);
   }

   @ObfuscatedSignature(descriptor = "(IIII)V")
   @ObfuscatedName("bo")
   public void method5969(int var1, int var2, int var3, int var4) {
      this.field2739 += var1 * 1397226421;
      this.field2741 += var2 * 1547321988;
      this.field2740 += var3 * 1771197592;
      this.method5950(var4 + this.field2742 * 1859166197, -611724391);
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)V")
   @ObfuscatedName("sm")
   public void method5980(classGY var1) {
      this.method5953(var1, 578993763);
   }

   @ToRemove(unused = "true")
   @ObfuscatedSignature(descriptor = "(Ltq;Ljava/awt/event/WindowEvent;)V")
   @ObfuscatedName("cf")
   public static void method5979(GameEngine var0, WindowEvent var1) {
      if (var0 == null) {
         var0.method11081();
      }
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)Z")
   @ObfuscatedName("bu")
   boolean method5974(classGY var1) {
      return -787916131 * this.field2739 == 1011829641 * var1.field2739
         && this.field2741 * -1332787439 == var1.field2741 * 1650177407
         && this.field2740 * -478154003 == var1.field2740 * -809571305
         && 1859166197 * var1.field2742 == -546842966 * this.field2742;
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bj")
   public void method5965(int var1, int var2) {
      this.field2739 += var1 * 1397226421;
      this.field2740 += var2 * 1296710373;
   }

   @ObfuscatedSignature(descriptor = "(Lgy;)Z")
   @ObfuscatedName("ba")
   boolean method5975(classGY var1) {
      return -787916131 * this.field2739 == -787916131 * var1.field2739
         && this.field2741 * 1650177407 == var1.field2741 * 1650177407
         && this.field2740 * -478154003 == var1.field2740 * -478154003
         && 1859166197 * var1.field2742 == 1859166197 * this.field2742;
   }

   @ObfuscatedSignature(descriptor = "(JLjava/lang/String;B)I")
   @ObfuscatedName("az")
   static final int method5949(long var0, String var2, byte var3) {
      try {
         Random var4 = new Random();
         classXY var5 = new classXY(128);
         classXY var6 = new classXY(128);
         int[] var7 = new int[]{var4.nextInt(), var4.nextInt(), (int)(var0 >> 32), (int)var0};
         classXY.method13029(var5, 10, (byte)-5);

         for (int var8 = 0; var8 < 4; var8++) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            var5.method13037(var4.nextInt(), 977469246);
         }

         var5.method13037(var7[0], -545239523);
         var5.method13037(var7[1], 2104395591);
         classXY.method13047(var5, var0);
         classXY.method13047(var5, 0L);

         for (int var22 = 0; var22 < 4; var22++) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            var5.method13037(var4.nextInt(), -146300208);
         }

         var5.method13195(classDD.field1394, classDD.field1395, 720769824);
         classXY.method13029(var6, 10, (byte)-98);

         for (int var23 = 0; var23 < 3; var23++) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            var6.method13037(var4.nextInt(), -47986130);
         }

         classXY.method13047(var6, var4.nextLong());
         var6.method13039(var4.nextLong());
         if (null != client.field945) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            var6.method13067(client.field945, 0, client.field945.length, (byte)48);
         } else {
            byte[] var24 = classJL.method6431((byte)0);
            var6.method13067(var24, 0, var24.length, (byte)40);
         }

         classXY.method13047(var6, var4.nextLong());
         var6.method13195(classDD.field1394, classDD.field1395, -1920462257);
         int var25 = classAU.method709(var2, (byte)78);
         if (var25 % 8 != 0) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            var25 += 8 - var25 % 8;
         }

         classXY var9 = new classXY(var25);
         var9.method13054(var2, (short)26214);
         var9.field6953 = -1095856699 * var25;
         var9.method13182(var7, (byte)17);
         classXY var10 = new classXY(702114061 * var9.field6953 + 702114061 * var6.field6953 + 5 + 702114061 * var5.field6953);
         classXY.method13029(var10, 2, (byte)-71);
         classXY.method13029(var10, var5.field6953 * 702114061, (byte)-97);
         var10.method13067(var5.field6952, 0, var5.field6953 * 702114061, (byte)44);
         classXY.method13029(var10, 702114061 * var6.field6953, (byte)-54);
         var10.method13067(var6.field6952, 0, 702114061 * var6.field6953, (byte)73);
         var10.method13031(702114061 * var9.field6953, -1542382992);
         var10.method13067(var9.field6952, 0, var9.field6953 * 702114061, (byte)19);
         byte[] var12 = var10.field6952;
         int var14 = var12.length;
         StringBuilder var15 = new StringBuilder();

         for (byte var16 = 0; var16 < var14 + 0; var16 += 3) {
            if (var3 >= 0) {
               throw new IllegalStateException();
            }

            int var17 = var12[var16] & 255;
            var15.append(classOX.field5184[var17 >>> 2]);
            if (var16 < var14 - 1) {
               if (var3 >= 0) {
                  throw new IllegalStateException();
               }

               int var18 = var12[1 + var16] & 255;
               var15.append(classOX.field5184[(var17 & 3) << 4 | var18 >>> 4]);
               if (var16 < var14 - 2) {
                  if (var3 >= 0) {
                     throw new IllegalStateException();
                  }

                  int var19 = var12[var16 + 2] & 255;
                  var15.append(classOX.field5184[(var18 & 15) << 2 | var19 >>> 6]).append(classOX.field5184[var19 & 63]);
               } else {
                  var15.append(classOX.field5184[(var18 & 15) << 2]).append("=");
               }
            } else {
               var15.append(classOX.field5184[(var17 & 3) << 4]).append("==");
            }
         }

         String var13 = var15.toString();
         var13 = var13;

         try {
            URL var28 = new URL(classLZ.method7470("services", false, -1795033336) + "m=accountappeal/login.ws");
            URLConnection var29 = var28.openConnection();
            var29.setDoInput(true);
            var29.setDoOutput(true);
            var29.setConnectTimeout(5000);
            OutputStreamWriter var30 = new OutputStreamWriter(var29.getOutputStream());
            var30.write("data2=" + NPC.method3448(var13, (byte)60) + "&dest=" + NPC.method3448("passwordchoice.ws", (byte)17));
            var30.flush();
            InputStream var31 = var29.getInputStream();
            var10 = new classXY(new byte[1000]);

            do {
               int var32 = var31.read(var10.field6952, var10.field6953 * 702114061, 1000 - var10.field6953 * 702114061);
               if (var32 == -1) {
                  var30.close();
                  var31.close();
                  String var33 = new String(var10.field6952);
                  if (var33.startsWith("OFFLINE")) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     } else {
                        return 4;
                     }
                  } else if (var33.startsWith("WRONG")) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     } else {
                        return 7;
                     }
                  } else if (var33.startsWith("RELOAD")) {
                     if (var3 >= 0) {
                        throw new IllegalStateException();
                     } else {
                        return 3;
                     }
                  } else if (var33.startsWith("Not permitted for social network accounts.")) {
                     return 6;
                  } else {
                     var10.method13186(var7, 1656256084);

                     while (702114061 * var10.field6953 > 0) {
                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        }

                        if (0 != var10.field6952[702114061 * var10.field6953 - 1]) {
                           break;
                        }

                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        }

                        var10.field6953 -= -1095856699;
                     }

                     String var34 = new String(var10.field6952, 0, var10.field6953 * 702114061);
                     if (MessageNode.method1492(var34, 1385240717)) {
                        if (var3 >= 0) {
                           throw new IllegalStateException();
                        } else {
                           classUF.method11359(var34, (byte)-13);
                           return 2;
                        }
                     } else {
                        return 5;
                     }
                  }
               }

               var10.field6953 += -1095856699 * var32;
            } while (var10.field6953 * 702114061 < 1000);

            return 5;
         } catch (Throwable var20) {
            var20.printStackTrace();
            return 5;
         }
      } catch (RuntimeException var21) {
         throw classEG.method3899(var21, "gy.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("az")
   public int method5932(int var1) {
      try {
         return -787916131 * this.field2742;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.az(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(I)I")
   @ObfuscatedName("as")
   public int method5939(int var1) {
      try {
         return this.field2739 * -478154003;
      } catch (RuntimeException var2) {
         throw classEG.method3899(var2, "gy.ae(" + 41);
      }
   }

   @ObfuscatedSignature(descriptor = "(II)V")
   @ObfuscatedName("bt")
   public void method5966(int var1, int var2) {
      this.field2739 += var1 * 1397226421;
      this.field2741 += var2 * 1296710373;
   }
}
